package com.cgd.mkt.salary_process.repository.mis;

import com.cgd.mkt.salary_process.model.mis.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface ReSales extends JpaRepository<Sales,Long> {

    @Query(value = "select SUM(SalesValue) from Daily_SR_SKU_Wise_Summary " +
            "where SalesDate between ?2 and ?3 and SRID = ?1 ",
            nativeQuery = true)
    Double getSumSalesValue(Long srId, LocalDate start, LocalDate end);

    @Query(value = "select count(*) from (SELECT SalesDate " +
            "FROM Daily_SR_SKU_Wise_Summary WHERE SalesDate between ?2 and ?3 AND SRID = ?1 " +
            "AND DATEPART(WEEKDAY, SalesDate) != 6 " +  // AND SalesDate not in ('')
            "Group By(SalesDate) having SUM(SalesValue) > 0) A ",nativeQuery = true)
    Integer getTotalSalesDays(Long srId,LocalDate start,LocalDate end);


}
