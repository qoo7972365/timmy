/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CredentialAPIUtil
/*     */ {
/*  26 */   static CredentialManagerUtil credUtil = ;
/*     */   
/*     */   public static String credentialAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) {
/*  29 */     response.setContentType("text/xml; charset=UTF-8");
/*  30 */     String outputString = "";
/*     */     try
/*     */     {
/*  33 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*  34 */       if (request.getMethod().equals("GET")) {
/*  35 */         outputString = listCredential(request, response, isJsonFormat);
/*     */       } else {
/*  37 */         return credentialOperations(request, response, isJsonFormat);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  42 */       e.printStackTrace();
/*     */     }
/*  44 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String credentialOperations(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*     */   {
/*  49 */     String outputString = "";
/*     */     try
/*     */     {
/*  52 */       if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*     */       {
/*  54 */         return deleteCredential(request, response);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  60 */       boolean exists = false;
/*  61 */       String credentialID = request.getParameter("credentialID");
/*  62 */       if ((credentialID != null) && (credentialID.trim().length() > 0)) {
/*  63 */         exists = DBQueryUtil.checkForDuplicateEntry("CREDENTIALMANAGER", "CREDENTIALID", credentialID, "");
/*     */       }
/*  65 */       if (!exists)
/*     */       {
/*  67 */         outputString = addCredential(request, response);
/*     */       }
/*     */       else {
/*  70 */         outputString = updateCredential(request, response);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  75 */       e.printStackTrace();
/*     */     }
/*  77 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String addCredential(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  81 */     String outputString = null;
/*  82 */     String credentialType = request.getParameter("type");
/*  83 */     String credentialName = request.getParameter("credentialName");
/*  84 */     String credentialID = request.getParameter("credentialID");
/*  85 */     String credentialDescription = "Test";
/*  86 */     HashMap credentialMapToAdd = new HashMap();
/*  87 */     boolean isAPICallFromAAM = (request.getParameter("apicallfrom") != null) && (request.getParameter("apicallfrom").equals("admin"));
/*     */     
/*     */     try
/*     */     {
/*  91 */       if ((credentialType == null) || (credentialType.trim().equals(""))) {
/*  92 */         AMLog.debug("REST API : The type should not be empty.");
/*  93 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.emptymessage"), "4202");
/*     */       }
/*     */       
/*  96 */       if (!"admin".equals(request.getParameter("apicallfrom"))) {
/*  97 */         credentialType = CredentialManagerUtil.getCredentialTypeKey(credentialType);
/*  98 */         if (credentialType == null) {
/*  99 */           AMLog.debug("REST API : The specified type in request URI is wrong.");
/* 100 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.wrongtyp.msg"), "4005");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 105 */       if ((credentialName == null) || ("".equals(credentialName.trim()))) {
/* 106 */         AMLog.debug("REST API : The Credential Name should not be empty.");
/* 107 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.nameempty"), "5001");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 112 */       int userID = DelegatedUserRoleUtil.getLoginUserid(request);
/* 113 */       AMLog.debug("REST API :userID  " + userID);
/* 114 */       List credNameList = credUtil.getCredentialNames(userID);
/* 115 */       if ((!isAPICallFromAAM) && (credNameList.contains(credentialName))) {
/* 116 */         AMLog.debug("REST API : The Credential Name already exists.");
/* 117 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.namealreadyexists"), "5003");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 122 */       if ((credentialID == null) || (credentialID.equals(""))) {
/* 123 */         credentialID = "-1";
/* 124 */       } else if (!isInteger(credentialID)) {
/* 125 */         AMLog.debug("REST API : The Credential ID should be numeric.");
/* 126 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.notvalidnumber"), "5006");
/*     */       }
/*     */       
/* 129 */       credentialMapToAdd.put("credentialName", credentialName);
/* 130 */       credentialMapToAdd.put("credentialType", credentialType);
/* 131 */       credentialMapToAdd.put("credentialID", credentialID);
/* 132 */       credentialMapToAdd.put("credentialDescr", credentialDescription);
/* 133 */       outputString = addOrUpdateCredential(request, response, credentialMapToAdd, true);
/*     */     } catch (Exception e) {
/* 135 */       e.printStackTrace();
/*     */     }
/* 137 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String updateCredential(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 141 */     HashMap credentialMapToUpdate = new HashMap();
/* 142 */     String credentialID = request.getParameter("credentialID");
/* 143 */     String credentialName = "";
/* 144 */     String credentialType = "";
/* 145 */     String outputString = null;
/*     */     try
/*     */     {
/* 148 */       if ((!EnterpriseUtil.isAdminServer) && (Integer.parseInt(credentialID) >= 10000000) && (!"admin".equals(request.getParameter("apicallfrom")))) {
/* 149 */         AMLog.debug("REST API : Credential created from Admin server cannot be deleted in Managed Server.");
/* 150 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.credentialManager.adminRestriction"), "5007");
/*     */       }
/*     */       
/*     */ 
/* 154 */       if (!isInteger(credentialID)) {
/* 155 */         AMLog.debug("REST API : The Credential ID should be numeric.");
/* 156 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.notvalidnumber"), "5006");
/*     */       }
/*     */       
/* 159 */       outputString = checkIfCredentialIDExist(request, response, credentialID);
/* 160 */       credentialMapToUpdate.put("credentialID", credentialID);
/* 161 */       credentialMapToUpdate.put("credentialType", credUtil.getCredentialTypeFromCredentialID(credentialID));
/* 162 */       Properties credProps = credUtil.getDetailsForACredential(credentialID);
/* 163 */       if ((credentialName == null) || (credentialName.equals("")))
/*     */       {
/* 165 */         Properties prop = credUtil.getDetailsForACredential(credentialID);
/* 166 */         credentialName = prop.getProperty("credentialName");
/* 167 */         credentialType = prop.getProperty("credentialType");
/*     */       }
/* 169 */       credentialMapToUpdate.put("credentialName", credentialName);
/* 170 */       if (outputString == null) {
/* 171 */         outputString = addOrUpdateCredential(request, response, credentialMapToUpdate, false);
/* 172 */         if (outputString == null) {
/* 173 */           AMLog.debug("REST API : Somethig went wrong in updating credential.");
/* 174 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.update.failed"), "5009");
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 179 */       e.printStackTrace();
/*     */     }
/* 181 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String addOrUpdateCredential(HttpServletRequest request, HttpServletResponse response, HashMap credentialMapToAddorUpdate, boolean isAddOperation) throws Exception {
/* 185 */     String outputString = null;
/* 186 */     String credentialID = "";
/* 187 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*     */     
/* 189 */     response.setContentType("text/xml; charset=UTF-8");
/*     */     
/* 191 */     credentialID = credentialMapToAddorUpdate.get("credentialID").toString();
/*     */     
/* 193 */     boolean isPasswordEncrypted = false;
/* 194 */     if ("admin".equals(request.getParameter("apicallfrom"))) {
/* 195 */       isPasswordEncrypted = true;
/*     */     }
/* 197 */     JSONArray jsonArrayToAdd = new JSONArray();
/* 198 */     if ((credentialMapToAddorUpdate.get("credentialType").toString().equalsIgnoreCase("ssh")) && (request.getParameter("sshPKAuth") != null) && (request.getParameter("sshPKAuth").equalsIgnoreCase("on"))) {
/* 199 */       String credName = (String)credentialMapToAddorUpdate.get("credentialName");
/* 200 */       jsonArrayToAdd = credUtil.getJSONArrayToAdd(request, credentialMapToAddorUpdate.get("credentialType").toString(), isPasswordEncrypted, credName);
/*     */     } else {
/* 202 */       jsonArrayToAdd = credUtil.getJSONArrayToAdd(request, credentialMapToAddorUpdate.get("credentialType").toString(), isPasswordEncrypted);
/*     */     }
/*     */     
/* 205 */     credentialMapToAddorUpdate.put("credentialDetails", jsonArrayToAdd);
/*     */     
/* 207 */     boolean isAdded = false;
/* 208 */     if (isAddOperation) {
/* 209 */       int userID = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey")));
/* 210 */       Long credID = Long.valueOf(1L);
/* 211 */       if ("admin".equals(request.getParameter("apicallfrom"))) {
/* 212 */         credID = credUtil.addCredential(credentialMapToAddorUpdate);
/*     */       }
/*     */       else {
/* 215 */         credID = credUtil.addCredential(userID, credentialMapToAddorUpdate);
/*     */       }
/* 217 */       AMLog.debug("REST API : The Credential(" + credentialMapToAddorUpdate.get("credentialName") + ") added successfully.");
/* 218 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.add.success"), "4000");
/*     */       try {
/* 220 */         if (EnterpriseUtil.isAdminServer()) {
/* 221 */           credentialMapToAddorUpdate.put("credentialID", credID);
/* 222 */           CredentialManagerUtil.addCredentialTasktoSync(credentialMapToAddorUpdate, "add");
/*     */         }
/*     */       } catch (Exception e) {
/* 225 */         e.printStackTrace();
/*     */       }
/*     */     } else {
/* 228 */       String userID = request.getParameter("apikey") == null ? DBUtil.getUserID(request.getRemoteUser()) : DBUtil.getUserIDForAPIKey(request.getParameter("apikey"));
/* 229 */       Vector<String> credIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(Integer.parseInt(userID), 4);
/* 230 */       boolean flag = false;
/* 231 */       if (((credIdList != null) && (!credIdList.contains(credentialID))) || (credIdList.isEmpty())) {
/* 232 */         flag = true;
/*     */       }
/* 234 */       if ((flag) && (DelegatedUserRoleUtil.isDelegatedAdmin(userID))) {
/* 235 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */       }
/* 237 */       isAdded = credUtil.updateACredential(request, credentialMapToAddorUpdate);
/* 238 */       if (!isAdded) {
/* 239 */         AMLog.debug("REST API : You are not the Owner for updating credential.");
/* 240 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.update.invalidUser"), "5009");
/* 241 */         return outputString;
/*     */       }
/* 243 */       AMLog.debug("REST API : The credential(" + credentialID + ") updated successfully");
/* 244 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.update.success"), "4000");
/*     */       try {
/* 246 */         if (EnterpriseUtil.isAdminServer()) {
/* 247 */           CredentialManagerUtil.addCredentialTasktoSync(credentialMapToAddorUpdate, "update");
/*     */         }
/*     */       } catch (Exception e) {
/* 250 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 254 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String deleteCredential(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 258 */     String credentialID = request.getParameter("credentialID");
/* 259 */     String outputString = null;
/*     */     try
/*     */     {
/* 262 */       if ((!EnterpriseUtil.isAdminServer) && (Integer.parseInt(credentialID) >= 10000000) && (!"admin".equals(request.getParameter("apicallfrom")))) {
/* 263 */         AMLog.debug("REST API : Credential created from Admin server cannot be deleted in Managed Server.");
/* 264 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.credentialManager.adminRestriction"), "5007");
/*     */       }
/*     */       
/* 267 */       if (!isInteger(credentialID)) {
/* 268 */         AMLog.debug("REST API : The Credential ID should be numeric.");
/* 269 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.notvalidnumber"), "5006");
/*     */       }
/*     */       
/* 272 */       outputString = checkIfCredentialIDExist(request, response, credentialID);
/* 273 */       if (outputString == null) {
/* 274 */         Hashtable<String, String> userdetails = CommonAPIUtil.getUserNameForAPIKey(request.getParameter("apikey"));
/* 275 */         String username = (String)userdetails.get("USERNAME");
/* 276 */         String userId = (String)userdetails.get("USERID");
/* 277 */         boolean flag = false;
/* 278 */         if (DBUtil.isDelegatedAdmin(username)) {
/* 279 */           Vector<String> credIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(Integer.parseInt(userId), 4);
/* 280 */           if (((credIdList != null) && (!credIdList.contains(credentialID))) || (credIdList.isEmpty())) {
/* 281 */             flag = true;
/*     */           }
/*     */         }
/* 284 */         if (flag) {
/* 285 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */         }
/* 287 */         credUtil.deleteCredential(new Long(credentialID).longValue());
/* 288 */         AMLog.debug("REST API : Credential deleted successfully.");
/* 289 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.delete.success"), "4000");
/*     */         try {
/* 291 */           if (EnterpriseUtil.isAdminServer()) {
/* 292 */             HashMap credentialIDToDelete = new HashMap();
/* 293 */             credentialIDToDelete.put("credentialID", credentialID);
/* 294 */             CredentialManagerUtil.addCredentialTasktoSync(credentialIDToDelete, "delete");
/*     */           }
/*     */         } catch (Exception e) {
/* 297 */           e.printStackTrace();
/*     */         }
/* 299 */         return outputString;
/*     */       }
/*     */     } catch (Exception e) {
/* 302 */       e.printStackTrace();
/*     */     }
/* 304 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String listCredential(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 308 */     String outputString = "";
/* 309 */     String reasonCode = "4000";
/* 310 */     String message = "";
/* 311 */     String type = request.getParameter("type");
/* 312 */     AMLog.info("RESTAPI: type: " + type);
/*     */     try
/*     */     {
/* 315 */       if ("".equals(type)) {
/* 316 */         AMLog.debug("REST API : The type is or empty.");
/* 317 */         message = FormatUtil.getString("am.webclient.api.type.emptymessage");
/* 318 */         reasonCode = "4202";
/* 319 */         return URITree.generateXML(request, response, message, reasonCode);
/*     */       }
/*     */       
/* 322 */       if (isJsonFormat) {
/* 323 */         response.setContentType("text/plain; charset=UTF-8");
/*     */       } else {
/* 325 */         response.setContentType("text/xml; charset=UTF-8");
/*     */       }
/* 327 */       CredentialManagerUtil credUtil = CredentialManagerUtil.getInstance();
/* 328 */       ArrayList<Hashtable> credList = new ArrayList();
/* 329 */       int userID = Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(request.getParameter("apikey")));
/* 330 */       boolean isPrivileged = CommonAPIUtil.isDelegatedAdminAPIRequest(request);
/* 331 */       if ((type == null) || (type.equalsIgnoreCase("all"))) {
/* 332 */         credList = (ArrayList)credUtil.getCredentialList(userID, isPrivileged);
/*     */       } else {
/* 334 */         String credType = CredentialManagerUtil.getCredentialTypeKey(type);
/* 335 */         if (credType == null) {
/* 336 */           message = FormatUtil.getString("am.webclient.api.wrongtyp.msg");
/* 337 */           reasonCode = "4005";
/* 338 */           return URITree.generateXML(request, response, message, reasonCode);
/*     */         }
/*     */         
/* 341 */         String delegatedCondition = "";
/* 342 */         if (isPrivileged) {
/* 343 */           Vector credIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userID, 4);
/* 344 */           delegatedCondition = "and " + DBUtil.getCondition("cm.CREDENTIALID", credIdList);
/*     */         }
/*     */         
/* 347 */         String query = "select cm.CREDENTIALID, cm.NAME, cm.TYPE, cd.CREDENTIALVALUES from CREDENTIALMANAGER cm join CREDENTIALDETAILS cd on cm.CREDENTIALID=cd.CREDENTIALID where type='" + credType + "' " + delegatedCondition;
/* 348 */         credList = (ArrayList)credUtil.getCredentialList(query);
/*     */       }
/* 350 */       for (Hashtable ht : credList) {
/* 351 */         ht.put("credentialType", ht.get("i18NType"));
/* 352 */         ht.remove("i18NType");
/* 353 */         ht.remove("credentialDescription");
/*     */       }
/* 355 */       HashMap dataMap = new HashMap();
/* 356 */       dataMap.put("response-code", reasonCode);
/* 357 */       dataMap.put("uri", request.getRequestURI());
/* 358 */       dataMap.put("result", credList);
/* 359 */       dataMap.put("sortingParam", "credentialID");
/* 360 */       dataMap.put("sortingOrder", "asc");
/* 361 */       dataMap.put("parentNode", "Credentials");
/* 362 */       dataMap.put("nodeName", "Credential");
/* 363 */       outputString = CommonAPIUtil.getOutputAsString(dataMap, isJsonFormat);
/*     */     } catch (NullPointerException ne) {
/* 365 */       ne.printStackTrace();
/* 366 */       message = FormatUtil.getString("am.webclient.nodata.text");
/* 367 */       reasonCode = "4009";
/* 368 */       outputString = URITree.generateXML(request, response, message, reasonCode);
/*     */     } catch (Exception e) {
/* 370 */       e.printStackTrace();
/* 371 */       AMLog.debug("REST API : The specified request URI is incorrect: the method specified is wrong");
/* 372 */       message = FormatUtil.getString("am.webclient.apikey.wrongmethod.message");
/* 373 */       reasonCode = "4016";
/* 374 */       outputString = URITree.generateXML(request, response, message, reasonCode);
/*     */     }
/* 376 */     return outputString;
/*     */   }
/*     */   
/*     */   public static String checkIfCredentialIDExist(HttpServletRequest request, HttpServletResponse response, String credID) throws Exception {
/* 380 */     if ((credID != null) && (!credID.equals(""))) {
/* 381 */       List credIDList = credUtil.getCredentialIDs();
/* 382 */       boolean isIDPresent = credIDList.contains(Integer.valueOf(Integer.parseInt(credID)));
/* 383 */       if (!isIDPresent) {
/* 384 */         AMLog.debug("REST API : The Credential ID is not exist.");
/* 385 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.idnotexist"), "5004");
/*     */       }
/*     */     } else {
/* 388 */       AMLog.debug("REST API : The Credential ID should not be empty.");
/* 389 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.credential.idempty"), "5005");
/*     */     }
/* 391 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isInteger(String s) {
/*     */     try {
/* 396 */       Integer.parseInt(s);
/*     */     } catch (NumberFormatException e) {
/* 398 */       return false;
/*     */     }
/* 400 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\CredentialAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */