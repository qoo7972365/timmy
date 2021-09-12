/*     */ package com.adventnet.appmanager.server.wlogic.bean;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.ExecuteQueryAction;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.awolf.data.DatasetProduceException;
/*     */ import com.adventnet.awolf.data.DatasetProducer;
/*     */ import com.adventnet.awolf.data.UrlProducer;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.jfree.data.category.DefaultCategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetMSSQLPerfGraph
/*     */   implements DatasetProducer, UrlProducer, Serializable
/*     */ {
/*  28 */   private String pieChartType = null;
/*  29 */   private String mgName = null;
/*  30 */   private String resID = null;
/*  31 */   private String type = null;
/*  32 */   private String period = "0";
/*  33 */   private String startdate = null;
/*  34 */   private String enddate = null;
/*  35 */   private String database = null;
/*  36 */   private String topqrycnt = null;
/*  37 */   private long starttime = 0L;
/*  38 */   private long endtime = 0L;
/*  39 */   private long collectionTime = 0L;
/*  40 */   static Log log = LogFactory.getLog("WebClient");
/*  41 */   private Hashtable urls = null;
/*  42 */   private String downIdsList = null;
/*  43 */   private boolean isData = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public void setParam(String resid, String type)
/*     */   {
/*  49 */     this.resID = resid;
/*  50 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String type, String period) {
/*  54 */     this.resID = resid;
/*  55 */     this.type = type;
/*  56 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String type, long starttime, long endtime) {
/*  60 */     this.resID = resid;
/*  61 */     this.type = type;
/*  62 */     this.starttime = starttime;
/*  63 */     this.endtime = endtime;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String type, long starttime, long endtime, String period)
/*     */   {
/*  68 */     this.resID = resid;
/*  69 */     this.type = type;
/*  70 */     this.starttime = starttime;
/*  71 */     this.endtime = endtime;
/*  72 */     this.period = period;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String type, String period, String startdate, String enddate)
/*     */   {
/*  77 */     this.resID = resid;
/*  78 */     this.type = type;
/*  79 */     this.period = period;
/*  80 */     this.startdate = startdate;
/*  81 */     this.enddate = enddate;
/*     */   }
/*     */   
/*     */   public void setParam(String resid, String type, String database, String period, String startdate, String enddate, String topqrycnt) {
/*  85 */     this.resID = resid;
/*  86 */     this.type = type;
/*  87 */     this.database = database;
/*  88 */     this.period = period;
/*  89 */     this.startdate = startdate;
/*  90 */     this.enddate = enddate;
/*  91 */     this.topqrycnt = topqrycnt;
/*     */   }
/*     */   
/*     */   public void setParam(String tempType, String chartType, String groupName, long startTime, long endTime)
/*     */   {
/*  96 */     this.type = tempType;
/*  97 */     this.pieChartType = chartType;
/*  98 */     this.mgName = groupName;
/*  99 */     this.starttime = startTime;
/* 100 */     this.endtime = endTime;
/*     */   }
/*     */   
/*     */   public long getLastDataCollectedTime() {
/* 104 */     return this.collectionTime;
/*     */   }
/*     */   
/*     */   public String getDownIdsList() {
/* 108 */     return this.downIdsList;
/*     */   }
/*     */   
/*     */   public boolean checkData() {
/* 112 */     return this.isData;
/*     */   }
/*     */   
/*     */   public Object produceDataset(Map params) throws DatasetProduceException {
/* 116 */     AMLog.debug(this.type + " -> parameters -> startdate : " + this.startdate + " , enddate : " + this.enddate + " , database : " + this.database + " , topqrycnt : " + this.topqrycnt);
/* 117 */     if (this.type.equals("QUERYBYMOET")) {
/* 118 */       return null;
/*     */     }
/* 120 */     Object toReturn = null;
/* 121 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/* 122 */     ResultSet set = null;
/* 123 */     StringBuilder query = new StringBuilder();
/* 124 */     Statement stmt = null;
/* 125 */     Connection con = null;
/*     */     try {
/* 127 */       if ((this.topqrycnt != null) && (!this.topqrycnt.equals("polledValues"))) {
/* 128 */         con = getSqlDBConnection(Integer.parseInt(this.resID));
/* 129 */         if (con == null) {
/* 130 */           this.topqrycnt = "polledValues";
/*     */         } else {
/* 132 */           stmt = con.createStatement();
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 136 */       this.topqrycnt = "polledValues";
/* 137 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 140 */       if ((this.period != null) && (!this.period.equals("")) && (!this.period.equals("Execution Time"))) {
/* 141 */         if ((this.database == null) || (this.database.equalsIgnoreCase("All")) || (this.database.equalsIgnoreCase("Database"))) {
/* 142 */           if ((this.topqrycnt == null) || (this.topqrycnt.equals("polledValues"))) {
/* 143 */             if (this.type.equals("QUERYBYIO")) {
/* 144 */               if (this.period.equals("0")) {
/* 145 */                 query.append(" select TOP 10 AVGIO \tfrom (select AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' ORDER BY AVGIO DESC");
/*     */               } else {
/* 147 */                 query.append(" select TOP 10 AVGIO \tfrom (select AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append("  and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY AVGIO DESC");
/*     */               }
/* 149 */             } else if (this.type.equals("QUERYBYCPU")) {
/* 150 */               if (this.period.equals("0")) {
/* 151 */                 query.append(" select TOP 10 AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' ORDER BY AVGCPU DESC");
/*     */               } else {
/* 153 */                 query.append(" select TOP 10 AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY AVGCPU DESC");
/*     */               }
/* 155 */             } else if (this.type.equals("QUERYBYIOT")) {
/* 156 */               if (this.period.equals("0")) {
/* 157 */                 query.append(" select TOP 10 TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' ORDER BY AVGIO DESC");
/*     */               } else {
/* 159 */                 query.append(" select TOP 10 TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY AVGIO DESC");
/*     */               }
/* 161 */             } else if (this.type.equals("QUERYBYCPUT")) {
/* 162 */               if (this.period.equals("0")) {
/* 163 */                 query.append(" select TOP 10 TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' ORDER BY AVGCPU DESC");
/*     */               } else {
/* 165 */                 query.append(" select TOP 10 TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY AVGCPU DESC");
/*     */               }
/* 167 */             } else if (this.type.equals("QUERYBYMOE")) {
/* 168 */               if (this.period.equals("0")) {
/* 169 */                 query.append(" select TOP 10 EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' ORDER BY EXCCOUNT DESC");
/*     */               } else {
/* 171 */                 query.append(" select TOP 10 EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY EXCCOUNT DESC");
/*     */               }
/*     */             }
/* 174 */             if ((DBQueryUtil.isMysql()) || (DBQueryUtil.isPgsql())) {
/* 175 */               query.replace(query.indexOf(" TOP 10"), query.indexOf(" TOP 10") + 7, "");
/* 176 */               query.append(" LIMIT 10");
/*     */             }
/* 178 */             AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 179 */             set = AMConnectionPool.executeQueryStmt(query.toString());
/*     */           } else {
/* 181 */             if (this.type.equals("QUERYBYIO")) {
/* 182 */               query.append("SELECT TOP 10 \"AverageIO\" = (total_logical_reads + total_logical_writes) / qs.execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and attribute = 'dbid' ORDER BY \"AverageIO\" DESC");
/* 183 */             } else if (this.type.equals("QUERYBYCPU")) {
/* 184 */               query.append("SELECT TOP 10 \"AVGCPU\" = total_worker_time / (qs.execution_count*1000) FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY \"AVGCPU\" DESC");
/* 185 */             } else if (this.type.equals("QUERYBYIOT")) {
/* 186 */               query.append("SELECT TOP 10 \"TotalIO\" = (total_logical_reads + total_logical_writes), \"AverageIO\" = (total_logical_reads + total_logical_writes) / qs.execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and attribute = 'dbid' ORDER BY \"AverageIO\" DESC");
/* 187 */             } else if (this.type.equals("QUERYBYCPUT")) {
/* 188 */               query.append("SELECT TOP 10 \"TotalCPUused\" = total_worker_time/1000, \"AverageCPUused\" = total_worker_time / (qs.execution_count*1000) FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' ORDER BY \"AverageCPUused\" DESC");
/* 189 */             } else if (this.type.equals("QUERYBYMOE")) {
/* 190 */               query.append("SELECT TOP 10 \"Executioncount\" = execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and attribute = 'dbid' ORDER BY \"Executioncount\" DESC");
/*     */             }
/* 192 */             AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 193 */             set = stmt.executeQuery(query.toString());
/*     */           }
/*     */         }
/* 196 */         else if ((this.topqrycnt == null) || (this.topqrycnt.equals("polledValues"))) {
/* 197 */           if (this.type.equals("QUERYBYIO")) {
/* 198 */             if (this.period.equals("0")) {
/* 199 */               query.append(" select TOP 10 AVGIO \tfrom (select AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/*     */             } else {
/* 201 */               query.append(" select TOP 10 AVGIO \tfrom (select AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/*     */             }
/* 203 */           } else if (this.type.equals("QUERYBYCPU")) {
/* 204 */             if (!this.period.equals("0")) {
/* 205 */               query.append(" select TOP 10 AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/*     */             } else {
/* 207 */               query.append(" select TOP 10 AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/*     */             }
/* 209 */           } else if (this.type.equals("QUERYBYIOT")) {
/* 210 */             if (this.period.equals("0")) {
/* 211 */               query.append(" select TOP 10 TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/*     */             } else {
/* 213 */               query.append(" select TOP 10 TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/*     */             }
/* 215 */           } else if (this.type.equals("QUERYBYCPUT")) {
/* 216 */             if (this.period.equals("0")) {
/* 217 */               query.append(" select TOP 10 TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/*     */             } else {
/* 219 */               query.append(" select TOP 10 TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/*     */             }
/* 221 */           } else if (this.type.equals("QUERYBYMOE")) {
/* 222 */             if (this.period.equals("0")) {
/* 223 */               query.append(" select TOP 10 EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY EXCCOUNT DESC");
/*     */             } else {
/* 225 */               query.append(" select TOP 10 EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS last_execution_time,DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("' and DATABASENAME like '").append(this.database).append("*' ORDER BY EXCCOUNT DESC");
/*     */             }
/*     */           }
/* 228 */           if ((DBQueryUtil.isMysql()) || (DBQueryUtil.isPgsql())) {
/* 229 */             query.replace(query.indexOf(" TOP 10"), query.indexOf(" TOP 10") + 7, "");
/* 230 */             query.append(" LIMIT 10");
/*     */           }
/* 232 */           AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 233 */           set = AMConnectionPool.executeQueryStmt(query.toString());
/*     */         } else {
/* 235 */           if (this.type.equals("QUERYBYIO")) {
/* 236 */             query.append("select TOP 10 AverageIO from (SELECT AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("')ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 237 */           } else if (this.type.equals("QUERYBYCPU")) {
/* 238 */             query.append("select TOP 10 AverageCPUused from (SELECT AverageCPUused = total_worker_time / (qs.execution_count*1000) ,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("')ss WHERE attribute = 'dbid' and ss.DatabaseName like '").append(this.database).append("*' ORDER BY AverageCPUused DESC");
/* 239 */           } else if (this.type.equals("QUERYBYIOT")) {
/* 240 */             query.append("select TOP 10 TotalIO, AverageIO from (SELECT TotalIO = (total_logical_reads + total_logical_writes), AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count, DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("')ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 241 */           } else if (this.type.equals("QUERYBYCPUT")) {
/* 242 */             query.append("select TOP 10 TotalCPUused, AverageCPUused from (SELECT TotalCPUused = total_worker_time/1000 ,AverageCPUused = total_worker_time / (qs.execution_count*1000), DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("')ss WHERE attribute = 'dbid' and ss.DatabaseName like '").append(this.database).append("*' ORDER BY AverageCPUused DESC");
/* 243 */           } else if (this.type.equals("QUERYBYMOE")) {
/* 244 */             query.append("select TOP 10 Executioncount from (SELECT Executioncount = execution_count, DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(this.startdate).append("' and last_execution_time<='").append(this.enddate).append("')ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*     */           }
/* 246 */           AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 247 */           set = stmt.executeQuery(query.toString());
/*     */         }
/*     */         
/*     */       }
/* 251 */       else if ((this.database == null) || (this.database.equalsIgnoreCase("All")) || (this.database.equalsIgnoreCase("Database"))) {
/* 252 */         if ((this.topqrycnt == null) || (this.topqrycnt.equals("polledValues"))) {
/* 253 */           if (this.type.equals("QUERYBYIO")) {
/* 254 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYIO where RESOURCEID=" + this.resID);
/* 255 */             query.append(" select AVGIO from (select AVGIO,").append(formatLastExeTime()).append(" AS \"last_execution_time\",RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" ORDER BY AVGIO DESC");
/* 256 */           } else if (this.type.equals("QUERYBYCPU")) {
/* 257 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYCPU where RESOURCEID=" + this.resID);
/* 258 */             query.append(" select AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS \"last_execution_time\",RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" ORDER BY AVGCPU DESC");
/* 259 */           } else if (this.type.equals("QUERYBYIOT")) {
/* 260 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYIO where RESOURCEID=" + this.resID);
/* 261 */             query.append(" select TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS \"last_execution_time\",RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" ORDER BY AVGIO DESC");
/* 262 */           } else if (this.type.equals("QUERYBYCPUT")) {
/* 263 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYCPU where RESOURCEID=" + this.resID);
/* 264 */             query.append(" select TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS \"last_execution_time\",RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" ORDER BY AVGCPU DESC");
/* 265 */           } else if (this.type.equals("QUERYBYMOE")) {
/* 266 */             this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYMOE where RESOURCEID=" + this.resID);
/* 267 */             query.append(" select EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS \"last_execution_time\",RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" ORDER BY EXCCOUNT DESC");
/*     */           }
/* 269 */           AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 270 */           set = AMConnectionPool.executeQueryStmt(query.toString());
/*     */         } else {
/* 272 */           if (this.type.equals("QUERYBYIO")) {
/* 273 */             query.append("SELECT TOP 10 \"AverageIO\" = (total_logical_reads + total_logical_writes) / qs.execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' ORDER BY \"AverageIO\" DESC");
/* 274 */           } else if (this.type.equals("QUERYBYCPU")) {
/* 275 */             query.append("SELECT TOP 10 \"AVGCPU\" = total_worker_time / (qs.execution_count*1000) FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' ORDER BY \"AVGCPU\" DESC");
/* 276 */           } else if (this.type.equals("QUERYBYIOT")) {
/* 277 */             query.append("SELECT TOP 10 \"TotalIO\" = (total_logical_reads + total_logical_writes),\"AverageIO\" = (total_logical_reads + total_logical_writes) / qs.execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' ORDER BY \"AverageIO\" DESC");
/* 278 */           } else if (this.type.equals("QUERYBYCPUT")) {
/* 279 */             query.append("SELECT TOP 10 \"TotalCPUused\" = total_worker_time/1000, \"AverageCPUused\" = total_worker_time / (qs.execution_count*1000) FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' ORDER BY \"AverageCPUused\" DESC");
/* 280 */           } else if (this.type.equals("QUERYBYMOE")) {
/* 281 */             query.append("SELECT TOP 10 \"Executioncount\" = execution_count FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE attribute = 'dbid' ORDER BY \"Executioncount\" DESC");
/*     */           }
/* 283 */           AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 284 */           set = stmt.executeQuery(query.toString());
/*     */         }
/*     */       }
/* 287 */       else if ((this.topqrycnt == null) || (this.topqrycnt.equals("polledValues"))) {
/* 288 */         if (this.type.equals("QUERYBYIO")) {
/* 289 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYIO where RESOURCEID=" + this.resID);
/* 290 */           query.append(" select AVGIO from (select AVGIO,").append(formatLastExeTime()).append(" AS \"last_execution_time\",DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/* 291 */         } else if (this.type.equals("QUERYBYCPU")) {
/* 292 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYCPU where RESOURCEID=" + this.resID);
/* 293 */           query.append(" select AVGCPU from (select AVGCPU,").append(formatLastExeTime()).append(" AS \"last_execution_time\",DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/* 294 */         } else if (this.type.equals("QUERYBYIOT")) {
/* 295 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYIO where RESOURCEID=" + this.resID);
/* 296 */           query.append(" select TOTALIO from (select TOTALIO,AVGIO,").append(formatLastExeTime()).append(" AS \"last_execution_time\",DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGIO DESC");
/* 297 */         } else if (this.type.equals("QUERYBYCPUT")) {
/* 298 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYCPU where RESOURCEID=" + this.resID);
/* 299 */           query.append(" select TOTALCPU from (select TOTALCPU,AVGCPU,").append(formatLastExeTime()).append(" AS \"last_execution_time\",DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" and DATABASENAME like '").append(this.database).append("*' ORDER BY AVGCPU DESC");
/* 300 */         } else if (this.type.equals("QUERYBYMOE")) {
/* 301 */           this.collectionTime = getMaxCollectionTimeFrom("select max(COLLECTIONTIME) from AM_MSSQL_QRYBYMOE where RESOURCEID=" + this.resID);
/* 302 */           query.append(" select EXCCOUNT from (select EXCCOUNT,").append(formatLastExeTime()).append(" AS \"last_execution_time\",DATABASENAME,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where RESOURCEID=").append(this.resID).append(" and COLLECTIONTIME=").append(this.collectionTime).append(" and DATABASENAME like '").append(this.database).append("*' ORDER BY EXCCOUNT DESC");
/*     */         }
/* 304 */         AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 305 */         set = AMConnectionPool.executeQueryStmt(query.toString());
/*     */       } else {
/* 307 */         if (this.type.equals("QUERYBYIO")) {
/* 308 */           query.append("select TOP 10 AverageIO from (SELECT AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 309 */         } else if (this.type.equals("QUERYBYCPU")) {
/* 310 */           query.append("select TOP 10 AverageCPUused from (SELECT AverageCPUused = total_worker_time / (qs.execution_count*1000) ,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss WHERE attribute = 'dbid' and ss.DatabaseName like '").append(this.database).append("*' ORDER BY AverageCPUused DESC");
/* 311 */         } else if (this.type.equals("QUERYBYIOT")) {
/* 312 */           query.append("select TOP 10 TotalIO, AverageIO from (SELECT TotalIO = (total_logical_reads + total_logical_writes), AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count, DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 313 */         } else if (this.type.equals("QUERYBYCPUT")) {
/* 314 */           query.append("select TOP 10 TotalCPUused, AverageCPUused from (SELECT TotalCPUused = total_worker_time/1000 ,AverageCPUused = total_worker_time / (qs.execution_count*1000),DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss WHERE attribute = 'dbid' and ss.DatabaseName like '").append(this.database).append("*' ORDER BY AverageCPUused DESC");
/* 315 */         } else if (this.type.equals("QUERYBYMOE")) {
/* 316 */           query.append("select TOP 10 Executioncount from (SELECT Executioncount = execution_count, DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'), attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(this.database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*     */         }
/* 318 */         AMLog.debug("MSSQL Performance Graph query =======>> " + query.toString());
/* 319 */         set = stmt.executeQuery(query.toString());
/*     */       }
/*     */       
/*     */ 
/* 323 */       int i = 0;
/* 324 */       String pooln; while (set.next()) {
/* 325 */         i += 1;
/* 326 */         pooln = Integer.toString(i);
/* 327 */         result.addValue(new Double(set.getDouble(1)), "", pooln);
/*     */       }
/* 329 */       toReturn = result;
/* 330 */       return (String)toReturn;
/*     */     } catch (Exception e) {
/* 332 */       e.printStackTrace();
/*     */     } finally {
/* 334 */       if (set != null) {
/*     */         try {
/* 336 */           set.close();
/*     */         } catch (SQLException e) {
/* 338 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 341 */       if (stmt != null) {
/*     */         try {
/* 343 */           stmt.close();
/*     */         } catch (SQLException e) {
/* 345 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 348 */       if (con != null) {
/*     */         try {
/* 350 */           con.close();
/*     */         } catch (SQLException e) {
/* 352 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 356 */     return null;
/*     */   }
/*     */   
/*     */   private String formatLastExeTime() {
/* 360 */     if (DBQueryUtil.isMssql())
/* 361 */       return "DATEADD(ss,LASTEXETIME/1000,'1970-1-1 5:30:0')";
/* 362 */     if (DBQueryUtil.isMysql())
/* 363 */       return "FROM_UNIXTIME(LASTEXETIME/1000)";
/* 364 */     if (DBQueryUtil.isPgsql()) {
/* 365 */       return "to_timestamp(LASTEXETIME/1000)::timestamp without time zone";
/*     */     }
/* 367 */     return null;
/*     */   }
/*     */   
/*     */   private final long getMaxCollectionTimeFrom(String query) {
/* 371 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 372 */     ResultSet set = null;
/* 373 */     collectionTime = -1L;
/*     */     try {
/* 375 */       set = AMConnectionPool.executeQueryStmt(query);
/* 376 */       if (set.next()) {
/* 377 */         collectionTime = set.getLong(1);
/* 378 */         if (collectionTime != 0L) {} }
/* 379 */       return System.currentTimeMillis();
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 383 */       exp.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 386 */         if (set != null) {
/* 387 */           set.close();
/*     */         }
/*     */       } catch (Exception e) {
/* 390 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final void closeResultSet(ResultSet set)
/*     */   {
/*     */     try {
/* 398 */       if (set != null) {
/* 399 */         set.close();
/*     */       }
/*     */     } catch (Exception e) {
/* 402 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasExpired(Map params, Date since) {
/* 407 */     return true;
/*     */   }
/*     */   
/*     */   public String getProducerId() {
/* 411 */     return "DataProducer";
/*     */   }
/*     */   
/*     */   public Object produceUrl(Map params)
/*     */     throws DatasetProduceException
/*     */   {
/* 417 */     return this.urls;
/*     */   }
/*     */   
/*     */   public static Connection getSqlDBConnection(int resid) {
/* 421 */     Connection conn = null;
/*     */     try {
/* 423 */       conn = ExecuteQueryAction.getDbConnection(resid, null);
/*     */     } catch (Exception ex) {
/* 425 */       ex.printStackTrace();
/*     */     }
/* 427 */     return conn;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\wlogic\bean\GetMSSQLPerfGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */