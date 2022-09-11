package com.cgd.mkt.salary_process.repository.mis;

import com.cgd.mkt.salary_process.model.mis.MHNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReMHNode extends JpaRepository<MHNode,Long> {
    Optional<MHNode> findByNodeId(Long nodeId);
}
