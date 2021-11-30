package com.raspberryawards.raspberry_awards.service.serviceImpl;

import com.raspberryawards.raspberry_awards.dto.Winner;
import com.raspberryawards.raspberry_awards.dto.WinnerResult;
import com.raspberryawards.raspberry_awards.entity.Award;
import com.raspberryawards.raspberry_awards.helper.CSVHelper;
import com.raspberryawards.raspberry_awards.repository.AwardRepository;
import com.raspberryawards.raspberry_awards.service.ImportService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportServiceImpl implements ImportService {

    private AwardRepository repository;

    public ImportServiceImpl(AwardRepository repository) {
        this.repository = repository;
    }

    private static String csvFile = "/home/max/IdeaProjects/raspberry_awards/src/main/resources/static/movielist.csv";

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void importCsv() {
        try {
            String csvFileName = csvFile;
            InputStream inputStream = new FileInputStream(csvFileName);
            List<Award> awardList = CSVHelper.csvToAward(inputStream);
            repository.saveAll(awardList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<WinnerResult> findResult() throws Exception {

        List<Object[]> arrayListFromDb = repository.findDistinct();

        if (arrayListFromDb.isEmpty())
            throw new Exception("No productors with two awards!");

        WinnerResult winners = new WinnerResult();
        List<WinnerResult> winnerResults = new ArrayList<>();
        List<Winner> minWinnerList = new ArrayList<>();
        List<Winner> maxWinnerList = new ArrayList<>();

        //For each procucer take awards list
        for (Object[] record : arrayListFromDb) {
            List<Award> sortedAwardList = new ArrayList<>();


            List<Award> awardsFromProdutors = this.awardsFromProdutors(record);
            sortedAwardList = awardsFromProdutors.stream().sorted(Comparator.comparing(Award::getYear)).collect(Collectors.toList());
            List<Award> minAwardList = new ArrayList<>(sortedAwardList);
            List<Award> maxAwardList = new ArrayList<>(sortedAwardList);
            minAwardList = this.onlyTwoItemsMin(minAwardList);
            maxAwardList = this.maxAwards(maxAwardList);

            Winner minWinner = new Winner(minAwardList.get(0).getProducers(),
                    (minAwardList.get(1).getYear() - minAwardList.get(0).getYear()),
                    minAwardList.get(0).getYear(), minAwardList.get(1).getYear());
            Winner maxWinner = new Winner(maxAwardList.get(0).getProducers(),
                    (maxAwardList.get(1).getYear() - maxAwardList.get(0).getYear()),
                    maxAwardList.get(0).getYear(), maxAwardList.get(1).getYear());
            minWinnerList.add(minWinner);
            maxWinnerList.add(maxWinner);
            winners.setMin(minWinnerList);
            winners.setMax(maxWinnerList);
            winnerResults.add(winners);
        }
        return winnerResults;
    }

    /**
     * Return award sequence of a productors ids
     * @param record
     * @return awardsFromProdutors
     * */
    protected List<Award> awardsFromProdutors(Object[] record){
        String[] idsString = record[0].toString().split(",");
        List<Award> awardList = new ArrayList<>();
        for (String idString : idsString){
            Award award;
            String stringToConvert = String.valueOf(idString);
            Long idLong = Long.parseLong(stringToConvert);
            award = repository.findById(idLong).get();
            awardList.add(award);
        }
        return awardList;
    }

    /**
     * Return a list of two awards with the min interval
     * @param list
     * @return onlyTwoItemsMin
     * */
    protected List<Award> onlyTwoItemsMin(List<Award> list){
        while (list.size() > 2)
            list.remove(list.size() - 1 );
        return list;
    }

    /**
     * Return award sequence of a productors ids with max interval
     * @param list
     * @return awardsFromProdutors
     * */
    protected List<Award> maxAwards(List<Award> list){
        List<Award> maxAwardList = new ArrayList<>();
        maxAwardList.add(list.get(0));
        maxAwardList.add(list.get(list.size() -1));
        return maxAwardList;
    }

    /**
     * Moocked service to test endpoint
     * */
    @Override
    public List<Award> findAll() {
        Award award = new Award();
        award.setId(1L);
        award.setProducers("Spielberg");
        award.setWinner(true);
        award.setStudios("MGM");
        award.setYear(1981);
        List<Award> awardList = new ArrayList<>();
        awardList.add(award);
        return awardList;
    }

}
