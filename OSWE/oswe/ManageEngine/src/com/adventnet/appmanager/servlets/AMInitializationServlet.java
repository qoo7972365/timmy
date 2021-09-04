/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AMServicePackFixes;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.MonitorsAdder;
/*     */ import com.adventnet.appmanager.struts.actions.AMProductCommunicator;
/*     */ import com.adventnet.appmanager.struts.actions.AnamolyJob;
/*     */ import com.adventnet.appmanager.struts.actions.BusyJob;
/*     */ import com.adventnet.appmanager.struts.actions.SummaryMailer;
/*     */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*     */ import com.adventnet.appmanager.util.ChildMOHandler;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*     */ import com.adventnet.management.scheduler.Scheduler;
/*     */ import com.manageengine.apminsight.apm.client.util.ApmReferencedClientUtil;
/*     */ import com.manageengine.apminsight.client.util.APMInsightClientUtilAPI;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMInitializationServlet
/*     */   extends HttpServlet
/*     */ {
/*  45 */   private ServletContext servletContext = null;
/*     */   
/*     */ 
/*     */   private static void waitTillMonAdder()
/*     */   {
/*  50 */     int temp_count = 1;
/*  51 */     while (!MonitorsAdder.isCmdone())
/*     */     {
/*  53 */       if (temp_count == 360) {
/*     */         break;
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  59 */         Thread.sleep(500L);
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/*  63 */         exc.printStackTrace();
/*     */       }
/*  65 */       temp_count++;
/*     */     }
/*     */   }
/*     */   
/*     */   public void init(ServletConfig sConfig)
/*     */     throws ServletException
/*     */   {
/*  72 */     super.init(sConfig);
/*  73 */     BusyJob bj = new BusyJob();
/*  74 */     AnamolyJob aj = new AnamolyJob();
/*  75 */     SummaryMailer sm = new SummaryMailer();
/*  76 */     SummaryMailer.obj_ref.put("summarymailer", sm);
/*     */     
/*  78 */     sm.start();
/*     */     
/*     */ 
/*  81 */     AMProductCommunicator apmComm = new AMProductCommunicator();
/*  82 */     apmComm.start();
/*  83 */     Constants.isAnomalyAllowed = FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed();
/*  84 */     if (FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed()) {
/*     */       try {
/*  86 */         Date date = new Date();
/*  87 */         date = new Date(ReportDataUtilities.returnNextHour());
/*     */         
/*  89 */         Scheduler.getScheduler("main").scheduleTask(aj, date);
/*  90 */         Scheduler.getScheduler("main").scheduleTask(bj, date);
/*     */       }
/*     */       catch (Exception ex) {
/*  93 */         System.out.println("***PROBLEM IN INITIALIZING THE ANOMALY THREAD****");
/*  94 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*  97 */     this.servletContext = sConfig.getServletContext();
/*  98 */     this.servletContext.setAttribute("PROCESSTEMPLATE", "0");
/*  99 */     this.servletContext.setAttribute("SERVICETEMPLATE", "1");
/* 100 */     initDefaultTabs(this.servletContext);
/*     */     
/*     */ 
/* 103 */     APMInsightClientUtilAPI.init(new ApmReferencedClientUtil());
/*     */     
/* 105 */     int wait = 0;
/* 106 */     while (!Constants.queries_inserted)
/*     */     {
/* 108 */       wait++;
/*     */       try
/*     */       {
/* 111 */         Thread.sleep(300L);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 115 */         e.printStackTrace();
/*     */       }
/* 117 */       if (wait >= 200)
/*     */       {
/* 119 */         System.out.println("Waited for 1 minutes to check whether queries in file is inserted.");
/*     */       }
/*     */     }
/*     */     
/* 123 */     while (!AMServicePackFixes.isReady())
/*     */     {
/*     */       try
/*     */       {
/* 127 */         Thread.sleep(1000L);
/*     */       }
/*     */       catch (Exception exc)
/*     */       {
/* 131 */         exc.printStackTrace();
/*     */       }
/*     */     }
/* 134 */     waitTillMonAdder();
/* 135 */     ManagedApplication mo = new ManagedApplication();
/* 136 */     ArrayList rows = mo.getRows("select * from AM_ATTRIBUTES,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ATTRIBUTES.RESOURCETYPE and (TYPE=1 OR TYPE=2) ");
/* 137 */     Hashtable availabilitykeys = new Hashtable();
/* 138 */     Hashtable healthkeys = new Hashtable();
/*     */     
/* 140 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/* 142 */       ArrayList row = (ArrayList)rows.get(i);
/* 143 */       String resourcetype = (String)row.get(1);
/* 144 */       String attributetype = (String)row.get(2);
/* 145 */       String attributeid = (String)row.get(0);
/* 146 */       if (attributetype.equals("Availability"))
/*     */       {
/* 148 */         availabilitykeys.put(resourcetype, attributeid);
/*     */       }
/*     */       else
/*     */       {
/* 152 */         healthkeys.put(resourcetype, attributeid);
/*     */       }
/*     */     }
/* 155 */     availabilitykeys.put("UrlEle", "408");
/* 156 */     healthkeys.put("UrlEle", "409");
/* 157 */     availabilitykeys.put("RBMURL", "8120");
/* 158 */     healthkeys.put("RBMURL", "8121");
/* 159 */     availabilitykeys.putAll(ChildMOHandler.getChildMonitorAvailabilityIDs());
/* 160 */     healthkeys.putAll(ChildMOHandler.getChildMonitorHealthIDs());
/* 161 */     this.servletContext.setAttribute("availabilitykeys", availabilitykeys);
/* 162 */     Constants.putGlobalObject("availabilitykeys", availabilitykeys);
/* 163 */     this.servletContext.setAttribute("healthkeys", healthkeys);
/* 164 */     Constants.putGlobalObject("healthkeys", healthkeys);
/*     */     
/* 166 */     Hashtable attributes = new Hashtable();
/* 167 */     rows = mo.getRows("select ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES ");
/* 168 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/* 170 */       ArrayList row = (ArrayList)rows.get(i);
/* 171 */       attributes.put(row.get(0), row.get(1));
/*     */     }
/* 173 */     this.servletContext.setAttribute("attributes", attributes);
/*     */     
/*     */ 
/* 176 */     Properties props = new Properties()
/*     */     {
/*     */       public Object get(Object key) {
/* 179 */         Object retKey = super.get(key);
/* 180 */         if (retKey == null) {
/* 181 */           ResultSet set = null;
/*     */           try
/*     */           {
/* 184 */             String appKey = String.valueOf(key);
/*     */             
/* 186 */             String query = "SELECT RESOURCENAME,DISPLAYNAME,GROUPTYPE FROM AM_ManagedObject,AM_HOLISTICAPPLICATION WHERE AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.RESOURCEID=" + appKey;
/* 187 */             String appName = null;
/* 188 */             int groupType; if ((!"all".equals(appKey)) && (!"-".equals(appKey)) && (!"orphaned".equals(appKey)))
/*     */             {
/* 190 */               set = AMConnectionPool.executeQueryStmt(query);
/* 191 */               if (set.next())
/*     */               {
/* 193 */                 groupType = set.getInt("GROUPTYPE");
/* 194 */                 String displayName = set.getString("DISPLAYNAME");
/* 195 */                 appName = set.getString("RESOURCENAME");
/* 196 */                 if (groupType == 1013)
/*     */                 {
/* 198 */                   super.setProperty(appKey, displayName);
/*     */                 }
/*     */                 else
/*     */                 {
/* 202 */                   super.setProperty(appKey, appName);
/*     */                 }
/*     */               }
/*     */             }
/* 206 */             return appName;
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 210 */             AMLog.info("Exception: ignore it:" + ex.getMessage());
/*     */           }
/*     */           finally {
/* 213 */             AMConnectionPool.closeStatement(set);
/*     */           }
/*     */           
/* 216 */           return null;
/*     */         }
/* 218 */         return retKey;
/*     */       }
/*     */       
/*     */ 
/* 222 */     };
/* 223 */     rows = mo.getRows("select resourceid,DISPLAYNAME from AM_ManagedObject where type='HAI'");
/* 224 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/* 226 */       ArrayList row = (ArrayList)rows.get(i);
/* 227 */       props.setProperty((String)row.get(0), (String)row.get(1));
/*     */     }
/* 229 */     this.servletContext.setAttribute("applications", props);
/* 230 */     Hashtable thresholdcondition = new Hashtable();
/* 231 */     thresholdcondition.put("LT", "<");
/* 232 */     thresholdcondition.put("GT", ">");
/* 233 */     thresholdcondition.put("EQ", "=");
/* 234 */     thresholdcondition.put("NE", "!=");
/* 235 */     thresholdcondition.put("LE", "<=");
/* 236 */     thresholdcondition.put("GE", ">=");
/* 237 */     thresholdcondition.put("CT", "contains");
/* 238 */     thresholdcondition.put("DC", "does not contain");
/* 239 */     thresholdcondition.put("QL", "equal to");
/* 240 */     thresholdcondition.put("NQ", "not equal to");
/* 241 */     thresholdcondition.put("SW", "starts with");
/* 242 */     thresholdcondition.put("EW", "ends with");
/* 243 */     this.servletContext.setAttribute("thresholdconditions", thresholdcondition);
/*     */     
/* 245 */     Properties severityprops = new Properties();
/* 246 */     severityprops.setProperty("5", getSeverityImage("5"));
/* 247 */     severityprops.setProperty("4", getSeverityImage("4"));
/* 248 */     severityprops.setProperty("1", getSeverityImage("1"));
/* 249 */     severityprops.setProperty("-1", getSeverityImage("-1"));
/* 250 */     this.servletContext.setAttribute("severityprops", severityprops);
/*     */     
/* 252 */     severityprops = new Properties();
/* 253 */     severityprops.setProperty("StatelessSessionBean", getEJBImage("StatelessSessionBean"));
/* 254 */     severityprops.setProperty("StatefullSessionBean", getEJBImage("StatefullSessionBean"));
/* 255 */     severityprops.setProperty("EntityBean", getEJBImage("EntityBean"));
/* 256 */     severityprops.setProperty("MessageDrivenBean", getEJBImage("MessageDrivenBean"));
/* 257 */     this.servletContext.setAttribute("ejbTypes", severityprops);
/*     */     
/* 259 */     Properties colors = new Properties();
/* 260 */     colors.setProperty("AVAILABLE", "#00FF00");
/* 261 */     colors.setProperty("UNAVAILABLE", "#FF0000");
/* 262 */     colors.setProperty("UNMANAGED", "#0066CC");
/* 263 */     colors.setProperty("SCHEDULED", "#FF00FF");
/* 264 */     colors.setProperty("CRITICAL", "#FF0000");
/* 265 */     colors.setProperty("CLEAR", "#00FF00");
/* 266 */     colors.setProperty("WARNING", "#FE7301");
/* 267 */     colors.setProperty("BARCOLOR", "#47BEFA");
/* 268 */     this.servletContext.setAttribute("colors", colors);
/*     */     
/*     */ 
/* 271 */     Hashtable moTypes = new Hashtable()
/*     */     {
/*     */       public Object get(Object key) {
/* 274 */         Object retKey = super.get(key);
/* 275 */         if (retKey == null) {
/* 276 */           return key;
/*     */         }
/* 278 */         return retKey;
/*     */       }
/*     */       
/*     */ 
/* 282 */     };
/* 283 */     rows = mo.getRows("select RESOURCETYPE, DISPLAYNAME,IMAGEPATH from AM_ManagedResourceType");
/* 284 */     Hashtable moTypeImages = new Hashtable(rows.size());
/* 285 */     ArrayList row = null;
/* 286 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/* 288 */       row = (ArrayList)rows.get(i);
/* 289 */       moTypes.put(row.get(0), row.get(1));
/* 290 */       if (row.get(2) != null) {
/* 291 */         moTypeImages.put(row.get(0), row.get(2));
/*     */       }
/*     */     }
/*     */     
/* 295 */     for (int i = 0; i < Constants.categoryLink.length; i++)
/*     */     {
/* 297 */       moTypes.put(Constants.categoryLink[i], Constants.categoryTitle[i]);
/*     */     }
/*     */     
/* 300 */     this.servletContext.setAttribute("motypedisplaynames", moTypes);
/* 301 */     this.servletContext.setAttribute("motypeImage", moTypeImages);
/*     */     
/*     */ 
/*     */ 
/* 305 */     this.servletContext.setAttribute("barcolors", getBarColors());
/*     */     
/* 307 */     rows = mo.getRows("select * from AM_GLOBALCONFIG");
/* 308 */     Hashtable globalconfig = new Hashtable(rows.size());
/* 309 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/*     */ 
/* 312 */       row = (ArrayList)rows.get(i);
/* 313 */       Object globalKey = row.get(0);
/* 314 */       Object globalValue = row.get(1);
/* 315 */       AMLog.debug("AMInitializationServlet.init globalKey : " + globalKey + " globalValue : " + globalValue);
/* 316 */       if ((globalKey != null) && (globalValue != null))
/*     */       {
/* 318 */         if ((((String)row.get(0)).equalsIgnoreCase("showgettingstarted")) && (!OEMUtil.isRemove("introtab.show")))
/*     */         {
/* 320 */           globalconfig.put(row.get(0), "false");
/*     */         }
/*     */         else
/*     */         {
/* 324 */           globalconfig.put(row.get(0), row.get(1));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 329 */     if (globalconfig.get("action.contentTransferEncoding") != null) {
/* 330 */       Constants.setTransferEncoding((String)globalconfig.get("action.contentTransferEncoding"));
/*     */     }
/* 332 */     this.servletContext.setAttribute("globalconfig", globalconfig);
/* 333 */     Properties amServerProps = getAMServerProperties();
/* 334 */     this.servletContext.setAttribute("amserverprops", amServerProps);
/*     */     
/* 336 */     Constants.putGlobalObject("amserverprops", amServerProps);
/*     */     
/* 338 */     globalconfig.put("am_server_type", amServerProps.get("am.server.type"));
/*     */     
/*     */     try
/*     */     {
/* 342 */       Constants.EMAIL_ADDRESS = DBUtil.getGlobalConfigValue("GlobalEMailAddress");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 346 */       e.printStackTrace();
/*     */     }
/*     */     try
/*     */     {
/* 350 */       Constants.snmpVersion = DBUtil.getGlobalConfigValue("SNMPversion");
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 354 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 359 */       com.adventnet.awolf.chart.ChartInfo.sevenThirtyMAValue = DBUtil.getGlobalConfigValue("sevenThirtyMAValue");
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 363 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 368 */       String plotGraph = DBUtil.getGlobalConfigValue("PlotGraph");
/* 369 */       if (!"".equals(plotGraph))
/*     */       {
/*     */ 
/* 372 */         com.adventnet.awolf.chart.ChartInfo.plotGraphValue = plotGraph;
/*     */       }
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 377 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 382 */       String maintenanceToAvailablity = DBUtil.getGlobalConfigValue("AddSchedMaintenancetoAvail");
/* 383 */       if (!"".equals("maintenanceToAvailablity"))
/*     */       {
/* 385 */         Constants.addMaintenanceToAvailablity = maintenanceToAvailablity;
/*     */       }
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 390 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 395 */       String graphType = DBUtil.getGlobalConfigValue("BarchartForAttributeReport");
/* 396 */       if (!"".equals(graphType))
/*     */       {
/* 398 */         Constants.attributesReportGraphType = graphType;
/*     */       }
/*     */     }
/*     */     catch (Exception e1)
/*     */     {
/* 403 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 407 */     ResultSet rs = null;
/* 408 */     String query = "select * from AM_MAILSETTINGS";
/*     */     try
/*     */     {
/* 411 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 412 */       if (rs.next())
/*     */       {
/* 414 */         Constants.putGlobalObject("SMTP", "true");
/*     */       }
/*     */     } catch (Exception e) {
/* 417 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 420 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 422 */     setMGDrillDown();
/* 423 */     BreadcrumbUtil.setInitContext(this.servletContext);
/* 424 */     setUserPrivilages();
/* 425 */     Constants.NMS_INITIALIZED = true;
/*     */   }
/*     */   
/*     */   private void setUserPrivilages() {
/* 429 */     ResultSet rs = null;
/*     */     try {
/* 431 */       Hashtable<String, Boolean> users = new Hashtable();
/* 432 */       rs = AMConnectionPool.executeQueryStmt("select AM_UserPasswordTable.USERNAME,AM_HOLISTICAPPLICATION_OWNERS.OWNERID from AM_UserGroupTable,AM_UserPasswordTable left outer join AM_HOLISTICAPPLICATION_OWNERS on AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and (AM_UserGroupTable.GROUPNAME = 'ADMIN' or AM_UserGroupTable.GROUPNAME = 'ENTERPRISEADMIN') and AM_UserPasswordTable.USERNAME != 'admin'  group by AM_UserPasswordTable.USERNAME, AM_HOLISTICAPPLICATION_OWNERS. OWNERID");
/* 433 */       while (rs.next()) {
/* 434 */         String username = rs.getString("USERNAME");
/* 435 */         String ownerid = rs.getString("OWNERID");
/* 436 */         boolean isPrivelage = false;
/* 437 */         if ((ownerid != null) && (!"null".equalsIgnoreCase(ownerid))) {
/* 438 */           isPrivelage = true;
/*     */         }
/* 440 */         users.put(username + "_privilege", Boolean.valueOf(isPrivelage));
/*     */       }
/* 442 */       this.servletContext.setAttribute("userPrivileges", users);
/*     */     }
/*     */     catch (Exception ex) {
/* 445 */       ex.printStackTrace();
/*     */     } finally {
/* 447 */       if (rs != null) {
/* 448 */         AMConnectionPool.closeStatement(rs);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void setMGDrillDown()
/*     */   {
/*     */     try
/*     */     {
/* 457 */       if ((DBUtil.hasGlobalConfigValue("am.webclient.mgdrilldown")) && (DBUtil.getGlobalConfigValueasBoolean("am.webclient.mgdrilldown")))
/*     */       {
/* 459 */         this.servletContext.setAttribute("mgdrilldown", "true");
/*     */       }
/*     */       else
/*     */       {
/* 463 */         DBUtil.insertIntoGlobalConfig("am.webclient.mgdrilldown", "false");
/* 464 */         this.servletContext.setAttribute("mgdrilldown", "false");
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 469 */       exc.printStackTrace();
/*     */     }
/* 471 */     System.out.println("The servlet context value for the mgdrilldown==>" + this.servletContext.getAttribute("mgdrilldown"));
/*     */   }
/*     */   
/*     */   public void destroy()
/*     */   {
/* 476 */     this.servletContext.removeAttribute("availabilitykeys");
/* 477 */     this.servletContext.removeAttribute("healthkeys");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getSeverityImage(String severity)
/*     */   {
/* 484 */     if (severity.equals("1"))
/*     */     {
/* 486 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" >";
/*     */     }
/* 488 */     if (severity.equals("4"))
/*     */     {
/* 490 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\">";
/*     */     }
/* 492 */     if (severity.equals("5"))
/*     */     {
/* 494 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\">";
/*     */     }
/*     */     
/*     */ 
/* 498 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\"  alt=\"Unknown\">";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String getEJBImage(String severity)
/*     */   {
/* 505 */     if (severity.equals("StatelessSessionBean"))
/*     */     {
/* 507 */       return "<image src='/images/icon_ejb_stateless.gif' alt='Stateless Session Bean'>";
/*     */     }
/* 509 */     if (severity.equals("EntityBean"))
/*     */     {
/* 511 */       return "<image src='/images/icon_ejb_entity.gif' alt='Entity Bean' >";
/*     */     }
/* 513 */     if (severity.equals("StatefullSessionBean"))
/*     */     {
/* 515 */       return "<image src='/images/icon_ejb_stateful.gif' alt='Statefull Session Bean'>";
/*     */     }
/*     */     
/*     */ 
/* 519 */     return "<image src='/images/icon_ejb_mbean.gif' alt='Message Driven Bean'>";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Hashtable getBarColors()
/*     */   {
/* 527 */     Hashtable barcolors = new Hashtable(3);
/*     */     
/* 529 */     Paint[] bar_chart_colors = { new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250), new Color(71, 190, 250) };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 538 */     barcolors.put("Blue", bar_chart_colors);
/*     */     
/* 540 */     bar_chart_colors = new Paint[] { new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0), new Color(51, 204, 0) };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 549 */     barcolors.put("Green", bar_chart_colors);
/*     */     
/* 551 */     return barcolors;
/*     */   }
/*     */   
/*     */   private Properties getAMServerProperties() {
/* 555 */     Properties newprops = new Properties();
/*     */     try {
/* 557 */       if (!isWindows())
/*     */       {
/* 559 */         newprops.load(new FileInputStream(new File("../conf/AMServer.properties")));
/*     */       }
/*     */       else
/*     */       {
/* 563 */         newprops.load(new FileInputStream(new File("..\\conf\\AMServer.properties")));
/*     */       }
/*     */     } catch (Exception e) {
/* 566 */       return newprops;
/*     */     }
/* 568 */     return newprops;
/*     */   }
/*     */   
/*     */   private void initDefaultTabs(ServletContext servletContext)
/*     */   {
/* 573 */     servletContext.setAttribute("HOMETAB", Integer.valueOf(1));
/* 574 */     servletContext.setAttribute("MONITORTAB", Integer.valueOf(2));
/* 575 */     servletContext.setAttribute("ALARMTAB", Integer.valueOf(4));
/* 576 */     servletContext.setAttribute("REPORTTAB", Integer.valueOf(5));
/* 577 */     servletContext.setAttribute("SUPPORTTAB", Integer.valueOf(6));
/* 578 */     servletContext.setAttribute("ADMINTAB", Integer.valueOf(7));
/* 579 */     servletContext.setAttribute("EUMTAB", Integer.valueOf(3));
/* 580 */     servletContext.setAttribute("APMINSIGHTTAB", Integer.valueOf(18));
/*     */   }
/*     */   
/*     */   public boolean isWindows() {
/* 584 */     String osName = System.getProperty("os.name");
/* 585 */     if (osName == null)
/* 586 */       return false;
/* 587 */     boolean isWindows = (osName.startsWith("Windows")) || (osName.startsWith("windows"));
/*     */     
/* 589 */     return isWindows;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\AMInitializationServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */