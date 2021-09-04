/*      */ package com.adventnet.appmanager.mssql.struts;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.ExecuteQueryAction;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ public final class MsSQLDispatchAction
/*      */   extends DispatchAction
/*      */ {
/*   50 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*   52 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*      */   
/*      */   public ActionForward overviewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/*   56 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/*   58 */       if ("true".equals(request.getParameter("sqljobmanagement"))) {
/*   59 */         return sqlJobsManagementAction(mapping, form, request, response);
/*      */       }
/*   61 */       if ("true".equals(request.getParameter("sqldbmanagement"))) {
/*   62 */         return sqlDatabaseManagementAction(mapping, form, request, response);
/*      */       }
/*      */       
/*   65 */       this.mo.getReloadPeriod(request);
/*   66 */       String pollint = (String)request.getAttribute("reloadperiod");
/*      */       
/*   68 */       if (request.getParameter("showconfigdiv") != null) {
/*   69 */         request.setAttribute("reloadperiod", null);
/*      */       }
/*      */       
/*   72 */       String resourceid = request.getParameter("resourceid");
/*   73 */       request.setAttribute("resourceid", resourceid);
/*      */       try {
/*   75 */         Long.parseLong(pollint);
/*      */       } catch (Exception e) {
/*   77 */         pollint = "300";
/*      */       }
/*   79 */       HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/*   80 */       if (map != null) {
/*   81 */         request.setAttribute("systeminfo", map);
/*      */       }
/*      */       
/*   84 */       ResultSet rs = null;
/*      */       try {
/*   86 */         String scripts_query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='Script Monitor' and AM_PARENTCHILDMAPPER.PARENTID=" + request.getParameter("resourceid") + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*   87 */         rs = AMConnectionPool.executeQueryStmt(scripts_query);
/*   88 */         ArrayList<String> al = new ArrayList();
/*   89 */         while (rs.next())
/*      */         {
/*   91 */           al.add(rs.getString(1));
/*      */         }
/*   93 */         request.setAttribute("script_ids", al);
/*      */       } catch (Exception exc1) {
/*   95 */         exc1.printStackTrace();
/*      */       } finally {
/*   97 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       
/*  100 */       String name = null;
/*  101 */       String displayname = null;
/*  102 */       int configured = 1;
/*  103 */       ResultSet modetails = null;
/*      */       try {
/*  105 */         modetails = AMConnectionPool.executeQueryStmt("select RESOURCENAME,DISPLAYNAME,DCSTARTED from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  106 */         if (modetails.next()) {
/*  107 */           name = modetails.getString("RESOURCENAME");
/*  108 */           request.setAttribute("name", name);
/*  109 */           displayname = modetails.getString("DISPLAYNAME");
/*  110 */           request.setAttribute("displayname", displayname);
/*  111 */           configured = modetails.getInt("DCSTARTED");
/*  112 */           request.setAttribute("showdata", configured + "");
/*      */         } else {
/*  114 */           return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*      */         }
/*      */       } catch (Exception e) {
/*  117 */         e.printStackTrace();
/*      */       } finally {
/*  119 */         AMConnectionPool.closeResultSet(modetails);
/*      */       }
/*      */       
/*  122 */       ResourceConfig mssql = null;
/*      */       try {
/*  124 */         mssql = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/*      */       } catch (Exception e) {
/*  126 */         e.printStackTrace();
/*      */       }
/*      */       
/*  129 */       String details = request.getParameter("details");
/*  130 */       if (details == null) {
/*  131 */         details = "Availability";
/*      */       }
/*  133 */       request.setAttribute("configured", "true");
/*  134 */       request.setAttribute("details", details);
/*  135 */       request.setAttribute("resourceid", String.valueOf(mssql.getresourceID()));
/*      */       
/*  137 */       if (details.equals("Availability")) {
/*  138 */         request.setAttribute("details", getMsSqlDetails(mssql.getresourceID(), mssql.getApplnDiscPort()));
/*  139 */         request.setAttribute("performance", getPerformance(mssql.getresourceID()));
/*  140 */         request.setAttribute("dbdetails", getDBDetails(mssql.getresourceID()));
/*  141 */         Hashtable disableTable = EnterpriseUtil.getDisableTable();
/*  142 */         String jobsenabled = (String)disableTable.get("MSSQL-DB-server#SCHEDULEDJOBS");
/*  143 */         ArrayList jobs = getMsSqlJobDetails(mssql.getresourceID());
/*  144 */         if ((jobsenabled != null) && (jobsenabled.equals("true")))
/*      */         {
/*  146 */           request.setAttribute("JOBS", jobs);
/*      */         }
/*  148 */       } else if (details.equals("DB")) {
/*  149 */         request.setAttribute("dbdetails", getDBDetails(mssql.getresourceID()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  177 */       e.printStackTrace();
/*      */     }
/*  179 */     return mapping.findForward("overview");
/*      */   }
/*      */   
/*      */   public Properties getPerformance(int resid)
/*      */   {
/*  184 */     long maxtime = 0L;
/*  185 */     Properties p = new Properties();
/*  186 */     p.setProperty("BUFFERHITRATIO", "-");
/*  187 */     p.setProperty("DATABASEPAGES", "-");
/*  188 */     p.setProperty("PAGELIFEEXP", "-");
/*  189 */     p.setProperty("TOTALPAGES", "-");
/*  190 */     p.setProperty("CONNECTIONTIME", "-");
/*  191 */     p.setProperty("FREEPAGES", "-");
/*  192 */     p.setProperty("PAGELOOKUPSPERMIN", "-");
/*  193 */     p.setProperty("PAGEREADSPERMIN", "-");
/*  194 */     p.setProperty("PAGEWRITESPERMIN", "-");
/*  195 */     p.setProperty("CONNECTIONS", "-");
/*  196 */     p.setProperty("LOGINSPERMIN", "-");
/*  197 */     p.setProperty("LOGOUTSPERMIN", "-");
/*  198 */     p.setProperty("AVGLOCKWAITTIME", "-");
/*  199 */     p.setProperty("DEADLOCKSPERMIN", "-");
/*  200 */     p.setProperty("LOCKREQUESTSPERMIN", "-");
/*  201 */     p.setProperty("LOCKTIMEOUTSPERMIN", "-");
/*  202 */     p.setProperty("LOCKWAITSPERMIN", "-");
/*  203 */     p.setProperty("AVGLATCHWAITTIME", "-");
/*  204 */     p.setProperty("LATCHWAITSPERMIN", "-");
/*  205 */     p.setProperty("CACHEHITRATIO", "-");
/*  206 */     p.setProperty("CACHECOUNT", "-");
/*  207 */     p.setProperty("CACHEPAGES", "-");
/*  208 */     p.setProperty("CACHEUSEDPERMIN", "-");
/*  209 */     p.setProperty("CONNECTIONMEMORY", "-");
/*  210 */     p.setProperty("GRANTEDWORKSPACEMEMORY", "-");
/*  211 */     p.setProperty("LOCKMEMORY", "-");
/*  212 */     p.setProperty("MEMORYGRANTSPENDING", "-");
/*  213 */     p.setProperty("MEMORYGRANTSSUCCESS", "-");
/*  214 */     p.setProperty("OPTIMIZERMEMORY", "-");
/*  215 */     p.setProperty("SQLCACHEMEMORY", "-");
/*  216 */     p.setProperty("TOTALMEMORY", "-");
/*  217 */     p.setProperty("AUTOPARAMSPERMIN", "-");
/*  218 */     p.setProperty("BATCHREQUESTSPERMIN", "-");
/*  219 */     p.setProperty("SQLCOMPILATIONSPERMIN", "-");
/*  220 */     p.setProperty("SQLRECOMPILATIONSPERMIN", "-");
/*  221 */     p.setProperty("FAILEDAUTOPARAMS", "-");
/*  222 */     p.setProperty("FULLSCANSPERMIN", "-");
/*  223 */     p.setProperty("PROBESCANSPERMIN", "-");
/*  224 */     p.setProperty("RANGESCANSPERMIN", "-");
/*  225 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQLBUFFERDETAILS where RESOURCEID=" + resid + "";
/*  226 */     ResultSet rs = null;
/*      */     try {
/*  228 */       rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/*  229 */       if (rs.next()) {
/*  230 */         maxtime = rs.getLong(1);
/*      */       }
/*  232 */       rs.close();
/*  233 */       if (maxtime == 0L) {
/*  234 */         return p;
/*      */       }
/*  236 */       String dataquery = "select AM_MSSQLBUFFERDETAILS.BUFFERHITRATIO , AM_MSSQLBUFFERDETAILS.TOTALPAGES , AM_MSSQLBUFFERDETAILS.DATABASEPAGES, AM_MSSQLBUFFERDETAILS.PAGELIFEEXP, AM_MSSQLBUFFERDETAILS.FREEPAGES , AM_MSSQLBUFFERDETAILS.PAGELOOKUPSPERMIN , AM_MSSQLBUFFERDETAILS.PAGEREADSPERMIN , AM_MSSQLBUFFERDETAILS.PAGEWRITESPERMIN , AM_MSSQLGENERALDETAILS.CONNECTIONS , AM_MSSQLGENERALDETAILS.LOGINSPERMIN , AM_MSSQLGENERALDETAILS.LOGOUTSPERMIN , AM_MSSQLLOCKDETAILS.AVGLOCKWAITTIME , AM_MSSQLLOCKDETAILS.DEADLOCKSPERMIN , AM_MSSQLLOCKDETAILS.LOCKREQUESTSPERMIN , AM_MSSQLLOCKDETAILS.LOCKTIMEOUTSPERMIN , AM_MSSQLLOCKDETAILS.LOCKWAITSPERMIN , AM_MSSQLLATCHES.AVGLATCHWAITTIME , AM_MSSQLLATCHES.LATCHWAITSPERMIN , AM_MSSQLCACHEDETAILS.CACHEHITRATIO , AM_MSSQLCACHEDETAILS.CACHECOUNT , AM_MSSQLCACHEDETAILS.CACHEPAGES , AM_MSSQLCACHEDETAILS.CACHEUSEDPERMIN , AM_MSSQLMEMORYDETAILS.CONNECTIONMEMORY , AM_MSSQLMEMORYDETAILS.GRANTEDWORKSPACEMEMORY , AM_MSSQLMEMORYDETAILS.LOCKMEMORY , AM_MSSQLMEMORYDETAILS.MEMORYGRANTSPENDING , AM_MSSQLMEMORYDETAILS.MEMORYGRANTSSUCCESS , AM_MSSQLMEMORYDETAILS.OPTIMIZERMEMORY , AM_MSSQLMEMORYDETAILS.SQLCACHEMEMORY , AM_MSSQLMEMORYDETAILS.TOTALMEMORY , AM_MSSQLSQLDETAILS.AUTOPARAMSPERMIN , AM_MSSQLSQLDETAILS.BATCHREQUESTSPERMIN , AM_MSSQLSQLDETAILS.SQLCOMPILATIONSPERMIN , AM_MSSQLSQLDETAILS.SQLRECOMPILATIONSPERMIN , AM_MSSQLSQLDETAILS.FAILEDAUTOPARAMS , AM_MSSQLACCESSMETHODDETAILS.FULLSCANSPERMIN , AM_MSSQLACCESSMETHODDETAILS.PROBESCANSPERMIN , AM_MSSQLACCESSMETHODDETAILS.RANGESCANSPERMIN , AM_ManagedObjectData.RESPONSETIME  from AM_MSSQLBUFFERDETAILS left outer join AM_MSSQLACCESSMETHODDETAILS on AM_MSSQLACCESSMETHODDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLACCESSMETHODDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_MSSQLMEMORYDETAILS on AM_MSSQLMEMORYDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLMEMORYDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLSQLDETAILS on AM_MSSQLSQLDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLSQLDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLLATCHES on AM_MSSQLLATCHES.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLLATCHES.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLCACHEDETAILS on AM_MSSQLCACHEDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLCACHEDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_MSSQLLOCKDETAILS on AM_MSSQLLOCKDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLLOCKDETAILS.RESOURCEID = AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLGENERALDETAILS on AM_MSSQLGENERALDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLGENERALDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME where AM_MSSQLBUFFERDETAILS.RESOURCEID=" + resid + " and AM_MSSQLBUFFERDETAILS.COLLECTIONTIME=" + maxtime;
/*  237 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/*  238 */       if (rs.next()) {
/*  239 */         p.setProperty("BUFFERHITRATIO", String.valueOf(rs.getDouble("BUFFERHITRATIO")));
/*  240 */         p.setProperty("DATABASEPAGES", String.valueOf(rs.getLong("DATABASEPAGES")));
/*  241 */         p.setProperty("PAGELIFEEXP", String.valueOf(rs.getLong("PAGELIFEEXP")));
/*  242 */         p.setProperty("TOTALPAGES", String.valueOf(rs.getLong("TOTALPAGES")));
/*  243 */         p.setProperty("CONNECTIONTIME", String.valueOf(rs.getLong("RESPONSETIME")));
/*  244 */         p.setProperty("FREEPAGES", String.valueOf(rs.getLong("FREEPAGES")));
/*  245 */         p.setProperty("PAGELOOKUPSPERMIN", String.valueOf(rs.getDouble("PAGELOOKUPSPERMIN")));
/*  246 */         p.setProperty("PAGEREADSPERMIN", String.valueOf(rs.getDouble("PAGEREADSPERMIN")));
/*  247 */         p.setProperty("PAGEWRITESPERMIN", String.valueOf(rs.getDouble("PAGEWRITESPERMIN")));
/*  248 */         p.setProperty("CONNECTIONS", String.valueOf(rs.getLong("CONNECTIONS")));
/*  249 */         p.setProperty("LOGINSPERMIN", String.valueOf(rs.getDouble("LOGINSPERMIN")));
/*  250 */         p.setProperty("LOGOUTSPERMIN", String.valueOf(rs.getDouble("LOGOUTSPERMIN")));
/*  251 */         p.setProperty("AVGLOCKWAITTIME", String.valueOf(rs.getDouble("AVGLOCKWAITTIME")));
/*  252 */         p.setProperty("DEADLOCKSPERMIN", String.valueOf(rs.getDouble("DEADLOCKSPERMIN")));
/*  253 */         p.setProperty("LOCKREQUESTSPERMIN", String.valueOf(rs.getDouble("LOCKREQUESTSPERMIN")));
/*  254 */         p.setProperty("LOCKTIMEOUTSPERMIN", String.valueOf(rs.getDouble("LOCKTIMEOUTSPERMIN")));
/*  255 */         p.setProperty("LOCKWAITSPERMIN", String.valueOf(rs.getDouble("LOCKWAITSPERMIN")));
/*  256 */         p.setProperty("AVGLATCHWAITTIME", String.valueOf(rs.getDouble("AVGLATCHWAITTIME")));
/*  257 */         p.setProperty("LATCHWAITSPERMIN", String.valueOf(rs.getDouble("LATCHWAITSPERMIN")));
/*  258 */         p.setProperty("CACHEHITRATIO", String.valueOf(rs.getDouble("CACHEHITRATIO")));
/*  259 */         p.setProperty("CACHECOUNT", String.valueOf(rs.getLong("CACHECOUNT")));
/*  260 */         p.setProperty("CACHEPAGES", String.valueOf(rs.getLong("CACHEPAGES")));
/*  261 */         p.setProperty("CACHEUSEDPERMIN", String.valueOf(rs.getDouble("CACHEUSEDPERMIN")));
/*  262 */         p.setProperty("CONNECTIONMEMORY", String.valueOf(rs.getLong("CONNECTIONMEMORY")));
/*  263 */         p.setProperty("GRANTEDWORKSPACEMEMORY", String.valueOf(rs.getLong("GRANTEDWORKSPACEMEMORY")));
/*  264 */         p.setProperty("LOCKMEMORY", String.valueOf(rs.getLong("LOCKMEMORY")));
/*  265 */         p.setProperty("MEMORYGRANTSPENDING", String.valueOf(rs.getLong("MEMORYGRANTSPENDING")));
/*  266 */         p.setProperty("MEMORYGRANTSSUCCESS", String.valueOf(rs.getLong("MEMORYGRANTSSUCCESS")));
/*  267 */         p.setProperty("OPTIMIZERMEMORY", String.valueOf(rs.getLong("OPTIMIZERMEMORY")));
/*  268 */         p.setProperty("SQLCACHEMEMORY", String.valueOf(rs.getLong("SQLCACHEMEMORY")));
/*  269 */         p.setProperty("TOTALMEMORY", String.valueOf(rs.getLong("TOTALMEMORY")));
/*  270 */         p.setProperty("AUTOPARAMSPERMIN", String.valueOf(rs.getDouble("AUTOPARAMSPERMIN")));
/*  271 */         p.setProperty("BATCHREQUESTSPERMIN", String.valueOf(rs.getDouble("BATCHREQUESTSPERMIN")));
/*  272 */         p.setProperty("SQLCOMPILATIONSPERMIN", String.valueOf(rs.getDouble("SQLCOMPILATIONSPERMIN")));
/*  273 */         p.setProperty("SQLRECOMPILATIONSPERMIN", String.valueOf(rs.getDouble("SQLRECOMPILATIONSPERMIN")));
/*  274 */         p.setProperty("FAILEDAUTOPARAMS", String.valueOf(rs.getLong("FAILEDAUTOPARAMS")));
/*  275 */         p.setProperty("FULLSCANSPERMIN", String.valueOf(rs.getDouble("FULLSCANSPERMIN")));
/*  276 */         p.setProperty("PROBESCANSPERMIN", String.valueOf(rs.getDouble("PROBESCANSPERMIN")));
/*  277 */         p.setProperty("RANGESCANSPERMIN", String.valueOf(rs.getDouble("RANGESCANSPERMIN")));
/*      */       }
/*      */     } catch (Exception e) {
/*  280 */       AMLog.fatal("MsSQL : Problem in fetching the data for Performance", e);
/*  281 */       e.printStackTrace();
/*      */     } finally {
/*  283 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  285 */     return p;
/*      */   }
/*      */   
/*      */   public Properties getMsSqlDetails(int id, int port) {
/*  289 */     Properties p = new Properties();
/*  290 */     String mssqldetailsquery = "select * from AM_MSSQLDETAILS where RESOURCEID=" + id + "";
/*  291 */     ResultSet rs = null;
/*      */     try {
/*  293 */       rs = AMConnectionPool.executeQueryStmt(mssqldetailsquery);
/*  294 */       if (rs.next()) {
/*  295 */         p.setProperty("VERSION", rs.getString("VERSION"));
/*  296 */         p.setProperty("ODBC", rs.getString("ODBCDRIVERVERSION"));
/*  297 */         p.setProperty("PORT", String.valueOf(port));
/*      */       }
/*      */     } catch (Exception e) {
/*  300 */       AMLog.fatal("MS SQL : Exception in querying the data for MSSQLDetails", e);
/*  301 */       e.printStackTrace();
/*      */     } finally {
/*  303 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  305 */     return p;
/*      */   }
/*      */   
/*      */   public ActionForward performanceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  309 */     ResultSet rs = null;
/*      */     try {
/*  311 */       response.setContentType("text/html; charset=UTF-8");
/*  312 */       String resourceid = request.getParameter("resourceid");
/*  313 */       request.setAttribute("resourceid", resourceid);
/*  314 */       String dbQuery = "select DISPLAYNAME from AM_ManagedObject MO JOIN AM_PARENTCHILDMAPPER PC ON MO.RESOURCEID=PC.CHILDID WHERE MO.TYPE='DataBase' AND PC.PARENTID=" + resourceid + " order by DISPLAYNAME";
/*  315 */       rs = AMConnectionPool.executeQueryStmt(dbQuery);
/*  316 */       ArrayList dbList = new ArrayList();
/*  317 */       while (rs.next()) {
/*  318 */         dbList.add(rs.getString("DISPLAYNAME"));
/*      */       }
/*  320 */       request.setAttribute("dbList", dbList);
/*  321 */       getMsSQLPerformanceDetailsFilterBy(mapping, form, request, response);
/*      */     } catch (Exception e) {
/*  323 */       e.printStackTrace();
/*      */     } finally {
/*  325 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  327 */     return mapping.findForward("performance");
/*      */   }
/*      */   
/*      */   public ActionForward backupDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/*  332 */       response.setContentType("text/html; charset=UTF-8");
/*  333 */       String resourceid = request.getParameter("resourceid");
/*  334 */       request.setAttribute("resourceid", resourceid);
/*  335 */       String name = getResourceNameForResourceID(resourceid);
/*  336 */       request.setAttribute("name", name);
/*      */       
/*  338 */       ResourceConfig mssql = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/*      */       
/*  340 */       ArrayList backups = getMsSqlBackupDetails(mssql.getresourceID(), name);
/*  341 */       request.setAttribute("BACKUPS", backups);
/*  342 */       ArrayList restore = getMsSqlRestoreDetails(mssql.getresourceID());
/*  343 */       request.setAttribute("RESTORE", restore);
/*      */     } catch (Exception e) {
/*  345 */       e.printStackTrace();
/*      */     }
/*  347 */     return mapping.findForward("backuprestore");
/*      */   }
/*      */   
/*      */   public ActionForward replicationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/*  352 */       response.setContentType("text/html; charset=UTF-8");
/*  353 */       String resourceid = request.getParameter("resourceid");
/*  354 */       request.setAttribute("resourceid", resourceid);
/*  355 */       String name = getResourceNameForResourceID(resourceid);
/*  356 */       request.setAttribute("name", name);
/*      */       
/*  358 */       ResourceConfig mssql = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/*      */       
/*  360 */       request.setAttribute("REPL-DETAILS", getMssqlReplicationDetails(mssql.getresourceID()));
/*  361 */       request.setAttribute("logshippingdetails", getLogShippingDetails(mssql.getresourceID()));
/*      */     } catch (Exception e) {
/*  363 */       e.printStackTrace();
/*      */     }
/*  365 */     return mapping.findForward("replication");
/*      */   }
/*      */   
/*      */   public ArrayList getMsSqlBackupDetails(int resourceid, String resourceName) {
/*  369 */     ArrayList backupDetails = new ArrayList();
/*      */     try
/*      */     {
/*  372 */       Calendar ucal = Calendar.getInstance();
/*  373 */       ucal.setTimeInMillis(0L);
/*  374 */       String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */       
/*  376 */       String query = " select RESOURCEID,SERVER,DBNAME," + DBQueryUtil.formatTimeWithColName(unixStartTime, "BACKUPSTART") + " AS BACKUPSTART ," + DBQueryUtil.formatTimeWithColName(unixStartTime, "BACKUPEND") + " AS BACKUPEND," + DBQueryUtil.formatTimeWithColName(unixStartTime, "EXPIRATION") + " AS EXPIRATION,RECOVERY,DAMAGED,TOTALTIME,BKUPTYPE,BKUPSIZE,LOGICALNAME,PHYNAME,BKUPSETNAME,DESCRIPTION, BKUPAGE from AM_MSSQL_BACKUP join AM_PARENTCHILDMAPPER on AM_MSSQL_BACKUP.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/*  377 */       ResultSet results = null;
/*      */       try {
/*  379 */         results = AMConnectionPool.executeQueryStmt(query);
/*  380 */         String backUpType = null;
/*  381 */         String backUpTypeDes = null;
/*  382 */         while (results.next()) {
/*  383 */           String dbName = results.getString("DBNAME");
/*  384 */           int dbdcstatus = 1;
/*  385 */           DataCollectionControllerUtil dcc = new DataCollectionControllerUtil();
/*  386 */           if (DataCollectionControllerUtil.isUnManaged(DBUtil.getResourceIdForResourceName(resourceName + "#" + dbName))) {
/*  387 */             dbdcstatus = 0;
/*      */           }
/*  389 */           if (dbdcstatus == 1) {
/*  390 */             Properties backups = new Properties();
/*  391 */             backups.setProperty("RESOURCEID", results.getString("RESOURCEID"));
/*  392 */             backups.setProperty("SERVER", results.getString("SERVER"));
/*  393 */             backups.setProperty("DBNAME", results.getString("DBNAME"));
/*  394 */             backups.setProperty("BACKUPSTART", results.getString("BACKUPSTART"));
/*  395 */             backups.setProperty("BACKUPEND", results.getString("BACKUPEND"));
/*  396 */             if (results.getString("EXPIRATION").contains("1970-01-01")) {
/*  397 */               backups.setProperty("EXPIRATION", "-");
/*      */             } else {
/*  399 */               backups.setProperty("EXPIRATION", results.getString("EXPIRATION"));
/*      */             }
/*  401 */             backups.setProperty("RECOVERY", results.getString("RECOVERY"));
/*  402 */             backups.setProperty("DAMAGED", results.getString("DAMAGED"));
/*  403 */             backups.setProperty("TOTALTIME", results.getString("TOTALTIME"));
/*  404 */             backUpType = results.getString("BKUPTYPE");
/*  405 */             if ("D".equalsIgnoreCase(backUpType)) {
/*  406 */               backUpTypeDes = "Database";
/*  407 */             } else if ("I".equalsIgnoreCase(backUpType)) {
/*  408 */               backUpTypeDes = "Differential database";
/*  409 */             } else if ("L".equalsIgnoreCase(backUpType)) {
/*  410 */               backUpTypeDes = "Log";
/*  411 */             } else if ("F".equalsIgnoreCase(backUpType)) {
/*  412 */               backUpTypeDes = "File or filegroup";
/*  413 */             } else if ("G".equalsIgnoreCase(backUpType)) {
/*  414 */               backUpTypeDes = "Differential file";
/*  415 */             } else if ("P".equalsIgnoreCase(backUpType)) {
/*  416 */               backUpTypeDes = "Partial";
/*  417 */             } else if ("Q".equalsIgnoreCase(backUpType)) {
/*  418 */               backUpTypeDes = "Differential partial";
/*      */             } else {
/*  420 */               backUpTypeDes = "-";
/*      */             }
/*  422 */             backups.setProperty("BKUPTYPEDesc", backUpTypeDes);
/*  423 */             backups.setProperty("BKUPTYPE", backUpType);
/*  424 */             backups.setProperty("BKUPSIZE", FormatUtil.formatBytesToMB(results.getString("BKUPSIZE")));
/*  425 */             backups.setProperty("LOGICALNAME", results.getString("LOGICALNAME"));
/*  426 */             backups.setProperty("PHYNAME", results.getString("PHYNAME"));
/*  427 */             if (results.getString("BKUPSETNAME") != null) {
/*  428 */               backups.setProperty("BKUPSETNAME", results.getString("BKUPSETNAME"));
/*      */             } else {
/*  430 */               backups.setProperty("BKUPSETNAME", "");
/*      */             }
/*  432 */             if (results.getString("DESCRIPTION") != null) {
/*  433 */               backups.setProperty("DESCRIPTION", results.getString("DESCRIPTION"));
/*      */             } else {
/*  435 */               backups.setProperty("DESCRIPTION", "");
/*      */             }
/*  437 */             backups.setProperty("BKUPAGE", results.getString("BKUPAGE"));
/*  438 */             backupDetails.add(backups);
/*      */           }
/*      */         }
/*      */       } catch (SQLException sqlException) {
/*  442 */         sqlException.printStackTrace();
/*      */       } finally {
/*  444 */         AMConnectionPool.closeResultSet(results);
/*      */       }
/*      */     } catch (Exception exception) {
/*  447 */       exception.printStackTrace();
/*      */     }
/*  449 */     return backupDetails;
/*      */   }
/*      */   
/*      */   public ArrayList getMsSqlRestoreDetails(int resourceid)
/*      */   {
/*  454 */     ArrayList restoreDetails = new ArrayList();
/*  455 */     ResultSet rs = null;
/*      */     try {
/*  457 */       rs = AMConnectionPool.executeQueryStmt("select DBNAME,RESTOREDBY,STARTED,RSTFROM,RSTTO from AM_MSSQL_RESTOREBACKUP where RESOURCEID=" + resourceid);
/*  458 */       while (rs.next()) {
/*  459 */         Properties restore = new Properties();
/*  460 */         restore.setProperty("DBNAME", rs.getString("DBNAME"));
/*  461 */         restore.setProperty("RESTOREDBY", rs.getString("RESTOREDBY"));
/*  462 */         restore.setProperty("STARTED", rs.getString("STARTED"));
/*  463 */         restore.setProperty("RSTFROM", rs.getString("RSTFROM"));
/*  464 */         restore.setProperty("RSTTO", rs.getString("RSTTO"));
/*  465 */         restoreDetails.add(restore);
/*      */       }
/*      */     } catch (Exception ex) {
/*  468 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  471 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  473 */     return restoreDetails;
/*      */   }
/*      */   
/*      */   public HashMap getMssqlReplicationDetails(int resourceid)
/*      */   {
/*  478 */     long maxtime = 0L;
/*  479 */     HashMap replDetails = null;
/*  480 */     ResultSet rs = null;
/*  481 */     StringBuilder dataquery = null;
/*  482 */     DecimalFormat df = new DecimalFormat("#.##");
/*      */     try {
/*  484 */       replDetails = new HashMap();
/*      */       try {
/*  486 */         String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQL_REPLSUBSCRIPTIONDETAILS JOIN AM_ManagedObject on AM_MSSQL_REPLSUBSCRIPTIONDETAILS.SUBSCRIPTIONID=AM_ManagedObject.RESOURCEID JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_MANAGEDOBJECT.RESOURCEID WHERE AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/*  487 */         rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/*  488 */         if (rs.next()) {
/*  489 */           maxtime = rs.getLong(1);
/*      */         }
/*  491 */         AMConnectionPool.closeResultSet(rs);
/*  492 */         if (maxtime == 0L) {
/*  493 */           return replDetails;
/*      */         }
/*  495 */         dataquery = new StringBuilder();
/*  496 */         dataquery.append("SELECT PUBLICATIONID,PUBLICATION,PUBLICATIONTYPE,PUBLISHERDB,STATUS,SUBSCRIPTIONSCOUNT,RUNNINGDISTRIBUTIONAGENTS,AVERAGELATENCY, WORSTLATENCY,LATENCYTHRESHOLD,EXPIRATIONTHRESHOLD FROM AM_MSSQL_REPLPUBLICATIONS JOIN AM_ManagedObject ON AM_MSSQL_REPLPUBLICATIONS.PUBLICATIONID=AM_ManagedObject.RESOURCEID JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_MANAGEDOBJECT.RESOURCEID AND AM_PARENTCHILDMAPPER.PARENTID=").append(resourceid).append("  AND COLLECTIONTIME=").append(maxtime).append(" ORDER BY PUBLICATIONTYPE DESC");
/*  497 */         rs = AMConnectionPool.executeQueryStmt(dataquery.toString());
/*  498 */         ArrayList publicationsList = new ArrayList();
/*  499 */         while (rs.next()) {
/*  500 */           Properties publicationDetails = new Properties();
/*  501 */           publicationDetails.setProperty("PUBLICATIONID", rs.getString("PUBLICATIONID"));
/*  502 */           publicationDetails.setProperty("PUBLICATION", rs.getString("PUBLICATION"));
/*  503 */           publicationDetails.setProperty("PUBLICATIONTYPE", rs.getString("PUBLICATIONTYPE"));
/*  504 */           publicationDetails.setProperty("PUBLISHERDB", rs.getString("PUBLISHERDB"));
/*  505 */           publicationDetails.setProperty("STATUS", rs.getString("STATUS"));
/*  506 */           if (rs.getString("SUBSCRIPTIONSCOUNT") != null) {
/*  507 */             publicationDetails.setProperty("SUBSCRIPTIONSCOUNT", rs.getString("SUBSCRIPTIONSCOUNT"));
/*      */           } else {
/*  509 */             publicationDetails.setProperty("SUBSCRIPTIONSCOUNT", "0");
/*      */           }
/*  511 */           publicationDetails.setProperty("RUNNINGDISTRIBUTIONAGENTS", rs.getString("RUNNINGDISTRIBUTIONAGENTS"));
/*  512 */           if ((rs.getString("AVERAGELATENCY") != null) && (!rs.getString("AVERAGELATENCY").equalsIgnoreCase("null"))) {
/*  513 */             publicationDetails.setProperty("AVERAGELATENCY", rs.getString("AVERAGELATENCY"));
/*      */           } else {
/*  515 */             publicationDetails.setProperty("AVERAGELATENCY", "-");
/*      */           }
/*  517 */           if ((rs.getString("WORSTLATENCY") != null) && (!rs.getString("WORSTLATENCY").equalsIgnoreCase("null"))) {
/*  518 */             publicationDetails.setProperty("WORSTLATENCY", rs.getString("WORSTLATENCY"));
/*      */           } else {
/*  520 */             publicationDetails.setProperty("WORSTLATENCY", "-");
/*      */           }
/*  522 */           if (rs.getString("LATENCYTHRESHOLD") != null) {
/*  523 */             publicationDetails.setProperty("LATENCYTHRESHOLD", rs.getString("LATENCYTHRESHOLD"));
/*      */           } else {
/*  525 */             publicationDetails.setProperty("LATENCYTHRESHOLD", "-");
/*      */           }
/*  527 */           if (rs.getString("EXPIRATIONTHRESHOLD") != null) {
/*  528 */             publicationDetails.setProperty("EXPIRATIONTHRESHOLD", rs.getString("EXPIRATIONTHRESHOLD"));
/*      */           } else {
/*  530 */             publicationDetails.setProperty("EXPIRATIONTHRESHOLD", "-");
/*      */           }
/*  532 */           publicationsList.add(publicationDetails);
/*      */         }
/*  534 */         replDetails.put("SQL-REPL-PUBLICATIONS", publicationsList);
/*      */       } catch (Exception e) {
/*  536 */         e.printStackTrace();
/*  537 */         AMLog.debug("Problem in getting publication details from the resource --> " + resourceid);
/*      */       }
/*      */       finally {}
/*      */       
/*      */       try
/*      */       {
/*  543 */         dataquery = new StringBuilder();
/*  544 */         dataquery.append("SELECT SUBSCRIPTIONID,SUBSCRIBER,SUBSCRIBERDB,PUBLICATION,PUBLICATIONTYPE,PUBLISHER,PUBLISHERDB,RUNNINGSTATUS,LASTSYNCHRONIZATION,DELIVERYRATE,DURATION,CONNECTIONTYPE,EXPIRATIONSTATUS,CURRENTLATENCY,PERFORMANCE FROM AM_MSSQL_REPLSUBSCRIPTIONDETAILS JOIN AM_ManagedObject ON AM_MSSQL_REPLSUBSCRIPTIONDETAILS.SUBSCRIPTIONID=AM_ManagedObject.RESOURCEID JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_MANAGEDOBJECT.RESOURCEID WHERE AM_PARENTCHILDMAPPER.PARENTID=").append(resourceid).append(" AND COLLECTIONTIME=").append(maxtime);
/*  545 */         rs = AMConnectionPool.executeQueryStmt(dataquery.toString());
/*  546 */         ArrayList al_TransactionalSubsc = new ArrayList();
/*  547 */         ArrayList al_SnapshotSubsc = new ArrayList();
/*  548 */         ArrayList al_MergeSubsc = new ArrayList();
/*  549 */         while (rs.next()) {
/*  550 */           if (rs.getString("PUBLICATIONTYPE").equalsIgnoreCase("Transactional publication")) {
/*  551 */             Properties subscriptionDetails = new Properties();
/*  552 */             subscriptionDetails.setProperty("SUBSCRIPTIONID", rs.getString("SUBSCRIPTIONID"));
/*  553 */             subscriptionDetails.setProperty("SUBSCRIBER", rs.getString("SUBSCRIBER"));
/*  554 */             subscriptionDetails.setProperty("SUBSCRIBERDB", rs.getString("SUBSCRIBERDB"));
/*  555 */             subscriptionDetails.setProperty("PUBLICATION", rs.getString("PUBLICATION"));
/*  556 */             subscriptionDetails.setProperty("PUBLISHERDB", rs.getString("PUBLISHERDB"));
/*  557 */             subscriptionDetails.setProperty("RUNNINGSTATUS", rs.getString("RUNNINGSTATUS"));
/*  558 */             subscriptionDetails.setProperty("EXPIRATIONSTATUS", rs.getString("EXPIRATIONSTATUS"));
/*  559 */             if (rs.getString("CURRENTLATENCY") != null) {
/*  560 */               subscriptionDetails.setProperty("CURRENTLATENCY", rs.getString("CURRENTLATENCY"));
/*      */             } else {
/*  562 */               subscriptionDetails.setProperty("CURRENTLATENCY", "-");
/*      */             }
/*  564 */             if ((rs.getString("PERFORMANCE") != null) && (!rs.getString("PERFORMANCE").equalsIgnoreCase("null"))) {
/*  565 */               subscriptionDetails.setProperty("PERFORMANCE", rs.getString("PERFORMANCE"));
/*      */             } else {
/*  567 */               subscriptionDetails.setProperty("PERFORMANCE", "-");
/*      */             }
/*  569 */             al_TransactionalSubsc.add(subscriptionDetails);
/*  570 */           } else if (rs.getString("PUBLICATIONTYPE").equalsIgnoreCase("Snapshot publication")) {
/*  571 */             Properties subscriptionDetails = new Properties();
/*  572 */             subscriptionDetails.setProperty("SUBSCRIPTIONID", rs.getString("SUBSCRIPTIONID"));
/*  573 */             subscriptionDetails.setProperty("SUBSCRIBER", rs.getString("SUBSCRIBER"));
/*  574 */             subscriptionDetails.setProperty("SUBSCRIBERDB", rs.getString("SUBSCRIBERDB"));
/*  575 */             subscriptionDetails.setProperty("PUBLICATION", rs.getString("PUBLICATION"));
/*  576 */             subscriptionDetails.setProperty("PUBLISHERDB", rs.getString("PUBLISHERDB"));
/*  577 */             subscriptionDetails.setProperty("RUNNINGSTATUS", rs.getString("RUNNINGSTATUS"));
/*  578 */             subscriptionDetails.setProperty("LASTSYNCHRONIZATION", rs.getString("LASTSYNCHRONIZATION"));
/*  579 */             subscriptionDetails.setProperty("EXPIRATIONSTATUS", rs.getString("EXPIRATIONSTATUS"));
/*  580 */             if (rs.getString("CURRENTLATENCY") != null) {
/*  581 */               subscriptionDetails.setProperty("CURRENTLATENCY", rs.getString("CURRENTLATENCY"));
/*      */             } else {
/*  583 */               subscriptionDetails.setProperty("CURRENTLATENCY", "-");
/*      */             }
/*  585 */             al_SnapshotSubsc.add(subscriptionDetails);
/*      */           } else {
/*  587 */             Properties subscriptionDetails = new Properties();
/*  588 */             subscriptionDetails.setProperty("SUBSCRIPTIONID", rs.getString("SUBSCRIPTIONID"));
/*  589 */             subscriptionDetails.setProperty("SUBSCRIBER", rs.getString("SUBSCRIBER"));
/*  590 */             subscriptionDetails.setProperty("SUBSCRIBERDB", rs.getString("SUBSCRIBERDB"));
/*  591 */             subscriptionDetails.setProperty("PUBLICATION", rs.getString("PUBLICATION"));
/*  592 */             subscriptionDetails.setProperty("PUBLISHERDB", rs.getString("PUBLISHERDB"));
/*  593 */             subscriptionDetails.setProperty("RUNNINGSTATUS", rs.getString("RUNNINGSTATUS"));
/*  594 */             if ((rs.getString("LASTSYNCHRONIZATION") != null) && (!rs.getString("LASTSYNCHRONIZATION").equalsIgnoreCase("null"))) {
/*  595 */               subscriptionDetails.setProperty("LASTSYNCHRONIZATION", rs.getString("LASTSYNCHRONIZATION"));
/*      */             }
/*  597 */             if (rs.getString("DELIVERYRATE") != null) {
/*  598 */               subscriptionDetails.setProperty("DELIVERYRATE", df.format(Double.parseDouble(rs.getString("DELIVERYRATE"))));
/*      */             }
/*  600 */             if (rs.getString("DURATION") != null) {
/*  601 */               subscriptionDetails.setProperty("DURATION", rs.getString("DURATION"));
/*      */             } else {
/*  603 */               subscriptionDetails.setProperty("DURATION", "-");
/*      */             }
/*  605 */             subscriptionDetails.setProperty("CONNECTIONTYPE", rs.getString("CONNECTIONTYPE"));
/*  606 */             subscriptionDetails.setProperty("EXPIRATIONSTATUS", rs.getString("EXPIRATIONSTATUS"));
/*  607 */             if (rs.getString("CURRENTLATENCY") != null) {
/*  608 */               subscriptionDetails.setProperty("CURRENTLATENCY", rs.getString("CURRENTLATENCY"));
/*      */             } else {
/*  610 */               subscriptionDetails.setProperty("CURRENTLATENCY", "-");
/*      */             }
/*  612 */             if ((rs.getString("PERFORMANCE") != null) && (!rs.getString("PERFORMANCE").equalsIgnoreCase("null"))) {
/*  613 */               subscriptionDetails.setProperty("PERFORMANCE", rs.getString("PERFORMANCE"));
/*      */             } else {
/*  615 */               subscriptionDetails.setProperty("PERFORMANCE", "-");
/*      */             }
/*  617 */             al_MergeSubsc.add(subscriptionDetails);
/*      */           }
/*      */         }
/*  620 */         replDetails.put("SQL-REPL-TRANSACTIONALSUBSCRIPTIONS", al_TransactionalSubsc);
/*  621 */         replDetails.put("SQL-REPL-SNAPSHOTSUBSCRIPTIONS", al_SnapshotSubsc);
/*  622 */         replDetails.put("SQL-REPL-MERGESUBSCRIPTIONS", al_MergeSubsc);
/*      */       } catch (Exception e) {
/*  624 */         e.printStackTrace();
/*  625 */         AMLog.debug("Problem in getting subscription details from the resource --> " + resourceid);
/*      */       }
/*      */       finally {}
/*      */       
/*      */       try
/*      */       {
/*  631 */         dataquery = new StringBuilder();
/*  632 */         dataquery.append("SELECT DISTINCT AM_MSSQL_REPLAGENTDETAILS.RESOURCEID,AM_MSSQL_REPLAGENTDETAILS.AGENTID,AM_MSSQL_REPLAGENTDETAILS.AGENT,AM_MSSQL_REPLAGENTDETAILS.AGENTNAME,AM_MSSQL_REPLAGENTDETAILS.PUBLICATION, AM_MSSQL_REPLAGENTDETAILS.PUBLISHERDB,AM_MSSQL_REPLAGENTDETAILS.LASTSTARTTIME,AM_MSSQL_REPLAGENTDETAILS.STATUS,AM_MSSQL_REPLAGENTDETAILS.DISTRIBUTIONDATABASE,DURATION,AM_MSSQL_REPLAGENTDETAILS.LASTACTION,AM_MSSQL_REPLAGENTDETAILS.DELIVERYRATE,AM_MSSQL_REPLAGENTDETAILS.DELIVERYLATENCY,AM_MSSQL_REPLAGENTDETAILS.TRANSACTIONS, AM_MSSQL_REPLAGENTDETAILS.COMMANDS,AM_MSSQL_REPLAGENTDETAILS.AVG_COMMANDS FROM AM_MSSQL_REPLAGENTDETAILS JOIN AM_ManagedObject ON AM_MSSQL_REPLAGENTDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID JOIN AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID WHERE AM_PARENTCHILDMAPPER.PARENTID=").append(resourceid);
/*  633 */         rs = AMConnectionPool.executeQueryStmt(dataquery.toString());
/*  634 */         ArrayList replAgentsList = new ArrayList();
/*  635 */         while (rs.next()) {
/*  636 */           Properties replAgentDetails = new Properties();
/*  637 */           replAgentDetails.setProperty("AGENTID", rs.getString("AGENTID"));
/*  638 */           replAgentDetails.setProperty("RESOURCEID", rs.getString("RESOURCEID"));
/*  639 */           replAgentDetails.setProperty("AGENT", rs.getString("AGENT"));
/*  640 */           replAgentDetails.setProperty("AGENTNAME", rs.getString("AGENTNAME"));
/*  641 */           if (rs.getString("PUBLICATION") != null) {
/*  642 */             replAgentDetails.setProperty("PUBLICATION", rs.getString("PUBLICATION"));
/*      */           }
/*  644 */           if (rs.getString("PUBLISHERDB") != null) {
/*  645 */             replAgentDetails.setProperty("PUBLISHERDB", rs.getString("PUBLISHERDB"));
/*      */           }
/*  647 */           if ((rs.getString("LASTSTARTTIME") != null) && (!rs.getString("LASTSTARTTIME").equals("null"))) {
/*  648 */             replAgentDetails.setProperty("LASTSTARTTIME", rs.getString("LASTSTARTTIME"));
/*      */           } else {
/*  650 */             replAgentDetails.setProperty("LASTSTARTTIME", "-");
/*      */           }
/*  652 */           replAgentDetails.setProperty("STATUS", rs.getString("STATUS"));
/*  653 */           if ((rs.getString("DISTRIBUTIONDATABASE") != null) && (!rs.getString("DISTRIBUTIONDATABASE").equals("null"))) {
/*  654 */             replAgentDetails.setProperty("DISTRIBUTIONDATABASE", rs.getString("DISTRIBUTIONDATABASE"));
/*      */           } else {
/*  656 */             replAgentDetails.setProperty("DISTRIBUTIONDATABASE", "-");
/*      */           }
/*  658 */           if ((rs.getString("DURATION") != null) && (!rs.getString("DURATION").equals("null"))) {
/*  659 */             replAgentDetails.setProperty("DURATION", rs.getString("DURATION"));
/*      */           } else {
/*  661 */             replAgentDetails.setProperty("DURATION", "-");
/*      */           }
/*  663 */           if ((rs.getString("LASTACTION") != null) && (!rs.getString("LASTACTION").equals("null"))) {
/*  664 */             replAgentDetails.setProperty("LASTACTION", rs.getString("LASTACTION"));
/*      */           } else {
/*  666 */             replAgentDetails.setProperty("LASTACTION", "-");
/*      */           }
/*  668 */           if ((rs.getString("DELIVERYRATE") != null) && (!rs.getString("DELIVERYRATE").equalsIgnoreCase("null"))) {
/*  669 */             replAgentDetails.setProperty("DELIVERYRATE", df.format(Double.parseDouble(rs.getString("DELIVERYRATE"))));
/*      */           } else {
/*  671 */             replAgentDetails.setProperty("DELIVERYRATE", "-");
/*      */           }
/*  673 */           if ((rs.getString("DELIVERYLATENCY") != null) && (!rs.getString("DELIVERYLATENCY").equalsIgnoreCase("null"))) {
/*  674 */             replAgentDetails.setProperty("DELIVERYLATENCY", rs.getString("DELIVERYLATENCY"));
/*      */           } else {
/*  676 */             replAgentDetails.setProperty("DELIVERYLATENCY", "-");
/*      */           }
/*  678 */           if ((rs.getString("TRANSACTIONS") != null) && (!rs.getString("TRANSACTIONS").equals("null"))) {
/*  679 */             replAgentDetails.setProperty("TRANSACTIONS", rs.getString("TRANSACTIONS"));
/*      */           } else {
/*  681 */             replAgentDetails.setProperty("TRANSACTIONS", "-");
/*      */           }
/*  683 */           if ((rs.getString("COMMANDS") != null) && (!rs.getString("COMMANDS").equals("null"))) {
/*  684 */             replAgentDetails.setProperty("COMMANDS", rs.getString("COMMANDS"));
/*      */           } else {
/*  686 */             replAgentDetails.setProperty("COMMANDS", "-");
/*      */           }
/*  688 */           if ((rs.getString("AVG_COMMANDS") != null) && (!rs.getString("AVG_COMMANDS").equals("null"))) {
/*  689 */             replAgentDetails.setProperty("AVG_COMMANDS", rs.getString("AVG_COMMANDS"));
/*      */           } else {
/*  691 */             replAgentDetails.setProperty("AVG_COMMANDS", "-");
/*      */           }
/*  693 */           replAgentsList.add(replAgentDetails);
/*      */         }
/*  695 */         replDetails.put("SQL-REPL-AGENTDETAILS", replAgentsList);
/*      */       } catch (Exception e) {
/*  697 */         e.printStackTrace();
/*  698 */         AMLog.debug("Problem in getting replication agent details from the resource --> " + resourceid);
/*      */       }
/*      */       finally {}
/*      */     }
/*      */     catch (Exception e) {
/*  703 */       e.printStackTrace();
/*  704 */       AMLog.fatal("MSSQL : Problem in fetching the data for Performance", e);
/*      */     } finally {
/*  706 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  708 */     return replDetails;
/*      */   }
/*      */   
/*      */   public List getLogShippingDetails(int resourceid) {
/*  712 */     List logShipping = new ArrayList();
/*  713 */     ResultSet rs = null;
/*      */     try {
/*  715 */       rs = AMConnectionPool.executeQueryStmt("select DBNAME, AGENTTYPE, STATUS, TIMESINCEBACKUP, LOGTIME, ERRORLOGTIME, ERRORMESSAGE from AM_MSSQL_LOGSHIPPING where COLLECTIONTIME in (select max(COLLECTIONTIME) from AM_MSSQL_LOGSHIPPING where RESOURCEID=" + resourceid + ") and RESOURCEID=" + resourceid + " order by DBNAME, LOGTIME");
/*  716 */       while (rs.next()) {
/*  717 */         Map h = new HashMap();
/*  718 */         h.put("DBNAME", rs.getString("DBNAME"));
/*      */         
/*      */ 
/*  721 */         String agentType = new String();
/*  722 */         switch (rs.getInt("AGENTTYPE")) {
/*      */         case 0: 
/*  724 */           agentType = "Backup";
/*  725 */           break;
/*      */         case 1: 
/*  727 */           agentType = "Copy";
/*  728 */           break;
/*      */         case 2: 
/*  730 */           agentType = "Restore";
/*  731 */           break;
/*      */         default: 
/*  733 */           agentType = "Unknown";
/*      */         }
/*  735 */         h.put("AGENTTYPE", agentType);
/*      */         
/*      */ 
/*  738 */         String status = new String();
/*  739 */         switch (rs.getInt("STATUS")) {
/*      */         case 0: 
/*  741 */           status = "Starting";
/*  742 */           break;
/*      */         case 1: 
/*  744 */           status = "Running";
/*  745 */           break;
/*      */         case 2: 
/*  747 */           status = "Success";
/*  748 */           break;
/*      */         case 3: 
/*  750 */           status = "Error";
/*  751 */           break;
/*      */         case 4: 
/*  753 */           status = "Warning";
/*  754 */           break;
/*      */         default: 
/*  756 */           status = "Unknown";
/*      */         }
/*  758 */         h.put("STATUS", status);
/*      */         
/*  760 */         h.put("TIMESINCEBACKUP", Long.valueOf(rs.getLong("TIMESINCEBACKUP")));
/*  761 */         String logTime = rs.getString("LOGTIME");
/*  762 */         if ((logTime != null) && (logTime.indexOf(".") != -1)) {
/*  763 */           logTime = logTime.substring(0, logTime.lastIndexOf("."));
/*      */         }
/*  765 */         h.put("LOGTIME", logTime);
/*  766 */         String errorLogTime = rs.getString("ERRORLOGTIME");
/*  767 */         if ((errorLogTime != null) && (!errorLogTime.equals("1900-01-01 00:00:00.0")) && (errorLogTime.indexOf(".") != -1)) {
/*  768 */           errorLogTime = errorLogTime.substring(0, errorLogTime.lastIndexOf("."));
/*      */         } else {
/*  770 */           errorLogTime = "";
/*      */         }
/*  772 */         h.put("ERRORLOGTIME", errorLogTime);
/*  773 */         h.put("ERRORMESSAGE", rs.getString("ERRORMESSAGE"));
/*  774 */         logShipping.add(h);
/*      */       }
/*      */     } catch (Exception e) {
/*  777 */       e.printStackTrace();
/*      */     } finally {
/*  779 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  781 */     return logShipping;
/*      */   }
/*      */   
/*      */   public ActionForward getMsSQLPerformanceDetailsFilterBy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  786 */     ActionErrors errors = new ActionErrors();
/*  787 */     ResultSet results = null;
/*  788 */     String resourceid = request.getParameter("resourceid");
/*  789 */     Connection con = null;
/*  790 */     Statement stmt = null;
/*  791 */     String topqrycnt = request.getParameter("topqrycnt");
/*      */     try {
/*  793 */       if ((topqrycnt != null) && (!topqrycnt.equals("polledValues"))) {
/*  794 */         con = getSqlDBConnection(Integer.parseInt(resourceid));
/*  795 */         if (con == null) {
/*  796 */           topqrycnt = "polledValues";
/*      */         } else {
/*  798 */           stmt = con.createStatement();
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/*  802 */       topqrycnt = "polledValues";
/*  803 */       e.printStackTrace();
/*      */     }
/*      */     try {
/*  806 */       String database = request.getParameter("database");
/*  807 */       String period = request.getParameter("period");
/*  808 */       String hrefname = request.getParameter("hrefname");
/*  809 */       String fromDate = request.getParameter("fromDate");
/*  810 */       String toDate = request.getParameter("toDate");
/*      */       
/*  812 */       AMLog.debug("Parameters for mssql performance report =====>> database : " + database + " , resourceid : " + resourceid + " , period : " + period + " , hrefname : " + hrefname + " , fromDate : " + fromDate + " , toDate : " + toDate + " , polledValues : " + topqrycnt);
/*  813 */       if ((hrefname == null) || (hrefname.equals(""))) {
/*  814 */         hrefname = "QUERYBYCPU";
/*      */       }
/*  816 */       ArrayList performanceData = null;
/*      */       
/*  818 */       StringBuilder query = new StringBuilder();
/*      */       Properties waitstats;
/*  820 */       if (!MsSQLAction.sqlVersion.equals("sql2000"))
/*      */       {
/*  822 */         performanceData = new ArrayList();
/*  823 */         if ((period != null) && (!period.equals("")) && (period.equals("4"))) {
/*  824 */           if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/*  825 */             if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */             {
/*  827 */               Calendar ucal = Calendar.getInstance();
/*  828 */               ucal.setTimeInMillis(0L);
/*  829 */               String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*  830 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  831 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*  832 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  833 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*  834 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  835 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*  836 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  837 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName, ").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*  838 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  839 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*  840 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  841 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*  842 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  843 */                 query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */               }
/*  845 */               AMLog.debug("MSSQLPerformance Report Query (Custom Period) : " + query.toString());
/*  846 */               results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */             } else {
/*  848 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  849 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  850 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  851 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  852 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  853 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/*  854 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  855 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  856 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  857 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageTimeBlocked DESC");
/*  858 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  859 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/*  860 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  861 */                 query.append("SELECT TOP ").append(topqrycnt).append(" creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' ORDER BY \"Avg Exec Time in ms\" DESC");
/*      */               }
/*  863 */               AMLog.debug("MSSQLPerformance Report Query (Custom Period)SQL : " + query.toString());
/*  864 */               results = stmt.executeQuery(query.toString());
/*      */             }
/*      */           }
/*  867 */           else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/*  869 */             Calendar ucal = Calendar.getInstance();
/*  870 */             ucal.setTimeInMillis(0L);
/*  871 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/*  873 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  874 */               query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*  875 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  876 */               query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*  877 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  878 */               query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*  879 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  880 */               query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*  881 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  882 */               query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*  883 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  884 */               query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */             }
/*  886 */             AMLog.debug("MSSQLPerformance Report Query (Custom Period DB) :" + query.toString());
/*  887 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           } else {
/*  889 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  890 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  891 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  892 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  893 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  894 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/*  895 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  896 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  897 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  898 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/*  899 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  900 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */             }
/*  902 */             AMLog.debug("MSSQLPerformance Report Query (Custom Period DB)SQL :" + query.toString());
/*  903 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */         }
/*  906 */         else if ((period != null) && (!period.equals("")) && (!period.equals("Execution Time"))) {
/*  907 */           long[] timeStamp = null;
/*  908 */           timeStamp = ReportUtilities.getTimeStamp(period);
/*  909 */           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*  910 */           fromDate = sdf.format(new Date(timeStamp[0]));
/*  911 */           toDate = sdf.format(new Date(timeStamp[1]));
/*  912 */           if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/*  913 */             if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */             {
/*  915 */               Calendar ucal = Calendar.getInstance();
/*  916 */               ucal.setTimeInMillis(0L);
/*  917 */               String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */               
/*  919 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  920 */                 if (period.equals("0")) {
/*  921 */                   query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */                 } else {
/*  923 */                   query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */                 }
/*  925 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  926 */                 if (period.equals("0")) {
/*  927 */                   query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */                 } else {
/*  929 */                   query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageCPUused DESC");
/*      */                 }
/*  931 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  932 */                 if (period.equals("0")) {
/*  933 */                   query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageCLRTime DESC");
/*      */                 } else {
/*  935 */                   query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */                 }
/*  937 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE"))
/*      */               {
/*  939 */                 if (period.equals("0")) {
/*  940 */                   query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  942 */                   query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*  944 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  945 */                 if (period.equals("0")) {
/*  946 */                   query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageTimeBlocked DESC");
/*      */                 } else {
/*  948 */                   query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */                 }
/*  950 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR"))
/*      */               {
/*  952 */                 if (period.equals("0")) {
/*  953 */                   query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  955 */                   query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*  957 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ"))
/*      */               {
/*  959 */                 if (period.equals("0")) {
/*  960 */                   query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 } else {
/*  962 */                   query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,last_execution_time from (select AVGEXETIME,MAXEXETIME,MINEXETIME,NOOFEXECS,QUERY,").append(formatTime(unixStartTime)).append("AS last_execution_time,RESOURCEID,COLLECTIONTIME  from AM_MSSQL_QRYBYSRQ)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and RESOURCEID=").append(resourceid);
/*      */                 }
/*      */               }
/*  965 */               AMLog.debug("MSSQLPerformance Report Query period : (" + period + "): " + query.toString());
/*  966 */               results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */             } else {
/*  968 */               if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  969 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageIO DESC");
/*  970 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/*  971 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/*  972 */               } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/*  973 */                 query.append("SELECT TOP ").append(topqrycnt).append("  (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount'= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/*  974 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/*  975 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY Executioncount DESC");
/*  976 */               } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/*  977 */                 query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and attribute = 'dbid' ORDER BY AverageTimeBlocked DESC");
/*  978 */               } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/*  979 */                 query.append("SELECT TOP ").append(topqrycnt).append("  (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/*  980 */               } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/*  981 */                 query.append("SELECT TOP ").append(topqrycnt).append("  creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' ORDER BY \"Avg Exec Time in ms\" DESC");
/*      */               }
/*  983 */               AMLog.debug("MSSQLPerformance Report Query period : (" + period + ") SQL: " + query.toString());
/*  984 */               results = stmt.executeQuery(query.toString());
/*      */             }
/*      */           }
/*  987 */           else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/*  989 */             Calendar ucal = Calendar.getInstance();
/*  990 */             ucal.setTimeInMillis(0L);
/*  991 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/*  993 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/*  994 */               if (period.equals("0")) {
/*  995 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageIO DESC");
/*      */               } else {
/*  997 */                 query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append("  ORDER BY AverageIO DESC");
/*      */               }
/*  999 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1000 */               if (period.equals("0")) {
/* 1001 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */               } else {
/* 1003 */                 query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCPUused DESC");
/*      */               }
/* 1005 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1006 */               if (period.equals("0")) {
/* 1007 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */               } else {
/* 1009 */                 query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageCLRTime DESC");
/*      */               }
/* 1011 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1012 */               if (period.equals("0")) {
/* 1013 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               } else {
/* 1015 */                 query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               }
/* 1017 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1018 */               if (period.equals("0")) {
/* 1019 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */               } else {
/* 1021 */                 query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" ORDER BY AverageTimeBlocked DESC");
/*      */               }
/* 1023 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1024 */               if (period.equals("0")) {
/* 1025 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               } else {
/* 1027 */                 query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid);
/*      */               }
/*      */             }
/* 1030 */             AMLog.debug("MSSQLPerformance Report Query period : (" + period + " database " + database + "): " + query.toString());
/* 1031 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           } else {
/* 1033 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1034 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 1035 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1036 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/* 1037 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1038 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/* 1039 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1040 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/* 1041 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1042 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("')ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/* 1043 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1044 */               query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE last_execution_time>='").append(fromDate).append("' and last_execution_time<='").append(toDate).append("' and cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */             }
/* 1046 */             AMLog.debug("MSSQLPerformance Report Query period : (" + period + " database " + database + ") SQL: " + query.toString());
/* 1047 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */           
/*      */         }
/* 1051 */         else if ((database == null) || (database.equalsIgnoreCase("All")) || (database.equalsIgnoreCase("Database"))) {
/* 1052 */           if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */           {
/* 1054 */             Calendar ucal = Calendar.getInstance();
/* 1055 */             ucal.setTimeInMillis(0L);
/* 1056 */             String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */             
/* 1058 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1059 */               query.append(" select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYIO where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYIO", resourceid)).append(" ORDER BY AVGIO DESC");
/* 1060 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1061 */               query.append(" select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYCPU where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCPU", resourceid)).append(" ORDER BY AVGCPU DESC");
/* 1062 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1063 */               query.append(" select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYCLR where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCLR", resourceid)).append(" ORDER BY AVGCLR DESC");
/* 1064 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1065 */               query.append(" select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOE where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOE", resourceid));
/* 1066 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1067 */               query.append(" select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYMOB where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOB", resourceid)).append(" ORDER BY AVGTB DESC");
/* 1068 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1069 */               query.append(" select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYLPR where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYLPR", resourceid));
/* 1070 */             } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/* 1071 */               query.append(" select AVGEXETIME as \"Avg Exec Time in ms\",MAXEXETIME as \"MaxExecTime in ms\",MINEXETIME as \"MinExecTime in ms\",NOOFEXECS as NumberOfExecs,QUERY as query_text,").append(formatTime(unixStartTime)).append(" AS last_execution_time from AM_MSSQL_QRYBYSRQ where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYSRQ", resourceid)).append(" ORDER BY AVGEXETIME DESC");
/* 1072 */             } else if (hrefname.equalsIgnoreCase("MEMORY")) {
/* 1073 */               query.append("select RESOURCEID ,SINGLEPAGES,MULTIPAGES,VIRTUALMEMR,VIRTUALMEMC,AWEALLOCATED,SHAREDMEMR,SHAREDMEMC,COMPONENTNAME  from AM_MSSQL_MEMORYUSEDBYCOMP where COLLECTIONTIME =(select max(COLLECTIONTIME) from AM_MSSQL_MEMORYUSEDBYCOMP where resourceid=").append(resourceid).append(") order by SINGLEPAGES desc");
/* 1074 */             } else if (hrefname.equals("QUERYBYCMI")) {
/* 1075 */               query.append(" select * from AM_MSSQL_QRYBYCMI where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCMI", resourceid));
/* 1076 */             } else if (hrefname.equals("WAITSTATS")) {
/* 1077 */               query.append(" select WAITTYPE, WAIT, WAITCOUNT, AVGWAIT, SIGNALWAIT from AM_MSSQL_WAITSTATS where RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_WAITSTATS", resourceid)).append(" order by WAIT desc");
/*      */             }
/* 1079 */             AMLog.debug("MSSQLPerformance Report Query period1 : (" + period + " database " + database + "): " + query.toString());
/* 1080 */             results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */           }
/*      */           else {
/* 1083 */             if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1084 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY AverageIO DESC");
/* 1085 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1086 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY AverageCPUused DESC");
/* 1087 */             } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1088 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE total_clr_time <> 0 ORDER BY AverageCLRTime DESC");
/* 1089 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1090 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid' ORDER BY Executioncount DESC");
/* 1091 */             } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1092 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa where attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/* 1093 */             } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1094 */               query.append("SELECT TOP ").append(topqrycnt).append(" (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE cp.plan_handle=qs.plan_handle ORDER BY Planusage ASC");
/* 1095 */             } else if (hrefname.equalsIgnoreCase("QUERYBYSRQ")) {
/* 1096 */               query.append("SELECT TOP ").append(topqrycnt).append("  creation_time,(total_elapsed_time/execution_count)/1000 AS \"Avg Exec Time in ms\", max_elapsed_time/1000 AS \"MaxExecTime in ms\" , min_elapsed_time/1000 AS \"MinExecTime in ms\" , (total_worker_time/execution_count)/1000 AS \"Avg CPU Time in ms\", qs.execution_count AS NumberOfExecs , (total_logical_writes+total_logical_Reads)/execution_count AS \"Avg Logical IOs\" , max_logical_reads AS MaxLogicalReads , min_logical_reads AS MinLogicalReads , max_logical_writes AS MaxLogicalWrites , min_logical_writes AS MinLogicalWrites,(SELECT SUBSTRING(text,(statement_start_offset/2)+1,((CASE WHEN statement_end_offset = -1 then LEN(CONVERT(nvarchar(max), text)) * 2 ELSE statement_end_offset end -statement_start_offset)/2 )+1) FROM sys.dm_exec_sql_text(sql_handle) ) AS query_text,qs.last_execution_time FROM sys.dm_exec_query_stats qs ORDER BY \"Avg Exec Time in ms\" DESC");
/* 1097 */             } else if (hrefname.equalsIgnoreCase("MEMORY")) {
/* 1098 */               query.append("SELECT TOP ").append(topqrycnt).append(" TYPE as COMPONENTNAME,SUM(single_pages_kb) as SINGLEPAGES,SUM(multi_pages_kb)as MULTIPAGES,SUM(virtual_memory_reserved_kb)as VIRTUALMEMR,SUM(virtual_memory_committed_kb)as VIRTUALMEMC,SUM(awe_allocated_kb)as AWEALLOCATED,SUM(shared_memory_reserved_kb)as SHAREDMEMR,SUM(shared_memory_committed_kb)as SHAREDMEMC FROM sys.dm_os_memory_clerks GROUP BY type ORDER BY SUM(single_pages_kb) DESC");
/* 1099 */               if (MsSQLAction.sqlVersion.equals("sql2012")) {
/* 1100 */                 query.append("SELECT TOP ").append(topqrycnt).append("  type as COMPONENTNAME,SUM(pages_kb) as SPA Mem,SUM(pages_kb)as MPA Mem,SUM(virtual_memory_reserved_kb)as VMR,SUM(virtual_memory_committed_kb)as VMC,SUM(awe_allocated_kb)as AWE,SUM(shared_memory_reserved_kb)as SMR,SUM(shared_memory_committed_kb)as SMC FROM sys.dm_os_memory_clerks GROUP BY type ORDER BY SUM(pages_kb) DESC");
/*      */               }
/* 1102 */             } else if (hrefname.equals("QUERYBYCMI")) {
/* 1103 */               query.append("SELECT  TOP ").append(topqrycnt).append(" TOTALCOST  = ROUND(avg_total_user_cost * avg_user_impact * (user_seeks + user_scans),0) , avg_user_impact as AVGUSERIMPT, TBNAME = statement  , EQUALITYUSG = equality_columns , INEQUALITYUSG = inequality_columns , INCLUDECOLMS = included_columns FROM  sys.dm_db_missing_index_groups g INNER JOIN    sys.dm_db_missing_index_group_stats s  ON s.group_handle = g.index_group_handle  INNER JOIN    sys.dm_db_missing_index_details d   ON d.index_handle = g.index_handle ORDER BY TotalCost DESC");
/* 1104 */             } else if (hrefname.equals("WAITSTATS")) {
/* 1105 */               query.append("SELECT top ").append(topqrycnt).append(" wait_type as WAITTYPE, wait_time_ms AS WAIT, waiting_tasks_count AS WAITCOUNT,wait_time_ms / waiting_tasks_count AS AVGWAIT,signal_wait_time_ms AS SIGNALWAIT").append(" FROM sys.dm_os_wait_stats WHERE wait_type NOT IN ('BROKER_EVENTHANDLER','BROKER_RECEIVE_WAITFOR','BROKER_TASK_STOP','BROKER_TO_FLUSH','BROKER_TRANSMITTER','CHECKPOINT_QUEUE',").append("'CHKPT','CLR_AUTO_EVENT','CLR_MANUAL_EVENT','CLR_SEMAPHORE','DBMIRROR_DBM_EVENT','DBMIRROR_EVENTS_QUEUE','DBMIRROR_WORKER_QUEUE','DBMIRRORING_CMD','DIRTY_PAGE_POLL','DISPATCHER_QUEUE_SEMAPHORE',").append("'EXECSYNC','FSAGENT','FT_IFTS_SCHEDULER_IDLE_WAIT','FT_IFTSHC_MUTEX','HADR_CLUSAPI_CALL','HADR_FILESTREAM_IOMGR_IOCOMPLETION','HADR_LOGCAPTURE_WAIT','HADR_NOTIFICATION_DEQUEUE','HADR_TIMER_TASK',").append("'HADR_WORK_QUEUE','KSOURCE_WAKEUP','LAZYWRITER_SLEEP','LOGMGR_QUEUE','ONDEMAND_TASK_QUEUE','PWAIT_ALL_COMPONENTS_INITIALIZED','QDS_PERSIST_TASK_MAIN_LOOP_SLEEP','SLEEP_MASTERDBREADY',").append("'QDS_CLEANUP_STALE_QUERIES_TASK_MAIN_LOOP_SLEEP','REQUEST_FOR_DEADLOCK_SEARCH','RESOURCE_QUEUE','SERVER_IDLE_CHECK','SLEEP_BPOOL_FLUSH','SLEEP_DBSTARTUP','SLEEP_DCOMSTARTUP',").append("'SLEEP_MASTERMDREADY','SLEEP_MASTERUPGRADED','SLEEP_MSDBSTARTUP','SLEEP_SYSTEMTASK','SLEEP_TASK','SLEEP_TEMPDBSTARTUP','SNI_HTTP_ACCEPT','SP_SERVER_DIAGNOSTICS_SLEEP','SQLTRACE_BUFFER_FLUSH',").append("'SQLTRACE_INCREMENTAL_FLUSH_SLEEP','SQLTRACE_WAIT_ENTRIES','WAIT_FOR_RESULTS','WAITFOR','WAITFOR_TASKSHUTDOWN','WAIT_XTP_HOST_WAIT','WAIT_XTP_OFFLINE_CKPT_NEW_LOG','WAIT_XTP_CKPT_CLOSE','XE_DISPATCHER_JOIN',").append("'XE_DISPATCHER_WAIT','XE_TIMER_EVENT') AND waiting_tasks_count > 0 ORDER BY wait_time_ms DESC");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1115 */             AMLog.debug("MSSQLPerformance Report Query1 period : (" + period + " database " + database + ") SQL: " + query.toString());
/* 1116 */             results = stmt.executeQuery(query.toString());
/*      */           }
/*      */         }
/* 1119 */         else if ((topqrycnt == null) || (topqrycnt.equals("polledValues")))
/*      */         {
/* 1121 */           Calendar ucal = Calendar.getInstance();
/* 1122 */           ucal.setTimeInMillis(0L);
/* 1123 */           String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */           
/* 1125 */           if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1126 */             query.append(" select AVGEXETIME,AverageIO,TotalIO,IndividualQuery,DATABASENAME,last_execution_time from (select AVGEXETIME,AVGIO as AverageIO,TOTALIO as TotalIO,INDQUERY as IndividualQuery,DATABASENAME as DATABASENAME,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYIO)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYIO", resourceid)).append(" ORDER BY AverageIO DESC");
/* 1127 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1128 */             query.append(" select AVGEXETIME,AverageCPUused,TotalCPUused,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCPU as AverageCPUused,TOTALCPU as TotalCPUused,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCPU)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCPU", resourceid)).append(" ORDER BY AverageCPUused DESC");
/* 1129 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1130 */             query.append(" select AVGEXETIME,AverageCLRTime,TotalCLRTime,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGCLR as AverageCLRTime,TOTALCLR as TotalCLRTime,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYCLR)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYCLR", resourceid)).append(" ORDER BY AverageCLRTime DESC");
/* 1131 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1132 */             query.append(" select AVGEXETIME,Executioncount,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,EXCCOUNT as Executioncount,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOE)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOE", resourceid));
/* 1133 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1134 */             query.append(" select AVGEXETIME,AverageTimeBlocked,TotalTimeBlocked,IndividualQuery,DatabaseName,last_execution_time from (select AVGEXETIME,AVGTB as AverageTimeBlocked,TOTALTB as TotalTimeBlocked,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYMOB)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYMOB", resourceid)).append(" ORDER BY AverageTimeBlocked DESC");
/* 1135 */           } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1136 */             query.append(" select AVGEXETIME,Planusage,IndividualQuery,DatabaseName,CACHEOBJTYPE,last_execution_time from (select AVGEXETIME,PLANUSAGE as Planusage,INDQUERY as IndividualQuery,DATABASENAME as DatabaseName,CACHEOBJTYPE as CACHEOBJTYPE,").append(formatTime(unixStartTime)).append(" AS last_execution_time,RESOURCEID,COLLECTIONTIME from AM_MSSQL_QRYBYLPR)pa where DATABASENAME like '").append(database).append("*' and RESOURCEID=").append(resourceid).append(" and COLLECTIONTIME=").append(getMaxTime("AM_MSSQL_QRYBYLPR", resourceid));
/*      */           }
/* 1138 */           AMLog.debug("MSSQLPerformance Report Query2 period : (" + period + " database " + database + "): " + query.toString());
/* 1139 */           results = AMConnectionPool.executeQueryStmt(query.toString());
/*      */         } else {
/* 1141 */           if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1142 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageIO = (total_logical_reads + total_logical_writes) / qs.execution_count,TotalIO = (total_logical_reads + total_logical_writes),Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageIO DESC");
/* 1143 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1144 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCPUused = total_worker_time / (qs.execution_count*1000) ,TotalCPUused = total_worker_time/1000,Executioncount = qs.execution_count,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END -qs.statement_start_offset)/2)+1),ParentQuery = qt.text,DatabaseName =COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time,attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY AverageCPUused DESC");
/* 1145 */           } else if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1146 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageCLRTime = total_clr_time / (execution_count*1000) ,TotalCLRTime = total_clr_time/1000 ,Executioncount= qs.execution_count ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1,((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2   ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid),qs.last_execution_time FROM sys.dm_exec_query_stats as qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt WHERE total_clr_time <> 0)ss where ss.DatabaseName like '").append(database).append("' ORDER BY AverageCLRTime DESC");
/* 1147 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1148 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Executioncount = execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid' ORDER BY Executioncount DESC");
/* 1149 */           } else if (hrefname.equalsIgnoreCase("QUERYBYMOB")) {
/* 1150 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,AverageTimeBlocked = (total_elapsed_time - total_worker_time) / (qs.execution_count*1000) ,TotalTimeBlocked = (total_elapsed_time - total_worker_time)/1000 ,Executioncount = qs.execution_count ,IndividualQuery = SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1 THEN LEN(CONVERT(NVARCHAR(MAX), qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName =  COALESCE(DB_NAME(qt.dbid),DB_NAME(CAST(pa.value as int))+'*','Resource'),qs.last_execution_time, attribute FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) as qt OUTER APPLY sys.dm_exec_plan_attributes(plan_handle) pa)ss where ss.DatabaseName like '").append(database).append("*' and attribute = 'dbid'  ORDER BY AverageTimeBlocked DESC");
/* 1151 */           } else if (hrefname.equalsIgnoreCase("QUERYBYLPR")) {
/* 1152 */             query.append("select TOP ").append(topqrycnt).append(" * from (SELECT (total_elapsed_time/execution_count)/1000 AS AVGEXETIME,Planusage = cp.usecounts ,IndividualQuery= SUBSTRING (qt.text,(qs.statement_start_offset/2)+1, ((CASE WHEN qs.statement_end_offset = -1  THEN LEN(CONVERT(NVARCHAR(MAX),qt.text)) * 2 ELSE qs.statement_end_offset END - qs.statement_start_offset)/2)+1) ,ParentQuery = qt.text ,DatabaseName = DB_NAME(qt.dbid) ,cp.cacheobjtype,qs.last_execution_time FROM sys.dm_exec_query_stats qs CROSS APPLY sys.dm_exec_sql_text(qs.sql_handle) AS qt INNER JOIN sys.dm_exec_cached_plans as cp on qs.plan_handle=cp.plan_handle WHERE cp.plan_handle=qs.plan_handle)ss where ss.DatabaseName like '").append(database).append("*' ORDER BY Planusage ASC");
/*      */           }
/* 1154 */           AMLog.debug("MSSQLPerformance Report Query2 period : (" + period + " database " + database + ") SQL: " + query.toString());
/* 1155 */           results = stmt.executeQuery(query.toString());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1160 */         if (hrefname.equalsIgnoreCase("QUERYBYIO")) {
/* 1161 */           int i = 0;
/* 1162 */           performanceData = new ArrayList();
/* 1163 */           while (results.next()) {
/* 1164 */             Properties byio = new Properties();
/* 1165 */             byio.setProperty("Q.No", ++i + "");
/* 1166 */             byio.setProperty("AVGIO", results.getString("AverageIO"));
/* 1167 */             byio.setProperty("TOTALIO", results.getString("TotalIO"));
/* 1168 */             byio.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1169 */             if (results.getString("DATABASENAME") != null) {
/* 1170 */               byio.setProperty("DATABASENAME", results.getString("DATABASENAME"));
/*      */             } else {
/* 1172 */               byio.setProperty("DATABASENAME", "");
/*      */             }
/* 1174 */             byio.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1175 */             byio.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1176 */             performanceData.add(byio);
/*      */           }
/* 1178 */           AMConnectionPool.closeStatement(results);
/* 1179 */         } else if (hrefname.equalsIgnoreCase("QUERYBYCPU")) {
/* 1180 */           int i = 0;
/* 1181 */           performanceData = new ArrayList();
/* 1182 */           while (results.next()) {
/* 1183 */             Properties bycpu = new Properties();
/* 1184 */             bycpu.setProperty("Q.No", i++ + "");
/* 1185 */             bycpu.setProperty("AVGCPU", results.getString("AverageCPUused"));
/* 1186 */             bycpu.setProperty("TOTALCPU", results.getString("TotalCPUused"));
/* 1187 */             bycpu.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1188 */             if (results.getString("DatabaseName") != null) {
/* 1189 */               bycpu.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */             }
/* 1191 */             bycpu.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1192 */             bycpu.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1193 */             performanceData.add(bycpu);
/*      */           }
/* 1195 */         } else { if (hrefname.equalsIgnoreCase("QUERYBYCLR")) {
/* 1196 */             performanceData = new ArrayList();
/* 1197 */             while (results.next()) {
/* 1198 */               Properties byclr = new Properties();
/* 1199 */               byclr.setProperty("AVGCLR", results.getString("AverageCLRTime"));
/* 1200 */               byclr.setProperty("TOTALCLR", results.getString("TotalCLRTime"));
/* 1201 */               byclr.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1202 */               if (results.getString("DatabaseName") != null) {
/* 1203 */                 byclr.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */               } else {
/* 1205 */                 byclr.setProperty("DATABASENAME", "");
/*      */               }
/* 1207 */               byclr.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1208 */               byclr.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1209 */               performanceData.add(byclr);
/*      */             } }
/* 1211 */           if (hrefname.equalsIgnoreCase("QUERYBYMOE")) {
/* 1212 */             int i = 0;
/* 1213 */             performanceData = new ArrayList();
/* 1214 */             while (results.next()) {
/* 1215 */               Properties bymoe = new Properties();
/* 1216 */               bymoe.setProperty("Q.No", ++i + "");
/* 1217 */               bymoe.setProperty("EXCCOUNT", String.valueOf(results.getInt("Executioncount")));
/* 1218 */               bymoe.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1219 */               if (results.getString("DatabaseName") != null) {
/* 1220 */                 bymoe.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */               } else {
/* 1222 */                 bymoe.setProperty("DATABASENAME", "");
/*      */               }
/* 1224 */               bymoe.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1225 */               bymoe.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1226 */               performanceData.add(bymoe);
/*      */             }
/* 1228 */           } else { if (hrefname.equals("QUERYBYMOB")) {
/* 1229 */               performanceData = new ArrayList();
/* 1230 */               while (results.next()) {
/* 1231 */                 Properties bymob = new Properties();
/* 1232 */                 bymob.setProperty("AVGTB", results.getString("AverageTimeBlocked"));
/* 1233 */                 bymob.setProperty("TOTALTB", results.getString("TotalTimeBlocked"));
/* 1234 */                 bymob.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1235 */                 if (results.getString("DatabaseName") != null) {
/* 1236 */                   bymob.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */                 } else {
/* 1238 */                   bymob.setProperty("DATABASENAME", "");
/*      */                 }
/* 1240 */                 bymob.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1241 */                 bymob.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1242 */                 performanceData.add(bymob);
/*      */               }
/*      */             }
/* 1245 */             if (hrefname.equals("QUERYBYLPR")) {
/* 1246 */               performanceData = new ArrayList();
/* 1247 */               while (results.next()) {
/* 1248 */                 Properties bylpr = new Properties();
/* 1249 */                 bylpr.setProperty("PLANUSAGE", results.getString("Planusage"));
/* 1250 */                 bylpr.setProperty("CACHEOBJTYPE", results.getString("CACHEOBJTYPE"));
/* 1251 */                 bylpr.setProperty("INDQUERY", results.getString("IndividualQuery"));
/* 1252 */                 if (results.getString("DatabaseName") != null) {
/* 1253 */                   bylpr.setProperty("DATABASENAME", results.getString("DatabaseName"));
/*      */                 } else {
/* 1255 */                   bylpr.setProperty("DATABASENAME", "");
/*      */                 }
/* 1257 */                 bylpr.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1258 */                 bylpr.setProperty("AVGEXETIME", results.getString("AVGEXETIME"));
/* 1259 */                 performanceData.add(bylpr);
/*      */               } }
/* 1261 */             if (hrefname.equals("QUERYBYSRQ")) {
/* 1262 */               performanceData = new ArrayList();
/* 1263 */               while (results.next()) {
/* 1264 */                 Properties bysrq = new Properties();
/* 1265 */                 bysrq.setProperty("AVGEXETIME", results.getString("Avg Exec Time in ms"));
/* 1266 */                 bysrq.setProperty("MAXEXETIME", results.getString("MaxExecTime in ms"));
/* 1267 */                 bysrq.setProperty("MINEXETIME", results.getString("MinExecTime in ms"));
/* 1268 */                 bysrq.setProperty("NOOFEXECS", results.getString("NumberOfExecs"));
/* 1269 */                 bysrq.setProperty("INDQUERY", results.getString("query_text") == null ? "" : results.getString("query_text"));
/* 1270 */                 bysrq.setProperty("LASTEXETIME", results.getString("last_execution_time"));
/* 1271 */                 performanceData.add(bysrq);
/*      */               } }
/* 1273 */             if (hrefname.equals("MEMORY")) {
/* 1274 */               performanceData = new ArrayList();
/* 1275 */               while (results.next()) {
/* 1276 */                 Properties mem = new Properties();
/* 1277 */                 mem.setProperty("TYPE", results.getString("COMPONENTNAME"));
/* 1278 */                 mem.setProperty("SINGLEPAGES", String.valueOf(results.getInt("SINGLEPAGES")));
/* 1279 */                 mem.setProperty("MULTIPAGES", String.valueOf(results.getInt("MULTIPAGES")));
/* 1280 */                 mem.setProperty("VIRTUALMEMR", String.valueOf(results.getInt("VIRTUALMEMR")));
/* 1281 */                 mem.setProperty("VIRTUALMEMC", String.valueOf(results.getInt("VIRTUALMEMC")));
/* 1282 */                 mem.setProperty("AWEALLOCATED", String.valueOf(results.getInt("AWEALLOCATED")));
/* 1283 */                 mem.setProperty("SHAREDMEMR", String.valueOf(results.getInt("SHAREDMEMR")));
/* 1284 */                 mem.setProperty("SHAREDMEMC", String.valueOf(results.getInt("SHAREDMEMC")));
/* 1285 */                 mem.setProperty("DBConID", String.valueOf(resourceid));
/*      */                 
/* 1287 */                 performanceData.add(mem);
/*      */               }
/*      */             }
/* 1290 */             if (hrefname.equalsIgnoreCase("QUERYBYCMI")) {
/* 1291 */               performanceData = new ArrayList();
/* 1292 */               while (results.next()) {
/* 1293 */                 Properties bycmi = new Properties();
/* 1294 */                 bycmi.setProperty("TOTALCOST", String.valueOf(results.getInt("TOTALCOST")));
/* 1295 */                 bycmi.setProperty("AVGUSERIMPT", results.getString("AVGUSERIMPT"));
/* 1296 */                 bycmi.setProperty("TBNAME", results.getString("TBNAME"));
/* 1297 */                 if (results.getString("EQUALITYUSG") != null) {
/* 1298 */                   bycmi.setProperty("EQUALITYUSG", results.getString("EQUALITYUSG"));
/*      */                 } else {
/* 1300 */                   bycmi.setProperty("EQUALITYUSG", "");
/*      */                 }
/* 1302 */                 if (results.getString("INEQUALITYUSG") != null) {
/* 1303 */                   bycmi.setProperty("INEQUALITYUSG", results.getString("INEQUALITYUSG"));
/*      */                 } else {
/* 1305 */                   bycmi.setProperty("INEQUALITYUSG", "");
/*      */                 }
/* 1307 */                 if (results.getString("INCLUDECOLMS") != null) {
/* 1308 */                   bycmi.setProperty("INCLUDECOLMS", results.getString("INCLUDECOLMS"));
/*      */                 } else {
/* 1310 */                   bycmi.setProperty("INCLUDECOLMS", "");
/*      */                 }
/* 1312 */                 performanceData.add(bycmi);
/*      */               } }
/* 1314 */             if (hrefname.equals("WAITSTATS")) {
/* 1315 */               performanceData = new ArrayList();
/* 1316 */               while (results.next()) {
/* 1317 */                 waitstats = new Properties();
/* 1318 */                 waitstats.put("WAITTYPE", results.getString("WAITTYPE"));
/* 1319 */                 waitstats.put("WAIT", String.valueOf(results.getLong("WAIT")));
/* 1320 */                 waitstats.put("WAITCOUNT", String.valueOf(results.getLong("WAITCOUNT")));
/* 1321 */                 waitstats.put("AVGWAIT", String.valueOf(results.getLong("AVGWAIT")));
/* 1322 */                 waitstats.put("SIGNALWAIT", String.valueOf(results.getLong("SIGNALWAIT")));
/*      */                 
/* 1324 */                 performanceData.add(waitstats);
/*      */               }
/*      */             } } }
/* 1327 */         request.setAttribute(hrefname, performanceData);
/*      */       } else {
/* 1329 */         request.setAttribute(hrefname, new ArrayList());
/*      */       }
/* 1331 */       AMLog.debug("Peformance query : " + query);
/* 1332 */       request.setAttribute("database", database);
/* 1333 */       request.setAttribute("selectedPeriod", period);
/* 1334 */       request.setAttribute("selectedDb", database);
/* 1335 */       request.setAttribute("fromDate", fromDate);
/* 1336 */       request.setAttribute("toDate", toDate);
/* 1337 */       request.setAttribute("hrefname", hrefname);
/* 1338 */       request.setAttribute("topqrycnt", topqrycnt);
/* 1339 */       return mapping.findForward("performance");
/*      */     } catch (Exception e) {
/* 1341 */       errors.add("org.apache.struts.action.ERROR", new ActionMessage(e.getMessage()));
/* 1342 */       e.printStackTrace();
/*      */     } finally {
/* 1344 */       if (results != null) {
/*      */         try {
/* 1346 */           results.close();
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
/* 1366 */     saveErrors(request, errors);
/* 1367 */     String o = request.getParameter("noredirect");
/* 1368 */     if ((o != null) && (o.equals("false"))) {
/* 1369 */       return mapping.findForward("performance");
/*      */     }
/* 1371 */     return mapping.findForward("performance");
/*      */   }
/*      */   
/*      */   private String formatTime(String unixStartTime) {
/* 1375 */     if (DBQueryUtil.isMssql())
/* 1376 */       return "DATEADD(ss,LASTEXETIME/1000,'" + unixStartTime + "')";
/* 1377 */     if (DBQueryUtil.isMysql())
/* 1378 */       return "FROM_UNIXTIME(LASTEXETIME/1000)";
/* 1379 */     if (DBQueryUtil.isPgsql()) {
/* 1380 */       return "to_timestamp(LASTEXETIME/1000)::timestamp without time zone";
/*      */     }
/* 1382 */     return null;
/*      */   }
/*      */   
/*      */   private String formatCreationDate() {
/* 1386 */     if (DBQueryUtil.isMssql())
/* 1387 */       return "DATEADD(ss,CREATIONDATE/1000,'1970-01-01')";
/* 1388 */     if (DBQueryUtil.isMysql())
/* 1389 */       return "FROM_UNIXTIME(CREATIONDATE/1000)";
/* 1390 */     if (DBQueryUtil.isPgsql()) {
/* 1391 */       return "to_timestamp(CREATIONDATE/1000)::timestamp without time zone";
/*      */     }
/* 1393 */     return null;
/*      */   }
/*      */   
/*      */   public static Connection getSqlDBConnection(int resid)
/*      */   {
/* 1398 */     Connection conn = null;
/*      */     try {
/* 1400 */       conn = ExecuteQueryAction.getDbConnection(resid, null);
/*      */     } catch (Exception ex) {
/* 1402 */       ex.printStackTrace();
/*      */     }
/* 1404 */     return conn;
/*      */   }
/*      */   
/*      */   public String getSQLVersion(String resourceid) {
/* 1408 */     ResultSet rs_sqlVersion = null;
/* 1409 */     String sqlVersion = null;
/*      */     try {
/* 1411 */       rs_sqlVersion = AMConnectionPool.executeQueryStmt("select ODBCDRIVERVERSION from AM_MSSQLDETAILS where RESOURCEID=" + resourceid);
/* 1412 */       if (rs_sqlVersion.next()) {
/* 1413 */         String version = rs_sqlVersion.getString("ODBCDRIVERVERSION");
/* 1414 */         if (version.toString().startsWith("8.")) {
/* 1415 */           sqlVersion = "sql2000";
/* 1416 */         } else if (version.toString().startsWith("9.")) {
/* 1417 */           sqlVersion = "sql2005";
/* 1418 */         } else if (version.toString().startsWith("10.5")) {
/* 1419 */           sqlVersion = "sql2008r2";
/* 1420 */         } else if (version.toString().startsWith("10.")) {
/* 1421 */           sqlVersion = "sql2008";
/* 1422 */         } else if (version.toString().startsWith("11.0")) {
/* 1423 */           sqlVersion = "sql2012";
/* 1424 */         } else if (version.toString().startsWith("12.0")) {
/* 1425 */           sqlVersion = "sql2014";
/* 1426 */         } else if (version.toString().startsWith("13.0")) {
/* 1427 */           sqlVersion = "sql2016";
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1432 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1435 */       AMConnectionPool.closeResultSet(rs_sqlVersion);
/*      */     }
/* 1437 */     return sqlVersion;
/*      */   }
/*      */   
/*      */   private static long getMaxTime(String tableName, String resId) {
/* 1441 */     long maxtime = 0L;
/* 1442 */     ResultSet rs = null;
/* 1443 */     String query = "select max(COLLECTIONTIME) from " + tableName + " where RESOURCEID=" + resId;
/*      */     try {
/* 1445 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1446 */       if (rs.next()) {
/* 1447 */         maxtime = rs.getLong(1);
/*      */       }
/*      */     } catch (Exception e) {
/* 1450 */       e.printStackTrace();
/*      */     } finally {
/* 1452 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1454 */     return maxtime;
/*      */   }
/*      */   
/*      */ 
/*      */   public Hashtable getoverviewDBDetails(int resid)
/*      */   {
/* 1460 */     long maxtime = 0L;
/* 1461 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQL_DATABASEDETAILS  , AM_ManagedObject , AM_DATABASES where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_DATABASES.PARENTID=" + resid + " and AM_ManagedObject.RESOURCEID=AM_DATABASES.DATABASEID";
/* 1462 */     Hashtable h = new Hashtable();
/* 1463 */     ArrayList resids = new ArrayList();
/* 1464 */     String resIdList = "";
/* 1465 */     ResultSet rs = null;
/*      */     try {
/* 1467 */       rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 1468 */       if (rs.next()) {
/* 1469 */         maxtime = rs.getLong(1);
/*      */       }
/* 1471 */       AMConnectionPool.closeStatement(rs);
/* 1472 */       if (maxtime == 0L) {
/* 1473 */         return h;
/*      */       }
/*      */       
/* 1476 */       Calendar ucal = Calendar.getInstance();
/* 1477 */       ucal.setTimeInMillis(0L);
/* 1478 */       String unixStartTime = ucal.get(1) + "-" + (ucal.get(2) + 1) + "-" + ucal.get(5) + " " + ucal.get(11) + ":" + ucal.get(12) + ":" + ucal.get(13);
/*      */       
/* 1480 */       String dataquery = "select TOP " + Constants.querycount + " AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID , AM_MSSQL_DATABASEDETAILS.TRANSACTIONSPERMIN , AM_MSSQL_DATABASEDETAILS.ACTIVETRANSACTIONS , AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE , AM_MSSQL_DATABASEDETAILS.LOGCACHEHITRATIO , AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE , AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE , AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITSPERMIN , AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITTIME , AM_MSSQL_DATABASEDETAILS.LOGFULSHESPERMIN , AM_MSSQL_DATABASEDETAILS.LOGUSEDPERCENT , AM_MSSQL_DATABASEDETAILS.REPLICATIONTRANSACTIONPERMIN,AM_MSSQL_DATABASEDETAILS.BULKCPYROWS,AM_MSSQL_DATABASEDETAILS.TOTALBULKCPYROWS,AM_MSSQL_DATABASEDETAILS.BULTCPYTPUT,AM_MSSQL_DATABASEDETAILS.TOTALBULTCPYTPUT,AM_MSSQL_DATABASEDETAILS.BKUPRSTTPUT,AM_MSSQL_DATABASEDETAILS.TOTALBKUPRSTTPUT,AM_MSSQL_DATABASEDETAILS.LOGCHEREADS,AM_MSSQL_DATABASEDETAILS.TOTALLOGCHEREADS,AM_MSSQL_DATABASEDETAILS.STATUS,DATEADD(ss,CREATIONDATE/1000,'" + unixStartTime + "') AS CREATIONDATE,AM_MSSQL_DATABASEDETAILS.STATUS1 from   AM_MSSQL_DATABASEDETAILS , AM_DATABASES , AM_ManagedObject where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID and AM_ManagedObject.RESOURCEID=AM_DATABASES.DATABASEID and  AM_DATABASES.PARENTID=" + resid + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME=" + maxtime + " order by AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE desc";
/* 1481 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 1482 */       while (rs.next()) {
/* 1483 */         Properties p = new Properties();
/* 1484 */         int state = -1;
/* 1485 */         p.setProperty("DATAFILESSIZE", String.valueOf(rs.getString("DATAFILESSIZE")));
/* 1486 */         p.setProperty("LOGUSEDPERCENT", String.valueOf(rs.getDouble("LOGUSEDPERCENT")));
/* 1487 */         p.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 1488 */         if (String.valueOf(rs.getString("STATUS")) != null) {
/* 1489 */           p.setProperty("STATUS", rs.getString("STATUS"));
/*      */         } else {
/* 1491 */           p.setProperty("STATUS", "-");
/*      */         }
/*      */         
/* 1494 */         if (String.valueOf(rs.getString("STATUS1")) != null) {
/* 1495 */           p.setProperty("STATUS1", rs.getString("STATUS1"));
/*      */         } else {
/* 1497 */           p.setProperty("STATUS1", "-");
/*      */         }
/*      */         
/* 1500 */         if (rs.getString("CREATIONDATE") != null) {
/* 1501 */           if (rs.getString("CREATIONDATE").equals("1970-01-01 05:30:00.0")) {
/* 1502 */             p.setProperty("CREATIONDATE", "-");
/*      */           } else {
/* 1504 */             p.setProperty("CREATIONDATE", rs.getString("CREATIONDATE"));
/*      */           }
/*      */         } else {
/* 1507 */           p.setProperty("CREATIONDATE", "-");
/*      */         }
/*      */         
/* 1510 */         p.setProperty("DBID", String.valueOf(rs.getInt("RESOURCEID")));
/* 1511 */         h.put(String.valueOf(rs.getInt("RESOURCEID")), p);
/*      */       }
/* 1513 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception e) {
/* 1516 */       AMLog.fatal("MSSQL : Problem in fetching the data for Database", e);
/*      */     } finally {
/* 1518 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1520 */     return h;
/*      */   }
/*      */   
/*      */   public ActionForward waitStatsQueries(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*      */     try {
/* 1525 */       response.setContentType("text/html; charset=UTF-8");
/* 1526 */       String resourceid = request.getParameter("resourceid");
/* 1527 */       String waittype = request.getParameter("waittype");
/* 1528 */       request.setAttribute("resourceid", resourceid);
/* 1529 */       Connection con = null;
/* 1530 */       Statement stmt = null;
/* 1531 */       ResultSet rs = null;
/*      */       try {
/* 1533 */         ArrayList allqueries = new ArrayList();
/* 1534 */         con = ExecuteQueryAction.getDbConnection(Integer.parseInt(resourceid), null);
/* 1535 */         if (con != null) {
/* 1536 */           stmt = con.createStatement();
/* 1537 */           rs = stmt.executeQuery("SELECT top 10 sqltext.text,req.status,querystats.execution_count,querystats.total_physical_reads,querystats.total_logical_reads,querystats.total_logical_writes" + " FROM sys.dm_exec_requests req CROSS APPLY sys.dm_exec_sql_text(sql_handle) AS sqltext JOIN sys.dm_exec_query_stats querystats ON querystats.sql_handle=req.sql_handle" + " WHERE req.wait_type='" + waittype + "' or req.last_wait_type='" + waittype + "'");
/*      */           
/*      */ 
/* 1540 */           while (rs.next())
/*      */           {
/* 1542 */             Properties prop = new Properties();
/* 1543 */             prop.put("status", rs.getString("status"));
/* 1544 */             prop.put("sqlquery", rs.getString("text"));
/* 1545 */             prop.put("executioncount", rs.getString("execution_count"));
/* 1546 */             prop.put("totalphysicalreads", rs.getString("total_physical_reads"));
/* 1547 */             prop.put("totallogicalreads", rs.getString("total_logical_reads"));
/* 1548 */             prop.put("totallogicalwrites", rs.getString("total_logical_writes"));
/* 1549 */             allqueries.add(prop);
/*      */           }
/* 1551 */           request.setAttribute("queriesforwaitstats", allqueries);
/*      */         } else {
/* 1553 */           AMLog.debug("MsSQLAction QueriesForWait :: Couldn't establish connection with resource ID " + resourceid);
/*      */         }
/*      */       }
/*      */       catch (SQLException e) {
/* 1557 */         e.printStackTrace();
/* 1558 */         AMLog.debug("MsSQLAction QueriesForWait :: Error in getting data..");
/*      */       }
/*      */       finally {
/* 1561 */         if (rs != null) {
/* 1562 */           rs.close();
/*      */         }
/* 1564 */         if (stmt != null) {
/* 1565 */           stmt.close();
/*      */         }
/* 1567 */         if (con != null) {
/* 1568 */           con.close();
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1572 */       e.printStackTrace();
/*      */     }
/* 1574 */     return mapping.findForward("waitstatsqueries");
/*      */   }
/*      */   
/*      */   public Hashtable getDBDetails(int resid) {
/* 1578 */     long maxtime = 0L;
/* 1579 */     StringBuffer resIdList = new StringBuffer();
/* 1580 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQL_DATABASEDETAILS, AM_ManagedObject, AM_DATABASES where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_DATABASES.PARENTID=" + resid + " and AM_ManagedObject.RESOURCEID=AM_DATABASES.DATABASEID";
/* 1581 */     Hashtable h = new Hashtable();
/* 1582 */     ResultSet rsMaxTime = null;
/* 1583 */     ResultSet rs = null;
/* 1584 */     ResultSet rsTime = null;
/*      */     try {
/* 1586 */       rsMaxTime = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 1587 */       if (rsMaxTime.next()) {
/* 1588 */         maxtime = rsMaxTime.getLong(1);
/*      */       }
/* 1590 */       if (maxtime == 0L) {
/* 1591 */         return h;
/*      */       }
/* 1593 */       String dataquery = "select  AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID , AM_MSSQL_DATABASEDETAILS.TRANSACTIONSPERMIN , AM_MSSQL_DATABASEDETAILS.ACTIVETRANSACTIONS , AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE, AM_MSSQL_DATABASEDETAILS.LOGCACHEHITRATIO , AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE, AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE, AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITSPERMIN ,AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITTIME , AM_MSSQL_DATABASEDETAILS.LOGFULSHESPERMIN , AM_MSSQL_DATABASEDETAILS.LOGUSEDPERCENT,AM_MSSQL_DATABASEDETAILS.REPLICATIONTRANSACTIONPERMIN , AM_MSSQL_DATABASEDETAILS.STATUS , AM_MSSQL_DATABASEDETAILS.STATUS1,BULKCPYROWSPERMIN, BULKCPYTPUTPERMIN, BKUPRSTTPUTPERMIN, LOGCHEREADSPERMIN," + formatCreationDate() + " AS CREATIONDATE, DBMODE, COMPATIBILITYLEVEL, AUTOSHRINK, PAGEVERIFY, AUTOCREATESTATS, AUTOUPDATESTATS from AM_ManagedObject inner join AM_MSSQL_DATABASEDETAILS on AM_ManagedObject.RESOURCEID= AM_MSSQL_DATABASEDETAILS.RESOURCEID inner join AM_DATABASES on AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID and AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID left outer join AM_MSSQL_DATABASEDETAILS_EXT on AM_ManagedObject.RESOURCEID=AM_MSSQL_DATABASEDETAILS_EXT.RESOURCEID where  AM_DATABASES.PARENTID=" + resid + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME=" + maxtime + " AND AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME=AM_MSSQL_DATABASEDETAILS_EXT.COLLECTIONTIME";
/* 1594 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 1595 */       while (rs.next()) {
/* 1596 */         Properties p = new Properties();
/* 1597 */         p.setProperty("ACTIVETRANSACTIONS", String.valueOf(rs.getDouble("ACTIVETRANSACTIONS")));
/* 1598 */         p.setProperty("DATAFILESSIZE", rs.getDouble("DATAFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("DATAFILESSIZE") / 1024.0D) : rs.getString("DATAFILESSIZE"));
/* 1599 */         p.setProperty("LOGCACHEHITRATIO", String.valueOf(rs.getDouble("LOGCACHEHITRATIO")));
/* 1600 */         p.setProperty("LOGFILESSIZE", rs.getDouble("LOGFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILESSIZE") / 1024.0D) : rs.getString("LOGFILESSIZE"));
/* 1601 */         p.setProperty("LOGFILEUSEDSIZE", rs.getDouble("LOGFILEUSEDSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILEUSEDSIZE") / 1024.0D) : rs.getString("LOGFILEUSEDSIZE"));
/* 1602 */         p.setProperty("LOGFLUSHWAITSPERMIN", String.valueOf(rs.getDouble("LOGFLUSHWAITSPERMIN")));
/* 1603 */         p.setProperty("LOGFLUSHWAITTIME", String.valueOf(rs.getDouble("LOGFLUSHWAITTIME")));
/* 1604 */         p.setProperty("LOGFULSHESPERMIN", String.valueOf(rs.getDouble("LOGFULSHESPERMIN")));
/* 1605 */         p.setProperty("LOGUSEDPERCENT", String.valueOf(rs.getDouble("LOGUSEDPERCENT")));
/* 1606 */         p.setProperty("REPLICATIONTRANSACTIONPERMIN", String.valueOf(rs.getDouble("REPLICATIONTRANSACTIONPERMIN")));
/* 1607 */         p.setProperty("TRANSACTIONSPERMIN", String.valueOf(rs.getDouble("TRANSACTIONSPERMIN")));
/*      */         
/* 1609 */         if (rs.getString("BULKCPYROWSPERMIN") != null) {
/* 1610 */           p.setProperty("BULKCPYROWSPERMIN", String.valueOf(rs.getDouble("BULKCPYROWSPERMIN")));
/*      */         }
/* 1612 */         if (rs.getString("BULKCPYTPUTPERMIN") != null) {
/* 1613 */           p.setProperty("BULKCPYTPUTPERMIN", String.valueOf(rs.getDouble("BULKCPYTPUTPERMIN")));
/*      */         }
/* 1615 */         if (rs.getString("BKUPRSTTPUTPERMIN") != null) {
/* 1616 */           p.setProperty("BKUPRSTTPUTPERMIN", String.valueOf(rs.getDouble("BKUPRSTTPUTPERMIN")));
/*      */         }
/* 1618 */         if (rs.getString("LOGCHEREADSPERMIN") != null) {
/* 1619 */           p.setProperty("LOGCHEREADSPERMIN", String.valueOf(rs.getDouble("LOGCHEREADSPERMIN")));
/*      */         }
/*      */         
/* 1622 */         p.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*      */         
/* 1624 */         if (String.valueOf(rs.getString("STATUS")) != null) {
/* 1625 */           p.setProperty("STATUS", rs.getString("STATUS"));
/*      */         }
/*      */         else {
/* 1628 */           p.setProperty("STATUS", "-");
/*      */         }
/*      */         
/* 1631 */         if (String.valueOf(rs.getString("STATUS1")) != null) {
/* 1632 */           p.setProperty("STATUS1", rs.getString("STATUS1"));
/*      */         }
/*      */         else {
/* 1635 */           p.setProperty("STATUS1", "-");
/*      */         }
/*      */         
/*      */ 
/* 1639 */         if (rs.getString("CREATIONDATE") != null) {
/* 1640 */           if (rs.getTimestamp("CREATIONDATE").getTime() <= 0L) {
/* 1641 */             p.setProperty("CREATIONDATE", "-");
/*      */           } else {
/* 1643 */             p.setProperty("CREATIONDATE", rs.getString("CREATIONDATE"));
/*      */           }
/*      */         } else {
/* 1646 */           p.setProperty("CREATIONDATE", "-");
/*      */         }
/* 1648 */         if (rs.getString("DBMODE") != null) {
/* 1649 */           p.setProperty("DBMODE", rs.getString("DBMODE"));
/*      */         } else {
/* 1651 */           p.setProperty("DBMODE", "-");
/*      */         }
/* 1653 */         if (rs.getString("COMPATIBILITYLEVEL") != null) {
/* 1654 */           p.setProperty("COMPATIBILITYLEVEL", rs.getString("COMPATIBILITYLEVEL"));
/*      */         } else {
/* 1656 */           p.setProperty("COMPATIBILITYLEVEL", "-");
/*      */         }
/* 1658 */         if (rs.getString("PAGEVERIFY") != null) {
/* 1659 */           p.setProperty("PAGEVERIFY", rs.getString("PAGEVERIFY"));
/*      */         } else {
/* 1661 */           p.setProperty("PAGEVERIFY", "-");
/*      */         }
/* 1663 */         if (rs.getString("AUTOSHRINK") != null) {
/* 1664 */           p.setProperty("AUTOSHRINK", Boolean.toString(rs.getBoolean("AUTOSHRINK")));
/*      */         } else {
/* 1666 */           p.setProperty("AUTOSHRINK", "-");
/*      */         }
/* 1668 */         if (rs.getString("AUTOCREATESTATS") != null) {
/* 1669 */           p.setProperty("AUTOCREATESTATS", Boolean.toString(rs.getBoolean("AUTOCREATESTATS")));
/*      */         } else {
/* 1671 */           p.setProperty("AUTOCREATESTATS", "-");
/*      */         }
/* 1673 */         if (rs.getString("AUTOUPDATESTATS") != null) {
/* 1674 */           p.setProperty("AUTOUPDATESTATS", Boolean.toString(rs.getBoolean("AUTOUPDATESTATS")));
/*      */         } else {
/* 1676 */           p.setProperty("AUTOUPDATESTATS", "-");
/*      */         }
/*      */         
/* 1679 */         if ((rs.getString("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (rs.getString("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (rs.getString("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) {
/* 1680 */           if (resIdList.length() <= 0) {
/* 1681 */             resIdList.append(rs.getString("RESOURCEID"));
/*      */           }
/*      */           else {
/* 1684 */             resIdList.append("," + rs.getString("RESOURCEID"));
/*      */           }
/*      */         }
/* 1687 */         p.setProperty("DBID", String.valueOf(rs.getInt("RESOURCEID")));
/* 1688 */         h.put(rs.getString("DISPLAYNAME"), p);
/*      */       }
/* 1690 */       AMConnectionPool.closeResultSet(rs);
/*      */       
/* 1692 */       if (resIdList.length() > 0)
/*      */       {
/* 1694 */         StringBuffer resourceIdList = new StringBuffer();
/* 1695 */         String timeQuery = "select RESOURCEID,max(COLLECTIONTIME) as COLLECTIONTIME from AM_MSSQL_DATABASEDETAILS where STATUS='ONLINE' and STATUS1='ACTIVE' and  RESOURCEID in (" + resIdList + ") group by RESOURCEID";
/*      */         try {
/* 1697 */           rsTime = AMConnectionPool.executeQueryStmt(timeQuery);
/*      */         } catch (Exception ex) {
/* 1699 */           ex.printStackTrace();
/*      */         }
/*      */         
/* 1702 */         while (rsTime.next()) {
/* 1703 */           if (resourceIdList.length() == 0) {
/* 1704 */             resourceIdList.append(rsTime.getString("RESOURCEID") + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME = " + rsTime.getLong("COLLECTIONTIME"));
/*      */           }
/*      */           else {
/* 1707 */             resourceIdList.append(" or AM_MSSQL_DATABASEDETAILS.RESOURCEID = " + rsTime.getString("RESOURCEID") + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME = " + rsTime.getLong("COLLECTIONTIME"));
/*      */           }
/*      */         }
/* 1710 */         if (resourceIdList.length() > 0) {
/* 1711 */           dataquery = "select AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID , AM_MSSQL_DATABASEDETAILS.TRANSACTIONSPERMIN , AM_MSSQL_DATABASEDETAILS.ACTIVETRANSACTIONS , AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE , AM_MSSQL_DATABASEDETAILS.LOGCACHEHITRATIO , AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE, AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE , AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITSPERMIN , AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITTIME , AM_MSSQL_DATABASEDETAILS.LOGFULSHESPERMIN , AM_MSSQL_DATABASEDETAILS.LOGUSEDPERCENT, AM_MSSQL_DATABASEDETAILS.REPLICATIONTRANSACTIONPERMIN , AM_MSSQL_DATABASEDETAILS.STATUS , AM_MSSQL_DATABASEDETAILS.STATUS1,BULKCPYROWSPERMIN, BULKCPYTPUTPERMIN, BKUPRSTTPUTPERMIN, LOGCHEREADSPERMIN,to_timestamp(CREATIONDATE/1000)::timestamp without time zone AS CREATIONDATE, DBMODE, COMPATIBILITYLEVEL, AUTOSHRINK, PAGEVERIFY, AUTOCREATESTATS, AUTOUPDATESTATS from AM_ManagedObject inner join AM_MSSQL_DATABASEDETAILS on AM_ManagedObject.RESOURCEID= AM_MSSQL_DATABASEDETAILS.RESOURCEID left outer join AM_MSSQL_DATABASEDETAILS_EXT on AM_ManagedObject.RESOURCEID = AM_MSSQL_DATABASEDETAILS_EXT.RESOURCEID where (AM_MSSQL_DATABASEDETAILS.RESOURCEID = " + resourceIdList + ") and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME=AM_MSSQL_DATABASEDETAILS_EXT.COLLECTIONTIME";
/*      */         }
/*      */         try
/*      */         {
/* 1715 */           rs = AMConnectionPool.executeQueryStmt(dataquery);
/*      */         } catch (Exception e) {
/* 1717 */           e.printStackTrace();
/*      */         }
/* 1719 */         while (rs.next()) {
/* 1720 */           Properties existing = (Properties)h.get(rs.getString("DISPLAYNAME"));
/* 1721 */           if (existing != null)
/*      */           {
/* 1723 */             existing.setProperty("ACTIVETRANSACTIONS", String.valueOf(rs.getDouble("ACTIVETRANSACTIONS")));
/* 1724 */             existing.setProperty("DATAFILESSIZE", rs.getDouble("DATAFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("DATAFILESSIZE") / 1024.0D) : rs.getString("DATAFILESSIZE"));
/* 1725 */             existing.setProperty("LOGCACHEHITRATIO", String.valueOf(rs.getDouble("LOGCACHEHITRATIO")));
/* 1726 */             existing.setProperty("LOGFILESSIZE", rs.getDouble("LOGFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILESSIZE") / 1024.0D) : rs.getString("LOGFILESSIZE"));
/* 1727 */             existing.setProperty("LOGFILEUSEDSIZE", rs.getDouble("LOGFILEUSEDSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILEUSEDSIZE") / 1024.0D) : rs.getString("LOGFILEUSEDSIZE"));
/* 1728 */             existing.setProperty("LOGFLUSHWAITSPERMIN", String.valueOf(rs.getDouble("LOGFLUSHWAITSPERMIN")));
/* 1729 */             existing.setProperty("LOGFLUSHWAITTIME", String.valueOf(rs.getDouble("LOGFLUSHWAITTIME")));
/* 1730 */             existing.setProperty("LOGFULSHESPERMIN", String.valueOf(rs.getDouble("LOGFULSHESPERMIN")));
/* 1731 */             existing.setProperty("LOGUSEDPERCENT", String.valueOf(rs.getDouble("LOGUSEDPERCENT")));
/* 1732 */             existing.setProperty("REPLICATIONTRANSACTIONPERMIN", String.valueOf(rs.getDouble("REPLICATIONTRANSACTIONPERMIN")));
/* 1733 */             existing.setProperty("TRANSACTIONSPERMIN", String.valueOf(rs.getDouble("TRANSACTIONSPERMIN")));
/* 1734 */             existing.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*      */             
/* 1736 */             if (rs.getString("BULKCPYROWSPERMIN") != null) {
/* 1737 */               existing.setProperty("BULKCPYROWSPERMIN", String.valueOf(rs.getDouble("BULKCPYROWSPERMIN")));
/*      */             }
/* 1739 */             if (rs.getString("BULKCPYTPUTPERMIN") != null) {
/* 1740 */               existing.setProperty("BULKCPYTPUTPERMIN", String.valueOf(rs.getDouble("BULKCPYTPUTPERMIN")));
/*      */             }
/* 1742 */             if (rs.getString("BKUPRSTTPUTPERMIN") != null) {
/* 1743 */               existing.setProperty("BKUPRSTTPUTPERMIN", String.valueOf(rs.getDouble("BKUPRSTTPUTPERMIN")));
/*      */             }
/* 1745 */             if (rs.getString("LOGCHEREADSPERMIN") != null) {
/* 1746 */               existing.setProperty("LOGCHEREADSPERMIN", String.valueOf(rs.getDouble("LOGCHEREADSPERMIN")));
/*      */             }
/*      */             
/*      */ 
/* 1750 */             if (rs.getString("CREATIONDATE") != null) {
/* 1751 */               if (rs.getTimestamp("CREATIONDATE").getTime() <= 0L) {
/* 1752 */                 existing.setProperty("CREATIONDATE", "-");
/*      */               } else {
/* 1754 */                 existing.setProperty("CREATIONDATE", rs.getString("CREATIONDATE"));
/*      */               }
/*      */             } else {
/* 1757 */               existing.setProperty("CREATIONDATE", "-");
/*      */             }
/* 1759 */             if (rs.getString("DBMODE") != null) {
/* 1760 */               existing.setProperty("DBMODE", rs.getString("DBMODE"));
/*      */             } else {
/* 1762 */               existing.setProperty("DBMODE", "-");
/*      */             }
/* 1764 */             if (rs.getString("COMPATIBILITYLEVEL") != null) {
/* 1765 */               existing.setProperty("COMPATIBILITYLEVEL", rs.getString("COMPATIBILITYLEVEL"));
/*      */             } else {
/* 1767 */               existing.setProperty("COMPATIBILITYLEVEL", "-");
/*      */             }
/* 1769 */             if (rs.getString("PAGEVERIFY") != null) {
/* 1770 */               existing.setProperty("PAGEVERIFY", rs.getString("PAGEVERIFY"));
/*      */             } else {
/* 1772 */               existing.setProperty("PAGEVERIFY", "-");
/*      */             }
/* 1774 */             if (rs.getString("AUTOSHRINK") != null) {
/* 1775 */               existing.setProperty("AUTOSHRINK", Boolean.toString(rs.getBoolean("AUTOSHRINK")));
/*      */             } else {
/* 1777 */               existing.setProperty("AUTOSHRINK", "-");
/*      */             }
/* 1779 */             if (rs.getString("AUTOCREATESTATS") != null) {
/* 1780 */               existing.setProperty("AUTOCREATESTATS", Boolean.toString(rs.getBoolean("AUTOCREATESTATS")));
/*      */             } else {
/* 1782 */               existing.setProperty("AUTOCREATESTATS", "-");
/*      */             }
/* 1784 */             if (rs.getString("AUTOUPDATESTATS") != null) {
/* 1785 */               existing.setProperty("AUTOUPDATESTATS", Boolean.toString(rs.getBoolean("AUTOUPDATESTATS")));
/*      */             } else {
/* 1787 */               existing.setProperty("AUTOUPDATESTATS", "-");
/*      */             }
/*      */             
/* 1790 */             h.put(rs.getString("DISPLAYNAME"), existing);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1796 */       AMLog.fatal("MsSQL : Problem in fetching the data for Performance", e);
/*      */     }
/*      */     finally {
/* 1799 */       AMConnectionPool.closeResultSet(rs);
/* 1800 */       AMConnectionPool.closeResultSet(rsTime);
/* 1801 */       AMConnectionPool.closeResultSet(rsMaxTime);
/*      */     }
/* 1803 */     return h;
/*      */   }
/*      */   
/*      */   public ArrayList getMsSqlJobDetails(int resourceid) {
/* 1807 */     String query = "select  AM_MSSQLJOBDETAILS.*, AM_ManagedObject.DISPLAYNAME as JOBNAME from AM_MSSQLJOBDETAILS, AM_ManagedObject, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID = " + resourceid + " and AM_MSSQLJOBDETAILS.JOBID = AM_PARENTCHILDMAPPER.CHILDID and AM_MSSQLJOBDETAILS.JOBID = AM_ManagedObject.RESOURCEID";
/* 1808 */     ArrayList jobDetails = new ArrayList();
/* 1809 */     ResultSet results = null;
/*      */     try {
/* 1811 */       results = AMConnectionPool.executeQueryStmt(query);
/* 1812 */       while (results.next()) {
/* 1813 */         Properties jobs = new Properties();
/* 1814 */         jobs.setProperty("JOBID", String.valueOf(results.getInt("JOBID")));
/* 1815 */         jobs.setProperty("JOBNAME", results.getString("JOBNAME"));
/* 1816 */         jobs.setProperty("JOB_STATUS", String.valueOf(results.getInt("JOB_STATUS")));
/* 1817 */         jobs.setProperty("JOB_CURRENT_STATUS", String.valueOf(results.getInt("JOB_CURRENT_STATUS")));
/* 1818 */         jobs.setProperty("RUN_DATE", results.getString("RUN_DATE"));
/* 1819 */         jobs.setProperty("RUN_DURATION", String.valueOf(results.getInt("RUN_DURATION")));
/* 1820 */         jobs.setProperty("RETRIES_ATTEMPTED", String.valueOf(results.getInt("RETRIES_ATTEMPTED")));
/* 1821 */         jobDetails.add(jobs);
/*      */       }
/*      */     } catch (SQLException sqlException) {
/* 1824 */       sqlException.printStackTrace();
/*      */     } catch (Exception exception) {
/* 1826 */       exception.printStackTrace();
/*      */     } finally {
/* 1828 */       AMConnectionPool.closeResultSet(results);
/*      */     }
/* 1830 */     return jobDetails;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward sqlDatabaseManagementAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1837 */     String resourceid = request.getParameter("resourceid");
/*      */     try
/*      */     {
/* 1840 */       String[] dbIDs = request.getParameterValues("sqldatabasecheckbox");
/* 1841 */       String action = request.getParameter("sqldbActions");
/* 1842 */       int dcStarted = 1;
/* 1843 */       if (dbIDs != null) {
/* 1844 */         ArrayList dbList = new ArrayList(Arrays.asList(dbIDs));
/* 1845 */         if ("disable".equals(action)) {
/* 1846 */           dcStarted = 2;
/* 1847 */           List<String> entities = new ArrayList();
/* 1848 */           for (String dbResID : dbIDs)
/*      */           {
/* 1850 */             entities.add(dbResID + "_" + "3150");
/* 1851 */             entities.add(dbResID + "_" + "3151");
/* 1852 */             entities.add(dbResID + "_" + "3128");
/* 1853 */             entities.add(dbResID + "_" + "3129");
/* 1854 */             entities.add(dbResID + "_" + "3130");
/*      */           }
/* 1856 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Alert WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 1857 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Event WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 1858 */           for (Object entityObj : entities)
/*      */           {
/* 1860 */             String entity = (String)entityObj;
/* 1861 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Alert WHERE ENTITY='" + entity + "'");
/* 1862 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Event WHERE ENTITY='" + entity + "'");
/* 1863 */             FaultUtil.deleteEventFromCache(entity);
/*      */           }
/*      */         }
/* 1866 */         DBUtil.updateDCStarted(dcStarted, dbList);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1869 */       ex.printStackTrace();
/*      */     }
/* 1871 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid, true);
/*      */   }
/*      */   
/*      */   public ActionForward backupManagementActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1875 */     String dbs = request.getParameter("ids");
/* 1876 */     StringBuilder sb = new StringBuilder();
/* 1877 */     String action = request.getParameter("operation");
/* 1878 */     response.setContentType("text/html; charset=UTF-8");
/* 1879 */     PrintWriter out = response.getWriter();
/* 1880 */     String statusMsg = FormatUtil.getString("am.webclient.mssql.dbdetails.enabledb.statusmsg");
/*      */     try {
/* 1882 */       if (action.equals("enableMonitoring")) {
/* 1883 */         Vector listIds = new Vector();
/* 1884 */         String[] resourceid = dbs.split(",");
/* 1885 */         for (String resId : resourceid) {
/* 1886 */           listIds.add(resId);
/* 1887 */           if (DBUtil.disableDBIds.contains(resId)) {
/* 1888 */             DBUtil.disableDBIds.remove(resId);
/*      */           }
/*      */         }
/* 1891 */         updateDCStarted(1, listIds);
/* 1892 */       } else if ((action.equals("disableMonitoring")) || (action.equals("disableMonitoringReset"))) {
/* 1893 */         statusMsg = FormatUtil.getString("am.webclient.mssql.dbdetails.unmanagedb.statusmsg");
/* 1894 */         request.setAttribute("message", statusMsg);
/* 1895 */         Vector listIds = new Vector();
/* 1896 */         String[] resourceid = dbs.split(",");
/* 1897 */         for (String resId : resourceid) {
/* 1898 */           listIds.add(resId);
/* 1899 */           if (!DBUtil.disableDBIds.contains(resId)) {
/* 1900 */             DBUtil.disableDBIds.add(resId);
/*      */           }
/* 1902 */           if (action.equals("disableMonitoringReset")) {
/* 1903 */             FaultUtil.deleteAlertsForResource(resId);
/*      */           }
/*      */         }
/* 1906 */         updateDCStarted(2, listIds);
/*      */       }
/*      */     } catch (Exception e1) {
/* 1909 */       statusMsg = FormatUtil.getString("Error Message") + e1.getMessage();
/* 1910 */       e1.printStackTrace();
/*      */     }
/* 1912 */     out.print(statusMsg);
/* 1913 */     out.flush();
/* 1914 */     return null;
/*      */   }
/*      */   
/*      */   public static void updateDCStarted(int dcStarted, Vector<String> resIDList)
/*      */   {
/* 1919 */     String condition = DBUtil.getRequiredCondn("RESOURCEID", resIDList);
/* 1920 */     String query = "UPDATE AM_ManagedObject SET DCSTARTED='" + dcStarted + "' WHERE " + condition;
/*      */     try {
/* 1922 */       AMConnectionPool.executeUpdateStmt(query);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1926 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward sqlJobsManagementAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1932 */     String resourceid = request.getParameter("resourceid");
/*      */     try {
/* 1934 */       String[] jobids = request.getParameterValues("sqljobscheckbox");
/* 1935 */       String action = request.getParameter("sqljobActions");
/* 1936 */       int dcStarted = 1;
/* 1937 */       if (jobids != null) {
/* 1938 */         ArrayList sqlJobs = new ArrayList(Arrays.asList(jobids));
/* 1939 */         if ("disable".equals(action)) {
/* 1940 */           dcStarted = 2;
/* 1941 */           Vector entities = new Vector();
/* 1942 */           for (String jobResID : jobids)
/*      */           {
/* 1944 */             AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + resourceid + " and CHILDRESOURCEID in (" + jobResID + ")");
/* 1945 */             entities.add(jobResID + "_" + "3158");
/* 1946 */             entities.add(jobResID + "_" + "3159");
/* 1947 */             entities.add(jobResID + "_" + "3160");
/* 1948 */             entities.add(jobResID + "_" + "3161");
/*      */           }
/* 1950 */           entities.add(resourceid + "_" + "3101");
/* 1951 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Alert WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 1952 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Event WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 1953 */           for (Object entityObj : entities)
/*      */           {
/* 1955 */             String entity = (String)entityObj;
/* 1956 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Alert WHERE ENTITY='" + entity + "'");
/* 1957 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Event WHERE ENTITY='" + entity + "'");
/* 1958 */             FaultUtil.deleteEventFromCache(entity);
/*      */           }
/*      */         } else {
/* 1961 */           for (String jobResID : jobids) {
/* 1962 */             AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values(" + resourceid + ",'3101'," + jobResID + ",'3161')");
/*      */           }
/*      */         }
/* 1965 */         DBUtil.updateDCStarted(dcStarted, sqlJobs);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1968 */       ex.printStackTrace();
/*      */     }
/* 1970 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid, true);
/*      */   }
/*      */   
/*      */   public ActionForward systables(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 1974 */     List sqlout = new ArrayList();
/* 1975 */     String reportType = null;
/*      */     try {
/* 1977 */       String resourceid = request.getParameter("resourceid");
/* 1978 */       String dbname = request.getParameter("dbname");
/* 1979 */       reportType = request.getParameter("reportType");
/* 1980 */       String query = "SELECT [System Table Name], (SELECT ROWS FROM [" + dbname + "]..sysindexes S WHERE S.Indid < 2 AND S.ID = OBJECT_ID(A.[System Table Name])) AS [Total Rows], [Total Space Used in MB] FROM  (SELECT QUOTENAME(USER_NAME(so.uid)) + '.' + QUOTENAME(OBJECT_NAME(si.id)) AS [System Table Name],CONVERT(Numeric(15,2),(((CONVERT(Numeric(15,2),SUM(si.Reserved)) * (SELECT LOW FROM master.dbo.spt_values (NOLOCK) WHERE number = 1 AND type = 'E')) / 1024.)/1024.)) AS [Total Space Used in MB] FROM [" + dbname + "]..sysindexes si (NOLOCK) INNER JOIN [" + dbname + "]..sysobjects so (NOLOCK) ON si.id = so.id AND so.type IN ('S') AND (OBJECTPROPERTY(si.id, 'IsMSShipped') = 1) WHERE indid IN (0, 1, 255) GROUP BY QUOTENAME(USER_NAME(so.uid)) + '.' + QUOTENAME(OBJECT_NAME(si.id))) as A ORDER BY [Total Space Used in MB] DESC";
/* 1981 */       sqlout = executeQueries(resourceid, query, dbname);
/* 1982 */       request.setAttribute("sqlout", sqlout);
/* 1983 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.systables.text"));
/*      */     } catch (Exception e) {
/* 1985 */       e.printStackTrace();
/*      */     }
/* 1987 */     if ("csv".equals(reportType)) {
/* 1988 */       request.setAttribute("reportName", "SystemTables");
/* 1989 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 1991 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward usertables(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 1995 */     List sqlout = new ArrayList();
/* 1996 */     String reportType = null;
/*      */     try {
/* 1998 */       String resourceid = request.getParameter("resourceid");
/* 1999 */       String dbname = request.getParameter("dbname");
/* 2000 */       reportType = request.getParameter("reportType");
/* 2001 */       String query = "SELECT [System Table Name], (SELECT ROWS FROM [" + dbname + "]..sysindexes S WHERE S.Indid < 2 AND S.ID = OBJECT_ID(A.[System Table Name])) AS [Total Rows], [Total Space Used in MB] FROM (SELECT QUOTENAME(USER_NAME(so.uid)) + '.' + QUOTENAME(OBJECT_NAME(si.id)) AS [System Table Name],CONVERT(Numeric(15,2),(((CONVERT(Numeric(15,2),SUM(si.Reserved)) * (SELECT LOW FROM master.dbo.spt_values (NOLOCK) WHERE number = 1 AND type = 'E')) / 1024.)/1024.)) AS [Total Space Used in MB] FROM [" + dbname + "]..sysindexes si (NOLOCK) INNER JOIN [" + dbname + "]..sysobjects so (NOLOCK) ON si.id = so.id AND so.type IN ('U')WHERE indid IN (0, 1, 255)GROUP BY QUOTENAME(USER_NAME(so.uid)) + '.' + QUOTENAME(OBJECT_NAME(si.id))) as A ORDER BY [Total Space Used in MB] DESC";
/* 2002 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2003 */       request.setAttribute("sqlout", sqlout);
/* 2004 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.usertables.text"));
/*      */     } catch (Exception e) {
/* 2006 */       e.printStackTrace();
/*      */     }
/* 2008 */     if ("csv".equals(reportType)) {
/* 2009 */       request.setAttribute("reportName", "UserTables");
/* 2010 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2012 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward indexdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2016 */     List sqlout = new ArrayList();
/* 2017 */     String reportType = null;
/*      */     try {
/* 2019 */       String resourceid = request.getParameter("resourceid");
/* 2020 */       String dbname = request.getParameter("dbname");
/* 2021 */       reportType = request.getParameter("reportType");
/* 2022 */       String query = "SELECT CAST(SO.[name] AS CHAR(20)) AS [Table Name], CAST(SI.[name] AS CHAR(30)) AS [Index Name], SI.xmaxlen AS 'Maximum Size Row', SI.maxirow AS 'Max Noleafindex Row', fg.groupname as [File Group Name], CAST(SC.[name] AS CHAR(15)) AS [Column Name], CAST(ST.[name] AS CHAR(10)) AS [Type], CASE WHEN (SI.status & 16)<>0 THEN 'Yes' ELSE 'No' END AS [Clustered Index] FROM [" + dbname + "].dbo.sysobjects SO INNER JOIN [" + dbname + "].dbo.sysindexes SI INNER JOIN [" + dbname + "].dbo.sysfilegroups fg on SI.groupid = fg.groupid INNER JOIN [" + dbname + "].dbo.sysindexkeys SIK ON SIK.[id] = SI.[id] AND SIK.indid = SI.indid INNER JOIN [" + dbname + "].dbo.syscolumns SC INNER JOIN [" + dbname + "].dbo.systypes ST  ON SC.xtype = ST.xtype ON SIK.[id] = SC.[id] AND SIK.colid = SC.colid ON SO.[id] =SI.[id] WHERE SO.xtype = 'u' AND SI.indid > 0 AND SI.indid < 255 AND (SI.status & 64)=0  ORDER BY [Table Name], [Index Name], SIK.keyno";
/* 2023 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2024 */       request.setAttribute("sqlout", sqlout);
/* 2025 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.indexdetails.text"));
/*      */     } catch (Exception e) {
/* 2027 */       e.printStackTrace();
/*      */     }
/* 2029 */     if ("csv".equals(reportType)) {
/* 2030 */       request.setAttribute("reportName", "IndexDetails");
/* 2031 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2033 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward fragmentdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2037 */     List sqlout = new ArrayList();
/* 2038 */     String reportType = null;
/*      */     try {
/* 2040 */       String resourceid = request.getParameter("resourceid");
/* 2041 */       String dbname = request.getParameter("dbname");
/* 2042 */       String dbId = null;
/* 2043 */       reportType = request.getParameter("reportType");
/* 2044 */       sqlout = executeQueries(resourceid, "select DB_ID() as dbid", dbname);
/* 2045 */       dbId = (String)((HashMap)sqlout.get(1)).get("1");
/* 2046 */       String query = "SELECT OBJECT_NAME(i.OBJECT_ID) AS [Table Name],i.name AS [Index Name],ROUND(indexstats.avg_fragmentation_in_percent,2)as [Fragmentation Percent] FROM sys.dm_db_index_physical_stats(" + dbId + ", NULL, NULL, NULL, 'DETAILED') indexstats INNER JOIN sys.indexes i ON i.OBJECT_ID = indexstats.OBJECT_ID AND i.index_id = indexstats.index_id WHERE indexstats.avg_fragmentation_in_percent > 0";
/* 2047 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2048 */       request.setAttribute("sqlout", sqlout);
/* 2049 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.fragmentationdetails.text"));
/*      */     } catch (Exception e) {
/* 2051 */       e.printStackTrace();
/*      */     }
/* 2053 */     if ("csv".equals(reportType)) {
/* 2054 */       request.setAttribute("reportName", "FragmentationDetails");
/* 2055 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2057 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward tablerelation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2061 */     List sqlout = new ArrayList();
/* 2062 */     String reportType = null;
/*      */     try {
/* 2064 */       String resourceid = request.getParameter("resourceid");
/* 2065 */       String dbname = request.getParameter("dbname");
/* 2066 */       reportType = request.getParameter("reportType");
/* 2067 */       String query = "SELECT RO.NAME AS [Parent Table], RC.NAME AS [Parent Column], FO.NAME AS [Foreign Table], FC.NAME AS [Foreign Column] FROM sysforeignkeys F INNER JOIN sysobjects RO ON F.rkeyid = RO.id INNER JOIN syscolumns RC ON RC.id = RO.id AND RC.colid = F.rkey INNER JOIN sysobjects FO ON F.fkeyid = FO.id INNER JOIN syscolumns FC ON FC.id = FO.id AND FC.colid = F.fkey ORDER BY RO.NAME, RC.NAME, FO.NAME, FC.NAME";
/* 2068 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2069 */       request.setAttribute("sqlout", sqlout);
/* 2070 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.tablerelationship.text"));
/*      */     } catch (Exception e) {
/* 2072 */       e.printStackTrace();
/*      */     }
/* 2074 */     if ("csv".equals(reportType)) {
/* 2075 */       request.setAttribute("reportName", "TableRelationship");
/* 2076 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2078 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward viewsdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2082 */     List sqlout = new ArrayList();
/* 2083 */     String reportType = null;
/*      */     try {
/* 2085 */       String resourceid = request.getParameter("resourceid");
/* 2086 */       String dbname = request.getParameter("dbname");
/* 2087 */       reportType = request.getParameter("reportType");
/* 2088 */       String query = "SELECT name AS [View Name],SCHEMA_NAME(schema_id) AS [Schema Name],OBJECTPROPERTYEX(object_id,'IsIndexed') AS [IsIndexed],OBJECTPROPERTYEX(object_id,'IsIndexable') AS [IsIndexable] ,create_date as [Create Date],modify_date as [Modify Date] FROM sys.views";
/* 2089 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2090 */       request.setAttribute("sqlout", sqlout);
/* 2091 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.viewdetails.text"));
/*      */     } catch (Exception e) {
/* 2093 */       e.printStackTrace();
/*      */     }
/* 2095 */     if ("csv".equals(reportType)) {
/* 2096 */       request.setAttribute("reportName", "ViewDetails");
/* 2097 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2099 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward indexnotused(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2103 */     List sqlout = new ArrayList();
/* 2104 */     String reportType = null;
/*      */     try {
/* 2106 */       String resourceid = request.getParameter("resourceid");
/* 2107 */       String dbname = request.getParameter("dbname");
/* 2108 */       reportType = request.getParameter("reportType");
/* 2109 */       String query = "SELECT o.name as [Object Name],i.name as [Index Name],i.Type_Desc as [Type] FROM sys.objects AS o JOIN sys.indexes AS i ON o.object_id = i.object_id LEFT OUTER JOIN sys.dm_db_index_usage_stats AS s ON i.object_id =s.object_id  AND i.index_id = s.index_id WHERE  o.type = 'u' AND i.type IN (1, 2) AND (s.index_id IS NULL) OR (s.user_seeks = 0 AND s.user_scans = 0 AND s.user_lookups = 0 )";
/* 2110 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2111 */       request.setAttribute("sqlout", sqlout);
/* 2112 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.indexnotused.text"));
/*      */     } catch (Exception e) {
/* 2114 */       e.printStackTrace();
/*      */     }
/* 2116 */     if ("csv".equals(reportType)) {
/* 2117 */       request.setAttribute("reportName", "IndexNotUsed");
/* 2118 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2120 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward indexused(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 2124 */     List sqlout = new ArrayList();
/* 2125 */     String reportType = null;
/*      */     try {
/* 2127 */       String resourceid = request.getParameter("resourceid");
/* 2128 */       String dbname = request.getParameter("dbname");
/* 2129 */       reportType = request.getParameter("reportType");
/* 2130 */       String query = "SELECT o.name as[Object Name],SCHEMA_NAME(o.schema_id) as [Schema Name],i.name as[Index Name],i.Type_Desc as [Type], s.user_seeks as [User Seeks],s.user_scans as [User Scans],s.user_lookups as [User Lookups],s.user_updates as [User Updates] FROM sys.objects AS o JOIN sys.indexes AS i ON o.object_id = i.object_id JOIN sys.dm_db_index_usage_stats AS s ON i.object_id = s.object_id AND i.index_id = s.index_id WHERE  o.type = 'u' AND i.type IN (1, 2)  AND(s.user_seeks > 0 or s.user_scans > 0 or s.user_lookups > 0 )";
/* 2131 */       sqlout = executeQueries(resourceid, query, dbname);
/* 2132 */       request.setAttribute("sqlout", sqlout);
/* 2133 */       request.setAttribute("reportName", FormatUtil.getString("am.action.shortname.indexused.text"));
/*      */     } catch (Exception e) {
/* 2135 */       e.printStackTrace();
/*      */     }
/* 2137 */     if ("csv".equals(reportType)) {
/* 2138 */       request.setAttribute("reportName", "IndexUsed");
/* 2139 */       return mapping.findForward("report.dbtables.csv");
/*      */     }
/* 2141 */     return new ActionForward("/jsp/mssql/action.jsp");
/*      */   }
/*      */   
/*      */   public List executeQueries(String resourceid, String query, String database) {
/* 2145 */     Connection con = null;
/* 2146 */     Statement stmt = null;
/* 2147 */     ResultSet rs = null;
/* 2148 */     a = new ArrayList();
/*      */     try {
/* 2150 */       ResourceConfig obj = (ResourceConfig)this.api.getCollectData(getResourceNameForResourceID(resourceid), "MSSQL");
/*      */       try {
/* 2152 */         con = ExecuteQueryAction.getDbConnection(Integer.parseInt(resourceid), null);
/*      */       } catch (Exception e1) {
/* 2154 */         e1.printStackTrace(); }
/*      */       ArrayList databaseName;
/* 2156 */       if (con != null) {
/* 2157 */         rs = null;
/* 2158 */         databaseName = new ArrayList();
/* 2159 */         Map data = new HashMap();
/*      */         try {
/* 2161 */           stmt = con.createStatement();
/*      */         }
/*      */         catch (Exception e) {
/* 2164 */           e.printStackTrace();
/* 2165 */           AMLog.debug("MSSQL Action : Unable to create statement for MSSQL monitor with resource id " + obj.getresourceID() + ". MSSQL monitor details -> MSSQL host: " + obj.getHostName() + " MSSQL Port: " + obj.getApplnDiscPort() + " MSSQL User: " + obj.getUserName() + " MSSQL DBName: " + obj.getDatabaseName());
/*      */         }
/*      */       } else {
/* 2168 */         return a;
/*      */       }
/* 2170 */       stmt.executeUpdate("use [" + database + "]");
/* 2171 */       rs = stmt.executeQuery(query);
/* 2172 */       ResultSetMetaData rsmd = rs.getMetaData();
/* 2173 */       int numberOfColumns = rsmd.getColumnCount();
/*      */       
/*      */ 
/*      */ 
/* 2177 */       Map p1 = new HashMap();
/* 2178 */       for (int j = 1; j <= numberOfColumns; j++) {
/* 2179 */         String names = rsmd.getColumnName(j);
/* 2180 */         p1.put(Integer.toString(j), FormatUtil.getString(names));
/*      */       }
/* 2182 */       a.add(p1);
/* 2183 */       while (rs.next())
/*      */       {
/* 2185 */         Map p = new HashMap();
/* 2186 */         for (int i = 1; i <= numberOfColumns; i++) {
/* 2187 */           String names = rsmd.getColumnName(i);
/* 2188 */           String user = rs.getString(i);
/* 2189 */           p.put(Integer.toString(i), user);
/*      */         }
/* 2191 */         a.add(p);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2212 */       return a;
/*      */     }
/*      */     catch (Exception ea)
/*      */     {
/* 2194 */       ea.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 2199 */         if (rs != null) {
/* 2200 */           rs.close();
/*      */         }
/* 2202 */         if (stmt != null) {
/* 2203 */           stmt.close();
/*      */         }
/* 2205 */         if (con != null) {
/* 2206 */           con.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 2209 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static String getResourceNameForResourceID(String resourceid)
/*      */   {
/* 2216 */     String resName = null;
/* 2217 */     ResultSet rs = null;
/*      */     try {
/* 2219 */       rs = AMConnectionPool.executeQueryStmt("SELECT RESOURCENAME FROM AM_ManagedObject WHERE RESOURCEID=" + resourceid);
/* 2220 */       if (rs.next()) {
/* 2221 */         resName = rs.getString(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException e) {
/* 2225 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2228 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2230 */     return resName;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\mssql\struts\MsSQLDispatchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */