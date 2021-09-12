/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import HTTPClient.HTTPConnection;
/*     */ import HTTPClient.HTTPResponse;
/*     */ import HTTPClient.NVPair;
/*     */ import HTTPClient.URI;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.filter.ExtProdIntegFilter;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.ExtProdUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.net.UnknownHostException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShowNetworkDeviceDetails
/*     */   extends DispatchAction
/*     */ {
/*  42 */   private static boolean opmCentralInteg = false;
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
/*     */   public ActionForward showNWDSnapshot(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  60 */     String prodName = "OpManager";
/*  61 */     String errorMessage = null;
/*     */     try
/*     */     {
/*  64 */       String resId = request.getParameter("resId");
/*     */       
/*  66 */       int resIdInt = Integer.parseInt(resId);
/*     */       
/*  68 */       String resName = ExtProdUtil.getResName(resId);
/*     */       
/*  70 */       String resType = ExtProdUtil.getResType(resId);
/*     */       
/*  72 */       int masId = EnterpriseUtil.getManagedServerIndex(Integer.parseInt(resId));
/*     */       
/*  74 */       Properties extDetails = ExtProdUtil.getExtProductInfoUsingMasId(masId + "", "OpManager");
/*  75 */       String protocol = extDetails.getProperty("protocol");
/*  76 */       String host = extDetails.getProperty("host");
/*  77 */       String port = extDetails.getProperty("port");
/*  78 */       String apiKey = extDetails.getProperty("apiKey");
/*  79 */       String userName = extDetails.getProperty("username");
/*  80 */       String password = extDetails.getProperty("password");
/*  81 */       String prodId = extDetails.getProperty("ID");
/*     */       
/*  83 */       String parentName = ExtProdUtil.getRouterNameOfInterface(resName);
/*  84 */       String parentResId = ExtProdUtil.getResId(parentName, Integer.parseInt(prodId));
/*  85 */       String parentType = ExtProdUtil.getResType(parentResId);
/*  86 */       HashMap deviceLink = IntegProdDBUtil.getExtDeviceLinkForResourceId(parentResId);
/*  87 */       String parentLink = (String)deviceLink.get(parentResId);
/*     */       
/*  89 */       String jumpToApi = "/apiclient/ember/index.jsp#/";
/*     */       
/*  91 */       if (resType.indexOf("Interface") != -1)
/*     */       {
/*  93 */         jumpToApi = jumpToApi + "Inventory/Interface/" + resName + "/Summary";
/*     */       }
/*     */       else
/*     */       {
/*  97 */         jumpToApi = jumpToApi + "Inventory/Device/" + resName + "/Summary";
/*     */       }
/*     */       
/* 100 */       String urlToContact = protocol + "://" + host + ":" + port;
/*     */       
/* 102 */       if (!EnterpriseUtil.isAdminServer())
/*     */       {
/* 104 */         if (apiKey == null)
/*     */         {
/* 106 */           apiKey = getAPIKeyFromOPM(urlToContact, userName, password, request, response);
/*     */         }
/* 108 */         if ((apiKey != null) && (!apiKey.equals("")))
/*     */         {
/* 110 */           apiKey = validateAndGenerateApiKey(urlToContact, apiKey, userName, password, request, response);
/* 111 */           if ((apiKey != null) && (apiKey.equals("Connection refused")))
/*     */           {
/* 113 */             errorMessage = getErrorMessage(prodName, response, apiKey);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 118 */           errorMessage = getErrorMessage(prodName, response, null);
/*     */         }
/*     */         
/*     */ 
/* 122 */         String devSum = getDeviceSummary(resName, urlToContact, apiKey, resType, parentType, errorMessage);
/* 123 */         String eventList = getAlarmList(urlToContact, apiKey, resType, errorMessage, resName, resId);
/*     */         
/* 125 */         request.setAttribute("deviceSummary", devSum);
/*     */         
/* 127 */         if ((eventList != null) && (!eventList.contains("error")))
/*     */         {
/* 129 */           request.setAttribute("eventList", eventList);
/*     */         }
/* 131 */         request.setAttribute("resName", resName);
/* 132 */         request.setAttribute("resType", resType);
/*     */         
/* 134 */         request.setAttribute("jumpToApi", jumpToApi);
/*     */         
/* 136 */         if (resType.indexOf("Interface") != -1)
/*     */         {
/* 138 */           request.setAttribute("parentName", parentName);
/* 139 */           request.setAttribute("parentLink", parentLink);
/* 140 */           request.setAttribute("parentType", parentType);
/*     */         }
/* 142 */         request.setAttribute("urlToContact", urlToContact);
/* 143 */         request.setAttribute("errorMessage", errorMessage);
/*     */         
/* 145 */         String forwardUrl = "/jsp/APMNetworkSnapshot.jsp?&resId=" + resId;
/* 146 */         return new ActionForward(forwardUrl);
/*     */       }
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 151 */       ExtProdIntegFilter.writeErrorMessage(prodName, response, FormatUtil.getString("am.mypage.adminactions.noauthorization.text"));
/* 152 */       nfe.printStackTrace();
/* 153 */       return null;
/*     */     }
/* 155 */     return null;
/*     */   }
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
/*     */   public static String getAPIKeyFromOPM(String urlToContact, String username, String password, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 169 */     String apiKey = null;
/*     */     try
/*     */     {
/* 172 */       String url = urlToContact + "/mobileNativeLogin";
/* 173 */       URI uri = new URI(url);
/* 174 */       HTTPConnection con = new HTTPConnection(uri);
/* 175 */       NVPair nvpairP = new NVPair("Pragma", "no-cache");
/* 176 */       NVPair nvpair1 = new NVPair("clienttype", "html");
/* 177 */       NVPair nvPair2 = new NVPair("userName", username);
/* 178 */       NVPair nvPair3 = new NVPair("password", password);
/* 179 */       NVPair[] headersP = { nvpairP, nvpair1, nvPair2, nvPair3 };
/* 180 */       HTTPResponse rsp1 = con.Post(url, headersP);
/*     */       
/* 182 */       byte[] b = rsp1.getData();
/* 183 */       String s = new String(b);
/*     */       
/* 185 */       JSONObject json1 = null;
/*     */       
/* 187 */       if (s.indexOf("error") == -1)
/*     */       {
/* 189 */         json1 = new JSONObject(s);
/* 190 */         JSONObject ipauth = json1.getJSONObject("IphoneAuth");
/* 191 */         JSONObject details = ipauth.getJSONObject("Details");
/* 192 */         apiKey = (String)details.get("apiKey");
/*     */         
/* 194 */         String updateApiKey = "update AM_INTEGRATEDPRODUCTS set APIKEY='" + apiKey + "' where PRODUCT_NAME='OpManager'";
/* 195 */         AMConnectionPool.executeUpdateStmt(updateApiKey);
/* 196 */         if (EnterpriseUtil.isManagedServer())
/*     */         {
/* 198 */           EnterpriseUtil.addUpdateQueryToFile(updateApiKey);
/*     */         }
/*     */         
/*     */       }
/* 202 */       else if ((s.indexOf("error") != -1) && (s.indexOf("Authentication failed") != -1))
/*     */       {
/* 204 */         apiKey = getAPIKeyFromOPM(urlToContact, username, password, request, response);
/*     */       }
/*     */     }
/*     */     catch (JSONException e)
/*     */     {
/* 209 */       e.printStackTrace();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 213 */       e.printStackTrace();
/*     */     }
/* 215 */     return apiKey;
/*     */   }
/*     */   
/*     */   public static String validateAndGenerateApiKey(String urlToContact, String apiKey, String username, String password, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 220 */     String apiKeyNew = apiKey;
/*     */     try {
/* 222 */       String url = urlToContact + "/api/json/admin/listUsers?apiKey=" + apiKey;
/* 223 */       URI uri = new URI(url);
/* 224 */       HTTPConnection con = new HTTPConnection(uri);
/* 225 */       NVPair nvpairP = new NVPair("Pragma", "no-cache");
/* 226 */       NVPair[] headersP = { nvpairP };
/* 227 */       HTTPResponse rsp1 = con.Post(url, headersP);
/*     */       
/* 229 */       byte[] b = rsp1.getData();
/* 230 */       String s = new String(b);
/*     */       
/* 232 */       if ((s.indexOf("error") != -1) && (s.indexOf("Authentication failed") != -1))
/*     */       {
/* 234 */         apiKeyNew = getAPIKeyFromOPM(urlToContact, username, password, request, response);
/*     */       }
/*     */     } catch (Exception e) {
/* 237 */       e.printStackTrace();
/* 238 */       if ((e.getMessage() != null) && ((e.getMessage().indexOf("Connection refused") != -1) || ((e instanceof UnknownHostException))))
/*     */       {
/* 240 */         return "Connection refused";
/*     */       }
/*     */     }
/* 243 */     return apiKeyNew;
/*     */   }
/*     */   
/*     */   public static String getDeviceSummary(String resName, String urlToContact, String apiKey, String resType, String parentType, String errorMessage)
/*     */   {
/* 248 */     String devJSON = null;
/*     */     try
/*     */     {
/* 251 */       if ((errorMessage != null) && (!errorMessage.equals("")))
/*     */       {
/* 253 */         return getDeviceDetailsFromDB(resName, resType, parentType);
/*     */       }
/*     */       
/* 256 */       if (!opmCentralInteg)
/*     */       {
/* 258 */         String devSummaryUrl = urlToContact + "/api/json/device/getDeviceSummary?apiKey=" + apiKey + "&name=" + resName;
/* 259 */         if (resType.indexOf("Interface") != -1)
/*     */         {
/* 261 */           devSummaryUrl = urlToContact + "/api/json/device/getInterfaceSummary?apiKey=" + apiKey + "&interfaceName=" + resName;
/*     */         }
/* 263 */         HTTPResponse resp = getRequest(devSummaryUrl);
/* 264 */         byte[] b = resp.getData();
/* 265 */         devJSON = new String(b);
/* 266 */         if (devJSON.indexOf("error") != -1)
/*     */         {
/* 268 */           if (devJSON.indexOf("This operation is blocked in Central Server") != -1)
/*     */           {
/* 270 */             opmCentralInteg = true;
/* 271 */             devJSON = getDeviceDetailsFromDB(resName, resType, parentType);
/* 272 */             AMLog.debug("opmCentralInteg: first time: " + opmCentralInteg);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 278 */         devJSON = getDeviceDetailsFromDB(resName, resType, parentType);
/* 279 */         AMLog.debug("opmCentralInteg: not the first time: " + opmCentralInteg);
/*     */       }
/*     */     } catch (Exception e) {
/* 282 */       e.printStackTrace();
/*     */     }
/* 284 */     return devJSON;
/*     */   }
/*     */   
/*     */ 
/*     */   public static String getAlarmList(String urlToContact, String apiKey, String resType, String errorMessage, String resName, String resId)
/*     */   {
/* 290 */     String eventJSON = null;
/* 291 */     if (resType.contains("Interface"))
/*     */     {
/* 293 */       return "";
/*     */     }
/*     */     try
/*     */     {
/* 297 */       if ((errorMessage == null) || (errorMessage.equals("")))
/*     */       {
/* 299 */         String eventAlarmLink = urlToContact + "/api/json/alarm/getAlarmList?apiKey=" + apiKey;
/* 300 */         HTTPResponse resp = getRequest(eventAlarmLink);
/*     */         
/* 302 */         byte[] b = resp.getData();
/* 303 */         eventJSON = new String(b);
/*     */       }
/*     */       else
/*     */       {
/* 307 */         eventJSON = getLatestHealthAlarmFromDB(resName, resId);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 311 */       e.printStackTrace();
/*     */     }
/* 313 */     return eventJSON;
/*     */   }
/*     */   
/*     */   public static String getLatestHealthAlarmFromDB(String resName, String resId)
/*     */   {
/* 318 */     String eventJSON = null;
/*     */     try
/*     */     {
/* 321 */       int healthAttrId = Constants.getHealthIDforResourceID(resId);
/*     */       
/* 323 */       HashMap<String, String> latestEvent = FaultUtil.getLatestEvent(resId + "_" + healthAttrId);
/*     */       
/* 325 */       JSONArray alarmList = new JSONArray();
/* 326 */       JSONObject alarmObject = new JSONObject();
/*     */       
/* 328 */       alarmObject.put("source", resName);
/* 329 */       alarmObject.put("message", latestEvent.get("MESSAGE"));
/*     */       
/* 331 */       Date date = new Date(System.currentTimeMillis());
/* 332 */       String modTimeStr = date.toString();
/*     */       
/* 334 */       alarmObject.put("modTimeStr", modTimeStr);
/* 335 */       alarmObject.put("status", latestEvent.get("SEVERITY"));
/*     */       
/* 337 */       alarmList.put(alarmObject);
/*     */       
/* 339 */       JSONObject alarmDetails = new JSONObject();
/* 340 */       alarmDetails.put("Details", alarmList);
/* 341 */       JSONObject dummyEventJSON = new JSONObject();
/* 342 */       dummyEventJSON.put("Alarm", alarmDetails);
/*     */       
/* 344 */       eventJSON = dummyEventJSON.toString();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 348 */       e.printStackTrace();
/*     */     }
/* 350 */     return eventJSON;
/*     */   }
/*     */   
/*     */   public static String getDeviceDetailsFromDB(String resName, String resType, String parentType)
/*     */   {
/* 355 */     String devJSON = "";
/* 356 */     JSONObject newJSON = new JSONObject();
/* 357 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 360 */       rs = AMConnectionPool.executeQueryStmt("select ext.NAME, ext.DISPLAYNAME, ext.CATEGORY,ext.TYPE, mo.DISPLAYNAME,ext.IPADDRESS from ExternalDeviceDetails ext inner join AM_ManagedObject mo on ext.NAME=mo.RESOURCENAME where ext.NAME = '" + resName + "'");
/* 361 */       if (rs.next())
/*     */       {
/* 363 */         String category = rs.getString("CATEGORY");
/* 364 */         if ((category != null) && (!category.equals("")))
/*     */         {
/* 366 */           int startindex = category.indexOf("OpManager-");
/* 367 */           category = category.substring(startindex + 10);
/*     */         }
/*     */         
/* 370 */         if (category.indexOf("Interface") != -1)
/*     */         {
/* 372 */           category = parentType;
/* 373 */           newJSON.append("name", rs.getString("IPADDRESS"));
/*     */         }
/* 375 */         newJSON.append("category", category);
/* 376 */         newJSON.append("displayName", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 377 */         newJSON.append("ipAddress", rs.getString("IPADDRESS"));
/* 378 */         newJSON.append("type", ExtProdUtil.removeProdNameAndProbeId(rs.getString("TYPE")));
/*     */       }
/* 380 */       devJSON = newJSON.toString();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 384 */       e.printStackTrace();
/* 385 */       String error = e.getMessage();
/* 386 */       devJSON = "error:" + error;
/*     */     }
/*     */     finally
/*     */     {
/* 390 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/* 393 */     return devJSON;
/*     */   }
/*     */   
/*     */   public static String getErrorMessage(String prodName, HttpServletResponse hresponse, String errorString)
/*     */   {
/* 398 */     String errorMessage = null;
/*     */     try
/*     */     {
/* 401 */       if ((errorString == null) || ((errorString != null) && (errorString.equals(""))))
/*     */       {
/* 403 */         errorMessage = FormatUtil.getString("opmConnector.minimal.ui.otherError");
/*     */       }
/* 405 */       if (errorString.equals("Connection refused"))
/*     */       {
/* 407 */         errorMessage = FormatUtil.getString("opmConnector.minimal.ui.opmDown", new String[] { prodName });
/*     */       }
/* 409 */       else if (errorString.equals("Incorrect APIKey"))
/*     */       {
/* 411 */         errorMessage = FormatUtil.getString("opmConnector.minimal.ui.updateCredentials", new String[] { prodName });
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 416 */       e.printStackTrace();
/*     */     }
/* 418 */     return errorMessage;
/*     */   }
/*     */   
/*     */   public static HTTPResponse getRequest(String url) throws Exception
/*     */   {
/* 423 */     URI uri = new URI(url);
/* 424 */     HTTPConnection con = new HTTPConnection(uri);
/* 425 */     NVPair[] headersP = new NVPair[0];
/* 426 */     HTTPResponse rsp1 = con.Get(url, headersP);
/* 427 */     return rsp1;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowNetworkDeviceDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */