package com.raspberryawards.raspberry_awards.controller;

import com.raspberryawards.raspberry_awards.dto.WinnerResult;
import com.raspberryawards.raspberry_awards.entity.Award;
import com.raspberryawards.raspberry_awards.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/awards")
public class AwardController {

    @Autowired
    private ImportService service;

    /**
     * Endpoint return movie awarded
     * */
    @GetMapping(value = "/result")
    ResponseEntity<List<WinnerResult>> findResult() throws Exception {
        List<WinnerResult> list = service.findResult();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    List<Award> findAll() {
        List<Award> list = service.findAll();
        return list;
    }

}
