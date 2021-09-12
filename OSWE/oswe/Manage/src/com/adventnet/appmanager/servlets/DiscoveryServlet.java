/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.discovery.DiscoveryUtil;
/*     */ import com.adventnet.appmanager.struts.actions.NewDiscoveryAction;
/*     */ import com.adventnet.appmanager.utils.client.CommonAPIUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ public class DiscoveryServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*     */     try
/*     */     {
/*  28 */       process(req, resp, true);
/*     */     }
/*     */     catch (Exception e) {
/*  31 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*     */     try
/*     */     {
/*  39 */       process(req, resp, false);
/*     */     }
/*     */     catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void process(HttpServletRequest request, HttpServletResponse resp, boolean isGet) throws Exception {
/*  47 */     String uri = null;
/*  48 */     String method = null;
/*  49 */     String[] nodes = null;
/*     */     try {
/*  51 */       resp.setCharacterEncoding("UTF-8");
/*  52 */       String networkDetailsStr = request.getParameter("networkDetails");
/*     */       
/*  54 */       networkDetailsStr = networkDetailsStr.replaceAll("&quot;", "\"");
/*  55 */       JSONObject networkDetails = new JSONObject(networkDetailsStr);
/*     */       
/*  57 */       String apikey = (String)networkDetails.get("apikey");
/*  58 */       if (validateAPIKey(apikey))
/*     */       {
/*  60 */         AMLog.debug("REST API : Validation true");
/*     */         
/*  62 */         uri = request.getRequestURI();
/*  63 */         method = getMethodFromURI(uri);
/*  64 */         if ("startDiscovery".equalsIgnoreCase(method))
/*     */         {
/*  66 */           discoverDevices(request, resp, networkDetails);
/*  67 */         } else if ("getDiscoveryStatusDetails".equalsIgnoreCase(method))
/*     */         {
/*  69 */           getDiscoveryStatus(request, resp, networkDetails);
/*     */ 
/*     */         }
/*  72 */         else if ("deleteDiscoveryDetails".equalsIgnoreCase(method))
/*     */         {
/*  74 */           String id = networkDetails.get("discoveryID").toString();
/*  75 */           deleteDiscoveryDetails(Long.parseLong(id), resp);
/*     */         }
/*  77 */         else if ("startRediscovery".equalsIgnoreCase(method))
/*     */         {
/*  79 */           startRediscovery(request, resp, networkDetails);
/*     */         }
/*  81 */         else if ("stopDiscovery".equalsIgnoreCase(method))
/*     */         {
/*  83 */           String discID = networkDetails.getString("discoveryID").toString();
/*  84 */           stopDiscovery(Long.parseLong(discID), resp);
/*     */         }
/*  86 */         else if ("disableScheduleRediscovery".equalsIgnoreCase(method))
/*     */         {
/*     */ 
/*  89 */           disableScheduledRediscovery(networkDetails, resp);
/*     */         }
/*  91 */         else if ("scheduleRediscovery".equalsIgnoreCase(method))
/*     */         {
/*     */ 
/*  94 */           scheduleRediscovery(networkDetails, resp);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (JSONException jsone)
/*     */     {
/* 100 */       jsone.printStackTrace();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private String getMethodFromURI(String uri) {
/* 109 */     String patternStr = "(/AppManager/Discovery/)(.*)";
/* 110 */     String method = null;
/* 111 */     Pattern pattern = Pattern.compile(patternStr);
/* 112 */     Matcher matcher = pattern.matcher(uri);
/* 113 */     while (matcher.find())
/*     */     {
/* 115 */       method = matcher.group(2);
/*     */     }
/*     */     
/* 118 */     return method;
/*     */   }
/*     */   
/*     */   public static void startRediscovery(HttpServletRequest request, HttpServletResponse response, JSONObject networkDetails) throws Exception {
/* 122 */     NewDiscoveryAction obj = new NewDiscoveryAction();
/*     */     
/* 124 */     long discoveryID = networkDetails.getLong("discoveryID");
/* 125 */     obj.callStartDiscovery(networkDetails, discoveryID, response);
/*     */   }
/*     */   
/*     */   public static void deleteDiscoveryDetails(long id, HttpServletResponse response) {
/* 129 */     NewDiscoveryAction obj = new NewDiscoveryAction();
/* 130 */     obj.deleteDiscoveryDetails(Long.valueOf(id), response);
/*     */   }
/*     */   
/*     */   public static void stopDiscovery(long id, HttpServletResponse response) {
/* 134 */     AMLog.logDiscoveryInfo("Inside stopDiscovery..id::" + id);
/* 135 */     DiscoveryUtil discoveryUtil = new DiscoveryUtil();
/* 136 */     boolean isStopped = discoveryUtil.stopDiscovery(id);
/* 137 */     response.setContentType("text/html; charset=UTF-8");
/*     */     
/*     */     try
/*     */     {
/* 141 */       PrintWriter out = response.getWriter();
/* 142 */       out.println(String.valueOf(isStopped));
/* 143 */       out.flush();
/*     */     }
/*     */     catch (IOException e) {
/* 146 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void disableScheduledRediscovery(JSONObject json, HttpServletResponse response) {
/* 151 */     NewDiscoveryAction obj = new NewDiscoveryAction();
/*     */     try {
/* 153 */       JSONObject scheduleTimeDetails = json.getJSONObject("scheduleTimeDetails");
/* 154 */       long discoveryID = json.getLong("discoveryID");
/* 155 */       obj.disableScheduleDiscovery(Long.valueOf(discoveryID), scheduleTimeDetails, response);
/*     */     }
/*     */     catch (JSONException e)
/*     */     {
/* 159 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void scheduleRediscovery(JSONObject json, HttpServletResponse response)
/*     */   {
/* 165 */     NewDiscoveryAction obj = new NewDiscoveryAction();
/*     */     try {
/* 167 */       JSONObject scheduleTimeDetails = json.getJSONObject("scheduleTimeDetails");
/* 168 */       obj.ScheduleDiscovery(scheduleTimeDetails, response);
/*     */     }
/*     */     catch (JSONException e)
/*     */     {
/* 172 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void discoverDevices(HttpServletRequest request, HttpServletResponse response, JSONObject networkDetails) throws Exception
/*     */   {
/* 178 */     DiscoveryUtil discoveryUtil = new DiscoveryUtil();
/* 179 */     NewDiscoveryAction obj = new NewDiscoveryAction();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 189 */       long discoveryID = discoveryUtil.insertDiscoveryDetailsToDB(networkDetails);
/*     */       
/* 191 */       if (discoveryID != -1L) {
/* 192 */         obj.callStartDiscovery(networkDetails, discoveryID, response);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 197 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void getDiscoveryStatus(HttpServletRequest request, HttpServletResponse response, JSONObject networkDetails)
/*     */     throws IOException
/*     */   {
/* 206 */     String outputString = "";
/* 207 */     NewDiscoveryAction discAction = new NewDiscoveryAction();
/*     */     try {
/* 209 */       discAction.getDiscoveryStatusMsgInMAS(networkDetails, response);
/*     */     }
/*     */     catch (Exception e) {
/* 212 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean validateAPIKey(String apiKey)
/*     */   {
/* 220 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 223 */       if (apiKey != null) {
/*     */         boolean bool;
/* 225 */         if (CommonAPIUtil.apikeys.containsKey(apiKey))
/*     */         {
/* 227 */           return true;
/*     */         }
/*     */         
/*     */ 
/* 231 */         rs = AMConnectionPool.executeQueryStmt("select * from AM_UserPasswordTable where APIKEY = '" + apiKey + "'");
/* 232 */         if (rs.next())
/*     */         {
/* 234 */           AMLog.info("REST API : Inside validate API Key method for key:" + rs.getString("APIKEY"));
/* 235 */           return true;
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 242 */       ex.printStackTrace();
/* 243 */       AMLog.audit("validateAPIKey ------->" + ex.getMessage());
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 248 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 250 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\DiscoveryServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */