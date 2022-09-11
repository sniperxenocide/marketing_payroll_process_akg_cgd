package com.cgd.mkt.salary_process.repository.mis;

import com.cgd.mkt.salary_process.model.mis.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReEmployees extends JpaRepository<Employees,Long> {

    @Query(value = " select EmployeeID from Employees " +
            " where DesignationID in ?1 and Status = 16 order by EmployeeID ",
            nativeQuery = true )
    ArrayList<Long> getAllEmpIdsByDesignationList(long[] designationIds);

}
