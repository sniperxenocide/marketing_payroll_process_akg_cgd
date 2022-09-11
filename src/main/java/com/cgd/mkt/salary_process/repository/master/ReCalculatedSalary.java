package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.CalculatedSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public interface ReCalculatedSalary extends JpaRepository<CalculatedSalary, Long> {
    ArrayList<CalculatedSalary> findAllByProcessId(Long processId);


    @Query(value = " select grade as Grade, " +
            " concat( " +
            " round(count(*)/ " +
            "    ( " +
            "       select count(*) from calculated_salary where process_id like ?1 " +
            "    )*100,2) , '%') Percent " +
            " from calculated_salary " +
            " where process_id like ?1 " +
            " group by grade ",
    nativeQuery = true)
    ArrayList<String> getGradeWisePercentByProcessId(String processId);
}