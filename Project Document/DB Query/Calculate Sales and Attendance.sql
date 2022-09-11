declare @empId int = 41950148
declare @joiningDate date =  (select JoiningDate from Employees where EmployeeID = @empId)
declare @start varchar(10) = '2020-11-01'
declare @end varchar(10) = '2020-11-30'


select @joiningDate JoiningDate,* from 
(
select SUM(SalesValue) as Total 
from Daily_SR_SKU_Wise_Summary  
where SalesDate between @start and @end
and SRID = @empId
) a,
(
select count(*) Attendance from    
(
SELECT SalesDate 
FROM Daily_SR_SKU_Wise_Summary 
WHERE SalesDate Between (case when @joiningDate > @start then @joiningDate else @start end) AND @end
AND SRID = @empId
AND DATEPART(WEEKDAY, SalesDate) != 6 
AND SalesDate not in ('')
Group By(SalesDate) having SUM(SalesValue) > 0 
) p
) b