package com.cgd.mkt.salary_process.controller;

import com.cgd.mkt.salary_process.service.master.SeCalculatedSalary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalaryController {
    private final SeCalculatedSalary service;

    public SalaryController(SeCalculatedSalary service) {
        this.service = service;
    }

    @GetMapping("/salary")
    public String getSalaryPage(Model model, @RequestParam Long processId){
        try {
            model.addAttribute("process",service.getProcess(processId));
            model.addAttribute("salary",service.getAllSalaryByProcess(processId));
            return "salary";
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("redirect","/process");
            return "response";
        }
    }
}
