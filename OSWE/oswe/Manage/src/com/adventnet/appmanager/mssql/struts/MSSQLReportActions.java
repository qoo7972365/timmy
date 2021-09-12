/*      */ package com.adventnet.appmanager.mssql.struts;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.ExecuteQueryAction;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetMSSQLPerfGraph;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.chart.ChartInfo;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MSSQLReportActions
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward generateMSSQLPerformanceReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   45 */     ActionMessages messages = new ActionMessages();
/*   46 */     ActionErrors errors = new ActionErrors();
/*   47 */     ArrayList performanceData = null;
/*   48 */     String[] columnheadings = null;
/*   49 */     String[] columns = null;
/*   50 */     Connection con = null;
/*   51 */     Statement stmt = null;
/*   52 */     ResultSet results = null;
/*   53 */     ResultSet rs = null;
/*   54 */     ResultSet modetails = null;
/*   55 */     String heading = null;
/*   56 */     String database = null;
/*   57 */     String hrefname = null;
/*   58 */     StringBuilder query = new StringBuilder();
/*   59 */     int[] cellwidth = null;
/*      */     try {
/*   61 */       String resourceid = request.getParameter("resourceid");
/*   62 */       period = request.getParameter("period");
/*   63 */       String fromDate = request.getParameter("fromDate");
/*   64 */       String toDate = request.getParameter("toDate");
/*   65 */       String topqrycnt = request.getParameter("topqrycnt");
/*   66 */       String reportType = request.getParameter("reportType");
/*   67 */       if (reportType.contains("?")) {
/*   68 */         reportType = reportType.substring(0, reportType.indexOf("?"));
/*      */       }
/*   70 */       boolean hasGraph = false;
/*   71 */       database = request.getParameter("database");
/*   72 */       hrefname = request.getParameter("hrefname");
/*   73 */       con = getSqlDBConnection(Integer.parseInt(resourceid));
/*   74 */       stmt = con.createStatement();
/*   75 */       if (MsSQLAction.sqlVersion == null) {
/*   76 */         MsSQLAction.setSQLVersion(resourceid);
/*      */       }
/*   78 */       if (!MsSQLAction.sqlVersion.equals("sql2000"))
/*      */       {
/*   80 */         performanceData = new ArrayList();
/*   81 */         if ((period != null) && (!period.equals("")) && (period.equals("4"))) {
/*   82 */           if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/*   83 */             if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */             {
/*   85 */               Calendar ucal = Calendar.getInstance();
/*   86 */               ucal.setTimeInMillis(0L);
/*   87 */               String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*   88 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*   89 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*   90 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*   91 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*   92 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*   93 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*   94 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*   95 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*   96 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*   97 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*   98 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*   99 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*  100 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  101 */                 query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */               }
/*  103 */               AMLog.debug("MSSQLPerformance Report Query (Custom Period) : " + query.toString());
/*  104 */               results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */             } else {
/*  106 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  107 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  108 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  109 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  110 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  111 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/*  112 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  113 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  114 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  115 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageTimeBlocked DESC");
/*  116 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  117 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/*  118 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  119 */                 query.append("SELECT TOP ").append(topqrycnt).append(" creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' ORDER BY \"Avg Exec Time in ms\" DESC");
/*      */               }
/*  121 */               AMLog.debug("MSSQLPerformance Report Query (Custom Period)SQL : " + query.toString());
/*  122 */               results = stmt.executeQuery(query.toString());
/*      */             }
/*      */           }
/*  125 */           else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/*  127 */             Calendar ucal = Calendar.getInstance();
/*  128 */             ucal.setTimeInMillis(0L);
/*  129 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/*  131 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  132 */               query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*  133 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  134 */               query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*  135 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  136 */               query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*  137 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  138 */               query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*  139 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  140 */               query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*  141 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  142 */               query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */             }
/*  144 */             AMLog.debug("MSSQLPerformance Report Query (Custom Period DB) :" + query.toString());
/*  145 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           } else {
/*  147 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  148 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  149 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  150 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  151 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  152 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/*  153 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  154 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  155 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  156 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/*  157 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  158 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */             }
/*  160 */             AMLog.debug("MSSQLPerformance Report Query (Custom Period DB)SQL :" + query.toString());
/*  161 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */         }
/*  164 */         else if ((period != null) && (!period.equals("")) && (!period.equals("Execution Time"))) {
/*  165 */           long[] timeStamp = null;
/*  166 */           timeStamp = ReportUtilities.getTimeStamp(period);
/*  167 */           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*  168 */           fromDate = sdf.format(new Date(timeStamp[0]));
/*  169 */           toDate = sdf.format(new Date(timeStamp[1]));
/*  170 */           if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/*  171 */             if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */             {
/*  173 */               Calendar ucal = Calendar.getInstance();
/*  174 */               ucal.setTimeInMillis(0L);
/*  175 */               String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */               
/*  177 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  178 */                 if (period.equals("0")) {
/*  179 */                   query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */                 } else {
/*  181 */                   query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */                 }
/*  183 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  184 */                 if (period.equals("0")) {
/*  185 */                   query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */                 } else {
/*  187 */                   query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageCPUused DESC");
/*      */                 }
/*  189 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  190 */                 if (period.equals("0")) {
/*  191 */                   query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageCLRTime DESC");
/*      */                 } else {
/*  193 */                   query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */                 }
/*  195 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE"))
/*      */               {
/*  197 */                 if (period.equals("0")) {
/*  198 */                   query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  200 */                   query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*  202 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  203 */                 if (period.equals("0")) {
/*  204 */                   query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageTimeBlocked DESC");
/*      */                 } else {
/*  206 */                   query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */                 }
/*  208 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR"))
/*      */               {
/*  210 */                 if (period.equals("0")) {
/*  211 */                   query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  213 */                   query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*  215 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ"))
/*      */               {
/*  217 */                 if (period.equals("0")) {
/*  218 */                   query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  220 */                   query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatUnixTime(unixStartTime)).append("AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*      */               }
/*  223 */               AMLog.debug("MSSQLPerformance Report Query period : (" + period + "): " + query.toString());
/*  224 */               results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */             } else {
/*  226 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  227 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  228 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  229 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  230 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  231 */                 query.append("SELECT TOP ").append(topqrycnt).append("  (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount'= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/*  232 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  233 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  234 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  235 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageTimeBlocked DESC");
/*  236 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  237 */                 query.append("SELECT TOP ").append(topqrycnt).append("  (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/*  238 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  239 */                 query.append("SELECT TOP ").append(topqrycnt).append("  creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' ORDER BY \"Avg Exec Time in ms\" DESC");
/*      */               }
/*  241 */               AMLog.debug("MSSQLPerformance Report Query period : (" + period + ") SQL: " + query.toString());
/*  242 */               results = stmt.executeQuery(query.toString());
/*      */             }
/*      */           }
/*  245 */           else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/*  247 */             Calendar ucal = Calendar.getInstance();
/*  248 */             ucal.setTimeInMillis(0L);
/*  249 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/*  251 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  252 */               if (period.equals("0")) {
/*  253 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */               } else {
/*  255 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageIO DESC");
/*      */               }
/*  257 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  258 */               if (period.equals("0")) {
/*  259 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */               } else {
/*  261 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */               }
/*  263 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  264 */               if (period.equals("0")) {
/*  265 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */               } else {
/*  267 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */               }
/*  269 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  270 */               if (period.equals("0")) {
/*  271 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               } else {
/*  273 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               }
/*  275 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  276 */               if (period.equals("0")) {
/*  277 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */               } else {
/*  279 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */               }
/*  281 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  282 */               if (period.equals("0")) {
/*  283 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               } else {
/*  285 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               }
/*      */             }
/*  288 */             AMLog.debug("MSSQLPerformance Report Query period : (" + period + " database " + database + "): " + query.toString());
/*  289 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           } else {
/*  291 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  292 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  293 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  294 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  295 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  296 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/*  297 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  298 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  299 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  300 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/*  301 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  302 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */             }
/*  304 */             AMLog.debug("MSSQLPerformance Report Query period : (" + period + " database " + database + ") SQL: " + query.toString());
/*  305 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */           
/*      */         }
/*  309 */         else if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/*  310 */           if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/*  312 */             Calendar ucal = Calendar.getInstance();
/*  313 */             ucal.setTimeInMillis(0L);
/*  314 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/*  316 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  317 */               query.append(" select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYIO where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYIO", resourceid)).append(" ORDER BY AVGIO DESC");
/*  318 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  319 */               query.append(" select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYCPU where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCPU", resourceid)).append(" ORDER BY AVGCPU DESC");
/*  320 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  321 */               query.append(" select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYCLR where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCLR", resourceid)).append(" ORDER BY AVGCLR DESC");
/*  322 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  323 */               query.append(" select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOE where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOE", resourceid));
/*  324 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  325 */               query.append(" select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOB", resourceid)).append(" ORDER BY AVGTB DESC");
/*  326 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  327 */               query.append(" select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYLPR where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYLPR", resourceid));
/*  328 */             } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  329 */               query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYSRQ where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYSRQ", resourceid)).append(" ORDER BY AVGEXETIME DESC");
/*  330 */             } else if (hrefname.equalsIgnoreCase("MEMORY")) {
/*  331 */               query.append("select RESOURCEID ,SINGLEPAGES,MULTIPAGES,VIRTUALMEMR,VIRTUALMEMC,AWEALLOCATED,SHAREDMEMR,SHAREDMEMC,COMPONENTNAME  from AM_MSSQL_MEMORYUSEDBYCOMP where COLLECTIONTIME =(select max(COLLECTIONTIME) from AM_MSSQL_MEMORYUSEDBYCOMP where resourceid=").append(resourceid).append(") order by SINGLEPAGES desc");
/*  332 */             } else if (hrefname.equals("QUERYBYCMI")) {
/*  333 */               query.append(" select * from AM_MSSQL_QRYBYCMI where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCMI", resourceid));
/*  334 */             } else if (hrefname.equals("WAITSTATS")) {
/*  335 */               query.append(" select WAITTYPE, WAIT, WAITCOUNT, AVGWAIT, SIGNALWAIT from AM_MSSQL_WAITSTATS where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_WAITSTATS", resourceid)).append(" order by WAIT desc");
/*      */             }
/*  337 */             AMLog.debug("MSSQLPerformance Report Query period1 : (" + period + " database " + database + "): " + query.toString());
/*  338 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           }
/*      */           else {
/*  341 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  342 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY AverageIO DESC");
/*  343 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  344 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  345 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  346 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/*  347 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  348 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY Executioncount DESC");
/*  349 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  350 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/*  351 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  352 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/*  353 */             } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  354 */               query.append("SELECT TOP ").append(topqrycnt).append("  creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs ORDER BY \"Avg Exec Time in ms\" DESC");
/*  355 */             } else if (hrefname.equalsIgnoreCase("MEMORY")) {
/*  356 */               query.append("SELECT TOP ").append(topqrycnt).append(" TYPE as COMPONENTNAME,SUM(single_pages_kb) as SINGLEPAGES,SUM(multi_pages_kb)as MULTIPAGES,SUM(virtual_memory_reserved_kb)as VIRTUALMEMR,SUM(virtual_memory_committed_kb)as VIRTUALMEMC,SUM(awe_allocated_kb)as AWEALLOCATED,SUM(shared_memory_reserved_kb)as SHAREDMEMR,SUM(shared_memory_committed_kb)as SHAREDMEMC FROM sys.dm_os_memory_clerks GROUP BY type ORDER BY SUM(single_pages_kb) DESC");
/*  357 */               if (MsSQLAction.sqlVersion.equals("sql2012")) {
/*  358 */                 query.append("SELECT TOP ").append(topqrycnt).append("  type as COMPONENTNAME,SUM(pages_kb) as SPA Mem,SUM(pages_kb)as MPA Mem,SUM(virtual_memory_reserved_kb)as VMR,SUM(virtual_memory_committed_kb)as VMC,SUM(awe_allocated_kb)as AWE,SUM(shared_memory_reserved_kb)as SMR,SUM(shared_memory_committed_kb)as SMC FROM sys.dm_os_memory_clerks GROUP BY type ORDER BY SUM(pages_kb) DESC");
/*      */               }
/*  360 */             } else if (hrefname.equals("QUERYBYCMI")) {
/*  361 */               query.append("SELECT  TOP ").append(topqrycnt).append(" TOTALCOST  = ROUND(avg_total_user_cost * avg_user_impact * (user_seeks + user_scans),0) , avg_user_impact as AVGUSERIMPT, TBNAME = statement  , EQUALITYUSG = equality_columns , INEQUALITYUSG = inequality_columns , INCLUDECOLMS = included_columns FROM  sys.dm_db_missing_index_groups g INNER JOIN    sys.dm_db_missing_index_group_stats s  ON s.group_handle = g.index_group_handle  INNER JOIN    sys.dm_db_missing_index_details d   ON d.index_handle = g.index_handle ORDER BY TotalCost DESC");
/*  362 */             } else if (hrefname.equals("WAITSTATS")) {
/*  363 */               query.append("SELECT top ").append(topqrycnt).append(" wait_type as WAITTYPE, wait_time_ms AS WAIT, waiting_tasks_count AS WAITCOUNT,wait_time_ms / waiting_tasks_count AS AVGWAIT,signal_wait_time_ms AS SIGNALWAIT").append(" FROM sys.dm_os_wait_stats WHERE wait_type NOT IN ('BROKER_EVENTHANDLER','BROKER_RECEIVE_WAITFOR','BROKER_TASK_STOP','BROKER_TO_FLUSH','BROKER_TRANSMITTER','CHECKPOINT_QUEUE',").append("'CHKPT','CLR_AUTO_EVENT','CLR_MANUAL_EVENT','CLR_SEMAPHORE','DBMIRROR_DBM_EVENT','DBMIRROR_EVENTS_QUEUE','DBMIRROR_WORKER_QUEUE','DBMIRRORING_CMD','DIRTY_PAGE_POLL','DISPATCHER_QUEUE_SEMAPHORE',").append("'EXECSYNC','FSAGENT','FT_IFTS_SCHEDULER_IDLE_WAIT','FT_IFTSHC_MUTEX','HADR_CLUSAPI_CALL','HADR_FILESTREAM_IOMGR_IOCOMPLETION','HADR_LOGCAPTURE_WAIT','HADR_NOTIFICATION_DEQUEUE','HADR_TIMER_TASK',").append("'HADR_WORK_QUEUE','KSOURCE_WAKEUP','LAZYWRITER_SLEEP','LOGMGR_QUEUE','ONDEMAND_TASK_QUEUE','PWAIT_ALL_COMPONENTS_INITIALIZED','QDS_PERSIST_TASK_MAIN_LOOP_SLEEP','SLEEP_MASTERDBREADY',").append("'QDS_CLEANUP_STALE_QUERIES_TASK_MAIN_LOOP_SLEEP','REQUEST_FOR_DEADLOCK_SEARCH','RESOURCE_QUEUE','SERVER_IDLE_CHECK','SLEEP_BPOOL_FLUSH','SLEEP_DBSTARTUP','SLEEP_DCOMSTARTUP',").append("'SLEEP_MASTERMDREADY','SLEEP_MASTERUPGRADED','SLEEP_MSDBSTARTUP','SLEEP_SYSTEMTASK','SLEEP_TASK','SLEEP_TEMPDBSTARTUP','SNI_HTTP_ACCEPT','SP_SERVER_DIAGNOSTICS_SLEEP','SQLTRACE_BUFFER_FLUSH',").append("'SQLTRACE_INCREMENTAL_FLUSH_SLEEP','SQLTRACE_WAIT_ENTRIES','WAIT_FOR_RESULTS','WAITFOR','WAITFOR_TASKSHUTDOWN','WAIT_XTP_HOST_WAIT','WAIT_XTP_OFFLINE_CKPT_NEW_LOG','WAIT_XTP_CKPT_CLOSE','XE_DISPATCHER_JOIN',").append("'XE_DISPATCHER_WAIT','XE_TIMER_EVENT') AND waiting_tasks_count > 0 ORDER BY wait_time_ms DESC");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  373 */             AMLog.debug("MSSQLPerformance Report Query1 period : (" + period + " database " + database + ") SQL: " + query.toString());
/*  374 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */         }
/*  377 */         else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */         {
/*  379 */           Calendar ucal = Calendar.getInstance();
/*  380 */           ucal.setTimeInMillis(0L);
/*  381 */           String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */           
/*  383 */           if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  384 */             query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYIO", resourceid)).append(" ORDER BY AverageIO DESC");
/*  385 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  386 */             query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCPU", resourceid)).append(" ORDER BY AverageCPUused DESC");
/*  387 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  388 */             query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCLR", resourceid)).append(" ORDER BY AverageCLRTime DESC");
/*  389 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  390 */             query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOE", resourceid));
/*  391 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  392 */             query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOB", resourceid)).append(" ORDER BY AverageTimeBlocked DESC");
/*  393 */           } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  394 */             query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatUnixTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYLPR", resourceid));
/*      */           }
/*  396 */           AMLog.debug("MSSQLPerformance Report Query2 period : (" + period + " database " + database + "): " + query.toString());
/*  397 */           results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */         } else {
/*  399 */           if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  400 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  401 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  402 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  403 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  404 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/*  405 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  406 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  407 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  408 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/*  409 */           } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  410 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */           }
/*  412 */           AMLog.debug("MSSQLPerformance Report Query2 period : (" + period + " database " + database + ") SQL: " + query.toString());
/*  413 */           results = stmt.executeQuery(query.toString());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  418 */         if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  419 */           hasGraph = true;
/*  420 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybyio.tableheading");
/*  421 */           request.setAttribute("image_heading", "Average IO Time");
/*  422 */           request.setAttribute("image1_heading", "Total IO Time");
/*  423 */           int i = 0;
/*  424 */           performanceData = new ArrayList();
/*  425 */           while (results.next()) {
/*  426 */             Properties byio = new Properties();
/*  427 */             byio.setProperty("Q.No", ++i + "");
/*  428 */             byio.setProperty("AVGIO", results.getString("AverageIO"));
/*  429 */             byio.setProperty("TOTALIO", results.getString("TotalIO"));
/*  430 */             byio.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  431 */             if (results.getString("DATABASENAME") != null) {
/*  432 */               byio.setProperty("DATABASENAME", results.getString("DATABASENAME"));
/*      */             } else {
/*  434 */               byio.setProperty("DATABASENAME", "");
/*      */             }
/*  436 */             byio.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  437 */             byio.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  438 */             performanceData.add(byio);
/*      */           }
/*  440 */           AMConnectionPool.closeStatement(results);
/*  441 */           columns = new String[] { "Q.No", "AVGIO", "TOTALIO", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  442 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssqldetails.qno"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.averageio"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.totalio"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  451 */           cellwidth = new int[] { 25, 50, 50, 50, 300, 50, 100 };
/*  452 */         } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  453 */           hasGraph = true;
/*  454 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybycpu.tableheading");
/*  455 */           request.setAttribute("image_heading", "Average CPU Time");
/*  456 */           request.setAttribute("image1_heading", "Total CPU Time");
/*  457 */           int i = 0;
/*  458 */           performanceData = new ArrayList();
/*  459 */           while (results.next()) {
/*  460 */             Properties bycpu = new Properties();
/*  461 */             bycpu.setProperty("Q.No", ++i + "");
/*  462 */             bycpu.setProperty("AVGCPU", results.getString("AverageCPUused"));
/*  463 */             bycpu.setProperty("TOTALCPU", results.getString("TotalCPUused"));
/*  464 */             bycpu.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  465 */             if (results.getString("DatabaseName") != null) {
/*  466 */               bycpu.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             }
/*  468 */             bycpu.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  469 */             bycpu.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  470 */             performanceData.add(bycpu);
/*      */           }
/*  472 */           AMConnectionPool.closeStatement(results);
/*  473 */           columns = new String[] { "Q.No", "AVGCPU", "TOTALCPU", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  474 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssqldetails.qno"), FormatUtil.getString("am.webclient.mssql.costlyquerybycpu.averagecpu"), FormatUtil.getString("am.webclient.mssql.costlyquerybycpu.totalcpu"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybyio.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  483 */           cellwidth = new int[] { 25, 50, 50, 50, 300, 50, 100 };
/*  484 */         } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  485 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybyclr.tableheading");
/*  486 */           performanceData = new ArrayList();
/*  487 */           while (results.next()) {
/*  488 */             Properties byclr = new Properties();
/*  489 */             byclr.setProperty("AVGCLR", results.getString("AverageCLRTime"));
/*  490 */             byclr.setProperty("TOTALCLR", results.getString("TotalCLRTime"));
/*  491 */             byclr.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  492 */             if (results.getString("DatabaseName") != null) {
/*  493 */               byclr.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             } else {
/*  495 */               byclr.setProperty("DATABASENAME", "");
/*      */             }
/*  497 */             byclr.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  498 */             byclr.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  499 */             performanceData.add(byclr);
/*      */           }
/*  501 */           columns = new String[] { "AVGCLR", "TOTALCLR", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  502 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.costlyquerybyclr.averageclr"), FormatUtil.getString("am.webclient.mssql.costlyquerybyclr.totalclr"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  510 */           cellwidth = new int[] { 50, 50, 50, 300, 50, 100 };
/*  511 */         } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  512 */           hasGraph = true;
/*  513 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.tableheading");
/*  514 */           request.setAttribute("image_heading", "Most Frequently Executed");
/*  515 */           int i = 0;
/*  516 */           performanceData = new ArrayList();
/*  517 */           while (results.next()) {
/*  518 */             Properties bymoe = new Properties();
/*  519 */             bymoe.setProperty("Q.No", ++i + "");
/*  520 */             bymoe.setProperty("EXCCOUNT", String.valueOf(results.getInt("Executioncount")));
/*  521 */             bymoe.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  522 */             if (results.getString("DatabaseName") != null) {
/*  523 */               bymoe.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             } else {
/*  525 */               bymoe.setProperty("DATABASENAME", "");
/*      */             }
/*  527 */             bymoe.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  528 */             bymoe.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  529 */             performanceData.add(bymoe);
/*      */           }
/*  531 */           AMConnectionPool.closeStatement(results);
/*  532 */           columns = new String[] { "Q.No", "EXCCOUNT", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  533 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssqldetails.qno"), FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.exccount"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybymoe.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  541 */           cellwidth = new int[] { 25, 50, 50, 300, 50, 100 };
/*  542 */         } else if (hrefname.equals("QUERYBYMOB")) {
/*  543 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybymob.tableheading");
/*  544 */           performanceData = new ArrayList();
/*  545 */           while (results.next()) {
/*  546 */             Properties bymob = new Properties();
/*  547 */             bymob.setProperty("AVGTB", results.getString("AverageTimeBlocked"));
/*  548 */             bymob.setProperty("TOTALTB", results.getString("TotalTimeBlocked"));
/*  549 */             bymob.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  550 */             if (results.getString("DatabaseName") != null) {
/*  551 */               bymob.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             } else {
/*  553 */               bymob.setProperty("DATABASENAME", "");
/*      */             }
/*  555 */             bymob.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  556 */             bymob.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  557 */             performanceData.add(bymob);
/*      */           }
/*  559 */           AMConnectionPool.closeStatement(results);
/*  560 */           columns = new String[] { "AVGTB", "TOTALTB", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  561 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.costlyquerybymob.averagetb"), FormatUtil.getString("am.webclient.mssql.costlyquerybymob.totaltb"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybyclr.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybymob.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybymob.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  569 */           cellwidth = new int[] { 50, 50, 50, 300, 50, 100 };
/*      */         }
/*  571 */         else if (hrefname.equals("QUERYBYLPR")) {
/*  572 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.tableheading");
/*  573 */           performanceData = new ArrayList();
/*  574 */           while (results.next()) {
/*  575 */             Properties bylpr = new Properties();
/*  576 */             bylpr.setProperty("PLANUSAGE", results.getString("Planusage"));
/*  577 */             bylpr.setProperty("CACHEOBJTYPE", results.getString("CACHEOBJTYPE"));
/*  578 */             bylpr.setProperty("INDQUERY", results.getString("IndividualQuery"));
/*  579 */             if (results.getString("DatabaseName") != null) {
/*  580 */               bylpr.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             } else {
/*  582 */               bylpr.setProperty("DATABASENAME", "");
/*      */             }
/*  584 */             bylpr.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  585 */             bylpr.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/*  586 */             performanceData.add(bylpr);
/*      */           }
/*  588 */           AMConnectionPool.closeStatement(results);
/*  589 */           columns = new String[] { "PLANUSAGE", "CACHEOBJTYPE", "AVGEXETIME", "INDQUERY", "DATABASENAME", "LASTEXETIME" };
/*  590 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.planusage"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.cacheobjtype"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.databasename"), FormatUtil.getString("am.webclient.mssql.costlyquerybylpr.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  598 */           cellwidth = new int[] { 50, 50, 50, 300, 50, 100 };
/*  599 */         } else if (hrefname.equals("QUERYBYSRQ")) {
/*  600 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.tableheading");
/*  601 */           performanceData = new ArrayList();
/*  602 */           while (results.next()) {
/*  603 */             Properties bysrq = new Properties();
/*  604 */             bysrq.setProperty("AVGEXETIME", results.getString("Avg Exec Time in ms"));
/*  605 */             bysrq.setProperty("MAXEXETIME", results.getString("MaxExecTime in ms"));
/*  606 */             bysrq.setProperty("MINEXETIME", results.getString("MinExecTime in ms"));
/*  607 */             bysrq.setProperty("NOOFEXECS", results.getString("NumberOfExecs"));
/*  608 */             bysrq.setProperty("INDQUERY", results.getString("query_text"));
/*  609 */             bysrq.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/*  610 */             performanceData.add(bysrq);
/*      */           }
/*  612 */           AMConnectionPool.closeStatement(results);
/*  613 */           columns = new String[] { "AVGEXETIME", "MAXEXETIME", "MINEXETIME", "NOOFEXECS", "INDQUERY", "LASTEXETIME" };
/*  614 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.avgexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.Maxexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.Minexetime"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.excutions"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.query"), FormatUtil.getString("am.webclient.mssql.costlyquerybysrq.lastexetime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  622 */           cellwidth = new int[] { 50, 50, 50, 50, 50, 50 };
/*  623 */         } else if (hrefname.equals("MEMORY")) {
/*  624 */           heading = FormatUtil.getString("am.webclient.mssql.memoryusage.tableheading");
/*  625 */           performanceData = new ArrayList();
/*  626 */           while (results.next()) {
/*  627 */             Properties mem = new Properties();
/*  628 */             mem.setProperty("TYPE", results.getString("COMPONENTNAME"));
/*  629 */             mem.setProperty("SINGLEPAGES", String.valueOf(results.getInt("SINGLEPAGES")));
/*  630 */             mem.setProperty("MULTIPAGES", String.valueOf(results.getInt("MULTIPAGES")));
/*  631 */             mem.setProperty("VIRTUALMEMR", String.valueOf(results.getInt("VIRTUALMEMR")));
/*  632 */             mem.setProperty("VIRTUALMEMC", String.valueOf(results.getInt("VIRTUALMEMC")));
/*  633 */             mem.setProperty("AWEALLOCATED", String.valueOf(results.getInt("AWEALLOCATED")));
/*  634 */             mem.setProperty("SHAREDMEMR", String.valueOf(results.getInt("SHAREDMEMR")));
/*  635 */             mem.setProperty("SHAREDMEMC", String.valueOf(results.getInt("SHAREDMEMC")));
/*  636 */             mem.setProperty("DBConID", String.valueOf(resourceid));
/*      */             
/*  638 */             performanceData.add(mem);
/*      */           }
/*  640 */           AMConnectionPool.closeStatement(results);
/*  641 */           columns = new String[] { "TYPE", "SINGLEPAGES", "MULTIPAGES", "VIRTUALMEMR", "VIRTUALMEMC", "AWEALLOCATED", "SHAREDMEMR", "SHAREDMEMC" };
/*  642 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.memoryusage.componenttype"), FormatUtil.getString("am.webclient.mssql.memoryusage.singlepages"), FormatUtil.getString("am.webclient.mssql.memoryusage.multipages"), FormatUtil.getString("am.webclient.mssql.memoryusage.virtualmemoryr"), FormatUtil.getString("am.webclient.mssql.memoryusage.virtualmemoryc"), FormatUtil.getString("am.webclient.mssql.memoryusage.aweallocated"), FormatUtil.getString("am.webclient.mssql.memoryusage.sharedmemoryr"), FormatUtil.getString("am.webclient.mssql.memoryusage.sharedmemoryc") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  652 */           cellwidth = new int[] { 50, 50, 50, 50, 50, 50, 50, 100 };
/*  653 */         } else if (hrefname.equalsIgnoreCase("QUERYBYCMI")) {
/*  654 */           heading = FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.tableheading");
/*  655 */           performanceData = new ArrayList();
/*  656 */           while (results.next()) {
/*  657 */             Properties bycmi = new Properties();
/*  658 */             bycmi.setProperty("TOTALCOST", String.valueOf(results.getInt("TOTALCOST")));
/*  659 */             bycmi.setProperty("AVGUSERIMPT", results.getString("AVGUSERIMPT"));
/*  660 */             bycmi.setProperty("TBNAME", results.getString("TBNAME"));
/*  661 */             if (results.getString("EQUALITYUSG") != null) {
/*  662 */               bycmi.setProperty("EQUALITYUSG", results.getString("EQUALITYUSG"));
/*      */             } else {
/*  664 */               bycmi.setProperty("EQUALITYUSG", "");
/*      */             }
/*  666 */             if (results.getString("INEQUALITYUSG") != null) {
/*  667 */               bycmi.setProperty("INEQUALITYUSG", results.getString("INEQUALITYUSG"));
/*      */             } else {
/*  669 */               bycmi.setProperty("INEQUALITYUSG", "");
/*      */             }
/*  671 */             if (results.getString("INCLUDECOLMS") != null) {
/*  672 */               bycmi.setProperty("INCLUDECOLMS", results.getString("INCLUDECOLMS"));
/*      */             } else {
/*  674 */               bycmi.setProperty("INCLUDECOLMS", "");
/*      */             }
/*  676 */             performanceData.add(bycmi);
/*      */           }
/*  678 */           AMConnectionPool.closeStatement(results);
/*  679 */           columns = new String[] { "TOTALCOST", "AVGUSERIMPT", "TBNAME", "EQUALITYUSG", "INEQUALITYUSG", "INCLUDECOLMS" };
/*  680 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.totalcost"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.avguserimpact"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.tablename"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.equalityusage"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.inequalityusage"), FormatUtil.getString("am.webclient.mssql.costlyquerybycmi.includecolumns") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  688 */           cellwidth = new int[] { 50, 50, 50, 50, 50, 50 };
/*  689 */         } else if (hrefname.equalsIgnoreCase("WAITSTATS")) {
/*  690 */           heading = FormatUtil.getString("am.webclient.mssql.performance.waitstats.topwaits");
/*  691 */           performanceData = new ArrayList();
/*  692 */           while (results.next()) {
/*  693 */             Properties waitstats = new Properties();
/*  694 */             waitstats.put("WAITTYPE", results.getString("WAITTYPE"));
/*  695 */             waitstats.put("WAIT", String.valueOf(results.getLong("WAIT")));
/*  696 */             waitstats.put("WAITCOUNT", String.valueOf(results.getLong("WAITCOUNT")));
/*  697 */             waitstats.put("AVGWAIT", String.valueOf(results.getLong("AVGWAIT")));
/*  698 */             waitstats.put("SIGNALWAIT", String.valueOf(results.getLong("SIGNALWAIT")));
/*  699 */             performanceData.add(waitstats);
/*      */           }
/*  701 */           AMConnectionPool.closeStatement(results);
/*  702 */           columns = new String[] { "WAITTYPE", "WAIT", "WAITCOUNT", "AVGWAIT", "SIGNALWAIT" };
/*  703 */           columnheadings = new String[] { FormatUtil.getString("am.webclient.mssql.performance.waitstats.waittype"), FormatUtil.getString("am.webclient.mssql.performance.waitstats.waitingcount"), FormatUtil.getString("am.webclient.mssql.performance.waitstats.waittime"), FormatUtil.getString("am.webclient.mssql.performance.waitstats.avgwaittime"), FormatUtil.getString("am.webclient.mssql.performance.waitstats.signaltime") };
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  710 */           cellwidth = new int[] { 100, 50, 50, 50, 50 };
/*      */         }
/*  712 */         request.setAttribute("data", performanceData);
/*      */       } else {
/*  714 */         request.setAttribute("data", new ArrayList());
/*      */       }
/*  716 */       request.setAttribute("resourceid", resourceid);
/*  717 */       request.setAttribute("fromDate", fromDate);
/*  718 */       request.setAttribute("toDate", toDate);
/*  719 */       request.setAttribute("columnheadings", columnheadings);
/*  720 */       request.setAttribute("columns", columns);
/*  721 */       request.setAttribute("selectedPeriod", period);
/*  722 */       request.setAttribute("selectedDb", database);
/*  723 */       request.setAttribute("database", database);
/*  724 */       request.setAttribute("topqrycnt", topqrycnt);
/*  725 */       request.setAttribute("hrefname", hrefname);
/*  726 */       request.setAttribute("reportType", reportType);
/*  727 */       request.setAttribute("cellwidth", cellwidth);
/*      */       
/*  729 */       if ((period != null) && (period.equals("4"))) {
/*  730 */         heading = heading + " (" + request.getParameter("fromDate") + " - " + request.getParameter("toDate") + ")";
/*  731 */       } else if ((period != null) && (period.equals("0"))) {
/*  732 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.today.text") + ")";
/*  733 */       } else if ((period != null) && (period.equals("3"))) {
/*  734 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.yesterday.text") + ")";
/*  735 */       } else if ((period != null) && (period.equals("6"))) {
/*  736 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.thisweek.text") + ")";
/*  737 */       } else if ((period != null) && (period.equals("1"))) {
/*  738 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.7days.text") + ")";
/*  739 */       } else if ((period != null) && (period.equals("12"))) {
/*  740 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.lastweek.text") + ")";
/*  741 */       } else if ((period != null) && (period.equals("7"))) {
/*  742 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.thismonth.text") + ")";
/*  743 */       } else if ((period != null) && (period.equals("2"))) {
/*  744 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.30days.text") + ")";
/*  745 */       } else if ((period != null) && (period.equals("11"))) {
/*  746 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.lastmonth.text") + ")";
/*  747 */       } else if ((period != null) && (period.equals("9"))) {
/*  748 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.thisquarter.text") + ")";
/*  749 */       } else if ((period != null) && (period.equals("8"))) {
/*  750 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.thisyear.text") + ")";
/*  751 */       } else if ((period != null) && (period.equals("5"))) {
/*  752 */         heading = heading + " (" + FormatUtil.getString("am.webclient.historydata.period.last1year.text") + ")";
/*      */       }
/*      */       
/*  755 */       if ((database != null) && (!database.equals("Database")) && (!database.equals("All"))) {
/*  756 */         heading = heading + " (Database : " + database + ")";
/*      */       }
/*      */       
/*  759 */       String resourceName = null;
/*  760 */       modetails = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  761 */       if (modetails.next()) {
/*  762 */         resourceName = modetails.getString("DISPLAYNAME");
/*      */       }
/*  764 */       AMConnectionPool.closeStatement(modetails);
/*      */       
/*  766 */       if (!heading.equals(""))
/*  767 */         request.setAttribute("heading", resourceName + " - " + heading);
/*      */       GetMSSQLPerfGraph mssqlperfGraph_avgTime;
/*  769 */       if ((reportType != null) && ((reportType.equals("pdf")) || (reportType.equals("excel")))) {
/*  770 */         if (hasGraph) {
/*  771 */           mssqlperfGraph_avgTime = new GetMSSQLPerfGraph();
/*      */           try {
/*  773 */             if (period == null) {
/*  774 */               period = "Execution Time";
/*      */             }
/*  776 */             if ((database == null) || ("null".equals(database)) || ("Database".equals(database))) {
/*  777 */               database = "Database";
/*      */             }
/*  779 */             if (((fromDate != null) && (toDate != null)) || (!period.equals("Execution Time")) || (!database.equals("All")) || (topqrycnt != null)) {
/*  780 */               mssqlperfGraph_avgTime.setParam(resourceid, hrefname, database, period, fromDate, toDate, topqrycnt);
/*      */             } else {
/*  782 */               mssqlperfGraph_avgTime.setParam(resourceid, hrefname);
/*      */             }
/*      */           } catch (Exception e) {
/*  785 */             e.printStackTrace();
/*      */           }
/*  787 */           ChartInfo cinfo_avgTime = new ChartInfo();
/*  788 */           cinfo_avgTime.setDataSet(mssqlperfGraph_avgTime);
/*  789 */           cinfo_avgTime.setHeight("500");
/*  790 */           cinfo_avgTime.setWidth("500");
/*  791 */           cinfo_avgTime.setXaxisLabel("Q.No");
/*  792 */           cinfo_avgTime.setYaxisLabel("Avg Time(ms.)");
/*  793 */           String image_AvgTime = cinfo_avgTime.get3DbarChartAsJPG();
/*  794 */           request.setAttribute("image", image_AvgTime);
/*  795 */           AMLog.debug("image for AvgTime of " + hrefname + " : " + image_AvgTime);
/*      */           
/*  797 */           GetMSSQLPerfGraph mssqlperfGraph_totalTime = new GetMSSQLPerfGraph();
/*      */           try {
/*  799 */             if (period == null) {
/*  800 */               period = "Execution Time";
/*      */             }
/*  802 */             if ((database == null) || ("null".equals(database)) || ("Database".equals(database))) {
/*  803 */               database = "All";
/*      */             }
/*  805 */             if (((fromDate != null) && (toDate != null)) || (!period.equals("Execution Time")) || (!database.equals("All")) || (topqrycnt != null)) {
/*  806 */               mssqlperfGraph_totalTime.setParam(resourceid, hrefname + "T", database, period, fromDate, toDate, topqrycnt);
/*      */             } else {
/*  808 */               mssqlperfGraph_totalTime.setParam(resourceid, hrefname + "T");
/*      */             }
/*      */           } catch (Exception e) {
/*  811 */             e.printStackTrace();
/*      */           }
/*  813 */           ChartInfo cinfo_totalTime = new ChartInfo();
/*  814 */           cinfo_totalTime.setDataSet(mssqlperfGraph_totalTime);
/*  815 */           cinfo_totalTime.setHeight("500");
/*  816 */           cinfo_totalTime.setWidth("500");
/*  817 */           cinfo_totalTime.setXaxisLabel("Q.No");
/*  818 */           cinfo_totalTime.setYaxisLabel("Total Time(ms.)");
/*  819 */           String image_TotTime = cinfo_totalTime.get3DbarChartAsJPG();
/*  820 */           request.setAttribute("image1", image_TotTime);
/*      */         }
/*  822 */         request.setAttribute("report-type-template", "report.mssqlperformancereport");
/*  823 */         request.setAttribute("sp-report-type", reportType);
/*  824 */         return mapping.findForward("report.mssqlperformancereport.pdf");
/*      */       }
/*  826 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/*  827 */       request.setAttribute(hrefname, performanceData);
/*  828 */       return mapping.findForward("report.mssqlperformancereport");
/*      */     } catch (Exception exp) {
/*      */       String period;
/*  831 */       exp.printStackTrace();
/*  832 */       AMLog.fatal("MSSQLReportActions :  Exception in generating MS-SQL Performance Report", exp);
/*  833 */       request.setAttribute("heading", "0");
/*  834 */       request.setAttribute("strTime", "0");
/*  835 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/*  836 */       errors.add("org.apache.struts.action.ERROR", new ActionMessage(exp.getMessage()));
/*  837 */       saveMessages(request, messages);
/*  838 */       saveErrors(request, errors);
/*  839 */       return mapping.findForward("report.message");
/*      */     } finally {
/*  841 */       closeResultSet(results);
/*  842 */       closeResultSet(rs);
/*  843 */       closeResultSet(modetails);
/*  844 */       if (stmt != null) {
/*  845 */         stmt.close();
/*      */       }
/*  847 */       if (con != null) {
/*  848 */         con.close();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private String formatUnixTime(String unixStartTime) {
/*  854 */     if (DBQueryUtil.isMssql())
/*  855 */       return "DATEADD(ss,LASTEXETIME/1000,'" + unixStartTime + "')";
/*  856 */     if (DBQueryUtil.isMysql())
/*  857 */       return "FROM_UNIXTIME(LASTEXETIME/1000)";
/*  858 */     if (DBQueryUtil.isPgsql()) {
/*  859 */       return "to_timestamp(LASTEXETIME/1000)";
/*      */     }
/*  861 */     return null;
/*      */   }
/*      */   
/*      */   private Connection getSqlDBConnection(int resid) {
/*  865 */     Connection conn = null;
/*      */     try {
/*  867 */       conn = ExecuteQueryAction.getDbConnection(resid, null);
/*      */     } catch (Exception ex) {
/*  869 */       ex.printStackTrace();
/*      */     }
/*  871 */     return conn;
/*      */   }
/*      */   
/*      */   private void closeResultSet(ResultSet rs) {
/*      */     try {
/*  876 */       if (rs != null) {
/*  877 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     } catch (Exception exc) {
/*  880 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward generateMSSQLDatabasesReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  885 */     ActionMessages messages = new ActionMessages();
/*  886 */     ActionErrors errors = new ActionErrors();
/*  887 */     String resourceid = request.getParameter("resourceid");
/*  888 */     String heading = FormatUtil.getString("am.reporttab.mssql.reports.mssqldatabasedetailsreport.text");
/*  889 */     String selectedPeriod = request.getParameter("selectedPeriod");
/*  890 */     if ("true".equals(request.getParameter("isschedule"))) {
/*  891 */       selectedPeriod = request.getParameter("period");
/*      */     }
/*  893 */     String[] columns = { "DISPLAYNAME", "DATAFILESUSEDSIZE", "DATAFILESFREESIZE", "DATAFILESSIZE", "MAXSIZE", "LOGFILEUSEDSIZE", "LOGFILESFREESIZE", "LOGFILESSIZE", "LOG_MAXSIZE", "TOTALSIZE", "DBSTATUS", "STATUS1", "DBMODE", "CREATIONDATE" };
/*  894 */     long[] timestamp = null;
/*  895 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  898 */       if ((selectedPeriod != null) && (!"null".equals(selectedPeriod))) {
/*  899 */         timestamp = ReportUtilities.getTimeStamp(selectedPeriod);
/*      */       }
/*  901 */       String emailPDF = null;
/*  902 */       if (request.getParameter("getpdf") != null) {
/*  903 */         emailPDF = request.getParameter("getpdf");
/*      */       }
/*  905 */       h = new ArrayList();
/*  906 */       rs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resourceid);
/*  907 */       if (rs.next()) {
/*  908 */         request.setAttribute("PARENT_DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*      */       }
/*  910 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  912 */       DecimalFormat df = new DecimalFormat("#,##0.00");
/*  913 */       DecimalFormat df1 = new DecimalFormat("#,###");
/*  914 */       StringBuilder dataquery = new StringBuilder();
/*  915 */       if (timestamp != null) {
/*  916 */         dataquery.append("select M1.RESOURCEID as DBID,M1.DISPLAYNAME, COALESCE(AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE,'-1') as DFDATAFILESSIZE, COALESCE(AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE,'-1')as LOGFILESSIZE  ,COALESCE( AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE,'-1')as LOGFILEUSEDSIZE,  AM_MSSQL_DATABASEDETAILS.STATUS as DBSTATUS, DATEADD(ss,CREATIONDATE/1000,'1970-01-01') AS CREATIONDATE, AM_MSSQL_DATABASEDETAILS.STATUS1, AM_MSSQL_DATABASEDETAILS.DBMODE, AM_MSSQL_DATAFILESIZEDETAILS.*   , AM_MSSQL_MIRRORINGMONITOR_DETAILS.MIRRORINGSTATE, AM_MSSQL_MIRRORINGMONITOR_DETAILS.ROLE from AM_ManagedObject M1,AM_ManagedObject M2,AM_MSSQL_DATABASEDETAILS ,AM_DATABASES   left outer join  AM_MSSQL_DATAFILESIZEDETAILS  on AM_MSSQL_DATAFILESIZEDETAILS.COLLECTIONTIME in (SELECT max(COLLECTIONTIME) from AM_MSSQL_DATAFILESIZEDETAILS where AM_MSSQL_DATAFILESIZEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID) and  AM_MSSQL_DATAFILESIZEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID  left outer join  AM_MSSQL_MIRRORINGMONITOR_DETAILS on AM_MSSQL_MIRRORINGMONITOR_DETAILS.COLLECTIONTIME in (SELECT max(COLLECTIONTIME) from AM_MSSQL_MIRRORINGMONITOR_DETAILS where AM_MSSQL_MIRRORINGMONITOR_DETAILS.RESOURCEID=AM_MSSQL_DATAFILESIZEDETAILS.RESOURCEID) where AM_MSSQL_DATABASEDETAILS.CREATIONDATE>" + timestamp[0] + " AND AM_MSSQL_DATABASEDETAILS.CREATIONDATE<" + timestamp[1] + " AND AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME in " + "(SELECT max(COLLECTIONTIME) from AM_MSSQL_DATABASEDETAILS where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID) " + "and M1.TYPE='Database' and M1.RESOURCEID=AM_DATABASES.DATABASEID and AM_DATABASES.PARENTID=M2.RESOURCEID and ").append("M2.RESOURCEID=" + resourceid).append(" order by M1.DISPLAYNAME, M2.DISPLAYNAME");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  933 */         dataquery.append("select M1.RESOURCEID as DBID,M1.DISPLAYNAME, COALESCE(AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE,'-1') as DFDATAFILESSIZE, COALESCE(AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE,'-1')as LOGFILESSIZE  ,COALESCE( AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE,'-1')as LOGFILEUSEDSIZE,  AM_MSSQL_DATABASEDETAILS.STATUS as DBSTATUS, DATEADD(ss,CREATIONDATE/1000,'1970-01-01') AS CREATIONDATE, AM_MSSQL_DATABASEDETAILS.STATUS1, AM_MSSQL_DATABASEDETAILS.DBMODE,AM_MSSQL_DATAFILESIZEDETAILS.*   , AM_MSSQL_MIRRORINGMONITOR_DETAILS.MIRRORINGSTATE, AM_MSSQL_MIRRORINGMONITOR_DETAILS.ROLE from AM_ManagedObject M1,AM_ManagedObject M2,AM_MSSQL_DATABASEDETAILS ,AM_DATABASES   left outer join  AM_MSSQL_DATAFILESIZEDETAILS  on AM_MSSQL_DATAFILESIZEDETAILS.COLLECTIONTIME in (SELECT max(COLLECTIONTIME) from AM_MSSQL_DATAFILESIZEDETAILS where AM_MSSQL_DATAFILESIZEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID) and  AM_MSSQL_DATAFILESIZEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID  left outer join  AM_MSSQL_MIRRORINGMONITOR_DETAILS on AM_MSSQL_MIRRORINGMONITOR_DETAILS.RESOURCEID=AM_DATABASES.DATABASEID  and AM_MSSQL_MIRRORINGMONITOR_DETAILS.COLLECTIONTIME in (SELECT max(COLLECTIONTIME) from AM_MSSQL_MIRRORINGMONITOR_DETAILS where AM_MSSQL_MIRRORINGMONITOR_DETAILS.RESOURCEID=AM_DATABASES.DATABASEID) where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME in (SELECT max(COLLECTIONTIME) from AM_MSSQL_DATABASEDETAILS where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID) and M1.TYPE='Database' and M1.RESOURCEID=AM_DATABASES.DATABASEID and AM_DATABASES.PARENTID=M2.RESOURCEID and ").append("M2.RESOURCEID=" + resourceid).append(" order by M1.DISPLAYNAME, M2.DISPLAYNAME");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  950 */       rs = AMConnectionPool.executeQueryStmt(dataquery.toString());
/*  951 */       Properties p; while (rs.next()) {
/*  952 */         p = new Properties();
/*  953 */         if (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID"))) {
/*  954 */           p.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME") + " Unmanaged");
/*      */         } else {
/*  956 */           p.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*      */         }
/*      */         
/*  959 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  960 */           p.setProperty("DATAFILESUSEDSIZE", " ");
/*  961 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (rs.getString("DATAFILESUSEDSIZE").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  962 */           p.setProperty("DATAFILESUSEDSIZE", " ");
/*      */         } else {
/*  964 */           p.setProperty("DATAFILESUSEDSIZE", df.format(Double.parseDouble(rs.getString("DATAFILESUSEDSIZE"))));
/*      */         }
/*      */         
/*  967 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  968 */           p.setProperty("DATAFILESFREESIZE", " ");
/*  969 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (rs.getString("DATAFILESFREESIZE").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  970 */           p.setProperty("DATAFILESFREESIZE", " ");
/*      */         } else {
/*  972 */           p.setProperty("DATAFILESFREESIZE", df.format(Double.parseDouble(rs.getString("DATAFILESFREESIZE"))));
/*      */         }
/*      */         
/*  975 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  976 */           p.setProperty("DATAFILESSIZE", " ");
/*  977 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (rs.getString("DFDATAFILESSIZE").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  978 */           p.setProperty("DATAFILESSIZE", " ");
/*      */         } else {
/*  980 */           p.setProperty("DATAFILESSIZE", df.format(Double.parseDouble(rs.getString("DFDATAFILESSIZE"))));
/*      */         }
/*      */         
/*  983 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  984 */           p.setProperty("MAXSIZE", " ");
/*  985 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (rs.getString("MAXSIZE").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  986 */           p.setProperty("MAXSIZE", " ");
/*      */         }
/*  988 */         else if (Double.parseDouble(rs.getString("MAXSIZE")) == -1.0D) {
/*  989 */           p.setProperty("MAXSIZE", "Infinite");
/*      */         } else {
/*  991 */           p.setProperty("MAXSIZE", df1.format(Double.parseDouble(rs.getString("MAXSIZE"))));
/*      */         }
/*      */         
/*      */ 
/*  995 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  996 */           p.setProperty("LOGFILEUSEDSIZE", " ");
/*  997 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("LOGFILEUSEDSIZE")) == -1.0D) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/*  998 */           p.setProperty("LOGFILEUSEDSIZE", " ");
/*      */         } else {
/* 1000 */           p.setProperty("LOGFILEUSEDSIZE", df.format(Double.parseDouble(rs.getString("LOGFILEUSEDSIZE"))));
/*      */         }
/*      */         
/* 1003 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1004 */           p.setProperty("LOGFILESFREESIZE", " ");
/* 1005 */         } else if ((rs.getString("DFDATAFILESSIZE").equalsIgnoreCase("null")) || (rs.getString("DBSTATUS").equalsIgnoreCase("null")) || (rs.getString("STATUS1").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1006 */           p.setProperty("LOGFILESFREESIZE", " ");
/* 1007 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("LOGFILEUSEDSIZE")) == -1.0D) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1008 */           p.setProperty("LOGFILESFREESIZE", "");
/*      */         } else {
/* 1010 */           p.setProperty("LOGFILESFREESIZE", df.format(Double.parseDouble(rs.getString("LOGFILESSIZE")) - Double.parseDouble(rs.getString("LOGFILEUSEDSIZE"))));
/*      */         }
/*      */         
/* 1013 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1014 */           p.setProperty("LOGFILESSIZE", " ");
/* 1015 */         } else if ((rs.getString("DFDATAFILESSIZE").equalsIgnoreCase("null")) || (rs.getString("DBSTATUS").equalsIgnoreCase("null")) || (rs.getString("STATUS1").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1016 */           p.setProperty("LOGFILESSIZE", " ");
/* 1017 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("LOGFILESSIZE")) == -1.0D) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1018 */           p.setProperty("LOGFILESSIZE", " ");
/*      */         } else {
/* 1020 */           p.setProperty("LOGFILESSIZE", df.format(Double.parseDouble(rs.getString("LOGFILESSIZE"))));
/*      */         }
/*      */         
/* 1023 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1024 */           p.setProperty("LOG_MAXSIZE", " ");
/* 1025 */         } else if ((rs.getString("DFDATAFILESSIZE").equalsIgnoreCase("null")) || (rs.getString("DBSTATUS").equalsIgnoreCase("null")) || (rs.getString("STATUS1").equalsIgnoreCase("null")) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1026 */           p.setProperty("LOG_MAXSIZE", " ");
/* 1027 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (rs.getString("MAXSIZE") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1028 */           p.setProperty("LOG_MAXSIZE", " ");
/*      */         }
/* 1030 */         else if (Double.parseDouble(rs.getString("LOG_MAXSIZE")) == -1.0D) {
/* 1031 */           p.setProperty("LOG_MAXSIZE", "Infinite");
/*      */         } else {
/* 1033 */           p.setProperty("LOG_MAXSIZE", df1.format(Double.parseDouble(rs.getString("LOG_MAXSIZE"))));
/*      */         }
/*      */         
/*      */ 
/* 1037 */         if ((rs.getString("DFDATAFILESSIZE") == null) || (rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1038 */           p.setProperty("TOTALSIZE", " ");
/* 1039 */         } else if ((rs.getString("DFDATAFILESSIZE").equalsIgnoreCase("null")) || (rs.getString("DBSTATUS").equalsIgnoreCase("null")) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1040 */           p.setProperty("TOTALSIZE", " ");
/* 1041 */         } else if ((rs.getString("DBSTATUS").trim().equals("OFFLINE")) || (rs.getString("STATUS1").trim().equals("INACTIVE")) || (Double.parseDouble(rs.getString("DFDATAFILESSIZE")) == -1.0D) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1042 */           p.setProperty("TOTALSIZE", " ");
/*      */         } else {
/* 1044 */           p.setProperty("TOTALSIZE", df.format(Double.parseDouble(rs.getString("DFDATAFILESSIZE")) + Double.parseDouble(rs.getString("LOGFILESSIZE"))));
/*      */         }
/* 1046 */         if ((rs.getString("MIRRORINGSTATE") != null) && (!rs.getString("MIRRORINGSTATE").equals("")) && (!rs.getString("MIRRORINGSTATE").toLowerCase().equals("null"))) {
/* 1047 */           p.setProperty("DBSTATUS", rs.getString("ROLE") + ", " + rs.getString("MIRRORINGSTATE"));
/* 1048 */           p.setProperty("STATUS1", "MIRRORING");
/*      */         } else {
/* 1050 */           if ((rs.getString("DBSTATUS") == null) || (rs.getString("STATUS1") == null) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1051 */             p.setProperty("DBSTATUS", " ");
/* 1052 */           } else if (((rs.getString("STATUS1").trim().equals("INACTIVE")) && (rs.getString("DBSTATUS").trim().equals("ONLINE"))) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1053 */             if (((request.getParameter("reportType") != null) && ("pdf".equals(request.getParameter("reportType")))) || ((emailPDF != null) && (emailPDF.equals("true"))) || ("true".equals(request.getParameter("isschedule")))) {
/* 1054 */               p.setProperty("DBSTATUS", rs.getString("DBSTATUS"));
/*      */             } else {
/* 1056 */               p.setProperty("DBSTATUS", rs.getString("DBSTATUS") + " - " + rs.getString("STATUS1"));
/*      */             }
/* 1058 */           } else if (((rs.getString("STATUS1").trim().equals("INACTIVE")) && (rs.getString("DBSTATUS").trim().equals("OFFLINE"))) || (DataCollectionControllerUtil.isUnManaged(rs.getString("DBID")))) {
/* 1059 */             p.setProperty("DBSTATUS", rs.getString("DBSTATUS"));
/*      */           } else {
/* 1061 */             p.setProperty("DBSTATUS", rs.getString("DBSTATUS"));
/*      */           }
/* 1063 */           p.setProperty("STATUS1", rs.getString("STATUS1"));
/*      */         }
/* 1065 */         p.setProperty("DBMODE", rs.getString("DBMODE"));
/* 1066 */         p.setProperty("CREATIONDATE", rs.getString("CREATIONDATE"));
/* 1067 */         h.add(p);
/*      */       }
/* 1069 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1071 */       request.setAttribute("resourceid", resourceid);
/* 1072 */       saveErrors(request, errors);
/*      */       
/* 1074 */       request.setAttribute("selectedPeriod", selectedPeriod);
/* 1075 */       if (((request.getParameter("reportType") != null) && ("pdf".equals(request.getParameter("reportType")))) || ((emailPDF != null) && (emailPDF.equals("true"))) || ("true".equals(request.getParameter("isschedule")))) {
/* 1076 */         request.setAttribute("columns", columns);
/* 1077 */         request.setAttribute("heading", heading);
/* 1078 */         request.setAttribute("data", h);
/* 1079 */         request.setAttribute("report-type-template", "report.mssqldatabasesreport");
/* 1080 */         request.setAttribute("sp-report-type", "pdf");
/* 1081 */         return mapping.findForward("report.mssqldatabasesreport.pdf");
/*      */       }
/* 1083 */       request.setAttribute("dbdetails", h);
/* 1084 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/* 1085 */       return mapping.findForward("report.mssqldatabasesreport");
/*      */     } catch (Exception exp) {
/*      */       List h;
/* 1088 */       exp.printStackTrace();
/* 1089 */       AMLog.fatal("Reports :  Exception in generating MS-SQL Databases Report", exp);
/* 1090 */       request.setAttribute("heading", "0");
/* 1091 */       request.setAttribute("strTime", "0");
/* 1092 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 1093 */       saveMessages(request, messages);
/* 1094 */       return mapping.findForward("report.mssqldatabasesreport");
/*      */     } finally {
/* 1096 */       if (rs != null) {
/* 1097 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward generateMirroringHistoryReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1103 */     DecimalFormat df = new DecimalFormat("#,###.##");
/* 1104 */     ReportForm controls = (ReportForm)form;
/* 1105 */     ActionMessages messages = new ActionMessages();
/* 1106 */     ActionErrors errors = new ActionErrors();
/* 1107 */     String resourceid = request.getParameter("resourceid");
/* 1108 */     String parent_resourceid = request.getParameter("sqlresid");
/* 1109 */     String period = request.getParameter("period");
/* 1110 */     String emailPDF = null;
/* 1111 */     long startTime = 0L;
/* 1112 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1115 */       long val = 6L;
/*      */       try {
/* 1117 */         val = Long.parseLong(period);
/*      */       } catch (Exception e) {
/* 1119 */         e.printStackTrace();
/*      */       }
/* 1121 */       startTime = System.currentTimeMillis() - val * 3600L * 1000L;
/*      */       
/* 1123 */       ArrayList columns = new ArrayList();
/* 1124 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.role"));
/* 1125 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.state"));
/* 1126 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.safetylevel"));
/* 1127 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.witnessconn"));
/* 1128 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.unsentlog") + " (" + FormatUtil.getString("KB") + ")");
/* 1129 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.oldunsenttran") + " " + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timeformat.unit"));
/* 1130 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timetosendlog") + " " + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timeformat.unit"));
/* 1131 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.currentsendrate") + " (" + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.unit.kbpersec") + ")");
/* 1132 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.currentrateofnewtransactions") + " (" + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.unit.kbpersec") + ")");
/* 1133 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.unrestoredlog") + " (" + FormatUtil.getString("KB") + ")");
/* 1134 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timetorestorelog") + " " + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timeformat.unit"));
/* 1135 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.currentrestorerate") + " (" + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.unit.kbpersec") + ")");
/* 1136 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.mirrorcommitoverhead") + " " + FormatUtil.getString("ms"));
/* 1137 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timetosendandrestorelog") + " " + FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.timeformat.unit"));
/* 1138 */       columns.add(FormatUtil.getString("am.webclient.mssql.dbdetails.mirror.witnessaddress"));
/* 1139 */       columns.add(FormatUtil.getString("Time"));
/*      */       
/* 1141 */       String heading = FormatUtil.getString("am.webclient.mssqldetails.mirrordetails.historyreport") + " of " + request.getParameter("resourcename");
/* 1142 */       ArrayList historyList = new ArrayList();
/* 1143 */       String query = "select ROLE, MIRRORINGSTATE, MIRRORINGSAFETYLEVEL, WITNESSCONN, UNSENTLOG, OLDUNSENTTRANS,TIMETOSENDLOG, CURRENTSENDRATE, CURRENTRATEOFNEWTRANSACTIONS, UNRESTOREDLOG,TIMETORESTORELOG, CURRENTRESTORERATE, MIRRORCOMMITOVERHEAD, TIMETOSENDANDRESTORELOG, WITNESSADDRESS, COLLECTIONTIME from AM_MSSQL_MIRRORINGMONITOR_DETAILS where RESOURCEID=" + resourceid + " and collectiontime>=" + startTime + " order by COLLECTIONTIME desc";
/* 1144 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1145 */       int day = 0;
/* 1146 */       int hr = 0;
/* 1147 */       int rem = 0;
/* 1148 */       int mn = 0;
/* 1149 */       int sec = 0;
/* 1150 */       DecimalFormat hourFormat = new DecimalFormat("00");
/* 1151 */       long collTime; while (rs.next()) {
/* 1152 */         ArrayList al = new ArrayList();
/* 1153 */         al.add(rs.getString("ROLE"));
/* 1154 */         al.add(rs.getString("MIRRORINGSTATE"));
/* 1155 */         al.add(rs.getString("MIRRORINGSAFETYLEVEL"));
/* 1156 */         al.add(rs.getString("WITNESSCONN"));
/* 1157 */         al.add(df.format(Double.parseDouble(rs.getString("UNSENTLOG"))));
/* 1158 */         day = 0;
/* 1159 */         hr = rs.getInt("OLDUNSENTTRANS") / 3600;
/* 1160 */         if (hr >= 24) {
/* 1161 */           day = hr / 24;
/* 1162 */           hr %= 24;
/*      */         }
/* 1164 */         rem = rs.getInt("OLDUNSENTTRANS") % 3600;
/* 1165 */         mn = rem / 60;
/* 1166 */         sec = rem % 60;
/* 1167 */         al.add((day > 0 ? hourFormat.format(day) + ":" : "00:") + hourFormat.format(hr) + ":" + hourFormat.format(mn) + ":" + hourFormat.format(sec));
/* 1168 */         if (rs.getFloat("TIMETOSENDLOG") != -1.0F) {
/* 1169 */           day = 0;
/* 1170 */           hr = rs.getInt("TIMETOSENDLOG") / 3600;
/* 1171 */           if (hr >= 24) {
/* 1172 */             day = hr / 24;
/* 1173 */             hr %= 24;
/*      */           }
/* 1175 */           rem = rs.getInt("TIMETOSENDLOG") % 3600;
/* 1176 */           mn = rem / 60;
/* 1177 */           sec = rem % 60;
/* 1178 */           al.add((day > 0 ? hourFormat.format(day) + ":" : "00:") + hourFormat.format(hr) + ":" + hourFormat.format(mn) + ":" + hourFormat.format(sec));
/*      */         } else {
/* 1180 */           al.add("-1");
/*      */         }
/* 1182 */         al.add(df.format(Double.parseDouble(rs.getString("CURRENTSENDRATE"))));
/* 1183 */         al.add(df.format(Double.parseDouble(rs.getString("CURRENTRATEOFNEWTRANSACTIONS"))));
/* 1184 */         al.add(df.format(Double.parseDouble(rs.getString("UNRESTOREDLOG"))));
/* 1185 */         if (rs.getFloat("TIMETORESTORELOG") != -1.0F) {
/* 1186 */           day = 0;
/* 1187 */           hr = rs.getInt("TIMETORESTORELOG") / 3600;
/* 1188 */           if (hr >= 24) {
/* 1189 */             day = hr / 24;
/* 1190 */             hr %= 24;
/*      */           }
/* 1192 */           rem = rs.getInt("TIMETORESTORELOG") % 3600;
/* 1193 */           mn = rem / 60;
/* 1194 */           sec = rem % 60;
/* 1195 */           al.add((day > 0 ? hourFormat.format(day) + ":" : "00:") + hourFormat.format(hr) + ":" + hourFormat.format(mn) + ":" + hourFormat.format(sec));
/*      */         } else {
/* 1197 */           al.add("-1");
/*      */         }
/* 1199 */         al.add(df.format(Double.parseDouble(rs.getString("CURRENTRESTORERATE"))));
/* 1200 */         al.add(df.format(Double.parseDouble(rs.getString("MIRRORCOMMITOVERHEAD"))));
/* 1201 */         if (rs.getFloat("TIMETOSENDANDRESTORELOG") != -1.0F) {
/* 1202 */           day = 0;
/* 1203 */           hr = rs.getInt("TIMETOSENDANDRESTORELOG") / 3600;
/* 1204 */           if (hr >= 24) {
/* 1205 */             day = hr / 24;
/* 1206 */             hr %= 24;
/*      */           }
/* 1208 */           rem = rs.getInt("TIMETOSENDANDRESTORELOG") % 3600;
/* 1209 */           mn = rem / 60;
/* 1210 */           sec = rem % 60;
/* 1211 */           al.add((day > 0 ? hourFormat.format(day) + ":" : "00:") + hourFormat.format(hr) + ":" + hourFormat.format(mn) + ":" + hourFormat.format(sec));
/*      */         } else {
/* 1213 */           al.add("-");
/*      */         }
/* 1215 */         al.add(rs.getString("WITNESSADDRESS"));
/* 1216 */         collTime = Long.valueOf(rs.getString("COLLECTIONTIME")).longValue();
/* 1217 */         Calendar cal = new GregorianCalendar();
/* 1218 */         cal.setTime(new Date(collTime));
/* 1219 */         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
/* 1220 */         al.add(sdf.format(cal.getTime()));
/* 1221 */         historyList.add(al);
/*      */       }
/* 1223 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1225 */       request.setAttribute("period", period);
/* 1226 */       request.setAttribute("reportType", controls.getReporttype());
/* 1227 */       request.setAttribute("columns", columns);
/* 1228 */       request.setAttribute("resourceid", resourceid);
/* 1229 */       request.setAttribute("sqlresid", parent_resourceid);
/* 1230 */       request.setAttribute("resourcename", request.getParameter("resourcename"));
/* 1231 */       String parent_displayName = DBUtil.getDisplaynameforResourceID(parent_resourceid);
/* 1232 */       request.setAttribute("PARENT_DISPLAYNAME", parent_displayName);
/* 1233 */       heading = heading + "(" + parent_displayName + ")";
/* 1234 */       saveErrors(request, errors);
/*      */       
/* 1236 */       if (request.getParameter("getpdf") != null) {
/* 1237 */         emailPDF = request.getParameter("getpdf");
/*      */       }
/* 1239 */       if (("pdf".equals(request.getParameter("reportType"))) || ("true".equals(emailPDF)) || ("true".equals(request.getParameter("isschedule")))) {
/* 1240 */         request.setAttribute("heading", heading);
/* 1241 */         request.setAttribute("data", historyList);
/* 1242 */         request.setAttribute("report-type-template", "report.mirrorhistoryreport");
/* 1243 */         request.setAttribute("sp-report-type", "pdf");
/* 1244 */         return mapping.findForward("report.mirrorhistoryreport.pdf");
/*      */       }
/* 1246 */       request.setAttribute("data", historyList);
/* 1247 */       request.setAttribute("report-type-template", "report.mirrorhistoryreport");
/* 1248 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/* 1249 */       return mapping.findForward("report.mirrorhistoryreport");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1253 */       exp.printStackTrace();
/* 1254 */       AMLog.fatal("Reports :  Exception in generating MSSQL-Server Mirroring history report", exp);
/* 1255 */       request.setAttribute("heading", "0");
/* 1256 */       request.setAttribute("strTime", "0");
/* 1257 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 1258 */       saveMessages(request, messages);
/* 1259 */       return mapping.findForward("report.message");
/*      */     } finally {
/* 1261 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward generateBackUpReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 1266 */     ActionMessages messages = new ActionMessages();
/* 1267 */     Statement stmt = null;
/* 1268 */     Connection con = null;
/* 1269 */     ResultSet rs = null;
/* 1270 */     ArrayList<ArrayList<String>> data = new ArrayList();
/*      */     try
/*      */     {
/* 1273 */       String resourceID = request.getParameter("resourceID");
/* 1274 */       reportName = request.getParameter("reportName");
/* 1275 */       String noOfDays = request.getParameter("noOfDays");
/* 1276 */       String reportType = request.getParameter("reportType");
/*      */       
/* 1278 */       ArrayList<String> columns = new ArrayList();
/* 1279 */       columns.add("Database Name");
/* 1280 */       columns.add("Creation Date");
/*      */       
/* 1282 */       String heading = null;
/* 1283 */       String query = null;
/* 1284 */       if (reportName.equals("neverbackedup")) {
/* 1285 */         heading = FormatUtil.getString("am.reporttab.backup.reportname.neverbackedup");
/* 1286 */         query = "select sysdb.name as database_name, crdate as creation_date from master..sysdatabases as sysdb left join msdb.dbo.backupset on msdb.dbo.backupset.database_name=sysdb.name where msdb.dbo.backupset.backup_set_id is null and sysdb.name<>'tempdb' order by database_name";
/* 1287 */       } else if (reportName.equals("notbackeduppastndays")) {
/* 1288 */         heading = FormatUtil.getString("am.reporttab.backup.reportname.notbackeduppastndays") + " " + noOfDays + " " + FormatUtil.getString("am.webclient.global.mvavg.days.text");
/* 1289 */         query = "SELECT msdb.dbo.backupset.database_name, database_creation_date as creation_date, MAX(msdb.dbo.backupset.backup_finish_date) AS last_db_backup_date," + "DATEDIFF(dd, MAX(msdb.dbo.backupset.backup_finish_date), GETDATE()) AS [backup_age] FROM msdb.dbo.backupset " + "WHERE msdb.dbo.backupset.type='D' and (msdb.dbo.backupset.database_name in (select name from master..sysdatabases)) GROUP BY msdb.dbo.backupset.database_name, database_creation_date HAVING (MAX(msdb.dbo.backupset.backup_finish_date) < DATEADD(dd, -" + noOfDays + ", GETDATE()))" + " UNION (select sysdb.name, crdate as creation_date, NULL, NULL from master..sysdatabases as sysdb left join msdb.dbo.backupset on msdb.dbo.backupset.database_name=sysdb.name where msdb.dbo.backupset.backup_set_id is null and sysdb.name<>'tempdb')" + " ORDER BY backup_age desc, database_name";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1296 */         columns.add("Last Backup Date");
/* 1297 */         columns.add("Backup Age (Days)");
/*      */       }
/* 1299 */       String resourceName = DBUtil.getDisplaynameforResourceID(resourceID);
/* 1300 */       if (!heading.equals("")) {
/* 1301 */         request.setAttribute("heading", resourceName + " - " + heading);
/*      */       }
/* 1303 */       request.setAttribute("columns", columns);
/* 1304 */       request.setAttribute("PARENT_DISPLAYNAME", resourceName.replaceAll(" ", "_"));
/* 1305 */       request.setAttribute("backupReport", reportName);
/* 1306 */       request.setAttribute("report-type-template", "report.backup");
/*      */       
/* 1308 */       con = getSqlDBConnection(Integer.parseInt(resourceID));
/* 1309 */       stmt = con.createStatement();
/* 1310 */       rs = stmt.executeQuery(query);
/* 1311 */       ArrayList<String> arr; while (rs.next()) {
/* 1312 */         arr = new ArrayList();
/* 1313 */         arr.add(rs.getString("database_name"));
/* 1314 */         arr.add(rs.getString("creation_date"));
/* 1315 */         if (reportName.equals("notbackeduppastndays")) {
/* 1316 */           if (rs.getString("last_db_backup_date") != null) {
/* 1317 */             arr.add(rs.getString("last_db_backup_date"));
/* 1318 */             arr.add(rs.getString("backup_age"));
/*      */           } else {
/* 1320 */             arr.add("-");
/* 1321 */             arr.add("-");
/*      */           }
/*      */         }
/* 1324 */         data.add(arr);
/*      */       }
/* 1326 */       request.setAttribute("data", data);
/* 1327 */       request.setAttribute("errMsg", FormatUtil.getString("No_Data_Available"));
/* 1328 */       if ("pdf".equals(reportType)) {
/* 1329 */         return mapping.findForward("report.backup.pdf");
/*      */       }
/* 1331 */       return mapping.findForward("report.backup");
/*      */     }
/*      */     catch (NullPointerException e) {
/* 1334 */       request.setAttribute("errMsg", FormatUtil.getString("am.unabletoconnect.mssql"));
/* 1335 */       return mapping.findForward("report.backup.pdf");
/*      */     } catch (Exception exp) { String reportName;
/* 1337 */       AMLog.fatal("Reports :  Exception ", exp);
/* 1338 */       request.setAttribute("heading", "0");
/* 1339 */       request.setAttribute("strTime", "0");
/* 1340 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 1341 */       saveMessages(request, messages);
/* 1342 */       return mapping.findForward("report.message");
/*      */     } finally {
/* 1344 */       if (rs != null) {
/*      */         try {
/* 1346 */           rs.close();
/*      */         } catch (SQLException e) {
/* 1348 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1351 */       if (stmt != null) {
/*      */         try {
/* 1353 */           stmt.close();
/*      */         } catch (SQLException e) {
/* 1355 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1358 */       if (con != null) {
/*      */         try {
/* 1360 */           con.close();
/*      */         } catch (SQLException e) {
/* 1362 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward generateUnusedObjReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 1369 */     ActionMessages messages = new ActionMessages();
/* 1370 */     Statement stmt = null;
/* 1371 */     Connection con = null;
/* 1372 */     ResultSet rs = null;
/* 1373 */     ArrayList<ArrayList<String>> data = new ArrayList();
/*      */     try
/*      */     {
/* 1376 */       String resourceID = request.getParameter("resourceID");
/* 1377 */       reportName = request.getParameter("reportName");
/* 1378 */       String reportType = request.getParameter("reportType");
/* 1379 */       String dbName = request.getParameter("database");
/*      */       
/* 1381 */       ArrayList<String> columns = new ArrayList();
/*      */       
/* 1383 */       String heading = null;
/* 1384 */       String query = null;
/* 1385 */       if (reportName.equals("database")) {
/* 1386 */         heading = FormatUtil.getString("am.reporttab.unused.reportname.database");
/* 1387 */         query = "SELECT t.name AS 'object_name', create_date FROM sys.dm_db_index_usage_stats i RIGHT OUTER JOIN sys.databases t ON (t.database_id = i.database_id) GROUP BY i.database_id, t.name, create_date HAVING SUM(i.user_seeks + i.user_scans + i.user_lookups + i.user_updates) is NULL and t.name not in ('master','model','tempdb') order by create_date";
/* 1388 */         columns.add("am.webclient.common.databasename");
/* 1389 */         columns.add("am.webclient.mssqldetails.creationdate");
/* 1390 */       } else if (reportName.equals("tables")) {
/* 1391 */         heading = FormatUtil.getString("am.reporttab.unused.reportname.tables");
/* 1392 */         query = "SELECT t.name AS 'object_name', create_date, modify_date FROM sys.dm_db_index_usage_stats i RIGHT OUTER JOIN sys.tables t ON (t.object_id = i.object_id) GROUP BY i.object_id, t.name, create_date, modify_date HAVING SUM(i.user_seeks + i.user_scans + i.user_lookups) is NULL order by create_date";
/* 1393 */         columns.add("Table Name");
/* 1394 */         columns.add("am.webclient.mssqldetails.creationdate");
/* 1395 */         columns.add("am.webclient.monitorgroupdetails.lastmodified.text");
/*      */       }
/* 1397 */       String resourceName = DBUtil.getDisplaynameforResourceID(resourceID);
/* 1398 */       if (!heading.equals("")) {
/* 1399 */         request.setAttribute("heading", resourceName + " - " + heading);
/*      */       }
/* 1401 */       request.setAttribute("columns", columns);
/* 1402 */       request.setAttribute("PARENT_DISPLAYNAME", resourceName.replaceAll(" ", "_"));
/* 1403 */       request.setAttribute("unusedReport", reportName);
/* 1404 */       request.setAttribute("report-type-template", "report.unusedobj");
/* 1405 */       request.setAttribute("errMsg", FormatUtil.getString("No_Data_Available"));
/*      */       
/* 1407 */       con = getSqlDBConnection(Integer.parseInt(resourceID));
/* 1408 */       stmt = con.createStatement();
/*      */       try {
/* 1410 */         if (reportName.equals("tables")) {
/* 1411 */           stmt.executeUpdate("use [" + dbName + "]");
/*      */         }
/* 1413 */         rs = stmt.executeQuery(query);
/* 1414 */         while (rs.next()) {
/* 1415 */           ArrayList<String> arr = new ArrayList();
/* 1416 */           arr.add(rs.getString("object_name"));
/* 1417 */           arr.add(rs.getString("create_date"));
/* 1418 */           if (reportName.equals("tables")) {
/* 1419 */             arr.add(rs.getString("modify_date"));
/*      */           }
/* 1421 */           data.add(arr);
/*      */         }
/*      */       } catch (SQLException e) {
/* 1424 */         e.printStackTrace();
/* 1425 */         request.setAttribute("errMsg", FormatUtil.getString("am.webclient.support.threaddump.corruptedDB"));
/*      */       }
/*      */       
/* 1428 */       request.setAttribute("data", data);
/* 1429 */       if ("pdf".equals(reportType)) {
/* 1430 */         return mapping.findForward("report.unusedobj.pdf");
/*      */       }
/* 1432 */       return mapping.findForward("report.unusedobj");
/*      */     }
/*      */     catch (NullPointerException e) {
/* 1435 */       request.setAttribute("errMsg", FormatUtil.getString("am.unabletoconnect.mssql"));
/* 1436 */       return mapping.findForward("report.unusedobj.pdf");
/*      */     } catch (Exception exp) { String reportName;
/* 1438 */       AMLog.fatal("Reports :  Exception ", exp);
/* 1439 */       request.setAttribute("heading", "0");
/* 1440 */       request.setAttribute("strTime", "0");
/* 1441 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("report.failed.exception", exp.toString()));
/* 1442 */       saveMessages(request, messages);
/* 1443 */       return mapping.findForward("report.message");
/*      */     } finally {
/* 1445 */       if (rs != null) {
/*      */         try {
/* 1447 */           rs.close();
/*      */         } catch (SQLException e) {
/* 1449 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1452 */       if (stmt != null) {
/*      */         try {
/* 1454 */           stmt.close();
/*      */         } catch (SQLException e) {
/* 1456 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1459 */       if (con != null) {
/*      */         try {
/* 1461 */           con.close();
/*      */         } catch (SQLException e) {
/* 1463 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static long getMaxTime(String tableName, String resId) {
/* 1470 */     long maxtime = 0L;
/* 1471 */     ResultSet rs = null;
/* 1472 */     String query = "select max(COLLECTIONTIME) from " + tableName + " where RESOURCEID=" + resId;
/* 1473 */     if ("AM_MSSQL_MEMORYUSEDBYCOMP".equalsIgnoreCase(tableName)) {
/* 1474 */       query = "select max(COLLECTIONTIME) from AM_MSSQL_MEMORYUSEDBYCOMP join AM_DATABASES on AM_DATABASES.DATABASEID=AM_MSSQL_MEMORYUSEDBYCOMP.RESOURCEID and AM_DATABASES.PARENTID=" + resId;
/*      */     }
/*      */     try {
/* 1477 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1478 */       if (rs.next()) {
/* 1479 */         maxtime = rs.getLong(1);
/*      */       }
/*      */     } catch (Exception e) {
/* 1482 */       e.printStackTrace();
/*      */     } finally {
/* 1484 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1486 */     return maxtime;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\mssql\struts\MSSQLReportActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */