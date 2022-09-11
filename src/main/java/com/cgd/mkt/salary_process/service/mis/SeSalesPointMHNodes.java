package com.cgd.mkt.salary_process.service.mis;

import com.cgd.mkt.salary_process.model.mis.SalesPointMHNodes;
import com.cgd.mkt.salary_process.repository.mis.ReSalesPointMHNodes;
import org.springframework.stereotype.Service;

@Service
public class SeSalesPointMHNodes {
    private final ReSalesPointMHNodes repository;

    public SeSalesPointMHNodes(ReSalesPointMHNodes repository) {
        this.repository = repository;
    }

    public SalesPointMHNodes getBySalesPointId(Long salesPointId){
        if(salesPointId==null) return null;
        return repository.findBySalesPointId(salesPointId).orElse(null);
    }
}
