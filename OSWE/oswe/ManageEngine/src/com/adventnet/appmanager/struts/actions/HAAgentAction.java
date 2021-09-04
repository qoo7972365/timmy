/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import HTTPClient.HTTPConnection;
/*     */ import HTTPClient.HTTPResponse;
/*     */ import HTTPClient.ModuleException;
/*     */ import HTTPClient.ParseException;
/*     */ import HTTPClient.URI;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.HAAgentUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HAAgentAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward startAPM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/*  31 */       String masId = request.getParameter("masId");
/*  32 */       ActionMessages messages = new ActionMessages();
/*  33 */       Properties agentInfo = HAAgentUtil.getHAAgentDetails(masId);
/*  34 */       agentInfo.put("command", "start");
/*  35 */       AMLog.debug("HAAgent Action : agentInfo = " + agentInfo);
/*  36 */       ActionExecutor actionExecutor = new ActionExecutor(agentInfo);
/*  37 */       actionExecutor.start();
/*  38 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.haagent.start.triggered"));
/*  39 */       saveMessages(request, messages);
/*     */     } catch (Exception e) {
/*  41 */       request.setAttribute("ErrorMessage", e.getLocalizedMessage());
/*  42 */       e.printStackTrace();
/*     */     }
/*  44 */     return mapping.findForward("managedservers");
/*     */   }
/*     */   
/*     */ 
/*     */   private String getParams(Properties agentInfo)
/*     */   {
/*  50 */     StringBuffer toReturn = new StringBuffer();
/*  51 */     toReturn.append("?");
/*  52 */     toReturn.append("id").append("=").append(agentInfo.getProperty("AGENTID"));
/*  53 */     toReturn.append("&");
/*  54 */     toReturn.append("command").append("=").append(agentInfo.getProperty("command"));
/*  55 */     toReturn.append("&");
/*  56 */     toReturn.append("key").append("=").append(agentInfo.getProperty("APIKEY"));
/*  57 */     return toReturn.toString();
/*     */   }
/*     */   
/*     */   public ActionForward stopAPM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try
/*     */     {
/*  64 */       String masId = request.getParameter("masId");
/*  65 */       ActionMessages messages = new ActionMessages();
/*  66 */       Properties agentInfo = HAAgentUtil.getHAAgentDetails(masId);
/*  67 */       if (agentInfo != null) {
/*  68 */         agentInfo.put("command", "stop");
/*  69 */         AMLog.debug("HAAgent Action : agentInfo = " + agentInfo);
/*  70 */         ActionExecutor actionExecutor = new ActionExecutor(agentInfo);
/*  71 */         actionExecutor.start();
/*  72 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.haagent.stop.triggered"));
/*  73 */         saveMessages(request, messages);
/*     */       }
/*     */     } catch (Exception e) {
/*  76 */       request.setAttribute("ErrorMessage", e.getLocalizedMessage());
/*  77 */       e.printStackTrace();
/*     */     }
/*     */     
/*  80 */     return mapping.findForward("managedservers");
/*     */   }
/*     */   
/*     */   class ActionExecutor extends Thread {
/*     */     Properties agentInfo;
/*     */     String errMessage;
/*     */     
/*  87 */     public ActionExecutor(Properties agentInfo) { this.agentInfo = agentInfo; }
/*     */     
/*     */     public void run()
/*     */     {
/*  91 */       String uriStr = "http://" + this.agentInfo.getProperty("HOST") + ":" + this.agentInfo.getProperty("PORT");
/*     */       
/*  93 */       String method = "HAAgentServlet";
/*  94 */       String params = HAAgentAction.this.getParams(this.agentInfo);
/*  95 */       AMLog.audit("HAAgent URL : " + uriStr);
/*  96 */       AMLog.audit("HAAgent params : " + params);
/*  97 */       byte[] data = null;
/*     */       try {
/*  99 */         URI uri = new URI(uriStr);
/* 100 */         HTTPConnection con1 = new HTTPConnection(uri);
/* 101 */         HTTPResponse rsp = con1.Post(method + params);
/* 102 */         data = rsp.getData();
/*     */       } catch (ParseException e) {
/* 104 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 106 */         e.printStackTrace();
/*     */       } catch (ModuleException e) {
/* 108 */         e.printStackTrace();
/*     */       }
/* 110 */       String res = new String(data);
/* 111 */       AMLog.debug("High Availability Agent Log : Agent Response : " + res);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\HAAgentAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */