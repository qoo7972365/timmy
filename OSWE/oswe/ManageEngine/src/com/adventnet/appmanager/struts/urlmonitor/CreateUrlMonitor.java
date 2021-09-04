/*      */ package com.adventnet.appmanager.struts.urlmonitor;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObjectDao;
/*      */ import com.adventnet.appmanager.server.dao.UrlConfiguration;
/*      */ import com.adventnet.appmanager.server.dao.UrlConfigurationDao;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.UrlMonitor;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.UrlSequence;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.HttpClient;
/*      */ import com.adventnet.appmanager.util.ParseHtml;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.StringReader;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class CreateUrlMonitor
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward createUrlMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   78 */     ActionMessages messages = new ActionMessages();
/*   79 */     ActionErrors errors = new ActionErrors();
/*   80 */     String monitorname = request.getParameter("monitorname");
/*   81 */     String haid = null;
/*   82 */     String urlsequenceid = (String)request.getAttribute("sequenceid");
/*   83 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*   85 */       Properties argsprops = new Properties();
/*   86 */       argsprops.setProperty("monitorType", "UrlMonitor");
/*   87 */       argsprops.setProperty("displayname", monitorname);
/*   88 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/*   90 */         String param = (String)e.nextElement();
/*   91 */         if (!argsprops.containsKey(param))
/*      */         {
/*   93 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*   95 */         if (param.equals("haid"))
/*      */         {
/*   97 */           String[] multiVal = request.getParameterValues(param);
/*   98 */           if ((multiVal != null) && (multiVal.length > 0)) {
/*   99 */             String val = Arrays.asList(multiVal).toString().replaceAll(", ", ",");
/*  100 */             val = val.substring(1, val.length() - 1);
/*  101 */             argsprops.setProperty(param, val);
/*      */           }
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  107 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/*  108 */         ArrayList<String> al1 = new ArrayList();
/*  109 */         String displayname = request.getParameter("monitorname");
/*  110 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/*  111 */           displayname = "";
/*      */         }
/*  113 */         String status = "Success";
/*  114 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/*  115 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/*  116 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/*  117 */           status = "Failed";
/*  118 */           message = (String)responseMap.get("message");
/*      */         }
/*  120 */         al1.add(FormatUtil.getTrimmedText(displayname, 200));
/*  121 */         al1.add(status);
/*  122 */         al1.add(message);
/*  123 */         al1.add(masDisplayName);
/*  124 */         request.setAttribute("discoverystatus", al1);
/*  125 */         request.setAttribute("type", "UrlMonitor");
/*  126 */         request.setAttribute("basetype", "Script Monitor");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  140 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor&haid=" + haid);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  144 */         e.printStackTrace();
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
/*  155 */     ArrayList addStatus = new ArrayList();
/*      */     try
/*      */     {
/*  158 */       String username = request.getParameter("userid");
/*  159 */       String password = request.getParameter("password");
/*      */       
/*  161 */       String url = getUrl(form, request);
/*  162 */       if ((url != null) && (!url.equals("")))
/*      */       {
/*  164 */         if ((monitorname == null) || (monitorname.equals("")))
/*      */         {
/*  166 */           if (urlsequenceid != null)
/*      */           {
/*  168 */             if ((url != null) && (url.length() > 30))
/*      */             {
/*  170 */               monitorname = url.substring(0, 30);
/*      */             }
/*      */             else
/*      */             {
/*  174 */               monitorname = url;
/*      */             }
/*      */             
/*      */           }
/*      */           else {
/*  179 */             monitorname = url;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  185 */         if (checkIfMonitorAlreadyExists(url))
/*      */         {
/*  187 */           addStatus.add(FormatUtil.getTrimmedText(monitorname, 200));
/*  188 */           addStatus.add("Failed");
/*  189 */           addStatus.add(FormatUtil.getString("am.webclient.script.monitorexists"));
/*  190 */           request.setAttribute("discoverystatus", addStatus);
/*  191 */           errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("am.webclient.script.monitorexists"));
/*  192 */           saveErrors(request, errors);
/*  193 */           return mapping.findForward("newurlconf");
/*      */         }
/*      */         
/*  196 */         String method = request.getParameter("method");
/*  197 */         int pollInterval = Integer.parseInt(request.getParameter("pollInterval"));
/*  198 */         pollInterval = pollInterval * 60 * 1000;
/*  199 */         String[] requestparams = getReqParams(form, request);
/*  200 */         String checkfor = request.getParameter("checkfor");
/*  201 */         haid = request.getParameter("haid");
/*  202 */         StringBuffer wsbfrequestparams = new StringBuffer();
/*  203 */         for (int i = 0; i < requestparams.length; i++)
/*      */         {
/*      */ 
/*  206 */           wsbfrequestparams.append(requestparams[i]);
/*  207 */           wsbfrequestparams.append("&");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  212 */         AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  213 */         AMManagedObject ammo = new AMManagedObject();
/*  214 */         ammo.setRESOURCENAME(FormatUtil.getTrimmedText(url, 2000));
/*  215 */         if (urlsequenceid == null)
/*      */         {
/*  217 */           ammo.setType("UrlMonitor");
/*  218 */           boolean isallowed = DataCollectionControllerUtil.isallowed();
/*  219 */           if (!isallowed)
/*      */           {
/*  221 */             if (request.getParameter("wiz") != null)
/*      */             {
/*  223 */               return new ActionForward("/showresource.do?method=associateMonitors");
/*      */             }
/*      */             
/*      */ 
/*  227 */             return new ActionForward("/adminAction.do?method=showMonitorTemplates&fromwhere=unabletocreate");
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/*  233 */           ammo.setType("UrlEle");
/*      */         }
/*  235 */         ammo.setDISPLAYNAME(FormatUtil.getTrimmedText(monitorname, 200));
/*  236 */         ammo.setDESCRIPTION("URL Monitor for " + FormatUtil.getTrimmedText(url, 200));
/*  237 */         dao.create(ammo);
/*      */         
/*  239 */         request.setAttribute("urlid", String.valueOf(ammo.getRESOURCEID()));
/*  240 */         if (ammo.getRESOURCEID() != -1) {
/*  241 */           request.setAttribute("resourceid", ammo.getRESOURCEID() + "");
/*  242 */           if (ClientDBUtil.isPrivilegedUser(request)) {
/*  243 */             RestrictedUsersViewUtil.insertIntoAMUserResourcesTableandsynchtoAAM(request.getRemoteUser(), Long.valueOf(ammo.getRESOURCEID() + "").longValue());
/*      */           }
/*      */         }
/*      */         
/*  247 */         request.getSession().setAttribute("urlid", String.valueOf(ammo.getRESOURCEID()));
/*      */         
/*  249 */         UrlConfiguration urlmonitor = new UrlConfiguration(ammo.getRESOURCEID(), url, username, password, wsbfrequestparams.toString(), method, checkfor, pollInterval);
/*  250 */         UrlConfigurationDao urldao = UrlConfigurationDao.getUrlConfigurationDao();
/*  251 */         initUrlConf(urlmonitor, form, request);
/*  252 */         urldao.create(urlmonitor);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  257 */         String httpcondition = request.getParameter("httpcondition");
/*  258 */         int value = Integer.parseInt(request.getParameter("httpvalue"));
/*  259 */         configureThreshold(ammo.getRESOURCEID(), httpcondition, value);
/*      */         
/*      */ 
/*  262 */         ManagedApplication mo = new ManagedApplication();
/*  263 */         String[] urlids = { String.valueOf(ammo.getRESOURCEID()) };
/*  264 */         AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*  265 */         adder.addInterDependentAttributes(ammo.getRESOURCEID());
/*      */         
/*  267 */         String[] selMonitorGroups = request.getParameterValues("haid");
/*  268 */         if (urlsequenceid == null)
/*      */         {
/*  270 */           if ((selMonitorGroups != null) && (selMonitorGroups.length > 0))
/*      */           {
/*  272 */             mo.updateManagedApplicationResources(selMonitorGroups, "junk", urlids, "100", null);
/*      */           }
/*  274 */           new AMAttributesDependencyAdder().addDependentAttributes(5, ammo.getRESOURCEID());
/*      */         }
/*      */         else
/*      */         {
/*  278 */           mo.updateManagedApplicationResources(urlsequenceid, "junk", urlids, "100");
/*      */         }
/*      */         
/*      */ 
/*  282 */         if (urlsequenceid == null)
/*      */         {
/*  284 */           UrlMonitor monitor = new UrlMonitor(ammo.getRESOURCEID(), url, username, password, wsbfrequestparams.toString(), method, checkfor, pollInterval, false, urlmonitor.getCustomHeaders());
/*      */           
/*  286 */           initUrlConf(monitor, form, request);
/*      */         }
/*      */         else
/*      */         {
/*  290 */           UrlSequence monitor = new UrlSequence(Integer.parseInt(urlsequenceid), ammo.getRESOURCEID(), url, username, password, wsbfrequestparams.toString(), method, checkfor, pollInterval, urlmonitor.getCustomHeaders());
/*  291 */           initUrlConf(monitor, form, request);
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
/*  302 */         boolean isnetworkreahable = true;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  315 */         if ((!isnetworkreahable) && (urlsequenceid == null))
/*      */         {
/*  317 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.network.admin.notreachable", "proxy server"));
/*  318 */           saveMessages(request, messages);
/*      */           try
/*      */           {
/*  321 */             Integer.parseInt(haid);
/*  322 */             return mapping.findForward("success");
/*      */           }
/*      */           catch (NumberFormatException ne)
/*      */           {
/*  326 */             return new ActionForward("/showresource.do?resourceid=" + request.getAttribute("urlid") + "&type=UrlMonitor&method=showdetails");
/*      */           }
/*      */         }
/*  329 */         addStatus.add(FormatUtil.getTrimmedText(monitorname, 200));
/*  330 */         addStatus.add("Success");
/*  331 */         addStatus.add("/wsm.do?resourceid=" + ammo.getRESOURCEID() + "&frompage=addmonitor&method=showdetails");
/*  332 */         request.setAttribute("discoverystatus", addStatus);
/*  333 */         request.setAttribute("resourceid", String.valueOf(ammo.getRESOURCEID()));
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  340 */       addStatus.add(FormatUtil.getTrimmedText(monitorname, 200));
/*  341 */       addStatus.add("Failed");
/*  342 */       int errorcode = se.getErrorCode();
/*  343 */       if (errorcode == 1062)
/*      */       {
/*  345 */         addStatus.add(FormatUtil.getString("urlcreation.failure.nameexists", new String[] { monitorname }));
/*  346 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("urlcreation.failure.nameexists", monitorname));
/*      */       }
/*      */       else
/*      */       {
/*  350 */         addStatus.add(FormatUtil.getString("urlcreation.failure", new String[] { se.toString() }));
/*  351 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("urlcreation.failure", se.toString()));
/*      */       }
/*  353 */       request.setAttribute("discoverystatus", addStatus);
/*  354 */       saveErrors(request, errors);
/*  355 */       return mapping.findForward("newurlconf");
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*      */ 
/*  361 */       ee.printStackTrace();
/*  362 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*  363 */       throw ee;
/*      */     }
/*  365 */     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/*  366 */     request.setAttribute("hideFieldsForIT360", hideFieldsForIT360);
/*  367 */     request.setAttribute("isDiscUrlMonitorComplete", "true");
/*  368 */     if (!errors.isEmpty()) {
/*  369 */       saveErrors(request, errors);
/*  370 */       return mapping.findForward("newurlconf");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  376 */     if (request.getParameter("wiz") == null)
/*      */     {
/*  378 */       if (urlsequenceid != null) {
/*  379 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlmonitor.success", "<a href='/showresource.do?resourceid=" + urlsequenceid + "&method=showResourceForResourceID'>" + FormatUtil.getString("am.webclient.monitorgroupdetails.monitordetails.text") + "</a>"));
/*      */       } else {
/*  381 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlmonitor.success", "<a href='/showresource.do?resourceid=" + request.getSession().getAttribute("urlid") + "&method=showResourceForResourceID'>" + FormatUtil.getString("am.webclient.monitorgroupdetails.monitordetails.text") + "</a>"));
/*      */       }
/*  383 */       saveMessages(request, messages);
/*      */     }
/*      */     try
/*      */     {
/*  387 */       Integer.parseInt(haid);
/*  388 */       if (request.getParameter("wiz") != null)
/*      */       {
/*      */ 
/*      */ 
/*  392 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.success.createHAProfiles"));
/*  393 */         saveMessages(request, messages);
/*  394 */         return mapping.findForward("HAProfiles");
/*      */       }
/*      */       
/*  397 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor&haid=" + haid);
/*      */     }
/*      */     catch (NumberFormatException ne)
/*      */     {
/*      */       try
/*      */       {
/*  403 */         Thread.sleep(2500L);
/*      */       }
/*      */       catch (Exception re) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  411 */       boolean isAPI = request.getParameter("isAPI") != null;
/*  412 */       if ((urlsequenceid == null) && (!isAPI)) {
/*  413 */         clearForm((DynaActionForm)form);
/*  414 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor&haid=" + haid);
/*      */       } }
/*  416 */     return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor&haid=" + haid);
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
/*      */   public ActionForward editUrlMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  431 */     boolean isAPI = request.getParameter("isAPI") != null;
/*  432 */     ManagedApplication mo = new ManagedApplication();
/*      */     
/*  434 */     ActionMessages messages = new ActionMessages();
/*  435 */     ActionErrors errors = new ActionErrors();
/*      */     
/*      */     try
/*      */     {
/*  439 */       String selectedscheme = "default";
/*  440 */       if (request.getParameter("selectedscheme") != null)
/*      */       {
/*  442 */         selectedscheme = request.getParameter("selectedscheme");
/*  443 */         request.setAttribute("selectedscheme", selectedscheme);
/*      */       }
/*  445 */       String selectedSkin = "Grey";
/*  446 */       if (request.getParameter("selectedSkin") != null)
/*      */       {
/*  448 */         selectedSkin = request.getParameter("selectedSkin");
/*  449 */         request.setAttribute("selectedskin", selectedSkin);
/*      */       }
/*      */       
/*  452 */       String resourceid = request.getParameter("resourceid");
/*  453 */       ArrayList rows = mo.getRows("select displayname,AM_URL.*," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWRD from AM_URL,AM_ManagedObject where urlid=resourceid and resourceid=" + resourceid);
/*  454 */       request.setAttribute("AMActionForm", new AMActionForm());
/*  455 */       request.setAttribute("method", "updateUrlMonitor");
/*  456 */       if ((rows.size() > 0) && (!isAPI))
/*      */       {
/*  458 */         ArrayList row = (ArrayList)rows.get(0);
/*  459 */         String userName = (String)row.get(3);
/*  460 */         if (("null".equals(userName)) || (userName == null)) {
/*  461 */           userName = "";
/*      */         }
/*  463 */         ((DynaActionForm)form).set("monitorname", (String)row.get(0));
/*  464 */         ((DynaActionForm)form).set("urlid", (String)row.get(1));
/*  465 */         ((DynaActionForm)form).set("userid", userName);
/*  466 */         ((DynaActionForm)form).set("password", (String)row.get(12));
/*  467 */         ((DynaActionForm)form).set("url", (String)row.get(2));
/*  468 */         ((DynaActionForm)form).set("method", (String)row.get(6));
/*  469 */         int pollinterval = Integer.parseInt((String)row.get(8));
/*  470 */         pollinterval /= 60000;
/*  471 */         ((DynaActionForm)form).set("pollInterval", new Integer(pollinterval));
/*      */         
/*      */ 
/*  474 */         String queryString = (String)row.get(5);
/*  475 */         queryString = queryString.replace('&', '\n');
/*  476 */         ((DynaActionForm)form).set("requestparams", queryString);
/*  477 */         ((DynaActionForm)form).set("checkfor", (String)row.get(7));
/*  478 */         if (request.getParameter("haid") != null) {
/*  479 */           ((DynaActionForm)form).set("haid", request.getParameter("haid"));
/*      */         }
/*  481 */         int timeoutinterval = Integer.parseInt((String)row.get(10));
/*  482 */         timeoutinterval /= 1000;
/*  483 */         ((DynaActionForm)form).set("timeout", new Integer(timeoutinterval));
/*  484 */         ((DynaActionForm)form).set("errorcontent", (String)row.get(9));
/*  485 */         String verifyerror = "false";
/*  486 */         if (DBQueryUtil.isPgsql()) {
/*  487 */           verifyerror = String.valueOf(((String)row.get(11)).equals("t"));
/*      */         } else {
/*  489 */           verifyerror = String.valueOf(((String)row.get(11)).equals("1"));
/*      */         }
/*  491 */         ((DynaActionForm)form).set("verifyerror", verifyerror);
/*  492 */         ((DynaActionForm)form).set("customHeaders", String.valueOf(row.get(12)));
/*  493 */         boolean isCaseSensitive = true;boolean isRegEx = false;
/*  494 */         if (DBQueryUtil.isPgsql()) {
/*  495 */           isCaseSensitive = ((String)row.get(13)).equals("t");
/*  496 */           isRegEx = ((String)row.get(14)).equals("t");
/*      */         } else {
/*  498 */           isCaseSensitive = ((String)row.get(13)).equals("1");
/*  499 */           isRegEx = ((String)row.get(14)).equals("1");
/*      */         }
/*  501 */         ((DynaActionForm)form).set("caseSensitive", Boolean.valueOf(isCaseSensitive));
/*  502 */         ((DynaActionForm)form).set("regEx", Boolean.valueOf(isRegEx));
/*  503 */         ((DynaActionForm)form).set("userAgent", String.valueOf(row.get(15)));
/*      */         
/*      */ 
/*  506 */         int attributeid = 403;
/*      */         
/*  508 */         ArrayList threshold = mo.getRows("select CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDCONDITION   from AM_ATTRIBUTETHRESHOLDMAPPER,AM_THRESHOLDCONFIG where AM_ATTRIBUTETHRESHOLDMAPPER.id=" + (String)row.get(1) + " and attribute=" + attributeid + " and thresholdconfigurationid=AM_THRESHOLDCONFIG.ID");
/*      */         
/*  510 */         if (threshold.size() > 0)
/*      */         {
/*  512 */           threshold = (ArrayList)threshold.get(0);
/*  513 */           ((DynaActionForm)form).set("httpvalue", new Integer((String)threshold.get(0)));
/*  514 */           ((DynaActionForm)form).set("httpcondition", (String)threshold.get(1));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  520 */       rows = mo.getRows("select URLSEQID from  AM_URLSequence where urlid=" + resourceid);
/*  521 */       if ((rows.size() > 0) && (!isAPI))
/*      */       {
/*  523 */         rows = (ArrayList)rows.get(0);
/*  524 */         ((DynaActionForm)form).set("userseqid", (String)rows.get(0));
/*      */       }
/*      */       
/*  527 */       return mapping.findForward("urlconf");
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  531 */       ee.printStackTrace();
/*      */     }
/*  533 */     return mapping.findForward("urlconf");
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
/*      */   public ActionForward updateUrlMonitor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  549 */     boolean isAPI = request.getParameter("isAPI") != null;
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  555 */       UrlConfiguration urlconf = getUrlConfiguration(mapping, form, request, response);
/*  556 */       initUrlConf(urlconf, form, request);
/*  557 */       UrlConfigurationDao urldao = UrlConfigurationDao.getUrlConfigurationDao();
/*  558 */       urldao.save(urlconf);
/*      */       
/*  560 */       String httpcondition = "";
/*  561 */       String httpValue = "";
/*      */       
/*  563 */       if (isAPI) {
/*  564 */         httpcondition = request.getParameter("httpcondition");
/*  565 */         httpValue = request.getParameter("httpvalue");
/*      */       } else {
/*  567 */         httpcondition = "" + ((DynaActionForm)form).get("httpcondition");
/*  568 */         httpValue = "" + ((DynaActionForm)form).get("httpvalue");
/*      */       }
/*      */       
/*  571 */       int value = Integer.parseInt(httpValue);
/*  572 */       configureThreshold(urlconf.getURLID(), httpcondition, value);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  577 */       int urlid = urlconf.getURLID();
/*  578 */       String urlseq = request.getParameter("userseqid");
/*  579 */       ArrayList rows = mo.getRows("select type,AM_PARENTCHILDMAPPER.parentid from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.childid=AM_ManagedObject.resourceid  and resourceid = " + urlid);
/*  580 */       String resourcetype = "UrlMonitor";
/*  581 */       if (rows.size() > 0)
/*      */       {
/*  583 */         ArrayList row = (ArrayList)rows.get(0);
/*  584 */         resourcetype = (String)row.get(0);
/*  585 */         urlseq = (String)row.get(1);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  590 */       String parent = "";
/*      */       
/*  592 */       if (resourcetype.equals("UrlMonitor"))
/*      */       {
/*  594 */         UrlMonitor.stopMonitoring(urlconf.getURLID());
/*      */         
/*  596 */         UrlMonitor monitor = new UrlMonitor(urlconf.getURLID(), urlconf.getURL(), urlconf.getUSERID(), urlconf.getPASSWORD(), urlconf.getQUERYSTRING(), urlconf.getMETHOD(), urlconf.getAVAILABILITYSTRING(), urlconf.getPollInterval(), false, urlconf.getCustomHeaders());
/*  597 */         initUrlConf(monitor, form, request);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  602 */         UrlSequence sequence = UrlSequence.getUrlSequence(urlseq);
/*      */         
/*      */ 
/*  605 */         parent = "&parentid=" + urlseq;
/*  606 */         ArrayList temprows = mo.getRows("select resourcename from AM_ManagedObject where resourceid=" + urlseq);
/*  607 */         temprows = (ArrayList)temprows.get(0);
/*  608 */         parent = parent + "&parentname=" + temprows.get(0);
/*      */         
/*      */ 
/*  611 */         if (sequence != null)
/*      */         {
/*  613 */           while (sequence.getURLID() != urlconf.getURLID())
/*      */           {
/*  615 */             sequence.setPollInterval(urlconf.getPollInterval());
/*  616 */             sequence = sequence.getNextUrl();
/*      */           }
/*  618 */           if (sequence != null)
/*      */           {
/*  620 */             sequence.setURL(urlconf.getURL());
/*  621 */             sequence.setAVAILABILITYSTRING(urlconf.getAVAILABILITYSTRING());
/*  622 */             sequence.setUNAVAILABILITYSTRING(urlconf.getUNAVAILABILITYSTRING());
/*  623 */             sequence.setPollInterval(urlconf.getPollInterval());
/*  624 */             sequence.setQUERYSTRING(urlconf.getQUERYSTRING());
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  629 */         String q1 = "UPDATE AM_URL SET PollInterval=" + urlconf.getPollInterval() + " FROM AM_PARENTCHILDMAPPER WHERE AM_URL.URLID=AM_PARENTCHILDMAPPER.CHILDID and AM_PARENTCHILDMAPPER.PARENTID=" + urlseq;
/*  630 */         if (DBQueryUtil.getDBType().equals("mysql"))
/*      */         {
/*  632 */           q1 = "update AM_URL,AM_PARENTCHILDMAPPER set PollInterval=" + urlconf.getPollInterval() + " where AM_PARENTCHILDMAPPER.childid=urlid and parentid=" + urlseq;
/*      */         }
/*  634 */         mo.executeUpdateStmt(q1);
/*      */       }
/*  636 */       String q2 = "update AM_ManagedObject set displayname='" + FormatUtil.getTrimmedText(request.getParameter("monitorname"), 200) + "',RESOURCENAME='" + FormatUtil.getTrimmedText(urlconf.getURL(), 2000) + "' where resourceid=" + urlconf.getURLID();
/*      */       
/*  638 */       mo.executeUpdateStmt(q2);
/*  639 */       EnterpriseUtil.addUpdateQueryToFile(q2);
/*      */       
/*  641 */       String url = "/showresource.do?haid=" + request.getParameter("haid") + "&name=" + request.getParameter("name") + "&resourceid=" + request.getParameter("urlid") + "&resourcename=" + request.getParameter("monitorname") + "&type=" + resourcetype + "&method=showdetails" + parent;
/*  642 */       return new ActionForward(url, true);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  646 */       e.printStackTrace(); }
/*  647 */     return null;
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
/*      */   protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter)
/*      */     throws Exception
/*      */   {
/*  663 */     String methodname = request.getParameter(parameter);
/*  664 */     if (methodname == null)
/*      */     {
/*  666 */       methodname = "createUrlMonitor";
/*      */     }
/*  668 */     return methodname;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private UrlConfiguration getUrlConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  677 */     String urlid = request.getParameter("urlid");
/*  678 */     String monitorname = request.getParameter("monitorname");
/*  679 */     String username = request.getParameter("userid");
/*  680 */     String password = request.getParameter("password");
/*  681 */     if (("null".equalsIgnoreCase(password)) || (password == null)) {
/*  682 */       password = getUrlMonitorPasswordFromDB(urlid);
/*      */     }
/*  684 */     String url = request.getParameter("url");
/*  685 */     String method = request.getParameter("method");
/*  686 */     Integer pollInterval = new Integer(request.getParameter("pollInterval"));
/*  687 */     pollInterval = new Integer(pollInterval.intValue() * 60 * 1000);
/*  688 */     String[] requestparams = getReqParams(form, request);
/*  689 */     String checkfor = request.getParameter("checkfor");
/*  690 */     String haid = request.getParameter("haid");
/*  691 */     StringBuffer wsbfrequestparams = new StringBuffer();
/*  692 */     for (int i = 0; i < requestparams.length; i++)
/*      */     {
/*  694 */       wsbfrequestparams.append(requestparams[i]);
/*  695 */       wsbfrequestparams.append("&");
/*      */     }
/*      */     
/*  698 */     AMManagedObject ammo = new AMManagedObject();
/*  699 */     ammo.setRESOURCENAME(monitorname);
/*  700 */     ammo.setType("UrlMonitor");
/*  701 */     ammo.setDISPLAYNAME(monitorname);
/*  702 */     ammo.setDESCRIPTION("URL Monitor for " + url);
/*      */     
/*  704 */     UrlConfiguration urlmonitor = new UrlConfiguration(Integer.parseInt(urlid), url, username, password, wsbfrequestparams.toString(), method, checkfor, pollInterval.intValue());
/*  705 */     return urlmonitor;
/*      */   }
/*      */   
/*      */ 
/*      */   private void initUrlConf(UrlConfiguration urlmonitor, ActionForm form, HttpServletRequest request)
/*      */   {
/*  711 */     initUrlConf(urlmonitor, form, request, null);
/*      */   }
/*      */   
/*      */   private void initUrlConf(UrlConfiguration urlmonitor, ActionForm form, HttpServletRequest request, String customHeaders) {
/*  715 */     boolean isAPI = request.getParameter("isAPI") != null;
/*  716 */     String unavailabilitystring = "";String userAgent = "";
/*  717 */     String verifyerror = "";
/*      */     
/*  719 */     boolean isCaseSensitive = false;boolean isRegEx = false;
/*  720 */     int timout; if (isAPI)
/*      */     {
/*  722 */       unavailabilitystring = request.getParameter("errorcontent");
/*  723 */       int timout = Integer.parseInt(request.getParameter("timeout"));
/*  724 */       verifyerror = request.getParameter("verifyerror");
/*  725 */       customHeaders = request.getParameter("customHeaders");
/*  726 */       isCaseSensitive = Boolean.parseBoolean(request.getParameter("caseSensitive") == null ? "false" : request.getParameter("caseSensitive"));
/*  727 */       isRegEx = Boolean.parseBoolean(request.getParameter("regEx") == null ? "false" : request.getParameter("regEx"));
/*  728 */       userAgent = request.getParameter("userAgent") == null ? "" : request.getParameter("userAgent");
/*      */     }
/*      */     else
/*      */     {
/*  732 */       unavailabilitystring = (String)((DynaActionForm)form).get("errorcontent");
/*  733 */       timout = Integer.parseInt("" + ((DynaActionForm)form).get("timeout"));
/*  734 */       verifyerror = ((DynaActionForm)form).get("verifyerror").toString();
/*  735 */       customHeaders = ((DynaActionForm)form).get("customHeaders").toString();
/*  736 */       isCaseSensitive = ((Boolean)(((DynaActionForm)form).get("caseSensitive") == null ? Boolean.valueOf(false) : ((DynaActionForm)form).get("caseSensitive"))).booleanValue();
/*  737 */       isRegEx = ((Boolean)(((DynaActionForm)form).get("regEx") == null ? Boolean.valueOf(false) : ((DynaActionForm)form).get("regEx"))).booleanValue();
/*  738 */       userAgent = (String)((DynaActionForm)form).get("userAgent") == null ? "" : (String)((DynaActionForm)form).get("userAgent");
/*      */     }
/*  740 */     urlmonitor.setUnAvailabilityString(unavailabilitystring);
/*  741 */     urlmonitor.setTimeout(timout * 1000);
/*  742 */     urlmonitor.setVerifyerror(verifyerror.equals("true"));
/*  743 */     urlmonitor.setCustomHeaders(customHeaders);
/*  744 */     urlmonitor.setCaseSensitive(isCaseSensitive);
/*  745 */     urlmonitor.setRegEx(isRegEx);
/*  746 */     urlmonitor.setUserAgent(userAgent);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void configureThreshold(int urlid, String condition, int code)
/*      */   {
/*      */     try
/*      */     {
/*  755 */       String thresholdid = "select ID from AM_THRESHOLDCONFIG where DESCRIPTION = '##Threshod for URL##' and CRITICALTHRESHOLDCONDITION='" + condition + "' and CRITICALTHRESHOLDVALUE=" + code + "";
/*  756 */       ArrayList rows = mo.getRows(thresholdid);
/*  757 */       String id = null;
/*      */       
/*  759 */       if (rows.size() == 0)
/*      */       {
/*      */ 
/*  762 */         int newThresholdId = DBQueryUtil.getIncrementedID("ID", "AM_THRESHOLDCONFIG");
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  768 */         String threshold = "insert into AM_THRESHOLDCONFIG (ID,NAME,TYPE,DESCRIPTION,CRITICALTHRESHOLDCONDITION,CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE,WARNINGTHRESHOLDCONDITION,WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE,INFOTHRESHOLDCONDITION,INFOTHRESHOLDVALUE,INFOTHRESHOLDMESSAGE) values(" + newThresholdId + ",'Threshold" + newThresholdId + "',0,'##Threshod for URL##','" + condition + "'," + code + ",'URL is unavailable.Response code is " + code + "','EQ',-1,'','" + compcondition.get(condition) + "'," + code + ",'URL is Available.Response code is " + code + "')";
/*      */         
/*      */ 
/*  771 */         mo.executeUpdateStmt(threshold);
/*  772 */         AMCacheHandler.setThresholdProfileinCache(newThresholdId);
/*  773 */         rows = mo.getRows(thresholdid);
/*  774 */         if (rows.size() > 0)
/*      */         {
/*  776 */           id = (String)((ArrayList)rows.get(0)).get(0);
/*  777 */           String deleteattributemapperquery = "delete from AM_ATTRIBUTETHRESHOLDMAPPER where  id=" + urlid + " and ATTRIBUTE=403";
/*  778 */           mo.executeUpdateStmt(deleteattributemapperquery);
/*  779 */           String attributemapperquery = "insert into AM_ATTRIBUTETHRESHOLDMAPPER values (" + urlid + ",403," + id + ")";
/*  780 */           mo.executeUpdateStmt(attributemapperquery);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  786 */           return;
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  792 */         id = (String)((ArrayList)rows.get(0)).get(0);
/*  793 */         String deleteattributemapperquery = "delete from AM_ATTRIBUTETHRESHOLDMAPPER where  id=" + urlid + " and ATTRIBUTE=403";
/*  794 */         mo.executeUpdateStmt(deleteattributemapperquery);
/*  795 */         String attributemapperquery = "insert into AM_ATTRIBUTETHRESHOLDMAPPER values (" + urlid + ",403," + id + ")";
/*  796 */         mo.executeUpdateStmt(attributemapperquery);
/*      */       }
/*      */       
/*  799 */       AMCacheHandler.setThresholdconfiguration(urlid + "_403", id);
/*      */     }
/*      */     catch (Exception ex) {
/*  802 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*  808 */   private static Hashtable compcondition = new Hashtable(6);
/*  809 */   private static ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */   static {
/*  812 */     String[] conditionValues = { "LT", "GT", "EQ", "NE", "LE", "GE" };
/*  813 */     compcondition.put("LT", "GE");
/*  814 */     compcondition.put("GT", "LE");
/*  815 */     compcondition.put("EQ", "NE");
/*  816 */     compcondition.put("GE", "LT");
/*  817 */     compcondition.put("LE", "GT");
/*  818 */     compcondition.put("NE", "EQ");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward createUrlSequence(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  830 */       String urlseq = request.getParameter("userseqid");
/*      */       
/*  832 */       String haid = request.getParameter("haid");
/*  833 */       boolean isallowed = DataCollectionControllerUtil.isallowed();
/*  834 */       if (!isallowed)
/*      */       {
/*  836 */         if (request.getParameter("wiz") != null)
/*      */         {
/*  838 */           return new ActionForward("/showresource.do?method=associateMonitors");
/*      */         }
/*      */         
/*      */ 
/*  842 */         return new ActionForward("/adminAction.do?method=showMonitorTemplates&fromwhere=unabletocreate");
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  847 */         Integer.parseInt(haid);
/*      */       }
/*      */       catch (NumberFormatException ne)
/*      */       {
/*  851 */         haid = null;
/*      */       }
/*  853 */       if (urlseq == null)
/*      */       {
/*      */ 
/*      */ 
/*  857 */         String userseqname = request.getParameter("userseqname");
/*  858 */         if ((userseqname == null) || (userseqname.equals(""))) {
/*  859 */           userseqname = request.getParameter("url");
/*      */         }
/*  861 */         AMManagedObjectDao dao = AMManagedObjectDao.getAMManagedObjectDao();
/*  862 */         AMManagedObject ammo = new AMManagedObject();
/*  863 */         ammo.setRESOURCENAME(userseqname);
/*  864 */         ammo.setType("UrlSeq");
/*  865 */         ammo.setDISPLAYNAME(userseqname);
/*  866 */         ammo.setDESCRIPTION("Monitors set of URLs");
/*  867 */         dao.create(ammo);
/*      */         
/*  869 */         urlseq = String.valueOf(ammo.getRESOURCEID());
/*  870 */         ((DynaActionForm)form).set("userseqid", urlseq);
/*      */         
/*      */ 
/*      */ 
/*  874 */         if (haid != null)
/*      */         {
/*  876 */           mo.updateManagedApplicationResources(haid, "", new String[] { urlseq });
/*      */         }
/*  878 */         new AMAttributesDependencyAdder().addDependentAttributes(5, Integer.parseInt(urlseq));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  885 */       request.setAttribute("sequenceid", urlseq);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  890 */       convertRequestParams(form, request);
/*      */       
/*  892 */       createUrlMonitor(mapping, form, request, response);
/*      */       
/*  894 */       String urlid = (String)request.getAttribute("urlid");
/*      */       
/*      */ 
/*  897 */       mo.executeUpdateStmt("Insert into AM_URLSequence values (" + urlseq + "," + urlid + ")");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  902 */       boolean addnext = (request.getParameter("finish") == null) || (request.getParameter("finish").equals("false"));
/*  903 */       if (addnext)
/*      */       {
/*  905 */         ((DynaActionForm)form).set("errorcontent", "");
/*  906 */         ((DynaActionForm)form).set("checkfor", "");
/*  907 */         String url = getUrl(form, request);
/*      */         
/*  909 */         String method = request.getParameter("method");
/*  910 */         String[] requestparams = getReqParams(form, request);
/*      */         
/*      */ 
/*  913 */         String urltofetchresponse = url;
/*  914 */         int depth = 3;
/*      */         ActionMessages messages;
/*      */         do {
/*  917 */           depth--;
/*  918 */           boolean nolinksfound = true;
/*  919 */           boolean isnetworkreahable = HttpClient.isNetworkReachable(urltofetchresponse);
/*      */           
/*  921 */           if (!isnetworkreahable)
/*      */             break label1191;
/*  923 */           String rsp = HttpClient.getResponse(urltofetchresponse, method, getQueryString(requestparams));
/*  924 */           messages = new ActionMessages();
/*  925 */           rsp = "<base href=\"" + url + "\">" + rsp;
/*  926 */           ParseHtml ph = new ParseHtml(rsp);
/*  927 */           Hashtable temp = ph.getInputTags();
/*  928 */           String[] reqparams = new String[temp.size()];
/*  929 */           ArrayList list = new ArrayList(temp.size());
/*  930 */           StringBuffer sbf = new StringBuffer();
/*      */           
/*  932 */           Enumeration enuma1 = temp.keys();
/*  933 */           int i = 0;
/*  934 */           while (enuma1.hasMoreElements())
/*      */           {
/*  936 */             String name = (String)enuma1.nextElement();
/*  937 */             String value = (String)temp.get(name);
/*  938 */             reqparams[i] = (name + "=" + value);
/*  939 */             Properties reqprops = new Properties();
/*  940 */             reqprops.setProperty("label", reqparams[i]);
/*  941 */             reqprops.setProperty("value", reqparams[i]);
/*  942 */             list.add(reqprops);
/*  943 */             sbf.append(name + "=" + value + "\n");
/*  944 */             i++;
/*      */           }
/*  946 */           ((DynaActionForm)form).set("requestparams", sbf.toString());
/*  947 */           request.setAttribute("reqparams", list);
/*  948 */           request.setAttribute("parsedhtml", ph);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  953 */           Hashtable urls = ph.getLinks();
/*  954 */           Vector vtemp = new Vector(urls.size());
/*  955 */           enuma1 = urls.keys();
/*  956 */           while (enuma1.hasMoreElements())
/*      */           {
/*  958 */             String link = (String)enuma1.nextElement();
/*  959 */             String linktext = (String)urls.get(link);
/*  960 */             Properties props = new Properties();
/*  961 */             props.setProperty("label", FormatUtil.getTrimmedText(linktext, 80));
/*  962 */             props.setProperty("value", link);
/*  963 */             vtemp.add(props);
/*  964 */             nolinksfound = false;
/*      */           }
/*  966 */           request.setAttribute("availableurls", vtemp);
/*      */           
/*      */ 
/*  969 */           vtemp = new Vector();
/*  970 */           temp = ph.getFormUrls();
/*  971 */           enuma1 = temp.keys();
/*  972 */           while (enuma1.hasMoreElements())
/*      */           {
/*  974 */             String name = (String)enuma1.nextElement();
/*  975 */             String value = (String)temp.get(name);
/*  976 */             Properties formurl = new Properties();
/*  977 */             formurl.setProperty("label", FormatUtil.getTrimmedText(value, 80));
/*  978 */             formurl.setProperty("value", value);
/*  979 */             vtemp.add(formurl);
/*  980 */             nolinksfound = false;
/*      */           }
/*  982 */           request.setAttribute("availableforms", vtemp);
/*      */           
/*  984 */           vtemp = new Vector();
/*  985 */           temp = ph.getFrames();
/*      */           
/*  987 */           enuma1 = temp.keys();
/*  988 */           while (enuma1.hasMoreElements())
/*      */           {
/*  990 */             String name = (String)enuma1.nextElement();
/*  991 */             String value = (String)temp.get(name);
/*  992 */             Properties formurl = new Properties();
/*  993 */             formurl.setProperty("label", FormatUtil.getTrimmedText(value, 80));
/*  994 */             formurl.setProperty("value", value);
/*  995 */             vtemp.add(formurl);
/*  996 */             nolinksfound = false;
/*      */           }
/*  998 */           if (vtemp.size() > 0)
/*  999 */             request.setAttribute("availableframes", vtemp);
/* 1000 */           if (!nolinksfound) {
/*      */             break;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1006 */           ParseHtml ph2 = new ParseHtml("<html>" + rsp + "</html>");
/* 1007 */           urltofetchresponse = ph2.getLocationDotHref();
/* 1008 */         } while ((urltofetchresponse != null) && (depth != 0));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1013 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.success"));
/* 1014 */         saveMessages(request, messages);
/*      */         
/*      */ 
/*      */         break label1260;
/*      */         
/*      */         label1191:
/*      */         
/* 1021 */         ActionMessages messages = new ActionMessages();
/* 1022 */         if (request.isUserInRole("ADMIN"))
/*      */         {
/* 1024 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.network.admin.notreachable", "proxy server"));
/*      */         }
/*      */         else
/*      */         {
/* 1028 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.network.notreachable"));
/*      */         }
/* 1030 */         saveMessages(request, messages);
/*      */         
/*      */ 
/*      */ 
/*      */         label1260:
/*      */         
/*      */ 
/* 1037 */         request.setAttribute("AMActionForm", new AMActionForm());
/*      */         
/* 1039 */         ((DynaActionForm)form).set("url", getUrl(form, request));
/* 1040 */         return mapping.findForward("urlseq");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1047 */       ActionMessages messages = new ActionMessages();
/* 1048 */       request.setAttribute("isDiscUrlSeqComplete", "true");
/* 1049 */       if (request.getParameter("wiz") != null)
/*      */       {
/*      */ 
/*      */ 
/* 1053 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("urlcreation.success.createHAProfiles"));
/* 1054 */         saveMessages(request, messages);
/* 1055 */         return mapping.findForward("HAProfiles");
/*      */       }
/* 1057 */       request.setAttribute("AMActionForm", new AMActionForm());
/*      */       
/* 1059 */       boolean isAPI = request.getParameter("isAPI") != null;
/* 1060 */       if (!isAPI)
/*      */       {
/* 1062 */         clearForm((DynaActionForm)form);
/* 1063 */         return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=UrlSeq&haid=" + haid);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Throwable e)
/*      */     {
/*      */ 
/* 1071 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1074 */     return mapping.findForward("urlseq");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getQueryString(String[] requestparams)
/*      */   {
/* 1084 */     StringBuffer wsbfrequestparams = new StringBuffer();
/* 1085 */     for (int i = 0; i < requestparams.length; i++)
/*      */     {
/* 1087 */       wsbfrequestparams.append(requestparams[i]);
/* 1088 */       wsbfrequestparams.append("&");
/*      */     }
/*      */     
/* 1091 */     return wsbfrequestparams.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   private String getUrl(ActionForm form, HttpServletRequest request)
/*      */   {
/* 1097 */     String url = request.getParameter("url");
/* 1098 */     String option = request.getParameter("opt");
/* 1099 */     if ((option == null) || (option.equals("1")))
/*      */     {
/* 1101 */       return url;
/*      */     }
/* 1103 */     if ((option != null) && (option.equals("3")))
/*      */     {
/* 1105 */       return request.getParameter("formurl");
/*      */     }
/* 1107 */     if ((option != null) && (option.equals("4")))
/*      */     {
/* 1109 */       return request.getParameter("frame");
/*      */     }
/*      */     
/*      */ 
/* 1113 */     ((DynaActionForm)form).set("requestparams", "");
/* 1114 */     return request.getParameter("otherurl");
/*      */   }
/*      */   
/*      */ 
/*      */   private String[] getReqParams(ActionForm form, HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/* 1122 */       String reqparams = request.getParameter("requestparams");
/* 1123 */       StringReader sr = new StringReader(reqparams);
/* 1124 */       BufferedReader br = new BufferedReader(sr);
/*      */       
/*      */ 
/* 1127 */       String line = null;
/* 1128 */       Vector v = new Vector();
/* 1129 */       while ((line = br.readLine()) != null)
/*      */       {
/* 1131 */         v.add(line);
/*      */       }
/*      */       
/* 1134 */       String[] ar = new String[v.size()];
/* 1135 */       for (int i = 0; i < v.size(); i++)
/*      */       {
/* 1137 */         ar[i] = ((String)v.get(i));
/*      */       }
/* 1139 */       return ar;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1143 */       e.printStackTrace(); }
/* 1144 */     return new String[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void convertRequestParams(ActionForm frm, HttpServletRequest request)
/*      */   {
/* 1153 */     DynaActionForm form = (DynaActionForm)frm;
/* 1154 */     ArrayList skipnames = new ArrayList(10);
/* 1155 */     StringBuffer parameters = new StringBuffer();
/* 1156 */     Enumeration parameternames = request.getParameterNames();
/* 1157 */     while (parameternames.hasMoreElements())
/*      */     {
/* 1159 */       String name = (String)parameternames.nextElement();
/*      */       
/*      */ 
/*      */       try
/*      */       {
/* 1164 */         if (form.get(name) != null) {
/*      */           continue;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (IllegalArgumentException ie)
/*      */       {
/* 1172 */         if ((name.equals("org.apache.struts.taglib.html.TOKEN")) || (name.equals("actionmethod")) || (name.equals("finish")) || (name.equals("opt"))) {} } if ((!name.equals("otherurl")) && 
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1178 */         (name.startsWith("adv_")))
/*      */       {
/*      */ 
/*      */ 
/* 1182 */         String value = request.getParameter(name);
/* 1183 */         if (value != null)
/*      */         {
/* 1185 */           parameters.append(name.substring(4));
/* 1186 */           parameters.append("=");
/* 1187 */           parameters.append(value);
/* 1188 */           parameters.append("\n");
/*      */         }
/*      */       } }
/* 1191 */     form.set("requestparams", parameters.toString());
/*      */   }
/*      */   
/*      */   private void clearForm(DynaActionForm frm) {
/* 1195 */     Map map = frm.getMap();
/* 1196 */     Set keyset = map.keySet();
/* 1197 */     Iterator iter = keyset.iterator();
/* 1198 */     while (iter.hasNext()) {
/* 1199 */       String key = (String)iter.next();
/* 1200 */       Object def = null;
/* 1201 */       if (key.equals("url")) {
/* 1202 */         def = "http://";
/* 1203 */       } else if (key.equals("pollInterval")) {
/* 1204 */         def = new Integer(5);
/* 1205 */       } else if (key.equals("method")) {
/* 1206 */         def = "G";
/* 1207 */       } else if (key.equals("timout")) {
/* 1208 */         def = new Integer(5);
/* 1209 */       } else if (key.equals("verifyerror")) {
/* 1210 */         def = "true";
/* 1211 */       } else if (key.equals("httpvalue")) {
/* 1212 */         def = new Integer(200);
/* 1213 */       } else if (key.equals("httpcondition")) {
/* 1214 */         def = "GT";
/*      */       }
/* 1216 */       frm.set(key, def);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getUrlMonitorPasswordFromDB(String resourceid)
/*      */   {
/* 1225 */     String password = "";
/*      */     try {
/* 1227 */       ArrayList rows = mo.getRows("select " + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWRD from AM_URL where urlid=" + resourceid);
/* 1228 */       if (rows.size() > 0)
/*      */       {
/* 1230 */         ArrayList row = (ArrayList)rows.get(0);
/* 1231 */         password = (String)row.get(0);
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1236 */       AMLog.debug("Unable to get encoded password from DB for URL Monitor with resourceId :: " + resourceid);
/* 1237 */       ex.printStackTrace();
/*      */     }
/* 1239 */     return password;
/*      */   }
/*      */   
/*      */   public static boolean checkIfMonitorAlreadyExists(String url)
/*      */   {
/* 1244 */     String qry = "select URLID from AM_URL where URL=?";
/* 1245 */     PreparedStatement pst = null;
/* 1246 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 1249 */       pst = AMConnectionPool.getPreparedStatement(qry);
/* 1250 */       pst.setString(1, url);
/* 1251 */       rs = pst.executeQuery();
/* 1252 */       if (rs.next())
/*      */       {
/* 1254 */         return true;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1259 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1263 */       AMConnectionPool.closeResultSet(rs);
/* 1264 */       AMConnectionPool.closePreparedStatement(pst);
/*      */     }
/* 1266 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\urlmonitor\CreateUrlMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */