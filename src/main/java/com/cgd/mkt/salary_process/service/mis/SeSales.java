package com.cgd.mkt.salary_process.service.mis;

import com.cgd.mkt.salary_process.model.master.SalaryPeriod;
import com.cgd.mkt.salary_process.model.mis.Employees;
import com.cgd.mkt.salary_process.repository.mis.ReSales;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class SeSales {
    private final ReSales repository;

    public SeSales(ReSales repository) {
        this.repository = repository;
    }

//    public Double getTotalSalesValue(Long employeeId, Date start,Date end){
//        return repository.getSumSalesValue(employeeId,start,end);
//    }
//
//    public Integer getTotalSalesDays(Long employeeId, Date start,Date end){
//        return repository.getTotalSalesDays(employeeId,start,end);
//    }

    public Double getTotalSales(Employees emp, SalaryPeriod salaryPeriod) throws Exception{
        LocalDate start = salaryPeriod.getStartDate();
        LocalDate end = salaryPeriod.getEndDate();
        if(emp.getJoiningDate()!=null && emp.getJoiningDate().isAfter(salaryPeriod.getStartDate()))
            start = emp.getJoiningDate();
        return repository.getSumSalesValue(emp.getEmployeeId(),start,end);
    }

    public int getValidSalesDays(Employees emp, SalaryPeriod salaryPeriod) throws Exception{
        LocalDate start = salaryPeriod.getStartDate();
        LocalDate end = salaryPeriod.getEndDate();
        if(emp.getJoiningDate()!=null && emp.getJoiningDate().isAfter(salaryPeriod.getStartDate()))
            start = emp.getJoiningDate();
        return repository.getTotalSalesDays(emp.getEmployeeId(),start,end);
    }
}
