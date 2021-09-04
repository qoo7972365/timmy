/*      */ package com.adventnet.appmanager.hostresource.struts;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.AMThresholdApplier;
/*      */ import com.adventnet.appmanager.fault.ExecuteSDPTicketAction;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.server.hostresources.datacollection.HostResourceMonitor;
/*      */ import com.adventnet.appmanager.server.hostresources.datacollection.HostSnmpDataCollector;
/*      */ import com.adventnet.appmanager.server.hostresources.util.HostConfInfoGeneric;
/*      */ import com.adventnet.appmanager.server.hostresources.util.HostUtil;
/*      */ import com.adventnet.appmanager.server.template.AMTemplateConfiguration;
/*      */ import com.adventnet.appmanager.server.template.AMTemplateFactory;
/*      */ import com.adventnet.appmanager.util.CLITelnetHandler;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.cli.CLIMessage;
/*      */ import com.adventnet.cli.CLISession;
/*      */ import com.adventnet.nms.appln.hostresource.datacollection.server.model.HostDetails;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.topodb.TopoAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.me.apm.scheduledtasks.util.ScheduledTasksUtil;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.Action;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.htmlparser.util.Translate;
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class HostResourceAction
/*      */   extends Action
/*      */ {
/*   76 */   private Vector List = new Vector();
/*   77 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*   78 */   AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */   
/*   80 */   TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*      */   
/*   82 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*   86 */   public static Hashtable telnetMapper = new Hashtable();
/*      */   
/*      */   public ActionForward showNetworkInterface(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   91 */     return new ActionForward("/jsp/NetworkInterface.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward editProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   99 */     String sqlmanid = request.getParameter("sqlmanid");
/*  100 */     ActionMessages messages = new ActionMessages();
/*  101 */     ActionErrors errors = new ActionErrors();
/*  102 */     String resourceid = request.getParameter("resourceid");
/*  103 */     this.mo.getReloadPeriod(request);
/*  104 */     String haid = request.getParameter("haid");
/*  105 */     String appname = request.getParameter("appName");
/*  106 */     String resourcename = request.getParameter("resourcename");
/*  107 */     String processid = request.getParameter("processid");
/*  108 */     String query = "select DISPLAYNAME , PROCESSNAME , COMMAND from AM_HOST_PROCESS_INFO where AM_HOST_PROCESS_INFO.PARENTID=" + resourceid + " and AM_HOST_PROCESS_INFO.RESOURCEID=" + processid;
/*      */     try
/*      */     {
/*  111 */       ResultSet info = AMConnectionPool.executeQueryStmt(query);
/*  112 */       if (info.next())
/*      */       {
/*  114 */         ((DynaActionForm)form).set("displayname", info.getString("DISPLAYNAME"));
/*  115 */         ((DynaActionForm)form).set("command", info.getString("COMMAND"));
/*  116 */         ((DynaActionForm)form).set("processname", info.getString("PROCESSNAME"));
/*  117 */         AMConnectionPool.closeStatement(info);
/*  118 */         request.setAttribute("category", getCategory(request.getParameter("resourceid"), request));
/*      */         
/*  120 */         return new ActionForward(".hostresource.editprocess");
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*  130 */       e.printStackTrace();
/*  131 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid);
/*      */     }
/*  133 */     if (Constants.sqlManager)
/*      */     {
/*  135 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */     }
/*  137 */     return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */   public ActionForward removeProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  143 */     String sqlmanid = request.getParameter("sqlmanid");
/*  144 */     ActionMessages messages = new ActionMessages();
/*  145 */     ActionErrors errors = new ActionErrors();
/*  146 */     AMRCAnalyser rcaAnalyser = new AMRCAnalyser();
/*  147 */     String resourceid = request.getParameter("resourceid");
/*  148 */     Vector v = getCategory(resourceid, request);
/*  149 */     Properties p = (Properties)request.getAttribute("ids");
/*  150 */     String availid = p.getProperty("Availability");
/*  151 */     String healthid = p.getProperty("Health");
/*  152 */     this.mo.getReloadPeriod(request);
/*  153 */     String haid = request.getParameter("haid");
/*  154 */     String appname = request.getParameter("appName");
/*  155 */     String resourcename = request.getParameter("name");
/*  156 */     String[] processids = request.getParameterValues("monitors");
/*  157 */     boolean isVirtualMachine = false;
/*  158 */     String tabId = request.getParameter("tabId");
/*  159 */     if (request.getParameter("isVirtualMachine") != null)
/*      */     {
/*  161 */       isVirtualMachine = true;
/*      */     }
/*  163 */     if ((request.getParameter("enabled") != null) && (request.getParameter("enabled").equals("true")))
/*      */     {
/*  165 */       for (int i = 0; i < processids.length; i++)
/*      */       {
/*  167 */         String processid = processids[i];
/*  168 */         String selectQuery = "select RESOURCEID as RESID from AM_HOST_PROCESS_ENABLEREPORTS where RESOURCEID=" + processid;
/*  169 */         ResultSet rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  170 */         if (!rs.first())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  176 */           String insertQuery = "insert into AM_HOST_PROCESS_ENABLEREPORTS values('" + resourceid + "','" + processid + "')";
/*  177 */           AMConnectionPool.executeUpdateStmt(insertQuery);
/*      */         }
/*      */       }
/*  180 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", "Reports enabled successfully."));saveMessages(request, messages);
/*      */       
/*  182 */       if (Constants.sqlManager)
/*      */       {
/*  184 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/*      */       
/*  187 */       if (isVirtualMachine)
/*      */       {
/*  189 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID#tabId-" + tabId + "&periodicity-0&granularity-5", true);
/*      */       }
/*      */       
/*      */ 
/*  193 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*  196 */     if ((request.getParameter("enabled") != null) && (request.getParameter("enabled").equals("false")))
/*      */     {
/*  198 */       for (int i = 0; i < processids.length; i++)
/*      */       {
/*  200 */         String processid = processids[i];
/*  201 */         String selectQuery = "select RESOURCEID as RESID from AM_HOST_PROCESS_ENABLEREPORTS where RESOURCEID=" + processid;
/*  202 */         ResultSet rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  203 */         if (rs.first())
/*      */         {
/*  205 */           AMConnectionPool.executeUpdateStmt("delete from AM_HOST_PROCESS_ENABLEREPORTS where RESOURCEID=" + processid);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  212 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", "Reports disabled successfully."));
/*  213 */       saveMessages(request, messages);
/*  214 */       if (Constants.sqlManager)
/*      */       {
/*  216 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/*  218 */       if (isVirtualMachine)
/*      */       {
/*  220 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID#tabId-" + tabId + "&periodicity-0&granularity-5", true);
/*      */       }
/*      */       
/*      */ 
/*  224 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*      */ 
/*  228 */     if ((request.getParameter("managedprocess") != null) && ((request.getParameter("managedprocess").equals("true")) || (request.getParameter("managedprocess").equals("false"))))
/*      */     {
/*  230 */       boolean managed = true;
/*  231 */       String unmanageAndResetProcess = request.getParameter("unmanageAndResetProcess");
/*  232 */       Vector residVector = new Vector();
/*  233 */       if (request.getParameter("managedprocess").equals("false"))
/*      */       {
/*  235 */         managed = false;
/*      */       }
/*  237 */       for (String processid : processids)
/*      */       {
/*  239 */         if (managed)
/*      */         {
/*  241 */           DataCollectionControllerUtil.updateManageMonitors(new String[0], processid);
/*      */         }
/*      */         else
/*      */         {
/*  245 */           DataCollectionControllerUtil.updateUnManageMonitors(new String[0], processid);
/*      */         }
/*  247 */         residVector.add(processid);
/*      */       }
/*  249 */       if ((!managed) && (unmanageAndResetProcess != null) && (unmanageAndResetProcess.equals("true")))
/*      */       {
/*  251 */         HashSet residHset = new HashSet();
/*      */         try
/*      */         {
/*  254 */           residHset = FaultUtil.getAllChildResids(residVector, new HashSet());
/*  255 */           FaultUtil.deleteAlertsForResource(residHset);
/*  256 */           ExecuteSDPTicketAction.closeTicketUsingRestApi(new ArrayList(residHset), FormatUtil.getString("am.webclient.action.ticket.closed.msg", new String[] { FormatUtil.getString("am.modowntime.reason.unmanaged") }), true);
/*  257 */           new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), Integer.parseInt(healthid), System.currentTimeMillis());
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/*  261 */           ee.printStackTrace();
/*      */         }
/*      */       }
/*  264 */       String message = managed ? FormatUtil.getString("am.webclient.apikey.managesuccess.message") : FormatUtil.getString("am.webclient.apikey.unmanagesuccess.message");
/*  265 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", message));
/*  266 */       saveMessages(request, messages);
/*  267 */       if (Constants.sqlManager)
/*      */       {
/*  269 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/*  271 */       if (isVirtualMachine)
/*      */       {
/*  273 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID#tabId-" + tabId + "&periodicity-0&granularity-5", true);
/*      */       }
/*      */       
/*      */ 
/*  277 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*  280 */     boolean deleted = true;
/*  281 */     Statement stmt = null;
/*      */     try {
/*  283 */       stmt = AMConnectionPool.getConnection().createStatement();
/*      */     }
/*      */     catch (Exception e) {
/*  286 */       e.printStackTrace();
/*      */     }
/*  288 */     Vector resIds = new Vector(Arrays.asList(processids));
/*  289 */     String conditionStr = DBUtil.getInConditonQueryfromList(resIds);
/*  290 */     AMLog.debug("HostResourceAction.removeProcess called with processids : " + conditionStr);
/*      */     
/*  292 */     stmt.addBatch("delete from AM_HOST_PROCESS_INFO where RESOURCEID in " + conditionStr);
/*  293 */     stmt.addBatch("delete from AM_HOST_PROCESS_INSTANCE where RESOURCEID in " + conditionStr);
/*  294 */     stmt.addBatch("delete from AM_HOST_PROCESS_CPUMEM where RESOURCEID in " + conditionStr);
/*      */     
/*  296 */     stmt.addBatch("delete from AM_ATTRIBUTEACTIONMAPPER WHERE ID in " + conditionStr);
/*  297 */     stmt.addBatch("delete from AM_ATTRIBUTETHRESHOLDMAPPER WHERE ID in " + conditionStr);
/*      */     
/*  299 */     stmt.addBatch("delete from AM_RCAMAPPER where CHILDRESOURCEID in " + conditionStr);
/*      */     
/*  301 */     stmt.addBatch("delete from Event where SOURCE in " + conditionStr);
/*  302 */     stmt.addBatch("delete from Alert where SOURCE in " + conditionStr);
/*      */     
/*  304 */     stmt.addBatch("delete from AM_PARENTCHILDMAPPER where CHILDID in " + conditionStr);
/*  305 */     stmt.addBatch("delete from AM_ManagedObject where RESOURCEID in " + conditionStr);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  314 */     List<String> attrList = new ArrayList(DBUtil.getAttributeDetailsForResourceType("Process", 0, true).keySet());
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  328 */     for (int i = 0; i < processids.length; i++)
/*      */     {
/*  330 */       String processid = processids[i];
/*      */       try
/*      */       {
/*  333 */         for (String attrId : attrList)
/*      */         {
/*  335 */           String entity = processid + "_" + attrId;
/*  336 */           FaultUtil.deleteEventFromCache(entity);
/*      */         }
/*      */         
/*  339 */         AMCacheHandler.removeThresholdConfigurationForResourceId(processid);
/*  340 */         ArrayList<String> list = DBUtil.getArchiveingTableNamesForResource(processid);
/*  341 */         if (list != null)
/*      */         {
/*  343 */           for (String tableName : list)
/*      */           {
/*  345 */             stmt.addBatch("delete from " + tableName + " where RESID=" + processid);
/*      */           }
/*      */         }
/*  348 */         HostSnmpDataCollector.removeProcessFromMem(resourceid, processid);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  352 */         deleted = false;
/*  353 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  360 */       stmt.executeBatch();
/*  361 */       stmt.clearBatch();
/*  362 */       stmt.close();
/*  363 */       deleted = true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  367 */       deleted = false;
/*  368 */       System.out.println("HOSTRESOURCE : PROBLEM IN DELETING THE DATA IN THE HOST TABLES FOR PROCESS");
/*  369 */       e.printStackTrace();
/*      */     }
/*      */     
/*  372 */     if (deleted)
/*      */     {
/*  374 */       rcaAnalyser.applyRCA(Integer.parseInt(resourceid), Integer.parseInt(healthid), System.currentTimeMillis());
/*  375 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", "Processes deleted successfully ."));
/*  376 */       saveMessages(request, messages);
/*  377 */       if (Constants.sqlManager)
/*      */       {
/*  379 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/*  381 */       if (isVirtualMachine)
/*      */       {
/*  383 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID#tabId-" + tabId + "&periodicity-0&granularity-5", true);
/*      */       }
/*      */       
/*      */ 
/*  387 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  392 */     rcaAnalyser.applyRCA(Integer.parseInt(resourceid), Integer.parseInt(healthid), System.currentTimeMillis());
/*  393 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", "Problem in deleting Processes."));
/*  394 */     saveMessages(request, messages);
/*  395 */     if (Constants.sqlManager)
/*      */     {
/*  397 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */     }
/*  399 */     if (isVirtualMachine)
/*      */     {
/*  401 */       return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID#tabId-" + tabId + "&periodicity-0&granularity-5", true);
/*      */     }
/*      */     
/*      */ 
/*  405 */     return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward removeDrive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name, String resourceid)
/*      */     throws Exception
/*      */   {
/*  415 */     String url = "/jsp/RemoveDisk.jsp?resourceid=" + resourceid + "&deleted=true";
/*  416 */     return new ActionForward(url);
/*      */   }
/*      */   
/*      */   public ActionForward removeIISData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name, String resourceid)
/*      */     throws Exception
/*      */   {
/*  422 */     String url = "/jsp/RemoveSites.jsp?resourceid=" + resourceid + "&deleted=true";
/*  423 */     return new ActionForward(url);
/*      */   }
/*      */   
/*      */   public ActionForward addProcessScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  429 */     request.setAttribute("category", getCategory(request.getParameter("resourceid"), request));
/*      */     
/*  431 */     request.setAttribute("configured", "true");
/*  432 */     return new ActionForward("/jsp/HostResourceConfig.jsp");
/*      */   }
/*      */   
/*      */   private Vector getCategory(String resourceid, HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/*  439 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  440 */       if (modetails.next())
/*      */       {
/*  442 */         String name = modetails.getString("RESOURCENAME");
/*  443 */         String displayname = modetails.getString("DISPLAYNAME");
/*  444 */         HostDetails host = (HostDetails)this.api.getCollectData(name, "HOST");
/*  445 */         Vector v = host.getCategorysAsVector();
/*  446 */         v.add("Process");
/*  447 */         String type = modetails.getString("TYPE");
/*  448 */         request.setAttribute("ids", getAttributeIDS(type));
/*  449 */         return v;
/*      */       }
/*      */       
/*      */ 
/*  453 */       return new Vector();
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  458 */       e.printStackTrace(); }
/*  459 */     return new Vector();
/*      */   }
/*      */   
/*      */   public ActionForward getServicesList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  465 */     Vector v = new Vector();
/*      */     try
/*      */     {
/*  468 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject ,AM_HOSTINFO where AM_ManagedObject.RESOURCEID=" + request.getParameter("resourceid") + " and  AM_ManagedObject.RESOURCEID=AM_HOSTINFO.RESOURCEID");
/*  469 */       if (modetails.next())
/*      */       {
/*  471 */         String name = modetails.getString("RESOURCENAME");
/*  472 */         String displayname = modetails.getString("DISPLAYNAME");
/*  473 */         String mode = modetails.getString("MODE");
/*  474 */         request.setAttribute("displayname", displayname);
/*  475 */         HostDetails host = (HostDetails)this.api.getCollectData(name, "HOST");
/*  476 */         String type = host.getResourceType();
/*      */         
/*  478 */         HostResourceMonitor hostresource = new HostResourceMonitor();
/*  479 */         v = hostresource.getAllServicesInHost(host);
/*  480 */         setlistVector(v);
/*  481 */         request.setAttribute("servicelist", v);
/*  482 */         return new ActionForward("/jsp/HostResourceServicesList.jsp");
/*      */       }
/*      */       
/*      */ 
/*  486 */       setlistVector(v);
/*  487 */       request.setAttribute("servicelist", v);
/*  488 */       return new ActionForward("/jsp/HostResourceServicesList.jsp");
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  493 */       e.printStackTrace();
/*  494 */       setlistVector(v);
/*  495 */       request.setAttribute("servicelist", v); }
/*  496 */     return new ActionForward("/jsp/HostResourceServicesList.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getScheduledTasksList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  503 */     Vector<Vector<String>> schTaskList = new Vector();
/*  504 */     ResultSet modetails = null;
/*      */     try
/*      */     {
/*  507 */       String resourceId = request.getParameter("resourceid");
/*  508 */       modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject ,AM_HOSTINFO where AM_ManagedObject.RESOURCEID=" + resourceId + " and  AM_ManagedObject.RESOURCEID=AM_HOSTINFO.RESOURCEID");
/*  509 */       if (modetails.next())
/*      */       {
/*  511 */         String name = modetails.getString("RESOURCENAME");
/*  512 */         String displayname = modetails.getString("DISPLAYNAME");
/*  513 */         String resourceid = request.getParameter("resourceid");
/*  514 */         request.setAttribute("displayname", displayname);
/*  515 */         HostDetails host = (HostDetails)this.api.getCollectData(name, "HOST");
/*  516 */         String type = host.getResourceType();
/*      */         
/*  518 */         HostResourceMonitor hostresource = new HostResourceMonitor();
/*  519 */         schTaskList = ScheduledTasksUtil.getScheduledTasksForAdding(host, resourceid);
/*  520 */         setlistVector(schTaskList);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  525 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  529 */       AMConnectionPool.closeResultSet(modetails);
/*  530 */       request.setAttribute("tasksList", schTaskList);
/*      */     }
/*  532 */     return new ActionForward("/jsp/HostResourceScheduledTasksList.jsp");
/*      */   }
/*      */   
/*      */   public boolean addServices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  538 */     AMThresholdApplier apply = new AMThresholdApplier();
/*  539 */     int M = 0;
/*  540 */     int n = 0;
/*  541 */     String displayname = null;
/*  542 */     String sname = null;
/*      */     
/*      */ 
/*      */ 
/*  546 */     ArrayList list = new ArrayList();
/*  547 */     String[] add = request.getParameterValues("services");
/*  548 */     M = add.length;
/*  549 */     for (int j = 0; j < M; j++)
/*      */     {
/*      */ 
/*  552 */       list.add(add[j]);
/*      */     }
/*  554 */     for (int k = 0; k < M; k++)
/*      */     {
/*      */ 
/*      */       try
/*      */       {
/*  559 */         n = Integer.parseInt((String)list.get(k));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  563 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*  567 */       Vector plist = (Vector)this.List.get(n);
/*  568 */       sname = (String)plist.get(0);
/*  569 */       displayname = (String)plist.get(1);
/*  570 */       String servicestatus = (String)plist.get(2);
/*  571 */       ActionMessages messages = new ActionMessages();
/*  572 */       ActionErrors errors = new ActionErrors();
/*  573 */       String resourceid = request.getParameter("resourceid");
/*  574 */       String name = null;
/*  575 */       String resourceType = null;
/*      */       try
/*      */       {
/*  578 */         ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  579 */         if (modetails.next())
/*      */         {
/*  581 */           name = modetails.getString("RESOURCENAME");
/*  582 */           resourceType = modetails.getString("TYPE");
/*      */ 
/*      */ 
/*      */         }
/*  586 */         else if (k == M - 1)
/*      */         {
/*  588 */           return false;
/*      */         }
/*      */         
/*  591 */         if (modetails != null) {
/*  592 */           AMConnectionPool.closeStatement(modetails);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  596 */         e.printStackTrace();
/*      */       }
/*  598 */       this.mo.getReloadPeriod(request);
/*  599 */       String haid = request.getParameter("haid");
/*  600 */       String appname = request.getParameter("appName");
/*      */       
/*  602 */       String query = null;
/*      */       try
/*      */       {
/*  605 */         int serviceid = -1;
/*  606 */         AMAttributesDependencyAdder attributesAdder = new AMAttributesDependencyAdder();
/*      */         try {
/*  608 */           AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  609 */           AMManagedObject ammo = new AMManagedObject();
/*  610 */           ammo.setRESOURCENAME(sname);
/*  611 */           ammo.setType("Service");
/*  612 */           ammo.setDISPLAYNAME(displayname);
/*  613 */           ammo.setDESCRIPTION("Service Monitoring for System is added");
/*  614 */           dao.create(ammo);
/*  615 */           serviceid = ammo.getRESOURCEID();
/*  616 */           attributesAdder.addInterDependentAttributes(serviceid);
/*      */           
/*      */ 
/*  619 */           DBUtil.insertParentChildMapper(Integer.parseInt(resourceid), serviceid);attributesAdder.addDependentAttributes(Integer.parseInt(resourceid), serviceid);
/*  620 */           long timeofadding = System.currentTimeMillis();
/*      */           
/*      */ 
/*  623 */           if (servicestatus.indexOf("True") != -1) {
/*  624 */             apply.applyThreshold(serviceid, 731, 1L, timeofadding, FormatUtil.getString("am.webclient.host.ServiceRunning.text"));
/*      */           } else {
/*  626 */             apply.applyThreshold(serviceid, 731, 0L, timeofadding, FormatUtil.getString("am.webclient.host.ServiceNotRunning.text"));
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  631 */           e.printStackTrace();
/*      */         }
/*  633 */         if (serviceid != -1)
/*      */         {
/*  635 */           if (k == M - 1)
/*      */           {
/*  637 */             return true;
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  642 */         else if (k == M - 1)
/*      */         {
/*  644 */           return true;
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  650 */         e.printStackTrace();
/*  651 */         if (k == M - 1)
/*      */         {
/*  653 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  658 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean addScheduledTasks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  665 */     int M = 0;
/*  666 */     String taskName = null;
/*  667 */     String resourceName = (String)request.getAttribute("hostResName");
/*  668 */     String resourceid = request.getParameter("resourceid");
/*  669 */     String[] add = request.getParameterValues("selectedtasks");
/*  670 */     M = add.length;
/*  671 */     for (int k = 0; k < M; k++)
/*      */     {
/*  673 */       int n = 0;
/*      */       try
/*      */       {
/*  676 */         n = Integer.parseInt(add[k]);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  680 */         e.printStackTrace();
/*  681 */         continue;
/*      */       }
/*      */       
/*  684 */       Vector tList = (Vector)this.List.get(n);
/*  685 */       taskName = (String)tList.get(0);
/*      */       
/*  687 */       this.mo.getReloadPeriod(request);
/*      */       try
/*      */       {
/*  690 */         DataCollectionDBUtil.CheckandinsertAMMO(resourceName + "-" + taskName, "ScheduledTask", taskName, Integer.parseInt(resourceid), true);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  694 */         e.printStackTrace();
/*  695 */         return false;
/*      */       }
/*      */     }
/*  698 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward actiononServices(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String performaction)
/*      */     throws Exception
/*      */   {
/*  706 */     response.setContentType("text/html; charset=UTF-8");
/*  707 */     ActionMessages messages = new ActionMessages();
/*  708 */     ActionErrors errors = new ActionErrors();
/*  709 */     AMRCAnalyser rcaAnalyser = new AMRCAnalyser();
/*  710 */     String resourceid = request.getParameter("resourceid");
/*  711 */     Vector v = getCategory(resourceid, request);
/*  712 */     Properties p = (Properties)request.getAttribute("ids");
/*  713 */     String availid = p.getProperty("Availability");
/*  714 */     String healthid = p.getProperty("Health");
/*  715 */     this.mo.getReloadPeriod(request);
/*  716 */     String haid = request.getParameter("haid");
/*  717 */     String appname = request.getParameter("appName");
/*  718 */     String resourcename = request.getParameter("name");
/*  719 */     String managedservice = request.getParameter("managedservice");
/*  720 */     String unmanageAndResetService = request.getParameter("unmanageAndResetService");
/*  721 */     String[] serviceids = request.getParameterValues("services");
/*  722 */     if ((managedservice != null) && ((managedservice.equals("true")) || (managedservice.equals("false"))))
/*      */     {
/*  724 */       HashSet residHset = new HashSet();
/*  725 */       boolean managed = true;
/*  726 */       if (managedservice.equals("false"))
/*      */       {
/*  728 */         managed = false;
/*      */       }
/*  730 */       for (String serviceid : serviceids)
/*      */       {
/*  732 */         if (managed)
/*      */         {
/*  734 */           DataCollectionControllerUtil.updateManageMonitors(new String[0], serviceid);
/*      */         }
/*      */         else
/*      */         {
/*  738 */           DataCollectionControllerUtil.updateUnManageMonitors(new String[0], serviceid);
/*      */         }
/*  740 */         residHset.add(serviceid);
/*      */       }
/*  742 */       if ((!managed) && (unmanageAndResetService != null) && (unmanageAndResetService.equals("true")))
/*      */       {
/*      */         try
/*      */         {
/*  746 */           FaultUtil.deleteAlertsForResource(residHset);
/*  747 */           ExecuteSDPTicketAction.closeTicketUsingRestApi(new ArrayList(residHset), FormatUtil.getString("am.webclient.action.ticket.closed.msg", new String[] { FormatUtil.getString("am.modowntime.reason.unmanaged") }), true);
/*  748 */           new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), Integer.parseInt(healthid), System.currentTimeMillis());
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/*  752 */           ee.printStackTrace();
/*      */         }
/*      */       }
/*  755 */       String message = managed ? FormatUtil.getString("am.webclient.apikey.managesuccess.message") : FormatUtil.getString("am.webclient.apikey.unmanagesuccess.message");
/*  756 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", message));
/*  757 */       saveMessages(request, messages);
/*  758 */       if (Constants.sqlManager)
/*      */       {
/*  760 */         String sqlmanid = request.getParameter("msid");
/*  761 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/*  763 */       return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */     }
/*  765 */     boolean deleted = true;
/*  766 */     if ((performaction.equalsIgnoreCase("Edit")) || (performaction.equalsIgnoreCase("EditSave")))
/*      */     {
/*      */ 
/*  769 */       request.setAttribute("category", getCategory(request.getParameter("resourceid"), request));
/*  770 */       if (performaction.equalsIgnoreCase("Edit"))
/*      */       {
/*  772 */         String servicename = request.getParameter("servicename");
/*  773 */         String displayname = request.getParameter("displayname");
/*  774 */         String serviceid = request.getParameter("serviceid");
/*  775 */         ((DynaActionForm)form).set("processname", servicename);
/*  776 */         ((DynaActionForm)form).set("displayname", displayname);
/*  777 */         request.setAttribute("serviceid", serviceid);
/*  778 */         return new ActionForward(".hostresource.editservice");
/*      */       }
/*  780 */       if (performaction.equalsIgnoreCase("EditSave"))
/*      */       {
/*  782 */         String servicename = request.getParameter("processname");
/*  783 */         String displayname = request.getParameter("displayname");
/*  784 */         String serviceid = request.getParameter("serviceid");
/*      */         try
/*      */         {
/*  787 */           AMConnectionPool.executeUpdateStmt("update AM_ManagedObject set RESOURCENAME='" + servicename + "', DISPLAYNAME='" + displayname + "' where RESOURCEID=" + serviceid);
/*      */ 
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  792 */           ex.printStackTrace();
/*      */         }
/*  794 */         if (Constants.sqlManager)
/*      */         {
/*  796 */           String sqlmanid = request.getParameter("msid");
/*  797 */           return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */         }
/*      */         
/*  800 */         return new ActionForward("/HostResource.do?name=" + resourcename + "&haid=" + haid + "&appName=" + appname + "&resourceid=" + resourceid, true);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  805 */     if (performaction.equalsIgnoreCase("Delete"))
/*      */     {
/*  807 */       Hashtable deletiondetails = HostUtil.windowsServiceActions(serviceids, null, performaction, 731, resourceid, healthid, false);
/*  808 */       String message = (String)deletiondetails.get("appmanager.message");
/*  809 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", message));
/*  810 */       saveMessages(request, messages);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  815 */       HostDetails host = null;
/*  816 */       String ostype = null;
/*  817 */       Properties hostProps = new Properties();
/*  818 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject ,AM_HOSTINFO where AM_ManagedObject.RESOURCEID=" + request.getParameter("resourceid") + " and  AM_ManagedObject.RESOURCEID=AM_HOSTINFO.RESOURCEID");
/*  819 */       if (modetails.next())
/*      */       {
/*  821 */         String name = modetails.getString("RESOURCENAME");
/*  822 */         host = (HostDetails)this.api.getCollectData(name, "HOST");
/*  823 */         ostype = modetails.getString("TYPE");
/*  824 */         if ("VirtualMachine".equals(ostype))
/*      */         {
/*  826 */           host.setResourceName(host.getDisplayName());
/*      */         }
/*  828 */         hostProps = host.getProperties();
/*  829 */         hostProps.setProperty("userName", "\"" + hostProps.getProperty("userName") + "\"");
/*  830 */         hostProps.setProperty("password", "\"" + hostProps.getProperty("password") + "\"");
/*      */       }
/*      */       
/*  833 */       AMConnectionPool.closeStatement(modetails);
/*      */       
/*  835 */       Hashtable resultofaction = HostUtil.windowsServiceActions(serviceids, hostProps, performaction, 731, resourceid, ostype, false);
/*  836 */       request.setAttribute("resultofaction", resultofaction);
/*      */     }
/*      */     
/*  839 */     String ServiceQuery = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE='Service'";
/*  840 */     ArrayList configuredservices = this.mo.getRows(ServiceQuery);
/*  841 */     if (configuredservices.size() > 0)
/*  842 */       request.setAttribute("windowsservices", configuredservices);
/*  843 */     response.setContentType("text/xml");
/*  844 */     response.setHeader("Cache-Control", "no-cache");
/*  845 */     response.getWriter().write("success");
/*  846 */     if ((request.getParameter("isVM") != null) && (Boolean.parseBoolean(request.getParameter("isVM"))))
/*      */     {
/*  848 */       request.setAttribute("windowsServices", configuredservices);
/*  849 */       return new ActionForward("/manageVMInstances.do?method=getVMTabs&resourceid=" + resourceid);
/*      */     }
/*      */     
/*      */ 
/*  853 */     return new ActionForward("/jsp/Windows_Services.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward getProcessList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  861 */     Vector v = new Vector();
/*  862 */     String resourceid = request.getParameter("resourceid");
/*  863 */     String addtotemplate = request.getParameter("addtotemplate");
/*      */     
/*      */     try
/*      */     {
/*  867 */       ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject ,AM_HOSTINFO where AM_ManagedObject.RESOURCEID=" + request.getParameter("resourceid") + " and  AM_ManagedObject.RESOURCEID=AM_HOSTINFO.RESOURCEID");
/*  868 */       if (modetails.next())
/*      */       {
/*  870 */         String name = modetails.getString("RESOURCENAME");
/*  871 */         String displayname = modetails.getString("DISPLAYNAME");
/*  872 */         String mode = modetails.getString("MODE");
/*  873 */         request.setAttribute("displayname", displayname);
/*  874 */         HostDetails host = (HostDetails)this.api.getCollectData(name, "HOST");
/*  875 */         String type = host.getResourceType();
/*  876 */         if ((type.toLowerCase().indexOf("windows") != -1) && (mode.equals("SNMP")))
/*      */         {
/*  878 */           request.setAttribute("msg", "Command & Args is not available in the SNMP mode of monitoring in windows systems.");
/*      */         }
/*  880 */         HostResourceMonitor hostresource = new HostResourceMonitor();
/*  881 */         v = hostresource.getRunningProcessInHost(host);
/*  882 */         setlistVector(v);
/*  883 */         request.setAttribute("processlist", v);
/*  884 */         String show = request.getParameter("show");
/*  885 */         if ((show != null) && (show.equals("true")))
/*      */         {
/*  887 */           return new ActionForward("/jsp/HostResourceProcessList.jsp?resType=" + type + "&show=true");
/*      */         }
/*      */         
/*      */ 
/*  891 */         return new ActionForward("/jsp/HostResourceProcessList.jsp");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  896 */       request.setAttribute("processlist", v);
/*  897 */       setlistVector(v);
/*  898 */       return new ActionForward("/jsp/HostResourceProcessList.jsp");
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  903 */       e.printStackTrace();
/*  904 */       setlistVector(v);
/*  905 */       request.setAttribute("processlist", v); }
/*  906 */     return new ActionForward("/jsp/HostResourceProcessList.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward getAllServerList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  913 */       String selectedtype = request.getParameter("optionSelected");
/*  914 */       String templatetype = request.getParameter("templatetype");
/*  915 */       AMTemplateConfiguration templateconfig = AMTemplateFactory.getAMTemplateConfiguration(Integer.parseInt(templatetype));
/*  916 */       List serverlist = templateconfig.getAllAvailableServerList(selectedtype, request);
/*  917 */       if ((serverlist != null) && (serverlist.size() > 0)) {
/*  918 */         request.setAttribute("serverdetaillist", serverlist.get(0));
/*  919 */         request.setAttribute("servertypei18nkey", Constants.getServerTypei18nKeys());
/*      */       }
/*      */     }
/*      */     catch (Exception exc) {
/*  923 */       exc.printStackTrace();
/*      */     }
/*  925 */     return mapping.findForward("serverlist");
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean addProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/*  933 */       int M = 0;
/*  934 */       int n = 0;
/*  935 */       String displayname = null;
/*  936 */       String processname = null;
/*  937 */       String command = null;
/*  938 */       String pcpu = null;
/*  939 */       String pmem = null;
/*  940 */       ArrayList list = new ArrayList();
/*  941 */       String[] resources = request.getParameterValues("selectedMonitors");
/*      */       
/*  943 */       if ((request.getParameter("edit") != null) && (request.getParameter("edit").equals("true")))
/*      */       {
/*  945 */         M += 1;
/*      */       }
/*      */       else
/*      */       {
/*  949 */         String[] add = request.getParameterValues("process");
/*      */         
/*  951 */         M = add.length;
/*  952 */         for (int j = 0; j < M; j++)
/*      */         {
/*      */ 
/*  955 */           list.add(add[j]);
/*      */         }
/*      */       }
/*  958 */       for (int k = 0; k < M; k++)
/*      */       {
/*  960 */         if ((request.getParameter("edit") == null) || (!request.getParameter("edit").equals("true")))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  966 */           int p = 0;
/*      */           
/*      */           try
/*      */           {
/*  970 */             p = Integer.parseInt((String)list.get(k));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  974 */             e.printStackTrace();
/*      */           }
/*  976 */           n = p;
/*  977 */           Vector plist = (Vector)this.List.get(n);
/*  978 */           processname = (String)plist.get(0);
/*  979 */           command = (String)plist.get(3);
/*  980 */           pcpu = (String)plist.get(1);
/*  981 */           pmem = (String)plist.get(2);
/*  982 */           displayname = processname;
/*      */         }
/*  984 */         ActionMessages messages = new ActionMessages();
/*  985 */         ActionErrors errors = new ActionErrors();
/*  986 */         String resourceid = request.getParameter("resourceid");
/*  987 */         String name = null;
/*  988 */         String resourceType = null;
/*      */         
/*      */ 
/*      */         try
/*      */         {
/*  993 */           ResultSet modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/*  994 */           if (modetails.next())
/*      */           {
/*  996 */             name = modetails.getString("RESOURCENAME");
/*  997 */             resourceType = modetails.getString("TYPE");
/*      */ 
/*      */ 
/*      */           }
/* 1001 */           else if (k == M - 1)
/*      */           {
/* 1003 */             return false;
/*      */           }
/*      */           
/* 1006 */           if (modetails != null) {
/* 1007 */             AMConnectionPool.closeStatement(modetails);
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 1011 */           e.printStackTrace();
/*      */         }
/* 1013 */         this.mo.getReloadPeriod(request);
/* 1014 */         if ((request.getParameter("edit") != null) && (request.getParameter("edit").equals("true")))
/*      */         {
/* 1016 */           displayname = request.getParameter("displayname");
/* 1017 */           processname = request.getParameter("processname");
/* 1018 */           command = request.getParameter("command");
/* 1019 */           command = command != null ? FormatUtil.findAndReplaceAll(command, "&quot;", "\"") : command;
/* 1020 */           String updateQuery = "update AM_ManagedObject set DISPLAYNAME='" + displayname + "' where RESOURCEID=" + request.getParameter("processid");
/* 1021 */           AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */         }
/*      */         
/* 1024 */         String haid = request.getParameter("haid");
/* 1025 */         String appname = request.getParameter("appName");
/* 1026 */         String resourcename = request.getParameter("resourcename");
/* 1027 */         String query = null;
/* 1028 */         boolean edit = false;
/* 1029 */         if ((request.getParameter("edit") != null) && (request.getParameter("edit").equals("true")))
/*      */         {
/* 1031 */           edit = true;
/*      */         }
/*      */         
/* 1034 */         if (edit)
/*      */         {
/* 1036 */           query = "select AM_HOST_PROCESS_INFO.DISPLAYNAME from AM_HOST_PROCESS_INFO where AM_HOST_PROCESS_INFO.PARENTID=" + resourceid + " and  AM_HOST_PROCESS_INFO.RESOURCEID !=" + request.getParameter("processid") + " and AM_HOST_PROCESS_INFO.DISPLAYNAME='" + displayname + "'";
/*      */         }
/*      */         else
/*      */         {
/* 1040 */           query = "select AM_HOST_PROCESS_INFO.DISPLAYNAME from AM_HOST_PROCESS_INFO where AM_HOST_PROCESS_INFO.PARENTID=" + resourceid + " and  AM_HOST_PROCESS_INFO.DISPLAYNAME='" + displayname + "'";
/*      */         }
/*      */         try
/*      */         {
/* 1044 */           ResultSet checkingprocess = AMConnectionPool.executeQueryStmt(query);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1057 */           if (!edit)
/*      */           {
/* 1059 */             if (resources != null) {
/*      */               int processid;
/* 1061 */               for (int i = 0; i < resources.length; i++)
/*      */               {
/* 1063 */                 processid = insertAMMO(displayname, Integer.parseInt(resources[i]));
/*      */               }
/*      */             }
/* 1066 */             int processid = insertAMMO(displayname, Integer.parseInt(resourceid));
/* 1067 */             if (processid != -1)
/*      */             {
/* 1069 */               HostResourceMonitor hm = new HostResourceMonitor();
/* 1070 */               PreparedStatement ps = null;
/*      */               
/* 1072 */               Connection con = null;
/*      */               try
/*      */               {
/* 1075 */                 con = AMConnectionPool.getConnection();
/* 1076 */                 ps = con.prepareStatement("insert into AM_HOST_PROCESS_INFO values (?,?,?,?,?,'Contains')");
/*      */                 
/* 1078 */                 ps.setInt(1, Integer.parseInt(resourceid));
/* 1079 */                 ps.setInt(2, processid);
/* 1080 */                 ps.setString(3, displayname);
/* 1081 */                 ps.setString(4, processname);
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1087 */                 if (command == null)
/*      */                 {
/* 1089 */                   ps.setString(5, "");
/*      */                 }
/* 1091 */                 else if ((command != null) && (command.equals("null")))
/*      */                 {
/* 1093 */                   ps.setString(5, "");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1097 */                   String modified = command;
/*      */                   
/* 1099 */                   modified = FormatUtil.findReplace(modified, "'", "'");
/* 1100 */                   ps.setString(5, modified);
/*      */                 }
/* 1102 */                 ps.executeUpdate();
/*      */                 
/* 1104 */                 HostDetails host = (HostDetails)this.api.getCollectData(name, "HOST");
/* 1105 */                 if (host != null)
/*      */                 {
/*      */ 
/* 1108 */                   Vector v = hm.getRunningProcessInHost(host);
/* 1109 */                   Vector totest = new Vector();
/* 1110 */                   for (int i = 0; i < v.size(); i++)
/*      */                   {
/* 1112 */                     Vector test = (Vector)v.get(i);
/* 1113 */                     String temp = "";
/* 1114 */                     for (int j = 0; j < test.size(); j++)
/*      */                     {
/* 1116 */                       temp = temp + (String)test.get(j) + " ";
/*      */                     }
/* 1118 */                     if (!temp.equals(""))
/*      */                     {
/* 1120 */                       totest.add(temp);
/*      */                     }
/*      */                   }
/* 1123 */                   long timeofadding = System.currentTimeMillis();
/* 1124 */                   if ((v.size() != 1) && (v.size() != 0))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1132 */                     double total_memory = 0.0D;
/* 1133 */                     if ((resourceType.equals("HP-UX")) || (resourceType.equals("HP-TRU64")))
/*      */                     {
/* 1135 */                       double available_memory = 0.0D;double available_memory_percentage = 0.0D;
/*      */                       
/* 1137 */                       query = "select PHYMEMUTIL, PHYMEMUTILMB from HostCpuMemDataCollected where RESOURCEID=" + resourceid + " order by COLLECTIONTIME desc";
/* 1138 */                       query = DBQueryUtil.getTopNValues(query, "1");
/* 1139 */                       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1140 */                       while (rs.next())
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1150 */                         available_memory = rs.getDouble(2);
/* 1151 */                         available_memory_percentage = rs.getDouble(1);
/*      */                       }
/* 1153 */                       AMConnectionPool.closeStatement(rs);
/* 1154 */                       if (available_memory != 0.0D)
/*      */                       {
/* 1156 */                         total_memory = available_memory * 1024.0D * 1024.0D * 100.0D / available_memory_percentage;
/*      */ 
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/*      */ 
/* 1171 */                 e.printStackTrace();
/*      */               }
/*      */               
/* 1174 */               if (k == M - 1)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1181 */                 return true;
/*      */               }
/*      */               
/*      */ 
/*      */             }
/* 1186 */             else if (k == M - 1)
/*      */             {
/* 1188 */               return true;
/*      */             }
/*      */             
/*      */           }
/*      */           else
/*      */           {
/* 1194 */             HostResourceMonitor hm = new HostResourceMonitor();
/* 1195 */             PreparedStatement ps = null;
/* 1196 */             Connection con = null;
/*      */             
/*      */             try
/*      */             {
/* 1200 */               con = AMConnectionPool.getConnection();
/* 1201 */               ps = con.prepareStatement("update AM_HOST_PROCESS_INFO set DISPLAYNAME=? , PROCESSNAME=?,COMMAND=? where AM_HOST_PROCESS_INFO.RESOURCEID=?");
/* 1202 */               ps.setInt(4, Integer.parseInt(request.getParameter("processid")));
/* 1203 */               ps.setString(1, displayname);
/* 1204 */               ps.setString(2, processname);
/* 1205 */               String cmd = "";
/* 1206 */               if (command == null)
/*      */               {
/* 1208 */                 ps.setString(3, "");
/*      */               }
/* 1210 */               else if ((command != null) && (command.equals("null")))
/*      */               {
/* 1212 */                 ps.setString(3, "");
/*      */               }
/*      */               else
/*      */               {
/* 1216 */                 String modified = command;
/*      */                 
/* 1218 */                 if (DBQueryUtil.getDBType().equals("mysql"))
/*      */                 {
/* 1220 */                   modified = FormatUtil.findReplace(modified, "'", "'");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1224 */                   modified = FormatUtil.findReplace(modified, "'", "''");
/*      */                 }
/* 1226 */                 ps.setString(3, modified);
/* 1227 */                 cmd = modified;
/*      */               }
/* 1229 */               ps.executeUpdate();
/* 1230 */               String updatequery = "update AM_HOST_PROCESS_INFO set DISPLAYNAME='" + displayname + "' , PROCESSNAME='" + processname + "',COMMAND='" + cmd + "' where AM_HOST_PROCESS_INFO.RESOURCEID=" + request.getParameter("processid");
/* 1231 */               EnterpriseUtil.addUpdateQueryToFile(updatequery);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1235 */               e.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/* 1239 */             if (k == M - 1)
/*      */             {
/*      */ 
/* 1242 */               return true;
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1249 */           e.printStackTrace();
/* 1250 */           if (k == M - 1)
/*      */           {
/* 1252 */             return false;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1260 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1263 */     return false;
/*      */   }
/*      */   
/*      */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1268 */     String sqlmanid = request.getParameter("sqlmanid");
/* 1269 */     ActionMessages messages = new ActionMessages();
/* 1270 */     ActionErrors errors = new ActionErrors();
/* 1271 */     String name = null;
/* 1272 */     String displayname = null;
/* 1273 */     String resourceid = request.getParameter("resourceid");
/* 1274 */     String resourcename = request.getParameter("name");
/* 1275 */     String opmanager = null;
/* 1276 */     opmanager = request.getParameter("opmanager");
/* 1277 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1278 */     String modeofcollection = null;
/* 1279 */     int configured = 1;
/* 1280 */     boolean configuredState = false;
/* 1281 */     boolean unknownHost = false;
/* 1282 */     String hostErrorMessage = null;
/* 1283 */     String hostCategory = null;
/* 1284 */     Vector hostCategoryAsVector = new Vector();
/* 1285 */     String hostResourceName = null;
/* 1286 */     String hostResourceType = null;
/* 1287 */     String hostUserName = null;
/* 1288 */     String hostPassWord = null;
/* 1289 */     String hostPrompt = null;
/* 1290 */     String keyBasedAuth = null;
/* 1291 */     int hostPollInterval = 60;
/* 1292 */     String addtotemplate = request.getParameter("addtotemplate");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1300 */       if ((addtotemplate != null) && (resourceid == null)) {
/* 1301 */         return getAllServerList(mapping, form, request, response);
/*      */       }
/*      */       
/* 1304 */       String telnetCheck = request.getParameter("TelnetClient");
/* 1305 */       if ((telnetCheck != null) && (telnetCheck.equals("true")))
/*      */       {
/* 1307 */         CLITelnetHandler telnetHandler = CLITelnetHandler.getInstance();
/* 1308 */         CLISession telnetSession = (CLISession)telnetMapper.get(resourceid);
/* 1309 */         String resourceName = "";
/* 1310 */         String OS = "";
/* 1311 */         String query = "";
/* 1312 */         HostDetails host = null;
/* 1313 */         Properties Hostprops = null;
/* 1314 */         String command = request.getParameter("command");
/* 1315 */         if (command == null)
/*      */         {
/* 1317 */           command = "";
/*      */         }
/* 1319 */         Hashtable commands = new Hashtable();
/* 1320 */         System.out.println("Inside Telnet Session------>" + command);
/* 1321 */         query = "Select RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 1322 */         ResultSet result = AMConnectionPool.executeQueryStmt(query);
/* 1323 */         while (result.next())
/*      */         {
/* 1325 */           resourceName = result.getString(1);
/*      */         }
/*      */         
/* 1328 */         if (telnetSession == null)
/*      */         {
/* 1330 */           request.setAttribute("HostName", resourceName);
/* 1331 */           host = (HostDetails)this.api.getCollectData(resourceName, "HOST");
/* 1332 */           Hostprops = host.getProperties();
/* 1333 */           Hostprops.setProperty("targetAddress", Hostprops.getProperty("resourceName"));
/* 1334 */           ResultSet rs1 = null;
/* 1335 */           String mode = null;
/* 1336 */           int telnetSshPort = 23;
/*      */           try
/*      */           {
/* 1339 */             String detailsquery = "select * from AM_HOSTINFO where RESOURCEID=" + resourceid;
/* 1340 */             rs1 = AMConnectionPool.executeQueryStmt(detailsquery);
/* 1341 */             if (rs1.next())
/*      */             {
/* 1343 */               mode = rs1.getString("MODE");
/* 1344 */               Hostprops.setProperty("MODE", mode);
/* 1345 */               Hostprops.setProperty("TELNETPORT", String.valueOf(rs1.getInt("TELNETPORT")));
/* 1346 */               Hostprops.setProperty("port", String.valueOf(rs1.getInt("TELNETPORT")));
/* 1347 */               telnetSshPort = rs1.getInt("TELNETPORT");
/* 1348 */               String community = rs1.getString("SNMPCOMMUNITY");
/* 1349 */               Hostprops.setProperty("SNMPCOMMUNITY", community);
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/* 1360 */               AMConnectionPool.closeStatement(rs1);
/*      */             }
/*      */             catch (Exception rsexc)
/*      */             {
/* 1364 */               rsexc.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/* 1368 */             System.out.println("Host Properties------->" + Hostprops);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1354 */             e.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*      */             try
/*      */             {
/* 1360 */               AMConnectionPool.closeStatement(rs1);
/*      */             }
/*      */             catch (Exception rsexc)
/*      */             {
/* 1364 */               rsexc.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1369 */           telnetSession = telnetHandler.getNewSession(Hostprops);
/* 1370 */           String prompt = telnetSession.getInitialMessage();
/* 1371 */           String[] splitprompt = prompt.split("\n");
/* 1372 */           request.setAttribute("prompt", splitprompt[(splitprompt.length - 1)]);
/* 1373 */           telnetMapper.put(resourceid, telnetSession);
/* 1374 */           request.setAttribute("resourceid", resourceid);
/* 1375 */           return new ActionForward("/jsp/TelnetClient.jsp");
/*      */         }
/*      */         
/* 1378 */         if ((telnetSession != null) && (!command.equalsIgnoreCase("exit")))
/*      */         {
/* 1380 */           String UserName = request.getRemoteUser();
/* 1381 */           String remoteHost = request.getRemoteHost();
/* 1382 */           String prompt = telnetSession.getInitialMessage();
/* 1383 */           String[] splitprompt = prompt.split("\n");
/* 1384 */           request.setAttribute("prompt", splitprompt[(splitprompt.length - 1)]);
/* 1385 */           prompt = splitprompt[(splitprompt.length - 1)].substring(splitprompt[(splitprompt.length - 1)].length() - 2);
/* 1386 */           telnetSession.setCLIPrompt(prompt);
/* 1387 */           String output = getOutput(prompt, command, 1000, telnetSession);
/* 1388 */           request.setAttribute("output", output);
/* 1389 */           request.setAttribute("command", command);
/* 1390 */           request.setAttribute("resourceid", resourceid);
/* 1391 */           request.setAttribute("prompt", prompt);
/* 1392 */           Object dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/* 1393 */           Date date = new Date();
/* 1394 */           Constants.telnetInfo(((DateFormat)dateFormat).format(date) + " \t\t " + remoteHost + " \t\t " + UserName + " \t\t " + resourceName + " \t\t " + command);
/* 1395 */           return new ActionForward("/jsp/TelnetClient.jsp");
/*      */         }
/*      */         
/*      */ 
/* 1399 */         telnetSession.close();
/* 1400 */         telnetMapper.remove(resourceid);
/* 1401 */         System.out.println("Session related datas are deleted");
/* 1402 */         return new ActionForward("/jsp/TelnetClient.jsp");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1408 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1417 */       String NetworkInterface = request.getParameter("NetworkInterface");
/* 1418 */       if ((NetworkInterface != null) && (NetworkInterface.equals("true")))
/*      */       {
/* 1420 */         String net_resourceid = request.getParameter("net_resourceid");
/* 1421 */         String netquery = "select AM_ManagedObject.RESOURCENAME,AM_NETWORKINTERFACE.NAME,AM_NETWORKINTERFACE.SPEED,AM_NETWORKINTERFACE.STATUS,AM_HOSTINFO.MODE from AM_ManagedObject,AM_NETWORKINTERFACE,AM_PARENTCHILDMAPPER,AM_HOSTINFO where AM_NETWORKINTERFACE.RESID=" + net_resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_NETWORKINTERFACE.RESID and AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_HOSTINFO.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID order by AM_NETWORKINTERFACE.COLLECTIONTIME DESC";
/* 1422 */         netquery = DBQueryUtil.getTopNValues(netquery, 1);
/*      */         
/* 1424 */         ResultSet result = AMConnectionPool.executeQueryStmt(netquery);
/* 1425 */         HashMap network_interface = new HashMap();
/* 1426 */         String net_name = "";
/* 1427 */         String server_name = "";
/* 1428 */         String speed = "";
/* 1429 */         String status = "";
/* 1430 */         String mode = "";
/* 1431 */         if (result.next())
/*      */         {
/* 1433 */           net_name = result.getString("NAME");
/* 1434 */           server_name = result.getString("RESOURCENAME");
/* 1435 */           speed = result.getString("SPEED");
/* 1436 */           status = result.getString("STATUS");
/* 1437 */           mode = result.getString("MODE");
/*      */         }
/*      */         
/* 1440 */         AMConnectionPool.closeStatement(result);
/*      */         
/* 1442 */         network_interface.put("resid", net_resourceid);
/* 1443 */         network_interface.put("name", net_name);
/* 1444 */         network_interface.put("sname", server_name);
/* 1445 */         network_interface.put("speed", speed);
/* 1446 */         network_interface.put("status", status);
/* 1447 */         network_interface.put("mode", mode);
/* 1448 */         request.setAttribute("interface", network_interface);
/* 1449 */         return showNetworkInterface(mapping, form, request, response);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1455 */       e.printStackTrace();
/*      */     }
/* 1457 */     String removeDrive = request.getParameter("removedrive");
/* 1458 */     if ((removeDrive != null) && (removeDrive.equals("true")))
/*      */     {
/* 1460 */       String[] driveids = request.getParameterValues("drives");
/* 1461 */       String resType = request.getParameter("resType");
/* 1462 */       String hostResType = request.getParameter("hostResType");
/* 1463 */       String serverResourceid = request.getParameter("resourceid");
/* 1464 */       if ((resType != null) && ("ScheduledTask".equals(resType)))
/*      */       {
/* 1466 */         HostUtil.windowsServiceActions(driveids, null, "Delete", 739, serverResourceid, hostResType, false, true);
/*      */       }
/* 1468 */       Statement s = null;
/*      */       try
/*      */       {
/* 1471 */         s = AMConnectionPool.getConnection().createStatement();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1475 */         e.printStackTrace();
/*      */       }
/* 1477 */       for (String drive : driveids)
/*      */       {
/* 1479 */         s.addBatch("DELETE FROM AM_PARENTCHILDMAPPER WHERE CHILDID=" + drive);
/* 1480 */         s.addBatch("DELETE FROM HostDiskUtilDataCollected WHERE RESOURCEID=" + drive);
/* 1481 */         s.addBatch("DELETE FROM AM_DISK_MinMaxAvgData WHERE RESID=" + drive);
/* 1482 */         EnterpriseUtil.addUpdateQueryToFile("DELETE FROM AM_PARENTCHILDMAPPER WHERE CHILDID=" + drive);
/* 1483 */         EnterpriseUtil.addUpdateQueryToFile("DELETE FROM AM_DISK_MinMaxAvgData WHERE RESID=" + drive);
/*      */       }
/*      */       try
/*      */       {
/* 1487 */         s.executeBatch();
/* 1488 */         s.clearBatch();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1496 */         if (s != null)
/*      */         {
/*      */           try
/*      */           {
/* 1500 */             s.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1504 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/* 1508 */         isDeleted = DataCollectionControllerUtil.deleteChild(driveids);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1492 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1496 */         if (s != null)
/*      */         {
/*      */           try
/*      */           {
/* 1500 */             s.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1504 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       boolean isDeleted;
/* 1509 */       EnterpriseUtil.handleDeletionInAAM(driveids);
/* 1510 */       if (isDeleted)
/*      */       {
/* 1512 */         return removeDrive(mapping, form, request, response, resourcename, resourceid);
/*      */       }
/*      */     }
/*      */     
/* 1516 */     String removeSite = request.getParameter("removesites");
/* 1517 */     if ((removeSite != null) && (removeSite.equals("true")))
/*      */     {
/* 1519 */       String[] siteids = request.getParameterValues("sites");
/* 1520 */       Statement s = null;
/*      */       try
/*      */       {
/* 1523 */         s = AMConnectionPool.getConnection().createStatement();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1527 */         e.printStackTrace();
/*      */       }
/* 1529 */       for (String site : siteids)
/*      */       {
/* 1531 */         s.addBatch("DELETE FROM AM_PARENTCHILDMAPPER WHERE CHILDID=" + site);
/* 1532 */         s.addBatch("DELETE FROM HostDiskUtilDataCollected WHERE RESOURCEID=" + site);
/* 1533 */         s.addBatch("DELETE FROM AM_DISK_MinMaxAvgData WHERE RESID=" + site);
/* 1534 */         EnterpriseUtil.addUpdateQueryToFile("DELETE FROM AM_PARENTCHILDMAPPER WHERE CHILDID=" + site);
/* 1535 */         EnterpriseUtil.addUpdateQueryToFile("DELETE FROM AM_DISK_MinMaxAvgData WHERE RESID=" + site);
/*      */       }
/*      */       try
/*      */       {
/* 1539 */         s.executeBatch();
/* 1540 */         s.clearBatch();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1548 */         if (s != null)
/*      */         {
/*      */           try
/*      */           {
/* 1552 */             s.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1556 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/* 1560 */         isDeleted = DataCollectionControllerUtil.deleteChild(siteids);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1544 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1548 */         if (s != null)
/*      */         {
/*      */           try
/*      */           {
/* 1552 */             s.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 1556 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       boolean isDeleted;
/* 1561 */       EnterpriseUtil.handleDeletionInAAM(siteids);
/* 1562 */       if (isDeleted)
/*      */       {
/* 1564 */         return removeIISData(mapping, form, request, response, resourcename, resourceid);
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
/*      */     try
/*      */     {
/* 1587 */       modetails = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid + "");
/* 1588 */       if (modetails.next())
/*      */       {
/* 1590 */         name = modetails.getString("RESOURCENAME");
/* 1591 */         displayname = modetails.getString("DISPLAYNAME");
/* 1592 */         configured = modetails.getInt("DCSTARTED");
/* 1593 */         if ((configured == 2) || (configured == 1))
/*      */         {
/* 1595 */           configuredState = true;
/*      */         }
/* 1597 */         if (configured == 2)
/*      */         {
/* 1599 */           unknownHost = true;
/*      */         }
/* 1601 */         request.setAttribute("displayname", displayname);
/* 1602 */         request.setAttribute("showdata", configured + "");
/*      */         try
/*      */         {
/* 1605 */           AMConnectionPool.closeStatement(modetails);
/*      */ 
/*      */         }
/*      */         catch (Exception exc) {}
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 1615 */           AMConnectionPool.closeStatement(modetails);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/* 1620 */         return new ActionForward("/jsp/NoData.jsp?message=No Data Available.");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       ResultSet modetails;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       boolean forwardNoData;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1676 */       e.printStackTrace();
/*      */     }
/* 1623 */     if (unknownHost)
/*      */     {
/* 1625 */       modetails = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,HostDetails.ERRORMSG,HostDetails.USERNAME,HostDetails.PASSWORD,HostDetails.PROMPT,HostDetails.CATEGORY,AM_HOSTINFO.MODE,CollectData.POLLINTERVAL,AM_HOSTINFO.SNMPCOMMUNITY from AM_ManagedObject left outer join AM_HOSTINFO on  AM_HOSTINFO.RESOURCEID=AM_ManagedObject.RESOURCEID  left outer join HostDetails on HostDetails.RESOURCENAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on  AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME where AM_ManagedObject.RESOURCEID=" + resourceid);
/* 1626 */       if (modetails.next())
/*      */       {
/* 1628 */         hostResourceName = modetails.getString("RESOURCENAME");
/* 1629 */         hostResourceType = modetails.getString("TYPE");
/* 1630 */         modeofcollection = modetails.getString("MODE");
/* 1631 */         hostErrorMessage = modetails.getString("ERRORMSG");
/* 1632 */         hostUserName = modetails.getString("USERNAME");
/* 1633 */         hostPassWord = modetails.getString("PASSWORD");
/* 1634 */         hostPrompt = modetails.getString("PROMPT");
/* 1635 */         hostCategory = modetails.getString("CATEGORY");
/* 1636 */         hostPollInterval = modetails.getInt("POLLINTERVAL");
/* 1637 */         request.setAttribute("mode", modetails.getString("MODE"));
/* 1638 */         keyBasedAuth = modetails.getString("SNMPCOMMUNITY");
/*      */       }
/* 1640 */       if (modetails != null) {
/* 1641 */         AMConnectionPool.closeStatement(modetails);
/*      */       }
/*      */     }
/*      */     else {
/* 1645 */       forwardNoData = true;
/* 1646 */       modetails = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.TYPE,HostDetails.ERRORMSG,HostDetails.USERNAME,HostDetails.PASSWORD,HostDetails.PROMPT,HostDetails.CATEGORY,CollectData.POLLINTERVAL from AM_ManagedObject,HostDetails,CollectData where HostDetails.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid);
/* 1647 */       if (modetails.next())
/*      */       {
/* 1649 */         hostResourceName = modetails.getString("RESOURCENAME");
/* 1650 */         hostResourceType = modetails.getString("TYPE");
/* 1651 */         hostErrorMessage = modetails.getString("ERRORMSG");
/* 1652 */         hostUserName = modetails.getString("USERNAME");
/* 1653 */         hostPassWord = modetails.getString("PASSWORD");
/* 1654 */         hostPrompt = modetails.getString("PROMPT");
/* 1655 */         hostCategory = modetails.getString("CATEGORY");
/* 1656 */         forwardNoData = false;
/*      */       }
/* 1658 */       AMConnectionPool.closeStatement(modetails);
/* 1659 */       if (forwardNoData)
/*      */       {
/* 1661 */         return new ActionForward("/jsp/NoData.jsp?message=Internal Server Error.Unable to get the data for the Monitor Instance.");
/*      */       }
/*      */       
/*      */ 
/* 1665 */       modetails = AMConnectionPool.executeQueryStmt("select MODE,SNMPCOMMUNITY from AM_HOSTINFO where RESOURCEID=" + resourceid + "");
/* 1666 */       if (modetails.next())
/*      */       {
/* 1668 */         modeofcollection = modetails.getString("MODE");
/* 1669 */         keyBasedAuth = modetails.getString("SNMPCOMMUNITY");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1680 */       if ((hostCategory != null) && (!hostCategory.equals("NULL")))
/*      */       {
/* 1682 */         StringTokenizer tokenizeCategory = new StringTokenizer(hostCategory, ",");
/* 1683 */         while (tokenizeCategory.hasMoreTokens())
/*      */         {
/* 1685 */           String catName = tokenizeCategory.nextToken().trim();
/* 1686 */           hostCategoryAsVector.add(catName);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1692 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1700 */     request.setAttribute("hostResName", hostResourceName);
/* 1701 */     request.setAttribute("hostResType", hostResourceType);
/* 1702 */     request.setAttribute("hostErrorMessage", hostErrorMessage);
/* 1703 */     request.setAttribute("hostConfigured", Boolean.valueOf(configuredState));
/* 1704 */     request.setAttribute("name", name);
/* 1705 */     request.setAttribute("resourceid", resourceid);
/* 1706 */     request.setAttribute("haid", request.getParameter("haid"));
/* 1707 */     request.setAttribute("appName", request.getParameter("appName"));
/* 1708 */     request.setAttribute("mode", modeofcollection);
/*      */     
/*      */ 
/* 1711 */     Vector v1 = new Vector();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1722 */     String SchdeuledTasksListScreen = request.getParameter("getScheduledTasksList");
/* 1723 */     if ((SchdeuledTasksListScreen != null) && (SchdeuledTasksListScreen.equals("true")))
/*      */     {
/* 1725 */       return getScheduledTasksList(mapping, form, request, response);
/*      */     }
/* 1727 */     String addScheduledTasks = request.getParameter("addScheduledTasks");
/* 1728 */     if ((addScheduledTasks != null) && (addScheduledTasks.equals("true")))
/*      */     {
/* 1730 */       boolean tasksAdded = addScheduledTasks(mapping, form, request, response);
/* 1731 */       return new ActionForward("/jsp/ReloadHostResource.jsp?resourceid=" + request.getParameter("resourceid"));
/*      */     }
/*      */     
/* 1734 */     String editProcess = request.getParameter("editProcess");
/*      */     
/* 1736 */     if ((editProcess != null) && (editProcess.equals("true")))
/*      */     {
/* 1738 */       return editProcess(mapping, form, request, response);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1743 */     String addProcessScreen = request.getParameter("addProcesScreen");
/* 1744 */     if ((addProcessScreen != null) && (addProcessScreen.equals("true")))
/*      */     {
/* 1746 */       return addProcessScreen(mapping, form, request, response);
/*      */     }
/* 1748 */     String ProcessListScreen = request.getParameter("getProcessList");
/* 1749 */     if ((ProcessListScreen != null) && (ProcessListScreen.equals("true")))
/*      */     {
/* 1751 */       return getProcessList(mapping, form, request, response);
/*      */     }
/* 1753 */     String addProcess = request.getParameter("addProcess");
/* 1754 */     if ((addProcess != null) && (addProcess.equals("true")))
/*      */     {
/*      */ 
/* 1757 */       boolean processadded = addProcess(mapping, form, request, response);
/* 1758 */       if (processadded)
/*      */       {
/*      */ 
/* 1761 */         String editProcess1 = request.getParameter("edit");
/*      */         
/* 1763 */         if ((editProcess1 != null) && (editProcess1.equals("true")))
/*      */         {
/* 1765 */           if (Constants.sqlManager)
/*      */           {
/* 1767 */             return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */           }
/*      */           
/* 1770 */           return new ActionForward("/HostResource.do?name=" + request.getParameter("name") + "&haid=" + request.getParameter("haid") + "&appName=" + request.getParameter("appName") + "&resourceid=" + request.getParameter("resourceid"), true);
/*      */         }
/*      */         
/*      */ 
/* 1774 */         if (Constants.sqlManager)
/*      */         {
/* 1776 */           return new ActionForward("/jsp/mssql/ReloadMSSQL.jsp?resourceid=" + sqlmanid);
/*      */         }
/*      */         
/* 1779 */         return new ActionForward("/jsp/ReloadHostResource.jsp?resourceid=" + request.getParameter("resourceid"));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1784 */       boolean edit = false;
/* 1785 */       if ((request.getParameter("edit") != null) && (request.getParameter("edit").equals("true")))
/*      */       {
/* 1787 */         edit = true;
/*      */       }
/* 1789 */       if (edit)
/*      */       {
/* 1791 */         request.setAttribute("category", getCategory(request.getParameter("resourceid"), request));
/*      */         
/* 1793 */         return new ActionForward("/showTile.do?TileName=.hostresource.editprocess&editProcess=true");
/*      */       }
/*      */       
/*      */ 
/* 1797 */       if (Constants.sqlManager)
/*      */       {
/* 1799 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=2", true);
/*      */       }
/* 1801 */       return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID&showadddiv=true&configure=false&addProcess=false&addProcesScreen=false");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1806 */     String removeProcess = request.getParameter("removeProcess");
/* 1807 */     if ((removeProcess != null) && (removeProcess.equals("true")))
/*      */     {
/* 1809 */       return removeProcess(mapping, form, request, response);
/*      */     }
/* 1811 */     String ServiceListScreen = request.getParameter("getServiceList");
/* 1812 */     if ((ServiceListScreen != null) && (ServiceListScreen.equals("true")))
/*      */     {
/* 1814 */       return getServicesList(mapping, form, request, response);
/*      */     }
/* 1816 */     String addServices = request.getParameter("addServices");
/* 1817 */     if ((addServices != null) && (addServices.equals("true")))
/*      */     {
/* 1819 */       boolean serviceadded = addServices(mapping, form, request, response);
/*      */       
/* 1821 */       if (Constants.sqlManager)
/*      */       {
/* 1823 */         sqlmanid = request.getParameter("sqlmanid");
/* 1824 */         return new ActionForward("/jsp/mssql/ReloadMSSQL.jsp?resourceid=" + sqlmanid);
/*      */       }
/*      */       
/*      */ 
/* 1828 */       if (serviceadded == true) {
/* 1829 */         return new ActionForward("/jsp/ReloadHostResource.jsp?resourceid=" + request.getParameter("resourceid"));
/*      */       }
/* 1831 */       return new ActionForward("/jsp/ReloadHostResource.jsp?resourceid=" + request.getParameter("resourceid"));
/*      */     }
/* 1833 */     String actiononServices = request.getParameter("actiononServices");
/* 1834 */     if (actiononServices != null)
/*      */     {
/* 1836 */       return actiononServices(mapping, form, request, response, actiononServices);
/*      */     }
/* 1838 */     if ((request.getParameter("configure") != null) && (request.getParameter("configure").equals("true")) && (request.getParameter("showconfigdiv") == null))
/*      */     {
/* 1840 */       String username = (String)((DynaActionForm)form).get("username");
/* 1841 */       String password = Translate.decode((String)((DynaActionForm)form).get("password"));
/* 1842 */       String privateKey = (String)((DynaActionForm)form).get("description");
/* 1843 */       String passphrase = request.getParameter("passphrase");
/* 1844 */       if (passphrase != null) {
/* 1845 */         passphrase = Translate.decode(passphrase);
/*      */       }
/* 1847 */       String sshPKAuthChecked = null;
/* 1848 */       if (request.getParameter("sshPKAuth") != null)
/*      */       {
/* 1850 */         String mode = (String)((DynaActionForm)form).get("mode");
/* 1851 */         if (mode.equals("SSH"))
/*      */         {
/*      */ 
/* 1854 */           sshPKAuthChecked = "checked";
/*      */         }
/*      */       }
/* 1857 */       String mode = (String)((DynaActionForm)form).get("mode");
/* 1858 */       String prompt = Translate.decode((String)((DynaActionForm)form).get("prompt"));
/*      */       
/* 1860 */       String timeout = request.getParameter("timeout");
/* 1861 */       if ((timeout == null) || (timeout.trim().length() == 0)) {
/* 1862 */         timeout = "-1";
/*      */       }
/*      */       
/* 1865 */       Properties TestProps = new Properties();
/* 1866 */       TestProps.setProperty("name", name);
/* 1867 */       if (prompt != null)
/*      */       {
/* 1869 */         TestProps.setProperty("prompt", prompt);
/*      */       }
/* 1871 */       TestProps.setProperty("id", resourceid);
/* 1872 */       TestProps.setProperty("TIMEOUT", timeout);
/* 1873 */       if (mode.equalsIgnoreCase("SNMP"))
/*      */       {
/* 1875 */         String snmpport = (String)((DynaActionForm)form).get("port");
/* 1876 */         String snmpVersion = (String)((DynaActionForm)form).get("snmpVersionValue");
/*      */         
/* 1878 */         TestProps.setProperty("SNMPPORT", snmpport);
/*      */         
/* 1880 */         if ((snmpVersion != null) && (snmpVersion.equalsIgnoreCase("v3")))
/*      */         {
/* 1882 */           TestProps.setProperty("SNMPVERSION", "v3");
/* 1883 */           TestProps.setProperty("SNMPUserName", (String)((DynaActionForm)form).get("snmpUserName"));
/* 1884 */           TestProps.setProperty("contextName", (String)((DynaActionForm)form).get("snmpContextName"));
/* 1885 */           String snmpSecurityLevel = (String)((DynaActionForm)form).get("snmpSecurityLevel");
/* 1886 */           TestProps.setProperty("securityLevel", snmpSecurityLevel);
/* 1887 */           if ((snmpSecurityLevel.equalsIgnoreCase("AuthNoPriv")) || (snmpSecurityLevel.equalsIgnoreCase("AuthPriv")))
/*      */           {
/* 1889 */             TestProps.setProperty("authProtocol", (String)((DynaActionForm)form).get("snmpAuthProtocol"));
/* 1890 */             TestProps.setProperty("authPassword", (String)((DynaActionForm)form).get("snmpAuthPassword"));
/*      */           }
/* 1892 */           if (snmpSecurityLevel.equalsIgnoreCase("AuthPriv"))
/*      */           {
/* 1894 */             TestProps.setProperty("privPassword", (String)((DynaActionForm)form).get("snmpPrivPassword"));
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1899 */           TestProps.setProperty("SNMPVERSION", "v1v2");
/* 1900 */           TestProps.setProperty("SNMPCOMMUNITY", (String)((DynaActionForm)form).get("snmpCommunityString"));
/*      */         }
/*      */       }
/* 1903 */       if ((mode.equals("TELNET")) || (mode.equals("SSH")))
/*      */       {
/* 1905 */         String telnetport = (String)((DynaActionForm)form).get("port");
/* 1906 */         TestProps.setProperty("TELNETPORT", telnetport);
/*      */       }
/* 1908 */       String os = request.getParameter("os");
/* 1909 */       Integer poll = (Integer)((DynaActionForm)form).get("pollinterval");
/* 1910 */       String eventlog_status = String.valueOf(((DynaActionForm)form).get("eventlog_status"));
/* 1911 */       Properties userProps = new Properties();
/* 1912 */       userProps.setProperty("resourceName", name);
/* 1913 */       userProps.setProperty("TIMEOUT", timeout);
/* 1914 */       userProps.setProperty("userName", username);
/* 1915 */       userProps.setProperty("password", password);
/*      */       
/* 1917 */       if (sshPKAuthChecked != null)
/*      */       {
/* 1919 */         userProps.setProperty("sshPKAuthChecked", sshPKAuthChecked);
/* 1920 */         if (privateKey != null)
/*      */         {
/* 1922 */           userProps.setProperty("privateKey", privateKey);
/* 1923 */           TestProps.setProperty("privateKey", privateKey);
/* 1924 */           TestProps.setProperty("givenhost", name);
/* 1925 */           userProps.setProperty("password", passphrase);
/* 1926 */           TestProps.setProperty("password", passphrase);
/*      */         }
/*      */       }
/* 1929 */       userProps.setProperty("resourceType", os);
/* 1930 */       userProps.setProperty("eventlog_status", eventlog_status);
/* 1931 */       TestProps.setProperty("eventlog_status", eventlog_status);
/* 1932 */       userProps.setProperty("pollInterval", String.valueOf(poll.intValue() * 60));
/* 1933 */       TestProps.setProperty("pollinterval", String.valueOf(poll.intValue() * 60));
/* 1934 */       Vector v = new Vector();
/* 1935 */       if ((os != null) && (os.equals("Node")))
/*      */       {
/* 1937 */         os = null;
/* 1938 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.os"));
/* 1939 */         saveMessages(request, messages);
/*      */       }
/* 1941 */       if ((poll != null) && (poll.intValue() == 0))
/*      */       {
/* 1943 */         poll = new Integer(300);
/* 1944 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage("hostresource.pollinterval"));
/* 1945 */         saveMessages(request, messages);
/*      */       }
/* 1947 */       if (!messages.isEmpty())
/*      */       {
/* 1949 */         request.setAttribute("category", v);
/*      */       }
/*      */       
/*      */ 
/* 1953 */       TestProps.setProperty("HOST", name);
/* 1954 */       TestProps.setProperty("username", username);
/* 1955 */       TestProps.setProperty("password", password);
/*      */       
/* 1957 */       if (sshPKAuthChecked != null)
/*      */       {
/* 1959 */         TestProps.setProperty("sshPKAuthChecked", sshPKAuthChecked);
/* 1960 */         userProps.setProperty("password", passphrase);
/* 1961 */         TestProps.setProperty("password", passphrase);
/*      */       }
/* 1963 */       TestProps.setProperty("os", os);
/* 1964 */       TestProps.setProperty("MODE", mode);
/* 1965 */       String newdisplayname = request.getParameter("displayname");
/* 1966 */       TestProps.setProperty("DISPLAYNAME", newdisplayname);
/* 1967 */       Properties authresult = null;
/* 1968 */       AMDCInf amdc = null;
/*      */       try
/*      */       {
/* 1971 */         amdc = (AMDCInf)Class.forName("com.adventnet.appmanager.server.hostresources.datacollection.ScheduleHostDataCollection").newInstance();
/* 1972 */         authresult = amdc.CheckAuthentication(TestProps);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1976 */         e.printStackTrace();
/*      */       }
/* 1978 */       String result = authresult.getProperty("authentication");
/* 1979 */       if (result.equals("failed"))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1984 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", authresult.getProperty("error")));
/* 1985 */         saveErrors(request, errors);
/*      */         
/* 1987 */         if (request.getParameter("configured") != null)
/*      */         {
/*      */ 
/* 1990 */           v = hostCategoryAsVector;
/*      */           
/* 1992 */           String category = hostCategory;
/* 1993 */           v.add("Process");
/* 1994 */           request.setAttribute("category", v);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2007 */         request.setAttribute("category", v);
/*      */         
/* 2009 */         request.setAttribute("ids", getAttributeIDS(hostResourceType));
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/* 2015 */           AMConnectionPool.executeUpdateStmt("update HostDetails set ERRORMSG ='Data Collection reconfigured. Kindly wait till the next polling interval' where RESOURCENAME='" + hostResourceName + "'");
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2019 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2026 */         if ((mode != null) && (modeofcollection != null) && (!mode.equalsIgnoreCase(modeofcollection))) {
/* 2027 */           String notSupportedAttrbsId = HostConfInfoGeneric.getNotSupportedAttrbs(hostResourceType, modeofcollection);
/* 2028 */           if ((notSupportedAttrbsId != null) && (notSupportedAttrbsId.length() > 0)) {
/* 2029 */             StringBuffer query = new StringBuffer();
/* 2030 */             query.append("DELETE from AM_HOST_CONFIG_INFO where AM_HOST_CONFIG_INFO.RESOURCEID IN (select amo.RESOURCEID from AM_ManagedObject amo,AM_PARENTCHILDMAPPER ampcm where ampcm.PARENTID=");
/* 2031 */             query.append(resourceid);
/* 2032 */             query.append(" and amo.RESOURCEID=ampcm.CHILDID and amo.TYPE in ('HOST_CONF_PROC','HOST_CONF_NET','HOST_CONF_PRNT') union select RESOURCEID from AM_ManagedObject where RESOURCEID=");
/* 2033 */             query.append(resourceid);
/* 2034 */             query.append(") and AM_HOST_CONFIG_INFO.ATTRIBUTEID in (");
/* 2035 */             query.append(notSupportedAttrbsId);
/* 2036 */             query.append(")");
/* 2037 */             AMLog.debug("SERVER CONFIG INFORMATION DELETE: Mode chaneged from " + modeofcollection + " to " + mode + " for resource id " + resourceid + "   DELETE QUERY: " + query);
/*      */             try {
/* 2039 */               AMConnectionPool.executeUpdateStmt(query.toString());
/*      */             } catch (Exception ex) {
/* 2041 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/* 2045 */         HostResourceMonitor.confInfoPolling.remove(resourceid);
/*      */         
/* 2047 */         amdc.ScheduleDataCollection(TestProps);
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
/* 2078 */       String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 2079 */       if (request.getParameter("haid") != null)
/*      */       {
/* 2081 */         path = path + "&haid=" + request.getParameter("haid");
/*      */       }
/* 2083 */       if (result.equals("passed"))
/*      */       {
/* 2085 */         return new ActionForward(path, true);
/*      */       }
/*      */       
/*      */ 
/* 2089 */       path = path + "&showconfigdiv=true&configure=false";
/* 2090 */       return new ActionForward(path);
/*      */     }
/*      */     
/*      */ 
/* 2094 */     if (request.getParameter("reconfigure") != null)
/*      */     {
/* 2096 */       String os = System.getProperty("os.name");
/* 2097 */       if ((os.startsWith("Windows")) || (os.startsWith("windows")))
/*      */       {
/* 2099 */         ((DynaActionForm)form).set("hostos", "Windows");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2104 */         ((DynaActionForm)form).set("hostos", "NotWindows");
/*      */       }
/* 2106 */       ((DynaActionForm)form).set("username", hostUserName);
/* 2107 */       ((DynaActionForm)form).set("password", hostPassWord);
/*      */       
/* 2109 */       ((DynaActionForm)form).set("os", hostResourceType);
/* 2110 */       ((DynaActionForm)form).set("prompt", hostPrompt);
/* 2111 */       ((DynaActionForm)form).set("displayname", displayname);
/* 2112 */       ((DynaActionForm)form).set("pollinterval", new Integer(hostPollInterval / 60));
/* 2113 */       boolean eventlog_status = false;
/*      */       try
/*      */       {
/* 2116 */         ResultSet rs = AMConnectionPool.executeQueryStmt("select STATUS from AM_HOSTEVENTLOGSTATUS where RESOURCEID=" + resourceid);
/* 2117 */         int status = -1;
/* 2118 */         if (rs.next())
/*      */         {
/* 2120 */           status = rs.getInt("STATUS");
/*      */         }
/* 2122 */         if (status == 1) {
/* 2123 */           eventlog_status = true;
/*      */         } else {
/* 2125 */           eventlog_status = false;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/* 2129 */         e.printStackTrace();
/*      */       }
/*      */       
/* 2132 */       ((DynaActionForm)form).set("eventlog_status", new Boolean(eventlog_status));
/* 2133 */       String modequery = "select * from AM_HOSTINFO where RESOURCEID=" + resourceid;
/* 2134 */       ResultSet rs = AMConnectionPool.executeQueryStmt(modequery);
/* 2135 */       if (rs.next())
/*      */       {
/* 2137 */         ((DynaActionForm)form).set("mode", rs.getString("MODE"));
/* 2138 */         if (rs.getString("MODE").equals("SNMP"))
/*      */         {
/* 2140 */           ((DynaActionForm)form).set("port", rs.getString("SNMPPORT"));
/* 2141 */           ((DynaActionForm)form).set("snmpVersionValue", "v1v2");
/* 2142 */           request.setAttribute("snmpVersionValue", "v1v2");
/*      */           
/* 2144 */           if ((rs.getString("SNMPVERSION") != null) && (rs.getString("SNMPVERSION").equalsIgnoreCase("v3")))
/*      */           {
/* 2146 */             request.setAttribute("snmpVersionValue", "v3");
/* 2147 */             ((DynaActionForm)form).set("snmpVersionValue", "v3");
/* 2148 */             ((DynaActionForm)form).set("snmpUserName", rs.getString("USERNAME"));
/* 2149 */             ((DynaActionForm)form).set("snmpContextName", rs.getString("CONTEXTNAME"));
/* 2150 */             String securityLevel = rs.getString("SECURITYLEVEL");
/* 2151 */             ((DynaActionForm)form).set("snmpSecurityLevel", securityLevel);
/* 2152 */             request.setAttribute("snmpSecurityLevel", securityLevel);
/* 2153 */             if ((securityLevel.equalsIgnoreCase("AuthNoPriv")) || (securityLevel.equalsIgnoreCase("AuthPriv")))
/*      */             {
/* 2155 */               ((DynaActionForm)form).set("snmpAuthProtocol", rs.getString("AUTHPROTOCOL"));
/*      */ 
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 2168 */         else if ((rs.getString("MODE").equals("TELNET")) || (rs.getString("MODE").equals("SSH")))
/*      */         {
/* 2170 */           ((DynaActionForm)form).set("port", rs.getString("TELNETPORT"));
/* 2171 */           String s1 = rs.getString("SNMPCOMMUNITY");
/* 2172 */           if ((s1 != null) && (s1.equalsIgnoreCase("keyBasedAuth"))) {
/* 2173 */             request.setAttribute("keyBasedAuth", "true");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2178 */           ((DynaActionForm)form).set("port", "161");
/*      */         }
/*      */       }
/* 2181 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2183 */       String category = hostCategory;
/*      */       
/* 2185 */       v1 = hostCategoryAsVector;
/* 2186 */       v1.add("Process");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2211 */       request.setAttribute("category", v1);
/*      */       
/* 2213 */       request.setAttribute("ids", getAttributeIDS(hostResourceType));
/*      */       
/* 2215 */       if (request.getParameter("include") != null)
/*      */       {
/* 2217 */         return null;
/*      */       }
/* 2219 */       return mapping.findForward("ConfigureHostResources");
/*      */     }
/*      */     
/*      */ 
/* 2223 */     String category = hostCategory;
/*      */     
/* 2225 */     String errormessage = hostErrorMessage;
/* 2226 */     if ((errormessage != null) && (!errormessage.equalsIgnoreCase("Data Collection Successful")) && (errormessage.indexOf("Authentication failed.") != -1))
/*      */     {
/* 2228 */       if ((modeofcollection != null) && (modeofcollection.equalsIgnoreCase("SSH")) && (keyBasedAuth != null) && (keyBasedAuth.equalsIgnoreCase("keyBasedAuth"))) {
/* 2229 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.discovery.host.keybased.authentication.failed.text")));
/*      */       } else {
/* 2231 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.discovery.host.authentication.text")));
/*      */       }
/* 2233 */       saveErrors(request, errors);
/*      */     }
/* 2235 */     else if ((errormessage != null) && (errormessage.indexOf("Data Collection reconfigured. Kindly wait till the next polling interval") != -1))
/*      */     {
/* 2237 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.host.reconfigured")));
/* 2238 */       saveMessages(request, messages);
/*      */     }
/* 2240 */     else if ((errormessage != null) && ((errormessage.indexOf("Invalid passphrase") != -1) || (errormessage.indexOf("Assertion failed, next byte value") != -1))) {
/* 2241 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString("am.webclient.discovery.host.invalid.passphrase.text")));
/* 2242 */       saveErrors(request, errors);
/*      */     }
/* 2244 */     else if (((errormessage == null) || (!"Data Collection Successful".equalsIgnoreCase(errormessage))) && (!"Data collection yet to start".equalsIgnoreCase(errormessage)))
/*      */     {
/*      */ 
/*      */ 
/* 2248 */       if (errormessage != null)
/*      */       {
/* 2250 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("appmanager.error", FormatUtil.getString(errormessage)));
/* 2251 */         saveErrors(request, errors);
/*      */       }
/*      */     }
/* 2254 */     v1 = hostCategoryAsVector;
/* 2255 */     v1.add("Process");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2284 */     request.setAttribute("category", v1);
/*      */     
/* 2286 */     request.setAttribute("ids", getAttributeIDS(hostResourceType));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2362 */     if (request.getParameter("showconfigdiv") == null)
/*      */     {
/* 2364 */       if (request.getAttribute("reloadperiod") == null) {
/* 2365 */         this.mo.getReloadPeriod(request);
/*      */       }
/*      */       
/*      */     }
/*      */     else {
/* 2370 */       request.removeAttribute("reloadperiod");
/*      */     }
/* 2372 */     if (request.getAttribute("discoverystatus") != null)
/*      */     {
/* 2374 */       request.removeAttribute("reloadperiod");
/*      */     }
/* 2376 */     if ((request.getParameter("opmanager") != null) && (opmanager.equals("opmanager")))
/*      */     {
/* 2378 */       return new ActionForward("/jsp/HostResourceToSend.jsp");
/*      */     }
/*      */     
/*      */ 
/* 2382 */     return new ActionForward("/jsp/HostResource.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ArrayList getNetworkInterface(int resourceid)
/*      */   {
/* 2389 */     String Interfacequery = "SELECT RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE='NetInterface'";
/* 2390 */     netinterface = new ArrayList();
/* 2391 */     ArrayList net_resid = new ArrayList();
/* 2392 */     long collectiontime = 0L;
/* 2393 */     String netid = null;
/* 2394 */     ResultSet rs = null;
/*      */     try {
/* 2396 */       rs = AMConnectionPool.executeQueryStmt(Interfacequery);
/*      */       
/* 2398 */       while (rs.next())
/*      */       {
/* 2400 */         net_resid.add(rs.getString("RESOURCEID"));
/* 2401 */         if (netid == null)
/*      */         {
/* 2403 */           netid = rs.getString("RESOURCEID");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 2408 */           netid = netid + "," + rs.getString("RESOURCEID");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2413 */       if (netid != null)
/*      */       {
/* 2415 */         String query = "Select MAX(COLLECTIONTIME) from AM_NETWORKINTERFACE where RESID in (" + netid + ")";
/* 2416 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2417 */         if (rs.next())
/*      */         {
/* 2419 */           collectiontime = rs.getLong(1);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2425 */       for (int j = 0; j < net_resid.size(); j++)
/*      */       {
/* 2427 */         Interfacequery = "SELECT NAME,SPEED,BYTES_RX,BYTES_TX,ERROR_RX,ERROR_TX,RESID,STATUS,RX_UTILIZATION,TX_UTILIZATION FROM AM_NETWORKINTERFACE WHERE RESID=" + net_resid.get(j) + " and COLLECTIONTIME=" + collectiontime;
/*      */         
/* 2429 */         rs = AMConnectionPool.executeQueryStmt(Interfacequery);
/* 2430 */         if (rs.next())
/*      */         {
/* 2432 */           ArrayList networkinterface = new ArrayList();
/* 2433 */           networkinterface.add(rs.getString("NAME"));
/* 2434 */           networkinterface.add(rs.getString("SPEED"));
/* 2435 */           networkinterface.add(rs.getString("BYTES_RX"));
/* 2436 */           networkinterface.add(rs.getString("BYTES_TX"));
/* 2437 */           networkinterface.add(rs.getString("ERROR_RX"));
/* 2438 */           networkinterface.add(rs.getString("ERROR_TX"));
/* 2439 */           networkinterface.add(rs.getString("RESID"));
/* 2440 */           networkinterface.add(rs.getString("STATUS"));
/* 2441 */           networkinterface.add(rs.getString("RX_UTILIZATION"));
/* 2442 */           networkinterface.add(rs.getString("TX_UTILIZATION"));
/* 2443 */           netinterface.add(networkinterface);
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
/* 2467 */       return netinterface;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2454 */       System.out.println("HostResource : Exception in querying the data for Alert for System Monitoring" + e);
/* 2455 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2461 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Properties getAlerts(int resourceid)
/*      */   {
/* 2471 */     String alertquery = "select AM_ATTRIBUTES.ATTRIBUTE , Alert.SEVERITY from Alert ,  AM_ATTRIBUTES where Alert.SOURCE=" + resourceid + " and Alert.CATEGORY=cast(AM_ATTRIBUTES.ATTRIBUTEID as CHAR)";
/* 2472 */     alert = new Properties();
/* 2473 */     ResultSet rs = null;
/*      */     try {
/* 2475 */       rs = AMConnectionPool.executeQueryStmt(alertquery);
/* 2476 */       while (rs.next()) {
/* 2477 */         alert.setProperty(rs.getString(1), String.valueOf(rs.getInt(2)));
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
/* 2495 */       return alert;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2481 */       AMLog.fatal("HostResource : Exception in querying the data for Alert for System Monitoring", e);
/* 2482 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2488 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getAttributeIDS(String type)
/*      */   {
/* 2499 */     Properties ids = new Properties();
/* 2500 */     Map<String, String> m = AMAttributesCache.getAllAttributeIds(type);
/* 2501 */     ids.putAll(m);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2527 */     return ids;
/*      */   }
/*      */   
/*      */   private String getAvailabilityIDForOS(String osArg)
/*      */   {
/* 2532 */     String attributeID = "750";
/* 2533 */     if (osArg.equals("Linux"))
/*      */     {
/* 2535 */       attributeID = "700";
/*      */     }
/* 2537 */     else if (osArg.equals("WindowsNT"))
/*      */     {
/* 2539 */       attributeID = "800";
/*      */     }
/* 2541 */     else if (osArg.equals("WindowsNT_Server"))
/*      */     {
/* 2543 */       attributeID = "1000";
/*      */     }
/* 2545 */     else if (osArg.equals("SUN"))
/*      */     {
/* 2547 */       attributeID = "1100";
/*      */     }
/* 2549 */     else if (osArg.equals("Windows 2000"))
/*      */     {
/* 2551 */       attributeID = "1200";
/*      */     }
/* 2553 */     return attributeID;
/*      */   }
/*      */   
/*      */ 
/*      */   private String getHealthIDForOS(String osArg)
/*      */   {
/* 2559 */     String attributeID = "750";
/* 2560 */     if (osArg.equals("Linux"))
/*      */     {
/* 2562 */       attributeID = "701";
/*      */     }
/* 2564 */     else if (osArg.equals("WindowsNT"))
/*      */     {
/* 2566 */       attributeID = "801";
/*      */     }
/* 2568 */     else if (osArg.equals("WindowsNT_Server"))
/*      */     {
/* 2570 */       attributeID = "1001";
/*      */     }
/* 2572 */     else if (osArg.equals("SUN"))
/*      */     {
/* 2574 */       attributeID = "1101";
/*      */     }
/* 2576 */     else if (osArg.equals("Windows 2000"))
/*      */     {
/* 2578 */       attributeID = "1201";
/*      */     }
/* 2580 */     else if (osArg.equals("snmp-node"))
/*      */     {
/* 2582 */       attributeID = "1201";
/*      */     }
/* 2584 */     return attributeID;
/*      */   }
/*      */   
/*      */   public static String getSeqresponsetime(String url_id)
/*      */   {
/* 2589 */     String query = "select CHILDID  from AM_PARENTCHILDMAPPER where parentid=" + url_id;
/* 2590 */     long responsetime = 0L;
/*      */     try
/*      */     {
/* 2593 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 2594 */       while (rs.next())
/*      */       {
/* 2596 */         String child_id = rs.getString("CHILDID");
/* 2597 */         if (child_id != null)
/*      */         {
/*      */           try
/*      */           {
/* 2601 */             String query1 = "select  RESPONSETIME from AM_ManagedObjectData where resid=" + child_id + " order by collectiontime desc";
/* 2602 */             query1 = DBQueryUtil.getTopNValues(query1, "1");
/* 2603 */             AMConnectionPool.getInstance();ResultSet rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 2604 */             if (rs1.next())
/*      */             {
/* 2606 */               responsetime += rs1.getLong("RESPONSETIME");
/*      */             }
/*      */             try
/*      */             {
/* 2610 */               AMConnectionPool.closeStatement(rs1);
/*      */             }
/*      */             catch (Exception e1) {}
/*      */           }
/*      */           catch (Exception exc) {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 2625 */         AMConnectionPool.closeStatement(rs);
/*      */ 
/*      */       }
/*      */       catch (Exception e1) {}
/*      */ 
/*      */     }
/*      */     catch (SQLException exc)
/*      */     {
/*      */ 
/* 2634 */       exc.printStackTrace();
/*      */     }
/* 2636 */     return String.valueOf(responsetime);
/*      */   }
/*      */   
/*      */   public int insertAMMO(String moname, int resourceid) {
/* 2640 */     int id = -1;
/* 2641 */     AMAttributesDependencyAdder attributesAdder = new AMAttributesDependencyAdder();
/*      */     try {
/* 2643 */       AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/* 2644 */       AMManagedObject ammo = new AMManagedObject();
/* 2645 */       ammo.setRESOURCENAME(moname);
/* 2646 */       ammo.setType("Process");
/* 2647 */       ammo.setDISPLAYNAME(moname);
/* 2648 */       ammo.setDESCRIPTION("Process Monitoring for System is added");
/* 2649 */       dao.create(ammo);
/* 2650 */       id = ammo.getRESOURCEID();
/* 2651 */       attributesAdder.addInterDependentAttributes(id);
/*      */       
/*      */ 
/* 2654 */       DBUtil.insertParentChildMapper(resourceid, id);
/* 2655 */       attributesAdder.addDependentAttributes(resourceid, id);
/*      */       
/*      */ 
/* 2658 */       return id;
/*      */     }
/*      */     catch (Exception e) {
/* 2661 */       e.printStackTrace(); }
/* 2662 */     return id;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getOutput(String prompt, String command, int timeout, CLISession cliSession)
/*      */   {
/* 2668 */     String output = "";
/*      */     try
/*      */     {
/* 2671 */       CLIMessage climsg = new CLIMessage("");
/* 2672 */       climsg.setCLIPrompt(prompt);
/* 2673 */       climsg.setData(command);
/* 2674 */       climsg.setPromptEcho(false);
/* 2675 */       climsg.setCommandEcho(false);
/* 2676 */       climsg.setRequestTimeout(timeout * 1000);
/* 2677 */       CLIMessage outMsg = cliSession.syncSend(climsg);
/* 2678 */       output = outMsg.getData();
/* 2679 */       AMLog.debug("RAW :" + command + "-->" + output);
/* 2680 */       output = removeNewLine(output);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2684 */       exc.printStackTrace();
/*      */     }
/* 2686 */     return output;
/*      */   }
/*      */   
/*      */   private String removeNewLine(String str) {
/* 2690 */     if (str.startsWith("\r\n"))
/*      */     {
/* 2692 */       str = str.substring(2);
/*      */     }
/* 2694 */     return str;
/*      */   }
/*      */   
/*      */   public static HashMap<String, ArrayList<String>> getDeviceList(String resourceid, String type)
/*      */   {
/* 2699 */     HashMap<String, ArrayList<String>> toReturn = new HashMap();
/* 2700 */     ResultSet result = null;
/* 2701 */     String query = "Select RESOURCEID,DISPLAYNAME,CREATIONTIME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE='" + type + "'";
/* 2702 */     if ((type != null) && (type.equals("NetInterface")))
/*      */     {
/* 2704 */       query = "SELECT DISTINCT resid as RESOURCEID, NAME as DISPLAYNAME,CREATIONTIME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_NETWORKINTERFACE where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and AM_NETWORKINTERFACE.resid=AM_ManagedObject.RESOURCEID and AM_NETWORKINTERFACE.resid=AM_PARENTCHILDMAPPER.CHILDID and TYPE='NetInterface'";
/*      */     }
/* 2706 */     if ((type != null) && (type.equals("NetAdapter")))
/*      */     {
/* 2708 */       query = "SELECT RESOURCEID,DISPLAYNAME,CREATIONTIME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.RESOURCEID =AM_PARENTCHILDMAPPER.CHILDID and TYPE='NetAdapter'";
/*      */     }
/*      */     try
/*      */     {
/* 2712 */       if (type != null)
/*      */       {
/* 2714 */         result = AMConnectionPool.executeQueryStmt(query);
/* 2715 */         while (result.next())
/*      */         {
/* 2717 */           ArrayList<String> values = new ArrayList();
/* 2718 */           values.add(result.getString("DISPLAYNAME"));
/* 2719 */           values.add(result.getString("CREATIONTIME"));
/* 2720 */           toReturn.put(result.getString("RESOURCEID"), values);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2726 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2730 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 2732 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static HashMap<String, String> getIISDataList(String resourceid, String type)
/*      */   {
/* 2737 */     HashMap<String, String> toReturn = new HashMap();
/* 2738 */     ResultSet result = null;
/* 2739 */     String query = "Select RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID AND AM_ManagedObject.TYPE='" + type + "'";
/*      */     try
/*      */     {
/* 2742 */       result = AMConnectionPool.executeQueryStmt(query);
/* 2743 */       while (result.next())
/*      */       {
/* 2745 */         toReturn.put(result.getString("RESOURCEID"), result.getString("DISPLAYNAME"));
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 2750 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2754 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 2756 */     return toReturn;
/*      */   }
/*      */   
/*      */   public Vector getlistVector()
/*      */   {
/* 2761 */     return this.List;
/*      */   }
/*      */   
/*      */   public void setlistVector(Vector vectorList)
/*      */   {
/* 2766 */     this.List = vectorList;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\hostresource\struts\HostResourceAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */