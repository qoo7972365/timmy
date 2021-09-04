/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import com.adventnet.appmanager.client.jdk15.JDK15Action;
/*    */ import com.adventnet.appmanager.ingres.struts.IngresAction;
/*    */ import com.adventnet.appmanager.mysql.struts.MySqlAction;
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import com.adventnet.appmanager.util.HeapDumper;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ import org.apache.struts.actions.DispatchAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugInfo
/*    */   extends DispatchAction
/*    */ {
/*    */   public ActionForward triggerThreadDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 24 */     return new JDK15Action().getThreadInfo(mapping, form, request, response);
/*    */   }
/*    */   
/*    */   public ActionForward showThreadDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 28 */     return new JDK15Action().showThreadDump(mapping, form, request, response);
/*    */   }
/*    */   
/*    */   public ActionForward triggerProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*    */   {
/* 33 */     String type = request.getParameter("type");
/* 34 */     if ("mysql".equalsIgnoreCase(type)) {
/* 35 */       return new MySqlAction().triggerprocesslist(mapping, form, request, response);
/*    */     }
/* 37 */     return new IngresAction().triggerIngersProcess(mapping, form, request, response);
/*    */   }
/*    */   
/*    */   public ActionForward showProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*    */   {
/* 42 */     String type = request.getParameter("type");
/* 43 */     if ("mysql".equalsIgnoreCase(type)) {
/* 44 */       return new MySqlAction().displayprocesslist(mapping, form, request, response);
/*    */     }
/* 46 */     return new IngresAction().displayIngresProcessList(mapping, form, request, response);
/*    */   }
/*    */   
/*    */   public ActionForward triggerHeapDump(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*    */   {
/* 51 */     response.setContentType("text/html; charset=UTF-8");
/* 52 */     PrintWriter out = response.getWriter();
/* 53 */     if ((request.isUserInRole("ENTERPRISEADMIN")) || (request.isUserInRole("ADMIN"))) {
/*    */       try {
/* 55 */         HeapDumper.dumpHeap(true);
/* 56 */         out.write(FormatUtil.getString("am.javaruntime.heapdump.success"));
/*    */       } catch (RuntimeException e) {
/* 58 */         e.printStackTrace();
/* 59 */         out.write(FormatUtil.getStringEn("am.webclient.trigger.heapdump.error") + " : " + e.getMessage());
/*    */       }
/*    */     } else {
/* 62 */       out.write(FormatUtil.getStringEn("am.webclient.api.user.violation"));
/*    */     }
/* 64 */     out.flush();
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DebugInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */