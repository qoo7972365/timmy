/*     */ package com.adventnet.appmanager.struts.urlmonitor;
/*     */ 
/*     */ import com.adventnet.agent.utilities.xml.XMLDataReader;
/*     */ import com.adventnet.agent.utilities.xml.XMLNode;
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*     */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*     */ import com.adventnet.appmanager.server.dao.UrlConfiguration;
/*     */ import com.adventnet.appmanager.server.dao.UrlConfigurationDao;
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.UrlSequence;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.manageengine.appmanager.plugin.PluginUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateUrlSequence
/*     */   extends DispatchAction
/*     */ {
/*  55 */   Log log = LogFactory.getLog("UrlDataCollector");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getTransactionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  69 */     AMLog.debug("CreateUrlSequence :: getTransactionDetails is called \n");
/*     */     try {
/*  71 */       boolean bo = doLicenseCheck(request);
/*  72 */       if (!bo) {
/*  73 */         return new ActionForward("/jsp/licenserestricted.html", true);
/*     */       }
/*  75 */       String userName = request.getParameter("userName").toString();
/*  76 */       String password = request.getParameter("password").toString();
/*  77 */       String resourceID = request.getParameter("resourceID").toString();
/*  78 */       if (PluginUtil.isPlugin()) {
/*  79 */         password = userName + "@opm";
/*     */       }
/*  81 */       AMLog.debug("CreateUrlSequence :: getTransactionDetails :: resourceID :: " + resourceID);
/*  82 */       if (userName.trim().length() == 0)
/*     */       {
/*  84 */         request.setAttribute("message", "Invalid Userame/password");
/*  85 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/*  87 */       AMLog.debug("User Name is " + userName + " password is : " + DBQueryUtil.MD5(password));
/*  88 */       ManagedApplication mo = new ManagedApplication();
/*  89 */       ArrayList userRow = mo.getRows("select USERNAME,PASSWORD from AM_UserPasswordTable where AM_UserPasswordTable.USERNAME='" + userName + "' and PASSWORD=" + DBQueryUtil.MD5(password));
/*  90 */       AMLog.debug("No of rows of users retrieved is : " + userRow + " Number of entries " + userRow.size());
/*  91 */       if (userRow.size() == 0)
/*     */       {
/*  93 */         request.setAttribute("message", "Invalid Username/password");
/*  94 */         return new ActionForward("/jsp/authfailed.html");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */       AMLog.debug("The resource ID of the Sequence is : " + resourceID);
/* 102 */       ArrayList childIDList = mo.getRows("select CHILDID from AM_PARENTCHILDMAPPER where PARENTID=" + resourceID);
/* 103 */       AMLog.debug("The number of child ID's retrieved is : " + childIDList.size());
/* 104 */       StringBuffer urlSequences = new StringBuffer();
/* 105 */       int i = 0; for (int size = childIDList.size(); i < size; i++)
/*     */       {
/* 107 */         ArrayList childEntryList = (ArrayList)childIDList.get(i);
/* 108 */         String childID = childEntryList.get(0).toString();
/* 109 */         AMLog.debug("Child Url Sequence ID : " + childID);
/* 110 */         ArrayList childUrlList = mo.getRows("select DISPLAYNAME from AM_ManagedObject where TYPE='UrlEle' and RESOURCEID=" + childID);
/* 111 */         ArrayList childUrlEntryList = (ArrayList)childUrlList.get(0);
/* 112 */         String urlEntry = childUrlEntryList.get(0).toString();
/* 113 */         urlSequences.append(urlEntry);
/* 114 */         urlSequences.append(":-:");
/*     */       }
/* 116 */       response.getWriter().println(urlSequences.toString());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 120 */       AMLog.fatal("Error in GetTransactionDetails ");
/* 121 */       e.printStackTrace();
/* 122 */       return null;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward getAllTransactions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 129 */     AMLog.debug("CreateUrlSequence :: getAllTransactions is called \n");
/*     */     try {
/* 131 */       boolean bo = doLicenseCheck(request);
/* 132 */       if (!bo) {
/* 133 */         return new ActionForward("/jsp/licenserestricted.html", true);
/*     */       }
/* 135 */       String userName = request.getParameter("userName").toString();
/* 136 */       String password = request.getParameter("password").toString();
/*     */       
/*     */ 
/* 139 */       if (userName.trim().length() == 0)
/*     */       {
/* 141 */         request.setAttribute("message", "Invalid Userame/password");
/* 142 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/* 144 */       if (PluginUtil.isPlugin()) {
/* 145 */         password = userName + "@opm";
/*     */       }
/* 147 */       AMLog.debug("User Name is " + userName + " password is : " + DBQueryUtil.MD5(password));
/* 148 */       ManagedApplication mo = new ManagedApplication();
/* 149 */       ArrayList userRow = mo.getRows("select USERNAME,PASSWORD from AM_UserPasswordTable where AM_UserPasswordTable.USERNAME='" + userName + "' and PASSWORD=" + DBQueryUtil.MD5(password));
/* 150 */       AMLog.debug("No of rows of users retrieved is : " + userRow + " Number of entries " + userRow.size());
/* 151 */       if (userRow.size() == 0)
/*     */       {
/* 153 */         request.setAttribute("message", "Invalid Username/password");
/* 154 */         return new ActionForward("/jsp/authfailed.html");
/*     */       }
/* 156 */       ArrayList urlSeqList = mo.getRows("select DISPLAYNAME,RESOURCEID from AM_ManagedObject where TYPE='UrlSeq'");
/* 157 */       AMLog.debug("Number of rows of RESOURCES is : " + urlSeqList + " Number of entries  = " + urlSeqList.size());
/* 158 */       StringBuffer urlSequences = new StringBuffer();
/* 159 */       int i = 0; for (int size = urlSeqList.size(); i < size; i++)
/*     */       {
/* 161 */         ArrayList entryList = (ArrayList)urlSeqList.get(i);
/* 162 */         AMLog.debug("Url Sequence Name : " + entryList.get(0).toString());
/* 163 */         urlSequences.append(entryList.get(0).toString());
/* 164 */         urlSequences.append(":-:");
/* 165 */         urlSequences.append(entryList.get(1).toString());
/* 166 */         urlSequences.append(",");
/*     */       }
/* 168 */       AMLog.debug("URL Sequence String : " + urlSequences.toString());
/* 169 */       response.getWriter().println(urlSequences.toString());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 173 */       AMLog.fatal("Error in GetAllTransactions ");
/* 174 */       e.printStackTrace();
/* 175 */       return null;
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */   
/*     */   public ActionForward updateUrlSeq(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/* 182 */     String datainxml = null;
/* 183 */     AMLog.debug("CreateUrlSequence :: updateUrlSeq is called ");
/*     */     try {
/* 185 */       datainxml = request.getParameter("seqinfo");
/* 186 */       AMLog.debug("seqinfo for Update :: " + datainxml);
/* 187 */       if (this.log.isDebugEnabled())
/*     */       {
/* 189 */         AMLog.debug("seqinfo for Update :: " + datainxml);
/*     */       }
/* 191 */       AMLog.debug("Update Request from Recorder " + request.getParameter("version"));
/* 192 */       AMLog.debug(" **********************************************************");
/* 193 */       String urlSequenceID = request.getParameter("transactionID");
/* 194 */       String userName = request.getParameter("username");
/* 195 */       String password = request.getParameter("password");
/* 196 */       String pollIntervalStr = request.getParameter("pollinterval");
/* 197 */       if (PluginUtil.isPlugin()) {
/* 198 */         password = userName + "@opm";
/*     */       }
/* 200 */       AMLog.debug("Sequence ID " + urlSequenceID + " UserName : " + userName + "password : " + password + " PollInterval : " + pollIntervalStr);
/* 201 */       int pollInterval = Integer.parseInt(pollIntervalStr);
/* 202 */       AMLog.debug("Pollinterval from the tool is " + pollInterval);
/* 203 */       boolean bo = doLicenseCheck(request);
/* 204 */       if (!bo) {
/* 205 */         response.getWriter().println("Error: License check failed for user : " + userName);
/* 206 */         return new ActionForward("/jsp/licenserestricted.html", true);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 211 */         pollInterval *= 60000;
/* 212 */         AMLog.debug("converted to millisecond" + pollInterval);
/*     */       }
/*     */       catch (NumberFormatException ne) {
/* 215 */         response.getWriter().println("Error : Poll Interval not in correct format " + pollIntervalStr);
/* 216 */         pollIntervalStr = "300000";
/* 217 */         AMLog.debug("defaulting to 5 minutes");
/*     */       }
/* 219 */       ManagedApplication mo = new ManagedApplication();
/* 220 */       String userQry = "select GROUPNAME from AM_UserPasswordTable,AM_UserGroupTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and AM_UserPasswordTable.USERNAME='" + userName + "' and PASSWORD=" + DBQueryUtil.MD5(password);
/* 221 */       ArrayList users = mo.getRows(userQry);
/* 222 */       boolean isAdminUser = false;
/* 223 */       AMLog.debug("Number of rows for user validation " + users.size());
/* 224 */       if (users.size() == 0) {
/* 225 */         request.setAttribute("message", "Invalid Userame/password");
/* 226 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/* 228 */       for (int i = 0; i < users.size(); i++) {
/* 229 */         ArrayList roles = (ArrayList)users.get(i);
/* 230 */         String role = (String)roles.get(0);
/* 231 */         if ((role.equals("ADMIN")) || (role.equals("DEMO"))) {
/* 232 */           isAdminUser = true;
/* 233 */           break;
/*     */         }
/*     */       }
/* 236 */       if (!isAdminUser) {
/* 237 */         request.setAttribute("message", userName + " does not have rights to create Update Url Sequence");
/* 238 */         response.getWriter().println(userName + " does not have rights to use Update Url Sequence");
/* 239 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/* 241 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 251 */       int resourceID = Integer.parseInt(urlSequenceID);
/* 252 */       ArrayList oldUrlList = mo.getRows("select CHILDID from AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID='" + String.valueOf(resourceID) + "'");
/* 253 */       AMLog.debug("Old UrlList size " + oldUrlList.size());
/* 254 */       ArrayList childIDArrayList = new ArrayList();
/* 255 */       int i = 0; for (int size = oldUrlList.size(); i < size; i++) {
/* 256 */         ArrayList row = (ArrayList)oldUrlList.get(i);
/* 257 */         String childID = row.get(0).toString();
/* 258 */         childIDArrayList.add(childID);
/* 259 */         AMLog.debug("Child Entry from DB is " + childID);
/*     */       }
/* 261 */       XMLDataReader x = new XMLDataReader(new ByteArrayInputStream(datainxml.getBytes()));
/* 262 */       XMLNode node = x.getRootNode();
/* 263 */       Vector urls = node.getChildNodes();
/* 264 */       if (urls.size() <= 0)
/*     */       {
/*     */ 
/*     */ 
/* 268 */         response.getWriter().println("Error : Number of URL's to be updated is zero ");
/* 269 */         return new ActionForward("Some Error page");
/*     */       }
/* 271 */       int currentUrlCount = 0;
/* 272 */       int existingUrlCount = childIDArrayList.size();
/* 273 */       String currentURL = "";
/* 274 */       int rowsAffected = 0;
/* 275 */       int i = 0; for (int size = urls.size(); i < size; currentUrlCount++) {
/* 276 */         XMLNode urlNode = (XMLNode)urls.get(i);
/* 277 */         AMLog.debug("Url at index = " + i + " = " + urlNode.getAttribute("address").toString());
/* 278 */         currentURL = urlNode.getAttribute("address").toString();
/*     */         
/*     */ 
/* 281 */         AMLog.debug("currentUrlCount : " + currentUrlCount + " existingUrlCount : " + existingUrlCount);
/* 282 */         if (currentUrlCount < existingUrlCount)
/*     */         {
/*     */ 
/* 285 */           String childID = childIDArrayList.get(i).toString();
/* 286 */           AMLog.debug("Processing child ID : " + childID);
/*     */           
/* 288 */           EnterpriseUtil.addUpdateQueryToFile("update AM_ManagedObject set RESOURCENAME='" + currentURL + "', DISPLAYNAME='" + currentURL + "', DESCRIPTION='URL Monitor for " + currentURL + "' where RESOURCEID=" + childID);
/* 289 */           rowsAffected = mo.executeUpdateStmt("update AM_ManagedObject set RESOURCENAME='" + currentURL + "', DISPLAYNAME='" + currentURL + "', DESCRIPTION='URL Monitor for " + currentURL + "' where RESOURCEID=" + childID);
/* 290 */           AMLog.debug("Rows affected after executeUpdate of AM_ManagedObject : " + rowsAffected);
/* 291 */           if (rowsAffected != 1)
/*     */           {
/*     */ 
/* 294 */             AMLog.fatal("Something is seriously wrong while updating URL's in URL Sequence");
/*     */           }
/*     */           
/* 297 */           EnterpriseUtil.addUpdateQueryToFile("update AM_URL set URL='" + currentURL + "' where URLID=" + childID);
/* 298 */           rowsAffected = mo.executeUpdateStmt("update AM_URL set URL='" + currentURL + "' where URLID=" + childID);
/* 299 */           AMLog.debug("Rows affected after executeUpdate of AM_URL : " + rowsAffected);
/* 300 */           if (rowsAffected != 1)
/*     */           {
/*     */ 
/* 303 */             AMLog.fatal("Update URL Sequence : AM_URL Cause of concern : No of rows affected " + rowsAffected);
/*     */           }
/*     */           
/*     */ 
/* 307 */           XMLNode alertNode = urlNode.getChildNode("AlertCondition");
/* 308 */           if (alertNode != null)
/*     */           {
/*     */ 
/*     */ 
/* 312 */             XMLNode cNode = alertNode.getChildNode("HTTPResponseCode");
/*     */             
/* 314 */             if (cNode == null)
/*     */             {
/* 316 */               AMLog.debug("HttpResponseCode is " + cNode);
/*     */               
/* 318 */               String query = "select * from AM_THRESHOLDCONFIG where NAME='Threshold" + childID + "'";
/* 319 */               ArrayList oldThresholdList = mo.getRows(query);
/* 320 */               if (oldThresholdList.size() != 0)
/*     */               {
/*     */                 try
/*     */                 {
/* 324 */                   query = "delete from AM_THRESHOLDCONFIG where NAME='Threshold" + childID + "'";
/* 325 */                   EnterpriseUtil.addUpdateQueryToFile(query);
/* 326 */                   mo.executeUpdateStmt(query);
/*     */                 }
/*     */                 catch (Exception e)
/*     */                 {
/* 330 */                   AMLog.fatal("Exception occurred while trying to delete Threshold configuration for URL ID : " + childID);
/*     */                 }
/*     */                 try {
/* 333 */                   query = "delete from AM_ATTRIBUTETHRESHOLDMAPPER where ID=" + childID + " and ATTRIBUTE=403";
/* 334 */                   EnterpriseUtil.addUpdateQueryToFile(query);
/* 335 */                   mo.executeUpdateStmt(query);
/*     */                 }
/*     */                 catch (Exception e)
/*     */                 {
/* 339 */                   AMLog.fatal("Exception occurred while trying to delete Threshold Mapper configuration for URL ID : " + childID);
/*     */                 }
/*     */               }
/*     */             }
/*     */             else {
/* 344 */               String condition = cNode.getAttribute("condition").toString();
/* 345 */               int code = Integer.valueOf(cNode.getAttribute("code").toString()).intValue();
/* 346 */               condition = condition.toLowerCase();
/* 347 */               AMLog.debug("HttpResponse Code is : " + condition + " code = " + code + " Resource ID " + childID);
/* 348 */               if (condition.equals("greater than"))
/*     */               {
/* 350 */                 condition = "GT";
/*     */               }
/* 352 */               else if (condition.equals("equal to"))
/*     */               {
/* 354 */                 condition = "EQ";
/*     */               }
/* 356 */               else if (condition.equals("not equal to"))
/*     */               {
/* 358 */                 condition = "NE";
/*     */               }
/* 360 */               else if (condition.equals("less than"))
/*     */               {
/* 362 */                 condition = "LT";
/*     */               }
/* 364 */               else if (condition.equals("less than or equal"))
/*     */               {
/* 366 */                 condition = "LE";
/*     */               }
/*     */               else
/*     */               {
/* 370 */                 condition = "GT";
/*     */               }
/* 372 */               AMLog.debug("HttpResponse Code is : " + condition + " code = " + code + " Resource ID " + childID);
/* 373 */               CreateUrlMonitor.configureThreshold(Integer.valueOf(childID).intValue(), condition, code);
/*     */             }
/* 375 */             XMLNode mcNode = alertNode.getChildNode("MatchContent");
/* 376 */             if (mcNode == null)
/*     */             {
/* 378 */               AMLog.debug("MatchContent Node is null " + mcNode);
/*     */               try
/*     */               {
/* 381 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set AVAILABILITYSTRING='' where URLID=" + childID);
/* 382 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set AVAILABILITYSTRING='' where URLID=" + childID);
/* 383 */                 AMLog.debug("MatchContent :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 387 */                 AMLog.fatal("Exception while trying to update Match Content : AVAILABILITYSTRING for URLID : " + childID);
/*     */               }
/*     */             }
/* 390 */             if (mcNode != null)
/*     */             {
/* 392 */               AMLog.debug("MatchContent Node is " + mcNode.toString());
/* 393 */               String matchContent = mcNode.getNodeValue();
/* 394 */               AMLog.debug("MatchContent Node Value is " + mcNode.getNodeValue());
/* 395 */               if ((matchContent != null) && (matchContent.length() > 0))
/*     */               {
/* 397 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set AVAILABILITYSTRING='" + matchContent + "' where URLID=" + childID);
/* 398 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set AVAILABILITYSTRING='" + matchContent + "' where URLID=" + childID);
/* 399 */                 AMLog.debug("MatchContent :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */             }
/* 402 */             XMLNode usNode = alertNode.getChildNode("UnavailabilityString");
/* 403 */             if (usNode == null)
/*     */             {
/* 405 */               AMLog.debug("UnavailabilityString node is null");
/*     */               try
/*     */               {
/* 408 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set UNAVAILABILITYSTRING='' where URLID=" + childID);
/* 409 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set UNAVAILABILITYSTRING='' where URLID=" + childID);
/* 410 */                 AMLog.debug("MatchContent :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 414 */                 AMLog.fatal("Exception while trying to update Not Match Content : UNAVAILABILITYSTRING for URLID : " + childID);
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 419 */               AMLog.debug("UnavailabilityString Node is " + usNode.toString());
/* 420 */               AMLog.debug("UnavailabilityString Node Value is " + usNode.getNodeValue());
/* 421 */               String usContent = usNode.getNodeValue();
/* 422 */               if ((usContent != null) && (usContent.length() > 0))
/*     */               {
/* 424 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set UNAVAILABILITYSTRING='" + usContent + "' where URLID=" + childID);
/* 425 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set UNAVAILABILITYSTRING='" + usContent + "' where URLID=" + childID);
/* 426 */                 AMLog.debug("UnavailabilityString :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */             }
/* 429 */             XMLNode authNode = urlNode.getChildNode("AuthInfo");
/* 430 */             if (authNode == null)
/*     */             {
/* 432 */               AMLog.debug("AuthInfo node is null");
/*     */               try
/*     */               {
/* 435 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set USERID='' , PASSWORD='' where URLID=" + childID);
/* 436 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set USERID='' , PASSWORD='' where URLID=" + childID);
/* 437 */                 AMLog.debug("AuthInfo :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 441 */                 AMLog.fatal("Exception while trying to update AuthInfo for URLID : " + childID);
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 446 */               AMLog.debug("AuthInfo Node is " + authNode.toString());
/* 447 */               AMLog.debug("AuthInfo Node userName  is " + authNode.getAttribute("username").toString() + " password : " + authNode.getAttribute("password").toString());
/* 448 */               String uName = authNode.getAttribute("username").toString();
/* 449 */               String pwd = authNode.getAttribute("password").toString();
/* 450 */               if (uName.length() > 0)
/*     */               {
/* 452 */                 rowsAffected = mo.executeUpdateStmt("update AM_URL set USERID='" + uName + "' , PASSWORD='" + pwd + "' where URLID=" + childID);
/* 453 */                 EnterpriseUtil.addUpdateQueryToFile("update AM_URL set USERID='" + uName + "' , PASSWORD='" + pwd + "' where URLID=" + childID);
/* 454 */                 AMLog.debug("AuthInfo :: No of rows affected is " + rowsAffected);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 461 */           createUrlMonitor(resourceID, urlNode, pollInterval);
/*     */         }
/* 275 */         i++;
/*     */       }
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
/*     */ 
/* 465 */       if (currentUrlCount < existingUrlCount)
/*     */       {
/* 467 */         for (int i = currentUrlCount; i < existingUrlCount; i++)
/*     */         {
/*     */ 
/*     */ 
/* 471 */           String childID = childIDArrayList.get(i).toString();
/* 472 */           AMLog.debug("Deleting the RESOURCE from AM_URLData : " + childID);
/* 473 */           rowsAffected = mo.executeUpdateStmt("delete from AM_URLData where URLID=" + childID);
/* 474 */           if (rowsAffected != 1)
/*     */           {
/* 476 */             AMLog.fatal("Delete Child entries has some unexpected results while executing : delete from AM_URLData where URLID=" + childID);
/*     */           }
/* 478 */           AMLog.debug("Deleting the AM_URL from AM_URL with URLID : " + childID);
/* 479 */           rowsAffected = mo.executeUpdateStmt("delete from AM_URL where URLID=" + childID);
/* 480 */           if (rowsAffected != 1)
/*     */           {
/* 482 */             AMLog.fatal("Delete Child entries has some unexpected results while executing : delete from AM_URL where URLID=" + childID);
/*     */           }
/* 484 */           Vector v = new Vector();
/* 485 */           v.add(childID);
/* 486 */           DataCollectionControllerUtil.deleteMO(childID, v, true);
/*     */ 
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 504 */       response.getWriter().println("URLSequence updation error : " + e.getMessage());
/* 505 */       AMLog.fatal(datainxml);
/* 506 */       e.printStackTrace();
/* 507 */       return null;
/*     */     }
/* 509 */     response.getWriter().println("URL Sequence updated successfully");
/* 510 */     return mapping.findForward("success");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward createUrlSeq(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 520 */     String datainxml = null;
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 526 */       datainxml = request.getParameter("seqinfo");
/*     */       
/*     */ 
/* 529 */       AMLog.debug("CreateUrlSequence monitor XML data from recorder." + datainxml);
/*     */       
/* 531 */       AMLog.debug("Request from Recorder " + request.getParameter("version"));
/* 532 */       String urlsequencedisplayname = request.getParameter("transactionname");
/*     */       
/* 534 */       String username = request.getParameter("username");
/* 535 */       String password = request.getParameter("password");
/* 536 */       if (PluginUtil.isPlugin()) {
/* 537 */         password = username + "@opm";
/*     */       }
/* 539 */       String pollinterval = request.getParameter("pollinterval");
/* 540 */       AMLog.debug("Pollinterval from the tool is " + pollinterval);
/*     */       
/* 542 */       boolean bo = doLicenseCheck(request);
/* 543 */       if (!bo)
/*     */       {
/* 545 */         return new ActionForward("/jsp/licenserestricted.html", true);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 550 */         pollinterval = String.valueOf(Integer.parseInt(pollinterval) * 60000);
/* 551 */         AMLog.debug("converted to millisecond" + pollinterval);
/*     */       }
/*     */       catch (NumberFormatException ne)
/*     */       {
/* 555 */         pollinterval = "300000";
/* 556 */         AMLog.debug("defaulting to 5 minutes");
/*     */       }
/* 558 */       ManagedApplication mo = new ManagedApplication();
/* 559 */       ArrayList users = mo.getRows("select GROUPNAME from AM_UserPasswordTable,AM_UserGroupTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and AM_UserPasswordTable.USERNAME='" + username + "' and PASSWORD=" + DBQueryUtil.MD5(password));
/* 560 */       boolean isAdminUser = false;
/* 561 */       if (users.size() == 0)
/*     */       {
/* 563 */         request.setAttribute("message", "Invalid username/password");
/*     */         
/* 565 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/* 567 */       for (int i = 0; i < users.size(); i++)
/*     */       {
/* 569 */         ArrayList roles = (ArrayList)users.get(i);
/* 570 */         String role = (String)roles.get(0);
/* 571 */         if ((role.equals("ADMIN")) || (role.equals("DEMO")))
/* 572 */           isAdminUser = true;
/*     */       }
/* 574 */       if (!isAdminUser)
/*     */       {
/* 576 */         request.setAttribute("message", username + " does not have rights to create Transaction Monitor");
/*     */         
/* 578 */         return new ActionForward("/jsp/authfailed.html", true);
/*     */       }
/* 580 */       XMLDataReader x = new XMLDataReader(new ByteArrayInputStream(datainxml.getBytes()));
/*     */       
/* 582 */       XMLNode node = x.getRootNode();
/* 583 */       Vector urls = node.getChildNodes();
/* 584 */       if (urls.size() == 0)
/*     */       {
/*     */ 
/* 587 */         return null;
/*     */       }
/*     */       
/* 590 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/* 591 */       AMManagedObject ammo = new AMManagedObject();
/* 592 */       ammo.setRESOURCENAME(urlsequencedisplayname);
/* 593 */       ammo.setType("UrlSeq");
/* 594 */       ammo.setDISPLAYNAME(urlsequencedisplayname);
/* 595 */       ammo.setDESCRIPTION("Monitors set of URLs");
/* 596 */       dao.create(ammo);
/* 597 */       int urlseqid = ammo.getRESOURCEID();
/* 598 */       request.setAttribute("urlseqid", String.valueOf(urlseqid));
/* 599 */       new AMAttributesDependencyAdder().addDependentAttributes(5, urlseqid);
/* 600 */       new AMAttributesDependencyAdder().addInterDependentAttributes(urlseqid);
/* 601 */       for (int i = 0; i < urls.size(); i++)
/*     */       {
/* 603 */         XMLNode urlnode = (XMLNode)urls.get(i);
/* 604 */         createUrlMonitor(urlseqid, urlnode, Integer.parseInt(pollinterval));
/*     */       }
/* 606 */       AMLog.debug("Create a url sequence" + urlseqid);
/*     */       
/* 608 */       ArrayList rows = mo.getRows("select AM_URL.*,AM_ManagedObject.resourceid from AM_URL,AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_URL.URLID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.resourceid and AM_ManagedObject.type='UrlSeq' and AM_ManagedObject.resourceid='" + urlseqid + "' order by resourceid,urlid ");
/*     */       
/* 610 */       for (int i = 0; i < rows.size(); i++)
/*     */       {
/* 612 */         ArrayList row = (ArrayList)rows.get(i);
/* 613 */         int urlid = Integer.parseInt((String)row.get(0));
/* 614 */         String msUrl = (String)row.get(1);
/* 615 */         String msUserId = (String)row.get(2);
/* 616 */         String msPassword = (String)row.get(3);
/* 617 */         String msQueryString = (String)row.get(4);
/* 618 */         String msMethod = (String)row.get(5);
/* 619 */         String msAvailabilityString = (String)row.get(6);
/* 620 */         long mnPollInterval = Integer.parseInt((String)row.get(7));
/* 621 */         String urlsequenceid = (String)row.get(15);
/* 622 */         UrlSequence monitor = new UrlSequence(Integer.parseInt(urlsequenceid), urlid, msUrl, msUserId, msPassword, msQueryString, msMethod, msAvailabilityString, mnPollInterval, (String)row.get(11));
/* 623 */         monitor.setUnAvailabilityString((String)row.get(8));
/* 624 */         monitor.setTimeout(Integer.parseInt((String)row.get(9)));
/* 625 */         monitor.setVerifyerror(row.get(10).equals("1"));
/* 626 */         monitor.setCaseSensitive((row.get(12).equals("1")) || (row.get(12).equals("t")));
/* 627 */         monitor.setRegEx((row.get(13).equals("1")) || (row.get(13).equals("t")));
/* 628 */         monitor.setUserAgent((String)row.get(14));
/*     */         
/* 630 */         AMLog.debug("UrlDataCollector : Configuring URL monitor for " + msUrl);
/*     */       }
/*     */       
/*     */ 
/* 634 */       return mapping.findForward("success");
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 638 */       AMLog.fatal(datainxml);
/* 639 */       e.printStackTrace(); }
/* 640 */     return null;
/*     */   }
/*     */   
/*     */   public boolean doLicenseCheck(HttpServletRequest request)
/*     */   {
/* 645 */     ActionMessages messages = new ActionMessages();
/* 646 */     ManagedApplication mo = new ManagedApplication();
/* 647 */     int numberofmonitors = Constants.getNoofMonitors_withoutnatives();
/* 648 */     int maxallowed = FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 670 */     String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 671 */     if ((maxallowed != -1) && (maxallowed <= numberofmonitors))
/*     */     {
/*     */ 
/* 674 */       if (usertype.equals("F"))
/*     */       {
/* 676 */         String m1 = FormatUtil.getString("freeedition.monitor.restriction1", new String[] { OEMUtil.getOEMString("product.name") });
/* 677 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(m1));
/* 678 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("freeedition.monitor.restriction2", String.valueOf(maxallowed)));
/* 679 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("freeedition.monitor.restriction3"));
/*     */       }
/*     */       else
/*     */       {
/* 683 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction1", String.valueOf(maxallowed)));
/* 684 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction2"));
/*     */       }
/* 686 */       saveMessages(request, messages);
/* 687 */       return false;
/*     */     }
/* 689 */     return true;
/*     */   }
/*     */   
/*     */   private void createUrlMonitor(int urlseqid, XMLNode node, int pollinterval) throws Exception
/*     */   {
/*     */     try {
/* 695 */       String url = (String)node.getAttribute("address");
/* 696 */       String displayname = (String)node.getAttribute("displayname");
/* 697 */       String method = (String)node.getAttribute("method");
/* 698 */       String username = null;
/* 699 */       String password = null;
/* 700 */       String requestparams = "";
/* 701 */       Long timout = new Long(120L);
/* 702 */       String unavailabilitystring = "";
/* 703 */       String verifyerror = "true";
/* 704 */       String checkfor = "";
/*     */       
/* 706 */       AMLog.debug("The pollinreval is " + pollinterval);
/* 707 */       String httpcondition = null;
/* 708 */       int wnhttpvalue = -1;
/* 709 */       XMLNode postdata = node.getChildNode("PostData");
/* 710 */       if (postdata != null) {
/* 711 */         requestparams = postdata.getNodeValue().trim();
/*     */       }
/* 713 */       XMLNode alertconditions = node.getChildNode("AlertCondition");
/* 714 */       if (alertconditions != null)
/*     */       {
/* 716 */         Vector conditions = alertconditions.getChildNodes();
/* 717 */         if ((conditions != null) && (conditions.size() != 0))
/*     */         {
/* 719 */           for (int i = 0; i < conditions.size(); i++)
/*     */           {
/* 721 */             XMLNode condition = (XMLNode)conditions.get(i);
/* 722 */             if (condition.getNodeName().equals("MatchContent"))
/*     */             {
/* 724 */               checkfor = condition.getNodeValue();
/* 725 */             } else if (condition.getNodeName().equals("UnavailabilityString"))
/*     */             {
/* 727 */               unavailabilitystring = condition.getNodeValue();
/* 728 */             } else if (condition.getNodeName().equals("HTTPResponseCode"))
/*     */             {
/* 730 */               httpcondition = (String)condition.getAttribute("condition");
/* 731 */               String httpvalue = (String)condition.getAttribute("code");
/*     */               try
/*     */               {
/* 734 */                 wnhttpvalue = Integer.parseInt(httpvalue);
/*     */               }
/*     */               catch (NumberFormatException ne)
/*     */               {
/*     */                 break;
/*     */               }
/*     */               
/* 741 */               if (httpcondition.equals("Greater than"))
/*     */               {
/* 743 */                 httpcondition = "GT";
/* 744 */               } else if (httpcondition.equals("Equal to"))
/*     */               {
/* 746 */                 httpcondition = "EQ";
/* 747 */               } else if (httpcondition.equals("Not Equal to"))
/*     */               {
/* 749 */                 httpcondition = "NE";
/*     */               }
/* 751 */               else if (httpcondition.equals("Less than"))
/*     */               {
/* 753 */                 httpcondition = "LT";
/*     */               }
/* 755 */               else if (httpcondition.equals("Less than/equal"))
/*     */               {
/* 757 */                 httpcondition = "LE";
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 764 */       XMLNode authinfo = node.getChildNode("AuthInfo");
/* 765 */       if (authinfo != null)
/*     */       {
/* 767 */         username = (String)authinfo.getAttribute("username");
/* 768 */         password = (String)authinfo.getAttribute("password");
/*     */       }
/*     */       
/* 771 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/* 772 */       AMManagedObject ammo = new AMManagedObject();
/* 773 */       ammo.setRESOURCENAME(FormatUtil.getTrimmedText(url, 2000));
/* 774 */       ammo.setType("UrlEle");
/* 775 */       if (displayname.trim().equals(""))
/*     */       {
/* 777 */         ammo.setDISPLAYNAME(FormatUtil.getTrimmedText(url, 200));
/*     */       }
/*     */       else {
/* 780 */         ammo.setDISPLAYNAME(displayname);
/*     */       }
/* 782 */       ammo.setDESCRIPTION("URL Monitor for " + FormatUtil.getTrimmedText(url, 200));
/* 783 */       dao.create(ammo);
/* 784 */       int urlid = ammo.getRESOURCEID();
/* 785 */       AMLog.debug("created urlid" + urlid);
/* 786 */       UrlConfiguration urlmonitor = new UrlConfiguration(urlid, url, username, password, requestparams, method, checkfor, pollinterval);
/* 787 */       UrlConfigurationDao urldao = UrlConfigurationDao.getUrlConfigurationDao();
/* 788 */       urlmonitor.setUnAvailabilityString(unavailabilitystring);
/* 789 */       urlmonitor.setTimeout(timout.intValue() * 1000);
/* 790 */       urlmonitor.setVerifyerror(verifyerror.equals("true"));
/* 791 */       urlmonitor.setCaseSensitive(true);
/* 792 */       urlmonitor.setRegEx(false);
/* 793 */       urlmonitor.setUserAgent("");
/* 794 */       urldao.create(urlmonitor);
/* 795 */       if ((httpcondition != null) && (wnhttpvalue != -1))
/*     */       {
/* 797 */         CreateUrlMonitor.configureThreshold(ammo.getRESOURCEID(), httpcondition, wnhttpvalue);
/*     */       }
/*     */       
/* 800 */       ManagedApplication mo = new ManagedApplication();
/* 801 */       String[] urlids = { String.valueOf(ammo.getRESOURCEID()) };
/* 802 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 803 */       adder.addInterDependentAttributes(ammo.getRESOURCEID());
/* 804 */       mo.updateManagedApplicationResources(String.valueOf(urlseqid), "junk", urlids, "100");
/* 805 */       return;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 809 */       AMLog.fatal(e);
/*     */       
/* 811 */       throw e;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\urlmonitor\CreateUrlSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */