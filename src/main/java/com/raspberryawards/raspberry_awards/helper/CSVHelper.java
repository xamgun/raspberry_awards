package com.raspberryawards.raspberry_awards.helper;

import com.raspberryawards.raspberry_awards.entity.Award;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<Award> csvToAward(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT
                             .withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim()
                             .withAllowMissingColumnNames()
                             .withDelimiter(';'));) {

            List<Award> awardList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Award award = new Award(
                        Integer.parseInt(csvRecord.get("year")),
                        csvRecord.get("title"),
                        csvRecord.get("studios"),
                        csvRecord.get("producers"),
                        csvRecord.get("winner").equals("yes") ? true : false
                );

                awardList.add(award);
            }

            return awardList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
