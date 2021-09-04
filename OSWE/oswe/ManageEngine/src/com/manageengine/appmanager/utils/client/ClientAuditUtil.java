/*     */ package com.manageengine.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.MyThreadLocal;
/*     */ import com.me.apm.server.audit.AuditUtil;
/*     */ import java.security.Principal;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientAuditUtil
/*     */ {
/*     */   public String getBrowserNameFromUserAgent(String userAgent)
/*     */   {
/*  24 */     String browserName = "-";
/*     */     try {
/*  26 */       userAgent = userAgent.toLowerCase();
/*  27 */       if (userAgent.indexOf("chrome") != -1) {
/*  28 */         browserName = "Chrome";
/*     */       }
/*  30 */       else if (userAgent.indexOf("chromium") != -1) {
/*  31 */         browserName = "Chromium";
/*     */       }
/*  33 */       else if (userAgent.indexOf("firefox") != -1) {
/*  34 */         browserName = "Firefox";
/*     */       }
/*  36 */       else if (userAgent.indexOf("safari") != -1) {
/*  37 */         browserName = "Safari";
/*     */       }
/*  39 */       else if ((userAgent.indexOf("opr") != -1) || (userAgent.indexOf("opera") != -1)) {
/*  40 */         browserName = "Opera";
/*     */       }
/*  42 */       else if (userAgent.indexOf("msie") != -1) {
/*  43 */         browserName = "IE";
/*     */       }
/*     */       else {
/*  46 */         browserName = "User";
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  50 */       e.printStackTrace();
/*     */     }
/*  52 */     return browserName;
/*     */   }
/*     */   
/*     */   public Map<String, String> getDetailsFromRequest(HttpServletRequest request) {
/*  56 */     Map<String, String> toReturn = new HashMap();
/*  57 */     String apikey = "-";
/*  58 */     String user = "-";
/*  59 */     String operationDoneFrom = "-";
/*  60 */     String host = "-";
/*  61 */     String useADDM = "-";
/*     */     try {
/*  63 */       apikey = request.getParameter("apikey");
/*  64 */       useADDM = request.getParameter("useADDM");
/*     */       
/*  66 */       if (apikey != null) {
/*     */         try {
/*  68 */           operationDoneFrom = "RESTAPI";
/*  69 */           user = (String)Constants.getUserDetailsForAPIKey(apikey).get("USERNAME");
/*     */         }
/*     */         catch (Exception e) {
/*  72 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/*     */         try
/*     */         {
/*  77 */           String userAgent = "";
/*     */           try {
/*  79 */             user = request.getUserPrincipal().getName();
/*     */           }
/*     */           catch (Exception e) {
/*  82 */             AMLog.debug("ClientAuditUtil:user detail not vailable in request. So accessing threadlocal");
/*  83 */             user = MyThreadLocal.getUserName();
/*     */           }
/*     */           try {
/*  86 */             userAgent = request.getHeader("User-Agent");
/*     */           }
/*     */           catch (Exception e) {
/*  89 */             AMLog.debug("ClientAuditUtil:userAgent detail not vailable in request. So accessing threadlocal");
/*  90 */             userAgent = MyThreadLocal.getUserAgent();
/*     */           }
/*  92 */           if (userAgent != null) {
/*  93 */             operationDoneFrom = getBrowserNameFromUserAgent(userAgent);
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  97 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 102 */         host = request.getParameter("host");
/* 103 */         if (host == null) {
/* 104 */           host = request.getRemoteAddr();
/*     */         }
/*     */       } catch (Exception e) {
/* 107 */         AMLog.debug("ClientAuditUtil:Host detail not vailable in request. So accessing threadlocal");
/* 108 */         host = MyThreadLocal.getHostName();
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 112 */       ex.printStackTrace();
/*     */     }
/* 114 */     toReturn.put("username", user != null ? user : "-");
/* 115 */     toReturn.put("hostname", host != null ? host : "-");
/* 116 */     toReturn.put("operationDoneFrom", operationDoneFrom != null ? operationDoneFrom : "-");
/*     */     
/* 118 */     return toReturn;
/*     */   }
/*     */   
/*     */ 
/*     */   public void addToAuditLog(HttpServletRequest request, int haid, int operation, String auditLogMsg)
/*     */   {
/* 124 */     Map<String, String> toInsert = new HashMap();
/*     */     try {
/* 126 */       toInsert.putAll(getDetailsFromRequest(request));
/* 127 */       toInsert.put("resourceid", Integer.toString(haid));
/* 128 */       toInsert.put("description", auditLogMsg);
/* 129 */       toInsert.put("operation", Integer.toString(operation));
/* 130 */       AuditUtil auditUtil = new AuditUtil();
/* 131 */       auditUtil.insertAuditLog(toInsert);
/*     */     }
/*     */     catch (Exception e) {
/* 134 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JSONObject getResourceTypeAndNameFromID(List<String> idList)
/*     */   {
/* 142 */     String ids = idList.toString();
/* 143 */     ids = ids.substring(1, ids.length() - 1);
/* 144 */     JSONObject json = new JSONObject();
/* 145 */     String query = "Select RESOURCEID, RESOURCENAME, TYPE FROM AM_MANAGEDOBJECT where RESOURCEID in (" + ids + ")";
/* 146 */     ResultSet rs = null;
/*     */     try {
/* 148 */       rs = AMConnectionPool.executeQueryStmt(query);
/*     */       
/* 150 */       while (rs.next()) {
/* 151 */         String rsId = rs.getString("RESOURCEID");
/* 152 */         String rsName = rs.getString("RESOURCENAME");
/* 153 */         String rsType = rs.getString("TYPE");
/* 154 */         JSONObject temp = new JSONObject();
/* 155 */         temp.put("resourceName", rsName);
/* 156 */         temp.put("type", rsType);
/* 157 */         json.put(rsId, temp);
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 161 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 164 */       AMConnectionPool.closeResultSet(rs);
/*     */     }
/* 166 */     return json;
/*     */   }
/*     */   
/*     */   public void setDetailsForAuditInThreadLocal(HttpServletRequest request)
/*     */   {
/*     */     try {
/* 172 */       if (request != null) {
/* 173 */         String hostName = request.getRemoteHost();
/* 174 */         MyThreadLocal.setHostName(hostName);
/* 175 */         String userName = request.getUserPrincipal().getName();
/* 176 */         MyThreadLocal.setUserName(userName);
/* 177 */         String userAgent = request.getHeader("User-Agent");
/* 178 */         MyThreadLocal.setUserAgent(userAgent);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 183 */       AMLog.debug("ClientAuditUtil:Exception in setDetailsForAuditInThreadLocal");
/* 184 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\utils\client\ClientAuditUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */