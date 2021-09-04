/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.me.apm.msdynamics.crm.util.MSDynamicsCRMUtil;
/*     */ import com.me.apm.server.datatable.DataTableDefinition;
/*     */ import com.me.apm.server.msdynamics.crm.MSCRMEventLogHandler;
/*     */ import com.me.apm.server.xenapp.XenAppEventLogHandler;
/*     */ import com.me.apm.xenapp.util.XenAppUtil;
/*     */ import java.io.PrintWriter;
/*     */ import javax.servlet.RequestDispatcher;
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
/*     */ public class DataTableAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward initXenAppEventLogTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  29 */     String resId = request.getParameter("resourceid");
/*  30 */     JsonObject initParams = XenAppUtil.getEventLogTableInitParams(resId);
/*     */     
/*  32 */     response.setContentType("text/html");
/*  33 */     response.setCharacterEncoding("UTF-8");
/*     */     
/*  35 */     String actionPath = (String)request.getAttribute("actionPath");
/*  36 */     request.setAttribute("actionPath", actionPath);
/*  37 */     request.setAttribute("initParams", initParams.toString());
/*  38 */     request.setAttribute("title", FormatUtil.getString("am.webclient.cluster.windows.tab.events"));
/*  39 */     request.setAttribute("tableName", XenAppUtil.getEventLogTableDefinition().getName());
/*     */     
/*  41 */     RequestDispatcher rd = request.getRequestDispatcher("/jsp/datatable/EventLogView.jsp");
/*  42 */     rd.include(request, response);
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getEventLogForXenApp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  49 */     String resId = request.getParameter("resourceid");
/*     */     try
/*     */     {
/*  52 */       PrintWriter out = response.getWriter();
/*  53 */       String actionPath = (String)request.getAttribute("actionPath");
/*  54 */       request.setAttribute("actionPath", actionPath);
/*  55 */       XenAppEventLogHandler dataHandler = new XenAppEventLogHandler();
/*  56 */       JsonObject tableData = dataHandler.getTableData(request, response);
/*     */       
/*  58 */       response.setCharacterEncoding("UTF-8");
/*  59 */       response.setContentType("application/json");
/*  60 */       response.setHeader("Cache-Control", "no-store");
/*     */       
/*  62 */       out.println(tableData);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  66 */       AMLog.debug("Problem when getting the eventlog details for XenApp" + resId, ex);
/*     */     }
/*     */     
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward initDynamicsCRMEventLogTable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  74 */     String resId = request.getParameter("resourceid");
/*  75 */     JsonObject initParams = MSDynamicsCRMUtil.getEventLogTableInitParams(resId);
/*     */     
/*  77 */     response.setContentType("text/html");
/*  78 */     response.setCharacterEncoding("UTF-8");
/*     */     
/*  80 */     String actionPath = (String)request.getAttribute("actionPath");
/*  81 */     request.setAttribute("actionPath", actionPath);
/*  82 */     request.setAttribute("initParams", initParams.toString());
/*  83 */     request.setAttribute("title", FormatUtil.getString("am.webclient.cluster.windows.tab.events"));
/*  84 */     request.setAttribute("tableName", MSDynamicsCRMUtil.getEventLogTableDefinition().getName());
/*     */     
/*  86 */     RequestDispatcher rd = request.getRequestDispatcher("/jsp/datatable/EventLogView.jsp");
/*  87 */     rd.include(request, response);
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getEventLogForDynamicsCRM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  94 */     String resId = request.getParameter("resourceid");
/*     */     try
/*     */     {
/*  97 */       PrintWriter out = response.getWriter();
/*  98 */       String actionPath = (String)request.getAttribute("actionPath");
/*  99 */       request.setAttribute("actionPath", actionPath);
/* 100 */       MSCRMEventLogHandler dataHandler = new MSCRMEventLogHandler();
/* 101 */       JsonObject tableData = dataHandler.getTableData(request, response);
/*     */       
/* 103 */       response.setCharacterEncoding("UTF-8");
/* 104 */       response.setContentType("application/json");
/* 105 */       response.setHeader("Cache-Control", "no-store");
/*     */       
/* 107 */       out.println(tableData);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 111 */       AMLog.debug("Problem when getting the eventlog details for MS Dynamics CRM" + resId, ex);
/*     */     }
/*     */     
/* 114 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DataTableAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */