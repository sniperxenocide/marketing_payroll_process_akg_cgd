package com.cgd.mkt.salary_process.model.master;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id private Long id;
    private String username;
    private String password;
}
