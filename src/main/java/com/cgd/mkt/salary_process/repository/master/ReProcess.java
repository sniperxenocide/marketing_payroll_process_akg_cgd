package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.enums.ProcessStatus;
import com.cgd.mkt.salary_process.model.master.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public interface ReProcess extends JpaRepository<Process,Long> {
    ArrayList<Process> findByStatusOrderByIdDesc(ProcessStatus status);

    @Query(value = "select  process_start_time from process order by id desc limit 1 "
            ,nativeQuery = true)
    LocalDateTime getMaxStartTime();
}
