/*     */ package com.adventnet.appmanager.client.restapi;
/*     */ 
/*     */ import com.adventnet.appmanager.client.wsm.WSMAction;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.restapi.datacollection.RestAPIDataCollector;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RestAPIAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public void updateInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  37 */     String resID = request.getParameter("resID");
/*  38 */     String inputType = request.getParameter("inputType");
/*  39 */     String input = request.getParameter("input");
/*  40 */     input = StringEscapeUtils.unescapeXml(input);
/*  41 */     String result = "";
/*  42 */     PreparedStatement ps = null;
/*     */     try
/*     */     {
/*  45 */       ps = AMConnectionPool.getPreparedStatement("update AM_RESTAPI_DETAILS set " + inputType + "=? where RESOURCEID=?");
/*  46 */       ps.setString(1, input);
/*  47 */       ps.setInt(2, Integer.parseInt(resID));
/*  48 */       ps.executeUpdate();
/*  49 */       result = FormatUtil.getString("am.restapi.update.input.success.message", new String[] { inputType });
/*  50 */       RestAPIDataCollector.xsltOrJSONModifiedResIDs.put(resID, Long.valueOf(System.currentTimeMillis()));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  54 */       e.printStackTrace();
/*  55 */       result = "Error during update : " + e.getMessage();
/*     */     }
/*     */     finally
/*     */     {
/*  59 */       AMConnectionPool.closePreparedStatement(ps);
/*     */     }
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html;charset=UTF-8");
/*  64 */       PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
/*  65 */       out.println(result);
/*  66 */       out.flush();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  70 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward enabledisablereports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  80 */     ActionMessages messages = new ActionMessages();
/*  81 */     String[] custAttrs = request.getParameterValues("custom");
/*  82 */     String enable = request.getParameter("enable");
/*  83 */     String resourceId = request.getParameter("resourceid");
/*  84 */     int hasreport = 0;
/*  85 */     if (enable.equals("true")) {
/*  86 */       hasreport = 1;
/*     */     }
/*  88 */     if (custAttrs != null)
/*     */     {
/*  90 */       WSMAction.updateReportSettings(custAttrs, hasreport, enable);
/*  91 */       if (hasreport == 1) {
/*  92 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.reportenabled.text"));
/*     */       }
/*     */       else
/*     */       {
/*  96 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.wsm.reportdisabled.text"));
/*     */       }
/*  98 */       saveMessages(request, messages);
/*     */     }
/* 100 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceId);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\restapi\RestAPIAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */