package com.cgd.mkt.salary_process.repository.mis;

import com.cgd.mkt.salary_process.model.mis.MHNode;
import com.cgd.mkt.salary_process.model.mis.SalesPointMHNodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReSalesPointMHNodes extends JpaRepository<SalesPointMHNodes,Long> {
    Optional<SalesPointMHNodes> findBySalesPointId(Long salesPointId);
}
