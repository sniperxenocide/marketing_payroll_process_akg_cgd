package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "marketing_node_group")
public class MarketingNodeGroup {
    @Id
    private Long id;
    private String name;
}
