/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMPredefinedThresholdConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.AMScriptProcess;
/*      */ import com.adventnet.appmanager.server.framework.AM_KeyValueDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.credentialManager.CredentialManagerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMFileDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMScriptDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.ScheduleScriptDataCollection;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.WMIDataCollector;
/*      */ import com.adventnet.appmanager.server.wsm.util.FileUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.CLIPerformDataCollectionIfcImpl;
/*      */ import com.adventnet.appmanager.util.CLITelnetHandler;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FileDirUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportCustomAttributeUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.cli.CLISession;
/*      */ import com.adventnet.cli.transport.LoginException;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.adventnet.utilities.file.FileUtils;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.apache.struts.upload.FormFile;
/*      */ import org.htmlparser.util.Translate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CreateScriptMonitor
/*      */   extends DispatchAction
/*      */ {
/*   97 */   private ManagedApplication mo = new ManagedApplication();
/*   98 */   private String exit_command = "echo $?";
/*   99 */   private String exit_command1 = "echo $?1";
/*  100 */   WMIDataCollector wmi = new WMIDataCollector();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward createscript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws SQLException
/*      */   {
/*  108 */     String tablePresent = request.getParameter("tablespresent");
/*  109 */     if (tablePresent != null)
/*      */     {
/*  111 */       request.setAttribute("tablespresent", "true");
/*      */       
/*  113 */       Hashtable table_atts = new Hashtable();
/*  114 */       ArrayList tableids = new ArrayList();
/*      */       
/*  116 */       if ((tablePresent != null) && (tablePresent.equals("on")))
/*      */       {
/*  118 */         int table_count = Integer.parseInt(request.getParameter("table_row"));
/*      */         
/*  120 */         String tablename = "";
/*  121 */         String str_att_tab = "";
/*  122 */         String num_att_tab = "";
/*  123 */         String table_delimiter = "";
/*  124 */         String primary_index = "";
/*      */         
/*      */ 
/*  127 */         for (int k = 1; k <= table_count; k++)
/*      */         {
/*  129 */           String satts = "";
/*  130 */           String natts = "";
/*  131 */           Hashtable temp = new Hashtable();
/*  132 */           tablename = request.getParameter("table" + k).trim();
/*  133 */           str_att_tab = request.getParameter("stringatt" + k).trim();
/*  134 */           satts = satts.length() > 0 ? satts + "\n" + str_att_tab : str_att_tab;
/*  135 */           num_att_tab = request.getParameter("numericatt" + k).trim();
/*  136 */           natts = natts.length() > 0 ? natts + "\n" + num_att_tab : num_att_tab;
/*  137 */           table_delimiter = request.getParameter("cdl" + k).trim();
/*  138 */           if ((table_delimiter != null) && (table_delimiter.equals("\\t")))
/*      */           {
/*  140 */             table_delimiter = "t";
/*      */           }
/*  142 */           temp.put("name", tablename);
/*  143 */           temp.put("strings", satts.trim());
/*  144 */           temp.put("numerics", natts.trim());
/*  145 */           temp.put("delimiter", table_delimiter);
/*  146 */           primary_index = request.getParameter("pcatt" + k).trim();
/*  147 */           temp.put("primary", primary_index);
/*  148 */           table_atts.put(Integer.valueOf(10000 + k), temp);
/*  149 */           tableids.add(Integer.valueOf(10000 + k));
/*      */         }
/*      */         
/*  152 */         request.setAttribute("table_atts", table_atts);
/*  153 */         request.setAttribute("tableids", tableids);
/*      */       }
/*      */     }
/*      */     
/*  157 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  159 */       ActionErrors errors = new ActionErrors();
/*  160 */       ActionMessages messages = new ActionMessages();
/*  161 */       String haid = request.getParameter("haid");
/*  162 */       String resourcetype = ((AMActionForm)form).getType();
/*  163 */       Properties argsprops = new Properties();
/*  164 */       argsprops.setProperty("monitorType", resourcetype);
/*  165 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*  167 */         String param = (String)e.nextElement();
/*  168 */         if (!argsprops.containsKey(param))
/*      */         {
/*  170 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*  172 */         if (param.equals("haid"))
/*      */         {
/*  174 */           String[] multiVal = request.getParameterValues(param);
/*  175 */           if ((multiVal != null) && (multiVal.length > 0)) {
/*  176 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/*  177 */             val = val.substring(1, val.length() - 1);
/*  178 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  184 */         getRemoteHostDetails(argsprops);
/*  185 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/*  186 */         ArrayList<String> al1 = new ArrayList();
/*  187 */         String displayname = request.getParameter("displayname");
/*  188 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/*  189 */           displayname = request.getParameter("displayName");
/*      */         }
/*  191 */         String status = "Success";
/*  192 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/*  193 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/*  194 */         al1.add(displayname);
/*  195 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/*  196 */           status = "Failed";
/*  197 */           message = (String)responseMap.get("message");
/*      */         }
/*  199 */         al1.add(status);
/*  200 */         al1.add(message);
/*  201 */         al1.add(masDisplayName);
/*  202 */         request.setAttribute("discoverystatus", al1);
/*  203 */         request.setAttribute("type", resourcetype);
/*  204 */         request.setAttribute("basetype", "Script Monitor");
/*      */         
/*  206 */         if (((String)responseMap.get("addStatus")).equals("true")) {
/*  207 */           addScriptHostDetailsInAAM(argsprops);
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
/*  227 */         e.printStackTrace();
/*      */       }
/*  229 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid);
/*      */     }
/*      */     
/*  232 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  233 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*      */     
/*  235 */     boolean ret = DataCollectionControllerUtil.isallowed();
/*  236 */     if (!ret)
/*      */     {
/*  238 */       return new ActionForward("/showresource.do?method=showResourceTypes&fromwhere=unabletocreate");
/*      */     }
/*  240 */     String customType = request.getParameter("customType");
/*  241 */     int baseid = -1;
/*  242 */     String basetype = "Script Monitor";
/*  243 */     System.out.println("The customType===>" + customType);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  248 */     PreparedStatement ps1 = null;
/*      */     try
/*      */     {
/*  251 */       String wiz = "";
/*  252 */       if (request.getParameter("wiz") != null)
/*      */       {
/*  254 */         wiz = "&wiz=true";
/*      */       }
/*  256 */       configuredList = new Hashtable();
/*  257 */       int resourceid = -1;
/*  258 */       String filepath = null;
/*  259 */       File dir = null;
/*  260 */       String[] file_checkname = new String[2];
/*  261 */       ArrayList al1 = new ArrayList();
/*  262 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  263 */       String displayname = ((AMActionForm)form).getDisplayname();
/*  264 */       boolean isCredManager = ((AMActionForm)form).getIsCredManager();
/*  265 */       Long credentialID = Long.valueOf(0L);
/*  266 */       if (isCredManager) {
/*  267 */         credentialID = Long.valueOf(new Long(((AMActionForm)form).getCredentialID()).longValue());
/*      */       }
/*  269 */       FormFile file = ((AMActionForm)form).getTheFile();
/*  270 */       String arguements = ((AMActionForm)form).getMessage().trim();
/*  271 */       int poll_interval = ((AMActionForm)form).getPollInterval();
/*  272 */       int pollinginteval = poll_interval * 60;
/*  273 */       int timeout = ((AMActionForm)form).getTimeout();
/*  274 */       String string_att = ((AMActionForm)form).getString_att().trim();
/*  275 */       String numeric_att = ((AMActionForm)form).getNumeric_att().trim();
/*  276 */       ArrayList stringattributes = getAttributes(string_att);
/*  277 */       boolean isCommand = ((AMActionForm)form).getisCommand();
/*  278 */       boolean isFile = false;
/*  279 */       if (request.getParameter("isFile") != null) {
/*  280 */         isFile = true;
/*      */       }
/*  282 */       ArrayList numericattributes = getAttributes(numeric_att);
/*  283 */       boolean successful = false;
/*  284 */       String delimiter = ((AMActionForm)form).getDelimiter();
/*      */       
/*  286 */       if ((((AMActionForm)form).getType() != null) && (((AMActionForm)form).getType().equals("SYSTEM:9999")))
/*      */       {
/*  288 */         ((AMActionForm)form).setType(request.getParameter("monitorType"));
/*      */       }
/*  290 */       if ((customType != null) && (customType.equals("true")))
/*      */       {
/*      */         try
/*      */         {
/*  294 */           String qry1 = "select RESOURCETYPEID from AM_ManagedResourceType where RESOURCETYPE='" + ((AMActionForm)form).getType() + "'";
/*  295 */           ResultSet rs = AMConnectionPool.executeQueryStmt(qry1);
/*  296 */           if (rs.next())
/*      */           {
/*  298 */             baseid = rs.getInt(1);
/*      */           }
/*  300 */           rs.close();
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  304 */           exc.printStackTrace();
/*      */         }
/*      */         try
/*      */         {
/*  308 */           delimiter = NewMonitorUtil.getDelimiterforType(baseid);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  312 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  316 */       System.out.println("The baseid========>" + baseid);
/*      */       
/*  318 */       String outputfile = null;
/*  319 */       if (!isFile) {
/*  320 */         outputfile = "-1";
/*      */       } else {
/*  322 */         outputfile = ((AMActionForm)form).getOutputfile();
/*      */       }
/*  324 */       String haid = ((AMActionForm)form).getHaid();
/*  325 */       String command = null;
/*  326 */       String filename = null;
/*  327 */       ArrayList parsed_args = new AMScriptDataCollector().parseArg(arguements);
/*  328 */       String exec_dir = null;
/*  329 */       String serverpath = null;
/*  330 */       String serversite = "1";
/*  331 */       String taskatlocal = "loacl";
/*  332 */       if (((AMActionForm)form).getServersite() != null)
/*      */       {
/*  334 */         taskatlocal = ((AMActionForm)form).getServersite();
/*      */       }
/*  336 */       if ((delimiter == null) || (delimiter.trim().equals(""))) {
/*  337 */         delimiter = "";
/*      */       }
/*  339 */       String resourcetype = ((AMActionForm)form).getType();
/*  340 */       System.out.println("The resourcetype 1111111111111====>" + resourcetype);
/*  341 */       exec_dir = ((AMActionForm)form).getWorkingdirectory();
/*  342 */       serverpath = ((AMActionForm)form).getServerpath();
/*  343 */       String mode = "";
/*  344 */       if (taskatlocal.equals("remote"))
/*      */       {
/*  346 */         mode = ((AMActionForm)form).getMode();
/*      */ 
/*      */ 
/*      */       }
/*  350 */       else if ((!System.getProperty("os.name").startsWith("windows")) && (!System.getProperty("os.name").startsWith("Windows")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  356 */         mode = ((AMActionForm)form).getMode();
/*      */       }
/*      */       
/*      */ 
/*  360 */       if (isCommand) {
/*  361 */         mode = "-1";
/*      */       }
/*  363 */       String addtoapplication = request.getParameter("addtoha");
/*  364 */       String haidname = request.getParameter("haid");
/*  365 */       request.setAttribute("type", resourcetype);
/*  366 */       request.setAttribute("basetype", "Script Monitor");
/*  367 */       String choosehost = "-1";
/*  368 */       if (((AMActionForm)form).getChoosehost() != null)
/*      */       {
/*  370 */         choosehost = ((AMActionForm)form).getChoosehost().trim();
/*  371 */         if ((choosehost != null) && (choosehost.contains(","))) {
/*  372 */           StringTokenizer st = new StringTokenizer(choosehost);
/*  373 */           while (st.hasMoreTokens()) {
/*  374 */             choosehost = st.nextToken(",");
/*      */           }
/*  376 */           ((AMActionForm)form).setChoosehost(choosehost);
/*      */         }
/*      */       }
/*  379 */       if (taskatlocal.equals("remote"))
/*      */       {
/*  381 */         serversite = "2";
/*      */       }
/*  383 */       ArrayList hostdetailsarray = new ArrayList();
/*  384 */       Hashtable output = new Hashtable();
/*  385 */       String shell = "sh";
/*  386 */       shell = mode;
/*  387 */       if ((serverpath != null) && (serverpath.indexOf("\\") != -1))
/*  388 */         shell = "windows";
/*  389 */       this.exit_command = Constants.getExitCommand_Script(shell);
/*  390 */       this.exit_command1 = (this.exit_command + "1");
/*  391 */       if (serversite.equals("2"))
/*      */       {
/*  393 */         output = doCheckforRemote(form, serversite, hostdetailsarray, timeout);
/*      */       }
/*  395 */       ArrayList num_str1 = new ArrayList();
/*  396 */       StringBuffer tempexppecteddef_wohash; ArrayList num; if (resourcetype.equals("QENGINE"))
/*      */       {
/*      */ 
/*      */ 
/*  400 */         String qenginehome = getQEngineHome(arguements);
/*  401 */         exec_dir = qenginehome + File.separator + "bin";
/*  402 */         serverpath = exec_dir + File.separator + "CommandLineWebPerfTest.bat";
/*  403 */         if ((System.getProperty("os.name").startsWith("windows")) || (System.getProperty("os.name").startsWith("Windows")))
/*      */         {
/*  405 */           serverpath = exec_dir + File.separator + "CommandLineWebPerfTest.bat";
/*      */         }
/*      */         else
/*      */         {
/*  409 */           serverpath = exec_dir + File.separator + "CommandLineWebPerfTest.sh";
/*      */         }
/*  411 */         File argumentfile = new File(arguements);
/*  412 */         if (!argumentfile.exists())
/*      */         {
/*  414 */           log.fatal("Test file doesnot exist");
/*  415 */           al1.add(arguements);
/*  416 */           al1.add("Failed");
/*  417 */           al1.add(FormatUtil.getString("am.webclient.discovery.qengine.noscriptfile.text"));
/*  418 */           request.setAttribute("discoverystatus", al1);
/*  419 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=QENGINE" + wiz + "&haid=" + haid);
/*      */         }
/*      */         
/*  422 */         ((AMActionForm)form).setWorkingdirectory(exec_dir);
/*  423 */         ((AMActionForm)form).setServerpath(serverpath);
/*  424 */         outputfile = getProjectHome(arguements) + File.separator + "webperflogs" + File.separator + getTestCaseName(arguements) + "_report.txt";
/*  425 */         ((AMActionForm)form).setOutputfile(outputfile);
/*      */         
/*  427 */         log.info("Output file " + outputfile);
/*  428 */         log.info("serverpath " + serverpath);
/*  429 */         log.info("execdir " + exec_dir);
/*      */         
/*      */ 
/*      */ 
/*  433 */         File typeprop = new File(getProjectHome(arguements) + File.separator + "webperflogs" + File.separator + "type" + File.separator + getTestCaseName(arguements) + ".prop");
/*  434 */         if (!typeprop.exists())
/*      */         {
/*  436 */           log.fatal("type.prop file does not exists for testcase" + getTestCaseName(arguements));
/*  437 */           al1.add(arguements);
/*  438 */           al1.add("Failed");
/*  439 */           al1.add(FormatUtil.getString("am.webclient.discovery.qengine.nodescfile.text", new String[] { getScriptDirectory(arguements) }));
/*  440 */           request.setAttribute("discoverystatus", al1);
/*  441 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=QENGINE" + wiz + "&haid=" + haid);
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  446 */           String tempexpecteddef = FileUtils.getFileContents(typeprop);
/*  447 */           log.debug("Contents in type.prop " + tempexpecteddef);
/*  448 */           tempexppecteddef_wohash = new StringBuffer();
/*  449 */           StringTokenizer tokens = new StringTokenizer(tempexpecteddef, "\n");
/*  450 */           num = new ArrayList();
/*  451 */           ArrayList stri = new ArrayList();
/*  452 */           while (tokens.hasMoreElements())
/*      */           {
/*  454 */             String line = tokens.nextToken();
/*  455 */             if (!line.startsWith("#"))
/*      */             {
/*      */ 
/*      */               try
/*      */               {
/*      */ 
/*  461 */                 StringTokenizer st_eq = new StringTokenizer(line, "=");
/*  462 */                 String name = st_eq.nextToken();
/*  463 */                 String type = st_eq.nextToken();
/*  464 */                 if (type.indexOf("umer") != -1)
/*      */                 {
/*  466 */                   num.add(name);
/*      */                 }
/*      */                 else
/*      */                 {
/*  470 */                   stri.add(name);
/*      */                 }
/*      */               }
/*      */               catch (Exception exc) {}
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  482 */           num_str1.add(stri);
/*  483 */           num_str1.add(num);
/*      */ 
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/*      */ 
/*  491 */           log.info("Unhandled exception ", e);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  496 */       File f1 = new File(serverpath).getAbsoluteFile();
/*  497 */       File f2 = new File(exec_dir).getAbsoluteFile();
/*  498 */       al1.add(serverpath);
/*  499 */       boolean bo = true;
/*      */       
/*  501 */       String haid_str = "";
/*  502 */       if (request.getParameter("haid") != null)
/*      */       {
/*  504 */         haid_str = "&haid=" + request.getParameter("haid");
/*      */       }
/*  506 */       if (!bo)
/*      */       {
/*  508 */         al1.add("Failed");
/*  509 */         al1.add(FormatUtil.getString("am.webclient.script.improperexpectedrsults"));
/*  510 */         request.setAttribute("discoverystatus", al1);
/*  511 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */       }
/*      */       
/*  514 */       if (serversite.equals("2"))
/*      */       {
/*  516 */         if (output.get("failure") != null)
/*      */         {
/*  518 */           String error_msg = "am.webclient.script.loginexception.text";
/*  519 */           boolean issuccess = true;
/*  520 */           if (output.get("failure").equals("Login Exception"))
/*      */           {
/*  522 */             issuccess = false;
/*      */           }
/*  524 */           else if (output.get("failure").equals("ConnectException"))
/*      */           {
/*  526 */             issuccess = false;
/*  527 */             error_msg = "am.webclient.connectexception.text";
/*      */           }
/*  529 */           if (!issuccess)
/*      */           {
/*  531 */             al1.add("Failed");
/*  532 */             al1.add(FormatUtil.getString(error_msg));
/*  533 */             request.setAttribute("discoverystatus", al1);
/*  534 */             return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */           }
/*      */         }
/*      */         int code;
/*  538 */         if ((!String.valueOf(output.get(this.exit_command)).trim().startsWith("0")) && (isCommand)) {
/*  539 */           AMLog.debug("Exit command from RemoteServer is : " + String.valueOf(output.get(this.exit_command)));
/*  540 */           code = 0;
/*  541 */           String exitcode; if (output.get(this.exit_command).toString().indexOf("\n") > 0) {
/*  542 */             String[] exitcode = output.get(this.exit_command).toString().split("\n");
/*  543 */             code = Integer.parseInt(exitcode[0].trim());
/*      */           }
/*      */           else {
/*  546 */             exitcode = output.get(this.exit_command).toString();
/*  547 */             code = Integer.parseInt(exitcode.trim());
/*      */           }
/*      */           
/*  550 */           AMLog.debug("Exit code is : " + code + "  Command to execute in remote server : " + serverpath + "  OS name is : " + System.getProperty("os.name"));
/*  551 */           al1.add("Failed");
/*  552 */           al1.add(errorHandling(code, serverpath));
/*  553 */           request.setAttribute("discoverystatus", al1);
/*  554 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*  556 */         if (!String.valueOf(output.get(this.exit_command)).trim().startsWith("0"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  564 */           al1.add("Failed");
/*  565 */           al1.add(FormatUtil.getString("am.webclient.script.noscript"));
/*  566 */           request.setAttribute("discoverystatus", al1);
/*  567 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*      */         
/*  570 */         if ((!String.valueOf(output.get(this.exit_command1)).trim().startsWith("0")) && ((!isCommand) || (!f2.isDirectory())))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  578 */           al1.add("Failed");
/*  579 */           al1.add(FormatUtil.getString("am.webclient.script.improperdirectory"));
/*  580 */           request.setAttribute("discoverystatus", al1);
/*  581 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         try
/*      */         {
/*  588 */           if (isCommand) {
/*  589 */             long[] collection_exit = new long[2];
/*  590 */             collection_exit = new AMScriptDataCollector().runCommand("", serverpath, parsed_args, exec_dir, timeout, true, resourceid, false, resourcetype);
/*  591 */             exitvalue = (int)collection_exit[1];
/*  592 */             AMLog.debug("Exit code is : " + exitvalue + "  Command is : " + serverpath + "  OS name is : " + System.getProperty("os.name"));
/*  593 */             if (exitvalue != 0) {
/*  594 */               al1.add("Failed");
/*  595 */               al1.add(errorHandling(exitvalue, serverpath));
/*  596 */               request.setAttribute("discoverystatus", al1);
/*  597 */               return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */             }
/*      */           }
/*      */         } catch (Exception exc) {
/*      */           int exitvalue;
/*  602 */           al1.add("Failed");
/*  603 */           al1.add(FormatUtil.getString("am.webclient.command.nocommand.notfound"));
/*  604 */           request.setAttribute("discoverystatus", al1);
/*  605 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*  607 */         if ((!f1.exists()) && (!isCommand))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  613 */           al1.add("Failed");
/*  614 */           al1.add(FormatUtil.getString("am.webclient.script.noscript"));
/*  615 */           request.setAttribute("discoverystatus", al1);
/*  616 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*  618 */         if ((!f2.exists()) || (!f2.isDirectory()))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  624 */           al1.add("Failed");
/*  625 */           al1.add(FormatUtil.getString("am.webclient.script.improperdirectory"));
/*  626 */           request.setAttribute("discoverystatus", al1);
/*  627 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*      */       }
/*      */       
/*  631 */       int scriptcheck = 1;
/*  632 */       String[] filename_path = new String[2];
/*  633 */       if (isCommand) {
/*  634 */         filename_path = new String[] { "", serverpath };
/*  635 */         filename = serverpath;
/*      */       } else {
/*  637 */         filename_path = getFilename(serverpath, serversite);
/*  638 */         filename = filename_path[1];
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  644 */       scriptcheck = 4;
/*  645 */       filepath = ".." + File.separator + "scripts" + File.separator + file_checkname[0] + "_dir" + File.separator;
/*  646 */       if ((System.getProperty("os.name").equalsIgnoreCase("windows")) || (isCommand))
/*      */       {
/*  648 */         command = filename;
/*      */       }
/*      */       else
/*      */       {
/*  652 */         command = mode + " " + filename;
/*      */       }
/*      */       String argumentswithdoublequotes;
/*  655 */       if (scriptcheck == 4)
/*      */       {
/*  657 */         String mon_type = ((AMActionForm)form).getType();
/*  658 */         if ((mon_type != null) && (mon_type.trim().equals("SYSTEM:9999")))
/*      */         {
/*  660 */           mon_type = request.getParameter("monitorType");
/*      */         }
/*  662 */         resourceid = NewMonitorUtil.mocreate(displayname, filename, mon_type);
/*  663 */         if ((mon_type != null) && (mon_type.equals("Script Monitor")))
/*      */         {
/*      */           try
/*      */           {
/*      */ 
/*  668 */             AMPredefinedThresholdConfiguration.updateThreshold(resourceid);
/*  669 */             AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/*  670 */             String rulesmapperentry = "insert into AM_RCARULESMAPPER values (" + resourceid + ",2201,1)";
/*  671 */             toinsert.addBatch(rulesmapperentry);
/*  672 */             if (FaultUtil.isRCAEnabled())
/*      */             {
/*  674 */               String rcamapperentry_avail = "insert into AM_RCAMAPPER values (" + resourceid + ",2201," + resourceid + ",2200)";
/*  675 */               String rcamapperentry_health = "insert into AM_RCAMAPPER values (" + resourceid + ",2201," + resourceid + ",2202)";
/*  676 */               toinsert.addBatch(rcamapperentry_avail);
/*  677 */               toinsert.addBatch(rcamapperentry_health);
/*      */             }
/*  679 */             toinsert.executeBatch();
/*  680 */             toinsert.clearBatch();
/*  681 */             toinsert.close();
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/*  685 */             exc.printStackTrace();
/*      */           }
/*      */         }
/*  688 */         if ((delimiter == null) || (delimiter.trim().equals("")))
/*      */         {
/*  690 */           delimiter = "s";
/*      */         }
/*  692 */         argumentswithdoublequotes = arguements;
/*      */         
/*  694 */         if (resourcetype.equals("QENGINE"))
/*      */         {
/*  696 */           argumentswithdoublequotes = "\"" + arguements + "\"";
/*  697 */           Properties props = new Properties();
/*  698 */           ArrayList idList = new ArrayList();
/*  699 */           idList.add("4300");
/*  700 */           idList.add("4301");
/*  701 */           idList.add("4303");
/*  702 */           NewMonitorUtil.insertAttributes(resourceid, num_str1, ((AMActionForm)form).getType(), "", props, "NO", idList, null, null, "AM_MinMaxAvgData");
/*      */         }
/*  704 */         System.out.println("The arguements --->" + arguements);
/*  705 */         PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_ScriptArgs values (?,?,?,?,?,?,?,?,?,?,?,?)");
/*  706 */         ps.setInt(1, resourceid);
/*  707 */         ps.setString(2, filename);
/*  708 */         ps.setString(3, arguements);
/*  709 */         ps.setString(4, outputfile);
/*  710 */         ps.setString(5, delimiter);
/*  711 */         ps.setInt(6, timeout);
/*  712 */         ps.setString(7, mode);
/*  713 */         ps.setString(8, displayname);
/*  714 */         ps.setInt(9, pollinginteval);
/*  715 */         ps.setString(10, serversite);
/*  716 */         ps.setString(11, exec_dir);
/*  717 */         ps.setString(12, serverpath);
/*      */         
/*  719 */         ArrayList num_str = new ArrayList();
/*  720 */         num_str.add(stringattributes);
/*  721 */         num_str.add(numericattributes);
/*      */         try
/*      */         {
/*  724 */           ps.execute();
/*      */           
/*  726 */           if (serversite.equals("2"))
/*      */           {
/*  728 */             String host_id = "-10";
/*  729 */             if ((choosehost != null) && (choosehost.equals("-1")))
/*      */             {
/*      */ 
/*  732 */               String usrname = (String)hostdetailsarray.get(1);
/*  733 */               int count = -1;
/*  734 */               int tableid = NewMonitorUtil.getNextTableId("AM_SCRIPTHOSTDETAILS", "ID");
/*      */               
/*  736 */               ps1 = AMConnectionPool.getConnection().prepareStatement("insert into AM_SCRIPTHOSTDETAILS(ID,HOSTNAME,USERNAME,PASSWORD,MODE,PROMPT,PORT) values(" + tableid + ",'" + hostdetailsarray.get(0) + "',?," + DBQueryUtil.encode(hostdetailsarray.get(2).toString()) + ",'" + hostdetailsarray.get(3) + "','" + hostdetailsarray.get(4) + "'," + hostdetailsarray.get(5) + ")");
/*  737 */               ps1.setString(1, usrname);
/*  738 */               ps1.executeUpdate();
/*  739 */               ResultSet rs = null;
/*      */               try
/*      */               {
/*  742 */                 if ((usrname.indexOf('\\') != -1) && (!DBQueryUtil.getDBType().equals("mssql")) && (!DBQueryUtil.getDBType().equals("pgsql")))
/*      */                 {
/*  744 */                   usrname = StrUtil.findReplace(usrname, "\\", "\\\\");
/*      */                 }
/*  746 */                 rs = AMConnectionPool.executeQueryStmt("select ID from AM_SCRIPTHOSTDETAILS where HOSTNAME ='" + hostdetailsarray.get(0) + "' and MODE in ('TELNET','SSH','SSH_KEY') and USERNAME='" + usrname + "' UNION select RESOURCEID from  AM_ManagedObject where RESOURCENAME='" + hostdetailsarray.get(0) + "'");
/*  747 */                 if (rs.next())
/*      */                 {
/*  749 */                   host_id = rs.getString("ID");
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/*  754 */                 exc.printStackTrace();
/*      */               }
/*      */               finally {
/*  757 */                 rs.close();
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/*  762 */               host_id = ((AMActionForm)form).getChoosehost();
/*      */             }
/*      */             
/*  765 */             AMConnectionPool.executeUpdateStmt("insert into AM_SCRIPTHOST_MAPPER values(" + resourceid + "," + host_id + ")");
/*  766 */             DBUtil.insertParentChildMapper(Integer.parseInt(host_id), resourceid);
/*  767 */             if (isCredManager) {
/*  768 */               CredentialManagerUtil credUtil = new CredentialManagerUtil();
/*  769 */               credUtil.addToCredentialToResourceMap(credentialID.longValue(), resourceid);
/*      */             }
/*      */           }
/*      */           
/*  773 */           if ((customType != null) && (customType.equals("true")))
/*      */           {
/*      */ 
/*      */ 
/*  777 */             AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*  778 */             adder.addInterDependentAttributes(resourceid);
/*      */             
/*      */             try
/*      */             {
/*  782 */               String qry = "select RESOURCEGROUP from AM_ManagedResourceType  where resourcetype='" + ((AMActionForm)form).getType() + "'";
/*  783 */               ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  784 */               if (rs.next())
/*      */               {
/*  786 */                 String group = rs.getString(1);
/*  787 */                 if (group != null)
/*      */                 {
/*  789 */                   qry = "select resourceid from AM_ManagedObject where type='" + group + "'";
/*  790 */                   ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/*  791 */                   if (rs1.next())
/*      */                   {
/*  793 */                     int resid = rs1.getInt(1);
/*  794 */                     adder.addDependentAttributes(resid, resourceid);
/*      */                   }
/*  796 */                   rs1.close();
/*  797 */                   rs.close();
/*      */                 }
/*      */                 
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             catch (Exception exc) {}
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  810 */             Properties props = new Properties();
/*  811 */             ArrayList idList = new ArrayList();
/*  812 */             idList.add("2200");
/*  813 */             idList.add("2201");
/*  814 */             idList.add("2203");
/*  815 */             int healthid = NewMonitorUtil.insertAttributes(resourceid, num_str, ((AMActionForm)form).getType(), "", props, "NO", idList, null, null, "AM_MinMaxAvgData");
/*  816 */             manageTableAtts(request, resourceid, healthid, "", "Script Monitor", "AM_MinMaxAvgData");
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
/*      */           try
/*      */           {
/*  832 */             ps.close();
/*      */           }
/*      */           catch (Exception exc) {}
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  839 */           successful = true;
/*      */         }
/*      */         catch (SQLException sqle4)
/*      */         {
/*  822 */           sqle4.printStackTrace();
/*      */         }
/*      */         catch (Exception e4)
/*      */         {
/*  826 */           e4.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  840 */         al1.add("Success");
/*  841 */         request.setAttribute("resourceid", Integer.valueOf(resourceid));
/*      */         
/*  843 */         al1.add("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/*      */       }
/*  845 */       else if (scriptcheck == 5)
/*      */       {
/*  847 */         al1.add("Failed");
/*  848 */         al1.add(FormatUtil.getString("am.webclient.script.monitorexists"));
/*  849 */         request.setAttribute("discoverystatus", al1);
/*  850 */         String[] resourceids; if (request.getParameter("wiz") != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  856 */           resourceids = new String[] { String.valueOf(resourceid) };
/*      */           
/*      */ 
/*  859 */           new ManagedApplication().updateManagedApplicationResources(haid, "junk", resourceids, "100");
/*      */           
/*      */ 
/*  862 */           request.setAttribute("type", ((AMActionForm)form).getType());
/*      */           
/*  864 */           request.setAttribute("basetype", "Script Monitor");
/*  865 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "" + wiz + "&haid=" + haid);
/*      */         }
/*  867 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + ((AMActionForm)form).getType() + "&haid=" + haid);
/*      */       }
/*      */       
/*      */ 
/*  871 */       request.setAttribute("discoverystatus", al1);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  882 */       String resourcename = "";
/*  883 */       String type = "Script Monitor";
/*  884 */       String[] selMonitorGroups; if ((successful) && (addtoapplication != null))
/*      */       {
/*  886 */         if ((resourceid != -1) && 
/*  887 */           (ClientDBUtil.isPrivilegedUser(request))) {
/*  888 */           RestrictedUsersViewUtil.insertIntoAMUserResourcesTableandsynchtoAAM(request.getRemoteUser(), Long.valueOf(resourceid).longValue());
/*      */         }
/*      */         
/*  891 */         addtoapplication = request.getParameter("addtoha");
/*  892 */         if (addtoapplication.equals("true"))
/*      */         {
/*  894 */           String[] resources = { String.valueOf(resourceid) };
/*  895 */           Vector forUpdate = new Vector();
/*  896 */           selMonitorGroups = request.getParameterValues("haid");
/*  897 */           this.mo.updateManagedApplicationResources(selMonitorGroups, "xyz", resources, null, forUpdate);
/*  898 */           if (forUpdate != null)
/*      */           {
/*  900 */             for (int i = 0; i < forUpdate.size(); i++)
/*      */             {
/*  902 */               EnterpriseUtil.addUpdateQueryToFile(String.valueOf(forUpdate.get(i)));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  907 */       System.out.println("Calling datacollection process... for script for customType====>");
/*      */       try
/*      */       {
/*  910 */         AM_KeyValueDataCollector amkv = null;
/*  911 */         if ((customType != null) && (customType.equals("true")))
/*      */         {
/*  913 */           System.out.println("The form type===>" + ((AMActionForm)form).getType());
/*  914 */           Properties p = DataCollectionControllerUtil.getAttributeProps(((AMActionForm)form).getType());
/*  915 */           System.out.println("The newly created custom monitor type====>" + type);
/*      */           
/*      */ 
/*  918 */           amkv = new AM_KeyValueDataCollector(String.valueOf(resourceid), resourcename, resourcetype, basetype, baseid, p);
/*      */         }
/*      */         else
/*      */         {
/*  922 */           amkv = new AM_KeyValueDataCollector(String.valueOf(resourceid), resourcename, ((AMActionForm)form).getType());
/*      */         }
/*      */         
/*  925 */         Scheduler.getScheduler("KeyValue_Monitor").scheduleTask(amkv, System.currentTimeMillis());
/*      */         
/*  927 */         System.out.println("Entry made in the datacollector hashtable...");
/*  928 */         AMScriptProcess.resid_instance.put(String.valueOf(resourceid), amkv);
/*  929 */         log.info("Triggering completed");
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*  934 */         exc.printStackTrace();
/*      */       }
/*  936 */       String wiz1 = request.getParameter("wiz");
/*  937 */       if (wiz1 != null)
/*      */       {
/*      */ 
/*  940 */         if (resourcetype.equals("QENGINE"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  946 */           String[] resourceids = { String.valueOf(resourceid) };
/*  947 */           new ManagedApplication().updateManagedApplicationResources(haid, "junk", resourceids, "100");
/*  948 */           request.setAttribute("type", ((AMActionForm)form).getType());
/*  949 */           return new ActionForward(mapping.findForward("HAProfiles").getPath() + "&haid=" + haid + wiz);
/*      */         }
/*  951 */         String value = request.getParameter("value");
/*  952 */         request.setAttribute("discoverystatus", al1);
/*  953 */         if (value == null)
/*      */         {
/*      */ 
/*      */ 
/*  957 */           return new ActionForward("/showresource.do?method=associateMonitors" + wiz + haid_str);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  963 */         return new ActionForward("/showActionProfiles.do?method=getHAProfiles" + wiz + haid_str);
/*      */       }
/*      */       
/*      */ 
/*  967 */       String path = "/showresource.do";
/*  968 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + ((AMActionForm)form).getType() + "&haid=" + haid + wiz);
/*      */     }
/*      */     catch (Exception e) {
/*      */       Hashtable configuredList;
/*  972 */       e.printStackTrace();
/*  973 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  979 */         ps1.close();
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
/*      */   public ActionForward disableRowByUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  995 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  996 */     String resourceid = request.getParameter("resourceid");
/*  997 */     if (request.getParameter("tabId") != null) {
/*  998 */       resourceid = request.getParameter("rowid");
/*      */     }
/* 1000 */     String tableid = request.getParameter("tableid") != null ? request.getParameter("tableid") : "";
/* 1001 */     String scriptid = request.getParameter("scriptid");
/* 1002 */     StringTokenizer str = new StringTokenizer(resourceid, ",");
/* 1003 */     String type = request.getParameter("type") != null ? request.getParameter("type") : "";
/* 1004 */     boolean isamCreated = false;
/* 1005 */     if (ConfMonitorConfiguration.getInstance().isConfMonitor(type)) {
/* 1006 */       isamCreated = true;
/*      */     }
/* 1008 */     ArrayList attids = new ArrayList();
/* 1009 */     ArrayList dtables = new ArrayList();
/* 1010 */     ArrayList types = new ArrayList();
/*      */     try
/*      */     {
/* 1013 */       if (!isamCreated) {
/* 1014 */         String pCol = null;
/* 1015 */         String qry = "select PRIMARY_COLUMN from AM_SCRIPT_TABLES WHERE TABLEID=" + tableid;
/* 1016 */         ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1017 */         if (rs.next()) {
/* 1018 */           pCol = rs.getString(1);
/* 1019 */           StringTokenizer sttok = new StringTokenizer(pCol, "#");
/* 1020 */           while (sttok.hasMoreElements()) {
/* 1021 */             qry = "select AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID,DATATABLE,TYPE FROM AM_CAM_DC_ATTRIBUTES,AM_ATTRIBUTES_EXT WHERE GROUPID=" + tableid + " AND ATTRIBUTENAME='" + sttok.nextToken() + "' and AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ATTRIBUTES_EXT.ATTRIBUTEID";
/* 1022 */             ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 1023 */             if (rs1.next()) {
/* 1024 */               attids.add(rs1.getString(1));
/* 1025 */               dtables.add(rs1.getString(2));
/* 1026 */               types.add(rs1.getString(3));
/*      */             }
/* 1028 */             closeResultSet(rs1);
/*      */           }
/*      */         }
/* 1031 */         closeResultSet(rs);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1036 */       exc.printStackTrace();
/*      */     }
/* 1038 */     while (str.hasMoreTokens())
/*      */     {
/* 1040 */       StringBuffer sb = new StringBuffer("");
/*      */       try
/*      */       {
/* 1043 */         String rowid = str.nextToken();
/* 1044 */         String value = "";
/*      */         
/* 1046 */         FaultUtil.deleteAlertsForResource(rowid);
/* 1047 */         if (!isamCreated) {
/* 1048 */           AMConnectionPool.executeUpdateStmt("update AM_ManagedObject set DCSTARTED=5 where RESOURCEID=" + rowid);
/* 1049 */           AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + scriptid + " and CHILDRESOURCEID=" + rowid);
/*      */           
/* 1051 */           for (int i = 0; i < attids.size(); i++) {
/* 1052 */             String rowidcol = "RESID";
/* 1053 */             if (types.get(i).equals("0")) {
/* 1054 */               rowidcol = "RESID";
/*      */             }
/*      */             else {
/* 1057 */               rowidcol = "ROWID";
/*      */             }
/* 1059 */             String qry = "select value from " + dtables.get(i) + " where attributeid=" + attids.get(i) + " and " + rowidcol + "=" + rowid + " order by collectiontime desc";
/* 1060 */             qry = DBQueryUtil.getTopNValues(qry, 1);
/* 1061 */             ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1062 */             if (rs.next()) {
/* 1063 */               value = rs.getString(1);
/*      */             }
/* 1065 */             sb.append(attids.get(i));
/* 1066 */             sb.append("$");
/* 1067 */             sb.append(value + "#");
/* 1068 */             closeResultSet(rs);
/*      */           }
/* 1070 */           String toinsert = sb.toString();
/* 1071 */           if (toinsert.length() > 0) {
/* 1072 */             toinsert = toinsert.substring(0, toinsert.length() - 1);
/* 1073 */             String q1 = "insert into AM_SCRIPT_ROWS VALUES (" + rowid + ",'" + toinsert + "')";
/* 1074 */             AMConnectionPool.executeUpdateStmt(q1);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1080 */         exc.printStackTrace();
/*      */       }
/*      */     }
/* 1083 */     if (isamCreated)
/*      */     {
/*      */       try {
/* 1086 */         AMConnectionPool.executeUpdateStmt("update AM_ManagedObject set DCSTARTED=5 where RESOURCEID in(" + resourceid + ")");
/* 1087 */         AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + scriptid + " and CHILDRESOURCEID in(" + resourceid + ")");
/* 1088 */         String query = "select RESOURCEID,DISPLAYNAME from Am_ManagedObject where RESOURCEID in(" + resourceid + ")";
/* 1089 */         ResultSet rs = null;
/* 1090 */         AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/*      */         try {
/* 1092 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1093 */           while (rs.next()) {
/* 1094 */             toinsert.addBatch("insert  into AM_SCRIPT_ROWS VALUES (" + rs.getString("RESOURCEID") + ",'" + rs.getString("DISPLAYNAME") + "')");
/*      */           }
/* 1096 */           toinsert.executeBatch();
/*      */         }
/*      */         catch (Exception e) {
/* 1099 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/*      */           try {
/* 1103 */             rs.close();
/* 1104 */             toinsert.clearBatch();
/* 1105 */             toinsert.close();
/*      */           }
/*      */           catch (Exception e) {
/* 1108 */             e.printStackTrace();
/*      */           }
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
/* 1121 */         if (!Constants.sqlManager) {
/*      */           break label1176;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1114 */         e.printStackTrace();
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1123 */       String sqlmanid = request.getParameter("sqlmanid");
/* 1124 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=8"); }
/*      */     label1176:
/* 1126 */     if (request.getParameter("fromPopUpWindow") != null) {
/* 1127 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1131 */     if (request.getParameter("tabId") != null) {
/* 1132 */       String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/* 1133 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid + appendHash, true);
/*      */     }
/*      */     
/* 1136 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward enableRowByUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1142 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1143 */     String resourceid = request.getParameter("resourceid");
/* 1144 */     if (request.getParameter("tabId") != null) {
/* 1145 */       resourceid = request.getParameter("rowid");
/*      */     }
/* 1147 */     String tableid = request.getParameter("tableid") != null ? request.getParameter("tableid") : "";
/* 1148 */     String scriptid = request.getParameter("scriptid");
/* 1149 */     String table_health = request.getParameter("table_health");
/* 1150 */     String mon_health = request.getParameter("mon_health");
/* 1151 */     String type = request.getParameter("type") != null ? request.getParameter("type") : "";
/* 1152 */     if ((ConfMonitorConfiguration.getInstance().isConfMonitor(type)) && (!type.equalsIgnoreCase("QueryMonitor"))) {
/* 1153 */       Hashtable healthkeys = (Hashtable)request.getSession().getServletContext().getAttribute("healthkeys");
/* 1154 */       mon_health = (String)healthkeys.get(type);
/* 1155 */       HashMap tableDetails = ConfMonitorConfiguration.getInstance().getUITableDetails(type + "-" + request.getParameter("tableName"));
/* 1156 */       table_health = tableDetails != null ? (String)tableDetails.get("HEALTHATTRIBUTEID") : null;
/*      */       
/* 1158 */       if (table_health == null)
/*      */       {
/* 1160 */         String rowid = request.getParameter("rowid");
/* 1161 */         StringTokenizer str = new StringTokenizer(rowid, ",");
/* 1162 */         if (str.hasMoreTokens())
/*      */         {
/* 1164 */           rowid = str.nextToken();
/*      */         }
/* 1166 */         Properties commonProps = DBUtil.getCommonAttributes(DBUtil.getTypeName(Integer.parseInt(rowid)));
/* 1167 */         table_health = commonProps.getProperty("Health");
/*      */       }
/*      */     }
/* 1170 */     StringTokenizer str = new StringTokenizer(resourceid, ",");
/* 1171 */     while (str.hasMoreTokens())
/*      */     {
/*      */       try
/*      */       {
/* 1175 */         String rowid = str.nextToken();
/*      */         
/* 1177 */         AMConnectionPool.executeUpdateStmt("update AM_ManagedObject set DCSTARTED=1 where RESOURCEID=" + rowid);
/* 1178 */         AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values(" + scriptid + "," + mon_health + "," + rowid + "," + table_health + ")");
/* 1179 */         AMConnectionPool.executeUpdateStmt("delete from AM_SCRIPT_ROWS where ROWID=" + rowid);
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1183 */         exc.printStackTrace();
/*      */       }
/*      */     }
/* 1186 */     if (Constants.sqlManager)
/*      */     {
/* 1188 */       String sqlmanid = request.getParameter("sqlmanid");
/* 1189 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=8");
/*      */     }
/* 1191 */     if (request.getParameter("fromPopUpWindow") != null) {
/* 1192 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1196 */     if (request.getParameter("tabId") != null) {
/* 1197 */       String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/* 1198 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid + appendHash, true);
/*      */     }
/*      */     
/* 1201 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean insertTableAttributes(ArrayList tableAttributes, String tablename, int resourceid, String delimiter, int mon_healthid, String toappend)
/*      */   {
/* 1209 */     ArrayList sattributes = (ArrayList)tableAttributes.get(0);
/* 1210 */     ArrayList nattributes = (ArrayList)tableAttributes.get(1);
/* 1211 */     ArrayList pattributes = (ArrayList)tableAttributes.get(2);
/* 1212 */     String primary_column = "";
/* 1213 */     System.out.println("pattributes---" + pattributes);
/* 1214 */     System.out.println("sattributes---" + sattributes);
/* 1215 */     System.out.println("nattributes---" + nattributes);
/* 1216 */     if ((pattributes.size() == 0) || ((sattributes.size() == 0) && (nattributes.size() == 0)))
/*      */     {
/* 1218 */       System.out.println("Either Primary Attributes or the combination of the String and Numeric Attributes size is 0 ->Insertion");
/* 1219 */       return false;
/*      */     }
/* 1221 */     for (int i = 0; i < pattributes.size(); i++)
/*      */     {
/* 1223 */       primary_column = primary_column + pattributes.get(i);
/* 1224 */       if (i != pattributes.size() - 1)
/*      */       {
/* 1226 */         primary_column = primary_column + "#";
/*      */       }
/*      */     }
/* 1229 */     int att_type = 3;
/* 1230 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1231 */     String type = "Script Monitor";
/*      */     try
/*      */     {
/* 1234 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 1235 */       AMConnectionPool.getInstance();Statement depinsert = AMConnectionPool.getConnection().createStatement();
/* 1236 */       String error_message = "";
/* 1237 */       int tableid = NewMonitorUtil.getNextTableId("AM_SCRIPT_TABLES", "TABLEID");
/* 1238 */       if (tableid == -1)
/*      */       {
/* 1240 */         return false;
/*      */       }
/* 1242 */       String table_insert = "insert into AM_SCRIPT_TABLES(SCRIPTID,TABLENAME,TABLEID,PRIMARY_COLUMN,COL_DELIMITER,ERROR) values(" + resourceid + ",'" + tablename + "'," + tableid + ",'" + primary_column + "','" + delimiter + "','" + error_message + "')";
/* 1243 */       AMConnectionPool.executeUpdateStmt(table_insert);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1249 */       String am_attributes_entry = "";
/* 1250 */       String am_cam_attributes_entry = "";
/* 1251 */       int displaytype = 0;
/* 1252 */       String expression = "";
/* 1253 */       int graphtype = 0;
/* 1254 */       int temp_attid = NewMonitorUtil.getAttributeId();
/* 1255 */       int healthid = temp_attid;
/* 1256 */       String attributetype = "SCRIPT_" + tableid + "_HEALTH";
/* 1257 */       String attributetype_name = "Health";
/*      */       
/* 1259 */       String rowresourcetype = "SCRIPT_ROW";
/*      */       
/* 1261 */       String attr_health = "insert into AM_ATTRIBUTES VALUES(" + healthid + ",'" + rowresourcetype + "','Health',2,'Health','NULL')";
/* 1262 */       String attr_health_ext = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + healthid + ",'AM_ManagedObjectData','RESID','-1','HEALTH','COLLECTIONTIME','1','','Health','')";
/* 1263 */       String am_table_health = "insert into AM_CAM_DC_ATTRIBUTES(ATTRIBUTEID,ATTRIBUTENAME,DISPLAYNAME,TYPE,GROUPID,DISPLAYTYPE,EXPRESSION,GRAPHTYPE) values(" + healthid + ",'Health','Health',2," + tableid + ",-1,'-1',-1)";
/* 1264 */       temp_attid++;
/* 1265 */       for (int i = 0; i < sattributes.size(); i++)
/*      */       {
/*      */ 
/* 1268 */         String attributename = (String)sattributes.get(i);
/* 1269 */         am_attributes_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + rowresourcetype + "','" + attributename + "'," + att_type + ",'" + attributename + "')";
/* 1270 */         String am_attributes_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_CAM_COLUMNAR_DATA" + toappend + "','ROWID','ATTRIBUTEID','VALUE','COLLECTIONTIME','2','','Health','')";
/* 1271 */         am_cam_attributes_entry = "insert into AM_CAM_DC_ATTRIBUTES(ATTRIBUTEID,ATTRIBUTENAME,DISPLAYNAME,TYPE,GROUPID,DISPLAYTYPE,EXPRESSION,GRAPHTYPE) values(" + temp_attid + ",'" + attributename + "','" + attributename + "'," + att_type + "," + tableid + "," + displaytype + ",'" + expression + "'," + graphtype + ")";
/*      */         
/* 1273 */         toinsert.addBatch(am_attributes_entry);
/* 1274 */         toinsert.addBatch(am_cam_attributes_entry);
/* 1275 */         toinsert.addBatch(am_attributes_ext_entry);
/*      */         
/* 1277 */         String qry = "insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")";
/* 1278 */         depinsert.addBatch(qry);
/* 1279 */         temp_attid++;
/*      */       }
/* 1281 */       att_type = 0;
/* 1282 */       for (int i = 0; i < nattributes.size(); i++)
/*      */       {
/*      */ 
/* 1285 */         String attributename = (String)nattributes.get(i);
/* 1286 */         am_attributes_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + rowresourcetype + "','" + attributename + "'," + att_type + ",'" + attributename + "')";
/* 1287 */         am_cam_attributes_entry = "insert into AM_CAM_DC_ATTRIBUTES(ATTRIBUTEID,ATTRIBUTENAME,DISPLAYNAME,TYPE,GROUPID,DISPLAYTYPE,EXPRESSION,GRAPHTYPE) values(" + temp_attid + ",'" + attributename + "','" + attributename + "'," + att_type + "," + tableid + "," + displaytype + ",'" + expression + "'," + graphtype + ")";
/* 1288 */         String am_attributes_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_SCRIPT_TABULAR_NUMERIC_DATA" + toappend + "','RESID','ATTRIBUTEID','VALUE','COLLECTIONTIME','2','','" + attributename + "','')";
/* 1289 */         toinsert.addBatch(am_attributes_entry);
/* 1290 */         toinsert.addBatch(am_cam_attributes_entry);
/* 1291 */         toinsert.addBatch(am_attributes_ext_entry);
/*      */         
/*      */ 
/* 1294 */         String qry = "insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")";
/* 1295 */         depinsert.addBatch(qry);
/* 1296 */         temp_attid++;
/*      */       }
/* 1298 */       toinsert.addBatch(attr_health);
/* 1299 */       toinsert.addBatch(attr_health_ext);
/* 1300 */       toinsert.addBatch(am_table_health);
/* 1301 */       toinsert.executeBatch();
/* 1302 */       toinsert.clearBatch();
/* 1303 */       depinsert.addBatch("insert into AM_ATTRIBUTESDEPENDENCY VALUES(" + mon_healthid + "," + healthid + ")");
/* 1304 */       depinsert.executeBatch();
/* 1305 */       depinsert.clearBatch();
/*      */ 
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1310 */       exc.printStackTrace();
/*      */     }
/* 1312 */     return true;
/*      */   }
/*      */   
/*      */   private static int getScriptTableID()
/*      */   {
/* 1317 */     int tableid = -1;
/* 1318 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 1321 */       String qry = "select max(TABLEID) from AM_SCRIPT_TABLES";
/* 1322 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1323 */       if (rs.next())
/*      */       {
/* 1325 */         String val = rs.getString(1);
/* 1326 */         if ((val != null) && (!val.equals("NULL")))
/*      */         {
/* 1328 */           tableid = Integer.parseInt(val);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1334 */       exc.printStackTrace();
/*      */     }
/* 1336 */     return tableid;
/*      */   }
/*      */   
/*      */   public ActionForward filemon(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1341 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/* 1343 */       ActionErrors errors = new ActionErrors();
/* 1344 */       ActionMessages messages = new ActionMessages();
/* 1345 */       String haid = request.getParameter("haid");
/* 1346 */       String resourcetype = ((AMActionForm)form).getType();
/* 1347 */       Properties argsprops = new Properties();
/* 1348 */       argsprops.setProperty("monitorType", resourcetype);
/* 1349 */       String clearConditionRuleType = request.getParameter("clearConditionRuleType");
/* 1350 */       String clearConditionCountVal = request.getParameter("clearConditionCountVal");
/* 1351 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/* 1353 */         String param = (String)e.nextElement();
/* 1354 */         if (!argsprops.containsKey(param))
/*      */         {
/* 1356 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/* 1358 */         if (param.equals("haid"))
/*      */         {
/* 1360 */           String[] multiVal = request.getParameterValues(param);
/* 1361 */           if ((multiVal != null) && (multiVal.length > 0)) {
/* 1362 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/* 1363 */             val = val.substring(1, val.length() - 1);
/* 1364 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/* 1368 */       if ((clearConditionRuleType != null) && (clearConditionRuleType.equals("1"))) {
/* 1369 */         argsprops.setProperty("clearConditionRuleType", "All");
/*      */       } else {
/* 1371 */         clearConditionCountVal = clearConditionCountVal == null ? "1" : clearConditionCountVal;
/* 1372 */         argsprops.setProperty("clearConditionRuleType", clearConditionCountVal);
/*      */       }
/*      */       try
/*      */       {
/* 1376 */         getRemoteHostDetails(argsprops);
/* 1377 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/* 1378 */         ArrayList<String> al1 = new ArrayList();
/* 1379 */         String displayname = request.getParameter("displayname");
/* 1380 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/* 1381 */           displayname = request.getParameter("displayName");
/*      */         }
/* 1383 */         String status = "Success";
/* 1384 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/* 1385 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/* 1386 */         al1.add(displayname);
/* 1387 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/* 1388 */           status = "Failed";
/* 1389 */           message = (String)responseMap.get("message");
/*      */         }
/* 1391 */         al1.add(status);
/* 1392 */         al1.add(message);
/* 1393 */         al1.add(masDisplayName);
/* 1394 */         request.setAttribute("discoverystatus", al1);
/* 1395 */         request.setAttribute("type", resourcetype);
/* 1396 */         request.setAttribute("basetype", "Script Monitor");
/*      */         
/*      */ 
/* 1399 */         if (((String)responseMap.get("addStatus")).equals("true")) {
/* 1400 */           addScriptHostDetailsInAAM(argsprops);
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
/* 1420 */         e.printStackTrace();
/*      */       }
/* 1422 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid);
/*      */     }
/*      */     
/* 1425 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 1426 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/* 1427 */     PreparedStatement ps1 = null;
/* 1428 */     boolean ret = DataCollectionControllerUtil.isallowed();
/* 1429 */     if (!ret)
/*      */     {
/* 1431 */       return new ActionForward("/showresource.do?method=showResourceTypes&fromwhere=unabletocreate");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1436 */       String wiz = "";
/* 1437 */       if (request.getParameter("wiz") != null)
/*      */       {
/* 1439 */         wiz = "&wiz=true";
/*      */       }
/* 1441 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*      */       
/* 1443 */       int resourceid = -1;
/* 1444 */       String filepath = null;
/* 1445 */       int conChk_MonAlert = -1;
/* 1446 */       int fCheckType = -1;int RegexCheck = 0;
/* 1447 */       String conChk_ruleType = null;
/* 1448 */       File dir = null;
/* 1449 */       String[] file_checkname = new String[2];
/* 1450 */       ArrayList al1 = new ArrayList();
/* 1451 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1452 */       String dname = ((AMActionForm)form).getDisplayname();
/* 1453 */       String mtype = ((AMActionForm)form).getMtype();
/* 1454 */       String server = ((AMActionForm)form).getServersite();
/* 1455 */       boolean isCredManager = ((AMActionForm)form).getIsCredManager();
/* 1456 */       Long credentialID = Long.valueOf(0L);
/* 1457 */       if (isCredManager) {
/* 1458 */         credentialID = Long.valueOf(new Long(((AMActionForm)form).getCredentialID()).longValue());
/*      */       }
/* 1460 */       String fpath = ((AMActionForm)form).getFilepath();
/*      */       
/*      */ 
/* 1463 */       String contentChk = ((AMActionForm)form).getContentChk();
/* 1464 */       String regexpChk = ((AMActionForm)form).getRegexChk();
/* 1465 */       String content = null;
/* 1466 */       String checkEmpty = ((AMActionForm)form).getCheckEmpty();
/* 1467 */       int intCheckEmpty = 0;
/*      */       
/* 1469 */       int clearCondition = 0;
/* 1470 */       String clearConditionRuleType = "0";
/* 1471 */       String clearConditionContent = "";
/* 1472 */       int intClearConditionRegEx = 0;
/*      */       
/* 1474 */       if ((mtype.equals("file")) && (contentChk != null) && (contentChk.equalsIgnoreCase("on"))) {
/* 1475 */         content = ((AMActionForm)form).getCcontent();
/* 1476 */         fCheckType = ((AMActionForm)form).getFileCheckType();
/* 1477 */         conChk_MonAlert = ((AMActionForm)form).getSelectStatusType();
/* 1478 */         intCheckEmpty = (checkEmpty != null) && (checkEmpty.equalsIgnoreCase("on")) ? 1 : intCheckEmpty;
/* 1479 */         if ("on".equalsIgnoreCase(regexpChk)) {
/* 1480 */           RegexCheck = 1;
/* 1481 */           conChk_ruleType = null;
/*      */         }
/*      */         else {
/* 1484 */           conChk_ruleType = ((AMActionForm)form).getSelectRuleType();
/* 1485 */           String countVal = ((AMActionForm)form).getCountval();
/* 1486 */           if (conChk_ruleType.equals("0")) {
/* 1487 */             conChk_ruleType = countVal;
/*      */           }
/*      */           else {
/* 1490 */             conChk_ruleType = "All";
/*      */           }
/*      */         }
/* 1493 */         clearCondition = ((AMActionForm)form).getClearCondition();
/* 1494 */         clearConditionRuleType = ((AMActionForm)form).getClearConditionRuleType();
/* 1495 */         clearConditionContent = ((AMActionForm)form).getClearConditionContent();
/* 1496 */         String clearConditionCountVal = ((AMActionForm)form).getClearConditionCountVal();
/* 1497 */         String clearConditionRegEx = ((AMActionForm)form).getClearConditionRegEx();
/* 1498 */         if (clearConditionRuleType.equals("0")) {
/* 1499 */           clearConditionRuleType = clearConditionCountVal;
/*      */         } else {
/* 1501 */           clearConditionRuleType = "All";
/*      */         }
/* 1503 */         if ((clearConditionRegEx != null) && (clearConditionRegEx.equalsIgnoreCase("on"))) {
/* 1504 */           intClearConditionRegEx = 1;
/*      */         }
/* 1506 */         if (clearCondition == 0) {
/* 1507 */           clearConditionRuleType = "0";
/* 1508 */           clearConditionContent = "";
/* 1509 */           intClearConditionRegEx = 0;
/*      */         }
/* 1511 */       } else if ((mtype.equals("file")) && (contentChk == null)) {
/* 1512 */         content = "";
/* 1513 */         conChk_MonAlert = -1;
/* 1514 */         conChk_ruleType = null;
/* 1515 */         clearCondition = 0;
/* 1516 */         clearConditionRuleType = "0";
/* 1517 */         clearConditionContent = "";
/* 1518 */         intClearConditionRegEx = 0;
/*      */       }
/*      */       
/*      */ 
/* 1522 */       String fileDirAgeChk = ((AMActionForm)form).getFileDirAge();
/* 1523 */       int fileDirAge_MonAlert = -1;
/* 1524 */       int fileDirAge_ChangeType = -1;
/* 1525 */       int fileDirAge_timeVal = 0;
/* 1526 */       String fileDirAge_timeUnit = null;
/* 1527 */       if ((fileDirAgeChk != null) && (fileDirAgeChk.equalsIgnoreCase("on")))
/*      */       {
/* 1529 */         fileDirAge_MonAlert = ((AMActionForm)form).getSelectMonStatus();
/* 1530 */         fileDirAge_ChangeType = ((AMActionForm)form).getSelectChangeType();
/* 1531 */         fileDirAge_timeVal = ((AMActionForm)form).getTimeval();
/* 1532 */         fileDirAge_timeUnit = ((AMActionForm)form).getTimeUnit();
/*      */       }
/*      */       
/* 1535 */       int subDirChkVal = 0;
/* 1536 */       String subDirChk = ((AMActionForm)form).getSubDirCntChk();
/* 1537 */       if ((subDirChk != null) && (subDirChk.equalsIgnoreCase("on"))) {
/* 1538 */         subDirChkVal = 1;
/*      */       }
/*      */       
/* 1541 */       String choosehost = "-1";
/* 1542 */       if (((AMActionForm)form).getChoosehost() != null)
/*      */       {
/* 1544 */         choosehost = ((AMActionForm)form).getChoosehost().trim();
/* 1545 */         if ((choosehost != null) && (choosehost.contains(","))) {
/* 1546 */           StringTokenizer st = new StringTokenizer(choosehost);
/* 1547 */           while (st.hasMoreTokens()) {
/* 1548 */             choosehost = st.nextToken(",");
/*      */           }
/* 1550 */           ((AMActionForm)form).setChoosehost(choosehost);
/*      */         }
/*      */       }
/* 1553 */       boolean successful = false;
/*      */       
/* 1555 */       String resourcetype = ((AMActionForm)form).getType();
/* 1556 */       request.setAttribute("type", resourcetype);
/* 1557 */       int poll_interval = ((AMActionForm)form).getPollInterval();
/* 1558 */       int pollinginterval = poll_interval * 60;
/* 1559 */       int timeout = ((AMActionForm)form).getTimeout();
/* 1560 */       ArrayList hostdetailsarray = new ArrayList();
/* 1561 */       Hashtable output = new Hashtable();
/* 1562 */       String haid = request.getParameter("haid");
/* 1563 */       String mmode = null;
/*      */       
/*      */ 
/* 1566 */       this.exit_command1 = (this.exit_command + "1");
/* 1567 */       if (server.equals("remote"))
/*      */       {
/* 1569 */         output = doCheckforRemote(form, server, hostdetailsarray, timeout);
/*      */         
/* 1571 */         String sysfile = null;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1579 */         mmode = (String)hostdetailsarray.get(3);
/*      */       }
/*      */       
/*      */ 
/* 1583 */       File f1 = new File(fpath).getAbsoluteFile();
/*      */       
/* 1585 */       al1.add(fpath);
/* 1586 */       boolean bo = true;
/*      */       
/* 1588 */       String haid_str = "";
/* 1589 */       if (request.getParameter("haid") != null)
/*      */       {
/* 1591 */         haid_str = "&haid=" + request.getParameter("haid");
/*      */       }
/* 1593 */       if (!bo)
/*      */       {
/* 1595 */         al1.add("Failed");
/* 1596 */         al1.add(FormatUtil.getString("am.webclient.script.improperexpectedrsults"));
/* 1597 */         request.setAttribute("discoverystatus", al1);
/* 1598 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */       }
/*      */       
/* 1601 */       if (server.equals("remote"))
/*      */       {
/* 1603 */         if (output.get("failure") != null)
/*      */         {
/* 1605 */           String error_msg = "am.webclient.script.loginexception.text";
/* 1606 */           boolean issuccess = true;
/* 1607 */           if (output.get("failure").equals("Login Exception"))
/*      */           {
/* 1609 */             issuccess = false;
/*      */           }
/* 1611 */           else if (output.get("failure").equals("ConnectException"))
/*      */           {
/* 1613 */             issuccess = false;
/* 1614 */             error_msg = "am.webclient.connectexception.text";
/*      */           }
/*      */           
/* 1617 */           if (!issuccess)
/*      */           {
/* 1619 */             al1.add("Failed");
/* 1620 */             al1.add(FormatUtil.getString(error_msg));
/* 1621 */             request.setAttribute("discoverystatus", al1);
/* 1622 */             System.out.println("The discoverystatus:" + al1);
/* 1623 */             return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */           }
/*      */         }
/* 1626 */         if (mmode.equals("WMI")) {
/* 1627 */           if (mtype.equals("directory")) {
/* 1628 */             ((AMActionForm)form).setMtype("directory");
/*      */             
/* 1630 */             if ((String.valueOf(output.get(this.exit_command)).trim().startsWith("0")) || (AMFileDataCollector.checkPathContainsPatternForm(fpath))) {
/* 1631 */               AMLog.info("provided file path is having pattern format");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/* 1637 */               al1.add("Failed");
/* 1638 */               al1.add(FormatUtil.getString("Folder does not exist"));
/* 1639 */               request.setAttribute("discoverystatus", al1);
/* 1640 */               return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */             }
/*      */           }
/*      */           else {
/* 1644 */             ((AMActionForm)form).setMtype("file");
/*      */           }
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
/*      */ 
/*      */ 
/*      */ 
/* 1661 */         if (!mmode.equals("WMI"))
/*      */         {
/* 1663 */           if (mtype.equals("directory")) {
/* 1664 */             ((AMActionForm)form).setMtype("directory");
/*      */             
/* 1666 */             if ((String.valueOf(output.get(this.exit_command)).trim().startsWith("0")) || (AMFileDataCollector.checkPathContainsPatternForm(fpath))) {
/* 1667 */               AMLog.info("provided file path is having pattern format");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 1672 */               al1.add("Failed");
/* 1673 */               al1.add(FormatUtil.getString("Directory does not exist"));
/* 1674 */               request.setAttribute("discoverystatus", al1);
/* 1675 */               return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */             }
/*      */           }
/*      */           else {
/* 1679 */             ((AMActionForm)form).setMtype("file");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           
/*      */ 
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
/*      */ 
/*      */       }
/* 1702 */       else if (mtype.equals("file")) {
/* 1703 */         ((AMActionForm)form).setMtype("file");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 1720 */       else if (mtype.equals("directory")) {
/* 1721 */         ((AMActionForm)form).setMtype("directory");
/*      */         
/* 1723 */         if (((!f1.exists()) || (!f1.isDirectory())) && (!AMFileDataCollector.checkPathContainsPatternForm(fpath)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1730 */           al1.add("Failed");
/* 1731 */           al1.add(FormatUtil.getString("The Directory does not exist / It may be a File"));
/* 1732 */           request.setAttribute("discoverystatus", al1);
/* 1733 */           System.out.println("The file doesnot exist");
/* 1734 */           return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1740 */       String serversite = null;
/* 1741 */       if (server.equals("remote")) {
/* 1742 */         serversite = "2";
/*      */       } else
/* 1744 */         serversite = "1";
/* 1745 */       int scriptcheck = 1;
/* 1746 */       String filename = null;
/* 1747 */       String[] filename_path = new String[2];
/* 1748 */       filename_path = getFilename(fpath, serversite);
/* 1749 */       filename = filename_path[1];
/*      */       
/* 1751 */       scriptcheck = 4;
/*      */       
/* 1753 */       if (scriptcheck == 4)
/*      */       {
/* 1755 */         resourceid = NewMonitorUtil.mocreate(dname, fpath, mtype);
/* 1756 */         adder.addInterDependentAttributes(resourceid);
/* 1757 */         if ((content != null) && (content.equals("")))
/*      */         {
/* 1759 */           content = "";
/*      */         }
/* 1761 */         AMConnectionPool.getInstance();PreparedStatement ps = AMConnectionPool.getPreparedStatement("insert into AM_FILEDIR values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */         
/*      */         try
/*      */         {
/* 1765 */           ps.setInt(1, resourceid);
/* 1766 */           ps.setString(2, fpath);
/* 1767 */           ps.setString(3, mtype);
/* 1768 */           ps.setInt(4, pollinginterval);
/* 1769 */           ps.setString(5, serversite);
/* 1770 */           ps.setString(6, content);
/* 1771 */           ps.setInt(7, timeout);
/* 1772 */           ps.setString(8, "");
/* 1773 */           ps.setInt(9, conChk_MonAlert);
/* 1774 */           ps.setString(10, conChk_ruleType);
/* 1775 */           ps.setInt(11, fCheckType);
/* 1776 */           ps.setInt(12, fileDirAge_MonAlert);
/* 1777 */           ps.setInt(13, fileDirAge_ChangeType);
/* 1778 */           ps.setInt(14, fileDirAge_timeVal);
/* 1779 */           ps.setString(15, fileDirAge_timeUnit);
/* 1780 */           ps.setInt(16, subDirChkVal);
/* 1781 */           ps.setInt(17, RegexCheck);
/* 1782 */           ps.setInt(18, clearCondition);
/* 1783 */           ps.setString(19, clearConditionRuleType);
/* 1784 */           ps.setString(20, clearConditionContent);
/* 1785 */           ps.setInt(21, intClearConditionRegEx);
/* 1786 */           ps.setInt(22, intCheckEmpty);
/* 1787 */           ps.executeUpdate();
/*      */           
/* 1789 */           if (server.equals("remote"))
/*      */           {
/* 1791 */             String host_id = "-10";
/*      */             
/*      */ 
/* 1794 */             if ((choosehost != null) && (choosehost.equals("-1")))
/*      */             {
/* 1796 */               if (((String)hostdetailsarray.get(3)).equals("WMI")) {}
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1804 */               int count = -1;
/* 1805 */               String username = (String)hostdetailsarray.get(1);
/* 1806 */               if ((username.indexOf('\\') != -1) && (!DBQueryUtil.getDBType().equals("mssql")) && (!DBQueryUtil.getDBType().equals("pgsql")))
/*      */               {
/* 1808 */                 username = StrUtil.findReplace(username, "\\", "\\\\");
/*      */               }
/* 1810 */               ResultSet rs = null;
/* 1811 */               ResultSet rs1 = null;
/*      */               try
/*      */               {
/* 1814 */                 String query1 = "select * from AM_SCRIPTHOSTDETAILS where HOSTNAME ='" + hostdetailsarray.get(0) + "' and USERNAME='" + username + "'";
/* 1815 */                 rs = AMConnectionPool.executeQueryStmt(query1);
/* 1816 */                 if (!rs.next())
/*      */                 {
/* 1818 */                   int tableid = NewMonitorUtil.getNextTableId("AM_SCRIPTHOSTDETAILS", "ID");
/* 1819 */                   ps1 = AMConnectionPool.getConnection().prepareStatement("insert into AM_SCRIPTHOSTDETAILS(ID,HOSTNAME,USERNAME,PASSWORD,MODE,PROMPT,PORT) values(" + tableid + ",'" + hostdetailsarray.get(0) + "',?," + DBQueryUtil.encode(hostdetailsarray.get(2).toString()) + ",'" + hostdetailsarray.get(3) + "','" + hostdetailsarray.get(4) + "'," + hostdetailsarray.get(5) + ")");
/* 1820 */                   ps1.setString(1, username);
/* 1821 */                   ps1.executeUpdate();
/*      */                 }
/*      */                 
/* 1824 */                 query1 = "select ID from AM_SCRIPTHOSTDETAILS where HOSTNAME ='" + hostdetailsarray.get(0) + "' and MODE in ('TELNET','SSH','SSH_KEY','WMI') and USERNAME='" + username + "' UNION select RESOURCEID from  AM_ManagedObject where RESOURCENAME='" + hostdetailsarray.get(0) + "'";
/* 1825 */                 rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 1826 */                 if (rs1.next())
/*      */                 {
/* 1828 */                   host_id = rs1.getString("ID");
/*      */                 }
/*      */                 
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/* 1834 */                 exc.printStackTrace();
/*      */               }
/*      */               finally {
/* 1837 */                 AMConnectionPool.closeStatement(rs);
/* 1838 */                 AMConnectionPool.closeStatement(rs1);
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 1843 */               host_id = ((AMActionForm)form).getChoosehost();
/*      */             }
/*      */             
/* 1846 */             AMConnectionPool.executeUpdateStmt("insert into AM_SCRIPTHOST_MAPPER values(" + resourceid + "," + host_id + ")");
/*      */             
/*      */ 
/* 1849 */             if (isCredManager) {
/* 1850 */               CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 1851 */               credUtil.addToCredentialToResourceMap(credentialID.longValue(), resourceid);
/*      */             }
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
/*      */           try
/*      */           {
/* 1877 */             ps.close();
/* 1878 */             ps1.close();
/*      */           }
/*      */           catch (Exception ex) {}
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 1885 */           successful = true;
/*      */         }
/*      */         catch (SQLException sqle4)
/*      */         {
/* 1868 */           sqle4.printStackTrace();
/*      */         }
/*      */         catch (Exception e4)
/*      */         {
/* 1872 */           e4.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*      */           try {
/* 1877 */             ps.close();
/* 1878 */             ps1.close();
/*      */           }
/*      */           catch (Exception ex) {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1886 */         al1.add("Success");
/* 1887 */         al1.add("/showresource.do?resourceid=" + resourceid + "&moname=" + filename + "&type=" + ((AMActionForm)form).getType() + "&method=showdetails&resourcename=" + dname + "&mtype=" + mtype);
/*      */       }
/* 1889 */       request.setAttribute("discoverystatus", al1);
/* 1890 */       request.setAttribute("resourceid", Integer.valueOf(resourceid));
/* 1891 */       if ((resourceid != -1) && 
/* 1892 */         (ClientDBUtil.isPrivilegedUser(request))) {
/* 1893 */         RestrictedUsersViewUtil.insertIntoAMUserResourcesTableandsynchtoAAM(request.getRemoteUser(), Long.valueOf(resourceid).longValue());
/*      */       }
/*      */       
/* 1896 */       String addtoapplication = request.getParameter("addtoha");
/* 1897 */       String resourcename = "";
/* 1898 */       String type = "File System Monitor";
/* 1899 */       if ((successful) && (addtoapplication != null) && (addtoapplication.equals("true")))
/*      */       {
/* 1901 */         String[] resources = { String.valueOf(resourceid) };
/* 1902 */         String[] selMonitorGroups = request.getParameterValues("haid");
/* 1903 */         this.mo.updateManagedApplicationResources(selMonitorGroups, "xyz", resources, null, null);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1908 */         AM_KeyValueDataCollector amkv = new AM_KeyValueDataCollector(String.valueOf(resourceid), resourcename, mtype);
/*      */         
/* 1910 */         Scheduler.getScheduler("KeyValue_Monitor").scheduleTask(amkv, System.currentTimeMillis());
/* 1911 */         System.out.println("Entry made in the datacollector hashtable...");
/* 1912 */         AMScriptProcess.resid_instance.put(String.valueOf(resourceid), amkv);
/* 1913 */         log.info("Triggering completed");
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 1918 */         exc.printStackTrace();
/*      */       }
/* 1920 */       String wiz1 = request.getParameter("wiz");
/*      */       
/* 1922 */       if (wiz1 != null)
/*      */       {
/* 1924 */         String value = request.getParameter("value");
/* 1925 */         request.setAttribute("discoverystatus", al1);
/* 1926 */         if (value == null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1931 */           return new ActionForward("/showresource.do?method=associateMonitors" + wiz + haid_str + "&mtype=" + mtype);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1937 */         return new ActionForward("/showActionProfiles.do?method=getHAProfiles" + wiz + haid_str + "&mtype=" + mtype);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1943 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid + wiz);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1947 */       e.printStackTrace(); }
/* 1948 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward wmihost(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1956 */     int hostID = 0;
/* 1957 */     String hostmode = null;
/* 1958 */     int id = Integer.parseInt(request.getParameter("id"));
/* 1959 */     String hostquery = "Select MODE  from AM_SCRIPTHOSTDETAILS where ID=" + id;
/*      */     try
/*      */     {
/* 1962 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1963 */       ResultSet rs = AMConnectionPool.executeQueryStmt(hostquery);
/* 1964 */       if (rs.next())
/*      */       {
/*      */ 
/* 1967 */         hostmode = rs.getString("MODE");
/*      */       }
/*      */       
/* 1970 */       if (hostmode.equals("WMI"))
/*      */       {
/* 1972 */         hostID = id;
/*      */       }
/*      */       else
/* 1975 */         hostID = 0;
/* 1976 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/* 1979 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1982 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Hashtable doCheckforRemote(ActionForm form, String serversite, ArrayList hostdetailsarray, int timeout)
/*      */   {
/* 1990 */     Hashtable output = new Hashtable();
/* 1991 */     String choosehost = ((AMActionForm)form).getChoosehost().trim();
/* 1992 */     WMIDataCollector wmi = new WMIDataCollector();
/* 1993 */     String hostname = null;
/* 1994 */     String monitoringmode = null;
/* 1995 */     String username = null;
/* 1996 */     String password = null;
/* 1997 */     String prompt = "$";
/* 1998 */     String port = null;
/* 1999 */     String mtype = null;
/* 2000 */     boolean isCommand = ((AMActionForm)form).getisCommand();
/* 2001 */     FileDirUtil fdUtil = new FileDirUtil();
/* 2002 */     if (((AMActionForm)form).getMtype() == null)
/*      */     {
/* 2004 */       mtype = "script";
/*      */     }
/*      */     else {
/* 2007 */       mtype = ((AMActionForm)form).getMtype();
/*      */     }
/* 2009 */     int i = 0;
/* 2010 */     String script = null;
/* 2011 */     String folder = null;
/* 2012 */     ArrayList directory = new ArrayList();
/* 2013 */     if (serversite.equals("remote")) {
/* 2014 */       serversite = "2";
/*      */     }
/* 2016 */     if ((mtype.equals("directory")) || (mtype.equals("file")))
/*      */     {
/*      */ 
/* 2019 */       script = ((AMActionForm)form).getFilepath().trim();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2024 */       folder = script;
/*      */     }
/*      */     else {
/* 2027 */       script = ((AMActionForm)form).getServerpath().trim();
/* 2028 */       folder = ((AMActionForm)form).getWorkingdirectory().trim();
/*      */     }
/*      */     
/*      */ 
/* 2032 */     String sshkey = null;
/* 2033 */     String exit_command = "echo $?";
/* 2034 */     String shell = "sh";
/* 2035 */     String lscommand = "ls ";
/* 2036 */     shell = ((AMActionForm)form).getMode();
/* 2037 */     String passphrase = ((AMActionForm)form).getPassphrase();
/* 2038 */     if ((script != null) && (script.indexOf("\\") != -1))
/*      */     {
/* 2040 */       shell = "windows";
/* 2041 */       lscommand = "dir ";
/*      */     }
/* 2043 */     exit_command = Constants.getExitCommand_Script(shell);
/* 2044 */     if (serversite.equals("2"))
/*      */     {
/* 2046 */       CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 2047 */       Long credentialID = Long.valueOf(0L);
/* 2048 */       Properties credentialSelectedProperties = null;
/* 2049 */       boolean isCredentialManager = ((AMActionForm)form).getIsCredManager();
/* 2050 */       if (isCredentialManager)
/*      */       {
/* 2052 */         credentialID = Long.valueOf(new Long(((AMActionForm)form).getCredentialID()).longValue());
/* 2053 */         credentialSelectedProperties = credUtil.rowNameVsValue(credentialID.longValue());
/*      */       }
/*      */       
/* 2056 */       if (choosehost.equals("-1"))
/*      */       {
/* 2058 */         hostname = ((AMActionForm)form).getHost();
/* 2059 */         monitoringmode = ((AMActionForm)form).getMonitoringmode();
/* 2060 */         if (isCredentialManager)
/*      */         {
/* 2062 */           username = credentialSelectedProperties.getProperty("username");
/* 2063 */           password = credentialSelectedProperties.getProperty("password");
/* 2064 */           if (credentialSelectedProperties.getProperty("prompt") != null)
/*      */           {
/* 2066 */             prompt = credentialSelectedProperties.getProperty("prompt");
/*      */           }
/* 2068 */           if (("ssh".equalsIgnoreCase(monitoringmode)) && (credentialSelectedProperties.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */           {
/* 2070 */             passphrase = credentialSelectedProperties.getProperty("passPhrase");
/*      */           }
/*      */           
/* 2073 */           ((AMActionForm)form).setIsCredManager(isCredentialManager);
/*      */         }
/*      */         else {
/* 2076 */           username = ((AMActionForm)form).getUsername();
/* 2077 */           password = ((AMActionForm)form).getPassword();
/* 2078 */           prompt = Translate.decode(((AMActionForm)form).getPrompt());
/*      */           
/* 2080 */           if (((AMActionForm)form).isSshkey())
/*      */           {
/* 2082 */             sshkey = ((AMActionForm)form).getDescription();
/* 2083 */             if (passphrase != null) {
/* 2084 */               passphrase = Translate.decode(passphrase);
/*      */             }
/*      */           }
/*      */           
/* 2088 */           if ((sshkey != null) && (sshkey.trim().equals("")))
/*      */           {
/* 2090 */             String hostdetails = "select MODE from AM_SCRIPTHOSTDETAILS where ID=" + choosehost;
/*      */             try
/*      */             {
/* 2093 */               AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2094 */               ResultSet rs = AMConnectionPool.executeQueryStmt(hostdetails);
/* 2095 */               if (rs.next())
/*      */               {
/*      */ 
/* 2098 */                 monitoringmode = rs.getString("MODE");
/*      */               }
/*      */             }
/*      */             catch (Exception ex)
/*      */             {
/* 2103 */               ex.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2109 */         port = ((AMActionForm)form).getPort();
/*      */       }
/*      */       else
/*      */       {
/* 2113 */         String hostdetails = "select HOSTNAME,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,MODE,PROMPT,PORT,USERNAME as SNMPCOMMUNITY from AM_SCRIPTHOSTDETAILS where ID=" + choosehost + " UNION select m.RESOURCENAME,h.USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,inf.MODE,h.PROMPT,inf.TELNETPORT,inf.SNMPCOMMUNITY as SNMPCOMMUNITY from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and m.RESOURCEID=" + choosehost;
/* 2114 */         AMLog.debug("Hostdetails Query is :" + hostdetails);
/*      */         try
/*      */         {
/* 2117 */           String snmpCommunity = "";
/* 2118 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2119 */           ResultSet rs = AMConnectionPool.executeQueryStmt(hostdetails);
/* 2120 */           if (rs.next())
/*      */           {
/* 2122 */             hostname = rs.getString("HOSTNAME");
/* 2123 */             username = rs.getString("USERNAME");
/* 2124 */             password = rs.getString("PASSWORD");
/* 2125 */             monitoringmode = rs.getString("MODE");
/* 2126 */             snmpCommunity = rs.getString("SNMPCOMMUNITY");
/* 2127 */             prompt = Translate.decode(rs.getString("PROMPT"));
/* 2128 */             port = rs.getString("PORT");
/*      */           }
/* 2130 */           if ("keyBasedAuth".equals(snmpCommunity)) {
/* 2131 */             monitoringmode = "SSH_KEY";
/*      */           }
/* 2133 */           if ((monitoringmode != null) && (monitoringmode.equals("SSH_KEY"))) {
/* 2134 */             passphrase = password;
/*      */           }
/* 2136 */           rs.close();
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 2140 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2145 */       if (script.indexOf("\\") != -1)
/* 2146 */         script = StrUtil.findReplace(script, "\\", "\\\\");
/*      */       CLIPerformDataCollectionIfcImpl perform;
/* 2148 */       if ((monitoringmode != null) && (monitoringmode.equals("WMI")) && (mtype != null) && (mtype.equals("file")))
/*      */       {
/* 2150 */         String tempfile = null;
/*      */         
/* 2152 */         if (AMFileDataCollector.checkPathContainsPatternForm(script)) {
/* 2153 */           tempfile = fdUtil.replaceDateFormatPattern(script);
/*      */         }
/*      */         else {
/* 2156 */           tempfile = script;
/*      */         }
/* 2158 */         output = wmi.getData(hostname, username, password, tempfile, new Vector(), "wmi_fileexists.vbs");
/*      */ 
/*      */       }
/* 2161 */       else if ((monitoringmode != null) && (monitoringmode.equals("WMI")) && (mtype != null) && (mtype.equals("directory"))) {
/* 2162 */         String tempfolder = null;
/* 2163 */         if (folder.indexOf("\\") != -1) {
/* 2164 */           tempfolder = StrUtil.findReplace(folder, "\\", "\\\\");
/*      */         }
/*      */         else {
/* 2167 */           tempfolder = folder;
/*      */         }
/* 2169 */         if (AMFileDataCollector.checkPathContainsPatternForm(tempfolder)) {
/* 2170 */           tempfolder = fdUtil.replaceDateFormatPattern(tempfolder);
/*      */         }
/* 2172 */         output = wmi.getData(hostname, username, password, tempfolder, new Vector(), "wmi_folderexists.vbs");
/*      */       }
/* 2174 */       else if ((monitoringmode != null) && (!monitoringmode.equals("WMI"))) {
/* 2175 */         perform = new CLIPerformDataCollectionIfcImpl();
/*      */       }
/*      */       
/*      */ 
/* 2179 */       Properties hostProps = new Properties();
/* 2180 */       hostProps.setProperty("targetAddress", hostname);
/* 2181 */       hostProps.setProperty("userName", username);
/* 2182 */       hostProps.setProperty("password", password);
/* 2183 */       hostProps.setProperty("prompt", prompt);
/* 2184 */       hostProps.setProperty("port", port);
/* 2185 */       hostProps.setProperty("MODE", monitoringmode);
/* 2186 */       hostProps.setProperty("debug", "true");
/* 2187 */       hostProps.setProperty("loginPrompt", ":");
/* 2188 */       hostProps.setProperty("passwordPrompt", ":");
/*      */       
/*      */ 
/* 2191 */       hostdetailsarray.add(hostname);
/* 2192 */       hostdetailsarray.add(username);
/* 2193 */       if ((passphrase != null) && (sshkey != null)) {
/* 2194 */         hostdetailsarray.add(passphrase);
/*      */       } else {
/* 2196 */         hostdetailsarray.add(password);
/*      */       }
/* 2198 */       String sshPKAuthChecked = null;
/* 2199 */       if ((monitoringmode != null) && (monitoringmode.equals("SSH")) && (sshkey != null))
/*      */       {
/* 2201 */         sshPKAuthChecked = "checked";
/* 2202 */         monitoringmode = "SSH_KEY";
/* 2203 */         hostProps.setProperty("privateKey", sshkey);
/* 2204 */         hostProps.setProperty("remotescript", "true");
/* 2205 */         hostProps.setProperty("SNMPCOMMUNITY", "keyBasedAuth");
/* 2206 */         hostProps.setProperty("sshPKAuthChecked", sshPKAuthChecked);
/* 2207 */         if (passphrase != null) {
/* 2208 */           hostProps.setProperty("password", passphrase);
/*      */         }
/*      */       }
/*      */       
/* 2212 */       if ((monitoringmode != null) && (monitoringmode.equals("SSH_KEY")) && (sshkey == null))
/*      */       {
/* 2214 */         sshPKAuthChecked = "checked";
/* 2215 */         monitoringmode = "SSH";
/* 2216 */         hostProps.setProperty("SNMPCOMMUNITY", "keyBasedAuth");
/*      */         
/* 2218 */         hostProps.setProperty("MODE", "SSH");
/* 2219 */         if (passphrase != null) {
/* 2220 */           hostProps.setProperty("password", passphrase);
/*      */         }
/*      */       }
/*      */       
/* 2224 */       hostdetailsarray.add(monitoringmode);
/* 2225 */       hostdetailsarray.add(prompt);
/* 2226 */       hostdetailsarray.add(port);
/* 2227 */       if (!monitoringmode.equals("WMI")) {
/*      */         try
/*      */         {
/* 2230 */           CLISession sess = null;
/*      */           
/*      */           try
/*      */           {
/* 2234 */             sess = CLITelnetHandler.getInstance().getCLISession(hostProps);
/*      */ 
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/* 2239 */             exc.printStackTrace();
/* 2240 */             if ((exc instanceof LoginException))
/*      */             {
/* 2242 */               output.put("failure", "Login Exception");
/* 2243 */               return output;
/*      */             }
/*      */             
/*      */ 
/* 2247 */             output.put("failure", "ConnectException");
/* 2248 */             return output;
/*      */           }
/*      */           
/* 2251 */           ArrayList ht = new ArrayList();
/* 2252 */           ht.add(prompt);
/* 2253 */           if (mtype.equals("file"))
/*      */           {
/*      */ 
/* 2256 */             String tempfile = null;
/* 2257 */             if (AMFileDataCollector.checkPathContainsPatternForm(script)) {
/* 2258 */               tempfile = fdUtil.replaceDateFormatPattern(folder);
/*      */             }
/*      */             else {
/* 2261 */               tempfile = folder;
/*      */             }
/* 2263 */             if (tempfile.indexOf("*") != -1) {
/* 2264 */               ht.clear();
/* 2265 */               String lastModFileCmd = "ls -t " + tempfile + " | head -1";
/* 2266 */               ht.add(lastModFileCmd);
/* 2267 */               output = CLITelnetHandler.getInstance().getCommandOutputforFile(sess, ht, timeout);
/* 2268 */               if (output.get(lastModFileCmd) != null) {
/* 2269 */                 tempfile = ((String)((Vector)output.get(lastModFileCmd)).lastElement()).trim();
/*      */               }
/* 2271 */               ht.clear();
/* 2272 */               ht.add(prompt);
/*      */             }
/* 2274 */             ht.add("file");
/* 2275 */             ht.add("test -f " + tempfile);
/* 2276 */             ht.add(exit_command);
/*      */           }
/* 2278 */           else if (mtype.equals("directory"))
/*      */           {
/* 2280 */             if (AMFileDataCollector.checkPathContainsPatternForm(folder)) {
/* 2281 */               folder = fdUtil.replaceDateFormatPattern(folder);
/*      */             }
/* 2283 */             ht.add("dir");
/* 2284 */             ht.add("cd " + folder);
/* 2285 */             ht.add(exit_command);
/*      */           }
/*      */           else
/*      */           {
/* 2289 */             if (script.indexOf("\\\\") != -1)
/*      */             {
/* 2291 */               script = StrUtil.findReplace(script, "\\\\", "\\");
/*      */             }
/* 2293 */             if (!isCommand) {
/* 2294 */               ht.add("ls " + script);
/* 2295 */               ht.add(exit_command);
/*      */             }
/*      */             else {
/* 2298 */               ht.add(script);
/* 2299 */               ht.add(exit_command);
/*      */             }
/* 2301 */             ht.add("cd " + folder);
/* 2302 */             ht.add(exit_command);
/*      */           }
/* 2304 */           ht.add(exit_command);
/*      */           
/*      */ 
/*      */ 
/* 2308 */           if (sess != null)
/*      */           {
/* 2310 */             if ((mtype != null) && ((mtype.equals("file")) || (mtype.equals("directory"))))
/*      */             {
/* 2312 */               output = CLITelnetHandler.getInstance().getCommandforFileCreation(sess, ht, timeout);
/*      */             }
/*      */             else
/*      */             {
/* 2316 */               output = CLITelnetHandler.getInstance().getCommandforScriptCreation(sess, ht, timeout);
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/* 2321 */             output.put("failure", "Unable to get Session");
/*      */           }
/* 2323 */           if (sess != null)
/*      */           {
/*      */ 
/* 2326 */             CLITelnetHandler.getInstance().close(sess);
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2332 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/* 2336 */     return output;
/*      */   }
/*      */   
/*      */   protected static ArrayList getAttributes(String attribute)
/*      */   {
/* 2341 */     ArrayList al = new ArrayList();
/* 2342 */     if (attribute != null)
/*      */     {
/* 2344 */       StringTokenizer newliner = new StringTokenizer(attribute, "\n");
/* 2345 */       while (newliner.hasMoreTokens())
/*      */       {
/* 2347 */         String att = newliner.nextToken();
/* 2348 */         if ((att != null) && (!att.trim().equals("")))
/*      */         {
/* 2350 */           al.add(att.trim());
/*      */         }
/*      */       }
/*      */     }
/* 2354 */     return al;
/*      */   }
/*      */   
/*      */   public static boolean checkarguments(String results)
/*      */   {
/*      */     try
/*      */     {
/* 2361 */       StringTokenizer st = new StringTokenizer(results, "\n");
/* 2362 */       while (st.hasMoreTokens())
/*      */       {
/* 2364 */         String temp = st.nextToken();
/* 2365 */         if (temp.indexOf("=") == -1)
/*      */         {
/* 2367 */           return false;
/*      */         }
/*      */       }
/* 2370 */       return true;
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2374 */       exc.printStackTrace(); }
/* 2375 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String[] getFilename(String filename, String serversite)
/*      */   {
/* 2382 */     String[] toreturn = new String[2];
/* 2383 */     if ((serversite != null) && (serversite.equals("2")))
/*      */     {
/* 2385 */       if ((filename.indexOf("/") == -1) && (filename.indexOf("\\") == -1)) {
/* 2386 */         return new String[] { "", filename };
/*      */       }
/* 2388 */       if (filename.startsWith("/"))
/*      */       {
/*      */         try {
/* 2391 */           int index = filename.lastIndexOf("/");
/*      */           
/* 2393 */           if (index == 0)
/*      */           {
/* 2395 */             toreturn[0] = filename.substring(0, 1);
/* 2396 */             toreturn[1] = filename.substring(index + 1);
/*      */           }
/*      */           else
/*      */           {
/* 2400 */             toreturn[0] = filename.substring(0, index - 1);
/* 2401 */             toreturn[1] = filename.substring(index + 1);
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/* 2405 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2410 */         int index = filename.lastIndexOf("\\");
/* 2411 */         if (index == 0)
/*      */         {
/* 2413 */           toreturn[0] = filename.substring(0, 1);
/* 2414 */           toreturn[1] = filename.substring(index + 1);
/*      */         }
/*      */         else
/*      */         {
/* 2418 */           toreturn[0] = filename.substring(0, index - 1);
/* 2419 */           toreturn[1] = filename.substring(index + 1);
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     }
/* 2425 */     else if ((System.getProperty("os.name").startsWith("windows")) || (System.getProperty("os.name").startsWith("Windows")))
/*      */     {
/* 2427 */       if ((filename.indexOf("/") == -1) && (filename.indexOf("\\") == -1)) {
/* 2428 */         return new String[] { "", filename };
/*      */       }
/*      */       try {
/* 2431 */         int index = filename.lastIndexOf("\\");
/* 2432 */         toreturn[0] = filename.substring(0, index - 1);
/* 2433 */         toreturn[1] = filename.substring(index + 1);
/*      */       }
/*      */       catch (Exception ex) {
/* 2436 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/* 2443 */         int index = filename.lastIndexOf("/");
/* 2444 */         if (index == 0)
/*      */         {
/* 2446 */           toreturn[0] = filename.substring(0, 1);
/* 2447 */           toreturn[1] = filename.substring(index + 1);
/*      */         }
/*      */         else {
/* 2450 */           toreturn[0] = filename.substring(0, index - 1);
/* 2451 */           toreturn[1] = filename.substring(index + 1);
/*      */         }
/*      */       }
/*      */       catch (Exception ex) {
/* 2455 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 2459 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static int serversite_checkexists(String serverpath, String arguements) {
/* 2463 */     int toreturn = 4;
/* 2464 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2465 */     String query = "select serverpath,arguements from AM_ScriptArgs where serverpath='" + serverpath + "'";
/* 2466 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2469 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2470 */       int i; if (rs.next())
/*      */       {
/* 2472 */         if (rs.getString(2).equals(arguements))
/*      */         {
/* 2474 */           return 5;
/*      */         }
/*      */         
/*      */ 
/* 2478 */         while (rs.next())
/*      */         {
/* 2480 */           if (rs.getString(2).equals(arguements))
/*      */           {
/* 2482 */             return 5;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2489 */         return 4;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2494 */       System.out.println("No script exists");
/* 2495 */       return 4;
/*      */ 
/*      */     }
/*      */     catch (Exception e1)
/*      */     {
/*      */ 
/* 2501 */       e1.printStackTrace();
/* 2502 */       return 5;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2508 */         rs.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2520 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2521 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/* 2522 */     AMActionForm amf = new AMActionForm();
/* 2523 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2524 */     String type = request.getParameter("type");
/*      */     
/* 2526 */     request.setAttribute("baseid", request.getParameter("baseid"));
/* 2527 */     int resourceid = Integer.parseInt(request.getParameter("resourceid"));
/* 2528 */     String selectedscheme = "";
/* 2529 */     if (request.getParameter("selectedscheme") != null)
/*      */     {
/* 2531 */       selectedscheme = request.getParameter("selectedscheme");
/* 2532 */       request.setAttribute("selectedscheme", selectedscheme);
/*      */     }
/* 2534 */     String selectedSkin = "Grey";
/* 2535 */     if (request.getParameter("selectedSkin") != null)
/*      */     {
/* 2537 */       selectedSkin = request.getParameter("selectedSkin");
/* 2538 */       request.setAttribute("selectedskin", selectedSkin);
/*      */     }
/* 2540 */     ResultSet rs = null;
/* 2541 */     ResultSet rs1 = null;
/* 2542 */     ResultSet rs2 = null;
/* 2543 */     String query = null;
/* 2544 */     if ((type.equals("File System Monitor")) || (type.equals("file")) || (type.equals("directory"))) {
/* 2545 */       query = " select DISPLAYNAME,FILE_DIR,MTYPE,POLLINTERVAL,SERVERSITE,CONTENT,TIMEOUT,CONCHECK_MONALERT,CONCHECK_RULETYPE,FILECHECKTYPE,FILEDIRAGE_MONALERT,FILEDIRAGE_FILECHANGESTATUS,FILEDIRAGE_TIMELIMIT,FILEDIRAGE_TIMEUNIT,SUBDIRECTORYCHK,REGEXCHECK,CONCHECK_FAILURE_ALERT_CONDN,CONCHECK_FAILURE_ALERT_RULETYPE,CONCHECK_FAILURE_ALERT_CONTENT,CONCHECK_FAILURE_ALERT_REGEX,CHECKEMPTY from AM_ManagedObject,AM_FILEDIR where AM_ManagedObject.RESOURCEID=AM_FILEDIR.RESOURCEID and AM_ManagedObject.RESOURCEID =" + resourceid;
/*      */     }
/*      */     else {
/* 2548 */       query = "select * from AM_ScriptArgs where resourceid=" + resourceid;
/*      */     }
/* 2550 */     String haid = null;
/*      */     try
/*      */     {
/* 2553 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2554 */       request.setAttribute("resultset", rs);
/* 2555 */       if (rs.next())
/*      */       {
/* 2557 */         if ((type.equals("File System Monitor")) || (type.equals("file")) || (type.equals("directory"))) {
/* 2558 */           String displayname = rs.getString("DISPLAYNAME");
/* 2559 */           ((AMActionForm)form).setDisplayname(displayname);
/* 2560 */           ((AMActionForm)form).setFilepath(rs.getString("FILE_DIR"));
/* 2561 */           ((AMActionForm)form).setMtype(rs.getString("MTYPE"));
/* 2562 */           ((AMActionForm)form).setPollInterval(rs.getInt("POLLINTERVAL") / 60);
/* 2563 */           ((AMActionForm)form).setTimeout(rs.getInt("TIMEOUT"));
/*      */           
/* 2565 */           request.setAttribute("resourcename", displayname);
/*      */           
/*      */ 
/* 2568 */           String con = rs.getString("CONTENT");
/* 2569 */           int RegexCheck = rs.getInt("REGEXCHECK");
/* 2570 */           int conChk_MonAlert = rs.getInt("CONCHECK_MONALERT");
/* 2571 */           String conChk_ruleType = rs.getString("CONCHECK_RULETYPE");
/* 2572 */           int fCheckType = rs.getInt("FILECHECKTYPE");
/* 2573 */           int checkEmpty = rs.getInt("CHECKEMPTY");
/*      */           
/* 2575 */           String clearConditionCountVal = "1";
/* 2576 */           int clearCondition = rs.getInt("CONCHECK_FAILURE_ALERT_CONDN");
/* 2577 */           String clearConditionRuleType = rs.getString("CONCHECK_FAILURE_ALERT_RULETYPE");
/* 2578 */           String clearConditionContent = rs.getString("CONCHECK_FAILURE_ALERT_CONTENT");
/* 2579 */           int clearConditionRegEx = rs.getInt("CONCHECK_FAILURE_ALERT_REGEX");
/*      */           
/* 2581 */           String contentChk = null;
/* 2582 */           if ((con == null) || (con.equals("null")) || (con.equals(""))) {
/* 2583 */             con = "";
/* 2584 */             conChk_MonAlert = -1;
/* 2585 */             conChk_ruleType = null;
/* 2586 */             RegexCheck = 0;
/* 2587 */             checkEmpty = 0;
/* 2588 */             clearCondition = 0;
/* 2589 */             clearConditionRuleType = "0";
/* 2590 */             clearConditionCountVal = "1";
/* 2591 */             clearConditionContent = "";
/* 2592 */             clearConditionRegEx = 0;
/*      */           }
/*      */           else {
/* 2595 */             contentChk = "on";
/* 2596 */             if (RegexCheck == 1) {
/* 2597 */               ((AMActionForm)form).setRegexChk("on");
/* 2598 */               conChk_ruleType = null;
/*      */             }
/* 2600 */             if ((conChk_ruleType == null) || (conChk_ruleType.equals("null")) || (conChk_ruleType.equals("All"))) {
/* 2601 */               ((AMActionForm)form).setSelectRuleType("1");
/*      */ 
/*      */ 
/*      */             }
/* 2605 */             else if (Integer.parseInt(conChk_ruleType) >= 1) {
/* 2606 */               ((AMActionForm)form).setSelectRuleType("0");
/* 2607 */               ((AMActionForm)form).setCountval(conChk_ruleType);
/*      */             }
/*      */             
/* 2610 */             if ((clearConditionRuleType != null) && ("All".equalsIgnoreCase(clearConditionRuleType))) {
/* 2611 */               clearConditionRuleType = "1";
/*      */             } else {
/* 2613 */               clearConditionCountVal = clearConditionRuleType;
/* 2614 */               clearConditionRuleType = "0";
/*      */             }
/* 2616 */             if (clearConditionContent == null) {
/* 2617 */               clearConditionContent = "";
/*      */             }
/*      */           }
/* 2620 */           ((AMActionForm)form).setContentChk(contentChk);
/* 2621 */           ((AMActionForm)form).setCcontent(con);
/* 2622 */           if ((con != null) && (!con.equals("")) && (conChk_MonAlert == -1)) {
/* 2623 */             ((AMActionForm)form).setSelectStatusType(0);
/* 2624 */             ((AMActionForm)form).setFileCheckType(0);
/*      */           } else {
/* 2626 */             ((AMActionForm)form).setSelectStatusType(conChk_MonAlert);
/* 2627 */             ((AMActionForm)form).setFileCheckType(fCheckType);
/*      */           }
/*      */           
/* 2630 */           ((AMActionForm)form).setClearCondition(clearCondition);
/* 2631 */           ((AMActionForm)form).setClearConditionRuleType(clearConditionRuleType);
/* 2632 */           ((AMActionForm)form).setClearConditionCountVal(clearConditionCountVal);
/* 2633 */           ((AMActionForm)form).setClearConditionContent(clearConditionContent);
/* 2634 */           if (clearConditionRegEx == 0) {
/* 2635 */             ((AMActionForm)form).setClearConditionRegEx("off");
/*      */           } else {
/* 2637 */             ((AMActionForm)form).setClearConditionRegEx("on");
/*      */           }
/* 2639 */           if (checkEmpty == 0)
/*      */           {
/* 2641 */             ((AMActionForm)form).setCheckEmpty("off");
/*      */           }
/*      */           else
/*      */           {
/* 2645 */             ((AMActionForm)form).setCheckEmpty("on");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/* 2651 */           int fileDirAge_MonAlert = rs.getInt("FILEDIRAGE_MONALERT");
/* 2652 */           int fileDirAge_Changetype = rs.getInt("FILEDIRAGE_FILECHANGESTATUS");
/* 2653 */           int fileDirAge_TimeLimit = rs.getInt("FILEDIRAGE_TIMELIMIT");
/* 2654 */           String fileDirAge_TimeUnit = rs.getString("FILEDIRAGE_TIMEUNIT");
/* 2655 */           String fileDirAgeChk = null;
/* 2656 */           if ((fileDirAge_MonAlert != 0) && (fileDirAge_MonAlert != 1)) {
/* 2657 */             fileDirAge_MonAlert = -1;
/* 2658 */             fileDirAge_Changetype = -1;
/* 2659 */             fileDirAge_TimeLimit = 10;
/* 2660 */             fileDirAge_TimeUnit = null;
/*      */           }
/*      */           else {
/* 2663 */             fileDirAgeChk = "on";
/*      */           }
/*      */           
/* 2666 */           ((AMActionForm)form).setFileDirAge(fileDirAgeChk);
/* 2667 */           ((AMActionForm)form).setSelectMonStatus(fileDirAge_MonAlert);
/* 2668 */           ((AMActionForm)form).setSelectChangeType(fileDirAge_Changetype);
/* 2669 */           ((AMActionForm)form).setTimeval(fileDirAge_TimeLimit);
/* 2670 */           ((AMActionForm)form).setTimeUnit(fileDirAge_TimeUnit);
/* 2671 */           AMLog.debug("After : fileDirAgeChk :" + fileDirAgeChk + "\tfileDirAge_MonAlert :" + fileDirAge_MonAlert + "\tfileDirAge_Changetype :" + fileDirAge_Changetype + "\tfileDirAge_TimeLimit :" + fileDirAge_TimeLimit);
/*      */           
/*      */ 
/*      */ 
/* 2675 */           int subdDirCntChk = rs.getInt("SUBDIRECTORYCHK");
/* 2676 */           String subDirChk = null;
/* 2677 */           if (subdDirCntChk == 1) {
/* 2678 */             subDirChk = "on";
/*      */           }
/* 2680 */           ((AMActionForm)form).setSubDirCntChk(subDirChk);
/*      */         }
/*      */         else {
/* 2683 */           Properties ess_atts = new Properties();
/* 2684 */           ess_atts.setProperty("Availability", "2200");
/* 2685 */           ess_atts.setProperty("Health", "2201");
/* 2686 */           ess_atts.setProperty("ResponseTime", "2202");
/* 2687 */           if (!type.equals("Script Monitor"))
/*      */           {
/* 2689 */             ess_atts = DataCollectionControllerUtil.getAttributeProps(type);
/*      */           }
/* 2691 */           request.setAttribute("ess_atts", ess_atts);
/* 2692 */           request.setAttribute("resourcename", rs.getString("displayname"));
/* 2693 */           ((AMActionForm)form).setDisplayname(rs.getString("displayname"));
/*      */           
/* 2695 */           String opfile = rs.getString("opfile");
/* 2696 */           if ("-1".equals(opfile)) {
/* 2697 */             ((AMActionForm)form).setOutputfile("");
/*      */           } else {
/* 2699 */             ((AMActionForm)form).setOutputfile(rs.getString("opfile"));
/*      */           }
/*      */           
/* 2702 */           request.setAttribute("opfile", opfile);
/* 2703 */           ((AMActionForm)form).setTimeout(rs.getInt("timeout"));
/*      */           
/* 2705 */           ((AMActionForm)form).setPollInterval(Integer.parseInt(rs.getString("pollinterval")) / 60);
/* 2706 */           ((AMActionForm)form).setServerpath(rs.getString("serverpath"));
/* 2707 */           ((AMActionForm)form).setWorkingdirectory(rs.getString("executiondirectory"));
/* 2708 */           String mode = rs.getString("mode");
/* 2709 */           if ("-1".equals(mode)) {
/* 2710 */             ((AMActionForm)form).setMode("");
/* 2711 */             ((AMActionForm)form).setisCommand(true);
/*      */           } else {
/* 2713 */             ((AMActionForm)form).setMode(rs.getString("mode"));
/*      */           }
/* 2715 */           ((AMActionForm)form).setWorkingdirectory(rs.getString("executiondirectory"));
/* 2716 */           String opdelimiter_new = "";
/* 2717 */           if ((rs.getString("opdelimiter") == null) || (rs.getString("opdelimiter").equals("s")))
/*      */           {
/* 2719 */             opdelimiter_new = "";
/*      */           }
/*      */           else
/*      */           {
/* 2723 */             opdelimiter_new = rs.getString("opdelimiter");
/*      */           }
/* 2725 */           ((AMActionForm)form).setDelimiter(opdelimiter_new);
/* 2726 */           ArrayList string_numeric = getStringNumeric(resourceid);
/* 2727 */           ((AMActionForm)form).setString_att((String)string_numeric.get(0));
/* 2728 */           ((AMActionForm)form).setNumeric_att((String)string_numeric.get(1));
/* 2729 */           ((AMActionForm)form).setMessage(rs.getString("arguements"));
/*      */           
/* 2731 */           boolean isOutputSettings = false;
/* 2732 */           boolean isStringAttr = true;boolean isNumAttr = true;
/*      */           
/* 2734 */           if ((string_numeric.get(0) == null) || ("null".equals(string_numeric.get(0))) || ("".equals(string_numeric.get(0)))) {
/* 2735 */             isStringAttr = false;
/*      */           }
/* 2737 */           if ((string_numeric.get(1) == null) || ("null".equals(string_numeric.get(1))) || ("".equals(string_numeric.get(1)))) {
/* 2738 */             isNumAttr = false;
/*      */           }
/* 2740 */           if ((isStringAttr) || (isNumAttr)) {
/* 2741 */             isOutputSettings = true;
/*      */           }
/* 2743 */           ((AMActionForm)form).setOpfile(isOutputSettings);
/*      */         }
/*      */         
/* 2746 */         boolean ss = false;
/* 2747 */         if (rs.getString("serversite").equals("1"))
/* 2748 */           ss = true;
/* 2749 */         ((AMActionForm)form).setSslenabled(ss);
/* 2750 */         if (ss)
/*      */         {
/*      */ 
/* 2753 */           ((AMActionForm)form).setServersite("local");
/*      */         }
/*      */         else
/*      */         {
/* 2757 */           String host_query = null;
/*      */           
/* 2759 */           ((AMActionForm)form).setServersite("remote");
/* 2760 */           String localhostaddr = null;
/*      */           try {
/* 2762 */             localhostaddr = InetAddress.getLocalHost().getHostName();
/*      */           } catch (Exception e) {
/* 2764 */             e.printStackTrace();
/* 2765 */             AMLog.debug("Unknown Host" + e.getMessage());
/*      */           }
/* 2767 */           if ((type.equals("File System Monitor")) || (type.equals("file")) || (type.equals("directory")))
/*      */           {
/* 2769 */             host_query = "select ID,HOSTNAME,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD, MODE,PROMPT,PORT from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY','WMI') UNION select m.RESOURCEID,m.RESOURCENAME,h.USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,inf.MODE,h.PROMPT,inf.TELNETPORT from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY','WMI') and h.RESOURCENAME not like '" + localhostaddr + "%' ";
/*      */           }
/*      */           else {
/* 2772 */             host_query = "select ID,HOSTNAME,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD, MODE,PROMPT,PORT from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') UNION select m.RESOURCEID,m.RESOURCENAME,h.USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,inf.MODE,h.PROMPT,inf.TELNETPORT from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%'";
/*      */           }
/*      */           
/* 2775 */           rs1 = AMConnectionPool.executeQueryStmt(host_query);
/* 2776 */           if (rs1.next())
/*      */           {
/* 2778 */             ((AMActionForm)form).setHost(rs1.getString("HOSTNAME"));
/* 2779 */             ((AMActionForm)form).setPort(rs1.getString("PORT"));
/* 2780 */             ((AMActionForm)form).setPrompt(Translate.decode(rs1.getString("PROMPT")));
/* 2781 */             ((AMActionForm)form).setUsername(rs1.getString("USERNAME"));
/*      */             
/* 2783 */             ((AMActionForm)form).setMonitoringmode(rs1.getString("MODE"));
/*      */           }
/*      */           else
/*      */           {
/* 2787 */             ((AMActionForm)form).setChoosehost("-1");
/*      */           }
/* 2789 */           String host_mapper = "select * from AM_SCRIPTHOST_MAPPER WHERE SCRIPTID=" + resourceid;
/* 2790 */           AMLog.debug("host_mapper :" + host_mapper);
/* 2791 */           rs2 = AMConnectionPool.executeQueryStmt(host_mapper);
/* 2792 */           if (rs2.next())
/*      */           {
/* 2794 */             int hostid = Integer.parseInt(rs2.getString("HOSTID"));
/* 2795 */             String mapperid = null;
/* 2796 */             if (hostid > 100000) {
/* 2797 */               mapperid = "2," + rs2.getString("HOSTID");
/*      */             } else {
/* 2799 */               mapperid = "1," + rs2.getString("HOSTID");
/*      */             }
/* 2801 */             ((AMActionForm)form).setChoosehost(mapperid);
/*      */           }
/*      */           else
/*      */           {
/* 2805 */             ((AMActionForm)form).setChoosehost("-1");
/*      */           }
/*      */         }
/* 2808 */         setTableParams(request, resourceid);
/*      */       }
/*      */       else
/*      */       {
/* 2812 */         System.out.println("There is no entry for the script with this resourceid");
/*      */       }
/*      */       try
/*      */       {
/* 2816 */         rs.close();
/* 2817 */         rs1.close();
/* 2818 */         rs2.close();
/*      */ 
/*      */       }
/*      */       catch (Exception exc) {}
/*      */ 
/*      */     }
/*      */     catch (SQLException exc)
/*      */     {
/*      */ 
/* 2827 */       exc.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/* 2831 */     request.setAttribute("type", "Script Monitor");
/* 2832 */     String typedisplayname = ShowResourceDetails.getDisplayName(type);
/* 2833 */     request.setAttribute("typedisplayname", typedisplayname);
/* 2834 */     return mapping.findForward("scriptconf");
/*      */   }
/*      */   
/*      */   public static void setTableParams(HttpServletRequest request, int resourceid)
/*      */   {
/* 2839 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2840 */     Hashtable table_atts = new Hashtable();
/* 2841 */     ArrayList tableids = new ArrayList();
/*      */     try
/*      */     {
/* 2844 */       String qry = "select TABLENAME,TABLEID,PRIMARY_COLUMN,COL_DELIMITER  from AM_SCRIPT_TABLES where SCRIPTID=" + resourceid;
/* 2845 */       ResultSet tab_rs = AMConnectionPool.executeQueryStmt(qry);
/* 2846 */       while (tab_rs.next())
/*      */       {
/* 2848 */         Hashtable temp = new Hashtable();
/* 2849 */         String query1 = "select ATTRIBUTEID,ATTRIBUTENAME,TYPE from AM_CAM_DC_ATTRIBUTES WHERE GROUPID=" + tab_rs.getString("TABLEID") + " and TYPE!=2 and TYPE!=1";
/* 2850 */         ResultSet att_rs = AMConnectionPool.executeQueryStmt(query1);
/* 2851 */         String satts = "";
/* 2852 */         String natts = "";
/* 2853 */         if (att_rs.next())
/*      */         {
/* 2855 */           if (att_rs.getString("TYPE").equals("0"))
/*      */           {
/* 2857 */             natts = att_rs.getString("ATTRIBUTENAME");
/*      */           }
/*      */           else
/*      */           {
/* 2861 */             satts = att_rs.getString("ATTRIBUTENAME");
/*      */           }
/*      */         }
/* 2864 */         while (att_rs.next())
/*      */         {
/* 2866 */           if (att_rs.getString("TYPE").equals("0"))
/*      */           {
/* 2868 */             natts = natts + "\n" + att_rs.getString("ATTRIBUTENAME");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 2873 */             satts = satts + "\n" + att_rs.getString("ATTRIBUTENAME");
/*      */           }
/*      */         }
/*      */         
/* 2877 */         closeResultSet(att_rs);
/* 2878 */         temp.put("name", tab_rs.getString("TABLENAME").trim());
/* 2879 */         temp.put("strings", satts.trim());
/* 2880 */         temp.put("numerics", natts.trim());
/* 2881 */         if (tab_rs.getString("COL_DELIMITER").equals("t"))
/*      */         {
/* 2883 */           temp.put("delimiter", "\\t");
/*      */         }
/*      */         else
/*      */         {
/* 2887 */           temp.put("delimiter", tab_rs.getString("COL_DELIMITER"));
/*      */         }
/* 2889 */         String col_list = "";
/* 2890 */         String pcol = tab_rs.getString("PRIMARY_COLUMN");
/*      */         try
/*      */         {
/* 2893 */           StringTokenizer st = new StringTokenizer(pcol, "#");
/* 2894 */           while (st.hasMoreTokens())
/*      */           {
/* 2896 */             col_list = col_list + "\n" + st.nextToken();
/*      */           }
/* 2898 */           col_list.trim();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/* 2904 */         temp.put("primary", col_list);
/* 2905 */         table_atts.put(tab_rs.getString("TABLEID"), temp);
/* 2906 */         tableids.add(tab_rs.getString("TABLEID"));
/*      */       }
/* 2908 */       closeResultSet(tab_rs);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2912 */       exc.printStackTrace();
/*      */     }
/* 2914 */     request.setAttribute("table_atts", table_atts);
/* 2915 */     request.setAttribute("tableids", tableids);
/*      */   }
/*      */   
/*      */   public ActionForward updateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2920 */     String type = ((AMActionForm)form).getType();
/* 2921 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2922 */     String mtype = request.getParameter("mtype");
/* 2923 */     String dname = null;
/* 2924 */     int resourceid = Integer.parseInt(String.valueOf(request.getParameter("resourceid")));
/* 2925 */     String choosehost = ((AMActionForm)form).getChoosehost();
/* 2926 */     if ((choosehost != null) && (choosehost.contains(","))) {
/* 2927 */       StringTokenizer st = new StringTokenizer(choosehost);
/* 2928 */       while (st.hasMoreTokens()) {
/* 2929 */         choosehost = st.nextToken(",");
/*      */       }
/* 2931 */       ((AMActionForm)form).setChoosehost(choosehost);
/*      */     }
/*      */     
/* 2934 */     if ((type.equals("File System Monitor")) || (type.equals("file")) || (type.equals("directory"))) {
/* 2935 */       dname = ((AMActionForm)form).getDisplayname();
/* 2936 */       String file_dir = ((AMActionForm)form).getFilepath();
/* 2937 */       String file_dirPath = FormatUtil.findReplace(file_dir, "\\", "\\\\");
/*      */       
/* 2939 */       String pollinterval = String.valueOf(((AMActionForm)form).getPollInterval() * 60);
/*      */       
/* 2941 */       String serversite = ((AMActionForm)form).getServersite();
/* 2942 */       int timeout = ((AMActionForm)form).getTimeout();
/* 2943 */       if (serversite.equals("remote"))
/*      */       {
/* 2945 */         serversite = "2";
/*      */       }
/*      */       else {
/* 2948 */         serversite = "1";
/*      */       }
/*      */       
/* 2951 */       String content = "";String conChk_ruleType = null;String regexpChk = null;
/* 2952 */       int conChk_MonAlert = -1;int fCheckType = -1;int RegexCheck = 0;
/* 2953 */       String contentChk = ((AMActionForm)form).getContentChk();
/*      */       
/* 2955 */       int intClearConditionRegEx = 0;
/* 2956 */       int clearCondition = 0;
/* 2957 */       String clearConditionRuleType = "0";
/* 2958 */       String clearConditionCountVal = "1";
/* 2959 */       String clearConditionContent = "";
/* 2960 */       String clearConditionRegEx = "0";
/* 2961 */       int checkEmpty = 0;
/* 2962 */       if ((mtype.equals("file")) && (contentChk != null) && (contentChk.equalsIgnoreCase("on"))) {
/* 2963 */         content = ((AMActionForm)form).getCcontent();
/* 2964 */         regexpChk = ((AMActionForm)form).getRegexChk();
/* 2965 */         fCheckType = ((AMActionForm)form).getFileCheckType();
/* 2966 */         conChk_MonAlert = ((AMActionForm)form).getSelectStatusType();
/* 2967 */         String strCheckEmpty = ((AMActionForm)form).getCheckEmpty();
/* 2968 */         checkEmpty = "on".equalsIgnoreCase(strCheckEmpty) ? 1 : 0;
/* 2969 */         if ("on".equalsIgnoreCase(regexpChk)) {
/* 2970 */           RegexCheck = 1;
/*      */         }
/*      */         else {
/* 2973 */           conChk_ruleType = ((AMActionForm)form).getSelectRuleType();
/* 2974 */           String countVal = ((AMActionForm)form).getCountval();
/* 2975 */           if (conChk_ruleType.equals("0")) {
/* 2976 */             conChk_ruleType = countVal;
/*      */           }
/*      */           else {
/* 2979 */             conChk_ruleType = "All";
/*      */           }
/*      */         }
/* 2982 */         clearCondition = ((AMActionForm)form).getClearCondition();
/* 2983 */         clearConditionRuleType = ((AMActionForm)form).getClearConditionRuleType();
/* 2984 */         clearConditionCountVal = ((AMActionForm)form).getClearConditionCountVal();
/* 2985 */         clearConditionContent = ((AMActionForm)form).getClearConditionContent();
/* 2986 */         clearConditionRegEx = ((AMActionForm)form).getClearConditionRegEx();
/* 2987 */         if (clearConditionRuleType.equals("0")) {
/* 2988 */           clearConditionRuleType = clearConditionCountVal;
/*      */         } else {
/* 2990 */           clearConditionRuleType = "All";
/*      */         }
/* 2992 */         if ((clearConditionRegEx != null) && (clearConditionRegEx.equalsIgnoreCase("on"))) {
/* 2993 */           intClearConditionRegEx = 1;
/*      */         }
/* 2995 */         if (clearConditionContent == null) {
/* 2996 */           clearConditionContent = "";
/*      */         }
/* 2998 */         if (clearCondition == 0) {
/* 2999 */           clearConditionRuleType = "0";
/* 3000 */           clearConditionContent = "";
/* 3001 */           intClearConditionRegEx = 0;
/*      */         }
/*      */       }
/* 3004 */       String fileDirAgeChk = ((AMActionForm)form).getFileDirAge();
/* 3005 */       AMLog.debug("fileDirAgeChk :" + fileDirAgeChk);
/* 3006 */       int fileDirAge_MonAlert = -1;
/* 3007 */       int fileDirAge_ChangeType = -1;
/* 3008 */       int fileDirAge_timeVal = 10;
/* 3009 */       String fileDirAge_timeUnit = null;
/*      */       
/* 3011 */       AMLog.debug("\tfileDirAgeChk :" + fileDirAgeChk + "\tfileDirAge_MonAlert :" + fileDirAge_MonAlert + "\tfileDirAge_ChangeType :" + fileDirAge_ChangeType + "\tfileDirAge_timeVal :" + fileDirAge_timeVal);
/* 3012 */       if ((fileDirAgeChk != null) && (fileDirAgeChk.equalsIgnoreCase("on")))
/*      */       {
/* 3014 */         fileDirAge_MonAlert = ((AMActionForm)form).getSelectMonStatus();
/* 3015 */         fileDirAge_ChangeType = ((AMActionForm)form).getSelectChangeType();
/* 3016 */         fileDirAge_timeVal = ((AMActionForm)form).getTimeval();
/* 3017 */         fileDirAge_timeUnit = ((AMActionForm)form).getTimeUnit();
/*      */       }
/* 3019 */       int subDirChkVal = 0;
/* 3020 */       String subDirChk = ((AMActionForm)form).getSubDirCntChk();
/* 3021 */       if ((subDirChk != null) && (subDirChk.equalsIgnoreCase("on"))) {
/* 3022 */         subDirChkVal = 1;
/*      */       }
/*      */       
/* 3025 */       PreparedStatement ps = null;
/*      */       try {
/* 3027 */         AMConnectionPool.getInstance();ps = AMConnectionPool.getPreparedStatement("update AM_FILEDIR set FILE_DIR=?,POLLINTERVAL=?,SERVERSITE=?,CONTENT=?,TIMEOUT=?,CONCHECK_MONALERT=?,CONCHECK_RULETYPE=?,FILECHECKTYPE=?,FILEDIRAGE_MONALERT=?,FILEDIRAGE_FILECHANGESTATUS=?,FILEDIRAGE_TIMELIMIT=?,FILEDIRAGE_TIMEUNIT =?,SUBDIRECTORYCHK=?,REGEXCHECK=?,CONCHECK_FAILURE_ALERT_CONDN=?,CONCHECK_FAILURE_ALERT_RULETYPE=?,CONCHECK_FAILURE_ALERT_CONTENT=?,CONCHECK_FAILURE_ALERT_REGEX=?,CHECKEMPTY=? where RESOURCEID=" + resourceid);
/* 3028 */         String temp_query = "update AM_ManagedObject set RESOURCENAME='" + file_dirPath + "',displayname='" + dname + "' where resourceid=" + resourceid;
/* 3029 */         AMConnectionPool.executeUpdateStmt(temp_query);
/* 3030 */         String updatquery = "update AM_FILEDIR set FILE_DIR='" + file_dir + "',POLLINTERVAL=" + pollinterval + ",SERVERSITE='" + serversite + "',CONTENT='" + content + "',TIMEOUT=" + timeout + ",CONCHECK_MONALERT=" + conChk_MonAlert + ",CONCHECK_RULETYPE='" + conChk_ruleType + "',FILECHECKTYPE=" + fCheckType + ",FILEDIRAGE_MONALERT=" + fileDirAge_MonAlert + ",FILEDIRAGE_FILECHANGESTATUS=" + fileDirAge_ChangeType + ",FILEDIRAGE_TIMELIMIT=" + fileDirAge_timeVal + ",FILEDIRAGE_TIMEUNIT ='" + fileDirAge_timeUnit + "',SUBDIRECTORYCHK=" + subDirChkVal + ",REGEXCHECK=" + RegexCheck + ",CONCHECK_FAILURE_ALERT_CONDN=" + clearCondition + ",CONCHECK_FAILURE_ALERT_RULETYPE=" + clearConditionRuleType + ",CONCHECK_FAILURE_ALERT_CONTENT=" + clearConditionContent + ",CONCHECK_FAILURE_ALERT_REGEX=" + intClearConditionRegEx + ",CHECKEMPTY=" + checkEmpty + " where RESOURCEID=" + resourceid;
/*      */         
/* 3032 */         ps.setString(1, file_dir);
/* 3033 */         ps.setInt(2, Integer.parseInt(pollinterval));
/* 3034 */         ps.setString(3, serversite);
/* 3035 */         ps.setString(4, content);
/* 3036 */         ps.setInt(5, timeout);
/* 3037 */         ps.setInt(6, conChk_MonAlert);
/* 3038 */         ps.setString(7, conChk_ruleType);
/* 3039 */         ps.setInt(8, fCheckType);
/* 3040 */         ps.setInt(9, fileDirAge_MonAlert);
/* 3041 */         ps.setInt(10, fileDirAge_ChangeType);
/* 3042 */         ps.setInt(11, fileDirAge_timeVal);
/* 3043 */         ps.setString(12, fileDirAge_timeUnit);
/* 3044 */         ps.setInt(13, subDirChkVal);
/* 3045 */         ps.setInt(14, RegexCheck);
/* 3046 */         ps.setInt(15, clearCondition);
/* 3047 */         ps.setString(16, clearConditionRuleType);
/* 3048 */         ps.setString(17, clearConditionContent);
/* 3049 */         ps.setInt(18, intClearConditionRegEx);
/* 3050 */         ps.setInt(19, checkEmpty);
/* 3051 */         ps.execute();
/*      */         
/*      */ 
/* 3054 */         EnterpriseUtil.addUpdateQueryToFile(temp_query);
/* 3055 */         EnterpriseUtil.addUpdateQueryToFile(updatquery);
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 3061 */           ps.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3068 */         if (!serversite.equals("2")) {
/*      */           break label1534;
/*      */         }
/*      */       }
/*      */       catch (Exception qe)
/*      */       {
/* 3056 */         qe.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try {
/* 3061 */           ps.close();
/*      */         }
/*      */         catch (Exception exc) {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3070 */       serversite = "2";
/* 3071 */       String hostid = ((AMActionForm)form).getChoosehost();
/* 3072 */       ResultSet rssel = null;
/*      */       try
/*      */       {
/* 3075 */         rssel = AMConnectionPool.executeQueryStmt("select * from AM_SCRIPTHOST_MAPPER where SCRIPTID=" + resourceid);
/* 3076 */         int count; if (rssel.next()) {
/* 3077 */           count = AMConnectionPool.executeUpdateStmt("update AM_SCRIPTHOST_MAPPER set HOSTID=" + choosehost + " where SCRIPTID=" + resourceid);
/*      */         } else {
/* 3079 */           count = AMConnectionPool.executeUpdateStmt("insert into AM_SCRIPTHOST_MAPPER values(" + resourceid + "," + choosehost + ")");
/*      */         }
/*      */       }
/*      */       catch (SQLException exc) {
/*      */         int count;
/* 3084 */         exc.printStackTrace();
/*      */       }
/*      */       finally {
/*      */         try {
/* 3088 */           if (rssel != null) {
/* 3089 */             AMConnectionPool.closeStatement(rssel);
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {}
/*      */       }
/*      */       
/*      */ 
/*      */       label1534:
/*      */       
/* 3098 */       serversite = "1";
/* 3099 */       String deletequery = "delete from AM_SCRIPTHOST_MAPPER where SCRIPTID=" + resourceid;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 3106 */         int count = AMConnectionPool.executeUpdateStmt(deletequery);
/* 3107 */         CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 3108 */         credUtil.removeFromMapping(String.valueOf(resourceid));
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 3113 */         exc.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3118 */       DataCollectionControllerUtil.rescheduleScriptDataCollection(resourceid, mtype);
/*      */     }
/*      */     
/* 3121 */     String path = "/showresource.do?resourceid=" + resourceid + "&type=" + mtype + "&method=showdetails&resourcename=" + dname;
/* 3122 */     return new ActionForward(path, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteTableByUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3130 */     String resourceid = request.getParameter("resourceid");
/* 3131 */     String tableid = request.getParameter("tableid");
/* 3132 */     ScheduleScriptDataCollection.deleteTable(Integer.parseInt(resourceid), tableid, "Script Monitor", "-1", true);
/* 3133 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */   public ActionForward deleteRowByUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3138 */     String resourceid = request.getParameter("resourceid");
/* 3139 */     if (request.getParameter("tabId") != null) {
/* 3140 */       resourceid = request.getParameter("rowid");
/*      */     }
/*      */     
/* 3143 */     String scriptid = request.getParameter("tableid");
/* 3144 */     StringTokenizer str = new StringTokenizer(resourceid, ",");
/* 3145 */     String resType = request.getParameter("resType");
/* 3146 */     String tabName = request.getParameter("tabName");
/* 3147 */     String tableName = request.getParameter("tableName");
/* 3148 */     for (;;) { if (str.hasMoreTokens())
/*      */       {
/* 3150 */         Statement todelete = null;
/*      */         try
/*      */         {
/* 3153 */           String rowid = str.nextToken();
/* 3154 */           StringBuilder query = new StringBuilder();
/* 3155 */           String tableid = new String();
/* 3156 */           query.append("select TABLEID from AM_TABLE_ROWS where RESID = ").append(scriptid).append(" and ROWID = ").append(rowid);
/* 3157 */           ResultSet rs = null;
/*      */           try {
/* 3159 */             rs = AMConnectionPool.executeQueryStmt(query.toString());
/* 3160 */             if (rs.next()) {
/* 3161 */               tableid = rs.getString("TABLEID");
/*      */             }
/*      */           } catch (Exception e) {
/* 3164 */             e.printStackTrace();
/*      */           } finally {
/* 3166 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */           
/* 3169 */           String resID = null;
/* 3170 */           if ((resType != null) && (tabName != null) && (resType.equals("QueryMonitor")) && (tabName.equals("Execution Time"))) {
/* 3171 */             ArrayList resIds = new ArrayList();
/* 3172 */             resIds = DBUtil.getRows("select ROWID from AM_TABLE_ROWS where AM_TABLE_ROWS.TABLEID= " + tableid);
/* 3173 */             for (int i = 0; i < resIds.size(); i++) {
/* 3174 */               ArrayList resIDList = (ArrayList)resIds.get(i);
/* 3175 */               if (i != 0) {
/* 3176 */                 resID = resID + "," + (String)resIDList.get(0);
/*      */               } else {
/* 3178 */                 resID = (String)resIDList.get(0);
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 3183 */           ScheduleScriptDataCollection.deleteScriptRow(rowid, false, request.getParameter("baseid"), request.getParameter("type"), tableid, Integer.parseInt(scriptid), tableName);
/* 3184 */           if (resID != null) {
/* 3185 */             todelete = AMConnectionPool.getConnection().createStatement();
/* 3186 */             todelete.addBatch("delete from AM_ManagedObject where RESOURCEID in (" + resID + ")");
/* 3187 */             todelete.addBatch("delete from AM_SCRIPT_TABULAR_NUMERIC_DATA_" + scriptid + " where RESID in (" + resID + ")");
/* 3188 */             todelete.addBatch("delete from AM_CAM_COLUMNAR_DATA_" + scriptid + " where ROWID in (" + resID + ")");
/* 3189 */             todelete.executeBatch();
/* 3190 */             todelete.clearBatch();
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/* 3199 */             if (todelete != null) {
/* 3200 */               todelete.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 3204 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 3195 */           exc.printStackTrace();
/*      */         }
/*      */         finally {
/*      */           try {
/* 3199 */             if (todelete != null) {
/* 3200 */               todelete.close();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/* 3204 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3209 */     if (Constants.sqlManager)
/*      */     {
/* 3211 */       String sqlmanid = request.getParameter("sqlmanid");
/* 3212 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + sqlmanid + "&datatype=8");
/*      */     }
/* 3214 */     if (request.getParameter("fromPopUpWindow") != null) {
/* 3215 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 3219 */     if (request.getParameter("tabId") != null) {
/* 3220 */       String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/* 3221 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid + appendHash, true);
/*      */     }
/*      */     
/* 3224 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + scriptid, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void checkAndUpdateScalar(ActionForm form, int resourceid, ArrayList todelete, ArrayList toadd)
/*      */   {
/* 3232 */     ArrayList temp = new ArrayList();
/* 3233 */     ArrayList temp1 = new ArrayList();
/* 3234 */     ArrayList stringattributes_new = null;
/* 3235 */     ArrayList numericattributes_new = null;
/* 3236 */     String string_att = ((AMActionForm)form).getString_att().trim();
/* 3237 */     stringattributes_new = getAttributes(string_att);
/* 3238 */     Hashtable oldStringAttributes = getExistingStringAttributes(resourceid);
/* 3239 */     Enumeration attributes_key = oldStringAttributes.keys();
/* 3240 */     while (attributes_key.hasMoreElements())
/*      */     {
/* 3242 */       String token = (String)attributes_key.nextElement();
/* 3243 */       String attribute = (String)oldStringAttributes.get(token);
/* 3244 */       if (!stringattributes_new.contains(attribute))
/*      */       {
/*      */ 
/* 3247 */         todelete.add(token);
/*      */       }
/*      */     }
/* 3250 */     for (int i = 0; i < stringattributes_new.size(); i++)
/*      */     {
/* 3252 */       String tocheck_add = (String)stringattributes_new.get(i);
/* 3253 */       if (!oldStringAttributes.containsValue(tocheck_add))
/*      */       {
/*      */ 
/* 3256 */         temp.add(tocheck_add);
/*      */       }
/*      */     }
/*      */     
/* 3260 */     toadd.add(temp);
/*      */     
/* 3262 */     String numeric_att = ((AMActionForm)form).getNumeric_att().trim();
/* 3263 */     numericattributes_new = getAttributes(numeric_att);
/* 3264 */     Hashtable oldNumericAttributes = getExistingNumericAttributes(resourceid);
/* 3265 */     attributes_key = oldNumericAttributes.keys();
/* 3266 */     while (attributes_key.hasMoreElements())
/*      */     {
/* 3268 */       String token = (String)attributes_key.nextElement();
/* 3269 */       String attribute = (String)oldNumericAttributes.get(token);
/* 3270 */       if (!numericattributes_new.contains(attribute))
/*      */       {
/*      */ 
/* 3273 */         todelete.add(token);
/*      */       }
/*      */     }
/* 3276 */     for (int i = 0; i < numericattributes_new.size(); i++)
/*      */     {
/* 3278 */       String tocheck_add = (String)numericattributes_new.get(i);
/* 3279 */       if (!oldNumericAttributes.containsValue(tocheck_add))
/*      */       {
/*      */ 
/* 3282 */         temp1.add(tocheck_add);
/*      */       }
/*      */     }
/*      */     
/* 3286 */     toadd.add(temp1);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward updateScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3292 */     int resourceid = Integer.parseInt(String.valueOf(request.getParameter("resourceid")));
/*      */     try
/*      */     {
/* 3295 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       
/*      */ 
/* 3298 */       String type = ((AMActionForm)form).getType();
/*      */       try {
/* 3300 */         if (type.equals("QENGINE")) {
/* 3301 */           AMActionForm amform = (AMActionForm)form;
/* 3302 */           String temp_query = "update AM_ManagedObject set displayname='" + FormatUtil.findReplace(amform.getDisplayname(), "'", "''") + "' where resourceid=" + resourceid;
/* 3303 */           int temp_count = AMConnectionPool.executeUpdateStmt(temp_query);
/* 3304 */           String temp_query2 = "update  AM_ScriptArgs set timeout=" + amform.getTimeout() + ",pollinterval=" + amform.getPollInterval() * 60 + " where resourceid=" + amform.getResourceid();
/* 3305 */           temp_count = AMConnectionPool.executeUpdateStmt(temp_query2);
/* 3306 */           EnterpriseUtil.addUpdateQueryToFile(temp_query);
/* 3307 */           return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + amform.getResourceid());
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 3312 */         log.warn("Unable to update the qengine script monitor");
/* 3313 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + ((AMActionForm)form).getResourceid());
/*      */       }
/* 3315 */       ResultSet rs = null;
/* 3316 */       FormFile file = null;
/* 3317 */       String displayname_new = ((AMActionForm)form).getDisplayname();
/* 3318 */       String choosehost = ((AMActionForm)form).getChoosehost();
/* 3319 */       if ((choosehost != null) && (choosehost.contains(","))) {
/* 3320 */         StringTokenizer st = new StringTokenizer(choosehost);
/* 3321 */         while (st.hasMoreTokens()) {
/* 3322 */           choosehost = st.nextToken(",");
/*      */         }
/* 3324 */         ((AMActionForm)form).setChoosehost(choosehost);
/*      */       }
/* 3326 */       String arguements_new = ((AMActionForm)form).getMessage().trim();
/* 3327 */       int poll_interval_new = ((AMActionForm)form).getPollInterval();
/* 3328 */       int pollinginteval_new = poll_interval_new * 60;
/* 3329 */       int timeout_new = ((AMActionForm)form).getTimeout();
/* 3330 */       String serverpath_new = null;
/* 3331 */       serverpath_new = ((AMActionForm)form).getServerpath();
/* 3332 */       String serversite = ((AMActionForm)form).getServersite();
/* 3333 */       boolean isCommand = ((AMActionForm)form).getisCommand();
/* 3334 */       String mode_new = "";
/* 3335 */       if (serversite.equals("remote"))
/*      */       {
/* 3337 */         mode_new = ((AMActionForm)form).getMode();
/*      */ 
/*      */ 
/*      */       }
/* 3341 */       else if ((!System.getProperty("os.name").startsWith("windows")) && (!System.getProperty("os.name").startsWith("Windows")))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3347 */         mode_new = ((AMActionForm)form).getMode();
/*      */       }
/*      */       
/*      */ 
/* 3351 */       if (isCommand) {
/* 3352 */         mode_new = "-1";
/*      */       }
/* 3354 */       boolean editAttributes = ((AMActionForm)form).isOpfile();
/*      */       
/* 3356 */       ArrayList todelete = new ArrayList();
/* 3357 */       ArrayList toadd = new ArrayList();
/* 3358 */       if (editAttributes)
/*      */       {
/* 3360 */         checkAndUpdateScalar(form, resourceid, todelete, toadd);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3418 */       int healthid = 2201;
/* 3419 */       if (editAttributes)
/*      */       {
/* 3421 */         deleteAttributes(todelete);
/* 3422 */         Properties props = new Properties();
/* 3423 */         ArrayList idList = new ArrayList();
/* 3424 */         idList.add("2200");
/* 3425 */         idList.add("2201");
/* 3426 */         idList.add("2202");
/* 3427 */         healthid = NewMonitorUtil.insertAttributes(resourceid, toadd, ((AMActionForm)form).getType(), "", props, "NO", idList, null, null, "AM_MinMaxAvgData");
/*      */       }
/*      */       
/* 3430 */       String delimiter_new_str = "";
/* 3431 */       if (((AMActionForm)form).getDelimiter() != null)
/*      */       {
/* 3433 */         delimiter_new_str = ((AMActionForm)form).getDelimiter();
/*      */       }
/*      */       else
/*      */       {
/* 3437 */         delimiter_new_str = "s";
/*      */       }
/*      */       
/* 3440 */       boolean isFile = false;
/* 3441 */       if (request.getParameter("isFile") != null) {
/* 3442 */         isFile = true;
/*      */       }
/* 3444 */       String outputfile_new = null;
/* 3445 */       if (!isFile) {
/* 3446 */         outputfile_new = "-1";
/*      */       } else {
/* 3448 */         outputfile_new = ((AMActionForm)form).getOutputfile();
/*      */       }
/* 3450 */       String haid = ((AMActionForm)form).getHaid();
/* 3451 */       FormFile file_new = ((AMActionForm)form).getTheFile();
/* 3452 */       String filename_new = null;
/* 3453 */       if (serversite.equals("remote"))
/*      */       {
/* 3455 */         serversite = "2";
/* 3456 */         String hostid = ((AMActionForm)form).getChoosehost();
/* 3457 */         ResultSet rssel = null;
/*      */         try
/*      */         {
/* 3460 */           rssel = AMConnectionPool.executeQueryStmt("select * from AM_SCRIPTHOST_MAPPER where SCRIPTID=" + resourceid);
/* 3461 */           int count; if (rssel.next()) {
/* 3462 */             count = AMConnectionPool.executeUpdateStmt("update AM_SCRIPTHOST_MAPPER set HOSTID=" + choosehost + " where SCRIPTID=" + resourceid);
/*      */           } else {
/* 3464 */             count = AMConnectionPool.executeUpdateStmt("insert into AM_SCRIPTHOST_MAPPER values(" + resourceid + "," + choosehost + ")");
/*      */           }
/*      */         }
/*      */         catch (SQLException exc)
/*      */         {
/*      */           try {
/*      */             int count;
/* 3471 */             count = AMConnectionPool.executeUpdateStmt("update AM_SCRIPTHOST_MAPPER set HOSTID=" + choosehost + " where SCRIPTID=" + resourceid);
/*      */           }
/*      */           catch (Exception exc1) {
/*      */             int count;
/* 3475 */             exc1.printStackTrace();
/*      */           }
/*      */         }
/*      */         finally {
/*      */           try {
/* 3480 */             if (rssel != null) {
/* 3481 */               AMConnectionPool.closeStatement(rssel);
/*      */             }
/*      */           }
/*      */           catch (Exception ex) {}
/*      */         }
/*      */       }
/*      */       
/* 3488 */       serversite = "1";
/* 3489 */       String deletequery = "delete from AM_SCRIPTHOST_MAPPER where SCRIPTID=" + resourceid;
/*      */       try
/*      */       {
/* 3492 */         int count = AMConnectionPool.executeUpdateStmt(deletequery);
/* 3493 */         CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 3494 */         credUtil.removeFromMapping(String.valueOf(resourceid));
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 3498 */         exc.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 3503 */       String exec_dir_new = null;
/*      */       
/* 3505 */       char delim_type = '=';
/* 3506 */       if (file_new != null)
/*      */       {
/* 3508 */         filename_new = String.valueOf(file);
/*      */       }
/* 3510 */       String query = "select * from AM_ScriptArgs where resourceid=" + resourceid;
/* 3511 */       String outputfile_old = "";
/* 3512 */       String delimiter_old = "";
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 3517 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3518 */         if (rs.next())
/*      */         {
/*      */ 
/* 3521 */           outputfile_old = rs.getString("opfile");
/* 3522 */           delimiter_old = rs.getString("opdelimiter");
/*      */         }
/* 3524 */         rs.close();
/*      */       }
/*      */       catch (SQLException exc)
/*      */       {
/* 3528 */         exc.printStackTrace();
/*      */       }
/* 3530 */       int baseid = -1;
/* 3531 */       String baseId = request.getParameter("baseid");
/* 3532 */       if ((baseId == null) || ("null".equals(baseId)))
/*      */       {
/* 3534 */         baseid = -1;
/*      */       }
/*      */       else
/*      */       {
/* 3538 */         baseid = Integer.parseInt(baseId);
/*      */       }
/* 3540 */       String outputfile_new_temp = outputfile_new;
/* 3541 */       if ((!editAttributes) && (baseid == -1))
/*      */       {
/* 3543 */         outputfile_new = outputfile_old;
/* 3544 */         delimiter_new_str = delimiter_old;
/*      */       }
/* 3546 */       updateTables(request, resourceid, healthid, null, "", "Script Monitor", "AM_MinMaxAvgData");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3658 */       boolean ss = serversite.equals("1");
/* 3659 */       exec_dir_new = ((AMActionForm)form).getWorkingdirectory();
/*      */       
/*      */ 
/*      */ 
/* 3663 */       String filename = null;
/* 3664 */       String[] filename_path = new String[2];
/* 3665 */       AMLog.debug("the filename===============>:" + serverpath_new);
/* 3666 */       if (isCommand) {
/* 3667 */         filename_path = new String[] { "", serverpath_new };
/* 3668 */         filename = serverpath_new;
/*      */       } else {
/* 3670 */         filename_path = getFilename(serverpath_new, serversite);
/* 3671 */         filename = filename_path[1];
/*      */       }
/*      */       
/* 3674 */       PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("update AM_ScriptArgs set scriptname=?,arguements=?,opfile=?,opdelimiter=?,timeout=?,mode=?,displayname=?,pollinterval=?,executiondirectory=?,serverpath=?,serversite=? where resourceid=" + resourceid);
/*      */       try
/*      */       {
/* 3677 */         AMConnectionPool.getInstance();Statement toupdate = AMConnectionPool.getConnection().createStatement();
/*      */         
/*      */ 
/* 3680 */         String temp_query = "update AM_ManagedObject set displayname='" + FormatUtil.findReplace(displayname_new, "'", "''") + "' where resourceid=" + resourceid;
/* 3681 */         toupdate.addBatch(temp_query);
/* 3682 */         EnterpriseUtil.addUpdateQueryToFile(temp_query);
/* 3683 */         System.out.println("the filename===============>:" + filename_path[1]);
/*      */         
/*      */ 
/* 3686 */         ps.setString(1, filename_path[1]);
/* 3687 */         ps.setString(2, arguements_new);
/* 3688 */         ps.setString(3, outputfile_new);
/* 3689 */         ps.setString(4, delimiter_new_str);
/* 3690 */         ps.setInt(5, timeout_new);
/* 3691 */         ps.setString(6, mode_new);
/* 3692 */         ps.setString(7, displayname_new);
/* 3693 */         ps.setInt(8, pollinginteval_new);
/* 3694 */         ps.setString(9, exec_dir_new);
/* 3695 */         ps.setString(10, serverpath_new);
/* 3696 */         ps.setString(11, serversite);
/*      */         
/*      */ 
/*      */ 
/* 3700 */         ps.execute();
/*      */         
/* 3702 */         toupdate.executeBatch();
/* 3703 */         toupdate.clearBatch();
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 3707 */         System.out.println("Problem in updating the update queries for the script");
/* 3708 */         exc.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3712 */         ps.close();
/*      */       }
/* 3714 */       String resourcetype = "Script Monitor";
/* 3715 */       String basetype = request.getParameter("basetype");
/*      */       
/* 3717 */       if ((basetype != null) && (!basetype.equals("")))
/*      */       {
/* 3719 */         resourcetype = type;
/* 3720 */         basetype = "Script Monitor";
/* 3721 */         DataCollectionControllerUtil.rescheduleCustomDataCollection(resourceid, resourcetype, baseid, basetype);
/*      */       }
/*      */       else
/*      */       {
/* 3725 */         DataCollectionControllerUtil.rescheduleScriptDataCollection(resourceid, resourcetype);
/*      */       }
/* 3727 */       String haid1 = "";
/* 3728 */       if (request.getParameter("haid") != null)
/*      */       {
/* 3730 */         haid1 = "&haid=" + request.getParameter("haid");
/*      */       }
/* 3732 */       String path = "/showresource.do?resourceid=" + resourceid + "&resourcename=" + displayname_new + "&type=" + resourcetype + "&method=showdetails" + haid1;
/* 3733 */       return new ActionForward(path, true);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 3737 */       exc.printStackTrace();
/*      */       
/* 3739 */       String path = "/showresource.do?resourceid=" + resourceid;
/* 3740 */       return new ActionForward(path, true);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void updateTables(HttpServletRequest request, int resourceid, int mon_healthid, String type, String toappend, String monType, String archivalTable)
/*      */   {
/* 3750 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3751 */     System.out.println("The request===>" + request);
/* 3752 */     System.out.println("Params===>" + request.getParameterNames());
/* 3753 */     String hastables = request.getParameter("tablespresent");
/* 3754 */     System.out.println("TABLES PRESENT===>" + hastables);
/*      */     try
/*      */     {
/* 3757 */       if ((hastables != null) && (hastables.equals("on")))
/*      */       {
/* 3759 */         int table_count = Integer.parseInt(request.getParameter("table_row"));
/* 3760 */         int old_table_count = getNoOfTables(resourceid);
/* 3761 */         System.out.println("THE NO OF TABLES====>" + table_count);
/* 3762 */         boolean checktables = true;
/* 3763 */         if (table_count < old_table_count)
/*      */         {
/* 3765 */           table_count = old_table_count;
/* 3766 */           checktables = false;
/*      */         }
/* 3768 */         System.out.println("THE NO OF TABLES====>" + table_count);
/* 3769 */         String tablename = "";
/* 3770 */         String str_att_tab = "";
/* 3771 */         String num_att_tab = "";
/* 3772 */         String table_delimiter = "";
/* 3773 */         String primary_index = "";
/* 3774 */         ArrayList tablenames = new ArrayList();
/* 3775 */         if (checktables)
/*      */         {
/* 3777 */           int m = 1;
/* 3778 */           if (type != null)
/*      */           {
/* 3780 */             m = old_table_count + 1;
/*      */           }
/* 3782 */           for (int k = m; k <= table_count; k++)
/*      */           {
/* 3784 */             System.out.println("K===>" + k);
/* 3785 */             tablename = request.getParameter("table" + k).trim();
/* 3786 */             tablenames.add(tablename);
/* 3787 */             str_att_tab = request.getParameter("stringatt" + k).trim();
/* 3788 */             num_att_tab = request.getParameter("numericatt" + k).trim();
/* 3789 */             table_delimiter = request.getParameter("cdl" + k).trim();
/* 3790 */             if ((table_delimiter != null) && (table_delimiter.equals("\\t")))
/*      */             {
/* 3792 */               table_delimiter = "t";
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 3797 */             primary_index = request.getParameter("pcatt" + k).trim();
/* 3798 */             ArrayList stringattributes_table = new ArrayList();
/* 3799 */             ArrayList numericattributes_table = new ArrayList();
/* 3800 */             ArrayList primaryattributes_table = new ArrayList();
/* 3801 */             if (!str_att_tab.equals(""))
/*      */             {
/* 3803 */               stringattributes_table = getAttributes(str_att_tab);
/*      */             }
/* 3805 */             if (!num_att_tab.equals(""))
/*      */             {
/* 3807 */               numericattributes_table = getAttributes(num_att_tab);
/*      */             }
/* 3809 */             if (!primary_index.equals(""))
/*      */             {
/* 3811 */               primaryattributes_table = getAttributes(primary_index);
/*      */             }
/* 3813 */             ArrayList tableAttributes = new ArrayList();
/* 3814 */             tableAttributes.add(stringattributes_table);
/* 3815 */             tableAttributes.add(numericattributes_table);
/* 3816 */             tableAttributes.add(primaryattributes_table);
/* 3817 */             System.out.println("stringattributes_table===>" + stringattributes_table);
/* 3818 */             System.out.println("numericattributes_table===>" + numericattributes_table);
/* 3819 */             System.out.println("primaryattributes_table===>" + primaryattributes_table);
/*      */             try
/*      */             {
/* 3822 */               String qry = "SELECT TABLEID,PRIMARY_COLUMN FROM AM_SCRIPT_TABLES WHERE TABLENAME='" + tablename + "' AND SCRIPTID=" + resourceid;
/* 3823 */               System.out.println("The qry===>" + qry);
/* 3824 */               ResultSet rs1 = AMConnectionPool.executeQueryStmt(qry);
/* 3825 */               boolean isTablesInserted; if (rs1.next())
/*      */               {
/*      */ 
/* 3828 */                 String res_type = "SCRIPT_ROW";
/* 3829 */                 String primarycol = rs1.getString("PRIMARY_COLUMN");
/* 3830 */                 String tableid = rs1.getString("TABLEID");
/* 3831 */                 String q1 = "SELECT ATTRIBUTEID FROM AM_CAM_DC_ATTRIBUTES WHERE ATTRIBUTENAME='Health' and GROUPID=" + tableid;
/* 3832 */                 System.out.println("q1--->" + q1);
/* 3833 */                 ResultSet rs2 = AMConnectionPool.executeQueryStmt(q1);
/*      */                 
/*      */                 boolean isTablesUpdated;
/* 3836 */                 if (rs2.next())
/*      */                 {
/* 3838 */                   int healthid = rs2.getInt("ATTRIBUTEID");
/* 3839 */                   isTablesUpdated = updateTableAttributes(tableAttributes, tablename, resourceid, healthid, rs1.getInt("TABLEID"), primarycol, table_delimiter, toappend);
/*      */                 }
/* 3841 */                 closeResultSet(rs2);
/*      */               }
/*      */               else
/*      */               {
/* 3845 */                 Properties props = new Properties();
/* 3846 */                 isTablesInserted = NewMonitorUtil.insertTableAttributes(tableAttributes, tablename, resourceid, table_delimiter, mon_healthid, toappend, props, null, null, monType, archivalTable);
/*      */               }
/* 3848 */               closeResultSet(rs1);
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 3852 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/* 3856 */         if (checktables)
/*      */         {
/*      */           try
/*      */           {
/* 3860 */             ResultSet rs2 = AMConnectionPool.executeQueryStmt("SELECT TABLENAME,TABLEID FROM AM_SCRIPT_TABLES WHERE SCRIPTID=" + resourceid);
/* 3861 */             while (rs2.next())
/*      */             {
/* 3863 */               if ((!tablenames.contains(rs2.getString("TABLENAME"))) && 
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3869 */                 (type == null)) {
/* 3870 */                 ScheduleScriptDataCollection.deleteTable(resourceid, rs2.getString("TABLEID"));
/*      */               }
/*      */             }
/* 3873 */             closeResultSet(rs2);
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/* 3877 */             exc.printStackTrace();
/*      */           }
/*      */         } else {
/* 3880 */           table_count = Integer.parseInt(request.getParameter("table_row"));
/* 3881 */           for (int k = 1; k <= table_count; k++) {
/*      */             try
/*      */             {
/* 3884 */               tablename = request.getParameter("table" + k).trim();
/* 3885 */               AMLog.info("tablename  tablename===>" + tablename);
/* 3886 */               tablenames.add(tablename);
/*      */             }
/*      */             catch (Exception e) {
/* 3889 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/* 3893 */           ResultSet rs2 = null;
/*      */           try
/*      */           {
/* 3896 */             rs2 = AMConnectionPool.executeQueryStmt("SELECT TABLENAME,TABLEID FROM AM_SCRIPT_TABLES WHERE SCRIPTID=" + resourceid);
/* 3897 */             while (rs2.next())
/*      */             {
/* 3899 */               if (!tablenames.contains(rs2.getString("TABLENAME")))
/*      */               {
/* 3901 */                 ScheduleScriptDataCollection.deleteTable(resourceid, rs2.getString("TABLEID"), "Script Monitor", "-1", true);
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception exc)
/*      */           {
/* 3907 */             exc.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/* 3911 */             AMConnectionPool.closeResultSet(rs2);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 3919 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void closeResultSet(ResultSet rs)
/*      */   {
/*      */     try
/*      */     {
/* 3928 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception exc) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getNoOfTables(int scriptid)
/*      */   {
/* 3938 */     int count = 0;
/*      */     try
/*      */     {
/* 3941 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3942 */       String qry = "select count(*) from AM_SCRIPT_TABLES WHERE SCRIPTID=" + scriptid;
/* 3943 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 3944 */       if (rs.next())
/*      */       {
/* 3946 */         count = rs.getInt(1);
/*      */       }
/* 3948 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 3952 */       exc.printStackTrace();
/*      */     }
/* 3954 */     return count;
/*      */   }
/*      */   
/*      */   public static boolean updateTableAttributes(ArrayList tableAttributes, String tablename, int resourceid, int healthid, int tableid, String pcol, String table_delimiter, String toappend)
/*      */   {
/* 3959 */     return updateTableAttributes(tableAttributes, tablename, resourceid, healthid, tableid, pcol, table_delimiter, toappend, "Script Monitor");
/*      */   }
/*      */   
/*      */   public static boolean updateTableAttributes(ArrayList tableAttributes, String tablename, int resourceid, int healthid, int tableid, String pcol, String table_delimiter, String toappend, String monType)
/*      */   {
/* 3964 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 3965 */     boolean toreturn = true;
/* 3966 */     ArrayList satts = (ArrayList)tableAttributes.get(0);
/* 3967 */     ArrayList natts = (ArrayList)tableAttributes.get(1);
/* 3968 */     ArrayList patts = (ArrayList)tableAttributes.get(2);
/* 3969 */     String primary_column = "";
/* 3970 */     if ((patts.size() == 0) || ((satts.size() == 0) && (natts.size() == 0)))
/*      */     {
/* 3972 */       System.out.println("Either Primary Attributes or the combination of the String and Numeric Attributes size is 0--Updation");
/* 3973 */       return false;
/*      */     }
/* 3975 */     for (int i = 0; i < patts.size(); i++)
/*      */     {
/* 3977 */       primary_column = primary_column + patts.get(i);
/* 3978 */       if (i != patts.size() - 1)
/*      */       {
/* 3980 */         primary_column = primary_column + "#";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3985 */     String rowresourcetype = "SCRIPT_ROW";
/* 3986 */     if ((monType != null) && (monType.equals("QueryMonitor")))
/*      */     {
/* 3988 */       rowresourcetype = "QueryMonitor_" + tablename + "_ROW";
/*      */     }
/* 3990 */     else if ((monType != null) && (monType.equals("Script Monitor")))
/*      */     {
/* 3992 */       rowresourcetype = "SCRIPT_" + tablename + "_ROW";
/*      */     }
/*      */     else
/*      */     {
/* 3996 */       rowresourcetype = monType + "_" + tablename + "_ROW";
/*      */     }
/*      */     try
/*      */     {
/* 4000 */       System.out.println("The pcol===>" + pcol);
/* 4001 */       System.out.println("The primary_column====>" + primary_column);
/* 4002 */       AMConnectionPool.getInstance();Statement todeleteres = AMConnectionPool.getConnection().createStatement();
/* 4003 */       if (!pcol.equals(primary_column))
/*      */       {
/*      */ 
/* 4006 */         String q1 = "select resourceid from AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid=" + resourceid + " and  AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and RESOURCENAME like '" + tablename + "%'";
/* 4007 */         System.out.println("The q1====>" + q1);
/* 4008 */         ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/* 4009 */         while (rs.next())
/*      */         {
/* 4011 */           todeleteres.addBatch("delete from AM_SCRIPT_TABULAR_NUMERIC_DATA WHERE RESID=" + rs.getString("resourceid"));
/* 4012 */           todeleteres.addBatch("delete from AM_CAM_COLUMNAR_DATA WHERE ROWID=" + rs.getString("resourceid"));
/* 4013 */           todeleteres.addBatch("delete from AM_PARENTCHILDMAPPER where CHILDID=" + rs.getString("resourceid"));
/* 4014 */           todeleteres.addBatch("delete from AM_ManagedObject where resourceid=" + rs.getString("resourceid"));
/* 4015 */           EnterpriseUtil.addUpdateQueryToFile("delete from AM_PARENTCHILDMAPPER where CHILDID=" + rs.getString("resourceid"));
/* 4016 */           EnterpriseUtil.addUpdateQueryToFile("delete from AM_ManagedObject where resourceid=" + rs.getString("resourceid"));
/*      */         }
/*      */         
/* 4019 */         todeleteres.addBatch("update AM_SCRIPT_TABLES set PRIMARY_COLUMN='" + primary_column + "',COL_DELIMITER='" + table_delimiter + "' WHERE TABLEID=" + tableid);
/* 4020 */         todeleteres.executeBatch();
/* 4021 */         todeleteres.clearBatch();
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 4028 */         AMConnectionPool.executeUpdateStmt("update AM_SCRIPT_TABLES set PRIMARY_COLUMN='" + primary_column + "',COL_DELIMITER='" + table_delimiter + "' WHERE TABLEID=" + tableid);
/*      */       }
/* 4030 */       String qry = "SELECT ATTRIBUTEID,ATTRIBUTENAME,TYPE,DISPLAYTYPE FROM AM_CAM_DC_ATTRIBUTES,AM_SCRIPT_TABLES WHERE SCRIPTID=" + resourceid + " AND TABLENAME = '" + tablename + "' AND GROUPID=TABLEID";
/* 4031 */       System.out.println("The qry=======>" + qry);
/* 4032 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 4033 */       Hashtable ht = new Hashtable();
/* 4034 */       ArrayList al = new ArrayList();
/* 4035 */       ArrayList alname = new ArrayList();
/* 4036 */       ArrayList al_str_name = new ArrayList();
/* 4037 */       ArrayList al_num_name = new ArrayList();
/* 4038 */       while (rs.next())
/*      */       {
/* 4040 */         Properties p = new Properties();
/* 4041 */         p.setProperty("id", rs.getString("ATTRIBUTEID"));
/* 4042 */         p.setProperty("name", rs.getString("ATTRIBUTENAME"));
/* 4043 */         p.setProperty("type", rs.getString("TYPE"));
/* 4044 */         p.setProperty("reports", rs.getString("DISPLAYTYPE"));
/* 4045 */         alname.add(rs.getString("ATTRIBUTENAME"));
/* 4046 */         if (rs.getString("TYPE").equals("3"))
/*      */         {
/* 4048 */           al_str_name.add(rs.getString("ATTRIBUTENAME"));
/*      */         }
/*      */         else
/*      */         {
/* 4052 */           al_num_name.add(rs.getString("ATTRIBUTENAME"));
/*      */         }
/* 4054 */         al.add(rs.getString("ATTRIBUTEID"));
/* 4055 */         ht.put(rs.getString("ATTRIBUTEID"), p);
/*      */       }
/*      */       
/* 4058 */       String am_attributes_entry = "";
/* 4059 */       String am_cam_attributes_entry = "";
/* 4060 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 4061 */       AMConnectionPool.getInstance();Statement depinsert = AMConnectionPool.getConnection().createStatement();
/* 4062 */       String graphtype = "0";
/* 4063 */       String expression = "";
/* 4064 */       AMConnectionPool.getInstance();Statement todelete = AMConnectionPool.getConnection().createStatement();
/* 4065 */       AMConnectionPool.getInstance();Statement depdelete = AMConnectionPool.getConnection().createStatement();
/* 4066 */       for (int i = 0; i < al.size(); i++)
/*      */       {
/* 4068 */         String attributename = ((Properties)ht.get(al.get(i))).getProperty("name");
/* 4069 */         String attributetype = ((Properties)ht.get(al.get(i))).getProperty("type");
/* 4070 */         if (attributetype.equals("0"))
/*      */         {
/* 4072 */           if (!natts.contains(attributename))
/*      */           {
/* 4074 */             ScheduleScriptDataCollection.deleteTableAttributes((String)al.get(i));
/*      */           }
/*      */         }
/* 4077 */         else if (attributetype.equals("3"))
/*      */         {
/*      */ 
/* 4080 */           if (!satts.contains(attributename))
/*      */           {
/* 4082 */             ScheduleScriptDataCollection.deleteTableAttributes((String)al.get(i));
/*      */           }
/*      */         }
/*      */       }
/* 4086 */       int temp_attid = getAttributeId();
/* 4087 */       for (int i = 0; i < satts.size(); i++)
/*      */       {
/* 4089 */         if (!al_str_name.contains(satts.get(i)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4095 */           int att_type = 3;
/* 4096 */           am_attributes_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + rowresourcetype + "','" + satts.get(i) + "'," + att_type + ",'" + satts.get(i) + "')";
/* 4097 */           String am_attributes_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_CAM_COLUMNAR_DATA" + toappend + "','ROWID','ATTRIBUTEID','VALUE','COLLECTIONTIME','2','','" + satts.get(i) + "','')";
/* 4098 */           am_cam_attributes_entry = "insert into AM_CAM_DC_ATTRIBUTES(ATTRIBUTEID,ATTRIBUTENAME,DISPLAYNAME,TYPE,GROUPID,DISPLAYTYPE,EXPRESSION,GRAPHTYPE) values(" + temp_attid + ",'" + satts.get(i) + "','" + satts.get(i) + "'," + att_type + "," + tableid + "," + 0 + ",'" + expression + "'," + graphtype + ")";
/* 4099 */           toinsert.addBatch(am_attributes_entry);
/* 4100 */           toinsert.addBatch(am_cam_attributes_entry);
/* 4101 */           toinsert.addBatch(am_attributes_ext_entry);
/* 4102 */           depinsert.addBatch("insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")");
/* 4103 */           temp_attid++;
/*      */         }
/*      */       }
/*      */       
/* 4107 */       for (int i = 0; i < natts.size(); i++)
/*      */       {
/* 4109 */         if (!al_num_name.contains(natts.get(i)))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4115 */           int att_type = 0;
/* 4116 */           am_attributes_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + rowresourcetype + "','" + natts.get(i) + "'," + att_type + ",'" + natts.get(i) + "')";
/* 4117 */           am_cam_attributes_entry = "insert into AM_CAM_DC_ATTRIBUTES(ATTRIBUTEID,ATTRIBUTENAME,DISPLAYNAME,TYPE,GROUPID,DISPLAYTYPE,EXPRESSION,GRAPHTYPE) values(" + temp_attid + ",'" + natts.get(i) + "','" + natts.get(i) + "'," + att_type + "," + tableid + "," + 0 + ",'" + expression + "'," + graphtype + ")";
/* 4118 */           String am_attributes_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_SCRIPT_TABULAR_NUMERIC_DATA" + toappend + "','RESID','ATTRIBUTEID','VALUE','COLLECTIONTIME','2','','" + natts.get(i) + "','')";
/*      */           
/* 4120 */           toinsert.addBatch(am_attributes_entry);
/* 4121 */           toinsert.addBatch(am_attributes_ext_entry);
/* 4122 */           toinsert.addBatch(am_cam_attributes_entry);
/* 4123 */           depinsert.addBatch("insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")");
/* 4124 */           temp_attid++;
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 4129 */         toinsert.executeBatch();
/* 4130 */         toinsert.clearBatch();
/* 4131 */         depinsert.executeBatch();
/* 4132 */         depinsert.clearBatch();
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 4136 */         exc.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4141 */       exc.printStackTrace();
/* 4142 */       toreturn = false;
/*      */     }
/* 4144 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static void updateKeyFile(String hostname, String sshkey)
/*      */   {
/* 4149 */     String nmshome = System.getProperty("webnms.rootdir");
/*      */     
/*      */     try
/*      */     {
/* 4153 */       File f = new File(nmshome + File.separator + "adventnet_ssh_privateKey_file.txt_" + hostname);
/* 4154 */       if (f.exists())
/*      */       {
/* 4156 */         f.delete();
/* 4157 */         f = new File(nmshome + File.separator + "adventnet_ssh_privateKey_file.txt_" + hostname);
/* 4158 */         FileWriter f1 = new FileWriter(f);
/* 4159 */         f1.write(sshkey);
/* 4160 */         f1.close();
/*      */       }
/*      */       else
/*      */       {
/* 4164 */         f = new File(nmshome + File.separator + "adventnet_ssh_privateKey_file.txt_" + hostname);
/* 4165 */         FileWriter f1 = new FileWriter(f);
/* 4166 */         f1.write(sshkey);
/* 4167 */         f1.close();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 4172 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward updateHostDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4178 */     String updated = "success";
/* 4179 */     String avoidFilter = request.getParameter("avoidFilter");
/* 4180 */     String resourceID = request.getParameter("resourceid");
/* 4181 */     if (request.getParameter("mapperid") != null)
/*      */     {
/* 4183 */       String monitoringmode = ((AMActionForm)form).getMonitoringmode();
/* 4184 */       String sshkey = "";
/* 4185 */       CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 4186 */       Long credentialID = Long.valueOf(0L);
/* 4187 */       Properties credentialSelectedProperties = null;
/* 4188 */       boolean isCredentialManager = ((AMActionForm)form).getIsCredManager();
/* 4189 */       String usrname = "";String passwrd = "";String prompt = "";String passphrase = "";
/* 4190 */       if (isCredentialManager)
/*      */       {
/* 4192 */         credentialID = Long.valueOf(new Long(((AMActionForm)form).getCredentialID()).longValue());
/* 4193 */         credentialSelectedProperties = credUtil.rowNameVsValue(credentialID.longValue());
/* 4194 */         usrname = credentialSelectedProperties.getProperty("username");
/* 4195 */         passwrd = credentialSelectedProperties.getProperty("password");
/* 4196 */         if (credentialSelectedProperties.getProperty("prompt") != null)
/*      */         {
/* 4198 */           prompt = credentialSelectedProperties.getProperty("prompt");
/*      */         }
/* 4200 */         if (("ssh".equalsIgnoreCase(monitoringmode)) && (credentialSelectedProperties.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */         {
/* 4202 */           passwrd = credentialSelectedProperties.getProperty("passPhrase");
/* 4203 */           String canonicalHostName = ((AMActionForm)form).getHost();
/*      */           try
/*      */           {
/* 4206 */             InetAddress address = InetAddress.getByName(((AMActionForm)form).getHost());
/* 4207 */             canonicalHostName = address.getCanonicalHostName();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 4211 */             e.printStackTrace();
/*      */           }
/* 4213 */           updateKeyFile(canonicalHostName, sshkey);
/*      */         }
/*      */         
/* 4216 */         ((AMActionForm)form).setIsCredManager(isCredentialManager);
/*      */       }
/*      */       else
/*      */       {
/* 4220 */         if (((AMActionForm)form).isSshkey())
/*      */         {
/* 4222 */           sshkey = ((AMActionForm)form).getDescription();
/* 4223 */           monitoringmode = "SSH_KEY";
/* 4224 */           String canonicalHostName = ((AMActionForm)form).getHost();
/*      */           try
/*      */           {
/* 4227 */             InetAddress address = InetAddress.getByName(((AMActionForm)form).getHost());
/* 4228 */             canonicalHostName = address.getCanonicalHostName();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 4232 */             e.printStackTrace();
/*      */           }
/* 4234 */           updateKeyFile(canonicalHostName, sshkey);
/*      */         }
/*      */         
/* 4237 */         usrname = ((AMActionForm)form).getUsername();
/* 4238 */         passwrd = ((AMActionForm)form).getPassword();
/* 4239 */         if ((monitoringmode != null) && (monitoringmode.indexOf("SSH_KEY") != -1)) {
/* 4240 */           passwrd = ((AMActionForm)form).getPassphrase();
/*      */         }
/* 4242 */         prompt = ((AMActionForm)form).getPrompt();
/*      */       }
/* 4244 */       String port = ((AMActionForm)form).getPort();
/*      */       try
/*      */       {
/* 4247 */         PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("update AM_SCRIPTHOSTDETAILS set HOSTNAME=?,USERNAME=?,PASSWORD=" + DBQueryUtil.encode(passwrd) + ",MODE=?,PROMPT=?,PORT=? where ID=" + request.getParameter("mapperid"));
/* 4248 */         ps.setString(1, ((AMActionForm)form).getHost());
/* 4249 */         ps.setString(2, usrname);
/* 4250 */         String pwd = "";
/* 4251 */         ps.setString(3, monitoringmode);
/* 4252 */         ps.setString(4, Translate.decode(prompt));
/* 4253 */         ps.setInt(5, Integer.parseInt(port));
/* 4254 */         ps.executeUpdate();
/* 4255 */         if (isCredentialManager) {
/* 4256 */           if (DBQueryUtil.checkForDuplicateEntry("CREDENTIALTORESOURCEMAPPING", "RESOURCEID", resourceID, "")) {
/* 4257 */             credUtil.updateCredentialToResourceMap(credentialID.longValue(), Integer.parseInt(resourceID));
/*      */           }
/*      */           else {
/* 4260 */             credUtil.addToCredentialToResourceMap(credentialID.longValue(), Integer.parseInt(resourceID));
/*      */           }
/*      */         }
/*      */         else {
/* 4264 */           credUtil.removeFromMapping(resourceID);
/*      */         }
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/* 4269 */         updated = "failure";
/* 4270 */         exc.printStackTrace();
/*      */       }
/*      */     }
/* 4273 */     String path = "/jsp/ScriptHostDetails.jsp?toclose=true&updated=" + updated + "&avoidFilter" + avoidFilter;
/* 4274 */     return new ActionForward(path, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean isPresent(String[] results_old, String tocheck)
/*      */   {
/* 4280 */     for (int i = 0; i < results_old.length; i++)
/*      */     {
/*      */ 
/* 4283 */       if (tocheck != null)
/*      */       {
/* 4285 */         if ((tocheck.trim().equals("")) || (tocheck.equals(results_old[i])))
/*      */         {
/* 4287 */           return true;
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/* 4292 */         return true;
/*      */       }
/*      */     }
/*      */     
/* 4296 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void calldataCollector(Properties data) {}
/*      */   
/*      */ 
/*      */   public static int getAttributeId()
/*      */   {
/* 4306 */     ResultSet rs = null;
/* 4307 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/* 4309 */     String query = "select max(attributeid) from AM_ATTRIBUTES where attributeid>=20000";
/* 4310 */     temp_attid = 20000;
/* 4311 */     boolean empty_rs = true;
/*      */     try
/*      */     {
/* 4314 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4315 */       if (rs.next())
/*      */       {
/* 4317 */         if (rs.getString(1) != null)
/*      */         {
/* 4319 */           if (!rs.getString(1).equalsIgnoreCase("null"))
/*      */           {
/* 4321 */             empty_rs = false;
/* 4322 */             temp_attid = rs.getInt(1) + 1;
/*      */           }
/*      */           else
/*      */           {
/* 4326 */             temp_attid += 1;
/*      */           }
/*      */           
/*      */         }
/*      */         else {
/* 4331 */           temp_attid += 1;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4336 */       return temp_attid + 1;
/*      */ 
/*      */     }
/*      */     catch (SQLException sqlexc)
/*      */     {
/* 4341 */       temp_attid += 1;
/* 4342 */       sqlexc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 4348 */         rs.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4352 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward disableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4362 */     String[] applications = request.getParameterValues("checkbox");
/* 4363 */     String resourceid = request.getParameter("resourceid");
/* 4364 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4389 */     if (applications != null)
/*      */     {
/* 4391 */       for (int i = 0; i < applications.length; i++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4397 */         CAMDBUtil.removeFromArchiverConfig(applications[i]);
/* 4398 */         String query = "update AM_Script_Resource_Attributes_Mapper set REPORTS='0' where ATTRIBUTEID=" + applications[i];
/* 4399 */         ReportCustomAttributeUtil.disableReportsInAttributesExt(applications[i]);
/* 4400 */         ResultSet rs = null;
/*      */         try
/*      */         {
/* 4403 */           rs = AMConnectionPool.executeQueryStmt("select * from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + applications[i]);
/* 4404 */           if (rs.next())
/*      */           {
/* 4406 */             query = "update AM_CAM_DC_ATTRIBUTES set DISPLAYTYPE=0 where ATTRIBUTEID=" + applications[i];
/*      */           }
/* 4408 */           count = AMConnectionPool.executeUpdateStmt(query);
/*      */         }
/*      */         catch (SQLException exc) {
/*      */           int count;
/* 4412 */           exc.printStackTrace();
/*      */         }
/*      */         finally {
/* 4415 */           AMConnectionPool.closeResultSet(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 4421 */       ReportUtil.loadAllAttributeDetails();
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4425 */       ee.printStackTrace();
/*      */     }
/* 4427 */     String resourcetype = "Script Monitor";
/* 4428 */     String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 4429 */     return new ActionForward(path, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward enableReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4435 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4436 */     String[] applications = request.getParameterValues("checkbox");
/* 4437 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4462 */     if (applications != null)
/*      */     {
/* 4464 */       for (int i = 0; i < applications.length; i++)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4470 */         CAMDBUtil.addToArchiverConfig(applications[i]);
/* 4471 */         String query = "update AM_Script_Resource_Attributes_Mapper set REPORTS='1' where ATTRIBUTEID=" + applications[i];
/* 4472 */         ReportCustomAttributeUtil.enableReportsInAttributesExt(applications[i]);
/* 4473 */         ResultSet rs = null;
/*      */         try
/*      */         {
/* 4476 */           rs = AMConnectionPool.executeQueryStmt("select * from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + applications[i]);
/* 4477 */           if (rs.next())
/*      */           {
/* 4479 */             query = "update AM_CAM_DC_ATTRIBUTES set DISPLAYTYPE=1 where ATTRIBUTEID=" + applications[i];
/*      */           }
/* 4481 */           count = AMConnectionPool.executeUpdateStmt(query);
/*      */         }
/*      */         catch (SQLException exc) {
/*      */           int count;
/* 4485 */           exc.printStackTrace();
/*      */         }
/*      */         finally {
/* 4488 */           AMConnectionPool.closeResultSet(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 4493 */     String resourcetype = "Script Monitor";
/*      */     try
/*      */     {
/* 4496 */       ReportUtil.loadAllAttributeDetails();
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4500 */       ee.printStackTrace();
/*      */     }
/* 4502 */     String path = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/* 4503 */     return new ActionForward(path, true);
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
/*      */   public static void manageTableAtts(HttpServletRequest request, int resourceid, int healthid, String toappend, String monType, String archivalTable)
/*      */   {
/*      */     try
/*      */     {
/* 4518 */       String hastables = request.getParameter("tablespresent");
/* 4519 */       System.out.println("TABLES PRESENT===>" + hastables);
/* 4520 */       if ((hastables != null) && (hastables.equals("on")))
/*      */       {
/* 4522 */         int table_count = Integer.parseInt(request.getParameter("table_row"));
/* 4523 */         System.out.println("THE NO OF TABLES====>" + table_count);
/*      */         
/* 4525 */         String tablename = "";
/* 4526 */         String str_att_tab = "";
/* 4527 */         String num_att_tab = "";
/* 4528 */         String table_delimiter = "";
/* 4529 */         String primary_index = "";
/* 4530 */         for (int k = 1; k <= table_count; k++)
/*      */         {
/* 4532 */           tablename = request.getParameter("table" + k).trim();
/* 4533 */           str_att_tab = request.getParameter("stringatt" + k).trim();
/* 4534 */           num_att_tab = request.getParameter("numericatt" + k).trim();
/* 4535 */           table_delimiter = request.getParameter("cdl" + k).trim();
/* 4536 */           if ((table_delimiter != null) && (table_delimiter.equals("\\t")))
/*      */           {
/* 4538 */             table_delimiter = "t";
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 4543 */           primary_index = request.getParameter("pcatt" + k).trim();
/* 4544 */           ArrayList stringattributes_table = new ArrayList();
/* 4545 */           ArrayList numericattributes_table = new ArrayList();
/* 4546 */           ArrayList primaryattributes_table = new ArrayList();
/* 4547 */           if (!str_att_tab.equals(""))
/*      */           {
/* 4549 */             stringattributes_table = getAttributes(str_att_tab);
/*      */           }
/* 4551 */           if (!num_att_tab.equals(""))
/*      */           {
/* 4553 */             numericattributes_table = getAttributes(num_att_tab);
/*      */           }
/* 4555 */           if (!primary_index.equals(""))
/*      */           {
/* 4557 */             primaryattributes_table = getAttributes(primary_index);
/*      */           }
/* 4559 */           ArrayList tableAttributes = new ArrayList();
/* 4560 */           tableAttributes.add(stringattributes_table);
/* 4561 */           tableAttributes.add(numericattributes_table);
/* 4562 */           tableAttributes.add(primaryattributes_table);
/* 4563 */           Properties props = new Properties();
/* 4564 */           boolean isTablesInserted = NewMonitorUtil.insertTableAttributes(tableAttributes, tablename, resourceid, table_delimiter, healthid, toappend, props, null, null, monType, archivalTable);
/* 4565 */           System.out.println(tablename);
/* 4566 */           System.out.println(str_att_tab);
/* 4567 */           System.out.println(num_att_tab);
/* 4568 */           System.out.println(table_delimiter);
/* 4569 */           System.out.println(primary_index);
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*      */ 
/* 4579 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static int insertAttributes(int resourceid, ArrayList num_str, String type, boolean edit, String grouptype, ServletContext sct, String toappend)
/*      */   {
/* 4587 */     int temp_attid = getAttributeId();
/* 4588 */     int availabilityid = 2200;
/* 4589 */     int healthid = 2201;
/* 4590 */     int responsetimeid = 2202;
/* 4591 */     boolean insertResourcesAgain = false;
/*      */     
/* 4593 */     if ((type != null) && (type.equals("QENGINE")))
/*      */     {
/* 4595 */       availabilityid = 4300;
/* 4596 */       healthid = 4301;
/* 4597 */       responsetimeid = 4303;
/*      */     }
/* 4599 */     else if ((type != null) && (!type.equals("Script Monitor")) && (!edit))
/*      */     {
/* 4601 */       availabilityid = temp_attid;
/* 4602 */       healthid = availabilityid + 1;
/* 4603 */       responsetimeid = healthid + 1;
/* 4604 */       temp_attid = responsetimeid + 1;
/* 4605 */       NewMonitorUtil.healthAvailabilityEntry(type, availabilityid, healthid, responsetimeid, grouptype);
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 4610 */         if (sct != null)
/*      */         {
/* 4612 */           Hashtable availabilitykeys = (Hashtable)sct.getAttribute("availabilitykeys");
/* 4613 */           availabilitykeys.put(type, String.valueOf(availabilityid));
/* 4614 */           Hashtable healthkeys = (Hashtable)sct.getAttribute("healthkeys");
/* 4615 */           healthkeys.put(type, String.valueOf(healthid));
/*      */         }
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/*      */ 
/* 4622 */       FaultUtil.singleHealthandAvailId(String.valueOf(availabilityid), String.valueOf(healthid));
/*      */     }
/* 4624 */     else if ((type != null) && (!type.equals("Script Monitor")) && (edit))
/*      */     {
/* 4626 */       Properties p = DataCollectionControllerUtil.getAttributeProps(type);
/* 4627 */       healthid = Integer.parseInt(p.getProperty("Health"));
/* 4628 */       insertResourcesAgain = true;
/*      */     }
/*      */     
/* 4631 */     ArrayList stringattributes = (ArrayList)num_str.get(0);
/* 4632 */     ArrayList numericattributes = (ArrayList)num_str.get(1);
/*      */     
/*      */     try
/*      */     {
/* 4636 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/*      */       
/* 4638 */       String rulesmapperentry = "insert into AM_RCARULESMAPPER values (" + resourceid + "," + healthid + ",1)";
/* 4639 */       if ((type != null) && (type.equals("Script Monitor")) && (!edit))
/*      */       {
/*      */ 
/* 4642 */         toinsert.addBatch(rulesmapperentry);
/* 4643 */         if (FaultUtil.isRCAEnabled())
/*      */         {
/* 4645 */           String rcamapperentry_avail = "insert into AM_RCAMAPPER values (" + resourceid + "," + healthid + "," + resourceid + "," + availabilityid + ")";
/* 4646 */           String rcamapperentry_health = "insert into AM_RCAMAPPER values (" + resourceid + "," + healthid + "," + resourceid + "," + responsetimeid + ")";
/* 4647 */           toinsert.addBatch(rcamapperentry_avail);
/* 4648 */           toinsert.addBatch(rcamapperentry_health);
/*      */         }
/*      */       }
/*      */       
/* 4652 */       int att_type = 3;
/* 4653 */       if (type == null)
/*      */       {
/* 4655 */         type = "Script Monitor";
/*      */       }
/* 4657 */       for (int i = 0; i < stringattributes.size(); i++)
/*      */       {
/*      */         try
/*      */         {
/* 4661 */           String displayname = (String)stringattributes.get(i);
/*      */           
/* 4663 */           String attributename = displayname;
/* 4664 */           String am_attribtues_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + type + "','" + attributename + "'," + att_type + ",'" + displayname + "')";
/* 4665 */           String resource_attribute_mapper = "insert into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/* 4666 */           String attributesdependency = "insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")";
/* 4667 */           String am_att_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_Script_String_Data" + toappend + "','RESOURCEID','ATTRIBUTEID','VALUE','COLLECTIONTIME','1','','" + attributename + "','')";
/* 4668 */           toinsert.addBatch(am_attribtues_entry);
/* 4669 */           toinsert.addBatch(resource_attribute_mapper);
/* 4670 */           toinsert.addBatch(attributesdependency);
/* 4671 */           toinsert.addBatch(am_att_ext_entry);
/* 4672 */           System.out.println("Query====>" + am_attribtues_entry);
/* 4673 */           System.out.println("Query====>" + am_att_ext_entry);
/* 4674 */           System.out.println("Query====>" + resource_attribute_mapper);
/* 4675 */           System.out.println("Query====>" + attributesdependency);
/* 4676 */           resource_attribute_mapper = "replace into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/*      */           
/* 4678 */           String dbtype = "";
/* 4679 */           if (DBUtil.hasGlobalConfigValue("am.admin.dbtype")) {
/* 4680 */             dbtype = DBUtil.getGlobalConfigValue("am.admin.dbtype");
/*      */           } else {
/* 4682 */             dbtype = System.getProperty("am.dbserver.type");
/*      */           }
/* 4684 */           if ((dbtype.equalsIgnoreCase("mssql")) || (dbtype.equalsIgnoreCase("pgsql"))) {
/* 4685 */             resource_attribute_mapper = "delete from AM_Script_Resource_Attributes_Mapper where RESOURCEID=" + resourceid + " and ATTRIBUTEID=" + temp_attid;
/* 4686 */             EnterpriseUtil.addUpdateQueryToFile(resource_attribute_mapper);
/* 4687 */             resource_attribute_mapper = "insert into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/*      */           }
/*      */           
/* 4690 */           EnterpriseUtil.addUpdateQueryToFile(resource_attribute_mapper);
/*      */           
/* 4692 */           if (insertResourcesAgain)
/*      */           {
/* 4694 */             ArrayList al = NewMonitorUtil.getResourceIds(type);
/* 4695 */             for (int j = 0; j < al.size(); j++)
/*      */             {
/* 4697 */               String rcamapperentry_att = "insert into AM_RCAMAPPER values (" + al.get(j) + "," + healthid + "," + al.get(j) + "," + temp_attid + ")";
/* 4698 */               toinsert.addBatch(rcamapperentry_att);
/* 4699 */               System.out.println("Query====>" + rcamapperentry_att);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 4704 */           else if (FaultUtil.isRCAEnabled())
/*      */           {
/* 4706 */             String rcamapperentry_att = "insert into AM_RCAMAPPER values (" + resourceid + "," + healthid + "," + resourceid + "," + temp_attid + ")";
/* 4707 */             toinsert.addBatch(rcamapperentry_att);
/* 4708 */             System.out.println("Query====>" + rcamapperentry_att);
/*      */           }
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 4716 */         temp_attid++;
/*      */       }
/* 4718 */       att_type = 0;
/* 4719 */       for (int i = 0; i < numericattributes.size(); i++)
/*      */       {
/*      */         try
/*      */         {
/* 4723 */           String displayname = (String)numericattributes.get(i);
/*      */           
/* 4725 */           String attributename = displayname;
/* 4726 */           String am_attribtues_entry = "insert into AM_ATTRIBUTES(ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values(" + temp_attid + ",'" + type + "','" + attributename + "'," + att_type + ",'" + displayname + "')";
/* 4727 */           String resource_attribute_mapper = "insert into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/* 4728 */           String attributesdependency = "insert into AM_ATTRIBUTESDEPENDENCY VALUES (" + healthid + "," + temp_attid + ")";
/*      */           
/* 4730 */           String am_att_ext_entry = "INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ARCHIVEDDATA_TABLENAME,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + temp_attid + ",'AM_Script_Numeric_Data" + toappend + "','RESOURCEID','ATTRIBUTEID','VALUE','COLLECTIONTIME','1','','AM" + toappend + "_MinMaxAvgData','" + attributename + "','')";
/* 4731 */           toinsert.addBatch(am_attribtues_entry);
/* 4732 */           toinsert.addBatch(am_att_ext_entry);
/* 4733 */           toinsert.addBatch(resource_attribute_mapper);
/* 4734 */           toinsert.addBatch(attributesdependency);
/* 4735 */           System.out.println("Query====>" + am_attribtues_entry);
/* 4736 */           System.out.println("Query====>" + am_att_ext_entry);
/* 4737 */           System.out.println("Query====>" + resource_attribute_mapper);
/* 4738 */           System.out.println("Query====>" + attributesdependency);
/*      */           
/* 4740 */           resource_attribute_mapper = "replace into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/*      */           
/* 4742 */           String dbtype = "";
/* 4743 */           if (DBUtil.hasGlobalConfigValue("am.admin.dbtype")) {
/* 4744 */             dbtype = DBUtil.getGlobalConfigValue("am.admin.dbtype");
/*      */           } else {
/* 4746 */             dbtype = System.getProperty("am.dbserver.type");
/*      */           }
/* 4748 */           if ((dbtype.equalsIgnoreCase("mssql")) || (dbtype.equalsIgnoreCase("pgsql"))) {
/* 4749 */             resource_attribute_mapper = "delete from AM_Script_Resource_Attributes_Mapper where RESOURCEID=" + resourceid + " and ATTRIBUTEID=" + temp_attid;
/* 4750 */             EnterpriseUtil.addUpdateQueryToFile(resource_attribute_mapper);
/* 4751 */             resource_attribute_mapper = "insert into AM_Script_Resource_Attributes_Mapper values(" + resourceid + "," + temp_attid + ",'0')";
/*      */           }
/*      */           
/* 4754 */           EnterpriseUtil.addUpdateQueryToFile(resource_attribute_mapper);
/* 4755 */           if (insertResourcesAgain)
/*      */           {
/* 4757 */             ArrayList al = NewMonitorUtil.getResourceIds(type);
/* 4758 */             for (int j = 0; j < al.size(); j++)
/*      */             {
/* 4760 */               String rcamapperentry_att = "insert into AM_RCAMAPPER values (" + al.get(j) + "," + healthid + "," + al.get(j) + "," + temp_attid + ")";
/* 4761 */               toinsert.addBatch(rcamapperentry_att);
/* 4762 */               System.out.println("Query====>" + rcamapperentry_att);
/*      */             }
/*      */             
/*      */ 
/*      */           }
/* 4767 */           else if (FaultUtil.isRCAEnabled())
/*      */           {
/* 4769 */             String rcamapperentry_att = "insert into AM_RCAMAPPER values (" + resourceid + "," + healthid + "," + resourceid + "," + temp_attid + ")";
/* 4770 */             toinsert.addBatch(rcamapperentry_att);
/* 4771 */             System.out.println("Query====>" + rcamapperentry_att);
/*      */           }
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 4779 */         temp_attid++;
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 4784 */         toinsert.executeBatch();
/* 4785 */         toinsert.clearBatch();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 4789 */         System.out.println("Problem in executing the queries in the Batch statement");
/* 4790 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4796 */       System.out.println("Excpetion in the Insert Queries for the attributes");
/* 4797 */       exc.printStackTrace();
/*      */     }
/* 4799 */     return healthid;
/*      */   }
/*      */   
/*      */   public static ArrayList getResourceIds(String type)
/*      */   {
/* 4804 */     ArrayList al = new ArrayList();
/* 4805 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 4808 */       String qry = "select resourceid from AM_ManagedObject where type='" + type + "'";
/* 4809 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 4810 */       while (rs.next())
/*      */       {
/* 4812 */         al.add(rs.getString(1));
/*      */       }
/* 4814 */       rs.close();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4818 */       exc.printStackTrace();
/*      */     }
/* 4820 */     return al;
/*      */   }
/*      */   
/*      */   public static boolean deleteAttributes(ArrayList attributelist)
/*      */   {
/* 4825 */     boolean toreturn = true;
/*      */     try
/*      */     {
/* 4828 */       AMConnectionPool.getInstance();Statement todelete = AMConnectionPool.getConnection().createStatement();
/* 4829 */       for (int i = 0; i < attributelist.size(); i++)
/*      */       {
/* 4831 */         String att_id = (String)attributelist.get(i);
/* 4832 */         todelete.addBatch("delete from AM_ATTRIBUTES where ATTRIBUTEID=" + att_id);
/* 4833 */         todelete.addBatch("delete from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + att_id);
/* 4834 */         todelete.addBatch("delete from AM_MGVIEW where ATTRIBUTEID=" + att_id);
/* 4835 */         todelete.addBatch("delete from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + att_id);
/* 4836 */         todelete.addBatch("delete from AM_Script_String_Data where ATTRIBUTEID=" + att_id);
/* 4837 */         todelete.addBatch("delete from AM_Script_Numeric_Data where ATTRIBUTEID=" + att_id);
/* 4838 */         todelete.addBatch("delete from AM_Script_Resource_Attributes_Mapper where attributeid=" + att_id);
/* 4839 */         todelete.addBatch("delete from AM_ATTRIBUTESDEPENDENCY where CHILDID=" + att_id);
/* 4840 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_ATTRIBUTES where ATTRIBUTEID=" + att_id);
/* 4841 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + att_id);
/* 4842 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_MGVIEW where ATTRIBUTEID=" + att_id);
/* 4843 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_CAM_DC_ATTRIBUTES where ATTRIBUTEID=" + att_id);
/* 4844 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_Script_String_Data where ATTRIBUTEID=" + att_id);
/* 4845 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_Script_Numeric_Data where ATTRIBUTEID=" + att_id);
/* 4846 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_Script_Resource_Attributes_Mapper where attributeid=" + att_id);
/* 4847 */         EnterpriseUtil.addUpdateQueryToFile("delete from AM_ATTRIBUTESDEPENDENCY where CHILDID=" + att_id);
/*      */         
/* 4849 */         if (FaultUtil.isRCAEnabled())
/*      */         {
/* 4851 */           todelete.addBatch("delete from AM_RCAMAPPER  where CHILD_RESOURCEATTRIBUTEMAPPERID=" + att_id);
/* 4852 */           EnterpriseUtil.addUpdateQueryToFile("delete from AM_RCAMAPPER  where CHILD_RESOURCEATTRIBUTEMAPPERID=" + att_id);
/*      */         }
/*      */       }
/* 4855 */       todelete.executeBatch();
/* 4856 */       todelete.clearBatch();
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4860 */       System.out.println("Exception in deleting the attributes for the Script Monitor");
/*      */     }
/*      */     try
/*      */     {
/* 4864 */       ReportUtil.loadAllAttributeDetails();
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 4868 */       ee.printStackTrace();
/*      */     }
/* 4870 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public static ActionForward createscript1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 4876 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 4877 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/* 4878 */     Properties data = new Properties();
/* 4879 */     data.setProperty("resourceid", "642");
/* 4880 */     data.setProperty("delimiter", "=");
/* 4881 */     data.setProperty("outputfile", "/home/harikrishnan/DailyBuilds/sp1.txt");
/* 4882 */     data.setProperty("mode", "sh");
/* 4883 */     data.setProperty("pollinterval", "5");
/* 4884 */     data.setProperty("timeout", "5");
/* 4885 */     data.setProperty("arguements", "100,200");
/* 4886 */     data.setProperty("scriptname", "sp.sh");
/* 4887 */     String haid = null;
/* 4888 */     calldataCollector(data);
/* 4889 */     return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Script Monitor&haid=" + haid);
/*      */   }
/*      */   
/*      */   public ActionForward getScriptHostDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4894 */     String mapperid = request.getParameter("mapperid");
/* 4895 */     String mtype = request.getParameter("mtype");
/* 4896 */     String resourceid = request.getParameter("resourceid");
/* 4897 */     HashMap finalCredMap = new HashMap();
/* 4898 */     ResultSet rs = null;
/* 4899 */     AdminActions action = new AdminActions();
/* 4900 */     HashMap credentialList = action.getCredListForFileDirAndScriptMon(mtype);
/* 4901 */     request.setAttribute("credentialHash", credentialList);
/*      */     try {
/* 4903 */       rs = AMConnectionPool.executeQueryStmt("select CREDENTIALID from CREDENTIALTORESOURCEMAPPING  where RESOURCEID=" + resourceid);
/* 4904 */       if (rs.next()) {
/* 4905 */         ((AMActionForm)form).setCredentialID(rs.getString("CREDENTIALID"));
/* 4906 */         ((AMActionForm)form).setIsCredManager(true);
/* 4907 */         request.setAttribute("credentialIDSel", rs.getString("CREDENTIALID"));
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 4912 */       exc.printStackTrace();
/*      */     }
/*      */     finally {
/* 4915 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 4917 */     return new ActionForward("/jsp/ScriptHostDetails.jsp?mapperid=" + mapperid + "&mtype=" + mtype + "&resourceid=" + resourceid);
/*      */   }
/*      */   
/*      */   public static int checkexists(String filename, FormFile file, String[] file_checkname)
/*      */   {
/* 4922 */     file_checkname = AMScriptDataCollector.getfile(filename);
/* 4923 */     String filepath = ".." + File.separator + "scripts" + File.separator;
/* 4924 */     File dir = new File(filepath + file_checkname[0] + "_dir");
/* 4925 */     int ret = -1;
/*      */     try
/*      */     {
/* 4928 */       if ((dir.exists()) && (dir.isDirectory()))
/*      */       {
/* 4930 */         File scriptfile = new File(dir + File.separator + filename);
/* 4931 */         if (scriptfile.exists())
/*      */         {
/* 4933 */           ret = 0;
/*      */         }
/*      */         else
/*      */         {
/* 4937 */           ret = -1;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 4943 */         dir.mkdir();
/* 4944 */         String uploaddir = filepath + file_checkname[0] + "_dir" + File.separator;
/*      */         
/*      */ 
/* 4947 */         if (uploadfile(file, uploaddir))
/*      */         {
/*      */ 
/* 4950 */           ret = 1;
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e1)
/*      */     {
/* 4958 */       e1.printStackTrace();
/* 4959 */       ret = -1;
/*      */     }
/*      */     
/* 4962 */     return ret;
/*      */   }
/*      */   
/*      */   public static boolean isargs_exist(String arguements, String filename)
/*      */   {
/* 4967 */     boolean args_exist = false;
/* 4968 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 4969 */     ResultSet rs = null;
/*      */     
/* 4971 */     String query = "select arguements from AM_ScriptArgs where scriptname='" + filename + "'";
/*      */     try
/*      */     {
/* 4974 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 4975 */       while (rs.next())
/*      */       {
/* 4977 */         if (rs.getString(1).equalsIgnoreCase(arguements))
/*      */         {
/* 4979 */           args_exist = true;
/*      */         }
/*      */       }
/*      */       
/* 4983 */       rs.close();
/*      */     }
/*      */     catch (SQLException sqlexc)
/*      */     {
/* 4987 */       sqlexc.printStackTrace();
/* 4988 */       args_exist = false;
/*      */     }
/*      */     catch (Exception exc2)
/*      */     {
/* 4992 */       exc2.printStackTrace();
/* 4993 */       args_exist = false;
/*      */     }
/* 4995 */     return args_exist;
/*      */   }
/*      */   
/*      */   public static boolean uploadfile(FormFile file, String uploadDir)
/*      */   {
/*      */     try
/*      */     {
/* 5002 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 5003 */       InputStream stream = file.getInputStream();
/*      */       
/* 5005 */       OutputStream bos = new FileOutputStream(uploadDir + file);
/* 5006 */       int bytesRead = 0;
/* 5007 */       byte[] buffer = new byte[' '];
/* 5008 */       while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
/* 5009 */         bos.write(buffer, 0, bytesRead);
/*      */       }
/* 5011 */       bos.close();
/* 5012 */       String data = "The file has been written to \"" + uploadDir + file + "\"";
/*      */       
/*      */ 
/*      */ 
/* 5016 */       stream.close();
/* 5017 */       return true;
/*      */ 
/*      */     }
/*      */     catch (FileNotFoundException fnfe)
/*      */     {
/*      */ 
/* 5023 */       fnfe.printStackTrace();
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*      */ 
/* 5030 */       ioe.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 5035 */     return false;
/*      */   }
/*      */   
/*      */   public static String[] getfile(String filename)
/*      */   {
/* 5040 */     String[] ret = new String[2];
/* 5041 */     ret[0] = filename;
/* 5042 */     ret[1] = "bat";
/* 5043 */     if (System.getProperty("os.name").equalsIgnoreCase("windows"))
/*      */     {
/* 5045 */       ret[1] = "bat";
/*      */     }
/*      */     else {
/* 5048 */       ret[1] = "sh";
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 5054 */       StringTokenizer st = new StringTokenizer(filename, ".");
/* 5055 */       ret[0] = st.nextToken();
/* 5056 */       ret[1] = st.nextToken();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5060 */       System.out.println("Enter the valid file script file");
/* 5061 */       e.printStackTrace();
/*      */     }
/* 5063 */     return ret;
/*      */   }
/*      */   
/*      */   public static int mocreate(String displayname, String scriptname)
/*      */   {
/* 5068 */     return NewMonitorUtil.mocreate(displayname, scriptname, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String[] parseArguements(String arguements, char delimiter)
/*      */   {
/* 5075 */     String[] ret_arr = null;
/* 5076 */     int i = 0;
/* 5077 */     int delim_count = AMScriptDataCollector.char_occurance(arguements, delimiter);
/* 5078 */     ret_arr = new String[delim_count + 1];
/* 5079 */     StringTokenizer st = new StringTokenizer(arguements, String.valueOf(delimiter));
/* 5080 */     while (st.hasMoreTokens())
/*      */     {
/* 5082 */       ret_arr[(i++)] = st.nextToken();
/*      */     }
/*      */     
/* 5085 */     return ret_arr;
/*      */   }
/*      */   
/*      */   public static int char_occurance(String arguements, char delimiter)
/*      */   {
/* 5090 */     int count = 0;
/* 5091 */     for (int j = 0; j < arguements.length(); j++)
/*      */     {
/* 5093 */       if (arguements.charAt(j) == delimiter)
/* 5094 */         count++;
/*      */     }
/* 5096 */     return count;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/* 5104 */     String des = new String();
/* 5105 */     while (str.indexOf(find) != -1) {
/* 5106 */       des = des + str.substring(0, str.indexOf(find));
/* 5107 */       des = des + replace;
/* 5108 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/* 5110 */     des = des + str;
/* 5111 */     return des;
/*      */   }
/*      */   
/*      */   private String getQEngineHome(String scriptpath) {
/* 5115 */     return getParent(scriptpath, 5);
/*      */   }
/*      */   
/*      */   private String getProjectHome(String scriptpath) {
/* 5119 */     return getParent(scriptpath, 3);
/*      */   }
/*      */   
/*      */   private String getScriptDirectory(String scriptpath) {
/* 5123 */     return getParent(scriptpath, 1);
/*      */   }
/*      */   
/*      */   private String getParent(String scriptpath, int level)
/*      */   {
/* 5128 */     StringTokenizer tokens = new StringTokenizer(scriptpath, File.separator);
/* 5129 */     ArrayList tokenslist = new ArrayList();
/*      */     
/* 5131 */     while (tokens.hasMoreTokens())
/*      */     {
/* 5133 */       tokenslist.add(tokens.nextToken());
/*      */     }
/* 5135 */     int index = tokenslist.size() - level;
/* 5136 */     String qenginehome = "";
/* 5137 */     if (index > 0)
/*      */     {
/* 5139 */       qenginehome = (String)tokenslist.get(0);
/* 5140 */       if (scriptpath.startsWith("/"))
/*      */       {
/* 5142 */         qenginehome = "/" + (String)tokenslist.get(0);
/*      */       }
/* 5144 */       for (int i = 1; i < index; i++)
/*      */       {
/* 5146 */         qenginehome = qenginehome + File.separator + tokenslist.get(i);
/*      */       }
/*      */     }
/* 5149 */     return qenginehome;
/*      */   }
/*      */   
/*      */   private String getTestCaseName(String scriptpath)
/*      */   {
/* 5154 */     int index = scriptpath.lastIndexOf(File.separator);
/* 5155 */     scriptpath = scriptpath.substring(index);
/* 5156 */     scriptpath = StrUtil.removeText(scriptpath, ".tst");
/* 5157 */     log.info("TestCase Name is " + scriptpath);
/* 5158 */     return scriptpath;
/*      */   }
/*      */   
/*      */   private static String encode_type(String value, String type) {
/* 5162 */     String toreturn = "";
/* 5163 */     StringTokenizer st = new StringTokenizer(value, "\n");
/* 5164 */     while (st.hasMoreTokens())
/*      */     {
/* 5166 */       String temp = st.nextToken();
/* 5167 */       if (!temp.trim().equals(""))
/*      */       {
/* 5169 */         toreturn = toreturn + temp.trim() + "=" + type + "\n";
/*      */       }
/*      */     }
/* 5172 */     toreturn = toreturn.substring(0, toreturn.length() - 1);
/* 5173 */     return toreturn;
/*      */   }
/*      */   
/*      */   private static String[] decode_type(String value)
/*      */   {
/* 5178 */     String[] toreturn = new String[2];
/* 5179 */     toreturn[0] = "";
/* 5180 */     toreturn[1] = "";
/* 5181 */     StringTokenizer st = new StringTokenizer(value, "\n");
/* 5182 */     while (st.hasMoreTokens())
/*      */     {
/* 5184 */       String str = st.nextToken();
/* 5185 */       StringTokenizer st1 = new StringTokenizer(str, "=");
/* 5186 */       if (st1.hasMoreTokens())
/*      */       {
/*      */         try
/*      */         {
/* 5190 */           String first_part = st1.nextToken();
/* 5191 */           String second_part = st1.nextToken().trim();
/* 5192 */           if (second_part.equalsIgnoreCase("String"))
/*      */           {
/* 5194 */             toreturn[0] = (toreturn[0] + first_part + "\n");
/*      */           }
/*      */           else
/*      */           {
/* 5198 */             toreturn[1] = (toreturn[1] + first_part + "\n");
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 5203 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 5208 */     return toreturn;
/*      */   }
/*      */   
/*      */   private static Hashtable getExistingStringAttributes(int resid)
/*      */   {
/* 5213 */     String query = "select AM_ATTRIBUTES.ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where resourceid=" + resid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and TYPE=3";
/* 5214 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5215 */     Hashtable att_table = null;
/* 5216 */     att_table = new Hashtable();
/*      */     try
/*      */     {
/* 5219 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 5220 */       if (rs.next())
/*      */       {
/*      */ 
/* 5223 */         att_table.put(rs.getString(1), rs.getString(2));
/*      */       }
/* 5225 */       while (rs.next())
/*      */       {
/* 5227 */         att_table.put(rs.getString(1), rs.getString(2));
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5232 */       exc.printStackTrace();
/*      */     }
/* 5234 */     return att_table;
/*      */   }
/*      */   
/*      */   private static Hashtable getExistingNumericAttributes(int resid)
/*      */   {
/* 5239 */     String query = "select AM_ATTRIBUTES.ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where resourceid=" + resid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and TYPE=0";
/* 5240 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5241 */     Hashtable att_table = null;
/* 5242 */     att_table = new Hashtable();
/*      */     try
/*      */     {
/* 5245 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 5246 */       if (rs.next())
/*      */       {
/*      */ 
/* 5249 */         att_table.put(rs.getString(1), rs.getString(2));
/*      */       }
/* 5251 */       while (rs.next())
/*      */       {
/* 5253 */         att_table.put(rs.getString(1), rs.getString(2));
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5258 */       exc.printStackTrace();
/*      */     }
/* 5260 */     return att_table;
/*      */   }
/*      */   
/*      */   public static ArrayList getStringNumeric(int resid)
/*      */   {
/* 5265 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5266 */     ArrayList toreturn = new ArrayList();
/* 5267 */     String str = "";
/* 5268 */     ResultSet rs1 = null;
/* 5269 */     String query = "select DISPLAYNAME from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where resourceid=" + resid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and TYPE=3 order by DISPLAYNAME";
/*      */     try
/*      */     {
/* 5272 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 5273 */       if (rs1.next())
/*      */       {
/* 5275 */         str = rs1.getString(1);
/* 5276 */         while (rs1.next())
/*      */         {
/* 5278 */           str = str + "\n" + rs1.getString(1);
/*      */         }
/*      */       }
/* 5281 */       rs1.close();
/* 5282 */       toreturn.add(str);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5286 */       System.out.println("Exception in getting the string attributes!!!!");
/* 5287 */       exc.printStackTrace();
/*      */     }
/* 5289 */     str = "";
/* 5290 */     query = "select DISPLAYNAME from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where resourceid=" + resid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID and TYPE=0 order by DISPLAYNAME";
/*      */     try
/*      */     {
/* 5293 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 5294 */       if (rs1.next())
/*      */       {
/* 5296 */         str = rs1.getString(1);
/* 5297 */         while (rs1.next())
/*      */         {
/* 5299 */           str = str + "\n" + rs1.getString(1);
/*      */         }
/*      */       }
/* 5302 */       rs1.close();
/* 5303 */       toreturn.add(str);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 5307 */       System.out.println("Exception in getting the Numeric Attributes!!!");
/* 5308 */       exc.printStackTrace();
/*      */     }
/* 5310 */     return toreturn;
/*      */   }
/*      */   
/*      */   public static void healthAvailabilityEntry(String type, int availabilityid, int healthid, int responsetimeid, String grouptype)
/*      */   {
/* 5315 */     Statement st = null;
/* 5316 */     System.out.println("healthAvailabilityEntry called");
/* 5317 */     int group_avail = 7050;
/* 5318 */     int group_health = 7051;
/* 5319 */     if (grouptype != null)
/*      */     {
/* 5321 */       if (grouptype.equals("APP"))
/*      */       {
/* 5323 */         group_avail = 7000;
/* 5324 */         group_health = 7001;
/*      */       }
/* 5326 */       else if (grouptype.equals("MS"))
/*      */       {
/* 5328 */         group_avail = 7070;
/* 5329 */         group_health = 7071;
/*      */       }
/* 5331 */       else if (grouptype.equals("DBS"))
/*      */       {
/* 5333 */         group_avail = 7010;
/* 5334 */         group_health = 7011;
/*      */       }
/* 5336 */       else if (grouptype.equals("WEB"))
/*      */       {
/* 5338 */         group_avail = 7040;
/* 5339 */         group_health = 7041;
/*      */       }
/* 5341 */       else if (grouptype.equals("SER"))
/*      */       {
/* 5343 */         group_avail = 7030;
/* 5344 */         group_health = 7031;
/*      */       }
/* 5346 */       else if (grouptype.equals("SYS"))
/*      */       {
/* 5348 */         group_avail = 7020;
/* 5349 */         group_health = 7021;
/*      */       }
/* 5351 */       else if (grouptype.equals("CAM"))
/*      */       {
/* 5353 */         group_avail = 7050;
/* 5354 */         group_health = 7051;
/*      */       }
/* 5356 */       else if (grouptype.equals("ERP"))
/*      */       {
/* 5358 */         group_avail = 3700;
/* 5359 */         group_health = 3701;
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/* 5364 */       AMConnectionPool.getInstance();st = AMConnectionPool.getConnection().createStatement();
/* 5365 */       st.addBatch("insert into AM_ATTRIBUTES (ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values (" + availabilityid + ",'" + type + "','Availability',1,'Availability')");
/* 5366 */       st.addBatch("insert into AM_ATTRIBUTES (ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME) values (" + healthid + ",'" + type + "','Health',2,'Health')");
/* 5367 */       st.addBatch("insert into AM_ATTRIBUTES (ATTRIBUTEID,RESOURCETYPE,ATTRIBUTE,TYPE,DISPLAYNAME,UNITS) values (" + responsetimeid + ",'" + type + "','ResponseTime',0,'Response Time','ms')");
/* 5368 */       st.addBatch("INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + availabilityid + ",'AM_ManagedObjectData','RESID','-1','AVAILABILITY','COLLECTIONTIME','1','','Availability','')");
/* 5369 */       st.addBatch("INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + healthid + ",'AM_ManagedObjectData','RESID','-1','HEALTH','COLLECTIONTIME','1','','Health','')");
/* 5370 */       st.addBatch("INSERT INTO AM_ATTRIBUTES_EXT(ATTRIBUTEID,DATATABLE,RESID_COL,ATTID_COL,VALUE_COL,COLTIME_VAL,ATTRIBUTE_LEVEL,EXPRESSION,ISARCHIVEING,REPORTS_ENABLED,ARCHIVEDDATA_TABLENAME,ATTRIBUTE_SHORTNAME,CONDITIONS) VALUES (" + responsetimeid + ",'AM_ManagedObjectData','RESID','-1','RESPONSETIME','COLLECTIONTIME','1','',1,1,'AM_RESPONSETIME_MinMaxAvgData','ResponseTime','')");
/* 5371 */       st.addBatch("INSERT INTO AM_MGVIEW VALUES(" + availabilityid + ",'NO','0','RAW')");
/* 5372 */       st.addBatch("INSERT INTO AM_MGVIEW VALUES(" + healthid + ",'NO','0','RAW')");
/* 5373 */       st.addBatch("INSERT INTO AM_MGVIEW VALUES(" + responsetimeid + ",'NO','0','RAW')");
/*      */       
/*      */ 
/* 5376 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(" + group_avail + "," + availabilityid + ")");
/* 5377 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(" + group_health + "," + healthid + ")");
/* 5378 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(17," + availabilityid + ")");
/* 5379 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(18," + healthid + ")");
/* 5380 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(" + healthid + "," + availabilityid + ")");
/* 5381 */       st.addBatch("insert into AM_ATTRIBUTESDEPENDENCY (PARENTID,CHILDID) values(" + healthid + "," + responsetimeid + ")");
/* 5382 */       st.addBatch("insert into AM_ArchiverConfig values ('AM_ManagedObjectData','RESPONSETIME','RESID'," + responsetimeid + ")");
/* 5383 */       st.executeBatch(); return;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5387 */       System.out.println("Exception while Executing the batch for the montior type essential attributes entry");
/* 5388 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 5394 */         st.close();
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
/*      */   public void addScriptHostDetailsInAAM(Properties argsProps)
/*      */   {
/* 5409 */     PreparedStatement ps = null;
/*      */     try {
/* 5411 */       String serversite = argsProps.getProperty("serversite");
/* 5412 */       String choosehost = argsProps.getProperty("choosehost");
/* 5413 */       AMLog.debug("CreateScriptMonitor.addScriptHostDetailsInAAM(): serversite: " + serversite + "     choosehost:" + choosehost);
/* 5414 */       if ((serversite != null) && (serversite.equals("remote")) && (choosehost != null) && (choosehost.equals("-1")))
/*      */       {
/*      */ 
/* 5417 */         boolean isCredentialManager = "true".equals(argsProps.getProperty("isCredManager"));
/* 5418 */         CredentialManagerUtil credUtil = new CredentialManagerUtil();
/* 5419 */         Long credentialID = Long.valueOf(0L);
/* 5420 */         Properties credentialSelectedProperties = null;
/* 5421 */         String username = "";String password = "";String prompt = "";String passphrase = "";String privateKey = "";
/* 5422 */         String host = argsProps.getProperty("host");
/* 5423 */         String mode = argsProps.getProperty("monitoringmode");
/* 5424 */         if (isCredentialManager)
/*      */         {
/* 5426 */           credentialID = new Long(argsProps.getProperty("credentialID"));
/* 5427 */           credentialSelectedProperties = credUtil.rowNameVsValue(credentialID.longValue());
/* 5428 */           username = credentialSelectedProperties.getProperty("username");
/* 5429 */           password = credentialSelectedProperties.getProperty("password");
/* 5430 */           if (credentialSelectedProperties.getProperty("prompt") != null)
/*      */           {
/* 5432 */             prompt = credentialSelectedProperties.getProperty("prompt");
/*      */           }
/* 5434 */           if (("ssh".equalsIgnoreCase(mode)) && (credentialSelectedProperties.getProperty("sshPKAuth", "false").equalsIgnoreCase("true")))
/*      */           {
/* 5436 */             password = credentialSelectedProperties.getProperty("passPhrase");
/* 5437 */             updateKeyFile(host, privateKey);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 5442 */           username = argsProps.getProperty("username");
/* 5443 */           password = argsProps.getProperty("password");
/* 5444 */           passphrase = argsProps.getProperty("passphrase");
/* 5445 */           prompt = argsProps.getProperty("prompt");
/* 5446 */           privateKey = argsProps.getProperty("description");
/* 5447 */           if ((mode != null) && (mode.equalsIgnoreCase("ssh")) && (privateKey != null) && (privateKey.trim().length() > 0))
/*      */           {
/*      */ 
/* 5450 */             passphrase = passphrase == null ? "" : passphrase;
/* 5451 */             mode = "SSH_KEY";
/* 5452 */             password = passphrase;
/* 5453 */             updateKeyFile(host, privateKey);
/*      */           }
/*      */         }
/* 5456 */         String port = argsProps.getProperty("port");
/* 5457 */         int tableid = NewMonitorUtil.getNextTableId("AM_SCRIPTHOSTDETAILS", "ID");
/* 5458 */         StringBuilder query = new StringBuilder();
/* 5459 */         query.append("insert into AM_SCRIPTHOSTDETAILS(ID,HOSTNAME,USERNAME,PASSWORD,MODE,PROMPT,PORT) values(");
/* 5460 */         query.append(tableid).append(",");
/* 5461 */         query.append("'").append(host).append("'").append(",");
/* 5462 */         query.append("?").append(",");
/* 5463 */         query.append(DBQueryUtil.encode(password)).append(",");
/* 5464 */         query.append("'").append(mode).append("'").append(",");
/* 5465 */         query.append("'").append(prompt).append("'").append(",");
/* 5466 */         query.append(port).append(")");
/* 5467 */         ps = AMConnectionPool.getPreparedStatement(query.toString());
/* 5468 */         ps.setString(1, username);
/* 5469 */         ps.executeUpdate();
/*      */       }
/*      */       return;
/* 5472 */     } catch (Exception ex) { ex.printStackTrace();
/*      */     } finally {
/* 5474 */       if (ps != null) {
/*      */         try {
/* 5476 */           ps.close();
/*      */         } catch (Exception ex1) {
/* 5478 */           ex1.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void getRemoteHostDetails(Properties argsProps)
/*      */   {
/* 5490 */     ResultSet rs = null;
/*      */     try {
/* 5492 */       Properties hostDetails = new Properties();
/* 5493 */       String serversite = argsProps.getProperty("serversite");
/* 5494 */       String choosehost = argsProps.getProperty("choosehost");
/* 5495 */       if ((serversite != null) && (serversite.equals("remote")) && (choosehost != null) && (!choosehost.equals("-1")))
/*      */       {
/* 5497 */         choosehost = choosehost.substring(choosehost.lastIndexOf(",") + 1);
/* 5498 */         AMLog.debug("CreateScriptMonitor.getRemoteHostDetails(): choosehost:" + choosehost);
/* 5499 */         String hostDetailsQuery = "select HOSTNAME,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,MODE,PROMPT,PORT,USERNAME as SNMPCOMMUNITY from AM_SCRIPTHOSTDETAILS where ID=" + choosehost;
/* 5500 */         rs = AMConnectionPool.executeQueryStmt(hostDetailsQuery);
/* 5501 */         if (rs.next()) {
/* 5502 */           hostDetails.put("host", rs.getString("HOSTNAME"));
/* 5503 */           hostDetails.put("username", rs.getString("USERNAME"));
/* 5504 */           hostDetails.put("password", rs.getString("PASSWORD"));
/* 5505 */           hostDetails.put("monitoringmode", rs.getString("MODE"));
/* 5506 */           hostDetails.put("prompt", Translate.decode(rs.getString("PROMPT")));
/* 5507 */           hostDetails.put("port", rs.getString("PORT"));
/*      */         }
/* 5509 */         String mode = (String)hostDetails.get("monitoringmode");
/* 5510 */         AMLog.debug("CreateScriptMonitor.getRemoteHostDetails(): mode:" + mode);
/* 5511 */         if ((mode != null) && (mode.equals("SSH_KEY"))) {
/* 5512 */           hostDetails.put("sshkey", "on");
/* 5513 */           hostDetails.put("passphrase", hostDetails.get("password"));
/* 5514 */           String fileName = System.getProperty("webnms.rootdir") + File.separator + "adventnet_ssh_privateKey_file.txt_" + hostDetails.get("host");
/* 5515 */           hostDetails.put("description", FileUtil.getContentofFile(fileName));
/*      */         }
/* 5517 */         hostDetails.put("choosehost", "-1");
/* 5518 */         argsProps.putAll(hostDetails);
/*      */       }
/*      */     } catch (Exception ex) {
/* 5521 */       ex.printStackTrace();
/*      */     } finally {
/* 5523 */       AMConnectionPool.closeStatement(rs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String errorHandling(int exitcode, String serverpath)
/*      */   {
/* 5559 */     String error = null;
/* 5560 */     if (System.getProperty("os.name").startsWith("Windows")) {
/* 5561 */       error = FormatUtil.getString("am.webclient.command.nocommand");
/*      */ 
/*      */     }
/* 5564 */     else if (exitcode == 1) {
/* 5565 */       error = FormatUtil.getString("am.webclient.command.nocommand.error");
/*      */     }
/* 5567 */     else if (exitcode == 2) {
/* 5568 */       error = FormatUtil.getString("am.webclient.command.nocommand.incorrect");
/*      */     }
/* 5570 */     else if (exitcode == 126) {
/* 5571 */       error = FormatUtil.getString("am.webclient.command.nocommand.noauthentication");
/*      */     }
/* 5573 */     else if (exitcode == 127) {
/* 5574 */       error = FormatUtil.getString("am.webclient.command.nocommand.notfound");
/*      */     }
/*      */     else {
/* 5577 */       error = FormatUtil.getString("am.webclient.command.nocommand.failed");
/*      */     }
/*      */     
/* 5580 */     return error;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CreateScriptMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */