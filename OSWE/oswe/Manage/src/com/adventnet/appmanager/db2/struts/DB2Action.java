/*     */ package com.adventnet.appmanager.db2.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DB2Action
/*     */   extends DispatchAction
/*     */ {
/*  43 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*     */   
/*  45 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward details(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  49 */     AMActionForm amform = (AMActionForm)request.getAttribute("AMActionForm");
/*  50 */     ActionMessages messages = new ActionMessages();
/*  51 */     ActionErrors errors = new ActionErrors();
/*  52 */     this.mo.getReloadPeriod(request);
/*  53 */     String pollint = (String)request.getAttribute("reloadperiod");
/*     */     try
/*     */     {
/*  56 */       Long.parseLong(pollint);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  60 */       pollint = "300";
/*     */     }
/*  62 */     int configured = 1;
/*  63 */     String name = null;
/*  64 */     String displayname = null;
/*  65 */     String resourceid = request.getParameter("resourceid");
/*     */     
/*     */     try
/*     */     {
/*  69 */       String scripts_query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='Script Monitor' and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  70 */       ResultSet rs = AMConnectionPool.executeQueryStmt(scripts_query);
/*  71 */       ArrayList al = new ArrayList();
/*  72 */       while (rs.next())
/*     */       {
/*  74 */         al.add(rs.getString(1));
/*     */       }
/*  76 */       request.setAttribute("script_ids", al);
/*     */       try
/*     */       {
/*  79 */         if (rs != null)
/*     */         {
/*  81 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/*  86 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/*  91 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*  95 */     String queryMonitor = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='QueryMonitor' and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  96 */     System.out.println("the queryMonito========>:" + queryMonitor);
/*     */     
/*     */     try
/*     */     {
/* 100 */       ResultSet rsQry = AMConnectionPool.executeQueryStmt(queryMonitor);
/* 101 */       ArrayList ql = new ArrayList();
/* 102 */       while (rsQry.next())
/*     */       {
/* 104 */         ql.add(rsQry.getString(1));
/*     */       }
/* 106 */       request.setAttribute("query_ids", ql);
/*     */       try
/*     */       {
/* 109 */         if (rsQry != null)
/*     */         {
/* 111 */           AMConnectionPool.closeStatement(rsQry);
/*     */         }
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/* 116 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/* 121 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 127 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/* 128 */     if (map != null)
/*     */     {
/* 130 */       request.setAttribute("systeminfo", map);
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 135 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 136 */       if (modetails.next())
/*     */       {
/* 138 */         name = modetails.getString("RESOURCENAME");
/* 139 */         displayname = modetails.getString("DISPLAYNAME");
/* 140 */         configured = modetails.getInt("DCSTARTED");
/* 141 */         request.setAttribute("showdata", configured + "");
/* 142 */         if (modetails != null)
/*     */         {
/* 144 */           AMConnectionPool.closeStatement(modetails);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 149 */         if (modetails != null)
/*     */         {
/* 151 */           AMConnectionPool.closeStatement(modetails);
/*     */         }
/* 153 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 158 */       e.printStackTrace();
/*     */     }
/*     */     
/* 161 */     ResourceConfig db2 = (ResourceConfig)this.api.getCollectData(name, "DB2");
/* 162 */     ((DynaActionForm)form).set("username", db2.getUserName());
/* 163 */     ((DynaActionForm)form).set("password", db2.getPassword());
/* 164 */     ((DynaActionForm)form).set("instance", db2.getDatabaseName());
/* 165 */     ((DynaActionForm)form).set("displayname", displayname);
/* 166 */     ((DynaActionForm)form).set("pollinterval", new Integer(db2.getPollInterval() / 60));
/*     */     
/* 168 */     if (db2.getErrorMsg().equalsIgnoreCase("Data Collection configured. Kindly wait till the next polling interval"))
/*     */     {
/* 170 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.DataCollec.ErrMsg")));
/* 171 */       saveMessages(request, messages);
/*     */     }
/* 173 */     else if ((!db2.getErrorMsg().equalsIgnoreCase("Data Collection Sucessful")) && (db2.getErrorMsg().indexOf("Monitoring Not Started") == -1))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 179 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", db2.getErrorMsg()));
/* 180 */       saveErrors(request, errors);
/*     */     }
/* 182 */     request.setAttribute("error", db2.getErrorMsg());
/*     */     
/* 184 */     Properties details = new Properties();
/* 185 */     Properties p = new Properties();
/* 186 */     if (configured == 2)
/*     */     {
/* 188 */       p = getDB2Details(db2.getresourceID(), db2.getApplnDiscPort());
/*     */       
/*     */ 
/* 191 */       amform.setDatabases((ArrayList)p.get("rows"));
/*     */       
/*     */ 
/* 194 */       String selectedDatabaseId = amform.getDatabasesId();
/* 195 */       if (selectedDatabaseId == null)
/*     */       {
/* 197 */         selectedDatabaseId = p.getProperty("firstDatabaseId");
/* 198 */         if (selectedDatabaseId != null)
/* 199 */           amform.setDatabasesId(selectedDatabaseId);
/*     */       }
/* 201 */       Properties temp = new Properties();
/* 202 */       temp = (Properties)p.get("db");
/* 203 */       if (selectedDatabaseId != null)
/*     */       {
/* 205 */         details.put("db", (Properties)temp.get(selectedDatabaseId));
/*     */       }
/*     */       else
/*     */       {
/* 209 */         selectedDatabaseId = "-1";
/*     */       }
/* 211 */       details.put("dbmdetails", (Properties)p.get("dbmdetails"));
/* 212 */       details.put("rows", (ArrayList)p.get("rows"));
/* 213 */       details.put("selectedDatabaseId", selectedDatabaseId);
/* 214 */       request.setAttribute("db2details", details);
/*     */     }
/*     */     else
/*     */     {
/* 218 */       p.put("PORT", db2.getApplnDiscPort() + "");
/* 219 */       details.put("dbmdetails", p);
/* 220 */       details.put("rows", new ArrayList());
/* 221 */       request.setAttribute("db2details", details);
/*     */     }
/* 223 */     return mapping.findForward("DisplayDB2Data");
/*     */   }
/*     */   
/*     */   public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 228 */     ActionMessages messages = new ActionMessages();
/* 229 */     ActionErrors errors = new ActionErrors();
/* 230 */     this.mo.getReloadPeriod(request);
/*     */     
/* 232 */     int poll = 300;
/* 233 */     String username = null;
/* 234 */     String password = null;
/* 235 */     String result = null;
/* 236 */     String instance = null;
/* 237 */     Integer pollInt = null;
/* 238 */     String name = null;
/* 239 */     String displayname = null;
/* 240 */     String configured = null;
/* 241 */     String resourceid = request.getParameter("resourceid");
/*     */     
/*     */     try
/*     */     {
/* 245 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 246 */       if (modetails.next())
/*     */       {
/* 248 */         name = modetails.getString("RESOURCENAME");
/* 249 */         displayname = modetails.getString("DISPLAYNAME");
/* 250 */         configured = modetails.getString("DCSTARTED");
/* 251 */         if (modetails != null)
/*     */         {
/* 253 */           AMConnectionPool.closeStatement(modetails);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 258 */         if (modetails != null)
/*     */         {
/* 260 */           AMConnectionPool.closeStatement(modetails);
/*     */         }
/* 262 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 267 */       e.printStackTrace();
/*     */     }
/*     */     
/* 270 */     username = (String)((DynaActionForm)form).get("username");
/* 271 */     password = (String)((DynaActionForm)form).get("password");
/* 272 */     instance = (String)((DynaActionForm)form).get("instance");
/* 273 */     pollInt = (Integer)((DynaActionForm)form).get("pollinterval");
/*     */     
/* 275 */     if ((username != null) && (username.length() == 0))
/*     */     {
/* 277 */       username = "";
/* 278 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.username"));
/*     */     }
/* 280 */     if ((instance != null) && (instance.length() == 0))
/*     */     {
/* 282 */       instance = "";
/* 283 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("oracle.instance"));
/*     */     }
/* 285 */     if (pollInt == null)
/*     */     {
/* 287 */       poll = 300;
/* 288 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */     }
/*     */     else
/*     */     {
/* 292 */       poll = pollInt.intValue() * 60;
/*     */     }
/*     */     
/* 295 */     if (!errors.isEmpty())
/*     */     {
/* 297 */       saveErrors(request, errors);
/*     */     }
/* 299 */     ResourceConfig col = (ResourceConfig)this.api.getCollectData(name, "DB2");
/* 300 */     if ((col.getUserName().equals(username)) && (col.getPassword().equals(password)) && (col.getDatabaseName().equals(instance)))
/*     */     {
/*     */       try
/*     */       {
/* 304 */         String newdisplayname = request.getParameter("displayname");
/* 305 */         if (!displayname.equals(newdisplayname))
/*     */         {
/*     */ 
/* 308 */           String updatequery = "update AM_ManagedObject set DISPLAYNAME='" + newdisplayname + "' where AM_ManagedObject.RESOURCEID='" + col.getresourceID() + "'";
/* 309 */           AMConnectionPool.executeUpdateStmt(updatequery);
/* 310 */           EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*     */         }
/* 312 */         if (poll != col.getPollInterval() / 60)
/*     */         {
/* 314 */           AMDataCollectionHandler.getInstance().updateCollectData(name, "DB2", poll, true);
/*     */         }
/* 316 */         request.setAttribute("displayname", newdisplayname);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 320 */         e.printStackTrace();
/*     */       }
/* 322 */       result = "passed";
/*     */     }
/*     */     else
/*     */     {
/* 326 */       Properties TestProps = new Properties();
/* 327 */       TestProps.setProperty("HOST", col.getHostName());
/* 328 */       TestProps.setProperty("PORT", String.valueOf(col.getApplnDiscPort()));
/* 329 */       TestProps.setProperty("username", username);
/* 330 */       TestProps.setProperty("password", password);
/* 331 */       TestProps.setProperty("instance", instance);
/* 332 */       Properties authresult = null;
/* 333 */       AMDCInf amdc = null;
/*     */       try
/*     */       {
/* 336 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.db2.datacollection.ScheduleDB2DataCollection").newInstance();
/* 337 */         authresult = amdc.CheckAuthentication(TestProps);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 341 */         e.printStackTrace();
/*     */       }
/* 343 */       result = authresult.getProperty("authentication");
/*     */       
/* 345 */       if (result.equals("failed"))
/*     */       {
/* 347 */         if (configured.equals("2")) {
/* 348 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", "Unable to edit the monitor details as the connection to DB2 server could not be established. <br>Error Message:<br> " + authresult.getProperty("exception")));
/*     */         } else
/* 350 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", authresult.getProperty("error")));
/* 351 */         saveMessages(request, messages);
/* 352 */         request.setAttribute("reloadperiod", null);
/* 353 */         request.setAttribute("showconfigdiv", "true");
/*     */       }
/*     */       else
/*     */       {
/* 357 */         String newdisplayname = request.getParameter("displayname");
/* 358 */         displayname = newdisplayname;
/* 359 */         AMDataCollectionHandler.getInstance().updateCollectData(name, "DB2", poll, true);
/* 360 */         request.setAttribute("reloadperiod", poll + "");
/* 361 */         TestProps.setProperty("name", name);
/* 362 */         TestProps.setProperty("pollinterval", String.valueOf(poll));
/* 363 */         TestProps.setProperty("displayname", newdisplayname);
/* 364 */         if (!amdc.ScheduleDataCollection(TestProps))
/*     */         {
/* 366 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", "Datacollection scheduling failed"));
/* 367 */           saveMessages(request, messages);
/*     */         }
/* 369 */         request.setAttribute("displayname", newdisplayname);
/*     */       }
/*     */     }
/* 372 */     String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 373 */     if (request.getParameter("haid") != null)
/*     */     {
/* 375 */       path = path + "&haid=" + request.getParameter("haid");
/*     */     }
/* 377 */     if (result.equals("passed"))
/*     */     {
/* 379 */       return new ActionForward(path, true);
/*     */     }
/*     */     
/*     */ 
/* 383 */     return new ActionForward(path);
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getDB2Details(int resourceid, int port)
/*     */   {
/* 389 */     Properties p = new Properties();
/* 390 */     Properties dbmdetails = new Properties();
/* 391 */     Properties db = new Properties();
/* 392 */     ArrayList rows = new ArrayList();
/* 393 */     String firstDatabaseId = null;
/* 394 */     String firstDatabase = null;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 399 */       dbmdetails.setProperty("VERSION", "-");
/* 400 */       dbmdetails.setProperty("INSTANCENAME", "-");
/* 401 */       dbmdetails.setProperty("PORT", "-");
/* 402 */       dbmdetails.setProperty("NUMBEROFNODES", "-");
/* 403 */       dbmdetails.put("SERVERSTARTTIME", new Long(0L));
/* 404 */       dbmdetails.put("RESPONSETIME", new Long(0L));
/* 405 */       dbmdetails.setProperty("REMOTECONN", "-");
/* 406 */       dbmdetails.setProperty("LOCALCONN", "-");
/* 407 */       dbmdetails.setProperty("TOTALCONN", "-");
/* 408 */       dbmdetails.setProperty("AGENTSREGISTERED", "-");
/* 409 */       dbmdetails.setProperty("AGENTSWAITING", "-");
/* 410 */       dbmdetails.setProperty("AGENTSIDLE", "-");
/* 411 */       dbmdetails.setProperty("AGENTSACTIVE", "-");
/* 412 */       dbmdetails.setProperty("DB2STATUS", "-");
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 417 */       ResultSet rs = null;
/* 418 */       long collectiontime = getMaxCollectionTime(resourceid);
/* 419 */       String query = "select AM_DB2DETAILS.VERSION,AM_DB2DETAILS.INSTANCENAME,AM_DB2DETAILS.NUMBEROFNODES,AM_DB2DETAILS.SERVERSTARTTIME,AM_DB2CONNECTION_AGENTS.REMOTECONN,AM_DB2CONNECTION_AGENTS.LOCALCONN,AM_DB2CONNECTION_AGENTS.TOTALCONN,AM_DB2CONNECTION_AGENTS.AGENTSREGISTERED,AM_DB2CONNECTION_AGENTS.AGENTSWAITING,AM_DB2CONNECTION_AGENTS.AGENTSIDLE,AM_DB2CONNECTION_AGENTS.AGENTSACTIVE,AM_DB2CONNECTION_AGENTS.DB2STATUS,AM_ManagedObjectData.RESPONSETIME from AM_DB2DETAILS,AM_DB2CONNECTION_AGENTS,AM_ManagedObjectData where AM_ManagedObjectData.RESID=AM_DB2DETAILS.RESOURCEID and AM_DB2CONNECTION_AGENTS.RESOURCEID=AM_DB2DETAILS.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=AM_DB2CONNECTION_AGENTS.COLLECTIONTIME and AM_DB2CONNECTION_AGENTS.COLLECTIONTIME='" + collectiontime + "' and AM_DB2DETAILS.RESOURCEID='" + resourceid + "'";
/*     */       try
/*     */       {
/* 422 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 423 */         if (rs.next())
/*     */         {
/* 425 */           dbmdetails.setProperty("VERSION", rs.getString("VERSION"));
/* 426 */           dbmdetails.setProperty("INSTANCENAME", rs.getString("INSTANCENAME"));
/* 427 */           dbmdetails.setProperty("PORT", String.valueOf(port));
/* 428 */           dbmdetails.setProperty("NUMBEROFNODES", rs.getString("NUMBEROFNODES"));
/* 429 */           dbmdetails.put("SERVERSTARTTIME", new Long(rs.getLong("SERVERSTARTTIME")));
/*     */           
/* 431 */           dbmdetails.put("RESPONSETIME", new Long(rs.getLong("RESPONSETIME")));
/*     */           
/* 433 */           dbmdetails.setProperty("REMOTECONN", rs.getString("REMOTECONN"));
/* 434 */           dbmdetails.setProperty("LOCALCONN", rs.getString("LOCALCONN"));
/* 435 */           dbmdetails.setProperty("TOTALCONN", rs.getString("TOTALCONN"));
/* 436 */           dbmdetails.setProperty("AGENTSREGISTERED", rs.getString("AGENTSREGISTERED"));
/* 437 */           dbmdetails.setProperty("AGENTSWAITING", rs.getString("AGENTSWAITING"));
/* 438 */           dbmdetails.setProperty("AGENTSIDLE", rs.getString("AGENTSIDLE"));
/* 439 */           dbmdetails.setProperty("AGENTSACTIVE", rs.getString("AGENTSACTIVE"));
/* 440 */           dbmdetails.setProperty("DB2STATUS", rs.getString("DB2STATUS"));
/*     */         }
/*     */         try
/*     */         {
/* 444 */           if (rs != null)
/*     */           {
/* 446 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 455 */         AMLog.fatal("DB2 : Exception in querying the data for DBM details", e);
/* 456 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/* 460 */       query = "select AM_DB2DATABASEDETAILS.RESOURCEID,AM_DB2DATABASEDETAILS.PARENTRESOURCEID,AM_DB2DATABASEDETAILS.DBNAME,AM_DB2DATABASEDETAILS.DBALIAS,AM_DB2DATABASEDETAILS.DBPATH,AM_DB2DATABASEDETAILS.DBSTATUS,AM_DB2DATABASEDETAILS.DATABASECONNECTEDTIME,COALESCE(AM_DB2DATABASESTATUS.RATEOFWORK,0) as RATEOFWORK,COALESCE(AM_DB2DATABASESTATUS.SORTSOVERFLOWED,0) as SORTSOVERFLOWED,COALESCE(AM_DB2DATABASESTATUS.RATEOFSUCCESSFULSQL,0) as RATEOFSUCCESSFULSQL,COALESCE(AM_DB2DATABASESTATUS.RATEOFFAILEDSQL,0) as RATEOFFAILEDSQL,COALESCE(AM_DB2DATABASESTATUS.LOGUTIL,0) as LOGUTIL,COALESCE(AM_DB2DATABASESTATUS.CATCACHEHITRATIO,0) as CATCACHEHITRATIO,COALESCE(AM_DB2DATABASESTATUS.PKGCACHEHITRATIO,0) as PKGCACHEHITRATIO,COALESCE(AM_DB2DATABASESTATUS.RATEOFDEADLOCKS,0) as RATEOFDEADLOCKS,COALESCE(AM_DB2DATABASESTATUS.DBSIZE,0) as DBSIZE,COALESCE(AM_DB2BUFFERPOOLSTATS.DIRECTREADS,0) as DIRECTREADS,COALESCE(AM_DB2BUFFERPOOLSTATS.DIRECTWRITES,0) as DIRECTWRITES,COALESCE(AM_DB2BUFFERPOOLSTATS.DATAPAGEHITRATIO,0) as DATAPAGEHITRATIO,COALESCE(AM_DB2BUFFERPOOLSTATS.INDEXHITRATIO,0) as INDEXHITRATIO,COALESCE(AM_DB2BUFFERPOOLSTATS.BUFFERPOOLHITRATIO,0) as BUFFERPOOLHITRATIO,COALESCE(AM_DB2BUFFERPOOLSTATS.COLLECTIONTIME,0) as COLLECTIONTIME ,(CASE when (ROWSINSERTEDRATE is null or ROWSINSERTEDRATE<0) then 0 else ROWSINSERTEDRATE end) as ROWSINSERTEDRATE,(CASE when (ROWSDELETEDRATE is null or ROWSDELETEDRATE<0) then 0 else ROWSDELETEDRATE end) as ROWSDELETEDRATE, (CASE when (ROWSSELECTEDRATE is null or ROWSSELECTEDRATE<0) then 0 else ROWSSELECTEDRATE end) as ROWSSELECTEDRATE, (CASE when (ROWSUPDATEDRATE is null or ROWSUPDATEDRATE <0) then 0 else ROWSUPDATEDRATE end) as ROWSUPDATEDRATE  from AM_DB2DATABASEDETAILS left outer join AM_DB2DATABASESTATUS on AM_DB2DATABASESTATUS.RESOURCEID=AM_DB2DATABASEDETAILS.RESOURCEID and AM_DB2DATABASESTATUS.COLLECTIONTIME='" + collectiontime + "' left outer join AM_DB2BUFFERPOOLSTATS on AM_DB2BUFFERPOOLSTATS.RESOURCEID=AM_DB2DATABASEDETAILS.RESOURCEID and AM_DB2BUFFERPOOLSTATS.COLLECTIONTIME=AM_DB2DATABASESTATUS.COLLECTIONTIME  left outer join AM_DB2_ROWSTATS on AM_DB2_ROWSTATS.RESOURCEID=AM_DB2DATABASEDETAILS.RESOURCEID and AM_DB2_ROWSTATS.COLLECTIONTIME=AM_DB2DATABASESTATUS.COLLECTIONTIME   where AM_DB2DATABASEDETAILS.PARENTRESOURCEID='" + resourceid + "' order by AM_DB2DATABASEDETAILS.DBNAME";
/*     */       try
/*     */       {
/* 463 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 464 */         int i = 1;
/* 465 */         int j = 1;
/* 466 */         while (rs.next())
/*     */         {
/* 468 */           String resId = null;
/* 469 */           Properties p1 = new Properties();
/* 470 */           Properties dataProps = new Properties();
/*     */           
/*     */ 
/* 473 */           p1.put("RESOURCEID", new Integer(0));
/* 474 */           p1.setProperty("DBNAME", "-");
/* 475 */           p1.setProperty("DBALIAS", "-");
/* 476 */           p1.setProperty("DBPATH", "-");
/* 477 */           p1.setProperty("DBSTATUS", "-");
/* 478 */           p1.put("DATABASECONNECTEDTIME", new Long(0L));
/*     */           
/* 480 */           p1.setProperty("RATEOFWORK", "-");
/* 481 */           p1.setProperty("SORTSOVERFLOWED", "-");
/* 482 */           p1.setProperty("RATEOFSUCCESSFULSQL", "-");
/* 483 */           p1.setProperty("RATEOFFAILEDSQL", "-");
/* 484 */           p1.setProperty("LOGUTIL", "-");
/* 485 */           p1.setProperty("CATCACHEHITRATIO", "-");
/* 486 */           p1.setProperty("PKGCACHEHITRATIO", "-");
/* 487 */           p1.setProperty("RATEOFDEADLOCKS", "-");
/* 488 */           p1.setProperty("DBSIZE", "-");
/*     */           
/* 490 */           p1.setProperty("DIRECTREADS", "-");
/* 491 */           p1.setProperty("DIRECTWRITES", "-");
/* 492 */           p1.setProperty("DATAPAGEHITRATIO", "-");
/* 493 */           p1.setProperty("INDEXHITRATIO", "-");
/* 494 */           p1.setProperty("BUFFERPOOLHITRATIO", "-");
/* 495 */           p1.setProperty("ROWSINSERTEDRATE", "-");
/* 496 */           p1.setProperty("ROWSDELETEDRATE", "-");
/* 497 */           p1.setProperty("ROWSSELECTEDRATE", "-");
/* 498 */           p1.setProperty("ROWSUPDATEDRATE", "-");
/*     */           
/*     */ 
/*     */           try
/*     */           {
/* 503 */             resId = rs.getString("RESOURCEID");
/* 504 */             dataProps.setProperty("label", rs.getString("DBNAME"));
/* 505 */             dataProps.setProperty("value", resId);
/* 506 */             rows.add(dataProps);
/*     */             
/* 508 */             p1.put("RESOURCEID", resId);
/* 509 */             p1.setProperty("DBNAME", rs.getString("DBNAME"));
/* 510 */             p1.setProperty("DBALIAS", rs.getString("DBALIAS"));
/* 511 */             p1.setProperty("DBPATH", rs.getString("DBPATH"));
/* 512 */             p1.setProperty("DBSTATUS", rs.getString("DBSTATUS"));
/* 513 */             p1.put("DATABASECONNECTEDTIME", new Long(rs.getLong("DATABASECONNECTEDTIME")));
/*     */             
/* 515 */             p1.setProperty("RATEOFWORK", rs.getString("RATEOFWORK"));
/* 516 */             p1.setProperty("SORTSOVERFLOWED", rs.getString("SORTSOVERFLOWED"));
/* 517 */             p1.setProperty("RATEOFSUCCESSFULSQL", rs.getString("RATEOFSUCCESSFULSQL"));
/* 518 */             p1.setProperty("RATEOFFAILEDSQL", rs.getString("RATEOFFAILEDSQL"));
/* 519 */             p1.setProperty("LOGUTIL", rs.getString("LOGUTIL"));
/* 520 */             p1.setProperty("CATCACHEHITRATIO", rs.getString("CATCACHEHITRATIO"));
/* 521 */             p1.setProperty("PKGCACHEHITRATIO", rs.getString("PKGCACHEHITRATIO"));
/* 522 */             p1.setProperty("RATEOFDEADLOCKS", rs.getString("RATEOFDEADLOCKS"));
/* 523 */             p1.setProperty("DBSIZE", rs.getString("DBSIZE"));
/*     */             
/* 525 */             p1.setProperty("DIRECTREADS", rs.getString("DIRECTREADS"));
/* 526 */             p1.setProperty("DIRECTWRITES", rs.getString("DIRECTWRITES"));
/* 527 */             p1.setProperty("DATAPAGEHITRATIO", rs.getString("DATAPAGEHITRATIO"));
/* 528 */             p1.setProperty("INDEXHITRATIO", rs.getString("INDEXHITRATIO"));
/* 529 */             p1.setProperty("BUFFERPOOLHITRATIO", rs.getString("BUFFERPOOLHITRATIO"));
/* 530 */             p1.setProperty("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/*     */             
/* 532 */             p1.setProperty("ROWSINSERTEDRATE", rs.getString("ROWSINSERTEDRATE"));
/* 533 */             p1.setProperty("ROWSDELETEDRATE", rs.getString("ROWSDELETEDRATE"));
/* 534 */             p1.setProperty("ROWSSELECTEDRATE", rs.getString("ROWSSELECTEDRATE"));
/* 535 */             p1.setProperty("ROWSUPDATEDRATE", rs.getString("ROWSUPDATEDRATE"));
/* 536 */             if ((i == 1) && (rs.getLong("COLLECTIONTIME") != -1L))
/*     */             {
/* 538 */               firstDatabaseId = resId;
/* 539 */               i++;
/*     */             }
/* 541 */             if (j == 1)
/*     */             {
/* 543 */               firstDatabase = resId;
/* 544 */               j++;
/*     */             }
/* 546 */             ArrayList tablestats = getTableSpaceStats(resId, collectiontime);
/* 547 */             p1.put("TABLESTATS", tablestats);
/*     */           }
/*     */           catch (Exception exp)
/*     */           {
/* 551 */             exp.printStackTrace();
/*     */           }
/* 553 */           db.put(resId, p1);
/*     */         }
/* 555 */         if (firstDatabaseId == null)
/*     */         {
/* 557 */           firstDatabaseId = firstDatabase;
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 562 */         AMLog.fatal("DB2 : Exception in querying the data for DB details", e);
/* 563 */         e.printStackTrace();
/*     */       }
/* 565 */       if (rs != null)
/*     */       {
/* 567 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/* 569 */       p.put("dbmdetails", dbmdetails);
/* 570 */       p.put("db", db);
/* 571 */       p.put("rows", rows);
/* 572 */       if (firstDatabaseId != null) {
/* 573 */         p.setProperty("firstDatabaseId", firstDatabaseId);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 577 */       ex.printStackTrace();
/*     */     }
/* 579 */     return p;
/*     */   }
/*     */   
/*     */   public long getMaxCollectionTime(int resourceid)
/*     */   {
/* 584 */     long time = 0L;
/*     */     
/* 586 */     String query = "select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + resourceid;
/*     */     try
/*     */     {
/* 589 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 590 */       if (rs.next())
/*     */       {
/* 592 */         time = rs.getLong(1);
/*     */       }
/* 594 */       if (rs != null)
/*     */       {
/* 596 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 601 */       AMLog.fatal("DB2 : Exception in query while getting maximum collectiontime ", e);
/* 602 */       e.printStackTrace();
/*     */     }
/* 604 */     return time;
/*     */   }
/*     */   
/*     */   public ArrayList getTableSpaceStats(String resourceid, long collectiontime) {
/* 608 */     ArrayList tablespace = new ArrayList();
/*     */     try
/*     */     {
/* 611 */       String tablespacequery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, ALLOCATEDMEMORY, PERCENTFREEMEMORY, FREEMEMORY from AM_PARENTCHILDMAPPER inner join AM_DB2_TABLESPACESTATS on AM_DB2_TABLESPACESTATS.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_DB2_TABLESPACESTATS.COLLECTIONTIME=" + collectiontime + " left outer join AM_ManagedObject on  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedObject.TYPE='DB2-tablespace' and  AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/* 612 */       tablespace = this.mo.getRows(tablespacequery);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 616 */       ex.printStackTrace();
/*     */     }
/* 618 */     return tablespace;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\db2\struts\DB2Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */