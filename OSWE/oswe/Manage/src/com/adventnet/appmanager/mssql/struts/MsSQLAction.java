/*     */ package com.adventnet.appmanager.mssql.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.Cookie;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ 
/*     */ public final class MsSQLAction extends Action
/*     */ {
/*  39 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*     */   
/*  41 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*  43 */   public static String sqlVersion = null;
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  48 */     response.setContentType("text/html; charset=UTF-8");
/*     */     
/*  50 */     int datatype = 1;
/*  51 */     ActionMessages messages = new ActionMessages();
/*  52 */     ActionErrors errors = new ActionErrors();
/*  53 */     String resourceid = request.getParameter("resourceid");
/*  54 */     request.setAttribute("resourceid", resourceid);
/*     */     
/*  56 */     setSQLVersion(resourceid);
/*     */     try
/*     */     {
/*  59 */       Cookie[] cookies = request.getCookies();
/*  60 */       for (Cookie cookie : cookies) {
/*  61 */         if ((resourceid + "_sql_seltab").equals(cookie.getName())) {
/*  62 */           datatype = Integer.parseInt(cookie.getValue());
/*  63 */           request.setAttribute("datatype", cookie.getValue());
/*  64 */           break;
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*     */     }
/*     */     
/*  71 */     if (request.getParameter("showconfigdiv") != null) {
/*  72 */       request.setAttribute("reloadperiod", null);
/*     */     }
/*     */     
/*  75 */     String name = DBUtil.getResourceNameFromId(resourceid);
/*  76 */     String displayname = DBUtil.getDisplaynameforResourceID(resourceid);
/*  77 */     String haid = request.getParameter("haid");
/*  78 */     String appName = request.getParameter("appName");
/*  79 */     request.setAttribute("haid", haid);
/*  80 */     request.setAttribute("name", name);
/*  81 */     request.setAttribute("appName", appName);
/*  82 */     ResourceConfig mssql = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/*  83 */     if ((request.getParameter("configure") != null) && (request.getParameter("configure").equals("true"))) {
/*  84 */       int poll = 300;
/*  85 */       String username = null;
/*  86 */       String password = null;
/*  87 */       username = (String)((DynaActionForm)form).get("username");
/*  88 */       password = (String)((DynaActionForm)form).get("password");
/*  89 */       String instance = (String)((DynaActionForm)form).get("instance");
/*  90 */       Integer pollInt = (Integer)((DynaActionForm)form).get("pollinterval");
/*  91 */       if ((username != null) && (username.length() == 0))
/*     */       {
/*  93 */         username = null;
/*  94 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.username"));
/*     */       }
/*  96 */       if (pollInt == null)
/*     */       {
/*  98 */         poll = 300;
/*  99 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */       }
/*     */       else {
/* 102 */         poll = pollInt.intValue() * 60;
/*     */       }
/* 104 */       if ((instance != null) && (instance.length() == 0))
/*     */       {
/* 106 */         instance = "";
/*     */       }
/*     */       
/*     */ 
/* 110 */       if (!errors.isEmpty()) {
/* 111 */         saveErrors(request, errors);
/* 112 */         request.setAttribute("configured", "false");
/* 113 */         if (request.getParameter("configured") != null) {
/* 114 */           request.setAttribute("configured", "true");
/* 115 */           request.setAttribute("dbdetails", getDBDetails(Integer.parseInt(resourceid)));
/*     */         }
/* 117 */         return mapping.getInputForward();
/*     */       }
/*     */       
/* 120 */       ResourceConfig col = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/* 121 */       Properties TestProps = new Properties();
/* 122 */       TestProps.setProperty("HOST", col.getTargetAddress());
/* 123 */       TestProps.setProperty("PORT", String.valueOf(col.getApplnDiscPort()));
/* 124 */       TestProps.setProperty("username", username);
/* 125 */       TestProps.setProperty("password", password);
/* 126 */       TestProps.setProperty("instance", instance);
/* 127 */       Properties authresult = null;
/*     */       try
/*     */       {
/* 130 */         AMDCInf amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.mssql.datacollection.ScheduleMsSQLDataCollection").newInstance();
/* 131 */         authresult = amdc.CheckAuthentication(TestProps);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 135 */         e.printStackTrace();
/*     */       }
/* 137 */       String result = authresult.getProperty("authentication");
/* 138 */       if (result.equals("failed"))
/*     */       {
/* 140 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", authresult.getProperty("error")));
/* 141 */         saveMessages(request, messages);
/* 142 */         request.setAttribute("configured", "false");
/* 143 */         if (request.getParameter("configured") != null) {
/* 144 */           request.setAttribute("configured", "true");
/* 145 */           request.setAttribute("dbdetails", getDBDetails(Integer.parseInt(resourceid)));
/*     */         }
/* 147 */         request.setAttribute("reloadperiod", null);
/* 148 */         request.setAttribute("showconfigdiv", "true");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 153 */         String newdisplayname = request.getParameter("displayname");
/* 154 */         displayname = newdisplayname;
/* 155 */         AMDataCollectionHandler.getInstance().updateCollectData(name, "MSSQL", poll, true);
/* 156 */         request.setAttribute("reloadperiod", poll + "");
/* 157 */         if (DBQueryUtil.getDBType().equalsIgnoreCase("mysql")) {
/* 158 */           username = findReplace(username, "\\", "\\\\");
/*     */         }
/* 160 */         String updatequery = "update AM_RESOURCECONFIG  set USERNAME='" + username + "' , DATABASENAME='" + instance + "' ,PASSWORD=" + DBQueryUtil.encode(password) + " where RESOURCEID='" + col.getresourceID() + "'";
/* 161 */         AMConnectionPool.executeUpdateStmt(updatequery);
/* 162 */         updatequery = "update AM_ManagedObject  set  DISPLAYNAME='" + newdisplayname + "' , DCSTARTED=2 where RESOURCEID='" + resourceid + "'";
/* 163 */         AMConnectionPool.executeUpdateStmt(updatequery);
/* 164 */         EnterpriseUtil.addUpdateQueryToFile(updatequery);
/* 165 */         updatequery = "update AM_RESOURCECONFIG set ERRORMSG='Data Collection reconfigured. Kindly wait till the next polling interval' where RESOURCEID='" + resourceid + "'";
/* 166 */         AMConnectionPool.executeUpdateStmt(updatequery);
/* 167 */         request.setAttribute("displayname", newdisplayname);
/* 168 */         AMDataCollectionHandler.getInstance();AMDataCollectionHandler.scheduleDataCollection(col, true);
/*     */       }
/* 170 */       String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 171 */       if (request.getParameter("haid") != null)
/*     */       {
/* 173 */         path = path + "&haid=" + request.getParameter("haid");
/*     */       }
/* 175 */       if (result.equals("passed"))
/*     */       {
/* 177 */         return new ActionForward(path, true);
/*     */       }
/*     */       
/*     */ 
/* 181 */       path = path + "&showconfigdiv=true&configure=false";
/* 182 */       return new ActionForward(path);
/*     */     }
/*     */     
/* 185 */     mssql = null;
/* 186 */     mssql = (ResourceConfig)this.api.getCollectData(name, "MSSQL");
/* 187 */     String details = request.getParameter("details");
/* 188 */     if (details == null) {
/* 189 */       details = "Availability";
/*     */     }
/* 191 */     request.setAttribute("configured", "true");
/* 192 */     request.setAttribute("details", details);
/* 193 */     request.setAttribute("resourceid", String.valueOf(mssql.getresourceID()));
/*     */     
/*     */ 
/* 196 */     ((DynaActionForm)form).set("username", mssql.getUserName());
/* 197 */     ((DynaActionForm)form).set("password", mssql.getPassword());
/* 198 */     ((DynaActionForm)form).set("instance", mssql.getDatabaseName());
/* 199 */     ((DynaActionForm)form).set("displayname", displayname);
/* 200 */     ((DynaActionForm)form).set("pollinterval", new Integer(mssql.getPollInterval() / 60));
/* 201 */     request.setAttribute("dbdetails", getDBDetails(mssql.getresourceID()));
/* 202 */     request.setAttribute("details", "reconfigure");
/*     */     
/*     */ 
/*     */ 
/* 206 */     if (mssql.getErrorMsg().equalsIgnoreCase("Data Collection reconfigured. Kindly wait till the next polling interval"))
/*     */     {
/* 208 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", com.adventnet.appmanager.util.FormatUtil.getString(mssql.getErrorMsg())));
/* 209 */       saveMessages(request, messages);
/*     */     }
/* 211 */     else if ((!mssql.getErrorMsg().equalsIgnoreCase("Data Collection Sucessful")) && (mssql.getErrorMsg().indexOf("Monitoring Not Started") == -1))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 217 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", mssql.getErrorMsg()));
/* 218 */       saveErrors(request, errors);
/*     */     }
/* 220 */     request.setAttribute("error", mssql.getErrorMsg());
/* 221 */     return mapping.findForward("DisplayMsSqlData");
/*     */   }
/*     */   
/*     */   public ActionForward sqlDatabaseManagementAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 226 */     String resourceid = request.getParameter("resourceid");
/*     */     try
/*     */     {
/* 229 */       String[] dbIDs = request.getParameterValues("sqldatabasecheckbox");
/* 230 */       String action = request.getParameter("sqldbActions");
/*     */       
/* 232 */       int dcStarted = 1;
/* 233 */       if (dbIDs != null) {
/* 234 */         ArrayList dbList = new ArrayList(Arrays.asList(dbIDs));
/* 235 */         if ("disable".equals(action)) {
/* 236 */           dcStarted = 2;
/* 237 */           List<String> entities = new ArrayList();
/* 238 */           for (String dbResID : dbIDs)
/*     */           {
/* 240 */             entities.add(dbResID + "_" + "3150");
/* 241 */             entities.add(dbResID + "_" + "3151");
/* 242 */             entities.add(dbResID + "_" + "3128");
/* 243 */             entities.add(dbResID + "_" + "3129");
/* 244 */             entities.add(dbResID + "_" + "3130");
/*     */           }
/* 246 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Alert WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 247 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Event WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 248 */           for (Object entityObj : entities)
/*     */           {
/* 250 */             String entity = (String)entityObj;
/* 251 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Alert WHERE ENTITY='" + entity + "'");
/* 252 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Event WHERE ENTITY='" + entity + "'");
/* 253 */             FaultUtil.deleteEventFromCache(entity);
/*     */           }
/*     */         }
/* 256 */         DBUtil.updateDCStarted(dcStarted, dbList);
/*     */       }
/*     */     } catch (Exception ex) {
/* 259 */       ex.printStackTrace();
/*     */     }
/* 261 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid, true);
/*     */   }
/*     */   
/*     */   public ActionForward sqlJobsManagementAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 266 */     String resourceid = request.getParameter("resourceid");
/*     */     try
/*     */     {
/* 269 */       String[] jobids = request.getParameterValues("sqljobscheckbox");
/* 270 */       String action = request.getParameter("sqljobActions");
/*     */       
/* 272 */       int dcStarted = 1;
/* 273 */       if (jobids != null) {
/* 274 */         ArrayList sqlJobs = new ArrayList(Arrays.asList(jobids));
/* 275 */         if ("disable".equals(action)) {
/* 276 */           dcStarted = 2;
/* 277 */           Vector entities = new Vector();
/* 278 */           for (String jobResID : jobids)
/*     */           {
/* 280 */             AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + resourceid + " and CHILDRESOURCEID in (" + jobResID + ")");
/* 281 */             entities.add(jobResID + "_" + "3158");
/* 282 */             entities.add(jobResID + "_" + "3159");
/* 283 */             entities.add(jobResID + "_" + "3160");
/* 284 */             entities.add(jobResID + "_" + "3161");
/*     */           }
/* 286 */           entities.add(resourceid + "_" + "3101");
/* 287 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Alert WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 288 */           AMConnectionPool.executeUpdateStmt("DELETE FROM Event WHERE ENTITY IN " + DBUtil.getInConditonQueryfromList(entities));
/* 289 */           for (Object entityObj : entities)
/*     */           {
/* 291 */             String entity = (String)entityObj;
/* 292 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Alert WHERE ENTITY='" + entity + "'");
/* 293 */             EnterpriseUtil.addUpdateQueryToFile("DELETE FROM Event WHERE ENTITY='" + entity + "'");
/* 294 */             FaultUtil.deleteEventFromCache(entity);
/*     */           }
/*     */         } else {
/* 297 */           for (String jobResID : jobids) {
/* 298 */             AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values(" + resourceid + ",'3101'," + jobResID + ",'3161')");
/*     */           }
/*     */         }
/* 301 */         DBUtil.updateDCStarted(dcStarted, sqlJobs);
/*     */       }
/*     */     } catch (Exception ex) {
/* 304 */       ex.printStackTrace();
/*     */     }
/* 306 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid, true);
/*     */   }
/*     */   
/*     */   public Properties getMsSqlDetails(int id, int port) {
/* 310 */     Properties p = new Properties();
/* 311 */     ResultSet rs = null;
/* 312 */     String mssqldetailsquery = "select * from AM_MSSQLDETAILS where RESOURCEID=" + id + "";
/*     */     try {
/* 314 */       rs = AMConnectionPool.executeQueryStmt(mssqldetailsquery);
/* 315 */       if (rs.next()) {
/* 316 */         p.setProperty("VERSION", rs.getString("VERSION"));
/* 317 */         p.setProperty("ODBC", rs.getString("ODBCDRIVERVERSION"));
/* 318 */         p.setProperty("PORT", String.valueOf(port));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 322 */       AMLog.fatal("MS SQL : Exception in querying the data for MSSQLDetails", e);
/* 323 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 326 */       AMConnectionPool.closeResultSet(rs);
/*     */     }
/* 328 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getPerformance(int resid) {
/* 332 */     long maxtime = 0L;
/* 333 */     Properties p = new Properties();
/* 334 */     ResultSet rs = null;
/* 335 */     ResultSet rs2 = null;
/* 336 */     p.setProperty("BUFFERHITRATIO", "-");
/* 337 */     p.setProperty("DATABASEPAGES", "-");
/* 338 */     p.setProperty("PAGELIFEEXP", "-");
/* 339 */     p.setProperty("TOTALPAGES", "-");
/* 340 */     p.setProperty("CONNECTIONTIME", "-");
/* 341 */     p.setProperty("FREEPAGES", "-");
/* 342 */     p.setProperty("PAGELOOKUPSPERMIN", "-");
/* 343 */     p.setProperty("PAGEREADSPERMIN", "-");
/* 344 */     p.setProperty("PAGEWRITESPERMIN", "-");
/* 345 */     p.setProperty("CONNECTIONS", "-");
/* 346 */     p.setProperty("LOGINSPERMIN", "-");
/* 347 */     p.setProperty("LOGOUTSPERMIN", "-");
/* 348 */     p.setProperty("AVGLOCKWAITTIME", "-");
/* 349 */     p.setProperty("DEADLOCKSPERMIN", "-");
/* 350 */     p.setProperty("LOCKREQUESTSPERMIN", "-");
/* 351 */     p.setProperty("LOCKTIMEOUTSPERMIN", "-");
/* 352 */     p.setProperty("LOCKWAITSPERMIN", "-");
/* 353 */     p.setProperty("AVGLATCHWAITTIME", "-");
/* 354 */     p.setProperty("LATCHWAITSPERMIN", "-");
/* 355 */     p.setProperty("CACHEHITRATIO", "-");
/* 356 */     p.setProperty("CACHECOUNT", "-");
/* 357 */     p.setProperty("CACHEPAGES", "-");
/* 358 */     p.setProperty("CACHEUSEDPERMIN", "-");
/* 359 */     p.setProperty("CONNECTIONMEMORY", "-");
/* 360 */     p.setProperty("GRANTEDWORKSPACEMEMORY", "-");
/* 361 */     p.setProperty("LOCKMEMORY", "-");
/* 362 */     p.setProperty("MEMORYGRANTSPENDING", "-");
/* 363 */     p.setProperty("MEMORYGRANTSSUCCESS", "-");
/* 364 */     p.setProperty("OPTIMIZERMEMORY", "-");
/* 365 */     p.setProperty("SQLCACHEMEMORY", "-");
/* 366 */     p.setProperty("TOTALMEMORY", "-");
/* 367 */     p.setProperty("AUTOPARAMSPERMIN", "-");
/* 368 */     p.setProperty("BATCHREQUESTSPERMIN", "-");
/* 369 */     p.setProperty("SQLCOMPILATIONSPERMIN", "-");
/* 370 */     p.setProperty("SQLRECOMPILATIONSPERMIN", "-");
/* 371 */     p.setProperty("FAILEDAUTOPARAMS", "-");
/* 372 */     p.setProperty("FULLSCANSPERMIN", "-");
/* 373 */     p.setProperty("PROBESCANSPERMIN", "-");
/* 374 */     p.setProperty("RANGESCANSPERMIN", "-");
/* 375 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQLBUFFERDETAILS where RESOURCEID=" + resid + "";
/*     */     try {
/* 377 */       rs2 = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 378 */       if (rs2.next()) {
/* 379 */         maxtime = rs2.getLong(1);
/*     */       }
/* 381 */       if (maxtime == 0L) {
/* 382 */         return p;
/*     */       }
/* 384 */       String dataquery = "select AM_MSSQLBUFFERDETAILS.BUFFERHITRATIO , AM_MSSQLBUFFERDETAILS.TOTALPAGES , AM_MSSQLBUFFERDETAILS.DATABASEPAGES, AM_MSSQLBUFFERDETAILS.PAGELIFEEXP, AM_MSSQLBUFFERDETAILS.FREEPAGES , AM_MSSQLBUFFERDETAILS.PAGELOOKUPSPERMIN , AM_MSSQLBUFFERDETAILS.PAGEREADSPERMIN , AM_MSSQLBUFFERDETAILS.PAGEWRITESPERMIN , AM_MSSQLGENERALDETAILS.CONNECTIONS , AM_MSSQLGENERALDETAILS.LOGINSPERMIN , AM_MSSQLGENERALDETAILS.LOGOUTSPERMIN , AM_MSSQLLOCKDETAILS.AVGLOCKWAITTIME , AM_MSSQLLOCKDETAILS.DEADLOCKSPERMIN , AM_MSSQLLOCKDETAILS.LOCKREQUESTSPERMIN , AM_MSSQLLOCKDETAILS.LOCKTIMEOUTSPERMIN , AM_MSSQLLOCKDETAILS.LOCKWAITSPERMIN , AM_MSSQLLATCHES.AVGLATCHWAITTIME , AM_MSSQLLATCHES.LATCHWAITSPERMIN , AM_MSSQLCACHEDETAILS.CACHEHITRATIO , AM_MSSQLCACHEDETAILS.CACHECOUNT , AM_MSSQLCACHEDETAILS.CACHEPAGES , AM_MSSQLCACHEDETAILS.CACHEUSEDPERMIN , AM_MSSQLMEMORYDETAILS.CONNECTIONMEMORY , AM_MSSQLMEMORYDETAILS.GRANTEDWORKSPACEMEMORY , AM_MSSQLMEMORYDETAILS.LOCKMEMORY , AM_MSSQLMEMORYDETAILS.MEMORYGRANTSPENDING , AM_MSSQLMEMORYDETAILS.MEMORYGRANTSSUCCESS , AM_MSSQLMEMORYDETAILS.OPTIMIZERMEMORY , AM_MSSQLMEMORYDETAILS.SQLCACHEMEMORY , AM_MSSQLMEMORYDETAILS.TOTALMEMORY , AM_MSSQLSQLDETAILS.AUTOPARAMSPERMIN , AM_MSSQLSQLDETAILS.BATCHREQUESTSPERMIN , AM_MSSQLSQLDETAILS.SQLCOMPILATIONSPERMIN , AM_MSSQLSQLDETAILS.SQLRECOMPILATIONSPERMIN , AM_MSSQLSQLDETAILS.FAILEDAUTOPARAMS , AM_MSSQLACCESSMETHODDETAILS.FULLSCANSPERMIN , AM_MSSQLACCESSMETHODDETAILS.PROBESCANSPERMIN , AM_MSSQLACCESSMETHODDETAILS.RANGESCANSPERMIN , AM_ManagedObjectData.RESPONSETIME  from AM_MSSQLBUFFERDETAILS left outer join AM_MSSQLACCESSMETHODDETAILS on AM_MSSQLACCESSMETHODDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLACCESSMETHODDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_MSSQLMEMORYDETAILS on AM_MSSQLMEMORYDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLMEMORYDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLSQLDETAILS on AM_MSSQLSQLDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLSQLDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLLATCHES on AM_MSSQLLATCHES.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLLATCHES.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLCACHEDETAILS on AM_MSSQLCACHEDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLCACHEDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_MSSQLLOCKDETAILS on AM_MSSQLLOCKDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and AM_MSSQLLOCKDETAILS.RESOURCEID = AM_MSSQLBUFFERDETAILS.RESOURCEID left outer join AM_MSSQLGENERALDETAILS on AM_MSSQLGENERALDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLGENERALDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME where AM_MSSQLBUFFERDETAILS.RESOURCEID=" + resid + " and AM_MSSQLBUFFERDETAILS.COLLECTIONTIME=" + maxtime;
/* 385 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 386 */       if (rs.next()) {
/* 387 */         p.setProperty("BUFFERHITRATIO", String.valueOf(rs.getDouble("BUFFERHITRATIO")));
/* 388 */         p.setProperty("DATABASEPAGES", String.valueOf(rs.getLong("DATABASEPAGES")));
/* 389 */         p.setProperty("PAGELIFEEXP", String.valueOf(rs.getLong("PAGELIFEEXP")));
/* 390 */         p.setProperty("TOTALPAGES", String.valueOf(rs.getLong("TOTALPAGES")));
/* 391 */         p.setProperty("CONNECTIONTIME", String.valueOf(rs.getLong("RESPONSETIME")));
/* 392 */         p.setProperty("FREEPAGES", String.valueOf(rs.getLong("FREEPAGES")));
/* 393 */         p.setProperty("PAGELOOKUPSPERMIN", String.valueOf(rs.getDouble("PAGELOOKUPSPERMIN")));
/* 394 */         p.setProperty("PAGEREADSPERMIN", String.valueOf(rs.getDouble("PAGEREADSPERMIN")));
/* 395 */         p.setProperty("PAGEWRITESPERMIN", String.valueOf(rs.getDouble("PAGEWRITESPERMIN")));
/* 396 */         p.setProperty("CONNECTIONS", String.valueOf(rs.getLong("CONNECTIONS")));
/* 397 */         p.setProperty("LOGINSPERMIN", String.valueOf(rs.getDouble("LOGINSPERMIN")));
/* 398 */         p.setProperty("LOGOUTSPERMIN", String.valueOf(rs.getDouble("LOGOUTSPERMIN")));
/* 399 */         p.setProperty("AVGLOCKWAITTIME", String.valueOf(rs.getDouble("AVGLOCKWAITTIME")));
/* 400 */         p.setProperty("DEADLOCKSPERMIN", String.valueOf(rs.getDouble("DEADLOCKSPERMIN")));
/* 401 */         p.setProperty("LOCKREQUESTSPERMIN", String.valueOf(rs.getDouble("LOCKREQUESTSPERMIN")));
/* 402 */         p.setProperty("LOCKTIMEOUTSPERMIN", String.valueOf(rs.getDouble("LOCKTIMEOUTSPERMIN")));
/* 403 */         p.setProperty("LOCKWAITSPERMIN", String.valueOf(rs.getDouble("LOCKWAITSPERMIN")));
/* 404 */         p.setProperty("AVGLATCHWAITTIME", String.valueOf(rs.getDouble("AVGLATCHWAITTIME")));
/* 405 */         p.setProperty("LATCHWAITSPERMIN", String.valueOf(rs.getDouble("LATCHWAITSPERMIN")));
/* 406 */         p.setProperty("CACHEHITRATIO", String.valueOf(rs.getDouble("CACHEHITRATIO")));
/* 407 */         p.setProperty("CACHECOUNT", String.valueOf(rs.getLong("CACHECOUNT")));
/* 408 */         p.setProperty("CACHEPAGES", String.valueOf(rs.getLong("CACHEPAGES")));
/* 409 */         p.setProperty("CACHEUSEDPERMIN", String.valueOf(rs.getDouble("CACHEUSEDPERMIN")));
/* 410 */         p.setProperty("CONNECTIONMEMORY", String.valueOf(rs.getLong("CONNECTIONMEMORY")));
/* 411 */         p.setProperty("GRANTEDWORKSPACEMEMORY", String.valueOf(rs.getLong("GRANTEDWORKSPACEMEMORY")));
/* 412 */         p.setProperty("LOCKMEMORY", String.valueOf(rs.getLong("LOCKMEMORY")));
/* 413 */         p.setProperty("MEMORYGRANTSPENDING", String.valueOf(rs.getLong("MEMORYGRANTSPENDING")));
/* 414 */         p.setProperty("MEMORYGRANTSSUCCESS", String.valueOf(rs.getLong("MEMORYGRANTSSUCCESS")));
/* 415 */         p.setProperty("OPTIMIZERMEMORY", String.valueOf(rs.getLong("OPTIMIZERMEMORY")));
/* 416 */         p.setProperty("SQLCACHEMEMORY", String.valueOf(rs.getLong("SQLCACHEMEMORY")));
/* 417 */         p.setProperty("TOTALMEMORY", String.valueOf(rs.getLong("TOTALMEMORY")));
/* 418 */         p.setProperty("AUTOPARAMSPERMIN", String.valueOf(rs.getDouble("AUTOPARAMSPERMIN")));
/* 419 */         p.setProperty("BATCHREQUESTSPERMIN", String.valueOf(rs.getDouble("BATCHREQUESTSPERMIN")));
/* 420 */         p.setProperty("SQLCOMPILATIONSPERMIN", String.valueOf(rs.getDouble("SQLCOMPILATIONSPERMIN")));
/* 421 */         p.setProperty("SQLRECOMPILATIONSPERMIN", String.valueOf(rs.getDouble("SQLRECOMPILATIONSPERMIN")));
/* 422 */         p.setProperty("FAILEDAUTOPARAMS", String.valueOf(rs.getLong("FAILEDAUTOPARAMS")));
/* 423 */         p.setProperty("FULLSCANSPERMIN", String.valueOf(rs.getDouble("FULLSCANSPERMIN")));
/* 424 */         p.setProperty("PROBESCANSPERMIN", String.valueOf(rs.getDouble("PROBESCANSPERMIN")));
/* 425 */         p.setProperty("RANGESCANSPERMIN", String.valueOf(rs.getDouble("RANGESCANSPERMIN")));
/*     */       }
/*     */     } catch (Exception e) {
/* 428 */       AMLog.fatal("MsSQL : Problem in fetching the data for Performance", e);
/* 429 */       e.printStackTrace();
/*     */     } finally {
/* 431 */       AMConnectionPool.closeResultSet(rs);
/* 432 */       AMConnectionPool.closeResultSet(rs2);
/*     */     }
/* 434 */     return p;
/*     */   }
/*     */   
/*     */   public Hashtable getDBDetails(int resid) {
/* 438 */     long maxtime = 0L;
/* 439 */     StringBuffer resIdList = new StringBuffer();
/* 440 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_MSSQL_DATABASEDETAILS, AM_ManagedObject, AM_DATABASES where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_DATABASES.PARENTID=" + resid + " and AM_ManagedObject.RESOURCEID=AM_DATABASES.DATABASEID";
/* 441 */     Hashtable h = new Hashtable();
/* 442 */     ResultSet rsMaxTime = null;
/* 443 */     ResultSet rs = null;
/* 444 */     ResultSet rsTime = null;
/*     */     try {
/* 446 */       rsMaxTime = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 447 */       if (rsMaxTime.next()) {
/* 448 */         maxtime = rsMaxTime.getLong(1);
/*     */       }
/* 450 */       if (maxtime == 0L) {
/* 451 */         return h;
/*     */       }
/* 453 */       String dataquery = "select  AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID , AM_MSSQL_DATABASEDETAILS.TRANSACTIONSPERMIN , AM_MSSQL_DATABASEDETAILS.ACTIVETRANSACTIONS , AM_MSSQL_DATABASEDETAILS.DATAFILESSIZE, AM_MSSQL_DATABASEDETAILS.LOGCACHEHITRATIO , AM_MSSQL_DATABASEDETAILS.LOGFILESSIZE, AM_MSSQL_DATABASEDETAILS.LOGFILEUSEDSIZE, AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITSPERMIN , AM_MSSQL_DATABASEDETAILS.LOGFLUSHWAITTIME , AM_MSSQL_DATABASEDETAILS.LOGFULSHESPERMIN , AM_MSSQL_DATABASEDETAILS.LOGUSEDPERCENT , AM_MSSQL_DATABASEDETAILS.REPLICATIONTRANSACTIONPERMIN , AM_MSSQL_DATABASEDETAILS.STATUS , AM_MSSQL_DATABASEDETAILS.STATUS1 from   AM_MSSQL_DATABASEDETAILS , AM_DATABASES , AM_ManagedObject where AM_MSSQL_DATABASEDETAILS.RESOURCEID=AM_DATABASES.DATABASEID and AM_ManagedObject.RESOURCEID=AM_DATABASES.DATABASEID and  AM_DATABASES.PARENTID=" + resid + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME=" + maxtime;
/* 454 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 455 */       while (rs.next()) {
/* 456 */         Properties p = new Properties();
/* 457 */         p.setProperty("ACTIVETRANSACTIONS", String.valueOf(rs.getDouble("ACTIVETRANSACTIONS")));
/* 458 */         p.setProperty("DATAFILESSIZE", rs.getDouble("DATAFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("DATAFILESSIZE") / 1024.0D) : rs.getString("DATAFILESSIZE"));
/* 459 */         p.setProperty("LOGCACHEHITRATIO", String.valueOf(rs.getDouble("LOGCACHEHITRATIO")));
/* 460 */         p.setProperty("LOGFILESSIZE", rs.getDouble("LOGFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILESSIZE") / 1024.0D) : rs.getString("LOGFILESSIZE"));
/* 461 */         p.setProperty("LOGFILEUSEDSIZE", rs.getDouble("LOGFILEUSEDSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILEUSEDSIZE") / 1024.0D) : rs.getString("LOGFILEUSEDSIZE"));
/* 462 */         p.setProperty("LOGFLUSHWAITSPERMIN", String.valueOf(rs.getDouble("LOGFLUSHWAITSPERMIN")));
/* 463 */         p.setProperty("LOGFLUSHWAITTIME", String.valueOf(rs.getDouble("LOGFLUSHWAITTIME")));
/* 464 */         p.setProperty("LOGFULSHESPERMIN", String.valueOf(rs.getDouble("LOGFULSHESPERMIN")));
/* 465 */         p.setProperty("LOGUSEDPERCENT", String.valueOf(rs.getDouble("LOGUSEDPERCENT")));
/* 466 */         p.setProperty("REPLICATIONTRANSACTIONPERMIN", String.valueOf(rs.getDouble("REPLICATIONTRANSACTIONPERMIN")));
/* 467 */         p.setProperty("TRANSACTIONSPERMIN", String.valueOf(rs.getDouble("TRANSACTIONSPERMIN")));
/* 468 */         p.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*     */         
/* 470 */         if (String.valueOf(rs.getString("STATUS")) != null) {
/* 471 */           p.setProperty("STATUS", rs.getString("STATUS"));
/*     */         }
/*     */         else {
/* 474 */           p.setProperty("STATUS", "-");
/*     */         }
/*     */         
/* 477 */         if (String.valueOf(rs.getString("STATUS1")) != null) {
/* 478 */           p.setProperty("STATUS1", rs.getString("STATUS1"));
/*     */         }
/*     */         else {
/* 481 */           p.setProperty("STATUS1", "-");
/*     */         }
/*     */         
/* 484 */         if ((rs.getString("STATUS").trim().equalsIgnoreCase("OFFLINE")) || (rs.getString("STATUS").trim().equalsIgnoreCase("SUSPECT")) || (rs.getString("STATUS1").trim().equalsIgnoreCase("INACTIVE"))) {
/* 485 */           if (resIdList.length() <= 0) {
/* 486 */             resIdList.append(rs.getString("RESOURCEID"));
/*     */           }
/*     */           else {
/* 489 */             resIdList.append("," + rs.getString("RESOURCEID"));
/*     */           }
/*     */         }
/* 492 */         p.setProperty("DBID", String.valueOf(rs.getInt("RESOURCEID")));
/* 493 */         h.put(rs.getString("DISPLAYNAME"), p);
/*     */       }
/* 495 */       AMConnectionPool.closeResultSet(rs);
/* 496 */       rs = null;
/*     */       
/* 498 */       if (resIdList.length() > 0)
/*     */       {
/* 500 */         StringBuffer resourceIdList = new StringBuffer();
/*     */         
/* 502 */         String timeQuery = "select RESOURCEID,max(COLLECTIONTIME) as COLLECTIONTIME from AM_MSSQL_DATABASEDETAILS where STATUS='ONLINE' and STATUS1='ACTIVE' and  RESOURCEID in (" + resIdList + ") group by RESOURCEID";
/*     */         try {
/* 504 */           rsTime = AMConnectionPool.executeQueryStmt(timeQuery);
/*     */         } catch (Exception ex) {
/* 506 */           ex.printStackTrace();
/*     */         }
/*     */         
/* 509 */         while (rsTime.next()) {
/* 510 */           if (resourceIdList.length() == 0) {
/* 511 */             resourceIdList.append(rsTime.getString("RESOURCEID") + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME = " + rsTime.getLong("COLLECTIONTIME"));
/*     */           }
/*     */           else {
/* 514 */             resourceIdList.append(" or AM_MSSQL_DATABASEDETAILS.RESOURCEID = " + rsTime.getString("RESOURCEID") + " and AM_MSSQL_DATABASEDETAILS.COLLECTIONTIME = " + rsTime.getLong("COLLECTIONTIME"));
/*     */           }
/*     */         }
/* 517 */         if (resourceIdList.length() > 0) {
/* 518 */           dataquery = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,DATAFILESSIZE,TRANSACTIONSPERMIN,TOTALTRANSACTIONS,REPLICATIONTRANSACTIONPERMIN,TOTALREPLICATIONTRANSACTION,ACTIVETRANSACTIONS,LOGCACHEHITRATIO,LOGFILESSIZE,LOGFILEUSEDSIZE,LOGFULSHESPERMIN,TOTALLOGFLUSHES,LOGFLUSHWAITSPERMIN,TOTALLOGFLUSHWAITS,LOGFLUSHWAITTIME,LOGUSEDPERCENT from AM_MSSQL_DATABASEDETAILS , AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_MSSQL_DATABASEDETAILS.RESOURCEID and (AM_MSSQL_DATABASEDETAILS.RESOURCEID = " + resourceIdList + ")";
/*     */         }
/* 520 */         AMConnectionPool.closeStatement(rsTime);
/*     */         try
/*     */         {
/* 523 */           rs = AMConnectionPool.executeQueryStmt(dataquery);
/*     */         } catch (Exception exx) {
/* 525 */           exx.printStackTrace();
/*     */         }
/* 527 */         while (rs.next()) {
/* 528 */           Properties existing = (Properties)h.get(rs.getString("DISPLAYNAME"));
/* 529 */           if (existing != null)
/*     */           {
/* 531 */             existing.setProperty("ACTIVETRANSACTIONS", String.valueOf(rs.getDouble("ACTIVETRANSACTIONS")));
/* 532 */             existing.setProperty("DATAFILESSIZE", rs.getDouble("DATAFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("DATAFILESSIZE") / 1024.0D) : rs.getString("DATAFILESSIZE"));
/* 533 */             existing.setProperty("LOGCACHEHITRATIO", String.valueOf(rs.getDouble("LOGCACHEHITRATIO")));
/* 534 */             existing.setProperty("LOGFILESSIZE", rs.getDouble("LOGFILESSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILESSIZE") / 1024.0D) : rs.getString("LOGFILESSIZE"));
/* 535 */             existing.setProperty("LOGFILEUSEDSIZE", rs.getDouble("LOGFILEUSEDSIZE") >= 0.0D ? String.valueOf(rs.getDouble("LOGFILEUSEDSIZE") / 1024.0D) : rs.getString("LOGFILEUSEDSIZE"));
/* 536 */             existing.setProperty("LOGFLUSHWAITSPERMIN", String.valueOf(rs.getDouble("LOGFLUSHWAITSPERMIN")));
/* 537 */             existing.setProperty("LOGFLUSHWAITTIME", String.valueOf(rs.getDouble("LOGFLUSHWAITTIME")));
/* 538 */             existing.setProperty("LOGFULSHESPERMIN", String.valueOf(rs.getDouble("LOGFULSHESPERMIN")));
/* 539 */             existing.setProperty("LOGUSEDPERCENT", String.valueOf(rs.getDouble("LOGUSEDPERCENT")));
/* 540 */             existing.setProperty("REPLICATIONTRANSACTIONPERMIN", String.valueOf(rs.getDouble("REPLICATIONTRANSACTIONPERMIN")));
/* 541 */             existing.setProperty("TRANSACTIONSPERMIN", String.valueOf(rs.getDouble("TRANSACTIONSPERMIN")));
/* 542 */             existing.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 543 */             h.put(rs.getString("DISPLAYNAME"), existing);
/*     */           }
/*     */         }
/* 546 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 550 */       AMLog.fatal("MsSQL : Problem in fetching the data for Performance", e);
/*     */     }
/*     */     finally {
/* 553 */       AMConnectionPool.closeResultSet(rs);
/* 554 */       AMConnectionPool.closeResultSet(rsTime);
/* 555 */       AMConnectionPool.closeResultSet(rsMaxTime);
/*     */     }
/* 557 */     return h;
/*     */   }
/*     */   
/*     */ 
/*     */   private String findReplace(String str, String find, String replace)
/*     */   {
/* 563 */     if ((str == null) || (find == null) || (replace == null)) { return null;
/*     */     }
/* 565 */     String des = new String();
/* 566 */     while (str.indexOf(find) != -1) {
/* 567 */       des = des + str.substring(0, str.indexOf(find));
/* 568 */       des = des + replace;
/* 569 */       str = str.substring(str.indexOf(find) + find.length());
/*     */     }
/* 571 */     des = des + str;
/* 572 */     return des;
/*     */   }
/*     */   
/*     */   public ArrayList getMsSqlJobDetails(int resourceid) {
/* 576 */     String query = "select  AM_MSSQLJOBDETAILS.*, AM_ManagedObject.DISPLAYNAME as JOBNAME from AM_MSSQLJOBDETAILS, AM_ManagedObject, AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID = " + resourceid + " and AM_MSSQLJOBDETAILS.JOBID = AM_PARENTCHILDMAPPER.CHILDID and AM_MSSQLJOBDETAILS.JOBID = AM_ManagedObject.RESOURCEID";
/* 577 */     System.out.println(query);
/* 578 */     ArrayList jobDetails = new ArrayList();
/* 579 */     ResultSet results = null;
/*     */     try {
/* 581 */       results = AMConnectionPool.executeQueryStmt(query);
/* 582 */       while (results.next()) {
/* 583 */         Properties jobs = new Properties();
/* 584 */         jobs.setProperty("JOBID", String.valueOf(results.getInt("JOBID")));
/* 585 */         jobs.setProperty("JOBNAME", results.getString("JOBNAME"));
/* 586 */         jobs.setProperty("JOB_STATUS", String.valueOf(results.getInt("JOB_STATUS")));
/* 587 */         jobs.setProperty("JOB_CURRENT_STATUS", String.valueOf(results.getInt("JOB_CURRENT_STATUS")));
/* 588 */         jobs.setProperty("RUN_DATE", results.getString("RUN_DATE"));
/* 589 */         jobs.setProperty("RUN_DURATION", String.valueOf(results.getInt("RUN_DURATION")));
/* 590 */         jobs.setProperty("RETRIES_ATTEMPTED", String.valueOf(results.getInt("RETRIES_ATTEMPTED")));
/* 591 */         jobDetails.add(jobs);
/*     */       }
/* 593 */       results.close();
/*     */     } catch (SQLException sqlException) {
/* 595 */       sqlException.printStackTrace();
/*     */     } catch (Exception exception) {
/* 597 */       exception.printStackTrace();
/*     */     }
/* 599 */     return jobDetails;
/*     */   }
/*     */   
/*     */   public static void setSQLVersion(String resourceid) {
/* 603 */     ResultSet rs_sqlVersion = null;
/*     */     try {
/* 605 */       rs_sqlVersion = AMConnectionPool.executeQueryStmt("select ODBCDRIVERVERSION from AM_MSSQLDETAILS where RESOURCEID=" + resourceid);
/* 606 */       if (rs_sqlVersion.next()) {
/* 607 */         String version = rs_sqlVersion.getString("ODBCDRIVERVERSION");
/* 608 */         if (version.toString().startsWith("8.")) {
/* 609 */           sqlVersion = "sql2000";
/* 610 */         } else if (version.toString().startsWith("9.")) {
/* 611 */           sqlVersion = "sql2005";
/* 612 */         } else if (version.toString().startsWith("10.5")) {
/* 613 */           sqlVersion = "sql2008r2";
/* 614 */         } else if (version.toString().startsWith("10.")) {
/* 615 */           sqlVersion = "sql2008";
/* 616 */         } else if (version.toString().startsWith("11.0")) {
/* 617 */           sqlVersion = "sql2012";
/* 618 */         } else if (version.toString().startsWith("12.0")) {
/* 619 */           sqlVersion = "sql2014";
/* 620 */         } else if (version.toString().startsWith("13.0")) {
/* 621 */           sqlVersion = "sql2016";
/*     */         }
/*     */       }
/*     */       return;
/*     */     } catch (Exception e) {
/* 626 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 629 */       if (rs_sqlVersion != null) {
/*     */         try {
/* 631 */           AMConnectionPool.closeStatement(rs_sqlVersion);
/*     */         }
/*     */         catch (Exception e) {
/* 634 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\mssql\struts\MsSQLAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */