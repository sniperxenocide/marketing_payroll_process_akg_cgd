package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Role {
    @Id private Long id;
    private String roleName;
    private String description;

}
