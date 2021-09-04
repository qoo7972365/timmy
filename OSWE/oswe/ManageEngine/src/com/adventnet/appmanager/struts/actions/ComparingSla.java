/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.actions.AMReportActions;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
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
/*      */ 
/*      */ 
/*      */ public class ComparingSla
/*      */ {
/*   63 */   private ManagedApplication mo = new ManagedApplication();
/*   64 */   static Vector v = null;
/*      */   
/*   66 */   public static Hashtable slids = new Hashtable();
/*      */   
/*      */   static {
/*   69 */     v = new Vector();
/*   70 */     v.add(String.valueOf(0));
/*   71 */     v.add(String.valueOf(3));
/*   72 */     v.add(String.valueOf(6));
/*   73 */     v.add(String.valueOf(1));
/*   74 */     v.add(String.valueOf(12));
/*   75 */     v.add(String.valueOf(7));
/*   76 */     v.add(String.valueOf(2));
/*   77 */     v.add(String.valueOf(11));
/*   78 */     v.add(String.valueOf(9));
/*   79 */     v.add(String.valueOf(8));
/*   80 */     v.add(String.valueOf(5)); }
/*   81 */   private static String htmlMailTpl = getHTMLMailTpl();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList ApplicationAvailablityForManager()
/*      */   {
/*   88 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*   89 */     ArrayList appdata = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*   94 */       String query = "SELECT RESOURCEID,RESOURCENAME  FROM AM_ManagedObject where type='HAI' ORDER BY RESOURCENAME";
/*      */       
/*   96 */       String slid = "";
/*   97 */       String slaname = "";
/*   98 */       String slaval = "";
/*   99 */       String slaop = "";
/*  100 */       ArrayList list = new ArrayList();
/*  101 */       ArrayList alist = this.mo.getRows(query);
/*      */       
/*  103 */       ArrayList availablitymo = new ArrayList();
/*  104 */       Properties props = new Properties();
/*      */       
/*  106 */       for (int i = 0; i < alist.size(); i++)
/*      */       {
/*  108 */         list = (ArrayList)alist.get(i);
/*  109 */         String haid = (String)list.get(0);
/*  110 */         String name = (String)list.get(1);
/*  111 */         String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME AS SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_APPLICATIONAVAILABLITY.OPERATOR, AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_APPLICATIONAVAILABLITY where AM_SLO_APPLICATIONAVAILABLITY.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=1 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid;
/*  112 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/*  113 */         boolean issla = false;
/*  114 */         double slavalue = 0.0D;
/*  115 */         ArrayList data = new ArrayList();
/*  116 */         data.add(haid);
/*  117 */         data.add(name);
/*  118 */         if (rs.next())
/*      */         {
/*  120 */           issla = true;
/*  121 */           slid = rs.getString("SLA_ID");
/*  122 */           slaname = rs.getString("SNAME");
/*  123 */           slaop = rs.getString("OPERATOR");
/*  124 */           slaval = String.valueOf(rs.getFloat("PERCENTAVAIL"));
/*  125 */           slavalue = Double.parseDouble(slaval);
/*  126 */           data.add(slid);
/*  127 */           data.add(slaname);
/*  128 */           data.add(slaop);
/*  129 */           data.add(slaval);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  134 */           data.add("not set");
/*  135 */           data.add("not set");
/*  136 */           data.add("not");
/*  137 */           data.add("set");
/*  138 */           issla = false;
/*      */         }
/*      */         
/*  141 */         for (int period = 0; period < 13; period++)
/*      */         {
/*      */ 
/*  144 */           if ((period != 4) && (period != 10))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  151 */             long[] timestamp = ReportUtilities.getTimeStamp(String.valueOf(period));
/*  152 */             long starttime = timestamp[0];
/*  153 */             long endtime = timestamp[1];
/*      */             
/*  155 */             props = ReportUtilities.getMonitorGroupAvailability(String.valueOf(haid), String.valueOf(period), starttime, endtime);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  162 */             if (issla)
/*      */             {
/*  164 */               String available = props.getProperty("available");
/*  165 */               if (available != null)
/*      */               {
/*      */ 
/*  168 */                 double avail = Double.parseDouble(available);
/*      */                 
/*      */ 
/*  171 */                 if (slaop.equalsIgnoreCase("greater than"))
/*      */                 {
/*  173 */                   if (avail > slavalue)
/*      */                   {
/*  175 */                     props.setProperty("SLA", "PASS");
/*      */                   }
/*      */                   else
/*      */                   {
/*  179 */                     props.setProperty("SLA", "FAIL");
/*      */                   }
/*      */                 }
/*  182 */                 else if (slaop.equalsIgnoreCase("equal to"))
/*      */                 {
/*  184 */                   if (avail == slavalue)
/*      */                   {
/*  186 */                     props.setProperty("SLA", "PASS");
/*      */                   }
/*      */                   else
/*      */                   {
/*  190 */                     props.setProperty("SLA", "FAIL");
/*      */                   }
/*      */                 }
/*  193 */                 else if (slaop.equalsIgnoreCase("greater equal to"))
/*      */                 {
/*  195 */                   if (avail >= slavalue)
/*      */                   {
/*  197 */                     props.setProperty("SLA", "PASS");
/*      */                   }
/*      */                   else
/*      */                   {
/*  201 */                     props.setProperty("SLA", "FAIL");
/*      */                   }
/*      */                   
/*      */                 }
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*  209 */               props.setProperty("SLA", "PASS");
/*      */             }
/*  211 */             data.add(props);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  216 */         appdata.add(data);
/*  217 */         rs.close();
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  223 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  227 */     return appdata;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList checkingSystemAvailablityForManager(int period)
/*      */   {
/*  233 */     ArrayList a1 = null;
/*      */     try
/*      */     {
/*  236 */       long[] temptime = ReportUtilities.getTimeStamp(String.valueOf(period));
/*  237 */       long startime = temptime[0];
/*  238 */       long endtime = temptime[1];
/*  239 */       Boolean ismanager = Boolean.valueOf(false);
/*  240 */       String user = "";
/*      */       
/*  242 */       a1 = checkingSystemAvailablityForManager(period, startime, endtime, ismanager, user);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  246 */       e.printStackTrace();
/*      */     }
/*  248 */     return a1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList checkingSystemAvailablityForManager(int period, long starttime, long endtime, Boolean ismanager, String user)
/*      */   {
/*  255 */     ArrayList a1 = null;
/*      */     
/*  257 */     a1 = checkingSystemAvailablityForManager(period, starttime, endtime, ismanager, user, "");
/*      */     
/*  259 */     return a1;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList checkingSystemAvailablityForManager(int period, long starttime, long endtime, Boolean ismanager, String user, String serverSla)
/*      */   {
/*  265 */     return checkingSystemAvailablityForManager(period, starttime, endtime, ismanager, user, serverSla, null, null);
/*      */   }
/*      */   
/*      */   public ArrayList checkingSystemAvailablityForManager(int period, long starttime, long endtime, Boolean ismanager, String user, String serverSla, Vector slaResIds) {
/*  269 */     return checkingSystemAvailablityForManager(period, starttime, endtime, ismanager, user, serverSla, slaResIds, null);
/*      */   }
/*      */   
/*      */   public ArrayList checkingSystemAvailablityForManager(int period, long starttime, long endtime, Boolean ismanager, String user, String serverSla, Vector slaResIds, HttpServletRequest request)
/*      */   {
/*  274 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  275 */     ArrayList sysdata = new ArrayList();
/*  276 */     ArrayList syschk = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/*  280 */       ResultSet set = null;
/*  281 */       ResultSet rs = null;
/*      */       
/*  283 */       String getresids = null;
/*  284 */       String slid = "";
/*  285 */       String slaname = "";
/*  286 */       String slaval = "";
/*  287 */       String slaop = "";
/*  288 */       String optionmail = "false";
/*  289 */       long startTime = 0L;
/*  290 */       long endTime = 0L;
/*      */       
/*      */ 
/*  293 */       if (!ismanager.booleanValue())
/*      */       {
/*      */ 
/*      */ 
/*  297 */         if ((serverSla != null) && (serverSla.equals("true")))
/*      */         {
/*  299 */           getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType,AM_SLA_RESID_MAPPER where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and RESOURCEID=RESID and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node')";
/*      */         }
/*      */         else
/*      */         {
/*  303 */           getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node')";
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  308 */         String managerQueryCondition = "";
/*  309 */         String loginUserid = null;
/*  310 */         boolean isUserResourceEnabled = false;
/*  311 */         if (Constants.isUserResourceEnabled()) {
/*  312 */           isUserResourceEnabled = true;
/*  313 */           loginUserid = Constants.getLoginUserid(request);
/*      */         } else {
/*  315 */           String mancondn = ReportUtilities.getQueryCondition("AM_ManagedObject.RESOURCEID", user);
/*  316 */           managerQueryCondition = " and " + mancondn;
/*  317 */           if (mancondn.trim().equals("AM_ManagedObject.RESOURCEID in (-1)"))
/*      */           {
/*  319 */             managerQueryCondition = "";
/*      */           }
/*      */         }
/*      */         
/*  323 */         if ((serverSla != null) && (serverSla.equals("true"))) {
/*  324 */           if (isUserResourceEnabled) {
/*  325 */             getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_USERRESOURCESTABLE,AM_ManagedObject,AM_ManagedResourceType,AM_SLA_RESID_MAPPER where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.RESOURCEID=RESID and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */           } else {
/*  327 */             getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType,AM_SLA_RESID_MAPPER where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and RESOURCEID=RESID and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') " + managerQueryCondition;
/*      */           }
/*      */         }
/*  330 */         else if (isUserResourceEnabled) {
/*  331 */           getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_USERRESOURCESTABLE,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */         } else {
/*  333 */           getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') " + managerQueryCondition;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  341 */       if (slaResIds != null)
/*      */       {
/*  343 */         getresids = getresids + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", slaResIds);
/*      */       }
/*  345 */       getresids = getresids + " ORDER BY DISPLAYNAME";
/*      */       
/*  347 */       ArrayList moDet = DBUtil.getRows(getresids);
/*      */       
/*  349 */       for (int i = 0; i < moDet.size(); i++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*  354 */         ArrayList details = (ArrayList)moDet.get(i);
/*  355 */         String resid = (String)details.get(0);
/*  356 */         String moname = (String)details.get(1);
/*  357 */         String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME AS SNAME, AM_SLO.NAME,AM_SLA.MAILOPTION, AM_SLO.ID, AM_SLO_SYSTEMAVAILABLITY.OPERATOR, AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_SYSTEMAVAILABLITY where AM_SLO_SYSTEMAVAILABLITY.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=2 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + resid;
/*      */         
/*  359 */         rs = AMConnectionPool.executeQueryStmt(query1);
/*  360 */         boolean issla = false;
/*  361 */         Properties p5 = new Properties();
/*  362 */         ArrayList data = new ArrayList();
/*  363 */         data.add(resid);
/*  364 */         data.add(moname);
/*  365 */         if (rs.next())
/*      */         {
/*  367 */           issla = true;
/*  368 */           slid = rs.getString("SLA_ID");
/*  369 */           slaname = rs.getString("SNAME");
/*  370 */           optionmail = rs.getString("MAILOPTION");
/*  371 */           slaop = rs.getString("OPERATOR");
/*  372 */           slaval = String.valueOf(rs.getFloat("PERCENTAVAIL"));
/*      */           
/*  374 */           data.add(slid);
/*  375 */           data.add(slaname);
/*  376 */           data.add(slaop);
/*  377 */           data.add(slaval);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  382 */           data.add("not set");
/*  383 */           data.add("not set");
/*  384 */           data.add("not");
/*  385 */           data.add("set");
/*  386 */           issla = false;
/*      */         }
/*  388 */         String sla = "";
/*  389 */         String check = null;
/*  390 */         p5.setProperty("mailoption", optionmail);
/*  391 */         Properties props = new Properties();
/*  392 */         if (period != 10)
/*      */         {
/*      */ 
/*      */ 
/*  396 */           if (period == 4)
/*      */           {
/*      */ 
/*  399 */             long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*  400 */             if (endtime < mintimeindb)
/*      */             {
/*      */ 
/*  403 */               long[] timestamp1 = ReportUtilities.getTimeStamp("0");
/*  404 */               startTime = timestamp1[0];
/*  405 */               endTime = timestamp1[1];
/*  406 */               check = "false";
/*      */ 
/*      */             }
/*  409 */             else if (starttime > System.currentTimeMillis())
/*      */             {
/*      */ 
/*  412 */               long[] timestamp2 = ReportUtilities.getTimeStamp("0");
/*  413 */               startTime = timestamp2[0];
/*  414 */               endTime = timestamp2[1];
/*  415 */               check = "false";
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  420 */               if (mintimeindb > starttime)
/*      */               {
/*  422 */                 startTime = mintimeindb;
/*      */               }
/*  424 */               else if (mintimeindb != 0L)
/*      */               {
/*  426 */                 startTime = starttime;
/*      */               }
/*  428 */               long currenttime = System.currentTimeMillis();
/*      */               
/*  430 */               if (endtime > currenttime)
/*      */               {
/*  432 */                 endTime = currenttime;
/*      */               }
/*      */               else
/*      */               {
/*  436 */                 endTime = endtime;
/*      */               }
/*      */               
/*  439 */               check = "true";
/*      */             }
/*      */             
/*      */           }
/*      */           else
/*      */           {
/*  445 */             ArrayList checkperiod = checkPeriod(resid, period);
/*  446 */             startTime = ((Long)checkperiod.get(0)).longValue();
/*  447 */             endTime = ((Long)checkperiod.get(1)).longValue();
/*  448 */             check = (String)checkperiod.get(2);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  453 */           if ((check != null) && (!check.equalsIgnoreCase("false")))
/*      */           {
/*  455 */             long totalduration = endTime - startTime;
/*  456 */             long uptime = 0L;
/*  457 */             long totaldowntime = 0L;
/*  458 */             long totalUnmanagedtime = 0L;
/*  459 */             long totalScheduledtime = 0L;
/*  460 */             int typeID = 0;
/*  461 */             String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount, TYPE from AM_MO_DowntimeData where RESID=" + resid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE";
/*      */             
/*      */ 
/*  464 */             String down = null;
/*  465 */             String unmanage = "";
/*  466 */             String schedule = "";
/*  467 */             String mttr1 = null;
/*  468 */             String mtbf1 = null;
/*  469 */             int count = 1;
/*  470 */             rs = AMConnectionPool.executeQueryStmt(query);
/*  471 */             while (rs.next())
/*      */             {
/*  473 */               int resourceid = rs.getInt("RESID");
/*      */               
/*  475 */               count = rs.getInt("DownCount");
/*  476 */               typeID = rs.getInt("TYPE");
/*  477 */               if (typeID == 1)
/*      */               {
/*  479 */                 totaldowntime = rs.getLong("TotalDownTime");
/*      */               }
/*  481 */               else if (typeID == 2)
/*      */               {
/*  483 */                 totalUnmanagedtime = rs.getLong("TotalDownTime");
/*      */               }
/*      */               else
/*      */               {
/*  487 */                 totalScheduledtime = rs.getLong("TotalDownTime");
/*      */               }
/*  489 */               if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("true"))) {
/*  490 */                 uptime = totalduration - totaldowntime;
/*      */               }
/*      */               else {
/*  493 */                 uptime = totalduration - (totaldowntime + totalUnmanagedtime + totalScheduledtime);
/*      */               }
/*  495 */               float upPercent = (float)uptime / (float)totalduration * 100.0F;
/*  496 */               float unmanagedPercent = (float)totalUnmanagedtime / (float)totalduration * 100.0F;
/*  497 */               float scheduledPercent = (float)totalScheduledtime / (float)totalduration * 100.0F;
/*  498 */               long mttr = totaldowntime / count;
/*  499 */               long mtbf = uptime / count;
/*  500 */               down = ReportUtilities.format(totaldowntime);
/*  501 */               unmanage = ReportUtilities.format(totalUnmanagedtime);
/*  502 */               schedule = ReportUtilities.format(totalScheduledtime);
/*  503 */               mttr1 = ReportUtilities.format(mttr);
/*  504 */               mtbf1 = ReportUtilities.format(mtbf);
/*  505 */               props.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/*  506 */               props.setProperty("mttr", mttr1);
/*  507 */               props.setProperty("totaldowntime", down);
/*  508 */               props.setProperty("mtbf", mtbf1);
/*  509 */               if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("false"))) {
/*  510 */                 if (unmanagedPercent != 0.0D) {
/*  511 */                   props.setProperty("UnmanagedPer", String.valueOf(Math.round(unmanagedPercent * 100.0F) / 100.0F));
/*      */                 }
/*  513 */                 if (scheduledPercent != 0.0D) {
/*  514 */                   props.setProperty("ScheduledPer", String.valueOf(Math.round(scheduledPercent * 100.0F) / 100.0F));
/*      */                 }
/*      */               }
/*  517 */               String avail = props.getProperty("available");
/*  518 */               if (issla)
/*      */               {
/*  520 */                 Properties p1 = checkingAvailablity(slaop, slaval, avail);
/*  521 */                 sla = p1.getProperty("SLA");
/*      */                 
/*  523 */                 props.setProperty("SLA", sla);
/*      */               }
/*      */               else
/*      */               {
/*  527 */                 props.setProperty("SLA", "PASS");
/*      */               }
/*      */             }
/*      */             
/*  531 */             if ((totaldowntime == 0L) && (totalUnmanagedtime == 0L) && (totalScheduledtime == 0L))
/*      */             {
/*  533 */               String sec = "0 " + FormatUtil.getString("am.webclient.report.secs");
/*  534 */               props.setProperty("available", "100");
/*  535 */               props.setProperty("mttr", sec);
/*  536 */               props.setProperty("totaldowntime", sec);
/*  537 */               props.setProperty("UnmanagedPer", "0");
/*  538 */               props.setProperty("ScheduledPer", "0");
/*  539 */               props.setProperty("mtbf", ReportUtilities.format(totalduration));
/*  540 */               props.setProperty("SLA", "PASS");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  545 */             props.setProperty("available", "NA");
/*  546 */             props.setProperty("mttr", "NA");
/*  547 */             props.setProperty("totaldowntime", "NA");
/*  548 */             props.setProperty("mtbf", "NA");
/*  549 */             props.setProperty("SLA", "PASS");
/*  550 */             props.setProperty("UnmanagedPer", "NA");
/*  551 */             props.setProperty("ScheduledPer", "NA");
/*      */           }
/*      */           
/*  554 */           data.add(props);
/*      */           
/*      */ 
/*  557 */           String per = String.valueOf(period);
/*  558 */           long[] temptime = ReportUtilities.getTimeStamp(per);
/*  559 */           long startTime1 = temptime[0];
/*  560 */           long endTime1 = temptime[1];
/*  561 */           ArrayList a1 = new ArrayList();
/*  562 */           String query2 = "select RESID,SEVERITY,AM_ManagedObject.DISPLAYNAME,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject where AM_ManagedObject.RESOURCEID= " + resid + " and AM_EventHistoryData.RESID=AM_ManagedObject.RESOURCEID  and  ARCHIVEDTIME >=" + startTime1 + "  and ARCHIVEDTIME <=" + endTime1 + " and SEVERITY <> 5 group by RESID,SEVERITY,DISPLAYNAME";
/*  563 */           ArrayList data1 = new ArrayList();
/*  564 */           String query3 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME as SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_TROUBLETICKETVOLUME.OPERATOR , AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,AM_SLO_TROUBLETICKETVOLUME.DURATION FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_TROUBLETICKETVOLUME where AM_SLO_TROUBLETICKETVOLUME.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=3 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + resid;
/*  565 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(query3);
/*      */           
/*      */ 
/*      */ 
/*  569 */           String tslaop = "";
/*  570 */           double tslavalue = 0.0D;
/*  571 */           double ttickvalue = 0.0D;
/*  572 */           String check1 = "";
/*  573 */           if (rs1.next())
/*      */           {
/*      */ 
/*  576 */             check1 = "true";
/*  577 */             String tslid = rs1.getString("SLA_ID");
/*  578 */             String tslaname = rs1.getString("SNAME");
/*  579 */             tslaop = rs1.getString("OPERATOR");
/*  580 */             String tslaval = rs1.getString("TICKETVALUE");
/*  581 */             String tsladur = rs1.getString("DURATION");
/*  582 */             tslavalue = Double.parseDouble(tslaval);
/*      */             
/*  584 */             data.add(tslaop);
/*  585 */             data.add(tslaval);
/*  586 */             data.add(tsladur);
/*  587 */             ttickvalue = 1.0D * tslavalue;
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  592 */             check1 = "false";
/*      */             
/*  594 */             data.add("not");
/*  595 */             data.add("set");
/*  596 */             data.add("not set");
/*      */           }
/*      */           
/*      */ 
/*  600 */           int k = period;
/*  601 */           if (k == 9)
/*      */           {
/*      */ 
/*  604 */             ttickvalue = 3.0D * tslavalue;
/*      */ 
/*      */           }
/*  607 */           else if (k == 8)
/*      */           {
/*  609 */             ttickvalue = 12.0D * tslavalue;
/*      */           }
/*      */           
/*  612 */           a1 = checkTroubleTicket(query2, tslaop, String.valueOf(ttickvalue), check1);
/*  613 */           data.add(a1);
/*  614 */           data.add(p5);
/*  615 */           sysdata.add(data);
/*  616 */           rs1.close();
/*      */         } }
/*  618 */       if (rs != null)
/*      */       {
/*  620 */         rs.close();
/*      */       }
/*  622 */       if (set != null)
/*      */       {
/*  624 */         set.close();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*      */ 
/*  633 */       System.out.println("exception in Comparing sla system availablity " + ee);
/*  634 */       ee.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  638 */     return sysdata;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties checkingAvailablity(String slaop, String slaval, String available)
/*      */   {
/*  644 */     Properties props = new Properties();
/*      */     
/*      */     try
/*      */     {
/*  648 */       double avail = Double.parseDouble(available);
/*      */       
/*  650 */       double slavalue = 0.0D;
/*  651 */       slavalue = Double.parseDouble(slaval);
/*      */       
/*  653 */       if (slaop.equalsIgnoreCase("greater than"))
/*      */       {
/*  655 */         if (avail > slavalue)
/*      */         {
/*  657 */           props.setProperty("SLA", "PASS");
/*      */         }
/*      */         else
/*      */         {
/*  661 */           props.setProperty("SLA", "FAIL");
/*      */         }
/*      */       }
/*  664 */       else if (slaop.equalsIgnoreCase("equal to"))
/*      */       {
/*  666 */         if (avail == slavalue)
/*      */         {
/*  668 */           props.setProperty("SLA", "PASS");
/*      */         }
/*      */         else
/*      */         {
/*  672 */           props.setProperty("SLA", "FAIL");
/*      */         }
/*      */       }
/*  675 */       else if (slaop.equalsIgnoreCase("greater equal to"))
/*      */       {
/*  677 */         if (avail >= slavalue)
/*      */         {
/*  679 */           props.setProperty("SLA", "PASS");
/*      */         }
/*      */         else
/*      */         {
/*  683 */           props.setProperty("SLA", "FAIL");
/*      */         }
/*      */       }
/*      */     } catch (NumberFormatException exx) {
/*  687 */       exx.printStackTrace();
/*      */     }
/*      */     catch (Exception ex) {
/*  690 */       ex.printStackTrace();
/*      */     }
/*  692 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final ArrayList getDataFromEventTable(String query)
/*      */   {
/*  699 */     ArrayList toReturn = new ArrayList();
/*  700 */     ResultSet set = null;
/*  701 */     Properties props = new Properties();
/*  702 */     ArrayList data = new ArrayList();
/*      */     
/*      */ 
/*  705 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  706 */     int totalclear = 0;
/*  707 */     int totalcritical = 0;
/*  708 */     int totalwarning = 0;
/*      */     try
/*      */     {
/*  711 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*      */ 
/*      */ 
/*  715 */       int clear = 0;
/*  716 */       int critical = 0;
/*  717 */       int warning = 0;
/*      */       
/*  719 */       int attributeID = 0;
/*      */       
/*  721 */       while (set.next())
/*      */       {
/*      */ 
/*      */ 
/*  725 */         int resid = set.getInt("RESID");
/*      */         
/*  727 */         int severity = set.getInt("SEVERITY");
/*      */         
/*  729 */         if (severity == 1)
/*      */         {
/*  731 */           critical = set.getInt("OCCURANCES");
/*  732 */           totalcritical += critical;
/*      */ 
/*      */ 
/*      */         }
/*  736 */         else if (severity == 4)
/*      */         {
/*  738 */           warning = set.getInt("OCCURANCES");
/*  739 */           totalwarning += warning;
/*      */ 
/*      */         }
/*  742 */         else if (severity == 5)
/*      */         {
/*  744 */           clear = set.getInt("OCCURANCES");
/*  745 */           totalclear += clear;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  751 */       set.close();
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  755 */       AMLog.fatal("BSM :  Exception in trouble ticket datafromeventtable  method ", exp);
/*      */     }
/*      */     
/*  758 */     toReturn.add(0, String.valueOf(totalclear));
/*  759 */     toReturn.add(1, String.valueOf(totalcritical));
/*  760 */     toReturn.add(2, String.valueOf(totalwarning));
/*      */     
/*      */ 
/*  763 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList checkPeriod(String resid, int per)
/*      */   {
/*  771 */     long mintimeindb = ReportUtilities.getMinTimeInDB(resid);
/*      */     
/*  773 */     ArrayList data = new ArrayList();
/*  774 */     long startTime = 0L;
/*  775 */     long endTime = 0L;
/*      */     
/*      */ 
/*  778 */     long[] temptime = ReportUtilities.getTimeStamp("0");
/*  779 */     if (per == 0)
/*      */     {
/*      */ 
/*  782 */       if (mintimeindb > temptime[0])
/*      */       {
/*  784 */         startTime = mintimeindb;
/*  785 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  788 */       else if (mintimeindb != 0L)
/*      */       {
/*  790 */         startTime = temptime[0];
/*  791 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  794 */       data.add(new Long(startTime));
/*  795 */       data.add(new Long(endTime));
/*  796 */       data.add("true");
/*      */     }
/*      */     
/*  799 */     long todayST = temptime[0];
/*  800 */     temptime = ReportUtilities.getTimeStamp("3");
/*  801 */     if (per == 3)
/*      */     {
/*  803 */       if (mintimeindb > todayST)
/*      */       {
/*  805 */         data.add(new Long("0"));
/*  806 */         data.add(new Long("0"));
/*  807 */         data.add("false");
/*      */       }
/*      */       else
/*      */       {
/*  811 */         if ((mintimeindb > temptime[0]) && (mintimeindb < todayST))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  818 */           startTime = mintimeindb;
/*  819 */           endTime = temptime[1];
/*      */ 
/*      */         }
/*  822 */         else if (mintimeindb != 0L)
/*      */         {
/*  824 */           startTime = temptime[0];
/*  825 */           endTime = temptime[1];
/*      */         }
/*      */         
/*      */ 
/*  829 */         data.add(new Long(startTime));
/*  830 */         data.add(new Long(endTime));
/*  831 */         data.add("true");
/*      */       }
/*      */     }
/*      */     
/*  835 */     temptime = ReportUtilities.getTimeStamp("1");
/*  836 */     if (per == 1)
/*      */     {
/*  838 */       if (mintimeindb > temptime[0])
/*      */       {
/*  840 */         startTime = mintimeindb;
/*  841 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  844 */       else if (mintimeindb != 0L)
/*      */       {
/*  846 */         startTime = temptime[0];
/*  847 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  850 */       data.add(new Long(startTime));
/*  851 */       data.add(new Long(endTime));
/*  852 */       data.add("true");
/*      */     }
/*  854 */     temptime = ReportUtilities.getTimeStamp("2");
/*  855 */     if (per == 2)
/*      */     {
/*  857 */       if (mintimeindb > temptime[0])
/*      */       {
/*  859 */         startTime = mintimeindb;
/*  860 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  863 */       else if (mintimeindb != 0L)
/*      */       {
/*  865 */         startTime = temptime[0];
/*  866 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  869 */       data.add(new Long(startTime));
/*  870 */       data.add(new Long(endTime));
/*  871 */       data.add("true");
/*      */     }
/*  873 */     temptime = ReportUtilities.getTimeStamp("5");
/*  874 */     if (per == 5)
/*      */     {
/*  876 */       if (mintimeindb > temptime[0])
/*      */       {
/*  878 */         startTime = mintimeindb;
/*  879 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  882 */       else if (mintimeindb != 0L)
/*      */       {
/*  884 */         startTime = temptime[0];
/*  885 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  888 */       data.add(new Long(startTime));
/*  889 */       data.add(new Long(endTime));
/*  890 */       data.add("true");
/*      */     }
/*  892 */     temptime = ReportUtilities.getTimeStamp("6");
/*  893 */     if (per == 6)
/*      */     {
/*  895 */       if (mintimeindb > temptime[0])
/*      */       {
/*  897 */         startTime = mintimeindb;
/*  898 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  901 */       else if (mintimeindb != 0L)
/*      */       {
/*  903 */         startTime = temptime[0];
/*  904 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  907 */       data.add(new Long(startTime));
/*  908 */       data.add(new Long(endTime));
/*  909 */       data.add("true");
/*      */     }
/*  911 */     long weekST = temptime[0];
/*  912 */     temptime = ReportUtilities.getTimeStamp("7");
/*  913 */     if (per == 7)
/*      */     {
/*  915 */       if (mintimeindb > temptime[0])
/*      */       {
/*  917 */         startTime = mintimeindb;
/*  918 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  921 */       else if (mintimeindb != 0L)
/*      */       {
/*  923 */         startTime = temptime[0];
/*  924 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  927 */       data.add(new Long(startTime));
/*  928 */       data.add(new Long(endTime));
/*  929 */       data.add("true");
/*      */     }
/*  931 */     long monthST = temptime[0];
/*  932 */     temptime = ReportUtilities.getTimeStamp("8");
/*  933 */     if (per == 8)
/*      */     {
/*  935 */       if (mintimeindb > temptime[0])
/*      */       {
/*  937 */         startTime = mintimeindb;
/*  938 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  941 */       else if (mintimeindb != 0L)
/*      */       {
/*  943 */         startTime = temptime[0];
/*  944 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  947 */       data.add(new Long(startTime));
/*  948 */       data.add(new Long(endTime));
/*  949 */       data.add("true");
/*      */     }
/*  951 */     temptime = ReportUtilities.getTimeStamp("9");
/*  952 */     if (per == 9)
/*      */     {
/*  954 */       if (mintimeindb > temptime[0])
/*      */       {
/*  956 */         startTime = mintimeindb;
/*  957 */         endTime = temptime[1];
/*      */ 
/*      */       }
/*  960 */       else if (mintimeindb != 0L)
/*      */       {
/*  962 */         startTime = temptime[0];
/*  963 */         endTime = temptime[1];
/*      */       }
/*      */       
/*  966 */       data.add(new Long(startTime));
/*  967 */       data.add(new Long(endTime));
/*  968 */       data.add("true");
/*      */     }
/*      */     
/*  971 */     temptime = ReportUtilities.getTimeStamp("11");
/*  972 */     if (per == 11)
/*      */     {
/*  974 */       if (mintimeindb > monthST)
/*      */       {
/*  976 */         data.add(new Long("0"));
/*  977 */         data.add(new Long("0"));
/*  978 */         data.add("false");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  983 */         if ((mintimeindb > temptime[0]) && (mintimeindb < monthST))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  988 */           startTime = mintimeindb;
/*  989 */           endTime = temptime[1];
/*      */ 
/*      */         }
/*  992 */         else if (mintimeindb != 0L)
/*      */         {
/*  994 */           startTime = temptime[0];
/*  995 */           endTime = temptime[1];
/*      */         }
/*      */         
/*  998 */         data.add(new Long(startTime));
/*  999 */         data.add(new Long(endTime));
/* 1000 */         data.add("true");
/*      */       }
/*      */     }
/* 1003 */     if (per == 12)
/*      */     {
/* 1005 */       temptime = ReportUtilities.getTimeStamp("12");
/* 1006 */       if (mintimeindb > weekST)
/*      */       {
/* 1008 */         data.add(new Long("0"));
/* 1009 */         data.add(new Long("0"));
/* 1010 */         data.add("false");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1015 */         if ((mintimeindb > temptime[0]) && (mintimeindb < weekST))
/*      */         {
/*      */ 
/* 1018 */           startTime = mintimeindb;
/* 1019 */           endTime = temptime[1];
/*      */ 
/*      */         }
/* 1022 */         else if (mintimeindb != 0L)
/*      */         {
/* 1024 */           startTime = temptime[0];
/* 1025 */           endTime = temptime[1];
/*      */         }
/*      */         
/* 1028 */         data.add(new Long(startTime));
/* 1029 */         data.add(new Long(endTime));
/* 1030 */         data.add("true");
/*      */       }
/*      */     }
/* 1033 */     return data;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList checkingApplicationTroubleTicket(int period)
/*      */   {
/* 1041 */     ArrayList a1 = null;
/*      */     try
/*      */     {
/* 1044 */       long[] temptime = ReportUtilities.getTimeStamp(String.valueOf(period));
/* 1045 */       long startime = temptime[0];
/* 1046 */       long endtime = temptime[1];
/* 1047 */       Boolean ismanager = Boolean.valueOf(false);
/* 1048 */       String user = "";
/*      */       
/* 1050 */       a1 = checkingApplicationTroubleTicket(startime, endtime, String.valueOf(period), ismanager, user);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1054 */       e.printStackTrace();
/*      */     }
/* 1056 */     return a1;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList checkingApplicationTroubleTicket(long starttime, long endtime, String period, Boolean ismanager, String user)
/*      */   {
/* 1062 */     ArrayList toReturn = null;
/*      */     try {
/* 1064 */       toReturn = checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, user, null);
/*      */     } catch (Exception e) {
/* 1066 */       e.printStackTrace();
/*      */     }
/* 1068 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList checkingApplicationTroubleTicket(long starttime, long endtime, String period, Boolean ismanager, String user, Vector slaResIds)
/*      */   {
/* 1075 */     ArrayList toReturn = null;
/*      */     try {
/* 1077 */       toReturn = checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, user, slaResIds, "");
/*      */     } catch (Exception e) {
/* 1079 */       e.printStackTrace();
/*      */     }
/* 1081 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList checkingApplicationTroubleTicket(long starttime, long endtime, String period, Boolean ismanager, String user, Vector slaResIds, String sla)
/*      */   {
/* 1087 */     ArrayList toReturn = null;
/*      */     try {
/* 1089 */       toReturn = checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, user, slaResIds, sla, null);
/*      */     } catch (Exception ex) {
/* 1091 */       ex.printStackTrace();
/*      */     }
/* 1093 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList checkingApplicationTroubleTicket(long starttime, long endtime, String period, Boolean ismanager, String user, Vector slaResIds, String sla, HttpServletRequest request)
/*      */     throws Exception
/*      */   {
/* 1100 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1101 */     ComparingSla cs = new ComparingSla();
/* 1102 */     ArrayList checking = new ArrayList();
/* 1103 */     String query = null;
/*      */     
/*      */     try
/*      */     {
/* 1107 */       if (!ismanager.booleanValue())
/*      */       {
/* 1109 */         if ((Constants.subGroupsEnabled.equals("true")) && ("true".equals(Constants.slaSubGroupsEnabled)))
/*      */         {
/*      */ 
/* 1112 */           if ((sla != null) && (sla.trim().equals("true"))) {
/* 1113 */             query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_SLA_RESID_MAPPER where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_SLA_RESID_MAPPER.RESID= AM_HOLISTICAPPLICATION.HAID ";
/*      */           } else {
/* 1115 */             query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID ";
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */         }
/* 1123 */         else if ((sla != null) && (sla.trim().equals("true")))
/*      */         {
/* 1125 */           query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_SLA_RESID_MAPPER where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID  and AM_SLA_RESID_MAPPER.RESID= AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0 ";
/*      */         }
/*      */         else {
/* 1128 */           query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0";
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 1137 */       else if ((Constants.subGroupsEnabled.equals("true")) && ("true".equals(Constants.slaSubGroupsEnabled)))
/*      */       {
/*      */ 
/* 1140 */         if ((sla != null) && (sla.trim().equals("true")))
/*      */         {
/* 1142 */           if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 1143 */             String loginUserid = Constants.getLoginUserid(request);
/* 1144 */             query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE,AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID  and ha.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_SLA_RESID_MAPPER.RESID=ha.HAID";
/*      */           } else {
/* 1146 */             query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_HOLISTICAPPLICATION_OWNERS as hao,AM_UserPasswordTable as upt,AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID  and ha.HAID=hao.HAID and hao.OWNERID=upt.USERID and AM_SLA_RESID_MAPPER.RESID=ha.HAID and upt.USERNAME='" + user + "' ";
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1151 */           if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 1152 */             String loginUserid = Constants.getLoginUserid(request);
/* 1153 */             query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE where mo.RESOURCEID=ha.HAID  and ha.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */           } else {
/* 1155 */             query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_HOLISTICAPPLICATION_OWNERS as hao,AM_UserPasswordTable as upt where mo.RESOURCEID=ha.HAID  and ha.HAID=hao.HAID and hao.OWNERID=upt.USERID and upt.USERNAME='" + user + "'";
/*      */           }
/* 1157 */           if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()))
/*      */           {
/* 1159 */             query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE as hao,AM_UserPasswordTable as upt where mo.RESOURCEID=ha.HAID  and ha.HAID=hao.RESOURCEID and hao.USERID=upt.USERID and upt.USERNAME='" + user + "'";
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */       }
/* 1169 */       else if ((sla != null) && (sla.trim().equals("true")))
/*      */       {
/* 1171 */         if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 1172 */           String loginUserid = Constants.getLoginUserid(request);
/* 1173 */           query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE,AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID  and ha.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_SLA_RESID_MAPPER.RESID=ha.HAID and ha.TYPE=0  ";
/*      */         } else {
/* 1175 */           query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_HOLISTICAPPLICATION_OWNERS as hao,AM_UserPasswordTable as upt,AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID  and ha.HAID=hao.HAID and hao.OWNERID=upt.USERID and AM_SLA_RESID_MAPPER.RESID=ha.HAID and upt.USERNAME='" + user + "' and ha.TYPE=0  ";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1180 */       else if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 1181 */         String loginUserid = Constants.getLoginUserid(request);
/* 1182 */         query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE where mo.RESOURCEID=ha.HAID  and ha.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ha.TYPE=0";
/*      */       } else {
/* 1184 */         query = "SELECT mo.RESOURCEID,mo.DISPLAYNAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_HOLISTICAPPLICATION_OWNERS as hao,AM_UserPasswordTable as upt where mo.RESOURCEID=ha.HAID  and ha.HAID=hao.HAID and hao.OWNERID=upt.USERID and upt.USERNAME='" + user + "' and ha.TYPE=0";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1192 */       if (slaResIds != null)
/*      */       {
/* 1194 */         query = query + " and " + Constants.getCondition("RESOURCEID", slaResIds);
/*      */       }
/* 1196 */       query = query + " ORDER BY DISPLAYNAME";
/* 1197 */       AMLog.debug("checkingApplicationTroubleTicket -> QUERY -1 = " + query);
/*      */       
/* 1199 */       ArrayList list = this.mo.getRows(query);
/* 1200 */       if (list.size() == 0)
/*      */       {
/*      */ 
/* 1203 */         String mancondn = ReportUtilities.getQueryCondition("AM_ManagedObject.RESOURCEID", user);
/* 1204 */         String managerQueryCondition = " and " + mancondn;
/* 1205 */         if (mancondn.trim().equals("AM_ManagedObject.RESOURCEID in (-1)"))
/*      */         {
/* 1207 */           managerQueryCondition = "";
/*      */         }
/* 1209 */         if ((sla != null) && (sla.trim().equals("true")))
/*      */         {
/*      */ 
/* 1212 */           query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_SLA_RESID_MAPPER where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_SLA_RESID_MAPPER.RESID= AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0 " + managerQueryCondition;
/*      */         }
/*      */         else {
/* 1215 */           query = "SELECT RESOURCEID,DISPLAYNAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.TYPE=0" + managerQueryCondition;
/*      */         }
/*      */         
/*      */ 
/* 1219 */         if (slaResIds != null)
/*      */         {
/* 1221 */           query = query + " and " + Constants.getCondition("RESOURCEID", slaResIds);
/*      */         }
/* 1223 */         query = query + " ORDER BY DISPLAYNAME";
/* 1224 */         AMLog.debug("checkingApplicationTroubleTicket list is empty through query1 hence-> QUERY -2 = " + query);
/*      */         
/* 1226 */         list = this.mo.getRows(query);
/*      */       }
/* 1228 */       ArrayList a1 = new ArrayList();
/* 1229 */       Properties pop = new Properties();
/* 1230 */       AMReportActions am = new AMReportActions();
/*      */       
/* 1232 */       String slaop = "";
/* 1233 */       String slaval = "";
/* 1234 */       String haid = null;
/* 1235 */       String name = null;
/* 1236 */       long startTime = 0L;
/* 1237 */       long endTime = 0L;
/* 1238 */       String mailopt = "false";
/* 1239 */       for (int i = 0; i < list.size(); i++)
/*      */       {
/* 1241 */         a1 = (ArrayList)list.get(i);
/* 1242 */         haid = (String)a1.get(0);
/* 1243 */         name = (String)a1.get(1);
/* 1244 */         if ((period != null) && (period.equals("4")))
/*      */         {
/* 1246 */           long mintimeindb = ReportUtilities.getMinTimeInDB(haid);
/* 1247 */           if (endtime < mintimeindb)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1254 */             startTime = starttime;
/* 1255 */             endTime = endtime;
/* 1256 */             period = "4";
/*      */ 
/*      */           }
/* 1259 */           else if (starttime > System.currentTimeMillis())
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1266 */             startTime = starttime;
/* 1267 */             endTime = endtime;
/* 1268 */             period = "4";
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 1274 */             if (mintimeindb > starttime)
/*      */             {
/* 1276 */               startTime = mintimeindb;
/*      */             }
/* 1278 */             else if (mintimeindb != 0L)
/*      */             {
/* 1280 */               startTime = starttime;
/*      */             }
/* 1282 */             long currenttime = System.currentTimeMillis();
/*      */             
/* 1284 */             if (endtime > currenttime)
/*      */             {
/* 1286 */               endTime = currenttime;
/*      */             }
/*      */             else
/*      */             {
/* 1290 */               endTime = endtime;
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1298 */           startTime = starttime;
/* 1299 */           endTime = endtime;
/*      */         }
/*      */         
/* 1302 */         ArrayList data = new ArrayList();
/* 1303 */         data.add(haid);
/* 1304 */         data.add(name);
/* 1305 */         String available = null;
/* 1306 */         String unmanage = null;
/* 1307 */         String schedule = null;
/*      */         
/* 1309 */         pop = ReportUtilities.getMonitorGroupAvailability(String.valueOf(haid), period, starttime, endtime);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1317 */         if (!pop.isEmpty())
/*      */         {
/* 1319 */           available = pop.getProperty("available");
/*      */           
/* 1321 */           String mttr = pop.getProperty("mttr");
/* 1322 */           String mtbf = pop.getProperty("mtbf");
/* 1323 */           String totaldowntime = pop.getProperty("totaldowntime");
/* 1324 */           unmanage = pop.getProperty("ServicesUnMgPercent");
/* 1325 */           schedule = pop.getProperty("ServicesSchPercent");
/*      */           
/* 1327 */           if ((mttr != null) && ((totaldowntime != null) || (unmanage != null) || (schedule != null)))
/*      */           {
/* 1329 */             if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("true")) && (!"NA".equalsIgnoreCase(available))) {
/* 1330 */               double up = Double.valueOf(available.trim()).doubleValue() + Double.valueOf(unmanage.trim()).doubleValue() + Double.valueOf(schedule.trim()).doubleValue();
/* 1331 */               available = String.valueOf(up);
/*      */             }
/*      */             else {
/* 1334 */               available = pop.getProperty("available");
/*      */             }
/* 1336 */             data.add(mttr);
/* 1337 */             data.add(mtbf);
/* 1338 */             data.add(totaldowntime);
/* 1339 */             data.add(available);
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           data.add("0");
/* 1346 */           data.add("0");
/* 1347 */           data.add("0");
/* 1348 */           data.add("NA");
/*      */         }
/*      */         
/* 1351 */         String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME AS SNAME,AM_SLA.MAILOPTION, AM_SLO.NAME, AM_SLO.ID, AM_SLO_APPLICATIONAVAILABLITY.OPERATOR, AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_APPLICATIONAVAILABLITY where AM_SLO_APPLICATIONAVAILABLITY.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=1 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid;
/* 1352 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 1353 */         boolean issla = false;
/* 1354 */         double slavalue = 0.0D;
/* 1355 */         Properties pr = new Properties();
/* 1356 */         if (rs.next())
/*      */         {
/* 1358 */           issla = true;
/* 1359 */           String slid = rs.getString("SLA_ID");
/* 1360 */           String slaname = rs.getString("SNAME");
/* 1361 */           mailopt = rs.getString("MAILOPTION");
/* 1362 */           slaop = rs.getString("OPERATOR");
/* 1363 */           slaval = String.valueOf(rs.getFloat("PERCENTAVAIL"));
/* 1364 */           slavalue = Double.parseDouble(slaval);
/* 1365 */           data.add(slid);
/* 1366 */           data.add(slaname);
/* 1367 */           data.add(slaop);
/* 1368 */           data.add(slaval);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1373 */           data.add("not set");
/* 1374 */           data.add("not set");
/* 1375 */           data.add("not");
/* 1376 */           data.add("set");
/* 1377 */           issla = false;
/*      */         }
/*      */         
/*      */ 
/* 1381 */         pr.setProperty("mailoption", mailopt);
/* 1382 */         Properties p1 = new Properties();
/*      */         
/* 1384 */         if ((available != null) && (issla))
/*      */         {
/* 1386 */           p1 = cs.checkingAvailablity(slaop, slaval, available);
/*      */         }
/*      */         else
/*      */         {
/* 1390 */           p1.setProperty("SLA", "PASS");
/*      */         }
/* 1392 */         p1.setProperty("period", period);
/*      */         
/* 1394 */         data.add(p1);
/*      */         
/*      */ 
/*      */ 
/* 1398 */         ArrayList data1 = new ArrayList();
/* 1399 */         String query3 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME as SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_TROUBLETICKETVOLUME.OPERATOR , AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,AM_SLO_TROUBLETICKETVOLUME.DURATION FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_TROUBLETICKETVOLUME where AM_SLO_TROUBLETICKETVOLUME.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=3 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid;
/* 1400 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(query3);
/*      */         
/*      */ 
/*      */ 
/* 1404 */         String tslaop = "";
/* 1405 */         double tslavalue = 0.0D;
/* 1406 */         double ttickvalue = 0.0D;
/* 1407 */         String check = "";
/* 1408 */         if (rs1.next())
/*      */         {
/*      */ 
/* 1411 */           check = "true";
/* 1412 */           String tslid = rs1.getString("SLA_ID");
/* 1413 */           String tslaname = rs1.getString("SNAME");
/* 1414 */           tslaop = rs1.getString("OPERATOR");
/* 1415 */           String tslaval = rs1.getString("TICKETVALUE");
/* 1416 */           String tsladur = rs1.getString("DURATION");
/* 1417 */           tslavalue = Double.parseDouble(tslaval);
/*      */           
/* 1419 */           data.add(tslaop);
/* 1420 */           data.add(tslaval);
/* 1421 */           data.add(tsladur);
/* 1422 */           ttickvalue = 1.0D * tslavalue;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1427 */           check = "false";
/*      */           
/* 1429 */           data.add("not");
/* 1430 */           data.add("set");
/* 1431 */           data.add("not set");
/*      */         }
/*      */         
/*      */ 
/* 1435 */         int k = Integer.parseInt(period);
/* 1436 */         if (k == 9)
/*      */         {
/*      */ 
/* 1439 */           ttickvalue = 3.0D * tslavalue;
/*      */ 
/*      */         }
/* 1442 */         else if (k == 8)
/*      */         {
/* 1444 */           ttickvalue = 12.0D * tslavalue;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1449 */         ArrayList a3 = new ArrayList();
/* 1450 */         String query4 = "select CATEGORY,RESID,SEVERITY,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject,AM_ATTRIBUTES,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_ATTRIBUTES.ATTRIBUTEID=CATEGORY  and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >= " + starttime + " and ARCHIVEDTIME <= " + endtime + " group by CATEGORY,SEVERITY,RESID order by CATEGORY,SEVERITY";
/*      */         
/* 1452 */         a3 = cs.checkTroubleTicket(query4, tslaop, String.valueOf(ttickvalue), check);
/* 1453 */         data.add(a3);
/* 1454 */         data.add(pr);
/*      */         
/* 1456 */         if (unmanage != null) {
/* 1457 */           data.add(unmanage);
/*      */         }
/*      */         else {
/* 1460 */           data.add("0");
/*      */         }
/* 1462 */         if (schedule != null) {
/* 1463 */           data.add(schedule);
/*      */         }
/*      */         else {
/* 1466 */           data.add("0");
/*      */         }
/*      */         
/* 1469 */         checking.add(data);
/* 1470 */         rs.close();
/* 1471 */         rs1.close();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (NumberFormatException exx) {}catch (Exception ee)
/*      */     {
/*      */ 
/* 1481 */       System.out.println("exception in comparing sla checking application trouble ticket method " + ee);
/* 1482 */       ee.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1486 */     return checking;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList checkTroubleTicket(String query, String slaop, String slaval, String check)
/*      */   {
/* 1495 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1496 */     ArrayList data = new ArrayList();
/* 1497 */     ArrayList a1 = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 1501 */       String critical = "";
/* 1502 */       Properties props = new Properties();
/* 1503 */       a1 = getDataFromEventTable(query);
/* 1504 */       for (int i = 0; i < a1.size(); i++)
/*      */       {
/* 1506 */         critical = (String)a1.get(1);
/*      */       }
/* 1508 */       double slavalue = 0.0D;
/* 1509 */       slavalue = Double.parseDouble(slaval);
/* 1510 */       if (check.equalsIgnoreCase("true"))
/*      */       {
/*      */ 
/* 1513 */         double critic = Double.parseDouble(critical);
/*      */         
/*      */ 
/* 1516 */         if (slaop.equalsIgnoreCase("less than"))
/*      */         {
/* 1518 */           if (critic < slavalue)
/*      */           {
/* 1520 */             a1.add("PASS");
/*      */           }
/*      */           else
/*      */           {
/* 1524 */             a1.add("FAIL");
/*      */           }
/*      */         }
/* 1527 */         else if (slaop.equalsIgnoreCase("equal to"))
/*      */         {
/* 1529 */           if (critic == slavalue)
/*      */           {
/* 1531 */             a1.add("PASS");
/*      */           }
/*      */           else
/*      */           {
/* 1535 */             a1.add("FAIL");
/*      */           }
/*      */         }
/* 1538 */         else if (slaop.equalsIgnoreCase("less equal to"))
/*      */         {
/* 1540 */           if (critic <= slavalue)
/*      */           {
/* 1542 */             a1.add("PASS");
/*      */           }
/*      */           else
/*      */           {
/* 1546 */             a1.add("FAIL");
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1553 */         a1.add("PASS");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 1561 */       e.printStackTrace();
/*      */     }
/* 1563 */     return a1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ArrayList getEventData()
/*      */   {
/* 1571 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/* 1573 */     ArrayList eventdata = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1578 */       String query = "SELECT RESOURCEID,RESOURCENAME  FROM AM_ManagedObject where type='HAI'";
/* 1579 */       ArrayList list = new ArrayList();
/* 1580 */       ArrayList list1 = new ArrayList();
/* 1581 */       ArrayList alist = this.mo.getRows(query);
/* 1582 */       ArrayList data = new ArrayList();
/*      */       
/*      */ 
/* 1585 */       AMReportActions am = new AMReportActions();
/* 1586 */       ComparingSla cs = new ComparingSla();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1591 */       for (int i = 0; i < alist.size(); i++)
/*      */       {
/* 1593 */         ArrayList data1 = new ArrayList();
/* 1594 */         list1 = (ArrayList)alist.get(i);
/* 1595 */         String haid1 = (String)list1.get(0);
/* 1596 */         String name1 = (String)list1.get(1);
/*      */         
/* 1598 */         data1.add(haid1);
/* 1599 */         data1.add(name1);
/* 1600 */         String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_TROUBLETICKETVOLUME.OPERATOR, AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,AM_SLO_TROUBLETICKETVOLUME.DURATION FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_TROUBLETICKETVOLUME where AM_SLO_TROUBLETICKETVOLUME.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=3 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid1;
/* 1601 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 1602 */         boolean issla = false;
/*      */         
/*      */ 
/* 1605 */         String slaop = "";
/* 1606 */         double slavalue = 0.0D;
/* 1607 */         double tickvalue = 0.0D;
/* 1608 */         String check = "";
/* 1609 */         if (rs.next())
/*      */         {
/* 1611 */           issla = true;
/* 1612 */           check = "true";
/* 1613 */           String slid = rs.getString("SLA_ID");
/* 1614 */           String slaname = rs.getString("NAME");
/* 1615 */           slaop = rs.getString("OPERATOR");
/* 1616 */           String slaval = rs.getString("TICKETVALUE");
/* 1617 */           String sladur = rs.getString("DURATION");
/* 1618 */           slavalue = Double.parseDouble(slaval);
/* 1619 */           data1.add(slid);
/* 1620 */           data1.add(slaname);
/* 1621 */           data1.add(slaop);
/* 1622 */           data1.add(slaval);
/* 1623 */           data1.add(sladur);
/* 1624 */           tickvalue = 1.0D * slavalue;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1629 */           check = "false";
/* 1630 */           data1.add("not set");
/* 1631 */           data1.add("not set");
/* 1632 */           data1.add("not");
/* 1633 */           data1.add("set");
/* 1634 */           data1.add("not set");
/* 1635 */           issla = false;
/*      */         }
/*      */         
/*      */ 
/* 1639 */         for (int k = 0; k < v.size(); k++)
/*      */         {
/* 1641 */           long[] timestamp1 = ReportUtilities.getTimeStamp(String.valueOf(v.get(k)));
/* 1642 */           long starttime1 = timestamp1[0];
/* 1643 */           long endtime1 = timestamp1[1];
/* 1644 */           if (k == 8)
/*      */           {
/*      */ 
/* 1647 */             tickvalue = 3.0D * slavalue;
/*      */ 
/*      */           }
/* 1650 */           else if (k == 9)
/*      */           {
/* 1652 */             tickvalue = 12.0D * slavalue;
/*      */           }
/*      */           
/*      */ 
/* 1656 */           String query3 = "select RESID,SEVERITY,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject,AM_ATTRIBUTES,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_ATTRIBUTES.ATTRIBUTEID=CATEGORY  and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid1 + " and  ARCHIVEDTIME >= " + starttime1 + " and ARCHIVEDTIME <= " + endtime1 + " group by CATEGORY,SEVERITY,RESID order by CATEGORY,SEVERITY";
/* 1657 */           ArrayList a3 = checkTroubleTicket(query3, slaop, String.valueOf(tickvalue), check);
/*      */           
/* 1659 */           data1.add(a3);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1664 */         eventdata.add(data1);
/* 1665 */         rs.close();
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 1672 */       System.out.println("exception in comparing sla get event data method" + ee);
/* 1673 */       ee.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1678 */     return eventdata;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final ArrayList getDataFromAction(String slaid)
/*      */   {
/* 1685 */     ResultSet set = null;
/* 1686 */     Properties props = new Properties();
/* 1687 */     ArrayList data = new ArrayList();
/*      */     
/*      */ 
/* 1690 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */     try
/*      */     {
/* 1694 */       if (!slaid.equalsIgnoreCase("not set")) {
/* 1695 */         String query = "SELECT AM_EMAILACTION.ID,AM_EMAILACTION.FROMADDRESS,AM_EMAILACTION.TOADDRESS,AM_EMAILACTION.SUBJECT,AM_EMAILACTION.MESSAGE FROM AM_EMAILACTION,AM_SLA_ACTION_MAPPER WHERE AM_EMAILACTION.ID=AM_SLA_ACTION_MAPPER.ACTION_ID AND AM_SLA_ACTION_MAPPER.SLA_ID='" + slaid + "'";
/* 1696 */         set = AMConnectionPool.executeQueryStmt(query);
/*      */         
/*      */ 
/* 1699 */         if (set.next())
/*      */         {
/*      */ 
/* 1702 */           String id = set.getString("ID");
/* 1703 */           String fadd = set.getString("FROMADDRESS");
/* 1704 */           String tadd = set.getString("TOADDRESS");
/* 1705 */           String sub = set.getString("SUBJECT");
/* 1706 */           String mess = set.getString("MESSAGE");
/* 1707 */           data.add(id);
/* 1708 */           data.add(fadd);
/* 1709 */           data.add(tadd);
/* 1710 */           data.add(sub);
/* 1711 */           data.add(mess);
/*      */         }
/* 1713 */         set.close();
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1718 */       AMLog.fatal("BSM :  Exception ", exp);
/*      */     }
/*      */     
/*      */ 
/* 1722 */     return data;
/*      */   }
/*      */   
/*      */   public String getValueForPeriod(String period) {
/* 1726 */     Properties pq = DBUtil.getRawValue();
/* 1727 */     String RV = pq.getProperty("israw");
/* 1728 */     String PV = pq.getProperty("rawvalue");
/* 1729 */     if ("day".equals(period))
/* 1730 */       return "Day";
/* 1731 */     if ("week".equals(period))
/* 1732 */       return "Week";
/* 1733 */     if ("month".equals(period)) {
/* 1734 */       return "Month";
/*      */     }
/* 1736 */     int p = Integer.parseInt(period);
/* 1737 */     if (p == 0)
/*      */     {
/* 1739 */       return FormatUtil.getString("am.webclient.period.today");
/*      */     }
/* 1741 */     if ((p == 1) || (p == -7))
/*      */     {
/* 1743 */       return FormatUtil.getString("am.webclient.period.last7days");
/*      */     }
/* 1745 */     if ((p == 2) || (p == -30))
/*      */     {
/* 1747 */       return FormatUtil.getString("am.webclient.period.last30days");
/*      */     }
/* 1749 */     if ((p == 3) || (p == -1))
/*      */     {
/* 1751 */       return FormatUtil.getString("am.webclient.period.yesterday");
/*      */     }
/* 1753 */     if (p == 4)
/*      */     {
/*      */ 
/* 1756 */       return FormatUtil.getString("am.webclient.period.customtimeperiod");
/*      */     }
/* 1758 */     if ((p == 5) || (p == -5))
/*      */     {
/* 1760 */       return FormatUtil.getString("am.webclient.period.lastoneyear");
/*      */     }
/* 1762 */     if (p == 6)
/*      */     {
/* 1764 */       return FormatUtil.getString("am.webclient.period.thisweek");
/*      */     }
/*      */     
/* 1767 */     if (p == 7)
/*      */     {
/* 1769 */       return FormatUtil.getString("am.webclient.period.thismonth");
/*      */     }
/* 1771 */     if (p == 8)
/*      */     {
/* 1773 */       return FormatUtil.getString("am.webclient.period.thisyear");
/*      */     }
/* 1775 */     if (p == 9)
/*      */     {
/* 1777 */       return FormatUtil.getString("am.webclient.period.thisquarter");
/*      */     }
/* 1779 */     if (p == 10)
/*      */     {
/* 1781 */       return FormatUtil.getString("am.webclient.period.thishalf");
/*      */     }
/* 1783 */     if (p == 11)
/*      */     {
/* 1785 */       return FormatUtil.getString("am.webclient.period.lastmonth");
/*      */     }
/* 1787 */     if (p == 12)
/*      */     {
/* 1789 */       return FormatUtil.getString("am.webclient.period.lastweek");
/*      */     }
/* 1791 */     if (p == 14)
/*      */     {
/* 1793 */       return FormatUtil.getString("am.webclient.period.polleddata", new String[] { PV });
/*      */     }
/*      */     
/* 1796 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList systemAvailablity()
/*      */   {
/* 1803 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1804 */     ArrayList sysdata = new ArrayList();
/* 1805 */     ArrayList syschk = new ArrayList();
/*      */     try
/*      */     {
/* 1808 */       ResultSet set = null;
/* 1809 */       ResultSet rs = null;
/*      */       
/*      */ 
/* 1812 */       String slid = "";
/* 1813 */       String slaname = "";
/* 1814 */       String slaval = "";
/* 1815 */       String slaop = "";
/*      */       
/*      */ 
/* 1818 */       String getresids = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node')  ORDER BY RESOURCENAME";
/*      */       
/*      */ 
/*      */ 
/* 1822 */       set = AMConnectionPool.executeQueryStmt(getresids);
/* 1823 */       while (set.next())
/*      */       {
/*      */ 
/* 1826 */         String resid = String.valueOf(set.getInt("RESOURCEID"));
/* 1827 */         String moname = set.getString("DISPLAYNAME");
/* 1828 */         String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME AS SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_SYSTEMAVAILABLITY.OPERATOR, AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_SYSTEMAVAILABLITY where AM_SLO_SYSTEMAVAILABLITY.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=2 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + resid;
/*      */         
/* 1830 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 1831 */         boolean issla = false;
/*      */         
/* 1833 */         ArrayList data = new ArrayList();
/* 1834 */         data.add(resid);
/* 1835 */         data.add(moname);
/* 1836 */         if (rs.next())
/*      */         {
/* 1838 */           issla = true;
/* 1839 */           slid = rs.getString("SLA_ID");
/* 1840 */           slaname = rs.getString("SNAME");
/* 1841 */           slaop = rs.getString("OPERATOR");
/* 1842 */           slaval = String.valueOf(rs.getFloat("PERCENTAVAIL"));
/*      */           
/* 1844 */           data.add(slid);
/* 1845 */           data.add(slaname);
/* 1846 */           data.add(slaop);
/* 1847 */           data.add(slaval);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1852 */           data.add("not set");
/* 1853 */           data.add("not set");
/* 1854 */           data.add("not");
/* 1855 */           data.add("set");
/* 1856 */           issla = false;
/*      */         }
/* 1858 */         String sla = "";
/* 1859 */         for (int period = 0; period <= 12; period++)
/*      */         {
/* 1861 */           Properties props = new Properties();
/* 1862 */           if ((period != 4) && (period != 10))
/*      */           {
/*      */ 
/*      */ 
/* 1866 */             ArrayList checkperiod = checkPeriod(resid, period);
/* 1867 */             long startTime = ((Long)checkperiod.get(0)).longValue();
/* 1868 */             long endTime = ((Long)checkperiod.get(1)).longValue();
/* 1869 */             String check = (String)checkperiod.get(2);
/* 1870 */             if ((check != null) && (!check.equalsIgnoreCase("false")))
/*      */             {
/* 1872 */               long totalduration = endTime - startTime;
/* 1873 */               long uptime = 0L;
/* 1874 */               long totaldowntime = 0L;
/* 1875 */               long totalUnmanagedtime = 0L;
/* 1876 */               long totalScheduledtime = 0L;
/* 1877 */               int typeID = 0;
/* 1878 */               String query = "select RESID,sum(case when DOWNTIME < " + endTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ")  else UPTIME end) > " + endTime + " then " + endTime + " else case when UPTIME = 0 then " + endTime + " else UPTIME end end - case when DOWNTIME < " + startTime + " and (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end) > " + startTime + " then " + startTime + " else DOWNTIME end) as TotalDownTime, count(*) as DownCount, TYPE from AM_MO_DowntimeData where RESID=" + resid + " and (" + startTime + " < DOWNTIME or " + startTime + " < (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) and (" + endTime + " > DOWNTIME or " + endTime + " > (case when UPTIME=0 then (" + (endTime + 1L) + ") else UPTIME end)) group by RESID, TYPE";
/*      */               
/*      */ 
/* 1881 */               rs = AMConnectionPool.executeQueryStmt(query);
/* 1882 */               if (rs.next())
/*      */               {
/*      */ 
/* 1885 */                 int resourceid = rs.getInt("RESID");
/*      */                 
/*      */ 
/* 1888 */                 int count = rs.getInt("DownCount");
/* 1889 */                 typeID = rs.getInt("TYPE");
/* 1890 */                 if (typeID == 1)
/*      */                 {
/* 1892 */                   totaldowntime = rs.getLong("TotalDownTime");
/*      */                 }
/* 1894 */                 else if (typeID == 2)
/*      */                 {
/* 1896 */                   totalUnmanagedtime = rs.getLong("TotalDownTime");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1900 */                   totalScheduledtime = rs.getLong("TotalDownTime");
/*      */                 }
/*      */                 
/* 1903 */                 uptime = totalduration - (totaldowntime + totalUnmanagedtime + totalScheduledtime);
/* 1904 */                 float upPercent = (float)uptime / (float)totalduration * 100.0F;
/*      */                 
/* 1906 */                 props.setProperty("available", String.valueOf(Math.round(upPercent * 100.0F) / 100.0F));
/* 1907 */                 String avail = props.getProperty("available");
/* 1908 */                 if (issla)
/*      */                 {
/* 1910 */                   Properties p1 = checkingAvailablity(slaop, slaval, avail);
/* 1911 */                   sla = p1.getProperty("SLA");
/*      */                   
/* 1913 */                   props.setProperty("SLA", sla);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1917 */                   props.setProperty("SLA", "PASS");
/*      */                 }
/*      */                 
/*      */               }
/*      */               else
/*      */               {
/* 1923 */                 props.setProperty("available", "100");
/* 1924 */                 props.setProperty("SLA", "PASS");
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 1929 */               props.setProperty("available", "NA");
/* 1930 */               props.setProperty("SLA", "PASS");
/*      */             }
/*      */             
/* 1933 */             data.add(props);
/*      */           }
/*      */         }
/*      */         
/* 1937 */         sysdata.add(data);
/*      */       }
/* 1939 */       set.close();
/* 1940 */       rs.close();
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*      */ 
/* 1947 */       System.out.println("exception in buisnessaction system availablity " + ee);
/* 1948 */       ee.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1952 */     return sysdata;
/*      */   }
/*      */   
/*      */ 
/*      */   public final ArrayList getTroubleTicket(String haid)
/*      */   {
/* 1958 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1959 */     ArrayList data1 = new ArrayList();
/*      */     try {
/* 1961 */       String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME as SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_TROUBLETICKETVOLUME.OPERATOR, AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,AM_SLO_TROUBLETICKETVOLUME.DURATION FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_TROUBLETICKETVOLUME where AM_SLO_TROUBLETICKETVOLUME.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=3 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid;
/* 1962 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/*      */       
/*      */ 
/*      */ 
/* 1966 */       String slaop = "";
/* 1967 */       double slavalue = 0.0D;
/*      */       
/* 1969 */       String check = "";
/* 1970 */       if (rs.next())
/*      */       {
/*      */ 
/* 1973 */         check = "true";
/* 1974 */         String slid = rs.getString("SLA_ID");
/* 1975 */         String slaname = rs.getString("SNAME");
/* 1976 */         slaop = rs.getString("OPERATOR");
/* 1977 */         String slaval = rs.getString("TICKETVALUE");
/* 1978 */         String sladur = rs.getString("DURATION");
/* 1979 */         slavalue = Double.parseDouble(slaval);
/* 1980 */         data1.add(slid);
/* 1981 */         data1.add(slaname);
/* 1982 */         data1.add(slaop);
/* 1983 */         data1.add(slaval);
/* 1984 */         data1.add(sladur);
/* 1985 */         data1.add(check);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1990 */         check = "false";
/* 1991 */         data1.add("not set");
/* 1992 */         data1.add("not set");
/* 1993 */         data1.add("not");
/* 1994 */         data1.add("set");
/* 1995 */         data1.add("not set");
/* 1996 */         data1.add(check);
/*      */       }
/*      */       
/* 1999 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/* 2002 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2005 */     return data1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getHTMLMailTpl()
/*      */   {
/*      */     try
/*      */     {
/* 2020 */       String filePath = "./conf/ManagerMail.html";
/* 2021 */       if (System.getProperty("locale") != null)
/*      */       {
/* 2023 */         String newFilePath = "./conf/ManagerMail_" + System.getProperty("locale") + ".html";
/* 2024 */         if (new File(newFilePath).exists())
/*      */         {
/* 2026 */           filePath = newFilePath;
/*      */         }
/*      */       }
/* 2029 */       return FormatUtil.getContentsAsString(filePath);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (IOException io)
/*      */     {
/*      */ 
/*      */ 
/* 2038 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template"); }
/* 2039 */     return "error in sending mail";
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getHTMLMailTemplate()
/*      */   {
/* 2045 */     return htmlMailTpl;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getSummaryMailTemplate()
/*      */   {
/*      */     try
/*      */     {
/* 2055 */       String filePath = "./conf/Admin_SummaryMail_temp.html";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2064 */       return FormatUtil.getContentsAsString(filePath);
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (IOException io)
/*      */     {
/*      */ 
/* 2071 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template"); }
/* 2072 */     return "error in sending mail";
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ComparingSla.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */