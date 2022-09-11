package com.cgd.mkt.salary_process.model.mis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @ToString
@Table(name = "Employees")
public class Employees {

    @Column(name = "EmployeeID")
    @Id private Long employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Code")
    private String oracleCode;

    @Column(name = "DesignationID")
    private Long designationId;

    @Column(name = "Designation")
    private String designation;

    @Column(name = "Status")
    private Long status;

    @Column(name = "SalesPointID")
    private Long salesPointID;

    @Column(name = "JoiningDate")
    private LocalDate joiningDate;

}
