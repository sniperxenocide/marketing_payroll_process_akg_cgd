package com.cgd.mkt.salary_process.service.master;

import com.cgd.mkt.salary_process.model.master.CalculatedSalary;
import com.cgd.mkt.salary_process.model.master.Process;
import com.cgd.mkt.salary_process.repository.master.ReCalculatedSalary;
import com.cgd.mkt.salary_process.repository.master.ReProcess;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SeCalculatedSalary {
    private final ReCalculatedSalary repository;
    private final ReProcess reProcess;

    public SeCalculatedSalary(ReCalculatedSalary repository, ReProcess reProcess) {
        this.repository = repository;
        this.reProcess = reProcess;
    }

    public Process getProcess(Long processId) throws Exception{
        Process process = reProcess.findById(processId).orElse(null);
        if(process == null) throw  new Exception("Process Not Found");
        return process;
    }

    public ArrayList<CalculatedSalary> getAllSalaryByProcess(Long processId){
        return repository.findAllByProcessId(processId);
    }
}
