/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.WMIDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.WMIDataCollector;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
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
/*      */ public final class WMIPerfAction
/*      */   extends DispatchAction
/*      */ {
/*   54 */   private ManagedApplication mo = new ManagedApplication();
/*   55 */   private boolean ErrorMsg = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward createWMIPerfmonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   63 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*   64 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*   65 */     String haid = request.getParameter("haid");
/*   66 */     ActionErrors errors = new ActionErrors();
/*   67 */     ActionMessages messages = new ActionMessages();
/*   68 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*   70 */       String resourcetype = ((AMActionForm)form).getType();
/*   71 */       Properties argsprops = new Properties();
/*   72 */       argsprops.setProperty("monitorType", resourcetype);
/*   73 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*   75 */         String param = (String)e.nextElement();
/*   76 */         if (!argsprops.containsKey(param))
/*      */         {
/*   78 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*   80 */         if (param.equals("haid"))
/*      */         {
/*   82 */           String[] multiVal = request.getParameterValues(param);
/*   83 */           if ((multiVal != null) && (multiVal.length > 0)) {
/*   84 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/*   85 */             val = val.substring(1, val.length() - 1);
/*   86 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*   92 */         getRemoteHostDetails(argsprops);
/*   93 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/*   94 */         ArrayList<String> al1 = new ArrayList();
/*   95 */         String displayname = request.getParameter("displayname");
/*   96 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/*   97 */           displayname = request.getParameter("displayName");
/*      */         }
/*   99 */         String status = "Success";
/*  100 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/*  101 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/*  102 */         al1.add(displayname);
/*  103 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/*  104 */           status = "Failed";
/*  105 */           message = (String)responseMap.get("message");
/*      */         }
/*  107 */         al1.add(status);
/*  108 */         al1.add(message);
/*  109 */         al1.add(masDisplayName);
/*  110 */         request.setAttribute("discoverystatus", al1);
/*  111 */         request.setAttribute("type", resourcetype);
/*  112 */         request.setAttribute("basetype", "Script Monitor");
/*  113 */         if (((String)responseMap.get("addStatus")).equals("true")) {
/*  114 */           addHostDetailsInAAM(argsprops);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  134 */         e.printStackTrace();
/*      */       }
/*  136 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid);
/*      */     }
/*  138 */     int resourceid = -1;
/*  139 */     String name = null;
/*  140 */     String existingResourceId = null;
/*  141 */     String[] selMonitorGroups = request.getParameterValues("haid");
/*      */     try {
/*  143 */       ArrayList addStatus = null;
/*  144 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  145 */       name = ((AMActionForm)form).getDisplayname();
/*  146 */       String description = ((AMActionForm)form).getDescription();
/*  147 */       int pollinterval = ((AMActionForm)form).getPollInterval();
/*  148 */       if (pollinterval <= 0)
/*      */       {
/*  150 */         pollinterval = 5;
/*      */       }
/*  152 */       String choosehost = ((AMActionForm)form).getChoosehost();
/*  153 */       String hostid = "-10";
/*  154 */       boolean validhost = true;
/*  155 */       boolean validmo = true;
/*  156 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCENAME='" + name + "' and TYPE='Generic WMI'");
/*  157 */       if ((rs1.next()) && (request.getParameter("updatemonitor") == null))
/*      */       {
/*  159 */         validmo = false;
/*  160 */         existingResourceId = rs1.getString("RESOURCEID");
/*  161 */         addStatus = new ArrayList();
/*  162 */         addStatus.add(name);
/*  163 */         addStatus.add("Exist");
/*  164 */         addStatus.add("/showresource.do?resourceid=" + existingResourceId + "&type=Generic WMI");
/*  165 */         request.setAttribute("discoverystatus", addStatus);
/*  166 */         request.setAttribute("resourceid", existingResourceId);
/*      */         
/*  168 */         if (!Constants.sqlManager) {
/*  169 */           errors = new ActionErrors();
/*  170 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.errormsg.wmi.monitorexists.text")));
/*  171 */           saveErrors(request, errors);
/*  172 */           return new ActionForward("/jsp/NewWMIMonitor.jsp");
/*      */         }
/*      */       }
/*      */       try {
/*  176 */         rs1.close();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  180 */         ex.printStackTrace();
/*      */       }
/*  182 */       if (choosehost.equals("-1"))
/*      */       {
/*  184 */         String hostname = ((AMActionForm)form).getHost();
/*  185 */         String username = ((AMActionForm)form).getUsername();
/*  186 */         String password = ((AMActionForm)form).getPassword();
/*      */         try {
/*  188 */           Hashtable test = WMIDBUtil.getclasseslist(hostname, username, password);
/*  189 */           if (test.get("ErrorMsg") != null)
/*      */           {
/*  191 */             request.setAttribute("error", test.get("ErrorMsg"));
/*  192 */             errors = new ActionErrors();
/*  193 */             errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError((String)test.get("ErrorMsg")));
/*  194 */             saveErrors(request, errors);
/*  195 */             validhost = false;
/*      */             
/*  197 */             addStatus = new ArrayList();
/*  198 */             addStatus.add(name);
/*  199 */             addStatus.add("Failed");
/*  200 */             addStatus.add((String)test.get("ErrorMsg"));
/*  201 */             request.setAttribute("discoverystatus", addStatus);
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  207 */           request.setAttribute("error", ex);
/*  208 */           ex.printStackTrace();
/*  209 */           validhost = false;
/*      */         }
/*  211 */         if (validhost)
/*      */         {
/*  213 */           hostid = WMIDBUtil.checkandaddHost(hostname, username, password);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  218 */         hostid = choosehost;
/*      */       }
/*  220 */       if ((request.getParameter("updatemonitor") != null) && (request.getParameter("updatemonitor").equals("true")))
/*      */       {
/*  222 */         String resid = request.getParameter("resourceid");
/*      */         try {
/*  224 */           if (validhost)
/*      */           {
/*  226 */             AMConnectionPool.executeUpdateStmt("update AM_CAM_WMI_EXT_INFO set POLLINTERVAL=" + pollinterval * 60 + " ,HOSTID=" + hostid + " where RESOURCEID=" + resid);
/*  227 */             String query2 = "update AM_ManagedObject set RESOURCENAME='" + name + "',DESCRIPTION='" + description + "' where resourceid=" + resid;
/*  228 */             AMConnectionPool.executeUpdateStmt(query2);
/*  229 */             String query3 = "update AM_ManagedObject set DISPLAYNAME='" + name + "' where resourceid=" + resid;
/*  230 */             AMConnectionPool.executeUpdateStmt(query3);
/*  231 */             if (EnterpriseUtil.isManagedServer())
/*      */             {
/*  233 */               EnterpriseUtil.addUpdateQueryToFile(query2);
/*  234 */               EnterpriseUtil.addUpdateQueryToFile(query3);
/*      */             }
/*      */             try
/*      */             {
/*  238 */               DataCollectionControllerUtil.rescheduleScriptDataCollection(Integer.parseInt(resid), "Generic WMI");
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/*  242 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  248 */           System.out.println("WMI:Problem in updating the Monitor details for " + resid);
/*  249 */           ex.printStackTrace();
/*      */         }
/*  251 */         return new ActionForward("/WMIPerfCounters.do?method=showDetails&resourceid=" + resid, true);
/*      */       }
/*      */       
/*      */ 
/*  255 */       if ((validmo) && (validhost))
/*      */       {
/*  257 */         if (name != null)
/*      */         {
/*  259 */           resourceid = WMIDBUtil.mocreate(name, name, description, "Generic WMI");
/*      */         }
/*  261 */         if (resourceid != -1)
/*      */         {
/*  263 */           if (ClientDBUtil.isPrivilegedUser(request)) {
/*  264 */             RestrictedUsersViewUtil.insertIntoAMUserResourcesTableandsynchtoAAM(request.getRemoteUser(), Long.valueOf(resourceid).longValue());
/*      */           }
/*  266 */           AMConnectionPool.executeUpdateStmt("insert into AM_CAM_WMI_EXT_INFO (RESOURCEID,POLLINTERVAL,HOSTID)values(" + resourceid + "," + pollinterval * 60 + "," + hostid + ")");
/*  267 */           AMConnectionPool.executeUpdateStmt("insert into AM_RCARULESMAPPER values (" + resourceid + ",7100,1)");
/*  268 */           AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values (" + resourceid + ",7100," + resourceid + ",7101)");
/*      */           
/*      */ 
/*  271 */           WMIDBUtil.scheduleDatacollection(String.valueOf(resourceid), name, "Generic WMI", false);
/*  272 */           request.setAttribute("resourceid", Integer.valueOf(resourceid));
/*  273 */           request.setAttribute("isfromresourcepage", "true");
/*  274 */           request.setAttribute("displayname", name + ":" + description);
/*      */           
/*      */ 
/*  277 */           String[] resources = { String.valueOf(resourceid) };
/*  278 */           if ((selMonitorGroups != null) && (selMonitorGroups.length > 0)) {
/*  279 */             Vector forUpdate = new Vector();
/*  280 */             this.mo.updateManagedApplicationResources(selMonitorGroups, "xyz", resources, null, forUpdate);
/*  281 */             if (forUpdate != null)
/*      */             {
/*  283 */               for (int i = 0; i < forUpdate.size(); i++)
/*      */               {
/*  285 */                 EnterpriseUtil.addUpdateQueryToFile(forUpdate.get(i) + "");
/*      */               }
/*      */             }
/*      */             
/*  289 */             AMLog.debug("[" + resourceid + "WPC obj is associated with" + haid + "]");
/*      */           }
/*      */           
/*  292 */           addStatus = new ArrayList();
/*  293 */           addStatus.add(name);
/*  294 */           addStatus.add("Success");
/*  295 */           addStatus.add("/showresource.do?resourceid=" + resourceid + "&type=Generic WMI");
/*  296 */           request.setAttribute("discoverystatus", addStatus);
/*  297 */           request.setAttribute("resourceid", String.valueOf(resourceid));
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  302 */           return new ActionForward("/jsp/NewWMIMonitor.jsp");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  307 */         return new ActionForward("/jsp/NewWMIMonitor.jsp");
/*      */       }
/*  309 */       if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */       {
/*  311 */         return new ActionForward("/jsp/NewWMIMonitor.jsp?isDiscoveryComplete=true&resourceid=" + resourceid);
/*      */       }
/*      */       
/*      */ 
/*  315 */       return new ActionForward("/WMIPerfCounters.do?method=showDetails&resourceid=" + resourceid, true);
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  322 */       System.out.println("Problem in creating the WMI PERFORMANCE Monitor");
/*  323 */       ex.printStackTrace();
/*      */     }
/*  325 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&type=Generic WMI&moname=" + name + "&method=showdetails&resourcename=" + name + "&viewType=showResourceTypes", true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showGraph(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  336 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*      */ 
/*  339 */     request.setAttribute("resourceid", resourceid);
/*  340 */     return new ActionForward("/jsp/WMIGraphs.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  350 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  351 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*  353 */     if (resourceid == null)
/*      */     {
/*  355 */       resourceid = (String)request.getAttribute(resourceid);
/*      */     }
/*  357 */     String displayname = "";
/*  358 */     String description = "";
/*  359 */     String username = "";
/*  360 */     int pollinterval = 5;
/*      */     
/*  362 */     String host = "";
/*  363 */     String hostid = "";
/*  364 */     long collectiontime = 0L;
/*  365 */     String ErrorMsg = null;
/*      */     try
/*      */     {
/*  368 */       request.setAttribute("hosts", null);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  372 */       ex.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/*  376 */       String query = "SELECT AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.DESCRIPTION,AM_CAM_WMI_EXT_INFO.POLLINTERVAL,AM_SCRIPTHOSTDETAILS.HOSTNAME,AM_CAM_WMI_EXT_INFO.HOSTID,AM_SCRIPTHOSTDETAILS.USERNAME,AM_CAM_WMI_EXT_INFO.ERRORMESSAGE FROM AM_ManagedObject,AM_CAM_WMI_EXT_INFO,AM_SCRIPTHOSTDETAILS where AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_ManagedObject.RESOURCEID=AM_CAM_WMI_EXT_INFO.RESOURCEID and AM_SCRIPTHOSTDETAILS.ID=AM_CAM_WMI_EXT_INFO.HOSTID ";
/*      */       
/*  378 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  379 */       if (rs.next())
/*      */       {
/*  381 */         displayname = rs.getString("DISPLAYNAME");
/*  382 */         description = rs.getString("DESCRIPTION");
/*  383 */         pollinterval = rs.getInt("POLLINTERVAL");
/*  384 */         pollinterval /= 60;
/*  385 */         host = rs.getString("HOSTNAME");
/*  386 */         username = rs.getString("USERNAME");
/*  387 */         hostid = rs.getString("HOSTID");
/*  388 */         ErrorMsg = rs.getString("ERRORMESSAGE");
/*  389 */         if ((ErrorMsg != null) && (!ErrorMsg.equalsIgnoreCase("null")) && (!ErrorMsg.equalsIgnoreCase("")))
/*      */         {
/*  391 */           ActionErrors errors = new ActionErrors();
/*  392 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(ErrorMsg));
/*  393 */           saveErrors(request, errors);
/*      */         }
/*      */       }
/*  396 */       ((AMActionForm)form).setDisplayname(displayname);
/*  397 */       ((AMActionForm)form).setDescription(description);
/*  398 */       ((AMActionForm)form).setPollInterval(pollinterval);
/*  399 */       ((AMActionForm)form).setChoosehost(hostid);
/*  400 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  404 */       ex.printStackTrace();
/*      */     }
/*      */     try {
/*  407 */       String coll_query = "select max(COLLECTIONTIME) as COLLECTIONTIME from AM_ManagedObjectData where RESID=" + resourceid;
/*  408 */       ResultSet ws = AMConnectionPool.executeQueryStmt(coll_query);
/*  409 */       if (ws.next())
/*      */       {
/*  411 */         collectiontime = ws.getLong("COLLECTIONTIME");
/*      */       }
/*  413 */       ws.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  417 */       ex.printStackTrace();
/*      */     }
/*  419 */     if (collectiontime != 0L)
/*      */     {
/*  421 */       if (collectiontime > AMServerFramework.serverStartupTime)
/*      */       {
/*  423 */         request.setAttribute("LASTDC", String.valueOf(collectiontime));
/*  424 */         request.setAttribute("NEXTDC", String.valueOf(collectiontime + pollinterval * 60 * 1000));
/*      */       }
/*      */       else
/*      */       {
/*  428 */         request.setAttribute("LASTDC", String.valueOf(collectiontime));
/*  429 */         request.setAttribute("NEXTDC", String.valueOf(AMServerFramework.serverStartupTime + pollinterval * 60 * 1000));
/*      */       }
/*      */       
/*      */     }
/*      */     else {
/*  434 */       request.setAttribute("NEXTDC", String.valueOf(System.currentTimeMillis() + pollinterval * 1000));
/*      */     }
/*      */     
/*      */ 
/*  438 */     request.setAttribute("resourceid", resourceid);
/*  439 */     request.setAttribute("displayname", displayname);
/*  440 */     request.setAttribute("host", host);
/*  441 */     request.setAttribute("reloadperiod", String.valueOf(pollinterval * 60));
/*  442 */     request.setAttribute("isfromresourcepage", "true");
/*  443 */     request.setAttribute("resourcename", displayname);
/*  444 */     request.setAttribute("description", description);
/*  445 */     if (request.getParameter("addedAttributes") != null)
/*      */     {
/*  447 */       ActionMessages messages = new ActionMessages();
/*  448 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wmi.addedattributes.text")));
/*  449 */       saveMessages(request, messages);
/*      */     }
/*  451 */     if (!Constants.sqlManager) {
/*  452 */       return new ActionForward("/jsp/WMIPerfDetails.jsp?resourceid=" + resourceid);
/*      */     }
/*      */     
/*  455 */     int parentid = -1;
/*  456 */     ResultSet rs = null;
/*      */     try {
/*  458 */       rs = AMConnectionPool.executeQueryStmt("select parentid from AM_PARENTCHILDMAPPER where childid=" + resourceid);
/*  459 */       while (rs.next()) {
/*  460 */         parentid = rs.getInt("parentid");
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  464 */       ex.printStackTrace();
/*      */     }
/*  466 */     AMConnectionPool.closeStatement(rs);
/*  467 */     return new ActionForward("/showresource.do?resourceid=" + parentid + "&method=showResourceForResourceID&viewType=showDetailsView&datatype=1");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  480 */     ArrayList attributes = null;
/*  481 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  482 */     ResultSet rs = null;
/*  483 */     ResultSet rs1 = null;
/*  484 */     String resourceid = request.getParameter("resourceid");
/*  485 */     String displayname = "";
/*  486 */     String description = "";
/*      */     try
/*      */     {
/*  489 */       rs = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.DESCRIPTION from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + resourceid);
/*  490 */       if (rs.next())
/*      */       {
/*  492 */         displayname = rs.getString("DISPLAYNAME");
/*  493 */         description = rs.getString("DESCRIPTION");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  498 */       attributes = this.mo.getRows("select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME as ATTRIBUTENAME,AM_CAM_DC_GROUPS.GROUPNAME as CLASSNAME,AM_CAM_GROUP_INSTANCE_MAPPING.INSTANCENAME,AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID as REPORTS,AM_CAM_DC_GROUPS.GROUPID from AM_CAM_DC_GROUPS inner join AM_CAM_GROUP_INSTANCE_MAPPING on AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID=AM_CAM_DC_GROUPS.GROUPID  inner join AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER on AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID=AM_CAM_GROUP_INSTANCE_MAPPING.GROUPID inner join AM_CAM_DC_ATTRIBUTES on AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID left outer join AM_ARCHIVERCONFIG_EXTN on AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID where AM_CAM_DC_GROUPS.RESOURCEID=" + resourceid);
/*  499 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  503 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  507 */     if (attributes != null)
/*      */     {
/*  509 */       request.setAttribute("bulkedit", attributes);
/*      */     }
/*  511 */     request.setAttribute("displayname", displayname);
/*  512 */     request.setAttribute("description", description);
/*      */     
/*  514 */     request.setAttribute("resourceid", resourceid);
/*  515 */     return mapping.findForward("bulkview");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward EditHost(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  524 */     String hostname = null;
/*  525 */     String username = null;
/*  526 */     String password = null;
/*  527 */     String hostid = "-1";
/*  528 */     String resourceid = request.getParameter("resourceid");
/*  529 */     String action = request.getParameter("action");
/*  530 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  531 */     if (action.equals("showform"))
/*      */     {
/*  533 */       hostid = request.getParameter("hostid").trim();
/*      */       
/*  535 */       ResultSet rs = null;
/*      */       try {
/*  537 */         ((AMActionForm)form).setHost(hostname);
/*      */         
/*  539 */         rs = AMConnectionPool.executeQueryStmt("select ID,HOSTNAME,USERNAME from AM_SCRIPTHOSTDETAILS where ID=" + hostid + " and MODE='WMI'");
/*  540 */         if (rs.next())
/*      */         {
/*  542 */           hostname = rs.getString("HOSTNAME");
/*  543 */           username = rs.getString("USERNAME");
/*  544 */           hostid = rs.getString("ID");
/*  545 */           ((AMActionForm)form).setHost(hostname);
/*  546 */           ((AMActionForm)form).setUsername(username);
/*      */         }
/*  548 */         request.setAttribute("resourceid", resourceid);
/*  549 */         request.setAttribute("hostid", hostid);
/*      */         
/*  551 */         rs.close();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  555 */         ex.printStackTrace();
/*      */       }
/*  557 */       return new ActionForward("/jsp/WMIHostDetails.jsp");
/*      */     }
/*  559 */     if (action.equals("update"))
/*      */     {
/*      */ 
/*  562 */       hostid = request.getParameter("hostid");
/*  563 */       hostname = ((AMActionForm)form).getHost();
/*  564 */       username = ((AMActionForm)form).getUsername();
/*  565 */       password = ((AMActionForm)form).getPassword();
/*      */       try
/*      */       {
/*  568 */         if (DBQueryUtil.getDBType().equals("mysql"))
/*      */         {
/*  570 */           username = WMIDBUtil.findReplace(username, "\\", "\\\\");
/*      */         }
/*  572 */         if (!hostid.equals("-1"))
/*      */         {
/*  574 */           AMConnectionPool.executeUpdateStmt("update AM_SCRIPTHOSTDETAILS set HOSTNAME='" + hostname + "',USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + " where ID=" + hostid);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  579 */         ex.printStackTrace();
/*      */       }
/*  581 */       return new ActionForward("/WMIPerfCounters.do?method=getDetails&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*  584 */     return new ActionForward("/WMIPerfCounters.do?method=showDetails&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showSingleGraphScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  595 */     ActionErrors errors = new ActionErrors();
/*      */     try
/*      */     {
/*  598 */       String attributeid = "";
/*  599 */       String classresourceid = "";
/*  600 */       if (request.getParameter("attributeid") != null)
/*      */       {
/*  602 */         String tempAttribID = request.getParameter("attributeid");
/*  603 */         int marker = tempAttribID.indexOf("|");
/*  604 */         attributeid = tempAttribID.substring(0, marker);
/*  605 */         classresourceid = tempAttribID.substring(marker + 1, tempAttribID.length());
/*  606 */         request.setAttribute("attributeid", attributeid);
/*  607 */         request.setAttribute("classresourceid", classresourceid);
/*  608 */         request.setAttribute("type", "Generic WMI");
/*  609 */         Map attributeInfo = CAMDBUtil.getAttributeInfo(Integer.parseInt(attributeid));
/*  610 */         request.setAttribute("attributeinfo", attributeInfo);
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  615 */       ee.printStackTrace();
/*      */     }
/*      */     
/*  618 */     return new ActionForward("/jsp/cam_showsingleattributegraph.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showClasses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  627 */     String hostname = "";
/*  628 */     String username = "guest";
/*  629 */     String password = "guest";
/*  630 */     boolean hoststatus = true;
/*  631 */     Properties userprops = null;
/*  632 */     String toshow = request.getParameter("toshow");
/*  633 */     String resourceid = request.getParameter("resourceid");
/*  634 */     Vector listofclasses = null;
/*  635 */     request.setAttribute("resourceid", resourceid);
/*  636 */     userprops = WMIDBUtil.getUserDetails(resourceid);
/*  637 */     hostname = userprops.getProperty("hostname");
/*  638 */     username = userprops.getProperty("username");
/*  639 */     password = userprops.getProperty("password");
/*  640 */     if (Constants.sqlManager) {
/*  641 */       String date = new Date().toString();
/*  642 */       if ((resourceid == null) || (resourceid.equals(""))) {
/*  643 */         resourceid = (String)request.getAttribute("resourceid");
/*  644 */         String d = new Date().toString();
/*  645 */         return new ActionForward("/MSSql.do?resourceid=" + resourceid + "&methods=hostDetails&noredirect=false&date=" + d);
/*      */       }
/*      */     }
/*  648 */     if ((toshow != null) && (toshow.equals("classes")))
/*      */     {
/*  650 */       Hashtable temp = WMIDBUtil.getclasseslist(hostname, username, password);
/*  651 */       if (temp.get("ErrorMsg") != null)
/*      */       {
/*  653 */         request.setAttribute("ErrorMsg", (String)temp.get("ErrorMsg"));
/*  654 */         ActionErrors errors = new ActionErrors();
/*  655 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError((String)temp.get("ErrorMsg")));
/*  656 */         saveErrors(request, errors);
/*  657 */         return new ActionForward("/WMIPerfCounters.do?method=getDetails&resourceid=" + resourceid);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  662 */       listofclasses = (Vector)temp.get("classlist");
/*  663 */       if (listofclasses.size() <= 0)
/*      */       {
/*  665 */         ActionErrors errors = new ActionErrors();
/*  666 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("There are no WMI Performance Counter Classes in the Host, subclassing Win32_PerfFormattedData"));
/*  667 */         saveErrors(request, errors);
/*  668 */         return new ActionForward("/WMIPerfCounters.do?method=showDetails&resourceid=" + resourceid);
/*      */       }
/*  670 */       request.setAttribute("listofclasses", listofclasses);
/*  671 */       request.setAttribute("hostname", hostname);
/*  672 */       return new ActionForward("/jsp/WMIClassesList.jsp");
/*      */     }
/*      */     
/*      */ 
/*  676 */     if ((toshow != null) && (toshow.equals("classeswithattribs")))
/*      */     {
/*  678 */       String[] classes = request.getParameterValues("classes");
/*  679 */       Hashtable classeswithattribs = getAttributeList(classes, hostname, username, password);
/*  680 */       request.setAttribute("classeswithattribs", classeswithattribs);
/*  681 */       request.setAttribute("hostname", hostname);
/*  682 */       return new ActionForward("/jsp/WMIClassesList.jsp");
/*      */     }
/*  684 */     return mapping.findForward("bulkview");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  694 */     String[] attributes = request.getParameterValues("attributes");
/*  695 */     String hostname = request.getParameter("hostname");
/*  696 */     ActionMessages messages = new ActionMessages();
/*  697 */     int hostid = -1;
/*  698 */     int attributeid = -1;
/*  699 */     int healthid = 7100;
/*  700 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  701 */     String resourceid = request.getParameter("resourceid");
/*  702 */     String isFromResourcePage = request.getParameter("isfromresourcepage");
/*      */     
/*  704 */     request.setAttribute("resourceid", resourceid);
/*  705 */     int ResID = -1;
/*      */     try {
/*  707 */       ResID = Integer.parseInt(resourceid);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  711 */       ex.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/*  715 */       ResultSet rs1 = AMConnectionPool.executeQueryStmt("select ID from AM_SCRIPTHOSTDETAILS where HOSTNAME='" + hostname + "' and MODE='WMI'");
/*  716 */       if (rs1.next())
/*      */       {
/*  718 */         hostid = rs1.getInt("ID");
/*      */       }
/*  720 */       rs1.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  724 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  727 */     if (attributes != null)
/*      */     {
/*      */ 
/*  730 */       for (int i = 0; i < attributes.length; i++)
/*      */       {
/*      */ 
/*  733 */         int marker = attributes[i].indexOf("|");
/*  734 */         String classname = attributes[i].substring(0, marker);
/*  735 */         String attributename = attributes[i].substring(marker + 1, attributes[i].length());
/*  736 */         String[] conditions = request.getParameterValues(classname);
/*  737 */         String condition = "";
/*  738 */         if ((conditions != null) && (conditions.length > 0))
/*      */         {
/*      */ 
/*  741 */           for (int c = 0; c < conditions.length; c++)
/*      */           {
/*  743 */             condition = parseCondition(conditions[c]);
/*      */             
/*      */             try
/*      */             {
/*  747 */               System.out.println("the condition isssssss====>" + condition);
/*  748 */               temp = WMIDBUtil.addAttribute(resourceid, hostname, classname, condition, attributename);
/*      */             }
/*      */             catch (Exception ex) {
/*      */               boolean temp;
/*  752 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  760 */       WMIDBUtil.addAllDependentAttributes(ResID, 7100);
/*  761 */       String resourcetype = "Generic WMI";
/*  762 */       if (!Constants.sqlManager) {
/*      */         try {
/*  764 */           DataCollectionControllerUtil.rescheduleScriptDataCollection(Integer.parseInt(resourceid), resourcetype);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  768 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  772 */       ReportUtil.loadAllSecondLevelAttribute();
/*      */     }
/*  774 */     if (!Constants.sqlManager) {
/*  775 */       return new ActionForward("/WMIPerfCounters.do?method=showDetails&addedAttributes=true&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*  778 */     return new ActionForward("/WMIPerfCounters.do?method=getDetails&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteAttributes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  789 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  790 */     String resourceid = request.getParameter("resourceid");
/*  791 */     StringBuffer commaSepValues = new StringBuffer();
/*      */     
/*  793 */     String[] attributes = request.getParameterValues("attributes");
/*      */     try
/*      */     {
/*  796 */       AMConnectionPool.getInstance();Statement todelete = AMConnectionPool.getConnection().createStatement();
/*  797 */       for (int i = 0; i < attributes.length; i++)
/*      */       {
/*  799 */         int marker = attributes[i].indexOf("|");
/*  800 */         String attributeid = attributes[i].substring(0, marker);
/*  801 */         String classresourceid = attributes[i].substring(marker + 1, attributes[i].length());
/*  802 */         WMIDBUtil.deleteattribute(classresourceid, attributeid);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*      */ 
/*  810 */       exc.printStackTrace();
/*  811 */       System.out.println("Exception in deleting the attributes for the WMI Performance Monitor");
/*      */     }
/*  813 */     return new ActionForward("/WMIPerfCounters.do?method=getDetails&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward changeReportStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  825 */     String resourceid = request.getParameter("resourceid");
/*  826 */     String reports = request.getParameter("reports");
/*  827 */     String[] attributes = request.getParameterValues("attributes");
/*  828 */     ActionMessages messages = new ActionMessages();
/*  829 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  830 */     for (int i = 0; i < attributes.length; i++)
/*      */     {
/*  832 */       int marker = attributes[i].indexOf("|");
/*  833 */       String attributeid = attributes[i].substring(0, marker);
/*  834 */       String classresourceid = attributes[i].substring(marker + 1, attributes[i].length());
/*  835 */       if (reports.equals("enabled"))
/*      */       {
/*  837 */         WMIDBUtil.addToArchiverConfig(classresourceid, attributeid);
/*      */       }
/*  839 */       else if (reports.equals("disabled"))
/*      */       {
/*  841 */         WMIDBUtil.removeFromArchiverConfig(classresourceid, attributeid);
/*      */       }
/*      */     }
/*  844 */     if (reports.equals("enabled"))
/*      */     {
/*  846 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wmi.reports.enabled.text")));
/*  847 */       saveMessages(request, messages);
/*      */     }
/*  849 */     else if (reports.equals("disabled"))
/*      */     {
/*  851 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wmi.reports.disabled.text")));
/*  852 */       saveMessages(request, messages);
/*      */     }
/*      */     
/*  855 */     return new ActionForward("/WMIPerfCounters.do?method=getDetails&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteHost(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  864 */     String host = request.getParameter("host");
/*  865 */     String query = "delete from AM_SCRIPTHOSTDETAILS where AM_SCRIPTHOSTDETAILS.ID=" + host;
/*  866 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  867 */     ResultSet rs = null;
/*  868 */     ResultSet rs1 = null;
/*  869 */     String type = "";
/*  870 */     String resType = "";
/*  871 */     if (request.getParameter("type") != null) {
/*  872 */       type = request.getParameter("type");
/*      */     }
/*  874 */     if (request.getParameter("resType") != null) {
/*  875 */       resType = request.getParameter("resType");
/*      */     }
/*      */     try
/*      */     {
/*  879 */       rs = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_CAM_WMI_EXT_INFO where HOSTID=" + host);
/*  880 */       rs1 = AMConnectionPool.executeQueryStmt("select SCRIPTID from AM_SCRIPTHOST_MAPPER where HOSTID=" + host);
/*  881 */       if ((rs.next()) || (rs1.next()))
/*      */       {
/*  883 */         ActionErrors errors = new ActionErrors();
/*  884 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.wmi.host.used.text")));
/*  885 */         saveErrors(request, errors);
/*  886 */         rs.close();
/*  887 */         rs1.close();
/*  888 */         if (type.equals("script")) {
/*  889 */           if (!resType.equals("")) {
/*  890 */             return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resType + "&restype=" + resType + "&haid=null");
/*      */           }
/*      */           
/*  893 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Script Monitor&restype=Script Monitor&haid=null");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  898 */         return new ActionForward("/jsp/NewWMIMonitor.jsp");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  903 */       ActionMessages messages = new ActionMessages();
/*  904 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wmi.host.delete.success.text")));
/*  905 */       saveMessages(request, messages);
/*  906 */       AMConnectionPool.executeUpdateStmt(query);
/*  907 */       rs.close();
/*  908 */       rs1.close();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  913 */       ex.printStackTrace();
/*      */     }
/*  915 */     if (type.equals("script")) {
/*  916 */       if (!resType.equals("")) {
/*  917 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resType + "&restype=" + resType + "&haid=null");
/*      */       }
/*      */       
/*  920 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Script Monitor&restype=Script Monitor&haid=null");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  925 */     return new ActionForward("/jsp/NewWMIMonitor.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Hashtable getAttributeList(String[] classes, String hostname, String username, String password)
/*      */   {
/*  937 */     Hashtable classeswithattribs = new Hashtable();
/*  938 */     Hashtable result = new Hashtable();
/*  939 */     Vector attributelist = null;
/*  940 */     Vector Instancelist = null;
/*  941 */     Vector outputvector = null;
/*  942 */     WMIDataCollector wmidc = new WMIDataCollector();
/*  943 */     for (int i = 0; i < classes.length; i++)
/*      */     {
/*  945 */       Vector group = new Vector();
/*      */       try {
/*  947 */         Hashtable commands = new Hashtable();
/*  948 */         commands.put(classes[i], classes[i]);
/*  949 */         Hashtable output = wmidc.executeCommands(hostname, username, password, commands, new Vector(), "listattributes.vbs");
/*  950 */         if (output.get("ErrorMsg") == null)
/*      */         {
/*  952 */           outputvector = (Vector)output.get(classes[i]);
/*  953 */           if (outputvector.size() > 2)
/*      */           {
/*  955 */             outputvector.removeElementAt(0);
/*  956 */             outputvector.removeElementAt(0);
/*  957 */             String attribs = (String)outputvector.elementAt(0);
/*  958 */             attributelist = tokenizer(attribs);
/*  959 */             String Insts = (String)outputvector.elementAt(1);
/*      */             
/*  961 */             Instancelist = tokenizer(Insts);
/*  962 */             group.add(attributelist);
/*  963 */             group.add(checkandAddInstance(Instancelist));
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  971 */           group.add(new Vector());
/*  972 */           group.add(new Vector());
/*  973 */           String errormsg = (String)output.get("ErrorMsg");
/*  974 */           if (errormsg.indexOf("The RPC server is unavailable") >= 0)
/*      */           {
/*  976 */             group.add(FormatUtil.getString("am.errormsg.wmi.invalidhost.text"));
/*      */           }
/*  978 */           else if (errormsg.indexOf("Access is denied") >= 0)
/*      */           {
/*  980 */             group.add(FormatUtil.getString("am.errormsg.wmi.authenticationfailure.text"));
/*      */           }
/*  982 */           else if ((errormsg.indexOf("NO INSTANCE") >= 0) || (errormsg.indexOf("Invalid class") >= 0))
/*      */           {
/*  984 */             group.add("No Instance of the Class");
/*      */           }
/*      */           else
/*      */           {
/*  988 */             group.add(errormsg);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  994 */         ex.printStackTrace();
/*      */       }
/*  996 */       classeswithattribs.put(classes[i], group);
/*      */     }
/*  998 */     return classeswithattribs;
/*      */   }
/*      */   
/*      */ 
/*      */   private Vector tokenizer(String attributes)
/*      */   {
/* 1004 */     Vector attributeslist = new Vector();
/* 1005 */     attributes = attributes.trim();
/* 1006 */     int indexvalue = attributes.indexOf("Marker:");
/* 1007 */     if (indexvalue != -1)
/*      */     {
/* 1009 */       attributes = attributes.substring(7);
/* 1010 */       StringTokenizer tok = new StringTokenizer(attributes, "\t");
/* 1011 */       while (tok.hasMoreElements())
/*      */       {
/* 1013 */         String temp = tok.nextToken();
/* 1014 */         attributeslist.add(temp);
/*      */       }
/*      */     }
/* 1017 */     return attributeslist;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Vector checkandAddInstance(Vector instances)
/*      */   {
/* 1024 */     if ((instances.size() == 1) && (((String)instances.elementAt(0)).trim().equals("Name=")))
/*      */     {
/* 1026 */       return new Vector();
/*      */     }
/*      */     
/* 1029 */     return instances;
/*      */   }
/*      */   
/*      */ 
/*      */   private String parseCondition(String condition)
/*      */   {
/* 1035 */     condition = WMIDBUtil.findReplace(condition, "\\", "\\\\");
/*      */     
/* 1037 */     int marker = condition.indexOf("=");
/* 1038 */     if (marker >= 0)
/*      */     {
/* 1040 */       String name = condition.substring(0, marker);
/* 1041 */       String value = condition.substring(marker + 1, condition.length());
/* 1042 */       condition = name + "='" + value + "'";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1048 */     return condition;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addHostDetailsInAAM(Properties argsProps)
/*      */   {
/*      */     try
/*      */     {
/* 1058 */       String choosehost = argsProps.getProperty("choosehost");
/* 1059 */       AMLog.debug("WMIPerfAction.addHostDetailsInAAM(): choosehost:" + choosehost);
/* 1060 */       if ((choosehost != null) && (choosehost.equals("-1")))
/*      */       {
/* 1062 */         String host = argsProps.getProperty("host");
/* 1063 */         String username = argsProps.getProperty("username");
/* 1064 */         String password = argsProps.getProperty("password");
/* 1065 */         WMIDBUtil.checkandaddHost(host, username, password);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1068 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void getRemoteHostDetails(Properties argsProps)
/*      */   {
/* 1078 */     ResultSet rs = null;
/*      */     try {
/* 1080 */       Properties hostDetails = new Properties();
/* 1081 */       String choosehost = argsProps.getProperty("choosehost");
/* 1082 */       AMLog.debug("WMIPerfAction.getRemoteHostDetails(): choosehost:" + choosehost);
/* 1083 */       if ((choosehost != null) && (!choosehost.equals("-1"))) {
/* 1084 */         String hostDetailsQuery = "select HOSTNAME,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD from AM_SCRIPTHOSTDETAILS where ID=" + choosehost;
/* 1085 */         rs = AMConnectionPool.executeQueryStmt(hostDetailsQuery);
/* 1086 */         if (rs.next()) {
/* 1087 */           hostDetails.put("host", rs.getString("HOSTNAME"));
/* 1088 */           hostDetails.put("username", rs.getString("USERNAME"));
/* 1089 */           hostDetails.put("password", rs.getString("PASSWORD"));
/*      */         }
/* 1091 */         hostDetails.put("choosehost", "-1");
/* 1092 */         argsProps.putAll(hostDetails);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1095 */       ex.printStackTrace();
/*      */     } finally {
/* 1097 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\WMIPerfAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */