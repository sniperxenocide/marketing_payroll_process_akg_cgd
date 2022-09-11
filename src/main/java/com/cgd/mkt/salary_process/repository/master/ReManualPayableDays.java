package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.ManualPayableDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReManualPayableDays extends JpaRepository<ManualPayableDays, Long> {

}