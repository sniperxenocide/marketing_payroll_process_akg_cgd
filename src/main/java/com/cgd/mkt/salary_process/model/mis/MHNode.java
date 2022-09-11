package com.cgd.mkt.salary_process.model.mis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter @NoArgsConstructor
@Entity
public class MHNode {
    @Id @Column(name = "NodeID")
    private Long nodeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "ParentID")
    private Long parentId;
}
