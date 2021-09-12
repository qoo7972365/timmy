/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMPredefinedActionConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.AMScriptProcess;
/*      */ import com.adventnet.appmanager.server.framework.AM_KeyValueDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.ConfCreateMoInf;
/*      */ import com.adventnet.appmanager.server.framework.CustomDCInf;
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.confparser.PreConfMonitorXMLParser;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDCInf;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ApacheDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ResourceConfig;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ScheduleScriptDataCollection;
/*      */ import com.adventnet.appmanager.server.framework.querymonitor.QueryUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DifferentialPollingUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.utils.client.BusinessViewUtil;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cluster.windows.util.WindowsClusterUtil;
/*      */ import com.me.apm.server.exchange.util.ExchangeMonitorUtil;
/*      */ import com.me.apm.server.mqseries.util.MQUtil;
/*      */ import com.me.apm.server.selenium.datacollection.RealBrowserDC;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
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
/*      */ import org.htmlparser.util.Translate;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ public class NewMonitorConf extends DispatchAction
/*      */ {
/*      */   private static final String SUCCESS = "success";
/*   75 */   HashMap<String, Properties> confMonPasswordMap = new HashMap();
/*   76 */   private static final String NMS_HOME = System.getProperty("webnms.rootdir");
/*   77 */   private ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*   78 */   Properties getencodedColumnVals = new Properties();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   85 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*   87 */   PreConfMonitorXMLParser preConfParser = new PreConfMonitorXMLParser();
/*   88 */   HashMap preConfMap = this.preConfParser.getPreConfMonitorListSupported();
/*   89 */   static long credentialID = -1L;
/*   90 */   static boolean isCredentialManager = false;
/*   91 */   HashMap resourceConfigMap = populateMap();
/*      */   
/*      */ 
/*      */   public ActionForward getDropDownValues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   96 */     String toShowArgs = "";String toHideArgs = "";
/*   97 */     String rowName = request.getParameter("rowName");
/*   98 */     String dropDownSelected = request.getParameter("selectedOption");
/*   99 */     String monType = request.getParameter("type");
/*  100 */     ArrayList monArgs = NewMonitorUtil.getArgsforConfMon(monType, request);
/*  101 */     HashMap onChangeMap = (HashMap)monArgs.get(15);
/*  102 */     HashMap valueMap = (HashMap)onChangeMap.get(rowName);
/*  103 */     if (valueMap != null)
/*      */     {
/*  105 */       toShowArgs = (String)valueMap.get(dropDownSelected + "_OnChangeShowArgs");
/*  106 */       toHideArgs = (String)valueMap.get(dropDownSelected + "_OnChangeHideArgs");
/*      */     }
/*      */     try
/*      */     {
/*  110 */       response.setContentType("text/json charset=UTF-8");
/*  111 */       PrintWriter out = response.getWriter();
/*  112 */       JSONObject jsonObject = new JSONObject();
/*  113 */       jsonObject.put("toShowArgs", toShowArgs != null ? toShowArgs : "");
/*  114 */       jsonObject.put("toHideArgs", toHideArgs != null ? toHideArgs : "");
/*  115 */       out.print(jsonObject);
/*  116 */       out.flush();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  120 */       ex.printStackTrace();
/*      */     }
/*  122 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward createMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  128 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  129 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*  130 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  131 */     ActionMessages messages = new ActionMessages();
/*  132 */     String montype = "SilverStream";
/*  133 */     String basetype = "Script Monitor";
/*  134 */     String haid = null;
/*  135 */     String amcreated = "YES";
/*  136 */     String dcclass = "com.adventnet.appmanager.server.framework.datacollection.AMScriptDataCollector";
/*  137 */     String dctype = "JAVACLASS";
/*  138 */     int baseid = -1;
/*  139 */     int resourceid = -1;
/*  140 */     Connection con = null;
/*  141 */     String displayname = "";
/*  142 */     PreparedStatement ps = null;
/*  143 */     int parent_resourceid = -1;
/*  144 */     String isAgentEnabled = "NO";
/*  145 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*  146 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  148 */       montype = request.getParameter("montype");
/*  149 */       haid = ((AMActionForm)form).getHaid();
/*  150 */       ArrayList args = NewMonitorUtil.getArgsforConfMon(montype);
/*  151 */       Properties argsprops = getValuesforArgs(request, args);
/*  152 */       HashMap<String, String> restKeyMap = (HashMap)args.get(args.size() - 1);
/*  153 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*  155 */         String param = (String)e.nextElement();
/*  156 */         if (!argsprops.containsKey(param))
/*      */         {
/*  158 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*  160 */         if ((param.equals("selectedAgents")) || (param.equals("haid")))
/*      */         {
/*  162 */           String[] multiVal = request.getParameterValues(param);
/*  163 */           if ((multiVal != null) && (multiVal.length > 0)) {
/*  164 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/*  165 */             val = val.substring(1, val.length() - 1);
/*  166 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/*  170 */       for (Enumeration enu = ((Properties)argsprops.clone()).keys(); enu.hasMoreElements();)
/*      */       {
/*  172 */         String key = (String)enu.nextElement();
/*  173 */         String value = argsprops.getProperty(key);
/*  174 */         String restKey = (String)restKeyMap.get(key);
/*  175 */         String restVal = (String)restKeyMap.get(value);
/*      */         
/*  177 */         if ((key == null) || ((!key.equals("masSelectType")) && (!key.equals("selectedServer"))))
/*      */         {
/*      */ 
/*      */ 
/*  181 */           if ((restVal != null) && (restKey != null) && (!restVal.equals("")) && (!restKey.equals("")))
/*      */           {
/*  183 */             argsprops.setProperty(restKey, restVal);
/*      */           }
/*  185 */           else if ((restVal != null) && (!restVal.equals("")))
/*      */           {
/*  187 */             argsprops.setProperty(key, restVal);
/*      */           }
/*  189 */           else if ((restKey != null) && (!restKey.equals("")))
/*      */           {
/*  191 */             argsprops.setProperty(restKey, value);
/*      */           }
/*      */           
/*  194 */           if ((key != null) && (key.equals("montype")) && (value != null) && (value.equals("LDAP Server")))
/*  195 */             argsprops.setProperty(key, value);
/*      */         }
/*      */       }
/*  198 */       displayname = request.getParameter("displayname");
/*  199 */       argsprops.setProperty("pollinterval", "" + request.getParameter("pollinterval"));
/*  200 */       argsprops.setProperty("DisplayName", displayname);
/*  201 */       argsprops.setProperty("displayname", displayname);
/*  202 */       argsprops.setProperty("monitorType", montype);
/*  203 */       ArrayList al1 = new ArrayList();
/*      */       try
/*      */       {
/*  206 */         String status = "Success";
/*  207 */         String message = "";
/*  208 */         String managedServerName = "";
/*  209 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/*  210 */         message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/*  211 */         if (((String)responseMap.get("addStatus")).equals("false"))
/*      */         {
/*  213 */           status = "Failed";
/*  214 */           message = (String)responseMap.get("message");
/*      */         }
/*  216 */         managedServerName = (String)responseMap.get("managedServerDispName");
/*  217 */         al1.add(displayname);
/*  218 */         al1.add(status);
/*  219 */         al1.add(message);
/*  220 */         al1.add(managedServerName);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  224 */         e.printStackTrace();
/*      */       }
/*  226 */       removeSpecialCharacters(argsprops);
/*  227 */       request.setAttribute("argsasprops", argsprops);
/*  228 */       request.setAttribute("basetype", "Script Monitor");
/*  229 */       request.setAttribute("discoverystatus", al1);
/*  230 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */     }
/*      */     try
/*      */     {
/*  234 */       String[] selMonitorGroups = request.getParameterValues("haid");
/*      */       
/*  236 */       ConfCreateMoInf inf = (ConfCreateMoInf)Class.forName("com.adventnet.appmanager.server.discovery.CreateNewMo").newInstance();
/*  237 */       displayname = request.getParameter("displayname");
/*  238 */       String pollinterval = request.getParameter("pollinterval");
/*  239 */       int poll_interval = Integer.parseInt(pollinterval) * 60;
/*  240 */       montype = request.getParameter("montype");
/*  241 */       isCredentialManager = false;
/*  242 */       String isTestCredReqd = "false";
/*  243 */       isCredentialManager = false;
/*  244 */       if (this.preConfMap.containsKey(montype))
/*      */       {
/*  246 */         isTestCredReqd = "true";
/*  247 */         AdminActions am = new AdminActions();
/*  248 */         String os; if (Constants.serverTypes.toLowerCase().trim().indexOf(montype.toLowerCase().trim()) != -1)
/*      */         {
/*  250 */           ((AMActionForm)form).setType("SYSTEM:9999");
/*  251 */           os = request.getParameter("os");
/*      */         }
/*  253 */         return am.configureHostDiscovery(mapping, form, request, response);
/*      */       }
/*  255 */       Properties props = getClass(montype);
/*  256 */       if (props != null)
/*      */       {
/*  258 */         amcreated = props.getProperty("amcreated");
/*  259 */         dcclass = props.getProperty("dcclass");
/*  260 */         dctype = props.getProperty("dctype");
/*  261 */         ArrayList args = NewMonitorUtil.getArgsforConfMon(montype);
/*  262 */         String addtoapplication = request.getParameter("addtoha");
/*      */         
/*  264 */         haid = ((AMActionForm)form).getHaid();
/*  265 */         Properties argsprops = getValuesforArgs(request, args);
/*  266 */         String exrole; if (montype.equalsIgnoreCase("Exchange-server")) {
/*  267 */           exrole = null;
/*  268 */           String roleselected = null;
/*  269 */           String servicename = null;
/*  270 */           String servicelist = null;
/*  271 */           if (argsprops.getProperty("version").equalsIgnoreCase("selectversion")) {
/*  272 */             argsprops.setProperty("version", "2013");
/*      */           }
/*  274 */           if ((!argsprops.getProperty("version").equalsIgnoreCase("2003")) && (!argsprops.getProperty("version").equalsIgnoreCase("2000")) && (!argsprops.getProperty("version").equalsIgnoreCase("5"))) {
/*  275 */             exrole = "ServerRole" + argsprops.getProperty("version");
/*  276 */             roleselected = argsprops.getProperty(exrole);
/*  277 */             servicename = ExchangeMonitorUtil.getRoleServices(roleselected);
/*  278 */             servicelist = ExchangeMonitorUtil.getServicesList(servicename);
/*      */           }
/*      */           else {
/*  281 */             roleselected = "2003server";
/*  282 */             servicename = ExchangeMonitorUtil.getRoleServices(roleselected);
/*  283 */             servicelist = ExchangeMonitorUtil.getServicesList(servicename);
/*      */           }
/*  285 */           if (argsprops.getProperty(servicename) == null) {
/*  286 */             argsprops.setProperty(servicename, "False");
/*      */           }
/*  288 */           if ((!roleselected.equalsIgnoreCase("False")) && (argsprops.getProperty(servicename).equalsIgnoreCase("False"))) {
/*  289 */             argsprops.setProperty(servicename, servicelist);
/*      */           }
/*      */         }
/*  292 */         if (!DataCollectionControllerUtil.isallowed())
/*      */         {
/*  294 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction1", String.valueOf(FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted())));
/*  295 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction2"));
/*  296 */           saveMessages(request, messages);
/*  297 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */         }
/*  299 */         boolean dcsucess = true;
/*  300 */         Properties authresult = null;
/*      */         try
/*      */         {
/*  303 */           String qry1 = "select RESOURCETYPEID from AM_ManagedResourceType where RESOURCETYPE='" + montype + "'";
/*  304 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry1);
/*  305 */           if (rs.next())
/*      */           {
/*  307 */             baseid = rs.getInt(1);
/*      */           }
/*  309 */           rs.close();
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  313 */           exc.printStackTrace();
/*      */         }
/*  315 */         if ((dctype != null) && (!dctype.equals("URL")) && (!dcclass.equals("")))
/*      */         {
/*      */           try
/*      */           {
/*  319 */             CustomDCInf amdc = (CustomDCInf)Class.forName(dcclass).newInstance();
/*  320 */             authresult = amdc.CheckAuthentication(argsprops);
/*  321 */             if ((request.getParameter("forceadd") != null) && (request.getParameter("forceadd").equals("true"))) {
/*  322 */               authresult.setProperty("authentication", "passed");
/*  323 */               authresult.setProperty("error", "Authentication Details are correct");
/*      */             }
/*  325 */             if (authresult.getProperty("authentication").equals("passed"))
/*      */             {
/*  327 */               dcsucess = true;
/*      */             }
/*      */             else
/*      */             {
/*  331 */               dcsucess = false;
/*      */             }
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/*  336 */             exc.printStackTrace();
/*  337 */             if ((request.getParameter("forceadd") != null) && (request.getParameter("forceadd").equals("true"))) {
/*  338 */               authresult = new Properties();
/*  339 */               authresult.setProperty("authentication", "passed");
/*  340 */               authresult.setProperty("error", "Authentication Details are correct");
/*      */             }
/*      */           }
/*      */           catch (NoClassDefFoundError er)
/*      */           {
/*  345 */             er.printStackTrace();
/*  346 */             dcsucess = false;
/*  347 */             if (montype.equals("WebsphereMQ"))
/*      */             {
/*  349 */               authresult = new Properties();
/*  350 */               authresult.setProperty("error", FormatUtil.getString("am.webclient.mqseries.classnotfound.text", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("product.talkback.mailid") }));
/*      */             }
/*      */           }
/*      */         }
/*  354 */         request.setAttribute("basetype", "Script Monitor");
/*  355 */         argsprops.setProperty("pollinterval", "" + pollinterval);
/*  356 */         argsprops.setProperty("DisplayName", displayname);
/*  357 */         removeSpecialCharacters(argsprops);
/*  358 */         request.setAttribute("argsasprops", argsprops);
/*  359 */         isAgentEnabled = ConfMonitorConfiguration.getInstance().getTypeDescription(montype).getProperty("IS-AGENT-ENABLED");
/*  360 */         String runOnServer = request.getParameter("runOnServer");
/*  361 */         String[] selectedAgents = ((AMActionForm)form).getSelectedAgents();
/*  362 */         ActionForward localActionForward; if (!dcsucess)
/*      */         {
/*  364 */           boolean isCheckAuthenEnable = true;
/*  365 */           if ((isAgentEnabled != null) && (isAgentEnabled.equalsIgnoreCase("YES")))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  370 */             isCheckAuthenEnable = false;
/*  371 */             if (runOnServer != null) {
/*  372 */               isCheckAuthenEnable = true;
/*      */             }
/*      */           }
/*      */           
/*  376 */           if (isCheckAuthenEnable) {
/*  377 */             ArrayList al1 = new ArrayList();
/*  378 */             al1.add(displayname);
/*  379 */             al1.add("Failed");
/*  380 */             al1.add(FormatUtil.getString(authresult.getProperty("error")));
/*  381 */             request.setAttribute("discoverystatus", al1);
/*  382 */             return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */           }
/*      */         }
/*  385 */         boolean monitorAlreadyExists = NewMonitorUtil.checkIfMonitorAlreadyExists(argsprops, montype);
/*  386 */         if (monitorAlreadyExists) {
/*  387 */           ArrayList al1 = new ArrayList();
/*  388 */           al1.add(displayname);
/*  389 */           al1.add("Failed");
/*  390 */           al1.add(FormatUtil.getString("am.webclient.script.monitorexists"));
/*  391 */           request.setAttribute("discoverystatus", al1);
/*  392 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */         }
/*  394 */         int agentCount = 0;
/*  395 */         HashMap displayNamesMap = new HashMap();
/*  396 */         if ((selectedAgents != null) && (selectedAgents.length > 0)) {
/*  397 */           agentCount = selectedAgents.length;
/*  398 */           displayNamesMap = NewMonitorUtil.getAgentNames(selectedAgents, agentCount);
/*  399 */           request.setAttribute("isAgentAssociated", "true");
/*      */         }
/*  401 */         if ((isAgentEnabled != null) && (isAgentEnabled.equalsIgnoreCase("YES")))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  406 */           System.out.println("[ Creating Parent Monitor ]");
/*  407 */           parent_resourceid = inf.createMo(displayname, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, addtoapplication, selMonitorGroups, args, true, -1, -1);
/*  408 */           if (parent_resourceid > 0)
/*      */           {
/*      */             try
/*      */             {
/*  412 */               AMPredefinedActionConfiguration.applyPredefinedActions(parent_resourceid);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/*  416 */               e.printStackTrace();
/*      */             }
/*      */           }
/*  419 */           if (parent_resourceid != -1) {
/*  420 */             request.setAttribute("resourceid", parent_resourceid + "");
/*      */           }
/*      */           
/*      */ 
/*  424 */           if ((agentCount == 0) || (runOnServer != null))
/*      */           {
/*  426 */             System.out.println("[ Creating Monitor on Local Server]");
/*  427 */             String disname = displayname + "-" + "Local";
/*  428 */             int agentId = Integer.parseInt(EnterpriseUtil.getDistributedStartResourceId());
/*  429 */             resourceid = inf.createMo(disname, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, null, (String[])null, args, false, parent_resourceid, agentId);
/*  430 */             long nextRowId = DBQueryUtil.getLongIncrementedID("AM_EUM_PARENTCHILD_MAPPING", "RELATIONSHIPID");
/*  431 */             String insertQry = "insert into AM_EUM_PARENTCHILD_MAPPING (RELATIONSHIPID,PARENTID,CHILDID) values (" + nextRowId + "," + parent_resourceid + "," + resourceid + ")";
/*  432 */             AMConnectionPool.executeUpdateStmt(insertQry);
/*      */           }
/*      */           
/*      */ 
/*  436 */           for (int k = 0; k < agentCount; k++)
/*      */           {
/*      */             try
/*      */             {
/*  440 */               System.out.println("[ Creating Monitor For Agent " + selectedAgents[k] + "]");
/*  441 */               int agentId = Integer.parseInt(selectedAgents[k]);
/*  442 */               String disname = displayname;
/*  443 */               if (displayNamesMap.get(selectedAgents[k]) != null) {
/*  444 */                 disname = displayname + "-" + (String)displayNamesMap.get(selectedAgents[k]);
/*      */               }
/*      */               
/*  447 */               resourceid = inf.createMo(disname, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, null, (String[])null, args, false, parent_resourceid, agentId);
/*  448 */               long nextRowId = DBQueryUtil.getLongIncrementedID("AM_EUM_PARENTCHILD_MAPPING", "RELATIONSHIPID");
/*  449 */               String insertQry = "insert into AM_EUM_PARENTCHILD_MAPPING (RELATIONSHIPID,PARENTID,CHILDID) values (" + nextRowId + "," + parent_resourceid + "," + resourceid + ")";
/*  450 */               AMConnectionPool.executeUpdateStmt(insertQry);
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  454 */               ee.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*  458 */         else if (montype.equals("MongoDB"))
/*      */         {
/*  460 */           AMLog.debug("mongodb in result");
/*  461 */           String host1 = (String)argsprops.get("hostname");
/*  462 */           String port1 = (String)argsprops.get("port");
/*      */           
/*  464 */           int parentid = -1;
/*  465 */           ArrayList hosts = null;
/*  466 */           String process = null;
/*  467 */           HashMap<String, ArrayList> shardHash = new HashMap();
/*  468 */           ArrayList activeHosts = null;
/*      */           
/*  470 */           if (authresult.get("hosts") != null)
/*      */           {
/*  472 */             hosts = (ArrayList)authresult.remove("hosts");
/*      */           }
/*  474 */           if (authresult.get("process") != null)
/*      */           {
/*  476 */             process = (String)authresult.get("process");
/*      */           }
/*  478 */           if (authresult.get("shardReplHash") != null)
/*      */           {
/*  480 */             shardHash = (HashMap)authresult.remove("shardReplHash");
/*      */           }
/*  482 */           if (authresult.get("activeHosts") != null)
/*      */           {
/*  484 */             activeHosts = (ArrayList)authresult.remove("activeHosts");
/*      */           }
/*      */           
/*  487 */           if ((hosts != null) && (!hosts.isEmpty()) && (process != null) && (process.equals("mongod")))
/*      */           {
/*  489 */             String single = (String)authresult.get("single");
/*  490 */             parentid = DataCollectionDBUtil.getParentIDForMongo(montype, displayname, process, activeHosts, hosts, single, baseid);
/*  491 */             if (parentid != -1)
/*      */             {
/*  493 */               argsprops.put("bvresourceid", new String(parentid + ""));
/*  494 */               authresult.put("bvresourceid", new String(parentid + ""));
/*      */             }
/*      */           }
/*  497 */           resourceid = inf.createMo(displayname, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  498 */           if (resourceid != -1)
/*      */           {
/*  500 */             request.setAttribute("resourceid", resourceid + "");
/*      */           }
/*  502 */           if ((hosts != null) && (!hosts.isEmpty()) && (parentid != -1) && (parentid != resourceid))
/*      */           {
/*  504 */             DBUtil.insertParentChildMapper(parentid, resourceid);
/*      */           }
/*  506 */           if ((process != null) && (process.equals("mongos")))
/*      */           {
/*  508 */             parentid = resourceid;
/*  509 */             DataCollectionDBUtil.updateMongosChildren(montype, parentid, activeHosts, hosts, shardHash, baseid);
/*      */           }
/*  511 */           if ((activeHosts != null) && (!activeHosts.isEmpty()))
/*      */           {
/*      */ 
/*  514 */             AMLog.debug("inside mongo child discovery : " + activeHosts);
/*  515 */             Iterator itt = activeHosts.iterator();
/*  516 */             while (itt.hasNext())
/*      */             {
/*  518 */               String hh = (String)itt.next();
/*  519 */               int index = hh.indexOf(":");
/*  520 */               String h1 = hh.substring(0, index);
/*  521 */               String p1 = hh.substring(index + 1);
/*  522 */               String dispName = h1 + "_" + p1;
/*  523 */               Properties argProp = (Properties)argsprops.clone();
/*  524 */               argProp.put("hostname", h1);
/*  525 */               argProp.put("port", p1);
/*  526 */               Properties authRes = (Properties)argProp.clone();
/*  527 */               int bvresId = DataCollectionDBUtil.getBVResID(h1, p1, shardHash, hosts, montype);
/*  528 */               int pid = parentid;
/*  529 */               if (bvresId != -1)
/*      */               {
/*  531 */                 pid = bvresId;
/*      */               }
/*  533 */               if (pid != -1)
/*      */               {
/*  535 */                 argProp.put("bvresourceid", new String(pid + ""));
/*  536 */                 authRes.put("bvresourceid", new String(pid + ""));
/*      */               }
/*  538 */               argProp.put("process", "mongod");
/*  539 */               authRes.put("process", "mongod");
/*  540 */               int resId = inf.createMo(dispName, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argProp, authRes, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  541 */               AMLog.debug("child resId = " + resId);
/*  542 */               if ((pid != -1) && (pid != resId))
/*      */               {
/*  544 */                 DBUtil.insertParentChildMapper(pid, resId);
/*      */               }
/*      */             }
/*      */           }
/*  548 */           DataCollectionDBUtil.updateMongosReplicaSetChild(shardHash, parentid);
/*      */         }
/*  550 */         else if (montype.equals("Cassandra"))
/*      */         {
/*  552 */           AMLog.debug("cassandra in result " + argsprops);
/*  553 */           String host1 = (String)argsprops.get("hostname");
/*  554 */           String port1 = (String)argsprops.get("port");
/*  555 */           displayname = host1 + "_" + port1;
/*  556 */           String dname1 = argsprops.getProperty("DisplayName");
/*  557 */           List liveNodes = (List)authresult.remove("LiveNodes");
/*  558 */           String cname1 = null;
/*  559 */           if (liveNodes != null)
/*      */           {
/*  561 */             cname1 = DataCollectionDBUtil.getCassandraClusterName(host1, liveNodes);
/*      */           }
/*  563 */           if ((cname1 == null) || (cname1.equals("")))
/*      */           {
/*  565 */             cname1 = dname1 + "_Cluster";
/*      */           }
/*  567 */           argsprops.put("clustername", cname1);
/*  568 */           authresult.put("clustername", cname1);
/*  569 */           ArrayList hosts = null;
/*  570 */           ArrayList activeHosts = null;
/*      */           
/*  572 */           if (authresult.get("hosts") != null)
/*      */           {
/*  574 */             hosts = (ArrayList)authresult.remove("hosts");
/*      */           }
/*  576 */           if (authresult.get("activeHosts") != null)
/*      */           {
/*  578 */             activeHosts = (ArrayList)authresult.remove("activeHosts");
/*      */           }
/*  580 */           resourceid = inf.createMo(dname1, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  581 */           Thread.sleep(5000L);
/*  582 */           if (resourceid != -1)
/*      */           {
/*  584 */             request.setAttribute("resourceid", resourceid + "");
/*  585 */             DataCollectionDBUtil.insertJavaHome(resourceid, argsprops);
/*      */           }
/*  587 */           if ((activeHosts != null) && (!activeHosts.isEmpty()))
/*      */           {
/*      */ 
/*  590 */             AMLog.debug("inside cassandra cluster discovery : " + activeHosts);
/*  591 */             Iterator itt = activeHosts.iterator();
/*  592 */             while (itt.hasNext())
/*      */             {
/*  594 */               String hh = (String)itt.next();
/*  595 */               String pp = (String)argsprops.get("port");
/*  596 */               String dispName = hh + "_" + pp;
/*  597 */               Properties argProp = (Properties)argsprops.clone();
/*  598 */               argProp.put("hostname", hh);
/*  599 */               Properties authRes = (Properties)argProp.clone();
/*  600 */               int resId = inf.createMo(dispName, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argProp, authRes, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  601 */               AMLog.debug("cassandra cluster child resId = " + resId);
/*  602 */               Thread.sleep(5000L);
/*  603 */               DataCollectionDBUtil.insertJavaHome(resId, argProp);
/*      */             }
/*      */           }
/*      */         }
/*  607 */         else if (montype.equals("Redis"))
/*      */         {
/*  609 */           AMLog.debug("redis in result " + argsprops);
/*  610 */           String host1 = (String)argsprops.get("hostname");
/*  611 */           String port1 = (String)argsprops.get("port");
/*  612 */           displayname = host1 + "_" + port1;
/*  613 */           String dname1 = argsprops.getProperty("DisplayName");
/*  614 */           String role = (String)argsprops.get("role");
/*  615 */           ArrayList hosts = null;
/*  616 */           ArrayList activeHosts = null;
/*  617 */           ArrayList slaves = null;
/*      */           
/*  619 */           if (authresult.get("hosts") != null)
/*      */           {
/*  621 */             hosts = (ArrayList)authresult.remove("hosts");
/*      */           }
/*  623 */           if (authresult.get("activeHosts") != null)
/*      */           {
/*  625 */             activeHosts = (ArrayList)authresult.remove("activeHosts");
/*      */           }
/*  627 */           if (authresult.get("Slaves") != null)
/*      */           {
/*  629 */             slaves = (ArrayList)authresult.remove("Slaves");
/*      */           }
/*  631 */           String cname1 = null;
/*  632 */           if ("slave".equals(role))
/*      */           {
/*  634 */             String masterHost = argsprops.getProperty("MasterHost");
/*  635 */             String masterPort = argsprops.getProperty("MasterPort");
/*  636 */             cname1 = DataCollectionDBUtil.getRedisClusterName(masterHost, masterPort, role, null);
/*      */           }
/*  638 */           else if (("master".equals(role)) && (slaves != null) && (!slaves.isEmpty()))
/*      */           {
/*  640 */             cname1 = DataCollectionDBUtil.getRedisClusterName(null, null, role, slaves);
/*      */           }
/*  642 */           if ((cname1 == null) || (cname1.equals("")))
/*      */           {
/*  644 */             cname1 = dname1 + "_Cluster";
/*      */           }
/*  646 */           argsprops.put("clustername", cname1);
/*  647 */           authresult.put("clustername", cname1);
/*  648 */           resourceid = inf.createMo(dname1, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  649 */           Thread.sleep(5000L);
/*  650 */           if (resourceid != -1)
/*      */           {
/*  652 */             request.setAttribute("resourceid", resourceid + "");
/*      */           }
/*  654 */           if ((activeHosts != null) && (!activeHosts.isEmpty()))
/*      */           {
/*      */ 
/*  657 */             AMLog.debug("inside redis cluster discovery : " + activeHosts);
/*  658 */             Iterator itt = activeHosts.iterator();
/*  659 */             while (itt.hasNext())
/*      */             {
/*  661 */               String hh = (String)itt.next();
/*  662 */               String pp = (String)argsprops.get("port");
/*  663 */               if (role.equals("slave"))
/*      */               {
/*  665 */                 pp = argsprops.getProperty("MasterPort");
/*      */               }
/*  667 */               String dispName = hh + "_" + pp;
/*  668 */               Properties argProp = (Properties)argsprops.clone();
/*  669 */               argProp.put("hostname", hh);
/*  670 */               argProp.put("port", pp);
/*  671 */               Properties authRes = (Properties)argProp.clone();
/*  672 */               int resId = inf.createMo(dispName, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argProp, authRes, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*  673 */               AMLog.debug("redis cluster child resId = " + resId);
/*  674 */               Thread.sleep(5000L);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  681 */           resourceid = inf.createMo(displayname, montype, poll_interval, baseid, basetype, amcreated, dcclass, dctype, dcsucess, argsprops, authresult, addtoapplication, selMonitorGroups, args, false, -1, -1);
/*      */           
/*      */ 
/*      */ 
/*  685 */           argsprops.setProperty("pollinterval", pollinterval);
/*      */           
/*      */ 
/*  688 */           if (resourceid != -1) {
/*  689 */             if (("XenServerHost".equals(montype)) || ("OfficeSharePointServer".equals(montype)))
/*      */             {
/*  691 */               String q4 = "UPDATE AM_ManagedObject SET DCSTARTED='2' WHERE RESOURCEID='" + resourceid + "'";
/*  692 */               AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q4);
/*  693 */               AM_KeyValueDataCollector amkvdc = (AM_KeyValueDataCollector)AMScriptProcess.resid_instance.get(String.valueOf(resourceid));
/*  694 */               if (amkvdc != null)
/*      */               {
/*  696 */                 amkvdc.setDCStarted(2);
/*      */               }
/*      */             }
/*  699 */             request.setAttribute("resourceid", resourceid + "");
/*      */           }
/*  701 */           if (isCredentialManager)
/*      */           {
/*      */             try
/*      */             {
/*  705 */               credUtil.addToCredentialToResourceMap(credentialID, resourceid);
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/*  709 */               ex.printStackTrace();
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  718 */         System.out.println("DC not triggered");
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
/*      */       try
/*      */       {
/*  737 */         ps.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/*      */       ArrayList al1;
/*      */       
/*      */ 
/*  745 */       al1 = new ArrayList();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  724 */       System.out.println("Exception e---------->" + exc.getMessage());
/*  725 */       exc.printStackTrace();
/*  726 */       al1 = new ArrayList();
/*  727 */       al1.add(displayname);
/*  728 */       al1.add("Failed");
/*  729 */       al1.add("Internal Database Error");
/*  730 */       request.setAttribute("discoverystatus", al1);
/*  731 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  737 */         ps.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */     
/*      */ 
/*      */     ArrayList al1;
/*      */     
/*      */ 
/*  746 */     al1.add(displayname);
/*  747 */     al1.add("Success");
/*      */     
/*  749 */     int resid = -1;
/*  750 */     if ((isAgentEnabled != null) && (isAgentEnabled.equalsIgnoreCase("YES"))) {
/*  751 */       al1.add("/showresource.do?resourceid=" + parent_resourceid + "&method=showResourceForResourceID");
/*  752 */       resid = parent_resourceid;
/*      */     }
/*      */     else {
/*  755 */       al1.add("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/*  756 */       resid = resourceid;
/*      */     }
/*  758 */     if ((resid != -1) && 
/*  759 */       (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request))) {
/*  760 */       RestrictedUsersViewUtil.insertIntoAMUserResourcesTableandsynchtoAAM(request.getRemoteUser(), Long.valueOf(resid).longValue());
/*      */     }
/*      */     
/*      */ 
/*  764 */     request.setAttribute("discoverystatus", al1);
/*  765 */     if (montype.equals("JBOSS7")) {
/*  766 */       montype = "JBOSS-server";
/*      */     }
/*  768 */     return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + montype + "&haid=" + haid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void removeSpecialCharacters(Properties props)
/*      */   {
/*      */     try
/*      */     {
/*  781 */       Enumeration en = props.propertyNames();
/*  782 */       while (en.hasMoreElements())
/*      */       {
/*  784 */         String key = (String)en.nextElement();
/*  785 */         if (!(props.get(key) instanceof String))
/*      */         {
/*  787 */           return;
/*      */         }
/*  789 */         String val = props.getProperty(key);
/*  790 */         if (!key.equals("Queries"))
/*      */         {
/*  792 */           if ((System.getProperty("am.dbserver.type") != null) && (System.getProperty("am.dbserver.type").equals("mssql")))
/*      */           {
/*  794 */             val = val.replaceAll("'", "''");
/*  795 */             if ((key.equalsIgnoreCase("UserName")) || (key.equalsIgnoreCase("Remote Source File Name")) || (key.equalsIgnoreCase("Local Source File Name")) || (key.equalsIgnoreCase("Local Destination File Name")) || (key.equalsIgnoreCase("Remote Destination File Name")))
/*      */             {
/*  797 */               val = StrUtil.findReplace(val, "\\", "\\\\");
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  802 */             if (DBQueryUtil.isMysql())
/*      */             {
/*  804 */               val = StrUtil.findReplace(val, "\\", "\\\\");
/*      */             }
/*  806 */             val = StrUtil.findReplace(val, "'", "\\'");
/*      */           }
/*      */         }
/*  809 */         props.setProperty(key, val);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  814 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void removeSpecialCharactersforEdition(Properties props)
/*      */   {
/*      */     try
/*      */     {
/*  822 */       Enumeration en = props.propertyNames();
/*  823 */       while (en.hasMoreElements())
/*      */       {
/*  825 */         String key = (String)en.nextElement();
/*  826 */         String val = props.getProperty(key);
/*  827 */         if (!key.equals("Queries"))
/*      */         {
/*  829 */           if ((System.getProperty("am.dbserver.type") != null) && (System.getProperty("am.dbserver.type").equals("mssql")))
/*      */           {
/*  831 */             if ((!key.equalsIgnoreCase("Remote Source File Name")) && (!key.equalsIgnoreCase("Local Source File Name")) && (!key.equalsIgnoreCase("Local Destination File Name")) && (!key.equalsIgnoreCase("Remote Destination File Name")))
/*      */             {
/*  833 */               val = StrUtil.findReplace(val, "\\", "\\\\");
/*      */             }
/*  835 */             val = val.replaceAll("'", "''");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  840 */             if ((DBQueryUtil.isPgsql()) || ((DBQueryUtil.isMysql()) && (!key.equalsIgnoreCase("Remote Source File Name")) && (!key.equalsIgnoreCase("Local Source File Name")) && (!key.equalsIgnoreCase("Local Destination File Name")) && (!key.equalsIgnoreCase("Remote Destination File Name"))) || ((key.toLowerCase().equals("username")) && (DBQueryUtil.isPgsql()))) {
/*  841 */               val = StrUtil.findReplace(val, "\\", "\\\\");
/*      */             }
/*  843 */             val = StrUtil.findReplace(val, "'", "\\'");
/*      */           }
/*      */         }
/*  846 */         props.setProperty(key, val);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  851 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  859 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*  860 */     String haid = request.getParameter("haid");
/*  861 */     String type = request.getParameter("type");
/*  862 */     if ("RBM".equals(type)) {
/*  863 */       request.setAttribute("isRBM", "true");
/*      */     }
/*  865 */     String resourceid = request.getParameter("resourceid");
/*  866 */     AMActionForm amform = (AMActionForm)form;
/*      */     try
/*      */     {
/*  869 */       ArrayList args = NewMonitorUtil.getArgsforConfMon(type, request);
/*  870 */       Properties argsasprops = NewMonitorUtil.getArgsasProps(type, resourceid);
/*  871 */       if (credUtil.getCredentialIDForResourceID(resourceid) == -1L)
/*      */       {
/*  873 */         argsasprops.setProperty("CredentialDetails", "nocm");
/*      */       }
/*      */       else
/*      */       {
/*  877 */         argsasprops.setProperty("CredentialDetails", "cm");
/*      */       }
/*      */       
/*  880 */       String isAgentEnabled = ConfMonitorConfiguration.getInstance().getTypeDescription(type).getProperty("IS-AGENT-ENABLED");
/*  881 */       if ((isAgentEnabled != null) && (isAgentEnabled.equalsIgnoreCase("YES")))
/*      */       {
/*  883 */         HashMap associationMap = NewMonitorUtil.getAssociatedAgents(resourceid, type);
/*  884 */         ArrayList<String> associatedAgents = (ArrayList)associationMap.get("associatedAgents");
/*  885 */         String[] selectedAgents = new String[0];
/*  886 */         if ((associatedAgents != null) && (!associatedAgents.isEmpty())) {
/*  887 */           selectedAgents = (String[])associatedAgents.toArray(selectedAgents);
/*  888 */           amform.setSelectedAgents(selectedAgents);
/*  889 */           request.setAttribute("isAgentAssociated", "true");
/*      */         }
/*  891 */         boolean isAssociatedtoLocal = ((Boolean)associationMap.get("isAssociatedtoLocal")).booleanValue();
/*  892 */         request.setAttribute("isAssociatedtoLocal", Boolean.valueOf(isAssociatedtoLocal));
/*      */       }
/*      */       
/*      */ 
/*  896 */       removeSpecialCharactersforEdition(argsasprops);
/*      */       
/*  898 */       String displayname = type;
/*  899 */       int pollinterval = 300;
/*  900 */       ResultSet rs = null;
/*      */       
/*      */       try
/*      */       {
/*  904 */         String qry = "select displayname,pollinterval from AM_ScriptArgs where resourceid=" + resourceid;
/*  905 */         AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(qry);
/*      */         
/*  907 */         if (rs.next())
/*      */         {
/*  909 */           displayname = rs.getString(1);
/*  910 */           pollinterval = rs.getInt(2);
/*      */         }
/*  912 */         pollinterval /= 60;
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (Exception exc) {}finally
/*      */       {
/*      */ 
/*      */ 
/*  920 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/*  923 */       if (type.equals("MongoDB"))
/*      */       {
/*      */ 
/*  926 */         ArrayList nameList = (ArrayList)args.get(0);
/*  927 */         ArrayList typeList = (ArrayList)args.get(1);
/*  928 */         int indx = nameList.indexOf("discoverchildren");
/*  929 */         typeList.set(indx, Integer.valueOf(6));
/*      */       }
/*  931 */       if ((type.equals("Cassandra")) || (type.equals("Redis")))
/*      */       {
/*      */ 
/*  934 */         ArrayList nameList = (ArrayList)args.get(0);
/*  935 */         ArrayList typeList = (ArrayList)args.get(1);
/*  936 */         int indx = nameList.indexOf("discoverclusternodes");
/*  937 */         typeList.set(indx, Integer.valueOf(6));
/*      */       }
/*      */       
/*  940 */       argsasprops.setProperty("pollinterval", "" + pollinterval);
/*  941 */       argsasprops.setProperty("DisplayName", displayname);
/*  942 */       request.setAttribute("argsasprops", argsasprops);
/*  943 */       request.setAttribute("args", args);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  947 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  951 */     if ((EnterpriseUtil.isIt360MSPEdition) && (type.equals("VMWare ESX/ESXi")))
/*      */     {
/*  953 */       String siteId = CustomerManagementAPI.getSiteID(resourceid);
/*  954 */       int custId = CustomerManagementAPI.getCustomerIdForSiteId(siteId);
/*  955 */       amform.setOrganization(Integer.toString(custId));
/*  956 */       amform.setHaid(siteId);
/*      */     }
/*  958 */     return new ActionForward("/jsp/newConfType.jsp?haid=" + haid + "&customType=true&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  966 */     String resourceid = request.getParameter("resourceid");
/*  967 */     String errormsg = "";
/*  968 */     String displayname = request.getParameter("displayname");
/*  969 */     String montype = request.getParameter("montype");
/*  970 */     if (this.preConfMap.containsKey(montype))
/*      */     {
/*  972 */       montype = getResourceTypeForPreConf(montype);
/*      */     }
/*  974 */     String pollinterval = request.getParameter("pollinterval");
/*  975 */     int poll_interval = Integer.parseInt(pollinterval) * 60;
/*  976 */     String isAgentEnabled = request.getParameter("isAgentEnabled");
/*  977 */     String isAgentAssociated = request.getParameter("isAgentAssociated");
/*  978 */     String returnTo = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/*  979 */     String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/*  980 */     boolean newRequest = true;
/*      */     try
/*      */     {
/*  983 */       ArrayList args = NewMonitorUtil.getArgsforConfMon(montype);
/*  984 */       ActionErrors errors = new ActionErrors();
/*      */       
/*  986 */       Properties argsasprops = getValuesforArgs(request, args);
/*  987 */       BusinessViewUtil.deleteBussinessViewCache();
/*  988 */       if (this.preConfMap.containsKey(montype))
/*      */       {
/*      */ 
/*  991 */         errormsg = updateAsPerType(request, args, argsasprops);
/*  992 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID&message=" + URLEncoder.encode(errormsg));
/*      */       }
/*  994 */       Properties props = getClass(montype);
/*  995 */       String dcclass = props.getProperty("dcclass");
/*      */       
/*  997 */       String haid = ((AMActionForm)form).getHaid();
/*  998 */       CustomDCInf amdc = (CustomDCInf)Class.forName(dcclass).newInstance();
/*  999 */       Properties authresult = new Properties();
/* 1000 */       String parentMOType = ConfMonitorConfiguration.getInstance().getParentType(montype);
/* 1001 */       if (parentMOType == null)
/*      */       {
/* 1003 */         authresult = amdc.CheckAuthentication(argsasprops);
/*      */       }
/*      */       else
/*      */       {
/* 1007 */         authresult.setProperty("authentication", "passed");
/* 1008 */         authresult.setProperty("error", FormatUtil.getString("am.webclient.confmonitors.edit.success"));
/*      */       }
/* 1010 */       String runOnServer = request.getParameter("runOnServer");
/* 1011 */       boolean isCheckAuthenEnable = false;
/* 1012 */       if ((isAgentEnabled != null) && (isAgentEnabled.equals("YES"))) {
/* 1013 */         isCheckAuthenEnable = true;
/* 1014 */         if (runOnServer != null) {
/* 1015 */           isCheckAuthenEnable = false;
/*      */         }
/*      */       }
/* 1018 */       if ((authresult.getProperty("authentication").equals("passed")) || (isCheckAuthenEnable))
/*      */       {
/*      */         try {
/* 1021 */           String q1 = "update AM_ScriptArgs set displayname='" + displayname + "',pollinterval=" + poll_interval + " where resourceid=" + resourceid;
/* 1022 */           EnterpriseUtil.addUpdateQueryToFile(q1);
/* 1023 */           String q2 = "update AM_ManagedObject set DCSTARTED='2',DISPLAYNAME='" + displayname + "' where RESOURCEID=" + resourceid;
/* 1024 */           EnterpriseUtil.addUpdateQueryToFile(q2);
/* 1025 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q1);
/* 1026 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q2);
/*      */           
/* 1028 */           AM_KeyValueDataCollector amkvdc = (AM_KeyValueDataCollector)AMScriptProcess.resid_instance.get(String.valueOf(resourceid));
/* 1029 */           amkvdc.setDCStarted(2);
/* 1030 */           if (isAgentEnabled.equals("YES")) {
/* 1031 */             NewMonitorUtil.updateEUMChildDisplayNames(resourceid, displayname, poll_interval + "", "Resource");
/*      */           }
/*      */           
/* 1034 */           if (montype.equals("WindowsAzure"))
/*      */           {
/* 1036 */             String query = "SELECT CHILDID FROM AM_PARENTCHILDMAPPER WHERE PARENTID='" + resourceid + "'";
/* 1037 */             ResultSet rs = null;
/* 1038 */             String childResID = null;
/*      */             try
/*      */             {
/* 1041 */               AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 1042 */               while (rs.next())
/*      */               {
/* 1044 */                 String child_id = rs.getString(1);
/* 1045 */                 if (childResID == null)
/*      */                 {
/* 1047 */                   childResID = child_id;
/*      */                 }
/*      */                 else
/*      */                 {
/* 1051 */                   childResID = childResID + "," + child_id;
/*      */                 }
/*      */               }
/* 1054 */               if (childResID != null)
/*      */               {
/* 1056 */                 query = "UPDATE AM_ScriptArgs set pollinterval=" + poll_interval + " where resourceid in (" + childResID + ")";
/* 1057 */                 AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query);
/*      */               }
/*      */             }
/*      */             catch (SQLException e)
/*      */             {
/* 1062 */               e.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/* 1066 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */           }
/* 1069 */           if ("VMWare ESX/ESXi".equals(montype))
/*      */           {
/* 1071 */             String q3 = "update AM_ManagedObject set DCSTARTED='2' where RESOURCEID=" + resourceid;
/* 1072 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q3);
/*      */           }
/* 1074 */           if (("XenServerHost".equals(montype)) || ("OfficeSharePointServer".equals(montype)))
/*      */           {
/* 1076 */             String q4 = "UPDATE AM_ManagedObject SET DCSTARTED='2' WHERE RESOURCEID='" + resourceid + "'";
/* 1077 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q4);
/*      */           }
/*      */           
/* 1080 */           if ("WebsphereMQ".equals(montype)) {
/*      */             try {
/* 1082 */               List<String> mosToDelete = null;
/*      */               
/* 1084 */               boolean isSysObjectFilterEnabled = Boolean.parseBoolean(request.getParameter("FilterSysObject"));
/* 1085 */               List<String> sysQueues = null;List<String> sysChannels = null;
/*      */               
/* 1087 */               if (isSysObjectFilterEnabled) {
/* 1088 */                 String[] sysObjects = request.getParameter("SysObjects") != null ? request.getParameter("SysObjects").trim().split(";") : new String[0];
/* 1089 */                 for (String sysObject : sysObjects) {
/* 1090 */                   String[] temp = sysObject.trim().split(":");
/* 1091 */                   String currFilter = temp[0].trim();
/* 1092 */                   if (currFilter.contains("_QUEUES")) {
/* 1093 */                     mosToDelete = new ArrayList();
/* 1094 */                     sysQueues = new ArrayList();
/* 1095 */                     sysQueues = Arrays.asList(temp[1].trim().split(","));
/* 1096 */                     String sysQueuesCondition = currFilter.startsWith("INCLUDE_") ? "include" : "exclude";
/*      */                     
/* 1098 */                     ArrayList<ArrayList> existingQueuesList = DBUtil.getRows("select resourceid, resourcename,type,displayname from AM_ManagedObject m inner join AM_PARENTCHILDMAPPER p on p.childid = m.resourceid where p.parentid = " + resourceid + " and type='WebsphereMQ_Queue Monitoring_ROW' and displayname like 'SYSTEM%'");
/* 1099 */                     boolean isWildCard = ((String)sysQueues.get(0)).equals("*");
/* 1100 */                     if (!isWildCard) {
/* 1101 */                       for (ArrayList<String> row : existingQueuesList) {
/* 1102 */                         if ((sysQueuesCondition.equals("exclude")) && (MQUtil.isPatternMatch(sysQueues, String.valueOf(row.get(3))))) {
/* 1103 */                           mosToDelete.add(String.valueOf(row.get(0)));
/* 1104 */                         } else if ((sysQueuesCondition.equals("include")) && (!MQUtil.isPatternMatch(sysQueues, String.valueOf(row.get(3))))) {
/* 1105 */                           mosToDelete.add(String.valueOf(row.get(0)));
/*      */                         }
/*      */                       }
/* 1108 */                     } else if ((isWildCard) && (sysQueuesCondition.equals("exclude"))) {
/* 1109 */                       for (ArrayList<String> row : existingQueuesList) {
/* 1110 */                         mosToDelete.add(String.valueOf(row.get(0)));
/*      */                       }
/*      */                     }
/* 1113 */                     String mos = FormatUtil.join(mosToDelete, ",");
/* 1114 */                     AMLog.debug("NewMonitor :: updateMonitor :: child queue to delete for resourceid = " + resourceid + " : " + mos);
/* 1115 */                     ScheduleScriptDataCollection.deleteScriptRow(mos, true, "-1", "WebsphereMQ", "", -1, "Queue Monitoring");
/* 1116 */                     AMLog.debug("NewMonitor :: updateMonitor :: child mos deleted successfully for resourceid = " + resourceid);
/* 1117 */                   } else if ((currFilter.contains("_CHANNELS")) && (!currFilter.equals("INCLUDE_INACTIVE_CHANNELS"))) {
/* 1118 */                     mosToDelete = new ArrayList();
/* 1119 */                     sysChannels = new ArrayList();
/* 1120 */                     sysChannels = Arrays.asList(temp[1].trim().split(","));
/* 1121 */                     String sysChannelsCondition = currFilter.startsWith("INCLUDE_") ? "include" : "exclude";
/*      */                     
/* 1123 */                     ArrayList<ArrayList> existingChannelsList = DBUtil.getRows("select resourceid, resourcename,type,displayname from AM_ManagedObject m inner join AM_PARENTCHILDMAPPER p on p.childid = m.resourceid where p.parentid = " + resourceid + " and type='WebsphereMQ_Channel Monitoring_ROW' and displayname like 'SYSTEM%'");
/* 1124 */                     boolean isWildCard = ((String)sysChannels.get(0)).equals("*");
/* 1125 */                     if (!isWildCard) {
/* 1126 */                       for (ArrayList<String> row : existingChannelsList) {
/* 1127 */                         if ((sysChannelsCondition.equals("exclude")) && (MQUtil.isPatternMatch(sysChannels, String.valueOf(row.get(3))))) {
/* 1128 */                           mosToDelete.add(String.valueOf(row.get(0)));
/* 1129 */                         } else if ((sysChannelsCondition.equals("include")) && (!MQUtil.isPatternMatch(sysChannels, String.valueOf(row.get(3))))) {
/* 1130 */                           mosToDelete.add(String.valueOf(row.get(0)));
/*      */                         }
/*      */                       }
/* 1133 */                     } else if ((isWildCard) && (sysChannelsCondition.equals("exclude"))) {
/* 1134 */                       for (ArrayList<String> row : existingChannelsList) {
/* 1135 */                         mosToDelete.add(String.valueOf(row.get(0)));
/*      */                       }
/*      */                     }
/* 1138 */                     String mos = FormatUtil.join(mosToDelete, ",");
/* 1139 */                     AMLog.debug("NewMonitor :: updateMonitor :: child queue to delete for resourceid = " + resourceid + " : " + mos);
/* 1140 */                     ScheduleScriptDataCollection.deleteScriptRow(mos, true, "-1", "WebsphereMQ", "", -1, "Channel Monitoring");
/* 1141 */                     AMLog.debug("NewMonitor :: updateMonitor :: child mos deleted successfully for resourceid = " + resourceid);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             } catch (Exception e) {
/* 1146 */               AMLog.debug("NewMonitor :: updateMonitor :: error occured while deleting child mos for WebSphere MQ resourceid = " + resourceid + " : error messgae : " + e.getMessage());
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1151 */           Properties argsProps = new Properties();
/* 1152 */           Enumeration<String> eunmObj = request.getParameterNames();
/* 1153 */           while (eunmObj.hasMoreElements()) {
/* 1154 */             String paramName = (String)eunmObj.nextElement();
/* 1155 */             String[] arr = request.getParameterValues(paramName);
/* 1156 */             if ((arr != null) && (arr.length != 0))
/*      */             {
/*      */ 
/* 1159 */               StringBuilder builder = new StringBuilder();
/* 1160 */               for (int i = 0; i < arr.length; i++) {
/* 1161 */                 builder.append(arr[i]).append(",");
/*      */               }
/* 1163 */               argsProps.put(paramName, builder.substring(0, builder.length() - 1));
/*      */             } }
/* 1165 */           NewMonitorUtil.doActivitiesWhenConfigurationChanged(argsProps, true);
/*      */           
/* 1167 */           ActionMessages messages = new ActionMessages();
/* 1168 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.confmonitors.edit.success")));
/* 1169 */           saveMessages(request, messages);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1173 */           exc.printStackTrace();
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 1178 */           updateArgumentsforResource(request, args, Integer.parseInt(resourceid), montype, authresult, argsasprops);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1182 */           exc.printStackTrace();
/*      */         }
/*      */         
/* 1185 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1186 */         ResultSet rs = null;
/* 1187 */         String parentType = "null";
/*      */         try {
/* 1189 */           String colName = ConfMonitorConfiguration.getInstance().getColumnNameUsedForHost(montype);
/* 1190 */           if ((colName != null) && (request.getParameter(colName) != null)) {
/*      */             try {
/* 1192 */               String qry = "select type from AM_ManagedObject where resourceid=(select parentid from AM_PARENTCHILDMAPPER where childid=" + resourceid + ") and " + resourceid + " in (select CHILDID from AM_PARENTCHILDMAPPER)";
/* 1193 */               rs = AMConnectionPool.executeQueryStmt(qry);
/* 1194 */               if (rs.next())
/*      */               {
/* 1196 */                 parentType = rs.getString("TYPE");
/*      */               }
/*      */             }
/*      */             catch (Exception exc)
/*      */             {
/* 1201 */               exc.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/* 1205 */               AMConnectionPool.closeResultSet(rs);
/*      */             }
/* 1207 */             if (!Constants.resourceTypes.contains(parentType))
/*      */             {
/* 1209 */               String query = "update AM_ManagedObject set RESOURCENAME='" + request.getParameter(colName) + "' where resourceid=" + resourceid;
/* 1210 */               EnterpriseUtil.addUpdateQueryToFile(query);
/* 1211 */               AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(query);
/*      */             }
/*      */           }
/*      */         } catch (Exception eh) {
/* 1215 */           eh.printStackTrace();
/*      */         }
/*      */         
/* 1218 */         if (isAgentEnabled.equals("YES")) {
/* 1219 */           AMActionForm amform = (AMActionForm)form;
/* 1220 */           updateAgentAssociation(request, amform, Integer.parseInt(resourceid), montype, args, props, authresult, argsasprops);
/*      */         }
/* 1222 */         HashMap argumentsCtrlingDCComponents = ConfMonitorConfiguration.getInstance().getArgumentsCtrlingDCForType(montype);
/* 1223 */         if (argumentsCtrlingDCComponents != null) {
/* 1224 */           ArrayList argsListThatWillaffectDC = new ArrayList();
/* 1225 */           argsListThatWillaffectDC.addAll(argumentsCtrlingDCComponents.keySet());
/* 1226 */           DifferentialPollingUtil.updateDCComponentForResID(resourceid, argsListThatWillaffectDC, "ARGUMENT", argsasprops);
/*      */         }
/* 1228 */         DifferentialPollingUtil.updateUIElementsStatusForId(resourceid, montype, null, "ARGUMENT");
/*      */       }
/*      */       else {
/* 1231 */         String testdisplayname = new String();
/* 1232 */         int testpollinterval = 300;
/* 1233 */         ResultSet rs = null;
/*      */         try
/*      */         {
/* 1236 */           String query = "select DISPLAYNAME,POLLINTERVAL from AM_ScriptArgs where RESOURCEID=" + resourceid;
/* 1237 */           AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt(query);
/* 1238 */           String authEnabled = "false";
/* 1239 */           if (rs.next())
/*      */           {
/* 1241 */             testdisplayname = rs.getString("DISPLAYNAME");
/* 1242 */             testpollinterval = rs.getInt("POLLINTERVAL");
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1248 */           ex.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 1252 */           closeResultSet(rs);
/*      */         }
/*      */         
/* 1255 */         String q1 = "update AM_ScriptArgs set displayname='" + displayname + "',pollinterval=" + poll_interval + " where resourceid=" + resourceid;
/* 1256 */         String q2 = "update AM_ScriptArgs set displayname='" + displayname + "' where resourceid=" + resourceid;
/* 1257 */         String q3 = "update AM_ScriptArgs set pollinterval=" + poll_interval + " where resourceid=" + resourceid;
/* 1258 */         String q4 = "update AM_ManagedObject set DCSTARTED='2',DISPLAYNAME='" + displayname + "' where RESOURCEID=" + resourceid;
/*      */         
/* 1260 */         if ((!testdisplayname.equals(displayname)) && (testpollinterval != poll_interval))
/*      */         {
/* 1262 */           EnterpriseUtil.addUpdateQueryToFile(q1);
/* 1263 */           EnterpriseUtil.addUpdateQueryToFile(q4);
/* 1264 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q1);
/* 1265 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q4);
/* 1266 */           AMLog.debug("Changed the Display Name and Polling interval alone with Error Message :" + errormsg);
/* 1267 */           errormsg = authresult.getProperty("error") + FormatUtil.getString("am.displaynameandpollcheck.editmonitor");
/*      */         }
/* 1269 */         else if (!testdisplayname.equals(displayname))
/*      */         {
/* 1271 */           EnterpriseUtil.addUpdateQueryToFile(q2);
/* 1272 */           EnterpriseUtil.addUpdateQueryToFile(q4);
/* 1273 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q2);
/* 1274 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q4);
/* 1275 */           AMLog.debug("Changed the Display Name alone with Error Message :" + errormsg);
/* 1276 */           errormsg = authresult.getProperty("error") + FormatUtil.getString("am.displayname.editmonitor");
/*      */         }
/* 1278 */         else if (testpollinterval != poll_interval)
/*      */         {
/* 1280 */           errormsg = authresult.getProperty("error") + FormatUtil.getString("am.pollcheck.editmonitor");
/* 1281 */           EnterpriseUtil.addUpdateQueryToFile(q3);
/* 1282 */           AMLog.debug("Changed the Polling interval alone with Error Message :" + errormsg);
/* 1283 */           AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(q3);
/*      */         }
/* 1285 */         request.setAttribute("errormsg", errormsg);
/* 1286 */         errors.add("org.apache.struts.action.ERROR", new ActionError(errormsg));
/* 1287 */         saveErrors(request, errors);
/* 1288 */         newRequest = false;
/* 1289 */         request.setAttribute("appendHash", appendHash);
/* 1290 */         returnTo = "/manageConfMons.do?haid=" + request.getParameter("haid") + "&method=editMonitor&resourceid=" + resourceid + "&type=" + montype + "&resourcename=" + displayname;
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1296 */       exc.printStackTrace();
/*      */     }
/*      */     
/* 1299 */     return new ActionForward(returnTo + appendHash, newRequest);
/*      */   }
/*      */   
/*      */   public static void updateArgumentsforResource(HttpServletRequest request, ArrayList args, int resourceid, String montype, Properties authresult, Properties argsAsProps)
/*      */   {
/* 1304 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 1305 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1306 */     ArrayList args_name = (ArrayList)args.get(0);
/* 1307 */     ArrayList args_type = (ArrayList)args.get(1);
/* 1308 */     String toset = "";
/* 1309 */     String tosetAgentMonitor = "";
/* 1310 */     String tosetInAdmin = "";
/* 1311 */     String tosetAgentMonitorInAdmin = "";
/* 1312 */     boolean unManageVMs = false;
/* 1313 */     String isAgentEnabled = request.getParameter("isAgentEnabled");
/* 1314 */     String isAgentAssociated = request.getParameter("isAgentAssociated");
/* 1315 */     String enterpriseAdmindbServer = DBQueryUtil.getAdminDBType();
/* 1316 */     boolean isCM = false;
/* 1317 */     if ("cm".equalsIgnoreCase(request.getParameter("CredentialDetails")))
/*      */     {
/* 1319 */       isCM = true;
/* 1320 */       long getCredIDIfPresent = credUtil.checkResIDInMappingTable(resourceid);
/* 1321 */       long cmValueLong = new Long(request.getParameter("cmValue")).longValue();
/*      */       
/* 1323 */       if (getCredIDIfPresent == -1L)
/*      */       {
/* 1325 */         credUtil.addToCredentialToResourceMap(cmValueLong, resourceid);
/*      */ 
/*      */ 
/*      */       }
/* 1329 */       else if (cmValueLong != getCredIDIfPresent)
/*      */       {
/* 1331 */         credUtil.updateCredentialToResourceMap(cmValueLong, resourceid);
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/* 1337 */       credUtil.removeFromMapping(resourceid + "");
/*      */     }
/* 1339 */     for (int i = 0; i < args_name.size(); i++)
/*      */     {
/*      */ 
/* 1342 */       if (args_type.get(i).toString().equals("7"))
/*      */       {
/* 1344 */         String encVal = request.getParameter((String)args_name.get(i));
/* 1345 */         if (encVal == null) {
/* 1346 */           encVal = getConfMonitorPasswordFromDB(request, (String)args_name.get(i));
/*      */         }
/* 1348 */         if ((System.getProperty("am.dbserver.type") != null) && (System.getProperty("am.dbserver.type").equals("mssql")))
/*      */         {
/* 1350 */           if (!isCM)
/*      */           {
/* 1352 */             if (!encVal.trim().equals(""))
/*      */             {
/* 1354 */               toset = toset + "[" + args_name.get(i) + "]=" + DBQueryUtil.encode(encVal) + ",";
/*      */             }
/*      */             else
/*      */             {
/* 1358 */               toset = toset + "[" + args_name.get(i) + "]='" + encVal + "',";
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 1363 */           else if (!argsAsProps.getProperty((String)args_name.get(i)).trim().equals(""))
/*      */           {
/* 1365 */             toset = toset + "[" + args_name.get(i) + "]=" + DBQueryUtil.encode(argsAsProps.getProperty((String)args_name.get(i))) + ",";
/*      */           }
/*      */           else
/*      */           {
/* 1369 */             toset = toset + "[" + args_name.get(i) + "]='" + argsAsProps.getProperty((String)args_name.get(i)) + "',";
/*      */ 
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1375 */         else if (System.getProperty("am.dbserver.type").equals("mysql"))
/*      */         {
/* 1377 */           if (!isCM)
/*      */           {
/* 1379 */             toset = toset + "`" + args_name.get(i) + "`=encode('" + encVal + "','" + Constants.ENCODEKEY + "'),";
/*      */           }
/*      */           else
/*      */           {
/* 1383 */             toset = toset + "`" + args_name.get(i) + "`=encode('" + argsAsProps.getProperty((String)args_name.get(i)) + "','" + Constants.ENCODEKEY + "'),";
/*      */           }
/*      */           
/*      */ 
/*      */         }
/* 1388 */         else if (!isCM)
/*      */         {
/* 1390 */           toset = toset + "\"" + args_name.get(i) + "\"=" + DBQueryUtil.encode(encVal) + ",";
/*      */         }
/*      */         else
/*      */         {
/* 1394 */           toset = toset + "\"" + args_name.get(i) + "\"=" + DBQueryUtil.encode(argsAsProps.getProperty((String)args_name.get(i))) + ",";
/*      */         }
/*      */         
/*      */ 
/* 1398 */         tosetAgentMonitor = toset;
/*      */       }
/*      */       else
/*      */       {
/* 1402 */         String temp = "";
/* 1403 */         if (!isCM)
/*      */         {
/* 1405 */           temp = request.getParameter((String)args_name.get(i)) != null ? request.getParameter((String)args_name.get(i)) : "";
/*      */         }
/*      */         else
/*      */         {
/* 1409 */           temp = argsAsProps.getProperty((String)args_name.get(i)) != null ? argsAsProps.getProperty((String)args_name.get(i)) : "";
/*      */         }
/* 1411 */         String tempForAdmin = temp;
/*      */         
/* 1413 */         if (DBQueryUtil.isMysql())
/*      */         {
/* 1415 */           temp = StrUtil.findReplace(temp, "\\", "\\\\");
/*      */         }
/* 1417 */         tempForAdmin = enterpriseAdmindbServer.equals("mssql") ? StrUtil.findReplace(tempForAdmin, "\\", "\\\\") : tempForAdmin;
/*      */         
/* 1419 */         if (((String)args_name.get(i)).equalsIgnoreCase("AddVMS"))
/*      */         {
/*      */ 
/*      */ 
/* 1423 */           String addVMs_Previous = ConfMonitorUtil.getInstance().getArgColumnValue(montype, "AddVMS", resourceid);
/*      */           
/* 1425 */           AMLog.debug("NewMonitorConf : updateArgumentsforResource : resid=" + resourceid + " addVMs_Previous=" + addVMs_Previous + "temp==" + temp);
/* 1426 */           if (((addVMs_Previous.equalsIgnoreCase("yes")) && (temp.equalsIgnoreCase("False"))) || ((!addVMs_Previous.equals("1")) && (temp.equals("1"))))
/*      */           {
/* 1428 */             controllChildResources(resourceid, montype, true);
/*      */           }
/* 1430 */           if (((addVMs_Previous.equalsIgnoreCase("False")) && (temp.equalsIgnoreCase("yes"))) || ((!addVMs_Previous.equals("2")) && (temp.equals("2"))))
/*      */           {
/* 1432 */             controllChildResources(resourceid, montype, false);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1438 */         if (((String)args_name.get(i)).equalsIgnoreCase(WindowsClusterUtil.arg_eventlog))
/*      */         {
/* 1440 */           String eventLog = ConfMonitorUtil.getInstance().getArgColumnValue(montype, WindowsClusterUtil.arg_eventlog, resourceid);
/* 1441 */           AMLog.debug("NewMonitorConf : Windows cluster event log config changed from '" + eventLog + "' to '" + temp + "' for resourceid " + resourceid);
/* 1442 */           WindowsClusterUtil.getInstance().checkAndUpdateEventLog(resourceid, eventLog, temp);
/*      */         }
/*      */         
/* 1445 */         if (temp.indexOf("'") != -1)
/*      */         {
/*      */ 
/* 1448 */           temp = (System.getProperty("am.dbserver.type").equals("mssql")) || (System.getProperty("am.dbserver.type").equals("pgsql")) ? temp.replaceAll("'", "''") : StrUtil.findReplace(temp, "'", "\\'");
/* 1449 */           toset = toset + (System.getProperty("am.dbserver.type").equals("mysql") ? "`" + args_name.get(i) + "`='" + temp + "'," : System.getProperty("am.dbserver.type").equals("mssql") ? "[" + args_name.get(i) + "]='" + temp + "'," : new StringBuilder().append("\"").append(args_name.get(i)).append("\"='").append(temp).append("',").toString());
/* 1450 */           tempForAdmin = (enterpriseAdmindbServer.equals("mssql")) || (enterpriseAdmindbServer.equals("pgsql")) ? tempForAdmin.replaceAll("'", "''") : StrUtil.findReplace(tempForAdmin, "'", "\\'");
/*      */           
/* 1452 */           tosetInAdmin = tosetInAdmin + (enterpriseAdmindbServer.equals("mysql") ? "`" + args_name.get(i) + "`='" + tempForAdmin + "'," : enterpriseAdmindbServer.equals("mssql") ? "[" + args_name.get(i) + "]='" + tempForAdmin + "'," : new StringBuilder().append("\"").append(args_name.get(i)).append("\"='").append(tempForAdmin).append("',").toString());
/* 1453 */           tosetAgentMonitor = toset;
/* 1454 */           tosetAgentMonitorInAdmin = tosetInAdmin;
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1459 */           String charToAppend = NewMonitorUtil.getSpecialCharToAppend();
/* 1460 */           String charToAppendInAdminServer = enterpriseAdmindbServer.equals("mysql") ? "`" : "\"";
/*      */           
/* 1462 */           toset = toset + (System.getProperty("am.dbserver.type").equals("mssql") ? "[" + args_name.get(i) + "]='" + temp + "'," : new StringBuilder().append(charToAppend).append(args_name.get(i)).append(charToAppend).append("='").append(temp).append("',").toString());
/* 1463 */           tosetInAdmin = tosetInAdmin + (enterpriseAdmindbServer.equals("mssql") ? "[" + args_name.get(i) + "]='" + tempForAdmin + "'," : new StringBuilder().append(charToAppendInAdminServer).append(args_name.get(i)).append(charToAppendInAdminServer).append("='").append(tempForAdmin).append("',").toString());
/*      */           
/* 1465 */           if (!((String)args_name.get(i)).equalsIgnoreCase("isParent")) {
/* 1466 */             tosetAgentMonitor = tosetAgentMonitor + (System.getProperty("am.dbserver.type").equals("mssql") ? "[" + args_name.get(i) + "]='" + temp + "'," : new StringBuilder().append(charToAppend).append(args_name.get(i)).append(charToAppend).append("='").append(temp).append("',").toString());
/* 1467 */             tosetAgentMonitorInAdmin = tosetAgentMonitorInAdmin + (enterpriseAdmindbServer.equals("mssql") ? "[" + args_name.get(i) + "]='" + tempForAdmin + "'," : new StringBuilder().append(charToAppendInAdminServer).append(args_name.get(i)).append(charToAppendInAdminServer).append("='").append(tempForAdmin).append("',").toString());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1473 */     toset = toset.substring(0, toset.length() - 1);
/* 1474 */     tosetInAdmin = tosetInAdmin.substring(0, tosetInAdmin.length() - 1);
/*      */     
/* 1476 */     String baseid = "-1";
/* 1477 */     ResultSet rs = null;
/* 1478 */     baseid = Constants.getTypeId(montype);
/*      */     
/* 1480 */     if (montype.equals("QueryMonitor"))
/*      */     {
/*      */ 
/* 1483 */       Connection con = null;
/* 1484 */       Properties argsasprops = getValuesforArgs(request, args);
/* 1485 */       authresult = QueryUtil.getConnection(argsasprops);
/* 1486 */       con = (Connection)authresult.get("connection");
/* 1487 */       ResultSet dcStartSet = null;
/* 1488 */       String dcStart = "select DCSTARTED from AM_ManagedObject where RESOURCEID=" + resourceid;
/*      */       try
/*      */       {
/* 1491 */         AMConnectionPool.getInstance();dcStartSet = AMConnectionPool.executeQueryStmt(dcStart);
/* 1492 */         if ((dcStartSet.next()) && (dcStartSet.getInt(1) == 2))
/*      */         {
/* 1494 */           boolean inserted = QueryUtil.insertQryAttributes(argsasprops, resourceid, Integer.parseInt(baseid), con);
/* 1495 */           if (inserted)
/*      */           {
/* 1497 */             String dcStartUpdate = "UPDATE AM_ManagedObject SET DCSTARTED='1' WHERE RESOURCEID='" + resourceid + "'";
/* 1498 */             AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(dcStartUpdate);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1503 */           QueryUtil.updateAttributes(argsasprops, authresult, resourceid, Integer.parseInt(baseid));
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
/* 1514 */         AMConnectionPool.closeResultSet(dcStartSet);
/*      */         try
/*      */         {
/* 1517 */           if (con != null)
/*      */           {
/* 1519 */             con.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ee) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1529 */         qry = "update AM_ARGS_" + baseid + " set " + toset + " where resourceid=" + resourceid;
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1509 */         ex.printStackTrace();
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/* 1514 */         AMConnectionPool.closeResultSet(dcStartSet);
/*      */         try
/*      */         {
/* 1517 */           if (con != null)
/*      */           {
/* 1519 */             con.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ee) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     String qry;
/*      */     
/*      */ 
/* 1530 */     if (EnterpriseUtil.isManagedServer())
/*      */     {
/* 1532 */       EnterpriseUtil.addUpdateQueryToFile("update AM_ARGS_" + baseid + " set " + tosetInAdmin + " where resourceid=" + resourceid);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1537 */       AMConnectionPool.executeUpdateStmt(qry);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1541 */       exc.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1547 */     if (isAgentEnabled.equals("YES"))
/*      */     {
/* 1549 */       tosetAgentMonitor = tosetAgentMonitor.substring(0, tosetAgentMonitor.length() - 1);
/* 1550 */       qry = "update AM_ARGS_" + baseid + " set " + tosetAgentMonitor + " where resourceid in(select CHILDID  from AM_PARENTCHILDMAPPER PMO ,AM_ManagedObject MO WHERE PMO.PARENTID=" + resourceid + " AND MO.RESOURCEID=PMO.CHILDID AND MO.TYPE='" + montype + "')";
/* 1551 */       if (EnterpriseUtil.isManagedServer()) {
/* 1552 */         tosetAgentMonitorInAdmin = tosetAgentMonitorInAdmin.substring(0, tosetAgentMonitorInAdmin.length() - 1);
/* 1553 */         EnterpriseUtil.addUpdateQueryToFile("update AM_ARGS_" + baseid + " set " + tosetAgentMonitorInAdmin + " where resourceid in(select CHILDID  from AM_PARENTCHILDMAPPER PMO ,AM_ManagedObject MO WHERE PMO.PARENTID=" + resourceid + " AND MO.RESOURCEID=PMO.CHILDID AND MO.TYPE='" + montype + "')");
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1559 */         AMConnectionPool.executeUpdateStmt(qry);
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1563 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Properties getValuesforArgs(HttpServletRequest request, ArrayList args)
/*      */   {
/* 1572 */     Properties props = new Properties();
/* 1573 */     isCredentialManager = false;
/* 1574 */     ArrayList args_name = (ArrayList)args.get(0);
/* 1575 */     ArrayList args_type = (ArrayList)args.get(1);
/* 1576 */     NewMonitorConf newMonConf = new NewMonitorConf();
/* 1577 */     boolean isFromRestAPI = Boolean.valueOf(request.getParameter("fromRESTAPI")).booleanValue();
/* 1578 */     for (int i = 0; i < args_name.size(); i++)
/*      */     {
/* 1580 */       if (request.getParameter((String)args_name.get(i)) != null) {
/* 1581 */         props.setProperty((String)args_name.get(i), request.getParameter((String)args_name.get(i)));
/*      */       }
/* 1583 */       if ((args_type.get(i).toString().equals("7")) && (request.getParameter((String)args_name.get(i)) == null) && (!isFromRestAPI)) {
/* 1584 */         String monType = request.getParameter("montype");
/* 1585 */         if (newMonConf.preConfMap.containsKey(monType))
/*      */         {
/* 1587 */           String encval = "";
/* 1588 */           if (monType.toLowerCase().indexOf("snmp") == -1) {
/* 1589 */             encval = newMonConf.getPreConfMonitorPasswordFromDB(request);
/*      */           } else {
/* 1591 */             Properties snmpEncodedVals = getSNMPMonitorEncodedValsFromDB(request);
/* 1592 */             if ("SNMP:161".equals(monType)) {
/* 1593 */               encval = (String)snmpEncodedVals.get("snmpCommunityString");
/*      */             } else {
/* 1595 */               encval = (String)snmpEncodedVals.get("snmpAuthPassword");
/*      */             }
/*      */           }
/* 1598 */           props.setProperty((String)args_name.get(i), encval);
/*      */         } else {
/* 1600 */           props.setProperty((String)args_name.get(i), getConfMonitorPasswordFromDB(request, (String)args_name.get(i)));
/*      */         }
/*      */       }
/*      */     }
/* 1604 */     if ("cm".equalsIgnoreCase(request.getParameter("CredentialDetails")))
/*      */     {
/* 1606 */       isCredentialManager = true;
/* 1607 */       CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 1608 */       Properties credentialSelectedProperties = new Properties();
/* 1609 */       String cmValue = request.getParameter("cmValue");
/* 1610 */       if ((cmValue != null) && (!cmValue.equals("")))
/*      */       {
/* 1612 */         credentialID = new Long(cmValue).longValue();
/*      */       }
/* 1614 */       credentialSelectedProperties = credUtil.rowNameVsValue(credentialID);
/*      */       
/* 1616 */       props.putAll(credentialSelectedProperties);
/* 1617 */       if (("MSSQLDB:1433".equalsIgnoreCase(request.getParameter("montype"))) && ("false".equalsIgnoreCase(credentialSelectedProperties.getProperty("namedInstance")))) {
/* 1618 */         props.setProperty("instance", "");
/*      */       }
/*      */     }
/* 1621 */     if (("MSSQLDB:1433".equalsIgnoreCase(request.getParameter("montype"))) && ("Yes".equalsIgnoreCase(request.getParameter("namedInstance")))) {
/* 1622 */       props.setProperty("instance", request.getParameter("instance"));
/* 1623 */       props.setProperty("namedInstance", request.getParameter("namedInstance"));
/*      */     }
/* 1625 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getArgValuesAsString(HttpServletRequest request, ArrayList args)
/*      */   {
/* 1631 */     String argsasstring = "";
/* 1632 */     for (int i = 0; i < args.size(); i++)
/*      */     {
/* 1634 */       argsasstring = argsasstring + request.getParameter((String)args.get(i)) + " ";
/*      */     }
/* 1636 */     return argsasstring.trim();
/*      */   }
/*      */   
/*      */   public static Properties getClass(String type)
/*      */   {
/* 1641 */     return NewMonitorUtil.getClass(type);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward updateKey(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1650 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1651 */     ActionErrors errors = new ActionErrors();
/* 1652 */     String tableId = request.getParameter("tableId");
/*      */     try {
/* 1654 */       String resourceid = request.getParameter("resourceid");
/* 1655 */       String[] primaryKey = request.getParameterValues("pkValue");
/* 1656 */       String primaryKeys = "";
/*      */       
/* 1658 */       for (int i = 0; i < primaryKey.length; i++)
/*      */       {
/* 1660 */         if (!primaryKeys.trim().equals("")) {
/* 1661 */           primaryKeys = primaryKeys + "#" + primaryKey[i];
/*      */         }
/*      */         else {
/* 1664 */           primaryKeys = primaryKey[i];
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1669 */       String qry = "update AM_SCRIPT_TABLES set PRIMARY_COLUMN='" + primaryKeys + "' where TABLEID=" + tableId;
/*      */       
/* 1671 */       AMLog.debug("the qry:" + qry);
/* 1672 */       AMConnectionPool.executeUpdateStmt(qry);
/*      */       
/* 1674 */       ActionMessages messages = new ActionMessages();
/*      */       
/*      */ 
/* 1677 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Key(s) updated successfully"));
/* 1678 */       saveMessages(request, messages);
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1685 */       errors.add("org.apache.struts.action.ERROR", new ActionError("Updating key(s) failed"));
/* 1686 */       saveErrors(request, errors);
/*      */       
/* 1688 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 1692 */     return new ActionForward("/jsp/queryEdit.jsp?tableId=" + tableId);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void controllChildResources(int resourceid, String montype, boolean unmanage)
/*      */   {
/* 1700 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1701 */     String childtype = "-1";
/* 1702 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1705 */       String qry = "select TYPENAME from AM_MONITOR_TYPES WHERE PARENTTYPE='" + montype + "'";
/* 1706 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 1707 */       if (rs.next())
/*      */       {
/* 1709 */         childtype = rs.getString(1);
/*      */       }
/* 1711 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1715 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1719 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 1722 */     if (!childtype.equalsIgnoreCase("-1"))
/*      */     {
/*      */ 
/* 1725 */       ArrayList childResourceIds = new ArrayList();
/*      */       
/*      */       try
/*      */       {
/* 1729 */         String qry = "select CHILDID  from AM_PARENTCHILDMAPPER PMO ,AM_ManagedObject MO WHERE PMO.PARENTID=" + resourceid + " AND MO.RESOURCEID=PMO.CHILDID  AND MO.TYPE='" + childtype + "'";
/* 1730 */         rs = AMConnectionPool.executeQueryStmt(qry);
/* 1731 */         String childid = "";
/*      */         
/* 1733 */         while (rs.next())
/*      */         {
/*      */ 
/* 1736 */           childid = rs.getString(1);
/* 1737 */           childResourceIds.add(childid);
/* 1738 */           ConfMonitorConfiguration.getInstance().addUnmanagedChildList(childid);
/*      */         }
/*      */         
/*      */ 
/* 1742 */         int lengthIDs = childResourceIds.size();
/* 1743 */         String[] childIDs = new String[lengthIDs];
/* 1744 */         for (int i = 0; i < lengthIDs; i++)
/*      */         {
/* 1746 */           childIDs[i] = ((String)childResourceIds.get(i));
/*      */         }
/*      */         
/* 1749 */         if (unmanage)
/*      */         {
/* 1751 */           DataCollectionControllerUtil.updateUnManageMonitors(childIDs, "");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1756 */           DataCollectionControllerUtil.updateManageMonitors(childIDs, "");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1763 */         exc.printStackTrace();
/*      */ 
/*      */       }
/*      */       finally
/*      */       {
/*      */ 
/* 1769 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateAgentAssociation(HttpServletRequest request, AMActionForm amform, int resourceid, String montype, ArrayList args, Properties props, Properties authresult, Properties argsasprops)
/*      */     throws Exception
/*      */   {
/* 1777 */     ArrayList selectedAgents = null;
/* 1778 */     ArrayList deselectedAgents = null;
/* 1779 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1780 */     int baseid = NewMonitorUtil.getBaseId(montype);
/* 1781 */     String displayname = request.getParameter("displayname");
/* 1782 */     String pollinterval = request.getParameter("pollinterval");
/* 1783 */     String dcclass = props.getProperty("dcclass");
/* 1784 */     String dctype = props.getProperty("dctype");
/* 1785 */     String localId = EnterpriseUtil.getDistributedStartResourceId();
/* 1786 */     String basetype = "Script Monitor";
/*      */     
/*      */ 
/* 1789 */     String[] currentSelectedAgents = amform.getSelectedAgents();
/*      */     
/*      */ 
/*      */ 
/* 1793 */     ArrayList currentSelectedlist = new ArrayList();
/* 1794 */     if (currentSelectedAgents != null) {
/* 1795 */       currentSelectedlist = new ArrayList(Arrays.asList(currentSelectedAgents));
/*      */     }
/* 1797 */     AMLog.debug("currentSelectedlist::" + currentSelectedlist);
/*      */     
/*      */ 
/*      */ 
/* 1801 */     HashMap associationMap = NewMonitorUtil.getAssociatedAgents(Integer.toString(resourceid), montype);
/* 1802 */     ArrayList<String> preAssociatedAgents = (ArrayList)associationMap.get("associatedAgents");
/* 1803 */     AMLog.debug("preAssociatedAgents::" + preAssociatedAgents);
/* 1804 */     boolean isAssociatedtoLocal = ((Boolean)associationMap.get("isAssociatedtoLocal")).booleanValue();
/*      */     
/* 1806 */     String selQuery = "select AGENTID,DISPLAYNAME from AM_RBMAGENTDATA";
/* 1807 */     ResultSet rs = null;
/* 1808 */     String runOnServer = request.getParameter("runOnServer");
/*      */     try {
/* 1810 */       rs = AMConnectionPool.executeQueryStmt(selQuery);
/* 1811 */       while (rs.next()) {
/* 1812 */         String agentId = rs.getString("AGENTID");
/* 1813 */         if (!agentId.equals(localId))
/*      */         {
/*      */ 
/* 1816 */           AMLog.debug("agentID ==>" + agentId);
/* 1817 */           if ((currentSelectedlist.contains(agentId)) && (!preAssociatedAgents.contains(agentId))) {
/* 1818 */             if (selectedAgents == null) {
/* 1819 */               selectedAgents = new ArrayList();
/*      */             }
/* 1821 */             selectedAgents.add(agentId);
/*      */           }
/*      */           
/* 1824 */           if ((!currentSelectedlist.contains(agentId)) && (preAssociatedAgents.contains(agentId))) {
/* 1825 */             if (deselectedAgents == null) {
/* 1826 */               deselectedAgents = new ArrayList();
/*      */             }
/*      */             
/* 1829 */             deselectedAgents.add(agentId);
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1834 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1837 */       rs.close();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1842 */     String haid = null;
/* 1843 */     String addtoapplication = "false";
/*      */     
/* 1845 */     int childresid = -1;
/* 1846 */     int poll_interval = Integer.parseInt(pollinterval) * 60;
/* 1847 */     if ((runOnServer != null) && (!isAssociatedtoLocal))
/*      */     {
/* 1849 */       AMLog.debug("[ Creating Monitor on Local Server]");
/* 1850 */       int agentId = Integer.parseInt(localId);
/*      */       try {
/* 1852 */         ConfCreateMoInf inf = (ConfCreateMoInf)Class.forName("com.adventnet.appmanager.server.discovery.CreateNewMo").newInstance();
/* 1853 */         childresid = inf.createMo(displayname + "-Local", montype, poll_interval, baseid, basetype, "YES", dcclass, dctype, true, argsasprops, authresult, addtoapplication, haid, args, false, resourceid, agentId);
/* 1854 */         long nextRowId = DBQueryUtil.getLongIncrementedID("AM_EUM_PARENTCHILD_MAPPING", "RELATIONSHIPID");
/* 1855 */         String insertQry = "insert into AM_EUM_PARENTCHILD_MAPPING (RELATIONSHIPID,PARENTID,CHILDID) values (" + nextRowId + "," + resourceid + "," + childresid + ")";
/* 1856 */         AMConnectionPool.executeUpdateStmt(insertQry);
/*      */       }
/*      */       catch (Exception e) {
/* 1859 */         AMLog.debug("Class Not found exception");
/* 1860 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1867 */     AMLog.debug("isAssociatedtoLocal::" + isAssociatedtoLocal);
/* 1868 */     if ((runOnServer == null) && (isAssociatedtoLocal))
/*      */     {
/* 1870 */       String childId = getAssociatedChildId(resourceid, localId);
/* 1871 */       AMLog.debug("Childid::" + childId);
/* 1872 */       if ((childId != null) && (!childId.trim().equals(""))) {
/* 1873 */         Properties p = new Properties();
/* 1874 */         String[] resourceIds = new String[1];
/* 1875 */         resourceIds[0] = childId;
/* 1876 */         DataCollectionDBUtil.deleteMonitor(resourceIds, p, montype);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1881 */     if (selectedAgents != null)
/*      */     {
/* 1883 */       Iterator it = selectedAgents.iterator();
/* 1884 */       String[] strArray = new String[selectedAgents.size() + 1];
/* 1885 */       selectedAgents.toArray(strArray);
/* 1886 */       HashMap displayNamesMap = NewMonitorUtil.getAgentNames(strArray, selectedAgents.size());
/* 1887 */       String disname = displayname;
/* 1888 */       while (it.hasNext()) {
/* 1889 */         String agentid = (String)it.next();
/*      */         try {
/* 1891 */           AMLog.debug("[ Creating Monitor For Agent " + agentid + "]");
/* 1892 */           ConfCreateMoInf inf = (ConfCreateMoInf)Class.forName("com.adventnet.appmanager.server.discovery.CreateNewMo").newInstance();
/* 1893 */           int agentId = Integer.parseInt(agentid);
/* 1894 */           if ((displayNamesMap != null) && (displayNamesMap.get(agentid) != null)) {
/* 1895 */             disname = displayname + "-" + (String)displayNamesMap.get(agentid);
/*      */           }
/* 1897 */           childresid = inf.createMo(disname, montype, poll_interval, baseid, basetype, "YES", dcclass, dctype, true, argsasprops, authresult, addtoapplication, haid, args, false, resourceid, agentId);
/* 1898 */           long nextRowId = DBQueryUtil.getLongIncrementedID("AM_EUM_PARENTCHILD_MAPPING", "RELATIONSHIPID");
/* 1899 */           String insertQry = "insert into AM_EUM_PARENTCHILD_MAPPING (RELATIONSHIPID,PARENTID,CHILDID) values (" + nextRowId + "," + resourceid + "," + childresid + ")";
/* 1900 */           AMConnectionPool.executeUpdateStmt(insertQry);
/* 1901 */           if ("RBM".equals(montype)) {
/* 1902 */             new RealBrowserDC().associateRBMtoAgent(resourceid, childresid, agentId);
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 1909 */           ee.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1914 */     if (deselectedAgents != null)
/*      */     {
/* 1916 */       Iterator it = deselectedAgents.iterator();
/* 1917 */       String agentid = "";
/* 1918 */       while (it.hasNext()) {
/* 1919 */         String id = (String)it.next();
/* 1920 */         if (agentid.equals("")) {
/* 1921 */           agentid = id;
/*      */         }
/*      */         else {
/* 1924 */           agentid = agentid + "," + id;
/*      */         }
/*      */       }
/* 1927 */       String childId = getAssociatedChildId(resourceid, agentid);
/* 1928 */       AMLog.debug("Childid::" + childId);
/* 1929 */       if ((childId != null) && (!childId.trim().equals(""))) {
/* 1930 */         Properties p = new Properties();
/* 1931 */         StringTokenizer st = new StringTokenizer(childId, ",");
/* 1932 */         ArrayList<String> delList = new ArrayList();
/*      */         
/* 1934 */         while (st.hasMoreTokens()) {
/* 1935 */           delList.add(st.nextToken());
/*      */         }
/* 1937 */         String[] resourceIds = new String[delList.size()];
/* 1938 */         delList.toArray(resourceIds);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1944 */         DataCollectionDBUtil.deleteMonitor(resourceIds, p, montype);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getAssociatedChildId(int resourceid, String agentid) throws Exception
/*      */   {
/* 1951 */     String getchildIds = "select CHILDID from AM_EUM_PARENTCHILD_MAPPING,AM_RESOURCE_AGENT_MAPPING where  CHILDID=RESOURCEID and AGENTID in(" + agentid + ") and PARENTID=" + resourceid;
/* 1952 */     String associatedchildid = "";
/* 1953 */     ResultSet rs = null;
/*      */     
/* 1955 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1957 */       rs = AMConnectionPool.executeQueryStmt(getchildIds);
/* 1958 */       AMLog.debug("getchildIds::" + getchildIds);
/* 1959 */       while (rs.next()) {
/* 1960 */         if (associatedchildid.equals("")) {
/* 1961 */           associatedchildid = rs.getString("CHILDID");
/*      */         }
/*      */         else {
/* 1964 */           associatedchildid = associatedchildid + "," + rs.getString("CHILDID");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1969 */       e.printStackTrace();
/* 1970 */       return null;
/*      */     }
/*      */     finally {
/* 1973 */       rs.close();
/*      */     }
/* 1975 */     return associatedchildid;
/*      */   }
/*      */   
/*      */   public ActionForward editPreConfMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1980 */     String resourceID = request.getParameter("resourceid");
/* 1981 */     String haid = request.getParameter("haid");
/* 1982 */     Properties argsAsProps = new Properties();
/* 1983 */     argsAsProps.setProperty("CredentialDetails", "nocm");
/* 1984 */     String resourceType = request.getParameter("type");
/* 1985 */     resourceType = getResourceTypeForPreConf(resourceType);
/* 1986 */     ArrayList args = NewMonitorUtil.getArgsforConfMon(resourceType, request);
/* 1987 */     Properties propsFromDevice = new Properties();
/*      */     try
/*      */     {
/* 1990 */       propsFromDevice = getPropertiesFromType(request);
/*      */       
/* 1992 */       argsAsProps.putAll(propsFromDevice);
/* 1993 */       request.setAttribute("args", args);
/* 1994 */       removeSpecialCharactersforEdition(argsAsProps);
/* 1995 */       request.setAttribute("argsasprops", argsAsProps);
/* 1996 */       request.setAttribute("resourcename", request.getParameter("resourcename"));
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2000 */       ex.printStackTrace();
/*      */     }
/* 2002 */     return new ActionForward("/jsp/newConfType.jsp?haid=" + haid + "&customType=true&resourceid=" + resourceID);
/*      */   }
/*      */   
/*      */   private Properties getPropertiesFromType(HttpServletRequest request)
/*      */   {
/* 2007 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2008 */     Properties prop = new Properties();
/* 2009 */     String resourceType = request.getParameter("type");
/* 2010 */     resourceType = getResourceTypeForPreConf(resourceType);
/* 2011 */     String resourceID = request.getParameter("resourceid");
/* 2012 */     String resourceName = request.getParameter("resourcename");
/* 2013 */     String displayName = request.getParameter("displayName");
/* 2014 */     String netmask = "";
/* 2015 */     String componentName = request.getParameter("componentName");
/*      */     
/* 2017 */     long resID = new Long(resourceID).longValue();
/* 2018 */     long credentialID = credUtil.getCredentialIDForResourceID(resourceID);
/* 2019 */     prop.setProperty("netmask", netmask);
/* 2020 */     ResourceConfig getConfigurationsFromResourceConfig = null;
/*      */     try
/*      */     {
/* 2023 */       if (("host".equalsIgnoreCase(componentName)) || (Constants.serverTypes.indexOf(resourceType) != -1))
/*      */       {
/*      */ 
/* 2026 */         Properties hostProps = credUtil.getHostProperties(resourceID, credentialID);
/* 2027 */         prop.putAll(hostProps);
/* 2028 */         return prop;
/*      */       }
/* 2030 */       if (("SNMP:161".equalsIgnoreCase(componentName)) || ("SNMP:161".equalsIgnoreCase(resourceType)) || ("SNMP".equalsIgnoreCase(resourceType)))
/*      */       {
/* 2032 */         Properties getSNMPTypeProps = getSNMPTypeProps(resourceID, credentialID);
/* 2033 */         prop.setProperty("mode", "SNMP");
/* 2034 */         prop.putAll(getSNMPTypeProps);
/* 2035 */         return prop;
/*      */       }
/*      */       
/* 2038 */       Properties genProps = credUtil.getGeneralPropsForResource(resID);
/* 2039 */       prop.putAll(genProps);
/* 2040 */       if (credentialID != -1L)
/*      */       {
/* 2042 */         String cmValue = credentialID + "";
/* 2043 */         prop.setProperty("CredentialDetails", "cm");
/* 2044 */         prop.setProperty("cmValue", cmValue);
/*      */       }
/*      */       
/* 2047 */       if (this.resourceConfigMap.containsKey(resourceType.toString()))
/*      */       {
/* 2049 */         getConfigurationsFromResourceConfig = (ResourceConfig)this.api.getCollectData(resourceName, (String)this.resourceConfigMap.get(resourceType));
/* 2050 */         String hostName = getConfigurationsFromResourceConfig.getHostName();
/* 2051 */         prop.setProperty("host", hostName);
/* 2052 */         prop.setProperty("port", String.valueOf(getConfigurationsFromResourceConfig.getApplnDiscPort()));
/*      */         
/*      */ 
/* 2055 */         if (credentialID == -1L)
/*      */         {
/* 2057 */           String userName = getConfigurationsFromResourceConfig.getUserName();
/* 2058 */           prop.setProperty("username", userName);
/* 2059 */           prop.setProperty("password", getConfigurationsFromResourceConfig.getPassword());
/* 2060 */           prop.setProperty("instance", getConfigurationsFromResourceConfig.getDatabaseName());
/* 2061 */           prop.setProperty("jndiurl", getConfigurationsFromResourceConfig.getDatabaseName());
/* 2062 */           if (("JDK1.5:1099".equalsIgnoreCase(resourceType)) || ("JDK1.5".equalsIgnoreCase(resourceType))) {
/* 2063 */             if (!"".equals(userName)) {
/* 2064 */               prop.setProperty("authEnabled", "true");
/*      */             } else {
/* 2066 */               prop.setProperty("authEnabled", "false");
/*      */             }
/* 2068 */             Properties jmxprops = getJreJmxUrlProperties(resourceID);
/* 2069 */             prop.putAll(jmxprops);
/*      */           }
/* 2071 */           if (resourceType.toLowerCase().indexOf("mssql") != -1)
/*      */           {
/* 2073 */             String authType = userName.indexOf("\\") == -1 ? "SQL" : "Windows";
/* 2074 */             prop.setProperty("authType", authType);
/*      */           }
/*      */         }
/* 2077 */         if (resourceType.toLowerCase().indexOf("mssql") != -1)
/*      */         {
/* 2079 */           String instanceName = getConfigurationsFromResourceConfig.getDatabaseName();
/* 2080 */           if ((instanceName == null) || ("null".equalsIgnoreCase(instanceName)) || ("".equalsIgnoreCase(instanceName))) {
/* 2081 */             prop.setProperty("instance", "");
/*      */           }
/*      */           else {
/* 2084 */             prop.setProperty("instance", instanceName);
/* 2085 */             prop.setProperty("namedInstance", "Yes");
/*      */           }
/*      */         }
/* 2088 */         if (resourceType.toLowerCase().indexOf("sybase") != -1)
/*      */         {
/* 2090 */           String instanceName = getConfigurationsFromResourceConfig.getDatabaseName();
/* 2091 */           if ((instanceName == null) || ("null".equalsIgnoreCase(instanceName)) || ("".equalsIgnoreCase(instanceName))) {
/* 2092 */             prop.setProperty("instance", "master");
/* 2093 */             prop.setProperty("jconnect", "Yes");
/*      */           }
/*      */           else {
/* 2096 */             prop.setProperty("instance", instanceName);
/* 2097 */             prop.setProperty("namedInstance", "Yes");
/*      */           }
/*      */         }
/*      */         
/* 2101 */         return prop;
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
/* 2119 */       if (resourceType.toLowerCase().indexOf("weblogic") != -1)
/*      */       {
/* 2121 */         prop.putAll(getWebLogicProperties(resID, credentialID));
/* 2122 */         return prop;
/*      */       }
/* 2124 */       if (resourceType.toLowerCase().indexOf("apache") != -1)
/*      */       {
/* 2126 */         prop.putAll(getApacheProperties(resID, credentialID));
/* 2127 */         return prop;
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/* 2138 */       ex.printStackTrace();
/*      */     }
/* 2140 */     return prop;
/*      */   }
/*      */   
/*      */   private Properties getJreJmxUrlProperties(String resID)
/*      */   {
/* 2145 */     Properties prop = new Properties();
/* 2146 */     ResultSet rs = null;
/* 2147 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2150 */       String query = "SELECT JMXURL FROM AM_MX4J_EXT_INFO where RESOURCEID =" + resID;
/* 2151 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2152 */       while (rs.next())
/*      */       {
/* 2154 */         String jmxEnabled = "false";
/* 2155 */         String jmxurl = rs.getString("JMXURL");
/* 2156 */         jmxEnabled = (jmxurl != null) && (!"".equalsIgnoreCase(jmxurl)) ? "true" : "false";
/* 2157 */         prop.setProperty("jmxEnabled", jmxEnabled);
/* 2158 */         prop.setProperty("jmxurl", jmxurl);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2163 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2167 */       closeResultSet(rs);
/*      */     }
/* 2169 */     return prop;
/*      */   }
/*      */   
/*      */   private Properties getSNMPTypeProps(String resourceID, long credentialID)
/*      */   {
/* 2174 */     Properties prop = new Properties();
/* 2175 */     prop.setProperty("mode", "SNMP");
/* 2176 */     ResultSet rs = null;
/* 2177 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2180 */       String query = "select " + DBQueryUtil.decode("AM_SNMP_EXT_INFO.COMMUNITYSTRING") + " as communityString,AM_SNMP_EXT_INFO.TIMEOUT,AM_SNMP_EXT_INFO.VERSION,AM_SNMP_EXT_INFO.SNMPPORT,AM_SNMP_EXT_INFO.USERNAME,AM_SNMP_EXT_INFO.CONTEXTNAME,AM_SNMP_EXT_INFO.SECURITYLEVEL,AM_SNMP_EXT_INFO.AUTHPROTOCOL," + DBQueryUtil.decode("AM_SNMP_EXT_INFO.AUTHPASSWORD") + " as AUTHPASSWORD,AM_SNMP_EXT_INFO.PRIVPROTOCOL," + DBQueryUtil.decode("AM_SNMP_EXT_INFO.PRIVPASSWORD") + " as PRIVPASSWORD,AM_ManagedObject.displayname,ManagedObject.pollinterval,InetService.TARGETNAME from AM_SNMP_EXT_INFO left join AM_ManagedObject on AM_SNMP_EXT_INFO.resourceid=AM_ManagedObject.resourceid left join ManagedObject on AM_ManagedObject.resourcename=ManagedObject.name left join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME where AM_SNMP_EXT_INFO.resourceid=" + resourceID;
/* 2181 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2182 */       if (rs.next())
/*      */       {
/* 2184 */         String displayName = rs.getString("displayname");
/* 2185 */         String hostName = rs.getString("TARGETNAME");
/* 2186 */         int pollInterval = rs.getInt("pollinterval");
/* 2187 */         int timeOut = rs.getInt("TIMEOUT");
/* 2188 */         prop.setProperty("DisplayName", displayName);
/* 2189 */         prop.setProperty("pollinterval", pollInterval / 60 + "");
/* 2190 */         prop.setProperty("host", hostName);
/* 2191 */         prop.setProperty("timeout", timeOut + "");
/* 2192 */         prop.setProperty("snmpPort", rs.getInt("SNMPPORT") + "");
/* 2193 */         if (credentialID != -1L)
/*      */         {
/* 2195 */           prop.setProperty("SNMPCredentialDetails", "cmSNMP");
/* 2196 */           prop.setProperty("cmSNMPValue", credentialID + "");
/*      */         }
/*      */         else
/*      */         {
/* 2200 */           prop.setProperty("SNMPCredentialDetails", "nocmSNMP");
/* 2201 */           String version = rs.getString("VERSION");
/* 2202 */           if (!version.equalsIgnoreCase("v3"))
/*      */           {
/* 2204 */             prop.setProperty("snmpVersionValue", "v1v2");
/* 2205 */             prop.setProperty("snmpCommunityString", rs.getString("COMMUNITYSTRING"));
/*      */           }
/*      */           else
/*      */           {
/* 2209 */             prop.setProperty("snmpVersionValue", "v3");
/* 2210 */             prop.setProperty("snmpUserName", rs.getString("USERNAME"));
/* 2211 */             prop.setProperty("snmpContextName", rs.getString("CONTEXTNAME"));
/* 2212 */             String snmpSecurityLevel = rs.getString("SECURITYLEVEL");
/* 2213 */             prop.setProperty("snmpSecurityLevel", snmpSecurityLevel);
/* 2214 */             if (("AUTHPRIV".equalsIgnoreCase(snmpSecurityLevel)) || ("AUTHNOPRIV".equalsIgnoreCase(snmpSecurityLevel)))
/*      */             {
/* 2216 */               prop.setProperty("snmpAuthProtocol", rs.getString("AUTHPROTOCOL"));
/* 2217 */               prop.setProperty("snmpAuthPassword", rs.getString("AUTHPASSWORD"));
/*      */             }
/* 2219 */             if ("AUTHPRIV".equalsIgnoreCase(snmpSecurityLevel))
/*      */             {
/* 2221 */               prop.setProperty("snmpPrivPassword", rs.getString("PRIVPASSWORD"));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2229 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2233 */       closeResultSet(rs);
/*      */     }
/* 2235 */     return prop;
/*      */   }
/*      */   
/*      */   private Properties getJBossProperties(long resID, long credentialID)
/*      */   {
/* 2240 */     Properties prop = new Properties();
/* 2241 */     ResultSet rs = null;
/* 2242 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2245 */       if (credentialID == -1L)
/*      */       {
/*      */ 
/* 2248 */         String query = "select AM_RESOURCECONFIG.USERNAME, " + DBQueryUtil.decodeBytes("AM_RESOURCECONFIG.PASSWORD") + " as PASSWORD,AM_RESOURCECONFIG.DATABASENAME as VERSION,AM_JBOSS_AUTHINFO.sslenabled from AM_RESOURCECONFIG,AM_JBOSS_AUTHINFO where AM_RESOURCECONFIG.RESOURCEID=AM_JBOSS_AUTHINFO.RESOURCEID and AM_RESOURCECONFIG.RESOURCEID=" + resID;
/* 2249 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2250 */         while (rs.next())
/*      */         {
/* 2252 */           String authEnabled = "true";
/* 2253 */           if (rs.getString("USERNAME").equals(""))
/*      */           {
/* 2255 */             authEnabled = "false";
/*      */           }
/* 2257 */           prop.setProperty("username", rs.getString("USERNAME"));
/* 2258 */           prop.setProperty("password", new String(rs.getBytes("PASSWORD")));
/* 2259 */           prop.setProperty("version", rs.getString("VERSION"));
/* 2260 */           prop.setProperty("authEnabled", authEnabled);
/* 2261 */           prop.setProperty("sslenabled", rs.getString("sslenabled"));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2267 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2271 */       closeResultSet(rs);
/*      */     }
/* 2273 */     return prop;
/*      */   }
/*      */   
/*      */   private Properties getApacheProperties(long resID, long credentialID)
/*      */   {
/* 2278 */     Properties prop = new Properties();
/* 2279 */     ResultSet rs = null;
/* 2280 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2281 */     ApacheDataCollector apacheCollector = new ApacheDataCollector();
/* 2282 */     String sslEnabled = ApacheDataCollector.getsslstatus((int)resID);
/* 2283 */     String serverStatusURL = "";
/*      */     
/*      */     try
/*      */     {
/* 2287 */       if (credentialID == -1L)
/*      */       {
/* 2289 */         String query = "select AUTHENTICATION,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,ISURL,URL from AM_APACHE_CONFIG where RESID=" + resID;
/* 2290 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 2291 */         String authEnabled = "false";
/* 2292 */         if (rs.next())
/*      */         {
/* 2294 */           String username = rs.getString("USERNAME");
/* 2295 */           String password = rs.getString("PASSWORD");
/* 2296 */           if (rs.getBoolean("AUTHENTICATION"))
/*      */           {
/* 2298 */             authEnabled = "true";
/*      */           }
/* 2300 */           if (rs.getBoolean("ISURL"))
/*      */           {
/* 2302 */             serverStatusURL = rs.getString("URL");
/* 2303 */             prop.setProperty("serverstatusurl", "Yes");
/*      */           }
/* 2305 */           prop.setProperty("authEnabled", authEnabled);
/* 2306 */           prop.setProperty("sslenabled", sslEnabled);
/* 2307 */           prop.setProperty("apacheurl", serverStatusURL);
/* 2308 */           prop.setProperty("username", username);
/* 2309 */           prop.setProperty("password", password);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2316 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2320 */       closeResultSet(rs);
/*      */     }
/* 2322 */     return prop;
/*      */   }
/*      */   
/*      */ 
/*      */   private Properties getWebLogicProperties(long resID, long credentialID)
/*      */   {
/* 2328 */     Properties prop = new Properties();
/* 2329 */     ResultSet set = null;
/* 2330 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2333 */       if (credentialID == -1L)
/*      */       {
/* 2335 */         String query = "select USERNAME," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD,POLLINTERVAL,AM_ManagedObject.DISPLAYNAME,DATABASENAME,AM_JBOSS_AUTHINFO.sslenabled from AM_RESOURCECONFIG,CollectData,AM_ManagedObject  left outer join AM_JBOSS_AUTHINFO on AM_ManagedObject.RESOURCEID=AM_JBOSS_AUTHINFO.RESOURCEID where CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and AM_RESOURCECONFIG.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.resourceid=" + resID;
/* 2336 */         set = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 2338 */         if (set.next())
/*      */         {
/* 2340 */           String username = set.getString(1);
/* 2341 */           String password = new String(set.getBytes(2));
/* 2342 */           String version = set.getString(5);
/* 2343 */           prop.setProperty("username", username);
/* 2344 */           prop.setProperty("password", password);
/* 2345 */           prop.setProperty("version", version);
/* 2346 */           if (set.getString(6) != null) {
/* 2347 */             prop.setProperty("sslenabled", set.getString(6));
/*      */           } else {
/* 2349 */             prop.setProperty("sslenabled", "false");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 2356 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2360 */       closeResultSet(set);
/*      */     }
/* 2362 */     return prop;
/*      */   }
/*      */   
/*      */   private final void closeResultSet(ResultSet set)
/*      */   {
/* 2367 */     if (set != null)
/*      */     {
/*      */       try
/*      */       {
/* 2371 */         set.close();
/*      */       }
/*      */       catch (Exception ex) {
/* 2374 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private Properties getResourceConfigProps(HttpServletRequest request)
/*      */   {
/* 2384 */     String monType = request.getParameter("montype");
/* 2385 */     Properties resourceConfigProps = new Properties();
/*      */     try
/*      */     {
/* 2388 */       String password = request.getParameter("password");
/* 2389 */       if (password == null) {
/* 2390 */         password = getPreConfMonitorPasswordFromDB(request);
/*      */       }
/* 2392 */       resourceConfigProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 2393 */       resourceConfigProps.setProperty("password", Translate.decode(password));
/* 2394 */       if ((monType == null) || (monType.equalsIgnoreCase("null")))
/*      */       {
/* 2396 */         monType = request.getParameter("type");
/*      */       }
/* 2398 */       if (("JDK1.5:1099".equalsIgnoreCase(monType)) || ("JDK1.5".equalsIgnoreCase(monType)))
/*      */       {
/* 2400 */         resourceConfigProps.setProperty("jndiurl", request.getParameter("jndiurl"));
/* 2401 */         resourceConfigProps.setProperty("jmxurl", request.getParameter("jmxurl"));
/*      */       }
/* 2403 */       else if ((!"MSSQLDB:1433".equalsIgnoreCase(monType)) || ("Yes".equalsIgnoreCase(request.getParameter("namedInstance"))))
/*      */       {
/* 2405 */         resourceConfigProps.setProperty("instance", request.getParameter("instance"));
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2410 */       ex.printStackTrace();
/*      */     }
/* 2412 */     return resourceConfigProps;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Properties getWebLogicConfigProps(HttpServletRequest request)
/*      */   {
/* 2435 */     Properties wLogicProps = new Properties();
/*      */     try
/*      */     {
/* 2438 */       String password = request.getParameter("password");
/* 2439 */       if (password == null) {
/* 2440 */         password = getPreConfMonitorPasswordFromDB(request);
/*      */       }
/* 2442 */       wLogicProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 2443 */       wLogicProps.setProperty("password", Translate.decode(password));
/* 2444 */       wLogicProps.setProperty("version", request.getParameter("version"));
/* 2445 */       String sslEnabled = request.getParameter("sslenabled");
/*      */       
/* 2447 */       if ((sslEnabled != null) && (!sslEnabled.equals("")))
/*      */       {
/* 2449 */         wLogicProps.setProperty("sslenabled", sslEnabled);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2454 */       ex.printStackTrace();
/*      */     }
/* 2456 */     return wLogicProps;
/*      */   }
/*      */   
/*      */   private Properties getApacheConfigProps(HttpServletRequest request)
/*      */   {
/* 2461 */     Properties apacheProps = new Properties();
/*      */     try
/*      */     {
/* 2464 */       String sslEnabled = "true";
/* 2465 */       String authEnabled = "false";
/* 2466 */       sslEnabled = request.getParameter("sslenabled");
/* 2467 */       authEnabled = request.getParameter("authEnabled");
/* 2468 */       if ((sslEnabled != null) && (sslEnabled.equalsIgnoreCase("false")))
/*      */       {
/* 2470 */         sslEnabled = "false";
/*      */       }
/* 2472 */       if ((authEnabled != null) && (authEnabled.equalsIgnoreCase("true")))
/*      */       {
/* 2474 */         authEnabled = "true";
/* 2475 */         String password = request.getParameter("password");
/* 2476 */         if (password == null) {
/* 2477 */           password = getPreConfMonitorPasswordFromDB(request);
/*      */         }
/* 2479 */         apacheProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 2480 */         apacheProps.setProperty("password", Translate.decode(password));
/*      */       }
/* 2482 */       apacheProps.setProperty("authEnabled", authEnabled);
/* 2483 */       apacheProps.setProperty("sslenabled", sslEnabled);
/*      */       
/* 2485 */       String serverStatusURL = "false";
/* 2486 */       serverStatusURL = request.getParameter("serverstatusurl");
/* 2487 */       if ((serverStatusURL != null) && ("Yes".equalsIgnoreCase(serverStatusURL))) {
/* 2488 */         apacheProps.setProperty("apacheurl", request.getParameter("apacheurl"));
/* 2489 */         apacheProps.setProperty("isapacheurl", "true");
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2494 */       ex.printStackTrace();
/*      */     }
/* 2496 */     return apacheProps;
/*      */   }
/*      */   
/*      */ 
/*      */   private Properties getWebSphereConfigProps(HttpServletRequest request)
/*      */   {
/* 2502 */     Properties webSphereConfigProps = new Properties();
/*      */     try
/*      */     {
/* 2505 */       webSphereConfigProps.setProperty("version", request.getParameter("version"));
/* 2506 */       String sslEnabled = request.getParameter("sslenabled");
/* 2507 */       String soapPort = request.getParameter("soapport");
/* 2508 */       webSphereConfigProps.setProperty("soapport", soapPort);
/* 2509 */       if ((sslEnabled != null) && (sslEnabled.equalsIgnoreCase("false")))
/*      */       {
/* 2511 */         sslEnabled = "false";
/*      */       }
/* 2513 */       String authEnabled = request.getParameter("authEnabled");
/* 2514 */       webSphereConfigProps.setProperty("username", "");
/* 2515 */       webSphereConfigProps.setProperty("password", "");
/* 2516 */       if ((authEnabled != null) && (!authEnabled.equalsIgnoreCase("false")))
/*      */       {
/* 2518 */         String password = request.getParameter("password");
/* 2519 */         if (password == null) {
/* 2520 */           password = getPreConfMonitorPasswordFromDB(request);
/*      */         }
/* 2522 */         webSphereConfigProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 2523 */         webSphereConfigProps.setProperty("password", Translate.decode(password));
/*      */       }
/* 2525 */       webSphereConfigProps.setProperty("sslenabled", sslEnabled);
/* 2526 */       webSphereConfigProps.setProperty("authEnabled", authEnabled);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2530 */       ex.printStackTrace();
/*      */     }
/* 2532 */     return webSphereConfigProps;
/*      */   }
/*      */   
/*      */ 
/*      */   private Properties getJBossConfigProps(HttpServletRequest request)
/*      */   {
/* 2538 */     Properties jbossProps = new Properties();
/*      */     try
/*      */     {
/* 2541 */       jbossProps.setProperty("version", request.getParameter("version"));
/* 2542 */       String sslEnabled = request.getParameter("sslenabled");
/* 2543 */       if ((sslEnabled != null) && (sslEnabled.equalsIgnoreCase("false")))
/*      */       {
/* 2545 */         sslEnabled = "false";
/*      */       }
/* 2547 */       String authEnabled = request.getParameter("authEnabled");
/* 2548 */       jbossProps.setProperty("username", "");
/* 2549 */       jbossProps.setProperty("password", "");
/* 2550 */       if ((authEnabled != null) && (!authEnabled.equalsIgnoreCase("false")))
/*      */       {
/* 2552 */         jbossProps.setProperty("username", request.getParameter("username"));
/* 2553 */         jbossProps.setProperty("password", request.getParameter("password"));
/*      */       }
/* 2555 */       jbossProps.setProperty("sslenabled", sslEnabled);
/* 2556 */       jbossProps.setProperty("authEnabled", authEnabled);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2560 */       ex.printStackTrace();
/*      */     }
/* 2562 */     return jbossProps;
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
/*      */ 
/*      */ 
/*      */   private Properties getBasicPropertiesForUpdate(HttpServletRequest request)
/*      */   {
/* 2582 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2583 */     Properties generalProps = new Properties();
/* 2584 */     generalProps.setProperty("HOST", request.getParameter("host"));
/* 2585 */     String mode = request.getParameter("mode");
/* 2586 */     if (mode == null)
/*      */     {
/* 2588 */       String port = request.getParameter("port");
/* 2589 */       port = port != null ? port : "";
/* 2590 */       generalProps.setProperty("PORT", port);
/*      */     }
/* 2592 */     if ((request.getParameter("resourceid") != null) && (!request.getParameter("resourceid").equals("")))
/*      */     {
/* 2594 */       generalProps.setProperty("id", request.getParameter("resourceid"));
/* 2595 */       generalProps.setProperty("name", credUtil.getResourceNameFromResourceID(request.getParameter("resourceid")));
/*      */     }
/*      */     else
/*      */     {
/* 2599 */       generalProps.setProperty("name", request.getParameter("host"));
/*      */     }
/* 2601 */     String credDetails = request.getParameter("CredentialDetails");
/* 2602 */     if (!"cm".equalsIgnoreCase(credDetails))
/*      */     {
/* 2604 */       if ((request.getParameter("authEnabled") != null) && (!request.getParameter("authEnabled").equals("")))
/*      */       {
/* 2606 */         generalProps.setProperty("authEnabled", request.getParameter("authEnabled"));
/*      */       }
/*      */     }
/* 2609 */     generalProps.setProperty("displayname", request.getParameter("displayname"));
/* 2610 */     generalProps.setProperty("DISPLAYNAME", request.getParameter("displayname"));
/* 2611 */     generalProps.setProperty("displayName", request.getParameter("displayname"));
/* 2612 */     generalProps.setProperty("givenhost", request.getParameter("host"));
/* 2613 */     generalProps.setProperty("pollinterval", Integer.parseInt(request.getParameter("pollinterval")) * 60 + "");
/* 2614 */     return generalProps;
/*      */   }
/*      */   
/*      */   private Properties getSNMPProperties(HttpServletRequest request)
/*      */   {
/* 2619 */     AdminActions adminActions = new AdminActions();
/* 2620 */     Properties snmpProps = adminActions.setSnmpValuesFromRequest("host", request);
/* 2621 */     return snmpProps;
/*      */   }
/*      */   
/*      */   private Properties getPropsAsPerProtocol(HttpServletRequest request, String protocol)
/*      */   {
/* 2626 */     Properties protocolProps = new Properties();
/* 2627 */     String sshPKAuth = request.getParameter("sshPKAuth");
/* 2628 */     protocolProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 2629 */     protocolProps.setProperty("prompt", Translate.decode(request.getParameter("prompt")));
/* 2630 */     String password = request.getParameter("password");
/* 2631 */     if (((sshPKAuth == null) || ("false".equalsIgnoreCase(sshPKAuth))) && (password == null)) {
/* 2632 */       password = getPreConfMonitorPasswordFromDB(request);
/*      */     }
/* 2634 */     if (password != null)
/*      */     {
/* 2636 */       protocolProps.setProperty("password", Translate.decode(password));
/*      */     }
/* 2638 */     if ((protocol.equalsIgnoreCase("ssh")) && (sshPKAuth != null) && (sshPKAuth.equalsIgnoreCase("yes")))
/*      */     {
/*      */ 
/* 2641 */       protocolProps.setProperty("sshPKAuthChecked", "checked");
/* 2642 */       protocolProps.setProperty("privateKey", request.getParameter("description"));
/* 2643 */       protocolProps.setProperty("password", Translate.decode(request.getParameter("passphrase")));
/* 2644 */       protocolProps.setProperty("passphrase", Translate.decode(request.getParameter("passphrase")));
/*      */     }
/*      */     
/*      */ 
/* 2648 */     return protocolProps;
/*      */   }
/*      */   
/*      */   public Properties getAuthResultAsPerResourceType(String monType, HttpServletRequest request, boolean isTestCredentials)
/*      */   {
/* 2653 */     monType = getResourceTypeForPreConf(monType);
/* 2654 */     CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2655 */     Properties authResult = new Properties();
/* 2656 */     Properties credProps = new Properties();
/* 2657 */     String temp = (String)request.getAttribute("testCredentialReq");
/* 2658 */     if ((temp != null) && (temp.equals("true"))) {
/* 2659 */       credProps.setProperty("testCredentialReq", "true");
/*      */     }
/* 2661 */     HashMap detailsToSchedule = new HashMap();
/* 2662 */     String resourceID = request.getParameter("resourceid");
/* 2663 */     detailsToSchedule.put("resourceID", resourceID);
/*      */     
/* 2665 */     String dcClassName = (String)credUtil.populateClassMap().get(monType);
/* 2666 */     detailsToSchedule.put("dcClassName", dcClassName);
/* 2667 */     detailsToSchedule.put("monType", monType);
/* 2668 */     long cmValueLong = -1L;
/* 2669 */     boolean scheduleDataCollection = true;
/* 2670 */     boolean isNonCMValue = false;
/*      */     try
/*      */     {
/* 2673 */       if (this.resourceConfigMap.containsKey(monType))
/*      */       {
/* 2675 */         dcClassName = (String)credUtil.populateClassMap().get(monType);
/* 2676 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2677 */         String credDetails = request.getParameter("CredentialDetails");
/* 2678 */         if ("cm".equalsIgnoreCase(credDetails))
/*      */         {
/* 2680 */           String cmValue = request.getParameter("cmValue");
/* 2681 */           cmValueLong = new Long(cmValue).longValue();
/* 2682 */           credProps = credUtil.getPropertiesFromCMSelected(cmValue);
/* 2683 */           detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmValueLong));
/* 2684 */           if ((monType.toLowerCase().indexOf("mssql") != -1) && ("false".equalsIgnoreCase(credProps.getProperty("namedInstance"))))
/*      */           {
/* 2686 */             credProps.put("instance", "");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 2691 */           credProps = getResourceConfigProps(request);
/* 2692 */           isNonCMValue = true;
/*      */         }
/*      */         
/* 2695 */         if ((monType.toLowerCase().indexOf("mssql") != -1) && ("Yes".equalsIgnoreCase(request.getParameter("namedInstance"))))
/*      */         {
/* 2697 */           credProps.put("instance", request.getParameter("instance"));
/* 2698 */           credProps.put("namedInstance", request.getParameter("namedInstance"));
/*      */         }
/* 2700 */         if (monType.toLowerCase().indexOf("sybase") != -1)
/*      */         {
/* 2702 */           if ("yes".equalsIgnoreCase(request.getParameter("jconnect").toLowerCase()))
/*      */           {
/* 2704 */             credProps.put("jconnect", "Yes");
/*      */           }
/*      */           else
/*      */           {
/* 2708 */             credProps.put("jconnect", "No");
/*      */           }
/*      */         }
/*      */         
/* 2712 */         detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2713 */         credProps.putAll(getBasicPropertiesForUpdate(request));
/* 2714 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 2715 */         authResult = dcClass.CheckAuthentication(credProps);
/* 2716 */         String result = authResult.getProperty("authentication");
/* 2717 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 2719 */           setDisplayName(credProps, resourceID);
/* 2720 */           if (monType.indexOf("JDK1.5") != -1) {
/* 2721 */             setPort(credProps, resourceID);
/*      */           }
/* 2723 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
/*      */         }
/*      */       }
/*      */       
/* 2727 */       if (monType.toLowerCase().indexOf("apache") != -1)
/*      */       {
/* 2729 */         dcClassName = (String)credUtil.populateClassMap().get(monType);
/* 2730 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2731 */         String credDetails = request.getParameter("CredentialDetails");
/* 2732 */         if ("cm".equalsIgnoreCase(credDetails))
/*      */         {
/* 2734 */           String cmValue = request.getParameter("cmValue");
/* 2735 */           cmValueLong = new Long(cmValue).longValue();
/* 2736 */           credProps = credUtil.getPropertiesFromCMSelected(cmValue);
/* 2737 */           detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmValueLong));
/*      */         }
/*      */         else
/*      */         {
/* 2741 */           credProps = getApacheConfigProps(request);
/* 2742 */           isNonCMValue = true;
/*      */         }
/* 2744 */         detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2745 */         credProps.putAll(getBasicPropertiesForUpdate(request));
/* 2746 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 2747 */         authResult = dcClass.CheckAuthentication(credProps);
/* 2748 */         String result = authResult.getProperty("authentication");
/* 2749 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 2751 */           setDisplayName(credProps, resourceID);
/* 2752 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
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
/* 2785 */       if (monType.toLowerCase().indexOf("websphere") != -1)
/*      */       {
/* 2787 */         dcClassName = (String)credUtil.populateClassMap().get(monType);
/* 2788 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2789 */         String credDetails = request.getParameter("CredentialDetails");
/* 2790 */         if ("cm".equalsIgnoreCase(credDetails))
/*      */         {
/* 2792 */           String cmValue = request.getParameter("cmValue");
/* 2793 */           cmValueLong = new Long(cmValue).longValue();
/* 2794 */           credProps = credUtil.getPropertiesFromCMSelected(cmValue);
/* 2795 */           detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmValueLong));
/*      */         }
/*      */         else
/*      */         {
/* 2799 */           credProps = getWebSphereConfigProps(request);
/* 2800 */           isNonCMValue = true;
/*      */         }
/* 2802 */         detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2803 */         credProps.putAll(getBasicPropertiesForUpdate(request));
/* 2804 */         credProps.setProperty("resourceType", "WebSphere-server");
/* 2805 */         String mode = request.getParameter("mode");
/* 2806 */         credProps.setProperty("MODE", mode);
/* 2807 */         if ("ND".equalsIgnoreCase(mode))
/*      */         {
/* 2809 */           credProps.setProperty("ndhost", request.getParameter("ndhost"));
/* 2810 */           credProps.setProperty("ndport", request.getParameter("ndport"));
/* 2811 */           credProps.setProperty("AppServers", request.getParameter("AppServers"));
/*      */         }
/* 2813 */         String port = request.getParameter("port");
/* 2814 */         port = port != null ? port : "";
/* 2815 */         credProps.setProperty("PORT", port);
/* 2816 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 2817 */         authResult = dcClass.CheckAuthentication(credProps);
/* 2818 */         String result = authResult.getProperty("authentication");
/* 2819 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 2821 */           setDisplayName(credProps, resourceID);
/* 2822 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
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
/* 2856 */       if (monType.toLowerCase().indexOf("weblogic") != -1)
/*      */       {
/* 2858 */         dcClassName = (String)credUtil.populateClassMap().get(monType);
/* 2859 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2860 */         String credDetails = request.getParameter("CredentialDetails");
/* 2861 */         if ("cm".equalsIgnoreCase(credDetails))
/*      */         {
/* 2863 */           String cmValue = request.getParameter("cmValue");
/* 2864 */           cmValueLong = new Long(cmValue).longValue();
/* 2865 */           credProps = credUtil.getPropertiesFromCMSelected(cmValue);
/* 2866 */           detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmValueLong));
/*      */         }
/*      */         else
/*      */         {
/* 2870 */           credProps = getWebLogicConfigProps(request);
/* 2871 */           isNonCMValue = true;
/*      */         }
/* 2873 */         detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2874 */         credProps.putAll(getBasicPropertiesForUpdate(request));
/* 2875 */         credProps.setProperty("resourceType", "WEBLOGIC-server");
/* 2876 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 2877 */         authResult = dcClass.CheckAuthentication(credProps);
/* 2878 */         String result = authResult.getProperty("authentication");
/* 2879 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 2881 */           setDisplayName(credProps, resourceID);
/* 2882 */           setSSLEnabled(credProps, resourceID);
/* 2883 */           setPort(credProps, resourceID);
/* 2884 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
/*      */         }
/*      */       }
/*      */       
/* 2888 */       if (monType.toLowerCase().indexOf("snmp") != -1)
/*      */       {
/*      */ 
/* 2891 */         dcClassName = "com.adventnet.appmanager.cam.CAMDataCollectionController";
/* 2892 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2893 */         String resourceName = request.getParameter("resourcename");
/* 2894 */         String snmpCredDetails = request.getParameter("SNMPCredentialDetails");
/* 2895 */         if ("cmSNMP".equalsIgnoreCase(snmpCredDetails))
/*      */         {
/* 2897 */           String cmSNMPValue = request.getParameter("cmSNMPValue");
/* 2898 */           long cmSNMPValueLong = new Long(cmSNMPValue).longValue();
/* 2899 */           credProps = credUtil.getPropertiesFromCMSelected(cmSNMPValue);
/* 2900 */           Properties snmpProperties = getSNMPProperties(request);
/* 2901 */           credProps.putAll(snmpProperties);
/* 2902 */           detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmSNMPValueLong));
/*      */         }
/*      */         else
/*      */         {
/* 2906 */           Properties snmpProperties = getSNMPProperties(request);
/* 2907 */           credProps.putAll(snmpProperties);
/* 2908 */           isNonCMValue = true;
/*      */         }
/* 2910 */         detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2911 */         credProps.setProperty("MODE", "SNMP");
/* 2912 */         credProps.setProperty("SNMPPORT", request.getParameter("snmpPort"));
/* 2913 */         credProps.putAll(getBasicPropertiesForUpdate(request));
/* 2914 */         credProps.setProperty("resourceType", "SNMP:161");
/*      */         
/* 2916 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 2917 */         authResult = dcClass.CheckAuthentication(credProps);
/* 2918 */         String result = authResult.getProperty("authentication");
/* 2919 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 2921 */           setDisplayName(credProps, resourceID);
/* 2922 */           setSNMPPort(credProps, resourceID);
/* 2923 */           setPollInterval(resourceID, request.getParameter("pollinterval"));
/* 2924 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
/*      */         }
/*      */       }
/* 2927 */       if (Constants.serverTypes.indexOf(monType) != -1)
/*      */       {
/* 2929 */         dcClassName = "com.adventnet.appmanager.server.hostresources.datacollection.ScheduleHostDataCollection";
/* 2930 */         detailsToSchedule.put("dcClass", dcClassName);
/* 2931 */         String mode = request.getParameter("mode");
/*      */         
/* 2933 */         String os = request.getParameter("os");
/*      */         
/* 2935 */         if (mode.equalsIgnoreCase("SNMP"))
/*      */         {
/* 2937 */           String snmpCredDetails = request.getParameter("SNMPCredentialDetails");
/* 2938 */           if ("cmSNMP".equalsIgnoreCase(snmpCredDetails))
/*      */           {
/* 2940 */             String cmSNMPValue = request.getParameter("cmSNMPValue");
/* 2941 */             long cmSNMPValueLong = new Long(cmSNMPValue).longValue();
/* 2942 */             credProps = credUtil.getPropertiesFromCMSelected(cmSNMPValue);
/* 2943 */             Properties snmpProperties = getSNMPProperties(request);
/* 2944 */             credProps.putAll(snmpProperties);
/* 2945 */             detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmSNMPValueLong));
/*      */           }
/*      */           else
/*      */           {
/* 2949 */             Properties snmpProperties = getSNMPProperties(request);
/* 2950 */             credProps.putAll(snmpProperties);
/* 2951 */             isNonCMValue = true;
/*      */           }
/* 2953 */           detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2954 */           credProps.setProperty("MODE", "SNMP");
/* 2955 */           credProps.setProperty("SNMPPORT", request.getParameter("snmpPort"));
/* 2956 */           credProps.setProperty("os", os);
/* 2957 */           credProps.putAll(getBasicPropertiesForUpdate(request));
/*      */         }
/* 2959 */         if (mode.equalsIgnoreCase("TELNET"))
/*      */         {
/* 2961 */           String telnetCredDetails = request.getParameter("TelnetCredentialDetails");
/* 2962 */           if ("cmTelnet".equalsIgnoreCase(telnetCredDetails))
/*      */           {
/* 2964 */             String cmTelnetValue = request.getParameter("cmTelnetValue");
/* 2965 */             long cmTelnetValueLong = new Long(cmTelnetValue).longValue();
/* 2966 */             credProps = credUtil.getPropertiesFromCMSelected(cmTelnetValue);
/* 2967 */             detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmTelnetValueLong));
/*      */           }
/*      */           else
/*      */           {
/* 2971 */             Properties telnetProps = getPropsAsPerProtocol(request, "Telnet");
/* 2972 */             credProps.putAll(telnetProps);
/* 2973 */             isNonCMValue = true;
/*      */           }
/* 2975 */           detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 2976 */           credProps.setProperty("MODE", "TELNET");
/* 2977 */           credProps.setProperty("TELNETPORT", request.getParameter("snmptelnetport"));
/* 2978 */           credProps.setProperty("os", os);
/* 2979 */           credProps.putAll(getBasicPropertiesForUpdate(request));
/*      */         }
/* 2981 */         if (mode.equalsIgnoreCase("SSH"))
/*      */         {
/* 2983 */           String sshCredDetails = request.getParameter("SSHCredentialDetails");
/* 2984 */           if ("cmSSH".equalsIgnoreCase(sshCredDetails))
/*      */           {
/* 2986 */             String cmSSHValue = request.getParameter("cmSSHValue");
/* 2987 */             long cmSSHValueLong = new Long(cmSSHValue).longValue();
/* 2988 */             credProps = credUtil.getPropertiesFromCMSelected(cmSSHValue);
/* 2989 */             detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmSSHValueLong));
/* 2990 */             Properties credNameProp = credUtil.getDetailsForACredential(cmSSHValue);
/* 2991 */             String credentialName = credNameProp.getProperty("credentialName");
/* 2992 */             if (credProps.getProperty("sshPKAuth", "").equalsIgnoreCase("true")) {
/* 2993 */               String passphrase = credProps.getProperty("passphrase", "");
/* 2994 */               String privateKeyFileName = NMS_HOME + File.separator + "credentialManager_privateKey_" + credentialName + ".txt";
/* 2995 */               String fileFormatAsPerCanonicalName = credUtil.getNameAsPerSSHFormat(request.getParameter("host"));
/* 2996 */               String serverSSHFile = NMS_HOME + File.separator + fileFormatAsPerCanonicalName;
/* 2997 */               credUtil.copyFile(privateKeyFileName, serverSSHFile);
/* 2998 */               credProps.setProperty("sshPKAuthChecked", "checked");
/* 2999 */               credProps.setProperty("sshPKFileName", privateKeyFileName.trim());
/* 3000 */               credProps.setProperty("password", passphrase);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 3005 */             Properties sshProps = getPropsAsPerProtocol(request, "SSH");
/* 3006 */             credProps.putAll(sshProps);
/* 3007 */             isNonCMValue = true;
/*      */           }
/* 3009 */           detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 3010 */           credProps.setProperty("MODE", "SSH");
/* 3011 */           credProps.setProperty("TELNETPORT", request.getParameter("snmptelnetport"));
/* 3012 */           credProps.setProperty("os", os);
/* 3013 */           credProps.putAll(getBasicPropertiesForUpdate(request));
/*      */         }
/* 3015 */         if (mode.equalsIgnoreCase("WMI"))
/*      */         {
/* 3017 */           String wmiCredDetails = request.getParameter("WMICredentialDetails");
/* 3018 */           String eventLogStatus = request.getParameter("eventlog_status");
/* 3019 */           if ("cmWMI".equalsIgnoreCase(wmiCredDetails))
/*      */           {
/* 3021 */             String cmWMIValue = request.getParameter("cmWMIValue");
/* 3022 */             long cmWMIValueLong = new Long(cmWMIValue).longValue();
/* 3023 */             credProps = credUtil.getPropertiesFromCMSelected(cmWMIValue);
/* 3024 */             detailsToSchedule.put("credentialIDInLong", Long.valueOf(cmWMIValueLong));
/*      */           }
/*      */           else
/*      */           {
/* 3028 */             String password = request.getParameter("password");
/* 3029 */             if (password == null) {
/* 3030 */               password = getPreConfMonitorPasswordFromDB(request);
/*      */             }
/* 3032 */             credProps.setProperty("username", Translate.decode(request.getParameter("username")));
/* 3033 */             credProps.setProperty("password", Translate.decode(password));
/* 3034 */             isNonCMValue = true;
/*      */           }
/* 3036 */           detailsToSchedule.put("isNonCMValue", Boolean.valueOf(isNonCMValue));
/* 3037 */           credProps.setProperty("MODE", "WMI");
/* 3038 */           credProps.setProperty("os", os);
/* 3039 */           credProps.setProperty("eventlog_status", eventLogStatus);
/* 3040 */           credProps.putAll(getBasicPropertiesForUpdate(request));
/*      */         }
/* 3042 */         credProps.setProperty("TIMEOUT", request.getParameter("timeout"));
/* 3043 */         AMDCInf dcClass = (AMDCInf)Class.forName(dcClassName).newInstance();
/* 3044 */         credProps.setProperty("testCredentials", "true");
/* 3045 */         authResult = dcClass.CheckAuthentication(credProps);
/* 3046 */         String result = authResult.getProperty("authentication");
/* 3047 */         if (("passed".equalsIgnoreCase(result)) && (!isTestCredentials))
/*      */         {
/* 3049 */           scheduleDataCollection = credUtil.scheduleDataCollectionAsPerType(detailsToSchedule, credProps);
/*      */ 
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 3060 */       ex.printStackTrace();
/*      */     }
/* 3062 */     return authResult;
/*      */   }
/*      */   
/*      */   private String updateAsPerType(HttpServletRequest request, ArrayList args, Properties argsasprops)
/*      */   {
/* 3067 */     String errormsg = "";
/* 3068 */     String monType = request.getParameter("montype");
/* 3069 */     monType = getResourceTypeForPreConf(monType);
/* 3070 */     String resourceID = request.getParameter("resourceid");
/*      */     
/* 3072 */     Properties authResult = new Properties();
/* 3073 */     ActionErrors errors = new ActionErrors();
/* 3074 */     AMDCInf amdc = null;
/*      */     try
/*      */     {
/* 3077 */       authResult = getAuthResultAsPerResourceType(monType, request, false);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3081 */       e.printStackTrace();
/*      */     }
/* 3083 */     String result = authResult.getProperty("authentication");
/* 3084 */     if ("passed".equalsIgnoreCase(result))
/*      */     {
/* 3086 */       ActionMessages messages = new ActionMessages();
/* 3087 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.confmonitors.edit.success")));
/* 3088 */       saveMessages(request, messages);
/*      */     }
/*      */     else
/*      */     {
/* 3092 */       errormsg = authResult.getProperty("error");
/* 3093 */       errors.add("org.apache.struts.action.ERROR", new ActionError(errormsg));
/* 3094 */       saveErrors(request, errors);
/*      */     }
/* 3096 */     return errormsg;
/*      */   }
/*      */   
/*      */ 
/*      */   private HashMap populateMap()
/*      */   {
/* 3102 */     HashMap populateMap = new HashMap();
/* 3103 */     populateMap.put("MYSQLDB:3306", "MYSQL");
/* 3104 */     populateMap.put("SYBASEDB:5000", "SYBASE");
/* 3105 */     populateMap.put("DB2:50000", "DB2");
/* 3106 */     populateMap.put("MSSQLDB:1433", "MSSQL");
/* 3107 */     populateMap.put("JDK1.5:1099", "JDK1.5");
/* 3108 */     populateMap.put(".Net:9080", "DOTNET");
/* 3109 */     populateMap.put(".Net", "DOTNET");
/* 3110 */     return populateMap;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getResourceTypeForPreConf(String resourceType)
/*      */   {
/* 3119 */     WindowsClusterUtil.getInstance(); if (WindowsClusterUtil.getResourceTypeName().equalsIgnoreCase(resourceType))
/*      */     {
/* 3121 */       return resourceType;
/*      */     }
/*      */     
/* 3124 */     resourceType = resourceType.toLowerCase().indexOf("apache") != -1 ? "APACHE:80" : resourceType.toLowerCase().indexOf("freebsd") != -1 ? "FreeBSD" : resourceType.toLowerCase().indexOf("windows") != -1 ? "Windows" : resourceType.toLowerCase().indexOf("snmp") != -1 ? "SNMP:161" : resourceType.toLowerCase().indexOf("jdk") != -1 ? "JDK1.5:1099" : resourceType.toLowerCase().indexOf(".net") != -1 ? ".Net:9080" : resourceType.toLowerCase().indexOf("sybase") != -1 ? "SYBASEDB:5000" : resourceType.toLowerCase().indexOf("weblogic") != -1 ? "WEBLOGIC:7001" : resourceType.toLowerCase().indexOf("db2") != -1 ? "DB2:50000" : resourceType.toLowerCase().indexOf("mssql") != -1 ? "MSSQLDB:1433" : resourceType.toLowerCase().indexOf("mysql") != -1 ? "MYSQLDB:3306" : resourceType;
/* 3125 */     return resourceType;
/*      */   }
/*      */   
/*      */   private void setDisplayName(Properties credProps, String resourceID)
/*      */   {
/* 3130 */     String displayName = credProps.getProperty("displayname", credProps.getProperty("DISPLAYNAME", credProps.getProperty("displayName", "")));
/*      */     try
/*      */     {
/* 3133 */       String dispNameQuery = "update AM_ManagedObject set displayname='" + displayName + "' where resourceid=" + resourceID;
/* 3134 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(dispNameQuery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3138 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void setPort(Properties credProps, String resourceID)
/*      */   {
/* 3144 */     String port = credProps.getProperty("port", credProps.getProperty("PORT", credProps.getProperty("port", "")));
/*      */     try
/*      */     {
/* 3147 */       String updatePortQuery = "update CollectData set APPLNDISCPORT='" + port + "' where RESOURCENAME = (select RESOURCENAME from AM_ManagedObject where resourceid=" + resourceID + ")";
/* 3148 */       AMConnectionPool.executeUpdateStmt(updatePortQuery);
/* 3149 */       updatePortQuery = "update InetService set PORTNO='" + port + "' where NAME = (select RESOURCENAME from AM_ManagedObject where resourceid=" + resourceID + ")";
/* 3150 */       AMConnectionPool.executeUpdateStmt(updatePortQuery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3154 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setSNMPPort(Properties credProps, String resourceID)
/*      */   {
/* 3164 */     String snmpPort = credProps.getProperty("SNMPPORT");
/*      */     try
/*      */     {
/* 3167 */       String snmpPortUpdateQuery = "update AM_SNMP_EXT_INFO set SNMPPORT='" + snmpPort + "' where resourceid=" + resourceID;
/* 3168 */       AMConnectionPool.executeUpdateStmt(snmpPortUpdateQuery);
/* 3169 */       snmpPortUpdateQuery = "update InetService set PORTNO='" + snmpPort + "' where NAME = (select RESOURCENAME from AM_ManagedObject where resourceid=" + resourceID + ")";
/* 3170 */       AMConnectionPool.executeUpdateStmt(snmpPortUpdateQuery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3174 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private void setSSLEnabled(Properties credProps, String resourceID) {
/* 3179 */     String sslenabled = credProps.getProperty("sslenabled", credProps.getProperty("sslenabled", credProps.getProperty("sslenabled", "")));
/* 3180 */     if (sslenabled == null)
/*      */     {
/* 3182 */       sslenabled = "false";
/*      */     }
/*      */     
/* 3185 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3188 */       rs = AMConnectionPool.executeQueryStmt("select * from AM_JBOSS_AUTHINFO where resourceid=" + resourceID);
/* 3189 */       int count; int count; if (rs.next()) {
/* 3190 */         count = AMConnectionPool.executeUpdateStmt("update AM_JBOSS_AUTHINFO set sslenabled='" + sslenabled + "' where resourceid=" + resourceID);
/*      */       } else {
/* 3192 */         count = AMConnectionPool.executeUpdateStmt("insert into AM_JBOSS_AUTHINFO values(" + resourceID + ",'" + sslenabled + "')");
/*      */       }
/*      */       return;
/*      */     }
/*      */     catch (SQLException exc) {
/* 3197 */       exc.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 3201 */         if (rs != null) {
/* 3202 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */   private void setPollInterval(String resourceID, String pollInterval)
/*      */   {
/*      */     try
/*      */     {
/* 3213 */       pollInterval = pollInterval != null ? pollInterval : "5";
/* 3214 */       String updatePollIntervalQuery = "update ManagedObject set pollinterval=" + Integer.parseInt(pollInterval) * 60 + " where name=(select resourcename from AM_ManagedObject where resourceid=" + resourceID + ")";
/* 3215 */       AMConnectionPool.getInstance();AMConnectionPool.executeUpdateStmt(updatePollIntervalQuery);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3219 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPreConfMonitorPasswordFromDB(HttpServletRequest request)
/*      */   {
/* 3229 */     String monType = request.getParameter("montype");
/* 3230 */     String resourceID = request.getParameter("resourceid");
/* 3231 */     String resourceName = request.getParameter("resourcename");
/* 3232 */     String password = "";
/* 3233 */     ResultSet rs = null;
/* 3234 */     if ((monType == null) || (monType.equalsIgnoreCase("null")))
/*      */     {
/* 3236 */       monType = request.getParameter("type");
/*      */     }
/* 3238 */     monType = getResourceTypeForPreConf(monType);
/* 3239 */     if (this.resourceConfigMap.containsKey(monType))
/*      */     {
/*      */       try {
/* 3242 */         ResourceConfig getConfigurationsFromResourceConfig = (ResourceConfig)this.api.getCollectData(resourceName, (String)this.resourceConfigMap.get(monType));
/* 3243 */         password = getConfigurationsFromResourceConfig.getPassword();
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3247 */         ex.printStackTrace();
/*      */       }
/*      */     } else {
/* 3250 */       String query = "";
/* 3251 */       if (monType.toLowerCase().indexOf("weblogic") != -1)
/*      */       {
/* 3253 */         query = "select " + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD from AM_RESOURCECONFIG where RESOURCEID=" + resourceID;
/*      */       }
/* 3255 */       else if (monType.toLowerCase().indexOf("apache") != -1)
/*      */       {
/* 3257 */         query = "select " + DBQueryUtil.decode("PASSWORD") + " as PASSWORD from AM_APACHE_CONFIG where RESID=" + resourceID;
/*      */       }
/* 3259 */       else if (Constants.serverTypes.indexOf(monType) != -1)
/*      */       {
/* 3261 */         query = "select " + DBQueryUtil.decode("HostDetails.password") + "as PASSWORD from AM_ManagedObject left join HostDetails on AM_ManagedObject.resourcename=HostDetails.resourcename where AM_ManagedObject.resourceid=" + resourceID;
/*      */       }
/*      */       try {
/* 3264 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3265 */         if (rs.next())
/*      */         {
/* 3267 */           if (monType.toLowerCase().indexOf("weblogic") != -1) {
/* 3268 */             password = new String(rs.getBytes(1));
/*      */           } else {
/* 3270 */             password = rs.getString("PASSWORD");
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3276 */         AMLog.debug("Unable to get encoded value from DB for Monitortype :: " + monType + " with resourceId :: " + resourceID);
/* 3277 */         ex.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3281 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 3284 */     return password;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Properties getSNMPMonitorEncodedValsFromDB(HttpServletRequest request)
/*      */   {
/* 3292 */     String snmpVersion = request.getParameter("snmpVersionValue");
/* 3293 */     String resourceID = request.getParameter("resourceid");
/* 3294 */     Properties prop = new Properties();
/* 3295 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3298 */       if ((resourceID == null) || ("".equals(resourceID))) {
/* 3299 */         return prop;
/*      */       }
/* 3301 */       String query = "select " + DBQueryUtil.decode("AM_SNMP_EXT_INFO.COMMUNITYSTRING") + " as communityString,AM_SNMP_EXT_INFO.VERSION,AM_SNMP_EXT_INFO.SECURITYLEVEL," + DBQueryUtil.decode("AM_SNMP_EXT_INFO.AUTHPASSWORD") + " as AUTHPASSWORD," + DBQueryUtil.decode("AM_SNMP_EXT_INFO.PRIVPASSWORD") + " as PRIVPASSWORD from AM_SNMP_EXT_INFO where resourceid=" + resourceID;
/* 3302 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3303 */       if (rs.next())
/*      */       {
/* 3305 */         if (!"v3".equalsIgnoreCase(snmpVersion))
/*      */         {
/* 3307 */           prop.setProperty("snmpCommunityString", rs.getString("COMMUNITYSTRING"));
/*      */         }
/*      */         else
/*      */         {
/* 3311 */           String snmpSecurityLevel = request.getParameter("snmpSecurityLevel");
/* 3312 */           if (("AUTHPRIV".equalsIgnoreCase(snmpSecurityLevel)) || ("AUTHNOPRIV".equalsIgnoreCase(snmpSecurityLevel)))
/*      */           {
/* 3314 */             prop.setProperty("snmpAuthPassword", rs.getString("AUTHPASSWORD"));
/*      */           }
/* 3316 */           if ("AUTHPRIV".equalsIgnoreCase(snmpSecurityLevel))
/*      */           {
/* 3318 */             prop.setProperty("snmpPrivPassword", rs.getString("PRIVPASSWORD"));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3325 */       AMLog.debug("Unable to get encoded value from DB for SNMP Monitor with resourceId :: " + resourceID);
/* 3326 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3330 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 3332 */     return prop;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getConfMonitorPasswordFromDB(HttpServletRequest request, String encodedColumnName)
/*      */   {
/* 3341 */     NewMonitorConf newMonConf = new NewMonitorConf();
/* 3342 */     String monType = request.getParameter("montype");
/* 3343 */     if ((monType == null) || (monType.equalsIgnoreCase("null")))
/*      */     {
/* 3345 */       monType = request.getParameter("type");
/*      */     }
/* 3347 */     String resourceID = request.getParameter("resourceid");
/* 3348 */     String encodedVal = "";
/*      */     try {
/* 3350 */       if (!newMonConf.confMonPasswordMap.containsKey(resourceID)) {
/* 3351 */         newMonConf.getencodedColumnVals = NewMonitorUtil.getArgsasProps(monType, resourceID);
/* 3352 */         newMonConf.confMonPasswordMap.put(resourceID, newMonConf.getencodedColumnVals);
/*      */       }
/*      */       else {
/* 3355 */         newMonConf.getencodedColumnVals = ((Properties)newMonConf.confMonPasswordMap.get(resourceID));
/*      */       }
/* 3357 */       encodedVal = (String)newMonConf.getencodedColumnVals.get(encodedColumnName);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3361 */       AMLog.debug("Unable to get encoded value from DB for Monitortype :: " + monType + " with resourceId :: " + resourceID);
/* 3362 */       ex.printStackTrace();
/*      */     }
/* 3364 */     return encodedVal;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\NewMonitorConf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */