package com.cgd.mkt.salary_process.model.mis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @ToString
@Table(name = "Daily_SR_SKU_Wise_Summary")
public class Sales {

    @Id @Column(name = "ItemID")
    private Long id;

    @Column(name = "SRID")
    private Long employeeId;

    @Column(name = "SalesDate")
    private LocalDateTime salesDate;

    @Column(name = "SalesValue")
    private Double salesValue;

}
