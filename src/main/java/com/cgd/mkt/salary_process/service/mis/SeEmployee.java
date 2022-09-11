package com.cgd.mkt.salary_process.service.mis;

import com.cgd.mkt.salary_process.model.mis.Employees;
import com.cgd.mkt.salary_process.repository.master.ReDesignation;
import com.cgd.mkt.salary_process.repository.mis.ReEmployees;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SeEmployee {

    private final ReEmployees repository;
    private final ReDesignation reDesignation;

    public SeEmployee(ReEmployees repository, ReDesignation reDesignation) {
        this.repository = repository;
        this.reDesignation = reDesignation;
    }

    public ArrayList<Long> getActiveEmployeeIds(){
        return repository.getAllEmpIdsByDesignationList(reDesignation.getAllActive());
    }

    public Employees getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public int countActiveEmployees(){
        return getActiveEmployeeIds().size();
    }
}
