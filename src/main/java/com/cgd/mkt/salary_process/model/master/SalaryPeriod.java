package com.cgd.mkt.salary_process.model.master;

import com.cgd.mkt.salary_process.enums.PeriodStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "salary_period")
public class SalaryPeriod  {
    @Id private Long id;
    private int year;
    private int month;
    private String code;
    private String description;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Enumerated(value = EnumType.STRING)
    private PeriodStatus status;
}
