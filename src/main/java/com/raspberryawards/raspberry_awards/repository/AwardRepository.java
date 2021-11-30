package com.raspberryawards.raspberry_awards.repository;

import com.raspberryawards.raspberry_awards.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findByWinnerTrue();

    @Query(value = "select group_concat(distinct(a.id)), count(a.producers) from award a " +
            "where a.winner = 'yes' " +
            "group by a.producers " +
            "having count(a.producers) > 1 " +
            "order by a.producers;", nativeQuery = true)
    List<Object[]> findDistinct();
}
