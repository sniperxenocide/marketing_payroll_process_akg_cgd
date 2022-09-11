package com.cgd.mkt.salary_process.model.master;

import com.cgd.mkt.salary_process.enums.Active;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Designation{
    @Id private Long id;
    @Column(name = "mis_id")
    private Long misId;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Active active;

}
