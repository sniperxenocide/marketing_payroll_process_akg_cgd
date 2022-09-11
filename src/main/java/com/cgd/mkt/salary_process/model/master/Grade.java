package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Grade {
    @Id
    private Long id;
    private String name;

}
