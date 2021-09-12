/*     */ package com.adventnet.appmanager.server.sybase.struts;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SybaseAction
/*     */   extends DispatchAction
/*     */ {
/*  47 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*     */   
/*  49 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward details(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  53 */     AMActionForm amform = (AMActionForm)request.getAttribute("AMActionForm");
/*  54 */     ActionMessages messages = new ActionMessages();
/*  55 */     ActionErrors errors = new ActionErrors();
/*  56 */     this.mo.getReloadPeriod(request);
/*  57 */     String pollint = (String)request.getAttribute("reloadperiod");
/*     */     try {
/*  59 */       Long.parseLong(pollint);
/*     */     }
/*     */     catch (Exception e) {
/*  62 */       pollint = "300";
/*     */     }
/*  64 */     int configured = 1;
/*  65 */     String name = null;
/*  66 */     String displayname = null;
/*  67 */     String resourceid = request.getParameter("resourceid");
/*  68 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */     try
/*     */     {
/*  71 */       String scripts_query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='Script Monitor' and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  72 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(scripts_query);
/*  73 */       ArrayList al = new ArrayList();
/*  74 */       while (rs.next())
/*     */       {
/*  76 */         al.add(rs.getString(1));
/*     */       }
/*  78 */       request.setAttribute("script_ids", al);
/*     */       try
/*     */       {
/*  81 */         closeResultSet(rs);
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/*  85 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/*  90 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  95 */     String queryMonitor = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type ='QueryMonitor' and AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*  96 */     System.out.println("the queryMonito========>:" + queryMonitor);
/*     */     
/*     */     try
/*     */     {
/* 100 */       AMConnectionPool.getInstance();ResultSet rsQry = AMConnectionPool.executeQueryStmt(queryMonitor);
/* 101 */       ArrayList ql = new ArrayList();
/* 102 */       while (rsQry.next())
/*     */       {
/* 104 */         ql.add(rsQry.getString(1));
/*     */       }
/* 106 */       request.setAttribute("query_ids", ql);
/*     */       try
/*     */       {
/* 109 */         closeResultSet(rsQry);
/*     */       }
/*     */       catch (Exception exc1)
/*     */       {
/* 113 */         exc1.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception exc1)
/*     */     {
/* 118 */       exc1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 122 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/* 123 */     if (map != null) {
/* 124 */       request.setAttribute("systeminfo", map);
/*     */     }
/*     */     try
/*     */     {
/* 128 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 129 */       if (modetails.next()) {
/* 130 */         name = modetails.getString("RESOURCENAME");
/* 131 */         displayname = modetails.getString("DISPLAYNAME");
/* 132 */         configured = modetails.getInt("DCSTARTED");
/*     */         
/* 134 */         request.setAttribute("showdata", configured + "");
/* 135 */         closeResultSet(modetails);
/*     */       }
/*     */       else {
/* 138 */         closeResultSet(modetails);
/* 139 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 143 */       e.printStackTrace();
/*     */     }
/*     */     
/* 146 */     ResourceConfig sybase = (ResourceConfig)this.api.getCollectData(name, "SYBASE");
/* 147 */     ((DynaActionForm)form).set("username", sybase.getUserName());
/* 148 */     ((DynaActionForm)form).set("password", sybase.getPassword());
/* 149 */     ((DynaActionForm)form).set("instance", sybase.getDatabaseName());
/* 150 */     ((DynaActionForm)form).set("displayname", displayname);
/* 151 */     ((DynaActionForm)form).set("pollinterval", new Integer(sybase.getPollInterval() / 60));
/*     */     
/* 153 */     if (sybase.getErrorMsg().equalsIgnoreCase("Data Collection configured. Kindly wait till the next polling interval")) {
/* 154 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.DataCollec.ErrMsg")));
/* 155 */       saveMessages(request, messages);
/*     */     }
/* 157 */     else if ((!sybase.getErrorMsg().equalsIgnoreCase("Data Collection Sucessful")) && (sybase.getErrorMsg().indexOf("Monitoring Not Started") == -1))
/*     */     {
/*     */ 
/*     */ 
/* 161 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", sybase.getErrorMsg()));
/* 162 */       saveErrors(request, errors);
/*     */     }
/* 164 */     request.setAttribute("error", sybase.getErrorMsg());
/*     */     
/* 166 */     Properties details = new Properties();
/* 167 */     Properties p = new Properties();
/*     */     
/* 169 */     if (configured == 2)
/*     */     {
/* 171 */       p = getSybaseDetails(sybase.getresourceID(), sybase.getApplnDiscPort());
/*     */       
/*     */ 
/* 174 */       amform.setDatabases((ArrayList)p.get("rows"));
/*     */       
/*     */ 
/* 177 */       String selectedDatabaseId = amform.getDatabasesId();
/* 178 */       if (selectedDatabaseId == null) {
/* 179 */         selectedDatabaseId = p.getProperty("firstDatabaseId");
/* 180 */         if (selectedDatabaseId != null)
/* 181 */           amform.setDatabasesId(selectedDatabaseId);
/*     */       }
/* 183 */       Properties temp = new Properties();
/* 184 */       temp = (Properties)p.get("db");
/* 185 */       if (selectedDatabaseId != null) {
/* 186 */         details.put("db", (Properties)temp.get(selectedDatabaseId));
/*     */       }
/*     */       else {
/* 189 */         selectedDatabaseId = "-1";
/*     */       }
/* 191 */       details.put("dbmdetails", (Properties)p.get("dbmdetails"));
/* 192 */       details.put("rows", (ArrayList)p.get("rows"));
/* 193 */       details.put("allCommands", (ArrayList)p.get("allCommands"));
/* 194 */       details.put("allTransactions", (ArrayList)p.get("allTransactions"));
/* 195 */       details.put("selectedDatabaseId", selectedDatabaseId);
/* 196 */       request.setAttribute("sybasedetails", details);
/*     */     }
/*     */     else {
/* 199 */       p.put("PORT", sybase.getApplnDiscPort() + "");
/* 200 */       details.put("dbmdetails", p);
/* 201 */       details.put("rows", new ArrayList());
/* 202 */       request.setAttribute("sybasedetails", details);
/*     */     }
/* 204 */     return mapping.findForward("DisplaySybaseData");
/*     */   }
/*     */   
/*     */   public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 209 */     ActionMessages messages = new ActionMessages();
/* 210 */     ActionErrors errors = new ActionErrors();
/* 211 */     this.mo.getReloadPeriod(request);
/*     */     
/* 213 */     int poll = 300;
/* 214 */     String username = null;
/* 215 */     String password = null;
/* 216 */     String result = null;
/* 217 */     String instance = null;
/* 218 */     String jconnect = null;
/* 219 */     Integer pollInt = null;
/* 220 */     String name = null;
/* 221 */     String displayname = null;
/* 222 */     String configured = null;
/* 223 */     String resourceid = request.getParameter("resourceid");
/*     */     
/* 225 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */     try {
/* 227 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 228 */       if (modetails.next()) {
/* 229 */         name = modetails.getString("RESOURCENAME");
/* 230 */         displayname = modetails.getString("DISPLAYNAME");
/* 231 */         configured = modetails.getString("DCSTARTED");
/* 232 */         closeResultSet(modetails);
/*     */       }
/*     */       else {
/* 235 */         closeResultSet(modetails);
/* 236 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 240 */       e.printStackTrace();
/*     */     }
/*     */     
/* 243 */     username = (String)((DynaActionForm)form).get("username");
/* 244 */     password = (String)((DynaActionForm)form).get("password");
/* 245 */     instance = (String)((DynaActionForm)form).get("instance");
/* 246 */     pollInt = (Integer)((DynaActionForm)form).get("pollinterval");
/*     */     try
/*     */     {
/* 249 */       jconnect = (String)((DynaActionForm)form).get("jconnect");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 253 */       AMLog.debug("Sybase Action Error : database message =" + e.getMessage());
/*     */     }
/*     */     
/*     */ 
/* 257 */     if ((username != null) && (username.length() == 0)) {
/* 258 */       username = "";
/* 259 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.username"));
/*     */     }
/* 261 */     if ((instance != null) && (instance.length() == 0)) {
/* 262 */       instance = "";
/* 263 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("oracle.instance"));
/*     */     }
/* 265 */     if (pollInt == null) {
/* 266 */       poll = 300;
/* 267 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("hostresource.pollinterval"));
/*     */     }
/*     */     else {
/* 270 */       poll = pollInt.intValue() * 60;
/*     */     }
/*     */     
/* 273 */     if (!errors.isEmpty()) {
/* 274 */       saveErrors(request, errors);
/*     */     }
/* 276 */     ResourceConfig col = (ResourceConfig)this.api.getCollectData(name, "SYBASE");
/* 277 */     if ((col.getUserName().equals(username)) && (col.getPassword().equals(password)) && (col.getDatabaseName().equals(instance))) {
/*     */       try {
/* 279 */         String newdisplayname = request.getParameter("displayname");
/* 280 */         if (!displayname.equals(newdisplayname)) {
/* 281 */           AMConnectionPool update = AMConnectionPool.getInstance();
/* 282 */           String updatequery = "update AM_ManagedObject set DISPLAYNAME='" + newdisplayname + "' where AM_ManagedObject.RESOURCEID='" + col.getresourceID() + "'";
/* 283 */           AMConnectionPool.executeUpdateStmt(updatequery);
/* 284 */           EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*     */         }
/* 286 */         if (poll != col.getPollInterval() / 60) {
/* 287 */           AMDataCollectionHandler.getInstance().updateCollectData(name, "SYBASE", poll, true);
/*     */         }
/* 289 */         request.setAttribute("displayname", newdisplayname);
/*     */       }
/*     */       catch (Exception e) {
/* 292 */         e.printStackTrace();
/*     */       }
/* 294 */       result = "passed";
/*     */     }
/*     */     else {
/* 297 */       Properties TestProps = new Properties();
/* 298 */       TestProps.setProperty("HOST", col.getHostName());
/* 299 */       TestProps.setProperty("PORT", String.valueOf(col.getApplnDiscPort()));
/* 300 */       TestProps.setProperty("username", username);
/* 301 */       TestProps.setProperty("password", password);
/* 302 */       TestProps.setProperty("instance", instance);
/*     */       try
/*     */       {
/* 305 */         TestProps.setProperty("jconnect", jconnect);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 309 */         AMLog.debug("Sybase Action Error :" + e.getMessage());
/*     */       }
/* 311 */       Properties authresult = null;
/* 312 */       AMDCInf amdc = null;
/*     */       try {
/* 314 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.sybase.datacollection.ScheduleSybaseDataCollection").newInstance();
/* 315 */         authresult = amdc.CheckAuthentication(TestProps);
/*     */       }
/*     */       catch (Exception e) {
/* 318 */         e.printStackTrace();
/*     */       }
/* 320 */       result = authresult.getProperty("authentication");
/*     */       
/* 322 */       if (result.equals("failed"))
/*     */       {
/* 324 */         if (configured.equals("2")) {
/* 325 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.webclient.sybase.errorMsg", new String[] { authresult.getProperty("exception") })));
/*     */         } else
/* 327 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", authresult.getProperty("error")));
/* 328 */         saveMessages(request, messages);
/* 329 */         request.setAttribute("reloadperiod", null);
/* 330 */         request.setAttribute("showconfigdiv", "true");
/*     */       }
/*     */       else {
/* 333 */         String newdisplayname = request.getParameter("displayname");
/* 334 */         displayname = newdisplayname;
/* 335 */         AMDataCollectionHandler.getInstance().updateCollectData(name, "Sybase", poll, true);
/* 336 */         request.setAttribute("reloadperiod", poll + "");
/* 337 */         TestProps.setProperty("name", name);
/* 338 */         TestProps.setProperty("pollinterval", String.valueOf(poll));
/* 339 */         TestProps.setProperty("displayname", newdisplayname);
/* 340 */         if (!amdc.ScheduleDataCollection(TestProps)) {
/* 341 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", "Datacollection scheduling failed"));
/* 342 */           saveMessages(request, messages);
/*     */         }
/* 344 */         request.setAttribute("displayname", newdisplayname);
/*     */       }
/*     */     }
/* 347 */     String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 348 */     if (request.getParameter("haid") != null) {
/* 349 */       path = path + "&haid=" + request.getParameter("haid");
/*     */     }
/* 351 */     if (result.equals("passed")) {
/* 352 */       return new ActionForward(path, true);
/*     */     }
/*     */     
/* 355 */     return new ActionForward(path);
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getSybaseDetails(int resourceid, int port)
/*     */   {
/* 361 */     Properties p = new Properties();
/* 362 */     Properties dbmdetails = new Properties();
/* 363 */     Properties db = new Properties();
/* 364 */     Properties memorydetails = new Properties();
/* 365 */     ArrayList rows = new ArrayList();
/* 366 */     String firstDatabaseId = null;
/* 367 */     String firstDatabase = null;
/* 368 */     ArrayList DB = new ArrayList();
/* 369 */     ArrayList allCommands = new ArrayList();
/* 370 */     ArrayList allTransactions = new ArrayList();
/*     */     
/*     */     try
/*     */     {
/* 374 */       dbmdetails.setProperty("VERSION", "-");
/* 375 */       dbmdetails.setProperty("HOST", "-");
/* 376 */       dbmdetails.setProperty("PORT", "-");
/* 377 */       dbmdetails.setProperty("RESPONSETIME", "-");
/* 378 */       dbmdetails.setProperty("TotalMemory", "-");
/* 379 */       dbmdetails.setProperty("UsedMemory", "-");
/* 380 */       dbmdetails.setProperty("FreeMemory", "-");
/* 381 */       dbmdetails.setProperty("MemoryPercentage", "-");
/* 382 */       dbmdetails.setProperty("TOTALREMOTECONNECTION", "-");
/* 383 */       dbmdetails.setProperty("ACTIVEREMOTECONNECTIOn", "-");
/* 384 */       dbmdetails.setProperty("TOTALUSERCONNECTION", "-");
/* 385 */       dbmdetails.setProperty("ACTIVEUSERCONNECTION", "-");
/* 386 */       dbmdetails.setProperty("USERCONNECTIONPERCENTAGE", "-");
/*     */       
/*     */ 
/*     */ 
/* 390 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 391 */       ResultSet rs = null;
/* 392 */       long collectiontime = getMaxCollectionTime(resourceid);
/*     */       
/* 394 */       String query = "select AM_SYBASEDETAILS.VERSION,AM_SYBASEDETAILS.HOST,AM_ManagedObjectData.RESPONSETIME from AM_SYBASEDETAILS,AM_ManagedObjectData where AM_ManagedObjectData.RESID=AM_SYBASEDETAILS.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME='" + collectiontime + "' and AM_SYBASEDETAILS.RESOURCEID='" + resourceid + "'";
/*     */       try
/*     */       {
/* 397 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 398 */         if (rs.next()) {
/* 399 */           dbmdetails.setProperty("VERSION", rs.getString("VERSION"));
/* 400 */           dbmdetails.setProperty("HOST", rs.getString("HOST"));
/* 401 */           dbmdetails.setProperty("PORT", String.valueOf(port));
/*     */           
/* 403 */           dbmdetails.put("RESPONSETIME", new Long(rs.getLong("RESPONSETIME")));
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 408 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 414 */         AMLog.fatal("Sybase : Exception in querying the data for DBM details", e);
/* 415 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 421 */         String memory_query = "Select * from AM_SYBASEMEMORYDETAILS where RESOURCEID='" + resourceid + "' and COLLECTIONTIME=" + collectiontime;
/*     */         
/* 423 */         rs = AMConnectionPool.executeQueryStmt(memory_query);
/* 424 */         if (rs.next())
/*     */         {
/* 426 */           dbmdetails.put("TotalMemory", Double.valueOf(rs.getDouble("TOTALMEMORY")));
/* 427 */           dbmdetails.put("UsedMemory", Double.valueOf(rs.getDouble("USEDMEMORY")));
/* 428 */           dbmdetails.put("FreeMemory", Double.valueOf(rs.getDouble("FREEMEMORY")));
/* 429 */           dbmdetails.put("MemoryPercentage", Double.valueOf(rs.getDouble("MEMORYPERCENT")));
/*     */         }
/*     */         try {
/* 432 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e) {
/* 437 */         AMLog.fatal("Sybase : Exception in querying the data for DBM details", e);
/* 438 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 444 */         String memory_query = "Select * from AM_SYBASECONNECTIONDETAILS where RESOURCEID='" + resourceid + "' and COLLECTIONTIME=" + collectiontime;
/*     */         
/* 446 */         rs = AMConnectionPool.executeQueryStmt(memory_query);
/* 447 */         if (rs.next())
/*     */         {
/* 449 */           dbmdetails.put("TOTALREMOTECONNECTION", Long.valueOf(rs.getLong("TOTALREMOTECONNECTIONS")));
/* 450 */           dbmdetails.put("ACTIVEREMOTECONNECTIOn", Long.valueOf(rs.getLong("ACTIVEREMOTECONNECTIONS")));
/* 451 */           dbmdetails.put("REMOTECONNECTIONPERCENTAGE", Double.valueOf(rs.getDouble("REMOTECONNECTIONPERCENTAGE")));
/* 452 */           dbmdetails.put("TOTALUSERCONNECTION", Long.valueOf(rs.getLong("TOTALUSERCONNECTIONS")));
/* 453 */           dbmdetails.put("ACTIVEUSERCONNECTION", Long.valueOf(rs.getLong("ACTIVEUSERCONNECTIONS")));
/* 454 */           dbmdetails.put("USERCONNECTIONPERCENTAGE", Double.valueOf(rs.getDouble("USERCONNECTIONPERCENTAGE")));
/*     */         }
/*     */         try {
/* 457 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e) {
/* 462 */         AMLog.fatal("Sybase : Exception in querying the data for DBM details", e);
/* 463 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 469 */         String process_query = "Select * from AM_SYBASECOMMANDPROCESSDETAILS where RESOURCEID='" + resourceid + "' and COLLECTIONTIME=" + collectiontime;
/*     */         
/*     */ 
/* 472 */         rs = AMConnectionPool.executeQueryStmt(process_query);
/* 473 */         while (rs.next()) {
/* 474 */           Properties props = new Properties();
/* 475 */           props.setProperty("HOSTNAME", rs.getString("HOSTNAME"));
/* 476 */           props.setProperty("IPADDRESS", rs.getString("IPADDRESS"));
/* 477 */           props.setProperty("PROCESSNAME", rs.getString("PROCESSNAME"));
/* 478 */           props.setProperty("USERNAME", rs.getString("USERNAME"));
/* 479 */           props.setProperty("DBNAME", rs.getString("DBNAME"));
/* 480 */           props.setProperty("COMMAND", rs.getString("COMMAND"));
/* 481 */           props.setProperty("STATUS", rs.getString("STATUS"));
/* 482 */           props.setProperty("PHYSICALIO", rs.getString("PHYSICALIO"));
/* 483 */           props.setProperty("MEMUSAGE", rs.getString("MEMUSAGE"));
/* 484 */           props.setProperty("TIME_BLOCKED", rs.getString("TIME_BLOCKED"));
/* 485 */           allCommands.add(props);
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 490 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e) {
/* 495 */         AMLog.fatal("Sybase : Exception in querying the data for current process details", e);
/* 496 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 503 */         String process_query = "Select * from AM_SYBASETRANSACTIONDETAILS where RESOURCEID='" + resourceid + "' and COLLECTIONTIME=" + collectiontime;
/*     */         
/*     */ 
/* 506 */         rs = AMConnectionPool.executeQueryStmt(process_query);
/* 507 */         while (rs.next()) {
/* 508 */           Properties props = new Properties();
/* 509 */           props.setProperty("TYPE", rs.getString("TYPE"));
/* 510 */           props.setProperty("COORDINATOR", rs.getString("COORDINATOR"));
/* 511 */           props.setProperty("STATE", rs.getString("STATE"));
/* 512 */           props.setProperty("CONNECTION", rs.getString("CONNECTION"));
/* 513 */           props.setProperty("DBNAME", rs.getString("DBNAME"));
/* 514 */           props.setProperty("PROGRAM_NAME", rs.getString("PROGRAM_NAME"));
/* 515 */           props.setProperty("TRANSACTION_NAME", rs.getString("TRANSACTION_NAME"));
/* 516 */           props.setProperty("STARTTIME", rs.getString("STARTTIME"));
/*     */           
/* 518 */           allTransactions.add(props);
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 523 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e) {
/* 528 */         AMLog.fatal("Sybase : Exception in querying the data for transaction details", e);
/* 529 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 534 */       String DBDetails_query = "select * from AM_SYBASEDATABASEDETAILS where PARENTRESOURCEID='" + resourceid + "' and COLLECTIONTIME=" + getMaxCollectionTimeDbDetails(resourceid, "AM_SYBASEDATABASEDETAILS");
/*     */       try
/*     */       {
/* 537 */         rs = AMConnectionPool.executeQueryStmt(DBDetails_query);
/* 538 */         while (rs.next()) {
/* 539 */           String name = rs.getString("NAME");
/* 540 */           String size = rs.getString("SIZE");
/* 541 */           String creator = rs.getString("CREATOR");
/* 542 */           String resid = rs.getString("RESOURCEID");
/* 543 */           String totalsize = rs.getString("TOTALSIZE");
/* 544 */           String usedpercentage = rs.getString("USEDPERCENTAGE");
/* 545 */           Properties p1 = new Properties();
/* 546 */           p1.setProperty("NAME", name);
/* 547 */           p1.setProperty("SIZE", size);
/* 548 */           p1.setProperty("CREATOR", creator);
/* 549 */           p1.setProperty("RESID", resid);
/* 550 */           p1.setProperty("TOTALDBSIZE", totalsize);
/* 551 */           p1.setProperty("USEDPERCENTAGE", usedpercentage);
/* 552 */           DB.add(p1);
/*     */         }
/*     */         
/* 555 */         for (int i = 0; i < DB.size() - 1; i++)
/*     */         {
/* 557 */           for (int j = i + 1; j < DB.size(); j++)
/*     */           {
/* 559 */             Properties list1 = (Properties)DB.get(i);
/* 560 */             Properties list2 = (Properties)DB.get(j);
/* 561 */             int v1 = Float.valueOf(list1.getProperty("USEDPERCENTAGE")).intValue();
/* 562 */             int v2 = Float.valueOf(list2.getProperty("USEDPERCENTAGE")).intValue();
/* 563 */             if (v1 < v2)
/*     */             {
/* 565 */               DB.set(i, list2);
/* 566 */               DB.set(j, list1);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 571 */         System.out.println("the sorted list is===>" + DB);
/*     */         try {
/* 573 */           closeResultSet(rs);
/*     */         }
/*     */         catch (Exception exc) {}
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 579 */         AMLog.fatal("Sybase : Exception in querying the data for DBM details", e);
/* 580 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 585 */       p.put("dbmdetails", dbmdetails);
/* 586 */       p.put("db", db);
/* 587 */       p.put("rows", DB);
/* 588 */       p.put("allCommands", allCommands);
/* 589 */       p.put("allTransactions", allTransactions);
/*     */       
/* 591 */       if (firstDatabaseId != null) {
/* 592 */         p.setProperty("firstDatabaseId", firstDatabaseId);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 596 */       ex.printStackTrace();
/*     */     }
/* 598 */     return p;
/*     */   }
/*     */   
/*     */ 
/*     */   public long getMaxCollectionTime(int resourceid)
/*     */   {
/* 604 */     long time = 0L;
/* 605 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */     
/* 607 */     String query = "select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID=" + resourceid;
/*     */     try {
/* 609 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 610 */       if (rs.next()) {
/* 611 */         time = rs.getLong(1);
/*     */       }
/* 613 */       closeResultSet(rs);
/*     */     }
/*     */     catch (Exception e) {
/* 616 */       AMLog.fatal("Sybase : Exception in query while getting maximum collectiontime ", e);
/* 617 */       e.printStackTrace();
/*     */     }
/* 619 */     return time;
/*     */   }
/*     */   
/*     */   public long getMaxCollectionTimeDbDetails(int resourceid, String tbName)
/*     */   {
/* 624 */     time = 0L;
/* 625 */     ResultSet rs = null;
/* 626 */     String query = "select max(COLLECTIONTIME) from " + tbName + " where PARENTRESOURCEID=" + resourceid;
/*     */     try {
/* 628 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 629 */       if (rs.next()) {
/* 630 */         time = rs.getLong(1);
/*     */       }
/* 632 */       closeResultSet(rs);
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
/*     */ 
/* 646 */       return time;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 635 */       AMLog.fatal("Sybase : Exception in query while getting maximum collectiontime from table:" + tbName, e);
/* 636 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 639 */         if (rs != null) {
/* 640 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       } catch (Exception ex) {
/* 643 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void closeResultSet(ResultSet set)
/*     */   {
/*     */     try
/*     */     {
/* 653 */       if (set != null)
/*     */       {
/* 655 */         AMConnectionPool.closeStatement(set);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 660 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\server\sybase\struts\SybaseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */