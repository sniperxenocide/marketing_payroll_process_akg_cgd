package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.MarketingNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReMarketingNode extends JpaRepository<MarketingNode, Long> {
    Optional<MarketingNode> findByNodeId(int nodeId);
}