package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReDesignation extends JpaRepository<Designation, Long>{

    @Query(value = " select mis_id from designation where active = 'YES' ", nativeQuery = true)
    long[] getAllActive();

    Optional<Designation> findByMisId(Long misId);
}