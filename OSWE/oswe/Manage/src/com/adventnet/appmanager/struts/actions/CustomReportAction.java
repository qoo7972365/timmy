/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportCustomAttributeUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomReportAction
/*     */   extends DispatchAction
/*     */ {
/*  81 */   private static Log log = LogFactory.getLog("WebClient");
/*     */   
/*  83 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*  85 */   private static List attributeList = ReportUtil.getAttributeList();
/*     */   
/*     */ 
/*     */   public ActionForward showCustomReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  91 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  92 */     AMActionForm am = (AMActionForm)form;
/*  93 */     ActionMessages messages = new ActionMessages();
/*  94 */     ActionErrors errors = new ActionErrors();
/*  95 */     ResultSet rs = null;
/*  96 */     String error = null;
/*  97 */     if ((Constants.getUserType() != null) && (Constants.getUserType().equals("F")))
/*     */     {
/*  99 */       request.setAttribute("tabtoselect", "6");
/* 100 */       request.setAttribute("helpkey", "showCustomReports");
/* 101 */       return new ActionForward("/jsp/helpmessages_container.jsp");
/*     */     }
/* 103 */     if (Constants.isPrivilegedUser(request)) {
/* 104 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 109 */       ArrayList bh = ReportUtil.populateResourceTypes();
/*     */       
/* 111 */       am.setResourceTypeForReports(bh);
/* 112 */       ArrayList lst = am.getResourceTypeNamesForReports();
/* 113 */       request.setAttribute("resourcetypenameforreports", lst);
/*     */     }
/*     */     catch (Exception ex) {
/* 116 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 120 */     ResultSet set = null;
/* 121 */     String query = "select  AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,AM_EMAILACTION.TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/*     */     
/* 123 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/* 125 */       query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 131 */       set = AMConnectionPool.executeQueryStmt(query);
/* 132 */       AMLog.debug("Alert Escalation : " + query);
/* 133 */       ArrayList rows = new ArrayList();
/* 134 */       while (set.next())
/*     */       {
/* 136 */         String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 137 */         Properties dataProps = new Properties();
/* 138 */         dataProps.setProperty("label", labelvalue);
/* 139 */         dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 140 */         rows.add(dataProps);
/*     */       }
/* 142 */       set.close();
/* 143 */       am.setApplications(rows);
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 147 */       AMLog.fatal("Alert Escalation :  Exception ", exp);
/* 148 */       exp.printStackTrace();
/* 149 */       throw new Exception(exp);
/*     */     }
/*     */     try {
/* 152 */       set = AMConnectionPool.executeQueryStmt("select * from AM_GLOBALCONFIG where NAME in ('SummaryMailer','SummaryMailer.Time','SummaryMailer.Emailid') ");
/* 153 */       boolean hasnostatus = true;
/* 154 */       while (set.next())
/*     */       {
/* 156 */         String key = set.getString("NAME");
/* 157 */         String value = set.getString("VALUE");
/* 158 */         if (key.equals("SummaryMailer"))
/*     */         {
/* 160 */           am.setStatus(value);
/* 161 */           hasnostatus = false;
/*     */         }
/* 163 */         if (key.equals("SummaryMailer.Time"))
/*     */         {
/* 165 */           String[] timearry = value.split(":");
/* 166 */           am.setDailyhour(timearry[0]);
/* 167 */           am.setDailyminute(timearry[1]);
/*     */         }
/*     */         else
/*     */         {
/* 171 */           am.setDailyhour("08");
/* 172 */           am.setDailyminute("00");
/*     */         }
/* 174 */         if (key.equals("SummaryMailer.Emailid"))
/*     */         {
/* 176 */           am.setSendmail(value);
/*     */         }
/*     */       }
/*     */       
/* 180 */       set.close();
/* 181 */       if (hasnostatus)
/*     */       {
/* 183 */         am.setStatus("enable");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 189 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/* 193 */     String selectedType = null;
/* 194 */     String toappend = "";
/* 195 */     if ((request.getParameter("selectedType") != null) && (!request.getParameter("selectedType").equals("")))
/*     */     {
/* 197 */       selectedType = request.getParameter("selectedType");
/* 198 */       toappend = "?selectedType=" + selectedType;
/*     */     }
/*     */     
/* 201 */     if ((request.getParameter("toshow") != null) && (request.getParameter("toshow").equals("summarmailer")))
/*     */     {
/* 203 */       return new ActionForward("/jsp/EnableCustomReports.jsp?summarymailer=true");
/*     */     }
/*     */     
/*     */ 
/* 207 */     return new ActionForward("/jsp/EnableCustomReports.jsp" + toappend);
/*     */   }
/*     */   
/*     */   public ActionForward sendAttributeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 213 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 214 */     AMActionForm am = (AMActionForm)form;
/* 215 */     ActionMessages messages = new ActionMessages();
/* 216 */     ActionErrors errors = new ActionErrors();
/* 217 */     ResultSet rs = null;
/* 218 */     String error = null;
/*     */     try {
/* 220 */       String restype = request.getParameter("resourcetype");
/* 221 */       List attList = null;
/* 222 */       if ("windows".equalsIgnoreCase(restype)) {
/* 223 */         attList = ReportUtil.getAttributesForWindows();
/*     */       }
/* 225 */       else if ("vmcluster".equalsIgnoreCase(restype))
/*     */       {
/* 227 */         attList = ReportUtil.getAttributesForVMClusters();
/*     */       }
/* 229 */       else if ("vmrpool".equalsIgnoreCase(restype))
/*     */       {
/* 231 */         attList = ReportUtil.getAttributesForVMRPools();
/*     */       }
/*     */       else {
/* 234 */         attList = ReportUtil.getAttributesForResourcetype(restype);
/*     */       }
/*     */       
/* 237 */       ArrayList reportEnabledList = ReportUtil.getReportEnabledAttributes();
/* 238 */       Hashtable camAttDetails = new Hashtable();
/* 239 */       if ("SNMP".equalsIgnoreCase(restype))
/*     */       {
/* 241 */         camAttDetails = ReportUtil.getCAMAttDetails();
/*     */       }
/* 243 */       String text = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'><table border='0' cellpadding='5' cellspacing='0' width='100%'> ";
/*     */       
/* 245 */       for (int j = 0; j < attList.size(); j++) {
/* 246 */         String res = attList.get(j).toString();
/* 247 */         String[] temp = res.split("#");
/* 248 */         String attributeid = temp[0];
/*     */         
/* 250 */         String displayname = temp[1];
/* 251 */         if ((camAttDetails != null) && (camAttDetails.get(attributeid) != null))
/*     */         {
/* 253 */           Hashtable camAttHash = (Hashtable)camAttDetails.get(attributeid);
/* 254 */           displayname = FormatUtil.getString((String)camAttHash.get("ATTRIBUTENAME")) + " ( " + camAttHash.get("MONITORNAME") + " )"; }
/*     */         String[] tempAttrId;
/*     */         String[] tempAttrId;
/* 257 */         if (attributeid.indexOf(",") != -1) {
/* 258 */           tempAttrId = attributeid.split(",");
/*     */         } else {
/* 260 */           tempAttrId = new String[] { attributeid };
/*     */         }
/*     */         
/* 263 */         if (attributeList.contains(tempAttrId[0])) {
/* 264 */           text = text + "<tr> <td><input type='checkbox' name='attributetypes'  value='" + attributeid + "'  disabled checked/><span class='bodytext' title='" + FormatUtil.getString(displayname) + "'>" + FormatUtil.getTrimmedText(FormatUtil.getString(displayname), 80) + "</span></tr></td>";
/* 265 */         } else if ((reportEnabledList.contains(tempAttrId[0])) || (ReportUtil.getReportEnabledAttributesForWindows(tempAttrId[0]))) {
/* 266 */           text = text + "<tr> <td><input type='checkbox' name='attributetypes'  value='" + attributeid + "'   checked/><span class='bodytext' title='" + FormatUtil.getString(displayname) + "'>" + FormatUtil.getTrimmedText(FormatUtil.getString(displayname), 80) + "</span></tr></td>";
/*     */         } else {
/* 268 */           text = text + "<tr> <td><input type='checkbox' name='attributetypes'  value='" + attributeid + "' /><span class='bodytext' title='" + FormatUtil.getString(displayname) + "'>" + FormatUtil.getTrimmedText(FormatUtil.getString(displayname), 80) + "</span></tr></td>";
/*     */         }
/*     */       }
/*     */       
/* 272 */       text = text + "</table>";
/* 273 */       response.setContentType("text/html; charset=UTF-8");
/* 274 */       PrintWriter pw = response.getWriter();
/*     */       
/* 276 */       pw.print(text);
/*     */     } catch (Exception ex) {
/* 278 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 281 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward enableCustomReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 287 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 288 */     AMActionForm am = (AMActionForm)form;
/* 289 */     ActionMessages messages = new ActionMessages();
/* 290 */     ActionErrors errors = new ActionErrors();
/* 291 */     ResultSet rs = null;
/* 292 */     String restype = am.getResTypeValue();
/* 293 */     String error = null;
/* 294 */     String resID = request.getParameter("resourceid");
/* 295 */     String popup = request.getParameter("popup");
/* 296 */     popup = popup == null ? "" : popup.toLowerCase();
/*     */     try {
/* 298 */       List newAttributeList = new ArrayList();
/* 299 */       List attList = null;
/* 300 */       if ("windows".equalsIgnoreCase(restype)) {
/* 301 */         attList = ReportUtil.getAttributesForWindows();
/*     */       }
/* 303 */       else if ("vmcluster".equalsIgnoreCase(restype))
/*     */       {
/* 305 */         attList = ReportUtil.getAttributesForVMClusters();
/*     */       }
/* 307 */       else if ("vmrpool".equalsIgnoreCase(restype))
/*     */       {
/* 309 */         attList = ReportUtil.getAttributesForVMRPools();
/*     */       }
/*     */       else {
/* 312 */         attList = ReportUtil.getAttributesForResourcetype(restype);
/*     */       }
/*     */       
/* 315 */       List onlyAttId = new ArrayList();
/* 316 */       for (int j = 0; j < attList.size(); j++) {
/* 317 */         String all = attList.get(j).toString();
/* 318 */         String[] temp = all.split("#");
/* 319 */         if (temp[0].indexOf(",") != -1)
/*     */         {
/* 321 */           StringTokenizer st = new StringTokenizer(temp[0], ",");
/* 322 */           while (st.hasMoreTokens()) {
/* 323 */             String tempAttId = st.nextToken();
/* 324 */             if (!attributeList.contains(tempAttId)) {
/* 325 */               onlyAttId.add(tempAttId);
/*     */             }
/*     */           }
/*     */         }
/* 329 */         else if (!attributeList.contains(temp[0])) {
/* 330 */           onlyAttId.add(temp[0]);
/*     */         }
/*     */       }
/*     */       
/* 334 */       if (request.getParameterValues("attributetypes") != null) {
/* 335 */         String[] attributearray = request.getParameterValues("attributetypes");
/* 336 */         for (int i = 0; i < attributearray.length; i++) {
/* 337 */           String currentAttId = attributearray[i];
/* 338 */           if (!attributeList.contains(currentAttId)) {
/* 339 */             if (currentAttId.indexOf(",") != -1) {
/* 340 */               StringTokenizer st = new StringTokenizer(currentAttId, ",");
/* 341 */               while (st.hasMoreTokens()) {
/* 342 */                 String singleAttributeId = st.nextToken();
/* 343 */                 if (onlyAttId.contains(singleAttributeId))
/* 344 */                   onlyAttId.remove(singleAttributeId);
/* 345 */                 enableReportInExtensionTable(singleAttributeId);
/*     */               }
/*     */             }
/*     */             else {
/* 349 */               if (onlyAttId.contains(currentAttId)) {
/* 350 */                 onlyAttId.remove(currentAttId);
/*     */               }
/* 352 */               enableReportInExtensionTable(currentAttId);
/* 353 */               if (("SNMP".equalsIgnoreCase(restype)) || ("Web Service".equalsIgnoreCase(restype)))
/*     */               {
/* 355 */                 ReportCustomAttributeUtil.addToArchiverConfig(currentAttId);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 362 */       disableReportInExtensionTable(onlyAttId);
/* 363 */       if (("SNMP".equalsIgnoreCase(restype)) || ("Web Service".equalsIgnoreCase(restype)))
/*     */       {
/* 365 */         ReportCustomAttributeUtil.removeFromArchiverConfig(onlyAttId);
/*     */       }
/*     */       
/* 368 */       request.setAttribute("message", FormatUtil.getString("am.webclient.customattribute.success.heading.text"));
/* 369 */       showCustomReports(mapping, form, request, response);
/*     */     }
/*     */     catch (Exception ex) {
/* 372 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 375 */     String toappend = "";
/* 376 */     if (request.getParameter("selectedType") != null)
/*     */     {
/* 378 */       toappend = "&selectedType=" + request.getParameter("selectedType");
/*     */     }
/* 380 */     ReportUtil.loadAllAttributeDetails();
/* 381 */     if (popup.equals("true")) {
/* 382 */       return new ActionForward("/jsp/EnableCustomReports.jsp?enable=true" + toappend);
/*     */     }
/* 384 */     if ((resID != null) && (!resID.equals("null")) && (!resID.equals(""))) {
/* 385 */       return new ActionForward("/showresource.do?resourceid=" + resID + "&method=showResourceForResourceID&reportMessage=1", true);
/*     */     }
/*     */     
/* 388 */     return new ActionForward("/jsp/EnableCustomReports.jsp?enable=true" + toappend);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward saveSummaryMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 396 */     ResultSet rs = null;
/* 397 */     Enumeration e = request.getParameterNames();
/* 398 */     while (e.hasMoreElements()) {
/* 399 */       String key = (String)e.nextElement();
/* 400 */       String[] values = request.getParameterValues(key);
/* 401 */       System.out.print("   " + key + " = ");
/* 402 */       for (int i = 0; i < values.length; i++) {
/* 403 */         System.out.print(values[i] + " ");
/*     */       }
/*     */     }
/* 406 */     String emailid = ((AMActionForm)form).getSendmail();
/* 407 */     String hour = ((AMActionForm)form).getDailyhour();
/* 408 */     String minute = ((AMActionForm)form).getDailyminute();
/* 409 */     String status = ((AMActionForm)form).getStatus();
/* 410 */     AMConnectionPool cp = new AMConnectionPool();
/*     */     
/*     */     try
/*     */     {
/* 414 */       DBUtil.updateGlobalConfigValue("SummaryMailer", status);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 418 */       ex.printStackTrace();
/*     */     }
/*     */     try
/*     */     {
/* 422 */       rs = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='SummaryMailer.Emailid'");
/* 423 */       if (rs.next())
/*     */       {
/*     */ 
/* 426 */         DBUtil.updateGlobalConfigValue("SummaryMailer.Emailid", emailid);
/*     */       }
/*     */       else
/*     */       {
/* 430 */         AMConnectionPool.executeUpdateStmt("INSERT INTO AM_GLOBALCONFIG VALUES('SummaryMailer.Emailid','" + emailid + "')");
/*     */       }
/* 432 */       rs.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 436 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 441 */       rs = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='SummaryMailer.Time'");
/* 442 */       if (rs.next())
/*     */       {
/*     */ 
/* 445 */         DBUtil.updateGlobalConfigValue("SummaryMailer.Time", hour + ":" + minute);
/*     */       }
/*     */       else
/*     */       {
/* 449 */         AMConnectionPool.executeUpdateStmt("INSERT INTO AM_GLOBALCONFIG VALUES('SummaryMailer.Time','" + hour + ":" + minute + "')");
/*     */       }
/* 451 */       rs.close();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 455 */       ex.printStackTrace();
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 460 */       SummaryMailer sm = (SummaryMailer)SummaryMailer.obj_ref.get("summarymailer");
/* 461 */       if (sm != null)
/*     */       {
/* 463 */         sm.enabled = false;
/*     */       }
/* 465 */       SummaryMailer smnew = new SummaryMailer();
/* 466 */       SummaryMailer.obj_ref.put("summarymailer", smnew);
/* 467 */       smnew.start();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 471 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 474 */     request.setAttribute("message", FormatUtil.getString("am.webclient.downttimereport.success.message.text"));
/* 475 */     return new ActionForward("/customReports.do?method=showCustomReports&toshow=summarmailer");
/*     */   }
/*     */   
/*     */   public void enableReportInExtensionTable(String attributeid) {
/* 479 */     boolean toReturn = false;
/* 480 */     ResultSet rs = null;
/* 481 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */     try {
/* 483 */       String from_Query = "Select DATATABLE,ARCHIVEDDATA_TABLENAME from AM_ATTRIBUTES_EXT where attributeid in (" + attributeid + ")";
/* 484 */       rs = AMConnectionPool.executeQueryStmt(from_Query);
/*     */       
/* 486 */       if (rs.next()) {
/* 487 */         String archived_table = rs.getString("ARCHIVEDDATA_TABLENAME");
/*     */         
/* 489 */         String data_table = rs.getString("DATATABLE");
/* 490 */         if ((!"AM_CAM_DC_ATTRIBUTES".equals(data_table)) && ("AM_MinMaxAvgData".equals(archived_table))) {
/* 491 */           archived_table = data_table + "_MinMaxAvgData";
/*     */         }
/* 493 */         if (!"AM_CAM_DC_ATTRIBUTES".equals(data_table)) {
/* 494 */           createArchivingTables(archived_table);
/*     */         }
/* 496 */         String update_Query = "UPDATE AM_ATTRIBUTES_EXT SET ISARCHIVEING=1 ,REPORTS_ENABLED=1,ARCHIVEDDATA_TABLENAME='" + archived_table + "' where ATTRIBUTEID in (" + attributeid + ")";
/* 497 */         AMConnectionPool.executeUpdateStmt(update_Query);
/* 498 */         EnterpriseUtil.addUpdateQueryToFile(update_Query);
/*     */       }
/*     */       else {
/* 501 */         System.out.println("****PROBLEM IN CUSTOMREPORTACTION CLASS WHILE UPDATING AM_ATTRIBUTES_EXT TABLE FOR THE ID===>" + attributeid);
/*     */       }
/* 503 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     catch (Exception ex) {
/* 506 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void createArchivingTables(String tablename) {
/* 511 */     ResultSet set = null;
/*     */     try
/*     */     {
/* 514 */       AMConnectionPool cp = new AMConnectionPool();
/* 515 */       ArrayList a1 = new ArrayList();
/* 516 */       String query = null;
/*     */       
/*     */ 
/* 519 */       if ((!isTableCreated(tablename)) && (!"AM_MinMaxAvgData".equals(tablename)))
/*     */       {
/*     */ 
/* 522 */         query = "create table " + tablename + " (ARCHIVEDTIME BIGINT,RESID integer not null,DURATION integer not null,ATTRIBUTEID integer not null,MINVALUE BIGINT,MAXVALUE BIGINT,TOTAL BIGINT,TOTALCOUNT BIGINT,FIFTHMINUTE BIGINT DEFAULT -1,TENTHMINUTE BIGINT DEFAULT -1,FIFTEENTHMINUTE BIGINT DEFAULT -1,TWENTYTHMINUTE BIGINT DEFAULT -1,TWENTYFIFTHMINUTE BIGINT DEFAULT -1,THIRTYTHMINUTE BIGINT DEFAULT -1,THIRTYFIFTHMINUTE BIGINT DEFAULT -1,FORTYTHMINUTE BIGINT DEFAULT -1,FORTYFIFTHMINUTE BIGINT DEFAULT -1,FIFTYTHMINUTE BIGINT DEFAULT -1,FIFTYFIFTHMINUTE BIGINT DEFAULT -1,SIXTYTHMINUTE BIGINT DEFAULT -1,PRIMARY KEY (ARCHIVEDTIME,RESID,DURATION,ATTRIBUTEID))";
/*     */         
/* 524 */         AMConnectionPool.executeUpdateStmt(query);
/*     */         
/* 526 */         AMLog.debug("CustomReportAction :Inside createArchivingTables().. Creating Index for newly created archivedtables.." + tablename);
/* 527 */         query = "CREATE INDEX " + tablename + "_ndx" + " ON " + tablename + " (RESID)";
/* 528 */         AMConnectionPool.executeUpdateStmt(query);
/* 529 */         System.out.println("Table creatd sucessfully in AMServerFramework.java for the first time for-------> " + tablename);
/*     */       }
/*     */       return;
/*     */     }
/*     */     catch (Exception ex) {
/* 534 */       System.out.println("EXCEPTION in creating Archiving tables in ===>CustomReportAction.java file");
/* 535 */       ex.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 538 */         if (set != null) {
/* 539 */           AMConnectionPool.closeStatement(set);
/*     */         }
/*     */       } catch (Exception e) {
/* 542 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 547 */   private boolean isTableCreated(String tablename) { ResultSet rs = null;
/* 548 */     isTable = true;
/*     */     try {
/* 550 */       AMConnectionPool cp = new AMConnectionPool();
/* 551 */       ArrayList a1 = new ArrayList();
/* 552 */       rs = AMConnectionPool.executeQueryStmt(DBQueryUtil.getTopNValues("select * from " + tablename, "1"));
/* 553 */       if (rs.next()) {}
/* 554 */       return true;
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 559 */       isTable = false;
/*     */     }
/*     */     finally {
/*     */       try {
/* 563 */         if (rs != null) {
/* 564 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */       } catch (Exception e) {
/* 567 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void disableReportInExtensionTable(List attList)
/*     */   {
/* 574 */     boolean toReturn = false;
/* 575 */     String update_Query = null;
/*     */     try {
/* 577 */       for (int j = 0; j < attList.size(); j++) {
/* 578 */         String attributeid = attList.get(j).toString();
/* 579 */         if (!attributeList.contains(attributeid)) {
/* 580 */           update_Query = "UPDATE AM_ATTRIBUTES_EXT SET ISARCHIVEING=0 ,REPORTS_ENABLED=0 where ATTRIBUTEID in (" + attributeid + ")";
/* 581 */           AMConnectionPool.executeUpdateStmt(update_Query);
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 585 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CustomReportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */