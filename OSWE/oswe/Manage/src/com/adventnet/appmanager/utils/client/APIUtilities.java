/*    */ package com.adventnet.appmanager.utils.client;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class APIUtilities
/*    */ {
/*    */   public static String emptyParameterResponse(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName)
/*    */   {
/* 14 */     String outputString = null;
/*    */     try {
/* 16 */       AMLog.debug("REST API : " + apiName + " : " + parameterName + " should be mentioned");
/* 17 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { parameterName }), "4100");
/*    */     }
/*    */     catch (Exception ex) {
/* 20 */       ex.printStackTrace();
/*    */     }
/* 22 */     return outputString;
/*    */   }
/*    */   
/*    */   public static String duplicateNameResponse(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName)
/*    */   {
/* 27 */     String outputString = null;
/*    */     try {
/* 29 */       AMLog.debug("REST API : " + apiName + ": " + parameterName + "  already exists");
/* 30 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.parameter.value.exists.text", new String[] { parameterName }), "4101");
/*    */     }
/*    */     catch (Exception ex) {
/* 33 */       ex.printStackTrace();
/*    */     }
/* 35 */     return outputString;
/*    */   }
/*    */   
/*    */   public static String defaultIntegerResponse(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName) {
/* 39 */     String outputString = null;
/*    */     try {
/* 41 */       AMLog.debug("REST API : " + apiName + " : " + parameterName + " should be integer");
/* 42 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { parameterName }), "4103");
/*    */     }
/*    */     catch (Exception ex) {
/* 45 */       ex.printStackTrace();
/*    */     }
/* 47 */     return outputString;
/*    */   }
/*    */   
/*    */   public static String wrongBusinessHourValue(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName) {
/* 51 */     String outputString = null;
/*    */     try {
/* 53 */       AMLog.debug("REST API : " + apiName + " : " + parameterName + " should be between 0 to 24");
/* 54 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.hourvalue.wrong.text", new String[] { parameterName }), "4102");
/*    */     }
/*    */     catch (Exception ex) {
/* 57 */       ex.printStackTrace();
/*    */     }
/* 59 */     return outputString;
/*    */   }
/*    */   
/*    */   public static String wrongBusinessMinuteValue(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName) {
/* 63 */     String outputString = null;
/*    */     try {
/* 65 */       AMLog.debug("REST API : " + apiName + " : " + parameterName + " should be between 0 to 60");
/* 66 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.businesshour.minutevalue.wrong.text", new String[] { parameterName }), "4102");
/*    */     }
/*    */     catch (Exception ex) {
/* 69 */       ex.printStackTrace();
/*    */     }
/* 71 */     return outputString;
/*    */   }
/*    */   
/*    */   public static String wrongNameResponse(HttpServletRequest request, HttpServletResponse response, String apiName, String parameterName) {
/* 75 */     String outputString = null;
/*    */     try {
/* 77 */       AMLog.debug("REST API : " + apiName + " : " + parameterName + " does not exist ");
/* 78 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.wrongname.text", new String[] { parameterName }), "4106");
/*    */     }
/*    */     catch (Exception ex) {
/* 81 */       ex.printStackTrace();
/*    */     }
/* 83 */     return outputString;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\APIUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */