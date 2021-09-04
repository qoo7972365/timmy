/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.AMServerStartup;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.struts.actions.AdminActions;
/*      */ import com.adventnet.appmanager.struts.actions.AdminTools;
/*      */ import com.adventnet.appmanager.struts.actions.DataCollectionController;
/*      */ import com.adventnet.appmanager.struts.actions.DownTimeSchedulerAction;
/*      */ import com.adventnet.appmanager.struts.actions.HAIDManagerAction;
/*      */ import com.adventnet.appmanager.struts.actions.SearchAction;
/*      */ import com.adventnet.appmanager.struts.actions.ShowApplication;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DifferentialPollingUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.MyThreadLocal;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.util.StartUtil;
/*      */ import com.adventnet.appmanager.util.UserSessionHandler;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.adventnet.nms.topodb.TopoAPI;
/*      */ import com.adventnet.security.authorization.Coding;
/*      */ import com.manageengine.appmanager.diagnostics.util.APMDiagnosticsFaultHandler;
/*      */ import com.manageengine.appmanager.diagnostics.util.DiagnosticsAPIUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.util.ADAuthenticationUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.manageengine.appmanager.utils.client.ClientAuditUtil;
/*      */ import com.me.apm.eventlog.util.EventLogUtil;
/*      */ import com.me.apm.server.audit.AuditUtil;
/*      */ import java.io.File;
/*      */ import java.io.StringWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.Attributes;
/*      */ import javax.naming.directory.DirContext;
/*      */ import javax.naming.directory.SearchResult;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.apache.struts.mock.MockHttpServletRequest;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONObject;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ public class CommonAPIUtil
/*      */ {
/*  106 */   private static ArrayList<String> supportedViews = new ArrayList();
/*  107 */   public static AuditUtil auditUtil = new AuditUtil();
/*  108 */   public static ClientAuditUtil cliAuditUtil = new ClientAuditUtil();
/*  109 */   public static Hashtable<String, Hashtable<String, String>> apikeys = new Hashtable();
/*  110 */   public static MGActionNotifier mgNotifier = MGActionNotifier.getInstance();
/*      */   
/*  112 */   static { supportedViews.add("toplevel");
/*  113 */     supportedViews.add("bottomlevel");
/*  114 */     supportedViews.add("all");
/*      */   }
/*      */   
/*      */   public static String getMGList(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  120 */     String outputString = "";
/*  121 */     String uri = request.getRequestURI();
/*  122 */     boolean isJsonFormat = uri.contains("json");
/*  123 */     if (isJsonFormat)
/*      */     {
/*  125 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else
/*      */     {
/*  129 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*  131 */     if (!validateMonitorGroupDetails(request))
/*      */     {
/*  133 */       AMLog.debug("REST API : The specified value for the request parameter is not valid. Kindly check whether the value given is present or associated to specified user.");
/*  134 */       String message = FormatUtil.getString("am.restapi.invalidmg.txt");
/*  135 */       String errorCode = "4042";
/*  136 */       outputString = URITree.generateXML(request, response, message, errorCode);
/*      */     }
/*      */     else
/*      */     {
/*  140 */       HashMap<String, Properties> mgAvail = null;
/*  141 */       HashMap<String, String> healthAvailEntitys = null;
/*  142 */       HashMap<String, Object> mgDetails = new HashMap();
/*  143 */       ArrayList<Hashtable<?, ?>> mgList = new ArrayList();
/*  144 */       mgDetails.put("uri", uri);
/*  145 */       mgDetails.put("response-code", "4000");
/*  146 */       mgDetails.put("sortingParam", "AVAILABILITYSEVERITY,HEALTHSEVERITY,DISPLAYNAME,RESOURCEID");
/*  147 */       mgDetails.put("parentNodeName", "MonitorGroups");
/*  148 */       mgDetails.put("nodeName", "MonitorGroup");
/*  149 */       mgDetails.put("subNodeName", "SubMonitorGroup");
/*  150 */       String mgId = request.getParameter("groupId");
/*  151 */       String mgName = request.getParameter("groupName");
/*  152 */       String treeview = request.getParameter("treeview");
/*  153 */       boolean getOutageReports = (request.getParameter("outageReports") == null) || (request.getParameter("outageReports").equalsIgnoreCase("true"));
/*  154 */       boolean getSeverityDetails = (request.getParameter("severityDetails") == null) || (request.getParameter("severityDetails").equalsIgnoreCase("true"));
/*  155 */       if (mgName != null) {
/*  156 */         mgId = Constants.getResourceID(mgName);
/*      */       }
/*  158 */       HashMap groupdetail = listGroupMap(request, true);
/*  159 */       AMLog.info("RESTAPI: groupdetail:" + groupdetail);
/*  160 */       if ((groupdetail != null) && (groupdetail.containsKey("invalid"))) {
/*  161 */         String errorCode = "";
/*  162 */         String message = "";
/*  163 */         if ((treeview != null) && (treeview.equalsIgnoreCase("toplevel"))) {
/*  164 */           errorCode = "4571";
/*  165 */           message = FormatUtil.getString("am.restapi.parent.group.not.exist.txt");
/*  166 */         } else if ((treeview != null) && (treeview.equalsIgnoreCase("bottomlevel"))) {
/*  167 */           errorCode = "4572";
/*  168 */           message = FormatUtil.getString("am.restapi.child.group.not.exist.txt");
/*  169 */         } else if ((treeview != null) && (!treeview.equals("default")) && (!supportedViews.contains(treeview))) {
/*  170 */           errorCode = "4573";
/*  171 */           message = FormatUtil.getString("am.restapi.listmonitorgroup.supported.treeviews.txt");
/*      */         } else {
/*  173 */           errorCode = "4570";
/*  174 */           message = FormatUtil.getString("am.restapi.invalid.request.txt");
/*      */         }
/*  176 */         outputString = URITree.generateXML(request, response, message, errorCode);
/*  177 */         return outputString;
/*      */       }
/*  179 */       ArrayList<ArrayList<String>> firstLevelGrouplist = (ArrayList)groupdetail.get("applications");
/*  180 */       Hashtable chilmos = (Hashtable)groupdetail.get("childlist");
/*  181 */       if (getOutageReports) {
/*  182 */         mgAvail = (HashMap)ReportUtilities.getTodaysAvailabilityForAllMonitorGroups();
/*      */       }
/*  184 */       for (int i = 0; i < firstLevelGrouplist.size(); i++) {
/*      */         try
/*      */         {
/*  187 */           Hashtable monGrp = new Hashtable();
/*  188 */           ArrayList mgAList = (ArrayList)firstLevelGrouplist.get(i);
/*  189 */           AMLog.info("RESTAPI mgAList: " + mgAList);
/*  190 */           String monitorGroupName = mgAList.get(0) != null ? (String)mgAList.get(0) : "";
/*  191 */           String monitorGroupType = "0".equals(mgAList.get(2)) ? "Monitor Group" : "Sub Group";
/*  192 */           String restitle = mgAList.get(8) != null ? (String)mgAList.get(1) + "-UnManaged" : (String)mgAList.get(1);
/*  193 */           String monitorGroupresid = mgAList.get(6) != null ? (String)mgAList.get(6) : "";
/*  194 */           String monitorGroupActionStatus = mgAList.get(9) != null ? "disabled" : ((String)mgAList.get(9)).equals("1") ? "enabled" : "disabled";
/*  195 */           String DetailsPageURL = "/showapplication.do?&method=showApplication&haid=" + monitorGroupresid;
/*  196 */           String monitorGroupDescription = (String)mgAList.get(10);
/*  197 */           if (!monitorGroupresid.equals("orphaned")) {
/*  198 */             monGrp.put("NAME", monitorGroupName);
/*  199 */             monGrp.put("Type", monitorGroupType);
/*  200 */             monGrp.put("DISPLAYNAME", restitle);
/*  201 */             monGrp.put("RESOURCEID", monitorGroupresid);
/*  202 */             monGrp.put("Action", monitorGroupActionStatus);
/*  203 */             monGrp.put("DetailsPageURL", DetailsPageURL);
/*  204 */             monGrp.put("DESCRIPTION", monitorGroupDescription);
/*  205 */             if ((mgId != null) && (mgId.equals(monitorGroupresid))) {
/*  206 */               monGrp.put("SELECTED", "true");
/*      */             }
/*  208 */             if (getSeverityDetails)
/*      */             {
/*  210 */               healthAvailEntitys = getHealthAvailabilityDetails(monitorGroupresid, "HAI");
/*  211 */               monGrp.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/*  212 */               monGrp.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/*  213 */               monGrp.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/*  214 */               monGrp.put("HealthRCAURL", "/jsp/RCA.jsp?resourceid=" + monitorGroupresid + "&attributeid=18");
/*  215 */               monGrp.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/*  216 */               monGrp.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/*  217 */               monGrp.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/*  218 */               monGrp.put("AvailabilityRCAURL", "/jsp/RCA.jsp?resourceid=" + monitorGroupresid + "&attributeid=17");
/*      */             }
/*  220 */             if (getOutageReports)
/*      */             {
/*  222 */               String todaysAvail = "100";
/*  223 */               String todaysSchdDownTime = "0";
/*  224 */               String todaysUnmng = "0";
/*  225 */               String todaysUnAvail = "0";
/*  226 */               if ((mgAvail != null) && (mgAvail.containsKey(monitorGroupresid))) {
/*  227 */                 Properties mgProps = (Properties)mgAvail.get(monitorGroupresid);
/*  228 */                 todaysAvail = mgProps.getProperty("available");
/*  229 */                 todaysSchdDownTime = mgProps.getProperty("ServicesSchPercent");
/*  230 */                 todaysUnmng = mgProps.getProperty("ServicesUnMgPercent");
/*  231 */                 todaysUnAvail = mgProps.getProperty("unavailable");
/*      */               }
/*  233 */               if (todaysAvail != null) {
/*  234 */                 monGrp.put("TODAYAVAILPERCENT", todaysAvail);
/*      */               }
/*  236 */               if (todaysSchdDownTime != null) {
/*  237 */                 monGrp.put("TODAYSCHEDDOWNPERCENT", todaysSchdDownTime);
/*      */               }
/*  239 */               if (todaysUnmng != null) {
/*  240 */                 monGrp.put("TODAYUNMANGDPERCENT", todaysUnmng);
/*      */               }
/*  242 */               if (todaysUnAvail != null) {
/*  243 */                 monGrp.put("TODAYUNAVAILPERCENT", todaysUnAvail);
/*      */               }
/*      */               
/*  246 */               String outages = (String)mgAList.get(11);
/*  247 */               String availUnknownCount = (String)mgAList.get(12);
/*  248 */               String upCount = (String)mgAList.get(13);
/*  249 */               String downCount = (String)mgAList.get(14);
/*  250 */               String healthUnknownCount = (String)mgAList.get(15);
/*  251 */               String clearCount = (String)mgAList.get(16);
/*  252 */               String warningCount = (String)mgAList.get(17);
/*  253 */               String criticalCount = (String)mgAList.get(18);
/*  254 */               if (outages != null) {
/*  255 */                 monGrp.put("OUTAGES", outages);
/*      */               }
/*      */               
/*  258 */               if (availUnknownCount != null) {
/*  259 */                 monGrp.put("AvailabilityUnknownCount", availUnknownCount);
/*      */               }
/*  261 */               if (upCount != null) {
/*  262 */                 monGrp.put("UPCOUNT", upCount);
/*      */               }
/*  264 */               if (downCount != null) {
/*  265 */                 monGrp.put("DOWNCOUNT", downCount);
/*      */               }
/*  267 */               if (healthUnknownCount != null) {
/*  268 */                 monGrp.put("HealthUnknownCount", healthUnknownCount);
/*      */               }
/*  270 */               if (clearCount != null) {
/*  271 */                 monGrp.put("CLEARCOUNT", clearCount);
/*      */               }
/*  273 */               if (warningCount != null) {
/*  274 */                 monGrp.put("WARNINGCOUNT", warningCount);
/*      */               }
/*  276 */               if (criticalCount != null) {
/*  277 */                 monGrp.put("CRITICALCOUNT", criticalCount);
/*      */               }
/*      */             }
/*      */           }
/*  281 */           if (chilmos.get(String.valueOf(monitorGroupresid)) != null) {
/*  282 */             ArrayList singlechilmos = (ArrayList)chilmos.get(String.valueOf(monitorGroupresid));
/*  283 */             getAllChildNodestoDisplay(mgId, singlechilmos, monitorGroupresid, chilmos, 1, monGrp, mgAvail, getOutageReports, getSeverityDetails, false);
/*      */           }
/*  285 */           mgList.add(monGrp);
/*      */         }
/*      */         catch (Exception e) {
/*  288 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  292 */       mgDetails.put("result", mgList);
/*  293 */       outputString = getOutputAsString(mgDetails, isJsonFormat);
/*      */     }
/*  295 */     return outputString;
/*      */   }
/*      */   
/*      */   private static boolean validateMonitorGroupDetails(HttpServletRequest request)
/*      */   {
/*  300 */     if ((request.getParameter("groupName") == null) && (request.getParameter("groupId") == null))
/*      */     {
/*      */ 
/*  303 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  307 */     String operatorCondition = getConditionForOperator(request);
/*  308 */     operatorCondition = operatorCondition.contains("in") ? " and AM_HOLISTICAPPLICATION.HAID " + operatorCondition.substring(operatorCondition.indexOf("in")) : operatorCondition;
/*  309 */     AMLog.info("RESTAPI : validate Monitor Group: operator condition:" + operatorCondition);
/*  310 */     String chkQuery = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION where resourceid=haid " + operatorCondition;
/*  311 */     String mgId = request.getParameter("groupId");
/*  312 */     String mgName = request.getParameter("groupName");
/*      */     
/*  314 */     chkQuery = mgName != null ? chkQuery + " and AM_ManagedObject.DISPLAYNAME='" + mgName + "'" : mgId != null ? chkQuery + " and AM_HOLISTICAPPLICATION.HAID=" + mgId : chkQuery;
/*  315 */     AMLog.info("RESTAPI: check query for List Monitor groups:" + chkQuery);
/*  316 */     ResultSet rs = null;
/*  317 */     boolean chkResult = false;
/*      */     try
/*      */     {
/*  320 */       rs = AMConnectionPool.executeQueryStmt(chkQuery);
/*  321 */       if (rs.next())
/*      */       {
/*  323 */         chkResult = true;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  328 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  332 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  334 */     return chkResult;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Properties getServerDetails(String resourceType, String resourceId)
/*      */   {
/*  340 */     Properties props = new Properties();
/*  341 */     String checkalreadyexists = "";
/*  342 */     String hostOs = "";
/*  343 */     String hostId = resourceId;
/*  344 */     String hostName = "";
/*      */     try
/*      */     {
/*  347 */       String cpuUtil = "-1";
/*  348 */       String phymemUtil = "-1";
/*  349 */       String diskUtil = "-1";
/*  350 */       InetAddress address = null;
/*  351 */       String host = getHostName(resourceType, resourceId);
/*  352 */       AMLog.debug("RESTAPI:Hostname:" + host);
/*  353 */       hostName = host != null ? host : "";
/*  354 */       if ((hostName != null) && (!hostName.equals("")))
/*      */       {
/*  356 */         TopoAPI tapi = (TopoAPI)com.adventnet.nms.util.NmsUtil.getAPI("TopoAPI");
/*  357 */         checkalreadyexists = tapi.getNodeNameByIP(hostName);
/*  358 */         if (checkalreadyexists != null)
/*      */         {
/*  360 */           address = InetAddress.getByName(hostName);
/*  361 */           hostOs = getHostOs(hostName);
/*  362 */           hostId = Constants.getResourceID(checkalreadyexists);
/*      */           
/*  364 */           props.setProperty("HOSTOS", hostOs);
/*  365 */           props.setProperty("HOSTIP", address.toString());
/*  366 */           props.setProperty("HOSTID", hostId);
/*      */         }
/*  368 */         Properties sysProps = getSystemDetails(hostId);
/*  369 */         AMLog.info("RESTAPI : System Properties:" + sysProps);
/*  370 */         cpuUtil = sysProps.getProperty("CPUUTIL");
/*  371 */         phymemUtil = sysProps.getProperty("PHYMEMUTIL");
/*  372 */         diskUtil = sysProps.getProperty("DISKUTIL");
/*      */       }
/*  374 */       props.setProperty("CPUUTIL", cpuUtil);
/*  375 */       props.setProperty("PHYMEMUTIL", phymemUtil);
/*  376 */       props.setProperty("DISKUTIL", diskUtil);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  381 */       e.printStackTrace();
/*      */     }
/*  383 */     return props;
/*      */   }
/*      */   
/*      */   public static Properties getSystemDetails(String resid)
/*      */   {
/*  388 */     String cpu = "-1";
/*  389 */     String mem = "-1";
/*  390 */     String disk = "-1";
/*  391 */     ResultSet rs = null;
/*  392 */     Properties sysProps = new Properties();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  397 */       String qry = "select coalesce(CPUUTIL,-1) as CPUUTIL, coalesce(PHYMEMUTIL,-1) as PHYMEMUTIL,coalesce(DISKUTIL,-1) as DISKUTIL  from HostCpuMemDataCollected, HostDiskUtilDataCollected where HostCpuMemDataCollected.collectiontime = HostDiskUtilDataCollected.collectiontime and HostCpuMemDataCollected.resourceid = HostDiskUtilDataCollected.resourceid and HostDiskUtilDataCollected.resourceid= " + resid + " order by HostCpuMemDataCollected.collectiontime desc";
/*  398 */       qry = DBQueryUtil.getTopNValues(qry, 1);
/*  399 */       AMLog.info("RESTAPI: Disk performance Qry: " + qry);
/*  400 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  401 */       if (rs.next())
/*      */       {
/*  403 */         cpu = rs.getString("CPUUTIL");
/*  404 */         mem = rs.getString("PHYMEMUTIL");
/*  405 */         disk = rs.getString("DISKUTIL");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  410 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  414 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  416 */     sysProps.setProperty("CPUUTIL", cpu);
/*  417 */     sysProps.setProperty("PHYMEMUTIL", mem);
/*  418 */     sysProps.setProperty("DISKUTIL", disk);
/*  419 */     return sysProps;
/*      */   }
/*      */   
/*      */   public static String getHostOs(String hostIp) throws SQLException
/*      */   {
/*  424 */     ResultSet rs = null;
/*  425 */     String hostOs = "";
/*  426 */     String qry = "select RESOURCETYPE from CollectData where TARGETADDRESS='" + hostIp + "' and COMPONENTNAME='HOST'";
/*      */     try
/*      */     {
/*  429 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  430 */       if (rs.next())
/*      */       {
/*  432 */         hostOs = rs.getString("RESOURCETYPE");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  437 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  441 */       rs.close();
/*      */     }
/*  443 */     return hostOs;
/*      */   }
/*      */   
/*      */   public static String getHostName(String resourceType, String resourceid)
/*      */   {
/*  448 */     String column = null;
/*  449 */     ResultSet rs = null;
/*  450 */     String hostName = null;
/*  451 */     String typeId = null;
/*      */     
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/*  457 */         typeId = Constants.getTypeId(resourceType);
/*  458 */         if (typeId != null)
/*      */         {
/*  460 */           column = ConfMonitorConfiguration.getInstance().getColumnNameUsedForHost(resourceType, true);
/*  461 */           if ((column != null) && (!column.trim().equals(""))) {
/*  462 */             String qry = "select " + column + " as host from AM_ARGS_" + typeId + " where RESOURCEID=" + resourceid;
/*  463 */             rs = AMConnectionPool.executeQueryStmt(qry);
/*  464 */             if (rs.next())
/*      */             {
/*  466 */               hostName = rs.getString("host").trim();
/*  467 */               return hostName;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  474 */         e.printStackTrace();
/*  475 */         AMLog.info("REST API: error in getting hostname from cof monitor types..");
/*      */       }
/*  477 */       rs = AMConnectionPool.executeQueryStmt("Select TargetAddress from InetService left outer join AM_ManagedObject on ResourceName = Name where Type='" + resourceType + "' and ResourceID=" + resourceid);
/*  478 */       if (rs.next())
/*      */       {
/*  480 */         hostName = rs.getString("TargetAddress");
/*  481 */         return hostName;
/*      */       }
/*      */       
/*      */ 
/*  485 */       rs = AMConnectionPool.executeQueryStmt("Select AM_ManagedObject.ResourceName from CollectData left outer join AM_ManagedObject on AM_ManagedObject.ResourceName = CollectData.ResourceName where COMPONENTNAME='HOST' and Type='" + resourceType + "' and ResourceID=" + resourceid);
/*  486 */       if (rs.next())
/*      */       {
/*  488 */         hostName = rs.getString("ResourceName");
/*  489 */         return hostName;
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/*  495 */         if ((hostName != null) && (!hostName.equals("")))
/*      */         {
/*  497 */           if (hostName.equals("localhost"))
/*      */           {
/*  499 */             hostName = AMServerStartup.ipaddress;
/*      */           }
/*      */           
/*  502 */           hostName = InetAddress.getByName(hostName).getHostAddress();
/*  503 */           if (InetAddress.getByName(hostName).isLoopbackAddress())
/*      */           {
/*  505 */             hostName = AMServerStartup.ipaddress;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  511 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/*  516 */       String error = e.getMessage();
/*  517 */       if (error.indexOf("Invalid column name") != -1)
/*      */       {
/*  519 */         AMLog.debug("Invalid column name " + column + " in AM_ARGS_" + typeId + " table");
/*      */       }
/*      */       else
/*      */       {
/*  523 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  528 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  532 */       if (rs != null)
/*      */       {
/*  534 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  537 */     return hostName;
/*      */   }
/*      */   
/*  540 */   private static ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   public static String getDowntimeSchedulesDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  543 */     String outputString = "";
/*  544 */     String uri = request.getRequestURI();
/*  545 */     Vector<String> ids_owned = new Vector();
/*  546 */     Vector<String> taskIds = new Vector();
/*  547 */     ArrayList<Properties> list = new ArrayList();
/*  548 */     boolean isPriviledgedUser = false;
/*  549 */     int userId = Integer.parseInt(getUserIdForAPIKey(request.getParameter("apikey")));
/*  550 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  551 */     if (isJsonFormat) {
/*  552 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/*  555 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  560 */       ArrayList<Hashtable<String, String>> scheduleTaskList = new ArrayList();
/*  561 */       if (Constants.isPrivilegedUser(request))
/*      */       {
/*  563 */         DownTimeSchedulerAction ds = new DownTimeSchedulerAction();
/*  564 */         isPriviledgedUser = true;
/*  565 */         ids_owned = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(request, 5);
/*  566 */         taskIds = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(userId, 5);
/*  567 */         ArrayList<String> tasks = ds.getSchedulesForResourcesOwnedByUser(request);
/*  568 */         taskIds.addAll(tasks);
/*  569 */         list = DataCollectionControllerUtil.getDowntimeList("", taskIds);
/*      */       }
/*      */       else
/*      */       {
/*  573 */         list = DataCollectionControllerUtil.getDowntimeList("");
/*      */       }
/*  575 */       if (list.size() == 0)
/*      */       {
/*  577 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.maintenance.nodowntimescheduled"), "4000", true);
/*      */       }
/*      */       else
/*      */       {
/*  581 */         String prevTaskId = "";
/*  582 */         for (int i = 0; i < list.size(); i++)
/*      */         {
/*  584 */           Properties p = (Properties)list.get(i);
/*  585 */           String taskid = p.getProperty("TASKID");
/*  586 */           Hashtable<String, String> scheduleDetails = new Hashtable();
/*  587 */           if (!prevTaskId.equals(taskid))
/*      */           {
/*  589 */             scheduleDetails.put("TASKNAME", p.getProperty("TASKNAME"));
/*  590 */             scheduleDetails.put("TASKID", p.getProperty("TASKID"));
/*  591 */             String maintenanceString = "";
/*  592 */             String maintenanceStatus = DataCollectionControllerUtil.underMaintenanceTaskCheck(taskid, 0, 0L);
/*  593 */             if (maintenanceStatus.equals("1"))
/*      */             {
/*  595 */               maintenanceString = FormatUtil.getString("am.webclient.downtimescheduler.idle.txt");
/*      */             }
/*  597 */             else if (maintenanceStatus.equals("2"))
/*      */             {
/*  599 */               maintenanceString = FormatUtil.getString("am.webclient.downtimescheduler.nomonitor.txt");
/*      */             }
/*  601 */             else if (maintenanceStatus.equals("3"))
/*      */             {
/*  603 */               maintenanceString = FormatUtil.getString("am.webclient.downtimescheduler.expired.txt");
/*      */             }
/*  605 */             else if (maintenanceStatus.equals("4"))
/*      */             {
/*  607 */               maintenanceString = FormatUtil.getString("am.webclient.downtimescheduler.running.txt");
/*      */             }
/*  609 */             if (p.getProperty("STATUS").equals("Disabled"))
/*      */             {
/*  611 */               maintenanceString = FormatUtil.getString("am.webclient.downtimescheduler.idle.txt");
/*      */             }
/*  613 */             scheduleDetails.put("STATUS", maintenanceString);
/*  614 */             scheduleDetails.put("OCCURENCE", p.getProperty("TYPE"));
/*      */           }
/*  616 */           scheduleDetails.put("STARTTIME", p.getProperty("STARTTIME"));
/*  617 */           scheduleDetails.put("ENDTIME", p.getProperty("ENDTIME"));
/*  618 */           if (!prevTaskId.equals(taskid))
/*      */           {
/*  620 */             prevTaskId = taskid;
/*  621 */             scheduleTaskList.add(scheduleDetails);
/*      */           }
/*      */         }
/*      */       }
/*  625 */       HashMap<String, Object> results = new HashMap();
/*  626 */       results.put("response-code", "4000");
/*  627 */       results.put("uri", uri);
/*  628 */       results.put("result", scheduleTaskList);
/*  629 */       results.put("sortingParam", "TASKNAME");
/*  630 */       results.put("parentNode", "Schedules");
/*  631 */       results.put("nodeName", "Schedule");
/*  632 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  636 */       ex.printStackTrace();
/*  637 */       AMLog.debug("REST API : getDowntimeSchedulesDetail: Server error");
/*  638 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/*      */     
/*  641 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getRecorderCompatibility(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  646 */     String version = null;
/*  647 */     String rec_ver = request.getParameter("version");
/*  648 */     String uri = request.getRequestURI();
/*  649 */     JSONObject result = new JSONObject();
/*  650 */     version = DBUtil.getGlobalConfigValue("RBMRecorderVersion");
/*      */     
/*  652 */     if (version.equalsIgnoreCase(rec_ver))
/*      */     {
/*  654 */       result.put("eC", "0");
/*      */     }
/*      */     else
/*      */     {
/*  658 */       result.put("eC", "1000");
/*      */     }
/*  660 */     return result.toString();
/*      */   }
/*      */   
/*      */   public static String getUsergroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  664 */     String uri = request.getRequestURI();
/*  665 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  666 */     if (isJsonFormat) {
/*  667 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/*  669 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*  671 */     String outputString = "";
/*  672 */     int usergroupId = -1;
/*  673 */     String[] nodes = uri.split("/");
/*  674 */     String usergroup = null;
/*  675 */     AMLog.debug("api_nodes: usergroup" + nodes);
/*  676 */     if (nodes.length > 4) {
/*  677 */       usergroup = nodes[4];
/*      */     }
/*  679 */     if (usergroup != null) {
/*  680 */       Pattern p = Pattern.compile("[0-9]*");
/*  681 */       Matcher m = p.matcher(usergroup);
/*  682 */       boolean isId = m.matches();
/*  683 */       if (isId) {
/*  684 */         usergroupId = Integer.parseInt(usergroup);
/*      */       } else {
/*  686 */         usergroup = URLDecoder.decode(usergroup, "UTF-8");
/*  687 */         ArrayList<String> groupIdList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME ='" + usergroup + "'");
/*  688 */         if (groupIdList.size() == 0) {
/*  689 */           AMLog.debug("REST API: The usergroup parameter in the request url is wrong ");
/*  690 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongusergroupparameter.message"), "4615");
/*  691 */           return outputString;
/*      */         }
/*      */         
/*  694 */         usergroupId = Integer.parseInt((String)groupIdList.get(0));
/*  695 */         AMLog.debug("api is id false  n usergroup id:" + usergroupId);
/*      */       }
/*      */     } else {
/*  698 */       AMLog.debug("REST API: The usergroup id or name in the request url is wrong ");
/*  699 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.details.wrongnamemessage"), "4612");
/*  700 */       return outputString;
/*      */     }
/*      */     
/*  703 */     ArrayList<Hashtable> userGroupList = UserConfigurationUtil.getUsergroupDetails(usergroupId);
/*  704 */     if (userGroupList.size() == 0) {
/*  705 */       Hashtable msg = new Hashtable();
/*  706 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nousergroupdetails.message"));
/*  707 */       userGroupList.add(msg);
/*      */     }
/*  709 */     HashMap results = new HashMap();
/*  710 */     results.put("response-code", "4000");
/*  711 */     results.put("uri", uri);
/*  712 */     results.put("result", userGroupList);
/*  713 */     results.put("sortingParam", "GroupName");
/*  714 */     results.put("parentNode", "Usergroups");
/*  715 */     results.put("nodeName", "Usergroup");
/*  716 */     results.put("subNodeName", "AssociatedMonitorGroup,AssociatedUsers");
/*  717 */     outputString = getOutputAsString(results, isJsonFormat);
/*  718 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String createUsergroup(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  723 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/*  724 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/*  725 */     if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/*  726 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege.usergroup"), "4008");
/*      */     }
/*  728 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/*  730 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/*  732 */     String uri = request.getRequestURI();
/*  733 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/*  734 */     if (isJsonFormat) {
/*  735 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/*  737 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*  739 */     String outputString = "";
/*  740 */     String groupName = request.getParameter("usergroupName");
/*      */     
/*  742 */     if (groupName == null) {
/*  743 */       AMLog.debug("REST API : usergroupName should be given in the url");
/*  744 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.useradministration.usergroup.api.name.empty.text", new String[] { groupName }), "4604");
/*  745 */       return outputString;
/*      */     }
/*      */     
/*  748 */     if ((groupName != null) && (!groupName.trim().equals("")) && (UserConfigurationUtil.checkDuplicateEntry("AM_USERGROUP_CONFIG", "GROUPNAME", groupName, ""))) {
/*  749 */       AMLog.debug("REST API : usergroup name exist");
/*  750 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.useradministration.usergroup.duplicate.message.text", new String[] { groupName }), "4604");
/*  751 */       return outputString;
/*      */     }
/*      */     
/*  754 */     ArrayList<String> selectedGroups = new ArrayList();
/*  755 */     ArrayList<String> selectedUser = new ArrayList();
/*  756 */     if ((request.getParameter("groupId") != null) && (!request.getParameter("groupId").equals(""))) {
/*  757 */       String[] groupId = request.getParameter("groupId").split(",");
/*  758 */       if (isAPICallFromAAM) {
/*  759 */         selectedGroups = new ArrayList(Arrays.asList(groupId));
/*  760 */         getRelevantResourceIds(selectedGroups);
/*      */       } else {
/*  762 */         selectedGroups = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("groupId") + ")");
/*  763 */         if ((!isAPICallFromAAM) && (groupId.length != selectedGroups.size())) {
/*  764 */           AMLog.debug("REST API: The groupid in the request url is wrong or the values are repeated.");
/*  765 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupid.message"), "4605");
/*  766 */           return outputString;
/*      */         }
/*      */       }
/*  769 */     } else if ((request.getParameter("groupName") != null) && (!request.getParameter("groupName").equals(""))) {
/*  770 */       selectedGroups = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("groupName") + "'");
/*  771 */       if (selectedGroups.size() == 0) {
/*  772 */         AMLog.debug("REST API: The groupName in the request url is wrong ");
/*  773 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupname.message"), "4606");
/*  774 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/*  778 */     if ((request.getParameter("userId") != null) && (!request.getParameter("userId").equals(""))) {
/*  779 */       String[] userid = request.getParameter("userId").split(",");
/*  780 */       selectedUser = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERID in (" + request.getParameter("userId") + ") and USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/*  781 */       if (userid.length != selectedUser.size()) {
/*  782 */         AMLog.debug("REST API: The userid in the request url is wrong or the values are repeated.");
/*  783 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.wronguseridmessage"), "4605");
/*  784 */         return outputString;
/*      */       }
/*      */     }
/*  787 */     else if ((request.getParameter("userName") != null) && (!request.getParameter("userName").equals(""))) {
/*  788 */       selectedUser = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME NOT IN ('reportadmin','systemadmin_enterprise') and USERNAME='" + request.getParameter("userName") + "'");
/*  789 */       if (selectedUser.size() == 0) {
/*  790 */         AMLog.debug("REST API: The userName in the request url is wrong ");
/*  791 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.wrongusernamemessage"), "4606");
/*  792 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  797 */     ArrayList domainDetails = new ArrayList();
/*  798 */     ArrayList domainGroupList = new ArrayList();
/*  799 */     boolean validateDomainGroup = false;
/*  800 */     boolean createDomainGroup = false;
/*  801 */     int domainid = -1;
/*  802 */     String forceadd = request.getParameter("forceadd");
/*  803 */     if ("true".equalsIgnoreCase(forceadd)) {
/*  804 */       validateDomainGroup = false;
/*  805 */       if (request.getParameter("domainid") != null) {
/*  806 */         createDomainGroup = true;
/*      */       }
/*      */     }
/*  809 */     else if (request.getParameter("domainname") != null) {
/*  810 */       String domainName = request.getParameter("domainname");
/*  811 */       domainid = ADAuthenticationUtil.getDomainID(domainName);
/*  812 */       if (domainid != 0) {
/*  813 */         domainDetails = ADAuthenticationUtil.getDomainDetails(String.valueOf(domainid), true);
/*  814 */         if (domainDetails.size() > 0) {
/*  815 */           validateDomainGroup = true;
/*      */         }
/*      */       }
/*      */     }
/*  819 */     else if (request.getParameter("domainid") != null) {
/*  820 */       String domainID = request.getParameter("domainid");
/*      */       
/*  822 */       domainDetails = ADAuthenticationUtil.getDomainDetails(domainID, true);
/*  823 */       if (domainDetails.size() > 0) {
/*  824 */         domainid = Integer.parseInt(domainID);
/*  825 */         validateDomainGroup = true;
/*      */       }
/*      */     }
/*      */     
/*  829 */     if (validateDomainGroup) {
/*  830 */       domainDetails.add(groupName);
/*  831 */       domainDetails.add("usergroup");
/*  832 */       domainGroupList = getDomainUserorGroup(domainDetails);
/*  833 */       if (domainGroupList.size() > 0) {
/*  834 */         for (int i = 0; i < domainGroupList.size(); i++) {
/*  835 */           Hashtable groupDetails = (Hashtable)domainGroupList.get(i);
/*  836 */           String domainUserName = (String)groupDetails.get("sAMAccountName");
/*  837 */           if (domainUserName.equals(groupName)) {
/*  838 */             createDomainGroup = true;
/*  839 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  847 */     int aamUsergroupId = -1;
/*  848 */     if (isAPICallFromAAM) {
/*      */       try {
/*  850 */         aamUsergroupId = Integer.parseInt(request.getParameter("aamUsergroupId"));
/*  851 */         StartUtil.printStr("CommonAPIUtil.createUsergroup():aamUsergroupId::::" + aamUsergroupId);
/*      */       } catch (Exception ex) {
/*  853 */         StartUtil.printStr("CommonAPIUtil.createUsergroup(): Invalid user group id " + request.getParameter("aamUsergroupId") + " from Admin server");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  858 */     int tempGroupid = -1;
/*      */     try {
/*  860 */       if ((EnterpriseUtil.isManagedServer()) && (aamUsergroupId != -1)) {
/*  861 */         tempGroupid = aamUsergroupId;
/*      */       } else {
/*  863 */         tempGroupid = DBQueryUtil.getIncrementedID("GROUPID", "AM_USERGROUP_CONFIG");
/*      */       }
/*  865 */       String query = "insert into AM_USERGROUP_CONFIG (GROUPID,GROUPNAME) values (" + tempGroupid + ",'" + groupName + "')";
/*  866 */       if (createDomainGroup) {
/*  867 */         if ("true".equals(forceadd)) {
/*  868 */           String[] domainidList = request.getParameter("domainid").split(",");
/*  869 */           for (String id : domainidList) {
/*      */             try {
/*  871 */               AMConnectionPool.executeUpdateStmt("insert into AM_DOMAINUSERGROUP_MAPPING (GROUPID,DOMAINID) values (" + tempGroupid + "," + id + ")");
/*      */             } catch (Exception ex) {
/*  873 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/*  878 */           String domainmapping = "insert into AM_DOMAINUSERGROUP_MAPPING values(" + tempGroupid + "," + domainid + ")";
/*  879 */           AMConnectionPool.executeUpdateStmt(domainmapping);
/*      */         }
/*      */       }
/*  882 */       AMConnectionPool.executeUpdateStmt(query);
/*  883 */       Iterator i$; if (selectedGroups.size() > 0) {
/*  884 */         for (i$ = selectedGroups.iterator(); i$.hasNext();) { monitorgroup = (String)i$.next();
/*  885 */           String mappingQuery = "insert into AM_USERGROUP_MAPPING values (" + tempGroupid + "," + monitorgroup + ")";
/*  886 */           AMConnectionPool.executeUpdateStmt(mappingQuery);
/*  887 */           if (selectedUser.size() > 0) {
/*  888 */             for (String userid : selectedUser) {
/*  889 */               String userquery = "insert into AM_HOLISTICAPPLICATION_OWNERS values (" + monitorgroup + "," + userid + ")";
/*  890 */               AMConnectionPool.executeUpdateStmt(userquery);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       String monitorgroup;
/*  897 */       if (selectedUser.size() > 0) {
/*  898 */         for (String userid : selectedUser) {
/*  899 */           String userquery = "insert into AM_USERGROUP_OWNERS values (" + tempGroupid + "," + userid + ")";
/*  900 */           AMConnectionPool.executeUpdateStmt(userquery);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  905 */       if ((EnterpriseUtil.isAdminServer()) && (Constants.isSsoEnabled())) {
/*  906 */         HashMap<String, String> restApiParams = new HashMap();
/*  907 */         restApiParams.put("usergroupName", groupName);
/*  908 */         restApiParams.put("aamUsergroupId", String.valueOf(tempGroupid));
/*  909 */         if ((selectedUser != null) && (selectedUser.size() > 0)) {
/*  910 */           restApiParams.put("userGroupUsers", request.getParameter("userId"));
/*      */         }
/*  912 */         if ((selectedGroups != null) && (selectedGroups.size() > 0)) {
/*  913 */           restApiParams.put("associatedMonitorGroupsId", request.getParameter("groupId"));
/*      */         }
/*  915 */         if (domainid != -1) {
/*  916 */           restApiParams.put("domainID", String.valueOf(domainid));
/*      */         }
/*  918 */         MASSyncUtil.pushUserGroupDetailsToMAS(restApiParams, "add");
/*      */       }
/*      */     } catch (Exception ex) {
/*  921 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  924 */     UserConfigurationUtil.updateUserPrivileges(request, null);
/*      */     
/*      */ 
/*  927 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate)) {
/*      */       try
/*      */       {
/*  930 */         List<String> uList = new ArrayList();
/*      */         
/*  932 */         if (!selectedUser.isEmpty())
/*      */         {
/*  934 */           for (String userid : selectedUser)
/*      */           {
/*  936 */             if (RestrictedUsersViewUtil.isRestrictedRole(userid))
/*      */             {
/*  938 */               uList.add(userid);
/*      */             }
/*      */           }
/*  941 */           if (!uList.isEmpty()) {
/*  942 */             AMLog.debug("[CommonAPIUtil::(createUserGroup)]ruser(s) : " + uList);
/*  943 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(uList);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  949 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  954 */     ArrayList<Hashtable> userGroupList = UserConfigurationUtil.getUsergroupDetails(tempGroupid);
/*      */     
/*  956 */     if ("true".equals(request.getParameter("profilecreationscript"))) {
/*  957 */       if (userGroupList.size() == 0) {
/*  958 */         AMLog.debug("REST API: Problem in adding the user goup");
/*  959 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.add.failure.text"), "4500");
/*  960 */         return outputString;
/*      */       }
/*  962 */       AMLog.debug("REST API: UserGroup added successfully");
/*  963 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.add.success.text"), "4000");
/*  964 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*  968 */     if (userGroupList.size() == 0) {
/*  969 */       Hashtable msg = new Hashtable();
/*  970 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nousergroupdetails.message"));
/*  971 */       userGroupList.add(msg);
/*      */     }
/*  973 */     HashMap results = new HashMap();
/*  974 */     results.put("response-code", "4000");
/*  975 */     results.put("uri", uri);
/*  976 */     results.put("result", userGroupList);
/*  977 */     results.put("sortingParam", "GroupName");
/*  978 */     results.put("parentNode", "Usergroups");
/*  979 */     results.put("nodeName", "Usergroup");
/*  980 */     results.put("subNodeName", "AssociatedMonitorGroup,AssociatedUsers");
/*  981 */     outputString = getOutputAsString(results, isJsonFormat);
/*  982 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String updateUsergroup(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  988 */     String loginUserRole = getOwnerRole(request);
/*  989 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/*  990 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/*  991 */     if ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!loginUserRole.equals("ENTERPRISEADMIN")))) {
/*  992 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege.usergroup"), "4008");
/*      */     }
/*  994 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/*  996 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/*  998 */     String uri = request.getRequestURI();
/*  999 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1000 */     if (isJsonFormat) {
/* 1001 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 1003 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1005 */     String outputString = "";
/*      */     
/* 1007 */     String usergroupId = request.getParameter("usergroupId");
/* 1008 */     String usergroupName = request.getParameter("usergroupName");
/* 1009 */     Pattern p = Pattern.compile("[0-9]*");
/* 1010 */     if ((usergroupId != null) && (!usergroupId.trim().equals(""))) {
/* 1011 */       Matcher m = p.matcher(usergroupId);
/* 1012 */       boolean isId = m.matches();
/* 1013 */       if (!isId) {
/* 1014 */         AMLog.debug("REST API: The usergroupid in the request url is wrong");
/* 1015 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.wronguseridmessage"), "4605");
/* 1016 */         return outputString;
/*      */       }
/* 1018 */       ArrayList<String> idList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPID=" + usergroupId);
/* 1019 */       if (idList.size() == 0) {
/* 1020 */         AMLog.debug("REST API: The usergroupid in the request url is wrong");
/* 1021 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.wronguseridmessage"), "4605");
/* 1022 */         return outputString;
/*      */       }
/*      */     }
/* 1025 */     else if ((usergroupName != null) && (!usergroupName.trim().equals(""))) {
/* 1026 */       ArrayList<String> idList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME='" + usergroupName + "'");
/* 1027 */       if (idList.size() > 0) {
/* 1028 */         usergroupId = (String)idList.get(0);
/*      */       } else {
/* 1030 */         AMLog.debug("REST API: The usergroupid in the request url is wrong");
/* 1031 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.groupname.text"), "4605");
/* 1032 */         return outputString;
/*      */       }
/*      */     }
/*      */     else {
/* 1036 */       AMLog.debug("REST API: The usergroupid in the request url is not provided");
/* 1037 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.usergroupidemptymessage"), "4605");
/* 1038 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/* 1042 */     if (request.getParameter("newusergroupname") != null) {
/* 1043 */       if (UserConfigurationUtil.checkDuplicateEntry("AM_USERGROUP_CONFIG", "GROUPNAME", request.getParameter("newusergroupname"), " where GROUPID != " + usergroupId)) {
/* 1044 */         AMLog.debug("REST API : usergroup name exist");
/* 1045 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.useradministration.usergroup.duplicate.message.text", new String[] { request.getParameter("newusergroupname") }), "4604");
/* 1046 */         return outputString;
/*      */       }
/*      */       try {
/* 1049 */         AMConnectionPool.executeUpdateStmt("update AM_USERGROUP_CONFIG set GROUPNAME='" + request.getParameter("newusergroupname") + "' where GROUPID=" + usergroupId);
/*      */       } catch (Exception ex) {
/* 1051 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1058 */     ArrayList<String> selectedMonitor = new ArrayList();
/* 1059 */     ArrayList<String> selectedUsers = new ArrayList();
/* 1060 */     ArrayList<String> removeMG = new ArrayList();
/* 1061 */     ArrayList<String> removeUsers = new ArrayList();
/* 1062 */     ArrayList<String> domainList = new ArrayList();
/*      */     
/* 1064 */     Vector<String> allHaidsOwners = new Vector();
/*      */     
/*      */ 
/* 1067 */     ArrayList<String> alreadyPresentHaid = new ArrayList();
/* 1068 */     if ((request.getParameter("associateGroupId") != null) && (!request.getParameter("associateGroupId").equals(""))) {
/* 1069 */       String[] groupId = request.getParameter("associateGroupId").split(",");
/* 1070 */       selectedMonitor = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("associateGroupId") + ")");
/* 1071 */       if (groupId.length != selectedMonitor.size()) {
/* 1072 */         AMLog.debug("REST API: The associateGroupId in the request url is wrong or the values are repeated.");
/* 1073 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupid.message"), "4605");
/* 1074 */         return outputString;
/*      */       }
/* 1076 */     } else if ((request.getParameter("associateGroupName") != null) && (!request.getParameter("associateGroupName").equals(""))) {
/* 1077 */       selectedMonitor = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("associateGroupName") + "'");
/* 1078 */       if (selectedMonitor.size() == 0) {
/* 1079 */         AMLog.debug("REST API: The associateGroupName in the request url is wrong ");
/* 1080 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupname.message"), "4606");
/* 1081 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 1085 */     if ((request.getParameter("associateUserId") != null) && (!request.getParameter("associateUserId").equals(""))) {
/* 1086 */       String[] userid = request.getParameter("associateUserId").split(",");
/* 1087 */       selectedUsers = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERID in (" + request.getParameter("associateUserId") + ") and USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/* 1088 */       if (userid.length != selectedUsers.size()) {
/* 1089 */         AMLog.debug("REST API: The associateuserid in the request url is wrong or the values are repeated.");
/* 1090 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.useridwrongmessage"), "4605");
/* 1091 */         return outputString;
/*      */       }
/*      */     }
/* 1094 */     else if ((request.getParameter("associateUserName") != null) && (!request.getParameter("associateUserName").equals(""))) {
/* 1095 */       selectedUsers = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME NOT IN ('reportadmin','systemadmin_enterprise') and USERNAME='" + request.getParameter("associateUserName") + "'");
/* 1096 */       if (selectedUsers.size() == 0) {
/* 1097 */         AMLog.debug("REST API: The associateUserName in the request url is wrong ");
/* 1098 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.usernamewrongmessage"), "4606");
/* 1099 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1104 */     if ((request.getParameter("removeGroupId") != null) && (!request.getParameter("removeGroupId").equals(""))) {
/* 1105 */       String[] removeGroupId = request.getParameter("removeGroupId").split(",");
/* 1106 */       removeMG = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("removeGroupId") + ")");
/* 1107 */       if (removeGroupId.length != removeMG.size()) {
/* 1108 */         AMLog.debug("REST API: The removeGroupId in the request url is wrong or the values are repeated.");
/* 1109 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongremovegroupid.message"), "4611");
/* 1110 */         return outputString;
/*      */       }
/* 1112 */     } else if ((request.getParameter("removeGroupName") != null) && (!request.getParameter("removeGroupName").equals(""))) {
/* 1113 */       removeMG = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("removeGroupName") + "'");
/* 1114 */       if (removeMG.size() == 0) {
/* 1115 */         AMLog.debug("REST API: The removeGroupName in the request url is wrong ");
/* 1116 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongremovegroupName.message"), "4612");
/* 1117 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 1121 */     if ((request.getParameter("removeUserId") != null) && (!request.getParameter("removeUserId").equals(""))) {
/* 1122 */       String[] userid = request.getParameter("removeUserId").split(",");
/* 1123 */       removeUsers = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERID in (" + request.getParameter("removeUserId") + ") and USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/* 1124 */       if (userid.length != removeUsers.size()) {
/* 1125 */         AMLog.debug("REST API: The removeuserid in the request url is wrong or the values are repeated.");
/* 1126 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.removeuseridwrongmessage"), "4605");
/* 1127 */         return outputString;
/*      */       }
/*      */     }
/* 1130 */     else if ((request.getParameter("removeUserName") != null) && (!request.getParameter("removeUserName").equals(""))) {
/* 1131 */       removeUsers = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME NOT IN ('reportadmin','systemadmin_enterprise') and USERNAME='" + request.getParameter("removeUserName") + "'");
/* 1132 */       if (removeUsers.size() == 0) {
/* 1133 */         AMLog.debug("REST API: The associateUserName in the request url is wrong ");
/* 1134 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.removeusernamewrongmessage"), "4606");
/* 1135 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 1139 */     if ((request.getParameter("domainusergroup") != null) && ("true".equals(request.getParameter("domainusergroup"))))
/*      */     {
/* 1141 */       if (request.getParameter("usergrouprole") != null) {
/* 1142 */         String usergrouprole = request.getParameter("usergrouprole");
/* 1143 */         if (("admin".equalsIgnoreCase(usergrouprole)) || ("deladmin".equalsIgnoreCase(usergrouprole)) || ("operator".equalsIgnoreCase(usergrouprole))) {
/* 1144 */           AMConnectionPool.executeUpdateStmt("update AM_USERGROUP_CONFIG set USERLOGINROLE = '" + usergrouprole + "' where GROUPID=" + usergroupId);
/*      */         }
/*      */       }
/*      */       
/* 1148 */       domainList = mo.getRowsForSingleColumn("select ID from AM_DOMAINCONTROLLERS where ID in(" + request.getParameter("usergroupdomains") + ")");
/* 1149 */       if (domainList.size() == 0) {
/* 1150 */         AMLog.debug("REST API: The Domain ID in the request url is wrong ");
/* 1151 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.domain.delete.idnotexist"), "4606");
/* 1152 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 1156 */     if (isAPICallFromAAM) {
/* 1157 */       ResultSet rs = null;
/*      */       try {
/* 1159 */         String query = "select USERID from AM_USERGROUP_OWNERS where GROUPID=" + usergroupId;
/* 1160 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1161 */         while (rs.next()) {
/* 1162 */           removeUsers.add(rs.getString(1));
/*      */         }
/* 1164 */         if ((removeUsers.size() > 0) && (selectedUsers.size() > 0)) {
/* 1165 */           removeUsers.removeAll(selectedUsers);
/*      */         }
/*      */       } catch (Exception ex) {
/* 1168 */         ex.printStackTrace();
/*      */       } finally {
/* 1170 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1174 */     ArrayList<String> alreadyAddedUsers = mo.getRowsForSingleColumn("select USERID from AM_USERGROUP_OWNERS where GROUPID=" + usergroupId);
/* 1175 */     alreadyPresentHaid = mo.getRowsForSingleColumn("select HAID from AM_USERGROUP_MAPPING where GROUPID=" + usergroupId);
/* 1176 */     Iterator i$; if (selectedMonitor.size() > 0)
/*      */     {
/* 1178 */       for (i$ = selectedMonitor.iterator(); i$.hasNext();) { haid = (String)i$.next();
/* 1179 */         if (!alreadyPresentHaid.contains(haid)) {
/*      */           try {
/* 1181 */             AMConnectionPool.executeUpdateStmt("insert into AM_USERGROUP_MAPPING values (" + usergroupId + "," + haid + ")");
/*      */           } catch (Exception ex) {
/* 1183 */             ex.printStackTrace();
/*      */           }
/*      */           
/* 1186 */           addedUsers = mo.getRowsForSingleColumn("select OWNERID from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid);
/* 1187 */           if (alreadyAddedUsers.size() > 0)
/*      */           {
/* 1189 */             for (String userid : alreadyAddedUsers) {
/* 1190 */               if (!addedUsers.contains(userid)) {
/*      */                 try {
/* 1192 */                   AMConnectionPool.executeUpdateStmt("insert into AM_HOLISTICAPPLICATION_OWNERS values (" + haid + "," + userid + ")");
/*      */                 } catch (Exception ex) {
/* 1194 */                   ex.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 1199 */           if (selectedUsers.size() > 0) {
/* 1200 */             for (String userid : selectedUsers) {
/* 1201 */               if ((!addedUsers.contains(userid)) && (!alreadyAddedUsers.contains(userid))) {
/*      */                 try {
/* 1203 */                   AMConnectionPool.executeUpdateStmt("insert into AM_HOLISTICAPPLICATION_OWNERS values (" + haid + "," + userid + ")");
/*      */                 } catch (Exception ex) {
/* 1205 */                   ex.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     String haid;
/*      */     ArrayList<String> addedUsers;
/*      */     Iterator i$;
/* 1216 */     if (selectedUsers.size() > 0) {
/* 1217 */       for (String userid : selectedUsers) {
/* 1218 */         if (!alreadyAddedUsers.contains(userid)) {
/*      */           try {
/* 1220 */             AMConnectionPool.executeUpdateStmt("insert into AM_USERGROUP_OWNERS values (" + usergroupId + "," + userid + ")");
/*      */           } catch (Exception ex) {
/* 1222 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1227 */       for (i$ = alreadyPresentHaid.iterator(); i$.hasNext();) { haid = (String)i$.next();
/* 1228 */         addedUsers = mo.getRowsForSingleColumn("select OWNERID from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid);
/* 1229 */         for (String userid : selectedUsers) {
/* 1230 */           if (!addedUsers.contains(userid)) {
/*      */             try {
/* 1232 */               AMConnectionPool.executeUpdateStmt("insert into AM_HOLISTICAPPLICATION_OWNERS values (" + haid + "," + userid + ")");
/*      */             } catch (Exception ex) {
/* 1234 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     String haid;
/*      */     ArrayList<String> addedUsers;
/* 1242 */     if (removeUsers.size() > 0) {
/* 1243 */       if (DBQueryUtil.isPgsql()) {
/* 1244 */         AMConnectionPool.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS using AM_USERGROUP_OWNERS,AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and  AM_USERGROUP_OWNERS.GROUPID=AM_USERGROUP_MAPPING.GROUPID and " + ManagedApplication.getCondition("AM_USERGROUP_OWNERS.USERID", removeUsers) + " and AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and  AM_USERGROUP_OWNERS.GROUPID=" + usergroupId);
/*      */       } else {
/* 1246 */         AMConnectionPool.executeUpdateStmt("delete AM_HOLISTICAPPLICATION_OWNERS from AM_HOLISTICAPPLICATION_OWNERS, AM_USERGROUP_OWNERS,AM_USERGROUP_MAPPING where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and  AM_USERGROUP_OWNERS.GROUPID=AM_USERGROUP_MAPPING.GROUPID and " + ManagedApplication.getCondition("AM_USERGROUP_OWNERS.USERID", removeUsers) + " and  AM_USERGROUP_MAPPING.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and  AM_USERGROUP_OWNERS.GROUPID=" + usergroupId);
/*      */       }
/*      */       
/* 1249 */       AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_OWNERS where " + ManagedApplication.getCondition("USERID", removeUsers) + " and GROUPID=" + usergroupId);
/*      */     }
/*      */     
/*      */ 
/* 1253 */     if (removeMG.size() > 0) {
/*      */       try {
/* 1255 */         ArrayList<String> addedOwners = mo.getRowsForSingleColumn("select USERID from AM_USERGROUP_OWNERS where GROUPID=" + usergroupId);
/* 1256 */         if (DBQueryUtil.isPgsql()) {
/* 1257 */           AMConnectionPool.executeUpdateStmt("delete from AM_HOLISTICAPPLICATION_OWNERS using AM_USERGROUP_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and " + ManagedApplication.getCondition("AM_USERGROUP_OWNERS.USERID", addedOwners) + " and " + ManagedApplication.getCondition("AM_HOLISTICAPPLICATION_OWNERS.HAID", removeMG) + " and  AM_USERGROUP_OWNERS.GROUPID=" + usergroupId);
/*      */         } else {
/* 1259 */           AMConnectionPool.executeUpdateStmt("delete AM_HOLISTICAPPLICATION_OWNERS from AM_HOLISTICAPPLICATION_OWNERS, AM_USERGROUP_OWNERS where AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_USERGROUP_OWNERS.USERID and " + ManagedApplication.getCondition("AM_USERGROUP_OWNERS.USERID", addedOwners) + " and " + ManagedApplication.getCondition("AM_HOLISTICAPPLICATION_OWNERS.HAID", removeMG) + " and  AM_USERGROUP_OWNERS.GROUPID=" + usergroupId);
/*      */         }
/* 1261 */         AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_MAPPING where GROUPID=" + usergroupId + " and " + ManagedApplication.getCondition("HAID", removeMG));
/*      */       } catch (Exception ex) {
/* 1263 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1267 */     if (domainList.size() > 0) {
/*      */       try {
/* 1269 */         AMConnectionPool.executeUpdateStmt("delete from AM_DOMAINUSERGROUP_MAPPING where GROUPID=" + usergroupId);
/* 1270 */         for (String id : domainList) {
/*      */           try {
/* 1272 */             AMConnectionPool.executeUpdateStmt("insert into AM_DOMAINUSERGROUP_MAPPING (GROUPID,DOMAINID) values (" + usergroupId + "," + id + ")");
/*      */           } catch (Exception ex) {
/* 1274 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/* 1278 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1281 */     UserConfigurationUtil.updateUserPrivileges(request, null);
/* 1282 */     ArrayList<Hashtable> userGroupList = UserConfigurationUtil.getUsergroupDetails(Integer.valueOf(usergroupId).intValue());
/*      */     
/*      */ 
/* 1285 */     if ((EnterpriseUtil.isAdminServer()) && (Constants.isSsoEnabled())) {
/* 1286 */       Object restApiParams = new HashMap();
/* 1287 */       if ((usergroupName != null) && (usergroupName.trim().length() > 0)) {
/* 1288 */         ((HashMap)restApiParams).put("usergroupName", usergroupName);
/*      */       }
/* 1290 */       if ((usergroupId != null) && (usergroupId.trim().length() > 0)) {
/* 1291 */         ((HashMap)restApiParams).put("userGroupId", usergroupId);
/*      */       }
/* 1293 */       String newUserGroupName = request.getParameter("newusergroupname");
/* 1294 */       if ((newUserGroupName != null) && (newUserGroupName.trim().length() > 0)) {
/* 1295 */         ((HashMap)restApiParams).put("usergroupName", newUserGroupName);
/*      */       }
/* 1297 */       if ((selectedUsers != null) && (selectedUsers.size() > 0)) {
/* 1298 */         ((HashMap)restApiParams).put("userGroupUsers", request.getParameter("associateUserId"));
/*      */       }
/* 1300 */       if ((selectedMonitor != null) && (selectedMonitor.size() > 0)) {
/* 1301 */         ((HashMap)restApiParams).put("associatedMonitorGroupsId", request.getParameter("associateGroupId"));
/*      */       }
/* 1303 */       if ((domainList != null) && (domainList.size() > 0)) {
/* 1304 */         ((HashMap)restApiParams).put("domainusergroup", "true");
/* 1305 */         ((HashMap)restApiParams).put("usergroupdomains", request.getParameter("usergroupdomains"));
/* 1306 */         ((HashMap)restApiParams).put("usergrouprole", request.getParameter("usergrouprole"));
/*      */       }
/* 1308 */       MASSyncUtil.pushUserGroupDetailsToMAS((HashMap)restApiParams, "update");
/*      */     }
/*      */     
/*      */ 
/* 1312 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*      */ 
/*      */ 
/* 1319 */         if (!removeUsers.isEmpty())
/*      */         {
/* 1321 */           allHaidsOwners.addAll(removeUsers);
/*      */         }
/* 1323 */         if (!selectedUsers.isEmpty())
/*      */         {
/* 1325 */           allHaidsOwners.addAll(selectedUsers);
/*      */         }
/* 1327 */         if (!allHaidsOwners.isEmpty())
/*      */         {
/* 1329 */           Object uList = new ArrayList();
/* 1330 */           for (String ownerId : allHaidsOwners)
/*      */           {
/* 1332 */             if ((!((List)uList).contains(ownerId)) && (RestrictedUsersViewUtil.isRestrictedRole(ownerId)))
/*      */             {
/* 1334 */               ((List)uList).add(ownerId);
/*      */             }
/*      */           }
/* 1337 */           if (!((List)uList).isEmpty())
/*      */           {
/* 1339 */             AMLog.debug("[CommonAPIUtil::(updateUserGroup)]ruser(s) : " + uList);
/* 1340 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll((Collection)uList);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1347 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1353 */     if ("true".equals(request.getParameter("profilecreationscript"))) {
/* 1354 */       if (userGroupList.size() == 0) {
/* 1355 */         AMLog.debug("REST API: Problem in adding the user goup");
/* 1356 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.failure.text"), "4500");
/* 1357 */         return outputString;
/*      */       }
/* 1359 */       AMLog.debug("REST API: UserGroup added successfully");
/* 1360 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.update.success.text"), "4000");
/* 1361 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/* 1365 */     if (userGroupList.size() == 0) {
/* 1366 */       Hashtable msg = new Hashtable();
/* 1367 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nousergroupdetails.message"));
/* 1368 */       userGroupList.add(msg);
/*      */     }
/*      */     
/* 1371 */     HashMap results = new HashMap();
/* 1372 */     results.put("response-code", "4000");
/* 1373 */     results.put("uri", uri);
/* 1374 */     results.put("result", userGroupList);
/* 1375 */     results.put("sortingParam", "GroupName");
/* 1376 */     results.put("parentNode", "Usergroups");
/* 1377 */     results.put("nodeName", "Usergroup");
/* 1378 */     results.put("subNodeName", "AssociatedMonitorGroup,AssociatedUsers");
/* 1379 */     outputString = getOutputAsString(results, isJsonFormat);
/* 1380 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteUsergroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1384 */     String loginUserRole = getOwnerRole(request);
/* 1385 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/* 1386 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 1387 */     if ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!loginUserRole.equals("ENTERPRISEADMIN")))) {
/* 1388 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege.usergroup"), "4008");
/*      */     }
/* 1390 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/* 1392 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/*      */     
/* 1395 */     String uri = request.getRequestURI();
/* 1396 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1397 */     if (isJsonFormat) {
/* 1398 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 1400 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1402 */     String groupName = request.getParameter("usergroupName");
/*      */     
/* 1404 */     String outputString = "";
/* 1405 */     ArrayList<String> groupidList = new ArrayList();
/* 1406 */     if ((request.getParameter("usergroupId") != null) && (!request.getParameter("usergroupId").equals(""))) {
/* 1407 */       String usergroupid = request.getParameter("usergroupId");
/* 1408 */       if ((isAPICallFromAAM) && (usergroupid.equals("all"))) {
/* 1409 */         groupidList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG");
/*      */       } else {
/* 1411 */         String[] usergroupidArr = request.getParameter("usergroupId").split(",");
/* 1412 */         groupidList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPID in (" + request.getParameter("usergroupId") + ")");
/* 1413 */         if (usergroupidArr.length != groupidList.size()) {
/* 1414 */           AMLog.debug("REST API: The usergroupid in the request url is wrong or the values are repeated.");
/* 1415 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.delete.wronguseridmessage"), "4605");
/* 1416 */           return outputString;
/*      */         }
/*      */       }
/* 1419 */     } else if ((request.getParameter("usergroupName") != null) && (!request.getParameter("usergroupName").equals(""))) {
/* 1420 */       groupidList = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME = '" + request.getParameter("usergroupName") + "'");
/* 1421 */       if (groupidList.size() == 0) {
/* 1422 */         AMLog.debug("REST API: The usergroupName in the request url is wrong ");
/* 1423 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.delete.wronguseridmessage"), "4606");
/* 1424 */         return outputString;
/*      */       }
/*      */     } else {
/* 1427 */       AMLog.debug("REST API : group id/user name parameter missing in the request.");
/* 1428 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { "usergroup id/user name" }), "4600");
/* 1429 */       return outputString;
/*      */     }
/*      */     
/* 1432 */     UserConfigurationUtil.deleteUserGroupDetails(groupidList);
/* 1433 */     UserConfigurationUtil.updateUserPrivileges(request, null);
/* 1434 */     AMLog.debug("REST API : usergroup details sucessfully deleted.");
/* 1435 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.usergroup.delete.success.text"), "4000");
/* 1436 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getManagedServerList(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1442 */     String outputString = "";
/* 1443 */     String errorCode = "";
/* 1444 */     String message = "";
/* 1445 */     String uri = request.getRequestURI();
/* 1446 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1447 */     if (isJsonFormat) {
/* 1448 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/* 1451 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1453 */     HashMap results = new HashMap();
/* 1454 */     results.put("uri", uri);
/*      */     try
/*      */     {
/* 1457 */       List listOfMas = EnterpriseUtil.getDistributedServers();
/* 1458 */       results.put("result", listOfMas);
/* 1459 */       if (listOfMas.size() > 0)
/*      */       {
/* 1461 */         results.put("response-code", "4000");
/* 1462 */         results.put("nodeName", "Monitor");
/*      */       }
/*      */       else
/*      */       {
/* 1466 */         message = FormatUtil.getString("am.webclient.managedserver.nomanagedserver.message");
/* 1467 */         errorCode = "4129";
/* 1468 */         return URITree.generateXML(request, response, message, errorCode);
/*      */       }
/* 1470 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1474 */       e.printStackTrace();
/* 1475 */       AMLog.debug("REST API : Server error");
/* 1476 */       message = FormatUtil.getString("am.webclient.apikey.wrongserver.message");
/* 1477 */       errorCode = "4128";
/* 1478 */       return URITree.generateXML(request, response, message, errorCode);
/*      */     }
/* 1480 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String refreshUserResources(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1485 */     String outputString = "";
/* 1486 */     String errorCode = "";
/* 1487 */     String message = "";
/* 1488 */     boolean isDone = false;
/* 1489 */     String output = FormatUtil.getString("Unable to refresh UserResources for given user");
/* 1490 */     String status = "4567";
/*      */     try
/*      */     {
/* 1493 */       String usersList = request.getParameter("userIds");
/* 1494 */       AMLog.debug("[MAS - refreshUserResources] usersList:  " + usersList);
/* 1495 */       String[] userIds = usersList.split(",");
/*      */       
/* 1497 */       for (String userId : userIds)
/*      */       {
/* 1499 */         String userName = DBUtil.getSingleDataFromDB("select USERNAME from AM_UserPasswordTable where USERID=" + userId);
/* 1500 */         if (userName != null)
/*      */         {
/* 1502 */           HttpSession hs = UserSessionHandler.getInstance().checkSessionForUserId(userName);
/* 1503 */           AMLog.debug("[MAS - refreshUserResources]userName: " + userName + " and hs : " + hs);
/* 1504 */           if (hs != null)
/*      */           {
/*      */             try
/*      */             {
/* 1508 */               hs.removeAttribute(userId + "_IsUserResTableUpdated");
/* 1509 */               isDone = true;
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1513 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1519 */       if (isDone) {
/* 1520 */         output = FormatUtil.getString("Successfully resources refreshed for users: " + usersList);
/* 1521 */         status = "4000";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1526 */       e.printStackTrace();
/* 1527 */       AMLog.debug("REST API : Server error");
/*      */     }
/* 1529 */     return URITree.generateXML(request, response, output, status);
/*      */   }
/*      */   
/*      */   public static String getUsergroupDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1533 */     String outputString = "";
/* 1534 */     String uri = request.getRequestURI();
/* 1535 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1536 */     if (isJsonFormat) {
/* 1537 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/* 1540 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1542 */     if (!isAdminRole(request)) {
/* 1543 */       AMLog.debug("REST API : No privileges to do this operation.");
/* 1544 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/* 1545 */       return outputString;
/*      */     }
/* 1547 */     ArrayList<Hashtable<String, String>> userGroupDetails = new ArrayList();
/* 1548 */     ManagedApplication mo = new ManagedApplication();
/* 1549 */     ArrayList<ArrayList<String>> lists = mo.getRows("select GROUPID,GROUPNAME from AM_USERGROUP_CONFIG");
/* 1550 */     for (int i = 0; i < lists.size(); i++) {
/* 1551 */       Hashtable<String, String> usergroup = new Hashtable();
/* 1552 */       ArrayList<String> details = (ArrayList)lists.get(i);
/* 1553 */       usergroup.put("ID", details.get(0));
/* 1554 */       usergroup.put("GROUPNAME", details.get(1));
/* 1555 */       userGroupDetails.add(usergroup);
/*      */     }
/* 1557 */     HashMap<String, Object> results = new HashMap();
/* 1558 */     results.put("response-code", "4000");
/* 1559 */     results.put("uri", uri);
/* 1560 */     results.put("result", userGroupDetails);
/* 1561 */     results.put("sortingParam", "GROUPNAME");
/* 1562 */     results.put("parentNode", "Usergroups");
/* 1563 */     results.put("nodeName", "Usergroup");
/* 1564 */     outputString = getOutputAsString(results, isJsonFormat);
/* 1565 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getUserDetails(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1570 */     String outputString = "";
/* 1571 */     String uri = request.getRequestURI();
/* 1572 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1573 */     if (isJsonFormat) {
/* 1574 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/* 1577 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/* 1580 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 1581 */     ManagedApplication mo = new ManagedApplication();
/* 1582 */     ArrayList<ArrayList<String>> lists = mo.getRows("select AM_UserGroupTable.USERNAME,AM_UserGroupTable.GROUPNAME, AM_UserPasswordTable.USERID from AM_UserGroupTable,AM_UserPasswordTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and GROUPNAME not in ('ENTERPRISEADMIN','REPORTER')");
/* 1583 */     String userNamelist = "";
/* 1584 */     String userRolelist = "";
/* 1585 */     String userKey = "";
/* 1586 */     for (int i = 0; i < lists.size(); i++)
/*      */     {
/* 1588 */       Hashtable<String, String> userDetails = new Hashtable();
/* 1589 */       ArrayList<String> list = (ArrayList)lists.get(i);
/* 1590 */       userNamelist = (String)list.get(0);
/* 1591 */       userRolelist = (String)list.get(1);
/* 1592 */       userKey = (String)list.get(2);
/* 1593 */       userDetails.put("ID", userKey);
/* 1594 */       userDetails.put("UserName", userNamelist);
/* 1595 */       userDetails.put("Role", userRolelist);
/* 1596 */       usersList.add(userDetails);
/*      */     }
/*      */     
/* 1599 */     HashMap<String, Object> results = new HashMap();
/* 1600 */     results.put("response-code", "4000");
/* 1601 */     results.put("uri", uri);
/* 1602 */     results.put("result", usersList);
/* 1603 */     results.put("sortingParam", "userName");
/* 1604 */     results.put("parentNode", "Users");
/* 1605 */     results.put("nodeName", "User");
/* 1606 */     outputString = getOutputAsString(results, isJsonFormat);
/* 1607 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getDomainDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1611 */     String outputString = "";
/* 1612 */     String uri = request.getRequestURI();
/* 1613 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 1614 */     if (isJsonFormat) {
/* 1615 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     }
/*      */     else {
/* 1618 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 1620 */     ArrayList<Hashtable<String, String>> domainDetails = ADAuthenticationUtil.getDomainList();
/* 1621 */     HashMap<String, Object> results = new HashMap();
/* 1622 */     results.put("response-code", "4000");
/* 1623 */     results.put("uri", uri);
/* 1624 */     results.put("result", domainDetails);
/* 1625 */     results.put("sortingParam", "label");
/* 1626 */     results.put("parentNode", "Usergroups");
/* 1627 */     results.put("nodeName", "Usergroup");
/* 1628 */     outputString = getOutputAsString(results, isJsonFormat);
/* 1629 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String addMonitorGroup(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1635 */     response.setContentType("text/xml; charset=UTF-8");
/* 1636 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 1637 */     HAIDManagerAction hm = new HAIDManagerAction();
/* 1638 */     String outputString = "";
/* 1639 */     String applicationName = "";
/* 1640 */     String description = "";
/* 1641 */     int groupType_int = 0;
/* 1642 */     String locationID = request.getParameter("locationid");
/* 1643 */     boolean useADDM = Boolean.valueOf(request.getParameter("useADDM") == null ? "false" : request.getParameter("useADDM")).booleanValue();
/* 1644 */     if ((request.getParameter("name") != null) && (!request.getParameter("name").equals("")))
/*      */     {
/* 1646 */       applicationName = request.getParameter("name");
/* 1647 */       if (request.getParameter("description") == null)
/*      */       {
/* 1649 */         description = FormatUtil.getString("am.webclient.newmonitorgroup.description.textbox");
/*      */       }
/*      */       else
/*      */       {
/* 1653 */         description = request.getParameter("description");
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1658 */       AMLog.debug("REST API : The name should not be empty.");
/* 1659 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.name.emptymessage"), "4262");
/* 1660 */       return outputString;
/*      */     }
/* 1662 */     if ((request.getParameter("grouptype") != null) && (!request.getParameter("grouptype").equals("")))
/*      */     {
/* 1664 */       if (request.getParameter("grouptype").equalsIgnoreCase("webappgroup"))
/*      */       {
/* 1666 */         groupType_int = 2;
/*      */       }
/* 1668 */       else if (request.getParameter("grouptype").equalsIgnoreCase("monitorgroup"))
/*      */       {
/* 1670 */         groupType_int = 1;
/*      */       }
/*      */       else
/*      */       {
/* 1674 */         AMLog.debug("REST API : The grouptype should be either monitorgroup or webappgroup.");
/* 1675 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.grouptype.emptymessage"), "4263");
/* 1676 */         return outputString;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1681 */       AMLog.debug("REST API : The grouptype should be either monitorgroup or webappgroup.");
/* 1682 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.grouptype.emptymessage"), "4263");
/* 1683 */       return outputString;
/*      */     }
/*      */     
/* 1686 */     ResultSet rs = null;
/* 1687 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 1690 */       ps = AMConnectionPool.getConnection().prepareStatement("select count(*) from AM_ManagedObject mo,AM_HOLISTICAPPLICATION ha where mo.RESOURCEID=ha.HAID and  mo.TYPE='HAI' and ha.TYPE=0 and DISPLAYNAME=?");
/* 1691 */       ps.setString(1, applicationName);
/* 1692 */       rs = ps.executeQuery();
/* 1693 */       String str1; while (rs.next())
/*      */       {
/* 1695 */         int namecount = rs.getInt(1);
/* 1696 */         if (namecount != 0)
/*      */         {
/* 1698 */           AMLog.debug("REST API : The name for adding Monitor Group already exist.");
/* 1699 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.monitorgroupname.msg"), "4261");
/* 1700 */           return outputString;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1711 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 1713 */         if (ps != null) {
/* 1714 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 1717 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 1721 */       username = "";
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1706 */       ex.printStackTrace();
/* 1707 */       return "false";
/*      */     }
/*      */     finally
/*      */     {
/* 1711 */       AMConnectionPool.closeStatement(rs);
/*      */       try {
/* 1713 */         if (ps != null) {
/* 1714 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 1717 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */     String username;
/* 1722 */     AMManagedObject ammo = mo.createManagedApplication(applicationName, description, username, null, null, false, groupType_int);
/* 1723 */     if (ammo != null)
/*      */     {
/* 1725 */       int haid = ammo.getRESOURCEID();
/* 1726 */       request.setAttribute("name", applicationName);
/* 1727 */       request.setAttribute("type", request.getParameter("grouptype"));
/* 1728 */       String auditLogMsg = FormatUtil.getString("am.audit.addMonitorGroup.msg", new String[] { ammo.getRESOURCENAME() });
/*      */       
/* 1730 */       cliAuditUtil.addToAuditLog(request, haid, 1, auditLogMsg);
/*      */       
/* 1732 */       String userid = request.getParameter("userid");
/* 1733 */       request.setAttribute("resourceid", haid + "");
/* 1734 */       hm.addMonitorGroup(haid, locationID);
/* 1735 */       Hashtable<String, String> userDetails = getUserNameForAPIKey(request.getParameter("apikey"));
/* 1736 */       if (userDetails != null) {
/* 1737 */         String role = (String)userDetails.get("GROUPNAME");
/* 1738 */         String restrictedAdmin = (String)userDetails.get("RESTRICTEDADMIN");
/* 1739 */         if ((("ADMIN".equalsIgnoreCase(role)) || ("ENTERPRISEADMIN".equalsIgnoreCase(role))) && ("0".equals(restrictedAdmin))) {
/* 1740 */           String id = (String)userDetails.get("USERID");
/* 1741 */           boolean containsid = false;
/* 1742 */           if ((userid != null) && (!userid.contains("," + id + ","))) {
/* 1743 */             containsid = true;
/*      */           }
/* 1745 */           if (!containsid) {
/* 1746 */             hm.addOwner(new String[] { id }, haid);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1752 */       if ((userid != null) && (!userid.equals("")))
/*      */       {
/* 1754 */         String[] owners = userid.split(",");
/* 1755 */         int count = 0;
/* 1756 */         if (1 < owners.length)
/*      */         {
/* 1758 */           String checkquery = "select count(USERID) from AM_UserPasswordTable where USERID in (" + userid + ")";
/*      */           try
/*      */           {
/* 1761 */             rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 1762 */             while (rs.next())
/*      */             {
/* 1764 */               count = rs.getInt(1);
/*      */             }
/* 1766 */             if (owners.length == count)
/*      */             {
/* 1768 */               hm.addOwner(owners, haid);
/*      */             }
/*      */             else
/*      */             {
/* 1772 */               AMLog.debug("REST API: The userid in the request url is wrong or the values are repeated.");
/* 1773 */               return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguserid.message"), "4264");
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1779 */             ex.printStackTrace();
/* 1780 */             AMLog.debug("REST API: Server error");
/* 1781 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1788 */       if (groupType_int == 2)
/*      */       {
/* 1790 */         if ((request.getParameter("EndUserTransactionGroup") != null) && (request.getParameter("EndUserTransactionGroup").equals("enable")))
/*      */         {
/* 1792 */           MSreq.addParameter("mgtypes_1001", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1796 */           MSreq.addParameter("mgtypes_1001", "0");
/*      */         }
/* 1798 */         if ((request.getParameter("NetworkDevicesGroup") != null) && (request.getParameter("NetworkDevicesGroup").equals("enable")))
/*      */         {
/* 1800 */           MSreq.addParameter("mgtypes_1007", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1804 */           MSreq.addParameter("mgtypes_1007", "0");
/*      */         }
/* 1806 */         if ((request.getParameter("EdgeDevicesGroup") != null) && (request.getParameter("EdgeDevicesGroup").equals("enable")))
/*      */         {
/* 1808 */           MSreq.addParameter("mgtypes_1008", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1812 */           MSreq.addParameter("mgtypes_1008", "0");
/*      */         }
/* 1814 */         if ((request.getParameter("WebServerGroup") != null) && (request.getParameter("WebServerGroup").equals("enable")))
/*      */         {
/* 1816 */           MSreq.addParameter("mgtypes_1002", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1820 */           MSreq.addParameter("mgtypes_1002", "0");
/*      */         }
/* 1822 */         if ((request.getParameter("ApplicationServerGroup") != null) && (request.getParameter("ApplicationServerGroup").equals("enable")))
/*      */         {
/* 1824 */           MSreq.addParameter("mgtypes_1003", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1828 */           MSreq.addParameter("mgtypes_1003", "0");
/*      */         }
/* 1830 */         if ((request.getParameter("DatabaseGroup") != null) && (request.getParameter("DatabaseGroup").equals("enable")))
/*      */         {
/* 1832 */           MSreq.addParameter("mgtypes_1004", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1836 */           MSreq.addParameter("mgtypes_1004", "0");
/*      */         }
/* 1838 */         if ((request.getParameter("ServersGroup") != null) && (request.getParameter("ServersGroup").equals("enable")))
/*      */         {
/* 1840 */           MSreq.addParameter("mgtypes_1006", "1");
/*      */         }
/*      */         else
/*      */         {
/* 1844 */           MSreq.addParameter("mgtypes_1006", "0");
/*      */         }
/* 1846 */         hm.addWebGroup(MSreq, haid);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1851 */         if (mgNotifier.shouldNotify())
/*      */         {
/* 1853 */           AMLog.debug("Monitor group creation notifier -" + mgNotifier);
/* 1854 */           Hashtable toNotify = new Hashtable();
/* 1855 */           toNotify.put("MGID", haid + "");
/* 1856 */           toNotify.put("MGNAME", applicationName);
/* 1857 */           toNotify.put("EventType", "Updated");
/* 1858 */           mgNotifier.setProperties(toNotify);
/* 1859 */           Scheduler.getScheduler("main").scheduleTask(mgNotifier, System.currentTimeMillis());
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1864 */         AMLog.debug("Exception occurred while notifying the monitor group creation to the observers ; the mg with haid " + haid);
/* 1865 */         e.printStackTrace();
/*      */       }
/*      */       
/* 1868 */       if (useADDM) {
/* 1869 */         ParentChildRelationalUtil.addToUseAddmTable(haid);
/*      */       }
/* 1871 */       if ((request.getParameter("needhaid") != null) && (request.getParameter("needhaid").equals("true"))) {
/* 1872 */         return haid + "";
/*      */       }
/*      */       
/*      */ 
/* 1876 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*      */         try
/*      */         {
/* 1880 */           String qry = "select OWNERID from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + haid;
/* 1881 */           ArrayList<String> ownersOfHaid = DBUtil.getRowsForSingleColumn(qry);
/* 1882 */           if (!ownersOfHaid.isEmpty())
/*      */           {
/* 1884 */             List<String> uList = new ArrayList();
/* 1885 */             for (String owner : ownersOfHaid)
/*      */             {
/* 1887 */               if (RestrictedUsersViewUtil.isRestrictedRole(owner)) {
/* 1888 */                 uList.add(owner);
/*      */               }
/*      */             }
/* 1891 */             if (!uList.isEmpty())
/*      */             {
/* 1893 */               AMLog.debug("[CommonAPIUtil::(addMonitorGroup)]ruser(s) : " + uList);
/* 1894 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(uList);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1900 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1906 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mongroup.succmsg"), "4000", true);
/* 1907 */     return outputString;
/*      */   }
/*      */   
/*      */   public static boolean isDelegatedAdminAPIRequest(HttpServletRequest request) {
/* 1911 */     return isDelegatedAdmin(request);
/*      */   }
/*      */   
/*      */   public static boolean checkResourceidforDelegatedAdmin(HttpServletRequest request, String resourceid) {
/* 1915 */     boolean toRestrict = false;
/*      */     try {
/* 1917 */       String[] resIds = resourceid.split(",");
/* 1918 */       Vector<String> resIdsOwned = new Vector();
/* 1919 */       boolean isDelegatedAdmin = isDelegatedAdmin(request);
/* 1920 */       if ((resIds.length > 0) && (isDelegatedAdmin)) {
/* 1921 */         resIdsOwned = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(getUserIdForAPIKey(request.getParameter("apikey")));
/* 1922 */         for (String resId : resIds) {
/* 1923 */           if (!resIdsOwned.contains(resId)) {
/* 1924 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1929 */       ex.printStackTrace();
/*      */     }
/* 1931 */     return toRestrict;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEESyncRequest(HttpServletRequest request)
/*      */   {
/* 1940 */     return isEESyncRequest(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   private static boolean isEESyncRequest(String apiKey) {
/* 1944 */     return getUsername(apiKey).equalsIgnoreCase("systemadmin_enterprise");
/*      */   }
/*      */   
/*      */   public static String eventLogRuleAPI(HttpServletRequest request, HttpServletResponse response, boolean isjson)
/*      */   {
/* 1949 */     response.setContentType("text/xml; charset=UTF-8");
/* 1950 */     String outputString = "";
/*      */     try
/*      */     {
/* 1953 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 1954 */       if (request.getMethod().equals("GET"))
/*      */       {
/* 1956 */         return listEventLogs(request, response, isjson);
/*      */       }
/* 1958 */       return eventLogOperations(request, response, isjson);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1963 */       e.printStackTrace();
/*      */     }
/* 1965 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String eventLogOperations(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */   {
/* 1970 */     String outputString = "";
/*      */     try
/*      */     {
/* 1973 */       String ruleid = request.getParameter("ruleid");
/* 1974 */       String action = request.getParameter("action");
/* 1975 */       boolean exists = false;
/* 1976 */       if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*      */       {
/* 1978 */         return deleteLogRule(request, response, false);
/*      */       }
/*      */       
/*      */ 
/* 1982 */       if ((ruleid != null) && (ruleid.trim().length() > 0))
/*      */       {
/* 1984 */         exists = DBQueryUtil.checkForDuplicateEntry("AM_GLOBALEVENTLOGRULES", "RULEID", ruleid, "");
/*      */       }
/* 1986 */       if ((!exists) && ((action == null) || (action.trim().length() == 0)))
/*      */       {
/* 1988 */         outputString = createNewRule(request, response, false);
/*      */ 
/*      */ 
/*      */       }
/* 1992 */       else if (request.getParameter("action") != null)
/*      */       {
/* 1994 */         if ("changestatus".equalsIgnoreCase(request.getParameter("action")))
/*      */         {
/* 1996 */           outputString = changestatus(request, response, false);
/*      */         }
/*      */         else
/*      */         {
/* 2000 */           outputString = performlogAction(request, response, false);
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 2005 */         outputString = editLogRule(request, response, false);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2011 */       e.printStackTrace();
/*      */     }
/* 2013 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String listEventLogs(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */   {
/* 2018 */     String outputString = null;
/* 2019 */     ResultSet rs = null;
/* 2020 */     String uri = request.getRequestURI();
/* 2021 */     ArrayList<Hashtable<String, String>> eventlogrules = new ArrayList();
/*      */     try {
/* 2023 */       if (isJsonFormat) {
/* 2024 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 2026 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 2029 */       if ((!isAdminRole(request)) && (!isDelegatedAdmin(request))) {
/* 2030 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 2031 */         return outputString;
/*      */       }
/* 2033 */       int loginUserId = Integer.parseInt(getUserIdForAPIKey(request.getParameter("apikey")));
/* 2034 */       Vector logRuleIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(loginUserId, 9);
/* 2035 */       rs = AMConnectionPool.executeQueryStmt("select RULEID,RULENAME,EVENTID,SOURCE,CATEGORY,USERNAME,EVENTTYPE,DESCRIPTIONSTRING,ALERTSEVERITY,RULETYPE,STATUS,LOGCATEGORY,RULESCOPE from AM_GLOBALEVENTLOGRULES where " + DBUtil.getCondition("RULEID", logRuleIdList));
/* 2036 */       while (rs.next()) {
/* 2037 */         Hashtable<String, String> logrule = new Hashtable();
/* 2038 */         logrule.put("RULEID", rs.getString("RULEID"));
/* 2039 */         logrule.put("RULENAME", rs.getString("RULENAME"));
/* 2040 */         logrule.put("EVENTID", rs.getString("EVENTID"));
/* 2041 */         logrule.put("SOURCE", rs.getString("SOURCE"));
/* 2042 */         logrule.put("CATEGORY", rs.getString("CATEGORY"));
/* 2043 */         logrule.put("USERNAME", rs.getString("USERNAME"));
/* 2044 */         logrule.put("EVENTTYPE", rs.getString("EVENTTYPE"));
/* 2045 */         logrule.put("DESCRIPTIONSTRING", rs.getString("DESCRIPTIONSTRING"));
/* 2046 */         logrule.put("ALERTSEVERITY", rs.getString("ALERTSEVERITY"));
/* 2047 */         logrule.put("RULETYPE", rs.getString("RULETYPE"));
/* 2048 */         logrule.put("STATUS", rs.getString("STATUS"));
/* 2049 */         logrule.put("LOGCATEGORY", rs.getString("LOGCATEGORY"));
/* 2050 */         logrule.put("RULESCOPE", rs.getString("RULESCOPE"));
/* 2051 */         eventlogrules.add(logrule);
/*      */       }
/* 2053 */       if (eventlogrules.size() == 0) {
/* 2054 */         Hashtable msg = new Hashtable();
/* 2055 */         msg.put("Message", FormatUtil.getString("am.webclient.api.eventlogrules.list.empty.text"));
/* 2056 */         eventlogrules.add(msg);
/*      */       }
/* 2058 */       HashMap results = new HashMap();
/* 2059 */       results.put("response-code", "4000");
/* 2060 */       results.put("uri", uri);
/* 2061 */       results.put("result", eventlogrules);
/* 2062 */       results.put("sortingParam", "RULENAME");
/* 2063 */       results.put("parentNode", "RULENAMES");
/* 2064 */       results.put("nodeName", "RULENAME");
/* 2065 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 2067 */       ex.printStackTrace();
/*      */     } finally {
/* 2069 */       if (rs != null) {
/* 2070 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 2073 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String eventLogFileAPI(HttpServletRequest request, HttpServletResponse response, boolean isjson)
/*      */   {
/* 2078 */     response.setContentType("text/xml; charset=UTF-8");
/* 2079 */     String outputString = "";
/*      */     try
/*      */     {
/* 2082 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 2083 */       if (request.getMethod().equals("GET"))
/*      */       {
/* 2085 */         return listLogFiles(request, response, isjson);
/*      */       }
/* 2087 */       return eventLogFileOperations(request, response, isjson);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2092 */       e.printStackTrace();
/*      */     }
/* 2094 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String listLogFiles(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */   {
/* 2099 */     String outputString = null;
/* 2100 */     ResultSet rs = null;
/* 2101 */     String uri = request.getRequestURI();
/* 2102 */     ArrayList<Hashtable<String, String>> logfiles = new ArrayList();
/*      */     try {
/* 2104 */       if (isJson) {
/* 2105 */         response.setContentType("text/plain; charset=UTF-8");
/*      */       } else {
/* 2107 */         response.setContentType("text/xml; charset=UTF-8");
/*      */       }
/*      */       
/* 2110 */       if ((!isAdminRole(request)) && (!isDelegatedAdmin(request))) {
/* 2111 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 2112 */         return outputString;
/*      */       }
/* 2114 */       rs = AMConnectionPool.executeQueryStmt("select RULETYPE,DESCRIPTION,CATEGORY from AM_RULETYPE");
/* 2115 */       while (rs.next()) {
/* 2116 */         Object logfile = new Hashtable();
/* 2117 */         ((Hashtable)logfile).put("RULETYPE", rs.getString("RULETYPE"));
/* 2118 */         ((Hashtable)logfile).put("LOGFILE", rs.getString("DESCRIPTION"));
/* 2119 */         ((Hashtable)logfile).put("CATEGORY", rs.getString("CATEGORY"));
/* 2120 */         logfiles.add(logfile);
/*      */       }
/* 2122 */       if (logfiles.size() == 0) {
/* 2123 */         Hashtable msg = new Hashtable();
/* 2124 */         msg.put("Message", FormatUtil.getString("am.webclient.api.logfile.list.empty.text"));
/* 2125 */         logfiles.add(msg);
/*      */       }
/* 2127 */       HashMap results = new HashMap();
/* 2128 */       results.put("response-code", "4000");
/* 2129 */       results.put("uri", uri);
/* 2130 */       results.put("result", logfiles);
/* 2131 */       results.put("sortingParam", "LOGFILE");
/* 2132 */       results.put("parentNode", "LOGFILES");
/* 2133 */       results.put("nodeName", "LOGFILE");
/* 2134 */       outputString = getOutputAsString(results, isJson);
/*      */     } catch (Exception ex) {
/* 2136 */       ex.printStackTrace();
/*      */     } finally {
/* 2138 */       if (rs != null) {
/* 2139 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/* 2142 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String eventLogFileOperations(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */   {
/* 2147 */     String outputString = "";
/*      */     try
/*      */     {
/* 2150 */       String ruletype = request.getParameter("ruletype");
/* 2151 */       boolean exists = false;
/* 2152 */       if ((request.getParameter("TO_DELETE") != null) && ("true".equalsIgnoreCase(request.getParameter("TO_DELETE"))))
/*      */       {
/* 2154 */         return deleteLogFile(request, response, false);
/*      */       }
/*      */       
/* 2157 */       if ((ruletype != null) && (ruletype.trim().length() > 0))
/*      */       {
/* 2159 */         exists = DBQueryUtil.checkForDuplicateEntry("AM_RULETYPE", "RULETYPE", ruletype, "");
/*      */       }
/* 2161 */       if (!exists)
/*      */       {
/* 2163 */         outputString = addEventLogFile(request, response, false);
/*      */       }
/*      */       else
/*      */       {
/* 2167 */         outputString = editLogFile(request, response, false);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2172 */       e.printStackTrace();
/*      */     }
/* 2174 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String createNewRule(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */     throws Exception
/*      */   {
/* 2180 */     response.setContentType("text/xml; charset=UTF-8");
/* 2181 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*      */     try
/*      */     {
/* 2184 */       int loginUserId = Integer.parseInt(getUserIdForAPIKey(request.getParameter("apikey")));
/* 2185 */       String severity = "1";
/* 2186 */       String status = "1";
/* 2187 */       String eventtype = "0";
/* 2188 */       String monitorchooser = "allmonitor";
/*      */       
/* 2190 */       String rulename = request.getParameter("rulename");
/* 2191 */       String eventid = request.getParameter("eventid");
/* 2192 */       String source = request.getParameter("source");
/* 2193 */       String category = request.getParameter("category");
/* 2194 */       String username = request.getParameter("username");
/* 2195 */       if ((request.getParameter("eventtype") != null) && (request.getParameter("eventtype").trim().length() > 0))
/*      */       {
/* 2197 */         eventtype = request.getParameter("eventtype");
/*      */       }
/* 2199 */       String message = request.getParameter("message");
/* 2200 */       severity = request.getParameter("severity");
/* 2201 */       String ruletype = request.getParameter("ruletype");
/* 2202 */       boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_RULETYPE", "RULETYPE", ruletype, "");
/* 2203 */       if (!exists) {
/* 2204 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eventlogfile.notexist.text"), "4578");
/*      */       }
/*      */       
/* 2207 */       status = request.getParameter("status");
/* 2208 */       String resourceids = request.getParameter("resourceids");
/* 2209 */       String logCategoryName = request.getParameter("logCategoryName");
/* 2210 */       String cleartype = "0";
/* 2211 */       String clearpollscount = "1";
/* 2212 */       String pollstoretry = "1";
/* 2213 */       String clearevent = "-1";
/* 2214 */       String matchcount = "1";
/* 2215 */       String usemsgRegex = "0";
/* 2216 */       String matchrules = "1";
/* 2217 */       String log_starttime = "";
/* 2218 */       String log_endtime = "";
/* 2219 */       String userId = getUserIdForAPIKey(request.getParameter("apikey"));
/* 2220 */       if (DelegatedUserRoleUtil.isDelegatedAdmin(userId))
/*      */       {
/* 2222 */         if (("allmonitor".equalsIgnoreCase(request.getParameter("applyto"))) || ("monitortype".equalsIgnoreCase(request.getParameter("applyto"))))
/*      */         {
/* 2224 */           AMLog.debug("REST API : Delegated Admin cannot create a rule of type allmonitor or monitortype.");
/* 2225 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */         }
/*      */       }
/*      */       
/* 2229 */       if ((request.getParameter("log_starttime") != null) && (request.getParameter("log_starttime").trim().length() > 0)) {
/* 2230 */         log_starttime = request.getParameter("log_starttime");
/*      */       }
/*      */       
/* 2233 */       if ((request.getParameter("log_endtime") != null) && (request.getParameter("log_endtime").trim().length() > 0)) {
/* 2234 */         log_endtime = request.getParameter("log_endtime");
/*      */       }
/*      */       
/* 2237 */       if ((request.getParameter("matchrules") != null) && (request.getParameter("matchrules").trim().length() > 0)) {
/* 2238 */         matchrules = request.getParameter("matchrules");
/*      */       }
/*      */       
/* 2241 */       if ((request.getParameter("cleartype") != null) && (request.getParameter("cleartype").trim().length() > 0))
/*      */       {
/* 2243 */         cleartype = request.getParameter("cleartype");
/*      */       }
/* 2245 */       if ((request.getParameter("clearpollscount") != null) && (request.getParameter("clearpollscount").trim().length() > 0))
/*      */       {
/* 2247 */         clearpollscount = request.getParameter("clearpollscount");
/*      */       }
/* 2249 */       if ((request.getParameter("pollstoretry") != null) && (request.getParameter("pollstoretry").trim().length() > 0))
/*      */       {
/* 2251 */         pollstoretry = request.getParameter("pollstoretry");
/*      */       }
/* 2253 */       else if ((request.getParameter("alarmcount") != null) && (request.getParameter("alarmcount").trim().length() > 0))
/*      */       {
/* 2255 */         pollstoretry = request.getParameter("alarmcount");
/*      */       }
/* 2257 */       if ((request.getParameter("clearevent") != null) && (request.getParameter("clearevent").trim().length() > 0))
/*      */       {
/* 2259 */         clearevent = request.getParameter("clearevent");
/*      */       }
/* 2261 */       if ((request.getParameter("matchcount") != null) && (request.getParameter("matchcount").trim().length() > 0))
/*      */       {
/* 2263 */         matchcount = request.getParameter("matchcount");
/*      */       }
/* 2265 */       if ((request.getParameter("ismessageRegex") != null) && (request.getParameter("ismessageRegex").trim().length() > 0))
/*      */       {
/* 2267 */         usemsgRegex = request.getParameter("ismessageRegex");
/*      */       }
/* 2269 */       request.setAttribute("isadd", "true");
/* 2270 */       String[] errormsg = validateeventlogParameters(request);
/*      */       
/* 2272 */       if (!errormsg[0].equalsIgnoreCase("success"))
/*      */       {
/* 2274 */         return URITree.generateXML(request, response, errormsg[1], errormsg[0]);
/*      */       }
/*      */       
/* 2277 */       String osname = System.getProperty("os.name");
/* 2278 */       boolean isWindows = (osname.startsWith("Windows")) || (osname.startsWith("Windows")) || (EnterpriseUtil.isAdminServer());
/* 2279 */       if (!isWindows)
/*      */       {
/* 2281 */         return URITree.generateXML(request, response, "4105", "am.webclient.eventlog.notsupported");
/*      */       }
/*      */       
/* 2284 */       if ((request.getParameter("monitorchooser") != null) && (request.getParameter("monitorchooser").trim().length() > 0))
/*      */       {
/* 2286 */         monitorchooser = request.getParameter("monitorchooser");
/*      */       }
/*      */       else {
/* 2289 */         monitorchooser = request.getParameter("applyto");
/*      */       }
/* 2291 */       String savetype = "new";
/* 2292 */       String displayname = request.getParameter("displayname");
/* 2293 */       String prerulestatus = "";
/* 2294 */       String prescope = request.getParameter("prescope");
/* 2295 */       String servertypes = request.getParameter("servertypes");
/* 2296 */       HashMap<String, String> mappingdetails = new HashMap();
/*      */       
/* 2298 */       if (servertypes != null)
/*      */       {
/* 2300 */         String[] montypes = servertypes.split(",");
/* 2301 */         for (String monType : montypes)
/*      */         {
/* 2303 */           mappingdetails.put(monType, AMAttributesCache.getHealthId(monType));
/*      */         }
/*      */       }
/* 2306 */       String ruleid = request.getParameter("ruleid");
/* 2307 */       HashMap<String, String> logprops = new HashMap();
/* 2308 */       logprops.put("rulename", rulename);
/* 2309 */       logprops.put("eventid", eventid);
/* 2310 */       logprops.put("source", source);
/* 2311 */       logprops.put("category", category);
/* 2312 */       logprops.put("username", username);
/* 2313 */       logprops.put("eventtype", eventtype);
/* 2314 */       logprops.put("message", message);
/* 2315 */       logprops.put("severity", severity);
/* 2316 */       logprops.put("ruletype", ruletype);
/* 2317 */       logprops.put("status", status);
/* 2318 */       logprops.put("resourceids", resourceids);
/* 2319 */       logprops.put("logCategoryName", logCategoryName);
/* 2320 */       logprops.put("monitorchooser", monitorchooser);
/* 2321 */       logprops.put("savetype", savetype);
/* 2322 */       logprops.put("displayname", displayname);
/* 2323 */       logprops.put("prerulestatus", prerulestatus);
/* 2324 */       logprops.put("prescope", prescope);
/* 2325 */       logprops.put("ruleid", ruleid);
/* 2326 */       logprops.put("haid", request.getParameter("haid"));
/* 2327 */       logprops.put("cleartype", cleartype);
/* 2328 */       logprops.put("clearpollscount", clearpollscount);
/* 2329 */       logprops.put("pollstoretry", pollstoretry);
/* 2330 */       logprops.put("clearevent", clearevent);
/* 2331 */       logprops.put("matchcount", matchcount);
/* 2332 */       logprops.put("ismessageRegex", usemsgRegex);
/* 2333 */       logprops.put("matchrules", matchrules);
/* 2334 */       logprops.put("log_starttime", log_starttime);
/* 2335 */       logprops.put("log_endtime", log_endtime);
/* 2336 */       String scopetype = monitorchooser;
/* 2337 */       String residtoadd = request.getParameter("resourceids");
/* 2338 */       int adminruleidavail = -1;
/* 2339 */       if ((ruleid != null) && (ruleid.trim().length() > 0))
/*      */       {
/* 2341 */         adminruleidavail = Integer.parseInt(ruleid);
/*      */       }
/* 2343 */       if ((EnterpriseUtil.isAdminServer()) && ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */       {
/* 2345 */         EventLogUtil.setResourceIdFromHaid(logprops);
/* 2346 */         residtoadd = (String)logprops.get("resourceids");
/*      */       }
/* 2348 */       else if (("monitorgroup".equalsIgnoreCase(scopetype)) && (adminruleidavail < 10000))
/*      */       {
/* 2350 */         EventLogUtil.setResourceIdFromHaid(logprops);
/* 2351 */         residtoadd = (String)logprops.get("resourceids");
/*      */       }
/* 2353 */       boolean isLogRuleCreated = false;
/* 2354 */       if ("admin".equals(request.getParameter("apicallfrom"))) {
/* 2355 */         isLogRuleCreated = EventLogUtil.addorEditeventLog(logprops, mappingdetails, null, -1);
/*      */       }
/*      */       else {
/* 2358 */         isLogRuleCreated = EventLogUtil.addorEditeventLog(logprops, mappingdetails, null, loginUserId);
/*      */       }
/* 2360 */       if (isLogRuleCreated)
/*      */       {
/* 2362 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 2364 */           String apiurl = "/AppManager/xml/logrule";
/* 2365 */           int taskType = 5;
/* 2366 */           if (("monitorlist".equalsIgnoreCase(scopetype)) || ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */           {
/* 2368 */             HashMap<String, String> manservers = EnterpriseUtil.maptoManagedServers(residtoadd);
/* 2369 */             Set<String> keys = manservers.keySet();
/* 2370 */             Iterator<String> it = keys.iterator();
/* 2371 */             String masid = "";
/* 2372 */             while (it.hasNext())
/*      */             {
/* 2374 */               masid = (String)it.next();
/* 2375 */               String manresids = (String)manservers.get(masid);
/* 2376 */               logprops.put("resourceids", manresids);
/* 2377 */               MASSyncUtil.addTasktoSync(logprops, apiurl, masid, "POST", taskType, 1);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2382 */             if ((servertypes != null) && (servertypes.length() > 0))
/*      */             {
/* 2384 */               logprops.put("servertypes", servertypes);
/*      */             }
/*      */             else {
/* 2387 */               logprops.put("servertypes", "");
/*      */             }
/* 2389 */             MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", taskType, 1);
/*      */           }
/*      */         }
/* 2392 */         AMAttributesCache.refreshEventLogCache();
/* 2393 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.succmsg", new String[] { "logrule" }), "4000", true);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2399 */       e.printStackTrace();
/*      */     }
/* 2401 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String[] validateeventlogParameters(HttpServletRequest request) {
/* 2405 */     String errormsg = "";
/* 2406 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2409 */       String action = request.getParameter("action");
/* 2410 */       HashSet<String> validateparams = null;
/* 2411 */       uri = request.getRequestURI();
/* 2412 */       String[] arrayOfString1; if ("true".equalsIgnoreCase((String)request.getAttribute("isadd")))
/*      */       {
/* 2414 */         if ((request.getParameter("rulename") == null) || (request.getParameter("rulename").length() == 0)) {
/* 2415 */           String errorcode = "4100";
/* 2416 */           errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "rulename" });
/* 2417 */           return new String[] { errorcode, errormsg };
/*      */         }
/*      */       }
/* 2420 */       String errorcode = null;
/* 2421 */       if ((request.getParameter("severity") != null) && (request.getParameter("severity").length() > 0))
/*      */       {
/* 2423 */         validateparams = new HashSet();
/* 2424 */         validateparams.add("0");
/* 2425 */         validateparams.add("1");
/* 2426 */         if (!validateparams.contains(request.getParameter("severity")))
/*      */         {
/* 2428 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "severity", "0,1" });
/* 2429 */           errorcode = "4102";
/* 2430 */           return new String[] { errorcode, errormsg };
/*      */         }
/*      */       }
/* 2433 */       if (((request.getParameter("status") != null) && (request.getParameter("status").length() > 0)) || ("changestatus".equalsIgnoreCase(request.getParameter("action"))))
/*      */       {
/* 2435 */         validateparams = new HashSet();
/* 2436 */         validateparams.add("enable");
/* 2437 */         validateparams.add("disable");
/* 2438 */         validateparams.add("0");
/* 2439 */         validateparams.add("1");
/* 2440 */         if (("changestatus".equalsIgnoreCase(request.getParameter("action"))) && ((request.getParameter("status") == null) || (request.getParameter("status").length() == 0)))
/*      */         {
/* 2442 */           errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "status" });
/* 2443 */           errorcode = "4100";
/* 2444 */           return new String[] { errorcode, errormsg };
/*      */         }
/* 2446 */         if (!validateparams.contains(request.getParameter("status")))
/*      */         {
/* 2448 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "status", "enable,disable,0,1" });
/* 2449 */           errorcode = "4102";
/* 2450 */           return new String[] { errorcode, errormsg }; } }
/*      */       Object eventtype;
/*      */       String[] eventTypeParams;
/* 2453 */       if ((request.getParameter("eventtype") != null) && (request.getParameter("eventtype").length() > 0))
/*      */       {
/* 2455 */         validateparams = new HashSet();
/* 2456 */         validateparams.add("0");
/* 2457 */         validateparams.add("1");
/* 2458 */         validateparams.add("2");
/* 2459 */         validateparams.add("3");
/* 2460 */         eventtype = request.getParameter("eventtype");
/* 2461 */         if (((String)eventtype).contains(",")) {
/* 2462 */           eventTypeParams = ((String)eventtype).split(",");
/* 2463 */           for (String type : eventTypeParams) {
/* 2464 */             if (!validateparams.contains(type)) {
/* 2465 */               errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "eventtype", "0,1,2,3" });
/* 2466 */               errorcode = "4102";
/* 2467 */               return new String[] { errorcode, errormsg };
/*      */             }
/*      */           }
/* 2470 */         } else if (!validateparams.contains(eventtype)) {
/* 2471 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "eventtype", "0,1,2,3" });
/* 2472 */           errorcode = "4102";
/* 2473 */           return new String[] { errorcode, errormsg };
/*      */         }
/*      */       }
/* 2476 */       if ((request.getParameter("applyto") != null) && (request.getParameter("applyto").length() > 0))
/*      */       {
/* 2478 */         validateparams = new HashSet();
/* 2479 */         validateparams.add("allmonitor");
/* 2480 */         validateparams.add("monitortype");
/* 2481 */         validateparams.add("monitorlist");
/* 2482 */         validateparams.add("monitorgroup");
/* 2483 */         if (!validateparams.contains(request.getParameter("applyto")))
/*      */         {
/* 2485 */           errorcode = "4102";
/* 2486 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.list", new String[] { "applyto", "allmonitor,monitortype,monitorlist,monitorgroup" });
/* 2487 */           return new String[] { errorcode, errormsg }; } }
/*      */       String servertypequoted;
/*      */       int size;
/* 2490 */       if ("monitortype".equalsIgnoreCase(request.getParameter("applyto")))
/*      */       {
/* 2492 */         String servertypes = request.getParameter("servertypes");
/* 2493 */         if ((servertypes == null) || (servertypes.trim().length() == 0))
/*      */         {
/* 2495 */           errorcode = "4100";
/* 2496 */           errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "servertypes" });
/* 2497 */           return new String[] { errorcode, errormsg };
/*      */         }
/* 2499 */         servertypequoted = appendquotes(servertypes);
/* 2500 */         String query = "select RESOURCETYPE from AM_ManagedResourceType where RESOURCETYPE in (" + servertypequoted + ")";
/* 2501 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2502 */         rs.last();
/* 2503 */         if (rs != null)
/*      */         {
/* 2505 */           rs.close();
/*      */         }
/* 2507 */         size = rs.getRow();
/* 2508 */         if ((size < 1) || (servertypes.split(",").length != size))
/*      */         {
/* 2510 */           errorcode = "4102";
/* 2511 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.text", new String[] { "servertypes" });
/* 2512 */           return new String[] { errorcode, errormsg };
/*      */         }
/*      */       }
/* 2515 */       if ("monitorlist".equalsIgnoreCase(request.getParameter("applyto")))
/*      */       {
/* 2517 */         String resourceids = request.getParameter("resourceids");
/* 2518 */         if ((resourceids == null) || (resourceids.trim().length() == 0))
/*      */         {
/* 2520 */           errorcode = "4100";
/* 2521 */           errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "resourceids" });
/* 2522 */           return new String[] { errorcode, errormsg };
/*      */         }
/* 2524 */         String query = "select RESOURCENAME from AM_ManagedObject where RESOURCEID in (" + resourceids + ") and TYPE in (select RESOURCETYPE from AM_ManagedResourceType where SUBGROUP='Windows') or TYPE in (select MONTYPE from WMICONFIGDETAILS where LOGSCREATED='true')";
/* 2525 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2526 */         rs.last();
/* 2527 */         int size = rs.getRow();
/* 2528 */         rs.close();
/* 2529 */         if ((size < 1) || (resourceids.split(",").length != size))
/*      */         {
/* 2531 */           errorcode = "4102";
/* 2532 */           errormsg = FormatUtil.getString("am.webclient.api.common.param.wrong.text", new String[] { "resourceids" });
/* 2533 */           return new String[] { errorcode, errormsg };
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*      */       String uri;
/* 2539 */       String errorcode = "4105";
/* 2540 */       errormsg = FormatUtil.getString("am.webclient.api.common.problem.msg");
/* 2541 */       e.printStackTrace();
/* 2542 */       return new String[] { errorcode, errormsg };
/*      */     }
/*      */     finally
/*      */     {
/* 2546 */       if (rs != null)
/*      */       {
/* 2548 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 2552 */     return new String[] { "success" };
/*      */   }
/*      */   
/*      */   public static String validatelogrulelist(HttpServletRequest request)
/*      */   {
/* 2557 */     ResultSet rs = null;
/* 2558 */     String ruleid = request.getParameter("ruleid");
/* 2559 */     String resourceids = request.getParameter("resourceids");
/* 2560 */     StringBuffer wrongresid = new StringBuffer();
/*      */     try
/*      */     {
/* 2563 */       String action = request.getParameter("action");
/* 2564 */       String query = "select RESOURCEID from AM_EVENTLOGRESOURCEIDMAPPER where RULEID=" + ruleid;
/* 2565 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2566 */       HashSet<String> residsavailable = new HashSet();
/* 2567 */       while (rs.next())
/*      */       {
/* 2569 */         residsavailable.add(rs.getString("RESOURCEID"));
/*      */       }
/* 2571 */       String[] residarray = resourceids.split(",");
/* 2572 */       if ("add".equalsIgnoreCase(action)) {
/* 2573 */         for (String resid : residarray)
/*      */         {
/* 2575 */           if (residsavailable.contains(resid))
/*      */           {
/* 2577 */             wrongresid.append(resid + ",");
/*      */           }
/*      */           
/*      */         }
/* 2581 */       } else if ("remove".equalsIgnoreCase(action))
/*      */       {
/* 2583 */         for (String resid : residarray) {
/* 2584 */           if (!residsavailable.contains(resid))
/*      */           {
/* 2586 */             wrongresid.append(resid + ",");
/*      */           }
/*      */         }
/*      */       }
/* 2590 */       if (wrongresid.length() > 0)
/*      */       {
/* 2592 */         return wrongresid.substring(0, wrongresid.length() - 1);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2597 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2602 */       if (rs != null)
/*      */       {
/* 2604 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 2607 */     return wrongresid.toString();
/*      */   }
/*      */   
/*      */   public static String editLogRule(HttpServletRequest request, HttpServletResponse response, boolean isjson) throws Exception
/*      */   {
/* 2612 */     response.setContentType("text/xml; charset=UTF-8");
/* 2613 */     ResultSet rs = null;
/* 2614 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 2615 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/* 2617 */       AMLog.debug("REST API : Admin server users cannot edit logrules.");
/* 2618 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 2619 */       return outputString;
/*      */     }
/*      */     try
/*      */     {
/* 2623 */       String[] errormsg = validateeventlogParameters(request);
/* 2624 */       if (!errormsg[0].equalsIgnoreCase("success"))
/*      */       {
/* 2626 */         outputString = URITree.generateXML(request, response, errormsg[1], errormsg[0]);
/* 2627 */         return outputString;
/*      */       }
/* 2629 */       String qry = "select RULENAME,EVENTID,SOURCE,CATEGORY,USERNAME,EVENTTYPE,DESCRIPTIONSTRING,ALERTSEVERITY,RULETYPE,STATUS,LOGCATEGORY,RULESCOPE,THRESHOLDID,MATCHCOUNT,CLEAREVENT,CLEARTYPE,ISMESSAGEREGEX,MATCHRULES,LOGTIME,LOG_ENDTIME from AM_GLOBALEVENTLOGRULES where RULEID=" + request.getParameter("ruleid");
/* 2630 */       String rulename = null;
/* 2631 */       String eventid = null;
/* 2632 */       String source = null;
/* 2633 */       String category = null;
/* 2634 */       String username = null;
/* 2635 */       String eventtype = null;
/* 2636 */       String message = null;
/* 2637 */       String severity = null;
/* 2638 */       String ruletype = null;
/* 2639 */       String status = null;
/* 2640 */       String resourceids = null;
/* 2641 */       String logCategoryName = null;
/* 2642 */       String monitorchooser = null;
/* 2643 */       String savetype = null;
/* 2644 */       String displayname = null;
/* 2645 */       String prerulestatus = null;
/* 2646 */       String prescope = null;
/* 2647 */       String ismsgRegex = "0";
/* 2648 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 2649 */       String thresholdid = "";
/* 2650 */       String matchcount = "";
/* 2651 */       String clearevent = "";
/* 2652 */       String cleartype = "";
/* 2653 */       String matchrules = "";
/* 2654 */       String log_starttime = null;
/* 2655 */       String log_endtime = null;
/* 2656 */       if (rs.next())
/*      */       {
/* 2658 */         rulename = rs.getString("RULENAME");
/* 2659 */         eventid = rs.getString("EVENTID");
/* 2660 */         source = rs.getString("SOURCE");
/* 2661 */         category = rs.getString("CATEGORY");
/* 2662 */         username = rs.getString("USERNAME");
/* 2663 */         eventtype = rs.getString("EVENTTYPE");
/* 2664 */         message = rs.getString("DESCRIPTIONSTRING");
/* 2665 */         severity = rs.getString("ALERTSEVERITY");
/* 2666 */         ruletype = rs.getString("RULETYPE");
/* 2667 */         prerulestatus = rs.getString("STATUS");
/* 2668 */         logCategoryName = rs.getString("LOGCATEGORY");
/* 2669 */         prescope = rs.getString("RULESCOPE");
/* 2670 */         thresholdid = rs.getString("THRESHOLDID");
/* 2671 */         matchcount = rs.getString("MATCHCOUNT");
/* 2672 */         clearevent = rs.getString("CLEAREVENT");
/* 2673 */         cleartype = rs.getString("CLEARTYPE");
/* 2674 */         ismsgRegex = rs.getString("ISMESSAGEREGEX");
/* 2675 */         matchrules = rs.getString("MATCHRULES");
/* 2676 */         if (rs.getTime("LOGTIME") != null) {
/* 2677 */           log_starttime = rs.getTime("LOGTIME").toString().substring(0, rs.getTime("LOGTIME").toString().lastIndexOf(":"));
/*      */         }
/* 2679 */         if (rs.getTime("LOG_ENDTIME") != null) {
/* 2680 */           log_endtime = rs.getTime("LOG_ENDTIME").toString().substring(0, rs.getTime("LOG_ENDTIME").toString().lastIndexOf(":"));
/*      */         }
/*      */       }
/*      */       else {
/* 2684 */         return createNewRule(request, response, false);
/*      */       }
/* 2686 */       rs.close();
/* 2687 */       String clearpollscount = "1";
/* 2688 */       String pollstoretry = "1";
/* 2689 */       if ((thresholdid != null) && (!thresholdid.equals("-1")))
/*      */       {
/* 2691 */         rs = AMConnectionPool.executeQueryStmt("select CRITICAL_POLLSTOTRY,WARNING_POLLSTOTRY,CLEAR_POLLSTOTRY from AM_THRESHOLDCONFIG where ID=" + thresholdid);
/* 2692 */         if (rs.next())
/*      */         {
/* 2694 */           if ("1".equals(severity))
/*      */           {
/* 2696 */             pollstoretry = rs.getString("CRITICAL_POLLSTOTRY");
/*      */           }
/*      */           else
/*      */           {
/* 2700 */             pollstoretry = rs.getString("WARNING_POLLSTOTRY");
/*      */           }
/* 2702 */           clearpollscount = rs.getString("CLEAR_POLLSTOTRY");
/*      */         }
/*      */       }
/* 2705 */       if (request.getParameter("logCategoryName") != null) {
/* 2706 */         logCategoryName = request.getParameter("logCategoryName");
/*      */       }
/* 2708 */       if (request.getParameter("rulename") != null)
/*      */       {
/* 2710 */         rulename = request.getParameter("rulename");
/*      */       }
/* 2712 */       if (request.getParameter("eventid") != null)
/*      */       {
/* 2714 */         eventid = request.getParameter("eventid");
/*      */       }
/* 2716 */       if (request.getParameter("source") != null)
/*      */       {
/* 2718 */         source = request.getParameter("source");
/*      */       }
/* 2720 */       if (request.getParameter("category") != null)
/*      */       {
/* 2722 */         category = request.getParameter("category");
/*      */       }
/* 2724 */       if (request.getParameter("username") != null)
/*      */       {
/* 2726 */         username = request.getParameter("username");
/*      */       }
/* 2728 */       if (request.getParameter("eventtype") != null)
/*      */       {
/* 2730 */         eventtype = request.getParameter("eventtype");
/*      */       }
/* 2732 */       if (request.getParameter("message") != null)
/*      */       {
/* 2734 */         message = request.getParameter("message");
/*      */       }
/* 2736 */       if (request.getParameter("severity") != null)
/*      */       {
/* 2738 */         severity = request.getParameter("severity");
/*      */       }
/* 2740 */       if (request.getParameter("ruletype") != null)
/*      */       {
/* 2742 */         ruletype = request.getParameter("ruletype");
/*      */       }
/* 2744 */       if (request.getParameter("status") != null)
/*      */       {
/* 2746 */         status = request.getParameter("status");
/*      */       }
/*      */       else
/*      */       {
/* 2750 */         status = prerulestatus;
/*      */       }
/* 2752 */       if (request.getParameter("displayname") != null)
/*      */       {
/* 2754 */         displayname = request.getParameter("displayname");
/*      */       }
/* 2756 */       if (request.getParameter("applyto") != null)
/*      */       {
/* 2758 */         monitorchooser = request.getParameter("applyto");
/*      */       }
/* 2760 */       else if (request.getParameter("monitorchooser") != null)
/*      */       {
/* 2762 */         monitorchooser = request.getParameter("monitorchooser");
/*      */ 
/*      */ 
/*      */       }
/* 2766 */       else if ("0".equals(prescope))
/*      */       {
/* 2768 */         monitorchooser = "allmonitor";
/*      */       }
/* 2770 */       else if ("1".equals(prescope))
/*      */       {
/* 2772 */         monitorchooser = "monitortype";
/*      */       }
/* 2774 */       else if ("3".equals(prescope))
/*      */       {
/* 2776 */         monitorchooser = "monitorgroup";
/*      */       }
/*      */       
/* 2779 */       if ((request.getParameter("cleartype") != null) && (request.getParameter("cleartype").trim().length() > 0))
/*      */       {
/* 2781 */         cleartype = request.getParameter("cleartype");
/*      */       }
/* 2783 */       if ((request.getParameter("clearpollscount") != null) && (request.getParameter("clearpollscount").trim().length() > 0))
/*      */       {
/* 2785 */         clearpollscount = request.getParameter("clearpollscount");
/*      */       }
/* 2787 */       if ((request.getParameter("clearevent") != null) && (request.getParameter("clearevent").trim().length() > 0))
/*      */       {
/* 2789 */         clearevent = request.getParameter("clearevent");
/*      */       }
/* 2791 */       if ((request.getParameter("pollstoretry") != null) && (request.getParameter("pollstoretry").trim().length() > 0))
/*      */       {
/* 2793 */         pollstoretry = request.getParameter("pollstoretry");
/*      */       }
/* 2795 */       else if ((request.getParameter("alarmcount") != null) && (request.getParameter("alarmcount").trim().length() > 0))
/*      */       {
/* 2797 */         pollstoretry = request.getParameter("alarmcount");
/*      */       }
/* 2799 */       if ((request.getParameter("matchcount") != null) && (request.getParameter("matchcount").trim().length() > 0))
/*      */       {
/* 2801 */         matchcount = request.getParameter("matchcount");
/*      */       }
/* 2803 */       if ((request.getParameter("ismessageRegex") != null) && (request.getParameter("ismessageRegex").trim().length() > 0))
/*      */       {
/* 2805 */         ismsgRegex = request.getParameter("ismessageRegex");
/*      */       }
/* 2807 */       if ((request.getParameter("matchrules") != null) && (request.getParameter("matchrules").trim().length() > 0)) {
/* 2808 */         matchrules = request.getParameter("matchrules");
/*      */       }
/* 2810 */       if ((request.getParameter("log_starttime") != null) && (request.getParameter("log_starttime").trim().length() > 0)) {
/* 2811 */         log_starttime = request.getParameter("log_starttime");
/*      */       }
/* 2813 */       if ((request.getParameter("log_endtime") != null) && (request.getParameter("log_endtime").trim().length() > 0)) {
/* 2814 */         log_endtime = request.getParameter("log_endtime");
/*      */       }
/* 2816 */       resourceids = request.getParameter("resourceids");
/* 2817 */       String ruleid = request.getParameter("ruleid");
/* 2818 */       String haid = request.getParameter("haid");
/* 2819 */       displayname = rulename;
/* 2820 */       int ruleidint = Integer.parseInt(ruleid);
/* 2821 */       String servertypes = request.getParameter("servertypes");
/* 2822 */       HashMap<String, String> mappingdetails = new HashMap();
/* 2823 */       if (servertypes != null)
/*      */       {
/* 2825 */         String[] montypes = servertypes.split(",");
/* 2826 */         for (String monType : montypes)
/*      */         {
/* 2828 */           mappingdetails.put(monType, AMAttributesCache.getHealthId(monType));
/*      */         }
/*      */       }
/* 2831 */       HashMap<String, String> logprops = new HashMap();
/* 2832 */       logprops.put("rulename", rulename);
/* 2833 */       logprops.put("eventid", eventid);
/* 2834 */       logprops.put("source", source);
/* 2835 */       logprops.put("category", category);
/* 2836 */       logprops.put("username", username);
/* 2837 */       logprops.put("eventtype", eventtype);
/* 2838 */       logprops.put("message", message);
/* 2839 */       logprops.put("severity", severity);
/* 2840 */       logprops.put("ruletype", ruletype);
/* 2841 */       logprops.put("status", status);
/* 2842 */       logprops.put("resourceids", resourceids);
/* 2843 */       logprops.put("logCategoryName", logCategoryName);
/* 2844 */       logprops.put("monitorchooser", monitorchooser);
/* 2845 */       logprops.put("savetype", savetype);
/* 2846 */       logprops.put("displayname", displayname);
/* 2847 */       logprops.put("prerulestatus", prerulestatus);
/* 2848 */       logprops.put("prescope", prescope);
/* 2849 */       logprops.put("ruleid", ruleid);
/* 2850 */       logprops.put("haid", haid);
/* 2851 */       logprops.put("thresholdid", thresholdid);
/* 2852 */       logprops.put("matchcount", matchcount);
/* 2853 */       logprops.put("clearevent", clearevent);
/* 2854 */       logprops.put("cleartype", cleartype);
/* 2855 */       logprops.put("pollstoretry", pollstoretry);
/* 2856 */       logprops.put("clearpollscount", clearpollscount);
/* 2857 */       logprops.put("ismessageRegex", ismsgRegex);
/* 2858 */       logprops.put("matchrules", matchrules);
/* 2859 */       logprops.put("log_starttime", log_starttime);
/* 2860 */       logprops.put("log_endtime", log_endtime);
/* 2861 */       String[] montypeandhealthid = null;
/* 2862 */       if (servertypes != null)
/*      */       {
/* 2864 */         String[] montypes = servertypes.split(",");
/* 2865 */         montypeandhealthid = new String[montypes.length];
/* 2866 */         for (int i = 0; i < montypes.length; i++) {
/* 2867 */           montypeandhealthid[i] = (montypes[i] + "#" + AMAttributesCache.getHealthId(montypes[i]));
/*      */         }
/*      */       }
/* 2870 */       String scopetype = monitorchooser;
/* 2871 */       String redidtoadd = request.getParameter("resourceids");
/* 2872 */       int adminruleidavail = -1;
/* 2873 */       if ((ruleid != null) && (ruleid.trim().length() > 0))
/*      */       {
/* 2875 */         adminruleidavail = Integer.parseInt(ruleid);
/*      */       }
/* 2877 */       if ((EnterpriseUtil.isAdminServer()) && ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */       {
/* 2879 */         EventLogUtil.setResourceIdFromHaid(logprops);
/* 2880 */         redidtoadd = (String)logprops.get("resourceids");
/*      */       }
/* 2882 */       else if (("monitorgroup".equalsIgnoreCase(scopetype)) && (adminruleidavail < 10000))
/*      */       {
/* 2884 */         EventLogUtil.setResourceIdFromHaid(logprops);
/* 2885 */         redidtoadd = (String)logprops.get("resourceids");
/*      */       }
/* 2887 */       EventLogUtil.addorEditeventLog(logprops, null, montypeandhealthid, -1);
/* 2888 */       String apiurl; if ((EnterpriseUtil.isAdminServer()) && (ruleidint >= 10000))
/*      */       {
/* 2890 */         apiurl = "/AppManager/xml/logrule";
/* 2891 */         int taskType = 5;
/* 2892 */         if (("monitorlist".equalsIgnoreCase(scopetype)) || ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */         {
/* 2894 */           if (("monitorlist".equalsIgnoreCase(scopetype)) && (
/* 2895 */             (redidtoadd == null) || (redidtoadd.trim().length() == 0)))
/*      */           {
/* 2897 */             StringBuffer sb = new StringBuffer();
/* 2898 */             rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_EVENTLOGRESOURCEIDMAPPER where RULEID=" + ruleid);
/* 2899 */             while (rs.next())
/*      */             {
/* 2901 */               sb.append(rs.getString("RESOURCEID") + ",");
/*      */             }
/* 2903 */             if (sb.length() > 1)
/*      */             {
/* 2905 */               redidtoadd = sb.substring(0, sb.length() - 1);
/*      */             }
/*      */           }
/*      */           
/* 2909 */           HashMap<String, String> manservers = EnterpriseUtil.maptoManagedServers(redidtoadd);
/* 2910 */           Set<String> keys = manservers.keySet();
/* 2911 */           Iterator<String> it = keys.iterator();
/* 2912 */           String masid = "";
/* 2913 */           while (it.hasNext())
/*      */           {
/* 2915 */             masid = (String)it.next();
/* 2916 */             String manresids = (String)manservers.get(masid);
/* 2917 */             logprops.put("resourceids", manresids);
/* 2918 */             MASSyncUtil.addTasktoSync(logprops, apiurl, masid, "POST", taskType, 1);
/*      */           }
/* 2920 */           String masIdtodelete = EventLogUtil.getManagedServerIndxtoDelete(manservers);
/* 2921 */           apiurl = "/AppManager/xml/logrule";
/* 2922 */           HashMap<String, String> delerules = new HashMap();
/* 2923 */           delerules.put("ruleids", ruleid);
/* 2924 */           delerules.put("TO_DELETE", "true");
/* 2925 */           for (String masid1 : masIdtodelete.split(",")) {
/* 2926 */             MASSyncUtil.addTasktoSync(delerules, apiurl, masid1, "POST", taskType, 1);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2931 */           if ((servertypes != null) && (servertypes.length() > 0)) {
/* 2932 */             logprops.put("servertypes", servertypes.substring(0, servertypes.length() - 1));
/*      */           }
/* 2934 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", taskType, 1);
/*      */         }
/*      */       }
/* 2937 */       AMAttributesCache.refreshEventLogCache();
/* 2938 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/* 2939 */       return outputString;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2943 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 2948 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 2950 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteLogRule(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */   {
/* 2955 */     ResultSet rs = null;
/* 2956 */     String outputString = "";
/* 2957 */     response.setContentType("text/xml; charset=UTF-8");
/*      */     try
/*      */     {
/* 2960 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 2961 */       String rulestodelete = request.getParameter("ruleids");
/* 2962 */       int userId = Integer.parseInt(getUserIdForAPIKey(request.getParameter("apikey")));
/* 2963 */       boolean isPriviledgedUser = Constants.isPrivilegedUser(request);
/* 2964 */       String[] rules = new String[0];
/* 2965 */       rulestodelete = (rulestodelete != null) && (rulestodelete.trim().endsWith(",")) ? rulestodelete.trim().substring(0, rulestodelete.lastIndexOf(",")) : rulestodelete;
/* 2966 */       if (rulestodelete != null)
/*      */       {
/* 2968 */         rules = rulestodelete.split(",");
/* 2969 */         for (int i = 0; i < rules.length; i++)
/*      */         {
/* 2971 */           Integer.parseInt(rules[i]);
/* 2972 */           if (((isPriviledgedUser) && (!DelegatedUserRoleUtil.isOwnedByDelegatedUser(Integer.parseInt(rules[i]), userId, 9))) || ((!EnterpriseUtil.isAdminServer()) && (Integer.parseInt(rules[i]) >= 10000) && (!"admin".equals(request.getParameter("apicallfrom")))))
/*      */           {
/* 2974 */             AMLog.debug("REST API : The user is not authorized to access the specified resource");
/* 2975 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 2976 */             return outputString;
/*      */           }
/*      */         }
/*      */       }
/* 2980 */       String qry = "select RULETYPE from AM_GLOBALEVENTLOGRULES where RULEID in (" + rulestodelete + ")";
/* 2981 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 2982 */       String ruletype = "";
/* 2983 */       if (rs.next())
/*      */       {
/* 2985 */         ruletype = rs.getString("RULETYPE");
/*      */       } else {
/* 2987 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eventlog.notexist.text"), "4577", true);
/*      */       }
/*      */       
/* 2990 */       if (EventLogUtil.deleteEventLogRules(rulestodelete, ruletype))
/*      */       {
/* 2992 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 2994 */           String apiurl = "/AppManager/xml/logrule";
/* 2995 */           HashMap<String, String> logprops = new HashMap();
/* 2996 */           logprops.put("ruleids", request.getParameter("ruleids"));
/* 2997 */           logprops.put("TO_DELETE", "true");
/* 2998 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/*      */         
/* 3001 */         AMAttributesCache.refreshEventLogCache();
/* 3002 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.delete"), "4000", true);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3007 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 3012 */       if (rs != null)
/*      */       {
/* 3014 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 3017 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteLogFile(HttpServletRequest request, HttpServletResponse response, boolean isJson)
/*      */   {
/* 3022 */     response.setContentType("text/xml; charset=UTF-8");
/* 3023 */     ResultSet rs = null;
/* 3024 */     String outputString = "";
/*      */     try {
/* 3026 */       String loginUsername = getUsername(request.getParameter("apikey"));
/* 3027 */       boolean isdelegAdmin = DBUtil.isDelegatedAdmin(loginUsername);
/* 3028 */       if (isdelegAdmin) {
/* 3029 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/* 3031 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 3032 */       String eventid = null;
/* 3033 */       if (request.getParameter("ruletype") != null)
/*      */       {
/* 3035 */         eventid = request.getParameter("ruletype");
/* 3036 */         boolean exists = DBQueryUtil.checkForDuplicateEntry("AM_RULETYPE", "RULETYPE", eventid, "");
/* 3037 */         if (!exists) {
/* 3038 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.eventlogfile.notexist.text"), "4578");
/* 3039 */           return outputString;
/*      */         }
/*      */       }
/* 3042 */       else if (request.getParameter("logfile") != null)
/*      */       {
/* 3044 */         String qry = "select RULETYPE from AM_RULETYPE where DESCRIPTION ='" + request.getParameter("logfile") + "'";
/* 3045 */         rs = AMConnectionPool.executeQueryStmt(qry);
/* 3046 */         if (rs.next())
/*      */         {
/* 3048 */           eventid = String.valueOf(rs.getInt("RULETYPE"));
/*      */         }
/*      */         
/* 3051 */         if (rs != null)
/*      */         {
/* 3053 */           rs.close();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3058 */         String errorcode = "4100";
/* 3059 */         String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "logfile" });
/* 3060 */         outputString = URITree.generateXML(request, response, errormsg, errorcode, true);
/* 3061 */         return outputString;
/*      */       }
/* 3063 */       if (EventLogUtil.deleteLogFilerule(eventid))
/*      */       {
/* 3065 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 3067 */           String apiurl = "/AppManager/xml/logfile";
/* 3068 */           Object logprops = new HashMap();
/* 3069 */           ((HashMap)logprops).put("ruletype", eventid);
/* 3070 */           ((HashMap)logprops).put("TO_DELETE", "true");
/* 3071 */           MASSyncUtil.addTasktoSync((HashMap)logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/* 3073 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logfile.delete"), "4000", true);
/*      */       }
/* 3075 */       AMAttributesCache.refreshEventLogCache();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 3081 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 3086 */       if (rs != null)
/*      */       {
/* 3088 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 3091 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String addEventLogFile(HttpServletRequest request, HttpServletResponse response, boolean isjson) throws Exception {
/* 3095 */     response.setContentType("text/xml; charset=UTF-8");
/* 3096 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*      */     try
/*      */     {
/* 3099 */       String loginUsername = getUsername(request.getParameter("apikey"));
/* 3100 */       boolean isdelegAdmin = DBUtil.isDelegatedAdmin(loginUsername);
/* 3101 */       if (isdelegAdmin) {
/* 3102 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/* 3104 */       String category = request.getParameter("category");
/* 3105 */       String logFileName = request.getParameter("logFileName");
/*      */       
/* 3107 */       int ruletype = -1;
/* 3108 */       if (request.getParameter("ruletype") != null)
/*      */       {
/* 3110 */         ruletype = Integer.parseInt(request.getParameter("ruletype"));
/*      */       }
/* 3112 */       if ((logFileName == null) || (logFileName.trim().length() == 0))
/*      */       {
/* 3114 */         String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "logFileName" });
/* 3115 */         return URITree.generateXML(request, response, errormsg, "4100");
/*      */       }
/*      */       
/* 3118 */       int[] iscreated = EventLogUtil.createRuleType(category, logFileName, ruletype);
/* 3119 */       if (iscreated[0] == 1)
/*      */       {
/* 3121 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 3123 */           HashMap<String, String> logprops = new HashMap();
/* 3124 */           logprops.put("category", category);
/* 3125 */           logprops.put("logFileName", logFileName);
/* 3126 */           logprops.put("ruletype", String.valueOf(iscreated[1]));
/* 3127 */           String apiurl = "/AppManager/xml/logfile";
/* 3128 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/* 3130 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.succmsg", new String[] { "logfile" }), "4000", true);
/* 3131 */         AMAttributesCache.refreshEventLogCache();
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3137 */       e.printStackTrace();
/*      */     }
/* 3139 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String editLogFile(HttpServletRequest request, HttpServletResponse response, boolean isjson) throws Exception {
/* 3143 */     String filename = request.getParameter("logFileName");
/* 3144 */     String ruletype = request.getParameter("ruletype");
/* 3145 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*      */     try
/*      */     {
/* 3148 */       if (request.getParameter("ruletype") != null)
/*      */       {
/* 3150 */         if ((filename == null) || (filename.trim().length() == 0))
/*      */         {
/* 3152 */           String errormsg = FormatUtil.getString("am.webclient.api.common.parameter.missing.text", new String[] { "logFileName" });
/* 3153 */           return URITree.generateXML(request, response, errormsg, "4100");
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 3158 */           Integer.parseInt(request.getParameter("ruletype"));
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3162 */           String errormsg = FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "logfile" });
/* 3163 */           return URITree.generateXML(request, response, errormsg, "4103");
/*      */         }
/*      */       }
/*      */       
/* 3167 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.name.emptymessage"), "4262");
/* 3168 */       if (EventLogUtil.editLogFileName(filename, ruletype))
/*      */       {
/* 3170 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 3172 */           String apiurl = "/AppManager/xml/logfile";
/*      */           
/* 3174 */           HashMap<String, String> logprops = new HashMap();
/* 3175 */           logprops.put("logFileName", filename);
/* 3176 */           logprops.put("ruletype", ruletype);
/* 3177 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/* 3179 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logfile" }), "4000", true);
/* 3180 */         AMAttributesCache.refreshEventLogCache();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3185 */       e.printStackTrace();
/*      */     }
/* 3187 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String performlogAction(HttpServletRequest request, HttpServletResponse response, boolean isJson) throws Exception {
/* 3191 */     ResultSet rs = null;
/* 3192 */     response.setContentType("text/xml; charset=UTF-8");
/* 3193 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/*      */     try
/*      */     {
/* 3196 */       String[] errormsg = validateeventlogParameters(request);
/* 3197 */       String ruleid = request.getParameter("ruleid");
/* 3198 */       if (!errormsg[0].equalsIgnoreCase("success"))
/*      */       {
/* 3200 */         outputString = URITree.generateXML(request, response, errormsg[1], errormsg[0]);
/* 3201 */         return outputString;
/*      */       }
/* 3203 */       Object logprops = new HashMap();
/* 3204 */       String rulestodelete = request.getParameter("ruleids");
/* 3205 */       ((HashMap)logprops).put("ruleid", ruleid);
/* 3206 */       ((HashMap)logprops).put("action", request.getParameter("action"));
/* 3207 */       String status = "";
/* 3208 */       if ((request.getParameter("status") == null) || (request.getParameter("status").trim().length() == 0))
/*      */       {
/* 3210 */         rs = AMConnectionPool.executeQueryStmt("select STATUS from AM_GLOBALEVENTLOGRULES where RULEID=" + ruleid);
/* 3211 */         if (rs.next())
/*      */         {
/* 3213 */           status = String.valueOf(rs.getString("STATUS"));
/*      */         }
/* 3215 */         if (rs != null)
/*      */         {
/* 3217 */           rs.close();
/*      */         }
/*      */       }
/* 3220 */       ((HashMap)logprops).put("status", status);
/* 3221 */       StringBuffer healthids; if ("monitortype".equalsIgnoreCase(request.getParameter("type")))
/*      */       {
/* 3223 */         boolean success = false;
/* 3224 */         healthids = new StringBuffer();
/* 3225 */         if ("remove".equalsIgnoreCase(request.getParameter("action"))) {
/* 3226 */           String servertypes = appendquotes(request.getParameter("servertypes"));
/* 3227 */           String qry = "select am.ATTRIBUTEID from AM_EVENTLOGATTRIBUTEMAPPING  ae inner join AM_ATTRIBUTES am on ae.ATTRIBUTEID = am.ATTRIBUTEID where ae.RULEID=" + ruleid + " and am.RESOURCETYPE in (" + servertypes + ")";
/* 3228 */           rs = AMConnectionPool.executeQueryStmt(qry);
/* 3229 */           while (rs.next())
/*      */           {
/* 3231 */             healthids.append(rs.getInt("ATTRIBUTEID") + ",");
/*      */           }
/* 3233 */           if (rs != null)
/*      */           {
/* 3235 */             rs.close();
/*      */           }
/* 3237 */           if (EventLogUtil.deleteResourceGroups((HashMap)logprops, healthids.substring(0, healthids.length() - 1))) {
/* 3238 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/* 3239 */             success = true;
/*      */           }
/*      */         }
/* 3242 */         else if ("add".equalsIgnoreCase(request.getParameter("action")))
/*      */         {
/* 3244 */           String[] servertypes = request.getParameter("servertypes").split(",");
/* 3245 */           String quatedservertypes = appendquotes(request.getParameter("servertypes"));
/* 3246 */           HashSet<String> supportedtypes = new HashSet();
/* 3247 */           rs = AMConnectionPool.executeQueryStmt("select distinct RESOURCETYPE from AM_ManagedResourceType where SUBGROUP='Windows' or RESOURCETYPE in (select MONTYPE from WMICONFIGDETAILS)");
/* 3248 */           while (rs.next())
/*      */           {
/* 3250 */             supportedtypes.add(rs.getString("RESOURCETYPE"));
/*      */           }
/* 3252 */           StringBuilder wrongserver = new StringBuilder();
/* 3253 */           for (String servertype : servertypes) {
/* 3254 */             if (supportedtypes.contains(servertype))
/*      */             {
/* 3256 */               healthids.append(AMAttributesCache.getHealthId(servertype) + ",");
/*      */             }
/*      */             else
/*      */             {
/* 3260 */               wrongserver.append(servertype + ",");
/*      */             }
/*      */           }
/* 3263 */           if (rs != null)
/*      */           {
/* 3265 */             rs.close();
/*      */           }
/* 3267 */           if (wrongserver.length() > 0)
/*      */           {
/* 3269 */             String errorstring = FormatUtil.getString("am.webclient.api.eventlog.montype.wrong", new String[] { wrongserver.substring(0, wrongserver.length() - 1) });
/* 3270 */             outputString = URITree.generateXML(request, response, errorstring, "4102", true);
/* 3271 */             return outputString;
/*      */           }
/* 3273 */           EventLogUtil.insertResourceGroups(healthids.substring(0, healthids.length() - 1), (HashMap)logprops);
/* 3274 */           success = true;
/* 3275 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */         }
/* 3277 */         if ((EnterpriseUtil.isAdminServer()) && (success)) {
/* 3278 */           HashMap<String, String> params = new HashMap();
/* 3279 */           params.put("type", request.getParameter("type"));
/* 3280 */           params.put("action", request.getParameter("action"));
/* 3281 */           params.put("servertypes", request.getParameter("servertypes"));
/* 3282 */           String apiurl = "/AppManager/xml/logrule";
/* 3283 */           String masIDs = "all";
/* 3284 */           MASSyncUtil.addTasktoSync(params, apiurl, masIDs, "POST", 5, 1);
/*      */         }
/*      */       }
/* 3287 */       else if ("monitorlist".equalsIgnoreCase(request.getParameter("type"))) {
/*      */         boolean exist;
/* 3289 */         if ("remove".equalsIgnoreCase(request.getParameter("action"))) {
/* 3290 */           String monlisterr = validatelogrulelist(request);
/* 3291 */           if (monlisterr.length() > 0)
/*      */           {
/* 3293 */             return FormatUtil.getString("am.webclient.api.logrule.monlist.notavail", new String[] { monlisterr });
/*      */           }
/* 3295 */           if (EventLogUtil.deleteResourceidfromRCAMapper(request.getParameter("resourceids"), true, (HashMap)logprops)) {
/* 3296 */             if (EnterpriseUtil.isAdminServer()) {
/* 3297 */               String query = "select RESOURCEID from AM_EVENTLOGRESOURCEIDMAPPER where RULEID=" + ruleid;
/* 3298 */               rs = AMConnectionPool.executeQueryStmt(query);
/* 3299 */               StringBuffer sbf = new StringBuffer();
/* 3300 */               while (rs.next())
/*      */               {
/* 3302 */                 sbf.append(rs.getString("RESOURCEID") + ",");
/*      */               }
/* 3304 */               String resids = sbf.substring(0, sbf.length() - 1);
/* 3305 */               HashMap<String, String> manservers = EnterpriseUtil.maptoManagedServers(resids);
/* 3306 */               Set<String> keys = manservers.keySet();
/* 3307 */               Iterator<String> it = keys.iterator();
/* 3308 */               String masid = "";
/* 3309 */               while (it.hasNext())
/*      */               {
/* 3311 */                 masid = (String)it.next();
/* 3312 */                 String manresids = (String)manservers.get(masid);
/* 3313 */                 ((HashMap)logprops).put("resourceids", manresids);
/* 3314 */                 ((HashMap)logprops).put("fromadmin", "true");
/* 3315 */                 ((HashMap)logprops).put("type", "monitorlist");
/* 3316 */                 String apiurl = "/AppManager/xml/logrule";
/* 3317 */                 MASSyncUtil.addTasktoSync((HashMap)logprops, apiurl, masid, "POST", 5, 1);
/*      */               }
/* 3319 */               rs.close();
/*      */             }
/* 3321 */             if (request.getParameter("fromadmin") != null)
/*      */             {
/* 3323 */               rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_EVENTLOGRESOURCEIDMAPPER where RULEID=" + ruleid);
/* 3324 */               exist = rs.next();
/* 3325 */               rs.close();
/* 3326 */               if (!exist)
/*      */               {
/* 3328 */                 String qry = "select RULETYPE from AM_GLOBALEVENTLOGRULES where RULEID =" + ruleid;
/* 3329 */                 rs = AMConnectionPool.executeQueryStmt(qry);
/* 3330 */                 String ruletype = "";
/* 3331 */                 if (rs.next())
/*      */                 {
/* 3333 */                   ruletype = String.valueOf(rs.getInt("RULETYPE"));
/*      */                 }
/* 3335 */                 rs.close();
/* 3336 */                 EventLogUtil.deleteEventLogRules(ruleid, ruletype);
/*      */               }
/*      */             }
/* 3339 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */           }
/*      */         }
/* 3342 */         else if ("add".equalsIgnoreCase(request.getParameter("action")))
/*      */         {
/* 3344 */           String monlisterr = validatelogrulelist(request);
/* 3345 */           if (monlisterr.length() > 0)
/*      */           {
/* 3347 */             return FormatUtil.getString("am.webclient.api.logrule.monlist.exist", new String[] { monlisterr });
/*      */           }
/* 3349 */           String newresourceids = request.getParameter("resourceids");
/* 3350 */           String apiurl; if (EnterpriseUtil.isAdminServer())
/*      */           {
/* 3352 */             HashMap<String, String> params = new HashMap();
/* 3353 */             EventLogUtil.assignlogvalues(params, request.getParameter("ruleid"));
/* 3354 */             ((HashMap)logprops).put("status", status);
/* 3355 */             HashMap<String, String> manserversmapping = EnterpriseUtil.maptoManagedServers(newresourceids);
/* 3356 */             params.put("action", "add");
/* 3357 */             params.put("fromadmin", "true");
/* 3358 */             params.put("type", "monitorlist");
/* 3359 */             apiurl = "/AppManager/xml/logrule";
/* 3360 */             Set<String> manserverlist = manserversmapping.keySet();
/* 3361 */             Iterator<String> managedit = manserverlist.iterator();
/* 3362 */             String managedname = "";
/* 3363 */             while (managedit.hasNext())
/*      */             {
/* 3365 */               managedname = (String)managedit.next();
/* 3366 */               String indresourceids = (String)manserversmapping.get(managedname);
/* 3367 */               params.put("resourceids", indresourceids);
/* 3368 */               MASSyncUtil.addTasktoSync(params, apiurl, managedname, "POST", 5, 1);
/*      */             }
/*      */           }
/* 3371 */           if ("true".equalsIgnoreCase(request.getParameter("fromadmin")))
/*      */           {
/* 3373 */             String query = "select RULEID from AM_GLOBALEVENTLOGRULES where RULEID=" + ruleid;
/* 3374 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 3375 */             boolean exists = rs.next();
/* 3376 */             rs.close();
/* 3377 */             if (!exists)
/*      */             {
/* 3379 */               return createNewRule(request, response, false);
/*      */             }
/*      */           }
/* 3382 */           EventLogUtil.insertselectedResidinRCAMapper(newresourceids, (HashMap)logprops);
/* 3383 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */         }
/*      */       }
/* 3386 */       else if ("monitorgroup".equalsIgnoreCase(request.getParameter("type")))
/*      */       {
/* 3388 */         if (("true".equalsIgnoreCase(request.getParameter("fromadmin"))) && ("add".equalsIgnoreCase(request.getParameter("action"))))
/*      */         {
/* 3390 */           rs = AMConnectionPool.executeQueryStmt("select * from AM_GLOBALEVENTLOGRULES where RULEID=" + request.getParameter("ruleid"));
/* 3391 */           boolean exising = rs.next();
/* 3392 */           if (!exising)
/*      */           {
/* 3394 */             outputString = createNewRule(request, response, false);
/*      */           }
/*      */           else
/*      */           {
/* 3398 */             String newresourceids = request.getParameter("resourceids");
/* 3399 */             EventLogUtil.insertselectedResidinRCAMapper(newresourceids, (HashMap)logprops);
/* 3400 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */           }
/*      */         }
/* 3403 */         if (("true".equalsIgnoreCase(request.getParameter("fromadmin"))) && ("remove".equalsIgnoreCase(request.getParameter("action"))))
/*      */         {
/* 3405 */           if (("remove".equalsIgnoreCase(request.getParameter("action"))) && 
/* 3406 */             (EventLogUtil.deleteResourceidfromRCAMapper(request.getParameter("resourceids"), true, (HashMap)logprops))) {
/* 3407 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */           }
/*      */           
/* 3410 */           rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_EVENTLOGRESOURCEIDMAPPER where RULEID=" + ruleid);
/* 3411 */           boolean exist = rs.next();
/* 3412 */           rs.close();
/* 3413 */           if (!exist)
/*      */           {
/* 3415 */             String qry = "select RULETYPE from AM_GLOBALEVENTLOGRULES where RULEID =" + ruleid;
/* 3416 */             rs = AMConnectionPool.executeQueryStmt(qry);
/* 3417 */             String ruletype = "";
/* 3418 */             if (rs.next())
/*      */             {
/* 3420 */               ruletype = String.valueOf(rs.getInt("RULETYPE"));
/*      */             }
/* 3422 */             rs.close();
/* 3423 */             EventLogUtil.deleteEventLogRules(ruleid, ruletype);
/*      */           }
/* 3425 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */         }
/*      */       }
/* 3428 */       AMAttributesCache.refreshEventLogCache();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3432 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3436 */       if (rs != null)
/*      */       {
/* 3438 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 3441 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String changestatus(HttpServletRequest request, HttpServletResponse response, boolean isjson) throws Exception {
/* 3445 */     String ruleids = request.getParameter("ruleids");
/* 3446 */     String type = request.getParameter("status");
/* 3447 */     String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.problem.msg"), "4105");
/* 3448 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3451 */       String[] errormsg = validateeventlogParameters(request);
/* 3452 */       if (!errormsg[0].equalsIgnoreCase("success"))
/*      */       {
/* 3454 */         outputString = URITree.generateXML(request, response, errormsg[1], errormsg[0]);
/* 3455 */         return outputString;
/*      */       }
/* 3457 */       String ruletype = "";
/* 3458 */       rs = AMConnectionPool.executeQueryStmt("select RULETYPE from AM_GLOBALEVENTLOGRULES where ruleid in (" + ruleids + ")");
/* 3459 */       if (rs.next())
/*      */       {
/* 3461 */         ruletype = String.valueOf(rs.getInt("RULETYPE"));
/*      */       }
/* 3463 */       String[] ruleidtochange = ruleids.split(",");
/* 3464 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.name.emptymessage"), "4262");
/* 3465 */       int statusid = -1;
/* 3466 */       if ("enable".equalsIgnoreCase(type))
/*      */       {
/* 3468 */         statusid = 1;
/*      */       }
/* 3470 */       else if ("disable".equalsIgnoreCase(type))
/*      */       {
/* 3472 */         statusid = 0;
/*      */       }
/* 3474 */       else if ("0".equalsIgnoreCase(type))
/*      */       {
/* 3476 */         statusid = 0;
/*      */       }
/* 3478 */       else if ("1".equalsIgnoreCase(type))
/*      */       {
/* 3480 */         statusid = 1;
/*      */       }
/* 3482 */       if (statusid > -1)
/*      */       {
/* 3484 */         boolean isenable = EventLogUtil.globalenabledisable(statusid, ruleidtochange, ruletype, type);
/* 3485 */         AMAttributesCache.refreshEventLogCache();
/* 3486 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 3488 */           StringBuffer ruleidscommans = new StringBuffer();
/* 3489 */           for (String eachrule : ruleidtochange) {
/* 3490 */             ruleidscommans.append(eachrule + ",");
/*      */           }
/* 3492 */           HashMap<String, String> logprops = new HashMap();
/* 3493 */           logprops.put("ruleids", ruleidscommans.substring(0, ruleidscommans.length() - 1));
/* 3494 */           logprops.put("status", type);
/* 3495 */           logprops.put("action", "changestatus");
/* 3496 */           String apiurl = "/AppManager/xml/logrule";
/* 3497 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/* 3499 */         if (isenable)
/*      */         {
/* 3501 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.logrule.edit", new String[] { "logrule" }), "4000", true);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3507 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3511 */       if (rs != null)
/*      */       {
/* 3513 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 3517 */     return outputString;
/*      */   }
/*      */   
/*      */   public static ArrayList<Hashtable> getSortedList(ArrayList<Hashtable> result, String orderByStr)
/*      */   {
/*      */     try
/*      */     {
/* 3524 */       String[] orderByParams = orderByStr.split(",");
/* 3525 */       TreeMap<String, Hashtable> sortedMap = new TreeMap();
/* 3526 */       for (int i = 0; i < result.size(); i++)
/*      */       {
/* 3528 */         if (!((Hashtable)result.get(i)).containsKey("Message"))
/*      */         {
/* 3530 */           String orderByParam = "";
/* 3531 */           if (orderByParams.length > 1)
/*      */           {
/* 3533 */             for (int j = 0; j < orderByParams.length; j++)
/*      */             {
/* 3535 */               orderByParam = orderByParam + ((Hashtable)result.get(i)).get(orderByParams[j]) + "_";
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/* 3540 */             orderByParam = (String)((Hashtable)result.get(i)).get(orderByStr);
/*      */           }
/* 3542 */           sortedMap.put(orderByParam, result.get(i));
/*      */         }
/*      */         else
/*      */         {
/* 3546 */           sortedMap.put("Message", result.get(i));
/*      */         }
/*      */       }
/* 3549 */       return new ArrayList(sortedMap.values());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3553 */       e.printStackTrace();
/*      */     }
/* 3555 */     return result;
/*      */   }
/*      */   
/*      */   public static String appendquotes(String montypes)
/*      */   {
/*      */     try
/*      */     {
/* 3562 */       String[] montypesarr = montypes.split(",");
/* 3563 */       StringBuffer montypebuffer = new StringBuffer();
/* 3564 */       for (String montype : montypesarr) {
/* 3565 */         montypebuffer.append("'" + montype + "'" + ",");
/*      */       }
/* 3567 */       montypes = montypebuffer.substring(0, montypebuffer.length() - 1);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3572 */       e.printStackTrace();
/*      */     }
/* 3574 */     return montypes;
/*      */   }
/*      */   
/*      */   public static String deleteMonitor(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3579 */     response.setContentType("text/xml; charset=UTF-8");
/* 3580 */     if ((request.getParameter("resourceid") == null) || (request.getParameter("resourceid").equals(""))) {
/* 3581 */       AMLog.debug("REST API : Improper resourceid in the request.");
/* 3582 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 3583 */       return outputString;
/*      */     }
/* 3585 */     ResultSet rs = null;
/* 3586 */     String outputString = "";
/* 3587 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 3588 */     DataCollectionController dcc = new DataCollectionController();
/* 3589 */     String resID = request.getParameter("resourceid");
/* 3590 */     if (checkResourceidforDelegatedAdmin(request, resID)) {
/* 3591 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.monitor.delegatedAdmin.check.text"), "4501");
/* 3592 */       return outputString;
/*      */     }
/*      */     try {
/* 3595 */       int count = 0;
/*      */       
/* 3597 */       checkquery = "select AM_ManagedObject.RESOURCEID from AM_ManagedResourceType,AM_ManagedObject where (AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE or AM_ManagedObject.TYPE='Windows') and RESOURCEID IN (" + resID + ")";
/* 3598 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3599 */       String resourceid; while (rs.next()) {
/* 3600 */         resourceid = rs.getString("RESOURCEID");
/* 3601 */         MSreq.addParameter("select", resourceid);
/* 3602 */         count++;
/*      */       }
/* 3604 */       if (count == 0) {
/* 3605 */         AMLog.debug("REST API : Improper resourceid in the request.");
/* 3606 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 3607 */         return outputString;
/*      */       }
/* 3609 */       MSreq.addParameter("type", "All");
/* 3610 */       dcc.deleteMO(null, null, MSreq, response);
/* 3611 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.deletesuc.msg"), "4000", true);
/* 3612 */       return outputString;
/*      */     } catch (Exception ex) { String checkquery;
/* 3614 */       ex.printStackTrace();
/* 3615 */       AMLog.debug("REST API : Server error");
/* 3616 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/* 3617 */       return outputString;
/*      */     }
/*      */     finally {
/* 3620 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String pollNow(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 3627 */     String outputString = "";
/* 3628 */     String resourceid = "";
/* 3629 */     String resID = "";
/* 3630 */     String monType = "";
/* 3631 */     int Resid = -1;
/* 3632 */     String baseid = "-1";
/* 3633 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 3634 */     AdminTools at = new AdminTools();
/* 3635 */     response.setContentType("text/xml; charset=UTF-8");
/* 3636 */     if ((request.getParameter("resourceid") == null) || (request.getParameter("resourceid").equals("")))
/*      */     {
/* 3638 */       AMLog.debug("REST API : Improper resourceid in the request.");
/* 3639 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 3640 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 3646 */       resID = request.getParameter("resourceid");
/* 3647 */       if (resID != null)
/*      */       {
/* 3649 */         Resid = Integer.parseInt(resID);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3654 */       e.printStackTrace();
/* 3655 */       AMLog.debug("REST API : The specified request URI is incorrect:the resourceid specified is wrong");
/* 3656 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongresidparam.message"), "4002");
/*      */     }
/*      */     
/* 3659 */     if (isPrivilegedUser(request)) {
/* 3660 */       Vector<String> resIdsOwned = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(getUserIdForAPIKey(request.getParameter("apikey")));
/* 3661 */       if (!resIdsOwned.contains(resID)) {
/* 3662 */         AMLog.debug("REST API : The user is not authorized to do this operation.");
/* 3663 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 3664 */         return outputString;
/*      */       }
/*      */     }
/* 3667 */     if (DataCollectionControllerUtil.underMaintenance(Resid))
/*      */     {
/* 3669 */       AMLog.debug("REST API : The monitor is under maintenance. Try pollnow after maintenance.");
/* 3670 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.poll.maintenance.msg"), "4049");
/* 3671 */       return outputString;
/*      */     }
/* 3673 */     if (DataCollectionControllerUtil.isUnManaged(resID))
/*      */     {
/* 3675 */       AMLog.debug("REST API : The monitor cant be polled when unmanaged.");
/* 3676 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.poll.unmanage.msg"), "4050");
/* 3677 */       return outputString;
/*      */     }
/*      */     
/* 3680 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3683 */       String checkquery = "select AM_ManagedResourceType.RESOURCETYPE,AM_ManagedObject.RESOURCEID,TYPEID from AM_ManagedObject,AM_ManagedResourceType left outer join AM_MONITOR_TYPES on RESOURCETYPEID=TYPEID  where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE in " + Constants.resourceTypes + " and AM_ManagedObject.RESOURCEID='" + resID + "'";
/* 3684 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 3685 */       if (rs.next())
/*      */       {
/* 3687 */         resourceid = rs.getString("RESOURCEID");
/* 3688 */         monType = rs.getString("RESOURCETYPE");
/* 3689 */         baseid = rs.getString("TYPEID");
/* 3690 */         if (rs.wasNull()) {
/* 3691 */           baseid = "-1";
/*      */         }
/* 3693 */         if (resID.equals(resourceid))
/*      */         {
/* 3695 */           MSreq.addParameter("resourceid", resourceid);
/* 3696 */           MSreq.addParameter("resourcetype", monType);
/* 3697 */           if ((baseid != null) && (!baseid.equalsIgnoreCase("null")) && (!baseid.equals("-1"))) {
/* 3698 */             MSreq.addParameter("baseid", baseid);
/*      */           }
/* 3700 */           at.pollNow(null, null, MSreq, response);
/* 3701 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.pollnowsuc.msg"), "4000", true);
/* 3702 */           return outputString;
/*      */         }
/*      */         
/*      */ 
/* 3706 */         AMLog.debug("REST API : Improper resourceid in the request.");
/* 3707 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 3708 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3714 */       AMLog.debug("REST API : Improper resourceid in the request.");
/* 3715 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/* 3716 */       return outputString;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */       String str1;
/* 3721 */       ex.printStackTrace();
/* 3722 */       AMLog.debug("REST API : Server error");
/* 3723 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/* 3724 */       return outputString;
/*      */     }
/*      */     finally
/*      */     {
/* 3728 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String searchMO(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat)
/*      */     throws org.json.JSONException
/*      */   {
/* 3735 */     String responseCode = "4000";
/* 3736 */     String queryString = request.getParameter("query") != null ? request.getParameter("query") : "";
/* 3737 */     String uri = request.getRequestURI();
/* 3738 */     String Filtercondition = (uri.contains("ApmAdminServices")) || (Constants.isIt360) ? "'Network','Node','HAI'" : "'Network','Node'";
/* 3739 */     String monitorTypesFilterCondition = !Constants.sqlManager ? " AND AM_ManagedResourceType.RESOURCETYPE NOT IN (" + Filtercondition + ")" : " AND AM_ManagedObject.TYPE IN ('MSSQL-DB-Server','HAI')";
/* 3740 */     String searchCondition = request.getParameter("searchCondition") != null ? request.getParameter("searchCondition") : "";
/* 3741 */     ResultSet rs = null;
/* 3742 */     String toReturn = "";
/*      */     
/*      */     try
/*      */     {
/* 3746 */       HashMap results = new HashMap();
/* 3747 */       ArrayList<Hashtable<String, String>> searchResults = new ArrayList();
/*      */       
/* 3749 */       HashMap extDeviceMap = null;
/* 3750 */       if (Constants.isExtDeviceConfigured())
/*      */       {
/* 3752 */         if (Constants.isIt360)
/*      */         {
/* 3754 */           extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", true);
/*      */         }
/*      */         else
/*      */         {
/* 3758 */           extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 3765 */         SearchAction searchObj = new SearchAction();
/* 3766 */         String query = searchObj.searchQuery(request, queryString, searchCondition, monitorTypesFilterCondition, true);
/* 3767 */         StringBuilder tempsearch = new StringBuilder();
/* 3768 */         ResultSet resourceidSet = null;
/*      */         try {
/* 3770 */           resourceidSet = AMConnectionPool.executeQueryStmt(query);
/* 3771 */           while (resourceidSet.next())
/*      */           {
/* 3773 */             if (tempsearch.indexOf(resourceidSet.getString("RESOURCEID")) == -1)
/*      */             {
/* 3775 */               tempsearch.append(resourceidSet.getString(1)).append(",");
/*      */             }
/*      */           }
/* 3778 */           tempsearch.append("-1");
/*      */         } catch (Exception ex) {
/* 3780 */           ex.printStackTrace();
/*      */         } finally {
/* 3782 */           if (resourceidSet == null) {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3787 */         String qry = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE, AM_ManagedResourceType.IMAGEPATH, AM_ManagedResourceType.SUBGROUP from AM_ManagedObject, AM_ManagedResourceType where  AM_ManagedResourceType.RESOURCETYPE = AM_ManagedObject.TYPE and AM_ManagedObject.RESOURCEID IN (" + tempsearch.toString() + ")";
/* 3788 */         AMLog.info("REST API: Search Qry:" + qry);
/* 3789 */         rs = AMConnectionPool.executeQueryStmt(qry);
/* 3790 */         while (rs.next())
/*      */         {
/* 3792 */           String resId = rs.getString("RESOURCEID");
/* 3793 */           String resType = rs.getString("TYPE");
/* 3794 */           String monitorDetailsPage = "/showresource.do?resourceid=" + resId + "&method=showResourceForResourceID&PRINTER_FRIENDLY=true";
/* 3795 */           if (resType.equals("HAI"))
/*      */           {
/* 3797 */             monitorDetailsPage = "/showapplication.do?method=showApplication&haid=" + resId + "&PRINTER_FRIENDLY=true";
/*      */           }
/* 3799 */           else if ((extDeviceMap != null) && (extDeviceMap.get(resId) != null))
/*      */           {
/* 3801 */             monitorDetailsPage = (String)extDeviceMap.get(resId);
/*      */           }
/*      */           
/*      */ 
/* 3805 */           Hashtable<String, String> searchInfo = new Hashtable();
/* 3806 */           searchInfo.put("ResourceId", resId);
/* 3807 */           searchInfo.put("DisplayName", rs.getString("DISPLAYNAME"));
/* 3808 */           searchInfo.put("Type", resType);
/* 3809 */           if ("Port-Test".equalsIgnoreCase(resType)) {
/* 3810 */             searchInfo.put("Type", "Service Monitoring");
/*      */           }
/* 3812 */           searchInfo.put("DetailsPageURL", monitorDetailsPage);
/* 3813 */           searchInfo.put("SubGroup", rs.getString("SUBGROUP"));
/* 3814 */           if ((!Constants.sqlManager) && (EnterpriseUtil.isAdminServer())) {
/* 3815 */             searchInfo.put("ManagedServer", CommDBUtil.getManagedServerNameWithPort(resId));
/*      */           }
/* 3817 */           searchInfo.put("ImagePath", rs.getString("IMAGEPATH"));
/*      */           try
/*      */           {
/* 3820 */             HashMap<String, String> healthAvailDetails = getHealthAvailabilityDetails(resId, resType);
/* 3821 */             searchInfo.put("HealthSeverity", healthAvailDetails.get("HEALTHSEVERITY"));
/* 3822 */             searchInfo.put("HealthStatus", healthAvailDetails.get("HEALTHSTATUS"));
/* 3823 */             searchInfo.put("HealthMessage", healthAvailDetails.get("HEALTHMESSAGE"));
/*      */             
/* 3825 */             searchInfo.put("AvailabilitySeverity", healthAvailDetails.get("AVAILABILITYSEVERITY"));
/* 3826 */             searchInfo.put("AvailabilityStatus", healthAvailDetails.get("AVAILABILITYSTATUS"));
/* 3827 */             searchInfo.put("AvailabilityMessage", healthAvailDetails.get("AVAILABILITYMESSAGE"));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 3831 */             AMLog.debug("Error in getting health availability status for resourceid : " + resId);
/* 3832 */             e.printStackTrace();
/*      */           }
/* 3834 */           searchResults.add(searchInfo);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3839 */         responseCode = "4008";
/* 3840 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3844 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/* 3847 */       if (searchResults.size() <= 0)
/*      */       {
/* 3849 */         AMLog.debug("REST API : No Search Results found for the given qry string");
/* 3850 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.noresults.msg", new String[] { queryString }), "4037");
/*      */       }
/*      */       
/*      */ 
/* 3854 */       results.put("response-code", responseCode);
/* 3855 */       results.put("uri", uri);
/* 3856 */       results.put("result", searchResults);
/* 3857 */       results.put("nodeName", "Monitor");
/* 3858 */       results.put("sortingParam", uri.contains("ApmAdminServices") ? "DisplayName" : "AvailabilitySeverity,HealthSeverity,DisplayName");
/* 3859 */       toReturn = getOutputAsString(results, isJsonFormat);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3863 */       e.printStackTrace();
/*      */     }
/* 3865 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static String searchMO(String query)
/*      */   {
/* 3870 */     JSONArray toReturn = new JSONArray();
/* 3871 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3874 */       String qry = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject, CollectData where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and (CollectData.TARGETADDRESS like '%" + query + "%' or CollectData.DISPLAYNAME like '%" + query + "%'  or CollectData.RESOURCETYPE like '%" + query + "%' or AM_ManagedObject.DISPLAYNAME like '%" + query + "%') union select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE from AM_ManagedObject,AM_ManagedResourceType where (AM_ManagedObject.TYPE like '%" + query + "%' or AM_ManagedObject.RESOURCENAME like '%" + query + "%' or AM_ManagedObject.DISPLAYNAME like '%" + query + "%') and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE";
/* 3875 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 3876 */       while (rs.next())
/*      */       {
/* 3878 */         JSONObject searchInfo = new JSONObject();
/* 3879 */         searchInfo.put("resourceid", rs.getString("RESOURCEID"));
/* 3880 */         searchInfo.put("displayname", rs.getString("DISPLAYNAME"));
/* 3881 */         searchInfo.put("type", rs.getString("TYPE"));
/* 3882 */         searchInfo.put("managedServer", CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID")));
/* 3883 */         toReturn.put(searchInfo);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3888 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3892 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 3894 */     return toReturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getAdminMG(String resourceid)
/*      */   {
/* 3901 */     JSONArray mgs = new JSONArray();
/* 3902 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/* 3906 */       String query = "";
/* 3907 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*      */ 
/* 3910 */         query = "select parentid,displayname from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_HOLISTICAPPLICATION_EXT where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID AND AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID AND AM_HOLISTICAPPLICATION_EXT.APP_TYPE NOT IN ('CUSTOMER', 'SITE', 'SLA') AND type='HAI' AND childid=" + resourceid;
/*      */       }
/*      */       else
/*      */       {
/* 3914 */         query = "select parentid,displayname from AM_PARENTCHILDMAPPER,AM_ManagedObject WHERE parentid=resourceid and type='HAI' and childid=" + resourceid;
/*      */       }
/* 3916 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 3918 */       while (rs.next())
/*      */       {
/* 3920 */         JSONObject monitorGroup = new JSONObject();
/* 3921 */         monitorGroup.put("resourceid", rs.getString("PARENTID"));
/* 3922 */         monitorGroup.put("displayname", rs.getString("displayname") + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("PARENTID")));
/* 3923 */         mgs.put(monitorGroup);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3928 */       ex.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 3933 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3936 */     return mgs.toString();
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
/*      */   public static boolean skipRESTAPIKeyValidation(HttpServletRequest request)
/*      */   {
/* 3951 */     String uri = request.getRequestURI();
/* 3952 */     String[] nodes = uri.split("/");
/* 3953 */     if ((nodes[1].equals("AppManager")) && (nodes[2].equals("xml")) && (nodes[3].equals("CustomerSiteHandler")))
/*      */     {
/*      */ 
/* 3956 */       AMLog.debug("Skipping REST API key validation for Customer Sites");
/* 3957 */       return true;
/*      */     }
/* 3959 */     if ((nodes[1].equals("AppManager")) && ((nodes[2].equals("json")) || (nodes[2].equals("xml"))) && (nodes[3].equals("ApmAdminServices")))
/*      */     {
/* 3961 */       if (Constants.isIt360)
/*      */       {
/* 3963 */         MyThreadLocal.setIsOpm(Boolean.valueOf(true));
/*      */       }
/*      */       
/* 3966 */       AMLog.debug("Skipping REST API key validation for APM Admin Services");
/* 3967 */       return true;
/*      */     }
/* 3969 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean skipValidationForPlugin(HttpServletRequest request)
/*      */   {
/* 3974 */     if ((PluginUtil.isPlugin()) && (
/* 3975 */       (request.getRequestURI().contains("Users")) || (request.getRequestURI().contains("ListServer")))) {
/* 3976 */       return true;
/*      */     }
/*      */     
/* 3979 */     return false;
/*      */   }
/*      */   
/* 3982 */   private static String types = Constants.resourceTypes;
/*      */   
/*      */   public static HashMap listGroupMap(HttpServletRequest request) throws Exception {
/* 3985 */     return listGroupMap(request, false);
/*      */   }
/*      */   
/*      */   public static HashMap listGroupMap(HttpServletRequest request, boolean showTreeView) throws Exception
/*      */   {
/* 3990 */     HashMap groupMap = new HashMap();
/* 3991 */     ArrayList<ArrayList<String>> listofGroups = new ArrayList();
/* 3992 */     String operatorCondition = getConditionForOperator(request);
/* 3993 */     operatorCondition = operatorCondition.contains("in") ? " and AM_HOLISTICAPPLICATION.HAID " + operatorCondition.substring(operatorCondition.indexOf("in")) : operatorCondition;
/* 3994 */     AMLog.info("RESTAPI : Operator cond:" + operatorCondition);
/* 3995 */     String listQuery = "select  A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE, A1.DESCRIPTION from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID" + operatorCondition;
/* 3996 */     String mgId = request.getParameter("groupId");
/* 3997 */     String mgName = request.getParameter("groupName");
/* 3998 */     boolean dolistAllMGs = false;
/* 3999 */     if ((mgId == null) && (mgName == null)) {
/* 4000 */       dolistAllMGs = true;
/*      */     }
/* 4002 */     String treeview = request.getParameter("treeview");
/* 4003 */     if ((treeview == null) || (treeview.trim().length() == 0)) {
/* 4004 */       treeview = "default";
/*      */     }
/* 4006 */     if ((!treeview.equals("default")) && (!supportedViews.contains(treeview))) {
/* 4007 */       groupMap.put("applications", listofGroups);
/* 4008 */       groupMap.put("invalid", "groupid");
/* 4009 */       return groupMap;
/*      */     }
/* 4011 */     boolean getOutageReports = (request.getParameter("outageReports") == null) || (request.getParameter("outageReports").equalsIgnoreCase("true"));
/* 4012 */     if (mgName != null) {
/* 4013 */       mgId = Constants.getResourceID(mgName);
/*      */     }
/* 4015 */     if ((mgId != null) && (showTreeView)) {
/* 4016 */       String condn = getTreeViewCondition(mgId, treeview);
/* 4017 */       AMLog.info("RESTAPI : Tree view condition:" + condn);
/* 4018 */       listQuery = listQuery + " and " + condn + " order by A1.DISPLAYNAME,child.DISPLAYNAME";
/* 4019 */     } else if (mgId != null) {
/* 4020 */       listQuery = listQuery + " and AM_HOLISTICAPPLICATION.HAID=" + mgId + "  order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */     } else {
/* 4022 */       listQuery = listQuery + "  order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */     }
/*      */     try
/*      */     {
/* 4026 */       if (Constants.sqlManager)
/*      */       {
/* 4028 */         types = Constants.sqlManagerresourceTypes;
/*      */       }
/* 4030 */       ArrayList<String> MonitorsinMGs = new ArrayList();
/* 4031 */       ArrayList<ArrayList<String>> list = new ArrayList(5);
/* 4032 */       AMLog.info("REST API: ListMonitorGroups Query:-->" + listQuery);
/* 4033 */       list = mo.getRows(listQuery);
/* 4034 */       if (list.size() == 0) {
/* 4035 */         groupMap.put("applications", listofGroups);
/* 4036 */         groupMap.put("invalid", "groupid");
/* 4037 */         return groupMap;
/*      */       }
/* 4039 */       Hashtable childMOs = new Hashtable();
/* 4040 */       Hashtable<String, ArrayList<ArrayList<String>>> childMOsforMG = new Hashtable();
/* 4041 */       for (int j = 0; j < list.size(); j++)
/*      */       {
/* 4043 */         ArrayList<String> singlerow = (ArrayList)list.get(j);
/* 4044 */         String resourcename = (String)singlerow.get(0);
/* 4045 */         String displayname = (String)singlerow.get(1);
/* 4046 */         String childactionstatus = (String)singlerow.get(2);
/* 4047 */         String owner = (String)singlerow.get(3);
/* 4048 */         String CREATIONDATE = (String)singlerow.get(4);
/* 4049 */         String MODIFIEDDATE = (String)singlerow.get(5);
/* 4050 */         String MGresourceid = (String)singlerow.get(6);
/* 4051 */         if (MGresourceid != null)
/*      */         {
/* 4053 */           int residint = Integer.parseInt(MGresourceid);
/* 4054 */           if ((EnterpriseUtil.isAdminServer()) && (residint > EnterpriseUtil.RANGE))
/*      */           {
/* 4056 */             displayname = displayname + "_" + CommDBUtil.getManagedServerNameWithPort(MGresourceid);
/*      */           }
/*      */         }
/* 4059 */         String unmanagednodes = (String)singlerow.get(7);
/* 4060 */         String childid = (String)singlerow.get(8);
/* 4061 */         String childname = (String)singlerow.get(9);
/* 4062 */         if (childid != null)
/*      */         {
/* 4064 */           int residint = Integer.parseInt(childid);
/* 4065 */           if ((EnterpriseUtil.isAdminServer()) && (residint > EnterpriseUtil.RANGE))
/*      */           {
/* 4067 */             childname = childname + "_" + CommDBUtil.getManagedServerNameWithPort(childid);
/*      */           }
/*      */         }
/* 4070 */         String childtype = (String)singlerow.get(10);
/* 4071 */         String MGactionstatus = (String)singlerow.get(11);
/* 4072 */         String imagepath = (String)singlerow.get(12);
/* 4073 */         String shortname = (String)singlerow.get(13);
/* 4074 */         String unmanageChildmos = (String)singlerow.get(14);
/*      */         
/* 4076 */         String MGType = (String)singlerow.get(15);
/* 4077 */         String MGDescription = (String)singlerow.get(16);
/* 4078 */         MonitorsinMGs.add(childid);
/*      */         
/* 4080 */         if ((childMOs.containsKey(MGresourceid)) || (childMOsforMG.containsKey(MGresourceid)))
/*      */         {
/* 4082 */           ArrayList<ArrayList<String>> childmo = null;
/* 4083 */           if (childtype != null)
/*      */           {
/*      */ 
/*      */ 
/* 4087 */             if (childtype.equals("HAI"))
/*      */             {
/* 4089 */               if (childMOsforMG.get(MGresourceid) != null)
/*      */               {
/* 4091 */                 childmo = (ArrayList)childMOsforMG.get(MGresourceid);
/*      */               }
/*      */               else
/*      */               {
/* 4095 */                 childmo = new ArrayList();
/* 4096 */                 childMOsforMG.put(MGresourceid, childmo);
/*      */               }
/*      */               
/*      */ 
/*      */             }
/* 4101 */             else if (childMOs.get(MGresourceid) != null)
/*      */             {
/* 4103 */               childmo = (ArrayList)childMOs.get(MGresourceid);
/*      */             }
/*      */             else
/*      */             {
/* 4107 */               childmo = new ArrayList();
/* 4108 */               childMOs.put(MGresourceid, childmo);
/*      */             }
/*      */             
/* 4111 */             ArrayList<String> singrow = new ArrayList();
/* 4112 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null))
/*      */             {
/* 4114 */               singrow.add(childid);
/* 4115 */               singrow.add(childname);
/* 4116 */               singrow.add(childtype);
/* 4117 */               singrow.add(imagepath);
/* 4118 */               singrow.add(shortname);
/* 4119 */               singrow.add(unmanageChildmos);
/* 4120 */               singrow.add(childactionstatus);
/* 4121 */               childmo.add(singrow);
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 4126 */           ArrayList<ArrayList<String>> childmo1 = new ArrayList();
/* 4127 */           ArrayList<String> singrow = new ArrayList();
/*      */           
/* 4129 */           if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*      */           {
/* 4131 */             singrow.add(childid);
/* 4132 */             singrow.add(childname);
/* 4133 */             singrow.add(childtype);
/* 4134 */             singrow.add(imagepath);
/* 4135 */             singrow.add(shortname);
/* 4136 */             singrow.add(unmanageChildmos);
/* 4137 */             singrow.add(childactionstatus);
/* 4138 */             childmo1.add(singrow);
/* 4139 */             if (childtype.equals("HAI"))
/*      */             {
/* 4141 */               childMOsforMG.put(MGresourceid, childmo1);
/*      */             }
/*      */             else
/*      */             {
/* 4145 */               childMOs.put(MGresourceid, childmo1);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 4150 */             ArrayList dummylist = new ArrayList();
/* 4151 */             childMOs.put(MGresourceid, dummylist);
/*      */           }
/*      */           
/* 4154 */           ArrayList<String> singlemonitorgroup = new ArrayList();
/*      */           
/* 4156 */           singlemonitorgroup.add(resourcename);
/* 4157 */           singlemonitorgroup.add(displayname);
/* 4158 */           singlemonitorgroup.add(MGType);
/* 4159 */           singlemonitorgroup.add(owner);
/* 4160 */           singlemonitorgroup.add(CREATIONDATE);
/* 4161 */           singlemonitorgroup.add(MODIFIEDDATE);
/* 4162 */           singlemonitorgroup.add(MGresourceid);
/* 4163 */           singlemonitorgroup.add("HAI");
/* 4164 */           singlemonitorgroup.add(unmanagednodes);
/* 4165 */           singlemonitorgroup.add(MGactionstatus);
/* 4166 */           singlemonitorgroup.add(MGDescription);
/* 4167 */           if (getOutageReports) {
/* 4168 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("outages"));
/*      */             
/* 4170 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("availUnknownCount"));
/* 4171 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("upCount"));
/* 4172 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("downCount"));
/* 4173 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("healthUnknownCount"));
/* 4174 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("clearCount"));
/* 4175 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("warningCount"));
/* 4176 */             singlemonitorgroup.add(getOutagesMap(MGresourceid).get("criticalCount"));
/*      */           }
/* 4178 */           listofGroups.add(singlemonitorgroup);
/*      */         }
/*      */       }
/* 4181 */       Hashtable<String, ArrayList<ArrayList<String>>> childlist = new Hashtable();
/*      */       try
/*      */       {
/* 4184 */         for (int k = 0; k < listofGroups.size(); k++)
/*      */         {
/* 4186 */           ArrayList<String> singlerow = (ArrayList)listofGroups.get(k);
/* 4187 */           String tempid = (String)singlerow.get(6);
/* 4188 */           ArrayList<ArrayList<String>> mosinOrder = new ArrayList();
/* 4189 */           if (childMOsforMG.get(tempid) != null)
/*      */           {
/* 4191 */             mosinOrder = (ArrayList)childMOsforMG.get(tempid);
/*      */           }
/* 4193 */           if (childMOs.get(tempid) != null)
/*      */           {
/* 4195 */             ArrayList<ArrayList<String>> monitors = (ArrayList)childMOs.get(tempid);
/* 4196 */             for (int w = 0; w < monitors.size(); w++)
/*      */             {
/* 4198 */               mosinOrder.add(monitors.get(w));
/*      */             }
/*      */           }
/*      */           
/* 4202 */           if ((mosinOrder != null) && (mosinOrder.size() > 0))
/*      */           {
/* 4204 */             childlist.put(tempid, mosinOrder);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 4210 */         ex.printStackTrace();
/*      */       }
/* 4212 */       removeUnwantedMGs(listofGroups, treeview, dolistAllMGs);
/* 4213 */       groupMap.put("applications", listofGroups);
/* 4214 */       groupMap.put("childlist", childlist);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4218 */       ex.printStackTrace();
/*      */     }
/* 4220 */     return groupMap;
/*      */   }
/*      */   
/*      */   private static void removeUnwantedMGs(ArrayList<ArrayList<String>> listofGroups, String treeview, boolean dolistAllMGs)
/*      */   {
/* 4225 */     ArrayList<String> mgIds = new ArrayList();
/* 4226 */     ArrayList<String> childIds = new ArrayList();
/* 4227 */     ArrayList<ArrayList<String>> mgsToRemove = new ArrayList();
/*      */     
/*      */ 
/* 4230 */     for (ArrayList<String> mgInfo : listofGroups)
/*      */     {
/* 4232 */       mgIds.add(mgInfo.get(6));
/*      */     }
/* 4234 */     Vector<String> allChilds = new Vector();
/* 4235 */     for (String mgID : mgIds)
/*      */     {
/*      */ 
/* 4238 */       getChildIDs(allChilds, mgID);
/*      */     }
/*      */     
/* 4241 */     for (ArrayList<String> monitorGroupInfo : listofGroups)
/*      */     {
/* 4243 */       String monitorGroupId = (String)monitorGroupInfo.get(6);
/* 4244 */       String monitorType = (String)monitorGroupInfo.get(2);
/*      */       
/* 4246 */       if (allChilds.contains(monitorGroupId))
/*      */       {
/* 4248 */         mgsToRemove.add(monitorGroupInfo);
/*      */       }
/* 4250 */       else if ((monitorType.equals("1")) && (!allChilds.contains(monitorGroupId)) && (!"bottomlevel".equalsIgnoreCase(treeview)) && (dolistAllMGs))
/*      */       {
/* 4252 */         mgsToRemove.add(monitorGroupInfo);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 4258 */     for (ArrayList<String> mgToRemove : mgsToRemove)
/*      */     {
/* 4260 */       listofGroups.remove(mgToRemove);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void getAllChildNodestoDisplay(String mgId, ArrayList singlechilmos, String resIdTOCheck, Hashtable childmos, int level, Hashtable monitorGroup, HashMap<String, Properties> mgAvail, boolean getOutageReports, boolean getSeverityDetails, boolean includeMonitors) throws Exception
/*      */   {
/* 4266 */     HashMap healthAvailEntitys = null;
/* 4267 */     ArrayList<Hashtable> subMonGrpList = new ArrayList();
/* 4268 */     ArrayList<Hashtable> monitorsList = new ArrayList();
/* 4269 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 4271 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 4272 */       String childresid = (String)singlerow.get(0);
/* 4273 */       String childresname = (String)singlerow.get(1);
/* 4274 */       String childtype = (String)singlerow.get(2) + "";
/* 4275 */       String imagePath = (String)singlerow.get(3);
/* 4276 */       String shortname = (String)singlerow.get(4) + "";
/* 4277 */       String childactionstatus = singlerow.get(6) != null ? (String)singlerow.get(6) + "" : "0";
/* 4278 */       if (childactionstatus.equals("1"))
/*      */       {
/* 4280 */         childactionstatus = "enabled";
/*      */       }
/*      */       else
/*      */       {
/* 4284 */         childactionstatus = "disabled";
/*      */       }
/* 4286 */       int spacing = 0;
/* 4287 */       if (level >= 1)
/*      */       {
/* 4289 */         spacing = 1 * level;
/*      */       }
/* 4291 */       if ((childtype.equals("HAI")) || (includeMonitors))
/*      */       {
/* 4293 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 4294 */         Hashtable SubMonitorGroup = new Hashtable();
/* 4295 */         String type = childtype.equals("HAI") ? "Sub Group" : childtype;
/* 4296 */         SubMonitorGroup.put("NAME", childresname);
/* 4297 */         SubMonitorGroup.put("TYPE", type);
/* 4298 */         SubMonitorGroup.put("DISPLAYNAME", childresname);
/* 4299 */         SubMonitorGroup.put("RESOURCEID", childresid);
/* 4300 */         SubMonitorGroup.put("Action", childactionstatus);
/* 4301 */         SubMonitorGroup.put("IMAGEPATH", imagePath);
/* 4302 */         SubMonitorGroup.put("DetailsPageURL", "/showresource.do?method=showResourceForResourceID&resourceid=" + childresid + "&method=showApplication");
/* 4303 */         if ((mgId != null) && (mgId.equals(childresid))) {
/* 4304 */           SubMonitorGroup.put("SELECTED", "true");
/*      */         }
/* 4306 */         if (getSeverityDetails) {
/* 4307 */           healthAvailEntitys = getHealthAvailabilityDetails(childresid, childtype);
/* 4308 */           SubMonitorGroup.put("MODTIME", healthAvailEntitys.get("HEALTHMODTIME"));
/* 4309 */           SubMonitorGroup.put("HEALTHSEVERITY", "" + healthAvailEntitys.get("HEALTHSEVERITY"));
/* 4310 */           SubMonitorGroup.put("HEALTHSTATUS", "" + healthAvailEntitys.get("HEALTHSTATUS"));
/* 4311 */           SubMonitorGroup.put("HEALTHMESSAGE", "" + healthAvailEntitys.get("HEALTHMESSAGE"));
/* 4312 */           SubMonitorGroup.put("HealthRCAURL", "/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=18");
/* 4313 */           SubMonitorGroup.put("AVAILABILITYSEVERITY", "" + healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 4314 */           SubMonitorGroup.put("AVAILABILITYSTATUS", "" + healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 4315 */           SubMonitorGroup.put("AVAILABILITYMESSAGE", "" + healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 4316 */           SubMonitorGroup.put("AvailabilityRCAURL", "/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=17");
/*      */         }
/* 4318 */         if (getOutageReports) {
/* 4319 */           String todaysAvail = "100";
/* 4320 */           String todaysSchdDownTime = "0";
/* 4321 */           String todaysUnmng = "0";
/* 4322 */           String todaysUnAvail = "0";
/* 4323 */           if ((!childtype.equals("HAI")) && (!mgAvail.containsKey(childresid))) {
/* 4324 */             Properties availProps = ReportUtilities.getTodaysAvailabilityForMonitors(childresid);
/* 4325 */             if (availProps != null) {
/* 4326 */               mgAvail.put(childresid, availProps);
/*      */             }
/*      */           }
/* 4329 */           if (mgAvail.containsKey(childresid)) {
/* 4330 */             Properties mgProps = (Properties)mgAvail.get(childresid);
/* 4331 */             todaysAvail = mgProps.getProperty("available");
/* 4332 */             todaysSchdDownTime = mgProps.getProperty("ServicesSchPercent");
/* 4333 */             todaysUnmng = mgProps.getProperty("ServicesUnMgPercent");
/* 4334 */             todaysUnAvail = mgProps.getProperty("unavailable");
/*      */           }
/* 4336 */           if (todaysAvail != null) {
/* 4337 */             SubMonitorGroup.put("TODAYAVAILPERCENT", todaysAvail);
/*      */           }
/* 4339 */           if (todaysSchdDownTime != null) {
/* 4340 */             SubMonitorGroup.put("TODAYSCHEDDOWNPERCENT", todaysSchdDownTime);
/*      */           }
/* 4342 */           if (todaysUnmng != null) {
/* 4343 */             SubMonitorGroup.put("TODAYUNMANGDPERCENT", todaysUnmng);
/*      */           }
/* 4345 */           if (todaysUnAvail != null) {
/* 4346 */             SubMonitorGroup.put("TODAYUNAVAILPERCENT", todaysUnAvail);
/*      */           }
/*      */         }
/* 4349 */         if (childmos.get(String.valueOf(childresid)) != null)
/*      */         {
/* 4351 */           getAllChildNodestoDisplay(mgId, singlechilmos1, childresid, childmos, level + 1, SubMonitorGroup, mgAvail, getOutageReports, getSeverityDetails, includeMonitors);
/*      */         }
/*      */         
/* 4354 */         if (childtype.equals("HAI"))
/*      */         {
/* 4356 */           subMonGrpList.add(SubMonitorGroup);
/*      */         }
/*      */         else
/*      */         {
/* 4360 */           monitorsList.add(SubMonitorGroup);
/*      */         }
/*      */       }
/*      */     }
/* 4364 */     if (subMonGrpList.size() > 0)
/*      */     {
/* 4366 */       monitorGroup.put("SubMonitorGroup", subMonGrpList);
/*      */     }
/* 4368 */     if ((monitorsList.size() > 0) && (includeMonitors))
/*      */     {
/* 4370 */       monitorGroup.put("Monitors", monitorsList);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getOutputAsString(HashMap results, boolean isJsonFormat) throws Exception
/*      */   {
/* 4376 */     String toReturn = "";
/* 4377 */     String responseCode = (String)results.get("response-code");
/* 4378 */     String uri = (String)results.get("uri");
/* 4379 */     String sortByParam = results.get("sortingParam") != null ? (String)results.get("sortingParam") : "DISPLAYNAME";
/* 4380 */     boolean needSorting = results.get("skipSorting") == null;
/* 4381 */     String parentNodeName = (String)results.get("parentNodeName");
/* 4382 */     String nodeName = (String)results.get("nodeName");
/* 4383 */     String subNodeName = results.get("subNodeName") != null ? (String)results.get("subNodeName") : "-1";
/* 4384 */     ArrayList<String> subNodes = new ArrayList();
/* 4385 */     if (subNodeName.contains(","))
/*      */     {
/* 4387 */       StringTokenizer st = new StringTokenizer(subNodeName, ",");
/* 4388 */       while (st.hasMoreTokens())
/*      */       {
/* 4390 */         subNodes.add(st.nextToken());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 4395 */       subNodes.add(subNodeName);
/*      */     }
/* 4397 */     ArrayList<Hashtable> result = (ArrayList)results.get("result");
/* 4398 */     ArrayList<Hashtable> sortedResult = needSorting ? getSortedList(result, sortByParam) : result;
/* 4399 */     String sortingOrder = (String)results.get("sortingOrder");
/* 4400 */     if ((sortingOrder != null) && (sortingOrder.equals("desc")))
/*      */     {
/* 4402 */       java.util.Collections.reverse(sortedResult);
/*      */     }
/*      */     
/* 4405 */     if (isJsonFormat)
/*      */     {
/* 4407 */       JSONObject jsonOutput = new JSONObject();
/* 4408 */       JSONObject jsonResponse = new JSONObject();
/* 4409 */       JSONArray jsonResultArray = new JSONArray();
/*      */       
/*      */       try
/*      */       {
/* 4413 */         for (int i = 0; i < sortedResult.size(); i++)
/*      */         {
/* 4415 */           jsonResultArray.put(new JSONObject((Map)sortedResult.get(i)));
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4420 */         AMLog.debug("REST API : Error in generating JSON Feed!");
/* 4421 */         responseCode = "40xx";
/* 4422 */         jsonResultArray = new JSONArray();
/* 4423 */         jsonResultArray.put(new JSONObject().put("message", FormatUtil.getString("Error in generating JSON Feed!")));
/* 4424 */         e.printStackTrace();
/*      */       }
/* 4426 */       jsonOutput.put("response-code", responseCode);
/* 4427 */       jsonResponse.put("uri", uri);
/* 4428 */       jsonResponse.put("result", jsonResultArray);
/* 4429 */       jsonOutput.put("response", jsonResponse);
/*      */       
/* 4431 */       toReturn = jsonOutput.toString();
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/* 4437 */         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
/* 4438 */         DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
/* 4439 */         Document doc = docBuilder.newDocument();
/*      */         
/* 4441 */         AMLog.debug("REST API : root node uri" + uri);
/* 4442 */         Element rootNode = doc.createElement("AppManager-response");
/* 4443 */         rootNode.setAttribute("uri", uri);
/* 4444 */         doc.appendChild(rootNode);
/* 4445 */         Element resultNode = doc.createElement("result");
/* 4446 */         rootNode.appendChild(resultNode);
/*      */         
/* 4448 */         Element responseNode = doc.createElement("response");
/* 4449 */         responseNode.setAttribute("response-code", responseCode);
/* 4450 */         resultNode.appendChild(responseNode);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 4455 */         Element parentNode = null;
/* 4456 */         if (parentNodeName != null)
/*      */         {
/* 4458 */           parentNode = doc.createElement(parentNodeName);
/* 4459 */           responseNode.appendChild(parentNode);
/*      */         }
/*      */         
/* 4462 */         for (int i = 0; i < result.size(); i++)
/*      */         {
/* 4464 */           Element nodeElement = doc.createElement(nodeName);
/* 4465 */           ArrayList<String> keys = new ArrayList(((Hashtable)result.get(i)).keySet());
/*      */           
/* 4467 */           for (int k = 0; k < keys.size(); k++)
/*      */           {
/* 4469 */             if (!((Hashtable)result.get(i)).isEmpty())
/*      */             {
/* 4471 */               if (subNodes.contains(keys.get(k)))
/*      */               {
/* 4473 */                 AMLog.info("REST API: XML Creation: SUBNODE:" + (String)keys.get(k));
/* 4474 */                 ArrayList<Hashtable> subNodeList = (ArrayList)((Hashtable)result.get(i)).get(keys.get(k));
/* 4475 */                 appendSubNodes(subNodeList, (String)keys.get(k), nodeElement, doc);
/*      */               }
/*      */               else
/*      */               {
/* 4479 */                 AMLog.info("REST API: XML Creation: key:" + (String)keys.get(k) + "\tValue:" + ((Hashtable)result.get(i)).get(keys.get(k)));
/* 4480 */                 nodeElement.setAttribute((String)keys.get(k), (String)((Hashtable)result.get(i)).get(keys.get(k)));
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 4485 */           if (parentNodeName != null)
/*      */           {
/* 4487 */             parentNode.appendChild(nodeElement);
/*      */           }
/*      */           else
/*      */           {
/* 4491 */             responseNode.appendChild(nodeElement);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 4498 */         TransformerFactory factory = TransformerFactory.newInstance();
/* 4499 */         Transformer transformer = factory.newTransformer();
/* 4500 */         transformer.setOutputProperty("indent", "yes");
/* 4501 */         StringWriter sw = new StringWriter();
/* 4502 */         StreamResult streamResult = new StreamResult(sw);
/* 4503 */         DOMSource source = new DOMSource(doc);
/* 4504 */         transformer.transform(source, streamResult);
/* 4505 */         toReturn = sw.toString();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4509 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 4512 */     return toReturn;
/*      */   }
/*      */   
/*      */   private static void appendSubNodes(ArrayList<Hashtable> subNodeList, String subNodeName, Element nodeElement, Document doc)
/*      */   {
/* 4517 */     if ((subNodeList == null) || (subNodeList.size() < 1))
/*      */     {
/* 4519 */       return;
/*      */     }
/*      */     
/* 4522 */     for (int i = 0; i < subNodeList.size(); i++)
/*      */     {
/* 4524 */       if (!((Hashtable)subNodeList.get(i)).isEmpty())
/*      */       {
/* 4526 */         Element subNodeElement = doc.createElement(subNodeName);
/* 4527 */         ArrayList<String> keys = new ArrayList(((Hashtable)subNodeList.get(i)).keySet());
/*      */         
/* 4529 */         for (int k = 0; k < keys.size(); k++)
/*      */         {
/* 4531 */           if ((((String)keys.get(k)).equals(subNodeName)) && (((Hashtable)subNodeList.get(i)).get(subNodeName) != null))
/*      */           {
/* 4533 */             AMLog.info("REST API: append subnodes:" + subNodeName);
/* 4534 */             ArrayList<Hashtable> newSubNodeList = (ArrayList)((Hashtable)subNodeList.get(i)).get(subNodeName);
/* 4535 */             appendSubNodes(newSubNodeList, subNodeName, subNodeElement, doc);
/*      */           }
/*      */           else
/*      */           {
/*      */             try
/*      */             {
/* 4541 */               AMLog.info("REST API: append key:" + (String)keys.get(k) + " value:" + ((Hashtable)subNodeList.get(i)).get(keys.get(k)));
/* 4542 */               subNodeElement.setAttribute((String)keys.get(k), (String)((Hashtable)subNodeList.get(i)).get(keys.get(k)));
/*      */             }
/*      */             catch (ClassCastException cce)
/*      */             {
/* 4546 */               if ((((Hashtable)subNodeList.get(i)).get(keys.get(k)) instanceof Hashtable))
/*      */               {
/* 4548 */                 AMLog.info("REST API: Found Hashtable :" + ((Hashtable)subNodeList.get(i)).get(keys.get(k)));
/* 4549 */                 ArrayList<Hashtable> newSubNodeList = new ArrayList();
/* 4550 */                 newSubNodeList.add((Hashtable)((Hashtable)subNodeList.get(i)).get(keys.get(k)));
/* 4551 */                 appendSubNodes(newSubNodeList, (String)keys.get(k), subNodeElement, doc);
/*      */               }
/* 4553 */               else if ((((Hashtable)subNodeList.get(i)).get(keys.get(k)) instanceof ArrayList))
/*      */               {
/* 4555 */                 AMLog.info("REST API: Found ArrayList :" + ((Hashtable)subNodeList.get(i)).get(keys.get(k)));
/* 4556 */                 appendSubNodes((ArrayList)((Hashtable)subNodeList.get(i)).get(keys.get(k)), (String)keys.get(k), subNodeElement, doc);
/*      */               }
/*      */               else
/*      */               {
/* 4560 */                 cce.printStackTrace();
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 4565 */               String key = (String)keys.get(k);
/* 4566 */               String val = (String)((Hashtable)subNodeList.get(i)).get(key);
/*      */               try
/*      */               {
/* 4569 */                 if (key.contains(" "))
/*      */                 {
/* 4571 */                   if (key.contains("("))
/*      */                   {
/* 4573 */                     val = val + key.substring(key.indexOf("(") - 1);
/* 4574 */                     key = key.substring(0, key.indexOf("(") - 1);
/*      */                   }
/* 4576 */                   key = key.replaceAll(" ", "_");
/* 4577 */                   subNodeElement.setAttribute(key, val);
/*      */                 }
/*      */                 else
/*      */                 {
/* 4581 */                   AMLog.info("REST API: Errrrrrr: append key:" + key + " value:" + val);
/*      */                 }
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/* 4586 */                 AMLog.info("REST API: Errrrrrr: append key:" + key + " value:" + val);
/* 4587 */                 ex.printStackTrace();
/* 4588 */                 break;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 4593 */         nodeElement.appendChild(subNodeElement);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static HashMap<String, String> getHealthAvailabilityDetails(String resID, String resType) throws Exception
/*      */   {
/* 4600 */     return CommDBUtil.getHealthAvailabilityDetails(resID, resType);
/*      */   }
/*      */   
/*      */   public static String getMGDetails(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4605 */     String outputString = "";
/* 4606 */     String uri = request.getRequestURI();
/* 4607 */     boolean isJsonFormat = uri.contains("json");
/* 4608 */     if (isJsonFormat) {
/* 4609 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 4611 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 4613 */     HashMap mgDetails = new HashMap();
/* 4614 */     ArrayList<Hashtable<?, ?>> mgList = new ArrayList();
/* 4615 */     mgDetails.put("uri", uri);
/* 4616 */     mgDetails.put("response-code", "4000");
/* 4617 */     mgDetails.put("sortingParam", "HEALTHSEVERITY,AVAILABILITYSEVERITY,DISPLAYNAME,RESOURCEID");
/* 4618 */     mgDetails.put("parentNodeName", "MonitorGroups");
/* 4619 */     mgDetails.put("nodeName", "MonitorGroup");
/* 4620 */     mgDetails.put("subNodeName", "SubMonitorGroup,Monitors");
/*      */     
/* 4622 */     HashMap groupdetail = listGroupMap(request);
/* 4623 */     if (groupdetail.get("invalid") != null)
/*      */     {
/* 4625 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.groupid.notassociated.msg"), "4510");
/* 4626 */       return outputString;
/*      */     }
/* 4628 */     HashMap<String, Properties> mgAvail = null;
/* 4629 */     HashMap<String, String> healthAvailEntitys = null;
/* 4630 */     ArrayList<ArrayList<String>> firstLevelGrouplist = (ArrayList)groupdetail.get("applications");
/* 4631 */     Hashtable chilmos = (Hashtable)groupdetail.get("childlist");
/* 4632 */     String mgId = request.getParameter("groupId");
/* 4633 */     String mgName = request.getParameter("groupName");
/* 4634 */     boolean getOutageReports = (request.getParameter("outageReports") == null) || (request.getParameter("outageReports").equalsIgnoreCase("true"));
/* 4635 */     boolean getSeverityDetails = (request.getParameter("severityDetails") == null) || (request.getParameter("severityDetails").equalsIgnoreCase("true"));
/* 4636 */     if (mgName != null) {
/* 4637 */       mgId = Constants.getResourceID(mgName);
/*      */     }
/* 4639 */     for (int i = 0; i < firstLevelGrouplist.size(); i++) {
/* 4640 */       Hashtable monGrp = new Hashtable();
/*      */       
/* 4642 */       ArrayList mgAList = (ArrayList)firstLevelGrouplist.get(i);
/* 4643 */       String monitorGroupName = mgAList.get(0) != null ? (String)mgAList.get(0) : "";
/* 4644 */       String monitorGroupType = "0".equals(mgAList.get(2)) ? "Monitor Group" : "Sub Group";
/* 4645 */       String restitle = mgAList.get(8) != null ? (String)mgAList.get(1) + "-UnManaged" : (String)mgAList.get(1);
/* 4646 */       String monitorGroupresid = mgAList.get(6) != null ? (String)mgAList.get(6) : "";
/* 4647 */       String monitorGroupActionStatus = mgAList.get(9) != null ? "disabled" : ((String)mgAList.get(9)).equals("1") ? "enabled" : "disabled";
/* 4648 */       String DetailsPageURL = "/showresource.do?method=showResourceForResourceID&resourceid=" + monitorGroupresid + "&method=showApplication";
/* 4649 */       if (!monitorGroupresid.equals("orphaned")) {
/* 4650 */         monGrp.put("NAME", monitorGroupName);
/* 4651 */         monGrp.put("Type", monitorGroupType);
/* 4652 */         monGrp.put("DISPLAYNAME", restitle);
/* 4653 */         monGrp.put("RESOURCEID", monitorGroupresid);
/* 4654 */         monGrp.put("Action", monitorGroupActionStatus);
/* 4655 */         monGrp.put("DetailsPageURL", DetailsPageURL);
/* 4656 */         if (getSeverityDetails) {
/* 4657 */           healthAvailEntitys = getHealthAvailabilityDetails(monitorGroupresid, "HAI");
/* 4658 */           monGrp.put("HEALTHSEVERITY", "" + (String)healthAvailEntitys.get("HEALTHSEVERITY"));
/* 4659 */           monGrp.put("HEALTHSTATUS", "" + (String)healthAvailEntitys.get("HEALTHSTATUS"));
/* 4660 */           monGrp.put("HEALTHMESSAGE", "" + (String)healthAvailEntitys.get("HEALTHMESSAGE"));
/* 4661 */           monGrp.put("HealthRCAURL", "/jsp/RCA.jsp?resourceid=" + monitorGroupresid + "&attributeid=18");
/* 4662 */           monGrp.put("AVAILABILITYSEVERITY", "" + (String)healthAvailEntitys.get("AVAILABILITYSEVERITY"));
/* 4663 */           monGrp.put("AVAILABILITYSTATUS", "" + (String)healthAvailEntitys.get("AVAILABILITYSTATUS"));
/* 4664 */           monGrp.put("AVAILABILITYMESSAGE", "" + (String)healthAvailEntitys.get("AVAILABILITYMESSAGE"));
/* 4665 */           monGrp.put("AvailabilityRCAURL", "/jsp/RCA.jsp?resourceid=" + monitorGroupresid + "&attributeid=17");
/* 4666 */           monGrp.put("MODTIME", healthAvailEntitys.get("HEALTHMODTIME"));
/*      */         }
/* 4668 */         if (getOutageReports) {
/* 4669 */           String todaysAvail = "100";
/* 4670 */           String todaysSchdDownTime = "0";
/* 4671 */           String todaysUnmng = "0";
/* 4672 */           String todaysUnAvail = "0";
/* 4673 */           mgAvail = (HashMap)ReportUtilities.getTodaysAvailabilityForAllMonitorGroups();
/* 4674 */           if ((mgAvail != null) && (mgAvail.containsKey(monitorGroupresid))) {
/* 4675 */             Properties mgProps = (Properties)mgAvail.get(monitorGroupresid);
/* 4676 */             todaysAvail = mgProps.getProperty("available");
/* 4677 */             todaysSchdDownTime = mgProps.getProperty("ServicesSchPercent");
/* 4678 */             todaysUnmng = mgProps.getProperty("ServicesUnMgPercent");
/* 4679 */             todaysUnAvail = mgProps.getProperty("unavailable");
/*      */           }
/* 4681 */           monGrp.put("TODAYAVAILPERCENT", todaysAvail);
/* 4682 */           monGrp.put("TODAYSCHEDDOWNPERCENT", todaysSchdDownTime);
/* 4683 */           monGrp.put("TODAYUNMANGDPERCENT", todaysUnmng);
/* 4684 */           monGrp.put("TODAYUNAVAILPERCENT", todaysUnAvail);
/*      */         }
/*      */       }
/* 4687 */       if (chilmos.get(monitorGroupresid + "") != null)
/*      */       {
/* 4689 */         ArrayList singlechilmos = (ArrayList)chilmos.get(monitorGroupresid + "");
/*      */         try {
/* 4691 */           getAllChildNodestoDisplay(mgId, singlechilmos, monitorGroupresid, chilmos, 1, monGrp, mgAvail, getOutageReports, getSeverityDetails, true);
/*      */         }
/*      */         catch (Exception e) {
/* 4694 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 4698 */       mgList.add(monGrp);
/*      */     }
/* 4700 */     mgDetails.put("result", mgList);
/* 4701 */     outputString = getOutputAsString(mgDetails, isJsonFormat);
/*      */     
/* 4703 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String getOutagesForMG(String monitorGroupresid)
/*      */   {
/* 4708 */     int TotalCnt = 0;
/* 4709 */     int severeCnt = 0;
/*      */     try
/*      */     {
/* 4712 */       ManagedApplication mo = new ManagedApplication();
/* 4713 */       ArrayList<ArrayList<String>> childResids = mo.getRows("select Childid, max(AM_ManagedObject.type) as Type  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID left outer join Alert on childid = source where AM_PARENTCHILDMAPPER.PARENTID=" + monitorGroupresid + " and COALESCE(groupname,'AppManager')!='AppManager_Component' group by childid,groupname,severity");
/* 4714 */       TotalCnt = childResids.size();
/* 4715 */       if (TotalCnt == 0)
/*      */       {
/* 4717 */         return "0/0";
/*      */       }
/* 4719 */       for (int i = 0; i < childResids.size(); i++)
/*      */       {
/* 4721 */         String childResid = (String)((ArrayList)childResids.get(i)).get(0);
/* 4722 */         String childResType = (String)((ArrayList)childResids.get(i)).get(1);
/* 4723 */         HashMap<String, String> healthAvailEntitys = getHealthAvailabilityDetails(childResid, childResType);
/* 4724 */         String severity = (String)healthAvailEntitys.get("HEALTHSEVERITY");
/* 4725 */         if ((severity.equals("1")) || (severity.equals("4")))
/*      */         {
/* 4727 */           severeCnt++;
/*      */         }
/*      */       }
/* 4730 */       return severeCnt + "/" + TotalCnt;
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/* 4734 */     return "0/0";
/*      */   }
/*      */   
/*      */ 
/*      */   private static HashMap<String, String> getOutagesMap(String monitorGroupresid)
/*      */   {
/* 4740 */     int TotalCnt = 0;int severeCnt = 0;int clearCount = 0;int warningCount = 0;int criticalCount = 0;int upCount = 0;int downCount = 0;int healthUnknownCount = 0;int availUnknownCount = 0;
/* 4741 */     String outages = null;
/* 4742 */     HashMap<String, String> outagesMap = new HashMap();
/*      */     
/*      */     try
/*      */     {
/* 4746 */       ManagedApplication mo = new ManagedApplication();
/* 4747 */       ArrayList<ArrayList<String>> childResids = mo.getRows("select Childid, max(AM_ManagedObject.type) as Type  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID left outer join Alert on childid = source where AM_PARENTCHILDMAPPER.PARENTID=" + monitorGroupresid + " and COALESCE(groupname,'AppManager')!='AppManager_Component' group by childid,groupname,severity");
/* 4748 */       TotalCnt = childResids.size();
/* 4749 */       if (TotalCnt == 0)
/*      */       {
/* 4751 */         outages = "0/0";
/*      */       }
/* 4753 */       for (int i = 0; i < childResids.size(); i++)
/*      */       {
/* 4755 */         String childResid = (String)((ArrayList)childResids.get(i)).get(0);
/* 4756 */         String childResType = (String)((ArrayList)childResids.get(i)).get(1);
/* 4757 */         HashMap<String, String> healthAvailEntitys = getHealthAvailabilityDetails(childResid, childResType);
/* 4758 */         String healthstatus = (String)healthAvailEntitys.get("HEALTHSEVERITY");
/* 4759 */         String availstatus = (String)healthAvailEntitys.get("AVAILABILITYSEVERITY");
/*      */         
/* 4761 */         if (healthstatus != null)
/*      */         {
/* 4763 */           if (healthstatus.equals("5"))
/*      */           {
/* 4765 */             clearCount++;
/*      */           }
/* 4767 */           else if (healthstatus.equals("4"))
/*      */           {
/* 4769 */             severeCnt++;
/* 4770 */             warningCount++;
/*      */           }
/* 4772 */           else if (healthstatus.equals("1"))
/*      */           {
/* 4774 */             severeCnt++;
/* 4775 */             criticalCount++;
/*      */           }
/*      */           else
/*      */           {
/* 4779 */             healthUnknownCount++;
/*      */           }
/*      */           
/*      */         }
/*      */         else {
/* 4784 */           healthUnknownCount++;
/*      */         }
/*      */         
/*      */ 
/* 4788 */         if (availstatus != null)
/*      */         {
/* 4790 */           if (availstatus.equals("5"))
/*      */           {
/* 4792 */             upCount++;
/*      */           }
/* 4794 */           else if (availstatus.equals("1"))
/*      */           {
/* 4796 */             downCount++;
/*      */           }
/*      */           else
/*      */           {
/* 4800 */             availUnknownCount++;
/*      */           }
/*      */           
/*      */         }
/*      */         else {
/* 4805 */           availUnknownCount++;
/*      */         }
/*      */       }
/* 4808 */       outages = severeCnt + "/" + TotalCnt;
/* 4809 */       outagesMap.put("outages", outages);
/* 4810 */       outagesMap.put("upCount", upCount + "");
/* 4811 */       outagesMap.put("downCount", downCount + "");
/* 4812 */       outagesMap.put("availUnknownCount", availUnknownCount + "");
/* 4813 */       outagesMap.put("healthUnknownCount", healthUnknownCount + "");
/* 4814 */       outagesMap.put("criticalCount", criticalCount + "");
/* 4815 */       outagesMap.put("warningCount", warningCount + "");
/* 4816 */       outagesMap.put("clearCount", clearCount + "");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4820 */       e.printStackTrace();
/*      */     }
/* 4822 */     return outagesMap;
/*      */   }
/*      */   
/*      */   public static void removeUserDetails(String apikey) {
/*      */     try {
/* 4827 */       if ((apikey != null) && (apikeys.containsKey(apikey))) {
/* 4828 */         apikeys.remove(apikey);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 4832 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static Hashtable getUserDetails(String userName) {
/*      */     try {
/* 4838 */       String apikey = Constants.getUserAPIkey(userName);
/* 4839 */       if ((apikey != null) && (apikeys.containsKey(apikey))) {
/* 4840 */         apikeys.remove(apikey);
/*      */       }
/* 4842 */       return getUserNameForAPIKey(apikey);
/*      */     }
/*      */     catch (Exception e) {
/* 4845 */       e.printStackTrace();
/*      */     }
/* 4847 */     return null;
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getUserNameForAPIKey(String apiKey)
/*      */   {
/* 4852 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 4855 */       if ((apiKey != null) && (apikeys.containsKey(apiKey)))
/*      */       {
/* 4857 */         AMLog.debug("RESTAPI : APIKeys:" + apikeys);
/* 4858 */         return (Hashtable)apikeys.get(apiKey);
/*      */       }
/*      */       
/*      */ 
/* 4862 */       Object userDetails = Constants.getUserDetailsForAPIKey(apiKey);
/* 4863 */       if (userDetails != null)
/*      */       {
/* 4865 */         apikeys.put(apiKey, userDetails);
/* 4866 */         return (Hashtable<String, String>)userDetails;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4872 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 4876 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 4878 */     return null;
/*      */   }
/*      */   
/*      */   public static String getUserIdForAPIKey(String apiKey)
/*      */   {
/* 4883 */     Hashtable<String, String> userdetails = getUserNameForAPIKey(apiKey);
/* 4884 */     return userdetails != null ? (String)userdetails.get("USERID") : "-1";
/*      */   }
/*      */   
/*      */   public static String getUsername(String apiKey)
/*      */   {
/* 4889 */     Hashtable<String, String> userdetails = getUserNameForAPIKey(apiKey);
/* 4890 */     return userdetails != null ? (String)userdetails.get("USERNAME") : "";
/*      */   }
/*      */   
/*      */   public static void getChildIDs(Vector toreturn, String resourceid)
/*      */   {
/* 4895 */     ArrayList<String> childResIds = new ArrayList();
/* 4896 */     ResultSet hasChildrenRes = null;
/*      */     try
/*      */     {
/* 4899 */       hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.CHILDID  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1");
/* 4900 */       while (hasChildrenRes.next())
/*      */       {
/* 4902 */         String chResId = hasChildrenRes.getString(1);
/* 4903 */         if (!resourceid.equals(chResId))
/*      */         {
/* 4905 */           childResIds.add(chResId);
/*      */         }
/* 4907 */         toreturn.add(chResId);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4912 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 4915 */       AMConnectionPool.closeStatement(hasChildrenRes);
/*      */     }
/* 4917 */     if (childResIds.size() > 0)
/*      */     {
/* 4919 */       for (String childResid : childResIds)
/*      */       {
/* 4921 */         getChildIDs(toreturn, childResid);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getConditionForOperator(HttpServletRequest request)
/*      */   {
/* 4928 */     return getConditionForOperator(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static String getConditionForOperator(String apikey)
/*      */   {
/* 4933 */     Hashtable<String, String> ownerDetails = getUserNameForAPIKey(apikey);
/* 4934 */     String qryCondition = "";
/* 4935 */     if (ownerDetails != null)
/*      */     {
/* 4937 */       String owner = (String)ownerDetails.get("USERNAME");
/* 4938 */       String ownerRole = (String)ownerDetails.get("GROUPNAME");
/* 4939 */       String restrictedAdmin = (String)ownerDetails.get("RESTRICTEDADMIN");
/* 4940 */       if ((owner != null) && (!owner.equalsIgnoreCase("admin")) && (ownerRole != null) && (("OPERATOR".equalsIgnoreCase(ownerRole)) || (isDelegatedAdmin(apikey)))) {
/* 4941 */         return getCondition(owner, ownerRole);
/*      */       }
/*      */     }
/* 4944 */     return qryCondition;
/*      */   }
/*      */   
/*      */   public static String getConditionForOperator(String owner, String ownerRole) {
/* 4948 */     return getCondition(owner, ownerRole);
/*      */   }
/*      */   
/*      */   public static String getCondition(String owner, String ownerRole) {
/* 4952 */     String qryCondition = "";
/*      */     try
/*      */     {
/* 4955 */       StringBuilder resIds = new StringBuilder();
/* 4956 */       resIds.append("-1");
/* 4957 */       Vector<String> resIds_vector = new Vector();
/* 4958 */       if (ownerRole != null)
/*      */       {
/* 4960 */         Hashtable<String, String> ownerDetails = getUserDetails(owner);
/* 4961 */         String userId = (String)ownerDetails.get("USERID");
/* 4962 */         resIds_vector = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(userId);
/* 4963 */         if ((resIds_vector != null) && (resIds_vector.size() != 0))
/*      */         {
/* 4965 */           for (int i = 0; i < resIds_vector.size(); i++)
/*      */           {
/* 4967 */             resIds.append(",").append((String)resIds_vector.get(i));
/*      */           }
/*      */         }
/* 4970 */         qryCondition = " and AM_ManagedObject.RESOURCEID in (" + resIds + ") ";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4975 */       e.printStackTrace();
/*      */     }
/* 4977 */     return qryCondition;
/*      */   }
/*      */   
/*      */   public static boolean isAssociatedToOperator(HttpServletRequest request, String residparam) {
/* 4981 */     boolean isassociate = false;
/*      */     try {
/* 4983 */       String apikey = request.getParameter("apikey");
/* 4984 */       Hashtable<String, String> ownerDetails = getUserNameForAPIKey(apikey);
/* 4985 */       Vector resIds_vector = new Vector();
/* 4986 */       if (ownerDetails != null)
/*      */       {
/* 4988 */         String owner = (String)ownerDetails.get("USERNAME");
/* 4989 */         String ownerRole = (String)ownerDetails.get("GROUPNAME");
/* 4990 */         if ((ownerRole != null) && (ownerRole.equalsIgnoreCase("OPERATOR")))
/*      */         {
/* 4992 */           resIds_vector = com.adventnet.appmanager.struts.beans.ClientDBUtil.getResourceIdentity(owner);
/* 4993 */           if ((resIds_vector != null) && (resIds_vector.contains(residparam))) {
/* 4994 */             isassociate = true;
/*      */           }
/*      */         }
/* 4997 */         else if ("ADMIN".equals(ownerDetails.get("GROUPNAME"))) {
/* 4998 */           isassociate = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 5003 */       e.printStackTrace();
/* 5004 */       return isassociate;
/*      */     }
/*      */     
/* 5007 */     return isassociate;
/*      */   }
/*      */   
/*      */   public static boolean isPrivilegedUser(HttpServletRequest request) {
/* 5011 */     return isPrivilegedUser(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static boolean isPrivilegedUser(String apikey) {
/* 5015 */     return (isDelegatedAdmin(apikey)) || (getOwnerRole(apikey).equals("OPERATOR"));
/*      */   }
/*      */   
/*      */   public static boolean isDelegatedAdmin(HttpServletRequest request) {
/* 5019 */     return isDelegatedAdmin(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static boolean isDelegatedAdmin(String apikey) {
/* 5023 */     if ((apikey != null) && (DBUtil.isDelegatedAdminEnabled())) {
/* 5024 */       Hashtable<String, String> userDetails = getUserNameForAPIKey(apikey);
/* 5025 */       if (userDetails != null) {
/* 5026 */         String role = (String)userDetails.get("GROUPNAME");
/* 5027 */         String restrictedAdmin = (String)userDetails.get("RESTRICTEDADMIN");
/* 5028 */         if ((("ADMIN".equalsIgnoreCase(role)) || ("ENTERPRISEADMIN".equalsIgnoreCase(role))) && (("0".equals(restrictedAdmin)) || ("false".equals(restrictedAdmin)))) {
/* 5029 */           return true;
/*      */         }
/*      */       }
/*      */     }
/* 5033 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAdminRole(HttpServletRequest request)
/*      */   {
/* 5038 */     return isAdminRole(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static boolean isAdminRole(String apiKey)
/*      */   {
/* 5043 */     String ownerRole = getOwnerRole(apiKey);
/* 5044 */     return ((ownerRole.equalsIgnoreCase("ADMIN")) || (ownerRole.equalsIgnoreCase("ENTERPRISEADMIN"))) && (!isDelegatedAdmin(apiKey));
/*      */   }
/*      */   
/*      */   public static boolean isOperatorRole(HttpServletRequest request)
/*      */   {
/* 5049 */     String ownerRole = getOwnerRole(request);
/* 5050 */     if (ownerRole.equalsIgnoreCase("OPERATOR"))
/*      */     {
/* 5052 */       return true;
/*      */     }
/* 5054 */     return false;
/*      */   }
/*      */   
/*      */   public static String getOwnerRole(HttpServletRequest request)
/*      */   {
/* 5059 */     return getOwnerRole(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static String getOwnerRole(String apikey)
/*      */   {
/* 5064 */     String ownerRole = "USERS";
/*      */     try
/*      */     {
/* 5067 */       Hashtable<String, String> ownerDetails = getUserNameForAPIKey(apikey);
/* 5068 */       if ((ownerDetails != null) && (ownerDetails.get("GROUPNAME") != null)) {
/* 5069 */         return (String)ownerDetails.get("GROUPNAME");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5074 */       e.printStackTrace();
/*      */     }
/* 5076 */     return ownerRole;
/*      */   }
/*      */   
/*      */   public static String getOwnerName(HttpServletRequest request)
/*      */   {
/* 5081 */     return getOwnerName(request.getParameter("apikey"));
/*      */   }
/*      */   
/*      */   public static String getOwnerName(String apikey)
/*      */   {
/* 5086 */     String ownerName = "admin";
/*      */     try
/*      */     {
/* 5089 */       Hashtable<String, String> ownerDetails = getUserNameForAPIKey(apikey);
/* 5090 */       if ((ownerDetails != null) && (ownerDetails.get("USERNAME") != null)) {
/* 5091 */         return (String)ownerDetails.get("USERNAME");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5096 */       e.printStackTrace();
/*      */     }
/* 5098 */     return ownerName;
/*      */   }
/*      */   
/*      */   public static boolean isAPIKeyRequest(HttpServletRequest request) {
/* 5102 */     AMLog.debug("REST API : Inside validateMethod");
/* 5103 */     String uri = request.getRequestURI();
/* 5104 */     if (uri.toLowerCase().contains("authenticator"))
/*      */     {
/* 5106 */       return true;
/*      */     }
/* 5108 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getUserImagePath(HttpServletRequest request, String username, String userid)
/*      */   {
/* 5114 */     String uri = "/images/icon_user.gif";
/*      */     try
/*      */     {
/* 5117 */       String home = new File(".").getAbsoluteFile().getParentFile().getAbsoluteFile().getParentFile().getAbsolutePath();
/* 5118 */       String Dirpath = home + File.separator + "working" + File.separator + "users" + File.separator + username + File.separator;
/* 5119 */       String f1 = Dirpath + userid + ".jpg";
/* 5120 */       if (new File(Dirpath).exists()) {
/* 5121 */         if (new File(f1).exists()) {
/* 5122 */           uri = "/users/" + username + "/" + userid + ".jpg";
/*      */         }
/*      */         else {
/* 5125 */           uri = "/images/icon_user.gif";
/*      */         }
/*      */       } else {
/* 5128 */         uri = "/images/icon_user.gif";
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5134 */       e.printStackTrace();
/*      */     }
/* 5136 */     return uri;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String clearAlerts(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 5147 */     String errorCode = "4051";
/* 5148 */     String entity = request.getParameter("entity");
/* 5149 */     if ((entity == null) || (entity.length() == 0) || ("null".equalsIgnoreCase(entity)))
/*      */     {
/* 5151 */       errorCode = "4051";
/* 5152 */       return URITree.generateXML(request, response, FaultUtil.getString("am.fault.null.entity"), errorCode);
/*      */     }
/*      */     
/* 5155 */     boolean alertCleared = FaultUtil.clearAlert(entity, getUsername(request.getParameter("apikey")));
/* 5156 */     if (!alertCleared)
/*      */     {
/* 5158 */       errorCode = "4052";
/* 5159 */       return URITree.generateXML(request, response, FaultUtil.getString("am.fault.alertcleared.failed"), errorCode);
/*      */     }
/* 5161 */     if (request.getParameter("incrementedIdVal") != null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/* 5166 */         int incrementedIdVal = Integer.parseInt(request.getParameter("incrementedIdVal"));
/* 5167 */         com.adventnet.appmanager.fault.ExecuteSDPTicketAction.deleteSDPTicket(entity, incrementedIdVal);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 5171 */         errorCode = "4053";
/*      */       }
/*      */     }
/* 5174 */     return URITree.generateXML(request, response, FaultUtil.getString("am.fault.alertcleared.message"), errorCode);
/*      */   }
/*      */   
/*      */   public static String SyncMonitors(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5179 */     String[] resID = null;
/* 5180 */     String[] haID = null;
/* 5181 */     String outputString = "";
/* 5182 */     ResultSet rs = null;
/* 5183 */     String type = "1";
/* 5184 */     String grouptype = null;
/* 5185 */     String userid = "1";
/* 5186 */     boolean allowCreateMG = true;
/*      */     try { String str1;
/* 5188 */       if (request.getParameter("type") != null) {
/* 5189 */         type = request.getParameter("type");
/*      */       } else {
/* 5191 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.type.emptymessage."), "4202");
/* 5192 */         return outputString;
/*      */       }
/* 5194 */       if (request.getParameter("grouptype") != null) {
/* 5195 */         grouptype = request.getParameter("grouptype");
/*      */       } else {
/* 5197 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.grouptype.emptymessage"), "4500");
/* 5198 */         return outputString;
/*      */       }
/*      */       
/* 5201 */       if (type.equalsIgnoreCase("MG")) {
/* 5202 */         type = "0";
/*      */       } else {
/* 5204 */         type = "1";
/*      */       }
/* 5206 */       if (grouptype.equalsIgnoreCase("Sub-Group")) {
/* 5207 */         grouptype = "1";
/* 5208 */       } else if (grouptype.equalsIgnoreCase("WebApp")) {
/* 5209 */         grouptype = "2";
/* 5210 */       } else if (grouptype.equalsIgnoreCase("WebSer")) {
/* 5211 */         grouptype = "1002";
/* 5212 */       } else if (grouptype.equalsIgnoreCase("AppSer")) {
/* 5213 */         grouptype = "1003";
/* 5214 */       } else if (grouptype.equalsIgnoreCase("Database")) {
/* 5215 */         grouptype = "1004";
/* 5216 */       } else if (grouptype.equalsIgnoreCase("Database-Cluster")) {
/* 5217 */         grouptype = "1005";
/* 5218 */       } else if (grouptype.equalsIgnoreCase("Servers")) {
/* 5219 */         grouptype = "1006";
/*      */       }
/*      */       
/* 5222 */       if ((request.getParameter("resourceid") != null) && (!request.getParameter("resourceid").equals(""))) {
/* 5223 */         resID = request.getParameter("resourceid").split(",");
/*      */       } else {
/* 5225 */         AMLog.debug("REST API : The resourceid should not be empty.");
/* 5226 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.resid.emptymessage"), "4505");
/* 5227 */         return outputString;
/*      */       }
/* 5229 */       if ((request.getParameter("haid") != null) && (!request.getParameter("haid").equals(""))) {
/* 5230 */         haID = request.getParameter("haid").split(",");
/* 5231 */         allowCreateMG = false; } else { String checkquery;
/* 5232 */         if ((request.getParameter("mgname") != null) && (!request.getParameter("mgname").equals("")) && (type != null) && (!type.equals("")) && (grouptype != null) && (!grouptype.equals(""))) {
/* 5233 */           checkquery = "select RESOURCEID from AM_ManagedObject MO,AM_HOLISTICAPPLICATION HA WHERE MO.TYPE='HAI' and MO.RESOURCENAME ='" + request.getParameter("mgname") + "' and HA.TYPE=" + type + " and HA.GROUPTYPE=" + grouptype + " and MO.RESOURCEID=HA.HAID order by resourceid asc";
/*      */           try {
/* 5235 */             rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 5236 */             if (rs.next()) {
/* 5237 */               haID = new String[1];
/* 5238 */               haID[0] = rs.getString("RESOURCEID");
/* 5239 */               allowCreateMG = false;
/*      */             }
/* 5241 */             AMConnectionPool.closeStatement(rs);
/*      */           } catch (Exception e) {
/* 5243 */             e.printStackTrace();
/*      */           }
/*      */         } else {
/* 5246 */           AMLog.debug("REST API : The haid should not be empty.");
/* 5247 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.emptymessage"), "4506");
/* 5248 */           return outputString;
/*      */         }
/*      */       }
/* 5251 */       if ((haID != null) && (EnterpriseUtil.isAdminServer) && (Integer.parseInt(haID[0]) > EnterpriseUtil.RANGE)) {
/* 5252 */         allowCreateMG = true;
/*      */       }
/* 5254 */       if (allowCreateMG) {
/* 5255 */         if (type.equals("0")) {
/* 5256 */           MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 5257 */           MSreq.setContentType("text/xml; charset=UTF-8");
/* 5258 */           MSreq.addParameter("name", request.getParameter("mgname"));
/* 5259 */           if (grouptype.equals("1")) {
/* 5260 */             MSreq.addParameter("grouptype", "monitorgroup");
/* 5261 */           } else if (grouptype.equals("2")) {
/* 5262 */             MSreq.addParameter("grouptype", "webappgroup");
/*      */           }
/* 5264 */           MSreq.addParameter("description", "Monitor Group");
/* 5265 */           MSreq.addParameter("userid", userid);
/* 5266 */           MSreq.addParameter("locationid", "19");
/* 5267 */           MSreq.addParameter("needhaid", "true");
/* 5268 */           haID = new String[1];
/* 5269 */           haID[0] = addMonitorGroup(MSreq, response);
/* 5270 */         } else if (type.equals("1")) {
/* 5271 */           HAIDManagerAction hm = new HAIDManagerAction();
/* 5272 */           HashMap subGroupDetails = new HashMap();
/* 5273 */           int result = 0;
/* 5274 */           subGroupDetails.put("name", request.getParameter("mgname"));
/* 5275 */           String[] owners = userid.split(",");
/* 5276 */           subGroupDetails.put("userid", owners);
/* 5277 */           subGroupDetails.put("locationid", "19");
/* 5278 */           subGroupDetails.put("haid", "");
/* 5279 */           subGroupDetails.put("subGroupType", grouptype);
/* 5280 */           subGroupDetails.put("description", "Sub Group");
/* 5281 */           result = hm.createSubGroup(subGroupDetails);
/* 5282 */           if (result == -1) {
/* 5283 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.errmsg"), "4503", true);
/* 5284 */             return outputString;
/*      */           }
/* 5286 */           haID = new String[1];
/* 5287 */           haID[0] = Integer.toString(result);
/*      */         }
/*      */       }
/* 5290 */       request.setAttribute("resourceid", haID[0]);
/*      */       
/* 5292 */       if ((request.getParameter("overwrite") != null) && (request.getParameter("overwrite").equals("true"))) {
/* 5293 */         HAIDManagerAction hm = new HAIDManagerAction();
/* 5294 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 5295 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 5296 */         String checkquery = "select * from AM_PARENTCHILDMAPPER where PARENTID=" + haID[0];
/* 5297 */         rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 5298 */         String ids = "";
/* 5299 */         while (rs.next()) {
/* 5300 */           ids = ids + rs.getString("CHILDID") + ",";
/*      */         }
/* 5302 */         AMConnectionPool.closeStatement(rs);
/* 5303 */         String[] idstoDelete = ids.split(",");
/* 5304 */         if (!ids.equals("")) {
/* 5305 */           MSreq.addParameter("removefromhaid", haID[0]);
/* 5306 */           MSreq.setAttribute("monitors", idstoDelete);
/* 5307 */           MSreq.setAttribute("uri", "removeMonitors.do");
/* 5308 */           String apikey = request.getParameter("apikey");
/* 5309 */           String remoteHost = request.getRemoteAddr();
/* 5310 */           MSreq.addParameter("apikey", apikey);
/* 5311 */           MSreq.addParameter("host", remoteHost);
/* 5312 */           hm.removeMonitors(null, null, MSreq, response);
/*      */         }
/*      */       }
/* 5315 */       if ((resID != null) && (haID != null)) {
/* 5316 */         for (int i = 0; i < resID.length; i++) { boolean added;
/* 5317 */           for (int j = 0; j < haID.length; j++) {
/* 5318 */             long id = DBUtil.insertParentChildMapper(Integer.parseInt(haID[j]), Integer.parseInt(resID[i]));
/* 5319 */             AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 5320 */             added = adder.addDependentAttributes(Integer.parseInt(haID[j]), Integer.parseInt(resID[i]));
/*      */           }
/*      */         }
/* 5323 */         for (int j = 0; j < haID.length; j++) {
/*      */           try {
/* 5325 */             new AMRCAnalyser().applyRCA(Integer.parseInt(haID[j]), 17, System.currentTimeMillis(), true, true, 1);
/* 5326 */             new AMRCAnalyser().applyRCA(Integer.parseInt(haID[j]), 18, System.currentTimeMillis(), true, false, 2);
/*      */           }
/*      */           catch (Exception ex) {
/* 5329 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 5340 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ef) {
/* 5342 */         ef.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 5346 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.associate.succmsg"), "4000", true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5334 */       e.printStackTrace();
/* 5335 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.associate.errmsg"), "4507", true);
/* 5336 */       return outputString;
/*      */     }
/*      */     finally {
/*      */       try {
/* 5340 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ef) {
/* 5342 */         ef.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5347 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String AssociateMonitorstoGroup(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5352 */     String[] resID = null;
/* 5353 */     String[] haID = null;
/* 5354 */     String outputString = "";
/* 5355 */     ResultSet rs = null;
/* 5356 */     PreparedStatement ps = null;
/* 5357 */     String apiKey = request.getParameter("apikey");
/*      */     try {
/* 5359 */       BusinessViewUtil.deleteBussinessViewCache();
/* 5360 */       boolean allowCreateMG = true;
/* 5361 */       boolean useADDM = Boolean.valueOf(request.getParameter("useADDM") == null ? "false" : request.getParameter("useADDM")).booleanValue();
/* 5362 */       if ((request.getParameter("resourceid") != null) && (!request.getParameter("resourceid").equals(""))) {
/* 5363 */         resID = request.getParameter("resourceid").split(",");
/*      */       } else {
/* 5365 */         AMLog.debug("REST API : The resourceid should not be empty.");
/* 5366 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.resid.emptymessage"), "4505");
/* 5367 */         return outputString;
/*      */       }
/* 5369 */       if ((request.getParameter("haid") != null) && (!request.getParameter("haid").equals(""))) {
/* 5370 */         haID = request.getParameter("haid").split(",");
/* 5371 */         allowCreateMG = false;
/* 5372 */       } else if ((request.getParameter("mgname") != null) && (!request.getParameter("mgname").equals(""))) {
/* 5373 */         ps = AMConnectionPool.getConnection().prepareStatement("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and " + DBQueryUtil.castasCitext("RESOURCENAME") + " =? order by resourceid asc");
/* 5374 */         ps.setString(1, request.getParameter("mgname"));
/* 5375 */         rs = ps.executeQuery();
/*      */         try {
/* 5377 */           if (rs.next()) {
/* 5378 */             haID = new String[1];
/* 5379 */             haID[0] = rs.getString("RESOURCEID");
/* 5380 */             allowCreateMG = false;
/*      */           }
/* 5382 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 5384 */           ((Exception)e).printStackTrace();
/*      */         }
/*      */       }
/*      */       else {
/* 5388 */         AMLog.debug("REST API : The haid should not be empty.");
/* 5389 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.emptymessage"), "4506");
/* 5390 */         return outputString;
/*      */       }
/*      */       
/* 5393 */       if ((haID != null) && (EnterpriseUtil.isAdminServer) && (Integer.parseInt(haID[0]) > EnterpriseUtil.RANGE)) {
/* 5394 */         allowCreateMG = true;
/*      */       }
/*      */       
/* 5397 */       if (haID != null) {
/* 5398 */         ps = AMConnectionPool.getConnection().prepareStatement("select TYPE from AM_ManagedObject where RESOURCEID='" + haID[0] + "'");
/* 5399 */         rs = ps.executeQuery();
/* 5400 */         String type = "";
/*      */         try {
/* 5402 */           if (rs.next()) {
/* 5403 */             type = rs.getString("TYPE");
/*      */           }
/* 5405 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 5407 */           e.printStackTrace();
/*      */         }
/* 5409 */         if (!"HAI".equals(type)) {
/* 5410 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong"), "4510");
/* 5411 */           return outputString;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 5416 */       if (allowCreateMG)
/*      */       {
/* 5418 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 5419 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 5420 */         MSreq.addParameter("name", request.getParameter("mgname"));
/* 5421 */         MSreq.addParameter("grouptype", "monitorgroup");
/* 5422 */         MSreq.addParameter("description", "Monitor Grooup");
/* 5423 */         MSreq.addParameter("userid", "1");
/* 5424 */         MSreq.addParameter("locationid", "19");
/* 5425 */         MSreq.addParameter("needhaid", "true");
/* 5426 */         MSreq.addParameter("useADDM", useADDM + "");
/* 5427 */         if (apiKey != null) {
/* 5428 */           MSreq.addParameter("apikey", request.getParameter("apikey"));
/* 5429 */           String remoteHost = request.getRemoteAddr();
/* 5430 */           MSreq.addParameter("host", remoteHost);
/*      */         }
/*      */         
/* 5433 */         haID = new String[1];
/* 5434 */         haID[0] = addMonitorGroup(MSreq, response);
/*      */       }
/*      */       
/* 5437 */       request.setAttribute("resourceid", haID[0]);
/*      */       String checkquery;
/* 5439 */       if ((request.getParameter("overwrite") != null) && (request.getParameter("overwrite").equals("true"))) {
/* 5440 */         HAIDManagerAction hm = new HAIDManagerAction();
/* 5441 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 5442 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 5443 */         checkquery = "select * from AM_PARENTCHILDMAPPER where PARENTID=" + haID[0];
/* 5444 */         rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 5445 */         String ids = "";
/* 5446 */         while (rs.next()) {
/* 5447 */           ids = ids + rs.getString("CHILDID") + ",";
/*      */         }
/* 5449 */         AMConnectionPool.closeStatement(rs);
/* 5450 */         String[] idstoDelete = ids.split(",");
/* 5451 */         if (!ids.equals("")) {
/* 5452 */           MSreq.addParameter("removefromhaid", haID[0]);
/* 5453 */           MSreq.setAttribute("monitors", idstoDelete);
/* 5454 */           MSreq.setAttribute("uri", "removeMonitors.do");
/* 5455 */           MSreq.addParameter("useADDM", useADDM + "");
/* 5456 */           String apikey = request.getParameter("apikey");
/* 5457 */           if (apikey != null) {
/* 5458 */             String remoteHost = request.getRemoteAddr();
/* 5459 */             MSreq.addParameter("apikey", apikey);
/* 5460 */             MSreq.addParameter("host", remoteHost);
/*      */           }
/* 5462 */           hm.removeMonitors(null, null, MSreq, response);
/*      */         }
/*      */       }
/* 5465 */       if ((resID != null) && (haID != null)) {
/* 5466 */         boolean isDelegated = isDelegatedAdmin(request);
/* 5467 */         Vector ownedResids = new Vector();
/* 5468 */         if (isDelegated) {
/* 5469 */           ownedResids = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(getUserIdForAPIKey(apiKey));
/*      */         }
/* 5471 */         if (checkResourceidforDelegatedAdmin(request, haID[0])) {
/* 5472 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 5473 */           return outputString;
/*      */         }
/* 5475 */         boolean isAuto = Boolean.FALSE.booleanValue();
/* 5476 */         long topLevelId = -1L;
/* 5477 */         String[] isAutoMapped = new String[0];
/* 5478 */         if (request.getParameter("isAutoMapped") != null) {
/* 5479 */           isAutoMapped = request.getParameter("isAutoMapped").split(",");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5488 */         for (int i = 0; i < resID.length; i++)
/*      */         {
/* 5490 */           if ((!isDelegated) || ((isDelegated) && (ownedResids.contains(resID[i]))))
/*      */           {
/* 5492 */             if (useADDM) {
/* 5493 */               topLevelId = Long.parseLong(request.getParameter("topLevelGroupId") == null ? "-1" : request.getParameter("topLevelGroupId"));
/* 5494 */               isAuto = Boolean.valueOf(isAutoMapped[i]).booleanValue(); }
/*      */             boolean added;
/* 5496 */             for (int j = 0; j < haID.length; j++) {
/* 5497 */               int groupID = Integer.valueOf(haID[j]).intValue();
/* 5498 */               int resourceID = Integer.valueOf(resID[i]).intValue();
/* 5499 */               if (groupID == resourceID) {
/* 5500 */                 outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.associate.failure"), "4500");
/* 5501 */                 return outputString;
/*      */               }
/* 5503 */               long id = DBUtil.insertParentChildMapper(groupID, resourceID);
/* 5504 */               if (topLevelId != -1L) {
/* 5505 */                 ParentChildRelationalUtil.addToAutoManualRelationOfAddmTable(topLevelId, id, isAuto);
/*      */               }
/*      */               try {
/* 5508 */                 String auditLogMsg = FormatUtil.getString("am.audit.monitorassociate.msg", new String[] { DBUtil.getDisplaynameforResourceID(resID[i]), DBUtil.getDisplaynameforResourceID(haID[j]) });
/* 5509 */                 cliAuditUtil.addToAuditLog(request, Integer.parseInt(haID[j]), 5, auditLogMsg);
/*      */               }
/*      */               catch (Exception e) {
/* 5512 */                 e.printStackTrace();
/*      */               }
/* 5514 */               AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 5515 */               added = adder.addDependentAttributes(Integer.parseInt(haID[j]), Integer.parseInt(resID[i]));
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 5520 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */         {
/* 5522 */           List<String> allOwnersList = new ArrayList();
/* 5523 */           for (int j = 0; j < haID.length; j++) {
/*      */             try {
/* 5525 */               Vector<String> allOwnersInHaidHierarchy = new Vector();
/* 5526 */               RestrictedUsersViewUtil.getAllOwnersInMGTree(allOwnersInHaidHierarchy, new String[] { haID[j] });
/* 5527 */               if (!allOwnersInHaidHierarchy.isEmpty())
/*      */               {
/* 5529 */                 Iterator<String> it = allOwnersInHaidHierarchy.iterator();
/* 5530 */                 while (it.hasNext())
/*      */                 {
/* 5532 */                   String owner = (String)it.next();
/* 5533 */                   if (!allOwnersList.contains(owner))
/*      */                   {
/* 5535 */                     allOwnersList.add(owner);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 5542 */               e.printStackTrace();
/*      */             }
/*      */           }
/* 5545 */           if (!allOwnersList.isEmpty()) {
/*      */             try
/*      */             {
/* 5548 */               AMLog.debug("[CommonAPIUtil::(AssociateMonitorstoGroup)]ruser(s) : " + allOwnersList);
/* 5549 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(allOwnersList);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 5553 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 5559 */         if (!useADDM) {
/* 5560 */           for (int j = 0; j < haID.length; j++) {
/*      */             try {
/* 5562 */               new AMRCAnalyser().applyRCA(Integer.parseInt(haID[j]), 17, System.currentTimeMillis(), true, true, 1);
/* 5563 */               new AMRCAnalyser().applyRCA(Integer.parseInt(haID[j]), 18, System.currentTimeMillis(), true, false, 2);
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 5567 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 5578 */         AMConnectionPool.closeStatement(rs);
/* 5579 */         if (ps != null) {
/* 5580 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5583 */         e.printStackTrace();
/*      */       }
/*      */       
/* 5586 */       EventLogUtil.applyEventLogRules(haID[0], request.getParameter("resourceid"));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5573 */       e.printStackTrace();
/* 5574 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.associate.errmsg"), "4507", true);
/* 5575 */       return outputString;
/*      */     } finally {
/*      */       try {
/* 5578 */         AMConnectionPool.closeStatement(rs);
/* 5579 */         if (ps != null) {
/* 5580 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5583 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 5587 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.associate.succmsg"), "4000", true);
/* 5588 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String removeMonitorFrmMG(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5593 */     String[] resID = null;
/* 5594 */     String haID = null;
/* 5595 */     String outputString = "";
/* 5596 */     ResultSet rs = null;
/* 5597 */     PreparedStatement ps = null;
/*      */     try {
/* 5599 */       String disperrname = "";
/* 5600 */       if ((request.getParameter("resourceid") != null) && (!request.getParameter("resourceid").equals(""))) {
/* 5601 */         resID = request.getParameter("resourceid").split(",");
/*      */       } else {
/* 5603 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.resid.emptymessage"), "4505");
/* 5604 */         return outputString; }
/*      */       boolean haidavail;
/* 5606 */       if ((request.getParameter("haid") != null) && (!request.getParameter("haid").equals(""))) {
/* 5607 */         haID = request.getParameter("haid");
/* 5608 */         rs = null;
/* 5609 */         rs = DBQueryUtil.executeQueryStmt("select RESOURCEID from AM_ManagedObject where RESOURCEID=" + haID + " and TYPE='HAI'");
/* 5610 */         haidavail = rs.next();
/* 5611 */         if (!haidavail) {
/* 5612 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.worng"), "4510");
/* 5613 */           return outputString;
/*      */         }
/* 5615 */         disperrname = haID;
/* 5616 */       } else { if ((request.getParameter("mgname") != null) && (!request.getParameter("mgname").equals(""))) {
/* 5617 */           ps = AMConnectionPool.getConnection().prepareStatement("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and " + DBQueryUtil.castasCitext("RESOURCENAME") + " =? order by resourceid asc");
/* 5618 */           ps.setString(1, request.getParameter("mgname"));
/* 5619 */           haID = "";
/* 5620 */           rs = ps.executeQuery();
/*      */           try {
/* 5622 */             if (rs.next()) {
/* 5623 */               haID = rs.getString("RESOURCEID");
/*      */             }
/*      */             
/* 5626 */             AMConnectionPool.closeStatement(rs);
/* 5627 */             if ((haID == null) || (haID.equals(""))) {
/* 5628 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong"), "4511");
/* 5629 */               return outputString;
/*      */             }
/* 5631 */             disperrname = request.getParameter("mgname");
/*      */           } catch (Exception e) {
/* 5633 */             ((Exception)e).printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 5638 */         AMLog.debug("REST API : The Monitor Group name should not be empty.");
/* 5639 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haidmgname.emptymessage"), "4502");
/* 5640 */         return outputString;
/*      */       }
/*      */       String chkmonGrp;
/* 5643 */       if ((isAdminRole(request)) || (isDelegatedAdmin(request))) {
/* 5644 */         if (checkResourceidforDelegatedAdmin(request, haID)) {
/* 5645 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 5646 */           return outputString;
/*      */         }
/* 5648 */         chkmonGrp = checkParenidforchildID(haID, request.getParameter("resourceid"));
/* 5649 */         HAIDManagerAction hm; if ((chkmonGrp.equals("success")) || (chkmonGrp.split(",").length != resID.length)) {
/* 5650 */           request.setAttribute("resourceid", haID);
/* 5651 */           hm = new HAIDManagerAction();
/* 5652 */           MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 5653 */           MSreq.setContentType("text/xml; charset=UTF-8");
/* 5654 */           AMConnectionPool.closeStatement(rs);
/* 5655 */           String[] idstoDelete = resID;
/* 5656 */           if (resID != null) {
/* 5657 */             MSreq.addParameter("removefromhaid", haID);
/* 5658 */             MSreq.setAttribute("monitors", idstoDelete);
/* 5659 */             MSreq.setAttribute("uri", "removeMonitors.do");
/* 5660 */             String apikey = request.getParameter("apikey");
/* 5661 */             String remoteHost = request.getRemoteAddr();
/* 5662 */             MSreq.addParameter("apikey", apikey);
/* 5663 */             MSreq.addParameter("host", remoteHost);
/* 5664 */             hm.removeMonitors(null, null, MSreq, response);
/*      */           }
/*      */         }
/* 5667 */         if (!chkmonGrp.equals("success")) {
/* 5668 */           AMLog.debug("REST API : The Monitor does not fall under monitor group.");
/* 5669 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.monid.wrongmessage", new String[] { chkmonGrp }), "4514");
/* 5670 */           return outputString;
/*      */         }
/*      */       }
/*      */       else {
/* 5674 */         AMLog.debug("REST API : The Monitor does have privliges under monitor group.");
/* 5675 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.privilges.wrongmessage", new String[] { disperrname }), "4515");
/* 5676 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 5685 */         AMConnectionPool.closeStatement(rs);
/* 5686 */         if (ps != null) {
/* 5687 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5690 */         e.printStackTrace();
/*      */       }
/*      */       
/* 5693 */       EventLogUtil.removemonitorFromEventLog(request.getParameter("resourceid"), haID);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5680 */       e.printStackTrace();
/* 5681 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.unassociate.errmsg"), "4516", true);
/* 5682 */       return outputString;
/*      */     } finally {
/*      */       try {
/* 5685 */         AMConnectionPool.closeStatement(rs);
/* 5686 */         if (ps != null) {
/* 5687 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5690 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 5694 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.unassociate.succmsg"), "4000");
/* 5695 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String moveSubGroup(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5700 */     String outputString = "";
/* 5701 */     ResultSet rs = null;
/* 5702 */     PreparedStatement ps = null;
/*      */     try {
/* 5704 */       String disperrname = "";
/* 5705 */       String haiD = request.getParameter("haid");
/*      */       
/* 5707 */       Vector<String> allOldOwners = new Vector();
/* 5708 */       Vector<String> allNewOwners = new Vector();
/*      */       
/* 5710 */       String fromparid = "";
/*      */       
/* 5712 */       String toParID = request.getParameter("tohaid");
/*      */       boolean haidavail;
/* 5714 */       if ((haiD != null) && (!haiD.equals("")))
/*      */       {
/* 5716 */         if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate)) {
/*      */           try
/*      */           {
/* 5719 */             RestrictedUsersViewUtil.getAllOwnersInMGTree(allOldOwners, new String[] { haiD });
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 5723 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/* 5727 */         rs = null;
/* 5728 */         rs = DBQueryUtil.executeQueryStmt("select RESOURCEID from AM_ManagedObject where RESOURCEID=" + haiD + " and TYPE='HAI'");
/* 5729 */         haidavail = rs.next();
/* 5730 */         if (!haidavail) {
/* 5731 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.worng"), "4510");
/* 5732 */           return outputString;
/*      */         }
/* 5734 */         fromparid = DBUtil.getFirstParentId(haiD);
/* 5735 */         if (fromparid == null) {
/* 5736 */           fromparid = "-1";
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 5744 */         AMLog.debug("REST API : The Monitor Group name should not be empty.");
/* 5745 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haidmgname.emptymessage"), "4502");
/* 5746 */         haidavail = outputString;
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
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 5908 */           AMConnectionPool.closeStatement(rs);
/* 5909 */           if (ps != null) {
/* 5910 */             ps.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 5913 */           ((Exception)e).printStackTrace(); }
/* 5914 */         return haidavail;
/*      */       }
/*      */       boolean haidavail;
/* 5749 */       if ((toParID != null) && (!toParID.equals(""))) {
/* 5750 */         rs = null;
/* 5751 */         if (!toParID.equals("0")) {
/* 5752 */           rs = DBQueryUtil.executeQueryStmt("select RESOURCEID from AM_ManagedObject where RESOURCEID=" + toParID + " and TYPE='HAI'");
/* 5753 */           haidavail = rs.next();
/* 5754 */           if (!haidavail) {
/* 5755 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.tohaid.wrong"), "4518");
/* 5756 */             return outputString;
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/* 5761 */         AMLog.debug("REST API : The Monitor Group name should not be empty.");
/* 5762 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.tohaid.emptymessage"), "4517");
/* 5763 */         return outputString;
/*      */       }
/*      */       String chkparentid;
/* 5766 */       if ((isAdminRole(request)) || (isDelegatedAdmin(request))) {
/* 5767 */         if (checkResourceidforDelegatedAdmin(request, toParID + "," + haiD)) {
/* 5768 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 5769 */           return outputString;
/*      */         }
/* 5771 */         chkparentid = checkParenidforchildID(toParID, haiD, false);
/* 5772 */         Object toprtIDs; if (!chkparentid.equals("success"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 5777 */           toprtIDs = new ArrayList();
/* 5778 */           String toplvlprt2 = "";
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 5783 */           getTopLevelGroup(toParID, (ArrayList)toprtIDs);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           String deleteqry;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5798 */           if (!((ArrayList)toprtIDs).contains(haiD.trim())) {
/* 5799 */             deleteqry = "";
/* 5800 */             if (!fromparid.equals("-1")) {
/* 5801 */               deleteqry = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + fromparid + " and CHILDID=" + haiD;
/* 5802 */               AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(deleteqry);
/* 5803 */               EnterpriseUtil.addUpdateQueryToFile(deleteqry);
/*      */             }
/* 5805 */             String updateqry = "";
/* 5806 */             if (!toParID.equals("0")) {
/* 5807 */               DBUtil.insertParentChildMapper(Integer.parseInt(toParID), Integer.parseInt(haiD));
/* 5808 */               updateqry = "update AM_HOLISTICAPPLICATION set TYPE =1 where HAID=" + haiD;
/*      */             } else {
/* 5810 */               updateqry = "update AM_HOLISTICAPPLICATION set TYPE =0 where HAID=" + haiD;
/*      */             }
/*      */             try {
/* 5813 */               if (!fromparid.equals("-1")) {
/* 5814 */                 if (!toParID.equals("0")) {
/* 5815 */                   String auditLogMsg = FormatUtil.getString("am.audit.move.subgroup", new String[] { DBUtil.getDisplaynameforResourceID(haiD), DBUtil.getDisplaynameforResourceID(fromparid), DBUtil.getDisplaynameforResourceID(toParID) });
/* 5816 */                   cliAuditUtil.addToAuditLog(request, Integer.parseInt(fromparid), 7, auditLogMsg);
/* 5817 */                   cliAuditUtil.addToAuditLog(request, Integer.parseInt(toParID), 7, auditLogMsg);
/*      */                 }
/*      */                 else {
/* 5820 */                   String auditLogMsg = FormatUtil.getString("am.audit.move.subgroup.tomain", new String[] { DBUtil.getDisplaynameforResourceID(haiD), DBUtil.getDisplaynameforResourceID(fromparid) });
/* 5821 */                   cliAuditUtil.addToAuditLog(request, Integer.parseInt(fromparid), 7, auditLogMsg);
/* 5822 */                   cliAuditUtil.addToAuditLog(request, Integer.parseInt(haiD), 7, auditLogMsg);
/*      */                 }
/*      */               }
/*      */               else {
/* 5826 */                 String auditLogMsg = FormatUtil.getString("am.audit.move.subgroup", new String[] { DBUtil.getDisplaynameforResourceID(haiD), "Home", DBUtil.getDisplaynameforResourceID(toParID) });
/* 5827 */                 cliAuditUtil.addToAuditLog(request, Integer.parseInt(toParID), 7, auditLogMsg);
/*      */               }
/*      */             }
/*      */             catch (Exception ex) {
/* 5831 */               ex.printStackTrace();
/*      */             }
/* 5833 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updateqry);
/* 5834 */             EnterpriseUtil.addUpdateQueryToFile(updateqry);
/*      */           }
/*      */           else {
/* 5837 */             AMLog.debug("REST API : The Monitor group does not fall under monitor group.");
/* 5838 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.hierarchy.errmsg"), "4519");
/* 5839 */             return outputString;
/*      */           }
/*      */           
/* 5842 */           if (!fromparid.equals("-1")) {
/* 5843 */             new AMRCAnalyser().applyRCA(Integer.parseInt(fromparid), 17, System.currentTimeMillis(), true, true, 1);
/* 5844 */             new AMRCAnalyser().applyRCA(Integer.parseInt(fromparid), 18, System.currentTimeMillis(), true, false, 2);
/*      */           }
/* 5846 */           new AMRCAnalyser().applyRCA(Integer.parseInt(toParID), 17, System.currentTimeMillis(), true, true, 1);
/* 5847 */           new AMRCAnalyser().applyRCA(Integer.parseInt(toParID), 18, System.currentTimeMillis(), true, false, 2);
/*      */           
/* 5849 */           if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */           {
/* 5851 */             List<String> ulist = new ArrayList();
/*      */             try
/*      */             {
/* 5854 */               RestrictedUsersViewUtil.getAllOwnersInMGTree(allNewOwners, new String[] { toParID });
/* 5855 */               AMLog.debug("[CommonAPIUtil::(moveSubGroup)] allOldOwners: " + allOldOwners);
/* 5856 */               AMLog.debug("[CommonAPIUtil::(moveSubGroup)] allNewOwners: " + allNewOwners);
/*      */               
/* 5858 */               if (!allNewOwners.isEmpty()) {
/* 5859 */                 for (String s : allNewOwners)
/*      */                 {
/* 5861 */                   if ((!ulist.contains(s)) && (RestrictedUsersViewUtil.isRestrictedRole(s)))
/*      */                   {
/* 5863 */                     ulist.add(s);
/*      */                   }
/*      */                 }
/*      */               }
/* 5867 */               if (!allOldOwners.isEmpty())
/*      */               {
/* 5869 */                 for (String s : allOldOwners)
/*      */                 {
/* 5871 */                   if ((!ulist.contains(s)) && (RestrictedUsersViewUtil.isRestrictedRole(s)))
/*      */                   {
/* 5873 */                     ulist.add(s);
/*      */                   }
/*      */                 }
/*      */               }
/* 5877 */               if (!ulist.isEmpty())
/*      */               {
/* 5879 */                 AMLog.debug("[CommonAPIUtil::(moveSubGroup)]ruser(s) : " + ulist);
/* 5880 */                 RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(ulist);
/*      */               }
/*      */             }
/*      */             catch (Exception exx)
/*      */             {
/* 5885 */               exx.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 5891 */           AMLog.debug("REST API : The Monitor group does not fall under monitor group.");
/* 5892 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.move.avail"), "4520");
/* 5893 */           return outputString;
/*      */         }
/*      */       }
/*      */       else {
/* 5897 */         AMLog.debug("REST API : The Monitor does have privliges under monitor group.");
/* 5898 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.privilges.wrongmessage", new String[] { disperrname }), "4515");
/* 5899 */         return outputString;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 5908 */         AMConnectionPool.closeStatement(rs);
/* 5909 */         if (ps != null) {
/* 5910 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5913 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 5917 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.move.succmsg"), "4000");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5903 */       e.printStackTrace();
/* 5904 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.move.errmsg"), "4521", true);
/* 5905 */       return outputString;
/*      */     } finally {
/*      */       try {
/* 5908 */         AMConnectionPool.closeStatement(rs);
/* 5909 */         if (ps != null) {
/* 5910 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 5913 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5918 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String editGroup(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 5924 */     String outputString = "";
/* 5925 */     ResultSet rs = null;
/* 5926 */     PreparedStatement ps = null;
/*      */     try {
/*      */       String haid;
/* 5929 */       if ((isAdminRole(request)) || (isDelegatedAdmin(request)))
/*      */       {
/* 5931 */         haid = request.getParameter("haid");
/* 5932 */         String groupname = request.getParameter("groupname");
/* 5933 */         String description = request.getParameter("description");
/* 5934 */         String owners = request.getParameter("owners");
/* 5935 */         String location = request.getParameter("location");
/* 5936 */         ServletContext servletContext = request.getSession().getServletContext();
/* 5937 */         Properties applications = null;
/*      */         try {
/* 5939 */           applications = (Properties)servletContext.getAttribute("applications");
/*      */         }
/*      */         catch (Exception e) {
/* 5942 */           e.printStackTrace();
/*      */         }
/*      */         
/* 5945 */         if ((haid != null) && (!haid.equals(""))) {
/*      */           try {
/* 5947 */             rs = null;
/* 5948 */             ps = AMConnectionPool.getConnection().prepareStatement("select RESOURCEID from AM_ManagedObject where RESOURCEID=? and TYPE='HAI'");
/* 5949 */             ps.setInt(1, Integer.parseInt(haid));
/* 5950 */             rs = ps.executeQuery();
/* 5951 */             boolean haidavail = rs.next();
/* 5952 */             if (!haidavail)
/*      */             {
/* 5954 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.worng"), "4510");
/* 5955 */               String str1 = outputString;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 5963 */                 if (ps != null) {
/* 5964 */                   ps.close();
/*      */                 }
/* 5966 */                 if (rs != null) {
/* 5967 */                   AMConnectionPool.closeStatement(rs);
/*      */                 }
/*      */               } catch (Exception e) {
/* 5970 */                 e.printStackTrace();
/*      */               }
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
/* 6114 */               return str1;
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 5959 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 5963 */               if (ps != null) {
/* 5964 */                 ps.close();
/*      */               }
/* 5966 */               if (rs != null) {
/* 5967 */                 AMConnectionPool.closeStatement(rs);
/*      */               }
/*      */             } catch (Exception e) {
/* 5970 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5979 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.emptymessage"), "4506");
/* 5980 */         return outputString;
/*      */         
/* 5982 */         if (groupname != null) {
/* 5983 */           if (!groupname.trim().equalsIgnoreCase("")) {
/*      */             try {
/* 5985 */               boolean ischild = false;
/*      */               try {
/* 5987 */                 rs = AMConnectionPool.executeQueryStmt("select TYPE from AM_HOLISTICAPPLICATION where HAID=" + haid);
/* 5988 */                 if ((rs.next()) && 
/* 5989 */                   ("1".equals(rs.getString("TYPE")))) {
/* 5990 */                   ischild = true;
/*      */                 }
/*      */               }
/*      */               catch (Exception e) {
/* 5994 */                 e.printStackTrace();
/*      */               }
/*      */               finally {
/* 5997 */                 AMConnectionPool.closeStatement(rs);
/*      */               }
/* 5999 */               String condition = " and ha.TYPE=0";
/* 6000 */               if (ischild) {
/* 6001 */                 condition = " and mo.RESOURCEID IN(select CHILDID from AM_PARENTCHILDMAPPER where parentid in (select parentid from AM_PARENTCHILDMAPPER where childid=" + haid + "))";
/*      */               }
/* 6003 */               ps = AMConnectionPool.getConnection().prepareStatement("select mo.RESOURCENAME from AM_ManagedObject mo,AM_HOLISTICAPPLICATION ha where mo.RESOURCEID=ha.HAID and mo.RESOURCENAME=? and mo.TYPE='HAI' and mo.RESOURCEID <> ? " + condition);
/* 6004 */               ps.setString(1, groupname);
/* 6005 */               ps.setInt(2, Integer.parseInt(haid));
/* 6006 */               rs = ps.executeQuery();
/* 6007 */               boolean grpnameval = rs.next();
/* 6008 */               if (grpnameval) {
/* 6009 */                 outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mongroup.available"), "4522");
/* 6010 */                 String str2 = outputString;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 try
/*      */                 {
/* 6018 */                   if (ps != null) {
/* 6019 */                     ps.close();
/*      */                   }
/* 6021 */                   if (rs != null) {
/* 6022 */                     AMConnectionPool.closeStatement(rs);
/*      */                   }
/*      */                 } catch (Exception e) {
/* 6025 */                   e.printStackTrace();
/*      */                 }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6114 */                 return str2;
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 6014 */               e.printStackTrace();
/*      */             }
/*      */             finally {
/*      */               try {
/* 6018 */                 if (ps != null) {
/* 6019 */                   ps.close();
/*      */                 }
/* 6021 */                 if (rs != null) {
/* 6022 */                   AMConnectionPool.closeStatement(rs);
/*      */                 }
/*      */               } catch (Exception e) {
/* 6025 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 6031 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mongroup.empty"), "4523");
/* 6032 */           return outputString;
/*      */         }
/*      */         
/*      */ 
/* 6036 */         if (checkResourceidforDelegatedAdmin(request, haid)) {
/* 6037 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 6038 */           return outputString;
/*      */         }
/*      */         
/* 6041 */         String wrongNames = "";
/* 6042 */         String ownids = null;
/* 6043 */         String lids = "";
/* 6044 */         if (owners != null) {
/* 6045 */           ownids = "";
/* 6046 */           String[] ownernames = owners.split(",");
/* 6047 */           for (String myOwn : ownernames) {
/* 6048 */             if ((myOwn != null) && (!myOwn.trim().equals(""))) {
/* 6049 */               String ownid = (String)DBUtil.username_userid_mapping.get(myOwn);
/* 6050 */               if (ownid != null) {
/* 6051 */                 ownids = ownids + ownid + ",";
/*      */               } else {
/* 6053 */                 wrongNames = wrongNames + myOwn + ",";
/*      */               }
/*      */             }
/*      */           }
/* 6057 */           if ((wrongNames != null) && (wrongNames.length() > 0)) {
/* 6058 */             wrongNames = wrongNames.substring(0, wrongNames.length() - 1);
/* 6059 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.owners.wrong", new String[] { wrongNames }), "4524");
/* 6060 */             return outputString;
/*      */           }
/* 6062 */           if ((ownids != null) && (ownids.length() > 0))
/*      */           {
/* 6064 */             ownids = ownids.substring(0, ownids.length() - 1);
/*      */           }
/*      */         }
/* 6067 */         String locids = null;
/* 6068 */         if ((location != null) && (!location.trim().equals("")))
/*      */         {
/* 6070 */           locids = chkGMapLocations(location);
/* 6071 */           if (locids == null)
/*      */           {
/* 6073 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.location.wrong"), "4525");
/* 6074 */             return outputString;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 6079 */         else if ((location != null) && (location.trim().equals(""))) {
/* 6080 */           locids = "";
/*      */         }
/* 6082 */         ShowApplication sa = new ShowApplication();
/* 6083 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 6084 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 6085 */         MSreq.setAttribute("haid", haid);
/* 6086 */         MSreq.setAttribute("name", groupname);
/* 6087 */         MSreq.setAttribute("description", description);
/* 6088 */         MSreq.setAttribute("locationid", locids);
/* 6089 */         MSreq.setAttribute("owners", ownids);
/* 6090 */         MSreq.setAttribute("fromwhere", "allmonitorgroups");
/* 6091 */         MSreq.setAttribute("applications", applications);
/* 6092 */         MSreq.setAttribute("isRESTAPI", "true");
/* 6093 */         sa.update(null, null, MSreq, response);
/*      */       }
/*      */       else {
/* 6096 */         AMLog.debug("REST API : The Monitor does have privliges under monitor group.");
/* 6097 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.privilges.wrongmessage"), "4515");
/* 6098 */         return outputString;
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
/* 6109 */       if (rs != null) {
/*      */         try {
/* 6111 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 6113 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 6118 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.group.edit.succmsg"), "4000");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6102 */       e.printStackTrace();
/* 6103 */       AMLog.audit("exception ------>" + e.getMessage());
/* 6104 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.group.edit.errmsg"), "4526", true);
/* 6105 */       return outputString;
/*      */     }
/*      */     finally
/*      */     {
/* 6109 */       if (rs != null) {
/*      */         try {
/* 6111 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 6113 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 6119 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String chkGMapLocations(String locations) {
/* 6123 */     ResultSet rs = null;
/* 6124 */     String type = "";
/* 6125 */     String id = null;
/* 6126 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/* 6129 */       ps = AMConnectionPool.getConnection().prepareStatement("select ID,NAME from AM_GMapCountryCoord where NAME =?");
/* 6130 */       ps.setString(1, locations);
/*      */       
/* 6132 */       rs = ps.executeQuery();
/* 6133 */       String name = "";
/* 6134 */       while (rs.next())
/*      */       {
/* 6136 */         if (rs.getString("ID") != null) {
/* 6137 */           id = rs.getString("ID");
/*      */         }
/*      */       }
/* 6140 */       return id;
/*      */     } catch (Exception e) {
/*      */       String str1;
/* 6143 */       e.printStackTrace();
/* 6144 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 6149 */         if (rs != null) {
/* 6150 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 6152 */         if (ps != null)
/*      */         {
/* 6154 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 6157 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean isToplevel(String haid)
/*      */   {
/* 6166 */     ResultSet rs = null;
/* 6167 */     String type = "";
/*      */     try {
/* 6169 */       String toplvlqry11 = "select TYPE from AM_HOLISTICAPPLICATION where TYPE=0 and HAID=" + haid;
/* 6170 */       rs = DBQueryUtil.executeQueryStmt(toplvlqry11);
/* 6171 */       if (rs.next())
/* 6172 */         type = rs.getString("TYPE");
/*      */       boolean bool;
/* 6174 */       if ((type != null) && (!type.equals("")) && (type.trim().equalsIgnoreCase("0"))) {
/* 6175 */         return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6192 */       return false;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6180 */       e.printStackTrace();
/* 6181 */       return false;
/*      */     }
/*      */     finally {
/* 6184 */       if (rs != null) {
/*      */         try {
/* 6186 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 6188 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getTopLevelGroup(String haid, ArrayList<String> parentIds)
/*      */   {
/* 6198 */     ResultSet rs = null;
/* 6199 */     toplevelparent = "";
/* 6200 */     String type = "";
/*      */     try {
/* 6202 */       String toplvlqry = "select pc.PARENTID,ah.TYPE from AM_PARENTCHILDMAPPER pc inner join AM_HOLISTICAPPLICATION ah on ah.HAID=pc.PARENTID where CHILDID =" + haid;
/* 6203 */       rs = DBQueryUtil.executeQueryStmt(toplvlqry);
/* 6204 */       if (rs.next()) {
/* 6205 */         toplevelparent = rs.getString("PARENTID");
/* 6206 */         type = rs.getString("TYPE");
/*      */       }
/* 6208 */       if ((type != null) && (!type.equals(""))) {
/* 6209 */         int typeint = Integer.parseInt(type);
/* 6210 */         if (typeint == 0) {
/* 6211 */           parentIds.add(toplevelparent.trim());
/* 6212 */           return toplevelparent;
/*      */         }
/*      */         
/* 6215 */         if (toplevelparent != null)
/* 6216 */           parentIds.add(toplevelparent.trim()); }
/* 6217 */       return getTopLevelGroup(toplevelparent.trim(), parentIds);
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/* 6224 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 6227 */       if (rs != null) {
/*      */         try {
/* 6229 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/* 6231 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String checkParenidforchildID(String parentID, String chilIDs)
/*      */   {
/* 6239 */     return checkParenidforchildID(parentID, chilIDs, true);
/*      */   }
/*      */   
/*      */   public static String checkParenidforchildID(String parentID, String chilIDs, boolean chkforgroup) {
/* 6243 */     ResultSet rsForQuery = null;
/* 6244 */     String childID = "";
/* 6245 */     String retrnSt = "";
/* 6246 */     String include = "";
/* 6247 */     if (chkforgroup) {
/* 6248 */       include = " and am.TYPE<>'HAI'";
/*      */     }
/* 6250 */     String parentIDQuery = "select CHILDID from AM_PARENTCHILDMAPPER pc inner join AM_ManagedObject am on pc.CHILDID=am.RESOURCEID where pc.PARENTID='" + parentID + "' and pc.CHILDID in (" + chilIDs + ")" + include;
/*      */     
/*      */ 
/* 6253 */     ArrayList<String> ChildIdArr = new ArrayList();
/*      */     try
/*      */     {
/* 6256 */       rsForQuery = AMConnectionPool.executeQueryStmt(parentIDQuery);
/* 6257 */       while (rsForQuery.next())
/*      */       {
/* 6259 */         childID = rsForQuery.getString("CHILDID");
/* 6260 */         ChildIdArr.add(childID);
/*      */       }
/* 6262 */       StringBuffer sbfail = new StringBuffer();
/* 6263 */       String[] chilIDMat = chilIDs.split(",");
/* 6264 */       boolean validmonchk = false;
/* 6265 */       for (String chID : chilIDMat) {
/* 6266 */         if (!ChildIdArr.contains(chID.trim())) {
/* 6267 */           validmonchk = true;
/* 6268 */           sbfail.append(chID.trim() + ",");
/*      */         }
/*      */       }
/* 6271 */       if (validmonchk) {
/* 6272 */         retrnSt = sbfail.toString();
/* 6273 */         retrnSt = retrnSt.substring(0, retrnSt.length() - 1);
/*      */       }
/*      */       else {
/* 6276 */         retrnSt = "success";
/*      */       }
/*      */       
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 6282 */       e.printStackTrace();
/*      */     }
/*      */     catch (Exception et) {
/* 6285 */       AMLog.audit("error while checking parentchildchecking in RestAPI--->" + et.getMessage());
/*      */     }
/*      */     finally
/*      */     {
/* 6289 */       AMConnectionPool.closeStatement(rsForQuery);
/*      */     }
/* 6291 */     return retrnSt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String AddSubGroup(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 6299 */     response.setContentType("text/xml; charset=UTF-8");
/* 6300 */     HAIDManagerAction hm = new HAIDManagerAction();
/* 6301 */     HashMap subGroupDetails = new HashMap();
/* 6302 */     String outputString = "";
/* 6303 */     String description = null;
/* 6304 */     String groupType = "0";
/* 6305 */     String locationID = request.getParameter("locationid");
/* 6306 */     String haid = null;
/* 6307 */     String userid = "1";
/* 6308 */     ResultSet rs = null;
/* 6309 */     boolean allowCreateMG = true;
/* 6310 */     PreparedStatement ps = null;
/* 6311 */     String subgrpDisplayName = request.getParameter("name");
/* 6312 */     boolean useADDM = (request.getParameter("useADDM") == null ? Boolean.FALSE : Boolean.valueOf(request.getParameter("useADDM"))).booleanValue();
/*      */     try {
/* 6314 */       if ((subgrpDisplayName != null) && (!subgrpDisplayName.equals(""))) {
/* 6315 */         subGroupDetails.put("name", subgrpDisplayName);
/*      */       } else {
/* 6317 */         AMLog.debug("REST API : The name should not be empty.");
/* 6318 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.name.emptymessage"), "4262");
/* 6319 */         return outputString;
/*      */       }
/*      */       
/* 6322 */       if (request.getParameter("description") == null) {
/* 6323 */         description = FormatUtil.getString("am.webclient.newmonitorgroup.description.textbox");
/*      */       } else {
/* 6325 */         description = request.getParameter("description");
/*      */       }
/*      */       String grouptype;
/* 6328 */       if ((request.getParameter("grouptype") != null) && (!request.getParameter("grouptype").equals(""))) {
/* 6329 */         grouptype = request.getParameter("grouptype");
/*      */         
/* 6331 */         if (("sub-group".equalsIgnoreCase(grouptype)) || ("monitorgroup".equalsIgnoreCase(grouptype))) {
/* 6332 */           groupType = "1";
/* 6333 */         } else if ("EndUserTransactionGroup".equalsIgnoreCase(grouptype)) {
/* 6334 */           groupType = "1001";
/* 6335 */         } else if (("WebServerGroup".equalsIgnoreCase(grouptype)) || ("URL".equalsIgnoreCase(grouptype)) || ("SER".equalsIgnoreCase(grouptype))) {
/* 6336 */           groupType = "1002";
/* 6337 */         } else if (("ApplicationServerGroup".equalsIgnoreCase(grouptype)) || ("APP".equalsIgnoreCase(grouptype))) {
/* 6338 */           groupType = "1003";
/* 6339 */         } else if (("DatabaseGroup".equalsIgnoreCase(grouptype)) || ("DBS".equalsIgnoreCase(grouptype))) {
/* 6340 */           groupType = "1004";
/* 6341 */         } else if ("DatabaseCluster".equalsIgnoreCase(grouptype)) {
/* 6342 */           groupType = "1005";
/* 6343 */         } else if ("ServersGroup".equalsIgnoreCase(grouptype)) {
/* 6344 */           groupType = "1006";
/* 6345 */         } else if ("EdgeDevicesGroup".equalsIgnoreCase(grouptype)) {
/* 6346 */           groupType = "1008";
/* 6347 */         } else if ("NetworkDevicesGroup".equalsIgnoreCase(grouptype)) {
/* 6348 */           groupType = "1007";
/*      */         }
/* 6350 */         else if (useADDM) {
/* 6351 */           groupType = "1";
/*      */         } else {
/* 6353 */           AMLog.debug("REST API : The grouptype should be either sub-group/WebServerGroup/DatabaseGroup/DatabaseCluster/ServersGroup/EdgeDevicesGroup/NetworkDevicesGroup/EndUserTransactionGroup/Site24x7");
/* 6354 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.grouptype.emptymessage"), "4263");
/* 6355 */           return outputString;
/*      */         }
/*      */       }
/*      */       else {
/* 6359 */         AMLog.debug("REST API : The grouptype mentioned in the request URL should not be empty.");
/* 6360 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.grouptype.emptymessage"), "4500");
/* 6361 */         return outputString;
/*      */       }
/* 6363 */       if ((request.getParameter("haid") != null) && (!request.getParameter("haid").equals(""))) {
/* 6364 */         haid = request.getParameter("haid");
/* 6365 */         String checkquery = "select RESOURCEID from AM_ManagedObject where RESOURCEID='" + haid + "' and TYPE='HAI'";
/*      */         try {
/* 6367 */           rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 6368 */           if (!rs.next()) {
/* 6369 */             AMConnectionPool.closeStatement(rs);
/* 6370 */             AMLog.debug("REST API : The given haid in the URL is wrong.");
/* 6371 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongids.message"), "4501");
/* 6372 */             return outputString;
/*      */           }
/*      */         } catch (Exception e) {
/* 6375 */           e.printStackTrace();
/*      */         }
/* 6377 */         allowCreateMG = false;
/* 6378 */       } else { if ((request.getParameter("mgname") != null) && (!request.getParameter("mgname").equals(""))) {
/* 6379 */           ps = AMConnectionPool.getConnection().prepareStatement("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME=?");
/* 6380 */           ps.setString(1, request.getParameter("mgname"));
/*      */           try {
/* 6382 */             rs = ps.executeQuery();
/* 6383 */             if (rs.next()) {
/* 6384 */               haid = rs.getString("RESOURCEID");
/* 6385 */               allowCreateMG = false;
/*      */             }
/* 6387 */             AMConnectionPool.closeStatement(rs);
/*      */           } catch (Exception e) {
/* 6389 */             e.printStackTrace();
/*      */             
/*      */             try
/*      */             {
/* 6393 */               if (ps != null) {
/* 6394 */                 ps.close();
/*      */               }
/*      */             } catch (Exception e) {
/* 6397 */               ((Exception)e).printStackTrace();
/*      */             }
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 6393 */               if (ps != null) {
/* 6394 */                 ps.close();
/*      */               }
/*      */             } catch (Exception e) {
/* 6397 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/* 6401 */         AMLog.debug("REST API : The haid/mgname should not be empty.");
/* 6402 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("The haid/mgname should not be empty"), "4502");
/* 6403 */         return outputString;
/*      */       }
/* 6405 */       if ((haid != null) && (checkResourceidforDelegatedAdmin(request, haid))) {
/* 6406 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 6407 */         return outputString;
/*      */       }
/* 6409 */       if ((haid != null) && (EnterpriseUtil.isAdminServer) && (Integer.parseInt(haid) > EnterpriseUtil.RANGE)) {
/* 6410 */         allowCreateMG = true;
/*      */       }
/* 6412 */       if (allowCreateMG) {
/* 6413 */         MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 6414 */         MSreq.setContentType("text/xml; charset=UTF-8");
/* 6415 */         MSreq.addParameter("name", request.getParameter("mgname"));
/* 6416 */         MSreq.addParameter("grouptype", "monitorgroup");
/* 6417 */         MSreq.addParameter("userid", "1");
/* 6418 */         MSreq.addParameter("locationid", "19");
/* 6419 */         MSreq.addParameter("needhaid", "true");
/* 6420 */         MSreq.addParameter("description", description);
/* 6421 */         MSreq.addParameter("useADDM", useADDM + "");
/* 6422 */         haid = addMonitorGroup(MSreq, response);
/*      */       }
/* 6424 */       if ((request.getParameter("userid") != null) && (!request.getParameter("userid").equals(""))) {
/* 6425 */         userid = request.getParameter("userid");
/*      */       }
/* 6427 */       String[] owners = userid.split(",");
/* 6428 */       subGroupDetails.put("selectedowners_list", owners);
/* 6429 */       subGroupDetails.put("locationid", locationID);
/* 6430 */       subGroupDetails.put("haid", haid);
/* 6431 */       subGroupDetails.put("subGroupType", groupType);
/* 6432 */       subGroupDetails.put("description", description);
/* 6433 */       int result = 0;
/* 6434 */       PreparedStatement pst = null;
/* 6435 */       pst = AMConnectionPool.getConnection().prepareStatement("select am_mo.RESOURCEID from AM_PARENTCHILDMAPPER am_pcm,AM_ManagedObject am_mo,AM_HOLISTICAPPLICATION am_ha where am_mo.resourceid=am_pcm.childid and am_mo.RESOURCEID=am_ha.HAID and am_mo.TYPE='HAI' and  am_pcm.PARENTID IN (?) and am_mo.DISPLAYNAME=? and am_ha.GROUPTYPE=?");
/* 6436 */       pst.setInt(1, Integer.parseInt(haid));
/* 6437 */       pst.setString(2, request.getParameter("name"));
/* 6438 */       pst.setInt(3, Integer.parseInt(groupType));
/* 6439 */       rs = pst.executeQuery();
/*      */       try {
/* 6441 */         if (rs.next()) {
/* 6442 */           AMLog.debug("REST API : The name for adding sub-Group already exists.");
/* 6443 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroupname.msg"), "4261");
/* 6444 */           String str2 = outputString;
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
/*      */           try
/*      */           {
/* 6457 */             if (pst != null) {
/* 6458 */               pst.close();
/*      */             }
/*      */           } catch (Exception e) {
/* 6461 */             e.printStackTrace();
/*      */           }
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
/* 6487 */           return str2;
/*      */         }
/* 6446 */         result = hm.createSubGroup(subGroupDetails);
/* 6447 */         if (useADDM) {
/* 6448 */           ParentChildRelationalUtil.addToUseAddmTable(result);
/*      */         }
/*      */         
/* 6451 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 6457 */           if (pst != null) {
/* 6458 */             pst.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 6461 */           e.printStackTrace();
/*      */         }
/*      */         
/* 6464 */         request.setAttribute("resourceid", result + "");
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 6453 */         e.printStackTrace();
/*      */         
/*      */         try
/*      */         {
/* 6457 */           if (pst != null) {
/* 6458 */             pst.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 6461 */           ((Exception)e).printStackTrace();
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/* 6457 */           if (pst != null) {
/* 6458 */             pst.close();
/*      */           }
/*      */         } catch (Exception e) {
/* 6461 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 6465 */       if (result == -1) {
/* 6466 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.errmsg"), "4503", true);
/* 6467 */         return outputString;
/*      */       }
/*      */       try {
/* 6470 */         String auditLogMsg = FormatUtil.getString("am.audit.addSubgroup.msg", new String[] { result + "", subgrpDisplayName, DBUtil.getDisplaynameforResourceID(haid) });
/* 6471 */         cliAuditUtil.addToAuditLog(request, Integer.parseInt(haid), 2, auditLogMsg);
/*      */       }
/*      */       catch (Exception e) {
/* 6474 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 6484 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ef) {
/* 6486 */         ef.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 6490 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.succmsg"), "4000", true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6478 */       e.printStackTrace();
/* 6479 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.subgroup.errmsg"), "4503", true);
/* 6480 */       return outputString;
/*      */     }
/*      */     finally {
/*      */       try {
/* 6484 */         AMConnectionPool.closeStatement(rs);
/*      */       } catch (Exception ef) {
/* 6486 */         ef.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 6491 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteGroup(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6496 */     ResultSet rs = null;
/* 6497 */     PreparedStatement ps = null;
/* 6498 */     ArrayList haID = new ArrayList();
/* 6499 */     String wrongID = "";
/* 6500 */     String outputString = "";
/*      */     try { String resIDs;
/* 6502 */       if (request.getParameter("haid") != null) {
/* 6503 */         ps = AMConnectionPool.getConnection().prepareStatement("select HAID from AM_HOLISTICAPPLICATION Ha,AM_ManagedObject Mo where Ha.HAID=Mo.RESOURCEID and Ha.TYPE=0 and Mo.RESOURCEID=?");
/* 6504 */         resIDs = request.getParameter("haid");
/* 6505 */         String resID; if (resIDs.contains(",")) {
/* 6506 */           resID = null;
/* 6507 */           StringTokenizer st = new StringTokenizer(resIDs, ",");
/* 6508 */           for (int i = 0; st.hasMoreTokens(); i++) {
/* 6509 */             resID = st.nextToken();
/* 6510 */             ps.setInt(1, Integer.parseInt(resID));
/*      */             try {
/* 6512 */               rs = ps.executeQuery();
/* 6513 */               if (rs.next()) {
/* 6514 */                 haID.add(rs.getString("HAID"));
/*      */               } else {
/* 6516 */                 wrongID = wrongID.concat(resID + ",");
/* 6517 */                 i--;
/*      */               }
/*      */             } catch (Exception e) {
/* 6520 */               e.printStackTrace();
/*      */             } finally {
/* 6522 */               AMConnectionPool.closeResultSet(rs);
/*      */             }
/*      */           }
/*      */           
/* 6526 */           if (haID.size() == 0) {
/* 6527 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong.all"), "4510");
/* 6528 */             return outputString;
/*      */           }
/*      */         }
/*      */         else {
/* 6532 */           ps.setInt(1, Integer.parseInt(resIDs));
/*      */           try {
/* 6534 */             rs = ps.executeQuery();
/* 6535 */             if (rs.next()) {
/* 6536 */               haID.add(rs.getString("HAID"));
/*      */             } else {
/* 6538 */               AMConnectionPool.closeStatement(rs);
/* 6539 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong"), "4510");
/* 6540 */               resID = outputString;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 6545 */               AMConnectionPool.closeResultSet(rs);
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
/* 6608 */               return resID;
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 6543 */             e.printStackTrace();
/*      */           } finally {
/* 6545 */             AMConnectionPool.closeResultSet(rs);
/*      */           }
/*      */         }
/* 6548 */       } else if (request.getParameter("name") != null) {
/* 6549 */         ps = AMConnectionPool.getConnection().prepareStatement("select HAID from AM_HOLISTICAPPLICATION Ha,AM_ManagedObject Mo  where Ha.HAID=Mo.RESOURCEID and Ha.TYPE=0 and Mo.RESOURCENAME=?");
/* 6550 */         ps.setString(1, request.getParameter("name"));
/*      */         try {
/* 6552 */           rs = ps.executeQuery();
/* 6553 */           if (rs.next()) {
/* 6554 */             haID.add(rs.getString("HAID"));
/*      */           } else {
/* 6556 */             AMConnectionPool.closeStatement(rs);
/* 6557 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.name.wrong"), "4511");
/* 6558 */             resIDs = outputString;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 6563 */             AMConnectionPool.closeResultSet(rs);
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
/* 6608 */             return resIDs;
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 6561 */           e.printStackTrace();
/*      */         } finally {
/* 6563 */           AMConnectionPool.closeResultSet(rs);
/*      */         }
/*      */       } else {
/* 6566 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haidmgname.emptymessage"), "4502");
/* 6567 */         return outputString;
/*      */       }
/* 6569 */       if (!haID.isEmpty()) {
/* 6570 */         for (int i = 0; i < haID.size(); i++) {
/*      */           try {
/* 6572 */             String haid = (String)haID.get(i);
/* 6573 */             if (checkResourceidforDelegatedAdmin(request, haid)) {
/* 6574 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 6575 */               return outputString;
/*      */             }
/* 6577 */             hm = new HAIDManagerAction();
/* 6578 */             MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 6579 */             MSreq.setContentType("text/xml; charset=UTF-8");
/* 6580 */             ServletContext servletContext = request.getSession().getServletContext();
/* 6581 */             Hashtable ht = (Hashtable)servletContext.getAttribute("applications");
/* 6582 */             MSreq.addParameter("select", haid);
/* 6583 */             MSreq.setAttribute("applications", ht);
/* 6584 */             String apikey = request.getParameter("apikey");
/* 6585 */             String remoteHost = request.getRemoteAddr();
/* 6586 */             MSreq.addParameter("apikey", apikey);
/* 6587 */             MSreq.addParameter("host", remoteHost);
/* 6588 */             hm.delete(null, null, MSreq, response);
/*      */           } catch (Exception ed) { HAIDManagerAction hm;
/* 6590 */             ed.printStackTrace();
/* 6591 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.error.delete"), "4512");
/* 6592 */             return outputString;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 6603 */         if (ps != null) {
/* 6604 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 6607 */         e.printStackTrace();
/*      */       }
/* 6609 */       if (wrongID == "") {
/*      */         break label997;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6597 */       e.printStackTrace();
/* 6598 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.error.delete"), "4500");
/* 6599 */       return outputString;
/*      */     }
/*      */     finally {
/*      */       try {
/* 6603 */         if (ps != null) {
/* 6604 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 6607 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 6610 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong.specified", new String[] { wrongID.toString() }), "4000");
/*      */     break label1012;
/*      */     label997:
/* 6613 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("haid.applicationdeletion.success"), "4000");
/*      */     label1012:
/* 6615 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteSubGroup(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6620 */     ResultSet rs = null;
/* 6621 */     PreparedStatement ps = null;
/* 6622 */     ArrayList haID = new ArrayList();
/* 6623 */     String wrongID = "";
/* 6624 */     String outputString = "";
/*      */     try { String resIDs;
/* 6626 */       if (request.getParameter("haid") != null) {
/* 6627 */         ps = AMConnectionPool.getConnection().prepareStatement("select HAID from AM_HOLISTICAPPLICATION Ha,AM_ManagedObject Mo where Ha.HAID=Mo.RESOURCEID and Ha.TYPE=1 and Mo.RESOURCEID=?");
/* 6628 */         resIDs = request.getParameter("haid");
/* 6629 */         String resID; if (resIDs.contains(",")) {
/* 6630 */           resID = null;
/* 6631 */           StringTokenizer st = new StringTokenizer(resIDs, ",");
/* 6632 */           for (int i = 0; st.hasMoreTokens(); i++) {
/* 6633 */             resID = st.nextToken();
/* 6634 */             ps.setInt(1, Integer.parseInt(resID));
/*      */             try {
/* 6636 */               rs = ps.executeQuery();
/* 6637 */               if (rs.next()) {
/* 6638 */                 haID.add(rs.getString("HAID"));
/*      */               } else {
/* 6640 */                 wrongID = wrongID.concat(resID + ",");
/* 6641 */                 i--;
/*      */               }
/*      */             } catch (Exception e) {
/* 6644 */               e.printStackTrace();
/*      */             } finally {
/* 6646 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */           }
/*      */           
/* 6650 */           if (haID.size() == 0) {
/* 6651 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong.all"), "4510");
/* 6652 */             return outputString;
/*      */           }
/*      */         }
/*      */         else {
/* 6656 */           ps.setInt(1, Integer.parseInt(resIDs));
/*      */           try {
/* 6658 */             rs = ps.executeQuery();
/* 6659 */             if (rs.next()) {
/* 6660 */               haID.add(rs.getString("HAID"));
/*      */             } else {
/* 6662 */               AMConnectionPool.closeStatement(rs);
/* 6663 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong"), "4510");
/* 6664 */               resID = outputString;
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 6669 */               AMConnectionPool.closeResultSet(rs);
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
/* 6732 */               return resID;
/*      */             }
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 6667 */             e.printStackTrace();
/*      */           } finally {
/* 6669 */             AMConnectionPool.closeResultSet(rs);
/*      */           }
/*      */         }
/* 6672 */       } else if (request.getParameter("name") != null) {
/* 6673 */         ps = AMConnectionPool.getConnection().prepareStatement("select HAID from AM_HOLISTICAPPLICATION Ha,AM_ManagedObject Mo  where Ha.HAID=Mo.RESOURCEID and Ha.TYPE=1 and Mo.RESOURCENAME=?");
/* 6674 */         ps.setString(1, request.getParameter("name"));
/*      */         try {
/* 6676 */           rs = ps.executeQuery();
/* 6677 */           if (rs.next()) {
/* 6678 */             haID.add(rs.getString("HAID"));
/*      */           } else {
/* 6680 */             AMConnectionPool.closeStatement(rs);
/* 6681 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong"), "4511");
/* 6682 */             resIDs = outputString;
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 6687 */             AMConnectionPool.closeResultSet(rs);
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
/* 6732 */             return resIDs;
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 6685 */           e.printStackTrace();
/*      */         } finally {
/* 6687 */           AMConnectionPool.closeResultSet(rs);
/*      */         }
/*      */       } else {
/* 6690 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haidmgname.emptymessage"), "4502");
/* 6691 */         return outputString;
/*      */       }
/* 6693 */       if (!haID.isEmpty()) {
/* 6694 */         for (int i = 0; i < haID.size(); i++) {
/*      */           try {
/* 6696 */             String haid = (String)haID.get(i);
/* 6697 */             if (checkResourceidforDelegatedAdmin(request, haid)) {
/* 6698 */               outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mg.delegatedAdmin.check.text"), "4500");
/* 6699 */               return outputString;
/*      */             }
/* 6701 */             hm = new HAIDManagerAction();
/* 6702 */             MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 6703 */             MSreq.setContentType("text/xml; charset=UTF-8");
/* 6704 */             ServletContext servletContext = request.getSession().getServletContext();
/* 6705 */             Hashtable ht = (Hashtable)servletContext.getAttribute("applications");
/* 6706 */             MSreq.addParameter("select", haid);
/* 6707 */             MSreq.setAttribute("applications", ht);
/* 6708 */             String apikey = request.getParameter("apikey");
/* 6709 */             String remoteHost = request.getRemoteAddr();
/* 6710 */             MSreq.addParameter("apikey", apikey);
/* 6711 */             MSreq.addParameter("host", remoteHost);
/* 6712 */             hm.delete(null, null, MSreq, response);
/*      */           } catch (Exception ed) { HAIDManagerAction hm;
/* 6714 */             ed.printStackTrace();
/* 6715 */             outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sg.error.delete"), "4513");
/* 6716 */             return outputString;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 6727 */         if (ps != null) {
/* 6728 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 6731 */         e.printStackTrace();
/*      */       }
/* 6733 */       if (wrongID == "") {
/*      */         break label997;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 6721 */       e.printStackTrace();
/* 6722 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sg.error.delete"), "4513");
/* 6723 */       return outputString;
/*      */     }
/*      */     finally {
/*      */       try {
/* 6727 */         if (ps != null) {
/* 6728 */           ps.close();
/*      */         }
/*      */       } catch (Exception e) {
/* 6731 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 6734 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.haid.wrong.specified", new String[] { wrongID.toString() }), "4000");
/*      */     break label1012;
/*      */     label997:
/* 6737 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("haid.applicationdeletion.success"), "4000");
/*      */     label1012:
/* 6739 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String createUser(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6744 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/* 6745 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 6746 */     if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/* 6747 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */     }
/* 6749 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/* 6751 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/* 6753 */     String uri = request.getRequestURI();
/* 6754 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 6755 */     if (isJsonFormat) {
/* 6756 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 6758 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/*      */ 
/* 6762 */     String userName = request.getParameter("userName");
/* 6763 */     String password = request.getParameter("password");
/* 6764 */     String role = request.getParameter("role");
/* 6765 */     String aamUserId = request.getParameter("aamUserId");
/* 6766 */     String emailId = "";
/* 6767 */     if (request.getParameter("email") != null) {
/* 6768 */       emailId = request.getParameter("email");
/*      */     }
/* 6770 */     String description = "";
/* 6771 */     if (request.getParameter("description") != null) {
/* 6772 */       description = request.getParameter("description");
/*      */     }
/* 6774 */     String apikey = "";
/* 6775 */     if ((isAPICallFromAAM) && (request.getParameter("apikeyofuser") != null)) {
/* 6776 */       apikey = request.getParameter("apikeyofuser");
/*      */     }
/* 6778 */     Vector errList = new Vector();
/* 6779 */     if ((userName == null) || (userName.equals(""))) {
/* 6780 */       errList.add("userName");
/*      */     }
/* 6782 */     if (((password == null) || (password.equals(""))) && (request.getParameter("domainname") == null) && (request.getParameter("domainid") == null)) {
/* 6783 */       errList.add("password");
/*      */     }
/* 6785 */     if ((role == null) || (role.equals(""))) {
/* 6786 */       errList.add("Role");
/*      */     }
/*      */     
/* 6789 */     String restrictedAdmin = request.getParameter("delegatedadmin");
/* 6790 */     if (("ADMIN".equals(role)) && (restrictedAdmin == null)) {
/* 6791 */       errList.add("delegatedadmin");
/*      */     }
/* 6793 */     if (restrictedAdmin != null) {
/* 6794 */       if ("true".equalsIgnoreCase(restrictedAdmin)) {
/* 6795 */         restrictedAdmin = "0";
/*      */       } else {
/* 6797 */         restrictedAdmin = "1";
/*      */       }
/*      */     } else {
/* 6800 */       restrictedAdmin = "1";
/*      */     }
/*      */     
/* 6803 */     if (!errList.isEmpty()) {
/* 6804 */       AMLog.debug("REST API : Parameters shouldnot be empty.");
/* 6805 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { getListAsString(errList) }), "4600");
/* 6806 */       return outputString;
/*      */     }
/*      */     
/* 6809 */     if (userName.length() > 50) {
/* 6810 */       AMLog.debug("REST API : user name length exceeded.");
/* 6811 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("user.creation.namelengthexceed.text"), "4601");
/* 6812 */       return outputString;
/*      */     }
/*      */     
/* 6815 */     if (role != null) {
/* 6816 */       role = role.toUpperCase();
/*      */     }
/*      */     
/* 6819 */     List validUserRoles = new ArrayList(UserConfigurationUtil.USER_GROUPS);
/* 6820 */     if (isAPICallFromAAM)
/*      */     {
/* 6822 */       validUserRoles.add("REPORTER");
/*      */     }
/* 6824 */     if (!validUserRoles.contains(role)) {
/* 6825 */       AMLog.debug("REST API :Inavalid user group.");
/* 6826 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.invaliduserrole.text"), "4602");
/* 6827 */       return outputString;
/*      */     }
/*      */     
/* 6830 */     if (!PluginUtil.isPlugin()) {
/* 6831 */       String userAllowedResult = UserConfigurationUtil.CheckNewUserAvailability();
/* 6832 */       if (!userAllowedResult.equals("true")) {
/* 6833 */         AMLog.debug("REST API : user count exceeded.");
/* 6834 */         String outputString = URITree.generateXML(request, response, userAllowedResult, "4603");
/* 6835 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 6839 */     if (UserConfigurationUtil.isUserExist(userName, null).booleanValue()) {
/* 6840 */       AMLog.debug("REST API : user exist");
/* 6841 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("user.creation.nameexists", new String[] { userName }), "4604");
/* 6842 */       return outputString;
/*      */     }
/*      */     
/* 6845 */     ArrayList<String> selectedGroups = new ArrayList();
/* 6846 */     ArrayList<String> selectedusergroup = new ArrayList();
/* 6847 */     boolean isUserGroup = false;
/* 6848 */     if ((role.equals("OPERATOR")) || (role.equals("MANAGER")) || (role.equals("ADMIN")) || (role.equals("ENTERPRISEADMIN"))) {
/* 6849 */       if ((request.getParameter("groupId") != null) && (!request.getParameter("groupId").equals(""))) {
/* 6850 */         String[] groupId = request.getParameter("groupId").split(",");
/* 6851 */         if (isAPICallFromAAM) {
/* 6852 */           selectedGroups = new ArrayList(Arrays.asList(groupId));
/* 6853 */           getRelevantResourceIds(selectedGroups);
/*      */         } else {
/* 6855 */           selectedGroups = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("groupId") + ")");
/* 6856 */           if ((!isAPICallFromAAM) && (groupId.length != selectedGroups.size())) {
/* 6857 */             AMLog.debug("REST API: The groupid in the request url is wrong or the values are repeated.");
/* 6858 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupid.message"), "4605");
/* 6859 */             return outputString;
/*      */           }
/*      */         }
/* 6862 */       } else if ((request.getParameter("groupName") != null) && (!request.getParameter("groupName").equals(""))) {
/* 6863 */         selectedGroups = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("groupName") + "'");
/* 6864 */         if (selectedGroups.size() == 0) {
/* 6865 */           AMLog.debug("REST API: The groupName in the request url is wrong ");
/* 6866 */           String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupname.message"), "4606");
/* 6867 */           return outputString;
/*      */         }
/*      */       }
/*      */       
/* 6871 */       if ((request.getParameter("usergroupId") != null) && (!request.getParameter("usergroupId").equals(""))) {
/* 6872 */         String[] usergroupid = request.getParameter("usergroupId").split(",");
/* 6873 */         if (isAPICallFromAAM) {
/* 6874 */           selectedusergroup = new ArrayList(Arrays.asList(usergroupid));
/*      */         } else {
/* 6876 */           selectedusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPID in (" + request.getParameter("usergroupId") + ")");
/* 6877 */           if (usergroupid.length != selectedusergroup.size()) {
/* 6878 */             AMLog.debug("REST API: The usergroupid in the request url is wrong or the values are repeated.");
/* 6879 */             String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociateusergroupid.message"), "4605");
/* 6880 */             return outputString;
/*      */           }
/*      */         }
/* 6883 */         isUserGroup = true;
/* 6884 */       } else if ((request.getParameter("usergroupName") != null) && (!request.getParameter("usergroupName").equals(""))) {
/* 6885 */         selectedusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME = '" + request.getParameter("usergroupName") + "'");
/* 6886 */         if (selectedusergroup.size() == 0) {
/* 6887 */           AMLog.debug("REST API: The usergroupName in the request url is wrong ");
/* 6888 */           String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociateusergroupname.message"), "4606");
/* 6889 */           return outputString;
/*      */         }
/* 6891 */         isUserGroup = true;
/*      */       }
/*      */     }
/* 6894 */     ArrayList domainDetails = new ArrayList();
/* 6895 */     ArrayList adUserDetails = new ArrayList();
/* 6896 */     boolean validateDomainUser = false;
/* 6897 */     boolean createDomainUser = false;
/* 6898 */     int domainid = -1;
/* 6899 */     String forceadd = request.getParameter("forceadd");
/* 6900 */     if ("true".equals(forceadd)) {
/* 6901 */       validateDomainUser = false;
/*      */ 
/*      */     }
/* 6904 */     else if (request.getParameter("domainname") != null) {
/* 6905 */       String domainName = request.getParameter("domainname");
/* 6906 */       domainid = ADAuthenticationUtil.getDomainID(domainName);
/* 6907 */       if (domainid != 0) {
/* 6908 */         domainDetails = ADAuthenticationUtil.getDomainDetails(String.valueOf(domainid), true);
/* 6909 */         if (domainDetails.size() > 0) {
/* 6910 */           validateDomainUser = true;
/*      */         }
/*      */       }
/*      */     }
/* 6914 */     else if (request.getParameter("domainid") != null) {
/* 6915 */       String domainID = request.getParameter("domainid");
/*      */       
/* 6917 */       domainDetails = ADAuthenticationUtil.getDomainDetails(domainID, true);
/* 6918 */       if (domainDetails.size() > 0) {
/* 6919 */         domainid = Integer.parseInt(domainID);
/* 6920 */         validateDomainUser = true;
/*      */       }
/*      */     }
/*      */     
/* 6924 */     String distinguishedName = null;
/*      */     
/* 6926 */     if ("true".equalsIgnoreCase(request.getParameter("forceadd"))) {
/* 6927 */       validateDomainUser = false;
/* 6928 */       createDomainUser = true;
/* 6929 */       createDomainUser = true;
/*      */     }
/*      */     
/* 6932 */     if (validateDomainUser)
/*      */     {
/* 6934 */       HashMap<String, String> details = new HashMap();
/* 6935 */       details.put("username", (String)domainDetails.get(5));
/* 6936 */       details.put("credentials", (String)domainDetails.get(7));
/* 6937 */       details.put("domianName", (String)domainDetails.get(1));
/* 6938 */       details.put("dnsHost", (String)domainDetails.get(2));
/* 6939 */       details.put("searchBase", (String)domainDetails.get(4));
/* 6940 */       details.put("port", domainDetails.get(3) + "");
/* 6941 */       details.put("importdata", "userlist");
/* 6942 */       details.put("addNewUser", "true");
/* 6943 */       details.put("searchUserName", userName);
/* 6944 */       if ("LDAP".equalsIgnoreCase((String)domainDetails.get(6))) {
/* 6945 */         details.put("server", "ldap");
/*      */       } else {
/* 6947 */         details.put("server", "ad");
/*      */       }
/* 6949 */       DirContext context = ADAuthenticationUtil.authenticateADUser(details);
/* 6950 */       if (context != null)
/*      */       {
/* 6952 */         Attributes attrs = null;
/* 6953 */         NamingEnumeration answer = ADAuthenticationUtil.searchContext(context, details);
/* 6954 */         if (answer != null)
/*      */         {
/* 6956 */           String primarygroupid = null;
/* 6957 */           while (answer.hasMoreElements()) {
/*      */             try
/*      */             {
/* 6960 */               SearchResult sr = (SearchResult)answer.next();
/* 6961 */               attrs = sr.getAttributes();
/* 6962 */               if (attrs != null) {
/* 6963 */                 details.put("dn", sr.getNameInNamespace());
/* 6964 */                 if ((attrs != null) && (attrs.get((String)details.get("usersamName")) != null)) {
/* 6965 */                   String newADUserName = (String)attrs.get((String)details.get("usersamName")).get();
/* 6966 */                   if (userName.equals(newADUserName)) {
/* 6967 */                     distinguishedName = sr.getNameInNamespace();
/* 6968 */                     createDomainUser = true;
/*      */                   }
/*      */                 }
/* 6971 */                 break;
/*      */               }
/*      */             } catch (Exception ex) {
/* 6974 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 6981 */         ArrayList groupmem = new ArrayList();
/* 6982 */         String primarygroupid = null;
/* 6983 */         StringBuilder groupSearchFilter = new StringBuilder();
/*      */         try {
/* 6985 */           String[] memberOf = details.get("userMemberOf") != null ? ((String)details.get("userMemberOf")).split(";") : new String[0];
/* 6986 */           String groupid = (String)details.get("userGroupid");
/* 6987 */           for (String member : memberOf) {
/* 6988 */             if ((attrs != null) && (attrs.get(member) != null)) {
/* 6989 */               NamingEnumeration groupEnum = attrs.get(member).getAll();
/*      */               
/*      */               try
/*      */               {
/* 6993 */                 while (groupEnum.hasMoreElements()) {
/* 6994 */                   groupSearchFilter.append("(").append(((String)groupEnum.next()).split(",", 2)[0]).append(")");
/*      */                 }
/*      */               } catch (Exception ex) {
/* 6997 */                 ex.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 7002 */           String[] groupMemberAttribute = details.get("groupMember") != null ? ((String)details.get("groupMember")).split(";") : new String[0];
/* 7003 */           for (String groupMember : groupMemberAttribute) {
/* 7004 */             groupSearchFilter.append("(").append(groupMember).append("=").append(distinguishedName).append(")");
/*      */           }
/*      */           
/*      */ 
/* 7008 */           if ((groupid != null) && (attrs != null) && (attrs.get(groupid) != null)) {
/* 7009 */             primarygroupid = (String)attrs.get(groupid).get();
/*      */           }
/*      */           
/* 7012 */           String grouptokenAttribute = (String)details.get("groupTokenId");
/* 7013 */           if ((grouptokenAttribute != null) && (primarygroupid != null)) {
/* 7014 */             details.put("importdata", "usergroup");
/*      */             
/* 7016 */             NamingEnumeration groupanswers = ADAuthenticationUtil.searchContext(context, details);
/* 7017 */             while (groupanswers.hasMoreElements()) {
/*      */               try
/*      */               {
/* 7020 */                 SearchResult sr = (SearchResult)groupanswers.next();
/* 7021 */                 attrs = sr.getAttributes();
/* 7022 */                 if ((attrs != null) && (attrs.get(grouptokenAttribute) != null)) {
/* 7023 */                   String groupTokenId = (String)attrs.get(grouptokenAttribute).get();
/* 7024 */                   if ((groupTokenId.equalsIgnoreCase(primarygroupid)) && (attrs != null) && (attrs.get((String)details.get("groupsamName")) != null)) {
/* 7025 */                     String groupName = (String)attrs.get((String)details.get("groupsamName")).get();
/* 7026 */                     groupmem.add(groupName);
/*      */                   }
/*      */                 }
/*      */               } catch (Exception ex) {
/* 7030 */                 ex.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 7036 */           ex.printStackTrace();
/*      */         }
/*      */         
/* 7039 */         if (groupSearchFilter.toString().trim().length() > 2) {
/* 7040 */           details.put("importdata", "usergroup");
/* 7041 */           details.put("groupFilterParam", groupSearchFilter.toString());
/* 7042 */           NamingEnumeration groupanswers = ADAuthenticationUtil.searchContext(context, details);
/* 7043 */           String primaryUserGroup = null;
/* 7044 */           while (groupanswers.hasMoreElements()) {
/*      */             try
/*      */             {
/* 7047 */               SearchResult sr = (SearchResult)groupanswers.next();
/* 7048 */               attrs = sr.getAttributes();
/* 7049 */               if ((attrs != null) && (attrs.get((String)details.get("groupsamName")) != null))
/*      */               {
/* 7051 */                 String groupName = (String)attrs.get((String)details.get("groupsamName")).get();
/* 7052 */                 groupmem.add(groupName);
/*      */               }
/*      */             } catch (Exception ex) {
/* 7055 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 7060 */           ArrayList<HashMap<String, String>> arr = new ArrayList();
/* 7061 */           ResultSet rs = null;
/*      */           try {
/* 7063 */             rs = AMConnectionPool.executeQueryStmt("select AM_USERGROUP_CONFIG.GROUPID,GROUPNAME from AM_USERGROUP_CONFIG, AM_DOMAINUSERGROUP_MAPPING where AM_USERGROUP_CONFIG.GROUPID=AM_DOMAINUSERGROUP_MAPPING.GROUPID and AM_DOMAINUSERGROUP_MAPPING.DOMAINID =" + domainid);
/* 7064 */             while (rs.next()) {
/* 7065 */               HashMap<String, String> map = new HashMap();
/* 7066 */               map.put("groupname", rs.getString("GROUPNAME"));
/* 7067 */               map.put("groupid", rs.getString("GROUPID"));
/* 7068 */               arr.add(map);
/*      */             }
/*      */           } catch (Exception ex) {
/* 7071 */             ex.printStackTrace();
/*      */           } finally {
/* 7073 */             if (rs != null) {
/* 7074 */               AMConnectionPool.closeResultSet(rs);
/*      */             }
/*      */           }
/*      */           
/* 7078 */           for (int i = 0; i < arr.size(); i++) {
/* 7079 */             HashMap<String, String> map = (HashMap)arr.get(i);
/* 7080 */             String groupname = (String)map.get("groupname");
/* 7081 */             if (groupmem.contains(groupname)) {
/* 7082 */               isUserGroup = true;
/* 7083 */               selectedusergroup.add(map.get("groupid"));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 7092 */     int createdUserId = -1;
/* 7093 */     if (createDomainUser) {
/* 7094 */       HashMap<String, String> adUser = new HashMap();
/* 7095 */       adUser.put("role", role);
/* 7096 */       if ("true".equals(forceadd)) {
/* 7097 */         adUser.put("domainid", request.getParameter("domainid"));
/*      */       } else {
/* 7099 */         adUser.put("domainid", domainid + "");
/*      */       }
/*      */       
/* 7102 */       adUser.put("username", userName);
/* 7103 */       if (request.getParameter("dn") != null) {
/* 7104 */         distinguishedName = request.getParameter("dn");
/*      */       }
/* 7106 */       adUser.put("dn", distinguishedName);
/* 7107 */       if ((aamUserId != null) && (aamUserId.trim().length() > 0)) {
/* 7108 */         adUser.put("aamUserId", aamUserId);
/*      */       }
/* 7110 */       if ((role != null) && (role.trim().length() > 0)) {
/* 7111 */         adUser.put("role", role);
/*      */       }
/* 7113 */       adUser.put("restrictedAdmin", restrictedAdmin);
/* 7114 */       adUser.put("isAPICallFromAAM", String.valueOf(isAPICallFromAAM));
/* 7115 */       createdUserId = UserConfigurationUtil.createDomainUser(adUser);
/*      */     } else {
/* 7117 */       if (password == null) {
/* 7118 */         AMLog.debug("REST API : Parameters shouldnot be empty.");
/* 7119 */         errList.add("password");
/* 7120 */         String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { getListAsString(errList) }), "4600");
/* 7121 */         return outputString;
/*      */       }
/* 7123 */       String md5Password = request.getParameter("md5Password");
/* 7124 */       if ((isAPICallFromAAM) && (md5Password != null) && (md5Password.equals("false")))
/*      */       {
/* 7126 */         password = Coding.convertFromBase(password);
/*      */       }
/* 7128 */       Properties userProps = new Properties();
/* 7129 */       userProps.setProperty("userName", userName);
/* 7130 */       userProps.setProperty("password", password);
/* 7131 */       userProps.setProperty("email", emailId);
/* 7132 */       userProps.setProperty("description", description);
/* 7133 */       userProps.setProperty("restrictedAdmin", restrictedAdmin);
/* 7134 */       if (request.getParameter("apikeyofuser") != null) {
/* 7135 */         userProps.setProperty("apikeyofuser", apikey);
/*      */       }
/* 7137 */       if (md5Password != null) {
/* 7138 */         userProps.setProperty("md5Password", md5Password);
/*      */       }
/* 7140 */       if ((aamUserId != null) && (aamUserId.trim().length() > 0)) {
/* 7141 */         userProps.setProperty("aamUserId", aamUserId);
/*      */       }
/* 7143 */       if ((role != null) && (role.trim().length() > 0)) {
/* 7144 */         userProps.setProperty("role", role);
/*      */       }
/* 7146 */       userProps.setProperty("isAPICallFromAAM", String.valueOf(isAPICallFromAAM));
/* 7147 */       createdUserId = UserConfigurationUtil.insertUserDetails(userProps);
/*      */     }
/* 7149 */     if (createdUserId == -1) {
/* 7150 */       AMLog.debug("REST API : server exception occured");
/* 7151 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.create.exception"), "4000");
/* 7152 */       return outputString;
/*      */     }
/*      */     
/* 7155 */     UserConfigurationUtil.updateGroups(userName, new String[] { role });
/*      */     
/* 7157 */     if ((isUserGroup) && (selectedusergroup.size() > 0)) {
/* 7158 */       ArrayList<String> mgs = UserConfigurationUtil.updateOwnerUserGroups((String[])selectedusergroup.toArray(new String[selectedusergroup.size()]), createdUserId, false);
/* 7159 */       for (String id : mgs) {
/* 7160 */         if (!selectedGroups.contains(id)) {
/* 7161 */           selectedGroups.add(id);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 7166 */     if (selectedGroups.size() > 0) {
/* 7167 */       UserConfigurationUtil.updateMonitorGroupDetails(userName, createdUserId + "", role, selectedGroups);
/*      */     }
/*      */     
/* 7170 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 7171 */     usersList = UserConfigurationUtil.getUserListDetails(createdUserId);
/* 7172 */     UserConfigurationUtil.updateUserPrivileges(request, userName);
/*      */     
/*      */ 
/* 7175 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate)) {
/*      */       try
/*      */       {
/* 7178 */         AMLog.debug("[CommonAPIUtil::(createUser)]ruser(s) : " + createdUserId);
/* 7179 */         RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(String.valueOf(createdUserId));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 7183 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 7188 */     if ("true".equals(request.getParameter("profilecreationscript"))) {
/* 7189 */       if (usersList.size() == 0) {
/* 7190 */         AMLog.debug("REST API: Problem in adding the user goup");
/* 7191 */         String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.create.failure.text"), "4500");
/* 7192 */         return outputString;
/*      */       }
/* 7194 */       AMLog.debug("REST API: Problem in adding the user goup");
/* 7195 */       String outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.create.success.text"), "4000");
/* 7196 */       return outputString;
/*      */     }
/*      */     
/* 7199 */     if (usersList.size() == 0) {
/* 7200 */       Hashtable msg = new Hashtable();
/* 7201 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nouserdetails.message"));
/* 7202 */       usersList.add(msg);
/*      */     }
/* 7204 */     HashMap results = new HashMap();
/* 7205 */     results.put("response-code", "4000");
/* 7206 */     results.put("uri", uri);
/* 7207 */     results.put("result", usersList);
/* 7208 */     results.put("sortingParam", "userName");
/* 7209 */     results.put("parentNode", "Users");
/* 7210 */     results.put("nodeName", "User");
/* 7211 */     results.put("subNodeName", "AssociatedGroups,AssociatedUserGroups");
/* 7212 */     String outputString = getOutputAsString(results, isJsonFormat);
/* 7213 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 7218 */     String loginUserRole = getOwnerRole(request);
/* 7219 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/* 7220 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/*      */     
/* 7222 */     if ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!loginUserRole.equals("ENTERPRISEADMIN")))) {
/* 7223 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */     }
/* 7225 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/* 7227 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/*      */     
/* 7230 */     String outputString = "";
/* 7231 */     String currentUserName = getOwnerName(request);
/* 7232 */     ArrayList<String> userIdList = new ArrayList();
/* 7233 */     if ((request.getParameter("userId") != null) && (!request.getParameter("userId").equals(""))) {
/* 7234 */       String userId = request.getParameter("userId");
/* 7235 */       if ((isAPICallFromAAM) && (userId.equals("all"))) {
/* 7236 */         userIdList = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/*      */       } else {
/* 7238 */         String[] userIdArr = request.getParameter("userId").split(",");
/* 7239 */         userIdList = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERID in (" + request.getParameter("userId") + ") and USERNAME NOT IN ('admin','reportadmin','systemadmin_enterprise','" + currentUserName + "')");
/* 7240 */         AMLog.debug("CommonAPIUtil.deleteUser():Users to delete:" + request.getParameter("userId") + "  users from database:" + userIdList + "    isAPICallFromAAM::::" + isAPICallFromAAM);
/* 7241 */         if (userIdArr.length != userIdList.size()) {
/* 7242 */           AMLog.debug("REST API: The userid in the request url is wrong or the values are repeated.");
/* 7243 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguserid.message"), "4264");
/* 7244 */           return outputString;
/*      */         }
/*      */       }
/* 7247 */     } else if ((request.getParameter("userName") != null) && (!request.getParameter("userName").equals(""))) {
/* 7248 */       userIdList = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME ='" + request.getParameter("userName") + "' and USERNAME NOT IN ('admin','reportadmin','systemadmin_enterprise','" + currentUserName + "')");
/* 7249 */       if (userIdList.size() == 0) {
/* 7250 */         AMLog.debug("REST API: The user name in the request url is wrong ");
/* 7251 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.create.wrongusernamemessage"), "4607");
/* 7252 */         return outputString;
/*      */       }
/*      */     } else {
/* 7255 */       AMLog.debug("REST API : user id/user name parameter missing in the request.");
/* 7256 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { "user id/user name" }), "4600");
/* 7257 */       return outputString;
/*      */     }
/* 7259 */     UserConfigurationUtil.deleteUserDetails(userIdList, currentUserName);
/* 7260 */     AMLog.debug("REST API : user details sucessfully deleted.");
/* 7261 */     outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.delete.success.text"), "4000");
/* 7262 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 7267 */     if ((EnterpriseUtil.isManagedServer()) && (Constants.isSsoEnabled()) && (getOwnerRole(request).equals("ADMIN")))
/*      */     {
/* 7269 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.sso.restriction.in.mas.text"), "4008");
/*      */     }
/* 7271 */     String uri = request.getRequestURI();
/* 7272 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 7273 */     if (isJsonFormat) {
/* 7274 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 7276 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/* 7279 */     HashMap<String, String> hmap = DBUtil.username_userid_mapping;
/* 7280 */     String loginUserId = (String)hmap.get(getOwnerName(request));
/* 7281 */     String loginUserRole = getOwnerRole(request);
/* 7282 */     String outputString = "";
/* 7283 */     String userId = request.getParameter("userId");
/* 7284 */     String userName = request.getParameter("userName");
/* 7285 */     String password = request.getParameter("password");
/* 7286 */     String oldPassword = request.getParameter("oldPassword");
/* 7287 */     String role = request.getParameter("role");
/* 7288 */     String updateChk = "false";
/* 7289 */     String description = request.getParameter("description");
/* 7290 */     String email = request.getParameter("email");
/* 7291 */     String loginUser = getOwnerName(request);
/* 7292 */     String restrictedAdmin = request.getParameter("delegatedadmin");
/* 7293 */     String apiCallFromAAM = request.getParameter("apicallfrom");
/* 7294 */     boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 7295 */     Vector errList = new Vector();
/*      */     
/* 7297 */     if (PluginUtil.isPlugin()) {
/* 7298 */       if ((userName != null) && (!userName.equals(""))) {
/* 7299 */         userId = (String)hmap.get(request.getParameter("userName"));
/*      */       } else {
/* 7301 */         errList.add("userName");
/*      */       }
/*      */     }
/*      */     
/* 7305 */     if ((userId != null) && (userId.equals(""))) {
/* 7306 */       errList.add("userId");
/*      */     }
/*      */     
/* 7309 */     if ((userName != null) && (userName.equals(""))) {
/* 7310 */       errList.add("userName");
/*      */     }
/* 7312 */     if (((password == null) || (!password.equals(""))) || (
/*      */     
/*      */ 
/* 7315 */       ((role == null) || (!role.equals(""))) || 
/*      */       
/*      */ 
/*      */ 
/* 7319 */       (!errList.isEmpty()))) {
/* 7320 */       AMLog.debug("REST API : user parameter is empty in the request.");
/* 7321 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { getListAsString(errList) }), "4600");
/* 7322 */       return outputString;
/*      */     }
/*      */     
/* 7325 */     if ((userName != null) && (!isAPICallFromAAM)) {
/* 7326 */       userId = DBUtil.getUserID(userName);
/* 7327 */       if ("-1".equals(userId)) {
/* 7328 */         AMLog.debug("REST API: The user name in the request url is wrong ");
/* 7329 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.create.wrongusernamemessage"), "4607");
/* 7330 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 7334 */     if (userId != null) {
/* 7335 */       if ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!loginUserRole.equals("ENTERPRISEADMIN")))) {
/* 7336 */         AMLog.debug("REST API :No privilege to update this user info.");
/* 7337 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.noprivilege.msg"), "4608");
/* 7338 */         return outputString;
/*      */       }
/*      */     } else {
/* 7341 */       userId = loginUserId;
/*      */     }
/*      */     
/*      */ 
/* 7345 */     ArrayList<ArrayList<String>> lists = mo.getRows("select AM_UserGroupTable.USERNAME,AM_UserGroupTable.GROUPNAME, AM_UserPasswordTable.USERID,AM_UserPasswordTable.EMAILID,AM_UserPasswordTable.DESCRIPTION,AM_UserPasswordTable.APIKEY,AM_UserPasswordTable.RESTRICTEDADMIN from AM_UserGroupTable,AM_UserPasswordTable where AM_UserGroupTable.USERNAME=AM_UserPasswordTable.USERNAME and AM_UserPasswordTable.USERID=" + userId + " and AM_UserGroupTable.USERNAME NOT IN ('reportadmin','systemadmin_enterprise') ORDER BY GROUPNAME");
/* 7346 */     if (lists.size() == 0) {
/* 7347 */       AMLog.debug("REST API :Invalid User Id.");
/* 7348 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wronguserid.message"), "4264");
/* 7349 */       return outputString;
/*      */     }
/*      */     
/* 7352 */     if ((userId.equals("1")) && (userName != null) && (!userName.equals("admin"))) {
/* 7353 */       AMLog.debug("REST API :Cannot change deafault admin user name.");
/* 7354 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.cannotchangeadminusername.msg"), "4609");
/* 7355 */       return outputString;
/*      */     }
/* 7357 */     if ((userId.equals("1")) && (role != null) && (!role.equalsIgnoreCase("ADMIN"))) {
/* 7358 */       AMLog.debug("REST API :Cannot change deafault admin user role.");
/* 7359 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.cannotchangeadminuserrole.msg"), "4610");
/* 7360 */       return outputString;
/*      */     }
/*      */     
/* 7363 */     if ((userName != null) && (userName.length() > 50)) {
/* 7364 */       AMLog.debug("REST API : user name length exceeded.");
/* 7365 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("user.creation.namelengthexceed.text"), "4601");
/* 7366 */       return outputString;
/*      */     }
/*      */     
/* 7369 */     if ((userName != null) && (UserConfigurationUtil.isUserExist(userName, userId).booleanValue())) {
/* 7370 */       AMLog.debug("REST API : user exist");
/* 7371 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("user.creation.nameexists", new String[] { userName }), "4604");
/* 7372 */       return outputString;
/*      */     }
/*      */     
/* 7375 */     if (role != null) {
/* 7376 */       role = role.toUpperCase();
/*      */     }
/* 7378 */     if ((role != null) && (!"".equals(role)) && (!UserConfigurationUtil.USER_GROUPS.contains(role))) {
/* 7379 */       AMLog.debug("REST API :Inavalid user group.");
/* 7380 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.invaliduserrole.text"), "4602");
/* 7381 */       return outputString;
/*      */     }
/* 7383 */     String oldRole = (String)((ArrayList)lists.get(0)).get(1);
/* 7384 */     String oldUserName = (String)((ArrayList)lists.get(0)).get(0);
/* 7385 */     if ((restrictedAdmin == null) || (restrictedAdmin.trim().length() == 0) || (restrictedAdmin.equalsIgnoreCase("null"))) {
/* 7386 */       restrictedAdmin = (String)((ArrayList)lists.get(0)).get(6);
/* 7387 */     } else if ("true".equalsIgnoreCase(restrictedAdmin)) {
/* 7388 */       restrictedAdmin = "0";
/* 7389 */     } else if ("false".equalsIgnoreCase(restrictedAdmin)) {
/* 7390 */       restrictedAdmin = "1";
/*      */     }
/*      */     
/* 7393 */     if ((password != null) && (!"".equals(password))) {
/* 7394 */       if ((userId.equals(loginUserId)) && ((oldPassword == null) || (oldPassword.equals(""))) && (!PluginUtil.isPlugin())) {
/* 7395 */         AMLog.debug("REST API : old password  parameter missing in the request.");
/* 7396 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.parameter.emptymessage", new String[] { "oldPassword" }), "4600");
/* 7397 */         return outputString;
/*      */       }
/* 7399 */       updateChk = "true";
/*      */     }
/*      */     
/* 7402 */     if ((userName == null) || ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")))) {
/* 7403 */       userName = (String)((ArrayList)lists.get(0)).get(0);
/*      */     }
/* 7405 */     if (email == null) {
/* 7406 */       email = ((ArrayList)lists.get(0)).get(3) != null ? (String)((ArrayList)lists.get(0)).get(3) : " ";
/*      */     }
/* 7408 */     if ((role == null) || ("".equals(role)) || ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")))) {
/* 7409 */       role = lists.size() > 1 ? "ADMIN" : (String)((ArrayList)lists.get(0)).get(1);
/*      */     }
/*      */     
/* 7412 */     if ((description == null) || ((!isAPICallFromAAM) && (!loginUserRole.equals("ADMIN")))) {
/* 7413 */       description = ((ArrayList)lists.get(0)).get(4) != null ? (String)((ArrayList)lists.get(0)).get(4) : " ";
/*      */     }
/*      */     
/* 7416 */     ArrayList<String> selectedMonitor = new ArrayList();
/* 7417 */     ArrayList<String> removeMG = new ArrayList();
/* 7418 */     ArrayList<String> selectedusergroup = new ArrayList();
/* 7419 */     ArrayList<String> removeusergroup = new ArrayList();
/* 7420 */     ArrayList<String> domainList = new ArrayList();
/* 7421 */     String groupIdString = "";
/* 7422 */     boolean isUserGroup = false;
/* 7423 */     if (((loginUserRole.equals("ADMIN")) || ((loginUserRole.equals("ENTERPRISEADMIN")) && (EnterpriseUtil.isAdminServer())) || (isAPICallFromAAM)) && ((role.equals("OPERATOR")) || (role.equals("MANAGER")) || (role.equals("ADMIN")) || (role.equals("ENTERPRISEADMIN")))) {
/* 7424 */       if ((request.getParameter("associateGroupId") != null) && (!request.getParameter("associateGroupId").equals(""))) {
/* 7425 */         String[] groupId = request.getParameter("associateGroupId").split(",");
/* 7426 */         selectedMonitor = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("associateGroupId") + ")");
/* 7427 */         if (groupId.length != selectedMonitor.size()) {
/* 7428 */           AMLog.debug("REST API: The associateGroupId in the request url is wrong or the values are repeated.");
/* 7429 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupid.message"), "4605");
/* 7430 */           return outputString;
/*      */         }
/* 7432 */       } else if ((request.getParameter("associateGroupName") != null) && (!request.getParameter("associateGroupName").equals(""))) {
/* 7433 */         selectedMonitor = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("associateGroupName") + "'");
/* 7434 */         if (selectedMonitor.size() == 0) {
/* 7435 */           AMLog.debug("REST API: The associateGroupName in the request url is wrong ");
/* 7436 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociategroupname.message"), "4606");
/* 7437 */           return outputString;
/*      */         }
/* 7439 */       } else if ((request.getParameter("associateUsergroupId") != null) && (!request.getParameter("associateUsergroupId").equals(""))) {
/* 7440 */         String[] usergroupid = request.getParameter("associateUsergroupId").split(",");
/* 7441 */         selectedusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPID in (" + request.getParameter("associateUsergroupId") + ")");
/* 7442 */         if (usergroupid.length != selectedusergroup.size()) {
/* 7443 */           AMLog.debug("REST API: The usergroupid in the request url is wrong or the values are repeated.");
/* 7444 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociateusergroupid.message"), "4605");
/* 7445 */           return outputString;
/*      */         }
/* 7447 */         isUserGroup = true;
/* 7448 */       } else if ((request.getParameter("associateUsergroupName") != null) && (!request.getParameter("associateUsergroupName").equals(""))) {
/* 7449 */         selectedusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME = '" + request.getParameter("associateUsergroupName") + "'");
/* 7450 */         if (selectedusergroup.size() == 0) {
/* 7451 */           AMLog.debug("REST API: The usergroupName in the request url is wrong ");
/* 7452 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociateusergroupname.message"), "4606");
/* 7453 */           return outputString;
/*      */         }
/* 7455 */         isUserGroup = true;
/*      */       }
/*      */       
/* 7458 */       if ((request.getParameter("removeGroupId") != null) && (!request.getParameter("removeGroupId").equals(""))) {
/* 7459 */         if (isUserGroup) {
/* 7460 */           AMLog.debug("REST API: The request should contain either Monitor Group parameters or UserGroup parameters ");
/* 7461 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.user.update.wrongparameters.message"), "4606");
/* 7462 */           return outputString;
/*      */         }
/* 7464 */         String[] removeGroupId = request.getParameter("removeGroupId").split(",");
/* 7465 */         removeMG = mo.getRowsForSingleColumn("select HAID from AM_HOLISTICAPPLICATION where HAID in (" + request.getParameter("removeGroupId") + ")");
/* 7466 */         if (removeGroupId.length != removeMG.size()) {
/* 7467 */           AMLog.debug("REST API: The request should contain either Monitor Group parameters or UserGroup parameters ");
/* 7468 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.user.update.wrongparameters.message"), "4611");
/* 7469 */           return outputString;
/*      */         }
/* 7471 */         groupIdString = request.getParameter("removeGroupId");
/* 7472 */       } else if ((request.getParameter("removeGroupName") != null) && (!request.getParameter("removeGroupName").equals(""))) {
/* 7473 */         if (isUserGroup) {
/* 7474 */           AMLog.debug("REST API: The usergroupName in the request url is wrong ");
/* 7475 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongassociateusergroupname.message"), "4606");
/* 7476 */           return outputString;
/*      */         }
/* 7478 */         removeMG = mo.getRowsForSingleColumn("select RESOURCEID from AM_ManagedObject WHERE TYPE='HAI' and RESOURCENAME='" + request.getParameter("removeGroupName") + "'");
/* 7479 */         if (removeMG.size() == 0) {
/* 7480 */           AMLog.debug("REST API: The removeGroupName in the request url is wrong ");
/* 7481 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongremovegroupName.message"), "4612");
/* 7482 */           return outputString;
/*      */         }
/* 7484 */         groupIdString = (String)removeMG.get(0);
/* 7485 */       } else if ((request.getParameter("removeUsergroupId") != null) && (!request.getParameter("removeUsergroupId").equals(""))) {
/* 7486 */         String[] removeUsergroupId = request.getParameter("removeUsergroupId").split(",");
/* 7487 */         removeusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPID in (" + request.getParameter("removeUsergroupId") + ")");
/* 7488 */         if (removeUsergroupId.length != removeusergroup.size()) {
/* 7489 */           AMLog.debug("REST API: The removeUserGroupId in the request url is wrong or the values are repeated.");
/* 7490 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongremoveusergroupid.message"), "4611");
/* 7491 */           return outputString;
/*      */         }
/* 7493 */         isUserGroup = true;
/* 7494 */       } else if ((request.getParameter("removeUsergroupName") != null) && (!request.getParameter("removeUsergroupName").equals(""))) {
/* 7495 */         removeusergroup = mo.getRowsForSingleColumn("select GROUPID from AM_USERGROUP_CONFIG where GROUPNAME='" + request.getParameter("removeUsergroupName") + "'");
/* 7496 */         if (removeusergroup.size() == 0) {
/* 7497 */           AMLog.debug("REST API: The removeUsergroupName in the request url is wrong ");
/* 7498 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wrongremoveusergroupName.message"), "4612");
/* 7499 */           return outputString;
/*      */         }
/* 7501 */         isUserGroup = true;
/*      */       }
/*      */       
/* 7504 */       if ((request.getParameter("domainuser") != null) && ("true".equals(request.getParameter("domainuser")))) {
/* 7505 */         domainList = mo.getRowsForSingleColumn("select ID from AM_DOMAINCONTROLLERS where ID in (" + request.getParameter("userdomainids") + ")");
/* 7506 */         if (domainList.size() == 0) {
/* 7507 */           AMLog.debug("REST API: The Domain id in the request url is wrong ");
/* 7508 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("The Domain id in the request URL is wrong"), "4612");
/* 7509 */           return outputString;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 7515 */     if (domainList.size() > 0) {
/*      */       try {
/* 7517 */         AMConnectionPool.executeUpdateStmt("delete from AM_DOMAINUSER_MAPPER where USERID=" + userId);
/* 7518 */         for (String id : domainList) {
/*      */           try {
/* 7520 */             AMConnectionPool.executeUpdateStmt("insert into AM_DOMAINUSER_MAPPER (USERID,DOMAINID) values (" + userId + "," + id + ") ");
/*      */           } catch (Exception ex) {
/* 7522 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/* 7526 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 7530 */     if (isUserGroup) {
/* 7531 */       if (selectedusergroup.size() > 0) {
/* 7532 */         ArrayList<String> mgs = UserConfigurationUtil.updateOwnerUserGroups((String[])selectedusergroup.toArray(new String[selectedusergroup.size()]), Integer.valueOf(userId).intValue(), false);
/* 7533 */         for (String id : mgs) {
/* 7534 */           if (selectedMonitor.contains(id)) {
/* 7535 */             selectedMonitor.add(id);
/*      */           }
/*      */         }
/*      */       }
/* 7539 */       if (removeusergroup.size() > 0) {
/* 7540 */         AMConnectionPool.executeUpdateStmt("delete from AM_USERGROUP_OWNERS where USERID=" + userId + " and " + ManagedApplication.getCondition("GROUPID", removeusergroup));
/* 7541 */         removeMG = mo.getRowsForSingleColumn("select HAID from AM_USERGROUP_MAPPING where " + ManagedApplication.getCondition("GROUPID", removeusergroup));
/* 7542 */         StringBuilder resids = new StringBuilder();
/* 7543 */         for (String id : removeMG) {
/* 7544 */           resids.append(id).append(",");
/*      */         }
/* 7546 */         if (resids.toString().trim().length() > 1) {
/* 7547 */           groupIdString = resids.substring(0, resids.length() - 1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 7552 */     Properties userProps = new Properties();
/* 7553 */     userProps.setProperty("userId", userId);
/* 7554 */     userProps.setProperty("updateChk", updateChk);
/* 7555 */     userProps.setProperty("userName", userName);
/* 7556 */     userProps.setProperty("loginUser", loginUser);
/* 7557 */     userProps.setProperty("oldUserName", oldUserName);
/* 7558 */     if ((isAPICallFromAAM) && (password != null)) {
/* 7559 */       password = Coding.convertFromBase(password);
/* 7560 */       if (oldPassword != null) {
/* 7561 */         oldPassword = Coding.convertFromBase(oldPassword);
/*      */       }
/*      */     }
/* 7564 */     if ((password != null) && (!"".equals(password))) {
/* 7565 */       userProps.setProperty("password", password);
/*      */     }
/* 7567 */     if (oldPassword != null) {
/* 7568 */       userProps.setProperty("oldPassword", oldPassword);
/*      */     }
/* 7570 */     userProps.setProperty("description", description);
/* 7571 */     userProps.setProperty("email", email);
/* 7572 */     userProps.setProperty("restrictedAdmin", restrictedAdmin);
/* 7573 */     if ((role != null) && (role.trim().length() > 0)) {
/* 7574 */       userProps.setProperty("role", role);
/*      */     }
/* 7576 */     if (domainList.size() > 0) {
/* 7577 */       userProps.setProperty("domainuser", "true");
/* 7578 */       userProps.setProperty("userdomainids", request.getParameter("userdomainids"));
/*      */     }
/* 7580 */     String errorType = UserConfigurationUtil.updateUserDetails(userProps);
/* 7581 */     AMLog.debug("api_error  " + errorType);
/*      */     
/* 7583 */     if (!errorType.equals("noerror")) {
/* 7584 */       if (errorType.equals("oldpwdnotmatch")) {
/* 7585 */         AMLog.debug("REST API : Old Password Specified does not match.");
/* 7586 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.changepassword.oldpasswordfailure.text"), "4613");
/* 7587 */         return outputString; }
/* 7588 */       if (errorType.equals("pwdinconchar")) {
/* 7589 */         AMLog.debug("REST API : Password should not have more than three consecutive characters from the previous password");
/* 7590 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.userconfig.passwordrules.threeconsecutive.text"), "4614");
/* 7591 */         return outputString;
/*      */       }
/* 7593 */       AMLog.debug("REST API : Server Error while processing the request");
/* 7594 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/* 7595 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/* 7599 */     UserConfigurationUtil.updateGroups(userName, new String[] { role });
/* 7600 */     if ((loginUserRole.equals("ADMIN")) || (loginUserRole.equals("ENTERPRISEADMIN"))) {
/* 7601 */       if (selectedMonitor.size() > 0) {
/* 7602 */         ArrayList<String> alreadyPresent = new ArrayList();
/* 7603 */         String groupQuery = "select HAID from AM_HOLISTICAPPLICATION_OWNERS where OWNERID=" + userId;
/* 7604 */         if (removeMG.size() > 0) {
/* 7605 */           groupQuery = groupQuery + " AND HAID NOT IN (" + groupIdString + ")";
/*      */         }
/* 7607 */         alreadyPresent = mo.getRowsForSingleColumn(groupQuery);
/* 7608 */         selectedMonitor.addAll(alreadyPresent);
/* 7609 */         UserConfigurationUtil.updateMonitorGroupDetails(userName, userId, role, selectedMonitor);
/*      */       }
/*      */       
/* 7612 */       if ((!role.equals("OPERATOR")) && (!role.equals("MANAGER")) && ((oldRole.equals("OPERATOR")) || (oldRole.equals("MANAGER")))) {
/* 7613 */         UserConfigurationUtil.updateMonitorGroupDetails(userName, userId, role, null);
/* 7614 */       } else if ((selectedMonitor.size() == 0) && (removeMG.size() > 0)) {
/* 7615 */         String deleteQuery = "delete from AM_HOLISTICAPPLICATION_OWNERS where OWNERID=" + userId + "AND HAID IN (" + groupIdString + ")";
/*      */         try {
/* 7617 */           AMConnectionPool.executeUpdateStmt(deleteQuery);
/*      */         } catch (Exception ex) {
/* 7619 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 7624 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 7625 */     usersList = UserConfigurationUtil.getUserListDetails(Integer.parseInt(userId));
/* 7626 */     UserConfigurationUtil.updateUserPrivileges(request, userName);
/*      */     
/*      */ 
/* 7629 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate)) {
/*      */       try
/*      */       {
/* 7632 */         RestrictedUsersViewUtil.userVsIsRoleRestrictedMap.remove(userId);
/*      */         
/* 7634 */         if (RestrictedUsersViewUtil.isRestrictedRole(userId)) {
/* 7635 */           AMLog.debug("[CommonAPIUtil::(udpateUser)]ruser(s) : " + userId);
/* 7636 */           RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.add(userId);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 7641 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 7646 */     if ("true".equals(request.getParameter("profilecreationscript"))) {
/* 7647 */       if (usersList.size() == 0) {
/* 7648 */         AMLog.debug("REST API: Problem in updating the user");
/* 7649 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.update.failure.text"), "4500");
/* 7650 */         return outputString;
/*      */       }
/* 7652 */       AMLog.debug("REST API: User Updated successfully");
/* 7653 */       outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.update.success.text"), "4000");
/* 7654 */       return outputString;
/*      */     }
/*      */     
/*      */ 
/* 7658 */     if (usersList.size() == 0) {
/* 7659 */       Hashtable msg = new Hashtable();
/* 7660 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nouserdetails.message"));
/* 7661 */       usersList.add(msg);
/*      */     } else {
/* 7663 */       Hashtable msg = new Hashtable();
/* 7664 */       msg.put("Message", FormatUtil.getString("am.api.users.update.successmessage"));
/* 7665 */       usersList.add(msg);
/*      */     }
/*      */     
/*      */ 
/* 7669 */     Hashtable userDetails = getUserDetails(userName);
/* 7670 */     AMLog.info("CommonAPIUtil: RESTAPI: userdetails of user:" + userDetails);
/* 7671 */     HashMap results = new HashMap();
/* 7672 */     results.put("response-code", "4000");
/* 7673 */     results.put("uri", uri);
/* 7674 */     results.put("result", usersList);
/* 7675 */     results.put("sortingParam", "userName");
/* 7676 */     results.put("parentNode", "Users");
/* 7677 */     results.put("nodeName", "User");
/* 7678 */     results.put("subNodeName", "AssociatedGroups,AssociatedUserGroups");
/* 7679 */     outputString = getOutputAsString(results, isJsonFormat);
/* 7680 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 7685 */     HashMap<String, String> hmap = DBUtil.username_userid_mapping;
/* 7686 */     int loginUserId = Integer.parseInt((String)hmap.get(getOwnerName(request)));
/* 7687 */     String uri = request.getRequestURI();
/* 7688 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 7689 */     if (isJsonFormat) {
/* 7690 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 7692 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/* 7694 */     String outputString = "";
/* 7695 */     int userId = -1;
/* 7696 */     String[] nodes = uri.split("/");
/* 7697 */     String user = null;
/* 7698 */     AMLog.debug("api_nodes:" + nodes);
/* 7699 */     if (nodes.length > 4) {
/* 7700 */       user = nodes[4];
/*      */     }
/*      */     
/* 7703 */     if (user != null) {
/* 7704 */       Pattern p = Pattern.compile("[0-9]*");
/* 7705 */       Matcher m = p.matcher(user);
/* 7706 */       boolean isId = m.matches();
/* 7707 */       if (isId) {
/* 7708 */         userId = Integer.parseInt(user);
/*      */       } else {
/* 7710 */         ArrayList<String> userIdList = mo.getRowsForSingleColumn("select USERID from AM_UserPasswordTable where USERNAME ='" + user + "' and USERNAME NOT IN ('reportadmin','systemadmin_enterprise')");
/* 7711 */         if (userIdList.size() == 0) {
/* 7712 */           AMLog.debug("REST API: The user parameter in the request url is wrong ");
/* 7713 */           outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.wronguserparameter.message"), "4615");
/* 7714 */           return outputString;
/*      */         }
/*      */         
/* 7717 */         userId = Integer.parseInt((String)userIdList.get(0));
/* 7718 */         AMLog.debug("api is id false  n userid:" + userId);
/*      */       }
/* 7720 */       if ((!getOwnerRole(request).equals("ADMIN")) && (!getOwnerRole(request).equals("ENTERPRISEADMIN")) && (loginUserId != userId)) {
/* 7721 */         AMLog.debug("REST API: The no privilege to view this data ");
/* 7722 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.api.users.noprivilegetoviewuser.message"), "4616");
/* 7723 */         return outputString;
/*      */       }
/*      */     }
/* 7726 */     else if ((!getOwnerRole(request).equals("ADMIN")) && (!getOwnerRole(request).equals("ENTERPRISEADMIN"))) {
/* 7727 */       userId = loginUserId;
/* 7728 */       if (isDelegatedAdminAPIRequest(request)) {
/* 7729 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/* 7730 */         return outputString;
/*      */       }
/*      */     }
/*      */     
/* 7734 */     ArrayList<Hashtable<String, String>> usersList = new ArrayList();
/* 7735 */     usersList = UserConfigurationUtil.getUserListDetails(userId);
/* 7736 */     if (usersList.size() == 0) {
/* 7737 */       Hashtable msg = new Hashtable();
/* 7738 */       msg.put("Message", FormatUtil.getString("am.webclient.apikey.nouserdetails.message"));
/* 7739 */       usersList.add(msg);
/*      */     }
/* 7741 */     HashMap results = new HashMap();
/* 7742 */     results.put("response-code", "4000");
/* 7743 */     results.put("uri", uri);
/* 7744 */     results.put("result", usersList);
/* 7745 */     results.put("sortingParam", "userName");
/* 7746 */     results.put("parentNode", "Users");
/* 7747 */     results.put("nodeName", "User");
/* 7748 */     results.put("subNodeName", "AssociatedGroups,AssociatedUserGroups");
/* 7749 */     outputString = getOutputAsString(results, isJsonFormat);
/* 7750 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String getListAsString(List<String> list) {
/* 7754 */     StringBuilder sb = new StringBuilder();
/* 7755 */     for (String str : list) {
/* 7756 */       sb.append(str).append(",");
/*      */     }
/* 7758 */     return sb.deleteCharAt(sb.length() - 1).toString();
/*      */   }
/*      */   
/*      */   public static String enableDisableAlarmsAction(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 7764 */     MockHttpServletRequest MSreq = new MockHttpServletRequest();
/* 7765 */     MSreq.setContentType("text/xml; charset=UTF-8");
/* 7766 */     MSreq.addParameter("method", "alterActionsforMG");
/* 7767 */     Vector<String> resIdsOwned = new Vector();
/*      */     
/* 7769 */     if ((request.getParameter("alteraction") != null) && ((request.getParameter("alteraction").equals("enable")) || (request.getParameter("alteraction").equals("disable"))))
/*      */     {
/* 7771 */       if (request.getParameter("alteraction").equals("enable"))
/*      */       {
/* 7773 */         MSreq.addParameter("operation", "Enable Actions");
/* 7774 */         MSreq.addParameter("alteraction", "enable");
/*      */       }
/*      */       else
/*      */       {
/* 7778 */         MSreq.addParameter("operation", "Disable Actions");
/* 7779 */         MSreq.addParameter("alteraction", "disable");
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 7784 */       AMLog.debug("REST API : Server error");
/* 7785 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.enabledisablealarms.failed.message"), "4551");
/*      */     }
/* 7787 */     MSreq.addParameter("operation", "Enable Actions");
/*      */     
/*      */ 
/* 7790 */     ManagedApplication mo = new ManagedApplication();
/*      */     
/* 7792 */     String resid = request.getParameter("resourceid") != null ? request.getParameter("resourceid") : null;
/* 7793 */     String mgID = request.getParameter("haid") != null ? request.getParameter("haid") : null;
/*      */     
/* 7795 */     Vector haid = new Vector();
/* 7796 */     Vector tempAllResid = new Vector();
/* 7797 */     Vector allResids = new Vector();
/* 7798 */     Vector temphaid = new Vector();
/* 7799 */     Vector mgchildIds = new Vector();
/* 7800 */     if ((resid == null) && (mgID != null))
/*      */     {
/* 7802 */       resid = mgID;
/*      */     }
/* 7804 */     else if ((resid != null) && (mgID == null))
/*      */     {
/* 7806 */       resid = resid;
/*      */     }
/* 7808 */     else if ((resid != null) && (mgID != null))
/*      */     {
/* 7810 */       resid = resid + "," + mgID;
/*      */     }
/*      */     else
/*      */     {
/* 7814 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/*      */     }
/* 7816 */     String[] resourceid = resid.split(",");
/* 7817 */     String tempResid = null;
/*      */     try
/*      */     {
/* 7820 */       if (isValidResid(request, response, resid))
/*      */       {
/* 7822 */         String[] mgid = resid.split(",");
/* 7823 */         for (int i = 0; i < mgid.length; i++)
/*      */         {
/* 7825 */           ParentChildRelationalUtil.getAllChildMapper(mgchildIds, mgID, true);
/* 7826 */           Enumeration e = mgchildIds.elements();
/* 7827 */           while (e.hasMoreElements()) {
/* 7828 */             tempAllResid.add(e.nextElement());
/*      */           }
/*      */         }
/* 7831 */         for (int i = 0; i < resourceid.length; i++)
/*      */         {
/* 7833 */           if (isFirstLevelMG(resourceid[i]))
/*      */           {
/* 7835 */             haid.add(resourceid[i]);
/*      */           }
/* 7837 */           tempAllResid.add(resourceid[i]);
/*      */         }
/*      */         
/* 7840 */         for (int i = 0; i < haid.size(); i++)
/*      */         {
/* 7842 */           mo.getAllChildsinTree(tempAllResid, "" + haid.get(i));
/* 7843 */           tempAllResid.add(haid.get(i));
/*      */         }
/*      */         
/*      */ 
/* 7847 */         for (int i = 0; i < tempAllResid.size(); i++)
/*      */         {
/* 7849 */           if (!allResids.contains(tempAllResid.get(i)))
/*      */           {
/* 7851 */             allResids.add(tempAllResid.get(i));
/*      */           }
/*      */         }
/*      */         
/* 7855 */         for (int i = allResids.size() - 1; i > 0; i--)
/*      */         {
/* 7857 */           if (allResids.get(i) == null)
/*      */           {
/* 7859 */             allResids.remove(i);
/* 7860 */             i--;
/*      */           }
/*      */         }
/* 7863 */         resid = "" + allResids.get(0);
/* 7864 */         for (int i = 1; i < allResids.size(); i++)
/*      */         {
/* 7866 */           resid = resid + "," + allResids.get(i);
/*      */         }
/*      */         
/* 7869 */         boolean isAdminRole = isAdminRole(request);
/* 7870 */         boolean isDelegatedAdmin = isDelegatedAdmin(request);
/* 7871 */         if (isDelegatedAdmin) {
/* 7872 */           resIdsOwned = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(getUserIdForAPIKey(request.getParameter("apikey")));
/*      */         }
/* 7874 */         if ((!isAdminRole) && ((!isDelegatedAdmin) || (!resIdsOwned.containsAll(Arrays.asList(resid.split(",")))))) {
/* 7875 */           AMLog.debug("REST API : Do not have authorized access to one or more resources involved in this operation.");
/* 7876 */           return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */         }
/*      */         
/* 7879 */         MSreq.addParameter("groupviewfilterby", "-");
/* 7880 */         MSreq.addParameter("retaintree", "null");
/* 7881 */         MSreq.addParameter("selectid", resid);
/* 7882 */         MSreq.addParameter("isAPI", "true");
/* 7883 */         DataCollectionController dcc = new DataCollectionController();
/* 7884 */         dcc.alterActionsforMG(null, null, MSreq, response);
/*      */       }
/*      */       else
/*      */       {
/* 7888 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongmanage.message"), "4006");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 7894 */       e.printStackTrace();
/* 7895 */       AMLog.debug("REST API : Server error");
/* 7896 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/* 7898 */     return URITree.generateXML(request, response, FormatUtil.getString("Enable/Disable alarms done successfully."), "4000");
/*      */   }
/*      */   
/*      */   private static boolean isFirstLevelMG(String resid)
/*      */     throws Exception
/*      */   {
/* 7904 */     boolean isFirstMG = false;
/* 7905 */     ResultSet rs = null;
/* 7906 */     int count = 0;
/* 7907 */     String query = "select count(AM_HOLISTICAPPLICATION.HAID) as count from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_HOLISTICAPPLICATION.TYPE=0 and AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and AM_HOLISTICAPPLICATION.HAID in (" + resid + ")";
/*      */     
/*      */     try
/*      */     {
/* 7911 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 7912 */       while (rs.next())
/*      */       {
/* 7914 */         count = rs.getInt("count");
/* 7915 */         if ((count != 0) && (count > 0))
/*      */         {
/* 7917 */           isFirstMG = true;
/*      */         }
/*      */         else
/*      */         {
/* 7921 */           isFirstMG = false;
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 7928 */       ex.printStackTrace();
/* 7929 */       AMLog.debug("REST API : Server error");
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 7934 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 7936 */     return isFirstMG;
/*      */   }
/*      */   
/*      */   private static boolean isValidResid(HttpServletRequest request, HttpServletResponse response, String resid)
/*      */     throws Exception
/*      */   {
/* 7942 */     boolean isValid = false;
/* 7943 */     ResultSet rs = null;
/* 7944 */     String[] resourceid = resid.split(",");
/* 7945 */     String checkquery = "select count(RESOURCEID) from AM_ManagedObject where (TYPE in " + Constants.resourceTypes + " or TYPE='HAI') and RESOURCEID in (" + resid + ")";
/*      */     
/*      */     try
/*      */     {
/* 7949 */       int count = 0;
/* 7950 */       rs = AMConnectionPool.executeQueryStmt(checkquery);
/* 7951 */       while (rs.next())
/*      */       {
/* 7953 */         count = rs.getInt(1);
/*      */       }
/* 7955 */       if (resourceid.length == count)
/*      */       {
/* 7957 */         isValid = true;
/*      */       }
/*      */       else
/*      */       {
/* 7961 */         isValid = false;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 7967 */       ex.printStackTrace();
/* 7968 */       AMLog.debug("REST API : Server error");
/* 7969 */       isValid = false;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 7974 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 7976 */     return isValid;
/*      */   }
/*      */   
/*      */   public static ArrayList getDomainUserorGroup(ArrayList domainDetails)
/*      */   {
/* 7981 */     ArrayList userOrGroupList = new ArrayList();
/*      */     try {
/* 7983 */       Map paramMap = new HashMap();
/* 7984 */       paramMap.put("domainName", domainDetails.get(1));
/* 7985 */       paramMap.put("domainController", domainDetails.get(2));
/* 7986 */       paramMap.put("domainUsername", domainDetails.get(5));
/* 7987 */       paramMap.put("domainPassword", domainDetails.get(7));
/* 7988 */       paramMap.put("searchText", domainDetails.get(8));
/* 7989 */       String authentication = (String)domainDetails.get(6);
/* 7990 */       if ("LDAP".equalsIgnoreCase(authentication)) {
/* 7991 */         paramMap.put("server", "ldap");
/*      */       } else {
/* 7993 */         paramMap.put("server", "ad");
/*      */       }
/*      */       
/* 7996 */       paramMap.put("importdata", domainDetails.get(9));
/* 7997 */       paramMap.put("domainId", domainDetails.get(0));
/*      */       
/* 7999 */       userOrGroupList = ADAuthenticationUtil.fetchADUserList(paramMap);
/*      */     } catch (Exception ex) {
/* 8001 */       ex.printStackTrace();
/*      */     }
/* 8003 */     return userOrGroupList;
/*      */   }
/*      */   
/*      */   public static String addService(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8007 */     int resID = -1;
/* 8008 */     int serviceid = -1;
/* 8009 */     if (request.getParameter("resourceid") != null) {
/* 8010 */       if ((isOperatorRole(request)) && (!isAssociatedToOperator(request, request.getParameter("resourceid")))) {
/* 8011 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */     } else {
/* 8014 */       AMLog.debug("Service Add REST API : Server resourceid not in the request");
/* 8015 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*      */     }
/*      */     try {
/* 8018 */       resID = Integer.parseInt(request.getParameter("resourceid"));
/*      */     } catch (Exception e) {
/* 8020 */       AMLog.debug("Service Add REST API : Improper resourceid in the request : " + request.getParameter("resourceid"));
/* 8021 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.mainresiderr.msg"), "4037");
/*      */     }
/*      */     
/* 8024 */     if (request.getParameter("name") == null) {
/* 8025 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.hostresourceconfig.servicename.alert"), "4565");
/*      */     }
/*      */     try
/*      */     {
/* 8029 */       String name = request.getParameter("name");
/* 8030 */       String displayname = request.getParameter("displayname") != null ? request.getParameter("displayname") : name;
/* 8031 */       serviceid = com.adventnet.appmanager.server.template.AMProcessMOUtil.createProcessMO(resID, displayname, name, 1);
/*      */     } catch (Exception e) {
/* 8033 */       e.printStackTrace();
/*      */     }
/* 8035 */     String output = FormatUtil.getString("Unable able to add Service");
/* 8036 */     String status = "4566";
/* 8037 */     if (serviceid != -1) {
/* 8038 */       output = FormatUtil.getString("Service added successfully");
/* 8039 */       request.setAttribute("resourceid", String.valueOf(serviceid));
/* 8040 */       status = "4000";
/*      */     }
/* 8042 */     return URITree.generateXML(request, response, output, status);
/*      */   }
/*      */   
/*      */   public static String insertGlobalConfigValue(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 8048 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 8049 */       boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 8050 */       if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/* 8051 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */       }
/* 8053 */       String key = request.getParameter("key");
/* 8054 */       String value = request.getParameter("value");
/* 8055 */       if ((key == null) || (key.trim().length() == 0) || (key.equalsIgnoreCase("null"))) {
/* 8056 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.invalid.key"), "4090");
/*      */       }
/* 8058 */       if (value == null) {
/* 8059 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.invalid.value"), "4091");
/*      */       }
/* 8061 */       if (DBUtil.hasGlobalConfigValue(key)) {
/* 8062 */         return updateGlobalConfigValue(request, response);
/*      */       }
/* 8064 */       DBUtil.insertIntoGlobalConfig(key, value);
/*      */     }
/*      */     catch (Exception ex) {
/* 8067 */       ex.printStackTrace();
/* 8068 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/* 8070 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.insert.success.text"), "4092");
/*      */   }
/*      */   
/*      */   public static String updateSSOInPropFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 8075 */       String confFilePath = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + "conf" + File.separator + "AMServer.properties";
/* 8076 */       String value = request.getParameter("am.sso.enabled");
/* 8077 */       AMCacheHandler.updateOrInsertPropinConfFile(confFilePath, "am.sso.enabled", value, "=");
/* 8078 */       AMCacheHandler.updateOrInsertPropinConfFile(confFilePath, "am.user.resource.enabled", value, "=");
/* 8079 */       DBUtil.updateGlobalConfigValue("am.sso.enabled", value);
/*      */     } catch (Exception e) {
/* 8081 */       e.printStackTrace();
/* 8082 */       return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */     }
/* 8084 */     return URITree.generateXML(request, response, FormatUtil.getString("success"), "4000");
/*      */   }
/*      */   
/*      */   public static String updateConfFileConfigurationInMAS(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8088 */     try { Map<String, String[]> serverPropsMap = request.getParameterMap();
/* 8089 */       AMLog.debug("updateConfFileConfigurationInMAS :: serverPropsMap :" + serverPropsMap);
/* 8090 */       String confFilePath = "";
/* 8091 */       boolean bulksave = false;boolean updateGlobalVar = false;boolean jvmparamFlag = false;
/* 8092 */       String delimiter = "=";
/* 8093 */       HashMap<String, String> data = new HashMap();
/* 8094 */       String[] val = new String[1];
/* 8095 */       if (serverPropsMap.containsKey("bulksave")) {
/* 8096 */         val = (String[])serverPropsMap.get("bulksave");
/* 8097 */         if ("true".equalsIgnoreCase(val[0])) {
/* 8098 */           bulksave = true;
/*      */         }
/*      */       }
/* 8101 */       if (serverPropsMap.containsKey("conffiletype")) {
/* 8102 */         val = (String[])serverPropsMap.get("conffiletype");
/*      */         
/* 8104 */         if ("threads".equalsIgnoreCase(val[0])) {
/* 8105 */           updateGlobalVar = false;
/* 8106 */           delimiter = " ";
/* 8107 */           confFilePath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "threads.conf";
/* 8108 */         } else if ("dbparams".equalsIgnoreCase(val[0])) {
/* 8109 */           updateGlobalVar = false;
/* 8110 */           delimiter = " ";
/* 8111 */           confFilePath = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "database_params.conf";
/* 8112 */         } else if ("amserver".equalsIgnoreCase(val[0])) {
/* 8113 */           updateGlobalVar = true;
/* 8114 */           delimiter = "=";
/* 8115 */           confFilePath = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + "conf" + File.separator + "AMServer.properties";
/* 8116 */         } else if ("jvmparams".equalsIgnoreCase(val[0])) {
/* 8117 */           jvmparamFlag = true;
/*      */         }
/*      */       }
/*      */       
/* 8121 */       for (Map.Entry<String, String[]> entry : serverPropsMap.entrySet()) {
/* 8122 */         String propKey = (String)entry.getKey();
/* 8123 */         String[] propVal = (String[])entry.getValue();
/* 8124 */         if ((!"apikey".equalsIgnoreCase(propKey)) && (!"bulksave".equalsIgnoreCase(propKey)) && (!"conffiletype".equalsIgnoreCase(propKey))) {
/* 8125 */           if ((!bulksave) && (!jvmparamFlag)) {
/* 8126 */             AMCacheHandler.updateOrInsertPropinConfFile(confFilePath, propKey, propVal[0], delimiter);
/*      */           }
/*      */           
/* 8129 */           if ((bulksave) || (updateGlobalVar) || (jvmparamFlag)) {
/* 8130 */             data.put(propKey, propVal[0]);
/*      */           }
/* 8132 */           if (jvmparamFlag) {
/* 8133 */             AMCacheHandler.writeJVMparamsToStartupFile(data);
/*      */           }
/*      */         }
/*      */       }
/* 8137 */       if ((bulksave) && (!jvmparamFlag)) {
/* 8138 */         AMCacheHandler.updatebulkPropsinConfFile(confFilePath, data, delimiter);
/*      */       }
/* 8140 */       if (updateGlobalVar) {
/* 8141 */         AMCacheHandler.updateGlobalVariables(data);
/*      */       }
/*      */     } catch (Exception e) {
/* 8144 */       e.printStackTrace();
/* 8145 */       return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */     }
/* 8147 */     return URITree.generateXML(request, response, FormatUtil.getString("success"), "4000");
/*      */   }
/*      */   
/*      */   public static String updateServerConfigInMAS(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8151 */     try { Map<String, String[]> serverPropsMap = request.getParameterMap();
/* 8152 */       HashMap<String, String> data = new HashMap();
/* 8153 */       for (Map.Entry<String, String[]> entry : serverPropsMap.entrySet()) {
/* 8154 */         String propKey = (String)entry.getKey();
/* 8155 */         String[] propVal = (String[])entry.getValue();
/* 8156 */         if (!"apikey".equalsIgnoreCase(propKey)) {
/* 8157 */           DBUtil.updateServerConfigValue(propKey, propVal[0]);
/* 8158 */           data.put(propKey, propVal[0]);
/*      */         }
/*      */       }
/* 8161 */       AMCacheHandler.updateGlobalVariables(data);
/*      */     } catch (Exception e) {
/* 8163 */       e.printStackTrace();
/* 8164 */       return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */     }
/* 8166 */     return URITree.generateXML(request, response, FormatUtil.getString("success"), "4000");
/*      */   }
/*      */   
/*      */   public static String updatePerformancePollingDataInMAS(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8170 */     try { Map<String, String[]> perfPropsMap = request.getParameterMap();
/* 8171 */       AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/* 8172 */       String updateQuery = "";
/* 8173 */       String tabname = request.getParameter("tabname");
/* 8174 */       for (Iterator i$ = perfPropsMap.entrySet().iterator(); i$.hasNext();) { entry = (Map.Entry)i$.next();
/* 8175 */         String propKey = (String)entry.getKey();
/* 8176 */         String[] propVal = (String[])entry.getValue();
/* 8177 */         if ((!"apikey".equalsIgnoreCase(propKey)) && (!"tabname".equalsIgnoreCase(propKey)))
/*      */         {
/* 8179 */           if ("showDatabaseTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8181 */             if ("mysqlTableData".equalsIgnoreCase(propKey)) {
/* 8182 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='MYSQL-DB-server' AND COMPONENTNAME='TABLEDATA'";
/* 8183 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8185 */             else if ("sybaseDBDetails".equalsIgnoreCase(propKey)) {
/* 8186 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='SYBASE-DB-server' AND COMPONENTNAME='DATABASE DETAILS'";
/* 8187 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8189 */             else if ("failedScheduledBackupJobs".equalsIgnoreCase(propKey)) {
/* 8190 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='ORACLE-DB-server' AND COMPONENTNAME='FAILEDBACKUPJOBSONLY'";
/* 8191 */               stmt.addBatch(updateQuery);
/*      */             }
/*      */           }
/* 8194 */           else if ("showconfigureMSSQLTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8196 */             if ("mssqlScheduledJobs".equalsIgnoreCase(propKey)) {
/* 8197 */               if ("false".equalsIgnoreCase(propVal[0])) {
/* 8198 */                 String entityid = null;
/*      */                 try
/*      */                 {
/* 8201 */                   ArrayList entityList = DBUtil.getRows("SELECT distinct ENTITY FROM Event WHERE CATEGORY in ('3158','3159','3160','3161')");
/* 8202 */                   for (int i = 0; i < entityList.size(); i++)
/*      */                   {
/* 8204 */                     entityid = (String)((ArrayList)entityList.get(i)).get(0);
/* 8205 */                     FaultUtil.deleteAlertsForEntity(entityid);
/*      */                   }
/*      */                 } catch (Exception ex) {
/* 8208 */                   ex.printStackTrace();
/*      */                 }
/*      */               }
/* 8211 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='MSSQL-DB-server' AND COMPONENTNAME='SCHEDULEDJOBS'";
/* 8212 */               stmt.addBatch(updateQuery);
/*      */             }
/*      */           }
/* 8215 */           else if ("showAmazonTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8217 */             if ("amazonS3BucketTableData".equalsIgnoreCase(propKey)) {
/* 8218 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='Amazon' AND COMPONENTNAME='S3BucketTABLEDATA'";
/* 8219 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8221 */             else if ("amazonEC2PrimaryKey".equalsIgnoreCase(propKey)) {
/* 8222 */               updateQuery = "update AM_GLOBALCONFIG set VALUE ='" + propVal[0] + "' where NAME='am.amazonEC2PrimaryKey'";
/* 8223 */               stmt.addBatch(updateQuery);
/* 8224 */               com.adventnet.appmanager.struts.actions.DBaction.deletAllAmazonMonitors();
/*      */             }
/* 8226 */             else if ("mergeEnabled".equalsIgnoreCase(propKey)) {
/* 8227 */               updateQuery = "update AM_GLOBALCONFIG set VALUE ='" + propVal[0] + "' where NAME='am.amazonEC2.mergeEnabled'";
/* 8228 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8230 */             else if ("am.amazonEC2.state.terminated.dontalert".equalsIgnoreCase(propKey)) {
/* 8231 */               DBUtil.insertOrUpdateGlobalConfigValue("am.amazonEC2.state.terminated.dontalert", propVal[0]);
/*      */             }
/* 8233 */             else if ("am.amazon.signatureversion4.enabled".equalsIgnoreCase(propKey)) {
/* 8234 */               DBUtil.insertOrUpdateGlobalConfigValue("am.amazon.signatureversion4.enabled", propVal[0]);
/*      */             }
/* 8236 */           } else if ("showPingTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8238 */             if ("am.ping.packtosend".equalsIgnoreCase(propKey)) {
/* 8239 */               DBUtil.updateGlobalConfigValue(propKey, propVal[0]);
/* 8240 */               com.adventnet.appmanager.util.AppManagerUtil.pingPackToSend = Integer.parseInt(propVal[0]);
/*      */             }
/* 8242 */           } else if ("showWebservicesTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8244 */             if ("am.webservices.operationtime".equalsIgnoreCase(propKey)) {
/* 8245 */               DBUtil.updateGlobalConfigValue(propKey, propVal[0]);
/* 8246 */               com.adventnet.appmanager.util.AppManagerUtil.webServicesOperationTime = Integer.parseInt(propVal[0]);
/*      */             }
/* 8248 */           } else if ("showSnmpTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8250 */             if ("SNMPversion".equalsIgnoreCase(propKey)) {
/* 8251 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/* 8252 */               Constants.snmpVersion = propVal[0];
/*      */             }
/*      */           }
/* 8255 */           else if ("showUrlTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8257 */             if ("urlDebug".equalsIgnoreCase(propKey)) {
/* 8258 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='HTTP(S) URLs' AND COMPONENTNAME='URLDEBUG'";
/* 8259 */               stmt.addBatch(updateQuery);
/* 8260 */             } else if ("urlResponses".equalsIgnoreCase(propKey)) {
/* 8261 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='HTTP(S) URLs' AND COMPONENTNAME='URLRESPONSES'";
/* 8262 */               stmt.addBatch(updateQuery);
/*      */             }
/*      */           }
/* 8265 */           else if ("showDataCollectionTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8267 */             if ("POLLING_COUNT".equalsIgnoreCase(propKey)) {
/* 8268 */               DBUtil.updateGlobalConfigValue(propKey, propVal[0]);
/* 8269 */               DataCollectionControllerUtil.setPolling(Integer.parseInt(propVal[0]));
/*      */             }
/*      */           }
/* 8272 */           else if ("showWeblogicTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8274 */             if ("wlswebapp".equalsIgnoreCase(propKey)) {
/* 8275 */               String enableWebappresid = request.getParameter("wlswebapp_enable");
/* 8276 */               if (enableWebappresid != null) {
/* 8277 */                 StringTokenizer webappTokenizer = new StringTokenizer(enableWebappresid, ",");
/* 8278 */                 while (webappTokenizer.hasMoreTokens())
/*      */                 {
/* 8280 */                   String enableWebappid = webappTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8283 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLEWEBAPP=1 where RESOURCEID=" + enableWebappid);
/* 8284 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLEWEBAPP=1 where RESOURCEID=" + enableWebappid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8287 */                     exc.printStackTrace();
/*      */                   }
/* 8289 */                   AMDataCollector.disablewebapplist.remove(enableWebappid);
/*      */                 }
/* 8291 */                 AMLog.debug("AMDataCollector.disablewebapplist :" + AMDataCollector.disablewebapplist);
/*      */               }
/*      */               
/* 8294 */               String disableWebappresid = request.getParameter("wlswebapp_disable");
/* 8295 */               if (disableWebappresid != null) {
/* 8296 */                 StringTokenizer webappTokenizer = new StringTokenizer(disableWebappresid, ",");
/* 8297 */                 while (webappTokenizer.hasMoreTokens())
/*      */                 {
/* 8299 */                   String disableWebappid = webappTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8302 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLEWEBAPP=0 where RESOURCEID=" + disableWebappid);
/* 8303 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLEWEBAPP=0 where RESOURCEID=" + disableWebappid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8306 */                     exc.printStackTrace();
/*      */                   }
/* 8308 */                   if (!AMDataCollector.disablewebapplist.contains(disableWebappid)) {
/* 8309 */                     AMDataCollector.disablewebapplist.add(disableWebappid);
/*      */                   }
/*      */                 }
/* 8312 */                 AMLog.debug("AMDataCollector.disablewebapplist :" + AMDataCollector.disablewebapplist);
/*      */               }
/*      */             }
/* 8315 */             if ("wlsejb".equalsIgnoreCase(propKey)) {
/* 8316 */               String enableEjbresid = request.getParameter("wlsejb_enable");
/* 8317 */               if (enableEjbresid != null) {
/* 8318 */                 StringTokenizer ejbTokenizer = new StringTokenizer(enableEjbresid, ",");
/* 8319 */                 while (ejbTokenizer.hasMoreTokens())
/*      */                 {
/* 8321 */                   String enableEjbid = ejbTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8324 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLEEJB=1 where RESOURCEID=" + enableEjbid);
/* 8325 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLEEJB=1 where RESOURCEID=" + enableEjbid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8328 */                     exc.printStackTrace();
/*      */                   }
/* 8330 */                   AMDataCollector.disableEjblist.remove(enableEjbid);
/*      */                 }
/* 8332 */                 AMLog.debug("AMDataCollector.disableEjblist :" + AMDataCollector.disableEjblist);
/*      */               }
/* 8334 */               String disableEjbresid = request.getParameter("wlsejb_disable");
/* 8335 */               if (disableEjbresid != null) {
/* 8336 */                 StringTokenizer ejbTokenizer = new StringTokenizer(disableEjbresid, ",");
/* 8337 */                 while (ejbTokenizer.hasMoreTokens())
/*      */                 {
/* 8339 */                   String disableEjbid = ejbTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8342 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLEEJB=0 where RESOURCEID=" + disableEjbid);
/* 8343 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLEEJB=0 where RESOURCEID=" + disableEjbid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8346 */                     exc.printStackTrace();
/*      */                   }
/* 8348 */                   if (!AMDataCollector.disableEjblist.contains(disableEjbid)) {
/* 8349 */                     AMDataCollector.disableEjblist.add(disableEjbid);
/*      */                   }
/*      */                 }
/* 8352 */                 AMLog.debug("AMDataCollector.disableEjblist :" + AMDataCollector.disableEjblist);
/*      */               }
/*      */             }
/* 8355 */             if ("wlsservlet".equalsIgnoreCase(propKey)) {
/* 8356 */               String enableServletresid = request.getParameter("wlsservlet_enable");
/* 8357 */               if (enableServletresid != null) {
/* 8358 */                 StringTokenizer servletTokenizer = new StringTokenizer(enableServletresid, ",");
/* 8359 */                 while (servletTokenizer.hasMoreTokens())
/*      */                 {
/* 8361 */                   String enableServletid = servletTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8364 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLESERVLET=1 where RESOURCEID=" + enableServletid);
/* 8365 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLESERVLET=1 where RESOURCEID=" + enableServletid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8368 */                     exc.printStackTrace();
/*      */                   }
/* 8370 */                   AMDataCollector.disableServletlist.remove(enableServletid);
/*      */                 }
/* 8372 */                 AMLog.debug("AMDataCollector.disableServletlist :" + AMDataCollector.disableServletlist);
/*      */               }
/* 8374 */               String disableServletresid = request.getParameter("wlsservlet_disable");
/* 8375 */               if (disableServletresid != null) {
/* 8376 */                 StringTokenizer servletTokenizer = new StringTokenizer(disableServletresid, ",");
/* 8377 */                 while (servletTokenizer.hasMoreTokens())
/*      */                 {
/* 8379 */                   String disableServletid = servletTokenizer.nextToken();
/*      */                   try
/*      */                   {
/* 8382 */                     stmt.addBatch("update AM_WLS_DISABLE_STATS set ENABLESERVLET=0 where RESOURCEID=" + disableServletid);
/* 8383 */                     AMLog.debug("update AM_WLS_DISABLE_STATS set ENABLESERVLET=0 where RESOURCEID=" + disableServletid);
/*      */                   }
/*      */                   catch (Exception exc) {
/* 8386 */                     exc.printStackTrace();
/*      */                   }
/* 8388 */                   if (!AMDataCollector.disableServletlist.contains(disableServletid)) {
/* 8389 */                     AMDataCollector.disableServletlist.add(disableServletid);
/*      */                   }
/*      */                 }
/* 8392 */                 AMLog.debug("AMDataCollector.disableServletlist :" + AMDataCollector.disableServletlist);
/*      */               }
/*      */             }
/*      */           }
/* 8396 */           else if ("showDiskIoTab".equalsIgnoreCase(tabname))
/*      */           {
/* 8398 */             AdminActions adminaction = new AdminActions();
/* 8399 */             if ("diskIOAix".equalsIgnoreCase(propKey)) {
/* 8400 */               if ("false".equalsIgnoreCase(propVal[0])) {
/* 8401 */                 adminaction.disableDiskIO("AIX");
/*      */               }
/* 8403 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='AIX' AND COMPONENTNAME='DISK IO STATS'";
/* 8404 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8406 */             else if ("diskIOFreeBsd".equalsIgnoreCase(propKey)) {
/* 8407 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='FreeBSD / OpenBSD' AND COMPONENTNAME='DISK IO STATS'";
/* 8408 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8410 */             else if ("diskIOMacOS".equalsIgnoreCase(propKey)) {
/* 8411 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='Mac OS' AND COMPONENTNAME='DISK IO STATS'";
/* 8412 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8414 */             else if ("diskIOHPUX".equalsIgnoreCase(propKey)) {
/* 8415 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='HP-UX' AND COMPONENTNAME='DISK IO STATS'";
/* 8416 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8418 */             else if ("diskIOLinux".equalsIgnoreCase(propKey)) {
/* 8419 */               if ("false".equalsIgnoreCase(propVal[0])) {
/* 8420 */                 adminaction.disableDiskIO("Linux");
/*      */               }
/* 8422 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='Linux' AND COMPONENTNAME='DISK IO STATS'";
/* 8423 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8425 */             else if ("diskIOSun".equalsIgnoreCase(propKey)) {
/* 8426 */               if ("false".equalsIgnoreCase(propVal[0])) {
/* 8427 */                 adminaction.disableDiskIO("SUN");
/*      */               }
/* 8429 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='SUN' AND COMPONENTNAME='DISK IO STATS'";
/* 8430 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8432 */             else if ("diskIOWindows".equalsIgnoreCase(propKey)) {
/* 8433 */               updateQuery = "update AM_GLOBAL_DISABLECOLLECTION set STATUS ='" + propVal[0] + "' where MONITORTYPE='Windows' AND COMPONENTNAME='DISK IO STATS'";
/* 8434 */               stmt.addBatch(updateQuery);
/*      */             }
/* 8436 */             else if ("am.server.windows.disk.monitoring.type".equalsIgnoreCase(propKey))
/*      */             {
/* 8438 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8440 */             else if ("am.show.process.down.if.server.down".equalsIgnoreCase(propKey))
/*      */             {
/* 8442 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8444 */             else if ("am.show.windows.service.down.if.server.down".equalsIgnoreCase(propKey))
/*      */             {
/* 8446 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8448 */             else if ("am.server.linux.process.cpu.usage.irixmodeoff".equalsIgnoreCase(propKey))
/*      */             {
/* 8450 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8452 */             else if ("am.server.hardware.health.monitoring".equalsIgnoreCase(propKey))
/*      */             {
/* 8454 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/* 8455 */               if ("disable".equalsIgnoreCase(propVal[0])) {
/* 8456 */                 String[] hwTypes = { "Hw_Fan", "Hw_PowerSupply", "Hw_Temperature", "Hw_Cpu", "Hw_Disk", "Hw_Array", "Hw_Chassis" };
/* 8457 */                 adminaction.deleteMODetails(hwTypes);
/*      */               }
/*      */             }
/* 8460 */             else if ("am.server.enable.hardware".equalsIgnoreCase(propKey)) {
/* 8461 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8463 */             else if ("deleteDevice".equalsIgnoreCase(propKey)) {
/* 8464 */               String[] deleteDeviceArray = propVal[0].split("#");
/* 8465 */               adminaction.deleteMODetails(deleteDeviceArray);
/*      */             }
/* 8467 */             else if ("am.server.error.alert.settings".equalsIgnoreCase(propKey))
/*      */             {
/* 8469 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8471 */             else if ("am.snmp.agent.error.timedout.retry".equalsIgnoreCase(propKey))
/*      */             {
/* 8473 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8475 */             else if ("am.server.hardware.critical.status.messages".equalsIgnoreCase(propKey))
/*      */             {
/* 8477 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8479 */             else if ("am.server.hardware.warning.status.messages".equalsIgnoreCase(propKey))
/*      */             {
/* 8481 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/* 8483 */             else if ("am.server.hardware.clear.status.messages".equalsIgnoreCase(propKey))
/*      */             {
/* 8485 */               DBUtil.insertOrUpdateGlobalConfigValue(propKey, propVal[0]);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 8492 */         stmt.executeBatch();
/* 8493 */         stmt.clearBatch();
/* 8494 */         stmt.close();
/*      */       }
/*      */       catch (Exception e) {
/*      */         Map.Entry<String, String[]> entry;
/* 8498 */         stmt = null;
/* 8499 */         e.printStackTrace();
/* 8500 */         return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */       }
/*      */       finally {
/* 8503 */         if (stmt != null) {
/* 8504 */           stmt.close();
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 8508 */       e.printStackTrace();
/* 8509 */       return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */     }
/* 8511 */     return URITree.generateXML(request, response, FormatUtil.getString("success"), "4000");
/*      */   }
/*      */   
/*      */   public static String updateGlobalConfigValue(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 8516 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 8517 */       boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 8518 */       if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/* 8519 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */       }
/* 8521 */       String key = request.getParameter("key");
/* 8522 */       String value = request.getParameter("value");
/* 8523 */       if ((key == null) || (key.trim().length() == 0) || (key.equalsIgnoreCase("null"))) {
/* 8524 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.invalid.key"), "4090");
/*      */       }
/* 8526 */       if (value == null) {
/* 8527 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.invalid.value"), "4091");
/*      */       }
/* 8529 */       if (key.equals("selfMonitoring")) {
/* 8530 */         DiagnosticsAPIUtil.enableOrDisableSelfMonitoring(Boolean.valueOf(value).booleanValue());
/*      */       } else {
/* 8532 */         if (!DBUtil.hasGlobalConfigValue(key)) {
/* 8533 */           return insertGlobalConfigValue(request, response);
/*      */         }
/* 8535 */         DBUtil.updateGlobalConfigValue(key, value);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 8539 */       ex.printStackTrace();
/* 8540 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/* 8542 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.update.success.text"), "4094");
/*      */   }
/*      */   
/*      */   public static String deleteGlobalConfigValue(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 8548 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 8549 */       boolean isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 8550 */       if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/* 8551 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */       }
/* 8553 */       String key = request.getParameter("key");
/* 8554 */       if ((key == null) || (key.trim().length() == 0) || (key.equalsIgnoreCase("null"))) {
/* 8555 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.invalid.key"), "4090");
/*      */       }
/* 8557 */       if (!DBUtil.hasGlobalConfigValue(key)) {
/* 8558 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.key.notexist.text"), "4095");
/*      */       }
/* 8560 */       DBUtil.deleteGlobalConfigValue(key);
/*      */     } catch (Exception ex) {
/* 8562 */       ex.printStackTrace();
/* 8563 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     }
/* 8565 */     return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.globalconfig.delete.success.text"), "4096");
/*      */   }
/*      */   
/*      */   public static String updatedcComponentStatusInMAS(HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 8570 */     String configureBy = request.getParameter("configureBy");
/* 8571 */     String monitorType = request.getParameter("Monitortype");
/* 8572 */     String dcComponentName = request.getParameter("dcComponentsSelect");
/* 8573 */     int pollingInterval = Integer.parseInt(request.getParameter("pollingInterval"));
/*      */     try {
/* 8575 */       AMLog.debug("RESTAPI : updatedcComponentStatusInMAS :: monitorType :" + monitorType + "\t dcComponentName :" + dcComponentName + "\t pollingInterval :" + pollingInterval);
/* 8576 */       if ("Monitors".equalsIgnoreCase(configureBy)) {
/* 8577 */         String resourceIds = request.getParameter("resourceIds");
/* 8578 */         DifferentialPollingUtil.bulkUpdatePollingStatus(dcComponentName, resourceIds, pollingInterval, monitorType);
/*      */       } else {
/* 8580 */         String status = "true";
/* 8581 */         Properties dcComponentVsStatus = new Properties();
/* 8582 */         Properties dcComponentPropsVsIds = new Properties();
/* 8583 */         String baseId = Constants.getTypeId(monitorType);
/* 8584 */         Vector resIdList = DBUtil.getResIdsOfGivenResTypes(monitorType);
/* 8585 */         resIdList.add(baseId + "");
/* 8586 */         status = request.getParameter("status");
/* 8587 */         AMLog.debug("RESTAPI : updatedcComponentStatusInMAS ::  baseId :" + baseId + "\n resIdList :" + resIdList + "\t status :" + status);
/* 8588 */         dcComponentVsStatus.put(dcComponentName, status);
/* 8589 */         Properties resIdProps = new Properties();
/* 8590 */         dcComponentPropsVsIds.put(dcComponentName, resIdProps);
/* 8591 */         Iterator resIds = resIdList.iterator();
/* 8592 */         while (resIds.hasNext()) {
/* 8593 */           String resId = (String)resIds.next();
/* 8594 */           Properties dcComponentPropsVsresId = new Properties();
/* 8595 */           dcComponentPropsVsresId.put("Status", status);
/* 8596 */           dcComponentPropsVsresId.put("PollingInterval", Integer.valueOf(pollingInterval));
/* 8597 */           resIdProps.put(resId, dcComponentPropsVsresId);
/*      */         }
/* 8599 */         AMLog.debug(" RESTAPI : updatedcComponentStatusInMAS :: dcComponentVsStatus :" + dcComponentVsStatus + "\n dcComponentPropsVsIds :" + dcComponentPropsVsIds);
/* 8600 */         if ((!dcComponentVsStatus.isEmpty()) || (!dcComponentPropsVsIds.isEmpty())) {
/* 8601 */           DifferentialPollingUtil.updateDCComponentForType(monitorType, dcComponentVsStatus, dcComponentPropsVsIds);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 8606 */       e.printStackTrace();
/* 8607 */       return URITree.generateXML(request, response, FormatUtil.getString("Error"), "4444");
/*      */     }
/* 8609 */     return URITree.generateXML(request, response, FormatUtil.getString("success"), "4000");
/*      */   }
/*      */   
/*      */   private static void getRelevantResourceIds(ArrayList<String> aListOfResourceIds) {
/*      */     try {
/* 8614 */       ArrayList<String> removeList = new ArrayList();
/* 8615 */       for (int i = 0; i < aListOfResourceIds.size(); i++) {
/* 8616 */         int id = Integer.parseInt((String)aListOfResourceIds.get(i));
/* 8617 */         if ((id < Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId())) || (id > EnterpriseUtil.getDistributedEndResourceId())) {
/* 8618 */           removeList.add(String.valueOf(id));
/*      */         }
/*      */       }
/* 8621 */       aListOfResourceIds.removeAll(removeList);
/*      */     } catch (Exception ex) {
/* 8623 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String getTreeViewCondition(String mgId, String treeview)
/*      */   {
/*      */     try
/*      */     {
/* 8633 */       StringBuilder condnBuilder = new StringBuilder();
/* 8634 */       if (treeview.equals("default")) {
/* 8635 */         condnBuilder.append("(").append("AM_HOLISTICAPPLICATION.HAID=").append(mgId).append(")");
/* 8636 */         return condnBuilder.toString();
/*      */       }
/* 8638 */       condnBuilder.append("(");
/* 8639 */       if ((treeview.equalsIgnoreCase("all")) || (treeview.equalsIgnoreCase("toplevel")))
/*      */       {
/* 8641 */         HashMap<String, String> list1 = new HashMap();
/* 8642 */         ParentChildRelationalUtil.getParentMGs(list1, mgId);
/* 8643 */         AMLog.debug("CommonAPIUtil.getTreeViewCondition():list1: " + list1);
/* 8644 */         if ((list1 != null) && (list1.size() > 0)) {
/* 8645 */           int k = 0;
/* 8646 */           Iterator<String> itr1 = list1.keySet().iterator();
/* 8647 */           while (itr1.hasNext()) {
/* 8648 */             String key = (String)itr1.next();
/* 8649 */             String value = (String)list1.get(key);
/* 8650 */             condnBuilder.append("(AM_HOLISTICAPPLICATION.HAID").append("=").append(key).append(" AND ").append("AM_PARENTCHILDMAPPER.CHILDID").append("=").append(value).append(")");
/* 8651 */             k++;
/* 8652 */             if (k != list1.size()) {
/* 8653 */               condnBuilder.append(" OR ");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 8659 */       if ((treeview.equalsIgnoreCase("all")) || (treeview.equalsIgnoreCase("bottomlevel")))
/*      */       {
/* 8661 */         HashMap<String, ArrayList<String>> list2 = new HashMap();
/* 8662 */         ParentChildRelationalUtil.getChildMGs(list2, mgId);
/* 8663 */         AMLog.debug("CommonAPIUtil.getTreeViewCondition():list2: " + list2);
/* 8664 */         if ((list2 != null) && (list2.size() > 0)) {
/* 8665 */           int k = 0;
/* 8666 */           if (!condnBuilder.toString().equals("(")) {
/* 8667 */             condnBuilder.append(" OR ");
/*      */           }
/* 8669 */           Iterator<String> itr1 = list2.keySet().iterator();
/* 8670 */           while (itr1.hasNext()) {
/* 8671 */             String key = (String)itr1.next();
/* 8672 */             ArrayList<String> value = (ArrayList)list2.get(key);
/* 8673 */             String valueStr = value.toString().replaceAll(", ", ",");
/* 8674 */             valueStr = valueStr.substring(1, valueStr.length() - 1);
/* 8675 */             condnBuilder.append("(AM_HOLISTICAPPLICATION.HAID").append("=").append(key).append(" AND ").append("AM_PARENTCHILDMAPPER.CHILDID").append(" IN (").append(valueStr).append("))");
/* 8676 */             k++;
/* 8677 */             if (k != list2.size()) {
/* 8678 */               condnBuilder.append(" OR ");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 8683 */       if (condnBuilder.toString().equals("("))
/*      */       {
/* 8685 */         return null;
/*      */       }
/* 8687 */       condnBuilder.append(")");
/* 8688 */       AMLog.debug("CommonAPIUtil.getTreeViewCondition():condnBuilder length: " + condnBuilder.length());
/* 8689 */       return condnBuilder.toString();
/*      */     } catch (Exception ex) {
/* 8691 */       ex.printStackTrace();
/*      */     }
/* 8693 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String updateDbPollTable(HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 8701 */     Statement st = null;
/*      */     try {
/* 8703 */       String apiCallFromAAM = request.getParameter("apicallfrom");
/* 8704 */       isAPICallFromAAM = (apiCallFromAAM != null) && (apiCallFromAAM.equals("admin"));
/* 8705 */       if ((!isAPICallFromAAM) && (!getOwnerRole(request).equals("ADMIN")) && ((!EnterpriseUtil.isAdminServer()) || (!getOwnerRole(request).equals("ENTERPRISEADMIN")))) {
/* 8706 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.admin.noprivilege"), "4008");
/*      */       }
/* 8708 */       Object aListofQueries = new ArrayList();
/* 8709 */       String[] keyString = { "RESOURCES", "AGENTREPORT" };
/* 8710 */       for (int i = 0; i < keyString.length; i++) {
/* 8711 */         StringBuilder queryBuilder = new StringBuilder();
/* 8712 */         queryBuilder.append("INSERT INTO DBPOLL VALUES ('").append(keyString[i]).append("ADMIN").append("'").append(",").append(DBQueryUtil.convertVarBinaryToVarChar(request.getParameter(keyString[i]))).append(")");
/* 8713 */         ((ArrayList)aListofQueries).add(queryBuilder.toString());
/*      */       }
/* 8715 */       if (((ArrayList)aListofQueries).size() > 0) {
/* 8716 */         st = AMConnectionPool.getConnection().createStatement();
/* 8717 */         for (int i = 0; i < ((ArrayList)aListofQueries).size(); i++) {
/* 8718 */           st.addBatch((String)((ArrayList)aListofQueries).get(i));
/*      */         }
/* 8720 */         st.executeBatch();
/* 8721 */         ReportUtil.updateAdminEncodeKey();
/*      */       }
/*      */     } catch (Exception ex) { boolean isAPICallFromAAM;
/* 8724 */       ex.printStackTrace();
/* 8725 */       return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.apikey.wrongserver.message"), "4128");
/*      */     } finally {
/* 8727 */       if (st != null) {
/*      */         try {
/* 8729 */           st.close();
/*      */         } catch (Exception ex1) {
/* 8731 */           ex1.printStackTrace();
/*      */         }
/* 8733 */         st = null;
/*      */       }
/*      */     }
/* 8736 */     return URITree.generateXML(request, response, FormatUtil.getString("Success"), "4000");
/*      */   }
/*      */   
/*      */   public static String getQueryOutput(HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 8740 */     String outputString = "";
/* 8741 */     String xmlString = "";
/* 8742 */     String uri = request.getRequestURI();
/* 8743 */     boolean isJsonFormat = uri.toLowerCase().contains("json");
/* 8744 */     if (isJsonFormat) {
/* 8745 */       response.setContentType("text/plain; charset=UTF-8");
/*      */     } else {
/* 8747 */       response.setContentType("text/xml; charset=UTF-8");
/*      */     }
/*      */     
/* 8750 */     if (request.getParameter("query") != null) {
/* 8751 */       if (!isAdminRole(request)) {
/* 8752 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4444");
/* 8753 */         return xmlString; }
/* 8754 */       if (!request.getParameter("query").toLowerCase().startsWith("select")) {
/* 8755 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.restapi.query.invalid"), "4444");
/* 8756 */         return xmlString;
/*      */       }
/*      */     } else {
/* 8759 */       AMLog.debug("REST API : getQueryOutput : Query string is empty");
/* 8760 */       xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.restapi.query.empty"), "4444");
/* 8761 */       return xmlString;
/*      */     }
/* 8763 */     AMLog.debug("REST API : getQueryOutput : Query is " + request.getParameter("query"));
/*      */     
/* 8765 */     ResultSet rs = null;
/* 8766 */     List<Map<String, String>> queryOutput = new ArrayList();
/* 8767 */     String query = request.getParameter("query");
/* 8768 */     query = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(query);
/*      */     try {
/* 8770 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 8771 */       ResultSetMetaData metadata = rs.getMetaData();
/* 8772 */       boolean isEmpty = true;
/* 8773 */       Map<String, String> rows; while (rs.next()) {
/* 8774 */         isEmpty = false;
/* 8775 */         rows = new LinkedHashMap();
/* 8776 */         for (int i = 1; i <= metadata.getColumnCount(); i++) {
/* 8777 */           String columnName = metadata.getColumnName(i);
/* 8778 */           rows.put(columnName, rs.getString(columnName));
/*      */         }
/* 8780 */         queryOutput.add(rows);
/*      */       }
/* 8782 */       if (isEmpty) {
/* 8783 */         xmlString = URITree.generateXML(request, response, FormatUtil.getString("am.restapi.query.output.empty"), "4000");
/* 8784 */         return xmlString;
/*      */       }
/*      */     } catch (Exception e) {
/* 8787 */       e.printStackTrace();
/*      */     } finally {
/* 8789 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 8792 */     HashMap<String, Object> results = new HashMap();
/* 8793 */     results.put("response-code", "4000");
/* 8794 */     results.put("uri", uri);
/* 8795 */     results.put("result", queryOutput);
/* 8796 */     results.put("sortingParam", "GROUPNAME");
/* 8797 */     results.put("parentNode", "Usergroups");
/* 8798 */     results.put("nodeName", "Usergroup");
/* 8799 */     outputString = getOutputAsString(results, isJsonFormat);
/* 8800 */     return outputString;
/*      */   }
/*      */   
/*      */   public static String diagnosticConfigAPI(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 8804 */     String outputString = null;
/*      */     try {
/* 8806 */       if (request.getMethod().equals("GET"))
/*      */       {
/* 8808 */         return listDiagnosticInfo(request, response, isJsonFormat);
/*      */       }
/* 8810 */       return editDiagnosticInfo(request, response, isJsonFormat);
/*      */     }
/*      */     catch (Exception ex) {
/* 8813 */       ex.printStackTrace();
/*      */     }
/* 8815 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String listDiagnosticInfo(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 8819 */     String outputString = null;
/*      */     try {
/* 8821 */       ArrayList<Hashtable<String, String>> diagnosticDetails = new ArrayList();
/* 8822 */       String uri = request.getRequestURI();
/* 8823 */       if (isAdminRole(request))
/*      */       {
/* 8825 */         diagnosticDetails = DiagnosticsAPIUtil.getDiagnosticDetails(null);
/*      */       }
/*      */       else {
/* 8828 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 8831 */       HashMap results = new HashMap();
/* 8832 */       results.put("response-code", "4000");
/* 8833 */       results.put("uri", uri);
/* 8834 */       results.put("result", diagnosticDetails);
/* 8835 */       results.put("sortingParam", "DIAGONISTICNAME");
/* 8836 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 8838 */       ex.printStackTrace();
/*      */     }
/* 8840 */     return outputString;
/*      */   }
/*      */   
/*      */   private static String editDiagnosticInfo(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) throws Exception {
/* 8844 */     String outputString = null;
/*      */     try {
/* 8846 */       ArrayList<Hashtable<String, String>> diagnosticDetails = new ArrayList();
/* 8847 */       String uri = request.getRequestURI();
/* 8848 */       int diagnosticid = 0;
/* 8849 */       HashMap<String, String> updateDetails = new HashMap();
/* 8850 */       String diagnosticname = null;
/* 8851 */       if (isAdminRole(request))
/*      */       {
/* 8853 */         if (request.getParameter("diagnosticid") != null) {
/* 8854 */           String id = request.getParameter("diagnosticid");
/* 8855 */           if (Constants.isIntegerNumber(id)) {
/* 8856 */             diagnosticid = Integer.valueOf(id).intValue();
/* 8857 */             String diagName = DiagnosticsAPIUtil.getDiagnosticName(diagnosticid);
/* 8858 */             if (diagName == null) {
/* 8859 */               AMLog.debug("REST API : Diagnostic Details does not exist for id " + diagnosticid);
/* 8860 */               return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.diagnostic.info.notexists.text"), "4103");
/*      */             }
/*      */             
/* 8863 */             diagnosticDetails = DiagnosticsAPIUtil.getDiagnosticDetails(diagName);
/* 8864 */             Hashtable<String, String> diagDetails = (Hashtable)diagnosticDetails.get(0);
/* 8865 */             diagnosticname = (String)diagDetails.get("DIAGONISTICNAME");
/*      */           }
/*      */           else {
/* 8868 */             AMLog.debug("REST API : Diagnosticid should be an integer");
/* 8869 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "diagnosticid" }), "4103");
/*      */           }
/*      */         }
/* 8872 */         else if (request.getParameter("diagnosticname") != null) {
/* 8873 */           diagnosticname = request.getParameter("diagnosticname");
/* 8874 */           diagnosticDetails = DiagnosticsAPIUtil.getDiagnosticDetails(diagnosticname);
/* 8875 */           if (diagnosticDetails.size() == 0) {
/* 8876 */             AMLog.debug("REST API : Diagnostic Details does not exist for name " + diagnosticname);
/* 8877 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.diagnostic.info.notexists.text"), "4103");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 8882 */         updateDetails.put("diagnosticname", diagnosticname);
/* 8883 */         updateDetails.put("diagnosticid", String.valueOf(diagnosticid));
/*      */         
/* 8885 */         if (request.getParameter("pollinterval") != null) {
/* 8886 */           String pollInterval = request.getParameter("pollinterval");
/* 8887 */           if (Constants.isIntegerNumber(pollInterval)) {
/* 8888 */             updateDetails.put("pollinterval", pollInterval);
/*      */           } else {
/* 8890 */             AMLog.debug("REST API : pollinterval should be an integer");
/* 8891 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "pollinterval" }), "4103");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 8896 */         if (request.getParameter("pollcount") != null) {
/* 8897 */           String pollCount = request.getParameter("pollcount");
/* 8898 */           if (Constants.isIntegerNumber(pollCount)) {
/* 8899 */             updateDetails.put("pollcount", pollCount);
/*      */           } else {
/* 8901 */             AMLog.debug("REST API : pollcount should be an integer");
/* 8902 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "pollcount" }), "4103");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 8907 */         if (request.getParameter("criticalvalue") != null) {
/* 8908 */           String criticalValue = request.getParameter("criticalvalue");
/* 8909 */           updateDetails.put("criticalvalue", criticalValue);
/*      */         }
/*      */         
/* 8912 */         DiagnosticsAPIUtil.updateDiagnosticDetails(updateDetails);
/* 8913 */         diagnosticDetails = DiagnosticsAPIUtil.getDiagnosticDetails(diagnosticname);
/*      */       }
/*      */       else {
/* 8916 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */       
/* 8919 */       HashMap results = new HashMap();
/* 8920 */       results.put("response-code", "4000");
/* 8921 */       results.put("uri", uri);
/* 8922 */       results.put("result", diagnosticDetails);
/* 8923 */       results.put("sortingParam", "DIAGONISTICNAME");
/* 8924 */       results.put("parentNode", "DiagnosticInfo");
/* 8925 */       results.put("nodeName", "Diagnostics");
/* 8926 */       outputString = getOutputAsString(results, isJsonFormat);
/*      */     } catch (Exception ex) {
/* 8928 */       ex.printStackTrace();
/*      */     }
/* 8930 */     return outputString;
/*      */   }
/*      */   
/* 8933 */   public static String clearDiagnosticAlert(HttpServletRequest request, HttpServletResponse response, boolean isJsonFormat) { String outputString = null;
/* 8934 */     boolean isDiscarded = false;
/*      */     try {
/* 8936 */       String uri = request.getRequestURI();
/* 8937 */       if (isAdminRole(request)) {
/* 8938 */         if (request.getParameter("resourceid") != null) {
/* 8939 */           String AlertID = request.getParameter("resourceid");
/* 8940 */           if (Constants.isIntegerNumber(AlertID)) {
/* 8941 */             String userName = getUsername(request.getParameter("apikey"));
/* 8942 */             Map<String, String> resultProp = APMDiagnosticsFaultHandler.getInstance().clearAlert(AlertID, userName);
/* 8943 */             isDiscarded = ((String)resultProp.get("RESULT")).equalsIgnoreCase("true");
/* 8944 */             if (!isDiscarded) {
/* 8945 */               AMLog.debug("REST API : Problem in discarding the diagnostic alert");
/* 8946 */               return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.diagnostic.clear.failure"), "4444");
/*      */             }
/*      */           } else {
/* 8949 */             AMLog.debug("REST API : Alertid should be an integer");
/* 8950 */             return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.common.numeric.message.text", new String[] { "diagnosticid" }), "4103");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 8955 */         outputString = URITree.generateXML(request, response, FormatUtil.getString("Success"), "4000");
/*      */       } else {
/* 8957 */         return URITree.generateXML(request, response, FormatUtil.getString("am.webclient.api.user.violation"), "4037");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 8961 */       e.printStackTrace();
/*      */     }
/* 8963 */     return outputString;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\CommonAPIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */