package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReUser extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
