package com.cgd.mkt.salary_process.controller;

import com.cgd.mkt.salary_process.repository.master.ReCalculatedSalary;
import com.cgd.mkt.salary_process.repository.master.ReProcess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class DashboardController {
    private final ReProcess reProcess;
    private final ReCalculatedSalary reCalculatedSalary;

    public DashboardController(ReProcess reProcess, ReCalculatedSalary reCalculatedSalary) {
        this.reProcess = reProcess;
        this.reCalculatedSalary = reCalculatedSalary;
    }

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model){
        try {
            ArrayList<String> list = reCalculatedSalary.getGradeWisePercentByProcessId(Integer.toString(62));
            for (String l:list) System.out.println(l);
        }catch (Exception e){e.printStackTrace();}
        try {
            model.addAttribute("msg","Welcome to Salary Processing Dashboard");
            model.addAttribute("totalRun",reProcess.count());
            model.addAttribute("totalProcessedEmployee",reCalculatedSalary.count());
            String lastRun = reProcess.getMaxStartTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss"));
            model.addAttribute("lastRun",lastRun);
            return "dashboard";
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            return "response";
        }

    }
}
