/*     */ package com.adventnet.appmanager.utils.client;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.customfields.MyFields;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.struts.actions.MyPageAction;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.ReportUtil;
/*     */ import com.manageengine.apminsight.client.struts.actionhelpers.APMinsightBaseActionHelper;
/*     */ import com.manageengine.apminsight.client.struts.bean.ApdexInfo;
/*     */ import com.manageengine.apminsight.client.struts.bean.RTInfo;
/*     */ import com.manageengine.apminsight.server.RequestInfo;
/*     */ import com.manageengine.apminsight.server.TimeWindow;
/*     */ import com.manageengine.apminsight.server.dao.ResourceType;
/*     */ import com.manageengine.apminsight.server.db.APMInsightDBUtil;
/*     */ import com.manageengine.apminsight.server.db.PartitionContext;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GetCurrentDataUtil
/*     */ {
/*     */   public static String getCurrentData(HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  46 */     String monitorResourceName = "";
/*  47 */     AMLog.info("inside  get current  details  method");
/*  48 */     String uri = request.getRequestURI();
/*  49 */     boolean isJsonFormat = uri.contains("json");
/*  50 */     if (isJsonFormat)
/*     */     {
/*  52 */       response.setContentType("text/plain; charset=UTF-8");
/*     */     }
/*     */     else
/*     */     {
/*  56 */       response.setContentType("text/xml; charset=UTF-8");
/*     */     }
/*  58 */     String resID = "";
/*  59 */     int intResID = 0;
/*  60 */     String xmlString = "";
/*  61 */     String errorCode = "";
/*  62 */     String message = "";
/*  63 */     if (request.getParameter("resourceid") != null)
/*     */     {
/*  65 */       resID = request.getParameter("resourceid");
/*  66 */       if ((CommonAPIUtil.isOperatorRole(request)) && (!CommonAPIUtil.isAssociatedToOperator(request, resID))) {
/*  67 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*  68 */         return xmlString;
/*     */       }
/*     */       
/*     */     }
/*     */     else
/*     */     {
/*  74 */       AMLog.debug("REST API : Improper resourceid in the request.1==>" + request.getParameter("resourceid"));
/*  75 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*  76 */       return xmlString;
/*     */     }
/*     */     try
/*     */     {
/*  80 */       intResID = Integer.parseInt(resID);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  84 */       AMLog.debug("REST API : Improper resourceid in the request.2==>" + request.getParameter("resourceid"));
/*  85 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*     */     }
/*     */     
/*  88 */     HashMap widgetProps = new HashMap();
/*  89 */     HashMap widgetData = new HashMap();
/*  90 */     AMLog.debug("REST API : inside getCurrentData method");
/*  91 */     MyPageAction mpa = new MyPageAction();
/*  92 */     ResultSet rs = null;
/*  93 */     ResultSet rs1 = null;
/*     */     
/*  95 */     HashMap result = new HashMap();
/*  96 */     result.put("uri", uri);
/*  97 */     result.put("response-code", "4000");
/*  98 */     result.put("sortingParam", "DISPLAYNAME");
/*  99 */     result.put("nodeName", "Monitorinfo");
/* 100 */     result.put("subNodeName", "Attribute,CHILDMONITORS,CHILDMONITORINFO");
/* 101 */     AMLog.info("inside  get current  details  method2");
/*     */     
/* 103 */     AMLog.debug("REST API : root node uri" + uri);
/* 104 */     String query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME as typeshortname,AM_ManagedObject.DISPLAYNAME,InetService.TARGETNAME,InetService.TARGETADDRESS,AM_ManagedObject.DESCRIPTION,(case when AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID then 'false' else 'true' end) as Managed FROM AM_ManagedResourceType,AM_ManagedObject   left outer join InetService on AM_ManagedObject.RESOURCENAME = InetService.NAME  LEFT JOIN AM_UnManagedNodes ON (AM_ManagedObject .RESOURCEID = AM_UnManagedNodes.resid) where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " and RESOURCEID='" + resID + "'";
/* 105 */     String query1 = DBQueryUtil.getTopNValues("select COLLECTIONTIME from AM_ManagedObjectData where RESID='" + resID + "' order by COLLECTIONTIME desc", "1");
/* 106 */     String typeStr = "";
/* 107 */     String typeStrdisp = "";
/* 108 */     String dispStr = "";
/* 109 */     String targetName = "";
/* 110 */     String targetAddr = "";
/* 111 */     long time = 0L;
/* 112 */     String timeStr = "";
/* 113 */     ArrayList<Hashtable> monitorDetails = new ArrayList();
/* 114 */     Hashtable monitorInfo = new Hashtable();
/*     */     try
/*     */     {
/* 117 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 118 */       if (rs.next())
/*     */       {
/* 120 */         typeStr = rs.getString("TYPE");
/* 121 */         if (typeStr.contains("Window"))
/*     */         {
/* 123 */           typeStrdisp = "Windows Server";
/*     */         }
/* 125 */         else if (typeStr.contains("node"))
/*     */         {
/* 127 */           typeStrdisp = "Unknown";
/*     */         }
/*     */         else
/*     */         {
/* 131 */           typeStrdisp = typeStr;
/*     */         }
/* 133 */         dispStr = rs.getString("DISPLAYNAME");
/* 134 */         targetName = rs.getString("TARGETNAME") != null ? rs.getString("TARGETNAME") : "";
/* 135 */         targetAddr = rs.getString("TARGETADDRESS") != null ? rs.getString("TARGETADDRESS") : "";
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 140 */         AMLog.debug("REST API : Improper resourceid in the request.==>" + request.getParameter("resourceid"));
/* 141 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 142 */         return xmlString;
/*     */       }
/*     */       
/* 145 */       Properties sysProps = CommonAPIUtil.getServerDetails(typeStr, resID);
/*     */       
/* 147 */       todaysAvail = "100";
/* 148 */       String todaysSchdDownTime = "0";
/* 149 */       String todaysUnmng = "0";
/* 150 */       String todaysUnAvail = "0";
/* 151 */       Properties todaysAvailProps = new Properties();
/* 152 */       if (resID != null)
/*     */       {
/* 154 */         todaysAvailProps = ReportUtilities.getTodaysAvailabilityForMonitors(resID);
/*     */         
/* 156 */         todaysAvail = todaysAvailProps.getProperty("available");
/* 157 */         todaysSchdDownTime = todaysAvailProps.getProperty("OverallScheduledDowntime");
/* 158 */         todaysUnmng = todaysAvailProps.getProperty("OverallUnmanagedTime");
/* 159 */         todaysUnAvail = todaysAvailProps.getProperty("unavailable");
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 164 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 165 */         if (rs1.next())
/*     */         {
/* 167 */           timeStr = FormatUtil.formatDT(rs1.getString("COLLECTIONTIME"));
/*     */ 
/*     */ 
/*     */         }
/* 171 */         else if (typeStr.equals("APM-Insight-Instance"))
/*     */         {
/* 173 */           timeStr = FormatUtil.formatDT(APMInsightDBUtil.getAgentLastCommunicatedTime(Long.valueOf(Long.parseLong(resID))) + "");
/*     */         }
/*     */         else
/*     */         {
/* 177 */           timeStr = FormatUtil.getString("Wait till next poll");
/*     */         }
/*     */         
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/* 183 */         ex.printStackTrace();
/* 184 */         AMLog.debug("REST API : Server error");
/* 185 */         return xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*     */       }
/*     */       finally {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 192 */       AMLog.info("inside  get current  details  method3");
/*     */       
/* 194 */       monitorResourceName = rs.getString("RESOURCENAME");
/* 195 */       monitorInfo.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/* 196 */       monitorInfo.put("RESOURCEID", resID);
/* 197 */       monitorInfo.put("TYPESHORTNAME", rs.getString("typeshortname"));
/* 198 */       monitorInfo.put("TYPE", rs.getString("TYPE"));
/* 199 */       monitorInfo.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 200 */       monitorInfo.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/* 201 */       monitorInfo.put("MANAGED", rs.getString("Managed"));
/* 202 */       monitorInfo.put("IMAGEPATH", rs.getString("IMAGEPATH"));
/* 203 */       monitorInfo.put("LASTPOLLEDTIME", timeStr);
/* 204 */       monitorInfo.put("TARGETNAME", targetName);
/* 205 */       monitorInfo.put("TARGETADDRESS", targetAddr);
/* 206 */       monitorInfo.put("TODAYAVAILPERCENT", todaysAvail);
/* 207 */       monitorInfo.put("TODAYSCHEDDOWNPERCENT", todaysSchdDownTime);
/* 208 */       monitorInfo.put("TODAYUNMANGDPERCENT", todaysUnmng);
/* 209 */       monitorInfo.put("TODAYUNAVAILPERCENT", todaysUnAvail);
/* 210 */       monitorInfo.put("CPUUTIL", sysProps.getProperty("CPUUTIL"));
/* 211 */       monitorInfo.put("PHYMEMUTIL", sysProps.getProperty("PHYMEMUTIL"));
/* 212 */       monitorInfo.put("DISKUTIL", sysProps.getProperty("DISKUTIL"));
/*     */       
/* 214 */       String healthid = AMAttributesCache.getHealthId(typeStr);
/* 215 */       String availid = AMAttributesCache.getAvailabilityId(typeStr);
/* 216 */       Object healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(resID, typeStr);
/* 217 */       String detailsURL = "/showresource.do?resourceid=" + rs.getString("RESOURCEID") + "&method=showResourceForResourceID&PRINTER_FRIENDLY=true";
/* 218 */       String rcaURL = "/jsp/RCA.jsp?resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + healthid;
/*     */       
/* 220 */       monitorInfo.put("DetailsPageURL", detailsURL);
/* 221 */       monitorInfo.put("RCAPageURL", rcaURL);
/* 222 */       monitorInfo.put("HEALTHATTRIBUTEID", healthid);
/* 223 */       monitorInfo.put("HEALTHSEVERITY", "" + (String)((HashMap)healthAvailEntitys).get("HEALTHSEVERITY"));
/* 224 */       monitorInfo.put("HEALTHSTATUS", "" + (String)((HashMap)healthAvailEntitys).get("HEALTHSTATUS"));
/* 225 */       monitorInfo.put("HEALTHMESSAGE", "" + (String)((HashMap)healthAvailEntitys).get("HEALTHMESSAGE"));
/* 226 */       monitorInfo.put("AVAILABILITYATTRIBUTEID", availid);
/* 227 */       monitorInfo.put("AVAILABILITYSEVERITY", "" + (String)((HashMap)healthAvailEntitys).get("AVAILABILITYSEVERITY"));
/* 228 */       monitorInfo.put("AVAILABILITYSTATUS", "" + (String)((HashMap)healthAvailEntitys).get("AVAILABILITYSTATUS"));
/* 229 */       monitorInfo.put("AVAILABILITYMESSAGE", "" + (String)((HashMap)healthAvailEntitys).get("AVAILABILITYMESSAGE"));
/* 230 */       if ("true".equalsIgnoreCase(request.getParameter("customFields"))) {
/* 231 */         customFields = MyFields.getCustomFields(intResID);
/* 232 */         for (String fieldName : customFields.keySet()) {
/* 233 */           if (isJsonFormat) {
/* 234 */             monitorInfo.put(StringEscapeUtils.escapeJson(fieldName), customFields.get(fieldName));
/*     */           } else {
/* 236 */             if ((fieldName != null) && ((Character.isDigit(fieldName.charAt(0))) || (fieldName.startsWith("'")))) {
/* 237 */               fieldName = "_" + fieldName;
/*     */             }
/* 239 */             monitorInfo.put(fieldName.replaceAll(" ", ""), customFields.get(fieldName));
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/*     */       String todaysAvail;
/*     */       Map<String, String> customFields;
/* 246 */       ex.printStackTrace();
/* 247 */       AMLog.debug("REST API : Server error");
/* 248 */       return xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*     */     }
/*     */     finally
/*     */     {
/* 252 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 254 */     Object toreturn = new ArrayList();
/* 255 */     ArrayList attriArr = new ArrayList();
/* 256 */     toreturn = ReportUtil.getAttributesForResourcetype(typeStr);
/* 257 */     if (ConfMonitorConfiguration.getInstance().isConfMonitor(typeStr)) {
/* 258 */       addStringAndConfigAttributes((List)toreturn, typeStr);
/*     */     }
/* 260 */     AMLog.info("inside  get current  details  method4");
/*     */     
/* 262 */     ArrayList<Hashtable<?, ?>> attribList = new ArrayList();
/* 263 */     if (typeStr.equals("APM-Insight-Instance"))
/*     */     {
/* 265 */       attribList = getAPMInsightDetails(resID);
/* 266 */       monitorInfo.put("Attribute", attribList);
/*     */     }
/*     */     else
/*     */     {
/* 270 */       attribList = getAttributeDetails((List)toreturn, resID, typeStr);
/* 271 */       monitorInfo.put("Attribute", attribList);
/*     */       
/* 273 */       ArrayList childMonitorList = new ArrayList();
/* 274 */       Hashtable childMoInfo = new Hashtable();
/*     */       
/* 276 */       ArrayList<Hashtable<?, ?>> childMoList = new ArrayList();
/* 277 */       String childQuery = "select resourceid,resourcename,displayname,type from AM_PARENTCHILDMAPPER map, AM_ManagedObject as mo where map.parentid=" + resID + " and mo.resourceid=map.childid and mo.type not like 'HOST_CONF_%'  and type!='CPU Core'";
/* 278 */       AMLog.debug("child  query:" + childQuery);
/* 279 */       ResultSet rs5 = null;
/*     */       
/*     */       try
/*     */       {
/* 283 */         rs5 = AMConnectionPool.executeQueryStmt(childQuery);
/* 284 */         while (rs5.next())
/*     */         {
/* 286 */           Hashtable childMoDetails = new Hashtable();
/* 287 */           HashMap<String, String> healthAvailEntitys = CommonAPIUtil.getHealthAvailabilityDetails(rs5.getString("resourceid"), rs5.getString("type"));
/* 288 */           childMoDetails.put("RESOURCEID", rs5.getString("resourceid"));
/* 289 */           String childMonitorName = rs5.getString("displayname");
/* 290 */           childMonitorName = childMonitorName.replaceAll(monitorResourceName + ":", "");
/* 291 */           if (childMonitorName.startsWith("DiskUtilization-"))
/*     */           {
/* 293 */             childMonitorName = childMonitorName.replaceAll("DiskUtilization-", "");
/*     */           }
/* 295 */           else if ((childMonitorName.startsWith("NetInterface ")) && (childMonitorName.contains("-")))
/*     */           {
/* 297 */             childMonitorName = childMonitorName.substring(childMonitorName.indexOf("-") + 1);
/*     */           }
/* 299 */           else if (childMonitorName.endsWith("_ROW"))
/*     */           {
/* 301 */             childMonitorName = childMonitorName.substring(0, childMonitorName.lastIndexOf("_ROW"));
/*     */           }
/* 303 */           childMoDetails.put("DISPLAYNAME", FormattedMOType(childMonitorName));
/* 304 */           childMoDetails.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 305 */           childMoDetails.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/*     */           
/*     */ 
/*     */ 
/* 309 */           String childMonitorType = rs5.getString("type");
/*     */           
/* 311 */           ArrayList childMoType = (ArrayList)childMoInfo.get(childMonitorType);
/* 312 */           if (childMoType == null)
/*     */           {
/* 314 */             childMoType = new ArrayList();
/* 315 */             childMoInfo.put(childMonitorType, childMoType);
/*     */           }
/* 317 */           childMoType.add(childMoDetails);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 322 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/* 325 */         AMConnectionPool.closeStatement(rs5);
/*     */       }
/*     */       
/* 328 */       Object itr = childMoInfo.keySet().iterator();
/* 329 */       while (((Iterator)itr).hasNext())
/*     */       {
/* 331 */         String childType = (String)((Iterator)itr).next();
/* 332 */         List childAttributes = null;
/* 333 */         childAttributes = ReportUtil.getAttributesForResourcetype(childType);
/* 334 */         if (ConfMonitorConfiguration.getInstance().isConfMonitor(typeStr)) {
/* 335 */           addStringAndConfigAttributes(childAttributes, childType);
/*     */         }
/* 337 */         String healthid = AMAttributesCache.getHealthId(childType);
/* 338 */         String availid = AMAttributesCache.getAvailabilityId(childType);
/*     */         
/* 340 */         if (!"225".equals(healthid)) {
/* 341 */           ArrayList childMoType = (ArrayList)childMoInfo.get(childType);
/* 342 */           for (int i = 0; i < childMoType.size(); i++)
/*     */           {
/* 344 */             Hashtable childMoDetails = (Hashtable)childMoType.get(i);
/* 345 */             String childMonitorResId = (String)childMoDetails.get("RESOURCEID");
/*     */             
/* 347 */             ArrayList<Hashtable<?, ?>> secattribList = new ArrayList();
/* 348 */             AMLog.info("REST API: childMonitorResId==>" + childMonitorResId);
/* 349 */             AMLog.info("REST API: child Attrbs==>" + childAttributes);
/* 350 */             secattribList = getAttributeDetails(childAttributes, childMonitorResId, childType);
/* 351 */             if (secattribList.size() > 0)
/*     */             {
/* 353 */               childMoDetails.put("CHILDATTRIBUTES", secattribList);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 358 */         Hashtable childMonitorDetails = new Hashtable();
/* 359 */         childMonitorDetails.put("DISPLAYNAME", FormattedMOType(childType));
/* 360 */         childMonitorDetails.put("HEALTHID", healthid != null ? healthid : "NA");
/* 361 */         childMonitorDetails.put("AVAILABILITYID", availid != null ? availid : "NA");
/* 362 */         childMonitorDetails.put("CHILDMONITORINFO", childMoInfo.get(childType));
/* 363 */         AMLog.info("REST API: ChildMonitorInfo for Type" + childMonitorDetails);
/* 364 */         childMonitorList.add(childMonitorDetails);
/*     */       }
/*     */       
/* 367 */       if (childMonitorList.size() > 0)
/*     */       {
/* 369 */         monitorInfo.put("CHILDMONITORS", childMonitorList);
/*     */       }
/*     */     }
/* 372 */     monitorDetails.add(monitorInfo);
/* 373 */     result.put("result", monitorDetails);
/* 374 */     xmlString = CommonAPIUtil.getOutputAsString(result, isJsonFormat);
/* 375 */     return xmlString;
/*     */   }
/*     */   
/*     */   private static ArrayList<Hashtable<?, ?>> getAPMInsightDetails(String resID)
/*     */   {
/* 380 */     long instId = Long.parseLong(resID);
/* 381 */     ArrayList<Hashtable<?, ?>> attrbList = new ArrayList();
/*     */     
/*     */     try
/*     */     {
/* 385 */       AMLog.info("RESTAPI : APMINSIGHT params: instId:" + instId + " ApplicationId:" + APMInsightDBUtil.getAppIdForInsId(instId) + " Timewindow:" + TimeWindow.THREE_HOURS + " resourcetype:" + ResourceType.INSTANCE);
/* 386 */       RequestInfo reqInfo = new RequestInfo(instId, APMInsightDBUtil.getAppIdForInsId(instId), TimeWindow.THREE_HOURS, ResourceType.INSTANCE);
/* 387 */       reqInfo.setResourceType(reqInfo.getScope());
/* 388 */       PartitionContext pCtx = new PartitionContext(reqInfo.getParentId());
/* 389 */       reqInfo.setPartitionContext(pCtx);
/* 390 */       Object[] apdexAndRTInfo = APMinsightBaseActionHelper.getApdexAndRTvalues(Long.parseLong(resID), reqInfo);
/* 391 */       ApdexInfo apdexInfo = (ApdexInfo)apdexAndRTInfo[0];
/* 392 */       RTInfo rtInfo = (RTInfo)apdexAndRTInfo[1];
/*     */       
/* 394 */       String averageResponseTime = rtInfo.getResponseTime() + "";
/* 395 */       String thoughtPut = rtInfo.getThroughPut() + "";
/* 396 */       String requestCount = rtInfo.getRequestCount() + "";
/* 397 */       String apdex = apdexInfo.getApdex() + "";
/* 398 */       String satisfied = apdexInfo.getSatisfied() + "";
/* 399 */       String frustated = apdexInfo.getFrustrated() + "";
/* 400 */       String tolerating = apdexInfo.getTolerating() + "";
/*     */       
/* 402 */       Hashtable<String, String> attrbDetails = new Hashtable();
/* 403 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.avgresponsetime"));
/* 404 */       attrbDetails.put("Value", averageResponseTime);
/* 405 */       attrbDetails.put("Units", " " + FormatUtil.getString("apminsight.units.ms"));
/* 406 */       attrbList.add(attrbDetails);
/*     */       
/* 408 */       attrbDetails = new Hashtable();
/* 409 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.throughput"));
/* 410 */       attrbDetails.put("Value", thoughtPut);
/* 411 */       attrbDetails.put("Units", " " + FormatUtil.getString("apminsight.units.rpm"));
/* 412 */       attrbList.add(attrbDetails);
/*     */       
/* 414 */       attrbDetails = new Hashtable();
/* 415 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.reqcount"));
/* 416 */       attrbDetails.put("Value", requestCount);
/* 417 */       attrbDetails.put("Units", " ");
/* 418 */       attrbList.add(attrbDetails);
/*     */       
/* 420 */       attrbDetails = new Hashtable();
/* 421 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.apdexscore"));
/* 422 */       attrbDetails.put("Value", apdex);
/* 423 */       attrbDetails.put("Units", " ");
/* 424 */       attrbList.add(attrbDetails);
/*     */       
/* 426 */       attrbDetails = new Hashtable();
/* 427 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.home.frustrated"));
/* 428 */       attrbDetails.put("Value", frustated);
/* 429 */       attrbDetails.put("Units", "  %");
/* 430 */       attrbList.add(attrbDetails);
/*     */       
/* 432 */       attrbDetails = new Hashtable();
/* 433 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.home.satisfied"));
/* 434 */       attrbDetails.put("Value", satisfied);
/* 435 */       attrbDetails.put("Units", "  %");
/* 436 */       attrbList.add(attrbDetails);
/*     */       
/* 438 */       attrbDetails = new Hashtable();
/* 439 */       attrbDetails.put("DISPLAYNAME", FormatUtil.getString("apminsight.home.tolerating"));
/* 440 */       attrbDetails.put("Value", tolerating);
/* 441 */       attrbDetails.put("Units", "  %");
/* 442 */       attrbList.add(attrbDetails);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 446 */       e.printStackTrace();
/*     */     }
/* 448 */     return attrbList;
/*     */   }
/*     */   
/*     */   private static String FormattedMOType(String childMonitorType)
/*     */   {
/* 453 */     if (childMonitorType.endsWith("_ROW"))
/*     */     {
/* 455 */       Pattern p = Pattern.compile("([^\\_]+)_(.*)_ROW");
/* 456 */       Matcher m = p.matcher(childMonitorType);
/* 457 */       if (m.matches())
/*     */       {
/* 459 */         return m.group(2);
/*     */       }
/*     */     }
/* 462 */     return FormattedDisplayName(FormatUtil.getString(childMonitorType));
/*     */   }
/*     */   
/*     */   private static String FormattedDisplayName(String dispName)
/*     */   {
/* 467 */     if (dispName.endsWith("'"))
/*     */     {
/* 469 */       Pattern p = Pattern.compile("[^=]+=\\'([^\\']+)\\'");
/* 470 */       Matcher m = p.matcher(dispName);
/* 471 */       if (m.matches())
/*     */       {
/* 473 */         return m.group(1);
/*     */       }
/*     */     }
/* 476 */     return FormatUtil.getString(dispName);
/*     */   }
/*     */   
/*     */   public String getHistoryDetails(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception
/*     */   {
/* 481 */     Hashtable reportform = new Hashtable();
/* 482 */     String monitorResourceName = "";
/* 483 */     AMLog.info("inside  get current  details  method");
/* 484 */     String uri = request.getRequestURI();
/* 485 */     if (isJsonFormat)
/*     */     {
/* 487 */       response.setContentType("text/plain; charset=UTF-8");
/*     */     }
/*     */     else
/*     */     {
/* 491 */       response.setContentType("text/xml; charset=UTF-8");
/*     */     }
/* 493 */     String resID = request.getParameter("resourceid");
/* 494 */     String resourcename = request.getParameter("resourcename");
/* 495 */     String period = request.getParameter("period");
/* 496 */     String attrbId = request.getParameter("attributeID");
/* 497 */     int attID = -1;
/* 498 */     int intResID = 0;
/* 499 */     String xmlString = "";
/* 500 */     String errorCode = "";
/* 501 */     String message = "";
/*     */     try
/*     */     {
/* 504 */       intResID = Integer.parseInt(resID);
/* 505 */       if ((CommonAPIUtil.isOperatorRole(request)) && (!CommonAPIUtil.isAssociatedToOperator(request, resID))) {
/* 506 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 512 */       AMLog.debug("REST API : Improper resourceid in the request.2==>" + resID);
/* 513 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 518 */       attID = Integer.parseInt(attrbId);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 522 */       AMLog.debug("REST API : Improper attributeID in the request.2==>" + attrbId);
/* 523 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainattrerr.msg"), "4037");
/*     */     }
/*     */     
/*     */ 
/* 527 */     HashMap result = new HashMap();
/* 528 */     result.put("uri", uri);
/* 529 */     result.put("response-code", "4000");
/* 530 */     result.put("sortingParam", "ResourceName");
/* 531 */     result.put("nodeName", "Monitorinfo");
/* 532 */     result.put("subNodeName", "RawData,Attribute,ArchiveData,ThresholdDetails");
/*     */     
/* 534 */     HistoryDataAPIUtil hu = new HistoryDataAPIUtil();
/* 535 */     ArrayList<Hashtable> reportlist = new ArrayList();
/* 536 */     Hashtable reportTable = hu.getHistoryData(request, intResID, attID, period, reportform);
/* 537 */     reportlist.add(reportTable);
/*     */     
/* 539 */     result.put("result", reportlist);
/* 540 */     xmlString = CommonAPIUtil.getOutputAsString(result, isJsonFormat);
/* 541 */     return xmlString;
/*     */   }
/*     */   
/* 544 */   public static void addStringAndConfigAttributes(List attList, String type) { ResultSet rs = null;
/* 545 */     String query = "select AM_ATTRIBUTES.ATTRIBUTEID,AM_ATTRIBUTES.RESOURCETYPE,AM_ATTRIBUTES.DISPLAYNAME,AM_ATTRIBUTES.UNITS,AM_ATTRIBUTES_EXT.REPORTS_ENABLED,AM_ATTRIBUTES_EXT.ISARCHIVEING from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and AM_ATTRIBUTES.resourcetype='" + type + "' and AM_ATTRIBUTES.TYPE in(3,5,6)";
/*     */     try
/*     */     {
/* 548 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 549 */       while (rs.next()) {
/* 550 */         String aid = rs.getString("ATTRIBUTEID");
/* 551 */         String aname = FormatUtil.getString(rs.getString("DISPLAYNAME").trim());
/* 552 */         String unit = FormatUtil.getString(rs.getString("UNITS"));
/*     */         
/* 554 */         if ((unit != null) && (!"-".equalsIgnoreCase(unit)) && (!"".equalsIgnoreCase(unit)))
/*     */         {
/* 556 */           attList.add(aid + "#" + aname + " " + FormatUtil.getString("in") + " " + unit);
/*     */         }
/*     */         else {
/* 559 */           attList.add(aid + "#" + aname);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 565 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 568 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/* 572 */   public static ArrayList getAttributeDetails(List attributes, String resID, String typeStr) { MyPageAction mpa = new MyPageAction();
/* 573 */     HashMap widgetProps = new HashMap();
/* 574 */     HashMap widgetData = new HashMap();
/* 575 */     HashMap residDisplayNames = new HashMap();
/* 576 */     HashMap residTypemapping = new HashMap();
/* 577 */     String typeStrdisp = "";
/*     */     
/* 579 */     String attriName = "";
/* 580 */     ManagedApplication mo = new ManagedApplication();
/* 581 */     String metricdataStr = "";
/* 582 */     ArrayList selectedMonitorsArr = new ArrayList();
/* 583 */     ArrayList relatedAttributeList = new ArrayList();
/* 584 */     ArrayList controlHeader = new ArrayList();
/* 585 */     ArrayList allAttributesArr = new ArrayList();
/* 586 */     ArrayList metaInfoInOrder = new ArrayList();
/* 587 */     String[] mapping = { resID + "=" + typeStr };
/*     */     
/* 589 */     widgetProps.put("toporBottom_N", "1000");
/* 590 */     widgetProps.put("period", "20");
/* 591 */     widgetProps.put("resourcetype", typeStr);
/* 592 */     widgetProps.put("toporBottom", "desc");
/* 593 */     if ((resID != null) && (!resID.equals(""))) {
/* 594 */       widgetProps.put("selectmonitortype", "-1");
/*     */     } else {
/* 596 */       widgetProps.put("selectmonitortype", "ALL");
/*     */     }
/* 598 */     widgetProps.put("residDisplayNames", residDisplayNames);
/* 599 */     widgetProps.put("residTypeMapping", residTypemapping);
/*     */     
/* 601 */     selectedMonitorsArr.add(resID);
/* 602 */     widgetProps.put("selectedMonitors", selectedMonitorsArr);
/*     */     
/* 604 */     controlHeader.add("monitorLink");
/* 605 */     controlHeader.add("wholenumber");
/*     */     
/* 607 */     for (int i = 0; i < attributes.size(); i++)
/*     */     {
/* 609 */       attriName = "" + attributes.get(i);
/* 610 */       String[] nodes = attriName.split("#");
/* 611 */       controlHeader.add("wholenumber");
/* 612 */       if (i == 0)
/*     */       {
/* 614 */         widgetProps.put("baseAttribute", "" + nodes[0]);
/*     */       }
/*     */       else
/*     */       {
/* 618 */         relatedAttributeList.add("" + nodes[0]);
/*     */       }
/* 620 */       allAttributesArr.add("" + nodes[0]);
/*     */     }
/*     */     
/*     */ 
/* 624 */     widgetProps.put("controlHeader", controlHeader);
/* 625 */     widgetProps.put("allAttributes", allAttributesArr);
/* 626 */     widgetProps.put("relatedAttributeList", relatedAttributeList);
/* 627 */     widgetProps.put("widgettype", "2");
/* 628 */     widgetProps.put("monitorSelectionType", "1");
/* 629 */     mpa.getSelectedMonitors(widgetProps);
/* 630 */     boolean gotTableDetails = mo.getAttrbuteExtEntries(allAttributesArr);
/* 631 */     widgetData = mpa.gatherMetrics(widgetProps);
/* 632 */     ArrayList metricdata = (ArrayList)widgetData.get("metricdata");
/* 633 */     ArrayList header = (ArrayList)widgetData.get("header");
/* 634 */     ArrayList metricVal = new ArrayList();
/* 635 */     ArrayList<Hashtable<?, ?>> attribList = new ArrayList();
/* 636 */     if (metricdata.size() > 0)
/*     */     {
/*     */       try
/*     */       {
/* 640 */         metricVal = (ArrayList)metricdata.get(0);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 644 */         e.printStackTrace();
/*     */       }
/* 646 */       for (int j = 1; (j < header.size()) && (j < metricVal.size()); j++)
/*     */       {
/* 648 */         Hashtable attrib = new Hashtable();
/*     */         try
/*     */         {
/* 651 */           if ((String)metricVal.get(j) == null)
/*     */           {
/* 653 */             metricdataStr = FormatUtil.getString("am.webclient.api.getdata.nodata");
/*     */           }
/*     */           else
/*     */           {
/* 657 */             metricdataStr = (String)metricVal.get(j);
/* 658 */             if ((metricdataStr.equals("-1")) || (metricdataStr.equals("-1.0")) || (metricdataStr.equals("-1.00")))
/*     */             {
/* 660 */               metricdataStr = "-";
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 666 */           metricdataStr = FormatUtil.getString("am.webclient.api.getdata.nodata");
/*     */         }
/* 668 */         String[] attributeDetails = DBUtil.getAttributeDetails("" + header.get(j));
/* 669 */         if ((metricdataStr != null) && (!attributeDetails[1].trim().equals("")) && (!metricdataStr.equals("-")) && (!attributeDetails[1].endsWith("_rw")))
/*     */         {
/* 671 */           attrib.put("DISPLAYNAME", attributeDetails[1].trim());
/* 672 */           attrib.put("Value", metricdataStr);
/* 673 */           attrib.put("Units", attributeDetails[2]);
/* 674 */           attrib.put("AttributeID", "" + header.get(j));
/*     */         }
/* 676 */         if (attrib.size() > 0)
/*     */         {
/* 678 */           attribList.add(attrib);
/*     */         }
/*     */       }
/*     */     }
/* 682 */     return attribList;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\GetCurrentDataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */