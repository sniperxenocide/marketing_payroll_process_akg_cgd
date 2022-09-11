package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.SalesConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReSalesConfig extends JpaRepository<SalesConfig, Long>{

    @Query(value = "select * from sales_config " +
            "where group_id = ?1 and designation_id = ?2 " +
            "and ?3 between min_sales and max_sales ",
            nativeQuery = true)
    Optional<SalesConfig> getGradeSalary(Long groupId,Long designationId,Double avgSales);
}