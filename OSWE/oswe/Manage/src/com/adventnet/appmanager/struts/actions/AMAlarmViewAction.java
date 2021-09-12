/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmGraphUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.webclient.util.SavePreferencesAction;
/*      */ import com.adventnet.nms.fe.common.CustomViewException;
/*      */ import com.adventnet.nms.fe.common.TableColumn;
/*      */ import com.adventnet.nms.util.NmsUtil;
/*      */ import com.adventnet.nms.webclient.common.WebClientUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.URLDecoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.Action;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AMAlarmViewAction
/*      */   extends Action
/*      */ {
/*      */   public static final String VIEWDATASTRING = "viewData";
/*      */   
/*      */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws ServletException, IOException, CustomViewException, Exception
/*      */   {
/*   80 */     Vector data = new Vector();
/*   81 */     TableColumn[] headerList = null;
/*   82 */     String viewId = "Alerts.5";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   89 */     response.setContentType("text/html;charset=UTF-8");
/*      */     
/*   91 */     String displayName = "";
/*   92 */     long total = 0L;
/*   93 */     String val = null;
/*   94 */     String tempname = null;
/*      */     
/*      */ 
/*   97 */     String loginName = "";
/*   98 */     boolean isBSGUser = false;
/*   99 */     if (EnterpriseUtil.isIt360MSPEdition)
/*      */     {
/*  101 */       loginName = request.getRemoteUser();
/*  102 */       It360SPUserManagementUtil usrMgmt = new It360SPUserManagementUtil();
/*  103 */       isBSGUser = usrMgmt.isBSGUser(loginName);
/*      */     }
/*      */     
/*  106 */     String isCor = request.getParameter("isCorrelated");
/*      */     
/*  108 */     if (isCor != null)
/*      */     {
/*  110 */       request.getSession().setAttribute("isCorrelation", isCor);
/*      */     }
/*      */     
/*  113 */     String sessionIsCor = (String)request.getSession().getAttribute("isCorrelation");
/*      */     
/*      */ 
/*      */ 
/*  117 */     ArrayList monitorLevelResTypeList = Constants.getAllMonitorLevelResourceTypes();
/*  118 */     String monitorLevelResType = monitorLevelResTypeList.toString().replace("[", "");
/*  119 */     monitorLevelResType = monitorLevelResType.toString().replace("]", "");
/*      */     
/*      */ 
/*      */ 
/*  123 */     String resTypeFilter = " inner join AM_ManagedObject amo on amo.RESOURCEID=Alert.source and amo.TYPE in (" + monitorLevelResType + ")";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  128 */     String groupNameCondition = " AND (GROUPNAME='AppManager_Component') ";
/*      */     
/*  130 */     if ((sessionIsCor == null) || ((sessionIsCor != null) && (sessionIsCor.equals("true"))))
/*      */     {
/*  132 */       resTypeFilter = "";
/*  133 */       groupNameCondition = " AND (GROUPNAME='AppManager') ";
/*      */     }
/*      */     
/*      */ 
/*  137 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*      */ 
/*      */ 
/*  141 */     String tempviewId = request.getParameter("viewId");
/*  142 */     String timeId = request.getParameter("timeId");
/*  143 */     String tempdisplayName = request.getParameter("displayName");
/*  144 */     request.setAttribute("isAlertsPage", "true");
/*  145 */     Hashtable ht = getAlarmCount(sessionIsCor, request, isBSGUser, loginName);
/*  146 */     request.setAttribute("alertdetails", ht);
/*  147 */     String timeFilter = "";
/*  148 */     if ("Alerts.2".equalsIgnoreCase(timeId)) {
/*  149 */       long currentTime = System.currentTimeMillis();
/*  150 */       long lastonehour = currentTime - 3600000L;
/*  151 */       timeFilter = " and MODTIME > " + lastonehour + " ";
/*      */     }
/*  153 */     if ("Alerts.4".equalsIgnoreCase(timeId)) {
/*  154 */       long currentTime = System.currentTimeMillis();
/*  155 */       long lastoneday = currentTime - 86400000L;
/*  156 */       timeFilter = " and MODTIME > " + lastoneday + " ";
/*      */     }
/*      */     
/*  159 */     if ("Alerts.32".equalsIgnoreCase(timeId))
/*      */     {
/*  161 */       long currentTime = System.currentTimeMillis();
/*  162 */       long last_twohour = currentTime - 7200000L;
/*  163 */       timeFilter = " and MODTIME > " + last_twohour + " ";
/*      */     }
/*      */     
/*  166 */     if ("Alerts.34".equalsIgnoreCase(timeId))
/*      */     {
/*  168 */       long currentTime = System.currentTimeMillis();
/*  169 */       long last_fourhour = currentTime - 14400000L;
/*  170 */       timeFilter = " and MODTIME > " + last_fourhour + " ";
/*      */     }
/*      */     
/*  173 */     if ("Alerts.36".equalsIgnoreCase(timeId))
/*      */     {
/*  175 */       long currentTime = System.currentTimeMillis();
/*  176 */       long last_sixhour = currentTime - 21600000L;
/*  177 */       timeFilter = " and MODTIME > " + last_sixhour + " ";
/*      */     }
/*      */     
/*  180 */     if ("Alerts.37".equalsIgnoreCase(timeId))
/*      */     {
/*  182 */       long currentTime = System.currentTimeMillis();
/*  183 */       long last_twelve = currentTime - 43200000L;
/*  184 */       timeFilter = " and MODTIME > " + last_twelve + " ";
/*      */     }
/*      */     
/*  187 */     if ("Alerts.38".equalsIgnoreCase(timeId))
/*      */     {
/*  189 */       long[] timeStamp = ReportUtilities.getTimeStamp("0");
/*  190 */       timeFilter = " and MODTIME > " + timeStamp[0] + " ";
/*      */     }
/*      */     
/*  193 */     if ("Alerts.39".equalsIgnoreCase(timeId))
/*      */     {
/*  195 */       long[] timeStamp = ReportUtilities.getTimeStamp("3");
/*  196 */       timeFilter = " and MODTIME > " + timeStamp[0] + " and MODTIME < " + timeStamp[1] + " ";
/*      */     }
/*      */     
/*  199 */     if ((tempviewId != null) && (tempviewId.equals("Trap")))
/*      */     {
/*  201 */       ArrayList list = executeTrapView();
/*  202 */       request.setAttribute("list", list);
/*  203 */       return mapping.findForward("trapView");
/*      */     }
/*  205 */     Vector list = WebClientUtil.getModuleIncrements(userName, "Alerts");
/*  206 */     request.setAttribute("PAGE_LENGTHS", list);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  211 */     String startIndex = request.getParameter("FROM_INDEX");
/*  212 */     String orderByColumn = request.getParameter("orderByColumn");
/*  213 */     if (orderByColumn == null)
/*      */     {
/*  215 */       orderByColumn = "MODTIME";
/*      */     }
/*  217 */     if (orderByColumn.equals("message"))
/*      */     {
/*  219 */       orderByColumn = "MMESSAGE";
/*      */     }
/*  221 */     String isAscending = request.getParameter("isAscending");
/*  222 */     String orderby = null;
/*  223 */     if (isAscending == null)
/*      */     {
/*  225 */       isAscending = "false";
/*  226 */       orderby = "desc";
/*      */ 
/*      */ 
/*      */     }
/*  230 */     else if (isAscending.equals("true"))
/*      */     {
/*  232 */       orderby = "asc";
/*      */     }
/*      */     else
/*      */     {
/*  236 */       orderby = "desc";
/*      */     }
/*      */     
/*  239 */     String pageNumber = "1";
/*  240 */     if (request.getParameter("PAGE_NUMBER") != null) {
/*  241 */       pageNumber = request.getParameter("PAGE_NUMBER");
/*      */     }
/*      */     
/*  244 */     String severity = request.getParameter("severity");
/*  245 */     if (tempviewId != null)
/*      */     {
/*  247 */       viewId = tempviewId;
/*      */     }
/*      */     
/*  250 */     if (tempdisplayName != null)
/*      */     {
/*  252 */       displayName = tempdisplayName;
/*      */     }
/*      */     
/*      */ 
/*  256 */     SavePreferencesAction spa = new SavePreferencesAction();
/*  257 */     HttpSession session = request.getSession();
/*  258 */     String viewLength = request.getParameter("viewLength");
/*  259 */     if ((viewLength == null) || (viewLength.trim().equals("")))
/*      */     {
/*  261 */       if (session.getAttribute("alarm_viewlength") != null) {
/*  262 */         viewLength = (String)session.getAttribute("alarm_viewlength");
/*      */       } else {
/*  264 */         viewLength = (String)list.elementAt(0);
/*  265 */         session.setAttribute("alarm_viewlength", viewLength);
/*  266 */         spa.savealarmviewlength(request);
/*      */       }
/*  268 */       viewLength = (viewLength == null) || (viewLength.trim().equals("")) ? "25" : viewLength;
/*      */     }
/*      */     else {
/*  271 */       session.setAttribute("alarm_viewlength", viewLength);
/*  272 */       spa.savealarmviewlength(request);
/*      */     }
/*      */     
/*  275 */     if (startIndex == null)
/*      */     {
/*  277 */       startIndex = "0";
/*      */     }
/*  279 */     if (!startIndex.equals("0"))
/*      */     {
/*  281 */       startIndex = String.valueOf(Integer.parseInt(startIndex) - 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  286 */     AMActionForm amform = (AMActionForm)form;
/*  287 */     String haid = amform.getHaid();
/*  288 */     String monitor = request.getParameter("monitor");
/*  289 */     if (monitor != null) {
/*  290 */       monitor = URLDecoder.decode(monitor);
/*  291 */       if ((monitor.equalsIgnoreCase("Trap")) || (monitor.equalsIgnoreCase("JMXNotification"))) {
/*  292 */         monitor = monitor + ",";
/*      */       }
/*      */     }
/*  295 */     String aliasname = null;
/*  296 */     String valueid = null;
/*      */     
/*      */ 
/*  299 */     if ((haid != null) && (haid.equals("-")) && (monitor.equals("-")))
/*      */     {
/*  301 */       haid = request.getParameter("haid");
/*      */       
/*      */ 
/*  304 */       if ((haid != null) && (!haid.equals("null"))) {
/*  305 */         amform.setHaid(haid);
/*      */       }
/*  307 */       monitor = URLDecoder.decode(request.getParameter("monitor"));
/*  308 */       if ((monitor != null) && (!monitor.equals("null"))) {
/*  309 */         amform.setMonitor(monitor);
/*      */       }
/*      */     }
/*  312 */     String query = "";
/*  313 */     String totalcountquery = "";
/*  314 */     Hashtable datatable = null;
/*  315 */     Object graph = null;
/*  316 */     String sevQueryInterrupter = "";
/*  317 */     if (severity != null)
/*      */     {
/*  319 */       sevQueryInterrupter = " AND SEVERITY=" + severity;
/*      */     }
/*      */     
/*  322 */     boolean privalegedUser = false;
/*      */     
/*  324 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/*  325 */       privalegedUser = true;
/*      */     }
/*      */     
/*      */ 
/*  329 */     String widgetId = request.getParameter("widgetId");
/*  330 */     if ((widgetId != null) && (!widgetId.trim().equals("")))
/*      */     {
/*  332 */       request.setAttribute("alertsfor", "true");
/*  333 */       groupNameCondition = " AND (GROUPNAME='AppManager') ";
/*  334 */       Vector widgetResIds = new Vector(getWidgetResourceIds(Integer.parseInt(widgetId)));
/*      */       
/*      */ 
/*  337 */       if ((privalegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */       {
/*  339 */         String owner = request.getRemoteUser();
/*  340 */         Vector resourceids = new Vector();
/*  341 */         if (EnterpriseUtil.isIt360MSPEdition())
/*      */         {
/*  343 */           resourceids = EnterpriseUtil.filterResourceIds(request);
/*      */         }
/*      */         else
/*      */         {
/*  347 */           resourceids = ClientDBUtil.getResourceIdentity(owner);
/*      */         }
/*  349 */         resourceids.retainAll(widgetResIds);
/*      */         
/*      */ 
/*  352 */         if (resourceids.size() > 0)
/*      */         {
/*  354 */           graph = new AlarmGraphUtil(haid, monitor, resourceids);
/*  355 */           query = "select * from Alert where " + DependantMOUtil.getCondition("SOURCE", resourceids) + sevQueryInterrupter + groupNameCondition + "ORDER BY " + orderByColumn + " " + orderby;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  360 */         Vector modependants = widgetResIds;
/*  361 */         graph = new AlarmGraphUtil(haid, monitor, modependants);
/*      */         
/*  363 */         query = "select * from Alert where " + DependantMOUtil.getCondition("SOURCE", modependants) + sevQueryInterrupter + groupNameCondition + "ORDER BY " + orderByColumn + " " + orderby;
/*      */       }
/*  365 */       totalcountquery = query;
/*  366 */       datatable = getData(query);
/*  367 */       data = (Vector)datatable.get("viewData");
/*      */     }
/*  369 */     else if ((monitor == null) || (monitor.equals("-")) || (monitor.trim().length() == 0))
/*      */     {
/*  371 */       request.setAttribute("alertsfor", "true");
/*      */       
/*  373 */       Vector haidsvector = new Vector();
/*  374 */       haidsvector.add(haid);
/*  375 */       ManagedApplication.getChildIDs(haidsvector, haid);
/*  376 */       Vector ids = DependantMOUtil.getDependantResourceIDS(haidsvector);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  396 */       if (ids.size() > 0)
/*      */       {
/*      */ 
/*      */ 
/*  400 */         if (!Constants.isIt360)
/*      */         {
/*  402 */           ids.remove(haid);
/*      */         }
/*      */         
/*  405 */         String severityCondition = "";
/*  406 */         if ((viewId == null) || (viewId.equalsIgnoreCase("Alerts")))
/*      */         {
/*      */ 
/*  409 */           severityCondition = "";
/*      */         }
/*  411 */         else if (viewId.equalsIgnoreCase("Alerts.1"))
/*      */         {
/*      */ 
/*  414 */           severityCondition = "AND SEVERITY=1 ";
/*      */         }
/*  416 */         else if (viewId.equalsIgnoreCase("Alerts.14"))
/*      */         {
/*      */ 
/*  419 */           severityCondition = "AND SEVERITY=4 ";
/*      */         }
/*  421 */         else if (viewId.equalsIgnoreCase("Alerts.15"))
/*      */         {
/*      */ 
/*  424 */           severityCondition = "AND SEVERITY=5 ";
/*      */         }
/*  426 */         query = "select * from Alert " + resTypeFilter + " where " + DependantMOUtil.getCondition("SOURCE", ids) + timeFilter + sevQueryInterrupter + groupNameCondition + severityCondition + " ORDER BY " + orderByColumn + " " + orderby;
/*  427 */         totalcountquery = query;
/*  428 */         query = DBQueryUtil.addLimit(query, Integer.parseInt(startIndex), Integer.parseInt(viewLength), orderByColumn + " " + orderby);
/*  429 */         datatable = getData(query);
/*  430 */         data = (Vector)datatable.get("viewData");
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/*  436 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  439 */         request.setAttribute("alertsfor", "true");
/*  440 */         if (monitor.indexOf("$ComplexType") != -1)
/*      */         {
/*  442 */           monitor = getResourceTypesForComplexType(monitor.substring(0, monitor.lastIndexOf(",")));
/*      */         }
/*  444 */         String[] resTypes = new String[1];
/*  445 */         if (monitor.indexOf(",") != -1) {
/*  446 */           resTypes = monitor.split(",");
/*      */         }
/*  448 */         request.setAttribute("checkBoxList", monitor);
/*  449 */         Vector<String> monitors = new Vector();
/*      */         
/*  451 */         if (!privalegedUser)
/*      */         {
/*  453 */           monitors = getMonitorIdsforAlarm(resTypes);
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
/*  464 */         if (monitors.size() != 0)
/*      */         {
/*  466 */           if ((privalegedUser) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */           {
/*  468 */             String owner = request.getRemoteUser();
/*  469 */             Vector resourceids = new Vector();
/*  470 */             if (EnterpriseUtil.isIt360MSPEdition())
/*      */             {
/*  472 */               resourceids = ClientDBUtil.getMonitorTypesForOwner(owner, monitor, request);
/*      */             }
/*      */             else
/*      */             {
/*  476 */               resourceids = getMonitorIdsforAlarm(resTypes, owner);
/*      */             }
/*  478 */             if (resourceids.size() != 0) {
/*  479 */               graph = new AlarmGraphUtil(haid, monitor, resourceids);
/*  480 */               query = "select * from Alert " + resTypeFilter + " where " + DependantMOUtil.getCondition("SOURCE", resourceids) + timeFilter + sevQueryInterrupter + groupNameCondition + "ORDER BY " + orderByColumn + " " + orderby;
/*      */ 
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/*  490 */             Vector modependants = monitors;
/*  491 */             graph = new AlarmGraphUtil(haid, monitor, modependants);
/*      */             
/*      */ 
/*  494 */             query = "select * from Alert " + resTypeFilter + " where " + DependantMOUtil.getCondition("SOURCE", modependants) + timeFilter + sevQueryInterrupter + groupNameCondition + "ORDER BY " + orderByColumn + " " + orderby;
/*      */           }
/*  496 */           totalcountquery = query;
/*  497 */           query = DBQueryUtil.addLimit(query, Integer.parseInt(startIndex), Integer.parseInt(viewLength), orderByColumn + " " + orderby);
/*  498 */           datatable = getData(query);
/*  499 */           data = (Vector)datatable.get("viewData");
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  504 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  507 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  512 */     tempname = FormatUtil.getString("am.webclient.alert.alertfor.text", new String[] { val });
/*  513 */     request.setAttribute("viewId", viewId);
/*  514 */     request.setAttribute("displayName", displayName);
/*  515 */     request.setAttribute("tempname", tempname);
/*  516 */     ArrayList applications = new ArrayList();
/*  517 */     String owner = request.getRemoteUser();
/*  518 */     if ((privalegedUser) && (!EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/*  520 */       applications = AlarmUtil.getApplicationsForOwner(owner, request);
/*      */ 
/*      */ 
/*      */     }
/*  524 */     else if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/*  526 */       if (isBSGUser)
/*      */       {
/*  528 */         applications = AlarmUtil.getApplicationsForOwner(owner, request);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  533 */         applications = AlarmUtil.getApplicationsForAdmin(request);
/*      */       }
/*      */       
/*      */     }
/*      */     else {
/*  538 */       applications = AlarmUtil.getApplicationsForAdmin();
/*      */     }
/*      */     
/*  541 */     if (applications != null)
/*      */     {
/*  543 */       request.setAttribute("applications", applications);
/*  544 */       amform.setApplications(applications);
/*      */     }
/*  546 */     if (graph != null)
/*      */     {
/*  548 */       request.setAttribute("graph", graph);
/*      */     }
/*      */     
/*  551 */     ArrayList<Properties> customFieldValues = new ArrayList();
/*  552 */     customFieldValues = MyFields.getCustomFieldforAlarms();
/*  553 */     request.setAttribute("customFieldAlarm", customFieldValues);
/*      */     
/*  555 */     ArrayList<HashMap<String, String>> resourcetypes = DBUtil.getResourceTypesforAlarm();
/*  556 */     request.setAttribute("alarmResTypes", resourcetypes);
/*  557 */     ActionMessages actionMsg = new ActionMessages();
/*  558 */     if ((data == null) || (data.isEmpty()))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  565 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  566 */       Object graph1 = new AlarmGraphUtil("NoData");
/*  567 */       request.setAttribute("graph", graph1);
/*      */       
/*  569 */       String modifyUrl = "/fault/invokeAlarmCV.do?viewId=" + viewId + "&displayName=" + displayName + "&actionToPerform=modify";
/*  570 */       request.setAttribute("actionURL", modifyUrl);
/*  571 */       request.setAttribute("msgForUrl", NmsUtil.GetString("webclient.common.messagepage.modifycv"));
/*  572 */       return mapping.findForward("messagePage");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  577 */     headerList = getDefaultColumns();
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
/*  589 */       total = getTotalRows(totalcountquery);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  593 */       e.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  602 */     if (orderByColumn != null)
/*      */     {
/*  604 */       if (orderByColumn.equals("MMESSAGE"))
/*      */       {
/*  606 */         orderByColumn = "message";
/*      */       }
/*  608 */       request.setAttribute("orderByColumn", orderByColumn);
/*      */     }
/*      */     
/*  611 */     request.setAttribute("PAGENUMBER", pageNumber);
/*  612 */     AlarmUtil.removeNMSAlerts();
/*  613 */     String pageTitle = NmsUtil.GetString("webclient.fault.alarm.pagetitle");
/*  614 */     request.setAttribute("pageTitle", pageTitle);
/*      */     
/*  616 */     String isAssign = WebClientUtil.getClientParameter(userName, "ASSIGN_ALERT");
/*  617 */     request.setAttribute("isAssign", isAssign);
/*  618 */     request.setAttribute("RECORDS", new Long(total));
/*  619 */     request.setAttribute("viewLength", new Integer(viewLength));
/*  620 */     request.setAttribute("startIndex", startIndex);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  629 */     request.setAttribute("permittedToViewAlert", Boolean.TRUE);
/*  630 */     request.setAttribute("permittedToDeleteAlert", Boolean.TRUE);
/*  631 */     request.setAttribute("permittedToClearAlert", Boolean.TRUE);
/*  632 */     request.setAttribute("permittedToPickup", Boolean.TRUE);
/*      */     
/*  634 */     request.setAttribute("headerList", headerList);
/*      */     
/*  636 */     request.setAttribute("viewData", data);
/*  637 */     request.setAttribute("action", "AMAlarmView.do");
/*  638 */     request.setAttribute("PAGE_LENGTHS", list);
/*  639 */     if (haid != null) {
/*  640 */       request.setAttribute("haid", haid);
/*      */     }
/*  642 */     if (monitor != null) {
/*  643 */       request.setAttribute("monitor", monitor);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  648 */     request.setAttribute("CUSTOMIZE_COLUMNS_ACTION", "AlarmColumnCustomizer.do");
/*      */     
/*  650 */     return mapping.findForward("alarmView");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Vector<String> getMonitorIdsforAlarm(String[] resTypes, String owner)
/*      */   {
/*  658 */     Vector<String> monitors = new Vector();
/*  659 */     Vector<String> monTypes = new Vector();
/*  660 */     HashMap<String, HashMap<String, String>> customFieldFilter = new HashMap();
/*  661 */     String operatorCondition = "";
/*  662 */     boolean isUserResourceEnabled = false;
/*  663 */     if (owner != null) {
/*  664 */       if (Constants.isUserResourceEnabled()) {
/*  665 */         isUserResourceEnabled = true;
/*      */       } else {
/*  667 */         operatorCondition = " and " + ManagedApplication.getCondition("RESOURCEID", ClientDBUtil.getResourceIdentity(owner));
/*      */       }
/*      */     }
/*      */     
/*  671 */     ResultSet rs = null;
/*  672 */     String query = null;
/*      */     try
/*      */     {
/*  675 */       for (String resourceType : resTypes) {
/*  676 */         if ((resourceType.indexOf("[") != -1) || (resourceType.indexOf("]") != -1)) {
/*  677 */           if (resourceType.indexOf("[") != -1) {
/*  678 */             resourceType = resourceType.substring(1, resourceType.length());
/*      */           }
/*      */           else {
/*  681 */             resourceType = resourceType.substring(0, resourceType.length() - 2);
/*      */           }
/*      */         }
/*  684 */         if (resourceType.equals("Trap"))
/*      */         {
/*  686 */           if (isUserResourceEnabled)
/*      */           {
/*  688 */             query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + owner + " and AM_ManagedObject.TYPE='Trap'";
/*      */           } else {
/*  690 */             query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.TYPE='Trap'" + operatorCondition;
/*      */           }
/*      */           
/*  693 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  694 */           while (rs.next()) {
/*  695 */             monitors.add(rs.getString("RESOURCEID"));
/*      */           }
/*      */         }
/*  698 */         if (resourceType.equals("JMXNotification"))
/*      */         {
/*  700 */           if (isUserResourceEnabled) {
/*  701 */             query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_USERRESOURCESTABLE where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + owner + " and AM_ManagedObject.TYPE='JMXNotification'";
/*      */           } else {
/*  703 */             query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.TYPE='JMXNotification'" + operatorCondition;
/*      */           }
/*      */           
/*  706 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  707 */           while (rs.next()) {
/*  708 */             monitors.add(rs.getString("RESOURCEID"));
/*      */           }
/*      */         }
/*  711 */         if (resourceType.indexOf("$") != -1) {
/*  712 */           String tableName = "AM_MYFIELDS_SYSTEMDATA";
/*      */           
/*  714 */           String aliasname = resourceType.substring(0, resourceType.indexOf("$"));
/*  715 */           String valueid = resourceType.substring(resourceType.indexOf("$") + 1);
/*      */           
/*      */ 
/*  718 */           if (aliasname.indexOf("USERDATA_") != -1)
/*      */           {
/*  720 */             tableName = "AM_MYFIELDS_USERDATA";
/*      */           }
/*  722 */           else if (aliasname.equalsIgnoreCase("VALUEID"))
/*      */           {
/*  724 */             tableName = "AM_MYFIELDS_LABELDATA";
/*      */           }
/*      */           
/*      */ 
/*  728 */           if (customFieldFilter.containsKey(tableName)) {
/*  729 */             HashMap<String, String> userdatavalues = (HashMap)customFieldFilter.get(tableName);
/*  730 */             if (userdatavalues.containsKey(aliasname)) {
/*  731 */               String values = (String)userdatavalues.get(aliasname);
/*  732 */               userdatavalues.put(aliasname, values + valueid + ",");
/*      */             } else {
/*  734 */               userdatavalues.put(aliasname, valueid + ",");
/*      */             }
/*      */           } else {
/*  737 */             HashMap<String, String> addtableName = new HashMap();
/*  738 */             addtableName.put(aliasname, valueid + ",");
/*  739 */             customFieldFilter.put(tableName, addtableName);
/*      */           }
/*      */           
/*      */         }
/*  743 */         else if ((resourceType.indexOf("OpManager-") != -1) || (resourceType.indexOf("OpStor-") != -1))
/*      */         {
/*      */ 
/*  746 */           String resTypeQuery = "select RESOURCETYPE from AM_ManagedResourceType where SUBGROUP = '" + resourceType + "'";
/*  747 */           rs = AMConnectionPool.executeQueryStmt(resTypeQuery);
/*  748 */           while (rs.next())
/*      */           {
/*  750 */             monTypes.add(rs.getString(1));
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*  755 */         else if ("File System Monitor".equalsIgnoreCase(resourceType)) {
/*  756 */           monTypes.add("directory");
/*  757 */           monTypes.add("file");
/*      */         } else {
/*  759 */           monTypes.add(resourceType.trim());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  764 */       StringBuilder datatablesubQuery = new StringBuilder();
/*  765 */       Vector<String> customFieldParentIds = new Vector();
/*  766 */       StringBuilder customFieldQuery = new StringBuilder();
/*  767 */       Iterator<String> it = customFieldFilter.keySet().iterator();
/*  768 */       while (it.hasNext()) {
/*  769 */         String tableName = (String)it.next();
/*  770 */         datatablesubQuery.append("select RESOURCEID from " + tableName + " union");
/*  771 */         customFieldQuery.append(" select RESOURCEID from " + tableName + " where (");
/*  772 */         HashMap<String, String> columnValues = (HashMap)customFieldFilter.get(tableName);
/*  773 */         Iterator<String> columns = columnValues.keySet().iterator();
/*  774 */         while (columns.hasNext()) {
/*  775 */           String columnName = (String)columns.next();
/*  776 */           String valueid = (String)columnValues.get(columnName);
/*  777 */           customFieldQuery.append(columnName + " in (" + valueid.substring(0, valueid.length() - 1) + ") or ");
/*      */         }
/*  779 */         customFieldQuery.replace(customFieldQuery.length() - 4, customFieldQuery.length(), "");
/*  780 */         customFieldQuery.append(") " + operatorCondition + " union");
/*      */       }
/*      */       
/*      */ 
/*  784 */       if (customFieldQuery.length() > 0)
/*      */       {
/*  786 */         ResultSet customFieldRs = null;
/*      */         try
/*      */         {
/*  789 */           customFieldRs = AMConnectionPool.executeQueryStmt(customFieldQuery.substring(0, customFieldQuery.length() - 6));
/*  790 */           while (customFieldRs.next())
/*      */           {
/*  792 */             String resid = customFieldRs.getString("RESOURCEID");
/*  793 */             monitors.add(resid);
/*  794 */             if (!customFieldParentIds.contains(resid))
/*      */             {
/*  796 */               customFieldParentIds.add(resid);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  802 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  810 */       for (int i = 0; i < customFieldParentIds.size(); i++)
/*      */       {
/*      */ 
/*  813 */         MyFields.getAllChildforMyFields(monitors, String.valueOf(customFieldParentIds.get(i)), datatablesubQuery.substring(0, datatablesubQuery.length() - 6));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  822 */       ArrayList<String> eumIds = MyFields.getEumChildIdsFromParentMonIds(false, new ArrayList(monitors));
/*  823 */       if (!eumIds.isEmpty())
/*      */       {
/*  825 */         monitors.addAll(eumIds);
/*      */       }
/*      */       
/*  828 */       if ((owner != null) && (!monTypes.isEmpty()))
/*      */       {
/*  830 */         if (isUserResourceEnabled) {
/*  831 */           query = "SELECT AM_PARENTCHILDMAPPER.CHILDID CHILDID FROM AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedObject WHERE AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID AND AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=" + owner + " AND " + ManagedApplication.getStringCondition("AM_ManagedObject.TYPE", monTypes) + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*      */         } else {
/*  833 */           query = "SELECT AM_PARENTCHILDMAPPER.CHILDID CHILDID FROM AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_ManagedObject WHERE AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID AND AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "' AND " + ManagedApplication.getStringCondition("AM_ManagedObject.TYPE", monTypes) + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID";
/*      */         }
/*      */         
/*  836 */         rs = AMConnectionPool.executeQueryStmt(query); }
/*  837 */       while (rs.next())
/*      */       {
/*  839 */         monitors.add(rs.getString("CHILDID")); continue;
/*      */         
/*      */ 
/*  842 */         if (!monTypes.isEmpty())
/*      */         {
/*  844 */           rs = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and " + ManagedApplication.getStringCondition("AM_ManagedResourceType.RESOURCETYPE", monTypes) + "");
/*  845 */           while (rs.next())
/*      */           {
/*  847 */             monitors.add(rs.getString("RESOURCEID"));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  853 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  856 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*  859 */     return monitors;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Vector<String> getMonitorIdsforAlarm(String[] resTypes)
/*      */   {
/*  866 */     return getMonitorIdsforAlarm(resTypes, null);
/*      */   }
/*      */   
/*      */   public Hashtable getData(String query)
/*      */   {
/*  871 */     ResultSet rs = null;
/*  872 */     Hashtable h = new Hashtable();
/*  873 */     if (query.trim().equals(""))
/*      */     {
/*  875 */       return h;
/*      */     }
/*  877 */     Vector data = new Vector();
/*      */     try
/*      */     {
/*  880 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  881 */       int count = 0;
/*  882 */       while (rs.next())
/*      */       {
/*  884 */         count++;
/*  885 */         Properties p = new Properties();
/*  886 */         p.setProperty("modTime", DateFormat.getDateTimeInstance(2, 2).format(new Date(rs.getLong("MODTIME"))));
/*  887 */         p.setProperty("modTimeMillis", "" + rs.getLong("MODTIME"));
/*  888 */         p.setProperty("entity", rs.getString("ENTITY"));
/*  889 */         p.setProperty("severity", getSeverityString(rs.getInt("SEVERITY")));
/*  890 */         p.setProperty("ownerName", rs.getString("OWNERNAME"));
/*  891 */         p.setProperty("message", rs.getString("MMESSAGE"));
/*  892 */         p.setProperty("category", rs.getString("CATEGORY"));
/*  893 */         p.setProperty("source", rs.getString("SOURCE"));
/*  894 */         p.setProperty("imgsrc", getImageSrc(rs.getInt("SEVERITY")));
/*  895 */         if ((rs.getString("WHO") == null) || (rs.getString("WHO").equalsIgnoreCase("NULL"))) {
/*  896 */           p.setProperty("who", "");
/*      */         }
/*      */         else {
/*  899 */           p.setProperty("who", rs.getString("WHO"));
/*      */         }
/*  901 */         data.add(p);
/*      */       }
/*  903 */       h.put("viewData", data);
/*  904 */       h.put("count", String.valueOf(count));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  908 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  911 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  913 */     return h;
/*      */   }
/*      */   
/*      */   public int getTotalRows(String query)
/*      */   {
/*  918 */     int count = 0;
/*  919 */     ResultSet rs = null;
/*  920 */     if (query.toUpperCase().contains("ORDER BY"))
/*      */     {
/*  922 */       query = query.substring(0, query.toUpperCase().indexOf("ORDER BY"));
/*      */     }
/*  924 */     query = "select count(*) as count from ( " + query + "  ) as countView";
/*      */     try
/*      */     {
/*  927 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  928 */       rs.first();
/*  929 */       if (rs.isFirst()) {
/*  930 */         count = rs.getInt(1);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  935 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  938 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  940 */     return count;
/*      */   }
/*      */   
/*      */   public String getImageSrc(int severity)
/*      */   {
/*  945 */     if (severity == 1) {
/*  946 */       return "/images/icon_health_critical.gif";
/*      */     }
/*  948 */     if (severity == 4) {
/*  949 */       return "/images/icon_health_warning.gif";
/*      */     }
/*  951 */     if (severity == 5) {
/*  952 */       return "/images/icon_health_clear.gif";
/*      */     }
/*      */     
/*  955 */     return "/images/icon_health_unknown.gif";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getSeverityString(int severity)
/*      */   {
/*  962 */     if (severity == 1) {
/*  963 */       return "Critical";
/*      */     }
/*  965 */     if (severity == 4) {
/*  966 */       return "Warning";
/*      */     }
/*  968 */     if (severity == 5) {
/*  969 */       return "Clear";
/*      */     }
/*      */     
/*  972 */     return "Unknown";
/*      */   }
/*      */   
/*      */ 
/*      */   private List<String> getWidgetResourceIds(int widgetId)
/*      */   {
/*  978 */     List<Integer> availabilityWidgetTypes = new ArrayList();
/*  979 */     availabilityWidgetTypes.add(new Integer("505"));
/*  980 */     availabilityWidgetTypes.add(new Integer(107));
/*  981 */     List<Integer> supportedWidgetTypes = new ArrayList();
/*  982 */     supportedWidgetTypes.addAll(availabilityWidgetTypes);
/*  983 */     List<String> toReturn = new ArrayList();
/*  984 */     if (widgetId > 0)
/*      */     {
/*  986 */       Map<String, String> widgetProps = getWidgetProps(widgetId);
/*      */       
/*  988 */       Integer widgetType = Integer.valueOf(widgetProps.get("WIDGETTYPE") == null ? 0 : Integer.parseInt((String)widgetProps.get("WIDGETTYPE")));
/*      */       
/*  990 */       if ((widgetType.intValue() > 0) && (supportedWidgetTypes.contains(widgetType)) && (availabilityWidgetTypes.contains(widgetType)))
/*      */       {
/*  992 */         toReturn = getResIdsForAvailabilityWidgets(widgetProps);
/*      */       }
/*      */     }
/*  995 */     return toReturn;
/*      */   }
/*      */   
/*      */   private List<String> getResIdsForAvailabilityWidgets(Map<String, String> widgetProps) {
/*  999 */     List<String> toReturn = new ArrayList();
/* 1000 */     Integer widgetType = Integer.valueOf(widgetProps.get("WIDGETTYPE") == null ? 0 : Integer.parseInt((String)widgetProps.get("WIDGETTYPE")));
/* 1001 */     int widgetId = widgetProps.get("WIDGETID") == null ? 0 : Integer.parseInt((String)widgetProps.get("WIDGETID"));
/* 1002 */     if (Integer.parseInt("505") == widgetType.intValue())
/*      */     {
/* 1004 */       toReturn = getResIdsForExternalProdWidget(widgetId);
/*      */     }
/* 1006 */     else if (widgetType.intValue() == 107)
/*      */     {
/* 1008 */       String resType = (String)widgetProps.get("RESOURCETYPE");
/* 1009 */       if ((resType != null) && (!resType.trim().equals("")))
/*      */       {
/*      */ 
/* 1012 */         String selectMonitortype = (String)widgetProps.get("SELECTMONITORTYPE");
/* 1013 */         if ("CUSTOM".equalsIgnoreCase(selectMonitortype))
/*      */         {
/* 1015 */           toReturn = getMyPageWidgetMonitors((String)widgetProps.get("WIDGETID"));
/*      */         }
/* 1017 */         else if ("CUSTOM FIELDS".equalsIgnoreCase(selectMonitortype))
/*      */         {
/* 1019 */           toReturn = getResourceIdsForCustomFields((String)widgetProps.get("WIDGETID"));
/*      */         }
/*      */         else
/*      */         {
/* 1023 */           String condition = getResourceTypesForComplexTypeCondition(resType, "RESOURCETYPE");
/* 1024 */           toReturn = getResourceIdsForcodn(condition);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1029 */     return toReturn;
/*      */   }
/*      */   
/*      */   private List<String> getResourceIdsForcodn(String condition)
/*      */   {
/* 1034 */     List<String> resourceIds = new ArrayList();
/* 1035 */     if ((condition != null) && (!condition.trim().equals("")))
/*      */     {
/* 1037 */       ResultSet rs = null;
/* 1038 */       String query = "select RESOURCEID from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPe=AM_ManagedResourceType.RESOURCETYPE where ";
/* 1039 */       query = query + " " + condition;
/*      */       try {
/* 1041 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/* 1043 */         while (rs.next())
/*      */         {
/* 1045 */           resourceIds.add(rs.getString("RESOURCEID"));
/*      */         }
/*      */       } catch (SQLException e) {
/* 1048 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1052 */         if (rs != null)
/*      */         {
/* 1054 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1059 */     return resourceIds;
/*      */   }
/*      */   
/*      */   private List<String> getMyPageWidgetMonitors(String widgetid) {
/* 1063 */     ResultSet rs = null;
/* 1064 */     ArrayList<String> resourceIds = new ArrayList();
/* 1065 */     String query = "select RESOURCEID from AM_MYPAGE_WIDGET_MONITORS where WIDGETID=" + widgetid;
/*      */     try {
/* 1067 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 1069 */       while (rs.next())
/*      */       {
/* 1071 */         resourceIds.add(rs.getString("RESOURCEID"));
/*      */       }
/*      */     } catch (SQLException e) {
/* 1074 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1078 */       if (rs != null)
/*      */       {
/* 1080 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1084 */     return resourceIds;
/*      */   }
/*      */   
/*      */   private List<String> getResIdsForExternalProdWidget(int widgetId)
/*      */   {
/* 1089 */     List<String> toReturn = new ArrayList();
/* 1090 */     ArrayList<String> associatedTypes = getSelectedTypesForExtProductWidget(Integer.toString(widgetId));
/* 1091 */     String associatedTypeVals = "";
/* 1092 */     String criteria = "";
/*      */     
/* 1094 */     String resourceidsQuery = "select RESOURCEID from AM_ManagedObject inner join ExternalDeviceDetails   on AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME inner join AM_AssociatedExtDevices  on AM_AssociatedExtDevices.RESID=AM_ManagedObject.RESOURCEID and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE";
/* 1095 */     if (associatedTypes.size() != 0)
/*      */     {
/* 1097 */       associatedTypeVals = " IN (";
/*      */       
/* 1099 */       for (int i = 0; i < associatedTypes.size(); i++)
/*      */       {
/* 1101 */         if (i != associatedTypes.size() - 1)
/*      */         {
/* 1103 */           associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "',";
/*      */         }
/*      */         else
/*      */         {
/* 1107 */           associatedTypeVals = associatedTypeVals + "'" + (String)associatedTypes.get(i) + "') ";
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1113 */     if (associatedTypeVals.equalsIgnoreCase(""))
/*      */     {
/* 1115 */       criteria = " where ExternalDeviceDetails.CATEGORY like 'OpManager-%' and ExternalDeviceDetails.CATEGORY not like 'OpManager-Interface%' ";
/*      */     }
/*      */     else
/*      */     {
/* 1119 */       criteria = " where ExternalDeviceDetails.CATEGORY " + associatedTypeVals + " ";
/*      */     }
/* 1121 */     resourceidsQuery = resourceidsQuery + criteria;
/* 1122 */     ResultSet rs = null;
/*      */     try {
/* 1124 */       rs = AMConnectionPool.executeQueryStmt(resourceidsQuery);
/*      */       
/* 1126 */       while (rs.next())
/*      */       {
/* 1128 */         toReturn.add(rs.getString("RESOURCEID"));
/*      */       }
/*      */     } catch (SQLException e) {
/* 1131 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1135 */       if (rs != null)
/*      */       {
/* 1137 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1141 */     return toReturn;
/*      */   }
/*      */   
/*      */   private Map<String, String> getWidgetProps(int widgetId) {
/* 1145 */     Map<String, String> toReturn = new HashMap();
/*      */     
/* 1147 */     String query = "select WIDGETID,WIDGETTYPE,RESOURCETYPE,SELECTMONITORTYPE from AM_MYPAGE_WIDGETS where WIDGETID='" + widgetId + "'";
/* 1148 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1151 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1152 */       if (rs.next())
/*      */       {
/* 1154 */         toReturn.put("WIDGETTYPE", rs.getString("WIDGETTYPE"));
/* 1155 */         toReturn.put("WIDGETID", rs.getString("WIDGETID"));
/* 1156 */         toReturn.put("RESOURCETYPE", rs.getString("RESOURCETYPE"));
/* 1157 */         toReturn.put("SELECTMONITORTYPE", rs.getString("SELECTMONITORTYPE"));
/*      */       }
/*      */     } catch (SQLException e) {
/* 1160 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1164 */       if (rs != null)
/*      */       {
/* 1166 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/* 1169 */     return toReturn;
/*      */   }
/*      */   
/*      */   private ArrayList<String> getSelectedTypesForExtProductWidget(String widgetid) {
/* 1173 */     ResultSet rs = null;
/* 1174 */     ArrayList<String> associatedTypes = new ArrayList();
/* 1175 */     String query = "select ASSOCIATEDTYPES from AM_WIDGETS_ASSOCIATEDTYPES where WIDGETID=" + widgetid;
/*      */     try {
/* 1177 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 1179 */       while (rs.next())
/*      */       {
/* 1181 */         associatedTypes.add(rs.getString("ASSOCIATEDTYPES"));
/*      */       }
/*      */     } catch (SQLException e) {
/* 1184 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1188 */       if (rs != null)
/*      */       {
/* 1190 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1194 */     return associatedTypes;
/*      */   }
/*      */   
/*      */   public String getResourceTypesForComplexType(String resourceType)
/*      */   {
/* 1199 */     String resType = null;
/*      */     try
/*      */     {
/* 1202 */       resType = getResourceTypesForComplexTypeCondition(resourceType, "SUBGROUP").trim();
/* 1203 */       AMLog.debug("AMAlarmViewAction.getResourceTypesForComplexType1 : " + resType);
/* 1204 */       if (resourceType.equalsIgnoreCase("$ComplexType_All"))
/*      */       {
/* 1206 */         resType = resType + "," + getResourceTypesForComplexTypeCondition("$ComplexType_StorageDevices", "SUBGROUP").trim();
/* 1207 */         resType = resType + "," + getResourceTypesForComplexTypeCondition("$ComplexType_NetworkDevices", "SUBGROUP").trim();
/*      */       }
/* 1209 */       resType = resType.replace("in (", "").replace("IN (", "").replace(")", "").replace("'", "").replace("RESOURCETYPE", "").replace("RESOURCEGROUP", "").replace("SUBGROUP ", "").replace("SUBGROUP", "").trim();
/* 1210 */       AMLog.debug("AMAlarmViewAction.getResourceTypesForComplexType2 : " + resType);
/* 1211 */       if (resType.length() == 0)
/*      */       {
/* 1213 */         resType = "-";
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1218 */       localException.printStackTrace();
/*      */     }
/* 1220 */     AMLog.debug("AMAlarmViewAction.getResourceTypesForComplexType2 : " + resType);
/* 1221 */     return resType;
/*      */   }
/*      */   
/*      */ 
/*      */   public String getResourceTypesForComplexTypeCondition(String resourceType, String columnName)
/*      */   {
/* 1227 */     String condition = " RESOURCETYPE in ('" + resourceType + "')";
/*      */     try
/*      */     {
/* 1230 */       if (resourceType.equals("$ComplexType_Windows"))
/*      */       {
/* 1232 */         condition = " RESOURCEGROUP IN ('SYS') and SUBGROUP in ('Windows') ";
/*      */       }
/* 1234 */       else if (resourceType.equals("$ComplexType_Servers"))
/*      */       {
/* 1236 */         condition = " RESOURCETYPE in " + Constants.serverTypes;
/*      */       }
/* 1238 */       else if (resourceType.equals("$ComplexType_AllSers"))
/*      */       {
/* 1240 */         condition = " RESOURCETYPE in " + Constants.allServerTypes;
/*      */       }
/* 1242 */       else if (resourceType.equals("$ComplexType_All"))
/*      */       {
/* 1244 */         condition = " RESOURCETYPE in " + Constants.resourceTypes;
/*      */       }
/* 1246 */       else if (resourceType.equals("$ComplexType_NetworkDevices"))
/*      */       {
/* 1248 */         condition = columnName + getNWDSubGroups(columnName);
/*      */       }
/* 1250 */       else if (resourceType.equals("$ComplexType_StorageDevices"))
/*      */       {
/* 1252 */         condition = columnName + getSANSubGroups(columnName);
/*      */       }
/* 1254 */       else if (resourceType.equals("$ComplexType_AllApps"))
/*      */       {
/* 1256 */         if (Constants.getAppsSubGroups() == null)
/*      */         {
/* 1258 */           condition = " RESOURCETYPE in ('')";
/*      */         }
/*      */         else
/*      */         {
/* 1262 */           condition = " RESOURCETYPE " + Constants.getAppsSubGroups();
/*      */         }
/*      */       }
/* 1265 */       else if (resourceType.equals("$ComplexType_VC_HAI"))
/*      */       {
/* 1267 */         condition = " RESOURCETYPE in ('HAI')";
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1272 */       ex.printStackTrace();
/*      */     }
/* 1274 */     return condition;
/*      */   }
/*      */   
/*      */   private String getNWDSubGroups(String columnName)
/*      */   {
/* 1279 */     ResultSet subGrpRs = null;
/* 1280 */     ArrayList<String> grpList = new ArrayList();
/* 1281 */     String subGroup = new String();
/*      */     try
/*      */     {
/* 1284 */       String excludeInterface = " and SUBGROUP <> 'OpManager-Interface' and SUBGROUP like 'OpManager-%'";
/* 1285 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT " + columnName + " from AM_ManagedResourceType where RESOURCEGROUP='NWD' " + excludeInterface);
/* 1286 */       while (subGrpRs.next())
/*      */       {
/* 1288 */         grpList.add(subGrpRs.getString(1));
/*      */       }
/* 1290 */       if (grpList.size() > 0)
/*      */       {
/* 1292 */         subGroup = " IN (";
/* 1293 */         for (int i = 0; i < grpList.size(); i++)
/*      */         {
/* 1295 */           if (i != grpList.size() - 1)
/*      */           {
/* 1297 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "',";
/*      */           }
/*      */           else
/*      */           {
/* 1301 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "')";
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1308 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1312 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */     
/* 1315 */     return subGroup;
/*      */   }
/*      */   
/*      */   private String getSANSubGroups(String columnName) {
/* 1319 */     ResultSet subGrpRs = null;
/* 1320 */     ArrayList<String> grpList = new ArrayList();
/* 1321 */     String subGroup = new String();
/*      */     try
/*      */     {
/* 1324 */       subGrpRs = AMConnectionPool.executeQueryStmt("select DISTINCT " + columnName + " from AM_ManagedResourceType where RESOURCEGROUP='SAN' ");
/* 1325 */       while (subGrpRs.next())
/*      */       {
/* 1327 */         grpList.add(subGrpRs.getString(1));
/*      */       }
/* 1329 */       if (grpList.size() > 0)
/*      */       {
/* 1331 */         subGroup = " IN (";
/* 1332 */         for (int i = 0; i < grpList.size(); i++)
/*      */         {
/* 1334 */           if (i != grpList.size() - 1)
/*      */           {
/* 1336 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "',";
/*      */           }
/*      */           else
/*      */           {
/* 1340 */             subGroup = subGroup + "'" + (String)grpList.get(i) + "')";
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1347 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1351 */       AMConnectionPool.closeStatement(subGrpRs);
/*      */     }
/*      */     
/* 1354 */     return subGroup;
/*      */   }
/*      */   
/* 1357 */   private Hashtable getAlarmCount(String sessionIsCor, HttpServletRequest request, boolean isBSGUser, String loginName) { String alertCountQueryPart1 = "select severity,count(*) from Alert";
/* 1358 */     ArrayList monitorLevelResTypeList = Constants.getAllMonitorLevelResourceTypes();
/* 1359 */     String monitorLevelResType = monitorLevelResTypeList.toString().replace("[", "");
/* 1360 */     monitorLevelResType = monitorLevelResType.replace("]", "");
/* 1361 */     String resTypeFilter = " inner join AM_ManagedObject amo on amo.RESOURCEID=Alert.source and amo.TYPE in (" + monitorLevelResType + ") and entity not like '%_721'";
/* 1362 */     String excludeOPMAlerts = " and amo.TYPE not like 'OpManager-Interface%'";
/* 1363 */     String groupNameCondition = " WHERE (GROUPNAME='AppManager_Component') ";
/* 1364 */     if ((sessionIsCor == null) || ((sessionIsCor != null) && (sessionIsCor.equals("true"))))
/*      */     {
/* 1366 */       resTypeFilter = " inner join AM_ManagedObject amo on ALERT.SOURCE=amo.RESOURCEID ";
/* 1367 */       groupNameCondition = " AND (GROUPNAME='AppManager') ";
/*      */     }
/* 1369 */     String selectedMonitorCondition = "";
/* 1370 */     if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition()))
/*      */     {
/* 1372 */       Vector resourceids = new Vector();
/* 1373 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*      */ 
/* 1376 */         if (isBSGUser)
/*      */         {
/* 1378 */           resourceids = CustomerManagementAPI.filterUserBasedResourceIds(loginName, new Vector());
/*      */         }
/*      */         else
/*      */         {
/* 1382 */           resourceids = CustomerManagementAPI.filterResourceIds(request, false);
/*      */         }
/* 1384 */         Properties siteProp = CustomerManagementAPI.getSiteProp(request);
/* 1385 */         if ((siteProp == null) || (siteProp.size() == 0))
/*      */         {
/*      */ 
/* 1388 */           Vector bsgVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/* 1389 */           resourceids.addAll(bsgVector);
/*      */         }
/* 1391 */         ArrayList selectedMonitors = new ArrayList(resourceids);
/* 1392 */         selectedMonitors.add(Integer.valueOf(-1));
/* 1393 */         AMLog.debug("selectedMonitors:" + selectedMonitors);
/* 1394 */         String selectedMon = selectedMonitors.toString().replace("[", "(");
/* 1395 */         selectedMon = selectedMon.replace("]", ")");
/* 1396 */         selectedMonitorCondition = " and source in " + selectedMon + " ";
/*      */       }
/*      */       else
/*      */       {
/* 1400 */         resourceids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 1401 */         String selectedMon = null;
/*      */         try
/*      */         {
/* 1404 */           selectedMon = resourceids.toString();
/*      */           
/* 1406 */           selectedMon = selectedMon.substring(1, selectedMon.length());
/* 1407 */           selectedMon = "(" + selectedMon;
/* 1408 */           selectedMon = selectedMon.substring(0, selectedMon.length() - 1);
/* 1409 */           selectedMon = selectedMon + ")";
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1413 */           AMLog.fatal("#--# Exception occured in getting alarm count in getAlarmCount method: " + e.getMessage());
/*      */         }
/* 1415 */         selectedMonitorCondition = " and source in " + selectedMon + " ";
/*      */       }
/*      */     }
/*      */     
/* 1419 */     String groupByString = " group by severity";
/*      */     
/* 1421 */     String alertCountQuery = alertCountQueryPart1 + resTypeFilter + excludeOPMAlerts + groupNameCondition + selectedMonitorCondition + groupByString;
/* 1422 */     return DBUtil.executeQueryToGetAlertCountDetails(alertCountQuery);
/*      */   }
/*      */   
/*      */   public static ArrayList executeTrapView()
/*      */   {
/* 1427 */     String hostname = "";
/* 1428 */     String time = "";
/* 1429 */     String message = "";
/* 1430 */     String entity = "";
/* 1431 */     String who = "";
/* 1432 */     ArrayList list = new ArrayList();
/* 1433 */     String query = "select SOURCE,MODTIME,MMESSAGE,ENTITY,WHO from Alert where GROUPNAME='AppManager_Trap'";
/* 1434 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1437 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1438 */       while (rs.next())
/*      */       {
/* 1440 */         Properties p = new Properties();
/* 1441 */         time = FormatUtil.formatDT(rs.getLong("MODTIME") + "");
/* 1442 */         message = rs.getString("MMESSAGE");
/* 1443 */         entity = rs.getString("ENTITY");
/* 1444 */         who = rs.getString("WHO");
/* 1445 */         hostname = entity.substring(0, entity.indexOf('_'));
/* 1446 */         p.setProperty("hostname", hostname);
/* 1447 */         p.setProperty("time", time);
/* 1448 */         p.setProperty("message", message);
/* 1449 */         p.setProperty("entity", entity);
/* 1450 */         p.setProperty("who", who);
/* 1451 */         list.add(p);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1456 */       AMLog.fatal("#--# Exception occured in getting tight in executeTrapView method: " + e.getMessage());
/*      */     }
/*      */     finally {
/* 1459 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/* 1461 */     return list;
/*      */   }
/*      */   
/*      */   private TableColumn[] getDefaultColumns()
/*      */   {
/* 1466 */     AMLog.debug("Replacing with default headers");
/*      */     try
/*      */     {
/* 1469 */       return new TableColumn[] { new TableColumn("severity", "Severity", 100), new TableColumn("message", "Message", 100), new TableColumn("source", "Source", 100), new TableColumn("category", "Category", 100), new TableColumn("who", "Acknowledge", 100), new TableColumn("modTime", "Date / Time", 100) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1481 */       e.printStackTrace(); }
/* 1482 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private List<String> getResourceIdsForCustomFields(String widgetid)
/*      */   {
/* 1489 */     ResultSet rs = null;
/* 1490 */     List<String> resourceIds = new ArrayList();
/* 1491 */     String query = "select fmet.ALIASNAME as CUSTOMFIELD,wc.VALUE as CUSTOMID from AM_MYPAGE_WIDGET_CUSTOMFIELD as wc, AM_MYFIELDS_METADATA as fmet where  wc.FIELDID=fmet.FIELDID and wc.WIDGETID=" + widgetid;
/* 1492 */     String customField = "";
/* 1493 */     String customID = "";
/*      */     try {
/* 1495 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1496 */       if (rs.next())
/*      */       {
/* 1498 */         customField = rs.getString("CUSTOMFIELD");
/* 1499 */         customID = rs.getString("CUSTOMID");
/* 1500 */         resourceIds = MyFields.customFieldResourceIDs(customField + "$" + customID, "All");
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1505 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1509 */       if (rs != null)
/*      */       {
/* 1511 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/* 1515 */     return resourceIds;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\AMAlarmViewAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */