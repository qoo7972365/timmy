/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.SmtpMailer;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.reporting.actions.AMReportActions;
/*      */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.awolf.chart.ChartInfo;
/*      */ import com.adventnet.awolf.data.support.BarSupport;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.util.SLAUtil;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.struts.action.ActionError;
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
/*      */ public class BussinessAction
/*      */   extends DispatchAction
/*      */ {
/*   93 */   private static Log log = LogFactory.getLog("WebClient");
/*   94 */   private static boolean mgCheck = true; private ManagedApplication mo;
/*   95 */   public BussinessAction() { this.mo = new ManagedApplication(); }
/*   96 */   static Vector v = null;
/*      */   
/*      */   static {
/*   99 */     v = new Vector();
/*  100 */     v.add(String.valueOf(0));
/*  101 */     v.add(String.valueOf(3));
/*  102 */     v.add(String.valueOf(6));
/*  103 */     v.add(String.valueOf(12));
/*  104 */     v.add(String.valueOf(7));
/*  105 */     v.add(String.valueOf(11));
/*  106 */     v.add(String.valueOf(9));
/*  107 */     v.add(String.valueOf(8));
/*  108 */     v.add(String.valueOf(5));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward generateTroubleTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  117 */     CustomerManagementAPI.getInstance();boolean showSLA = CustomerManagementAPI.isUserPermittedToSeeAllBSG(request);
/*  118 */     ComparingSla cs = new ComparingSla();
/*  119 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  120 */     ActionMessages messages = new ActionMessages();
/*  121 */     ActionErrors errors = new ActionErrors();
/*  122 */     ReportForm rf = (ReportForm)form;
/*  123 */     String period = rf.getPeriod();
/*  124 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/*      */     
/*  126 */     request.setAttribute("defaultView", defaultview);
/*  127 */     request.setAttribute("reloadperiod", "300");
/*      */     
/*  129 */     if ((request.getParameter("PRINTER_FRIENDLY") != null) && (request.getParameter("PRINTER_FRIENDLY").equalsIgnoreCase("true")))
/*      */     {
/*  131 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */     }
/*      */     
/*  134 */     ArrayList eventdata = new ArrayList();
/*  135 */     ArrayList alist = new ArrayList();
/*  136 */     Hashtable ht = new Hashtable();
/*  137 */     String pvalue = new String();
/*      */     
/*      */     try
/*      */     {
/*  141 */       if (showSLA)
/*      */       {
/*  143 */         String query = "";
/*  144 */         String username = request.getRemoteUser();
/*  145 */         String haType = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/*  146 */         String orderByStr = " ORDER BY RESOURCENAME";
/*  147 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  149 */           haType = " ";
/*  150 */           orderByStr = " ";
/*      */         }
/*  152 */         if ((!request.isUserInRole("MANAGER")) && (!ClientDBUtil.isPrivilegedUser(request)))
/*      */         {
/*  154 */           query = "SELECT RESOURCEID,RESOURCENAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION, AM_SLA_RESID_MAPPER where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_SLA_RESID_MAPPER.RESID=AM_HOLISTICAPPLICATION.HAID " + haType + orderByStr;
/*      */ 
/*      */ 
/*      */         }
/*  158 */         else if (Constants.isSsoEnabled()) {
/*  159 */           String loginUserid = Constants.getLoginUserid(request);
/*  160 */           query = "SELECT mo.RESOURCEID,mo.RESOURCENAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_USERRESOURCESTABLE, AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID and AM_SLA_RESID_MAPPER.RESID=ha.HAID and ha.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + "  " + orderByStr;
/*      */         } else {
/*  162 */           query = "SELECT mo.RESOURCEID,mo.RESOURCENAME  FROM AM_ManagedObject as mo,AM_HOLISTICAPPLICATION as ha,AM_HOLISTICAPPLICATION_OWNERS as hao,AM_UserPasswordTable as upt, AM_SLA_RESID_MAPPER where mo.RESOURCEID=ha.HAID and AM_SLA_RESID_MAPPER.RESID=ha.HAID and ha.HAID=hao.HAID and hao.OWNERID=upt.USERID and upt.USERNAME='" + username + "' " + orderByStr;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  167 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  169 */           Vector slaIds = CustomerManagementAPI.filterSLAIds(request);
/*  170 */           if (slaIds != null)
/*      */           {
/*  172 */             query = query + " and " + Constants.getCondition("RESOURCEID", slaIds);
/*      */           }
/*  174 */           query = query + " ORDER BY RESOURCENAME";
/*  175 */           AMLog.debug("generateTroubleTicket -> QUERY -1 = " + query);
/*      */         }
/*  177 */         ArrayList list = new ArrayList();
/*  178 */         ArrayList list1 = new ArrayList();
/*  179 */         alist = this.mo.getRows(query);
/*  180 */         if (alist.size() == 0)
/*      */         {
/*  182 */           query = "SELECT RESOURCEID,RESOURCENAME  FROM AM_ManagedObject,AM_HOLISTICAPPLICATION, AM_SLA_RESID_MAPPER where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID  and AM_SLA_RESID_MAPPER.RESID=AM_HOLISTICAPPLICATION.HAID " + haType + orderByStr;
/*  183 */           if (EnterpriseUtil.isIt360MSPEdition())
/*      */           {
/*  185 */             Vector slaIds = CustomerManagementAPI.filterSLAIds(request);
/*  186 */             if (slaIds != null)
/*      */             {
/*  188 */               query = query + " and " + Constants.getCondition("RESOURCEID", slaIds);
/*      */             }
/*  190 */             query = query + " ORDER BY RESOURCENAME";
/*  191 */             AMLog.debug("generateTroubleTicket -> QUERY -2 = " + query);
/*      */           }
/*  193 */           alist = this.mo.getRows(query);
/*      */         }
/*  195 */         ArrayList data = new ArrayList();
/*      */         
/*  197 */         Hashtable hash = new Hashtable();
/*  198 */         AMReportActions am = new AMReportActions();
/*  199 */         long customstartTime = 0L;
/*  200 */         long customendTime = 0L;
/*  201 */         String startdate = rf.getStartDate();
/*  202 */         String enddate = rf.getEndDate();
/*  203 */         long starttime = 0L;
/*  204 */         long endtime = 0L;
/*      */         
/*  206 */         if ((request.getParameter("value") != null) && (request.getParameter("value").equalsIgnoreCase("4")))
/*      */         {
/*      */ 
/*  209 */           period = String.valueOf(4);
/*  210 */           customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  211 */           customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*      */           
/*  213 */           starttime = customstartTime;
/*  214 */           long currenttime = System.currentTimeMillis();
/*      */           
/*  216 */           if (customendTime > currenttime)
/*      */           {
/*  218 */             endtime = currenttime;
/*      */           }
/*      */           else
/*      */           {
/*  222 */             endtime = customendTime;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  230 */           long[] timestamp = ReportUtilities.getTimeStamp(String.valueOf(period));
/*  231 */           starttime = timestamp[0];
/*  232 */           endtime = timestamp[1];
/*      */         }
/*  234 */         String haid = null;
/*  235 */         String name = null;
/*  236 */         pvalue = cs.getValueForPeriod(period);
/*  237 */         ArrayList forgraph = new ArrayList();
/*      */         
/*  239 */         for (int i = 0; i < alist.size(); i++)
/*      */         {
/*  241 */           Properties props = new Properties();
/*  242 */           list = (ArrayList)alist.get(i);
/*  243 */           haid = (String)list.get(0);
/*  244 */           name = (String)list.get(1);
/*  245 */           String query1 = "select CATEGORY,RESID,SEVERITY,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject,AM_ATTRIBUTES,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_ATTRIBUTES.ATTRIBUTEID=CATEGORY  and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and  ARCHIVEDTIME >= " + starttime + " and ARCHIVEDTIME <= " + endtime + " group by CATEGORY,SEVERITY,RESID order by CATEGORY,SEVERITY";
/*      */           
/*  247 */           data = cs.getDataFromEventTable(query1);
/*  248 */           String critical = (String)data.get(1);
/*  249 */           props.setProperty("resname", name);
/*  250 */           props.setProperty("available", critical);
/*  251 */           props.setProperty("type", "troubleticket");
/*  252 */           forgraph.add(props);
/*  253 */           ht.put(name, (String)data.get(1));
/*      */         }
/*      */         
/*      */ 
/*  257 */         Properties[] propArr = (Properties[])forgraph.toArray(new Properties[0]);
/*  258 */         Arrays.sort(propArr, new ComparatorImpl(false));
/*  259 */         ArrayList listdata = new ArrayList();
/*  260 */         for (int h = 0; h < propArr.length; h++)
/*      */         {
/*      */ 
/*  263 */           Properties tempprop = propArr[h];
/*  264 */           listdata.add(tempprop);
/*      */         }
/*      */         
/*      */ 
/*  268 */         BarSupport b = new BarSupport(listdata);
/*  269 */         request.setAttribute("bargraph", b);
/*  270 */         request.setAttribute("data", data);
/*      */         
/*      */ 
/*  273 */         for (int i = 0; i < alist.size(); i++)
/*      */         {
/*  275 */           ArrayList data1 = new ArrayList();
/*  276 */           list1 = (ArrayList)alist.get(i);
/*  277 */           String haid1 = (String)list1.get(0);
/*  278 */           String name1 = (String)list1.get(1);
/*  279 */           data1.add(haid1);
/*  280 */           data1.add(name1);
/*  281 */           String query1 = "SELECT AM_SLA_RESID_MAPPER.SLA_ID, AM_SLA.NAME as SNAME, AM_SLO.NAME, AM_SLO.ID, AM_SLO_TROUBLETICKETVOLUME.OPERATOR, AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,AM_SLO_TROUBLETICKETVOLUME.DURATION FROM AM_SLA_RESID_MAPPER, AM_SLA, AM_SLO, AM_SLO_TROUBLETICKETVOLUME where AM_SLO_TROUBLETICKETVOLUME.SLO_ID=AM_SLO.ID and AM_SLO.SLA_ID=AM_SLA.ID and AM_SLO.SLO_TYPE_ID=3 and AM_SLA_RESID_MAPPER.SLA_ID=AM_SLA.ID and RESID=" + haid1;
/*  282 */           ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/*  283 */           boolean issla = false;
/*      */           
/*      */ 
/*  286 */           String slaop = "";
/*  287 */           double slavalue = 0.0D;
/*  288 */           double tickvalue = 0.0D;
/*  289 */           String check = "";
/*  290 */           if (rs.next())
/*      */           {
/*  292 */             issla = true;
/*  293 */             check = "true";
/*  294 */             String slid = rs.getString("SLA_ID");
/*  295 */             String slaname = rs.getString("SNAME");
/*  296 */             slaop = rs.getString("OPERATOR");
/*  297 */             String slaval = rs.getString("TICKETVALUE");
/*  298 */             String sladur = rs.getString("DURATION");
/*  299 */             slavalue = Double.parseDouble(slaval);
/*  300 */             data1.add(slid);
/*  301 */             data1.add(slaname);
/*  302 */             data1.add(slaop);
/*  303 */             data1.add(slaval);
/*  304 */             data1.add(sladur);
/*  305 */             tickvalue = 1.0D * slavalue;
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  310 */             check = "false";
/*  311 */             data1.add("not set");
/*  312 */             data1.add("not set");
/*  313 */             data1.add("not");
/*  314 */             data1.add("set");
/*  315 */             data1.add("not set");
/*  316 */             issla = false;
/*      */           }
/*      */           
/*  319 */           for (int k = 0; k < v.size(); k++)
/*      */           {
/*  321 */             long[] timestamp1 = ReportUtilities.getTimeStamp(String.valueOf(v.get(k)));
/*  322 */             long starttime1 = timestamp1[0];
/*  323 */             long endtime1 = timestamp1[1];
/*      */             
/*  325 */             if (k == 6)
/*      */             {
/*      */ 
/*  328 */               tickvalue = 3.0D * slavalue;
/*      */ 
/*      */             }
/*  331 */             else if (k == 7)
/*      */             {
/*  333 */               tickvalue = 12.0D * slavalue;
/*      */             }
/*      */             
/*      */ 
/*  337 */             String query3 = "select CATEGORY,RESID,SEVERITY,sum(OCCURANCES) as OCCURANCES from AM_EventHistoryData,AM_ManagedObject,AM_ATTRIBUTES,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=RESID and AM_ATTRIBUTES.ATTRIBUTEID=CATEGORY  and AM_PARENTCHILDMAPPER.CHILDID=RESID and AM_PARENTCHILDMAPPER.PARENTID=" + haid1 + " and  ARCHIVEDTIME >= " + starttime1 + " and ARCHIVEDTIME <= " + endtime1 + " group by CATEGORY,SEVERITY,RESID order by CATEGORY,SEVERITY";
/*  338 */             ArrayList a3 = cs.checkTroubleTicket(query3, slaop, String.valueOf(tickvalue), check);
/*  339 */             data1.add(a3);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  345 */           eventdata.add(data1);
/*  346 */           rs.close();
/*      */         }
/*      */       }
/*  349 */       request.setAttribute("event", eventdata);
/*  350 */       request.setAttribute("tabtoselect", "2");
/*  351 */       request.setAttribute("array", alist);
/*  352 */       request.setAttribute("map", ht);
/*  353 */       request.setAttribute("pvalue", pvalue);
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  358 */       System.out.println("exception in buisnessaction generate trouble ticket method" + ee);
/*  359 */       ee.printStackTrace();
/*  360 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */     }
/*      */     
/*      */ 
/*  364 */     return new ActionForward("/jsp/BAEventReport.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward generateApplicationAvailablity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  374 */     if (DBUtil.isDelegatedAdmin(request.getRemoteUser()))
/*      */     {
/*  376 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/*      */     
/*  379 */     CustomerManagementAPI.getInstance();boolean showSLA = CustomerManagementAPI.isUserPermittedToSeeAllBSG(request);
/*  380 */     ComparingSla cs = new ComparingSla();
/*  381 */     ActionMessages messages = new ActionMessages();
/*  382 */     ActionErrors errors = new ActionErrors();
/*  383 */     ReportForm rf = (ReportForm)form;
/*      */     
/*  385 */     DBUtil db = new DBUtil();
/*  386 */     String MGService = "false";
/*  387 */     long starttime = 0L;
/*  388 */     long endtime = 0L;
/*  389 */     long customstartTime = 0L;
/*  390 */     long customendTime = 0L;
/*  391 */     BarSupport b = null;
/*  392 */     String sla = null;
/*  393 */     String startdate = rf.getStartDate();
/*  394 */     String enddate = rf.getEndDate();
/*  395 */     Boolean ismanager = Boolean.valueOf(false);
/*  396 */     if ((request.isUserInRole("MANAGER")) || (ClientDBUtil.isPrivilegedUser(request))) {
/*  397 */       ismanager = Boolean.valueOf(true);
/*      */     }
/*  399 */     String username = request.getRemoteUser();
/*  400 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/*      */     
/*  402 */     request.setAttribute("defaultView", defaultview);
/*  403 */     request.setAttribute("selectTabName", request.getParameter("selectTabName") != null ? request.getParameter("selectTabName") : "");
/*  404 */     if ((request.getParameter("PRINTER_FRIENDLY") != null) && (request.getParameter("PRINTER_FRIENDLY").equalsIgnoreCase("true")))
/*      */     {
/*  406 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */     }
/*      */     try
/*      */     {
/*  410 */       sla = request.getParameter("sla");
/*  411 */       ArrayList a1 = new ArrayList();
/*  412 */       Properties pop = new Properties();
/*  413 */       Hashtable ht = new Hashtable();
/*  414 */       Hashtable hash = new Hashtable();
/*  415 */       AMReportActions am = new AMReportActions();
/*  416 */       String period; if ((request.getParameter("value") != null) && (request.getParameter("value").equalsIgnoreCase("4")))
/*      */       {
/*  418 */         String period = String.valueOf(4);
/*  419 */         customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  420 */         customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*  421 */         starttime = customstartTime;
/*  422 */         endtime = customendTime;
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*  430 */         period = rf.getPeriod();
/*      */         
/*  432 */         long[] timestamp = ReportUtilities.getTimeStamp(String.valueOf(period));
/*  433 */         starttime = timestamp[0];
/*  434 */         endtime = timestamp[1];
/*      */       }
/*  436 */       String haid = null;
/*  437 */       String name = null;
/*      */       
/*  439 */       ArrayList forgraph = new ArrayList();
/*      */       
/*  441 */       ArrayList arrlist = new ArrayList();
/*  442 */       if (showSLA)
/*      */       {
/*      */ 
/*  445 */         String fromSchedule = request.getParameter("isschedule");
/*  446 */         if ("true".equals(fromSchedule))
/*      */         {
/*  448 */           ResultSet rs = null;
/*      */           try {
/*  450 */             String query = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID=" + request.getParameter("sid");
/*  451 */             rs = AMConnectionPool.executeQueryStmt(query);
/*  452 */             if (rs.next()) {
/*  453 */               haid = rs.getString("RESOURCEID");
/*      */             }
/*      */           }
/*      */           catch (Exception ex) {
/*  457 */             ex.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*  461 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */           
/*  464 */           String[] temp = haid.split(",");
/*  465 */           Vector slaidlist = new Vector();
/*  466 */           for (String str : temp) {
/*  467 */             slaidlist.add(str);
/*      */           }
/*      */           
/*  470 */           arrlist = cs.checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, username, slaidlist, sla);
/*      */         }
/*  472 */         else if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  474 */           Vector slaIds = CustomerManagementAPI.filterSLAIds(request);
/*  475 */           arrlist = cs.checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, username, slaIds, "true");
/*      */         }
/*      */         else
/*      */         {
/*  479 */           arrlist = cs.checkingApplicationTroubleTicket(starttime, endtime, period, ismanager, username, null, sla);
/*      */         }
/*      */       }
/*      */       
/*  483 */       String unmanage = "0";
/*  484 */       String schedule = "0";
/*  485 */       for (int i = 0; i < arrlist.size(); i++)
/*      */       {
/*  487 */         Properties props = new Properties();
/*  488 */         a1 = (ArrayList)arrlist.get(i);
/*  489 */         haid = (String)a1.get(0);
/*  490 */         name = (String)a1.get(1);
/*  491 */         String available = (String)a1.get(5);
/*      */         
/*  493 */         if (a1.size() == 18) {
/*  494 */           unmanage = (String)a1.get(16);
/*  495 */           schedule = (String)a1.get(17);
/*      */         }
/*      */         else {
/*  498 */           unmanage = "0";
/*  499 */           schedule = "0";
/*      */         }
/*      */         
/*  502 */         String down = "0";
/*      */         
/*  504 */         if ((!available.equalsIgnoreCase("NA")) && (!unmanage.equalsIgnoreCase("NA")) && (!schedule.equalsIgnoreCase("NA")))
/*      */         {
/*      */           try {
/*  507 */             if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("true"))) {
/*  508 */               double up = Double.parseDouble(available);
/*  509 */               double cal = 100.0D - up;
/*  510 */               down = Double.toString(cal);
/*  511 */               props.setProperty("addunmg_schtosla", "false");
/*      */             }
/*      */             else {
/*  514 */               double cal = 100.0D - (Double.parseDouble(available) + Double.parseDouble(unmanage) + Double.parseDouble(schedule));
/*  515 */               down = String.valueOf(cal);
/*  516 */               props.setProperty("addunmg_schtosla", "true");
/*      */             }
/*      */           } catch (Exception ex) {
/*  519 */             ex.printStackTrace();
/*      */           }
/*  521 */           props.setProperty("data", "OK");
/*  522 */           props.setProperty("available", available);
/*  523 */           props.setProperty("downtime", down);
/*  524 */           if ((!unmanage.trim().equals("")) && (!unmanage.trim().equals("0.0"))) {
/*  525 */             props.setProperty("ServicesUnMgPercent", unmanage);
/*      */           }
/*  527 */           if ((!schedule.trim().equals("")) && (!schedule.trim().equals("0.0"))) {
/*  528 */             props.setProperty("ServicesSchPercent", schedule);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  535 */           props.setProperty("data", "NA");
/*  536 */           props.setProperty("available", "0");
/*  537 */           props.setProperty("downtime", "100");
/*  538 */           props.setProperty("ServicesUnMgPercent", "0");
/*  539 */           props.setProperty("ServicesSchPercent", "0");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  544 */         props.setProperty("resname", name);
/*  545 */         props.setProperty("type", "availablity");
/*      */         
/*  547 */         if (mgCheck) {
/*  548 */           MGService = db.getGlobalConfigValueForMGAvailability();
/*  549 */           if (MGService == null)
/*  550 */             MGService = "false";
/*  551 */           mgCheck = false;
/*      */         }
/*      */         
/*  554 */         props.setProperty("MG", MGService);
/*  555 */         forgraph.add(props);
/*      */         
/*  557 */         if (a1.size() == 18) {
/*  558 */           a1.remove(16);
/*  559 */           a1.remove(16);
/*      */         }
/*      */       }
/*      */       
/*  563 */       mgCheck = true;
/*      */       
/*  565 */       Properties[] propArr = (Properties[])forgraph.toArray(new Properties[0]);
/*  566 */       Arrays.sort(propArr, new ComparatorImpl(true));
/*  567 */       ArrayList list = new ArrayList();
/*  568 */       for (int h = 0; h < propArr.length; h++)
/*      */       {
/*      */ 
/*  571 */         Properties tempprop = propArr[h];
/*  572 */         list.add(tempprop);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  577 */       String pvalue = cs.getValueForPeriod(period);
/*  578 */       request.setAttribute("pvalue", pvalue);
/*  579 */       request.setAttribute("checklist", arrlist);
/*      */       
/*  581 */       b = new BarSupport(list);
/*  582 */       request.setAttribute("availgraph", b);
/*  583 */       request.setAttribute("tabtoselect", "0");
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (NullPointerException ex) {}catch (NumberFormatException ex1s) {}catch (Exception ee)
/*      */     {
/*      */ 
/*      */ 
/*  591 */       System.out.println("exception in buisnessaction generate application availablity method" + ee);
/*  592 */       ee.printStackTrace();
/*  593 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  598 */       boolean infoMessage = false;
/*  599 */       if (showSLA)
/*      */       {
/*  601 */         AMConnectionPool con = new AMConnectionPool();
/*  602 */         String query = "select count(*) as slacount from  AM_SLA";
/*  603 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  604 */         if (rs.next())
/*      */         {
/*  606 */           int slacount = rs.getInt("slacount");
/*  607 */           if (slacount <= 0)
/*      */           {
/*  609 */             infoMessage = true;
/*      */           }
/*      */         }
/*  612 */         rs.close();
/*      */       }
/*  614 */       if (infoMessage)
/*      */       {
/*  616 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.manager.nosla.text")));
/*  617 */         saveMessages(request, messages);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  622 */       ex.printStackTrace();
/*      */     }
/*  624 */     if (("pdf".equals(request.getParameter("reportType"))) || ("csv".equals(request.getParameter("reportType")))) {
/*  625 */       String image = null;
/*  626 */       HashMap pdfData = new HashMap();
/*  627 */       StringBuffer csvData = new StringBuffer();
/*  628 */       String availheading = FormatUtil.getString("am.webclient.manager.slatab.heading.text", new String[] { (String)request.getAttribute("pvalue") });
/*  629 */       if ((request.getParameter("value") != null) && (request.getParameter("value").equalsIgnoreCase("4"))) {
/*  630 */         availheading = FormatUtil.getString("am.webclient.manager.slatab.heading.text", new String[] { (String)request.getAttribute("pvalue") }) + " ( " + startdate + " - " + enddate + " )";
/*      */       }
/*  632 */       if ("pdf".equals(request.getParameter("reportType"))) {
/*      */         try {
/*  634 */           ChartInfo cinfo = new ChartInfo();
/*  635 */           cinfo.setDataSet(b);
/*  636 */           image = cinfo.getMultiplePieChartAsJPG();
/*      */         }
/*      */         catch (Exception e) {
/*  639 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  643 */         ArrayList headerList = new ArrayList();
/*  644 */         headerList.add(FormatUtil.getString("am.webclient.common.name.text"));headerList.add(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));headerList.add(FormatUtil.getString("am.webclient.manager.slatab.meetssla.text"));headerList.add(FormatUtil.getString("TotalDowntime"));headerList.add(FormatUtil.getString("Availability") + "%");headerList.add(FormatUtil.getString("MTTR"));headerList.add(FormatUtil.getString("MTBF"));headerList.add(FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/*  645 */         pdfData.put("heading", FormatUtil.getString("am.webclient.report.sla.heading"));
/*  646 */         pdfData.put("Availabilityheading", availheading);
/*  647 */         pdfData.put("AvailabilityImage", image);
/*  648 */         pdfData.put("stats-Table-header", headerList);
/*      */       }
/*      */       else {
/*  651 */         csvData.append("\t\t\t" + FormatUtil.getString("am.webclient.report.sla.heading") + "\n");
/*  652 */         csvData.append(availheading + "\n");
/*  653 */         csvData.append("\n" + FormatUtil.getString("am.webclient.common.name.text") + "," + FormatUtil.getString("am.webclient.manager.slatab.slaname.text") + "," + FormatUtil.getString("am.webclient.manager.slatab.meetssla.text") + "," + FormatUtil.getString("TotalDowntime") + "," + FormatUtil.getString("Availability") + "%," + FormatUtil.getString("MTTR") + "," + FormatUtil.getString("MTBF") + "," + FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/*      */       }
/*      */       
/*  656 */       ArrayList availdata = (ArrayList)request.getAttribute("checklist");
/*  657 */       ArrayList slaDataForPDF = new ArrayList();
/*  658 */       for (int g = 0; g < availdata.size(); g++) {
/*      */         try {
/*  660 */           LinkedHashMap pdfMap = new LinkedHashMap();
/*  661 */           ArrayList alist = (ArrayList)availdata.get(g);
/*  662 */           String resid = (String)alist.get(0);
/*  663 */           String resname = (String)alist.get(1);
/*  664 */           String sname = (String)alist.get(7);
/*  665 */           Properties props = (Properties)alist.get(10);
/*  666 */           String availsla = props.getProperty("SLA");
/*  667 */           String avblity = (String)alist.get(5);
/*  668 */           String totaldown = (String)alist.get(4);
/*  669 */           String mttr = (String)alist.get(2);
/*  670 */           String mtbf = (String)alist.get(3);
/*  671 */           String ticksla = "";
/*  672 */           String ttvalue = "";
/*  673 */           String imgPath = File.separator + "images" + File.separator + "questionmark.gif";
/*  674 */           String meetsSLA = FormatUtil.getString("am.webclient.report.sla.notSet");
/*  675 */           ArrayList a1 = (ArrayList)alist.get(14);
/*  676 */           for (int h = 0; h < a1.size(); h++) {
/*  677 */             ttvalue = (String)a1.get(1);
/*  678 */             ticksla = (String)a1.get(3);
/*      */           }
/*  680 */           String title = resname;
/*  681 */           if (EnterpriseUtil.isAdminServer()) {
/*  682 */             if (CommDBUtil.getManagedServerNameWithPort(resid).equals("Admin Server")) {
/*  683 */               title = FormatUtil.getString("am.webclient.gettingstarted.adminserver.text");
/*  684 */               resname = resname + "_" + title;
/*      */             }
/*      */             else {
/*  687 */               title = FormatUtil.getString("am.webclient.managedserver.tooltip.monitorgroupname", new String[] { CommDBUtil.getManagedServerNameWithPort(resid) });
/*  688 */               resname = resname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */             }
/*      */           }
/*  691 */           if ((availsla == null) || ((avblity != null) && (avblity.equals("NA")))) {
/*  692 */             imgPath = "";
/*  693 */             meetsSLA = FormatUtil.getString("am.webclient.report.sla.notSet");
/*      */           } else {
/*  695 */             imgPath = File.separator + "images" + File.separator + "questionmark.gif";
/*  696 */             if ((availsla.equals("PASS")) && (ticksla.equals("PASS")) && (!sname.equals("not set"))) {
/*  697 */               imgPath = File.separator + "images" + File.separator + "icon_tickmark.gif";
/*  698 */               meetsSLA = FormatUtil.getString("am.webclient.report.sla.pass");
/*  699 */             } else if ((availsla.equals("FAIL")) || (ticksla.equals("FAIL"))) {
/*  700 */               imgPath = File.separator + "images" + File.separator + "cross.gif";
/*  701 */               meetsSLA = FormatUtil.getString("am.webclient.report.sla.fail");
/*      */             }
/*      */           }
/*  704 */           if ("pdf".equals(request.getParameter("reportType"))) {
/*  705 */             pdfMap.put("Name", resname);
/*  706 */             pdfMap.put("SLA Name", sname);
/*  707 */             pdfMap.put("Image-Meets SLA", imgPath);
/*  708 */             pdfMap.put("Total Downtime", totaldown);
/*  709 */             pdfMap.put("Availability%", avblity);
/*  710 */             pdfMap.put("MTTR", mttr);
/*  711 */             pdfMap.put("MTBF", mtbf);
/*  712 */             pdfMap.put("Events", ttvalue);
/*  713 */             slaDataForPDF.add(pdfMap);
/*      */           }
/*      */           else {
/*  716 */             csvData.append("\n" + resname + "," + sname + "," + meetsSLA + "," + totaldown + "," + avblity + "," + mttr + "," + mtbf + "," + ttvalue);
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/*  720 */           e.printStackTrace();
/*      */         }
/*      */       }
/*  723 */       if ("pdf".equals(request.getParameter("reportType"))) {
/*  724 */         pdfData.put("TableHeading", "SLA Statistics");
/*  725 */         pdfData.put("availdata", slaDataForPDF);
/*  726 */         request.setAttribute("data", pdfData);
/*  727 */         request.setAttribute("reportname", "SLAApplicationAvailability");
/*  728 */         request.setAttribute("reportType", "pdf");
/*  729 */         request.setAttribute("report-type-template", "report.SLA.Application");
/*  730 */         return mapping.findForward("report.pdf");
/*      */       }
/*      */       
/*      */ 
/*  734 */       PrintWriter out = response.getWriter();
/*      */       try {
/*  736 */         String reportName = "SLAApplicationsAvailailityReport_" + new Date(System.currentTimeMillis());
/*  737 */         response.setContentType("text/csv; charset=UTF-8");
/*  738 */         response.setHeader("Content-disposition", "attachment;filename=\"" + reportName + ".csv\"");
/*  739 */         out.println(csvData.toString());
/*  740 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  744 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  747 */         if (out != null)
/*      */         {
/*  749 */           out.close();
/*      */         }
/*      */       }
/*  752 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  756 */     return new ActionForward("/jsp/BussinessApplication.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward generateSystemAvailablity(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  767 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  768 */     ReportForm rf = (ReportForm)form;
/*      */     
/*  770 */     ComparingSla cs = new ComparingSla();
/*  771 */     Boolean ismanager = Boolean.valueOf(false);
/*  772 */     BarSupport b = null;
/*  773 */     if ((request.isUserInRole("MANAGER")) || (ClientDBUtil.isPrivilegedUser(request))) {
/*  774 */       ismanager = Boolean.valueOf(true);
/*      */     }
/*  776 */     String username = request.getRemoteUser();
/*  777 */     ActionMessages messages = new ActionMessages();
/*  778 */     ActionErrors errors = new ActionErrors();
/*  779 */     request.setAttribute("reloadperiod", "300");
/*  780 */     String period = null;
/*  781 */     String serverSla = null;
/*  782 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/*      */     
/*  784 */     request.setAttribute("defaultView", defaultview);
/*  785 */     String startdate = rf.getStartDate();
/*  786 */     String enddate = rf.getEndDate();
/*  787 */     long customstartTime = 0L;
/*  788 */     long customendTime = 0L;
/*  789 */     if ((request.getParameter("PRINTER_FRIENDLY") != null) && (request.getParameter("PRINTER_FRIENDLY").equalsIgnoreCase("true")))
/*      */     {
/*  791 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/*      */     }
/*  793 */     if ((request.getParameter("value") != null) && (request.getParameter("value").equalsIgnoreCase("4")))
/*      */     {
/*  795 */       period = "4";
/*      */       
/*  797 */       customstartTime = ReportUtilities.parseAndReturnTimeStamp(startdate);
/*  798 */       customendTime = ReportUtilities.parseAndReturnTimeStamp(enddate);
/*      */     }
/*      */     else
/*      */     {
/*  802 */       period = rf.getPeriod();
/*      */     }
/*      */     
/*  805 */     int per = Integer.parseInt(period);
/*  806 */     String pvalue = cs.getValueForPeriod(period);
/*  807 */     request.setAttribute("pvalue", pvalue);
/*  808 */     AMReportActions am = new AMReportActions();
/*      */     try
/*      */     {
/*  811 */       serverSla = request.getParameter("sla");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  815 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  819 */     ArrayList sysdata = null;
/*  820 */     String fromSchedule = request.getParameter("isschedule");
/*  821 */     if ("true".equals(fromSchedule))
/*      */     {
/*  823 */       ResultSet rs = null;
/*  824 */       String ids = "";
/*      */       try {
/*  826 */         String query = "SELECT RESOURCEID FROM AM_SCHEDULER_RESOURCETYPE_MAPPING where SCHEDULEID=" + request.getParameter("sid");
/*  827 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  828 */         if (rs.next()) {
/*  829 */           ids = rs.getString("RESOURCEID");
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/*  833 */         ex.printStackTrace();
/*      */       }
/*      */       finally {
/*  836 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/*  839 */       String[] temp = ids.split(",");
/*  840 */       Vector slaidlist = new Vector();
/*  841 */       for (String str : temp) {
/*  842 */         slaidlist.add(str);
/*      */       }
/*  844 */       sysdata = cs.checkingSystemAvailablityForManager(per, customstartTime, customendTime, ismanager, username, serverSla, slaidlist);
/*      */     }
/*      */     else {
/*  847 */       sysdata = cs.checkingSystemAvailablityForManager(per, customstartTime, customendTime, ismanager, username, serverSla);
/*      */     }
/*      */     
/*      */ 
/*  851 */     String resid = null;
/*  852 */     String resname = null;
/*  853 */     String sla = null;
/*  854 */     String down = null;
/*  855 */     String mttr = null;
/*  856 */     String mtbf = null;
/*  857 */     String slid = null;
/*  858 */     String slaname = null;
/*  859 */     String slaop = null;
/*  860 */     String slaval = null;
/*  861 */     String available = "";
/*  862 */     String downpercent = "";
/*  863 */     String unmanage = "";
/*  864 */     String schedule = "";
/*  865 */     Hashtable ht = new Hashtable();
/*      */     try
/*      */     {
/*  868 */       String addunmg_schtosla = "true";
/*  869 */       ArrayList graph = new ArrayList();
/*  870 */       for (int k = 0; k < sysdata.size(); k++)
/*      */       {
/*  872 */         Properties props = new Properties();
/*  873 */         ArrayList data = new ArrayList();
/*  874 */         data = (ArrayList)sysdata.get(k);
/*  875 */         for (int i = 0; i < data.size(); i++)
/*      */         {
/*  877 */           resid = (String)data.get(0);
/*  878 */           resname = (String)data.get(1);
/*  879 */           slid = (String)data.get(2);
/*  880 */           slaname = (String)data.get(3);
/*  881 */           slaop = (String)data.get(4);
/*  882 */           slaval = (String)data.get(5);
/*  883 */           Properties p1 = (Properties)data.get(6);
/*  884 */           available = p1.getProperty("available");
/*  885 */           down = p1.getProperty("totaldowntime");
/*  886 */           unmanage = p1.getProperty("UnmanagedPer");
/*  887 */           schedule = p1.getProperty("ScheduledPer");
/*  888 */           mttr = p1.getProperty("mttr");
/*  889 */           mtbf = p1.getProperty("mtbf");
/*  890 */           if ((unmanage == null) || (unmanage.equals("null"))) {
/*  891 */             unmanage = "0";
/*      */           }
/*  893 */           if ((schedule == null) || (schedule.equals("null"))) {
/*  894 */             schedule = "0";
/*      */           }
/*  896 */           if ((!available.equalsIgnoreCase("NA")) && (!unmanage.equalsIgnoreCase("NA")) && (!schedule.equalsIgnoreCase("NA")))
/*      */           {
/*  898 */             if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("true"))) {
/*  899 */               double cal = 100.0D - Double.parseDouble(available);
/*  900 */               downpercent = String.valueOf(cal);
/*  901 */               addunmg_schtosla = "false";
/*      */             }
/*      */             else {
/*  904 */               double cal = 100.0D - (Double.parseDouble(available) + Double.parseDouble(unmanage) + Double.parseDouble(schedule));
/*  905 */               downpercent = String.valueOf(cal);
/*  906 */               addunmg_schtosla = "true";
/*      */             }
/*      */           }
/*      */         }
/*  910 */         props.setProperty("resname", resname);
/*  911 */         props.setProperty("type", "availablity");
/*  912 */         props.setProperty("monitor", "true");
/*  913 */         if ((!available.equalsIgnoreCase("NA")) && (!unmanage.equalsIgnoreCase("NA")) && (!schedule.equalsIgnoreCase("NA")))
/*      */         {
/*  915 */           props.setProperty("data", "OK");
/*  916 */           if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("false"))) {
/*  917 */             if ((!available.trim().equals("")) || (!available.equals("0"))) {
/*  918 */               props.setProperty("available", available);
/*      */             }
/*  920 */             if ((!downpercent.trim().equals("")) || (!downpercent.equals("0"))) {
/*  921 */               props.setProperty("downtime", downpercent);
/*      */             }
/*  923 */             if ((!unmanage.trim().equals("")) || (!unmanage.equals("0"))) {
/*  924 */               props.setProperty("UnmanagedPer", unmanage);
/*      */             }
/*  926 */             if ((!schedule.trim().equals("")) || (!schedule.equals("0"))) {
/*  927 */               props.setProperty("ScheduledPer", schedule);
/*      */             }
/*      */           }
/*      */           else {
/*  931 */             if ((!available.trim().equals("")) || (!available.equals("0"))) {
/*  932 */               props.setProperty("available", available);
/*      */             }
/*  934 */             if ((!downpercent.trim().equals("")) || (!downpercent.equals("0"))) {
/*  935 */               props.setProperty("downtime", downpercent);
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  941 */           props.setProperty("data", "NA");
/*  942 */           if ((Constants.addMaintenanceToAvailablity != null) && (Constants.addMaintenanceToAvailablity.equals("false"))) {
/*  943 */             if (!available.trim().equals("")) {
/*  944 */               props.setProperty("available", available);
/*      */             }
/*  946 */             if (!downpercent.trim().equals("")) {
/*  947 */               props.setProperty("downtime", downpercent);
/*      */             }
/*  949 */             if (!unmanage.trim().equals("")) {
/*  950 */               props.setProperty("UnmanagedPer", unmanage);
/*      */             }
/*  952 */             if (!schedule.trim().equals("")) {
/*  953 */               props.setProperty("ScheduledPer", schedule);
/*      */             }
/*      */           }
/*      */           else {
/*  957 */             if (!available.trim().equals("")) {
/*  958 */               props.setProperty("available", available);
/*      */             }
/*  960 */             if (!downpercent.trim().equals("")) {
/*  961 */               props.setProperty("downtime", downpercent);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  966 */         props.setProperty("addunmg_schtosla", addunmg_schtosla);
/*  967 */         System.out.println("PROPS in BussinessAction:" + props);
/*  968 */         graph.add(props);
/*      */       }
/*      */       
/*  971 */       Properties[] propArr = (Properties[])graph.toArray(new Properties[0]);
/*  972 */       Arrays.sort(propArr, new ComparatorImpl(true));
/*  973 */       ArrayList list = new ArrayList();
/*  974 */       for (int h = 0; h < propArr.length; h++)
/*      */       {
/*  976 */         Properties tempprop = propArr[h];
/*  977 */         list.add(tempprop);
/*      */       }
/*      */       
/*      */ 
/*  981 */       Collections.sort(sysdata, new ComparatorArrayImpl(true));
/*  982 */       request.setAttribute("system", sysdata);
/*  983 */       b = new BarSupport(list);
/*  984 */       request.setAttribute("sysavailgraph", b);
/*      */     }
/*      */     catch (NumberFormatException exx)
/*      */     {
/*  988 */       System.out.println("This Error is Harmless");
/*      */     }
/*      */     catch (NullPointerException ex) {
/*  991 */       System.out.println("Harmless");
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  995 */       System.out.println("exception in buisnessaction system availablity method" + ee);
/*  996 */       ee.printStackTrace();
/*  997 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */     }
/*      */     
/*      */ 
/* 1001 */     request.setAttribute("tabtoselect", "1");
/* 1002 */     if (("pdf".equals(request.getParameter("reportType"))) || ("csv".equals(request.getParameter("reportType")))) {
/* 1003 */       String image = null;
/* 1004 */       HashMap pdfData = new HashMap();
/* 1005 */       StringBuffer csvData = new StringBuffer();
/* 1006 */       String availheading = FormatUtil.getString("am.webclient.manager.serverslatab.graphheading.text", new String[] { (String)request.getAttribute("pvalue") });
/* 1007 */       if ((request.getParameter("value") != null) && (request.getParameter("value").equalsIgnoreCase("4"))) {
/* 1008 */         availheading = FormatUtil.getString("am.webclient.manager.slatab.heading.text", new String[] { (String)request.getAttribute("pvalue") }) + " ( " + startdate + " - " + enddate + " )";
/*      */       }
/* 1010 */       if ("pdf".equals(request.getParameter("reportType"))) {
/*      */         try {
/* 1012 */           ChartInfo cinfo = new ChartInfo();
/* 1013 */           cinfo.setDataSet(b);
/* 1014 */           image = cinfo.getMultiplePieChartAsJPG();
/*      */         }
/*      */         catch (Exception e) {
/* 1017 */           e.printStackTrace();
/*      */         }
/*      */         
/* 1020 */         ArrayList headerList = new ArrayList();
/* 1021 */         headerList.add(FormatUtil.getString("am.webclient.common.name.text"));headerList.add(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));headerList.add(FormatUtil.getString("am.webclient.manager.slatab.meetssla.text"));headerList.add(FormatUtil.getString("TotalDowntime"));headerList.add(FormatUtil.getString("Availability") + "%");headerList.add(FormatUtil.getString("MTTR"));headerList.add(FormatUtil.getString("MTBF"));headerList.add(FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/* 1022 */         pdfData.put("heading", FormatUtil.getString("am.webclient.report.sla.heading"));
/* 1023 */         pdfData.put("Availabilityheading", availheading);
/* 1024 */         pdfData.put("AvailabilityImage", image);
/* 1025 */         pdfData.put("stats-Table-header", headerList);
/*      */       } else {
/* 1027 */         csvData.append("\t\t\t" + FormatUtil.getString("am.webclient.report.sla.heading") + "\n");
/* 1028 */         csvData.append(availheading + "\n");
/* 1029 */         csvData.append("\n" + FormatUtil.getString("am.webclient.common.name.text") + "," + FormatUtil.getString("am.webclient.manager.slatab.slaname.text") + "," + FormatUtil.getString("am.webclient.manager.slatab.meetssla.text") + "," + FormatUtil.getString("TotalDowntime") + "," + FormatUtil.getString("Availability") + "%," + FormatUtil.getString("MTTR") + "," + FormatUtil.getString("MTBF") + "," + FormatUtil.getString("am.webclient.manager.ttslatab.yaxis.text"));
/*      */       }
/*      */       
/* 1032 */       ArrayList availdata = (ArrayList)request.getAttribute("system");
/* 1033 */       ArrayList slaDataForPDF = new ArrayList();
/* 1034 */       for (int g = 0; g < availdata.size(); g++) {
/*      */         try {
/* 1036 */           LinkedHashMap pdfMap = new LinkedHashMap();
/* 1037 */           ArrayList alist = (ArrayList)availdata.get(g);
/* 1038 */           resid = (String)alist.get(0);
/* 1039 */           resname = (String)alist.get(1);
/* 1040 */           String sname = (String)alist.get(3);
/* 1041 */           Properties p1 = (Properties)alist.get(6);
/*      */           
/* 1043 */           String totaldown = p1.getProperty("totaldowntime");
/* 1044 */           mttr = p1.getProperty("mttr");
/* 1045 */           mtbf = p1.getProperty("mtbf");
/* 1046 */           String availsla = p1.getProperty("SLA");
/* 1047 */           String avblity = p1.getProperty("available");
/* 1048 */           String ttvalue = "";
/* 1049 */           String ticksla = "";
/* 1050 */           String meetsSLA = "NA";
/* 1051 */           String imgPath = File.separator + "images" + File.separator + "questionmark.gif";
/* 1052 */           ArrayList a1 = (ArrayList)alist.get(10);
/* 1053 */           for (int h = 0; h < a1.size(); h++) {
/* 1054 */             ttvalue = (String)a1.get(1);
/* 1055 */             ticksla = (String)a1.get(3);
/*      */           }
/* 1057 */           String title = resname;
/* 1058 */           if (EnterpriseUtil.isAdminServer()) {
/* 1059 */             if (CommDBUtil.getManagedServerNameWithPort(resid).equals("Admin Server")) {
/* 1060 */               title = FormatUtil.getString("am.webclient.gettingstarted.adminserver.text");
/* 1061 */               resname = resname + "_" + title;
/*      */             }
/*      */             else {
/* 1064 */               title = FormatUtil.getString("am.webclient.managedserver.tooltip.monitorgroupname", new String[] { CommDBUtil.getManagedServerNameWithPort(resid) });
/* 1065 */               resname = resname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */             }
/*      */           }
/* 1068 */           if ((availsla == null) || ((avblity != null) && (avblity.equals("NA")))) {
/* 1069 */             imgPath = "";meetsSLA = "NA";
/*      */           } else {
/* 1071 */             imgPath = File.separator + "images" + File.separator + "questionmark.gif";
/* 1072 */             meetsSLA = FormatUtil.getString("am.webclient.report.sla.notSet");
/* 1073 */             if ((availsla.equals("PASS")) && (ticksla.equals("PASS")) && (!sname.equals("not set"))) {
/* 1074 */               imgPath = File.separator + "images" + File.separator + "icon_tickmark.gif";
/* 1075 */               meetsSLA = FormatUtil.getString("am.webclient.report.sla.pass");
/* 1076 */             } else if ((availsla.equals("FAIL")) || (ticksla.equals("FAIL"))) {
/* 1077 */               imgPath = File.separator + "images" + File.separator + "cross.gif";
/* 1078 */               meetsSLA = FormatUtil.getString("am.webclient.report.sla.fail");
/*      */             }
/*      */           }
/* 1081 */           if ("pdf".equals(request.getParameter("reportType"))) {
/* 1082 */             pdfMap.put("Name", resname);
/* 1083 */             pdfMap.put("SLA Name", sname);
/* 1084 */             pdfMap.put("Image-Meets SLA", imgPath);
/* 1085 */             pdfMap.put("Total Downtime", totaldown);
/* 1086 */             pdfMap.put("Availability%", avblity);
/* 1087 */             pdfMap.put("MTTR", mttr);
/* 1088 */             pdfMap.put("MTBF", mtbf);
/* 1089 */             pdfMap.put("Events", ttvalue);
/* 1090 */             slaDataForPDF.add(pdfMap);
/*      */           } else {
/* 1092 */             csvData.append("\n" + resname + "," + sname + "," + meetsSLA + "," + totaldown + "," + avblity + "," + mttr + "," + mtbf + "," + ttvalue);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1097 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1100 */       if ("pdf".equals(request.getParameter("reportType"))) {
/* 1101 */         pdfData.put("TableHeading", "Server Availability Statistics - UpTime %");
/* 1102 */         pdfData.put("availdata", slaDataForPDF);
/* 1103 */         request.setAttribute("data", pdfData);
/* 1104 */         request.setAttribute("reportname", "SLASystemAvailability");
/* 1105 */         request.setAttribute("reportType", "pdf");
/* 1106 */         request.setAttribute("report-type-template", "report.SLA.Application");
/* 1107 */         return mapping.findForward("report.pdf");
/*      */       }
/*      */       
/*      */ 
/* 1111 */       PrintWriter out = response.getWriter();
/*      */       try {
/* 1113 */         String reportName = "SLAServersAvailailityReport_" + new Date(System.currentTimeMillis());
/* 1114 */         response.setContentType("text/csv; charset=UTF-8");
/* 1115 */         response.setHeader("Content-disposition", "attachment;filename=\"" + reportName + ".csv\"");
/* 1116 */         out.println(csvData.toString());
/* 1117 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1121 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 1124 */         if (out != null)
/*      */         {
/* 1126 */           out.close();
/*      */         }
/*      */       }
/* 1129 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1133 */     return new ActionForward("/jsp/SystemAvailablity.jsp");
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getOperationalHours(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1150 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1151 */     ReportForm rf = (ReportForm)form;
/*      */     
/*      */ 
/*      */ 
/* 1155 */     ActionMessages messages = new ActionMessages();
/* 1156 */     ActionErrors errors = new ActionErrors();
/* 1157 */     String value = request.getParameter("value");
/* 1158 */     if ((value != null) && (value.equals("true")))
/*      */     {
/* 1160 */       request.setAttribute("tabtoselect", "3");
/* 1161 */       return new ActionForward("/jsp/OperationalHours.jsp");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1170 */       String starthour = rf.getStarthours();
/* 1171 */       String startminute = rf.getStartminutes();
/* 1172 */       String endhour = rf.getEndhours();
/* 1173 */       String endminute = rf.getEndminutes();
/* 1174 */       String hourstowork = rf.getWorkinghours();
/* 1175 */       String mon = rf.getMonday();
/* 1176 */       String tue = rf.getTuesday();
/* 1177 */       String wed = rf.getWednesday();
/* 1178 */       String thur = rf.getThursday();
/* 1179 */       String fri = rf.getFriday();
/* 1180 */       String sat = rf.getSaturday();
/* 1181 */       String sun = rf.getSunday();
/* 1182 */       String timequery = "insert into AM_WORKINGHOURS(STARTHOUR,STARTMINUTE,ENDHOUR,ENDMINUTE,HOURSTOWORK) VALUES ( " + starthour + "," + startminute + " ," + endhour + " ," + endminute + " , '" + hourstowork + "')";
/*      */       
/* 1184 */       String dayquery = "insert into AM_WORKINGDAYS(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY) VALUES ( '" + mon + " ', '" + tue + "' , '" + wed + "'  , '" + thur + "' , '" + fri + "'  , '" + sat + "'  , '" + sun + "')";
/*      */       
/* 1186 */       AMConnectionPool.executeUpdateStmt(timequery);
/* 1187 */       AMConnectionPool.executeUpdateStmt(dayquery);
/* 1188 */       request.setAttribute("tabtoselect", "3");
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*      */ 
/* 1195 */       System.out.println("exception in buisnessaction " + ee);
/* 1196 */       ee.printStackTrace();
/* 1197 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1202 */     return new ActionForward("/jsp/OperationalHours.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addSLA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1214 */     ReportForm rf = (ReportForm)form;
/* 1215 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1216 */     ActionMessages messages = new ActionMessages();
/* 1217 */     ActionErrors errors = new ActionErrors();
/* 1218 */     ResultSet rs = null;
/* 1219 */     Statement toinsert = null;
/* 1220 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/* 1221 */     String reqForAdminLayout = (request.getParameter("reqForAdminLayout") != null) && (request.getParameter("reqForAdminLayout").equalsIgnoreCase("true")) ? request.getParameter("reqForAdminLayout") : "";
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1227 */       Connection c = AMConnectionPool.getConnection();
/* 1228 */       toinsert = c.createStatement();
/* 1229 */       toinsert.setQueryTimeout(600);
/* 1230 */       String sname = rf.getSlaname();
/* 1231 */       String sdesc = rf.getSladesc();
/* 1232 */       String disable = rf.getDisablemail();
/* 1233 */       String actname = "SLA_" + sname + "Action";
/* 1234 */       String from = rf.getFrom();
/* 1235 */       String dismail = "";
/* 1236 */       if (disable != null)
/*      */       {
/* 1238 */         dismail = disable;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1243 */         dismail = "false";
/*      */       }
/* 1245 */       String to = rf.getTo();
/*      */       
/* 1247 */       String subject = rf.getSubject();
/* 1248 */       String message = rf.getMessage();
/*      */       
/* 1250 */       String slaid = "";
/* 1251 */       int tableid = getNextTableId("AM_SLA", "ID");
/* 1252 */       String query1 = "INSERT INTO AM_SLA(ID,NAME,DESCRIPTION,MAILOPTION) VALUES (" + tableid + ",'" + sname + "' ,'" + sdesc + "' , '" + dismail + "')";
/*      */       
/* 1254 */       toinsert.executeUpdate(query1, 1);
/* 1255 */       rs = toinsert.getGeneratedKeys();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1261 */       slaid = Integer.toString(tableid);
/*      */       
/*      */ 
/* 1264 */       String mailcheck = rf.getMailcheck();
/* 1265 */       if ((mailcheck != null) && (mailcheck.equalsIgnoreCase("mail")))
/*      */       {
/* 1267 */         insertAction(slaid, from, to, subject, message, actname);
/*      */       }
/* 1269 */       addSlo(mapping, form, request, response, slaid, sname);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1274 */       request.setAttribute("tabtoselect", "3");
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*      */ 
/* 1280 */       System.out.println("exception in buisnessaction add SLA method" + ee);
/* 1281 */       ee.printStackTrace();
/* 1282 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1287 */       rs.close();
/*      */     }
/*      */     
/* 1290 */     request.setAttribute("tabtoselect", "3");
/*      */     
/* 1292 */     generateSLA(mapping, form, request, response);
/*      */     
/* 1294 */     return new ActionForward("/showBussiness.do?method=generateSLA&sla=true&defaultView=" + defaultview + "&reqForAdminLayout=" + reqForAdminLayout, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public void insertAction(String slaid, String from, String to, String subject, String message, String actname)
/*      */   {
/* 1300 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */     try
/*      */     {
/* 1304 */       int nextId = DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE");
/* 1305 */       String act1 = " INSERT INTO AM_ACTIONPROFILE(ID,NAME,TYPE) VALUES (" + nextId + ",'" + actname + "','25')";
/* 1306 */       AMConnectionPool.executeUpdateStmt(act1);
/* 1307 */       String getid = "SELECT ID FROM AM_ACTIONPROFILE WHERE NAME= '" + actname + "' AND TYPE = 25";
/* 1308 */       ResultSet r1 = AMConnectionPool.executeQueryStmt(getid);
/* 1309 */       String actid = "";
/*      */       
/* 1311 */       if (r1.next())
/*      */       {
/* 1313 */         actid = r1.getString("ID");
/*      */       }
/*      */       
/*      */ 
/* 1317 */       String act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE) values (" + actid + ",'" + from + "','" + to + "','" + subject + "','" + message + "' )";
/* 1318 */       AMConnectionPool.executeUpdateStmt(act2);
/*      */       
/* 1320 */       String queryact = "INSERT INTO AM_SLA_ACTION_MAPPER VALUES( '" + slaid + "' , '" + actid + "')";
/* 1321 */       AMConnectionPool.executeUpdateStmt(queryact);
/* 1322 */       r1.close();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1327 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Hashtable popupForSla(String slaid)
/*      */   {
/* 1338 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/* 1341 */     Hashtable ht = new Hashtable();
/*      */     
/*      */     try
/*      */     {
/* 1345 */       String quer = "select AM_SLA.ID,AM_SLA.NAME,AM_SLA.DESCRIPTION,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.OPERATOR,'-1') AS APPOP,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL,'-1') AS APPVAL,COALESCE(AM_SLO_SYSTEMAVAILABLITY.OPERATOR,'-1') AS SYSOP,COALESCE(AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL,'-1') AS SYSVAL,COALESCE(AM_SLO_TROUBLETICKETVOLUME.OPERATOR,'-1') AS TICKOP, COALESCE(AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,'-1')  AS TICKVAL FROM AM_SLA,AM_SLO left outer join AM_SLO_APPLICATIONAVAILABLITY on AM_SLO.ID=AM_SLO_APPLICATIONAVAILABLITY.SLO_ID left outer join AM_SLO_SYSTEMAVAILABLITY on AM_SLO.ID=AM_SLO_SYSTEMAVAILABLITY.SLO_ID left outer join AM_SLO_TROUBLETICKETVOLUME on AM_SLO.ID=AM_SLO_TROUBLETICKETVOLUME.SLO_ID WHERE AM_SLA.ID=AM_SLO.SLA_ID and AM_SLA.ID=" + slaid + "  order by AM_SLA.NAME";
/* 1346 */       ArrayList arr = new ArrayList();
/*      */       
/* 1348 */       ResultSet rs = AMConnectionPool.executeQueryStmt(quer);
/*      */       
/*      */ 
/*      */ 
/* 1352 */       String appop = "";
/* 1353 */       String sysop = "";
/* 1354 */       String tickop = "";
/*      */       
/* 1356 */       while (rs.next())
/*      */       {
/*      */ 
/* 1359 */         String sid = rs.getString("ID");
/* 1360 */         if (!arr.contains(sid))
/*      */         {
/* 1362 */           arr.add(sid);
/*      */         }
/*      */         
/* 1365 */         sysop = rs.getString("SYSOP");
/* 1366 */         String nulval = String.valueOf(-1);
/* 1367 */         Properties p1 = (Properties)ht.get(sid);
/* 1368 */         if (p1 == null)
/*      */         {
/* 1370 */           p1 = new Properties();
/* 1371 */           p1.setProperty("appop", "not");
/* 1372 */           p1.setProperty("appval", "set");
/* 1373 */           p1.setProperty("sysop", "not");
/* 1374 */           p1.setProperty("sysval", "set");
/* 1375 */           p1.setProperty("tickop", "not");
/*      */           
/* 1377 */           p1.setProperty("tickval", "set");
/* 1378 */           p1.setProperty("type", "not defined");
/*      */         }
/*      */         
/* 1381 */         String sname = rs.getString("NAME");
/* 1382 */         String sdesc = rs.getString("DESCRIPTION");
/* 1383 */         p1.setProperty("sname", sname);
/* 1384 */         p1.setProperty("sdesc", sdesc);
/* 1385 */         appop = rs.getString("APPOP");
/* 1386 */         String appval = String.valueOf(rs.getFloat("APPVAL"));
/*      */         
/* 1388 */         if (!appop.equalsIgnoreCase(nulval))
/*      */         {
/*      */ 
/* 1391 */           p1.setProperty("appop", appop);
/* 1392 */           p1.setProperty("appval", appval);
/* 1393 */           p1.setProperty("type", "Business Application");
/*      */         }
/*      */         
/*      */ 
/* 1397 */         String sysval = String.valueOf(rs.getFloat("SYSVAL"));
/* 1398 */         if (!sysop.equalsIgnoreCase(nulval))
/*      */         {
/*      */ 
/* 1401 */           p1.setProperty("appop", sysop);
/* 1402 */           p1.setProperty("appval", sysval);
/* 1403 */           p1.setProperty("type", "Server");
/*      */         }
/*      */         
/* 1406 */         tickop = rs.getString("TICKOP");
/* 1407 */         String tickval = rs.getString("TICKVAL");
/* 1408 */         if (!tickop.equalsIgnoreCase(nulval))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1413 */           p1.setProperty("tickop", tickop);
/* 1414 */           p1.setProperty("tickval", tickval);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1419 */         ht.put(sid, p1);
/*      */       }
/*      */       
/* 1422 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception exec)
/*      */     {
/*      */ 
/* 1428 */       exec.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1432 */     return ht;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward generateSLA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1445 */     CustomerManagementAPI.getInstance();boolean showSLA = CustomerManagementAPI.isUserPermittedToSeeAllBSG(request);
/* 1446 */     ArrayList arr = new ArrayList();
/* 1447 */     Hashtable ht = new Hashtable();
/* 1448 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/* 1449 */     String reqForAdminLayout = (request.getParameter("reqForAdminLayout") != null) && (request.getParameter("reqForAdminLayout").equalsIgnoreCase("true")) ? request.getParameter("reqForAdminLayout") : "";
/* 1450 */     if (showSLA)
/*      */     {
/* 1452 */       ReportForm rf = (ReportForm)form;
/* 1453 */       String param = request.getParameter("sla");
/*      */       
/*      */ 
/* 1456 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1457 */       ActionMessages messages = new ActionMessages();
/* 1458 */       ActionErrors errors = new ActionErrors();
/* 1459 */       Boolean ismanager = Boolean.valueOf(false);
/* 1460 */       if ((request.isUserInRole("MANAGER")) || (ClientDBUtil.isPrivilegedUser(request))) {
/* 1461 */         ismanager = Boolean.valueOf(true);
/*      */       }
/* 1463 */       String username = request.getRemoteUser();
/* 1464 */       Vector slaIds = null;
/* 1465 */       Vector resIds = null;
/*      */       
/* 1467 */       request.setAttribute("defaultView", defaultview);
/* 1468 */       request.setAttribute("reqForAdminLayout", reqForAdminLayout);
/* 1469 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 1471 */         slaIds = CustomerManagementAPI.getFilteredSLAIds(request);
/* 1472 */         resIds = CustomerManagementAPI.filterSLAIds(request);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1477 */         String quer = "";
/*      */         
/* 1479 */         if (!ismanager.booleanValue())
/*      */         {
/* 1481 */           String orderByStr = "";
/* 1482 */           quer = "select AM_SLA.ID,AM_SLA.NAME,AM_SLA.DESCRIPTION,AM_SLA.MAILOPTION,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.OPERATOR,'-1') AS APPOP,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL,'-1') AS APPVAL,COALESCE(AM_SLO_SYSTEMAVAILABLITY.OPERATOR,'-1') AS SYSOP,COALESCE(AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL,'-1') AS SYSVAL,COALESCE(AM_SLO_TROUBLETICKETVOLUME.OPERATOR,'-1') AS TICKOP, COALESCE(AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,'-1')  AS TICKVAL FROM AM_SLA,AM_SLO left outer join AM_SLO_APPLICATIONAVAILABLITY on AM_SLO.ID=AM_SLO_APPLICATIONAVAILABLITY.SLO_ID left outer join AM_SLO_SYSTEMAVAILABLITY on AM_SLO.ID=AM_SLO_SYSTEMAVAILABLITY.SLO_ID left outer join AM_SLO_TROUBLETICKETVOLUME on AM_SLO.ID=AM_SLO_TROUBLETICKETVOLUME.SLO_ID WHERE AM_SLA.ID=AM_SLO.SLA_ID " + orderByStr;
/* 1483 */           if (EnterpriseUtil.isIt360MSPEdition())
/*      */           {
/* 1485 */             quer = quer + " and " + Constants.getCondition("SLA_ID", slaIds);
/*      */           }
/* 1487 */           quer = quer + orderByStr;
/* 1488 */           AMLog.debug("generateSLA -> QUERY -1 = " + quer);
/*      */         }
/*      */         else {
/* 1491 */           String mancondn = ReportUtilities.getQueryCondition("AM_SLA_RESID_MAPPER.RESID", username);
/* 1492 */           if (!mancondn.trim().equals("AM_SLA_RESID_MAPPER.RESID in (-1)"))
/*      */           {
/* 1494 */             quer = "SELECT AM_SLA.ID,AM_SLA.NAME,AM_SLA.DESCRIPTION,AM_SLA.MAILOPTION, COALESCE(AM_SLO_APPLICATIONAVAILABLITY.OPERATOR, '-1') AS APPOP,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL, '-1') AS APPVAL,COALESCE(AM_SLO_SYSTEMAVAILABLITY.OPERATOR, '-1') AS SYSOP,COALESCE(AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL, '-1') AS SYSVAL, COALESCE(AM_SLO_TROUBLETICKETVOLUME.OPERATOR, '-1') AS TICKOP,COALESCE(AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE, '-1') AS TICKVAL FROM  AM_SLA JOIN AM_SLO on  AM_SLA.ID  = AM_SLO.SLA_ID LEFT OUTER JOIN AM_SLO_APPLICATIONAVAILABLITY ON AM_SLO.ID  = AM_SLO_APPLICATIONAVAILABLITY.SLO_ID  LEFT OUTER JOIN AM_SLO_SYSTEMAVAILABLITY ON AM_SLO.ID  = AM_SLO_SYSTEMAVAILABLITY.SLO_ID  LEFT OUTER JOIN AM_SLO_TROUBLETICKETVOLUME ON AM_SLO.ID  = AM_SLO_TROUBLETICKETVOLUME.SLO_ID  LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_SLA.ID  = AM_SLA_RESID_MAPPER.SLA_ID  where  " + mancondn + " ORDER BY AM_SLA.NAME";
/*      */           }
/*      */           else
/*      */           {
/* 1498 */             quer = "select AM_SLA.ID,AM_SLA.NAME,AM_SLA.DESCRIPTION,AM_SLA.MAILOPTION,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.OPERATOR,'-1') AS APPOP,COALESCE(AM_SLO_APPLICATIONAVAILABLITY.PERCENTAVAIL,'-1') AS APPVAL,COALESCE(AM_SLO_SYSTEMAVAILABLITY.OPERATOR,'-1') AS SYSOP,COALESCE(AM_SLO_SYSTEMAVAILABLITY.PERCENTAVAIL,'-1') AS SYSVAL,COALESCE(AM_SLO_TROUBLETICKETVOLUME.OPERATOR,'-1') AS TICKOP, COALESCE(AM_SLO_TROUBLETICKETVOLUME.TICKETVALUE,'-1')  AS TICKVAL FROM AM_SLA,AM_SLO left outer join AM_SLO_APPLICATIONAVAILABLITY on AM_SLO.ID=AM_SLO_APPLICATIONAVAILABLITY.SLO_ID left outer join AM_SLO_SYSTEMAVAILABLITY on AM_SLO.ID=AM_SLO_SYSTEMAVAILABLITY.SLO_ID left outer join AM_SLO_TROUBLETICKETVOLUME on AM_SLO.ID=AM_SLO_TROUBLETICKETVOLUME.SLO_ID WHERE AM_SLA.ID=AM_SLO.SLA_ID order by AM_SLA.NAME ";
/*      */           }
/*      */         }
/*      */         
/* 1502 */         ResultSet rs = AMConnectionPool.executeQueryStmt(quer);
/* 1503 */         String q1 = "select HOST from AM_MAILSETTINGS ";
/* 1504 */         ResultSet rst = AMConnectionPool.executeQueryStmt(q1);
/* 1505 */         if (rst.next())
/*      */         {
/* 1507 */           request.setAttribute("smtp", "true");
/*      */         }
/*      */         else
/*      */         {
/* 1511 */           request.setAttribute("smtp", "false");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1516 */         String appop = "";
/* 1517 */         String sysop = "";
/* 1518 */         String tickop = "";
/*      */         
/*      */ 
/* 1521 */         while (rs.next())
/*      */         {
/*      */ 
/* 1524 */           String sid = rs.getString("ID");
/* 1525 */           if (!arr.contains(sid))
/*      */           {
/* 1527 */             arr.add(sid);
/*      */           }
/*      */           
/* 1530 */           sysop = rs.getString("SYSOP");
/* 1531 */           String nulval = String.valueOf(-1);
/* 1532 */           Properties p1 = (Properties)ht.get(sid);
/* 1533 */           if (p1 == null)
/*      */           {
/* 1535 */             p1 = new Properties();
/* 1536 */             p1.setProperty("appop", "not");
/* 1537 */             p1.setProperty("appval", "set");
/* 1538 */             p1.setProperty("sysop", "not");
/* 1539 */             p1.setProperty("sysval", "set");
/* 1540 */             p1.setProperty("tickop", "not");
/*      */             
/* 1542 */             p1.setProperty("tickval", "set");
/*      */             
/* 1544 */             p1.setProperty("type", "not defined");
/*      */           }
/*      */           
/* 1547 */           String sname = rs.getString("NAME");
/* 1548 */           String sdesc = rs.getString("DESCRIPTION");
/* 1549 */           String disable = rs.getString("MAILOPTION");
/* 1550 */           p1.setProperty("sname", sname);
/* 1551 */           p1.setProperty("sdesc", sdesc);
/* 1552 */           p1.setProperty("option", disable);
/* 1553 */           appop = rs.getString("APPOP");
/* 1554 */           String appval = String.valueOf(rs.getFloat("APPVAL"));
/*      */           
/* 1556 */           if (!appop.equalsIgnoreCase(nulval))
/*      */           {
/*      */ 
/* 1559 */             p1.setProperty("appop", appop);
/* 1560 */             p1.setProperty("appval", appval);
/* 1561 */             p1.setProperty("type", "Business Application");
/*      */           }
/*      */           
/*      */ 
/* 1565 */           String sysval = String.valueOf(rs.getFloat("SYSVAL"));
/* 1566 */           if (!sysop.equalsIgnoreCase(nulval))
/*      */           {
/*      */ 
/* 1569 */             p1.setProperty("appop", sysop);
/* 1570 */             p1.setProperty("appval", sysval);
/* 1571 */             p1.setProperty("type", "Server");
/*      */           }
/*      */           
/*      */ 
/* 1575 */           tickop = rs.getString("TICKOP");
/* 1576 */           String tickval = rs.getString("TICKVAL");
/* 1577 */           if (!tickop.equalsIgnoreCase(nulval))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/* 1582 */             p1.setProperty("tickop", tickop);
/* 1583 */             p1.setProperty("tickval", tickval);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1589 */           ht.put(sid, p1);
/*      */         }
/*      */         
/* 1592 */         rs.close();
/* 1593 */         rst.close();
/*      */ 
/*      */       }
/*      */       catch (Exception exec)
/*      */       {
/* 1598 */         exec.printStackTrace();
/*      */       }
/*      */       
/* 1601 */       String oldquery = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject join AM_HOLISTICAPPLICATION on AM_ManagedObject.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_SLA_RESID_MAPPER.RESID  in (AM_ManagedObject.RESOURCEID) WHERE AM_ManagedObject.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID  AND AM_HOLISTICAPPLICATION.TYPE =0 AND RESID is null  ";
/* 1602 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*      */ 
/* 1605 */         oldquery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject left join AM_SLA_RESID_MAPPER on AM_SLA_RESID_MAPPER.RESID=AM_ManagedObject.RESOURCEID inner join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION_EXT.RESOURCEID=AM_SLA_RESID_MAPPER.RESID " + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", resIds);
/*      */       }
/*      */       
/* 1608 */       String query = "";
/* 1609 */       String cond = "";
/* 1610 */       if (Constants.subGroupsEnabled.equals("false"))
/*      */       {
/* 1612 */         cond = " AND AM_HOLISTICAPPLICATION.TYPE  = 0";
/*      */       }
/* 1614 */       if (!ismanager.booleanValue())
/*      */       {
/* 1616 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1618 */           query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject left join AM_SLA_RESID_MAPPER on AM_SLA_RESID_MAPPER.RESID=AM_ManagedObject.RESOURCEID inner join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION_EXT.RESOURCEID=AM_SLA_RESID_MAPPER.RESID " + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", resIds);
/*      */         }
/*      */         else
/*      */         {
/* 1622 */           query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject JOIN AM_HOLISTICAPPLICATION ON AM_ManagedObject.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID  LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_SLA_RESID_MAPPER.RESID  in ( AM_ManagedObject.RESOURCEID) WHERE RESID is null" + cond;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 1627 */       else if (Constants.isUserResourceEnabled()) {
/* 1628 */         String loginUserid = Constants.getLoginUserid(request);
/* 1629 */         query = "SELECT  mo.RESOURCEID,mo.DISPLAYNAME\tFROM  AM_ManagedObject AS  mo join  AM_HOLISTICAPPLICATION on mo.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID JOIN AM_USERRESOURCESTABLE on AM_HOLISTICAPPLICATION.HAID  = AM_USERRESOURCESTABLE.RESOURCEID  LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_SLA_RESID_MAPPER.RESID  in ( mo.RESOURCEID )\t WHERE AM_USERRESOURCESTABLE.USERID =" + loginUserid + " " + cond + "\tAND AM_SLA_RESID_MAPPER.RESID  is null ";
/*      */       } else {
/* 1631 */         query = "SELECT  mo.RESOURCEID,mo.DISPLAYNAME\tFROM  AM_ManagedObject AS  mo join  AM_HOLISTICAPPLICATION on mo.RESOURCEID  = AM_HOLISTICAPPLICATION.HAID JOIN AM_HOLISTICAPPLICATION_OWNERS AS  hao on AM_HOLISTICAPPLICATION.HAID  = hao.HAID JOIN AM_UserPasswordTable AS  upt on hao.OWNERID  = upt.USERID   LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_SLA_RESID_MAPPER.RESID  in ( mo.RESOURCEID )\t WHERE upt.USERNAME ='" + username + "' " + cond + "\tAND AM_SLA_RESID_MAPPER.RESID  is null ";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1637 */       FormatUtil.printQueryChange("BussinessAction.java", oldquery, query);
/*      */       
/* 1639 */       ArrayList rows = getMonitorGroups(query, true);
/* 1640 */       if (rows.size() == 0)
/*      */       {
/* 1642 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1644 */           query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject left join AM_SLA_RESID_MAPPER on AM_SLA_RESID_MAPPER.RESID=AM_ManagedObject.RESOURCEID inner join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION_EXT.RESOURCEID=AM_SLA_RESID_MAPPER.RESID " + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", resIds);
/*      */         }
/*      */         else
/*      */         {
/* 1648 */           query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject join AM_HOLISTICAPPLICATION on  AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_SLA_RESID_MAPPER on  AM_SLA_RESID_MAPPER.RESID in (AM_ManagedObject.RESOURCEID) where  AM_HOLISTICAPPLICATION.TYPE=0 and RESID is null";
/*      */         }
/* 1650 */         rows = getMonitorGroups(query, true);
/*      */       }
/* 1652 */       rows = updateSGNameswithTree(rows);
/* 1653 */       rf.setToAdd(rows);
/* 1654 */       request.setAttribute("moToSelected", rows);
/* 1655 */       String oldquery2 = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID  = AM_SLA_RESID_MAPPER.RESID WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'sys' AND AM_ManagedResourceType.RESOURCETYPE  NOT IN ( 'Node' ,'snmp-node') AND AM_SLA_RESID_MAPPER.SLA_ID  is null ORDER BY RESOURCENAME";
/* 1656 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/* 1658 */         query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID  = AM_SLA_RESID_MAPPER.RESID WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'sys' AND AM_ManagedResourceType.RESOURCETYPE  NOT IN ( 'Node' ,'snmp-node') AND AM_SLA_RESID_MAPPER.SLA_ID  is null " + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", resIds) + "  ORDER BY RESOURCENAME";
/*      */       }
/*      */       
/*      */ 
/* 1662 */       String query2 = "";
/* 1663 */       if (!ismanager.booleanValue())
/*      */       {
/* 1665 */         String orderByStr = " ORDER BY RESOURCENAME";
/* 1666 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/* 1668 */           query2 = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID  = AM_SLA_RESID_MAPPER.RESID  WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'sys' AND\tAM_ManagedResourceType.RESOURCETYPE  NOT IN ( 'Node','snmp-node')  AND\tAM_SLA_RESID_MAPPER.SLA_ID  is null " + " and " + Constants.getCondition("AM_ManagedObject.RESOURCEID", resIds) + orderByStr;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1673 */           query2 = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM AM_ManagedObject JOIN AM_ManagedResourceType ON AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedResourceType.RESOURCETYPE NOT IN ('Node','snmp-node') LEFT JOIN AM_SLA_RESID_MAPPER ON AM_SLA_RESID_MAPPER.RESID IN (AM_ManagedObject.RESOURCEID) WHERE RESID IS NULL " + orderByStr;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1678 */         String mancondn = ReportUtilities.getQueryCondition("AM_ManagedObject.RESOURCEID", username);
/* 1679 */         if (!mancondn.trim().equals("AM_ManagedObject.RESOURCEID in (-1)"))
/*      */         {
/* 1681 */           query2 = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID  = AM_SLA_RESID_MAPPER.RESID  WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'sys'  AND\tAM_ManagedResourceType.RESOURCETYPE  NOT IN('Node' ,'snmp-node') AND  AM_SLA_RESID_MAPPER.SLA_ID  is null AND " + mancondn + "  ORDER BY RESOURCENAME";
/*      */         }
/*      */         else
/*      */         {
/* 1685 */           query2 = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE LEFT OUTER JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID  = AM_SLA_RESID_MAPPER.RESID WHERE AM_ManagedResourceType.RESOURCEGROUP  = 'sys' AND AM_ManagedResourceType.RESOURCETYPE  NOT IN ( 'Node' ,'snmp-node') AND AM_SLA_RESID_MAPPER.SLA_ID  is null ORDER BY RESOURCENAME";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1691 */       FormatUtil.printQueryChange("BussinessAction.java", oldquery2, query2);
/* 1692 */       ArrayList a2 = getMonitorGroups(query2, false);
/*      */       
/* 1694 */       rf.setSystemAdd(a2);
/*      */     }
/* 1696 */     request.setAttribute("list", ht);
/* 1697 */     request.setAttribute("slaid", arr);
/* 1698 */     request.setAttribute("tabtoselect", "3");
/* 1699 */     if (reqForAdminLayout.equals("true"))
/*      */     {
/* 1701 */       return new ActionForward("/showTile.do?TileName=Tile.SLA.Admin");
/*      */     }
/*      */     
/* 1704 */     if (defaultview.equals("true"))
/*      */     {
/*      */ 
/* 1707 */       return new ActionForward("/showTile.do?TileName=Tile.SLA.Conf");
/*      */     }
/*      */     
/* 1710 */     return new ActionForward("/showTile.do?TileName=Tile.SLA.Manager");
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
/*      */   public ActionForward removeSla(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1724 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1725 */     ReportForm rf = (ReportForm)form;
/*      */     
/* 1727 */     ActionMessages messages = new ActionMessages();
/* 1728 */     ActionErrors errors = new ActionErrors();
/* 1729 */     String value = request.getParameter("value");
/* 1730 */     String[] slaid = request.getParameterValues("slaids");
/* 1731 */     String sname = request.getParameter("sname");
/*      */     
/*      */     try
/*      */     {
/* 1735 */       SLAUtil.removeSLA(slaid, true);
/* 1736 */       request.setAttribute("tabtoselect", "3");
/*      */ 
/*      */     }
/*      */     catch (Exception eee)
/*      */     {
/*      */ 
/* 1742 */       System.out.println("exception in buisnessaction remove sla method" + eee);
/* 1743 */       eee.printStackTrace();
/* 1744 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", eee.toString()));
/*      */     }
/*      */     
/*      */ 
/* 1748 */     generateSLA(mapping, form, request, response);
/*      */     
/* 1750 */     if (EnterpriseUtil.isIt360MSPAdminServer)
/*      */     {
/* 1752 */       AMLog.debug("[SLA removeSla] Going to FWD:");
/* 1753 */       return new ActionForward("/showBussiness.do?method=generateApplicationAvailablity", true);
/*      */     }
/* 1755 */     if ((request.getParameter("reqForAdminLayout") != null) && (request.getParameter("reqForAdminLayout").equals("true")))
/*      */     {
/* 1757 */       return new ActionForward("/showTile.do?TileName=Tile.SLA.Admin");
/*      */     }
/*      */     
/* 1760 */     return new ActionForward("/showTile.do?TileName=Tile.SLA.Manager");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addSlo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String slaid, String sname)
/*      */     throws Exception
/*      */   {
/* 1771 */     AMConnectionPool cp1 = AMConnectionPool.getInstance();
/* 1772 */     ReportForm rf = (ReportForm)form;
/* 1773 */     String param = request.getParameter("sla");
/* 1774 */     ActionMessages messages = new ActionMessages();
/* 1775 */     ActionErrors errors = new ActionErrors();
/*      */     
/* 1777 */     String chkapp = rf.getSlaapplication();
/*      */     
/* 1779 */     String chksys = rf.getSlasystem();
/* 1780 */     String chktick = rf.getSlaticket();
/* 1781 */     String appop = rf.getAppoperator();
/* 1782 */     String sysop = rf.getSysoperator();
/* 1783 */     String ticop = rf.getTicketoperator();
/* 1784 */     String apval = rf.getAppvalue();
/* 1785 */     String sysval = rf.getSysvalue();
/* 1786 */     String tickval = rf.getTicketvalue();
/* 1787 */     String duration = "month";
/* 1788 */     if ((chkapp != null) && (chkapp.equalsIgnoreCase("app")))
/*      */     {
/* 1790 */       String[] mgid = rf.getSlaCombo1();
/*      */       
/* 1792 */       for (int i = 0; i < mgid.length; i++)
/*      */       {
/* 1794 */         String resid = mgid[i];
/*      */         
/* 1796 */         String query1 = "INSERT INTO AM_SLA_RESID_MAPPER VALUES( '" + slaid + "' , '" + resid + "')";
/* 1797 */         AMConnectionPool.executeUpdateStmt(query1);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1804 */       String[] typeid = rf.getSystemCombo2();
/* 1805 */       for (int t = 0; t < typeid.length; t++)
/*      */       {
/*      */ 
/* 1808 */         String reid = typeid[t];
/*      */         
/* 1810 */         String querysys = "INSERT INTO AM_SLA_RESID_MAPPER VALUES( '" + slaid + "' , '" + reid + "')";
/*      */         
/* 1812 */         AMConnectionPool.executeUpdateStmt(querysys);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1819 */     Connection con = AMConnectionPool.getConnection();
/* 1820 */     Statement stmt = con.createStatement();
/*      */     
/*      */     try
/*      */     {
/* 1824 */       if ((chkapp != null) && (chkapp.equalsIgnoreCase("app")))
/*      */       {
/* 1826 */         int tableid = getNextTableId("AM_SLO", "ID");
/* 1827 */         String query2 = "INSERT INTO AM_SLO(ID,NAME,SLO_TYPE_ID,SLA_ID) VALUES (" + tableid + ",'application availablity',1,'" + slaid + "')";
/* 1828 */         stmt.executeUpdate(query2);
/*      */         
/* 1830 */         String query3 = "INSERT INTO AM_SLO_APPLICATIONAVAILABLITY(SLO_ID,OPERATOR,PERCENTAVAIL) VALUES(" + tableid + ",'" + appop + "' ,'" + apval + "')";
/* 1831 */         stmt.executeUpdate(query3);
/*      */       }
/*      */       
/* 1834 */       if ((chkapp != null) && (chkapp.equalsIgnoreCase("sys")))
/*      */       {
/* 1836 */         int tableid = getNextTableId("AM_SLO", "ID");
/* 1837 */         String query4 = "INSERT INTO AM_SLO(ID,NAME,SLO_TYPE_ID,SLA_ID) VALUES (" + tableid + ",'system availablity',2,'" + slaid + "')";
/* 1838 */         stmt.executeUpdate(query4);
/*      */         
/* 1840 */         String query5 = "INSERT INTO AM_SLO_SYSTEMAVAILABLITY(SLO_ID,OPERATOR,PERCENTAVAIL) VALUES(" + tableid + ",'" + appop + "' ," + apval + ")";
/* 1841 */         stmt.executeUpdate(query5);
/*      */       }
/*      */       
/* 1844 */       if ((chktick != null) && (chktick.equalsIgnoreCase("tick")))
/*      */       {
/* 1846 */         int tableid = getNextTableId("AM_SLO", "ID");
/* 1847 */         String query6 = "INSERT INTO AM_SLO(ID,NAME,SLO_TYPE_ID,SLA_ID) VALUES (" + tableid + ",'trouble ticket volume',3,'" + slaid + "')";
/* 1848 */         stmt.executeUpdate(query6);
/*      */         
/* 1850 */         String query7 = "INSERT INTO AM_SLO_TROUBLETICKETVOLUME (SLO_ID,OPERATOR,TICKETVALUE,DURATION) VALUES(" + tableid + ",'" + ticop + "' ," + tickval + " ,'" + duration + "')";
/* 1851 */         System.out.println("SLA Query:" + query7);
/* 1852 */         stmt.executeUpdate(query7);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception exe)
/*      */     {
/*      */ 
/* 1860 */       exe.printStackTrace();
/*      */     }
/*      */     
/* 1863 */     return new ActionForward("/showTile.do?TileName=Tile.SLA.Manager");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editSla(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1873 */     ReportForm rf = (ReportForm)form;
/*      */     
/* 1875 */     ActionMessages messages = new ActionMessages();
/* 1876 */     ActionErrors errors = new ActionErrors();
/* 1877 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1878 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/* 1879 */     String reqForAdminLayout = (request.getParameter("reqForAdminLayout") != null) && (request.getParameter("reqForAdminLayout").equalsIgnoreCase("true")) ? request.getParameter("reqForAdminLayout") : "";
/*      */     
/*      */     try
/*      */     {
/* 1883 */       String slaid = request.getParameter("slaid");
/* 1884 */       request.setAttribute("slaid", slaid);
/* 1885 */       generateSLA(mapping, form, request, response);
/* 1886 */       Hashtable htl = (Hashtable)request.getAttribute("list");
/* 1887 */       ResultSet rs = null;
/* 1888 */       Properties pro = (Properties)htl.get(slaid);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1893 */       String sname = pro.getProperty("sname");
/* 1894 */       String sdesc = pro.getProperty("sdesc");
/* 1895 */       String disable = pro.getProperty("option");
/* 1896 */       String appop = pro.getProperty("appop");
/* 1897 */       String sysop = pro.getProperty("sysop");
/* 1898 */       String tickop = pro.getProperty("tickop");
/* 1899 */       String tickval = pro.getProperty("tickval");
/* 1900 */       String appval = pro.getProperty("appval");
/* 1901 */       String sysval = pro.getProperty("sysval");
/*      */       
/* 1903 */       String type = pro.getProperty("type");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1908 */       String slaap = rf.getSlaapplication();
/*      */       
/* 1910 */       if (type.equalsIgnoreCase("Business Application"))
/*      */       {
/*      */ 
/*      */ 
/* 1914 */         rf.setSlaapplication("app");
/* 1915 */         rf.setAppoperator(appop);
/* 1916 */         rf.setAppvalue(appval);
/* 1917 */         rf.setAvailablity("avail");
/*      */       }
/*      */       
/*      */ 
/* 1921 */       if (type.equalsIgnoreCase("Server"))
/*      */       {
/*      */ 
/* 1924 */         rf.setSlaapplication("sys");
/* 1925 */         rf.setAppoperator(appop);
/* 1926 */         rf.setAppvalue(appval);
/* 1927 */         rf.setAvailablity("avail");
/*      */       }
/*      */       
/*      */ 
/* 1931 */       if ((!tickval.equals("set")) || (!tickval.equals("applicable")))
/*      */       {
/*      */ 
/* 1934 */         rf.setSlaticket("tick");
/* 1935 */         rf.setTicketoperator(tickop);
/* 1936 */         rf.setTicketvalue(tickval);
/*      */       }
/*      */       
/*      */ 
/* 1940 */       rf.setSlaname(sname);
/* 1941 */       rf.setSladesc(sdesc);
/*      */       
/* 1943 */       String getact = "SELECT FROMADDRESS,TOADDRESS,SUBJECT,MESSAGE FROM AM_EMAILACTION,AM_ACTIONPROFILE,AM_SLA_ACTION_MAPPER WHERE AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.ID=AM_SLA_ACTION_MAPPER.ACTION_ID AND AM_SLA_ACTION_MAPPER.SLA_ID=" + slaid;
/*      */       
/* 1945 */       rs = AMConnectionPool.executeQueryStmt(getact);
/* 1946 */       String from = "";
/* 1947 */       String to = "";
/* 1948 */       String sub = "";
/* 1949 */       String mess = "";
/* 1950 */       boolean ismail = false;
/* 1951 */       if (rs.next())
/*      */       {
/* 1953 */         ismail = true;
/* 1954 */         from = rs.getString("FROMADDRESS");
/* 1955 */         to = rs.getString("TOADDRESS");
/* 1956 */         sub = rs.getString("SUBJECT");
/* 1957 */         mess = rs.getString("MESSAGE");
/*      */       }
/* 1959 */       if (ismail)
/*      */       {
/* 1961 */         rf.setFrom(from);
/* 1962 */         rf.setTo(to);
/* 1963 */         rf.setSubject(sub);
/* 1964 */         rf.setMessage(mess);
/* 1965 */         rf.setMailcheck("mail");
/* 1966 */         rf.setDisablemail(disable);
/*      */       }
/* 1968 */       String queryforba = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM AM_ManagedObject,AM_SLA_RESID_MAPPER WHERE AM_ManagedObject.TYPE='HAI' AND AM_SLA_RESID_MAPPER.RESID=AM_ManagedObject.RESOURCEID AND AM_SLA_RESID_MAPPER.SLA_ID=" + slaid;
/* 1969 */       ArrayList rows = getMonitorGroups(queryforba, true);
/* 1970 */       rows = updateSGNameswithTree(rows);
/* 1971 */       rf.setPresent(rows);
/* 1972 */       request.setAttribute("moPresent", rows);
/*      */       
/* 1974 */       String queryforresource = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME FROM AM_ManagedObject JOIN AM_SLA_RESID_MAPPER ON AM_ManagedObject.RESOURCEID = AM_SLA_RESID_MAPPER.RESID AND AM_SLA_RESID_MAPPER.SLA_ID=" + slaid;
/*      */       
/* 1976 */       ArrayList AL1 = getMonitorGroups(queryforresource, false);
/* 1977 */       rf.setSystemPresent(AL1);
/* 1978 */       rs.close();
/*      */ 
/*      */     }
/*      */     catch (Exception exec)
/*      */     {
/* 1983 */       exec.printStackTrace();
/*      */     }
/* 1985 */     request.setAttribute("edit", "true");
/* 1986 */     return new ActionForward("/showBussiness.do?method=generateSLA&sla=true&defaultView=" + defaultview + "&reqForAdminLayout=" + reqForAdminLayout);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateSLA(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1995 */     ReportForm rf = (ReportForm)form;
/*      */     
/* 1997 */     ActionMessages messages = new ActionMessages();
/* 1998 */     ActionErrors errors = new ActionErrors();
/* 1999 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2000 */     String defaultview = (request.getParameter("defaultView") != null) && (request.getParameter("defaultView").equalsIgnoreCase("true")) ? request.getParameter("defaultView") : "";
/* 2001 */     String reqForAdminLayout = (request.getParameter("reqForAdminLayout") != null) && (request.getParameter("reqForAdminLayout").equalsIgnoreCase("true")) ? request.getParameter("reqForAdminLayout") : "";
/*      */     
/*      */     try
/*      */     {
/* 2005 */       String slaid = request.getParameter("slaid");
/* 2006 */       String sname = rf.getSlaname();
/* 2007 */       int i = 1;
/* 2008 */       String actname = "SLA_" + sname + "Action";
/*      */       
/* 2010 */       String sdesc = rf.getSladesc();
/* 2011 */       String disable = rf.getDisablemail();
/*      */       
/*      */ 
/* 2014 */       request.setAttribute("defaultView", defaultview);
/* 2015 */       String dismail = "";
/* 2016 */       if (disable != null)
/*      */       {
/* 2018 */         dismail = disable;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         dismail = "false";
/*      */       }
/* 2025 */       String from = rf.getFrom();
/* 2026 */       String to = rf.getTo();
/* 2027 */       String subject = rf.getSubject();
/* 2028 */       String message = rf.getMessage();
/* 2029 */       sdesc = FormatUtil.findAndReplaceAll(sdesc, "'", "\\'");
/* 2030 */       String query = "UPDATE AM_SLA SET NAME= '" + sname + " ' , DESCRIPTION= '" + sdesc + "' , MAILOPTION= '" + dismail + "' WHERE ID= " + slaid;
/* 2031 */       AMConnectionPool.executeUpdateStmt(query);
/* 2032 */       String mailcheck = rf.getMailcheck();
/*      */       
/* 2034 */       boolean isupdate = false;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2039 */       if ((mailcheck != null) && (mailcheck.equalsIgnoreCase("mail")))
/*      */       {
/* 2041 */         String getid = "SELECT ACTION_ID FROM AM_SLA_ACTION_MAPPER where SLA_ID= " + slaid;
/* 2042 */         ResultSet rs = AMConnectionPool.executeQueryStmt(getid);
/* 2043 */         String actid = "";
/* 2044 */         if (rs.next())
/*      */         {
/* 2046 */           actid = rs.getString("ACTION_ID");
/* 2047 */           isupdate = true;
/*      */         }
/*      */         
/*      */ 
/* 2051 */         if (isupdate)
/*      */         {
/*      */ 
/* 2054 */           String act2 = "update  AM_EMAILACTION set  FROMADDRESS= '" + from + "', TOADDRESS ='" + to + "', SUBJECT= '" + subject + "' , MESSAGE= '" + message + "' where ID= '" + actid + "'";
/*      */           
/* 2056 */           AMConnectionPool.executeUpdateStmt(act2);
/*      */         }
/*      */         else
/*      */         {
/* 2060 */           String act1 = " DELETE FROM AM_ACTIONPROFILE WHERE NAME= '" + actname + "' and TYPE='25'";
/* 2061 */           AMConnectionPool.executeUpdateStmt(act1);
/*      */           
/* 2063 */           insertAction(slaid, from, to, subject, message, actname);
/*      */         }
/* 2065 */         rs.close();
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/* 2073 */         String del = "DELETE FROM AM_SLA_ACTION_MAPPER WHERE SLA_ID = ' " + slaid + " '";
/* 2074 */         AMConnectionPool.executeUpdateStmt(del);
/*      */       }
/*      */       
/* 2077 */       SLAUtil.deleteSlo(slaid);
/*      */       
/* 2079 */       addSlo(mapping, form, request, response, slaid, sname);
/* 2080 */       generateSLA(mapping, form, request, response);
/*      */     }
/*      */     catch (Exception exec)
/*      */     {
/* 2084 */       exec.printStackTrace();
/*      */     }
/* 2086 */     if (reqForAdminLayout.equals("true"))
/*      */     {
/* 2088 */       return new ActionForward("/showTile.do?TileName=Tile.SLA.Admin");
/*      */     }
/*      */     
/* 2091 */     if (defaultview.equals("true"))
/*      */     {
/*      */ 
/* 2094 */       return new ActionForward("/showTile.do?TileName=Tile.SLA.Conf");
/*      */     }
/*      */     
/* 2097 */     return new ActionForward("/showTile.do?TileName=Tile.SLA.Manager");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward sendTestMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2108 */     ReportForm rf = (ReportForm)form;
/*      */     
/* 2110 */     ActionMessages messages = new ActionMessages();
/* 2111 */     ActionErrors errors = new ActionErrors();
/* 2112 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 2117 */       String host = InetAddress.getLocalHost().getHostName();
/* 2118 */       String port = System.getProperty("webserver.port");
/*      */       
/* 2120 */       String htmlmess = ComparingSla.getHTMLMailTemplate();
/*      */       
/* 2122 */       String from = request.getParameter("from");
/*      */       
/* 2124 */       String to = request.getParameter("to");
/*      */       
/* 2126 */       Hashtable additionalInfo = new Hashtable();
/* 2127 */       String fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.heading.text"));
/* 2128 */       String slaName = FormatUtil.findReplace(fline, "SLA Name", FormatUtil.getString("SLA Name"));
/* 2129 */       String resource = FormatUtil.findReplace(slaName, "~source~", FormatUtil.getString("am.webclient.camscreen.attributegraphs.resourcename.text"));
/* 2130 */       String slaAvailability = FormatUtil.findReplace(resource, "Availablity", FormatUtil.getString("Availablity"));
/* 2131 */       String attr = FormatUtil.findReplace(slaAvailability, "~attribute~", FormatUtil.getString("am.webclient.managermail.availability") + " - 99.9%");
/* 2132 */       String troubleTicketVolume = FormatUtil.findReplace(attr, "Trouble Ticket Volume", FormatUtil.getString("Trouble Ticket Volume"));
/* 2133 */       String messa = FormatUtil.findReplace(troubleTicketVolume, "~message~", FormatUtil.getString("am.webclient.managermail.troubleticket") + " - 10");
/* 2134 */       messa = FormatUtil.findReplace(messa, "~heading~", FormatUtil.getString("am.webclient.managermail.bsm.servermessage.text"));
/* 2135 */       String root = FormatUtil.findReplace(messa, "~rootcause~", FormatUtil.getString("am.webclient.managermail.bsm.rcmessage.text"));
/* 2136 */       String user = FormatUtil.findReplace(root, "~message~", FormatUtil.getString("am.webclient.managermail.bsm.alertmessage.text"));
/* 2137 */       user = FormatUtil.findReplace(user, "~userinfo~", " ");
/* 2138 */       String addinfo = FormatUtil.findReplace(user, "~addinfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 2139 */       String repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 2140 */       String hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*      */       
/*      */ 
/*      */ 
/* 2144 */       String nameFilled = null;
/* 2145 */       if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */       {
/* 2147 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*      */       }
/*      */       else
/*      */       {
/* 2151 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*      */       }
/*      */       
/* 2154 */       String portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/*      */       
/*      */ 
/* 2157 */       SmtpMailer mailer = new SmtpMailer(from, to, "", FormatUtil.getString("am.webclient.managermail.bsm.testmailmessage.text"));
/*      */       
/* 2159 */       String returnVal = null;
/* 2160 */       if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */       {
/* 2162 */         returnVal = mailer.sendMessage(FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") }), null, portFilled);
/*      */       }
/*      */       else
/*      */       {
/* 2166 */         returnVal = mailer.sendMessage(FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") }), null, portFilled);
/*      */       }
/*      */       
/* 2169 */       request.setAttribute("result", returnVal);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2174 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 2178 */     return new ActionForward("/jsp/Popup_sendmail.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   class ComparatorImpl
/*      */     implements Comparator
/*      */   {
/*      */     public ComparatorImpl() {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2192 */     public boolean isascending = true;
/*      */     
/*      */     public ComparatorImpl(boolean yes) {
/* 2195 */       this.isascending = yes;
/*      */     }
/*      */     
/*      */     public int compare(Object o1, Object o2)
/*      */     {
/* 2200 */       Properties prop1 = (Properties)o1;
/* 2201 */       Properties prop2 = (Properties)o2;
/* 2202 */       System.out.println("prop1:" + prop1);
/* 2203 */       System.out.println("prop2:" + prop2);
/* 2204 */       String available1 = prop1.getProperty("available");
/* 2205 */       String available2 = prop2.getProperty("available");
/* 2206 */       float val1 = 0.0F;
/* 2207 */       float val2 = 0.0F;
/*      */       try {
/* 2209 */         if (!"NA".equalsIgnoreCase(available1)) {
/* 2210 */           val1 = Float.parseFloat(available1);
/*      */         }
/* 2212 */         if (!"NA".equalsIgnoreCase(available2)) {
/* 2213 */           val2 = Float.parseFloat(available2);
/*      */         }
/*      */       } catch (NumberFormatException e) {
/* 2216 */         e.printStackTrace();
/*      */       }
/*      */       
/* 2219 */       if (val1 == val2)
/*      */       {
/* 2221 */         return 0;
/*      */       }
/* 2223 */       if (val1 > val2)
/*      */       {
/* 2225 */         if (this.isascending)
/*      */         {
/* 2227 */           return 1;
/*      */         }
/*      */         
/*      */ 
/* 2231 */         return -1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2236 */       if (this.isascending)
/*      */       {
/* 2238 */         return -1;
/*      */       }
/*      */       
/*      */ 
/* 2242 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object obj)
/*      */     {
/* 2250 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   class ComparatorArrayImpl
/*      */     implements Comparator
/*      */   {
/*      */     public ComparatorArrayImpl() {}
/*      */     
/* 2260 */     public boolean isascending = true;
/*      */     
/*      */     public ComparatorArrayImpl(boolean yes) {
/* 2263 */       this.isascending = yes;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public int compare(Object o1, Object o2)
/*      */     {
/* 2270 */       ArrayList a1 = (ArrayList)o1;
/* 2271 */       Properties pr1 = (Properties)a1.get(6);
/* 2272 */       ArrayList a2 = (ArrayList)o2;
/* 2273 */       Properties pr2 = (Properties)a2.get(6);
/* 2274 */       String s1 = pr1.getProperty("available");
/* 2275 */       String s2 = pr2.getProperty("available");
/* 2276 */       float val1 = 0.0F;
/* 2277 */       float val2 = 0.0F;
/*      */       
/* 2279 */       if (!s1.equalsIgnoreCase("NA"))
/*      */       {
/* 2281 */         val1 = Float.parseFloat(s1);
/*      */       }
/* 2283 */       if (!s2.equalsIgnoreCase("NA"))
/*      */       {
/* 2285 */         val2 = Float.parseFloat(s2);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2291 */       if (val1 == val2)
/*      */       {
/* 2293 */         return 0;
/*      */       }
/* 2295 */       if (val1 > val2)
/*      */       {
/* 2297 */         if (this.isascending)
/*      */         {
/* 2299 */           return 1;
/*      */         }
/*      */         
/*      */ 
/* 2303 */         return -1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2308 */       if (this.isascending)
/*      */       {
/* 2310 */         return -1;
/*      */       }
/*      */       
/*      */ 
/* 2314 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object obj)
/*      */     {
/* 2322 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   public static int getNextTableId(String tablename, String columnName)
/*      */   {
/* 2328 */     int tableid = -1;
/* 2329 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2332 */       String qry = "select max(" + columnName + ")+1 as tableid from " + tablename;
/* 2333 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 2334 */       if (rs.next())
/*      */       {
/* 2336 */         String val = rs.getString(1);
/* 2337 */         if ((val != null) && (!val.equals("NULL")))
/*      */         {
/* 2339 */           tableid = Integer.parseInt(val);
/*      */         }
/*      */         else
/*      */         {
/* 2343 */           tableid = 1;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2349 */       exc.printStackTrace();
/*      */     }
/* 2351 */     return tableid;
/*      */   }
/*      */   
/*      */   public ArrayList getMonitorGroups(String query, boolean isMG) {
/* 2355 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2356 */     ArrayList row = new ArrayList();
/* 2357 */     ResultSet rs1 = null;
/*      */     try
/*      */     {
/* 2360 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 2361 */       while (rs1.next())
/*      */       {
/* 2363 */         String displayname = rs1.getString("DISPLAYNAME");
/* 2364 */         String resourceid = rs1.getString("RESOURCEID");
/* 2365 */         Properties p = new Properties();
/* 2366 */         if ((!isMG) && 
/* 2367 */           (EnterpriseUtil.isAdminServer()) && (Integer.parseInt(resourceid) > EnterpriseUtil.RANGE))
/*      */         {
/* 2369 */           displayname = displayname + "_" + CommDBUtil.getManagedServerNameWithPort(resourceid);
/*      */         }
/*      */         
/* 2372 */         p.setProperty("label", displayname);
/* 2373 */         p.setProperty("value", resourceid);
/* 2374 */         row.add(p);
/*      */       }
/*      */     }
/*      */     catch (Exception exec)
/*      */     {
/* 2379 */       exec.printStackTrace();
/*      */     }
/*      */     finally {
/* 2382 */       AMConnectionPool.closeStatement(rs1);
/*      */     }
/* 2384 */     return row;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList updateSGNameswithTree(ArrayList rows)
/*      */   {
/* 2390 */     ArrayList retRows = new ArrayList();
/* 2391 */     Hashtable displaynameResidMap = new Hashtable();
/* 2392 */     ArrayList<String> templist = new ArrayList();
/* 2393 */     for (int n = 0; n < rows.size(); n++)
/*      */     {
/* 2395 */       Properties eachRow = (Properties)rows.get(n);
/* 2396 */       String displayname = (String)eachRow.get("label");
/* 2397 */       String resourceid = (String)eachRow.get("value");
/* 2398 */       String updatedDisplayname = "";
/* 2399 */       String sgTree = getMGtreeforSG(resourceid, "");
/*      */       
/* 2401 */       if ("".equals(sgTree))
/*      */       {
/* 2403 */         updatedDisplayname = displayname;
/*      */       }
/*      */       else
/*      */       {
/* 2407 */         updatedDisplayname = sgTree + "->" + displayname;
/*      */       }
/*      */       
/* 2410 */       if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(resourceid) > EnterpriseUtil.RANGE))
/*      */       {
/* 2412 */         updatedDisplayname = updatedDisplayname + "_" + CommDBUtil.getManagedServerNameWithPort(resourceid);
/*      */       }
/*      */       
/* 2415 */       Properties p = new Properties();
/* 2416 */       p.setProperty("label", updatedDisplayname);
/* 2417 */       p.setProperty("value", resourceid);
/* 2418 */       retRows.add(p);
/*      */     }
/* 2420 */     Collections.sort(retRows, new NameComparator(null));
/* 2421 */     AMLog.debug("DBUtil : updateSGNameswithTree : retRows ========>" + retRows);
/* 2422 */     return retRows;
/*      */   }
/*      */   
/*      */   public String getMGtreeforSG(String resourceid, String tree)
/*      */   {
/*      */     try
/*      */     {
/* 2429 */       String query = "select AM_PARENTCHILDMAPPER.PARENTID,AM_ManagedObject.DISPLAYNAME  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.CHILDID=" + resourceid + " and AM_ManagedObject.TYPE='HAI'";
/* 2430 */       ArrayList templist = DBUtil.getRows(query);
/* 2431 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 2433 */         ArrayList singlerow = (ArrayList)templist.get(i);
/* 2434 */         String parResId = (String)singlerow.get(0);
/* 2435 */         String displayname = (String)singlerow.get(1);
/* 2436 */         if ("".equals(tree))
/*      */         {
/* 2438 */           tree = getMGtreeforSG(parResId, displayname);
/*      */         }
/*      */         else
/*      */         {
/* 2442 */           tree = getMGtreeforSG(parResId, displayname + "->" + tree);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2448 */       ex.printStackTrace();
/*      */     }
/* 2450 */     return tree;
/*      */   }
/*      */   
/*      */   private static class NameComparator implements Comparator {
/*      */     public int compare(Object o1, Object o2) {
/* 2455 */       Properties ohm1 = (Properties)o1;
/* 2456 */       Properties ohm2 = (Properties)o2;
/* 2457 */       String mg1 = ohm1.getProperty("label");
/* 2458 */       String mg2 = ohm2.getProperty("label");
/* 2459 */       return mg1.compareTo(mg2);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\BussinessAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */