package com.cgd.mkt.salary_process.model.master;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id private Long id;
    @Column(name = "user_id") private Long userId;
    @Column(name = "role_id") private Long roleId;
}
