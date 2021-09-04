/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnamolyDetection
/*     */   extends DispatchAction
/*     */ {
/*  79 */   private static Log log = LogFactory.getLog("WebClient");
/*  80 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*  82 */   private static String host = "localhost";
/*  83 */   private static String port = System.getProperty("webserver.port");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  89 */   private static String htmlMailTpl = getHTMLMailTpl();
/*     */   
/*     */   public static String getMailTemplate(String monitorname, String attributename, String value, String rootcause) {
/*  92 */     String toreturn = htmlMailTpl;
/*     */     try {
/*  94 */       host = InetAddress.getLocalHost().getHostName();
/*  95 */       String fline = FormatUtil.findReplace(toreturn, "~topheading~", FormatUtil.getString("am.webclient.managermail.heading.text"));
/*  96 */       String resource = FormatUtil.findReplace(fline, "~source~", monitorname);
/*  97 */       String attr = FormatUtil.findReplace(resource, "~attribute~", attributename);
/*  98 */       String messa = FormatUtil.findReplace(attr, "~message~", value);
/*  99 */       String root = FormatUtil.findReplace(messa, "~rootcause~", rootcause);
/* 100 */       String repby = FormatUtil.findReplace(root, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 101 */       String hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*     */       
/*     */ 
/*     */ 
/* 105 */       String nameFilled = null;
/* 106 */       if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*     */       {
/* 108 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*     */       }
/*     */       else
/*     */       {
/* 112 */         nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*     */       }
/*     */       
/* 115 */       toreturn = FormatUtil.findReplace(nameFilled, "~port~", port);
/*     */     } catch (Exception ex) {
/* 117 */       ex.printStackTrace();
/*     */     }
/* 119 */     return toreturn;
/*     */   }
/*     */   
/*     */ 
/*     */   private static String getHTMLMailTpl()
/*     */   {
/*     */     try
/*     */     {
/* 127 */       String filePath = "./conf/AnamolyMail.html";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */       return FormatUtil.getContentsAsString(filePath);
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/*     */ 
/*     */ 
/* 145 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template");
/*     */       
/* 147 */       io.printStackTrace(); }
/* 148 */     return "error in sending mail";
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getHTMLMailTemplate()
/*     */   {
/* 154 */     return htmlMailTpl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showCustomTime(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 165 */       ReportForm rf = (ReportForm)form;
/* 166 */       long starttime = ReportUtilities.parseAndReturnTimeStamp(rf.getStartDate());
/* 167 */       long endtime = ReportUtilities.parseAndReturnTimeStamp(rf.getEndDate());
/* 168 */       request.setAttribute("sttime", starttime + "");request.setAttribute("edtime", endtime + "");
/*     */     }
/*     */     catch (Exception ex) {
/* 171 */       ex.printStackTrace();
/*     */     }
/* 173 */     return new ActionForward("/jsp/AttributeDetails.jsp?customtime=true");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showAlertTimes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 184 */     String returnUrl = "/jsp/AttributeDetails.jsp";
/*     */     try
/*     */     {
/* 187 */       String eid = request.getParameter("EID");
/* 188 */       String alerttime = request.getParameter("alerttime");
/* 189 */       String sType = request.getParameter("severityType");
/* 190 */       returnUrl = "/jsp/AttributeDetails.jsp?fromAlerts=true&haid=" + eid + "&time=" + alerttime + "&severity=" + sType;
/*     */     } catch (Exception ex) {
/* 192 */       ex.printStackTrace();
/*     */     }
/* 194 */     return new ActionForward(returnUrl);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showCustomGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 204 */     String returnUrl = "/jsp/AttributeDetails.jsp";
/*     */     try
/*     */     {
/* 207 */       String attid = request.getParameter("aid");
/* 208 */       String type = request.getParameter("rtype");
/* 209 */       String customgraph = request.getParameter("customgraph");
/* 210 */       String oldhaid = request.getParameter("oldhaid");
/* 211 */       String oldalertid = request.getParameter("oldalertid");
/* 212 */       String sType = request.getParameter("severityType");
/*     */       
/* 214 */       if (type.indexOf("@") > 0) {
/* 215 */         String[] temp = type.split("@");
/* 216 */         type = temp[0];
/*     */       }
/* 218 */       String key = type + "_" + attid;
/* 219 */       String cShowtime = request.getParameter("customshowtime");
/* 220 */       long time = 0L;
/* 221 */       if (cShowtime != null) {
/* 222 */         time = ReportDataUtilities.parseAndReturnTimeStamp(cShowtime);
/*     */       }
/* 224 */       request.setAttribute("resType", request.getParameter("rtype"));
/* 225 */       returnUrl = "/jsp/AttributeDetails.jsp?fromAlerts=true&haid=" + oldhaid + "&alertid=" + key + "&oldalertid=" + oldalertid + "&showtime=" + time + "&customgraph=true&severity=" + sType + "&resType=" + request.getParameter("rtype") + "&cst=" + cShowtime;
/*     */     }
/*     */     catch (Exception ex) {
/* 228 */       ex.printStackTrace();
/*     */     }
/* 230 */     return new ActionForward(returnUrl, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showRawDataValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 240 */     String returnUrl = "/jsp/AttributeDetails.jsp";
/*     */     
/*     */     try
/*     */     {
/* 244 */       String attid = request.getParameter("attid");
/* 245 */       String resid = request.getParameter("resid");
/* 246 */       String monitorname = request.getParameter("monitorname");
/* 247 */       String time = request.getParameter("time");
/* 248 */       String attName = request.getParameter("Adname");
/* 249 */       String isValue = request.getParameter("isValue");
/* 250 */       String isSecondlevel = request.getParameter("isSecondlevel");
/*     */       
/* 252 */       String allvalues = ReportDataUtilities.getRawDataValues(resid, attid);
/* 253 */       if ("711".equalsIgnoreCase(attid)) {
/* 254 */         attName = FormatUtil.getString("Disk Utilization");
/*     */       }
/*     */       
/* 257 */       request.setAttribute("attname", attName);
/* 258 */       returnUrl = "/jsp/AttributeDetailsDiv.jsp?graphkey=" + allvalues + "&isSecondlevel=" + isSecondlevel + "&toshow=" + resid + "&monitorname=" + monitorname + "&time=" + time + "&Adname=" + attName + "&isValue=" + isValue;
/*     */     } catch (Exception ex) {
/* 260 */       ex.printStackTrace();
/*     */     }
/* 262 */     return new ActionForward(returnUrl);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showArchivedDataValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 273 */     String returnUrl = "/jsp/AttributeDetails.jsp";
/*     */     
/*     */     try
/*     */     {
/* 277 */       String attid = request.getParameter("attid");
/*     */       
/*     */ 
/* 280 */       if (attid.indexOf("@") != -1) {
/* 281 */         List onlyAttributeids = new ArrayList();
/* 282 */         String[] T1 = attid.split("@");
/* 283 */         for (int n = 0; n < T1.length; n++) {
/* 284 */           String t2 = T1[n];
/* 285 */           onlyAttributeids.add(t2);
/*     */         }
/* 287 */         attid = ReportDataUtilities.getCommaSeparatedIds(onlyAttributeids);
/*     */       } else {
/* 289 */         attid = "(" + attid + ")";
/*     */       }
/*     */       
/* 292 */       String resid = request.getParameter("resid");
/*     */       
/* 294 */       String time = request.getParameter("time");
/*     */       
/* 296 */       String toShow = request.getParameter("toshow");
/*     */       
/* 298 */       String hour = request.getParameter("hour");
/* 299 */       String sttime = request.getParameter("sttime");
/*     */       
/* 301 */       String edtime = request.getParameter("edtime");
/*     */       
/* 303 */       String isanomaly = request.getParameter("isValue");
/* 304 */       String anomalyid = request.getParameter("anomalyid");
/* 305 */       Map Data = ReportDataUtilities.getArchivedDataValuesForGraph(resid, attid, hour, sttime, edtime, toShow, anomalyid);
/* 306 */       String MonitorName = Data.get("MonitorDisplayName").toString();
/* 307 */       String AttributeName = Data.get("AttributeDisplayName").toString();
/*     */       
/* 309 */       String allvalues = Data.get("data").toString();
/* 310 */       Map anomalyvalues = (Map)Data.get("anomalyvalues");
/*     */       
/* 312 */       String isSecondlevel = request.getParameter("isSecondlevel");
/* 313 */       request.setAttribute("avalue", anomalyvalues);
/* 314 */       request.setAttribute("attname", AttributeName);
/*     */       
/*     */ 
/* 317 */       returnUrl = "/jsp/AttributeDetailsDiv.jsp?graphkey=" + allvalues + "&isSecondlevel=" + isSecondlevel + "&toshow=" + toShow + "&monitorname=" + MonitorName + "&time=" + time + "&Adname=" + AttributeName + "&isValue=" + isanomaly + "&anomalyid=" + anomalyid;
/*     */     }
/*     */     catch (Exception ex) {
/* 320 */       ex.printStackTrace();
/*     */     }
/* 322 */     return new ActionForward(returnUrl);
/*     */   }
/*     */   
/* 325 */   public ActionForward sendAttributeDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { AMConnectionPool cp = AMConnectionPool.getInstance();
/* 326 */     ReportForm rf = (ReportForm)form;
/* 327 */     ActionMessages messages = new ActionMessages();
/* 328 */     ActionErrors errors = new ActionErrors();
/* 329 */     ResultSet rs = null;
/* 330 */     String error = null;
/*     */     try {
/* 332 */       String restype = request.getParameter("resourcetype");
/*     */       
/* 334 */       if (restype.indexOf("@") != -1) {
/* 335 */         String[] temp = restype.split("@");
/* 336 */         restype = temp[1];
/*     */       }
/*     */       
/* 339 */       if (restype.indexOf("wind") > 0) {
/* 340 */         restype = "windows";
/*     */       }
/*     */       
/* 343 */       List attList = null;
/* 344 */       if ("windows".equalsIgnoreCase(restype)) {
/* 345 */         attList = ReportUtil.getAttributesForWindows();
/*     */       } else {
/* 347 */         attList = ReportUtil.getAttributesForResourcetype(restype);
/*     */       }
/*     */       
/* 350 */       ArrayList reportEnabledList = ReportUtil.getReportEnabledAttributes();
/*     */       
/*     */ 
/* 353 */       String text = " <select name=\"dropdownattributes\" > ";
/* 354 */       text = text + "<option value='--select--'>---" + FormatUtil.getString("am.anomaly.dropdown.selectattribute.text") + "---</option>";
/* 355 */       for (int j = 0; j < attList.size(); j++) {
/* 356 */         String res = attList.get(j).toString();
/* 357 */         String[] temp = res.split("#");
/* 358 */         String attributeid = temp[0];
/*     */         
/* 360 */         String displayname = temp[1];
/* 361 */         text = text + " <option value='" + attributeid + "'>" + FormatUtil.getString(displayname) + "</option>";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 366 */       text = text + " </select>";
/* 367 */       response.setContentType("text/html; charset=UTF-8");
/* 368 */       PrintWriter pw = response.getWriter();
/*     */       
/* 370 */       pw.print(text);
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
/* 382 */       return null;
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 372 */       ex.printStackTrace();
/*     */     } finally {
/* 374 */       if (rs != null) {
/*     */         try {
/* 376 */           AMConnectionPool.closeStatement(rs);
/*     */         }
/*     */         catch (Exception e) {}
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AnamolyDetection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */