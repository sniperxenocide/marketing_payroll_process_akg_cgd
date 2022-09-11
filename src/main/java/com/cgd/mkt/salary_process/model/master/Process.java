package com.cgd.mkt.salary_process.model.master;

import com.cgd.mkt.salary_process.enums.ProcessMode;
import com.cgd.mkt.salary_process.enums.ProcessStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private SalaryPeriod period;

    @CreationTimestamp
    @Column(name = "process_start_time")
    private LocalDateTime processStartTime;

    @Column(name = "process_end_time")
    private LocalDateTime processEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_mode")
    private ProcessMode processMode;

    @Column(name = "total_employee")
    private int totalEmployee;

    @Column(name = "processed_employee")
    private int processedEmployee;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @ManyToOne
    @JoinColumn(name = "run_by")
    private User runBy;

}