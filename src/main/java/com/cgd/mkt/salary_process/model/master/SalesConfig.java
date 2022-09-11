package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "sales_config")
public class SalesConfig  {

    @Id private Long id;
    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private MarketingNodeGroup group;
    @Column(name = "min_sales")
    private Double minSales;
    @Column(name = "max_sales")
    private Double maxSales;
    @Column(name = "basic_salary")
    private Double basicSalary;

}
