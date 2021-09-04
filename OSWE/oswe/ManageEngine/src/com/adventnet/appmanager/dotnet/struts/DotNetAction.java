/*     */ package com.adventnet.appmanager.dotnet.struts;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
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
/*     */ public final class DotNetAction
/*     */   extends Action
/*     */ {
/*  46 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*     */   
/*  48 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  52 */     ActionMessages messages = new ActionMessages();
/*  53 */     ActionErrors errors = new ActionErrors();
/*  54 */     this.mo.getReloadPeriod(request);
/*  55 */     String pollint = (String)request.getAttribute("reloadperiod");
/*  56 */     if (request.getParameter("showconfigdiv") != null)
/*     */     {
/*  58 */       request.setAttribute("reloadperiod", null);
/*     */     }
/*  60 */     String resourceid = request.getParameter("resourceid");
/*  61 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollint));
/*  62 */     if (map != null)
/*     */     {
/*  64 */       request.setAttribute("systeminfo", map);
/*     */     }
/*     */     
/*  67 */     String name = null;
/*  68 */     String displayname = null;
/*  69 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  70 */     int configured = 1;
/*     */     try
/*     */     {
/*  73 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  74 */       if (modetails.next())
/*     */       {
/*  76 */         name = modetails.getString("RESOURCENAME");
/*  77 */         displayname = modetails.getString("DISPLAYNAME");
/*  78 */         request.setAttribute("displayname", displayname);
/*  79 */         configured = modetails.getInt("DCSTARTED");
/*  80 */         request.setAttribute("showdata", configured + "");
/*     */       }
/*     */       else
/*     */       {
/*  84 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*     */       }
/*  86 */       if (modetails != null) {
/*  87 */         modetails.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     }
/*     */     
/*  94 */     request.setAttribute("DotNetAgentAvailable", "false");
/*  95 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  98 */       String targetName = null;
/*  99 */       String query = "SELECT TARGETNAME FROM AM_ManagedObject,InetService WHERE AM_ManagedObject.RESOURCENAME=InetService.NAME AND TYPE='.Net' AND AM_ManagedObject.RESOURCEID='" + resourceid + "'";
/* 100 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 101 */       if (rs.next())
/*     */       {
/* 103 */         targetName = rs.getString(1).toLowerCase();
/*     */       }
/* 105 */       if (rs != null)
/*     */       {
/* 107 */         rs.close();
/*     */       }
/* 109 */       AMLog.debug("TARGET NAME IN DotNetAction is : " + targetName);
/* 110 */       query = "SELECT * FROM apm_instances WHERE TYPE='DOTNET'";
/* 111 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 112 */       while (rs.next())
/*     */       {
/* 114 */         String insightResourceID = rs.getString("RESOURCEID");
/* 115 */         String insightApplicationID = rs.getString("APPLICATIONID");
/* 116 */         String host = rs.getString("HOST").toLowerCase();
/* 117 */         AMLog.debug("insightResourceID :" + insightResourceID + "\t insightApplicationID:" + insightApplicationID + "\thost:" + host);
/* 118 */         if (targetName.indexOf(host) != -1)
/*     */         {
/* 120 */           request.setAttribute("DotNetAgentAvailable", "true");
/* 121 */           request.setAttribute("insightResourceID", insightResourceID);
/* 122 */           request.setAttribute("insightApplicationID", insightApplicationID);
/* 123 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 129 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 133 */       if (rs != null)
/*     */       {
/* 135 */         rs.close();
/*     */       }
/*     */     }
/* 138 */     String haid = request.getParameter("haid");
/* 139 */     String appName = request.getParameter("appName");
/* 140 */     request.setAttribute("haid", haid);
/* 141 */     request.setAttribute("name", name);
/* 142 */     request.setAttribute("appName", appName);
/* 143 */     String selectedscheme = "";
/* 144 */     if (request.getParameter("selectedscheme") != null)
/*     */     {
/* 146 */       selectedscheme = request.getParameter("selectedscheme");
/* 147 */       request.setAttribute("selectedscheme", selectedscheme);
/*     */     }
/* 149 */     String selectedSkin = "Grey";
/* 150 */     if (request.getParameter("selectedSkin") != null)
/*     */     {
/* 152 */       selectedSkin = request.getParameter("selectedSkin");
/* 153 */       request.setAttribute("selectedskin", selectedSkin);
/*     */     }
/*     */     
/* 156 */     ResourceConfig mssql = (ResourceConfig)this.api.getCollectData(name, "DOTNET");
/* 157 */     if ((request.getParameter("configure") != null) && (request.getParameter("configure").equals("true"))) {
/* 158 */       int poll = 300;
/* 159 */       String username = null;
/* 160 */       String password = null;
/* 161 */       AMActionForm amform = (AMActionForm)form;
/* 162 */       username = amform.getUsername();
/* 163 */       password = amform.getPassword();
/* 164 */       poll = amform.getPollInterval();
/* 165 */       poll *= 60;
/*     */       
/* 167 */       if ((username != null) && (username.length() == 0))
/*     */       {
/* 169 */         username = null;
/* 170 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.username"));
/*     */       }
/* 172 */       if (!errors.isEmpty()) {
/* 173 */         saveErrors(request, errors);
/* 174 */         request.setAttribute("configured", "false");
/* 175 */         if (request.getParameter("configured") != null) {
/* 176 */           request.setAttribute("configured", "true");
/* 177 */           request.setAttribute("dbdetails", getDBDetails(Integer.parseInt(resourceid)));
/*     */         }
/* 179 */         return mapping.getInputForward();
/*     */       }
/* 181 */       ResourceConfig col = (ResourceConfig)this.api.getCollectData(name, "DOTNET");
/* 182 */       Properties TestProps = new Properties();
/* 183 */       TestProps.setProperty("HOST", col.getTargetAddress());
/* 184 */       TestProps.setProperty("name", name);
/* 185 */       TestProps.setProperty("pollinterval", String.valueOf(poll));
/*     */       
/* 187 */       TestProps.setProperty("PORT", String.valueOf(col.getApplnDiscPort()));
/* 188 */       TestProps.setProperty("username", username);
/* 189 */       TestProps.setProperty("password", password);
/* 190 */       Properties authresult = null;
/* 191 */       AMDCInf amdc = null;
/*     */       try
/*     */       {
/* 194 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.dotnet.datacollection.ScheduleDotNetDataCollection").newInstance();
/* 195 */         authresult = amdc.CheckAuthentication(TestProps);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 199 */         e.printStackTrace();
/*     */       }
/* 201 */       String result = authresult.getProperty("authentication");
/* 202 */       if (result.equals("failed"))
/*     */       {
/* 204 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", authresult.getProperty("error")));
/* 205 */         saveMessages(request, messages);
/* 206 */         request.setAttribute("configured", "false");
/* 207 */         if (request.getParameter("configured") != null) {
/* 208 */           request.setAttribute("configured", "true");
/* 209 */           request.setAttribute("dbdetails", getDBDetails(Integer.parseInt(resourceid)));
/*     */         }
/* 211 */         request.setAttribute("reloadperiod", null);
/* 212 */         request.setAttribute("showconfigdiv", "true");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 218 */         String newdisplayname = request.getParameter("displayname");
/*     */         
/* 220 */         displayname = newdisplayname;
/* 221 */         request.setAttribute("reloadperiod", poll + "");
/* 222 */         AMConnectionPool update = AMConnectionPool.getInstance();
/* 223 */         String updatequery = "update AM_ManagedObject  set  DISPLAYNAME='" + newdisplayname + "' , DCSTARTED=2 where RESOURCEID='" + resourceid + "'";
/* 224 */         AMConnectionPool.executeUpdateStmt(updatequery);
/* 225 */         EnterpriseUtil.addUpdateQueryToFile(updatequery);
/* 226 */         updatequery = "update AM_RESOURCECONFIG set ERRORMSG='Data Collection reconfigured. Kindly wait till the next polling interval' where RESOURCEID='" + resourceid + "'";
/* 227 */         AMConnectionPool.executeUpdateStmt(updatequery);
/* 228 */         request.setAttribute("displayname", newdisplayname);
/* 229 */         amdc.ScheduleDataCollection(TestProps);
/*     */       }
/*     */       
/* 232 */       String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 233 */       if (request.getParameter("haid") != null)
/*     */       {
/* 235 */         path = path + "&haid=" + request.getParameter("haid");
/*     */       }
/* 237 */       if (result.equals("passed"))
/*     */       {
/* 239 */         return new ActionForward(path, true);
/*     */       }
/*     */       
/*     */ 
/* 243 */       request.setAttribute("performance", getPerformance(mssql.getresourceID()));
/* 244 */       path = path + "&showconfigdiv=true&configure=false";
/* 245 */       return new ActionForward(path);
/*     */     }
/*     */     
/* 248 */     mssql = null;
/* 249 */     mssql = (ResourceConfig)this.api.getCollectData(name, "DOTNET");
/* 250 */     String details = request.getParameter("details");
/* 251 */     if (details == null)
/*     */     {
/* 253 */       details = "Availability";
/*     */     }
/* 255 */     request.setAttribute("configured", "true");
/* 256 */     request.setAttribute("details", details);
/* 257 */     request.setAttribute("resourceid", String.valueOf(mssql.getresourceID()));
/*     */     
/* 259 */     AMActionForm amform1 = (AMActionForm)form;
/*     */     
/* 261 */     amform1.setUsername(mssql.getUserName());
/* 262 */     amform1.setPassword(mssql.getPassword());
/* 263 */     amform1.setDisplayname(displayname);
/* 264 */     amform1.setPollInterval(mssql.getPollInterval() / 60);
/*     */     
/* 266 */     request.setAttribute("dbdetails", getDBDetails(mssql.getresourceID()));
/* 267 */     request.setAttribute("details", "reconfigure");
/*     */     
/*     */ 
/*     */ 
/* 271 */     if (mssql.getErrorMsg().equalsIgnoreCase("Data Collection reconfigured. Kindly wait till the next polling interval"))
/*     */     {
/* 273 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString(mssql.getErrorMsg())));
/* 274 */       saveMessages(request, messages);
/*     */     }
/* 276 */     else if ((!mssql.getErrorMsg().equalsIgnoreCase("Data Collection Successful")) && (mssql.getErrorMsg().indexOf("Monitoring Not Started") == -1))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 282 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString(mssql.getErrorMsg())));
/* 283 */       saveErrors(request, errors);
/*     */     }
/* 285 */     request.setAttribute("error", mssql.getErrorMsg());
/* 286 */     if (details.equals("Availability")) {
/* 287 */       request.setAttribute("performance", getPerformance(mssql.getresourceID()));
/* 288 */       request.setAttribute("dbdetails", getDBDetails(mssql.getresourceID()));
/* 289 */       return mapping.findForward("DisplayDotNetData");
/*     */     }
/*     */     
/* 292 */     if (details.equals("DB"))
/*     */     {
/* 294 */       System.out.println("Inside the DotNet Apps Details Page");
/* 295 */       Hashtable dbs = getDBDetails(mssql.getresourceID());
/* 296 */       request.setAttribute("dbdetails", dbs);
/* 297 */       String dbid = request.getParameter("dbid");
/* 298 */       for (Enumeration e = dbs.keys(); e.hasMoreElements();)
/*     */       {
/* 300 */         String dbname = (String)e.nextElement();
/* 301 */         Properties appprop = (Properties)dbs.get(dbname);
/* 302 */         if (appprop.getProperty("DBID").equals(dbid))
/*     */         {
/* 304 */           request.setAttribute("performance", appprop);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 309 */       return mapping.findForward("DisplayDotNetData");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 315 */     return mapping.findForward("DisplayDotNetData");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Properties getPerformance(int resid)
/*     */   {
/* 322 */     long maxtime = 0L;
/* 323 */     Properties p = new Properties();
/* 324 */     p.setProperty("LOGICALTHREADS", "-");
/* 325 */     p.setProperty("PHYSICALTHREADS", "-");
/* 326 */     p.setProperty("QUEUELENGTH", "-");
/* 327 */     p.setProperty("CONTENTIONPERMIN", "-");
/* 328 */     p.setProperty("CURRENTMEMORY", "-");
/* 329 */     p.setProperty("CONNECTIONSPERMIN", "-");
/* 330 */     p.setProperty("BYTESSENTPERMIN", "-");
/* 331 */     p.setProperty("BYTESRECEIVEDPERMIN", "-");
/* 332 */     p.setProperty("EXCEPTIONSPERMIN", "-");
/* 333 */     p.setProperty("RUNTIMECHECKSPERMIN", "-");
/* 334 */     p.setProperty("GC", "-");
/* 335 */     p.setProperty("JIT", "-");
/*     */     
/*     */ 
/* 338 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_DOTNET_PERFORMANCE where RESOURCEID=" + resid + "";
/*     */     try {
/* 340 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 341 */       ResultSet rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 342 */       if (rs.next()) {
/* 343 */         maxtime = rs.getLong(1);
/*     */       }
/* 345 */       rs.close();
/* 346 */       if (maxtime == 0L) {
/* 347 */         return p;
/*     */       }
/* 349 */       String dataquery = "select  * from AM_DOTNET_PERFORMANCE where AM_DOTNET_PERFORMANCE.RESOURCEID=" + resid + " and AM_DOTNET_PERFORMANCE.COLLECTIONTIME=" + maxtime;
/* 350 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 351 */       if (rs.next())
/*     */       {
/* 353 */         p.setProperty("LOGICALTHREADS", String.valueOf(rs.getDouble("LOGICALTHREADS")));
/* 354 */         p.setProperty("PHYSICALTHREADS", String.valueOf(rs.getDouble("PHYSICALTHREADS")));
/* 355 */         p.setProperty("QUEUELENGTH", String.valueOf(rs.getDouble("QUEUELENGTH")));
/* 356 */         p.setProperty("CONTENTIONPERMIN", String.valueOf(rs.getDouble("CONTENTIONPERMIN")));
/* 357 */         p.setProperty("CURRENTMEMORY", String.valueOf(Math.round(rs.getDouble("CURRENTMEMORY") / 1048576.0D * 100.0D) / 100L));
/* 358 */         p.setProperty("CONNECTIONSPERMIN", String.valueOf(rs.getDouble("CONNECTIONSPERMIN")));
/* 359 */         p.setProperty("BYTESSENTPERMIN", String.valueOf(rs.getDouble("BYTESSENTPERMIN")));
/* 360 */         p.setProperty("BYTESRECEIVEDPERMIN", String.valueOf(rs.getDouble("BYTESRECEIVEDPERMIN")));
/* 361 */         p.setProperty("EXCEPTIONSPERMIN", String.valueOf(rs.getDouble("EXCEPTIONSPERMIN")));
/* 362 */         p.setProperty("RUNTIMECHECKSPERMIN", String.valueOf(rs.getDouble("RUNTIMECHECKSPERMIN")));
/* 363 */         p.setProperty("JIT", String.valueOf(rs.getDouble("JIT")));
/* 364 */         p.setProperty("GC", String.valueOf(rs.getDouble("GC")));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 370 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 373 */       AMLog.fatal("DOTNET : Problem in fetching the data for Performance", e);
/* 374 */       e.printStackTrace();
/*     */     }
/* 376 */     return p;
/*     */   }
/*     */   
/*     */   public Hashtable getDBDetails(int resid) {
/* 380 */     long maxtime = 0L;
/* 381 */     String maxtimequery = "select max(COLLECTIONTIME) from AM_DOTNET_APPSTATS  , AM_EAR where AM_DOTNET_APPSTATS.RESOURCEID=AM_EAR.EARID and AM_EAR.PARENTID=" + resid;
/* 382 */     Hashtable h = new Hashtable();
/*     */     try {
/* 384 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/* 385 */       ResultSet rs = AMConnectionPool.executeQueryStmt(maxtimequery);
/* 386 */       if (rs.next()) {
/* 387 */         maxtime = rs.getLong(1);
/*     */       }
/* 389 */       rs.close();
/* 390 */       if (maxtime == 0L) {
/* 391 */         return h;
/*     */       }
/* 393 */       String dataquery = "select  AM_ManagedObject.DISPLAYNAME , AM_ManagedObject.RESOURCEID as Resid , AM_DOTNET_APPSTATS.* from   AM_DOTNET_APPSTATS , AM_EAR , AM_ManagedObject where AM_DOTNET_APPSTATS.RESOURCEID=AM_EAR.EARID and AM_ManagedObject.RESOURCEID=AM_EAR.EARID and  AM_EAR.PARENTID=" + resid + " and AM_DOTNET_APPSTATS.COLLECTIONTIME=" + maxtime;
/* 394 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/* 395 */       while (rs.next())
/*     */       {
/* 397 */         Properties p = new Properties();
/* 398 */         p.setProperty("QUEUEDREQUESTS", String.valueOf(rs.getDouble("QUEUEDREQUESTS")));
/* 399 */         p.setProperty("ACTIVESESSIONS", String.valueOf(rs.getDouble("ACTIVESESSIONS")));
/* 400 */         p.setProperty("PENDINGTRANSACTIONS", String.valueOf(rs.getDouble("PENDINGTRANSACTIONS")));
/* 401 */         p.setProperty("ERRORSPERMIN", String.valueOf(rs.getDouble("ERRORSPERMIN")));
/* 402 */         p.setProperty("REQUESTSPERMIN", String.valueOf(rs.getDouble("REQUESTSPERMIN")));
/* 403 */         p.setProperty("TIMEOUTPERMIN", String.valueOf(rs.getDouble("TIMEOUTPERMIN")));
/* 404 */         p.setProperty("BYTESRECEIVEDPERMIN", String.valueOf(rs.getDouble("BYTESRECEIVEDPERMIN")));
/* 405 */         p.setProperty("BYTESSENTPERMIN", String.valueOf(rs.getDouble("BYTESSENTPERMIN")));
/* 406 */         p.setProperty("TRANSACTIOSPERMIN", String.valueOf(rs.getDouble("TRANSACTIOSPERMIN")));
/* 407 */         p.setProperty("ABNTRANSPERMIN", String.valueOf(rs.getDouble("ABNTRANSPERMIN")));
/*     */         
/* 409 */         p.setProperty("DBID", String.valueOf(rs.getInt("Resid")));
/* 410 */         h.put(rs.getString("DISPLAYNAME"), p);
/*     */       }
/* 412 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 415 */       AMLog.fatal("DOTNET : Problem in fetching the data for Performance", e);
/*     */     }
/* 417 */     return h;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\dotnet\struts\DotNetAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */