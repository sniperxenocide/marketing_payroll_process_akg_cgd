package com.cgd.mkt.salary_process.model.mis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor
@Entity
public class SalesPointMHNodes {
    @Id @Column(name = "SalesPointID") private Long salesPointId;
    @Column(name = "NodeID") private Long nodeId;
}
