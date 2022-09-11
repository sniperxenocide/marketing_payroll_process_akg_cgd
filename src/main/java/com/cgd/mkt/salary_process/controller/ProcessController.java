package com.cgd.mkt.salary_process.controller;

import com.cgd.mkt.salary_process.enums.ProcessMode;
import com.cgd.mkt.salary_process.enums.ProcessStatus;
import com.cgd.mkt.salary_process.model.master.Process;
import com.cgd.mkt.salary_process.service.master.SeProcess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProcessController {
    private final SeProcess service;

    public ProcessController(SeProcess service) {
        this.service = service;
    }

    @GetMapping("/process")
    public String getProcessPage(Model model){
        Process process = service.getRunningProcess();
        if (process==null) process = new Process();
        model.addAttribute("process",process);
        model.addAttribute("periods",service.getOpenPeriods());
        model.addAttribute("processModes", ProcessMode.values());
        model.addAttribute("processes",service.getAllCompletedProcess());
        return "process";
    }

    @PostMapping("/process/start")
    public String startProcess(Model model, @ModelAttribute("process") Process process,
                               HttpServletRequest request){
        try {
            System.out.println(process);
            int empCount = service.createNewProcess(process,request);
            model.addAttribute("msg","Process Started for Period: "
                    +process.getPeriod().getDescription()+" of "+empCount+" Employees");
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
        }
        model.addAttribute("redirect","/process");
        return "response";
    }


    @PostMapping("/process/stop")
    public String stopProcess(Model model,@RequestParam Long id){
        try {
            service.stopProcess(id);
            model.addAttribute("msg","Process Stopped Successfully");
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
        }
        model.addAttribute("redirect","/process");
        return "response";
    }
}
