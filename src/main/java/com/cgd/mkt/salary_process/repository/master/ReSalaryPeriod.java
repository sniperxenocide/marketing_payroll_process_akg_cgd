package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.enums.PeriodStatus;
import com.cgd.mkt.salary_process.model.master.SalaryPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReSalaryPeriod extends JpaRepository<SalaryPeriod, Long> {
    ArrayList<SalaryPeriod> findAllByStatus(PeriodStatus status);
}