/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import com.manageengine.appmanager.diagnostics.util.DiagnosticsAPIUtil;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Hashtable;
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
/*    */ 
/*    */ 
/*    */ public class DiagnosticInfoAction
/*    */   extends DispatchAction
/*    */ {
/*    */   public ActionForward showDiagnosticInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 27 */     ArrayList<Hashtable<String, String>> diagnosticinfo = DiagnosticsAPIUtil.getDiagnosticDetails(null);
/* 28 */     request.setAttribute("diagnosticinfo", diagnosticinfo);
/* 29 */     return mapping.findForward("diagnosticValue");
/*    */   }
/*    */   
/*    */   public ActionForward editDiagnosticInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*    */   {
/*    */     try {
/* 35 */       response.setContentType("text/html; charset=UTF-8");
/* 36 */       PrintWriter out = response.getWriter();
/* 37 */       String pollinterval = request.getParameter("pollinterval");
/* 38 */       String pollcount = request.getParameter("pollcount");
/* 39 */       String criticalvalue = request.getParameter("criticalvalue");
/* 40 */       String diagnosticname = request.getParameter("diagnosticname");
/* 41 */       HashMap<String, String> diagnosticDetails = new HashMap();
/* 42 */       diagnosticDetails.put("diagnosticname", diagnosticname);
/* 43 */       diagnosticDetails.put("pollinterval", pollinterval);
/* 44 */       diagnosticDetails.put("pollcount", pollcount);
/* 45 */       diagnosticDetails.put("criticalvalue", criticalvalue);
/* 46 */       String description = DiagnosticsAPIUtil.updateDiagnosticDetails(diagnosticDetails);
/* 47 */       out.println(description);
/* 48 */       out.flush();
/*    */     } catch (Exception ex) {
/* 50 */       ex.printStackTrace();
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DiagnosticInfoAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */