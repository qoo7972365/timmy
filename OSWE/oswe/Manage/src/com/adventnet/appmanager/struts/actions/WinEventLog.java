/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MASSyncUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.me.apm.eventlog.util.EventLogUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Time;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.util.LabelValueBean;
/*      */ 
/*      */ public final class WinEventLog extends org.apache.struts.actions.DispatchAction
/*      */ {
/*   38 */   private ManagedApplication mo = new ManagedApplication();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward forward(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   46 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   55 */     Hashtable tosend = new Hashtable();
/*   56 */     String query1 = "SELECT * FROM AM_RULETYPE order by RULETYPE";
/*   57 */     ArrayList ruletypes = this.mo.getRows(query1);
/*   58 */     tosend.put("ruletypes", ruletypes);
/*   59 */     int userId = DelegatedUserRoleUtil.getLoginUserid(request);
/*   60 */     java.util.Vector<String> eventLogIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userId, 9);
/*   61 */     boolean isDGAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*   62 */     String query2 = null;
/*   63 */     for (int i = 0; i < ruletypes.size(); i++)
/*      */     {
/*   65 */       String tempruletype = (String)((ArrayList)ruletypes.get(i)).get(0);
/*   66 */       if (isDGAdmin)
/*      */       {
/*   68 */         query2 = "SELECT evtlog.RULEID,evtlog.RULENAME,evtlog.RULETYPE,evtlog.STATUS FROM AM_GLOBALEVENTLOGRULES evtlog where " + DBUtil.getCondition("evtlog.RULEID", eventLogIdList) + " and evtlog.RULETYPE=" + tempruletype;
/*   69 */         if (!EnterpriseUtil.isAdminServer())
/*      */         {
/*   71 */           query2 = "SELECT evtlog.RULEID,evtlog.RULENAME,evtlog.RULETYPE,evtlog.STATUS FROM AM_GLOBALEVENTLOGRULES  evtlog where " + DBUtil.getCondition("evtlog.RULEID", eventLogIdList) + " and evtlog.RULETYPE=" + tempruletype + " and evtlog.RULEID not in (select RULEID from AM_GLOBALEVENTLOGRULES where RULEID>9999 and RULESCOPE=3) ";
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*   76 */         query2 = "SELECT AM_GLOBALEVENTLOGRULES.RULEID,AM_GLOBALEVENTLOGRULES.RULENAME,AM_GLOBALEVENTLOGRULES.RULETYPE,AM_GLOBALEVENTLOGRULES.STATUS FROM AM_GLOBALEVENTLOGRULES  WHERE RULETYPE=" + tempruletype;
/*   77 */         if (!EnterpriseUtil.isAdminServer())
/*      */         {
/*   79 */           query2 = "SELECT AM_GLOBALEVENTLOGRULES.RULEID,AM_GLOBALEVENTLOGRULES.RULENAME,AM_GLOBALEVENTLOGRULES.RULETYPE,AM_GLOBALEVENTLOGRULES.STATUS FROM AM_GLOBALEVENTLOGRULES  WHERE RULETYPE=" + tempruletype + " and RULEID not in (select RULEID from AM_GLOBALEVENTLOGRULES where RULEID>9999 and RULESCOPE=3) ";
/*      */         }
/*      */       }
/*   82 */       ArrayList list = this.mo.getRows(query2);
/*   83 */       tosend.put(tempruletype, list);
/*      */     }
/*   85 */     request.setAttribute("table", tosend);
/*   86 */     request.setAttribute("productEdition", Constants.getCategorytype());
/*   87 */     return mapping.findForward("success");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward globalEnableDisable(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*   96 */     ResultSet rs = null;
/*   97 */     java.sql.PreparedStatement ps = null;
/*      */     
/*   99 */     String[] rulestosave = null;
/*  100 */     String ruletype = "";
/*  101 */     String type = "";
/*  102 */     String logCategoryName = "";
/*  103 */     int status = 1;
/*      */     try {
/*  105 */       rulestosave = request.getParameterValues("rules");
/*  106 */       ruletype = request.getParameter("ruletype");
/*  107 */       type = request.getParameter("savetype");
/*  108 */       logCategoryName = request.getParameter("logCategoryName");
/*  109 */       if (logCategoryName != null)
/*      */       {
/*  111 */         request.setAttribute("logCategoryName", logCategoryName);
/*      */       }
/*  113 */       status = 1;
/*  114 */       if (type.equals("enable"))
/*      */       {
/*  116 */         status = 1;
/*      */       }
/*  118 */       else if (type.equals("disable"))
/*      */       {
/*  120 */         status = 0;
/*      */       }
/*  122 */       EventLogUtil.globalenabledisable(status, rulestosave, ruletype, type);
/*  123 */       if (EnterpriseUtil.isAdminServer()) {
/*  124 */         StringBuffer ruleidscommans = new StringBuffer();
/*  125 */         for (String eachrule : rulestosave) {
/*  126 */           ruleidscommans.append(eachrule + ",");
/*      */         }
/*  128 */         HashMap<String, String> logprops = new HashMap();
/*  129 */         logprops.put("ruleids", ruleidscommans.substring(0, ruleidscommans.length() - 1));
/*  130 */         logprops.put("status", type);
/*  131 */         logprops.put("action", "changestatus");
/*  132 */         String apiurl = "/AppManager/xml/logrule";
/*      */         
/*      */ 
/*  135 */         MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */       }
/*  137 */       AMAttributesCache.refreshEventLogCache();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  141 */       e.printStackTrace();
/*      */     }
/*  143 */     return new ActionForward("/eventlogrules.do?method=view");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward globalDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  154 */       String[] rulestodelete = request.getParameterValues("rules");
/*  155 */       StringBuffer ruleids = new StringBuffer();
/*  156 */       ruleids.append(rulestodelete[0]);
/*  157 */       for (int i = 1; i < rulestodelete.length; i++)
/*      */       {
/*  159 */         ruleids.append("," + rulestodelete[i]);
/*      */       }
/*  161 */       String ruletype = request.getParameter("ruletype");
/*  162 */       String logCategoryName = request.getParameter("logCategoryName");
/*  163 */       if (logCategoryName != null)
/*      */       {
/*  165 */         request.setAttribute("logCategoryName", logCategoryName);
/*      */       }
/*  167 */       EventLogUtil.deleteEventLogRules(ruleids.toString(), ruletype);
/*  168 */       if (EnterpriseUtil.isAdminServer()) {
/*  169 */         HashMap<String, String> logprops = new HashMap();
/*  170 */         logprops.put("TO_DELETE", "true");
/*  171 */         logprops.put("ruleids", ruleids.toString());
/*  172 */         logprops.put("apicallfrom", "admin");
/*  173 */         String apiurl = "/AppManager/xml/logrule";
/*  174 */         MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */       }
/*  176 */       AMAttributesCache.refreshEventLogCache();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  180 */       e.printStackTrace();
/*      */     }
/*  182 */     return new ActionForward("/eventlogrules.do?method=view", true);
/*      */   }
/*      */   
/*      */   private void setEventType(ActionForm form, String eventType)
/*      */   {
/*      */     try {
/*  188 */       if (eventType.contains(",")) {
/*  189 */         String[] eventLevel = eventType.split(",");
/*  190 */         for (String level : eventLevel) {
/*  191 */           if ("1".equals(level))
/*      */           {
/*  193 */             ((AMActionForm)form).setEventtype_error("true");
/*      */           }
/*  195 */           else if ("2".equals(level))
/*      */           {
/*  197 */             ((AMActionForm)form).setEventtype_warning("true");
/*      */           }
/*  199 */           else if ("3".equals(level))
/*      */           {
/*  201 */             ((AMActionForm)form).setEventtype_information("true");
/*      */           }
/*      */         }
/*  204 */       } else if ("0".equals(eventType)) {
/*  205 */         ((AMActionForm)form).setEventtype_any("true");
/*  206 */       } else if ("1".equals(eventType)) {
/*  207 */         ((AMActionForm)form).setEventtype_error("true");
/*  208 */       } else if ("2".equals(eventType)) {
/*  209 */         ((AMActionForm)form).setEventtype_warning("true");
/*  210 */       } else if ("3".equals(eventType)) {
/*  211 */         ((AMActionForm)form).setEventtype_information("true");
/*      */       }
/*      */     } catch (Exception ex) {
/*  214 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  224 */     String logCategoryName = request.getParameter("logCategory");
/*  225 */     if (logCategoryName == null)
/*      */     {
/*  227 */       logCategoryName = "EventLogs";
/*      */     }
/*  229 */     ArrayList logCategoryList = new ArrayList();
/*  230 */     logCategoryList.add(new LabelValueBean("", ""));
/*  231 */     String[] logCategories = { "WindowsEventLogs", "WindowsAzureEventLogs" };
/*  232 */     for (String category : logCategories)
/*      */     {
/*  234 */       String label = FormatUtil.getString("am.webclient.eventLogRule.logCategory." + category);
/*  235 */       logCategoryList.add(new LabelValueBean(label, category));
/*      */     }
/*  237 */     ((AMActionForm)form).setLogCategoryList(logCategoryList);
/*  238 */     request.setAttribute("logCategoryName", logCategoryName);
/*  239 */     request.setAttribute("logCategoryList", logCategoryList);
/*      */     
/*      */ 
/*  242 */     ResultSet rs = null;
/*  243 */     String scope = "0";
/*  244 */     ResultSet rs1 = null;
/*      */     try {
/*  246 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  247 */       String Query = "SELECT * FROM AM_RULETYPE order by RULETYPE";
/*  248 */       rs = AMConnectionPool.executeQueryStmt(Query);
/*  249 */       ArrayList RuleTypes = new ArrayList();
/*  250 */       String logCategory = null;
/*  251 */       while (rs.next())
/*      */       {
/*  253 */         ArrayList temp = new ArrayList();
/*  254 */         temp.add(rs.getString("RULETYPE"));
/*  255 */         temp.add(rs.getString("DESCRIPTION"));
/*  256 */         temp.add(rs.getString("CATEGORY"));
/*  257 */         RuleTypes.add(temp);
/*      */       }
/*  259 */       rs.close();
/*  260 */       request.setAttribute("RuleTypes", RuleTypes);
/*  261 */       System.out.println("the Ruletypes==>" + RuleTypes);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  265 */       rs.close();
/*  266 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  270 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  272 */     ArrayList haidlist = null;
/*  273 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  275 */       haidlist = AlarmUtil.getConfiguredGroups(request, true);
/*      */     }
/*      */     else
/*      */     {
/*  279 */       haidlist = AlarmUtil.getConfiguredGroups(request);
/*      */     }
/*  281 */     request.setAttribute("selectedhaid", "");
/*  282 */     request.setAttribute("haidlist", haidlist);
/*  283 */     if (request.getParameter("savetype").equals("new"))
/*      */     {
/*  285 */       request.setAttribute("cleartype", "0");
/*  286 */       EventLogUtil.getAllSupportedServerTypes(request);
/*  287 */       if ("EventLogs".equals(logCategoryName)) {
/*  288 */         setI18key(request);
/*  289 */         List allserverlist = getAllServerList(null, request);
/*  290 */         request.setAttribute("serverdetaillist", allserverlist);
/*      */       }
/*  292 */       request.setAttribute("ruleScope", "0");
/*  293 */       ((AMActionForm)form).setMessage("");
/*      */       
/*  295 */       ((AMActionForm)form).setEventtype_any("true");
/*      */       
/*  297 */       ((AMActionForm)form).setSeverity("1");
/*      */       
/*  299 */       ((AMActionForm)form).setStatus("1");
/*  300 */       ((AMActionForm)form).setMatchRules("1");
/*  301 */       ((AMActionForm)form).setLog_startTime("");
/*  302 */       ((AMActionForm)form).setLog_endTime("");
/*      */       
/*  304 */       ((AMActionForm)form).setRuletype(request.getParameter("ruletype"));
/*  305 */       request.setAttribute("thresholdid", "-1");
/*  306 */       return new ActionForward("/jsp/NewEventRule.jsp");
/*      */     }
/*      */     
/*      */ 
/*  310 */     int ruleid = Integer.parseInt(request.getParameter("ruleid"));
/*  311 */     rs = AMConnectionPool.executeQueryStmt("select HAID from AM_EVENTLOGHAIDMAPPER where RULEID=" + ruleid);
/*  312 */     if (rs.next())
/*      */     {
/*  314 */       request.setAttribute("selectedhaid", rs.getString("HAID"));
/*      */     }
/*  316 */     rs.close();
/*  317 */     AMActionForm amform = new AMActionForm();
/*  318 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  319 */     String query1 = "select * from AM_GLOBALEVENTLOGRULES where RULEID=" + ruleid;
/*  320 */     String subquery = "select RULEID from AM_GLOBALEVENTLOGRULES where RULEID >9999 and RULESCOPE =3";
/*  321 */     if (logCategoryName.equals("AzureDiagnosticLogs"))
/*      */     {
/*  323 */       query1 = "SELECT * FROM AM_GLOBALEVENTLOGRULES,AM_AZURELOGRULES WHERE AM_GLOBALEVENTLOGRULES.RULEID=AM_AZURELOGRULES.RULEID AND AM_GLOBALEVENTLOGRULES.RULEID='" + ruleid + "' and AM_GLOBALEVENTLOGRULES.RULEID not in (" + subquery + ")";
/*      */     }
/*      */     try {
/*  326 */       rs = AMConnectionPool.executeQueryStmt(query1);
/*  327 */       if (rs.next())
/*      */       {
/*  329 */         String rulename = rs.getString("RULENAME");
/*  330 */         String eventid = rs.getString("EVENTID");
/*  331 */         String source = rs.getString("SOURCE");
/*  332 */         String category = rs.getString("CATEGORY");
/*  333 */         String username = rs.getString("USERNAME");
/*  334 */         String matchCount = rs.getString("MATCHCOUNT");
/*  335 */         String matchcount = "1";
/*  336 */         String clearevent = "-1";
/*  337 */         Constants constutil = new Constants();
/*  338 */         username = constutil.findReplace(username, "\\\\", "\\");
/*  339 */         String message = rs.getString("DESCRIPTIONSTRING");
/*  340 */         if ((rulename.equals("-1")) || (rulename == null))
/*  341 */           rulename = "";
/*  342 */         if ((eventid.equals("-1")) || (eventid == null))
/*  343 */           eventid = "";
/*  344 */         if ((source.equals("-1")) || (source == null))
/*  345 */           source = "";
/*  346 */         if ((category.equals("-1")) || (category == null)) {
/*  347 */           category = "";
/*      */         }
/*  349 */         if ((username.equals("-1")) || (username == null))
/*  350 */           username = "";
/*  351 */         if ((message.equals("-1")) || (message == null))
/*  352 */           message = "";
/*  353 */         String logCategory = rs.getString("LOGCATEGORY");
/*  354 */         ((AMActionForm)form).setDisplayname(rulename);
/*  355 */         ((AMActionForm)form).setEventid(eventid);
/*  356 */         ((AMActionForm)form).setSource(source);
/*  357 */         ((AMActionForm)form).setCategory(category);
/*  358 */         ((AMActionForm)form).setUsername(username);
/*  359 */         String eventType = rs.getString("EVENTTYPE");
/*  360 */         setEventType(form, eventType);
/*  361 */         ((AMActionForm)form).setMessage(message);
/*  362 */         String severity = rs.getString("ALERTSEVERITY");
/*  363 */         ((AMActionForm)form).setSeverity(severity);
/*  364 */         ((AMActionForm)form).setRuletype(String.valueOf(rs.getInt("RULETYPE")));
/*  365 */         ((AMActionForm)form).setStatus(String.valueOf(rs.getInt("STATUS")));
/*  366 */         ((AMActionForm)form).setClearevent(rs.getString("CLEAREVENT"));
/*  367 */         request.setAttribute("prerulestatus", String.valueOf(rs.getInt("STATUS")));
/*  368 */         if (rs.getString("MATCHRULES") != null) {
/*  369 */           ((AMActionForm)form).setMatchRules(rs.getString("MATCHRULES"));
/*      */         } else {
/*  371 */           ((AMActionForm)form).setMatchRules("1");
/*      */         }
/*  373 */         if (rs.getTime("LOGTIME") != null) {
/*  374 */           ((AMActionForm)form).setLog_startTime(rs.getTime("LOGTIME").toString().substring(0, rs.getTime("LOGTIME").toString().lastIndexOf(":")));
/*      */         }
/*  376 */         if (rs.getTime("LOG_ENDTIME") != null) {
/*  377 */           ((AMActionForm)form).setLog_endTime(rs.getTime("LOG_ENDTIME").toString().substring(0, rs.getTime("LOG_ENDTIME").toString().lastIndexOf(":")));
/*      */         }
/*  379 */         ((AMActionForm)form).setMatchcount(matchCount);
/*  380 */         request.setAttribute("cleartype", rs.getString("CLEARTYPE"));
/*  381 */         String thresholdid = rs.getString("THRESHOLDID");
/*  382 */         request.setAttribute("ismessageRegex", rs.getString("ISMESSAGEREGEX"));
/*  383 */         String pollstoretry = "1";
/*  384 */         String clearpolls = "1";
/*  385 */         if (!"-1".equals(thresholdid))
/*      */         {
/*  387 */           request.setAttribute("thresholdid", thresholdid);
/*  388 */           rs1 = AMConnectionPool.executeQueryStmt("select CRITICAL_POLLSTOTRY,WARNING_POLLSTOTRY,CLEAR_POLLSTOTRY from AM_THRESHOLDCONFIG where ID=" + thresholdid);
/*  389 */           if (rs1.next())
/*      */           {
/*  391 */             if ("1".equals(severity))
/*      */             {
/*  393 */               pollstoretry = rs1.getString("CRITICAL_POLLSTOTRY");
/*      */             }
/*  395 */             else if ("4".equals(severity))
/*      */             {
/*  397 */               pollstoretry = rs1.getString("WARNING_POLLSTOTRY");
/*      */             }
/*  399 */             clearpolls = rs1.getString("CLEAR_POLLSTOTRY");
/*      */           }
/*  401 */           if (rs1 != null)
/*      */           {
/*  403 */             rs1.close();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  408 */           request.setAttribute("thresholdid", "-1");
/*      */         }
/*  410 */         ((AMActionForm)form).setClearpollscount(Integer.parseInt(clearpolls));
/*  411 */         ((AMActionForm)form).setPollstoretry(pollstoretry);
/*  412 */         scope = rs.getString("RULESCOPE");
/*  413 */         request.setAttribute("ruleScope", scope);
/*  414 */         if (logCategoryName.equals("AzureDiagnosticLogs"))
/*      */         {
/*  416 */           String errorCode = rs.getString("ERRORCODE");
/*  417 */           String errorMessage = rs.getString("ERRORMESSAGE");
/*  418 */           if ((errorCode == null) || (errorCode.equals("-1")))
/*      */           {
/*  420 */             errorCode = "";
/*      */           }
/*  422 */           if ((errorMessage == null) || (errorMessage.equals("-1")))
/*      */           {
/*  424 */             errorMessage = "";
/*      */           }
/*  426 */           ((AMActionForm)form).setErrorCode(errorCode);
/*  427 */           ((AMActionForm)form).setErrorMessage(errorMessage);
/*      */         }
/*  429 */         if ((logCategory == null) || (logCategory.equals("-1")))
/*      */         {
/*  431 */           logCategory = logCategories[0];
/*      */         }
/*  433 */         if ("EventLogs".equals(logCategoryName)) {
/*  434 */           setI18key(request);
/*  435 */           List allserverlist = null;
/*  436 */           if ("2".equals(scope)) {
/*  437 */             allserverlist = getAllServerList(String.valueOf(ruleid), request);
/*      */           }
/*      */           else
/*      */           {
/*  441 */             allserverlist = getAllServerList(null, request);
/*      */           }
/*  443 */           request.setAttribute("serverdetaillist", allserverlist);
/*      */           
/*  445 */           EventLogUtil.getAllSupportedServerTypes(request);
/*      */         }
/*  447 */         ((AMActionForm)form).setLogCategory(logCategory);
/*      */         
/*      */ 
/*  450 */         if ((source.length() > 0) || (category.length() > 0) || (username.length() > 0) || (message.length() > 0) || (!matchCount.equals("1"))) {
/*  451 */           ((AMActionForm)form).setAdvancedUser("true");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  458 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  462 */       rs.close();
/*  463 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  467 */       AMConnectionPool.closeStatement(rs);
/*  468 */       AMConnectionPool.closeStatement(rs1);
/*      */     }
/*  470 */     return new ActionForward("/jsp/NewEventRule.jsp");
/*      */   }
/*      */   
/*      */   public List getAllServerList(String ruleID, HttpServletRequest request)
/*      */   {
/*  475 */     ResultSet serverResult = null;
/*  476 */     Map<String, Properties> availableserverlist = new LinkedHashMap();
/*  477 */     Map<String, Properties> selectedserverlist = new LinkedHashMap();
/*  478 */     Properties allserverlist = new Properties();
/*  479 */     availableserverlist.put("All-Monitors", allserverlist);
/*      */     try {
/*  481 */       String privilegeCondition = "";
/*  482 */       boolean isUserResourceEnabled = false;
/*  483 */       String loginUserid = null;
/*  484 */       if (ClientDBUtil.isPrivilegedUser(request)) {
/*  485 */         if (Constants.isUserResourceEnabled()) {
/*  486 */           isUserResourceEnabled = true;
/*  487 */           loginUserid = Constants.getLoginUserid(request);
/*      */         } else {
/*  489 */           privilegeCondition = " and " + EnterpriseUtil.getCondition("amo.RESOURCEID", ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/*      */         }
/*      */       }
/*      */       
/*  493 */       String allserverquery = "select RESOURCETYPE,ami.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_ManagedObject amo, AM_ManagedResourceType amrt,AM_HOSTINFO ami where ami.RESOURCEID=amo.RESOURCEID and amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP in ('Windows','VirtualMachine')  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ami.MODE='WMI'" + privilegeCondition;
/*  494 */       if (isUserResourceEnabled) {
/*  495 */         allserverquery = "select RESOURCETYPE,ami.RESOURCEID,amo.DISPLAYNAME DISPNAME,amo.TYPE RESTYPE from AM_USERRESOURCESTABLE, AM_ManagedObject amo, AM_ManagedResourceType amrt,AM_HOSTINFO ami where AM_USERRESOURCESTABLE.RESOURCEID=amo.RESOURCEID and AM_USERRESOURCESTABLE.USERID = " + loginUserid + " and ami.RESOURCEID=amo.RESOURCEID and amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP in ('Windows','VirtualMachine')  and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ami.MODE='WMI'";
/*      */       }
/*  497 */       if ((ruleID != null) && (ruleID.length() > 0)) {
/*  498 */         allserverquery = "select DISTINCT RESOURCETYPE,amo.RESOURCEID,amo.DISPLAYNAME as DISPNAME,amo.TYPE as RESTYPE from AM_ManagedObject amo LEFT OUTER JOIN AM_EVENTLOGRESOURCEIDMAPPER ael on ael.RESOURCEID=amo.RESOURCEID and ael.RULEID=" + ruleID + " JOIN AM_ManagedResourceType amrt ON amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP in ('Windows','VirtualMachine') and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ael.RESOURCEID IS NULL JOIN AM_HOSTINFO ami ON ami.RESOURCEID=amo.RESOURCEID where ami.MODE='WMI' " + privilegeCondition;
/*  499 */         if (isUserResourceEnabled) {
/*  500 */           allserverquery = "select DISTINCT RESOURCETYPE,amo.RESOURCEID,amo.DISPLAYNAME as DISPNAME,amo.TYPE as RESTYPE from AM_USERRESOURCESTABLE, AM_ManagedObject amo LEFT OUTER JOIN AM_EVENTLOGRESOURCEIDMAPPER ael on ael.RESOURCEID=amo.RESOURCEID and ael.RULEID=" + ruleID + " JOIN AM_ManagedResourceType amrt ON amo.TYPE=amrt.RESOURCETYPE and amrt.SUBGROUP in ('Windows','VirtualMachine') and amrt.RESOURCETYPE NOT IN ('Windows95','WindowsNT_Server') and ael.RESOURCEID IS NULL JOIN AM_HOSTINFO ami ON ami.RESOURCEID=amo.RESOURCEID where AM_USERRESOURCESTABLE.RESOURCEID=amo.RESOURCEID and AM_USERRESOURCESTABLE.USERID = " + loginUserid + " and ami.MODE='WMI'";
/*      */         }
/*      */       }
/*  503 */       serverResult = AMConnectionPool.executeQueryStmt(allserverquery);
/*  504 */       while (serverResult.next()) {
/*  505 */         String subgroup = serverResult.getString("RESOURCETYPE");
/*  506 */         String resid = serverResult.getString("RESOURCEID");
/*  507 */         String displayname = serverResult.getString("DISPNAME");
/*  508 */         Properties serverdetail = (Properties)availableserverlist.get(subgroup);
/*  509 */         if (serverdetail == null) {
/*  510 */           serverdetail = new Properties();
/*  511 */           availableserverlist.put(subgroup, serverdetail);
/*      */         }
/*  513 */         serverdetail.setProperty(resid, displayname);
/*  514 */         allserverlist.setProperty(resid, displayname);
/*      */       }
/*  516 */       if (serverResult != null)
/*      */       {
/*  518 */         serverResult.close();
/*      */       }
/*  520 */       String wmiavailable = "select amo.RESOURCEID,amo.TYPE as RESOURCETYPE,amo.DISPLAYNAME as DISPNAME from AM_ManagedObject amo inner join WMICONFIGDETAILS wmc on wmc.MONTYPE=amo.TYPE";
/*  521 */       if ((ruleID != null) && (ruleID.length() > 0)) {
/*  522 */         wmiavailable = "select distinct amo.RESOURCEID,amo.TYPE as RESOURCETYPE,amo.DISPLAYNAME as DISPNAME from AM_ManagedObject amo join WMICONFIGDETAILS wcd on wcd.MONTYPE=amo.TYPE left OUTER join AM_EVENTLOGRESOURCEIDMAPPER ae on amo.RESOURCEID=ae.RESOURCEID  and ae.RULEID=" + ruleID + "  where LOGSCREATED='true' and ae.RESOURCEID  is null";
/*      */       }
/*      */       
/*  525 */       serverResult = AMConnectionPool.executeQueryStmt(wmiavailable);
/*  526 */       while (serverResult.next()) {
/*  527 */         String subgroup = serverResult.getString("RESOURCETYPE");
/*  528 */         String resid = serverResult.getString("RESOURCEID");
/*  529 */         String displayname = serverResult.getString("DISPNAME");
/*  530 */         Properties serverdetail = (Properties)availableserverlist.get(subgroup);
/*  531 */         if (serverdetail == null) {
/*  532 */           serverdetail = new Properties();
/*  533 */           availableserverlist.put(subgroup, serverdetail);
/*      */         }
/*  535 */         serverdetail.setProperty(resid, displayname);
/*  536 */         allserverlist.setProperty(resid, displayname);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  542 */       exc.printStackTrace();
/*      */     } finally {
/*  544 */       AMConnectionPool.closeStatement(serverResult);
/*      */     }
/*      */     
/*  547 */     if ((ruleID != null) && (ruleID.length() > 0)) {
/*      */       try
/*      */       {
/*  550 */         String selectedserverquery = "select ael.RESOURCEID,amo.DISPLAYNAME,amo.TYPE from AM_EVENTLOGRESOURCEIDMAPPER ael inner join AM_ManagedObject amo on ael.RESOURCEID=amo.RESOURCEID where ael.RULEID=" + ruleID;
/*  551 */         serverResult = AMConnectionPool.executeQueryStmt(selectedserverquery);
/*  552 */         Properties allselectedserverlist = new Properties();
/*  553 */         String servertype = "";
/*  554 */         while (serverResult.next()) {
/*  555 */           servertype = serverResult.getString(3);
/*  556 */           Properties selecteddetails = (Properties)selectedserverlist.get(servertype);
/*  557 */           if (selecteddetails == null) {
/*  558 */             selecteddetails = new Properties();
/*  559 */             selectedserverlist.put(servertype, selecteddetails);
/*      */           }
/*  561 */           selecteddetails.setProperty(serverResult.getString(1), serverResult.getString(2));
/*  562 */           if (availableserverlist.get(servertype) == null) {
/*  563 */             availableserverlist.put(servertype, new Properties());
/*      */           }
/*  565 */           allselectedserverlist.setProperty(serverResult.getString(1), serverResult.getString(2));
/*      */         }
/*  567 */         selectedserverlist.put("All-Monitors", allselectedserverlist);
/*      */ 
/*      */       }
/*      */       catch (Exception exc)
/*      */       {
/*  572 */         exc.printStackTrace();
/*      */       } finally {
/*  574 */         AMConnectionPool.closeStatement(serverResult);
/*      */       }
/*      */     }
/*  577 */     List returnlist = new ArrayList();
/*  578 */     returnlist.add(availableserverlist);
/*  579 */     returnlist.add(selectedserverlist);
/*  580 */     return returnlist;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  589 */     HashMap<String, String> logprops = new HashMap();
/*  590 */     String prescope = ((AMActionForm)form).getRuleScope();
/*  591 */     logprops.put("rulename", ((AMActionForm)form).getDisplayname());
/*  592 */     logprops.put("eventid", ((AMActionForm)form).getEventid());
/*  593 */     logprops.put("source", ((AMActionForm)form).getSource());
/*  594 */     logprops.put("category", ((AMActionForm)form).getCategory());
/*  595 */     logprops.put("username", ((AMActionForm)form).getUsername());
/*      */     
/*      */ 
/*  598 */     String eventtype = null;
/*      */     
/*  600 */     String eventtype_any = ((AMActionForm)form).getEventtype_any();
/*  601 */     String eventtype_error = ((AMActionForm)form).getEventtype_error();
/*  602 */     String eventtype_warning = ((AMActionForm)form).getEventtype_warning();
/*  603 */     String eventtype_information = ((AMActionForm)form).getEventtype_information();
/*      */     
/*  605 */     if ("on".equalsIgnoreCase(eventtype_error)) {
/*  606 */       eventtype = "1";
/*      */     }
/*  608 */     if ("on".equalsIgnoreCase(eventtype_warning)) {
/*  609 */       if ((eventtype != null) && (eventtype.trim().length() > 0)) {
/*  610 */         eventtype = eventtype + ",2";
/*      */       } else {
/*  612 */         eventtype = "2";
/*      */       }
/*      */     }
/*      */     
/*  616 */     if ("on".equalsIgnoreCase(eventtype_information)) {
/*  617 */       if ((eventtype != null) && (eventtype.trim().length() > 0)) {
/*  618 */         eventtype = eventtype + ",3";
/*      */       } else {
/*  620 */         eventtype = "3";
/*      */       }
/*      */     }
/*      */     
/*  624 */     if ("on".equalsIgnoreCase(eventtype_any)) {
/*  625 */       eventtype = "0";
/*      */     }
/*  627 */     logprops.put("eventtype", eventtype);
/*  628 */     logprops.put("message", ((AMActionForm)form).getMessage());
/*  629 */     logprops.put("severity", ((AMActionForm)form).getSeverity());
/*  630 */     logprops.put("ruletype", ((AMActionForm)form).getRuletype());
/*  631 */     logprops.put("status", ((AMActionForm)form).getStatus());
/*  632 */     logprops.put("resourceids", ((AMActionForm)form).getResourceid());
/*  633 */     logprops.put("cleartype", request.getParameter("cleartype"));
/*  634 */     logprops.put("prerulestatus", request.getParameter("prerulestatus"));
/*  635 */     logprops.put("prescope", prescope);
/*  636 */     logprops.put("monitorchooser", request.getParameter("monitorchooser"));
/*  637 */     logprops.put("savetype", request.getParameter("savetype"));
/*  638 */     logprops.put("displayname", request.getParameter("displayname"));
/*  639 */     logprops.put("haid", request.getParameter("haid"));
/*  640 */     logprops.put("cleartype", request.getParameter("cleartype"));
/*  641 */     logprops.put("clearpollscount", String.valueOf(((AMActionForm)form).getClearpollscount()));
/*  642 */     logprops.put("matchcount", ((AMActionForm)form).getMatchcount());
/*  643 */     logprops.put("pollstoretry", ((AMActionForm)form).getPollstoretry());
/*  644 */     logprops.put("thresholdid", request.getParameter("thresholdid"));
/*  645 */     logprops.put("clearevent", ((AMActionForm)form).getClearevent());
/*  646 */     logprops.put("matchrules", ((AMActionForm)form).getMatchRules());
/*  647 */     logprops.put("log_starttime", ((AMActionForm)form).getLog_startTime());
/*  648 */     logprops.put("log_endtime", ((AMActionForm)form).getLog_endTime());
/*  649 */     String ismessageRegex = "0";
/*  650 */     if (request.getParameter("ismessageRegex") != null)
/*      */     {
/*  652 */       ismessageRegex = request.getParameter("ismessageRegex");
/*      */     }
/*  654 */     logprops.put("ismessageRegex", ismessageRegex);
/*  655 */     String logCategoryName = request.getParameter("logCategoryName");
/*  656 */     String resourceids = ((AMActionForm)form).getResourceid();
/*  657 */     int adminruleid = -1;
/*  658 */     if ((logCategoryName == null) || (logCategoryName.equals("EventLogs")))
/*      */     {
/*  660 */       logCategoryName = "WindowsEventLogs";
/*  661 */       request.setAttribute("logCategoryName", "EventLogs");
/*      */     }
/*      */     else
/*      */     {
/*  665 */       request.setAttribute("logCategoryName", logCategoryName);
/*      */     }
/*  667 */     logprops.put("logCategoryName", logCategoryName);
/*  668 */     String servertypes = "";
/*  669 */     HashMap<String, String> mappingdetails = new HashMap();
/*  670 */     if ("monitortype".equals(request.getParameter("monitorchooser")))
/*      */     {
/*  672 */       String[] assignedmontypes = request.getParameterValues("servertypecheckbox");
/*  673 */       String[] echvalue = null;
/*  674 */       for (int jj = 0; jj < assignedmontypes.length; jj++)
/*      */       {
/*  676 */         echvalue = assignedmontypes[jj].split("#");
/*  677 */         mappingdetails.put(echvalue[0], echvalue[1]);
/*  678 */         servertypes = servertypes + echvalue[0] + ",";
/*      */       }
/*      */     }
/*  681 */     if ((request.getParameter("ruleid") != null) && (request.getParameter("ruleid").trim().length() > 0))
/*      */     {
/*  683 */       adminruleid = Integer.parseInt(request.getParameter("ruleid"));
/*      */     }
/*  685 */     if (("monitorgroup".equalsIgnoreCase(request.getParameter("monitorchooser"))) && (
/*  686 */       (EnterpriseUtil.isAdminServer()) || (adminruleid < 10000)))
/*      */     {
/*  688 */       EventLogUtil.setResourceIdFromHaid(logprops);
/*  689 */       resourceids = (String)logprops.get("resourceids");
/*      */     }
/*      */     
/*      */ 
/*  693 */     if (request.getParameter("savetype").equals("new"))
/*      */     {
/*      */       try
/*      */       {
/*  697 */         HashMap<String, ArrayList<String>> togeneraterestype = null;
/*  698 */         int resourceid = -1;
/*  699 */         int ruleid = -1;
/*  700 */         if ((request.getParameter("ruleid") != null) && (request.getParameter("ruleid").trim().length() > 0))
/*      */         {
/*  702 */           ruleid = Integer.parseInt(request.getParameter("ruleid"));
/*      */         }
/*  704 */         int tempattributeid = -1;
/*  705 */         ResultSet rs = null;
/*  706 */         ResultSet attributers = null;
/*  707 */         ResultSet ResourceTypeRs = null;
/*  708 */         String resourcetype = null;
/*  709 */         request.setAttribute("ruleId", Integer.valueOf(ruleid));
/*  710 */         int loginUserId = DelegatedUserRoleUtil.getLoginUserid(request);
/*  711 */         EventLogUtil.addorEditeventLog(logprops, mappingdetails, request.getParameterValues("servertypecheckbox"), loginUserId);
/*  712 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/*  714 */           String apiurl = "/AppManager/xml/logrule";
/*  715 */           String scopetype = request.getParameter("monitorchooser");
/*  716 */           int taskType = 5;
/*  717 */           logprops.put("apicallfrom", "admin");
/*  718 */           if (("monitorlist".equalsIgnoreCase(scopetype)) || ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */           {
/*  720 */             HashMap<String, String> manservers = EnterpriseUtil.maptoManagedServers(resourceids);
/*  721 */             Set<String> keys = manservers.keySet();
/*  722 */             Iterator<String> it = keys.iterator();
/*  723 */             String masid = "";
/*  724 */             while (it.hasNext())
/*      */             {
/*  726 */               masid = (String)it.next();
/*  727 */               String manresids = (String)manservers.get(masid);
/*  728 */               logprops.put("resourceids", manresids);
/*  729 */               MASSyncUtil.addTasktoSync(logprops, apiurl, masid, "POST", taskType, 1);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  734 */             if ((servertypes != null) && (servertypes.length() > 0))
/*      */             {
/*  736 */               logprops.put("servertypes", servertypes.substring(0, servertypes.length() - 1));
/*      */             }
/*      */             else {
/*  739 */               logprops.put("servertypes", "");
/*      */             }
/*  741 */             MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", taskType, 1);
/*      */           }
/*      */         }
/*  744 */         if ("allmonitor".equals(request.getParameter("monitorchooser"))) {
/*  745 */           saveWindowsAzureLogMonitoringInformation(mapping, form, request, response);
/*      */         }
/*  747 */         if (!"AzureDiagnosticLogs".equals(request.getParameter("logCategoryName")))
/*      */         {
/*  749 */           AMAttributesCache.refreshEventLogCache();
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  754 */         e.printStackTrace();
/*      */       }
/*      */       
/*  757 */       return new ActionForward("/eventlogrules.do?method=view", true);
/*      */     }
/*  759 */     if (request.getParameter("savetype").equals("edit"))
/*      */     {
/*  761 */       logprops.put("ruleid", request.getParameter("ruleid"));
/*  762 */       String ruleid = request.getParameter("ruleid");
/*  763 */       String ruletype = request.getParameter("ruletype");
/*  764 */       logprops.put("ruletype", ruletype);
/*  765 */       EventLogUtil.addorEditeventLog(logprops, null, request.getParameterValues("servertypecheckbox"), -1);
/*      */       
/*  767 */       if ((EnterpriseUtil.isAdminServer()) && (Integer.parseInt(ruleid) >= 10000))
/*      */       {
/*  769 */         String apiurl = "/AppManager/xml/logrule";
/*  770 */         String scopetype = request.getParameter("monitorchooser");
/*  771 */         int taskType = 5;
/*  772 */         logprops.put("apicallfrom", "admin");
/*  773 */         if (("monitorlist".equalsIgnoreCase(scopetype)) || ("monitorgroup".equalsIgnoreCase(scopetype)))
/*      */         {
/*  775 */           HashMap<String, String> manservers = EnterpriseUtil.maptoManagedServers(resourceids);
/*  776 */           Set<String> keys = manservers.keySet();
/*  777 */           Iterator<String> it = keys.iterator();
/*  778 */           String masid = "";
/*  779 */           while (it.hasNext())
/*      */           {
/*  781 */             masid = (String)it.next();
/*  782 */             String manresids = (String)manservers.get(masid);
/*  783 */             logprops.put("resourceids", manresids);
/*  784 */             MASSyncUtil.addTasktoSync(logprops, apiurl, masid, "POST", taskType, 1);
/*      */           }
/*  786 */           String masIdtodelete = EventLogUtil.getManagedServerIndxtoDelete(manservers);
/*  787 */           if (masIdtodelete.length() > 1)
/*      */           {
/*  789 */             apiurl = "/AppManager/xml/logrule";
/*  790 */             HashMap<String, String> delerules = new HashMap();
/*  791 */             delerules.put("ruleids", ruleid);
/*  792 */             delerules.put("TO_DELETE", "true");
/*  793 */             for (String masid1 : masIdtodelete.split(",")) {
/*  794 */               MASSyncUtil.addTasktoSync(delerules, apiurl, masid1, "POST", taskType, 1);
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  800 */           if ((servertypes != null) && (servertypes.length() > 0))
/*      */           {
/*  802 */             logprops.put("servertypes", servertypes.substring(0, servertypes.length() - 1));
/*      */           }
/*      */           else
/*      */           {
/*  806 */             logprops.put("servertypes", "");
/*      */           }
/*  808 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", taskType, 1);
/*      */         }
/*      */       }
/*  811 */       return new ActionForward("/eventlogrules.do?method=view", true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  816 */     return new ActionForward("/eventlogrules.do?method=view", true);
/*      */   }
/*      */   
/*      */   private void saveWindowsAzureLogMonitoringInformation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  821 */     String errorCode = ((AMActionForm)form).getErrorCode();
/*  822 */     String errorMessage = ((AMActionForm)form).getErrorMessage();
/*  823 */     String logCategoryName = request.getParameter("logCategoryName");
/*  824 */     int ruleId = ((Integer)request.getAttribute("ruleId")).intValue();
/*  825 */     if (!logCategoryName.equals("AzureDiagnosticLogs"))
/*      */     {
/*  827 */       return;
/*      */     }
/*  829 */     if ((errorCode == null) || (errorCode.trim().equals("")))
/*      */     {
/*  831 */       errorCode = "-1";
/*      */     }
/*  833 */     if ((errorMessage == null) || (errorMessage.trim().equals("")))
/*      */     {
/*  835 */       errorMessage = "-1";
/*      */     }
/*  837 */     String query = "";
/*  838 */     if (request.getParameter("savetype").equals("new"))
/*      */     {
/*  840 */       String insertQuery = "INSERT INTO AM_AZURELOGRULES VALUES('" + ruleId + "','" + errorCode + "','" + errorMessage + "')";
/*      */       try
/*      */       {
/*  843 */         AMConnectionPool.executeUpdateStmt(insertQuery);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  847 */         throw e;
/*      */       }
/*      */     }
/*  850 */     else if (request.getParameter("savetype").equals("edit"))
/*      */     {
/*  852 */       String updateQuery = "UPDATE AM_AZURELOGRULES SET ERRORCODE='" + errorCode + "', ERRORMESSAGE='" + errorMessage + "' where RULEID=" + ruleId;
/*      */       try
/*      */       {
/*  855 */         AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  859 */         throw e;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward addNewEventLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  866 */     String eventname = request.getParameter("eventname");
/*      */     
/*  868 */     String category = "EventLogs";
/*  869 */     boolean addsuccess = false;
/*      */     try
/*      */     {
/*  872 */       HashMap<String, String> logprops = new HashMap();
/*  873 */       logprops.put("category", category);
/*  874 */       logprops.put("logFileName", eventname);
/*  875 */       int[] returnval = EventLogUtil.createRuleType(category, eventname);
/*  876 */       if (returnval[0] == 1)
/*      */       {
/*  878 */         addsuccess = true;
/*      */       }
/*  880 */       if (addsuccess) {
/*  881 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/*  883 */           logprops.put("ruletype", String.valueOf(returnval[1]));
/*  884 */           String apiurl = "/AppManager/xml/logfile";
/*  885 */           MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */         }
/*  887 */         ActionMessages messages = new ActionMessages();
/*  888 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new org.apache.struts.action.ActionMessage(FormatUtil.getString("am.webclient.eventlog.create.successmsg")));
/*  889 */         saveMessages(request, messages);
/*      */       }
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  894 */       exc.printStackTrace();
/*      */     }
/*  896 */     if (addsuccess) {
/*  897 */       return new ActionForward("/jsp/AddNewEventLog.jsp?eventaction=ok&ruleName=" + eventname + "&successMsg=" + FormatUtil.getString("am.webclient.eventlog.create.successmsg"));
/*      */     }
/*  899 */     return new ActionForward("/jsp/AddNewEventLog.jsp?eventaction=ok&ruleName=" + eventname + "&errorMsg=" + FormatUtil.getString("am.webclient.eventlog.create.errormsg", new String[] { eventname }));
/*      */   }
/*      */   
/*      */   public ActionForward editEventLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  903 */     String eventname = request.getParameter("eventname");
/*      */     try
/*      */     {
/*  906 */       String eventID = request.getParameter("ruleid");
/*  907 */       EventLogUtil.editLogFileName(eventname, eventID);
/*  908 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  910 */         String apiurl = "/AppManager/xml/logfile";
/*      */         
/*  912 */         HashMap<String, String> logprops = new HashMap();
/*  913 */         logprops.put("logFileName", eventname);
/*  914 */         logprops.put("ruletype", eventID);
/*  915 */         MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */       }
/*  917 */       return new ActionForward("/jsp/AddNewEventLog.jsp?eventaction=ok&ruleName=" + eventname + "&successMsg=" + FormatUtil.getString("am.webclient.eventlog.edit.successmsg"));
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  921 */       exc.printStackTrace();
/*  922 */       return new ActionForward("/jsp/AddNewEventLog.jsp?eventaction=ok&ruleName=" + eventname + "&errorMsg=" + exc.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward deleteEventLog(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  928 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  931 */       EventLogUtil.deleteLogFilerule(request.getParameter("ruletype"));
/*  932 */       String apiurl; if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  934 */         apiurl = "/AppManager/xml/logfile";
/*  935 */         HashMap<String, String> logprops = new HashMap();
/*  936 */         logprops.put("ruletype", request.getParameter("ruletype"));
/*  937 */         logprops.put("TO_DELETE", "true");
/*  938 */         MASSyncUtil.addTasktoSync(logprops, apiurl, "all", "POST", 5, 1);
/*      */       }
/*  940 */       return new ActionForward("/eventlogrules.do?method=view&successMsg=" + FormatUtil.getString("am.webclient.eventlog.delete.successmsg"), true);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/*  944 */       exc.printStackTrace();
/*  945 */       return new ActionForward("/eventlogrules.do?method=view&errorMsg=" + exc.getMessage(), true);
/*      */     }
/*      */     finally {
/*  948 */       if (rs != null) {
/*      */         try {
/*  950 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  954 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void setI18key(HttpServletRequest request) {
/*  961 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  964 */       HashMap<String, String> serveri18nkeys = (HashMap)Constants.getServerTypei18nKeys();
/*  965 */       Map<String, String> serveri18nkeys1 = (Map)serveri18nkeys.clone();
/*  966 */       rs = AMConnectionPool.executeQueryStmt("select MONTYPE from WMICONFIGDETAILS where LOGSCREATED='true'");
/*  967 */       while (rs.next())
/*      */       {
/*  969 */         serveri18nkeys.put(rs.getString(1), rs.getString(1));
/*      */       }
/*  971 */       request.setAttribute("servertypei18nkey", serveri18nkeys);
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  976 */       e.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*  981 */       if (rs != null)
/*      */       {
/*  983 */         AMConnectionPool.closeStatement(rs);
/*      */       }
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
/*      */   public void checkRuleNameAlreadyExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  997 */     String ruleId = request.getParameter("ruleid");
/*  998 */     String givenRuleName = request.getParameter("enteredName");
/*  999 */     boolean isPresent = EventLogUtil.isRuleNameExists(givenRuleName, ruleId);
/*      */     try {
/* 1001 */       response.setContentType("text/html; charset=UTF-8");
/* 1002 */       PrintWriter out = response.getWriter();
/* 1003 */       out.println(String.valueOf(isPresent));
/* 1004 */       out.flush();
/*      */     } catch (Exception ex) {
/* 1006 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\WinEventLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */