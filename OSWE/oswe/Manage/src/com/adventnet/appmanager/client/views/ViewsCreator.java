/*      */ package com.adventnet.appmanager.client.views;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ViewsCreator
/*      */ {
/*   52 */   private static AMConnectionPool cp = null;
/*   53 */   public static String[] supportedTypes = { "Tomcat-server", "WEBLOGIC-server", "WebSphere-server", "DB2-server", "MSSQL-DB-server", "SYBASE-DB-server", "MYSQL-DB-server", "ORACLE-DB-server", "JMX1.2-MX4J-RMI", "MAIL-server", "Port-Test", "TELNET", "RMI", "SNMP", "WEB-server", "Apache-server", "IIS-server", "PHP", "WTA" };
/*   54 */   public static String[] supportedTypesDisplayName = { "am.monitortab.tableview.tomcat.text", "am.monitortab.tableview.weblogic.text", "am.monitortab.tableview.websphere.text", "am.monitortab.tableview.DB2.text", "am.monitortab.tableview.mssql.text", "Sybase", "am.monitortab.tableview.mysql.text", "am.monitortab.tableview.oracle.text", "am.monitortab.tableview.mx4j.text", "am.monitortab.tableview.Mail.text", "am.monitortab.tableview.service.text", "am.monitortab.tableview.telnet.text", "am.monitortab.tableview.RMI.text", "am.monitortab.tableview.SNMP.text", "am.monitortab.tableview.Web.text", "am.monitortab.tableview.Apache.text", "am.monitortab.tableview.IIS.text", "am.monitortab.tableview.PHP.text", "am.monitortab.tableview.exchange.text", "am.monitortab.tableview.url.text", "am.monitortab.tableview.urlseq.text", "am.monitortab.tableview.script.text", "am.monitortab.tableview.dotnet.text", "am.monitortab.tableview.webtransaction.text", "am.monitortab.tableview.rbm.text" };
/*   55 */   public static String osTypes = " 'Linux','Novell', 'Windows 2000', 'WindowsNT_Server','WindowsNT', 'Windows95', 'Windows Vista','SUN','SUN PC','Windows 2003','Windows XP','AIX','AS400/iSeries', 'HP-UX' , 'HP-TRU64 ' ";
/*   56 */   private static String types = com.adventnet.appmanager.util.Constants.resourceTypes;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/*   65 */       com.adventnet.appmanager.util.Constants.setResourceTypeNetViewandDisplayView();
/*   66 */       supportedTypes = new String[com.adventnet.appmanager.util.Constants.resourceTypes_netView.length];
/*   67 */       supportedTypesDisplayName = new String[com.adventnet.appmanager.util.Constants.resourceTypes_netView.length];
/*   68 */       for (int i = 0; i < com.adventnet.appmanager.util.Constants.resourceTypes_netView.length; i++)
/*      */       {
/*   70 */         if (!Arrays.asList(supportedTypes).contains(com.adventnet.appmanager.util.Constants.resourceTypes_netView[i]))
/*      */         {
/*      */ 
/*   73 */           supportedTypes[i] = com.adventnet.appmanager.util.Constants.resourceTypes_netView[i];
/*   74 */           supportedTypesDisplayName[i] = com.adventnet.appmanager.util.Constants.resourceTypes_display_netView[i];
/*      */         } }
/*   76 */       cp = AMConnectionPool.getInstance();
/*      */     }
/*      */     catch (Exception e) {
/*   79 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static List getNetworkServiceViewModel(String networkAddress, String typeOfService)
/*      */     throws Exception
/*      */   {
/*   86 */     return getNetworkServiceViewModel(networkAddress, typeOfService, null, -1, -1);
/*      */   }
/*      */   
/*      */   public static List getNetworkServiceViewModel(String networkAddress, String typeOfService, int startIndex, int noOfRows) throws Exception
/*      */   {
/*   91 */     return getNetworkServiceViewModel(networkAddress, typeOfService, null, startIndex, noOfRows);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getNetworkServiceViewModel(String networkAddress, String typeOfService, HttpServletRequest request, int startIndex, int noOfRows)
/*      */     throws Exception
/*      */   {
/*  110 */     List listOfData = new ArrayList();
/*  111 */     Map resNameVsInfoMap = new HashMap();
/*  112 */     String uniqueKey = generateLatestStatusTable();
/*  113 */     HashMap statusMap = new HashMap();
/*  114 */     ArrayList al = new ArrayList();
/*  115 */     ArrayList al_resname = new ArrayList();
/*  116 */     ArrayList al_type = new ArrayList();
/*      */     
/*  118 */     ResultSet statusRes = null;
/*      */     try
/*      */     {
/*  121 */       statusRes = AMConnectionPool.executeQueryStmt("select RESOURCEID,ATTRIBUTEID,SEVERITY from AM_TEMP_LATESTSTATUS,Alert where AM_TEMP_LATESTSTATUS.ID='" + uniqueKey + "' and AM_TEMP_LATESTSTATUS.RESOURCEID=Alert.SOURCE and " + DBQueryUtil.castasVarchar("AM_TEMP_LATESTSTATUS.ATTRIBUTEID") + "=Alert.CATEGORY and AM_TEMP_LATESTSTATUS.LATESTDATACOLLECTIONTIME=Alert.MODTIME GROUP BY RESOURCEID,ATTRIBUTEID,SEVERITY");
/*  122 */       while (statusRes.next())
/*      */       {
/*  124 */         statusMap.put(statusRes.getString(1) + "_" + statusRes.getString(2), "" + statusRes.getInt(3));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  129 */       if (statusRes != null) {
/*      */         try {
/*  131 */           AMConnectionPool.closeStatement(statusRes);
/*      */         }
/*      */         catch (Exception ex) {
/*  134 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  138 */       statusRes.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  127 */       ex.printStackTrace();
/*      */     } finally {
/*  129 */       if (statusRes != null) {
/*      */         try {
/*  131 */           AMConnectionPool.closeStatement(statusRes);
/*      */         }
/*      */         catch (Exception ex) {
/*  134 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  139 */     String resourceNames = "";
/*      */     
/*  141 */     String filterCondn = "";
/*  142 */     String owner = "admin";
/*  143 */     boolean isUserResourceEnabled = false;
/*  144 */     String loginUserid = null;
/*  145 */     if (request != null)
/*      */     {
/*  147 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  149 */         filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request));
/*      */       }
/*  151 */       else if (ClientDBUtil.isPrivilegedUser(request)) {
/*  152 */         if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/*  153 */           isUserResourceEnabled = true;
/*  154 */           loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*  155 */           filterCondn = " AND AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ") ";
/*      */         } else {
/*  157 */           owner = request.getRemoteUser();
/*  158 */           filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", ClientDBUtil.getResourceIdentity(owner)) + " ";
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  163 */     String query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from Node,ManagedObject,AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType  where Node.NAME=ManagedObject.NAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types + filterCondn;
/*      */     
/*  165 */     if (isUserResourceEnabled) {
/*  166 */       query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from Node,ManagedObject,AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType,AM_USERRESOURCESTABLE  where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and Node.NAME=ManagedObject.NAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types;
/*      */     }
/*      */     
/*  169 */     if (networkAddress != null)
/*      */     {
/*  171 */       if (isUserResourceEnabled) {
/*  172 */         query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from IpAddress,ManagedObject,AM_ManagedObject,AM_ATTRIBUTES,Node,AM_ManagedResourceType,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and IpAddress.PARENTNET='" + networkAddress + "' AND Node.NAME=IpAddress.PARENTNODE AND Node.NAME=ManagedObject.NAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' AND AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types;
/*      */       } else {
/*  174 */         query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from IpAddress,ManagedObject,AM_ManagedObject,AM_ATTRIBUTES,Node,AM_ManagedResourceType where IpAddress.PARENTNET='" + networkAddress + "' AND Node.NAME=IpAddress.PARENTNODE AND Node.NAME=ManagedObject.NAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' AND AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types + filterCondn;
/*      */       }
/*      */     }
/*      */     
/*  178 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*      */ 
/*  181 */       query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from Node,AM_ManagedObject,AM_ATTRIBUTES,AM_ManagedResourceType  where Node.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types + filterCondn;
/*  182 */       if (networkAddress != null)
/*      */       {
/*  184 */         query = "select AM_ManagedObject.RESOURCEID id,AM_ManagedObject.RESOURCENAME as name,AM_ManagedObject.TYPE as type,AM_ATTRIBUTES.ATTRIBUTEID attributeid,AM_ManagedObject.DISPLAYNAME dispname,AM_ManagedResourceType.SHORTNAME as SNAME from IpAddress,AM_ManagedObject,AM_ATTRIBUTES,Node,AM_ManagedResourceType where IpAddress.PARENTNET='" + networkAddress + "' AND Node.NAME=IpAddress.PARENTNODE AND Node.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.Type=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCEGROUP='SYS' AND AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.Type in " + types + filterCondn;
/*      */       }
/*      */       
/*  187 */       query = query + " GROUP BY RESOURCEID,RESOURCENAME ,AM_ManagedObject.TYPE,ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.SHORTNAME";
/*      */     }
/*      */     
/*  190 */     if ((startIndex != -1) && (noOfRows != -1))
/*      */     {
/*  192 */       query = DBQueryUtil.getInBetweenValuesWithSelectedColumns(query, startIndex, noOfRows, "AM_ManagedObject.RESOURCEID");
/*      */     }
/*      */     
/*  195 */     AMLog.debug("Servers Icon/Table View query:::" + query);
/*  196 */     long a = System.currentTimeMillis();
/*  197 */     ResultSet res = AMConnectionPool.executeQueryStmt(query);
/*  198 */     AMLog.debug("MAPS :  Time taken to find out device details is " + (System.currentTimeMillis() - a));
/*  199 */     while (res.next())
/*      */     {
/*  201 */       String resName = res.getString("name");
/*  202 */       String resTyp = res.getString("type");
/*  203 */       String id = "" + res.getInt("id");
/*      */       
/*  205 */       al.add(id);
/*  206 */       al_resname.add(resName);
/*  207 */       String dispName = res.getString("dispname");
/*  208 */       String sname = res.getString("SNAME");
/*  209 */       String ava = (String)statusMap.get(res.getInt("id") + "_" + res.getInt("attributeid"));
/*  210 */       if (ava == null)
/*      */       {
/*      */ 
/*      */ 
/*  214 */         ava = loadStatusFromEvent(res.getInt("id"), res.getInt("attributeid"), statusMap);
/*      */       }
/*      */       
/*  217 */       Map infoMap = new HashMap();
/*  218 */       infoMap.put("Resource_Name", resName);
/*  219 */       infoMap.put("Resource_Type", resTyp);
/*  220 */       infoMap.put("Resource_Id", id);
/*  221 */       infoMap.put("Resource_Ava", ava);
/*  222 */       infoMap.put("Resource_Disp_Name", dispName);
/*  223 */       infoMap.put("Short_Name", sname);
/*  224 */       resourceNames = resourceNames + "'" + resName + "',";
/*  225 */       listOfData.add(infoMap);
/*  226 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  228 */         resNameVsInfoMap.put(resName + "_" + Integer.parseInt(id) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE, infoMap);
/*      */       }
/*      */       else
/*      */       {
/*  232 */         resNameVsInfoMap.put(resName, infoMap);
/*      */       }
/*      */     }
/*  235 */     res.close();
/*  236 */     if (!resourceNames.equals(""))
/*      */     {
/*  238 */       addServices(resNameVsInfoMap, typeOfService, resourceNames.substring(0, resourceNames.length() - 1), uniqueKey, statusMap, filterCondn);
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  244 */       for (int i = 0; i < al.size(); i++)
/*      */       {
/*  246 */         String id = (String)al.get(i);
/*  247 */         String resname = (String)al_resname.get(i);
/*      */         
/*  249 */         String resNameKeyInMap = resname;
/*  250 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/*  252 */           resNameKeyInMap = resname + "_" + Integer.parseInt(id) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*      */         }
/*  254 */         Map reference = (HashMap)resNameVsInfoMap.get(resNameKeyInMap);
/*  255 */         List<Map<String, String>> monitorsUnderThis = new ArrayList();
/*  256 */         List<Map<String, String>> al1 = getAssociatedMonitorsForHost(resname, id, owner);
/*  257 */         List<Map<String, String>> al2 = getFileDirMonitorsForHost(resname, id, owner);
/*  258 */         List<Map<String, String>> al3 = getCustomMonitorsForHost(resname, id, owner);
/*  259 */         List<Map<String, String>> al4 = getConfBasedMonitorsForHost(resname, id, owner);
/*  260 */         if ((al1 != null) && (al1.size() > 0)) {
/*  261 */           monitorsUnderThis.addAll(al1);
/*      */         }
/*  263 */         if ((al2 != null) && (al2.size() > 0)) {
/*  264 */           monitorsUnderThis.addAll(al2);
/*      */         }
/*  266 */         if ((al3 != null) && (al3.size() > 0)) {
/*  267 */           monitorsUnderThis.addAll(al3);
/*      */         }
/*  269 */         if ((al4 != null) && (al4.size() > 0)) {
/*  270 */           monitorsUnderThis.addAll(al4);
/*      */         }
/*      */         
/*  273 */         if (monitorsUnderThis.size() > 0) {
/*  274 */           for (int j = 0; j < monitorsUnderThis.size(); j++) {
/*  275 */             Map<String, String> hm = (Map)monitorsUnderThis.get(j);
/*  276 */             String serviceId = (String)hm.get("RESOURCEID");
/*  277 */             String serviceType = (String)hm.get("TYPE");
/*  278 */             String serviceName = (String)hm.get("DISPLAYNAME");
/*  279 */             String avail_id = "";
/*  280 */             Hashtable availabilitykeys = (Hashtable)com.adventnet.appmanager.util.Constants.getGlobalObject("availabilitykeys");
/*      */             
/*  282 */             if (availabilitykeys != null) {
/*  283 */               avail_id = (String)availabilitykeys.get(serviceType);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  304 */               String serviceAva = (String)statusMap.get(serviceId + "_" + avail_id);
/*  305 */               if (serviceAva == null)
/*      */               {
/*  307 */                 serviceAva = loadStatusFromEvent(Integer.parseInt(serviceId), Integer.parseInt(avail_id), statusMap);
/*      */               }
/*      */               
/*  310 */               List serviceIdForType = null;
/*  311 */               serviceIdForType = (List)reference.get(serviceType + "_Id");
/*  312 */               if (serviceIdForType == null)
/*      */               {
/*  314 */                 serviceIdForType = new ArrayList();
/*  315 */                 reference.put(serviceType + "_Id", serviceIdForType);
/*      */               }
/*  317 */               if (!serviceIdForType.contains(serviceId))
/*      */               {
/*      */ 
/*  320 */                 serviceIdForType.add(serviceId);
/*      */                 
/*  322 */                 List serviceNamesForType = null;
/*  323 */                 serviceNamesForType = (List)reference.get(serviceType + "_Names");
/*  324 */                 if (serviceNamesForType == null)
/*      */                 {
/*  326 */                   serviceNamesForType = new ArrayList();
/*  327 */                   reference.put(serviceType + "_Names", serviceNamesForType);
/*      */                 }
/*  329 */                 serviceNamesForType.add(serviceName);
/*      */                 
/*  331 */                 List serviceAvaForType = null;
/*  332 */                 serviceAvaForType = (List)reference.get(serviceType + "_Ava");
/*  333 */                 if (serviceAvaForType == null)
/*      */                 {
/*  335 */                   serviceAvaForType = new ArrayList();
/*  336 */                   reference.put(serviceType + "_Ava", serviceAvaForType);
/*      */                 }
/*  338 */                 serviceAvaForType.add(serviceAva);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc) {
/*  346 */       exc.printStackTrace();
/*      */     }
/*  348 */     deleteLatestStatusTableEntries(uniqueKey);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  355 */     Map<String, HashMap<String, Object>> allVMsInfoMap = null;
/*  356 */     Map<String, HashMap<String, List<String>>> allConfMons = null;
/*  357 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/*  359 */       allVMsInfoMap = IconViewUtils.getAllVMs(request);
/*  360 */       allConfMons = IconViewUtils.getAllConfBasedMonitors(request);
/*      */     }
/*      */     else
/*      */     {
/*  364 */       allVMsInfoMap = IconViewUtils.getAllVMs(request);
/*  365 */       allConfMons = IconViewUtils.getAllConfBasedMonitors();
/*      */     }
/*  367 */     IconViewUtils.getTraditionalMonsUnderVMs(allVMsInfoMap);
/*      */     
/*      */ 
/*      */ 
/*  371 */     ArrayList<String> resourcesList = null;
/*  372 */     for (int i = 0; i < listOfData.size(); i++) {
/*  373 */       Map<String, Object> eachInfoMap = (HashMap)listOfData.get(i);
/*  374 */       String hostName = (String)eachInfoMap.get("Resource_Name");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  379 */       if (resourcesList == null) {
/*  380 */         resourcesList = new ArrayList();
/*      */       }
/*  382 */       resourcesList.add(hostName);
/*  383 */       if ((allConfMons != null) && (allConfMons.size() != 0))
/*      */       {
/*      */ 
/*  386 */         Map<String, List<String>> confMonDetails = (Map)allConfMons.get(hostName);
/*  387 */         if ((confMonDetails != null) && (confMonDetails.size() != 0))
/*      */         {
/*      */ 
/*  390 */           eachInfoMap.putAll(confMonDetails); }
/*      */       }
/*      */     }
/*  393 */     if ((allVMsInfoMap != null) && (allVMsInfoMap.size() > 0)) {
/*  394 */       Iterator<String> itr = allVMsInfoMap.keySet().iterator();
/*  395 */       while (itr.hasNext()) {
/*  396 */         String vmName = (String)itr.next();
/*  397 */         if ((resourcesList == null) || (!resourcesList.contains(vmName)))
/*      */         {
/*      */ 
/*  400 */           Map<String, Object> eachVMInfo = (Map)allVMsInfoMap.get(vmName);
/*  401 */           if ((allConfMons != null) && (allConfMons.size() > 0)) {
/*  402 */             Map<String, List<String>> confMonDetails = (Map)allConfMons.get(vmName);
/*  403 */             if ((confMonDetails != null) && (confMonDetails.size() > 0)) {
/*  404 */               eachVMInfo.putAll(confMonDetails);
/*      */             }
/*      */           }
/*  407 */           listOfData.add(eachVMInfo);
/*      */         }
/*      */       } }
/*  410 */     return listOfData;
/*      */   }
/*      */   
/*      */   private static void addServices(Map resourceNameVsInfoMap, String typeOfService, String commaSeparatedResourceNamesWithQuotes, String uniqueKey, Map statusMap) throws Exception
/*      */   {
/*  415 */     addServices(resourceNameVsInfoMap, typeOfService, commaSeparatedResourceNamesWithQuotes, uniqueKey, statusMap, null);
/*      */   }
/*      */   
/*      */ 
/*      */   private static void addServices(Map resourceNameVsInfoMap, String typeOfService, String commaSeparatedResourceNamesWithQuotes, String uniqueKey, Map statusMap, String filterCondition)
/*      */     throws Exception
/*      */   {
/*  422 */     String criticalQuery = "select IpAddress.PARENTNODE resname,AM_ManagedObject.DISPLAYNAME servicename,AM_ManagedObject.TYPE servicetype, AM_ManagedObject.RESOURCEID serviceid,AM_ATTRIBUTES.ATTRIBUTEID attributeid from InetService,AM_ManagedObject,AM_ATTRIBUTES,IpAddress";
/*  423 */     if (typeOfService != null)
/*      */     {
/*  425 */       criticalQuery = criticalQuery + ",AM_ManagedResourceType";
/*      */     }
/*  427 */     criticalQuery = criticalQuery + " where IpAddress.PARENTNODE IN (" + commaSeparatedResourceNamesWithQuotes + ") and InetService.INTERFACENAME=IpAddress.NAME and InetService.NAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ManagedObject.Type in " + types + " and AM_ATTRIBUTES.TYPE=1";
/*  428 */     if (typeOfService != null)
/*      */     {
/*  430 */       criticalQuery = criticalQuery + " and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='SYS' and AM_ManagedResourceType.RESOURCEGROUP='" + typeOfService + "'";
/*      */     }
/*  432 */     if (filterCondition != null) {
/*  433 */       criticalQuery = criticalQuery + filterCondition;
/*      */     }
/*  435 */     if (EnterpriseUtil.isAdminServer)
/*      */     {
/*      */ 
/*  438 */       criticalQuery = criticalQuery + " GROUP BY PARENTNODE,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.RESOURCEID";
/*      */     }
/*      */     else
/*      */     {
/*  442 */       criticalQuery = criticalQuery + " ORDER BY InetService.TARGETNAME";
/*      */     }
/*  444 */     long a = System.currentTimeMillis();
/*  445 */     ResultSet rs = AMConnectionPool.executeQueryStmt(criticalQuery);
/*  446 */     AMLog.debug("MAPS :  Query to find out service details is " + criticalQuery);
/*  447 */     AMLog.debug("MAPS :  Time taken to find out service details is " + (System.currentTimeMillis() - a));
/*  448 */     while (rs.next())
/*      */     {
/*  450 */       String resName = rs.getString("resname");
/*  451 */       String serviceType = rs.getString("servicetype");
/*  452 */       String serviceId = "" + rs.getInt("serviceid");
/*  453 */       String serviceName = rs.getString("servicename");
/*  454 */       String serviceAva = (String)statusMap.get(serviceId + "_" + rs.getInt("attributeid"));
/*  455 */       if (serviceAva == null)
/*      */       {
/*  457 */         serviceAva = loadStatusFromEvent(rs.getInt("serviceid"), rs.getInt("attributeid"), statusMap);
/*      */       }
/*  459 */       Map reference = null;
/*  460 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  462 */         reference = (HashMap)resourceNameVsInfoMap.get(resName + "_" + Integer.parseInt(serviceId) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE);
/*      */       }
/*      */       else
/*      */       {
/*  466 */         reference = (HashMap)resourceNameVsInfoMap.get(resName);
/*      */       }
/*  468 */       if (reference != null)
/*      */       {
/*  470 */         List serviceIdForType = null;
/*  471 */         serviceIdForType = (List)reference.get(serviceType + "_Id");
/*  472 */         if (serviceIdForType == null)
/*      */         {
/*  474 */           serviceIdForType = new ArrayList();
/*  475 */           reference.put(serviceType + "_Id", serviceIdForType);
/*      */         }
/*  477 */         if (!serviceIdForType.contains(serviceId))
/*      */         {
/*      */ 
/*  480 */           serviceIdForType.add(serviceId);
/*  481 */           List serviceNamesForType = null;
/*  482 */           serviceNamesForType = (List)reference.get(serviceType + "_Names");
/*  483 */           if (serviceNamesForType == null)
/*      */           {
/*  485 */             serviceNamesForType = new ArrayList();
/*  486 */             reference.put(serviceType + "_Names", serviceNamesForType);
/*      */           }
/*  488 */           serviceNamesForType.add(serviceName);
/*  489 */           List serviceAvaForType = null;
/*  490 */           serviceAvaForType = (List)reference.get(serviceType + "_Ava");
/*  491 */           if (serviceAvaForType == null)
/*      */           {
/*  493 */             serviceAvaForType = new ArrayList();
/*  494 */             reference.put(serviceType + "_Ava", serviceAvaForType);
/*      */           }
/*  496 */           serviceAvaForType.add(serviceAva);
/*      */         }
/*      */       } }
/*  499 */     rs.close();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getAllNetworksViewModel()
/*      */     throws Exception
/*      */   {
/*  508 */     return getNetworkServiceViewModel(null, null, null, -1, -1);
/*      */   }
/*      */   
/*      */   public static List getAllNetworksViewModel(int startIndex, int noOfRows)
/*      */     throws Exception
/*      */   {
/*  514 */     return getNetworkServiceViewModel(null, null, null, startIndex, noOfRows);
/*      */   }
/*      */   
/*      */   public static List getAllNetworksViewModel(HttpServletRequest request)
/*      */     throws Exception
/*      */   {
/*  520 */     return getNetworkServiceViewModel(null, null, request, -1, -1);
/*      */   }
/*      */   
/*      */   public static List getAllNetworksViewModel(HttpServletRequest request, int startIndex, int noOfRows)
/*      */     throws Exception
/*      */   {
/*  526 */     return getNetworkServiceViewModel(null, null, request, startIndex, noOfRows);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static List getServiceViewModel(String typeOfService)
/*      */     throws Exception
/*      */   {
/*  534 */     return getNetworkServiceViewModel(null, typeOfService);
/*      */   }
/*      */   
/*      */   public static List getNetworkViewModel(String networkAddress)
/*      */     throws Exception
/*      */   {
/*  540 */     return getNetworkServiceViewModel(networkAddress, null, null, -1, -1);
/*      */   }
/*      */   
/*      */   public static List getNetworkViewModel(String networkAddress, int startIndex, int noOfRows) throws Exception
/*      */   {
/*  545 */     return getNetworkServiceViewModel(networkAddress, null, null, startIndex, noOfRows);
/*      */   }
/*      */   
/*      */   public static List getNetworkViewModel(String networkAddress, HttpServletRequest request) throws Exception
/*      */   {
/*  550 */     return getNetworkServiceViewModel(networkAddress, null, request, -1, -1);
/*      */   }
/*      */   
/*      */   public static List getNetworkViewModel(String networkAddress, HttpServletRequest request, int startIndex, int noOfRows) throws Exception
/*      */   {
/*  555 */     return getNetworkServiceViewModel(networkAddress, null, request, startIndex, noOfRows);
/*      */   }
/*      */   
/*      */   private static String generateLatestStatusTable() throws Exception
/*      */   {
/*  560 */     String uniqueKey = Thread.currentThread().getName() + "#" + Thread.currentThread().hashCode() + "#" + System.currentTimeMillis();
/*      */     
/*      */ 
/*  563 */     String dumpAlertsQuery = "insert into AM_TEMP_LATESTSTATUS select '" + uniqueKey + "',AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID,COALESCE(MODTIME,-1) from AM_ManagedObject,AM_ATTRIBUTES,Alert where AM_ManagedObject.Type=AM_ATTRIBUTES.RESOURCETYPE and AM_ATTRIBUTES.TYPE=1 and AM_ManagedObject.resourceid=Alert.source and " + DBQueryUtil.castasVarchar("AM_ATTRIBUTES.attributeid") + "=Alert.category GROUP BY AM_ManagedObject.RESOURCEID,AM_ATTRIBUTES.ATTRIBUTEID,MODTIME";
/*      */     
/*  565 */     long a = System.currentTimeMillis();
/*  566 */     AMConnectionPool.executeUpdateStmt(dumpAlertsQuery);
/*  567 */     AMLog.debug("MAPS :  Time taken to dump events... " + (System.currentTimeMillis() - a));
/*  568 */     return uniqueKey;
/*      */   }
/*      */   
/*      */   private static void deleteLatestStatusTableEntries(String uniqueKey) throws Exception
/*      */   {
/*  573 */     String deleteDumpedEvents = "delete from AM_TEMP_LATESTSTATUS where ID='" + uniqueKey + "'";
/*  574 */     long a = System.currentTimeMillis();
/*  575 */     AMConnectionPool.executeUpdateStmt(deleteDumpedEvents);
/*  576 */     AMLog.debug("MAPS :  Time taken to delete status events... " + (System.currentTimeMillis() - a));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getTopNServers()
/*      */     throws Exception
/*      */   {
/*  586 */     String entity = "CPUUtilization";
/*  587 */     List retList = new ArrayList();
/*  588 */     long start = System.currentTimeMillis();
/*  589 */     String uniqueKey = DataCollectionDBUtil.generateLatestCollectionTimesTable();
/*      */     try
/*      */     {
/*  592 */       String sql = "select DISTINCT(AM_ManagedObject.RESOURCEID) id, AM_ManagedObject.DISPLAYNAME DISPLAYNAME, AM_ManagedObject.RESOURCENAME RESOURCENAME,  AM_ManagedObject.TYPE as TYPE,  AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME ,HostCpuMemDataCollected.CPUUTIL CURVALUE from AM_ManagedObject,HostCpuMemDataCollected,AM_TEMP_LATESTDATACOLLECTIONTIMES where    AM_ManagedObject.RESOURCEID=HostCpuMemDataCollected.RESOURCEID AND AM_ManagedObject.Type in " + types + " AND AM_ManagedObject.Type != 'Process'  AND  " + " AM_ManagedObject.RESOURCEID = AM_TEMP_LATESTDATACOLLECTIONTIMES.RESOURCEID AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME = HostCpuMemDataCollected.COLLECTIONTIME AND   " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.ID='" + uniqueKey + "' " + "ORDER BY HostCpuMemDataCollected.CPUUTIL DESC";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  599 */       sql = DBQueryUtil.getTopNValues(sql, 5);
/*      */       
/*  601 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*      */         try
/*      */         {
/*  605 */           long[] timeStamps = ReportUtilities.getTimeStamp("0");
/*  606 */           sql = "select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedObject.RESOURCEID as id,AM_ManagedObject.RESOURCENAME,min(MINVALUE) as Minimum,max(MAXVALUE) as Maximum,sum(TOTAL)/sum(TOTALCOUNT) as CURVALUE,AM_ManagedObject.RESOURCEID,min(ARCHIVEDTIME)as MinArchTime,max(ARCHIVEDTIME)as MaxArchTime,AM_MinMaxAvgData.ATTRIBUTEID from AM_MinMaxAvgData,AM_ManagedObject,AM_ATTRIBUTES where AM_ManagedObject.RESOURCEID=AM_MinMaxAvgData.RESID and AM_ATTRIBUTES.ATTRIBUTEID in (1357,1377,1457,1107,1207,1307,1657,807,708) and AM_MinMaxAvgData.DURATION=1 and AM_ATTRIBUTES.ATTRIBUTEID=AM_MinMaxAvgData.ATTRIBUTEID  and MINVALUE>=0 and MAXVALUE>=0 and AM_ManagedObject.TYPE=AM_ATTRIBUTES.RESOURCETYPE and ARCHIVEDTIME >=" + timeStamps[0] + " and ARCHIVEDTIME <=" + timeStamps[1] + " group by AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.RESOURCEID,RESID,AM_MinMaxAvgData.ATTRIBUTEID order by CURVALUE desc";
/*  607 */           sql = DBQueryUtil.getTopNValues(sql, 5);
/*  608 */           ArrayList list = ReportUtilities.getTabularData(sql, false);
/*  609 */           for (int i = 0; i < list.size(); i++)
/*      */           {
/*  611 */             ArrayList oneRow = (ArrayList)list.get(i);
/*  612 */             Map row = new HashMap();
/*  613 */             row.put("RESOURCEID", oneRow.get(2));
/*  614 */             row.put("TYPE", oneRow.get(1));
/*  615 */             row.put("RESOURCENAME", oneRow.get(3));
/*  616 */             row.put("DISPLAYNAME", oneRow.get(0));
/*  617 */             row.put(entity, oneRow.get(6));
/*  618 */             row.put("COLLECTIONTIME", "" + timeStamps[1]);
/*  619 */             retList.add(row);
/*      */           }
/*      */         }
/*      */         catch (Exception eee)
/*      */         {
/*  624 */           eee.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  629 */         ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  630 */         while (rs.next())
/*      */         {
/*  632 */           Map row = new HashMap();
/*  633 */           row.put("RESOURCEID", rs.getString("id"));
/*  634 */           row.put("TYPE", rs.getString("TYPE"));
/*  635 */           row.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/*  636 */           row.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  637 */           row.put(entity, rs.getString("CURVALUE"));
/*  638 */           row.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/*  639 */           retList.add(row);
/*      */         }
/*  641 */         rs.close();
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  645 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  649 */       if (!EnterpriseUtil.isAdminServer())
/*      */       {
/*  651 */         DataCollectionDBUtil.deleteLatestCollectionTimesTable(uniqueKey);
/*      */       }
/*      */     }
/*  654 */     return retList;
/*      */   }
/*      */   
/*      */   public static List getServicesInHost(String hostTargetName) throws Exception
/*      */   {
/*  659 */     return getServicesInHost(null, hostTargetName);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getServicesInHost(String resourceID, String hostTargetName)
/*      */     throws Exception
/*      */   {
/*  669 */     List retList = new ArrayList();
/*  670 */     long start = System.currentTimeMillis();
/*  671 */     String uniqueKey = DataCollectionDBUtil.generateLatestCollectionTimesTable();
/*  672 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  675 */       String sql = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.RESOURCENAME RESOURCENAME ,  AM_ManagedObject.DISPLAYNAME DISPLAYNAME, AM_ManagedObject.TYPE as TYPE, AM_ManagedObjectData.AVAILABILITY AVAILABILITY ,  AM_ManagedObjectData.HEALTH HEALTH, AM_ManagedObjectData.COLLECTIONTIME COLLECTIONTIME,  AM_ManagedObjectData.RESPONSETIME RESPONSETIME, IMAGEPATH  from  InetService,  AM_ManagedObjectData, AM_ManagedObject , IpAddress, AM_TEMP_LATESTDATACOLLECTIONTIMES, AM_ManagedResourceType where InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNODE ='" + hostTargetName + "' and AM_ManagedObject.RESOURCENAME = InetService.NAME AND  " + " AM_ManagedObject.Type in " + types + " AND AM_ManagedObject.RESOURCEID = AM_ManagedObjectData.RESID AND" + " AM_ManagedResourceType.RESOURCETYPE = AM_ManagedObject.TYPE AND" + " AM_ManagedObject.RESOURCEID = AM_TEMP_LATESTDATACOLLECTIONTIMES.RESOURCEID   AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME = AM_ManagedObjectData.COLLECTIONTIME AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.ID ='" + uniqueKey + "'";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  686 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  688 */         if (resourceID != null)
/*      */         {
/*      */ 
/*  691 */           int startID = Integer.parseInt(resourceID) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE * com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*  692 */           int endID = startID + com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*  693 */           sql = sql + " and (AM_ManagedObject.RESOURCEID between " + startID + " and " + endID + ") GROUP BY AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedObjectData.AVAILABILITY,AM_ManagedObjectData.HEALTH,AM_ManagedObjectData.COLLECTIONTIME,AM_ManagedObjectData.RESPONSETIME,IMAGEPATH";
/*      */         }
/*      */       }
/*      */       
/*  697 */       AMLog.debug("ViewsCreator : MAPS : Query to find services in host " + sql);
/*  698 */       rs = AMConnectionPool.executeQueryStmt(sql);
/*  699 */       while (rs.next())
/*      */       {
/*  701 */         Map row = new HashMap();
/*  702 */         row.put("RESOURCEID", rs.getString("RESOURCEID"));
/*  703 */         row.put("TYPE", rs.getString("TYPE"));
/*  704 */         row.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/*  705 */         row.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  706 */         row.put("RESPONSETIME", rs.getString("RESPONSETIME"));
/*  707 */         row.put("AVAILABILITY", rs.getString("AVAILABILITY"));
/*  708 */         row.put("HEALTH", rs.getString("HEALTH"));
/*  709 */         row.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/*  710 */         row.put("IMAGEPATH", rs.getString("IMAGEPATH"));
/*  711 */         retList.add(row);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  717 */         rs.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  724 */       DataCollectionDBUtil.deleteLatestCollectionTimesTable(uniqueKey);
/*      */     }
/*      */     
/*      */ 
/*  728 */     return retList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getServicesInHostForOwner(String hostTargetName, String owner)
/*      */     throws Exception
/*      */   {
/*  737 */     Vector resourceid = ClientDBUtil.getResourceIdentity(owner);
/*      */     
/*  739 */     List retList = new ArrayList();
/*  740 */     long start = System.currentTimeMillis();
/*  741 */     String uniqueKey = DataCollectionDBUtil.generateLatestCollectionTimesTable();
/*      */     
/*      */     try
/*      */     {
/*  745 */       String sql = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.RESOURCENAME RESOURCENAME ,  AM_ManagedObject.DISPLAYNAME DISPLAYNAME, AM_ManagedObject.TYPE as TYPE, AM_ManagedObjectData.AVAILABILITY AVAILABILITY ,  AM_ManagedObjectData.HEALTH HEALTH, AM_ManagedObjectData.COLLECTIONTIME COLLECTIONTIME,  AM_ManagedObjectData.RESPONSETIME RESPONSETIME  from  InetService,  AM_ManagedObjectData, AM_ManagedObject , IpAddress, AM_TEMP_LATESTDATACOLLECTIONTIMES where InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNODE ='" + hostTargetName + "' and AM_ManagedObject.RESOURCENAME = InetService.NAME AND  " + " AM_ManagedObject.RESOURCEID = AM_ManagedObjectData.RESID " + " AND AM_ManagedObject.RESOURCEID = AM_TEMP_LATESTDATACOLLECTIONTIMES.RESOURCEID   AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME = AM_ManagedObjectData.COLLECTIONTIME AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.ID ='" + uniqueKey + "' and " + DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceid);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  754 */       AMLog.debug("ViewsCreator : MAPS : Query to find services in host for owner " + sql);
/*  755 */       ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  756 */       while (rs.next())
/*      */       {
/*  758 */         Map row = new HashMap();
/*  759 */         row.put("RESOURCEID", rs.getString("RESOURCEID"));
/*  760 */         row.put("TYPE", rs.getString("TYPE"));
/*  761 */         row.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/*  762 */         row.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  763 */         row.put("RESPONSETIME", rs.getString("RESPONSETIME"));
/*  764 */         row.put("AVAILABILITY", rs.getString("AVAILABILITY"));
/*  765 */         row.put("HEALTH", rs.getString("HEALTH"));
/*  766 */         row.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/*  767 */         retList.add(row);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  772 */       DataCollectionDBUtil.deleteLatestCollectionTimesTable(uniqueKey);
/*      */     }
/*      */     
/*      */ 
/*  776 */     return retList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List getTopNApps()
/*      */     throws Exception
/*      */   {
/*  788 */     List retList = new ArrayList();
/*  789 */     long start = System.currentTimeMillis();
/*  790 */     String uniqueKey = DataCollectionDBUtil.generateLatestCollectionTimesTable();
/*      */     try
/*      */     {
/*  793 */       String sql = "select AM_ManagedObject.RESOURCEID id, AM_ManagedObject.DISPLAYNAME DISPLAYNAME,AM_ManagedObject.RESOURCENAME RESOURCENAME,  AM_ManagedObject.TYPE as TYPE,  AM_ManagedObjectData.HEALTH, AM_ManagedObjectData.AVAILABILITY , AM_ManagedObjectData.RESPONSETIME ,  AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME from AM_ManagedObject, AM_ManagedObjectData, AM_TEMP_LATESTDATACOLLECTIONTIMES where    AM_ManagedObject.RESOURCEID = AM_ManagedObjectData.RESID AND AM_ManagedObject.Type in " + types + " AND " + " AM_ManagedObject.RESOURCEID = AM_TEMP_LATESTDATACOLLECTIONTIMES.RESOURCEID AND " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.COLLECTIONTIME = AM_ManagedObjectData.COLLECTIONTIME AND   " + " AM_TEMP_LATESTDATACOLLECTIONTIMES.ID='" + uniqueKey + "' and AM_ManagedObject.TYPE <>'Process' and AM_ManagedObject.TYPE <>'HAI' " + "  ORDER BY AM_ManagedObjectData.RESPONSETIME DESC";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  801 */       sql = DBQueryUtil.getTopNValues(sql, 5);
/*  802 */       ResultSet rs = AMConnectionPool.executeQueryStmt(sql);
/*  803 */       while (rs.next())
/*      */       {
/*  805 */         Map row = new HashMap();
/*  806 */         row.put("RESOURCEID", rs.getString("id"));
/*  807 */         row.put("TYPE", rs.getString("TYPE"));
/*  808 */         row.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/*  809 */         row.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  810 */         row.put("RESPONSETIME", rs.getString("RESPONSETIME"));
/*  811 */         row.put("HEALTH", rs.getString("HEALTH"));
/*  812 */         row.put("AVAILABILITY", rs.getString("AVAILABILITY"));
/*  813 */         row.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/*  814 */         retList.add(row);
/*      */       }
/*  816 */       rs.close();
/*      */     }
/*      */     finally
/*      */     {
/*  820 */       DataCollectionDBUtil.deleteLatestCollectionTimesTable(uniqueKey);
/*      */     }
/*      */     
/*      */ 
/*  824 */     return retList;
/*      */   }
/*      */   
/*      */ 
/*      */   private static String loadStatusFromEvent(int resourceid, int attributeid, Map statusProps)
/*      */   {
/*  830 */     ResultSet eventRes = null;
/*  831 */     ava = "-1";
/*      */     try {
/*  833 */       String eventStatusQry = "select SEVERITY,TTIME from  Event  where ENTITY='" + resourceid + "_" + attributeid + "'  ORDER BY TTIME desc";
/*      */       
/*      */ 
/*  836 */       eventRes = AMConnectionPool.executeQueryStmt(eventStatusQry);
/*  837 */       if (eventRes.next()) {
/*  838 */         ava = eventRes.getInt("SEVERITY") + "";
/*  839 */         statusProps.put(resourceid + "_" + attributeid, ava);
/*      */       }
/*      */       
/*  842 */       return "-1";
/*      */ 
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  847 */       exc.printStackTrace();
/*  848 */       ava = "-1";
/*      */     }
/*      */     finally {
/*      */       try {
/*  852 */         if (eventRes != null) {
/*  853 */           AMConnectionPool.closeStatement(eventRes);
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Map<String, String>> getConfBasedMonitorsForHost(String serverName, String resourceId, String owner)
/*      */   {
/*  873 */     List<Map<String, String>> confMonitors = null;
/*      */     
/*  875 */     if ((serverName == null) || (serverName.length() == 0)) {
/*  876 */       return confMonitors;
/*      */     }
/*      */     
/*  879 */     String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/*      */     
/*  881 */     String[] ipAddrs = IconViewUtils.getAllHostIp(serverName);
/*      */     
/*  883 */     StringBuffer query = new StringBuffer();
/*  884 */     query.append("select DISTINCT TYPEID ,TYPENAME,IMAGEPATH from AM_MONITOR_TYPES,AM_ManagedObject, AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_MONITOR_TYPES.TYPENAME  and AM_ManagedResourceType.RESOURCETYPE=AM_MONITOR_TYPES.TYPENAME  and AM_MONITOR_TYPES.AMCREATED='YES' and AM_MONITOR_TYPES.TYPENAME NOT IN ('VMWare ESX/ESXi ','VirtualMachine','Hyper-V-Server','HyperVVirtualMachine','RDSInstance','EC2Instance','Amazon','SSLCertificateMonitor')");
/*  885 */     if (!owner.equals("admin"))
/*      */     {
/*  887 */       query.append(" and ");
/*  888 */       Vector resourceid = ClientDBUtil.getResourceIdentity(owner);
/*  889 */       String condn = DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceid);
/*  890 */       query.append(condn);
/*      */     }
/*  892 */     query.append(" order by TYPEID asc");
/*      */     try {
/*  894 */       ManagedApplication mp = new ManagedApplication();
/*  895 */       AMLog.info("ViewCreator : getConfBasedMonitorsForHost: query 1:" + query.toString());
/*  896 */       ArrayList<ArrayList<String>> rows = mp.getRows(query.toString());
/*  897 */       if ((rows == null) || (rows.size() == 0)) {
/*  898 */         return confMonitors;
/*      */       }
/*  900 */       confMonitors = new ArrayList();
/*      */       
/*  902 */       String typeID = "";
/*  903 */       StringBuffer queryBuff = new StringBuffer();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  910 */       for (int i = 0; i < rows.size(); i++)
/*      */       {
/*  912 */         ArrayList<String> typeInfoList = (ArrayList)rows.get(i);
/*  913 */         typeID = (String)typeInfoList.get(0);
/*  914 */         String confHost = IconViewUtils.getColumnNameUsedForHost((String)typeInfoList.get(1));
/*  915 */         if ((confHost != null) && (confHost.length() != 0) && (!confHost.equalsIgnoreCase("null")))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  922 */           queryBuff.append("select AM_ManagedObject.RESOURCEID,");
/*  923 */           queryBuff.append(confHost);
/*  924 */           queryBuff.append(",DISPLAYNAME from AM_ARGS_");
/*  925 */           queryBuff.append(typeID);
/*  926 */           queryBuff.append(",AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_ARGS_");
/*  927 */           queryBuff.append(typeID);
/*  928 */           queryBuff.append(".RESOURCEID and " + eumChildListCond);
/*      */           
/*  930 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/*  932 */             int startID = Integer.parseInt(resourceId) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE * com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*  933 */             int endID = startID + com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*  934 */             queryBuff.append(" and (AM_ManagedObject.RESOURCEID between ");
/*  935 */             queryBuff.append(startID);
/*  936 */             queryBuff.append(" and ");
/*  937 */             queryBuff.append(endID);
/*  938 */             queryBuff.append(")");
/*      */           }
/*  940 */           AMLog.info("ViewCreator : getConfBasedMonitorsForHost: query 2:" + queryBuff.toString());
/*  941 */           ArrayList<ArrayList<String>> confMonArgsList = mp.getRows(queryBuff.toString());
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  948 */           for (int j = 0; j < confMonArgsList.size(); j++) {
/*  949 */             ArrayList<String> resourceInfoList = (ArrayList)confMonArgsList.get(j);
/*  950 */             String confHostName = (String)resourceInfoList.get(1);
/*  951 */             if (confHostName != null)
/*      */             {
/*      */ 
/*  954 */               if (confHostName.equalsIgnoreCase("localhost")) {
/*  955 */                 confHostName = IconViewUtils.getLocalHost(0);
/*      */               }
/*  957 */               boolean hostChk = checkHost(serverName, ipAddrs, confHostName);
/*  958 */               if (hostChk)
/*      */               {
/*      */ 
/*  961 */                 Map<String, String> hm = new HashMap();
/*  962 */                 hm.put("RESOURCEID", resourceInfoList.get(0));
/*  963 */                 hm.put("RESOURCENAME", resourceInfoList.get(1));
/*  964 */                 hm.put("DISPLAYNAME", resourceInfoList.get(2));
/*  965 */                 hm.put("TYPE", typeInfoList.get(1));
/*  966 */                 hm.put("IMAGEPATH", typeInfoList.get(2));
/*  967 */                 confMonitors.add(hm);
/*      */               } } }
/*  969 */           queryBuff.delete(0, queryBuff.length());
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/*  973 */       ex.printStackTrace();
/*      */     }
/*  975 */     query.delete(0, query.length());
/*  976 */     query = null;
/*  977 */     return confMonitors;
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAssociatedMonitorsForHost(String serverName, String resourceId, String owner) {
/*  981 */     List al = getAssociatedMonitorsForHost(serverName, resourceId, owner, "both");
/*  982 */     return al;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Map<String, String>> getAssociatedMonitorsForHost(String serverName, String resourceId, String owner, String monType)
/*      */   {
/*  998 */     ResultSet rs = null;
/*  999 */     aListUrlMons = null;
/*      */     try {
/* 1001 */       String appendType = "";
/* 1002 */       if (monType.equalsIgnoreCase("Url")) {
/* 1003 */         appendType = "('UrlMonitor','UrlSeq','RBM','RBMURL')";
/* 1004 */       } else if (monType.equalsIgnoreCase("Script")) {
/* 1005 */         appendType = "('Script Monitor')";
/*      */       } else {
/* 1007 */         appendType = "('UrlMonitor','UrlSeq','RBM','RBMURL','Script Monitor')";
/*      */       }
/* 1009 */       aListUrlMons = new ArrayList();
/* 1010 */       StringBuffer queryBuff = new StringBuffer();
/* 1011 */       queryBuff.append("select AM_ManagedObject.RESOURCEID as resid,AM_ManagedObject.RESOURCENAME as resname,AM_ManagedObject.DISPLAYNAME as dispname,AM_ManagedObject.TYPE as type,AM_ManagedResourceType.IMAGEPATH as image from AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_ManagedResourceType where AM_ManagedObject.type in ");
/* 1012 */       queryBuff.append(appendType);
/* 1013 */       queryBuff.append(" and AM_PARENTCHILDMAPPER.PARENTID=");
/* 1014 */       queryBuff.append(resourceId);
/*      */       
/* 1016 */       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/*      */       
/* 1018 */       queryBuff.append(" and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and " + eumChildListCond);
/*      */       
/* 1020 */       if (!owner.equals("admin")) {
/* 1021 */         queryBuff.append(" and ");
/* 1022 */         Vector resourceid = ClientDBUtil.getResourceIdentity(owner);
/* 1023 */         String condn = DependantMOUtil.getCondition("AM_ManagedObject.RESOURCEID", resourceid);
/* 1024 */         queryBuff.append(condn);
/*      */       }
/* 1026 */       queryBuff.append(" order by AM_ManagedObject.DISPLAYNAME asc");
/* 1027 */       AMLog.info("ViewCreator : getAssociatedMonitorsForHost: query " + queryBuff.toString());
/* 1028 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 1029 */       while (rs.next()) {
/* 1030 */         Map<String, String> hm = new HashMap();
/* 1031 */         hm.put("RESOURCEID", rs.getString("resid"));
/* 1032 */         hm.put("RESOURCENAME", rs.getString("resname"));
/* 1033 */         hm.put("DISPLAYNAME", rs.getString("dispname"));
/* 1034 */         hm.put("TYPE", rs.getString("type"));
/* 1035 */         hm.put("IMAGEPATH", rs.getString("image"));
/* 1036 */         aListUrlMons.add(hm);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1048 */       return aListUrlMons;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1039 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 1043 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception ex1) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Map<String, String>> getCustomMonitorsForHost(String serverName, String resourceId, String owner)
/*      */   {
/* 1060 */     List<Map<String, String>> customMonitors = null;
/* 1061 */     if ((serverName == null) || (serverName.length() == 0)) {
/* 1062 */       return customMonitors;
/*      */     }
/*      */     
/* 1065 */     String[] ipAddrs = IconViewUtils.getAllHostIp(serverName);
/*      */     
/* 1067 */     StringBuffer query = new StringBuffer();
/* 1068 */     query.append("select wmi.RESOURCEID,RESOURCENAME,HOSTNAME,amo.DISPLAYNAME,amrt.RESOURCETYPE,IMAGEPATH from AM_ManagedObject amo,AM_CAM_WMI_EXT_INFO wmi,AM_SCRIPTHOSTDETAILS amscr,AM_ManagedResourceType amrt where wmi.RESOURCEID=amo.RESOURCEID and amscr.ID=wmi.HOSTID and amrt.RESOURCETYPE=amo.TYPE");
/* 1069 */     if (!owner.equals("admin"))
/*      */     {
/* 1071 */       query.append(" and ");
/* 1072 */       Vector resourceid = ClientDBUtil.getResourceIdentity(owner);
/* 1073 */       String condn = DependantMOUtil.getCondition("amo.RESOURCEID", resourceid);
/* 1074 */       query.append(condn);
/*      */     }
/* 1076 */     query.append(" order by RESOURCENAME asc");
/*      */     try {
/* 1078 */       ManagedApplication mp = new ManagedApplication();
/* 1079 */       AMLog.info("ViewCreator : getCustomMonitorsForHost: query :" + query.toString());
/* 1080 */       ArrayList<ArrayList<String>> rows = mp.getRows(query.toString());
/* 1081 */       if ((rows == null) || (rows.size() == 0)) {
/* 1082 */         return customMonitors;
/*      */       }
/* 1084 */       customMonitors = new ArrayList();
/* 1085 */       for (int i = 0; i < rows.size(); i++) {
/* 1086 */         ArrayList<String> resourceInfoList = (ArrayList)rows.get(i);
/* 1087 */         String childHostName = (String)resourceInfoList.get(2);
/* 1088 */         boolean hostChk = checkHost(serverName, ipAddrs, childHostName);
/* 1089 */         if (hostChk)
/*      */         {
/*      */ 
/* 1092 */           Map<String, String> hm = new HashMap();
/* 1093 */           hm.put("RESOURCEID", resourceInfoList.get(0));
/* 1094 */           hm.put("RESOURCENAME", resourceInfoList.get(1));
/* 1095 */           hm.put("HOSTNAME", resourceInfoList.get(2));
/* 1096 */           hm.put("DISPLAYNAME", resourceInfoList.get(3));
/* 1097 */           hm.put("TYPE", resourceInfoList.get(4));
/* 1098 */           hm.put("IMAGEPATH", resourceInfoList.get(5));
/* 1099 */           customMonitors.add(hm);
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1103 */       ex.printStackTrace();
/*      */     }
/* 1105 */     query.delete(0, query.length());
/* 1106 */     query = null;
/* 1107 */     return customMonitors;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Map<String, String>> getFileDirMonitorsForHost(String serverName, String resourceId, String owner)
/*      */   {
/* 1119 */     List<Map<String, String>> fileDirMonitors = null;
/* 1120 */     StringBuffer querybuf = new StringBuffer();
/* 1121 */     querybuf.append("select amo.RESOURCEID,amo.DISPLAYNAME,fdir.FILE_DIR,fdir.MTYPE,fdir.SERVERSITE,IMAGEPATH from AM_ManagedObject amo join AM_FILEDIR fdir on amo.RESOURCEID=fdir.RESOURCEID  join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=amo.TYPE");
/* 1122 */     if (!owner.equals("admin"))
/*      */     {
/* 1124 */       querybuf.append(" and ");
/* 1125 */       Vector resourceid = ClientDBUtil.getResourceIdentity(owner);
/* 1126 */       String condn = DependantMOUtil.getCondition("amo.RESOURCEID", resourceid);
/* 1127 */       querybuf.append(condn);
/*      */     }
/* 1129 */     querybuf.append(" order by DISPLAYNAME asc");
/*      */     try {
/* 1131 */       ManagedApplication mp = new ManagedApplication();
/* 1132 */       AMLog.info("ViewCreator : getFileDirMonitorsForHost: query 1:" + querybuf.toString());
/* 1133 */       ArrayList<ArrayList<String>> rows = mp.getRows(querybuf.toString());
/* 1134 */       if ((rows == null) || (rows.size() == 0)) {
/* 1135 */         return fileDirMonitors;
/*      */       }
/* 1137 */       boolean matched = false;
/* 1138 */       fileDirMonitors = new ArrayList();
/* 1139 */       for (int i = 0; i < rows.size(); i++) {
/* 1140 */         ArrayList<String> resourceInfoList = (ArrayList)rows.get(i);
/* 1141 */         String site = (String)resourceInfoList.get(4);
/* 1142 */         if (site.equals("1")) {
/* 1143 */           String hostName = IconViewUtils.getLocalHost(0);
/*      */           
/* 1145 */           if ((hostName != null) && (hostName.equalsIgnoreCase(serverName))) {
/* 1146 */             matched = true;
/*      */           }
/*      */           else {
/* 1149 */             hostName = IconViewUtils.getLocalHost(2);
/* 1150 */             if ((hostName != null) && (hostName.equalsIgnoreCase(serverName))) {
/* 1151 */               matched = true;
/*      */             }
/*      */           }
/* 1154 */         } else if (site.equals("2"))
/*      */         {
/* 1156 */           querybuf.delete(0, querybuf.length());
/* 1157 */           querybuf.append("select HOSTNAME from AM_SCRIPTHOSTDETAILS,AM_SCRIPTHOST_MAPPER where AM_SCRIPTHOST_MAPPER.HOSTID=AM_SCRIPTHOSTDETAILS.ID and AM_SCRIPTHOST_MAPPER.SCRIPTID=");
/* 1158 */           querybuf.append((String)resourceInfoList.get(0));
/* 1159 */           querybuf.append(" union select m.RESOURCENAME from AM_ManagedObject m, AM_SCRIPTHOST_MAPPER smap,HostDetails h where m.resourceid=smap.HOSTID and h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and smap.SCRIPTID=");
/* 1160 */           querybuf.append((String)resourceInfoList.get(0));
/* 1161 */           ArrayList hostNameRow = mp.getRows(querybuf.toString());
/* 1162 */           AMLog.info("ViewCreator : getFileDirMonitorsForHost: query 2:" + querybuf.toString());
/* 1163 */           if ((hostNameRow == null) || (hostNameRow.size() == 0)) {
/*      */             continue;
/*      */           }
/* 1166 */           String hostName = (String)((ArrayList)hostNameRow.get(0)).get(0);
/* 1167 */           String[] ipAddrs = IconViewUtils.getAllHostIp(serverName);
/* 1168 */           matched = checkHost(serverName, ipAddrs, hostName);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1178 */         if (matched)
/*      */         {
/*      */ 
/* 1181 */           Map<String, String> hm = new HashMap();
/* 1182 */           hm.put("RESOURCEID", resourceInfoList.get(0));
/* 1183 */           hm.put("DISPLAYNAME", resourceInfoList.get(1));
/* 1184 */           hm.put("FILEDIR", resourceInfoList.get(2));
/* 1185 */           hm.put("TYPE", resourceInfoList.get(3));
/* 1186 */           hm.put("IMAGEPATH", resourceInfoList.get(5));
/* 1187 */           hm.put("RESOURCENAME", serverName);
/* 1188 */           fileDirMonitors.add(hm);
/* 1189 */           matched = false;
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1193 */       ex.printStackTrace();
/*      */     }
/* 1195 */     querybuf.delete(0, querybuf.length());
/* 1196 */     querybuf = null;
/* 1197 */     return fileDirMonitors;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean checkHost(String qualHostName, String[] allIPAddr, String childHostName)
/*      */   {
/* 1209 */     if ((qualHostName != null) && (qualHostName.equalsIgnoreCase(childHostName))) {
/* 1210 */       return true;
/*      */     }
/* 1212 */     if ((allIPAddr == null) || (allIPAddr.length == 0)) {
/* 1213 */       return false;
/*      */     }
/* 1215 */     String[] confHostAllIp = IconViewUtils.getAllHostIp(childHostName);
/* 1216 */     if ((confHostAllIp == null) || (confHostAllIp.length == 0)) {
/* 1217 */       return false;
/*      */     }
/* 1219 */     boolean ok = false;
/* 1220 */     for (int i = 0; i < allIPAddr.length; i++) {
/* 1221 */       for (int j = 0; j < confHostAllIp.length; j++) {
/* 1222 */         if (allIPAddr[i].equals(confHostAllIp[j])) {
/* 1223 */           ok = true;
/* 1224 */           break;
/*      */         }
/*      */       }
/* 1227 */       if (ok) {
/*      */         break;
/*      */       }
/*      */     }
/* 1231 */     return ok;
/*      */   }
/*      */   
/*      */   public static void main(String[] args) throws Exception
/*      */   {
/* 1236 */     System.out.println(" View Creator output is ..." + getAllNetworksViewModel());
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\views\ViewsCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */