package com.raspberryawards.raspberry_awards.service;

import com.raspberryawards.raspberry_awards.dto.WinnerResult;
import com.raspberryawards.raspberry_awards.entity.Award;

import java.util.List;

public interface ImportService {
    void importCsv ();

    List<Award> findAll();

    List<WinnerResult> findResult() throws Exception;
}
