package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReGrade extends JpaRepository<Grade, Long> {

}