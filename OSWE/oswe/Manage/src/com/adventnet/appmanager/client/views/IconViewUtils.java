/*     */ package com.adventnet.appmanager.client.views;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
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
/*     */ public class IconViewUtils
/*     */ {
/*     */   public static String getQualifiedHostName(String hostName)
/*     */   {
/*  42 */     String qualHostName = null;
/*     */     try {
/*  44 */       InetAddress inetAddr = InetAddress.getByName(hostName);
/*  45 */       qualHostName = inetAddr.getCanonicalHostName();
/*     */     } catch (Exception ex) {
/*  47 */       ex.printStackTrace();
/*     */     }
/*  49 */     return qualHostName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getHostIp(String hostName)
/*     */   {
/*  58 */     String hostIP = null;
/*     */     try {
/*  60 */       InetAddress inetAddr = InetAddress.getByName(hostName);
/*  61 */       hostIP = inetAddr.getHostAddress();
/*     */     } catch (Exception ex) {
/*  63 */       ex.printStackTrace();
/*     */     }
/*  65 */     return hostIP;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getHostName(String hostName)
/*     */   {
/*  75 */     String host = null;
/*     */     try {
/*  77 */       InetAddress inetAddr = InetAddress.getByName(hostName);
/*  78 */       host = inetAddr.getHostName();
/*     */     } catch (Exception ex) {
/*  80 */       ex.printStackTrace();
/*     */     }
/*  82 */     return host;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getLocalHost(int i)
/*     */   {
/*  94 */     String result = null;
/*     */     try {
/*  96 */       InetAddress inetAddr = InetAddress.getLocalHost();
/*  97 */       if (inetAddr == null) {
/*  98 */         return result;
/*     */       }
/* 100 */       if (i == 0) {
/* 101 */         result = inetAddr.getHostName();
/* 102 */       } else if (i == 1) {
/* 103 */         result = inetAddr.getHostAddress();
/* 104 */       } else if (i == 2) {
/* 105 */         result = inetAddr.getCanonicalHostName();
/*     */       }
/*     */     } catch (Exception ex) {
/* 108 */       ex.printStackTrace();
/*     */     }
/* 110 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] getAllHostIp(String hostName)
/*     */   {
/* 120 */     String[] hostIps = null;
/*     */     try {
/* 122 */       InetAddress[] inetAddr = InetAddress.getAllByName(hostName);
/* 123 */       if (inetAddr != null) {
/* 124 */         hostIps = new String[inetAddr.length];
/* 125 */         for (int i = 0; i < inetAddr.length; i++) {
/* 126 */           hostIps[i] = inetAddr[i].getHostAddress();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (UnknownHostException ue) {
/* 131 */       AMLog.debug("Host " + hostName + " not known, so no IP address for the host could be found.");
/*     */     }
/*     */     catch (Exception ex) {
/* 134 */       ex.printStackTrace();
/*     */     }
/* 136 */     return hostIps;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Map<String, HashMap<String, Object>> getAllVMs()
/*     */   {
/* 145 */     return getAllVMs(null);
/*     */   }
/*     */   
/*     */   public static Map<String, HashMap<String, Object>> getAllVMs(HttpServletRequest request)
/*     */   {
/* 150 */     HashMap<String, HashMap<String, Object>> allVMs = null;
/* 151 */     ResultSet rs = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 156 */     String filterCondn = "";
/* 157 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 159 */       filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request));
/*     */     }
/*     */     
/* 162 */     String query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType  where AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP in ('VIR') and AM_ATTRIBUTES.TYPE=1 AND AM_ManagedObject.Type in ('VirtualMachine','HyperVVirtualMachine','XenServerVM') " + filterCondn;
/* 163 */     if ((request != null) && 
/* 164 */       (DBUtil.isDelegatedAdmin(request.getRemoteUser()))) {
/* 165 */       Vector<String> resourceIdList = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 166 */       query = query + " and " + DBUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceIdList);
/*     */     }
/*     */     
/* 169 */     AMLog.debug("getAllVMs : query :" + query);
/*     */     try
/*     */     {
/* 172 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 173 */       String ava; while (rs.next())
/*     */       {
/* 175 */         ava = "-1";
/* 176 */         String id = "" + rs.getInt("id");
/*     */         
/* 178 */         HashMap<String, String> latestEventMap = FaultUtil.getLatestEvent(id + "_" + rs.getInt("attributeid"));
/* 179 */         if (latestEventMap != null) {
/* 180 */           ava = (String)latestEventMap.get("SEVERITY");
/* 181 */           if (ava == null) {
/* 182 */             ava = "-1";
/*     */           }
/*     */         }
/*     */         
/* 186 */         HashMap<String, Object> infoMap = new HashMap();
/* 187 */         infoMap.put("Resource_Name", rs.getString("name"));
/* 188 */         infoMap.put("Resource_Type", rs.getString("type"));
/* 189 */         infoMap.put("Resource_Id", id);
/* 190 */         infoMap.put("Resource_Ava", ava);
/* 191 */         infoMap.put("Resource_Disp_Name", rs.getString("dispname"));
/* 192 */         infoMap.put("Short_Name", rs.getString("SNAME"));
/* 193 */         if (allVMs == null) {
/* 194 */           allVMs = new HashMap();
/*     */         }
/* 196 */         String hostName = (String)infoMap.get("Resource_Name");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 201 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/* 203 */           allVMs.put(hostName + "_" + Integer.parseInt((String)infoMap.get("Resource_Id")) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE, infoMap);
/*     */         }
/*     */         else
/*     */         {
/* 207 */           allVMs.put(hostName, infoMap);
/*     */         }
/*     */       }
/*     */       
/* 211 */       if (allVMs == null) {
/* 212 */         return allVMs;
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 217 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 220 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 225 */     ArrayList<String> list = null;
/* 226 */     StringBuffer queryBuff = new StringBuffer();
/* 227 */     ManagedApplication mp = new ManagedApplication();
/* 228 */     Iterator<String> itr = allVMs.keySet().iterator();
/* 229 */     while (itr.hasNext()) {
/* 230 */       String vmName = (String)itr.next();
/* 231 */       Object eachMap = (Map)allVMs.get(vmName);
/* 232 */       queryBuff.append("select GUESTOS from AM_VM_GUESTOSMAPPING ");
/* 233 */       queryBuff.append("where RESID=");
/* 234 */       queryBuff.append(((Map)eachMap).get("Resource_Id"));
/* 235 */       list = mp.getRowsForSingleColumn(queryBuff.toString());
/* 236 */       if ((list == null) || (list.size() == 0)) {
/* 237 */         ((Map)eachMap).put("Resource_Type", "Node");
/*     */       }
/*     */       else {
/* 240 */         ((Map)eachMap).put("Resource_Type", (String)list.get(0));
/*     */       }
/* 242 */       queryBuff.delete(0, queryBuff.length());
/*     */     }
/* 244 */     return allVMs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void getTraditionalMonsUnderVMs(Map<String, HashMap<String, Object>> allVMsInfoMap)
/*     */   {
/* 253 */     if ((allVMsInfoMap == null) || (allVMsInfoMap.size() == 0)) {
/* 254 */       return;
/*     */     }
/* 256 */     String allVMs = "";
/* 257 */     Iterator<String> itr = allVMsInfoMap.keySet().iterator();
/* 258 */     while (itr.hasNext()) {
/* 259 */       String s1 = (String)itr.next();
/*     */       
/*     */ 
/* 262 */       int idx = s1.lastIndexOf("_");
/* 263 */       if (idx != -1) {
/* 264 */         int t1 = -1;
/*     */         try {
/* 266 */           t1 = Integer.parseInt(s1.substring(idx + 1));
/*     */         }
/*     */         catch (Exception ex) {
/* 269 */           t1 = -1;
/*     */         }
/* 271 */         if (t1 != -1) {
/* 272 */           s1 = s1.substring(0, idx);
/*     */         }
/*     */       }
/*     */       
/* 276 */       allVMs = allVMs + "'" + s1 + "'" + ",";
/*     */     }
/* 278 */     allVMs = allVMs.substring(0, allVMs.length() - 1);
/*     */     
/* 280 */     StringBuffer queryBuff = new StringBuffer();
/* 281 */     queryBuff.append("select InetService.TARGETNAME resname,AM_ManagedObject.DISPLAYNAME servicename,AM_ManagedObject.TYPE servicetype, AM_ManagedObject.RESOURCEID serviceid,AM_ATTRIBUTES.ATTRIBUTEID attributeid from InetService,AM_ManagedObject,AM_ATTRIBUTES,IpAddress where InetService.TARGETNAME IN (");
/* 282 */     queryBuff.append(allVMs);
/* 283 */     queryBuff.append(") and InetService.INTERFACENAME=IpAddress.NAME and InetService.NAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ManagedObject.Type IN");
/* 284 */     queryBuff.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 285 */     queryBuff.append(" and AM_ATTRIBUTES.TYPE=1 ORDER BY InetService.TARGETNAME");
/*     */     
/* 287 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 290 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/*     */       
/* 292 */       while (rs.next())
/*     */       {
/* 294 */         String resName = rs.getString("resname");
/* 295 */         String serviceType = rs.getString("servicetype");
/* 296 */         String serviceId = "" + rs.getInt("serviceid");
/* 297 */         String serviceName = rs.getString("servicename");
/*     */         
/* 299 */         String serviceAva = "-1";
/* 300 */         HashMap<String, String> latestEventMap = FaultUtil.getLatestEvent(serviceId + "_" + rs.getInt("attributeid"));
/* 301 */         if (latestEventMap != null) {
/* 302 */           serviceAva = (String)latestEventMap.get("SEVERITY");
/* 303 */           if (serviceAva == null) {
/* 304 */             serviceAva = "-1";
/*     */           }
/*     */         }
/*     */         
/* 308 */         HashMap<String, Object> reference = null;
/* 309 */         if (EnterpriseUtil.isAdminServer())
/*     */         {
/* 311 */           reference = (HashMap)allVMsInfoMap.get(resName + "_" + Integer.parseInt(serviceId) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE);
/*     */         }
/*     */         else
/*     */         {
/* 315 */           reference = (HashMap)allVMsInfoMap.get(resName);
/*     */         }
/*     */         
/* 318 */         if (reference != null)
/*     */         {
/* 320 */           List<String> serviceIdForType = (ArrayList)reference.get(serviceType + "_Id");
/* 321 */           if (serviceIdForType == null)
/*     */           {
/* 323 */             serviceIdForType = new ArrayList();
/* 324 */             reference.put(serviceType + "_Id", serviceIdForType);
/*     */           }
/* 326 */           serviceIdForType.add(serviceId);
/*     */           
/* 328 */           List<String> serviceNamesForType = (ArrayList)reference.get(serviceType + "_Names");
/* 329 */           if (serviceNamesForType == null)
/*     */           {
/* 331 */             serviceNamesForType = new ArrayList();
/* 332 */             reference.put(serviceType + "_Names", serviceNamesForType);
/*     */           }
/* 334 */           serviceNamesForType.add(serviceName);
/*     */           
/* 336 */           List<String> serviceAvaForType = (ArrayList)reference.get(serviceType + "_Ava");
/* 337 */           if (serviceAvaForType == null)
/*     */           {
/* 339 */             serviceAvaForType = new ArrayList();
/* 340 */             reference.put(serviceType + "_Ava", serviceAvaForType);
/*     */           }
/* 342 */           serviceAvaForType.add(serviceAva);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 347 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/* 350 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HashMap<String, HashMap<String, List<String>>> getAllConfBasedMonitors()
/*     */   {
/* 360 */     return getAllConfBasedMonitors(null);
/*     */   }
/*     */   
/*     */   public static HashMap<String, HashMap<String, List<String>>> getAllConfBasedMonitors(HttpServletRequest request)
/*     */   {
/* 365 */     HashMap<String, HashMap<String, List<String>>> confMonitors = null;
/*     */     
/*     */ 
/*     */ 
/* 369 */     String filterCondn = "";
/* 370 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 372 */       filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request));
/*     */     }
/*     */     
/* 375 */     String query = "select DISTINCT TYPEID ,TYPENAME from AM_MONITOR_TYPES,AM_ManagedObject where AM_ManagedObject.TYPE=AM_MONITOR_TYPES.TYPENAME and AM_MONITOR_TYPES.AMCREATED='YES' and AM_MONITOR_TYPES.TYPENAME NOT IN ('VMWare ESX/ESXi ','VirtualMachine','Hyper-V-Server','HyperVVirtualMachine') " + filterCondn + " order by TYPEID asc";
/*     */     try {
/* 377 */       ManagedApplication mp = new ManagedApplication();
/* 378 */       ArrayList<ArrayList<String>> rows = mp.getRows(query);
/* 379 */       if ((rows == null) || (rows.size() == 0)) {
/* 380 */         return confMonitors;
/*     */       }
/* 382 */       confMonitors = new HashMap();
/*     */       
/* 384 */       String typeID = "";
/* 385 */       String attrbID = "";
/* 386 */       StringBuffer queryBuff = new StringBuffer();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 392 */       for (int i = 0; i < rows.size(); i++)
/*     */       {
/* 394 */         ArrayList<String> typeInfoList = (ArrayList)rows.get(i);
/* 395 */         typeID = (String)typeInfoList.get(0);
/* 396 */         String confHostName = getColumnNameUsedForHost((String)typeInfoList.get(1));
/* 397 */         if ((confHostName != null) && (confHostName.length() != 0) && (!confHostName.equalsIgnoreCase("null")))
/*     */         {
/*     */ 
/*     */ 
/* 401 */           String eumChildListCond = "AM_ManagedObject.RESOURCEID NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 406 */           attrbID = AMAttributesCache.getAvailabilityId((String)typeInfoList.get(1));
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 411 */           queryBuff.append("select AM_ManagedObject.RESOURCEID,");
/* 412 */           queryBuff.append(confHostName);
/* 413 */           queryBuff.append(",DISPLAYNAME from AM_ARGS_");
/* 414 */           queryBuff.append(typeID);
/* 415 */           queryBuff.append(",AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_ARGS_");
/* 416 */           queryBuff.append(typeID);
/* 417 */           queryBuff.append(".RESOURCEID and " + eumChildListCond);
/*     */           
/* 419 */           ArrayList<ArrayList<String>> confMonArgsList = mp.getRows(queryBuff.toString());
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 426 */           for (int j = 0; j < confMonArgsList.size(); j++)
/*     */           {
/* 428 */             ArrayList<String> resourceInfoList = (ArrayList)confMonArgsList.get(j);
/*     */             
/* 430 */             String hostName = (String)resourceInfoList.get(1);
/* 431 */             if (hostName != null)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 437 */               String serviceAva = "-1";
/* 438 */               HashMap<String, String> latestEventMap = FaultUtil.getLatestEvent((String)resourceInfoList.get(0) + "_" + attrbID);
/* 439 */               if (latestEventMap != null) {
/* 440 */                 serviceAva = (String)latestEventMap.get("SEVERITY");
/* 441 */                 if (serviceAva == null) {
/* 442 */                   serviceAva = "-1";
/*     */                 }
/*     */               }
/*     */               
/* 446 */               HashMap<String, List<String>> monitorsInfo = null;
/*     */               
/*     */ 
/* 449 */               if (EnterpriseUtil.isAdminServer())
/*     */               {
/* 451 */                 monitorsInfo = (HashMap)confMonitors.get(hostName + "_" + Integer.parseInt((String)resourceInfoList.get(0)) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE);
/*     */               }
/*     */               else
/*     */               {
/* 455 */                 monitorsInfo = (HashMap)confMonitors.get(hostName);
/*     */               }
/*     */               
/* 458 */               if (monitorsInfo == null) {
/* 459 */                 monitorsInfo = new HashMap();
/* 460 */                 if (EnterpriseUtil.isAdminServer())
/*     */                 {
/* 462 */                   confMonitors.put(hostName + "_" + Integer.parseInt((String)resourceInfoList.get(0)) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE, monitorsInfo);
/*     */                 }
/*     */                 else
/*     */                 {
/* 466 */                   confMonitors.put(hostName, monitorsInfo);
/*     */                 }
/*     */               }
/*     */               
/* 470 */               List<String> serviceIdForType = (List)monitorsInfo.get((String)typeInfoList.get(1) + "_Id");
/* 471 */               if (serviceIdForType == null)
/*     */               {
/* 473 */                 serviceIdForType = new ArrayList();
/* 474 */                 monitorsInfo.put((String)typeInfoList.get(1) + "_Id", serviceIdForType);
/*     */               }
/* 476 */               serviceIdForType.add(resourceInfoList.get(0));
/*     */               
/* 478 */               List<String> serviceNamesForType = (List)monitorsInfo.get((String)typeInfoList.get(1) + "_Names");
/* 479 */               if (serviceNamesForType == null)
/*     */               {
/* 481 */                 serviceNamesForType = new ArrayList();
/* 482 */                 monitorsInfo.put((String)typeInfoList.get(1) + "_Names", serviceNamesForType);
/*     */               }
/* 484 */               serviceNamesForType.add(resourceInfoList.get(2));
/*     */               
/* 486 */               List<String> serviceAvaForType = (List)monitorsInfo.get((String)typeInfoList.get(1) + "_Ava");
/* 487 */               if (serviceAvaForType == null)
/*     */               {
/* 489 */                 serviceAvaForType = new ArrayList();
/* 490 */                 monitorsInfo.put((String)typeInfoList.get(1) + "_Ava", serviceAvaForType);
/*     */               }
/* 492 */               serviceAvaForType.add(serviceAva);
/*     */             } }
/* 494 */           queryBuff.delete(0, queryBuff.length());
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 498 */       ex.printStackTrace();
/*     */     }
/* 500 */     return confMonitors;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getOSAttrbDataTable()
/*     */   {
/* 509 */     String tableName = null;
/* 510 */     ManagedApplication mp = new ManagedApplication();
/* 511 */     String query = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID='7612'";
/* 512 */     ArrayList<String> list = mp.getRowsForSingleColumn(query);
/* 513 */     if ((list != null) && (list.size() == 1))
/*     */     {
/* 515 */       tableName = (String)list.get(0);
/*     */     }
/* 517 */     return tableName;
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
/*     */   public static List getVMsForOS(String selectedOS, boolean filterByOS, HttpServletRequest request)
/*     */   {
/* 530 */     return getVMsForOS(selectedOS, filterByOS, request, -1, -1);
/*     */   }
/*     */   
/*     */ 
/*     */   public static List getVMsForOS(String selectedOS, boolean filterByOS, HttpServletRequest request, int startIndex, int noOfRows)
/*     */   {
/* 536 */     List<ArrayList<String>> vmList = null;
/* 537 */     ManagedApplication mp = new ManagedApplication();
/* 538 */     String resFilterCondn = "";
/* 539 */     String query = null;
/* 540 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 542 */       Vector resIdVector = EnterpriseUtil.filterResourceIds(request);
/* 543 */       resFilterCondn = " AND " + EnterpriseUtil.getCondition("RESOURCEID", resIdVector);
/* 544 */       query = "select RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject where Type in ('VirtualMachine','XenServerVM') " + resFilterCondn + " order by DISPLAYNAME";
/*     */     }
/* 546 */     else if ((request != null) && (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)))
/*     */     {
/* 548 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 549 */         String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 550 */         query = "select AM_ManagedObject.RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject,AM_USERRESOURCESTABLE where Type in ('VirtualMachine','XenServerVM') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid + " order by DISPLAYNAME";
/*     */       } else {
/* 552 */         Vector resIdVector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 553 */         resFilterCondn = " AND " + EnterpriseUtil.getCondition("RESOURCEID", resIdVector);
/* 554 */         query = "select RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject where Type in ('VirtualMachine','XenServerVM') " + resFilterCondn + " order by DISPLAYNAME";
/*     */       }
/*     */     }
/*     */     else {
/* 558 */       query = "select RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject where Type in ('VirtualMachine','XenServerVM') order by DISPLAYNAME";
/*     */     }
/*     */     
/* 561 */     if (filterByOS) {
/* 562 */       StringBuffer selectedOSCondition = new StringBuffer("(UPPER(guestos) like '%" + selectedOS.toUpperCase() + "%'");
/* 563 */       if (selectedOS.equalsIgnoreCase("linux")) {
/* 564 */         selectedOSCondition.append(" OR UPPER(guestos) like '%CENTOS%'");
/*     */       }
/* 566 */       else if (selectedOS.equalsIgnoreCase("Sun Solaris")) {
/* 567 */         selectedOSCondition.append(" OR UPPER(guestos) like '%SUN%'");
/*     */       }
/* 569 */       else if (selectedOS.equalsIgnoreCase("HP-UX / Tru64")) {
/* 570 */         selectedOSCondition.append(" OR UPPER(guestos) like '%HP%'");
/*     */       }
/* 572 */       else if (selectedOS.equalsIgnoreCase("Mac OS")) {
/* 573 */         selectedOSCondition.append(" OR UPPER(guestos) like '%MAC%'");
/*     */       }
/* 575 */       selectedOSCondition.append(")");
/* 576 */       query = "select RESOURCEID,RESOURCENAME,DISPLAYNAME,TYPE from AM_ManagedObject join AM_VM_GUESTOSMAPPING on RESID=RESOURCEID where " + selectedOSCondition.toString() + " and Type in ('VirtualMachine','XenServerVM') " + resFilterCondn + "  order by DISPLAYNAME";
/*     */     }
/*     */     
/* 579 */     if ((startIndex != -1) && (noOfRows != -1))
/*     */     {
/* 581 */       String totalQuery = DBUtil.getCountQuery(query);
/* 582 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 583 */       query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "AM_ManagedObject.DISPLAYNAME");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 588 */       ArrayList rows = mp.getRows(query);
/* 589 */       if ((rows == null) || (rows.size() == 0)) {
/* 590 */         return vmList;
/*     */       }
/*     */       
/* 593 */       String os = "Node";
/* 594 */       StringBuffer queryBuff = new StringBuffer();
/* 595 */       vmList = new ArrayList();
/* 596 */       for (int i = 0; i < rows.size(); i++)
/*     */       {
/* 598 */         if (queryBuff.length() > 0) {
/* 599 */           queryBuff.delete(0, queryBuff.length());
/*     */         }
/*     */         
/* 602 */         ArrayList<String> eachList = (ArrayList)rows.get(i);
/* 603 */         if ((eachList != null) && (eachList.size() != 0))
/*     */         {
/*     */ 
/*     */ 
/* 607 */           if (DBUtil.vmOSMapping.containsKey(eachList.get(0))) {
/* 608 */             os = (String)DBUtil.vmOSMapping.get(eachList.get(0));
/*     */           }
/* 610 */           else if (!filterByOS) {
/* 611 */             os = selectedOS;
/*     */           }
/*     */           else {
/* 614 */             queryBuff.append("select GUESTOS from AM_VM_GUESTOSMAPPING ");
/* 615 */             queryBuff.append("where RESID=");
/* 616 */             queryBuff.append((String)eachList.get(0));
/* 617 */             ArrayList<String> osFromTable = mp.getRowsForSingleColumn(queryBuff.toString());
/* 618 */             if ((osFromTable == null) || (osFromTable.size() == 0)) {
/* 619 */               os = "Node";
/*     */             }
/*     */             else {
/* 622 */               os = (String)osFromTable.get(0);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 628 */           os = os.toLowerCase();
/* 629 */           String sysTypeImg = "";
/* 630 */           if (os.indexOf("linux") != -1)
/*     */           {
/* 632 */             os = "Linux";
/* 633 */             sysTypeImg = "icon_monitors_linux.gif";
/*     */           }
/* 635 */           else if (os.indexOf("centos") != -1)
/*     */           {
/* 637 */             os = "Linux";
/* 638 */             sysTypeImg = "icon_monitors_linux.gif";
/*     */           }
/* 640 */           else if (os.indexOf("windo") != -1)
/*     */           {
/* 642 */             os = "Windows";
/* 643 */             sysTypeImg = "icon_monitors_windows.gif";
/*     */           }
/* 645 */           else if (os.indexOf("sun") != -1)
/*     */           {
/* 647 */             os = "Sun Solaris";
/* 648 */             sysTypeImg = "icon_monitors_solaris.gif";
/*     */           }
/* 650 */           else if (os.indexOf("aix") != -1)
/*     */           {
/* 652 */             os = "AIX";
/* 653 */             sysTypeImg = "icon_monitors_aix.gif";
/*     */           }
/* 655 */           else if (os.indexOf("hp") != -1)
/*     */           {
/* 657 */             os = "HP-UX / Tru64";
/* 658 */             sysTypeImg = "icon_monitors_hpunix.gif";
/*     */           }
/* 660 */           else if (os.indexOf("Mac") != -1)
/*     */           {
/* 662 */             os = "Mac OS";
/* 663 */             sysTypeImg = "icon_monitors_macOS.gif";
/*     */           }
/* 665 */           else if (os.indexOf("AS400/iSeries") != -1)
/*     */           {
/* 667 */             os = "AS400/iSeries";
/* 668 */             sysTypeImg = "icon_monitors_as400.gif";
/*     */           }
/* 670 */           else if (os.indexOf("Novell") != -1)
/*     */           {
/* 672 */             os = "Novell";
/* 673 */             sysTypeImg = "icon_monitors_novell.gif";
/*     */           }
/*     */           else
/*     */           {
/* 677 */             os = "Unknown";
/* 678 */             sysTypeImg = "icon_monitors_unknown.gif";
/*     */           }
/*     */           
/* 681 */           if ((!filterByOS) || 
/* 682 */             (selectedOS.toLowerCase().indexOf(os.toLowerCase()) != -1))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 689 */             if ((DBQueryUtil.isMssql()) && (eachList.size() == 5)) {
/* 690 */               eachList.remove(4);
/*     */             }
/*     */             
/* 693 */             eachList.add(os);
/* 694 */             eachList.add(sysTypeImg);
/* 695 */             vmList.add(eachList);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 700 */       ex.printStackTrace();
/*     */     }
/* 702 */     return vmList;
/*     */   }
/*     */   
/*     */   public static List getEC2InstancesForOS(String selectedOS, boolean filterByOS, HttpServletRequest request)
/*     */   {
/* 707 */     return getEC2InstancesForOS(selectedOS, filterByOS, request, -1, -1);
/*     */   }
/*     */   
/*     */   public static List getEC2InstancesForOS(String selectedOS, boolean filterByOS, HttpServletRequest request, int startIndex, int noOfRows)
/*     */   {
/* 712 */     List<ArrayList<String>> EC2InstancesList = null;
/* 713 */     ManagedApplication mp = new ManagedApplication();
/*     */     
/* 715 */     String resFilterCondn = "";
/* 716 */     String owner = request.getRemoteUser();
/* 717 */     String operatorresourceid = "";
/* 718 */     String query = null;
/* 719 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()))
/*     */     {
/* 721 */       Vector resIdVector = EnterpriseUtil.filterResourceIds(request);
/* 722 */       resFilterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resIdVector);
/* 723 */       query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_Amazon_EC2InstanceOSMAPPING.INSTANCEOS from AM_ManagedObject left join AM_Amazon_EC2InstanceOSMAPPING on AM_ManagedObject.RESOURCEID=AM_Amazon_EC2InstanceOSMAPPING.RESOURCEID where AM_ManagedObject.type in ('EC2Instance')" + operatorresourceid + " " + resFilterCondn;
/*     */     }
/*     */     
/* 726 */     if (com.adventnet.appmanager.util.Constants.isPrivilegedUser(request))
/*     */     {
/* 728 */       if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/* 729 */         String userid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 730 */         query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_Amazon_EC2InstanceOSMAPPING.INSTANCEOS from AM_USERRESOURCESTABLE, AM_ManagedObject left join AM_Amazon_EC2InstanceOSMAPPING on AM_ManagedObject.RESOURCEID=AM_Amazon_EC2InstanceOSMAPPING.RESOURCEID where AM_ManagedObject.type in ('EC2Instance') and AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + userid;
/*     */       } else {
/* 732 */         Vector resourceids = ClientDBUtil.getResourceIdentity(owner, request, null);
/* 733 */         operatorresourceid = "and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceids) + "";
/* 734 */         query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_Amazon_EC2InstanceOSMAPPING.INSTANCEOS from AM_ManagedObject left join AM_Amazon_EC2InstanceOSMAPPING on AM_ManagedObject.RESOURCEID=AM_Amazon_EC2InstanceOSMAPPING.RESOURCEID where AM_ManagedObject.type in ('EC2Instance')" + operatorresourceid + " " + resFilterCondn;
/*     */       }
/*     */     } else {
/* 737 */       query = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_Amazon_EC2InstanceOSMAPPING.INSTANCEOS from AM_ManagedObject left join AM_Amazon_EC2InstanceOSMAPPING on AM_ManagedObject.RESOURCEID=AM_Amazon_EC2InstanceOSMAPPING.RESOURCEID where AM_ManagedObject.type in ('EC2Instance')";
/*     */     }
/*     */     
/* 740 */     if ((startIndex != -1) && (noOfRows != -1))
/*     */     {
/* 742 */       String totalQuery = DBUtil.getCountQuery(query);
/* 743 */       request.setAttribute("totalObjCount", Integer.valueOf(DBUtil.getCount(totalQuery)));
/* 744 */       query = DBQueryUtil.addLimit(query, startIndex, noOfRows, "AM_ManagedObject.DISPLAYNAME");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 749 */       ArrayList rows = mp.getRows(query);
/* 750 */       if ((rows == null) || (rows.size() == 0)) {
/* 751 */         return EC2InstancesList;
/*     */       }
/*     */       
/* 754 */       String os = "";
/* 755 */       StringBuffer queryBuff = new StringBuffer();
/* 756 */       EC2InstancesList = new ArrayList();
/* 757 */       for (int i = 0; i < rows.size(); i++)
/*     */       {
/* 759 */         if (queryBuff.length() > 0) {
/* 760 */           queryBuff.delete(0, queryBuff.length());
/*     */         }
/*     */         
/* 763 */         ArrayList<String> eachList = (ArrayList)rows.get(i);
/* 764 */         if ((eachList != null) && (eachList.size() != 0))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 773 */           String osFromTable = (String)eachList.get(3);
/*     */           
/* 775 */           if ((osFromTable == null) || (osFromTable.length() == 0)) {
/* 776 */             os = "Node";
/*     */           }
/*     */           else {
/* 779 */             os = osFromTable;
/*     */           }
/* 781 */           os = os.toLowerCase();
/*     */           
/* 783 */           String sysTypeImg = "";
/* 784 */           if (os.indexOf("linux") != -1)
/*     */           {
/* 786 */             os = "Linux";
/* 787 */             sysTypeImg = "icon_monitors_linux.gif";
/*     */           }
/* 789 */           else if (os.indexOf("centos") != -1)
/*     */           {
/* 791 */             os = "Linux";
/* 792 */             sysTypeImg = "icon_monitors_linux.gif";
/*     */           }
/* 794 */           else if (os.indexOf("windo") != -1)
/*     */           {
/* 796 */             os = "Windows";
/* 797 */             sysTypeImg = "icon_monitors_windows.gif";
/*     */           }
/* 799 */           else if (os.indexOf("sun") != -1)
/*     */           {
/* 801 */             os = "Sun Solaris";
/* 802 */             sysTypeImg = "icon_monitors_solaris.gif";
/*     */           }
/* 804 */           else if (os.indexOf("aix") != -1)
/*     */           {
/* 806 */             os = "AIX";
/* 807 */             sysTypeImg = "icon_monitors_aix.gif";
/*     */           }
/* 809 */           else if (os.indexOf("hp") != -1)
/*     */           {
/* 811 */             os = "HP-UX / Tru64";
/* 812 */             sysTypeImg = "icon_monitors_hpunix.gif";
/*     */           }
/* 814 */           else if (os.indexOf("mac") != -1)
/*     */           {
/* 816 */             os = "Mac OS";
/* 817 */             sysTypeImg = "icon_monitors_macOS.gif";
/*     */           }
/* 819 */           else if (os.indexOf("as400/iseries") != -1)
/*     */           {
/* 821 */             os = "AS400/iSeries";
/* 822 */             sysTypeImg = "icon_monitors_as400.gif";
/*     */           }
/* 824 */           else if (os.indexOf("novell") != -1)
/*     */           {
/* 826 */             os = "Novell";
/* 827 */             sysTypeImg = "icon_monitors_novell.gif";
/*     */           }
/*     */           else
/*     */           {
/* 831 */             os = "Unknown";
/* 832 */             sysTypeImg = "icon_monitors_unknown.gif";
/*     */           }
/*     */           
/* 835 */           if ((!filterByOS) || 
/* 836 */             (selectedOS.toLowerCase().indexOf(os.toLowerCase()) != -1))
/*     */           {
/*     */ 
/*     */ 
/* 840 */             eachList.add(os);
/* 841 */             eachList.add(sysTypeImg);
/* 842 */             EC2InstancesList.add(eachList);
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 847 */       ex.printStackTrace();
/*     */     }
/* 849 */     return EC2InstancesList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getColumnNameUsedForHost(String resourceType)
/*     */   {
/* 857 */     String columnName = ConfMonitorConfiguration.getInstance().getColumnNameUsedForHost(resourceType, true);
/* 858 */     return columnName;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\views\IconViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */