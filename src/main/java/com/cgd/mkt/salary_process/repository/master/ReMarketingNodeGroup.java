package com.cgd.mkt.salary_process.repository.master;

import com.cgd.mkt.salary_process.model.master.MarketingNodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReMarketingNodeGroup extends JpaRepository<MarketingNodeGroup, Long>{

}