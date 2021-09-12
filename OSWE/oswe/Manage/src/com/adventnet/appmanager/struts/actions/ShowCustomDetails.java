/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.db.RepairTables;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorConstants;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.datacorrection.AMDataCorrectionUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.util.AppManagerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DifferentialPollingUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.utils.client.HistoryDataAPIUtil;
/*      */ import com.adventnet.nms.topodb.TopoAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.servlet.RequestDispatcher;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ShowCustomDetails
/*      */   extends DispatchAction
/*      */ {
/*      */   private static final String SUCCESS = "success";
/*   78 */   private static ShowCustomDetails customDetails = null;
/*   79 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ShowCustomDetails getInstance()
/*      */   {
/*   88 */     if (customDetails == null)
/*      */     {
/*   90 */       customDetails = new ShowCustomDetails();
/*      */     }
/*   92 */     return customDetails;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   99 */     String baseid = (String)request.getAttribute("baseid");
/*  100 */     String amcreated = (String)request.getAttribute("amcreated");
/*  101 */     String resourcetype = request.getParameter("type");
/*  102 */     if ((amcreated != null) && (amcreated.equals("YES")) && (!resourcetype.equals("QueryMonitor"))) {
/*  103 */       return showDataforConfs(mapping, form, request, response);
/*      */     }
/*      */     
/*  106 */     return showDataforScripts(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getMonitorName(String resourceid)
/*      */   {
/*  113 */     ArrayList rows = this.mo.getRows("select displayname from AM_ManagedObject where resourceid=" + resourceid);
/*  114 */     if (rows.size() > 0)
/*      */     {
/*  116 */       rows = (ArrayList)rows.get(0);
/*  117 */       return (String)rows.get(0);
/*      */     }
/*  119 */     return "";
/*      */   }
/*      */   
/*      */   private Properties getAPICustomConfigDetails(String resID)
/*      */   {
/*  124 */     Properties apiConfig = new Properties();
/*  125 */     PreparedStatement ps = null;
/*  126 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  129 */       ps = AMConnectionPool.getPreparedStatement("select RESPONSE,XSLTINPUT,JSONSCHEMA from AM_RESTAPI_DETAILS where RESOURCEID=?");
/*  130 */       ps.setInt(1, Integer.parseInt(resID));
/*  131 */       rs = ps.executeQuery();
/*  132 */       if (rs.next())
/*      */       {
/*  134 */         apiConfig.put("apiResp", rs.getString(1));
/*  135 */         apiConfig.put("xsltInput", rs.getString(2));
/*  136 */         apiConfig.put("jsonSchema", rs.getString(3));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  141 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  145 */       AMConnectionPool.closeResultSet(rs);
/*  146 */       AMConnectionPool.closePreparedStatement(ps);
/*      */     }
/*  148 */     return apiConfig;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap checkAndGetAPICustomAttrDetails(String resID, long collectionTime)
/*      */   {
/*  158 */     HashMap customAttr = new HashMap();
/*  159 */     HashMap toReturn = new HashMap();
/*  160 */     ArrayList<Integer> graphIds = new ArrayList();
/*  161 */     ResultSet rs = null;
/*  162 */     PreparedStatement ps = null;
/*      */     
/*      */     try
/*      */     {
/*  166 */       ps = AMConnectionPool.getPreparedStatement("select AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,ATTRIBUTE,TYPE,REPORTS_ENABLED from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID and RESOURCEID=?");
/*  167 */       ps.setInt(1, Integer.parseInt(resID));
/*  168 */       rs = ps.executeQuery();
/*  169 */       while (rs.next())
/*      */       {
/*  171 */         Properties prop = new Properties();
/*  172 */         prop.setProperty("Name", rs.getString("ATTRIBUTE"));
/*  173 */         prop.setProperty("Value", "-");
/*  174 */         prop.setProperty("Reports", String.valueOf(rs.getInt("REPORTS_ENABLED")));
/*  175 */         customAttr.put(Integer.valueOf(rs.getInt("ATTRIBUTEID")), prop);
/*  176 */         if (rs.getInt("TYPE") == 0)
/*      */         {
/*  178 */           prop.setProperty("Type", "0");
/*  179 */           graphIds.add(Integer.valueOf(rs.getInt("ATTRIBUTEID")));
/*      */         }
/*      */         else
/*      */         {
/*  183 */           prop.setProperty("Type", "1");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e1)
/*      */     {
/*  189 */       e1.printStackTrace();
/*  190 */       AMLog.debug("[ShowCustomDetails][checkAndGetAPICustomAttrDetails] Error while getting custom attributes config for REST API monitor :" + resID);
/*      */     }
/*      */     finally
/*      */     {
/*  194 */       AMConnectionPool.closeResultSet(rs);
/*  195 */       AMConnectionPool.closePreparedStatement(ps);
/*      */     }
/*      */     try
/*      */     {
/*  199 */       ps = AMConnectionPool.getPreparedStatement("select ATTRIBUTEID,VALUE from AM_CAM_COLUMNAR_DATA where ROWID=? and COLLECTIONTIME>=?");
/*  200 */       ps.setInt(1, Integer.parseInt(resID));
/*  201 */       ps.setLong(2, collectionTime);
/*  202 */       rs = ps.executeQuery();
/*  203 */       while (rs.next())
/*      */       {
/*  205 */         int attributeId = rs.getInt("ATTRIBUTEID");
/*  206 */         Properties prop = (Properties)customAttr.get(Integer.valueOf(attributeId));
/*  207 */         String[] attr_details = DBUtil.getAttributeDetails(attributeId);
/*  208 */         prop.setProperty("Name", attr_details[1]);
/*  209 */         prop.setProperty("Value", rs.getString("VALUE"));
/*  210 */         customAttr.put(Integer.valueOf(attributeId), prop);
/*      */       }
/*      */     }
/*      */     catch (Exception e2)
/*      */     {
/*  215 */       e2.printStackTrace();
/*  216 */       AMLog.debug("[ShowCustomDetails][checkAndGetAPICustomAttrDetails] Error while getting custom attributes values for REST API monitor :" + resID);
/*      */     }
/*      */     finally
/*      */     {
/*  220 */       AMConnectionPool.closeResultSet(rs);
/*  221 */       AMConnectionPool.closePreparedStatement(ps);
/*      */     }
/*      */     
/*  224 */     if (customAttr.size() != 0)
/*      */     {
/*  226 */       toReturn.put("customAttr", customAttr);
/*  227 */       toReturn.put("graphIds", graphIds);
/*      */     }
/*      */     
/*  230 */     return toReturn;
/*      */   }
/*      */   
/*      */   public ActionForward showDataforConfs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  235 */     response.setContentType("text/html; charset=UTF-8");
/*  236 */     String baseid = request.getParameter("baseid");
/*  237 */     ActionErrors errors = new ActionErrors();
/*  238 */     ActionMessages msgs = new ActionMessages();
/*  239 */     int tabId = 1;
/*  240 */     String resourceid = request.getParameter("resourceid");
/*  241 */     if ((request.getParameter("tabId") != null) && (!request.getParameter("tabId").equalsIgnoreCase("null"))) {
/*  242 */       tabId = Integer.parseInt(request.getParameter("tabId"));
/*  243 */       String monitorname = request.getParameter("monitorname");
/*  244 */       request.setAttribute("monitorname", URLDecoder.decode(monitorname != null ? monitorname : getMonitorName(resourceid)));
/*  245 */       request.setAttribute("tabId", Integer.valueOf(tabId));
/*      */     }
/*  247 */     if ((request.getAttribute("monitorname") == null) || ((String)request.getAttribute("monitorname") == null)) {
/*  248 */       request.setAttribute("monitorname", getMonitorName(resourceid));
/*      */     }
/*      */     
/*  251 */     String resourcename = request.getParameter("resourcename");
/*      */     
/*      */ 
/*  254 */     String resourcetype = request.getParameter("type");
/*      */     
/*      */ 
/*  257 */     String original_type = request.getParameter("original_type");
/*      */     
/*      */ 
/*  260 */     String moname = request.getParameter("moname");
/*      */     
/*  262 */     ManagedApplication mo = new ManagedApplication();
/*      */     
/*  264 */     String pollinterval = "";
/*  265 */     if (request.getParameter("tabRequest") == null) {
/*  266 */       request.setAttribute("reloadperiod", "300");
/*  267 */       pollinterval = getPollingInterval(resourceid, request) + "";
/*  268 */       if ((pollinterval == null) || (pollinterval.equals(""))) {
/*  269 */         int polling = 300;
/*  270 */         pollinterval = String.valueOf(polling);
/*      */       }
/*  272 */       request.setAttribute("pollinterval", pollinterval);
/*      */     }
/*      */     else {
/*  275 */       pollinterval = request.getParameter("pollinterval");
/*      */     }
/*  277 */     HashMap map = new HashMap();
/*  278 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  279 */     boolean isAgentParentMonitor = false;
/*  280 */     ConfMonitorConfiguration confmoniconf = ConfMonitorConfiguration.getInstance();
/*      */     
/*      */ 
/*      */ 
/*  284 */     HashMap grpReliedOnMap = confmoniconf.getGroupReliedOn(resourcetype);
/*  285 */     Properties reliedargsValues = new Properties();
/*  286 */     if ((grpReliedOnMap != null) && (tabId > 0)) {
/*  287 */       Set dataset = grpReliedOnMap.keySet();
/*  288 */       Iterator it = dataset.iterator();
/*  289 */       StringBuffer argumentName = new StringBuffer();
/*  290 */       String specialChar = ConfMonitorUtil.getSpecialCharToAppend();
/*  291 */       while (it.hasNext()) {
/*  292 */         String grpName = (String)it.next();
/*  293 */         String colName = (String)grpReliedOnMap.get(grpName);
/*  294 */         if (argumentName.indexOf(colName) == -1) {
/*  295 */           if (argumentName.length() > 0) {
/*  296 */             argumentName.append(",");
/*      */           }
/*  298 */           argumentName.append(specialChar + colName + specialChar + " as " + specialChar + colName.toUpperCase() + specialChar);
/*      */         }
/*      */       }
/*  301 */       if (argumentName.length() > 0) {
/*  302 */         String qry = "select " + argumentName.toString() + "  from AM_ARGS_" + baseid + " where RESOURCEID=" + resourceid;
/*  303 */         getMonitorInfoValue(qry, null, reliedargsValues);
/*  304 */         if ((reliedargsValues.getProperty("isParent") != null) && (reliedargsValues.getProperty("isParent").equalsIgnoreCase("true"))) {
/*  305 */           isAgentParentMonitor = true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  312 */     long collectiontime = 0L;
/*  313 */     long creationTime = 0L;
/*  314 */     if (request.getParameter("tabRequest") == null) {
/*  315 */       collectiontime = getMaxCollectionTime(resourceid, resourcetype, isAgentParentMonitor);
/*      */       
/*  317 */       String responsetime = null;
/*  318 */       ArrayList rows = mo.getPropertiesList("select RESPONSETIME from AM_ManagedObjectData where resID=" + resourceid + " and collectiontime=" + collectiontime);
/*  319 */       if (rows.size() > 0) {
/*  320 */         Properties jvmprops = (Properties)rows.get(0);
/*  321 */         responsetime = jvmprops.get("RESPONSETIME").toString();
/*      */       }
/*  323 */       if (responsetime != null) {
/*  324 */         request.setAttribute("responsetime", responsetime);
/*      */       }
/*  326 */       creationTime = ConfMonitorUtil.getCreationTime(resourceid);
/*  327 */       request.setAttribute("creationTime", Long.valueOf(creationTime));
/*  328 */       request.setAttribute("maxcollectiontime", String.valueOf(collectiontime));
/*      */       try
/*      */       {
/*  331 */         AMDataCorrectionUtil.checkFalseDowntime(Integer.parseInt(resourceid), resourcetype);
/*      */       }
/*      */       catch (Exception e) {
/*  334 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else {
/*  338 */       collectiontime = Long.parseLong(request.getParameter("collectionTime"));
/*  339 */       if (collectiontime == 0L) {
/*  340 */         collectiontime = getMaxCollectionTime(resourceid, resourcetype, isAgentParentMonitor);
/*      */       }
/*  342 */       creationTime = Long.parseLong(request.getParameter("creationTime"));
/*      */     }
/*  344 */     ArrayList attlist = getAttributesList(baseid);
/*  345 */     Properties attname = (Properties)attlist.get(0);
/*  346 */     Properties attdisp = (Properties)attlist.get(1);
/*  347 */     ResultSet rserror = null;
/*      */     try {
/*  349 */       rserror = AMConnectionPool.executeQueryStmt("select ERROR_TYPE,ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID=" + resourceid);
/*  350 */       if (rserror.next()) {
/*  351 */         String message = rserror.getString("ERROR_MESSAGE");
/*  352 */         String err_code = rserror.getString("ERROR_TYPE");
/*  353 */         if (err_code.equals("1")) {
/*  354 */           errors.add("org.apache.struts.action.ERROR", new ActionError(FormatUtil.getString(message)));
/*  355 */           saveErrors(request, errors);
/*  356 */           AMLog.debug("the errormessage:" + FormatUtil.getString(message));
/*      */         }
/*  358 */         else if ((err_code.equals("2")) && (!message.equals("am.datacollection.success")) && (!message.equals("am.datacollection.managed")) && (!message.equals("am.datacollection.unmanaged")) && (!message.equals("am.datacollection.maintenance"))) {
/*  359 */           msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString(message)));
/*  360 */           saveMessages(request, msgs);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  365 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  368 */       rserror.close();
/*      */     }
/*      */     
/*  371 */     Properties ess_atts = DBUtil.getCommonAttributes(original_type);
/*  372 */     Properties discoverPageUIDetails = confmoniconf.getTypeDescription(resourcetype);
/*  373 */     Object tabConfiguration = (ArrayList)confmoniconf.getTabsList(resourcetype);
/*  374 */     List groupsForTab = new ArrayList();
/*  375 */     HashMap groupData = new HashMap();
/*  376 */     HashMap tabCustomConfig = confmoniconf.getTabCustomisation(resourcetype);
/*  377 */     List tabToBeRemoved = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  384 */     boolean fullyConfigured = true;
/*  385 */     boolean isDiscoverySupported = (discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("DISCOVERY-SUPPORTED").equalsIgnoreCase("YES"));
/*  386 */     if (isDiscoverySupported)
/*      */     {
/*  388 */       int dcstarted = DBUtil.getDCStarted(Integer.parseInt(resourceid));
/*  389 */       if (dcstarted == 1) {
/*  390 */         fullyConfigured = false;
/*      */       }
/*      */     }
/*      */     
/*  394 */     boolean isParent = false;
/*  395 */     boolean allowEdit = true;
/*  396 */     boolean isDependent = confmoniconf.isDependentMonitor(resourcetype);
/*  397 */     String isAgentEnabled = confmoniconf.getTypeDescription(resourcetype).getProperty("IS-AGENT-ENABLED");
/*  398 */     if ((reliedargsValues.getProperty("ISPARENT") != null) && (reliedargsValues.getProperty("ISPARENT").equalsIgnoreCase("true"))) {
/*  399 */       isParent = true;
/*      */     }
/*  401 */     if (isDependent) {
/*  402 */       allowEdit = false;
/*      */     }
/*  404 */     if ((isAgentEnabled.equals("YES")) && (!isParent)) {
/*  405 */       allowEdit = true;
/*      */     }
/*  407 */     if ((isAgentEnabled.equals("YES")) && (!isParent))
/*      */     {
/*  409 */       request.setAttribute("eumChild", "true");
/*  410 */       HashMap<String, String> eumParentMonDetails = AppManagerUtil.getEumParentDetails(resourceid, new String[] { "RESOURCEID" });
/*  411 */       request.setAttribute("parentEumMonId", eumParentMonDetails.get("RESOURCEID"));
/*      */     }
/*      */     try
/*      */     {
/*  415 */       if ((tabConfiguration != null) && (tabId > 0)) {
/*  416 */         groupsForTab = confmoniconf.getUIConfiguration(resourcetype, (String)((List)tabConfiguration).get(tabId));
/*  417 */         groupData.put("Group_tab" + Integer.toString(tabId), groupsForTab);
/*      */       }
/*      */       else {
/*  420 */         groupData.put("GroupDetails", groupsForTab);
/*      */       }
/*      */       
/*  423 */       if (tabCustomConfig != null)
/*      */       {
/*  425 */         Set dataset = tabCustomConfig.keySet();
/*  426 */         Iterator it = dataset.iterator();
/*  427 */         while (it.hasNext())
/*      */         {
/*  429 */           String tabName = (String)it.next();
/*  430 */           HashMap tabConfigMap = (HashMap)tabCustomConfig.get(tabName);
/*  431 */           if ((tabConfigMap.get("RELIED-ON-ARG") != null) && (tabConfigMap.get("RELIED-ON-VALUE") != null))
/*      */           {
/*  433 */             String specialChar = ConfMonitorUtil.getSpecialCharToAppend();
/*  434 */             String configValueQuery = "select " + specialChar + (String)tabConfigMap.get("RELIED-ON-ARG") + specialChar + " from AM_ARGS_" + baseid + " where RESOURCEID=" + resourceid;
/*  435 */             ResultSet rs = null;
/*      */             try
/*      */             {
/*  438 */               rs = AMConnectionPool.executeQueryStmt(configValueQuery);
/*  439 */               if (rs.next())
/*      */               {
/*  441 */                 String reliedArgTabValue = rs.getString(1);
/*  442 */                 if (!reliedArgTabValue.equals((String)tabConfigMap.get("RELIED-ON-VALUE")))
/*      */                 {
/*  444 */                   tabToBeRemoved.add(tabName);
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*  449 */                 tabToBeRemoved.add(tabName);
/*      */               }
/*      */             }
/*      */             catch (Exception ee) {
/*  453 */               ee.printStackTrace();
/*      */             }
/*      */             finally {
/*  456 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */           }
/*      */           
/*  460 */           if ((tabConfigMap.get("RELIED-ON-CONFATTRIB") != null) && (tabConfigMap.get("RELIED-ON-ATTRIBVALUE") != null) && (tabConfigMap.get("RELIED-ON-CONFCONDITION") != null))
/*      */           {
/*      */ 
/*  463 */             String configValueQuery = "select ci.CONFVALUE as VALUE from AM_CONFIGURATION_INFO as ci ,AM_ATTRIBUTES  as att where att.RESOURCETYPE='" + resourcetype + "' and att.ATTRIBUTE='" + tabConfigMap.get("RELIED-ON-CONFATTRIB") + "' and  att.ATTRIBUTEID=ci.ATTRIBUTEID and ci.RESOURCEID=" + resourceid + " and ci.LATEST=1";
/*  464 */             ResultSet rs = null;
/*      */             try
/*      */             {
/*  467 */               rs = AMConnectionPool.executeQueryStmt(configValueQuery);
/*  468 */               if (rs.next())
/*      */               {
/*  470 */                 String reliedArgTabValue = rs.getString(1);
/*  471 */                 if (("CONTAINS".equals((String)tabConfigMap.get("RELIED-ON-CONFCONDITION"))) && (!reliedArgTabValue.contains((String)tabConfigMap.get("RELIED-ON-ATTRIBVALUE"))))
/*      */                 {
/*  473 */                   tabToBeRemoved.add(tabName);
/*      */                 }
/*  475 */                 else if (("EQUALS".equals((String)tabConfigMap.get("RELIED-ON-CONFCONDITION"))) && (!reliedArgTabValue.equals((String)tabConfigMap.get("RELIED-ON-ATTRIBVALUE"))))
/*      */                 {
/*  477 */                   tabToBeRemoved.add(tabName);
/*      */                 }
/*      */               }
/*      */               else
/*      */               {
/*  482 */                 tabToBeRemoved.add(tabName);
/*      */               }
/*      */             }
/*      */             catch (Exception ee) {
/*  486 */               ee.printStackTrace();
/*      */             }
/*      */             finally {
/*  489 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  494 */         request.setAttribute("tabToBeRemoved", tabToBeRemoved);
/*      */       }
/*      */     }
/*      */     catch (NullPointerException e)
/*      */     {
/*  499 */       e.printStackTrace();
/*      */     }
/*      */     catch (Exception e) {
/*  502 */       e.printStackTrace();
/*      */     }
/*      */     
/*  505 */     long pl = Long.parseLong(pollinterval);
/*  506 */     request.setAttribute("granularitySelected", pl / 60000L + "");
/*  507 */     request.setAttribute("perioicitySelected", (request.getParameter("TimeUnit") != null) && (!request.getParameter("TimeUnit").equalsIgnoreCase("null")) ? request.getParameter("TimeUnit") : "0");
/*  508 */     String granuleLevel = "PolledData";
/*  509 */     if ((request.getParameter("granularity") != null) && (!request.getParameter("granularity").equals(pl / 60000L + ""))) {
/*  510 */       granuleLevel = request.getParameter("granularity");
/*      */     }
/*  512 */     HashMap parameterList = new HashMap();
/*  513 */     parameterList.put("Resourcename", resourcename);
/*  514 */     parameterList.put("IsEEAdmin", Boolean.valueOf(request.isUserInRole("ENTERPRISEADMIN")));
/*  515 */     parameterList.put("Granularity", granuleLevel);
/*  516 */     parameterList.put("Periodicity", request.getParameter("TimeUnit") != null ? request.getParameter("TimeUnit") : "0");
/*  517 */     parameterList.put("DateSelected", request.getParameter("customDate") != null ? request.getParameter("customDate") : "");
/*  518 */     parameterList.put("WeekSelected", request.getParameter("weekUnit") != null ? request.getParameter("weekUnit") : "");
/*  519 */     parameterList.put("YearMonth", request.getParameter("monthUnit") != null ? request.getParameter("monthUnit") : "");
/*  520 */     if (request.getParameter("tabRequest") != null) {
/*  521 */       Properties timeProp = getTimePeriod(0L, System.currentTimeMillis(), (String)parameterList.get("Periodicity"), parameterList);
/*  522 */       SimpleDateFormat sdf = new SimpleDateFormat("MMM d,yyyy hh:mm aaa");
/*  523 */       String startDate = sdf.format(new Date(((Long)timeProp.get("STARTTIME")).longValue()));
/*  524 */       String endDate = sdf.format(new Date(((Long)timeProp.get("ENDTIME")).longValue()));
/*  525 */       boolean isValidDateRange = isValidDateSelected(creationTime, timeProp, parameterList);
/*  526 */       if (!isValidDateRange) {
/*  527 */         msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.confmonitors.outOfRange", new String[] { startDate, endDate })));
/*  528 */         saveMessages(request, msgs);
/*  529 */         request.setAttribute("granularitySelected", pl / 60000L + "");
/*  530 */         request.setAttribute("perioicitySelected", "0");
/*      */       }
/*  532 */       request.setAttribute("isValidDateRange", Boolean.valueOf(isValidDateRange));
/*  533 */       startDate = sdf.format(new Date(((Long)timeProp.get("STARTTIME")).longValue()));
/*  534 */       endDate = sdf.format(new Date(((Long)timeProp.get("ENDTIME")).longValue()));
/*  535 */       if (((Long)timeProp.get("ENDTIME")).longValue() > System.currentTimeMillis()) {
/*  536 */         endDate = sdf.format(new Date(System.currentTimeMillis()));
/*      */       }
/*  538 */       String reportPeriodMsg = FormatUtil.getString("am.webclient.conf.performancemetrics.report", new String[] { startDate, endDate, (String)request.getAttribute("granularitySelected") });
/*  539 */       request.setAttribute("ReportPeriodMessage", reportPeriodMsg);
/*  540 */       if (tabId > 0) {
/*  541 */         HashMap valueMap = ConfMonitorUtil.getDataforResource(resourceid, collectiontime, resourcetype, tabId + "", "", parameterList, timeProp);
/*  542 */         request.setAttribute("attributeValues", (HashMap)valueMap.get("VALUES"));
/*  543 */         request.setAttribute("graphValues", (HashMap)valueMap.get("GRAPH"));
/*  544 */         request.setAttribute("linkValues", (HashMap)valueMap.get("A-LINK"));
/*  545 */         request.setAttribute("disNameValues", (HashMap)valueMap.get("ATT-DIS-NAME"));
/*  546 */         String reportURL = "../showHistoryData.do?method=getData&reporttype=html&businessPeriod=oni&resourceid=" + resourceid + "&period=20&childid=null&confStartTime=" + (Long)timeProp.get("STARTTIME") + "&confEndTime=" + (Long)timeProp.get("ENDTIME");
/*  547 */         request.setAttribute("reportURL", reportURL);
/*      */       }
/*      */     }
/*      */     
/*  551 */     if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED").equals("YES"))) {
/*  552 */       String strMsg = ConfMonitorUtil.getFirstDCNotCompleteMsg(resourcetype, resourceid, pl);
/*  553 */       if (!strMsg.equals(""))
/*      */       {
/*  555 */         msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.eumagent.dcinited.text", new String[] { strMsg })));
/*  556 */         saveMessages(request, msgs);
/*      */       }
/*      */     }
/*      */     
/*  560 */     LinkedHashMap hostProps = new LinkedHashMap();
/*      */     
/*  562 */     getHostValues(resourceid, resourcetype, hostProps, true);
/*  563 */     long cleanupInterval = ConfMonitorConstants.getCleanupInteral();
/*  564 */     if (request.getParameter("reportMessage") != null) {
/*  565 */       msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(request.getParameter("reportMessage")));
/*  566 */       saveMessages(request, msgs);
/*      */     }
/*      */     
/*  569 */     if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED").equals("YES"))) {
/*  570 */       getassocciatedAgentDetails(resourceid, request, reliedargsValues);
/*      */     }
/*      */     
/*  573 */     HashMap tablesList = confmoniconf.getTableListInEachTab(resourcetype, tabId + "");
/*  574 */     if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("CustomConfig") != null) && (discoverPageUIDetails.getProperty("CustomConfig").equals("YES")))
/*      */     {
/*  576 */       ArrayList customConfigList = confmoniconf.getCustomConfigDetails(resourcetype);
/*  577 */       ArrayList actionsMap = new ArrayList();
/*  578 */       for (int n = 0; n < customConfigList.size(); n++)
/*      */       {
/*  580 */         HashMap methodArgumentsMap = new HashMap();
/*  581 */         ArrayList usersPermitted = new ArrayList();
/*  582 */         methodArgumentsMap = (HashMap)customConfigList.get(n);
/*  583 */         usersPermitted = (ArrayList)methodArgumentsMap.get("USERSPERMITTED");
/*  584 */         for (int i = 0; i < usersPermitted.size(); i++)
/*      */         {
/*  586 */           if (request.isUserInRole((String)usersPermitted.get(i)))
/*      */           {
/*      */ 
/*  589 */             actionsMap.add(methodArgumentsMap);
/*  590 */             break;
/*      */           }
/*      */         }
/*      */       }
/*  594 */       if ((actionsMap != null) && (actionsMap.size() > 0))
/*      */       {
/*  596 */         request.setAttribute("CustomConfigList", actionsMap);
/*  597 */         request.setAttribute("isCustomConfigEnabled", Boolean.valueOf(true));
/*      */       }
/*      */       else
/*      */       {
/*  601 */         request.setAttribute("isCustomConfigEnabled", Boolean.valueOf(false));
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  606 */       request.setAttribute("isCustomConfigEnabled", Boolean.valueOf(false));
/*      */     }
/*  608 */     ArrayList uielementsToRemove = DifferentialPollingUtil.getListOfUIElementsToRemove(resourceid, resourcetype);
/*  609 */     request.setAttribute("RemoveUIElements", uielementsToRemove);
/*  610 */     hostProps.put("HideHostDetails", (uielementsToRemove != null) && (!uielementsToRemove.isEmpty()) && (uielementsToRemove.contains("UI_HOSTDETAILS")) ? "true" : "false");
/*  611 */     getHostValues(resourceid, resourcetype, hostProps, true);
/*  612 */     request.setAttribute("TABLES-LIST", tablesList != null ? tablesList.size() + "" : "0");
/*  613 */     request.setAttribute("CleanUpInterval", cleanupInterval + "");
/*  614 */     request.setAttribute("ReliedOnArgsMap", reliedargsValues);
/*  615 */     request.setAttribute("serverName", (String)hostProps.get("serverName"));
/*  616 */     request.setAttribute("hostName", (String)hostProps.get("hostName"));
/*  617 */     request.setAttribute("hostOs", (String)hostProps.get("hostOs"));
/*  618 */     request.setAttribute("hostId", (String)hostProps.get("hostId"));
/*  619 */     request.setAttribute("ShowHostPerformance", (Boolean)hostProps.get("ShowHostPerformance"));
/*  620 */     request.setAttribute("groupData", groupData);
/*  621 */     request.setAttribute("tabConfiguration", tabConfiguration);
/*  622 */     request.setAttribute("ess_atts", ess_atts);
/*  623 */     request.setAttribute("TimeValues", parameterList);
/*  624 */     request.setAttribute("resourcetype", resourcetype);
/*      */     
/*  626 */     if (resourcetype.equals("RESTAPIMonitor"))
/*      */     {
/*  628 */       request.setAttribute("apiConfig", getAPICustomConfigDetails(resourceid));
/*  629 */       request.setAttribute("customAttr", checkAndGetAPICustomAttrDetails(resourceid, collectiontime));
/*  630 */       Properties props = NewMonitorUtil.getArgsasProps(resourcetype, resourceid, true);
/*  631 */       request.setAttribute("responseType", props.get("RespType"));
/*      */     }
/*      */     
/*  634 */     if (request.getParameter("tabRequest") == null) {
/*  635 */       return new ActionForward("/jsp/ConfDetails.jsp?baseid=" + baseid + "&fromwhere=" + request.getParameter("fromwhere"));
/*      */     }
/*      */     
/*  638 */     RequestDispatcher rd = request.getRequestDispatcher("/jsp/ConfDetails.jsp?baseid=" + baseid + "&fromwhere=" + request.getParameter("fromwhere") + "&fromTab=true");
/*  639 */     rd.include(request, response);
/*  640 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isValidDateSelected(long creationTime, Properties timeProp, HashMap parameterList) {
/*  644 */     boolean isvalidDateRange = ConfMonitorUtil.isValidDateRange(creationTime, timeProp);
/*  645 */     if (!isvalidDateRange) {
/*  646 */       parameterList.put("Granularity", "PolledData");
/*  647 */       parameterList.put("Periodicity", "0");
/*  648 */       parameterList.put("DateSelected", "");
/*  649 */       parameterList.put("WeekSelected", "");
/*  650 */       parameterList.put("YearMonth", "");
/*  651 */       Properties temptimeProp = getTimePeriod(0L, System.currentTimeMillis(), (String)parameterList.get("Periodicity"), parameterList);
/*  652 */       timeProp.putAll(temptimeProp);
/*      */     }
/*  654 */     return isvalidDateRange;
/*      */   }
/*      */   
/*      */   public ActionForward showDataforConfTables(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  660 */       HashMap parameterList = new HashMap();
/*  661 */       long pollingInterval = 300000L;
/*  662 */       String granularity = request.getParameter("granularity");
/*  663 */       boolean showAllRows = request.getParameter("showAllRows") != null;
/*      */       try {
/*  665 */         pollingInterval = Long.parseLong(request.getParameter("pollinterval")) / 60000L;
/*  666 */         if ((request.getParameter("granularity") == null) || ((pollingInterval + "").equals(granularity))) {
/*  667 */           granularity = "PolledData";
/*      */         }
/*      */       } catch (Exception e) {
/*  670 */         e.printStackTrace(); }
/*  671 */       ArrayList uielementsToRemove = DifferentialPollingUtil.getListOfUIElementsToRemove(request.getParameter("resourceid"), request.getParameter("type"));
/*  672 */       request.setAttribute("RemoveUIElements", uielementsToRemove);
/*  673 */       parameterList.put("Granularity", granularity);
/*  674 */       parameterList.put("Periodicity", request.getParameter("TimeUnit") != null ? request.getParameter("TimeUnit") : "0");
/*  675 */       parameterList.put("DateSelected", request.getParameter("customDate") != null ? request.getParameter("customDate") : "");
/*  676 */       parameterList.put("WeekSelected", request.getParameter("weekUnit") != null ? request.getParameter("weekUnit") : "");
/*  677 */       parameterList.put("YearMonth", request.getParameter("monthUnit") != null ? request.getParameter("monthUnit") : "");
/*  678 */       parameterList.put("showAllRows", Boolean.valueOf(showAllRows));
/*  679 */       if (showAllRows) {
/*  680 */         request.setAttribute("OpenInNewWindow", Boolean.valueOf(true));
/*      */       }
/*  682 */       long creationTime = Long.parseLong(request.getParameter("creationTime"));
/*  683 */       boolean isAgentParentMonitor = request.getParameter("isParentMonitor").equals("true");
/*  684 */       Properties timeProp = getTimePeriod(0L, System.currentTimeMillis(), (String)parameterList.get("Periodicity"), parameterList);
/*  685 */       isValidDateSelected(creationTime, timeProp, parameterList);
/*  686 */       String resourceType = request.getParameter("type");
/*  687 */       long collectiontime = 0L;
/*  688 */       boolean showRowStatus = false;
/*  689 */       if (request.getParameter("customDate") != null)
/*      */       {
/*  691 */         SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
/*  692 */         Calendar cal = Calendar.getInstance();
/*  693 */         cal.setTimeInMillis(System.currentTimeMillis());
/*  694 */         String selectedDate = dateFormat.format(cal.getTime());
/*  695 */         if (selectedDate.equals(request.getParameter("customDate"))) {
/*  696 */           showRowStatus = true;
/*      */         }
/*      */       }
/*  699 */       if (((String)parameterList.get("Periodicity")).equals("0")) {
/*  700 */         showRowStatus = true;
/*      */       }
/*      */       
/*  703 */       if (request.getParameter("tableId") != null) {
/*  704 */         HashMap tablesList = ConfMonitorConfiguration.getInstance().getTableListInEachTab(resourceType, request.getParameter("tabId"));
/*  705 */         String uiTableDisplayName = (String)tablesList.get(request.getParameter("tableId"));
/*  706 */         HashMap tableData = ConfMonitorConfiguration.getInstance().getUITableDetails(resourceType + "_" + uiTableDisplayName);
/*  707 */         ArrayList attributesListInUITable = (tableData != null) && (tableData.containsKey("COLUMNLIST")) ? (ArrayList)tableData.get("COLUMNLIST") : null;
/*  708 */         ArrayList numericIdsInDBTable = (tableData != null) && (tableData.containsKey("NUMERICIDS")) ? (ArrayList)tableData.get("NUMERICIDS") : null;
/*  709 */         boolean isNumericAttributesAvailable = false;
/*  710 */         if ((attributesListInUITable != null) && (numericIdsInDBTable != null)) {
/*  711 */           Iterator it = attributesListInUITable.iterator();
/*  712 */           while (it.hasNext()) {
/*  713 */             HashMap columnDetails = (HashMap)it.next();
/*  714 */             if (((String)columnDetails.get("TYPE")).equals("RAW")) {
/*  715 */               String attributeId = (String)((HashMap)columnDetails.get("ATTRIBUTES")).get("ATTRIBUTEID");
/*  716 */               if (numericIdsInDBTable.contains(attributeId)) {
/*  717 */                 isNumericAttributesAvailable = true;
/*  718 */                 break;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*  723 */         String tableName = tableData.get("NAME") != null ? (String)tableData.get("NAME") : (String)tableData.get("TABLENAME");
/*  724 */         if ((tableData.get("CHILD-TYPE") != null) && (!((String)tableData.get("CHILD-TYPE")).trim().equals(""))) {
/*  725 */           collectiontime = getMaxCollectionTime(request.getParameter("resourceid"), resourceType, isAgentParentMonitor);
/*      */         }
/*      */         else {
/*  728 */           collectiontime = getMaxCollectionTime(request.getParameter("resourceid"), resourceType, isAgentParentMonitor, tableName);
/*      */         }
/*  730 */         if ((tableData.get("CREATE-MO") != null) && (((String)tableData.get("CREATE-MO")).equalsIgnoreCase("NO"))) {
/*  731 */           if ((!"PolledData".equals(granularity)) || (!((String)parameterList.get("Periodicity")).equals("0"))) {
/*  732 */             request.setAttribute("SHOW-INSTANT-MSG", "YES");
/*      */           }
/*  734 */           resetValuesToCustomTimeSelected(parameterList);
/*  735 */           timeProp = getTimePeriod(0L, System.currentTimeMillis(), "0", parameterList);
/*      */         }
/*      */         
/*  738 */         HashMap valueMap = ConfMonitorUtil.getDataforResource(request.getParameter("resourceid"), collectiontime, resourceType, request.getParameter("tabId"), uiTableDisplayName, parameterList, timeProp);
/*      */         
/*  740 */         String reportURL = reportURL = "../showHistoryData.do?method=getData&reporttype=html&period=20&businessPeriod=oni&confStartTime=" + (Long)timeProp.get("STARTTIME") + "&confEndTime=" + (Long)timeProp.get("ENDTIME");
/*  741 */         request.setAttribute("reportURL", reportURL);
/*  742 */         request.setAttribute("resID", request.getParameter("resourceid"));
/*  743 */         request.setAttribute("resourceType", request.getParameter("type"));
/*  744 */         request.setAttribute("baseid1", request.getParameter("baseid"));
/*  745 */         request.setAttribute("tableDisName", uiTableDisplayName);
/*  746 */         request.setAttribute("tableId", request.getParameter("tableId"));
/*      */         
/*  748 */         if (valueMap != null) {
/*  749 */           request.setAttribute("valueMap", (HashMap)valueMap.get("TABLES"));
/*      */         } else {
/*  751 */           request.setAttribute("valueMap", new HashMap());
/*      */         }
/*  753 */         request.setAttribute("showRowStatus", Boolean.valueOf(showRowStatus));
/*  754 */         request.setAttribute("isNumericAttributesAvailable", Boolean.valueOf(isNumericAttributesAvailable));
/*      */         
/*      */ 
/*  757 */         String showLocationData = (String)tableData.get("ShowEUMLocationTable");
/*  758 */         if ("YES".equals(showLocationData)) {
/*  759 */           HashMap tableMap = (HashMap)valueMap.get("TABLES");
/*  760 */           String dataAttributeType = (String)tableMap.get("tableAttributeType");
/*  761 */           if (("1".equals(dataAttributeType)) || ("2".equals(dataAttributeType))) {
/*  762 */             ArrayList<String> tableIdList = (ArrayList)tableMap.get("tableIdList");
/*  763 */             ArrayList<String> attributeidList = new ArrayList();
/*  764 */             attributeidList.add((String)tableMap.get("tableAttribute"));
/*  765 */             Properties alert = FaultUtil.getStatus(tableIdList, attributeidList);
/*  766 */             request.setAttribute("alert", alert);
/*      */           }
/*  768 */           return new ActionForward("/jsp/includes/ConfEUMTableDetails.jsp");
/*      */         }
/*      */         
/*  771 */         return new ActionForward("/jsp/includes/ConfTableDetails.jsp");
/*      */       }
/*  773 */       if (request.getParameter("graphid") != null)
/*      */       {
/*  775 */         HashMap GraphList = ConfMonitorConfiguration.getInstance().getGraphListInEachTab(resourceType, request.getParameter("tabId"));
/*  776 */         String uiGraphDisplayName = (String)GraphList.get(request.getParameter("graphid"));
/*  777 */         String scrWidth = request.getParameter("ScreenWidth");
/*  778 */         String imgWidth = "900";
/*  779 */         String imgHeight = "350";
/*  780 */         String widhtType = (String)ConfMonitorConfiguration.getInstance().getUIGraphProperties(resourceType + "_" + uiGraphDisplayName).get("FULLWIDTH");
/*  781 */         if (widhtType.equals("2")) {
/*  782 */           imgWidth = Integer.parseInt(scrWidth) <= 1280 ? "450" : "600";
/*  783 */           imgHeight = Integer.parseInt(scrWidth) <= 1280 ? "280" : "350";
/*      */         }
/*  785 */         HashMap valueMap = null;
/*  786 */         String resid = request.getParameter("resourceid");
/*  787 */         if ((resid != null) && (!resid.equals("")))
/*      */         {
/*  789 */           HashMap graphProperties = ConfMonitorConfiguration.getInstance().getUIGraphProperties(resourceType + "_" + uiGraphDisplayName);
/*  790 */           String tableName = graphProperties.get("NAME") != null ? (String)graphProperties.get("NAME") : (String)graphProperties.get("TABLENAME");
/*  791 */           if ((graphProperties.get("CHILD-TYPE") != null) && (!((String)graphProperties.get("CHILD-TYPE")).trim().equals(""))) {
/*  792 */             collectiontime = getMaxCollectionTime(request.getParameter("resourceid"), resourceType, isAgentParentMonitor);
/*      */           }
/*      */           else {
/*  795 */             collectiontime = getMaxCollectionTime(request.getParameter("resourceid"), resourceType, isAgentParentMonitor, tableName);
/*      */           }
/*  797 */           if ((graphProperties.get("CREATE-MO") != null) && (((String)graphProperties.get("CREATE-MO")).equalsIgnoreCase("NO"))) {
/*  798 */             resetValuesToCustomTimeSelected(parameterList);
/*  799 */             timeProp = getTimePeriod(0L, System.currentTimeMillis(), "0", parameterList);
/*      */           }
/*  801 */           valueMap = ConfMonitorUtil.getDataforResource(resid, collectiontime, resourceType, request.getParameter("graphid"), uiGraphDisplayName, parameterList, timeProp, true);
/*      */         }
/*      */         
/*  804 */         if (valueMap != null)
/*      */         {
/*  806 */           request.setAttribute("valueMap", (HashMap)valueMap.get("GRAPH"));
/*      */         }
/*  808 */         request.setAttribute("GraphName", uiGraphDisplayName);
/*  809 */         request.setAttribute("resourceType", request.getParameter("type"));
/*  810 */         request.setAttribute("ImgWidth", imgWidth);
/*  811 */         request.setAttribute("ImgHeight", imgHeight);
/*  812 */         return new ActionForward("/jsp/includes/ConfUIGraphs.jsp");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  817 */       e.printStackTrace();
/*      */     }
/*  819 */     return null;
/*      */   }
/*      */   
/*  822 */   public void resetValuesToCustomTimeSelected(HashMap parameterList) { parameterList.put("Granularity", "PolledData");
/*      */     
/*  824 */     parameterList.put("Periodicity", "0");
/*  825 */     parameterList.put("DateSelected", "");
/*  826 */     parameterList.put("WeekSelected", "");
/*  827 */     parameterList.put("YearMonth", "");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward showDataforScripts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  834 */     String baseid = (String)request.getAttribute("baseid");
/*  835 */     ActionErrors errors = new ActionErrors();
/*  836 */     ActionMessages msgs = new ActionMessages();
/*      */     
/*  838 */     String haid = request.getParameter("haid");
/*  839 */     System.out.println("the haid of script monitor:" + haid);
/*  840 */     String applicationName = request.getParameter("name");
/*  841 */     System.out.println("the application name of script monitor:" + applicationName);
/*  842 */     String resourceid = request.getParameter("resourceid");
/*  843 */     String resourcename = request.getParameter("resourcename");
/*      */     
/*  845 */     String resourcetype = request.getParameter("type");
/*  846 */     String original_type = request.getParameter("original_type");
/*  847 */     String moname = request.getParameter("moname");
/*  848 */     ManagedApplication mo = new ManagedApplication();
/*  849 */     String pollinterval = (String)request.getAttribute("reloadperiod");
/*  850 */     if ((pollinterval == null) || (pollinterval.equals("")))
/*      */     {
/*  852 */       int polling = 300;
/*  853 */       pollinterval = String.valueOf(polling);
/*      */     }
/*  855 */     request.setAttribute("reloadperiod", pollinterval);
/*  856 */     HashMap map = new HashMap();
/*  857 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  858 */     String coll_query = "select max(collectiontime) from AM_ManagedObjectData where RESID=" + resourceid;
/*  859 */     long collectiontime = 0L;
/*      */     try
/*      */     {
/*  862 */       ResultSet col_time = AMConnectionPool.executeQueryStmt(coll_query);
/*  863 */       if (col_time.next())
/*      */       {
/*  865 */         collectiontime = col_time.getLong(1);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  870 */       exc.printStackTrace();
/*      */     }
/*  872 */     String poll_interval_qry = "select pollinterval from AM_ScriptArgs where resourceid=" + resourceid;
/*  873 */     long pl = 300000L;
/*      */     try
/*      */     {
/*  876 */       ResultSet pi = AMConnectionPool.executeQueryStmt(poll_interval_qry);
/*  877 */       if (pi.next())
/*      */       {
/*  879 */         pl = pi.getLong(1) * 1000L;
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  884 */       exc.printStackTrace();
/*      */     }
/*  886 */     long nextDC = collectiontime + pl;
/*  887 */     Properties dcTimes = new Properties();
/*  888 */     if (collectiontime != 0L)
/*      */     {
/*  890 */       if (collectiontime > AMServerFramework.serverStartupTime)
/*      */       {
/*      */ 
/*  893 */         dcTimes.setProperty("lastDC", String.valueOf(collectiontime));
/*  894 */         dcTimes.setProperty("nextDC", String.valueOf(nextDC));
/*      */       } else {
/*  896 */         dcTimes.setProperty("lastDC", String.valueOf(collectiontime));
/*      */         
/*  898 */         dcTimes.setProperty("nextDC", String.valueOf(AMServerFramework.serverStartupTime + pl));
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  903 */       dcTimes.setProperty("lastDC", "-");
/*  904 */       dcTimes.setProperty("nextDC", String.valueOf(System.currentTimeMillis() + pl));
/*      */     }
/*      */     
/*      */ 
/*  908 */     ArrayList attlist = getAttributesList(baseid);
/*      */     
/*  910 */     Properties attname = (Properties)attlist.get(0);
/*  911 */     Properties attdisp = (Properties)attlist.get(1);
/*  912 */     String responsetime = null;
/*  913 */     ArrayList rows = mo.getPropertiesList("select RESPONSETIME from AM_ManagedObjectData where resID=" + resourceid + " and collectiontime=" + collectiontime);
/*  914 */     if (rows.size() > 0)
/*      */     {
/*  916 */       Properties jvmprops = (Properties)rows.get(0);
/*  917 */       responsetime = jvmprops.get("RESPONSETIME").toString();
/*      */     }
/*  919 */     if (responsetime != null)
/*  920 */       request.setAttribute("responsetime", responsetime);
/*  921 */     ArrayList data = getGroupsAndData(baseid, resourceid, collectiontime, attlist, resourcetype);
/*  922 */     String amcreated = (String)request.getAttribute("amcreated");
/*  923 */     if ((amcreated != null) && (amcreated.equals("NO")))
/*      */     {
/*  925 */       ArrayList string_data = getStringData(baseid, resourceid, collectiontime, resourcetype);
/*  926 */       request.setAttribute("string_data", string_data);
/*  927 */       ArrayList numeric_data = getNumericData(baseid, resourceid, collectiontime, resourcetype);
/*  928 */       request.setAttribute("numeric_data", numeric_data);
/*      */     }
/*  930 */     ArrayList archlist = getArchivalAttributesList(resourcetype);
/*      */     
/*      */ 
/*  933 */     request.setAttribute("attributes", attlist);
/*  934 */     request.setAttribute("dcTimes", dcTimes);
/*  935 */     request.setAttribute("data", data);
/*  936 */     request.setAttribute("archlist", archlist);
/*  937 */     ArrayList table_details = getTableValuesforScript(resourceid, collectiontime, request, baseid, original_type);
/*      */     try
/*      */     {
/*  940 */       ResultSet rserror = AMConnectionPool.executeQueryStmt("select ERROR_TYPE,ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID=" + resourceid);
/*      */       
/*  942 */       if (rserror.next()) {
/*  943 */         String message = rserror.getString("ERROR_MESSAGE");
/*  944 */         String err_code = rserror.getString("ERROR_TYPE");
/*  945 */         if (err_code.equals("1"))
/*      */         {
/*  947 */           errors.add("org.apache.struts.action.ERROR", new ActionError(FormatUtil.getString(message)));
/*  948 */           saveErrors(request, errors);
/*  949 */           System.out.println("the errormessage:" + FormatUtil.getString(message));
/*      */         }
/*  951 */         else if ((err_code.equals("2")) && (!message.equals("am.datacollection.success")) && (!message.equals("am.datacollection.managed")) && (!message.equals("am.datacollection.unmanaged")) && (!message.equals("am.datacollection.maintenance")))
/*      */         {
/*  953 */           msgs.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString(message)));
/*  954 */           saveMessages(request, msgs);
/*      */         }
/*      */       }
/*      */       
/*  958 */       rserror.close();
/*      */     } catch (Exception ex) {
/*  960 */       ex.printStackTrace();
/*      */     }
/*  962 */     Properties ess_atts = DBUtil.getCommonAttributes(original_type);
/*      */     
/*  964 */     if (resourcetype.equals("QueryMonitor"))
/*      */     {
/*  966 */       LinkedHashMap monitorInformation = (LinkedHashMap)ConfMonitorConfiguration.getInstance().getMonitorInformation(resourcetype);
/*  967 */       monitorInformationValue(monitorInformation, resourcetype, resourceid, String.valueOf(collectiontime));
/*  968 */       String checkalreadyexists = "";
/*  969 */       String hostOs = "";
/*  970 */       String hostId = "";
/*  971 */       String hostName = "";
/*      */       try {
/*  973 */         hostName = getHostName(resourcetype, resourceid);
/*  974 */         if ((hostName != null) && (!hostName.equals(""))) {
/*  975 */           TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*  976 */           checkalreadyexists = tapi.getNodeNameByIP(hostName);
/*  977 */           if (checkalreadyexists != null) {
/*  978 */             InetAddress address = InetAddress.getByName(hostName);
/*  979 */             Properties hostData = getHostOs(hostName);
/*  980 */             hostOs = hostData.getProperty("HOST-TYPE");
/*  981 */             hostId = hostData.getProperty("HOST-ID");
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  987 */         AMLog.debug("Host Name null or Invalid for resId:" + resourceid);
/*      */       }
/*  989 */       request.setAttribute("monitorInformation", monitorInformation);
/*  990 */       request.setAttribute("checkalreadyexists", checkalreadyexists);
/*  991 */       request.setAttribute("hostName", hostName);
/*  992 */       request.setAttribute("hostOs", hostOs);
/*  993 */       request.setAttribute("hostId", hostId);
/*      */     }
/*  995 */     String reportURL = "../showHistoryData.do?method=getData&period=-7";
/*  996 */     if (!request.isUserInRole("ENTERPRISEADMIN")) {
/*  997 */       reportURL = "../showHistoryData.do?method=getData&reporttype=html&period=20&businessPeriod=oni";
/*      */     }
/*  999 */     request.setAttribute("reportURL", reportURL);
/* 1000 */     request.setAttribute("ess_atts", ess_atts);
/* 1001 */     request.setAttribute("tabledetails", table_details);
/* 1002 */     request.setAttribute("maxcollectiontime", String.valueOf(collectiontime));
/* 1003 */     return new ActionForward("/jsp/CustomDetails.jsp?baseid=" + baseid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ArrayList getArchivalAttributesList(String resourcetype)
/*      */   {
/* 1011 */     ArrayList al = new ArrayList();
/*      */     try
/*      */     {
/* 1014 */       String q1 = "select AM_ATTRIBUTES.ATTRIBUTEID from AM_ATTRIBUTES, AM_ATTRIBUTES_EXT WHERE  RESOURCETYPE='" + resourcetype + "' and AM_ATTRIBUTES_EXT.ISARCHIVEING=1 AND AM_ATTRIBUTES.ATTRIBUTEID= AM_ATTRIBUTES_EXT.ATTRIBUTEID";
/* 1015 */       System.out.println("The attributearchive query===" + q1);
/* 1016 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/* 1017 */       while (rs.next())
/*      */       {
/* 1019 */         al.add(rs.getString(1));
/*      */       }
/* 1021 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1025 */       exc.printStackTrace();
/*      */     }
/* 1027 */     return al;
/*      */   }
/*      */   
/*      */   public ArrayList getStringData(String baseid, String resourceid, long collectiontime, String resourcetype)
/*      */   {
/* 1032 */     ArrayList toreturn = new ArrayList();
/* 1033 */     String qry = "select ATTRIBUTEID,ATTRIBUTE,DISPLAYNAME FROM AM_ATTRIBUTES WHERE RESOURCETYPE LIKE '" + resourcetype + "' AND TYPE=3 AND ATTRIBUTE<>'ResponseTime' order by DISPLAYNAME";
/* 1034 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1035 */     ResultSet rs1 = null;
/* 1036 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1039 */       ArrayList attlist = new ArrayList();
/* 1040 */       String scalar_string = "AM_Script_String_Data_" + baseid;
/* 1041 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 1042 */       String data_qry = "select attributeid,value from " + scalar_string + " where resourceid=" + resourceid + " and collectiontime=" + collectiontime;
/* 1043 */       rs1 = AMConnectionPool.executeQueryStmt(data_qry);
/* 1044 */       Properties str_val = new Properties();
/* 1045 */       Properties str_disp = new Properties();
/* 1046 */       while (rs1.next())
/*      */       {
/* 1048 */         str_val.setProperty(rs1.getString(1), rs1.getString(2));
/*      */       }
/* 1050 */       while (rs.next())
/*      */       {
/* 1052 */         attlist.add(rs.getString(1));
/* 1053 */         str_disp.setProperty(rs.getString(1), rs.getString(2));
/*      */       }
/* 1055 */       toreturn.add(attlist);
/* 1056 */       toreturn.add(str_val);
/* 1057 */       toreturn.add(str_disp);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1061 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1065 */       closeResultSet(rs1);
/* 1066 */       closeResultSet(rs);
/*      */     }
/*      */     
/* 1069 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getNumericData(String baseid, String resourceid, long collectiontime, String resourcetype)
/*      */   {
/* 1075 */     ArrayList toreturn = new ArrayList();
/* 1076 */     String qry = "select ATTRIBUTEID,ATTRIBUTE,DISPLAYNAME FROM AM_ATTRIBUTES WHERE RESOURCETYPE LIKE '" + resourcetype + "' AND TYPE=0 AND ATTRIBUTE<>'ResponseTime' order by DISPLAYNAME";
/* 1077 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1078 */     ResultSet rs1 = null;
/* 1079 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1082 */       ArrayList attlist = new ArrayList();
/* 1083 */       String scalar_string = "AM_Script_Numeric_Data_" + baseid;
/* 1084 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 1085 */       String data_qry = "select attributeid,value from " + scalar_string + " where resourceid=" + resourceid + " and collectiontime=" + collectiontime;
/* 1086 */       rs1 = AMConnectionPool.executeQueryStmt(data_qry);
/* 1087 */       Properties str_val = new Properties();
/* 1088 */       Properties str_disp = new Properties();
/* 1089 */       while (rs1.next())
/*      */       {
/* 1091 */         str_val.setProperty(rs1.getString(1), rs1.getString(2));
/*      */       }
/* 1093 */       while (rs.next())
/*      */       {
/* 1095 */         attlist.add(rs.getString(1));
/* 1096 */         str_disp.setProperty(rs.getString(1), rs.getString(2));
/*      */       }
/* 1098 */       toreturn.add(attlist);
/* 1099 */       toreturn.add(str_val);
/* 1100 */       toreturn.add(str_disp);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1104 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1108 */       closeResultSet(rs1);
/* 1109 */       closeResultSet(rs);
/*      */     }
/* 1111 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getDataforResource(String baseid, String resourceid, long collectiontime)
/*      */   {
/* 1117 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1118 */     ArrayList alist = new ArrayList();
/* 1119 */     Properties p_num = new Properties();
/* 1120 */     Properties p_str = new Properties();
/* 1121 */     Properties p_conf = new Properties();
/* 1122 */     ResultSet rs_num = null;
/* 1123 */     ResultSet rs_str = null;
/* 1124 */     ResultSet rs_conf = null;
/*      */     try
/*      */     {
/* 1127 */       String qry = "select attributeid,value from AM_Script_Numeric_Data_" + baseid + " where resourceid=" + resourceid + " and collectiontime=" + collectiontime;
/* 1128 */       rs_num = AMConnectionPool.executeQueryStmt(qry);
/* 1129 */       while (rs_num.next())
/*      */       {
/* 1131 */         if (rs_num.getString(2) != null)
/*      */         {
/* 1133 */           p_num.setProperty(rs_num.getString(1), rs_num.getString(2));
/*      */         }
/*      */         else
/*      */         {
/* 1137 */           p_num.setProperty(rs_num.getString(1), "-");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1149 */         AMConnectionPool.closeStatement(rs_num);
/*      */       }
/*      */       catch (Exception e) {
/* 1152 */         AMLog.debug("Problem with closing result set");
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1158 */         String qry = "select attributeid,value from AM_Script_String_Data_" + baseid + " where resourceid=" + resourceid + " and collectiontime=" + collectiontime;
/* 1159 */         rs_str = AMConnectionPool.executeQueryStmt(qry);
/* 1160 */         while (rs_str.next())
/*      */         {
/* 1162 */           String value = rs_str.getString(2);
/* 1163 */           if (value == null) {
/* 1164 */             value = "-";
/*      */           }
/* 1166 */           p_str.setProperty(rs_str.getString(1), value);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1177 */           AMConnectionPool.closeStatement(rs_str);
/*      */         }
/*      */         catch (Exception e) {
/* 1180 */           AMLog.debug("Problem with closing result set");
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1186 */           String qry = "SELECT ATTRIBUTEID,CONFVALUE FROM AM_CONFIGURATION_INFO WHERE LATEST=1 AND RESOURCEID=" + resourceid;
/* 1187 */           rs_conf = AMConnectionPool.executeQueryStmt(qry);
/* 1188 */           while (rs_conf.next())
/*      */           {
/* 1190 */             if (rs_conf.getString(2) != null)
/*      */             {
/* 1192 */               p_conf.setProperty(rs_conf.getString(1), rs_conf.getString(2));
/*      */             }
/*      */             else
/*      */             {
/* 1196 */               p_conf.setProperty(rs_conf.getString(1), "-");
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/* 1208 */             AMConnectionPool.closeStatement(rs_conf);
/*      */           }
/*      */           catch (Exception e) {
/* 1211 */             AMLog.debug("Problem with closing result set");
/*      */           }
/*      */           
/* 1214 */           alist.add(p_num);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1202 */           exc.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1171 */         exc.printStackTrace();
/*      */       }
/*      */       finally {}
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1143 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1149 */         AMConnectionPool.closeStatement(rs_num);
/*      */       }
/*      */       catch (Exception e) {
/* 1152 */         AMLog.debug("Problem with closing result set");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1215 */     alist.add(p_str);
/* 1216 */     alist.add(p_conf);
/* 1217 */     return alist;
/*      */   }
/*      */   
/*      */   public ArrayList getGroupsAndData(String baseid, String resourceid, long collectiontime, ArrayList attlist, String resourcetype)
/*      */   {
/* 1222 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1223 */     ArrayList grouplist = new ArrayList();
/* 1224 */     Properties attname = (Properties)attlist.get(0);
/* 1225 */     Properties attdisp = (Properties)attlist.get(1);
/*      */     
/* 1227 */     if (resourcetype.equals("QueryMonitor")) {
/* 1228 */       baseid = resourceid;
/*      */     }
/* 1230 */     ArrayList datalist = getDataforResource(baseid, resourceid, collectiontime);
/* 1231 */     Properties p_num = (Properties)datalist.get(0);
/* 1232 */     Properties p_str = (Properties)datalist.get(1);
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1237 */       String qry = "select count(*) from AM_CUSTOM_MONITOR_GROUPS_CAPTION where TYPEID=" + baseid;
/* 1238 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1239 */       int gcount = 0;
/* 1240 */       if (rs.next())
/*      */       {
/* 1242 */         gcount = rs.getInt(1);
/*      */       }
/* 1244 */       for (int i = 1; i <= gcount; i++)
/*      */       {
/* 1246 */         HashMap group = new HashMap();
/*      */         try
/*      */         {
/* 1249 */           qry = "select CAPTION,FULLWIDTH,UNIT from AM_CUSTOM_MONITOR_GROUPS_CAPTION where typeid=" + baseid + " and GROUPID=" + i;
/* 1250 */           ResultSet rs0 = AMConnectionPool.executeQueryStmt(qry);
/* 1251 */           String caption = "";
/* 1252 */           String fullwidth = "0";
/* 1253 */           String gunit = "";
/* 1254 */           if (rs0.next())
/*      */           {
/* 1256 */             caption = rs0.getString("CAPTION");
/* 1257 */             fullwidth = rs0.getString("FULLWIDTH");
/* 1258 */             gunit = rs0.getString("UNIT");
/*      */           }
/* 1260 */           group.put("caption", caption);
/* 1261 */           group.put("fullwidth", fullwidth);
/* 1262 */           group.put("gunit", gunit);
/* 1263 */           qry = "select * from AM_CUSTOM_MONITOR_GROUPS_DEFINITION where typeid=" + baseid + " and GROUPID=" + i;
/* 1264 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 1265 */           int gtype = 1;
/* 1266 */           String attributeid = "-1";
/* 1267 */           String name = "";
/* 1268 */           String value = "-NA-";
/* 1269 */           LinkedHashMap data = new LinkedHashMap();
/* 1270 */           ArrayList graphlist = new ArrayList();
/* 1271 */           String gatts = "-1,";
/* 1272 */           String attidlist = "";
/* 1273 */           while (rs1.next())
/*      */           {
/* 1275 */             attributeid = rs1.getString("ATTRIBUTEID");
/* 1276 */             attidlist = attidlist + attributeid + ",";
/* 1277 */             if (rs1.getInt("GRAPH") != 0)
/*      */             {
/* 1279 */               gtype = rs1.getInt("GRAPH");
/* 1280 */               graphlist.add(attributeid);
/* 1281 */               gatts = gatts + attributeid + ",";
/*      */             }
/* 1283 */             name = attdisp.getProperty(attributeid);
/* 1284 */             if (p_num.containsKey(attributeid))
/*      */             {
/* 1286 */               if (p_num.getProperty(attributeid) != null) {
/* 1287 */                 value = p_num.getProperty(attributeid);
/*      */               }
/*      */               
/*      */             }
/* 1291 */             else if (p_str.getProperty(attributeid) != null) {
/* 1292 */               value = p_str.getProperty(attributeid);
/*      */             }
/* 1294 */             data.put(attributeid, value);
/*      */           }
/* 1296 */           gatts = gatts.substring(0, gatts.length() - 1);
/* 1297 */           group.put("attidlist", attidlist);
/* 1298 */           group.put("gatts", gatts);
/* 1299 */           group.put("gtype", String.valueOf(gtype));
/* 1300 */           group.put("graphs", graphlist);
/* 1301 */           group.put("data", data);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1305 */           exc.printStackTrace();
/*      */         }
/* 1307 */         grouplist.add(group);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1312 */       exc.printStackTrace();
/*      */     }
/* 1314 */     return grouplist;
/*      */   }
/*      */   
/*      */ 
/*      */   public ArrayList getTableValuesforScript(String scriptid, long collectiontime, HttpServletRequest request, String baseid, String original_type)
/*      */   {
/* 1320 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1321 */     String tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA";
/* 1322 */     String tabular_string = "AM_CAM_COLUMNAR_DATA";
/* 1323 */     boolean isTableHasNumeric = false;
/*      */     
/*      */     try
/*      */     {
/* 1327 */       Hashtable ht = new Hashtable();
/*      */       
/*      */ 
/* 1330 */       ArrayList tableids = new ArrayList();
/* 1331 */       Properties tabid_tabname = new Properties();
/* 1332 */       ArrayList tablenames = new ArrayList();
/* 1333 */       ArrayList toreturn = new ArrayList();
/* 1334 */       Hashtable table_resids = new Hashtable();
/* 1335 */       Hashtable table_rowsdisabled = new Hashtable();
/* 1336 */       String qry = "SELECT * FROM AM_SCRIPT_TABLES WHERE SCRIPTID in (" + scriptid + "," + baseid + ") order by TABLEID";
/* 1337 */       if ((!baseid.equals("-1")) && (!original_type.equals("QueryMonitor")))
/*      */       {
/* 1339 */         qry = "SELECT * FROM AM_SCRIPT_TABLES WHERE SCRIPTID=" + baseid;
/*      */       }
/* 1341 */       if ((!baseid.equals("-1")) && (original_type.equals("QueryMonitor")))
/*      */       {
/* 1343 */         qry = "SELECT * FROM AM_SCRIPT_TABLES WHERE ERROR not like 'TOVALIDATE' AND SCRIPTID in (" + scriptid + "," + baseid + ") order by TABLEID";
/*      */       }
/*      */       
/* 1346 */       if (!baseid.equals("-1")) {
/* 1347 */         tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/* 1348 */         tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/* 1349 */         if (original_type.equals("QueryMonitor"))
/*      */         {
/* 1351 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptid;
/* 1352 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + scriptid;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1357 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1358 */       Hashtable table_health = new Hashtable();
/* 1359 */       Hashtable table_avail = new Hashtable();
/* 1360 */       Hashtable primary_col = new Hashtable();
/*      */       
/* 1362 */       Properties tableerror = new Properties();
/* 1363 */       ActionErrors errors = new ActionErrors();
/* 1364 */       boolean error_counter = true;
/* 1365 */       while (rs.next())
/*      */       {
/* 1367 */         tableids.add(rs.getString("TABLEID"));
/* 1368 */         tablenames.add(rs.getString("TABLENAME"));
/* 1369 */         primary_col.put(rs.getString("TABLEID"), rs.getString("PRIMARY_COLUMN"));
/*      */         
/*      */ 
/* 1372 */         if ((rs.getString("PRIMARY_COLUMN").trim().equals("")) && (original_type != null) && (original_type.equals("QueryMonitor")))
/*      */         {
/*      */ 
/* 1375 */           String message = FormatUtil.getString("am.webclient.querymonitor.message.primkey", new String[] { rs.getString("TABLENAME") });
/* 1376 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(message));
/*      */         }
/*      */         
/*      */ 
/* 1380 */         tabid_tabname.setProperty(rs.getString("TABLEID"), rs.getString("TABLENAME"));
/* 1381 */         String error = rs.getString("ERROR");
/* 1382 */         tableerror.setProperty(rs.getString("TABLENAME"), rs.getString("ERROR"));
/* 1383 */         if ((error != null) && (!error.trim().equals("")) && (original_type != null) && (!original_type.equals("QueryMonitor")))
/*      */         {
/* 1385 */           if (error.equals("TOVALIDATE"))
/*      */           {
/* 1387 */             if (error_counter)
/*      */             {
/* 1389 */               String msg1 = FormatUtil.getString("am.scriptdetails.tables.generalerrror");
/* 1390 */               errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg1));
/* 1391 */               error_counter = false;
/*      */             }
/* 1393 */             System.out.println("The " + rs.getString("TABLENAME") + " is not present in the output file");
/* 1394 */             String msg = FormatUtil.getString("am.scriptdetails.table.notpresent", new String[] { rs.getString("TABLENAME") });
/* 1395 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg));
/*      */           }
/* 1397 */           if (error.equals("STARTED"))
/*      */           {
/* 1399 */             if (error_counter)
/*      */             {
/* 1401 */               String msg1 = FormatUtil.getString("am.scriptdetails.tables.generalerrror");
/* 1402 */               errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg1));
/* 1403 */               error_counter = false;
/*      */             }
/* 1405 */             System.out.println("The table " + rs.getString("TABLENAME") + " does not seems to be closed by the proper close tag in the output file");
/* 1406 */             String msg = FormatUtil.getString("am.scriptdetails.table.notclosed", new String[] { rs.getString("TABLENAME") });
/* 1407 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg));
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1412 */       saveErrors(request, errors);
/* 1413 */       if ((tableids != null) && (tableids.size() == 0))
/*      */       {
/* 1415 */         request.setAttribute("isTableHasNumeric", "false");
/* 1416 */         return new ArrayList();
/*      */       }
/* 1418 */       for (int i = 0; i < tableids.size(); i++)
/*      */       {
/*      */ 
/*      */         try
/*      */         {
/*      */ 
/*      */ 
/* 1425 */           String qry_hth = "select ATTRIBUTEID,TYPE from AM_CAM_DC_ATTRIBUTES WHERE GROUPID=" + tableids.get(i) + " AND (TYPE=2 OR TYPE=1)";
/*      */           
/* 1427 */           ResultSet rs_tid = AMConnectionPool.executeQueryStmt(qry_hth);
/* 1428 */           String table_health_id = "-1";
/* 1429 */           String table_avail_id = "-1";
/* 1430 */           while (rs_tid.next())
/*      */           {
/* 1432 */             if ((rs_tid.getString("TYPE") != null) && (rs_tid.getString("TYPE").equals("2")))
/*      */             {
/* 1434 */               table_health_id = rs_tid.getString("ATTRIBUTEID");
/* 1435 */               table_health.put(tableids.get(i), table_health_id);
/*      */             }
/*      */             else
/*      */             {
/* 1439 */               table_avail_id = rs_tid.getString("ATTRIBUTEID");
/* 1440 */               table_avail.put(tableids.get(i), table_avail_id);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1447 */           exc.printStackTrace();
/*      */         }
/*      */       }
/* 1450 */       Hashtable table_attids = new Hashtable();
/* 1451 */       Hashtable table_attid_name = new Hashtable();
/* 1452 */       ArrayList atts_res_value = new ArrayList();
/*      */       
/* 1454 */       String res_qry = "select resourceid,resourcename,dcstarted from AM_PARENTCHILDMAPPER, AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid=" + scriptid + " and  AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid order by resourceid,dcstarted";
/* 1455 */       ResultSet res_ids = AMConnectionPool.executeQueryStmt(res_qry);
/* 1456 */       ArrayList row_id = new ArrayList();
/* 1457 */       ArrayList row_name = new ArrayList();
/* 1458 */       ArrayList row_disable = new ArrayList();
/* 1459 */       while (res_ids.next())
/*      */       {
/* 1461 */         row_id.add(res_ids.getString(1));
/* 1462 */         row_name.add(res_ids.getString(2));
/* 1463 */         row_disable.add(Integer.valueOf(res_ids.getInt(3)));
/*      */       }
/* 1465 */       Hashtable table_data = new Hashtable();
/* 1466 */       Hashtable attid_details = new Hashtable();
/* 1467 */       Hashtable table_avail_data = new Hashtable();
/* 1468 */       for (int i = 0; i < tableids.size(); i++)
/*      */       {
/* 1470 */         if ((original_type != null) && (original_type.equals("QueryMonitor")) && (tablenames.get(i).toString().equals("Execution Time")))
/*      */         {
/* 1472 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/* 1473 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/*      */         }
/* 1475 */         else if ((original_type != null) && (original_type.equals("QueryMonitor")))
/*      */         {
/* 1477 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptid;
/* 1478 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + scriptid;
/*      */         }
/* 1480 */         ArrayList attributes = new ArrayList();
/* 1481 */         ArrayList order_list = new ArrayList();
/* 1482 */         String att_qry = "SELECT ATTRIBUTEID,ATTRIBUTENAME,TYPE,DISPLAYTYPE FROM AM_CAM_DC_ATTRIBUTES WHERE GROUPID=" + tableids.get(i) + " and TYPE <>2";
/* 1483 */         Hashtable ht_col_data = new Hashtable();
/* 1484 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(att_qry);
/* 1485 */         ArrayList name_type = new ArrayList();
/* 1486 */         while (rs1.next())
/*      */         {
/*      */ 
/* 1489 */           attributes.add(rs1.getString("ATTRIBUTEID"));
/* 1490 */           name_type = new ArrayList();
/* 1491 */           name_type.add(rs1.getString("ATTRIBUTENAME"));
/* 1492 */           name_type.add(rs1.getString("TYPE"));
/* 1493 */           if (!isTableHasNumeric)
/*      */           {
/* 1495 */             if (rs1.getString("TYPE").equals("0"))
/*      */             {
/* 1497 */               isTableHasNumeric = true;
/*      */             }
/*      */           }
/* 1500 */           name_type.add(rs1.getString("DISPLAYTYPE"));
/*      */           
/* 1502 */           attid_details.put(rs1.getString("ATTRIBUTEID"), name_type);
/* 1503 */           order_list.add(rs1.getString("ATTRIBUTEID"));
/*      */         }
/*      */         
/* 1506 */         ArrayList order_primary = new ArrayList();
/* 1507 */         StringTokenizer st = new StringTokenizer((String)primary_col.get(tableids.get(i)), "#");
/* 1508 */         while (st.hasMoreTokens())
/*      */         {
/* 1510 */           String attqry = "SELECT ATTRIBUTEID from AM_CAM_DC_ATTRIBUTES where  GROUPID=" + tableids.get(i) + " and ATTRIBUTENAME='" + st.nextToken() + "'";
/*      */           
/*      */ 
/*      */           try
/*      */           {
/* 1515 */             ResultSet rsatt = AMConnectionPool.executeQueryStmt(attqry);
/* 1516 */             if (rsatt.next())
/*      */             {
/*      */ 
/*      */ 
/* 1520 */               order_primary.add(rsatt.getString("ATTRIBUTEID"));
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1526 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1532 */         int z = 0;
/* 1533 */         for (int x = 0; x < order_primary.size(); x++)
/*      */         {
/* 1535 */           for (int y = 0; y < order_list.size(); y++)
/*      */           {
/* 1537 */             if (order_primary.get(x).equals(order_list.get(y)))
/*      */             {
/*      */ 
/* 1540 */               order_list.remove(y);
/* 1541 */               order_list.add(z, order_primary.get(x));
/* 1542 */               z++;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1551 */         ht_col_data.put("column", order_list);
/* 1552 */         ArrayList rowids = new ArrayList();
/* 1553 */         ArrayList rows_disabled = new ArrayList();
/* 1554 */         String tempRowId = null;
/* 1555 */         int tempRowIdVal = 0;
/*      */         
/* 1557 */         for (int c = 0; c < row_id.size(); c++)
/*      */         {
/* 1559 */           if (row_name.get(c).toString().startsWith(tablenames.get(i) + "_"))
/*      */           {
/* 1561 */             rowids.add(row_id.get(c));
/* 1562 */             if (((Integer)row_disable.get(c)).intValue() == 5) {
/* 1563 */               rows_disabled.add(row_id.get(c));
/*      */             }
/* 1565 */             if (tempRowIdVal == 0)
/*      */             {
/* 1567 */               tempRowId = (String)row_id.get(c);
/* 1568 */               tempRowIdVal++;
/*      */             }
/*      */             else
/*      */             {
/* 1572 */               tempRowId = tempRowId + "','" + (String)row_id.get(c);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1578 */         table_resids.put(tableids.get(i), rowids);
/* 1579 */         table_rowsdisabled.put(tableids.get(i), rows_disabled);
/*      */         
/* 1581 */         table_attids.put(tablenames.get(i), attributes);
/*      */         
/* 1583 */         Hashtable att_values = new Hashtable();
/* 1584 */         Hashtable data = new Hashtable();
/*      */         
/*      */ 
/*      */ 
/* 1588 */         ArrayList tempRowIdList = new ArrayList();
/*      */         ArrayList idListToBeDeleted;
/* 1590 */         if (tempRowId != null)
/*      */         {
/* 1592 */           String disabled_Dqry = "select * from AM_SCRIPT_ROWS WHERE ROWID IN ('" + tempRowId + "')";
/* 1593 */           ResultSet dis_rs = AMConnectionPool.executeQueryStmt(disabled_Dqry);
/* 1594 */           while (dis_rs.next()) {
/* 1595 */             Hashtable dis_att_values = new Hashtable();
/* 1596 */             String rowid = dis_rs.getString(1);
/* 1597 */             String data1 = dis_rs.getString(2);
/* 1598 */             StringTokenizer st1 = new StringTokenizer(data1, "#");
/* 1599 */             while (st1.hasMoreElements()) {
/* 1600 */               StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "$");
/* 1601 */               String attid = st2.hasMoreElements() ? st2.nextToken() : "";
/* 1602 */               String value = st2.hasMoreElements() ? st2.nextToken() : "";
/* 1603 */               dis_att_values.put(attid, value);
/*      */             }
/* 1605 */             data.put(rowid, dis_att_values);
/*      */           }
/* 1607 */           closeResultSet(dis_rs);
/*      */           
/* 1609 */           String str_qry = "select ATTRIBUTEID,VALUE,ROWID from " + tabular_string + " where ROWID in ('" + tempRowId + "') and COLLECTIONTIME=" + collectiontime;
/* 1610 */           Hashtable ht1 = new Hashtable();
/* 1611 */           ResultSet str_rs = AMConnectionPool.executeQueryStmt(str_qry);
/* 1612 */           boolean table_availibility = false;
/* 1613 */           while (str_rs.next())
/*      */           {
/* 1615 */             String rowid_str = str_rs.getString("ROWID");
/* 1616 */             if (str_rs.getString("ATTRIBUTEID").equals((String)table_avail.get(tableids.get(i))))
/*      */             {
/* 1618 */               table_availibility = true;
/*      */             }
/* 1620 */             if (data.containsKey(rowid_str))
/*      */             {
/* 1622 */               Hashtable h = (Hashtable)data.get(rowid_str);
/* 1623 */               h.put(str_rs.getString("ATTRIBUTEID"), str_rs.getString("VALUE"));
/* 1624 */               data.put(rowid_str, h);
/*      */             }
/*      */             else
/*      */             {
/* 1628 */               Hashtable attid_val = new Hashtable();
/* 1629 */               attid_val.put(str_rs.getString("ATTRIBUTEID"), str_rs.getString("VALUE"));
/* 1630 */               data.put(rowid_str, attid_val);
/* 1631 */               tempRowIdList.add(rowid_str);
/*      */             }
/*      */           }
/*      */           
/* 1635 */           if (table_availibility) {
/* 1636 */             table_avail_data.put((String)tableids.get(i), "YES");
/*      */           } else {
/* 1638 */             table_avail_data.put((String)tableids.get(i), "NO");
/*      */           }
/* 1640 */           String num_qry = "select ATTRIBUTEID,VALUE,RESID from " + tabular_numeric + " where RESID in ('" + tempRowId + "') and COLLECTIONTIME=" + collectiontime;
/*      */           
/* 1642 */           ResultSet num_rs = AMConnectionPool.executeQueryStmt(num_qry);
/*      */           
/* 1644 */           while (num_rs.next())
/*      */           {
/* 1646 */             if (data.containsKey(num_rs.getString("RESID")))
/*      */             {
/* 1648 */               Hashtable h = (Hashtable)data.get(num_rs.getString("RESID"));
/* 1649 */               String value = num_rs.getString("VALUE");
/* 1650 */               if (value.contains(".")) {
/* 1651 */                 value = DBUtil.getFloatasString(value);
/*      */               }
/* 1653 */               h.put(num_rs.getString("ATTRIBUTEID"), value);
/* 1654 */               data.put(num_rs.getString("RESID"), h);
/*      */             }
/*      */             else
/*      */             {
/* 1658 */               Hashtable resid_val = new Hashtable();
/* 1659 */               String value = num_rs.getString("VALUE");
/* 1660 */               if (value.contains(".")) {
/* 1661 */                 value = DBUtil.getFloatasString(value);
/*      */               }
/* 1663 */               resid_val.put(num_rs.getString("ATTRIBUTEID"), value);
/* 1664 */               data.put(num_rs.getString("RESID"), resid_val);
/* 1665 */               tempRowIdList.add(num_rs.getString("RESID"));
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1670 */           for (Object removeId : rowids)
/*      */           {
/* 1672 */             table_resids.remove((String)removeId);
/*      */           }
/*      */           
/* 1675 */           idListToBeDeleted = new ArrayList();
/* 1676 */           for (Object tempRowIdsToDelete : rowids)
/*      */           {
/* 1678 */             if (!tempRowIdList.contains((String)tempRowIdsToDelete))
/*      */             {
/* 1680 */               idListToBeDeleted.add((String)tempRowIdsToDelete);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1688 */         ht_col_data.put("data", data);
/* 1689 */         table_data.put(tableids.get(i), ht_col_data);
/*      */       }
/* 1691 */       toreturn.add(tableids);
/* 1692 */       toreturn.add(table_resids);
/* 1693 */       toreturn.add(tabid_tabname);
/* 1694 */       toreturn.add(attid_details);
/* 1695 */       toreturn.add(table_data);
/* 1696 */       toreturn.add(table_health);
/* 1697 */       toreturn.add(table_avail);
/* 1698 */       toreturn.add(table_avail_data);
/* 1699 */       toreturn.add(table_rowsdisabled);
/* 1700 */       request.setAttribute("isTableHasNumeric", String.valueOf(isTableHasNumeric));
/* 1701 */       return toreturn;
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1705 */       exc.printStackTrace();
/*      */       
/* 1707 */       request.setAttribute("isTableHasNumeric", "false"); }
/* 1708 */     return new ArrayList();
/*      */   }
/*      */   
/*      */   public ArrayList getAttributesList(String baseid)
/*      */   {
/* 1713 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1714 */     String qry = "select ATTRIBUTEID,ATTRIBUTE,DISPLAYNAME,UNITS FROM AM_ATTRIBUTES,AM_MONITOR_TYPES where TYPEID=" + baseid + " and TYPENAME=RESOURCETYPE";
/* 1715 */     System.out.println("The attributes qry====>" + qry);
/* 1716 */     ArrayList al = new ArrayList();
/* 1717 */     Properties attname = new Properties();
/* 1718 */     Properties attdisp = new Properties();
/* 1719 */     Properties attunit = new Properties();
/*      */     try
/*      */     {
/* 1722 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1723 */       while (rs.next())
/*      */       {
/* 1725 */         attname.setProperty(rs.getString(1), rs.getString(2));
/* 1726 */         attdisp.setProperty(rs.getString(1), rs.getString(3));
/* 1727 */         if ((rs.getString(4) != null) && (!rs.getString(4).equals("")) && (!rs.getString(4).equals("-"))) {
/* 1728 */           attunit.setProperty(rs.getString(1), "(" + rs.getString(4) + ")");
/*      */         } else
/* 1730 */           attunit.setProperty(rs.getString(1), "");
/*      */       }
/* 1732 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1736 */       exc.printStackTrace();
/*      */     }
/* 1738 */     al.add(attname);
/* 1739 */     al.add(attdisp);
/* 1740 */     al.add(attunit);
/* 1741 */     return al;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward myAction2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1748 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */   private void closeResultSet(ResultSet rs)
/*      */   {
/* 1753 */     AMConnectionPool.closeStatement(rs);
/*      */   }
/*      */   
/*      */   public HashMap getRowDetails(String scriptId, long collectiontime, String baseid, String original_type, long pl) throws SQLException
/*      */   {
/* 1758 */     HashMap rowDetails = new HashMap();
/* 1759 */     ArrayList row_ids = new ArrayList();
/* 1760 */     HashMap row_names = new HashMap();
/* 1761 */     HashMap row_creationtime = new HashMap();
/* 1762 */     ArrayList dcStatus = new ArrayList();
/* 1763 */     ArrayList row_disable = new ArrayList();
/* 1764 */     ArrayList firstDCNotComplete = new ArrayList();
/* 1765 */     HashMap primaryColValues = new HashMap();
/* 1766 */     String tempRowId = null;
/* 1767 */     int tempRowIdVal = 0;
/* 1768 */     String tabular_numeric = "";
/* 1769 */     String tabular_string = "";
/* 1770 */     if (!baseid.equals("-1")) {
/* 1771 */       tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/* 1772 */       tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/* 1773 */       if (original_type.equals("QueryMonitor"))
/*      */       {
/* 1775 */         tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptId;
/* 1776 */         tabular_string = "AM_CAM_COLUMNAR_DATA_" + scriptId;
/*      */       }
/*      */     }
/*      */     
/* 1780 */     boolean isTablesAvailable = false;
/* 1781 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1782 */     String res_qry = "select resourceid,resourcename,dcstarted,DISPLAYNAME,CREATIONTIME from AM_PARENTCHILDMAPPER, AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid=" + scriptId + " and  AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid order by resourceid,dcstarted";
/* 1783 */     ResultSet res_ids = null;
/*      */     try
/*      */     {
/* 1786 */       res_ids = AMConnectionPool.executeQueryStmt(res_qry);
/* 1787 */       while (res_ids.next()) {
/* 1788 */         row_ids.add(res_ids.getString(1));
/* 1789 */         row_names.put(res_ids.getString(1), res_ids.getString(2));
/* 1790 */         dcStatus.add(Integer.valueOf(res_ids.getInt(3)));
/* 1791 */         primaryColValues.put(res_ids.getString(1), res_ids.getString(4));
/* 1792 */         row_creationtime.put(res_ids.getString(1), res_ids.getString(5));
/* 1793 */         isTablesAvailable = true;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1799 */       AMLog.debug("Some other problem in collecting rowIds");
/* 1800 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1803 */       res_ids.close();
/*      */     }
/*      */     
/* 1806 */     if (isTablesAvailable) {
/* 1807 */       rowDetails.put("row_ids", row_ids);
/* 1808 */       rowDetails.put("row_names", row_names);
/*      */       
/* 1810 */       Hashtable numericData = new Hashtable();
/* 1811 */       Hashtable stringData = new Hashtable();
/* 1812 */       String str_qry = "";
/* 1813 */       String num_qry = "";
/* 1814 */       for (int c = 0; c < row_ids.size(); c++) {
/* 1815 */         if (((Integer)dcStatus.get(c)).intValue() == 5) {
/* 1816 */           row_disable.add(row_ids.get(c));
/*      */         }
/*      */         
/* 1819 */         String rowName = (String)row_names.get((String)row_ids.get(c));
/* 1820 */         if ((original_type.equals("QueryMonitor")) && (rowName.startsWith("Execution Time_"))) {
/* 1821 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptId;
/* 1822 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/*      */         }
/* 1824 */         else if ((original_type.equals("QueryMonitor")) && (!rowName.startsWith("Execution Time_"))) {
/* 1825 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptId;
/* 1826 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + scriptId;
/*      */         }
/*      */         else {
/* 1829 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/* 1830 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/*      */         }
/* 1832 */         str_qry = "select ATTRIBUTEID,VALUE,ROWID from " + tabular_string + " where ROWID = '" + (String)row_ids.get(c) + "' and COLLECTIONTIME=" + collectiontime;
/*      */         
/*      */ 
/* 1835 */         ResultSet str_rs = null;
/*      */         try {
/* 1837 */           str_rs = AMConnectionPool.executeQueryStmt(str_qry);
/* 1838 */           Hashtable attid_val = new Hashtable();
/* 1839 */           while (str_rs.next()) {
/* 1840 */             attid_val.put(str_rs.getString("ATTRIBUTEID"), str_rs.getString("VALUE"));
/*      */           }
/* 1842 */           stringData.put((String)row_ids.get(c), attid_val);
/*      */         }
/*      */         catch (Exception e) {
/* 1845 */           AMLog.debug("Some problem in collecting values of Table String Attributes from " + tabular_string + " table");
/* 1846 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/* 1849 */           str_rs.close();
/*      */         }
/*      */         
/* 1852 */         num_qry = "select ATTRIBUTEID,VALUE,RESID from " + tabular_numeric + " where RESID = '" + (String)row_ids.get(c) + "' and COLLECTIONTIME=" + collectiontime;
/*      */         
/* 1854 */         ResultSet num_rs = null;
/*      */         try {
/* 1856 */           num_rs = AMConnectionPool.executeQueryStmt(num_qry);
/* 1857 */           Hashtable resid_val = new Hashtable();
/* 1858 */           while (num_rs.next()) {
/* 1859 */             resid_val.put(num_rs.getString("ATTRIBUTEID"), num_rs.getString("VALUE"));
/*      */           }
/*      */           
/* 1862 */           numericData.put((String)row_ids.get(c), resid_val);
/*      */         }
/*      */         catch (Exception e) {
/* 1865 */           AMLog.debug("Some problem in collecting values of Table Numeric Attributes from " + tabular_numeric + " table");
/* 1866 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/* 1869 */           num_rs.close();
/*      */         }
/*      */         
/* 1872 */         String col_qry = "select COLLECTIONTIME from AM_ManagedObjectData where RESID=" + (String)row_ids.get(c);
/*      */         
/* 1874 */         ResultSet col_rs = null;
/*      */         try {
/* 1876 */           col_rs = AMConnectionPool.executeQueryStmt(col_qry);
/* 1877 */           Hashtable resid_colval = new Hashtable();
/* 1878 */           if (!col_rs.next())
/*      */           {
/*      */ 
/* 1881 */             long curTime = System.currentTimeMillis();
/*      */             
/* 1883 */             if (curTime - Long.parseLong("" + row_creationtime.get((String)row_ids.get(c))) < pl)
/*      */             {
/* 1885 */               firstDCNotComplete.add(primaryColValues.get((String)row_ids.get(c)));
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1893 */           AMLog.debug("Some problem in collecting values of Collection Time");
/* 1894 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/* 1897 */           col_rs.close();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1904 */       rowDetails.put("row_disable", row_disable);
/* 1905 */       rowDetails.put("primaryColValues", primaryColValues);
/* 1906 */       rowDetails.put("stringData", stringData);
/* 1907 */       rowDetails.put("numericData", numericData);
/* 1908 */       rowDetails.put("firstDCNotComplete", firstDCNotComplete);
/*      */     }
/* 1910 */     return rowDetails;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public HashMap getManagedObjectIds(String resourcetype, String resourceid, List groupList, String baseid)
/*      */     throws SQLException
/*      */   {
/* 1918 */     HashMap moAsRows = null;
/* 1919 */     ArrayList rowIds = new ArrayList();
/* 1920 */     HashMap displayNames = new HashMap();
/* 1921 */     HashMap dataTables = new HashMap();
/* 1922 */     HashMap tableValues = new HashMap();
/* 1923 */     HashMap groupDetails = new HashMap();
/* 1924 */     HashMap managedObjectValues = new HashMap();
/* 1925 */     String category = "";
/* 1926 */     String caption = "";
/* 1927 */     String columnType = "";
/* 1928 */     ArrayList attributesList = new ArrayList();
/* 1929 */     String tableType = "";
/* 1930 */     String tempAttributeId = null;
/* 1931 */     String tempRows = null;
/* 1932 */     String col_name = null;
/* 1933 */     String moattributeId = null;
/* 1934 */     HashMap columnDetails = new HashMap();
/* 1935 */     HashMap attributeDetails = new HashMap();
/* 1936 */     HashMap moRowsforTable = new HashMap();
/* 1937 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1938 */     String isAgentEnabled = "NO";
/* 1939 */     Properties discoverPageUIDetails = ConfMonitorConfiguration.getInstance().getTypeDescription(resourcetype);
/* 1940 */     if ((discoverPageUIDetails != null) && (discoverPageUIDetails.getProperty("IS-AGENT-ENABLED") != null)) {
/* 1941 */       isAgentEnabled = discoverPageUIDetails.getProperty("IS-AGENT-ENABLED");
/*      */     }
/* 1943 */     for (int grpct = 0; grpct < groupList.size(); grpct++) {
/* 1944 */       groupDetails = (HashMap)groupList.get(grpct);
/* 1945 */       category = (String)groupDetails.get("CATEGORY");
/* 1946 */       if (category.equals("UITABLE")) {
/* 1947 */         caption = (String)groupDetails.get("Name");
/*      */         try {
/* 1949 */           tableType = (String)groupDetails.get("ResourceType");
/*      */ 
/*      */         }
/*      */         catch (NullPointerException e)
/*      */         {
/* 1954 */           e.printStackTrace();
/*      */         }
/*      */         catch (Exception e) {
/* 1957 */           e.printStackTrace();
/*      */         }
/*      */         
/* 1960 */         if ((tableType != null) && (!tableType.equals("")))
/*      */         {
/* 1962 */           moAsRows = new HashMap();
/* 1963 */           attributesList = (ArrayList)groupDetails.get("COLUMNLIST");
/* 1964 */           String attributeId = "";
/* 1965 */           for (int i = 0; i < attributesList.size(); i++)
/*      */           {
/* 1967 */             columnDetails = (HashMap)attributesList.get(i);
/* 1968 */             columnType = (String)columnDetails.get("TYPE");
/*      */             
/* 1970 */             if (columnType.equals("MO")) {
/* 1971 */               String res_qry = "select resourceid,resourcename,Displayname,dcstarted from AM_PARENTCHILDMAPPER, AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid=" + resourceid + " and  AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid  and TYPE='" + tableType + "' order by resourceid,dcstarted";
/*      */               
/*      */ 
/*      */ 
/* 1975 */               ResultSet res_ids = null;
/* 1976 */               StringBuffer strBuf = new StringBuffer();
/*      */               try {
/* 1978 */                 res_ids = AMConnectionPool.executeQueryStmt(res_qry);
/* 1979 */                 while (res_ids.next())
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/* 1984 */                   displayNames.put(res_ids.getString("resourceid"), res_ids.getString("Displayname"));
/*      */                   
/*      */ 
/* 1987 */                   rowIds.add(res_ids.getString("resourceid"));
/* 1988 */                   strBuf.append(res_ids.getString("resourceid") + ",");
/*      */                 }
/* 1990 */                 moAsRows.put("DisplayNames", displayNames);
/*      */                 
/* 1992 */                 moAsRows.put("RowIds", rowIds);
/* 1993 */                 if ((strBuf != null) && (strBuf.length() > 0)) {
/* 1994 */                   strBuf.deleteCharAt(strBuf.length() - 1);
/*      */                 }
/* 1996 */                 moAsRows.put("UnManagedMos", getUnmanagedMos(strBuf.toString()));
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 2000 */                 e.printStackTrace();
/*      */               }
/*      */               finally {
/* 2003 */                 res_ids.close();
/*      */               }
/*      */             }
/* 2006 */             else if (columnType.equals("RAW"))
/*      */             {
/*      */               try {
/* 2009 */                 attributeDetails = (HashMap)columnDetails.get("ATTRIBUTES");
/* 2010 */                 attributeId = (String)attributeDetails.get("ATTRIBUTEID");
/*      */                 
/* 2012 */                 col_name = (String)attributeDetails.get("DISPLAY");
/*      */ 
/*      */               }
/*      */               catch (NullPointerException e)
/*      */               {
/* 2017 */                 e.printStackTrace();
/*      */               }
/*      */               catch (Exception e) {
/* 2020 */                 e.printStackTrace();
/*      */               }
/* 2022 */               if (tempAttributeId != null)
/*      */               {
/* 2024 */                 tempAttributeId = tempAttributeId + "','" + attributeId;
/*      */               }
/*      */               else
/*      */               {
/* 2028 */                 tempAttributeId = attributeId;
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2034 */           String getTables = "select ATTRIBUTEID, DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID in  ('" + tempAttributeId + "')";
/* 2035 */           ResultSet get_Tables = null;
/* 2036 */           HashMap attribute_values = new HashMap();
/* 2037 */           HashMap attribute_table = new HashMap();
/* 2038 */           HashMap resid_val = new HashMap();
/* 2039 */           String getValues = "";
/*      */           try {
/* 2041 */             get_Tables = AMConnectionPool.executeQueryStmt(getTables);
/* 2042 */             for (int j = 0; j < rowIds.size(); j++) {
/* 2043 */               get_Tables.beforeFirst();
/* 2044 */               attribute_values = new HashMap();
/* 2045 */               while (get_Tables.next())
/*      */               {
/* 2047 */                 String tableName = get_Tables.getString("DATATABLE");
/* 2048 */                 String attributeName = get_Tables.getString("VALUE_COL");
/* 2049 */                 if ((tableName.equals("AM_ManagedObjectData")) && (attributeName.equals("RESPONSETIME")))
/*      */                 {
/*      */ 
/*      */ 
/* 2053 */                   tableName = "AM_Script_Numeric_Data_" + baseid;
/*      */                 }
/*      */                 
/* 2056 */                 if ((!tableName.equals("AM_ManagedObjectData")) || (attributeName.equals("RESPONSETIME"))) {
/* 2057 */                   ResultSet get_Values = null;
/*      */                   try {
/* 2059 */                     getValues = DBQueryUtil.getTopNValues("select RESOURCEID,ATTRIBUTEID,VALUE from " + tableName + " where ATTRIBUTEID=" + get_Tables.getString("ATTRIBUTEID") + " and RESOURCEID=" + (String)rowIds.get(j) + " order by COLLECTIONTIME desc", "1");
/*      */                     
/* 2061 */                     get_Values = AMConnectionPool.executeQueryStmt(getValues);
/* 2062 */                     if (get_Values.first()) {
/* 2063 */                       attribute_values.put(get_Tables.getString("ATTRIBUTEID"), get_Values.getString("VALUE"));
/*      */                     }
/* 2065 */                     attribute_table.put(get_Tables.getString("ATTRIBUTEID"), tableName);
/*      */                   }
/*      */                   catch (Exception e)
/*      */                   {
/* 2069 */                     e.printStackTrace();
/*      */                   }
/*      */                   finally {}
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 2078 */               resid_val.put((String)rowIds.get(j), attribute_values);
/*      */             }
/* 2080 */             moAsRows.put("resid_val", resid_val);
/* 2081 */             moAsRows.put("DataTableOfAtts", attribute_table);
/*      */           }
/*      */           catch (Exception e) {
/* 2084 */             AMLog.debug("The errormessage:" + e.getMessage());
/* 2085 */             e.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/* 2089 */             get_Tables.close();
/*      */           }
/* 2091 */           moRowsforTable.put(Integer.toString(grpct), moAsRows);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2097 */     return moRowsforTable;
/*      */   }
/*      */   
/* 2100 */   public String getHostName(String resourceType, String resourceid) throws SQLException { return ConfMonitorUtil.getHostName(resourceType, resourceid); }
/*      */   
/*      */   public Properties getHostOs(String hostIp) throws SQLException
/*      */   {
/* 2104 */     ResultSet rs = null;
/* 2105 */     String hostOs = "";
/* 2106 */     String hostId = "";
/* 2107 */     Properties hostData = new Properties();
/* 2108 */     String qry = "select RESOURCETYPE,RESOURCEID from AM_ManagedObject,CollectData where AM_ManagedObject.resourcename=CollectData.resourcename and TARGETADDRESS='" + hostIp + "' and COMPONENTNAME='HOST' and TYPE=RESOURCETYPE";
/*      */     try
/*      */     {
/* 2111 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(qry);
/* 2112 */       if (rs.next()) {
/* 2113 */         hostOs = rs.getString("RESOURCETYPE");
/* 2114 */         hostId = rs.getString("RESOURCEID");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2120 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2123 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2125 */     hostData.setProperty("HOST-TYPE", hostOs);
/* 2126 */     hostData.setProperty("HOST-ID", hostId);
/*      */     
/* 2128 */     return hostData;
/*      */   }
/*      */   
/*      */   public void monitorInformationValue(LinkedHashMap monitorInformation, String resourcetype, String resourceid, String collectionTime) {
/* 2132 */     if ((monitorInformation != null) && (monitorInformation.size() > 0)) {
/* 2133 */       String typeId = Constants.getTypeId(resourcetype);
/* 2134 */       Set dataset = monitorInformation.keySet();
/* 2135 */       Iterator it = dataset.iterator();
/* 2136 */       StringBuffer attributesList = new StringBuffer();
/* 2137 */       StringBuffer argumentsList = new StringBuffer();
/* 2138 */       StringBuffer configattributeIdList = new StringBuffer();
/* 2139 */       String specialChar = ConfMonitorUtil.getSpecialCharToAppend();
/* 2140 */       HashMap monitorInfoDetails = new HashMap();
/* 2141 */       boolean isConfAttAvailable = false;
/* 2142 */       ArrayList completeConfigAttList = ConfMonitorConfiguration.getInstance().getConfigidsList(resourcetype);
/*      */       
/* 2144 */       while (it.hasNext()) {
/* 2145 */         String infoKey = (String)it.next();
/* 2146 */         monitorInfoDetails = (HashMap)monitorInformation.get(infoKey);
/* 2147 */         if (((String)monitorInfoDetails.get("INFO-TYPE")).equals("FROM-TABLE")) {
/* 2148 */           if (argumentsList.length() > 0) {
/* 2149 */             argumentsList.append(",");
/*      */           }
/* 2151 */           argumentsList.append(specialChar + infoKey + specialChar);
/*      */         }
/*      */         else {
/* 2154 */           String attributename = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, infoKey);
/* 2155 */           if ((completeConfigAttList != null) && (completeConfigAttList.contains(infoKey))) {
/* 2156 */             isConfAttAvailable = true;
/* 2157 */             if (configattributeIdList.length() > 0) {
/* 2158 */               configattributeIdList.append(",");
/*      */             }
/* 2160 */             configattributeIdList.append(infoKey);
/*      */           }
/*      */           else {
/* 2163 */             if (attributesList.length() > 0) {
/* 2164 */               attributesList.append(",");
/*      */             }
/* 2166 */             attributesList.append(specialChar + attributename + specialChar);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 2173 */         if ((isConfAttAvailable) && (!resourcetype.equals("QueryMonitor"))) {
/* 2174 */           String qry = "select CONFVALUE,ATTRIBUTEID,RESOURCEID from AM_CONFIGURATION_INFO where RESOURCEID=" + resourceid + " and LATEST=1 and ATTRIBUTEID in(" + configattributeIdList + ")";
/*      */           
/*      */ 
/* 2177 */           getMonitorInfoValue(qry, monitorInformation, null, isConfAttAvailable, resourcetype);
/*      */         }
/* 2179 */         if ((attributesList.length() > 0) && (!resourcetype.equals("QueryMonitor"))) {
/* 2180 */           ArrayList tableName = ConfMonitorUtil.getInstance().getCurrentTable(resourcetype, "");
/* 2181 */           String qry = "select " + attributesList.toString() + "  from " + (String)tableName.get(0) + " where RESOURCEID=" + resourceid + " and collectiontime=" + collectionTime;
/* 2182 */           getMonitorInfoValue(qry, monitorInformation, null, false, resourcetype);
/*      */         }
/* 2184 */         if (argumentsList.length() > 0) {
/* 2185 */           String tableName = "AM_ARGS_" + typeId;
/* 2186 */           String qry = "select " + argumentsList.toString() + "  from " + tableName + " where RESOURCEID=" + resourceid;
/* 2187 */           getMonitorInfoValue(qry, monitorInformation, null);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 2191 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void getMonitorInfoValue(String qry, LinkedHashMap monitorInformation, Properties reliedargsValues) throws SQLException {
/* 2197 */     getMonitorInfoValue(qry, monitorInformation, reliedargsValues, false, null);
/*      */   }
/*      */   
/*      */   public void getMonitorInfoValue(String qry, LinkedHashMap monitorInformation, Properties reliedargsValues, boolean isConfAttributes, String resourceType) throws SQLException {
/* 2201 */     ResultSet rs = null;
/* 2202 */     ResultSetMetaData metaData = null;
/* 2203 */     HashMap monitorInfoDetails = new HashMap();
/*      */     
/*      */     try
/*      */     {
/* 2207 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(qry);
/* 2208 */       metaData = rs.getMetaData();
/* 2209 */       if (isConfAttributes) {
/* 2210 */         while (rs.next()) {
/* 2211 */           if (monitorInformation != null) {
/* 2212 */             monitorInfoDetails = (HashMap)monitorInformation.get(rs.getString("ATTRIBUTEID"));
/* 2213 */             String infoValue = "<span><a href=\"javascript:void();\" onClick=fnOpenNewWindow('../showConfigurationData.do?method=getConfigurationData&resourceid=" + rs.getString("RESOURCEID") + "&attributeid=" + rs.getString("ATTRIBUTEID") + "&period=-7',740,550) class=\"new-report-link\">" + FormatUtil.getString(rs.getString("CONFVALUE")) + "</a></span>";
/* 2214 */             monitorInfoDetails.put("INFO-VALUE", infoValue);
/* 2215 */             monitorInformation.put(rs.getString("ATTRIBUTEID"), monitorInfoDetails);
/*      */           }
/*      */         }
/*      */       }
/* 2219 */       if (rs.next()) {
/* 2220 */         if ((monitorInformation != null) && (resourceType != null)) {
/* 2221 */           Iterator it = monitorInformation.keySet().iterator();
/* 2222 */           ArrayList completeConfigAttList = ConfMonitorConfiguration.getInstance().getConfigidsList(resourceType);
/* 2223 */           while (it.hasNext()) {
/* 2224 */             String attid = (String)it.next();
/* 2225 */             if ((completeConfigAttList == null) || (!completeConfigAttList.contains(attid))) {
/* 2226 */               monitorInfoDetails = (HashMap)monitorInformation.get(attid);
/* 2227 */               String attributename = ConfMonitorConfiguration.getInstance().getAttributeName(resourceType, attid);
/* 2228 */               monitorInfoDetails.put("INFO-VALUE", rs.getString(attributename));
/* 2229 */               monitorInformation.put(attid, monitorInfoDetails);
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 2234 */           for (int i = 1; i <= metaData.getColumnCount(); i++) {
/* 2235 */             String colName = metaData.getColumnName(i);
/* 2236 */             String infoValue = rs.getString(colName);
/* 2237 */             infoValue = infoValue == null ? "-" : infoValue;
/* 2238 */             if (monitorInformation != null) {
/* 2239 */               Iterator it = monitorInformation.keySet().iterator();
/* 2240 */               while (it.hasNext()) {
/* 2241 */                 String infoKey = (String)it.next();
/* 2242 */                 if (infoKey.toUpperCase().equals(colName.toUpperCase())) {
/* 2243 */                   monitorInfoDetails = (HashMap)monitorInformation.get(infoKey);
/* 2244 */                   monitorInfoDetails.put("INFO-VALUE", infoValue);
/* 2245 */                   monitorInformation.put(infoKey, monitorInfoDetails);
/* 2246 */                   break;
/*      */                 }
/*      */               }
/*      */             }
/*      */             else {
/* 2251 */               reliedargsValues.setProperty(colName, infoValue);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2259 */       e.printStackTrace();
/* 2260 */       AMLog.debug("getMonitorInfoValue Query :" + qry);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2264 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2268 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ArrayList getUnmanagedMos(String resIds) throws SQLException
/*      */   {
/* 2274 */     ArrayList unManagedIdList = new ArrayList();
/* 2275 */     ResultSet rs = null;
/*      */     
/* 2277 */     String qry = "select resid from AM_UnManagedNodes where resid in (" + resIds + ")";
/* 2278 */     if (!resIds.trim().equals("")) {
/*      */       try {
/* 2280 */         AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(qry);
/* 2281 */         while (rs.next())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2289 */           unManagedIdList.add(rs.getString("resid"));
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 2293 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/* 2296 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 2299 */     return unManagedIdList;
/*      */   }
/*      */   
/* 2302 */   public void getassocciatedAgentDetails(String resourceid, HttpServletRequest request, Properties reliedargsValues) throws Exception { String query = "select COALESCE(AM_RBMAGENTDATA.Displayname,'Local') as agentName,COALESCE(AM_RBMAGENTDATA.STATUS,'0') as Status from AM_PARENTCHILDMAPPER  join AM_RESOURCE_AGENT_MAPPING on AM_PARENTCHILDMAPPER.childid=AM_RESOURCE_AGENT_MAPPING.RESOURCEID and resourceid=" + resourceid + " left outer join AM_RBMAGENTDATA on AM_RBMAGENTDATA.AGENTID=AM_RESOURCE_AGENT_MAPPING.AGENTID";
/* 2303 */     if ((!reliedargsValues.isEmpty()) && (reliedargsValues.getProperty("isParent") != null) && (reliedargsValues.getProperty("isParent").equalsIgnoreCase("true")))
/*      */     {
/* 2305 */       query = "select AM_RBMAGENTDATA.Displayname as agentName,AM_RBMAGENTDATA.STATUS as Status from AM_PARENTCHILDMAPPER  join AM_RESOURCE_AGENT_MAPPING on AM_PARENTCHILDMAPPER.childid=AM_RESOURCE_AGENT_MAPPING.RESOURCEID and PARENTID=" + resourceid + " left outer join AM_RBMAGENTDATA on AM_RBMAGENTDATA.AGENTID=AM_RESOURCE_AGENT_MAPPING.AGENTID";
/*      */     }
/*      */     
/*      */ 
/* 2309 */     ResultSet rs = null;
/*      */     try {
/* 2311 */       AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 2312 */       ArrayList agentsDown = new ArrayList();
/* 2313 */       while (rs.next()) {
/* 2314 */         if (!rs.getString("Status").equals("0")) {
/* 2315 */           agentsDown.add(rs.getString("agentName"));
/*      */         }
/*      */       }
/* 2318 */       if (!agentsDown.isEmpty()) {
/* 2319 */         request.setAttribute("agentsDown", agentsDown);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2323 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 2326 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward getMonitorInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2331 */     try { StringBuffer result = new StringBuffer();
/* 2332 */       String resId = request.getParameter("resourceid");
/* 2333 */       String resourcename = request.getParameter("resourcename");
/* 2334 */       String type = request.getParameter("type");
/* 2335 */       String serverName = request.getParameter("serverName");
/* 2336 */       LinkedHashMap keyValuemap = new LinkedHashMap();
/* 2337 */       keyValuemap.put(FormatUtil.getString("am.webclient.common.name.text"), FormatUtil.getTrimmedText(resourcename, 35));
/* 2338 */       getHostValues(resId, type, keyValuemap, false, serverName);
/* 2339 */       getArguments(resId, type, keyValuemap);
/* 2340 */       LinkedHashMap monitorInformation = (LinkedHashMap)ConfMonitorConfiguration.getInstance().getMonitorInformation(type);
/* 2341 */       monitorInformationValue(monitorInformation, type, resId, request.getParameter("collectionTime"));
/* 2342 */       if (monitorInformation != null) {
/* 2343 */         Set dataset = monitorInformation.keySet();
/* 2344 */         Iterator it = dataset.iterator();
/* 2345 */         while (it.hasNext()) {
/* 2346 */           String colName = (String)it.next();
/* 2347 */           HashMap monInfo = (HashMap)monitorInformation.get(colName);
/* 2348 */           keyValuemap.put((String)monInfo.get("DISPLAY"), monInfo.get("INFO-VALUE") != null ? (String)monInfo.get("INFO-VALUE") : "-");
/*      */         }
/*      */       }
/* 2351 */       if (type.equals("Redis"))
/*      */       {
/* 2353 */         String val = (String)keyValuemap.get("Is Authentication required");
/* 2354 */         if (val != "False")
/*      */         {
/* 2356 */           keyValuemap.put("Is Authentication required", "No");
/*      */         }
/*      */       }
/* 2359 */       keyValuemap.put("MonitorGroup", "AssociatedMonitorGroups");
/* 2360 */       String isAgent = ConfMonitorConfiguration.getInstance().getTypeDescription(type).getProperty("IS-AGENT-ENABLED") != null ? ConfMonitorConfiguration.getInstance().getTypeDescription(type).getProperty("IS-AGENT-ENABLED") : "NO";
/* 2361 */       boolean isEumParent = false;
/* 2362 */       if (isAgent.equals("YES")) {
/* 2363 */         isEumParent = NewMonitorUtil.isEUMParent(resId, type);
/*      */       }
/* 2365 */       if (!isEumParent) {
/* 2366 */         getDCTimes(request.getParameter("collectionTime"), resId, keyValuemap);
/*      */       }
/*      */       
/* 2369 */       request.setAttribute("keyValuemap", keyValuemap);
/* 2370 */       response.setContentType("text/html");
/* 2371 */       response.setCharacterEncoding("UTF-8");
/* 2372 */       RequestDispatcher rd = request.getRequestDispatcher("/jsp/includes/ConfMonitorInfo.jsp?resourceName=" + resourcename + "&type=" + type + "&resourceId=" + resId);
/* 2373 */       rd.include(request, response);
/* 2374 */       return null;
/*      */     }
/*      */     catch (Exception e) {
/* 2377 */       e.printStackTrace();
/*      */     }
/* 2379 */     return null;
/*      */   }
/*      */   
/* 2382 */   public void getArguments(String resId, String resourcetype, LinkedHashMap keyValueMap) { ArrayList argsforConfMon = NewMonitorUtil.getArgsforConfMon(resourcetype);
/* 2383 */     Properties props = NewMonitorUtil.getArgsasProps(resourcetype, resId, true);
/* 2384 */     ArrayList argType = (ArrayList)argsforConfMon.get(1);
/* 2385 */     ArrayList argDisplayName = (ArrayList)argsforConfMon.get(5);
/* 2386 */     HashMap argDefaultValues = (HashMap)argsforConfMon.get(4);
/* 2387 */     ArrayList argNames = (ArrayList)argsforConfMon.get(0);
/* 2388 */     ArrayList argVisibility = (ArrayList)argsforConfMon.get(16);
/* 2389 */     Properties hostAvailability = ConfMonitorConfiguration.getInstance().getHostDetails(resourcetype);
/* 2390 */     String hostColumn = "";
/*      */     
/* 2392 */     if ((hostAvailability != null) && (hostAvailability.getProperty("Value").equals("YES"))) {
/* 2393 */       hostColumn = hostAvailability.getProperty("Name");
/*      */     }
/* 2395 */     for (int i = 0; i < argNames.size(); i++) {
/* 2396 */       String disName = (String)argDisplayName.get(i);
/* 2397 */       String type = (String)argType.get(i);
/* 2398 */       String argName = (String)argNames.get(i);
/*      */       
/* 2400 */       if (Boolean.parseBoolean(argVisibility.get(i).toString()))
/*      */       {
/*      */         try
/*      */         {
/* 2404 */           if (props.containsKey(argName)) {
/* 2405 */             String value = (props.getProperty(argName) != null) && (!props.getProperty(argName).trim().equals("")) ? props.getProperty(argName) : "-";
/* 2406 */             if ((hostColumn.equals(argName)) && (keyValueMap.containsKey(FormatUtil.getString("am.webclient.common.hostname.text"))))
/*      */             {
/* 2408 */               if (!((String)keyValueMap.get(FormatUtil.getString("am.webclient.common.hostname.text"))).trim().equals("")) {
/* 2409 */                 value = (String)keyValueMap.get(FormatUtil.getString("am.webclient.common.hostname.text"));
/*      */               }
/* 2411 */               keyValueMap.remove(FormatUtil.getString("am.webclient.common.hostname.text"));
/*      */             }
/* 2413 */             else if ((type.equals("2")) || (type.equals("4")) || (type.equals("5"))) {
/* 2414 */               LinkedHashMap hm = (LinkedHashMap)argDefaultValues.get(argName);
/* 2415 */               Iterator it = hm.keySet().iterator();
/* 2416 */               while (it.hasNext()) {
/* 2417 */                 String keypart = (String)it.next();
/* 2418 */                 String valuePart = (String)hm.get(keypart);
/* 2419 */                 if (value.equals(valuePart)) {
/* 2420 */                   value = FormatUtil.getString(keypart);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/* 2425 */             keyValueMap.put(disName, value);
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 2429 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2435 */   public void getHostValues(String resourceid, String resourcetype, LinkedHashMap props, boolean forDetailsPage) { getHostValues(resourceid, resourcetype, props, forDetailsPage, null); }
/*      */   
/*      */   public void getHostValues(String resourceid, String resourcetype, LinkedHashMap props, boolean forDetailsPage, String serverName) {
/* 2438 */     String checkalreadyexists = "";
/* 2439 */     String hostOs = "";
/* 2440 */     String hostId = "";
/* 2441 */     String hostName = "";
/*      */     try {
/* 2443 */       if ((props.containsKey("HideHostDetails")) && (((String)props.get("HideHostDetails")).equals("true"))) {
/* 2444 */         props.put("ShowHostPerformance", Boolean.valueOf(false));
/*      */       }
/*      */       else {
/* 2447 */         if ((serverName != null) && (!serverName.equals("null")) && (!serverName.trim().equals(""))) {
/* 2448 */           checkalreadyexists = serverName;
/* 2449 */           hostName = getHostName(resourcetype, resourceid);
/*      */ 
/*      */         }
/* 2452 */         else if (forDetailsPage) {
/* 2453 */           hostName = getHostName(resourcetype, resourceid);
/* 2454 */           if ((hostName != null) && (!hostName.equals(""))) {
/* 2455 */             TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/* 2456 */             checkalreadyexists = tapi.getNodeNameByIP(hostName);
/*      */           }
/*      */         }
/*      */         else {
/* 2460 */           hostName = ConfMonitorUtil.getHostName(resourcetype, resourceid, false);
/*      */         }
/*      */         
/* 2463 */         if ((checkalreadyexists != null) && (!checkalreadyexists.trim().equals(""))) {
/* 2464 */           Properties hostData = getHostOs(hostName);
/* 2465 */           hostOs = hostData.getProperty("HOST-TYPE");
/* 2466 */           hostId = hostData.getProperty("HOST-ID");
/*      */         }
/* 2468 */         if (!forDetailsPage) {
/*      */           try {
/* 2470 */             if ((hostId != null) && (!hostId.trim().equals(""))) {
/* 2471 */               props.put(FormatUtil.getString("am.webclient.common.hostname.text"), "<span><a href=\"showresource.do?resourceid=" + hostId + "&method=showResourceForResourceID\" class=\"new-report-link\" title=" + checkalreadyexists + ">" + FormatUtil.getTrimmedText(checkalreadyexists, 25) + "&nbsp;(" + hostName + ")</a></span>");
/*      */             }
/* 2473 */             else if ((hostName != null) && (!hostName.equals("null")) && (!hostName.trim().equals(""))) {
/* 2474 */               props.put(FormatUtil.getString("am.webclient.common.hostname.text"), FormatUtil.getTrimmedText(hostName, 35));
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 2478 */             props.put(FormatUtil.getString("am.webclient.common.hostname.text"), "-");
/*      */           }
/*      */         }
/*      */         else {
/* 2482 */           props.put("hostName", hostName != null ? hostName : "");
/* 2483 */           props.put("hostOs", hostOs != null ? hostOs : "");
/* 2484 */           if ((ConfMonitorUtil.showHostDetails(resourcetype)) && (hostOs != null) && (!hostOs.equalsIgnoreCase("unknown")) && (!hostOs.equalsIgnoreCase("node"))) {
/* 2485 */             RepairTables rt = new RepairTables();
/* 2486 */             Properties property = RepairTables.getValuesForHost(hostName, hostOs);
/* 2487 */             if ((property != null) && (property.size() > 0)) {
/* 2488 */               props.put("ShowHostPerformance", Boolean.valueOf(true));
/*      */             }
/*      */             else {
/* 2491 */               props.put("ShowHostPerformance", Boolean.valueOf(false));
/*      */             }
/*      */           }
/*      */           else {
/* 2495 */             props.put("ShowHostPerformance", Boolean.valueOf(false));
/*      */           }
/* 2497 */           if ((checkalreadyexists == null) || (checkalreadyexists.trim().equals(""))) {
/* 2498 */             props.put("ShowHostPerformance", Boolean.valueOf(false));
/*      */           }
/* 2500 */           props.put("hostId", hostId != null ? hostId : "");
/* 2501 */           props.put("serverName", checkalreadyexists != null ? checkalreadyexists : "");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2506 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/* 2510 */   public long getMaxCollectionTime(String resourceid, String type, boolean isAgentParentMonitor) { return getMaxCollectionTime(resourceid, type, isAgentParentMonitor, ""); }
/*      */   
/*      */   public long getMaxCollectionTime(String resourceid, String type, boolean isAgentParentMonitor, String tableName) {
/* 2513 */     if (isAgentParentMonitor) {
/* 2514 */       return 0L;
/*      */     }
/*      */     
/* 2517 */     ArrayList dbtableName = ConfMonitorUtil.getInstance().getCurrentTable(type, tableName);
/* 2518 */     String coll_query = "select max(collectiontime) from " + (String)dbtableName.get(0) + " where RESOURCEID=" + resourceid;
/* 2519 */     boolean createNewMO = tableName.equals("") ? true : ConfMonitorConfiguration.getInstance().createNewMOForRows(type, tableName);
/* 2520 */     if ((!tableName.trim().equals("")) && (createNewMO)) {
/* 2521 */       coll_query = "select max(collectiontime) from " + (String)dbtableName.get(0) + ",AM_PARENTCHILDMAPPER where PARENTID=" + resourceid + " and RESOURCEID=CHILDID";
/*      */     }
/* 2523 */     long collectiontime = 0L;
/* 2524 */     ResultSet col_time = null;
/*      */     try {
/* 2526 */       col_time = AMConnectionPool.executeQueryStmt(coll_query);
/* 2527 */       if (col_time.next()) {
/* 2528 */         collectiontime = col_time.getLong(1);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2533 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2537 */       AMConnectionPool.closeStatement(col_time);
/*      */     }
/*      */     
/* 2540 */     return collectiontime;
/*      */   }
/*      */   
/*      */   public void getDCTimes(String colTime, String resourceid, LinkedHashMap dcTimes)
/*      */   {
/* 2545 */     long collectiontime = 0L;
/* 2546 */     ResultSet rs = null;
/*      */     try {
/* 2548 */       String query = "select max(COLLECTIONTIME) as time from AM_ManagedObjectData where RESID=" + resourceid;
/* 2549 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2550 */       if (rs.next())
/*      */       {
/* 2552 */         collectiontime = rs.getLong("time");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2556 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2560 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2562 */     long pl = getPollingInterval(resourceid, null);
/* 2563 */     long nextDC = collectiontime + pl;
/* 2564 */     long lastDC = 0L;
/*      */     
/* 2566 */     if (collectiontime != 0L) {
/* 2567 */       lastDC = collectiontime;
/* 2568 */       if (collectiontime <= AMServerFramework.serverStartupTime) {
/* 2569 */         nextDC = AMServerFramework.serverStartupTime + pl;
/*      */       }
/* 2571 */       dcTimes.put(FormatUtil.getString("am.webclient.common.lastpolledat.text"), FormatUtil.formatDT("" + collectiontime));
/* 2572 */       dcTimes.put(FormatUtil.getString("am.webclient.common.nextpollat.text"), FormatUtil.formatDT("" + nextDC));
/*      */     }
/*      */     else
/*      */     {
/* 2576 */       dcTimes.put(FormatUtil.getString("am.webclient.common.lastpolledat.text"), "-");
/* 2577 */       dcTimes.put(FormatUtil.getString("am.webclient.common.nextpollat.text"), FormatUtil.formatDT("" + (System.currentTimeMillis() + pl)));
/*      */     }
/*      */   }
/*      */   
/*      */   public long getPollingInterval(String resourceid, HttpServletRequest request)
/*      */   {
/* 2583 */     String poll_interval_qry = "select pollinterval from AM_ScriptArgs where resourceid=" + resourceid;
/* 2584 */     long pl = 300000L;
/* 2585 */     ResultSet pi = null;
/*      */     try {
/* 2587 */       pi = AMConnectionPool.executeQueryStmt(poll_interval_qry);
/* 2588 */       if (pi.next()) {
/* 2589 */         if (request != null) {
/* 2590 */           request.setAttribute("reloadperiod", pi.getLong(1) + "");
/*      */         }
/* 2592 */         pl = pi.getLong(1) * 1000L;
/*      */       }
/*      */     }
/*      */     catch (Exception exc) {
/* 2596 */       exc.printStackTrace();
/* 2597 */       if (request != null) {
/* 2598 */         request.setAttribute("reloadperiod", "300");
/*      */       }
/*      */     }
/*      */     finally {
/* 2602 */       AMConnectionPool.closeStatement(pi);
/*      */     }
/* 2604 */     return pl;
/*      */   }
/*      */   
/*      */   public Properties getTimePeriod(long startTime, long endTime, String period, HashMap parameterList) {
/* 2608 */     Properties prop = new Properties();
/* 2609 */     Properties timeProps = new Properties();
/* 2610 */     if ((period.equals("15")) || (period.equals("16"))) {
/* 2611 */       if (period.equals("15")) {
/* 2612 */         String customDay = (String)parameterList.get("DateSelected");
/* 2613 */         StringTokenizer st = new StringTokenizer(customDay, "-");
/* 2614 */         ArrayList al = new ArrayList();
/* 2615 */         while (st.hasMoreTokens()) {
/* 2616 */           al.add(st.nextToken());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 2621 */         int month = Integer.parseInt((String)al.get(0)) - 1;
/* 2622 */         timeProps.setProperty("YEAR", (String)al.get(2));
/* 2623 */         timeProps.setProperty("MONTH", month + "");
/* 2624 */         timeProps.setProperty("DATE", (String)al.get(1));
/*      */       }
/*      */       else {
/* 2627 */         String selectedWeek = (String)parameterList.get("WeekSelected");
/* 2628 */         String YearMonth = (String)parameterList.get("YearMonth");
/* 2629 */         StringTokenizer st = new StringTokenizer(YearMonth, ",");
/* 2630 */         ArrayList al = new ArrayList();
/* 2631 */         while (st.hasMoreTokens()) {
/* 2632 */           al.add(st.nextToken());
/*      */         }
/*      */         
/* 2635 */         timeProps.setProperty("YEAR", (String)al.get(1));
/* 2636 */         timeProps.setProperty("MONTH", (String)al.get(0));
/* 2637 */         timeProps.setProperty("WEEK", selectedWeek);
/*      */       }
/*      */     }
/* 2640 */     HistoryDataAPIUtil historyObj = new HistoryDataAPIUtil();
/* 2641 */     prop = historyObj.getTimePeriod(startTime, endTime, period, timeProps);
/*      */     
/* 2643 */     return prop;
/*      */   }
/*      */   
/*      */   public ActionForward showMonthAndWeek(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 2647 */     response.setContentType("text/html;charset=UTF-8");
/* 2648 */     response.setCharacterEncoding("UTF-8");
/* 2649 */     PrintWriter pw = response.getWriter();
/*      */     try {
/* 2651 */       String result = ConfMonitorUtil.getWeekContent();
/* 2652 */       pw.print(result);
/* 2653 */       pw.flush();
/*      */     } catch (Exception e) {
/* 2655 */       e.printStackTrace(); }
/* 2656 */     return null;
/*      */   }
/*      */   
/* 2659 */   public ActionForward showAllMOsForType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { PrintWriter pw = response.getWriter();
/* 2660 */     response.setContentType("text/html");
/* 2661 */     response.setCharacterEncoding("UTF-8");
/* 2662 */     String resourceType = request.getParameter("resourceType");
/* 2663 */     List allMOsList = DBUtil.getAllMOs(resourceType);
/* 2664 */     StringBuffer out = new StringBuffer();
/* 2665 */     out.append("<div  class=\"confDetails-customWeek\">");
/* 2666 */     out.append("<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"z-index: 100;border-left:1px solid #C8C8C8;border-bottom:1px solid #C8C8C8;border-right:1px solid #C8C8C8\" >");
/* 2667 */     String oddRow = "<tr height=\"29px\" class=\"alarmheader \"  onmouseover=\"this.className='alarmHeaderHover'\" onmouseout=\"this.className='alarmheader'\">";
/* 2668 */     String evenRow = "<tr height=\"29px\"  class=\"confEven\" onmouseover=\"this.className='alarmHeaderHover'\" onmouseout=\"this.className='alarmheader'\">";
/* 2669 */     String tdWeekClass = "<td class=\"customWeekText confWeekRow\">";
/* 2670 */     Iterator mos = allMOsList.iterator();
/*      */     try
/*      */     {
/* 2673 */       while (mos.hasNext()) {
/* 2674 */         HashMap moDetails = (HashMap)mos.next();
/* 2675 */         String displayName = (String)moDetails.get("DISPLAYNAME");
/* 2676 */         String resid = (String)moDetails.get("RESOURCEID");
/* 2677 */         out.append(oddRow);
/* 2678 */         out.append(tdWeekClass);
/* 2679 */         out.append("<span style=\"left:5px\"><a href=\"/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID\" class=\"staticlinks\">" + displayName + "</a></span>");
/* 2680 */         out.append("</td></tr>");
/*      */       }
/* 2682 */       out.append("</table></div>");
/* 2683 */       pw.print(out.toString());
/*      */     }
/*      */     catch (Exception e) {
/* 2686 */       e.printStackTrace(); }
/* 2687 */     return null;
/*      */   }
/*      */   
/* 2690 */   public ActionForward getTableAndGraphCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { PrintWriter pw = response.getWriter();
/* 2691 */     response.setContentType("text/html");
/* 2692 */     response.setCharacterEncoding("UTF-8");
/* 2693 */     String resourcetype = request.getParameter("resourceType");
/* 2694 */     String tabId = request.getParameter("tabId");
/* 2695 */     ConfMonitorConfiguration confmoniconf = ConfMonitorConfiguration.getInstance();
/*      */     try {
/* 2697 */       HashMap tablesList = confmoniconf.getTableListInEachTab(resourcetype, tabId + "");
/* 2698 */       String tableCount = tablesList != null ? tablesList.size() + "" : "0";
/* 2699 */       pw.print(tableCount);
/* 2700 */       pw.print("|");
/* 2701 */       HashMap graphList = confmoniconf.getGraphListInEachTab(resourcetype, tabId + "");
/* 2702 */       String graphCount = graphList != null ? graphList.size() + "" : "0";
/* 2703 */       pw.print(graphCount);
/*      */     }
/*      */     catch (Exception e) {
/* 2706 */       e.printStackTrace();
/* 2707 */       pw.print("0");
/* 2708 */       pw.print("|");
/* 2709 */       pw.print("0");
/*      */     }
/* 2711 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowCustomDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */