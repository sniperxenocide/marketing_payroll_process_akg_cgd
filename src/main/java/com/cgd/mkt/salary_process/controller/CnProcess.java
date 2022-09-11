package com.cgd.mkt.salary_process.controller;

import com.cgd.mkt.salary_process.service.master.SeProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CnProcess {
    private final SeProcess service;

    public CnProcess(SeProcess service) {
        this.service = service;
    }

    @GetMapping("/process/status")
    public ResponseEntity<Object> getProcessStatus(){
        return new ResponseEntity<>(service.getRunningProcessStatus(), HttpStatus.OK);
    }
}
