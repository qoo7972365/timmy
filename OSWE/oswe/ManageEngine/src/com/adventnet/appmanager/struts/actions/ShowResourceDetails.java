/*       */ package com.adventnet.appmanager.struts.actions;
/*       */ 
/*       */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*       */ import com.adventnet.appmanager.client.views.ViewsCreator;
/*       */ import com.adventnet.appmanager.db.AMConnectionPool;
/*       */ import com.adventnet.appmanager.db.DBQueryUtil;
/*       */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*       */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*       */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*       */ import com.adventnet.appmanager.fault.DependentDeviceUtil;
/*       */ import com.adventnet.appmanager.fault.FaultUtil;
/*       */ import com.adventnet.appmanager.fault.RuleAnalyser;
/*       */ import com.adventnet.appmanager.logging.AMLog;
/*       */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*       */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*       */ import com.adventnet.appmanager.server.dao.UrlConfiguration;
/*       */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*       */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*       */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*       */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*       */ import com.adventnet.appmanager.server.framework.datacollection.AMScriptDataCollector;
/*       */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*       */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*       */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*       */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*       */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*       */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*       */ import com.adventnet.appmanager.util.ChildMOHandler;
/*       */ import com.adventnet.appmanager.util.Constants;
/*       */ import com.adventnet.appmanager.util.DBUtil;
/*       */ import com.adventnet.appmanager.util.DashboardUtil;
/*       */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*       */ import com.adventnet.appmanager.util.ExtProdUtil;
/*       */ import com.adventnet.appmanager.util.FormatUtil;
/*       */ import com.adventnet.appmanager.util.MGActionNotifier;
/*       */ import com.adventnet.appmanager.util.OEMUtil;
/*       */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*       */ import com.adventnet.appmanager.util.ReportUtil;
/*       */ import com.adventnet.appmanager.util.SupportZipUtil;
/*       */ import com.adventnet.appmanager.util.UserSessionHandler;
/*       */ import com.adventnet.appmanager.util.UserUtil;
/*       */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*       */ import com.adventnet.appmanager.webclient.util.SavePreferencesAction;
/*       */ import com.adventnet.awolf.data.support.StackedBarSupport;
/*       */ import com.adventnet.nms.topodb.ManagedObject;
/*       */ import com.adventnet.nms.topodb.TopoAPI;
/*       */ import com.adventnet.nms.util.NmsUtil;
/*       */ import com.adventnet.tools.prevalent.Wield;
/*       */ import com.manageengine.apminsight.server.dao.ApplicationInfo;
/*       */ import com.manageengine.apminsight.server.db.APMInsightDBUtil;
/*       */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*       */ import com.manageengine.appmanager.utils.client.ClientAuditUtil;
/*       */ import java.io.File;
/*       */ import java.io.FileInputStream;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.sql.ResultSet;
/*       */ import java.sql.SQLException;
/*       */ import java.util.ArrayList;
/*       */ import java.util.Arrays;
/*       */ import java.util.Collections;
/*       */ import java.util.Date;
/*       */ import java.util.Enumeration;
/*       */ import java.util.HashMap;
/*       */ import java.util.HashSet;
/*       */ import java.util.Hashtable;
/*       */ import java.util.Iterator;
/*       */ import java.util.LinkedHashMap;
/*       */ import java.util.List;
/*       */ import java.util.Map;
/*       */ import java.util.Properties;
/*       */ import java.util.Set;
/*       */ import java.util.StringTokenizer;
/*       */ import java.util.Vector;
/*       */ import javax.servlet.http.Cookie;
/*       */ import javax.servlet.http.HttpServletRequest;
/*       */ import javax.servlet.http.HttpServletResponse;
/*       */ import javax.servlet.http.HttpSession;
/*       */ import org.apache.commons.logging.Log;
/*       */ import org.apache.struts.action.ActionError;
/*       */ import org.apache.struts.action.ActionErrors;
/*       */ import org.apache.struts.action.ActionForm;
/*       */ import org.apache.struts.action.ActionForward;
/*       */ import org.apache.struts.action.ActionMapping;
/*       */ import org.apache.struts.action.ActionMessage;
/*       */ import org.apache.struts.action.ActionMessages;
/*       */ import org.htmlparser.util.Translate;
/*       */ import org.jfree.data.general.DefaultPieDataset;
/*       */ import org.json.JSONArray;
/*       */ import org.json.JSONObject;
/*       */ 
/*       */ public final class ShowResourceDetails extends org.apache.struts.actions.DispatchAction
/*       */ {
/*       */   private ManagedApplication mo;
/*       */   MGActionNotifier notifyConsole;
/*    96 */   private static Hashtable jbosserrorkeys = new Hashtable(3);
/*    97 */   public static ClientAuditUtil cliAuditUtil = new ClientAuditUtil();
/*       */   private final String INPROGRESS = "DataCollection in Progress. Please wait.";
/*       */   private final String STARTED = "DataCollection started.";
/*       */   private final String DOWN = "Websphere monitor is down.";
/*   101 */   private static String types = Constants.resourceTypes;
/*   102 */   private static String reason1 = FormatUtil.getString("jboss.datacollection.failed.reason.1", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*   103 */   private static String reason5 = FormatUtil.getString("jboss.datacollection.failed.reason.5", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*       */   private Log log1;
/*       */   
/*       */   static {
/*   107 */     jbosserrorkeys.put("jndi not bound", reason1);
/*   108 */     jbosserrorkeys.put("HostUnavailable", "jboss.datacollection.failed.reason.2");
/*   109 */     jbosserrorkeys.put("Unable to get MBeanServer", "jboss.datacollection.failed.reason.3");
/*   110 */     jbosserrorkeys.put("http-invoker.sar not deployed", "jboss.datacollection.failed.reason.4");
/*   111 */     jbosserrorkeys.put("jbossagent.sar not deployed", reason5);
/*   112 */     jbosserrorkeys.put("service not running", "jboss.datacollection.failed.reason.6");
/*   113 */     jbosserrorkeys.put("jboss.sslhandshake.failed", "jboss.sslhandshake.failed");
/*   114 */     jbosserrorkeys.put("org.jboss.naming.HttpNamingContextFactory", "jboss.401.jar.missing");
/*   115 */     jbosserrorkeys.put("Select proper JBoss version", "jboss.4.version.wrong");
/*   116 */     jbosserrorkeys.put("jboss.auth.failed", "jboss.auth.failed");
/*       */   }
/*       */   
/*       */   public ShowResourceDetails()
/*       */   {
/*    94 */     this.mo = new ManagedApplication();
/*    95 */     this.notifyConsole = MGActionNotifier.getInstance();
/*       */     
/*       */ 
/*    98 */     this.INPROGRESS = "DataCollection in Progress. Please wait.";
/*    99 */     this.STARTED = "DataCollection started.";
/*   100 */     this.DOWN = "Websphere monitor is down.";
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   124 */     this.log1 = org.apache.commons.logging.LogFactory.getLog("ShowResourceDetails");
/*       */   }
/*       */   
/*       */   public ActionForward editMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   130 */     String haid = request.getParameter("haid");
/*   131 */     String applicationName = request.getParameter("name");
/*   132 */     String resourceid = request.getParameter("resourceid");
/*   133 */     String resourcetype = request.getParameter("type");
/*   134 */     String path = "";
/*       */     
/*       */ 
/*   137 */     String haid_str = "";
/*       */     try {
/*   139 */       Integer.parseInt(haid);
/*   140 */       haid_str = "&haid=" + haid;
/*       */     }
/*       */     catch (Exception e) {}
/*   143 */     request.setAttribute("HelpKey", "Monitors Service Details");
/*   144 */     path = "/jsp/configure_resource.jsp?resid=" + resourceid + haid_str;
/*   145 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */   public ActionForward monitorErrors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*   150 */     String type = "all";
/*   151 */     String enableErrorMail = "false";
/*   152 */     String errorpollCount = "3";
/*       */     try
/*       */     {
/*   155 */       enableErrorMail = request.getParameter("enableErrorMail");
/*   156 */       errorpollCount = request.getParameter("errorpollCount");
/*   157 */       if ((enableErrorMail == null) || (enableErrorMail.equals("null")))
/*       */       {
/*   159 */         enableErrorMail = Constants.getSenderrormail();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*   164 */       if ((errorpollCount == null) || (errorpollCount.equals("null")) || (errorpollCount.equals("")))
/*       */       {
/*   166 */         errorpollCount = String.valueOf(Constants.getErrorPollCount());
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*   173 */       ex.printStackTrace();
/*       */     }
/*       */     
/*   176 */     Constants.saveChanges(enableErrorMail, errorpollCount);
/*       */     
/*   178 */     if (request.getParameter("type") != null)
/*       */     {
/*   180 */       if (request.getParameter("type").equals("error"))
/*       */       {
/*   182 */         type = "error";
/*       */       }
/*   184 */       if (request.getParameter("type").equals("information"))
/*       */       {
/*   186 */         type = "information";
/*       */       }
/*   188 */       if ("dcdelay".equals(request.getParameter("type"))) {
/*   189 */         type = "dcdelay";
/*       */       }
/*       */     }
/*   192 */     return new ActionForward("/jsp/monitorerrors.jsp?type=" + type);
/*       */   }
/*       */   
/*       */   public ActionForward showResourceMSSQL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   198 */     ArrayList listviewresourcetype = new ArrayList();
/*   199 */     listviewresourcetype = ((AMActionForm)form).getResourceTypes_listview(request);
/*   200 */     request.setAttribute("listviewresourcetype", listviewresourcetype);
/*       */     
/*   202 */     String customValue = request.getParameter("customValue");
/*       */     
/*   204 */     Hashtable listView = new Hashtable();
/*   205 */     ArrayList listViewSorted = new ArrayList();
/*   206 */     StringBuilder mssqlresid = new StringBuilder();
/*   207 */     Set<String> hostresid = new HashSet();
/*   208 */     ActionMessages messages = new ActionMessages();
/*   209 */     String type = "MSSQL-DB-server";
/*   210 */     int period = 1;
/*   211 */     String userName = request.getRemoteUser();
/*   212 */     long starttime = ReportUtilities.getStartTime(period);
/*   213 */     long endtime = ReportUtilities.getEndTime(period);
/*   214 */     request.setAttribute("startdate", new Date(starttime));
/*   215 */     request.setAttribute("endtime", new Date(endtime));
/*   216 */     Hashtable h = null;
/*   217 */     String owner = request.getRemoteUser();
/*   218 */     String role = "";
/*   219 */     HttpSession session = request.getSession();
/*   220 */     javax.servlet.ServletContext ctx = session.getServletContext();
/*   221 */     if (ClientDBUtil.isPrivilegedUser(request))
/*       */     {
/*   223 */       role = "operator";
/*       */     }
/*       */     else
/*       */     {
/*   227 */       role = "user";
/*       */     }
/*       */     
/*   230 */     String group = "Others";
/*       */     
/*   232 */     h = ReportUtilities.getAvailabilityHistoryByType(type, period, owner, role);
/*       */     
/*       */ 
/*       */ 
/*   236 */     request.setAttribute("downtime_report", h);
/*   237 */     if (period == 1) {
/*   238 */       request.setAttribute("type", "TIME");
/*       */     }
/*       */     else {
/*   241 */       request.setAttribute("type", "DATE");
/*       */     }
/*   243 */     long timeperiod = endtime - starttime;
/*       */     
/*   245 */     Hashtable toret = new Hashtable();
/*   246 */     Enumeration e = h.keys();
/*   247 */     while (e.hasMoreElements()) {
/*   248 */       String key = (String)e.nextElement();
/*   249 */       ArrayList l = (ArrayList)h.get(key);
/*   250 */       long total = 0L;
/*   251 */       long periods = timeperiod;
/*   252 */       int i = 0; for (int size = l.size(); i < size; i++) {
/*   253 */         Hashtable temp = (Hashtable)l.get(i);
/*   254 */         String status = (String)temp.get("STATUS");
/*   255 */         if (status.equals("NO_DC")) {
/*   256 */           long start = ((Date)temp.get("STARTTIME")).getTime();
/*   257 */           long end = ((Date)temp.get("ENDTIME")).getTime();
/*   258 */           periods -= end - start;
/*       */         }
/*   260 */         else if (status.equals("AVAILABALE")) {
/*   261 */           long start = ((Date)temp.get("STARTTIME")).getTime();
/*   262 */           long end = ((Date)temp.get("ENDTIME")).getTime();
/*   263 */           total += end - start;
/*       */         }
/*       */       }
/*   266 */       double per = total * 100L / periods;
/*       */       
/*   268 */       if (per > 100.0D)
/*       */       {
/*   270 */         per = 100.0D;
/*       */       }
/*   272 */       toret.put(key, Double.valueOf(per));
/*   273 */       int aper = (int)per;
/*   274 */       Properties existing = (Properties)listView.get(key.trim());
/*   275 */       if (existing == null)
/*       */       {
/*   277 */         existing = new Properties();
/*       */       }
/*   279 */       existing.setProperty("AVAILPER", Integer.toString(aper));
/*   280 */       listView.put(key.trim(), existing);
/*       */     }
/*       */     
/*       */ 
/*   284 */     request.setAttribute("AVAILABILITY", toret);
/*       */     
/*       */ 
/*       */ 
/*   288 */     String to_jsp = "/jsp/mssql/sqlserver.jsp";
/*   289 */     ResultSet rs = null;
/*   290 */     StringBuilder resIds = new StringBuilder();
/*   291 */     String qryAppend = "";
/*   292 */     Vector resIds_vector = new Vector();
/*   293 */     if (ClientDBUtil.isPrivilegedUser(request))
/*       */     {
/*   295 */       if (Constants.isUserResourceEnabled()) {
/*   296 */         String loginUserid = Constants.getLoginUserid(request);
/*   297 */         qryAppend = " and AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*       */       } else {
/*   299 */         resIds_vector = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*       */       }
/*       */     }
/*   302 */     if ((resIds_vector != null) && (resIds_vector.size() != 0))
/*       */     {
/*   304 */       for (int i = 0; i < resIds_vector.size(); i++)
/*       */       {
/*   306 */         resIds.append((String)resIds_vector.get(i)).append(",");
/*       */       }
/*   308 */       resIds.deleteCharAt(resIds.length() - 1);
/*   309 */       qryAppend = "and AM_ManagedObject.RESOURCEID in (" + resIds + ")";
/*       */     }
/*       */     try {
/*   312 */       if ((!request.isUserInRole("OPERATOR")) || (resIds_vector.size() != 0))
/*       */       {
/*   314 */         String dataTable = "";
/*   315 */         String qryCon = "";
/*   316 */         String s12 = "";
/*   317 */         String s14 = "";
/*   318 */         String customField = "";
/*   319 */         String value = "";
/*   320 */         String query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*   321 */         if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */         {
/*   323 */           query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION,AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST where CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */         }
/*   325 */         if ((customValue != null) && (!customValue.equals("null"))) {
/*       */           try
/*       */           {
/*   328 */             if (customValue.contains("$")) {
/*   329 */               customField = customValue.substring(0, customValue.indexOf("$"));
/*   330 */               value = customValue.substring(customValue.indexOf("$") + 1);
/*       */             }
/*   332 */             if (customField.indexOf("SYSTEMDATA") != -1)
/*       */             {
/*   334 */               query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE join AM_MYFIELDS_SYSTEMDATA on AM_ManagedObject.RESOURCEID=AM_MYFIELDS_SYSTEMDATA.RESOURCEID and AM_MYFIELDS_SYSTEMDATA." + customField + "='" + value + "' where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*   335 */               if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */               {
/*   337 */                 query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST,AM_MYFIELDS_SYSTEMDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_SYSTEMDATA.RESOURCEID and AM_MYFIELDS_SYSTEMDATA." + customField + "='" + value + "' AND CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */ 
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*   343 */             else if (customField.indexOf("USERDATA") != -1)
/*       */             {
/*   345 */               query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE join AM_MYFIELDS_USERDATA on AM_ManagedObject.RESOURCEID=AM_MYFIELDS_USERDATA.RESOURCEID and AM_MYFIELDS_USERDATA." + customField + "='" + value + "' where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*   346 */               if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */               {
/*   348 */                 query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST,AM_MYFIELDS_USERDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_USERDATA.RESOURCEID and AM_MYFIELDS_USERDATA." + customField + "='" + value + "' AND CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */ 
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*   354 */             else if (customField.equals("LOCATION_NAME"))
/*       */             {
/*   356 */               query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE join AM_MYFIELDS_ENTITYDATA on AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID AND AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' JOIN AM_MYFIELDS_LOCATION ON AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and LOCATIONID=" + value + " where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*       */               
/*   358 */               if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */               {
/*   360 */                 query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST,AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID AND AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' AND AM_MYFIELDS_ENTITYDATA.VALUEID=AM_MYFIELDS_LOCATION.LOCATIONID and LOCATIONID=" + value + " AND CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */               }
/*       */               
/*       */             }
/*   364 */             else if (customField.equals("USERNAME"))
/*       */             {
/*   366 */               query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE join AM_MYFIELDS_ENTITYDATA on AM_ManagedObject.RESOURCEID==AM_MYFIELDS_ENTITYDATA.RESOURCEID and DATATABLE='AM_UserPasswordTable' JOIN AM_UserPasswordTable ON AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and USERID=" + value + " where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*       */               
/*   368 */               if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */               {
/*   370 */                 query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST,AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_ENTITYDATA.RESOURCEID AND DATATABLE='AM_UserPasswordTable' AND AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and USERID=" + value + " AND CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */               }
/*       */               
/*       */             }
/*   374 */             else if (customField.equals("VALUEID")) {
/*   375 */               query = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from AM_ManagedObject join CollectData on CollectData.RESOURCENAME=AM_ManagedObject.RESOURCENAME and CollectData.COMPONENTNAME='MSSQL' left outer join CollectData as CollectData1 on CollectData1.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData1.COMPONENTNAME='Host' left outer join AM_ManagedObject as Host on Host.RESOURCENAME=CollectData1.RESOURCENAME and Host.TYPE=CollectData1.RESOURCETYPE join AM_MYFIELDS_LABELDATA on AM_ManagedObject.RESOURCEID=AM_MYFIELDS_LABELDATA.RESOURCEID and VALUEID=" + value + " where AM_ManagedObject.TYPE='MSSQL-DB-server'" + qryAppend + " order by AM_ManagedObject.DISPLAYNAME";
/*   376 */               if ((request.getParameter("search") != null) && (request.getParameter("search").equals("true")))
/*       */               {
/*   378 */                 query = "select MSSQL.RESOURCEID, MSSQL.DISPLAYNAME, MSSQL.DESCRIPTION, MSSQL.RESOURCENAME, MSSQL.TARGETADDRESS, HOST.RESOURCEID as HOSTRESOURCEID from (select RESOURCEID, AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.DESCRIPTION, AM_ManagedObject.RESOURCENAME, TARGETADDRESS from CollectData, AM_ManagedObject where AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME and TYPE=RESOURCETYPE and TYPE='MSSQL-DB-server' and AM_ManagedObject.RESOURCEID in (" + request.getAttribute("searchresourceids") + ") ) MSSQL, CollectData, AM_ManagedObject as HOST,AM_MYFIELDS_LABELDATA where AM_ManagedObject.RESOURCEID=AM_MYFIELDS_LABELDATA.RESOURCEID and VALUEID=" + value + " AND CollectData.RESOURCENAME=HOST.RESOURCENAME and MSSQL.TARGETADDRESS=CollectData.TARGETADDRESS and CollectData.COMPONENTNAME='Host' " + qryAppend + " order by DISPLAYNAME";
/*       */               }
/*       */               
/*       */             }
/*       */             
/*       */ 
/*       */           }
/*       */           catch (Exception exception2)
/*       */           {
/*   387 */             exception2.printStackTrace();
/*       */           }
/*       */         }
/*   390 */         rs = AMConnectionPool.executeQueryStmt(query);
/*   391 */         int count = 0;
/*   392 */         int propNumbStart = 0;
/*   393 */         String noOfRecordsPerPage = request.getParameter("viewLength");
/*   394 */         SavePreferencesAction spa = new SavePreferencesAction();
/*       */         
/*   396 */         if (noOfRecordsPerPage == null) {
/*   397 */           if (session.getAttribute("sql_viewlength") != null) {
/*   398 */             noOfRecordsPerPage = (String)session.getAttribute("sql_viewlength");
/*       */           } else {
/*   400 */             noOfRecordsPerPage = "10";
/*   401 */             session.setAttribute("sql_viewlength", noOfRecordsPerPage);
/*   402 */             spa.savealarmviewlength(request);
/*       */           }
/*       */         } else {
/*   405 */           session.setAttribute("sql_viewlength", noOfRecordsPerPage);
/*   406 */           spa.savealarmviewlength(request);
/*       */         }
/*   408 */         request.setAttribute("viewLength", Integer.valueOf(Integer.parseInt(noOfRecordsPerPage)));
/*   409 */         int pageNumber = 1;
/*   410 */         if (request.getParameter("page") != null) {
/*   411 */           pageNumber = Integer.parseInt(request.getParameter("page"));
/*       */         }
/*   413 */         propNumbStart = (pageNumber - 1) * Integer.parseInt(noOfRecordsPerPage) + 1;
/*   414 */         while (rs.next()) {
/*   415 */           count++;
/*   416 */           Properties existing = (Properties)listView.get(rs.getString("RESOURCEID").trim());
/*   417 */           if (existing == null)
/*       */           {
/*   419 */             existing = new Properties();
/*       */           }
/*   421 */           existing.setProperty("RESOURCEID", rs.getString("RESOURCEID"));
/*   422 */           existing.setProperty("RESOURCENAME", rs.getString("RESOURCENAME"));
/*   423 */           existing.setProperty("TARGETADDRESS", rs.getString("TARGETADDRESS"));
/*   424 */           existing.setProperty("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*   425 */           existing.setProperty("DESCRIPTION", rs.getString("DESCRIPTION"));
/*   426 */           listView.put(rs.getString("RESOURCEID").trim(), existing);
/*   427 */           if ((count >= propNumbStart) && (count <= propNumbStart + Integer.parseInt(noOfRecordsPerPage) - 1)) {
/*   428 */             mssqlresid.append(rs.getString("RESOURCEID")).append(",");
/*   429 */             if (rs.getString("HOSTRESOURCEID") != null) {
/*   430 */               existing.setProperty("HOSTID", rs.getString("HOSTRESOURCEID"));
/*   431 */               hostresid.add(rs.getString("HOSTRESOURCEID"));
/*       */             }
/*   433 */             listViewSorted.add(existing);
/*       */           }
/*       */         }
/*   436 */         request.setAttribute("COUNT", Integer.valueOf(count));
/*   437 */         AMConnectionPool.closeStatement(rs);
/*       */         
/*       */ 
/*   440 */         Hashtable hostinfo = new Hashtable();
/*   441 */         StringBuilder hostid = new StringBuilder();
/*       */         
/*       */         try
/*       */         {
/*   445 */           if (!hostresid.isEmpty())
/*       */           {
/*   447 */             Iterator<String> i = hostresid.iterator();
/*   448 */             while (i.hasNext())
/*       */             {
/*   450 */               hostid.append((String)i.next()).append(",");
/*       */             }
/*   452 */             if (hostid.length() != 0)
/*       */             {
/*   454 */               query = "select HostCpuMemDataCollected.RESOURCEID,ISNULL(PHYMEMUTIL,0) as PHYMEMUTIL,ISNULL(CPUUTIL,0) as CPUUTIL from (select RESOURCEID,max(COLLECTIONTIME) as COLLECTIONTIME from HostCpuMemDataCollected where HostCpuMemDataCollected.RESOURCEID in (" + hostid.substring(0, hostid.length() - 1) + ") group by RESOURCEID) as HostCpuMemDataCollected_MAX,HostCpuMemDataCollected where HostCpuMemDataCollected_MAX.RESOURCEID=HostCpuMemDataCollected.RESOURCEID and HostCpuMemDataCollected_MAX.COLLECTIONTIME=HostCpuMemDataCollected.COLLECTIONTIME";
/*   455 */               rs = AMConnectionPool.executeQueryStmt(query);
/*   456 */               while (rs.next())
/*       */               {
/*   458 */                 Properties p = new Properties();
/*   459 */                 p.put("HOSTMEMORY", rs.getString("PHYMEMUTIL"));
/*   460 */                 p.put("HOSTCPU", rs.getString("CPUUTIL"));
/*   461 */                 hostinfo.put(rs.getString("RESOURCEID"), p);
/*       */               }
/*   463 */               AMConnectionPool.closeStatement(rs);
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception exp)
/*       */         {
/*   469 */           exp.printStackTrace();
/*       */         }
/*       */         
/*       */ 
/*   473 */         if (mssqlresid.length() != 0)
/*       */         {
/*   475 */           query = "select AM_MSSQLBUFFERDETAILS.RESOURCEID ,AM_MSSQLBUFFERDETAILS.BUFFERHITRATIO, AM_MSSQLCACHEDETAILS.CACHEHITRATIO from AM_MSSQLBUFFERDETAILS, AM_MSSQLCACHEDETAILS, (select RESOURCEID,max(COLLECTIONTIME) as COLLECTIONTIME from AM_MSSQLBUFFERDETAILS where AM_MSSQLBUFFERDETAILS.RESOURCEID in (" + mssqlresid.substring(0, mssqlresid.length() - 1) + ") group by RESOURCEID) as MAXVALUE where AM_MSSQLCACHEDETAILS.RESOURCEID=AM_MSSQLBUFFERDETAILS.RESOURCEID and AM_MSSQLCACHEDETAILS.COLLECTIONTIME=AM_MSSQLBUFFERDETAILS.COLLECTIONTIME and  AM_MSSQLBUFFERDETAILS.RESOURCEID=MAXVALUE.RESOURCEID and AM_MSSQLBUFFERDETAILS.COLLECTIONTIME=MAXVALUE.COLLECTIONTIME";
/*   476 */           rs = AMConnectionPool.executeQueryStmt(query);
/*   477 */           while (rs.next())
/*       */           {
/*   479 */             Properties p = (Properties)listView.get(rs.getString("RESOURCEID"));
/*   480 */             p.put("BUFFERHITRATIOG", String.valueOf(rs.getInt("BUFFERHITRATIO")));
/*   481 */             p.put("CACHEHITRATIOG", String.valueOf(rs.getInt("CACHEHITRATIO")));
/*   482 */             if (p.getProperty("HOSTID") != null)
/*       */             {
/*   484 */               Properties p1 = (Properties)hostinfo.get(p.getProperty("HOSTID"));
/*   485 */               if (p1 != null)
/*       */               {
/*   487 */                 p.put("HOSTMEMORY", p1.getProperty("HOSTMEMORY"));
/*   488 */                 p.put("HOSTCPU", p1.getProperty("HOSTCPU"));
/*       */               }
/*       */             }
/*       */           }
/*   492 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */       }
/*   495 */       request.setAttribute("listView", listViewSorted);
/*       */       
/*   497 */       if (request.getParameter("fromwhere") != null)
/*       */       {
/*       */ 
/*   500 */         if (request.getParameter("fromwhere").equals("unmanagemonitors"))
/*       */         {
/*   502 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.unmanaged.datacollectionstops.text")));
/*       */         }
/*   504 */         else if (request.getParameter("fromwhere").equals("managemonitors"))
/*       */         {
/*   506 */           String messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*   507 */           if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*       */           {
/*   509 */             messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*       */           }
/*   511 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*       */         }
/*   513 */         if (request.getParameter("fromwhere").equals("bulkupdate"))
/*       */         {
/*   515 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.username.updated.text")));
/*       */         }
/*   517 */         else if (request.getParameter("fromwhere").equals("pollingMessage"))
/*       */         {
/*   519 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.polling.updated.text")));
/*       */         }
/*       */       }
/*   522 */       saveMessages(request, messages);
/*       */     }
/*       */     catch (Exception er) {
/*   525 */       er.printStackTrace();
/*       */     }
/*   527 */     request.setAttribute("customField", customValue);
/*   528 */     request.setAttribute("assignCustomValues", Boolean.valueOf(false));
/*   529 */     return new ActionForward(to_jsp);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public static Hashtable getAvailabilityHistoryByType(String type, int period, String owner, String role)
/*       */   {
/*   539 */     String query = "";
/*   540 */     if (!role.equals("operator"))
/*       */     {
/*   542 */       query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP='" + type + "'";
/*       */     }
/*       */     else
/*       */     {
/*   546 */       String condition = ReportUtilities.getQueryCondition("mo.RESOURCEID", owner);
/*   547 */       query = "select mo.resourceid,mo.displayname,mo.type from AM_ManagedObject mo,AM_ManagedResourceType mrt  where mo.TYPE=mrt.RESOURCETYPE and mrt.SUBGROUP NOT IN " + Constants.serverTypes + " AND mo.TYPE NOT LIKE '%OpManager%' and " + condition;
/*       */     }
/*   549 */     return getGenericAvailability(query, period, true);
/*       */   }
/*       */   
/*       */   private static Hashtable getGenericAvailability(String query, int period, boolean addType)
/*       */   {
/*   554 */     toret = new Hashtable();
/*   555 */     ResultSet rs = null;
/*       */     try {
/*   557 */       rs = AMConnectionPool.executeQueryStmt(query);
/*   558 */       while (rs.next()) {
/*   559 */         int resid = rs.getInt("resourceid");
/*   560 */         String dispname = rs.getString("displayname");
/*       */         
/*   562 */         if (addType)
/*       */         {
/*   564 */           String type = rs.getString("type");
/*   565 */           toret.put(Integer.toString(resid), ReportUtilities.getAvailabilityHistory(resid, period));
/*       */         }
/*       */         else
/*       */         {
/*   569 */           toret.put(Integer.toString(resid), ReportUtilities.getAvailabilityHistory(resid, period));
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   588 */       return toret;
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*   574 */       e.printStackTrace();
/*       */     }
/*       */     finally {
/*   577 */       if (rs != null) {
/*       */         try
/*       */         {
/*   580 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception e)
/*       */         {
/*   584 */           e.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward showdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*   595 */     response.setContentType("text/html;charset=utf-8");
/*   596 */     String resourceid = request.getParameter("resourceid");
/*   597 */     if ((Constants.isExtDeviceConfigured()) && (resourceid != null) && (DBUtil.isSite24x7Monitor(resourceid))) {
/*   598 */       return new ActionForward("/extDeviceAction.do?method=site24x7Reports&resourceid=" + resourceid);
/*       */     }
/*   600 */     showResourceTypes(mapping, form, request, response);
/*   601 */     String haid = request.getParameter("haid");
/*   602 */     String applicationName = request.getParameter("name");
/*   603 */     String opmanager = request.getParameter("toSend");
/*   604 */     String resourcetype = request.getParameter("type");
/*       */     
/*       */ 
/*   607 */     if (Constants.isIt360)
/*       */     {
/*   609 */       String resGroup = Constants.getResgroupForResourceType(resourcetype);
/*   610 */       if ((resGroup != null) && ((resGroup.equals("SYS")) || (resGroup.equals("VIR"))))
/*       */       {
/*   612 */         request.setAttribute("oldtab", "2");
/*       */       }
/*   614 */       else if ((resGroup != null) && (resGroup.equals("APPLICATION")))
/*       */       {
/*   616 */         request.setAttribute("oldtab", "3");
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*   621 */     String selectedscheme = "";
/*   622 */     if (request.getParameter("selectedscheme") != null)
/*       */     {
/*   624 */       selectedscheme = request.getParameter("selectedscheme");
/*   625 */       request.setAttribute("selectedscheme", selectedscheme);
/*       */     }
/*   627 */     String selectedSkin = "Grey";
/*   628 */     if (request.getParameter("selectedSkin") != null)
/*       */     {
/*   630 */       selectedSkin = request.getParameter("selectedSkin");
/*   631 */       request.setAttribute("selectedskin", selectedSkin);
/*       */     }
/*   633 */     ActionMessages messages = new ActionMessages();
/*   634 */     ActionErrors errors = new ActionErrors();
/*   635 */     if (request.getParameter("pollnow") != null)
/*       */     {
/*   637 */       if ((resourcetype.equals("RBMURL")) || (resourcetype.equals("RBM")))
/*       */       {
/*   639 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.rbm.pollnow.success.text")));
/*       */       }
/*       */       else
/*       */       {
/*   643 */         ArrayList agentRows = new ManagedApplication().getRows("select AGENTID from AM_RESOURCE_AGENT_MAPPING where RESOURCEID=" + resourceid + " and AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ")");
/*   644 */         if (agentRows.size() == 0)
/*       */         {
/*   646 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.commom.pollnow.success.text")));
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*   651 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.eumagent.pollnow.success.text")));
/*       */         }
/*       */       }
/*   654 */       saveMessages(request, messages);
/*       */     }
/*   656 */     ArrayList temprows = (ArrayList)request.getAttribute("resourcedetails_morow");
/*   657 */     if (temprows == null) {
/*   658 */       temprows = this.mo.getPropertiesList("select * from AM_ManagedObject where resourceid=" + resourceid);
/*       */     }
/*       */     
/*   661 */     if (temprows.size() == 0)
/*       */     {
/*   663 */       return new ActionForward("/showresource.do?method=showResourceTypes");
/*       */     }
/*   665 */     String fromwhere = request.getParameter("fromwhere");
/*   666 */     if (fromwhere != null)
/*       */     {
/*   668 */       if (((fromwhere.equals("managemonitors")) || (fromwhere.equals("unmanagemonitors")) || (fromwhere.equals("unmanageandresetmonitors"))) && (showMessage(request, messages)))
/*       */       {
/*   670 */         showMessage(request, messages);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*   695 */     String resourcename = request.getParameter("resourcename");
/*       */     
/*   697 */     String moname = request.getParameter("moname");
/*   698 */     String path = null;
/*   699 */     String original_type = "";
/*   700 */     int baseid = -1;
/*   701 */     String monitorname = null;
/*   702 */     if (temprows != null) {
/*   703 */       Properties p = (Properties)temprows.get(0);
/*   704 */       if (p != null) {
/*   705 */         monitorname = p.getProperty("DISPLAYNAME");
/*       */       }
/*       */     }
/*   708 */     if (monitorname == null) {
/*   709 */       monitorname = getMonitorName(resourceid);
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*   714 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*   715 */       String resourcetype_template = resourcetype + "-TEMPLATE";
/*       */       
/*       */ 
/*   718 */       String qry = "select basetype,RESOURCETYPEID,AMCREATED from AM_MONITOR_TYPES,AM_ManagedResourceType where typename='" + resourcetype + "' and RESOURCETYPEID=typeid";
/*   719 */       System.out.println("The template query===>" + qry);
/*   720 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*   721 */       if (rs.next())
/*       */       {
/*   723 */         original_type = resourcetype;
/*   724 */         resourcetype = rs.getString(1);
/*   725 */         baseid = rs.getInt(2);
/*   726 */         request.setAttribute("original_type", original_type);
/*   727 */         request.setAttribute("baseid", String.valueOf(baseid));
/*   728 */         request.setAttribute("amcreated", rs.getString(3));
/*       */       }
/*       */       else
/*       */       {
/*   732 */         request.setAttribute("baseid", "-1");
/*       */       }
/*   734 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception exc)
/*       */     {
/*   738 */       exc.printStackTrace();
/*       */     }
/*   740 */     this.mo.getReloadPeriod(request);
/*   741 */     request.setAttribute("monitorname", monitorname);
/*   742 */     if ((resourcetype.equalsIgnoreCase("APM-Insight-Instance")) || (resourcetype.equalsIgnoreCase("APM-Insight-Application")))
/*       */     {
/*   744 */       String scope = resourcetype.substring(12);
/*   745 */       long parentId = 0L;
/*   746 */       if (resourcetype.equalsIgnoreCase("APM-Insight-Instance"))
/*       */       {
/*   748 */         parentId = APMInsightDBUtil.getAppIdForInsId(Long.parseLong(resourceid));
/*       */       }
/*   750 */       else if (resourcetype.equalsIgnoreCase("APM-Insight-Application"))
/*       */       {
/*   752 */         ApplicationInfo appInfo = APMInsightDBUtil.getApplicationInfo(Long.parseLong(resourceid));
/*   753 */         if (appInfo.getInstanceCount() == 1)
/*       */         {
/*   755 */           resourceid = appInfo.getInstanceIds()[0].toString();
/*   756 */           parentId = appInfo.getApplicationId();
/*   757 */           scope = "INSTANCE";
/*       */         }
/*       */       }
/*   760 */       return new ActionForward("/apminsight/overview.do?method=showDetails&resourceId=" + resourceid + "&parentId=" + parentId + "&scope=" + scope + "#" + scope.toUpperCase().charAt(0) + "/O/" + resourceid + "/TW=H", true);
/*       */     }
/*   762 */     if (resourcetype.equals("ORACLE-DB-server"))
/*       */     {
/*   764 */       path = "/Oracle.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */     }
/*   766 */     else if ((resourcetype.toLowerCase().indexOf("windows") != -1) || (resourcetype.equals("Node")) || (resourcetype.toLowerCase().startsWith("novell")) || (resourcetype.equals("Linux")) || (resourcetype.equals("snmp-node")) || (resourcetype.equals("SUN")) || (resourcetype.equals("SUN PC")) || (resourcetype.equals("AIX")) || (resourcetype.equals("HP-UX")) || (resourcetype.equals("HP-TRU64")) || (resourcetype.equals("FreeBSD / OpenBSD")) || (resourcetype.equals("OpenBSD")) || (resourcetype.equals("FreeBSD")) || (resourcetype.equals("Mac OS")))
/*       */     {
/*   768 */       path = "/HostResource.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName + "&opmanager=" + opmanager;
/*       */     } else {
/*   770 */       if ((resourcetype.equals("WEBLOGIC-server")) || (resourcetype.equals("WEBLOGIC-Integration")))
/*       */       {
/*       */ 
/*       */ 
/*   774 */         return showWeblogicDetails(mapping, form, request, response);
/*       */       }
/*   776 */       if (resourcetype.equals("WLS-Cluster"))
/*       */       {
/*   778 */         return new ActionForward("/jsp/clusterdetails.jsp?network=WLS-Cluster&resourceid=" + request.getParameter("resourceid"));
/*       */       }
/*   780 */       if (resourcetype.equals("JBOSS-server"))
/*       */       {
/*       */ 
/*   783 */         setWTAProps(request, form);
/*   784 */         return showJBossDetails(mapping, form, request, response);
/*       */       }
/*   786 */       if (resourcetype.equals("Tomcat-server"))
/*       */       {
/*   788 */         setWTAProps(request, form);
/*   789 */         ResultSet rs_tom = null;
/*       */         try
/*       */         {
/*   792 */           rs_tom = AMConnectionPool.executeQueryStmt("select RESOURCEID,SSLENABLED,URL from AM_TOMCATINFO where RESOURCEID=" + resourceid);
/*   793 */           if (rs_tom.next()) {
/*   794 */             if ((rs_tom.getString("URL") != null) && (!rs_tom.getString("URL").equals(""))) {
/*   795 */               ((AMActionForm)form).setTomcatmanagerurl(rs_tom.getString("URL"));
/*       */             } else {
/*   797 */               ((AMActionForm)form).setTomcatmanagerurl("/manager");
/*       */             }
/*       */           }
/*       */           
/*       */ 
/*       */           try
/*       */           {
/*   804 */             if (rs_tom != null) {
/*   805 */               AMConnectionPool.closeStatement(rs_tom);
/*       */             }
/*       */           } catch (Exception e1) {
/*   808 */             e1.printStackTrace();
/*       */           }
/*       */           
/*   811 */           path = "/Tomcat.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */         }
/*       */         catch (Exception exc1)
/*       */         {
/*   801 */           exc1.printStackTrace();
/*       */         } finally {
/*       */           try {
/*   804 */             if (rs_tom != null) {
/*   805 */               AMConnectionPool.closeStatement(rs_tom);
/*       */             }
/*       */           } catch (Exception e1) {
/*   808 */             e1.printStackTrace();
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*   813 */       else if (resourcetype.equals("MYSQL-DB-server"))
/*       */       {
/*   815 */         path = "/MySql.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */       }
/*   817 */       else if (resourcetype.equals("MSSQL-DB-server"))
/*       */       {
/*   819 */         path = "/MSSql.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */       }
/*   821 */       else if (resourcetype.equals("SYBASE-DB-server"))
/*       */       {
/*       */ 
/*   824 */         path = "/Sybase.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName + "&method=details";
/*       */       }
/*   826 */       else if (resourcetype.equals("DB2-server"))
/*       */       {
/*   828 */         path = "/DB2.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName + "&method=details";
/*       */       }
/*   830 */       else if (resourcetype.equals("Exchange-server"))
/*       */       {
/*   832 */         path = "/Exchange.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */       }
/*   834 */       else if (resourcetype.equals(".Net"))
/*       */       {
/*   836 */         path = "/DotNet.do?name=" + moname + "&haid=" + haid + "&appName=" + applicationName;
/*       */       }
/*   838 */       else if (resourcetype.equals("Custom-Application"))
/*       */       {
/*   840 */         path = "/ShowCAM.do?method=showCAMApplication&camid=" + resourceid + "&haid" + haid;
/*       */       } else {
/*   842 */         if (resourcetype.equals("Port-Test"))
/*       */         {
/*       */ 
/*   845 */           ShowPortReconfiguration(mapping, form, request, response);
/*   846 */           return showPortTestDetails(mapping, form, request, response);
/*       */         }
/*   848 */         if ((resourcetype.equals("Script Monitor")) || (resourcetype.equals("QENGINE")) || (resourcetype.equals("file")) || (resourcetype.equals("directory")) || (resourcetype.equals("File System Monitor")))
/*       */         {
/*   850 */           if (request.getAttribute("amcreated") != null)
/*       */           {
/*   852 */             haid = request.getParameter("haid");
/*   853 */             applicationName = request.getParameter("name");
/*   854 */             resourceid = request.getParameter("resourceid");
/*   855 */             resourcename = request.getParameter("resourcename");
/*   856 */             resourcetype = request.getParameter("type");
/*   857 */             moname = request.getParameter("moname");
/*   858 */             fromwhere = request.getParameter("fromwhere");
/*   859 */             String baseid1 = (String)request.getAttribute("baseid");
/*   860 */             String original_type1 = (String)request.getAttribute("original_type");
/*   861 */             String params = "&haid=" + haid + "&name=" + applicationName + "&resourceid=" + resourceid + "&resourcename=" + applicationName + "&type=" + resourcetype + "&moname=" + moname + "&baseid=" + baseid1 + "&original_type=" + original_type1;
/*   862 */             if (fromwhere != null) {
/*   863 */               params = params + "&fromwhere=" + fromwhere;
/*       */             }
/*   865 */             if ((request.getParameter("reportMessage") != null) && 
/*   866 */               (request.getParameter("reportMessage").equals("1"))) {
/*   867 */               params = params + "&reportMessage=" + FormatUtil.getString("am.webclient.customattribute.success.heading.text");
/*       */             }
/*       */             
/*       */ 
/*   871 */             System.out.println("The output params======/showCustom.do?method=showData" + params);
/*   872 */             return new ActionForward("/showCustom.do?method=showData" + params, false);
/*       */           }
/*       */           
/*       */ 
/*       */ 
/*   877 */           return showScriptMonitorDetails(mapping, form, request, response);
/*       */         }
/*   879 */         if (resourcetype.equals("Ping Monitor"))
/*       */         {
/*       */ 
/*   882 */           return new ActionForward("/createPing.do?resourceid=" + resourceid + "&type=Ping Monitor&moname=" + moname + "&method=showPingMonitorDetails&resourcename=" + resourcename + "&viewType=showResourceTypesAll");
/*       */         }
/*       */         
/*   885 */         if (resourcetype.equals("Generic WMI"))
/*       */         {
/*   887 */           path = "/WMIPerfCounters.do?method=showDetails&resourceid=" + resourceid;
/*       */         } else {
/*   889 */           if ((resourcetype.equals("UrlMonitor")) || (resourcetype.equals("UrlEle")) || (resourcetype.equals("RBMURL")))
/*       */           {
/*   891 */             return showUrlMonitorDetails(mapping, form, request, response);
/*       */           }
/*   893 */           if ((resourcetype.equals("UrlSeq")) || (resourcetype.equals("RBM")))
/*       */           {
/*   895 */             return showUrlSequenceDetails(mapping, form, request, response);
/*       */           }
/*   897 */           if ((resourcetype.equals("APP")) || (resourcetype.equals("DBS")) || (resourcetype.equals("SER")) || (resourcetype.equals("SYS")) || (resourcetype.equals("URL")) || (resourcetype.equals("CAM")))
/*       */           {
/*   899 */             path = "/showresource.do?method=showResourceTypes&detailspage=true&group=" + resourcetype;
/*       */           }
/*   901 */           else if ((resourcetype.equals("MAIL-server")) || (resourcetype.equals("WEB-server")))
/*       */           {
/*   903 */             String pollinterval = "";
/*   904 */             String smtpPort = "";
/*   905 */             String popPort = "";
/*   906 */             String username = "";
/*   907 */             String password = "";
/*   908 */             String mailMsg = "";
/*   909 */             String emailid = "";
/*   910 */             TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*   911 */             ManagedObject mo = tapi.getByName(moname);
/*   912 */             boolean enabled = false;
/*   913 */             if (mo != null)
/*       */             {
/*   915 */               pollinterval = String.valueOf(mo.getPollInterval() / 60);
/*   916 */               enabled = mo.getStatusPollEnabled();
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*   922 */             if (enabled)
/*       */             {
/*   924 */               request.setAttribute("enabled", String.valueOf(enabled));
/*       */             }
/*   926 */             String status = request.getParameter("status");
/*       */             
/*   928 */             ArrayList rows = new ManagedApplication().getRows("select displayname from AM_ManagedObject where resourceid=" + resourceid);
/*   929 */             String displayname = "";
/*   930 */             if (rows.size() > 0)
/*       */             {
/*   932 */               rows = (ArrayList)rows.get(0);
/*   933 */               displayname = (String)rows.get(0);
/*       */             }
/*   935 */             ((AMActionForm)form).setDisplayname(displayname);
/*   936 */             if (((!enabled) || (status != null)) || 
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*   941 */               (resourcetype.equals("MAIL-server")))
/*       */             {
/*   943 */               AMConnectionPool cp = AMConnectionPool.getInstance();
/*   944 */               ResultSet set = null;
/*       */               try
/*       */               {
/*   947 */                 String query = "select AM_MailServerConfig.*,ID, HOST,PORT,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD from AM_MailServerConfig left outer join AM_POPServerConfig on ID=POPID where RESID=" + resourceid;
/*   948 */                 set = AMConnectionPool.executeQueryStmt(query);
/*       */                 
/*   950 */                 if (set.next())
/*       */                 {
/*       */ 
/*   953 */                   popPort = set.getInt("PORT") + "";
/*   954 */                   username = set.getString("USERNAME");
/*   955 */                   password = set.getString("PASSWORD");
/*   956 */                   emailid = set.getString("EMAILID");
/*   957 */                   String pophost = set.getString("HOST");
/*   958 */                   String popport = set.getString("PORT");
/*   959 */                   if (pophost != null)
/*       */                   {
/*   961 */                     ((AMActionForm)form).setpopenabled(true);
/*   962 */                     ((AMActionForm)form).setPopHost(pophost);
/*   963 */                     ((AMActionForm)form).setUsername(username);
/*   964 */                     ((AMActionForm)form).setPopPort(popport);
/*       */                   }
/*       */                   
/*       */ 
/*   968 */                   if (username != null) {
/*   969 */                     username = "&username=" + username;
/*       */                   } else {
/*   971 */                     username = "";
/*       */                   }
/*   973 */                   if (password == null) {
/*   974 */                     password = "";
/*       */                   }
/*       */                   
/*   977 */                   mailMsg = set.getString("TESTMESSAGE");
/*   978 */                   query = "select * from AM_MailServerConfig left outer join AM_SMTPServerConfig on ID=SMTPID where RESID=" + resourceid;
/*   979 */                   set = AMConnectionPool.executeQueryStmt(query);
/*   980 */                   if (set.next())
/*       */                   {
/*   982 */                     smtpPort = set.getInt("PORT") + "";
/*   983 */                     String smtpusername = set.getString("USERNAME");
/*   984 */                     ((AMActionForm)form).setSmtpUserName(smtpusername);
/*   985 */                     String smtppassword = set.getString("PASSWORD");
/*   986 */                     ((AMActionForm)form).setSmtpPassword(smtppassword);
/*   987 */                     if ((smtpusername == null) || (smtpusername.equals("")))
/*       */                     {
/*   989 */                       ((AMActionForm)form).setSmtpauthenabled(false);
/*       */                     }
/*       */                     else
/*       */                     {
/*   993 */                       ((AMActionForm)form).setSmtpauthenabled(true);
/*       */                     }
/*       */                   }
/*       */                 }
/*       */                 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */                 try
/*       */                 {
/*  1007 */                   if (set != null) {
/*  1008 */                     AMConnectionPool.closeStatement(set);
/*       */                   }
/*       */                 }
/*       */                 catch (Exception ex) {
/*  1012 */                   ex.printStackTrace();
/*       */                 }
/*       */                 
/*       */ 
/*  1016 */                 showAvailabilityDetails(mapping, form, request, response);
/*       */               }
/*       */               catch (Exception ex)
/*       */               {
/*  1001 */                 ex.printStackTrace();
/*       */               }
/*       */               finally
/*       */               {
/*       */                 try
/*       */                 {
/*  1007 */                   if (set != null) {
/*  1008 */                     AMConnectionPool.closeStatement(set);
/*       */                   }
/*       */                 }
/*       */                 catch (Exception ex) {
/*  1012 */                   ex.printStackTrace();
/*       */                 }
/*       */               }
/*       */             }
/*       */             
/*  1017 */             if (resourcetype.equals("MAIL-server"))
/*       */             {
/*  1019 */               request.setAttribute("password", password);
/*  1020 */               path = "/jsp/AvailabilityPerformance.jsp?resourcename=" + resourcename + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + username + "&smtpPort=" + smtpPort + "&popPort=" + popPort + "&pollInterval=" + pollinterval + "&mailMsg=" + mailMsg + "&moname=" + moname + "&emailid=" + emailid;
/*       */             }
/*  1022 */             else if (resourcetype.equals("WEB-server")) {
/*  1023 */               path = "/jsp/AvailabilityPerformance.jsp?resourcename=" + resourcename + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + "&pollInterval=" + pollinterval + "&moname=" + moname;
/*       */ 
/*       */ 
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */           }
/*  1032 */           else if (resourcetype.equals("Apache-server"))
/*       */           {
/*  1034 */             AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */             
/*  1036 */             HashMap apachedetails = new HashMap();
/*  1037 */             String connectionstatus = "";
/*  1038 */             String totalaccesses = "";
/*  1039 */             String totalkb = "";
/*  1040 */             String cpuload = "";
/*  1041 */             String uptime = "";
/*  1042 */             String reqpermin = "";
/*  1043 */             String bytespersec = "";
/*  1044 */             String bytesperreq = "";
/*  1045 */             String busyservers = "";
/*  1046 */             String idleservers = "";
/*  1047 */             String responsecode = "";
/*  1048 */             String enablestatus = "";
/*  1049 */             String collectiontime = "";
/*  1050 */             String query = DBQueryUtil.getTopNValues("select * from AM_ApacheDetails where resID=" + resourceid + " order by collectiontime desc", "1");
/*       */             
/*  1052 */             ResultSet set = null;
/*       */             try
/*       */             {
/*  1055 */               set = AMConnectionPool.executeQueryStmt(query);
/*  1056 */               if (set.next())
/*       */               {
/*       */ 
/*  1059 */                 if (set.getString("CPULOAD") != null)
/*       */                 {
/*  1061 */                   cpuload = String.valueOf(Float.parseFloat(set.getString("CPULOAD")));
/*       */                 }
/*  1063 */                 if ((set.getString("UPTIME") != null) && (set.getString("UPTIME") != "-1"))
/*       */                 {
/*  1065 */                   uptime = ReportUtilities.format(Long.parseLong(set.getString("UPTIME")) * 1000L);
/*       */                 }
/*  1067 */                 connectionstatus = set.getString("CONNECTIONSTATUS");
/*  1068 */                 totalaccesses = set.getString("TOTALACCESSES");
/*  1069 */                 totalkb = set.getString("TOTALKB");
/*       */                 
/*       */ 
/*  1072 */                 ArrayList al = servers_bytes(resourceid);
/*  1073 */                 reqpermin = set.getString("REQPERMIN");
/*       */                 
/*  1075 */                 bytesperreq = set.getString("BYTESPERREQ");
/*       */                 
/*       */ 
/*  1078 */                 busyservers = String.valueOf(al.get(1));
/*  1079 */                 idleservers = String.valueOf(al.get(2));
/*  1080 */                 bytespersec = String.valueOf(al.get(3));
/*       */                 
/*  1082 */                 enablestatus = set.getString("ENABLESTATUS");
/*  1083 */                 collectiontime = set.getString("COLLECTIONTIME");
/*       */ 
/*       */               }
/*       */               else
/*       */               {
/*  1088 */                 enablestatus = "2";
/*       */               }
/*  1090 */               apachedetails.put("connectionstatus", connectionstatus);
/*  1091 */               apachedetails.put("totalaccesses", totalaccesses);
/*  1092 */               apachedetails.put("totalkb", totalkb);
/*  1093 */               apachedetails.put("cpuload", cpuload);
/*  1094 */               apachedetails.put("uptime", uptime);
/*  1095 */               apachedetails.put("reqpermin", reqpermin);
/*  1096 */               apachedetails.put("bytespersec", bytespersec);
/*  1097 */               apachedetails.put("bytesperreq", bytesperreq);
/*  1098 */               apachedetails.put("busyservers", busyservers);
/*  1099 */               apachedetails.put("idleservers", idleservers);
/*  1100 */               apachedetails.put("responsecode", responsecode);
/*  1101 */               apachedetails.put("enablestatus", enablestatus);
/*  1102 */               apachedetails.put("collectiontime", collectiontime);
/*  1103 */               request.setAttribute("apachedetails", apachedetails);
/*       */               
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */               try
/*       */               {
/*  1114 */                 AMConnectionPool.closeStatement(set);
/*       */               }
/*       */               catch (Exception exc)
/*       */               {
/*  1118 */                 exc.printStackTrace();
/*       */               }
/*       */               
/*       */ 
/*  1122 */               pollinterval = "";
/*       */             }
/*       */             catch (Exception e)
/*       */             {
/*  1108 */               e.printStackTrace();
/*       */             }
/*       */             finally
/*       */             {
/*       */               try
/*       */               {
/*  1114 */                 AMConnectionPool.closeStatement(set);
/*       */               }
/*       */               catch (Exception exc)
/*       */               {
/*  1118 */                 exc.printStackTrace();
/*       */               }
/*       */             }
/*       */             
/*       */             String pollinterval;
/*  1123 */             String username = "";
/*  1124 */             String password = "";
/*  1125 */             TopoAPI tapi = (TopoAPI)NmsUtil.getAPI("TopoAPI");
/*  1126 */             ManagedObject mo = tapi.getByName(moname);
/*  1127 */             boolean sslenabled_val = false;
/*  1128 */             sslenabled_val = getSslstatus(resourceid);
/*  1129 */             request.setAttribute("sslenabled_val", new Boolean(sslenabled_val));
/*  1130 */             if (sslenabled_val)
/*       */             {
/*  1132 */               ((AMActionForm)form).setSslenabled(true);
/*       */             }
/*       */             
/*       */ 
/*  1136 */             boolean enabled = false;
/*  1137 */             if (mo != null)
/*       */             {
/*  1139 */               pollinterval = (String)request.getAttribute("reloadperiod");
/*  1140 */               if (pollinterval != null)
/*       */               {
/*       */                 try
/*       */                 {
/*  1144 */                   int wnPollinterval = Integer.parseInt(pollinterval);
/*  1145 */                   pollinterval = String.valueOf(wnPollinterval / 60);
/*       */                 }
/*       */                 catch (NumberFormatException ne) {}
/*       */               }
/*       */               
/*       */ 
/*  1151 */               enabled = mo.getStatusPollEnabled();
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*  1157 */             if (enabled)
/*       */             {
/*  1159 */               request.setAttribute("enabled", String.valueOf(enabled));
/*       */             }
/*  1161 */             String status = request.getParameter("status");
/*       */             
/*  1163 */             ArrayList rows = new ManagedApplication().getRows("select displayname,RESOURCENAME from AM_ManagedObject where resourceid=" + resourceid);
/*  1164 */             String displayname = "";
/*  1165 */             String resourcename_temp = "";
/*  1166 */             if (rows.size() > 0)
/*       */             {
/*  1168 */               rows = (ArrayList)rows.get(0);
/*  1169 */               displayname = (String)rows.get(0);
/*  1170 */               resourcename_temp = (String)rows.get(1);
/*       */             }
/*  1172 */             ((AMActionForm)form).setDisplayname(displayname);
/*  1173 */             if ((enabled) && (status == null)) {}
/*       */             
/*       */ 
/*       */ 
/*  1177 */             showAvailabilityDetails(mapping, form, request, response);
/*       */             
/*       */             try
/*       */             {
/*  1181 */               ResultSet rs_details = AMConnectionPool.executeQueryStmt("select RESID,AUTHENTICATION,USERNAME," + DBQueryUtil.decode("PASSWORD") + " as PASSWORD,ISURL,URL from AM_APACHE_CONFIG where RESID=" + resourceid);
/*  1182 */               if (rs_details.next())
/*       */               {
/*  1184 */                 if (rs_details.getString("AUTHENTICATION").equals("1"))
/*       */                 {
/*  1186 */                   ((AMActionForm)form).setApacheauth(true);
/*  1187 */                   ((AMActionForm)form).setApacheUserName(rs_details.getString("USERNAME"));
/*       */ 
/*       */                 }
/*       */                 else
/*       */                 {
/*  1192 */                   ((AMActionForm)form).setApacheauth(false);
/*       */                 }
/*  1194 */                 if (rs_details.getString("ISURL").equals("1"))
/*       */                 {
/*  1196 */                   ((AMActionForm)form).setServerstatusurl(true);
/*  1197 */                   ((AMActionForm)form).setApacheurl(rs_details.getString("URL"));
/*       */                 }
/*       */                 else
/*       */                 {
/*  1201 */                   ((AMActionForm)form).setServerstatusurl(false);
/*  1202 */                   String protocol = "http";
/*  1203 */                   if (sslenabled_val)
/*       */                   {
/*  1205 */                     protocol = "https";
/*       */                   }
/*  1207 */                   String host_port = "";
/*       */                   try
/*       */                   {
/*  1210 */                     String qry = "select InetService.TARGETNAME,CollectData.APPLNDISCPORT from InetService,CollectData where InetService.NAME='" + resourcename_temp + "' and InetService.NAME=CollectData.RESOURCENAME";
/*  1211 */                     AMConnectionPool.getInstance();ResultSet rs_qry = AMConnectionPool.executeQueryStmt(qry);
/*  1212 */                     if (rs_qry.next())
/*       */                     {
/*  1214 */                       String host = rs_qry.getString("TARGETNAME");
/*  1215 */                       String port_temp = rs_qry.getString("APPLNDISCPORT");
/*  1216 */                       host_port = host + ":" + port_temp;
/*       */                     }
/*       */                   }
/*       */                   catch (Exception e1) {}
/*       */                   
/*       */ 
/*  1222 */                   String serverstatusurl = protocol + "://" + host_port + "/server-status?auto";
/*       */                   
/*  1224 */                   request.setAttribute("serverstatusurl", serverstatusurl);
/*       */                 }
/*       */               }
/*       */               
/*       */               try
/*       */               {
/*  1230 */                 AMConnectionPool.closeStatement(rs_details);
/*       */               }
/*       */               catch (Exception exc) {}
/*       */             }
/*       */             catch (Exception exc1) {}
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */             try
/*       */             {
/*  1241 */               String query_msg = "select ERRORMSG from AM_RESOURCECONFIG where RESOURCEID=" + resourceid;
/*  1242 */               AMConnectionPool.getInstance();ResultSet rstype = AMConnectionPool.executeQueryStmt(query_msg);
/*  1243 */               if (rstype.next())
/*       */               {
/*  1245 */                 String message = rstype.getString("ERRORMSG");
/*  1246 */                 if (message.equals("serverstatus url problem"))
/*       */                 {
/*  1248 */                   messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.apache.serverstatusurlerror"));
/*       */                 }
/*  1250 */                 else if (message.equals("Authorization Failed"))
/*       */                 {
/*  1252 */                   messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.apache.authoriztionfailed"));
/*       */                 }
/*       */               }
/*       */             }
/*       */             catch (Exception exc) {}
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */             try
/*       */             {
/*  1268 */               if (enablestatus != null)
/*       */               {
/*  1270 */                 if (enablestatus.equals("0"))
/*       */                 {
/*  1272 */                   messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Enable Extended Status in the Apache's httpd.conf to get Request statistics,Bytes Transferred etc.,"));
/*       */                 }
/*       */               }
/*       */             }
/*       */             catch (Exception nullexc)
/*       */             {
/*  1278 */               nullexc.printStackTrace();
/*       */             }
/*  1280 */             saveMessages(request, messages);
/*       */             
/*  1282 */             if (resourcetype.equals("Apache-server")) {
/*  1283 */               path = "/jsp/ApacheDetails.jsp?resourcename=" + resourcename + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + "&pollInterval=" + pollinterval + "&moname=" + moname;
/*       */             }
/*       */           }
/*  1286 */           else if (resourcetype.equals("EJB"))
/*       */           {
/*  1288 */             ArrayList serverinfos = this.mo.getRows("select AM_ManagedObject.* from AM_EJB,AM_EAR,AM_ManagedObject where AM_EJB.parentid=earid and AM_EAR.parentid=AM_ManagedObject.resourceid and AM_EJB.ejbid=" + resourceid);
/*  1289 */             if (serverinfos.size() > 0)
/*       */             {
/*  1291 */               serverinfos = (ArrayList)serverinfos.get(0);
/*  1292 */               String serverresourceid = (String)serverinfos.get(0);
/*  1293 */               String servername = (String)serverinfos.get(1);
/*  1294 */               String type = (String)serverinfos.get(2);
/*  1295 */               if (type.equals("JBOSS-server"))
/*       */               {
/*  1297 */                 path = "/jsp/JBossEJBDetails.jsp?method=showJ2EEResources&resourceid=" + serverresourceid + "&resourcename=" + servername;
/*       */               }
/*  1299 */               else if (type.equals("WEBLOGIC-server"))
/*       */               {
/*  1301 */                 path = "/showEJB.do?method=getEJBData&resourceid=" + serverresourceid;
/*       */               }
/*       */             }
/*       */           }
/*  1305 */           else if (resourcetype.equals("JDBC"))
/*       */           {
/*       */ 
/*  1308 */             String query = "select AM_ManagedObject.*  from AM_ManagedObject,AM_JDBC where AM_JDBC.ID = " + resourceid + " and AM_ManagedObject.RESOURCEID=AM_JDBC.PARENTID";
/*  1309 */             ArrayList serverinfos = this.mo.getRows(query);
/*  1310 */             if (serverinfos.size() > 0)
/*       */             {
/*  1312 */               serverinfos = (ArrayList)serverinfos.get(0);
/*  1313 */               String serverresourceid = (String)serverinfos.get(0);
/*  1314 */               String servername = (String)serverinfos.get(1);
/*  1315 */               String type = (String)serverinfos.get(2);
/*  1316 */               if (type.equals("WEBLOGIC-server"))
/*       */               {
/*       */ 
/*  1319 */                 path = "/showJDBC.do?method=getJDBCPoolData&resourceid=" + serverresourceid + "&resourcename=" + servername + "&moname=" + servername;
/*       */               }
/*       */               
/*       */             }
/*       */           }
/*  1324 */           else if (resourcetype.equals("THREAD"))
/*       */           {
/*       */ 
/*  1327 */             String query = "select AM_ManagedObject.*  from AM_ManagedObject,AM_Thread where AM_Thread.ID = " + resourceid + " and AM_ManagedObject.RESOURCEID=AM_Thread.PARENTID";
/*  1328 */             ArrayList serverinfos = this.mo.getRows(query);
/*  1329 */             if (serverinfos.size() > 0)
/*       */             {
/*  1331 */               serverinfos = (ArrayList)serverinfos.get(0);
/*  1332 */               String serverresourceid = (String)serverinfos.get(0);
/*  1333 */               String servername = (String)serverinfos.get(1);
/*  1334 */               String type = (String)serverinfos.get(2);
/*  1335 */               if (type.equals("WEBLOGIC-server"))
/*       */               {
/*  1337 */                 path = "/showThread.do?method=getThreadPoolData&resourceid=" + serverresourceid + "&resourcename=" + servername + "&moname=" + servername;
/*       */               }
/*       */               
/*       */             }
/*       */           }
/*  1342 */           else if ((resourcetype.equals("WEBAPP")) || (resourcetype.equals("SERVLET")))
/*       */           {
/*  1344 */             String query = "select AM_ManagedObject.* from AM_WAR,AM_EAR,AM_ManagedObject where AM_WAR.parentid=earid and AM_EAR.parentid=AM_ManagedObject.resourceid  and AM_WAR.warid=" + resourceid;
/*  1345 */             if (resourcetype.equals("SERVLET"))
/*       */             {
/*  1347 */               query = "select AM_ManagedObject.* from AM_Servlet,AM_WAR,AM_EAR,AM_ManagedObject where AM_WAR.parentid=earid and AM_EAR.parentid=AM_ManagedObject.resourceid  and AM_Servlet.PARENTID = AM_WAR.WARID  AM_Servlet.ID=" + resourceid;
/*       */             }
/*  1349 */             ArrayList serverinfos = this.mo.getRows(query);
/*  1350 */             if (serverinfos.size() > 0)
/*       */             {
/*  1352 */               serverinfos = (ArrayList)serverinfos.get(0);
/*  1353 */               String serverresourceid = (String)serverinfos.get(0);
/*  1354 */               String servername = (String)serverinfos.get(1);
/*  1355 */               String type = (String)serverinfos.get(2);
/*  1356 */               if (type.equals("JBOSS-server"))
/*       */               {
/*  1358 */                 path = "/jsp/JBossWebAppDetails.jsp?method=showJ2EEResources&resourceid=" + serverresourceid + "&resourcename=" + servername;
/*       */               }
/*  1360 */               else if (type.equals("WEBLOGIC-server"))
/*       */               {
/*  1362 */                 path = "/showWebApp.do?method=getWebAppData&resourceid=" + serverresourceid;
/*       */               }
/*  1364 */               else if (type.equals("TOMCAT-server"))
/*       */               {
/*  1366 */                 path = "/Tomcat.do?name=" + servername + "&haid=&appName=&resourcename=" + servername + "&resourceid=" + serverresourceid + "#Application Summary";
/*       */               }
/*       */               
/*       */             }
/*       */           }
/*  1371 */           else if ((resourcetype.equals("Disk")) || (resourcetype.equals("DataBase")) || (resourcetype.equals("DataFiles")) || (resourcetype.equals("Process")))
/*       */           {
/*  1373 */             String query = "select AM_ManagedObject.*  from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.CHILDID = " + resourceid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.PARENTID";
/*  1374 */             ArrayList serverinfos = this.mo.getRows(query);
/*  1375 */             if (serverinfos.size() > 0)
/*       */             {
/*  1377 */               serverinfos = (ArrayList)serverinfos.get(0);
/*  1378 */               String serverresourceid = (String)serverinfos.get(0);
/*  1379 */               String servername = (String)serverinfos.get(1);
/*  1380 */               String type = (String)serverinfos.get(2);
/*  1381 */               if (resourcetype.equals("DataBase"))
/*       */               {
/*  1383 */                 if (type.equals("MYSQL-DB-server"))
/*       */                 {
/*  1385 */                   path = "/MySql.do?name=" + servername + "&resourceid=" + serverresourceid;
/*       */                 }
/*  1387 */                 else if (type.equals("MSSQL-DB-server"))
/*       */                 {
/*  1389 */                   ArrayList dbdetails = this.mo.getRows("select * from AM_ManagedObject where AM_ManagedObject.RESOURCEID=" + resourceid);
/*  1390 */                   if (dbdetails.size() > 0)
/*       */                   {
/*  1392 */                     ArrayList dbinfo = (ArrayList)dbdetails.get(0);
/*  1393 */                     String dbname = (String)dbinfo.get(1);
/*  1394 */                     path = "/MSSql.do?name=" + servername + "&resourceid=" + serverresourceid + "&details=DB&dbid=" + resourceid + "&dbname=" + dbname + "&haid=" + request.getParameter("haid");
/*       */                   }
/*       */                   
/*       */                 }
/*       */                 
/*       */               }
/*  1400 */               else if (resourcetype.equals("DataFiles"))
/*       */               {
/*  1402 */                 path = "/Oracle.do?name=" + servername + "&resourceid=" + serverresourceid;
/*       */               }
/*       */               else
/*       */               {
/*  1406 */                 path = "/HostResource.do?name=" + servername + "&resourceid=" + serverresourceid;
/*       */               }
/*       */             }
/*       */           }
/*  1410 */           else if (resourcetype.equals("Trap"))
/*       */           {
/*  1412 */             path = "/adminAction.do?method=editTrapListener&trapid=" + resourceid + "&edit=true,true";
/*       */           }
/*  1414 */           else if (resourcetype.equals("JMXNotification"))
/*       */           {
/*  1416 */             String qq = "select MBEANRESOURCEID from AM_JMXNOTIFICATION_PROFILES where RESOURCEID=" + resourceid;
/*  1417 */             AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(qq);
/*  1418 */             AMLog.debug("JMXNotification query in showdetails " + qq);
/*  1419 */             if (rs.first())
/*       */             {
/*  1421 */               int mBeanResourceID = rs.getInt(1);
/*  1422 */               path = "/showresource.do?resourceid=" + mBeanResourceID + "&method=showResourceForResourceID";
/*       */             }
/*  1424 */             AMConnectionPool.closeStatement(rs);
/*       */           }
/*  1426 */           else if (resourcetype.equals("WTA"))
/*       */           {
/*  1428 */             setWTAProps(request, form);
/*  1429 */             String view = request.getParameter("view");
/*  1430 */             if (view == null) view = "db";
/*  1431 */             String wtaresid = (String)request.getAttribute("wtaresourceid");
/*  1432 */             String resname = (String)request.getAttribute("monitorname");
/*  1433 */             path = "/WTA.do?view=" + view + "&method=showdetails&resourceid=" + wtaresid + "&resourcename=" + resname;
/*       */           }
/*  1435 */           else if (resourcetype.equals("ORACLE-APP-server"))
/*       */           {
/*  1437 */             path = "/oracleas.do?resourceid=" + resourceid + "&name=" + resourcename;
/*       */           }
/*  1439 */           else if (resourcetype.equals("Web Service"))
/*       */           {
/*  1441 */             path = "/wsm.do?resourceid=" + resourceid + "&name=" + resourcename + "&method=showdetails";
/*       */           }
/*  1443 */           else if (resourcetype.equals("JDK1.5")) {
/*  1444 */             path = "/JavaRuntime.do?resourceid=" + resourceid + "&name=" + resourcename;
/*       */           }
/*  1446 */           else if (resourcetype.equals("SAP")) {
/*  1447 */             path = "/sap.do?resourceid=" + resourceid + "&name=" + resourcename;
/*       */           }
/*  1449 */           else if (resourcetype.equals("SAP-CCMS")) {
/*  1450 */             path = "/sap.do?resourceid=" + resourceid + "&method=showCCMSDetails";
/*       */           }
/*  1452 */           else if (resourcetype.equals("AS400/iSeries"))
/*       */           {
/*  1454 */             path = "/as400.do?resourceid=" + resourceid + "&name=" + resourcename;
/*       */           }
/*       */           else
/*       */           {
/*  1458 */             if (resourcetype.equals("TELNET"))
/*       */             {
/*  1460 */               String displayname = null;
/*  1461 */               String q1 = "select DISPLAYNAME from AM_ManagedObject where  RESOURCEID=" + resourceid;
/*  1462 */               AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/*  1463 */               if (rs.next())
/*       */               {
/*  1465 */                 displayname = rs.getString("DISPLAYNAME");
/*       */               }
/*       */               
/*  1468 */               ((AMActionForm)form).setDisplayname(displayname);
/*  1469 */               AMConnectionPool.closeStatement(rs);
/*  1470 */               String resName = "";
/*  1471 */               String polling = "";
/*  1472 */               int temppollInterval1 = 0;
/*  1473 */               String pollQuery = "select RESOURCENAME from AM_ManagedObject where resourceid=" + resourceid;
/*  1474 */               AMConnectionPool.getInstance();ResultSet IISpollInterval = AMConnectionPool.executeQueryStmt(pollQuery);
/*  1475 */               if (IISpollInterval.next())
/*       */               {
/*  1477 */                 resName = IISpollInterval.getString(1);
/*       */               }
/*  1479 */               AMConnectionPool.closeStatement(IISpollInterval);
/*  1480 */               String pollQuery1 = "select POLLINTERVAL from CollectData where RESOURCENAME='" + resName + "'";
/*  1481 */               AMConnectionPool.getInstance();ResultSet IISpollInterval1 = AMConnectionPool.executeQueryStmt(pollQuery1);
/*  1482 */               if (IISpollInterval1.next())
/*       */               {
/*  1484 */                 String temppollInterval = IISpollInterval1.getString(1);
/*  1485 */                 System.out.println(temppollInterval);
/*       */                 try
/*       */                 {
/*  1488 */                   temppollInterval1 = Integer.parseInt(temppollInterval) / 60;
/*       */                 }
/*       */                 catch (Exception e) {}
/*       */                 
/*       */ 
/*       */ 
/*  1494 */                 polling = Integer.toString(temppollInterval1);
/*       */               }
/*  1496 */               AMConnectionPool.closeStatement(IISpollInterval1);
/*  1497 */               request.setAttribute("telnetpolling", polling);
/*       */             }
/*       */             
/*       */ 
/*  1501 */             return showAvailabilityDetails(mapping, form, request, response);
/*       */           } } } }
/*  1503 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */   private Long getlatesteventTime(HttpServletRequest request, HashMap alertMap)
/*       */   {
/*  1509 */     ResultSet rs = null;
/*  1510 */     long eventTime = 1L;
/*       */     try {
/*  1512 */       String operatorFilter = "";
/*  1513 */       String timeFilter = request.getSession().getAttribute("lastcriticalevent") != null ? " and Alert.MODTIME > " + request.getSession().getAttribute("lastcriticalevent") + " " : " ";
/*  1514 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/*  1515 */         HttpSession session = request.getSession();
/*       */         
/*  1517 */         Vector resids = (Vector)session.getAttribute("resourceIDforOwner");
/*  1518 */         if ((resids != null) && (resids.size() > 0))
/*       */         {
/*  1520 */           operatorFilter = " and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resids);
/*       */         }
/*       */       }
/*  1523 */       String query2 = "select MAX(MODTIME) as time,Alert.SEVERITY,Alert.SOURCE,Alert.CATEGORY from Alert,AM_ManagedObject,AM_ManagedResourceType where Alert.SOURCE=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE NOT IN ('HAI') and Alert.SEVERITY in (1,4)   " + operatorFilter + "  " + timeFilter + " GROUP BY Alert.SEVERITY,Alert.MODTIME,Alert.SOURCE,Alert.CATEGORY order by Alert.MODTIME desc";
/*       */       
/*  1525 */       rs = AMConnectionPool.executeQueryStmt(query2);
/*  1526 */       boolean latestEventAddded = false;
/*  1527 */       while (rs.next())
/*       */       {
/*  1529 */         if (!latestEventAddded)
/*       */         {
/*  1531 */           eventTime = rs.getLong("time");
/*  1532 */           latestEventAddded = true;
/*       */         }
/*  1534 */         HashMap<String, String> map = new HashMap();
/*  1535 */         map.put("SEVERITY", rs.getString("SEVERITY"));
/*  1536 */         map.put("SOURCE", rs.getString("SOURCE"));
/*  1537 */         map.put("CATEGORY", rs.getString("CATEGORY"));
/*  1538 */         alertMap.put("" + rs.getLong("time"), map);
/*       */       }
/*       */     } catch (Exception ex) {
/*  1541 */       ex.printStackTrace();
/*       */     } finally {
/*  1543 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     
/*  1546 */     return Long.valueOf(eventTime);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showPlasmaView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  1555 */     String healthfiltertype = "All";
/*  1556 */     boolean flag = true;
/*  1557 */     boolean flag1 = true;
/*  1558 */     boolean playSound = false;
/*  1559 */     boolean audioAlert = false;
/*  1560 */     boolean criticalEvent = false;
/*  1561 */     boolean resourceDownEvent = false;
/*  1562 */     boolean warningEvent = false;
/*  1563 */     boolean jumpIcon = false;
/*  1564 */     HttpSession session = request.getSession();
/*  1565 */     Cookie[] cookies = request.getCookies();
/*  1566 */     HashMap<Integer, String> fromCookieMap = new HashMap();
/*  1567 */     for (int i = 0; i < cookies.length; i++)
/*       */     {
/*  1569 */       if (cookies[i].getName().equals("plasmaview_healthfilter")) {
/*  1570 */         healthfiltertype = cookies[i].getValue();
/*       */       }
/*  1572 */       if ((cookies[i].getName().equals("check2")) && (cookies[i].getValue().equals("false"))) {
/*  1573 */         flag = false;
/*       */       }
/*  1575 */       if ((cookies[i].getName().equals("check3")) && (cookies[i].getValue().equals("false"))) {
/*  1576 */         flag1 = false;
/*       */       }
/*  1578 */       if ((cookies[i].getName().equals("check4")) && (cookies[i].getValue().equals("true"))) {
/*  1579 */         playSound = true;
/*       */       }
/*  1581 */       if ((cookies[i].getName().equals("check5")) && (cookies[i].getValue().equals("true")))
/*       */       {
/*  1583 */         fromCookieMap.put(Integer.valueOf(0), cookies[i].getValue());
/*       */       }
/*  1585 */       if ((cookies[i].getName().equals("check6")) && (cookies[i].getValue().equals("true")))
/*       */       {
/*  1587 */         fromCookieMap.put(Integer.valueOf(1), cookies[i].getValue());
/*       */       }
/*  1589 */       if ((cookies[i].getName().equals("check7")) && (cookies[i].getValue().equals("true")))
/*       */       {
/*  1591 */         fromCookieMap.put(Integer.valueOf(4), cookies[i].getValue());
/*       */       }
/*  1593 */       if ((cookies[i].getName().equals("check8")) && (cookies[i].getValue().equals("true"))) {
/*  1594 */         jumpIcon = true;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */     try
/*       */     {
/*  1602 */       if (playSound)
/*       */       {
/*  1604 */         HashMap<String, HashMap<String, String>> alertMap = new HashMap();
/*  1605 */         Long currentEvent = getlatesteventTime(request, alertMap);
/*  1606 */         if (session.getAttribute("lastcriticalevent") == null)
/*       */         {
/*  1608 */           session.setAttribute("lastcriticalevent", currentEvent);
/*       */         }
/*       */         else
/*       */         {
/*  1612 */           long lastevent = Long.parseLong(String.valueOf(session.getAttribute("lastcriticalevent")));
/*  1613 */           if (currentEvent.longValue() > lastevent)
/*       */           {
/*  1615 */             audioAlert = true;
/*  1616 */             session.setAttribute("lastcriticalevent", currentEvent);
/*       */           }
/*       */         }
/*  1619 */         request.setAttribute("alarmSound", setAlarmSound(currentEvent, alertMap, fromCookieMap));
/*       */       }
/*       */       else
/*       */       {
/*  1623 */         session.removeAttribute("lastcriticalevent");
/*       */       }
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  1628 */       ex.printStackTrace();
/*       */     }
/*  1630 */     request.setAttribute("soundalarm", Boolean.valueOf(audioAlert));
/*       */     
/*       */ 
/*  1633 */     String loggedUserName = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getLoggedInUserName(request);
/*  1634 */     boolean isIT360CustSpecifcMSPFlag = EnterpriseUtil.getIT360CustSpecifcMSPFlag(loggedUserName);
/*       */     
/*       */ 
/*  1637 */     if (flag)
/*       */     {
/*       */ 
/*  1640 */       if ((ClientDBUtil.isPrivilegedUser(request)) || (isIT360CustSpecifcMSPFlag))
/*       */       {
/*  1642 */         String owner = request.getRemoteUser();
/*  1643 */         ArrayList alertList = null;
/*  1644 */         if (isIT360CustSpecifcMSPFlag)
/*       */         {
/*  1646 */           alertList = AlarmUtil.getAlertsForOwner(owner, request);
/*       */         }
/*       */         else
/*       */         {
/*  1650 */           alertList = AlarmUtil.getAlertsForOwner(owner, request);
/*       */         }
/*  1652 */         if (alertList.size() != 0)
/*       */         {
/*  1654 */           request.setAttribute("recentAlarmsList", alertList);
/*       */         }
/*       */         
/*       */       }
/*       */       else
/*       */       {
/*  1660 */         ArrayList alertList = AlarmUtil.getRecentAlerts(0);
/*  1661 */         if (alertList.size() != 0)
/*       */         {
/*  1663 */           request.setAttribute("recentAlarmsList", alertList);
/*       */         }
/*       */       }
/*       */       try
/*       */       {
/*  1668 */         alertList = (ArrayList)request.getAttribute("recentAlarmsList");
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*       */         ArrayList alertList;
/*  1673 */         exc.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*  1677 */     String userName = request.getRemoteUser();
/*  1678 */     ArrayList list = new ArrayList(5);
/*       */     
/*  1680 */     String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*  1681 */     if (flag1)
/*       */     {
/*       */ 
/*  1684 */       if ((ClientDBUtil.isPrivilegedUser(request)) && (!isIT360CustSpecifcMSPFlag))
/*       */       {
/*       */ 
/*  1687 */         ArrayList templist = null;
/*  1688 */         if (healthfiltertype.equals("Critical/Warning")) {
/*  1689 */           templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,AM_UserPasswordTable.USERNAME,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE ,Alert.SEVERITY from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,Alert where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + eumChildListCond + " and Alert.source=AM_ManagedObject.resourceid and  Alert.groupname='AppManager' and Alert.severity!=5 and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID order by RESOURCENAME");
/*       */         } else {
/*  1691 */           templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,AM_UserPasswordTable.USERNAME,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + eumChildListCond + " and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID order by RESOURCENAME");
/*       */         }
/*       */         
/*       */ 
/*  1695 */         ArrayList listofHAID = new ArrayList(5);
/*  1696 */         for (int i = 0; i < templist.size(); i++)
/*       */         {
/*  1698 */           ArrayList oneRow = (ArrayList)templist.get(i);
/*  1699 */           String own = (String)oneRow.get(3);
/*  1700 */           if (own.equals(userName))
/*       */           {
/*       */ 
/*       */ 
/*  1704 */             Object tempHaid = oneRow.get(6);
/*  1705 */             if (listofHAID.indexOf(tempHaid) == -1)
/*       */             {
/*  1707 */               listofHAID.add(tempHaid);
/*  1708 */               list.add(oneRow);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       else {
/*  1714 */         String bsgFilterCondn = "";
/*  1715 */         String bsgType = "0";
/*  1716 */         if (isIT360CustSpecifcMSPFlag)
/*       */         {
/*  1718 */           bsgType = "1";
/*  1719 */           Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  1720 */           bsgFilterCondn = " and " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */         }
/*       */         
/*       */ 
/*  1724 */         if (healthfiltertype.equals("Critical/Warning")) {
/*  1725 */           list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID, AM_ManagedObject.TYPE,Alert.SEVERITY from AM_ManagedObject,AM_HOLISTICAPPLICATION,Alert where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and Alert.source=AM_ManagedObject.resourceid and " + eumChildListCond + " and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " and  Alert.groupname='AppManager' and Alert.severity!=5 order by RESOURCENAME");
/*       */         } else {
/*  1727 */           list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID, AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + eumChildListCond + " and AM_HOLISTICAPPLICATION.TYPE=" + bsgType + bsgFilterCondn + " order by RESOURCENAME");
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  1733 */     request.setAttribute("monitorgrouplist", list);
/*  1734 */     request.setAttribute("reloadperiod", "120");
/*  1735 */     if ((request.getParameter("viewType") != null) && (request.getParameter("viewType").equals("zoho")))
/*       */     {
/*  1737 */       ArrayList haid_name = parseFileforIds();
/*  1738 */       ArrayList idnames = (ArrayList)haid_name.get(0);
/*  1739 */       ArrayList resids = (ArrayList)haid_name.get(1);
/*  1740 */       String id_temp = "(";
/*  1741 */       for (int i = 0; i < resids.size(); i++)
/*       */       {
/*  1743 */         id_temp = id_temp + "'" + resids.get(i) + "',";
/*       */       }
/*  1745 */       id_temp = id_temp.substring(0, id_temp.length() - 1) + ")";
/*  1746 */       if ((ClientDBUtil.isPrivilegedUser(request)) && (!isIT360CustSpecifcMSPFlag))
/*       */       {
/*       */ 
/*  1749 */         ArrayList templist = null;
/*  1750 */         if (healthfiltertype.equals("Critical/Warning")) {
/*  1751 */           templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,AM_UserPasswordTable.USERNAME,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE,Alert.SEVERITY from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,Alert where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and Alert.source=AM_ManagedObject.resourceid and " + eumChildListCond + " and  Alert.groupname='AppManager' and Alert.severity!=5 and AM_HOLISTICAPPLICATION.HAID NOT IN " + id_temp + " and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID order by RESOURCENAME");
/*       */         } else {
/*  1753 */           templist = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,AM_UserPasswordTable.USERNAME,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID and " + eumChildListCond + " and AM_HOLISTICAPPLICATION.HAID NOT IN " + id_temp + " and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID order by RESOURCENAME");
/*       */         }
/*       */         
/*       */ 
/*  1757 */         ArrayList listofHAID = new ArrayList(5);
/*  1758 */         for (int i = 0; i < templist.size(); i++)
/*       */         {
/*  1760 */           ArrayList oneRow = (ArrayList)templist.get(i);
/*  1761 */           String own = (String)oneRow.get(3);
/*  1762 */           if (own.equals(userName))
/*       */           {
/*       */ 
/*       */ 
/*  1766 */             Object tempHaid = oneRow.get(6);
/*  1767 */             if (listofHAID.indexOf(tempHaid) == -1)
/*       */             {
/*  1769 */               listofHAID.add(tempHaid);
/*  1770 */               list.add(oneRow);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       else {
/*  1776 */         String bsgFilterCondn = "";
/*  1777 */         String bsgType = "0";
/*  1778 */         if (isIT360CustSpecifcMSPFlag)
/*       */         {
/*  1780 */           bsgType = "1";
/*  1781 */           Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  1782 */           bsgFilterCondn = " AND AM_HOLISTICAPPLICATION.TYPE=" + bsgType + " AND " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */         }
/*       */         
/*  1785 */         if (healthfiltertype.equals("Critical/Warning")) {
/*  1786 */           list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE,Alert.SEVERITY from AM_ManagedObject,AM_HOLISTICAPPLICATION,Alert where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID" + bsgFilterCondn + " and Alert.source=AM_ManagedObject.resourceid and " + eumChildListCond + " and  Alert.groupname='AppManager' and Alert.severity!=5 and AM_HOLISTICAPPLICATION.HAID NOT IN " + id_temp + " order by RESOURCENAME");
/*       */         } else {
/*  1788 */           list = this.mo.getRows("select RESOURCENAME,DISPLAYNAME,-1,OWNER,CREATIONDATE,MODIFIEDDATE,AM_HOLISTICAPPLICATION.HAID,AM_ManagedObject.TYPE from AM_ManagedObject,AM_HOLISTICAPPLICATION where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID" + bsgFilterCondn + " and " + eumChildListCond + " and AM_HOLISTICAPPLICATION.HAID NOT IN " + id_temp + " order by RESOURCENAME");
/*       */         }
/*       */       }
/*       */       
/*  1792 */       String zohourl = "https://monitor.zoho.com/gac";
/*       */       
/*       */       try
/*       */       {
/*  1796 */         ArrayList rows1 = this.mo.getRows("select value from AM_GLOBALCONFIG where name='zorro.monitors.url'");
/*  1797 */         ArrayList row1 = (ArrayList)rows1.get(0);
/*  1798 */         zohourl = (String)row1.get(0);
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  1802 */         exc.printStackTrace();
/*       */       }
/*  1804 */       request.setAttribute("zohourl", zohourl);
/*  1805 */       request.setAttribute("monitorgrouplist", list);
/*  1806 */       List temp = new ArrayList();
/*  1807 */       temp.add("18");
/*  1808 */       temp.add("17");
/*  1809 */       List avail_imgs = new ArrayList();
/*  1810 */       List health_imgs = new ArrayList();
/*  1811 */       Properties p = FaultUtil.getStatus(resids, temp);
/*  1812 */       String status_sep = "#";
/*  1813 */       for (int i = 0; i < resids.size(); i++)
/*       */       {
/*       */ 
/*  1816 */         String avail_img = getSeverityImageForAvailability(p.getProperty(resids.get(i) + status_sep + "17"));
/*  1817 */         String health_img = getSeverityImageForHealth(p.getProperty(resids.get(i) + status_sep + "18"));
/*  1818 */         health_img = health_img.replaceAll("'", "\\\\'");
/*  1819 */         avail_img = avail_img.replaceAll("'", "\\\\'");
/*  1820 */         avail_imgs.add(avail_img);
/*  1821 */         health_imgs.add(health_img);
/*       */       }
/*  1823 */       ArrayList alertlists = new ArrayList();
/*  1824 */       alertlists.add(idnames);
/*  1825 */       alertlists.add(resids);
/*  1826 */       alertlists.add(avail_imgs);
/*  1827 */       alertlists.add(health_imgs);
/*  1828 */       request.setAttribute("alertlists", alertlists);
/*       */       
/*  1830 */       return new ActionForward("/jsp/ZohoTclView.jsp");
/*       */     }
/*       */     
/*       */ 
/*  1834 */     String customizetab = request.getParameter("customizetab");
/*  1835 */     if (customizetab != null) {
/*  1836 */       return new ActionForward("/jsp/PlasmaViewFromTab.jsp");
/*       */     }
/*  1838 */     request.setAttribute("jumpIcon", Boolean.toString(jumpIcon));
/*  1839 */     return new ActionForward("/jsp/PlasmaView.jsp");
/*       */   }
/*       */   
/*       */ 
/*       */   private String getSeverityImageForAvailability(Object severity)
/*       */   {
/*  1845 */     long j = 0L;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  1864 */     if (severity == null)
/*       */     {
/*  1866 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_availability_unknown_mover.gif',1)\">";
/*       */     }
/*  1868 */     if (severity.equals("5"))
/*       */     {
/*  1870 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_availability_up_mover.gif',1)\">";
/*       */     }
/*  1872 */     if (severity.equals("1"))
/*       */     {
/*  1874 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_availability_down_mover.gif',1)\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1879 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_availability_unknown_mover.gif',1)\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String getSeverityImageForHealth(String severity)
/*       */   {
/*  1886 */     long j = 0L;
/*  1887 */     if (severity == null)
/*       */     {
/*  1889 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_health_unknown_mover.gif',1)\">";
/*       */     }
/*  1891 */     if (severity.equals("5"))
/*       */     {
/*  1893 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++j + "\" onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_health_clear_mover.gif',2)\">";
/*       */     }
/*  1895 */     if (severity.equals("4"))
/*       */     {
/*  1897 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++j + "\" onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_health_warning_mover.gif',2)\">";
/*       */     }
/*  1899 */     if (severity.equals("1"))
/*       */     {
/*  1901 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++j + "\" onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_health_critical_mover.gif',2)\">";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  1906 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + j + "','','/images/icon_health_unknown_mover.gif',1)\">";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private ArrayList parseFileforIds()
/*       */   {
/*  1933 */     ArrayList toreturn = new ArrayList();
/*  1934 */     Properties p = new Properties();
/*       */     try
/*       */     {
/*  1937 */       File f = new File(System.getProperty("webnms.rootdir") + File.separator + "conf" + File.separator + "zohoidmapper.conf");
/*  1938 */       FileInputStream fis = new FileInputStream(f);
/*  1939 */       p.load(fis);
/*  1940 */       Enumeration en = p.propertyNames();
/*  1941 */       ArrayList resids = new ArrayList();
/*  1942 */       ArrayList idnames = new ArrayList();
/*  1943 */       while (en.hasMoreElements())
/*       */       {
/*  1945 */         String ele = (String)en.nextElement();
/*  1946 */         idnames.add(ele);
/*  1947 */         resids.add(p.getProperty(ele));
/*       */       }
/*  1949 */       toreturn.add(idnames);
/*  1950 */       toreturn.add(resids);
/*       */     }
/*       */     catch (Exception exc)
/*       */     {
/*  1954 */       exc.printStackTrace();
/*       */     }
/*  1956 */     return toreturn;
/*       */   }
/*       */   
/*       */   private void setWTAProps(HttpServletRequest request, ActionForm form) {
/*  1960 */     String resourceid = request.getParameter("resourceid");
/*  1961 */     String query = "select DISPLAYNAME,WTARESOURCEID,RESOURCENAME from AM_MANAGEDOBJECT_TO_WTA,AM_ManagedObject where AM_MANAGEDOBJECT_TO_WTA.RESOURCEID=" + resourceid + " and AM_MANAGEDOBJECT_TO_WTA.WTARESOURCEID=AM_ManagedObject.RESOURCEID";
/*  1962 */     ResultSet set = null;
/*       */     try {
/*  1964 */       set = AMConnectionPool.executeQueryStmt(query);
/*  1965 */       if (set.next())
/*       */       {
/*  1967 */         request.setAttribute("wtaresourceid", set.getInt("WTARESOURCEID") + "");
/*  1968 */         request.setAttribute("wtaresourcename", set.getString("RESOURCENAME"));
/*  1969 */         ((AMActionForm)form).setDisplayname(set.getString("DISPLAYNAME"));
/*       */       }
/*       */       else
/*       */       {
/*  1973 */         query = "select DISPLAYNAME,RESOURCEID,RESOURCENAME from AM_ManagedObject where TYPE='WTA' and AM_ManagedObject.RESOURCEID=" + resourceid;
/*  1974 */         ResultSet set1 = AMConnectionPool.executeQueryStmt(query);
/*  1975 */         if (set1.next()) {
/*  1976 */           request.setAttribute("wtaresourceid", set1.getInt("RESOURCEID") + "");
/*  1977 */           request.setAttribute("wtaresourcename", set1.getString("RESOURCENAME"));
/*  1978 */           ((AMActionForm)form).setDisplayname(set1.getString("DISPLAYNAME"));
/*       */         }
/*  1980 */         AMConnectionPool.closeStatement(set1);
/*       */       }
/*       */       
/*  1983 */       String poll = (String)request.getAttribute("reloadperiod");
/*  1984 */       if (poll == null) {
/*  1985 */         poll = "300";
/*       */       }
/*  1987 */       String wtaresid = (String)request.getAttribute("wtaresourceid");
/*  1988 */       if (wtaresid != null) {
/*  1989 */         HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(wtaresid, Long.parseLong(poll));
/*  1990 */         if (map != null)
/*       */         {
/*  1992 */           request.setAttribute("wtasysteminfo", map);
/*       */         }
/*       */       }
/*       */       return;
/*       */     }
/*       */     catch (Exception e) {
/*  1998 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*  2002 */       if (set != null) {
/*       */         try {
/*  2004 */           AMConnectionPool.closeStatement(set);
/*       */         }
/*       */         catch (Exception e) {}
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   private ArrayList servers_bytes(String resourceid)
/*       */   {
/*  2014 */     toreturn = new ArrayList();
/*       */     
/*  2016 */     String query = DBQueryUtil.getTopNValues("select * from AM_ApacheDetails where resID=" + resourceid + " order by collectiontime desc", "1");
/*  2017 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  2018 */     ResultSet set = null;
/*       */     try
/*       */     {
/*  2021 */       set = AMConnectionPool.executeQueryStmt(query);
/*  2022 */       if (set.next())
/*       */       {
/*  2024 */         toreturn.add(set.getString("ENABLESTATUS"));
/*  2025 */         toreturn.add(set.getString("BUSYSERVERS"));
/*  2026 */         toreturn.add(set.getString("IDLESERVERS"));
/*  2027 */         toreturn.add(set.getString("BYTESPERSEC"));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2044 */       return toreturn;
/*       */     }
/*       */     catch (Exception exc)
/*       */     {
/*  2032 */       exc.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*       */       try
/*       */       {
/*  2038 */         AMConnectionPool.closeStatement(set);
/*       */       }
/*       */       catch (Exception exc1) {}
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showResourceForResourceID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  2055 */     response.setContentType("text/html;charset=utf-8");
/*  2056 */     String resourceid = request.getParameter("resourceid");
/*  2057 */     String fromwhere = request.getParameter("fromwhere");
/*       */     
/*  2059 */     ArrayList rows = this.mo.getPropertiesList("select * from AM_ManagedObject where resourceid=" + resourceid);
/*  2060 */     System.out.println("Query ::: " + rows);
/*  2061 */     request.setAttribute("resourcedetails_morow", rows);
/*       */     
/*       */ 
/*  2064 */     if ((Constants.isExtDeviceConfigured()) && (resourceid != null) && (DBUtil.isSite24x7Monitor(resourceid))) {
/*  2065 */       return new ActionForward("/extDeviceAction.do?method=site24x7Reports&resourceid=" + resourceid);
/*       */     }
/*       */     
/*  2068 */     if (rows.size() > 0)
/*       */     {
/*  2070 */       Properties props = (Properties)rows.get(0);
/*  2071 */       String resourcetype = props.getProperty("TYPE");
/*  2072 */       String name = props.getProperty("RESOURCENAME");
/*  2073 */       String displayname = props.getProperty("DISPLAYNAME");
/*  2074 */       String additionalparameters = "";
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2104 */       if (Constants.isIt360)
/*       */       {
/*  2106 */         String resGroup = Constants.getResgroupForResourceType(resourcetype);
/*  2107 */         if ((resGroup != null) && (resGroup.equals("SYS")) && (resGroup.equals("VIR")))
/*       */         {
/*  2109 */           request.setAttribute("oldtab", "2");
/*       */         }
/*  2111 */         else if ((resGroup != null) && (resGroup.equals("APPLICATION")))
/*       */         {
/*  2113 */           request.setAttribute("oldtab", "3");
/*       */         }
/*       */       }
/*  2116 */       if ((resourcetype.equals("UrlEle")) || (resourcetype.equals("RBMURL")))
/*       */       {
/*       */ 
/*  2119 */         ArrayList rows1 = this.mo.getRows("select URLSEQID,displayname from AM_URLSequence,AM_ManagedObject where resourceid=URLSEQID and URLID=" + resourceid);
/*  2120 */         if (rows1.size() > 0)
/*       */         {
/*  2122 */           rows1 = (ArrayList)rows1.get(0);
/*  2123 */           String urlsequenceid = (String)rows1.get(0);
/*  2124 */           String urlseqname = (String)rows1.get(1);
/*  2125 */           additionalparameters = "&parentname=" + urlseqname + "&parentid=" + urlsequenceid;
/*       */         }
/*       */       } else {
/*  2128 */         if (resourcetype.equals("Ping Monitor")) {
/*  2129 */           return new ActionForward("/showresource.do?resourceid=" + resourceid + "&resourcename=" + name + "&type=" + resourcetype + "&method=showdetails&moname=" + displayname);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  2134 */         if (resourcetype.equals("HAI"))
/*       */         {
/*       */ 
/*  2137 */           response.sendRedirect("/showapplication.do?method=showApplication&haid=" + resourceid);
/*  2138 */           return null;
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2147 */       if (resourcetype.equals("FreeBSD"))
/*       */       {
/*  2149 */         resourcetype = "FreeBSD / OpenBSD";
/*       */       }
/*       */       
/*  2152 */       System.out.println("The return path===>/showresource.do?type=" + resourcetype + "&resourceid=" + resourceid + "&moname=" + name + "&method=showdetails&resourcename=" + displayname + additionalparameters);
/*  2153 */       String returnpath = "/showresource.do?type=" + resourcetype + "&resourceid=" + resourceid + "&moname=" + name + "&method=showdetails&resourcename=" + displayname + additionalparameters;
/*  2154 */       if (fromwhere != null) {
/*  2155 */         returnpath = returnpath + "&fromwhere=" + fromwhere;
/*       */       }
/*  2157 */       return new ActionForward(returnpath);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2162 */     AMLog.debug("Going to showresourcetypes method");
/*  2163 */     return new ActionForward("/showresource.do?method=showResourceTypes");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showResourceTypesAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  2176 */     String customValue = "";
/*  2177 */     String forbulkAssign = "";
/*  2178 */     String searchCondition = "";
/*       */     try {
/*  2180 */       customValue = request.getParameter("customValue");
/*       */       
/*       */ 
/*  2183 */       if ((request.getParameter("searchnextpage") != null) && (request.getParameter("searchnextpage").equals("true"))) {
/*  2184 */         String searchQ = request.getParameter("searchString");
/*  2185 */         String searchOpt = request.getParameter("searchOption");
/*  2186 */         SearchAction searchObj = new SearchAction();
/*  2187 */         String query = searchObj.searchQuery(request, searchQ, searchOpt, "", false);
/*  2188 */         ResultSet rs = null;
/*  2189 */         StringBuilder searchResourceids = new StringBuilder();
/*       */         try {
/*  2191 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  2192 */           while (rs.next()) {
/*  2193 */             searchResourceids.append(rs.getString(1)).append(",");
/*       */           }
/*  2195 */           searchResourceids.append("-1");
/*  2196 */           request.setAttribute("searchresourceids", searchResourceids.toString());
/*  2197 */           request.setAttribute("searchString", searchQ);
/*       */         } catch (Exception ex) {
/*  2199 */           ex.printStackTrace();
/*       */         } finally {
/*  2201 */           if (rs != null) {
/*  2202 */             AMConnectionPool.closeStatement(rs);
/*       */           }
/*       */         }
/*  2205 */         searchCondition = "&search=true";
/*       */       }
/*  2207 */       forbulkAssign = request.getParameter("forbulkAssign");
/*  2208 */       ((AMActionForm)form).setUsername(request.getRemoteUser());
/*  2209 */       ArrayList listviewresourcetype = new ArrayList();
/*  2210 */       if (Constants.isIt360)
/*       */       {
/*  2212 */         listviewresourcetype = ((AMActionForm)form).getResourceTypes_listview(request);
/*       */       }
/*       */       else
/*       */       {
/*  2216 */         listviewresourcetype = ((AMActionForm)form).getResourceTypes_listview();
/*       */       }
/*  2218 */       request.setAttribute("listviewresourcetype", listviewresourcetype);
/*  2219 */     } catch (Exception ex) { ex.printStackTrace(); }
/*  2220 */     request.setAttribute("reloadperiod", "60");
/*       */     
/*  2222 */     ResultSet rs = null;
/*       */     
/*       */     try
/*       */     {
/*  2226 */       if (!"All".equals(request.getParameter("group"))) {
/*  2227 */         String vmResidList = "";
/*  2228 */         String vmQuery = "select RESOURCEID from AM_ManagedObject where type ='VirtualMachine'";
/*  2229 */         rs = AMConnectionPool.executeQueryStmt(vmQuery);
/*  2230 */         while (rs.next())
/*       */         {
/*  2232 */           vmResidList = vmResidList + rs.getString("RESOURCEID") + ",";
/*       */         }
/*  2234 */         if ((vmResidList != null) && (vmResidList.length() > 0))
/*       */         {
/*  2236 */           vmResidList = vmResidList.substring(0, vmResidList.length() - 1);
/*       */         }
/*  2238 */         request.setAttribute("VirtualMachines", vmResidList);
/*       */       }
/*  2240 */       String resourcetype = request.getParameter("network");
/*  2241 */       if (resourcetype != null) {
/*  2242 */         LinkedHashMap infraview = (LinkedHashMap)ConfMonitorConfiguration.getInstance().getInfrastructureView(resourcetype);
/*  2243 */         if ((infraview != null) && (infraview.size() > 0))
/*       */         {
/*  2245 */           listInfraViewDetails(request, infraview);
/*       */         }
/*       */       }
/*  2248 */       if (request.getParameter("fromwhere") != null) {
/*  2249 */         ActionMessages messages = new ActionMessages();
/*  2250 */         if (request.getParameter("fromwhere").equals("deleteMO")) {
/*  2251 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitor.deleted.text")));
/*       */         }
/*  2253 */         setActionMessages(request, messages);
/*  2254 */         saveMessages(request, messages);
/*       */       }
/*       */       
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/*  2260 */       ee.printStackTrace();
/*       */     }
/*       */     finally {
/*  2263 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*  2265 */     return new ActionForward("/jsp/networkdetails.jsp?network=" + request.getParameter("network") + "&customField=" + customValue + searchCondition + "&assignCustomValues=" + forbulkAssign);
/*       */   }
/*       */   
/*       */   public ActionForward getTotalCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  2271 */     ResultSet rs = null;
/*       */     try {
/*  2273 */       PrintWriter out = response.getWriter();
/*  2274 */       response.setContentType("text/html");
/*  2275 */       if (request.getSession().getAttribute("totalcountqueries") != null) {
/*  2276 */         countqueries = (ArrayList)request.getSession().getAttribute("totalcountqueries");
/*  2277 */         Iterator i = countqueries.iterator();
/*  2278 */         String countquery = "";
/*  2279 */         int total = 0;
/*  2280 */         while (i.hasNext()) {
/*  2281 */           countquery = (String)i.next();
/*  2282 */           rs = AMConnectionPool.executeQueryStmt(countquery);
/*  2283 */           if (rs.next()) {
/*  2284 */             total += rs.getInt(1);
/*       */           }
/*  2286 */           if (rs != null) {
/*  2287 */             rs.close();
/*       */           }
/*       */         }
/*  2290 */         out.println(total);
/*  2291 */         out.flush();
/*  2292 */         return null;
/*       */       }
/*       */       
/*  2295 */       out.println("Page Expired..");
/*  2296 */       out.flush();
/*  2297 */       return null;
/*       */     }
/*       */     catch (Exception e) {
/*       */       ArrayList<String> countqueries;
/*  2301 */       AMLog.debug("Error in getting the total count " + e.getMessage());
/*  2302 */       return null;
/*       */     }
/*       */     finally {
/*  2305 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */   }
/*       */   
/*       */   private boolean showMessage(HttpServletRequest request, ActionMessages messages) throws Exception {
/*  2310 */     boolean toret = false;
/*  2311 */     ResultSet rs = null;
/*  2312 */     if (request.isUserInRole("OPERATOR"))
/*       */     {
/*       */       try {
/*  2315 */         rs = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME in ('allowOperatorManage','allowOperatorUnmanageAndReset')");
/*  2316 */         while (rs.next())
/*       */         {
/*  2318 */           String val = rs.getString("VALUE");
/*  2319 */           if (val.equals("true"))
/*       */           {
/*  2321 */             toret = true;
/*       */           }
/*       */         }
/*       */       } catch (Exception ee) {
/*  2325 */         ee.printStackTrace();
/*       */       }
/*       */       finally {
/*  2328 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */       
/*  2331 */       if (!toret)
/*       */       {
/*  2333 */         request.setAttribute("OperatorNotAllowed", FormatUtil.getString("am.webclient.manageunmanage.error"));
/*  2334 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.manageunmanage.error"));
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*  2339 */       toret = true;
/*       */     }
/*  2341 */     return toret;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showResourceTypes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  2350 */     String customValue = null;
/*  2351 */     if (request.getParameter("customValue") != null) {
/*  2352 */       customValue = request.getParameter("customValue").toString();
/*       */     }
/*       */     
/*  2355 */     String direct = request.getParameter("direct");
/*  2356 */     boolean CategoryTypeFlag = false;boolean allAreNull = false;
/*  2357 */     String network = request.getParameter("selectedNetwork");
/*  2358 */     String requestedmethod = request.getParameter("method");
/*  2359 */     String monitor_viewtype = request.getParameter("monitor_viewtype");
/*  2360 */     if (monitor_viewtype == null) {
/*  2361 */       monitor_viewtype = "";
/*       */     }
/*  2363 */     String detailspage = request.getParameter("detailspage");
/*  2364 */     String resourcetype = request.getParameter("network");
/*  2365 */     String groupFromReq = request.getParameter("group");
/*       */     
/*  2367 */     if (Constants.isIt360)
/*       */     {
/*  2369 */       String isReqFromAdmin = request.getParameter("isReqFromAdmin");
/*  2370 */       if ((request.isUserInRole("USERS")) || (request.isUserInRole("OPERATOR")))
/*       */       {
/*  2372 */         request.setAttribute("isReqFromAdmin", "false");
/*       */       }
/*  2374 */       else if (isReqFromAdmin != null)
/*       */       {
/*  2376 */         request.setAttribute("isReqFromAdmin", isReqFromAdmin);
/*       */       }
/*       */       else
/*       */       {
/*  2380 */         String selectedTab = EnterpriseUtil.getSelectedTab(request);
/*  2381 */         if ((selectedTab != null) && (selectedTab.equals("Admin")))
/*       */         {
/*  2383 */           request.setAttribute("isReqFromAdmin", "true");
/*       */         }
/*       */       }
/*       */     }
/*  2387 */     request.setAttribute("reloadperiod", "120");
/*       */     
/*       */ 
/*  2390 */     if ((resourcetype != null) && (resourcetype.equals("VCenter")))
/*       */     {
/*  2392 */       return new ActionForward("/showresource.do?method=showMonitorGroupView&mgType=3");
/*       */     }
/*       */     
/*  2395 */     if (monitor_viewtype.equals("categoryview")) {
/*  2396 */       Cookie[] cookies = request.getCookies();
/*  2397 */       for (int i = 0; i < cookies.length; i++) {
/*  2398 */         if ((cookies[i].getName().equals("Category_type")) && (cookies[i].getValue().equals("showAddedMonitors"))) {
/*  2399 */           CategoryTypeFlag = true;
/*  2400 */           break;
/*       */         }
/*       */       }
/*  2403 */       if ((CategoryTypeFlag) || ((request.getParameter("defaultview1") != null) && (request.getParameter("defaultview1").equals("true")))) {
/*  2404 */         String role = "";
/*  2405 */         String owner = request.getRemoteUser();
/*  2406 */         if (ClientDBUtil.isPrivilegedUser(request))
/*       */         {
/*  2408 */           role = "operator";
/*       */         }
/*       */         else
/*       */         {
/*  2412 */           role = "user";
/*       */         }
/*  2414 */         MyPageAction mypage = new MyPageAction();
/*  2415 */         HashMap map = mypage.getAllAddedMonitorTypes(owner, role, new ArrayList(), false, "-1", request);
/*  2416 */         int totalNoOfMonitors = Integer.parseInt((String)map.get("totalMonCnt"));
/*  2417 */         map.remove("totalMonCnt");
/*  2418 */         Map<String, Hashtable> sortedMap = new java.util.TreeMap(map);
/*       */         
/*  2420 */         request.setAttribute("hmap", sortedMap);
/*  2421 */         request.setAttribute("totalMonitorCount", Integer.valueOf(totalNoOfMonitors));
/*  2422 */         return new ActionForward("/jsp/catagoryListView.jsp");
/*       */       }
/*       */     }
/*       */     try
/*       */     {
/*  2427 */       if (detailspage != null) {
/*  2428 */         ((AMActionForm)form).setUsername(request.getRemoteUser());
/*  2429 */         ArrayList listviewresourcetype = new ArrayList();
/*  2430 */         if (Constants.isIt360)
/*       */         {
/*  2432 */           listviewresourcetype = ((AMActionForm)form).getResourceTypes_listview(request);
/*       */         }
/*       */         else
/*       */         {
/*  2436 */           listviewresourcetype = ((AMActionForm)form).getResourceTypes_listview();
/*       */         }
/*       */         
/*  2439 */         request.setAttribute("listviewresourcetype", listviewresourcetype);
/*       */       }
/*  2441 */     } catch (Exception ex) { ex.printStackTrace(); }
/*  2442 */     if (Constants.sqlManager)
/*       */     {
/*  2444 */       if ((resourcetype == null) || (!resourcetype.equals("MSSQL-DB-server"))) {
/*  2445 */         return new ActionForward("/showresource.do?group=All&method=showResourceMSSQL&customValue=" + customValue);
/*       */       }
/*       */     }
/*  2448 */     if (Constants.isIt360)
/*       */     {
/*  2450 */       Object isReqFromAdmin = request.getAttribute("isReqFromAdmin");
/*  2451 */       if ((isReqFromAdmin != null) && (isReqFromAdmin.toString().equals("true")))
/*       */       {
/*  2453 */         request.setAttribute("oldtab", "6");
/*       */       }
/*       */       else
/*       */       {
/*  2457 */         if (resourcetype != null)
/*       */         {
/*  2459 */           if ((resourcetype.contains("Windows")) || (resourcetype.equals("Node")) || (resourcetype.toLowerCase().indexOf("novell") != -1) || (resourcetype.equals("Linux")) || (resourcetype.equals("snmp-node")) || (resourcetype.equals("SUN")) || (resourcetype.equals("SUN PC")) || (resourcetype.equals("AIX")) || (resourcetype.equals("HP-UX")) || (resourcetype.equals("HP-TRU64")) || (resourcetype.equals("FreeBSD / OpenBSD")) || (resourcetype.equals("OpenBSD")) || (resourcetype.equals("FreeBSD")) || (resourcetype.equals("Mac OS")) || (resourcetype.equals("Unknown")) || (resourcetype.equals("VirtualMachine")) || (resourcetype.equals("VMWare ESX/ESXi")))
/*       */           {
/*  2461 */             request.setAttribute("oldtab", "2");
/*       */           }
/*  2463 */           else if (!resourcetype.contains("OpManager"))
/*       */           {
/*  2465 */             request.setAttribute("oldtab", "3");
/*       */           }
/*  2467 */           else if (resourcetype.contains("OpManager"))
/*       */           {
/*  2469 */             request.setAttribute("oldtab", "1");
/*       */           }
/*  2471 */           else if (resourcetype.contains("OpStor"))
/*       */           {
/*  2473 */             request.setAttribute("oldtab", "9");
/*       */           }
/*       */         }
/*       */         
/*  2477 */         if (groupFromReq != null)
/*       */         {
/*  2479 */           if ((groupFromReq.equals("SYS")) || (groupFromReq.equals("VIR")))
/*       */           {
/*  2481 */             request.setAttribute("oldtab", "2");
/*       */           }
/*  2483 */           if (groupFromReq.equals("NWD"))
/*       */           {
/*  2485 */             request.setAttribute("oldtab", "1");
/*       */           }
/*  2487 */           if ((resourcetype != null) && (resourcetype.contains("OpStor")) && (groupFromReq.equals("NWD")))
/*       */           {
/*  2489 */             request.setAttribute("oldtab", "9");
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  2494 */         String selectedTab = EnterpriseUtil.getSelectedTab(request);
/*  2495 */         if (selectedTab != null)
/*       */         {
/*  2497 */           if (selectedTab.equals("Server"))
/*       */           {
/*  2499 */             request.setAttribute("oldtab", "2");
/*       */           }
/*  2501 */           else if (selectedTab.equals("Application"))
/*       */           {
/*  2503 */             request.setAttribute("oldtab", "3");
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  2508 */     if (EnterpriseUtil.isAdminServer())
/*       */     {
/*  2510 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  2512 */         DataCollectionControllerUtil.setUnmanaged_nodes(DataCollectionControllerUtil.getUnManagedNodes(request));
/*  2513 */         DataCollectionControllerUtil.setManaged_nodes(DataCollectionControllerUtil.getManagedNodes(request));
/*       */       }
/*       */       else
/*       */       {
/*  2517 */         DataCollectionControllerUtil.setUnmanaged_nodes(DataCollectionControllerUtil.getUnManagedNodes());
/*  2518 */         DataCollectionControllerUtil.setManaged_nodes(DataCollectionControllerUtil.getManagedNodes());
/*       */       }
/*       */     }
/*       */     String resourceid;
/*  2522 */     if (!EnterpriseUtil.isAdminServer())
/*       */     {
/*  2524 */       if ((direct != null) && (direct.equals("true")) && (request.getParameter("detailspage") != null))
/*       */       {
/*  2526 */         String tempResType = resourcetype;
/*  2527 */         if (tempResType.equals("FreeBSD / OpenBSD")) {
/*  2528 */           tempResType = "FreeBSD";
/*       */         }
/*  2530 */         types = Constants.resourceTypes;
/*       */         
/*       */ 
/*  2533 */         String filterCondn = "";
/*  2534 */         if (EnterpriseUtil.isIt360MSPEdition())
/*       */         {
/*       */ 
/*  2537 */           filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request, true));
/*       */         }
/*  2539 */         String query = "select resourceid from AM_ManagedObject where type='" + tempResType + "'" + filterCondn;
/*  2540 */         if (tempResType.contains("Site24x7")) {
/*  2541 */           query = "select resourceid from AM_ManagedObject where type like '%Site24x7-%' " + filterCondn;
/*       */         }
/*  2543 */         ArrayList rows = this.mo.getRows(query);
/*  2544 */         if (rows.size() == 1)
/*       */         {
/*  2546 */           rows = (ArrayList)rows.get(0);
/*  2547 */           resourceid = (String)rows.get(0);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2557 */     Wield w = Wield.getInstance();
/*  2558 */     String usertype = w.getUserType();
/*  2559 */     request.setAttribute("usertype", usertype);
/*       */     
/*       */ 
/*       */ 
/*  2563 */     String group = "APP";
/*  2564 */     String owner = request.getRemoteUser();
/*  2565 */     String query = null;
/*  2566 */     ActionMessages messages = new ActionMessages();
/*  2567 */     if (request.getParameter("fromwhere") != null)
/*       */     {
/*  2569 */       if (request.getParameter("fromwhere").equals("bulkupdate"))
/*       */       {
/*  2571 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.username.updated.text")));
/*       */       }
/*  2573 */       else if (request.getParameter("fromwhere").equals("deleteMO"))
/*       */       {
/*  2575 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitor.deleted.text")));
/*       */       }
/*  2577 */       else if (request.getParameter("fromwhere").equals("pollingMessage"))
/*       */       {
/*  2579 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.polling.updated.text")));
/*       */       }
/*  2581 */       else if ((request.getParameter("fromwhere").equals("managemonitors")) && (showMessage(request, messages)))
/*       */       {
/*  2583 */         if ((request.getParameter("messageneeded") == null) || (request.getParameter("messageneeded").trim().equals("")) || (!request.getParameter("messageneeded").equals("false")))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2589 */           String messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  2590 */           if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*       */           {
/*  2592 */             messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*       */           }
/*  2594 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*       */         }
/*       */       }
/*  2597 */       else if ((request.getParameter("fromwhere").equals("unmanagemonitors")) && (showMessage(request, messages)))
/*       */       {
/*  2599 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.unmanaged.datacollectionstops.text")));
/*       */       }
/*  2601 */       else if ((request.getParameter("fromwhere").equals("unmanageandresetmonitors")) && (showMessage(request, messages)))
/*       */       {
/*  2603 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.unmanagedandreset.datacollectionstops.text")));
/*       */       }
/*  2605 */       else if (request.getParameter("fromwhere").equals("unabletocreate"))
/*       */       {
/*  2607 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction1", String.valueOf(FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted())));
/*  2608 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction2"));
/*  2609 */       } else if (request.getParameter("fromwhere").equals("operator"))
/*       */       {
/*       */ 
/*  2612 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.userauthorization.unaunthorised")));
/*       */       }
/*       */       
/*       */ 
/*  2616 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*  2619 */     if (("WTA".equals(resourcetype)) && (FreeEditionDetails.getFreeEditionDetails().isWTAAddonPresent()))
/*       */     {
/*  2621 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.wta.upgrade.message")));
/*  2622 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  2627 */     String types_copy = types;
/*  2628 */     if (network != null)
/*       */     {
/*  2630 */       types = changeNetworkTypes(types);
/*       */     }
/*       */     
/*  2633 */     HashMap<String, ArrayList> CategoryView = new HashMap();
/*       */     
/*  2635 */     String eumChildString = Constants.getEUMChildString();
/*  2636 */     request.setAttribute("eumChildString", eumChildString);
/*  2637 */     String eumChildListCond = " and AM_ManagedObject.resourceid NOT IN (" + eumChildString + ") ";
/*       */     
/*  2639 */     String filterCondn1 = "";
/*  2640 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*  2642 */       filterCondn1 = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request, true));
/*       */     }
/*       */     
/*       */ 
/*  2646 */     String cond1 = " ('HAI','OpManager-Interface') ";
/*       */     
/*  2648 */     String query1 = "select MAX(AM_ManagedResourceType.RESOURCEGROUP),AM_ManagedResourceType.SUBGROUP, MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE),MAX(AM_MONITOR_TYPES.PARENTTYPE) from AM_ManagedResourceType left outer join AM_MONITOR_TYPES on AM_MONITOR_TYPES.TYPEID=AM_ManagedResourceType.RESOURCETYPEID left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and SUBGROUP NOT IN " + cond1 + eumChildListCond + filterCondn1 + " group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH,RESOURCEGROUP order by RESOURCEGROUP,SUBGROUP";
/*  2649 */     if (ClientDBUtil.isPrivilegedUser(request))
/*       */     {
/*  2651 */       if (Constants.isUserResourceEnabled()) {
/*  2652 */         String loginUserid = Constants.getLoginUserid(request);
/*  2653 */         query1 = "select MAX(AM_ManagedResourceType.RESOURCEGROUP),AM_ManagedResourceType.SUBGROUP, MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE),MAX(AM_MONITOR_TYPES.PARENTTYPE) from AM_ManagedResourceType left outer join AM_MONITOR_TYPES on AM_MONITOR_TYPES.TYPEID=AM_ManagedResourceType.RESOURCETYPEID left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ") " + eumChildListCond + filterCondn1 + " and SUBGROUP NOT IN " + cond1 + " group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH,RESOURCEGROUP order by RESOURCEGROUP,SUBGROUP";
/*       */       } else {
/*  2655 */         Vector permittedResourceIds = ClientDBUtil.getResourceIdentity(owner, request, null);
/*  2656 */         query1 = "select MAX(AM_ManagedResourceType.RESOURCEGROUP),AM_ManagedResourceType.SUBGROUP, MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE),MAX(AM_MONITOR_TYPES.PARENTTYPE) from AM_ManagedResourceType left outer join AM_MONITOR_TYPES on AM_MONITOR_TYPES.TYPEID=AM_ManagedResourceType.RESOURCETYPEID left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + DependantMOUtil.getCondition("RESOURCEID", permittedResourceIds) + eumChildListCond + filterCondn1 + " and SUBGROUP NOT IN " + cond1 + " group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH,RESOURCEGROUP order by RESOURCEGROUP,SUBGROUP";
/*       */       }
/*       */     }
/*       */     
/*  2660 */     ResultSet rs1 = null;
/*  2661 */     AMLog.debug("query1::: " + query1);
/*  2662 */     rs1 = AMConnectionPool.executeQueryStmt(query1);
/*       */     
/*  2664 */     while (rs1.next())
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2670 */       if (rs1.getString(2).equals("WTA"))
/*       */       {
/*  2672 */         String wtaCount = rs1.getString(4);
/*  2673 */         if ("0".equals(wtaCount)) {}
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  2678 */         ArrayList alllst = new ArrayList();
/*  2679 */         if (CategoryView.containsKey(rs1.getString(1)))
/*       */         {
/*  2681 */           ArrayList subCategory = (ArrayList)CategoryView.get(rs1.getString(1));
/*  2682 */           ArrayList sub = new ArrayList();
/*  2683 */           sub.add(rs1.getString(2));
/*  2684 */           if (rs1.getString(1).equals("SYS")) {
/*  2685 */             sub.add(rs1.getString(2));
/*       */           }
/*  2687 */           else if (rs1.getString(2).equals("Site24x7")) {
/*  2688 */             sub.add("Site24x7 Monitors");
/*       */           }
/*  2690 */           else if (rs1.getString(2).indexOf("OpStor") != -1)
/*       */           {
/*  2692 */             sub.add("Storage Devices");
/*       */           }
/*  2694 */           else if (rs1.getString(2).equals("VirtualMachine"))
/*       */           {
/*  2696 */             sub.add("Virtual Machine");
/*       */           }
/*  2698 */           else if (rs1.getString(2).equals("Container"))
/*       */           {
/*  2700 */             sub.add(rs1.getString(2));
/*       */           }
/*       */           else
/*       */           {
/*  2704 */             sub.add(rs1.getString(3));
/*       */           }
/*       */           
/*  2707 */           sub.add(rs1.getString(4));
/*  2708 */           sub.add(rs1.getString(5));
/*  2709 */           sub.add(rs1.getString(6));
/*  2710 */           sub.add(rs1.getString(7));
/*  2711 */           sub.add(rs1.getString(8));
/*  2712 */           subCategory.add(sub);
/*  2713 */           CategoryView.put(rs1.getString(1), subCategory);
/*       */         } else {
/*  2715 */           ArrayList sub = new ArrayList();
/*       */           
/*  2717 */           sub.add(rs1.getString(2));
/*  2718 */           if (rs1.getString(1).equals("SYS")) {
/*  2719 */             sub.add(rs1.getString(2));
/*       */           }
/*  2721 */           else if (rs1.getString(2).equals("Site24x7")) {
/*  2722 */             sub.add("Site24x7 Monitors");
/*       */           }
/*  2724 */           else if (rs1.getString(2).equals("VirtualMachine"))
/*       */           {
/*  2726 */             sub.add("Virtual Machine");
/*       */           }
/*  2728 */           else if (rs1.getString(2).indexOf("OpStor") != -1)
/*       */           {
/*  2730 */             sub.add("Storage Devices");
/*       */           }
/*  2732 */           else if (rs1.getString(2).equals("Container"))
/*       */           {
/*  2734 */             sub.add(rs1.getString(2));
/*       */           }
/*       */           else {
/*  2737 */             sub.add(rs1.getString(3));
/*       */           }
/*       */           
/*  2740 */           sub.add(rs1.getString(4));
/*  2741 */           sub.add(rs1.getString(5));
/*  2742 */           sub.add(rs1.getString(6));
/*  2743 */           sub.add(rs1.getString(7));
/*  2744 */           sub.add(rs1.getString(8));
/*  2745 */           alllst.add(sub);
/*  2746 */           CategoryView.put(rs1.getString(1), alllst);
/*       */         }
/*       */       } }
/*  2749 */     AMConnectionPool.closeStatement(rs1);
/*       */     
/*       */ 
/*  2752 */     ArrayList rows = null;
/*  2753 */     for (int c = 0; c < Constants.categoryLink.length; c++)
/*       */     {
/*  2755 */       group = Constants.categoryLink[c];
/*  2756 */       if ((ClientDBUtil.isPrivilegedUser(request)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */       {
/*  2758 */         if (EnterpriseUtil.isIt360MSPEdition())
/*       */         {
/*  2760 */           query = getResourceTypeQueryForOwner(group, network, owner, request);
/*  2761 */           rows = this.mo.getRows(query);
/*       */         }
/*       */       }
/*       */       
/*  2765 */       if (CategoryView.containsKey(group)) {
/*  2766 */         rows = (ArrayList)CategoryView.get(group);
/*       */       } else {
/*  2768 */         rows = new ArrayList();
/*       */       }
/*       */       
/*  2771 */       if (group.equals("URL"))
/*       */       {
/*  2773 */         if ((network != null) && (!network.equals("null")))
/*       */         {
/*  2775 */           for (int i = 0; i < rows.size(); i++)
/*       */           {
/*  2777 */             ArrayList al = (ArrayList)rows.get(i);
/*  2778 */             for (int j = 0; j < al.size(); j++)
/*       */             {
/*  2780 */               ResultSet rs_url = null;
/*       */               try
/*       */               {
/*  2783 */                 if ((String.valueOf(al.get(j)).equals("Apache-server")) || (String.valueOf(al.get(j)).equals("IIS-server")) || (String.valueOf(al.get(j)).equals("PHP")))
/*       */                 {
/*  2785 */                   String filterCondn = "";
/*  2786 */                   if (EnterpriseUtil.isIt360MSPEdition())
/*       */                   {
/*  2788 */                     filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request, true));
/*       */                   }
/*  2790 */                   String url_query = "select count(AM_ManagedObject.type) from AM_ManagedObject,InetService,IpAddress where AM_ManagedObject.type='" + al.get(j) + "' and AM_ManagedObject.RESOURCENAME=InetService.NAME and InetService.INTERFACENAME=IpAddress.NAME and IpAddress.PARENTNET='" + network + "'" + filterCondn;
/*  2791 */                   AMConnectionPool.getInstance();rs_url = AMConnectionPool.executeQueryStmt(url_query);
/*  2792 */                   if (rs_url.next())
/*       */                   {
/*  2794 */                     al.remove(2);
/*  2795 */                     al.add(2, rs_url.getString(1));
/*       */                   }
/*  2797 */                   AMConnectionPool.closeStatement(rs_url);
/*       */                 }
/*       */               }
/*       */               catch (Exception exc)
/*       */               {
/*  2802 */                 exc.printStackTrace();
/*       */               }
/*       */               finally {
/*  2805 */                 AMConnectionPool.closeStatement(rs_url);
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*  2810 */         remove_categorytype(rows);
/*  2811 */         if (rows.size() > 0)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  2817 */           request.setAttribute("webservices", rows);
/*  2818 */           request.setAttribute("URL", rows);
/*       */         }
/*       */       }
/*       */       else {
/*  2822 */         if (request.getParameter("viewHiddenMonitors") == null)
/*       */         {
/*  2824 */           remove_categorytype(rows);
/*       */         }
/*  2826 */         if (rows.size() > 0)
/*       */         {
/*  2828 */           request.setAttribute(Constants.category[c], rows); }
/*       */       }
/*       */     }
/*  2831 */     if (Constants.getCategorytype().equals("CLOUD"))
/*       */     {
/*  2833 */       String cntQuery = "SELECT COUNT(*) FROM AM_ManagedResourceType,AM_ManagedObject WHERE AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE AND AM_ManagedResourceType.RESOURCETYPE IN " + Constants.getNotSupported();
/*  2834 */       ResultSet resSet = null;
/*       */       try
/*       */       {
/*  2837 */         AMConnectionPool.getInstance();resSet = AMConnectionPool.executeQueryStmt(cntQuery);
/*  2838 */         if (resSet.next())
/*       */         {
/*  2840 */           int count = resSet.getInt(1);
/*  2841 */           if (count > 0)
/*       */           {
/*  2843 */             request.setAttribute("ContainsAdditionalMonitors", Boolean.valueOf(true));
/*  2844 */             request.setAttribute("additionalMonitorCount", Integer.valueOf(count));
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  2850 */         e.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/*  2854 */         AMConnectionPool.closeStatement(resSet);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  2861 */     if (request.getParameter("includeleftmenu") != null)
/*       */     {
/*  2863 */       return null;
/*       */     }
/*  2865 */     if (request.getParameter("detailspage") != null)
/*       */     {
/*  2867 */       if (resourcetype != null)
/*       */       {
/*  2869 */         if ((Boolean.valueOf(direct).booleanValue()) && ((resourcetype.equalsIgnoreCase("APM-Insight-Instance")) || (resourcetype.equalsIgnoreCase("APM-Insight-Application"))))
/*       */         {
/*  2871 */           return new ActionForward("/apminsight/home.do?method=viewAPMInsightHome#A/TW=H", true);
/*       */         }
/*  2873 */         if (("VirtualMachine".equals(resourcetype)) || ("VMWare ESX/ESXi".equals(resourcetype)))
/*       */         {
/*  2875 */           listESXServerDetails(mapping, form, request, response);
/*       */         }
/*  2877 */         if ("Hyper-V-Server".equalsIgnoreCase(resourcetype))
/*       */         {
/*  2879 */           listHyperVServerDetails(mapping, form, request, response);
/*       */         }
/*  2881 */         if ("XenServerHost".equals(resourcetype))
/*       */         {
/*  2883 */           listXenServerDetails(mapping, form, request, response);
/*       */         }
/*  2885 */         if ("Container".equals(resourcetype))
/*       */         {
/*  2887 */           listDockerContainerDetails(mapping, form, request, response);
/*       */         }
/*       */         
/*       */ 
/*  2891 */         LinkedHashMap infraview = (LinkedHashMap)ConfMonitorConfiguration.getInstance().getInfrastructureView(resourcetype);
/*  2892 */         if ((infraview != null) && (infraview.size() > 0))
/*       */         {
/*  2894 */           listInfraViewDetails(request, infraview);
/*       */         }
/*       */       }
/*  2897 */       return new ActionForward("/jsp/networkdetails.jsp?network=" + resourcetype);
/*       */     }
/*       */     
/*       */ 
/*  2901 */     String filterCondn = "";
/*  2902 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*  2904 */       filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request, true));
/*       */     }
/*  2906 */     String que = "SELECT COUNT(*) FROM AM_ManagedObject WHERE TYPE IN('Script Monitor','Custom-Application','QENGINE','File/Directory')" + filterCondn;
/*  2907 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  2908 */     ResultSet rs = null;
/*  2909 */     String intcount = null;
/*       */     try {
/*  2911 */       rs = AMConnectionPool.executeQueryStmt(que);
/*  2912 */       if (rs.next()) {
/*  2913 */         intcount = rs.getString(1);
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  2918 */       e.printStackTrace();
/*       */     }
/*       */     
/*  2921 */     AMConnectionPool.closeStatement(rs);
/*  2922 */     request.setAttribute("isCloud", Boolean.valueOf(EnterpriseUtil.isCloudEdition()));
/*  2923 */     request.setAttribute("count", intcount);
/*  2924 */     String to_jsp = "/jsp/network_content.jsp";
/*  2925 */     if (Constants.getCategorytype().equals("LAMP"))
/*       */     {
/*  2927 */       to_jsp = "/jsp/network_contentlamp.jsp";
/*       */     }
/*  2929 */     else if (!Constants.getCategorytype().equals("WINDOWS"))
/*       */     {
/*       */ 
/*       */ 
/*  2933 */       if (Constants.getCategorytype().equals("J2EE"))
/*       */       {
/*  2935 */         to_jsp = "/jsp/network_contentj2ee.jsp";
/*       */       }
/*  2937 */       else if (Constants.getCategorytype().equals("DATABASE"))
/*       */       {
/*  2939 */         to_jsp = "/jsp/network_contentdb.jsp";
/*       */       }
/*       */     }
/*       */     try {
/*  2943 */       if (network != null)
/*       */       {
/*  2945 */         types = types_copy;
/*       */       }
/*       */     } catch (Exception er) {
/*  2948 */       er.printStackTrace();
/*       */     }
/*       */     finally {
/*  2951 */       if (rs1 != null) {
/*  2952 */         AMConnectionPool.closeStatement(rs1);
/*       */       }
/*       */     }
/*  2955 */     return new ActionForward(to_jsp);
/*       */   }
/*       */   
/*       */   private String changeNetworkTypes(String types)
/*       */   {
/*  2960 */     String temp1 = types.replaceFirst("'UrlMonitor','UrlSeq','Custom-Application',", "");
/*  2961 */     String return_types = temp1.replaceFirst("'Script Monitor',", "");
/*  2962 */     return return_types;
/*       */   }
/*       */   
/*       */   private void remove_categorytype(ArrayList rows)
/*       */   {
/*  2967 */     int[] temp = new int[rows.size()];
/*  2968 */     for (int i = 0; i < temp.length; i++)
/*       */     {
/*  2970 */       temp[i] = 0;
/*       */     }
/*  2972 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  2974 */       ArrayList row = (ArrayList)rows.get(i);
/*  2975 */       for (int j = 0; j < Constants.resourceTypes_array.length; j++)
/*       */       {
/*  2977 */         if (row.contains(Constants.resourceTypes_array[j])) {
/*       */           break;
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  2983 */         if (j == Constants.resourceTypes_array.length - 1)
/*       */         {
/*  2985 */           temp[i] = 1;
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  2991 */     int j = 0;
/*  2992 */     for (int i = 0; i < temp.length; i++)
/*       */     {
/*  2994 */       if (temp[i] == 1)
/*       */       {
/*  2996 */         rows.remove(j);
/*  2997 */         j--;
/*       */       }
/*  2999 */       j++;
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward showGMapView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3007 */     return new ActionForward("/adminAction.do?method=customMapView");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward showIconsView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3015 */     if (request.isUserInRole("OPERATOR"))
/*       */     {
/*  3017 */       return mapping.findForward("accessRestricted");
/*       */     }
/*  3019 */     String network = request.getParameter("selectedNetwork");
/*       */     
/*       */ 
/*  3022 */     int selectedPage = 1;
/*  3023 */     int startIndex = 0;
/*       */     
/*  3025 */     if (request.getParameter("selectedPage") != null)
/*       */     {
/*  3027 */       selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*       */     }
/*  3029 */     else if (request.getSession().getAttribute("selectedPage") != null)
/*       */     {
/*  3031 */       selectedPage = ((Integer)request.getSession().getAttribute("selectedPage")).intValue();
/*       */     }
/*       */     
/*  3034 */     int noOfRows = 25;
/*       */     
/*  3036 */     if (request.getParameter("noOfRows") != null)
/*       */     {
/*  3038 */       noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*       */     }
/*  3040 */     else if (request.getSession().getAttribute("noOfRows") != null)
/*       */     {
/*  3042 */       noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*       */     }
/*       */     
/*  3045 */     startIndex = (selectedPage - 1) * noOfRows;
/*       */     
/*       */ 
/*  3048 */     if (network == null)
/*       */     {
/*       */ 
/*  3051 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  3053 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(request, startIndex, noOfRows));
/*       */ 
/*       */ 
/*       */       }
/*  3057 */       else if (Constants.isIt360)
/*       */       {
/*  3059 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(startIndex, noOfRows));
/*       */       }
/*       */       else
/*       */       {
/*  3063 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(request));
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*  3069 */     else if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*  3071 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, request, startIndex, noOfRows));
/*       */ 
/*       */ 
/*       */     }
/*  3075 */     else if (Constants.isIt360)
/*       */     {
/*  3077 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, startIndex, noOfRows));
/*       */     }
/*       */     else
/*       */     {
/*  3081 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, request));
/*       */     }
/*       */     
/*       */ 
/*  3085 */     showResourceTypes(mapping, form, request, response);
/*  3086 */     request.setAttribute("reloadperiod", "60");
/*  3087 */     return new ActionForward("/jsp/NetworkView.jsp?network=" + network);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward showDetailsView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3095 */     String network = request.getParameter("selectedNetwork");
/*  3096 */     request.setAttribute("reloadperiod", "60");
/*  3097 */     if (request.isUserInRole("OPERATOR"))
/*       */     {
/*  3099 */       return mapping.findForward("accessRestricted");
/*       */     }
/*       */     
/*  3102 */     int startIndex = 0;
/*  3103 */     int selectedPage = 1;
/*       */     
/*  3105 */     if (request.getParameter("selectedPage") != null)
/*       */     {
/*  3107 */       selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*       */     }
/*  3109 */     else if (request.getSession().getAttribute("selectedPage") != null)
/*       */     {
/*  3111 */       selectedPage = ((Integer)request.getSession().getAttribute("selectedPage")).intValue();
/*       */     }
/*       */     
/*  3114 */     int noOfRows = 25;
/*       */     
/*  3116 */     if (request.getParameter("noOfRows") != null)
/*       */     {
/*  3118 */       noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*       */     }
/*  3120 */     else if (request.getSession().getAttribute("noOfRows") != null)
/*       */     {
/*  3122 */       noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*       */     }
/*       */     
/*  3125 */     startIndex = (selectedPage - 1) * noOfRows;
/*       */     
/*       */ 
/*  3128 */     if (network == null)
/*       */     {
/*  3130 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  3132 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(request, startIndex, noOfRows));
/*       */ 
/*       */ 
/*       */       }
/*  3136 */       else if (Constants.isIt360)
/*       */       {
/*  3138 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(startIndex, noOfRows));
/*       */       }
/*       */       else
/*       */       {
/*  3142 */         request.setAttribute("mapmodel", ViewsCreator.getAllNetworksViewModel(request));
/*       */ 
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*  3148 */     else if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*  3150 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, request, startIndex, noOfRows));
/*       */ 
/*       */ 
/*       */     }
/*  3154 */     else if (Constants.isIt360)
/*       */     {
/*  3156 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, startIndex, noOfRows));
/*       */     }
/*       */     else
/*       */     {
/*  3160 */       request.setAttribute("mapmodel", ViewsCreator.getNetworkViewModel(network, request));
/*       */     }
/*       */     
/*       */ 
/*  3164 */     showResourceTypes(mapping, form, request, response);
/*  3165 */     return new ActionForward("/jsp/NetworkDetailsView.jsp?network=" + network + "");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showMonitorGroupView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3174 */     if (Constants.isIt360)
/*       */     {
/*  3176 */       return showMonitorGroupViewForIT360(mapping, form, request, response);
/*       */     }
/*       */     
/*  3179 */     if ((request.isUserInRole("OPERATOR")) && (!Constants.isIt360))
/*       */     {
/*  3181 */       return mapping.findForward("accessRestricted");
/*       */     }
/*  3183 */     String retaintree = "";
/*       */     try
/*       */     {
/*  3186 */       String customValue = request.getParameter("customValue");
/*  3187 */       String viewtype = request.getParameter("viewtype");
/*  3188 */       if (viewtype == null)
/*       */       {
/*  3190 */         viewtype = "";
/*       */       }
/*  3192 */       request.setAttribute("viewtype", viewtype);
/*       */       
/*       */ 
/*  3195 */       boolean isPrivilegedUser = false;
/*  3196 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/*  3197 */         isPrivilegedUser = true;
/*       */       }
/*  3199 */       String reqType = request.getParameter("type");
/*  3200 */       if (reqType == null)
/*       */       {
/*  3202 */         reqType = "";
/*       */       }
/*  3204 */       ActionMessages messages = new ActionMessages();
/*  3205 */       if (("true".equals(request.getParameter("uncategorize"))) || ("orphaned".equals(request.getParameter("retaintree")))) {
/*  3206 */         showUncategorizedMonitors(request);
/*  3207 */         if (request.getParameter("fromwhere") != null)
/*       */         {
/*  3209 */           setActionMessages(request, messages);
/*       */         }
/*  3211 */         saveMessages(request, messages);
/*  3212 */         request.setAttribute("defaultview", "showMonitorGroupView");
/*  3213 */         return new ActionForward("/jsp/MonitorGroupView.jsp");
/*       */       }
/*  3215 */       String monGpType = request.getParameter("mgType");
/*  3216 */       String monGpTypeBasedQry = "";
/*  3217 */       String monGpTypeBasedQry1 = "";
/*  3218 */       if (monGpType != null)
/*       */       {
/*  3220 */         if (monGpType.equals("3"))
/*       */         {
/*  3222 */           monGpTypeBasedQry = monGpTypeBasedQry + " AM_HOLISTICAPPLICATION.GROUPTYPE in (3, 1009, 1010) ";
/*       */         }
/*  3224 */         else if (monGpType.equals("2"))
/*       */         {
/*  3226 */           monGpTypeBasedQry = monGpTypeBasedQry + " AM_HOLISTICAPPLICATION.GROUPTYPE = " + monGpType + " ";
/*       */         }
/*       */       }
/*       */       
/*  3230 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  3231 */       request.setAttribute("reloadperiod", "120");
/*  3232 */       String userName = request.getRemoteUser();
/*       */       
/*  3234 */       ArrayList MonitorsinMGs = new ArrayList();
/*  3235 */       ArrayList list = new ArrayList(5);
/*  3236 */       String modifiedSys = Constants.serverTypes;
/*  3237 */       modifiedSys = modifiedSys.substring(0, modifiedSys.length() - 1) + ", '" + Constants.virtualServers + "'";
/*  3238 */       String modifiedSys1 = modifiedSys + ",'NODE')";
/*  3239 */       modifiedSys = modifiedSys + ",'NODE','HAI')";
/*       */       
/*  3241 */       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*  3242 */       String resTypes = Constants.resourceTypes;
/*       */       
/*  3244 */       if (resTypes.contains("OpManager"))
/*       */       {
/*  3246 */         resTypes = resTypes.replaceAll("OpManager", "");
/*       */       }
/*  3248 */       String query = "";
/*       */       
/*       */       ExtDeviceActions extdevices;
/*  3251 */       if (Constants.isIt360)
/*       */       {
/*  3253 */         extdevices = new ExtDeviceActions();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3258 */       ArrayList haids = new ArrayList();
/*       */       
/*  3260 */       ArrayList filteredHaids = new ArrayList();
/*  3261 */       ResultSet set = null;
/*       */       try
/*       */       {
/*  3264 */         if ((isPrivilegedUser) && (!EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/*       */ 
/*  3267 */           if ((monGpTypeBasedQry != null) && (monGpTypeBasedQry.trim().length() > 0)) {
/*  3268 */             monGpTypeBasedQry1 = " and " + monGpTypeBasedQry;
/*       */           }
/*  3270 */           if (Constants.isSsoEnabled()) {
/*  3271 */             String loginUserid = Constants.getLoginUserid(request);
/*  3272 */             query = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_USERRESOURCESTABLE where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID " + monGpTypeBasedQry1 + " and AM_HOLISTICAPPLICATION.HAID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " order by RESOURCENAME";
/*       */           } else {
/*  3274 */             query = "select RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID " + monGpTypeBasedQry1 + " and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + request.getRemoteUser() + "' order by RESOURCENAME";
/*       */           }
/*       */           
/*       */         }
/*       */         else
/*       */         {
/*  3280 */           String bsgFilterCondn = "";
/*  3281 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  3283 */             Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  3284 */             bsgFilterCondn = " WHERE " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */           }
/*  3286 */           if ((bsgFilterCondn != null) && (bsgFilterCondn.trim().length() > 0)) {
/*  3287 */             if ((monGpTypeBasedQry != null) && (monGpTypeBasedQry.trim().length() > 0)) {
/*  3288 */               monGpTypeBasedQry1 = " and " + monGpTypeBasedQry;
/*       */             }
/*       */             
/*       */ 
/*       */           }
/*  3293 */           else if ((monGpTypeBasedQry != null) && (monGpTypeBasedQry.trim().length() > 0)) {
/*  3294 */             monGpTypeBasedQry1 = " where " + monGpTypeBasedQry;
/*       */           }
/*       */           
/*       */ 
/*  3298 */           query = "SELECT HAID FROM AM_HOLISTICAPPLICATION" + bsgFilterCondn + monGpTypeBasedQry1;
/*       */         }
/*  3300 */         set = AMConnectionPool.executeQueryStmt(query);
/*  3301 */         while (set.next())
/*       */         {
/*  3303 */           if ((isPrivilegedUser) && (!EnterpriseUtil.isIt360MSPEdition()))
/*       */           {
/*  3305 */             String haid = set.getString(1);
/*       */             
/*  3307 */             ReportUtil.getAllMGs(haids, haid);
/*  3308 */             if (!haids.contains(haid))
/*       */             {
/*  3310 */               haids.add(haid);
/*       */             }
/*  3312 */             if (!filteredHaids.contains(haid))
/*       */             {
/*  3314 */               filteredHaids.add(haid);
/*       */             }
/*  3316 */             ArrayList tempList = new ArrayList();
/*  3317 */             ReportUtil.getLastLevelSubGroup(tempList, haid);
/*  3318 */             haids.addAll(tempList);
/*  3319 */             filteredHaids.addAll(tempList);
/*       */           }
/*       */           else
/*       */           {
/*  3323 */             haids.add(set.getString(1));
/*  3324 */             filteredHaids.add(set.getString(1));
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*  3335 */           if (set != null) {
/*  3336 */             AMConnectionPool.closeStatement(set);
/*       */           }
/*       */         }
/*       */         catch (Exception e) {}
/*       */         
/*       */ 
/*       */ 
/*  3343 */         dataTable = "";
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  3330 */         e.printStackTrace();
/*  3331 */         AMLog.debug("Error Message is ---->" + e.getMessage());
/*       */       }
/*       */       finally {
/*       */         try {
/*  3335 */           if (set != null) {
/*  3336 */             AMConnectionPool.closeStatement(set);
/*       */           }
/*       */         }
/*       */         catch (Exception e) {}
/*       */       }
/*       */       
/*       */       String dataTable;
/*       */       
/*  3344 */       String qryCon1 = "";
/*  3345 */       String qryCon2 = "";
/*  3346 */       String s12 = "";
/*  3347 */       String s14 = "";
/*  3348 */       String customField = "";
/*  3349 */       String value = "";
/*  3350 */       if (customValue != null) {
/*       */         try
/*       */         {
/*  3353 */           if (customValue.contains("$")) {
/*  3354 */             customField = customValue.substring(0, customValue.indexOf("$"));
/*  3355 */             value = customValue.substring(customValue.indexOf("$") + 1);
/*       */           }
/*  3357 */           if (customField.indexOf("SYSTEMDATA") != -1)
/*       */           {
/*  3359 */             dataTable = "AM_MYFIELDS_SYSTEMDATA,";
/*  3360 */             qryCon1 = " inner join AM_MYFIELDS_SYSTEMDATA on AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*  3361 */             qryCon2 = " and AM_MYFIELDS_SYSTEMDATA." + customField + "='" + value + "'";
/*       */           }
/*  3363 */           else if (customField.indexOf("USERDATA") != -1)
/*       */           {
/*  3365 */             dataTable = "AM_MYFIELDS_USERDATA,";
/*  3366 */             qryCon1 = "inner join AM_MYFIELDS_USERDATA on AM_MYFIELDS_USERDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*  3367 */             qryCon2 = " and AM_MYFIELDS_USERDATA." + customField + "='" + value + "'";
/*       */           }
/*  3369 */           else if (customField.equals("LOCATION_NAME"))
/*       */           {
/*  3371 */             dataTable = "AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,";
/*  3372 */             qryCon1 = " inner join AM_MYFIELDS_ENTITYDATA on  AM_MYFIELDS_ENTITYDATA.RESOURCEID= AM_HOLISTICAPPLICATION.HAID";
/*  3373 */             qryCon2 = " and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and VALUEID=LOCATIONID and LOCATIONID=" + value;
/*       */           }
/*  3375 */           else if (customField.equals("USERNAME"))
/*       */           {
/*  3377 */             dataTable = "AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,";
/*       */             
/*  3379 */             qryCon1 = " inner join AM_MYFIELDS_ENTITYDATA on  AM_MYFIELDS_ENTITYDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID inner join AM_UserPasswordTable on AM_UserPasswordTable.USERID=AM_MYFIELDS_ENTITYDATA.VALUEID ";
/*  3380 */             qryCon2 = "  and DATATABLE='AM_UserPasswordTable' and USERID=" + value;
/*       */           }
/*  3382 */           else if (customField.equals("VALUEID"))
/*       */           {
/*  3384 */             dataTable = "AM_MYFIELDS_LABELDATA,";
/*  3385 */             qryCon1 = " inner join AM_MYFIELDS_LABELDATA on AM_MYFIELDS_LABELDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID ";
/*  3386 */             qryCon2 = " and VALUEID=" + value;
/*       */           }
/*       */         }
/*       */         catch (Exception exception2)
/*       */         {
/*  3391 */           exception2.printStackTrace();
/*       */         }
/*       */       }
/*       */       
/*  3395 */       String siteCondition = " and AM_PARENTCHILDMAPPER.PARENTID IN (";
/*  3396 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*       */ 
/*  3399 */         Properties siteProp = EnterpriseUtil.getSiteProp(request);
/*  3400 */         if (siteProp != null)
/*       */         {
/*  3402 */           String siteName = siteProp.keys().nextElement().toString();
/*  3403 */           String siteId = siteProp.getProperty(siteName);
/*  3404 */           siteCondition = siteCondition + siteId + ")";
/*       */         }
/*       */         else
/*       */         {
/*  3408 */           Properties custProp = EnterpriseUtil.getCustProp(request);
/*  3409 */           if ((custProp != null) && (custProp.size() > 0))
/*       */           {
/*  3411 */             String custName = custProp.keys().nextElement().toString();
/*  3412 */             String custId = custProp.getProperty(custName);
/*  3413 */             Vector<String> assoSiteVect = EnterpriseUtil.getAllAssociatedSites("" + custId, EnterpriseUtil.getLoggedInUserName(request));
/*  3414 */             if ((assoSiteVect != null) && (assoSiteVect.size() > 0))
/*       */             {
/*  3416 */               String csv = assoSiteVect.toString();
/*  3417 */               csv = csv.substring(1, csv.length() - 1);
/*  3418 */               siteCondition = siteCondition + csv + ")";
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  3424 */       if ((monGpTypeBasedQry != null) && (monGpTypeBasedQry.trim().length() > 0)) {
/*  3425 */         monGpTypeBasedQry1 = " and " + monGpTypeBasedQry;
/*       */       }
/*       */       
/*  3428 */       String userCondition = "";
/*  3429 */       String topLevelGroupCondition = " and AM_HOLISTICAPPLICATION.TYPE=0 ";
/*  3430 */       if (isPrivilegedUser) {
/*  3431 */         userCondition = " and " + ManagedApplication.getCondition("AM_ManagedObject.RESOURCEID", filteredHaids) + " ";
/*  3432 */         topLevelGroupCondition = "";
/*       */       }
/*  3434 */       if ("3".equals(monGpType))
/*       */       {
/*  3436 */         topLevelGroupCondition = " and AM_HOLISTICAPPLICATION.TYPE in (0,1) ";
/*       */       }
/*  3438 */       String listquery = "select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_ManagedObject.TYPE,AM_ManagedObject.DISPLAYNAME  ,AM_ManagedObject.TYPE,AM_ManagedObject.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_HOLISTICAPPLICATION.TYPE,AM_ManagedObject.DCSTARTED,AM_HOLISTICAPPLICATION.GROUPTYPE,AM_PARENTCHILDMAPPER.PARENTID from  AM_HOLISTICAPPLICATION inner join AM_ManagedObject on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID " + qryCon1 + " left join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left join AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_HOLISTICAPPLICATION.HAID where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID " + monGpTypeBasedQry1 + userCondition + topLevelGroupCondition + qryCon2 + " order by AM_ManagedObject.DISPLAYNAME";
/*  3439 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  3441 */         String customerId = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getCustomerId(request);
/*  3442 */         listquery = "select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_ManagedObject.TYPE,AM_ManagedObject.DISPLAYNAME  ,AM_ManagedObject.TYPE,AM_ManagedObject.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_HOLISTICAPPLICATION.TYPE,AM_ManagedObject.DCSTARTED,AM_HOLISTICAPPLICATION.GROUPTYPE,AM_PARENTCHILDMAPPER.PARENTID from  AM_HOLISTICAPPLICATION inner join AM_ManagedObject on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left join AM_PARENTCHILDMAPPER ON AM_PARENTCHILDMAPPER.CHILDID=AM_HOLISTICAPPLICATION.HAID join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION_EXT.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE='BSG0' join CUSTOMERINFO on CUSTOMERINFO.CUSTOMERID=AM_PARENTCHILDMAPPER.PARENTID and CUSTOMERINFO.CUSTOMERID=" + customerId + " where AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  and AM_HOLISTICAPPLICATION.TYPE=1 order by AM_ManagedObject.DISPLAYNAME";
/*       */       }
/*  3444 */       list = this.mo.getRows(listquery);
/*  3445 */       ArrayList listofGroups = new ArrayList();
/*  3446 */       Vector childVec = new Vector();
/*  3447 */       for (int j = 0; j < list.size(); j++)
/*       */       {
/*  3449 */         ArrayList singlerow = (ArrayList)list.get(j);
/*  3450 */         if ((isPrivilegedUser) && ("1".equals((String)singlerow.get(15)))) {
/*  3451 */           String parentId = (String)singlerow.get(18);
/*  3452 */           if (filteredHaids.contains(parentId)) {}
/*       */         }
/*       */         else
/*       */         {
/*  3456 */           ArrayList singlemonitorgroup = new ArrayList();
/*  3457 */           singlemonitorgroup.add((String)singlerow.get(0));
/*  3458 */           singlemonitorgroup.add((String)singlerow.get(1));
/*       */           
/*  3460 */           if (("3".equals(monGpType)) && (singlerow.get(17) != null) && ("3".equals((String)singlerow.get(17))))
/*       */           {
/*  3462 */             singlemonitorgroup.add("0");
/*       */           }
/*       */           else
/*       */           {
/*  3466 */             singlemonitorgroup.add((String)singlerow.get(15));
/*       */           }
/*  3468 */           singlemonitorgroup.add((String)singlerow.get(3));
/*  3469 */           singlemonitorgroup.add((String)singlerow.get(4));
/*  3470 */           singlemonitorgroup.add((String)singlerow.get(5));
/*  3471 */           singlemonitorgroup.add((String)singlerow.get(6));
/*  3472 */           singlemonitorgroup.add("HAI");
/*  3473 */           singlemonitorgroup.add((String)singlerow.get(7));
/*  3474 */           singlemonitorgroup.add((String)singlerow.get(11));
/*  3475 */           if (filteredHaids.contains((String)singlerow.get(6)))
/*       */           {
/*  3477 */             singlemonitorgroup.add("enable");
/*       */           }
/*       */           else
/*       */           {
/*  3481 */             singlemonitorgroup.add("disable");
/*       */           }
/*  3483 */           singlemonitorgroup.add((String)singlerow.get(17));
/*  3484 */           listofGroups.add(singlemonitorgroup);
/*       */         }
/*       */       }
/*  3487 */       if (request.getParameter("fromwhere") != null)
/*       */       {
/*  3489 */         setActionMessages(request, messages);
/*       */       }
/*  3491 */       if (request.getParameter("retaintree") != null)
/*       */       {
/*  3493 */         retaintree = request.getParameter("retaintree");
/*       */       }
/*  3495 */       saveMessages(request, messages);
/*  3496 */       request.setAttribute("applications", listofGroups);
/*  3497 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*  3498 */       request.setAttribute("retaintree", retaintree);
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  3502 */       ex.printStackTrace();
/*       */     }
/*  3504 */     return new ActionForward("/jsp/MonitorGroupView.jsp");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward showMonitorGroupViewForIT360(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  3512 */     if ((request.isUserInRole("OPERATOR")) && (!Constants.isIt360))
/*       */     {
/*  3514 */       return mapping.findForward("accessRestricted");
/*       */     }
/*  3516 */     String retaintree = "";
/*       */     
/*       */     try
/*       */     {
/*  3520 */       String customValue = request.getParameter("customValue");
/*  3521 */       String viewtype = request.getParameter("viewtype");
/*  3522 */       if (viewtype == null)
/*       */       {
/*  3524 */         viewtype = "";
/*       */       }
/*  3526 */       request.setAttribute("viewtype", viewtype);
/*       */       
/*       */ 
/*       */ 
/*  3530 */       String reqType = request.getParameter("type");
/*  3531 */       if (reqType == null)
/*       */       {
/*  3533 */         reqType = "";
/*       */       }
/*  3535 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */       
/*  3537 */       String monGpType = request.getParameter("mgType");
/*  3538 */       String monGpTypeBasedQry = "SELECT HAID FROM AM_HOLISTICAPPLICATION";
/*  3539 */       ArrayList typeBasedMGList = null;
/*  3540 */       ArrayList listOfGroupsBasedOnType = null;
/*  3541 */       if (monGpType != null)
/*       */       {
/*  3543 */         listOfGroupsBasedOnType = new ArrayList();
/*       */         
/*  3545 */         if (monGpType.equals("3"))
/*       */         {
/*  3547 */           monGpTypeBasedQry = monGpTypeBasedQry + " WHERE GROUPTYPE in (3, 1009, 1010)";
/*       */         }
/*  3549 */         else if (monGpType.equals("2"))
/*       */         {
/*  3551 */           monGpTypeBasedQry = monGpTypeBasedQry + " WHERE GROUPTYPE = " + monGpType;
/*       */         }
/*       */       }
/*       */       
/*  3555 */       typeBasedMGList = this.mo.getRowsForSingleColumn(monGpTypeBasedQry);
/*       */       
/*  3557 */       request.setAttribute("reloadperiod", "120");
/*  3558 */       String userName = request.getRemoteUser();
/*  3559 */       ActionMessages messages = new ActionMessages();
/*  3560 */       ArrayList MonitorsinMGs = new ArrayList();
/*  3561 */       ArrayList list = new ArrayList(5);
/*  3562 */       String modifiedSys = Constants.serverTypes;
/*  3563 */       modifiedSys = modifiedSys.substring(0, modifiedSys.length() - 1) + ", '" + Constants.virtualServers + "'";
/*  3564 */       String modifiedSys1 = modifiedSys + ",'NODE')";
/*  3565 */       modifiedSys = modifiedSys + ",'NODE','HAI')";
/*       */       
/*  3567 */       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*       */       
/*  3569 */       String resTypes = Constants.resourceTypes;
/*       */       
/*  3571 */       if (resTypes.contains("OpManager"))
/*       */       {
/*  3573 */         resTypes = resTypes.replaceAll("OpManager", "");
/*       */       }
/*  3575 */       String query = "";
/*       */       
/*       */       ExtDeviceActions extdevices;
/*  3578 */       if ((System.getProperty("isConsole") != null) && (System.getProperty("isConsole").equalsIgnoreCase("true")))
/*       */       {
/*  3580 */         extdevices = new ExtDeviceActions();
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  3585 */       ArrayList haids = new ArrayList();
/*       */       
/*  3587 */       ArrayList filteredHaids = new ArrayList();
/*  3588 */       ResultSet set = null;
/*       */       try
/*       */       {
/*  3591 */         if ((request.isUserInRole("OPERATOR")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/*  3593 */           query = "select RESOURCEID from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + request.getRemoteUser() + "' order by RESOURCENAME";
/*       */         }
/*       */         else
/*       */         {
/*  3597 */           String bsgFilterCondn = "";
/*  3598 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  3600 */             Vector haidVector = EnterpriseUtil.filterCustSpecificHAIds(request);
/*  3601 */             bsgFilterCondn = " WHERE " + EnterpriseUtil.getCondition("AM_HOLISTICAPPLICATION.HAID", haidVector);
/*       */           }
/*  3603 */           query = "SELECT HAID FROM AM_HOLISTICAPPLICATION" + bsgFilterCondn;
/*       */         }
/*       */         
/*  3606 */         set = AMConnectionPool.executeQueryStmt(query);
/*  3607 */         while (set.next())
/*       */         {
/*  3609 */           if ((request.isUserInRole("OPERATOR")) && (!EnterpriseUtil.isIt360MSPEdition()))
/*       */           {
/*  3611 */             String haid = set.getString(1);
/*       */             
/*  3613 */             ReportUtil.getAllMGs(haids, haid);
/*  3614 */             if (!haids.contains(haid))
/*       */             {
/*  3616 */               haids.add(haid);
/*       */             }
/*  3618 */             if (!filteredHaids.contains(haid))
/*       */             {
/*  3620 */               filteredHaids.add(haid);
/*       */             }
/*  3622 */             ArrayList tempList = new ArrayList();
/*  3623 */             ReportUtil.getLastLevelSubGroup(tempList, haid);
/*  3624 */             haids.addAll(tempList);
/*  3625 */             filteredHaids.addAll(tempList);
/*       */           }
/*       */           else
/*       */           {
/*  3629 */             String haid = set.getString(1);
/*  3630 */             haids.add(haid);
/*  3631 */             filteredHaids.add(haid);
/*       */             
/*  3633 */             if (EnterpriseUtil.isIt360MSPEdition())
/*       */             {
/*  3635 */               ArrayList tempList = new ArrayList();
/*  3636 */               ReportUtil.getLastLevelSubGroup(tempList, haid);
/*  3637 */               haids.addAll(tempList);
/*  3638 */               filteredHaids.addAll(tempList);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  3645 */         e.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/*  3649 */         if (set != null) {
/*  3650 */           AMConnectionPool.closeStatement(set);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  3657 */       String dataTable = "";
/*  3658 */       String qryCon = "";
/*  3659 */       String s12 = "";
/*  3660 */       String s14 = "";
/*  3661 */       String customField = "";
/*  3662 */       String value = "";
/*  3663 */       if (customValue != null) {
/*       */         try
/*       */         {
/*  3666 */           if (customValue.contains("$")) {
/*  3667 */             customField = customValue.substring(0, customValue.indexOf("$"));
/*  3668 */             value = customValue.substring(customValue.indexOf("$") + 1);
/*       */           }
/*  3670 */           if (customField.indexOf("SYSTEMDATA") != -1)
/*       */           {
/*  3672 */             dataTable = "AM_MYFIELDS_SYSTEMDATA,";
/*  3673 */             qryCon = " and AM_MYFIELDS_SYSTEMDATA." + customField + "='" + value + "' and AM_MYFIELDS_SYSTEMDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*       */           }
/*  3675 */           else if (customField.indexOf("USERDATA") != -1)
/*       */           {
/*  3677 */             dataTable = "AM_MYFIELDS_USERDATA,";
/*  3678 */             qryCon = " and AM_MYFIELDS_USERDATA." + customField + "='" + value + "' and AM_MYFIELDS_USERDATA.RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*       */           }
/*  3680 */           else if (customField.equals("LOCATION_NAME"))
/*       */           {
/*  3682 */             dataTable = "AM_MYFIELDS_LOCATION,AM_MYFIELDS_ENTITYDATA,";
/*  3683 */             qryCon = " and AM_HOLISTICAPPLICATION.HAID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and AM_MYFIELDS_ENTITYDATA.DATATABLE='AM_MYFIELDS_LOCATION' and VALUEID=LOCATIONID and LOCATIONID=" + value;
/*  3684 */           } else if (customField.equals("USERNAME"))
/*       */           {
/*  3686 */             dataTable = "AM_UserPasswordTable,AM_MYFIELDS_ENTITYDATA,";
/*  3687 */             qryCon = " and AM_HOLISTICAPPLICATION.HAID=AM_MYFIELDS_ENTITYDATA.RESOURCEID and DATATABLE='AM_UserPasswordTable' and AM_MYFIELDS_ENTITYDATA.VALUEID=AM_UserPasswordTable.USERID and USERID=" + value;
/*  3688 */           } else if (customField.equals("VALUEID"))
/*       */           {
/*  3690 */             dataTable = "AM_MYFIELDS_LABELDATA,";
/*  3691 */             qryCon = " and AM_HOLISTICAPPLICATION.HAID=AM_MYFIELDS_LABELDATA.RESOURCEID and VALUEID=" + value;
/*       */           }
/*       */         }
/*       */         catch (Exception exception2)
/*       */         {
/*  3696 */           exception2.printStackTrace();
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  3703 */       String siteCondition = " and AM_PARENTCHILDMAPPER.PARENTID IN (";
/*  3704 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*       */ 
/*  3707 */         Properties siteProp = EnterpriseUtil.getSiteProp(request);
/*  3708 */         if (siteProp != null)
/*       */         {
/*  3710 */           String siteName = siteProp.keys().nextElement().toString();
/*  3711 */           String siteId = siteProp.getProperty(siteName);
/*  3712 */           siteCondition = siteCondition + siteId + ")";
/*       */         }
/*       */         else
/*       */         {
/*  3716 */           Properties custProp = EnterpriseUtil.getCustProp(request);
/*  3717 */           if ((custProp != null) && (custProp.size() > 0))
/*       */           {
/*  3719 */             String custName = custProp.keys().nextElement().toString();
/*  3720 */             String custId = custProp.getProperty(custName);
/*  3721 */             Vector<String> assoSiteVect = EnterpriseUtil.getAllAssociatedSites("" + custId, EnterpriseUtil.getLoggedInUserName(request));
/*  3722 */             if ((assoSiteVect != null) && (assoSiteVect.size() > 0))
/*       */             {
/*  3724 */               String csv = assoSiteVect.toString();
/*  3725 */               csv = csv.substring(1, csv.length() - 1);
/*  3726 */               siteCondition = siteCondition + csv + ")";
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  3733 */       if (reqType.equalsIgnoreCase("SYS"))
/*       */       {
/*  3735 */         list = this.mo.getRows("select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,HAI2.GROUPTYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_HOLISTICAPPLICATION as HAI2 on child.RESOURCEID=HAI2.HAID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and child.TYPE in " + modifiedSys + " order by A1.DISPLAYNAME,child.DISPLAYNAME");
/*       */       }
/*  3737 */       else if (reqType.equalsIgnoreCase("APPLICATION"))
/*       */       {
/*  3739 */         list = this.mo.getRows("select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,HAI2.GROUPTYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_HOLISTICAPPLICATION as HAI2 on child.RESOURCEID=HAI2.HAID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and child.TYPE not in " + modifiedSys1 + " and child.TYPE NOT LIKE '%OpManager%' and child.TYPE NOT LIKE '%OpStor%' order by A1.DISPLAYNAME,child.DISPLAYNAME");
/*       */       }
/*  3741 */       else if (reqType.equalsIgnoreCase("NWD"))
/*       */       {
/*  3743 */         list = this.mo.getRows("select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,HAI2.GROUPTYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_HOLISTICAPPLICATION as HAI2 on child.RESOURCEID=HAI2.HAID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and child.TYPE not in " + resTypes + " and child.TYPE NOT LIKE '%OpStor%' order by A1.DISPLAYNAME,child.DISPLAYNAME");
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  3748 */         list = this.mo.getRows("select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,HAI2.GROUPTYPE from " + dataTable + " AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_HOLISTICAPPLICATION as HAI2 on child.RESOURCEID=HAI2.HAID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID " + qryCon + " order by A1.DISPLAYNAME,child.DISPLAYNAME");
/*       */       }
/*  3750 */       ArrayList listofGroups = new ArrayList();
/*  3751 */       Hashtable childMOs = new Hashtable();
/*  3752 */       Hashtable childMOsforMG = new Hashtable();
/*       */       
/*  3754 */       for (int j = 0; j < list.size(); j++)
/*       */       {
/*  3756 */         ArrayList singlerow = (ArrayList)list.get(j);
/*  3757 */         String resourcename = (String)singlerow.get(0);
/*  3758 */         String displayname = (String)singlerow.get(1);
/*  3759 */         String childactionstatus = (String)singlerow.get(2);
/*  3760 */         String owner = (String)singlerow.get(3);
/*  3761 */         String CREATIONDATE = (String)singlerow.get(4);
/*  3762 */         String MODIFIEDDATE = (String)singlerow.get(5);
/*  3763 */         String MGresourceid = (String)singlerow.get(6);
/*  3764 */         String unmanagednodes = (String)singlerow.get(7);
/*  3765 */         String childid = (String)singlerow.get(8);
/*  3766 */         String childname = (String)singlerow.get(9);
/*  3767 */         String childtype = (String)singlerow.get(10);
/*  3768 */         String MGactionstatus = (String)singlerow.get(11);
/*  3769 */         String imagepath = (String)singlerow.get(12);
/*  3770 */         String shortname = (String)singlerow.get(13);
/*  3771 */         String unmanageChildmos = (String)singlerow.get(14);
/*  3772 */         String MGType = (String)singlerow.get(15);
/*  3773 */         String dcstarted = (String)singlerow.get(16);
/*  3774 */         String haiGroupType = (String)singlerow.get(17);
/*       */         
/*  3776 */         if (haids.contains(MGresourceid))
/*       */         {
/*       */ 
/*       */ 
/*  3780 */           MonitorsinMGs.add(childid);
/*       */           
/*  3782 */           if ((childMOs.containsKey(MGresourceid)) || (childMOsforMG.containsKey(MGresourceid)))
/*       */           {
/*  3784 */             ArrayList childmo = null;
/*  3785 */             if (childtype != null)
/*       */             {
/*       */ 
/*       */ 
/*       */ 
/*  3790 */               if (childtype.equals("HAI"))
/*       */               {
/*  3792 */                 if (!haids.contains(childid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*  3796 */                 if (childMOsforMG.get(MGresourceid) != null)
/*       */                 {
/*  3798 */                   childmo = (ArrayList)childMOsforMG.get(MGresourceid);
/*       */                 }
/*       */                 else
/*       */                 {
/*  3802 */                   childmo = new ArrayList();
/*  3803 */                   childMOsforMG.put(MGresourceid, childmo);
/*       */                 }
/*       */               }
/*       */               else
/*       */               {
/*  3808 */                 if (!filteredHaids.contains(MGresourceid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*  3812 */                 if (childMOs.get(MGresourceid) != null)
/*       */                 {
/*  3814 */                   childmo = (ArrayList)childMOs.get(MGresourceid);
/*       */                 }
/*       */                 else
/*       */                 {
/*  3818 */                   childmo = new ArrayList();
/*  3819 */                   childMOs.put(MGresourceid, childmo);
/*       */                 }
/*       */               }
/*  3822 */               ArrayList singrow = new ArrayList();
/*  3823 */               if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null))
/*       */               {
/*  3825 */                 singrow.add(childid);
/*  3826 */                 singrow.add(childname);
/*  3827 */                 singrow.add(childtype);
/*  3828 */                 singrow.add(imagepath);
/*  3829 */                 singrow.add(shortname);
/*  3830 */                 singrow.add(unmanageChildmos);
/*  3831 */                 singrow.add(childactionstatus);
/*  3832 */                 if (!filteredHaids.contains(childid))
/*       */                 {
/*  3834 */                   singrow.add("disable");
/*       */                 }
/*       */                 else
/*       */                 {
/*  3838 */                   singrow.add("enable");
/*       */                 }
/*       */                 
/*       */ 
/*       */ 
/*  3843 */                 singrow.add(dcstarted);
/*  3844 */                 singrow.add(haiGroupType);
/*  3845 */                 childmo.add(singrow);
/*       */               }
/*       */             }
/*       */           }
/*       */           else {
/*  3850 */             ArrayList childmo1 = new ArrayList();
/*  3851 */             ArrayList singrow = new ArrayList();
/*       */             
/*  3853 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*       */             {
/*  3855 */               singrow.add(childid);
/*  3856 */               singrow.add(childname);
/*  3857 */               singrow.add(childtype);
/*  3858 */               singrow.add(imagepath);
/*  3859 */               singrow.add(shortname);
/*  3860 */               singrow.add(unmanageChildmos);
/*  3861 */               singrow.add(childactionstatus);
/*  3862 */               if (!filteredHaids.contains(childid))
/*       */               {
/*  3864 */                 singrow.add("disable");
/*       */               }
/*       */               else
/*       */               {
/*  3868 */                 singrow.add("enable");
/*       */               }
/*       */               
/*  3871 */               singrow.add(dcstarted);
/*  3872 */               singrow.add(haiGroupType);
/*  3873 */               childmo1.add(singrow);
/*       */               
/*  3875 */               if (childtype.equals("HAI"))
/*       */               {
/*  3877 */                 if (!haids.contains(childid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*       */ 
/*  3882 */                 childMOsforMG.put(MGresourceid, childmo1);
/*       */               }
/*       */               else
/*       */               {
/*  3886 */                 if (!filteredHaids.contains(MGresourceid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*       */ 
/*  3891 */                 childMOs.put(MGresourceid, childmo1);
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*       */             else
/*       */             {
/*  3898 */               ArrayList dummylist = new ArrayList();
/*  3899 */               childMOs.put(MGresourceid, dummylist);
/*       */             }
/*       */             
/*  3902 */             ArrayList singlemonitorgroup = new ArrayList();
/*  3903 */             singlemonitorgroup.add(resourcename);
/*  3904 */             singlemonitorgroup.add(displayname);
/*  3905 */             singlemonitorgroup.add(MGType);
/*  3906 */             singlemonitorgroup.add(owner);
/*  3907 */             singlemonitorgroup.add(CREATIONDATE);
/*  3908 */             singlemonitorgroup.add(MODIFIEDDATE);
/*  3909 */             singlemonitorgroup.add(MGresourceid);
/*  3910 */             singlemonitorgroup.add("HAI");
/*  3911 */             singlemonitorgroup.add(unmanagednodes);
/*  3912 */             singlemonitorgroup.add(MGactionstatus);
/*  3913 */             if (filteredHaids.contains(MGresourceid))
/*       */             {
/*  3915 */               singlemonitorgroup.add("enable");
/*       */             }
/*       */             else
/*       */             {
/*  3919 */               singlemonitorgroup.add("disable");
/*       */             }
/*  3921 */             singlemonitorgroup.add(haiGroupType);
/*  3922 */             listofGroups.add(singlemonitorgroup);
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  3927 */       Hashtable childlist = new Hashtable();
/*       */       try
/*       */       {
/*  3930 */         for (int k = 0; k < listofGroups.size(); k++)
/*       */         {
/*  3932 */           ArrayList singlerow = (ArrayList)listofGroups.get(k);
/*  3933 */           String tempid = (String)singlerow.get(6);
/*  3934 */           ArrayList mosinOrder = new ArrayList();
/*  3935 */           if (childMOsforMG.get(tempid) != null)
/*       */           {
/*  3937 */             mosinOrder = (ArrayList)childMOsforMG.get(tempid);
/*       */           }
/*  3939 */           if (childMOs.get(tempid) != null)
/*       */           {
/*  3941 */             ArrayList monitors = (ArrayList)childMOs.get(tempid);
/*  3942 */             for (int w = 0; w < monitors.size(); w++)
/*       */             {
/*  3944 */               mosinOrder.add(monitors.get(w));
/*       */             }
/*       */           }
/*  3947 */           if ((mosinOrder != null) && (mosinOrder.size() > 0))
/*       */           {
/*  3949 */             childlist.put(tempid, mosinOrder);
/*       */           }
/*       */           
/*  3952 */           if ((monGpType != null) && (typeBasedMGList.contains(tempid)))
/*       */           {
/*  3954 */             listOfGroupsBasedOnType.add(singlerow);
/*       */           }
/*       */           
/*       */         }
/*       */         
/*       */       }
/*       */       catch (Exception ex)
/*       */       {
/*  3962 */         ex.printStackTrace();
/*       */       }
/*  3964 */       if ((!request.isUserInRole("OPERATOR")) && (customValue == null) && (!Constants.isIt360))
/*       */       {
/*  3966 */         String types = Constants.resourceTypes;
/*  3967 */         if (Constants.sqlManager) {
/*  3968 */           types = "('MSSQL-DB-server')";
/*       */         }
/*  3970 */         ArrayList list1 = new ArrayList();
/*       */         
/*  3972 */         if (reqType.equalsIgnoreCase("SYS"))
/*       */         {
/*  3974 */           String query0 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID left outer join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE in " + modifiedSys1 + " and  (AM_HOLISTICAPPLICATION.HAID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*  3975 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  3977 */             query0 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID" + siteCondition + " left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE IN ('BSG0', 'BSG1') left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE in " + modifiedSys1 + " and  (AM_HOLISTICAPPLICATION_EXT.RESOURCEID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*       */           }
/*  3979 */           list1 = this.mo.getRows(query0);
/*       */         }
/*  3981 */         else if (reqType.equalsIgnoreCase("APPLICATION"))
/*       */         {
/*  3983 */           String query1 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID left outer join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where  AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.TYPE not in " + modifiedSys1 + "  and AM_ManagedObject.TYPE NOT LIKE '%OpManager%' and (AM_HOLISTICAPPLICATION.HAID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*  3984 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  3986 */             query1 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID" + siteCondition + " left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE IN ('BSG0', 'BSG1') left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where  AM_ManagedObject.TYPE in " + types + " and AM_ManagedObject.TYPE not in " + modifiedSys1 + " and AM_ManagedObject.TYPE NOT LIKE '%OpManager%' and AM_ManagedObject.TYPE NOT LIKE '%NODE%' and (AM_HOLISTICAPPLICATION_EXT.RESOURCEID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*       */           }
/*  3988 */           list1 = this.mo.getRows(query1);
/*       */         }
/*  3990 */         else if (reqType.equalsIgnoreCase("NWD"))
/*       */         {
/*  3992 */           String query2 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID left outer join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE like '%OpManager%' and  (AM_HOLISTICAPPLICATION.HAID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*  3993 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  3995 */             query2 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID" + siteCondition + " left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE IN ('BSG0', 'BSG1') left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE like '%OpManager%' and  (AM_HOLISTICAPPLICATION_EXT.RESOURCEID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*       */           }
/*  3997 */           list1 = this.mo.getRows(query2);
/*       */         }
/*       */         else
/*       */         {
/*  4001 */           String query3 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID left outer join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID left outer join  AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID  where " + eumChildListCond + " and AM_ManagedObject.TYPE in " + types + " and  (AM_HOLISTICAPPLICATION.HAID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*  4002 */           if (EnterpriseUtil.isIt360MSPEdition())
/*       */           {
/*  4004 */             query3 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID " + siteCondition + " left outer join  AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE NOT in ('CUSTOMER', 'SITE', 'SLA') left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE in " + types + " and  (AM_HOLISTICAPPLICATION_EXT.RESOURCEID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*       */           }
/*  4006 */           list1 = this.mo.getRows(query3);
/*       */         }
/*  4008 */         ArrayList singlemg = new ArrayList();
/*  4009 */         if (list1.size() > 0)
/*       */         {
/*  4011 */           for (int k = 0; k < list1.size(); k++)
/*       */           {
/*  4013 */             ArrayList temparraylist = (ArrayList)list1.get(k);
/*  4014 */             String resid = (String)temparraylist.get(0);
/*  4015 */             if (!MonitorsinMGs.contains(resid))
/*       */             {
/*       */ 
/*       */ 
/*  4019 */               String resname = (String)temparraylist.get(1);
/*  4020 */               String type = (String)temparraylist.get(2);
/*  4021 */               String imgpath = (String)temparraylist.get(3);
/*  4022 */               String shortname = (String)temparraylist.get(4);
/*  4023 */               String unmanage = (String)temparraylist.get(5);
/*  4024 */               String actionstatsus = (String)temparraylist.get(6);
/*  4025 */               String dcstarted = (String)temparraylist.get(7);
/*  4026 */               ArrayList singrow = new ArrayList();
/*  4027 */               singrow.add(resid);
/*  4028 */               singrow.add(resname);
/*  4029 */               singrow.add(type);
/*  4030 */               singrow.add(imgpath);
/*  4031 */               singrow.add(shortname);
/*  4032 */               singrow.add(unmanage);
/*  4033 */               singrow.add(actionstatsus);
/*  4034 */               singrow.add("enable");
/*       */               
/*  4036 */               singrow.add(dcstarted);
/*  4037 */               singlemg.add(singrow);
/*       */             } }
/*  4039 */           ArrayList singlemonitorgroup = new ArrayList();
/*  4040 */           singlemonitorgroup.add("Orphaned Monitors");
/*  4041 */           singlemonitorgroup.add("Orphaned Monitors");
/*  4042 */           singlemonitorgroup.add("0");
/*  4043 */           singlemonitorgroup.add("");
/*  4044 */           singlemonitorgroup.add("-1");
/*  4045 */           singlemonitorgroup.add("-1");
/*  4046 */           singlemonitorgroup.add("orphaned");
/*  4047 */           singlemonitorgroup.add("HAI");
/*  4048 */           singlemonitorgroup.add(null);
/*  4049 */           singlemonitorgroup.add("1");
/*  4050 */           listofGroups.add(singlemonitorgroup);
/*  4051 */           childlist.put("orphaned", singlemg);
/*       */         }
/*       */       }
/*  4054 */       showResourceTypes(mapping, form, request, response);
/*       */       
/*  4056 */       if (request.getParameter("fromwhere") != null)
/*       */       {
/*  4058 */         if (request.getParameter("fromwhere").equals("unmanagemonitorgroups"))
/*       */         {
/*  4060 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.message.text")));
/*       */         }
/*  4062 */         else if (request.getParameter("fromwhere").equals("unmanageandresetmonitorgroups"))
/*       */         {
/*  4064 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.unmanageandreset.message.text")));
/*       */         }
/*  4066 */         else if (request.getParameter("fromwhere").equals("managemonitorgroups"))
/*       */         {
/*  4068 */           String messagetosay = FormatUtil.getString("am.webclient.monitorgroupview.manage.message.text");
/*  4069 */           if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*       */           {
/*  4071 */             messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*       */           }
/*  4073 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*       */         }
/*  4075 */         else if (request.getParameter("fromwhere").equals("pollingMessage"))
/*       */         {
/*  4077 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.polling.updated.text")));
/*       */         }
/*  4079 */         else if (request.getParameter("fromwhere").equals("bulkupdate"))
/*       */         {
/*  4081 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.username.updated.text")));
/*       */         }
/*  4083 */         else if (request.getParameter("fromwhere").equals("enableactions"))
/*       */         {
/*  4085 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.enablemesaage.text"));
/*       */         }
/*  4087 */         else if (request.getParameter("fromwhere").equals("disableactions"))
/*       */         {
/*  4089 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.disablemesaage.text"));
/*       */         }
/*  4091 */         else if (request.getParameter("fromwhere").equals("afterdeletingMGs"))
/*       */         {
/*  4093 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.MG.success"));
/*       */         }
/*  4095 */         else if (request.getParameter("fromwhere").equals("afterdeleting"))
/*       */         {
/*  4097 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.success"));
/*       */         }
/*  4099 */         else if (request.getParameter("fromwhere").equals("deletemonitorsonly"))
/*       */         {
/*  4101 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.monitor.success"));
/*       */         }
/*       */       }
/*  4104 */       if (request.getParameter("retaintree") != null)
/*       */       {
/*  4106 */         retaintree = request.getParameter("retaintree");
/*       */       }
/*       */       
/*  4109 */       saveMessages(request, messages);
/*  4110 */       if ((listOfGroupsBasedOnType != null) && (listOfGroupsBasedOnType.size() > 0)) {
/*  4111 */         request.setAttribute("applications", listOfGroupsBasedOnType);
/*       */       }
/*       */       else {
/*  4114 */         request.setAttribute("applications", listofGroups);
/*       */       }
/*  4116 */       request.setAttribute("childlist", childlist);
/*  4117 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*  4118 */       request.setAttribute("retaintree", retaintree);
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  4123 */       ex.printStackTrace();
/*       */     }
/*  4125 */     return new ActionForward("/jsp/MonitorGroupView.jsp");
/*       */   }
/*       */   
/*       */   public ActionForward showMonitorGroupViewIndividual(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/*  4130 */     if ((request.isUserInRole("OPERATOR")) && (!Constants.isIt360))
/*       */     {
/*  4132 */       return mapping.findForward("accessRestricted");
/*       */     }
/*  4134 */     Vector<String> childVec = new Vector();
/*  4135 */     String retaintree = "";
/*       */     try {
/*  4137 */       response.setContentType("text/html");
/*       */       
/*  4139 */       String getAllLevels = request.getParameter("alllevels");
/*  4140 */       if ("orphaned".equalsIgnoreCase(request.getParameter("parents"))) {
/*  4141 */         showUncategorizedMonitors(request);
/*  4142 */         return new ActionForward("/jsp/ShowMoreMonitors.jsp");
/*       */       }
/*       */       
/*  4145 */       if ("true".equalsIgnoreCase(request.getParameter("alllevels"))) {
/*  4146 */         getAllResourceinHaid(request);
/*  4147 */         return new ActionForward("/jsp/ShowMoreMonitors.jsp");
/*       */       }
/*       */       
/*  4150 */       String customValue = request.getParameter("customValue");
/*  4151 */       String viewtype = request.getParameter("viewtype");
/*  4152 */       if (viewtype == null) {
/*  4153 */         viewtype = "";
/*       */       }
/*  4155 */       request.setAttribute("viewtype", viewtype);
/*       */       
/*       */ 
/*       */ 
/*  4159 */       String reqType = request.getParameter("type");
/*  4160 */       if (reqType == null) {
/*  4161 */         reqType = "";
/*       */       }
/*  4163 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  4164 */       request.setAttribute("reloadperiod", "120");
/*  4165 */       String userName = request.getRemoteUser();
/*  4166 */       ActionMessages messages = new ActionMessages();
/*  4167 */       ArrayList list = new ArrayList(5);
/*  4168 */       String modifiedSys = Constants.serverTypes;
/*  4169 */       modifiedSys = modifiedSys.substring(0, modifiedSys.length() - 1) + ", '" + Constants.virtualServers + "'";
/*       */       
/*  4171 */       String modifiedSys1 = modifiedSys + ",'NODE')";
/*  4172 */       modifiedSys = modifiedSys + ",'NODE','HAI')";
/*  4173 */       String haid1 = request.getParameter("parents");
/*  4174 */       if (haid1.contains("|")) {
/*  4175 */         haid1 = haid1.substring(haid1.lastIndexOf("|") + 1, haid1.length());
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  4180 */       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*  4181 */       String resTypes = Constants.resourceTypes;
/*  4182 */       int selectedPage = 1;
/*  4183 */       if (request.getParameter("pageno") != null) {
/*  4184 */         selectedPage = Integer.parseInt(request.getParameter("pageno"));
/*       */       }
/*  4186 */       if (resTypes.contains("OpManager")) {
/*  4187 */         resTypes = resTypes.replaceAll("OpManager", "");
/*       */       }
/*  4189 */       String query = "";
/*  4190 */       ExtDeviceActions extdevices; if (Constants.isIt360) {
/*  4191 */         extdevices = new ExtDeviceActions();
/*       */       }
/*  4193 */       String dataTable = "";
/*  4194 */       String qryCon = "";
/*  4195 */       String s12 = "";
/*  4196 */       String s14 = "";
/*  4197 */       String customField = "";
/*  4198 */       String value = "";
/*  4199 */       String query11 = "select A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,CASE WHEN child.TYPE='HAI' THEN -2 else -1 end as a,'-1' as b,'-1' as c,AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid as RSID,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE as tpe,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,HAI2.GROUPTYPE from " + dataTable + " AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_HOLISTICAPPLICATION as HAI2 on child.RESOURCEID=HAI2.HAID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and AM_HOLISTICAPPLICATION.HAID=" + haid1 + " " + qryCon + " order by a,A1.DISPLAYNAME,child.DISPLAYNAME";
/*  4200 */       int startIndex = (selectedPage - 1) * 100;
/*  4201 */       query11 = DBQueryUtil.addLimit(query11, startIndex, 100, "a,DISPLAYNAME,test");
/*  4202 */       list = this.mo.getRows(query11);
/*  4203 */       request.setAttribute("showmore", "false");
/*  4204 */       if (list.size() >= 100) {
/*  4205 */         request.setAttribute("showmore", "true");
/*       */       }
/*       */       
/*  4208 */       ArrayList listofGroups = new ArrayList();
/*  4209 */       Hashtable childMOs = new Hashtable();
/*       */       
/*  4211 */       String MGresourceid = null;
/*  4212 */       String childid = null;
/*  4213 */       String childname = null;
/*  4214 */       String childtype = null;
/*  4215 */       String MGactionstatus = null;
/*  4216 */       String imagepath = null;
/*  4217 */       String shortname = null;
/*  4218 */       String unmanageChildmos = null;
/*  4219 */       String dcstarted = null;
/*  4220 */       String haiGroupType = null;
/*  4221 */       for (int j = 0; j < list.size(); j++) {
/*  4222 */         ArrayList singlerow = (ArrayList)list.get(j);
/*  4223 */         MGresourceid = (String)singlerow.get(6);
/*  4224 */         childid = (String)singlerow.get(8);
/*  4225 */         childname = (String)singlerow.get(9);
/*  4226 */         childtype = (String)singlerow.get(10);
/*  4227 */         MGactionstatus = (String)singlerow.get(11);
/*  4228 */         imagepath = (String)singlerow.get(12);
/*  4229 */         shortname = (String)singlerow.get(13);
/*  4230 */         unmanageChildmos = (String)singlerow.get(14);
/*  4231 */         dcstarted = (String)singlerow.get(16);
/*  4232 */         haiGroupType = (String)singlerow.get(17);
/*       */         
/*       */ 
/*       */ 
/*  4236 */         if (childMOs.containsKey(MGresourceid))
/*       */         {
/*  4238 */           ArrayList childmo = null;
/*  4239 */           if (childtype != null)
/*       */           {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4245 */             if (childMOs.get(MGresourceid) != null) {
/*  4246 */               childmo = (ArrayList)childMOs.get(MGresourceid);
/*       */             } else {
/*  4248 */               childmo = new ArrayList();
/*  4249 */               childMOs.put(MGresourceid, childmo);
/*       */             }
/*  4251 */             ArrayList singrow = new ArrayList();
/*  4252 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null)) {
/*  4253 */               singrow.add(childid);
/*  4254 */               childVec.add(childid);
/*  4255 */               singrow.add(childname);
/*  4256 */               singrow.add(childtype);
/*  4257 */               singrow.add(imagepath);
/*  4258 */               singrow.add(shortname);
/*  4259 */               singrow.add(unmanageChildmos);
/*  4260 */               singrow.add((String)singlerow.get(2));
/*  4261 */               singrow.add("enable");
/*  4262 */               singrow.add(dcstarted);
/*  4263 */               singrow.add(haiGroupType);
/*  4264 */               childmo.add(singrow);
/*       */             }
/*       */           }
/*  4267 */         } else { ArrayList childmo1 = new ArrayList();
/*  4268 */           ArrayList singrow = new ArrayList();
/*       */           
/*       */ 
/*  4271 */           if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*       */           {
/*  4273 */             singrow.add(childid);
/*  4274 */             childVec.add(childid);
/*  4275 */             singrow.add(childname);
/*  4276 */             singrow.add(childtype);
/*  4277 */             singrow.add(imagepath);
/*  4278 */             singrow.add(shortname);
/*  4279 */             singrow.add(unmanageChildmos);
/*  4280 */             singrow.add((String)singlerow.get(2));
/*  4281 */             singrow.add("enable");
/*  4282 */             singrow.add(dcstarted);
/*  4283 */             singrow.add(haiGroupType);
/*  4284 */             childmo1.add(singrow);
/*       */             
/*  4286 */             childMOs.put(MGresourceid, childmo1);
/*       */           }
/*       */           else {
/*  4289 */             ArrayList dummylist = new ArrayList();
/*  4290 */             childMOs.put(MGresourceid, dummylist);
/*       */           }
/*       */           
/*  4293 */           ArrayList singlemonitorgroup = new ArrayList();
/*  4294 */           singlemonitorgroup.add((String)singlerow.get(0));
/*  4295 */           singlemonitorgroup.add((String)singlerow.get(1));
/*  4296 */           singlemonitorgroup.add((String)singlerow.get(15));
/*       */           
/*  4298 */           singlemonitorgroup.add((String)singlerow.get(3));
/*  4299 */           singlemonitorgroup.add((String)singlerow.get(4));
/*  4300 */           singlemonitorgroup.add((String)singlerow.get(5));
/*  4301 */           singlemonitorgroup.add(MGresourceid);
/*  4302 */           singlemonitorgroup.add("HAI");
/*  4303 */           singlemonitorgroup.add((String)singlerow.get(7));
/*  4304 */           singlemonitorgroup.add(MGactionstatus);
/*  4305 */           singlemonitorgroup.add("disable");
/*  4306 */           singlemonitorgroup.add(haiGroupType);
/*  4307 */           listofGroups.add(singlemonitorgroup);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  4312 */       if (request.getParameter("retaintree") != null) {
/*  4313 */         retaintree = request.getParameter("retaintree");
/*       */       }
/*  4315 */       HashMap<String, HashMap<String, String>> childMonitorInfo = ChildMOHandler.getChildMonitorWithParentInfo(childVec);
/*  4316 */       saveMessages(request, messages);
/*  4317 */       request.setAttribute("applications", listofGroups);
/*  4318 */       request.setAttribute("childlist", childMOs);
/*  4319 */       request.setAttribute("childMonitorInfo", childMonitorInfo);
/*  4320 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*  4321 */       request.setAttribute("retaintree", retaintree);
/*       */     } catch (Exception ex) {
/*  4323 */       ex.printStackTrace();
/*       */     }
/*  4325 */     return new ActionForward("/jsp/ShowMoreMonitors.jsp");
/*       */   }
/*       */   
/*       */   public void showUncategorizedMonitors(HttpServletRequest request)
/*       */   {
/*       */     try {
/*  4331 */       String customValue = request.getParameter("customValue");
/*  4332 */       String viewtype = request.getParameter("viewtype");
/*  4333 */       if (viewtype == null) {
/*  4334 */         viewtype = "";
/*       */       }
/*  4336 */       request.setAttribute("viewtype", viewtype);
/*  4337 */       String modifiedSys = Constants.serverTypes;
/*  4338 */       modifiedSys = modifiedSys.substring(0, modifiedSys.length() - 1) + ", '" + Constants.virtualServers + "'";
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  4343 */       String modifiedSys1 = modifiedSys + ",'NODE')";
/*  4344 */       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*       */       
/*  4346 */       String reqType = request.getParameter("type");
/*  4347 */       StringBuffer nongroupparents = new StringBuffer();
/*  4348 */       HashSet monhavingentry = new HashSet();
/*  4349 */       monhavingentry.add("VMWare ESX/ESXi");
/*  4350 */       monhavingentry.add("Amazon");
/*  4351 */       monhavingentry.add("Hyper-V-Server");
/*  4352 */       monhavingentry.add("WLS-Cluster");
/*  4353 */       String siteCondition = " and AM_PARENTCHILDMAPPER.PARENTID IN (";
/*       */       
/*  4355 */       int selectedPage = 1;
/*  4356 */       if (request.getParameter("pageno") != null) {
/*  4357 */         selectedPage = Integer.parseInt(request.getParameter("pageno"));
/*       */       }
/*       */       
/*  4360 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  4362 */         Properties siteProp = EnterpriseUtil.getSiteProp(request);
/*  4363 */         if (siteProp != null)
/*       */         {
/*  4365 */           String siteName = siteProp.keys().nextElement().toString();
/*  4366 */           String siteId = siteProp.getProperty(siteName);
/*  4367 */           siteCondition = siteCondition + siteId + ")";
/*       */         }
/*       */         else {
/*  4370 */           Properties custProp = EnterpriseUtil.getCustProp(request);
/*  4371 */           if ((custProp != null) && (custProp.size() > 0)) {
/*  4372 */             String custName = custProp.keys().nextElement().toString();
/*       */             
/*  4374 */             String custId = custProp.getProperty(custName);
/*  4375 */             Vector<String> assoSiteVect = EnterpriseUtil.getAllAssociatedSites("" + custId, EnterpriseUtil.getLoggedInUserName(request));
/*       */             
/*       */ 
/*       */ 
/*  4379 */             if ((assoSiteVect != null) && (assoSiteVect.size() > 0)) {
/*  4380 */               String csv = assoSiteVect.toString();
/*  4381 */               csv = csv.substring(1, csv.length() - 1);
/*  4382 */               siteCondition = siteCondition + csv + ")";
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*  4387 */       if ((!request.isUserInRole("OPERATOR")) && (customValue == null) && (!Constants.isIt360))
/*       */       {
/*  4389 */         String types = Constants.resourceTypes;
/*  4390 */         if (Constants.sqlManager) {
/*  4391 */           types = "('MSSQL-DB-server')";
/*       */         }
/*  4393 */         ArrayList list1 = null;
/*  4394 */         String Parentcondn = "select distinct RESOURCEID from AM_ManagedObject, AM_ParentChildMapper where AM_ManagedObject.RESOURCEID=AM_ParentChildMapper.PARENTID and TYPE<>'HAI'";
/*  4395 */         String query3 = "SELECT  AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join  AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where " + eumChildListCond + " and AM_ManagedResourceType.RESOURCETYPE in " + types + "and AM_PARENTCHILDMAPPER.CHILDID is null  order by AM_ManagedObject.DISPLAYNAME";
/*  4396 */         if (EnterpriseUtil.isIt360MSPEdition()) {
/*  4397 */           query3 = "SELECT  Distinct AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED from AM_ManagedObject left outer join  AM_PARENTCHILDMAPPER on  AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID join  AM_HOLISTICAPPLICATION  on AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID " + siteCondition + " left outer join  AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE NOT in ('CUSTOMER', 'SITE', 'SLA') left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where AM_ManagedObject.TYPE in " + types + " and  (AM_HOLISTICAPPLICATION_EXT.RESOURCEID is NULL or AM_PARENTCHILDMAPPER.CHILDID is null) order by AM_ManagedObject.DISPLAYNAME";
/*       */         }
/*  4399 */         int startIndex = (selectedPage - 1) * 100;
/*  4400 */         query3 = DBQueryUtil.addLimit(query3, startIndex, 100, "AM_ManagedObject.DISPLAYNAME");
/*  4401 */         list1 = this.mo.getRows(query3);
/*       */         
/*  4403 */         request.setAttribute("showmore", "false");
/*  4404 */         if (list1.size() >= 100) {
/*  4405 */           request.setAttribute("showmore", "true");
/*       */         }
/*       */         
/*  4408 */         request.setAttribute("nouncategorize", "false");
/*  4409 */         if ((selectedPage == 1) && (list1.size() == 0)) {
/*  4410 */           request.setAttribute("nouncategorize", "true");
/*       */         }
/*  4412 */         ArrayList singlemg = new ArrayList();
/*  4413 */         Hashtable childlist = new Hashtable();
/*  4414 */         ArrayList listofGroups = new ArrayList();
/*  4415 */         if (list1.size() > 0) {
/*  4416 */           for (int k = 0; k < list1.size(); k++) {
/*  4417 */             ArrayList temparraylist = (ArrayList)list1.get(k);
/*  4418 */             String resid = (String)temparraylist.get(0);
/*  4419 */             String resname = (String)temparraylist.get(1);
/*  4420 */             String type = (String)temparraylist.get(2);
/*  4421 */             if (monhavingentry.contains(type)) {
/*  4422 */               nongroupparents.append(resid + ",");
/*       */             }
/*  4424 */             String imgpath = (String)temparraylist.get(3);
/*  4425 */             String shortname = (String)temparraylist.get(4);
/*  4426 */             String unmanage = (String)temparraylist.get(5);
/*  4427 */             String actionstatsus = (String)temparraylist.get(6);
/*  4428 */             String dcstarted = (String)temparraylist.get(7);
/*  4429 */             ArrayList singrow = new ArrayList();
/*  4430 */             singrow.add(resid);
/*  4431 */             singrow.add(resname);
/*  4432 */             singrow.add(type);
/*  4433 */             singrow.add(imgpath);
/*  4434 */             singrow.add(shortname);
/*  4435 */             singrow.add(unmanage);
/*  4436 */             singrow.add(actionstatsus);
/*  4437 */             singrow.add("enable");
/*  4438 */             singrow.add(dcstarted);
/*  4439 */             singlemg.add(singrow);
/*       */           }
/*  4441 */           ResultSet rs = null;
/*       */           try {
/*  4443 */             if ((nongroupparents != null) && (nongroupparents.length() > 0))
/*       */             {
/*  4445 */               String getmonquery = "select CHILDID from AM_ParentChildMapper where PARENTID in (" + nongroupparents.substring(0, nongroupparents.length() - 1) + ")";
/*  4446 */               String finaqry = "select CHILDID from AM_ParentChildMapper where CHILDID in (" + getmonquery + ") group by CHILDID having (COUNT(CHILDID) = 1)";
/*  4447 */               StringBuffer sb = new StringBuffer();
/*  4448 */               rs = AMConnectionPool.executeQueryStmt(finaqry);
/*  4449 */               while (rs.next()) {
/*  4450 */                 sb.append(rs.getString("CHILDID") + ",");
/*       */               }
/*  4452 */               if (rs != null) {
/*  4453 */                 AMConnectionPool.closeStatement(rs);
/*       */               }
/*  4455 */               String childidcondn = "";
/*  4456 */               if ((sb != null) && (sb.length() > 0)) {
/*  4457 */                 childidcondn = "and AM_ManagedObject.RESOURCEID in (" + sb.substring(0, sb.length() - 1) + ")";
/*       */               }
/*  4459 */               String uncat = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_UnManagedNodes.resid,AM_ManagedObject.ACTIONSTATUS,AM_ManagedObject.DCSTARTED  from AM_ManagedObject inner join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join  AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_ManagedObject.RESOURCEID where " + eumChildListCond + " and AM_ManagedResourceType.RESOURCETYPE in " + types + " " + childidcondn + " order by AM_ManagedObject.DISPLAYNAME";
/*  4460 */               ArrayList list11 = this.mo.getRows(uncat);
/*  4461 */               if (list11.size() > 0) {
/*  4462 */                 for (int k = 0; k < list11.size(); k++) {
/*  4463 */                   ArrayList temparraylist = (ArrayList)list11.get(k);
/*  4464 */                   String resid = (String)temparraylist.get(0);
/*  4465 */                   String resname = (String)temparraylist.get(1);
/*  4466 */                   String type = (String)temparraylist.get(2);
/*  4467 */                   String imgpath = (String)temparraylist.get(3);
/*  4468 */                   String shortname = (String)temparraylist.get(4);
/*  4469 */                   String unmanage = (String)temparraylist.get(5);
/*  4470 */                   String actionstatsus = (String)temparraylist.get(6);
/*  4471 */                   String dcstarted = (String)temparraylist.get(7);
/*  4472 */                   ArrayList singrow = new ArrayList();
/*  4473 */                   singrow.add(resid);
/*  4474 */                   singrow.add(resname);
/*  4475 */                   singrow.add(type);
/*  4476 */                   singrow.add(imgpath);
/*  4477 */                   singrow.add(shortname);
/*  4478 */                   singrow.add(unmanage);
/*  4479 */                   singrow.add(actionstatsus);
/*  4480 */                   singrow.add("enable");
/*  4481 */                   singrow.add(dcstarted);
/*  4482 */                   singlemg.add(singrow);
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*       */           catch (Exception ex)
/*       */           {
/*  4489 */             ex.printStackTrace();
/*       */           }
/*       */           finally {
/*  4492 */             if (rs != null) {
/*  4493 */               AMConnectionPool.closeStatement(rs);
/*       */             }
/*       */           }
/*       */         }
/*  4497 */         ArrayList singlemonitorgroup = new ArrayList();
/*  4498 */         singlemonitorgroup.add("Orphaned Monitors");
/*  4499 */         singlemonitorgroup.add("Orphaned Monitors");
/*  4500 */         singlemonitorgroup.add("0");
/*  4501 */         singlemonitorgroup.add("");
/*  4502 */         singlemonitorgroup.add("-1");
/*  4503 */         singlemonitorgroup.add("-1");
/*  4504 */         singlemonitorgroup.add("orphaned");
/*  4505 */         singlemonitorgroup.add("HAI");
/*  4506 */         singlemonitorgroup.add(null);
/*  4507 */         singlemonitorgroup.add("1");
/*  4508 */         listofGroups.add(singlemonitorgroup);
/*  4509 */         childlist.put("orphaned", singlemg);
/*  4510 */         request.setAttribute("applications", listofGroups);
/*  4511 */         request.setAttribute("childlist", childlist);
/*       */       }
/*  4513 */       ChildMOHandler.showUncategorizedMonitors(request);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  4517 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */   public void getAllResourceinHaid(HttpServletRequest request) {
/*       */     try {
/*  4523 */       String haid = request.getParameter("parents");
/*  4524 */       ArrayList filteredHaids = new ArrayList();
/*  4525 */       ArrayList haids = new ArrayList();
/*  4526 */       ArrayList tempList = new ArrayList();
/*  4527 */       tempList.add(haid);
/*  4528 */       ReportUtil.getLastLevelSubGroup(tempList, haid);
/*  4529 */       haids.addAll(tempList);
/*  4530 */       filteredHaids.addAll(tempList);
/*  4531 */       Vector mgsForOperator = null;
/*  4532 */       ArrayList MonitorsinMGs = new ArrayList();
/*  4533 */       String query = "select Distinct A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE,child.DCSTARTED,AM_HOLISTICAPPLICATION.GROUPTYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and " + ManagedApplication.getCondition("AM_HOLISTICAPPLICATION.HAID", haids) + "  order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*  4534 */       ArrayList list = this.mo.getRows(query);
/*  4535 */       ArrayList listofGroups = new ArrayList();
/*  4536 */       Hashtable childMOs = new Hashtable();
/*  4537 */       Hashtable childMOsforMG = new Hashtable();
/*  4538 */       for (int j = 0; j < list.size(); j++)
/*       */       {
/*  4540 */         ArrayList singlerow = (ArrayList)list.get(j);
/*  4541 */         String resourcename = (String)singlerow.get(0);
/*  4542 */         String displayname = (String)singlerow.get(1);
/*  4543 */         String childactionstatus = (String)singlerow.get(2);
/*  4544 */         String owner = (String)singlerow.get(3);
/*  4545 */         String CREATIONDATE = (String)singlerow.get(4);
/*  4546 */         String MODIFIEDDATE = (String)singlerow.get(5);
/*  4547 */         String MGresourceid = (String)singlerow.get(6);
/*  4548 */         String unmanagednodes = (String)singlerow.get(7);
/*  4549 */         String childid = (String)singlerow.get(8);
/*  4550 */         String childname = (String)singlerow.get(9);
/*  4551 */         String childtype = (String)singlerow.get(10);
/*  4552 */         String MGactionstatus = (String)singlerow.get(11);
/*  4553 */         String imagepath = (String)singlerow.get(12);
/*  4554 */         String shortname = (String)singlerow.get(13);
/*  4555 */         String unmanageChildmos = (String)singlerow.get(14);
/*  4556 */         String MGType = (String)singlerow.get(15);
/*  4557 */         String dcstarted = (String)singlerow.get(16);
/*  4558 */         String haiGroupType = (String)singlerow.get(17);
/*       */         
/*  4560 */         if (haid.equals(MGresourceid))
/*       */         {
/*       */ 
/*  4563 */           MGType = "0";
/*       */         }
/*  4565 */         if (haids.contains(MGresourceid))
/*       */         {
/*       */ 
/*       */ 
/*  4569 */           MonitorsinMGs.add(childid);
/*  4570 */           if ((childMOs.containsKey(MGresourceid)) || (childMOsforMG.containsKey(MGresourceid)))
/*       */           {
/*  4572 */             ArrayList childmo = null;
/*  4573 */             if (childtype != null)
/*       */             {
/*       */ 
/*       */ 
/*       */ 
/*  4578 */               if (childtype.equals("HAI"))
/*       */               {
/*  4580 */                 if (!haids.contains(childid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*  4584 */                 if (childMOsforMG.get(MGresourceid) != null)
/*       */                 {
/*  4586 */                   childmo = (ArrayList)childMOsforMG.get(MGresourceid);
/*       */                 }
/*       */                 else
/*       */                 {
/*  4590 */                   childmo = new ArrayList();
/*  4591 */                   childMOsforMG.put(MGresourceid, childmo);
/*       */                 }
/*       */               }
/*       */               else
/*       */               {
/*  4596 */                 if (!filteredHaids.contains(MGresourceid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*  4600 */                 if (childMOs.get(MGresourceid) != null)
/*       */                 {
/*  4602 */                   childmo = (ArrayList)childMOs.get(MGresourceid);
/*       */                 }
/*       */                 else
/*       */                 {
/*  4606 */                   childmo = new ArrayList();
/*  4607 */                   childMOs.put(MGresourceid, childmo);
/*       */                 }
/*       */               }
/*       */               
/*  4611 */               ArrayList singrow = new ArrayList();
/*  4612 */               if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null))
/*       */               {
/*  4614 */                 singrow.add(childid);
/*  4615 */                 singrow.add(childname);
/*  4616 */                 singrow.add(childtype);
/*  4617 */                 singrow.add(imagepath);
/*  4618 */                 singrow.add(shortname);
/*  4619 */                 singrow.add(unmanageChildmos);
/*  4620 */                 singrow.add(childactionstatus);
/*  4621 */                 if (!filteredHaids.contains(childid))
/*       */                 {
/*  4623 */                   singrow.add("disable");
/*       */                 }
/*       */                 else
/*       */                 {
/*  4627 */                   singrow.add("enable");
/*       */                 }
/*  4629 */                 singrow.add(dcstarted);
/*  4630 */                 singrow.add(haiGroupType);
/*  4631 */                 childmo.add(singrow);
/*       */               }
/*       */             }
/*       */           }
/*       */           else {
/*  4636 */             ArrayList childmo1 = new ArrayList();
/*  4637 */             ArrayList singrow = new ArrayList();
/*       */             
/*  4639 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*       */             {
/*  4641 */               singrow.add(childid);
/*  4642 */               singrow.add(childname);
/*  4643 */               singrow.add(childtype);
/*  4644 */               singrow.add(imagepath);
/*  4645 */               singrow.add(shortname);
/*  4646 */               singrow.add(unmanageChildmos);
/*  4647 */               singrow.add(childactionstatus);
/*  4648 */               if (!filteredHaids.contains(childid))
/*       */               {
/*  4650 */                 singrow.add("disable");
/*       */               }
/*       */               else
/*       */               {
/*  4654 */                 singrow.add("enable");
/*       */               }
/*       */               
/*  4657 */               singrow.add(dcstarted);
/*  4658 */               singrow.add(haiGroupType);
/*  4659 */               childmo1.add(singrow);
/*       */               
/*  4661 */               if (childtype.equals("HAI"))
/*       */               {
/*  4663 */                 if (!haids.contains(childid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*  4667 */                 childMOsforMG.put(MGresourceid, childmo1);
/*       */               }
/*       */               else
/*       */               {
/*  4671 */                 if (!filteredHaids.contains(MGresourceid)) {
/*       */                   continue;
/*       */                 }
/*       */                 
/*       */ 
/*  4676 */                 childMOs.put(MGresourceid, childmo1);
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*       */             else
/*       */             {
/*  4683 */               ArrayList dummylist = new ArrayList();
/*  4684 */               childMOs.put(MGresourceid, dummylist);
/*       */             }
/*       */             
/*  4687 */             ArrayList singlemonitorgroup = new ArrayList();
/*  4688 */             singlemonitorgroup.add(resourcename);
/*  4689 */             singlemonitorgroup.add(displayname);
/*  4690 */             singlemonitorgroup.add(MGType);
/*  4691 */             singlemonitorgroup.add(owner);
/*  4692 */             singlemonitorgroup.add(CREATIONDATE);
/*  4693 */             singlemonitorgroup.add(MODIFIEDDATE);
/*  4694 */             singlemonitorgroup.add(MGresourceid);
/*  4695 */             singlemonitorgroup.add("HAI");
/*  4696 */             singlemonitorgroup.add(unmanagednodes);
/*  4697 */             singlemonitorgroup.add(MGactionstatus);
/*  4698 */             if (filteredHaids.contains(MGresourceid))
/*       */             {
/*  4700 */               singlemonitorgroup.add("enable");
/*       */             }
/*       */             else
/*       */             {
/*  4704 */               singlemonitorgroup.add("disable");
/*       */             }
/*  4706 */             singlemonitorgroup.add(haiGroupType);
/*  4707 */             listofGroups.add(singlemonitorgroup);
/*       */           }
/*       */         } }
/*  4710 */       Hashtable childlist = new Hashtable();
/*       */       try
/*       */       {
/*  4713 */         for (int k = 0; k < listofGroups.size(); k++)
/*       */         {
/*  4715 */           ArrayList singlerow = (ArrayList)listofGroups.get(k);
/*  4716 */           String tempid = (String)singlerow.get(6);
/*  4717 */           ArrayList mosinOrder = new ArrayList();
/*  4718 */           if (childMOsforMG.get(tempid) != null)
/*       */           {
/*  4720 */             mosinOrder = (ArrayList)childMOsforMG.get(tempid);
/*       */           }
/*  4722 */           if (childMOs.get(tempid) != null)
/*       */           {
/*  4724 */             ArrayList monitors = (ArrayList)childMOs.get(tempid);
/*  4725 */             for (int w = 0; w < monitors.size(); w++)
/*       */             {
/*  4727 */               mosinOrder.add(monitors.get(w));
/*       */             }
/*       */           }
/*  4730 */           if ((mosinOrder != null) && (mosinOrder.size() > 0))
/*       */           {
/*  4732 */             childlist.put(tempid, mosinOrder);
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */       catch (Exception ex)
/*       */       {
/*  4739 */         ex.printStackTrace();
/*       */       }
/*  4741 */       request.setAttribute("applications", listofGroups);
/*  4742 */       request.setAttribute("childlist", childlist);
/*  4743 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*       */     }
/*       */     catch (Exception e) {}
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private void setActionMessages(HttpServletRequest request, ActionMessages messages)
/*       */   {
/*       */     try
/*       */     {
/*  4754 */       if ((request.getParameter("apimessage") != null) && (request.getParameter("apimessage").trim().length() > 0) && (!"null".equalsIgnoreCase(request.getParameter("apimessage"))))
/*       */       {
/*  4756 */         String apimessages = FormatUtil.getString("am.webclient.monitorgrp.apimessage.action.failed") + request.getParameter("apimessage");
/*  4757 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(apimessages));
/*  4758 */         return;
/*       */       }
/*  4760 */       if ((request.getParameter("fromwhere").equals("managemonitors")) && (showMessage(request, messages)))
/*       */       {
/*  4762 */         if ((request.getParameter("messageneeded") == null) || (request.getParameter("messageneeded").trim().equals("")) || (!request.getParameter("messageneeded").equals("false")))
/*       */         {
/*  4764 */           String messagetosay = FormatUtil.getString("am.webclient.managed.datacollection.starts.text");
/*  4765 */           if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*       */           {
/*  4767 */             messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*       */           }
/*  4769 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*       */         }
/*       */       }
/*  4772 */       else if ((request.getParameter("fromwhere").equals("unmanagemonitors")) && (showMessage(request, messages)))
/*       */       {
/*  4774 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.unmanaged.datacollectionstops.text")));
/*       */       }
/*  4776 */       else if ((request.getParameter("fromwhere").equals("unmanageandresetmonitors")) && (showMessage(request, messages)))
/*       */       {
/*  4778 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.unmanagedandreset.datacollectionstops.text")));
/*       */       }
/*  4780 */       else if (request.getParameter("fromwhere").equals("unabletocreate"))
/*       */       {
/*  4782 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction1", String.valueOf(FreeEditionDetails.getFreeEditionDetails().getNumberOfMonitorsPermitted())));
/*  4783 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("registered.monitor.restriction2"));
/*  4784 */       } else if (request.getParameter("fromwhere").equals("operator")) {
/*  4785 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.userauthorization.unaunthorised")));
/*       */ 
/*       */ 
/*       */       }
/*  4789 */       else if (request.getParameter("fromwhere").equals("unmanagemonitorgroups"))
/*       */       {
/*  4791 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.unmanage.message.text")));
/*       */       }
/*  4793 */       else if (request.getParameter("fromwhere").equals("unmanageandresetmonitorgroups"))
/*       */       {
/*  4795 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.monitorgroupview.unmanageandreset.message.text")));
/*       */       }
/*  4797 */       else if (request.getParameter("fromwhere").equals("managemonitorgroups"))
/*       */       {
/*  4799 */         String messagetosay = FormatUtil.getString("am.webclient.monitorgroupview.manage.message.text");
/*  4800 */         if ((request.getParameter("notallowed") != null) && (request.getParameter("notallowed").equals("true")))
/*       */         {
/*  4802 */           messagetosay = FormatUtil.getString("am.webclient.couldnotmanage.text", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*       */         }
/*  4804 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messagetosay));
/*       */       }
/*  4806 */       else if (request.getParameter("fromwhere").equals("pollingMessage"))
/*       */       {
/*  4808 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.polling.updated.text")));
/*       */       }
/*  4810 */       else if (request.getParameter("fromwhere").equals("bulkupdate"))
/*       */       {
/*  4812 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.username.updated.text")));
/*       */       }
/*  4814 */       else if (request.getParameter("fromwhere").equals("enableactions"))
/*       */       {
/*  4816 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.enablemesaage.text"));
/*       */       }
/*  4818 */       else if (request.getParameter("fromwhere").equals("disableactions"))
/*       */       {
/*  4820 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.disablemesaage.text"));
/*       */       }
/*  4822 */       else if (request.getParameter("fromwhere").equals("afterdeletingMGs"))
/*       */       {
/*  4824 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.MG.success"));
/*       */       }
/*  4826 */       else if (request.getParameter("fromwhere").equals("afterdeleting"))
/*       */       {
/*  4828 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.success"));
/*       */       }
/*  4830 */       else if (request.getParameter("fromwhere").equals("deletemonitorsonly"))
/*       */       {
/*  4832 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupview.delete.monitor.success"));
/*       */       }
/*       */     }
/*       */     catch (Exception e) {
/*  4836 */       AMLog.debug("Exception when setting action messages " + e.getMessage());
/*  4837 */       e.printStackTrace();
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */   public ArrayList getMonitorTypesforNewMonitor()
/*       */   {
/*  4844 */     return getMonitorTypesforNewMonitor(false);
/*       */   }
/*       */   
/*       */   public ArrayList getMonitorTypesforNewMonitor(boolean listMonitorsByOrder) {
/*  4848 */     ArrayList row = new ArrayList();
/*       */     try
/*       */     {
/*  4851 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */       
/*  4853 */       ResultSet rs = null;
/*  4854 */       String query = null;
/*       */       
/*       */ 
/*  4857 */       String[] temp = { "APP", "ERP", "TM", "MS", "SYS", "DBS", "SER", "URL", "MOM", "CAM", "VIR", "CLD" };
/*  4858 */       Properties p1 = new Properties();
/*  4859 */       p1.setProperty("APP", FormatUtil.getString("Application Servers"));
/*  4860 */       p1.setProperty("ERP", FormatUtil.getString("ERP"));
/*  4861 */       p1.setProperty("TM", FormatUtil.getString("am.monitortab.transactionmonitors.text"));
/*  4862 */       p1.setProperty("SYS", FormatUtil.getString("Systems"));
/*  4863 */       p1.setProperty("DBS", FormatUtil.getString("Database Servers"));
/*  4864 */       p1.setProperty("SER", FormatUtil.getString("Services"));
/*  4865 */       p1.setProperty("URL", FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices.title"));
/*  4866 */       p1.setProperty("MS", FormatUtil.getString("Mail Servers"));
/*  4867 */       p1.setProperty("MOM", FormatUtil.getString("Middleware/Portal"));
/*  4868 */       p1.setProperty("CAM", FormatUtil.getString("Custom Monitors"));
/*  4869 */       p1.setProperty("VIR", FormatUtil.getString("am.webclient.monitorgroupsecond.category.virtualserver"));
/*  4870 */       p1.setProperty("CLD", FormatUtil.getString("am.webclient.monitorgroupsecond.category.cloudapps"));
/*       */       
/*       */ 
/*  4873 */       ArrayList unsupported = Constants.getUnSupportedAsList();
/*       */       
/*       */ 
/*  4876 */       Properties props = Constants.getValueForNewMonitor();
/*       */       
/*  4878 */       boolean isVcenterAdded = false;
/*  4879 */       for (int i = 0; i < temp.length; i++)
/*       */       {
/*  4881 */         ArrayList subdata = new ArrayList();
/*  4882 */         String resGroup = listMonitorsByOrder == true ? "ALL" : temp[i];
/*  4883 */         query = getResourceTypeQueryforNewMonitor(resGroup);
/*       */         
/*  4885 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  4886 */         if (!unsupported.contains(resGroup))
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  4908 */           boolean firstTime = true;
/*       */           
/*       */ 
/*  4911 */           while (rs.next())
/*       */           {
/*  4913 */             resGroup = listMonitorsByOrder == true ? rs.getString("RESOURCEGROUP") : resGroup;
/*  4914 */             if ((!listMonitorsByOrder) || (!unsupported.contains(resGroup)))
/*       */             {
/*       */ 
/*  4917 */               if ((firstTime) && (!listMonitorsByOrder))
/*       */               {
/*       */                 try
/*       */                 {
/*  4921 */                   subdata.add(FormatUtil.getString(p1.getProperty(resGroup)));
/*  4922 */                   subdata.add(resGroup);
/*  4923 */                   subdata.add("-");
/*  4924 */                   row.add(subdata);
/*  4925 */                   firstTime = false;
/*       */                 }
/*       */                 catch (Exception exc) {
/*  4928 */                   System.out.println("Problem while checking out whether the type is allowed or not.. for the new monitor Caption.This should never occur..Mysql problem");
/*  4929 */                   exc.printStackTrace();
/*       */                 }
/*       */               }
/*  4932 */               String newmonitortype = rs.getString("RESOURCETYPE");
/*  4933 */               AMLog.debug("unsupported" + unsupported);
/*  4934 */               if (((newmonitortype == null) || (!ConfMonitorConfiguration.getInstance().isDependentMonitor(newmonitortype))) && (
/*       */               
/*       */ 
/*  4937 */                 (!EnterpriseUtil.isCloudEdition()) || (!unsupported.contains(newmonitortype))))
/*       */               {
/*       */ 
/*       */ 
/*  4941 */                 ArrayList data = new ArrayList();
/*  4942 */                 if (OEMUtil.isOEM())
/*       */                 {
/*  4944 */                   if (OEMUtil.isRemove("am.wmimonitors.remove")) {
/*  4945 */                     if ("SYS".equals(resGroup)) {
/*  4946 */                       if (!"Unknown".equals(rs.getString(2))) {
/*  4947 */                         data.add(FormatUtil.getString(rs.getString(2)));
/*       */                         
/*  4949 */                         data.add(rs.getString(2));
/*  4950 */                         data.add(rs.getString(4));
/*  4951 */                         data.add(rs.getString("RESOURCETYPE"));
/*  4952 */                         row.add(data);
/*       */                       }
/*       */                       
/*       */                     }
/*  4956 */                     else if ("MS".equals(resGroup))
/*       */                     {
/*  4958 */                       if (!"Exchange Server".equals(rs.getString(2))) {
/*  4959 */                         data.add(FormatUtil.getString(rs.getString(2)));
/*       */                         
/*  4961 */                         data.add(props.getProperty(rs.getString(1)));
/*  4962 */                         data.add(rs.getString(4));
/*  4963 */                         data.add(rs.getString("RESOURCETYPE"));
/*  4964 */                         row.add(data);
/*       */                       }
/*       */                       
/*       */ 
/*       */                     }
/*  4969 */                     else if ("APP".equals(resGroup)) {
/*  4970 */                       if (!"Microsoft .NET".equals(rs.getString(2))) {
/*  4971 */                         data.add(FormatUtil.getString(rs.getString(2)));
/*       */                         
/*  4973 */                         data.add(props.getProperty(rs.getString(1)));
/*  4974 */                         data.add(rs.getString(4));
/*  4975 */                         data.add(rs.getString("RESOURCETYPE"));
/*  4976 */                         row.add(data);
/*       */                       }
/*       */                       
/*       */ 
/*       */                     }
/*  4981 */                     else if ("SER".equals(resGroup)) {
/*  4982 */                       if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  4983 */                         data.add(FormatUtil.getString(rs.getString(2)));
/*       */                         
/*  4985 */                         data.add(props.getProperty(rs.getString(1)));
/*  4986 */                         data.add(rs.getString(4));
/*  4987 */                         data.add(rs.getString("RESOURCETYPE"));
/*  4988 */                         row.add(data);
/*       */                       }
/*       */                       
/*       */ 
/*       */                     }
/*  4993 */                     else if ("CAM".equals(resGroup)) {
/*  4994 */                       if ((!"Windows Performance Counters".equals(rs.getString(2))) && (!"QEngine Script Monitor".equals(rs.getString(2)))) {
/*  4995 */                         data.add(FormatUtil.getString(rs.getString(2)));
/*       */                         
/*  4997 */                         data.add(props.getProperty(rs.getString(1)));
/*  4998 */                         data.add(rs.getString(4));
/*  4999 */                         data.add(rs.getString("RESOURCETYPE"));
/*  5000 */                         row.add(data);
/*       */                       }
/*       */                       
/*       */                     }
/*       */                     else
/*       */                     {
/*  5006 */                       data.add(FormatUtil.getString(rs.getString(2)));
/*  5007 */                       data.add(props.getProperty(rs.getString(1)));
/*  5008 */                       data.add(rs.getString(4));
/*  5009 */                       data.add(rs.getString("RESOURCETYPE"));
/*  5010 */                       row.add(data);
/*       */                     }
/*       */                     
/*       */                   }
/*  5014 */                   else if ("SYS".equals(resGroup)) {
/*  5015 */                     if (!"Unknown".equals(rs.getString(2))) {
/*  5016 */                       data.add(FormatUtil.getString(rs.getString(2)));
/*       */                       
/*  5018 */                       data.add(rs.getString(2));
/*  5019 */                       data.add(rs.getString(4));
/*  5020 */                       data.add(rs.getString("RESOURCETYPE"));
/*  5021 */                       row.add(data);
/*       */                     }
/*       */                     
/*       */                   }
/*  5025 */                   else if ("SER".equals(resGroup)) {
/*  5026 */                     if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  5027 */                       data.add(FormatUtil.getString(rs.getString(2)));
/*  5028 */                       data.add(props.getProperty(rs.getString(1)));
/*  5029 */                       data.add(rs.getString(4));
/*  5030 */                       data.add(rs.getString("RESOURCETYPE"));
/*  5031 */                       row.add(data);
/*       */                     }
/*       */                   }
/*       */                   else {
/*  5035 */                     data.add(FormatUtil.getString(rs.getString(2)));
/*  5036 */                     if (props.getProperty(rs.getString(1)) != null)
/*       */                     {
/*  5038 */                       data.add(props.getProperty(rs.getString(1)));
/*       */                     }
/*  5040 */                     else if ((props.getProperty(rs.getString(1)) == null) && ("File System Monitor".equals(rs.getString(1))))
/*       */                     {
/*  5042 */                       data.add(rs.getString(1));
/*       */                     }
/*       */                     else
/*       */                     {
/*  5046 */                       data.add(FormatUtil.getString(rs.getString(2)));
/*       */                     }
/*  5048 */                     data.add(rs.getString(4));
/*  5049 */                     data.add(rs.getString("RESOURCETYPE"));
/*  5050 */                     row.add(data);
/*       */                     
/*  5052 */                     if ((temp[i].equals("VIR")) && (!isVcenterAdded))
/*       */                     {
/*  5054 */                       ArrayList<String> vcenArr = new ArrayList() {};
/*  5055 */                       row.add(vcenArr);
/*  5056 */                       isVcenterAdded = true;
/*       */ 
/*       */                     }
/*       */                     
/*       */ 
/*       */                   }
/*       */                   
/*       */ 
/*       */                 }
/*  5065 */                 else if ("SYS".equals(resGroup)) {
/*  5066 */                   if (!"Unknown".equals(rs.getString(2))) {
/*  5067 */                     data.add(FormatUtil.getString(rs.getString(2)));
/*       */                     
/*  5069 */                     data.add(rs.getString(2));
/*  5070 */                     data.add(rs.getString(4));
/*  5071 */                     data.add(rs.getString("RESOURCETYPE"));
/*  5072 */                     row.add(data);
/*       */                   }
/*       */                   
/*       */                 }
/*  5076 */                 else if ("SER".equals(resGroup)) {
/*  5077 */                   if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  5078 */                     data.add(FormatUtil.getString(rs.getString(2)));
/*  5079 */                     data.add(props.getProperty(rs.getString(1)));
/*  5080 */                     data.add(rs.getString(4));
/*  5081 */                     data.add(rs.getString("RESOURCETYPE"));
/*  5082 */                     row.add(data);
/*       */                   }
/*       */                 }
/*       */                 else {
/*  5086 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*  5087 */                   String type = "";
/*       */                   try
/*       */                   {
/*  5090 */                     if (rs.getString(1).equals("File System Monitor"))
/*       */                     {
/*  5092 */                       type = "File / Directory Monitor";
/*       */                     }
/*       */                     else
/*       */                     {
/*  5096 */                       type = rs.getString(1);
/*       */                     }
/*       */                   } catch (Exception ex) {
/*  5099 */                     ex.printStackTrace();
/*       */                   }
/*  5101 */                   String resType = rs.getString("RESOURCETYPE");
/*       */                   
/*       */ 
/*  5104 */                   if (props.getProperty(type) != null)
/*       */                   {
/*  5106 */                     data.add(props.getProperty(type));
/*       */                   }
/*       */                   else
/*       */                   {
/*  5110 */                     data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   }
/*  5112 */                   data.add(rs.getString(4));
/*  5113 */                   data.add(resType);
/*  5114 */                   row.add(data);
/*       */                 }
/*       */               }
/*       */             } }
/*  5118 */           AMConnectionPool.closeStatement(rs);
/*  5119 */           if (listMonitorsByOrder)
/*       */             break;
/*       */         }
/*       */       }
/*  5123 */       ArrayList a1 = new ArrayList();
/*  5124 */       a1.add(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/*  5125 */       a1.add("ALL");
/*  5126 */       a1.add("/images/icon_monitors_all.gif");
/*  5127 */       a1.add("--");
/*  5128 */       row.add(a1);
/*  5129 */       ArrayList a2 = new ArrayList();
/*  5130 */       a2.add(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/*  5131 */       a2.add("All");
/*  5132 */       a2.add("/images/icon_monitors_all.gif");
/*  5133 */       a2.add("--");
/*  5134 */       row.add(a2);
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  5139 */       ex.printStackTrace();
/*       */     }
/*  5141 */     return row;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getResourceTypeQueryforNewMonitor(String group)
/*       */   {
/*  5152 */     if (Constants.getNotSupported().length() > 0)
/*       */     {
/*  5154 */       types = Constants.resourceTypes + " OR AM_ManagedResourceType.RESOURCETYPE not in " + Constants.getNotSupported();
/*       */     }
/*       */     else
/*       */     {
/*  5158 */       types = Constants.resourceTypes;
/*       */     }
/*  5160 */     String nameForDisplay = "AM_ManagedResourceType.DISPLAYNAME";
/*  5161 */     String resourceGroupCondition = "AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and ";
/*  5162 */     if (group.equals("SYS"))
/*       */     {
/*  5164 */       nameForDisplay = "AM_ManagedResourceType.SUBGROUP";
/*       */     }
/*  5166 */     if (group.equals("ALL")) {
/*  5167 */       nameForDisplay = "(CASE WHEN AM_ManagedResourceType.RESOURCEGROUP='SYS' THEN AM_ManagedResourceType.SUBGROUP ELSE AM_ManagedResourceType.DISPLAYNAME END)";
/*  5168 */       resourceGroupCondition = "";
/*       */     }
/*  5170 */     String deprecatedFilteredTypes = types.replaceAll("WTA", "");
/*  5171 */     return "select AM_ManagedResourceType.SUBGROUP,MAX(COALESCE(" + nameForDisplay + ",AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) RESOURCETYPE,MAX(AM_ManagedResourceType.RESOURCEGROUP) RESOURCEGROUP from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE where " + resourceGroupCondition + " (AM_ManagedResourceType.RESOURCETYPE in " + deprecatedFilteredTypes + ")  group by  AM_ManagedResourceType.SUBGROUP,AM_ManagedResourceType.IMAGEPATH order by UPPER(MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)))";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ArrayList getMonitorTypes()
/*       */   {
/*  5178 */     ArrayList row = new ArrayList();
/*       */     try
/*       */     {
/*  5181 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */       
/*  5183 */       ResultSet rs = null;
/*  5184 */       String query = null;
/*       */       
/*  5186 */       String[] temp = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM" };
/*  5187 */       Properties p1 = new Properties();
/*  5188 */       p1.setProperty("APP", FormatUtil.getString("Application Servers"));
/*  5189 */       p1.setProperty("ERP", FormatUtil.getString("ERP"));
/*  5190 */       p1.setProperty("TM", FormatUtil.getString("am.monitortab.transactionmonitors.text"));
/*  5191 */       p1.setProperty("SYS", FormatUtil.getString("Systems"));
/*  5192 */       p1.setProperty("DBS", FormatUtil.getString("Database Servers"));
/*  5193 */       p1.setProperty("SER", FormatUtil.getString("Services"));
/*  5194 */       p1.setProperty("URL", FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices.title"));
/*  5195 */       p1.setProperty("MS", FormatUtil.getString("Mail Servers"));
/*  5196 */       p1.setProperty("MOM", FormatUtil.getString("Middleware/Portal"));
/*  5197 */       p1.setProperty("CAM", FormatUtil.getString("Custom Monitors"));
/*       */       
/*  5199 */       Properties props = Constants.getValueForNewMonitor();
/*  5200 */       for (int i = 0; i < temp.length; i++)
/*       */       {
/*  5202 */         ArrayList subdata = new ArrayList();
/*  5203 */         query = getResourceTypeQuery(temp[i], null);
/*  5204 */         rs = AMConnectionPool.executeQueryStmt(query);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5220 */         boolean firstTime = true;
/*       */         
/*  5222 */         while (rs.next())
/*       */         {
/*       */ 
/*  5225 */           if (firstTime)
/*       */           {
/*       */             try
/*       */             {
/*  5229 */               subdata.add(p1.getProperty(temp[i]));
/*  5230 */               subdata.add(temp[i]);
/*  5231 */               subdata.add("-");
/*  5232 */               row.add(subdata);
/*  5233 */               firstTime = false;
/*       */             }
/*       */             catch (Exception exc) {
/*  5236 */               System.out.println("Problem while checking out whether the type is allowed or not.. for the new monitor Caption.This should never occur..Mysql problem");
/*  5237 */               exc.printStackTrace();
/*       */             }
/*       */           }
/*  5240 */           ArrayList data = new ArrayList();
/*  5241 */           if (OEMUtil.isOEM())
/*       */           {
/*  5243 */             if (OEMUtil.isRemove("am.wmimonitors.remove")) {
/*  5244 */               if ("SYS".equals(temp[i])) {
/*  5245 */                 if (!"Unknown".equals(rs.getString(2))) {
/*  5246 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   
/*  5248 */                   data.add(rs.getString(2));
/*  5249 */                   data.add(rs.getString(4));
/*  5250 */                   row.add(data);
/*       */                 }
/*       */                 
/*       */               }
/*  5254 */               else if ("MS".equals(temp[i]))
/*       */               {
/*  5256 */                 if (!"Exchange Server".equals(rs.getString(2))) {
/*  5257 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   
/*  5259 */                   data.add(props.getProperty(rs.getString(1)));
/*  5260 */                   data.add(rs.getString(4));
/*  5261 */                   row.add(data);
/*       */                 }
/*       */                 
/*       */ 
/*       */               }
/*  5266 */               else if ("APP".equals(temp[i])) {
/*  5267 */                 if (!"Microsoft .NET".equals(rs.getString(2))) {
/*  5268 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   
/*  5270 */                   data.add(props.getProperty(rs.getString(1)));
/*  5271 */                   data.add(rs.getString(4));
/*  5272 */                   row.add(data);
/*       */                 }
/*       */                 
/*       */ 
/*       */               }
/*  5277 */               else if ("SER".equals(temp[i])) {
/*  5278 */                 if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  5279 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   
/*  5281 */                   data.add(props.getProperty(rs.getString(1)));
/*  5282 */                   data.add(rs.getString(4));
/*  5283 */                   row.add(data);
/*       */                 }
/*       */                 
/*       */ 
/*       */               }
/*  5288 */               else if ("CAM".equals(temp[i])) {
/*  5289 */                 if ((!"Windows Performance Counters".equals(rs.getString(2))) && (!"QEngine Script Monitor".equals(rs.getString(2)))) {
/*  5290 */                   data.add(FormatUtil.getString(rs.getString(2)));
/*       */                   
/*  5292 */                   data.add(props.getProperty(rs.getString(1)));
/*  5293 */                   data.add(rs.getString(4));
/*  5294 */                   row.add(data);
/*       */                 }
/*       */                 
/*       */ 
/*       */               }
/*       */               else
/*       */               {
/*       */ 
/*  5302 */                 data.add(FormatUtil.getString(rs.getString(2)));
/*       */                 
/*  5304 */                 data.add(props.getProperty(rs.getString(1)));
/*  5305 */                 data.add(rs.getString(4));
/*  5306 */                 row.add(data);
/*       */               }
/*       */             }
/*  5309 */             else if ("SYS".equals(temp[i])) {
/*  5310 */               if (!"Unknown".equals(rs.getString(2))) {
/*  5311 */                 data.add(FormatUtil.getString(rs.getString(2)));
/*       */                 
/*  5313 */                 data.add(rs.getString(2));
/*  5314 */                 data.add(rs.getString(4));
/*  5315 */                 row.add(data);
/*       */               }
/*       */               
/*       */             }
/*  5319 */             else if ("SER".equals(temp[i])) {
/*  5320 */               if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  5321 */                 data.add(FormatUtil.getString(rs.getString(2)));
/*  5322 */                 data.add(props.getProperty(rs.getString(1)));
/*  5323 */                 data.add(rs.getString(4));
/*  5324 */                 row.add(data);
/*       */               }
/*       */             }
/*       */             else
/*       */             {
/*  5329 */               data.add(FormatUtil.getString(rs.getString(2)));
/*  5330 */               if (props.getProperty(rs.getString(1)) != null)
/*       */               {
/*  5332 */                 data.add(props.getProperty(rs.getString(1)));
/*       */               }
/*       */               else
/*       */               {
/*  5336 */                 data.add(FormatUtil.getString(rs.getString(2)));
/*       */               }
/*  5338 */               data.add(rs.getString(4));
/*  5339 */               row.add(data);
/*       */             }
/*       */             
/*       */           }
/*  5343 */           else if ("SYS".equals(temp[i])) {
/*  5344 */             if (!"Unknown".equals(rs.getString(2))) {
/*  5345 */               data.add(FormatUtil.getString(rs.getString(2)));
/*       */               
/*  5347 */               data.add(rs.getString(2));
/*  5348 */               data.add(rs.getString(4));
/*  5349 */               row.add(data);
/*       */             }
/*       */             
/*       */           }
/*  5353 */           else if ("SER".equals(temp[i])) {
/*  5354 */             if (!"AdventNet JMX Agent".equals(rs.getString(2))) {
/*  5355 */               data.add(FormatUtil.getString(rs.getString(2)));
/*  5356 */               data.add(props.getProperty(rs.getString(1)));
/*  5357 */               data.add(rs.getString(4));
/*  5358 */               row.add(data);
/*       */             }
/*       */           }
/*       */           else
/*       */           {
/*  5363 */             data.add(FormatUtil.getString(rs.getString(2)));
/*  5364 */             String type = "";
/*       */             
/*       */             try
/*       */             {
/*  5368 */               if (rs.getString(1).equals("File System Monitor"))
/*       */               {
/*       */ 
/*  5371 */                 type = "File / Directory Monitor";
/*       */               }
/*       */               else
/*       */               {
/*  5375 */                 type = rs.getString(1);
/*       */               }
/*       */             }
/*       */             catch (Exception ex)
/*       */             {
/*  5380 */               ex.printStackTrace();
/*       */             }
/*  5382 */             if (props.getProperty(type) != null)
/*       */             {
/*  5384 */               data.add(props.getProperty(type));
/*       */             }
/*       */             else
/*       */             {
/*  5388 */               data.add(FormatUtil.getString(rs.getString(2)));
/*       */             }
/*  5390 */             data.add(rs.getString(4));
/*  5391 */             row.add(data);
/*       */           }
/*       */         }
/*       */         
/*  5395 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */       
/*  5398 */       ArrayList a1 = new ArrayList();
/*  5399 */       a1.add(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/*  5400 */       a1.add("ALL");
/*  5401 */       a1.add("/images/icon_monitors_all.gif");
/*  5402 */       row.add(a1);
/*  5403 */       ArrayList a2 = new ArrayList();
/*  5404 */       a2.add(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/*  5405 */       a2.add("All");
/*  5406 */       a2.add("/images/icon_monitors_all.gif");
/*  5407 */       row.add(a2);
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  5412 */       ex.printStackTrace();
/*       */     }
/*  5414 */     return row;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getResourceTypeQuery(String group, String network)
/*       */   {
/*  5422 */     types = Constants.resourceTypes;
/*       */     
/*  5424 */     String eumChildListCond = "AM_ManagedObject.RESOURCEID NOT IN (" + Constants.getEUMChildString() + ")";
/*  5425 */     if (network == null)
/*       */     {
/*  5427 */       String nameForDisplay = "DISPLAYNAME";
/*  5428 */       if (group.equals("SYS")) {
/*  5429 */         nameForDisplay = "SUBGROUP";
/*       */       }
/*  5431 */       return "select AM_ManagedResourceType.SUBGROUP, MAX(COALESCE(AM_ManagedResourceType." + nameForDisplay + ",AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE in " + types + "  group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5436 */     if ((group.equals("CAM")) || (group.equals("SCR")) || (group.equals("QA")) || (group.equals("MOM")))
/*       */     {
/*  5438 */       return "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE in " + types + "  group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5443 */     if (group.equals("SYS")) {
/*  5444 */       return "select AM_ManagedResourceType.SUBGROUP ,COALESCE(AM_ManagedResourceType.SUBGROUP,AM_ManagedResourceType.RESOURCETYPE),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME) ,AM_ManagedResourceType.RESOURCETYPE from IpAddress,AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " where AM_ManagedResourceType.RESOURCEGROUP='SYS'  and AM_ManagedResourceType.RESOURCETYPE in " + types + "  and AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNODE and IpAddress.PARENTNET='" + network + "' group by AM_ManagedResourceType.SUBGROUP,AM_ManagedResourceType.RESOURCETYPE,AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,AM_ManagedResourceType.DISPLAYNAME order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5449 */     String firstquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from IpAddress join InetService on InetService.INTERFACENAME=IpAddress.NAME join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=InetService.NAME and " + eumChildListCond + " right outer join AM_ManagedResourceType on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and  AM_ManagedResourceType.RESOURCETYPE in " + types + " and IpAddress.PARENTNET='" + network + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH";
/*       */     
/*       */ 
/*       */ 
/*  5453 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5454 */     ResultSet rs = null;
/*  5455 */     String sgroup = null;
/*  5456 */     ArrayList al = new ArrayList();
/*       */     try
/*       */     {
/*  5459 */       rs = AMConnectionPool.executeQueryStmt(firstquery);
/*  5460 */       while (rs.next())
/*       */       {
/*  5462 */         sgroup = rs.getString("SUBGROUP");
/*  5463 */         al.add(sgroup);
/*       */       }
/*       */       
/*  5466 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  5470 */       e.printStackTrace();
/*       */     }
/*       */     
/*  5473 */     StringBuffer buf = new StringBuffer();
/*  5474 */     String str = "";
/*  5475 */     if (al.size() != 0)
/*       */     {
/*  5477 */       for (int i = 0; i < al.size(); i++)
/*       */       {
/*  5479 */         String temp = (String)al.get(i);
/*  5480 */         buf.append(temp);
/*       */         
/*  5482 */         if (i != al.size() - 1)
/*       */         {
/*  5484 */           buf.append("'");
/*  5485 */           buf.append(",");
/*  5486 */           buf.append("'");
/*       */         }
/*       */       }
/*       */       
/*  5490 */       str = buf.toString();
/*       */     }
/*       */     
/*       */ 
/*  5494 */     String secondquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type) as resourcecount,COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE in " + types + "  group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH having count(type)=0 ";
/*       */     
/*       */ 
/*  5497 */     String thirdquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),0,COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from IpAddress join InetService on  InetService.INTERFACENAME=IpAddress.NAME join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=InetService.NAME right outer join AM_ManagedResourceType on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and AM_ManagedResourceType.RESOURCETYPE in " + types + " and IpAddress.PARENTNET != '" + network + "' and AM_ManagedResourceType.SUBGROUP NOT IN ('" + str + "') group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH";
/*       */     
/*       */ 
/*  5500 */     String fourthquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  and " + eumChildListCond + " where AM_ManagedResourceType.SUBGROUP in ('UrlSeq','UrlMonitor') group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH ";
/*  5501 */     if (group.equals("URL"))
/*       */     {
/*  5503 */       return firstquery + " union " + secondquery + " union " + thirdquery + " union " + fourthquery + " order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*  5507 */     return firstquery + " union " + secondquery + " union " + thirdquery + "  order by SUBGROUP";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getResourceTypeQueryForOwner(String group, String network, String owner)
/*       */   {
/*  5518 */     return getResourceTypeQueryForOwner(group, network, owner, null);
/*       */   }
/*       */   
/*       */   private String getResourceTypeQueryForOwner(String group, String network, String owner, HttpServletRequest request) {
/*  5522 */     Vector resourceids = null;
/*  5523 */     if (request != null)
/*       */     {
/*  5525 */       if (EnterpriseUtil.isIt360MSPEdition())
/*       */       {
/*  5527 */         resourceids = ClientDBUtil.getResourceIdentity(owner, request, null);
/*       */       }
/*       */       
/*       */     }
/*       */     else {
/*  5532 */       resourceids = ClientDBUtil.getResourceIdentity(owner);
/*       */     }
/*       */     
/*  5535 */     resourceids = Constants.filterEUMChild(resourceids);
/*       */     
/*       */ 
/*  5538 */     types = Constants.resourceTypes;
/*  5539 */     if (network == null)
/*       */     {
/*  5541 */       String nameForDisplay = "DISPLAYNAME";
/*  5542 */       if (group.equals("SYS"))
/*       */       {
/*  5544 */         nameForDisplay = "SUBGROUP";
/*       */       }
/*  5546 */       return "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType." + nameForDisplay + ",AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " and " + DependantMOUtil.getCondition("RESOURCEID", resourceids) + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*  5550 */     if ((group.equals("URL")) || (group.equals("CAM")) || (group.equals("SCR")) || (group.equals("QA")) || (group.equals("MOM")))
/*       */     {
/*       */ 
/*  5553 */       return "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " and " + DependantMOUtil.getCondition("RESOURCEID", resourceids) + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH order by SUBGROUP";
/*       */     }
/*  5555 */     if (group.equals("SYS")) {
/*  5556 */       return "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.SUBGROUP,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(COALESCE(SHORTNAME,AM_ManagedResourceType.DISPLAYNAME)),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE join IpAddress on AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNODE and AM_ManagedObject.Type in " + types + " and " + DependantMOUtil.getCondition("RESOURCEID", resourceids) + "where AM_ManagedResourceType.RESOURCEGROUP='SYS' and IpAddress.PARENTNET='" + network + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH order by SUBGROUP";
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5561 */     String firstquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type),COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from IpAddress join InetService on InetService.INTERFACENAME=IpAddress.NAME join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=InetService.NAME right outer join AM_ManagedResourceType on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and IpAddress.PARENTNET='" + network + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH";
/*       */     
/*       */ 
/*       */ 
/*  5565 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  5566 */     ResultSet rs = null;
/*  5567 */     String sgroup = null;
/*  5568 */     ArrayList al = new ArrayList();
/*       */     try
/*       */     {
/*  5571 */       rs = AMConnectionPool.executeQueryStmt(firstquery);
/*  5572 */       while (rs.next())
/*       */       {
/*  5574 */         sgroup = rs.getString("AM_ManagedResourceType.SUBGROUP");
/*  5575 */         al.add(sgroup);
/*       */       }
/*       */       
/*  5578 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  5582 */       e.printStackTrace();
/*       */     }
/*       */     
/*  5585 */     StringBuffer buf = new StringBuffer();
/*  5586 */     String str = "";
/*  5587 */     if (al.size() != 0)
/*       */     {
/*  5589 */       for (int i = 0; i < al.size(); i++)
/*       */       {
/*  5591 */         String temp = (String)al.get(i);
/*  5592 */         buf.append(temp);
/*       */         
/*  5594 */         if (i != al.size() - 1)
/*       */         {
/*  5596 */           buf.append("'");
/*  5597 */           buf.append(",");
/*  5598 */           buf.append("'");
/*       */         }
/*       */       }
/*       */       
/*  5602 */       str = buf.toString();
/*       */     }
/*       */     
/*       */ 
/*  5606 */     String secondquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),count(AM_ManagedObject.type) as resourcecount, COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from AM_ManagedResourceType left outer join AM_ManagedObject on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH having count(   AM_ManagedObject.type)=0 ";
/*       */     
/*       */ 
/*  5609 */     String thirdquery = "select AM_ManagedResourceType.SUBGROUP ,MAX(COALESCE(AM_ManagedResourceType.DISPLAYNAME,AM_ManagedResourceType.RESOURCETYPE)),0,COALESCE(IMAGEPATH,'/images/icon_monitors_urlmonitor.gif'),MAX(AM_ManagedResourceType.RESOURCETYPE) from IpAddress join InetService on InetService.INTERFACENAME=IpAddress.NAME join AM_ManagedObject on AM_ManagedObject.RESOURCENAME=InetService.NAME right outer join AM_ManagedResourceType on AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.Type in " + types + " where AM_ManagedResourceType.RESOURCEGROUP='" + group + "' and IpAddress.PARENTNET != '" + network + "' and AM_ManagedResourceType.SUBGROUP NOT IN ('" + str + "') group by AM_ManagedResourceType.SUBGROUP,IMAGEPATH";
/*       */     
/*  5611 */     return firstquery + " union " + secondquery + " union " + thirdquery + "  order by SUBGROUP";
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private static boolean getSslstatus(String resourceid)
/*       */   {
/*  5625 */     boolean sslenabled = false;
/*       */     try
/*       */     {
/*  5628 */       AMConnectionPool.getInstance();ResultSet rs1 = AMConnectionPool.executeQueryStmt("select sslenabled from AM_JBOSS_AUTHINFO where RESOURCEID=" + resourceid);
/*       */       try
/*       */       {
/*  5631 */         if (rs1.next())
/*       */         {
/*  5633 */           if (rs1.getString("sslenabled").equals("true"))
/*       */           {
/*  5635 */             sslenabled = true;
/*       */           }
/*       */         }
/*  5638 */         AMConnectionPool.closeStatement(rs1);
/*       */ 
/*       */       }
/*       */       catch (Exception exc) {}
/*       */ 
/*       */     }
/*       */     catch (Exception sqlexc)
/*       */     {
/*  5646 */       sqlexc.printStackTrace();
/*       */     }
/*  5648 */     return sslenabled;
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward associateScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  5655 */     String hostid = request.getParameter("hostid");
/*  5656 */     AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*  5657 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  5658 */     if (!isTokenValid(request))
/*       */     {
/*  5660 */       return mapping.findForward("/showresource.do?method=showResourceForResourceID&resourceid=" + hostid);
/*       */     }
/*       */     
/*       */ 
/*  5664 */     resetToken(request);
/*       */     
/*  5666 */     String[] resources = request.getParameterValues("selectedresource");
/*       */     
/*  5668 */     if ((resources != null) && (resources.length > 0))
/*       */     {
/*  5670 */       for (int i = 0; i < resources.length; i++)
/*       */       {
/*       */         try
/*       */         {
/*       */ 
/*  5675 */           DBUtil.insertParentChildMapper(Integer.parseInt(hostid), Integer.parseInt(resources[i]));
/*       */           
/*  5677 */           adder.addDependentAttributes(Integer.parseInt(hostid), Integer.parseInt(resources[i]));
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  5681 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*  5685 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + hostid, true);
/*       */   }
/*       */   
/*       */   public ActionForward removeScript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  5691 */     String hostid = request.getParameter("hostid");
/*  5692 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  5693 */     AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*  5694 */     if (!isTokenValid(request))
/*       */     {
/*  5696 */       return mapping.findForward("/showresource.do?method=showResourceForResourceID&resourceid=" + hostid);
/*       */     }
/*       */     
/*       */ 
/*  5700 */     resetToken(request);
/*       */     
/*  5702 */     String[] resources = request.getParameterValues("monitors");
/*  5703 */     if ((resources != null) && (resources.length > 0))
/*       */     {
/*  5705 */       for (int i = 0; i < resources.length; i++)
/*       */       {
/*       */         try
/*       */         {
/*  5709 */           String query = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + hostid + " and CHILDID=" + resources[i];
/*  5710 */           int count = AMConnectionPool.executeUpdateStmt(query);
/*  5711 */           adder.deleteDependantAttributes(Integer.parseInt(hostid), Integer.parseInt(resources[i]));
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  5715 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*  5719 */     return new ActionForward("/showresource.do?method=showResourceForResourceID&resourceid=" + hostid, true);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward addResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  5728 */     boolean goBackToMG = false;
/*  5729 */     if ((request.getParameter("goback") != null) && (request.getParameter("goback").equals("true")))
/*       */     {
/*  5731 */       AMLog.debug("MonitorGroup Association: Going back to MG details: " + request.getParameter("goback"));
/*  5732 */       goBackToMG = true;
/*       */     }
/*  5734 */     String haid = request.getParameter("haid");
/*  5735 */     if (!isTokenValid(request))
/*       */     {
/*  5737 */       return mapping.findForward("showapplication");
/*       */     }
/*       */     
/*       */ 
/*  5741 */     resetToken(request);
/*       */     
/*  5743 */     String applicationName = request.getParameter("applicationname");
/*  5744 */     applicationName = DBUtil.getDisplaynameforResourceID(haid);
/*  5745 */     String[] resources = request.getParameterValues("selectedresource");
/*       */     
/*  5747 */     if ((request.getParameter("MonitorGroup") != null) && (request.getParameter("MonitorGroup").equals("MonitorGroup")))
/*       */     {
/*  5749 */       resources = request.getParameterValues("selectedresourceMg");
/*       */     }
/*       */     
/*  5752 */     if ((resources != null) && (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true")))
/*       */     {
/*  5754 */       ArrayList<String> pk1 = new ArrayList();
/*  5755 */       for (int i = 0; i < resources.length; i++)
/*       */       {
/*  5757 */         pk1.add(resources[i]);
/*       */       }
/*  5759 */       ClientDBUtil.triggerDataChangeEvent("AM_ManagedObject", "update", pk1, null, null);
/*       */     }
/*       */     
/*  5762 */     String monitorname = request.getParameter("monitorname");
/*  5763 */     String resourcetype = request.getParameter("resourcetype");
/*  5764 */     String redirect = request.getParameter("savetype");
/*  5765 */     if (redirect == null) {
/*  5766 */       redirect = "1";
/*       */     }
/*  5768 */     HttpSession session = request.getSession(false);
/*       */     
/*  5770 */     String username = (String)session.getAttribute("userName");
/*  5771 */     String monitortype = request.getParameter("monitortype");
/*  5772 */     int result = 0;
/*  5773 */     if (resources != null)
/*       */     {
/*  5775 */       Vector forUpdate = new Vector();
/*  5776 */       result = this.mo.updateManagedApplicationResourcesForEnterprise(haid, "xyz", resources, forUpdate);
/*  5777 */       com.me.apm.eventlog.util.EventLogUtil.applyEventLogRules(haid, resources);
/*  5778 */       if (forUpdate != null)
/*       */       {
/*       */ 
/*  5781 */         for (int i = 0; i < forUpdate.size(); i++)
/*       */         {
/*  5783 */           EnterpriseUtil.addUpdateQueryToFile(forUpdate.get(i) + "");
/*       */         }
/*       */       }
/*       */       
/*  5787 */       String mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/*  5788 */       Vector<String> strArrayToVector = new Vector(Arrays.asList(resources));
/*       */       
/*  5790 */       String selDevices = strArrayToVector.toString();
/*  5791 */       selDevices = selDevices.substring(1, selDevices.length() - 1);
/*  5792 */       if (mapViewId != null)
/*       */       {
/*  5794 */         if ((selDevices != null) && (selDevices.length() != 0))
/*       */         {
/*  5796 */           selDevices = selDevices.substring(0, selDevices.length());
/*       */         }
/*  5798 */         String[] devicesToAdd = selDevices.split(",");
/*  5799 */         for (String deviceName : devicesToAdd)
/*       */         {
/*  5801 */           deviceName = deviceName.trim();
/*  5802 */           MapViewUtil.addDevice(mapViewId, deviceName);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5816 */     if ((monitortype == null) || (!monitortype.equals("network")))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5822 */       ActionMessages messages = new ActionMessages();
/*  5823 */       ActionErrors errors = new ActionErrors();
/*       */       
/*  5825 */       String[] ejbs = request.getParameterValues("ejb");
/*  5826 */       String[] wars = request.getParameterValues("war");
/*       */       
/*  5828 */       String resourceid = request.getParameter("resourceid");
/*  5829 */       this.mo.deleteMappingForParent(resourceid);
/*       */       
/*  5831 */       result = this.mo.updateManagedApplicationResources(resourceid, "xyz", ejbs);
/*       */       
/*  5833 */       if (result == 1)
/*       */       {
/*  5835 */         result = this.mo.updateManagedApplicationResources(resourceid, "jhg", wars);
/*       */       }
/*       */       
/*  5838 */       if (result == 1)
/*       */       {
/*  5840 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("j2ee.applications.configure.success"));
/*       */       }
/*       */       else
/*       */       {
/*  5844 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("j2ee.applications.configure.failed"));
/*       */       }
/*  5846 */       saveMessages(request, messages);
/*  5847 */       return new ActionForward("/showresource.do?method=showdetails");
/*       */     }
/*  5849 */     String query = "select type from AM_HOLISTICAPPLICATION where HAID=" + haid;
/*  5850 */     int type = 0;
/*       */     try {
/*  5852 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  5853 */       while (rs.next())
/*       */       {
/*  5855 */         type = rs.getInt(1);
/*       */       }
/*  5857 */       AMConnectionPool.closeStatement(rs);
/*       */     } catch (Exception e) {}
/*  5859 */     String message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.associatemonitor.message");
/*       */     
/*  5861 */     if (this.notifyConsole.shouldNotify())
/*       */     {
/*       */       try
/*       */       {
/*  5865 */         if (haid != null)
/*       */         {
/*  5867 */           resources = ExtProdUtil.getSelectedExtDevNames(resources, "OpManager");
/*  5868 */           Hashtable toNotifier = new Hashtable();
/*  5869 */           toNotifier.put("MGID", this.mo.getTOPLevelMG(haid));
/*  5870 */           toNotifier.put("subGrpMGID", haid);
/*       */           
/*  5872 */           boolean isSubGroup = false;
/*  5873 */           isSubGroup = Constants.isSubGroup(haid);
/*  5874 */           toNotifier.put("IS_SUBGROUP", Boolean.valueOf(isSubGroup));
/*  5875 */           toNotifier.put("DISPLAYNAME", applicationName);
/*  5876 */           toNotifier.put("MGNAME", applicationName);
/*  5877 */           if (isSubGroup)
/*       */           {
/*  5879 */             String parentGrpNames = com.adventnet.appmanager.util.BSIntegUtil.getTOPLevelIntegMGsNames("", haid);
/*  5880 */             parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/*  5881 */             toNotifier.put("MGNAME", applicationName + "-" + parentGrpNames);
/*       */           }
/*       */           
/*  5884 */           toNotifier.put("EventType", "Updated");
/*  5885 */           toNotifier.put("DEVICES_LIST", Arrays.toString(resources));
/*  5886 */           toNotifier.put("userName", request.getUserPrincipal().getName());
/*  5887 */           ExtProdUtil.doBVNotificationToOPM(toNotifier, request, haid);
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  5892 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*  5896 */     if ((result == 1) && (type == 0))
/*       */     {
/*  5898 */       message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.associatemonitor.message");
/*       */     }
/*  5900 */     else if ((result == 1) && (type == 1))
/*       */     {
/*  5902 */       message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.associatesubgroup.message");
/*       */     }
/*  5904 */     else if ((result == 0) && (type == 0))
/*       */     {
/*  5906 */       message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.associatemonitor.message.fail");
/*       */     }
/*       */     else
/*       */     {
/*  5910 */       message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.associatesubgroup.message.fail");
/*       */     }
/*  5912 */     String wiz = request.getParameter("wiz");
/*  5913 */     ActionMessages messages = new ActionMessages();
/*  5914 */     if (redirect.equals("2"))
/*       */     {
/*  5916 */       if (result == 1)
/*       */       {
/*  5918 */         String[] resourcesToAudit = request.getParameterValues("selectedresource");
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  5926 */         for (int i = 0; i < resourcesToAudit.length; i++) {
/*       */           try {
/*  5928 */             String monitorId = resourcesToAudit[i];
/*  5929 */             String auditLogMsg = FormatUtil.getString("am.audit.monitorassociate.msg", new String[] { DBUtil.getDisplaynameforResourceID(monitorId), applicationName });
/*  5930 */             cliAuditUtil.addToAuditLog(request, Integer.parseInt(haid), 5, auditLogMsg);
/*       */           }
/*       */           catch (Exception e) {
/*  5933 */             e.printStackTrace();
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  5941 */       if (wiz == null)
/*       */       {
/*       */ 
/*  5944 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message1));
/*  5945 */         saveMessages(request, messages);
/*  5946 */         if ((request.getParameter("MonitorGroup") != null) && (request.getParameter("MonitorGroup").equals("MonitorGroup")))
/*       */         {
/*  5948 */           return new ActionForward("/showresource.do?name=" + applicationName + "&haid=" + haid + "&type=" + resourcetype + "&method=getMonitorForm&showGroupOnly=true&message=" + message1 + "");
/*       */         }
/*  5950 */         if (goBackToMG)
/*       */         {
/*  5952 */           return new ActionForward("/showapplication.do?haid=" + haid + "&method=showApplication");
/*       */         }
/*  5954 */         return new ActionForward("/showresource.do?name=" + applicationName + "&haid=" + haid + "&type=" + resourcetype + "&typeSelectedFromCombo=true&method=getMonitorForm&message=" + message1 + "");
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  5960 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("createapplication.wizard.associate.success"));
/*  5961 */       saveMessages(request, messages);
/*  5962 */       return associateMonitors(mapping, form, request, response);
/*       */     }
/*  5964 */     if (redirect.equals("3"))
/*       */     {
/*  5966 */       message1 = FormatUtil.getString("am.webclient.monitorgroupsecond.removemonitor.message");
/*  5967 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(message1));
/*  5968 */       saveMessages(request, messages);
/*  5969 */       return new ActionForward("/showresource.do?name=" + applicationName + "&haid=" + haid + "&type=" + resourcetype + "&typeSelectedFromCombo=true&method=getMonitorForm");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  5975 */     if (wiz == null)
/*       */     {
/*  5977 */       return mapping.findForward("showapplication");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  5982 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("createapplication.wizard.associate.success"));
/*  5983 */     saveMessages(request, messages);
/*  5984 */     return mapping.findForward("HAProfiles");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward addResourceFromMapView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  5994 */     ArrayList<String> resourcesArray = (ArrayList)request.getAttribute("selectedresource");
/*  5995 */     int haid = ((Integer)request.getAttribute("bsgId")).intValue();
/*       */     
/*  5997 */     String[] resources = new String[resourcesArray.size()];
/*  5998 */     resources = (String[])resourcesArray.toArray(resources);
/*       */     
/*  6000 */     int result = 0;
/*  6001 */     if (resources != null)
/*       */     {
/*  6003 */       Vector forUpdate = new Vector();
/*  6004 */       result = this.mo.updateManagedApplicationResourcesForEnterprise(haid + "", "xyz", resources, forUpdate);
/*  6005 */       if (forUpdate != null)
/*       */       {
/*       */ 
/*  6008 */         for (int i = 0; i < forUpdate.size(); i++)
/*       */         {
/*  6010 */           EnterpriseUtil.addUpdateQueryToFile(forUpdate.get(i) + "");
/*       */         }
/*       */       }
/*       */     }
/*  6014 */     return new ActionForward("/showresource.do?method=showdetails");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showJBossDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6027 */     ActionErrors errors = new ActionErrors();
/*  6028 */     String haid = request.getParameter("haid");
/*  6029 */     String applicationName = request.getParameter("name");
/*  6030 */     String resourceid = request.getParameter("resourceid");
/*  6031 */     String resourcename = request.getParameter("resourcename");
/*  6032 */     String resourcetype = request.getParameter("type");
/*  6033 */     String poll = (String)request.getAttribute("reloadperiod");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  6038 */     AMDataCollectionHandler dcHandler = AMDataCollectionHandler.getInstance();
/*  6039 */     AMDataCollectionHandler handler = AMDataCollectionHandler.getInstance();
/*  6040 */     int dcstatus = dcHandler.getDataCollectionStatus(Integer.parseInt(resourceid));
/*  6041 */     request.setAttribute("showdata", dcstatus + "");
/*  6042 */     String dcmessage = dcHandler.isDataCollectionConfiguredForJBoss(Integer.parseInt(resourceid));
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6057 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*       */     
/*  6059 */     if (map != null)
/*       */     {
/*  6061 */       request.setAttribute("systeminfo", map);
/*       */     }
/*  6063 */     ArrayList rows = handler.getRows("SELECT AttributeName,Value FROM AM_JBOSS_DETAILS WHERE AM_JBOSS_DETAILS.RESOURCEID=" + resourceid);
/*  6064 */     Properties props = new Properties();
/*  6065 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  6067 */       ArrayList list = (ArrayList)rows.get(i);
/*  6068 */       props.setProperty((String)list.get(0), (String)list.get(1));
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6077 */     request.setAttribute("JBossProps", props);
/*       */     
/*       */ 
/*       */ 
/*  6081 */     ArrayList availability = handler.getRows("select resid,availability, count(*) from AM_ManagedObjectData where resid=" + resourceid + " group by resid,availability order by availability");
/*  6082 */     String s1 = "1";
/*  6083 */     String s2 = "0";
/*       */     
/*  6085 */     for (int i = 0; i < availability.size(); i++)
/*       */     {
/*  6087 */       ArrayList temp = (ArrayList)availability.get(i);
/*  6088 */       String wstemp = (String)temp.get(1);
/*  6089 */       if (wstemp.equals("1"))
/*       */       {
/*  6091 */         s1 = (String)temp.get(2);
/*       */       }
/*       */       else
/*       */       {
/*  6095 */         s2 = (String)temp.get(2);
/*       */       }
/*       */     }
/*  6098 */     double upcount = Integer.parseInt(s1);
/*  6099 */     double downcount = Integer.parseInt(s2);
/*       */     
/*  6101 */     double wfavailability = upcount / (upcount + downcount);
/*  6102 */     request.setAttribute("availabilitypercentage", truncate(String.valueOf(wfavailability * 100.0D)));
/*       */     
/*  6104 */     ArrayList currentavailability = handler.getRows("select availability, max(collectiontime) from AM_ManagedObjectData where resid=" + resourceid + " group by resid,availability order by availability");
/*       */     
/*  6106 */     String recentdowntime = "0";
/*  6107 */     String recentuptime = "0";
/*  6108 */     for (int i = 0; i < currentavailability.size(); i++)
/*       */     {
/*  6110 */       ArrayList temp = (ArrayList)currentavailability.get(i);
/*  6111 */       String s3 = (String)temp.get(0);
/*  6112 */       if (s3.equals("1"))
/*       */       {
/*  6114 */         recentuptime = (String)temp.get(1);
/*       */       }
/*       */       else
/*       */       {
/*  6118 */         recentdowntime = (String)temp.get(1);
/*       */       }
/*       */     }
/*  6121 */     String statusimage = "-1";
/*  6122 */     String recentDataCollectedTime = recentuptime;
/*  6123 */     if (Long.parseLong(recentuptime) >= Long.parseLong(recentdowntime))
/*       */     {
/*  6125 */       recentDataCollectedTime = recentuptime;
/*  6126 */       statusimage = "5";
/*       */     }
/*       */     else
/*       */     {
/*  6130 */       recentDataCollectedTime = recentdowntime;
/*  6131 */       statusimage = "1";
/*       */     }
/*  6133 */     request.setAttribute("statusimage", statusimage);
/*  6134 */     request.setAttribute("collectiontime", recentDataCollectedTime + "");
/*  6135 */     request.setAttribute("lastdatacollectedTime", new Date(Long.parseLong(recentDataCollectedTime)));
/*  6136 */     String jvmstate = "-1";
/*  6137 */     rows = this.mo.getPropertiesList("select SEVERITY,MMESSAGE from Alert where source=" + resourceid + " and category ='316'");
/*  6138 */     if (rows.size() > 0)
/*       */     {
/*  6140 */       Properties jvmprops = (Properties)rows.get(0);
/*  6141 */       jvmstate = jvmprops.get("SEVERITY").toString();
/*       */     }
/*  6143 */     request.setAttribute("jvmstate", jvmstate);
/*       */     
/*       */ 
/*       */ 
/*  6147 */     String availabilitystate = "-1";
/*  6148 */     rows = this.mo.getPropertiesList("select SEVERITY,MMESSAGE,ERRORMSG from Alert,AM_RESOURCECONFIG where source=" + resourceid + " and resourceid=source and category ='308'");
/*  6149 */     if (rows.size() > 0)
/*       */     {
/*       */ 
/*  6152 */       Properties jvmprops = (Properties)rows.get(0);
/*  6153 */       availabilitystate = jvmprops.get("SEVERITY").toString();
/*       */       
/*  6155 */       String errormsg = jvmprops.getProperty("ERRORMSG");
/*       */       
/*  6157 */       if ((availabilitystate != null) && (availabilitystate.equals("1")))
/*       */       {
/*  6159 */         String ip = (String)map.get("HOSTIP");
/*  6160 */         if (errormsg.startsWith("org.jboss.util.id.GUID"))
/*       */         {
/*  6162 */           errormsg = "Select proper JBoss version";
/*       */         }
/*  6164 */         String msgkey = (String)jbosserrorkeys.get(errormsg);
/*  6165 */         if (msgkey != null)
/*       */         {
/*  6167 */           if (!errormsg.equals("service not running"))
/*       */           {
/*       */ 
/*  6170 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage("jboss.datacollection.failed.1"));
/*       */           }
/*  6172 */           if (ip != null) {
/*  6173 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(msgkey, ip));
/*       */           }
/*       */         }
/*       */         else {
/*  6177 */           System.out.println("couldnot find value for Error " + msgkey);
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*  6182 */       else if ((availabilitystate != null) && (availabilitystate.equals("5")))
/*       */       {
/*  6184 */         if (errormsg.indexOf("Server redirected") != -1)
/*       */         {
/*  6186 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage("JBoss.access.redirected.withoutlink"));
/*       */         }
/*  6188 */         if ((errormsg.indexOf("jboss.auth.failed") != -1) || (errormsg.indexOf("JBoss Authentication Failure") != -1))
/*       */         {
/*  6190 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage("JBoss.access.restricted.withoutlink"));
/*       */         }
/*  6192 */         String msgkey = (String)jbosserrorkeys.get(errormsg);
/*  6193 */         if ((msgkey != null) && (errormsg.indexOf("jbossagent.sar not deployed") != -1)) {
/*  6194 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(msgkey));
/*       */         }
/*  6196 */         if (errormsg.indexOf("Server returned HTTP response code") != -1) {
/*  6197 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(errormsg));
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*       */ 
/*  6207 */       rows = this.mo.getPropertiesList("select DATABASENAME,ERRORMSG from AM_RESOURCECONFIG where resourceid=" + resourceid);
/*  6208 */       if (rows.size() > 0)
/*       */       {
/*  6210 */         Properties props1 = (Properties)rows.get(0);
/*  6211 */         String protocolversion = props1.getProperty("DATABASENAME");
/*  6212 */         String errormessage = props1.getProperty("ERRORMSG");
/*       */         
/*  6214 */         if ((errormessage.indexOf("jboss.auth.failed") != -1) || (errormessage.indexOf("JBoss Authentication Failure") != -1))
/*       */         {
/*  6216 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("JBoss.access.restricted.withoutlink")));
/*       */         }
/*  6218 */         if (!protocolversion.startsWith("JBOSS_HTTP"))
/*       */         {
/*       */ 
/*  6221 */           if (EnterpriseUtil.isAdminServer())
/*       */           {
/*  6223 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage("select.jboss.version.admin"));
/*       */           }
/*       */           else
/*       */           {
/*  6227 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage("select.jboss.version"));
/*       */           }
/*       */         }
/*  6230 */         else if ((errormessage.startsWith("org.jboss.util.id.GUID")) || (errormessage.startsWith("java.lang.NullPointerException")))
/*       */         {
/*  6232 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage("jboss.4.version.wrong"));
/*       */         }
/*       */         
/*  6235 */         if (protocolversion.equals("JBOSS_HTTP401"))
/*       */         {
/*  6237 */           if (!new File("./classes/jboss/401/jbossall-client.jar").exists())
/*       */           {
/*  6239 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage("jboss.401.jar.missing"));
/*  6240 */             String workingdir = null;
/*  6241 */             if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows")))
/*       */             {
/*  6243 */               workingdir = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*       */             }
/*       */             else
/*       */             {
/*  6247 */               workingdir = new File(NmsUtil.getAIM_ROOT()).getAbsoluteFile().getParentFile().getAbsolutePath();
/*       */             }
/*  6249 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage("jboss.401.jar.missing.1", workingdir + File.separator + "working" + File.separator + "classes" + File.separator + "jboss" + File.separator + "401"));
/*       */           }
/*       */         }
/*  6252 */         if (errormessage.indexOf("Server returned HTTP response code") != -1) {
/*  6253 */           errors.add("org.apache.struts.action.ERROR", new ActionMessage(errormessage));
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6263 */     String responsetime = null;
/*  6264 */     rows = this.mo.getPropertiesList("select RESPONSETIME from AM_ManagedObjectData where resID=" + resourceid + " and collectiontime=" + recentDataCollectedTime);
/*  6265 */     if (rows.size() > 0)
/*       */     {
/*  6267 */       Properties jvmprops = (Properties)rows.get(0);
/*  6268 */       responsetime = jvmprops.get("RESPONSETIME").toString();
/*       */     }
/*  6270 */     if (responsetime != null) {
/*  6271 */       request.setAttribute("responsetime", responsetime);
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  6276 */     String jvmdata = "0";
/*  6277 */     String temp = null;
/*  6278 */     String query2 = " select HEAPSIZECURRENT from AM_JVMData where ID=" + resourceid + " order by collectiontime desc";
/*  6279 */     query2 = DBQueryUtil.getTopNValues(query2, 1);
/*  6280 */     rows = this.mo.getPropertiesList(query2);
/*  6281 */     if (rows.size() > 0)
/*       */     {
/*  6283 */       Properties jvmprops = (Properties)rows.get(0);
/*  6284 */       temp = jvmprops.get("HEAPSIZECURRENT").toString();
/*       */     }
/*  6286 */     if (temp != null) {
/*  6287 */       request.setAttribute("heapsizecurrent", temp);
/*       */     }
/*       */     
/*       */ 
/*  6291 */     String path = "/jsp/JBossDetails.jsp?resourceID=" + resourceid + "&resourcetype=JBOSS-server&name=" + resourcename;
/*  6292 */     if (!errors.isEmpty())
/*       */     {
/*       */ 
/*  6295 */       saveErrors(request, errors);
/*       */     }
/*       */     
/*  6298 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showPortTestDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6310 */     this.mo.getReloadPeriod(request);
/*  6311 */     ActionMessages messages = new ActionMessages();
/*  6312 */     String haid = request.getParameter("haid");
/*  6313 */     String applicationName = request.getParameter("name");
/*  6314 */     String resourceid = request.getParameter("resourceid");
/*  6315 */     String resourcename = request.getParameter("resourcename");
/*  6316 */     String resourcetype = request.getParameter("type");
/*  6317 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*       */     
/*  6319 */     String infquery = "select  AM_PORTCONFIG.COMMAND , AM_PORTCONFIG.SEARCH , CollectData.APPLNDISCPORT  from AM_PORTCONFIG , AM_ManagedObject , CollectData where AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_PORTCONFIG.RESOURCEID=" + resourceid + " and AM_ManagedObject.RESOURCENAME=CollectData.RESOURCENAME";
/*       */     
/*  6321 */     ResultSet rs1 = AMConnectionPool.executeQueryStmt(infquery);
/*  6322 */     HashMap info = null;
/*  6323 */     if (rs1.next())
/*       */     {
/*  6325 */       info = new HashMap();
/*  6326 */       if (rs1.getString(1) != null) {
/*  6327 */         info.put("COMMAND", rs1.getString(1));
/*       */       } else
/*  6329 */         info.put("COMMAND", "-");
/*  6330 */       if (rs1.getString(2) != null) {
/*  6331 */         info.put("SEARCH", rs1.getString(2));
/*       */       } else
/*  6333 */         info.put("SEARCH", "-");
/*  6334 */       if (rs1.getString(3) != null) {
/*  6335 */         info.put("PORT", rs1.getString(3));
/*       */       } else {
/*  6337 */         info.put("PORT", "-");
/*       */       }
/*       */     }
/*       */     try {
/*  6341 */       AMConnectionPool.closeStatement(rs1);
/*       */     }
/*       */     catch (Exception exc) {}
/*       */     
/*       */ 
/*  6346 */     if (info != null) {
/*  6347 */       request.setAttribute("info", info);
/*       */     }
/*  6349 */     String poll = (String)request.getAttribute("reloadperiod");
/*  6350 */     if ((poll == null) || (poll.equalsIgnoreCase("null")))
/*       */     {
/*  6352 */       poll = "5";
/*       */     }
/*       */     
/*  6355 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*  6356 */     if (map != null)
/*       */     {
/*  6358 */       request.setAttribute("systeminfo", map);
/*       */     }
/*       */     
/*       */ 
/*  6362 */     String maxtime = "select max(COLLECTIONTIME) from  AM_ManagedObjectData where RESID=" + resourceid;
/*  6363 */     long curvalue = 0L;
/*  6364 */     int health = -1;
/*  6365 */     String error = null;
/*  6366 */     ResultSet rs = AMConnectionPool.executeQueryStmt(maxtime);
/*  6367 */     if (rs.next()) {
/*  6368 */       long time = rs.getLong(1);
/*  6369 */       AMConnectionPool.closeStatement(rs);
/*  6370 */       String dataquery = "select AM_RESOURCECONFIG.ERRORMSG, AM_ManagedObjectData.RESPONSETIME , AM_ManagedObjectData.HEALTH from AM_ManagedObjectData ,  AM_RESOURCECONFIG where AM_ManagedObjectData.RESID=" + resourceid + " and AM_ManagedObjectData.COLLECTIONTIME=" + time + " and AM_RESOURCECONFIG.RESOURCEID=AM_ManagedObjectData.RESID";
/*  6371 */       rs = AMConnectionPool.executeQueryStmt(dataquery);
/*  6372 */       if (rs.next()) {
/*  6373 */         curvalue = rs.getLong(2);
/*  6374 */         health = rs.getInt(3);
/*  6375 */         error = rs.getString(1);
/*  6376 */         if (error.equals("Configurations for Service Monitoring is changed. Please wait till the next polling interval."))
/*       */         {
/*  6378 */           error = FormatUtil.getString("am.webclient.servicemonitor.configchanged.text");
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  6384 */     request.setAttribute("responsetime", String.valueOf(curvalue));
/*  6385 */     request.setAttribute("health", new Integer(health));
/*  6386 */     if ((error != null) && (!error.equals("Data Collection Sucessful")))
/*       */     {
/*  6388 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", error));
/*  6389 */       saveMessages(request, messages);
/*       */     }
/*  6391 */     AMConnectionPool.closeStatement(rs);
/*       */     
/*  6393 */     String query = "select CATEGORY,SEVERITY from Alert where SOURCE='" + resourceid + "'";
/*  6394 */     rs = AMConnectionPool.executeQueryStmt(query);
/*  6395 */     while (rs.next()) {
/*  6396 */       request.setAttribute(rs.getString(1), new Integer(rs.getInt(2)));
/*       */     }
/*  6398 */     AMConnectionPool.closeStatement(rs);
/*       */     
/*       */ 
/*  6401 */     String path = "/jsp/PortDetails.jsp";
/*  6402 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showAvailabilityDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6414 */     String haid = request.getParameter("haid");
/*  6415 */     String applicationName = request.getParameter("name");
/*  6416 */     String resourceid = request.getParameter("resourceid");
/*  6417 */     String resourcename = request.getParameter("resourcename");
/*  6418 */     String resourcetype = request.getParameter("type");
/*  6419 */     ActionMessages messages = new ActionMessages();
/*  6420 */     String tempquery = "select * from AM_ManagedObjectData where resid=" + resourceid;
/*  6421 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  6422 */     ResultSet temprs = null;
/*  6423 */     String poll = (String)request.getAttribute("reloadperiod");
/*  6424 */     if (poll == null)
/*       */     {
/*  6426 */       poll = "300";
/*       */     }
/*  6428 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*  6429 */     if (map != null)
/*       */     {
/*  6431 */       map.put("TYPE", resourcetype);
/*  6432 */       request.setAttribute("systeminfo", map);
/*       */     }
/*  6434 */     if ((resourcetype.equals("IIS-server")) || (resourcetype.equals("PHP")))
/*       */     {
/*  6436 */       boolean sslenabled_val = false;
/*  6437 */       sslenabled_val = getSslstatus(resourceid);
/*  6438 */       request.setAttribute("sslenabled_val", new Boolean(sslenabled_val));
/*  6439 */       if (sslenabled_val)
/*       */       {
/*  6441 */         ((AMActionForm)form).setSslenabled(true);
/*       */       }
/*       */     }
/*  6444 */     if (resourcetype.equals("JMX1.2-MX4J-RMI"))
/*       */     {
/*  6446 */       ResultSet rs = null;
/*       */       try
/*       */       {
/*  6449 */         AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt("select ERRORMSG from AM_RESOURCECONFIG where resourceid=" + resourceid);
/*  6450 */         String temp = "";
/*  6451 */         if (rs.next())
/*       */         {
/*  6453 */           temp = rs.getString(1);
/*       */         }
/*  6455 */         if ((temp != null) && (temp.equals("Authentication Failed")))
/*       */         {
/*  6457 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.jmx.authentication.failure.text"));
/*  6458 */           saveMessages(request, messages);
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       catch (Exception exc) {}finally
/*       */       {
/*  6465 */         if (rs != null)
/*       */         {
/*  6467 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */       }
/*       */     }
/*  6471 */     if (resourcetype.equals("MAIL-server"))
/*       */     {
/*  6473 */       ResultSet rs = null;
/*       */       try
/*       */       {
/*  6476 */         AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt("select ERRORMSG from AM_RESOURCECONFIG where resourceid=" + resourceid);
/*  6477 */         String temp = "";
/*  6478 */         if (rs.next())
/*       */         {
/*  6480 */           temp = rs.getString(1);
/*       */         }
/*  6482 */         if ((temp != null) && ((temp.equals(FormatUtil.getString("am.webclient.mailserver.POPfailed"))) || (temp.equals("SMTP Authentication failed"))))
/*       */         {
/*  6484 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(temp));
/*  6485 */           saveMessages(request, messages);
/*       */ 
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       catch (Exception exc) {}finally
/*       */       {
/*       */ 
/*  6494 */         if (rs != null)
/*       */         {
/*  6496 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*  6501 */     if (resourcetype.equals("IIS-server"))
/*       */     {
/*  6503 */       resourcetype = "IIS Server";
/*  6504 */       request.setAttribute("DotNetAgentAvailable", "false");
/*  6505 */       ResultSet rs = null;
/*       */       try
/*       */       {
/*  6508 */         String targetName = null;
/*  6509 */         String query = "SELECT TARGETNAME FROM AM_ManagedObject,InetService WHERE AM_ManagedObject.RESOURCENAME=InetService.NAME AND TYPE='IIS-server' AND AM_ManagedObject.RESOURCEID='" + resourceid + "'";
/*  6510 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  6511 */         if (rs.next())
/*       */         {
/*  6513 */           targetName = rs.getString(1).toLowerCase();
/*       */         }
/*  6515 */         if (rs != null)
/*       */         {
/*  6517 */           rs.close();
/*       */         }
/*  6519 */         AMLog.debug("TARGET NAME IN DotNetAction is : " + targetName);
/*  6520 */         query = "SELECT * FROM apm_instances WHERE TYPE='DOTNET'";
/*  6521 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  6522 */         while (rs.next())
/*       */         {
/*  6524 */           String insightResourceID = rs.getString("RESOURCEID");
/*  6525 */           String insightApplicationID = rs.getString("APPLICATIONID");
/*  6526 */           String host = rs.getString("HOST").toLowerCase();
/*  6527 */           AMLog.debug("insightResourceID :" + insightResourceID + "\t insightApplicationID:" + insightApplicationID + "\thost:" + host);
/*  6528 */           if (targetName.indexOf(host) != -1)
/*       */           {
/*  6530 */             request.setAttribute("DotNetAgentAvailable", "true");
/*  6531 */             request.setAttribute("insightResourceID", insightResourceID);
/*  6532 */             request.setAttribute("insightApplicationID", insightApplicationID);
/*  6533 */             break;
/*       */           }
/*       */         }
/*  6536 */         if (rs != null)
/*       */         {
/*  6538 */           rs.close();
/*       */         }
/*       */         
/*  6541 */         if ((request.getParameter("websiteid") == null) && (request.getParameter("websitename") == null))
/*       */         {
/*  6543 */           long lastdc = map.get("LASTDC") != null ? ((Long)map.get("LASTDC")).longValue() : 0L;
/*  6544 */           ArrayList<Properties> poolList = new ArrayList();
/*  6545 */           query = "SELECT AM_PARENTCHILDMAPPER.CHILDID, AM_ManagedObject.DISPLAYNAME,AM_IIS_APPLICATIONPOOL_DATA.CPUUSAGE, AM_IIS_APPLICATIONPOOL_DATA.MEMORYUSAGE, AM_IIS_APPLICATIONPOOL_DATA.WORKERPROCESSCOUNT, AM_ManagedObject.DCSTARTED FROM AM_ManagedObject join AM_PARENTCHILDMAPPER  on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_IIS_APPLICATIONPOOL_DATA on AM_IIS_APPLICATIONPOOL_DATA.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_IIS_APPLICATIONPOOL_DATA.COLLECTIONTIME=" + lastdc + " where AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID='" + resourceid + "' AND AM_ManagedObject.TYPE='IIS-ApplicationPool'";
/*  6546 */           AMLog.debug("QUERY FOR IIS APPLICATION POOL DATA:" + query);
/*  6547 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  6548 */           while (rs.next())
/*       */           {
/*  6550 */             Properties prop = new Properties();
/*  6551 */             prop.setProperty("CHILDID", rs.getString(1));
/*  6552 */             prop.setProperty("DISPLAYNAME", rs.getString(2));
/*  6553 */             prop.setProperty("CPUUSAGE", rs.getFloat(3) + "");
/*  6554 */             prop.setProperty("MEMORYUSAGE", rs.getFloat(4) + "");
/*  6555 */             prop.setProperty("PROCESSCOUNT", rs.getInt(5) + "");
/*  6556 */             prop.setProperty("DCSTARTED", rs.getString(6));
/*  6557 */             poolList.add(prop);
/*       */           }
/*  6559 */           AMLog.debug("APPLICATION POOL LIST :" + poolList);
/*  6560 */           request.setAttribute("ApplicationPoolList", poolList);
/*       */         }
/*       */       }
/*       */       catch (Exception ex)
/*       */       {
/*  6565 */         ex.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/*  6569 */         if (rs != null)
/*       */         {
/*  6571 */           rs.close();
/*       */         }
/*       */       }
/*       */       
/*       */       try
/*       */       {
/*  6577 */         temprs = AMConnectionPool.executeQueryStmt(tempquery);
/*  6578 */         if (temprs.next())
/*       */         {
/*       */ 
/*  6581 */           ArrayList rows = new ManagedApplication().getRows("select displayname from AM_ManagedObject where resourceid=" + resourceid);
/*  6582 */           String displayname = "";
/*  6583 */           if (rows.size() > 0)
/*       */           {
/*  6585 */             rows = (ArrayList)rows.get(0);
/*  6586 */             displayname = (String)rows.get(0);
/*       */           }
/*  6588 */           ((AMActionForm)form).setDisplayname(displayname);
/*  6589 */           String resName = "";
/*  6590 */           String polling = "";
/*  6591 */           int temppollInterval1 = 0;
/*  6592 */           String errorMSG = "";
/*  6593 */           String pollQuery = "select AM_ManagedObject.RESOURCENAME,AM_RESOURCECONFIG.ERRORMSG from AM_ManagedObject left outer join AM_RESOURCECONFIG on  AM_RESOURCECONFIG.RESOURCEID=AM_ManagedObject.resourceid where  AM_ManagedObject.resourceid=" + resourceid;
/*  6594 */           ResultSet IISpollInterval = AMConnectionPool.executeQueryStmt(pollQuery);
/*  6595 */           if (IISpollInterval.next())
/*       */           {
/*  6597 */             resName = IISpollInterval.getString(1);
/*  6598 */             errorMSG = IISpollInterval.getString(2);
/*       */           }
/*  6600 */           if ((errorMSG != null) && (!errorMSG.equalsIgnoreCase("null")) && (!errorMSG.trim().equals("")))
/*       */           {
/*  6602 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(errorMSG));
/*  6603 */             saveMessages(request, messages);
/*       */           }
/*  6605 */           AMConnectionPool.closeStatement(IISpollInterval);
/*  6606 */           String pollQuery1 = "select POLLINTERVAL from CollectData where RESOURCENAME='" + resName + "'";
/*  6607 */           ResultSet IISpollInterval1 = AMConnectionPool.executeQueryStmt(pollQuery1);
/*  6608 */           String temppollInterval; if (IISpollInterval1.next())
/*       */           {
/*  6610 */             temppollInterval = IISpollInterval1.getString(1);
/*  6611 */             System.out.println(temppollInterval);
/*       */             try
/*       */             {
/*  6614 */               temppollInterval1 = Integer.parseInt(temppollInterval) / 60;
/*       */             }
/*       */             catch (Exception e) {}
/*       */             
/*       */ 
/*       */ 
/*  6620 */             polling = Integer.toString(temppollInterval1);
/*       */           }
/*  6622 */           AMConnectionPool.closeStatement(IISpollInterval1);
/*  6623 */           request.setAttribute("polling", polling);
/*  6624 */           if ((request.getParameter("websiteid") != null) && (request.getParameter("websitename") != null))
/*       */           {
/*  6626 */             request.setAttribute("websiteid", request.getParameter("websiteid"));
/*  6627 */             request.setAttribute("websitename", request.getParameter("websitename"));
/*  6628 */             return new ActionForward("/jsp/IISWebsiteStats.jsp");
/*       */           }
/*  6630 */           if ((request.getParameter("websiteidtoDelete") != null) && (request.getParameter("websiteidtoDelete") != null))
/*       */           {
/*       */             try
/*       */             {
/*  6634 */               Vector tempVector = new Vector();
/*  6635 */               String websiteToDelete = request.getParameter("websiteidtoDelete");
/*  6636 */               tempVector.add(websiteToDelete);
/*  6637 */               DataCollectionControllerUtil.deleteMO(resourceid, tempVector, true);
/*  6638 */               AMConnectionPool.executeUpdateStmt("delete from AM_PARENTCHILDMAPPER  where  CHILDID=" + websiteToDelete);
/*  6639 */               AMConnectionPool.executeUpdateStmt("delete from AM_IIS_TRAFFIC_DATA where resourceid=" + websiteToDelete);
/*  6640 */               AMConnectionPool.executeUpdateStmt("delete from AM_IIS_USERS_DATA where resourceid=" + websiteToDelete);
/*  6641 */               EnterpriseUtil.handleDeletionInAAM(new String[] { websiteToDelete });
/*  6642 */               EnterpriseUtil.addUpdateQueryToFile("delete from AM_PARENTCHILDMAPPER  where  CHILDID=" + websiteToDelete);
/*  6643 */               EnterpriseUtil.addUpdateQueryToFile("delete from AM_IIS_TRAFFIC_DATA where resourceid=" + websiteToDelete);
/*  6644 */               EnterpriseUtil.addUpdateQueryToFile("delete from AM_IIS_USERS_DATA where resourceid=" + websiteToDelete);
/*       */             } catch (Exception ex2) {
/*  6646 */               ex2.printStackTrace();
/*       */             }
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  6652 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.weblogic.datacollectioninprogress.text"));
/*       */         }
/*       */         
/*  6655 */         saveMessages(request, messages);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*  6665 */           AMConnectionPool.closeStatement(temprs);
/*       */         }
/*       */         catch (Exception exc2) {}
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6673 */         if (!resourcetype.equals("PHP")) {
/*       */           break label2549;
/*       */         }
/*       */       }
/*       */       catch (SQLException sqlexc)
/*       */       {
/*  6659 */         sqlexc.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/*       */         try
/*       */         {
/*  6665 */           AMConnectionPool.closeStatement(temprs);
/*       */         }
/*       */         catch (Exception exc2) {}
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6675 */     resourcetype = "PHP Monitor";
/*  6676 */     HashMap hm = new HashMap();
/*       */     
/*       */ 
/*       */     try
/*       */     {
/*  6681 */       temprs = AMConnectionPool.executeQueryStmt(tempquery);
/*  6682 */       long collectiontime = System.currentTimeMillis();
/*  6683 */       if (temprs.next())
/*       */       {
/*       */ 
/*       */         try
/*       */         {
/*  6688 */           ArrayList rows = new ManagedApplication().getRows("select displayname from AM_ManagedObject where resourceid=" + resourceid);
/*  6689 */           String displayname = "";
/*  6690 */           if (rows.size() > 0)
/*       */           {
/*  6692 */             rows = (ArrayList)rows.get(0);
/*  6693 */             displayname = (String)rows.get(0);
/*       */           }
/*  6695 */           ((AMActionForm)form).setDisplayname(displayname);
/*       */           
/*  6697 */           String query = "select version,relation,os_name from AM_PHPPATH where resourceid=" + resourceid;
/*  6698 */           rows = new ManagedApplication().getRows(query);
/*  6699 */           if (rows.size() > 0)
/*       */           {
/*  6701 */             rows = (ArrayList)rows.get(0);
/*  6702 */             String version = (String)rows.get(0);
/*  6703 */             String relation = (String)rows.get(1);
/*  6704 */             String os_name = (String)rows.get(2);
/*  6705 */             hm.put("version", version);
/*  6706 */             hm.put("relation", relation);
/*  6707 */             hm.put("os_name", os_name);
/*       */           }
/*  6709 */           String query1 = "select max(collectiontime) from AM_ManagedObjectData where resid=" + resourceid;
/*  6710 */           ResultSet rs = AMConnectionPool.executeQueryStmt(query1);
/*       */           
/*  6712 */           if (rs.next())
/*       */           {
/*  6714 */             if (rs.getString(1) != null)
/*       */             {
/*  6716 */               collectiontime = rs.getLong(1);
/*       */             }
/*       */           }
/*       */           
/*  6720 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception exc1)
/*       */         {
/*  6724 */           exc1.printStackTrace();
/*       */         }
/*       */         
/*       */         try
/*       */         {
/*  6729 */           String query = "select PAGEFAULTS,SWAPS,USERTIME from AM_PHPDETAILS where resourceid=" + resourceid + " and collectiontime=" + collectiontime;
/*  6730 */           ResultSet rs1 = AMConnectionPool.executeQueryStmt(query);
/*  6731 */           if (rs1.next())
/*       */           {
/*  6733 */             hm.put("pagefaults", rs1.getString("PAGEFAULTS"));
/*  6734 */             hm.put("swaps", rs1.getString("SWAPS"));
/*  6735 */             hm.put("usertime", rs1.getString("USERTIME"));
/*       */           }
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  6740 */           exc.printStackTrace();
/*       */         }
/*  6742 */         hm.put("collectiontime", String.valueOf(collectiontime));
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  6747 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.weblogic.datacollectioninprogress.text"));
/*       */       }
/*       */       
/*  6750 */       saveMessages(request, messages);
/*       */     }
/*       */     catch (SQLException sqlexc)
/*       */     {
/*  6754 */       sqlexc.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*       */       try
/*       */       {
/*  6760 */         AMConnectionPool.closeStatement(temprs);
/*       */       }
/*       */       catch (Exception exc2) {}
/*       */       
/*       */ 
/*       */ 
/*  6766 */       request.setAttribute("phpdata", hm);
/*       */     }
/*       */     
/*       */     label2549:
/*  6770 */     if (resourcetype.equals("PHP Monitor"))
/*       */     {
/*       */ 
/*  6773 */       String apache_resourceid = null;
/*  6774 */       apache_resourceid = com.adventnet.appmanager.server.framework.datacollection.PhpDataCollector.getApacheID(resourcename);
/*       */       try
/*       */       {
/*  6777 */         if (apache_resourceid != null)
/*       */         {
/*  6779 */           ArrayList apache_php_al = servers_bytes(apache_resourceid);
/*  6780 */           if (!String.valueOf(apache_php_al.get(0)).equals("2"))
/*       */           {
/*  6782 */             request.setAttribute("apache_id", apache_resourceid);
/*  6783 */             request.setAttribute("apache_stats", apache_php_al);
/*       */           }
/*       */         }
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  6789 */         exc.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  6794 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*       */     
/*       */ 
/*  6797 */     String query = "select SEVERITY  from Alert,AM_ATTRIBUTES where Alert.CATEGORY=" + DBQueryUtil.castasVarchar("AM_ATTRIBUTES.ATTRIBUTEID") + " and TYPE=1 and SOURCE='" + resourceid + "'";
/*  6798 */     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  6799 */     Integer availability = new Integer(-1);
/*  6800 */     if (rs.next())
/*       */     {
/*       */ 
/*  6803 */       availability = new Integer(rs.getInt(1));
/*       */     }
/*       */     else
/*       */     {
/*  6807 */       availability = new Integer(-1);
/*       */     }
/*  6809 */     if (availability.intValue() == 1) {
/*  6810 */       ActionErrors errors = new ActionErrors();
/*  6811 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(FormatUtil.getString("am.fault.threshold.availability.critical.message", new String[] { resourcename, "" })));
/*  6812 */       saveErrors(request, errors);
/*       */     }
/*       */     
/*  6815 */     AMConnectionPool.closeStatement(rs);
/*  6816 */     request.setAttribute("availability", availability);
/*       */     
/*       */ 
/*       */ 
/*  6820 */     String path = "/jsp/AvailabilityPerformance.jsp";
/*  6821 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showUrlSequenceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  6834 */     String haid = request.getParameter("haid");
/*  6835 */     String applicationName = request.getParameter("name");
/*  6836 */     String resourceid = request.getParameter("resourceid");
/*  6837 */     String resourcename = request.getParameter("resourcename");
/*  6838 */     String resourcetype = request.getParameter("type");
/*  6839 */     String poll = (String)request.getAttribute("reloadperiod");
/*  6840 */     Properties props = new Properties();
/*  6841 */     ArrayList rows = this.mo.getRows("select * from AM_ManagedObject where resourceid = " + resourceid);
/*  6842 */     if (rows.size() > 0)
/*       */     {
/*  6844 */       rows = (ArrayList)rows.get(0);
/*  6845 */       props.setProperty("name", (String)rows.get(3));
/*  6846 */       props.setProperty("resourcename", (String)rows.get(1));
/*       */     }
/*       */     
/*  6849 */     String query = "select AM_URL.PollInterval from AM_ManagedObject,AM_URL,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.childid=AM_ManagedObject.resourceid and AM_ManagedObject.resourceid=AM_URL.urlid and AM_PARENTCHILDMAPPER.parentid= " + resourceid + " order by urlid";
/*  6850 */     query = DBQueryUtil.getTopNValues(query, 1);
/*  6851 */     rows = this.mo.getRows(query);
/*  6852 */     long pollinterval = 0L;
/*  6853 */     if (rows.size() > 0)
/*       */     {
/*  6855 */       rows = (ArrayList)rows.get(0);
/*  6856 */       pollinterval = Long.parseLong((String)rows.get(0));
/*  6857 */       props.setProperty("pollInterval", String.valueOf(pollinterval / 60000L));
/*  6858 */       request.setAttribute("reloadperiod", String.valueOf(pollinterval / 1000L));
/*       */     }
/*  6860 */     rows = this.mo.getRows("select max(AM_ManagedObjectData.COLLECTIONTIME) from AM_ManagedObjectData,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.childid=AM_ManagedObjectData.resid and AM_PARENTCHILDMAPPER.parentid= " + resourceid);
/*  6861 */     if (rows.size() > 0)
/*       */     {
/*  6863 */       rows = (ArrayList)rows.get(0);
/*  6864 */       String maxcolltime = (String)rows.get(0);
/*  6865 */       long collectiontime = System.currentTimeMillis();
/*  6866 */       if (maxcolltime != null)
/*       */       {
/*  6868 */         collectiontime = Long.parseLong(maxcolltime);
/*  6869 */         props.setProperty("collectiontime", FormatUtil.formatDT(maxcolltime));
/*       */       }
/*       */       
/*  6872 */       long nextcolltime = collectiontime + pollinterval;
/*  6873 */       props.setProperty("nextcollectiontime", FormatUtil.formatDT(String.valueOf(nextcolltime)));
/*       */     }
/*       */     
/*  6876 */     if (resourcetype.equalsIgnoreCase("rbm"))
/*       */     {
/*  6878 */       ArrayList agentRows = this.mo.getRows("select AGENTID,SCRIPT from AM_RBMDATA where resourceid = " + resourceid);
/*  6879 */       String agentId = "0";
/*  6880 */       if (agentRows.size() > 0)
/*       */       {
/*  6882 */         agentRows = (ArrayList)agentRows.get(0);
/*  6883 */         agentId = (String)agentRows.get(0);
/*  6884 */         props.setProperty("scriptname", (String)agentRows.get(1));
/*       */       }
/*       */       
/*  6887 */       ArrayList agentNameRows = this.mo.getRows("select DISPLAYNAME from AM_RBMAGENTDATA where AGENTID = " + agentId);
/*  6888 */       if (agentNameRows.size() > 0)
/*       */       {
/*  6890 */         agentNameRows = (ArrayList)agentNameRows.get(0);
/*  6891 */         props.setProperty("agentname", (String)agentNameRows.get(0));
/*       */       }
/*  6893 */       String agent = (String)agentNameRows.get(0);
/*  6894 */       ArrayList runningAgentRows = this.mo.getRows("select * from AM_RBMAGENTDATA where AGENTID = " + agentId + " and STATUS=0");
/*  6895 */       if (runningAgentRows.size() <= 0)
/*       */       {
/*       */ 
/*  6898 */         ActionErrors errors = new ActionErrors();
/*  6899 */         errors.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.rbm.errormessage.agentnotrun", new String[] { agent })));
/*  6900 */         String reasonforunavailability = FormatUtil.getString("am.webclient.rbm.errormessage.agentnotrun", new String[] { agent });
/*       */         
/*  6902 */         saveErrors(request, errors);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  6907 */     request.setAttribute("props", props);
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  6914 */     rows = this.mo.getRows("select resid,count(*) from AM_ManagedObjectData,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.childid=AM_ManagedObjectData.resid and AM_PARENTCHILDMAPPER.parentid=" + resourceid + " group by resid order by resid");
/*       */     
/*       */ 
/*       */ 
/*  6918 */     Hashtable stacktable = new Hashtable(rows.size());
/*  6919 */     boolean hasdata = false;
/*  6920 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  6922 */       ArrayList row = (ArrayList)rows.get(i);
/*       */       
/*  6924 */       String urlid = (String)row.get(0);
/*  6925 */       GetWLSGraph wlsGraph = new GetWLSGraph();
/*       */       
/*  6927 */       wlsGraph.setParam(urlid, "AVAILABILITY");
/*       */       
/*       */ 
/*  6930 */       DefaultPieDataset ds = (DefaultPieDataset)wlsGraph.produceDataset(null);
/*  6931 */       long totalcount = Integer.parseInt((String)row.get(1));
/*       */       
/*  6933 */       Number[] values = new Float[2];
/*  6934 */       if (totalcount != 0L)
/*       */       {
/*  6936 */         hasdata = true;
/*  6937 */         List keys = ds.getKeys();
/*  6938 */         for (int k = 0; k < keys.size(); k++)
/*       */         {
/*       */ 
/*  6941 */           String key = (String)keys.get(k);
/*       */           
/*       */ 
/*       */ 
/*  6945 */           if (key.startsWith("Downtime"))
/*       */           {
/*  6947 */             values[1] = new Float(ds.getValue(key).intValue());
/*       */           }
/*       */           else
/*       */           {
/*  6951 */             values[0] = new Float(ds.getValue(key).intValue());
/*       */           }
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  6957 */         values[0] = new Float(0.0F);
/*  6958 */         values[1] = new Float(0.0F);
/*       */       }
/*       */       
/*  6961 */       stacktable.put("Url" + (i + 1), values);
/*       */     }
/*  6963 */     if ((hasdata) && (stacktable.size() > 0))
/*       */     {
/*  6965 */       StackedBarSupport stackbar = new StackedBarSupport(new String[] { "Up", "Down" }, stacktable);
/*  6966 */       request.setAttribute("stackgraph", stackbar);
/*       */     }
/*       */     
/*  6969 */     if (hasdata)
/*       */     {
/*       */       try
/*       */       {
/*  6973 */         rows = this.mo.getRows("select avg(responsetime),max(collectiontime) from AM_ManagedObjectData where resid=" + resourceid + "");
/*  6974 */         String temp = null;
/*  6975 */         String temp1 = null;
/*  6976 */         String lastdatacollectedtime = null;
/*  6977 */         if (rows.size() > 0)
/*       */         {
/*  6979 */           rows = (ArrayList)rows.get(0);
/*  6980 */           temp = (String)rows.get(0);
/*  6981 */           if (temp == null)
/*       */           {
/*  6983 */             temp = null;
/*       */ 
/*       */           }
/*       */           else
/*       */           {
/*  6988 */             lastdatacollectedtime = (String)rows.get(1);
/*       */           }
/*       */         }
/*  6991 */         if (temp != null)
/*       */         {
/*  6993 */           long onehourbefore = 0L;
/*       */           try
/*       */           {
/*  6996 */             onehourbefore = Long.parseLong(lastdatacollectedtime) - 3600000L;
/*       */           }
/*       */           catch (NumberFormatException ne)
/*       */           {
/*  7000 */             ne.printStackTrace();
/*       */           }
/*  7002 */           rows = this.mo.getRows("select avg(responsetime),max(collectiontime) from AM_ManagedObjectData where RESID=" + resourceid + "  and collectiontime > " + onehourbefore);
/*  7003 */           if (rows.size() > 0)
/*       */           {
/*  7005 */             rows = (ArrayList)rows.get(0);
/*  7006 */             temp = (String)rows.get(0);
/*       */           }
/*  7008 */           if (temp != null) {
/*  7009 */             request.setAttribute("avgresponsetime", new Double(temp));
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  7016 */           ActionMessages messages = new ActionMessages();
/*  7017 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("weblogic.datacollection.notstarted"));
/*  7018 */           saveMessages(request, messages);
/*       */         }
/*       */         
/*  7021 */         if (lastdatacollectedtime != null) {
/*  7022 */           request.setAttribute("lastdatacollectedtime", new Date(Long.parseLong(lastdatacollectedtime)));
/*       */         }
/*  7024 */         rows = this.mo.getRows("select RESPONSETIME from AM_ManagedObjectData where RESID=" + resourceid + " and COLLECTIONTIME=" + lastdatacollectedtime + "  and RESPONSETIME<>-1");
/*       */         
/*  7026 */         if (rows.size() > 0)
/*       */         {
/*  7028 */           rows = (ArrayList)rows.get(0);
/*  7029 */           temp1 = (String)rows.get(0);
/*       */         }
/*       */         
/*  7032 */         if (temp1 != null) {
/*  7033 */           request.setAttribute("currentvalue", temp1);
/*       */         }
/*       */       }
/*       */       catch (Exception e) {
/*  7037 */         e.printStackTrace();
/*       */       }
/*       */       
/*       */       try
/*       */       {
/*  7042 */         rows = this.mo.getRows("select avg(DNSTIME),avg(CONNECTIONTIME),avg(FIRSTBYTETIME),avg(LASTBYTETIME),max(COLLECTIONTIME) from AM_URLData_EXT where URLID=" + resourceid + "");
/*  7043 */         String dns = null;String conn = null;String fbt = null;String lbt = null;
/*  7044 */         String lastdatacollectedtime = null;
/*  7045 */         if (rows.size() > 0)
/*       */         {
/*  7047 */           rows = (ArrayList)rows.get(0);
/*  7048 */           dns = (String)rows.get(0);
/*  7049 */           if (dns == null)
/*       */           {
/*  7051 */             dns = null;
/*       */           }
/*       */           else
/*       */           {
/*  7055 */             lastdatacollectedtime = (String)rows.get(4);
/*       */           }
/*       */         }
/*  7058 */         if (dns != null)
/*       */         {
/*  7060 */           long onehourbefore = 0L;
/*       */           try
/*       */           {
/*  7063 */             onehourbefore = Long.parseLong(lastdatacollectedtime) - 3600000L;
/*       */           }
/*       */           catch (NumberFormatException ne)
/*       */           {
/*  7067 */             ne.printStackTrace();
/*       */           }
/*  7069 */           rows = this.mo.getRows("select avg(DNSTIME),avg(CONNECTIONTIME),avg(FIRSTBYTETIME),avg(LASTBYTETIME),max(COLLECTIONTIME) from AM_URLData_EXT where URLID=" + resourceid + " and COLLECTIONTIME > " + onehourbefore);
/*  7070 */           if (rows.size() > 0)
/*       */           {
/*  7072 */             rows = (ArrayList)rows.get(0);
/*  7073 */             dns = (String)rows.get(0);
/*  7074 */             conn = (String)rows.get(1);
/*  7075 */             fbt = (String)rows.get(2);
/*  7076 */             lbt = String.valueOf(Double.parseDouble((String)rows.get(3)) - Double.parseDouble((String)rows.get(2)));
/*       */           }
/*  7078 */           if (dns != null)
/*       */           {
/*  7080 */             request.setAttribute("avgdnstime", new Double(dns));
/*  7081 */             request.setAttribute("avgconntime", new Double(conn));
/*  7082 */             request.setAttribute("avgfbt", new Double(fbt));
/*  7083 */             request.setAttribute("avglbt", new Double(lbt));
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  7088 */           ActionMessages messages = new ActionMessages();
/*  7089 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("weblogic.datacollection.notstarted"));
/*  7090 */           saveMessages(request, messages);
/*       */         }
/*       */         
/*  7093 */         if (lastdatacollectedtime != null)
/*       */         {
/*  7095 */           request.setAttribute("lastdatacollectedtime", new Date(Long.parseLong(lastdatacollectedtime)));
/*       */         }
/*  7097 */         rows = this.mo.getRows("select DNSTIME,CONNECTIONTIME,FIRSTBYTETIME,LASTBYTETIME from AM_URLData_EXT where URLID=" + resourceid + " and COLLECTIONTIME=" + lastdatacollectedtime);
/*  7098 */         if (rows.size() > 0)
/*       */         {
/*  7100 */           rows = (ArrayList)rows.get(0);
/*  7101 */           dns = (String)rows.get(0);
/*  7102 */           conn = (String)rows.get(1);
/*  7103 */           fbt = (String)rows.get(2);
/*  7104 */           lbt = String.valueOf(Integer.parseInt((String)rows.get(3)) - Integer.parseInt((String)rows.get(2)));
/*  7105 */           request.setAttribute("currentdnsvalue", dns);
/*  7106 */           request.setAttribute("currentconnvalue", conn);
/*  7107 */           request.setAttribute("currentfbtvalue", fbt);
/*  7108 */           request.setAttribute("currentltbvalue", lbt);
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/*  7113 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/*  7117 */     if (hasdata)
/*       */     {
/*       */ 
/*  7120 */       rows = this.mo.getRows("select severity from Alert where source=" + resourceid + " and category='404'");
/*  7121 */       if (rows.size() > 0)
/*       */       {
/*  7123 */         rows = (ArrayList)rows.get(0);
/*  7124 */         String severity = (String)rows.get(0);
/*       */         
/*       */ 
/*  7127 */         if ((severity != null) && (severity.equals("1")))
/*       */         {
/*       */ 
/*  7130 */           rows = this.mo.getRows("select URLID,severity,mmessage from Alert,AM_URLSequence where URLSEQID=" + resourceid + " and source=URLID and category='408' order by URLID");
/*  7131 */           String alertmessage = null;
/*  7132 */           int messagestatus = 0;
/*  7133 */           for (int i = 0; (rows != null) && (i < rows.size()); i++)
/*       */           {
/*  7135 */             ArrayList row = (ArrayList)rows.get(i);
/*  7136 */             String urlid = (String)row.get(0);
/*  7137 */             String urleleseverity = (String)row.get(1);
/*       */             
/*  7139 */             if (urleleseverity.equals("1"))
/*       */             {
/*  7141 */               messagestatus = 2;
/*  7142 */               if (alertmessage == null)
/*       */               {
/*  7144 */                 alertmessage = (String)row.get(2);
/*       */               }
/*       */               
/*  7147 */               row = this.mo.getRows("select reason,URL from AM_URLData,AM_URL where AM_URLData.urlid=AM_URL.urlid and AM_URLData.urlid=" + urlid + " order by collectiontime desc");
/*  7148 */               if (row.size() > 0)
/*       */               {
/*  7150 */                 row = (ArrayList)row.get(0);
/*  7151 */                 String reasonforunavailability = (String)row.get(0);
/*  7152 */                 String url = (String)row.get(1);
/*  7153 */                 if (reasonforunavailability != null)
/*       */                 {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7161 */                   if (reasonforunavailability.equalsIgnoreCase("Host Unavailable"))
/*       */                   {
/*  7163 */                     reasonforunavailability = FormatUtil.getString("am.webclient.urlmonitor.exception.unavailable.text");
/*       */ 
/*       */                   }
/*  7166 */                   else if (reasonforunavailability.equalsIgnoreCase("Connection Timed Out. Increase the time out level"))
/*       */                   {
/*  7168 */                     reasonforunavailability = FormatUtil.getString("am.webclient.urlmonitor.exception.timeout.text");
/*       */                   }
/*       */                   else
/*       */                   {
/*  7172 */                     reasonforunavailability = reasonforunavailability;
/*       */                   }
/*  7174 */                   ActionErrors errors = new ActionErrors();
/*  7175 */                   errors.add("org.apache.struts.action.ERROR", new ActionMessage(url + " " + FormatUtil.getString("am.webclient.urlmonitor.exception.availabilitystringnotaccesible.text")));
/*  7176 */                   errors.add("org.apache.struts.action.ERROR", new ActionMessage(reasonforunavailability));
/*  7177 */                   saveErrors(request, errors);
/*  7178 */                   messagestatus = 1;
/*  7179 */                   break;
/*       */                 }
/*       */               }
/*       */             }
/*       */           }
/*  7184 */           if ((messagestatus == 2) && (alertmessage != null))
/*       */           {
/*       */ 
/*  7187 */             ActionErrors errors = new ActionErrors();
/*  7188 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(alertmessage));
/*  7189 */             saveErrors(request, errors);
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  7196 */     return new ActionForward("/jsp/urlseqdetails.jsp?&seqid=" + resourceid);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward editUrlSequenceDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  7206 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7207 */     ResultSet rs = null;
/*  7208 */     String seqid = request.getParameter("seqid");
/*  7209 */     String displayname = request.getParameter("displayname");
/*  7210 */     String pollinterval = request.getParameter("pollInterval");
/*  7211 */     String moname = request.getParameter("moname");
/*  7212 */     String timeout = request.getParameter("timeout");
/*  7213 */     if (timeout == null)
/*       */     {
/*  7215 */       timeout = "0";
/*       */     }
/*       */     
/*       */     try
/*       */     {
/*  7220 */       long poll = Long.parseLong(pollinterval) * 60000L;
/*  7221 */       long time_out = Long.parseLong(timeout) * 60000L;
/*       */       
/*  7223 */       String responseidquery = "select RESOURCEID FROM AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + seqid + " AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by RESOURCEID";
/*  7224 */       rs = AMConnectionPool.executeQueryStmt(responseidquery);
/*  7225 */       String resid = null;
/*  7226 */       if (rs.next())
/*       */       {
/*  7228 */         resid = rs.getString("RESOURCEID");
/*       */       }
/*       */       
/*       */ 
/*  7232 */       if (moname.startsWith("RBM"))
/*       */       {
/*  7234 */         String timeoutquery = "update AM_RBMDATA SET TIMEOUT=" + timeout + ",Pollinterval=" + poll + " where RESOURCEID=" + resid + "";
/*  7235 */         AMConnectionPool.executeUpdateStmt(timeoutquery);
/*  7236 */         ArrayList rows = this.mo.getRows("select URLID from AM_URLSequence where URLSEQID=" + resid);
/*  7237 */         for (int i = 0; (rows != null) && (i < rows.size()); i++)
/*       */         {
/*  7239 */           ArrayList row = (ArrayList)rows.get(i);
/*  7240 */           String urlid = (String)row.get(0);
/*  7241 */           AMConnectionPool.executeUpdateStmt("update AM_URL SET Pollinterval=" + poll + " where URLID=" + urlid + "");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*  7246 */       String displaynamequery = "update AM_ManagedObject SET DISPLAYNAME='" + displayname + "' where RESOURCEID=" + seqid + "";
/*  7247 */       String pollquery = "update AM_URL SET Pollinterval=" + poll + " where URLID=" + resid + "";
/*  7248 */       AMConnectionPool.executeUpdateStmt(displaynamequery);
/*  7249 */       AMConnectionPool.executeUpdateStmt(pollquery);
/*       */       
/*  7251 */       EnterpriseUtil.addUpdateQueryToFile(displaynamequery);
/*  7252 */       showUrlSequenceDetails(mapping, form, request, response);
/*       */ 
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/*  7257 */       ex.printStackTrace();
/*       */     }
/*       */     
/*  7260 */     if (moname.startsWith("RBM"))
/*       */     {
/*  7262 */       return new ActionForward("/showresource.do?resourceid=" + seqid + "&type=RBM&moname=" + moname + "&method=showdetails&resourcename=" + displayname);
/*       */     }
/*       */     
/*       */ 
/*  7266 */     return new ActionForward("/showresource.do?resourceid=" + seqid + "&type=UrlSeq&moname=" + moname + "&method=showdetails&resourcename=" + displayname);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showUrlMonitorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  7281 */     String haid = request.getParameter("haid");
/*  7282 */     String applicationName = request.getParameter("name");
/*  7283 */     String resourceid = request.getParameter("resourceid");
/*  7284 */     String parentresourceid = request.getParameter("parentid");
/*  7285 */     String resourcename = request.getParameter("resourcename");
/*  7286 */     String resourcetype = request.getParameter("type");
/*  7287 */     String poll = (String)request.getAttribute("reloadperiod");
/*  7288 */     String err_msg = "";
/*       */     
/*  7290 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*       */     
/*       */ 
/*       */ 
/*  7294 */     ArrayList rows = this.mo.getRows("select displayname,AM_URL.* from AM_URL,AM_ManagedObject where urlid=resourceid and resourceid=" + resourceid);
/*  7295 */     UrlConfiguration urlmonitor = new UrlConfiguration();
/*  7296 */     long poll_interval = 15L;
/*  7297 */     if (rows.size() > 0)
/*       */     {
/*  7299 */       ArrayList row = (ArrayList)rows.get(0);
/*  7300 */       urlmonitor.setURLID(Integer.parseInt((String)row.get(1)));
/*  7301 */       urlmonitor.setURL((String)row.get(2));
/*  7302 */       urlmonitor.setUSERID((String)row.get(3));
/*  7303 */       urlmonitor.setPASSWORD((String)row.get(4));
/*  7304 */       urlmonitor.setQUERYSTRING((String)row.get(5));
/*  7305 */       urlmonitor.setMETHOD((String)row.get(6));
/*  7306 */       String availStr = (String)row.get(7);
/*  7307 */       if (availStr == null)
/*       */       {
/*  7309 */         availStr = "-";
/*       */       }
/*  7311 */       urlmonitor.setAVAILABILITYSTRING(availStr);
/*  7312 */       long wnPollInterval = Long.parseLong((String)row.get(8));
/*  7313 */       int wnpi = Integer.parseInt(String.valueOf(wnPollInterval / 60000L));
/*  7314 */       poll_interval = wnPollInterval;
/*  7315 */       urlmonitor.setPollInterval(wnpi);
/*  7316 */       urlmonitor.setCustomHeaders((String)row.get(11));
/*  7317 */       request.setAttribute("urldetail", urlmonitor);
/*  7318 */       request.setAttribute("urlmonitorname", (String)row.get(0));
/*       */     }
/*  7320 */     if ((resourcetype != null) && (resourcetype.equalsIgnoreCase("UrlMonitor")))
/*       */     {
/*  7322 */       long collectiontime = System.currentTimeMillis();
/*  7323 */       map.remove("LASTDC");
/*  7324 */       map.remove("NEXTDC");
/*  7325 */       rows = this.mo.getRows("select LAST_POLLED,ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID= " + resourceid);
/*  7326 */       if (rows.size() > 0)
/*       */       {
/*  7328 */         rows = (ArrayList)rows.get(0);
/*  7329 */         String lastpoll = (String)rows.get(0);
/*  7330 */         err_msg = (String)rows.get(1);
/*  7331 */         long lasttime = 0L;
/*  7332 */         if (lastpoll == null)
/*       */         {
/*  7334 */           lasttime = 0L;
/*       */         }
/*       */         else {
/*  7337 */           lasttime = Long.parseLong(lastpoll);
/*       */         }
/*  7339 */         if (lasttime == 0L)
/*       */         {
/*  7341 */           map.put("LASTDC", new Long(collectiontime));
/*  7342 */           map.put("NEXTDC", new Long(collectiontime + poll_interval));
/*       */ 
/*       */ 
/*       */         }
/*  7346 */         else if (lasttime > AMServerFramework.serverStartupTime)
/*       */         {
/*  7348 */           map.put("LASTDC", new Long(lasttime));
/*  7349 */           map.put("NEXTDC", new Long(lasttime + poll_interval));
/*       */         }
/*       */         else
/*       */         {
/*  7353 */           map.put("LASTDC", new Long(lasttime));
/*  7354 */           map.put("NEXTDC", new Long(AMServerFramework.serverStartupTime + poll_interval));
/*       */         }
/*       */         
/*       */       }
/*       */       else
/*       */       {
/*  7360 */         map.put("LASTDC", new Long(collectiontime));
/*  7361 */         map.put("NEXTDC", new Long(collectiontime + poll_interval));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*  7366 */     if ((resourcetype != null) && (resourcetype.equalsIgnoreCase("RBMURL")))
/*       */     {
/*  7368 */       rows = this.mo.getRows("select max(AM_ManagedObjectData.COLLECTIONTIME) from AM_ManagedObjectData,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.childid=AM_ManagedObjectData.resid and AM_PARENTCHILDMAPPER.parentid= " + parentresourceid);
/*  7369 */       long collectiontime = System.currentTimeMillis();
/*  7370 */       if (rows.size() > 0)
/*       */       {
/*  7372 */         rows = (ArrayList)rows.get(0);
/*  7373 */         String maxtime = (String)rows.get(0);
/*  7374 */         long maxcolltime = 0L;
/*  7375 */         if (maxtime == null)
/*       */         {
/*  7377 */           maxcolltime = 0L;
/*       */         }
/*       */         else {
/*  7380 */           maxcolltime = Long.parseLong(maxtime);
/*       */         }
/*  7382 */         if (maxcolltime == 0L)
/*       */         {
/*       */ 
/*       */ 
/*  7386 */           map.put("LASTDC", new Long(collectiontime));
/*  7387 */           map.put("NEXTDC", new Long(collectiontime + poll_interval));
/*       */ 
/*       */ 
/*       */         }
/*  7391 */         else if (maxcolltime > AMServerFramework.serverStartupTime)
/*       */         {
/*  7393 */           map.put("LASTDC", new Long(maxcolltime));
/*  7394 */           map.put("NEXTDC", new Long(maxcolltime + poll_interval));
/*       */         }
/*       */         else
/*       */         {
/*  7398 */           map.put("LASTDC", new Long(maxcolltime));
/*  7399 */           map.put("NEXTDC", new Long(AMServerFramework.serverStartupTime + poll_interval));
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  7406 */         map.put("LASTDC", new Long(collectiontime));
/*  7407 */         map.put("NEXTDC", new Long(collectiontime + poll_interval));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*  7413 */     if (map != null)
/*       */     {
/*  7415 */       request.setAttribute("systeminfo", map);
/*       */     }
/*       */     
/*  7418 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*       */     
/*  7420 */     String query = "select SEVERITY,MMESSAGE from Alert where SOURCE=" + resourceid + " and (category='400' OR category='408')";
/*  7421 */     ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  7422 */     Integer availability = new Integer(-1);
/*       */     
/*  7424 */     if (rs.next())
/*       */     {
/*       */ 
/*  7427 */       availability = new Integer(rs.getInt(1));
/*  7428 */       String message = rs.getString(2);
/*  7429 */       request.setAttribute("availabilitymessage", message);
/*  7430 */       if (availability.intValue() != 5)
/*       */       {
/*       */ 
/*  7433 */         ActionErrors messages = new ActionErrors();
/*  7434 */         query = "select REASON from AM_URLData where urlid=" + resourceid + " order by collectiontime desc";
/*  7435 */         query = DBQueryUtil.getTopNValues(query, 1);
/*  7436 */         ArrayList rows1 = this.mo.getRows(query);
/*  7437 */         if (rows1.size() > 0)
/*       */         {
/*  7439 */           rows1 = (ArrayList)rows1.get(0);
/*  7440 */           String httpmessage = (String)rows1.get(0);
/*       */           
/*  7442 */           if (httpmessage != null)
/*       */           {
/*  7444 */             if (httpmessage.equalsIgnoreCase("Host Unavailable"))
/*       */             {
/*  7446 */               httpmessage = FormatUtil.getString("am.webclient.urlmonitor.exception.unavailable.text");
/*       */ 
/*       */             }
/*  7449 */             else if (httpmessage.equalsIgnoreCase("Connection Timed Out. Increase the time out level"))
/*       */             {
/*  7451 */               httpmessage = FormatUtil.getString("am.webclient.urlmonitor.exception.timeout.text");
/*       */             }
/*       */             else
/*       */             {
/*  7455 */               httpmessage = httpmessage;
/*       */             }
/*  7457 */             messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", httpmessage));
/*       */           }
/*       */           else
/*       */           {
/*  7461 */             messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", message));
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  7466 */           message = (!"".equalsIgnoreCase(err_msg)) && (!"am.datacollection.success".equalsIgnoreCase(err_msg)) ? err_msg : message;
/*  7467 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage("appmanager.error", message));
/*       */         }
/*  7469 */         saveErrors(request, messages);
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*  7474 */       availability = new Integer(-1);
/*       */     }
/*  7476 */     AMConnectionPool.closeStatement(rs);
/*  7477 */     request.setAttribute("availability", availability);
/*       */     
/*  7479 */     rows = this.mo.getRows("select SEVERITY,MMESSAGE from Alert where SOURCE=" + resourceid + " and (category='402' OR category='409')");
/*  7480 */     String responsetimeseverity = "-1";
/*  7481 */     if (rows.size() > 0)
/*       */     {
/*  7483 */       rows = (ArrayList)rows.get(0);
/*  7484 */       responsetimeseverity = (String)rows.get(0);
/*  7485 */       String message = (String)rows.get(1);
/*  7486 */       request.setAttribute("healthmessage", message);
/*       */     }
/*  7488 */     request.setAttribute("responsetimesevertiy", responsetimeseverity);
/*       */     
/*  7490 */     rows = this.mo.getRows("select avg(responsetime),max(collectiontime) from AM_ManagedObjectData where resid=" + resourceid + "");
/*  7491 */     String temp = null;
/*  7492 */     String lastdatacollectedtime = null;
/*  7493 */     if (rows.size() > 0)
/*       */     {
/*  7495 */       rows = (ArrayList)rows.get(0);
/*  7496 */       temp = (String)rows.get(0);
/*  7497 */       if (temp == null)
/*       */       {
/*  7499 */         temp = null;
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  7504 */         lastdatacollectedtime = (String)rows.get(1);
/*       */       }
/*       */     }
/*  7507 */     if (temp != null)
/*       */     {
/*  7509 */       long onehourbefore = 0L;
/*       */       try
/*       */       {
/*  7512 */         onehourbefore = Long.parseLong(lastdatacollectedtime) - 3600000L;
/*       */       }
/*       */       catch (NumberFormatException ne)
/*       */       {
/*  7516 */         ne.printStackTrace();
/*       */       }
/*  7518 */       rows = this.mo.getRows("select avg(responsetime),max(collectiontime) from AM_ManagedObjectData where resid=" + resourceid + " and availability=1 and collectiontime > " + onehourbefore);
/*  7519 */       if (rows.size() > 0)
/*       */       {
/*  7521 */         rows = (ArrayList)rows.get(0);
/*  7522 */         temp = (String)rows.get(0);
/*       */       }
/*  7524 */       if (temp != null) {
/*  7525 */         request.setAttribute("avgresponsetime", new Double(temp));
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  7532 */       ActionMessages messages = new ActionMessages();
/*  7533 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("weblogic.datacollection.notstarted"));
/*  7534 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*  7537 */     if (lastdatacollectedtime != null) {
/*  7538 */       request.setAttribute("lastdatacollectedtime", new Date(Long.parseLong(lastdatacollectedtime)));
/*       */     }
/*  7540 */     rows = this.mo.getRows("select RESPONSETIME from AM_ManagedObjectData where RESID=" + resourceid + " and COLLECTIONTIME=" + lastdatacollectedtime);
/*  7541 */     if (rows.size() > 0)
/*       */     {
/*  7543 */       rows = (ArrayList)rows.get(0);
/*  7544 */       temp = (String)rows.get(0);
/*  7545 */       request.setAttribute("currentvalue", temp);
/*       */     }
/*       */     
/*  7548 */     rows = this.mo.getRows("select avg(DNSTIME),avg(CONNECTIONTIME),avg(FIRSTBYTETIME),avg(LASTBYTETIME),max(COLLECTIONTIME) from AM_URLData_EXT where URLID=" + resourceid + "");
/*  7549 */     String dns = null;String conn = null;String fbt = null;String lbt = null;
/*  7550 */     lastdatacollectedtime = null;
/*  7551 */     if (rows.size() > 0)
/*       */     {
/*  7553 */       rows = (ArrayList)rows.get(0);
/*  7554 */       dns = (String)rows.get(0);
/*  7555 */       if (dns == null)
/*       */       {
/*  7557 */         dns = null;
/*       */       }
/*       */       else
/*       */       {
/*  7561 */         lastdatacollectedtime = (String)rows.get(4);
/*       */       }
/*       */     }
/*  7564 */     if (dns != null)
/*       */     {
/*  7566 */       long onehourbefore = 0L;
/*       */       try
/*       */       {
/*  7569 */         onehourbefore = Long.parseLong(lastdatacollectedtime) - 3600000L;
/*       */       }
/*       */       catch (NumberFormatException ne)
/*       */       {
/*  7573 */         ne.printStackTrace();
/*       */       }
/*  7575 */       rows = this.mo.getRows("select avg(DNSTIME),avg(CONNECTIONTIME),avg(FIRSTBYTETIME),avg(LASTBYTETIME),max(COLLECTIONTIME) from AM_URLData_EXT where URLID=" + resourceid + " and COLLECTIONTIME > " + onehourbefore);
/*  7576 */       if (rows.size() > 0)
/*       */       {
/*  7578 */         rows = (ArrayList)rows.get(0);
/*  7579 */         dns = (String)rows.get(0);
/*  7580 */         conn = (String)rows.get(1);
/*  7581 */         fbt = (String)rows.get(2);
/*  7582 */         lbt = String.valueOf(Double.parseDouble((String)rows.get(3)) - Double.parseDouble((String)rows.get(2)));
/*       */       }
/*  7584 */       if (dns != null)
/*       */       {
/*  7586 */         request.setAttribute("avgdnstime", new Double(dns));
/*  7587 */         request.setAttribute("avgconntime", new Double(conn));
/*  7588 */         request.setAttribute("avgfbt", new Double(fbt));
/*  7589 */         request.setAttribute("avglbt", new Double(lbt));
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*  7594 */       ActionMessages messages = new ActionMessages();
/*  7595 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("weblogic.datacollection.notstarted"));
/*  7596 */       saveMessages(request, messages);
/*       */     }
/*       */     
/*  7599 */     if (lastdatacollectedtime != null)
/*       */     {
/*  7601 */       request.setAttribute("lastdatacollectedtime", new Date(Long.parseLong(lastdatacollectedtime)));
/*       */     }
/*  7603 */     rows = this.mo.getRows("select DNSTIME,CONNECTIONTIME,FIRSTBYTETIME,LASTBYTETIME from AM_URLData_EXT where URLID=" + resourceid + " and COLLECTIONTIME=" + lastdatacollectedtime);
/*  7604 */     if (rows.size() > 0)
/*       */     {
/*  7606 */       rows = (ArrayList)rows.get(0);
/*  7607 */       dns = (String)rows.get(0);
/*  7608 */       conn = (String)rows.get(1);
/*  7609 */       fbt = (String)rows.get(2);
/*  7610 */       lbt = String.valueOf(Integer.parseInt((String)rows.get(3)) - Integer.parseInt((String)rows.get(2)));
/*  7611 */       request.setAttribute("currentdnsvalue", dns);
/*  7612 */       request.setAttribute("currentconnvalue", conn);
/*  7613 */       request.setAttribute("currentfbtvalue", fbt);
/*  7614 */       request.setAttribute("currentltbvalue", lbt);
/*       */     }
/*       */     try
/*       */     {
/*  7618 */       long len1 = 0L;
/*  7619 */       long len2 = 0L;
/*  7620 */       long len3 = 0L;
/*  7621 */       String query1 = "select CONTENTLENGTH,COLLECTIONTIME from AM_URLData where URLID=" + resourceid + " ORDER BY COLLECTIONTIME DESC";
/*  7622 */       query1 = DBQueryUtil.getTopNValues(query1, 2);
/*  7623 */       rs = AMConnectionPool.executeQueryStmt(query1);
/*  7624 */       ArrayList a = new ArrayList();
/*  7625 */       while (rs.next())
/*       */       {
/*  7627 */         String length = rs.getString("CONTENTLENGTH");
/*  7628 */         a.add(length);
/*       */       }
/*       */       
/*       */ 
/*       */       try
/*       */       {
/*  7634 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */       catch (Exception exc) {}
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7642 */       if (a.size() == 0)
/*       */       {
/*  7644 */         request.setAttribute("length1", String.valueOf("-"));
/*  7645 */         request.setAttribute("length2", String.valueOf("-"));
/*  7646 */         request.setAttribute("length3", String.valueOf("-"));
/*       */ 
/*       */       }
/*  7649 */       else if (a.size() == 1)
/*       */       {
/*  7651 */         len1 = Long.parseLong((String)a.get(0));
/*       */         
/*  7653 */         request.setAttribute("length1", String.valueOf(len1));
/*  7654 */         request.setAttribute("length2", String.valueOf("-"));
/*  7655 */         request.setAttribute("length3", String.valueOf("-"));
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */ 
/*  7661 */         len1 = Long.parseLong((String)a.get(0));
/*  7662 */         len2 = Long.parseLong((String)a.get(1));
/*  7663 */         if (len2 == 0L)
/*       */         {
/*  7665 */           request.setAttribute("length3", String.valueOf("-"));
/*       */         }
/*       */         else
/*       */         {
/*  7669 */           len3 = (len1 - len2) * 100L / len2;
/*  7670 */           request.setAttribute("length3", String.valueOf(len3));
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7677 */         request.setAttribute("length1", String.valueOf(len1));
/*  7678 */         request.setAttribute("length2", String.valueOf(len2));
/*       */       }
/*       */       
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  7684 */       e.printStackTrace();
/*       */     }
/*       */     
/*  7687 */     if ((request.getParameter("hostcalls") != null) && (request.getParameter("hostcalls").equals("true"))) {
/*  7688 */       return null;
/*       */     }
/*  7690 */     String path = "/jsp/ShowUrlPerformance.jsp";
/*  7691 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showWeblogicDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  7704 */     String haid = request.getParameter("haid");
/*  7705 */     String applicationName = request.getParameter("name");
/*  7706 */     String resourceid = request.getParameter("resourceid");
/*  7707 */     String resourcename = request.getParameter("resourcename");
/*  7708 */     String resourcetype = request.getParameter("type");
/*  7709 */     String moname = request.getParameter("moname");
/*  7710 */     String poll = (String)request.getAttribute("reloadperiod");
/*  7711 */     int dctemp = 0;
/*       */     
/*  7713 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     
/*  7715 */     AMDataCollectionHandler dcHandler = AMDataCollectionHandler.getInstance();
/*  7716 */     int dcstatus = dcHandler.getDataCollectionStatus(Integer.parseInt(resourceid));
/*  7717 */     request.setAttribute("showdata", dcstatus + "");
/*  7718 */     String query = "select errormsg from AM_RESOURCECONFIG  where resourceid=" + resourceid;
/*       */     
/*  7720 */     ResultSet rs = null;
/*  7721 */     ActionMessages messages1 = new ActionMessages();
/*       */     
/*  7723 */     ActionErrors errors = new ActionErrors();
/*  7724 */     rs = AMConnectionPool.executeQueryStmt(query);
/*       */     
/*  7726 */     String errormsg = null;
/*       */     
/*  7728 */     if (rs.next())
/*       */     {
/*  7730 */       errormsg = rs.getString(1);
/*  7731 */       if (errormsg != null)
/*       */       {
/*       */ 
/*  7734 */         if (errormsg.indexOf("data collection accessdenied") != -1)
/*       */         {
/*       */ 
/*       */ 
/*  7738 */           String reason2 = FormatUtil.getString("weblogic.errormessage.permissiondenied2.", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*  7739 */           messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(reason2));
/*  7740 */           messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("weblogic.errormessage.help"));
/*  7741 */           dctemp = 1;
/*       */         }
/*  7743 */         else if ((errormsg.indexOf("MBeans Registration Problem") != -1) || (errormsg.indexOf("Agent not registered") != -1))
/*       */         {
/*  7745 */           String reason = FormatUtil.getString("weblogic.agent.notregistered", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*  7746 */           messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(reason));
/*  7747 */           dctemp = 1;
/*       */         }
/*  7749 */         else if (errormsg.indexOf("Anonymous user permission denied") != -1)
/*       */         {
/*  7751 */           String reason = FormatUtil.getString("weblogic.anonymous.restriction", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*       */           
/*  7753 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(reason));
/*  7754 */           saveErrors(request, errors);
/*  7755 */           dctemp = 1;
/*       */         }
/*  7757 */         else if (errormsg.indexOf("Access Restricted") != -1)
/*       */         {
/*  7759 */           String reason = FormatUtil.getString("weblogic.access.restricted", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*  7760 */           messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(reason));
/*  7761 */           dctemp = 1;
/*       */         }
/*  7763 */         else if (errormsg.indexOf("T3 protocol not enabled") != -1)
/*       */         {
/*  7765 */           String reason = FormatUtil.getString("am.webclient.weblogic.t3.unsupported", new String[] { "http://apm.manageengine.com/weblogic---t3---protocol.html" });
/*  7766 */           messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(reason));
/*  7767 */           dctemp = 1;
/*       */         }
/*  7769 */         else if (errormsg.indexOf("Connection Failure") != -1)
/*       */         {
/*  7771 */           String reason = FormatUtil.getString("am.weblogic.connection.failed");
/*  7772 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(reason));
/*  7773 */           saveErrors(request, errors);
/*  7774 */           dctemp = 1;
/*       */         }
/*       */       }
/*       */     }
/*  7778 */     AMConnectionPool.closeStatement(rs);
/*  7779 */     String MonitorerrorsMessage = "select ERROR_MESSAGE from AM_MONITOR_ERRORS where resourceid=" + resourceid;
/*  7780 */     ResultSet rs3 = AMConnectionPool.executeQueryStmt(MonitorerrorsMessage);
/*  7781 */     String jarmsg = null;
/*       */     
/*  7783 */     if (rs3.next())
/*       */     {
/*  7785 */       jarmsg = rs3.getString(1);
/*       */       
/*  7787 */       if ((jarmsg != null) && (jarmsg.indexOf("am.datacollection.success") == -1) && (!jarmsg.equals("am.datacollection.managed")))
/*       */       {
/*       */ 
/*       */ 
/*  7791 */         System.out.println("MonitorerrorsMessage======>" + jarmsg);
/*  7792 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(jarmsg));
/*  7793 */         saveErrors(request, errors);
/*       */       }
/*       */     }
/*       */     
/*  7797 */     AMConnectionPool.closeStatement(rs3);
/*  7798 */     String query1 = "select HEAPSIZECURRENT from AM_JVMData where ID='" + resourceid + "' order by collectiontime desc";
/*  7799 */     query1 = DBQueryUtil.getTopNValues(query1, 1);
/*  7800 */     ArrayList jvmrows = this.mo.getRows(query1);
/*  7801 */     boolean jvmDataPresent = false;
/*  7802 */     if (jvmrows.size() > 0)
/*       */     {
/*  7804 */       jvmDataPresent = true;
/*  7805 */       if (dctemp == 1)
/*       */       {
/*  7807 */         messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.weblogic.datacollectionstopped.text"));
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*  7812 */       String avail_query = "select category,severity from Alert where source='" + resourceid + "' and category='217'";
/*  7813 */       String avail_status = "5";
/*       */       try
/*       */       {
/*  7816 */         AMConnectionPool.getInstance();ResultSet avail_rs = AMConnectionPool.executeQueryStmt(avail_query);
/*  7817 */         if (avail_rs.next())
/*       */         {
/*  7819 */           avail_status = avail_rs.getString("severity");
/*       */         }
/*  7821 */         AMConnectionPool.closeStatement(avail_rs);
/*       */       }
/*       */       catch (Exception exc)
/*       */       {
/*  7825 */         System.out.println("WEBLOGIC:PROBLEM IN GETTING THE SEVERITY FOR THE MESSAGE");
/*       */       }
/*  7827 */       if ((avail_status != null) && (avail_status.equals("5")) && (errormsg != null) && (errormsg.indexOf("Data Collection Successfull") == -1) && (errormsg.indexOf("data collection accessdenied") == -1) && (errormsg.indexOf("Agent not registered") == -1) && (errormsg.indexOf("Anonymous user permission denied") == -1) && (errormsg.indexOf("permission denied to deploy agent") == -1) && (errormsg.indexOf("unknown host") == -1) && (errormsg.indexOf("Access Restricted") == -1) && (errormsg.indexOf("MBeans Registration Problem") == -1))
/*       */       {
/*  7829 */         String reason = FormatUtil.getString("am.webclient.weblogic.datacollection.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*  7830 */         messages1.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(reason));
/*       */       }
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  7849 */     HashMap map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(poll));
/*  7850 */     if (map != null)
/*       */     {
/*  7852 */       request.setAttribute("systeminfo", map);
/*       */     }
/*  7854 */     ArrayList rows = this.mo.getRows("select category,severity from Alert where source='" + resourceid + "' and category in ('216','217','224')");
/*  7855 */     Properties status = new Properties();
/*  7856 */     status.put("216", "-1");
/*  7857 */     status.put("217", "-1");
/*  7858 */     status.put("224", "-1");
/*  7859 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  7861 */       ArrayList row = (ArrayList)rows.get(i);
/*  7862 */       String category = (String)row.get(0);
/*  7863 */       String severity = (String)row.get(1);
/*  7864 */       status.setProperty(category, severity);
/*       */     }
/*  7866 */     request.setAttribute("currentstatus", status);
/*       */     
/*  7868 */     String query2 = "select RESPONSETIME from AM_ManagedObjectData where resid='" + resourceid + "' order by collectiontime desc";
/*  7869 */     query2 = DBQueryUtil.getTopNValues(query2, 1);
/*  7870 */     rows = this.mo.getRows(query2);
/*  7871 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  7873 */       rows = (ArrayList)rows.get(0);
/*  7874 */       String responsetime = (String)rows.get(0);
/*  7875 */       request.setAttribute("responsetime", responsetime);
/*       */     }
/*  7877 */     String query3 = "select HEAPSIZECURRENT from AM_JVMData where ID='" + resourceid + "' order by collectiontime desc";
/*  7878 */     query3 = DBQueryUtil.getTopNValues(query3, 1);
/*  7879 */     rows = this.mo.getRows(query3);
/*  7880 */     for (int i = 0; i < rows.size(); i++)
/*       */     {
/*  7882 */       rows = (ArrayList)rows.get(0);
/*  7883 */       String responsetime = (String)rows.get(0);
/*  7884 */       responsetime = FormatUtil.formatNumber(String.valueOf(Long.parseLong(responsetime) / 1024L)) + " KB";
/*       */       
/*  7886 */       request.setAttribute("heapsize", responsetime);
/*       */     }
/*  7888 */     saveMessages(request, messages1);
/*  7889 */     String path = "/jsp/WlogicComponent.jsp";
/*  7890 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showScriptMonitorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  7899 */     String path = null;
/*  7900 */     ActionMessages messages = new ActionMessages();
/*  7901 */     ActionErrors errors = new ActionErrors();
/*       */     
/*       */     try
/*       */     {
/*  7905 */       String haid = request.getParameter("haid");
/*  7906 */       System.out.println("the haid of script monitor:" + haid);
/*  7907 */       String applicationName = request.getParameter("name");
/*  7908 */       System.out.println("the application name of script monitor:" + applicationName);
/*  7909 */       String resourceid = request.getParameter("resourceid");
/*  7910 */       String resourcename = request.getParameter("resourcename");
/*       */       
/*  7912 */       String resourcetype = request.getParameter("type");
/*  7913 */       String moname = request.getParameter("moname");
/*  7914 */       ManagedApplication mo = new ManagedApplication();
/*  7915 */       String pollinterval = (String)request.getAttribute("reloadperiod");
/*       */       
/*  7917 */       if ((pollinterval == null) || (pollinterval.equals("")))
/*       */       {
/*  7919 */         int polling = 300;
/*  7920 */         pollinterval = String.valueOf(polling);
/*       */       }
/*  7922 */       request.setAttribute("reloadperiod", pollinterval);
/*  7923 */       HashMap map = new HashMap();
/*  7924 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  7925 */       if ((!resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) {
/*  7926 */         map = ClientDBUtil.getSystemHealthPollInfoForService(resourceid, Long.parseLong(pollinterval));
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  7931 */       String mtype = null;
/*  7932 */       ResultSet rs = null;
/*  7933 */       ResultSet rs1 = null;
/*  7934 */       ResultSet col_time = null;
/*  7935 */       String coll_query = "select max(collectiontime) from AM_ManagedObjectData where RESID=" + resourceid;
/*  7936 */       long collectiontime = 0L;
/*  7937 */       if ((resourcetype != null) && (resourcetype.equals("QENGINE")))
/*       */       {
/*  7939 */         coll_query = "select max(collectiontime) from AM_QEngineScript_Responsetime where RESOURCEID=" + resourceid;
/*       */       }
/*       */       try
/*       */       {
/*  7943 */         col_time = AMConnectionPool.executeQueryStmt(coll_query);
/*  7944 */         if (col_time.next())
/*       */         {
/*  7946 */           collectiontime = col_time.getLong(1);
/*       */         }
/*       */         else
/*       */         {
/*  7950 */           System.out.println("some problem in getting the collectiontime....");
/*       */         }
/*  7952 */         String responsetime = null;
/*  7953 */         ArrayList rows = null;
/*  7954 */         if (!resourcetype.equals("QENGINE"))
/*       */         {
/*  7956 */           rows = mo.getPropertiesList("select RESPONSETIME from AM_ManagedObjectData where RESID=" + resourceid + " and collectiontime=" + collectiontime);
/*       */         }
/*       */         else
/*       */         {
/*  7960 */           rows = mo.getPropertiesList("select RESPONSETIME from AM_QEngineScript_Responsetime where RESOURCEID=" + resourceid + " and collectiontime=" + collectiontime);
/*       */         }
/*  7962 */         if (rows.size() > 0)
/*       */         {
/*  7964 */           Properties jvmprops = (Properties)rows.get(0);
/*  7965 */           responsetime = jvmprops.get("RESPONSETIME").toString();
/*       */         }
/*  7967 */         if (responsetime != null) {
/*  7968 */           request.setAttribute("responsetime", responsetime);
/*       */         }
/*  7970 */         long poll_interval = 0L;
/*  7971 */         mtype = null;
/*  7972 */         String scriptname = null;
/*  7973 */         String serversite = null;
/*  7974 */         String content = "NA";
/*       */         ResultSet rsdisplay;
/*  7976 */         ResultSet rsdisplay; if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) {
/*  7977 */           rsdisplay = AMConnectionPool.executeQueryStmt("select POLLINTERVAL,FILE_DIR,SERVERSITE,MTYPE,CONTENT,ERRORMSG from AM_FILEDIR,AM_ManagedObject where AM_FILEDIR.RESOURCEID=" + resourceid + " and AM_ManagedObject.RESOURCEID=" + resourceid);
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  7982 */           rsdisplay = AMConnectionPool.executeQueryStmt("select pollinterval,scriptname,serversite from AM_ScriptArgs where resourceid=" + resourceid);
/*       */         }
/*  7984 */         if (rsdisplay.next())
/*       */         {
/*  7986 */           poll_interval = rsdisplay.getLong(1);
/*       */           
/*  7988 */           scriptname = rsdisplay.getString(2);
/*  7989 */           request.setAttribute("scriptname", scriptname);
/*  7990 */           serversite = rsdisplay.getString(3);
/*  7991 */           if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory")))
/*       */           {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8061 */             mtype = rsdisplay.getString("MTYPE");
/*  8062 */             content = rsdisplay.getString("CONTENT");
/*       */             
/*  8064 */             request.setAttribute("content", content);
/*       */           }
/*  8066 */           if ((serversite != null) && (serversite.equals("2")))
/*       */           {
/*  8068 */             ResultSet host_query = null;
/*       */             try
/*       */             {
/*  8071 */               host_query = AMConnectionPool.executeQueryStmt("select HOSTNAME from AM_SCRIPTHOSTDETAILS,AM_SCRIPTHOST_MAPPER  where SCRIPTID=" + resourceid + " and HOSTID=AM_SCRIPTHOSTDETAILS.ID UNION  select m.RESOURCENAME from AM_ManagedObject m, AM_SCRIPTHOST_MAPPER smap,HostDetails h where m.resourceid=smap.HOSTID and h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and smap.SCRIPTID=" + resourceid);
/*  8072 */               AMLog.debug("host_query>>>" + host_query);
/*  8073 */               if (host_query.next())
/*       */               {
/*  8075 */                 request.setAttribute("hostname", host_query.getString("HOSTNAME"));
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*       */             catch (Exception exc)
/*       */             {
/*  8082 */               exc.printStackTrace();
/*       */             }
/*       */             finally {
/*  8085 */               AMConnectionPool.closeStatement(host_query);
/*       */             }
/*       */           }
/*       */           
/*  8089 */           if (collectiontime != 0L)
/*       */           {
/*  8091 */             if (collectiontime > AMServerFramework.serverStartupTime)
/*       */             {
/*  8093 */               map.put("LASTDC", new Long(collectiontime));
/*  8094 */               map.put("NEXTDC", new Long(collectiontime + poll_interval * 1000L));
/*       */             }
/*       */             else
/*       */             {
/*  8098 */               map.put("LASTDC", new Long(collectiontime));
/*  8099 */               map.put("NEXTDC", new Long(AMServerFramework.serverStartupTime + poll_interval * 1000L));
/*       */ 
/*       */             }
/*       */             
/*       */ 
/*       */           }
/*       */           else
/*       */           {
/*  8107 */             map.put("NEXTDC", new Long(System.currentTimeMillis() + poll_interval * 1000L));
/*       */           }
/*       */           
/*  8110 */           AMConnectionPool.closeStatement(rsdisplay);
/*       */         }
/*  8112 */         request.setAttribute("mtype", mtype);
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*  8123 */           AMConnectionPool.closeStatement(col_time);
/*       */         }
/*       */         catch (Exception exc1) {}
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  8130 */         if (resourcetype.equals("File System Monitor")) {
/*       */           break label1159;
/*       */         }
/*       */       }
/*       */       catch (SQLException sqlexc0)
/*       */       {
/*  8116 */         sqlexc0.printStackTrace();
/*       */       } catch (Exception eee) {
/*  8118 */         eee.printStackTrace();
/*       */       }
/*       */       finally
/*       */       {
/*       */         try {
/*  8123 */           AMConnectionPool.closeStatement(col_time);
/*       */         }
/*       */         catch (Exception exc1) {}
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8130 */       if ((resourcetype.equals("file")) || (resourcetype.equals("directory"))) { label1159:
/*  8131 */         String message = null;
/*  8132 */         if ((mtype != null) && (mtype.equals("file"))) {
/*  8133 */           String filequery = "select SIZE,SIZE_INCREASED,MODIFIEDTIME,CONTENTCOUNT,SIZEVALUE_INCREASED,LASTMODFILE from AM_FILE where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + collectiontime;
/*       */           try {
/*  8135 */             ResultSet rsfile = AMConnectionPool.executeQueryStmt(filequery);
/*  8136 */             HashMap filedata = new HashMap();
/*  8137 */             if (rsfile.next()) {
/*  8138 */               filedata.put("resourceid", resourceid);
/*  8139 */               filedata.put("mtype", mtype);
/*  8140 */               filedata.put("size", Double.valueOf(rsfile.getDouble("SIZE")));
/*  8141 */               filedata.put("sizeincreased", Integer.valueOf(rsfile.getInt("SIZE_INCREASED")));
/*  8142 */               filedata.put("mod", rsfile.getString("MODIFIEDTIME"));
/*  8143 */               filedata.put("sizevalueincreased", Double.valueOf(rsfile.getDouble("SIZEVALUE_INCREASED")));
/*  8144 */               filedata.put("lastmodfile", rsfile.getString("LASTMODFILE"));
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*  8149 */             AMConnectionPool.closeStatement(rsfile);
/*  8150 */             String attid = null;
/*  8151 */             String attname = null;
/*  8152 */             ArrayList enable = new ArrayList();
/*  8153 */             HashMap reports = new HashMap();
/*  8154 */             if ((mtype != null) && (mtype.equals("file"))) {
/*  8155 */               attid = "6010";
/*  8156 */               attname = "File Size";
/*       */             }
/*       */             else {
/*  8159 */               attid = "6002";
/*  8160 */               attname = "Dir Size";
/*       */             }
/*       */             
/*       */ 
/*  8164 */             String reportsquery = "select AM_Script_Resource_Attributes_Mapper.reports from  AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=" + attid;
/*       */             
/*       */ 
/*       */             try
/*       */             {
/*  8169 */               ResultSet rep = AMConnectionPool.executeQueryStmt(reportsquery);
/*  8170 */               if (rep.next())
/*       */               {
/*  8172 */                 if (rep.getString(1).equals("1"))
/*       */                 {
/*  8174 */                   reports.put(attname, "enabled");
/*       */                 }
/*       */                 else
/*       */                 {
/*  8178 */                   reports.put(attname, "disabled");
/*       */                 }
/*       */               }
/*       */               
/*  8182 */               AMConnectionPool.closeStatement(rep);
/*  8183 */             } catch (Exception e1) { e1.printStackTrace(); }
/*  8184 */             request.setAttribute("enable", reports);
/*  8185 */             request.setAttribute("filedata", filedata);
/*  8186 */           } catch (Exception e) { e.printStackTrace();
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  8191 */           String dirquery = "select SIZE,SIZE_INCREASED,FILESCOUNT,NEWFILESCOUNT,DELETEDFILESCOUNT,MODIFIEDFILESCOUNT,SIZEVALUE_INCREASED,SUBDIRECTORIESCOUNT,MODIFIEDTIME from AM_DIR where RESOURCEID=" + resourceid + " and COLLECTIONTIME=" + collectiontime;
/*       */           try {
/*  8193 */             ResultSet rsdir = AMConnectionPool.executeQueryStmt(dirquery);
/*  8194 */             HashMap dirdata = new HashMap();
/*  8195 */             if (rsdir.next())
/*       */             {
/*  8197 */               dirdata.put("resourceid", resourceid);
/*  8198 */               dirdata.put("mtype", mtype);
/*  8199 */               dirdata.put("size", Double.valueOf(rsdir.getDouble("SIZE")));
/*  8200 */               dirdata.put("sizeincreased", Integer.valueOf(rsdir.getInt("SIZE_INCREASED")));
/*  8201 */               dirdata.put("filescount", Integer.valueOf(rsdir.getInt("FILESCOUNT")));
/*  8202 */               dirdata.put("newfcount", Integer.valueOf(rsdir.getInt("NEWFILESCOUNT")));
/*  8203 */               dirdata.put("dfcount", Integer.valueOf(rsdir.getInt("DELETEDFILESCOUNT")));
/*  8204 */               dirdata.put("mfcount", Integer.valueOf(rsdir.getInt("MODIFIEDFILESCOUNT")));
/*  8205 */               dirdata.put("sizevalueincreased", Double.valueOf(rsdir.getDouble("SIZEVALUE_INCREASED")));
/*  8206 */               dirdata.put("subdircount", Integer.valueOf(rsdir.getInt("SUBDIRECTORIESCOUNT")));
/*  8207 */               dirdata.put("mod", rsdir.getString("MODIFIEDTIME"));
/*       */             }
/*       */             
/*  8210 */             AMConnectionPool.closeStatement(rsdir);
/*  8211 */             request.setAttribute("dirdata", dirdata);
/*  8212 */           } catch (Exception ed) { ed.printStackTrace();
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/*  8219 */         String query = "select AM_ATTRIBUTES.ATTRIBUTE,AM_ATTRIBUTES.TYPE,AM_Script_Numeric_Data.VALUE,AM_Script_Numeric_Data.COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from AM_Script_Numeric_Data,AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_Script_Numeric_Data.ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=AM_Script_Numeric_Data.ATTRIBUTEID and AM_Script_Numeric_Data.COLLECTIONTIME=" + collectiontime + " order by AM_Script_Numeric_Data.COLLECTIONTIME";
/*  8220 */         String query1 = "select AM_ATTRIBUTES.ATTRIBUTE, AM_Script_String_Data.VALUE,AM_Script_String_Data.COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from AM_Script_String_Data,AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_Script_String_Data.ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=AM_Script_String_Data.ATTRIBUTEID and AM_Script_String_Data.COLLECTIONTIME=" + collectiontime + " order by AM_Script_String_Data.COLLECTIONTIME";
/*  8221 */         String original_type = (String)request.getAttribute("original_type");
/*  8222 */         String scalar_numeric = "AM_Script_Numeric_Data";
/*  8223 */         String scalar_string = "AM_Script_String_Data";
/*  8224 */         String tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA";
/*  8225 */         String tabular_string = "AM_CAM_COLUMNAR_DATA";
/*  8226 */         int baseid = -1;
/*  8227 */         if (request.getAttribute("baseid") != null)
/*       */         {
/*  8229 */           baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*       */         }
/*  8231 */         Properties ess_atts = new Properties();
/*  8232 */         if (baseid == -1)
/*       */         {
/*       */ 
/*  8235 */           query = "select AM_ATTRIBUTES.ATTRIBUTE," + scalar_numeric + ".VALUE," + scalar_numeric + ".COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from " + scalar_numeric + ",AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and " + scalar_numeric + ".RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=" + scalar_numeric + ".ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=" + scalar_numeric + ".ATTRIBUTEID and " + scalar_numeric + ".COLLECTIONTIME=" + collectiontime + " order by " + scalar_numeric + ".COLLECTIONTIME";
/*  8236 */           query1 = "select AM_ATTRIBUTES.ATTRIBUTE, " + scalar_string + ".VALUE," + scalar_string + ".COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from " + scalar_string + ",AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and " + scalar_string + ".RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=" + scalar_string + ".ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=" + scalar_string + ".ATTRIBUTEID and " + scalar_string + ".COLLECTIONTIME=" + collectiontime + " order by " + scalar_string + ".COLLECTIONTIME";
/*  8237 */           ess_atts.setProperty("Availability", "2200");
/*  8238 */           ess_atts.setProperty("Health", "2201");
/*  8239 */           ess_atts.setProperty("ResponseTime", "2202");
/*       */         }
/*       */         else
/*       */         {
/*  8243 */           scalar_numeric = "AM_Script_Numeric_Data_" + baseid;
/*  8244 */           scalar_string = "AM_Script_String_Data_" + baseid;
/*  8245 */           tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/*  8246 */           tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/*       */           
/*  8248 */           query = "select AM_ATTRIBUTES.ATTRIBUTE," + scalar_numeric + ".VALUE," + scalar_numeric + ".COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from " + scalar_numeric + ",AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + baseid + " and " + scalar_numeric + ".RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=" + scalar_numeric + ".ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=" + scalar_numeric + ".ATTRIBUTEID and " + scalar_numeric + ".COLLECTIONTIME=" + collectiontime + " order by " + scalar_numeric + ".COLLECTIONTIME";
/*  8249 */           query1 = "select AM_ATTRIBUTES.ATTRIBUTE, " + scalar_string + ".VALUE," + scalar_string + ".COLLECTIONTIME,AM_ATTRIBUTES.attributeid  from " + scalar_string + ",AM_Script_Resource_Attributes_Mapper,AM_ATTRIBUTES where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + baseid + " and " + scalar_string + ".RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=" + scalar_string + ".ATTRIBUTEID and AM_ATTRIBUTES.ATTRIBUTEID=" + scalar_string + ".ATTRIBUTEID and " + scalar_string + ".COLLECTIONTIME=" + collectiontime + " order by " + scalar_string + ".COLLECTIONTIME";
/*  8250 */           ess_atts = DataCollectionControllerUtil.getAttributeProps(original_type);
/*       */         }
/*  8252 */         request.setAttribute("ess_atts", ess_atts);
/*  8253 */         System.out.println("The query===>" + query);
/*  8254 */         HashMap ht_numeric = new HashMap();
/*  8255 */         HashMap ht_string = new HashMap();
/*  8256 */         HashMap ht_numeric_1 = new HashMap();
/*  8257 */         HashMap ht_string_1 = new HashMap();
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*  8317 */           rs = AMConnectionPool.executeQueryStmt(query);
/*  8318 */           while (rs.next())
/*       */           {
/*  8320 */             String temp1 = rs.getString(1);
/*  8321 */             String temp2 = rs.getString(2);
/*  8322 */             if (temp1 != null)
/*       */             {
/*  8324 */               if (temp2 == null) {}
/*       */               
/*       */ 
/*       */ 
/*  8328 */               ht_numeric.put(temp1, temp2);
/*  8329 */               ht_numeric_1.put(rs.getString(4), temp2);
/*       */             }
/*       */           }
/*  8332 */           AMConnectionPool.closeStatement(rs);
/*       */ 
/*       */         }
/*       */         catch (Exception e)
/*       */         {
/*  8337 */           System.out.println("The query in error====>" + query);
/*  8338 */           e.printStackTrace();
/*       */         }
/*       */         try
/*       */         {
/*  8342 */           rs = AMConnectionPool.executeQueryStmt(query1);
/*  8343 */           while (rs.next())
/*       */           {
/*  8345 */             String temp1 = rs.getString(1);
/*  8346 */             String temp2 = rs.getString(2);
/*  8347 */             if ((temp2 != null) && (temp2.contains("."))) {
/*  8348 */               temp2 = DBUtil.getFloatasString(temp2);
/*       */             }
/*  8350 */             if (temp1 != null)
/*       */             {
/*  8352 */               if (temp2 == null) {}
/*       */               
/*       */ 
/*       */ 
/*  8356 */               ht_string.put(temp1, temp2);
/*  8357 */               ht_string_1.put(rs.getString(4), temp2);
/*       */             }
/*       */           }
/*  8360 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*       */         catch (Exception e)
/*       */         {
/*  8364 */           System.out.println("The query1 in error====>" + query1);
/*  8365 */           e.printStackTrace();
/*       */         }
/*  8367 */         String att_display = null;
/*  8368 */         if (baseid == -1)
/*       */         {
/*  8370 */           att_display = "select AM_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTE from AM_ATTRIBUTES,AM_Script_Resource_Attributes_Mapper where (resourcetype='Script Monitor' or resourcetype='QENGINE')  and AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID";
/*       */         }
/*       */         else
/*       */         {
/*  8374 */           att_display = "select AM_ATTRIBUTES.ATTRIBUTEID,ATTRIBUTE from AM_ATTRIBUTES,AM_Script_Resource_Attributes_Mapper where resourcetype='" + original_type + "'  and AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + baseid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID";
/*       */         }
/*  8376 */         System.out.println("The att_displ---->" + att_display);
/*  8377 */         ResultSet att_rs = null;
/*  8378 */         HashMap displayname_attributeid = new HashMap();
/*       */         try
/*       */         {
/*  8381 */           att_rs = AMConnectionPool.executeQueryStmt(att_display);
/*       */           
/*  8383 */           while (att_rs.next())
/*       */           {
/*  8385 */             String temp_disp = att_rs.getString(2);
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8391 */             Integer attributeid = new Integer(att_rs.getInt(1));
/*  8392 */             displayname_attributeid.put(temp_disp, attributeid);
/*       */           }
/*  8394 */           AMConnectionPool.closeStatement(att_rs);
/*  8395 */           request.setAttribute("display_attributeid", displayname_attributeid);
/*       */         }
/*       */         catch (SQLException sqlexc)
/*       */         {
/*  8399 */           sqlexc.printStackTrace();
/*       */         }
/*  8401 */         request.setAttribute("numeric_data", ht_numeric);
/*  8402 */         request.setAttribute("string_data", ht_string);
/*  8403 */         request.setAttribute("numeric_data_id_value", ht_numeric_1);
/*  8404 */         request.setAttribute("string_data_id_value", ht_string_1);
/*  8405 */         Integer numeric_attributes = new Integer(ht_numeric.size());
/*  8406 */         Integer string_attributes = new Integer(ht_string.size());
/*  8407 */         request.setAttribute("numeric_size", numeric_attributes);
/*  8408 */         request.setAttribute("string_size", string_attributes);
/*  8409 */         char delimiter = '=';
/*  8410 */         HashMap parser = new HashMap();
/*  8411 */         String op = null;
/*  8412 */         ArrayList attributes_temp = new ArrayList();
/*  8413 */         Properties p = new Properties();
/*       */         
/*  8415 */         if (baseid == -1)
/*       */         {
/*  8417 */           parser = AMScriptDataCollector.parseAttributes(Integer.parseInt(resourceid), attributes_temp, p);
/*       */         }
/*       */         else
/*       */         {
/*  8421 */           parser = AMScriptDataCollector.parseAttributes(baseid, attributes_temp, p);
/*       */         }
/*       */         
/*  8424 */         if (parser.size() > 0)
/*       */         {
/*       */ 
/*  8427 */           int total_size = parser.size();
/*  8428 */           int numeric_counter = 0;
/*  8429 */           int non_numeric_counter = 0;
/*  8430 */           for (int i = 0; i < attributes_temp.size(); i++)
/*       */           {
/*  8432 */             if (String.valueOf(parser.get(attributes_temp.get(i))).trim().equalsIgnoreCase("numeric"))
/*       */             {
/*  8434 */               numeric_counter += 1;
/*       */             }
/*       */             else
/*       */             {
/*  8438 */               non_numeric_counter += 1;
/*       */             }
/*       */           }
/*  8441 */           ArrayList numeric = new ArrayList();
/*  8442 */           ArrayList non_numeric = new ArrayList();
/*  8443 */           parse_types(parser, attributes_temp, numeric, non_numeric);
/*       */           
/*       */ 
/*       */ 
/*       */ 
/*  8448 */           request.setAttribute("attributes", attributes_temp);
/*  8449 */           request.setAttribute("numeric", numeric);
/*  8450 */           request.setAttribute("non_numeric", non_numeric);
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*  8455 */         HashMap to_enable = new HashMap();
/*  8456 */         HashMap to_disable = new HashMap();
/*  8457 */         ArrayList enable = new ArrayList();
/*  8458 */         HashMap hm_reports = new HashMap();
/*  8459 */         String repenable_query = null;
/*  8460 */         String tocheck = "1";
/*  8461 */         if (baseid == -1)
/*       */         {
/*  8463 */           repenable_query = "select AM_ATTRIBUTES.ATTRIBUTE,AM_Script_Resource_Attributes_Mapper.reports from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + resourceid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID";
/*       */         }
/*       */         else
/*       */         {
/*  8467 */           repenable_query = "select AM_ATTRIBUTES.ATTRIBUTE,AM_Script_Resource_Attributes_Mapper.reports from AM_ATTRIBUTES, AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.RESOURCEID=" + baseid + " and AM_Script_Resource_Attributes_Mapper.ATTRIBUTEID=AM_ATTRIBUTES.ATTRIBUTEID";
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*  8474 */           ResultSet rep_rs = AMConnectionPool.executeQueryStmt(repenable_query);
/*  8475 */           while (rep_rs.next())
/*       */           {
/*  8477 */             String temp_name = rep_rs.getString(1);
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8483 */             String temp = (String)parser.get(temp_name);
/*  8484 */             if ((temp != null) && (temp.trim().equalsIgnoreCase("numeric")))
/*       */             {
/*  8486 */               if (rep_rs.getString(2).equals(tocheck))
/*       */               {
/*  8488 */                 hm_reports.put(temp_name, "enabled");
/*       */               }
/*       */               else
/*       */               {
/*  8492 */                 hm_reports.put(temp_name, "disabled");
/*       */               }
/*  8494 */               enable.add(temp_name);
/*       */             }
/*       */           }
/*  8497 */           AMConnectionPool.closeStatement(rep_rs);
/*       */           
/*       */ 
/*  8500 */           ArrayList attributes = new ArrayList();
/*  8501 */           if (resourcetype.equals("QENGINE"))
/*       */           {
/*  8503 */             ArrayList rows = mo.getRows("select t2.attributeid from AM_Script_Resource_Attributes_Mapper t1,AM_ATTRIBUTES t2 where t1.attributeid=t2.attributeid and RESOURCEID=" + resourceid + " order by t2.attributeid");
/*  8504 */             for (int i = 0; i < rows.size(); i++) {
/*  8505 */               attributes.add((String)((ArrayList)rows.get(i)).get(0));
/*       */             }
/*       */             
/*  8508 */             String tempquery = "select AM_ATTRIBUTES.attributeid, attribute from AM_ATTRIBUTES,AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.attributeid=AM_ATTRIBUTES.ATTRIBUTEID and resourceid=" + resourceid + " and type=3 order by AM_ATTRIBUTES.attributeid";
/*  8509 */             rows = mo.getRows(tempquery);
/*  8510 */             ArrayList urlids = new ArrayList();
/*  8511 */             for (int i = 0; i < rows.size(); i++)
/*       */             {
/*  8513 */               urlids.add((String)((ArrayList)rows.get(i)).get(0));
/*       */             }
/*  8515 */             request.setAttribute("urlids", urlids);
/*       */             
/*       */ 
/*  8518 */             tempquery = "select AM_ATTRIBUTES.attributeid, attribute from AM_ATTRIBUTES,AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.attributeid=AM_ATTRIBUTES.ATTRIBUTEID and resourceid=" + resourceid + "  and type=0 and attribute like '%response_time%' order by AM_ATTRIBUTES.attributeid";
/*  8519 */             rows = mo.getRows(tempquery);
/*  8520 */             ArrayList responsetimeids = new ArrayList();
/*  8521 */             for (int i = 0; i < rows.size(); i++)
/*       */             {
/*  8523 */               responsetimeids.add((String)((ArrayList)rows.get(i)).get(0));
/*       */             }
/*  8525 */             request.setAttribute("responsetimeids", responsetimeids);
/*       */             
/*  8527 */             tempquery = "select AM_ATTRIBUTES.attributeid, attribute from AM_ATTRIBUTES,AM_Script_Resource_Attributes_Mapper where AM_Script_Resource_Attributes_Mapper.attributeid=AM_ATTRIBUTES.ATTRIBUTEID and resourceid=" + resourceid + "  and type=0 and attribute like '%response_code%' order by AM_ATTRIBUTES.attributeid";
/*  8528 */             rows = mo.getRows(tempquery);
/*  8529 */             ArrayList responsecodes = new ArrayList();
/*  8530 */             for (int i = 0; i < rows.size(); i++)
/*       */             {
/*  8532 */               responsecodes.add((String)((ArrayList)rows.get(i)).get(0));
/*       */             }
/*  8534 */             request.setAttribute("responsecodes", responsecodes);
/*       */             
/*  8536 */             log.info("attributes are " + attributes);
/*  8537 */             request.setAttribute("urlsize", new Integer(responsetimeids.size()));
/*       */           }
/*  8539 */           request.setAttribute("attributeslist", attributes);
/*       */ 
/*       */ 
/*       */         }
/*       */         catch (Exception exc_rep)
/*       */         {
/*       */ 
/*  8546 */           exc_rep.printStackTrace();
/*       */         }
/*  8548 */         request.setAttribute("hm_reports", hm_reports);
/*  8549 */         request.setAttribute("enable", enable);
/*       */         
/*  8551 */         ArrayList table_details = ShowCustomDetails.getInstance().getTableValuesforScript(resourceid, collectiontime, request, String.valueOf(baseid), original_type);
/*  8552 */         request.setAttribute("tabledetails", table_details);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  8557 */       if (map != null) {
/*  8558 */         if (((resourcetype.equals("File System Monitor")) || (resourcetype.equalsIgnoreCase("Script Monitor"))) && (mtype != null)) {
/*  8559 */           resourcetype = mtype;
/*       */         }
/*  8561 */         map.put("TYPE", resourcetype);
/*  8562 */         request.setAttribute("systeminfo", map);
/*       */       }
/*       */       
/*       */ 
/*  8566 */       if ((request.getParameter("fromhost") != null) && (request.getParameter("fromhost").equals("true")))
/*       */       {
/*       */ 
/*       */ 
/*  8570 */         return null;
/*       */       }
/*  8572 */       String editPage = null;
/*  8573 */       if (request.getParameter("editPage") == null) {
/*  8574 */         editPage = "false";
/*       */       } else {
/*  8576 */         editPage = "true";
/*       */       }
/*  8578 */       if (((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) && (!editPage.equals("true"))) {
/*  8579 */         path = "/jsp/FileMonitor.jsp?resourcename=" + moname + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + "&pollInterval=" + pollinterval + "&moname=" + moname;
/*       */       }
/*  8581 */       else if (((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) && (editPage.equals("true"))) {
/*  8582 */         path = "/updateScript.do?method=editScript&resourceid=" + resourceid + "&type=" + resourcetype + "&resourcename=" + request.getParameter("resourcename") + "&mtype=" + mtype;
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*  8588 */       String typedisplayname = getDisplayName(resourcetype);
/*       */       
/*  8590 */       if (((resourcetype.equals("Script Monitor")) || (resourcetype.equals("Script"))) && (!editPage.equals("true"))) {
/*  8591 */         String message = getErrorMessage(resourceid);
/*  8592 */         if ((message != null) && (!message.trim().equals("")) && (!message.equals("am.datacollection.success")) && (!message.equals("am.datacollection.managed")) && (!message.trim().equals("am.datacollection.maintenance")) && (!message.trim().equals("am.datacollection.unmanaged"))) {
/*  8593 */           errors.add("org.apache.struts.action.ERROR", new ActionError(message));
/*  8594 */           saveErrors(request, errors);
/*  8595 */           saveMessages(request, null);
/*       */         }
/*       */         
/*  8598 */         path = "/jsp/ScriptMonitor.jsp?resourcename=" + moname + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + "&pollInterval=" + pollinterval + "&moname=" + moname + "&typedisplayname=" + typedisplayname;
/*       */       }
/*  8600 */       else if (((resourcetype.equals("Script Monitor")) || (resourcetype.equals("Script"))) && (editPage.equals("true"))) {
/*  8601 */         path = "/updateScript.do?method=editScript&resourceid=" + resourceid + "&type=" + resourcetype + "&resourcename=" + request.getParameter("resourcename") + "&mtype=" + mtype;
/*       */ 
/*       */ 
/*       */       }
/*  8605 */       else if (resourcetype.equals("QENGINE")) {
/*  8606 */         path = "/jsp/QEngineScriptMonitor.jsp?resourcename=" + moname + "&resourceid=" + resourceid + "&name=" + applicationName + "&type=" + resourcetype + "&pollInterval=" + pollinterval + "&moname=" + moname;
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*       */         try
/*       */         {
/*  8613 */           String message = getErrorMessage(resourceid);
/*  8614 */           if ((!message.equals("am.datacollection.success")) && (!message.equals("am.datacollection.managed")) && (!message.trim().equals("am.datacollection.maintenance")) && (!message.trim().equals("am.datacollection.unmanaged")) && (
/*  8615 */             (resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))))
/*       */           {
/*       */ 
/*  8618 */             String pmsg = null;
/*  8619 */             int len = message.length();
/*  8620 */             AMLog.debug("the length:" + len);
/*  8621 */             if (len != 0) {
/*  8622 */               if (message.equals("Permission denied"))
/*       */               {
/*  8624 */                 pmsg = FormatUtil.getString("am.webclient.permission.message");
/*  8625 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8626 */                 saveErrors(request, errors);
/*  8627 */               } else if (message.equals("Login Failure"))
/*       */               {
/*  8629 */                 pmsg = FormatUtil.getString("am.webclient.script.loginexception.text");
/*  8630 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8631 */                 saveErrors(request, errors);
/*  8632 */               } else if (message.equals("Connection Exception"))
/*       */               {
/*  8634 */                 pmsg = FormatUtil.getString("am.webclient.script.connectexception.text");
/*  8635 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8636 */                 saveErrors(request, errors);
/*       */               }
/*  8638 */               else if (message.equals("Session Problem"))
/*       */               {
/*  8640 */                 pmsg = FormatUtil.getString("am.webclient.script.loginexception.text");
/*  8641 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8642 */                 saveErrors(request, errors);
/*       */               }
/*  8644 */               else if (message.indexOf("Access is denied") != -1) {
/*  8645 */                 pmsg = FormatUtil.getString("am.webclient.script.accessdenied.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*       */                 
/*  8647 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8648 */                 saveErrors(request, errors);
/*       */               }
/*  8650 */               else if (message.indexOf("The RPC server is unavailable") != -1)
/*       */               {
/*  8652 */                 pmsg = FormatUtil.getString("am.webclient.script.rpc.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") });
/*  8653 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8654 */                 saveErrors(request, errors);
/*       */ 
/*       */               }
/*  8657 */               else if (message.indexOf("Unable to") != -1)
/*       */               {
/*  8659 */                 pmsg = FormatUtil.getString(message) + "." + FormatUtil.getString("am.webclient.fsm.support", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*  8660 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8661 */                 saveErrors(request, errors);
/*       */               }
/*  8663 */               else if ((message.indexOf("matched") == -1) || (message.indexOf("modified") == -1) || (message.indexOf("file/subdirectory is created/deleted") == -1)) {
/*  8664 */                 AMLog.debug("Error message is :" + message);
/*  8665 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(message));
/*  8666 */                 saveErrors(request, errors);
/*       */               }
/*  8668 */               else if (message.indexOf("The content") != -1)
/*       */               {
/*  8670 */                 message = message.substring(message.indexOf("The content") + 11, message.indexOf("is matched"));
/*  8671 */                 message = message.trim().replaceAll(" ", ",");
/*  8672 */                 System.out.println("the FSM message:" + message);
/*  8673 */                 pmsg = FormatUtil.getString("am.webclient.contentmatch.text", new String[] { message });
/*  8674 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8675 */                 saveErrors(request, errors);
/*       */ 
/*       */ 
/*       */               }
/*  8679 */               else if ((message != null) && (message.indexOf("File/Directory does not exist") != -1)) {
/*  8680 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(FormatUtil.getString(message)));
/*  8681 */                 saveErrors(request, errors);
/*       */               }
/*  8683 */               else if (((!message.equals("Directory does not exist")) || (!message.equals("file does not exist")) || (!message.equals("Folder does not exist"))) && (len > 1))
/*       */               {
/*  8685 */                 pmsg = FormatUtil.getString(message) + "." + FormatUtil.getString("am.webclient.fsm.support", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/*  8686 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(pmsg));
/*  8687 */                 saveErrors(request, errors);
/*       */ 
/*       */ 
/*       */               }
/*  8691 */               else if ((message != null) && (!message.equals(""))) {
/*  8692 */                 errors.add("org.apache.struts.action.ERROR", new ActionError(FormatUtil.getString(message)));
/*  8693 */                 saveErrors(request, errors);
/*       */ 
/*       */               }
/*       */               
/*       */ 
/*       */             }
/*  8699 */             else if ((message != null) && (!message.equals(""))) {
/*  8700 */               errors.add("org.apache.struts.action.ERROR", new ActionError(FormatUtil.getString(message)));
/*  8701 */               saveErrors(request, errors);
/*       */ 
/*       */             }
/*       */             
/*       */           }
/*       */           
/*       */ 
/*       */         }
/*       */         catch (Exception ex)
/*       */         {
/*       */ 
/*  8712 */           ex.printStackTrace();
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception npe) {
/*  8717 */       npe.printStackTrace(); }
/*  8718 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */   public String getErrorMessage(String resourceid) {
/*  8722 */     ResultSet rserror = null;
/*  8723 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8724 */     message = "";
/*       */     try {
/*  8726 */       rserror = AMConnectionPool.executeQueryStmt("select ERROR_MESSAGE from AM_MONITOR_ERRORS where RESOURCEID=" + resourceid);
/*       */       
/*  8728 */       if (rserror.next()) {}
/*  8729 */       return rserror.getString("ERROR_MESSAGE");
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  8733 */       e.printStackTrace();
/*       */     }
/*       */     finally {
/*       */       try {
/*  8737 */         AMConnectionPool.closeStatement(rserror);
/*       */       }
/*       */       catch (Exception e) {
/*  8740 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   public static String getDisplayName(String type)
/*       */   {
/*  8747 */     String dname = type;
/*  8748 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     try
/*       */     {
/*  8751 */       String q1 = "select displayname from AM_ManagedResourceType where resourcetype ='" + type + "'";
/*  8752 */       ResultSet rs = AMConnectionPool.executeQueryStmt(q1);
/*  8753 */       if (rs.next())
/*       */       {
/*  8755 */         dname = rs.getString(1);
/*       */       }
/*  8757 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception exc)
/*       */     {
/*  8761 */       exc.printStackTrace();
/*       */     }
/*  8763 */     return dname;
/*       */   }
/*       */   
/*       */   private ArrayList getTableValuesforScript(String scriptid, long collectiontime, HttpServletRequest request, int baseid, String original_type)
/*       */   {
/*  8768 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  8769 */     String tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA";
/*  8770 */     String tabular_string = "AM_CAM_COLUMNAR_DATA";
/*  8771 */     boolean isTableHasNumeric = false;
/*       */     
/*       */     try
/*       */     {
/*  8775 */       Hashtable ht = new Hashtable();
/*       */       
/*       */ 
/*  8778 */       ArrayList tableids = new ArrayList();
/*  8779 */       Properties tabid_tabname = new Properties();
/*  8780 */       ArrayList tablenames = new ArrayList();
/*  8781 */       ArrayList toreturn = new ArrayList();
/*  8782 */       Hashtable table_resids = new Hashtable();
/*  8783 */       String qry = "SELECT * FROM AM_SCRIPT_TABLES WHERE SCRIPTID=" + scriptid;
/*       */       
/*  8785 */       if (baseid != -1)
/*       */       {
/*  8787 */         qry = "SELECT * FROM AM_SCRIPT_TABLES WHERE SCRIPTID=" + baseid;
/*  8788 */         tabular_numeric = "AM_SCRIPT_TABULAR_NUMERIC_DATA_" + baseid;
/*  8789 */         tabular_string = "AM_CAM_COLUMNAR_DATA_" + baseid;
/*       */       }
/*       */       
/*  8792 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  8793 */       Hashtable table_health = new Hashtable();
/*  8794 */       Hashtable table_avail = new Hashtable();
/*  8795 */       Hashtable primary_col = new Hashtable();
/*       */       
/*  8797 */       Properties tableerror = new Properties();
/*  8798 */       ActionErrors errors = new ActionErrors();
/*  8799 */       boolean error_counter = true;
/*  8800 */       while (rs.next())
/*       */       {
/*  8802 */         tableids.add(rs.getString("TABLEID"));
/*  8803 */         tablenames.add(rs.getString("TABLENAME"));
/*  8804 */         primary_col.put(rs.getString("TABLEID"), rs.getString("PRIMARY_COLUMN"));
/*       */         
/*  8806 */         tabid_tabname.setProperty(rs.getString("TABLEID"), rs.getString("TABLENAME"));
/*  8807 */         String error = rs.getString("ERROR");
/*  8808 */         tableerror.setProperty(rs.getString("TABLENAME"), rs.getString("ERROR"));
/*  8809 */         if ((error != null) && (!error.trim().equals("")))
/*       */         {
/*  8811 */           if (error.equals("TOVALIDATE"))
/*       */           {
/*  8813 */             if (error_counter)
/*       */             {
/*  8815 */               String msg1 = FormatUtil.getString("am.scriptdetails.tables.generalerrror");
/*  8816 */               errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg1));
/*  8817 */               error_counter = false;
/*       */             }
/*  8819 */             System.out.println("The " + rs.getString("TABLENAME") + " is not present in the output file");
/*  8820 */             String msg = FormatUtil.getString("am.scriptdetails.table.notpresent", new String[] { rs.getString("TABLENAME") });
/*  8821 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg));
/*       */           }
/*  8823 */           if (error.equals("STARTED"))
/*       */           {
/*  8825 */             if (error_counter)
/*       */             {
/*  8827 */               String msg1 = FormatUtil.getString("am.scriptdetails.tables.generalerrror");
/*  8828 */               errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg1));
/*  8829 */               error_counter = false;
/*       */             }
/*  8831 */             System.out.println("The table " + rs.getString("TABLENAME") + " does not seems to be closed by the proper close tag in the output file");
/*  8832 */             String msg = FormatUtil.getString("am.scriptdetails.table.notclosed", new String[] { rs.getString("TABLENAME") });
/*  8833 */             errors.add("org.apache.struts.action.ERROR", new ActionMessage(msg));
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*  8838 */       saveErrors(request, errors);
/*  8839 */       if ((tableids != null) && (tableids.size() == 0))
/*       */       {
/*  8841 */         request.setAttribute("isTableHasNumeric", "false");
/*  8842 */         return new ArrayList();
/*       */       }
/*  8844 */       for (int i = 0; i < tableids.size(); i++)
/*       */       {
/*       */ 
/*       */ 
/*       */         try
/*       */         {
/*       */ 
/*       */ 
/*  8852 */           String qry_hth = "select ATTRIBUTEID,TYPE from AM_CAM_DC_ATTRIBUTES WHERE GROUPID=" + tableids.get(i) + " AND (TYPE=2 OR TYPE=1)";
/*  8853 */           ResultSet rs_tid = AMConnectionPool.executeQueryStmt(qry_hth);
/*  8854 */           String table_health_id = "-1";
/*  8855 */           String table_avail_id = "-1";
/*  8856 */           while (rs_tid.next())
/*       */           {
/*  8858 */             if ((rs_tid.getString("TYPE") != null) && (rs_tid.getString("TYPE").equals("2")))
/*       */             {
/*  8860 */               table_health_id = rs_tid.getString("ATTRIBUTEID");
/*  8861 */               table_health.put(tableids.get(i), table_health_id);
/*       */             }
/*       */             else
/*       */             {
/*  8865 */               table_avail_id = rs_tid.getString("ATTRIBUTEID");
/*  8866 */               table_avail.put(tableids.get(i), table_avail_id);
/*       */             }
/*       */           }
/*       */         }
/*       */         catch (Exception exc)
/*       */         {
/*  8872 */           exc.printStackTrace();
/*       */         }
/*       */       }
/*  8875 */       Hashtable table_attids = new Hashtable();
/*  8876 */       Hashtable table_attid_name = new Hashtable();
/*  8877 */       ArrayList atts_res_value = new ArrayList();
/*  8878 */       String res_qry = "select resourceid,resourcename from AM_PARENTCHILDMAPPER, AM_ManagedObject where AM_PARENTCHILDMAPPER.parentid=" + scriptid + " and  AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid order by dcstarted,resourceid ";
/*  8879 */       ResultSet res_ids = AMConnectionPool.executeQueryStmt(res_qry);
/*  8880 */       ArrayList row_id = new ArrayList();
/*  8881 */       ArrayList row_name = new ArrayList();
/*  8882 */       while (res_ids.next())
/*       */       {
/*  8884 */         row_id.add(res_ids.getString(1));
/*  8885 */         row_name.add(res_ids.getString(2));
/*       */       }
/*  8887 */       Hashtable table_data = new Hashtable();
/*  8888 */       Hashtable attid_details = new Hashtable();
/*  8889 */       Hashtable table_avail_data = new Hashtable();
/*  8890 */       for (int i = 0; i < tableids.size(); i++)
/*       */       {
/*  8892 */         ArrayList attributes = new ArrayList();
/*  8893 */         ArrayList order_list = new ArrayList();
/*  8894 */         String att_qry = "SELECT ATTRIBUTEID,ATTRIBUTENAME,TYPE,DISPLAYTYPE FROM AM_CAM_DC_ATTRIBUTES WHERE GROUPID=" + tableids.get(i) + " and TYPE <>2";
/*  8895 */         Hashtable ht_col_data = new Hashtable();
/*  8896 */         ResultSet rs1 = AMConnectionPool.executeQueryStmt(att_qry);
/*  8897 */         ArrayList name_type = new ArrayList();
/*  8898 */         while (rs1.next())
/*       */         {
/*       */ 
/*  8901 */           attributes.add(rs1.getString("ATTRIBUTEID"));
/*  8902 */           name_type = new ArrayList();
/*  8903 */           name_type.add(rs1.getString("ATTRIBUTENAME"));
/*  8904 */           name_type.add(rs1.getString("TYPE"));
/*  8905 */           if (!isTableHasNumeric)
/*       */           {
/*  8907 */             if (rs1.getString("TYPE").equals("0"))
/*       */             {
/*  8909 */               isTableHasNumeric = true;
/*       */             }
/*       */           }
/*  8912 */           name_type.add(rs1.getString("DISPLAYTYPE"));
/*       */           
/*  8914 */           attid_details.put(rs1.getString("ATTRIBUTEID"), name_type);
/*  8915 */           order_list.add(rs1.getString("ATTRIBUTEID"));
/*       */         }
/*       */         
/*  8918 */         ArrayList order_primary = new ArrayList();
/*  8919 */         StringTokenizer st = new StringTokenizer((String)primary_col.get(tableids.get(i)), "#");
/*  8920 */         while (st.hasMoreTokens())
/*       */         {
/*  8922 */           String attqry = "SELECT ATTRIBUTEID from AM_CAM_DC_ATTRIBUTES where  GROUPID=" + tableids.get(i) + " and ATTRIBUTENAME='" + st.nextToken() + "'";
/*       */           
/*       */ 
/*  8925 */           System.out.println("the attqry=======>:" + attqry);
/*       */           try
/*       */           {
/*  8928 */             ResultSet rsatt = AMConnectionPool.executeQueryStmt(attqry);
/*  8929 */             if (rsatt.next())
/*       */             {
/*       */ 
/*       */ 
/*  8933 */               order_primary.add(rsatt.getString("ATTRIBUTEID"));
/*       */             }
/*       */             
/*       */           }
/*       */           catch (Exception ex)
/*       */           {
/*  8939 */             ex.printStackTrace();
/*       */           }
/*       */         }
/*  8942 */         System.out.println("the order_primary:" + order_primary);
/*       */         
/*       */ 
/*       */ 
/*  8946 */         int z = 0;
/*  8947 */         for (int x = 0; x < order_primary.size(); x++)
/*       */         {
/*  8949 */           for (int y = 0; y < order_list.size(); y++)
/*       */           {
/*  8951 */             if (order_primary.get(x).equals(order_list.get(y)))
/*       */             {
/*       */ 
/*  8954 */               order_list.remove(y);
/*  8955 */               order_list.add(z, order_primary.get(x));
/*  8956 */               z++;
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  8965 */         ht_col_data.put("column", order_list);
/*  8966 */         ArrayList rowids = new ArrayList();
/*       */         
/*  8968 */         String tempRowId = null;
/*  8969 */         int tempRowIdVal = 0;
/*       */         
/*  8971 */         for (int c = 0; c < row_id.size(); c++)
/*       */         {
/*  8973 */           if (row_name.get(c).toString().startsWith(tablenames.get(i) + "_"))
/*       */           {
/*  8975 */             rowids.add(row_id.get(c));
/*       */             
/*  8977 */             if (tempRowIdVal == 0)
/*       */             {
/*  8979 */               tempRowId = (String)row_id.get(c);
/*  8980 */               tempRowIdVal++;
/*       */             }
/*       */             else
/*       */             {
/*  8984 */               tempRowId = tempRowId + "','" + (String)row_id.get(c);
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*  8990 */         table_resids.put(tableids.get(i), rowids);
/*       */         
/*       */ 
/*  8993 */         table_attids.put(tablenames.get(i), attributes);
/*       */         
/*  8995 */         Hashtable att_values = new Hashtable();
/*  8996 */         Hashtable data = new Hashtable();
/*       */         
/*       */ 
/*       */ 
/*  9000 */         ArrayList tempRowIdList = new ArrayList();
/*       */         ArrayList idListToBeDeleted;
/*  9002 */         if (tempRowId != null)
/*       */         {
/*       */ 
/*  9005 */           String str_qry = "select ATTRIBUTEID,VALUE,ROWID from " + tabular_string + " where ROWID in ('" + tempRowId + "') and COLLECTIONTIME=" + collectiontime;
/*  9006 */           Hashtable ht1 = new Hashtable();
/*  9007 */           ResultSet str_rs = AMConnectionPool.executeQueryStmt(str_qry);
/*  9008 */           boolean table_availibility = false;
/*  9009 */           while (str_rs.next())
/*       */           {
/*  9011 */             if (str_rs.getString("ATTRIBUTEID").equals((String)table_avail.get(tableids.get(i))))
/*       */             {
/*  9013 */               table_availibility = true;
/*       */             }
/*  9015 */             if (data.containsKey(str_rs.getString("ROWID")))
/*       */             {
/*  9017 */               Hashtable h = (Hashtable)data.get(str_rs.getString("ROWID"));
/*  9018 */               h.put(str_rs.getString("ATTRIBUTEID"), str_rs.getString("VALUE"));
/*  9019 */               data.put(str_rs.getString("ROWID"), h);
/*       */             }
/*       */             else
/*       */             {
/*  9023 */               Hashtable attid_val = new Hashtable();
/*  9024 */               attid_val.put(str_rs.getString("ATTRIBUTEID"), str_rs.getString("VALUE"));
/*  9025 */               data.put(str_rs.getString("ROWID"), attid_val);
/*  9026 */               tempRowIdList.add(str_rs.getString("ROWID"));
/*       */             }
/*       */           }
/*       */           
/*  9030 */           if (table_availibility) {
/*  9031 */             table_avail_data.put((String)tableids.get(i), "YES");
/*       */           } else
/*  9033 */             table_avail_data.put((String)tableids.get(i), "NO");
/*  9034 */           String num_qry = "select ATTRIBUTEID,VALUE,RESID from " + tabular_numeric + " where RESID in ('" + tempRowId + "') and COLLECTIONTIME=" + collectiontime;
/*  9035 */           ResultSet num_rs = AMConnectionPool.executeQueryStmt(num_qry);
/*       */           
/*  9037 */           while (num_rs.next())
/*       */           {
/*  9039 */             if (data.containsKey(num_rs.getString("RESID")))
/*       */             {
/*  9041 */               Hashtable h = (Hashtable)data.get(num_rs.getString("RESID"));
/*       */               
/*  9043 */               h.put(num_rs.getString("ATTRIBUTEID"), num_rs.getString("VALUE"));
/*  9044 */               data.put(num_rs.getString("RESID"), h);
/*       */             }
/*       */             else
/*       */             {
/*  9048 */               Hashtable resid_val = new Hashtable();
/*  9049 */               resid_val.put(num_rs.getString("ATTRIBUTEID"), num_rs.getString("VALUE"));
/*  9050 */               data.put(num_rs.getString("RESID"), resid_val);
/*  9051 */               tempRowIdList.add(num_rs.getString("RESID"));
/*       */             }
/*       */           }
/*       */           
/*       */ 
/*  9056 */           for (Object removeId : rowids)
/*       */           {
/*  9058 */             table_resids.remove((String)removeId);
/*       */           }
/*       */           
/*  9061 */           idListToBeDeleted = new ArrayList();
/*  9062 */           for (Object tempRowIdsToDelete : rowids)
/*       */           {
/*  9064 */             if (!tempRowIdList.contains((String)tempRowIdsToDelete))
/*       */             {
/*  9066 */               idListToBeDeleted.add((String)tempRowIdsToDelete);
/*       */             }
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*  9074 */         ht_col_data.put("data", data);
/*  9075 */         table_data.put(tableids.get(i), ht_col_data);
/*       */       }
/*  9077 */       toreturn.add(tableids);
/*  9078 */       toreturn.add(table_resids);
/*  9079 */       toreturn.add(tabid_tabname);
/*  9080 */       toreturn.add(attid_details);
/*  9081 */       toreturn.add(table_data);
/*  9082 */       toreturn.add(table_health);
/*  9083 */       toreturn.add(table_avail);
/*  9084 */       toreturn.add(table_avail_data);
/*  9085 */       request.setAttribute("isTableHasNumeric", String.valueOf(isTableHasNumeric));
/*  9086 */       return toreturn;
/*       */     }
/*       */     catch (Exception exc)
/*       */     {
/*  9090 */       exc.printStackTrace();
/*       */       
/*  9092 */       request.setAttribute("isTableHasNumeric", "false"); }
/*  9093 */     return new ArrayList();
/*       */   }
/*       */   
/*       */   public static void parse_types(HashMap parser, ArrayList original, ArrayList numeric, ArrayList non_numeric)
/*       */   {
/*  9098 */     for (int i = 0; i < original.size(); i++)
/*       */     {
/*  9100 */       if (String.valueOf(parser.get(original.get(i))).trim().equalsIgnoreCase("numeric"))
/*       */       {
/*  9102 */         numeric.add(original.get(i));
/*       */       }
/*       */       else
/*       */       {
/*  9106 */         non_numeric.add(original.get(i));
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showJ2EEResources(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9122 */     String haid = request.getParameter("haid");
/*  9123 */     String applicationName = request.getParameter("name");
/*  9124 */     String resourceid = request.getParameter("resourceid");
/*  9125 */     String resourcename = request.getParameter("resourcename");
/*  9126 */     String monitorname = getMonitorName(resourceid);
/*  9127 */     request.setAttribute("monitorname", monitorname);
/*  9128 */     saveToken(request);
/*  9129 */     AMDataCollectionHandler handler = AMDataCollectionHandler.getInstance();
/*  9130 */     String oldquery = "select RESOURCEID,DISPLAYNAME,EARNAME ,EJBJAR,EJBTYPE,IF(AM_PARENTCHILDMAPPER.CHILDID is NULL,'','checked') from AM_ManagedObject,AM_EAR,AM_EJB left outer join AM_PARENTCHILDMAPPER on AM_EAR.PARENTID=AM_PARENTCHILDMAPPER.PARENTID  and AM_PARENTCHILDMAPPER.CHILDID=AM_EJB.EJBID where AM_EAR.PARENTID='" + resourceid + "' and AM_EAR.EARID=AM_EJB.PARENTID and AM_EJB.EJBID =AM_ManagedObject.ResourceID order by EJBTYPE,EJBJAR";
/*       */     
/*       */ 
/*  9133 */     String query = "select RESOURCEID,DISPLAYNAME,EARNAME ,EJBJAR,EJBTYPE,IF(AM_PARENTCHILDMAPPER.CHILDID is NULL,'','checked') from AM_ManagedObject join AM_EAR join AM_EJB left outer join AM_PARENTCHILDMAPPER on AM_EAR.PARENTID=AM_PARENTCHILDMAPPER.PARENTID  and AM_PARENTCHILDMAPPER.CHILDID=AM_EJB.EJBID where AM_EAR.PARENTID='" + resourceid + "' and AM_EAR.EARID=AM_EJB.PARENTID and AM_EJB.EJBID =AM_ManagedObject.ResourceID order by EJBTYPE,EJBJAR";
/*       */     
/*       */ 
/*  9136 */     FormatUtil.printQueryChange("ShowResourceDetails.java", oldquery, query);
/*       */     
/*  9138 */     ArrayList rows = handler.getRows(query);
/*  9139 */     boolean resourcedeployed = false;
/*  9140 */     if (rows.size() > 0)
/*       */     {
/*  9142 */       request.setAttribute("ejbs", rows);
/*  9143 */       resourcedeployed = true;
/*       */     }
/*  9145 */     String oldquery1 = "select RESOURCEID,DISPLAYNAME,EARNAME,IF(AM_PARENTCHILDMAPPER.CHILDID is NULL,'','checked')  from AM_ManagedObject,AM_EAR,AM_WAR left outer join AM_PARENTCHILDMAPPER on AM_EAR.PARENTID=AM_PARENTCHILDMAPPER.PARENTID and AM_PARENTCHILDMAPPER.CHILDID=AM_WAR.WARID where AM_EAR.PARENTID='" + resourceid + "' and AM_EAR.EARID=AM_WAR.PARENTID and AM_WAR.WARID =AM_ManagedObject.ResourceID ";
/*       */     
/*       */ 
/*  9148 */     String query1 = "select RESOURCEID,DISPLAYNAME,EARNAME,IF(AM_PARENTCHILDMAPPER.CHILDID is NULL,'','checked')  from AM_ManagedObject join AM_EAR join AM_WAR left outer join AM_PARENTCHILDMAPPER on AM_EAR.PARENTID=AM_PARENTCHILDMAPPER.PARENTID and AM_PARENTCHILDMAPPER.CHILDID=AM_WAR.WARID where AM_EAR.PARENTID='" + resourceid + "' and AM_EAR.EARID=AM_WAR.PARENTID and AM_WAR.WARID =AM_ManagedObject.ResourceID ";
/*       */     
/*       */ 
/*  9151 */     FormatUtil.printQueryChange("ShowResourceDetails.java", oldquery, query);
/*       */     
/*       */ 
/*  9154 */     rows = handler.getRows(query1);
/*  9155 */     if (rows.size() > 0)
/*       */     {
/*  9157 */       request.setAttribute("wars", rows);
/*  9158 */       resourcedeployed = true;
/*       */     }
/*  9160 */     if (resourcedeployed)
/*       */     {
/*  9162 */       return new ActionForward("/jsp/addResources.jsp");
/*       */     }
/*       */     
/*       */ 
/*  9166 */     String path = "/jsp/NoData.jsp?message=<center>No J2EE Application found for this server.</center>";
/*  9167 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward showJ2EEdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9179 */     String haid = request.getParameter("haid");
/*  9180 */     String applicationName = request.getParameter("name");
/*  9181 */     String resourceid = request.getParameter("resourceid");
/*  9182 */     String resourcename = request.getParameter("resourcename");
/*  9183 */     String resourcetype = request.getParameter("type");
/*  9184 */     String appservertype = request.getParameter("appservertype");
/*  9185 */     String ejbtype = request.getParameter("ejbtype");
/*  9186 */     String contextroot = request.getParameter("contextroot");
/*  9187 */     String path = null;
/*       */     
/*  9189 */     if (appservertype.endsWith("WEBLOGIC-server"))
/*       */     {
/*  9191 */       if (resourcetype.equals("ejb"))
/*       */       {
/*  9193 */         path = "/jsp/ShowEJBDetails.jsp?ejbName=" + resourcename + "&resID=" + resourceid + "&type=" + ejbtype + "&appName=" + applicationName + "&appserver=" + appservertype + "&appID=" + haid + "";
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  9198 */         path = "/jsp/ShowKeyValues.jsp?ejbName=" + resourcename + "&resID=" + resourceid + "&appName=" + applicationName + "&appserver=" + appservertype + "&appID=" + haid + "";
/*       */       }
/*       */       
/*       */     }
/*  9202 */     else if (appservertype.endsWith("JBOSS-server"))
/*       */     {
/*  9204 */       if (resourcetype.equals("ejb"))
/*       */       {
/*  9206 */         path = "/jsp/ShowjbossejbDetails.jsp?ejbName=" + resourcename + "&type=" + ejbtype + "&appName=" + applicationName + "&appserver=" + appservertype + "&appID=" + haid + "";
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  9211 */         path = "/jsp/ShowWARDetails.jsp?ejbName=" + resourcename + "&resID=" + resourceid + "&contextroot=" + contextroot + "&appName=" + applicationName + "&appserver=" + appservertype + "&appID=" + haid + "";
/*       */       }
/*       */     }
/*       */     
/*  9215 */     return new ActionForward(path);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward ShowPortReconfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9228 */     String resourceid = request.getParameter("resourceid");
/*  9229 */     String resourcename = request.getParameter("moname");
/*  9230 */     AMActionForm amform = (AMActionForm)form;
/*  9231 */     String query = "select AM_PORTCONFIG.COMMAND , AM_PORTCONFIG.SEARCH , AM_ManagedObject.DISPLAYNAME from AM_PORTCONFIG, AM_ManagedObject  where AM_PORTCONFIG.RESOURCEID=" + resourceid + " and AM_PORTCONFIG.RESOURCEID=AM_ManagedObject.RESOURCEID";
/*  9232 */     String poll = "select POLLINTERVAL ,TARGETADDRESS , APPLNDISCPORT  from CollectData where CollectData.RESOURCENAME='" + resourcename + "' and CollectData.RESOURCETYPE='Port-Test'";
/*       */     try {
/*  9234 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*       */       
/*  9236 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/*  9237 */       if (rs.next()) {
/*  9238 */         if (!rs.getString("COMMAND").equals("NULL"))
/*       */         {
/*  9240 */           amform.setCommand(rs.getString("COMMAND"));
/*       */         }
/*  9242 */         if (!rs.getString("SEARCH").equals("NULL"))
/*       */         {
/*  9244 */           amform.setSearch(rs.getString("SEARCH"));
/*       */         }
/*  9246 */         amform.setDisplayname(rs.getString("DISPLAYNAME"));
/*       */       }
/*  9248 */       AMConnectionPool.closeStatement(rs);
/*  9249 */       rs = AMConnectionPool.executeQueryStmt(poll);
/*  9250 */       if (rs.next())
/*       */       {
/*  9252 */         amform.setPollInterval(rs.getInt(1) / 60);
/*  9253 */         amform.setHost(rs.getString(2));
/*  9254 */         amform.setPort(String.valueOf(rs.getInt(3)));
/*       */       }
/*  9256 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  9260 */       e.printStackTrace();
/*       */     }
/*  9262 */     return new ActionForward("/jsp/TCPMonitoring.jsp");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward associateMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9273 */     ActionForward forward = showResourceTypes(mapping, form, request, response);
/*  9274 */     if (request.getParameter("wiz") != null)
/*       */     {
/*  9276 */       ArrayList rows = this.mo.getPropertiesList("select DISPLAYNAME,TYPE from AM_ManagedObject,AM_PARENTCHILDMAPPER where childid=resourceid AND parentid=" + request.getParameter("haid"));
/*  9277 */       request.setAttribute("associatedmonitors", rows);
/*  9278 */       return new ActionForward("/jsp/wiz_step2.jsp");
/*       */     }
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*  9284 */     return new ActionForward("/showresource.do?type=All&method=getMonitorForm&haid=" + request.getParameter("haid"));
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward getAssociateMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9295 */     String resourceid = request.getParameter("resourceid");
/*  9296 */     String hostid = request.getParameter("hostid");
/*  9297 */     String resourcetype = request.getParameter("type");
/*  9298 */     String type = resourcetype;
/*  9299 */     if (resourcetype.equals("UrlMonitor"))
/*       */     {
/*  9301 */       resourcetype = "'UrlMonitor','UrlSeq'";
/*       */     }
/*       */     else
/*       */     {
/*  9305 */       resourcetype = "'" + resourcetype + "'";
/*       */     }
/*  9307 */     saveToken(request);
/*  9308 */     String filterCondn = "";
/*  9309 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/*  9311 */       filterCondn = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", EnterpriseUtil.filterResourceIds(request, true));
/*       */     }
/*       */     
/*  9314 */     String toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject  left outer join AM_PARENTCHILDMAPPER pc1 on AM_ManagedObject.resourceid=pc1.childid and pc1.parentid=" + hostid + " where type in (" + resourcetype + ") and parentid is null " + filterCondn;
/*  9315 */     String configured = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_ManagedObject.type in (" + resourcetype + ") and AM_PARENTCHILDMAPPER.PARENTID=" + hostid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID " + filterCondn;
/*       */     
/*       */ 
/*  9318 */     ArrayList rows = this.mo.getRows(toconfigure);
/*  9319 */     request.setAttribute("toconfigure", rows);
/*  9320 */     rows = this.mo.getRows(configured);
/*  9321 */     request.setAttribute("configured", rows);
/*  9322 */     return new ActionForward("/jsp/choosescript.jsp?type=" + type);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward sendSupport1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9334 */     ActionMessages messages = new ActionMessages();
/*  9335 */     ActionErrors errors = new ActionErrors();
/*  9336 */     String mailMsg = ((AMActionForm)form).getMailMsg();
/*  9337 */     String name = request.getParameter("customername");
/*  9338 */     String mail = request.getParameter("customermail");
/*  9339 */     String no = request.getParameter("customerno");
/*       */     
/*  9341 */     String fileName = SupportZipUtil.getFileName();
/*  9342 */     System.out.println("===================Customer Details======================== ");
/*  9343 */     System.out.println("Name  " + name);
/*  9344 */     System.out.println("Mail ID  " + mail);
/*  9345 */     System.out.println("Contact No  " + no);
/*  9346 */     System.out.println("============================================================ ");
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9352 */     String msg = "";
/*  9353 */     String supporttype = request.getParameter("supporttype");
/*  9354 */     String AM_HOME = ".." + File.separator;
/*  9355 */     AM_HOME = new File(AM_HOME).getCanonicalPath() + File.separator;
/*  9356 */     long size = SupportZipUtil.getFileSize(new File(AM_HOME + "logs"), 0L);
/*       */     
/*  9358 */     if (size <= 52428800L) {
/*  9359 */       System.out.println("The following information are taken during the Support File creation " + fileName + ".zip.gz just for checking the Status of the DB.");
/*  9360 */       MyThread m = new MyThread();
/*  9361 */       m.setmailMsg(mailMsg);
/*  9362 */       if (name != null)
/*       */       {
/*  9364 */         m.customerName = name;
/*       */       }
/*  9366 */       if (mail != null)
/*       */       {
/*  9368 */         m.customermail = mail;
/*       */       }
/*  9370 */       if (no != null)
/*       */       {
/*  9372 */         m.contact = no;
/*       */       }
/*  9374 */       m.setfileName(fileName);
/*  9375 */       if (supporttype.equals("save"))
/*       */       {
/*  9377 */         if (!OEMUtil.isOEM())
/*       */         {
/*  9379 */           msg = FormatUtil.getString("am.webclient.support.message", new String[] { UserUtil.getSupportMailID(), UserUtil.getSupportMailID(), AM_HOME + "support" + File.separator + fileName + ".zip.gz" });
/*       */         }
/*       */         else
/*       */         {
/*  9383 */           msg = FormatUtil.getString("am.webclient.support.message", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.talkback.mailid"), AM_HOME + "support" + File.separator + fileName + ".zip.gz" });
/*       */         }
/*  9385 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(msg));
/*  9386 */         saveMessages(request, messages);
/*  9387 */         m.upload = false;
/*       */       }
/*  9389 */       else if (supporttype.equals("direct"))
/*       */       {
/*       */ 
/*  9392 */         msg = FormatUtil.getString("am.webclient.support.upload.message", new String[] { UserUtil.getSupportMailID(), UserUtil.getSupportMailID(), AM_HOME + "support" + File.separator + fileName + ".zip.gz" });
/*  9393 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(msg));
/*  9394 */         saveMessages(request, messages);
/*  9395 */         m.upload = true;
/*       */       }
/*       */       
/*  9398 */       m.start();
/*       */     }
/*       */     else
/*       */     {
/*  9402 */       msg = "<b>Sorry, the support information file could not be created as the size of logs exceed 50 MB.</b> <br><br>Please create the support information file manually by executing the <b>createSupportFile.sh/bat</b> file present in the<b> " + AM_HOME + "bin </b>directory.<br><br>Once the file is created, please upload the file to our ftp site.<br> Below are the details of our ftp service.<br>           Server Name = <b>ftp.adventnet.com</b><br> user account = anonymous<br> password = your email address <br><br>Once you have uploaded the file , send an email to <a href=\"mailto:" + NmsUtil.GetString("product.talkback.mailid") + "\" class='staticlinks'>" + NmsUtil.GetString("product.talkback.mailid") + "</a> with the details of uploaded log files i.e filename, directory etc and the problems you have with the Applications Manager. </b>";
/*  9403 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(msg));
/*  9404 */       saveErrors(request, errors);
/*       */     }
/*       */     
/*  9407 */     request.setAttribute("showinline", "sentsupport");
/*       */     
/*       */ 
/*       */ 
/*  9411 */     return new ActionForward("/common/serverinfo.do");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward sendSupport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9423 */     ActionMessages messages = new ActionMessages();
/*  9424 */     ActionErrors errors = new ActionErrors();
/*  9425 */     String mailMsg = ((AMActionForm)form).getMailMsg();
/*  9426 */     String fileName = SupportZipUtil.getFileName();
/*  9427 */     String msg = "";
/*  9428 */     String AM_HOME = ".." + File.separator;
/*  9429 */     AM_HOME = new File(AM_HOME).getCanonicalPath() + File.separator;
/*  9430 */     long size = SupportZipUtil.getFileSize(new File(AM_HOME + "logs"), 0L);
/*       */     
/*  9432 */     if (size <= 52428800L) {
/*  9433 */       System.out.println("The following information are taken during the Support File creation " + fileName + ".zip.gz just for checking the Status of the DB.");
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9442 */       request.setAttribute("showsendsupport", "true");
/*  9443 */       boolean proxyenabled = SupportZipUtil.checkProxyConfigured();
/*  9444 */       if (!proxyenabled)
/*       */       {
/*  9446 */         request.setAttribute("proxynotenabled", "true");
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     else
/*       */     {
/*  9453 */       msg = FormatUtil.getString("am.webclient.support.supportfileexceedslimit", new String[] { AM_HOME, UserUtil.getSupportMailID(), UserUtil.getSupportMailID() });
/*  9454 */       System.out.println("msg=====>");
/*  9455 */       System.out.println(msg);
/*       */       
/*  9457 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError(msg));
/*  9458 */       saveErrors(request, errors);
/*       */     }
/*       */     
/*  9461 */     request.setAttribute("showinline", "sentsupport");
/*       */     
/*       */ 
/*       */ 
/*  9465 */     return new ActionForward("/common/serverinfo.do");
/*       */   }
/*       */   
/*       */   public class MyThread
/*       */     extends Thread
/*       */   {
/*       */     private String mailMsg;
/*       */     private String fileName;
/*  9473 */     private String customerName = "Not Given";
/*  9474 */     private String customermail = "Not Given";
/*  9475 */     private String contact = "Not Given";
/*       */     private boolean upload;
/*       */     
/*       */     public MyThread() {}
/*       */     
/*  9480 */     public void setmailMsg(String mailMsg) { this.mailMsg = mailMsg; }
/*       */     
/*       */ 
/*       */     public void setfileName(String fileName)
/*       */     {
/*  9485 */       this.fileName = fileName;
/*       */     }
/*       */     
/*       */     public void run()
/*       */     {
/*       */       try {
/*  9491 */         SupportZipUtil.createSupportZip(this.mailMsg, this.fileName);
/*  9492 */         if (this.upload)
/*       */         {
/*  9494 */           com.adventnet.appmanager.util.SupportFileUploader.doUpload(this.fileName, this.customermail, this.mailMsg, this.customerName, this.contact);
/*       */         }
/*       */       }
/*       */       catch (Exception e) {
/*  9498 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward getMonitorForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9511 */     String haid = request.getParameter("haid");
/*  9512 */     String applicationName = request.getParameter("name");
/*  9513 */     String resourceid = request.getParameter("resourceid");
/*  9514 */     String resourcename = request.getParameter("resourcename");
/*  9515 */     String resourcetype = request.getParameter("type");
/*  9516 */     String moname = request.getParameter("moname");
/*  9517 */     String path = null;
/*  9518 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     
/*  9520 */     String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*  9521 */     String adminResourceIdentity = "";
/*  9522 */     if (request.isUserInRole("OPERATOR"))
/*       */     {
/*  9524 */       return mapping.findForward("accessRestricted");
/*       */     }
/*  9526 */     if ((request.isUserInRole("USERS")) && (!request.isUserInRole("ADMIN")) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*       */     {
/*  9528 */       return mapping.findForward("accessRestricted");
/*       */     }
/*       */     
/*       */ 
/*  9532 */     if (ClientDBUtil.isPrivilegedUser(request)) {
/*  9533 */       if (Constants.isUserResourceEnabled()) {
/*  9534 */         String loginUserid = Constants.getLoginUserid(request);
/*  9535 */         adminResourceIdentity = " and AM_ManagedObject.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*       */       } else {
/*  9537 */         Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  9538 */         adminResourceIdentity = " and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resids);
/*       */       }
/*       */     }
/*       */     
/*  9542 */     ResultSet typers = null;
/*       */     try {
/*  9544 */       typers = AMConnectionPool.executeQueryStmt("select GROUPTYPE from AM_HOLISTICAPPLICATION where HAID=" + haid);
/*  9545 */       if (typers.next())
/*       */       {
/*  9547 */         String groupType = typers.getString("GROUPTYPE");
/*  9548 */         ((AMActionForm)form).setGroupType(groupType);
/*  9549 */         if (request.getParameter("typeSelectedFromCombo") == null)
/*       */         {
/*  9551 */           HashMap typeToAdd = com.adventnet.appmanager.struts.beans.GroupComponent.getResourceTypeToSelect(groupType);
/*  9552 */           if (typeToAdd.size() > 0)
/*       */           {
/*  9554 */             resourcetype = (String)typeToAdd.get("value");
/*  9555 */             ((AMActionForm)form).setType(resourcetype);
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*       */     finally
/*       */     {
/*       */       try {
/*  9563 */         if (typers != null)
/*       */         {
/*  9565 */           typers.close();
/*       */         }
/*       */       }
/*       */       finally {}
/*       */     }
/*  9570 */     if ((request.getParameter("fromwhere") != null) && (request.getParameter("fromwhere").equals("monitorgroupview")))
/*       */     {
/*  9572 */       request.setAttribute("fromwhere", "monitorgroupview");
/*       */     }
/*  9574 */     saveToken(request);
/*  9575 */     String toconfigure = null;
/*  9576 */     String configured = null;
/*  9577 */     String oldtoconfigure = null;
/*  9578 */     String oldconfigured = null;
/*  9579 */     String devicetoconfigure = null;
/*  9580 */     String opstordevtoconfigure = null;
/*       */     
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*  9586 */     configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP!='NWD' and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedResourceType.SUBGROUP='" + resourcetype + "' and relationshipid is not null order by DISPLAYNAME";
/*  9587 */     toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP!='NWD' and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedResourceType.SUBGROUP='" + resourcetype + "' and relationshipid is null order by DISPLAYNAME";
/*       */     
/*  9589 */     if (resourcetype.equals("NWD"))
/*       */     {
/*  9591 */       configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='NWD' and " + eumChildListCond + adminResourceIdentity + " and relationshipid is not null order by DISPLAYNAME";
/*  9592 */       toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='NWD' and " + eumChildListCond + adminResourceIdentity + "and relationshipid is null order by DISPLAYNAME";
/*       */     }
/*       */     
/*  9595 */     if (resourcetype.equals("SAN"))
/*       */     {
/*  9597 */       configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='SAN' and " + eumChildListCond + adminResourceIdentity + " and relationshipid is not null order by DISPLAYNAME";
/*  9598 */       toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='SAN' and " + eumChildListCond + adminResourceIdentity + "and relationshipid is null order by DISPLAYNAME";
/*       */     }
/*       */     
/*  9601 */     if (resourcetype.equals("EMO"))
/*       */     {
/*  9603 */       configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='EMO' and " + eumChildListCond + adminResourceIdentity + " and relationshipid is not null order by DISPLAYNAME";
/*  9604 */       toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='EMO' and " + eumChildListCond + adminResourceIdentity + "and relationshipid is null order by DISPLAYNAME";
/*       */     }
/*       */     
/*  9607 */     if ((resourcetype.equals("APP")) || (resourcetype.equals("DBS")) || (resourcetype.equals("SYS")) || (resourcetype.equals("URL")))
/*       */     {
/*  9609 */       configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='" + resourcetype + "' and " + eumChildListCond + adminResourceIdentity + " and relationshipid is not null order by DISPLAYNAME";
/*  9610 */       toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='" + resourcetype + "' and " + eumChildListCond + adminResourceIdentity + " and relationshipid is null order by DISPLAYNAME";
/*       */     }
/*       */     
/*       */ 
/*  9614 */     if (resourcetype.equals("All"))
/*       */     {
/*  9616 */       if (Constants.isIt360)
/*       */       {
/*  9618 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_AssociatedExtDevices on AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER. CHILDID where  AM_ManagedResourceType.RESOURCEGROUP not in ('NET','HAI') and " + eumChildListCond + adminResourceIdentity + " and relationshipid is null order by AM_ManagedResourceType.SUBGROUP, AM_ManagedObject.DISPLAYNAME";
/*       */       }
/*  9620 */       else if (Constants.sqlManager) {
/*  9621 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_AssociatedExtDevices on AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER. CHILDID where  AM_ManagedResourceType.SUBGROUP ='MSSQL-DB-server' and " + eumChildListCond + adminResourceIdentity + " and PRODUCTID is null and relationshipid is null order by AM_ManagedResourceType.SUBGROUP, AM_ManagedObject.DISPLAYNAME";
/*       */       }
/*       */       else
/*       */       {
/*  9625 */         toconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME, AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_AssociatedExtDevices on AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER. CHILDID where  (AM_ManagedResourceType.SUBGROUP in ('Unknown') or AM_ManagedResourceType.SUBGROUP in " + Constants.resourceTypes + ") and " + eumChildListCond + adminResourceIdentity + " and PRODUCTID is null and relationshipid is null order by AM_ManagedResourceType.SUBGROUP, AM_ManagedObject.DISPLAYNAME";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*  9630 */       configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE join AM_PARENTCHILDMAPPER on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedObject.TYPE !='HAI' order by AM_ManagedResourceType.RESOURCEGROUP desc";
/*       */       
/*  9632 */       if (!Constants.sqlManager) {
/*  9633 */         if (EnterpriseUtil.isAdminServer())
/*       */         {
/*  9635 */           String tempQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_AssociatedExtDevices,AM_ManagedResourceType where AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and " + eumChildListCond + adminResourceIdentity + " and AM_AssociatedExtDevices.RESID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE";
/*  9636 */           ResultSet rs = AMConnectionPool.executeQueryStmt(tempQuery);
/*  9637 */           String associatedResIds = "-1";
/*  9638 */           int temp = 1;
/*  9639 */           while (rs.next())
/*       */           {
/*       */ 
/*  9642 */             associatedResIds = associatedResIds + "','" + rs.getString("RESOURCEID");
/*       */           }
/*       */           
/*  9645 */           AMConnectionPool.closeStatement(rs);
/*  9646 */           devicetoconfigure = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.SUBGROUP,AM_AssociatedExtDevices.PRODUCTID from AM_AssociatedExtDevices,AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedObject.RESOURCEID not in ('" + associatedResIds + "')";
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/*  9651 */           String tempQuery = "(select AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_PARENTCHILDMAPPER,ExternalDeviceDetails,AM_AssociatedExtDevices where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_AssociatedExtDevices.RESID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedObject.RESOURCENAME=ExternalDeviceDetails.NAME and AM_AssociatedExtDevices.PRODUCTID=ExternalDeviceDetails.PRODUCTID) UNION ALL (select AM_ManagedObject.RESOURCENAME from AM_ManagedObject,AM_PARENTCHILDMAPPER,ExternalLUNDetails,AM_AssociatedExtDevices where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_AssociatedExtDevices.RESID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.RESOURCEID=AM_AssociatedExtDevices.RESID and " + eumChildListCond + adminResourceIdentity + " and AM_ManagedObject.RESOURCENAME=ExternalLUNDetails.UID and AM_AssociatedExtDevices.PRODUCTID=ExternalLUNDetails.PRODUCTID)";
/*       */           
/*       */ 
/*  9654 */           ResultSet rs = AMConnectionPool.executeQueryStmt(tempQuery);
/*  9655 */           String associatedResName = "";
/*  9656 */           int temp = 1;
/*  9657 */           while (rs.next())
/*       */           {
/*  9659 */             if (temp == 1)
/*       */             {
/*  9661 */               associatedResName = rs.getString("RESOURCENAME");
/*  9662 */               temp++;
/*       */             }
/*       */             else
/*       */             {
/*  9666 */               associatedResName = associatedResName + "','" + rs.getString("RESOURCENAME");
/*       */             }
/*       */           }
/*  9669 */           AMConnectionPool.closeStatement(rs);
/*       */           ExtDeviceActions extdevices;
/*  9671 */           if (Constants.isIt360)
/*       */           {
/*  9673 */             devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID from ExternalDeviceDetails where ExternalDeviceDetails.NAME not in('" + associatedResName + "') and ExternalDeviceDetails.CATEGORY not in('OpManager-Desktop','OpManager-Server')";
/*  9674 */             extdevices = new ExtDeviceActions();
/*       */           }
/*       */           else
/*       */           {
/*  9678 */             if (ExtProdUtil.isServiceConfigured(ExtProdUtil.opstor))
/*       */             {
/*  9680 */               opstordevtoconfigure = "(select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID from ExternalDeviceDetails where ExternalDeviceDetails.NAME not in('" + associatedResName + "') and ExternalDeviceDetails.CATEGORY like 'OpStor%') UNION ALL (select UID,NAME,'OpStor-LUN',PRODUCTID from ExternalLUNDetails where UID not in ('" + associatedResName + "'))";
/*       */             }
/*  9682 */             if (ExtProdUtil.isServiceConfigured("OpManager"))
/*       */             {
/*  9684 */               devicetoconfigure = "select ExternalDeviceDetails.NAME,ExternalDeviceDetails.DISPLAYNAME,ExternalDeviceDetails.CATEGORY,ExternalDeviceDetails.PRODUCTID from ExternalDeviceDetails where ExternalDeviceDetails.NAME not in('" + associatedResName + "') and ExternalDeviceDetails.CATEGORY like 'OpManager%'";
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     }
/*  9690 */     ArrayList rows = this.mo.getRows(toconfigure);
/*  9691 */     System.out.println("the query===>" + toconfigure);
/*  9692 */     request.setAttribute("toconfigure", rows);
/*  9693 */     System.out.println("the rows===>" + rows);
/*  9694 */     rows = this.mo.getRows(configured);
/*  9695 */     System.out.println("the query===>" + toconfigure);
/*  9696 */     request.setAttribute("configured", rows);
/*  9697 */     System.out.println("the rows===>" + rows);
/*  9698 */     if (devicetoconfigure != null)
/*       */     {
/*  9700 */       rows = this.mo.getRows(devicetoconfigure);
/*  9701 */       request.setAttribute("devicetoconfigure", rows);
/*       */     }
/*       */     
/*  9704 */     if (opstordevtoconfigure != null)
/*       */     {
/*  9706 */       rows = this.mo.getRows(opstordevtoconfigure);
/*  9707 */       request.setAttribute("opstordevtoconfigure", rows);
/*       */     }
/*       */     
/*  9710 */     ChildMOHandler.getMonitorForm(request);
/*       */     
/*       */ 
/*       */ 
/*  9714 */     ResultSet rs = null;
/*  9715 */     HashMap configuredSite24x7Mon = new HashMap();
/*       */     
/*  9717 */     if ((Constants.isExtDeviceConfigured()) && (resourcetype.equals("All"))) {
/*       */       try {
/*  9719 */         if (!EnterpriseUtil.isAdminServer()) {
/*  9720 */           Iterator it = DBUtil.site24x7monList.keySet().iterator();
/*  9721 */           while (it.hasNext()) {
/*  9722 */             String key = (String)it.next();
/*  9723 */             HashMap mp = (HashMap)((HashMap)DBUtil.site24x7monList.get(key)).clone();
/*  9724 */             configuredSite24x7Mon.put(key, mp);
/*       */           }
/*       */         } else {
/*  9727 */           rs = AMConnectionPool.executeQueryStmt("select RESOURCENAME,TYPE,DISPLAYNAME from AM_ManagedObject where TYPE like '%Site24x7%'");
/*  9728 */           HashMap detailsM = new HashMap();
/*  9729 */           while (rs.next()) {
/*  9730 */             HashMap<String, String> detailsMap = new HashMap();
/*  9731 */             detailsMap.put("displayname", rs.getString("DISPLAYNAME"));
/*  9732 */             String type = rs.getString("TYPE");
/*  9733 */             detailsMap.put("monitortype", type.substring(type.lastIndexOf("-") + 1, type.length()));
/*  9734 */             detailsM.put(rs.getString("RESOURCENAME"), detailsMap);
/*       */           }
/*  9736 */           configuredSite24x7Mon.put("1", detailsM);
/*  9737 */           AMConnectionPool.closeStatement(rs);
/*       */         }
/*  9739 */         rs = AMConnectionPool.executeQueryStmt("select AM_ManagedObject.RESOURCENAME from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE join AM_PARENTCHILDMAPPER on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_PARENTCHILDMAPPER.PARENTID='" + haid + "' and AM_ManagedObject.TYPE !='HAI' and AM_ManagedResourceType.SUBGROUP='Site24x7' order by AM_ManagedResourceType.RESOURCEGROUP desc");
/*  9740 */         while (rs.next()) {
/*  9741 */           String resName = rs.getString("RESOURCENAME");
/*  9742 */           if (!EnterpriseUtil.isAdminServer()) {
/*  9743 */             String pid = "-1";
/*  9744 */             if (DBUtil.site24x7monListMapping.containsKey(resName)) {
/*  9745 */               pid = (String)DBUtil.site24x7monListMapping.get(resName);
/*       */             }
/*  9747 */             if ((configuredSite24x7Mon.containsKey(pid)) && (((HashMap)configuredSite24x7Mon.get(pid)).containsKey(resName))) {
/*  9748 */               ((HashMap)configuredSite24x7Mon.get(pid)).remove(resName);
/*       */             }
/*  9750 */           } else if (((HashMap)configuredSite24x7Mon.get("1")).containsKey(resName))
/*       */           {
/*  9752 */             ((HashMap)configuredSite24x7Mon.get("1")).remove(resName);
/*       */           }
/*       */         }
/*       */       } catch (Exception e) {
/*  9756 */         e.printStackTrace();
/*       */       }
/*       */       finally {
/*  9759 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/*  9762 */     request.setAttribute("configuredSite24x7Mon", configuredSite24x7Mon);
/*  9763 */     return new ActionForward("/jsp/chooseresource.jsp");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   private String truncate(String str)
/*       */   {
/*  9770 */     if (str.length() > 5)
/*       */     {
/*  9772 */       return str.substring(0, 5);
/*       */     }
/*       */     
/*  9775 */     return str;
/*       */   }
/*       */   
/*       */   private String getMonitorName(String resourceid)
/*       */   {
/*  9780 */     ArrayList rows = this.mo.getRows("select displayname from AM_ManagedObject where resourceid=" + resourceid);
/*  9781 */     if (rows.size() > 0)
/*       */     {
/*  9783 */       rows = (ArrayList)rows.get(0);
/*  9784 */       return (String)rows.get(0);
/*       */     }
/*  9786 */     return "";
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward updatePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*  9793 */     String addparam = null;
/*       */     
/*  9795 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*  9796 */     boolean pwdMatchCheck = true;
/*  9797 */     int userID = 0;
/*  9798 */     ResultSet rs = null;
/*  9799 */     ResultSet pwdPolicySet = null;
/*  9800 */     ResultSet userIDSet = null;
/*  9801 */     String errType = "noerror";
/*  9802 */     String method = "updateUser";
/*  9803 */     String status = "";
/*       */     
/*       */     try
/*       */     {
/*  9807 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  9808 */       String oldpassword = request.getParameter("oldpassword");
/*  9809 */       String password = request.getParameter("password");
/*       */       
/*  9811 */       String confirmPassword = request.getParameter("confirmpassword");
/*  9812 */       String userName = request.getRemoteUser();
/*       */       
/*  9814 */       if (com.manageengine.appmanager.util.ADAuthenticationUtil.getDomainIDforUser(userName) == -1)
/*       */       {
/*  9816 */         String checkquery = "select * from AM_UserPasswordTable , AM_UserGroupTable where AM_UserPasswordTable.USERNAME='" + request.getRemoteUser() + "' and AM_UserPasswordTable.PASSWORD=" + DBQueryUtil.MD5(oldpassword) + " and AM_UserPasswordTable.USERNAME=AM_UserGroupTable.USERNAME ";
/*  9817 */         rs = AMConnectionPool.executeQueryStmt(checkquery);
/*       */         
/*       */ 
/*       */ 
/*  9821 */         String pwdPolicyQuery = "select VALUE from AM_GLOBALCONFIG where NAME='am.admin.usermgmt.pwdpolicy.enabled'";
/*  9822 */         System.out.println("Password policy  Enabled Status " + pwdPolicyQuery);
/*  9823 */         pwdPolicySet = AMConnectionPool.executeQueryStmt(pwdPolicyQuery);
/*  9824 */         if (pwdPolicySet.next())
/*       */         {
/*  9826 */           status = pwdPolicySet.getString(1);
/*       */         }
/*  9828 */         if ((status != null) && (!status.equals("")) && (status.equals("true")))
/*       */         {
/*  9830 */           errType = updatepwdCheck(userName, password, oldpassword, confirmPassword, method);
/*       */         }
/*  9832 */         System.out.println("errType" + errType);
/*  9833 */         if ((errType.equals("noerror")) && (errType != null) && (!errType.equals("")))
/*       */         {
/*  9835 */           if (rs.next())
/*       */           {
/*  9837 */             String userIDQuery = "select USERID from AM_UserPasswordTable where USERNAME='" + request.getRemoteUser() + "'";
/*       */             
/*  9839 */             userIDSet = AMConnectionPool.executeQueryStmt(userIDQuery);
/*  9840 */             if (userIDSet.next())
/*       */             {
/*  9842 */               userID = userIDSet.getInt(1);
/*       */             }
/*       */             
/*       */ 
/*       */ 
/*       */ 
/*  9848 */             ush.updatePwdHistoryTable(userID, userName, password);
/*       */             
/*       */ 
/*  9851 */             String updatequery = "update AM_UserPasswordTable  set password=" + DBQueryUtil.MD5(password) + " where USERNAME='" + request.getRemoteUser() + "'";
/*  9852 */             AMConnectionPool.executeUpdateStmt(updatequery);
/*  9853 */             addparam = "true";
/*       */             
/*       */ 
/*  9856 */             if ((EnterpriseUtil.isAdminServer()) && (EnterpriseUtil.isPushUserConfigDetailsEnabled()) && (!oldpassword.equals(password))) {
/*  9857 */               Properties userProps = new Properties();
/*  9858 */               userProps.setProperty("userId", String.valueOf(userID));
/*  9859 */               userProps.setProperty("userName", userName);
/*  9860 */               userProps.setProperty("password", password);
/*  9861 */               userProps.setProperty("md5Password", "false");
/*  9862 */               com.adventnet.appmanager.util.MASSyncUtil.pushUserDetailsToMAS(userProps, "update");
/*       */             }
/*       */           }
/*       */           else
/*       */           {
/*  9867 */             addparam = "false";
/*  9868 */             errType = "oldpwdnotmatch";
/*       */           }
/*       */           
/*       */         }
/*       */         else {
/*  9873 */           addparam = "false";
/*       */         }
/*       */         
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/*  9880 */         addparam = "false";
/*  9881 */         errType = "aduser";
/*       */       }
/*       */       
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  9887 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*  9891 */       AMConnectionPool.closeStatement(userIDSet);
/*  9892 */       AMConnectionPool.closeStatement(rs);
/*  9893 */       AMConnectionPool.closeStatement(pwdPolicySet);
/*       */     }
/*       */     
/*  9896 */     String str = "/jsp/Popup_password.jsp?sucess=" + addparam + "&errType=" + errType;
/*  9897 */     return new ActionForward(str, true);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ActionForward getUserPwdPolicy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */   {
/*  9907 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  9908 */     ResultSet pwdPolicySet = null;
/*  9909 */     String status = "false";
/*       */     try
/*       */     {
/*  9912 */       String pwdPolicyQuery = "select VALUE from AM_GLOBALCONFIG where NAME='am.admin.usermgmt.pwdpolicy.enabled'";
/*  9913 */       pwdPolicySet = AMConnectionPool.executeQueryStmt(pwdPolicyQuery);
/*  9914 */       if (pwdPolicySet.next())
/*       */       {
/*  9916 */         status = pwdPolicySet.getString("VALUE");
/*  9917 */         System.out.println("Password Policy Status " + status);
/*       */       }
/*  9919 */       response.setContentType("text/html");
/*  9920 */       response.setHeader("Cache-Control", "no-cache");
/*  9921 */       response.getWriter().write(status);
/*       */ 
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/*  9926 */       System.out.println("pwdPolicySet" + e);
/*  9927 */       return null;
/*       */     }
/*       */     finally
/*       */     {
/*  9931 */       AMConnectionPool.closeStatement(pwdPolicySet);
/*       */     }
/*  9933 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */   public String updatepwdCheck(String userName, String password, String oldpassword, String confirmPassword, String method)
/*       */   {
/*  9941 */     boolean state = true;boolean samepwd = true;boolean pwdConChar = true;boolean pwdHisChkState = true;
/*  9942 */     String errType = "noerror";
/*  9943 */     int pwdLen = password.length();
/*  9944 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*       */     
/*  9946 */     if ((pwdLen < 8) || (pwdLen > 255))
/*       */     {
/*  9948 */       errType = "pwdlen";
/*  9949 */       return errType;
/*       */     }
/*  9951 */     if ((password == null) || (password.equals("")))
/*       */     {
/*  9953 */       errType = "pwdempty";
/*  9954 */       return errType;
/*       */     }
/*  9956 */     if ((confirmPassword == null) || (confirmPassword.equals("")))
/*       */     {
/*  9958 */       errType = "confirmpwdempty";
/*  9959 */       return errType;
/*       */     }
/*  9961 */     if (!password.equals(confirmPassword))
/*       */     {
/*  9963 */       errType = "pwdnotequal";
/*  9964 */       return errType;
/*       */     }
/*  9966 */     state = ush.checkUserNameAsPwd(userName, password);
/*  9967 */     if (!state)
/*       */     {
/*  9969 */       errType = "sameasuser";
/*  9970 */       return errType;
/*       */     }
/*  9972 */     state = ush.checkwithRegEx(password);
/*  9973 */     if (!state)
/*       */     {
/*  9975 */       errType = "notRegEx";
/*  9976 */       return errType;
/*       */     }
/*  9978 */     pwdConChar = ush.passwordToCheck(oldpassword, password);
/*  9979 */     if (!pwdConChar)
/*       */     {
/*  9981 */       errType = "pwdinconchar";
/*  9982 */       return errType;
/*       */     }
/*  9984 */     pwdHisChkState = ush.checkForPwdHistory(userName, password);
/*  9985 */     if (!pwdHisChkState)
/*       */     {
/*  9987 */       errType = "pwdinhistory";
/*  9988 */       return errType;
/*       */     }
/*       */     
/*       */ 
/*  9992 */     return errType;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public Properties getResponseAndContentLength(String resid)
/*       */   {
/* 10002 */     ResultSet rs = null;
/* 10003 */     Properties pro = null;
/* 10004 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*       */     
/*       */     try
/*       */     {
/* 10008 */       if (resid != null)
/*       */       {
/*       */ 
/* 10011 */         String query1 = "SELECT max(COLLECTIONTIME)as ColTime FROM AM_ManagedObjectData WHERE RESID=" + resid;
/*       */         
/* 10013 */         rs = AMConnectionPool.executeQueryStmt(query1);
/* 10014 */         long collectiontime = 0L;
/* 10015 */         if (rs.next())
/*       */         {
/* 10017 */           collectiontime = rs.getLong("ColTime");
/*       */         }
/*       */         
/* 10020 */         String query2 = "select AM_ManagedObjectData.RESPONSETIME,AM_URLData_EXT.DNSTIME,AM_URLData_EXT.CONNECTIONTIME,AM_URLData_EXT.FIRSTBYTETIME,AM_URLData_EXT.LASTBYTETIME,AM_URLData.CONTENTLENGTH from AM_ManagedObjectData,AM_URLData,AM_URLData_EXT where  AM_ManagedObjectData.RESID=AM_URLData.URLID  AND AM_ManagedObjectData.RESID=AM_URLData_EXT.URLID AND AM_ManagedObjectData.COLLECTIONTIME=AM_URLData_EXT.COLLECTIONTIME AND AM_ManagedObjectData.COLLECTIONTIME=AM_URLData.COLLECTIONTIME AND AM_ManagedObjectData.COLLECTIONTIME=" + collectiontime + " AND AM_ManagedObjectData.RESID=" + resid;
/* 10021 */         rs = AMConnectionPool.executeQueryStmt(query2);
/* 10022 */         String restime = "-";
/* 10023 */         String dnstime = "-";
/* 10024 */         String connectiontime = "-";
/* 10025 */         String firstbytetime = "-";
/* 10026 */         String lastbytetime = "-";
/* 10027 */         String clength = "-";
/* 10028 */         if (rs.next())
/*       */         {
/* 10030 */           pro = new Properties();
/* 10031 */           restime = rs.getString("RESPONSETIME");
/* 10032 */           clength = rs.getString("CONTENTLENGTH");
/* 10033 */           dnstime = rs.getString("DNSTIME");
/* 10034 */           connectiontime = rs.getString("CONNECTIONTIME");
/* 10035 */           firstbytetime = rs.getString("FIRSTBYTETIME");
/* 10036 */           lastbytetime = rs.getString("LASTBYTETIME");
/* 10037 */           pro.setProperty("responsetime", restime);
/* 10038 */           pro.setProperty("dnstime", dnstime);
/* 10039 */           pro.setProperty("connectiontime", connectiontime);
/* 10040 */           pro.setProperty("firstbytetime", firstbytetime);
/* 10041 */           pro.setProperty("lastbytetime", lastbytetime);
/* 10042 */           pro.setProperty("contentlength", clength);
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/* 10048 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/*       */     catch (Exception ex)
/*       */     {
/* 10052 */       ex.printStackTrace();
/*       */     }
/* 10054 */     return pro;
/*       */   }
/*       */   
/*       */   public ActionForward deleteDependentMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/*       */     try
/*       */     {
/* 10062 */       PrintWriter writer = response.getWriter();
/* 10063 */       String parentId = request.getParameter("parentId");
/* 10064 */       String childId = request.getParameter("childId");
/* 10065 */       JSONObject responseObj = new JSONObject();
/* 10066 */       response.setContentType("text/json");
/* 10067 */       response.setCharacterEncoding("UTF-8");
/* 10068 */       boolean isSuccess = DependentDeviceUtil.getInstance().deleteDependentMonitor(parentId, childId);
/* 10069 */       responseObj.put("result", isSuccess);
/* 10070 */       Hashtable<String, Hashtable<String, JSONArray>> subMgTable; Enumeration<String> enu; if (!isSuccess)
/*       */       {
/* 10072 */         responseObj.put("message", FormatUtil.getString("am.webclient.dependentMonitor.deleteFailed.errorMsg"));
/*       */       }
/*       */       else
/*       */       {
/* 10076 */         HashMap<String, HashMap<String, String>> dependentMonitorInfo = AMCacheHandler.getDependentDevice(childId);
/* 10077 */         if ((EnterpriseUtil.isAdminServer()) && ((dependentMonitorInfo == null) || (dependentMonitorInfo.isEmpty())))
/*       */         {
/* 10079 */           Hashtable mgTable = DBUtil.getMonitorsInAdminMG(childId, null);
/* 10080 */           EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(mgTable, childId, 2);
/* 10081 */           subMgTable = DBUtil.getAllMonitorsUnderSubGroupForMonitorGroup(childId, true);
/* 10082 */           for (enu = subMgTable.keys(); enu.hasMoreElements();)
/*       */           {
/* 10084 */             String subGroupID = (String)enu.nextElement();
/* 10085 */             Hashtable<String, JSONArray> subTable = (Hashtable)subMgTable.get(subGroupID);
/* 10086 */             if (AMCacheHandler.getDependentDevice(subGroupID) == null)
/*       */             {
/* 10088 */               EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(subTable, subGroupID, 2);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/* 10093 */       writer.print(responseObj);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10097 */       e.printStackTrace();
/*       */     }
/* 10099 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward selectDependentMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/* 10104 */     ResultSet result = null;
/* 10105 */     HashMap monInfo = new HashMap();
/* 10106 */     String haid = request.getParameter("haid");
/* 10107 */     request.setAttribute("haid", haid);
/*       */     try
/*       */     {
/* 10110 */       ArrayList allMonitors = new ArrayList();
/* 10111 */       ArrayList monitorsInMG = new ArrayList();
/* 10112 */       ArrayList<String> types = new ArrayList();
/* 10113 */       ArrayList selectedDependentMonitorList = new ArrayList();
/*       */       
/* 10115 */       String query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,AM_ManagedObject.TYPE AS TYPE,AM_ManagedResourceType.DISPLAYNAME AS TYPENAME,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType WHERE AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedResourceType.RESOURCETYPE  IN " + Constants.sqlManagerresourceTypes;
/* 10116 */       if (Constants.isIt360)
/*       */       {
/* 10118 */         query = query + " and AM_ManagedObject.TYPE not like 'OpManager-Interface%' ";
/*       */       }
/* 10120 */       if (!Constants.sqlManager)
/*       */       {
/* 10122 */         query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,AM_ManagedObject.TYPE AS TYPE,AM_ManagedResourceType.DISPLAYNAME AS TYPENAME,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType WHERE AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedResourceType.RESOURCETYPE NOT IN ('Network','Node','HAI') ";
/* 10123 */         if (Constants.isPrivilegedUser(request)) {
/* 10124 */           Vector<String> resIds = DelegatedUserRoleUtil.getResIDsForUser(request);
/* 10125 */           query = query + " and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resIds);
/*       */         }
/*       */         
/* 10128 */         if (Constants.isIt360)
/*       */         {
/* 10130 */           query = query + " and AM_ManagedObject.TYPE not like 'OpManager-Interface%' ";
/*       */         }
/*       */       }
/* 10133 */       query = query + "  ORDER BY AM_ManagedObject.DISPLAYNAME ";
/* 10134 */       result = AMConnectionPool.executeQueryStmt(query);
/* 10135 */       while (result.next())
/*       */       {
/* 10137 */         if (!haid.equals(result.getString("RESOURCEID")))
/*       */         {
/* 10139 */           HashMap monitorInfo = new HashMap();
/* 10140 */           String resourceTypeName = result.getString("TYPENAME");
/* 10141 */           String resourceType = result.getString("TYPE");
/* 10142 */           monitorInfo.put("resourceid", result.getString("RESOURCEID"));
/* 10143 */           monitorInfo.put("displayname", result.getString("DISPLAYNAME"));
/* 10144 */           monitorInfo.put("type", result.getString("TYPE"));
/* 10145 */           monitorInfo.put("typeName", resourceTypeName);
/* 10146 */           monitorInfo.put("imagepath", result.getString("IMAGEPATH"));
/* 10147 */           if (EnterpriseUtil.isManagedServer)
/*       */           {
/* 10149 */             monitorInfo.put("managedServer", FormatUtil.getString("Local Host"));
/*       */           }
/* 10151 */           else if (EnterpriseUtil.isAdminServer())
/*       */           {
/* 10153 */             monitorInfo.put("managedServer", CommDBUtil.getManagedServerNameWithPort(result.getString("RESOURCEID")));
/*       */           }
/*       */           else
/*       */           {
/* 10157 */             monitorInfo.put("managedServer", "");
/*       */           }
/* 10159 */           allMonitors.add(monitorInfo);
/* 10160 */           if (resourceType.contains("OpManager"))
/*       */           {
/* 10162 */             resourceTypeName = "Network Devices";
/*       */           }
/* 10164 */           if (resourceType.contains("OpStor"))
/*       */           {
/* 10166 */             resourceTypeName = "Storage Devices";
/*       */           }
/* 10168 */           if (resourceType.contains("Site24x7"))
/*       */           {
/* 10170 */             resourceTypeName = "Site24x7 Monitors";
/*       */           }
/* 10172 */           if (!types.contains(resourceTypeName))
/*       */           {
/* 10174 */             types.add(resourceTypeName);
/*       */           }
/*       */         }
/*       */       }
/* 10178 */       String dependentDeviceIDStr = "";
/* 10179 */       String monitorSelectedFromGUI = request.getParameter("selectedMonitors");
/* 10180 */       if ((monitorSelectedFromGUI != null) && (!monitorSelectedFromGUI.equals("")))
/*       */       {
/* 10182 */         monitorSelectedFromGUI = Translate.decode(monitorSelectedFromGUI);
/* 10183 */         JSONArray jsArr = new JSONArray(monitorSelectedFromGUI);
/* 10184 */         int size = jsArr.length();
/* 10185 */         for (int i = 0; i < size; i++)
/*       */         {
/* 10187 */           String tempResourceID = jsArr.getString(i);
/* 10188 */           if (dependentDeviceIDStr.equals(""))
/*       */           {
/* 10190 */             dependentDeviceIDStr = "'" + tempResourceID + "'";
/*       */           }
/*       */           else
/*       */           {
/* 10194 */             dependentDeviceIDStr = dependentDeviceIDStr + ",'" + tempResourceID + "'";
/*       */           }
/* 10196 */           selectedDependentMonitorList.add(tempResourceID);
/*       */         }
/* 10198 */         request.setAttribute("selectedMonitors", jsArr.toString());
/*       */       }
/*       */       else
/*       */       {
/* 10202 */         HashMap<String, HashMap<String, String>> dependentList = AMCacheHandler.getDependentDevice(haid);
/* 10203 */         if (dependentList != null)
/*       */         {
/* 10205 */           Iterator itr = dependentList.keySet().iterator();
/* 10206 */           JSONArray jsArr = new JSONArray();
/*       */           
/* 10208 */           while (itr.hasNext())
/*       */           {
/*       */ 
/* 10211 */             String selectedMonitors = (String)itr.next();
/* 10212 */             jsArr.put(selectedMonitors);
/* 10213 */             if (dependentDeviceIDStr.equals(""))
/*       */             {
/* 10215 */               dependentDeviceIDStr = "'" + selectedMonitors + "'";
/*       */             }
/*       */             else
/*       */             {
/* 10219 */               dependentDeviceIDStr = dependentDeviceIDStr + ",'" + selectedMonitors + "'";
/*       */             }
/* 10221 */             selectedDependentMonitorList.add(selectedMonitors);
/*       */           }
/* 10223 */           if (jsArr.length() > 0)
/*       */           {
/* 10225 */             request.setAttribute("selectedMonitors", jsArr.toString());
/*       */           }
/*       */         }
/*       */       }
/* 10229 */       request.setAttribute("selectedDependentMonitorList", selectedDependentMonitorList);
/* 10230 */       if ((EnterpriseUtil.isManagedServer) && (!dependentDeviceIDStr.equals("")))
/*       */       {
/* 10232 */         Vector<HashMap<String, String>> list = DependentDeviceUtil.getInstance().getManagedServerResource(haid, dependentDeviceIDStr);
/* 10233 */         if (list != null)
/*       */         {
/* 10235 */           int listSize = list.size();
/* 10236 */           for (int i = 0; i < listSize; i++)
/*       */           {
/* 10238 */             HashMap<String, String> resourceInfo = (HashMap)list.get(i);
/* 10239 */             if (resourceInfo != null)
/*       */             {
/* 10241 */               allMonitors.add(resourceInfo);
/* 10242 */               if (!types.contains(resourceInfo.get("type")))
/*       */               {
/* 10244 */                 types.add(resourceInfo.get("type"));
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/* 10250 */       if (EnterpriseUtil.isAdminServer())
/*       */       {
/* 10252 */         ArrayList<String> allManagedServers = EnterpriseUtil.getAllManagedServers();
/* 10253 */         if (allManagedServers != null)
/*       */         {
/* 10255 */           request.setAttribute("MAS", allManagedServers);
/*       */         }
/*       */       }
/* 10258 */       request.setAttribute("allMonitors", allMonitors);
/* 10259 */       Collections.sort(types, String.CASE_INSENSITIVE_ORDER);
/* 10260 */       request.setAttribute("types", types);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10264 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 10268 */       AMConnectionPool.closeStatement(result);
/*       */     }
/* 10270 */     return new ActionForward("/jsp/SelectDependentMonitor.jsp?haid=" + haid);
/*       */   }
/*       */   
/*       */   public ActionForward selectMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/* 10275 */     ResultSet result = null;
/* 10276 */     HashMap monInfo = new HashMap();
/* 10277 */     String haid = request.getParameter("haid");
/* 10278 */     String attributeType = request.getParameter("attributetype");
/*       */     try
/*       */     {
/* 10281 */       ArrayList allMonitors = new ArrayList();
/* 10282 */       ArrayList monitorsInMG = new ArrayList();
/* 10283 */       ArrayList<String> types = new ArrayList();
/*       */       
/*       */ 
/* 10286 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER WHERE AM_PARENTCHILDMAPPER.PARENTID=" + haid + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ORDER BY AM_ManagedObject.DISPLAYNAME");
/* 10287 */       while (result.next())
/*       */       {
/* 10289 */         HashMap monitorInfo = new HashMap();
/* 10290 */         monitorInfo.put("resourceid", result.getString("RESOURCEID"));
/* 10291 */         monitorInfo.put("displayname", result.getString("DISPLAYNAME"));
/* 10292 */         monitorInfo.put("type", result.getString("TYPE"));
/* 10293 */         monitorInfo.put("imagepath", result.getString("IMAGEPATH"));
/* 10294 */         allMonitors.add(monitorInfo);
/* 10295 */         if (!types.contains(result.getString("TYPE")))
/*       */         {
/* 10297 */           types.add(result.getString("TYPE"));
/*       */         }
/*       */       }
/* 10300 */       request.setAttribute("allMonitors", allMonitors);
/* 10301 */       Collections.sort(types, String.CASE_INSENSITIVE_ORDER);
/* 10302 */       request.setAttribute("types", types);
/*       */       
/* 10304 */       ArrayList<HashMap<String, String>> selectedChildMO = null;
/* 10305 */       ArrayList<String> childTypes = new ArrayList();
/* 10306 */       HashMap<String, String> childMonitorTypes = ChildMOHandler.getChildMOTypes();
/* 10307 */       Iterator itr = childMonitorTypes.keySet().iterator();
/* 10308 */       String childTypesStr = "";
/* 10309 */       while (itr.hasNext())
/*       */       {
/* 10311 */         String key = (String)itr.next();
/* 10312 */         String value = (String)childMonitorTypes.get(key);
/* 10313 */         if ((!"2".equals(attributeType)) || (!value.equals("Service")))
/*       */         {
/*       */ 
/*       */ 
/* 10317 */           childTypes.add(key);
/* 10318 */           if (childTypesStr.equals(""))
/*       */           {
/* 10320 */             childTypesStr = value;
/*       */           }
/*       */           else
/*       */           {
/* 10324 */             childTypesStr = childTypesStr + "," + value; }
/*       */         }
/*       */       }
/* 10327 */       if ("1".equals(attributeType))
/*       */       {
/* 10329 */         selectedChildMO = ChildMOHandler.listChildMonitorUnderMG(haid);
/*       */       }
/*       */       else
/*       */       {
/* 10333 */         selectedChildMO = ChildMOHandler.listChildMonitorUnderMG(haid, childTypesStr);
/*       */       }
/*       */       
/* 10336 */       if ((selectedChildMO != null) && (selectedChildMO.size() > 0))
/*       */       {
/* 10338 */         allMonitors.addAll(selectedChildMO);
/* 10339 */         types.addAll(childTypes);
/*       */       }
/*       */       
/* 10342 */       ArrayList<HashMap<String, String>> selectedDepMGrps = listSelectedDependentMGrps(haid);
/* 10343 */       if ((selectedDepMGrps != null) && (selectedDepMGrps.size() > 0)) {
/* 10344 */         allMonitors.addAll(selectedDepMGrps);
/* 10345 */         types.add("am.webclient.rule.type.dependent.group");
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10350 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 10354 */       AMConnectionPool.closeStatement(result);
/*       */     }
/* 10356 */     request.setAttribute("attributeType", attributeType);
/* 10357 */     return new ActionForward("/jsp/SelectMonitors.jsp?haid=" + haid + "&PRINTER_FRIENDLY=true");
/*       */   }
/*       */   
/*       */   public ActionForward selectDependentMonitorFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 10363 */     ResultSet result = null;
/* 10364 */     PrintWriter out = response.getWriter();
/* 10365 */     StringBuffer temp = new StringBuffer();
/* 10366 */     String resourceId = request.getParameter("haid");
/* 10367 */     String type = request.getParameter("type");
/* 10368 */     String managedServer = request.getParameter("managedServer");
/* 10369 */     ArrayList<HashMap<String, String>> resourceList = new ArrayList();
/*       */     try
/*       */     {
/* 10372 */       String qry = null;
/* 10373 */       if (managedServer != null)
/*       */       {
/* 10375 */         qry = DependentDeviceUtil.getFilterQuery(type, resourceId, managedServer);
/*       */       }
/*       */       else
/*       */       {
/* 10379 */         qry = DependentDeviceUtil.getFilterQuery(type, resourceId);
/*       */       }
/* 10381 */       if (Constants.isPrivilegedUser(request)) {
/* 10382 */         Vector<String> resIds = DelegatedUserRoleUtil.getResIDsForUser(request);
/* 10383 */         qry = qry + " and " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resIds);
/*       */       }
/* 10385 */       qry = qry + " ORDER BY AM_ManagedObject.DISPLAYNAME";
/* 10386 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt(qry);
/* 10387 */       while (result.next())
/*       */       {
/* 10389 */         HashMap<String, String> resourceMap = new HashMap();
/* 10390 */         resourceMap.put("resourceid", result.getString("RESOURCEID"));
/* 10391 */         resourceMap.put("displayname", result.getString("DISPLAYNAME"));
/* 10392 */         resourceMap.put("type", result.getString("TYPE"));
/* 10393 */         resourceMap.put("typeName", result.getString("TYPENAME"));
/* 10394 */         resourceMap.put("managedServer", result.getString("MANAGEDSERVER"));
/* 10395 */         resourceMap.put("imagepath", result.getString("IMAGEPATH"));
/* 10396 */         if (EnterpriseUtil.isAdminServer())
/*       */         {
/* 10398 */           resourceMap.put("managedServer", CommDBUtil.getManagedServerNameWithPort(result.getString("RESOURCEID")));
/*       */         }
/* 10400 */         resourceList.add(resourceMap);
/*       */       }
/* 10402 */       String dependentDeviceStr = "";
/* 10403 */       String monitorSelectedFromGUI = request.getParameter("selectedMonitors");
/* 10404 */       ArrayList selectedDependentMonitorList = new ArrayList();
/* 10405 */       if ((monitorSelectedFromGUI != null) && (!monitorSelectedFromGUI.equals("")))
/*       */       {
/* 10407 */         monitorSelectedFromGUI = Translate.decode(monitorSelectedFromGUI);
/* 10408 */         JSONArray jsArr = new JSONArray(monitorSelectedFromGUI);
/* 10409 */         int size = jsArr.length();
/* 10410 */         for (int i = 0; i < size; i++)
/*       */         {
/* 10412 */           String tempResourceID = jsArr.getString(i);
/* 10413 */           if (dependentDeviceStr.equals(""))
/*       */           {
/* 10415 */             dependentDeviceStr = "'" + tempResourceID + "'";
/*       */           }
/*       */           else
/*       */           {
/* 10419 */             dependentDeviceStr = dependentDeviceStr + ",'" + tempResourceID + "'";
/*       */           }
/* 10421 */           selectedDependentMonitorList.add(tempResourceID);
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 10426 */         HashMap<String, HashMap<String, String>> dependentList = AMCacheHandler.getDependentDevice(resourceId);
/* 10427 */         if (dependentList != null)
/*       */         {
/* 10429 */           Iterator itr = dependentList.keySet().iterator();
/* 10430 */           while (itr.hasNext())
/*       */           {
/* 10432 */             String selectedMonitors = (String)itr.next();
/* 10433 */             if (dependentDeviceStr.equals(""))
/*       */             {
/* 10435 */               dependentDeviceStr = "'" + selectedMonitors + "'";
/*       */             }
/*       */             else
/*       */             {
/* 10439 */               dependentDeviceStr = dependentDeviceStr + ",'" + selectedMonitors + "'";
/*       */             }
/* 10441 */             selectedDependentMonitorList.add(selectedMonitors);
/*       */           }
/*       */         }
/*       */       }
/* 10445 */       request.setAttribute("selectedDependentMonitorList", selectedDependentMonitorList);
/* 10446 */       if ((EnterpriseUtil.isManagedServer()) && (!dependentDeviceStr.equals("")))
/*       */       {
/* 10448 */         Vector<HashMap<String, String>> list = DependentDeviceUtil.getInstance().getManagedServerResource(resourceId, dependentDeviceStr);
/* 10449 */         if (list != null)
/*       */         {
/* 10451 */           int listSize = list.size();
/* 10452 */           for (int i = 0; i < listSize; i++)
/*       */           {
/* 10454 */             HashMap<String, String> dependentDeviceMap = (HashMap)list.get(i);
/* 10455 */             if ((dependentDeviceMap != null) && ((type.equals(dependentDeviceMap.get("typeName"))) || ("selectactions".equals(type))))
/*       */             {
/* 10457 */               resourceList.add(dependentDeviceMap);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/* 10463 */       temp.append("<table class='lrtbdarkborder' style='background-color:#FFF;' width='100%' border='0'  cellpadding='0' cellspacing='0'>");
/*       */       
/* 10465 */       if (resourceList.isEmpty())
/*       */       {
/* 10467 */         temp.append("<tr><td class='whitegrayborder' colspan='4' align='center'>").append(FormatUtil.getString("am.webclient.nodata.text")).append("</td></tr>");
/*       */       }
/* 10469 */       for (HashMap<String, String> resourceMap : resourceList)
/*       */       {
/* 10471 */         String resourceType = (String)resourceMap.get("type");
/* 10472 */         String displayName = (String)resourceMap.get("displayname");
/* 10473 */         String resourceid = (String)resourceMap.get("resourceid");
/* 10474 */         String imagePath = (String)resourceMap.get("imagepath");
/* 10475 */         String resourceDisplayName = (String)resourceMap.get("typeName");
/* 10476 */         String masServer = (String)resourceMap.get("managedServer");
/*       */         
/* 10478 */         if (resourceType.equalsIgnoreCase("HAI"))
/*       */         {
/* 10480 */           if (this.mo.isSubGroup(result.getString(1)))
/*       */           {
/* 10482 */             resourceType = FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.text");
/*       */           }
/*       */           else
/*       */           {
/* 10486 */             resourceType = FormatUtil.getString("am.webclient.gettingstarted.monitorgroup.text");
/*       */           }
/*       */         }
/*       */         
/* 10490 */         String tempStr = "<input type='checkbox' name='resourceid'";
/* 10491 */         if (selectedDependentMonitorList.contains(resourceid))
/*       */         {
/* 10493 */           tempStr = "<input type='checkbox' name='resourceid'";
/*       */         }
/* 10495 */         temp.append("<tr><td width='60%' align='left' colspan='2' class='whitegrayborder' valign='center'>" + tempStr + " value='" + resourceid + "'/>&nbsp&nbsp&nbsp;" + FormatUtil.getTrimmedText(displayName, 50) + "</td>" + "<td width='20%' class='whitegrayborder'>" + "<img src='" + imagePath + "'>&nbsp;" + resourceDisplayName + "</td>" + "<td align='left' class='whitegrayborder' width='20%'>");
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10503 */         if ((EnterpriseUtil.isManagedServer) || (EnterpriseUtil.isAdminServer))
/*       */         {
/* 10505 */           temp.append(masServer);
/* 10506 */           temp.append("<input type='hidden' id='mas' name='mas' value='" + masServer + "'/>");
/*       */         }
/*       */         else
/*       */         {
/* 10510 */           temp.append("&nbsp");
/*       */         }
/* 10512 */         temp.append("<input type='hidden' id='resourceTypeName' name='resourceTypeName' value='" + resourceDisplayName + "'/>" + "<input type='hidden' id='resourceType' name='resourceType' value='" + resourceType + "'/>" + "<input type='hidden' id='imagePath' name='imagePath' value='" + imagePath + "'/>" + "<input type='hidden' id='resourceName' name='resourceName' value='" + displayName + "'/>" + "</td>");
/*       */         
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10518 */         temp.append("</tr>");
/*       */       }
/* 10520 */       temp.append("</table>");
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10524 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 10528 */       AMConnectionPool.closeStatement(result);
/*       */     }
/*       */     
/* 10531 */     out.println(temp);
/* 10532 */     out.flush();
/* 10533 */     response.setContentType("text/plain");
/* 10534 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward searchDependentMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */   {
/* 10539 */     String query = request.getParameter("query");
/* 10540 */     String resourceId = request.getParameter("haid");
/* 10541 */     ArrayList allMonitors = new ArrayList();
/* 10542 */     ArrayList<String> types = getAllMonitorTypes();
/* 10543 */     ArrayList selectedDependentMonitorList = new ArrayList();
/* 10544 */     ArrayList searchResults = null;
/* 10545 */     HashMap<String, HashMap<String, String>> dependentList = AMCacheHandler.getDependentDevice(resourceId);
/*       */     
/* 10547 */     if (dependentList != null)
/*       */     {
/* 10549 */       Iterator itr = dependentList.keySet().iterator();
/* 10550 */       while (itr.hasNext())
/*       */       {
/* 10552 */         String selectedMonitors = (String)itr.next();
/* 10553 */         selectedDependentMonitorList.add(selectedMonitors);
/*       */       }
/*       */     }
/* 10556 */     request.setAttribute("selectedDependentMonitorList", selectedDependentMonitorList);
/*       */     try
/*       */     {
/* 10559 */       if (EnterpriseUtil.isAdminServer())
/*       */       {
/* 10561 */         searchResults = DependentDeviceUtil.getInstance().convertJSONArraytoArrayList(new JSONArray(com.adventnet.appmanager.utils.client.CommonAPIUtil.searchMO(query)));
/*       */       }
/*       */       else
/*       */       {
/* 10565 */         searchResults = DependentDeviceUtil.getInstance().getSearchResults(query);
/*       */       }
/* 10567 */       if (searchResults != null)
/*       */       {
/* 10569 */         for (Object resourceInfo : searchResults)
/*       */         {
/* 10571 */           HashMap resourceMap = (HashMap)resourceInfo;
/* 10572 */           if (resourceMap.get("message") == null)
/*       */           {
/*       */ 
/*       */ 
/* 10576 */             String type = (String)resourceMap.get("Type");
/* 10577 */             String resId = (String)resourceMap.get("ResourceId");
/* 10578 */             HashMap map = new HashMap();
/* 10579 */             map.put("type", type);
/* 10580 */             map.put("typeName", type);
/* 10581 */             map.put("resourceid", resId);
/* 10582 */             map.put("displayname", (String)resourceMap.get("DisplayName"));
/* 10583 */             if (EnterpriseUtil.isManagedServer)
/*       */             {
/* 10585 */               map.put("managedServer", FormatUtil.getString("Local Host"));
/*       */             }
/* 10587 */             else if (EnterpriseUtil.isAdminServer())
/*       */             {
/* 10589 */               map.put("managedServer", CommDBUtil.getManagedServerNameWithPort(resId));
/*       */             }
/*       */             else
/*       */             {
/* 10593 */               map.put("managedServer", "");
/*       */             }
/*       */             
/* 10596 */             if ((!"HAI".equalsIgnoreCase(type)) && ((resId == null) || (!resId.equals(resourceId))))
/*       */             {
/*       */ 
/*       */ 
/* 10600 */               resourceMap.put("type", type);
/* 10601 */               if (resourceMap.get("ImagePath") == null)
/*       */               {
/* 10603 */                 String[] resourcetype = com.adventnet.appmanager.util.ReportDataUtilities.getImagePath(type).split("@");
/*       */                 
/*       */ 
/* 10606 */                 map.put("imagepath", resourcetype[0]);
/* 10607 */                 map.put("typeName", resourcetype[1]);
/*       */               }
/*       */               else
/*       */               {
/* 10611 */                 map.put("imagepath", (String)resourceMap.get("ImagePath"));
/*       */               }
/* 10613 */               allMonitors.add(map);
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */     } catch (Exception e) {
/* 10619 */       e.printStackTrace();
/*       */     }
/* 10621 */     if (EnterpriseUtil.isAdminServer())
/*       */     {
/* 10623 */       ArrayList<String> allManagedServers = EnterpriseUtil.getAllManagedServers();
/* 10624 */       if (allManagedServers != null)
/*       */       {
/* 10626 */         request.setAttribute("MAS", allManagedServers);
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 10631 */     if (allMonitors.size() > 0)
/*       */     {
/* 10633 */       request.setAttribute("allMonitors", allMonitors);
/*       */     }
/* 10635 */     Collections.sort(types, String.CASE_INSENSITIVE_ORDER);
/* 10636 */     request.setAttribute("types", types);
/* 10637 */     request.setAttribute("haid", resourceId);
/* 10638 */     return new ActionForward("/jsp/SelectDependentMonitor.jsp");
/*       */   }
/*       */   
/*       */ 
/*       */   public ActionForward selectMonitorFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 10645 */     ResultSet result = null;
/* 10646 */     PrintWriter out = response.getWriter();
/* 10647 */     StringBuffer temp = new StringBuffer();
/* 10648 */     String haid = request.getParameter("haid");
/* 10649 */     String selectedType = request.getParameter("type");
/* 10650 */     String attributeType = request.getParameter("attributetype");
/* 10651 */     ArrayList<HashMap<String, String>> childMonitorList = null;
/* 10652 */     String qry = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME AS TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType WHERE AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedResourceType.RESOURCETYPE NOT IN ('Network','Node','HAI') ORDER BY AM_ManagedObject.DISPLAYNAME";
/* 10653 */     if ((request.getParameter("type") != null) && (request.getParameter("type").equals("selectactions")))
/*       */     {
/* 10655 */       StringBuilder queryBuilder = new StringBuilder();
/*       */       
/* 10657 */       queryBuilder.append("SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME AS TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER WHERE AM_PARENTCHILDMAPPER.PARENTID=").append(haid).append(" AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE");
/*       */       
/*       */ 
/* 10660 */       queryBuilder.append(" UNION SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_MG_DEPENDENTGROUPS WHERE AM_ManagedObject.RESOURCEID=AM_MG_DEPENDENTGROUPS.DEPENDENTGROUPID and AM_MG_DEPENDENTGROUPS.HAID=").append(haid).append(" and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ORDER BY DISPLAYNAME");
/* 10661 */       qry = queryBuilder.toString();
/*       */       
/* 10663 */       ArrayList<String> childTypes = new ArrayList();
/* 10664 */       HashMap<String, String> childMonitorTypes = ChildMOHandler.getChildMOTypes();
/* 10665 */       Iterator itr = childMonitorTypes.keySet().iterator();
/* 10666 */       String childTypesStr = "";
/* 10667 */       while (itr.hasNext())
/*       */       {
/* 10669 */         String key = (String)itr.next();
/* 10670 */         String value = (String)childMonitorTypes.get(key);
/* 10671 */         if ((!"2".equals(attributeType)) || (!value.equals("Service")))
/*       */         {
/*       */ 
/*       */ 
/* 10675 */           childTypes.add(key);
/* 10676 */           if (childTypesStr.equals(""))
/*       */           {
/* 10678 */             childTypesStr = value;
/*       */           }
/*       */           else
/*       */           {
/* 10682 */             childTypesStr = childTypesStr + "," + value; }
/*       */         }
/*       */       }
/* 10685 */       if ("1".equals(attributeType))
/*       */       {
/* 10687 */         childMonitorList = ChildMOHandler.listChildMonitorUnderMG(haid);
/*       */       }
/*       */       else
/*       */       {
/* 10691 */         childMonitorList = ChildMOHandler.listChildMonitorUnderMG(haid, childTypesStr);
/*       */       }
/*       */     }
/* 10694 */     else if ((selectedType != null) && (selectedType.equals("am.webclient.rule.type.dependent.group")))
/*       */     {
/* 10696 */       qry = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_MG_DEPENDENTGROUPS WHERE AM_ManagedObject.RESOURCEID=AM_MG_DEPENDENTGROUPS.DEPENDENTGROUPID and AM_MG_DEPENDENTGROUPS.HAID=" + haid + " and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ORDER BY AM_ManagedObject.DISPLAYNAME";
/*       */     }
/* 10698 */     else if (((selectedType != null) && (selectedType.equals("am.webclient.rule.type.process"))) || (selectedType.equals("am.webclient.rule.type.service")))
/*       */     {
/* 10700 */       childMonitorList = ChildMOHandler.selectChildMonitorFilter(haid, selectedType);
/*       */     }
/* 10702 */     else if (selectedType != null)
/*       */     {
/* 10704 */       qry = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME AS DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME AS TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER WHERE AM_PARENTCHILDMAPPER.PARENTID=" + haid + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedResourceType.DISPLAYNAME ='" + selectedType + "' ORDER BY AM_ManagedObject.DISPLAYNAME";
/*       */     }
/*       */     try
/*       */     {
/* 10708 */       boolean isDataExists = false;
/* 10709 */       temp.append("<table class='lrtbdarkborder' style='background-color:#FFF;' width='100%' border='0'  cellpadding='0' cellspacing='0'>");
/* 10710 */       if ((!"am.webclient.rule.type.process".equals(selectedType)) && (!"am.webclient.rule.type.service".equals(selectedType)))
/*       */       {
/* 10712 */         AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt(qry);
/* 10713 */         String resourceType = "";
/* 10714 */         while (result.next())
/*       */         {
/* 10716 */           resourceType = result.getString(3);
/* 10717 */           isDataExists = true;
/* 10718 */           if (resourceType.equalsIgnoreCase("HAI"))
/*       */           {
/* 10720 */             resourceType = FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.text");
/*       */           }
/* 10722 */           temp.append("<tr><td width='70%' align='left' colspan='2' class='whitegrayborder' valign='center'><input type='checkbox' id='selectedMonitorsFromAjax' name='selectedMonitorsFromAjax' value=" + result.getString(1) + ">&nbsp&nbsp&nbsp;" + FormatUtil.getTrimmedText(result.getString(2), 50) + "<input type='hidden' id='namesFromAjax' value='" + FormatUtil.getTrimmedText(result.getString(2), 40) + "'>" + "</td>" + "<td width='30%' class='whitegrayborder'>" + "<img src='" + result.getString(4) + "'>&nbsp;" + resourceType + "<input type='hidden' id='imagePathsFromAjax' value='" + result.getString(4) + "'>" + "</td>" + "</tr>");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10735 */       if (childMonitorList != null)
/*       */       {
/* 10737 */         for (HashMap<String, String> childMonitorMap : childMonitorList)
/*       */         {
/* 10739 */           isDataExists = true;
/* 10740 */           temp.append("<tr><td width='70%' align='left' colspan='2' class='whitegrayborder' valign='center'><input type='checkbox' id='selectedMonitorsFromAjax' name='selectedMonitorsFromAjax' value=" + (String)childMonitorMap.get("resourceid") + ">&nbsp&nbsp&nbsp;" + FormatUtil.getTrimmedText((String)childMonitorMap.get("displayname"), 50) + "<input type='hidden' id='namesFromAjax' value='" + FormatUtil.getTrimmedText((String)childMonitorMap.get("displayname"), 40) + "'>" + "</td>" + "<td width='30%' class='whitegrayborder'>" + "<img src='" + (String)childMonitorMap.get("imagepath") + "'>&nbsp;" + (String)childMonitorMap.get("type") + "<input type='hidden' id='imagePathsFromAjax' value='" + (String)childMonitorMap.get("imagepath") + "'>" + "</td>" + "</tr>");
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/* 10752 */       if (!isDataExists)
/*       */       {
/* 10754 */         temp.append("<tr><td width='70%' align='left' colspan='2' class='whitegrayborder' valign='center'>" + FormatUtil.getString("am.monitortab.nomonitors.text") + "</td><tr>");
/*       */       }
/* 10756 */       temp.append("</table>");
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10760 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 10764 */       AMConnectionPool.closeStatement(result);
/*       */     }
/* 10766 */     out.println(temp);
/* 10767 */     out.flush();
/* 10768 */     response.setContentType("text/plain");
/* 10769 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward getAlarmDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*       */   {
/* 10774 */     int type = Integer.parseInt(request.getParameter("type"));
/* 10775 */     String resourceId = request.getParameter("resourceId");
/* 10776 */     String attributeId = request.getParameter("attributeId");
/* 10777 */     response.setContentType("text/html;charset=utf-8");
/* 10778 */     PrintWriter out = response.getWriter();
/* 10779 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 10780 */     ArrayList<String> criticalActions = new ArrayList();
/* 10781 */     ArrayList<String> warningActions = new ArrayList();
/* 10782 */     ArrayList<String> clearActions = new ArrayList();
/* 10783 */     int attributeType = 2;
/* 10784 */     if (DBUtil.getAttributeDetails(attributeId)[0].equals("1"))
/*       */     {
/* 10786 */       attributeType = 1;
/*       */     }
/* 10788 */     StringBuffer temp = null;
/* 10789 */     ResultSet rs = null;
/*       */     
/*       */ 
/*       */     try
/*       */     {
/* 10794 */       if (type == 1)
/*       */       {
/* 10796 */         temp = new StringBuffer();
/* 10797 */         rs = AMConnectionPool.executeQueryStmt("SELECT AM_ACTIONPROFILE.NAME,SEVERITY FROM AM_ATTRIBUTEACTIONMAPPER,AM_ACTIONPROFILE WHERE AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " AND AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE=" + attributeId + " AND AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID");
/* 10798 */         while (rs.next())
/*       */         {
/* 10800 */           if (rs.getInt("SEVERITY") == 1)
/*       */           {
/* 10802 */             criticalActions.add(rs.getString(1));
/*       */           }
/* 10804 */           else if (rs.getInt("SEVERITY") == 4)
/*       */           {
/* 10806 */             warningActions.add(rs.getString(1));
/*       */           }
/*       */           else
/*       */           {
/* 10810 */             clearActions.add(rs.getString(1));
/*       */           }
/*       */         }
/* 10813 */         if (criticalActions.size() > 0)
/*       */         {
/* 10815 */           if (attributeType == 1)
/*       */           {
/* 10817 */             temp.append("<b>" + FormatUtil.getString("Down") + "</b>");
/*       */           }
/*       */           else
/*       */           {
/* 10821 */             temp.append("<b>" + FormatUtil.getString("Critical") + "</b>");
/*       */           }
/* 10823 */           for (String actions : criticalActions)
/*       */           {
/* 10825 */             temp.append(actions.toString() + ",");
/*       */           }
/* 10827 */           temp.deleteCharAt(temp.length() - 1);
/* 10828 */           temp.append("<br>");
/*       */         }
/*       */         
/* 10831 */         if (warningActions.size() > 0)
/*       */         {
/* 10833 */           temp.append("<b>" + FormatUtil.getString("Warning") + "</b>");
/* 10834 */           for (String actions : warningActions)
/*       */           {
/* 10836 */             temp.append(actions.toString() + ",");
/*       */           }
/* 10838 */           temp.deleteCharAt(temp.length() - 1);
/* 10839 */           temp.append("<br>");
/*       */         }
/*       */         
/* 10842 */         if (clearActions.size() > 0)
/*       */         {
/* 10844 */           if (attributeType == 1)
/*       */           {
/* 10846 */             temp.append("<b>" + FormatUtil.getString("Up") + "</b>");
/*       */           }
/*       */           else
/*       */           {
/* 10850 */             temp.append("<b>" + FormatUtil.getString("Clear") + "</b>");
/*       */           }
/* 10852 */           for (String actions : clearActions)
/*       */           {
/* 10854 */             temp.append(actions.toString() + ",");
/*       */           }
/* 10856 */           temp.deleteCharAt(temp.length() - 1);
/* 10857 */           temp.append("<br>");
/*       */         }
/*       */       }
/*       */       else
/*       */       {
/* 10862 */         if (type == 2)
/*       */         {
/* 10864 */           temp = new StringBuffer();
/* 10865 */           rs = AMConnectionPool.executeQueryStmt("SELECT AM_HA_RULE_CONDITION_CONFIG.MESSAGE FROM AM_HARULE,AM_HA_RULE_CONDITION_CONFIG WHERE HAID=" + resourceId + " AND  ATTRIBUTE_AFFECTED=" + attributeType + " AND AM_HARULE.RULEID=AM_HA_RULE_CONDITION_CONFIG.RULEID");
/* 10866 */           while (rs.next())
/*       */           {
/* 10868 */             temp.append(rs.getString("MESSAGE") + "<br>");
/*       */           }
/*       */         }
/*       */         
/*       */ 
/*       */ 
/* 10874 */         temp = new StringBuffer();
/* 10875 */         rs = AMConnectionPool.executeQueryStmt("SELECT AM_ManagedObject.DISPLAYNAME FROM AM_DEPENDENTMONITOR,AM_ManagedObject WHERE AM_DEPENDENTMONITOR.CHILDID=" + resourceId + " AND AM_DEPENDENTMONITOR.PARENTID=AM_ManagedObject.RESOURCEID");
/* 10876 */         while (rs.next())
/*       */         {
/* 10878 */           temp.append(rs.getString("DISPLAYNAME") + "<br>");
/*       */         }
/*       */       }
/* 10881 */       if ((temp != null) && (temp.length() < 1))
/*       */       {
/* 10883 */         temp.append(FormatUtil.getString("am.webclient.gettingstarted.notconfigured.text"));
/*       */       }
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10888 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 10892 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/* 10894 */     out.println(temp);
/* 10895 */     out.flush();
/* 10896 */     return null;
/*       */   }
/*       */   
/*       */   public ActionForward getSelectedMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */   {
/*       */     try
/*       */     {
/* 10903 */       String[] allSelectedMonitors = null;
/* 10904 */       String listSelectedMonitors = null;
/* 10905 */       if (request.getParameter("allSelectedMonitors") != null)
/*       */       {
/* 10907 */         listSelectedMonitors = request.getParameter("allSelectedMonitors");
/* 10908 */         allSelectedMonitors = listSelectedMonitors.split(",");
/*       */       }
/* 10910 */       response.setContentType("text/html;charset=utf-8");
/* 10911 */       PrintWriter out = response.getWriter();
/* 10912 */       StringBuffer toReturn = new StringBuffer();
/* 10913 */       if ((allSelectedMonitors != null) && (!"".equals(allSelectedMonitors[0])))
/*       */       {
/* 10915 */         toReturn.append("<ol>");
/* 10916 */         for (String resourceId : allSelectedMonitors)
/*       */         {
/* 10918 */           if (!resourceId.equals(""))
/*       */           {
/*       */ 
/*       */ 
/* 10922 */             toReturn.append("<li>" + DBUtil.getDisplaynameforResourceID(resourceId) + "</li>"); }
/*       */         }
/* 10924 */         toReturn.append("</ol>");
/*       */       }
/*       */       else
/*       */       {
/* 10928 */         toReturn.append(FormatUtil.getString("am.selectedmonitors.noMO.text"));
/*       */       }
/* 10930 */       out.println(toReturn);
/* 10931 */       out.flush();
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 10935 */       e.printStackTrace();
/*       */     }
/* 10937 */     return null;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public void listXenServerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 10950 */     DashboardUtil.createXenServerInfraDashboard();
/* 10951 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 10952 */     ResultSet rs = null;
/* 10953 */     String role = "";
/* 10954 */     String owner = request.getRemoteUser();
/* 10955 */     if (request.isUserInRole("OPERATOR"))
/*       */     {
/* 10957 */       role = "operator";
/*       */     }
/*       */     else
/*       */     {
/* 10961 */       role = "user";
/*       */     }
/* 10963 */     String condition = "";
/* 10964 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/* 10966 */       condition = ReportUtilities.getQueryCondition("RESOURCEID", owner, request);
/*       */     }
/*       */     else
/*       */     {
/* 10970 */       condition = ReportUtilities.getQueryCondition("RESOURCEID", owner);
/*       */     }
/* 10972 */     String dataTable = null;
/* 10973 */     Hashtable retDataHash = new Hashtable();
/* 10974 */     String status_sep = "#";
/* 10975 */     String rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID in (15005,15010,15003,15004,15009)";
/*       */     try {
/* 10977 */       rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 10978 */       if (rs.next())
/*       */       {
/* 10980 */         dataTable = rs.getString("DATATABLE");
/*       */       }
/* 10982 */       if (dataTable != null)
/*       */       {
/* 10984 */         String dataQuery = "SELECT RESOURCEID,CPUUtilInPercentage,UsedMemoryInPercentage,NumofVMs,NumofCPUCore,UsedMemory,COLLECTIONTIME FROM " + dataTable + "  ORDER BY COLLECTIONTIME DESC";
/* 10985 */         String[] columns = { "CPUUtilInPercentage", "UsedMemoryInPercentage", "NumofVMs", "NumofCPUCore", "UsedMemory" };
/* 10986 */         if (DBQueryUtil.isPgsql())
/*       */         {
/* 10988 */           dataQuery = "SELECT RESOURCEID,\"CPUUtilInPercentage\",\"UsedMemoryInPercentage\",\"NumofVMs\",\"NumofCPUCore\",\"UsedMemory\",COLLECTIONTIME FROM " + dataTable + "  ORDER BY COLLECTIONTIME DESC";
/*       */         }
/* 10990 */         HashMap atnameidMap = DBUtil.getAttidsForAttnames("XenServerHost", columns);
/* 10991 */         if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/* 10993 */           dataQuery = "SELECT RESOURCEID,CPUUtilInPercentage,UsedMemoryInPercentage,NumofVMs,NumofCPUCore,UsedMemory,COLLECTIONTIME FROM " + dataTable + " and " + condition + " order by COLLECTIONTIME desc";
/* 10994 */           if (DBQueryUtil.isPgsql())
/*       */           {
/*       */ 
/* 10997 */             dataQuery = "SELECT RESOURCEID,\"CPUUtilInPercentage\",\"UsedMemoryInPercentage\",\"NumofVMs\",\"NumofCPUCore\",\"UsedMemory\",COLLECTIONTIME FROM " + dataTable + " and " + condition + " order by COLLECTIONTIME desc";
/*       */           }
/*       */         }
/*       */         
/* 11001 */         rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11002 */         while (rs.next())
/*       */         {
/* 11004 */           for (String col : columns)
/*       */           {
/* 11006 */             String key = rs.getString("RESOURCEID") + status_sep + atnameidMap.get(col);
/* 11007 */             if ((retDataHash.get(key) == null) && (rs.getString(col) != null))
/*       */             {
/* 11009 */               retDataHash.put(key, rs.getString(col));
/*       */             }
/*       */             
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 11019 */       ee.printStackTrace();
/*       */     }
/*       */     
/* 11022 */     rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID in (15509,15513,15510)";
/*       */     try {
/* 11024 */       rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 11025 */       if (rs.next())
/*       */       {
/* 11027 */         dataTable = rs.getString("DATATABLE");
/*       */       }
/* 11029 */       if (dataTable != null)
/*       */       {
/* 11031 */         String[] columns = { "OverAllCPUUtilInPercentage", "UsedMemoryInPercentage", "NumVCPU" };
/* 11032 */         HashMap atnameidMap = DBUtil.getAttidsForAttnames("XenServerVM", columns);
/* 11033 */         String dataQuery = "select RESOURCEID,OverAllCPUUtilInPercentage,UsedMemoryInPercentage,NumVCPU,COLLECTIONTIME from " + dataTable + "  order by COLLECTIONTIME desc";
/* 11034 */         if (DBQueryUtil.isPgsql())
/*       */         {
/* 11036 */           dataQuery = "select RESOURCEID,\"OverAllCPUUtilInPercentage\",\"UsedMemoryInPercentage\",\"NumVCPU\",COLLECTIONTIME from " + dataTable + "  order by COLLECTIONTIME desc";
/*       */         }
/* 11038 */         if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/* 11040 */           dataQuery = "select RESOURCEID,OverAllCPUUtilInPercentage,UsedMemoryInPercentage,NumVCPU,COLLECTIONTIME from " + dataTable + "  and " + condition + " order by COLLECTIONTIME desc";
/* 11041 */           if (DBQueryUtil.isPgsql())
/*       */           {
/* 11043 */             dataQuery = "select RESOURCEID,\"OverAllCPUUtilInPercentage\",\"UsedMemoryInPercentage\",\"NumVCPU\",COLLECTIONTIME from " + dataTable + "  and " + condition + " order by COLLECTIONTIME desc";
/*       */           }
/*       */         }
/* 11046 */         rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11047 */         while (rs.next())
/*       */         {
/* 11049 */           for (String col : columns)
/*       */           {
/* 11051 */             String key = rs.getString("RESOURCEID") + status_sep + atnameidMap.get(col);
/* 11052 */             if ((retDataHash.get(key) == null) && (rs.getString(col) != null))
/*       */             {
/* 11054 */               retDataHash.put(key, rs.getString(col));
/*       */             }
/*       */             
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 11064 */       ee.printStackTrace();
/*       */     }
/*       */     
/* 11067 */     request.setAttribute("listView", retDataHash);
/* 11068 */     Hashtable guestHostHash = new Hashtable();
/* 11069 */     String query = "select RESOURCEID,DISPLAYNAME,PARENTID from AM_ManagedObject,AM_PARENTCHILDMAPPER where CHILDID=RESOURCEID and TYPE='XenServerVM' order by PARENTID,DISPLAYNAME";
/* 11070 */     if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/* 11072 */       query = "select RESOURCEID,DISPLAYNAME,PARENTID from AM_ManagedObject,AM_PARENTCHILDMAPPER where RESOURCEID=CHILDID and TYPE='XenServerVM' and " + condition + " order by PARENTID,DISPLAYNAME";
/*       */     }
/*       */     try
/*       */     {
/* 11076 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 11077 */       while (rs.next())
/*       */       {
/* 11079 */         String curParID = rs.getString("PARENTID");
/* 11080 */         String rKey = rs.getString("RESOURCEID") + "##" + rs.getString("DISPLAYNAME");
/* 11081 */         if (guestHostHash.get(curParID) != null)
/*       */         {
/* 11083 */           ArrayList guestList = (ArrayList)guestHostHash.get(curParID);
/* 11084 */           guestList.add(rKey);
/*       */         }
/*       */         else
/*       */         {
/* 11088 */           ArrayList guestList = new ArrayList();
/* 11089 */           guestHostHash.put(curParID, guestList);
/* 11090 */           guestList.add(rKey);
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 11096 */       ee.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 11100 */       if (rs != null)
/*       */       {
/* 11102 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/* 11105 */     String childListQuery = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE='XenServerVM'";
/* 11106 */     if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/* 11108 */       childListQuery = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE='XenServerVM' and " + condition;
/*       */     }
/*       */     
/* 11111 */     List childList = this.mo.getRows(childListQuery);
/* 11112 */     request.setAttribute("GuesHostMapping", guestHostHash);
/* 11113 */     request.setAttribute("ChildList", childList);
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public void listESXServerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 11121 */     DashboardUtil.createVirtualInfraDashboard();
/* 11122 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 11123 */     ResultSet rs = null;
/* 11124 */     String resourcetype = request.getParameter("network");
/* 11125 */     String rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID in (7520,7522,7523,7524)";
/*       */     
/* 11127 */     String role = "";
/* 11128 */     String owner = request.getRemoteUser();
/* 11129 */     if (ClientDBUtil.isPrivilegedUser(request))
/*       */     {
/* 11131 */       role = "operator";
/*       */     }
/*       */     else
/*       */     {
/* 11135 */       role = "user";
/*       */     }
/* 11137 */     String condition = "";
/* 11138 */     if (EnterpriseUtil.isIt360MSPEdition())
/*       */     {
/* 11140 */       condition = ReportUtilities.getQueryCondition("RESOURCEID", owner, request);
/*       */     }
/*       */     else
/*       */     {
/* 11144 */       condition = ReportUtilities.getQueryCondition("RESOURCEID", owner);
/*       */     }
/*       */     
/* 11147 */     String dataTable = null;
/* 11148 */     Hashtable retDataHash = new Hashtable();
/* 11149 */     String status_sep = "#";
/*       */     try {
/* 11151 */       rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 11152 */       if (rs.next())
/*       */       {
/* 11154 */         dataTable = rs.getString("DATATABLE");
/*       */       }
/* 11156 */       if (dataTable != null)
/*       */       {
/*       */ 
/* 11159 */         String dataQuery = "SELECT RESOURCEID,CPUUtil,MemUtil,DiskUsage,NetUsage,COLLECTIONTIME FROM " + dataTable + "  ORDER BY COLLECTIONTIME DESC";
/* 11160 */         String[] columns = { "CPUUtil", "MemUtil", "DiskUsage", "NetUsage" };
/* 11161 */         if (DBQueryUtil.isPgsql())
/*       */         {
/* 11163 */           dataQuery = "SELECT RESOURCEID,\"CPUUtil\",\"MemUtil\",\"DiskUsage\",\"NetUsage\",COLLECTIONTIME FROM " + dataTable + "  ORDER BY COLLECTIONTIME DESC";
/*       */         }
/* 11165 */         HashMap atnameidMap = DBUtil.getAttidsForAttnames("VMWare ESX/ESXi", columns);
/* 11166 */         if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/* 11168 */           dataQuery = "SELECT RESOURCEID,CPUUtil,MemUtil,DiskUsage,NetUsage,COLLECTIONTIME FROM " + dataTable + " where " + condition + " order by COLLECTIONTIME desc";
/* 11169 */           if (DBQueryUtil.isPgsql())
/*       */           {
/*       */ 
/* 11172 */             dataQuery = "SELECT RESOURCEID,\"CPUUtil\",\"MemUtil\",\"DiskUsage\",\"NetUsage\",COLLECTIONTIME FROM " + dataTable + " where " + condition + " order by COLLECTIONTIME desc";
/*       */           }
/*       */         }
/*       */         
/* 11176 */         rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11177 */         while (rs.next())
/*       */         {
/* 11179 */           for (String col : columns)
/*       */           {
/* 11181 */             String key = rs.getString("RESOURCEID") + status_sep + atnameidMap.get(col);
/* 11182 */             if ((retDataHash.get(key) == null) && (rs.getString(col) != null))
/*       */             {
/* 11184 */               retDataHash.put(key, rs.getString(col));
/*       */             }
/*       */             
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 11194 */       ee.printStackTrace();
/*       */     }
/*       */     
/* 11197 */     rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID in (7624,7626,7627,7628)";
/*       */     try {
/* 11199 */       rs = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 11200 */       if (rs.next())
/*       */       {
/* 11202 */         dataTable = rs.getString("DATATABLE");
/*       */       }
/* 11204 */       if (dataTable != null)
/*       */       {
/* 11206 */         String[] columns = { "AvgCPUUtil", "MemUtil", "AvgDiskUtil", "AvgNetUtil" };
/* 11207 */         HashMap atnameidMap = DBUtil.getAttidsForAttnames("VirtualMachine", columns);
/* 11208 */         String dataQuery = "select RESOURCEID,AvgCPUUtil,MemUtil,AvgDiskUtil,AvgNetUtil,COLLECTIONTIME from " + dataTable + "  order by COLLECTIONTIME desc";
/* 11209 */         if (DBQueryUtil.isPgsql())
/*       */         {
/* 11211 */           dataQuery = "select RESOURCEID,\"AvgCPUUtil\",\"MemUtil\",\"AvgDiskUtil\",\"AvgNetUtil\",COLLECTIONTIME from " + dataTable + "  order by COLLECTIONTIME desc";
/*       */         }
/* 11213 */         if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */         {
/* 11215 */           dataQuery = "select RESOURCEID,AvgCPUUtil,MemUtil,AvgDiskUtil,AvgNetUtil,COLLECTIONTIME from " + dataTable + "  where " + condition + " order by COLLECTIONTIME desc";
/* 11216 */           if (DBQueryUtil.isPgsql())
/*       */           {
/* 11218 */             dataQuery = "select RESOURCEID,\"AvgCPUUtil\",\"MemUtil\",\"AvgDiskUtil\",\"AvgNetUtil\",COLLECTIONTIME from " + dataTable + "  where " + condition + " order by COLLECTIONTIME desc";
/*       */           }
/*       */         }
/* 11221 */         rs = AMConnectionPool.executeQueryStmt(dataQuery);
/* 11222 */         while (rs.next())
/*       */         {
/* 11224 */           for (String col : columns)
/*       */           {
/* 11226 */             String key = rs.getString("RESOURCEID") + status_sep + atnameidMap.get(col);
/* 11227 */             if ((retDataHash.get(key) == null) && (rs.getString(col) != null))
/*       */             {
/* 11229 */               retDataHash.put(key, rs.getString(col));
/*       */             }
/*       */             
/*       */           }
/*       */           
/*       */         }
/*       */       }
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 11239 */       ee.printStackTrace();
/*       */     }
/*       */     
/* 11242 */     request.setAttribute("listView", retDataHash);
/*       */     
/* 11244 */     Hashtable guestHostHash = new Hashtable();
/* 11245 */     String pcMapQuery = "select RESOURCEID,DISPLAYNAME,PARENTID from AM_ManagedObject,AM_PARENTCHILDMAPPER where CHILDID=RESOURCEID and TYPE='VirtualMachine' order by PARENTID,DISPLAYNAME";
/* 11246 */     if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/* 11248 */       pcMapQuery = "select RESOURCEID,DISPLAYNAME,PARENTID from AM_ManagedObject,AM_PARENTCHILDMAPPER where RESOURCEID=CHILDID and TYPE='VirtualMachine' and " + condition + " order by PARENTID,DISPLAYNAME";
/*       */     }
/*       */     try
/*       */     {
/* 11252 */       rs = AMConnectionPool.executeQueryStmt(pcMapQuery);
/* 11253 */       while (rs.next())
/*       */       {
/* 11255 */         String curParID = rs.getString("PARENTID");
/* 11256 */         String rKey = rs.getString("RESOURCEID") + "##" + rs.getString("DISPLAYNAME");
/* 11257 */         if (guestHostHash.get(curParID) != null)
/*       */         {
/* 11259 */           ArrayList guestList = (ArrayList)guestHostHash.get(curParID);
/* 11260 */           guestList.add(rKey);
/*       */ 
/*       */         }
/*       */         else
/*       */         {
/* 11265 */           ArrayList guestList = new ArrayList();
/* 11266 */           guestHostHash.put(curParID, guestList);
/* 11267 */           guestList.add(rKey);
/*       */ 
/*       */         }
/*       */         
/*       */       }
/*       */       
/*       */ 
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/*       */ 
/* 11278 */       ee.printStackTrace();
/*       */     }
/*       */     finally {
/* 11281 */       if (rs != null)
/*       */       {
/* 11283 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     }
/* 11286 */     String childListQuery = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE='VirtualMachine'";
/* 11287 */     if (("operator".equals(role)) || (EnterpriseUtil.isIt360MSPEdition()))
/*       */     {
/* 11289 */       childListQuery = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.TYPE='VirtualMachine' and " + condition;
/*       */     }
/*       */     
/* 11292 */     List childList = this.mo.getRows(childListQuery);
/* 11293 */     request.setAttribute("GuesHostMapping", guestHostHash);
/* 11294 */     request.setAttribute("ChildList", childList);
/*       */   }
/*       */   
/*       */   private ArrayList<String> getAllMonitorTypes()
/*       */   {
/* 11299 */     ResultSet rs = null;
/* 11300 */     ArrayList<String> toReturn = new ArrayList();
/*       */     try
/*       */     {
/* 11303 */       rs = AMConnectionPool.executeQueryStmt("SELECT Distinct AM_ManagedResourceType.DISPLAYNAME FROM AM_ManagedObject,AM_ManagedResourceType WHERE AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedResourceType.RESOURCETYPE NOT IN ('Network','Node','HAI')");
/* 11304 */       while (rs.next())
/*       */       {
/* 11306 */         toReturn.add(rs.getString("DISPLAYNAME"));
/*       */       }
/*       */     }
/*       */     catch (SQLException e)
/*       */     {
/* 11311 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/* 11315 */       AMConnectionPool.closeStatement(rs);
/*       */     }
/* 11317 */     return toReturn;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private String getParentName(String pid)
/*       */   {
/* 11618 */     String name = "";
/* 11619 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 11620 */     ResultSet rs = null;
/*       */     try
/*       */     {
/* 11623 */       String q = "select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + Integer.parseInt(pid);
/* 11624 */       AMLog.debug("query = " + q);
/* 11625 */       rs = AMConnectionPool.executeQueryStmt(q);
/* 11626 */       if (rs.next())
/*       */       {
/* 11628 */         name = rs.getString(1);
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       try
/*       */       {
/* 11639 */         if (rs != null)
/*       */         {
/* 11641 */           rs.close();
/*       */         }
/*       */       }
/*       */       catch (Exception ee)
/*       */       {
/* 11646 */         ee.printStackTrace();
/*       */       }
/*       */       
/* 11649 */       AMLog.debug("parentname " + name + " for id " + pid);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 11633 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*       */       try
/*       */       {
/* 11639 */         if (rs != null)
/*       */         {
/* 11641 */           rs.close();
/*       */         }
/*       */       }
/*       */       catch (Exception ee)
/*       */       {
/* 11646 */         ee.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/* 11650 */     return name;
/*       */   }
/*       */   
/*       */   private String getParentID(String cid)
/*       */   {
/* 11655 */     String pid = null;
/* 11656 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 11657 */     ResultSet rs = null;
/*       */     try
/*       */     {
/* 11660 */       String q = "select PARENTID from AM_PARENTCHILDMAPPER where CHILDID = " + Integer.parseInt(cid);
/* 11661 */       AMLog.debug("query = " + q);
/* 11662 */       rs = AMConnectionPool.executeQueryStmt(q);
/* 11663 */       if (rs.next())
/*       */       {
/* 11665 */         pid = rs.getInt(1) + "";
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       try
/*       */       {
/* 11676 */         if (rs != null)
/*       */         {
/* 11678 */           rs.close();
/*       */         }
/*       */       }
/*       */       catch (Exception ee)
/*       */       {
/* 11683 */         ee.printStackTrace();
/*       */       }
/*       */       
/* 11686 */       AMLog.debug("parentid " + pid + " for cid " + cid);
/*       */     }
/*       */     catch (Exception e)
/*       */     {
/* 11670 */       e.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*       */       try
/*       */       {
/* 11676 */         if (rs != null)
/*       */         {
/* 11678 */           rs.close();
/*       */         }
/*       */       }
/*       */       catch (Exception ee)
/*       */       {
/* 11683 */         ee.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/* 11687 */     return pid;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   private void listInfraViewDetails(HttpServletRequest request, LinkedHashMap infraview)
/*       */     throws Exception
/*       */   {
/* 12070 */     String resourcetype = request.getParameter("network");
/* 12071 */     String typeId = Constants.getTypeId(resourcetype);
/* 12072 */     Set dataset = infraview.keySet();
/* 12073 */     if (dataset.size() < 4)
/*       */     {
/* 12075 */       AMLog.debug(FormatUtil.getString("am.webclient.nosql.infraview.warning"));
/* 12076 */       return;
/*       */     }
/* 12078 */     if (dataset.size() >= 5)
/*       */     {
/* 12080 */       request.setAttribute("EighthColumn", "true");
/*       */     }
/* 12082 */     Iterator it = dataset.iterator();
/* 12083 */     ArrayList attrList = new ArrayList();
/* 12084 */     ArrayList argumentList = new ArrayList();
/* 12085 */     HashMap ivDetails = new HashMap();
/*       */     
/* 12087 */     int cnt = 0;
/* 12088 */     while (it.hasNext()) {
/* 12089 */       String ivKey = (String)it.next();
/* 12090 */       ivDetails = (HashMap)infraview.get(ivKey);
/* 12091 */       if (((String)ivDetails.get("INFO-TYPE")).equals("FROM-TABLE"))
/*       */       {
/* 12093 */         argumentList.add(ivKey);
/* 12094 */         attrList.add(Integer.valueOf(-1));
/*       */       }
/*       */       else
/*       */       {
/* 12098 */         attrList.add(Integer.valueOf(ivKey));
/*       */       }
/* 12100 */       cnt++;
/* 12101 */       if (cnt == 5) {
/*       */         break;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12107 */     HashMap resIDPropsMap = new HashMap();
/* 12108 */     ArrayList paramlist = new ArrayList();
/* 12109 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 12110 */     ResultSet rs = null;ResultSet rs2 = null;ResultSet rs3 = null;ResultSet rs6 = null;ResultSet rs7 = null;ResultSet rss = null;
/*       */     
/*       */     try
/*       */     {
/* 12114 */       ArrayList dispList = new ArrayList();
/* 12115 */       ArrayList unitList = new ArrayList();
/* 12116 */       ArrayList typeList = new ArrayList();
/* 12117 */       ArrayList attrNameList = new ArrayList();
/* 12118 */       paramlist.add(dispList);
/* 12119 */       paramlist.add(unitList);
/*       */       
/* 12121 */       Iterator it11 = argumentList.iterator();
/* 12122 */       while (it11.hasNext())
/*       */       {
/* 12124 */         String argKey = (String)it11.next();
/* 12125 */         String dispName = argKey;
/* 12126 */         HashMap argsDetails = (HashMap)infraview.get(argKey);
/* 12127 */         if (argsDetails.get("DISPLAY") != null)
/*       */         {
/* 12129 */           dispName = (String)argsDetails.get("DISPLAY");
/*       */         }
/* 12131 */         dispList.add(FormatUtil.getString(dispName));
/* 12132 */         unitList.add("");
/* 12133 */         typeList.add(Integer.valueOf(3));
/* 12134 */         attrNameList.add(argKey);
/*       */       }
/*       */       
/*       */ 
/* 12138 */       Iterator it12 = attrList.iterator();
/* 12139 */       while (it12.hasNext())
/*       */       {
/* 12141 */         int id = ((Integer)it12.next()).intValue();
/* 12142 */         if (id != -1)
/*       */         {
/*       */ 
/*       */ 
/* 12146 */           String q = "select DISPLAYNAME, UNITS, TYPE, ATTRIBUTE from AM_ATTRIBUTES where ATTRIBUTEID = " + id;
/* 12147 */           rs = AMConnectionPool.executeQueryStmt(q);
/* 12148 */           if (rs.next())
/*       */           {
/* 12150 */             String disp = rs.getString(1);
/* 12151 */             String unit = rs.getString(2);
/* 12152 */             if (unit != null)
/*       */             {
/* 12154 */               unit = unit.trim();
/*       */             }
/* 12156 */             if (unit == null)
/*       */             {
/* 12158 */               unit = "";
/*       */             }
/* 12160 */             int type = rs.getInt(3);
/* 12161 */             String attrName = rs.getString(4);
/* 12162 */             dispList.add(FormatUtil.getString(disp));
/* 12163 */             unitList.add(unit);
/* 12164 */             typeList.add(Integer.valueOf(type));
/* 12165 */             attrNameList.add(attrName);
/*       */           }
/* 12167 */           rs = null;
/*       */         }
/*       */       }
/* 12170 */       int resTypeId = Integer.parseInt(typeId);
/*       */       
/* 12172 */       if (resTypeId != -1)
/*       */       {
/* 12174 */         String q3 = "select ammo.RESOURCEID, ammo.RESOURCENAME, ammo.DISPLAYNAME from AM_ManagedObject ammo where ammo.TYPE = '" + resourcetype + "'";
/* 12175 */         rs3 = AMConnectionPool.executeQueryStmt(q3);
/* 12176 */         while (rs3.next())
/*       */         {
/* 12178 */           int resId = rs3.getInt(1);
/* 12179 */           String resName = rs3.getString(2);
/* 12180 */           String dispName = rs3.getString(3);
/* 12181 */           HashMap propsMap = new HashMap();
/* 12182 */           propsMap.put("ResourceID", resId + "");
/* 12183 */           propsMap.put("ResourceName", resName);
/* 12184 */           propsMap.put("DisplayName", dispName);
/* 12185 */           resIDPropsMap.put(resId + "", propsMap);
/*       */         }
/*       */         
/* 12188 */         Set kk = resIDPropsMap.keySet();
/* 12189 */         Iterator it22 = kk.iterator();
/* 12190 */         while (it22.hasNext())
/*       */         {
/* 12192 */           String k = (String)it22.next();
/* 12193 */           HashMap pmap = (HashMap)resIDPropsMap.get(k);
/* 12194 */           String q6 = "select max(COLLECTIONTIME) from AM_ManagedObjectData where RESID = " + k;
/* 12195 */           long collectiontime = 0L;
/* 12196 */           rs6 = AMConnectionPool.executeQueryStmt(q6);
/* 12197 */           if (rs6.next())
/*       */           {
/* 12199 */             collectiontime = rs6.getLong(1);
/*       */           }
/* 12201 */           rs6 = null;
/*       */           
/* 12203 */           for (int i = 0; i < argumentList.size(); i++)
/*       */           {
/* 12205 */             String argKey = (String)argumentList.get(i);
/* 12206 */             String dname = (String)dispList.get(i);
/* 12207 */             String q2 = "select " + ConfMonitorUtil.getSpecialCharToAppend() + argKey + ConfMonitorUtil.getSpecialCharToAppend() + " from AM_ARGS_" + resTypeId + " where RESOURCEID=" + k;
/* 12208 */             AMLog.debug("q222 = " + q2);
/* 12209 */             rs2 = AMConnectionPool.executeQueryStmt(q2);
/* 12210 */             if (rs2.next())
/*       */             {
/* 12212 */               String argval = rs2.getString(1);
/* 12213 */               pmap.put(dname, argval);
/*       */             }
/*       */           }
/*       */           
/* 12217 */           for (int i = 0; i < attrList.size(); i++)
/*       */           {
/* 12219 */             int atid = ((Integer)attrList.get(i)).intValue();
/* 12220 */             if (atid != -1)
/*       */             {
/*       */ 
/*       */ 
/* 12224 */               String dname = (String)dispList.get(i);
/* 12225 */               int type = ((Integer)typeList.get(i)).intValue();
/* 12226 */               String attrName = (String)attrNameList.get(i);
/*       */               
/* 12228 */               String rawTable = null;
/* 12229 */               String rawTableQuery = "select DATATABLE from AM_ATTRIBUTES_EXT where ATTRIBUTEID = " + atid;
/* 12230 */               rss = AMConnectionPool.executeQueryStmt(rawTableQuery);
/* 12231 */               if (rss.next())
/*       */               {
/* 12233 */                 rawTable = rss.getString(1);
/*       */               }
/*       */               
/* 12236 */               String q7 = null;
/* 12237 */               if (type == 5)
/*       */               {
/* 12239 */                 q7 = "select CONFVALUE from AM_CONFIGURATION_INFO where ATTRIBUTEID = " + atid + " and RESOURCEID = " + k;
/*       */               }
/* 12241 */               else if (rawTable != null)
/*       */               {
/* 12243 */                 q7 = "select " + ConfMonitorUtil.getSpecialCharToAppend() + attrName + ConfMonitorUtil.getSpecialCharToAppend() + " from " + rawTable + " where RESOURCEID=" + k + " and COLLECTIONTIME=" + collectiontime;
/*       */               }
/* 12245 */               if (q7 != null)
/*       */               {
/* 12247 */                 rs7 = AMConnectionPool.executeQueryStmt(q7);
/* 12248 */                 if (rs7.next())
/*       */                 {
/* 12250 */                   if ((type == 5) || (type == 3))
/*       */                   {
/* 12252 */                     String val = rs7.getString(1);
/* 12253 */                     pmap.put(dname, val);
/*       */                   }
/*       */                   else
/*       */                   {
/* 12257 */                     double val = rs7.getDouble(1);
/* 12258 */                     pmap.put(dname, val + "");
/*       */                   }
/*       */                 }
/* 12261 */                 rs7 = null;
/*       */               }
/*       */             }
/*       */           }
/*       */         }
/*       */       }
/*       */       
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       try
/*       */       {
/* 12275 */         if (rs != null)
/*       */         {
/* 12277 */           rs.close();
/*       */         }
/* 12279 */         if (rs2 != null)
/*       */         {
/* 12281 */           rs2.close();
/*       */         }
/* 12283 */         if (rs3 != null)
/*       */         {
/* 12285 */           rs3.close();
/*       */         }
/* 12287 */         if (rs6 != null)
/*       */         {
/* 12289 */           rs6.close();
/*       */         }
/* 12291 */         if (rs7 != null)
/*       */         {
/* 12293 */           rs7.close();
/*       */         }
/* 12295 */         if (rss != null)
/*       */         {
/* 12297 */           rss.close();
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/* 12302 */         e.printStackTrace();
/*       */       }
/*       */       
/* 12305 */       request.setAttribute("ResIDPropsMap", resIDPropsMap);
/*       */     }
/*       */     catch (Exception ee)
/*       */     {
/* 12269 */       ee.printStackTrace();
/*       */     }
/*       */     finally
/*       */     {
/*       */       try
/*       */       {
/* 12275 */         if (rs != null)
/*       */         {
/* 12277 */           rs.close();
/*       */         }
/* 12279 */         if (rs2 != null)
/*       */         {
/* 12281 */           rs2.close();
/*       */         }
/* 12283 */         if (rs3 != null)
/*       */         {
/* 12285 */           rs3.close();
/*       */         }
/* 12287 */         if (rs6 != null)
/*       */         {
/* 12289 */           rs6.close();
/*       */         }
/* 12291 */         if (rs7 != null)
/*       */         {
/* 12293 */           rs7.close();
/*       */         }
/* 12295 */         if (rss != null)
/*       */         {
/* 12297 */           rss.close();
/*       */         }
/*       */       }
/*       */       catch (Exception e)
/*       */       {
/* 12302 */         e.printStackTrace();
/*       */       }
/*       */     }
/*       */     
/* 12306 */     request.setAttribute("ParamList", paramlist);
/* 12307 */     request.setAttribute("InfrastructureView", "true");
/*       */     
/* 12309 */     if (resourcetype.equals("MongoDB"))
/*       */     {
/* 12311 */       populateClusterName(request);
/*       */     }
/*       */   }
/*       */   
/*       */   private void populateClusterName(HttpServletRequest request) throws Exception
/*       */   {
/* 12317 */     HashMap resIdPropsMap = (HashMap)request.getAttribute("ResIDPropsMap");
/* 12318 */     ArrayList paramList = (ArrayList)request.getAttribute("ParamList");
/* 12319 */     ArrayList dispList = (ArrayList)paramList.get(0);
/* 12320 */     String dname1 = (String)dispList.get(0);
/*       */     
/* 12322 */     Set kk = resIdPropsMap.keySet();
/* 12323 */     Iterator it22 = kk.iterator();
/* 12324 */     while (it22.hasNext())
/*       */     {
/* 12326 */       String k = (String)it22.next();
/* 12327 */       HashMap pmap = (HashMap)resIdPropsMap.get(k);
/* 12328 */       String bvresid = (String)pmap.get(dname1);
/* 12329 */       if ((bvresid == null) || (bvresid.equals("")))
/*       */       {
/* 12331 */         String pid = getParentID(k);
/* 12332 */         String pname = getParentName(pid);
/* 12333 */         pmap.put(dname1, pname);
/*       */       }
/*       */       else
/*       */       {
/* 12337 */         String parentName = getParentName(bvresid);
/* 12338 */         pmap.put(dname1, parentName);
/*       */       }
/*       */     }
/*       */   }
/*       */   
/*       */   private String setAlarmSound(Long currentEvent, HashMap<String, HashMap<String, String>> alertMap, HashMap<Integer, String> fromCookieMap)
/*       */   {
/* 12345 */     String fileName = "";
/* 12346 */     boolean hasResourceDownAlarm = false;
/* 12347 */     boolean hasCriticalAlarm = false;
/* 12348 */     boolean hasWarningAlarm = false;
/* 12349 */     for (String modTime : alertMap.keySet())
/*       */     {
/*       */ 
/* 12352 */       HashMap<String, String> innerMap = (HashMap)alertMap.get(modTime);
/* 12353 */       String attributeId = (String)innerMap.get("CATEGORY");
/* 12354 */       if (FaultUtil.isAvailabilityId(attributeId))
/*       */       {
/* 12356 */         hasResourceDownAlarm = true;
/*       */ 
/*       */ 
/*       */       }
/* 12360 */       else if (FaultUtil.isAvailabilityId(attributeId))
/*       */       {
/* 12362 */         hasResourceDownAlarm = true;
/*       */       }
/* 12364 */       else if (1 == Integer.parseInt((String)innerMap.get("SEVERITY")))
/*       */       {
/* 12366 */         hasCriticalAlarm = true;
/*       */       }
/* 12368 */       else if (4 == Integer.parseInt((String)innerMap.get("SEVERITY")))
/*       */       {
/* 12370 */         hasWarningAlarm = true;
/*       */       }
/*       */     }
/*       */     
/*       */ 
/* 12375 */     if ((hasResourceDownAlarm) && (fromCookieMap.containsKey(Integer.valueOf(0))) && (new File(System.getProperty("webnms.rootdir") + "/resources/audio/alarm/resourcedown.mp3").exists()))
/*       */     {
/* 12377 */       fileName = "/resources/audio/alarm/resourcedown";
/*       */     }
/* 12379 */     else if ((hasCriticalAlarm) && (fromCookieMap.containsKey(Integer.valueOf(1))) && (new File(System.getProperty("webnms.rootdir") + "/resources/audio/alarm/critical.mp3").exists()))
/*       */     {
/* 12381 */       fileName = "/resources/audio/alarm/critical";
/*       */     }
/* 12383 */     else if ((hasWarningAlarm) && (fromCookieMap.containsKey(Integer.valueOf(4))) && (new File(System.getProperty("webnms.rootdir") + "/resources/audio/alarm/warning.mp3").exists()))
/*       */     {
/* 12385 */       fileName = "/resources/audio/alarm/warning";
/*       */     }
/* 12387 */     AMLog.debug("ShowResourceDetails.setAlarmSound called : hasResourceDownAlarm " + hasResourceDownAlarm + " hasCriticalAlarm : " + hasCriticalAlarm + " hasWarningAlarm : " + hasWarningAlarm + " filename : " + fileName);
/* 12388 */     return fileName;
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */   public ActionForward selectDependentMGroups(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {
/* 12396 */     ResultSet result = null;
/* 12397 */     String haid = request.getParameter("haid");
/* 12398 */     String searchStr = request.getParameter("search");
/* 12399 */     response.setContentType("text/html");
/* 12400 */     boolean isADDM = ((request.getParameter("fromADDM") != null) && ("true".equals(request.getParameter("fromADDM"))) ? Boolean.TRUE : Boolean.FALSE).booleanValue();
/*       */     
/* 12402 */     searchStr = (searchStr == null) || (searchStr.trim().length() == 0) || (searchStr.equalsIgnoreCase("null")) ? "" : searchStr;
/*       */     
/* 12404 */     ArrayList<String> mgroupsToDisable = new ArrayList();
/* 12405 */     ArrayList<String> aListSubGroups = new ArrayList();
/* 12406 */     request.setAttribute("isADDM", Boolean.valueOf(isADDM));
/*       */     try {
/* 12408 */       mgroupsToDisable.add(haid);
/* 12409 */       ArrayList<String> types = new ArrayList();
/* 12410 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType,AM_PARENTCHILDMAPPER WHERE AM_PARENTCHILDMAPPER.PARENTID=" + haid + " AND AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID AND AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE ORDER BY AM_ManagedObject.DISPLAYNAME");
/* 12411 */       while (result.next()) {
/* 12412 */         String resourceType = result.getString("TYPE");
/* 12413 */         String resourceId = result.getString("RESOURCEID");
/* 12414 */         mgroupsToDisable.add(resourceId);
/* 12415 */         if (resourceType.equalsIgnoreCase("Monitor Group")) {
/* 12416 */           aListSubGroups.add(resourceId);
/*       */         }
/*       */       }
/*       */     } catch (Exception e) {
/* 12420 */       e.printStackTrace();
/*       */     } finally {
/* 12422 */       AMConnectionPool.closeStatement(result);
/*       */     }
/*       */     try
/*       */     {
/* 12426 */       Vector<String> allChildGroups = new Vector();
/* 12427 */       for (int i = 0; i < aListSubGroups.size(); i++) {
/* 12428 */         String mgId = (String)aListSubGroups.get(i);
/* 12429 */         ParentChildRelationalUtil.getAllChildMapper(allChildGroups, mgId, true, false);
/*       */       }
/* 12431 */       mgroupsToDisable.addAll(allChildGroups);
/*       */       
/*       */ 
/* 12434 */       HashSet<String> dependentMonitorGroups = RuleAnalyser.getDependentMonitorGroups(haid, false);
/* 12435 */       if (dependentMonitorGroups == null) {
/* 12436 */         dependentMonitorGroups = new HashSet();
/*       */       }
/*       */       
/* 12439 */       String selectedMonGroupsFromClient = request.getParameter("selectedDepMonGroups");
/* 12440 */       if ((selectedMonGroupsFromClient != null) && (selectedMonGroupsFromClient.length() != 0)) {
/* 12441 */         selectedMonGroupsFromClient = Translate.decode(selectedMonGroupsFromClient);
/* 12442 */         StringTokenizer strToken = new StringTokenizer(selectedMonGroupsFromClient, ",");
/* 12443 */         while (strToken.hasMoreElements()) {
/* 12444 */           dependentMonitorGroups.add(strToken.nextToken());
/*       */         }
/*       */       }
/* 12447 */       if (dependentMonitorGroups.size() == 0) {
/* 12448 */         request.setAttribute("selectedDepMonGroups", "");
/*       */       } else {
/* 12450 */         String selectedDependentMGroupsStr = dependentMonitorGroups.toString();
/* 12451 */         selectedDependentMGroupsStr = selectedDependentMGroupsStr.substring(1, selectedDependentMGroupsStr.length() - 1);
/* 12452 */         selectedDependentMGroupsStr = selectedDependentMGroupsStr.replaceAll(", ", ",");
/* 12453 */         request.setAttribute("selectedDepMonGroups", selectedDependentMGroupsStr);
/*       */       }
/*       */       
/*       */ 
/* 12457 */       Object molist = new Vector();
/* 12458 */       ParentChildRelationalUtil.getParentMGs((Vector)molist, haid);
/* 12459 */       mgroupsToDisable.addAll((java.util.Collection)molist);
/*       */       
/* 12461 */       Map groupdetail = com.adventnet.appmanager.server.template.MonitorGroupUtil.getMGDetails(request, EnterpriseUtil.isAdminServer(), searchStr);
/* 12462 */       request.setAttribute("groupdetaillist", groupdetail);
/* 12463 */       request.setAttribute("mgroupsToDisable", mgroupsToDisable);
/* 12464 */       request.setAttribute("haid", haid);
/* 12465 */       request.setAttribute("PRINTER_FRIENDLY", "true");
/*       */     } catch (Exception e) {
/* 12467 */       e.printStackTrace();
/*       */     } finally {
/* 12469 */       AMConnectionPool.closeStatement(result);
/*       */     }
/* 12471 */     if (isADDM) {
/* 12472 */       return new ActionForward("/jsp/SelectDependentMGroups.jsp");
/*       */     }
/* 12474 */     return new ActionForward("/showTile.do?TileName=Tile.AddDependentMGGroups");
/*       */   }
/*       */   
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   public ArrayList<HashMap<String, String>> listSelectedDependentMGrps(String haid)
/*       */   {
/* 12485 */     ArrayList<HashMap<String, String>> selectedDepMGrps = new ArrayList();
/*       */     try {
/* 12487 */       HashSet<String> selDepenMGrpsList = RuleAnalyser.getDependentMonitorGroups(haid, false);
/* 12488 */       if ((selDepenMGrpsList == null) || (selDepenMGrpsList.size() == 0))
/*       */       {
/* 12490 */         return selectedDepMGrps;
/*       */       }
/* 12492 */       String condn = selDepenMGrpsList.toString();
/* 12493 */       condn = condn.substring(1, condn.length() - 1);
/* 12494 */       condn = condn.replaceAll(", ", ",");
/*       */       
/*       */ 
/* 12497 */       ResultSet rs = null;
/*       */       try {
/* 12499 */         String query = "SELECT AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedResourceType.DISPLAYNAME as TYPE,AM_ManagedResourceType.IMAGEPATH FROM AM_ManagedObject,AM_ManagedResourceType WHERE AM_ManagedObject.TYPE='HAI' AND AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE AND AM_ManagedObject.RESOURCEID in (" + condn + ") ORDER BY AM_ManagedObject.DISPLAYNAME";
/* 12500 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 12501 */         while (rs.next()) {
/* 12502 */           HashMap<String, String> h1 = new HashMap();
/* 12503 */           h1.put("resourceid", rs.getString(1));
/* 12504 */           h1.put("displayname", rs.getString(2));
/* 12505 */           h1.put("type", rs.getString("TYPE"));
/* 12506 */           h1.put("imagepath", rs.getString("IMAGEPATH"));
/* 12507 */           selectedDepMGrps.add(h1);
/*       */         }
/*       */       } catch (Exception e) {
/* 12510 */         e.printStackTrace();
/*       */       } finally {
/* 12512 */         AMConnectionPool.closeStatement(rs);
/*       */       }
/*       */     } catch (Exception ex) {
/* 12515 */       ex.printStackTrace();
/*       */     }
/* 12517 */     return selectedDepMGrps;
/*       */   }
/*       */   
/*       */   public void listHyperVServerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {}
/*       */   
/*       */   public void listDockerContainerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*       */     throws Exception
/*       */   {}
/*       */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowResourceDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */