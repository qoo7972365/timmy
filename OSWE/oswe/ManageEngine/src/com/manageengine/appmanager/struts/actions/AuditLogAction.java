/*    */ package com.manageengine.appmanager.struts.actions;
/*    */ 
/*    */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.me.apm.server.audit.AuditUtil;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Vector;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ import org.apache.struts.actions.DispatchAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuditLogAction
/*    */   extends DispatchAction
/*    */ {
/* 23 */   AuditUtil auditUtil = new AuditUtil();
/*    */   
/*    */   public ActionForward getAuditDetailsForMonitorGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/* 26 */     AMLog.debug("AuditLogAction:getAuditDetailsForMonitorGroup:#VIEW AUDIT LOG REQUEST##");
/*    */     try {
/* 28 */       ManagedApplication mo = new ManagedApplication();
/* 29 */       String resourceid = request.getParameter("haid");
/* 30 */       Vector childIds = new Vector();
/* 31 */       childIds.add(resourceid);
/* 32 */       ManagedApplication.getChildIDs(childIds, resourceid);
/* 33 */       String resourceids = childIds.toString();
/* 34 */       resourceids = resourceids.substring(1, resourceids.length() - 1);
/* 35 */       String nameString = this.auditUtil.getResourceNameforId(resourceid);
/* 36 */       request.setAttribute("auditName", nameString);
/* 37 */       request.setAttribute("resourceidlist", resourceids);
/*    */     }
/*    */     catch (Exception ex) {
/* 40 */       ex.printStackTrace();
/*    */     }
/* 42 */     String forwardPage = "auditForMG";
/* 43 */     return mapping.findForward(forwardPage);
/*    */   }
/*    */   
/*    */ 
/*    */   public ActionForward loadAuditLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/*    */     try
/*    */     {
/* 51 */       response.setContentType("text/json");
/* 52 */       response.setCharacterEncoding("UTF-8");
/* 53 */       int startIndex = Integer.parseInt(request.getParameter("startIndex"));
/* 54 */       String resourceid = request.getParameter("resourceid");
/* 55 */       AMLog.debug("AuditLogAction: loadAuditLog: Method call for resourceid " + resourceid);
/* 56 */       JsonObject auditLog = new JsonObject();
/* 57 */       auditLog = this.auditUtil.getAuditLogFromStartIndex(resourceid, startIndex);
/* 58 */       PrintWriter out = response.getWriter();
/*    */       try
/*    */       {
/* 61 */         JsonObject result = new JsonObject();
/* 62 */         result.add("auditLogMsg", auditLog);
/* 63 */         out.println(result);
/*    */       }
/*    */       catch (Exception exc)
/*    */       {
/* 67 */         exc.printStackTrace();
/*    */       }
/* 69 */       out.flush();
/*    */     }
/*    */     catch (Exception e) {
/* 72 */       e.printStackTrace();
/*    */     }
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\struts\actions\AuditLogAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */