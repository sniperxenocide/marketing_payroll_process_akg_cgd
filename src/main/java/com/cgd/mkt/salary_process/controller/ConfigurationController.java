package com.cgd.mkt.salary_process.controller;

import com.cgd.mkt.salary_process.repository.master.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConfigurationController {
    private final ReDesignation reDesignation;
    private final ReGrade reGrade;
    private final ReSalaryPeriod reSalaryPeriod;
    private final ReMarketingNode reMarketingNode;
    private final ReSalesConfig reSalesConfig;

    public ConfigurationController(ReDesignation reDesignation, ReGrade reGrade, ReSalaryPeriod reSalaryPeriod, ReMarketingNode reMarketingNode, ReSalesConfig reSalesConfig) {
        this.reDesignation = reDesignation;
        this.reGrade = reGrade;
        this.reSalaryPeriod = reSalaryPeriod;
        this.reMarketingNode = reMarketingNode;
        this.reSalesConfig = reSalesConfig;
    }

    @GetMapping("/configuration")
    public String configurationPage(Model model){
        model.addAttribute("periods",
                reSalaryPeriod.findAll(Sort.by(Sort.Order.desc("id"))));
        model.addAttribute("designations",reDesignation.findAll(Sort.by("id")));
        model.addAttribute("grades",reGrade.findAll(Sort.by("id")));
        model.addAttribute("nodes",reMarketingNode.findAll(Sort.by("group_id")));
        model.addAttribute("salesConfig",reSalesConfig.
                findAll(Sort.by("designation_id").and(Sort.by("group_id"))));
        return "configuration";
    }
}
