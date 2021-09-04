/*      */ package com.adventnet.appmanager.client.sap;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.json.JSONUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.AMUrlMonitorProcess;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.CCMSQueryHandlerInf;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.SAPCCMSDataCollector;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.SAPDataCollector;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.SAPUtil;
/*      */ import com.adventnet.appmanager.server.sap.datacollection.ScheduleSAPDataCollection;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.StartUtil;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.ApplnDataCollectionAPI;
/*      */ import com.adventnet.nms.applnfw.datacollection.server.model.CollectData;
/*      */ import com.adventnet.nms.applnfw.discovery.server.ResourceTypeIfc;
/*      */ import com.adventnet.nms.applnfw.discovery.server.model.DiscoveryInfo;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.File;
/*      */ import java.io.FileReader;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SAPAction
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   77 */     response.setContentType("text/html; charset=UTF-8");
/*   78 */     String screenid = null;
/*   79 */     String resourceid = request.getParameter("resourceid");
/*      */     try
/*      */     {
/*   82 */       if (CAMDBUtil.camScreenExists(resourceid, "SAP Custom Attributes"))
/*      */       {
/*   84 */         List screens = CAMDBUtil.getScreens(Long.parseLong(resourceid));
/*   85 */         screenid = (String)((ArrayList)screens.get(0)).get(0);
/*      */       }
/*      */       else
/*      */       {
/*   89 */         screenid = CAMDBUtil.insertCAMScreenDetails("SAP Custom Attributes", "", 0, Integer.parseInt(resourceid)) + "";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*   94 */       StartUtil.printStr("SAPAction : Problem in getting screenid for SAP. Error Message : " + e.getMessage());
/*   95 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  100 */       long lastCol = System.currentTimeMillis();
/*  101 */       String errorMessage = "";
/*      */       
/*  103 */       String query = "select max(COLLECTIONTIME) as coltime from AM_ManagedObjectData where RESID=" + resourceid;
/*  104 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  107 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  108 */         if (rs.next())
/*      */         {
/*  110 */           lastCol = rs.getLong("coltime");
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  115 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  119 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  122 */       query = "select ERROR_MESSAGE from AM_ManagedObject join AM_SAP_Config on AM_ManagedObject.RESOURCEID=AM_SAP_Config.RESOURCEID left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=AM_ManagedObject.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=" + lastCol + " left outer join AM_MONITOR_ERRORS on AM_MONITOR_ERRORS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_MONITOR_ERRORS.COLLECTIONTIME=" + lastCol + " where AM_ManagedObject.RESOURCEID=" + resourceid;
/*      */       try
/*      */       {
/*  125 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  126 */         if (rs.next())
/*      */         {
/*  128 */           if (rs.getString("ERROR_MESSAGE") != null)
/*      */           {
/*  130 */             errorMessage = rs.getString("ERROR_MESSAGE");
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  136 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  140 */         closeResultSet(rs);
/*      */       }
/*      */       
/*      */ 
/*  144 */       if ((!errorMessage.equals("")) && (!errorMessage.equals("am.datacollection.success")) && (!errorMessage.equals("am.datacollection.managed")))
/*      */       {
/*  146 */         ActionErrors errors = new ActionErrors();
/*  147 */         errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString(errorMessage)));
/*  148 */         saveErrors(request, errors);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  156 */     request.setAttribute("screenid", screenid);
/*  157 */     String datatypestr = request.getParameter("datatype");
/*  158 */     int datatype = 1;
/*  159 */     if (datatypestr != null) {
/*  160 */       datatype = Integer.parseInt(datatypestr);
/*      */     }
/*  162 */     if (datatype == 1) {
/*  163 */       overviewDetails(mapping, form, request, response);
/*      */     }
/*  165 */     else if (datatype == 2) {
/*  166 */       backgroundDetails(mapping, form, request, response);
/*      */     }
/*  168 */     else if (datatype == 3) {
/*  169 */       bufferDetails(mapping, form, request, response);
/*      */     }
/*  171 */     else if (datatype == 4) {
/*  172 */       dialogDetails(mapping, form, request, response);
/*      */     }
/*  174 */     else if (datatype == 5) {
/*  175 */       enqueueDetails(mapping, form, request, response);
/*      */     }
/*  177 */     else if (datatype == 6) {
/*  178 */       osDetails(mapping, form, request, response);
/*      */     }
/*  180 */     else if (datatype == 7) {
/*  181 */       spoolDetails(mapping, form, request, response);
/*      */     }
/*  183 */     else if (datatype == 8) {
/*  184 */       alertDetails(mapping, form, request, response);
/*      */     }
/*  186 */     else if (datatype == 9) {
/*  187 */       backgroundjobDetails(mapping, form, request, response);
/*      */     }
/*      */     
/*  190 */     return mapping.findForward("details");
/*      */   }
/*      */   
/*      */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  194 */     String resid = request.getParameter("resourceid");
/*  195 */     String dispName = ((AMActionForm)form).getDisplayname();
/*  196 */     int pollInterval = ((AMActionForm)form).getPollInterval() * 60;
/*  197 */     String username = ((AMActionForm)form).getUsername();
/*  198 */     String password = ((AMActionForm)form).getPassword();
/*  199 */     String type = ((AMActionForm)form).getType();
/*  200 */     String routerString = "";
/*  201 */     Properties conProps = SAPDataCollector.getConnProps(Integer.parseInt(resid));
/*  202 */     conProps.setProperty("username", username);
/*  203 */     conProps.setProperty("password", password);
/*  204 */     if (((AMActionForm)form).getUsedRouterString()) {
/*  205 */       routerString = ((AMActionForm)form).getRouterString();
/*  206 */       conProps.setProperty("routerString", routerString);
/*      */     }
/*  208 */     CCMSQueryHandlerInf handler = null;
/*  209 */     ActionMessages messages = new ActionMessages();
/*      */     try {
/*  211 */       handler = SAPUtil.getCCMSQueryHandler(conProps);
/*  212 */       handler.connect();
/*  213 */       handler.close();
/*  214 */       String query = "update AM_SAP_Config set USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + ",POLLINTERVAL='" + pollInterval / 60 + "',ROUTERSTRING='" + routerString + "' where resourceid=" + resid;
/*  215 */       AMConnectionPool.executeUpdateStmt(query);
/*      */     } catch (Exception e) {
/*  217 */       int error = handler.getError();
/*      */       
/*  219 */       if (error == 2) {
/*  220 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.disc.error3")));
/*  221 */         saveMessages(request, messages);
/*      */       }
/*  223 */       else if (error == 1) {
/*  224 */         messages.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.disc.error2")));
/*  225 */         saveMessages(request, messages);
/*      */       }
/*  227 */       return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid);
/*      */     }
/*      */     
/*  230 */     String query = "update AM_ManagedObject set displayname='" + dispName + "' where resourceid=" + resid;
/*  231 */     AMConnectionPool.executeUpdateStmt(query);
/*  232 */     EnterpriseUtil.addUpdateQueryToFile(query);
/*      */     
/*  234 */     if ((type != null) && (type.equals("SAP-CCMS")))
/*      */     {
/*  236 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.message", FormatUtil.getString("am.webclient.DataCollec.ErrMsg")));
/*  237 */       saveMessages(request, messages);
/*      */     }
/*      */     else
/*      */     {
/*  241 */       ApplnDataCollectionAPI api = (ApplnDataCollectionAPI)NmsUtil.getAPI("ApplnDataCollectionAPI");
/*  242 */       query = "select RESOURCENAME from AM_ManagedObject where resourceid=" + resid;
/*  243 */       ResultSet rs = null;
/*      */       try {
/*  245 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  246 */         if (rs.next()) {
/*      */           try {
/*  248 */             String resourcename = rs.getString("RESOURCENAME");
/*  249 */             query = "update CollectData set POLLINTERVAL=" + pollInterval + " where RESOURCENAME='" + resourcename + "'";
/*  250 */             AMConnectionPool.executeUpdateStmt(query);
/*  251 */             CollectData col1 = api.getCollectData(resourcename, "SAP");
/*  252 */             AMDataCollectionHandler.scheduleDataCollection(col1, true);
/*      */           } catch (Exception e) {
/*  254 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  258 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  261 */         closeResultSet(rs);
/*      */       }
/*      */     }
/*      */     
/*  265 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid);
/*      */   }
/*      */   
/*      */   public ActionForward overviewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  272 */       response.setContentType("text/html; charset=UTF-8");
/*  273 */       StartUtil.printStr("Inside overviewDetails");
/*  274 */       String resid = request.getParameter("resourceid");
/*      */       
/*  276 */       checkOSColState(resid, request);
/*      */       
/*  278 */       ArrayList resIDs = new ArrayList();
/*  279 */       resIDs.add(resid);
/*      */       
/*  281 */       String query = "select displayname from AM_ManagedObject where resourceid=" + resid;
/*  282 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  285 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  286 */         if (rs.next()) {
/*  287 */           String dispname = rs.getString("displayname");
/*  288 */           ((AMActionForm)form).setDisplayname(dispname);
/*  289 */           request.setAttribute("displayname", dispname);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  294 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  298 */         closeResultSet(rs);
/*      */       }
/*  300 */       query = "select pollinterval from CollectData cd,AM_ManagedObject mo where mo.resourceid=" + resid + " and mo.resourcename=cd.resourcename";
/*  301 */       int pollint = 0;
/*      */       try
/*      */       {
/*  304 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  305 */         if (rs.next()) {
/*  306 */           pollint = rs.getInt("pollinterval");
/*  307 */           int poll1 = pollint / 60;
/*  308 */           ((AMActionForm)form).setPollInterval(poll1);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  313 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  317 */         closeResultSet(rs);
/*      */       }
/*  319 */       String lastpoll = "-";
/*  320 */       String nextpoll = "-";
/*  321 */       query = "select max(collectiontime) as coltime from AM_ManagedObjectData where resid=" + resid;
/*      */       try
/*      */       {
/*  324 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  325 */         if (rs.next()) {
/*  326 */           long l = rs.getLong("coltime");
/*  327 */           long np = l + pollint * 1000;
/*  328 */           lastpoll = FormatUtil.formatDT(String.valueOf(l));
/*  329 */           nextpoll = FormatUtil.formatDT(String.valueOf(np));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  334 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  338 */         closeResultSet(rs);
/*      */       }
/*  340 */       request.setAttribute("LASTDC", lastpoll);
/*  341 */       request.setAttribute("NEXTDC", nextpoll);
/*      */       
/*  343 */       query = "select username,routerstring from AM_SAP_Config where resourceid=" + resid;
/*      */       try
/*      */       {
/*  346 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  347 */         while (rs.next()) {
/*  348 */           ((AMActionForm)form).setUsername(rs.getString("username"));
/*  349 */           if ((rs.getString("ROUTERSTRING") != null) && (!" ".equals(rs.getString("ROUTERSTRING"))) && (!rs.getString("ROUTERSTRING").isEmpty())) {
/*  350 */             ((AMActionForm)form).setUsedRouterString(true);
/*  351 */             ((AMActionForm)form).setRouterString(rs.getString("ROUTERSTRING"));
/*      */           } else {
/*  353 */             ((AMActionForm)form).setUsedRouterString(false);
/*  354 */             ((AMActionForm)form).setRouterString(rs.getString("ROUTERSTRING"));
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  360 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  364 */         closeResultSet(rs);
/*      */       }
/*  366 */       String poll = (String)request.getAttribute("reloadperiod");
/*  367 */       HashMap map = null;
/*  368 */       if (poll == null)
/*  369 */         poll = "300";
/*  370 */       map = ClientDBUtil.getSystemHealthPollInfoForService(resid, Long.parseLong(poll));
/*      */       
/*  372 */       if (map != null) {
/*  373 */         request.setAttribute("systeminfo", map);
/*      */       }
/*      */       
/*  376 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  378 */       query = "select * from AM_SAP_OSInfo where resourceid=" + resid + " order by collectiontime desc";
/*      */       try
/*      */       {
/*  381 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  382 */         if (rs.next()) {
/*  383 */           request.setAttribute("CPU", rs.getString("CPUUTILIZATION"));
/*  384 */           request.setAttribute("Memory", rs.getString("ESACT"));
/*  385 */           request.setAttribute("Disk", rs.getString("DISKUTILIZATION"));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  390 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  394 */         closeResultSet(rs);
/*      */       }
/*  396 */       query = "select bi.*,mo.DISPLAYNAME from AM_SAP_BufferInfo bi,AM_ManagedObject mo, AM_PARENTCHILDMAPPER pcm where pcm.parentid=" + resid + " and pcm.childid=mo.resourceid and mo.resourcename='Program' and mo.resourceid=bi.resourceid and mo.type='SAPBuffer' order by collectiontime";
/*      */       try
/*      */       {
/*  399 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  400 */         if (rs.next()) {
/*  401 */           request.setAttribute("ProgramResourceID", rs.getString("RESOURCEID"));
/*  402 */           request.setAttribute("ProgramDisplayName", rs.getString("DISPLAYNAME"));
/*  403 */           request.setAttribute("HitRatio", rs.getString("HITRATIO"));
/*  404 */           request.setAttribute("SpaceUsed", rs.getString("SPACEUSED"));
/*  405 */           request.setAttribute("DirectoryUsed", rs.getString("DIRECTORYUSED"));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  410 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  414 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  417 */       query = "select sutilization from AM_SAP_SpoolInfo where resourceid=" + resid + " order by collectiontime desc";
/*      */       try
/*      */       {
/*  420 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  421 */         if (rs.next()) {
/*  422 */           request.setAttribute("spoolutilization", rs.getString("sutilization"));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  427 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  431 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  434 */       query = "select butilization from AM_SAP_BackgroundInfo where resourceid=" + resid + " order by collectiontime desc";
/*      */       try
/*      */       {
/*  437 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  438 */         if (rs.next()) {
/*  439 */           request.setAttribute("backgroundutilization", rs.getString("butilization"));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  444 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  448 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  451 */       query = "select childid from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where pcm.parentid=" + resid + " and pcm.childid=mo.resourceid and mo.type='SAPBuffer'";
/*      */       try
/*      */       {
/*  454 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  455 */         while (rs.next()) {
/*  456 */           resIDs.add(rs.getString("childid"));
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  461 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  465 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  468 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  470 */       String o = request.getParameter("noredirect");
/*  471 */       if ((o != null) && (o.equals("false"))) {
/*  472 */         return mapping.findForward("overview");
/*      */       }
/*      */     } catch (Exception e) {
/*  475 */       e.printStackTrace();
/*      */     }
/*  477 */     return null;
/*      */   }
/*      */   
/*      */   private void checkOSColState(String resid, HttpServletRequest request)
/*      */   {
/*  482 */     String query = "select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resid;
/*  483 */     ResultSet rs = null;
/*      */     try {
/*  485 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  486 */       if (rs.next()) {
/*  487 */         String msg = rs.getString("ERRORMSG");
/*  488 */         if (msg.equals("am.webclient.sap.oscolerror.txt")) {
/*  489 */           ActionMessages messages = new ActionMessages();
/*  490 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.oscolerror.txt")));
/*  491 */           saveMessages(request, messages);
/*  492 */           request.setAttribute("OSCOLState", "false");
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  498 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  501 */       closeResultSet(rs);
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward backgroundDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  509 */       response.setContentType("text/html; charset=UTF-8");
/*  510 */       String resid = request.getParameter("resourceid");
/*  511 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  512 */       if (resIDs == null) {
/*  513 */         resIDs = new ArrayList();
/*  514 */         resIDs.add(resid);
/*      */       }
/*  516 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  518 */       Properties bgp = new Properties();
/*  519 */       String query = "select * from AM_SAP_BackgroundInfo where resourceid=" + resid + " order by collectiontime desc";
/*  520 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  523 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  524 */         if (rs.next()) {
/*  525 */           bgp.setProperty("SYSTEMWIDEQUEUELENGTH", rs.getString("SYSTEMWIDEQUEUELENGTH"));
/*  526 */           bgp.setProperty("SYSTEMWIDEFREEBPWP", rs.getString("SYSTEMWIDEFREEBPWP"));
/*  527 */           bgp.setProperty("BUTILIZATION", rs.getString("BUTILIZATION"));
/*  528 */           bgp.setProperty("NUMBEROFWPBTC", rs.getString("NUMBEROFWPBTC"));
/*  529 */           bgp.setProperty("ERRORSINWPBTC", rs.getString("ERRORSINWPBTC"));
/*  530 */           bgp.setProperty("ERRORFREQINWPBTC", rs.getString("ERRORFREQINWPBTC"));
/*  531 */           bgp.setProperty("ENDEDWPBTC", rs.getString("ENDEDWPBTC"));
/*  532 */           bgp.setProperty("SERVERSPECIFICQUEUELENGTH", rs.getString("SERVERSPECIFICQUEUELENGTH"));
/*      */           
/*  534 */           request.setAttribute("bgInfo", bgp);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  539 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  543 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  546 */       String o = request.getParameter("noredirect");
/*  547 */       if ((o != null) && (o.equals("false"))) {
/*  548 */         return mapping.findForward("background");
/*      */       }
/*      */     } catch (Exception e) {
/*  551 */       e.printStackTrace();
/*      */     }
/*  553 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward bufferDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  560 */       response.setContentType("text/html; charset=UTF-8");
/*  561 */       String resid = request.getParameter("resourceid");
/*  562 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  563 */       if (resIDs == null) {
/*  564 */         resIDs = new ArrayList();
/*  565 */         resIDs.add(resid);
/*      */       }
/*      */       
/*  568 */       String query = "select max(collectiontime) as coltime from AM_ManagedObjectData where resid=" + resid;
/*  569 */       ResultSet rs1 = null;
/*      */       try
/*      */       {
/*  572 */         rs1 = AMConnectionPool.executeQueryStmt(query);
/*  573 */         if (rs1.next()) {
/*  574 */           query = "select mo.DISPLAYNAME,bi.* from AM_SAP_BufferInfo bi,AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where mo.resourceid=bi.resourceid and bi.resourceid=pcm.childid and pcm.parentid=" + resid + " and collectiontime=" + rs1.getString("coltime");
/*  575 */           ResultSet rs = null;
/*  576 */           ArrayList buffdata = new ArrayList();
/*      */           try
/*      */           {
/*  579 */             rs = AMConnectionPool.executeQueryStmt(query);
/*  580 */             while (rs.next()) {
/*  581 */               Properties p = new Properties();
/*  582 */               p.setProperty("BUFFERID", rs.getString("RESOURCEID"));
/*  583 */               p.setProperty("BUFFERNAME", rs.getString("DISPLAYNAME"));
/*  584 */               p.setProperty("DIRECTORYUSED", rs.getString("DIRECTORYUSED"));
/*  585 */               p.setProperty("SPACEUSED", rs.getString("SPACEUSED"));
/*  586 */               p.setProperty("HITRATIO", rs.getString("HITRATIO"));
/*  587 */               p.setProperty("SWAP", rs.getString("SWAP"));
/*      */               
/*  589 */               buffdata.add(p);
/*      */               
/*  591 */               resIDs.add(rs.getString("RESOURCEID"));
/*      */             }
/*      */           }
/*      */           catch (Exception exp)
/*      */           {
/*  596 */             exp.printStackTrace();
/*      */           }
/*      */           finally {}
/*      */           
/*      */ 
/*      */ 
/*  602 */           request.setAttribute("buffInfo", buffdata);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  607 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  611 */         closeResultSet(rs1);
/*      */       }
/*      */       
/*  614 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  616 */       String o = request.getParameter("noredirect");
/*  617 */       if ((o != null) && (o.equals("false"))) {
/*  618 */         return mapping.findForward("buffer");
/*      */       }
/*      */     } catch (Exception e) {
/*  621 */       e.printStackTrace();
/*      */     }
/*      */     
/*  624 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward dialogDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  631 */       response.setContentType("text/html; charset=UTF-8");
/*  632 */       String resid = request.getParameter("resourceid");
/*  633 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  634 */       if (resIDs == null) {
/*  635 */         resIDs = new ArrayList();
/*  636 */         resIDs.add(resid);
/*      */       }
/*  638 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  640 */       String query = "select * from AM_SAP_DialogInfo where resourceid=" + resid + " order by collectiontime desc";
/*  641 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  644 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  645 */         if (rs.next()) {
/*  646 */           Properties dp = new Properties();
/*  647 */           dp.setProperty("FRONTENDRESPONSETIME", rs.getString("FRONTENDRESPONSETIME"));
/*  648 */           dp.setProperty("RESPONSETIME", rs.getString("RESPONSETIME"));
/*  649 */           dp.setProperty("QUEUETIME", rs.getString("QUEUETIME"));
/*  650 */           dp.setProperty("LOADPLUSGENTIME", rs.getString("LOADPLUSGENTIME"));
/*  651 */           dp.setProperty("DBREQUESTTIME", rs.getString("DBREQUESTTIME"));
/*  652 */           dp.setProperty("NETWORKTIME", rs.getString("NETWORKTIME"));
/*  653 */           dp.setProperty("USERSLOGGEDIN", rs.getString("USERSLOGGEDIN"));
/*      */           
/*  655 */           request.setAttribute("dialogInfo", dp);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  660 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  664 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  667 */       String o = request.getParameter("noredirect");
/*  668 */       if ((o != null) && (o.equals("false"))) {
/*  669 */         return mapping.findForward("dialog");
/*      */       }
/*      */     } catch (Exception e) {
/*  672 */       e.printStackTrace();
/*      */     }
/*      */     
/*  675 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward enqueueDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  682 */       response.setContentType("text/html; charset=UTF-8");
/*  683 */       String resid = request.getParameter("resourceid");
/*  684 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  685 */       if (resIDs == null) {
/*  686 */         resIDs = new ArrayList();
/*  687 */         resIDs.add(resid);
/*      */       }
/*  689 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  691 */       String query = "select * from AM_SAP_EnqueueInfo where resourceid=" + resid + " order by collectiontime desc";
/*  692 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  695 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  696 */         if (rs.next()) {
/*  697 */           Properties enqp = new Properties();
/*  698 */           enqp.setProperty("ENQUEUEREQUESTS", rs.getString("ENQUEUEREQUESTS"));
/*  699 */           enqp.setProperty("ENQUEUEREQUESTREJECTS", rs.getString("ENQUEUEREQUESTREJECTS"));
/*  700 */           enqp.setProperty("ENQUEUEREQUESTSERRORS", rs.getString("ENQUEUEREQUESTSERRORS"));
/*  701 */           enqp.setProperty("DEQUEUEREQUESTS", rs.getString("DEQUEUEREQUESTS"));
/*  702 */           enqp.setProperty("DEQUEUEREQUESTSERRORS", rs.getString("DEQUEUEREQUESTSERRORS"));
/*  703 */           enqp.setProperty("DEQUEUEALLREQUESTS", rs.getString("DEQUEUEALLREQUESTS"));
/*  704 */           enqp.setProperty("CLEANUPREQUESTS", rs.getString("CLEANUPREQUESTS"));
/*  705 */           enqp.setProperty("BACKUPREQUESTS", rs.getString("BACKUPREQUESTS"));
/*  706 */           enqp.setProperty("REPORTINGREQUESTS", rs.getString("REPORTINGREQUESTS"));
/*  707 */           enqp.setProperty("OWNERNAMES", rs.getString("OWNERNAMES"));
/*  708 */           enqp.setProperty("GRANULEARGUMENTS", rs.getString("GRANULEARGUMENTS"));
/*  709 */           enqp.setProperty("GRANULEENTRIES", rs.getString("GRANULEENTRIES"));
/*  710 */           enqp.setProperty("UPDATEQUEUE", rs.getString("UPDATEQUEUE"));
/*  711 */           enqp.setProperty("RECENTLOCKTIME", rs.getString("RECENTLOCKTIME"));
/*  712 */           enqp.setProperty("RECENTLOCKWAITTIME", rs.getString("RECENTLOCKWAITTIME"));
/*  713 */           enqp.setProperty("RECENTSERVERTIME", rs.getString("RECENTSERVERTIME"));
/*  714 */           enqp.setProperty("ENQUEUEFREQ", rs.getString("ENQUEUEFREQ"));
/*      */           
/*  716 */           request.setAttribute("enqInfo", enqp);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  721 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  725 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  728 */       String o = request.getParameter("noredirect");
/*  729 */       if ((o != null) && (o.equals("false"))) {
/*  730 */         return mapping.findForward("enqueue");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*  735 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward osDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  742 */       response.setContentType("text/html; charset=UTF-8");
/*  743 */       String resid = request.getParameter("resourceid");
/*      */       
/*  745 */       checkOSColState(resid, request);
/*      */       
/*  747 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  748 */       if (resIDs == null) {
/*  749 */         resIDs = new ArrayList();
/*  750 */         resIDs.add(resid);
/*      */       }
/*  752 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  754 */       String query = "select * from AM_SAP_OSInfo where resourceid=" + resid + " order by collectiontime desc";
/*  755 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  758 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  759 */         if (rs.next()) {
/*  760 */           Properties osp = new Properties();
/*  761 */           osp.setProperty("R3ROLLUSED", rs.getString("R3ROLLUSED"));
/*  762 */           osp.setProperty("ESACT", rs.getString("ESACT"));
/*  763 */           osp.setProperty("HEAPACT", rs.getString("HEAPACT"));
/*  764 */           osp.setProperty("PAGEIN", rs.getString("PAGEIN"));
/*  765 */           osp.setProperty("PAGEOUT", rs.getString("PAGEOUT"));
/*  766 */           osp.setProperty("CPU Utilization", rs.getString("CPUUTILIZATION"));
/*  767 */           osp.setProperty("Disk Utilization", rs.getString("DISKUTILIZATION"));
/*  768 */           osp.setProperty("SYSLOGFREQ", rs.getString("SYSLOGFREQ"));
/*      */           
/*  770 */           request.setAttribute("osInfo", osp);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  775 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  779 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  782 */       String o = request.getParameter("noredirect");
/*  783 */       if ((o != null) && (o.equals("false"))) {
/*  784 */         return mapping.findForward("os");
/*      */       }
/*      */     } catch (Exception e) {
/*  787 */       e.printStackTrace();
/*      */     }
/*  789 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward spoolDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  795 */       response.setContentType("text/html; charset=UTF-8");
/*  796 */       String resid = request.getParameter("resourceid");
/*  797 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  798 */       if (resIDs == null) {
/*  799 */         resIDs = new ArrayList();
/*  800 */         resIDs.add(resid);
/*      */       }
/*  802 */       request.setAttribute("resIDs", resIDs);
/*      */       
/*  804 */       String query = "select * from AM_SAP_SpoolInfo where resourceid=" + resid + " order by collectiontime desc";
/*  805 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  808 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  809 */         if (rs.next()) {
/*  810 */           Properties sp = new Properties();
/*  811 */           sp.setProperty("SUTILIZATION", rs.getString("SUTILIZATION"));
/*  812 */           sp.setProperty("NUMBEROFWPSPO", rs.getString("NUMBEROFWPSPO"));
/*  813 */           sp.setProperty("ERRORSINWPSPO", rs.getString("ERRORSINWPSPO"));
/*  814 */           sp.setProperty("ENDEDWPSPO", rs.getString("ENDEDWPSPO"));
/*  815 */           sp.setProperty("QUEUELENGTH", rs.getString("QUEUELENGTH"));
/*  816 */           sp.setProperty("SERVICEQUEUE", rs.getString("SERVICEQUEUE"));
/*  817 */           sp.setProperty("SERVICEQUEUEPRIV", rs.getString("SERVICEQUEUEPRIV"));
/*  818 */           sp.setProperty("SERVICEQUEUEPAGES", rs.getString("SERVICEQUEUEPAGES"));
/*  819 */           sp.setProperty("DEVICECACHEUSED", rs.getString("DEVICECACHEUSED"));
/*  820 */           sp.setProperty("DEVICECACHEFIXED", rs.getString("DEVICECACHEFIXED"));
/*  821 */           sp.setProperty("HOSTSPOOLLISTUSED", rs.getString("HOSTSPOOLLISTUSED"));
/*      */           
/*  823 */           request.setAttribute("spoolInfo", sp);
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  828 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  832 */         closeResultSet(rs);
/*      */       }
/*      */       
/*  835 */       String o = request.getParameter("noredirect");
/*  836 */       if ((o != null) && (o.equals("false"))) {
/*  837 */         return mapping.findForward("spool");
/*      */       }
/*      */     } catch (Exception e) {
/*  840 */       e.printStackTrace();
/*      */     }
/*      */     
/*  843 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward backgroundjobDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  856 */       response.setContentType("text/html: charset=UTF-8");
/*  857 */       String resid = request.getParameter("resourceid");
/*  858 */       ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/*  859 */       if (resIDs == null) {
/*  860 */         resIDs = new ArrayList();
/*      */       }
/*  862 */       Long coltime = null;
/*  863 */       ResultSet rs = null;
/*  864 */       SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
/*  865 */       Calendar cd = Calendar.getInstance();
/*  866 */       String query = "select max(collectiontime) as coltime from AM_ManagedObjectData where resid=" + resid;
/*      */       try {
/*  868 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  869 */         if (rs.next()) {
/*  870 */           coltime = Long.valueOf(rs.getLong("coltime"));
/*      */         }
/*      */       } catch (Exception e) {
/*  873 */         e.printStackTrace();
/*      */       } finally {
/*  875 */         closeResultSet(rs);
/*      */       }
/*  877 */       query = "select mo.RESOURCEID,mo.DISPLAYNAME,bg. CURRENTSTATUS ,bg.LASTRUNSTATUS,bg.LASTRUNDATETIME,bg.RUNTIME ,bg.DELAY,bg.AGE,bg.JOBCOUNT,bg.COLLECTIONTIME,bg.ISCCMSJOB from AM_ManagedObject mo left outer join AM_PARENTCHILDMAPPER pcm on mo.RESOURCEID=pcm.CHILDID left outer join AM_SAP_BACKGROUNDJOBINFO bg on bg.RESOURCEID=mo.RESOURCEID and bg.COLLECTIONTIME=" + coltime + " where pcm.PARENTID=" + resid + " and mo.TYPE='SAP-BGJOB'";
/*      */       try
/*      */       {
/*  880 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  881 */         ArrayList bgjobinfolist = new ArrayList();
/*  882 */         while (rs.next()) {
/*  883 */           Properties bginfo = new Properties();
/*  884 */           bginfo.setProperty("BGJOBID", rs.getString("RESOURCEID"));
/*  885 */           bginfo.setProperty("JOBNAME", rs.getString("DISPLAYNAME"));
/*  886 */           if (rs.getString("CURRENTSTATUS") == null) {
/*  887 */             bginfo.setProperty("CURRENTSTATUS", "-");
/*      */           } else {
/*  889 */             bginfo.setProperty("CURRENTSTATUS", rs.getString("CURRENTSTATUS"));
/*      */           }
/*  891 */           if (rs.getString("LASTRUNSTATUS") == null) {
/*  892 */             bginfo.setProperty("LASTRUNSTATUS", "-");
/*      */           } else {
/*  894 */             bginfo.setProperty("LASTRUNSTATUS", rs.getString("LASTRUNSTATUS"));
/*      */           }
/*  896 */           if ((rs.getString("LASTRUNDATETIME") == null) || (rs.getLong("LASTRUNDATETIME") == 0L)) {
/*  897 */             bginfo.setProperty("LASTRUNDATETIME", "-");
/*      */           } else {
/*  899 */             cd.setTimeInMillis(rs.getLong("LASTRUNDATETIME"));
/*  900 */             bginfo.setProperty("LASTRUNDATETIME", sdf.format(cd.getTime()));
/*      */           }
/*  902 */           if (rs.getString("RUNTIME") == null) {
/*  903 */             bginfo.setProperty("RUNTIME", "-");
/*      */           } else {
/*  905 */             bginfo.setProperty("RUNTIME", rs.getString("RUNTIME"));
/*      */           }
/*  907 */           if (rs.getString("DELAY") == null) {
/*  908 */             bginfo.setProperty("DELAY", "-");
/*      */           } else {
/*  910 */             bginfo.setProperty("DELAY", rs.getString("DELAY"));
/*      */           }
/*  912 */           if (rs.getString("COLLECTIONTIME") == null) {
/*  913 */             bginfo.setProperty("COLTIME", "-");
/*      */           } else {
/*  915 */             bginfo.setProperty("COLTIME", rs.getString("COLLECTIONTIME"));
/*      */           }
/*  917 */           if (rs.getString("JOBCOUNT") == null) {
/*  918 */             bginfo.setProperty("JOBCOUNT", "-");
/*      */           } else {
/*  920 */             bginfo.setProperty("JOBCOUNT", rs.getString("JOBCOUNT"));
/*      */           }
/*  922 */           if ((rs.getString("AGE") == null) || (rs.getString("AGE").equals("-1"))) {
/*  923 */             bginfo.setProperty("AGE", "-");
/*      */           } else {
/*  925 */             long age = rs.getLong("AGE") / 60000L;
/*  926 */             bginfo.setProperty("AGE", String.valueOf(age));
/*      */           }
/*  928 */           if (rs.getBoolean("ISCCMSJOB") == true) {
/*  929 */             bginfo.setProperty("ISCCMSJOB", "YES");
/*      */           } else {
/*  931 */             bginfo.setProperty("ISCCMSJOB", "NO");
/*      */           }
/*  933 */           bgjobinfolist.add(bginfo);
/*  934 */           resIDs.add(rs.getString("RESOURCEID"));
/*      */         }
/*  936 */         request.setAttribute("backgroundjobInfo", bgjobinfolist);
/*      */       } catch (Exception e) {
/*  938 */         e.printStackTrace();
/*      */       } finally {
/*  940 */         closeResultSet(rs);
/*      */       }
/*  942 */       String o = request.getParameter("noredirect");
/*  943 */       request.setAttribute("resIDs", resIDs);
/*  944 */       if ((o != null) && (o.equals("false"))) {
/*  945 */         return mapping.findForward("backgroundjob");
/*      */       }
/*      */     } catch (Exception e) {
/*  948 */       e.printStackTrace();
/*      */     }
/*  950 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward searchBackgroundJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  963 */       response.setContentType("text/html; charset=UTF-8");
/*  964 */       String jobname = request.getParameter("jobname");
/*  965 */       String username = request.getParameter("username");
/*  966 */       int resid = Integer.parseInt(request.getParameter("resourceid"));
/*  967 */       String resourcename = request.getParameter("resourcename");
/*  968 */       List<String> bgjobresult = null;
/*  969 */       Properties p = SAPDataCollector.getConnProps(resid);
/*  970 */       CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(p);
/*  971 */       bgjobresult = handler.searchBgJob(resid, jobname, username);
/*  972 */       if (!bgjobresult.isEmpty()) {
/*  973 */         request.setAttribute("backgroundjobList", bgjobresult);
/*      */       }
/*  975 */       request.setAttribute("resourceid", Integer.valueOf(resid));
/*  976 */       request.setAttribute("resourcename", resourcename);
/*      */     } catch (Exception e) {
/*  978 */       e.printStackTrace();
/*      */     }
/*  980 */     return mapping.findForward("searchbgjob");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward customSearchBgjob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  993 */     response.setContentType("text/html; charset=UTF-8");
/*  994 */     String jobname = request.getParameter("jobname");
/*  995 */     String username = request.getParameter("username");
/*  996 */     StringBuffer temp = new StringBuffer();
/*  997 */     int resid = Integer.parseInt(request.getParameter("resourceid"));
/*  998 */     PrintWriter out = response.getWriter();
/*      */     try {
/* 1000 */       Properties p = SAPDataCollector.getConnProps(resid);
/* 1001 */       CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(p);
/* 1002 */       List<String> bgjobresult = null;
/* 1003 */       bgjobresult = handler.searchBgJob(resid, jobname, username);
/* 1004 */       if ((bgjobresult != null) && (!bgjobresult.isEmpty())) {
/* 1005 */         for (int i = 0; i < bgjobresult.size(); i++) {
/* 1006 */           temp.append("<tr>");
/* 1007 */           if (i % 2 == 0) {
/* 1008 */             temp.append("<td class='yellowgrayborderbr'><input id='check").append(i + 1).append("' name='bgjobtobeadded' onclick='clickage(event)' type='checkbox' value='").append((String)bgjobresult.get(i)).append("'/></td>");
/* 1009 */             temp.append("<td class='yellowgrayborderbr'  style='padding:3px'><span class='bodytext'>").append((String)bgjobresult.get(i)).append("</span></td>");
/*      */           } else {
/* 1011 */             temp.append("<td class='whitegrayborderbr'><input id='check").append(i + 1).append("' name='bgjobtobeadded' onclick='clickage(event)' type='checkbox' value='").append((String)bgjobresult.get(i)).append("'/></td>");
/* 1012 */             temp.append("<td class='whitegrayborderbr'  style='padding:3px'><span class='bodytext'>").append((String)bgjobresult.get(i)).append("</span></td>");
/*      */           }
/* 1014 */           temp.append("</tr>");
/*      */         }
/*      */         
/* 1017 */       } else if (bgjobresult == null) {
/* 1018 */         temp.append("<tr><td class='whitegrayborderbr'  style='padding:3px' colspan='2' width='100%' align='center'><span class='bodytextbold'>").append(FormatUtil.getString("am.webclient.sap.bg.nullresult")).append("</span></td></tr>");
/* 1019 */       } else if (bgjobresult.isEmpty()) {
/* 1020 */         temp.append("<tr><td class='whitegrayborderbr'  style='padding:3px' colspan='2' width='100%' align='center'><span class='bodytextbold'>").append(FormatUtil.getString("am.webclient.sap.bg.emptyresult")).append("</span></td></tr>");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1024 */       e.printStackTrace();
/*      */     }
/* 1026 */     out.write(temp.toString());
/* 1027 */     out.flush();
/* 1028 */     out.close();
/* 1029 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward addBackgroundJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1041 */     response.setContentType("text/html; charset=UTF-8");
/* 1042 */     int resid = Integer.parseInt(request.getParameter("resourceid"));
/* 1043 */     String[] bgjobtobeadded = request.getParameterValues("bgjobtobeadded");
/* 1044 */     String queryselect = "select mo.RESOURCENAME from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where pcm.PARENTID=" + resid + " and pcm.CHILDID=mo.RESOURCEID and mo.TYPE='SAP-BGJOB'";
/* 1045 */     ResultSet rs = null;
/* 1046 */     PrintWriter out = response.getWriter();
/* 1047 */     StringBuffer temp = new StringBuffer();
/* 1048 */     List<String> bgjobinam = new ArrayList();
/* 1049 */     SAPDataCollector sapdc = new SAPDataCollector();
/*      */     try {
/* 1051 */       rs = AMConnectionPool.executeQueryStmt(queryselect);
/* 1052 */       while (rs.next()) {
/* 1053 */         bgjobinam.add(rs.getString("RESOURCENAME"));
/*      */       }
/* 1055 */       if (bgjobtobeadded.length != 0) {
/* 1056 */         for (String newbgjob : bgjobtobeadded) {
/* 1057 */           StartUtil.printStr("SAP Action : New Job added for host " + SAPDataCollector.getConnProps(resid).getProperty("host") + "=>" + newbgjob);
/* 1058 */           int bgjobid = -1;
/* 1059 */           if ((!newbgjob.equals("on")) && (!bgjobinam.contains(newbgjob))) {
/*      */             try {
/* 1061 */               bgjobid = sapdc.getNewMO(resid, newbgjob, newbgjob, "SAP-BGJOB", "SAP Backgroundjob");
/*      */             } catch (Exception e) {
/* 1063 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/* 1067 */         temp.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" border=\"0\"><tr>\t<td width=\"3%\" class=\"msg-table-width-bg\"><img src=\"../images/icon_message_success.gif\" alt=\"icon\" height=\"20\" width=\"20\"></td><td width=\"98%\" class=\"msg-table-width\">&nbsp;").append(FormatUtil.getString("am.webclient.sap.bg.addresult")).append("</td></tr></table>");
/*      */       } else {
/* 1069 */         StartUtil.printStr("SAP Datacollector:resourceid:" + resid + "Empty list for add job");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1073 */       e.printStackTrace();
/*      */     } finally {
/* 1075 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1077 */     out.write(temp.toString());
/* 1078 */     out.flush();
/* 1079 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteBackgroundJob(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1092 */       response.setContentType("text/html; charset=UTF-8");
/* 1093 */       String resid = request.getParameter("resourceid");
/* 1094 */       String[] bgjobtobedeleted = request.getParameterValues("bgjobtobedeleted");
/* 1095 */       DataCollectionControllerUtil dtcolcon = new DataCollectionControllerUtil();
/* 1096 */       ScheduleSAPDataCollection schedulesapdc = new ScheduleSAPDataCollection();
/* 1097 */       if (bgjobtobedeleted != null) {
/* 1098 */         Vector vec = new Vector();
/* 1099 */         for (int i = 0; i < bgjobtobedeleted.length; i++) {
/* 1100 */           if (!bgjobtobedeleted[i].equals("on")) {
/* 1101 */             vec.addElement(bgjobtobedeleted[i]);
/*      */           }
/*      */         }
/*      */         try {
/* 1105 */           List jobidlist = Arrays.asList(bgjobtobedeleted);
/* 1106 */           schedulesapdc.deleteTraceFiles(jobidlist);
/*      */         } catch (Exception e) {
/* 1108 */           e.printStackTrace();
/*      */         }
/* 1110 */         DataCollectionControllerUtil.deleteMO(resid, vec, true);
/* 1111 */         vec.clear();
/*      */       }
/*      */     } catch (Exception e) {
/* 1114 */       e.printStackTrace();
/*      */     }
/* 1116 */     return backgroundjobDetails(mapping, form, request, response);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward jobTraceAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1129 */     response.setContentType("text/html; charset=UTF-8");
/* 1130 */     String resId = request.getParameter("resId");
/* 1131 */     String jobname = request.getParameter("jobname");
/* 1132 */     String jobcount = request.getParameter("jobcount");
/* 1133 */     String bgresid = request.getParameter("bgresourceid");
/* 1134 */     String resourcename = request.getParameter("resourcename");
/* 1135 */     String more = request.getParameter("more");
/* 1136 */     StringBuffer temp = new StringBuffer();
/* 1137 */     JSONUtil jsonobj = new JSONUtil();
/* 1138 */     PrintWriter out = response.getWriter();
/* 1139 */     String dirname = SAPUtil.SAP_TRACE_FILES;
/*      */     try {
/* 1141 */       File dir = new File(dirname);
/* 1142 */       if (dir.isDirectory()) {
/* 1143 */         String[] filelist = dir.list();
/* 1144 */         for (int j = 0; j < filelist.length; j++) {
/* 1145 */           if (filelist[j].startsWith("SAP_JobTrace_" + bgresid)) {
/* 1146 */             File tracefile = new File(dirname + File.separator + filelist[j]);
/* 1147 */             if ((!tracefile.exists()) || (!tracefile.canRead())) break;
/* 1148 */             FileReader in = new FileReader(tracefile);
/* 1149 */             StringBuilder contents = new StringBuilder();
/* 1150 */             char[] buffer = new char['က'];
/* 1151 */             int read = 0;
/*      */             do {
/* 1153 */               contents.append(buffer, 0, read);
/* 1154 */               read = in.read(buffer);
/* 1155 */             } while (read >= 0);
/* 1156 */             in.close();
/* 1157 */             JSONArray jobtracelist = new JSONArray(contents.toString());
/* 1158 */             ArrayList list = (ArrayList)JSONUtil.getObject(jobtracelist.toString());
/* 1159 */             if ((more != null) && ("false".equals(more))) {
/* 1160 */               if (list.size() >= 4) {
/* 1161 */                 ArrayList limitlist = new ArrayList();
/* 1162 */                 for (int i = 0; i <= 4; i++) {
/* 1163 */                   limitlist.add(list.get(i));
/*      */                 }
/* 1165 */                 request.setAttribute("TraceHistory", limitlist);
/*      */               } else {
/* 1167 */                 request.setAttribute("TraceHistory", list);
/*      */               }
/* 1169 */               for (int i = 0; i < jobtracelist.length(); i++) {
/* 1170 */                 if ((jobtracelist.getJSONObject(i).getString("JOBNAME").equals(jobname)) && (jobtracelist.getJSONObject(i).getString("JOBCOUNT").equals(jobcount))) {
/* 1171 */                   Hashtable trace = (Hashtable)JSONUtil.getObject(jobtracelist.getJSONObject(i).toString());
/* 1172 */                   request.setAttribute("Trace", trace);
/*      */                 }
/*      */               }
/* 1175 */               request.setAttribute("resId", resId);
/* 1176 */               request.setAttribute("bgresourceid", bgresid);
/* 1177 */               request.setAttribute("jobname", jobname);
/* 1178 */               request.setAttribute("resourcename", resourcename);
/* 1179 */               return mapping.findForward("jobtrace");
/*      */             }
/* 1181 */             if ((request.getParameter("more") != null) && (request.getParameter("more").equals("true"))) {
/* 1182 */               for (int i = 5; i < list.size(); i++) {
/* 1183 */                 Hashtable ht = (Hashtable)list.get(i);
/* 1184 */                 temp.append("<tr  onmouseover=\"this.className='mondetailsHeaderHover' \" onmouseout=\"this.className='mondetailsHeader'\" height='30' >");
/* 1185 */                 temp.append("<td height='28' class='whitegrayborderbr' width='40%'>").append(ht.get("DATE")).append("</td>");
/* 1186 */                 temp.append("<td height='28' class='whitegrayborderbr' width='40%'>").append(ht.get("JOBCOUNT")).append("</td>");
/* 1187 */                 temp.append("<td height='28' class='whitegrayborderbr' width='20%' nowrap><a href='javascript:;'>").append(FormatUtil.getString("am.webclient.jdk15.viewtrace.text")).append("</a></td>");
/* 1188 */                 temp.append("</tr>");
/* 1189 */                 temp.append("<tr style='display:none' id='").append(ht.get("JOBCOUNT")).append("'><td colspan='2'>");
/* 1190 */                 temp.append("<table>");
/* 1191 */                 ArrayList trace = (ArrayList)ht.get("ERRORLOG");
/* 1192 */                 for (int k = 0; k < trace.size(); k++) {
/* 1193 */                   Hashtable log = (Hashtable)trace.get(k);
/* 1194 */                   temp.append("<tr>");
/* 1195 */                   if (k % 2 == 0) {
/* 1196 */                     temp.append("<td class='yellowgrayborderbr'  style='padding:3px'><span class='bodytext'>").append(log.get("TIME")).append("</span></td>");
/* 1197 */                     temp.append("<td class='yellowgrayborderbr'  style='padding:3px'><span class='bodytext'>").append(log.get("TEXT")).append("</span></td>");
/*      */                   } else {
/* 1199 */                     temp.append("<td class='whitegrayborderbr' style='padding:3px'><span class='bodytext'>").append(log.get("TIME")).append("</span></td>");
/* 1200 */                     temp.append("<td class='whitegrayborderbr'  style='padding:3px'><span class='bodytext'>").append(log.get("TEXT")).append("</span></td>");
/*      */                   }
/* 1202 */                   temp.append("</tr>");
/*      */                 }
/* 1204 */                 temp.append("</table>");
/* 1205 */                 temp.append("</td></tr>");
/*      */               }
/* 1207 */               out.println(temp);
/* 1208 */               out.flush();
/*      */             }
/* 1210 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1217 */       e.printStackTrace();
/*      */     }
/* 1219 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward alertDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1226 */       response.setContentType("text/html; charset=UTF-8");
/* 1227 */       String resid = request.getParameter("resourceid");
/* 1228 */       String query = "select * from AM_SAP_Alerts where resourceid=" + resid + " order by ALERTDATE DESC ,ALERTTIME DESC, COLOUR DESC";
/* 1229 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 1232 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1233 */         ArrayList alerts = new ArrayList();
/* 1234 */         while (rs.next()) {
/* 1235 */           Properties alert = new Properties();
/* 1236 */           alert.setProperty("ID", rs.getString("ID"));
/* 1237 */           alert.setProperty("ALSYSID", rs.getString("ALSYSID"));
/* 1238 */           alert.setProperty("MSEGNAME", rs.getString("MSEGNAME"));
/* 1239 */           Date date = new Date(rs.getLong("ALERTDATE"));
/* 1240 */           SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
/* 1241 */           alert.setProperty("ALERTDATE", sdf.format(date));
/* 1242 */           sdf = new SimpleDateFormat("HH:mm:ss");
/* 1243 */           alert.setProperty("ALERTTIME", sdf.format(new Date(rs.getLong("ALERTTIME") + AMAutomaticPortChanger.getSAPOffsetTime())));
/* 1244 */           alert.setProperty("SHORTNAME", rs.getString("SHORTNAME"));
/* 1245 */           alert.setProperty("OBJECTNAME", rs.getString("OBJECTNAME"));
/* 1246 */           alert.setProperty("ALSTATUS", rs.getString("ALSTATUS"));
/* 1247 */           alert.setProperty("COLOUR", rs.getString("COLOUR"));
/* 1248 */           alert.setProperty("SEVERITY", rs.getString("SEVERITY"));
/* 1249 */           alert.setProperty("MSG", rs.getString("MSG"));
/*      */           
/* 1251 */           alerts.add(alert);
/*      */         }
/* 1253 */         request.setAttribute("alertCount", alerts.size() + "");
/* 1254 */         request.setAttribute("alertInfo", alerts);
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1258 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1262 */         closeResultSet(rs);
/*      */       }
/*      */       
/* 1265 */       String o = request.getParameter("noredirect");
/* 1266 */       if ((o != null) && (o.equals("false"))) {
/* 1267 */         return mapping.findForward("alert");
/*      */       }
/*      */     } catch (Exception e) {
/* 1270 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1273 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward completeAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1278 */       String resid = request.getParameter("resourceid");
/* 1279 */       String[] alertstobecompleted = request.getParameterValues("alertstobecompleted");
/*      */       
/* 1281 */       if (alertstobecompleted != null) {
/* 1282 */         String alerts = "";
/* 1283 */         int i = 0; for (int len = alertstobecompleted.length; i < len; i++) {
/* 1284 */           if (!alertstobecompleted[i].equals("on")) {
/* 1285 */             alerts = alerts + alertstobecompleted[i] + ",";
/*      */           }
/*      */         }
/* 1288 */         if (alerts.endsWith(",")) {
/* 1289 */           alerts = alerts.substring(0, alerts.lastIndexOf(","));
/*      */         }
/* 1291 */         StartUtil.printStr("SAPAction : Alerts to be completed " + alerts);
/*      */         
/* 1293 */         String query = "select * from AM_SAP_Alerts where resourceid=" + resid + " and ID IN (" + alerts + ")";
/* 1294 */         ResultSet rs = null;
/* 1295 */         ArrayList alertlist = new ArrayList();
/*      */         try
/*      */         {
/* 1298 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1299 */           while (rs.next()) {
/* 1300 */             HashMap m = new HashMap();
/* 1301 */             m.put("ID", rs.getString("ID"));
/* 1302 */             m.put("ALSYSID", rs.getString("ALSYSID"));
/* 1303 */             m.put("MSEGNAME", rs.getString("MSEGNAME"));
/* 1304 */             m.put("ALUNIQNUM", rs.getString("ALUNIQNUM"));
/* 1305 */             m.put("ALINDEX", rs.getString("ALINDEX"));
/* 1306 */             m.put("DUMMYALIGN", rs.getString("DUMMYALIGN"));
/* 1307 */             m.put("ALERTDATE", new Date(rs.getLong("ALERTDATE")));
/* 1308 */             m.put("ALERTTIME", new Date(rs.getLong("ALERTTIME")));
/*      */             
/* 1310 */             alertlist.add(m);
/*      */           }
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/* 1315 */           exp.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 1319 */           closeResultSet(rs);
/*      */         }
/* 1321 */         StartUtil.printStr("SAPAction : Before completion " + alertlist);
/*      */         
/* 1323 */         Properties p = SAPDataCollector.getConnProps(Integer.parseInt(resid));
/* 1324 */         CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(p);
/* 1325 */         handler.connect();
/* 1326 */         handler.logon();
/* 1327 */         ArrayList comp = handler.completeAlerts(alertlist);
/* 1328 */         handler.close();
/*      */         
/* 1330 */         StartUtil.printStr("SAPAction : After completion " + comp);
/*      */         
/* 1332 */         Statement s = AMConnectionPool.getConnection().createStatement();
/*      */         
/* 1334 */         int i = 0; for (int size = comp.size(); i < size; i++) {
/* 1335 */           HashMap alert = (HashMap)comp.get(i);
/* 1336 */           String id = (String)alert.get("ID");
/* 1337 */           int returncode = ((Integer)alert.get("RC")).intValue();
/* 1338 */           if (returncode == 0) {
/* 1339 */             s.addBatch("delete from AM_SAP_Alerts where resourceid=" + resid + " and ID=" + id);
/*      */           }
/*      */         }
/*      */         try {
/* 1343 */           s.executeBatch();
/*      */         } catch (Exception e) {
/* 1345 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/* 1348 */           if (s != null) {
/* 1349 */             s.close();
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1354 */       e.printStackTrace();
/*      */     }
/* 1356 */     return alertDetails(mapping, form, request, response);
/*      */   }
/*      */   
/*      */   public ActionForward showRFCDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1361 */       response.setContentType("text/json; charset=UTF-8");
/* 1362 */       String resid = request.getParameter("resourceid");
/* 1363 */       PrintWriter pw = response.getWriter();
/* 1364 */       String jsonString = SAPHandler.getTRFCData(resid);
/* 1365 */       pw.write(jsonString);
/* 1366 */       pw.flush();
/* 1367 */       pw.close();
/*      */     } catch (Exception e) {
/* 1369 */       e.printStackTrace();
/*      */     }
/* 1371 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showQoutSchedular(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*      */     try {
/* 1376 */       response.setContentType("text/json; charset=UTF-8");
/* 1377 */       String resid = request.getParameter("resourceid");
/* 1378 */       PrintWriter pw = response.getWriter();
/* 1379 */       String jsonString = SAPHandler.getQoutSchedular(resid);
/* 1380 */       pw.write(jsonString);
/* 1381 */       pw.flush();
/* 1382 */       pw.close();
/*      */     } catch (Exception e) {
/* 1384 */       e.printStackTrace();
/*      */     }
/* 1386 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showQinSchedular(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1390 */     try { response.setContentType("text/json; charset=UTF-8");
/* 1391 */       String resid = request.getParameter("resourceid");
/* 1392 */       PrintWriter pw = response.getWriter();
/* 1393 */       String jsonString = SAPHandler.getQinSchedular(resid);
/* 1394 */       pw.write(jsonString);
/* 1395 */       pw.flush();
/* 1396 */       pw.close();
/*      */     } catch (Exception e) {
/* 1398 */       e.printStackTrace();
/*      */     }
/* 1400 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showQoutData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1404 */     try { response.setContentType("text/json; charset=UTF-8");
/* 1405 */       String resid = request.getParameter("resourceid");
/* 1406 */       PrintWriter pw = response.getWriter();
/* 1407 */       String jsonString = SAPHandler.getQoutData(resid);
/* 1408 */       pw.write(jsonString);
/* 1409 */       pw.flush();
/* 1410 */       pw.close();
/*      */     } catch (Exception e) {
/* 1412 */       e.printStackTrace();
/*      */     }
/* 1414 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showQinData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/* 1418 */     try { response.setContentType("text/json; charset=UTF-8");
/* 1419 */       String resid = request.getParameter("resourceid");
/* 1420 */       PrintWriter pw = response.getWriter();
/* 1421 */       String jsonString = SAPHandler.getQinData(resid);
/* 1422 */       pw.write(jsonString);
/* 1423 */       pw.flush();
/* 1424 */       pw.close();
/*      */     } catch (Exception e) {
/* 1426 */       e.printStackTrace();
/*      */     }
/* 1428 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showCCMSMonitorSets(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1435 */       response.setContentType("text/html; charset=UTF-8");
/* 1436 */       Properties conProps = new Properties();
/* 1437 */       conProps.setProperty("host", request.getParameter("host"));
/* 1438 */       conProps.setProperty("client", request.getParameter("logonclient"));
/* 1439 */       conProps.setProperty("language", request.getParameter("language"));
/* 1440 */       conProps.setProperty("systemnumber", request.getParameter("systemnumber"));
/* 1441 */       conProps.setProperty("username", request.getParameter("username"));
/* 1442 */       conProps.setProperty("password", request.getParameter("password"));
/* 1443 */       CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(conProps);
/*      */       try
/*      */       {
/* 1446 */         handler.connect();
/* 1447 */         handler.logon();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1451 */         StartUtil.printStr("SAPAction : Problem in connecting to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1452 */         exp.printStackTrace();
/*      */       }
/* 1454 */       List<String> monitorsetlist = handler.getCCMSMonitorSets();
/*      */       try
/*      */       {
/* 1457 */         handler.close();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1461 */         StartUtil.printStr("SAPAction : Problem in closing session to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1462 */         exp.printStackTrace();
/*      */       }
/* 1464 */       request.setAttribute("monitorset", monitorsetlist);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1468 */       StartUtil.printStr("SAPAction : Problem occured while choosing custom CCMS attributes. Error Message : " + e.getMessage());
/* 1469 */       e.printStackTrace();
/*      */     }
/* 1471 */     return mapping.findForward("showmonitorsets");
/*      */   }
/*      */   
/*      */   public ActionForward createCCMSMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1478 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/* 1480 */         ActionErrors errors = new ActionErrors();
/* 1481 */         ActionMessages messages = new ActionMessages();
/* 1482 */         String haid = request.getParameter("haid");
/* 1483 */         String resourcetype = ((AMActionForm)form).getType();
/* 1484 */         Properties argsprops = new Properties();
/* 1485 */         argsprops.setProperty("monitorType", resourcetype);
/* 1486 */         for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */         {
/* 1488 */           String param = (String)e.nextElement();
/* 1489 */           if (!argsprops.containsKey(param))
/*      */           {
/* 1491 */             argsprops.setProperty(param, request.getParameter(param));
/*      */           }
/* 1493 */           if (param.equals("haid"))
/*      */           {
/* 1495 */             String[] multiVal = request.getParameterValues(param);
/* 1496 */             if ((multiVal != null) && (multiVal.length > 0)) {
/* 1497 */               String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/* 1498 */               val = val.substring(1, val.length() - 1);
/* 1499 */               argsprops.setProperty(param, val);
/*      */             }
/*      */           }
/*      */         }
/*      */         try
/*      */         {
/* 1505 */           HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/* 1506 */           ArrayList<String> al1 = new ArrayList();
/* 1507 */           String displayname = request.getParameter("displayname");
/* 1508 */           if ((displayname == null) || (displayname.trim().length() == 0)) {
/* 1509 */             displayname = request.getParameter("displayName");
/*      */           }
/* 1511 */           String status = "Success";
/* 1512 */           String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/* 1513 */           String masDisplayName = (String)responseMap.get("managedServerDispName");
/* 1514 */           al1.add(displayname);
/* 1515 */           if (((String)responseMap.get("addStatus")).equals("false")) {
/* 1516 */             status = "Failed";
/* 1517 */             message = (String)responseMap.get("message");
/*      */           }
/* 1519 */           al1.add(status);
/* 1520 */           al1.add(message);
/* 1521 */           al1.add(masDisplayName);
/* 1522 */           request.setAttribute("discoverystatus", al1);
/* 1523 */           request.setAttribute("type", resourcetype);
/* 1524 */           request.setAttribute("basetype", "Script Monitor");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1542 */           e.printStackTrace();
/*      */         }
/* 1544 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid);
/*      */       }
/* 1546 */       int resourceid = -1;
/* 1547 */       String givenhost = ((AMActionForm)form).getHost();
/* 1548 */       String logonclient = ((AMActionForm)form).getLogonClient();
/* 1549 */       String systemnumber = ((AMActionForm)form).getSystemNumber();
/* 1550 */       String language = ((AMActionForm)form).getLanguage();
/* 1551 */       String username = ((AMActionForm)form).getUsername();
/* 1552 */       String password = ((AMActionForm)form).getPassword();
/* 1553 */       String monitorset = ((AMActionForm)form).getVersion();
/* 1554 */       String pollInterval = request.getParameter("pollInterval");
/* 1555 */       String type = ((AMActionForm)form).getType();
/* 1556 */       String displayname = ((AMActionForm)form).getDisplayname();
/* 1557 */       String resourcename = givenhost + "_" + monitorset + "_" + type;
/* 1558 */       String routerString = "";
/* 1559 */       if (((AMActionForm)form).getUsedRouterString()) {
/* 1560 */         routerString = ((AMActionForm)form).getRouterString();
/*      */       }
/*      */       
/* 1563 */       ArrayList msg = new ArrayList();
/* 1564 */       String[] selMonitorGroups = request.getParameterValues("haid");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1585 */       String fromRESTAPI = request.getParameter("fromRESTAPI");
/* 1586 */       if ((fromRESTAPI != null) && (fromRESTAPI.equals("true"))) {
/* 1587 */         ResourceTypeIfc resTypeIfc = null;
/*      */         try {
/* 1589 */           DiscoveryInfo discInfo = new DiscoveryInfo();
/* 1590 */           discInfo.setTargetAddress(givenhost);
/* 1591 */           discInfo.setUserProperty("logonClient", logonclient);
/* 1592 */           discInfo.setUserProperty("language", language);
/* 1593 */           discInfo.setUserProperty("systemNumber", systemnumber);
/* 1594 */           discInfo.setUserProperty("username", username);
/* 1595 */           discInfo.setUserProperty("password", password);
/* 1596 */           if ((!"".equals(routerString)) && (routerString != null)) {
/* 1597 */             discInfo.setUserProperty("routerString", routerString);
/*      */           }
/* 1599 */           resTypeIfc = (ResourceTypeIfc)Class.forName("com.adventnet.appmanager.server.sap.discovery.SAPDiscovery").newInstance();
/* 1600 */           boolean result = resTypeIfc.checkResourceType(discInfo, Constants.socketTimeOut);
/* 1601 */           if (!result) {
/* 1602 */             ArrayList<String> addStatus = new ArrayList();
/* 1603 */             addStatus.add(displayname);
/* 1604 */             addStatus.add("Failed");
/* 1605 */             String message = discInfo.getUserProperty("error_message");
/* 1606 */             if (message == null) {
/* 1607 */               message = FormatUtil.getString("am.webclient.discovery.servicenotrunning.text");
/*      */             }
/* 1609 */             addStatus.add(message);
/* 1610 */             request.setAttribute("type", type);
/* 1611 */             request.setAttribute("discoverystatus", addStatus);
/* 1612 */             return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=SAP-CCMS&restype=SAP-CCMS");
/*      */           }
/*      */         } catch (Throwable t) {
/* 1615 */           t.printStackTrace();
/*      */         } finally {
/* 1617 */           resTypeIfc = null;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1622 */       String query = "select RESOURCEID from AM_ManagedObject where RESOURCENAME='" + resourcename + "'";
/*      */       try
/*      */       {
/* 1625 */         ResultSet rs = null;
/*      */         try
/*      */         {
/* 1628 */           rs = AMConnectionPool.executeQueryStmt(query);
/* 1629 */           if (rs.next())
/*      */           {
/* 1631 */             resourceid = rs.getInt("RESOURCEID");
/* 1632 */             query = "update AM_SAP_Config set USERNAME='" + username + "',PASSWORD=" + DBQueryUtil.encode(password) + ",CLIENT='" + logonclient + "',LANGUAGE='" + language + "',SYSTEMNUMBER='" + systemnumber + "',POLLINTERVAL='" + pollInterval + "' where resourceid=" + resourceid;
/*      */           }
/*      */           else
/*      */           {
/* 1636 */             resourceid = NewMonitorUtil.mocreate(displayname, resourcename, type);
/* 1637 */             query = "insert into AM_SAP_Config (RESOURCEID,HOST,CLIENT,LANGUAGE,SYSTEMNUMBER,USERNAME,PASSWORD,MONITORSET,POLLINTERVAL,ROUTERSTRING) values (" + resourceid + ",'" + givenhost + "','" + logonclient + "','" + language + "','" + systemnumber + "','" + username + "'," + DBQueryUtil.encode(password) + ",'" + monitorset + "','" + pollInterval + "','" + routerString + "')";
/*      */           }
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/* 1642 */           exp.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 1646 */           closeResultSet(rs);
/*      */         }
/* 1648 */         AMConnectionPool.executeUpdateStmt(query);
/* 1649 */         msg.add(displayname);
/* 1650 */         msg.add("Success");
/* 1651 */         msg.add("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/* 1652 */         request.setAttribute("discoverystatus", msg);
/* 1653 */         request.setAttribute("type", "SAP-CCMS");
/* 1654 */         request.setAttribute("resourceid", Integer.valueOf(resourceid));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1658 */         StartUtil.printStr("SAP-CCMS_Action : Problem in creating new SAP CCMS Monitor. Error occured in inserting details for " + givenhost + " Error Message : " + e.getMessage());
/* 1659 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1665 */         long scheduleTime = System.currentTimeMillis();
/* 1666 */         SAPCCMSDataCollector ccmsd = new SAPCCMSDataCollector(resourceid);
/* 1667 */         Scheduler schedular = Scheduler.getScheduler("URLMonitor");
/* 1668 */         schedular.scheduleTask(ccmsd, scheduleTime);
/* 1669 */         AMUrlMonitorProcess.instances.put(Integer.valueOf(resourceid), ccmsd);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1673 */         StartUtil.printStr("SAP-CCMS_Action : Problem in creating new SAP CCMS Monitor. Error occured in scheduling data collection for " + givenhost + " Error Message : " + e.getMessage());
/* 1674 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/* 1678 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 1679 */       adder.addInterDependentAttributes(resourceid);
/*      */       
/*      */ 
/* 1682 */       if ((request.getParameter("addtoha") != null) && (request.getParameter("addtoha").equals("true")))
/*      */       {
/*      */         try
/*      */         {
/* 1686 */           String[] resources = { String.valueOf(resourceid) };
/* 1687 */           Vector forUpdate = new Vector();
/* 1688 */           ManagedApplication mo = new ManagedApplication();
/* 1689 */           mo.updateManagedApplicationResources(selMonitorGroups, "xyz", resources, null, forUpdate);
/* 1690 */           if (forUpdate != null)
/*      */           {
/* 1692 */             for (int i = 0; i < forUpdate.size(); i++)
/*      */             {
/* 1694 */               EnterpriseUtil.addUpdateQueryToFile(forUpdate.get(i) + "");
/*      */             }
/*      */           }
/* 1697 */           return new ActionForward("/showapplication.do?haid=" + request.getParameter("haid") + "&method=showApplication");
/*      */         }
/*      */         catch (Exception exp)
/*      */         {
/* 1701 */           StartUtil.printStr("SAP-CCMS_Action : Problem occured while associating SAP CCMS monitor to monitor group. Error Message : " + exp.getMessage());
/* 1702 */           exp.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1708 */       StartUtil.printStr("SAP-CCMS_Action : Problem occured while creating SAP CCMS monitor. Error Message : " + exp.getMessage());
/* 1709 */       exp.printStackTrace();
/*      */     }
/* 1711 */     return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=" + ((AMActionForm)form).getType());
/*      */   }
/*      */   
/*      */   public ActionForward showCCMSDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1716 */     response.setContentType("text/html; charset=UTF-8");
/* 1717 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*      */ 
/* 1720 */     String screenid = null;
/*      */     try
/*      */     {
/* 1723 */       if (CAMDBUtil.camScreenExists(resourceid, "SAP Custom Attributes"))
/*      */       {
/* 1725 */         List screens = CAMDBUtil.getScreens(Long.parseLong(resourceid));
/* 1726 */         screenid = (String)((ArrayList)screens.get(0)).get(0);
/*      */       }
/*      */       else
/*      */       {
/* 1730 */         screenid = CAMDBUtil.insertCAMScreenDetails("SAP Custom Attributes", "", 0, Integer.parseInt(resourceid)) + "";
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1735 */       StartUtil.printStr("SAP-CCMS_Action : Problem in getting screenid for SAP. Error Message : " + e.getMessage());
/* 1736 */       e.printStackTrace();
/*      */     }
/* 1738 */     request.setAttribute("screenid", screenid);
/* 1739 */     request.setAttribute("isfromresourcepage", "true");
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1744 */       int pollint = 5;
/* 1745 */       int connectionTime = 0;
/* 1746 */       long lastCol = System.currentTimeMillis();
/* 1747 */       String hostname = "localhost";
/* 1748 */       String monitorset = "SAP CCMS Monitor Templates";
/* 1749 */       String username = "bcuser";
/* 1750 */       String displayname = "";
/* 1751 */       String lastpoll = "-";
/* 1752 */       String nextpoll = "-";
/* 1753 */       String errorMessage = "";
/*      */       
/* 1755 */       String query = "select max(COLLECTIONTIME) as coltime from AM_ManagedObjectData where RESID=" + resourceid;
/* 1756 */       ResultSet rs = null;
/*      */       try
/*      */       {
/* 1759 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1760 */         if (rs.next())
/*      */         {
/* 1762 */           lastCol = rs.getLong("coltime");
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1767 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1771 */         closeResultSet(rs);
/*      */       }
/*      */       
/* 1774 */       query = "select DISPLAYNAME,HOST,MONITORSET,USERNAME,ROUTERSTRING,POLLINTERVAL,RESPONSETIME,ERROR_MESSAGE from AM_ManagedObject join AM_SAP_Config on AM_ManagedObject.RESOURCEID=AM_SAP_Config.RESOURCEID left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=AM_ManagedObject.RESOURCEID and AM_ManagedObjectData.COLLECTIONTIME=" + lastCol + " left outer join AM_MONITOR_ERRORS on AM_MONITOR_ERRORS.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_MONITOR_ERRORS.COLLECTIONTIME=" + lastCol + " where AM_ManagedObject.RESOURCEID=" + resourceid;
/*      */       try
/*      */       {
/* 1777 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 1778 */         if (rs.next())
/*      */         {
/* 1780 */           displayname = rs.getString("DISPLAYNAME");
/* 1781 */           hostname = rs.getString("HOST");
/* 1782 */           monitorset = rs.getString("MONITORSET");
/* 1783 */           username = rs.getString("USERNAME");
/* 1784 */           pollint = rs.getInt("POLLINTERVAL");
/* 1785 */           connectionTime = rs.getInt("RESPONSETIME");
/* 1786 */           if (rs.getString("ERROR_MESSAGE") != null)
/*      */           {
/* 1788 */             errorMessage = rs.getString("ERROR_MESSAGE");
/*      */           }
/* 1790 */           String routerString = rs.getString("ROUTERSTRING");
/* 1791 */           if ((routerString != null) && (!" ".equals(routerString)) && (!routerString.isEmpty())) {
/* 1792 */             ((AMActionForm)form).setUsedRouterString(true);
/* 1793 */             ((AMActionForm)form).setRouterString(routerString);
/*      */           } else {
/* 1795 */             ((AMActionForm)form).setUsedRouterString(false);
/* 1796 */             ((AMActionForm)form).setRouterString(routerString);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1802 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1806 */         closeResultSet(rs);
/*      */       }
/*      */       
/* 1809 */       if ((!errorMessage.equals("")) && (!errorMessage.equals("am.datacollection.success")) && (!errorMessage.equals("am.datacollection.managed")))
/*      */       {
/* 1811 */         ActionErrors errors = new ActionErrors();
/* 1812 */         errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString(errorMessage)));
/* 1813 */         saveErrors(request, errors);
/*      */       }
/* 1815 */       if (lastCol != 0L)
/*      */       {
/* 1817 */         lastpoll = FormatUtil.formatDT(String.valueOf(lastCol));
/* 1818 */         nextpoll = FormatUtil.formatDT(String.valueOf(lastCol + pollint * 60 * 1000));
/*      */       }
/*      */       else
/*      */       {
/* 1822 */         nextpoll = FormatUtil.formatDT(String.valueOf(System.currentTimeMillis() + pollint * 60 * 1000));
/*      */       }
/* 1824 */       ((AMActionForm)form).setPollInterval(pollint);
/* 1825 */       ((AMActionForm)form).setUsername(username);
/* 1826 */       ((AMActionForm)form).setDisplayname(displayname);
/* 1827 */       request.setAttribute("displayname", displayname);
/* 1828 */       request.setAttribute("hostname", hostname);
/* 1829 */       request.setAttribute("monitorset", monitorset);
/* 1830 */       request.setAttribute("LASTDC", lastpoll);
/* 1831 */       request.setAttribute("NEXTDC", nextpoll);
/* 1832 */       request.setAttribute("connectiontime", Integer.valueOf(connectionTime));
/* 1833 */       request.setAttribute("type", "SAP-CCMS");
/*      */       
/* 1835 */       String reloadperiod = (String)request.getAttribute("reloadperiod");
/* 1836 */       if (reloadperiod == null)
/*      */       {
/* 1838 */         reloadperiod = "300";
/* 1839 */         request.setAttribute("reloadperiod", reloadperiod);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1844 */       e.printStackTrace();
/*      */     }
/* 1846 */     return new ActionForward("/jsp/sap/CCMSDetails.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward showCCMSMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1853 */       response.setContentType("text/html; charset=UTF-8");
/* 1854 */       String resid = request.getParameter("resourceid");
/* 1855 */       Properties p = SAPDataCollector.getConnProps(Integer.parseInt(resid));
/* 1856 */       CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(p);
/*      */       try
/*      */       {
/* 1859 */         handler.connect();
/* 1860 */         handler.logon();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1864 */         StartUtil.printStr("SAPAction : Problem in connecting to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1865 */         exp.printStackTrace();
/*      */         
/* 1867 */         int error = handler.getError();
/* 1868 */         ActionErrors errors = new ActionErrors();
/* 1869 */         if (error == 2)
/*      */         {
/* 1871 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.disc.error3")));
/* 1872 */           saveErrors(request, errors);
/*      */         }
/* 1874 */         else if (error == 1)
/*      */         {
/* 1876 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.sap.disc.error2")));
/* 1877 */           saveErrors(request, errors);
/*      */         }
/* 1879 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + resid);
/*      */       }
/* 1881 */       List<String> monitors = handler.getCCMSMonitors();
/* 1882 */       request.setAttribute("monitorset", p.getProperty("monitorset"));
/* 1883 */       request.setAttribute("monitors", monitors);
/*      */       try
/*      */       {
/* 1886 */         handler.close();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1890 */         StartUtil.printStr("SAPAction : Problem in closing session to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1891 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1896 */       StartUtil.printStr("SAPAction : Problem occured while choosing custom CCMS attributes. Error Message : " + e.getMessage());
/* 1897 */       e.printStackTrace();
/*      */     }
/* 1899 */     return mapping.findForward("choosemonitorset");
/*      */   }
/*      */   
/*      */   public ActionForward showCCMSMonitorTreeElements(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1906 */       response.setContentType("text/html; charset=UTF-8");
/* 1907 */       String resid = request.getParameter("resourceid");
/* 1908 */       String monitorset = request.getParameter("set");
/* 1909 */       String monitorname = request.getParameter("monitor");
/* 1910 */       String screenid = request.getParameter("screenid");
/* 1911 */       Properties p = SAPDataCollector.getConnProps(Integer.parseInt(resid));
/* 1912 */       CCMSQueryHandlerInf handler = SAPUtil.getCCMSQueryHandler(p);
/*      */       try
/*      */       {
/* 1915 */         handler.connect();
/* 1916 */         handler.logon();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1920 */         StartUtil.printStr("SAPAction : Problem in connecting to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1921 */         exp.printStackTrace();
/*      */       }
/* 1923 */       Map monitors = handler.getCCMSMonitorTreeElements(monitorset, monitorname);
/* 1924 */       monitors.remove("monElementList");
/*      */       try
/*      */       {
/* 1927 */         handler.close();
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/* 1931 */         StartUtil.printStr("SAPAction : Problem in closing session to SAP server while choosing custom CCMS attributes. Error Message : " + exp.getMessage());
/* 1932 */         exp.printStackTrace();
/*      */       }
/* 1934 */       request.setAttribute("monitors", monitors);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1938 */       StartUtil.printStr("SAPAction : Problem occured while choosing custom CCMS attributes. Error Message : " + e.getMessage());
/* 1939 */       e.printStackTrace();
/*      */     }
/* 1941 */     return mapping.findForward("monitorelementlist");
/*      */   }
/*      */   
/*      */   public ActionForward createCustomCCMSMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1946 */     String resourceid = request.getParameter("resourceid");
/*      */     try
/*      */     {
/* 1949 */       String screenid = request.getParameter("screenid");
/* 1950 */       String[] monitorelements = request.getParameterValues("monitorelementscheckbox");
/* 1951 */       if (monitorelements != null)
/*      */       {
/* 1953 */         int size = monitorelements.length;
/* 1954 */         for (int i = 0; i < size; i++)
/*      */         {
/* 1956 */           String tmpStr = monitorelements[i];
/* 1957 */           StringTokenizer st = new StringTokenizer(tmpStr, "||");
/* 1958 */           String monitorname = st.nextToken();
/* 1959 */           String groupname = st.nextToken();
/* 1960 */           String attribute = st.nextToken();
/* 1961 */           String typeclass = st.nextToken();
/* 1962 */           int type = 1;
/* 1963 */           if (typeclass.equalsIgnoreCase("100"))
/*      */           {
/* 1965 */             type = 0;
/*      */           }
/*      */           
/*      */           try
/*      */           {
/* 1970 */             CAMDBUtil.addCCMSAttributes(Integer.parseInt(resourceid), Integer.parseInt(screenid), attribute, type, groupname);
/*      */           }
/*      */           catch (Exception exp)
/*      */           {
/* 1974 */             StartUtil.printStr("SAP-CCMS Action : Problem occured while adding custom CCMS attribute " + tmpStr + " . Error Message : " + exp.getMessage());
/* 1975 */             exp.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1982 */       StartUtil.printStr("SAPAction : Problem occured while choosing custom CCMS attributes. Error Message : " + e.getMessage());
/* 1983 */       e.printStackTrace();
/*      */     }
/* 1985 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*      */   }
/*      */   
/*      */   private void closeResultSet(ResultSet rs)
/*      */   {
/* 1990 */     AMConnectionPool.closeStatement(rs);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\sap\SAPAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */