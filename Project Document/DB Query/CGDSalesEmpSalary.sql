
-- ************************************ Configuration

declare @INF int = 100000000

-- Salary Grade Table
declare @GradeTable table(GradeID int,Grade varchar(2))
insert into @GradeTable values (1,'A+'),(2,'A'),(3,'B'),(4,'C'),(5,'P')

-- Zone Table  [A = Chittagong,Comilla,Sylhet]  B = [Dhaka,Mymensing,Barisal,Rangpur]
declare @ZoneTable table(ZoneID int, Zone varchar(1))
insert into @ZoneTable values (1,'A'),(2,'B')

-- Division(Node in Market Hierarchie) wise Zone Table
declare @NodeZoneTable table(NodeID int, ZoneID int)
insert into @NodeZoneTable values (382,2),(383,2),(384,2),(387,1),(394,1),(460,1),(490,2),(498,2),(500,2),(501,2),(605,2),(613,2)

-- Designation List for which salary is being calculated
declare	@DesignationTable table(DesignationID int,Designation varchar(40))
insert into @DesignationTable values 
	(1,'SR,SR-CPT'), 
	(20,'SR-BSC'), 
	(8,'CM-CPT'),   
	(21,'CM-BSC')  

-- Grade wise Salary Table
declare @SalaryTable table(GradID int, DesignationID int, Salary int)
insert into @SalaryTable values
	(1,1,16000),(1,20,16000),(1,8,12500),(1,21,12500),(2,1,15000),(2,20,15000),(2,8,12000),(2,21,12000),(3,1,13500),(3,20,13500),(3,8,11000),
	(3,21,11000),(4,1,12000),(4,20,12000),(4,8,10000),(4,21,10000),(5,1,10250),(5,20,10250),(5,8,8000),(5,21,8000)

-- Sales Range Table
declare @SalesRangeTable table(GradeID int, DesignationID int,ZoneID int,MinSales int, MaxSales int)
insert into @SalesRangeTable values
	(1,8,null,20000,@INF),(1,21,null,20000,@INF),(2,8,null,17000,19999),(2,21,null,17000,19999),(3,8,null,14000,16999),(3,21,null,14000,16999),
	(4,8,null,11000,13999),(4,21,null,11000,13999),(5,8,null,7500,10999),(5,21,null,7500,10999),(1,1,1,100000,@INF),(1,1,2,100000,@INF),
	(1,20,1,100000,@INF),(1,20,2,100000,@INF),(2,1,1,75000,99999),(2,1,2,50000,99999),(2,20,1,75000,99999),(2,20,2,50000,99999),
	(3,1,1,45000,74999),(3,1,2,30000,49999),(3,20,1,45000,74999),(3,20,2,30000,49999),(4,1,1,25000,44999),(4,1,2,20000,29999),
	(4,20,1,25000,44999),(4,20,2,20000,29999),(5,1,1,18750,24999),(5,1,2,15000,19999),(5,20,1,18750,24999),(5,20,2,15000,19999)

declare @MasterTable table(MinSales int, MaxSales int,ZoneID int, GradeID int, DesignationID int, Salary int)
insert into @MasterTable  
	select MinSales,MaxSales,ZoneID,R.GradeID,R.DesignationID,Salary from 
	@SalesRangeTable as R,@SalaryTable S
	where S.GradID = R.GradeID and S.DesignationID = R.DesignationID

-- Configuration ************************************


-- ************************************ Parameters

-- start and end date for a month
declare           
	@dateStart varchar(10) = '2020-07-01',
	@dateEnd varchar(10) = '2020-07-31'

declare @offday table(_day date)
insert into @offday values ('2020-07-31')  -- Eid-ul-adha vacation

-- ************************************ Parameters


-- Finding Zone of each employee
declare @empID int
declare @empZoneTable Table(EmpID int, NodeID int,NodeName varchar(50), ZoneID int)
declare iter cursor for select EmployeeID from Employees where DesignationID in (select DesignationID from @DesignationTable) 
open iter 
fetch next from iter into @empID

while @@FETCH_STATUS = 0
begin
	declare @spid int
	set @spid = (select SalesPointID from Employees where EmployeeID = @empID)

	if @spid is null
	begin
		INSERT INTO @empZoneTable VALUES (@empID,null,null,null)
	end
	else
	begin
		declare @nodeID int
		set @nodeID = (select top 1 NodeID from SalesPointMHNodes where SalesPointID = @spid)
		while (select LevelID from MHNode where NodeID = @nodeID) != 2
		begin
			set @nodeID = (select ParentID from MHNode where NodeID = @nodeID)
		end

		declare @zoneId int
		if @nodeID in (select NodeID from @NodeZoneTable where ZoneID = 1)  -- Chittagong, Sylhet , Comilla
			set @zoneId = 1
		else if @nodeID in (select NodeID from @NodeZoneTable where ZoneID = 2)  -- Dhaka,Mymensing,Barisal,Rangpur
			set @zoneId = 2

		INSERT INTO @empZoneTable VALUES (@empID,@nodeID, (select top 1 Name from MHNode where NodeID = @nodeID), @zoneId )
	end
	FETCH NEXT FROM iter INTO @empID
end
close iter
deallocate iter
-- Employee zone is stored in Table variable @empZoneTable

Select FinalTable.* , Salary from 
(
	-- Joining Employee monthly sales with employee zone table
	select SalesInfoTable.EmployeeID, SalesInfoTable.Name,SalesInfoTable.DesignationID, SalesInfoTable.Designation, 
		SalesInfoTable.JoiningDate, SalesInfoTable.TotalSales, SalesInfoTable.PhysicalAttendance, 
		case when PhysicalAttendance>0 then TotalSales/PhysicalAttendance else 0 end as AvgSales,
		ZoneInfoTable.NodeID, ZoneInfoTable.NodeName, ZoneInfoTable.ZoneID, (select Zone from @ZoneTable where ZoneID = ZoneInfoTable.ZoneID) Zone
	from
	(
		select E.EmployeeID,E.Name,E.DesignationID,E.Designation,E.JoiningDate,S.TotalSales
			,( 	select count(*) from  -- counting attendance based on sales amount of a day for the whole month
				(  
					SELECT DSKU.SalesDate
					FROM Daily_SR_SKU_Wise_Summary as DSKU
					WHERE DSKU.SalesDate Between (case when E.JoiningDate > @dateStart then E.JoiningDate else @dateStart end) AND @dateEnd -- Checking Joining Date
						AND DSKU.SRID = E.EmployeeID 
						AND DATEPART(WEEKDAY, DSKU.SalesDate) != 6 -- Friday
						AND DSKU.SalesDate not in (select _day from @offday)   -- Eid Vacation or Other Off Day
					Group By(DSKU.SalesDate) having SUM(DSKU.SalesValue) > 0
				) P
			) as PhysicalAttendance 
		from Employees as E
		left join
		(
			select SRID,SUM(SalesValue) as TotalSales
			from Daily_SR_SKU_Wise_Summary 
			where SalesDate between @dateStart and @dateEnd
			group by SRID
		) as S
		on E.EmployeeID = S.SRID
		where E.DesignationID in (1,20,8,21)
	) as SalesInfoTable 
	join 
	@empZoneTable as ZoneInfoTable 
	on SalesInfoTable.EmployeeID = ZoneInfoTable.EmpID
) FinalTable
left join 
@MasterTable as mTable
on FinalTable.DesignationID = mTable.DesignationID
and (FinalTable.ZoneID = mTable.ZoneID or mTable.ZoneID is null)
and (FinalTable.AvgSales between mTable.MinSales and mTable.MaxSales) 
