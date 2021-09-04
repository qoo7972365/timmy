/*      */ package com.adventnet.appmanager.hostresource.struts;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.hostresources.datacollection.HardwareConstants;
/*      */ import com.adventnet.appmanager.server.hostresources.datacollection.HardwareDataCollector;
/*      */ import com.adventnet.appmanager.server.template.MonitorGroupUtil;
/*      */ import com.adventnet.appmanager.struts.actions.AdminTools;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DataCollectionComponent;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.WinServiceActionUtil;
/*      */ import com.me.apm.scheduledtasks.util.ScheduledTasksUtil;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
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
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ public final class HostResourceDispatchAction extends DispatchAction
/*      */ {
/*      */   public ActionForward getOverViewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   51 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*      */ 
/*   54 */     boolean unknownHost = false;
/*   55 */     boolean configuredState = false;
/*   56 */     String name = null;
/*   57 */     String modeofcollection = null;
/*   58 */     String hostCategory = null;
/*   59 */     String hostErrorMessage = null;
/*   60 */     String hostResourceName = null;
/*   61 */     String hostResourceType = null;
/*   62 */     String hostUserName = null;
/*   63 */     String hostPassWord = null;
/*   64 */     String hostPrompt = null;
/*   65 */     int hostPollInterval = 60;
/*   66 */     ArrayList result = null;
/*   67 */     StringBuffer queryBuff = new StringBuffer();
/*   68 */     Vector hostCategoryAsVector = new Vector();
/*   69 */     ResultSet modetails = null;
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*   75 */       modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*   76 */       String displayname; int configured; if (modetails.next())
/*      */       {
/*   78 */         name = modetails.getString("RESOURCENAME");
/*   79 */         displayname = modetails.getString("DISPLAYNAME");
/*   80 */         configured = modetails.getInt("DCSTARTED");
/*   81 */         if ((configured == 2) || (configured == 1))
/*      */         {
/*   83 */           configuredState = true;
/*      */         }
/*      */         
/*   86 */         if (configured == 2)
/*      */         {
/*   88 */           unknownHost = true;
/*      */         }
/*   90 */         request.setAttribute("showdata", configured + "");
/*   91 */         request.setAttribute("displayname", displayname);
/*   92 */         request.setAttribute("hostConfigured", Boolean.valueOf(configuredState));
/*      */       }
/*      */       else
/*      */       {
/*   96 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*      */       }
/*   98 */       AMConnectionPool.closeStatement(modetails);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  103 */       if (unknownHost)
/*      */       {
/*  105 */         modetails = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,HostDetails.ERRORMSG,HostDetails.USERNAME,HostDetails.PASSWORD,HostDetails.PROMPT,HostDetails.CATEGORY,AM_HOSTINFO.MODE,CollectData.POLLINTERVAL from AM_ManagedObject,HostDetails,AM_HOSTINFO,CollectData where HostDetails.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_HOSTINFO.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid);
/*  106 */         if (modetails.next())
/*      */         {
/*  108 */           hostResourceName = modetails.getString("RESOURCENAME");
/*  109 */           hostResourceType = modetails.getString("TYPE");
/*  110 */           modeofcollection = modetails.getString("MODE");
/*  111 */           hostErrorMessage = modetails.getString("ERRORMSG");
/*  112 */           hostUserName = modetails.getString("USERNAME");
/*  113 */           hostPassWord = modetails.getString("PASSWORD");
/*  114 */           hostPrompt = modetails.getString("PROMPT");
/*  115 */           hostCategory = modetails.getString("CATEGORY");
/*  116 */           hostPollInterval = modetails.getInt("POLLINTERVAL");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  121 */         boolean forwardNoData = true;
/*  122 */         modetails = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,HostDetails.ERRORMSG,HostDetails.USERNAME,HostDetails.PASSWORD,HostDetails.PROMPT,HostDetails.CATEGORY,CollectData.POLLINTERVAL from AM_ManagedObject,HostDetails,CollectData where HostDetails.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid);
/*  123 */         if (modetails.next())
/*      */         {
/*  125 */           hostResourceName = modetails.getString("RESOURCENAME");
/*  126 */           hostResourceType = modetails.getString("TYPE");
/*  127 */           hostErrorMessage = modetails.getString("ERRORMSG");
/*  128 */           hostUserName = modetails.getString("USERNAME");
/*  129 */           hostPassWord = modetails.getString("PASSWORD");
/*  130 */           hostPrompt = modetails.getString("PROMPT");
/*  131 */           hostCategory = modetails.getString("CATEGORY");
/*  132 */           forwardNoData = false;
/*      */         }
/*  134 */         if (forwardNoData)
/*      */         {
/*  136 */           return new ActionForward("/jsp/NoData.jsp?message=Internal Server Error.Unable to get the data for the Monitor Instance.");
/*      */         }
/*      */       }
/*  139 */       request.setAttribute("mode", modeofcollection);
/*  140 */       request.setAttribute("hostResName", hostResourceName);
/*  141 */       request.setAttribute("hostResType", hostResourceType);
/*  142 */       request.setAttribute("hostErrorMessage", hostErrorMessage);
/*  143 */       request.setAttribute("hostConfigured", Boolean.valueOf(configuredState));
/*  144 */       request.setAttribute("name", name);
/*  145 */       request.setAttribute("resourceid", resourceid);
/*  146 */       request.setAttribute("haid", request.getParameter("haid"));
/*  147 */       request.setAttribute("appName", request.getParameter("appName"));
/*  148 */       AMConnectionPool.closeStatement(modetails);
/*      */       
/*      */ 
/*      */ 
/*  152 */       HashMap map = ClientDBUtil.getSystemInfoAndPollingInfoForHost(resourceid);
/*  153 */       if (map != null)
/*      */       {
/*  155 */         request.setAttribute("systeminfo", map);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  161 */       long time = 0L;
/*  162 */       String dctime = "select MAX(DCTIME) from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_HOST_PROCESS_INSTANCE where AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='Process' and AM_PARENTCHILDMAPPER.CHILDID=AM_HOST_PROCESS_INSTANCE.RESOURCEID and AM_ManagedObject.RESOURCEID=AM_HOST_PROCESS_INSTANCE.RESOURCEID AND AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/*  163 */       modetails = AMConnectionPool.executeQueryStmt(dctime);
/*  164 */       if (modetails.next())
/*      */       {
/*  166 */         time = modetails.getLong(1);
/*      */       }
/*  168 */       AMConnectionPool.closeStatement(modetails);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  173 */       ManagedApplication mo = new ManagedApplication();
/*  174 */       ArrayList list1 = mo.getRows("select AM_HOST_PROCESS_INFO.RESOURCEID,DISPLAYNAME,PROCESSNAME,COMMAND,COALESCE(INSTANCE,'-1') INSTANCE,COALESCE(PCPU,'-1') PCPU,COALESCE(PMEM,'-1') PMEM from AM_HOST_PROCESS_INFO left outer join AM_HOST_PROCESS_INSTANCE on AM_HOST_PROCESS_INFO.RESOURCEID=AM_HOST_PROCESS_INSTANCE.RESOURCEID and AM_HOST_PROCESS_INSTANCE.DCTIME=" + time + " left outer join AM_HOST_PROCESS_CPUMEM on AM_HOST_PROCESS_INFO.RESOURCEID=AM_HOST_PROCESS_CPUMEM.RESOURCEID and AM_HOST_PROCESS_CPUMEM.DCTIME=" + time + " where AM_HOST_PROCESS_INFO.PARENTID=" + resourceid);
/*      */       
/*      */ 
/*  177 */       if (list1.size() != 0)
/*      */       {
/*  179 */         request.setAttribute("process", list1);
/*      */       }
/*  181 */       ArrayList unManagedList = new ArrayList();
/*  182 */       String unmanageQuery = "select RESOURCEID from AM_UnManagedNodes,AM_HOST_PROCESS_INFO WHERE AM_UnManagedNodes.RESID=AM_HOST_PROCESS_INFO.RESOURCEID AND PARENTID=" + resourceid;
/*  183 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  186 */         rs = AMConnectionPool.executeQueryStmt(unmanageQuery);
/*  187 */         while (rs.next())
/*      */         {
/*  189 */           unManagedList.add(rs.getString(1));
/*      */         }
/*  191 */         AMConnectionPool.closeStatement(rs);
/*  192 */         if (hostResourceType.toLowerCase().indexOf("windows") != -1)
/*      */         {
/*  194 */           unmanageQuery = "SELECT RESID FROM AM_UnManagedNodes,AM_PARENTCHILDMAPPER,AM_ManagedObject WHERE AM_PARENTCHILDMAPPER.CHILDID=AM_UnManagedNodes.RESID AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE='Service' AND AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/*  195 */           rs = AMConnectionPool.executeQueryStmt(unmanageQuery);
/*  196 */           while (rs.next())
/*      */           {
/*  198 */             unManagedList.add(rs.getString(1));
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  204 */         e.printStackTrace();
/*      */       }
/*      */       finally {}
/*      */       
/*      */ 
/*      */ 
/*  210 */       request.setAttribute("UnManagedChildList", unManagedList);
/*  211 */       modetails = AMConnectionPool.executeQueryStmt("select tma.PROCESSID from AM_TEMPLATE_MONITOR_ASSOCIATION tma inner join AM_TEMPLATE_PROCESS_DEFINITION tpd on tma.TEMPLATEPROCESSID=tpd.TEMPLATEPROCESSID where MONITORID=" + resourceid + " and tpd.PROCESSTYPE=1");
/*  212 */       HashSet<String> regexids = new HashSet();
/*  213 */       while (modetails.next())
/*      */       {
/*  215 */         regexids.add(modetails.getString("PROCESSID"));
/*      */       }
/*  217 */       AMConnectionPool.closeStatement(modetails);
/*  218 */       request.setAttribute("regexprocess", regexids);
/*      */       
/*      */ 
/*      */ 
/*  222 */       boolean isWindowsResType = (hostResourceType != null) && (hostResourceType.toLowerCase().indexOf("windows") != -1);
/*  223 */       if (isWindowsResType) {
/*  224 */         String ServiceQuery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE='Service'";
/*  225 */         ArrayList configuredservices = mo.getRows(ServiceQuery);
/*  226 */         if ((configuredservices != null) && (configuredservices.size() > 0)) {
/*  227 */           request.setAttribute("windowsservices", configuredservices);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  234 */       if ((hostCategory != null) && (!hostCategory.equals("NULL")))
/*      */       {
/*  236 */         StringTokenizer tokenizeCategory = new StringTokenizer(hostCategory, ",");
/*  237 */         while (tokenizeCategory.hasMoreTokens())
/*      */         {
/*  239 */           String catName = tokenizeCategory.nextToken().trim();
/*  240 */           hostCategoryAsVector.add(catName);
/*      */         }
/*      */       }
/*  243 */       hostCategoryAsVector.add("Process");
/*  244 */       request.setAttribute("category", hostCategoryAsVector);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  249 */       request.setAttribute("ids", getAttributesId(hostResourceType));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  268 */       boolean isLinuxResType = (hostResourceType != null) && (hostResourceType.toLowerCase().indexOf("linux") != -1);
/*  269 */       if (isLinuxResType) {
/*      */         try {
/*  271 */           String threadCount = null;
/*  272 */           String threadCount_qry = "SELECT THREADCOUNT AS COUNT FROM AM_HostThreadCount WHERE RESOURCEID=" + resourceid + " and COLLECTIONTIME = (Select MAX(COLLECTIONTIME) from AM_HostThreadCount where RESOURCEID=" + resourceid + ")";
/*  273 */           modetails = AMConnectionPool.executeQueryStmt(threadCount_qry);
/*  274 */           if (modetails.next())
/*      */           {
/*  276 */             threadCount = modetails.getString("COUNT");
/*  277 */             AMLog.info("HOSTRESOURCE :: RESOURCEID :: " + resourceid + " :: threadcount---->" + threadCount);
/*      */           }
/*  279 */           request.setAttribute("threadCount", threadCount);
/*      */         } catch (Exception e) {
/*  281 */           AMLog.debug("HOSTRESOURCE :: RESOURCEID :: " + resourceid + " :: threadcount Error :: " + e.getMessage());
/*  282 */           e.printStackTrace();
/*      */ 
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  291 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  294 */       AMConnectionPool.closeStatement(modetails);
/*      */     }
/*      */     
/*  297 */     return mapping.findForward("OverViewDetailsPage");
/*      */   }
/*      */   
/*      */   public ActionForward getCpuCoreDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  302 */     HashMap<String, String> cpuCoresDetails = new HashMap();
/*  303 */     String resourceId = request.getParameter("resourceid");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  309 */     StringBuffer queryBuff = new StringBuffer();
/*  310 */     queryBuff.append("select CHILDID as RESID,DISPLAYNAME as DISPNAME from AM_PARENTCHILDMAPPER,AM_ManagedObject where PARENTID=");
/*  311 */     queryBuff.append(resourceId);
/*  312 */     queryBuff.append(" and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and TYPE='CPU Core'");
/*      */     
/*  314 */     ResultSet rs = null;
/*      */     try {
/*  316 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/*  317 */       while (rs.next()) {
/*  318 */         String resId = rs.getString("RESID");
/*  319 */         String disp = rs.getString("DISPNAME");
/*  320 */         disp = disp.substring(disp.lastIndexOf("-") + 1);
/*  321 */         cpuCoresDetails.put(disp, resId);
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  325 */       ex.printStackTrace();
/*  326 */       if (rs != null) {
/*  327 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     } finally {
/*  330 */       if (rs != null) {
/*  331 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*  335 */     request.setAttribute("cpucoredetails", cpuCoresDetails);
/*  336 */     queryBuff = null;
/*  337 */     return mapping.findForward("CpuCoreDetailsPage");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward getNetworkDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  344 */     String strResourceId = request.getParameter("resourceid");
/*  345 */     long resourceId = -1L;
/*      */     try {
/*  347 */       resourceId = Long.parseLong(strResourceId);
/*      */     } catch (Exception ex) {
/*  349 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  352 */     if (resourceId == -1L) {
/*  353 */       request.setAttribute("NetworkInfo", new HashMap());
/*  354 */       return mapping.findForward("NetworkDetailsPage");
/*      */     }
/*      */     
/*  357 */     ArrayList<ArrayList<String>> aListNetworkInterfaceDetails = getNetworkInterface(resourceId);
/*  358 */     ArrayList<ArrayList<String>> aListNetworkAdapterDetails = getNetworkAdapter(strResourceId);
/*  359 */     HashMap<String, ArrayList<ArrayList<String>>> networkInterfacesAndAdapters = new HashMap();
/*  360 */     networkInterfacesAndAdapters.put("NetInterface", aListNetworkInterfaceDetails);
/*  361 */     networkInterfacesAndAdapters.put("NetAdapter", aListNetworkAdapterDetails);
/*  362 */     request.setAttribute("NetworkInfo", networkInterfacesAndAdapters);
/*  363 */     return mapping.findForward("NetworkDetailsPage");
/*      */   }
/*      */   
/*      */   public ActionForward getMemoryDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  368 */     String resourceId = request.getParameter("resourceid");
/*  369 */     String resourceType = request.getParameter("resourceType");
/*  370 */     HashMap<String, HashMap<String, String>> latestMemUtilData = getLatestMemoryData(resourceId);
/*  371 */     request.setAttribute("latestMemUtilData", latestMemUtilData);
/*  372 */     HashMap attributeIds = getAttributesId(resourceType);
/*  373 */     request.setAttribute("attrbIds", attributeIds);
/*  374 */     return mapping.findForward("MemoryDetailsPage");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getEventLogDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  380 */     HashMap logdetails = com.me.apm.eventlog.util.EventLogUtil.getEventLogDetails(request.getParameter("resourceid"));
/*  381 */     String hoststatus = (String)logdetails.get("hosteventlogstatus");
/*  382 */     ArrayList latevents = (ArrayList)logdetails.get("latestevents");
/*  383 */     request.setAttribute("hosteventlogstatus", hoststatus);
/*  384 */     request.setAttribute("latestevents", latevents);
/*  385 */     return mapping.findForward("EventLogDetailsPage");
/*      */   }
/*      */   
/*      */   public ActionForward getScheduledTasksDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  390 */     String hostResourceName = null;
/*  391 */     String hostResourceType = null;
/*  392 */     ResultSet modetails = null;
/*  393 */     String resourceid = request.getParameter("resourceid");
/*      */     try {
/*  395 */       modetails = AMConnectionPool.executeQueryStmt("SELECT AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE from AM_ManagedObject WHERE AM_ManagedObject.RESOURCEID=" + resourceid);
/*      */       
/*  397 */       if (modetails.next())
/*      */       {
/*  399 */         hostResourceName = modetails.getString("RESOURCENAME");
/*  400 */         hostResourceType = modetails.getString("TYPE");
/*      */       }
/*      */     }
/*      */     catch (SQLException e) {
/*  404 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  408 */       AMConnectionPool.closeResultSet(modetails);
/*      */     }
/*  410 */     request.setAttribute("ids", getAttributesId(hostResourceType));
/*  411 */     request.setAttribute("hostResName", hostResourceName);
/*  412 */     request.setAttribute("hostResType", hostResourceType);
/*  413 */     request.setAttribute("resourceid", resourceid);
/*  414 */     request.setAttribute("haid", request.getParameter("haid"));
/*  415 */     request.setAttribute("appName", request.getParameter("appName"));
/*  416 */     HashMap taskdetails = ScheduledTasksUtil.getConfiguredTasksForHost(request.getParameter("resourceid"), true, false);
/*  417 */     request.setAttribute("latestTasks", taskdetails);
/*  418 */     return mapping.findForward("ScheduledTasksDetailsPage");
/*      */   }
/*      */   
/*      */   public ActionForward getConfigDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  423 */     String resourceId = request.getParameter("resourceid");
/*      */     
/*      */ 
/*  426 */     ResultSet rs = null;
/*  427 */     String confResIds = "";
/*  428 */     HashMap<String, ArrayList<String>> childIds = new HashMap();
/*  429 */     StringBuffer queryBuff = new StringBuffer();
/*  430 */     queryBuff.append("SELECT RESOURCEID,TYPE FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=");
/*  431 */     queryBuff.append(resourceId);
/*  432 */     queryBuff.append(" and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE in ('HOST_CONF_PROC','HOST_CONF_NET','HOST_CONF_PRNT')");
/*      */     try {
/*  434 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/*  435 */       while (rs.next()) {
/*  436 */         String resId = rs.getString("RESOURCEID");
/*  437 */         String type = rs.getString("TYPE");
/*  438 */         ArrayList<String> al = (ArrayList)childIds.get(type);
/*  439 */         if (al == null) {
/*  440 */           al = new ArrayList();
/*  441 */           childIds.put(type, al);
/*      */         }
/*  443 */         al.add(resId);
/*  444 */         confResIds = confResIds + resId + ",";
/*      */       }
/*      */     } catch (Exception ex) {
/*  447 */       ex.printStackTrace();
/*      */     } finally {
/*  449 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  451 */     if (confResIds.length() == 0) {
/*  452 */       confResIds = resourceId;
/*      */     } else {
/*  454 */       confResIds = confResIds + resourceId;
/*      */     }
/*      */     
/*  457 */     HashMap configDetails = pullConfigDetails(confResIds);
/*  458 */     request.setAttribute("CHILD_IDS", childIds);
/*  459 */     request.setAttribute("CONFIG_DETAILS", configDetails);
/*  460 */     return mapping.findForward("ConfigDetailsPage");
/*      */   }
/*      */   
/*      */   public ActionForward getHardwareHealthDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  465 */     String resourceId = request.getParameter("resourceid");
/*  466 */     String mode = request.getParameter("mode");
/*  467 */     int manufacturer = HardwareDataCollector.getManufacturerFromDB(resourceId);
/*  468 */     String manufac = String.valueOf(manufacturer);
/*  469 */     request.setAttribute("manufacturer", manufac);
/*  470 */     long collectionTime = getMaxCollectionTime(resourceId);
/*      */     
/*      */ 
/*  473 */     ResultSet rs = null;
/*  474 */     List<String> entityList = new ArrayList();
/*  475 */     Map<String, Map<String, String>> devicesDetails = new HashMap();
/*      */     
/*  477 */     List<String> orderToDisplay = new ArrayList();
/*  478 */     HashMap<String, String> hardwareValues = new HashMap();
/*  479 */     HashMap<String, String> hardwareConfigDetails = new HashMap();
/*  480 */     HashMap<String, String> hwAttributeIDsList = new HashMap();
/*      */     
/*  482 */     StringBuffer configResourceIDList = new StringBuffer("");
/*  483 */     StringBuffer configAttributeIDList = new StringBuffer("");
/*      */     
/*  485 */     HashMap<String, String> hardwareValuesResIDMap = new HashMap();
/*  486 */     StringBuffer temperatureResIDList = new StringBuffer("");
/*  487 */     StringBuffer fanResIDList = new StringBuffer("");
/*  488 */     StringBuffer powerSupplyResIDList = new StringBuffer("");
/*  489 */     StringBuffer voltageResIDList = new StringBuffer("");
/*  490 */     StringBuffer arrayResIDList = new StringBuffer("");
/*      */     
/*  492 */     String enabledAttributes = new HardwareDataCollector().getDCEnabledAttributes(resourceId);
/*  493 */     AMLog.debug("Hardware enabled Attributes for RESOURCEID ::" + resourceId + " : " + enabledAttributes);
/*  494 */     StringBuffer queryBuff = new StringBuffer();
/*  495 */     queryBuff.append("SELECT RESOURCEID,RESOURCENAME,TYPE FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=");
/*  496 */     queryBuff.append(resourceId);
/*  497 */     queryBuff.append(" and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE in (");
/*      */     
/*  499 */     if (enabledAttributes.contains("Fan")) {
/*  500 */       queryBuff.append("'Hw_Fan',");
/*  501 */       orderToDisplay.add("Hw_Fan");
/*      */     }
/*  503 */     if (enabledAttributes.contains("Temperature")) {
/*  504 */       queryBuff.append("'Hw_Temperature',");
/*  505 */       orderToDisplay.add("Hw_Temperature");
/*      */     }
/*  507 */     if (enabledAttributes.contains("PowerSupply")) {
/*  508 */       queryBuff.append("'Hw_PowerSupply',");
/*  509 */       orderToDisplay.add("Hw_PowerSupply");
/*      */     }
/*  511 */     if (enabledAttributes.contains("Voltage")) {
/*  512 */       queryBuff.append("'Hw_Voltage',");
/*  513 */       if (manufacturer != 2) {
/*  514 */         orderToDisplay.add("Hw_Voltage");
/*      */       }
/*      */     }
/*  517 */     if (enabledAttributes.contains("Cpu")) {
/*  518 */       queryBuff.append("'Hw_Cpu',");
/*  519 */       orderToDisplay.add("Hw_Cpu");
/*      */     }
/*  521 */     if (enabledAttributes.contains("Memorydevice")) {
/*  522 */       queryBuff.append("'Hw_Memorydevice',");
/*  523 */       orderToDisplay.add("Hw_Memorydevice");
/*      */     }
/*  525 */     if (enabledAttributes.contains("Array")) {
/*  526 */       queryBuff.append("'Hw_Array',");
/*  527 */       if ((manufacturer != 1) || (!mode.equalsIgnoreCase("WMI"))) {
/*  528 */         orderToDisplay.add("Hw_Array");
/*      */       }
/*      */     }
/*  531 */     if (enabledAttributes.contains("Disk")) {
/*  532 */       queryBuff.append("'Hw_Disk',");
/*  533 */       orderToDisplay.add("Hw_Disk");
/*      */     }
/*  535 */     if (enabledAttributes.contains("Chassis")) {
/*  536 */       queryBuff.append("'Hw_Chassis',");
/*  537 */       orderToDisplay.add("Hw_Chassis");
/*      */     }
/*      */     
/*  540 */     if (enabledAttributes.contains("Battery")) {
/*  541 */       queryBuff.append("'Hw_Battery',");
/*  542 */       if (!mode.equalsIgnoreCase("WMI")) {
/*  543 */         orderToDisplay.add("Hw_Battery");
/*      */       }
/*      */     }
/*  546 */     queryBuff.append("''");
/*      */     
/*  548 */     queryBuff.append(") order by RESOURCENAME asc");
/*      */     
/*  550 */     AMLog.debug("Hardware Information: Query for RESOURCEID ::" + resourceId + " : " + queryBuff.toString());
/*      */     try
/*      */     {
/*  553 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/*  554 */       while (rs.next()) {
/*  555 */         String resId = rs.getString("RESOURCEID");
/*  556 */         String deviceName = rs.getString("RESOURCENAME");
/*  557 */         String type = rs.getString("TYPE");
/*  558 */         String hardwareType = type.replace("Hw_", "").toUpperCase();
/*  559 */         String value = "";
/*  560 */         String attrbId = AMAttributesCache.getHealthId(type);
/*  561 */         entityList.add(resId + "_" + attrbId);
/*  562 */         Map<String, String> hmap = (Map)devicesDetails.get(type);
/*  563 */         if (hmap == null) {
/*  564 */           hmap = new HashMap();
/*  565 */           devicesDetails.put(type, hmap);
/*      */         }
/*  567 */         int idx = deviceName.indexOf(":");
/*  568 */         if (idx != -1) {
/*  569 */           deviceName = deviceName.substring(idx + 1);
/*  570 */           idx = deviceName.indexOf("-");
/*  571 */           if (idx != -1) {
/*  572 */             deviceName = deviceName.substring(idx + 1);
/*      */           }
/*      */         }
/*  575 */         if (manufacturer == 1)
/*      */         {
/*  577 */           if ((mode.equalsIgnoreCase("WMI")) && (manufacturer == 1)) {
/*  578 */             int lastunderscore = deviceName.lastIndexOf("_");
/*  579 */             deviceName = lastunderscore != -1 ? deviceName.substring(0, deviceName.lastIndexOf("_")) : deviceName;
/*      */           }
/*      */           else {
/*  582 */             deviceName = deviceName.replaceAll("_[0-9]_?[0-9]?", "");
/*      */           }
/*      */         }
/*  585 */         hmap.put(resId, deviceName);
/*      */         
/*      */ 
/*  588 */         if (type.equals("Hw_Temperature")) {
/*  589 */           temperatureResIDList.append(resId + ",");
/*      */         }
/*  591 */         else if (type.equals("Hw_Fan")) {
/*  592 */           fanResIDList.append(resId + ",");
/*      */         }
/*  594 */         else if (type.equals("Hw_PowerSupply")) {
/*  595 */           powerSupplyResIDList.append(resId + ",");
/*      */         }
/*  597 */         else if (type.equals("Hw_Voltage")) {
/*  598 */           voltageResIDList.append(resId + ",");
/*      */         }
/*  600 */         else if (type.equals("Hw_Array")) {
/*  601 */           arrayResIDList.append(resId + ",");
/*      */         }
/*      */         
/*      */ 
/*  605 */         if (type.equals("Hw_Cpu")) {
/*  606 */           configResourceIDList.append(resId + ",");
/*  607 */           configAttributeIDList.append("40532,");
/*  608 */           configAttributeIDList.append("40533,");
/*  609 */           configAttributeIDList.append("40534,");
/*      */         }
/*  611 */         else if (type.equals("Hw_Chassis")) {
/*  612 */           configResourceIDList.append(resId + ",");
/*  613 */           configAttributeIDList.append("40562,");
/*      */         }
/*  615 */         else if (type.equals("Hw_Disk")) {
/*  616 */           configResourceIDList.append(resId + ",");
/*  617 */           configAttributeIDList.append("40543,");
/*  618 */           configAttributeIDList.append("40542,");
/*      */         }
/*  620 */         else if (type.equals("Hw_Array")) {
/*  621 */           configResourceIDList.append(resId + ",");
/*  622 */           configAttributeIDList.append("40553,");
/*      */         }
/*  624 */         else if (type.equals("Hw_Memorydevice")) {
/*  625 */           configResourceIDList.append(resId + ",");
/*  626 */           configAttributeIDList.append("40508,");
/*  627 */           configAttributeIDList.append("40507,");
/*      */         }
/*      */       }
/*      */       
/*  631 */       String configResourceIDs = configResourceIDList.length() != 0 ? configResourceIDList.substring(0, configResourceIDList.lastIndexOf(",")) : "";
/*  632 */       String configAttributeIDs = configAttributeIDList.length() != 0 ? configAttributeIDList.substring(0, configAttributeIDList.lastIndexOf(",")) : "";
/*      */       
/*  634 */       if ((configResourceIDs.length() != 0) && (configAttributeIDs.length() != 0)) {
/*  635 */         hardwareConfigDetails = getHardwareConfigValue(configResourceIDs, configAttributeIDs);
/*      */       }
/*      */       
/*  638 */       hardwareValuesResIDMap.put("Temperature", temperatureResIDList.toString());
/*  639 */       hardwareValuesResIDMap.put("Fan", fanResIDList.toString());
/*  640 */       hardwareValuesResIDMap.put("PowerSupply", powerSupplyResIDList.toString());
/*  641 */       hardwareValuesResIDMap.put("Voltage", voltageResIDList.toString());
/*  642 */       hardwareValuesResIDMap.put("Array", arrayResIDList.toString());
/*      */       
/*  644 */       hardwareValues = getHardwareValuesfromDB(hardwareValuesResIDMap, collectionTime);
/*      */     }
/*      */     catch (Exception ex) {
/*  647 */       ex.printStackTrace();
/*  648 */       AMLog.debug("HostResourceDispatchAction :: getHardwareHealthDetails() :: Exception occured for RESOURCEID :: " + resourceId + " :: Exception : " + ex.getMessage());
/*      */     }
/*      */     finally {
/*  651 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  653 */     AMLog.debug("Hardware Information: Action class: getHardwareHealthDetails RESOURCEID :: " + resourceId + " entityList: " + entityList);
/*  654 */     AMLog.debug("Hardware Information: Action class: getHardwareHealthDetails RESOURCEID :: " + resourceId + " devicesDetails: " + devicesDetails);
/*      */     
/*      */ 
/*  657 */     String severity = "-1";
/*  658 */     Map<String, String> hwDeviceHealthStatus = new HashMap();
/*  659 */     HashMap<String, HashMap<String, String>> statusFromTable = FaultUtil.getLatestEvent(entityList);
/*  660 */     for (int i = 0; i < entityList.size(); i++) {
/*  661 */       String entity = (String)entityList.get(i);
/*  662 */       Map<String, String> innerMap = (Map)statusFromTable.get(entity);
/*  663 */       if ((innerMap == null) || (innerMap.size() == 0)) {
/*  664 */         severity = "-1";
/*      */       } else {
/*  666 */         severity = (String)innerMap.get("SEVERITY");
/*  667 */         if ((severity == null) || (severity.length() == 0)) {
/*  668 */           severity = "-1";
/*      */         }
/*      */       }
/*  671 */       hwDeviceHealthStatus.put(entity, severity);
/*      */     }
/*  673 */     AMLog.debug("Hardware Information: Action class: getHardwareHealthDetails :: hwDeviceHealthStatus for RESOURCEID ::" + resourceId + " :: " + hwDeviceHealthStatus);
/*      */     
/*      */ 
/*  676 */     Map<String, String> hwDeviceUIInfos = new HashMap();
/*  677 */     hwDeviceUIInfos.put("Hw_Fan_image", "../images/Fansensor.png");
/*  678 */     hwDeviceUIInfos.put("Hw_PowerSupply_image", "../images/Powersensor.png");
/*  679 */     hwDeviceUIInfos.put("Hw_Temperature_image", "../images/Temperaturesensor.png");
/*  680 */     hwDeviceUIInfos.put("Hw_Cpu_image", "../images/Processorsensor.png");
/*  681 */     hwDeviceUIInfos.put("Hw_Disk_image", "../images/Disksensor.png");
/*  682 */     hwDeviceUIInfos.put("Hw_Array_image", "../images/Arraysensor.png");
/*  683 */     hwDeviceUIInfos.put("Hw_Chassis_image", "../images/Chassissensor.png");
/*  684 */     hwDeviceUIInfos.put("Hw_Memorydevice_image", "../images/Memorydevicesensor.gif");
/*  685 */     hwDeviceUIInfos.put("Hw_Voltage_image", "../images/Voltagesensor.gif");
/*  686 */     hwDeviceUIInfos.put("Hw_Battery_image", "../images/Batterysensor.gif");
/*      */     
/*  688 */     hwDeviceUIInfos.put("Hw_Fan_title", "am.webclient.server.hardware.sensor.fan");
/*  689 */     hwDeviceUIInfos.put("Hw_PowerSupply_title", "am.webclient.server.hardware.sensor.power");
/*  690 */     hwDeviceUIInfos.put("Hw_Temperature_title", "am.webclient.server.hardware.sensor.temperature");
/*  691 */     hwDeviceUIInfos.put("Hw_Cpu_title", "am.webclient.server.hardware.sensor.processor");
/*  692 */     hwDeviceUIInfos.put("Hw_Disk_title", "am.webclient.server.hardware.sensor.disk");
/*  693 */     hwDeviceUIInfos.put("Hw_Array_title", "am.webclient.server.hardware.sensor.array");
/*  694 */     hwDeviceUIInfos.put("Hw_Chassis_title", "am.webclient.server.hardware.sensor.chassis");
/*  695 */     hwDeviceUIInfos.put("Hw_Memorydevice_title", "am.webclient.server.hardware.sensor.memory");
/*  696 */     hwDeviceUIInfos.put("Hw_Voltage_title", "am.webclient.server.hardware.sensor.voltage");
/*  697 */     hwDeviceUIInfos.put("Hw_Battery_title", "am.webclient.server.hardware.sensor.battery");
/*      */     
/*  699 */     hwDeviceUIInfos.put("Hw_Fan_valuetitle", "am.webclient.server.hardware.sensor.fan.valuetitle");
/*  700 */     hwDeviceUIInfos.put("Hw_PowerSupply_valuetitle", "am.webclient.server.hardware.sensor.power.valuetitle");
/*  701 */     hwDeviceUIInfos.put("Hw_Temperature_valuetitle", "am.webclient.server.hardware.sensor.temperature.valuetitle");
/*  702 */     hwDeviceUIInfos.put("Hw_Disk_valuetitle", "am.webclient.server.hardware.sensor.disk.valuetitle");
/*  703 */     hwDeviceUIInfos.put("Hw_Array_valuetitle", "am.webclient.server.hardware.sensor.array.valuetitle");
/*  704 */     hwDeviceUIInfos.put("Hw_Memorydevice_valuetitle", "am.webclient.server.hardware.sensor.memory.valuetitle");
/*  705 */     hwDeviceUIInfos.put("Hw_Voltage_valuetitle", "am.webclient.server.hardware.sensor.voltage.valuetitle");
/*  706 */     hwDeviceUIInfos.put("Hw_Memorydevice_desctitle", "am.webclient.server.hardware.sensor.memory.desctitle");
/*  707 */     hwDeviceUIInfos.put("Hw_Disk_desctitle", "am.webclient.server.hardware.sensor.disk.desctitle");
/*  708 */     hwDeviceUIInfos.put("Hw_Array_desctitle", "am.webclient.server.hardware.sensor.array.desctitle");
/*      */     
/*  710 */     if ((manufacturer != 2) || (!mode.equalsIgnoreCase("WMI"))) {
/*  711 */       hwAttributeIDsList.put("Hw_Fan", "40502");
/*      */     }
/*  713 */     hwAttributeIDsList.put("Hw_PowerSupply", "40512");
/*  714 */     hwAttributeIDsList.put("Hw_Temperature", "40522");
/*  715 */     hwAttributeIDsList.put("Hw_Array", "40552");
/*  716 */     hwAttributeIDsList.put("Hw_Voltage", "40517");
/*      */     
/*  718 */     request.setAttribute("hwDeviceDetails", devicesDetails);
/*  719 */     request.setAttribute("hwDeviceHealthDetails", hwDeviceHealthStatus);
/*  720 */     request.setAttribute("hwDeviceUIInfos", hwDeviceUIInfos);
/*  721 */     request.setAttribute("hwDeviceOrderToDisplay", orderToDisplay);
/*  722 */     request.setAttribute("hwDeviceValues", hardwareValues);
/*  723 */     request.setAttribute("hwDeviceConfigValues", hardwareConfigDetails);
/*  724 */     request.setAttribute("hwAttributeIDsList", hwAttributeIDsList);
/*  725 */     return mapping.findForward("HwHealthDetailsPage");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap<String, String> getHardwareConfigValue(String resIDList, String attributeIDList)
/*      */   {
/*  735 */     HashMap<String, String> configAttrMap = new HashMap();
/*  736 */     String configvalue = "";
/*  737 */     String attributeID = "";
/*  738 */     String resourceID = "";
/*  739 */     ResultSet rs = null;
/*  740 */     String configquery = "SELECT RESOURCEID,ATTRIBUTEID,CONFVALUE FROM AM_HOST_CONFIG_INFO WHERE ATTRIBUTEID in (" + attributeIDList + ") AND RESOURCEID in (" + resIDList + ") AND LATEST=1";
/*      */     try {
/*  742 */       rs = AMConnectionPool.executeQueryStmt(configquery);
/*  743 */       while (rs.next()) {
/*  744 */         attributeID = String.valueOf(rs.getInt("ATTRIBUTEID"));
/*  745 */         configvalue = rs.getString("CONFVALUE");
/*  746 */         resourceID = String.valueOf(rs.getInt("RESOURCEID"));
/*  747 */         String configAttrUIName = (String)HardwareConstants.hardwareUIConfigNames.get(attributeID) + "-" + resourceID;
/*  748 */         if (configvalue != null) {
/*  749 */           configAttrMap.put(configAttrUIName, configvalue);
/*      */         }
/*      */         else {
/*  752 */           configAttrMap.put(configAttrUIName, "");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  757 */       AMLog.debug("HostResourceDispatchAction :: getHardwareConfigValue() :: Exception occured while executing query :: " + configquery);
/*  758 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  761 */       if (rs != null) {
/*  762 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/*  765 */     return configAttrMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private long getMaxCollectionTime(String resourceid)
/*      */   {
/*  774 */     ResultSet rs = null;
/*  775 */     long collectiontime = 0L;
/*  776 */     String query = "Select MAX(COLLECTIONTIME) from HostCpuMemDataCollected where RESOURCEID = (" + resourceid + ")";
/*      */     try {
/*  778 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  779 */       if (rs.next())
/*      */       {
/*  781 */         collectiontime = rs.getLong(1);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  785 */       AMLog.debug("HostResourceDispatchAction :: getHardwareHealthDetails :: getMaxCollectionTime :: Exception occured while getting max collection time for RESOURCEID:: " + resourceid + "+ Query :: " + query + " :: " + e.getMessage());
/*      */     }
/*      */     finally {
/*  788 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/*  790 */     return collectiontime;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap<String, String> getHardwareValuesfromDB(HashMap<String, String> hwvaluesResIDs, long collectionTime)
/*      */   {
/*  799 */     HashMap<String, String> hardwareValueMap = new HashMap();
/*  800 */     for (Map.Entry<String, String> entry : hwvaluesResIDs.entrySet()) {
/*  801 */       String hwType = (String)entry.getKey();
/*  802 */       String hwTable = hwType.toUpperCase();
/*  803 */       String resIDList = (String)entry.getValue();
/*  804 */       if (!resIDList.equals(""))
/*      */       {
/*      */ 
/*  807 */         resIDList = resIDList.substring(0, resIDList.lastIndexOf(","));
/*  808 */         ResultSet rs = null;
/*  809 */         String value = "";
/*  810 */         int resourceid = 0;
/*  811 */         String hardwarequery = "SELECT RESOURCEID,VALUE FROM AM_HARDWARE_" + hwTable + " WHERE COLLECTIONTIME = " + collectionTime + " AND RESOURCEID in (" + resIDList + ")";
/*      */         try {
/*  813 */           rs = AMConnectionPool.executeQueryStmt(hardwarequery);
/*  814 */           while (rs.next()) {
/*  815 */             resourceid = rs.getInt("RESOURCEID");
/*  816 */             value = rs.getString("VALUE");
/*  817 */             String hwValueUIName = "Hw_" + hwType + "-" + String.valueOf(resourceid);
/*  818 */             if (value != null) {
/*  819 */               hardwareValueMap.put(hwValueUIName, value);
/*      */             }
/*      */             else {
/*  822 */               hardwareValueMap.put(hwValueUIName, "");
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  828 */           AMLog.debug("HostResourceDispatchAction :: getHardwareValuefromDB() :: Exception occured while executing query :: " + hardwarequery);
/*  829 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/*  832 */           AMConnectionPool.closeResultSet(rs);
/*      */         }
/*      */       } }
/*  835 */     return hardwareValueMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap pullConfigDetails(String resourceIds)
/*      */   {
/*  845 */     HashMap<String, HashMap<String, ArrayList<String>>> confDetails = new HashMap();
/*  846 */     ResultSet rs = null;
/*      */     try {
/*  848 */       StringBuffer queryBuff = new StringBuffer();
/*  849 */       queryBuff.append("select A.RESOURCEID,A.ATTRIBUTEID,B.ATTRIBUTE,B.DISPLAYNAME,A.CONFVALUE,B.UNITS from AM_HOST_CONFIG_INFO A,AM_ATTRIBUTES B where A.ATTRIBUTEID=B.ATTRIBUTEID and A.LATEST=1 and A.RESOURCEID in (");
/*  850 */       queryBuff.append(resourceIds);
/*  851 */       queryBuff.append(")");
/*      */       
/*      */ 
/*      */ 
/*  855 */       queryBuff.append(" and A.ATTRIBUTEID != 10008");
/*  856 */       queryBuff.append(" order by DISPLAYNAME");
/*  857 */       rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/*  858 */       while (rs.next()) {
/*  859 */         String resId = rs.getString("RESOURCEID");
/*  860 */         String attrbName = rs.getString("ATTRIBUTE");
/*  861 */         HashMap<String, ArrayList<String>> h = (HashMap)confDetails.get(resId);
/*  862 */         if (h == null) {
/*  863 */           h = new HashMap();
/*  864 */           confDetails.put(resId, h);
/*      */         }
/*  866 */         ArrayList<String> al = (ArrayList)h.get(attrbName);
/*  867 */         if (al == null) {
/*  868 */           al = new ArrayList();
/*  869 */           h.put(attrbName, al);
/*      */         }
/*  871 */         al.add(rs.getString("ATTRIBUTEID"));
/*  872 */         al.add(rs.getString("DISPLAYNAME"));
/*  873 */         al.add(rs.getString("CONFVALUE"));
/*  874 */         al.add(rs.getString("UNITS"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  879 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  882 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  884 */     return confDetails;
/*      */   }
/*      */   
/*      */   public ArrayList<ArrayList<String>> getNetworkAdapter(String resourceid)
/*      */   {
/*  889 */     String adapterQuery = "SELECT RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE='NetAdapter'";
/*  890 */     netAdapter = new ArrayList();
/*  891 */     ArrayList<String> net_resid = new ArrayList();
/*  892 */     long collectiontime = 0L;
/*  893 */     String netid = null;
/*  894 */     ResultSet rs = null;
/*      */     try {
/*  896 */       rs = AMConnectionPool.executeQueryStmt(adapterQuery);
/*  897 */       while (rs.next())
/*      */       {
/*  899 */         net_resid.add(rs.getString("RESOURCEID"));
/*  900 */         if (netid == null)
/*      */         {
/*  902 */           netid = rs.getString("RESOURCEID");
/*      */         }
/*      */         else
/*      */         {
/*  906 */           netid = netid + "," + rs.getString("RESOURCEID");
/*      */         }
/*      */       }
/*  909 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  911 */       for (int j = 0; j < net_resid.size(); j++)
/*      */       {
/*  913 */         adapterQuery = "SELECT DISPLAYNAME,STATUS,NETENABLED FROM AM_NETWORKADAPTER,AM_ManagedObject WHERE ADAPTERID=" + (String)net_resid.get(j) + " and RESOURCEID=ADAPTERID and COLLECTIONTIME = (Select MAX(COLLECTIONTIME) from AM_NETWORKADAPTER where ADAPTERID in (" + netid + "))";
/*  914 */         rs = AMConnectionPool.executeQueryStmt(adapterQuery);
/*  915 */         if (rs.next())
/*      */         {
/*  917 */           ArrayList<String> networkAdapter = new ArrayList();
/*  918 */           networkAdapter.add(rs.getString("DISPLAYNAME"));
/*  919 */           networkAdapter.add(rs.getString("STATUS"));
/*  920 */           networkAdapter.add(rs.getString("NETENABLED"));
/*  921 */           networkAdapter.add(net_resid.get(j));
/*  922 */           netAdapter.add(networkAdapter);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  940 */       return netAdapter;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  928 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  934 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList<ArrayList<String>> getNetworkInterface(long resourceid)
/*      */   {
/*  945 */     String Interfacequery = "SELECT RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE='NetInterface'";
/*  946 */     netinterface = new ArrayList();
/*  947 */     ArrayList<String> net_resid = new ArrayList();
/*  948 */     long collectiontime = 0L;
/*  949 */     String netid = null;
/*  950 */     ResultSet rs = null;
/*      */     try {
/*  952 */       rs = AMConnectionPool.executeQueryStmt(Interfacequery);
/*  953 */       while (rs.next())
/*      */       {
/*  955 */         net_resid.add(rs.getString("RESOURCEID"));
/*  956 */         if (netid == null)
/*      */         {
/*  958 */           netid = rs.getString("RESOURCEID");
/*      */         }
/*      */         else
/*      */         {
/*  962 */           netid = netid + "," + rs.getString("RESOURCEID");
/*      */         }
/*      */       }
/*  965 */       if (netid != null)
/*      */       {
/*  967 */         String query = "Select MAX(COLLECTIONTIME) from AM_NETWORKINTERFACE where RESID in (" + netid + ")";
/*  968 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  969 */         if (rs.next())
/*      */         {
/*  971 */           collectiontime = rs.getLong(1);
/*      */         }
/*      */       }
/*  974 */       for (int j = 0; j < net_resid.size(); j++)
/*      */       {
/*  976 */         Interfacequery = "SELECT NAME,SPEED,BYTES_RX,BYTES_TX,ERROR_RX,ERROR_TX,RESID,STATUS,RX_UTILIZATION,TX_UTILIZATION FROM AM_NETWORKINTERFACE WHERE RESID=" + (String)net_resid.get(j) + " and COLLECTIONTIME=" + collectiontime;
/*      */         
/*  978 */         rs = AMConnectionPool.executeQueryStmt(Interfacequery);
/*  979 */         if (rs.next())
/*      */         {
/*  981 */           ArrayList<String> networkinterface = new ArrayList();
/*  982 */           networkinterface.add(rs.getString("NAME"));
/*  983 */           networkinterface.add(rs.getString("SPEED"));
/*  984 */           networkinterface.add(rs.getString("BYTES_RX"));
/*  985 */           networkinterface.add(rs.getString("BYTES_TX"));
/*  986 */           networkinterface.add(rs.getString("ERROR_RX"));
/*  987 */           networkinterface.add(rs.getString("ERROR_TX"));
/*  988 */           networkinterface.add(rs.getString("RESID"));
/*  989 */           networkinterface.add(rs.getString("STATUS"));
/*  990 */           networkinterface.add(rs.getFloat("RX_UTILIZATION") + "");
/*  991 */           networkinterface.add(rs.getFloat("TX_UTILIZATION") + "");
/*  992 */           netinterface.add(networkinterface);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1010 */       return netinterface;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  998 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1004 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap<String, String> getAttributesId(String resourceType)
/*      */   {
/* 1015 */     HashMap<String, String> ids = (HashMap)AMAttributesCache.getAllAttributeIds(resourceType);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1040 */     return ids;
/*      */   }
/*      */   
/*      */   public HashMap<String, HashMap<String, String>> getLatestMemoryData(String resourceId)
/*      */   {
/* 1045 */     ResultSet rs = null;
/* 1046 */     HashMap<String, HashMap<String, String>> latestVals = new HashMap();
/*      */     try {
/* 1048 */       StringBuffer queryBuff = new StringBuffer();
/* 1049 */       queryBuff.append("select PHYMEMUTILMB, PHYMEMUTIL, SWAPMEMUTILMB, SWAPMEMUTIL,FREEPHYMEMMB from HostCpuMemDataCollected where RESOURCEID=");
/* 1050 */       queryBuff.append(resourceId);
/* 1051 */       queryBuff.append(" order by COLLECTIONTIME desc");
/* 1052 */       String query = DBQueryUtil.getTopNValues(queryBuff.toString(), 1);
/* 1053 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1054 */       while (rs.next()) {
/* 1055 */         HashMap<String, String> hm = new HashMap();
/* 1056 */         if (rs.getString("PHYMEMUTILMB") != null) {
/* 1057 */           hm.put("PhysicalMemUtilization", "default");
/* 1058 */           hm.put("CURVALUEMB", rs.getString("PHYMEMUTILMB"));
/* 1059 */           hm.put("CURVALUE", rs.getString("PHYMEMUTIL"));
/* 1060 */           if (rs.getString("FREEPHYMEMMB") != null)
/*      */           {
/* 1062 */             hm.put("FREEPHYMEMMB", rs.getString("FREEPHYMEMMB"));
/*      */           }
/* 1064 */           latestVals.put("PhysicalMemUtilization", hm);
/*      */         }
/*      */         
/* 1067 */         hm = new HashMap();
/* 1068 */         if (rs.getString("SWAPMEMUTILMB") != null) {
/* 1069 */           hm.put("SwapMemUtilization", "default");
/* 1070 */           hm.put("CURVALUEMB", rs.getString("SWAPMEMUTILMB"));
/* 1071 */           hm.put("CURVALUE", rs.getString("SWAPMEMUTIL"));
/* 1072 */           latestVals.put("SwapMemUtilization", hm);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1077 */       ex.printStackTrace();
/*      */     } finally {
/* 1079 */       if (rs != null) {
/* 1080 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 1083 */     return latestVals;
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
/*      */   public ActionForward winServAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1096 */     return winServiceActionGUI(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ActionForward winServiceActionGUI(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1110 */       String applyTo = "1";
/* 1111 */       String actionId = request.getParameter("actionid");
/* 1112 */       if (request.isUserInRole("OPERATOR"))
/*      */       {
/* 1114 */         return mapping.findForward("accessRestricted");
/*      */       }
/*      */       
/*      */ 
/* 1118 */       if ((actionId != null) && (actionId.length() > 0))
/*      */       {
/* 1120 */         AMActionForm amf = (AMActionForm)form;
/* 1121 */         StringBuffer queryBuff = new StringBuffer();
/* 1122 */         queryBuff.append("select PROF.NAME,PROF.TYPE as ACTION,JRE.TYPE ,JRE.EMAIL_ACTION_ID from AM_ACTIONPROFILE PROF,AM_JREACTIONS JRE where PROF.ID=JRE.ID and PROF.ID=");
/* 1123 */         queryBuff.append(actionId);
/* 1124 */         ResultSet rs = null;
/*      */         try {
/* 1126 */           rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 1127 */           if (rs.next()) {
/* 1128 */             amf.setWinServiceActionName(rs.getString(1));
/* 1129 */             amf.setWinServActionTask(rs.getString(2));
/* 1130 */             applyTo = rs.getString(3);
/* 1131 */             amf.setWinServActionApplyTo(applyTo);
/* 1132 */             amf.setSendMail(rs.getString(4));
/*      */           }
/*      */         } catch (Exception ex) {
/* 1135 */           ex.printStackTrace();
/*      */         } finally {
/* 1137 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 1139 */         queryBuff.delete(0, queryBuff.length());
/*      */         
/*      */ 
/* 1142 */         Map<String, String[]> selectedServices = WinServiceActionUtil.getSelectedWinServices(actionId);
/* 1143 */         request.setAttribute("selectedservices", selectedServices);
/*      */         
/*      */ 
/* 1146 */         if (applyTo.equals("3")) {
/* 1147 */           request.setAttribute("selectedMonitor", getSelectedGroups(actionId));
/*      */         }
/* 1149 */         queryBuff = null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1154 */       request.setAttribute("servertypei18nkey", Constants.getServerTypei18nKeys());
/*      */       
/* 1156 */       if ((applyTo.equals("1")) || (applyTo.equals("3"))) {
/* 1157 */         List allserverlist = getAllServerList(null, request);
/* 1158 */         request.setAttribute("serverdetaillist", allserverlist);
/*      */       } else {
/* 1160 */         List allserverlist = getAllServerList(actionId, request);
/* 1161 */         request.setAttribute("serverdetaillist", allserverlist);
/*      */       }
/* 1163 */       Map groupdetail = MonitorGroupUtil.getMGDetails(request);
/* 1164 */       request.setAttribute("groupdetaillist", groupdetail);
/*      */       
/*      */ 
/* 1167 */       ResultSet set = null;
/* 1168 */       String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 1169 */       if (EnterpriseUtil.isAdminServer()) {
/* 1170 */         query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */       }
/*      */       try
/*      */       {
/* 1174 */         ArrayList<Properties> rows = new ArrayList();
/* 1175 */         set = AMConnectionPool.executeQueryStmt(query);
/* 1176 */         while (set.next())
/*      */         {
/* 1178 */           String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 1179 */           Properties dataProps = new Properties();
/* 1180 */           dataProps.setProperty("label", labelvalue);
/* 1181 */           dataProps.setProperty("value", set.getString(1));
/* 1182 */           rows.add(dataProps);
/*      */         }
/* 1184 */         ((AMActionForm)form).setMaillist(rows);
/*      */       } catch (Exception exp) {
/* 1186 */         exp.printStackTrace();
/*      */       } finally {
/* 1188 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */     } catch (Exception exc) {
/* 1191 */       exc.printStackTrace();
/*      */     }
/* 1193 */     return new ActionForward(".WinServiceAction");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward UpdateWinServiceAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1207 */       ResultSet rs = null;
/* 1208 */       boolean isActionExist = false;
/* 1209 */       ActionMessages messages = new ActionMessages();
/*      */       
/*      */ 
/*      */ 
/* 1213 */       int actionID = -1;
/* 1214 */       boolean editAction = false;
/* 1215 */       String strActionId = request.getParameter("actionid");
/* 1216 */       if ((strActionId != null) && (strActionId.length() > 0)) {
/* 1217 */         editAction = true;
/* 1218 */         actionID = Integer.parseInt(strActionId);
/*      */       }
/* 1220 */       String displayName = ((AMActionForm)form).getWinServiceActionName();
/* 1221 */       if (DBQueryUtil.getDBType().equals("mssql")) {
/* 1222 */         displayName = displayName.replaceAll("'", "''");
/*      */       } else {
/* 1224 */         displayName = displayName.replaceAll("'", "\\\\'");
/*      */       }
/* 1226 */       String checkQuery = "select * from AM_ACTIONPROFILE where NAME='" + displayName + "'";
/* 1227 */       if (editAction) {
/* 1228 */         checkQuery = "select * from AM_ACTIONPROFILE where NAME='" + displayName + "' and ID!=" + strActionId;
/*      */       }
/*      */       try {
/* 1231 */         rs = AMConnectionPool.executeQueryStmt(checkQuery);
/* 1232 */         if (rs.next()) {
/* 1233 */           isActionExist = true;
/*      */         }
/*      */       } catch (Exception ex) {
/* 1236 */         ex.printStackTrace();
/*      */       } finally {
/* 1238 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/* 1240 */       if (isActionExist)
/*      */       {
/* 1242 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.windowsservices.action.alreadyexists", displayName));
/* 1243 */         saveMessages(request, messages);
/* 1244 */         if (!editAction) {
/* 1245 */           return new ActionForward("/HostResourceDispatch.do?method=winServAction");
/*      */         }
/* 1247 */         return new ActionForward("/HostResourceDispatch.do?method=winServAction&actionid=" + actionID);
/*      */       }
/*      */       
/*      */ 
/* 1251 */       String action = ((AMActionForm)form).getWinServActionTask();
/* 1252 */       String applyTo = ((AMActionForm)form).getWinServActionApplyTo();
/* 1253 */       String sendEmail = ((AMActionForm)form).getSendMail();
/*      */       
/*      */ 
/* 1256 */       HashMap<String, String[]> selServicesForAction = new HashMap();
/* 1257 */       String[] selectedServices = request.getParameterValues("selServCheckbox");
/* 1258 */       for (int i = 0; i < selectedServices.length; i++) {
/* 1259 */         String serviceName = request.getParameter("servname_" + selectedServices[i]);
/* 1260 */         String serviceDispName = request.getParameter("servdisp_" + selectedServices[i]);
/* 1261 */         String[] array = { serviceName, serviceDispName };
/* 1262 */         selServicesForAction.put(selectedServices[i], array);
/*      */       }
/*      */       
/*      */ 
/* 1266 */       ArrayList<Integer> aListResourceIds = null;
/* 1267 */       if ((applyTo == null) || (applyTo.trim().length() == 0)) {
/* 1268 */         applyTo = "1";
/*      */       }
/* 1270 */       if (applyTo.equals("2"))
/*      */       {
/* 1272 */         String selServerTypeId = request.getParameter("winServerTypeList");
/* 1273 */         String[] selServers = request.getParameterValues(selServerTypeId + "_selected");
/* 1274 */         if ((selServers != null) && (selServers.length > 0)) {
/* 1275 */           aListResourceIds = new ArrayList();
/* 1276 */           for (int i = 0; i < selServers.length; i++) {
/* 1277 */             aListResourceIds.add(Integer.valueOf(Integer.parseInt(selServers[i])));
/*      */           }
/*      */         }
/* 1280 */       } else if (applyTo.equals("3")) {
/* 1281 */         String[] mgSelected = request.getParameterValues("select");
/* 1282 */         if ((mgSelected != null) && (mgSelected.length > 0)) {
/* 1283 */           aListResourceIds = new ArrayList();
/* 1284 */           for (int i = 0; i < mgSelected.length; i++) {
/* 1285 */             aListResourceIds.add(Integer.valueOf(Integer.parseInt(mgSelected[i])));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1291 */       StringBuffer queryBuff = new StringBuffer();
/* 1292 */       if (!editAction) {
/* 1293 */         actionID = DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE");
/* 1294 */         queryBuff.append("insert into AM_ACTIONPROFILE (ID,NAME,TYPE) values(");
/* 1295 */         queryBuff.append(actionID);
/* 1296 */         queryBuff.append(",");
/* 1297 */         queryBuff.append("'");
/* 1298 */         queryBuff.append(displayName);
/* 1299 */         queryBuff.append("'");
/* 1300 */         queryBuff.append(",");
/* 1301 */         queryBuff.append(action);
/* 1302 */         queryBuff.append(")");
/* 1303 */         AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/* 1304 */         queryBuff.delete(0, queryBuff.length());
/*      */       } else {
/* 1306 */         queryBuff.append("UPDATE AM_ACTIONPROFILE SET NAME=");
/* 1307 */         queryBuff.append("'");queryBuff.append(displayName);queryBuff.append("'");
/* 1308 */         queryBuff.append(",");
/* 1309 */         queryBuff.append("TYPE=");
/* 1310 */         queryBuff.append(action);
/* 1311 */         queryBuff.append(" WHERE ID=");
/* 1312 */         queryBuff.append(actionID);
/* 1313 */         AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/* 1314 */         queryBuff.delete(0, queryBuff.length());
/*      */       }
/*      */       
/*      */ 
/* 1318 */       if (!editAction) {
/* 1319 */         queryBuff.append("insert into  AM_JREACTIONS (ID,DELAY,COUNT,EMAIL_ACTION_ID,TYPE,TARGET_RESID) values (");
/* 1320 */         queryBuff.append(actionID);
/* 1321 */         queryBuff.append(",");
/* 1322 */         queryBuff.append(-1);
/* 1323 */         queryBuff.append(",");
/* 1324 */         queryBuff.append(-1);
/* 1325 */         queryBuff.append(",");
/* 1326 */         queryBuff.append(sendEmail);
/* 1327 */         queryBuff.append(",");
/* 1328 */         queryBuff.append(applyTo);
/* 1329 */         queryBuff.append(",");
/* 1330 */         queryBuff.append(-1);
/* 1331 */         queryBuff.append(")");
/* 1332 */         AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/* 1333 */         com.manageengine.appmanager.util.DelegatedUserRoleUtil.addEntryToConfigUserTable(request, actionID, 2);
/* 1334 */         queryBuff.delete(0, queryBuff.length());
/*      */       } else {
/* 1336 */         queryBuff.append("UPDATE AM_JREACTIONS SET EMAIL_ACTION_ID=");
/* 1337 */         queryBuff.append(sendEmail);
/* 1338 */         queryBuff.append(",");
/* 1339 */         queryBuff.append("TYPE=");
/* 1340 */         queryBuff.append(applyTo);
/* 1341 */         queryBuff.append(" WHERE ID=");
/* 1342 */         queryBuff.append(actionID);
/* 1343 */         AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/* 1344 */         queryBuff.delete(0, queryBuff.length());
/*      */       }
/*      */       
/*      */ 
/* 1348 */       PreparedStatement ps = null;
/* 1349 */       if ((applyTo.equals("2")) || (applyTo.equals("3"))) {
/* 1350 */         if (editAction)
/*      */         {
/* 1352 */           queryBuff.append("delete from AM_ACTION_ASSOCIATED_RESOURCES where ID=");
/* 1353 */           queryBuff.append(actionID);
/*      */           try {
/* 1355 */             AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/*      */           } catch (Exception ex) {
/* 1357 */             ex.printStackTrace();
/*      */           }
/* 1359 */           queryBuff.delete(0, queryBuff.length());
/*      */         }
/* 1361 */         if ((aListResourceIds != null) && (aListResourceIds.size() > 0)) {
/*      */           try {
/* 1363 */             ps = AMConnectionPool.getPreparedStatement("insert into  AM_ACTION_ASSOCIATED_RESOURCES (ID,RESOURCEID) values (?,?)");
/* 1364 */             for (int i = 0; i < aListResourceIds.size(); i++) {
/* 1365 */               ps.setInt(1, actionID);
/* 1366 */               ps.setInt(2, ((Integer)aListResourceIds.get(i)).intValue());
/* 1367 */               ps.addBatch();
/*      */             }
/* 1369 */             ps.executeBatch();
/*      */             
/*      */ 
/*      */             try
/*      */             {
/* 1374 */               if (ps != null) {
/* 1375 */                 ps.close();
/* 1376 */                 ps = null;
/*      */               }
/*      */             } catch (Exception ex1) {
/* 1379 */               ex1.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/* 1387 */               if (editAction)
/*      */               {
/* 1389 */                 queryBuff.append("delete from AM_WINSERVICEACTION_SERVICE_SELECTED where ID=");
/* 1390 */                 queryBuff.append(actionID);
/*      */                 try {
/* 1392 */                   AMConnectionPool.executeUpdateStmt(queryBuff.toString());
/*      */                 } catch (Exception ex) {
/* 1394 */                   ex.printStackTrace();
/*      */                 }
/* 1396 */                 queryBuff.delete(0, queryBuff.length());
/*      */               }
/* 1398 */               ps = AMConnectionPool.getPreparedStatement("insert into  AM_WINSERVICEACTION_SERVICE_SELECTED (ID,SERVICE_NAME,SERVICE_DISPNAME) values (?,?,?)");
/* 1399 */               Iterator<String> itr = selServicesForAction.keySet().iterator();
/* 1400 */               while (itr.hasNext()) {
/* 1401 */                 String[] service = (String[])selServicesForAction.get(itr.next());
/* 1402 */                 ps.setInt(1, actionID);
/* 1403 */                 ps.setString(2, service[0]);
/* 1404 */                 ps.setString(3, service[1]);
/* 1405 */                 ps.addBatch();
/*      */               }
/* 1407 */               ps.executeBatch();
/*      */               
/*      */ 
/*      */               try
/*      */               {
/* 1412 */                 if (ps != null) {
/* 1413 */                   ps.close();
/* 1414 */                   ps = null;
/*      */                 }
/*      */               } catch (Exception ex1) {
/* 1417 */                 ex1.printStackTrace();
/*      */               }
/*      */               
/* 1420 */               if (!editAction) {
/*      */                 break label1644;
/*      */               }
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 1409 */               ex.printStackTrace();
/*      */             }
/*      */             finally {}
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1371 */             ex.printStackTrace();
/*      */           } finally {
/*      */             try {
/* 1374 */               if (ps != null) {
/* 1375 */                 ps.close();
/* 1376 */                 ps = null;
/*      */               }
/*      */             } catch (Exception ex1) {
/* 1379 */               ex1.printStackTrace();
/*      */             }
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1421 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.windowsservices.action.update.success.text", displayName));
/* 1422 */       saveMessages(request, messages);
/*      */       break label1671;
/*      */       label1644:
/* 1425 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.windowsservices.action.add.success.text", displayName));
/* 1426 */       saveMessages(request, messages);
/*      */     }
/*      */     catch (Exception ex) {
/*      */       label1671:
/* 1430 */       ex.printStackTrace();
/*      */     }
/* 1432 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
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
/*      */   public ActionForward delWindowsServiceAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1446 */     String[] actions = request.getParameterValues("winServActionCheckbox");
/* 1447 */     if (actions != null)
/*      */     {
/* 1449 */       FaultUtil.delWindowsServiceAction(actions);
/*      */     }
/* 1451 */     ActionMessages messages = new ActionMessages();
/* 1452 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.windowsservices.action.delete.message.text"));
/* 1453 */     saveMessages(request, messages);
/* 1454 */     return new ActionForward("/adminAction.do?method=showActionProfiles");
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
/*      */   public ActionForward getServicesFromTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1467 */     ResultSet rs = null;
/* 1468 */     ArrayList<ArrayList<String>> allServiceDetails = new ArrayList();
/* 1469 */     Map<String, String> templateDetails = new LinkedHashMap();
/* 1470 */     Map<String, ArrayList<ArrayList<String>>> templateServiceDetails = new LinkedHashMap();
/* 1471 */     templateDetails.put("all", "All");
/* 1472 */     templateServiceDetails.put("all", allServiceDetails);
/*      */     
/* 1474 */     boolean isAnyTemplateCreated = false;
/* 1475 */     String query = "select apt.TEMPLATEID,apt.TEMPLATENAME,atpd.TEMPLATEPROCESSID,atpd.PROCESSNAME,atpd.PROCESSCMD from am_process_template apt,AM_TEMPLATE_PROCESS_DEFINITION atpd where apt.TEMPLATEID=atpd.TEMPLATEID and apt.TEMPLATETYPE=1 order by apt.TEMPLATENAME,atpd.PROCESSNAME asc";
/*      */     try {
/* 1477 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1478 */       while (rs.next())
/*      */       {
/* 1480 */         String tid = rs.getString(1);
/* 1481 */         templateDetails.put(tid, rs.getString(2));
/*      */         
/*      */ 
/* 1484 */         ArrayList<ArrayList<String>> innerList = (ArrayList)templateServiceDetails.get(tid);
/* 1485 */         if (innerList == null) {
/* 1486 */           innerList = new ArrayList();
/* 1487 */           templateServiceDetails.put(tid, innerList);
/*      */         }
/* 1489 */         ArrayList<String> al = new ArrayList();
/* 1490 */         al.add(rs.getString(3));
/* 1491 */         al.add(rs.getString(4));
/* 1492 */         al.add(rs.getString(5));
/* 1493 */         innerList.add(al);
/*      */         
/*      */ 
/* 1496 */         allServiceDetails.add(al);
/* 1497 */         if (!isAnyTemplateCreated) {
/* 1498 */           isAnyTemplateCreated = true;
/*      */         }
/*      */       }
/*      */     } catch (Exception ex) {
/* 1502 */       ex.printStackTrace();
/*      */     } finally {
/* 1504 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1506 */     if (isAnyTemplateCreated) {
/* 1507 */       request.setAttribute("template_details", templateDetails);
/* 1508 */       request.setAttribute("template_service_details", templateServiceDetails);
/*      */     }
/* 1510 */     return mapping.findForward("TemplateServiceDetails");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public HashMap<String, String> getAllServerTypeList()
/*      */   {
/* 1519 */     HashMap<String, String> servertypemap = new LinkedHashMap();
/* 1520 */     String servertypeqry = "select DISPLAYNAME, NULL from AM_ManagedResourceType where SUBGROUP='Windows' and RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') order by DISPLAYNAME";
/* 1521 */     ResultSet servertypeset = null;
/*      */     try {
/* 1523 */       servertypeset = AMConnectionPool.executeQueryStmt(servertypeqry);
/* 1524 */       while (servertypeset.next()) {
/* 1525 */         String availabletype = servertypeset.getString(1);
/* 1526 */         String selectedtype = servertypeset.getString(2);
/* 1527 */         servertypemap.put(availabletype, selectedtype);
/*      */       }
/*      */     } catch (Exception exc) {
/* 1530 */       exc.printStackTrace();
/*      */     } finally {
/* 1532 */       AMConnectionPool.closeStatement(servertypeset);
/*      */     }
/* 1534 */     return servertypemap;
/*      */   }
/*      */   
/*      */   public List getAllServerList(String actionId, HttpServletRequest request)
/*      */   {
/* 1539 */     ResultSet serverResult = null;
/* 1540 */     Map<String, Properties> availableserverlist = new LinkedHashMap();
/* 1541 */     Map<String, Properties> selectedserverlist = new LinkedHashMap();
/*      */     try {
/* 1543 */       Properties allserverlist = new Properties();
/* 1544 */       String privilegeCondition = "";
/* 1545 */       boolean isUserResourceEnabled = false;
/* 1546 */       String loginUserid = null;
/* 1547 */       if ((request != null) && (ClientDBUtil.isPrivilegedUser(request))) {
/* 1548 */         if (Constants.isUserResourceEnabled()) {
/* 1549 */           isUserResourceEnabled = true;
/* 1550 */           loginUserid = Constants.getLoginUserid(request);
/*      */         } else {
/* 1552 */           Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 1553 */           privilegeCondition = " and " + EnterpriseUtil.getCondition("amo.RESOURCEID", resids);
/*      */         }
/*      */       }
/*      */       
/* 1557 */       availableserverlist.put("All-Monitors", allserverlist);
/* 1558 */       String allserverquery = "select RESOURCETYPE,ami.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_ManagedObject amo, AM_ManagedResourceType amrt,AM_HOSTINFO ami where ami.RESOURCEID=amo.RESOURCEID and amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP='Windows'  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ami.MODE='WMI'" + privilegeCondition;
/* 1559 */       if (isUserResourceEnabled) {
/* 1560 */         allserverquery = "select RESOURCETYPE,ami.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_USERRESOURCESTABLE, AM_ManagedObject amo, AM_ManagedResourceType amrt,AM_HOSTINFO ami where amo.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID = " + loginUserid + " and ami.RESOURCEID=amo.RESOURCEID and amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP='Windows'  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ami.MODE='WMI'" + privilegeCondition;
/*      */       }
/* 1562 */       if ((actionId != null) && (actionId.length() > 0))
/*      */       {
/* 1564 */         allserverquery = "select DISTINCT RESOURCETYPE,amo.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_ManagedObject amo LEFT OUTER JOIN AM_ACTION_ASSOCIATED_RESOURCES awr on awr.RESOURCEID=amo.RESOURCEID " + privilegeCondition + " and awr.ID=" + actionId + " JOIN AM_ManagedResourceType amrt ON amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP='Windows'  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and awr.RESOURCEID IS NULL JOIN AM_HOSTINFO ami ON ami.RESOURCEID=amo.RESOURCEID where ami.MODE='WMI'";
/* 1565 */         if (isUserResourceEnabled) {
/* 1566 */           allserverquery = "select DISTINCT RESOURCETYPE,amo.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_ManagedObject amo LEFT OUTER JOIN AM_ACTION_ASSOCIATED_RESOURCES awr on awr.RESOURCEID=amo.RESOURCEID  and awr.ID=" + actionId + " JOIN AM_ManagedResourceType amrt ON amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP='Windows'  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and awr.RESOURCEID IS NULL JOIN AM_HOSTINFO ami ON ami.RESOURCEID=amo.RESOURCEID join AM_USERRESOURCESTABLE on amo.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " where ami.MODE='WMI'";
/*      */         }
/*      */       }
/* 1569 */       serverResult = AMConnectionPool.executeQueryStmt(allserverquery);
/* 1570 */       while (serverResult.next()) {
/* 1571 */         String subgroup = serverResult.getString("RESOURCETYPE");
/* 1572 */         String resid = serverResult.getString("RESOURCEID");
/* 1573 */         String displayname = serverResult.getString("DISPNAME");
/* 1574 */         Properties serverdetail = (Properties)availableserverlist.get(subgroup);
/* 1575 */         if (serverdetail == null) {
/* 1576 */           serverdetail = new Properties();
/* 1577 */           availableserverlist.put(subgroup, serverdetail);
/*      */         }
/* 1579 */         serverdetail.setProperty(resid, displayname);
/* 1580 */         allserverlist.setProperty(resid, displayname);
/*      */       }
/*      */     } catch (Exception exc) {
/* 1583 */       exc.printStackTrace();
/*      */     } finally {
/* 1585 */       AMConnectionPool.closeStatement(serverResult);
/*      */     }
/*      */     
/* 1588 */     if ((actionId != null) && (actionId.length() > 0)) {
/*      */       try {
/* 1590 */         Properties allserverlist = new Properties();
/* 1591 */         selectedserverlist.put("All-Monitors", allserverlist);
/* 1592 */         String selectedserverquery = "select DISTINCT awr.RESOURCEID,amo.DISPLAYNAME,RESOURCETYPE from AM_ACTION_ASSOCIATED_RESOURCES awr ,AM_ManagedObject amo, AM_ManagedResourceType amrt where amrt.RESOURCETYPE=amo.TYPE and awr.RESOURCEID=amo.RESOURCEID and awr.ID=" + actionId;
/* 1593 */         serverResult = AMConnectionPool.executeQueryStmt(selectedserverquery);
/* 1594 */         while (serverResult.next()) {
/* 1595 */           String servertype = serverResult.getString(3);
/* 1596 */           Properties selecteddetails = (Properties)selectedserverlist.get(servertype);
/* 1597 */           if (selecteddetails == null) {
/* 1598 */             selecteddetails = new Properties();
/* 1599 */             selectedserverlist.put(servertype, selecteddetails);
/*      */           }
/* 1601 */           selecteddetails.setProperty(serverResult.getString(1), serverResult.getString(2));
/* 1602 */           allserverlist.setProperty(serverResult.getString(1), serverResult.getString(2));
/* 1603 */           if (availableserverlist.get(servertype) == null) {
/* 1604 */             availableserverlist.put(servertype, new Properties());
/*      */           }
/*      */         }
/*      */       } catch (Exception exc) {
/* 1608 */         exc.printStackTrace();
/*      */       } finally {
/* 1610 */         AMConnectionPool.closeStatement(serverResult);
/*      */       }
/*      */     }
/* 1613 */     List returnlist = new ArrayList();
/* 1614 */     returnlist.add(availableserverlist);
/* 1615 */     returnlist.add(selectedserverlist);
/* 1616 */     return returnlist;
/*      */   }
/*      */   
/*      */   public List getAllServerList(String actionId)
/*      */   {
/* 1621 */     return getAllServerList(actionId, null);
/*      */   }
/*      */   
/*      */   public List<Properties> getSelectedGroups(String actionId)
/*      */   {
/* 1626 */     List<Properties> selectedgrouplist = new ArrayList();
/* 1627 */     String mgselectedquery = "select awr.RESOURCEID,amo.RESOURCENAME from AM_ACTION_ASSOCIATED_RESOURCES awr,AM_ManagedObject amo where awr.RESOURCEID=amo.RESOURCEID and awr.ID=" + actionId;
/* 1628 */     ResultSet mgselectedset = null;
/*      */     try {
/* 1630 */       mgselectedset = AMConnectionPool.executeQueryStmt(mgselectedquery);
/* 1631 */       while (mgselectedset.next()) {
/* 1632 */         Properties mgnameidprop = new Properties();
/* 1633 */         mgnameidprop.setProperty("label", mgselectedset.getString(2));
/* 1634 */         mgnameidprop.setProperty("value", mgselectedset.getString(1));
/* 1635 */         selectedgrouplist.add(mgnameidprop);
/*      */       }
/*      */     } catch (Exception exc) {
/* 1638 */       exc.printStackTrace();
/*      */     } finally {
/* 1640 */       AMConnectionPool.closeStatement(mgselectedset);
/*      */     }
/* 1642 */     return selectedgrouplist;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showWinServActDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1655 */       ResultSet rs = null;
/* 1656 */       String applyTo = "";
/* 1657 */       String actionId = request.getParameter("actionid");
/* 1658 */       if ((actionId != null) && (actionId.length() > 0))
/*      */       {
/* 1660 */         StringBuffer queryBuff = new StringBuffer();
/* 1661 */         queryBuff.append("select PROF.NAME,PROF.TYPE as ACTION,JRE.TYPE from AM_ACTIONPROFILE PROF,AM_JREACTIONS JRE where PROF.ID=");
/* 1662 */         queryBuff.append("JRE.ID");
/* 1663 */         queryBuff.append(" and ");
/* 1664 */         queryBuff.append("PROF.ID=");
/* 1665 */         queryBuff.append(actionId);
/*      */         try {
/* 1667 */           rs = AMConnectionPool.executeQueryStmt(queryBuff.toString());
/* 1668 */           if (rs.next()) {
/* 1669 */             applyTo = rs.getString(3);
/* 1670 */             request.setAttribute("actionname", rs.getString(1));
/* 1671 */             request.setAttribute("actiontype", rs.getString(2));
/* 1672 */             request.setAttribute("applyactionto", applyTo);
/*      */           }
/*      */         } catch (Exception ex) {
/* 1675 */           ex.printStackTrace();
/*      */         } finally {
/* 1677 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 1679 */         queryBuff.delete(0, queryBuff.length());
/*      */         
/*      */ 
/* 1682 */         Map<String, String[]> selectedServices = WinServiceActionUtil.getSelectedWinServices(actionId);
/* 1683 */         if ((applyTo == null) || (applyTo.length() == 0)) {
/* 1684 */           applyTo = "1";
/*      */         }
/* 1686 */         if ((selectedServices != null) && (selectedServices.size() > 0)) {
/* 1687 */           request.setAttribute("selectedservices", selectedServices);
/* 1688 */           request.setAttribute("selectedservices_size", Integer.valueOf(selectedServices.size()));
/*      */         }
/*      */         
/*      */ 
/* 1692 */         Object resources = WinServiceActionUtil.getSelectedResources(actionId, applyTo);
/* 1693 */         if ((resources != null) && (((Map)resources).size() > 0)) {
/* 1694 */           request.setAttribute("selectedservers", resources);
/* 1695 */           request.setAttribute("selectedservers_size", Integer.valueOf(((Map)resources).size()));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1700 */       ex.printStackTrace();
/*      */     }
/* 1702 */     return mapping.findForward("testWinServiceAction");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward manualExecution(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1714 */     return manualExecWinServiceAction(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward manualExecWinServiceAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try {
/* 1720 */       String actionId = request.getParameter("actionid");
/* 1721 */       String actionName = request.getParameter("actionname");
/* 1722 */       String execute = request.getParameter("execWinServiceAction");
/* 1723 */       int actionID = Integer.parseInt(actionId);
/* 1724 */       Hashtable<String, String> result = getExecWinServiceActionResp(actionID + "", actionName, execute);
/* 1725 */       String message = (String)result.get("message");
/* 1726 */       String success = (String)result.get("success");
/* 1727 */       request.setAttribute("message", message);
/* 1728 */       request.setAttribute("success", success);
/* 1729 */       if (request.getParameter("remote") != null)
/*      */       {
/* 1731 */         AdminTools.sendResponse(actionId, success, message, response);
/* 1732 */         return null;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1737 */       ex.printStackTrace();
/*      */     }
/* 1739 */     return mapping.findForward("testWinServiceAction");
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getExecWinServiceActionResp(String actionID, String actionName, String execute)
/*      */   {
/* 1744 */     Hashtable<String, String> toReturn = new Hashtable();
/*      */     try
/*      */     {
/* 1747 */       int actionId = Integer.parseInt(actionID);
/* 1748 */       int status = 0;
/* 1749 */       boolean isSuccess = false;
/* 1750 */       String message = "";
/* 1751 */       execute = (execute == null) || (execute.length() == 0) ? "0" : execute;
/*      */       
/* 1753 */       if (execute.equals("0"))
/*      */       {
/* 1755 */         status = WinServiceActionUtil.sendTestMail(actionId);
/*      */       }
/*      */       else
/*      */       {
/* 1759 */         status = WinServiceActionUtil.triggerWinServiceAction(actionId, null);
/*      */       }
/*      */       
/* 1762 */       if (status == 1)
/*      */       {
/* 1764 */         message = FormatUtil.getString("am.webclient.testaction.success", new String[] { actionName });
/* 1765 */         isSuccess = true;
/*      */       }
/* 1767 */       else if (status == 2)
/*      */       {
/*      */ 
/* 1770 */         message = FormatUtil.getString("am.windowsservices.action.email.noresource.text");
/*      */       }
/* 1772 */       else if (status == 3)
/*      */       {
/*      */ 
/* 1775 */         message = FormatUtil.getString("am.windowsservices.action.email.noresponse.text");
/*      */       }
/*      */       else
/*      */       {
/* 1779 */         message = FormatUtil.getString("am.webclient.testaction.failed", new String[] { actionName });
/*      */       }
/*      */       
/* 1782 */       toReturn.put("success", isSuccess + "");
/* 1783 */       toReturn.put("message", message);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1787 */       e.printStackTrace();
/*      */     }
/* 1789 */     return toReturn;
/*      */   }
/*      */   
/*      */   public String customizeUptimeVal(String value) {
/* 1793 */     if ((value == null) || (value.equals("null")) || (value.trim().length() == 0)) {
/* 1794 */       return "-";
/*      */     }
/* 1796 */     StringBuffer result = new StringBuffer();
/* 1797 */     StringTokenizer tokens = new StringTokenizer(value, ":");
/* 1798 */     if (tokens.countTokens() == 4) {
/* 1799 */       String tok = tokens.nextToken();
/* 1800 */       if (!tok.equals("0")) {
/* 1801 */         result.append(tok);
/* 1802 */         result.append(" ");
/* 1803 */         result.append(FormatUtil.getString("am.webclient.server.config.days"));
/* 1804 */         result.append(", ");
/*      */       }
/* 1806 */       tok = tokens.nextToken();
/* 1807 */       if (!tok.equals("0")) {
/* 1808 */         result.append(tok);
/* 1809 */         result.append(" ");
/* 1810 */         result.append(FormatUtil.getString("am.webclient.server.config.hours"));
/* 1811 */         result.append(", ");
/*      */       }
/* 1813 */       tok = tokens.nextToken();
/* 1814 */       if (!tok.equals("0")) {
/* 1815 */         result.append(tok);
/* 1816 */         result.append(" ");
/* 1817 */         result.append(FormatUtil.getString("am.webclient.server.config.minutes"));
/* 1818 */         result.append(", ");
/*      */       }
/* 1820 */       tok = tokens.nextToken();
/* 1821 */       if (!tok.equals("0")) {
/* 1822 */         result.append(tok);
/* 1823 */         result.append(" ");
/* 1824 */         result.append(FormatUtil.getString("am.webclient.server.config.seconds"));
/*      */       }
/* 1826 */       tok = result.toString().trim();
/* 1827 */       int idx = tok.lastIndexOf(",");
/* 1828 */       if (idx == tok.length() - 1) {
/* 1829 */         tok = tok.substring(0, tok.length() - 1);
/*      */       }
/* 1831 */       return tok;
/*      */     }
/* 1833 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showHardwareConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1839 */     String resourceid = request.getParameter("resourceid");
/* 1840 */     AMActionForm amform = (AMActionForm)form;
/* 1841 */     String hardware = null;
/* 1842 */     request.setAttribute("resourceid", resourceid);
/*      */     try
/*      */     {
/* 1845 */       if ("disable".equalsIgnoreCase(DBUtil.getGlobalConfigValue("am.server.hardware.health.monitoring"))) {
/* 1846 */         request.setAttribute("disable", "true");
/* 1847 */         return mapping.findForward("HwConfigurationPage");
/*      */       }
/*      */     } catch (Exception e1) {
/* 1850 */       e1.printStackTrace();
/*      */     }
/*      */     
/* 1853 */     ResultSet hardwareSet = null;
/*      */     try {
/* 1855 */       hardwareSet = AMConnectionPool.executeQueryStmt("select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID=" + resourceid);
/*      */       
/* 1857 */       if (hardwareSet.next()) {
/* 1858 */         hardware = hardwareSet.getString("COMPONENTNAME");
/*      */       }
/*      */     } catch (SQLException e) {
/* 1861 */       e.printStackTrace();
/*      */     } finally {
/* 1863 */       AMConnectionPool.closeStatement(hardwareSet);
/*      */     }
/*      */     
/* 1866 */     if ((hardware == null) || (hardware.trim().equals(""))) {
/*      */       try {
/* 1868 */         hardware = DBUtil.getGlobalConfigValue("am.server.enable.hardware");
/*      */       } catch (Exception e) {
/* 1870 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 1874 */     AMLog.debug("Hardware : Show Hardware : " + hardware);
/* 1875 */     if ((hardware != null) && (hardware.indexOf("Array") != -1)) {
/* 1876 */       amform.setArray(true);
/*      */     }
/* 1878 */     if ((hardware != null) && (hardware.indexOf("Temperature") != -1)) {
/* 1879 */       amform.setTemperature(true);
/*      */     }
/* 1881 */     if ((hardware != null) && (hardware.indexOf("PowerSupply") != -1)) {
/* 1882 */       amform.setPower(true);
/*      */     }
/* 1884 */     if ((hardware != null) && (hardware.indexOf("Fan") != -1)) {
/* 1885 */       amform.setFan(true);
/*      */     }
/* 1887 */     if ((hardware != null) && (hardware.indexOf("Cpu") != -1)) {
/* 1888 */       amform.setProcessor(true);
/*      */     }
/* 1890 */     if ((hardware != null) && (hardware.indexOf("Disk") != -1)) {
/* 1891 */       amform.setDisk(true);
/*      */     }
/* 1893 */     if ((hardware != null) && (hardware.indexOf("Chassis") != -1)) {
/* 1894 */       amform.setChassis(true);
/*      */     }
/* 1896 */     if ((hardware != null) && (hardware.indexOf("Memorydevice") != -1)) {
/* 1897 */       amform.setMemorydevice(true);
/*      */     }
/* 1899 */     if ((hardware != null) && (hardware.indexOf("Voltage") != -1)) {
/* 1900 */       amform.setVoltage(true);
/*      */     }
/* 1902 */     if ((hardware != null) && (hardware.indexOf("Battery") != -1)) {
/* 1903 */       amform.setBattery(true);
/*      */     }
/* 1905 */     String message = (String)request.getAttribute("message");
/* 1906 */     if (message != null) {
/* 1907 */       request.setAttribute("message", message);
/*      */     }
/*      */     
/* 1910 */     return mapping.findForward("HwConfigurationPage");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward saveHardwareConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1916 */     String resourceid = request.getParameter("resourceid");
/* 1917 */     AMActionForm amform = (AMActionForm)form;
/* 1918 */     request.setAttribute("resourceid", resourceid);
/*      */     
/* 1920 */     String hardware = "";
/* 1921 */     if (amform.isTemperature()) {
/* 1922 */       hardware = hardware + "Temperature";
/*      */     }
/* 1924 */     if (amform.isFan()) {
/* 1925 */       hardware = hardware + "|Fan";
/*      */     }
/* 1927 */     if (amform.isProcessor()) {
/* 1928 */       hardware = hardware + "|Cpu";
/*      */     }
/* 1930 */     if (amform.isDisk()) {
/* 1931 */       hardware = hardware + "|Disk";
/*      */     }
/* 1933 */     if (amform.isArray()) {
/* 1934 */       hardware = hardware + "|Array";
/*      */     }
/* 1936 */     if (amform.isChassis()) {
/* 1937 */       hardware = hardware + "|Chassis";
/*      */     }
/* 1939 */     if (amform.isPower()) {
/* 1940 */       hardware = hardware + "|PowerSupply";
/*      */     }
/* 1942 */     if (amform.isMemorydevice()) {
/* 1943 */       hardware = hardware + "|Memorydevice";
/*      */     }
/* 1945 */     if (amform.isVoltage()) {
/* 1946 */       hardware = hardware + "|Voltage";
/*      */     }
/* 1948 */     if (amform.isBattery()) {
/* 1949 */       hardware = hardware + "|Battery";
/*      */     }
/*      */     
/* 1952 */     if ("".equals(hardware)) {
/* 1953 */       hardware = "Hw:Disabled";
/*      */     }
/*      */     
/* 1956 */     String[] components = "Temperature#Fan#Cpu#Disk#Array#Chassis#PowerSupply#Memorydevice#Voltage#Battery".split("#");
/* 1957 */     for (String component : components) {
/*      */       try {
/* 1959 */         if (!hardware.contains(component)) {
/* 1960 */           String[] attributes = { "Hw_" + component + "_Status", "Health", "Hw_" + component + "_Value" };
/* 1961 */           String attributeId; for (String attribute : attributes) {
/* 1962 */             attributeId = HardwareConstants.getAttributeId("Hw_" + component + "_" + attribute);
/* 1963 */             ArrayList<String> componentResourceids = getComponentResourceid(resourceid, "Hw_" + component);
/* 1964 */             for (String componentResourceid : componentResourceids) {
/* 1965 */               String entity = componentResourceid + "_" + attributeId;
/*      */               try {
/* 1967 */                 FaultUtil.deleteAlertsForEntity(entity);
/*      */               } catch (Exception e) {
/* 1969 */                 e.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/* 1975 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 1980 */       DataCollectionComponent.enableselectedComponent(resourceid, hardware);
/*      */     } catch (Exception se) {
/* 1982 */       se.printStackTrace();
/*      */     }
/* 1984 */     request.setAttribute("message", FormatUtil.getString("am.webclient.dbretention.sucess.text"));
/* 1985 */     return mapping.findForward("HwConfigurationPage");
/*      */   }
/*      */   
/*      */   private ArrayList<String> getComponentResourceid(String resourceid, String component) throws SQLException {
/* 1989 */     ArrayList<String> toReturn = new ArrayList();
/* 1990 */     ResultSet result = null;
/*      */     try {
/* 1992 */       String query = "select AM_ManagedObject.resourceid from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.parentid=" + resourceid + " and AM_PARENTCHILDMAPPER.childid=AM_ManagedObject.resourceid and AM_ManagedObject.type='" + component + "'";
/* 1993 */       AMLog.audit("Hardware : Delete resource query :" + query);
/* 1994 */       result = AMConnectionPool.executeQueryStmt(query);
/* 1995 */       while (result.next()) {
/* 1996 */         toReturn.add(result.getInt(1) + "");
/*      */       }
/*      */     } finally {
/* 1999 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 2001 */     return toReturn;
/*      */   }
/*      */   
/*      */   public ActionForward getMonitorsInSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2006 */     return mapping.findForward("monitorsInSystemDetailsPage");
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\hostresource\struts\HostResourceDispatchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */