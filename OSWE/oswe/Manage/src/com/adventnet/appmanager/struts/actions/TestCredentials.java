/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.CustomDCInf;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.util.DiagnosticsUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
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
/*     */ public class TestCredentials
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward testCredentialForConfMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  32 */     Properties authResult = new Properties();
/*  33 */     String monType = null;
/*     */     try
/*     */     {
/*  36 */       monType = request.getParameter("montype");
/*  37 */       if ((monType == null) || (monType.equalsIgnoreCase("null")))
/*     */       {
/*  39 */         monType = request.getParameter("type");
/*     */       }
/*  41 */       NewMonitorConf newMonConf = new NewMonitorConf();
/*     */       
/*     */ 
/*  44 */       if ((newMonConf.preConfMap.containsKey(monType)) || (monType.equalsIgnoreCase("node")))
/*     */       {
/*  46 */         monType = newMonConf.getResourceTypeForPreConf(monType);
/*     */         
/*  48 */         authResult = newMonConf.getAuthResultAsPerResourceType(monType, request, true);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  53 */         Properties props = NewMonitorConf.getClass(monType);
/*  54 */         ArrayList args = NewMonitorUtil.getArgsforConfMon(monType);
/*  55 */         String dcclass = props.getProperty("dcclass");
/*  56 */         CustomDCInf amdc = (CustomDCInf)Class.forName(dcclass).newInstance();
/*  57 */         Properties argsasprops = NewMonitorConf.getValuesforArgs(request, args);
/*  58 */         authResult = amdc.CheckAuthentication(argsasprops);
/*     */       }
/*  60 */       response.setContentType("text/html; charset=UTF-8");
/*  61 */       PrintWriter out = response.getWriter();
/*  62 */       if (authResult.getProperty("authentication").equalsIgnoreCase("passed"))
/*     */       {
/*  64 */         String passedMsg = NmsUtil.GetString("Passed");
/*  65 */         out.println("<font color=green>" + passedMsg + "</font>");
/*  66 */         out.flush();
/*     */       }
/*     */       else
/*     */       {
/*  70 */         String failedMsg = NmsUtil.GetString("Failed");
/*  71 */         String failedReason = FormatUtil.getString(authResult.getProperty("error"));
/*  72 */         out.println("<font color=red>" + failedMsg + ". " + failedReason + "</font>");
/*  73 */         out.flush();
/*     */       }
/*     */     }
/*     */     catch (NoClassDefFoundError er)
/*     */     {
/*  78 */       er.printStackTrace();
/*     */       try {
/*  80 */         if ("WebsphereMQ".equals(monType)) {
/*  81 */           String failedMsg = NmsUtil.GetString("Failed");
/*  82 */           String failedReason = FormatUtil.getString("am.webclient.mqseries.classnotfound.text", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("product.talkback.mailid") });
/*  83 */           PrintWriter out = response.getWriter();
/*  84 */           out.println("<font color=red>" + failedMsg + ". <br/>" + failedReason + "</font>");
/*  85 */           out.flush();
/*     */         }
/*     */       } catch (Exception e) {
/*  88 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  93 */       ex.printStackTrace();
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward testSnmpCredential(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 100 */     String snmpVersion = request.getParameter("snmpVersion");
/* 101 */     String snmpPort = request.getParameter("snmpPort");
/* 102 */     String hostName = request.getParameter("deviceToCheck");
/* 103 */     Properties deviceProperties = new Properties();
/* 104 */     deviceProperties.setProperty("HOST", hostName);
/* 105 */     deviceProperties.setProperty("PORT", snmpPort);
/* 106 */     deviceProperties.setProperty("SNMPPORT", snmpPort);
/* 107 */     if (snmpVersion.equalsIgnoreCase("v1v2")) {
/* 108 */       String snmpCommunity = request.getParameter("snmpCommunity");
/* 109 */       deviceProperties.setProperty("SNMPCOMMUNITY", snmpCommunity);
/* 110 */       deviceProperties.setProperty("SNMPVERSION", "v1v2");
/*     */     }
/* 112 */     else if (snmpVersion.equalsIgnoreCase("v3"))
/*     */     {
/* 114 */       String snmpSecurityLevel = request.getParameter("snmpSecurityLevel");
/* 115 */       String snmpUserName = request.getParameter("snmpUserName");
/* 116 */       String snmpContextName = request.getParameter("snmpContextName");
/* 117 */       String snmpAuthProtocol = request.getParameter("snmpAuthProtocol");
/* 118 */       String snmpAuthPassword = request.getParameter("snmpAuthPassword");
/* 119 */       String snmpPrivPassword = request.getParameter("snmpPrivPassword");
/* 120 */       deviceProperties.setProperty("SNMPUserName", snmpUserName);
/* 121 */       deviceProperties.setProperty("contextName", snmpContextName);
/* 122 */       deviceProperties.setProperty("securityLevel", snmpSecurityLevel);
/* 123 */       deviceProperties.setProperty("authProtocol", snmpAuthProtocol);
/* 124 */       deviceProperties.setProperty("authPassword", snmpAuthPassword);
/* 125 */       deviceProperties.setProperty("privPassword", snmpPrivPassword);
/* 126 */       deviceProperties.setProperty("SNMPVERSION", "V3");
/*     */     }
/*     */     
/* 129 */     HashMap resultMap = DiagnosticsUtil.checkAgentRunningAndGetSystemValues(deviceProperties);
/* 130 */     boolean isSnmpRunning = Boolean.parseBoolean((String)resultMap.get("isRunning"));
/* 131 */     AMLog.debug("isSnmpRunning in TestCredentials ===>" + isSnmpRunning);
/* 132 */     String closeButton = "<div style=\"float:right; cursor:pointer;\" onclick=\"javascript:closeMessage('testCredentialResult');\"><img align=\"right\" src=\"images/deleteWidget.gif\"/>";
/* 133 */     if (!isSnmpRunning)
/*     */     {
/*     */       try
/*     */       {
/* 137 */         String errorMessage = DiagnosticsUtil.getReadableSNMPErrorMessage((String)resultMap.get("errorString"), snmpVersion);
/* 138 */         AMLog.debug("errorMessage ===> " + errorMessage);
/* 139 */         response.setContentType("text/html; charset=UTF-8");
/* 140 */         PrintWriter out = response.getWriter();
/* 141 */         String failedMsg = NmsUtil.GetString("Failed");
/* 142 */         out.println("<font color=red>" + failedMsg + " : " + errorMessage + " </font>" + closeButton);
/* 143 */         out.flush();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 147 */         ex.printStackTrace();
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/*     */       try
/*     */       {
/* 154 */         response.setContentType("text/html; charset=UTF-8");
/* 155 */         PrintWriter out = response.getWriter();
/* 156 */         String passedMsg = NmsUtil.GetString("Passed");
/* 157 */         out.println("<font color=green>" + passedMsg + "</font>" + closeButton);
/* 158 */         out.flush();
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 162 */         ex.printStackTrace();
/*     */       }
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */   
/*     */   public String getReadableSNMPErrorMessage(String errorMsgFromAgent, String snmpVersion)
/*     */   {
/* 170 */     AMLog.debug("errorMessage ==>" + errorMsgFromAgent);
/*     */     
/* 172 */     return DiagnosticsUtil.getReadableSNMPErrorMessage(errorMsgFromAgent, snmpVersion);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\TestCredentials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */