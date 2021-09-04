/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.fault.AMEventBuffer;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.adventnet.appmanager.server.framework.comm.HClient;
/*     */ import com.adventnet.appmanager.util.AppManagerStartUpUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.LoadFactorCalculator;
/*     */ import com.adventnet.appmanager.util.SWMgmtUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class MASRequestProcessor
/*     */   extends HttpServlet
/*     */ {
/*  28 */   private ServletContext servletContext = null;
/*  29 */   private ServletConfig config = null;
/*     */   
/*     */   public void init(ServletConfig sConfig) throws ServletException {
/*  32 */     super.init(sConfig);
/*  33 */     this.servletContext = sConfig.getServletContext();
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  39 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  44 */     String runningVersion = SWMgmtUtil.getRunningBuildNumber();
/*  45 */     String buildnumber = request.getParameter("bn");
/*  46 */     int remoteBuildNo = Integer.parseInt(buildnumber.trim());
/*  47 */     int localBuildNo = Integer.parseInt(runningVersion.trim());
/*  48 */     String archivingtable = request.getHeader("archiving-tables");
/*  49 */     String dbtype = request.getParameter("dbtype");
/*     */     
/*     */ 
/*  52 */     if (archivingtable == null) {
/*  53 */       archivingtable = request.getParameter("archiving-tables");
/*     */     }
/*     */     
/*     */ 
/*  57 */     System.out.println("ARCHIVING TABLE ============== " + archivingtable);
/*  58 */     String errorCode = "1000";
/*  59 */     response.setContentType("text/html; charset=UTF-8");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  77 */     EnterpriseUtil.setAdminServerStatus(true);
/*  78 */     String command = request.getParameter("command");
/*     */     
/*  80 */     System.out.println("application/octet-stream");
/*  81 */     Map qryStrMap = HClient.getQueryString(request);
/*  82 */     if ("SYNCH_AAM".equals(command)) {
/*  83 */       EnterpriseUtil.setMASRegisteredStatus(true);
/*  84 */       EnterpriseUtil.setAdminSyncEnableStatus(true);
/*  85 */       PrintWriter out = null;
/*     */       try {
/*  87 */         qryStrMap.put("archiving-tables", archivingtable);
/*  88 */         out = response.getWriter();
/*  89 */         errorCode = "1000";
/*  90 */         String serverID = request.getParameter("serverID");
/*  91 */         String serverType = request.getParameter("serverType");
/*  92 */         int syId = 0;
/*  93 */         if ((serverType != null) && (serverType.equals("PY")))
/*     */         {
/*  95 */           syId = Integer.parseInt(serverID) + 10000;
/*     */         }
/*  97 */         else if ((serverType != null) && (serverType.equals("SY")))
/*     */         {
/*  99 */           syId = Integer.parseInt(serverID) - 10000;
/*     */         }
/* 101 */         int monitorCount = Constants.getNoofMonitors_withoutnatives();
/* 102 */         CommDBUtil.syncSecServerStatus(out, syId);
/* 103 */         out.println("update AM_MAS_SERVER set STATE ='" + errorCode + "',LOADFACTOR='" + LoadFactorCalculator.getLoadFactor() + "',MONITORSCOUNT=" + monitorCount + " where ID ='" + serverID + "';");
/* 104 */         out.println("update AM_MAS_SERVER set LOADFACTOR='" + LoadFactorCalculator.getLoadFactor() + "',MONITORSCOUNT=" + monitorCount + " where ID ='" + syId + "';");
/* 105 */         if (dbtype != null) {
/* 106 */           CommDBUtil.admindbtype = dbtype;
/* 107 */           com.adventnet.appmanager.db.DBQueryUtil.admindbserver = dbtype;
/* 108 */           if (DBUtil.hasGlobalConfigValue("am.admin.dbtype")) {
/* 109 */             DBUtil.updateGlobalConfigValue("am.admin.dbtype", dbtype);
/*     */           } else {
/* 111 */             DBUtil.insertIntoGlobalConfig("am.admin.dbtype", dbtype);
/*     */           }
/*     */         }
/* 114 */         AMLog.debug("AdminServer DBType got in MASRequestProcessor --->" + dbtype);
/* 115 */         response.setHeader("MAS_PushModelSyncStatus", AMEventBuffer.getInstance().isEventSynchedInAdminServer() + "");
/* 116 */         response.setHeader("MAS_PushModelEnabledStatus", AMEventBuffer.getInstance().isPushModelSynchingEnabled() + "");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 123 */         if (remoteBuildNo >= localBuildNo)
/*     */         {
/* 125 */           CommDBUtil.synchSWDetails(out, qryStrMap);
/*     */         }
/* 127 */         String synchStatus = (String)qryStrMap.get("synchStatus");
/* 128 */         if (("ENABLED".equals(synchStatus)) && (remoteBuildNo >= localBuildNo))
/*     */         {
/* 130 */           CommDBUtil.synchDeletes(out);
/* 131 */           CommDBUtil.fetchDataForSynch(out, qryStrMap);
/*     */           
/*     */ 
/* 134 */           out.println("status=Command " + command + " processed successfully;");
/* 135 */           EnterpriseUtil.syncUpdates(out);
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/* 141 */             AMEventBuffer.getInstance().setEventSynchedInAdminServer(false);
/* 142 */             AMEventBuffer.getInstance().clearBuffer();
/*     */           } catch (Exception ex) {
/* 144 */             AMLog.debug("Enterprise : exception occurred while disabling event push. The possible reason is event buffer is null " + ex.getMessage());
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 150 */         CommDBUtil.synchHotfixDetails(out, qryStrMap);
/*     */         
/*     */ 
/* 153 */         AMLog.debug("Enterprise : Request SUCCESS command " + command + " QueryString " + qryStrMap);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 157 */         AMLog.debug("Enterprise : Error occured processing request dropped. Command " + command + " QueryString " + request.getQueryString());
/* 158 */         e.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/* 162 */         if (out != null)
/*     */         {
/* 164 */           out.close();
/*     */         }
/*     */       }
/*     */     }
/* 168 */     else if ("UnRegister_Me_MAS".equals(command))
/*     */     {
/*     */ 
/* 171 */       String adminSyncStatus = request.getParameter("adminSyncStatus");
/* 172 */       if (adminSyncStatus != null)
/*     */       {
/* 174 */         boolean isEnabled = Boolean.parseBoolean(adminSyncStatus);
/* 175 */         EnterpriseUtil.setAdminSyncEnableStatus(isEnabled);
/*     */         
/*     */ 
/* 178 */         AMEventBuffer.getInstance().setEventSynchedInAdminServer(false);
/* 179 */         AMEventBuffer.getInstance().clearBuffer();
/*     */       }
/* 181 */       String unregisterMAS = request.getParameter("unregisterMAS");
/* 182 */       AMLog.debug("unregisterMAS :: " + unregisterMAS);
/* 183 */       if ((unregisterMAS != null) && (unregisterMAS.equals("true")))
/*     */       {
/* 185 */         EnterpriseUtil.setMASRegisteredStatus(false);
/* 186 */         AMEventBuffer.getInstance().setEventSynchedInAdminServer(false);
/* 187 */         AMEventBuffer.getInstance().clearBuffer();
/* 188 */         EnterpriseUtil.setAdminSyncEnableStatus(false);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/* 193 */       if ((EnterpriseUtil.isManagedServer()) && (AMAutomaticPortChanger.isSsoEnabled()) && ("false".equals(System.getProperty("adminConnected")))) {
/* 194 */         AMLog.debug("[SSO Restart] MAS Running in non-sso mode. Hence restarting");
/*     */         
/* 196 */         AppManagerStartUpUtil.getInstance().restartAM();
/*     */       }
/*     */     }
/*     */     catch (Exception exc) {
/* 200 */       exc.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void destroy()
/*     */   {
/* 209 */     this.servletContext.removeAttribute("availabilitykeys");
/* 210 */     this.servletContext.removeAttribute("healthkeys");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\MASRequestProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */