/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMServerUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMActionExecuter;
/*      */ import com.adventnet.appmanager.fault.AMAlertMailer;
/*      */ import com.adventnet.appmanager.fault.AMFaultProcess;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.fault.SmtpMailer;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.discovery.HostDiscoveryHandler;
/*      */ import com.adventnet.appmanager.server.framework.AMUrlMonitorProcess;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.RBMMonitor;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.UrlMonitor;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.UrlSequence;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.SAPCCMSDataCollector;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.SmtpEMailer;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.adventnet.nms.admin.ShutDownAPIImpl;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.PerformDataCollection;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InterruptedIOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.ConnectException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.NoRouteToHostException;
/*      */ import java.net.ProtocolException;
/*      */ import java.net.URLDecoder;
/*      */ import java.net.UnknownHostException;
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
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
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
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ public final class AdminTools extends DispatchAction
/*      */ {
/*   74 */   protected Scheduler schedular = null;
/*      */   
/*      */ 
/*   77 */   private static String htmlMailTpl = getHTMLMailTpl();
/*      */   
/*      */ 
/*      */   public ActionForward updateAttributesinMGView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*   82 */     String type = request.getParameter("type");
/*   83 */     String[] attids = request.getParameterValues("selectedresource");
/*      */     
/*      */     try
/*      */     {
/*   87 */       if (type.equals("Linux"))
/*      */       {
/*   89 */         int i = 0;
/*   90 */         String temp = "(";
/*   91 */         Hashtable server_props = Constants.getHostProperties();
/*   92 */         if (attids != null)
/*      */         {
/*   94 */           for (i = 0; i < attids.length; i++)
/*      */           {
/*   96 */             temp = temp + "'" + attids[i] + "',";
/*      */             try
/*      */             {
/*   99 */               Enumeration en = server_props.keys();
/*  100 */               while (en.hasMoreElements())
/*      */               {
/*  102 */                 String ele = (String)en.nextElement();
/*  103 */                 Properties p = (Properties)server_props.get(ele);
/*  104 */                 String id = p.getProperty(attids[i]);
/*  105 */                 if (id != null)
/*      */                 {
/*      */ 
/*      */ 
/*  109 */                   String qry = "INSERT INTO AM_MGVIEW VALUES(" + id + ",'NO','0','RAW')";
/*      */                   try
/*      */                   {
/*  112 */                     AMConnectionPool.executeUpdateStmt(qry);
/*      */ 
/*      */                   }
/*      */                   catch (Exception exc) {}
/*      */                 }
/*      */                 
/*      */               }
/*      */             }
/*      */             catch (Exception exc)
/*      */             {
/*  122 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*  125 */           temp = temp.substring(0, temp.length() - 1);
/*  126 */           temp = temp + ")";
/*      */         }
/*      */         else
/*      */         {
/*  130 */           temp = "('')";
/*      */         }
/*      */         
/*  133 */         String qr = "select AM_ATTRIBUTES.attributeid,attribute from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where RESOURCETYPE='" + type + "' AND AM_ATTRIBUTES_EXT.attributeid=AM_ATTRIBUTES.ATTRIBUTEID AND ATTRIBUTE_LEVEL=1 AND type <>1 and type <>2 and attribute not in" + temp;
/*      */         
/*  135 */         ResultSet rs = AMConnectionPool.executeQueryStmt(qr);
/*      */         try
/*      */         {
/*  138 */           while (rs.next())
/*      */           {
/*      */             try
/*      */             {
/*  142 */               Enumeration en = server_props.keys();
/*  143 */               while (en.hasMoreElements())
/*      */               {
/*  145 */                 String ele = (String)en.nextElement();
/*  146 */                 Properties p = (Properties)server_props.get(ele);
/*  147 */                 String id = p.getProperty(rs.getString(2));
/*  148 */                 if (id != null)
/*      */                 {
/*      */ 
/*      */ 
/*  152 */                   String qry = "DELETE FROM AM_MGVIEW WHERE ATTRIBUTEID=" + id;
/*  153 */                   AMConnectionPool.executeUpdateStmt(qry);
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception exc) {
/*  158 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  164 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  168 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  173 */         String qr = "select AM_ATTRIBUTES.attributeid,attribute from AM_ATTRIBUTES,AM_ATTRIBUTES_EXT where RESOURCETYPE='" + type + "' AND AM_ATTRIBUTES_EXT.attributeid=AM_ATTRIBUTES.ATTRIBUTEID AND ATTRIBUTE_LEVEL=1 AND type <>1 and type <>2";
/*  174 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  177 */           rs = AMConnectionPool.executeQueryStmt(qr);
/*  178 */           if (attids != null)
/*      */           {
/*  180 */             while (rs.next())
/*      */             {
/*  182 */               String att = rs.getString(1);
/*  183 */               int i = 0;
/*  184 */               for (i = 0; i < attids.length; i++)
/*      */               {
/*  186 */                 if (attids[i].equals(att))
/*      */                 {
/*      */                   try
/*      */                   {
/*  190 */                     String qry1 = "INSERT INTO AM_MGVIEW VALUES(" + att + ",'NO','0','RAW')";
/*  191 */                     AMConnectionPool.executeUpdateStmt(qry1);
/*      */                   }
/*      */                   catch (Exception exc) {}
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*  200 */               if (i == attids.length)
/*      */               {
/*      */                 try
/*      */                 {
/*  204 */                   String qry = "DELETE FROM AM_MGVIEW WHERE ATTRIBUTEID=" + att;
/*  205 */                   AMConnectionPool.executeUpdateStmt(qry);
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/*  209 */                   exc.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  216 */           while (rs.next())
/*      */           {
/*  218 */             String att = rs.getString(1);
/*      */             try
/*      */             {
/*  221 */               String qry = "DELETE FROM AM_MGVIEW WHERE ATTRIBUTEID=" + att;
/*  222 */               AMConnectionPool.executeUpdateStmt(qry);
/*      */             }
/*      */             catch (Exception exc)
/*      */             {
/*  226 */               exc.printStackTrace();
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  233 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*  237 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  243 */       exc.printStackTrace();
/*      */     }
/*  245 */     return new ActionForward("/jsp/EditMGView.jsp?type=" + type);
/*      */   }
/*      */   
/*      */   public ActionForward shutdownServer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  250 */     String user = request.getRemoteUser();
/*  251 */     if (!user.equalsIgnoreCase("admin"))
/*      */     {
/*  253 */       ActionErrors errors = new ActionErrors();
/*  254 */       if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */       {
/*  256 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.shutdown.notautorised.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") })));
/*      */       }
/*      */       else
/*      */       {
/*  260 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.webclient.shutdown.notautorised.text", new String[] { OEMUtil.getOEMString("product.name") })));
/*      */       }
/*      */       
/*  263 */       saveErrors(request, errors);
/*  264 */       return new ActionForward("/showTile.do?TileName=Tile.AdminConf");
/*      */     }
/*  266 */     String alert = request.getParameter("alert");
/*  267 */     if ((alert != null) && (!alert.equalsIgnoreCase("null")))
/*      */     {
/*  269 */       return new ActionForward("Tile.ShutdownServer");
/*      */     }
/*      */     try
/*      */     {
/*  273 */       if (OEMUtil.isOEM())
/*      */       {
/*  275 */         File f = new File("shutdown.lock");
/*  276 */         if (!f.exists())
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  282 */           f.createNewFile();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  288 */       ex.printStackTrace();
/*      */     }
/*      */     try {
/*  291 */       System.out.println("Shuting Down Applications Manager through the WebClient");
/*  292 */       ShutDownAPIImpl.getInstance().shutDownNMSServer(true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  296 */       e.printStackTrace();
/*      */     }
/*  298 */     return new ActionForward("/showTile.do?TileName=Tile.AdminConf");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward servercheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  305 */     return new ActionForward("/showTile.do?TileName=Tile.AdminConf");
/*      */   }
/*      */   
/*      */   public ActionForward restartCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  310 */     return new ActionForward("/applications.do");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward showRules(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  318 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  321 */       String query = "SELECT RULEID,RULENAME,TYPE,SEVERITY,STATUS,TIMEPERIOD,TIMEINTERVAL,TIMEUNIT FROM AM_ALERTRULE ORDER BY RULENAME";
/*  322 */       boolean isPrivileged = Constants.isPrivilegedUser(request);
/*  323 */       if (isPrivileged) {
/*  324 */         Vector ruleidList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(DelegatedUserRoleUtil.getLoginUserid(request), 7);
/*  325 */         query = "SELECT RULEID,RULENAME,TYPE,SEVERITY,STATUS,TIMEPERIOD,TIMEINTERVAL,TIMEUNIT FROM AM_ALERTRULE WHERE " + DBUtil.getCondition("RULEID", ruleidList) + " ORDER BY RULENAME";
/*      */       }
/*  327 */       AMLog.debug("showRules :: Show  Alert Rule query : " + query);
/*  328 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  329 */       ArrayList row = new ArrayList();
/*  330 */       while (rs.next())
/*      */       {
/*  332 */         ArrayList data = new ArrayList();
/*  333 */         String id = rs.getString("RULEID");
/*  334 */         String name = rs.getString("RULENAME");
/*  335 */         String type = rs.getString("TYPE");
/*  336 */         if (type.equals("1")) {
/*  337 */           type = "All Monitors";
/*      */         }
/*  339 */         else if (type.equals("2")) {
/*  340 */           type = "Monitor Group";
/*      */         }
/*  342 */         else if (type.equals("3")) {
/*  343 */           type = "Monitor Type";
/*      */         }
/*  345 */         else if (type.equals("4"))
/*      */         {
/*  347 */           type = FormatUtil.getString("am.webclient.alertescalation.showrule.selectedmonitor.text");
/*      */         }
/*  349 */         String severity = rs.getString("SEVERITY");
/*  350 */         String status = rs.getString("STATUS");
/*  351 */         int time = rs.getInt("TIMEPERIOD");
/*  352 */         String timeunit = rs.getString("TIMEUNIT");
/*  353 */         int timeint = rs.getInt("TIMEINTERVAL");
/*  354 */         data.add(id);
/*  355 */         data.add(name);
/*  356 */         data.add(type);
/*  357 */         data.add(severity);
/*  358 */         data.add(status);
/*  359 */         data.add(Integer.valueOf(time));
/*  360 */         data.add(timeunit);
/*  361 */         data.add(Integer.valueOf(timeint));
/*  362 */         row.add(data);
/*      */       }
/*  364 */       request.setAttribute("data", row);
/*  365 */       request.setAttribute("tabtoselect", "3");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  369 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  373 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  375 */     return new ActionForward("/jsp/ShowAlertEscalationDetails.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward newRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  383 */       int rid = 0;
/*  384 */       setSelectedMonitors(form, rid, request);
/*  385 */       getActions(mapping, form, request, response);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  389 */       e.printStackTrace();
/*      */     }
/*  391 */     return new ActionForward("/jsp/AlertEscalation.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward addRuledetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  397 */     AMActionForm am = (AMActionForm)form;
/*  398 */     ActionMessages messages = new ActionMessages();
/*  399 */     String name = am.getRulename();
/*  400 */     String execute = request.getParameter("execute");
/*  401 */     String ignoreAckedAlerts = request.getParameter("ack");
/*      */     
/*  403 */     String ruleid = null;
/*  404 */     if (execute.equals("false"))
/*      */     {
/*      */       try {
/*  407 */         String severity = am.getMonitorseverity();
/*  408 */         int type = am.getMonitortype();
/*  409 */         String timeunit = am.getTimeunit();
/*  410 */         String sendmail = am.getSendmail();
/*  411 */         String[] mgrpid = new String[0];
/*      */         try {
/*  413 */           mgrpid = am.getSelect();
/*      */         } catch (Exception npe) {
/*  415 */           npe.printStackTrace();
/*      */         }
/*  417 */         String[] selectedMonitors = am.getMaintenanceCombo2();
/*      */         
/*  419 */         int timeperiod = am.getTimeperiod();
/*  420 */         int timeint = am.getTimeinterval();
/*  421 */         System.out.println("the optmail value:" + am.getOptmail());
/*  422 */         String opt = "false";
/*  423 */         if (am.getOptmail() != null) {
/*  424 */           opt = am.getOptmail();
/*      */         } else {
/*  426 */           opt = "false";
/*      */         }
/*      */         
/*  429 */         PreparedStatement ps = null;
/*      */         try {
/*  431 */           int ruleID = DBQueryUtil.getIncrementedID("RULEID", "AM_ALERTRULE");
/*      */           
/*  433 */           ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_ALERTRULE(RULEID,RULENAME,TYPE,STATUS,SEVERITY,TIMEPERIOD,TIMEINTERVAL,TIMEUNIT,OPTIMISED,IGNOREACKED) values(" + ruleID + ",?,?,?,?,?,?,?,?,?)");
/*      */           
/*  435 */           ps.setString(1, name);
/*  436 */           ps.setInt(2, type);
/*  437 */           ps.setInt(3, 1);
/*  438 */           ps.setInt(4, Integer.parseInt(severity));
/*  439 */           ps.setInt(5, timeperiod);
/*  440 */           ps.setInt(6, timeint);
/*  441 */           ps.setString(7, timeunit);
/*  442 */           ps.setString(8, opt);
/*  443 */           ps.setString(9, ignoreAckedAlerts);
/*      */           
/*  445 */           int i = ps.executeUpdate();
/*  446 */           if (i > 0) {
/*  447 */             DelegatedUserRoleUtil.addEntryToConfigUserTable(ruleID, DelegatedUserRoleUtil.getLoginUserid(request), -1, 7);
/*  448 */             AMLog.debug("AdminTools :: addRuledetails : Adding Rule to AM_CONFIGTOUSER_MAPPING table : " + ruleID);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/*  458 */             if (ps != null) {
/*  459 */               ps.close();
/*      */             }
/*      */           }
/*      */           catch (Exception ee)
/*      */           {
/*  464 */             ee.printStackTrace();
/*      */           }
/*      */           
/*      */ 
/*  468 */           rsid = null;
/*      */         }
/*      */         catch (Exception se)
/*      */         {
/*  452 */           se.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/*      */           try
/*      */           {
/*  458 */             if (ps != null) {
/*  459 */               ps.close();
/*      */             }
/*      */           }
/*      */           catch (Exception ee)
/*      */           {
/*  464 */             ee.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  470 */         String query = "SELECT MAX(RULEID) FROM AM_ALERTRULE";
/*  471 */         AMLog.debug("Show  Alert Rule query : " + query);
/*  472 */         ResultSet rsid = AMConnectionPool.executeQueryStmt(query);
/*  473 */         while (rsid.next()) {
/*  474 */           ruleid = rsid.getString(1);
/*      */         }
/*      */         
/*  477 */         if (mgrpid.length != 0) {
/*  478 */           for (int j = 0; j < mgrpid.length; j++) {
/*  479 */             String inserttypemapper = "insert into AM_ALERTTYPEMAPPER (ID,RULEID,MONITOR_ID) values(" + DBQueryUtil.getIncrementedID("ID", "AM_ALERTTYPEMAPPER") + "," + ruleid + "," + mgrpid[j] + ")";
/*  480 */             AMConnectionPool.executeUpdateStmt(inserttypemapper);
/*      */           }
/*      */         }
/*      */         
/*  484 */         if (selectedMonitors.length != 0) {
/*  485 */           boolean appendComma = false;
/*  486 */           StringBuilder insertTypeMapper = new StringBuilder("insert into AM_ALERTTYPEMAPPER (ID,RULEID,MONITOR_ID) values");
/*  487 */           int id = DBQueryUtil.getIncrementedID("ID", "AM_ALERTTYPEMAPPER");
/*  488 */           for (String mID : selectedMonitors) {
/*  489 */             int monitorID = Integer.parseInt(mID);
/*  490 */             if (appendComma) insertTypeMapper.append(",");
/*  491 */             insertTypeMapper = insertTypeMapper.append("(").append(id++).append(",").append(ruleid).append(",").append(monitorID).append(")");
/*  492 */             appendComma = true;
/*      */           }
/*  494 */           AMConnectionPool.executeUpdateStmt(insertTypeMapper.toString());
/*      */         }
/*      */         
/*  497 */         String insertactionmapper = "insert into AM_ALERTACTIONMAPPER (ID,RULEID,ACTIONID) values(" + DBQueryUtil.getIncrementedID("ID", "AM_ALERTACTIONMAPPER") + "," + ruleid + "," + Integer.parseInt(sendmail) + ")";
/*  498 */         AMConnectionPool.executeUpdateStmt(insertactionmapper);
/*      */         
/*      */ 
/*  501 */         long ruletime = 0L;
/*  502 */         if (timeunit.equals("Hours"))
/*      */         {
/*      */ 
/*  505 */           ruletime = timeperiod * 60 * 60 * 1000;
/*  506 */           ruletime /= 3L;
/*  507 */         } else if (timeunit.equals("Minutes"))
/*      */         {
/*  509 */           ruletime = timeperiod * 60 * 1000;
/*  510 */           ruletime /= 2L;
/*      */         }
/*      */         
/*  513 */         AMAlertMailer aam = new AMAlertMailer(String.valueOf(ruleid), "false");
/*      */         
/*  515 */         this.schedular = Scheduler.getScheduler("main");
/*  516 */         this.schedular.scheduleTask(aam, System.currentTimeMillis());
/*  517 */         AMFaultProcess.instances.put(String.valueOf(ruleid), aam);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  521 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     else {
/*  525 */       ruleid = request.getParameter("ruleid");
/*      */       try {
/*  527 */         AMAlertMailer aam = new AMAlertMailer(String.valueOf(ruleid), "true");
/*  528 */         Thread t = new Thread(aam);
/*      */         
/*  530 */         t.start();
/*  531 */         t.join(120000L);
/*      */         
/*  533 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString(aam.message)));
/*  534 */         saveMessages(request, messages);
/*      */       }
/*      */       catch (Exception e) {
/*  537 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  540 */     request.setAttribute("tabtoselect", "3");
/*  541 */     return new ActionForward("/alertEscalation.do?method=showRules", true);
/*      */   }
/*      */   
/*      */   public ActionForward editRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  546 */     response.setContentType("text/html; charset=UTF-8");
/*  547 */     AMActionForm am = (AMActionForm)form;
/*  548 */     ResultSet rs1 = null;
/*  549 */     String rid = request.getParameter("rid");
/*  550 */     ResultSet rs = null;
/*  551 */     String name = null;
/*  552 */     int status = 0;
/*  553 */     String severity = null;
/*  554 */     String actionname = null;
/*  555 */     int type = 0;
/*  556 */     int timeperiod = 0;
/*  557 */     String timeunit = null;
/*  558 */     int timeint = 0;
/*  559 */     String resids = null;
/*  560 */     String optmail = null;
/*  561 */     String ignoreAckedAlerts = null;
/*  562 */     ArrayList rids = new ArrayList();
/*  563 */     setSelectedMonitors(form, Integer.parseInt(rid), request);
/*      */     
/*  565 */     String editquery = "select AM_ACTIONPROFILE.ID,NAME,RULENAME,AM_ALERTRULE.TYPE,SEVERITY,TIMEPERIOD,TIMEINTERVAL,TIMEUNIT,OPTIMISED,IGNOREACKED from AM_ALERTRULE,AM_ALERTACTIONMAPPER left outer join  AM_ACTIONPROFILE on AM_ACTIONPROFILE.ID=AM_ALERTACTIONMAPPER.ACTIONID where AM_ALERTRULE.RULEID= AM_ALERTACTIONMAPPER.RULEID and AM_ALERTRULE.RULEID=" + rid;
/*      */     try
/*      */     {
/*  568 */       rs = AMConnectionPool.executeQueryStmt(editquery);
/*  569 */       if (rs.next())
/*      */       {
/*  571 */         name = rs.getString("RULENAME");
/*  572 */         type = rs.getInt("TYPE");
/*  573 */         severity = rs.getString("SEVERITY");
/*  574 */         timeunit = rs.getString("TIMEUNIT");
/*  575 */         timeperiod = rs.getInt("TIMEPERIOD");
/*  576 */         timeint = rs.getInt("TIMEINTERVAL");
/*  577 */         actionname = rs.getString("ID");
/*  578 */         optmail = rs.getString("OPTIMISED");
/*  579 */         ignoreAckedAlerts = rs.getString("IGNOREACKED");
/*  580 */         if ((actionname == null) || (actionname.equals("NULL"))) {
/*  581 */           actionname = " ";
/*      */         }
/*      */       }
/*  584 */       getActions(mapping, form, request, response);
/*  585 */       if (type == 2)
/*      */       {
/*  587 */         String editquery1 = "Select MONITOR_ID from AM_ALERTTYPEMAPPER where RULEID=" + rid;
/*      */         
/*      */         try
/*      */         {
/*  591 */           rs1 = AMConnectionPool.executeQueryStmt(editquery1);
/*  592 */           while (rs1.next())
/*      */           {
/*  594 */             resids = rs1.getString("MONITOR_ID");
/*  595 */             rids.add(resids);
/*      */           }
/*      */         }
/*      */         catch (Exception se)
/*      */         {
/*  600 */           se.printStackTrace();
/*      */         }
/*  602 */         String[] resourceids = new String[rids.size()];
/*      */         
/*  604 */         for (int z = 0; z < rids.size(); z++)
/*      */         {
/*  606 */           resourceids[z] = ((String)rids.get(z));
/*      */         }
/*      */       }
/*      */       
/*  610 */       am.setRulename(name);
/*  611 */       am.setMonitortype(type);
/*  612 */       am.setRulestatus(status);
/*  613 */       am.setMonitorseverity(severity);
/*  614 */       am.setTimeunit(timeunit);
/*  615 */       am.setTimeperiod(timeperiod);
/*  616 */       am.setTimeinterval(timeint);
/*  617 */       am.setOptmail(optmail);
/*  618 */       am.setAck(ignoreAckedAlerts);
/*  619 */       am.setSendmail(actionname);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  624 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  628 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  630 */     return new ActionForward("/jsp/AlertEscalation.jsp?edit=true&sid=" + rid);
/*      */   }
/*      */   
/*      */ 
/*      */   private void setSelectedMonitors(ActionForm form, int rid, HttpServletRequest request)
/*      */   {
/*  636 */     ArrayList<Map<String, String>> unselectedMonitors = new ArrayList();
/*  637 */     ArrayList<Map<String, String>> selectedMonitors = new ArrayList();
/*  638 */     AMActionForm amform = (AMActionForm)form;
/*  639 */     String query = null;ResultSet rs = null;
/*  640 */     Vector resids = new Vector();
/*  641 */     String loginuserid = "";
/*      */     try
/*      */     {
/*  644 */       if (rid == 0)
/*      */       {
/*  646 */         if (Constants.isPrivilegedUser(request)) {
/*  647 */           if (Constants.isUserResourceEnabled()) {
/*  648 */             loginuserid = Constants.getLoginUserid(request);
/*  649 */             query = "select mo.RESOURCEID,mo.DISPLAYNAME,'true' UNSELECTED from AM_USERRESOURCESTABLE,AM_ManagedObject mo , AM_ManagedResourceType mrt where mo.TYPE=mrt.RESOURCETYPE and AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginuserid + " and mrt.RESOURCETYPE in " + Constants.resourceTypes;
/*      */           } else {
/*  651 */             resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  652 */             query = "select mo.RESOURCEID,mo.DISPLAYNAME,'true' UNSELECTED from AM_ManagedObject mo join  AM_ManagedResourceType mrt on mo.TYPE=mrt.RESOURCETYPE and " + Constants.getCondition("mo.RESOURCEID", resids) + " where mrt.RESOURCETYPE in" + Constants.resourceTypes;
/*      */           }
/*      */         } else {
/*  655 */           query = "select mo.RESOURCEID,mo.DISPLAYNAME,'true' UNSELECTED from AM_ManagedObject mo join  AM_ManagedResourceType mrt on mo.TYPE=mrt.RESOURCETYPE where mrt.RESOURCETYPE in " + Constants.resourceTypes;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*  660 */       else if (Constants.isPrivilegedUser(request)) {
/*  661 */         if (Constants.isUserResourceEnabled()) {
/*  662 */           loginuserid = Constants.getLoginUserid(request);
/*  663 */           query = "select mo.RESOURCEID,mo.DISPLAYNAME,CASE WHEN (atmp.MONITOR_ID is null) THEN 'true' ELSE 'false' END as UNSELECTED from AM_ManagedObject mo join  AM_ManagedResourceType mrt on mo.TYPE=mrt.RESOURCETYPE join AM_USERRESOURCESTABLE on AM_USERRESOURCESTABLE.RESOURCEID=mo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginuserid + " left join AM_ALERTTYPEMAPPER atmp on atmp.MONITOR_ID=mo.RESOURCEID and atmp.RULEID=" + rid + " where mrt.RESOURCETYPE in" + Constants.resourceTypes;
/*      */         }
/*      */         else {
/*  666 */           resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  667 */           query = "select mo.RESOURCEID,mo.DISPLAYNAME,CASE WHEN (atmp.MONITOR_ID is null) THEN 'true' ELSE 'false' END as UNSELECTED from AM_ManagedObject mo join  AM_ManagedResourceType mrt on mo.TYPE=mrt.RESOURCETYPE and " + Constants.getCondition("mo.RESOURCEID", resids) + " left join AM_ALERTTYPEMAPPER atmp on atmp.MONITOR_ID=mo.RESOURCEID and atmp.RULEID=" + rid + " where mrt.RESOURCETYPE in" + Constants.resourceTypes;
/*      */         }
/*      */         
/*      */       }
/*      */       else {
/*  672 */         query = "select mo.RESOURCEID,mo.DISPLAYNAME,CASE WHEN (atmp.MONITOR_ID is null) THEN 'true' ELSE 'false' END as UNSELECTED from AM_ManagedObject mo join  AM_ManagedResourceType mrt on mo.TYPE=mrt.RESOURCETYPE left join AM_ALERTTYPEMAPPER atmp on atmp.MONITOR_ID=mo.RESOURCEID and atmp.RULEID=" + rid + " where mrt.RESOURCETYPE in" + Constants.resourceTypes;
/*      */       }
/*      */       
/*  675 */       query = query + " and mo.TYPE not like 'OpManager-Interface%'";
/*  676 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  677 */       while (rs.next())
/*      */       {
/*  679 */         Map<String, String> monitorsMap = new HashMap();
/*  680 */         monitorsMap.put("label", rs.getString("DISPLAYNAME"));
/*  681 */         monitorsMap.put("value", rs.getString("RESOURCEID"));
/*  682 */         if ("true".equalsIgnoreCase(rs.getString("UNSELECTED")))
/*      */         {
/*  684 */           unselectedMonitors.add(monitorsMap);
/*      */         }
/*      */         else
/*      */         {
/*  688 */           selectedMonitors.add(monitorsMap);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  693 */       amform.setToAdd(unselectedMonitors);
/*  694 */       amform.setPresent(selectedMonitors);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  699 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  703 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward updateRuledetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  712 */     AMActionForm am = (AMActionForm)form;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  717 */       String rid = request.getParameter("sid");
/*      */       try
/*      */       {
/*  720 */         String name = am.getRulename();
/*      */         
/*  722 */         String severity = am.getMonitorseverity();
/*  723 */         int type = am.getMonitortype();
/*  724 */         String timeunit = am.getTimeunit();
/*  725 */         String sendmail = am.getSendmail();
/*  726 */         int timeint = am.getTimeinterval();
/*  727 */         String optmail = null;
/*  728 */         String ignoreAckedAlerts = am.getAck();
/*  729 */         if (am.getOptmail() != null)
/*      */         {
/*  731 */           optmail = "true";
/*      */         } else {
/*  733 */           optmail = "false";
/*      */         }
/*      */         
/*  736 */         String[] mgrpid = new String[0];
/*      */         try
/*      */         {
/*  739 */           mgrpid = am.getSelect();
/*      */         }
/*      */         catch (Exception npe) {}
/*      */         
/*      */ 
/*  744 */         int timeperiod = am.getTimeperiod();
/*  745 */         String updatequery = "update AM_ALERTRULE set RULENAME='" + name + "',TYPE=" + type + ",SEVERITY=" + severity + ",TIMEPERIOD=" + timeperiod + ",TIMEINTERVAL=" + timeint + ",TIMEUNIT='" + timeunit + "',OPTIMISED='" + optmail + "',IGNOREACKED='" + ignoreAckedAlerts + "' where RULEID=" + rid;
/*      */         
/*  747 */         AMConnectionPool.executeUpdateStmt(updatequery);
/*  748 */         if (type == 2)
/*      */         {
/*  750 */           String deleteold = "delete from AM_ALERTTYPEMAPPER where RULEID=" + rid;
/*  751 */           AMConnectionPool.executeUpdateStmt(deleteold);
/*  752 */           if (mgrpid.length != 0)
/*      */           {
/*  754 */             for (int j = 0; j < mgrpid.length; j++)
/*      */             {
/*  756 */               String updatetype = "insert into AM_ALERTTYPEMAPPER (ID,RULEID,MONITOR_ID) values (" + DBQueryUtil.getIncrementedID("ID", "AM_ALERTTYPEMAPPER") + "," + rid + "," + mgrpid[j] + " )";
/*  757 */               AMConnectionPool.executeUpdateStmt(updatetype);
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*  762 */         else if (type == 4)
/*      */         {
/*  764 */           String deleteold = "delete from AM_ALERTTYPEMAPPER where RULEID=" + rid;
/*  765 */           AMConnectionPool.executeUpdateStmt(deleteold);
/*  766 */           String[] selectedMonitors = am.getMaintenanceCombo2();
/*      */           
/*  768 */           PreparedStatement preSt = null;
/*  769 */           int id = DBQueryUtil.getIncrementedID("ID", "AM_ALERTTYPEMAPPER");
/*      */           try
/*      */           {
/*  772 */             if (selectedMonitors.length > 0)
/*      */             {
/*  774 */               int count = 1;
/*  775 */               preSt = AMConnectionPool.getPreparedStatement("insert into AM_ALERTTYPEMAPPER (ID,RULEID,MONITOR_ID) values(?,?,?)");
/*  776 */               int ruleId = Integer.valueOf(rid).intValue();
/*  777 */               for (String mID : selectedMonitors)
/*      */               {
/*  779 */                 count++;
/*  780 */                 int monitorID = Integer.parseInt(mID);
/*  781 */                 preSt.setInt(1, id++);
/*  782 */                 preSt.setInt(2, ruleId);
/*  783 */                 preSt.setInt(3, monitorID);
/*  784 */                 preSt.addBatch();
/*  785 */                 if (count % 1000 == 0)
/*      */                 {
/*  787 */                   preSt.executeBatch();
/*  788 */                   preSt.clearBatch();
/*      */                 }
/*      */               }
/*  791 */               if (count % 1000 != 0)
/*      */               {
/*  793 */                 preSt.executeBatch();
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  799 */             ex.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/*  803 */             AMConnectionPool.closePreparedStatement(preSt);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  808 */           String deletequery = "delete from AM_ALERTTYPEMAPPER where RULEID=" + rid;
/*  809 */           AMConnectionPool.executeUpdateStmt(deletequery);
/*      */         }
/*  811 */         String updateaction = "update AM_ALERTACTIONMAPPER set ACTIONID=" + sendmail + " where RULEID=" + rid;
/*  812 */         AMConnectionPool.executeUpdateStmt(updateaction);
/*      */         
/*  814 */         return new ActionForward("/alertEscalation.do?method=showRules", true);
/*      */       }
/*      */       catch (Exception eq)
/*      */       {
/*  818 */         eq.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  825 */       return null;
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  823 */       es.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void deleteRule(String rid) {
/*  828 */     Statement deletequery = null;
/*      */     try
/*      */     {
/*  831 */       AMConnectionPool.getInstance();deletequery = AMConnectionPool.getConnection().createStatement();
/*  832 */       String query1 = "DELETE FROM AM_ALERTRULE WHERE RULEID=" + rid;
/*  833 */       String query2 = "DELETE FROM AM_ALERTACTIONMAPPER WHERE RULEID=" + rid;
/*  834 */       String query3 = "DELETE FROM AM_ALERTTYPEMAPPER WHERE RULEID=" + rid;
/*  835 */       deletequery.addBatch(query1);
/*  836 */       deletequery.addBatch(query2);
/*  837 */       deletequery.addBatch(query3);
/*  838 */       deletequery.executeBatch();
/*  839 */       deletequery.clearBatch();
/*  840 */       deletequery.close();
/*  841 */       AMLog.debug("AdminTools :: deleteRule : Going to delete rule from AM_CONFIGTOUSER_MAPPING tables: " + rid);
/*  842 */       DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(Integer.parseInt(rid), 7);
/*  843 */       AMAlertMailer aam = (AMAlertMailer)AMFaultProcess.instances.get(String.valueOf(rid));
/*  844 */       aam.setEnabled(false);
/*  845 */       Scheduler schedular = Scheduler.getScheduler("main");
/*  846 */       schedular.removeTask(aam);
/*  847 */       AMFaultProcess.instances.remove(String.valueOf(rid)); return;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  852 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  857 */         if (deletequery != null) {
/*  858 */           deletequery.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*  863 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward removeRule(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  872 */       String[] rids = request.getParameterValues("ruleids");
/*  873 */       for (int i = 0; i < rids.length; i++)
/*      */       {
/*  875 */         String rid = rids[i];
/*  876 */         deleteRule(rid);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  881 */       ex.printStackTrace();
/*      */     }
/*  883 */     return new ActionForward("/alertEscalation.do?method=showRules", true);
/*      */   }
/*      */   
/*      */   public ActionForward sendAjaxDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  888 */     AMActionForm am = (AMActionForm)form;
/*  889 */     response.setContentType("text/html; charset=UTF-8");
/*      */     
/*  891 */     response.setHeader("Pragma", "no-cache");
/*  892 */     response.addHeader("Cache-Control", "no-cache");
/*      */     
/*  894 */     ResultSet rs = null;
/*  895 */     String sid = null;
/*  896 */     String edit = null;
/*  897 */     String resource = null;
/*  898 */     ArrayList resids = new ArrayList();
/*  899 */     ArrayList resourceids = new ArrayList();
/*      */     try
/*      */     {
/*  902 */       if (request.getParameter("sid") != null)
/*      */       {
/*  904 */         sid = request.getParameter("sid");
/*      */       }
/*      */       
/*  907 */       edit = request.getParameter("edit");
/*  908 */       resource = request.getParameter("resource");
/*  909 */       String editquery3 = null;
/*  910 */       if ((sid != null) && (!sid.equalsIgnoreCase("null")))
/*      */       {
/*  912 */         if ((resource != null) && (resource.equalsIgnoreCase("monitorgroup")))
/*      */         {
/*  914 */           editquery3 = "SELECT MONITOR_ID FROM  AM_ALERTTYPEMAPPER  where RULEID ='" + sid + "'";
/*  915 */           rs = AMConnectionPool.executeQueryStmt(editquery3);
/*  916 */           while (rs.next())
/*      */           {
/*  918 */             String resid = rs.getString("MONITOR_ID");
/*  919 */             resids.add(resid);
/*      */           }
/*  921 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*  923 */         String[] rids = new String[resids.size()];
/*  924 */         for (int k = 0; k < resids.size(); k++)
/*      */         {
/*  926 */           rids[k] = ((String)resids.get(k));
/*      */         }
/*  928 */         am.setMgroups(rids);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*  935 */       es.printStackTrace();
/*      */     }
/*  937 */     return new ActionForward("/jsp/AlertRes_Mtrgrp.jsp?resource=" + resource + "&edit=" + edit + "&ruleid=" + sid);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward sendActionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  944 */     AMActionForm am = (AMActionForm)form;
/*  945 */     response.setHeader("Pragma", "no-cache");
/*  946 */     response.addHeader("Cache-Control", "no-cache");
/*      */     
/*  948 */     response.setContentType("text/html; charset=UTF-8");
/*  949 */     String sname = null;
/*  950 */     String emailid = null;
/*  951 */     String htmlmess = getHTMLMailTemplate();
/*  952 */     String host = InetAddress.getLocalHost().getHostName();
/*  953 */     String port = System.getProperty("webserver.port");
/*      */     
/*  955 */     boolean isHTTPS = false;
/*  956 */     if (port.equals("0"))
/*      */     {
/*  958 */       isHTTPS = true;
/*  959 */       port = System.getProperty("ssl.port");
/*      */     }
/*      */     
/*  962 */     String returnVal = null;
/*  963 */     String sentactid = null;
/*  964 */     String actid = null;
/*      */     
/*  966 */     ResultSet rst = null;
/*      */     try
/*      */     {
/*  969 */       String subjectmessage = FormatUtil.getString("am.webclient.alertescalation.alertgenerationtest");
/*  970 */       String fromwhere = request.getParameter("createActionFrom");
/*  971 */       if ((fromwhere != null) && (fromwhere.equals("DowntimeSummary")))
/*      */       {
/*  973 */         subjectmessage = FormatUtil.getString("am.webclient.summarymailer.alertgenerationtest");
/*      */       }
/*  975 */       emailid = request.getParameter("emailid");
/*      */       
/*  977 */       if (emailid != null)
/*      */       {
/*  979 */         String frmAddress = "";
/*  980 */         if (Constants.EMAIL_ADDRESS != null) {
/*  981 */           frmAddress = Constants.EMAIL_ADDRESS;
/*      */         }
/*      */         else {
/*  984 */           frmAddress = emailid;
/*      */         }
/*      */         
/*  987 */         String q1 = "select HOST from AM_MAILSETTINGS ";
/*  988 */         rst = AMConnectionPool.executeQueryStmt(q1);
/*  989 */         if (rst.next())
/*      */         {
/*  991 */           String fline = FormatUtil.findReplace(htmlmess, "~topheading~", FormatUtil.getString("am.webclient.managermail.rulemail.topheading.text"));
/*  992 */           String mailheading = FormatUtil.findReplace(fline, "~Name~", FormatUtil.getString("am.webclient.eventlogrules.RuleName"));
/*  993 */           String startmail = FormatUtil.findReplace(mailheading, "~source~", "TEST");
/*  994 */           String attr = FormatUtil.findReplace(startmail, "~Alert~", FormatUtil.getString("am.webclient.alertescalation.showrules.alertname"));
/*  995 */           String messa = FormatUtil.findReplace(attr, "~Type~", FormatUtil.getString("All Monitors"));
/*  996 */           String schper = FormatUtil.findReplace(messa, "~Severity~", FormatUtil.getString("Severity"));
/*  997 */           String schmess = FormatUtil.findReplace(schper, "~message~", FormatUtil.getString("Clear"));
/*  998 */           String schgen = FormatUtil.findReplace(schmess, "~Time~", FormatUtil.getString("am.webclient.alertescalation.alertgeneration"));
/*  999 */           String genat = FormatUtil.findReplace(schgen, "~date~", FormatUtil.getString("am.webclient.alertescalation.alertgenerationtime"));
/* 1000 */           String mytab = FormatUtil.findReplace(genat, "~mytable~", "");
/* 1001 */           String user = FormatUtil.findReplace(mytab, "~userinfo~", "");
/* 1002 */           String addinfo = FormatUtil.findReplace(user, "~addInfo~", FormatUtil.getString("am.webclient.managermail.additionalinfo.text"));
/* 1003 */           String repby = FormatUtil.findReplace(addinfo, "~reportby~", FormatUtil.getString("am.webclient.managermail.reportby.text"));
/* 1004 */           String hostFilled = FormatUtil.findReplace(repby, "~host~", host);
/*      */           
/*      */ 
/* 1007 */           String nameFilled = null;
/* 1008 */           if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */           {
/* 1010 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("rebrand.product.name"));
/*      */           }
/*      */           else
/*      */           {
/* 1014 */             nameFilled = FormatUtil.findReplace(hostFilled, "~product name~", OEMUtil.getOEMString("product.name"));
/*      */           }
/*      */           
/*      */ 
/* 1018 */           String portFilled = FormatUtil.findReplace(nameFilled, "~port~", port);
/*      */           
/* 1020 */           if (isHTTPS)
/*      */           {
/* 1022 */             portFilled = FormatUtil.findReplace(portFilled, "http:", "https:");
/*      */           }
/*      */           
/* 1025 */           SmtpMailer mailer = new SmtpMailer(frmAddress, emailid, "", subjectmessage);
/* 1026 */           returnVal = mailer.sendMessage("", null, true, portFilled);
/*      */           
/* 1028 */           if (returnVal == null)
/*      */           {
/* 1030 */             sname = request.getParameter("emailname");
/* 1031 */             sname = URLDecoder.decode(sname);
/* 1032 */             sname = sname + "_Action";
/* 1033 */             ResultSet rs = null;
/*      */             try {
/* 1035 */               rs = AMConnectionPool.executeQueryStmt("SELECT * FROM AM_EMAILACTION where TOADDRESS='" + emailid + "' and ID IN (select ID from  AM_ACTIONPROFILE where NAME='" + sname + "')");
/* 1036 */               if (rs.next()) {
/* 1037 */                 PrintWriter pw = response.getWriter();
/* 1038 */                 pw.print(FormatUtil.getString("am.webclient.newaction.downtime.txt", new String[] { emailid }));
/* 1039 */                 AMConnectionPool.closeStatement(rs);
/* 1040 */                 ActionForward localActionForward = null;
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 try
/*      */                 {
/* 1050 */                   if (rs != null) {
/* 1051 */                     rs.close();
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ee)
/*      */                 {
/* 1056 */                   ee.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1250 */                 return localActionForward;
/*      */               }
/*      */               try
/*      */               {
/* 1050 */                 if (rs != null) {
/* 1051 */                   rs.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1056 */                 ee.printStackTrace();
/*      */               }
/*      */               
/* 1059 */               ps = null;
/*      */             }
/*      */             catch (Exception exe)
/*      */             {
/* 1044 */               exe.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 1050 */                 if (rs != null) {
/* 1051 */                   rs.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1056 */                 ee.printStackTrace();
/*      */               }
/*      */             }
/*      */             try
/*      */             {
/* 1061 */               PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_ACTIONPROFILE(ID,NAME,TYPE) values(" + DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + ",?,?)");
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 1070 */                 if (ps != null) {
/* 1071 */                   ps.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1076 */                 ee.printStackTrace();
/*      */               }
/*      */               
/* 1079 */               ps1 = null;
/*      */             }
/*      */             catch (Exception se)
/*      */             {
/* 1064 */               se.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 1070 */                 if (ps != null) {
/* 1071 */                   ps.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1076 */                 ee.printStackTrace();
/*      */               }
/*      */             }
/*      */             try
/*      */             {
/* 1081 */               PreparedStatement ps1 = AMConnectionPool.getConnection().prepareStatement("INSERT INTO AM_ACTIONPROFILE (ID,NAME,TYPE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + ",?,?)");
/*      */               
/* 1083 */               ps1.setString(1, sname);
/* 1084 */               ps1.setInt(2, 1);
/*      */               
/* 1086 */               ps1.executeUpdate();
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 1094 */                 if (ps1 != null) {
/* 1095 */                   ps1.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1100 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 1105 */               rsid = null;
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1088 */               e.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 1094 */                 if (ps1 != null) {
/* 1095 */                   ps1.close();
/*      */                 }
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 1100 */                 ee.printStackTrace();
/*      */               }
/*      */             }
/*      */             
/*      */             ResultSet rsid;
/*      */             
/*      */             try
/*      */             {
/* 1108 */               String actQuery = "SELECT MAX(ID) FROM AM_ACTIONPROFILE";
/* 1109 */               AMLog.debug("Show  Alert Rule query : " + actQuery);
/* 1110 */               rsid = AMConnectionPool.executeQueryStmt(actQuery);
/*      */               
/* 1112 */               if (rsid.next()) {
/* 1113 */                 actid = rsid.getString(1);
/*      */               }
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1118 */               e.printStackTrace();
/*      */             }
/*      */             finally {}
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1125 */             String subject = null;
/* 1126 */             if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */             {
/* 1128 */               subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") });
/*      */             }
/*      */             else
/*      */             {
/* 1132 */               subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") });
/*      */             }
/*      */             
/* 1135 */             String act2 = null;
/* 1136 */             if ((OEMUtil.getOEMString("isRebranded") != null) && (OEMUtil.getOEMString("isRebranded").equals("true")))
/*      */             {
/* 1138 */               act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE,SMTPPORT) values (" + actid + ",'" + frmAddress + "','" + emailid + "','" + subject + "','" + FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("rebrand.product.name") }) + "',25)";
/*      */             }
/*      */             else
/*      */             {
/* 1142 */               act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE,SMTPPORT) values (" + actid + ",'" + frmAddress + "','" + emailid + "','" + subject + "','" + FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("product.name") }) + "',25)";
/*      */             }
/* 1144 */             AMConnectionPool.executeUpdateStmt(act2);
/* 1145 */             DelegatedUserRoleUtil.addEntryToConfigUserTable(request, Integer.parseInt(actid), 2);
/* 1146 */             if (EnterpriseUtil.isAdminServer()) {
/* 1147 */               com.adventnet.appmanager.util.MASSyncUtil.pushEmailActionToMAS(actid);
/*      */             }
/* 1149 */             Properties pro = new Properties();
/* 1150 */             ArrayList rows = new ArrayList();
/* 1151 */             pro.setProperty("label", sname);
/* 1152 */             pro.setProperty("value", actid);
/* 1153 */             rows.add(pro);
/* 1154 */             getActions(mapping, form, request, response);
/* 1155 */             am.setSendmail(actid);
/* 1156 */             sentactid = returnVal + "," + actid;
/* 1157 */             request.setAttribute("tabtoselect", "3");
/* 1158 */             response.setContentType("text/plain");
/* 1159 */             PrintWriter pw = response.getWriter();
/* 1160 */             pw.print(sentactid);
/*      */           }
/*      */           else
/*      */           {
/* 1164 */             request.setAttribute("tabtoselect", "3");
/* 1165 */             getActions(mapping, form, request, response);
/*      */             
/* 1167 */             returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.mailmessage.text");
/* 1168 */             sentactid = returnVal + ",0";
/* 1169 */             PrintWriter pw = response.getWriter();
/* 1170 */             pw.print(sentactid);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1175 */           returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.smtpmailmessage.text");
/* 1176 */           getActions(mapping, form, request, response);
/* 1177 */           sentactid = returnVal + ",0";
/* 1178 */           PrintWriter pw = response.getWriter();
/* 1179 */           pw.print(sentactid);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (ConnectException ce) {
/* 1184 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/* 1185 */       sentactid = returnVal + ",0";
/* 1186 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/* 1187 */       System.out.println("*The Reason is====>" + returnVal);
/* 1188 */       ce.printStackTrace();
/*      */     }
/*      */     catch (UnknownHostException ee)
/*      */     {
/* 1192 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/* 1193 */       sentactid = returnVal + ",0";
/* 1194 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/* 1195 */       System.out.println("*The Reason is====>" + returnVal);
/* 1196 */       ee.printStackTrace();
/*      */     }
/*      */     catch (NoRouteToHostException nre)
/*      */     {
/* 1200 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/* 1201 */       sentactid = returnVal + ",0";
/* 1202 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/*      */       
/* 1204 */       System.out.println("*The Reason is====>" + returnVal);
/* 1205 */       nre.printStackTrace();
/*      */     }
/*      */     catch (InterruptedIOException soc)
/*      */     {
/* 1209 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.connectiontimeoutreason.text");
/* 1210 */       sentactid = returnVal + ",0";
/* 1211 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/*      */       
/* 1213 */       System.out.println("*The Reason is====>" + returnVal);
/* 1214 */       soc.printStackTrace();
/*      */     }
/*      */     catch (ProtocolException pr)
/*      */     {
/* 1218 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.protocalexceptionreason.text");
/* 1219 */       sentactid = returnVal + ",0";
/* 1220 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/* 1221 */       System.out.println("*The Reason is====>" + returnVal);
/*      */     }
/*      */     catch (EOFException e)
/*      */     {
/* 1225 */       e.printStackTrace();
/* 1226 */       returnVal = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/* 1227 */       sentactid = returnVal + ",0";
/* 1228 */       PrintWriter pw = response.getWriter();pw.print(sentactid);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1232 */       e.printStackTrace();
/* 1233 */       returnVal = e.getMessage();
/* 1234 */       if ((returnVal != null) && (returnVal.startsWith("Network is unreachable.")))
/*      */       {
/* 1236 */         returnVal = FormatUtil.getString("am.webclient.urlavailability.networksettingsreason.text");
/* 1237 */         sentactid = returnVal + ",0";
/* 1238 */         PrintWriter pw = response.getWriter();pw.print(sentactid);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1243 */         returnVal = FormatUtil.getString("am.webclient.urlavailability.exceptionreason.text");
/* 1244 */         sentactid = returnVal + ",0";
/* 1245 */         PrintWriter pw = response.getWriter();pw.print(sentactid);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1250 */       AMConnectionPool.closeStatement(rst);
/*      */     }
/* 1252 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward sendEnableDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1257 */     response.setHeader("Pragma", "no-cache");
/* 1258 */     response.addHeader("Cache-Control", "no-cache");
/*      */     
/* 1260 */     ResultSet rs = null;
/* 1261 */     int value = 0;
/* 1262 */     String id = null;
/*      */     
/*      */     try
/*      */     {
/* 1266 */       id = request.getParameter("id");
/* 1267 */       String valuequery = null;
/* 1268 */       if (id != null)
/*      */       {
/* 1270 */         String getvaluequery = "SELECT STATUS FROM AM_ALERTRULE WHERE RULEID='" + id + "'";
/* 1271 */         rs = AMConnectionPool.executeQueryStmt(getvaluequery);
/* 1272 */         if (rs.next())
/*      */         {
/* 1274 */           value = rs.getInt("STATUS");
/*      */         }
/*      */         
/* 1277 */         int sendvalue = value;
/*      */         
/* 1279 */         if (value == 1)
/*      */         {
/* 1281 */           valuequery = "UPDATE AM_ALERTRULE SET STATUS=2 where RULEID='" + id + "'";
/* 1282 */           sendvalue = 2;
/*      */         }
/*      */         else
/*      */         {
/* 1286 */           valuequery = "UPDATE AM_ALERTRULE SET STATUS=1 where RULEID='" + id + "'";
/* 1287 */           sendvalue = 1;
/*      */         }
/* 1289 */         AMConnectionPool.closeStatement(rs);
/* 1290 */         AMConnectionPool.executeUpdateStmt(valuequery);
/*      */         
/* 1292 */         String valuetoresponse = sendvalue + "," + id;
/* 1293 */         request.setAttribute("tabtoselect", "3");
/* 1294 */         PrintWriter pw = response.getWriter();
/* 1295 */         pw.print(valuetoresponse);
/*      */       }
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/* 1300 */       es.printStackTrace();
/*      */     }
/* 1302 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward pollNow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1307 */     String resourceID = request.getParameter("resourceid");
/* 1308 */     String resourcetype = request.getParameter("resourcetype");
/* 1309 */     ResultSet rs = null;
/* 1310 */     boolean isCustomtype = false;
/* 1311 */     int baseid = -1;
/*      */     
/* 1313 */     if ((request.getParameter("baseid") != null) && (!request.getParameter("baseid").equals("-1")))
/*      */     {
/* 1315 */       isCustomtype = true;
/*      */       try {
/* 1317 */         baseid = Integer.parseInt(request.getParameter("baseid"));
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1321 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1324 */     if ((isCustomtype) && (!resourcetype.equals("Script Monitor")))
/*      */     {
/*      */       try
/*      */       {
/* 1328 */         DataCollectionControllerUtil.rescheduleCustomDataCollection(Integer.parseInt(resourceID), resourcetype, baseid, "Script Monitor");
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1332 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1335 */     else if ((resourcetype.equals("Generic WMI")) || (resourcetype.equals("Script Monitor")) || (resourcetype.equals("directory")) || (resourcetype.equals("file")) || (resourcetype.equals("QENGINE ")))
/*      */     {
/*      */       try
/*      */       {
/* 1339 */         DataCollectionControllerUtil.rescheduleScriptDataCollection(Integer.parseInt(resourceID), resourcetype);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1343 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1346 */     else if (resourcetype.equals("UrlMonitor"))
/*      */     {
/*      */       try
/*      */       {
/* 1350 */         Hashtable urlmonitors = UrlMonitor.getUrlmonitors();
/* 1351 */         UrlMonitor urlconf = (UrlMonitor)urlmonitors.get(new Integer(Integer.parseInt(resourceID)));
/* 1352 */         UrlMonitor.stopMonitoring(urlconf.getURLID());
/* 1353 */         UrlMonitor monitor = new UrlMonitor(urlconf.getURLID(), urlconf.getURL(), urlconf.getUSERID(), urlconf.getPASSWORD(), urlconf.getQUERYSTRING(), urlconf.getMETHOD(), urlconf.getAVAILABILITYSTRING(), urlconf.getPollInterval(), false, urlconf.getCustomHeaders());
/* 1354 */         monitor.setUnAvailabilityString(urlconf.getUNAVAILABILITYSTRING());
/* 1355 */         monitor.setTimeout(urlconf.getTimeout());
/* 1356 */         monitor.setVerifyerror(urlconf.getVerifyerror());
/* 1357 */         monitor.setCaseSensitive(urlconf.isCaseSensitive());
/* 1358 */         monitor.setRegEx(urlconf.isRegEx());
/* 1359 */         monitor.setUserAgent(urlconf.getUserAgent());
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1363 */         ex.printStackTrace();
/*      */       }
/*      */     }
/* 1366 */     else if ((resourcetype.equals("RBM")) || (resourcetype.equals("RBMURL")))
/*      */     {
/* 1368 */       ResultSet rst = null;
/*      */       try
/*      */       {
/* 1371 */         RBMMonitor rbm = null;
/* 1372 */         System.out.println("POLL NOW : resource Type : " + resourcetype);
/* 1373 */         System.out.println("POLL NOW : RESOURCEID : " + resourceID);
/* 1374 */         System.out.println("POLL NOW : RESOURCEID : " + RBMMonitor.rIdVsScheduler);
/* 1375 */         if (resourcetype.equals("RBMURL"))
/*      */         {
/* 1377 */           int resId = 0;
/* 1378 */           rst = AMConnectionPool.executeQueryStmt("select URLSEQID from AM_URLSequence where URLID=" + resourceID);
/* 1379 */           if (rst.next())
/*      */           {
/* 1381 */             resId = rst.getInt(1);
/*      */           }
/* 1383 */           rbm = (RBMMonitor)RBMMonitor.rIdVsScheduler.get("" + resId);
/* 1384 */           rst.close();
/*      */         }
/*      */         else
/*      */         {
/* 1388 */           rbm = (RBMMonitor)RBMMonitor.rIdVsScheduler.get("" + resourceID);
/*      */         }
/* 1390 */         long datacollectiontime = 0L;
/* 1391 */         Scheduler schedular = Scheduler.getScheduler("RBM_Monitor");
/* 1392 */         schedular.removeTask(rbm);
/*      */         
/*      */         try
/*      */         {
/* 1396 */           datacollectiontime = System.currentTimeMillis() + 5000L;
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/* 1400 */           datacollectiontime = System.currentTimeMillis() + 300000L;
/*      */         }
/*      */         
/* 1403 */         System.out.println("Poll Now Resourceid " + resourceID + " : " + datacollectiontime);
/* 1404 */         schedular.scheduleTask(rbm, datacollectiontime);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1408 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1412 */         AMConnectionPool.closeStatement(rst);
/*      */       }
/*      */     } else {
/* 1415 */       if (resourcetype.equals("Web Service"))
/*      */       {
/* 1417 */         return new ActionForward("/wsm.do?method=pollNow&resourceid=" + resourceID, true);
/*      */       }
/* 1419 */       if (!resourcetype.equals("Ping Monitor"))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1437 */         if (resourcetype.equals("SAP-CCMS"))
/*      */         {
/*      */           try
/*      */           {
/* 1441 */             SAPCCMSDataCollector ccmsd = (SAPCCMSDataCollector)AMUrlMonitorProcess.instances.get(Integer.valueOf(resourceID));
/* 1442 */             Scheduler schedular = Scheduler.getScheduler("URLMonitor");
/* 1443 */             schedular.removeTask(ccmsd);
/* 1444 */             AMUrlMonitorProcess.instances.remove(Integer.valueOf(resourceID));
/*      */             
/* 1446 */             SAPCCMSDataCollector ccmsdnew = new SAPCCMSDataCollector(Integer.valueOf(resourceID).intValue());
/* 1447 */             long datacollectiontime = System.currentTimeMillis() + 200L;
/* 1448 */             schedular.scheduleTask(ccmsdnew, datacollectiontime);
/* 1449 */             AMUrlMonitorProcess.instances.put(resourceID, ccmsdnew);
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1453 */             ex.printStackTrace();
/*      */           }
/*      */         }
/* 1456 */         else if ((resourcetype.equals("WEB-server")) || (resourcetype.equals("MAIL-server")))
/*      */         {
/* 1458 */           ResultSet rs1 = null;
/*      */           try
/*      */           {
/* 1461 */             String query = "Select POLLINTERVAL  from ManagedObject,AM_ManagedObject where AM_ManagedObject.RESOURCENAME=ManagedObject.NAME and AM_ManagedObject.RESOURCEID=" + resourceID;
/* 1462 */             rs1 = AMConnectionPool.executeQueryStmt(query);
/* 1463 */             int pollinterval = 300;
/* 1464 */             if (rs1.next())
/*      */             {
/* 1466 */               pollinterval = rs1.getInt("POLLINTERVAL");
/*      */             }
/* 1468 */             HostDiscoveryHandler.enableStatusPolling(resourceID, pollinterval + "");
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1472 */             ex.printStackTrace();
/*      */           }
/*      */           finally
/*      */           {
/* 1476 */             AMConnectionPool.closeStatement(rs1);
/*      */           }
/*      */         }
/* 1479 */         else if ((resourcetype.equals("SNMP")) || (resourcetype.equals("JMX1.2-MX4J-RMI")))
/*      */         {
/*      */           try
/*      */           {
/* 1483 */             CAMServerUtil.checkAndScheduleCAMJob(Integer.valueOf(resourceID).intValue());
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1487 */             ex.printStackTrace();
/*      */           }
/*      */           
/*      */         }
/* 1491 */         else if (resourcetype.equals("UrlSeq"))
/*      */         {
/*      */ 
/*      */           try
/*      */           {
/* 1496 */             UrlSequence.stopMonitoringForSequence(Integer.parseInt(resourceID));
/* 1497 */             ManagedApplication mo = new ManagedApplication();
/* 1498 */             ArrayList rows = mo.getRows("select AM_URL.*,AM_ManagedObject.resourceid from AM_URL,AM_PARENTCHILDMAPPER,AM_ManagedObject where AM_URL.URLID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.resourceid and AM_ManagedObject.type='UrlSeq' and RESOURCEID=" + resourceID + " order by resourceid,urlid");
/* 1499 */             for (int i = 0; i < rows.size(); i++)
/*      */             {
/* 1501 */               ArrayList row = (ArrayList)rows.get(i);
/* 1502 */               int urlid = Integer.parseInt((String)row.get(0));
/* 1503 */               String msUrl = (String)row.get(1);
/* 1504 */               String msUserId = (String)row.get(2);
/* 1505 */               String msPassword = (String)row.get(3);
/* 1506 */               String msQueryString = (String)row.get(4);
/* 1507 */               String msMethod = (String)row.get(5);
/* 1508 */               String msAvailabilityString = (String)row.get(6);
/* 1509 */               long mnPollInterval = Integer.parseInt((String)row.get(7));
/* 1510 */               String urlsequenceid = (String)row.get(15);
/* 1511 */               UrlSequence monitor1 = new UrlSequence(Integer.parseInt(urlsequenceid), urlid, msUrl, msUserId, msPassword, msQueryString, msMethod, msAvailabilityString, mnPollInterval, (String)row.get(11));
/* 1512 */               monitor1.setUnAvailabilityString((String)row.get(8));
/* 1513 */               monitor1.setTimeout(Integer.parseInt((String)row.get(9)));
/* 1514 */               monitor1.setVerifyerror(row.get(10).equals("1"));
/* 1515 */               monitor1.setCaseSensitive((row.get(12).equals("1")) || (row.get(12).equals("t")));
/* 1516 */               monitor1.setRegEx((row.get(13).equals("1")) || (row.get(13).equals("t")));
/* 1517 */               monitor1.setUserAgent((String)row.get(14));
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1523 */             ex.printStackTrace();
/*      */           }
/*      */           catch (Error ex1)
/*      */           {
/* 1527 */             ex1.printStackTrace();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*      */           try
/*      */           {
/* 1534 */             Hashtable h1 = PerformDataCollection.getScheduledTasks();
/* 1535 */             String resourcedetailsquery = "Select AM_ManagedObject.RESOURCENAME,TYPE,componentname,POLLINTERVAL  from CollectData,AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceID;
/* 1536 */             rs = AMConnectionPool.executeQueryStmt(resourcedetailsquery);
/* 1537 */             if (rs.next())
/*      */             {
/* 1539 */               String resourcename = rs.getString("RESOURCENAME");
/*      */               
/* 1541 */               String componentname = rs.getString("componentname");
/* 1542 */               int pollinterval = rs.getInt("POLLINTERVAL");
/* 1543 */               Hashtable h = PerformDataCollection.getScheduledTasks();
/* 1544 */               PerformDataCollection p = (PerformDataCollection)h.get(resourcename + "\t" + componentname);
/* 1545 */               Scheduler s = Scheduler.getScheduler("DataCollection");
/* 1546 */               if (s != null)
/*      */               {
/* 1548 */                 s.removeTask(p);
/* 1549 */                 p.stopMonitoring = true;
/* 1550 */                 PerformDataCollection.getScheduledList().remove(resourcename + "\t" + componentname);
/* 1551 */                 com.adventnet.appmanager.server.hostresources.util.HostUtil.resetErrorCache(resourceID, resourcename, 15);
/* 1552 */                 new PerformDataCollection(resourcename, componentname, pollinterval, true);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/* 1559 */             AMConnectionPool.closeStatement(rs);
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1563 */             ex.printStackTrace();
/*      */           }
/*      */         } } }
/* 1566 */     String selectedscheme = request.getParameter("selectedscheme");
/* 1567 */     String selectedSkin = request.getParameter("selectedSkin");
/* 1568 */     String toForward = "/showresource.do?resourceid=" + resourceID + "&method=showResourceForResourceID&pollnow=true";
/* 1569 */     if ((selectedscheme != null) && (selectedSkin != null)) {
/* 1570 */       toForward = toForward + "&selectedscheme=" + selectedscheme + "&selectedSkin=" + selectedSkin;
/*      */     }
/* 1572 */     if ((isCustomtype) && (request.getParameter("tabId") != null))
/*      */     {
/* 1574 */       String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/* 1575 */       toForward = toForward + appendHash;
/*      */     }
/* 1577 */     return new ActionForward(toForward, true);
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward changeWebsiteStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1584 */     String changeStateTo = request.getParameter("state");
/* 1585 */     String resourceid = request.getParameter("resourceid");
/* 1586 */     String websiteid = request.getParameter("websiteid");
/* 1587 */     String query = "update AM_ManagedObject set DCSTARTED=2 where RESOURCEID=" + websiteid;
/*      */     
/*      */     try
/*      */     {
/*      */       try
/*      */       {
/* 1593 */         if (changeStateTo.equals("enable"))
/*      */         {
/* 1595 */           query = "update AM_ManagedObject set DCSTARTED=2 where RESOURCEID=" + websiteid;
/* 1596 */           AMConnectionPool.executeUpdateStmt("insert into AM_RCAMAPPER values (" + resourceid + ",2001," + websiteid + ",2014)");
/*      */         }
/* 1598 */         else if (changeStateTo.equals("disable"))
/*      */         {
/* 1600 */           query = "update AM_ManagedObject set DCSTARTED=7 where RESOURCEID=" + websiteid;
/* 1601 */           FaultUtil.deleteEventFromCache(websiteid);
/* 1602 */           AMConnectionPool.executeUpdateStmt("delete from Alert where SOURCE='" + websiteid + "'");
/* 1603 */           AMConnectionPool.executeUpdateStmt("delete from Event where SOURCE='" + websiteid + "'");
/* 1604 */           AMConnectionPool.executeUpdateStmt("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + resourceid + " and CHILDRESOURCEID=" + websiteid);
/* 1605 */           new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 2001, System.currentTimeMillis());
/*      */         }
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/* 1610 */         if (e.getMessage().indexOf("Duplicate") == -1)
/*      */         {
/* 1612 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1615 */       AMConnectionPool.executeUpdateStmt(query);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1619 */       ex.printStackTrace();
/*      */     }
/* 1621 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static String getHTMLMailTpl()
/*      */   {
/*      */     try
/*      */     {
/* 1630 */       return FormatUtil.getContentsAsString("./conf/AlertMail.html");
/*      */     }
/*      */     catch (IOException io)
/*      */     {
/* 1634 */       System.out.println("Comparing : Problem encountered when trying to form the HTML Mail template"); }
/* 1635 */     return "error in sending mail";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getHTMLMailTemplate()
/*      */   {
/* 1642 */     return htmlMailTpl;
/*      */   }
/*      */   
/*      */   private Vector<String> getActionIdList(HttpServletRequest request) {
/* 1646 */     String userId = DBUtil.getUserID(request.getRemoteUser());
/*      */     
/* 1648 */     Vector<String> resIds = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(userId);
/* 1649 */     Vector<String> actionIdList = new Vector();
/* 1650 */     if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/* 1651 */       actionIdList = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(Integer.parseInt(userId), 2);
/*      */     }
/*      */     else {
/* 1654 */       actionIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(Integer.parseInt(userId), 2);
/*      */     }
/* 1656 */     String qry = "select ACTIONID from AM_ATTRIBUTEACTIONMAPPER where " + DBUtil.getCondition("AM_ATTRIBUTEACTIONMAPPER.ID", resIds) + " and AM_ATTRIBUTEACTIONMAPPER.ACTIONID not in ";
/* 1657 */     actionIdList = DelegatedUserRoleUtil.getCompleteConfigIds(qry, actionIdList);
/* 1658 */     return actionIdList;
/*      */   }
/*      */   
/*      */   private final void getActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1662 */     ResultSet set = null;
/* 1663 */     Vector<String> userActionIds = new Vector();
/* 1664 */     boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/* 1665 */     if (isDelegatedAdmin) {
/* 1666 */       userActionIds = getActionIdList(request);
/*      */     }
/* 1668 */     String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,AM_EMAILACTION.TOADDRESS AS EMAIL_TOADDRESS,AM_SMSACTION.TOADDRESS AS SMS_TOADDRESS,AM_SERVERCMDACTION.COMMAND,AM_ACTIONPROFILE.TYPE FROM AM_ACTIONPROFILE LEFT JOIN AM_SMSACTION ON AM_SMSACTION.ID=AM_ACTIONPROFILE.ID  LEFT JOIN AM_EMAILACTION ON AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID LEFT JOIN AM_SERVERCMDACTION ON AM_SERVERCMDACTION.ID=AM_ACTIONPROFILE.ID WHERE TYPE IN ('1','2','3') and AM_ACTIONPROFILE.NAME!='Restart The Service'";
/* 1669 */     String orderbyCond = " order by TYPE,NAME";
/* 1670 */     String daCondition = "";
/* 1671 */     if (isDelegatedAdmin) {
/* 1672 */       daCondition = " and " + DBUtil.getCondition("AM_ACTIONPROFILE.ID", userActionIds);
/*      */     }
/* 1674 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/* 1676 */       query = query + " AND AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*      */     }
/*      */     
/* 1679 */     query = query + daCondition + orderbyCond;
/* 1680 */     AMLog.debug("getActions :: Alert Escalation query :" + query);
/*      */     try
/*      */     {
/* 1683 */       set = AMConnectionPool.executeQueryStmt(query);
/* 1684 */       ArrayList rows = new ArrayList();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1693 */       while (set.next())
/*      */       {
/* 1695 */         int type = set.getInt(6);
/* 1696 */         StringBuilder labelBld = new StringBuilder().append(set.getString(2)).append(":(");
/* 1697 */         if (type == 1)
/*      */         {
/* 1699 */           labelBld.append(set.getString(3)).append(")");
/*      */         }
/* 1701 */         else if (type == 2)
/*      */         {
/* 1703 */           labelBld.append(set.getString(4)).append(")");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/* 1709 */         else if (type == 3)
/*      */         {
/* 1711 */           labelBld.append(set.getString(5)).append(")");
/*      */         }
/* 1713 */         Properties dataProps = new Properties();
/* 1714 */         dataProps.setProperty("label", labelBld.toString());
/* 1715 */         dataProps.setProperty("value", set.getString(1));
/* 1716 */         rows.add(dataProps);
/*      */       }
/* 1718 */       AMConnectionPool.closeStatement(set);
/*      */       
/* 1720 */       ((AMActionForm)form).setApplications(rows);
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1724 */       AMLog.fatal("Alert Escalation :  Exception ", exp);
/* 1725 */       exp.printStackTrace();
/* 1726 */       throw new Exception(exp);
/*      */     }
/*      */   }
/*      */   
/*      */   public static Hashtable<String, String> getActionExecResp(String actionMethod, String actionId)
/*      */   {
/* 1732 */     Hashtable<String, String> result = new Hashtable();
/* 1733 */     String message = "";
/* 1734 */     if (actionMethod.equals("testAction"))
/*      */     {
/* 1736 */       result = AMActionExecuter.executeAction(Integer.parseInt(actionId));
/* 1737 */       String suxs = (String)result.get("success");
/* 1738 */       boolean success = (suxs != null) && (suxs.equals("true"));
/* 1739 */       message = result.get("message") != null ? (String)result.get("message") : "";
/* 1740 */       if (success)
/*      */       {
/* 1742 */         message = FormatUtil.getString("am.webclient.testaction.success", new String[] { (String)result.get("name") });
/*      */       }
/* 1744 */       else if (((String)result.get("actiontype")).equals("4")) {
/* 1745 */         message = FormatUtil.getString("am.webclient.pgmaction.failed", new String[] { (String)result.get("name") }) + message;
/*      */       }
/* 1747 */       else if ((((String)result.get("actiontype")).equals(String.valueOf(11))) || (((String)result.get("actiontype")).equals(String.valueOf(12))) || (((String)result.get("actiontype")).equals(String.valueOf(13)))) {
/* 1748 */         message = FormatUtil.getString("am.webclient.pgmaction.failed", new String[] { (String)result.get("name") });
/*      */       }
/* 1750 */       else if (((String)result.get("actiontype")).equals("3")) {
/* 1751 */         message = FormatUtil.getString("am.webclient.pgmaction.failed", new String[] { (String)result.get("name") }) + message;
/*      */       }
/*      */       else
/*      */       {
/* 1755 */         message = (message == null) || (message.equals("null")) ? "" : message;
/* 1756 */         message = FormatUtil.getString("am.webclient.testaction.failed", new String[] { (String)result.get("name") }) + message;
/*      */       }
/*      */     }
/* 1759 */     else if (actionMethod.equals("testthreaddumpAction"))
/*      */     {
/* 1761 */       HashMap<String, String> resultMap = com.adventnet.appmanager.jmx.JDK15Util.sendThreadDumpMail(Integer.parseInt(actionId));
/* 1762 */       result.putAll(resultMap);
/* 1763 */       String suxs = (String)result.get("success");
/* 1764 */       boolean success = (suxs != null) && (suxs.equals("true"));
/* 1765 */       message = FormatUtil.getString("am.webclient.testaction.success", new String[] { (String)result.get("name") });
/*      */       
/* 1767 */       if (!success)
/*      */       {
/* 1769 */         message = FormatUtil.getString("am.webclient.testaction.failed", new String[] { (String)result.get("name") });
/*      */       }
/*      */     }
/*      */     
/* 1773 */     result.put("message", message);
/* 1774 */     return result;
/*      */   }
/*      */   
/*      */   public ActionForward testAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1778 */     ActionMessages messages = new ActionMessages();
/* 1779 */     ActionErrors errors = new ActionErrors();
/* 1780 */     String actionID = request.getParameter("actionID");
/* 1781 */     if (isTestActionAllowed(request)) {
/* 1782 */       Hashtable<String, String> result = getActionExecResp("testAction", actionID);
/* 1783 */       boolean success = (result.get("success") != null) && (((String)result.get("success")).equals("true"));
/*      */       
/* 1785 */       String message = (String)result.get("message");
/* 1786 */       if (request.getParameter("remote") != null)
/*      */       {
/* 1788 */         sendResponse(actionID, success, message, response);
/* 1789 */         return null; }
/* 1790 */       if (((String)result.get("actiontype")).equals("3")) {
/* 1791 */         new ActionForward(mapping.findForward("success").getPath() + "&noreload=true");
/*      */       }
/*      */       
/* 1794 */       if (success) {
/* 1795 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", message));
/* 1796 */         saveMessages(request, messages);
/*      */       } else {
/* 1798 */         String errMsg = (String)result.get("message");
/* 1799 */         if ((errMsg == null) || (errMsg.equals("null"))) {
/* 1800 */           errMsg = "";
/*      */         }
/* 1802 */         if (((String)result.get("actiontype")).equals("17")) {
/* 1803 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.webclient.sdptestaction.failed", new String[] { (String)result.get("name") })));
/* 1804 */         } else if (!"".equals(errMsg))
/*      */         {
/* 1806 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", errMsg));
/*      */         } else {
/* 1808 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.webclient.testaction.failed", new String[] { (String)result.get("name") }) + errMsg));
/*      */         }
/* 1810 */         saveErrors(request, errors);
/*      */       }
/*      */     } else {
/* 1813 */       AMLog.debug("AdminTools : testAction. User " + request.getRemoteUser() + " not allowed to execute test Action ID : " + actionID);
/* 1814 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.mypage.adminactions.noauthorization.text")));
/* 1815 */       saveErrors(request, errors);
/*      */     }
/*      */     
/* 1818 */     if (request.getParameter("redirectto") != null) {
/* 1819 */       return new ActionForward(request.getParameter("redirectto") + "&noreload=true", true);
/*      */     }
/* 1821 */     return new ActionForward(mapping.findForward("success").getPath() + "&noreload=true");
/*      */   }
/*      */   
/*      */   public ActionForward testExecQueryAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1825 */     ActionMessages messages = new ActionMessages();
/* 1826 */     ActionErrors errors = new ActionErrors();
/* 1827 */     String actionID = request.getParameter("actionID");
/* 1828 */     if (isTestActionAllowed(request)) {
/* 1829 */       HashMap result = sendExecuteQueryMail(Integer.parseInt(actionID));
/* 1830 */       String suxs = (String)result.get("success");
/* 1831 */       boolean success = (suxs != null) && (suxs.equals("true"));
/*      */       
/* 1833 */       if (success) {
/* 1834 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.testaction.success", new String[] { (String)result.get("name") })));
/* 1835 */         saveMessages(request, messages);
/*      */       } else {
/* 1837 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.webclient.testaction.failed", new String[] { (String)result.get("name") })));
/* 1838 */         saveErrors(request, errors);
/*      */       }
/*      */     } else {
/* 1841 */       AMLog.debug("AdminTools : testExecQueryAction. User " + request.getRemoteUser() + " not allowed to execute test Action ID : " + actionID);
/* 1842 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.mypage.adminactions.noauthorization.text")));
/* 1843 */       saveErrors(request, errors);
/*      */     }
/* 1845 */     String redirectTo = request.getParameter("redirectto");
/* 1846 */     if (redirectTo != null) {
/* 1847 */       return new ActionForward(redirectTo + "&noreload=true");
/*      */     }
/* 1849 */     return new ActionForward(mapping.findForward("success").getPath() + "&noreload=true");
/*      */   }
/*      */   
/*      */   public ActionForward testthreaddumpAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1854 */     ActionMessages messages = new ActionMessages();
/* 1855 */     ActionErrors errors = new ActionErrors();
/* 1856 */     String actionID = request.getParameter("actionID");
/* 1857 */     if (isTestActionAllowed(request)) {
/* 1858 */       Hashtable<String, String> result = getActionExecResp("testthreaddumpAction", actionID);
/* 1859 */       boolean success = (result.get("success") != null) && (((String)result.get("success")).equals("true"));
/* 1860 */       String message = (String)result.get("message");
/*      */       
/* 1862 */       if (success) {
/* 1863 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", message));
/* 1864 */         saveMessages(request, messages);
/*      */       } else {
/* 1866 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", message));
/* 1867 */         saveErrors(request, errors);
/*      */       }
/*      */       
/* 1870 */       if (request.getParameter("remote") != null) {
/* 1871 */         sendResponse(actionID, success, message, response);
/* 1872 */         return null;
/*      */       }
/*      */     } else {
/* 1875 */       AMLog.debug("AdminTools : testthreaddumpAction. User " + request.getRemoteUser() + " not allowed to execute test Action ID : " + actionID);
/* 1876 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", FormatUtil.getString("am.mypage.adminactions.noauthorization.text")));
/* 1877 */       saveErrors(request, errors);
/*      */     }
/* 1879 */     if (request.getParameter("redirectto") != null) {
/* 1880 */       return new ActionForward(request.getParameter("redirectto") + "&noreload=true");
/*      */     }
/* 1882 */     return new ActionForward(mapping.findForward("success").getPath() + "&noreload=true");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isTestActionAllowed(HttpServletRequest request)
/*      */   {
/* 1893 */     boolean actionAllowed = false;
/*      */     try
/*      */     {
/* 1896 */       int actionID = Integer.parseInt(request.getParameter("actionID"));
/* 1897 */       actionAllowed = com.me.apm.fault.actions.util.ActionsUtil.isUserPermittedAction(request.getRemoteUser(), actionID);
/*      */     } catch (Exception e) {
/* 1899 */       AMLog.debug("AdminTools : isTestActionAllowed. Error occurred while get Actions for username :" + request.getRemoteUser() + " actionId :" + request.getParameter("actionID") + ". Error message : " + e.getMessage());
/* 1900 */       e.printStackTrace();
/*      */     }
/* 1902 */     return actionAllowed;
/*      */   }
/*      */   
/*      */   public static void sendResponse(String actionID, boolean status, String message, HttpServletResponse response)
/*      */   {
/* 1907 */     sendResponse(actionID, Boolean.toString(status), message, response);
/*      */   }
/*      */   
/*      */   public static void sendResponse(String actionID, String status, String message, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1914 */       response.setCharacterEncoding("UTF-8");
/* 1915 */       StringBuffer buffer = null;
/*      */       
/* 1917 */       PrintWriter out = response.getWriter();
/* 1918 */       buffer = new StringBuffer();
/* 1919 */       JSONObject json = new JSONObject();
/* 1920 */       json.put("status", status);
/* 1921 */       json.put("actionID", actionID);
/* 1922 */       json.put("message", message);
/* 1923 */       buffer.append(json.toString());
/* 1924 */       out.println(buffer);
/* 1925 */       out.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1929 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static HashMap sendExecuteQueryMail(int actionID)
/*      */   {
/* 1935 */     HashMap<String, String> result = new HashMap();
/* 1936 */     String fromAddress = null;
/* 1937 */     String toAddress = null;
/* 1938 */     String subject = null;
/* 1939 */     String mess = null;
/* 1940 */     int mailformat = 1;
/* 1941 */     ResultSet rs = null;
/*      */     try {
/* 1943 */       String query = "select AP.NAME,EA.FROMADDRESS frmaddr, EA.TOADDRESS toaddr, EA.SUBJECT subj, EA.MESSAGE mess, EA.MAILFORMAT mailfmt, EA.APPENDMESSAGE appendmsg from AM_EMAILACTION EA,AM_QUERYACTIONS QA,AM_ACTIONPROFILE AP where QA.EMAIL_ACTION_ID=EA.ID and AP.ID=QA.ID and QA.ID=" + actionID;
/* 1944 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1945 */       if (rs.next())
/*      */       {
/* 1947 */         fromAddress = rs.getString("frmaddr");
/* 1948 */         toAddress = rs.getString("toaddr");
/* 1949 */         subject = rs.getString("NAME") + "-" + rs.getString("subj");
/* 1950 */         mess = rs.getString("mess");
/* 1951 */         mailformat = rs.getInt("mailfmt");
/* 1952 */         result.put("name", rs.getString("NAME"));
/*      */       }
/* 1954 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1956 */       String returnVal = null;
/* 1957 */       SmtpEMailer mailer = null;
/*      */       try
/*      */       {
/* 1960 */         mailer = new SmtpEMailer(fromAddress, toAddress, "", subject);
/* 1961 */         returnVal = mailer.sendMessage(mess, null, true, mess, mailformat, null);
/*      */         
/* 1963 */         if (returnVal == null)
/*      */         {
/* 1965 */           AMLog.debug("Test Execute Query Action Mail Successfully Sent for Action ID===>" + actionID);
/* 1966 */           result.put("success", "true");
/*      */         }
/*      */         else
/*      */         {
/* 1970 */           AMLog.debug("Error Sending Test Execute Query Action Mail for Action ID===>" + actionID);
/* 1971 */           result.put("success", "false");
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1976 */         ex.printStackTrace();
/*      */       }
/* 1978 */       mailer.close();
/*      */     }
/*      */     catch (Exception e) {
/* 1981 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1984 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1986 */     return result;
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
/*      */   public ActionForward executeMBeanOperationWithChoosableParams(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2001 */     String[] selectedValues = request.getParameterValues("select");
/* 2002 */     ActionMessages messages = new ActionMessages();
/* 2003 */     ActionErrors errors = new ActionErrors();
/* 2004 */     String actionID = request.getParameter("actionID");
/* 2005 */     Hashtable result = AMActionExecuter.executeMBeanOperationAction(Integer.parseInt(actionID), selectedValues);
/* 2006 */     String suxs = (String)result.get("success");
/* 2007 */     boolean success = (suxs != null) && (suxs.equals("true"));
/*      */     
/* 2009 */     if (success)
/*      */     {
/* 2011 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("The action has been successfully executed")));
/* 2012 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", result.get("message")));
/* 2013 */       saveMessages(request, messages);
/*      */     }
/*      */     else
/*      */     {
/* 2017 */       String res = (String)result.get("message");
/* 2018 */       if ((res == null) || (res.equals("null")))
/*      */       {
/* 2020 */         res = "";
/*      */       }
/* 2022 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionMessage("appmanager.error", "The action '" + result.get("name") + "' has failed." + res));
/* 2023 */       saveErrors(request, errors);
/*      */     }
/* 2025 */     return new ActionForward("/MBeanOperationAction.do?method=executeMBeanOperationActionWithUserIntervention&actionID=" + actionID);
/*      */   }
/*      */   
/*      */   public ActionForward showLicCustomReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 2031 */     ActionMessages messages = new ActionMessages();
/* 2032 */     AMActionForm amform = (AMActionForm)form;
/* 2033 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2034 */     ResultSet rs = null;
/* 2035 */     ArrayList monthList = new ArrayList();
/* 2036 */     ArrayList yearList = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 2040 */       String monthQuery = "select distinct MONTH from IT360MONTHLYCOLLECTION";
/* 2041 */       rs = AMConnectionPool.executeQueryStmt(monthQuery);
/* 2042 */       while (rs.next())
/*      */       {
/* 2044 */         String month = rs.getString("MONTH");
/* 2045 */         monthList.add(month);
/*      */       }
/* 2047 */       String yearQuery = "select distinct YEAR from IT360MONTHLYCOLLECTION";
/* 2048 */       rs = AMConnectionPool.executeQueryStmt(yearQuery);
/* 2049 */       while (rs.next())
/*      */       {
/* 2051 */         String year = rs.getString("YEAR");
/* 2052 */         yearList.add(year);
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 2057 */       ee.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2061 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*      */ 
/* 2065 */     String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
/* 2066 */     List list = Arrays.asList(months);
/* 2067 */     int[] numbers = new int[monthList.size()];
/* 2068 */     for (int i = 0; i < monthList.size(); i++)
/*      */     {
/* 2070 */       String month = (String)monthList.get(i);
/* 2071 */       int monthInNumber = list.indexOf(month);
/* 2072 */       numbers[i] = monthInNumber;
/*      */     }
/* 2074 */     bubbleSortInAscendingOrder(numbers);
/*      */     
/* 2076 */     ArrayList aList = new ArrayList();
/* 2077 */     for (int j = 0; j < numbers.length; j++)
/*      */     {
/* 2079 */       int num = numbers[j];
/* 2080 */       aList.add(months[num]);
/*      */     }
/*      */     
/* 2083 */     request.setAttribute("monthsList", aList);
/* 2084 */     request.setAttribute("yearsList", yearList);
/* 2085 */     return new ActionForward("/it360/jsp/LicCustomReports.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   private static void bubbleSortInAscendingOrder(int[] numbers)
/*      */   {
/* 2091 */     for (int i = 0; i < numbers.length; i++)
/*      */     {
/* 2093 */       for (int j = 1; j < numbers.length - i; j++)
/*      */       {
/*      */ 
/* 2096 */         if (numbers[(j - 1)] > numbers[j])
/*      */         {
/* 2098 */           int temp = numbers[(j - 1)];
/* 2099 */           numbers[(j - 1)] = numbers[j];
/* 2100 */           numbers[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward showLicConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2108 */     ActionMessages messages = new ActionMessages();
/* 2109 */     AMActionForm amform = (AMActionForm)form;
/* 2110 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 2115 */       String fromAddress = getValueFromGlobalConfig("FROM_ADDRESS");
/* 2116 */       String toAddress = getValueFromGlobalConfig("TO_ADDRESS");
/* 2117 */       AMLog.debug("fromAddress " + fromAddress + " toAddress " + toAddress);
/*      */       
/* 2119 */       if ((fromAddress != null) && (toAddress != null))
/*      */       {
/* 2121 */         amform.setLicFromAddress(fromAddress);
/* 2122 */         amform.setLicToAddress(toAddress);
/*      */       }
/*      */       else
/*      */       {
/* 2126 */         fromAddress = "";
/* 2127 */         toAddress = "";
/*      */       }
/* 2129 */       request.setAttribute("licFromAddress", fromAddress);
/* 2130 */       request.setAttribute("licToAddress", toAddress);
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 2134 */       ee.printStackTrace();
/*      */     }
/* 2136 */     return new ActionForward("/it360/jsp/LicUsageConfig.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward updateLicConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2141 */     ActionMessages messages = new ActionMessages();
/* 2142 */     AMActionForm amform = (AMActionForm)form;
/* 2143 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*      */     
/*      */     try
/*      */     {
/* 2147 */       updateLicUsageGlobalValues("FROM_ADDRESS", amform.getLicFromAddress());
/* 2148 */       updateLicUsageGlobalValues("TO_ADDRESS", amform.getLicToAddress());
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/* 2152 */       ee.printStackTrace();
/*      */     }
/* 2154 */     showLicConfiguration(mapping, form, request, response);
/* 2155 */     request.setAttribute("sucess", FormatUtil.getString("am.webclient.dbretention.sucess.text"));
/*      */     
/* 2157 */     return new ActionForward("/it360/jsp/LicUsageConfig.jsp");
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
/*      */   public static void updateLicUsageGlobalValues(String key, String value)
/*      */   {
/* 2170 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2173 */       String selectQuery = "select * from AM_GLOBALCONFIG where NAME='" + key + "'";
/* 2174 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 2175 */       if (rs.next())
/*      */       {
/* 2177 */         String updateQuery = "update AM_GLOBALCONFIG set VALUE ='" + value + "' where NAME='" + key + "'";
/* 2178 */         AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */       }
/*      */       else
/*      */       {
/* 2182 */         String insertQuery = "insert into AM_GLOBALCONFIG (NAME, VALUE) values ('" + key + "','" + value + "')";
/* 2183 */         AMConnectionPool.executeUpdateStmt(insertQuery);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2188 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2192 */       AMConnectionPool.closeStatement(rs);
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
/*      */   private static String getValueFromGlobalConfig(String key)
/*      */   {
/* 2205 */     ResultSet rs = null;
/* 2206 */     String value = null;
/*      */     
/*      */     try
/*      */     {
/* 2210 */       rs = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='" + key + "'");
/* 2211 */       if (rs.next())
/*      */       {
/* 2213 */         value = rs.getString("VALUE");
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2218 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2222 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2224 */     return value;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AdminTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */