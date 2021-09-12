/*      */ package com.adventnet.appmanager.as400.struts;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMBatchStmtExecutor;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMThresholdApplier;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.as400.datacollection.AS400Util;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DataCollectionComponent;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.GenURLForHTML;
/*      */ import com.adventnet.nms.appln.hostresource.datacollection.server.model.HostDetails;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.ibm.as400.access.AS400;
/*      */ import com.ibm.as400.access.AS400BidiTransform;
/*      */ import com.ibm.as400.access.AS400Message;
/*      */ import com.ibm.as400.access.AS400SecurityException;
/*      */ import com.ibm.as400.access.CommandCall;
/*      */ import com.ibm.as400.access.Job;
/*      */ import com.ibm.as400.access.JobList;
/*      */ import com.ibm.as400.access.JobLog;
/*      */ import com.ibm.as400.access.MessageQueue;
/*      */ import com.ibm.as400.access.PrintObjectTransformedInputStream;
/*      */ import com.ibm.as400.access.PrintParameterList;
/*      */ import com.ibm.as400.access.Product;
/*      */ import com.ibm.as400.access.ProductList;
/*      */ import com.ibm.as400.access.QSYSObjectPathName;
/*      */ import com.ibm.as400.access.QueuedMessage;
/*      */ import com.ibm.as400.access.SocketProperties;
/*      */ import com.ibm.as400.access.SpooledFile;
/*      */ import com.ibm.as400.access.Subsystem;
/*      */ import com.ibm.as400.access.SystemValue;
/*      */ import com.ibm.as400.access.SystemValueList;
/*      */ import com.ibm.as400.access.User;
/*      */ import com.ibm.as400.access.UserList;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
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
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONObject;
/*      */ 
/*      */ public class AS400Action extends DispatchAction
/*      */ {
/*   80 */   private Hashtable as400Responses = null;
/*   81 */   private boolean isAction = false;
/*      */   
/*   83 */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception { response.setContentType("text/html; charset=UTF-8");
/*   84 */     String datatypestr = request.getParameter("datatype");
/*   85 */     int datatype = 1;
/*      */     
/*   87 */     String resid = request.getParameter("resourceid");
/*   88 */     Integer resourceid = Integer.valueOf(Integer.parseInt(resid));
/*      */     
/*   90 */     if (datatypestr != null) {
/*   91 */       datatype = Integer.parseInt(datatypestr);
/*      */     }
/*   93 */     if (datatype == 1) {
/*   94 */       overviewDetails(mapping, form, request, response);
/*      */     }
/*   96 */     else if (datatype == 2) {
/*   97 */       statusDetails(mapping, form, request, response);
/*      */     }
/*   99 */     else if (datatype == 3) {
/*  100 */       poolDetails(mapping, form, request, response);
/*      */     }
/*  102 */     else if (datatype == 4) {
/*  103 */       jobsDetails(mapping, form, request, response);
/*      */     }
/*  105 */     else if (datatype == 5) {
/*  106 */       messagesDetails(mapping, form, request, response);
/*      */     }
/*  108 */     else if (datatype == 6) {
/*  109 */       spoolDetails(mapping, form, request, response);
/*      */     }
/*  111 */     else if (datatype == 7) {
/*  112 */       printerDetails(mapping, form, request, response);
/*      */     }
/*  114 */     else if (datatype == 8) {
/*  115 */       diskDetails(mapping, form, request, response);
/*      */     }
/*  117 */     else if (datatype == 9) {
/*  118 */       problemDetails(mapping, form, request, response);
/*      */     }
/*  120 */     else if (datatype == 10) {
/*  121 */       subsystemDetails(mapping, form, request, response);
/*      */     }
/*  123 */     else if (datatype == 11) {
/*  124 */       historyLogDetails(mapping, form, request, response);
/*      */     }
/*  126 */     else if (datatype == 12) {
/*  127 */       queueDetails(mapping, form, request, response);
/*      */     }
/*  129 */     else if (datatype == 13) {
/*  130 */       ifsDetails(mapping, form, request, response);
/*      */     }
/*  132 */     else if (datatype == 14) {
/*  133 */       adminDetails(mapping, form, request, response);
/*      */     }
/*      */     try {
/*  136 */       if (!getIsActionMade()) {
/*  137 */         String validation = getAS400Error(resid);
/*  138 */         AMLog.info("AS400 Action: showdetails validation" + validation);
/*      */       }
/*  140 */       AMLog.info("AS400 Action: as400Responsess : " + this.as400Responses + "isAction" + this.isAction);
/*  141 */       if ((this.as400Responses.containsKey("success")) && (this.as400Responses.containsKey("message"))) {
/*  142 */         String message = "" + this.as400Responses.get("message");
/*  143 */         ActionMessages messages = new ActionMessages();
/*  144 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage(message));
/*  145 */         saveMessages(request, messages);
/*  146 */       } else if (this.as400Responses.containsKey("failed")) {
/*  147 */         String message = "" + this.as400Responses.get("failed");
/*  148 */         ActionErrors errors = new ActionErrors();
/*  149 */         errors.add("org.apache.struts.action.ERROR", new ActionError(message));
/*  150 */         saveErrors(request, errors);
/*      */       }
/*  152 */       setIsActionMade(false);
/*      */     } catch (Exception e) {
/*  154 */       e.printStackTrace();
/*      */     }
/*      */     
/*  157 */     this.as400Responses = null;
/*  158 */     return mapping.findForward("details");
/*      */   }
/*      */   
/*      */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  164 */     resid = request.getParameter("resourceid");
/*  165 */     String dispName = ((AMActionForm)form).getDisplayname();
/*  166 */     int pollInterval = ((AMActionForm)form).getPollInterval() * 60;
/*  167 */     String username = ((AMActionForm)form).getUsername();
/*  168 */     String password = ((AMActionForm)form).getPassword();
/*      */     
/*  170 */     String monname = " ";
/*  171 */     String query = "select RESOURCENAME from AM_ManagedObject where resourceid=" + resid;
/*  172 */     ResultSet rs = null;
/*  173 */     rs = AMConnectionPool.executeQueryStmt(query);
/*  174 */     if (rs.next()) {
/*  175 */       monname = rs.getString("RESOURCENAME");
/*      */     }
/*  177 */     String validation = "";
/*  178 */     setIsActionMade(true);
/*  179 */     validation = validateAS400(monname, username, password);
/*  180 */     AMLog.debug("AS400 Action: edit as400 monitor - validation" + validation);
/*  181 */     if (validation.equals("success")) {
/*  182 */       query = "update HostDetails set USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + " where RESOURCENAME='" + monname + "'";
/*  183 */       AMConnectionPool.executeUpdateStmt(query);
/*  184 */       EnterpriseUtil.addUpdateQueryToFile(query);
/*      */     } else {
/*  186 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */     }
/*      */     
/*  189 */     query = "update AM_ManagedObject set displayname='" + dispName + "' where resourceid=" + resid;
/*  190 */     AMConnectionPool.executeUpdateStmt(query);
/*  191 */     EnterpriseUtil.addUpdateQueryToFile(query);
/*  192 */     query = "update CollectData set pollinterval=" + pollInterval + " where resourcename='" + monname + "' and COMPONENTNAME='HOST'";
/*  193 */     AMConnectionPool.executeUpdateStmt(query);
/*  194 */     EnterpriseUtil.addUpdateQueryToFile(query);
/*      */     
/*  196 */     ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*  197 */     query = "select RESOURCENAME from AM_ManagedObject where resourceid=" + resid;
/*  198 */     rs = null;
/*      */     try {
/*  200 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  201 */       if (rs.next()) {
/*      */         try {
/*  203 */           HostDetails Host1 = (HostDetails)api.getCollectData(rs.getString("RESOURCENAME"), "HOST");
/*      */           
/*  205 */           Properties userProps = new Properties();
/*  206 */           userProps.setProperty("resourceName", dispName);
/*  207 */           userProps.setProperty("userName", username);
/*  208 */           userProps.setProperty("password", password);
/*  209 */           userProps.setProperty("resourceType", "AS400/iSeries");
/*  210 */           userProps.setProperty("pollInterval", String.valueOf(pollInterval));
/*      */           
/*  212 */           Host1.setActive(true);
/*  213 */           Host1.setProperties(userProps);
/*  214 */           Host1.setConfigured(true);
/*      */           
/*      */ 
/*  217 */           AMDataCollectionHandler.getInstance();AMDataCollectionHandler.scheduleDataCollection(Host1, true);
/*      */         }
/*      */         catch (Exception e) {
/*  220 */           e.printStackTrace();
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
/*  236 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  224 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  227 */       if (rs != null) {
/*      */         try {
/*  229 */           AMConnectionPool.closeStatement(rs);
/*      */         } catch (Exception e) {
/*  231 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward overviewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  245 */       response.setContentType("text/html; charset=UTF-8");
/*  246 */       String resid = request.getParameter("resourceid");
/*  247 */       Integer resourceid = Integer.valueOf(Integer.parseInt(resid));
/*  248 */       long collectiontime = getMaxCollectionTime(resourceid.intValue());
/*  249 */       ArrayList resIDs = new ArrayList();
/*  250 */       resIDs.add(resid);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  255 */       String query3 = null;
/*  256 */       ResultSet rs3 = null;
/*  257 */       boolean disablePool = false;boolean disableDisk = false;
/*      */       
/*      */ 
/*  260 */       String query = "select max(collectiontime) as coltime from AM_AS400_CONFIGURATION  where RESOURCEID=" + resid;
/*  261 */       ResultSet rs2 = AMConnectionPool.executeQueryStmt(query);
/*  262 */       if (rs2.next()) {
/*  263 */         query = "select mo.DISPLAYNAME,bi.* from AM_AS400_DISK bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + resid + " and collectiontime=" + rs2.getString("coltime") + " order by DRIVE_PER desc";
/*  264 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  265 */         ArrayList buffdata = new ArrayList();
/*  266 */         while (rs.next()) {
/*  267 */           buffdata.add(rs.getString("RESOURCEID"));
/*      */         }
/*  269 */         AMConnectionPool.closeStatement(rs);
/*  270 */         request.setAttribute("buffdata", buffdata);
/*      */       }
/*  272 */       AMConnectionPool.closeStatement(rs2);
/*      */       
/*      */ 
/*      */ 
/*  276 */       long time = 0L;
/*  277 */       query = "select max(collectiontime) as coltime from AM_AS400_STATUS where RESOURCEID=" + resid;
/*  278 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  279 */       if (rs.next()) {
/*  280 */         time = rs.getLong("coltime");
/*      */       }
/*  282 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  284 */       boolean jobtypeg = false;
/*  285 */       int count = 0;
/*  286 */       query = "select max(TCOUNT) as TCOUNT from AM_AS400_JOBTYPES where RESOURCEID=" + resid;
/*  287 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  288 */       if (rs.next()) {
/*  289 */         jobtypeg = true;
/*  290 */         if (rs.getString("TCOUNT") != null)
/*      */         {
/*  292 */           count = Integer.valueOf(Integer.parseInt(rs.getString("TCOUNT"))).intValue();
/*      */         }
/*  294 */         if (count < 1) {
/*  295 */           jobtypeg = false;
/*      */         }
/*      */       }
/*  298 */       AMConnectionPool.closeStatement(rs);
/*  299 */       request.setAttribute("jobtypeg", Boolean.valueOf(jobtypeg));
/*      */       
/*  301 */       query = "select * from AM_AS400_CONFIGURATION where RESOURCEID=" + resid;
/*  302 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  303 */       if (rs.next()) {
/*  304 */         request.setAttribute("servername1", rs.getString("NAME"));
/*  305 */         request.setAttribute("model1", rs.getString("MODEL"));
/*  306 */         request.setAttribute("version1", rs.getString("VERSION"));
/*  307 */         request.setAttribute("serialno1", rs.getString("SERIAL"));
/*  308 */         request.setAttribute("securitylevel1", rs.getString("SECURITY_LEVEL"));
/*  309 */         request.setAttribute("previoussystemend1", rs.getString("PREVIOUS_SYSTEM_END"));
/*  310 */         request.setAttribute("autodeviceconfiguration", rs.getString("AUTO_DEVICE_CONFIGURATION"));
/*  311 */         request.setAttribute("systemconsole", rs.getString("SYSTEM_CONSOLE"));
/*  312 */         request.setAttribute("jobmessagequeueinitialsize", rs.getString("JOB_MSGQUEUE_INITIAL_SIZE"));
/*  313 */         request.setAttribute("jobmessaequeuemaximumsize", rs.getString("JOB_MSGQUEUE_MAXIMUM_SIZE"));
/*  314 */         request.setAttribute("spoolingcontrolinitialsize", rs.getString("SPOOLING_CONTROL_INITIAL_SIZE"));
/*  315 */         request.setAttribute("maximumjobsallowed", rs.getString("MAXIMUM_JOBS_ALLOWED"));
/*  316 */         request.setAttribute("passwordvaliddays", rs.getString("PASSWORD_VALID_DAYS"));
/*  317 */         request.setAttribute("queryprocessingtimelimit", rs.getString("QUERY_PROCESSING_TIME_LIMIT"));
/*      */       }
/*      */       
/*  320 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  322 */       query = "select * from AM_AS400_STATUS where RESOURCEID=" + resid + " and COLLECTIONTIME=" + time;
/*  323 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  324 */       if (rs.next()) {
/*  325 */         request.setAttribute("ASP_PERCENTAGE", rs.getString("ASP_PERCENTAGE"));
/*  326 */         request.setAttribute("DB_PERCENTAGE", rs.getString("DB_PERCENTAGE"));
/*  327 */         request.setAttribute("PROCESSINGUNIT_PERCENTAGE", rs.getString("PROCESSINGUNIT_PERCENTAGE"));
/*  328 */         request.setAttribute("PERMANENT_ADDRESS_PERCENTAGE", rs.getString("PERMANENT_ADDRESS_PERCENTAGE"));
/*  329 */         request.setAttribute("TEMPORARY_ADDRESS_PERCENTAGE", rs.getString("TEMPORARY_ADDRESS_PERCENTAGE"));
/*  330 */         request.setAttribute("USERSUSPENDEDBYGROUPJOB", rs.getString("USERSUSPENDEDBYGROUPJOB"));
/*  331 */         request.setAttribute("USERSUSPENDEDBYSYSTEMREQUEST", rs.getString("USERSUSPENDEDBYSYSTEMREQUEST"));
/*  332 */         request.setAttribute("USERSTEMPORARILYSOFF", rs.getString("USERSTEMPORARILYSOFF"));
/*  333 */         request.setAttribute("CURRENTPROCESSINGCAPACITY", rs.getString("CURRENTPROCESSINGCAPACITY"));
/*  334 */         request.setAttribute("CURRENTINTRACTIVEPERFORMANCEPER", rs.getString("CURRENTINTRACTIVEPERFORMANCEPER"));
/*  335 */         request.setAttribute("SHAREDPROCESSINGPOOLPER", rs.getString("SHAREDPROCESSINGPOOLPER"));
/*  336 */         request.setAttribute("UNCAPPEDCPUCAPACITYPER", rs.getString("UNCAPPEDCPUCAPACITYPER"));
/*      */       }
/*      */       
/*  339 */       AMConnectionPool.closeStatement(rs);
/*      */       try
/*      */       {
/*  342 */         long responsetime = 0L;
/*  343 */         ResultSet rs1 = null;
/*      */         try
/*      */         {
/*  346 */           AMConnectionPool.getInstance();rs1 = AMConnectionPool.executeQueryStmt("select  RESPONSETIME from AM_ManagedObjectData where RESID=" + resid + " order by COLLECTIONTIME desc");
/*  347 */           if (rs1.next())
/*      */           {
/*  349 */             responsetime = rs1.getLong("RESPONSETIME");
/*      */           }
/*  351 */           request.setAttribute("responsetime", Long.valueOf(responsetime));
/*      */ 
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  356 */           exc.printStackTrace();
/*      */         }
/*      */         finally {
/*  359 */           AMConnectionPool.closeStatement(rs1);
/*      */         }
/*      */         
/*  362 */         String dispname = null;
/*  363 */         String resName = null;
/*  364 */         String query1 = "select DISPLAYNAME,RESOURCENAME from AM_ManagedObject where RESOURCEID=" + resid;
/*  365 */         rs = AMConnectionPool.executeQueryStmt(query1);
/*  366 */         if (rs.next()) {
/*  367 */           dispname = rs.getString("DISPLAYNAME");
/*  368 */           resName = rs.getString("RESOURCENAME");
/*  369 */           ((AMActionForm)form).setDisplayname(dispname);
/*  370 */           request.setAttribute("displayname", dispname);
/*  371 */           request.setAttribute("resourcename", resName);
/*      */         }
/*  373 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  375 */         query1 = "select USERNAME from HostDetails where RESOURCENAME='" + resName + "'";
/*  376 */         rs = AMConnectionPool.executeQueryStmt(query1);
/*  377 */         if (rs.next()) {
/*  378 */           String username = rs.getString("USERNAME");
/*  379 */           request.setAttribute("username", username);
/*  380 */           ((AMActionForm)form).setUsername(username);
/*      */         }
/*      */         
/*  383 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  385 */         query = "select POLLINTERVAL from CollectData cd,AM_ManagedObject mo where mo.RESOURCEID=" + resid + " and mo.RESOURCENAME=cd.RESOURCENAME";
/*  386 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  387 */         int pollint = 0;
/*  388 */         if (rs.next()) {
/*  389 */           pollint = rs.getInt("POLLINTERVAL");
/*  390 */           int poll1 = pollint / 60;
/*  391 */           ((AMActionForm)form).setPollInterval(poll1);
/*      */         }
/*  393 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  395 */         String lastpoll = "-";
/*  396 */         String nextpoll = "-";
/*  397 */         query = "select max(collectiontime) as coltime from AM_ManagedObjectData where RESID=" + resid;
/*  398 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  399 */         if (rs.next()) {
/*  400 */           long l = rs.getLong("coltime");
/*  401 */           long np = l + pollint * 1000;
/*  402 */           lastpoll = FormatUtil.formatDT(String.valueOf(l));
/*  403 */           nextpoll = FormatUtil.formatDT(String.valueOf(np));
/*      */         }
/*  405 */         AMConnectionPool.closeStatement(rs);
/*      */         
/*  407 */         request.setAttribute("LASTDC", lastpoll);
/*  408 */         request.setAttribute("NEXTDC", nextpoll);
/*      */         
/*  410 */         String poll = (String)request.getAttribute("reloadperiod");
/*  411 */         HashMap map = null;
/*  412 */         if (poll == null) {
/*  413 */           poll = "300";
/*      */         }
/*  415 */         map = com.adventnet.appmanager.struts.beans.ClientDBUtil.getSystemHealthPollInfoForService(resid, Long.parseLong(poll));
/*      */         
/*  417 */         if (map != null) {
/*  418 */           request.setAttribute("systeminfo", map);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  424 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  427 */         AMConnectionPool.closeStatement(rs);
/*  428 */         AMConnectionPool.closeStatement(rs2);
/*      */       }
/*      */       try
/*      */       {
/*  432 */         query3 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/*  433 */         rs3 = AMConnectionPool.executeQueryStmt(query3);
/*  434 */         while (rs3.next()) {
/*  435 */           if (rs3.getString("COMPONENTNAME").equals("POOLMONITORING")) {
/*  436 */             disablePool = true;
/*  437 */             request.setAttribute("disablePool", Boolean.valueOf(disablePool));
/*  438 */           } else if (rs3.getString("COMPONENTNAME").equals("DISKMONITORING")) {
/*  439 */             disableDisk = true;
/*  440 */             request.setAttribute("disableDisk", Boolean.valueOf(disableDisk));
/*      */           }
/*      */         }
/*      */       } catch (Exception ex) {
/*  444 */         ex.printStackTrace();
/*      */       } finally {
/*  446 */         AMConnectionPool.closeStatement(rs3);
/*      */       }
/*      */       
/*  449 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  451 */       String o = request.getParameter("noredirect");
/*  452 */       if ((o != null) && (o.equals("false")))
/*      */       {
/*  454 */         return mapping.findForward("overview");
/*      */       }
/*      */     } catch (Exception e) {
/*  457 */       e.printStackTrace();
/*      */     }
/*  459 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward statusDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  464 */     ResultSet rs = null;
/*      */     try {
/*  466 */       response.setContentType("text/html; charset=UTF-8");
/*  467 */       String resid = request.getParameter("resourceid");
/*      */       
/*  469 */       long time = 0L;
/*      */       
/*  471 */       String query = "select max(collectiontime) as coltime from AM_AS400_CONFIGURATION where resourceid=" + resid;
/*  472 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  473 */       if (rs.next()) {
/*  474 */         time = rs.getLong("coltime");
/*      */       }
/*  476 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  478 */       int ASP_PERCENTAGE = 0;int aspr = 0;int DB_PERCENTAGE = 0;int dbr = 0;int curip = 0;int PROCESSINGUNIT_PERCENTAGE = 0;int prur = 0;int TEMPORARY_ADDRESS_PERCENTAGE = 0;int tmpar = 0;int perar = 0;int PERMANENT_ADDRESS_PERCENTAGE = 0;int CURRENTPROCESSINGCAPACITY = 0;int curpc = 0;int CURRENTINTRACTIVEPERFORMANCEPER = 0;int SHAREDPROCESSINGPOOLPER = 0;int shrpp = 0;int UNCAPPEDCPUCAPACITYPER = 0;int ucpup = 0;
/*      */       
/*  480 */       query = "select * from AM_AS400_STATUS where resourceid=" + resid + " and COLLECTIONTIME=" + time;
/*  481 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  482 */       if (rs.next())
/*      */       {
/*  484 */         request.setAttribute("ASP_TOTAL", rs.getString("ASP_TOTAL"));
/*  485 */         request.setAttribute("CURRENT_UNPROTECTED_USED", rs.getString("CURRENT_UNPROTECTED_USED"));
/*  486 */         request.setAttribute("MAXIMUM_UNPROTECTED", rs.getString("MAXIMUM_UNPROTECTED"));
/*  487 */         request.setAttribute("MAIN_STORAGE", rs.getString("MAIN_STORAGE"));
/*  488 */         request.setAttribute("NUMBER_OF_PROCESSORS", rs.getString("NUMBER_OF_PROCESSORS"));
/*  489 */         request.setAttribute("NUMBER_OF_POOLS", rs.getString("NUMBER_OF_POOLS"));
/*  490 */         request.setAttribute("USERS_SIGNED_ON", rs.getString("USERS_SIGNED_ON"));
/*  491 */         request.setAttribute("TOTAL_JOBS", rs.getString("TOTAL_JOBS"));
/*  492 */         request.setAttribute("NO_OF_ACTIVE_JOBS", rs.getString("NO_OF_ACTIVE_JOBS"));
/*  493 */         request.setAttribute("NO_OF_BATCH_JOBS", rs.getString("NO_OF_BATCH_JOBS"));
/*  494 */         request.setAttribute("JOBS_WAITING_FOR_MSG", rs.getString("JOBS_WAITING_FOR_MSG"));
/*  495 */         request.setAttribute("ACTIVE_THREADS", rs.getString("ACTIVE_THREADS"));
/*  496 */         request.setAttribute("BATCHJOBENDENDWITHPRINTEROUTPUT", rs.getString("BATCHJOBENDENDWITHPRINTEROUTPUT"));
/*  497 */         request.setAttribute("BATCHJOBSENDING", rs.getString("BATCHJOBSENDING"));
/*  498 */         request.setAttribute("BATCHJOBHELDONJOBQUEUE", rs.getString("BATCHJOBHELDONJOBQUEUE"));
/*  499 */         request.setAttribute("BATCHJOBSHELDWHILERUNNING", rs.getString("BATCHJOBSHELDWHILERUNNING"));
/*  500 */         request.setAttribute("BATCHJOBSONHELDJOBQUEUE", rs.getString("BATCHJOBSONHELDJOBQUEUE"));
/*  501 */         request.setAttribute("BATCHJOBSONUNASSIGNEDJOBQUEUE", rs.getString("BATCHJOBSONUNASSIGNEDJOBQUEUE"));
/*  502 */         request.setAttribute("BATCHJOBSWAITINGTORUN", rs.getString("BATCHJOBSWAITINGTORUN"));
/*  503 */         request.setAttribute("NOOFPARTITIONS", rs.getString("NOOFPARTITIONS"));
/*  504 */         request.setAttribute("TOTALAUXILZRYSTORAGE", rs.getString("TOTALAUXILZRYSTORAGE"));
/*  505 */         request.setAttribute("USERSIGNOFFPRINTEROUTPUTWTOPRINT", rs.getString("USERSIGNOFFPRINTEROUTPUTWTOPRINT"));
/*  506 */         request.setAttribute("USERSUSPENDEDBYGROUPJOB", rs.getString("USERSUSPENDEDBYGROUPJOB"));
/*  507 */         request.setAttribute("USERSUSPENDEDBYSYSTEMREQUEST", rs.getString("USERSUSPENDEDBYSYSTEMREQUEST"));
/*  508 */         request.setAttribute("USERSTEMPORARILYSOFF", rs.getString("USERSTEMPORARILYSOFF"));
/*  509 */         request.setAttribute("MAXIMUMJOBSINSYSTEM", rs.getString("MAXIMUMJOBSINSYSTEM"));
/*      */         
/*  511 */         if (rs.getString("ASP_PERCENTAGE") != null)
/*      */         {
/*  513 */           ASP_PERCENTAGE = Integer.parseInt(rs.getString("ASP_PERCENTAGE"));
/*  514 */           aspr = 100 - ASP_PERCENTAGE;
/*      */         }
/*      */         else
/*      */         {
/*  518 */           aspr = 0;
/*      */         }
/*  520 */         request.setAttribute("ASP_PERCENTAGE", Integer.valueOf(ASP_PERCENTAGE));
/*  521 */         request.setAttribute("aspr", Integer.valueOf(aspr));
/*      */         
/*  523 */         if (rs.getString("DB_PERCENTAGE") != null)
/*      */         {
/*  525 */           DB_PERCENTAGE = Integer.parseInt(rs.getString("DB_PERCENTAGE"));
/*  526 */           dbr = 100 - DB_PERCENTAGE;
/*      */         }
/*      */         else
/*      */         {
/*  530 */           dbr = 0;
/*      */         }
/*  532 */         request.setAttribute("DB_PERCENTAGE", Integer.valueOf(DB_PERCENTAGE));
/*  533 */         request.setAttribute("dbr", Integer.valueOf(dbr));
/*      */         
/*  535 */         if (rs.getString("PROCESSINGUNIT_PERCENTAGE") != null)
/*      */         {
/*  537 */           PROCESSINGUNIT_PERCENTAGE = Integer.parseInt(rs.getString("PROCESSINGUNIT_PERCENTAGE"));
/*  538 */           prur = 100 - PROCESSINGUNIT_PERCENTAGE;
/*      */         }
/*      */         else
/*      */         {
/*  542 */           prur = 0;
/*      */         }
/*  544 */         request.setAttribute("PROCESSINGUNIT_PERCENTAGE", Integer.valueOf(PROCESSINGUNIT_PERCENTAGE));
/*  545 */         request.setAttribute("prur", Integer.valueOf(prur));
/*      */         
/*  547 */         if (rs.getString("TEMPORARY_ADDRESS_PERCENTAGE") != null)
/*      */         {
/*  549 */           TEMPORARY_ADDRESS_PERCENTAGE = Integer.parseInt(rs.getString("TEMPORARY_ADDRESS_PERCENTAGE"));
/*  550 */           tmpar = 100 - TEMPORARY_ADDRESS_PERCENTAGE;
/*      */         }
/*      */         else
/*      */         {
/*  554 */           tmpar = 0;
/*      */         }
/*  556 */         request.setAttribute("TEMPORARY_ADDRESS_PERCENTAGE", Integer.valueOf(TEMPORARY_ADDRESS_PERCENTAGE));
/*  557 */         request.setAttribute("tmpar", Integer.valueOf(tmpar));
/*      */         
/*  559 */         if (rs.getString("PERMANENT_ADDRESS_PERCENTAGE") != null)
/*      */         {
/*  561 */           PERMANENT_ADDRESS_PERCENTAGE = Integer.parseInt(rs.getString("PERMANENT_ADDRESS_PERCENTAGE"));
/*  562 */           perar = 100 - PERMANENT_ADDRESS_PERCENTAGE;
/*      */         }
/*      */         else
/*      */         {
/*  566 */           perar = 0;
/*      */         }
/*  568 */         request.setAttribute("PERMANENT_ADDRESS_PERCENTAGE", Integer.valueOf(PERMANENT_ADDRESS_PERCENTAGE));
/*  569 */         request.setAttribute("perar", Integer.valueOf(perar));
/*      */         
/*      */ 
/*  572 */         if (rs.getString("CURRENTPROCESSINGCAPACITY") != null)
/*      */         {
/*  574 */           CURRENTPROCESSINGCAPACITY = Integer.parseInt(rs.getString("CURRENTPROCESSINGCAPACITY"));
/*  575 */           curpc = 100 - CURRENTPROCESSINGCAPACITY;
/*      */         }
/*      */         else
/*      */         {
/*  579 */           curpc = 0;
/*      */         }
/*  581 */         request.setAttribute("CURRENTPROCESSINGCAPACITY", Integer.valueOf(CURRENTPROCESSINGCAPACITY));
/*  582 */         request.setAttribute("curpc", Integer.valueOf(curpc));
/*      */         
/*  584 */         if (rs.getString("CURRENTINTRACTIVEPERFORMANCEPER") != null)
/*      */         {
/*  586 */           CURRENTINTRACTIVEPERFORMANCEPER = Integer.parseInt(rs.getString("CURRENTINTRACTIVEPERFORMANCEPER"));
/*  587 */           curip = 100 - CURRENTINTRACTIVEPERFORMANCEPER;
/*      */         }
/*      */         else
/*      */         {
/*  591 */           curip = 0;
/*      */         }
/*  593 */         request.setAttribute("CURRENTINTRACTIVEPERFORMANCEPER", Integer.valueOf(CURRENTINTRACTIVEPERFORMANCEPER));
/*  594 */         request.setAttribute("curip", Integer.valueOf(curip));
/*      */         
/*      */ 
/*      */ 
/*  598 */         if (rs.getString("SHAREDPROCESSINGPOOLPER") != null)
/*      */         {
/*  600 */           SHAREDPROCESSINGPOOLPER = Integer.parseInt(rs.getString("SHAREDPROCESSINGPOOLPER"));
/*  601 */           shrpp = 100 - SHAREDPROCESSINGPOOLPER;
/*      */         }
/*      */         else
/*      */         {
/*  605 */           shrpp = 0;
/*      */         }
/*  607 */         request.setAttribute("SHAREDPROCESSINGPOOLPER", Integer.valueOf(SHAREDPROCESSINGPOOLPER));
/*  608 */         request.setAttribute("shrpp", Integer.valueOf(shrpp));
/*      */         
/*  610 */         if (rs.getString("UNCAPPEDCPUCAPACITYPER") != null)
/*      */         {
/*  612 */           UNCAPPEDCPUCAPACITYPER = Integer.parseInt(rs.getString("UNCAPPEDCPUCAPACITYPER"));
/*  613 */           ucpup = 100 - UNCAPPEDCPUCAPACITYPER;
/*      */         }
/*      */         else
/*      */         {
/*  617 */           ucpup = 0;
/*      */         }
/*  619 */         request.setAttribute("UNCAPPEDCPUCAPACITYPER", Integer.valueOf(UNCAPPEDCPUCAPACITYPER));
/*  620 */         request.setAttribute("ucpup", Integer.valueOf(ucpup));
/*      */       }
/*      */       else
/*      */       {
/*  624 */         request.setAttribute("ASP_PERCENTAGE", Integer.valueOf(ASP_PERCENTAGE));
/*  625 */         request.setAttribute("DB_PERCENTAGE", Integer.valueOf(DB_PERCENTAGE));
/*  626 */         request.setAttribute("PROCESSINGUNIT_PERCENTAGE", Integer.valueOf(PROCESSINGUNIT_PERCENTAGE));
/*  627 */         request.setAttribute("PERMANENT_ADDRESS_PERCENTAGE", Integer.valueOf(PERMANENT_ADDRESS_PERCENTAGE));
/*  628 */         request.setAttribute("TEMPORARY_ADDRESS_PERCENTAGE", Integer.valueOf(TEMPORARY_ADDRESS_PERCENTAGE));
/*  629 */         request.setAttribute("CURRENTPROCESSINGCAPACITY", Integer.valueOf(CURRENTPROCESSINGCAPACITY));
/*  630 */         request.setAttribute("CURRENTINTRACTIVEPERFORMANCEPER", Integer.valueOf(CURRENTINTRACTIVEPERFORMANCEPER));
/*  631 */         request.setAttribute("SHAREDPROCESSINGPOOLPER", Integer.valueOf(SHAREDPROCESSINGPOOLPER));
/*  632 */         request.setAttribute("UNCAPPEDCPUCAPACITYPER", Integer.valueOf(UNCAPPEDCPUCAPACITYPER));
/*  633 */         request.setAttribute("aspr", Integer.valueOf(aspr));
/*  634 */         request.setAttribute("dbr", Integer.valueOf(dbr));
/*  635 */         request.setAttribute("prur", Integer.valueOf(prur));
/*  636 */         request.setAttribute("tmpar", Integer.valueOf(tmpar));
/*  637 */         request.setAttribute("perar", Integer.valueOf(perar));
/*  638 */         request.setAttribute("curpc", Integer.valueOf(curpc));
/*  639 */         request.setAttribute("curip", Integer.valueOf(curip));
/*  640 */         request.setAttribute("shrpp", Integer.valueOf(shrpp));
/*  641 */         request.setAttribute("ucpup", Integer.valueOf(ucpup));
/*      */       }
/*  643 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  645 */       String query1 = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/*  646 */       rs = AMConnectionPool.executeQueryStmt(query1);
/*  647 */       if (rs.next()) {
/*  648 */         String dispname = rs.getString("DISPLAYNAME");
/*  649 */         ((AMActionForm)form).setDisplayname(dispname);
/*  650 */         request.setAttribute("displayname", dispname);
/*      */       }
/*  652 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*      */ 
/*  656 */       String o = request.getParameter("noredirect");
/*  657 */       if ((o != null) && (o.equals("false")))
/*      */       {
/*  659 */         return mapping.findForward("status");
/*      */       }
/*      */     } catch (Exception e) {
/*  662 */       e.printStackTrace();
/*      */     } finally {
/*  664 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  666 */     return null;
/*      */   }
/*      */   
/*      */   private void checkOSColState(String resid, HttpServletRequest request)
/*      */   {
/*  671 */     String query = "select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resid;
/*  672 */     ResultSet rs = null;
/*      */     try {
/*  674 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  675 */       if (rs.next()) {
/*  676 */         String msg = rs.getString("ERRORMSG");
/*  677 */         if (msg.equals("am.webclient.sap.oscolerror.txt")) {
/*  678 */           ActionMessages messages = new ActionMessages();
/*  679 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.oscolerror.txt")));
/*  680 */           saveMessages(request, messages);
/*  681 */           request.setAttribute("OSCOLState", "false");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  687 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  690 */       if (rs != null) {
/*  691 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward poolDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  698 */     Map data = new HashMap();
/*  699 */     List a = new ArrayList();
/*  700 */     String toremove = "Pool-";
/*  701 */     ResultSet rs = null;ResultSet rs1 = null;ResultSet rs2 = null;
/*      */     
/*  703 */     boolean disable = false;
/*      */     try {
/*  705 */       response.setContentType("text/html; charset=UTF-8");
/*  706 */       String resid = request.getParameter("resourceid");
/*  707 */       Integer resourceid = Integer.valueOf(Integer.parseInt(resid));
/*      */       
/*      */ 
/*  710 */       String query = "select max(collectiontime) as coltime from AM_AS400_CONFIGURATION where RESOURCEID=" + resid;
/*  711 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/*  712 */       Map p; if (rs1.next()) {
/*  713 */         query = "select mo.DISPLAYNAME,bi.* from AM_AS400_POOL bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=bi.RESOURCEID and bi.RESOURCEID=pcm.CHILDID and pcm.PARENTID=" + resid + " and COLLECTIONTIME=" + rs1.getString("coltime");
/*  714 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  715 */         ArrayList buffdata = new ArrayList();
/*  716 */         while (rs.next()) {
/*  717 */           p = new HashMap();
/*  718 */           String pooln = "";
/*  719 */           buffdata.add(rs.getString("RESOURCEID"));
/*  720 */           p.put("POOLRID", rs.getString("RESOURCEID"));
/*      */           
/*  722 */           pooln = rs.getString("DISPLAYNAME");
/*  723 */           pooln = pooln.substring(pooln.indexOf(toremove) + toremove.length());
/*      */           
/*  725 */           p.put("NAME", pooln);
/*  726 */           p.put("POOL_ID", rs.getString("POOL_ID"));
/*  727 */           p.put("SIZE", rs.getString("SIZE"));
/*  728 */           p.put("RESERVED_SIZE", rs.getString("RESERVED_SIZE"));
/*  729 */           p.put("DB_PAGES", rs.getString("DB_PAGES"));
/*  730 */           p.put("DB_FAULTS", rs.getString("DB_FAULTS"));
/*  731 */           p.put("NON_DB_PAGES", rs.getString("NON_DB_PAGES"));
/*  732 */           p.put("NON_DB_FAULTS", rs.getString("NON_DB_FAULTS"));
/*  733 */           a.add(p);
/*  734 */           data.put("pool", a);
/*      */         }
/*      */         
/*  737 */         String query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/*  738 */         rs2 = AMConnectionPool.executeQueryStmt(query2);
/*  739 */         while (rs2.next()) {
/*  740 */           if (rs2.getString("COMPONENTNAME").equals("POOLMONITORING")) {
/*  741 */             disable = true;
/*  742 */             request.setAttribute("disable", Boolean.valueOf(disable));
/*      */           }
/*      */         }
/*  745 */         request.setAttribute("buffdata", buffdata);
/*  746 */         request.setAttribute("data", data);
/*      */       }
/*      */       
/*  749 */       String o = request.getParameter("noredirect");
/*  750 */       if ((o != null) && (o.equals("false")))
/*      */       {
/*  752 */         return mapping.findForward("pool");
/*      */       }
/*      */     } catch (Exception e) {
/*  755 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  758 */       AMConnectionPool.closeStatement(rs);
/*  759 */       AMConnectionPool.closeStatement(rs1);
/*  760 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/*  762 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward jobsDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  766 */     Map data = new HashMap();
/*  767 */     Map jobMonData = new HashMap();
/*  768 */     List a = new ArrayList();
/*  769 */     List a1 = new ArrayList();
/*  770 */     ResultSet rs = null;
/*  771 */     String query = "";
/*  772 */     boolean disable = false;
/*  773 */     int jobCount = 0;
/*  774 */     double jcl = 0.0D;double jcr = 0.0D;double jw = 0.0D;double jclper = 0.0D;double jcrper = 0.0D;double jwper = 0.0D;
/*      */     try
/*      */     {
/*  777 */       response.setContentType("text/html; charset=UTF-8");
/*  778 */       String resid = request.getParameter("resourceid");
/*      */       
/*  780 */       query = "select * from AM_AS400_JOBS where RESOURCEID=" + resid + " order by TYPE";
/*  781 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  782 */       ArrayList buffdata = new ArrayList();
/*  783 */       while (rs.next()) {
/*  784 */         Map p = new HashMap();
/*      */         
/*  786 */         p.put("ID", rs.getString("ID"));
/*  787 */         p.put("JOBNAME", rs.getString("JOBNAME"));
/*  788 */         p.put("USERNAME", rs.getString("USERNAME"));
/*  789 */         p.put("NUMBER", rs.getString("NUMBER"));
/*  790 */         p.put("TYPE", rs.getString("TYPE"));
/*  791 */         p.put("STATUS", rs.getString("STATUS"));
/*  792 */         p.put("POOL", rs.getString("POOL"));
/*  793 */         p.put("FUNCTION", rs.getString("FUNCTION_COLUMN"));
/*  794 */         p.put("PRIORITY", rs.getString("PRIORITY"));
/*  795 */         p.put("THREADS", rs.getString("THREADS"));
/*      */         try {
/*  797 */           String subName = rs.getString("SUBSYSTEM");
/*  798 */           String queue = rs.getString("QUEUE");
/*  799 */           queue = queue.contains("-") ? queue.substring(0, queue.lastIndexOf("-")) : queue;
/*  800 */           subName = subName.contains("-") ? subName.substring(0, subName.lastIndexOf("-")) : subName;
/*  801 */           p.put("SUBSYSTEM", subName);
/*  802 */           p.put("QUEUE", queue);
/*      */         } catch (Exception e) {
/*  804 */           p.put("SUBSYSTEM", "-");
/*      */         }
/*  806 */         String cpuUsed = "-";String uptime = "-";
/*  807 */         cpuUsed = rs.getString("CPU_USED") != null ? rs.getString("CPU_USED") : "-";
/*  808 */         uptime = rs.getString("UPTIME") != null ? rs.getString("UPTIME") : "-";
/*  809 */         p.put("CPU_USED", cpuUsed);
/*  810 */         p.put("UPTIME", uptime);
/*  811 */         a.add(p);
/*  812 */         data.put("jobs", a);
/*  813 */         jobCount++;
/*      */       }
/*  815 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  817 */       request.setAttribute("data", data);
/*  818 */       query = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/*  819 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  820 */       while (rs.next()) {
/*  821 */         if (rs.getString("COMPONENTNAME").equals("JOBMONITORING")) {
/*  822 */           disable = true;
/*  823 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/*  826 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  828 */       if (!disable) {
/*  829 */         query = "select JOB_CLEAR,JOB_CRITICAL,JOB_WARNING from AM_AS400_ALERTSTATUS where RESOURCEID=" + resid;
/*  830 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  831 */         if (rs.next()) {
/*      */           try {
/*  833 */             jcl = Integer.parseInt(rs.getString("JOB_CLEAR"));
/*  834 */             jcr = Integer.parseInt(rs.getString("JOB_CRITICAL"));
/*  835 */             jw = Integer.parseInt(rs.getString("JOB_WARNING"));
/*  836 */             if (jobCount != 0) {
/*  837 */               jclper = jcl / jobCount * 100.0D;
/*  838 */               jcrper = jcr / jobCount * 100.0D;
/*  839 */               jwper = jw / jobCount * 100.0D;
/*      */             }
/*      */           } catch (Exception nfe) {
/*  842 */             jclper = 0.0D;
/*  843 */             jcrper = 0.0D;
/*  844 */             jwper = 0.0D;
/*      */           }
/*  846 */           DecimalFormat dec = new DecimalFormat("#.##");
/*  847 */           request.setAttribute("JOB_CLEAR", rs.getString("JOB_CLEAR"));
/*  848 */           request.setAttribute("JOB_CRITICAL", rs.getString("JOB_CRITICAL"));
/*  849 */           request.setAttribute("JOB_WARNING", rs.getString("JOB_WARNING"));
/*  850 */           request.setAttribute("JOB_CLEAR_PER", dec.format(jclper));
/*  851 */           request.setAttribute("JOB_CRITICAL_PER", dec.format(jcrper));
/*  852 */           request.setAttribute("JOB_WARNING_PER", dec.format(jwper));
/*      */         }
/*  854 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       try
/*      */       {
/*  858 */         query = "select ac.collectiontime as coltime,mo.DISPLAYNAME from AM_AS400_CONFIGURATION ac,AM_ManagedObject mo where mo.RESOURCEID=ac.RESOURCEID  and ac.RESOURCEID = " + resid;
/*  859 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  860 */         String maxcoltime = "0";String monname = "";
/*  861 */         DataCollectionComponent dcc = new DataCollectionComponent();
/*  862 */         if (rs.next()) {
/*  863 */           maxcoltime = rs.getString("coltime");
/*  864 */           monname = rs.getString("DISPLAYNAME");
/*      */         }
/*  866 */         request.setAttribute("monname", monname);
/*  867 */         AMConnectionPool.closeStatement(rs);
/*  868 */         query = "select mo.DISPLAYNAME,mo.RESOURCENAME,mo.RESOURCEID as JOBRID,bi.* from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_AS400_MONITOREDJOBS bi on mo.RESOURCEID=bi.RESOURCEID and bi.COLLECTIONTIME =" + maxcoltime + " where pcm.PARENTID=" + resid + " and mo.TYPE='AS400_MonJob'";
/*  869 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  870 */         while (rs.next()) {
/*  871 */           Map p = new HashMap();
/*  872 */           String jobrid = "";String str1 = ":JOB-";String resname = "";String dispname = "";String queue = "";String actJobs = "";String jobq = "";String outq = "";String cpuUsed = "";String threads = "";String storage = "";
/*  873 */           jobrid = rs.getString("JOBRID");
/*  874 */           buffdata.add(jobrid);
/*  875 */           dispname = rs.getString("DISPLAYNAME");
/*  876 */           resname = rs.getString("RESOURCENAME");
/*  877 */           String[] monjobids = { "", "", "", "" };
/*  878 */           String[] identityStyle = { "", "", "", "" };
/*  879 */           String[] monjobidentifiers = { "JOBNAME", "USERNAME", "JOBTYPE", "SUBSYSTEM" };
/*      */           try {
/*  881 */             if (dispname.indexOf(str1) != -1) {
/*  882 */               dispname = dispname.substring(dispname.indexOf(str1) + str1.length()) + "";
/*      */             }
/*  884 */             String str2 = resname.substring(resname.indexOf(str1) + str1.length()) + "";
/*  885 */             monjobids = str2.split("-", 4);
/*  886 */             for (int i = 0; i < 4; i++) {
/*  887 */               if (!dcc.getStatusforComponent(jobrid, monjobidentifiers[i])) {
/*  888 */                 identityStyle[i] = "text-line-through disabledtext";
/*      */               }
/*      */             }
/*      */           } catch (Exception e) {
/*  892 */             AMLog.debug("AS400 Action:  Problem while getting job name by substring for job : " + rs.getString("DISPLAYNAME") + " Resource id : " + resid + " due to error : " + e.getMessage());
/*      */           }
/*  894 */           queue = rs.getString("QUEUE") == null ? "-" : rs.getString("QUEUE");
/*  895 */           queue = queue.contains("-") ? queue.substring(0, queue.lastIndexOf("-")) : queue;
/*  896 */           actJobs = rs.getString("ACTIVE_JOBS") == null ? "-" : rs.getString("ACTIVE_JOBS");
/*  897 */           jobq = rs.getString("JOBQ_JOBS") == null ? "-" : rs.getString("JOBQ_JOBS");
/*  898 */           outq = rs.getString("OUTQ_JOBS") == null ? "-" : rs.getString("OUTQ_JOBS");
/*  899 */           cpuUsed = rs.getString("CPU_USED") == null ? "-" : rs.getString("CPU_USED");
/*  900 */           threads = rs.getString("THREADS") == null ? "-" : rs.getString("THREADS");
/*  901 */           storage = rs.getString("TEMP_STORAGE") == null ? "-" : rs.getString("TEMP_STORAGE");
/*      */           
/*  903 */           p.put("DISPLAYNAME", dispname);
/*  904 */           p.put("JOBRID", jobrid);
/*  905 */           p.put("ID", rs.getString("ID"));
/*  906 */           p.put("JOBNAME", monjobids[0]);
/*  907 */           p.put("USERNAME", monjobids[1]);
/*  908 */           p.put("SUBSYSTEM", monjobids[2]);
/*  909 */           p.put("TYPE", monjobids[3]);
/*  910 */           p.put("JNSTYLE", identityStyle[0]);
/*  911 */           p.put("UNSTYLE", identityStyle[1]);
/*  912 */           p.put("JTSTYLE", identityStyle[2]);
/*  913 */           p.put("JSSTYLE", identityStyle[3]);
/*  914 */           p.put("QUEUE", queue);
/*  915 */           p.put("ACTIVE_JOBS", actJobs);
/*  916 */           p.put("JOB_QUEUE", jobq);
/*  917 */           p.put("OUT_QUEUE", outq);
/*  918 */           p.put("CPU_USED", cpuUsed);
/*  919 */           p.put("THREADS", threads);
/*  920 */           p.put("TEMP_STORAGE", storage);
/*      */           
/*  922 */           a1.add(p);
/*  923 */           jobMonData.put("jobs", a1);
/*      */         }
/*  925 */         AMConnectionPool.closeStatement(rs);
/*  926 */         request.setAttribute("jobsToMon", jobMonData);
/*  927 */         request.setAttribute("buffdata", buffdata);
/*      */       } catch (Exception e) {
/*  929 */         e.printStackTrace();
/*      */       }
/*      */       String hostname;
/*  932 */       try { String serverUrl = GenURLForHTML.getServerUrl();
/*  933 */         hostname = Constants.getResName(resid);
/*  934 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname;
/*  935 */         request.setAttribute("Debug_Info_Job", serverUrl + "/MatchedJobs.html");
/*  936 */         request.setAttribute("Debug_Info_Job_Sum", serverUrl + "/JobSummary.html");
/*      */       } catch (Exception e) {
/*  938 */         e.printStackTrace();
/*      */       }
/*      */       
/*  941 */       String o = request.getParameter("noredirect");
/*  942 */       if ((o != null) && (o.equals("false"))) {
/*  943 */         return mapping.findForward("jobs");
/*      */       }
/*      */     } catch (Exception e) {
/*  946 */       e.printStackTrace();
/*      */     } finally {
/*  948 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  950 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward messagesDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  956 */     Map data = new HashMap();
/*  957 */     List a = new ArrayList();
/*  958 */     ResultSet rs = null;ResultSet rs2 = null;
/*  959 */     String query = "";String query2 = "";
/*  960 */     boolean disable = false;
/*      */     try {
/*  962 */       response.setContentType("text/html; charset=UTF-8");
/*  963 */       String resid = request.getParameter("resourceid");
/*  964 */       String status = "";
/*  965 */       status = request.getParameter("status");
/*  966 */       if (status == null) {
/*  967 */         status = "";
/*      */       }
/*      */       
/*  970 */       if (status.equals("allmsg")) {
/*  971 */         query = "select * from AM_AS400_MESSAGES where RESOURCEID in(" + resid + ") order by TYPE";
/*      */       }
/*      */       else {
/*  974 */         query = "select * from AM_AS400_MESSAGES where RESOURCEID=" + resid + " and REPLYSTATUS in('W') order by TYPE";
/*      */       }
/*  976 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  977 */       while (rs.next()) {
/*  978 */         Map p = new HashMap();
/*  979 */         p.put("ID", rs.getString("ID"));
/*  980 */         p.put("RESOURCEID", rs.getString("RESOURCEID"));
/*  981 */         p.put("MSG_ID", rs.getString("MSG_ID"));
/*  982 */         p.put("SEVERITY", rs.getString("SEVERITY"));
/*  983 */         p.put("TYPE", rs.getString("TYPE"));
/*  984 */         p.put("MESSAGE", rs.getString("MESSAGE"));
/*  985 */         p.put("DATE", rs.getString("DATE"));
/*  986 */         p.put("ANSWERED", rs.getString("ANSWERED"));
/*  987 */         p.put("MSG_ID_STATUS", rs.getString("MSG_ID_STATUS"));
/*  988 */         p.put("SEVSTATUS", rs.getString("SEVSTATUS"));
/*  989 */         p.put("MSGSTATUS", rs.getString("MSGSTATUS"));
/*  990 */         a.add(p);
/*  991 */         data.put("messages", a);
/*      */       }
/*      */       
/*  994 */       query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/*  995 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/*  996 */       while (rs2.next()) {
/*  997 */         if (rs2.getString("COMPONENTNAME").equals("MESSAGEMONITORING")) {
/*  998 */           disable = true;
/*  999 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/*      */       
/* 1003 */       request.setAttribute("data", data);
/*      */       try {
/* 1005 */         String serverUrl = GenURLForHTML.getServerUrl();
/* 1006 */         String hostname = Constants.getResName(resid);
/* 1007 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname + "/MatchedMessages.html";
/* 1008 */         request.setAttribute("Debug_Info_Msg", serverUrl);
/*      */       } catch (Exception e) {
/* 1010 */         e.printStackTrace();
/*      */       }
/*      */       long maxColTime;
/*      */       try {
/* 1014 */         String[] messageQue = Constants.getMonitorMessage(resid, "as400_messages");
/* 1015 */         maxColTime = getMaxCollectionTime(Integer.parseInt(resid));
/* 1016 */         long msgUpdatedTime = Long.parseLong(messageQue[2]);
/* 1017 */         if (msgUpdatedTime >= maxColTime) {
/* 1018 */           request.setAttribute("msgType", messageQue[0]);
/* 1019 */           request.setAttribute("monMsg", messageQue[1]);
/*      */         } else {
/* 1021 */           request.setAttribute("msgType", "");
/* 1022 */           request.setAttribute("monMsg", "");
/*      */         }
/*      */       } catch (Exception e) {
/* 1025 */         e.printStackTrace();
/*      */       }
/* 1027 */       String o = request.getParameter("noredirect");
/* 1028 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 1030 */         return mapping.findForward("messages");
/*      */       }
/*      */     } catch (Exception e) {
/* 1033 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1036 */       AMConnectionPool.closeStatement(rs);
/* 1037 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 1039 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward spoolDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1044 */     Map data = new HashMap();
/* 1045 */     List a = new ArrayList();
/* 1046 */     ResultSet rs = null;ResultSet rs2 = null;
/*      */     
/* 1048 */     boolean disable = false;
/* 1049 */     int spoolCount = 0;
/* 1050 */     double spcl = 0.0D;double spcr = 0.0D;double spw = 0.0D;double spclper = 0.0D;double spcrper = 0.0D;double spwper = 0.0D;
/*      */     try {
/* 1052 */       response.setContentType("text/html; charset=UTF-8");
/* 1053 */       String resid = request.getParameter("resourceid");
/*      */       
/* 1055 */       String query = "select * from AM_AS400_SPOOL where RESOURCEID=" + resid + "";
/* 1056 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1057 */       while (rs.next()) {
/* 1058 */         Map p = new HashMap();
/* 1059 */         p.put("ID", rs.getString("ID"));
/* 1060 */         p.put("NAME", rs.getString("NAME"));
/* 1061 */         p.put("NUMBER", rs.getString("NUMBER"));
/* 1062 */         p.put("JOB_NAME", rs.getString("JOB_NAME"));
/* 1063 */         p.put("JOB_NUMBER", rs.getString("JOB_NUMBER"));
/* 1064 */         p.put("JOB_OWNER", rs.getString("JOB_OWNER"));
/* 1065 */         p.put("STATUS", rs.getString("STATUS"));
/* 1066 */         p.put("TOTAL_PAGES", rs.getString("TOTAL_PAGES"));
/* 1067 */         p.put("PRINTER_NAME", rs.getString("PRINTER_NAME"));
/* 1068 */         a.add(p);
/* 1069 */         data.put("spool", a);
/* 1070 */         spoolCount++;
/*      */       }
/*      */       
/* 1073 */       String query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 1074 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 1075 */       while (rs2.next()) {
/* 1076 */         if (rs2.getString("COMPONENTNAME").equals("SPOOLMONITORING")) {
/* 1077 */           disable = true;
/* 1078 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/* 1081 */       request.setAttribute("data", data);
/*      */       String hostname;
/* 1083 */       try { String serverUrl = GenURLForHTML.getServerUrl();
/* 1084 */         hostname = Constants.getResName(resid);
/* 1085 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname;
/* 1086 */         request.setAttribute("Debug_Info_Spool", serverUrl + "/MatchedSpooledFiles.html");
/* 1087 */         request.setAttribute("Debug_Info_Spool_Sum", serverUrl + "/SpooledFilesSummary.html");
/*      */       } catch (Exception e) {
/* 1089 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 1093 */       if (!disable) {
/* 1094 */         query = "select SPOOL_CLEAR,SPOOL_CRITICAL,SPOOL_WARNING from AM_AS400_ALERTSTATUS where RESOURCEID=" + resid;
/* 1095 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1096 */         if (rs.next()) {
/*      */           try {
/* 1098 */             spcl = Integer.parseInt(rs.getString("SPOOL_CLEAR"));
/* 1099 */             spcr = Integer.parseInt(rs.getString("SPOOL_CRITICAL"));
/* 1100 */             spw = Integer.parseInt(rs.getString("SPOOL_WARNING"));
/* 1101 */             if (spoolCount != 0) {
/* 1102 */               spclper = spcl / spoolCount * 100.0D;
/* 1103 */               spcrper = spcr / spoolCount * 100.0D;
/* 1104 */               spwper = spw / spoolCount * 100.0D;
/*      */             }
/*      */           }
/*      */           catch (Exception nfe) {
/* 1108 */             spclper = 0.0D;
/* 1109 */             spcrper = 0.0D;
/* 1110 */             spwper = 0.0D;
/*      */           }
/* 1112 */           DecimalFormat dec = new DecimalFormat("#.##");
/* 1113 */           request.setAttribute("SPOOL_CLEAR", rs.getString("SPOOL_CLEAR"));
/* 1114 */           request.setAttribute("SPOOL_CRITICAL", rs.getString("SPOOL_CRITICAL"));
/* 1115 */           request.setAttribute("SPOOL_WARNING", rs.getString("SPOOL_WARNING"));
/* 1116 */           request.setAttribute("SPOOL_CLEAR_PER", dec.format(spclper));
/* 1117 */           request.setAttribute("SPOOL_CRITICAL_PER", dec.format(spcrper));
/* 1118 */           request.setAttribute("SPOOL_WARNING_PER", dec.format(spwper));
/*      */         }
/*      */       }
/* 1121 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1123 */       String o = request.getParameter("noredirect");
/* 1124 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 1126 */         return mapping.findForward("spool");
/*      */       }
/*      */     } catch (Exception e) {
/* 1129 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1132 */       AMConnectionPool.closeStatement(rs);
/* 1133 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 1135 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward printerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1139 */     Map data = new HashMap();
/* 1140 */     List a = new ArrayList();
/* 1141 */     ResultSet rs = null;ResultSet rs2 = null;
/*      */     
/* 1143 */     boolean disable = false;
/*      */     try {
/* 1145 */       response.setContentType("text/html; charset=UTF-8");
/* 1146 */       String resid = request.getParameter("resourceid");
/*      */       
/*      */ 
/* 1149 */       String query = "select * from AM_AS400_PRINTER where RESOURCEID=" + resid + "";
/* 1150 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1151 */       while (rs.next()) {
/* 1152 */         Map p = new HashMap();
/* 1153 */         p.put("DEVICE_NAME", rs.getString("DEVICE_NAME"));
/* 1154 */         p.put("DEVICE_CLASS", rs.getString("DEVICE_CLASS"));
/* 1155 */         p.put("DEVICE_TYPE", rs.getString("DEVICE_TYPE"));
/* 1156 */         p.put("STATUS", rs.getString("STATUS"));
/* 1157 */         p.put("JOB_NAME", rs.getString("JOB_NAME"));
/* 1158 */         p.put("JOB_NUMBER", rs.getString("JOB_NUMBER"));
/* 1159 */         p.put("JOB_STATUS", rs.getString("JOB_OWNER"));
/* 1160 */         p.put("STARTED_BY", rs.getString("STARTED_BY"));
/* 1161 */         p.put("TOTAL_PAGES", rs.getString("TOTAL_PAGES"));
/* 1162 */         p.put("SPOOLED_FILE_NAME", rs.getString("SPOOLED_FILE_NAME"));
/* 1163 */         p.put("SPOOLED_FILE_NUMBER", rs.getString("SPOOLED_FILE_NUMBER"));
/* 1164 */         p.put("SPOOLED_FILE_SIZE", rs.getString("SPOOLED_FILE_SIZE"));
/* 1165 */         p.put("OUTPUT_QUEUE_NAME", rs.getString("OUTPUT_QUEUE_NAME"));
/* 1166 */         p.put("OUTPUT_QUEUE_STATUS", rs.getString("OUTPUT_QUEUE_STATUS"));
/* 1167 */         a.add(p);
/* 1168 */         data.put("printer", a);
/*      */       }
/*      */       
/* 1171 */       String query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 1172 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 1173 */       while (rs2.next()) {
/* 1174 */         if (rs2.getString("COMPONENTNAME").equals("PRINTERMONITORING")) {
/* 1175 */           disable = true;
/* 1176 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/* 1179 */       request.setAttribute("data", data);
/*      */       String hostname;
/* 1181 */       try { String serverUrl = GenURLForHTML.getServerUrl();
/* 1182 */         hostname = Constants.getResName(resid);
/* 1183 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname + "/MatchedPrinters.html";
/* 1184 */         request.setAttribute("Debug_Info_Printer", serverUrl);
/*      */       } catch (Exception e) {
/* 1186 */         e.printStackTrace();
/*      */       }
/*      */       
/* 1189 */       String o = request.getParameter("noredirect");
/* 1190 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 1192 */         return mapping.findForward("printer");
/*      */       }
/*      */     } catch (Exception e) {
/* 1195 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1198 */       AMConnectionPool.closeStatement(rs);
/* 1199 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 1201 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward diskDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1205 */     Map data = new HashMap();
/* 1206 */     List a = new ArrayList();
/* 1207 */     String toremove = "DiskUtilization-";
/* 1208 */     ResultSet rs = null;ResultSet rs1 = null;ResultSet rs2 = null;
/*      */     
/* 1210 */     boolean disable = false;
/*      */     try
/*      */     {
/* 1213 */       response.setContentType("text/html; charset=UTF-8");
/* 1214 */       String resid = request.getParameter("resourceid");
/*      */       
/* 1216 */       String query = "select max(collectiontime) as coltime from AM_AS400_CONFIGURATION  where RESOURCEID=" + resid;
/* 1217 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 1218 */       if (rs1.next()) {
/* 1219 */         query = "select mo.DISPLAYNAME,bi.* from AM_AS400_DISK bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=bi.RESOURCEID and bi.RESOURCEID=pcm.CHILDID and pcm.PARENTID=" + resid + " and COLLECTIONTIME=" + rs1.getString("coltime") + " order by DRIVE_PER desc";
/* 1220 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1221 */         ArrayList buffdata = new ArrayList();
/* 1222 */         while (rs.next()) {
/* 1223 */           String dname = "";
/* 1224 */           Map p = new HashMap();
/* 1225 */           buffdata.add(rs.getString("RESOURCEID"));
/* 1226 */           p.put("DISKRID", rs.getString("RESOURCEID"));
/* 1227 */           dname = rs.getString("DISPLAYNAME");
/* 1228 */           dname = dname.substring(dname.indexOf(toremove) + toremove.length());
/*      */           
/* 1230 */           p.put("DISK_ARM_NUMBER", dname);
/* 1231 */           p.put("DISK_DRIVE_TYPE", rs.getString("DISK_DRIVE_TYPE"));
/* 1232 */           p.put("BLOCKS_READ", rs.getString("BLOCKS_READ"));
/* 1233 */           p.put("BLOCKS_WRITE", rs.getString("BLOCKS_WRITE"));
/* 1234 */           p.put("DRIVE_CAPACITY", rs.getString("DRIVE_CAPACITY"));
/* 1235 */           p.put("DRIVE_AVAILABLE_SAPCE", rs.getString("DRIVE_AVAILABLE_SAPCE"));
/* 1236 */           p.put("ASP_NUMBER", rs.getString("ASP_NUMBER"));
/* 1237 */           p.put("UNIT_STATUS", rs.getString("UNIT_STATUS"));
/* 1238 */           p.put("DISK_WAIT_TIME", rs.getString("DISK_WAIT_TIME"));
/* 1239 */           p.put("ARM_UTIL_PER", rs.getString("ARM_UTIL_PER"));
/* 1240 */           a.add(p);
/* 1241 */           data.put("disk", a);
/*      */         }
/*      */         
/* 1244 */         String query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 1245 */         rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 1246 */         while (rs2.next()) {
/* 1247 */           if (rs2.getString("COMPONENTNAME").equals("DISKMONITORING")) {
/* 1248 */             disable = true;
/* 1249 */             request.setAttribute("disable", Boolean.valueOf(disable));
/*      */           }
/*      */         }
/* 1252 */         request.setAttribute("buffdata", buffdata);
/* 1253 */         request.setAttribute("data", data);
/*      */       }
/*      */       long maxColTime;
/* 1256 */       try { String[] diskMessage = Constants.getMonitorMessage(resid, "as400_disk");
/* 1257 */         maxColTime = getMaxCollectionTime(Integer.parseInt(resid));
/* 1258 */         long msgUpdatedTime = Long.parseLong(diskMessage[2]);
/* 1259 */         if (msgUpdatedTime >= maxColTime) {
/* 1260 */           request.setAttribute("msgType", diskMessage[0]);
/* 1261 */           request.setAttribute("monMsg", diskMessage[1]);
/*      */         } else {
/* 1263 */           request.setAttribute("msgType", "");
/* 1264 */           request.setAttribute("monMsg", "");
/*      */         }
/*      */       } catch (Exception e) {
/* 1267 */         e.printStackTrace();
/*      */       }
/* 1269 */       String o = request.getParameter("noredirect");
/* 1270 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 1272 */         return mapping.findForward("disk");
/*      */       }
/*      */     } catch (Exception e) {
/* 1275 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1278 */       AMConnectionPool.closeStatement(rs);
/* 1279 */       AMConnectionPool.closeStatement(rs1);
/* 1280 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 1282 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward problemDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1286 */     Map data = new HashMap();
/* 1287 */     List a = new ArrayList();
/* 1288 */     ResultSet rs = null;ResultSet rs2 = null;
/*      */     
/* 1290 */     boolean disable = false;
/*      */     try {
/* 1292 */       response.setContentType("text/html; charset=UTF-8");
/* 1293 */       String resid = request.getParameter("resourceid");
/*      */       
/* 1295 */       String query = "select * from AM_AS400_PROBLEMS where RESOURCEID=" + resid + "";
/* 1296 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1297 */       while (rs.next()) {
/* 1298 */         Map p = new HashMap();
/* 1299 */         p.put("PROBLEM_ID", rs.getString("PROBLEM_ID"));
/* 1300 */         p.put("PROBLEM_SEVERITY", rs.getString("PROBLEM_SEVERITY"));
/* 1301 */         p.put("PROBLEM_TYPE", rs.getString("PROBLEM_TYPE"));
/* 1302 */         p.put("PROBLEM_STATUS", rs.getString("PROBLEM_STATUS"));
/* 1303 */         p.put("DATE_OPENED", rs.getString("DATE_OPENED"));
/* 1304 */         p.put("TIME_OPENED", rs.getString("TIME_OPENED"));
/* 1305 */         p.put("USER_PREPARED", rs.getString("USER_PREPARED"));
/*      */         
/* 1307 */         a.add(p);
/* 1308 */         data.put("problem", a);
/*      */       }
/*      */       
/* 1311 */       String query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 1312 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 1313 */       while (rs2.next()) {
/* 1314 */         if (rs2.getString("COMPONENTNAME").equals("PROBLEMMONITORING")) {
/* 1315 */           disable = true;
/* 1316 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/* 1319 */       request.setAttribute("data", data);
/*      */       try {
/* 1321 */         String serverUrl = GenURLForHTML.getServerUrl();
/* 1322 */         String hostname = Constants.getResName(resid);
/* 1323 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname + "/MatchedProblems.html";
/* 1324 */         request.setAttribute("Debug_Info_Problem", serverUrl);
/*      */       } catch (Exception e) {
/* 1326 */         e.printStackTrace();
/*      */       }
/*      */       long maxColTime;
/*      */       try {
/* 1330 */         String[] prbMessage = Constants.getMonitorMessage(resid, "as400_problem");
/* 1331 */         maxColTime = getMaxCollectionTime(Integer.parseInt(resid));
/* 1332 */         long msgUpdatedTime = Long.parseLong(prbMessage[2]);
/* 1333 */         if (msgUpdatedTime >= maxColTime) {
/* 1334 */           request.setAttribute("msgType", prbMessage[0]);
/* 1335 */           request.setAttribute("monMsg", prbMessage[1]);
/*      */         } else {
/* 1337 */           request.setAttribute("msgType", "");
/* 1338 */           request.setAttribute("monMsg", "");
/*      */         }
/*      */       } catch (Exception e) {
/* 1341 */         e.printStackTrace();
/*      */       }
/* 1343 */       String o = request.getParameter("noredirect");
/* 1344 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 1346 */         return mapping.findForward("problem");
/*      */       }
/*      */     } catch (Exception e) {
/* 1349 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1352 */       AMConnectionPool.closeStatement(rs);
/* 1353 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 1355 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward subsystemDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1359 */     Map data = new HashMap();
/* 1360 */     Map subsystemMonData = new HashMap();
/* 1361 */     List a = new ArrayList();
/* 1362 */     List a1 = new ArrayList();
/* 1363 */     String query1 = null;String query3 = null;
/* 1364 */     ResultSet rs = null;
/*      */     
/* 1366 */     boolean disable = false;
/* 1367 */     int subCount = 0;
/* 1368 */     double scl = 0.0D;double scr = 0.0D;double sclper = 0.0D;double scrper = 0.0D;
/*      */     try {
/* 1370 */       response.setContentType("text/html; charset=UTF-8");
/* 1371 */       String resid = request.getParameter("resourceid");
/* 1372 */       ArrayList buffdata = new ArrayList();
/*      */       
/* 1374 */       String query = "select * from AM_AS400_SUBSYSTEM where RESOURCEID=" + resid + "";
/* 1375 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1376 */       while (rs.next()) {
/* 1377 */         Map p = new HashMap();
/* 1378 */         p.put("ID", rs.getString("ID"));
/* 1379 */         p.put("NAME", rs.getString("NAME"));
/* 1380 */         p.put("LIBRARY", rs.getString("LIBRARY"));
/* 1381 */         p.put("ACTIVE_JOBS", rs.getString("ACTIVE_JOBS"));
/* 1382 */         p.put("TOTAL_STORAGE", rs.getString("TOTAL_STORAGE"));
/* 1383 */         p.put("STATUS", rs.getString("STATUS"));
/* 1384 */         a.add(p);
/* 1385 */         data.put("subsystem", a);
/* 1386 */         subCount++;
/*      */       }
/* 1388 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1390 */       query3 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 1391 */       rs = AMConnectionPool.executeQueryStmt(query3);
/* 1392 */       while (rs.next()) {
/* 1393 */         if (rs.getString("COMPONENTNAME").equals("SUBSYSTEMMONITORING")) {
/* 1394 */           disable = true;
/* 1395 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/* 1398 */       AMConnectionPool.closeStatement(rs);
/* 1399 */       request.setAttribute("data", data);
/*      */       
/* 1401 */       if (!disable) {
/* 1402 */         String query2 = "select SUBSYSTEM_CLEAR,SUBSYSTEM_CRITICAL from AM_AS400_ALERTSTATUS where RESOURCEID=" + resid;
/* 1403 */         rs = AMConnectionPool.executeQueryStmt(query2);
/* 1404 */         if (rs.next()) {
/*      */           try {
/* 1406 */             scl = Integer.parseInt(rs.getString("SUBSYSTEM_CLEAR"));
/* 1407 */             scr = Integer.parseInt(rs.getString("SUBSYSTEM_CRITICAL"));
/* 1408 */             if (subCount != 0) {
/* 1409 */               sclper = scl / subCount * 100.0D;
/* 1410 */               scrper = scr / subCount * 100.0D;
/*      */             }
/*      */           } catch (Exception nfe) {
/* 1413 */             sclper = 0.0D;
/* 1414 */             scrper = 0.0D;
/*      */           }
/* 1416 */           DecimalFormat dec = new DecimalFormat("#.##");
/* 1417 */           request.setAttribute("SUBSYSTEM_CLEAR", rs.getString("SUBSYSTEM_CLEAR"));
/* 1418 */           request.setAttribute("SUBSYSTEM_CRITICAL", rs.getString("SUBSYSTEM_CRITICAL"));
/* 1419 */           request.setAttribute("SUBSYSTEM_CLEAR_PER", dec.format(sclper));
/* 1420 */           request.setAttribute("SUBSYSTEM_CRITICAL_PER", dec.format(scrper));
/*      */         }
/* 1422 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       try
/*      */       {
/* 1426 */         query1 = "select mo.RESOURCENAME,mo.DISPLAYNAME,bi.* from AM_AS400_SUBSYSTEMMONITORING bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=bi.RESOURCEID and bi.RESOURCEID=pcm.CHILDID and pcm.PARENTID=" + resid;
/* 1427 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 1428 */         while (rs.next()) {
/* 1429 */           Map p = new HashMap();
/* 1430 */           buffdata.add(rs.getString("RESOURCEID"));
/* 1431 */           String dispName = "";String lib = "";
/* 1432 */           String resName = rs.getString("RESOURCENAME");
/* 1433 */           dispName = rs.getString("DISPLAYNAME");
/* 1434 */           lib = rs.getString("LIBRARY");
/* 1435 */           String[] monqueueids = { "", "" };
/*      */           try {
/* 1437 */             String str1 = ":SUBSYSTEM-";String str2 = "";
/* 1438 */             str2 = resName.substring(resName.indexOf(str1) + str1.length()) + "";
/* 1439 */             monqueueids = str2.split("-", 2);
/* 1440 */             dispName = monqueueids[0];
/* 1441 */             lib = monqueueids[1];
/*      */           } catch (Exception e) {
/* 1443 */             AMLog.debug("AS400 Action :  Problem while getting subsystem name by splitting the res name for subsystem : " + resName + " Resource id : " + resid + " due to error : " + e.getMessage());
/*      */           }
/* 1445 */           p.put("DISPLAYNAME", dispName);
/* 1446 */           p.put("SUBSYSTEMRID", rs.getString("RESOURCEID"));
/* 1447 */           p.put("ID", rs.getString("ID"));
/* 1448 */           p.put("LIBRARY", lib);
/* 1449 */           p.put("ACTIVE_JOBS", rs.getString("ACTIVE_JOBS"));
/* 1450 */           p.put("TOTAL_STORAGE", rs.getString("TOTAL_STORAGE"));
/* 1451 */           p.put("STATUS", rs.getString("STATUS"));
/* 1452 */           a1.add(p);
/* 1453 */           subsystemMonData.put("subsystem", a1);
/*      */         }
/* 1455 */         request.setAttribute("subsystemToMon", subsystemMonData);
/* 1456 */         request.setAttribute("buffdata", buffdata);
/*      */       }
/*      */       catch (Exception e) {
/* 1459 */         e.printStackTrace();
/*      */       }
/*      */       String hostname;
/* 1462 */       try { String serverUrl = GenURLForHTML.getServerUrl();
/* 1463 */         hostname = Constants.getResName(resid);
/* 1464 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname;
/* 1465 */         request.setAttribute("Debug_Info_Sub", serverUrl + "/MatchedSubsystems.html");
/* 1466 */         request.setAttribute("Debug_Info_Sub_Sum", serverUrl + "/SubsystemsSummary.html");
/*      */       } catch (Exception e) {
/* 1468 */         e.printStackTrace();
/*      */       }
/*      */       
/* 1471 */       String o = request.getParameter("noredirect");
/* 1472 */       if ((o != null) && (o.equals("false"))) {
/* 1473 */         return mapping.findForward("subsystem");
/*      */       }
/*      */     } catch (Exception e) {
/* 1476 */       e.printStackTrace();
/*      */     } finally {
/* 1478 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1480 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward adminDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/* 1486 */       response.setContentType("text/html; charset=UTF-8");
/* 1487 */       String resid = request.getParameter("resourceid");
/* 1488 */       String getmonjobs = request.getParameter("getmonjobs");
/* 1489 */       String getDiskLibraryName = request.getParameter("getDiskLibraryName");
/* 1490 */       ResultSet rs = null;
/* 1491 */       DataCollectionComponent dcc = new DataCollectionComponent();
/*      */       
/* 1493 */       if ((getmonjobs != null) && (getmonjobs.equals("true"))) {
/*      */         try {
/* 1495 */           int c = 0;
/* 1496 */           JSONArray a = new JSONArray();
/* 1497 */           String query = "select DISTINCT(mo.RESOURCENAME),mo.DISPLAYNAME,mo.RESOURCEID from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=pcm.CHILDID and mo.TYPE='AS400_MonJob' and pcm.PARENTID=" + resid;
/* 1498 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1499 */           while (rs.next()) {
/* 1500 */             JSONObject map = new JSONObject();
/* 1501 */             String resname = "";String dispname = "";String jobrid = "";String str1 = ":JOB-";String str2 = "";String toRemove = "-";
/* 1502 */             String[] jobResName = new String[4];
/* 1503 */             resname = rs.getString("RESOURCENAME");
/* 1504 */             dispname = rs.getString("DISPLAYNAME");
/* 1505 */             jobrid = rs.getString("RESOURCEID");
/* 1506 */             str2 = resname.substring(resname.indexOf(str1) + str1.length()) + "";
/* 1507 */             jobResName = str2.split(toRemove, 4);
/* 1508 */             String[] identityStyle = { "", "", "", "" };
/* 1509 */             String[] identityEnabled = { "", "", "", "" };
/* 1510 */             String[] monjobidentifiers = { "JOBNAME", "USERNAME", "JOBTYPE", "SUBSYSTEM" };
/*      */             try {
/* 1512 */               if (dispname.indexOf(str1) != -1) {
/* 1513 */                 dispname = dispname.substring(dispname.indexOf(str1) + str1.length()) + "";
/*      */               }
/* 1515 */               for (int i = 0; i < 4; i++) {
/* 1516 */                 if (dcc.getStatusforComponent(jobrid, monjobidentifiers[i])) {
/* 1517 */                   identityEnabled[i] = "checked";
/*      */                 } else {
/* 1519 */                   identityStyle[i] = "text-line-through disabledtext";
/*      */                 }
/*      */               }
/*      */             } catch (Exception e) {
/* 1523 */               e.printStackTrace();
/*      */             }
/* 1525 */             map.put("RESOURCENAME", resname);
/* 1526 */             map.put("DISPLAYNAME", dispname);
/* 1527 */             map.put("RESOURCEID", jobrid);
/* 1528 */             map.put("JOBNAME", jobResName[0]);
/* 1529 */             map.put("JN", identityEnabled[0]);
/* 1530 */             map.put("JNSTYLE", identityStyle[0]);
/* 1531 */             map.put("USERNAME", jobResName[1]);
/* 1532 */             map.put("UN", identityEnabled[1]);
/* 1533 */             map.put("UNSTYLE", identityStyle[1]);
/* 1534 */             map.put("JOBTYPE", jobResName[3]);
/* 1535 */             map.put("JT", identityEnabled[2]);
/* 1536 */             map.put("JTSTYLE", identityStyle[2]);
/* 1537 */             map.put("SUBSYSTEM", jobResName[2]);
/* 1538 */             map.put("SN", identityEnabled[3]);
/* 1539 */             map.put("JSSTYLE", identityStyle[3]);
/* 1540 */             map.put("MONJOBCOUNT", c);
/* 1541 */             c++;
/* 1542 */             a.put(map);
/*      */           }
/* 1544 */           PrintWriter pw = response.getWriter();
/* 1545 */           pw.print(a);
/* 1546 */           pw.flush();
/*      */         } catch (Exception e) {
/* 1548 */           e.printStackTrace();
/*      */         } finally {
/* 1550 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 1552 */       } else if ((getDiskLibraryName != null) && (getDiskLibraryName.equals("true"))) {
/*      */         try {
/* 1554 */           JSONObject map = new JSONObject();
/* 1555 */           String diskName = DBUtil.getSingleDataFromDB("select DISKLIBRARYNAME from AM_AS400_DC_PROPS where RESOURCEID=" + resid);
/* 1556 */           if (diskName == null) {
/* 1557 */             Map AS400Props = Constants.AS400Props;
/* 1558 */             String dataLibrary = (AS400Props != null) && (AS400Props.containsKey("disklibrary")) ? (String)AS400Props.get("disklibrary") : "Q";
/* 1559 */             if (dataLibrary.equalsIgnoreCase("Q")) {
/* 1560 */               String version = DBUtil.getSingleDataFromDB("select VERSION from AM_AS400_CONFIGURATION where RESOURCEID =" + resid);
/*      */               
/* 1562 */               if ((version != null) && ((version.equalsIgnoreCase("V5R4M0")) || (version.equalsIgnoreCase("V5R3M0")))) {
/* 1563 */                 diskName = "QMPGDATA";
/*      */               } else {
/* 1565 */                 diskName = "QPFRDATA";
/*      */               }
/*      */             }
/*      */             else {
/* 1569 */               diskName = dataLibrary;
/*      */             }
/*      */           }
/* 1572 */           map.put("DISKLIBRARYNAME", diskName != null ? diskName : "QPFRDATA");
/* 1573 */           PrintWriter pw = response.getWriter();
/* 1574 */           pw.print(map);
/*      */         }
/*      */         catch (Exception e) {
/* 1577 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1580 */       String o = request.getParameter("noredirect");
/* 1581 */       if ((o != null) && (o.equals("false"))) {
/* 1582 */         return mapping.findForward("admin");
/*      */       }
/*      */     } catch (Exception e) {
/* 1585 */       e.printStackTrace();
/*      */     }
/* 1587 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward updateDiskLibrary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1591 */     try { response.setContentType("text/html; charset=UTF-8");
/* 1592 */       String resourceid = request.getParameter("resourceid");
/* 1593 */       String diskLibName = request.getParameter("diskName");
/* 1594 */       String diskName = DBUtil.getSingleDataFromDB("select DISKLIBRARYNAME from AM_AS400_DC_PROPS where RESOURCEID=" + resourceid);
/*      */       
/* 1596 */       if (diskName == null) {
/* 1597 */         String query = "insert into AM_AS400_DC_PROPS (RESOURCEID,DISKLIBRARYNAME) values (" + resourceid + ",'" + diskLibName + "')";
/*      */         
/* 1599 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/*      */       else {
/* 1602 */         String query = "update AM_AS400_DC_PROPS set DISKLIBRARYNAME='" + diskLibName + "' where RESOURCEID=" + resourceid;
/*      */         
/* 1604 */         AMConnectionPool.executeUpdateStmt(query);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1609 */       e.printStackTrace();
/*      */     }
/* 1611 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward applyAS400Settings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1616 */     String resid = "";String settings = "";
/* 1617 */     Pattern quotePattern = Pattern.compile("&quot;");
/*      */     try
/*      */     {
/* 1620 */       response.setContentType("text/html; charset=UTF-8");
/* 1621 */       resid = request.getParameter("resourceid");
/* 1622 */       settings = request.getParameter("monsettings");
/* 1623 */       if ((settings != null) && (settings.equals("jobs"))) {
/*      */         try {
/* 1625 */           String jsonlist = request.getParameter("jobs");
/* 1626 */           jsonlist = jsonlist.trim();
/* 1627 */           Matcher matcher = quotePattern.matcher(jsonlist);
/* 1628 */           String joblist = matcher.replaceAll("\"");
/* 1629 */           AMLog.debug("AS400 Action: Monitored jobs to alter are  " + joblist);
/* 1630 */           JSONArray jjlist = new JSONArray(joblist);
/* 1631 */           int jobstoadd = jjlist.length();int count = 0;
/*      */           try {
/* 1633 */             for (int j = 0; j < jobstoadd; j++) {
/* 1634 */               JSONObject jobObj = jjlist.getJSONObject(count);
/* 1635 */               String resname = "";String jobrid = "";String excludes = "";
/* 1636 */               resname = jobObj.getString("RESNAME");
/* 1637 */               jobrid = jobObj.getString("ID");
/* 1638 */               excludes = jobObj.getString("EXCLUDE");
/* 1639 */               DataCollectionComponent.enableselectedComponent(jobrid, excludes);
/* 1640 */               DataCollectionComponent.loadComponent();
/* 1641 */               AMLog.info("AS400 Action: Included/Excluded identifiers for Monitored job " + jobrid + " - " + resname);
/* 1642 */               count++;
/*      */             }
/*      */           } catch (Exception e) {
/* 1645 */             e.printStackTrace();
/*      */           }
/*      */         } catch (Exception e) {
/* 1648 */           e.printStackTrace();
/* 1649 */           AMLog.debug("AS400 Action: Error while including/excluding identifiers to Monitored jobs " + e.getMessage());
/*      */         }
/* 1651 */         AMLog.debug("AS400 Action: Altered Monitored Jobs components for :" + resid);
/*      */       } else {
/* 1653 */         String componentname = request.getParameter("resourcetypedisplayname");
/* 1654 */         boolean enablemain = request.getParameter("resetall") != null;
/* 1655 */         String disabled = "";
/* 1656 */         if (!enablemain) {
/* 1657 */           disabled = request.getParameter("resourceid") + ",";
/*      */         }
/* 1659 */         if (!request.getParameter("unselected").equals("")) {
/* 1660 */           disabled = disabled + request.getParameter("unselected");
/*      */         }
/* 1662 */         if (!disabled.equals("")) {
/* 1663 */           DataCollectionComponent.enableselectedComponent(disabled, componentname);
/* 1664 */           DataCollectionComponent.loadComponent();
/*      */         }
/*      */         else
/*      */         {
/* 1668 */           DataCollectionComponent.enableallComponent(componentname);
/* 1669 */           DataCollectionComponent.loadComponent();
/*      */         }
/* 1671 */         return new ActionForward("/as400.do?method=getenabledetails&type=AS400/iSeries&resourceid=" + resid);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1675 */       e.printStackTrace();
/*      */     }
/* 1677 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward querydetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1685 */     AS400 as400 = null;
/* 1686 */     ResultSet rs = null;
/*      */     try {
/* 1688 */       Map data = new HashMap();
/* 1689 */       a = new ArrayList();
/* 1690 */       StringBuffer CommandMessage = new StringBuffer();
/*      */       
/* 1692 */       AMConnectionPool update = AMConnectionPool.getInstance();
/* 1693 */       String resid = request.getParameter("resourceid");
/* 1694 */       AMActionForm rf = (AMActionForm)form;
/* 1695 */       String Textarea = rf.getDescription();
/*      */       
/*      */ 
/* 1698 */       boolean admi = false;
/* 1699 */       boolean opr = false;
/* 1700 */       String persc = "";
/* 1701 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 1702 */         admi = true;
/* 1703 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCMD")) {
/* 1704 */         opr = true;
/*      */       }
/* 1706 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 1707 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 1710 */       String dispname = null;
/* 1711 */       String username = null;
/* 1712 */       String password = null;
/* 1713 */       String monname = " ";String query1 = "";
/* 1714 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 1715 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1716 */       if (rs.next()) {
/* 1717 */         monname = rs.getString("RESOURCENAME");
/* 1718 */         dispname = rs.getString("DISPLAYNAME");
/* 1719 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 1721 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1723 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 1724 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1725 */       if (rs.next()) {
/* 1726 */         username = rs.getString("USERNAME");
/* 1727 */         password = rs.getString("pass");
/*      */       }
/* 1729 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1731 */       if ((Textarea != null) && (!Textarea.trim().equals(""))) {
/* 1732 */         AMLog.debug("AS400 Action: The AS400 Command query is :" + Textarea);
/* 1733 */         String Textarea1 = Textarea.toLowerCase();
/*      */         
/* 1735 */         as400 = new AS400(monname, username, password);
/* 1736 */         as400.setGuiAvailable(false);
/* 1737 */         SocketProperties socket = new SocketProperties();
/* 1738 */         socket.setSoTimeout(60000);
/* 1739 */         as400.setSocketProperties(socket);
/*      */         
/* 1741 */         CommandCall command = new CommandCall(as400);
/* 1742 */         AMLog.debug("AS400CommandCall  executeCommand :");
/*      */         
/* 1744 */         if (command.run(Textarea1)) {
/* 1745 */           long et = System.currentTimeMillis();
/* 1746 */           AMLog.debug("AS400 Action:" + Textarea1 + "Command successful");
/*      */           try
/*      */           {
/* 1749 */             String Qry = "insert into AM_AS400_COMMANDS values(" + resid + ",'" + username + "','" + Textarea + "'," + et + ")";
/* 1750 */             AMConnectionPool.executeUpdateStmt(Qry);
/*      */           }
/*      */           catch (Exception er) {
/* 1753 */             AMLog.debug("AS400 Action: The Command :" + Textarea + "already exists in DataBase");
/*      */           }
/*      */         } else {
/* 1756 */           AMLog.debug("AS400 Action:" + Textarea1 + "Command failed");
/*      */         }
/*      */         
/*      */ 
/* 1760 */         AS400Message[] messagelist = command.getMessageList();
/*      */         
/* 1762 */         if (messagelist.length > 0) {
/* 1763 */           AMLog.debug("AS400 Action: messages from the command:" + messagelist.length);
/*      */         }
/*      */         
/* 1766 */         for (int i = 0; i < messagelist.length; i++) {
/* 1767 */           AMLog.debug("AS400 Action:" + messagelist[i].getID() + ":" + messagelist[i].getText());
/* 1768 */           CommandMessage.append(messagelist[i].getID() + ":" + messagelist[i].getText() + "<br>");
/*      */           
/*      */ 
/* 1771 */           messagelist[i].load();
/*      */           
/* 1773 */           CommandMessage.append(messagelist[i].getHelp() + "<br>");
/*      */         }
/* 1775 */         request.setAttribute("CommandMessage", CommandMessage);
/*      */         
/*      */ 
/* 1778 */         Map p1 = new HashMap();
/* 1779 */         a.add(p1);
/* 1780 */         data.put("command", a);
/*      */         
/* 1782 */         query1 = "select COMMAND from AM_AS400_COMMANDS where RESOURCEID=" + resid;
/* 1783 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 1784 */         while (rs.next()) {
/* 1785 */           Map p = new HashMap();
/* 1786 */           p.put("cmd", rs.getString("COMMAND"));
/* 1787 */           a.add(p);
/* 1788 */           data.put("command", a);
/*      */         }
/* 1790 */         AMConnectionPool.closeStatement(rs);
/* 1791 */         request.setAttribute("data", data);
/*      */         
/* 1793 */         rf.setCommand(Textarea);
/*      */       }
/*      */     } catch (Exception e) {
/*      */       List a;
/* 1797 */       e.printStackTrace();
/* 1798 */       request.setAttribute("CommandMessage", "" + e);
/* 1799 */       return new ActionForward("/jsp/as400/command.jsp");
/*      */     }
/*      */     finally {
/* 1802 */       AMConnectionPool.closeStatement(rs);
/* 1803 */       as400.disconnectAllServices();
/*      */     }
/*      */     
/* 1806 */     return new ActionForward("/jsp/as400/command.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward dspjoblog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1813 */     Map data = new HashMap();
/* 1814 */     List a = new ArrayList();
/* 1815 */     StringBuffer Error = new StringBuffer();
/* 1816 */     AS400 as400 = null;
/* 1817 */     ResultSet rs = null;
/*      */     try {
/* 1819 */       StringBuffer logMessage = new StringBuffer();
/* 1820 */       String resid = request.getParameter("resourceid");
/* 1821 */       String jobName = request.getParameter("jobName");
/* 1822 */       String user = request.getParameter("user");
/* 1823 */       String jobNumber = request.getParameter("jobNumber");
/* 1824 */       Integer num = Integer.valueOf(Integer.parseInt(jobNumber));
/* 1825 */       String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/* 1826 */       jobNumber = String.format(format, new Object[] { num });
/* 1827 */       request.setAttribute("qJobName", jobNumber + "/" + user + "/" + jobName);
/*      */       
/* 1829 */       String dispname = null;
/* 1830 */       String username = null;
/* 1831 */       String password = null;
/* 1832 */       String monname = " ";
/* 1833 */       String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 1834 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1835 */       if (rs.next()) {
/* 1836 */         monname = rs.getString("RESOURCENAME");
/* 1837 */         dispname = rs.getString("DISPLAYNAME");
/* 1838 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 1840 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1842 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 1843 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1844 */       if (rs.next()) {
/* 1845 */         username = rs.getString("USERNAME");
/* 1846 */         password = rs.getString("pass");
/*      */       }
/*      */       
/* 1849 */       as400 = new AS400(monname, username, password);
/* 1850 */       as400.setGuiAvailable(false);
/* 1851 */       SocketProperties socket = new SocketProperties();
/* 1852 */       socket.setSoTimeout(300000);
/* 1853 */       as400.setSocketProperties(socket);
/*      */       
/*      */ 
/* 1856 */       JobLog jl = new JobLog(as400, jobName, user, jobNumber);
/* 1857 */       request.setAttribute("username", jl.getUser());
/* 1858 */       Enumeration list2 = jl.getMessages();
/* 1859 */       while (list2.hasMoreElements()) {
/* 1860 */         Map p = new HashMap();
/* 1861 */         QueuedMessage k = (QueuedMessage)list2.nextElement();
/* 1862 */         logMessage.append(k.toString() + "<br>");
/* 1863 */         p.put("MSG", k.toString());
/* 1864 */         a.add(p);
/* 1865 */         data.put("dspjoblog", a);
/*      */       }
/* 1867 */       request.setAttribute("data", data);
/*      */     } catch (Exception e) {
/* 1869 */       e.printStackTrace();
/* 1870 */       Error.append(e.getMessage());
/* 1871 */       request.setAttribute("Error", Error);
/* 1872 */       request.setAttribute("logMessage", "" + e);
/*      */     }
/*      */     finally {
/* 1875 */       AMConnectionPool.closeStatement(rs);
/* 1876 */       as400.disconnectAllServices();
/*      */     }
/* 1878 */     return new ActionForward("/jsp/as400/joblog.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward userlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1886 */     Map data = new HashMap();
/* 1887 */     List a = new ArrayList();
/* 1888 */     StringBuffer Error = new StringBuffer();
/* 1889 */     AS400 as400 = null;
/* 1890 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1893 */       String resid = request.getParameter("resourceid");
/*      */       
/* 1895 */       dispname = null;
/* 1896 */       String username = null;
/* 1897 */       String password = null;
/*      */       
/* 1899 */       String monname = " ";
/* 1900 */       String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 1901 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1902 */       if (rs.next()) {
/* 1903 */         monname = rs.getString("RESOURCENAME");
/* 1904 */         dispname = rs.getString("DISPLAYNAME");
/* 1905 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 1907 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1909 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 1910 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1911 */       if (rs.next()) {
/* 1912 */         username = rs.getString("USERNAME");
/* 1913 */         password = rs.getString("pass");
/*      */       }
/* 1915 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1917 */       as400 = new AS400(monname, username, password);
/* 1918 */       as400.setGuiAvailable(false);
/* 1919 */       SocketProperties socket = new SocketProperties();
/* 1920 */       socket.setSoTimeout(300000);
/* 1921 */       as400.setSocketProperties(socket);
/*      */       
/* 1923 */       UserList ult = new UserList(as400);
/* 1924 */       ult.setUserProfile("*ALL");
/* 1925 */       ult.setGroupInfo("*NONE");
/* 1926 */       ult.setUserInfo("*ALL");
/* 1927 */       ult.load();
/* 1928 */       Enumeration list = ult.getUsers();
/* 1929 */       while (list.hasMoreElements()) {
/* 1930 */         Map p = new HashMap();
/* 1931 */         User usr = (User)list.nextElement();
/* 1932 */         usr.loadUserInformation();
/*      */         
/* 1934 */         p.put("Name", usr.getName());
/* 1935 */         p.put("Description", usr.getDescription());
/* 1936 */         p.put("Status", usr.getStatus());
/* 1937 */         p.put("Group profile Name", usr.getGroupProfileName());
/* 1938 */         p.put("Limit Capabilities", usr.getLimitCapabilities());
/* 1939 */         p.put("Storage Allocated", Integer.valueOf(usr.getMaximumStorageAllowed()));
/* 1940 */         p.put("Storage occupied by this user's owned objects", Integer.valueOf(usr.getStorageUsed()));
/* 1941 */         p.put("daysUntilPasswordExpire", Integer.valueOf(usr.getDaysUntilPasswordExpire()));
/* 1942 */         a.add(p);
/* 1943 */         data.put("userlist", a);
/*      */       }
/* 1945 */       request.setAttribute("data", data);
/*      */     } catch (Exception e) {
/*      */       String dispname;
/* 1948 */       e.printStackTrace();
/* 1949 */       Error.append(e.getMessage());
/* 1950 */       request.setAttribute("Error", Error);
/* 1951 */       request.setAttribute("logMessage", "" + e);
/* 1952 */       return new ActionForward("/jsp/as400/userlist.jsp");
/*      */     }
/*      */     finally {
/* 1955 */       AMConnectionPool.closeStatement(rs);
/* 1956 */       as400.disconnectAllServices();
/*      */     }
/*      */     
/* 1959 */     return new ActionForward("/jsp/as400/userlist.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward productlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1966 */     Map data = new HashMap();
/* 1967 */     List a = new ArrayList();
/* 1968 */     StringBuffer Error = new StringBuffer();
/* 1969 */     AS400 as400 = null;
/* 1970 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1973 */       String resid = request.getParameter("resourceid");
/*      */       
/* 1975 */       dispname = null;
/* 1976 */       String username = null;
/* 1977 */       String password = null;
/* 1978 */       String monname = " ";
/* 1979 */       String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 1980 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1981 */       if (rs.next()) {
/* 1982 */         monname = rs.getString("RESOURCENAME");
/* 1983 */         dispname = rs.getString("DISPLAYNAME");
/* 1984 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 1986 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1988 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 1989 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 1990 */       if (rs.next()) {
/* 1991 */         username = rs.getString("USERNAME");
/* 1992 */         password = rs.getString("pass");
/*      */       }
/* 1994 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 1996 */       as400 = new AS400(monname, username, password);
/* 1997 */       as400.setGuiAvailable(false);
/* 1998 */       SocketProperties socket = new SocketProperties();
/* 1999 */       socket.setSoTimeout(300000);
/* 2000 */       as400.setSocketProperties(socket);
/* 2001 */       ProductList pl = new ProductList(as400);
/*      */       
/* 2003 */       Product[] pd = pl.getProducts();
/* 2004 */       for (int i = 0; i < pd.length; i++) {
/* 2005 */         Map p = new HashMap();
/* 2006 */         p.put("productid", pd[i].getProductID());
/* 2007 */         p.put("productoption", pd[i].getProductOption());
/* 2008 */         p.put("description", pd[i].getDescriptionText());
/* 2009 */         a.add(p);
/* 2010 */         data.put("productlist", a);
/*      */       }
/* 2012 */       request.setAttribute("data", data);
/*      */     } catch (Exception e) { String dispname;
/* 2014 */       e.printStackTrace();
/* 2015 */       Error.append(e.getMessage());
/* 2016 */       request.setAttribute("Error", Error);
/* 2017 */       request.setAttribute("logMessage", "" + e);
/* 2018 */       return new ActionForward("/jsp/as400/productlist.jsp");
/*      */     }
/*      */     finally {
/* 2021 */       as400.disconnectAllServices();
/* 2022 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2025 */     return new ActionForward("/jsp/as400/productlist.jsp");
/*      */   }
/*      */   
/*      */   public long getMaxCollectionTime(int resourceid)
/*      */   {
/* 2030 */     long time = 0L;
/* 2031 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2032 */     ResultSet rs = null;
/* 2033 */     String query = "select max(COLLECTIONTIME) from AM_AS400_STATUS where RESOURCEID=" + resourceid;
/*      */     
/*      */     try
/*      */     {
/* 2037 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 2038 */       if (rs.next())
/*      */       {
/* 2040 */         time = rs.getLong(1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2046 */       e.printStackTrace();
/*      */     } finally {
/* 2048 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 2050 */     return time;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward systemvalue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2057 */     Map data = new HashMap();
/* 2058 */     List a = new ArrayList();
/* 2059 */     AS400 as400 = null;
/* 2060 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2063 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2065 */       dispname = null;
/* 2066 */       String username = null;
/* 2067 */       String password = null;
/*      */       
/* 2069 */       boolean admi = false;
/* 2070 */       boolean opr = false;
/* 2071 */       String persc = "";
/* 2072 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2073 */         admi = true;
/* 2074 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowNA")) {
/* 2075 */         opr = true;
/*      */       }
/*      */       
/* 2078 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2079 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/*      */ 
/* 2083 */       String monname = " ";String query1 = "";
/* 2084 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2085 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2086 */       if (rs.next()) {
/* 2087 */         monname = rs.getString("RESOURCENAME");
/* 2088 */         dispname = rs.getString("DISPLAYNAME");
/* 2089 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2091 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2093 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2094 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2095 */       if (rs.next()) {
/* 2096 */         username = rs.getString("USERNAME");
/* 2097 */         password = rs.getString("pass");
/*      */       }
/* 2099 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2101 */       as400 = new AS400(monname, username, password);
/* 2102 */       as400.setGuiAvailable(false);
/* 2103 */       SocketProperties socket = new SocketProperties();
/* 2104 */       socket.setSoTimeout(300000);
/* 2105 */       as400.setSocketProperties(socket);
/*      */       
/* 2107 */       SystemValue sv = new SystemValue();
/* 2108 */       SystemValueList svl = new SystemValueList(as400);
/* 2109 */       Vector v = svl.getGroup(8);
/* 2110 */       Enumeration list = v.elements();
/* 2111 */       while (list.hasMoreElements()) {
/* 2112 */         Map p = new HashMap();
/* 2113 */         StringBuffer stract = new StringBuffer();
/* 2114 */         sv = (SystemValue)list.nextElement();
/* 2115 */         if (sv.getType() == 2) {
/* 2116 */           p.put("name", sv.getName());
/* 2117 */           p.put("value", sv.getValue().toString());
/* 2118 */           p.put("description", sv.getDescription());
/* 2119 */           p.put("type", "1");
/* 2120 */         } else if (sv.getType() == 3) {
/* 2121 */           p.put("name", sv.getName());
/* 2122 */           p.put("value", sv.getValue().toString());
/* 2123 */           p.put("description", sv.getDescription());
/* 2124 */           p.put("type", "2");
/* 2125 */         } else if (sv.getType() == 1) {
/* 2126 */           p.put("name", sv.getName());
/* 2127 */           p.put("value", sv.getValue().toString());
/* 2128 */           p.put("description", sv.getDescription());
/* 2129 */           p.put("type", "3");
/* 2130 */         } else if (sv.getType() == 5) {
/* 2131 */           p.put("name", sv.getName());
/* 2132 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2133 */           p.put("description", sv.getDescription());
/* 2134 */           p.put("type", "4");
/*      */         }
/* 2136 */         else if (sv.getType() == 4) {
/* 2137 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2139 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2140 */             String tmp = (String)stringlist.get(i);
/* 2141 */             tmp = tmp.trim();
/*      */             
/* 2143 */             if (i < stringlist.size() - 1) {
/* 2144 */               stract.append(tmp + ",");
/*      */             } else {
/* 2146 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2151 */           String stracts = stract.toString();
/* 2152 */           p.put("name", sv.getName());
/* 2153 */           p.put("value", stracts);
/* 2154 */           p.put("description", sv.getDescription());
/* 2155 */           p.put("type", "5");
/*      */         }
/*      */         
/* 2158 */         a.add(p);
/* 2159 */         data.put("systemvalue", a);
/*      */       }
/* 2161 */       request.setAttribute("data", data);
/* 2162 */       request.setAttribute("head", "Network Attributes for");
/* 2163 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.networkattributes.title"));
/*      */     } catch (Exception e) {
/*      */       String dispname;
/* 2166 */       e.printStackTrace();
/* 2167 */       request.setAttribute("head", "Network Attributes for");
/* 2168 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.networkattributes.title"));
/* 2169 */       request.setAttribute("Error", e.getMessage());
/* 2170 */       request.setAttribute("logMessage", "" + e);
/* 2171 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2174 */       AMConnectionPool.closeStatement(rs);
/* 2175 */       as400.disconnectAllServices();
/*      */     }
/*      */     
/* 2178 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward systemcontrol(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2185 */     Map data = new HashMap();
/* 2186 */     List a = new ArrayList();
/* 2187 */     StringBuffer Error = new StringBuffer();
/* 2188 */     AS400 as400 = null;
/* 2189 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2192 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2194 */       dispname = null;
/* 2195 */       String username = null;
/* 2196 */       String password = null;
/*      */       
/*      */ 
/* 2199 */       boolean admi = false;
/* 2200 */       boolean opr = false;
/* 2201 */       String persc = "";
/* 2202 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2203 */         admi = true;
/* 2204 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowSC")) {
/* 2205 */         opr = true;
/*      */       }
/*      */       
/* 2208 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2209 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2212 */       String monname = " ";String query1 = "";
/* 2213 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2214 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2215 */       if (rs.next()) {
/* 2216 */         monname = rs.getString("RESOURCENAME");
/* 2217 */         dispname = rs.getString("DISPLAYNAME");
/* 2218 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2220 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2222 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2223 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2224 */       if (rs.next()) {
/* 2225 */         username = rs.getString("USERNAME");
/* 2226 */         password = rs.getString("pass");
/*      */       }
/* 2228 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2230 */       as400 = new AS400(monname, username, password);
/* 2231 */       as400.setGuiAvailable(false);
/* 2232 */       SocketProperties socket = new SocketProperties();
/* 2233 */       socket.setSoTimeout(300000);
/* 2234 */       as400.setSocketProperties(socket);
/*      */       
/* 2236 */       SystemValue sv = new SystemValue();
/* 2237 */       SystemValueList svl = new SystemValueList(as400);
/* 2238 */       Vector v = svl.getGroup(7);
/* 2239 */       Enumeration list = v.elements();
/* 2240 */       while (list.hasMoreElements()) {
/* 2241 */         Map p = new HashMap();
/* 2242 */         StringBuffer stract = new StringBuffer();
/* 2243 */         sv = (SystemValue)list.nextElement();
/* 2244 */         if (sv.getType() == 2) {
/* 2245 */           p.put("name", sv.getName());
/* 2246 */           p.put("value", sv.getValue().toString());
/* 2247 */           p.put("description", sv.getDescription());
/* 2248 */           p.put("type", "1");
/* 2249 */         } else if (sv.getType() == 3) {
/* 2250 */           p.put("name", sv.getName());
/* 2251 */           p.put("value", sv.getValue().toString());
/* 2252 */           p.put("description", sv.getDescription());
/* 2253 */           p.put("type", "2");
/* 2254 */         } else if (sv.getType() == 1) {
/* 2255 */           p.put("name", sv.getName());
/* 2256 */           p.put("value", sv.getValue().toString());
/* 2257 */           p.put("description", sv.getDescription());
/* 2258 */           p.put("type", "3");
/* 2259 */         } else if (sv.getType() == 5) {
/* 2260 */           p.put("name", sv.getName());
/* 2261 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2262 */           p.put("description", sv.getDescription());
/* 2263 */           p.put("type", "4");
/*      */         }
/* 2265 */         else if (sv.getType() == 4) {
/* 2266 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2268 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2269 */             String tmp = (String)stringlist.get(i);
/* 2270 */             tmp = tmp.trim();
/*      */             
/* 2272 */             if (i < stringlist.size() - 1) {
/* 2273 */               stract.append(tmp + ",");
/*      */             } else {
/* 2275 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2280 */           String stracts = stract.toString();
/*      */           
/* 2282 */           p.put("name", sv.getName());
/* 2283 */           p.put("value", stracts);
/* 2284 */           p.put("description", sv.getDescription());
/* 2285 */           p.put("type", "5");
/*      */         }
/* 2287 */         a.add(p);
/* 2288 */         data.put("systemvalue", a);
/*      */       }
/* 2290 */       request.setAttribute("data", data);
/* 2291 */       request.setAttribute("head", "System Control for");
/* 2292 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.systemcontrol.title"));
/*      */     } catch (Exception e) {
/*      */       String dispname;
/* 2295 */       e.printStackTrace();
/* 2296 */       request.setAttribute("head", "System Control for");
/* 2297 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.systemcontrol.title"));
/* 2298 */       Error.append(e.getMessage());
/* 2299 */       request.setAttribute("Error", Error);
/* 2300 */       request.setAttribute("logMessage", "" + e);
/* 2301 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2304 */       as400.disconnectAllServices();
/* 2305 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2308 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward storage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2315 */     Map data = new HashMap();
/* 2316 */     List a = new ArrayList();
/* 2317 */     StringBuffer Error = new StringBuffer();
/* 2318 */     AS400 as400 = null;
/* 2319 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2322 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2324 */       dispname = null;
/* 2325 */       String username = null;
/* 2326 */       String password = null;
/*      */       
/* 2328 */       boolean admi = false;
/* 2329 */       boolean opr = false;
/* 2330 */       String persc = "";
/* 2331 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2332 */         admi = true;
/* 2333 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCS")) {
/* 2334 */         opr = true;
/*      */       }
/*      */       
/* 2337 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2338 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2341 */       String monname = " ";String query1 = "";
/* 2342 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2343 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2344 */       if (rs.next()) {
/* 2345 */         monname = rs.getString("RESOURCENAME");
/* 2346 */         dispname = rs.getString("DISPLAYNAME");
/* 2347 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2349 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2351 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2352 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2353 */       if (rs.next()) {
/* 2354 */         username = rs.getString("USERNAME");
/* 2355 */         password = rs.getString("pass");
/*      */       }
/* 2357 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2359 */       as400 = new AS400(monname, username, password);
/* 2360 */       as400.setGuiAvailable(false);
/* 2361 */       SocketProperties socket = new SocketProperties();
/* 2362 */       socket.setSoTimeout(300000);
/* 2363 */       as400.setSocketProperties(socket);
/*      */       
/* 2365 */       SystemValue sv = new SystemValue();
/* 2366 */       SystemValueList svl = new SystemValueList(as400);
/* 2367 */       Vector v = svl.getGroup(6);
/* 2368 */       Enumeration list = v.elements();
/* 2369 */       while (list.hasMoreElements()) {
/* 2370 */         Map p = new HashMap();
/* 2371 */         StringBuffer stract = new StringBuffer();
/* 2372 */         sv = (SystemValue)list.nextElement();
/* 2373 */         if (sv.getType() == 2) {
/* 2374 */           p.put("name", sv.getName());
/* 2375 */           p.put("value", sv.getValue().toString());
/* 2376 */           p.put("description", sv.getDescription());
/* 2377 */           p.put("type", "1");
/* 2378 */         } else if (sv.getType() == 3) {
/* 2379 */           p.put("name", sv.getName());
/* 2380 */           p.put("value", sv.getValue().toString());
/* 2381 */           p.put("description", sv.getDescription());
/* 2382 */           p.put("type", "2");
/* 2383 */         } else if (sv.getType() == 1) {
/* 2384 */           p.put("name", sv.getName());
/* 2385 */           p.put("value", sv.getValue().toString());
/* 2386 */           p.put("description", sv.getDescription());
/* 2387 */           p.put("type", "3");
/* 2388 */         } else if (sv.getType() == 5) {
/* 2389 */           p.put("name", sv.getName());
/* 2390 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2391 */           p.put("description", sv.getDescription());
/* 2392 */           p.put("type", "4");
/*      */         }
/* 2394 */         else if (sv.getType() == 4) {
/* 2395 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2397 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2398 */             String tmp = (String)stringlist.get(i);
/* 2399 */             tmp = tmp.trim();
/*      */             
/* 2401 */             if (i < stringlist.size() - 1) {
/* 2402 */               stract.append(tmp + ",");
/*      */             } else {
/* 2404 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2409 */           String stracts = stract.toString();
/* 2410 */           p.put("name", sv.getName());
/* 2411 */           p.put("value", stracts);
/* 2412 */           p.put("description", sv.getDescription());
/* 2413 */           p.put("type", "5");
/*      */         }
/* 2415 */         a.add(p);
/* 2416 */         data.put("systemvalue", a);
/*      */       }
/* 2418 */       request.setAttribute("data", data);
/* 2419 */       request.setAttribute("head", "Storage for");
/* 2420 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.storage.title"));
/*      */     } catch (Exception e) { String dispname;
/* 2422 */       e.printStackTrace();
/* 2423 */       request.setAttribute("head", "Storage for");
/* 2424 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.storage.title"));
/* 2425 */       Error.append(e.getMessage());
/* 2426 */       request.setAttribute("Error", Error);
/* 2427 */       request.setAttribute("logMessage", "" + e);
/* 2428 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2431 */       as400.disconnectAllServices();
/* 2432 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2435 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward security(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2442 */     Map data = new HashMap();
/* 2443 */     List a = new ArrayList();
/* 2444 */     StringBuffer Error = new StringBuffer();
/* 2445 */     AS400 as400 = null;
/* 2446 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2449 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2451 */       dispname = null;
/* 2452 */       String username = null;
/* 2453 */       String password = null;
/*      */       
/* 2455 */       boolean admi = false;
/* 2456 */       boolean opr = false;
/* 2457 */       String persc = "";
/* 2458 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2459 */         admi = true;
/* 2460 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCSU")) {
/* 2461 */         opr = true;
/*      */       }
/*      */       
/* 2464 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2465 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2468 */       String monname = " ";String query1 = "";
/* 2469 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2470 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2471 */       if (rs.next()) {
/* 2472 */         monname = rs.getString("RESOURCENAME");
/* 2473 */         dispname = rs.getString("DISPLAYNAME");
/* 2474 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2476 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2478 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2479 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2480 */       if (rs.next()) {
/* 2481 */         username = rs.getString("USERNAME");
/* 2482 */         password = rs.getString("pass");
/*      */       }
/* 2484 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2486 */       as400 = new AS400(monname, username, password);
/* 2487 */       as400.setGuiAvailable(false);
/* 2488 */       SocketProperties socket = new SocketProperties();
/* 2489 */       socket.setSoTimeout(300000);
/* 2490 */       as400.setSocketProperties(socket);
/*      */       
/* 2492 */       SystemValue sv = new SystemValue();
/* 2493 */       SystemValueList svl = new SystemValueList(as400);
/* 2494 */       Vector v = svl.getGroup(5);
/* 2495 */       Enumeration list = v.elements();
/* 2496 */       while (list.hasMoreElements()) {
/* 2497 */         Map p = new HashMap();
/* 2498 */         StringBuffer stract = new StringBuffer();
/* 2499 */         sv = (SystemValue)list.nextElement();
/* 2500 */         if (sv.getType() == 2) {
/* 2501 */           p.put("name", sv.getName());
/* 2502 */           p.put("value", sv.getValue().toString());
/* 2503 */           p.put("description", sv.getDescription());
/* 2504 */           p.put("type", "1");
/* 2505 */         } else if (sv.getType() == 3) {
/* 2506 */           p.put("name", sv.getName());
/* 2507 */           p.put("value", sv.getValue().toString());
/* 2508 */           p.put("description", sv.getDescription());
/* 2509 */           p.put("type", "2");
/* 2510 */         } else if (sv.getType() == 1) {
/* 2511 */           p.put("name", sv.getName());
/* 2512 */           p.put("value", sv.getValue().toString());
/* 2513 */           p.put("description", sv.getDescription());
/* 2514 */           p.put("type", "3");
/* 2515 */         } else if (sv.getType() == 5) {
/* 2516 */           p.put("name", sv.getName());
/* 2517 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2518 */           p.put("description", sv.getDescription());
/* 2519 */           p.put("type", "4");
/*      */         }
/* 2521 */         else if (sv.getType() == 4) {
/* 2522 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2524 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2525 */             String tmp = (String)stringlist.get(i);
/* 2526 */             tmp = tmp.trim();
/*      */             
/* 2528 */             if (i < stringlist.size() - 1) {
/* 2529 */               stract.append(tmp + ",");
/*      */             } else {
/* 2531 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2536 */           String stracts = stract.toString();
/* 2537 */           p.put("name", sv.getName());
/* 2538 */           p.put("value", stracts);
/* 2539 */           p.put("description", sv.getDescription());
/* 2540 */           p.put("type", "5");
/*      */         }
/* 2542 */         a.add(p);
/* 2543 */         data.put("systemvalue", a);
/* 2544 */         request.setAttribute("head", "Security for");
/* 2545 */         request.setAttribute("title", FormatUtil.getString("am.webclient.as400.security.title"));
/*      */       }
/*      */       
/* 2548 */       request.setAttribute("data", data);
/*      */     } catch (Exception e) { String dispname;
/* 2550 */       e.printStackTrace();
/* 2551 */       request.setAttribute("head", "Security for");
/* 2552 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.security.title"));
/* 2553 */       Error.append(e.getMessage());
/* 2554 */       request.setAttribute("Error", Error);
/* 2555 */       request.setAttribute("logMessage", "" + e);
/* 2556 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2559 */       as400.disconnectAllServices();
/* 2560 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2563 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward msgandlogging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2570 */     Map data = new HashMap();
/* 2571 */     List a = new ArrayList();
/* 2572 */     AS400 as400 = null;
/* 2573 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2576 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2578 */       dispname = null;
/* 2579 */       String username = null;
/* 2580 */       String password = null;
/*      */       
/* 2582 */       boolean admi = false;
/* 2583 */       boolean opr = false;
/* 2584 */       String persc = "";
/* 2585 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2586 */         admi = true;
/* 2587 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowML")) {
/* 2588 */         opr = true;
/*      */       }
/*      */       
/* 2591 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2592 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2595 */       String monname = " ";String query1 = "";
/* 2596 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2597 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2598 */       if (rs.next()) {
/* 2599 */         monname = rs.getString("RESOURCENAME");
/* 2600 */         dispname = rs.getString("DISPLAYNAME");
/* 2601 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2603 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2605 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2606 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2607 */       if (rs.next()) {
/* 2608 */         username = rs.getString("USERNAME");
/* 2609 */         password = rs.getString("pass");
/*      */       }
/* 2611 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2613 */       as400 = new AS400(monname, username, password);
/* 2614 */       as400.setGuiAvailable(false);
/* 2615 */       SocketProperties socket = new SocketProperties();
/* 2616 */       socket.setSoTimeout(300000);
/* 2617 */       as400.setSocketProperties(socket);
/*      */       
/* 2619 */       SystemValue sv = new SystemValue();
/* 2620 */       SystemValueList svl = new SystemValueList(as400);
/* 2621 */       Vector v = svl.getGroup(4);
/* 2622 */       Enumeration list = v.elements();
/* 2623 */       while (list.hasMoreElements()) {
/* 2624 */         Map p = new HashMap();
/* 2625 */         StringBuffer stract = new StringBuffer();
/* 2626 */         sv = (SystemValue)list.nextElement();
/* 2627 */         if (sv.getType() == 2) {
/* 2628 */           p.put("name", sv.getName());
/* 2629 */           p.put("value", sv.getValue().toString());
/* 2630 */           p.put("description", sv.getDescription());
/* 2631 */           p.put("type", "1");
/* 2632 */         } else if (sv.getType() == 3) {
/* 2633 */           p.put("name", sv.getName());
/* 2634 */           p.put("value", sv.getValue().toString());
/* 2635 */           p.put("description", sv.getDescription());
/* 2636 */           p.put("type", "2");
/* 2637 */         } else if (sv.getType() == 1) {
/* 2638 */           p.put("name", sv.getName());
/* 2639 */           p.put("value", sv.getValue().toString());
/* 2640 */           p.put("description", sv.getDescription());
/* 2641 */           p.put("type", "3");
/* 2642 */         } else if (sv.getType() == 5) {
/* 2643 */           p.put("name", sv.getName());
/* 2644 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2645 */           p.put("description", sv.getDescription());
/* 2646 */           p.put("type", "4");
/*      */         }
/* 2648 */         else if (sv.getType() == 4) {
/* 2649 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2651 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2652 */             String tmp = (String)stringlist.get(i);
/* 2653 */             tmp = tmp.trim();
/*      */             
/* 2655 */             if (i < stringlist.size() - 1) {
/* 2656 */               stract.append(tmp + ",");
/*      */             } else {
/* 2658 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2663 */           String stracts = stract.toString();
/* 2664 */           p.put("name", sv.getName());
/* 2665 */           p.put("value", stracts);
/* 2666 */           p.put("description", sv.getDescription());
/* 2667 */           p.put("type", "5");
/*      */         }
/* 2669 */         a.add(p);
/* 2670 */         data.put("systemvalue", a);
/*      */       }
/* 2672 */       request.setAttribute("data", data);
/* 2673 */       request.setAttribute("head", "Messaging and Logging for");
/* 2674 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.messageandlogging.title"));
/*      */     } catch (Exception e) { String dispname;
/* 2676 */       e.printStackTrace();
/* 2677 */       request.setAttribute("head", "Messaging and Logging for");
/* 2678 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.messageandlogging.title"));
/* 2679 */       request.setAttribute("Error", e.getMessage());
/* 2680 */       request.setAttribute("logMessage", "" + e);
/* 2681 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2684 */       as400.disconnectAllServices();
/* 2685 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2688 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward librarylist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2695 */     Map data = new HashMap();
/* 2696 */     List a = new ArrayList();
/* 2697 */     StringBuffer Error = new StringBuffer();
/* 2698 */     AS400 as400 = null;
/* 2699 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2702 */       String resid = request.getParameter("resourceid");
/*      */       
/* 2704 */       dispname = null;
/* 2705 */       String username = null;
/* 2706 */       String password = null;
/*      */       
/* 2708 */       boolean admi = false;
/* 2709 */       boolean opr = false;
/* 2710 */       String persc = "";
/* 2711 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2712 */         admi = true;
/* 2713 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowLL")) {
/* 2714 */         opr = true;
/*      */       }
/*      */       
/* 2717 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2718 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/* 2720 */       String monname = " ";String query1 = "";
/* 2721 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2722 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2723 */       if (rs.next()) {
/* 2724 */         monname = rs.getString("RESOURCENAME");
/* 2725 */         dispname = rs.getString("DISPLAYNAME");
/* 2726 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2728 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2730 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2731 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2732 */       if (rs.next()) {
/* 2733 */         username = rs.getString("USERNAME");
/* 2734 */         password = rs.getString("pass");
/*      */       }
/* 2736 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2738 */       as400 = new AS400(monname, username, password);
/* 2739 */       as400.setGuiAvailable(false);
/* 2740 */       SocketProperties socket = new SocketProperties();
/* 2741 */       socket.setSoTimeout(300000);
/* 2742 */       as400.setSocketProperties(socket);
/*      */       
/* 2744 */       SystemValue sv = new SystemValue();
/* 2745 */       SystemValueList svl = new SystemValueList(as400);
/* 2746 */       Vector v = svl.getGroup(3);
/* 2747 */       Enumeration list = v.elements();
/* 2748 */       while (list.hasMoreElements()) {
/* 2749 */         Map p = new HashMap();
/* 2750 */         StringBuffer stract = new StringBuffer();
/* 2751 */         sv = (SystemValue)list.nextElement();
/* 2752 */         if (sv.getType() == 2) {
/* 2753 */           p.put("name", sv.getName());
/* 2754 */           p.put("value", sv.getValue().toString());
/* 2755 */           p.put("description", sv.getDescription());
/* 2756 */           p.put("type", "1");
/* 2757 */         } else if (sv.getType() == 3) {
/* 2758 */           p.put("name", sv.getName());
/* 2759 */           p.put("value", sv.getValue().toString());
/* 2760 */           p.put("description", sv.getDescription());
/* 2761 */           p.put("type", "2");
/* 2762 */         } else if (sv.getType() == 1) {
/* 2763 */           p.put("name", sv.getName());
/* 2764 */           p.put("value", sv.getValue().toString());
/* 2765 */           p.put("description", sv.getDescription());
/* 2766 */           p.put("type", "3");
/* 2767 */         } else if (sv.getType() == 5) {
/* 2768 */           p.put("name", sv.getName());
/* 2769 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2770 */           p.put("description", sv.getDescription());
/* 2771 */           p.put("type", "4");
/*      */         }
/* 2773 */         else if (sv.getType() == 4) {
/* 2774 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2776 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2777 */             String tmp = (String)stringlist.get(i);
/* 2778 */             tmp = tmp.trim();
/*      */             
/* 2780 */             if (i < stringlist.size() - 1) {
/* 2781 */               stract.append(tmp + ",");
/*      */             } else {
/* 2783 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2788 */           String stracts = stract.toString();
/* 2789 */           p.put("name", sv.getName());
/* 2790 */           p.put("value", stracts);
/* 2791 */           p.put("description", sv.getDescription());
/* 2792 */           p.put("type", "5");
/*      */         }
/* 2794 */         a.add(p);
/* 2795 */         data.put("systemvalue", a);
/*      */       }
/* 2797 */       request.setAttribute("data", data);
/* 2798 */       request.setAttribute("head", "Library List for");
/* 2799 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.librarylist.title"));
/*      */     } catch (Exception e) { String dispname;
/* 2801 */       e.printStackTrace();
/* 2802 */       request.setAttribute("head", "Library List for");
/* 2803 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.librarylist.title"));
/* 2804 */       Error.append(e.getMessage());
/* 2805 */       request.setAttribute("Error", Error);
/* 2806 */       request.setAttribute("logMessage", "" + e);
/* 2807 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2810 */       as400.disconnectAllServices();
/* 2811 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2814 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward dateandtime(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2821 */     Map data = new HashMap();
/* 2822 */     List a = new ArrayList();
/* 2823 */     StringBuffer Error = new StringBuffer();
/* 2824 */     AS400 as400 = null;
/* 2825 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2828 */       String resid = request.getParameter("resourceid");
/* 2829 */       dispname = null;
/* 2830 */       String username = null;
/* 2831 */       String password = null;
/*      */       
/* 2833 */       boolean admi = false;
/* 2834 */       boolean opr = false;
/* 2835 */       String persc = "";
/* 2836 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2837 */         admi = true;
/* 2838 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowDT")) {
/* 2839 */         opr = true;
/*      */       }
/* 2841 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2842 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2845 */       String monname = " ";String query1 = "";
/* 2846 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2847 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2848 */       if (rs.next()) {
/* 2849 */         monname = rs.getString("RESOURCENAME");
/* 2850 */         dispname = rs.getString("DISPLAYNAME");
/* 2851 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2853 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2855 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2856 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2857 */       if (rs.next()) {
/* 2858 */         username = rs.getString("USERNAME");
/* 2859 */         password = rs.getString("pass");
/*      */       }
/* 2861 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2863 */       as400 = new AS400(monname, username, password);
/* 2864 */       as400.setGuiAvailable(false);
/* 2865 */       SocketProperties socket = new SocketProperties();
/* 2866 */       socket.setSoTimeout(300000);
/* 2867 */       as400.setSocketProperties(socket);
/*      */       
/* 2869 */       SystemValue sv = new SystemValue();
/* 2870 */       SystemValueList svl = new SystemValueList(as400);
/* 2871 */       Vector v = svl.getGroup(1);
/* 2872 */       Enumeration list = v.elements();
/* 2873 */       while (list.hasMoreElements()) {
/* 2874 */         Map p = new HashMap();
/* 2875 */         StringBuffer stract = new StringBuffer();
/* 2876 */         sv = (SystemValue)list.nextElement();
/* 2877 */         if (sv.getType() == 2) {
/* 2878 */           p.put("name", sv.getName());
/* 2879 */           p.put("value", sv.getValue().toString());
/* 2880 */           p.put("description", sv.getDescription());
/* 2881 */           p.put("type", "1");
/* 2882 */         } else if (sv.getType() == 3) {
/* 2883 */           p.put("name", sv.getName());
/* 2884 */           p.put("value", sv.getValue().toString());
/* 2885 */           p.put("description", sv.getDescription());
/* 2886 */           p.put("type", "2");
/* 2887 */         } else if (sv.getType() == 1) {
/* 2888 */           p.put("name", sv.getName());
/* 2889 */           p.put("value", sv.getValue().toString());
/* 2890 */           p.put("description", sv.getDescription());
/* 2891 */           p.put("type", "3");
/* 2892 */         } else if (sv.getType() == 5) {
/* 2893 */           p.put("name", sv.getName());
/* 2894 */           p.put("value", ((Date)sv.getValue()).toString());
/* 2895 */           p.put("description", sv.getDescription());
/* 2896 */           p.put("type", "4");
/*      */         }
/* 2898 */         else if (sv.getType() == 4) {
/* 2899 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 2901 */           for (int i = 0; i < stringlist.size(); i++) {
/* 2902 */             String tmp = (String)stringlist.get(i);
/* 2903 */             tmp = tmp.trim();
/*      */             
/* 2905 */             if (i < stringlist.size() - 1) {
/* 2906 */               stract.append(tmp + ",");
/*      */             } else {
/* 2908 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 2913 */           String stracts = stract.toString();
/* 2914 */           p.put("name", sv.getName());
/* 2915 */           p.put("value", stracts);
/* 2916 */           p.put("description", sv.getDescription());
/* 2917 */           p.put("type", "5");
/*      */         }
/* 2919 */         a.add(p);
/* 2920 */         data.put("systemvalue", a);
/*      */       }
/* 2922 */       request.setAttribute("data", data);
/* 2923 */       request.setAttribute("head", "Date and Time for");
/* 2924 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.dateandtime.title"));
/*      */     } catch (Exception e) { String dispname;
/* 2926 */       e.printStackTrace();
/* 2927 */       request.setAttribute("head", "Date and Time for");
/* 2928 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.dateandtime.title"));
/* 2929 */       Error.append(e.getMessage());
/* 2930 */       request.setAttribute("Error", Error);
/* 2931 */       request.setAttribute("logMessage", "" + e);
/* 2932 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 2935 */       as400.disconnectAllServices();
/* 2936 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 2939 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward allocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 2946 */     Map data = new HashMap();
/* 2947 */     List a = new ArrayList();
/* 2948 */     StringBuffer Error = new StringBuffer();
/* 2949 */     AS400 as400 = null;
/* 2950 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/* 2954 */       String resid = request.getParameter("resourceid");
/* 2955 */       dispname = null;
/* 2956 */       String username = null;
/* 2957 */       String password = null;
/*      */       
/* 2959 */       boolean admi = false;
/* 2960 */       boolean opr = false;
/* 2961 */       String persc = "";
/* 2962 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400"))) {
/* 2963 */         admi = true;
/* 2964 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowAL")) {
/* 2965 */         opr = true;
/*      */       }
/* 2967 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 2968 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/* 2971 */       String monname = " ";String query1 = "";
/* 2972 */       query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 2973 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2974 */       if (rs.next()) {
/* 2975 */         monname = rs.getString("RESOURCENAME");
/* 2976 */         dispname = rs.getString("DISPLAYNAME");
/* 2977 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 2979 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2981 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 2982 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 2983 */       if (rs.next()) {
/* 2984 */         username = rs.getString("USERNAME");
/* 2985 */         password = rs.getString("pass");
/*      */       }
/* 2987 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 2989 */       as400 = new AS400(monname, username, password);
/* 2990 */       as400.setGuiAvailable(false);
/* 2991 */       SocketProperties socket = new SocketProperties();
/* 2992 */       socket.setSoTimeout(300000);
/* 2993 */       as400.setSocketProperties(socket);
/*      */       
/* 2995 */       SystemValue sv = new SystemValue();
/* 2996 */       SystemValueList svl = new SystemValueList(as400);
/* 2997 */       Vector v = svl.getGroup(0);
/* 2998 */       Enumeration list = v.elements();
/* 2999 */       while (list.hasMoreElements()) {
/* 3000 */         Map p = new HashMap();
/* 3001 */         StringBuffer stract = new StringBuffer();
/* 3002 */         sv = (SystemValue)list.nextElement();
/* 3003 */         if (sv.getType() == 2) {
/* 3004 */           p.put("name", sv.getName());
/* 3005 */           p.put("value", sv.getValue().toString());
/* 3006 */           p.put("description", sv.getDescription());
/* 3007 */           p.put("type", "1");
/* 3008 */         } else if (sv.getType() == 3) {
/* 3009 */           p.put("name", sv.getName());
/* 3010 */           p.put("value", sv.getValue().toString());
/* 3011 */           p.put("description", sv.getDescription());
/* 3012 */           p.put("type", "2");
/* 3013 */         } else if (sv.getType() == 1) {
/* 3014 */           p.put("name", sv.getName());
/* 3015 */           p.put("value", sv.getValue().toString());
/* 3016 */           p.put("description", sv.getDescription());
/* 3017 */           p.put("type", "3");
/* 3018 */         } else if (sv.getType() == 5) {
/* 3019 */           p.put("name", sv.getName());
/* 3020 */           p.put("value", ((Date)sv.getValue()).toString());
/* 3021 */           p.put("description", sv.getDescription());
/* 3022 */           p.put("type", "4");
/*      */         }
/* 3024 */         else if (sv.getType() == 4) {
/* 3025 */           List<String> stringlist = Arrays.asList((String[])sv.getValue());
/*      */           
/* 3027 */           for (int i = 0; i < stringlist.size(); i++) {
/* 3028 */             String tmp = (String)stringlist.get(i);
/* 3029 */             tmp = tmp.trim();
/*      */             
/* 3031 */             if (i < stringlist.size() - 1) {
/* 3032 */               stract.append(tmp + ",");
/*      */             } else {
/* 3034 */               stract.append(tmp);
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3039 */           String stracts = stract.toString();
/* 3040 */           p.put("name", sv.getName());
/* 3041 */           p.put("value", stracts);
/* 3042 */           p.put("description", sv.getDescription());
/* 3043 */           p.put("type", "5");
/*      */         }
/*      */         
/* 3046 */         a.add(p);
/* 3047 */         data.put("systemvalue", a);
/*      */       }
/* 3049 */       request.setAttribute("data", data);
/* 3050 */       request.setAttribute("head", "Allocation for");
/* 3051 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.allocation.title"));
/*      */     } catch (Exception e) { String dispname;
/* 3053 */       e.printStackTrace();
/* 3054 */       request.setAttribute("head", "Allocation for");
/* 3055 */       request.setAttribute("title", FormatUtil.getString("am.webclient.as400.allocation.title"));
/* 3056 */       Error.append(e.getMessage());
/* 3057 */       request.setAttribute("Error", Error);
/* 3058 */       request.setAttribute("logMessage", "" + e);
/* 3059 */       return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */     }
/*      */     finally {
/* 3062 */       as400.disconnectAllServices();
/* 3063 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3066 */     return new ActionForward("/jsp/as400/systemvalue.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward spoolAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3071 */     Map data = new HashMap();
/* 3072 */     List a = new ArrayList();
/*      */     try {
/* 3074 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 3075 */       response.setContentType("text/html; charset=UTF-8");
/* 3076 */       String resid = request.getParameter("resourceid");
/* 3077 */       String spool = request.getParameter("rowids");
/* 3078 */       String fn = request.getParameter("fn");
/* 3079 */       AS400 as400 = null;
/* 3080 */       Hashtable as400Response = new Hashtable();
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 3085 */         String name = "";String jobName = "";String jobUser = "";String sysname = "";String sdate = "";String stime = "";
/* 3086 */         int number = 0;int id = 0;
/* 3087 */         String dispname = "";
/* 3088 */         String username = "";
/* 3089 */         String password = "";
/* 3090 */         String monname = "";
/* 3091 */         String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 3092 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 3093 */         if (rs.next()) {
/* 3094 */           monname = rs.getString("RESOURCENAME");
/* 3095 */           dispname = rs.getString("DISPLAYNAME");
/* 3096 */           request.setAttribute("dispname", dispname);
/*      */         }
/* 3098 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 3100 */         query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3101 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 3102 */         if (rs.next()) {
/* 3103 */           username = rs.getString("USERNAME");
/* 3104 */           password = rs.getString("pass");
/*      */         }
/* 3106 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 3108 */         as400 = new AS400(monname, username, password);
/* 3109 */         as400.setGuiAvailable(false);
/* 3110 */         SocketProperties socket = new SocketProperties();
/* 3111 */         socket.setSoTimeout(60000);
/* 3112 */         as400.setSocketProperties(socket);
/*      */         
/* 3114 */         String query = "select NAME,NUMBER,JOB_NAME,JOB_OWNER,JOB_NUMBER,ID from AM_AS400_SPOOL where RESOURCEID=" + resid + " and ID in(" + spool + ")";
/* 3115 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3116 */         while (rs.next()) {
/* 3117 */           name = rs.getString("NAME");
/* 3118 */           number = rs.getInt("NUMBER");
/* 3119 */           jobName = rs.getString("JOB_NAME");
/* 3120 */           jobUser = rs.getString("JOB_OWNER");
/* 3121 */           String jobNumber = rs.getString("JOB_NUMBER");
/* 3122 */           Integer num = Integer.valueOf(Integer.parseInt(jobNumber));
/* 3123 */           String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/* 3124 */           jobNumber = String.format(format, new Object[] { num });
/* 3125 */           id = rs.getInt("ID");
/* 3126 */           SpooledFile sp = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber);
/*      */           
/* 3128 */           sysname = sp.getJobSysName();
/* 3129 */           sdate = sp.getCreateDate();
/* 3130 */           stime = sp.getCreateTime();
/*      */           ActionForward localActionForward;
/* 3132 */           if (fn.equals("Delete")) {
/* 3133 */             SpooledFile spf = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber, sysname, sdate, stime);
/*      */             try
/*      */             {
/* 3136 */               spf.delete();
/*      */               
/* 3138 */               String updqry = "delete from AM_AS400_SPOOL where ID=" + id;
/* 3139 */               toinsert.addBatch(updqry);
/* 3140 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing spool action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 3142 */               setIsActionMade(true);
/* 3143 */               as400Response.put("failed", de.getMessage());
/* 3144 */               setAs400Responsess(as400Response);
/* 3145 */               return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */             }
/* 3147 */           } else if (fn.equals("Hold")) {
/* 3148 */             SpooledFile spf = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber, sysname, sdate, stime);
/*      */             try {
/* 3150 */               spf.hold("*IMMED");
/*      */               
/* 3152 */               String updqry = "update AM_AS400_SPOOL set STATUS='*HELD' where ID=" + id;
/* 3153 */               toinsert.addBatch(updqry);
/* 3154 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing spool action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 3156 */               de.printStackTrace();
/* 3157 */               setIsActionMade(true);
/* 3158 */               as400Response.put("failed", de.getMessage());
/* 3159 */               setAs400Responsess(as400Response);
/* 3160 */               return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */             }
/* 3162 */           } else if (fn.equals("Release")) {
/* 3163 */             SpooledFile spf = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber, sysname, sdate, stime);
/*      */             try {
/* 3165 */               spf.release();
/* 3166 */               String updqry = "update AM_AS400_SPOOL set STATUS='*READY' where ID=" + id;
/* 3167 */               toinsert.addBatch(updqry);
/* 3168 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing spool action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 3170 */               setIsActionMade(true);
/* 3171 */               as400Response.put("failed", de.getMessage());
/* 3172 */               setAs400Responsess(as400Response);
/* 3173 */               return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */             }
/*      */           }
/* 3176 */           else if (fn.equals("MoveToTop")) {
/*      */             try {
/* 3178 */               SpooledFile spf = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber, sysname, sdate, stime);
/* 3179 */               spf.moveToTop();
/* 3180 */               AMLog.debug("AS400 Action: executing spool action move to top - for Host: " + monname);
/*      */             } catch (Exception de) {
/* 3182 */               setIsActionMade(true);
/* 3183 */               as400Response.put("failed", de.getMessage());
/* 3184 */               setAs400Responsess(as400Response);
/* 3185 */               return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid, true);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 3192 */           toinsert.executeBatch();
/* 3193 */           toinsert.close();
/*      */         } catch (Exception e) {
/* 3195 */           AMLog.debug("AS400 Action: Problem while executing batch statements in spool actions due to error: " + e.getMessage() + " for resid" + resid);
/*      */         }
/*      */         finally {
/* 3198 */           toinsert.close();
/* 3199 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         
/* 3202 */         setIsActionMade(true);
/* 3203 */         as400Response.put("success", "Spool Action Executed");
/* 3204 */         setAs400Responsess(as400Response);
/*      */       } catch (Exception es) {
/* 3206 */         AMLog.debug("AS400 Action: Problem while performing spool action due to error: " + es.getMessage() + " for resid" + resid);
/* 3207 */         es.printStackTrace();
/*      */       } finally {
/* 3209 */         as400.disconnectAllServices();
/*      */       }
/*      */       
/* 3212 */       return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=6", true);
/*      */     }
/*      */     catch (Exception e) {
/* 3215 */       e.printStackTrace();
/*      */     }
/* 3217 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward querydetailsl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3226 */     ResultSet rs = null;
/*      */     try {
/* 3228 */       Map data = new HashMap();
/* 3229 */       a = new ArrayList();
/* 3230 */       String resid = request.getParameter("resourceid");
/*      */       
/*      */ 
/* 3233 */       boolean admi = false;
/* 3234 */       boolean opr = false;
/* 3235 */       String persc = "";
/* 3236 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400")))
/*      */       {
/* 3238 */         admi = true;
/* 3239 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCMD")) {
/* 3240 */         opr = true;
/*      */       }
/*      */       
/* 3243 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 3244 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3249 */       Map p1 = new HashMap();
/* 3250 */       a.add(p1);
/* 3251 */       data.put("command", a);
/*      */       
/* 3253 */       String query1 = "select COMMAND from AM_AS400_COMMANDS where resourceid=" + resid;
/* 3254 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3255 */       while (rs.next()) {
/* 3256 */         Map p = new HashMap();
/* 3257 */         p.put("cmd", rs.getString("COMMAND"));
/* 3258 */         a.add(p);
/* 3259 */         data.put("command", a);
/*      */       }
/* 3261 */       AMConnectionPool.closeStatement(rs);
/* 3262 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/*      */       List a;
/* 3266 */       e.printStackTrace();
/* 3267 */       request.setAttribute("CommandMessage", "" + e);
/* 3268 */       return new ActionForward("/jsp/as400/command.jsp");
/*      */     } finally {
/* 3270 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3273 */     return new ActionForward("/jsp/as400/command.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward spoolview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3282 */     AS400 as400 = null;
/*      */     try {
/* 3284 */       response.setContentType("text/html; charset=UTF-8");
/* 3285 */       String resid = request.getParameter("resourceid");
/* 3286 */       spool = request.getParameter("rowids");
/*      */       
/* 3288 */       ResultSet rs = null;
/*      */       
/* 3290 */       String name = "";String jobName = "";String jobUser = "";
/* 3291 */       int number = 0;int id = 0;
/*      */       
/*      */ 
/* 3294 */       String dispname = null;
/* 3295 */       String username = null;
/* 3296 */       String password = null;
/* 3297 */       String monname = " ";
/* 3298 */       String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + resid;
/* 3299 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3300 */       if (rs.next()) {
/* 3301 */         monname = rs.getString("RESOURCENAME");
/* 3302 */         dispname = rs.getString("DISPLAYNAME");
/* 3303 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 3305 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3307 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3308 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3309 */       if (rs.next()) {
/* 3310 */         username = rs.getString("USERNAME");
/* 3311 */         password = rs.getString("pass");
/*      */       }
/* 3313 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3315 */       as400 = new AS400(monname, username, password);
/* 3316 */       as400.setGuiAvailable(false);
/* 3317 */       SocketProperties socket = new SocketProperties();
/* 3318 */       socket.setSoTimeout(300000);
/* 3319 */       as400.setSocketProperties(socket);
/*      */       
/*      */ 
/* 3322 */       String query = "select * from AM_AS400_SPOOL where RESOURCEID=" + resid + " and ID in(" + spool + ")";
/* 3323 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3324 */       if (rs.next()) {
/* 3325 */         name = rs.getString("NAME");
/* 3326 */         number = rs.getInt("NUMBER");
/* 3327 */         jobName = rs.getString("JOB_NAME");
/* 3328 */         jobUser = rs.getString("JOB_OWNER");
/* 3329 */         String jobNumber = rs.getString("JOB_NUMBER");
/* 3330 */         Integer num = Integer.valueOf(Integer.parseInt(jobNumber));
/* 3331 */         String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/* 3332 */         jobNumber = String.format(format, new Object[] { num });
/* 3333 */         id = rs.getInt("ID");
/* 3334 */         SpooledFile sp = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber);
/*      */         
/*      */ 
/*      */ 
/* 3338 */         byte[] buf = new byte['翿'];
/* 3339 */         PrintParameterList prtParm = new PrintParameterList();
/* 3340 */         prtParm.setParameter(65, "*WSCST");
/* 3341 */         prtParm.setParameter(-8, "/QSYS.LIB/QWPDEFAULT.WSCST");
/*      */         
/* 3343 */         StringBuffer sbuf = new StringBuffer();
/* 3344 */         int bytesRead = 0;
/* 3345 */         SpooledFile splf = new SpooledFile(as400, name, number, jobName, jobUser, jobNumber);
/*      */         
/* 3347 */         PrintObjectTransformedInputStream in = null;
/*      */         try {
/* 3349 */           in = splf.getTransformedInputStream(prtParm);
/*      */         } catch (Exception es) {
/* 3351 */           es.printStackTrace();
/* 3352 */           request.setAttribute("error", "" + es.getMessage());
/* 3353 */           ActionForward localActionForward = new ActionForward("/jsp/as400/spoolview.jsp");
/*      */           
/* 3355 */           AMConnectionPool.closeStatement(rs);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3379 */           return localActionForward;
/*      */         }
/*      */         finally
/*      */         {
/* 3355 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         
/*      */ 
/* 3359 */         AS400BidiTransform abt = new AS400BidiTransform(424);
/* 3360 */         String dst = abt.toAS400Layout("some bidirectional string");
/*      */         do
/*      */         {
/* 3363 */           bytesRead = in.read(buf);
/* 3364 */           if (bytesRead != -1) {
/* 3365 */             sbuf.append(new String(buf, 1, bytesRead));
/*      */           }
/* 3367 */         } while (bytesRead != -1);
/* 3368 */         request.setAttribute("sbuf", sbuf);
/* 3369 */         in.close();
/* 3370 */         output = sbuf.toString();
/*      */       }
/*      */     } catch (Exception e) { String spool;
/*      */       String output;
/* 3374 */       e.printStackTrace();
/* 3375 */       request.setAttribute("logMessage", "" + e);
/* 3376 */       return new ActionForward("/jsp/as400/spoolview.jsp");
/*      */     }
/*      */     finally {
/* 3379 */       as400.disconnectAllServices();
/*      */     }
/*      */     
/* 3382 */     return new ActionForward("/jsp/as400/spoolview.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward msgAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3387 */     Map data = new HashMap();
/* 3388 */     List a = new ArrayList();
/*      */     try {
/* 3390 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 3391 */       response.setContentType("text/html; charset=UTF-8");
/* 3392 */       String resid = request.getParameter("resourceid");
/* 3393 */       String spool = request.getParameter("rowids");
/* 3394 */       String fn = request.getParameter("fn");
/* 3395 */       String msgFilter = request.getParameter("msgFilter");
/* 3396 */       Hashtable as400Response = new Hashtable();
/* 3397 */       AS400 as400 = null;
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 3402 */         String key = "";
/* 3403 */         int id = 0;
/*      */         
/* 3405 */         String dispname = null;
/* 3406 */         String username = null;
/* 3407 */         String password = null;
/* 3408 */         String monname = " ";
/* 3409 */         String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where resourceid=" + resid;
/* 3410 */         ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/* 3411 */         if (rs.next()) {
/* 3412 */           monname = rs.getString("RESOURCENAME");
/* 3413 */           dispname = rs.getString("DISPLAYNAME");
/* 3414 */           request.setAttribute("dispname", dispname);
/*      */         }
/* 3416 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 3418 */         query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3419 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 3420 */         if (rs.next()) {
/* 3421 */           username = rs.getString("USERNAME");
/* 3422 */           password = rs.getString("pass");
/*      */         }
/* 3424 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 3426 */         String msgq = (String)Constants.AS400Props.get("messageQueue");
/*      */         
/* 3428 */         as400 = new AS400(monname, username, password);
/* 3429 */         as400.setGuiAvailable(false);
/* 3430 */         SocketProperties socket = new SocketProperties();
/* 3431 */         socket.setSoTimeout(60000);
/* 3432 */         as400.setSocketProperties(socket);
/* 3433 */         MessageQueue mq = new MessageQueue(as400);
/* 3434 */         mq.setPath(msgq);
/*      */         
/*      */ 
/*      */         ActionForward localActionForward1;
/*      */         
/* 3439 */         if (fn.equals("Remove ALL")) {
/*      */           try {
/* 3441 */             mq.remove("*ALL");
/* 3442 */             String delqry = "delete from AM_AS400_MESSAGES where resourceid=" + resid;
/* 3443 */             toinsert.addBatch(delqry);
/* 3444 */             AMLog.debug("AS400 Action: Delete query " + delqry + " after executing message action Remove ALL from Message queue for Host: " + monname);
/*      */           } catch (Exception de) {
/* 3446 */             if (msgFilter.equals("true")) {
/* 3447 */               request.setAttribute("error", de.getMessage());
/* 3448 */               return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg"); }
/* 3449 */             if (msgFilter.equals("false")) {
/* 3450 */               setIsActionMade(true);
/* 3451 */               as400Response.put("failed", de.getMessage());
/* 3452 */               setAs400Responsess(as400Response);
/* 3453 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */             }
/*      */           }
/* 3456 */         } else if (fn.equals("Remove KEEP_UNANSWERED")) {
/*      */           try {
/* 3458 */             mq.remove("*KEEPUNANS");
/* 3459 */             AMLog.debug("AS400 Action: Exceuted message action Remove KEEP_UNANSWERED from message queue for Host: " + monname);
/*      */           }
/*      */           catch (Exception de) {
/* 3462 */             if (msgFilter.equals("true")) {
/* 3463 */               request.setAttribute("error", de.getMessage());
/* 3464 */               return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg"); }
/* 3465 */             if (msgFilter.equals("false")) {
/* 3466 */               setIsActionMade(true);
/* 3467 */               as400Response.put("failed", de.getMessage());
/* 3468 */               setAs400Responsess(as400Response);
/* 3469 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */             }
/*      */             
/*      */           }
/* 3473 */         } else if (fn.equals("Remove NEW")) {
/*      */           try {
/* 3475 */             mq.remove("*NEW");
/* 3476 */             AMLog.debug("AS400 Action: Exceuted message action Remove NEW from Messageq queue for Host: " + monname);
/*      */           } catch (Exception de) {
/* 3478 */             if (msgFilter.equals("true")) {
/* 3479 */               request.setAttribute("error", de.getMessage());
/* 3480 */               return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg"); }
/* 3481 */             if (msgFilter.equals("false")) {
/* 3482 */               setIsActionMade(true);
/* 3483 */               as400Response.put("failed", de.getMessage());
/* 3484 */               setAs400Responsess(as400Response);
/* 3485 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */             }
/*      */             
/*      */           }
/* 3489 */         } else if (fn.equals("Remove OLD")) {
/*      */           try {
/* 3491 */             mq.remove("*OLD");
/* 3492 */             AMLog.debug("AS400 Action: Exceuted message action Remove OLD from Messageq queue for Host: " + monname);
/*      */           } catch (Exception de) {
/* 3494 */             if (msgFilter.equals("true")) {
/* 3495 */               request.setAttribute("error", de.getMessage());
/* 3496 */               return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg"); }
/* 3497 */             if (msgFilter.equals("false")) {
/* 3498 */               setIsActionMade(true);
/* 3499 */               as400Response.put("failed", de.getMessage());
/* 3500 */               setAs400Responsess(as400Response);
/* 3501 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 3509 */         if (fn.equals("Remove"))
/*      */         {
/* 3511 */           String query = "select * from AM_AS400_MESSAGES where resourceid=" + resid + " and ID in(" + spool + ")";
/* 3512 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 3513 */           while (rs.next()) {
/* 3514 */             key = rs.getString("MSGKEY");
/* 3515 */             id = rs.getInt("ID");
/*      */             
/* 3517 */             byte[] l = key.getBytes();
/*      */             
/* 3519 */             if (fn.equals("Remove")) {
/*      */               try {
/* 3521 */                 mq.remove(l);
/* 3522 */                 String delqry = "delete from AM_AS400_MESSAGES where ID=" + id;
/* 3523 */                 toinsert.addBatch(delqry);
/* 3524 */                 AMLog.debug("AS400 Action: Delete query " + delqry + " after executing message action Remove msg from Message queue for Host: " + monname);
/*      */               } catch (Exception de) { ActionForward localActionForward2;
/* 3526 */                 if (msgFilter.equals("true")) {
/* 3527 */                   request.setAttribute("error", de.getMessage());
/* 3528 */                   return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg"); }
/* 3529 */                 if (msgFilter.equals("false")) {
/* 3530 */                   setIsActionMade(true);
/* 3531 */                   as400Response.put("failed", de.getMessage());
/* 3532 */                   setAs400Responsess(as400Response);
/* 3533 */                   return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 3540 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         
/*      */         try
/*      */         {
/* 3545 */           toinsert.executeBatch();
/*      */ 
/*      */         }
/*      */         catch (Exception e) {}finally
/*      */         {
/* 3550 */           toinsert.close();
/* 3551 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try {}catch (Exception exp) {}
/*      */         
/*      */ 
/* 3559 */         setIsActionMade(true);
/* 3560 */         as400Response.put("success", "Message Action Executed");
/* 3561 */         setAs400Responsess(as400Response);
/*      */       }
/*      */       catch (Exception es) {
/* 3564 */         AMLog.debug("AS400 Action: Problem while performing message action due to error: " + es.getMessage() + " for resid" + resid);
/* 3565 */         es.printStackTrace();
/*      */       } finally {
/* 3567 */         as400.disconnectAllServices();
/*      */       }
/*      */       
/* 3570 */       if (msgFilter.equals("true"))
/* 3571 */         return new ActionForward("/as400.do?method=messageFilter&resourceid=" + resid + "&status=allmsg");
/* 3572 */       if (msgFilter.equals("false")) {
/* 3573 */         return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=5", true);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 3577 */       e.printStackTrace();
/* 3578 */       AMLog.debug("AS400 Action: Problem while performing message action due to error: " + e.getMessage());
/*      */     }
/* 3580 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward msgview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3588 */     ResultSet rs = null;
/* 3589 */     String query = "";
/* 3590 */     String dispname = "";
/* 3591 */     String monname = "";
/*      */     try {
/* 3593 */       response.setContentType("text/html; charset=UTF-8");
/* 3594 */       String resid = request.getParameter("resourceid");
/* 3595 */       spool = request.getParameter("rowids");
/*      */       
/* 3597 */       String query1 = "select RESOURCENAME,displayname from AM_ManagedObject where resourceid=" + resid;
/* 3598 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3599 */       if (rs.next()) {
/* 3600 */         monname = rs.getString("RESOURCENAME");
/* 3601 */         dispname = rs.getString("displayname");
/* 3602 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 3604 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3606 */       query = "select * from AM_AS400_MESSAGES where resourceid=" + resid + " and ID in(" + spool + ")";
/* 3607 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 3608 */       if (rs.next())
/*      */       {
/* 3610 */         request.setAttribute("MSG_ID", rs.getString("MSG_ID"));
/* 3611 */         request.setAttribute("MESSAGE", rs.getString("MESSAGE"));
/* 3612 */         request.setAttribute("DATE", rs.getString("DATE"));
/* 3613 */         request.setAttribute("ALERTOPTION", rs.getString("ALERTOPTION"));
/* 3614 */         request.setAttribute("CURRENTUSER", rs.getString("CURRENTUSER"));
/* 3615 */         request.setAttribute("FROMJOBNUM", rs.getString("FROMJOBNUM"));
/* 3616 */         request.setAttribute("FROMPGM", rs.getString("FROMPGM"));
/* 3617 */         request.setAttribute("REPLYSTATUS", rs.getString("REPLYSTATUS"));
/* 3618 */         request.setAttribute("FILENAME", rs.getString("FILENAME"));
/* 3619 */         request.setAttribute("MSGHELP", rs.getString("MSGHELP"));
/* 3620 */         request.setAttribute("MSG_QUEUE", rs.getString("MSGKEY"));
/*      */       }
/*      */     } catch (Exception e) {
/*      */       String spool;
/* 3624 */       e.printStackTrace();
/* 3625 */       request.setAttribute("logMessage", "" + e);
/* 3626 */       return new ActionForward("/jsp/as400/msgview.jsp");
/*      */     } finally {
/* 3628 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3631 */     return new ActionForward("/jsp/as400/msgview.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward modifysystemvalue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3636 */     String resourcetype = request.getParameter("type");
/* 3637 */     String displayname = request.getParameter("displayname");
/* 3638 */     String port = request.getParameter("port");
/* 3639 */     String resid = request.getParameter("resourceid");
/* 3640 */     String dtype = request.getParameter("dtype");
/* 3641 */     String errorMsg = "";
/* 3642 */     AS400 as400 = null;
/* 3643 */     ResultSet rs = null;
/*      */     
/*      */     try
/*      */     {
/* 3647 */       String dispname = null;
/* 3648 */       String username = null;
/* 3649 */       String password = null;
/* 3650 */       String monname = " ";
/* 3651 */       String query1 = "select RESOURCENAME,displayname from AM_ManagedObject where resourceid=" + resid;
/* 3652 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3653 */       if (rs.next()) {
/* 3654 */         monname = rs.getString("RESOURCENAME");
/* 3655 */         dispname = rs.getString("displayname");
/* 3656 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 3658 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3660 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3661 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3662 */       if (rs.next()) {
/* 3663 */         username = rs.getString("USERNAME");
/* 3664 */         password = rs.getString("pass");
/*      */       }
/*      */       
/* 3667 */       as400 = new AS400(monname, username, password);
/* 3668 */       as400.setGuiAvailable(false);
/* 3669 */       SocketProperties socket = new SocketProperties();
/* 3670 */       socket.setSoTimeout(60000);
/* 3671 */       as400.setSocketProperties(socket);
/*      */       
/*      */ 
/*      */ 
/* 3675 */       if (dtype.equals("1")) {
/*      */         try {
/* 3677 */           SystemValue sv = new SystemValue(as400, displayname);
/* 3678 */           sv.setValue(new java.math.BigDecimal(port));
/*      */         }
/*      */         catch (Exception e) {
/* 3681 */           AMLog.debug("Set SYSTEM VALUE :decimal" + e.getMessage());
/* 3682 */           errorMsg = e.getMessage();
/*      */         }
/*      */         
/* 3685 */       } else if (dtype.equals("2")) {
/*      */         try {
/* 3687 */           SystemValue sv = new SystemValue(as400, displayname);
/* 3688 */           int iport = Integer.parseInt(port);
/* 3689 */           sv.setValue(Integer.valueOf(iport));
/*      */         } catch (Exception e) {
/* 3691 */           AMLog.debug("Set SYSTEM VALUE :integer" + e.getMessage());
/* 3692 */           errorMsg = e.getMessage();
/*      */         }
/*      */         
/* 3695 */       } else if (dtype.equals("3")) {
/*      */         try {
/* 3697 */           SystemValue sv = new SystemValue(as400, displayname);
/* 3698 */           sv.setValue(port);
/*      */         } catch (Exception e) {
/* 3700 */           AMLog.debug("Set SYSTEM VALUE :string" + e.getMessage());
/* 3701 */           errorMsg = e.getMessage();
/*      */         }
/*      */         
/* 3704 */       } else if (dtype.equals("4")) {
/*      */         try
/*      */         {
/* 3707 */           DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
/* 3708 */           Date today = df.parse(port);
/*      */           
/* 3710 */           SystemValue sv = new SystemValue(as400, displayname);
/* 3711 */           sv.setValue(today);
/*      */         } catch (Exception e) {
/* 3713 */           AMLog.debug("Set SYSTEM VALUE :date" + e.getMessage());
/* 3714 */           errorMsg = e.getMessage();
/*      */         }
/*      */         
/* 3717 */       } else if (dtype.equals("5")) {
/*      */         try {
/* 3719 */           String[] out = port.split(",");
/* 3720 */           SystemValue sv = new SystemValue(as400, displayname);
/* 3721 */           sv.setValue(out);
/*      */         } catch (Exception e) {
/* 3723 */           AMLog.debug("Set SYSTEM VALUE :Array" + e.getMessage());
/* 3724 */           errorMsg = e.getMessage();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 3730 */       exc.printStackTrace();
/* 3731 */       errorMsg = exc.getMessage();
/*      */     } finally {
/* 3733 */       as400.disconnectAllServices();
/* 3734 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3737 */     request.setAttribute("Error", errorMsg);
/* 3738 */     request.setAttribute("Msg", "System Value Updated Successfully");
/*      */     
/*      */ 
/*      */ 
/* 3742 */     if (resourcetype.equals("Messaging and Logging for"))
/* 3743 */       return new ActionForward("/as400.do?method=msgandlogging&resourceid=" + resid);
/* 3744 */     if (resourcetype.equals("Network Attributes for"))
/* 3745 */       return new ActionForward("/as400.do?method=systemvalue&resourceid=" + resid);
/* 3746 */     if (resourcetype.equals("System Control for"))
/* 3747 */       return new ActionForward("/as400.do?method=systemcontrol&resourceid=" + resid);
/* 3748 */     if (resourcetype.equals("Storage for"))
/* 3749 */       return new ActionForward("/as400.do?method=storage&resourceid=" + resid);
/* 3750 */     if (resourcetype.equals("Security for"))
/* 3751 */       return new ActionForward("/as400.do?method=security&resourceid=" + resid);
/* 3752 */     if (resourcetype.equals("Library List for"))
/* 3753 */       return new ActionForward("/as400.do?method=librarylist&resourceid=" + resid);
/* 3754 */     if (resourcetype.equals("Date and Time for")) {
/* 3755 */       return new ActionForward("/as400.do?method=dateandtime&resourceid=" + resid);
/*      */     }
/*      */     
/* 3758 */     return new ActionForward("/as400.do?method=allocation&resourceid=" + resid);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward querydetaild(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3767 */     ResultSet rs = null;
/*      */     try {
/* 3769 */       Map data = new HashMap();
/* 3770 */       a = new ArrayList();
/* 3771 */       StringBuffer CommandMessage = new StringBuffer();
/* 3772 */       AS400 as400 = null;
/* 3773 */       AMConnectionPool update = AMConnectionPool.getInstance();
/* 3774 */       String resid = request.getParameter("resourceid");
/* 3775 */       AMActionForm rf = (AMActionForm)form;
/* 3776 */       String Textarea = rf.getDescription();
/*      */       
/* 3778 */       boolean admi = false;
/* 3779 */       boolean opr = false;
/* 3780 */       String persc = "";
/* 3781 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400")))
/*      */       {
/* 3783 */         admi = true;
/* 3784 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCMD")) {
/* 3785 */         opr = true;
/*      */       }
/*      */       
/* 3788 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 3789 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3794 */       String dispname = null;
/* 3795 */       String username = null;
/* 3796 */       String password = null;
/* 3797 */       String monname = " ";String query1 = "";
/* 3798 */       query1 = "select RESOURCENAME,displayname from AM_ManagedObject where resourceid=" + resid;
/* 3799 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3800 */       if (rs.next()) {
/* 3801 */         monname = rs.getString("RESOURCENAME");
/* 3802 */         dispname = rs.getString("displayname");
/* 3803 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 3805 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3807 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3808 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3809 */       if (rs.next()) {
/* 3810 */         username = rs.getString("USERNAME");
/* 3811 */         password = rs.getString("pass");
/*      */       }
/* 3813 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3815 */       if ((Textarea != null) && (!Textarea.trim().equals("")))
/*      */       {
/* 3817 */         AMLog.debug("AS400 Action:the AS400 Command query is :" + Textarea);
/* 3818 */         String Textarea1 = Textarea.toLowerCase();
/*      */         
/* 3820 */         AMLog.debug("AS400CommandCall  executeCommand :");
/*      */         
/* 3822 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */         try
/*      */         {
/* 3825 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_COMMANDS  where RESOURCEID=" + resid + " and COMMAND='" + Textarea + "'");
/* 3826 */           CommandMessage.append("Command :" + Textarea + " Deleted Successfully");
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3831 */           AMLog.debug("AS400 admin query : Problem in Deleting AS400 Details into Database Server due to error : " + e.getMessage());
/* 3832 */           e.printStackTrace();
/* 3833 */           CommandMessage.append("Error in Deleting:" + e.getMessage());
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 3838 */         request.setAttribute("CommandMessage", CommandMessage);
/*      */         
/*      */ 
/*      */ 
/* 3842 */         Map p1 = new HashMap();
/* 3843 */         a.add(p1);
/* 3844 */         data.put("command", a);
/*      */         
/* 3846 */         query1 = "select COMMAND from AM_AS400_COMMANDS where resourceid=" + resid;
/* 3847 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 3848 */         while (rs.next()) {
/* 3849 */           Map p = new HashMap();
/* 3850 */           p.put("cmd", rs.getString("COMMAND"));
/* 3851 */           a.add(p);
/* 3852 */           data.put("command", a);
/*      */         }
/* 3854 */         AMConnectionPool.closeStatement(rs);
/* 3855 */         request.setAttribute("data", data);
/*      */         
/*      */ 
/* 3858 */         rf.setCommand(Textarea);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*      */       List a;
/* 3863 */       e.printStackTrace();
/* 3864 */       request.setAttribute("CommandMessage", "" + e);
/* 3865 */       return new ActionForward("/jsp/as400/command.jsp");
/*      */     } finally {
/* 3867 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3870 */     return new ActionForward("/jsp/as400/command.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward querydetaildall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 3880 */     ResultSet rs = null;
/*      */     try {
/* 3882 */       Map data = new HashMap();
/* 3883 */       a = new ArrayList();
/* 3884 */       StringBuffer CommandMessage = new StringBuffer();
/* 3885 */       AS400 as400 = null;
/* 3886 */       AMConnectionPool update = AMConnectionPool.getInstance();
/* 3887 */       String resid = request.getParameter("resourceid");
/* 3888 */       AMActionForm rf = (AMActionForm)form;
/* 3889 */       String Textarea = rf.getDescription();
/*      */       
/*      */ 
/* 3892 */       boolean admi = false;
/* 3893 */       boolean opr = false;
/* 3894 */       String persc = "";
/* 3895 */       if ((request.isUserInRole("ADMIN")) && (DBUtil.getGlobalConfigValueasBoolean("allowAS400")))
/*      */       {
/* 3897 */         admi = true;
/* 3898 */       } else if (DBUtil.getGlobalConfigValueasBoolean("allowCMD")) {
/* 3899 */         opr = true;
/*      */       }
/* 3901 */       request.setAttribute("admi", Boolean.valueOf(admi));
/* 3902 */       request.setAttribute("opr", Boolean.valueOf(opr));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3907 */       String dispname = null;
/* 3908 */       String username = null;
/* 3909 */       String password = null;
/* 3910 */       String monname = " ";String query1 = "";
/* 3911 */       query1 = "select RESOURCENAME,displayname from AM_ManagedObject where resourceid=" + resid;
/* 3912 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3913 */       if (rs.next()) {
/* 3914 */         monname = rs.getString("RESOURCENAME");
/* 3915 */         dispname = rs.getString("displayname");
/* 3916 */         request.setAttribute("dispname", dispname);
/*      */       }
/* 3918 */       AMConnectionPool.closeStatement(rs);
/*      */       
/* 3920 */       query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 3921 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3922 */       if (rs.next()) {
/* 3923 */         username = rs.getString("USERNAME");
/* 3924 */         password = rs.getString("pass");
/*      */       }
/* 3926 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 3931 */       AMLog.debug("the AS400 Command query is :" + Textarea);
/* 3932 */       String Textarea1 = Textarea.toLowerCase();
/*      */       
/*      */ 
/* 3935 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       try
/*      */       {
/* 3938 */         AMConnectionPool.executeUpdateStmt("delete from AM_AS400_COMMANDS  where RESOURCEID=" + resid);
/* 3939 */         CommandMessage.append("History Deleted Successfully");
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3944 */         AMLog.debug("AS400 admin query : Problem in Deleting AS400 Details into Database Server due to error : " + e.getMessage());
/* 3945 */         e.printStackTrace();
/* 3946 */         CommandMessage.append("Error in Deleting:" + e.getMessage());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3953 */       request.setAttribute("CommandMessage", CommandMessage);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3959 */       Map p1 = new HashMap();
/* 3960 */       a.add(p1);
/* 3961 */       data.put("command", a);
/*      */       
/* 3963 */       query1 = "select COMMAND from AM_AS400_COMMANDS where resourceid=" + resid;
/* 3964 */       rs = AMConnectionPool.executeQueryStmt(query1);
/* 3965 */       while (rs.next()) {
/* 3966 */         Map p = new HashMap();
/* 3967 */         p.put("cmd", rs.getString("COMMAND"));
/* 3968 */         a.add(p);
/* 3969 */         data.put("command", a);
/*      */       }
/* 3971 */       AMConnectionPool.closeStatement(rs);
/* 3972 */       request.setAttribute("data", data);
/*      */       
/*      */ 
/* 3975 */       rf.setCommand(Textarea);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */       List a;
/* 3980 */       e.printStackTrace();
/* 3981 */       request.setAttribute("CommandMessage", "" + e);
/* 3982 */       return new ActionForward("/jsp/as400/command.jsp");
/*      */     }
/*      */     finally {
/* 3985 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 3988 */     return new ActionForward("/jsp/as400/command.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward jobFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 3993 */     Map map = new HashMap();
/* 3994 */     Map map1 = new HashMap();
/* 3995 */     List b = new ArrayList();
/* 3996 */     ResultSet rs1 = null;
/* 3997 */     ResultSet rs2 = null;
/* 3998 */     ResultSet rs3 = null;
/* 3999 */     AS400 as400 = null;
/* 4000 */     boolean isAllFiter = false;
/*      */     try {
/* 4002 */       response.setContentType("text/html; charset=UTF-8");
/* 4003 */       String resid = null;
/*      */       
/* 4005 */       String status = "";String fetchJobs = "";String monname = "";String fromAS400 = "";String jobName = "*ALL";String jobUser = "*ALL";String jobType = "*";String objName = "";String objLib = "";String referenceColumn = "";String jobrid = "";
/* 4006 */       String category = request.getParameter("catvalue");
/* 4007 */       String monitor = request.getParameter("monvalue");
/*      */       
/* 4009 */       fromAS400 = request.getParameter("fromAS400");
/* 4010 */       jobName = request.getParameter("jname");
/* 4011 */       jobUser = request.getParameter("juser");
/* 4012 */       jobType = request.getParameter("jtype");
/* 4013 */       jobrid = request.getParameter("jresid");
/*      */       
/*      */ 
/*      */ 
/* 4017 */       objName = request.getParameter("objname");
/* 4018 */       objLib = request.getParameter("objlib");
/* 4019 */       referenceColumn = objName + "-" + objLib;
/* 4020 */       if ((category != null) && (category.length() > 0) && (monitor != null) && (monitor.length() > 0)) {
/* 4021 */         resid = monitor;
/* 4022 */         status = category;
/*      */       } else {
/* 4024 */         resid = request.getParameter("resourceid");
/* 4025 */         status = request.getParameter("status");
/*      */       }
/*      */       
/*      */ 
/* 4029 */       String query = "select RESOURCEID,DISPLAYNAME,RESOURCENAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4030 */       StringBuilder resIds = new StringBuilder();
/* 4031 */       query = query + " and RESOURCEID=" + resid;
/* 4032 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 4033 */       while (rs1.next()) {
/* 4034 */         Map h = new HashMap();
/* 4035 */         h.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 4036 */         h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4037 */         b.add(h);
/* 4038 */         monname = rs1.getString("RESOURCENAME");
/* 4039 */         map.put("ResIds", b);
/* 4040 */         if (resid.equals("ALL")) {
/* 4041 */           resIds.append(rs1.getString("RESOURCEID")).append(",");
/*      */         }
/*      */       }
/* 4044 */       AMConnectionPool.closeStatement(rs1);
/* 4045 */       if (resid.equals("ALL")) {
/* 4046 */         isAllFiter = true;
/* 4047 */         resid = resIds.substring(0, resIds.length() - 1);
/*      */       }
/* 4049 */       fetchJobs = (status.equals("active")) || (status.equals("jobq")) || (status.equals("outq")) || (status.equals("jobinqueue")) ? "true" : "false";
/* 4050 */       request.setAttribute("rescolls", map);
/* 4051 */       request.setAttribute("fetchJobs", fetchJobs);
/* 4052 */       map1.put("ResIds", DBUtil.getAllMOs("AS400/iSeries"));
/* 4053 */       request.setAttribute("as400s", map1);
/* 4054 */       query = "";
/*      */       
/* 4056 */       Map data = new HashMap();
/* 4057 */       DataCollectionComponent dcc = new DataCollectionComponent();
/* 4058 */       boolean includeJobname = true;boolean includeUsername = true;boolean includeJobtype = true;boolean includeSubsystem = true;
/* 4059 */       String criticalcondition = null;
/* 4060 */       String criticalvalue = null;
/* 4061 */       String warningcondition = null;
/* 4062 */       String warningvalue = null;
/* 4063 */       String clearcondition = null;
/* 4064 */       String clearvalue = null;
/* 4065 */       boolean isThreshSet = false;
/* 4066 */       if (!isAllFiter)
/*      */       {
/* 4068 */         String thresholdQuery = "select CRITICALTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.CRITICALTHRESHOLDVALUE,WARNINGTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.WARNINGTHRESHOLDVALUE,INFOTHRESHOLDCONDITION,AM_PATTERNMATCHERCONFIG.INFOTHRESHOLDVALUE from AM_THRESHOLDCONFIG,AM_ATTRIBUTETHRESHOLDMAPPER,AM_PATTERNMATCHERCONFIG where AM_THRESHOLDCONFIG.ID=AM_PATTERNMATCHERCONFIG.ID and AM_PATTERNMATCHERCONFIG.ID=AM_ATTRIBUTETHRESHOLDMAPPER.THRESHOLDCONFIGURATIONID and AM_ATTRIBUTETHRESHOLDMAPPER.ID=" + resid + " and AM_ATTRIBUTETHRESHOLDMAPPER.ATTRIBUTE=2837";
/* 4069 */         ResultSet thSet = null;
/*      */         
/*      */         try
/*      */         {
/* 4073 */           thSet = AMConnectionPool.executeQueryStmt(thresholdQuery);
/* 4074 */           if (thSet.next())
/*      */           {
/* 4076 */             criticalcondition = thSet.getString("CRITICALTHRESHOLDCONDITION");
/* 4077 */             criticalvalue = thSet.getString("CRITICALTHRESHOLDVALUE");
/* 4078 */             warningcondition = thSet.getString("WARNINGTHRESHOLDCONDITION");
/* 4079 */             warningvalue = thSet.getString("WARNINGTHRESHOLDVALUE");
/* 4080 */             clearcondition = thSet.getString("INFOTHRESHOLDCONDITION");
/* 4081 */             clearvalue = thSet.getString("INFOTHRESHOLDVALUE");
/* 4082 */             isThreshSet = true;
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 4088 */           ee.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 4092 */           AMConnectionPool.closeStatement(thSet);
/*      */         }
/*      */       }
/*      */       
/* 4096 */       if ((isThreshSet) && ((status.equals("clear")) || (status.equals("warning")) || (status.equals("critical"))))
/*      */       {
/* 4098 */         String jobsQuery = "select * from AM_AS400_JOBS where RESOURCEID in (" + resid + ") order by STATUS";
/* 4099 */         ResultSet jobSet = null;
/*      */         
/*      */         try
/*      */         {
/* 4103 */           AMThresholdApplier apply = new AMThresholdApplier();
/* 4104 */           String thCondition = null;
/* 4105 */           String thValue = null;
/* 4106 */           if (status.equals("clear"))
/*      */           {
/* 4108 */             thCondition = clearcondition;
/* 4109 */             thValue = clearvalue;
/*      */ 
/*      */           }
/* 4112 */           else if (status.equals("warning"))
/*      */           {
/* 4114 */             thCondition = warningcondition;
/* 4115 */             thValue = warningvalue;
/*      */ 
/*      */           }
/* 4118 */           else if (status.equals("critical"))
/*      */           {
/* 4120 */             thCondition = criticalcondition;
/* 4121 */             thValue = criticalvalue;
/*      */           }
/*      */           
/* 4124 */           jobSet = AMConnectionPool.executeQueryStmt(jobsQuery);
/* 4125 */           while (jobSet.next())
/*      */           {
/* 4127 */             HashMap thMatch = apply.checkForMatch(jobSet.getString("STATUS"), thCondition, thValue);
/*      */             
/* 4129 */             if ((thMatch != null) && (Boolean.parseBoolean(thMatch.get("isMatched").toString())))
/*      */             {
/* 4131 */               if (data.containsKey(jobSet.getString("RESOURCEID")))
/*      */               {
/* 4133 */                 ArrayList a1 = (ArrayList)data.get(jobSet.getString("RESOURCEID"));
/* 4134 */                 Map p = new HashMap();
/* 4135 */                 p.put("ID", jobSet.getString("ID"));
/* 4136 */                 p.put("JOBNAME", jobSet.getString("JOBNAME"));
/* 4137 */                 p.put("USERNAME", jobSet.getString("USERNAME"));
/* 4138 */                 p.put("NUMBER", jobSet.getString("NUMBER"));
/* 4139 */                 p.put("TYPE", jobSet.getString("TYPE"));
/* 4140 */                 p.put("STATUS", jobSet.getString("STATUS"));
/* 4141 */                 p.put("POOL", jobSet.getString("POOL"));
/* 4142 */                 p.put("FUNCTION", jobSet.getString("FUNCTION_COLUMN"));
/* 4143 */                 p.put("PRIORITY", jobSet.getString("PRIORITY"));
/* 4144 */                 p.put("THREADS", jobSet.getString("THREADS"));
/* 4145 */                 p.put("QUEUE", jobSet.getString("QUEUE"));
/*      */                 try {
/* 4147 */                   String subname = jobSet.getString("SUBSYSTEM");
/* 4148 */                   if (subname.length() > 1) {
/* 4149 */                     subname = subname.substring(0, subname.lastIndexOf("-"));
/*      */                   }
/* 4151 */                   p.put("SUBSYSTEM", subname);
/*      */                 } catch (Exception e) {
/* 4153 */                   p.put("SUBSYSTEM", "-");
/*      */                 }
/* 4155 */                 p.put("CPU_USED", jobSet.getString("CPU_USED"));
/* 4156 */                 p.put("RESOURCEID", jobSet.getString("RESOURCEID"));
/*      */                 
/* 4158 */                 a1.add(p);
/* 4159 */                 data.put(jobSet.getString("RESOURCEID"), a1);
/*      */               } else {
/* 4161 */                 ArrayList a = new ArrayList();
/*      */                 
/* 4163 */                 Map p = new HashMap();
/* 4164 */                 p.put("ID", jobSet.getString("ID"));
/* 4165 */                 p.put("JOBNAME", jobSet.getString("JOBNAME"));
/* 4166 */                 p.put("USERNAME", jobSet.getString("USERNAME"));
/* 4167 */                 p.put("NUMBER", jobSet.getString("NUMBER"));
/* 4168 */                 p.put("TYPE", jobSet.getString("TYPE"));
/* 4169 */                 p.put("STATUS", jobSet.getString("STATUS"));
/* 4170 */                 p.put("POOL", jobSet.getString("POOL"));
/* 4171 */                 p.put("FUNCTION", jobSet.getString("FUNCTION_COLUMN"));
/* 4172 */                 p.put("PRIORITY", jobSet.getString("PRIORITY"));
/* 4173 */                 p.put("THREADS", jobSet.getString("THREADS"));
/* 4174 */                 p.put("QUEUE", jobSet.getString("QUEUE"));
/*      */                 try {
/* 4176 */                   String subname = jobSet.getString("SUBSYSTEM");
/* 4177 */                   if (subname.length() > 1) {
/* 4178 */                     subname = subname.substring(0, subname.lastIndexOf("-"));
/*      */                   }
/* 4180 */                   p.put("SUBSYSTEM", subname);
/*      */                 } catch (Exception e) {
/* 4182 */                   p.put("SUBSYSTEM", "-");
/*      */                 }
/* 4184 */                 p.put("CPU_USED", jobSet.getString("CPU_USED"));
/* 4185 */                 p.put("RESOURCEID", jobSet.getString("RESOURCEID"));
/*      */                 
/* 4187 */                 a.add(p);
/* 4188 */                 data.put(jobSet.getString("RESOURCEID"), a);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/* 4195 */           ee.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 4199 */           AMConnectionPool.closeStatement(jobSet);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 4204 */         if (status.equals("clear")) {
/* 4205 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("clearlstSQ") + ")";
/* 4206 */         } else if (status.equals("warning")) {
/* 4207 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("warnglstSQ") + ")";
/* 4208 */         } else if (status.equals("critical")) {
/* 4209 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("critlstSQ") + ")";
/* 4210 */         } else if (status.equals("active")) {
/* 4211 */           includeJobname = dcc.getStatusforComponent(jobrid + "", "JOBNAME");
/* 4212 */           includeUsername = dcc.getStatusforComponent(jobrid + "", "USERNAME");
/* 4213 */           includeJobtype = dcc.getStatusforComponent(jobrid + "", "JOBTYPE");
/* 4214 */           includeSubsystem = dcc.getStatusforComponent(jobrid + "", "SUBSYSTEM");
/* 4215 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ")";
/* 4216 */           if (includeJobname) query = query + " and JOBNAME ='" + jobName + "'";
/* 4217 */           if (includeUsername) query = query + " and USERNAME ='" + jobUser + "'";
/* 4218 */           if (includeJobtype) query = query + " and TYPE ='" + jobType + "'";
/* 4219 */         } else if (status.equals("subsystem")) {
/* 4220 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ") and SUBSYSTEM ='" + referenceColumn + "'";
/* 4221 */           request.setAttribute("subsystemjobs", "true");
/* 4222 */         } else if (status.equals("jobfromqueue")) {
/* 4223 */           query = "select * from AM_AS400_JOBS where RESOURCEID in(" + resid + ") and QUEUE ='" + referenceColumn + "'";
/* 4224 */           request.setAttribute("referencejobs", "true");
/*      */         }
/* 4226 */         if (((fromAS400 == null) || (!fromAS400.equals("true"))) && (!query.isEmpty()))
/* 4227 */           rs2 = AMConnectionPool.executeQueryStmt(query);
/* 4228 */         while (rs2.next())
/*      */         {
/* 4230 */           if (data.containsKey(rs2.getString("RESOURCEID")))
/*      */           {
/* 4232 */             ArrayList a1 = (ArrayList)data.get(rs2.getString("RESOURCEID"));
/* 4233 */             String subystem = "";String queue = "";
/* 4234 */             Object p = new HashMap();
/* 4235 */             ((Map)p).put("ID", rs2.getString("ID"));
/* 4236 */             ((Map)p).put("JOBNAME", rs2.getString("JOBNAME"));
/* 4237 */             ((Map)p).put("USERNAME", rs2.getString("USERNAME"));
/* 4238 */             ((Map)p).put("NUMBER", rs2.getString("NUMBER"));
/* 4239 */             ((Map)p).put("TYPE", rs2.getString("TYPE"));
/* 4240 */             ((Map)p).put("STATUS", rs2.getString("STATUS"));
/* 4241 */             ((Map)p).put("POOL", rs2.getString("POOL"));
/* 4242 */             ((Map)p).put("FUNCTION", rs2.getString("FUNCTION_COLUMN"));
/* 4243 */             ((Map)p).put("PRIORITY", rs2.getString("PRIORITY"));
/* 4244 */             ((Map)p).put("THREADS", rs2.getString("THREADS"));
/* 4245 */             subystem = rs2.getString("SUBSYSTEM");
/* 4246 */             queue = rs2.getString("QUEUE");
/* 4247 */             queue = queue.contains("-") ? queue.substring(0, queue.lastIndexOf("-")) : queue;
/* 4248 */             subystem = subystem.contains("-") ? subystem.substring(0, subystem.lastIndexOf("-")) : subystem;
/* 4249 */             ((Map)p).put("SUBSYSTEM", subystem);
/* 4250 */             ((Map)p).put("QUEUE", queue);
/* 4251 */             ((Map)p).put("CPU_USED", rs2.getString("CPU_USED"));
/* 4252 */             ((Map)p).put("UPTIME", rs2.getString("UPTIME"));
/* 4253 */             ((Map)p).put("RESOURCEID", rs2.getString("RESOURCEID"));
/*      */             
/* 4255 */             a1.add(p);
/* 4256 */             data.put(rs2.getString("RESOURCEID"), a1);
/*      */           } else {
/* 4258 */             ArrayList a = new ArrayList();
/* 4259 */             String subystem = "";String queue = "";
/* 4260 */             Object p = new HashMap();
/* 4261 */             ((Map)p).put("ID", rs2.getString("ID"));
/* 4262 */             ((Map)p).put("JOBNAME", rs2.getString("JOBNAME"));
/* 4263 */             ((Map)p).put("USERNAME", rs2.getString("USERNAME"));
/* 4264 */             ((Map)p).put("NUMBER", rs2.getString("NUMBER"));
/* 4265 */             ((Map)p).put("TYPE", rs2.getString("TYPE"));
/* 4266 */             ((Map)p).put("STATUS", rs2.getString("STATUS"));
/* 4267 */             ((Map)p).put("POOL", rs2.getString("POOL"));
/* 4268 */             ((Map)p).put("FUNCTION", rs2.getString("FUNCTION_COLUMN"));
/* 4269 */             ((Map)p).put("PRIORITY", rs2.getString("PRIORITY"));
/* 4270 */             ((Map)p).put("THREADS", rs2.getString("THREADS"));
/* 4271 */             subystem = rs2.getString("SUBSYSTEM");
/* 4272 */             queue = rs2.getString("QUEUE");
/* 4273 */             queue = queue.contains("-") ? queue.substring(0, queue.lastIndexOf("-")) : queue;
/* 4274 */             subystem = subystem.contains("-") ? subystem.substring(0, subystem.lastIndexOf("-")) : subystem;
/* 4275 */             ((Map)p).put("SUBSYSTEM", subystem);
/* 4276 */             ((Map)p).put("QUEUE", queue);
/* 4277 */             ((Map)p).put("CPU_USED", rs2.getString("CPU_USED"));
/* 4278 */             ((Map)p).put("UPTIME", rs2.getString("UPTIME"));
/* 4279 */             ((Map)p).put("RESOURCEID", rs2.getString("RESOURCEID"));
/*      */             
/* 4281 */             a.add(p);
/* 4282 */             data.put(rs2.getString("RESOURCEID"), a);
/* 4283 */             continue;
/*      */             
/* 4285 */             if (fromAS400.equals("true")) {
/* 4286 */               String username = "";String pass = "";
/* 4287 */               JobList joblist = null;
/* 4288 */               Enumeration list = null;
/* 4289 */               List a = new ArrayList();
/* 4290 */               int jobCount = 0;int jobListLen = 0;
/*      */               try {
/* 4292 */                 String jobSelType = "*";
/*      */                 
/* 4294 */                 query = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 4295 */                 rs2 = AMConnectionPool.executeQueryStmt(query);
/* 4296 */                 if (rs2.next()) {
/* 4297 */                   username = rs2.getString("USERNAME");
/* 4298 */                   pass = rs2.getString("pass");
/*      */                 }
/* 4300 */                 AMConnectionPool.closeStatement(rs2);
/* 4301 */                 long jt1 = System.currentTimeMillis();
/*      */                 
/* 4303 */                 if (jobType.equals("Not Valid")) {
/* 4304 */                   jobSelType = "";
/* 4305 */                 } else if (jobType.equals("Autostart")) {
/* 4306 */                   jobSelType = "A";
/* 4307 */                 } else if (jobType.equals("Batch")) {
/* 4308 */                   jobSelType = "B";
/* 4309 */                 } else if (jobType.equals("Interactive")) {
/* 4310 */                   jobSelType = "I";
/* 4311 */                 } else if (jobType.equals("Subsystem")) {
/* 4312 */                   jobSelType = "M";
/* 4313 */                 } else if (jobType.equals("Spooled Reader")) {
/* 4314 */                   jobSelType = "R";
/* 4315 */                 } else if (jobType.equals("System")) {
/* 4316 */                   jobSelType = "S";
/* 4317 */                 } else if (jobType.equals("Spooled Writer")) {
/* 4318 */                   jobSelType = "W";
/* 4319 */                 } else if (jobType.equals("Source PF System")) {
/* 4320 */                   jobSelType = "X";
/*      */                 }
/* 4322 */                 AMLog.debug("AS400 Action : Retrieving job details for job " + jobName + "/" + jobUser + "/" + jobType + "-" + status + " from AS400 server : " + monname + " resourceid " + resid + " with Identifiers JN:" + includeJobname + "JU:" + includeUsername + "JT:" + includeJobtype + "S:" + includeSubsystem);
/*      */                 
/* 4324 */                 as400 = new AS400(monname, username, pass);
/* 4325 */                 as400.setGuiAvailable(false);
/* 4326 */                 SocketProperties socket = new SocketProperties();
/* 4327 */                 socket.setSoTimeout(600000);
/* 4328 */                 as400.setSocketProperties(socket);
/* 4329 */                 joblist = new JobList(as400);
/*      */                 
/* 4331 */                 joblist.clearJobSelectionCriteria();
/* 4332 */                 if (status.equals("active")) {
/* 4333 */                   joblist.addJobSelectionCriteria(5, Boolean.TRUE);
/* 4334 */                   joblist.addJobSelectionCriteria(6, Boolean.FALSE);
/* 4335 */                   joblist.addJobSelectionCriteria(7, Boolean.FALSE);
/* 4336 */                 } else if ((status.equals("jobq")) || (status.equals("jobinqueue"))) {
/* 4337 */                   joblist.addJobSelectionCriteria(5, Boolean.FALSE);
/* 4338 */                   joblist.addJobSelectionCriteria(6, Boolean.TRUE);
/* 4339 */                   joblist.addJobSelectionCriteria(7, Boolean.FALSE);
/* 4340 */                   if (status.equals("jobinqueue")) {
/* 4341 */                     QSYSObjectPathName queuePath = new QSYSObjectPathName(objLib, objName, "JOBQ");
/* 4342 */                     joblist.addJobSelectionCriteria(12, queuePath.getPath());
/*      */                   }
/* 4344 */                 } else if (status.equals("outq")) {
/* 4345 */                   joblist.addJobSelectionCriteria(5, Boolean.FALSE);
/* 4346 */                   joblist.addJobSelectionCriteria(6, Boolean.FALSE);
/* 4347 */                   joblist.addJobSelectionCriteria(7, Boolean.TRUE);
/*      */                 }
/* 4349 */                 if (!status.equals("jobinqueue")) {
/* 4350 */                   if (includeJobtype) {
/* 4351 */                     joblist.addJobSelectionCriteria(4, jobSelType);
/*      */                   }
/* 4353 */                   if (includeUsername) {
/* 4354 */                     joblist.setUser(jobUser);
/*      */                   }
/* 4356 */                   if (includeJobname) {
/* 4357 */                     joblist.setName(jobName);
/*      */                   }
/*      */                 }
/* 4360 */                 jobListLen = joblist.getLength();
/* 4361 */                 list = joblist.getJobs();
/*      */                 
/* 4363 */                 while (list.hasMoreElements())
/*      */                 {
/* 4365 */                   if (jobCount == 100) {
/*      */                     break;
/*      */                   }
/* 4368 */                   Job j = (Job)list.nextElement();
/* 4369 */                   j.loadInformation();
/* 4370 */                   jobCount += 1;
/*      */                   
/* 4372 */                   Map p = new HashMap();
/* 4373 */                   p.put("ID", Integer.valueOf(jobCount));
/* 4374 */                   p.put("JOBNAME", j.getName());
/* 4375 */                   p.put("USERNAME", j.getUser());
/* 4376 */                   jobType = j.getType();
/*      */                   
/* 4378 */                   p.put("TYPE", jobType);
/* 4379 */                   p.put("RESOURCEID", request.getParameter("resourceid"));
/*      */                   try
/*      */                   {
/* 4382 */                     p.put("NUMBER", j.getNumber());
/*      */                   } catch (Exception e) {
/* 4384 */                     p.put("NUMBER", "0");
/*      */                   }
/*      */                   try {
/* 4387 */                     String jobStatus = "";
/* 4388 */                     jobStatus = j.getStatus();
/* 4389 */                     if (jobStatus.equals("*ACTIVE")) {
/* 4390 */                       jobStatus = (String)j.getValue(101);
/* 4391 */                       jobStatus = jobStatus.trim();
/* 4392 */                       if ((jobStatus.equals("")) || (jobStatus.length() < 1)) {
/* 4393 */                         jobStatus = "NONE";
/*      */                       }
/* 4395 */                     } else if (jobStatus.equals("*JOBQ")) {
/* 4396 */                       jobStatus = j.getJobStatusInJobQueue();
/*      */                     }
/* 4398 */                     p.put("STATUS", jobStatus);
/*      */                   } catch (Exception e) {
/* 4400 */                     p.put("STATUS", "-");
/*      */                   }
/*      */                   try {
/* 4403 */                     p.put("POOL", Integer.valueOf(j.getPoolIdentifier()));
/*      */                   } catch (Exception e) {
/* 4405 */                     p.put("POOL", Integer.valueOf(0));
/*      */                   }
/*      */                   try {
/* 4408 */                     p.put("FUNCTION", j.getFunctionName());
/*      */                   } catch (Exception e) {
/* 4410 */                     p.put("FUNCTION", "-");
/*      */                   }
/*      */                   try {
/* 4413 */                     int pri = (status.equals("jobq")) || (status.equals("jobinqueue")) ? j.getQueuePriority() : j.getRunPriority();
/* 4414 */                     p.put("PRIORITY", Integer.valueOf(pri));
/*      */                   } catch (Exception e) {
/* 4416 */                     p.put("PRIORITY", Integer.valueOf(0));
/*      */                   }
/*      */                   try {
/* 4419 */                     p.put("THREADS", (Integer)j.getValue(2008));
/*      */                   } catch (Exception e) {
/* 4421 */                     p.put("THREADS", Integer.valueOf(0));
/*      */                   }
/*      */                   try {
/* 4424 */                     String queue = j.getQueue();
/* 4425 */                     if (queue.trim().equals("")) {
/* 4426 */                       queue = "-";
/*      */                     }
/* 4428 */                     p.put("QUEUE", queue);
/*      */                   } catch (Exception e) {
/* 4430 */                     p.put("QUEUE", "-");
/*      */                   }
/*      */                   try {
/* 4433 */                     String sub = j.getSubsystem();
/* 4434 */                     p.put("SUBSYSTEM", sub);
/*      */                   } catch (Exception e) {
/* 4436 */                     p.put("SUBSYSTEM", "-");
/*      */                   }
/*      */                   try {
/* 4439 */                     p.put("CPU_USED", Integer.valueOf(j.getCPUUsed()));
/*      */                   } catch (Exception e) {
/* 4441 */                     p.put("CPU_USED", "0");
/*      */                   }
/* 4443 */                   p.put("UPTIME", "-");
/*      */                   
/* 4445 */                   a.add(p);
/*      */                 }
/* 4447 */                 data.put(request.getParameter("resourceid"), a);
/* 4448 */                 AMLog.debug("AS400 Action : Retrived " + jobCount + "(" + jobListLen + ") job details for job " + jobName + "/" + jobUser + "/" + jobType + "-" + jobSelType + " from AS400 server : " + monname + " resourceid " + resid + " in " + (System.currentTimeMillis() - jt1) + " ms");
/*      */               } catch (Exception e) {
/* 4450 */                 e.printStackTrace();
/*      */               } finally {
/*      */                 try {
/* 4453 */                   joblist.close();
/*      */                 } catch (Exception e) {
/* 4455 */                   AMLog.debug("AS400 Action : Problem in closing job list in job filter for AS400 server : " + monname + " resourceid " + resid + " due to error : " + e.getMessage());
/*      */                 }
/* 4457 */                 as400.resetAllServices();
/*      */               }
/*      */             }
/*      */           } } }
/* 4461 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 4464 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 4467 */       AMConnectionPool.closeStatement(rs1);
/* 4468 */       AMConnectionPool.closeStatement(rs2);
/* 4469 */       AMConnectionPool.closeStatement(rs3);
/*      */     }
/* 4471 */     return new ActionForward("/jsp/as400/jobFilter.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward subsystemFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4476 */     Map map = new HashMap();
/* 4477 */     Map map1 = new HashMap();
/* 4478 */     List b = new ArrayList();
/* 4479 */     ResultSet rs1 = null;
/* 4480 */     ResultSet rs2 = null;
/* 4481 */     ResultSet rs3 = null;
/*      */     try
/*      */     {
/* 4484 */       response.setContentType("text/html; charset=UTF-8");
/* 4485 */       String resid = null;
/* 4486 */       String status = null;
/* 4487 */       String category = request.getParameter("catvalue");
/* 4488 */       String monitor = request.getParameter("monvalue");
/* 4489 */       if ((category != null) && (monitor != null))
/*      */       {
/* 4491 */         resid = monitor;
/* 4492 */         status = category;
/*      */       }
/*      */       else
/*      */       {
/* 4496 */         resid = request.getParameter("resourceid");
/* 4497 */         status = request.getParameter("status");
/*      */       }
/* 4499 */       String query1 = null;
/* 4500 */       String query2 = null;
/* 4501 */       StringBuilder resIds = new StringBuilder();
/* 4502 */       if (!resid.equals("ALL"))
/*      */       {
/* 4504 */         query1 = "select DISPLAYNAME from AM_ManagedObject where TYPE='AS400/iSeries' and RESOURCEID=" + resid + "";
/* 4505 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4506 */         while (rs1.next()) {
/* 4507 */           Map h = new HashMap();
/* 4508 */           h.put("RESOURCEID", resid);
/* 4509 */           h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4510 */           b.add(h);
/* 4511 */           map.put("ResIds", b);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4516 */       query1 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4517 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4518 */       while (rs1.next()) {
/* 4519 */         Map h = new HashMap();
/* 4520 */         h.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 4521 */         resIds.append(rs1.getString("RESOURCEID")).append(",");
/* 4522 */         h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4523 */         b.add(h);
/* 4524 */         map.put("ResIds", b);
/* 4525 */         resid = resIds.substring(0, resIds.length() - 1);
/*      */       }
/*      */       
/*      */ 
/* 4529 */       request.setAttribute("rescolls", map);
/*      */       try
/*      */       {
/* 4532 */         String query3 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4533 */         rs3 = AMConnectionPool.executeQueryStmt(query3);
/* 4534 */         List b3 = new ArrayList();
/* 4535 */         while (rs3.next()) {
/* 4536 */           Map h = new HashMap();
/* 4537 */           h.put("RESOURCEID", rs3.getString("RESOURCEID"));
/* 4538 */           h.put("DISPLAYNAME", rs3.getString("DISPLAYNAME"));
/* 4539 */           b3.add(h);
/* 4540 */           map1.put("ResIds", b3);
/*      */         }
/* 4542 */         request.setAttribute("as400s", map1);
/*      */       } catch (Exception e) {
/* 4544 */         e.printStackTrace();
/*      */       }
/*      */       
/* 4547 */       Map data = new HashMap();
/* 4548 */       if (status.equals("clear")) {
/* 4549 */         query2 = "select * from AM_AS400_SUBSYSTEM where RESOURCEID in(" + resid + ") and STATUS in('*ACTIVE')";
/* 4550 */       } else if (status.equals("critical")) {
/* 4551 */         query2 = "select * from AM_AS400_SUBSYSTEM where RESOURCEID in(" + resid + ") and STATUS in('*INACTIVE')";
/*      */       }
/* 4553 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 4555 */       while (rs2.next())
/*      */       {
/* 4557 */         if (data.containsKey(rs2.getString("RESOURCEID")))
/*      */         {
/* 4559 */           ArrayList a1 = (ArrayList)data.get(rs2.getString("RESOURCEID"));
/*      */           
/* 4561 */           Map p = new HashMap();
/* 4562 */           p.put("ID", rs2.getString("ID"));
/* 4563 */           p.put("NAME", rs2.getString("NAME"));
/* 4564 */           p.put("LIBRARY", rs2.getString("LIBRARY"));
/* 4565 */           p.put("ACTIVE_JOBS", rs2.getString("ACTIVE_JOBS"));
/* 4566 */           p.put("TOTAL_STORAGE", rs2.getString("TOTAL_STORAGE"));
/* 4567 */           p.put("STATUS", rs2.getString("STATUS"));
/* 4568 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4569 */           a1.add(p);
/* 4570 */           data.put(rs2.getString("RESOURCEID"), a1);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 4575 */           ArrayList a = new ArrayList();
/*      */           
/* 4577 */           Map p = new HashMap();
/* 4578 */           p.put("ID", rs2.getString("ID"));
/* 4579 */           p.put("NAME", rs2.getString("NAME"));
/* 4580 */           p.put("LIBRARY", rs2.getString("LIBRARY"));
/* 4581 */           p.put("ACTIVE_JOBS", rs2.getString("ACTIVE_JOBS"));
/* 4582 */           p.put("TOTAL_STORAGE", rs2.getString("TOTAL_STORAGE"));
/* 4583 */           p.put("STATUS", rs2.getString("STATUS"));
/* 4584 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4585 */           a.add(p);
/* 4586 */           data.put(rs2.getString("RESOURCEID"), a);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 4592 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 4595 */       e.printStackTrace();
/*      */     } finally {
/* 4597 */       AMConnectionPool.closeStatement(rs1);
/* 4598 */       AMConnectionPool.closeStatement(rs2);
/* 4599 */       AMConnectionPool.closeStatement(rs3);
/*      */     }
/* 4601 */     return new ActionForward("/jsp/as400/subsystemFilter.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward spoolFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4606 */     Map map = new HashMap();
/* 4607 */     Map map1 = new HashMap();
/* 4608 */     List b = new ArrayList();
/* 4609 */     ResultSet rs1 = null;
/* 4610 */     ResultSet rs2 = null;
/* 4611 */     ResultSet rs3 = null;
/*      */     try {
/* 4613 */       response.setContentType("text/html; charset=UTF-8");
/* 4614 */       String resid = null;
/* 4615 */       String status = null;
/* 4616 */       String category = request.getParameter("catvalue");
/* 4617 */       String monitor = request.getParameter("monvalue");
/* 4618 */       if ((category != null) && (monitor != null))
/*      */       {
/* 4620 */         resid = monitor;
/* 4621 */         status = category;
/*      */       }
/*      */       else
/*      */       {
/* 4625 */         resid = request.getParameter("resourceid");
/* 4626 */         status = request.getParameter("status");
/*      */       }
/* 4628 */       String query1 = null;
/* 4629 */       String query2 = null;
/* 4630 */       StringBuilder resIds = new StringBuilder();
/* 4631 */       if (!resid.equals("ALL"))
/*      */       {
/* 4633 */         query1 = "select DISPLAYNAME from AM_ManagedObject where TYPE='AS400/iSeries' and RESOURCEID=" + resid + "";
/* 4634 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4635 */         while (rs1.next()) {
/* 4636 */           Map h = new HashMap();
/* 4637 */           h.put("RESOURCEID", resid);
/* 4638 */           h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4639 */           b.add(h);
/* 4640 */           map.put("ResIds", b);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4645 */       query1 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4646 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4647 */       while (rs1.next()) {
/* 4648 */         Map h = new HashMap();
/* 4649 */         h.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 4650 */         resIds.append(rs1.getString("RESOURCEID")).append(",");
/* 4651 */         h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4652 */         b.add(h);
/* 4653 */         map.put("ResIds", b);
/* 4654 */         resid = resIds.substring(0, resIds.length() - 1);
/*      */       }
/*      */       
/*      */ 
/* 4658 */       request.setAttribute("rescolls", map);
/*      */       try
/*      */       {
/* 4661 */         String query3 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4662 */         rs3 = AMConnectionPool.executeQueryStmt(query3);
/* 4663 */         List b3 = new ArrayList();
/* 4664 */         while (rs3.next()) {
/* 4665 */           Map h = new HashMap();
/* 4666 */           h.put("RESOURCEID", rs3.getString("RESOURCEID"));
/* 4667 */           h.put("DISPLAYNAME", rs3.getString("DISPLAYNAME"));
/* 4668 */           b3.add(h);
/* 4669 */           map1.put("ResIds", b3);
/*      */         }
/* 4671 */         request.setAttribute("as400s", map1);
/*      */       } catch (Exception e) {
/* 4673 */         e.printStackTrace();
/*      */       }
/*      */       
/* 4676 */       Map data = new HashMap();
/* 4677 */       if (status.equals("clear")) {
/* 4678 */         query2 = "select * from AM_AS400_SPOOL where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("splclearlstSQ") + ")";
/*      */       }
/* 4680 */       else if (status.equals("warning")) {
/* 4681 */         query2 = "select * from AM_AS400_SPOOL where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("splwarnglstSQ") + ")";
/*      */       }
/* 4683 */       else if (status.equals("critical")) {
/* 4684 */         query2 = "select * from AM_AS400_SPOOL where RESOURCEID in(" + resid + ") and STATUS in(" + (String)Constants.AS400Props.get("splcritlstSQ") + ")";
/*      */       }
/* 4686 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 4688 */       while (rs2.next())
/*      */       {
/* 4690 */         if (data.containsKey(rs2.getString("RESOURCEID")))
/*      */         {
/* 4692 */           ArrayList a1 = (ArrayList)data.get(rs2.getString("RESOURCEID"));
/* 4693 */           Map p = new HashMap();
/* 4694 */           p.put("ID", rs2.getString("ID"));
/* 4695 */           p.put("NAME", rs2.getString("NAME"));
/* 4696 */           p.put("NUMBER", rs2.getString("NUMBER"));
/* 4697 */           p.put("JOB_NAME", rs2.getString("JOB_NAME"));
/* 4698 */           p.put("JOB_NUMBER", rs2.getString("JOB_NUMBER"));
/* 4699 */           p.put("JOB_OWNER", rs2.getString("JOB_OWNER"));
/* 4700 */           p.put("STATUS", rs2.getString("STATUS"));
/* 4701 */           p.put("TOTAL_PAGES", rs2.getString("TOTAL_PAGES"));
/* 4702 */           p.put("PRINTER_NAME", rs2.getString("PRINTER_NAME"));
/* 4703 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4704 */           a1.add(p);
/* 4705 */           data.put(rs2.getString("RESOURCEID"), a1);
/*      */         }
/*      */         else
/*      */         {
/* 4709 */           ArrayList a = new ArrayList();
/* 4710 */           Map p = new HashMap();
/* 4711 */           p.put("ID", rs2.getString("ID"));
/* 4712 */           p.put("NAME", rs2.getString("NAME"));
/* 4713 */           p.put("NUMBER", rs2.getString("NUMBER"));
/* 4714 */           p.put("JOB_NAME", rs2.getString("JOB_NAME"));
/* 4715 */           p.put("JOB_NUMBER", rs2.getString("JOB_NUMBER"));
/* 4716 */           p.put("JOB_OWNER", rs2.getString("JOB_OWNER"));
/* 4717 */           p.put("STATUS", rs2.getString("STATUS"));
/* 4718 */           p.put("TOTAL_PAGES", rs2.getString("TOTAL_PAGES"));
/* 4719 */           p.put("PRINTER_NAME", rs2.getString("PRINTER_NAME"));
/* 4720 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4721 */           a.add(p);
/* 4722 */           data.put(rs2.getString("RESOURCEID"), a);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4727 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 4730 */       e.printStackTrace();
/*      */     } finally {
/* 4732 */       AMConnectionPool.closeStatement(rs1);
/* 4733 */       AMConnectionPool.closeStatement(rs2);
/* 4734 */       AMConnectionPool.closeStatement(rs3);
/*      */     }
/* 4736 */     return new ActionForward("/jsp/as400/spoolFilter.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward messageFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 4741 */     Map map = new HashMap();
/* 4742 */     Map map1 = new HashMap();
/* 4743 */     List b = new ArrayList();
/* 4744 */     ResultSet rs1 = null;
/* 4745 */     ResultSet rs2 = null;
/* 4746 */     ResultSet rs3 = null;
/*      */     try {
/* 4748 */       response.setContentType("text/html; charset=UTF-8");
/* 4749 */       boolean allservers = true;
/* 4750 */       String resid = null;
/* 4751 */       String status = null;
/* 4752 */       String category = request.getParameter("catvalue");
/* 4753 */       String monitor = request.getParameter("monvalue");
/* 4754 */       if ((category != null) && (monitor != null))
/*      */       {
/* 4756 */         resid = monitor;
/* 4757 */         status = category;
/*      */       }
/*      */       else
/*      */       {
/* 4761 */         resid = request.getParameter("resourceid");
/* 4762 */         status = request.getParameter("status");
/*      */       }
/*      */       
/* 4765 */       String query1 = null;
/* 4766 */       String query2 = null;
/* 4767 */       StringBuilder resIds = new StringBuilder();
/* 4768 */       if (!resid.equals("ALL"))
/*      */       {
/* 4770 */         query1 = "select DISPLAYNAME from AM_ManagedObject where TYPE='AS400/iSeries' and RESOURCEID=" + resid + "";
/* 4771 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4772 */         while (rs1.next()) {
/* 4773 */           Map h = new HashMap();
/* 4774 */           h.put("RESOURCEID", resid);
/* 4775 */           h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4776 */           b.add(h);
/* 4777 */           map.put("ResIds", b);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4782 */       allservers = false;
/* 4783 */       query1 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4784 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 4785 */       while (rs1.next()) {
/* 4786 */         Map h = new HashMap();
/* 4787 */         h.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 4788 */         resIds.append(rs1.getString("RESOURCEID")).append(",");
/* 4789 */         h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 4790 */         b.add(h);
/* 4791 */         map.put("ResIds", b);
/* 4792 */         resid = resIds.substring(0, resIds.length() - 1);
/*      */       }
/*      */       
/*      */ 
/* 4796 */       request.setAttribute("rescolls", map);
/* 4797 */       request.setAttribute("allservers", Boolean.valueOf(allservers));
/*      */       try
/*      */       {
/* 4800 */         String query3 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 4801 */         rs3 = AMConnectionPool.executeQueryStmt(query3);
/* 4802 */         List b3 = new ArrayList();
/* 4803 */         while (rs3.next()) {
/* 4804 */           Map h = new HashMap();
/* 4805 */           h.put("RESOURCEID", rs3.getString("RESOURCEID"));
/* 4806 */           h.put("DISPLAYNAME", rs3.getString("DISPLAYNAME"));
/* 4807 */           b3.add(h);
/* 4808 */           map1.put("ResIds", b3);
/*      */         }
/* 4810 */         request.setAttribute("as400s", map1);
/*      */       } catch (Exception e) {
/* 4812 */         e.printStackTrace();
/*      */       }
/*      */       
/* 4815 */       Map data = new HashMap();
/* 4816 */       if (status.equals("msgneedreply"))
/*      */       {
/* 4818 */         query2 = "select * from AM_AS400_MESSAGES where RESOURCEID in(" + resid + ") and REPLYSTATUS in('W') order by TYPE";
/*      */       }
/* 4820 */       else if (status.equals("allmsg")) {
/* 4821 */         query2 = "select * from AM_AS400_MESSAGES where RESOURCEID in(" + resid + ") order by TYPE";
/*      */       }
/* 4823 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 4825 */       while (rs2.next())
/*      */       {
/* 4827 */         if (data.containsKey(rs2.getString("RESOURCEID")))
/*      */         {
/* 4829 */           ArrayList a1 = (ArrayList)data.get(rs2.getString("RESOURCEID"));
/* 4830 */           Map p = new HashMap();
/* 4831 */           p.put("ID", rs2.getString("ID"));
/* 4832 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4833 */           p.put("MSG_ID", rs2.getString("MSG_ID"));
/* 4834 */           p.put("SEVERITY", rs2.getString("SEVERITY"));
/* 4835 */           p.put("TYPE", rs2.getString("TYPE"));
/* 4836 */           p.put("MESSAGE", rs2.getString("MESSAGE"));
/* 4837 */           p.put("DATE", rs2.getString("DATE"));
/* 4838 */           p.put("ANSWERED", rs2.getString("ANSWERED"));
/* 4839 */           p.put("REPLYSTATUS", rs2.getString("REPLYSTATUS"));
/* 4840 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4841 */           p.put("MSG_ID_STATUS", rs2.getString("MSG_ID_STATUS"));
/* 4842 */           p.put("SEVSTATUS", rs2.getString("SEVSTATUS"));
/* 4843 */           p.put("MSGSTATUS", rs2.getString("MSGSTATUS"));
/* 4844 */           a1.add(p);
/* 4845 */           data.put(rs2.getString("RESOURCEID"), a1);
/*      */         }
/*      */         else
/*      */         {
/* 4849 */           ArrayList a = new ArrayList();
/* 4850 */           Map p = new HashMap();
/* 4851 */           p.put("ID", rs2.getString("ID"));
/* 4852 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4853 */           p.put("MSG_ID", rs2.getString("MSG_ID"));
/* 4854 */           p.put("SEVERITY", rs2.getString("SEVERITY"));
/* 4855 */           p.put("TYPE", rs2.getString("TYPE"));
/* 4856 */           p.put("MESSAGE", rs2.getString("MESSAGE"));
/* 4857 */           p.put("DATE", rs2.getString("DATE"));
/* 4858 */           p.put("ANSWERED", rs2.getString("ANSWERED"));
/* 4859 */           p.put("REPLYSTATUS", rs2.getString("REPLYSTATUS"));
/* 4860 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 4861 */           p.put("MSG_ID_STATUS", rs2.getString("MSG_ID_STATUS"));
/* 4862 */           p.put("SEVSTATUS", rs2.getString("SEVSTATUS"));
/* 4863 */           p.put("MSGSTATUS", rs2.getString("MSGSTATUS"));
/* 4864 */           a.add(p);
/* 4865 */           data.put(rs2.getString("RESOURCEID"), a);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 4870 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 4873 */       e.printStackTrace();
/*      */     } finally {
/* 4875 */       AMConnectionPool.closeStatement(rs1);
/* 4876 */       AMConnectionPool.closeStatement(rs2);
/* 4877 */       AMConnectionPool.closeStatement(rs3);
/*      */     }
/* 4879 */     return new ActionForward("/jsp/as400/messageFilter.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward jobMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 4883 */     Map data = new HashMap();
/* 4884 */     List a = new ArrayList();
/* 4885 */     ResultSet rs = null;
/* 4886 */     String query = null;
/* 4887 */     AS400 as400 = null;
/*      */     try {
/* 4889 */       response.setContentType("text/html; charset=UTF-8");
/* 4890 */       String resid = request.getParameter("resourceid");
/* 4891 */       String fromAS400 = request.getParameter("fromAS400");
/* 4892 */       String jobName = "*ALL";String userName = "*ALL";String jobNumber = "*ALL";String jobType = "*";
/* 4893 */       String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/* 4894 */       if ((fromAS400 != null) && (fromAS400.equals("true"))) {
/*      */         try {
/* 4896 */           jobName = request.getParameter("jobName").trim();
/* 4897 */           if (jobName.equalsIgnoreCase("ALL")) jobName = "*ALL";
/* 4898 */           userName = request.getParameter("userName").trim();
/* 4899 */           if (userName.equalsIgnoreCase("ALL")) userName = "*ALL";
/* 4900 */           jobNumber = request.getParameter("jobNumber").trim();
/* 4901 */           if (jobNumber.equalsIgnoreCase("ALL")) jobNumber = "*ALL";
/* 4902 */           if (!jobNumber.equals("*ALL")) {
/* 4903 */             Integer jn = Integer.valueOf(Integer.parseInt(jobNumber));
/* 4904 */             jobNumber = String.format(format, new Object[] { jn });
/*      */           }
/* 4906 */           jobType = request.getParameter("jobType").trim();
/*      */         } catch (Exception e) {
/* 4908 */           jobType = "*";
/* 4909 */           jobName = userName = jobNumber = "*ALL";
/* 4910 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 4914 */       if ((fromAS400 == null) || (!fromAS400.equals("true"))) {
/* 4915 */         AMLog.debug("AS400 Action: Add Jobs to monitor from db");
/* 4916 */         query = "SELECT * FROM AM_AS400_JOBS where RESOURCEID = " + resid;
/* 4917 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 4918 */         while (rs.next()) {
/* 4919 */           Map p = new HashMap();
/* 4920 */           p.put("ID", rs.getString("ID"));
/* 4921 */           p.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 4922 */           p.put("JOBNAME", rs.getString("JOBNAME"));
/* 4923 */           p.put("USERNAME", rs.getString("USERNAME"));
/* 4924 */           p.put("NUMBER", rs.getString("NUMBER"));
/* 4925 */           p.put("TYPE", rs.getString("TYPE"));
/* 4926 */           p.put("STATUS", rs.getString("STATUS"));
/* 4927 */           p.put("POOL", rs.getString("POOL"));
/* 4928 */           p.put("FUNCTION", rs.getString("FUNCTION_COLUMN"));
/* 4929 */           p.put("PRIORITY", rs.getString("PRIORITY"));
/* 4930 */           p.put("THREADS", rs.getString("THREADS"));
/*      */           try {
/* 4932 */             String queue = rs.getString("QUEUE");
/* 4933 */             String subName = rs.getString("SUBSYSTEM");
/* 4934 */             queue = queue.contains("-") ? queue.substring(0, queue.lastIndexOf("-")) : queue;
/* 4935 */             subName = subName.contains("-") ? subName.substring(0, subName.lastIndexOf("-")) : subName;
/* 4936 */             p.put("QUEUE", queue);
/* 4937 */             p.put("SUBSYSTEM", subName);
/*      */           } catch (Exception e) {
/* 4939 */             p.put("SUBSYSTEM", "-");
/* 4940 */             p.put("QUEUE", "-");
/*      */           }
/* 4942 */           p.put("CPU_USED", rs.getString("CPU_USED"));
/* 4943 */           p.put("UPTIME", rs.getString("UPTIME"));
/* 4944 */           p.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME"));
/* 4945 */           a.add(p);
/* 4946 */           data.put("jobs", a);
/*      */         }
/* 4948 */         AMConnectionPool.closeStatement(rs);
/*      */       } else {
/* 4950 */         AMLog.debug("AS400 Action: Add Jobs to be monitor from AS400 ,filter by user/jobname/jobnumber/jobtype - " + userName + "/" + jobName + "/" + jobNumber + "/" + jobType);
/* 4951 */         String monname = "";String dispname = "";String username = "";String pass = "";String coltime = "0";
/* 4952 */         Enumeration list = null;
/* 4953 */         int jobcount = 0;int id = 0;
/* 4954 */         query = "select mo.RESOURCENAME,mo.DISPLAYNAME,hd.USERNAME," + DBQueryUtil.decode("hd.PASSWORD") + " as pass,ac.COLLECTIONTIME from AM_AS400_CONFIGURATION ac,AM_ManagedObject mo,HostDetails hd where mo.RESOURCENAME=hd.RESOURCENAME and mo.RESOURCEID=ac.RESOURCEID and mo.RESOURCEID = " + resid;
/* 4955 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 4956 */         if (rs.next()) {
/* 4957 */           monname = rs.getString("RESOURCENAME");
/* 4958 */           dispname = rs.getString("DISPLAYNAME");
/* 4959 */           coltime = rs.getString("COLLECTIONTIME");
/* 4960 */           username = rs.getString("USERNAME");
/* 4961 */           pass = rs.getString("pass");
/* 4962 */           request.setAttribute("dispname", dispname);
/*      */         }
/* 4964 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 4966 */         query = "select max(ID) as ID from AM_AS400_JOBS where RESOURCEID=" + resid;
/* 4967 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 4968 */         if (rs.next()) {
/* 4969 */           String maxId = rs.getString("ID");
/* 4970 */           if ((maxId == null) || (maxId.equals("null"))) {
/* 4971 */             id = 1;
/*      */           } else {
/* 4973 */             id = Integer.valueOf(Integer.parseInt(maxId)).intValue();
/*      */           }
/*      */         }
/* 4976 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 4978 */         JobList joblist = null;
/*      */         try {
/* 4980 */           AMLog.debug("AS400 Action: Jobs filterd by user/jobname/jobnumber/jobtype - " + userName + "/" + jobName + "/" + jobNumber + "/" + jobType + " ID:" + id);
/* 4981 */           as400 = new AS400(monname, username, pass);
/* 4982 */           as400.setGuiAvailable(false);
/* 4983 */           SocketProperties socket = new SocketProperties();
/* 4984 */           socket.setSoTimeout(600000);
/* 4985 */           as400.setSocketProperties(socket);
/* 4986 */           joblist = new JobList(as400);
/*      */           
/* 4988 */           if ((jobType.equals("*JOBQ")) || (jobType.equals("*OUTQ"))) {
/* 4989 */             joblist.addJobSelectionCriteria(5, Boolean.FALSE);
/*      */           }
/* 4991 */           if (!jobType.equals("*JOBQ")) {
/* 4992 */             joblist.addJobSelectionCriteria(6, Boolean.FALSE);
/*      */           }
/* 4994 */           if (!jobType.equals("*OUTQ")) {
/* 4995 */             joblist.addJobSelectionCriteria(7, Boolean.FALSE);
/*      */           }
/*      */           
/*      */ 
/* 4999 */           joblist.setUser(userName);
/* 5000 */           joblist.setName(jobName);
/* 5001 */           joblist.setNumber(jobNumber);
/*      */           
/* 5003 */           if ((!jobType.equals("*JOBQ")) || (!jobType.equals("*OUTQ"))) {
/* 5004 */             joblist.addJobSelectionCriteria(4, jobType);
/*      */           }
/*      */           
/* 5007 */           list = joblist.getJobs();
/* 5008 */           while (list.hasMoreElements())
/*      */           {
/* 5010 */             if (jobcount == 100) {
/*      */               break;
/*      */             }
/*      */             
/* 5014 */             Map p = new HashMap();
/* 5015 */             String jobtype = "";
/* 5016 */             Job j = (Job)list.nextElement();
/* 5017 */             j.loadInformation();
/* 5018 */             if (j.getType().equals("")) {
/* 5019 */               jobtype = "Not Valid";
/* 5020 */             } else if (j.getType().equals("A")) {
/* 5021 */               jobtype = "Autostart";
/* 5022 */             } else if (j.getType().equals("B")) {
/* 5023 */               jobtype = "Batch";
/* 5024 */             } else if (j.getType().equals("I")) {
/* 5025 */               jobtype = "Interactive";
/* 5026 */             } else if (j.getType().equals("M")) {
/* 5027 */               jobtype = "Subsystem";
/* 5028 */             } else if (j.getType().equals("R")) {
/* 5029 */               jobtype = "Spooled Reader";
/* 5030 */             } else if (j.getType().equals("S")) {
/* 5031 */               jobtype = "System";
/* 5032 */             } else if (j.getType().equals("W")) {
/* 5033 */               jobtype = "Spooled Writer";
/* 5034 */             } else if (j.getType().equals("X")) {
/* 5035 */               jobtype = "Source PF System";
/*      */             }
/*      */             
/* 5038 */             jobcount += 1;
/* 5039 */             id += 1;
/* 5040 */             p.put("ID", Integer.valueOf(id));
/*      */             try
/*      */             {
/* 5043 */               p.put("JOBNAME", j.getName());
/*      */             } catch (Exception e) {
/* 5045 */               p.put("JOBNAME", "-");
/*      */             }
/*      */             try {
/* 5048 */               p.put("USERNAME", j.getUser());
/*      */             } catch (Exception e) {
/* 5050 */               p.put("USERNAME", "-");
/*      */             }
/*      */             try {
/* 5053 */               int num = Integer.parseInt(j.getNumber());
/* 5054 */               p.put("NUMBER", Integer.valueOf(num));
/*      */             } catch (Exception e) {
/* 5056 */               p.put("NUMBER", "0");
/*      */             }
/*      */             try {
/* 5059 */               p.put("TYPE", jobtype);
/*      */             } catch (Exception e) {
/* 5061 */               p.put("TYPE", "-");
/*      */             }
/*      */             try {
/* 5064 */               p.put("STATUS", j.getValue(101));
/*      */             } catch (Exception e) {
/* 5066 */               p.put("STATUS", "-");
/*      */             }
/*      */             try {
/* 5069 */               p.put("POOL_ID", Integer.valueOf(j.getPoolIdentifier()));
/*      */             } catch (Exception e) {
/* 5071 */               p.put("POOL_ID", Integer.valueOf(0));
/*      */             }
/*      */             try {
/* 5074 */               p.put("FUNCTION", j.getFunctionName());
/*      */             } catch (Exception e) {
/* 5076 */               p.put("FUNCTION", "-");
/*      */             }
/*      */             try {
/* 5079 */               p.put("PRIORITY", Integer.valueOf(j.getRunPriority()));
/*      */             } catch (Exception e) {
/* 5081 */               p.put("PRIORITY", Integer.valueOf(0));
/*      */             }
/*      */             try {
/* 5084 */               p.put("THREADS", (Integer)j.getValue(2008));
/*      */             } catch (Exception e) {
/* 5086 */               p.put("THREADS", Integer.valueOf(0));
/*      */             }
/*      */             try {
/* 5089 */               String queue = j.getQueue();
/* 5090 */               if (queue.trim().equals("")) {
/* 5091 */                 queue = "-";
/*      */               }
/* 5093 */               p.put("QUEUE", queue);
/*      */             } catch (Exception e) {
/* 5095 */               p.put("QUEUE", "-");
/*      */             }
/*      */             try {
/* 5098 */               String sub = j.getSubsystem();
/* 5099 */               if (sub.trim().equals("")) {
/* 5100 */                 sub = "-";
/* 5101 */               } else if (sub.length() > 4) {
/* 5102 */                 sub = sub.substring(sub.lastIndexOf("/") + 1, sub.lastIndexOf("."));
/*      */               }
/* 5104 */               p.put("SUBSYSTEM", sub);
/*      */             } catch (Exception e) {
/* 5106 */               p.put("SUBSYSTEM", "-");
/*      */             }
/*      */             try {
/* 5109 */               p.put("CPU_USED", Integer.valueOf(j.getCPUUsed()));
/*      */             } catch (Exception e) {
/* 5111 */               p.put("CPU_USED", "0");
/*      */             }
/* 5113 */             p.put("UPTIME", "-");
/* 5114 */             p.put("COLLECTIONTIME", coltime);
/*      */             
/* 5116 */             a.add(p);
/* 5117 */             data.put("jobs", a);
/*      */           }
/*      */         } catch (Exception e) {
/* 5120 */           AMLog.debug("AS400 Action: Problem in fetching Job Details for host" + monname + "due to Error: " + e.getMessage());
/*      */         } finally {
/*      */           try {
/* 5123 */             joblist.close();
/*      */           } catch (Exception e) {
/* 5125 */             AMLog.debug("AS400 Action : Problem in closing job list for AS400 server : " + monname + " resourceid " + resid + " due to error : " + e.getMessage());
/*      */           }
/* 5127 */           as400.resetAllServices();
/*      */         }
/*      */       }
/* 5130 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 5133 */       e.printStackTrace();
/*      */     } finally {
/* 5135 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 5137 */     return new ActionForward("/jsp/as400/jobMonitor.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward addJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 5144 */     HashMap<String, Map> data = new HashMap();
/*      */     
/* 5146 */     ResultSet rs = null;
/* 5147 */     String hostname = "";
/* 5148 */     String resid = "";String fromAS400 = "";
/* 5149 */     JSONArray jids = new JSONArray();
/* 5150 */     Pattern quotePattern = Pattern.compile("&quot;");
/* 5151 */     String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/*      */     try
/*      */     {
/* 5154 */       response.setContentType("text/html; charset=UTF-8");
/* 5155 */       resid = request.getParameter("resourceid");
/* 5156 */       String jsonlist = request.getParameter("jobs");
/* 5157 */       fromAS400 = request.getParameter("fromAS400");
/*      */       try {
/* 5159 */         jsonlist = jsonlist.trim();
/* 5160 */         Matcher matcher = quotePattern.matcher(jsonlist);
/* 5161 */         String joblist = matcher.replaceAll("\"");
/* 5162 */         AMLog.debug("AS400 Action: Monitored jobs to add " + joblist);
/* 5163 */         JSONArray jjlist = new JSONArray(joblist);
/* 5164 */         int jobstoadd = jjlist.length();int count = 0;
/*      */         
/* 5166 */         for (int i = 0; i < jobstoadd; i++) {
/* 5167 */           JSONObject jobObj = jjlist.getJSONObject(count);
/* 5168 */           HashMap<String, String> p = new HashMap();
/* 5169 */           p.put("ID", jobObj.getString("ID"));
/* 5170 */           p.put("RESOURCEID", resid);
/* 5171 */           p.put("JOBNAME", jobObj.getString("JOBNAME"));
/* 5172 */           p.put("USERNAME", jobObj.getString("USERNAME"));
/* 5173 */           p.put("NUMBER", jobObj.getString("NUMBER"));
/* 5174 */           p.put("TYPE", jobObj.getString("TYPE"));
/* 5175 */           p.put("SUBSYSTEM", jobObj.getString("SUBSYSTEM"));
/* 5176 */           p.put("COLLECTIONTIME", jobObj.getString("COLLECTIONTIME"));
/*      */           
/* 5178 */           data.put(jobObj.getString("ID"), p);
/* 5179 */           count++;
/*      */         }
/* 5181 */         request.setAttribute("data", data);
/*      */       } catch (Exception e) {
/* 5183 */         e.printStackTrace();
/* 5184 */         AMLog.debug("AS400 Action: Error while adding jobs to Monitored jobs " + e.getMessage());
/*      */       }
/* 5186 */       hostname = Constants.getResName(resid);
/*      */     }
/*      */     catch (Exception e) {
/* 5189 */       e.printStackTrace();
/*      */     } finally {
/* 5191 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 5196 */       Collection col = data.values();
/* 5197 */       Iterator iter = col.iterator();
/* 5198 */       while (iter.hasNext())
/*      */       {
/* 5200 */         HashMap map = (HashMap)iter.next();
/* 5201 */         String job_name = "";String user_name = "";String job_number = "";String job_type = "";String subsystem = "";
/*      */         
/* 5203 */         Integer resrcid = Integer.valueOf(Integer.parseInt(map.get("RESOURCEID").toString()));
/*      */         
/* 5205 */         job_name = (String)map.get("JOBNAME");
/* 5206 */         user_name = (String)map.get("USERNAME");
/* 5207 */         job_number = (String)map.get("NUMBER");
/*      */         try {
/* 5209 */           Integer jn = Integer.valueOf(Integer.parseInt(job_number));
/* 5210 */           job_number = String.format(format, new Object[] { jn });
/*      */         } catch (Exception e) {
/* 5212 */           e.printStackTrace();
/*      */         }
/* 5214 */         job_type = (String)map.get("TYPE");
/* 5215 */         subsystem = (String)map.get("SUBSYSTEM");
/* 5216 */         if ((subsystem.trim().equals("null")) || (subsystem.length() < 1)) {
/* 5217 */           subsystem = "NONE";
/* 5218 */         } else if (subsystem.contains(".SBSD")) {
/* 5219 */           subsystem = subsystem.substring(subsystem.lastIndexOf("/") + 1, subsystem.lastIndexOf("."));
/*      */         }
/*      */         
/* 5222 */         String disp_name = hostname + ":" + "JOB" + "-" + job_name + "-" + user_name + "-" + subsystem;
/*      */         
/* 5224 */         String monjob_name = hostname + ":" + "JOB" + "-" + job_name + "-" + user_name + "-" + subsystem + "-" + job_type;
/*      */         
/* 5226 */         int jobrid = DataCollectionDBUtil.CheckandinsertAMMO(monjob_name, "AS400_MonJob", disp_name, resrcid.intValue(), false);
/*      */         
/* 5228 */         if (jobrid == -1) {
/* 5229 */           jobrid = DataCollectionDBUtil.CheckandinsertAMMO(monjob_name, "AS400_MonJob", disp_name, resrcid.intValue());
/* 5230 */           jids.put(map.get("ID"));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 5235 */       e.printStackTrace();
/*      */     }
/*      */     
/* 5238 */     AMLog.debug("AS400 Action: " + jids + " Jobs Added from AS400:" + fromAS400);
/* 5239 */     PrintWriter pw = response.getWriter();
/* 5240 */     pw.print(jids);
/* 5241 */     pw.flush();
/*      */     
/* 5243 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward getenabledetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 5249 */     ArrayList enableDetails = new ArrayList();
/* 5250 */     ArrayList currentDetails = new ArrayList();
/* 5251 */     ResultSet results = null;
/* 5252 */     String resourceid = request.getParameter("resourceid");
/* 5253 */     String resourcetypedisplayname = request.getParameter("resourcetypedisplayname");
/* 5254 */     String type = request.getParameter("type");
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 5259 */       String query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where AM_ManagedObject.type='" + type + "'";
/* 5260 */       results = AMConnectionPool.executeQueryStmt(query);
/* 5261 */       AMActionForm amForm = (AMActionForm)form;
/* 5262 */       amForm.setGatewayCheckStatus("true");
/* 5263 */       Properties current = new Properties();
/* 5264 */       while (results.next()) {
/* 5265 */         Properties enable = new Properties();
/*      */         
/* 5267 */         ArrayList existing = (ArrayList)DataCollectionComponent.generalTable.get(results.getString("RESOURCEID").trim());
/* 5268 */         if (results.getString("RESOURCEID").equals(resourceid))
/*      */         {
/* 5270 */           current.setProperty("RESOURCEID", results.getString("RESOURCEID"));
/* 5271 */           current.setProperty("DISPLAYNAME", results.getString("DISPLAYNAME"));
/* 5272 */           currentDetails.add(current);
/*      */         }
/* 5274 */         else if (!results.getString("RESOURCEID").equals(resourceid))
/*      */         {
/* 5276 */           enable.setProperty("RESOURCEID", results.getString("RESOURCEID"));
/* 5277 */           enable.setProperty("DISPLAYNAME", results.getString("DISPLAYNAME"));
/*      */           
/* 5279 */           enableDetails.add(enable);
/*      */         }
/*      */       }
/* 5282 */       request.setAttribute("enable", enableDetails);
/* 5283 */       request.setAttribute("current", current);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5287 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 5290 */       AMConnectionPool.closeStatement(results);
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 5295 */       Map resource = new HashMap();
/* 5296 */       List a = new ArrayList();
/* 5297 */       ArrayList existing = null;
/* 5298 */       String query = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where AM_ManagedObject.type='" + type + "'";
/* 5299 */       results = AMConnectionPool.executeQueryStmt(query);
/* 5300 */       String resourcetypes = "POOLMONITORING,JOBMONITORING,SPOOLMONITORING,MESSAGEMONITORING,SUBSYSTEMMONITORING,DISKMONITORING,PRINTERMONITORING,PROBLEMMONITORING,HISTORYMONITORING,";
/* 5301 */       String[] resourcetypedisplaynames = resourcetypes.split(",");
/* 5302 */       existing = (ArrayList)DataCollectionComponent.generalTable.get(request.getParameter("resourceid").trim());
/*      */       
/* 5304 */       for (String resourcetype : resourcetypedisplaynames)
/*      */       {
/* 5306 */         Map p = new HashMap();
/* 5307 */         p.put("resourcetype", resourcetype);
/* 5308 */         if ((existing != null) && (existing.contains(resourcetype))) {
/* 5309 */           p.put("status", "false");
/*      */         }
/*      */         else {
/* 5312 */           p.put("status", "true");
/*      */         }
/* 5314 */         a.add(p);
/* 5315 */         resource.put("resourcedetails", a);
/*      */       }
/* 5317 */       request.setAttribute("resource", resource);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5321 */       e.printStackTrace();
/*      */     }
/* 5323 */     return new ActionForward("/jsp/as400/enableData.jsp?resourceid=" + resourceid + "&resourcetype=AS400/iSeries&resourcetypedisplayname=" + resourcetypedisplayname);
/*      */   }
/*      */   
/*      */   public ActionForward subsystemMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 5327 */     Map data = new HashMap();
/* 5328 */     List a = new ArrayList();
/* 5329 */     ResultSet rs = null;
/* 5330 */     String query = null;
/*      */     try {
/* 5332 */       response.setContentType("text/html; charset=UTF-8");
/* 5333 */       String resid = request.getParameter("resourceid");
/* 5334 */       query = "select s.* from AM_AS400_SUBSYSTEM s left join AM_AS400_SUBSYSTEMMONITORING sm on sm.ID = s.ID and sm.COLLECTIONTIME = s.COLLECTIONTIME where sm.ID is null and s.RESOURCEID=" + resid + "";
/* 5335 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 5336 */       while (rs.next()) {
/* 5337 */         Map p = new HashMap();
/* 5338 */         p.put("ID", rs.getString("ID"));
/* 5339 */         p.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 5340 */         p.put("NAME", rs.getString("NAME"));
/* 5341 */         p.put("LIBRARY", rs.getString("LIBRARY"));
/* 5342 */         p.put("ACTIVE_JOBS", rs.getString("ACTIVE_JOBS"));
/* 5343 */         p.put("TOTAL_STORAGE", rs.getString("TOTAL_STORAGE"));
/* 5344 */         p.put("STATUS", rs.getString("STATUS"));
/*      */         try {
/* 5346 */           String timeAdded = "" + System.currentTimeMillis();
/* 5347 */           p.put("COLLECTIONTIME", timeAdded);
/*      */         } catch (Exception e) {
/* 5349 */           p.put("COLLECTIONTIME", "0");
/*      */         }
/* 5351 */         a.add(p);
/* 5352 */         data.put("subsystem", a);
/*      */       }
/* 5354 */       AMConnectionPool.closeStatement(rs);
/* 5355 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 5358 */       e.printStackTrace();
/*      */     } finally {
/* 5360 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 5362 */     return new ActionForward("/jsp/as400/subsystemMonitor.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward addSubsystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 5369 */     HashMap<String, Map> data = new HashMap();
/*      */     
/* 5371 */     Statement toinsert = null;
/* 5372 */     String hostname = "";
/* 5373 */     String resid = "";
/* 5374 */     JSONArray subids = new JSONArray();
/* 5375 */     Pattern quotePattern = Pattern.compile("&quot;");
/*      */     try {
/* 5377 */       response.setContentType("text/html; charset=UTF-8");
/* 5378 */       resid = request.getParameter("resourceid");
/* 5379 */       String jsonlist = request.getParameter("subs");
/* 5380 */       jsonlist = jsonlist.trim();
/* 5381 */       Matcher matcher = quotePattern.matcher(jsonlist);
/* 5382 */       String sublist = matcher.replaceAll("\"");
/*      */       try {
/* 5384 */         AMLog.debug("AS400 Action: Monitored subsystems to add " + sublist);
/* 5385 */         JSONArray jsublist = new JSONArray(sublist);
/* 5386 */         int substoadd = jsublist.length();int count = 0;
/*      */         
/* 5388 */         for (int i = 0; i < substoadd; i++) {
/* 5389 */           JSONObject subObj = jsublist.getJSONObject(count);
/* 5390 */           HashMap<String, String> p = new HashMap();
/* 5391 */           p.put("ID", subObj.getString("ID"));
/* 5392 */           p.put("RESOURCEID", resid);
/* 5393 */           p.put("NAME", subObj.getString("NAME"));
/* 5394 */           p.put("LIBRARY", subObj.getString("LIB"));
/* 5395 */           p.put("ACTIVE_JOBS", "0");
/* 5396 */           p.put("TOTAL_STORAGE", "0");
/* 5397 */           p.put("STATUS", "-");
/* 5398 */           p.put("COLLECTIONTIME", subObj.getString("COLLECTIONTIME"));
/*      */           
/* 5400 */           data.put(subObj.getString("ID"), p);
/* 5401 */           count++;
/*      */         }
/* 5403 */         request.setAttribute("data", data);
/*      */       } catch (Exception e) {
/* 5405 */         e.printStackTrace();
/* 5406 */         AMLog.debug("AS400 Action: Error while adding subsystemsto Monitored subsystems" + e.getMessage());
/*      */       }
/* 5408 */       hostname = Constants.getResName(resid);
/*      */     } catch (Exception e) {
/* 5410 */       e.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/* 5414 */       AMConnectionPool.getInstance();toinsert = AMConnectionPool.getConnection().createStatement();
/* 5415 */       Collection col = data.values();
/* 5416 */       Iterator iter = col.iterator();
/* 5417 */       while (iter.hasNext())
/*      */       {
/* 5419 */         HashMap map = (HashMap)iter.next();
/* 5420 */         Integer resrcid = Integer.valueOf(Integer.parseInt(map.get("RESOURCEID").toString()));
/* 5421 */         String lib_name = (String)map.get("LIBRARY");
/* 5422 */         String disp_name = (String)map.get("NAME");
/* 5423 */         String subsystem_name = (String)map.get("NAME");
/*      */         
/* 5425 */         disp_name = "SUBSYSTEM-" + disp_name + "-" + lib_name;
/* 5426 */         subsystem_name = hostname + ":" + "SUBSYSTEM" + "-" + subsystem_name + "-" + lib_name;
/*      */         
/* 5428 */         int subsystemrid = DataCollectionDBUtil.CheckandinsertAMMO(subsystem_name, "AS400_Subsystem", disp_name, resrcid.intValue());
/* 5429 */         String SqlIns = "insert into AM_AS400_SUBSYSTEMMONITORING values(" + subsystemrid + "," + map.get("ID") + ",'" + map.get("LIBRARY") + "'," + map.get("ACTIVE_JOBS") + "," + map.get("TOTAL_STORAGE") + ",'" + map.get("STATUS") + "'," + map.get("COLLECTIONTIME") + ")";
/* 5430 */         toinsert.addBatch(SqlIns);
/*      */         
/* 5432 */         subids.put(map.get("ID"));
/*      */       }
/* 5434 */       toinsert.executeBatch();
/*      */     }
/*      */     catch (Exception e) {
/* 5437 */       e.printStackTrace();
/*      */     } finally {
/* 5439 */       toinsert.close();
/*      */     }
/* 5441 */     AMLog.debug("AS400 Action: " + subids + " Subsystem(s) Added");
/* 5442 */     PrintWriter pw = response.getWriter();
/* 5443 */     pw.print(subids);
/* 5444 */     pw.flush();
/*      */     
/* 5446 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward jobActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5451 */     String resid = null;
/* 5452 */     String query = null;
/*      */     try {
/* 5454 */       response.setContentType("text/html; charset=UTF-8");
/* 5455 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 5456 */       resid = request.getParameter("resourceid");
/* 5457 */       String jobrid = request.getParameter("rowids");
/* 5458 */       String fn = request.getParameter("fn");
/* 5459 */       String isSpecificMonitor = request.getParameter("specificmonitor");
/* 5460 */       if ((fn.equals("removejob")) && (isSpecificMonitor.equals("true")))
/*      */       {
/*      */         try {
/* 5463 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5464 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MONITOREDJOBS where RESOURCEID in (" + jobrid + ")");
/* 5465 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MinMaxAvgData where RESID in (" + jobrid + ")");
/* 5466 */           String[] childids = jobrid.split(",");
/* 5467 */           DataCollectionControllerUtil.deleteChild(childids);
/* 5468 */           AMLog.debug("AS400 Action: Successfully removed jobs from monitoring for resid: " + resid);
/*      */         } catch (Exception e) {
/* 5470 */           AMLog.debug("AS400 Action: Problem while removing jobs from monitoring due to error: " + e.getMessage() + " for resid " + resid);
/* 5471 */           e.printStackTrace();
/*      */         }
/*      */         
/* 5474 */         return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=4", true);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 5479 */         ResultSet rs = null;
/* 5480 */         String jobName = "";String jobUser = "";String toRemove = "-";
/* 5481 */         int id = 0;
/* 5482 */         Hashtable as400Response = new Hashtable();
/*      */         
/* 5484 */         AS400 as400 = null;
/* 5485 */         String dispname = null;
/* 5486 */         String username = null;
/* 5487 */         String password = null;
/* 5488 */         String monname = " ";
/* 5489 */         String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where resourceid=" + resid;
/* 5490 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 5491 */         if (rs.next()) {
/* 5492 */           monname = rs.getString("RESOURCENAME");
/* 5493 */           dispname = rs.getString("DISPLAYNAME");
/* 5494 */           request.setAttribute("dispname", dispname);
/*      */         }
/*      */         
/* 5497 */         query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 5498 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 5499 */         if (rs.next()) {
/* 5500 */           username = rs.getString("USERNAME");
/* 5501 */           password = rs.getString("pass");
/*      */         }
/*      */         
/*      */ 
/* 5505 */         as400 = new AS400(monname, username, password);
/* 5506 */         as400.setGuiAvailable(false);
/* 5507 */         SocketProperties socket = new SocketProperties();
/* 5508 */         socket.setSoTimeout(60000);
/* 5509 */         as400.setSocketProperties(socket);
/*      */         
/* 5511 */         if (isSpecificMonitor.equals("true")) {
/* 5512 */           query = "select mo.DISPLAYNAME,bi.* from AM_AS400_MONITOREDJOBS bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=bi.RESOURCEID and bi.RESOURCEID=pcm.CHILDID and pcm.CHILDID in(" + jobrid + ")";
/*      */         } else {
/* 5514 */           query = "select JOBNAME as DISPLAYNAME,USERNAME,NUMBER,ID  from AM_AS400_JOBS where resourceid=" + resid + " and ID in(" + jobrid + ")";
/*      */         }
/* 5516 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 5517 */         while (rs.next()) {
/* 5518 */           jobName = rs.getString("DISPLAYNAME");
/* 5519 */           if (isSpecificMonitor.equals("true")) {
/* 5520 */             jobName = jobName.substring(jobName.indexOf(toRemove) + toRemove.length());
/*      */             try {
/* 5522 */               jobName = jobName.substring(0, jobName.indexOf(toRemove));
/*      */             } catch (Exception e) {
/* 5524 */               AMLog.debug("AS400 Action:  Problem while getting job name by substring for job : " + rs.getString("DISPLAYNAME") + " Resource id : " + resid + " due to error : " + e.getMessage() + ".So job name is " + jobName);
/*      */             }
/*      */           }
/* 5527 */           jobUser = rs.getString("USERNAME");
/* 5528 */           String jobNumber = rs.getString("NUMBER");
/* 5529 */           id = rs.getInt("ID");
/* 5530 */           Integer num = Integer.valueOf(Integer.parseInt(jobNumber));
/* 5531 */           String format = String.format("%%0%dd", new Object[] { Integer.valueOf(6) });
/* 5532 */           jobNumber = String.format(format, new Object[] { num });
/* 5533 */           Job sp = new Job(as400, jobName, jobUser, jobNumber);
/*      */           
/* 5535 */           if (fn.equals("End")) {
/*      */             try
/*      */             {
/* 5538 */               sp.end(0);
/*      */               
/* 5540 */               String updqry = "";
/* 5541 */               if (isSpecificMonitor.equals("true")) {
/* 5542 */                 updqry = "update AM_AS400_MONITOREDJOBS set STATUS='END ' where ID=" + id;
/*      */               } else {
/* 5544 */                 updqry = "update AM_AS400_JOBS set STATUS='END ' where ID=" + id;
/*      */               }
/* 5546 */               toinsert.addBatch(updqry);
/* 5547 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing job action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 5549 */               setIsActionMade(true);
/* 5550 */               as400Response.put("failed", de.getMessage());
/* 5551 */               setAs400Responsess(as400Response);
/* 5552 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=4", true);
/*      */             }
/*      */             
/* 5555 */           } else if (fn.equals("Hold")) {
/*      */             try
/*      */             {
/* 5558 */               sp.hold(true);
/*      */               
/* 5560 */               String updqry = "";
/* 5561 */               if (isSpecificMonitor.equals("true")) {
/* 5562 */                 updqry = "update AM_AS400_MONITOREDJOBS set STATUS='HLD ' where ID=" + id;
/*      */               } else {
/* 5564 */                 updqry = "update AM_AS400_JOBS set STATUS='HLD ' where ID=" + id;
/*      */               }
/* 5566 */               toinsert.addBatch(updqry);
/* 5567 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing job action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 5569 */               setIsActionMade(true);
/* 5570 */               as400Response.put("failed", de.getMessage());
/* 5571 */               setAs400Responsess(as400Response);
/* 5572 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=4", true);
/*      */             }
/*      */             
/*      */           }
/* 5576 */           else if (fn.equals("Release")) {
/*      */             try {
/* 5578 */               sp.release();
/*      */               
/* 5580 */               String updqry = "";
/* 5581 */               if (isSpecificMonitor.equals("true")) {
/* 5582 */                 updqry = "update AM_AS400_MONITOREDJOBS set STATUS='RUN ' where ID=" + id;
/*      */               } else {
/* 5584 */                 updqry = "update AM_AS400_JOBS set STATUS='RUN ' where ID=" + id;
/*      */               }
/* 5586 */               toinsert.addBatch(updqry);
/* 5587 */               AMLog.debug("AS400 Action: Update query " + updqry + " after executing job action for Host: " + monname);
/*      */             } catch (Exception de) {
/* 5589 */               setIsActionMade(true);
/* 5590 */               as400Response.put("failed", de.getMessage());
/* 5591 */               setAs400Responsess(as400Response);
/* 5592 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=4", true);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 5601 */           toinsert.executeBatch();
/*      */         } catch (Exception e) {
/* 5603 */           AMLog.debug("AS400 Action: Problem while executing batch statements in Job actions due to error: " + e.getMessage() + " for resid" + resid);
/* 5604 */           e.printStackTrace();
/*      */         } finally {
/* 5606 */           toinsert.close();
/* 5607 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 5609 */         setIsActionMade(true);
/* 5610 */         as400Response.put("success", "Job Action Executed");
/* 5611 */         setAs400Responsess(as400Response);
/*      */       } catch (Exception e1) {
/* 5613 */         e1.printStackTrace();
/*      */       }
/*      */       
/* 5616 */       return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=4", true);
/*      */     }
/*      */     catch (Exception e) {
/* 5619 */       e.printStackTrace();
/* 5620 */       AMLog.debug("AS400 Action: Problem while performing job action due to error: " + e.getMessage() + " for resid" + resid);
/*      */     }
/* 5622 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward subsystemActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 5627 */     String resid = null;
/* 5628 */     String query = null;
/*      */     try {
/* 5630 */       response.setContentType("text/html; charset=UTF-8");
/* 5631 */       AMConnectionPool.getInstance();Statement toinsert = AMConnectionPool.getConnection().createStatement();
/* 5632 */       resid = request.getParameter("resourceid");
/* 5633 */       String subrid = request.getParameter("rowids");
/* 5634 */       String fn = request.getParameter("fn");
/* 5635 */       String isSpecificMonitor = request.getParameter("specificmonitor");
/* 5636 */       if ((fn.equals("removesub")) && (isSpecificMonitor.equals("true"))) {
/*      */         try {
/* 5638 */           response.setContentType("text/html; charset=UTF-8");
/* 5639 */           resid = request.getParameter("resourceid");
/* 5640 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5641 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_SUBSYSTEMMONITORING where RESOURCEID in (" + subrid + ")");
/* 5642 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MinMaxAvgData where RESID in (" + subrid + ")");
/* 5643 */           String[] childids = subrid.split(",");
/* 5644 */           DataCollectionControllerUtil.deleteChild(childids);
/* 5645 */           AMLog.debug("AS400 Action: Successfully removed subsystems from monitoring for resid: " + resid);
/*      */         } catch (Exception e) {
/* 5647 */           AMLog.debug("AS400 Action: Problem while removing subsystems from monitoring due to error: " + e.getMessage() + " for resid" + resid);
/* 5648 */           e.printStackTrace();
/*      */         }
/*      */         
/* 5651 */         return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 5656 */         ResultSet rs = null;
/* 5657 */         String subName = "";String subLibrary = "";
/* 5658 */         String dname = "";
/* 5659 */         Hashtable as400Response = new Hashtable();
/* 5660 */         int id = 0;
/*      */         
/* 5662 */         AS400 as400 = null;
/* 5663 */         String dispname = null;
/* 5664 */         String username = null;
/* 5665 */         String password = null;
/* 5666 */         String monname = " ";
/* 5667 */         String query1 = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where resourceid=" + resid;
/* 5668 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 5669 */         if (rs.next()) {
/* 5670 */           monname = rs.getString("RESOURCENAME");
/* 5671 */           dispname = rs.getString("DISPLAYNAME");
/* 5672 */           request.setAttribute("dispname", dispname);
/*      */         }
/* 5674 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 5676 */         query1 = "select USERNAME," + DBQueryUtil.decode("PASSWORD") + " as pass from HostDetails where RESOURCENAME='" + monname + "'";
/* 5677 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 5678 */         if (rs.next()) {
/* 5679 */           username = rs.getString("USERNAME");
/* 5680 */           password = rs.getString("pass");
/*      */         }
/* 5682 */         AMConnectionPool.closeStatement(rs);
/*      */         
/* 5684 */         as400 = new AS400(monname, username, password);
/* 5685 */         as400.setGuiAvailable(false);
/* 5686 */         SocketProperties socket = new SocketProperties();
/* 5687 */         socket.setSoTimeout(60000);
/* 5688 */         as400.setSocketProperties(socket);
/*      */         
/* 5690 */         if (isSpecificMonitor.equals("true")) {
/* 5691 */           query = "select mo.RESOURCENAME as DISPLAYNAME,bi.* from AM_AS400_SUBSYSTEMMONITORING bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.RESOURCEID=bi.RESOURCEID and bi.RESOURCEID=pcm.CHILDID and pcm.CHILDID in(" + subrid + ")";
/*      */         } else {
/* 5693 */           query = "select NAME as DISPLAYNAME,LIBRARY,ID from AM_AS400_SUBSYSTEM where resourceid=" + resid + " and ID in(" + subrid + ")";
/*      */         }
/* 5695 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 5696 */         while (rs.next()) {
/* 5697 */           subLibrary = rs.getString("LIBRARY");
/* 5698 */           dname = rs.getString("DISPLAYNAME");
/* 5699 */           String toRemove = ":SUBSYSTEM-";
/* 5700 */           if (isSpecificMonitor.equals("true")) {
/* 5701 */             subName = dname.substring(dname.indexOf(toRemove) + toRemove.length(), dname.lastIndexOf("-"));
/*      */           } else {
/* 5703 */             subName = dname;
/*      */           }
/* 5705 */           id = rs.getInt("ID");
/*      */           
/* 5707 */           Subsystem sp = new Subsystem(as400, subLibrary, subName);
/*      */           
/* 5709 */           if (fn.equals("Delete")) {
/*      */             try
/*      */             {
/* 5712 */               sp.delete();
/*      */               
/* 5714 */               AMLog.debug("AS400 Action: Subsystem " + subName + "." + subLibrary + " deleted  for Host: " + monname + " of resid" + resid);
/*      */             } catch (Exception de) {
/* 5716 */               setIsActionMade(true);
/* 5717 */               as400Response.put("failed", de.getMessage());
/* 5718 */               setAs400Responsess(as400Response);
/* 5719 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */             }
/* 5721 */           } else if (fn.equals("End"))
/*      */           {
/*      */             try
/*      */             {
/* 5725 */               sp.endImmediately();
/*      */               
/* 5727 */               String updqry = "";
/* 5728 */               if (isSpecificMonitor.equals("true")) {
/* 5729 */                 updqry = "update AM_AS400_SUBSYSTEMMONITORING set STATUS='END' where ID=" + id;
/*      */               } else {
/* 5731 */                 updqry = "update AM_AS400_SUBSYSTEM set STATUS='END' where ID=" + id;
/*      */               }
/* 5733 */               toinsert.addBatch(updqry);
/* 5734 */               AMLog.debug("AS400 Action: Subsystem " + subName + "." + subLibrary + " ended  for Host: " + monname + " of resid" + resid);
/*      */             } catch (Exception de) {
/* 5736 */               setIsActionMade(true);
/* 5737 */               as400Response.put("failed", de.getMessage());
/* 5738 */               setAs400Responsess(as400Response);
/* 5739 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */             }
/*      */             
/*      */           }
/* 5743 */           else if (fn.equals("Start")) {
/*      */             try
/*      */             {
/* 5746 */               sp.start();
/*      */               
/* 5748 */               String updqry = "";
/* 5749 */               if (isSpecificMonitor.equals("true")) {
/* 5750 */                 updqry = "update AM_AS400_SUBSYSTEMMONITORING set STATUS='ACTIVE' where ID=" + id;
/*      */               } else {
/* 5752 */                 updqry = "update AM_AS400_SUBSYSTEM set STATUS='ACTIVE' where ID=" + id;
/*      */               }
/* 5754 */               toinsert.addBatch(updqry);
/* 5755 */               AMLog.debug("AS400 Action: Subsystem " + subName + "." + subLibrary + " started  for Host: " + monname + " of resid" + resid);
/*      */             } catch (Exception de) {
/* 5757 */               setIsActionMade(true);
/* 5758 */               as400Response.put("failed", de.getMessage());
/* 5759 */               setAs400Responsess(as400Response);
/* 5760 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */             }
/*      */             
/* 5763 */           } else if (fn.equals("Refresh")) {
/*      */             try
/*      */             {
/* 5766 */               sp.refresh();
/*      */               
/* 5768 */               AMLog.debug("AS400 Action: Subsystem " + subName + "." + subLibrary + " refreshed  for Host: " + monname + " of resid" + resid);
/*      */             } catch (Exception de) {
/* 5770 */               setIsActionMade(true);
/* 5771 */               as400Response.put("failed", de.getMessage());
/* 5772 */               setAs400Responsess(as400Response);
/* 5773 */               return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 5781 */           toinsert.executeBatch();
/*      */         } catch (Exception e) {
/* 5783 */           AMLog.debug("AS400 Action: Problem while executing batch statements in Subsystem actions due to error: " + e.getMessage() + " for resid" + resid);
/* 5784 */           e.printStackTrace();
/*      */         } finally {
/* 5786 */           toinsert.close();
/* 5787 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/* 5789 */         setIsActionMade(true);
/* 5790 */         as400Response.put("success", "Subsystem Action Executed");
/* 5791 */         setAs400Responsess(as400Response);
/*      */       }
/*      */       catch (Exception e1) {
/* 5794 */         e1.printStackTrace();
/*      */       }
/*      */       
/* 5797 */       return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=10", true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 5801 */       e.printStackTrace();
/* 5802 */       AMLog.debug("AS400 Action: Problem while performing subsystem action due to error: " + e.getMessage() + " for resid " + resid);
/*      */     }
/* 5804 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward historyLogDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 5808 */     Map data = new HashMap();
/* 5809 */     List a = new ArrayList();
/* 5810 */     ResultSet rs1 = null;ResultSet rs2 = null;
/* 5811 */     String query1 = "";String query2 = "";
/* 5812 */     boolean disable = false;
/*      */     try {
/* 5814 */       response.setContentType("text/html; charset=UTF-8");
/* 5815 */       String resid = request.getParameter("resourceid");
/*      */       
/* 5817 */       query1 = "select * from AM_AS400_HISTORYLOGS where RESOURCEID=" + resid + " order by TYPE";
/*      */       
/* 5819 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 5820 */       while (rs1.next()) {
/* 5821 */         Map p = new HashMap();
/* 5822 */         p.put("ID", rs1.getString("ID"));
/* 5823 */         p.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 5824 */         p.put("MSG_ID", rs1.getString("MSG_ID"));
/* 5825 */         p.put("MSG_ID_STATUS", rs1.getString("MSG_ID_STATUS"));
/* 5826 */         p.put("SEVERITY", rs1.getString("SEVERITY"));
/* 5827 */         p.put("SEVSTATUS", rs1.getString("SEVSTATUS"));
/* 5828 */         p.put("TYPE", rs1.getString("TYPE"));
/* 5829 */         p.put("MESSAGE", rs1.getString("MESSAGE"));
/* 5830 */         p.put("MSGSTATUS", rs1.getString("MSGSTATUS"));
/* 5831 */         p.put("DATE", rs1.getString("DATE"));
/* 5832 */         a.add(p);
/* 5833 */         data.put("historylog", a);
/*      */       }
/*      */       
/* 5836 */       query2 = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 5837 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/* 5838 */       while (rs2.next()) {
/* 5839 */         if (rs2.getString("COMPONENTNAME").equals("HISTORYMONITORING")) {
/* 5840 */           disable = true;
/* 5841 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/*      */       
/* 5845 */       request.setAttribute("data", data);
/*      */       try {
/* 5847 */         String serverUrl = GenURLForHTML.getServerUrl();
/* 5848 */         String hostname = Constants.getResName(resid);
/* 5849 */         serverUrl = serverUrl + "/Debug-Info/AS400/" + hostname + "/MatchedHistoryLogs.html";
/* 5850 */         request.setAttribute("Debug_Info_HlMsg", serverUrl);
/*      */       } catch (Exception e) {
/* 5852 */         e.printStackTrace();
/*      */       }
/*      */       long maxColTime;
/* 5855 */       try { String[] hisLogMessage = Constants.getMonitorMessage(resid, "as400_historylog");
/* 5856 */         maxColTime = getMaxCollectionTime(Integer.parseInt(resid));
/* 5857 */         long msgUpdatedTime = Long.parseLong(hisLogMessage[2]);
/* 5858 */         if (msgUpdatedTime >= maxColTime) {
/* 5859 */           request.setAttribute("msgType", hisLogMessage[0]);
/* 5860 */           request.setAttribute("monMsg", hisLogMessage[1]);
/*      */         } else {
/* 5862 */           request.setAttribute("msgType", "");
/* 5863 */           request.setAttribute("monMsg", "");
/*      */         }
/*      */       } catch (Exception e) {
/* 5866 */         e.printStackTrace();
/*      */       }
/*      */       
/* 5869 */       String o = request.getParameter("noredirect");
/* 5870 */       if ((o != null) && (o.equals("false")))
/*      */       {
/* 5872 */         return mapping.findForward("historylog");
/*      */       }
/*      */     } catch (Exception e) {
/* 5875 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 5878 */       AMConnectionPool.closeStatement(rs1);
/* 5879 */       AMConnectionPool.closeStatement(rs2);
/*      */     }
/* 5881 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward historyFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 5885 */     Map map = new HashMap();
/* 5886 */     Map map1 = new HashMap();
/* 5887 */     List b = new ArrayList();
/* 5888 */     ResultSet rs1 = null;
/* 5889 */     ResultSet rs2 = null;
/* 5890 */     ResultSet rs3 = null;
/*      */     try {
/* 5892 */       response.setContentType("text/html; charset=UTF-8");
/* 5893 */       String resid = null;
/* 5894 */       String status = null;
/* 5895 */       String category = request.getParameter("catvalue");
/* 5896 */       String monitor = request.getParameter("monvalue");
/* 5897 */       if ((category != null) && (monitor != null))
/*      */       {
/* 5899 */         resid = monitor;
/* 5900 */         status = category;
/*      */       }
/*      */       else
/*      */       {
/* 5904 */         resid = request.getParameter("resourceid");
/* 5905 */         status = request.getParameter("status");
/*      */       }
/*      */       
/*      */ 
/* 5909 */       String query1 = null;
/* 5910 */       String query2 = null;
/* 5911 */       StringBuilder resIds = new StringBuilder();
/* 5912 */       if (!resid.equals("ALL"))
/*      */       {
/* 5914 */         query1 = "select DISPLAYNAME from AM_ManagedObject where TYPE='AS400/iSeries' and RESOURCEID=" + resid + "";
/* 5915 */         rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 5916 */         while (rs1.next()) {
/* 5917 */           Map h = new HashMap();
/* 5918 */           h.put("RESOURCEID", resid);
/* 5919 */           h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 5920 */           b.add(h);
/* 5921 */           map.put("ResIds", b);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 5926 */       query1 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 5927 */       rs1 = AMConnectionPool.executeQueryStmt(query1);
/* 5928 */       while (rs1.next()) {
/* 5929 */         Map h = new HashMap();
/* 5930 */         h.put("RESOURCEID", rs1.getString("RESOURCEID"));
/* 5931 */         resIds.append(rs1.getString("RESOURCEID")).append(",");
/* 5932 */         h.put("DISPLAYNAME", rs1.getString("DISPLAYNAME"));
/* 5933 */         b.add(h);
/* 5934 */         map.put("ResIds", b);
/* 5935 */         resid = resIds.substring(0, resIds.length() - 1);
/*      */       }
/*      */       
/*      */ 
/* 5939 */       request.setAttribute("rescolls", map);
/*      */       try
/*      */       {
/* 5942 */         String query3 = "select RESOURCEID,DISPLAYNAME  from AM_ManagedObject where TYPE='AS400/iSeries'";
/* 5943 */         rs3 = AMConnectionPool.executeQueryStmt(query3);
/* 5944 */         List b3 = new ArrayList();
/* 5945 */         while (rs3.next()) {
/* 5946 */           Map h = new HashMap();
/* 5947 */           h.put("RESOURCEID", rs3.getString("RESOURCEID"));
/* 5948 */           h.put("DISPLAYNAME", rs3.getString("DISPLAYNAME"));
/* 5949 */           b3.add(h);
/* 5950 */           map1.put("ResIds", b3);
/*      */         }
/* 5952 */         request.setAttribute("as400s", map1);
/*      */       } catch (Exception e) {
/* 5954 */         e.printStackTrace();
/*      */       }
/*      */       
/* 5957 */       Map data = new HashMap();
/*      */       
/* 5959 */       if (status.equals("allmsg")) {
/* 5960 */         query2 = "select * from AM_AS400_HISTORYLOGS where RESOURCEID in(" + resid + ") order by TYPE";
/*      */       }
/* 5962 */       else if (status.equals("clear")) {
/* 5963 */         query2 = "select * from AM_AS400_HISTORYLOGS where RESOURCEID in(" + resid + ") and 1 not in (MSG_ID_STATUS,SEVSTATUS,MSGSTATUS) and 4 not in  (MSG_ID_STATUS,SEVSTATUS,MSGSTATUS)";
/*      */       }
/* 5965 */       else if (status.equals("warning")) {
/* 5966 */         query2 = "select * from AM_AS400_HISTORYLOGS where RESOURCEID in(" + resid + ") and 1 not in (MSG_ID_STATUS,SEVSTATUS,MSGSTATUS) and 4 in  (MSG_ID_STATUS,SEVSTATUS,MSGSTATUS)";
/*      */       }
/* 5968 */       else if (status.equals("critical")) {
/* 5969 */         query2 = "select * from AM_AS400_HISTORYLOGS where RESOURCEID in(" + resid + ") and 1 in (MSG_ID_STATUS,SEVSTATUS,MSGSTATUS)";
/*      */       }
/*      */       
/* 5972 */       rs2 = AMConnectionPool.executeQueryStmt(query2);
/*      */       
/* 5974 */       while (rs2.next())
/*      */       {
/* 5976 */         if (data.containsKey(rs2.getString("RESOURCEID")))
/*      */         {
/* 5978 */           ArrayList a1 = (ArrayList)data.get(rs2.getString("RESOURCEID"));
/* 5979 */           Map p = new HashMap();
/* 5980 */           p.put("ID", rs2.getString("ID"));
/* 5981 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 5982 */           p.put("MSG_ID", rs2.getString("MSG_ID"));
/* 5983 */           p.put("MSG_ID_STATUS", rs2.getString("MSG_ID_STATUS"));
/* 5984 */           p.put("SEVERITY", rs2.getString("SEVERITY"));
/* 5985 */           p.put("SEVSTATUS", rs2.getString("SEVSTATUS"));
/* 5986 */           p.put("TYPE", rs2.getString("TYPE"));
/* 5987 */           p.put("MESSAGE", rs2.getString("MESSAGE"));
/* 5988 */           p.put("MSGSTATUS", rs2.getString("MSGSTATUS"));
/* 5989 */           p.put("DATE", rs2.getString("DATE"));
/* 5990 */           a1.add(p);
/* 5991 */           data.put(rs2.getString("RESOURCEID"), a1);
/*      */         }
/*      */         else
/*      */         {
/* 5995 */           ArrayList a = new ArrayList();
/* 5996 */           Map p = new HashMap();
/* 5997 */           p.put("ID", rs2.getString("ID"));
/* 5998 */           p.put("RESOURCEID", rs2.getString("RESOURCEID"));
/* 5999 */           p.put("MSG_ID", rs2.getString("MSG_ID"));
/* 6000 */           p.put("MSG_ID_STATUS", rs2.getString("MSG_ID_STATUS"));
/* 6001 */           p.put("SEVERITY", rs2.getString("SEVERITY"));
/* 6002 */           p.put("SEVSTATUS", rs2.getString("SEVSTATUS"));
/* 6003 */           p.put("TYPE", rs2.getString("TYPE"));
/* 6004 */           p.put("MESSAGE", rs2.getString("MESSAGE"));
/* 6005 */           p.put("MSGSTATUS", rs2.getString("MSGSTATUS"));
/* 6006 */           p.put("DATE", rs2.getString("DATE"));
/* 6007 */           a.add(p);
/* 6008 */           data.put(rs2.getString("RESOURCEID"), a);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 6013 */       request.setAttribute("data", data);
/*      */     }
/*      */     catch (Exception e) {
/* 6016 */       e.printStackTrace();
/*      */     } finally {
/* 6018 */       AMConnectionPool.closeStatement(rs1);
/* 6019 */       AMConnectionPool.closeStatement(rs2);
/* 6020 */       AMConnectionPool.closeStatement(rs3);
/*      */     }
/* 6022 */     return new ActionForward("/jsp/as400/historyFilter.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward queueDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6027 */     Map data = new HashMap();
/* 6028 */     List dq = new ArrayList();
/* 6029 */     List jq = new ArrayList();
/* 6030 */     List oq = new ArrayList();
/* 6031 */     ResultSet rs = null;
/* 6032 */     String query = "";
/* 6033 */     boolean disable = false;
/*      */     try {
/* 6035 */       response.setContentType("text/html; charset=UTF-8");
/* 6036 */       String resid = request.getParameter("resourceid");
/* 6037 */       query = "select COMPONENTNAME from AM_MONITOR_DISABLECOLLECTION where RESOURCEID = " + resid;
/* 6038 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6039 */       while (rs.next()) {
/* 6040 */         if (rs.getString("COMPONENTNAME").equals("JOBMONITORING")) {
/* 6041 */           disable = true;
/* 6042 */           request.setAttribute("disable", Boolean.valueOf(disable));
/*      */         }
/*      */       }
/* 6045 */       AMConnectionPool.closeStatement(rs);
/* 6046 */       query = "select ac.collectiontime as coltime,mo.DISPLAYNAME from AM_AS400_CONFIGURATION ac,AM_ManagedObject mo where mo.RESOURCEID=ac.RESOURCEID  and ac.RESOURCEID = " + resid;
/* 6047 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6048 */       String maxcoltime = "0";String monname = "";
/* 6049 */       if (rs.next()) {
/* 6050 */         maxcoltime = rs.getString("coltime");
/* 6051 */         monname = rs.getString("DISPLAYNAME");
/*      */       }
/* 6053 */       request.setAttribute("monname", monname);
/* 6054 */       AMConnectionPool.closeStatement(rs);
/* 6055 */       ArrayList buffdata = new ArrayList();
/* 6056 */       query = "select mo.DISPLAYNAME,mo.RESOURCENAME,mo.RESOURCEID as QUEUERID,bi.* from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_AS400_MONITOREDDATAQUEUES bi on mo.RESOURCEID=bi.RESOURCEID and bi.COLLECTIONTIME =" + maxcoltime + " where pcm.PARENTID=" + resid + " and mo.TYPE='AS400_DTAQ'";
/* 6057 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6058 */       int dqn = 0;
/* 6059 */       while (rs.next()) {
/* 6060 */         Map p = new HashMap();
/* 6061 */         String queuerid = "";String resname = "";String dispname = "";
/* 6062 */         resname = rs.getString("RESOURCENAME");
/* 6063 */         dispname = rs.getString("DISPLAYNAME");
/* 6064 */         queuerid = rs.getString("QUEUERID");
/* 6065 */         buffdata.add(queuerid);
/* 6066 */         String[] monqueueids = { "", "" };
/*      */         try {
/* 6068 */           String str1 = ":DTAQ-";String str2 = "";
/* 6069 */           str2 = resname.substring(resname.indexOf(str1) + str1.length()) + "";
/* 6070 */           monqueueids = str2.split("-", 2);
/*      */         } catch (Exception e) {
/* 6072 */           monqueueids = new String[] { "QUEUENAME", "LIBRARY" };
/* 6073 */           AMLog.debug("AS400 Action :  Problem while getting dataqueue name by splitting the res name for data queue : " + resname + " Resource id : " + resid + " due to error : " + e.getMessage());
/*      */         }
/* 6075 */         dqn++;
/*      */         
/* 6077 */         p.put("ID", Integer.valueOf(dqn));
/* 6078 */         p.put("QUEUERID", queuerid);
/* 6079 */         p.put("DISPLAYNAME", dispname);
/* 6080 */         p.put("QUEUENAME", monqueueids[0]);
/* 6081 */         p.put("LIBRARY", monqueueids[1]);
/* 6082 */         p.put("TYPE", rs.getString("TYPE") == null ? "-" : rs.getString("TYPE"));
/* 6083 */         p.put("IFSPATH", rs.getString("IFSPATH") == null ? "-" : rs.getString("IFSPATH"));
/* 6084 */         p.put("SEQUENCE", rs.getString("SEQUENCE") == null ? "-" : rs.getString("SEQUENCE"));
/* 6085 */         p.put("LAST_CHANGED", rs.getString("LAST_CHANGED") == null ? "-" : rs.getString("LAST_CHANGED"));
/* 6086 */         p.put("OBJECT_SIZE", rs.getString("OBJECT_SIZE") == null ? "-" : rs.getString("OBJECT_SIZE"));
/* 6087 */         p.put("MESSAGES", rs.getString("MESSAGES") == null ? "-" : rs.getString("MESSAGES"));
/* 6088 */         p.put("ENTRIES_ALLOCATED", rs.getString("ENTRIES_ALLOCATED") == null ? "-" : rs.getString("ENTRIES_ALLOCATED"));
/* 6089 */         p.put("MAX_ENTRIES", rs.getString("MAX_ENTRIES") == null ? "-" : rs.getString("MAX_ENTRIES"));
/* 6090 */         p.put("ENTRIES_ALLOCATED_UTIL", rs.getString("ENTRIES_ALLOCATED_UTIL") == null ? "-" : rs.getString("ENTRIES_ALLOCATED_UTIL"));
/* 6091 */         p.put("CURRENT_ENTRIES_PER", rs.getString("CURRENT_ENTRIES_PER") == null ? "-" : rs.getString("CURRENT_ENTRIES_PER"));
/* 6092 */         p.put("DESCRIPTION", rs.getString("DESCRIPTION") == null ? " " : rs.getString("DESCRIPTION"));
/* 6093 */         p.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME") == null ? maxcoltime : rs.getString("COLLECTIONTIME"));
/* 6094 */         dq.add(p);
/*      */       }
/* 6096 */       AMConnectionPool.closeStatement(rs);
/* 6097 */       data.put("dataQueues", dq);
/*      */       
/* 6099 */       query = "select mo.DISPLAYNAME,mo.RESOURCENAME,mo.RESOURCEID as QUEUERID,bi.* from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_AS400_MONITOREDJOBQUEUES bi on mo.RESOURCEID=bi.RESOURCEID and bi.COLLECTIONTIME =" + maxcoltime + " where pcm.PARENTID=" + resid + " and mo.TYPE='AS400_JOBQ'";
/* 6100 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6101 */       int jqn = 0;
/* 6102 */       while (rs.next()) {
/* 6103 */         Map p = new HashMap();
/* 6104 */         String queuerid = "";String resname = "";String dispname = "";
/* 6105 */         resname = rs.getString("RESOURCENAME");
/* 6106 */         dispname = rs.getString("DISPLAYNAME");
/* 6107 */         queuerid = rs.getString("QUEUERID");
/* 6108 */         buffdata.add(queuerid);
/* 6109 */         String[] monqueueids = { "", "" };
/*      */         try {
/* 6111 */           String str1 = ":JOBQ-";String str2 = "";
/* 6112 */           str2 = resname.substring(resname.indexOf(str1) + str1.length()) + "";
/* 6113 */           monqueueids = str2.split("-", 2);
/*      */         } catch (Exception e) {
/* 6115 */           monqueueids = new String[] { "QUEUENAME", "LIBRARY" };
/* 6116 */           AMLog.debug("AS400 Action :  Problem while getting jobqueue name by splitting the res name for job queue : " + resname + " Resource id : " + resid + " due to error : " + e.getMessage());
/*      */         }
/* 6118 */         jqn++;
/* 6119 */         p.put("ID", Integer.valueOf(jqn));
/* 6120 */         p.put("QUEUERID", queuerid);
/* 6121 */         p.put("DISPLAYNAME", dispname);
/* 6122 */         p.put("QUEUENAME", monqueueids[0]);
/* 6123 */         p.put("LIBRARY", monqueueids[1]);
/* 6124 */         p.put("TYPE", rs.getString("TYPE") == null ? "-" : rs.getString("TYPE"));
/* 6125 */         p.put("IFSPATH", rs.getString("IFSPATH") == null ? "-" : rs.getString("IFSPATH"));
/* 6126 */         p.put("SEQUENCE_NUMBER", rs.getString("SEQUENCE_NUMBER") == null ? "-" : rs.getString("SEQUENCE_NUMBER"));
/* 6127 */         p.put("SUBSYSTEM", rs.getString("SUBSYSTEM") == null ? "-" : rs.getString("SUBSYSTEM"));
/* 6128 */         p.put("SUBSYSTEM_LIBRARY", rs.getString("SUBSYSTEM_LIBRARY") == null ? "-" : rs.getString("SUBSYSTEM_LIBRARY"));
/* 6129 */         p.put("STATUS", rs.getString("STATUS") == null ? "-" : rs.getString("STATUS"));
/* 6130 */         p.put("OBJECT_SIZE", rs.getString("OBJECT_SIZE") == null ? "-" : rs.getString("OBJECT_SIZE"));
/* 6131 */         p.put("DESCRIPTION", rs.getString("DESCRIPTION") == null ? " " : rs.getString("DESCRIPTION"));
/* 6132 */         p.put("JOBS", rs.getString("JOBS") == null ? "-" : rs.getString("JOBS"));
/* 6133 */         p.put("CURRENT_ACTIVE", rs.getString("CURRENT_ACTIVE") == null ? "-" : rs.getString("CURRENT_ACTIVE"));
/* 6134 */         p.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME") == null ? maxcoltime : rs.getString("COLLECTIONTIME"));
/* 6135 */         jq.add(p);
/*      */       }
/* 6137 */       AMConnectionPool.closeStatement(rs);
/* 6138 */       data.put("jobQueues", jq);
/*      */       
/* 6140 */       query = "select mo.DISPLAYNAME,mo.RESOURCENAME,mo.RESOURCEID as QUEUERID,bi.* from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_AS400_MONITOREDOUTQUEUES bi on mo.RESOURCEID=bi.RESOURCEID and bi.COLLECTIONTIME =" + maxcoltime + " where pcm.PARENTID=" + resid + " and mo.TYPE='AS400_OUTQ'";
/* 6141 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 6142 */       int oqn = 0;
/* 6143 */       String queuerid; while (rs.next()) {
/* 6144 */         Map p = new HashMap();
/* 6145 */         queuerid = "";String resname = "";String dispname = "";
/* 6146 */         resname = rs.getString("RESOURCENAME");
/* 6147 */         dispname = rs.getString("DISPLAYNAME");
/* 6148 */         queuerid = rs.getString("QUEUERID");
/* 6149 */         buffdata.add(queuerid);
/* 6150 */         String[] monqueueids = { "", "" };
/*      */         try {
/* 6152 */           String str1 = ":OUTQ-";String str2 = "";
/* 6153 */           str2 = resname.substring(resname.indexOf(str1) + str1.length()) + "";
/* 6154 */           monqueueids = str2.split("-", 2);
/*      */         } catch (Exception e) {
/* 6156 */           monqueueids = new String[] { "QUEUENAME", "LIBRARY" };
/* 6157 */           AMLog.debug("AS400 Action :  Problem while getting outqueue name by splitting the res name for out queue : " + resname + " Resource id : " + resid + " due to error : " + e.getMessage());
/*      */         }
/* 6159 */         oqn++;
/* 6160 */         p.put("ID", Integer.valueOf(oqn));
/* 6161 */         p.put("QUEUERID", queuerid);
/* 6162 */         p.put("DISPLAYNAME", dispname);
/* 6163 */         p.put("QUEUENAME", monqueueids[0]);
/* 6164 */         p.put("LIBRARY", monqueueids[1]);
/* 6165 */         p.put("TYPE", rs.getString("TYPE") == null ? "-" : rs.getString("TYPE"));
/* 6166 */         p.put("IFSPATH", rs.getString("IFSPATH") == null ? "-" : rs.getString("IFSPATH"));
/* 6167 */         p.put("SEQUENCE", rs.getString("SEQUENCE") == null ? "-" : rs.getString("SEQUENCE"));
/* 6168 */         p.put("STATUS", rs.getString("STATUS") == null ? "-" : rs.getString("STATUS"));
/* 6169 */         p.put("OBJECT_SIZE", rs.getString("OBJECT_SIZE") == null ? "-" : rs.getString("OBJECT_SIZE"));
/* 6170 */         p.put("DESCRIPTION", rs.getString("DESCRIPTION") == null ? " " : rs.getString("DESCRIPTION"));
/* 6171 */         p.put("FILES", rs.getString("FILES") == null ? "-" : rs.getString("FILES"));
/* 6172 */         p.put("WRITER", rs.getString("WRITER") == null ? "-" : rs.getString("WRITER"));
/* 6173 */         p.put("WRITER_STATUS", rs.getString("WRITER_STATUS") == null ? "-" : rs.getString("WRITER_STATUS"));
/* 6174 */         p.put("PRINTER", rs.getString("PRINTER") == null ? "-" : rs.getString("PRINTER"));
/* 6175 */         p.put("COLLECTIONTIME", rs.getString("COLLECTIONTIME") == null ? maxcoltime : rs.getString("COLLECTIONTIME"));
/* 6176 */         oq.add(p);
/*      */       }
/* 6178 */       AMConnectionPool.closeStatement(rs);
/* 6179 */       data.put("outQueues", oq);
/*      */       
/* 6181 */       request.setAttribute("data", data);
/* 6182 */       request.setAttribute("buffdata", buffdata);
/* 6183 */       String o = request.getParameter("noredirect");
/* 6184 */       if ((o != null) && (o.equals("false"))) {
/* 6185 */         return mapping.findForward("queues");
/*      */       }
/*      */     } catch (Exception e) {
/* 6188 */       e.printStackTrace();
/*      */     } finally {
/* 6190 */       if (rs != null) {
/* 6191 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 6194 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward queueMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6199 */     List a = new ArrayList();
/* 6200 */     ResultSet rs = null;
/* 6201 */     String query = null;
/* 6202 */     AS400 as400 = null;
/*      */     try {
/* 6204 */       response.setContentType("text/html; charset=UTF-8");
/* 6205 */       String resid = request.getParameter("resourceid");
/* 6206 */       String fromAS400 = request.getParameter("fromAS400");
/* 6207 */       String queueName = "*ALL";String libName = "*ALL";String queueType = "*ALL";
/* 6208 */       queueName = request.getParameter("queueName");
/* 6209 */       libName = request.getParameter("libName");
/* 6210 */       queueType = request.getParameter("queueType");
/* 6211 */       List<String> insertQueues = new ArrayList();
/* 6212 */       if ((fromAS400 == null) || (!fromAS400.equals("true"))) {
/* 6213 */         AMLog.debug("AS400 Action: Add Queues to monitor from db " + queueName + "/" + libName + "/" + queueType);
/* 6214 */         String queryCond = "";
/* 6215 */         if ((queueName != null) && (!"*ALL".equals(queueName))) {
/* 6216 */           queryCond = " and q.QUEUE like '%" + queueName.toUpperCase() + "%'";
/*      */         }
/* 6218 */         if ((libName != null) && (!"*ALL".equals(libName))) {
/* 6219 */           queryCond = queryCond + " and q.LIBRARY like '%" + libName.toUpperCase() + "%'";
/*      */         }
/* 6221 */         if ((queueType != null) && (!"*ALL".equals(queueType))) {
/* 6222 */           queryCond = queryCond + " and q.TYPE like '%" + queueType.replace("*", "") + "%'";
/*      */         }
/* 6224 */         query = "SELECT q.*,ac.COLLECTIONTIME as coltime FROM AM_AS400_QUEUES q,AM_AS400_CONFIGURATION ac where q.RESOURCEID=ac.RESOURCEID and q.RESOURCEID  = " + resid + queryCond;
/* 6225 */         rs = AMConnectionPool.executeQueryStmt(DBQueryUtil.getTopNValues(query, 100));
/* 6226 */         int qn = 0;
/* 6227 */         while (rs.next()) {
/* 6228 */           Map p = new HashMap();
/* 6229 */           qn++;
/* 6230 */           p.put("ID", Integer.valueOf(qn));
/* 6231 */           p.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 6232 */           p.put("QUEUENAME", rs.getString("QUEUE"));
/* 6233 */           p.put("LIBNAME", rs.getString("LIBRARY"));
/* 6234 */           p.put("TYPE", rs.getString("TYPE"));
/* 6235 */           p.put("IFSPATH", rs.getString("IFSPATH"));
/* 6236 */           p.put("COLLECTIONTIME", rs.getString("coltime"));
/* 6237 */           a.add(p);
/*      */         }
/* 6239 */         AMConnectionPool.closeStatement(rs);
/*      */       } else {
/* 6241 */         AMLog.debug("AS400 Action: Add Queues to monitor from AS400 " + queueName + "/" + libName + "/" + queueType);
/* 6242 */         String monname = "";String dispname = "";String username = "";String pass = "";String coltime = "0";
/* 6243 */         query = "select mo.RESOURCENAME,mo.DISPLAYNAME,hd.USERNAME," + DBQueryUtil.decode("hd.PASSWORD") + " as pass,ac.COLLECTIONTIME from AM_AS400_CONFIGURATION ac,AM_ManagedObject mo,HostDetails hd where mo.RESOURCENAME=hd.RESOURCENAME and mo.RESOURCEID=ac.RESOURCEID and mo.RESOURCEID = " + resid;
/* 6244 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 6245 */         if (rs.next()) {
/* 6246 */           monname = rs.getString("RESOURCENAME");
/* 6247 */           dispname = rs.getString("DISPLAYNAME");
/* 6248 */           username = rs.getString("USERNAME");
/* 6249 */           pass = rs.getString("pass");
/* 6250 */           coltime = rs.getString("COLLECTIONTIME");
/* 6251 */           request.setAttribute("dispname", dispname);
/*      */         }
/* 6253 */         AMConnectionPool.closeStatement(rs);
/*      */         try {
/* 6255 */           as400 = new AS400(monname, username, pass);
/* 6256 */           as400.setGuiAvailable(false);
/* 6257 */           SocketProperties socket = new SocketProperties();
/* 6258 */           socket.setSoTimeout(600000);
/* 6259 */           as400.setSocketProperties(socket);
/* 6260 */           List<Map<String, String>> as400Objects = AS400Util.listAS400Objects(as400, libName.toUpperCase(), queueName.toUpperCase(), queueType);
/* 6261 */           for (int i = 0; i < as400Objects.size(); i++) {
/* 6262 */             Map p = new HashMap();
/* 6263 */             String objname = "";String lib = "";String type = "";String ifspath = "";
/* 6264 */             objname = (String)((Map)as400Objects.get(i)).get("OBJECTNAME");
/* 6265 */             lib = (String)((Map)as400Objects.get(i)).get("LIBRARY");
/* 6266 */             type = (String)((Map)as400Objects.get(i)).get("TYPE");
/* 6267 */             ifspath = (String)((Map)as400Objects.get(i)).get("IFSPATH");
/* 6268 */             p.put("ID", Integer.valueOf(i));
/* 6269 */             p.put("RESOURCEID", resid);
/* 6270 */             p.put("QUEUENAME", objname);
/* 6271 */             p.put("LIBNAME", lib);
/* 6272 */             p.put("TYPE", type);
/* 6273 */             p.put("IFSPATH", ifspath);
/* 6274 */             p.put("COLLECTIONTIME", coltime);
/* 6275 */             a.add(p);
/* 6276 */             query = "insert into AM_AS400_QUEUES values(" + resid + ",'" + objname + "','" + lib + "','" + type + "','" + ifspath + "'," + coltime + ")";
/* 6277 */             insertQueues.add(query);
/*      */           }
/* 6279 */           if (!insertQueues.isEmpty()) {
/* 6280 */             AMBatchStmtExecutor.executeBatch(insertQueues);
/* 6281 */             AMLog.debug("AS400 Action: Inserting details of available queues for AS400 server : " + monname + " resourceid " + resid);
/*      */           }
/*      */         } catch (Exception e) {
/* 6284 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 6287 */       request.setAttribute("queues", a);
/*      */     } catch (Exception e) {
/* 6289 */       e.printStackTrace();
/*      */     } finally {
/* 6291 */       if (rs != null) {
/* 6292 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 6295 */     return new ActionForward("/jsp/as400/queueMonitor.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward addQueue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 6301 */     HashMap<String, Map> data = new HashMap();
/*      */     
/* 6303 */     String hostname = "";
/* 6304 */     String resid = "";
/* 6305 */     JSONArray queueids = new JSONArray();
/* 6306 */     Pattern quotePattern = Pattern.compile("&quot;");
/*      */     try {
/* 6308 */       response.setContentType("text/html; charset=UTF-8");
/* 6309 */       resid = request.getParameter("resourceid");
/* 6310 */       String jsonlist = request.getParameter("queues");
/* 6311 */       jsonlist = jsonlist.trim();
/* 6312 */       Matcher matcher = quotePattern.matcher(jsonlist);
/* 6313 */       String sublist = matcher.replaceAll("\"");
/*      */       try {
/* 6315 */         AMLog.debug("AS400 Action: Monitored Queues to add " + sublist);
/* 6316 */         JSONArray jqueuelist = new JSONArray(sublist);
/* 6317 */         int substoadd = jqueuelist.length();int count = 0;
/*      */         
/* 6319 */         for (int i = 0; i < substoadd; i++) {
/* 6320 */           JSONObject queueObj = jqueuelist.getJSONObject(count);
/* 6321 */           HashMap<String, String> p = new HashMap();
/* 6322 */           p.put("ID", queueObj.getString("ID"));
/* 6323 */           p.put("RESOURCEID", resid);
/* 6324 */           p.put("NAME", queueObj.getString("QUEUENAME"));
/* 6325 */           p.put("LIBRARY", queueObj.getString("LIBNAME"));
/* 6326 */           p.put("TYPE", queueObj.getString("TYPE"));
/* 6327 */           p.put("COLLECTIONTIME", queueObj.getString("COLLECTIONTIME"));
/* 6328 */           data.put(queueObj.getString("ID"), p);
/* 6329 */           count++;
/*      */         }
/*      */       } catch (Exception e) {
/* 6332 */         e.printStackTrace();
/* 6333 */         AMLog.debug("AS400 Action: Error while adding queues to Monitored queues" + e.getMessage());
/*      */       }
/* 6335 */       hostname = Constants.getResName(resid);
/*      */     } catch (Exception e) {
/* 6337 */       e.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/* 6341 */       Collection col = data.values();
/* 6342 */       Iterator iter = col.iterator();
/* 6343 */       while (iter.hasNext())
/*      */       {
/* 6345 */         HashMap map = (HashMap)iter.next();
/* 6346 */         Integer resrcid = Integer.valueOf(Integer.parseInt(map.get("RESOURCEID").toString()));
/* 6347 */         String lib_name = (String)map.get("LIBRARY");
/* 6348 */         String name = (String)map.get("NAME");
/* 6349 */         String type = (String)map.get("TYPE");
/* 6350 */         String disp_name = "";String queue_name = "";
/*      */         
/* 6352 */         disp_name = type + "-" + name + "-" + lib_name;
/* 6353 */         queue_name = hostname + ":" + type + "-" + name + "-" + lib_name;
/*      */         
/* 6355 */         int dtaqueuerid = DataCollectionDBUtil.CheckandinsertAMMO(queue_name, "AS400_" + type, disp_name, resrcid.intValue());
/* 6356 */         queueids.put(map.get("ID"));
/*      */       }
/*      */     } catch (Exception e) {
/* 6359 */       e.printStackTrace();
/*      */     }
/* 6361 */     AMLog.debug("AS400 Action: " + queueids + " Queues(s) Added");
/* 6362 */     PrintWriter pw = response.getWriter();
/* 6363 */     pw.print(queueids);
/* 6364 */     pw.flush();
/*      */     
/* 6366 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward queueActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 6371 */     String resid = null;
/*      */     try {
/* 6373 */       response.setContentType("text/html; charset=UTF-8");
/* 6374 */       resid = request.getParameter("resourceid");
/* 6375 */       String queuerid = request.getParameter("rowids");
/* 6376 */       String fn = request.getParameter("fn");
/* 6377 */       String isSpecificMonitor = request.getParameter("specificmonitor");
/* 6378 */       if ((fn.equals("removequeue")) && (isSpecificMonitor.equals("true")))
/*      */       {
/*      */         try {
/* 6381 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 6382 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MONITOREDDATAQUEUES where RESOURCEID in (" + queuerid + ")");
/* 6383 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MONITOREDJOBQUEUES where RESOURCEID in (" + queuerid + ")");
/* 6384 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MONITOREDOUTQUEUES where RESOURCEID in (" + queuerid + ")");
/* 6385 */           AMConnectionPool.executeUpdateStmt("delete from AM_AS400_MinMaxAvgData where RESID in (" + queuerid + ")");
/* 6386 */           String[] childids = queuerid.split(",");
/* 6387 */           DataCollectionControllerUtil.deleteChild(childids);
/* 6388 */           AMLog.debug("AS400 Action: Successfully removed queues " + queuerid + " from monitoring for resid: " + resid);
/*      */         } catch (Exception e) {
/* 6390 */           AMLog.debug("AS400 Action: Problem while removing queues from monitoring due to error: " + e.getMessage() + " for resid " + resid);
/* 6391 */           e.printStackTrace();
/*      */         }
/*      */         
/* 6394 */         return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&datatype=12", true);
/*      */       }
/*      */     } catch (Exception e) {
/* 6397 */       e.printStackTrace();
/* 6398 */       AMLog.debug("AS400 Action: Problem while performing queue action due to error: " + e.getMessage() + " for resid" + resid);
/*      */     }
/* 6400 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward ifsDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 6405 */       String o = request.getParameter("noredirect");
/* 6406 */       if ((o != null) && (o.equals("false"))) {
/* 6407 */         return mapping.findForward("ifs");
/*      */       }
/*      */     } catch (Exception e) {
/* 6410 */       e.printStackTrace();
/*      */     }
/* 6412 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward ifsMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 6417 */       response.setContentType("text/html; charset=UTF-8");
/* 6418 */       resid = request.getParameter("resourceid");
/*      */     } catch (Exception e) { String resid;
/* 6420 */       e.printStackTrace();
/*      */     }
/* 6422 */     return new ActionForward("/jsp/as400/ifsMonitor.jsp");
/*      */   }
/*      */   
/*      */   private String getAS400Error(String resid) {
/* 6426 */     String query = "";
/* 6427 */     Hashtable as400Response = new Hashtable();
/* 6428 */     ResultSet rs1 = null;
/* 6429 */     String validation = "";
/* 6430 */     query = "select ERROR_TYPE,ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID=" + resid;
/*      */     try {
/* 6432 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 6433 */       if (rs1.next()) {
/* 6434 */         String message = rs1.getString("ERROR_MESSAGE");
/* 6435 */         String errType = rs1.getString("ERROR_TYPE");
/* 6436 */         if (errType.equals("1")) {
/* 6437 */           as400Response.put("failed", FormatUtil.getString(message));
/* 6438 */           validation = "success";
/* 6439 */         } else if ((errType.equals("2")) && (!message.equals("am.datacollection.success")) && (!message.equals("am.datacollection.managed"))) {
/* 6440 */           as400Response.put("success", "validated");
/* 6441 */           as400Response.put("message", FormatUtil.getString(message));
/* 6442 */           validation = "success";
/*      */         }
/* 6444 */         as400Response.put("success", "validated");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 6448 */       e.printStackTrace();
/*      */     } finally {
/* 6450 */       AMConnectionPool.closeStatement(rs1);
/*      */     }
/* 6452 */     AMLog.info("AS400 Action: getAS400Error:" + as400Response);
/* 6453 */     setAs400Responsess(as400Response);
/* 6454 */     return validation;
/*      */   }
/*      */   
/*      */   private String validateAS400(String monname, String username, String pass) {
/* 6458 */     AS400 as400 = null;
/* 6459 */     String validation = "";
/* 6460 */     Hashtable as400Response = new Hashtable();
/*      */     try {
/* 6462 */       as400 = new AS400(monname, username, pass);
/* 6463 */       as400.setGuiAvailable(false);
/* 6464 */       as400.validateSignon();
/* 6465 */       as400Response.put("success", "validated");
/* 6466 */       validation = "success";
/*      */     } catch (AS400SecurityException ase) {
/* 6468 */       validation = "failed";
/* 6469 */       ase.printStackTrace();
/* 6470 */       as400Response.put("failed", ase.getMessage());
/*      */     } catch (Exception exp) {
/* 6472 */       validation = "failed";
/* 6473 */       exp.printStackTrace();
/* 6474 */       as400Response.put("failed", exp.getMessage());
/*      */     } finally {
/* 6476 */       as400.disconnectAllServices();
/*      */     }
/* 6478 */     setAs400Responsess(as400Response);
/* 6479 */     AMLog.info("AS400 Action: validateAS400 " + as400Response);
/* 6480 */     return validation;
/*      */   }
/*      */   
/*      */   public Hashtable getAs400Responsess() {
/* 6484 */     return this.as400Responses;
/*      */   }
/*      */   
/*      */   public void setAs400Responsess(Hashtable as400Responses) {
/* 6488 */     this.as400Responses = as400Responses;
/*      */   }
/*      */   
/*      */   public boolean getIsActionMade() {
/* 6492 */     return this.isAction;
/*      */   }
/*      */   
/*      */   public void setIsActionMade(boolean isAction) {
/* 6496 */     this.isAction = isAction;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\as400\struts\AS400Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */