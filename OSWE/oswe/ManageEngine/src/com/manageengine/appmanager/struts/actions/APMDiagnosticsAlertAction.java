/*     */ package com.manageengine.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.manageengine.appmanager.diagnostics.constants.DiagnosticsConstants;
/*     */ import com.manageengine.appmanager.diagnostics.util.APMDiagnosticsFaultHandler;
/*     */ import com.manageengine.appmanager.diagnostics.util.SelfDiagnosticUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class APMDiagnosticsAlertAction
/*     */   extends DispatchAction
/*     */   implements DiagnosticsConstants
/*     */ {
/*     */   private static final String EVENT_HISTORY = "EVENT_HISTORY";
/*     */   private static final String ALERT_DETAILS = "ALERT_DETAILS";
/*     */   private static final String ALERT_HISTORY = "ALERT_HISTORY";
/*     */   private static final String REQTYPE = "REQTYPE";
/*     */   private static final String AJAX = "AJAX";
/*     */   private static final String ALERTS_LIST = "ALERTS_LIST";
/*     */   
/*     */   public ActionForward annotateDiagnosticsAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  42 */     if ((!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))))
/*     */     {
/*  44 */       boolean result = APMDiagnosticsFaultHandler.getInstance().saveAnnotation(request);
/*  45 */       Map<String, String> resultProps = new HashMap();
/*  46 */       resultProps.put("RESULT", Boolean.toString(result));
/*  47 */       if (result)
/*     */       {
/*  49 */         resultProps.put("RESULT_IMG", "/images/icon_tickmark.gif");
/*  50 */         resultProps.put("STATUS", FormatUtil.getString("am.webclient.diagnostic.annotation.success"));
/*     */       }
/*     */       else
/*     */       {
/*  54 */         resultProps.put("RESULT_IMG", "/images/cross.gif");
/*  55 */         resultProps.put("STATUS", FormatUtil.getString("am.webclient.diagnostic.annotation.failure"));
/*     */       }
/*  57 */       Gson gson = new Gson();
/*  58 */       String jsonString = gson.toJson(resultProps, new TypeToken() {}.getType());
/*  59 */       PrintWriter out = null;
/*  60 */       response.setCharacterEncoding("UTF-8");
/*     */       try
/*     */       {
/*  63 */         out = response.getWriter();
/*  64 */         out.println(jsonString);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  68 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*  72 */         if (out != null)
/*     */         {
/*  74 */           out.close();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  80 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward clearDiagnosticsAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  88 */     if ((!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))))
/*     */     {
/*     */ 
/*  91 */       Map<String, String> resultProps = APMDiagnosticsFaultHandler.getInstance().clearAlert(request);
/*  92 */       Gson gson = new Gson();
/*  93 */       String jsonString = gson.toJson(resultProps, new TypeToken() {}.getType());
/*  94 */       PrintWriter out = null;
/*  95 */       response.setCharacterEncoding("UTF-8");
/*     */       try
/*     */       {
/*  98 */         out = response.getWriter();
/*  99 */         out.println(jsonString);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 103 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 107 */         if (out != null)
/*     */         {
/* 109 */           out.close();
/*     */         }
/*     */       }
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward clearMultipleDiagnosticsAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 122 */     if ((!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))))
/*     */     {
/* 124 */       boolean result = APMDiagnosticsFaultHandler.getInstance().clearMultipleAlerts(request);
/* 125 */       Map<String, String> resultProps = new HashMap();
/* 126 */       resultProps.put("RESULT", Boolean.toString(result));
/* 127 */       if (result)
/*     */       {
/* 129 */         resultProps.put("RESULT_IMG", "/images/icon_tickmark.gif");
/* 130 */         resultProps.put("STATE_IMG", SelfDiagnosticUtil.getStatusImage(4));
/* 131 */         resultProps.put("STATUS", FormatUtil.getString("am.webclient.diagnostic.clear.success"));
/*     */       }
/*     */       else
/*     */       {
/* 135 */         resultProps.put("RESULT_IMG", "/images/cross.gif");
/* 136 */         resultProps.put("STATUS", FormatUtil.getString("am.webclient.diagnostic.clear.failure.multiple"));
/*     */       }
/* 138 */       Gson gson = new Gson();
/* 139 */       String jsonString = gson.toJson(resultProps, new TypeToken() {}.getType());
/* 140 */       response.setCharacterEncoding("UTF-8");
/* 141 */       PrintWriter out = null;
/*     */       try
/*     */       {
/* 144 */         out = response.getWriter();
/* 145 */         out.println(jsonString);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 149 */         AMLog.warning("Error while clearing alerts ", e);
/*     */       }
/*     */       finally
/*     */       {
/* 153 */         if (out != null)
/*     */         {
/* 155 */           out.close();
/*     */         }
/*     */       }
/*     */     }
/* 159 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getAlertHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 168 */     if ((!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))))
/*     */     {
/* 170 */       String alertIdStr = request.getParameter("ALERT_ID");
/* 171 */       long count = APMDiagnosticsFaultHandler.getInstance().getEventHistoryCount(alertIdStr);
/* 172 */       request.setAttribute("totalObjCount", Long.valueOf(count));
/* 173 */       List<Map<String, String>> eventHistory = APMDiagnosticsFaultHandler.getInstance().getEventHistory(request);
/* 174 */       request.setAttribute("EVENT_HISTORY", eventHistory);
/* 175 */       Map<String, String> alertDetails = APMDiagnosticsFaultHandler.getInstance().getAlertDetails(request);
/* 176 */       request.setAttribute("ALERT_DETAILS", alertDetails);
/* 177 */       return mapping.findForward("ALERT_HISTORY");
/*     */     }
/*     */     
/*     */ 
/* 181 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 189 */     String parameter = mapping.getParameter();
/* 190 */     String paramValue = request.getParameter(parameter);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 195 */     if (paramValue == null) {
/* 196 */       return showDiagnosticsAlerts(mapping, form, request, response);
/*     */     }
/* 198 */     return super.execute(mapping, form, request, response);
/*     */   }
/*     */   
/*     */   private ActionForward showDiagnosticsAlerts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 203 */     String requestFromAlarmsPage = request.getParameter("fromAlertTab");
/* 204 */     if ((!DBUtil.isDelegatedAdmin(request.getRemoteUser())) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))))
/*     */     {
/* 206 */       String requestType = request.getParameter("REQTYPE");
/* 207 */       long count = APMDiagnosticsFaultHandler.getInstance().getAlertsListCount();
/* 208 */       request.setAttribute("totalObjCount", Long.valueOf(count));
/* 209 */       List<Map<String, String>> alertsList = APMDiagnosticsFaultHandler.getInstance().getAlertsList(request);
/* 210 */       if ((requestType != null) && (requestType.trim().equalsIgnoreCase("AJAX")))
/*     */       {
/* 212 */         Gson gson = new Gson();
/* 213 */         String jsonString = gson.toJson(alertsList, new TypeToken() {}.getType());
/* 214 */         response.setCharacterEncoding("UTF-8");
/* 215 */         PrintWriter out = null;
/*     */         try
/*     */         {
/* 218 */           out = response.getWriter();
/* 219 */           out.println(jsonString);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 223 */           e.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/* 227 */           if (out != null)
/*     */           {
/* 229 */             out.close();
/*     */           }
/*     */         }
/* 232 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 236 */       request.setAttribute("ALERTS_LIST", alertsList);
/*     */     }
/*     */     
/* 239 */     if ((requestFromAlarmsPage != null) && (requestFromAlarmsPage.equalsIgnoreCase("true"))) {
/* 240 */       return mapping.findForward("ALERTS_LIST_NOTILE");
/*     */     }
/* 242 */     return mapping.findForward("ALERTS_LIST");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\struts\actions\APMDiagnosticsAlertAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */