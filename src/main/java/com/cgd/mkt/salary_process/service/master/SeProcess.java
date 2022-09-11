package com.cgd.mkt.salary_process.service.master;

import com.cgd.mkt.salary_process.enums.PeriodStatus;
import com.cgd.mkt.salary_process.enums.ProcessMode;
import com.cgd.mkt.salary_process.enums.ProcessStatus;
import com.cgd.mkt.salary_process.model.master.*;
import com.cgd.mkt.salary_process.model.master.Process;
import com.cgd.mkt.salary_process.model.mis.Employees;
import com.cgd.mkt.salary_process.repository.master.*;
import com.cgd.mkt.salary_process.request_response.Response;
import com.cgd.mkt.salary_process.service.SeCommon;
import com.cgd.mkt.salary_process.service.mis.SeEmployee;
import com.cgd.mkt.salary_process.service.mis.SeMHNode;
import com.cgd.mkt.salary_process.service.mis.SeSales;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SeProcess {
    private final SeEmployee seEmployee;
    private final SeSales seSales;
    private final SeCommon seCommon;
    private final SeMHNode seMHNode;
    private final ReDesignation reDesignation;
    private final ReSalesConfig reSalesConfig;
    private final ReSalaryPeriod reSalaryPeriod;
    private final ReProcess reProcess;
    private final ReCalculatedSalary reCalculatedSalary;

    private static boolean currentProcessRunning = false;

    public SeProcess(SeEmployee seEmployee, SeSales seSales, ReSalaryPeriod reSalaryPeriod, ReProcess reProcess, SeCommon seCommon, ReCalculatedSalary reCalculatedSalary, SeMHNode seMHNode, ReDesignation reDesignation, ReSalesConfig reSalesConfig) {
        this.seEmployee = seEmployee;
        this.seSales = seSales;
        this.reSalaryPeriod = reSalaryPeriod;
        this.reProcess = reProcess;
        this.seCommon = seCommon;
        this.reCalculatedSalary = reCalculatedSalary;
        this.seMHNode = seMHNode;
        this.reDesignation = reDesignation;
        this.reSalesConfig = reSalesConfig;
    }

    public ArrayList<SalaryPeriod> getOpenPeriods(){
        return reSalaryPeriod.findAllByStatus(PeriodStatus.OPEN);
    }

    public ArrayList<Process> getAll(){
        return (ArrayList<Process>) reProcess.findAll(Sort.by(Sort.Order.desc("id")));
    }

    public Process getRunningProcess(){
        ArrayList<Process> processes = reProcess.findByStatusOrderByIdDesc(ProcessStatus.RUNNING);
        if (processes.size()==1) return processes.get(0);
        return null;
    }

    public Response getRunningProcessStatus(){
        Process process = getRunningProcess();
        HashMap<String,Object> map = new HashMap<>();
        if(process == null)
            return new Response(false,"No Running Process Found");

        double percent =  ((double)process.getProcessedEmployee()/(double)process.getTotalEmployee())*100;
        percent = new BigDecimal(percent).
                setScale(2, RoundingMode.HALF_UP).doubleValue();
        long timePassed = ChronoUnit.SECONDS.between(process.getProcessStartTime(),LocalDateTime.now());
        long timeToEnd = 0;
        try {timeToEnd = (long) (((double)timePassed/percent)*(100-percent));
        }catch (Exception ignored){}

        double timePassedD = (double)timePassed/60.0;
        double timeToEndD = (double)timeToEnd/60.0;

        timePassedD = new BigDecimal(timePassedD).setScale(2, RoundingMode.HALF_UP).doubleValue();
        timeToEndD = new BigDecimal(timeToEndD).setScale(2, RoundingMode.HALF_UP).doubleValue();

        map.put("id",process.getId());
        map.put("processedEmployee",process.getProcessedEmployee());
        map.put("percent",percent+" %");
        map.put("timePassed",timePassedD+" Min");
        map.put("timeToEnd",timeToEndD+" Min");
        return new Response(true,"Process Running",map);
    }

    public ArrayList<Process> getAllCompletedProcess(){
        return reProcess.findByStatusOrderByIdDesc(ProcessStatus.COMPLETED);
    }

    public void stopProcess(Long id) throws Exception{
        Process process = reProcess.findById(id).orElse(null);
        if(process==null) throw new Exception("Process Not Found");
        if(!process.getStatus().equals(ProcessStatus.RUNNING))
            throw new Exception("Process Already Completed");
        currentProcessRunning = false;
    }

    public int createNewProcess(Process process, HttpServletRequest request) throws Exception{
        int empCount = 0;
        try {
            empCount = seEmployee.countActiveEmployees();
        }catch (Exception e){throw new Exception("MIS Database Connection Failed");}
        process.setStatus(ProcessStatus.RUNNING);
        currentProcessRunning = true;
        User user = seCommon.getUser(request);
        if(user == null) throw new Exception("Unauthorized Request");
        process.setRunBy(user);
        if(process.getProcessMode().equals(ProcessMode.FINAL)){
            process.getPeriod().setStatus(PeriodStatus.CLOSED);
            reSalaryPeriod.save(process.getPeriod());
        }
        reProcess.save(process);
        startSalaryProcessingThread(process);
        return empCount;
    }


    private void startSalaryProcessingThread(Process process){
        new Thread(()->{
            ArrayList<Long> empIds = seEmployee.getActiveEmployeeIds();
            process.setTotalEmployee(empIds.size());
            reProcess.save(process);
            for (Long i:empIds){
                if(!currentProcessRunning) {
                    System.out.println("Process Stop By Command");
                    break;
                }
                try {
                    Employees employee = seEmployee.getById(i);
                    if(employee!=null) perEmployeeProcess(employee,process);
                }catch (Exception e){ System.out.println(e.getMessage());}

                process.setProcessedEmployee(process.getProcessedEmployee()+1);
                reProcess.save(process);
            }
            process.setProcessEndTime(LocalDateTime.now());
            process.setStatus(ProcessStatus.COMPLETED);
            reProcess.save(process);
            System.out.println("Process Completed");
        }).start();
    }

    private void perEmployeeProcess(Employees employee, Process process){
        CalculatedSalary calculatedSalary = new CalculatedSalary();
        StringBuilder commentBuilder = new StringBuilder();
        System.out.println("Processing Employee Id: "+employee.getEmployeeId());

        try {populateEmpBasicInfo(employee,calculatedSalary,process);
        }catch (Exception e){e.printStackTrace();commentBuilder.append(e.getMessage());}

        try {updateMarketingNodeInfo(employee,calculatedSalary);
        }catch (Exception e){e.printStackTrace();commentBuilder.append(e.getMessage());}

        try { updateSalesInfo(employee,calculatedSalary);
        }catch (Exception e){e.printStackTrace();commentBuilder.append(e.getMessage());}

        try { updateGradeSalaryInfo(employee,calculatedSalary);
        }catch (Exception e){e.printStackTrace();commentBuilder.append(e.getMessage());}

        try {
            calculatedSalary.setComment(commentBuilder.toString());
            reCalculatedSalary.save(calculatedSalary);
        }catch (Exception ignored){}
    }

    private void populateEmpBasicInfo(Employees emp,CalculatedSalary calculatedSalary,Process process) throws Exception{
        calculatedSalary.setPeriod(process.getPeriod());
        calculatedSalary.setPeriodCode(process.getPeriod().getCode());
        calculatedSalary.setProcess(process);
        calculatedSalary.setEmployeeId(emp.getEmployeeId());
        calculatedSalary.setEmployeeName(emp.getName());
        calculatedSalary.setOracleCode(emp.getOracleCode());
        calculatedSalary.setDesignation(emp.getDesignation());
        calculatedSalary.setJoiningDate(emp.getJoiningDate());
        Designation designation = reDesignation.findByMisId(emp.getDesignationId()).orElse(null);
        if(designation!=null) calculatedSalary.setDesignationObject(designation);
        reCalculatedSalary.save(calculatedSalary);
    }

    private void updateMarketingNodeInfo(Employees emp,CalculatedSalary calculatedSalary) throws Exception{
        MarketingNode marketingNode = seMHNode.getMainParent(emp.getSalesPointID());
        calculatedSalary.setMarketingNode(marketingNode);
        calculatedSalary.setMarketingNodeName(marketingNode.getNodeName());
        calculatedSalary.setNodeGroup(marketingNode.getGroup().getName());
        reCalculatedSalary.save(calculatedSalary);
    }

    private void updateSalesInfo(Employees emp,CalculatedSalary calculatedSalary) throws Exception{
        Double totalSales = seSales.getTotalSales(emp,calculatedSalary.getPeriod());
        if(totalSales==null || totalSales<=0) throw new Exception("Total Sales is Null or 0.");
        calculatedSalary.setTotalSales(totalSales);
        int salesDays = seSales.getValidSalesDays(emp,calculatedSalary.getPeriod());
        if(salesDays>0) {
            calculatedSalary.setValidSalesDays(salesDays);
            Double avgSales  = new BigDecimal(totalSales/salesDays).
                    setScale(2, RoundingMode.HALF_UP).doubleValue();
            calculatedSalary.setAverageSales(avgSales);
        }
        reCalculatedSalary.save(calculatedSalary);
    }

    private void updateGradeSalaryInfo(Employees emp,CalculatedSalary calculatedSalary) throws Exception{
        if(calculatedSalary.getMarketingNode()==null)
            return;
        if(calculatedSalary.getDesignationObject()==null)
            throw new Exception("Designation not Found");
        if(calculatedSalary.getAverageSales()==null)
            return;

        SalesConfig salesConfig = reSalesConfig.getGradeSalary(
                calculatedSalary.getMarketingNode().getGroup().getId(),
                calculatedSalary.getDesignationObject().getId(),
                calculatedSalary.getAverageSales()).orElse(null);
        if(salesConfig==null) throw new Exception("Grade & Salary not Applicable");

        calculatedSalary.setSalesConfig(salesConfig);
        calculatedSalary.setGrade(salesConfig.getGrade().getName());
        calculatedSalary.setBasicSalary(salesConfig.getBasicSalary());
        reCalculatedSalary.save(calculatedSalary);
    }

}
