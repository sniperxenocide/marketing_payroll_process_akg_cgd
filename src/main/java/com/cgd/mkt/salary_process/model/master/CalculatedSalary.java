package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "calculated_salary")
public class CalculatedSalary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "period_id")
    private SalaryPeriod period;

    @Column(name = "period_code")
    private String periodCode;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "oracle_code")
    private String oracleCode;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designationObject;

    private String designation;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "marketing_node_id")
    private MarketingNode marketingNode;

    @Column(name = "marketing_node_name")
    private String marketingNodeName;

    @Column(name = "node_group")
    private String nodeGroup;

    @Column(name = "total_sales")
    private Double totalSales;

    @Column(name = "valid_sales_days")
    private Integer validSalesDays;

    @Column(name = "average_sales")
    private Double averageSales;

    @Column(name = "basic_salary")
    private Double basicSalary;

    private String grade;

    @Column(name = "payable_days")
    private Integer payableDays;

    @Column(name = "payable_salary")
    private Double payableSalary;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "sales_config_id")
    private SalesConfig salesConfig;

    private String comment;

}
