package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor @ToString
@Entity
@Table(name = "marketing_node")
public class MarketingNode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Column(name = "id")
    private Long id;
    @Column(name = "node_id")
    private int nodeId;
    @Column(name = "node_name")
    private String nodeName;
    @Column(name = "node_level")
    private int nodeLevel;
    @Column(name = "parent_node_id")
    private int parentNodeId;
    @Column(name = "parent_node_name")
    private String parentNodeName;
    @Column(name = "parent_node_level")
    private int parentNodeLevel;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private MarketingNodeGroup group;
}
