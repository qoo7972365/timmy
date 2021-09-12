/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ public class AdminTemplateAction extends org.apache.struts.action.Action
/*    */ {
/*    */   public static final String ADMIN_LAYOUT = "adminLayout";
/*    */   
/*    */   public org.apache.struts.action.ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 14 */     String tileName = request.getParameter("TileName");
/* 15 */     if (tileName != null)
/*    */     {
/* 17 */       return new org.apache.struts.action.ActionForward(tileName);
/*    */     }
/* 19 */     return mapping.findForward("adminLayout");
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AdminTemplateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */