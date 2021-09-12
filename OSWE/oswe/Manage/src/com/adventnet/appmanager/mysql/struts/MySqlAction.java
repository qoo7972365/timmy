/*     */ package com.adventnet.appmanager.mysql.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.servlets.APIServlet;
/*     */ import com.adventnet.appmanager.util.DBSocketConnection;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ public class MySqlAction extends DispatchAction
/*     */ {
/*     */   private ApplnDataCollectionAPI api;
/*     */   private ManagedApplication mo;
/*     */   
/*     */   public MySqlAction()
/*     */   {
/*  47 */     this.api = ((ApplnDataCollectionAPI)com.adventnet.nms.util.NmsUtil.getAPI("ApplnDataCollectionAPI"));
/*  48 */     this.mo = new ManagedApplication();
/*     */   }
/*     */   
/*  51 */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { response.setContentType("text/html; charset=UTF-8");
/*  52 */     String datatypestr = request.getParameter("datatype");
/*  53 */     int datatype = 1;
/*  54 */     String resid = request.getParameter("resourceid");
/*  55 */     Integer resourceid = Integer.valueOf(Integer.parseInt(resid));
/*     */     
/*  57 */     if (datatypestr != null) {
/*  58 */       datatype = Integer.parseInt(datatypestr);
/*     */     }
/*  60 */     if (datatype == 1) {
/*  61 */       overviewDetails(mapping, form, request, response);
/*     */     }
/*  63 */     else if (datatype == 2) {
/*  64 */       configurationDetails(mapping, form, request, response);
/*     */     }
/*  66 */     return mapping.findForward("details");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward overviewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  73 */     response.setContentType("text/html; charset=UTF-8");
/*  74 */     request.setAttribute("configured", "true");
/*  75 */     ActionMessages messages = new ActionMessages();
/*  76 */     ActionErrors errors = new ActionErrors();
/*  77 */     this.mo.getReloadPeriod(request);
/*  78 */     String pollint = (String)request.getAttribute("reloadperiod");
/*  79 */     String resourceid = request.getParameter("resourceid");
/*  80 */     ResourceConfig mysql = null;
/*  81 */     ResultSet rs = null;
/*     */     try {
/*     */       try {
/*  84 */         Long.parseLong(pollint);
/*     */       } catch (Exception e) {
/*  86 */         pollint = "300";
/*     */       }
/*  88 */       HashMap map = com.adventnet.appmanager.struts.beans.ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/*  89 */       if (map != null) {
/*  90 */         request.setAttribute("systeminfo", map);
/*     */       }
/*  92 */       String name = null;
/*  93 */       String displayname = null;
/*  94 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  95 */       int configured = 1;
/*     */       try {
/*  97 */         rs = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  98 */         if (rs.next()) {
/*  99 */           name = rs.getString("RESOURCENAME");
/* 100 */           displayname = rs.getString("DISPLAYNAME");
/* 101 */           request.setAttribute("monitorname", displayname);
/* 102 */           configured = rs.getInt("DCSTARTED");
/* 103 */           request.setAttribute("showdata", configured + "");
/*     */         } else {
/* 105 */           return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */         }
/* 107 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/* 110 */         e.printStackTrace();
/*     */       }
/* 112 */       String haid = request.getParameter("haid");
/* 113 */       String appName = request.getParameter("appName");
/* 114 */       request.setAttribute("haid", haid);
/* 115 */       request.setAttribute("name", name);
/* 116 */       request.setAttribute("appName", appName);
/* 117 */       mysql = (ResourceConfig)this.api.getCollectData(name, "MYSQL");
/* 118 */       String details = request.getParameter("details");
/* 119 */       if (details == null) {
/* 120 */         details = "Availability";
/*     */       }
/* 122 */       request.setAttribute("configured", "true");
/* 123 */       request.setAttribute("details", details);
/* 124 */       request.setAttribute("resourceid", String.valueOf(mysql.getresourceID()));
/*     */       
/* 126 */       ((DynaActionForm)form).set("username", mysql.getUserName());
/* 127 */       ((DynaActionForm)form).set("password", mysql.getPassword());
/* 128 */       ((DynaActionForm)form).set("instance", mysql.getDatabaseName());
/* 129 */       ((DynaActionForm)form).set("displayname", displayname);
/* 130 */       ((DynaActionForm)form).set("pollinterval", new Integer(mysql.getPollInterval() / 60));
/* 131 */       request.setAttribute("details", "reconfigure");
/*     */       
/* 133 */       long maxtime = 0L;
/* 134 */       String maxtimequery = "select max(COLLECTIONTIME) from AM_MYSQLPERFORMANCE where RESOURCEID=" + mysql.getresourceID();
/*     */       try {
/* 136 */         AMConnectionPool pool1 = AMConnectionPool.getInstance();
/* 137 */         rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 138 */         if (rs.next()) {
/* 139 */           maxtime = rs.getLong(1);
/*     */         }
/* 141 */         closeResultSet(rs);
/*     */       } catch (Exception e) {
/* 143 */         AMLog.warning("MySQL : Problem in fetching the maximum collectiontime form Mysql Performance");
/* 144 */         e.printStackTrace();
/*     */       }
/*     */       
/* 147 */       Properties mysqldetails = new Properties();
/* 148 */       Properties replicationProperties = new Properties();
/* 149 */       replicationProperties = getReplicationStatus(mysql.getresourceID());
/* 150 */       request.setAttribute("SLAVE_STATUS", replicationProperties);
/* 151 */       Properties queryStats = new Properties();
/* 152 */       long queryStatsMaxTime = 0L;
/* 153 */       String mysqlQueryStats = "select max(COLLECTIONTIME) from AM_MYSQLQUERYSTATS where RESOURCEID = " + mysql.getresourceID();
/*     */       try {
/* 155 */         rs = AMConnectionPool.executeQueryStmt(mysqlQueryStats);
/* 156 */         if (rs.next()) {
/* 157 */           queryStatsMaxTime = rs.getLong(1);
/*     */         }
/*     */       } catch (Exception exception) {
/* 160 */         exception.printStackTrace();
/*     */       }
/* 162 */       closeResultSet(rs);
/* 163 */       queryStats = getQueryStats(mysql.getresourceID(), queryStatsMaxTime);
/* 164 */       queryStats.setProperty("MAXTIME", String.valueOf(queryStatsMaxTime));
/* 165 */       request.setAttribute("QUERY_STATS", queryStats);
/* 166 */       mysqldetails = getMySqlDetails(mysql.getresourceID());
/* 167 */       request.setAttribute("mysqldetails", mysqldetails);
/* 168 */       Properties perf = new Properties();
/* 169 */       perf = getPerformance(mysql.getresourceID(), maxtime);
/* 170 */       request.setAttribute("perf", perf);
/* 171 */       Properties servertuning = new Properties();
/* 172 */       servertuning = getServerTuning(mysql.getresourceID(), maxtime);
/* 173 */       request.setAttribute("servertuning", servertuning);
/* 174 */       String scripts_query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='Script Monitor' and AM_PARENTCHILDMAPPER.PARENTID=" + mysql.getresourceID() + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*     */       try {
/* 176 */         rs = AMConnectionPool.executeQueryStmt(scripts_query);
/* 177 */         ArrayList al = new ArrayList();
/* 178 */         while (rs.next()) {
/* 179 */           al.add(rs.getString(1));
/*     */         }
/* 181 */         request.setAttribute("script_ids", al);
/*     */         try {
/* 183 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc1) {
/* 186 */           exc1.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception exc1) {
/* 190 */         exc1.printStackTrace();
/*     */       }
/* 192 */       String queryMonitor = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='QueryMonitor' and AM_PARENTCHILDMAPPER.PARENTID=" + mysql.getresourceID() + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*     */       
/*     */       try
/*     */       {
/* 196 */         rs = AMConnectionPool.executeQueryStmt(queryMonitor);
/* 197 */         ArrayList ql = new ArrayList();
/* 198 */         while (rs.next())
/*     */         {
/* 200 */           ql.add(rs.getString(1));
/*     */         }
/* 202 */         request.setAttribute("query_ids", ql);
/*     */         try
/*     */         {
/* 205 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc1)
/*     */         {
/* 209 */           exc1.printStackTrace();
/*     */         }
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/* 214 */         exc1.printStackTrace();
/*     */       }
/*     */       try
/*     */       {
/* 218 */         if (maxtime == 0L) {
/* 219 */           request.setAttribute("tablelocks", new ArrayList());
/* 220 */           request.setAttribute("dbdetails", new ArrayList());
/*     */         }
/*     */         else
/*     */         {
/* 224 */           ArrayList list = new ArrayList();
/* 225 */           list = this.mo.getRows("select IMMEDIATELOCKS , LOCKSWAIT from AM_MYSQLTABLELOCKS where RESOURCEID=" + mysql.getresourceID() + " and COLLECTIONTIME=" + maxtime + "");
/* 226 */           request.setAttribute("tablelocks", list);
/* 227 */           Hashtable disableTable = EnterpriseUtil.getDisableTable();
/* 228 */           String ChkToCollect = (String)disableTable.get("MYSQL-DB-server#TABLEDATA");
/* 229 */           long ColTime = 0L;
/* 230 */           if ((ChkToCollect != null) && ((ChkToCollect.equals("onceaday")) || (ChkToCollect.equals("every"))))
/*     */           {
/* 232 */             String querymaxtime = "select max(COLLECTIONTIME) from AM_MYSQLDBTUNING,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.CHILDID=AM_MYSQLDBTUNING.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID=" + mysql.getresourceID();
/*     */             try
/*     */             {
/* 235 */               rs = AMConnectionPool.executeQueryStmt(querymaxtime);
/* 236 */               if (rs.next()) {
/* 237 */                 ColTime = rs.getLong(1);
/*     */               }
/* 239 */               closeResultSet(rs);
/*     */             }
/*     */             catch (Exception e) {
/* 242 */               AMLog.warning("MySQL : Problem in fetching the maximum collectiontime from am_mysqldbtuning");
/* 243 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */           else {
/* 247 */             ColTime = maxtime;
/*     */           }
/* 249 */           request.setAttribute("maxcollectiontime", String.valueOf(ColTime));
/* 250 */           list = this.mo.getRows("select AM_ManagedObject.DISPLAYNAME , AM_MYSQLDBTUNING.DBSIZE , AM_MYSQLDBTUNING.RESOURCEID , AM_MYSQLDBTUNING.NOOFTABLES from AM_ManagedObject, AM_MYSQLDBTUNING where AM_ManagedObject.RESOURCEID = AM_MYSQLDBTUNING.RESOURCEID and AM_ManagedObject.RESOURCENAME like '%" + name + "%' and AM_ManagedObject.TYPE = 'DataBase' and AM_MYSQLDBTUNING.COLLECTIONTIME=" + ColTime + "");
/* 251 */           request.setAttribute("dbdetails", list);
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 255 */         AMLog.warning("MySQL : Problem in fetching the data for Performance");
/* 256 */         request.setAttribute("tablelocks", new ArrayList());
/* 257 */         request.setAttribute("dbdetails", new ArrayList());
/* 258 */         e.printStackTrace();
/*     */       }
/* 260 */       Properties memory = new Properties();
/* 261 */       memory = getMemoryHealth(mysql.getresourceID(), maxtime);
/* 262 */       request.setAttribute("memoryhealth", memory);
/*     */     }
/*     */     catch (Exception e) {
/* 265 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 268 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/* 271 */     return mapping.findForward("overview");
/*     */   }
/*     */   
/*     */   public ActionForward configurationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 276 */     response.setContentType("text/html; charset=UTF-8");
/* 277 */     String resourceid = request.getParameter("resourceid");
/*     */     
/* 279 */     return mapping.findForward("configuration");
/*     */   }
/*     */   
/*     */   public ActionForward triggerprocesslist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 285 */     response.setContentType("text/html; charset=UTF-8");
/* 286 */     String resourceid = request.getParameter("resourceid");
/* 287 */     ResourceConfig mysql = null;
/* 288 */     Properties dbConProp = new Properties();
/* 289 */     Connection connection = null;
/* 290 */     Statement stmt = null;
/* 291 */     ResultSet rs = null;
/* 292 */     long collectionTime = 0L;
/* 293 */     String query = null;
/* 294 */     String referenceID = "0";
/* 295 */     DBSocketConnection dbCon = new DBSocketConnection();
/* 296 */     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/* 297 */     HashMap resNames = new HashMap();
/*     */     
/*     */ 
/* 300 */     String args = request.getParameter("args");
/* 301 */     String ipaddress = null;
/* 302 */     String apiKey = request.getParameter("apikey");
/* 303 */     String apiKeyargs = null;
/* 304 */     String uid = null;
/* 305 */     HashMap coloumnParams = new HashMap();
/* 306 */     boolean fromappman = true;
/* 307 */     Hashtable excludeParams; Enumeration enu; if (args != null) {
/* 308 */       fromappman = false;
/* 309 */       Hashtable mysqlParams = (Hashtable)com.adventnet.appmanager.json.JSONUtil.getObject(args);
/* 310 */       excludeParams = new Hashtable();
/* 311 */       ipaddress = (String)mysqlParams.get("ip");
/* 312 */       apiKeyargs = (String)mysqlParams.get("apikey");
/* 313 */       uid = (String)mysqlParams.get("uid");
/* 314 */       excludeParams = (Hashtable)mysqlParams.get("exclude");
/*     */       
/* 316 */       for (enu = excludeParams.keys(); enu.hasMoreElements();)
/*     */       {
/* 318 */         String key = (String)enu.nextElement();
/* 319 */         String value = (String)excludeParams.get(key);
/* 320 */         coloumnParams.put(key, convertStringtoArray(value));
/*     */       }
/*     */     }
/*     */     
/* 324 */     PrintWriter out = response.getWriter();
/* 325 */     StringBuffer temp = new StringBuffer();
/* 326 */     boolean appman = true;
/* 327 */     if ((uid != null) && (!uid.equals("")) && (!uid.equals("null")))
/*     */     {
/* 329 */       referenceID = uid;
/*     */     }
/* 331 */     if ((request.getRemoteUser() != null) || (validateAPIKey(apiKey)) || (validateAPIKey(apiKeyargs)))
/*     */     {
/*     */ 
/* 334 */       String commaseparated_commands = request.getParameter("excludecommand");
/* 335 */       ArrayList commands = new ArrayList();
/*     */       
/* 337 */       if (commaseparated_commands != null) {
/* 338 */         StringTokenizer commandTokens = new StringTokenizer(commaseparated_commands, ",");
/* 339 */         while (commandTokens.hasMoreTokens()) {
/* 340 */           commands.add(commandTokens.nextToken());
/*     */         }
/*     */       }
/*     */       
/* 344 */       if ((resourceid != null) && (!resourceid.equals("null"))) {
/* 345 */         query = "select RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE='MYSQL-DB-server' and RESOURCEID=" + resourceid;
/* 346 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 347 */         if (rs.next()) {
/* 348 */           resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*     */         }
/*     */       }
/* 351 */       AMConnectionPool.closeStatement(rs);
/*     */       
/* 353 */       if ((ipaddress != null) && (!ipaddress.equals("null"))) {
/* 354 */         query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, InetService where TARGETADDRESS='" + ipaddress + "' and TYPE='MYSQL-DB-server' and AM_ManagedObject.RESOURCENAME=InetService.NAME";
/* 355 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 356 */         while (rs.next()) {
/* 357 */           resNames.put(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*     */         }
/* 359 */         AMConnectionPool.closeStatement(rs);
/* 360 */         appman = false;
/*     */       }
/* 362 */       if (resNames.size() == 0) {
/* 363 */         if (!appman) {
/* 364 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 365 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.webclient.mysql.processlist.nosource") + " </span></td></tr></table>");
/* 366 */           out.write(temp.toString());
/* 367 */           out.flush();
/* 368 */           return null;
/*     */         }
/* 370 */         request.setAttribute("error", FormatUtil.getString("am.webclient.mysql.processlist.nosource"));
/* 371 */         return mapping.findForward("MySqlProcessList");
/*     */       }
/*     */       try {
/* 374 */         String resname = null;
/* 375 */         String dspname = null;
/* 376 */         Set set = resNames.entrySet();
/* 377 */         Iterator i = set.iterator();
/* 378 */         while (i.hasNext()) {
/*     */           try {
/* 380 */             Map.Entry me = (Map.Entry)i.next();
/* 381 */             resourceid = (String)me.getKey();
/* 382 */             resname = (String)me.getValue();
/* 383 */             mysql = (ResourceConfig)this.api.getCollectData(resname, "MYSQL");
/* 384 */             String dbName = mysql.getDatabaseName();
/* 385 */             String resourceName = mysql.getHostName();
/* 386 */             int port = mysql.getApplnDiscPort();
/* 387 */             String url = "jdbc:mysql://" + resourceName + ":" + port + "/" + dbName + "?characterEncoding=UTF-8";
/* 388 */             String driverName = "org.gjt.mm.mysql.Driver";
/* 389 */             dbConProp.setProperty("driverName", driverName);
/* 390 */             dbConProp.setProperty("url", url);
/* 391 */             dbConProp.setProperty("userName", mysql.getUserName());
/* 392 */             if (mysql.getPassword().equals("NULL")) {
/* 393 */               dbConProp.put("passWord", "");
/*     */             } else {
/* 395 */               dbConProp.put("passWord", mysql.getPassword());
/*     */             }
/* 397 */             connection = dbCon.connect(dbConProp);
/* 398 */             if (connection != null)
/*     */             {
/* 400 */               stmt = connection.createStatement();
/* 401 */               collectionTime = System.currentTimeMillis();
/* 402 */               Date logDate = new Date(collectionTime);
/* 403 */               String variablequery = "show full processlist";
/* 404 */               rs = stmt.executeQuery(variablequery);
/* 405 */               ArrayList processList = new ArrayList();
/* 406 */               while (rs.next()) {
/* 407 */                 Properties plist = new Properties();
/* 408 */                 plist.setProperty("Id", rs.getString("Id"));
/* 409 */                 plist.setProperty("User", rs.getString("User"));
/* 410 */                 plist.setProperty("Host", rs.getString("Host"));
/* 411 */                 if (rs.getString("db") != null) {
/* 412 */                   plist.setProperty("db", rs.getString("db"));
/*     */                 } else {
/* 414 */                   plist.setProperty("db", "-");
/*     */                 }
/* 416 */                 plist.setProperty("Command", rs.getString("Command"));
/* 417 */                 plist.setProperty("Time", rs.getString("Time"));
/* 418 */                 if (rs.getString("State") != null) {
/* 419 */                   plist.setProperty("State", rs.getString("State"));
/*     */                 } else {
/* 421 */                   plist.setProperty("State", "-");
/*     */                 }
/* 423 */                 if (rs.getString("Info") != null) {
/* 424 */                   plist.setProperty("Info", rs.getString("Info"));
/*     */                 } else {
/* 426 */                   plist.setProperty("Info", "-");
/*     */                 }
/* 428 */                 processList.add(plist);
/*     */               }
/* 430 */               java.util.Collections.sort(processList, new timeComparator(null));
/* 431 */               dspname = FormatUtil.getString("am.webclient.mysql.processlist.title", new String[] { resname }) + " , " + sdf.format(logDate);
/* 432 */               request.setAttribute("displayname", dspname);
/* 433 */               request.setAttribute("processList", processList);
/*     */               
/* 435 */               if (processList.size() > 0) {
/* 436 */                 genURLProcessList(resourceid, collectionTime, processList, resname, referenceID, coloumnParams, fromappman);
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 441 */             e.printStackTrace();
/*     */           }
/*     */           finally {
/* 444 */             rs.close();
/* 445 */             stmt.close();
/* 446 */             connection.close();
/*     */           }
/*     */         }
/* 449 */         rs.close();
/* 450 */         if (stmt != null) {
/* 451 */           stmt.close();
/*     */         }
/* 453 */         connection.close();
/*     */       }
/*     */       catch (Exception e) {
/* 456 */         e.printStackTrace();
/* 457 */         AMLog.debug("MySql Action : Problem in collecting process list " + e.getMessage());
/* 458 */         if (!appman) {
/* 459 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 460 */           temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString(new StringBuilder().append("Problem in collecting process list").append(e.getMessage()).toString()) + " </span></td></tr></table>");
/* 461 */           out.write(temp.toString());
/* 462 */           out.flush();
/* 463 */           return null;
/*     */         }
/* 465 */         request.setAttribute("error", "Problem in collecting process list " + e.getMessage());
/* 466 */         return mapping.findForward("MySqlProcessList");
/*     */       }
/*     */     } else {
/* 469 */       AMLog.debug("MySql Action : Wrong API Key to trigger process list");
/* 470 */       if (!appman) {
/* 471 */         temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 472 */         temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.javaruntime.wrongapikey") + " </span></td></tr></table>");
/* 473 */         out.write(temp.toString());
/* 474 */         out.flush();
/* 475 */         return null;
/*     */       }
/* 477 */       request.setAttribute("error", FormatUtil.getString("am.javaruntime.wrongapikey"));
/* 478 */       return mapping.findForward("MySqlProcessList");
/*     */     }
/* 480 */     if (!appman) {
/* 481 */       temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 482 */       temp.append("<span class='bodytext'>");
/* 483 */       if (request.getParameter("synUrls") != null) {
/* 484 */         temp.append("Message from Managed Server ").append(EnterpriseUtil.getManagedServerIndex()).append(" : ");
/*     */       }
/* 486 */       temp.append(FormatUtil.getString("am.webclient.mysql.processlist.success")).append(" </span></td></tr></table>");
/* 487 */       out.write(temp.toString());
/* 488 */       out.flush();
/* 489 */       return null;
/*     */     }
/* 491 */     return mapping.findForward("MySqlProcessList");
/*     */   }
/*     */   
/*     */   public ActionForward displayprocesslist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 497 */     response.setContentType("text/html; charset=UTF-8");
/* 498 */     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yy, HH:mm:ss z");
/* 499 */     SimpleDateFormat sdfw = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss-SS", java.util.Locale.getDefault());
/* 500 */     AMConnectionPool pool = new AMConnectionPool();
/* 501 */     long colTime = 0L;
/* 502 */     String resid = request.getParameter("resourceid");
/* 503 */     String ipaddress = request.getParameter("IP");
/* 504 */     String uid = request.getParameter("UID");
/* 505 */     StringBuilder resourceids = new StringBuilder();
/* 506 */     PrintWriter out = response.getWriter();
/* 507 */     StringBuffer temp = new StringBuffer();
/* 508 */     String query = null;
/* 509 */     ResultSet rs = null;
/* 510 */     ArrayList urllist = new ArrayList();
/*     */     try
/*     */     {
/* 513 */       if (request.getParameter("synUrls") != null)
/*     */         try {
/* 515 */           temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 516 */           temp.append("<tr><td>");
/* 517 */           temp.append("<span class='bodytext'> <a class=\"resourcename\" href='https://" + AMCacheHandler.getAMServerPropertiesValue("am.external.hostname") + ":" + AMCacheHandler.getAMServerPropertiesValue("am.ssl.port") + "/DebugInfo.do?method=displayprocesslist&IP=" + ipaddress + "&UID=" + uid + "&apikey=" + request.getParameter("apikey") + "'target=_blank><font color=black>Process List from Managed Server " + EnterpriseUtil.getManagedServerIndex() + "</font> </a><br> </span>");
/* 518 */           temp.append("</td></tr>");
/* 519 */           temp.append("</table>");
/* 520 */           out.write(temp.toString());
/* 521 */           out.flush();
/* 522 */           return null;
/*     */         } catch (Exception e) {
/* 524 */           e.printStackTrace();
/*     */         }
/*     */       Properties list;
/* 527 */       if ((request.getRemoteUser() != null) || (APIServlet.validateAPIKey(request)))
/*     */       {
/* 529 */         if (ipaddress != null) {
/* 530 */           query = "select RESOURCEID,AM_ManagedObject.RESOURCENAME from AM_ManagedObject, InetService where TARGETADDRESS='" + ipaddress + "' and TYPE='MYSQL-DB-server' and AM_ManagedObject.RESOURCENAME=InetService.NAME";
/* 531 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 532 */           while (rs.next()) {
/* 533 */             resourceids.append(rs.getString("RESOURCEID")).append(",");
/*     */           }
/* 535 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */         
/* 538 */         if ((resourceids.length() != 0) && (ipaddress != null) && (uid != null)) {
/* 539 */           query = "select URL,COLLECTIONTIME,RESOURCEID from AM_MONITOR_DEBUG_INFO where TYPE='Process list' and RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") and REFERENCEID='" + uid + "' order by COLLECTIONTIME desc";
/*     */         }
/* 541 */         else if ((resourceids.length() != 0) && (ipaddress != null)) {
/* 542 */           query = "select URL,COLLECTIONTIME,RESOURCEID from AM_MONITOR_DEBUG_INFO where TYPE='Process list' and RESOURCEID IN(" + resourceids.substring(0, resourceids.length() - 1) + ") order by COLLECTIONTIME desc";
/*     */         }
/* 544 */         else if (uid != null) {
/* 545 */           query = "select URL,COLLECTIONTIME,RESOURCEID  from AM_MONITOR_DEBUG_INFO   where  TYPE='Process list' and REFERENCEID='" + uid + "' order by  COLLECTIONTIME desc";
/*     */         }
/*     */         
/* 548 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 549 */         while (rs.next()) {
/* 550 */           list = new Properties();
/* 551 */           list.setProperty("URL", rs.getString("URL"));
/* 552 */           Date logDate = new Date(rs.getLong("COLLECTIONTIME"));
/* 553 */           list.setProperty("DSPNAME", sdfw.format(logDate));
/* 554 */           list.setProperty("DSPNAME_EXT", sdf.format(logDate));
/* 555 */           urllist.add(list);
/*     */         }
/* 557 */         AMConnectionPool.closeStatement(rs);
/* 558 */         request.setAttribute("urllist", urllist);
/*     */       } else {
/* 560 */         temp.append("<table width='100%' cellpadding='0' cellspacing='0' class='lrtbdarkborder'>");
/* 561 */         temp.append("<tr><td><span class='bodytext'> " + FormatUtil.getString("am.javaruntime.wrongapikey") + " </span></td></tr></table>");
/* 562 */         out.write(temp.toString());
/* 563 */         out.flush();
/* 564 */         return null;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 568 */       e.printStackTrace();
/* 569 */       AMLog.debug("MySql Action : Problem in collecting process list" + e.getMessage());
/*     */     }
/*     */     finally {
/* 572 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 574 */     return mapping.findForward("MySqlShowProcessList");
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 581 */     response.setContentType("text/html; charset=UTF-8");
/* 582 */     String resourceid = request.getParameter("resourceid");
/* 583 */     ActionMessages messages = new ActionMessages();
/* 584 */     ActionErrors errors = new ActionErrors();
/* 585 */     int poll = 300;
/* 586 */     String username = null;
/* 587 */     String password = null;
/* 588 */     ResultSet rs = null;
/* 589 */     String name = null;
/* 590 */     String displayname = null;
/* 591 */     int configured = 1;
/*     */     try
/*     */     {
/*     */       try {
/* 595 */         rs = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 596 */         if (rs.next()) {
/* 597 */           name = rs.getString("RESOURCENAME");
/* 598 */           displayname = rs.getString("DISPLAYNAME");
/* 599 */           configured = rs.getInt("DCSTARTED");
/* 600 */           request.setAttribute("showdata", configured + "");
/*     */         }
/* 602 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception e) {
/* 605 */         e.printStackTrace();
/*     */       }
/*     */       
/* 608 */       username = (String)((DynaActionForm)form).get("username");
/* 609 */       password = (String)((DynaActionForm)form).get("password");
/* 610 */       String instance = (String)((DynaActionForm)form).get("instance");
/*     */       
/* 612 */       Integer pollInt = (Integer)((DynaActionForm)form).get("pollinterval");
/* 613 */       if ((username != null) && (username.length() == 0)) {
/* 614 */         username = null;
/* 615 */         messages.add("org.apache.struts.action.ERROR", new org.apache.struts.action.ActionMessage("hostresource.username"));
/*     */       }
/* 617 */       if (pollInt == null) {
/* 618 */         poll = 300;
/* 619 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */       } else {
/* 621 */         poll = pollInt.intValue() * 60;
/*     */       }
/* 623 */       if ((instance != null) && (instance.length() == 0)) {
/* 624 */         instance = null;
/* 625 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("oracle.instance"));
/*     */       }
/* 627 */       if (!errors.isEmpty()) {
/* 628 */         saveErrors(request, errors);
/* 629 */         request.setAttribute("configured", "false");
/* 630 */         if (request.getParameter("configured") != null) {
/* 631 */           request.setAttribute("configured", "true");
/*     */         }
/*     */       }
/* 634 */       ResourceConfig col = (ResourceConfig)this.api.getCollectData(name, "MYSQL");
/* 635 */       Properties TestProps = new Properties();
/* 636 */       TestProps.setProperty("HOST", col.getHostName());
/* 637 */       TestProps.setProperty("PORT", String.valueOf(col.getApplnDiscPort()));
/* 638 */       TestProps.setProperty("username", username);
/* 639 */       TestProps.setProperty("password", password);
/* 640 */       TestProps.setProperty("instance", instance);
/* 641 */       Properties authresult = null;
/* 642 */       AMDCInf amdc = null;
/*     */       try {
/* 644 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.mysql.datacollection.ScheduleMySqlDataCollection").newInstance();
/* 645 */         authresult = amdc.CheckAuthentication(TestProps);
/* 646 */         AMLog.debug("MySQL Edit Monitor Result=======>" + authresult);
/*     */       }
/*     */       catch (Exception e) {
/* 649 */         e.printStackTrace();
/*     */       }
/* 651 */       String result = authresult.getProperty("authentication");
/* 652 */       if (result.equals("failed")) {
/* 653 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.mysql.error", new String[] { authresult.getProperty("exception") })));
/* 654 */         saveErrors(request, errors);
/* 655 */         request.setAttribute("configured", "false");
/* 656 */         if (request.getParameter("configured") != null) {
/* 657 */           request.setAttribute("configured", "true");
/*     */         }
/* 659 */         request.setAttribute("displayname", displayname);
/* 660 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/*     */       }
/* 662 */       String newdisplayname = request.getParameter("displayname");
/* 663 */       TestProps.setProperty("name", name);
/* 664 */       TestProps.setProperty("pollinterval", String.valueOf(poll));
/* 665 */       TestProps.setProperty("displayname", newdisplayname);
/* 666 */       if (!amdc.ScheduleDataCollection(TestProps)) {
/* 667 */         AMConnectionPool update = AMConnectionPool.getInstance();
/* 668 */         String updatequery = "update AM_RESOURCECONFIG set ERRORMSG='Datacollection scheduling failed' where RESOURCEID='" + resourceid + "'";
/* 669 */         AMConnectionPool.executeUpdateStmt(updatequery);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 673 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 676 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/* 679 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*     */   }
/*     */   
/*     */   public Properties getMySqlDetails(int id)
/*     */   {
/* 684 */     Properties p = new Properties();
/* 685 */     ResultSet rs = null;
/* 686 */     String mysqldetailsquery = null;
/*     */     try {
/* 688 */       mysqldetailsquery = "select ATTRIBUTEID,CONFVALUE from AM_MYSQL_CONFIGURATION_INFO where ATTRIBUTEID IN(5258,5172,5219,5161,5264,5156,5224,5223,5188,5160) and LATEST=1 and RESOURCEID=" + id + "";
/* 689 */       rs = AMConnectionPool.executeQueryStmt(mysqldetailsquery);
/* 690 */       while (rs.next()) {
/* 691 */         p.setProperty(rs.getString("ATTRIBUTEID"), rs.getString("CONFVALUE"));
/*     */       }
/* 693 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 696 */       AMLog.fatal("MySQL : Exception in querying the data for MySQLDetails", e);
/* 697 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 700 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 702 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getAlerts(int id)
/*     */   {
/* 707 */     Properties alert = new Properties();
/* 708 */     ResultSet rs = null;
/* 709 */     String alertquery = null;
/*     */     try {
/* 711 */       alertquery = "select CATEGORY , SEVERITY from Alert where SOURCE=" + id + "";
/* 712 */       rs = AMConnectionPool.executeQueryStmt(alertquery);
/* 713 */       while (rs.next()) {
/* 714 */         alert.setProperty(rs.getString(1), "" + rs.getInt(2));
/*     */       }
/* 716 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 719 */       AMLog.fatal("MySQL : Exception in querying the data for Alert for MySQL", e);
/* 720 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 723 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 725 */     return alert;
/*     */   }
/*     */   
/*     */   public Properties getPerformance(int resid, long maxtime)
/*     */   {
/* 730 */     Properties p = new Properties();
/* 731 */     ResultSet rs = null;
/*     */     try {
/* 733 */       if (maxtime == 0L) {
/* 734 */         return p;
/*     */       }
/* 736 */       String dataquery = "select CONNECTIONTIME,REQUESTRATE,BYTESRECEIVEDRATE,BYTESSENDRATE from AM_MYSQLPERFORMANCE where RESOURCEID=" + resid + " and COLLECTIONTIME=" + maxtime + "";
/* 737 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 738 */       if (rs.next()) {
/* 739 */         p.setProperty("CONNECTIONTIME", String.valueOf(rs.getLong("CONNECTIONTIME")));
/* 740 */         p.setProperty("REQUESTRATE", String.valueOf(rs.getLong("REQUESTRATE")));
/* 741 */         p.setProperty("BYTESRECEIVEDRATE", String.valueOf(rs.getLong("BYTESRECEIVEDRATE")));
/* 742 */         p.setProperty("BYTESSENDRATE", String.valueOf(rs.getLong("BYTESSENDRATE")));
/*     */       }
/* 744 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 747 */       AMLog.fatal("MySQL : Problem in fetching the data for Performance", e);
/* 748 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 751 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 753 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getServerTuning(int resid, long maxtime)
/*     */   {
/* 758 */     Properties p = new Properties();
/* 759 */     ResultSet rs = null;
/*     */     try {
/* 761 */       if (maxtime == 0L) {
/* 762 */         return p;
/*     */       }
/* 764 */       String dataquery = "select OPENCONNECTIONS , ABORTEDCONNECTIONS ,  ABORTEDCLIENTS , THREADSUSED , THREADSINCACHE from AM_MYSQLSERVERTUNING where AM_MYSQLSERVERTUNING.RESOURCEID=" + resid + " and AM_MYSQLSERVERTUNING.COLLECTIONTIME=" + maxtime;
/* 765 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 766 */       if (rs.next()) {
/* 767 */         p.setProperty("OPENCONNECTIONS", String.valueOf(rs.getLong("OPENCONNECTIONS")));
/* 768 */         p.setProperty("ABORTEDCONNECTIONS", String.valueOf(rs.getLong("ABORTEDCONNECTIONS")));
/* 769 */         p.setProperty("ABORTEDCLIENTS", String.valueOf(rs.getLong("ABORTEDCLIENTS")));
/* 770 */         p.setProperty("THREADSUSED", String.valueOf(rs.getLong("THREADSUSED")));
/* 771 */         p.setProperty("THREADSINCACHE", String.valueOf(rs.getLong("THREADSINCACHE")));
/*     */       }
/* 773 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 776 */       AMLog.fatal("MySQL : Problem in fetching the data for Performance", e);
/*     */     }
/*     */     finally {
/* 779 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 781 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getMemoryHealth(int resid, long maxtime)
/*     */   {
/* 786 */     Properties p = new Properties();
/* 787 */     ResultSet rs = null;
/*     */     try {
/* 789 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 790 */       if (maxtime == 0L) {
/* 791 */         return p;
/*     */       }
/* 793 */       String dataquery = "select KEYHITRATE,QUERYCACHEHITRATE,KEYBUFFERUSED from AM_MYSQLMEMORY where AM_MYSQLMEMORY.RESOURCEID=" + resid + " and AM_MYSQLMEMORY.COLLECTIONTIME=" + maxtime + "";
/* 794 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 795 */       if (rs.next()) {
/* 796 */         p.setProperty("KEYHITRATE", String.valueOf(rs.getLong("KEYHITRATE")));
/* 797 */         p.setProperty("QUERYCACHEHITRATE", String.valueOf(rs.getLong("QUERYCACHEHITRATE")));
/* 798 */         p.setProperty("KEYBUFFERUSED", String.valueOf(rs.getLong("KEYBUFFERUSED")));
/*     */       }
/* 800 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 803 */       AMLog.fatal("MySQL : Problem in fetching the data for Memory", e);
/* 804 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 807 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 809 */     return p;
/*     */   }
/*     */   
/*     */   public Properties getQueryStats(int resourceid, long maxCollectiontime)
/*     */   {
/* 814 */     properties = new Properties();
/* 815 */     ResultSet results = null;
/*     */     try {
/* 817 */       String statsQuery = "select  DELETEDRATE, INSERTEDRATE, SELECTEDRATE, UPDATEDRATE from AM_MYSQLQUERYSTATS where RESOURCEID = '" + resourceid + "' and COLLECTIONTIME = '" + maxCollectiontime + "'";
/* 818 */       results = AMConnectionPool.executeQueryStmt(statsQuery);
/* 819 */       if (results.next()) {
/* 820 */         properties.setProperty("RATE_DELETED", String.valueOf(results.getDouble("DELETEDRATE")));
/* 821 */         properties.setProperty("RATE_INSERTED", String.valueOf(results.getDouble("INSERTEDRATE")));
/* 822 */         properties.setProperty("RATE_SELECTED", String.valueOf(results.getDouble("SELECTEDRATE")));
/* 823 */         properties.setProperty("RATE_UPDATED", String.valueOf(results.getDouble("UPDATEDRATE")));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 837 */       return properties;
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 827 */       AMLog.fatal("MySQL : Problem in fetching the data for Query Statistics : ", exception);
/* 828 */       exception.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       try {
/* 832 */         closeResultSet(results);
/*     */       } catch (Exception exception) {
/* 834 */         exception.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getReplicationStatus(int resourceid)
/*     */   {
/* 842 */     Properties replicationProperties = new Properties();
/* 843 */     ResultSet results = null;
/*     */     try {
/* 845 */       String query = "select * from AM_MYSQLREPLICATION where RESOURCEID = " + resourceid;
/* 846 */       results = AMConnectionPool.executeQueryStmt(query);
/* 847 */       if (results.next()) {
/* 848 */         replicationProperties.setProperty("RESOURCEID", String.valueOf(results.getLong("RESOURCEID")));
/* 849 */         replicationProperties.setProperty("SLAVE_IO_STATUS", results.getString("SLAVE_IO_STATUS"));
/* 850 */         replicationProperties.setProperty("MASTER_HOST", results.getString("MASTER_HOST"));
/* 851 */         replicationProperties.setProperty("MASTER_USER", results.getString("MASTER_USER"));
/* 852 */         replicationProperties.setProperty("MASTER_PORT", String.valueOf(results.getInt("MASTER_PORT")));
/* 853 */         replicationProperties.setProperty("SLAVE_IO_RUNNING", results.getString("SLAVE_IO_RUNNING"));
/* 854 */         replicationProperties.setProperty("SLAVE_SQL_RUNNING", results.getString("SLAVE_SQL_RUNNING"));
/* 855 */         replicationProperties.setProperty("LAST_ERROR", results.getString("LAST_ERROR"));
/* 856 */         replicationProperties.setProperty("SECONDS_BEHIND_MASTER", String.valueOf(results.getInt("SECONDS_BEHIND_MASTER")));
/*     */       }
/* 858 */       return replicationProperties;
/*     */     } catch (Exception exception) {
/* 860 */       AMLog.fatal("MySQL : Problem in fetching the data for Replication Statistics : ", exception);
/* 861 */       exception.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       try {
/* 865 */         closeResultSet(results);
/*     */       } catch (Exception exception) {
/* 867 */         exception.printStackTrace();
/*     */       }
/*     */     }
/* 870 */     return null;
/*     */   }
/*     */   
/*     */   private void closeResultSet(ResultSet rs)
/*     */   {
/*     */     try {
/* 876 */       if (rs != null) {
/* 877 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 881 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void genURLProcessList(String resid, long colTime, ArrayList processList, String resname, String referenceID, HashMap coloumnParams, boolean appman)
/*     */   {
/* 888 */     AMConnectionPool pool = new AMConnectionPool();
/*     */     try {
/* 890 */       String processListURL = com.adventnet.appmanager.util.GenURLForHTML.genURLProcessList(resid, Long.valueOf(colTime), processList, resname, coloumnParams, appman);
/* 891 */       AMConnectionPool.executeUpdateStmt("insert into AM_MONITOR_DEBUG_INFO values (" + resid + ",'" + processListURL + "','" + referenceID + "','Process list'," + colTime + ")");
/*     */     } catch (Exception e) {
/* 893 */       e = 
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 898 */         e;e.printStackTrace();
/*     */     }
/*     */     finally {}
/*     */   }
/*     */   
/*     */   private static class timeComparator implements java.util.Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/* 903 */       Properties ohm1 = (Properties)o1;
/* 904 */       Properties ohm2 = (Properties)o2;
/* 905 */       int cpu1 = Integer.valueOf(Integer.parseInt(ohm1.getProperty("Time"))).intValue();
/* 906 */       int cpu2 = Integer.valueOf(Integer.parseInt(ohm2.getProperty("Time"))).intValue();
/* 907 */       if (cpu1 == cpu2)
/* 908 */         return 0;
/* 909 */       if (cpu1 > cpu2) {
/* 910 */         return -1;
/*     */       }
/* 912 */       return 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static ArrayList convertStringtoArray(String commaseparated_commands)
/*     */   {
/* 918 */     ArrayList colValue = new ArrayList();
/* 919 */     if (commaseparated_commands != null) {
/* 920 */       StringTokenizer commandTokens = new StringTokenizer(commaseparated_commands, ",");
/* 921 */       while (commandTokens.hasMoreTokens()) {
/* 922 */         colValue.add(commandTokens.nextToken().trim());
/*     */       }
/*     */     }
/* 925 */     return colValue;
/*     */   }
/*     */   
/*     */ 
/*     */   public static boolean validateAPIKey(String apiKey)
/*     */   {
/* 931 */     String APIKey = "";
/* 932 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 933 */     ResultSet rs = null;
/* 934 */     boolean validateKey = false;
/*     */     try {
/* 936 */       String checkquery = "select * from AM_UserPasswordTable where APIKEY='" + apiKey + "'";
/* 937 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 938 */       while (rs.next()) {
/* 939 */         APIKey = rs.getString("APIKEY");
/* 940 */         if (APIKey.equals(apiKey)) {
/* 941 */           validateKey = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 946 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 949 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 951 */     return validateKey;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\mysql\struts\MySqlAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */