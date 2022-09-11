package com.cgd.mkt.salary_process.model.master;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
@Entity
public class ManualPayableDays {
    @Id private Long id;
    private Long employeeId;
    private int payableDays;
    private Long periodId;
    private String periodCode;

}
