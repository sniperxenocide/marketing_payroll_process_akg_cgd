package com.cgd.mkt.salary_process.service.mis;

import com.cgd.mkt.salary_process.model.master.MarketingNode;
import com.cgd.mkt.salary_process.model.mis.MHNode;
import com.cgd.mkt.salary_process.model.mis.SalesPointMHNodes;
import com.cgd.mkt.salary_process.repository.master.ReMarketingNode;
import com.cgd.mkt.salary_process.repository.mis.ReMHNode;
import org.springframework.stereotype.Service;

@Service
public class SeMHNode {
    private final ReMHNode repository;
    private final SeSalesPointMHNodes seSalesPointMHNodes;
    private final ReMarketingNode reMarketingNode;


    public SeMHNode(ReMHNode repository, SeSalesPointMHNodes seSalesPointMHNodes, ReMarketingNode reMarketingNode) {
        this.repository = repository;
        this.seSalesPointMHNodes = seSalesPointMHNodes;
        this.reMarketingNode = reMarketingNode;
    }

    public MarketingNode getMainParent(Long salesPointId) throws Exception{
        SalesPointMHNodes salesPoint = seSalesPointMHNodes.getBySalesPointId(salesPointId);
        if(salesPoint==null) throw new Exception("Sales Point not Found.");
        MHNode mhNode = repository.findByNodeId(salesPoint.getNodeId()).orElse(null);
        if(mhNode==null) throw new Exception("MHNode not Found.");
        while (true){
            MarketingNode marketingNode = reMarketingNode.findByNodeId(mhNode.getNodeId().intValue()).orElse(null);
            if(marketingNode==null){
                if (mhNode.getParentId()==null) throw new Exception("Marketing Node not Found.");
                mhNode = repository.findByNodeId(mhNode.getParentId()).orElse(null);
                if(mhNode==null) throw new Exception("Marketing Node not Found.");
            }
            else return marketingNode;
        }
    }
}
