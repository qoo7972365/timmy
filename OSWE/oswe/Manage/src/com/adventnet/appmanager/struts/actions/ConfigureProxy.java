/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*    */ import com.adventnet.appmanager.struts.form.ProxyConfiguration;
/*    */ import com.adventnet.appmanager.util.ProxyUtil;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.Action;
/*    */ import org.apache.struts.action.ActionError;
/*    */ import org.apache.struts.action.ActionErrors;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ import org.apache.struts.action.ActionMessage;
/*    */ import org.apache.struts.action.ActionMessages;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ConfigureProxy
/*    */   extends Action
/*    */ {
/* 31 */   private ManagedApplication mo = new ManagedApplication();
/*    */   
/*    */ 
/*    */ 
/*    */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 38 */     ProxyConfiguration proxyconf = (ProxyConfiguration)form;
/* 39 */     String proxyMessage = "";
/*    */     
/* 41 */     ActionMessages messages = new ActionMessages();
/* 42 */     ActionErrors errors = new ActionErrors();
/* 43 */     String haid = request.getParameter("haid");
/*    */     
/* 45 */     String updateCredentials = "true".equals(request.getParameter("updateCredentials")) ? "true" : "false";
/*    */     
/*    */ 
/* 48 */     boolean useproxy = proxyconf.isUseproxy();
/* 49 */     String wsBypassproxy = request.getParameter("bypassproxy");
/*    */     
/*    */ 
/* 52 */     boolean bypassproxy = proxyconf.isBypassproxy();
/* 53 */     if (wsBypassproxy == null)
/*    */     {
/* 55 */       bypassproxy = false;
/* 56 */       proxyconf.setBypassproxy(false);
/*    */     }
/*    */     
/*    */ 
/* 60 */     Properties proxyProps = new Properties();
/* 61 */     proxyProps.setProperty("host", proxyconf.getHost());
/* 62 */     proxyProps.setProperty("port", proxyconf.getPort());
/* 63 */     proxyProps.setProperty("username", proxyconf.getUserId());
/* 64 */     proxyProps.setProperty("password", proxyconf.getPassword());
/* 65 */     proxyProps.setProperty("useproxy", Boolean.toString(useproxy));
/* 66 */     proxyProps.setProperty("bypassproxy", Boolean.toString(bypassproxy));
/* 67 */     proxyProps.setProperty("updateCredentials", updateCredentials);
/* 68 */     proxyProps.setProperty("dontProxyList", proxyconf.getDontProxyfor());
/*    */     
/* 70 */     boolean proxyResult = ProxyUtil.setProxyConfiguration(request, proxyProps);
/*    */     
/*    */ 
/* 73 */     proxyMessage = (String)request.getAttribute("proxyMessage");
/* 74 */     if (proxyResult)
/*    */     {
/* 76 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(proxyMessage));
/* 77 */       saveMessages(request, messages);
/*    */     }
/*    */     else
/*    */     {
/* 81 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(proxyMessage));
/* 82 */       saveErrors(request, errors);
/*    */     }
/*    */     
/* 85 */     if (request.getParameter("redirecturl") != null)
/*    */     {
/* 87 */       return new ActionForward(request.getParameter("redirecturl"));
/*    */     }
/* 89 */     if (haid != null)
/*    */     {
/* 91 */       return mapping.findForward("showapplication");
/*    */     }
/*    */     
/*    */ 
/* 95 */     return mapping.findForward("configureProxy");
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ConfigureProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */