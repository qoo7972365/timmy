/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JSONFeed
/*     */   extends DispatchAction
/*     */ {
/*  34 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward createMonitorGroupFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  39 */     response.setContentType("text/html; charset=UTF-8");
/*  40 */     ArrayList mgList = new ArrayList();
/*  41 */     ArrayList rscIdList = new ArrayList();
/*  42 */     ArrayList mgAttribList = new ArrayList();
/*  43 */     Properties alertStatus = new Properties();
/*     */     
/*  45 */     String mgAvailability = "17";
/*  46 */     String mgHealth = "18";
/*     */     
/*  48 */     mgAttribList.add(mgAvailability);
/*  49 */     mgAttribList.add(mgHealth);
/*     */     
/*     */     try
/*     */     {
/*  53 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/*  54 */         if (Constants.isUserResourceEnabled()) {
/*  55 */           String userid = Constants.getLoginUserid(request);
/*  56 */           mgList = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID, AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID order by RESOURCENAME");
/*     */         } else {
/*  58 */           mgList = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID, AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION_OWNERS.HAID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + request.getRemoteUser() + "' order by RESOURCENAME");
/*     */         }
/*     */       }
/*     */       else {
/*  62 */         mgList = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID, AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID order by RESOURCENAME");
/*     */       }
/*     */       
/*  65 */       for (int i = 0; i < mgList.size(); i++)
/*     */       {
/*  67 */         ArrayList mgRow = (ArrayList)mgList.get(i);
/*  68 */         String resourceId = (String)mgRow.get(6);
/*  69 */         rscIdList.add(resourceId);
/*     */       }
/*     */       
/*  72 */       alertStatus = FaultUtil.getStatus(rscIdList, mgAttribList);
/*     */       
/*     */ 
/*     */ 
/*  76 */       StringBuffer jsonStr = new StringBuffer();
/*     */       
/*  78 */       if (mgList.size() > 0)
/*     */       {
/*  80 */         jsonStr.append("var mgJson = '{\"MonitorGroup\": [ {");
/*     */       }
/*     */       
/*  83 */       for (int i = 0; i < mgList.size(); i++)
/*     */       {
/*     */ 
/*  86 */         ArrayList mgRow = (ArrayList)mgList.get(i);
/*  87 */         String resourceName = (String)mgRow.get(1);
/*  88 */         resourceName = URLEncoder.encode(resourceName);
/*  89 */         jsonStr.append("\"NAME\": \"" + resourceName + "\",");
/*  90 */         String resourceId = (String)mgRow.get(6);
/*     */         
/*  92 */         String mgHealthState = (String)alertStatus.get(resourceId + "#" + mgHealth);
/*  93 */         if (mgHealthState != null)
/*     */         {
/*  95 */           if (mgHealthState.equals("1")) {
/*  96 */             mgHealthState = "Critical";
/*  97 */           } else if (mgHealthState.equals("4")) {
/*  98 */             mgHealthState = "Warning";
/*     */           } else
/* 100 */             mgHealthState = "Clear";
/*     */         }
/* 102 */         if (mgHealthState == null) {
/* 103 */           mgHealthState = "-";
/*     */         }
/*     */         
/* 106 */         jsonStr.append("\"HEALTH\" : \"" + mgHealthState + "\",");
/*     */         
/* 108 */         String mgAvailState = (String)alertStatus.get(resourceId + "#" + mgAvailability);
/* 109 */         if (mgAvailState != null)
/*     */         {
/* 111 */           if (mgAvailState.equals("1")) {
/* 112 */             mgAvailState = "Down";
/*     */           } else {
/* 114 */             mgAvailState = "Up";
/*     */           }
/*     */         }
/* 117 */         if (mgAvailState == null) {
/* 118 */           mgAvailState = "-";
/*     */         }
/* 120 */         jsonStr.append("\"AVAILABILITY\" : \"" + mgAvailState + "\",");
/*     */         
/* 122 */         String rcaHealthMsg = (String)alertStatus.get(resourceId + "#" + mgHealth + "#" + "MESSAGE");
/* 123 */         if (rcaHealthMsg != null) {
/* 124 */           rcaHealthMsg = filterString(rcaHealthMsg);
/*     */         }
/* 126 */         if (rcaHealthMsg == null) {
/* 127 */           rcaHealthMsg = "-";
/*     */         }
/*     */         
/* 130 */         jsonStr.append("\"HEALTHRCA\" : \"" + FormatUtil.findReplace(rcaHealthMsg, "'", "\\'") + "\",");
/* 131 */         String rcaAvailMsg = (String)alertStatus.get(resourceId + "#" + mgAvailability + "#" + "MESSAGE");
/* 132 */         if (rcaAvailMsg != null) {
/* 133 */           rcaAvailMsg = filterString(rcaAvailMsg);
/*     */         }
/* 135 */         if (rcaAvailMsg == null) {
/* 136 */           rcaAvailMsg = "-";
/*     */         }
/* 138 */         jsonStr.append("\"AVAILRCA\" : \"" + FormatUtil.findReplace(rcaAvailMsg, "'", "\\'") + "\",");
/* 139 */         String monsInErr = getMonitorStatus(resourceId);
/*     */         
/* 141 */         if (monsInErr == null) {
/* 142 */           monsInErr = "-";
/*     */         }
/*     */         
/* 145 */         jsonStr.append("\"MONITORSTATUS\" : \"" + monsInErr + "\"}");
/*     */         
/* 147 */         if (i + 1 < mgList.size()) {
/* 148 */           jsonStr.append(",{");
/*     */         }
/*     */       }
/*     */       
/* 152 */       if (mgList.size() > 0) {
/* 153 */         jsonStr.append(" ] }' ");
/* 154 */         jsonStr.append(";");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 159 */       PrintWriter out = response.getWriter();
/* 160 */       out.println(jsonStr.toString());
/*     */ 
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/*     */ 
/* 166 */       exc.printStackTrace();
/*     */       
/* 168 */       PrintWriter out = response.getWriter();
/* 169 */       String jsonStr = "var mgJsonErr= Error while generating json feed for monitorgroups.";
/* 170 */       out.println(jsonStr);
/*     */     }
/*     */     
/* 173 */     String monJson = getMonitorsJSONFeed(mapping, form, request, response);
/*     */     
/* 175 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getMonitorsJSONFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 182 */     response.setContentType("text/html; charset=UTF-8");
/* 183 */     String privilegeCondition = "";
/* 184 */     String query = null;
/* 185 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/* 186 */       if (Constants.isUserResourceEnabled()) {
/* 187 */         String userid = Constants.getLoginUserid(request);
/* 188 */         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,AM_ManagedObject.resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType, AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " and AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and (AM_ManagedResourceType.RESOURCEGROUP='APP' or AM_ManagedResourceType.RESOURCEGROUP='CAM' or AM_ManagedResourceType.RESOURCEGROUP='DBS' or AM_ManagedResourceType.RESOURCEGROUP='SER' or AM_ManagedResourceType.RESOURCEGROUP='SYS' or AM_ManagedResourceType.RESOURCEGROUP='URL' or AM_ManagedResourceType.RESOURCEGROUP='MS' or AM_ManagedResourceType.RESOURCEGROUP='TM') and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " group by AM_ManagedObject.TYPE,resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.IMAGEPATH";
/*     */       } else {
/* 190 */         privilegeCondition = " and " + ManagedApplication.getCondition("RESOURCEID", ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/* 191 */         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and (AM_ManagedResourceType.RESOURCEGROUP='APP' or AM_ManagedResourceType.RESOURCEGROUP='CAM' or AM_ManagedResourceType.RESOURCEGROUP='DBS' or AM_ManagedResourceType.RESOURCEGROUP='SER' or AM_ManagedResourceType.RESOURCEGROUP='SYS' or AM_ManagedResourceType.RESOURCEGROUP='URL' or AM_ManagedResourceType.RESOURCEGROUP='MS' or AM_ManagedResourceType.RESOURCEGROUP='TM') and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " " + privilegeCondition + " group by AM_ManagedObject.TYPE,resourcename,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID,resourceid,AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.IMAGEPATH";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 197 */     ArrayList monList = this.mo.getRows(query);
/*     */     
/* 199 */     Hashtable availabilitykeys = (Hashtable)request.getSession().getServletContext().getAttribute("availabilitykeys");
/* 200 */     Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/*     */     
/*     */ 
/*     */ 
/* 204 */     Properties alertStatus = getAlerts(monList, availabilitykeys, healthkeys);
/*     */     
/*     */ 
/* 207 */     StringBuffer jsonStr = new StringBuffer();
/* 208 */     int monListSize = monList.size();
/* 209 */     if (monListSize > 0)
/*     */     {
/* 211 */       jsonStr.append("var monJson = '{\"Monitor\": [ {");
/*     */     }
/*     */     
/* 214 */     for (int i = 0; i < monListSize; i++)
/*     */     {
/* 216 */       ArrayList monRow = (ArrayList)monList.get(i);
/* 217 */       String resourceName = (String)monRow.get(0);
/* 218 */       String displayName = (String)monRow.get(5);
/* 219 */       String shortName = (String)monRow.get(3);
/* 220 */       String imagePath = (String)monRow.get(8);
/* 221 */       String resourceId = (String)monRow.get(6);
/* 222 */       if (shortName == null) {
/* 223 */         shortName = "-";
/*     */       }
/* 225 */       if (displayName == null) {
/* 226 */         displayName = "-";
/*     */       }
/* 228 */       if (resourceName == null) {
/* 229 */         resourceName = "-";
/*     */       }
/* 231 */       jsonStr.append("\"NAME\": \"" + FormatUtil.findReplace(resourceName, "'", "\\'") + "\",");
/* 232 */       jsonStr.append("\"DISPLAYNAME\": \"" + FormatUtil.findReplace(displayName, "'", "\\'") + "\",");
/* 233 */       jsonStr.append("\"SHORTNAME\": \"" + FormatUtil.findReplace(shortName, "'", "\\'") + "\",");
/* 234 */       jsonStr.append("\"RESOURCEID\": \"" + resourceId + "\",");
/* 235 */       jsonStr.append("\"IMAGEPATH\": \"" + imagePath + "\",");
/* 236 */       String resourceType = (String)monRow.get(7);
/* 237 */       String monHealthAttr = (String)healthkeys.get(resourceType);
/* 238 */       String monHealthState = (String)alertStatus.get(resourceId + "#" + monHealthAttr);
/* 239 */       if (monHealthState != null)
/*     */       {
/* 241 */         if (monHealthState.equals("1")) {
/* 242 */           monHealthState = "Critical";
/* 243 */         } else if (monHealthState.equals("4")) {
/* 244 */           monHealthState = "Warning";
/*     */         } else {
/* 246 */           monHealthState = "Clear";
/*     */         }
/*     */       }
/* 249 */       if (monHealthState == null) {
/* 250 */         monHealthState = "-";
/*     */       }
/* 252 */       jsonStr.append("\"HEALTH\" : \"" + monHealthState + "\",");
/*     */       
/* 254 */       String monAvailAttr = (String)availabilitykeys.get(resourceType);
/* 255 */       String monAvailState = (String)alertStatus.get(resourceId + "#" + monAvailAttr);
/* 256 */       if (monAvailState != null)
/*     */       {
/* 258 */         if (monAvailState.equals("1")) {
/* 259 */           monAvailState = "Down";
/*     */         } else
/* 261 */           monAvailState = "Up";
/*     */       }
/* 263 */       if (monAvailState == null) {
/* 264 */         monAvailState = "-";
/*     */       }
/* 266 */       jsonStr.append("\"AVAILABILITY\" : \"" + monAvailState + "\",");
/*     */       
/* 268 */       String rcaHealthMsg = (String)alertStatus.get(resourceId + "#" + monHealthAttr + "#" + "MESSAGE");
/* 269 */       if (rcaHealthMsg != null) {
/* 270 */         rcaHealthMsg = filterString(rcaHealthMsg);
/*     */       }
/* 272 */       if (rcaHealthMsg == null) {
/* 273 */         rcaHealthMsg = "-";
/*     */       }
/*     */       
/* 276 */       jsonStr.append("\"HEALTHRCA\" : \"" + FormatUtil.findReplace(rcaHealthMsg, "'", "\\'") + "\",");
/* 277 */       String rcaAvailMsg = (String)alertStatus.get(resourceId + "#" + monAvailAttr + "#" + "MESSAGE");
/* 278 */       if (rcaAvailMsg != null) {
/* 279 */         rcaAvailMsg = filterString(rcaAvailMsg);
/*     */       }
/* 281 */       if (rcaAvailMsg == null) {
/* 282 */         rcaAvailMsg = "-";
/*     */       }
/*     */       
/* 285 */       jsonStr.append("\"AVAILRCA\" : \"" + FormatUtil.findReplace(rcaAvailMsg, "'", "\\'") + "\"}");
/* 286 */       if (i + 1 < monListSize) {
/* 287 */         jsonStr.append(",{");
/*     */       }
/*     */     }
/*     */     
/* 291 */     if (monListSize > 0)
/*     */     {
/* 293 */       jsonStr.append(" ] }' ");
/* 294 */       jsonStr.append(";");
/*     */     }
/*     */     
/* 297 */     PrintWriter out = response.getWriter();
/* 298 */     out.println(jsonStr.toString());
/* 299 */     return jsonStr.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void getConsoleJSONFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 307 */     StringBuffer jsonStr = new StringBuffer();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 312 */       String toReturn = request.getParameter("toReturn");
/* 313 */       String mgId = request.getParameter("mgId");
/* 314 */       String monType = request.getParameter("category");
/*     */       
/* 316 */       String query = null;
/* 317 */       if ((toReturn != null) && (toReturn.equals("allMGResource")))
/*     */       {
/* 319 */         query = "select RESOURCENAME,RESOURCEID,TYPE from AM_ManagedObject where AM_ManagedObject.TYPE='HAI'";
/*     */       }
/* 321 */       else if ((toReturn != null) && (toReturn.equals("allMonInMG")))
/*     */       {
/* 323 */         query = "select RESOURCENAME,RESOURCEID,TYPE from AM_ManagedObject, AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID='" + mgId + "' and AM_ManagedObject.TYPE in " + Constants.serverTypes;
/*     */       }
/* 325 */       else if ((toReturn != null) && (toReturn.equals("OpManResource")))
/*     */       {
/* 327 */         if (monType != null)
/*     */         {
/* 329 */           monType = "OpManager-" + monType;
/* 330 */           query = "select RESOURCENAME,RESOURCEID,SUBSTRING(AM_ManagedObject.TYPE,11),AM_AssociatedExtDevices.IPADDRESS from AM_ManagedObject, AM_PARENTCHILDMAPPER, AM_AssociatedExtDevices, ExternalDeviceDetails where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID='" + mgId + "' and AM_ManagedObject.TYPE like 'OpManager-%' and AM_AssociatedExtDevices.RESID=AM_PARENTCHILDMAPPER.CHILDID and AM_AssociatedExtDevices.IPADDRESS=ExternalDeviceDetails.IPADDRESS and ExternalDeviceDetails.CATEGORY='" + monType + "'";
/*     */ 
/*     */ 
/*     */         }
/* 334 */         else if (mgId == null)
/*     */         {
/* 336 */           query = "select RESOURCENAME,RESOURCEID,SUBSTRING(TYPE,11),IPADDRESS from AM_ManagedObject,AM_AssociatedExtDevices where AM_ManagedObject.TYPE like 'OpManager-%' and AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID";
/*     */         }
/*     */         else
/*     */         {
/* 340 */           query = "select RESOURCENAME,RESOURCEID,SUBSTRING(TYPE,11),IPADDRESS from AM_ManagedObject,AM_AssociatedExtDevices,AM_PARENTCHILDMAPPER where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_PARENTCHILDMAPPER.PARENTID='" + mgId + "' and AM_ManagedObject.TYPE like 'OpManager-%'";
/*     */         }
/*     */       }
/*     */       
/* 344 */       ArrayList monList = this.mo.getRows(query);
/*     */       
/* 346 */       int monListSize = monList.size();
/* 347 */       if (monListSize > 0)
/*     */       {
/* 349 */         jsonStr.append("{\"Monitor\": [ {");
/*     */       }
/* 351 */       for (int i = 0; i < monListSize; i++)
/*     */       {
/* 353 */         ArrayList monRow = (ArrayList)monList.get(i);
/* 354 */         String resourceName = (String)monRow.get(0);
/* 355 */         jsonStr.append("\"RESOURCENAME\": \"" + resourceName + "\",");
/* 356 */         String resId = (String)monRow.get(1);
/* 357 */         jsonStr.append("\"RESOURCEID\": \"" + resId + "\",");
/* 358 */         if ((toReturn != null) && (toReturn.equals("OpManResource")))
/*     */         {
/* 360 */           String ipAddress = (String)monRow.get(3);
/* 361 */           jsonStr.append("\"IPADDRESS\": \"" + ipAddress + "\",");
/*     */         }
/* 363 */         String type = (String)monRow.get(2);
/* 364 */         jsonStr.append("\"TYPE\": \"" + type + "\"}");
/* 365 */         if (i + 1 < monListSize)
/*     */         {
/* 367 */           jsonStr.append(",{");
/*     */         }
/*     */       }
/*     */       
/* 371 */       if (monListSize > 0)
/*     */       {
/* 373 */         jsonStr.append(" ] } ");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 379 */       e.printStackTrace();
/*     */     }
/*     */     
/* 382 */     ServletOutputStream out = response.getOutputStream();
/* 383 */     out.println(jsonStr.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void getParentGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 392 */     String haid = request.getParameter("haid");
/* 393 */     ServletOutputStream out = response.getOutputStream();
/* 394 */     out.println(ReportUtil.getAllMGNames(new JSONObject(), haid, 0).toString());
/*     */   }
/*     */   
/*     */ 
/*     */   public void getMonitorCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 401 */     JSONObject count = new JSONObject();
/* 402 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 403 */     ResultSet result = null;
/* 404 */     String haid = request.getParameter("haid");
/* 405 */     if (haid == null)
/*     */     {
/* 407 */       haid = "0";
/*     */     }
/* 409 */     String query = "select \"SYS\",count(*) from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and type in" + Constants.serverTypes + " union select \"APP\",count(*) from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and type not in " + Constants.serverTypes + " and type not like '%OpManager%' union  select \"NWD\",count(*) from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and type like '%OpManager%'";
/*     */     
/*     */     try
/*     */     {
/* 413 */       result = AMConnectionPool.executeQueryStmt(query);
/* 414 */       while (result.next())
/*     */       {
/* 416 */         count.append(result.getString(1), result.getString(2));
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       try
/*     */       {
/* 427 */         if (result != null)
/*     */         {
/* 429 */           result.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 434 */         e.printStackTrace();
/*     */       }
/*     */       
/* 437 */       out = response.getOutputStream();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 421 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 427 */         if (result != null)
/*     */         {
/* 429 */           result.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 434 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     ServletOutputStream out;
/* 438 */     out.println(count.toString());
/*     */   }
/*     */   
/*     */ 
/*     */   private String getMonitorStatus(String resourceId)
/*     */   {
/* 444 */     String noOfMons = "";
/* 445 */     String monsInErr = "";
/*     */     try
/*     */     {
/* 448 */       Map monitorsNosAndErrors = DataCollectionDBUtil.getMonitorGroupsInfo();
/* 449 */       Map currMGInfo = (Map)monitorsNosAndErrors.get(resourceId);
/*     */       
/* 451 */       if (currMGInfo != null)
/*     */       {
/* 453 */         noOfMons = (String)currMGInfo.get("TOTALCHILDCOUNT");
/* 454 */         if ((noOfMons != null) && (noOfMons.equals("0")))
/*     */         {
/* 456 */           monsInErr = "0";
/*     */         }
/*     */         else
/*     */         {
/* 460 */           monsInErr = " " + (String)currMGInfo.get("CHILDRENINERROR") + "/";
/* 461 */           if (monsInErr.equals(" None/"))
/*     */           {
/* 463 */             monsInErr = "0/";
/*     */           }
/* 465 */           monsInErr = monsInErr + noOfMons + " in error";
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (Exception exc)
/*     */     {
/* 472 */       exc.printStackTrace();
/*     */     }
/*     */     
/* 475 */     return monsInErr;
/*     */   }
/*     */   
/*     */   public Properties getAlerts(ArrayList monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*     */   {
/* 480 */     ArrayList attribIDs = new ArrayList();
/* 481 */     ArrayList resIDs = new ArrayList();
/*     */     
/* 483 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*     */     {
/* 485 */       ArrayList row = (ArrayList)monitorList.get(j);
/* 486 */       String resourceid = (String)row.get(6);
/* 487 */       resIDs.add(resourceid);
/* 488 */       String resourceType = (String)row.get(7);
/* 489 */       Object healthkey = healthkeys.get(resourceType);
/* 490 */       if (attribIDs.indexOf(healthkey) == -1)
/*     */       {
/* 492 */         attribIDs.add(healthkey);
/*     */       }
/* 494 */       Object availabilitykey = availabilitykeys.get(resourceType);
/* 495 */       if (attribIDs.indexOf(availabilitykey) == -1)
/*     */       {
/* 497 */         attribIDs.add(availabilitykey);
/*     */       }
/*     */     }
/* 500 */     Properties alert = FaultUtil.getStatus(resIDs, attribIDs);
/* 501 */     return alert;
/*     */   }
/*     */   
/*     */   public String filterString(String fltStr) {
/* 505 */     fltStr = fltStr.replaceAll("<a class=\"resourcename\" href='", "");
/* 506 */     fltStr = fltStr.replaceAll("' target=_blank><font color=red>Link for Response of this url</font></a>", "");
/* 507 */     fltStr = fltStr.replaceAll("\"", "'");
/* 508 */     return fltStr;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\JSONFeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */