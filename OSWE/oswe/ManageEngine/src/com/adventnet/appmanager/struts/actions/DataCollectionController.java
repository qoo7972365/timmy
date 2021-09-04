/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMServerUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.fault.ExecuteSDPTicketAction;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.AgentUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ManagedAPICallUtil;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.UserUtil;
/*      */ import com.google.gson.Gson;
/*      */ import com.google.gson.reflect.TypeToken;
/*      */ import com.manageengine.apminsight.apm.mo.ApmInsightMOManager;
/*      */ import com.manageengine.apminsight.common.response.ResponseCode;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.json.JSONArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DataCollectionController
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward unManageMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   70 */     boolean proceed = true;
/*   71 */     String resid = "";
/*   72 */     String[] resourceid = null;
/*   73 */     if ((request.getParameter("isAPI") != null) && (request.getParameter("isAPI").equals("true")))
/*      */     {
/*   75 */       resid = request.getParameter("selectid");
/*   76 */       resourceid = resid.split(",");
/*   77 */       if (1 < resourceid.length)
/*      */       {
/*   79 */         resid = null;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*   84 */       resourceid = request.getParameterValues("select");
/*   85 */       resid = request.getParameter("resourceid");
/*   86 */       proceed = UserUtil.checkOperatorPermission(request);
/*      */     }
/*      */     
/*      */ 
/*   90 */     if (resourceid != null) {
/*   91 */       for (String currResId : resourceid)
/*      */       {
/*   93 */         AMAttributesCache.removeRealTimeAttrValues(Integer.parseInt(currResId));
/*   94 */         AMAttributesCache.clearExchangeRateRawValuesCache(currResId);
/*      */       }
/*   96 */     } else if ((resid != null) && (!resid.equals(""))) {
/*   97 */       AMAttributesCache.removeRealTimeAttrValues(Integer.parseInt(resid));
/*   98 */       AMAttributesCache.clearExchangeRateRawValuesCache(resid);
/*      */     }
/*      */     
/*  101 */     AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/*      */     
/*  103 */     String unopengroups = request.getParameter("unopengroups");
/*  104 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*  106 */     String listview = request.getParameter("listview");
/*  107 */     String listViewAppend = "";
/*  108 */     ResultSet select = null;
/*  109 */     ResultSet selectUnmg = null;
/*  110 */     String fromwhere = "unmanagemonitors";
/*      */     try
/*      */     {
/*  113 */       if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/*  114 */         resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */       }
/*      */       try {
/*  117 */         Vector resids = new Vector();
/*  118 */         if ((request.getAttribute("rbmagent") != null) && (((String)request.getAttribute("rbmagent")).equals("YES")))
/*      */         {
/*  120 */           String agentName = "" + request.getAttribute("agentname");
/*  121 */           if (agentName != null)
/*      */           {
/*  123 */             ResultSet rst = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_RBMDATA where AGENTNAME='" + agentName + "'");
/*  124 */             while (rst.next())
/*      */             {
/*  126 */               resids.addElement("" + rst.getString(1));
/*      */             }
/*      */           }
/*  129 */           String[] resIdArr = new String[resids.size()];
/*  130 */           for (int k = 0; k < resids.size(); k++)
/*      */           {
/*  132 */             resIdArr[k] = ((String)resids.elementAt(k));
/*      */           }
/*  134 */           resourceid = resIdArr;
/*  135 */           resid = null;
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception es)
/*      */       {
/*  141 */         es.printStackTrace();
/*      */       }
/*  143 */       long currentTime = System.currentTimeMillis();
/*  144 */       if ((listview != null) && (listview.equals("true"))) {
/*  145 */         listViewAppend = "&listview=true";
/*      */       }
/*      */       try {
/*  148 */         if (proceed)
/*      */         {
/*  150 */           if (resourceid != null) {
/*  151 */             for (String resId : resourceid) {
/*      */               try {
/*  153 */                 if (ParentChildRelationalUtil.isHAI(resId)) {
/*  154 */                   Vector<String> mgchildIds = new Vector();
/*  155 */                   ParentChildRelationalUtil.getAllChildMapper(mgchildIds, resId, true);
/*  156 */                   String[] subIds = (String[])mgchildIds.toArray(new String[mgchildIds.size()]);
/*  157 */                   DataCollectionControllerUtil.updateUnManageMonitors(subIds, null);
/*      */                 }
/*      */               } catch (Exception ec) {
/*  160 */                 ec.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*  165 */           DataCollectionControllerUtil.updateUnManageMonitors(resourceid, resid);
/*      */           
/*  167 */           if (!Constants.sqlManager) {
/*  168 */             ApmInsightMOManager.updateApmInsightMOState(resourceid, ResponseCode.UNMANAGE_AGENT);
/*      */           }
/*      */           
/*  171 */           if ((request.getParameter("isReset") != null) && ("true".equals(request.getParameter("isReset").toString())))
/*      */           {
/*  173 */             fromwhere = "unmanageandresetmonitors";
/*  174 */             Vector residVector = new Vector();
/*  175 */             HashSet residHset = new HashSet();
/*  176 */             if ((resid != null) && (!resid.trim().equals("")))
/*      */             {
/*  178 */               AMLog.debug("Unmanage & Reset Status called for resourceid--->" + resid);
/*  179 */               residVector.add(resid);
/*      */             }
/*  181 */             else if (resourceid != null)
/*      */             {
/*  183 */               for (int i = 0; i < resourceid.length; i++)
/*      */               {
/*  185 */                 if (resourceid[i] != null)
/*      */                 {
/*  187 */                   AMLog.debug("Unmanage & Reset Status called for resourceid[" + i + "]--->" + resourceid[i]);
/*  188 */                   residVector.add(resourceid[i]);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */             try
/*      */             {
/*  196 */               residHset = FaultUtil.getAllChildResids(residVector, new HashSet());
/*  197 */               FaultUtil.deleteAlertsForResource(residHset);
/*      */               
/*      */ 
/*      */ 
/*  201 */               ExecuteSDPTicketAction.closeTicketUsingRestApi(new ArrayList(residHset), FormatUtil.getString("am.webclient.action.ticket.closed.msg", new String[] { FormatUtil.getString("am.modowntime.reason.unmanaged") }), true);
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/*  205 */               ee.printStackTrace();
/*      */             }
/*      */           }
/*      */           
/*  209 */           if (request.getParameter("searchword") != null)
/*      */           {
/*  211 */             if ((request.getParameter("isFromEnterpriseSearch") != null) && (request.getParameter("isFromEnterpriseSearch").equalsIgnoreCase("true")))
/*      */             {
/*  213 */               String url = "/SearchTemp.do?query=" + request.getParameter("searchword") + "&module=IT360_APM&performSearch=true&detailedView=true" + "&fromIndex=0&pageLength=25&noOfRows=25&startIndex=1&personality=ManagedObjectPersonality&isAPMsearchRedirect=true";
/*      */               
/*  215 */               return new ActionForward(url);
/*      */             }
/*  217 */             if ((System.getProperty("EnterpriseSearch") != null) && (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true")))
/*      */             {
/*  219 */               return new ActionForward("/Search.do?old=true&query=" + request.getParameter("searchword"));
/*      */             }
/*      */             
/*      */ 
/*  223 */             return new ActionForward("/Search.do?query=" + request.getParameter("searchword"));
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  228 */           if ((Constants.sqlManager) && (listViewAppend.equals("")) && (request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */           {
/*  230 */             return new ActionForward("/showresource.do?group=All&method=showResourceMSSQL");
/*      */           }
/*      */           
/*  233 */           if ((Constants.sqlManager) && (!listViewAppend.equals("")))
/*      */           {
/*  235 */             if ((request.getParameter("network") != null) && (request.getParameter("network").equals("MSSQL-DB-server"))) {
/*  236 */               return new ActionForward("showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server&fromwhere=unmanagemonitors", true);
/*      */             }
/*  238 */             return new ActionForward("/showresource.do?group=All&method=showResourceMSSQL&fromwhere=unmanagemonitors", true);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  243 */           if (Constants.sqlManager)
/*      */           {
/*  245 */             return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&viewType=showDetailsView");
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  255 */           if ((listViewAppend.equals("")) && (request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */           {
/*  257 */             return new ActionForward("/index.do", true);
/*      */           }
/*      */         }
/*      */       } catch (Exception exx) {
/*  261 */         exx.printStackTrace();
/*      */       }
/*  263 */       if ((resid != null) && (!resid.trim().equals("")))
/*      */       {
/*      */ 
/*  266 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&fromwhere=" + fromwhere + "&resourceid=" + resid);
/*      */       }
/*  268 */       String group = request.getParameter("group");
/*  269 */       String viewmontype = "";
/*  270 */       String showmanage = "";
/*  271 */       if (request.getParameter("viewmontype") != null)
/*      */       {
/*  273 */         viewmontype = "&viewmontype=" + request.getParameter("viewmontype");
/*      */       }
/*  275 */       if ((request.getParameter("showmanage") != null) && (!request.getParameter("showmanage").equals("All")))
/*      */       {
/*  277 */         showmanage = "&showmanage=" + request.getParameter("showmanage");
/*      */       }
/*  279 */       if (group == null)
/*      */       {
/*  281 */         if (request.getParameter("fromiconview") != null) {
/*  282 */           return new ActionForward("/showresource.do?method=showIconsView&network=" + request.getParameter("selectedNetwork") + "&fromwhere=" + fromwhere);
/*      */         }
/*  284 */         return new ActionForward("/showresource.do?method=showResourceTypes&network=" + request.getParameter("type") + "&detailspage=true&fromwhere=" + fromwhere + listViewAppend + viewmontype + showmanage, true);
/*      */       }
/*      */       
/*      */ 
/*  288 */       if (group.equals("All")) {
/*  289 */         if ((request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */         {
/*  291 */           return new ActionForward("/webclient/common/jsp/home.jsp", true);
/*      */         }
/*  293 */         return new ActionForward("/showresource.do?method=showResourceTypesAll&group=All&fromwhere=" + fromwhere + viewmontype + showmanage, true);
/*      */       }
/*  295 */       return new ActionForward("/showresource.do?method=showResourceTypes&group=" + request.getParameter("group") + "&detailspage=true&fromwhere=" + fromwhere + listViewAppend + viewmontype + showmanage, true);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  300 */       e.printStackTrace(); }
/*  301 */     return null;
/*      */   }
/*      */   
/*      */   public String[] getUnopenChidresid(String[] resourcid, String unopengroups) {
/*      */     try {
/*  306 */       if ((unopengroups != null) && (unopengroups.trim().length() > 1)) {
/*  307 */         Vector<String> vresid = new Vector();
/*  308 */         StringTokenizer st = new StringTokenizer(unopengroups, "|");
/*  309 */         String str = "";
/*  310 */         while (st.hasMoreElements()) {
/*  311 */           str = st.nextToken();
/*  312 */           if (!str.equals("-1")) {
/*  313 */             ParentChildRelationalUtil.getAllChildMapper(vresid, str, true);
/*      */           }
/*      */           else
/*  316 */             ParentChildRelationalUtil.getUncategorizedMonitors(vresid);
/*      */         }
/*      */         String[] allresids;
/*  319 */         if (vresid.size() > 0) {
/*  320 */           allresids = new String[vresid.size() + resourcid.length];
/*  321 */           int i = 0;
/*  322 */           for (String oldresid : resourcid) {
/*  323 */             allresids[i] = oldresid;
/*  324 */             i++;
/*      */           }
/*  326 */           Iterator<String> ii = vresid.iterator();
/*  327 */           while (ii.hasNext()) {
/*  328 */             allresids[i] = ((String)ii.next());
/*  329 */             i++;
/*      */           } }
/*  331 */         return allresids;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  336 */       return resourcid;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  340 */       e.printStackTrace();
/*      */     }
/*  342 */     return resourcid;
/*      */   }
/*      */   
/*      */   public ActionForward it360LicenseCheckForManageMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  346 */     String resid = request.getParameter("resourceid");
/*      */     
/*  348 */     String[] resourceid = request.getParameterValues("select");
/*  349 */     response.setContentType("text/html");
/*  350 */     PrintWriter out = response.getWriter();
/*  351 */     if (Constants.isIt360)
/*      */     {
/*  353 */       Vector<String> licViolMessages = it360LicenseViolationCheck(resourceid, resid);
/*  354 */       if ((licViolMessages != null) && (!licViolMessages.isEmpty())) {
/*      */         try
/*      */         {
/*  357 */           Gson gson = new Gson();
/*  358 */           String jsonString = "";
/*      */           
/*      */           try
/*      */           {
/*  362 */             jsonString = gson.toJson(licViolMessages, new TypeToken() {}.getType());
/*      */           }
/*      */           catch (Exception e1)
/*      */           {
/*  366 */             e1.printStackTrace();
/*      */           }
/*  368 */           out.println(jsonString);
/*  369 */           out.flush();
/*  370 */           return null;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  374 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*  378 */     out.println("");
/*  379 */     out.flush();
/*  380 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward showUnmanageMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  385 */     boolean undermaintenance = DataCollectionControllerUtil.underMaintenance(request.getParameter("resid"));
/*  386 */     boolean unmanage = DBUtil.getGlobalConfigValueasBoolean("unmanage.monitor.after.maintenance");
/*  387 */     PrintWriter out = response.getWriter();
/*  388 */     if ((undermaintenance) && (!unmanage))
/*      */     {
/*  390 */       out.println(true);
/*  391 */       return null;
/*      */     }
/*  393 */     out.println(false);
/*  394 */     return null;
/*      */   }
/*      */   
/*      */   public ActionForward manageMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  399 */     boolean proceed = UserUtil.checkOperatorPermission(request);
/*      */     
/*  401 */     long currentTime = System.currentTimeMillis();
/*  402 */     AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/*  403 */     String[] resourceid = request.getParameterValues("select");
/*  404 */     int count = 0;
/*  405 */     String resid = request.getParameter("resourceid");
/*  406 */     String unopengroups = request.getParameter("unopengroups");
/*  407 */     String listview = request.getParameter("listview");
/*  408 */     String listViewAppend = "";
/*  409 */     String updateQuery = null;
/*  410 */     boolean appendInsightMsg = false;
/*      */     
/*  412 */     if ((listview != null) && (listview.equals("true"))) {
/*  413 */       listViewAppend = "&listview=true";
/*      */     }
/*      */     try {
/*  416 */       if (proceed)
/*      */       {
/*  418 */         if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/*  419 */           resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */         }
/*  421 */         com.adventnet.appmanager.server.framework.AMServerFramework.toXtraMonitorsPage = true;
/*  422 */         if (Constants.isIt360)
/*      */         {
/*  424 */           Vector<String> licViolMessages = it360LicenseViolationCheck(resourceid, resid);
/*  425 */           if ((licViolMessages != null) && (!licViolMessages.isEmpty()))
/*      */           {
/*  427 */             request.setAttribute("LIC_VIOL_ROOT_MESSAGE", FormatUtil.getString("it360.license.manage.notallowed"));
/*  428 */             request.setAttribute("LIC_VIOL_MESSAGES", licViolMessages);
/*  429 */             return new ActionForward("/showIT360Tile.do?TileName=IT360.IT360LicReject");
/*      */           }
/*      */         }
/*      */         
/*  433 */         if (resourceid != null) {
/*  434 */           for (String resId : resourceid) {
/*      */             try {
/*  436 */               if (ParentChildRelationalUtil.isHAI(resId)) {
/*  437 */                 Vector<String> mgchildIds = new Vector();
/*  438 */                 ParentChildRelationalUtil.getAllChildMapper(mgchildIds, resId, true);
/*  439 */                 String[] subIds = (String[])mgchildIds.toArray(new String[mgchildIds.size()]);
/*  440 */                 DataCollectionControllerUtil.updateManageMonitors(subIds, null);
/*      */               }
/*      */             } catch (Exception ec) {
/*  443 */               ec.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  448 */         DataCollectionControllerUtil.updateManageMonitors(resourceid, resid);
/*  449 */         if (!Constants.sqlManager)
/*      */         {
/*  451 */           ApmInsightMOManager.updateApmInsightMOState(resourceid, ResponseCode.MANAGE_AGENT);
/*      */         }
/*  453 */         if ((request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */         {
/*  455 */           return new ActionForward("/index.do", true);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exx) {
/*  460 */       exx.printStackTrace();
/*      */     }
/*  462 */     if (request.getParameter("searchword") != null)
/*      */     {
/*  464 */       if ((request.getParameter("isFromEnterpriseSearch") != null) && (request.getParameter("isFromEnterpriseSearch").equalsIgnoreCase("true")))
/*      */       {
/*  466 */         String url = "/SearchTemp.do?query=" + request.getParameter("searchword") + "&module=IT360_APM&performSearch=true&detailedView=true" + "&fromIndex=0&pageLength=25&noOfRows=25&startIndex=1&personality=ManagedObjectPersonality&isAPMsearchRedirect=true";
/*      */         
/*  468 */         return new ActionForward(url);
/*      */       }
/*  470 */       if ((System.getProperty("EnterpriseSearch") != null) && (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true")))
/*      */       {
/*  472 */         return new ActionForward("/Search.do?old=true&query=" + request.getParameter("searchword"));
/*      */       }
/*      */       
/*      */ 
/*  476 */       return new ActionForward("/Search.do?query=" + request.getParameter("searchword"));
/*      */     }
/*      */     
/*      */ 
/*  480 */     if ((Constants.sqlManager) && (!listViewAppend.equals("")))
/*      */     {
/*  482 */       if ((request.getParameter("network") != null) && (request.getParameter("network").equals("MSSQL-DB-server"))) {
/*  483 */         return new ActionForward("showresource.do?method=showResourceTypes&detailspage=true&network=MSSQL-DB-server&viewmontype=MSSQL-DB-server&fromwhere=managemonitors", true);
/*      */       }
/*  485 */       return new ActionForward("/showresource.do?group=All&method=showResourceMSSQL&fromwhere=managemonitors", true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  490 */     if (Constants.sqlManager)
/*      */     {
/*  492 */       return new ActionForward("/showresource.do?resourceid=" + resid + "&method=showResourceForResourceID&viewType=showDetailsView");
/*      */     }
/*  494 */     if ((resid != null) && (!resid.trim().equals("")))
/*      */     {
/*  496 */       String allowManage = DBUtil.getGlobalConfigValue("allowOperatorManage");
/*  497 */       if ((request.isUserInRole("OPERATOR")) && ("false".equals(allowManage))) {
/*  498 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&messageneeded=false&fromwhere=managemonitors&resourceid=" + resid, true);
/*      */       }
/*  500 */       String restype = DBUtil.getResourceType(resid);
/*  501 */       String url = "/GlobalActions.do?method=pollNow&resourceid=" + resid + "&resourcetype=" + restype + "&baseid=" + DBUtil.getBaseId(restype);
/*  502 */       return new ActionForward(url);
/*      */     }
/*  504 */     String group = request.getParameter("group");
/*  505 */     String viewmontype = "";
/*  506 */     String showmanage = "";
/*  507 */     if (request.getParameter("viewmontype") != null)
/*      */     {
/*  509 */       viewmontype = "&viewmontype=" + request.getParameter("viewmontype");
/*      */     }
/*  511 */     if ((request.getParameter("showmanage") != null) && (!request.getParameter("showmanage").equals("All")))
/*      */     {
/*  513 */       showmanage = "&showmanage=" + request.getParameter("showmanage");
/*      */     }
/*  515 */     if (group == null)
/*      */     {
/*  517 */       if (request.getParameter("fromiconview") != null) {
/*  518 */         return new ActionForward("/showresource.do?method=showIconsView&network=" + request.getParameter("selectedNetwork") + "&fromwhere=managemonitors");
/*      */       }
/*  520 */       return new ActionForward("/showresource.do?method=showResourceTypes&network=" + request.getParameter("type") + "&detailspage=true&fromwhere=managemonitors" + listViewAppend + viewmontype + showmanage, true);
/*      */     }
/*      */     
/*      */ 
/*  524 */     if (group.equals("All")) {
/*  525 */       if ((request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */       {
/*  527 */         return new ActionForward("/webclient/common/jsp/home.jsp", true);
/*      */       }
/*      */       
/*  530 */       String returnPath = "/showresource.do?method=showResourceTypesAll&group=All&fromwhere=managemonitors" + listViewAppend + viewmontype + showmanage;
/*  531 */       if (appendInsightMsg)
/*      */       {
/*  533 */         returnPath = returnPath + "&insightMsg=" + appendInsightMsg;
/*      */       }
/*      */       
/*  536 */       return new ActionForward(returnPath, true);
/*      */     }
/*  538 */     return new ActionForward("/showresource.do?method=showResourceTypes&group=" + request.getParameter("group") + "&detailspage=true&fromwhere=managemonitors" + listViewAppend + viewmontype + showmanage, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteMO(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  548 */     if (request.isUserInRole("OPERATOR"))
/*      */     {
/*  550 */       return mapping.findForward("accessRestricted");
/*      */     }
/*  552 */     String selectedsqlresourceid = "";
/*  553 */     String fromConfDetails = request.getParameter("fromConfDetails");
/*  554 */     String[] resourceid = request.getParameterValues("select");
/*  555 */     String unopengroups = request.getParameter("unopengroups");
/*  556 */     if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/*  557 */       resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */     }
/*  559 */     if ((fromConfDetails != null) && (fromConfDetails.equals("true"))) {
/*  560 */       String tempResId = request.getParameter("rowid");
/*      */       
/*  562 */       StringTokenizer ids = new StringTokenizer(tempResId, ",");
/*  563 */       String[] resIds = new String[ids.countTokens()];
/*  564 */       int i = 0;
/*  565 */       while (ids.hasMoreTokens()) {
/*  566 */         resIds[(i++)] = ids.nextToken();
/*      */       }
/*  568 */       resourceid = null;
/*  569 */       resourceid = resIds;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  574 */     if (resourceid != null) {
/*  575 */       for (String currResId : resourceid)
/*      */       {
/*  577 */         AMAttributesCache.removeRealTimeAttrValues(Integer.parseInt(currResId));
/*  578 */         AMAttributesCache.clearExchangeRateRawValuesCache(currResId);
/*  579 */         AMAttributesCache.removeFromProcessMaintenanceCache(currResId);
/*  580 */         AMAttributesCache.removeFromProcessUnManagedCache(currResId);
/*      */       }
/*      */     }
/*  583 */     String[] rbmAgents = request.getParameterValues("selectagent");
/*  584 */     Properties p = new Properties();
/*  585 */     String group = request.getParameter("group");
/*  586 */     String listview = request.getParameter("listview");
/*  587 */     String listViewAppend = "";
/*      */     
/*      */ 
/*  590 */     String redirect = request.getParameter("redirectto");
/*      */     
/*      */ 
/*      */ 
/*  594 */     String[] qresourceid = new String[0];
/*  595 */     String[] hresourceid = new String[0];
/*  596 */     String[] wresourceid = new String[0];
/*  597 */     ResultSet rs = null;
/*      */     
/*  599 */     if ((listview != null) && (listview.equals("true"))) {
/*  600 */       listViewAppend = "&listview=true";
/*      */     }
/*      */     
/*      */ 
/*  604 */     if ((Constants.sqlManager) && (resourceid != null) && (resourceid.length > 0))
/*      */     {
/*  606 */       StringBuilder sqlid = new StringBuilder();
/*  607 */       for (int i = 0; i < resourceid.length; i++)
/*      */       {
/*  609 */         sqlid.append(resourceid[i]).append(",");
/*      */       }
/*  611 */       StringBuilder hostid = new StringBuilder();
/*  612 */       ArrayList hresids = new ArrayList();
/*  613 */       rs = AMConnectionPool.executeQueryStmt("select hostid from SQLDBM_SQL_HOST_MAPPING where sqlid in(" + sqlid.substring(0, sqlid.length() - 1) + ")");
/*  614 */       selectedsqlresourceid = sqlid.substring(0, sqlid.length() - 1);
/*  615 */       while (rs.next())
/*      */       {
/*  617 */         hostid.append(rs.getString("hostid")).append(",");
/*  618 */         hresids.add(rs.getString("hostid"));
/*      */       }
/*  620 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*  623 */       if (hresids.size() > 0)
/*      */       {
/*  625 */         rs = AMConnectionPool.executeQueryStmt("select hostid from SQLDBM_SQL_HOST_MAPPING where sqlid not in(" + sqlid.substring(0, sqlid.length() - 1) + ") and hostid in(" + hostid.substring(0, hostid.length() - 1) + ")");
/*      */         
/*  627 */         while (rs.next())
/*      */         {
/*  629 */           hresids.remove(rs.getString("hostid"));
/*      */         }
/*  631 */         AMConnectionPool.closeStatement(rs);
/*  632 */         hresourceid = (String[])hresids.toArray(new String[0]);
/*      */       }
/*      */       
/*      */ 
/*  636 */       ArrayList qresids = new ArrayList();
/*  637 */       rs = AMConnectionPool.executeQueryStmt("select pcm.childid from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where pcm.parentid in(" + sqlid.substring(0, sqlid.length() - 1) + ") and pcm.childid=mo.resourceid and mo.type='QueryMonitor'");
/*  638 */       while (rs.next())
/*      */       {
/*  640 */         qresids.add(rs.getString("childid"));
/*      */       }
/*  642 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*  644 */       if (qresids.size() > 0)
/*      */       {
/*  646 */         qresourceid = (String[])qresids.toArray(new String[0]);
/*      */       }
/*      */       
/*  649 */       ArrayList wresids = new ArrayList();
/*  650 */       rs = AMConnectionPool.executeQueryStmt("select pcm.childid from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo where pcm.parentid in(" + sqlid.substring(0, sqlid.length() - 1) + ") and pcm.childid=mo.resourceid and mo.type='Generic WMI'");
/*  651 */       while (rs.next()) {
/*  652 */         wresids.add(rs.getString("childid"));
/*      */       }
/*  654 */       AMConnectionPool.closeStatement(rs);
/*  655 */       if (wresids.size() > 0) {
/*  656 */         wresourceid = (String[])wresids.toArray(new String[0]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  662 */     if ((rbmAgents != null) && (rbmAgents.length > 0))
/*      */     {
/*  664 */       for (int i = 0; i < rbmAgents.length; i++)
/*      */       {
/*  666 */         AgentUtil.deleteAgent("" + rbmAgents[i]);
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
/*      */       try
/*      */       {
/*  679 */         if ((resourceid != null) && (!Constants.sqlManager)) {
/*  680 */           resourceid = (String[])ApmInsightMOManager.updateApmInsightMOState(resourceid, ResponseCode.DELETE_AGENT);
/*      */         }
/*      */       } catch (Exception exc1) {
/*  683 */         exc1.printStackTrace();
/*  684 */         AMLog.debug("DataCollectionController : deleteMO : ERROR Thrown when updateApmInsightMOState CALLED :" + exc1.getMessage());
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  689 */       String[] fdresourceid = new String[0];
/*  690 */       ResultSet rs1 = null;
/*  691 */       StringBuilder hostid = new StringBuilder();
/*  692 */       if (resourceid != null)
/*      */       {
/*  694 */         for (int i = 0; i < resourceid.length; i++)
/*      */         {
/*  696 */           hostid.append(resourceid[i]).append(",");
/*      */         }
/*      */       }
/*      */       
/*  700 */       if (hostid.length() > 1) {
/*  701 */         ArrayList fdresids = new ArrayList();
/*  702 */         String qry = "select pcm.CHILDID from AM_PARENTCHILDMAPPER pcm,AM_ManagedObject mo,AM_SCRIPTHOST_MAPPER smap  where pcm.PARENTID in(" + hostid.substring(0, hostid.length() - 1) + ") and pcm.CHILDID=smap.SCRIPTID and smap.SCRIPTID=mo.RESOURCEID and (mo.TYPE='file' OR mo.TYPE='directory' OR mo.TYPE='Script Monitor')";
/*      */         try {
/*  704 */           rs1 = AMConnectionPool.executeQueryStmt(qry);
/*  705 */           while (rs1.next()) {
/*  706 */             fdresids.add(rs1.getString(1));
/*      */           }
/*      */         } catch (Exception e) {
/*  709 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/*  712 */           AMConnectionPool.closeStatement(rs1);
/*      */         }
/*  714 */         if (fdresids.size() > 0) {
/*  715 */           fdresourceid = (String[])fdresids.toArray(new String[0]);
/*      */         }
/*      */         
/*  718 */         if (fdresourceid.length > 0)
/*      */         {
/*  720 */           DataCollectionDBUtil.deleteMonitor(fdresourceid, p, "File System Monitor");
/*  721 */           DataCollectionDBUtil.deleteMonitor(fdresourceid, p, "Script Monitor");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  726 */       if ((group == null) && (resourceid != null) && (resourceid.length > 0))
/*      */       {
/*  728 */         DataCollectionDBUtil.deleteMonitor(resourceid, p, request.getParameter("type"));
/*      */       }
/*  730 */       else if ((resourceid != null) && (resourceid.length > 0))
/*      */       {
/*  732 */         DataCollectionDBUtil.deleteMonitor(resourceid, p, group);
/*      */       }
/*  734 */       if (resourceid != null)
/*      */       {
/*  736 */         int resourceLength = resourceid.length;
/*  737 */         if ((resourceid != null) && (resourceLength > 0))
/*      */         {
/*  739 */           JSONArray jsArr = null;
/*  740 */           for (int i = 0; i < resourceLength; i++)
/*      */           {
/*  742 */             jsArr = new JSONArray();
/*  743 */             jsArr.put(resourceid[i]);
/*      */           }
/*  745 */           if (jsArr != null)
/*      */           {
/*  747 */             DBUtil.deleteMonitorsAssociatedToAdminMGForDependency(jsArr);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  755 */       if ((Constants.sqlManager) && (hresourceid.length > 0))
/*      */       {
/*  757 */         DataCollectionDBUtil.deleteMonitor(hresourceid, p, "Windows");
/*      */       }
/*      */       
/*  760 */       if ((Constants.sqlManager) && (qresourceid.length > 0))
/*      */       {
/*  762 */         DataCollectionDBUtil.deleteMonitor(qresourceid, p, "Script Monitor");
/*      */       }
/*      */       
/*  765 */       if ((Constants.sqlManager) && (wresourceid.length > 0)) {
/*  766 */         DataCollectionDBUtil.deleteMonitor(wresourceid, p, "Generic WMI");
/*      */       }
/*  768 */       System.out.println("Finished the deletion.....");
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*      */ 
/*  775 */       e.printStackTrace();
/*      */     }
/*      */     
/*  778 */     if (request.getParameter("searchword") != null)
/*      */     {
/*  780 */       if ((request.getParameter("isFromEnterpriseSearch") != null) && (request.getParameter("isFromEnterpriseSearch").equalsIgnoreCase("true")))
/*      */       {
/*  782 */         String url = "/SearchTemp.do?query=" + request.getParameter("searchword") + "&module=IT360_APM&performSearch=true&detailedView=true" + "&fromIndex=0&pageLength=25&noOfRows=25&startIndex=1&personality=ManagedObjectPersonality&isAPMsearchRedirect=true";
/*      */         
/*  784 */         return new ActionForward(url);
/*      */       }
/*  786 */       if ((System.getProperty("EnterpriseSearch") != null) && (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true")))
/*      */       {
/*  788 */         return new ActionForward("/Search.do?old=true&query=" + request.getParameter("searchword"));
/*      */       }
/*      */       
/*      */ 
/*  792 */       return new ActionForward("/Search.do?query=" + request.getParameter("searchword"));
/*      */     }
/*      */     
/*  795 */     if (Constants.sqlManager)
/*      */     {
/*  797 */       return new ActionForward("/showresource.do?group=All&method=showResourceMSSQL");
/*      */     }
/*  799 */     String viewmontype = "";
/*  800 */     String showmanage = "";
/*  801 */     String fromwhere = "";
/*  802 */     if (request.getParameter("viewmontype") != null)
/*      */     {
/*  804 */       viewmontype = "&viewmontype=" + request.getParameter("viewmontype");
/*      */     }
/*  806 */     if ((request.getParameter("showmanage") != null) && (!request.getParameter("showmanage").equals("All")))
/*      */     {
/*  808 */       showmanage = "&showmanage=" + request.getParameter("showmanage");
/*      */     }
/*  810 */     if (request.getParameter("fromwhere") != null)
/*      */     {
/*  812 */       fromwhere = "&fromwhere=" + request.getParameter("fromwhere");
/*      */     }
/*      */     else {
/*  815 */       fromwhere = "&fromwhere=deleteMO";
/*      */     }
/*  817 */     if (group == null)
/*      */     {
/*  819 */       if ((fromConfDetails != null) && (fromConfDetails.equals("true")))
/*      */       {
/*  821 */         if (request.getParameter("fromPopUpWindow") != null) {
/*  822 */           return null;
/*      */         }
/*  824 */         String appendHash = ConfMonitorUtil.getHashValueOfURL(request);
/*  825 */         return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + request.getParameter("resourceid") + appendHash, true);
/*      */       }
/*  827 */       if (request.getParameter("fromiconview") != null) {
/*  828 */         return new ActionForward("/showresource.do?method=showIconsView&network=" + request.getParameter("selectedNetwork"));
/*      */       }
/*  830 */       if (redirect != null) {
/*  831 */         return new ActionForward(redirect);
/*      */       }
/*      */       
/*  834 */       return new ActionForward("/showresource.do?method=showResourceTypes&network=" + request.getParameter("type") + "&detailspage=true" + listViewAppend + viewmontype + showmanage + fromwhere);
/*      */     }
/*      */     
/*      */ 
/*  838 */     if (group.equals("All")) {
/*  839 */       if ((request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */       {
/*  841 */         return new ActionForward("/webclient/common/jsp/home.jsp", true);
/*      */       }
/*  843 */       return new ActionForward("/showresource.do?method=showResourceTypesAll&group=All" + listViewAppend + viewmontype + showmanage + fromwhere, true);
/*      */     }
/*  845 */     return new ActionForward("/showresource.do?method=showResourceTypes&group=" + request.getParameter("group") + "&detailspage=true" + listViewAppend + viewmontype + showmanage + fromwhere, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward fetchDataNowForResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  855 */     String resourceid = request.getParameter("resourceid");
/*      */     
/*  857 */     CAMServerUtil.checkAndScheduleCAMJob(Integer.parseInt(resourceid));
/*  858 */     System.out.println(" ACTION : Fetching data completed for resource with id " + resourceid);
/*  859 */     if (request.getParameter("redirectto") != null) {
/*  860 */       return new ActionForward(request.getParameter("redirectto"), true);
/*      */     }
/*  862 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID", true);
/*      */   }
/*      */   
/*      */   public ActionForward unManageMonitorGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*      */     try {
/*  868 */       String message = "";
/*  869 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  870 */       ManagedApplication mo = new ManagedApplication();
/*  871 */       ResultSet select = null;
/*  872 */       ResultSet selectUnmg = null;
/*  873 */       long currentTime = System.currentTimeMillis();
/*  874 */       boolean proceed = UserUtil.checkOperatorPermission(request);
/*  875 */       String retaintree = "";
/*  876 */       if (request.getParameter("retaintree") != null)
/*      */       {
/*  878 */         retaintree = request.getParameter("retaintree");
/*      */       }
/*  880 */       String unopengroups = request.getParameter("unopengroups");
/*  881 */       String fromwhere = "unmanagemonitorgroups";
/*  882 */       AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/*  883 */       String[] resourceid = request.getParameterValues("select");
/*      */       
/*  885 */       String resid = request.getParameter("resourceid");
/*      */       try {
/*  887 */         if (proceed)
/*      */         {
/*  889 */           ArrayList<String> batchqueries = new ArrayList();
/*  890 */           if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/*  891 */             resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */           }
/*  893 */           if ((resid != null) && (!resid.trim().equals("")))
/*      */           {
/*  895 */             batchqueries.add("insert into AM_UnManagedNodes values(" + resid + ")");
/*  896 */             EnterpriseUtil.addUpdateQueryToFile("insert into AM_UnManagedNodes values(" + resid + ")");
/*      */           }
/*  898 */           else if (resourceid != null)
/*      */           {
/*  900 */             String insertqry = null;
/*  901 */             if (EnterpriseUtil.isAdminServer())
/*      */             {
/*  903 */               Object[] ob = ManagedAPICallUtil.maptoManagedServers(resourceid, false);
/*  904 */               String adminhaid = (String)ob[0];
/*  905 */               HashMap<String, StringBuilder> managedresourcelist = (HashMap)ob[1];
/*  906 */               String callingmethod = "UnmanageMonitor";
/*  907 */               if ("true".equalsIgnoreCase(request.getParameter("isReset"))) {
/*  908 */                 callingmethod = "UnmanageAndResetMonitor";
/*      */               }
/*  910 */               message = ManagedAPICallUtil.callManagedServers(managedresourcelist, callingmethod, false);
/*  911 */               if ((adminhaid != null) && (adminhaid.length() > 3))
/*      */               {
/*      */ 
/*  914 */                 resourceid = adminhaid.split(",");
/*      */               }
/*      */               else {
/*  917 */                 resourceid = null;
/*      */               }
/*      */             }
/*  920 */             if (resourceid != null) {
/*  921 */               for (int i = 0; i < resourceid.length; i++)
/*      */               {
/*  923 */                 insertqry = "insert into AM_UnManagedNodes values(" + resourceid[i] + ")";
/*  924 */                 batchqueries.add(insertqry);
/*      */                 
/*      */ 
/*  927 */                 select = AMConnectionPool.executeQueryStmt("select DOWNTIME,TYPE from AM_MO_DowntimeData where RESID = " + resourceid[i] + " and UPTIME = 0 and TYPE != 2");
/*  928 */                 if (select.next())
/*      */                 {
/*  930 */                   AMConnectionPool.executeUpdateStmt("update AM_MO_DowntimeData set UPTIME = " + currentTime + " where RESID = " + resourceid[i] + " and DOWNTIME = " + select.getLong("DOWNTIME"));
/*  931 */                   EnterpriseUtil.addUpdateQueryToFile("update AM_MO_DowntimeData set UPTIME = " + currentTime + " where RESID = " + resourceid[i] + " and DOWNTIME = " + select.getLong("DOWNTIME"));
/*  932 */                   AMLog.debug("UNMANAGED TIME: For the Monitor : " + resourceid[i] + " started @ " + FormatUtil.formatDT(new StringBuilder().append("").append(select.getLong("DOWNTIME")).toString()) + " and completed @ " + FormatUtil.formatDT(new StringBuilder().append("").append(currentTime).toString()) + " and took " + (currentTime - select.getLong("DOWNTIME")) + " ms");
/*  933 */                   if (select.getString("TYPE").equalsIgnoreCase("1"))
/*      */                   {
/*  935 */                     insertqry = "insert into AM_MO_DowntimeData (RESID, DOWNTIME, UPTIME, TYPE) values ('" + resourceid[i] + "', '" + currentTime + "', 0, 2)";
/*  936 */                     batchqueries.add(insertqry);
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 else
/*      */                 {
/*  942 */                   selectUnmg = AMConnectionPool.executeQueryStmt("select DOWNTIME from AM_MO_DowntimeData where RESID = " + resourceid[i] + " and TYPE = 2 and UPTIME = 0");
/*  943 */                   if (selectUnmg.next()) {
/*  944 */                     System.out.println("Already the Row is inserted in AM_MO_DowntimeData");
/*      */                   }
/*      */                   else {
/*  947 */                     String insertQuery = "insert into AM_MO_DowntimeData (RESID, DOWNTIME, UPTIME, TYPE) values ('" + resourceid[i] + "', '" + currentTime + "', 0, 2)";
/*  948 */                     AMConnectionPool.executeUpdateStmt(insertQuery);
/*  949 */                     EnterpriseUtil.addUpdateQueryToFile(insertQuery);
/*      */                   }
/*      */                 }
/*  952 */                 if (!EnterpriseUtil.isAdminServer)
/*      */                 {
/*  954 */                   EnterpriseUtil.addUpdateQueryToFile(insertqry);
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */               try
/*      */               {
/*  961 */                 Iterator it = batchqueries.iterator();
/*  962 */                 while (it.hasNext())
/*      */                 {
/*  964 */                   stmt.addBatch((String)it.next());
/*      */                 }
/*  966 */                 stmt.executeBatch();
/*  967 */                 stmt.clearBatch();
/*  968 */                 stmt.close();
/*      */               }
/*      */               catch (SQLException sql)
/*      */               {
/*      */                 try
/*      */                 {
/*  974 */                   if ("pgsql".equals(System.getProperty("am.dbserver.type"))) {
/*  975 */                     sql = sql.getNextException();
/*  976 */                     AMLog.debug("SOME PROBLEM IN UPDATING THE UNMANAGED NODES BY BATCH,SO EXECUTING INDIVIDUALLY");
/*  977 */                     if ("23505".equals(sql.getSQLState())) {
/*  978 */                       Iterator it = batchqueries.iterator();
/*  979 */                       while (it.hasNext()) {
/*      */                         try
/*      */                         {
/*  982 */                           AMConnectionPool.executeUpdateStmt((String)it.next());
/*      */ 
/*      */                         }
/*      */                         catch (Exception e) {}
/*      */                       }
/*      */                       
/*      */                     }
/*      */                   }
/*      */                   else
/*      */                   {
/*  992 */                     sql.printStackTrace();
/*      */                   }
/*      */                 }
/*      */                 catch (Exception ee)
/*      */                 {
/*  997 */                   ee.printStackTrace();
/*      */                 }
/*      */               }
/*      */               catch (Exception e)
/*      */               {
/* 1002 */                 e.printStackTrace();
/*      */               }
/*      */               finally
/*      */               {
/* 1006 */                 select.close();
/* 1007 */                 if (selectUnmg != null)
/*      */                 {
/* 1009 */                   selectUnmg.close();
/*      */                 }
/*      */               }
/*      */               try
/*      */               {
/* 1014 */                 DataCollectionControllerUtil.setUnmanaged_nodes(DataCollectionControllerUtil.getUnManagedNodes());
/* 1015 */                 DataCollectionControllerUtil.setManaged_nodes(DataCollectionControllerUtil.getManagedNodes());
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/* 1019 */                 System.out.println("Some problem in updating the memory for UNManaged Nodes");
/* 1020 */                 exc.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/* 1024 */               if ((request.getParameter("isReset") != null) && ("true".equals(request.getParameter("isReset").toString())))
/*      */               {
/* 1026 */                 Vector residVector = new Vector();
/* 1027 */                 HashSet residHset = new HashSet();
/* 1028 */                 fromwhere = "unmanageandresetmonitorgroups";
/* 1029 */                 if ((resid != null) && (!resid.trim().equals("")))
/*      */                 {
/* 1031 */                   AMLog.debug("MG Level Unmanage & Reset Status called for resourceid--->" + resid);
/* 1032 */                   residVector.add(resid);
/*      */                 }
/* 1034 */                 else if (resourceid != null)
/*      */                 {
/* 1036 */                   for (int i = 0; i < resourceid.length; i++)
/*      */                   {
/* 1038 */                     if (resourceid[i] != null)
/*      */                     {
/* 1040 */                       AMLog.debug("MG Level Unmanage & Reset Status called for resourceid[" + i + "]--->" + resourceid[i]);
/* 1041 */                       residVector.add(resourceid[i]);
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/*      */                 try
/*      */                 {
/* 1048 */                   residHset = FaultUtil.getAllChildResids(residVector, new HashSet());
/* 1049 */                   String resStr = residVector.toString();
/* 1050 */                   resStr = resStr.substring(1, resStr.length() - 1);
/* 1051 */                   String[] resType = new String[1];
/* 1052 */                   resType[0] = "Service";
/* 1053 */                   if (!resStr.equals(""))
/*      */                   {
/* 1055 */                     Long[] serviceArr = DBUtil.getResourceIdForType(resType, resStr);
/* 1056 */                     if (serviceArr != null)
/*      */                     {
/* 1058 */                       for (int i = 0; i < serviceArr.length; i++)
/*      */                       {
/* 1060 */                         residHset.add(String.valueOf(serviceArr[i]));
/*      */                       }
/*      */                     }
/*      */                   }
/* 1064 */                   FaultUtil.deleteAlertsForResource(residHset);
/*      */                 }
/*      */                 catch (Exception ee) {
/* 1067 */                   ee.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception exx) {
/* 1075 */         exx.printStackTrace();
/*      */       }
/* 1077 */       if ((request.getParameter("haid") != null) && (!"".equals(request.getParameter("haid").trim())))
/*      */       {
/* 1079 */         String haid = request.getParameter("haid");
/* 1080 */         return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication&fromwhere=" + fromwhere);
/*      */       }
/* 1082 */       return new ActionForward("/showresource.do?method=showMonitorGroupView&fromwhere=" + fromwhere + "&retaintree=" + retaintree + "&apimessage=" + message, true);
/*      */     }
/*      */     catch (Exception e) {
/* 1085 */       e.printStackTrace(); }
/* 1086 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward ManageMonitorGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1093 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1094 */     long currentTime = System.currentTimeMillis();
/* 1095 */     String updateQuery = null;
/* 1096 */     ResultSet select = null;
/* 1097 */     boolean proceed = UserUtil.checkOperatorPermission(request);
/* 1098 */     ManagedApplication mo = new ManagedApplication();
/* 1099 */     AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/* 1100 */     String[] resourceid = request.getParameterValues("select");
/* 1101 */     int count = 0;
/* 1102 */     String retaintree = "";
/* 1103 */     String message = "";
/* 1104 */     if (request.getParameter("retaintree") != null)
/*      */     {
/* 1106 */       retaintree = request.getParameter("retaintree");
/*      */     }
/* 1108 */     String unopengroups = request.getParameter("unopengroups");
/* 1109 */     String apimessage = "";
/*      */     try {
/* 1111 */       if (proceed)
/*      */       {
/* 1113 */         if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/* 1114 */           resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */         }
/* 1116 */         if (resourceid != null)
/*      */         {
/* 1118 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/* 1120 */             Object[] ob = ManagedAPICallUtil.maptoManagedServers(resourceid, false);
/* 1121 */             String adminhaid = (String)ob[0];
/* 1122 */             HashMap<String, StringBuilder> managedresourcelist = (HashMap)ob[1];
/* 1123 */             message = ManagedAPICallUtil.callManagedServers(managedresourcelist, "ManageMonitor", false);
/* 1124 */             if ((adminhaid != null) && (adminhaid.length() > 3))
/*      */             {
/* 1126 */               resourceid = adminhaid.split(",");
/*      */             }
/*      */             else {
/* 1129 */               resourceid = null;
/*      */             }
/*      */           }
/* 1132 */           if (resourceid != null) {
/* 1133 */             String residstoquery = "(";
/* 1134 */             for (int i = 0; i < resourceid.length; i++)
/*      */             {
/* 1136 */               if (i + 1 == resourceid.length)
/*      */               {
/* 1138 */                 residstoquery = residstoquery + resourceid[i] + ")";
/*      */               }
/*      */               else
/*      */               {
/* 1142 */                 residstoquery = residstoquery + resourceid[i] + ",";
/*      */               }
/*      */             }
/* 1145 */             count = 0;
/* 1146 */             String configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.TYPE from AM_ManagedObject where RESOURCEID in " + residstoquery;
/* 1147 */             ArrayList rows = mo.getRows(configured);
/* 1148 */             for (int i = 0; i < rows.size(); i++)
/*      */             {
/* 1150 */               ArrayList singlerow = (ArrayList)rows.get(i);
/* 1151 */               String id = (String)singlerow.get(0);
/* 1152 */               String type = (String)singlerow.get(1);
/* 1153 */               if (type.equals("HAI"))
/* 1154 */                 count++;
/*      */             }
/* 1156 */             HashMap al = DataCollectionControllerUtil.getManaged_nodes();
/* 1157 */             if ((al != null) && (al.size() > 0))
/*      */             {
/* 1159 */               for (int j = 0; j < resourceid.length; j++)
/*      */               {
/* 1161 */                 if (al.containsKey(resourceid[j]))
/*      */                 {
/* 1163 */                   count++;
/*      */                 }
/*      */               }
/*      */             }
/* 1167 */             if (!DataCollectionControllerUtil.isallowed(resourceid.length - count))
/*      */             {
/* 1169 */               System.out.println("License Count Exceeded and hence the Manage Operation  is not allowed");
/* 1170 */               return new ActionForward("/showresource.do?method=showMonitorGroupView&notallowed=true&fromwhere=managemonitorgroups&retaintree=" + retaintree, true);
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1175 */             for (int j = 0; j < resourceid.length; j++)
/*      */             {
/*      */ 
/*      */ 
/* 1179 */               stmt.addBatch("delete from AM_UnManagedNodes where resid='" + resourceid[j] + "'");
/*      */               
/* 1181 */               select = AMConnectionPool.executeQueryStmt("select DOWNTIME from AM_MO_DowntimeData where RESID = " + resourceid[j] + " and UPTIME = 0");
/* 1182 */               if (select.next())
/*      */               {
/* 1184 */                 updateQuery = "update AM_MO_DowntimeData set UPTIME = " + currentTime + " where RESID = " + resourceid[j] + " and DOWNTIME = " + select.getLong("DOWNTIME");
/* 1185 */                 AMConnectionPool.executeUpdateStmt(updateQuery);
/* 1186 */                 EnterpriseUtil.addUpdateQueryToFile(updateQuery);
/*      */                 
/* 1188 */                 String availId = String.valueOf(Constants.getAvailIDforResourceID(resourceid[j]));
/* 1189 */                 if (FaultUtil.getLatestEvent(resourceid[j] + "_" + availId) != null) {
/* 1190 */                   int severity = Integer.parseInt((String)FaultUtil.getLatestEvent(resourceid[j] + "_" + availId).get("SEVERITY"));
/* 1191 */                   if (severity == 1) {
/* 1192 */                     String insertQuery = "insert into AM_MO_DowntimeData (RESID, DOWNTIME, UPTIME, TYPE, REASONID) values ('" + resourceid[j] + "','" + currentTime + "', 0, 1, -1)";
/* 1193 */                     AMConnectionPool.executeUpdateStmt(insertQuery);
/* 1194 */                     EnterpriseUtil.addUpdateQueryToFile(insertQuery);
/*      */                   }
/*      */                 }
/* 1197 */                 AMLog.debug("UNMANAGED TIME: For the Monitor : " + resourceid[j] + " started @ " + FormatUtil.formatDT(new StringBuilder().append("").append(select.getLong("DOWNTIME")).toString()) + " and completed @ " + FormatUtil.formatDT(new StringBuilder().append("").append(currentTime).toString()) + " and took " + (currentTime - select.getLong("DOWNTIME")) + " ms");
/*      */               }
/*      */               
/* 1200 */               EnterpriseUtil.addUpdateQueryToFile("delete from AM_UnManagedNodes where resid='" + resourceid[j] + "'");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exx)
/*      */     {
/* 1210 */       exx.printStackTrace();
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1215 */       stmt.executeBatch();
/* 1216 */       stmt.clearBatch();
/* 1217 */       stmt.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1221 */       System.out.println("SOME PROBLEM IN UPDATING THE MANAGED NODES");
/*      */     }
/*      */     
/* 1224 */     DataCollectionControllerUtil.setUnmanaged_nodes(DataCollectionControllerUtil.getUnManagedNodes());
/* 1225 */     DataCollectionControllerUtil.setManaged_nodes(DataCollectionControllerUtil.getManagedNodes());
/* 1226 */     if ((request.getAttribute("extramonitors") != null) && (String.valueOf(request.getAttribute("extramonitors")).equals("set")))
/*      */     {
/* 1228 */       return new ActionForward("/index.do", true);
/*      */     }
/*      */     
/* 1231 */     if ((request.getParameter("haid") != null) && (!"".equals(request.getParameter("haid").trim())))
/*      */     {
/* 1233 */       String haid = request.getParameter("haid");
/* 1234 */       return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication&fromwhere=managemonitorgroups");
/*      */     }
/*      */     
/* 1237 */     return new ActionForward("/showresource.do?method=showMonitorGroupView&fromwhere=managemonitorgroups&retaintree=" + retaintree + "&apimessage=" + message, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteMonitorGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1251 */       Properties p = new Properties();
/* 1252 */       String group = request.getParameter("group");
/* 1253 */       String unopengroups = request.getParameter("unopengroups");
/* 1254 */       ManagedApplication mo = new ManagedApplication();
/* 1255 */       String message = "";
/* 1256 */       if ((request.getParameter("choosedeleteoption") != null) && (request.getParameter("choosedeleteoption").equals("true")))
/*      */       {
/* 1258 */         String mos = request.getParameter("select");
/* 1259 */         if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/* 1260 */           Vector<String> vresid = new Vector();
/* 1261 */           StringTokenizer st = new StringTokenizer(unopengroups, "|");
/* 1262 */           String str = "";
/* 1263 */           while (st.hasMoreElements()) {
/* 1264 */             str = st.nextToken();
/* 1265 */             if ("-1".equals(str)) {
/* 1266 */               ParentChildRelationalUtil.getUncategorizedMonitors(vresid);
/*      */             }
/*      */             else {
/* 1269 */               ParentChildRelationalUtil.getAllChildMapper(vresid, str, true);
/*      */             }
/*      */           }
/* 1272 */           StringBuffer sb = new StringBuffer();
/* 1273 */           if (vresid.size() > 0) {
/* 1274 */             Iterator<String> it = vresid.iterator();
/* 1275 */             while (it.hasNext()) {
/* 1276 */               sb.append("|" + (String)it.next());
/*      */             }
/* 1278 */             mos = mos + sb.toString();
/*      */           }
/*      */         }
/* 1281 */         request.setAttribute("select", mos);
/* 1282 */         return new ActionForward("/jsp/deleteMonitorGroup.jsp");
/*      */       }
/* 1284 */       request.setAttribute("fromwhere", "allmonitorgroups");
/*      */       
/* 1286 */       String test = request.getParameter("select");
/* 1287 */       StringTokenizer tok = new StringTokenizer(test, "|");
/* 1288 */       int residsize = tok.countTokens();
/* 1289 */       String[] resourceid = new String[residsize];
/* 1290 */       int count = 0;
/* 1291 */       while (tok.hasMoreTokens())
/*      */       {
/* 1293 */         resourceid[count] = tok.nextToken();
/* 1294 */         count++;
/*      */       }
/*      */       try
/*      */       {
/* 1298 */         ArrayList MonitorGroups = new ArrayList();
/* 1299 */         ArrayList MonitorstoDelete = new ArrayList();
/* 1300 */         ArrayList MGtoDelete = new ArrayList();
/* 1301 */         ArrayList MonitorstoDelete_TYPE = new ArrayList();
/* 1302 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 1304 */           Object[] ob = ManagedAPICallUtil.maptoManagedServers(resourceid, true);
/* 1305 */           String adminhaid = (String)ob[0];
/* 1306 */           HashMap<String, StringBuilder> managedresourcelist = (HashMap)ob[1];
/*      */           
/* 1308 */           if (request.getParameter("todelete") == null)
/*      */           {
/* 1310 */             message = ManagedAPICallUtil.callManagedServers(managedresourcelist, "DeleteMonitor", true, true);
/*      */           }
/*      */           else
/*      */           {
/* 1314 */             message = ManagedAPICallUtil.callManagedServers(managedresourcelist, "DeleteMonitor", true, false);
/*      */           }
/* 1316 */           if ((adminhaid != null) && (adminhaid.length() > 3))
/*      */           {
/* 1318 */             resourceid = adminhaid.split(",");
/*      */           }
/*      */           else
/*      */           {
/* 1322 */             String retaintree = "";
/* 1323 */             if (request.getParameter("retaintree") != null)
/*      */             {
/* 1325 */               retaintree = request.getParameter("retaintree");
/* 1326 */               request.setAttribute("retaintree", retaintree);
/*      */             }
/*      */             
/* 1329 */             request.setAttribute("fromwhere", "monitorgroupview");
/*      */             
/* 1331 */             request.setAttribute("fromwhere", "monitorgroupview");
/* 1332 */             if (request.getParameter("todelete") != null)
/*      */             {
/* 1334 */               return new ActionForward("/jsp/deleteMonitorGroup.jsp?todelete=afterdeleting&apimessage=" + message);
/*      */             }
/*      */             
/*      */ 
/* 1338 */             return new ActionForward("/jsp/deleteMonitorGroup.jsp?todelete=afterdeletingMGs&apimessage=" + message);
/*      */           }
/*      */         }
/*      */         
/* 1342 */         for (int i = 0; i < resourceid.length; i++)
/*      */         {
/* 1344 */           String query1 = "select RESOURCEID,TYPE from AM_ManagedObject where RESOURCEID=" + resourceid[i];
/* 1345 */           ArrayList rows = mo.getRows(query1);
/* 1346 */           if ((rows.size() > 0) && (rows.get(0) != null) && (((ArrayList)rows.get(0)).get(1) != null))
/*      */           {
/* 1348 */             String TYPE = (String)((ArrayList)rows.get(0)).get(1);
/* 1349 */             if (TYPE.equals("HAI"))
/*      */             {
/* 1351 */               MGtoDelete.add(resourceid[i].trim());
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 1356 */               MonitorstoDelete.add(resourceid[i].trim());
/* 1357 */               MonitorstoDelete_TYPE.add(TYPE);
/*      */             }
/*      */           }
/*      */         }
/* 1361 */         if ((resourceid != null) && (request.getParameter("todelete") == null))
/*      */         {
/* 1363 */           for (int i = 0; i < MonitorstoDelete.size(); i++)
/*      */           {
/* 1365 */             String resourceidformonitor = (String)MonitorstoDelete.get(i);
/* 1366 */             DataCollectionDBUtil.deleteMonitor(new String[] { resourceidformonitor }, p, "All");
/*      */           }
/* 1368 */           request.setAttribute("todelete", "onlymonitors");
/*      */           
/*      */ 
/* 1371 */           if (EnterpriseUtil.isManagedServer()) {
/*      */             try
/*      */             {
/* 1374 */               File commands = new File(EnterpriseUtil.deleteDetailsFilePath);
/* 1375 */               commands.createNewFile();
/* 1376 */               Properties prop = new Properties();
/*      */               try
/*      */               {
/* 1379 */                 prop.load(new FileInputStream(commands));
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/* 1383 */                 ex.printStackTrace();
/*      */               }
/* 1385 */               String id = "";
/* 1386 */               if (prop.containsKey(EnterpriseUtil.deleteMonitorsKey))
/*      */               {
/* 1388 */                 id = prop.getProperty(EnterpriseUtil.deleteMonitorsKey) + ",";
/*      */               }
/* 1390 */               for (int i = 0; i < resourceid.length; i++)
/*      */               {
/* 1392 */                 id = id + resourceid[i] + ",";
/*      */               }
/* 1394 */               prop.setProperty(EnterpriseUtil.deleteMonitorsKey, id.substring(0, id.length() - 1));
/* 1395 */               prop.store(new FileOutputStream(commands), "");
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 1399 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1409 */         String retaintree = "";
/* 1410 */         if (request.getParameter("retaintree") != null)
/*      */         {
/* 1412 */           retaintree = request.getParameter("retaintree");
/* 1413 */           request.setAttribute("retaintree", retaintree);
/*      */         }
/* 1415 */         if (MGtoDelete.size() > 0)
/*      */         {
/* 1417 */           String[] MGs = new String[MGtoDelete.size()];
/* 1418 */           for (int j = 0; j < MGtoDelete.size(); j++)
/*      */           {
/* 1420 */             MGs[j] = ((String)MGtoDelete.get(j));
/*      */           }
/* 1422 */           request.setAttribute("selectresids", MGs);
/* 1423 */           request.setAttribute("fromwhere", "monitorgroupview");
/* 1424 */           return new ActionForward("/manageApplications.do?method=delete");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1429 */         if ((request.getParameter("haid") != null) && (!"".equals(request.getParameter("haid").trim())))
/*      */         {
/* 1431 */           String haid = request.getParameter("haid");
/* 1432 */           return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication&fromwhere=managemonitorgroups", true);
/*      */         }
/*      */         
/* 1435 */         return new ActionForward("/showresource.do?method=showMonitorGroupView&fromwhere=deletemonitorsonly&retaintree=" + retaintree, true);
/*      */ 
/*      */ 
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/*      */ 
/*      */ 
/* 1443 */         e.printStackTrace();
/*      */         
/*      */ 
/*      */ 
/* 1447 */         if ((request.getParameter("haid") != null) && (!"".equals(request.getParameter("haid").trim())))
/*      */         {
/* 1449 */           String haid = request.getParameter("haid");
/* 1450 */           return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication&fromwhere=managemonitorgroups");
/*      */         }
/*      */         
/* 1453 */         return new ActionForward("/showresource.do?method=showMonitorGroupView&fromwhere=managemonitorgroups", true);
/*      */       }
/*      */       
/*      */ 
/* 1457 */       return null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1456 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward alterActionsforMG(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1466 */     String resid = "";
/* 1467 */     String[] resourceid = null;
/* 1468 */     if ((request.getParameter("isAPI") != null) && (request.getParameter("isAPI").equals("true")))
/*      */     {
/* 1470 */       resid = request.getParameter("selectid");
/* 1471 */       resourceid = resid.split(",");
/*      */     }
/*      */     else
/*      */     {
/* 1475 */       resourceid = request.getParameterValues("select");
/*      */     }
/*      */     
/* 1478 */     String unopengroups = request.getParameter("unopengroups");
/*      */     
/* 1480 */     String actionstatus = "1";
/* 1481 */     String alteraction = request.getParameter("alteraction");
/* 1482 */     String actionmessage = "enableactions";
/* 1483 */     String retaintree = "";
/* 1484 */     if (request.getParameter("retaintree") != null)
/*      */     {
/* 1486 */       retaintree = request.getParameter("retaintree");
/*      */     }
/* 1488 */     if ((alteraction != null) && (alteraction.equals("disable")))
/*      */     {
/* 1490 */       actionstatus = "0";
/* 1491 */       actionmessage = "disableactions";
/*      */     }
/*      */     try
/*      */     {
/* 1495 */       AMConnectionPool.getInstance();Statement stmt = AMConnectionPool.getConnection().createStatement();
/* 1496 */       if (resourceid != null)
/*      */       {
/* 1498 */         if ((unopengroups != null) && (unopengroups.length() > 1)) {
/* 1499 */           resourceid = getUnopenChidresid(resourceid, unopengroups);
/*      */         }
/* 1501 */         for (int i = 0; i < resourceid.length; i++) {
/*      */           try
/*      */           {
/* 1504 */             stmt.addBatch("update AM_ManagedObject set ACTIONSTATUS=" + actionstatus + " where RESOURCEID=" + resourceid[i].trim());
/* 1505 */             EnterpriseUtil.addUpdateQueryToFile("update AM_ManagedObject set ACTIONSTATUS=" + actionstatus + " where RESOURCEID=" + resourceid[i].trim());
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/* 1509 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1516 */         stmt.executeBatch();
/* 1517 */         stmt.clearBatch();
/* 1518 */         stmt.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1522 */         System.out.println("SOME PROBLEM IN UPDATING THE Action status for the Monitors");
/* 1523 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1528 */       ex.printStackTrace();
/*      */     }
/* 1530 */     if (request.getParameter("haid") != null)
/*      */     {
/* 1532 */       String haid = request.getParameter("haid");
/* 1533 */       return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication&fromwhere=" + actionmessage);
/*      */     }
/*      */     
/* 1536 */     return new ActionForward("/showresource.do?method=showMonitorGroupView&fromwhere=" + actionmessage + "&retaintree=" + retaintree, true);
/*      */   }
/*      */   
/*      */   private Vector<String> it360LicenseViolationCheck(String[] resourceidsArray, String resid) {
/* 1540 */     Vector<String> toReturn = new Vector();
/* 1541 */     if (((resourceidsArray != null) && (resourceidsArray.length > 0)) || ((resid != null) && (!resid.trim().equals(""))))
/*      */     {
/* 1543 */       String resourceIds = "";
/* 1544 */       if ((resourceidsArray != null) && (resourceidsArray.length > 0))
/*      */       {
/* 1546 */         resourceIds = Arrays.asList(resourceidsArray).toString();
/* 1547 */         resourceIds = resourceIds.substring(1, resourceIds.length() - 1);
/*      */       }
/* 1549 */       else if ((resid != null) && (!resid.trim().equals("")))
/*      */       {
/* 1551 */         resourceIds = resid;
/*      */       }
/* 1553 */       if ((resourceIds != null) && (!resourceIds.trim().equals("")))
/*      */       {
/* 1555 */         String unmanagedResourceIdsQuery = "select resid,TYPE from AM_ManagedObject mo  join  AM_UnManagedNodes umo on umo.resid=mo.RESOURCEID where resid in  (" + resourceIds + ")";
/* 1556 */         ResultSet rs = null;
/* 1557 */         Properties unmanagedResourceProps = new Properties();
/*      */         try
/*      */         {
/* 1560 */           rs = AMConnectionPool.executeQueryStmt(unmanagedResourceIdsQuery);
/* 1561 */           while (rs.next())
/*      */           {
/* 1563 */             String monType = rs.getString("TYPE");
/* 1564 */             String str = unmanagedResourceProps.getProperty(monType);
/* 1565 */             int monCount = (str != null) && (!str.trim().equals("")) ? Integer.parseInt(str) : 0;
/* 1566 */             monCount++;
/* 1567 */             unmanagedResourceProps.setProperty(monType, Integer.toString(monCount));
/*      */           }
/*      */           
/* 1570 */           if ((unmanagedResourceProps != null) && (!unmanagedResourceProps.isEmpty()))
/*      */           {
/* 1572 */             toReturn = ClientDBUtil.getLicenseViolationMessages(unmanagedResourceProps, "MANAGE");
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1578 */           e.printStackTrace();
/*      */         }
/*      */         finally
/*      */         {
/* 1582 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1590 */     return toReturn;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DataCollectionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */