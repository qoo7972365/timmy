/*     */ package com.adventnet.appmanager.reporting.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.ForecastReportUtil;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ForecastReportAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward generateForecastReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  30 */       ReportForm controls = (ReportForm)form;
/*     */       
/*  32 */       String attributeid = request.getParameter("attributeid");
/*  33 */       String timestamp = request.getParameter("period");
/*  34 */       String rowCount = request.getParameter("numberOfRows");
/*  35 */       String resourceid = request.getParameter("resourceid");
/*  36 */       String individualReport = request.getParameter("individualReport");
/*  37 */       String growthTrend = request.getParameter("growthtrend");
/*  38 */       String startDate = controls.getStartDate();
/*  39 */       String endDate = controls.getEndDate();
/*  40 */       boolean isPrivilegedUser = Constants.isPrivilegedUser(request);
/*  41 */       String loginUserid = Constants.getLoginUserid(request);
/*  42 */       String reportType = request.getParameter("reporttype");
/*  43 */       String haid = request.getParameter("haid");
/*  44 */       String mgReport = request.getParameter("mgreport");
/*     */       
/*     */ 
/*     */ 
/*  48 */       HashMap<String, String> predictionValues = new HashMap();
/*  49 */       predictionValues.put("attributeid", attributeid);
/*  50 */       predictionValues.put("timestamp", timestamp);
/*  51 */       predictionValues.put("rows", rowCount);
/*  52 */       predictionValues.put("resourceid", resourceid);
/*  53 */       predictionValues.put("individualReport", individualReport);
/*  54 */       predictionValues.put("growthTrend", growthTrend);
/*  55 */       predictionValues.put("startDate", startDate);
/*  56 */       predictionValues.put("endDate", endDate);
/*  57 */       predictionValues.put("isPrivilegedUser", String.valueOf(isPrivilegedUser));
/*  58 */       predictionValues.put("loginUserid", loginUserid);
/*  59 */       predictionValues.put("owner", request.getRemoteUser());
/*  60 */       if ((mgReport != null) && ("true".equals(mgReport.trim()))) {
/*  61 */         predictionValues.put("haid", haid);
/*     */       }
/*     */       
/*     */ 
/*  65 */       HashMap<String, String> graphValue = ForecastReportUtil.predictForecastValues(predictionValues);
/*     */       
/*  67 */       ForecastReportUtil.setRequestAttributes(graphValue, request);
/*  68 */       if ("csv".equals(reportType)) {
/*  69 */         request.setAttribute("reportType", reportType);
/*  70 */         return mapping.findForward("report.forecast.csv");
/*     */       }
/*     */     } catch (Exception ex) {
/*  73 */       ex.printStackTrace();
/*     */     }
/*  75 */     return mapping.findForward("report.forecast");
/*     */   }
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
/*     */   public ActionForward getForecastAttribute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 114 */       response.setContentType("text/html;charset=UTF-8");
/* 115 */       String resourceGroup = request.getParameter("resourcegroup");
/* 116 */       ReportForm controls = (ReportForm)form;
/* 117 */       ArrayList arr = new ArrayList();
/* 118 */       ReportUtilities.setForecastReport(true);
/* 119 */       if ("APP".equals(resourceGroup))
/*     */       {
/* 121 */         arr = controls.getAppArrayAttribute();
/*     */       }
/* 123 */       else if ("DBS".equals(resourceGroup))
/*     */       {
/* 125 */         arr = controls.getDbArrayAttribute();
/*     */       }
/* 127 */       else if ("SER".equals(resourceGroup))
/*     */       {
/* 129 */         arr = controls.getServicesArrayAttribute();
/*     */       }
/* 131 */       else if ("SYS".equals(resourceGroup))
/*     */       {
/* 133 */         arr = controls.getServerArrayAttribute();
/*     */       }
/* 135 */       else if ("URL".equals(resourceGroup))
/*     */       {
/* 137 */         arr = controls.getUrlsArrayAttribute();
/*     */       }
/* 139 */       else if ("VIR".equals(resourceGroup))
/*     */       {
/* 141 */         arr = controls.getVirserverArrayAttribute();
/*     */       }
/* 143 */       else if ("CLD".equals(resourceGroup))
/*     */       {
/* 145 */         arr = controls.getCloudAppsArrayAttribute();
/*     */       }
/* 147 */       else if ("MS".equals(resourceGroup))
/*     */       {
/* 149 */         arr = controls.getMailserverArrayAttribute();
/*     */       }
/* 151 */       else if ("ERP".equals(resourceGroup))
/*     */       {
/* 153 */         arr = controls.getErpArrayAttribute();
/*     */       }
/* 155 */       else if ("MOM".equals(resourceGroup))
/*     */       {
/* 157 */         arr = controls.getMomArrayAttribute();
/*     */       }
/* 159 */       ReportUtilities.setForecastReport(false);
/* 160 */       PrintWriter out = response.getWriter();
/*     */       
/* 162 */       JSONArray jsonArray = new JSONArray(arr);
/* 163 */       out.println(jsonArray);
/* 164 */       out.flush();
/*     */     } catch (Exception ex) {
/* 166 */       ex.printStackTrace();
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\actions\ForecastReportAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */