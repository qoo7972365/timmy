/*      */ package com.adventnet.appmanager.webclient.fault.alarm;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.extprod.SDPIntegConfig;
/*      */ import com.adventnet.appmanager.server.framework.extprod.ServiceNowIntegConfig;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.nms.fe.common.TableColumn;
/*      */ import com.adventnet.nms.util.GenericUtility;
/*      */ import com.adventnet.nms.webclient.common.WebClientUtil;
/*      */ import com.adventnet.nms.webclient.fault.alarm.AlarmOperationsUtility;
/*      */ import com.adventnet.nms.webclient.fault.event.EventViewUtil;
/*      */ import com.manageengine.appmanager.plugin.PluginUtil;
/*      */ import com.manageengine.appmanager.plugin.RequestUtil;
/*      */ import com.me.apm.cmdb.APMHDSettingsUtil;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import com.me.helpdesk.constants.HelpDeskConstants.TicketNotesOperation;
/*      */ import com.me.helpdesk.interfaces.HelpDesk;
/*      */ import com.me.helpdesk.object.TicketNotes;
/*      */ import com.me.helpdesk.object.TicketSettings;
/*      */ import com.me.helpdesk.parser.HelpDeskConfParser;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletException;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AlarmOperationsDispatchAction
/*      */   extends DispatchAction
/*      */ {
/*      */   public ActionForward doExecuteActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*   95 */     String entity = request.getParameter("entity");
/*   96 */     request.setAttribute("entity", entity);
/*   97 */     request.setAttribute("showActionForm", Boolean.valueOf(APMHDSettingsUtil.getTicketSettingsCache().isDynamicTicketingUsingAction()));
/*   98 */     request.setAttribute("showRequestForm", Boolean.valueOf((APMHDSettingsUtil.getTicketSettingsCache().isDynamicTicketingUsingForm()) && (entity != null) && (entity.indexOf(",") == -1)));
/*   99 */     request.setAttribute("isMspDesk", Boolean.valueOf(APMHDSettingsUtil.isMSPDesk()));
/*  100 */     if (APMHDSettingsUtil.getTicketSettingsCache().isDynamicTicketingUsingAction())
/*      */     {
/*  102 */       String query = "select ID,NAME from AM_ACTIONPROFILE where TYPE in (" + 17 + "," + 19 + ")";
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  113 */       Map<Integer, String> actionsMap = new HashMap();
/*  114 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  117 */         rs = AMConnectionPool.executeQueryStmt(query);
/*      */         
/*  119 */         while (rs.next())
/*      */         {
/*  121 */           actionsMap.put(Integer.valueOf(rs.getInt("ID")), rs.getString("NAME"));
/*      */         }
/*  123 */         request.setAttribute("actionsMap", actionsMap);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  127 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  131 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*  134 */     if ((APMHDSettingsUtil.getTicketSettingsCache().isDynamicTicketingUsingForm()) && (entity != null) && (entity.indexOf(",") == -1))
/*      */     {
/*  136 */       Map<String, String> eventMap = APMHelpDeskUtil.getLatestEventPropsToShowInRATForm(entity, request);
/*  137 */       request.setAttribute("eventMap", eventMap);
/*      */       try {
/*  139 */         if (SDPIntegConfig.getInstance().isSDPConfigured())
/*      */         {
/*  141 */           request.setAttribute("TEMPLATES", APMHelpDeskUtil.getRequestTemplates());
/*  142 */           request.setAttribute("ACCOUNTS", APMHelpDeskUtil.getAccountNames());
/*  143 */           request.setAttribute("SITES", APMHelpDeskUtil.getSiteNames(null));
/*      */         }
/*      */         
/*  146 */         if (ServiceNowIntegConfig.getInstance().isServiceNowConfigured())
/*      */         {
/*  148 */           request.setAttribute("isServiceNow", "true");
/*  149 */           AMLog.audit("AlarmActions - isServiceNow:" + request.getAttribute("isServiceNow"));
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  153 */         request.setAttribute("otherFailure", APMHDSettingsUtil.isMSPDesk() ? FormatUtil.getString("webclient.fault.alarmdetails.otherfailures.sdpnotrunning", new String[] { FormatUtil.getString("am.extprod.mspdesk") }) : FormatUtil.getString("webclient.fault.alarmdetails.otherfailures.sdpnotrunning", new String[] { FormatUtil.getString("am.extprod.sdp") }));
/*  154 */         return mapping.findForward("otherFailures");
/*      */       }
/*      */     }
/*  157 */     return mapping.findForward("executeActions");
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
/*      */   public ActionForward getTemplateInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  171 */     response.setContentType("text/json;charset=UTF-8");
/*  172 */     String entity = request.getParameter("entity");
/*      */     try
/*      */     {
/*  175 */       PrintWriter out = response.getWriter();
/*  176 */       JSONObject obj = new JSONObject();
/*  177 */       Map<String, String> eventMap = APMHelpDeskUtil.getLatestEventPropsToShowInRATForm(entity, request);
/*      */       
/*  179 */       obj.put("Subject", eventMap.get("SUBJECT"));
/*  180 */       obj.put("Description", ((String)eventMap.get("MESSAGE")).trim());
/*  181 */       out.write(obj.toString());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  185 */       e.printStackTrace();
/*      */     }
/*      */     
/*  188 */     return null;
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
/*      */   public ActionForward traversePage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  209 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*  211 */     String entity = request.getParameter("entity");
/*  212 */     String monitortype = request.getParameter("monitortype");
/*      */     
/*  214 */     System.out.println("monitortype" + monitortype);
/*  215 */     request.setAttribute("entity", entity);
/*      */     
/*      */ 
/*      */ 
/*  219 */     String resid = null;
/*  220 */     ManagedApplication mo = new ManagedApplication();
/*  221 */     ArrayList rows = mo.getRows("select AM_ManagedObject.DISPLAYNAME,attribute,source from Alert,AM_ATTRIBUTES,AM_ManagedObject where resourceid=source and " + DBQueryUtil.castasVarchar("ATTRIBUTEID") + "=category and entity='" + entity + "'");
/*      */     
/*  223 */     if (rows.size() > 0)
/*      */     {
/*  225 */       rows = (ArrayList)rows.get(0);
/*  226 */       String sourcename = EnterpriseUtil.decodeString((String)rows.get(0));
/*      */       
/*  228 */       String attributename = (String)rows.get(1);
/*  229 */       resid = (String)rows.get(2);
/*  230 */       System.out.println("resourceid" + resid);
/*  231 */       if ((monitortype != null) && (!monitortype.equals("HAI")))
/*      */       {
/*      */         try
/*      */         {
/*  235 */           ArrayList associatedMGs = getAssociatedmonitorgroups(resid);
/*  236 */           if (associatedMGs != null)
/*      */           {
/*  238 */             System.out.println("associatedMG" + associatedMGs);
/*  239 */             request.setAttribute("associatedMG", associatedMGs);
/*      */           }
/*      */           
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  245 */           System.out.println("Exception e" + e);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  251 */       request.setAttribute("source", sourcename);
/*  252 */       request.setAttribute("category", attributename);
/*      */     }
/*      */     
/*  255 */     authorizationCheckForOperations(userName, request);
/*      */     
/*  257 */     Properties returnProp = AlarmUtil.getAlertProperties(userName, entity);
/*  258 */     if ("true".equalsIgnoreCase(request.getParameter("adminrequest"))) {
/*  259 */       ActionMessages messages = new ActionMessages();
/*  260 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("webclient.fault.annotationresponse.annotation.addedit.apm.message"));
/*  261 */       saveMessages(request, messages);
/*      */     }
/*      */     
/*  264 */     if (returnProp != null) {
/*  265 */       String ownerName = returnProp.getProperty("who");
/*  266 */       String groupName = returnProp.getProperty("groupName");
/*  267 */       String severity = returnProp.getProperty("stringseverity");
/*      */       
/*  269 */       request.setAttribute("groupName", groupName);
/*  270 */       if (ownerName == null)
/*      */       {
/*  272 */         request.setAttribute("ownerName", "-");
/*      */       }
/*      */       else
/*      */       {
/*  276 */         request.setAttribute("ownerName", ownerName);
/*      */       }
/*  278 */       request.setAttribute("severity", severity);
/*      */     }
/*      */     
/*  281 */     String tab = request.getParameter("tab");
/*      */     
/*  283 */     if (tab.equalsIgnoreCase("tabOne"))
/*      */     {
/*  285 */       request.setAttribute("showTabOne", new Boolean(true));
/*      */     }
/*  287 */     else if (tab.equalsIgnoreCase("tabTwo"))
/*      */     {
/*  289 */       String view = request.getParameter("view");
/*  290 */       if (view.equals("merge"))
/*      */       {
/*  292 */         request.setAttribute("showMerge", new Boolean(true));
/*      */       }
/*      */       else
/*      */       {
/*  296 */         request.setAttribute("showMerge", new Boolean(false));
/*      */       }
/*  298 */       request.setAttribute("showTabTwo", new Boolean(true));
/*      */     }
/*  300 */     else if (tab.equalsIgnoreCase("tabThree"))
/*      */     {
/*  302 */       request.setAttribute("showTabThree", new Boolean(true));
/*      */     }
/*      */     
/*  305 */     return mapping.findForward("alarmDetails");
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
/*      */   public ActionForward alertProperties(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*      */     try
/*      */     {
/*  331 */       String userName = (String)request.getSession().getAttribute("userName");
/*      */       
/*  333 */       String entity = request.getParameter("entity");
/*      */       
/*  335 */       request.setAttribute("entity", entity);
/*      */       
/*  337 */       Properties returnProp = AlarmUtil.getAlertProperties(userName, entity);
/*  338 */       if ((returnProp == null) || (returnProp.size() == 0)) {
/*  339 */         return mapping.findForward("alarmProperties");
/*      */       }
/*      */       
/*  342 */       String timeinMillis = returnProp.getProperty("modTime");
/*  343 */       Properties customizedValue = AlarmOperationsUtility.getInstance().customizeTheProperties(userName, returnProp);
/*  344 */       ArrayList thresholdinfo = new ArrayList();
/*      */       try
/*      */       {
/*  347 */         if (Integer.parseInt(customizedValue.getProperty("source")) > 0)
/*      */         {
/*  349 */           thresholdinfo = AlarmUtil.getThresholdDetailsForAttribute(customizedValue.getProperty("category"), customizedValue.getProperty("source"));
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {}
/*      */       
/*      */ 
/*      */ 
/*  356 */       if (thresholdinfo.size() > 0)
/*      */       {
/*  358 */         request.setAttribute("thresholdinfo", thresholdinfo);
/*      */       }
/*      */       else
/*      */       {
/*  362 */         ManagedApplication mo = new ManagedApplication();
/*  363 */         ArrayList rows = mo.getRows("select AM_ATTRIBUTES.TYPE from AM_ATTRIBUTES where AM_ATTRIBUTES.ATTRIBUTEID=" + customizedValue.getProperty("category") + " and AM_ATTRIBUTES.TYPE=0");
/*  364 */         if (rows.size() > 0)
/*      */         {
/*  366 */           request.setAttribute("notavailhealth", "true");
/*      */         }
/*      */       }
/*  369 */       ArrayList actioninfo = new ArrayList();
/*      */       try {
/*  371 */         if (Integer.parseInt(customizedValue.getProperty("source")) > 0)
/*      */         {
/*  373 */           actioninfo = AlarmUtil.getActionDetailsForAttribute(customizedValue.getProperty("category"), customizedValue.getProperty("source"));
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {}
/*      */       
/*      */ 
/*      */ 
/*  380 */       if (actioninfo.size() > 0)
/*      */       {
/*  382 */         request.setAttribute("actioninfo", actioninfo);
/*      */       }
/*  384 */       String resourceid = customizedValue.getProperty("source");
/*  385 */       HashMap<String, String> customFields = MyFields.customFieldforAlarms(resourceid);
/*  386 */       customizedValue.setProperty("label", (String)customFields.get("label"));
/*  387 */       customizedValue.setProperty("impact", (String)customFields.get("impact"));
/*  388 */       customizedValue.setProperty("urgency", (String)customFields.get("urgency"));
/*  389 */       customizedValue.setProperty("haslabel", (String)customFields.get("haslabel"));
/*  390 */       customizedValue.setProperty("hasimpact", (String)customFields.get("hasimpact"));
/*  391 */       customizedValue.setProperty("hasurgency", (String)customFields.get("hasurgency"));
/*  392 */       customizedValue.setProperty("resourceid", resourceid);
/*  393 */       customizedValue.setProperty("attributeid", customizedValue.getProperty("category"));
/*  394 */       customizedValue.setProperty("entity", entity);
/*  395 */       String ownerName = customizedValue.getProperty("owner");
/*  396 */       customizedValue.setProperty("owner", (ownerName != null) && (!ownerName.equalsIgnoreCase("NULL")) ? ownerName : "-");
/*  397 */       String source = (String)request.getAttribute("source");
/*  398 */       String category = (String)request.getAttribute("category");
/*  399 */       if (source != null)
/*  400 */         customizedValue.setProperty("source", source);
/*  401 */       if (category != null)
/*  402 */         customizedValue.setProperty("category", category);
/*  403 */       customizedValue.setProperty("timeinMillis", timeinMillis);
/*      */       
/*  405 */       request.setAttribute("alertProp", customizedValue);
/*  406 */       APMHelpDeskUtil.setCMDBAttributes(request);
/*  407 */       if (APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */       {
/*  409 */         Map<APMHelpDeskUtil.CIUrl, String> ciLinks = APMHelpDeskUtil.getCILinks(request);
/*  410 */         if (ciLinks != null)
/*      */         {
/*  412 */           for (Map.Entry<APMHelpDeskUtil.CIUrl, String> ciLink : ciLinks.entrySet())
/*      */           {
/*  414 */             request.setAttribute(((APMHelpDeskUtil.CIUrl)ciLink.getKey()).toString(), ciLink.getValue());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  421 */       if ((entity != null) && (entity.indexOf("_") != -1))
/*      */       {
/*  423 */         StringTokenizer strTokens = new StringTokenizer(entity, "_");
/*  424 */         String workOrderUrl = APMHelpDeskUtil.getTicketUrl(Long.parseLong(strTokens.nextToken()), Long.parseLong(strTokens.nextToken()));
/*  425 */         if (workOrderUrl != null)
/*      */         {
/*  427 */           request.setAttribute("workOrderUrl", workOrderUrl);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  434 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  437 */     return mapping.findForward("alarmProperties");
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
/*      */   public ActionForward otherFailures(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  460 */     String entity = request.getParameter("entity");
/*  461 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*  463 */     String groupName = request.getParameter("groupName");
/*      */     
/*  465 */     Properties otherFailures = null;
/*  466 */     if ((groupName != null) && (!groupName.equals("")))
/*      */     {
/*  468 */       otherFailures = AlarmOperationsUtility.getInstance().getOtherFailures(userName, entity, groupName);
/*      */     }
/*      */     
/*  471 */     request.setAttribute("otherFailures", otherFailures);
/*  472 */     return mapping.findForward("otherFailures");
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
/*      */   public ActionForward toAssignAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  496 */     String userName = (String)request.getSession().getAttribute("userName");
/*  497 */     boolean isUserPermittedToPickUp = GenericUtility.isAuthorized(userName, "Alert Pickup");
/*      */     
/*  499 */     if (isUserPermittedToPickUp)
/*      */     {
/*  501 */       String entity = request.getParameter("entity");
/*  502 */       request.setAttribute("entity", entity);
/*  503 */       request.setAttribute("userName", userName);
/*  504 */       return mapping.findForward("assignAlarm");
/*      */     }
/*      */     
/*      */ 
/*  508 */     ActionMessages actionMsg = new ActionMessages();
/*  509 */     ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.pickup");
/*  510 */     actionMsg.add("message", messageKey);
/*  511 */     request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  512 */     return mapping.findForward("messagePage");
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
/*      */   public ActionForward alertPickUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  535 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*  537 */     String entity = request.getParameter("entity");
/*  538 */     request.setAttribute("entity", entity);
/*      */     
/*  540 */     ManagedApplication mo = new ManagedApplication();
/*  541 */     ArrayList rows = mo.getRows("select resourcename,attribute from Alert,AM_ATTRIBUTES,AM_ManagedObject where resourceid=source and ATTRIBUTEID=category and entity='" + entity + "'");
/*      */     
/*  543 */     if (rows.size() > 0)
/*      */     {
/*  545 */       rows = (ArrayList)rows.get(0);
/*  546 */       String sourcename = (String)rows.get(0);
/*  547 */       String attributename = (String)rows.get(1);
/*  548 */       request.setAttribute("source", sourcename);
/*  549 */       request.setAttribute("category", attributename);
/*      */     }
/*      */     
/*      */ 
/*  553 */     authorizationCheckForOperations(userName, request);
/*      */     
/*  555 */     Boolean isUserPermittedToPickUp = (Boolean)request.getAttribute("isUserPermittedToPickUp");
/*      */     
/*  557 */     String pickUpStatus = "";
/*  558 */     if (isUserPermittedToPickUp.booleanValue())
/*      */     {
/*  560 */       pickUpStatus = AlarmOperationsUtility.getInstance().pickTheAlert(userName, entity);
/*      */     }
/*      */     else
/*      */     {
/*  564 */       ActionMessages actionMsg = new ActionMessages();
/*  565 */       ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.pickup");
/*  566 */       actionMsg.add("message", messageKey);
/*  567 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  568 */       return mapping.findForward("messagePage");
/*      */     }
/*      */     
/*  571 */     Properties returnProp = AlarmOperationsUtility.getInstance().getAlertProperties(userName, entity);
/*  572 */     String groupName = returnProp.getProperty("groupName");
/*  573 */     String ownerName = returnProp.getProperty("who");
/*  574 */     String severity = returnProp.getProperty("stringseverity");
/*  575 */     request.setAttribute("groupName", groupName);
/*  576 */     if (ownerName == null)
/*      */     {
/*  578 */       request.setAttribute("ownerName", "-");
/*      */     }
/*      */     else
/*      */     {
/*  582 */       request.setAttribute("ownerName", ownerName);
/*      */     }
/*  584 */     request.setAttribute("severity", severity);
/*      */     
/*  586 */     request.setAttribute("showTabOne", new Boolean(true));
/*  587 */     request.setAttribute("pickUpStatus", pickUpStatus);
/*  588 */     return mapping.findForward("alarmDetails");
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
/*      */   public ActionForward assignTheAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/*  610 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*  612 */     String entity = request.getParameter("entity");
/*  613 */     request.setAttribute("entity", entity);
/*      */     
/*  615 */     String toWhom = request.getParameter("assign");
/*  616 */     request.setAttribute("assignedOwner", toWhom);
/*      */     
/*  618 */     String viewId = request.getParameter("viewId");
/*  619 */     request.setAttribute("viewId", viewId);
/*      */     
/*  621 */     String from = request.getParameter("from");
/*  622 */     request.setAttribute("from", from);
/*      */     
/*  624 */     authorizationCheckForOperations(userName, request);
/*  625 */     Boolean isUserPermittedToPickUp = (Boolean)request.getAttribute("isUserPermittedToPickUp");
/*      */     
/*  627 */     String pickUpStatus = "";
/*  628 */     HashSet usersList = WebClientUtil.getUsersList();
/*  629 */     if (!usersList.contains(toWhom))
/*      */     {
/*  631 */       request.setAttribute("userDExist", new Boolean(true));
/*  632 */       return mapping.findForward("assignAlarm");
/*      */     }
/*      */     
/*  635 */     if (isUserPermittedToPickUp.booleanValue())
/*      */     {
/*  637 */       StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*      */       
/*  639 */       while (tokenize.hasMoreTokens())
/*      */       {
/*  641 */         String tempEntity = tokenize.nextToken();
/*  642 */         pickUpStatus = AlarmOperationsUtility.getInstance().pickTheAlert(toWhom, tempEntity);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  647 */       ActionMessages actionMsg = new ActionMessages();
/*  648 */       ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.pickup");
/*  649 */       actionMsg.add("message", messageKey);
/*  650 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  651 */       return mapping.findForward("messagePage");
/*      */     }
/*      */     
/*  654 */     if (!from.equals("listView"))
/*      */     {
/*  656 */       Properties returnProp = AlarmOperationsUtility.getInstance().getAlertProperties(userName, entity);
/*  657 */       String groupName = returnProp.getProperty("groupName");
/*  658 */       String ownerName = returnProp.getProperty("who");
/*  659 */       String severity = returnProp.getProperty("stringseverity");
/*  660 */       request.setAttribute("groupName", groupName);
/*  661 */       if (ownerName == null)
/*      */       {
/*  663 */         request.setAttribute("ownerName", "-");
/*      */       }
/*      */       else
/*      */       {
/*  667 */         request.setAttribute("ownerName", ownerName);
/*      */       }
/*  669 */       request.setAttribute("severity", severity);
/*      */     }
/*      */     
/*  672 */     request.setAttribute("pickUpStatus", pickUpStatus);
/*  673 */     return mapping.findForward("assignStatus");
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
/*      */   public ActionForward alertUnPick(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  695 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/*  697 */     String entity = request.getParameter("entity");
/*  698 */     request.setAttribute("entity", entity);
/*      */     
/*  700 */     authorizationCheckForOperations(userName, request);
/*  701 */     Boolean isUserPermittedToPickUp = (Boolean)request.getAttribute("isUserPermittedToPickUp");
/*      */     
/*  703 */     ManagedApplication mo = new ManagedApplication();
/*  704 */     ArrayList rows = mo.getRows("select resourcename,attribute from Alert,AM_ATTRIBUTES,AM_ManagedObject where resourceid=source and ATTRIBUTEID=category and entity='" + entity + "'");
/*      */     
/*  706 */     if (rows.size() > 0)
/*      */     {
/*  708 */       rows = (ArrayList)rows.get(0);
/*  709 */       String sourcename = (String)rows.get(0);
/*  710 */       String attributename = (String)rows.get(1);
/*  711 */       request.setAttribute("source", sourcename);
/*  712 */       request.setAttribute("category", attributename);
/*      */     }
/*  714 */     String unPickUpStatus = "";
/*  715 */     if (isUserPermittedToPickUp.booleanValue())
/*      */     {
/*  717 */       unPickUpStatus = AlarmOperationsUtility.getInstance().unPickTheAlert(userName, entity);
/*      */     }
/*      */     else
/*      */     {
/*  721 */       ActionMessages actionMsg = new ActionMessages();
/*  722 */       ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.unpick");
/*  723 */       actionMsg.add("message", messageKey);
/*  724 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  725 */       return mapping.findForward("messagePage");
/*      */     }
/*      */     
/*  728 */     Properties returnProp = AlarmOperationsUtility.getInstance().getAlertProperties(userName, entity);
/*  729 */     String groupName = returnProp.getProperty("groupName");
/*  730 */     String ownerName = returnProp.getProperty("who");
/*  731 */     String severity = returnProp.getProperty("stringseverity");
/*  732 */     request.setAttribute("groupName", groupName);
/*  733 */     if (ownerName == null)
/*      */     {
/*  735 */       request.setAttribute("ownerName", "-");
/*      */     }
/*      */     else
/*      */     {
/*  739 */       request.setAttribute("ownerName", ownerName);
/*      */     }
/*  741 */     request.setAttribute("severity", severity);
/*      */     
/*  743 */     request.setAttribute("showTabOne", new Boolean(true));
/*  744 */     request.setAttribute("UnPickStatus", unPickUpStatus);
/*  745 */     return mapping.findForward("alarmDetails");
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
/*      */   public ActionForward clearAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  767 */     ActionMessages messages = new ActionMessages();
/*  768 */     String userName = (String)request.getSession().getAttribute("userName");
/*  769 */     AMLog.debug("FAULT : Clear Alert called for user" + userName);
/*  770 */     String entity = request.getParameter("entity");
/*  771 */     request.setAttribute("entity", entity);
/*  772 */     ManagedApplication mo = new ManagedApplication();
/*  773 */     ArrayList rows = mo.getRows("select resourcename,attribute,resourceid,attributeid,MODTIME from Alert,AM_ATTRIBUTES,AM_ManagedObject where resourceid=source and ATTRIBUTEID=category and entity='" + entity + "'");
/*      */     
/*  775 */     String resourceid = null;
/*  776 */     String attributeid = null;
/*  777 */     String modTime = null;
/*  778 */     if (rows.size() > 0)
/*      */     {
/*  780 */       rows = (ArrayList)rows.get(0);
/*  781 */       String sourcename = (String)rows.get(0);
/*  782 */       String attributename = (String)rows.get(1);
/*  783 */       resourceid = (String)rows.get(2);
/*  784 */       attributeid = (String)rows.get(3);
/*  785 */       modTime = (String)rows.get(4);
/*  786 */       request.setAttribute("source", sourcename);
/*  787 */       request.setAttribute("category", attributename);
/*      */     }
/*  789 */     authorizationCheckForOperations(userName, request);
/*  790 */     Boolean isUserPermittedToClearAlert = (Boolean)request.getAttribute("isUserPermittedToClearAlert");
/*  791 */     String alertClearStatus = "";
/*      */     
/*  793 */     if (isUserPermittedToClearAlert.booleanValue())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  798 */       alertClearStatus = "" + FaultUtil.clearAlert(entity, new StringBuilder().append("").append(request.getRemoteUser()).toString());
/*  799 */       AMLog.debug("FAULT : Alert cleared ??" + alertClearStatus);
/*      */     }
/*      */     else
/*      */     {
/*  803 */       ActionMessages actionMsg = new ActionMessages();
/*  804 */       ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.clear");
/*  805 */       actionMsg.add("message", messageKey);
/*  806 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/*  807 */       return mapping.findForward("messagePage");
/*      */     }
/*  809 */     Properties returnProp = AlarmOperationsUtility.getInstance().getAlertProperties(userName, entity);
/*  810 */     String groupName = returnProp.getProperty("groupName");
/*  811 */     String ownerName = returnProp.getProperty("who");
/*  812 */     String severity = returnProp.getProperty("stringseverity");
/*  813 */     request.setAttribute("groupName", groupName);
/*  814 */     if (ownerName == null)
/*      */     {
/*  816 */       request.setAttribute("ownerName", "-");
/*      */     }
/*      */     else
/*      */     {
/*  820 */       request.setAttribute("ownerName", ownerName);
/*      */     }
/*  822 */     request.setAttribute("severity", severity);
/*      */     
/*  824 */     request.setAttribute("showTabOne", new Boolean(true));
/*  825 */     request.setAttribute("clearStatus", alertClearStatus);
/*      */     
/*  827 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("webclient.fault.alarm.clear.status.success"));
/*  828 */     saveMessages(request, messages);
/*  829 */     return mapping.findForward("alarmDetails");
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
/*      */   public ActionForward doAnnotate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  850 */     String userName = request.getParameter("userName");
/*  851 */     String actionFromCentral = request.getParameter("actionFromCentral") != null ? request.getParameter("actionFromCentral") : "false";
/*      */     
/*  853 */     if (actionFromCentral.equalsIgnoreCase("true"))
/*      */     {
/*  855 */       userName = request.getParameter("uName");
/*      */     }
/*  857 */     String entity = request.getParameter("entity");
/*  858 */     request.setAttribute("entity", entity);
/*  859 */     request.setAttribute("userName", userName);
/*  860 */     request.setAttribute("bulkannotate", request.getParameter("bulkannotate"));
/*  861 */     if ((request.getParameter("edit") != null) && (request.getParameter("edit").equals("true")))
/*      */     {
/*  863 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  866 */         String query = "select * from ANNOTATION where ENTITY='" + entity + "' and MODTIME='" + request.getParameter("time") + "'";
/*  867 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  868 */         while (rs.next())
/*      */         {
/*  870 */           String n1 = rs.getString("NOTES");
/*  871 */           if (n1.indexOf("Annotation :") != -1) {
/*  872 */             String[] temp = n1.split("Annotation :");
/*  873 */             request.setAttribute("notes", temp[1].trim());
/*      */           } else {
/*  875 */             request.setAttribute("notes", n1);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  881 */         e.printStackTrace();
/*      */       }
/*      */       finally {
/*  884 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/*  887 */     return mapping.findForward("setAnnotation");
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
/*      */   public ActionForward setAnnotation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/*  909 */     boolean isApiRequest = "true".equals(request.getParameter("isApiRequest"));
/*  910 */     String userName = request.getRemoteUser();
/*  911 */     if ((isApiRequest) && (request.getParameter("username") != null)) {
/*  912 */       userName = request.getParameter("username");
/*      */     }
/*  914 */     String actionFromCentral = request.getParameter("actionFromCentral") != null ? request.getParameter("actionFromCentral") : "false";
/*      */     
/*  916 */     if (actionFromCentral.equalsIgnoreCase("true"))
/*      */     {
/*  918 */       userName = request.getParameter("uName");
/*      */     }
/*  920 */     String entity = request.getParameter("entity");
/*  921 */     request.setAttribute("entity", entity);
/*  922 */     String annotationMessage = request.getParameter("text");
/*  923 */     String bulkAnnotate = request.getParameter("bulkannotate");
/*  924 */     authorizationCheckForOperations(userName, request);
/*  925 */     Boolean isUserPermittedToSetAnnotation = (Boolean)request.getAttribute("isUserPermittedToSetAnnotation");
/*  926 */     StringTokenizer tokenize = new StringTokenizer(entity, ",");
/*  927 */     Hashtable<String, Boolean> failureEntity = new Hashtable();
/*  928 */     boolean annotationStatus = false;
/*  929 */     boolean bulkAnnotation = false;
/*  930 */     List<String> entities = new ArrayList();
/*  931 */     if ((annotationMessage != null) && (annotationMessage.trim().length() > 0)) {
/*  932 */       annotationMessage = "Annotation : " + annotationMessage;
/*  933 */       while (tokenize.hasMoreTokens())
/*      */       {
/*  935 */         String tempEntity = tokenize.nextToken();
/*      */         try {
/*  937 */           String insertQuery = "insert into ANNOTATION values ('" + tempEntity + "','" + userName + "'," + System.currentTimeMillis() + ",'NULL','" + annotationMessage + "')";
/*  938 */           entities.add("'" + tempEntity + "'");
/*  939 */           AMConnectionPool.executeUpdateStmt(insertQuery);
/*  940 */           AMLog.debug("Annotation message successfully added for " + tempEntity + " message " + annotationMessage);
/*      */         } catch (Exception ex) {
/*  942 */           ex.printStackTrace();
/*      */         }
/*  944 */         if (APMHelpDeskUtil.isTicketingNotesEnabled())
/*      */         {
/*  946 */           Map<String, Long> entitiesVsTicketsMap = APMHelpDeskUtil.getEntitiesVsTicketsMap(entities);
/*  947 */           if ((entitiesVsTicketsMap != null) && (entitiesVsTicketsMap.size() > 0))
/*      */           {
/*      */             try
/*      */             {
/*  951 */               for (Long ticketId : entitiesVsTicketsMap.values())
/*      */               {
/*  953 */                 TicketNotes tn = new TicketNotes(HelpDeskConstants.TicketNotesOperation.ADD_NOTE, ticketId.longValue());
/*  954 */                 tn.setNotesText(request.getParameter("text"));
/*  955 */                 HelpDeskConfParser.getHelpDesk().handleTicketNotes(tn);
/*      */               }
/*      */             } catch (Exception e) {
/*  958 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  965 */           if ((annotationMessage != null) && (PluginUtil.isPlugin())) {
/*  966 */             String opmApiKey = RequestUtil.opm_apikey;
/*  967 */             String urlString = "/api/json/alarm/addNotes?apiKey=" + opmApiKey;
/*  968 */             Properties props = new Properties();
/*  969 */             props.setProperty("entity", entity);
/*  970 */             props.setProperty("notes", annotationMessage);
/*  971 */             String connResponse = RequestUtil.annotateOpmAlarms(urlString, props);
/*  972 */             AMLog.debug("Response from OPM After Annotation:" + connResponse);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  977 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*  981 */     if (failureEntity.isEmpty()) {
/*  982 */       annotationStatus = true;
/*      */     }
/*  984 */     if ("true".equals(bulkAnnotate)) {
/*  985 */       bulkAnnotation = true;
/*      */     }
/*  987 */     request.setAttribute("bulkAnnotate", Boolean.valueOf(bulkAnnotation));
/*  988 */     request.setAttribute("annotationMessage", annotationMessage);
/*  989 */     request.setAttribute("isAlertAnnotated", Boolean.valueOf(annotationStatus));
/*  990 */     if (isApiRequest) {
/*  991 */       return new ActionForward(null, false);
/*      */     }
/*  993 */     return mapping.findForward("annotationStatus");
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
/*      */   public ActionForward viewAnnotationAndHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/* 1015 */     String userName = (String)request.getSession().getAttribute("userName");
/* 1016 */     boolean isApiRequest = "true".equals(request.getParameter("isApiRequest"));
/* 1017 */     if ((isApiRequest) && (request.getParameter("username") != null)) {
/* 1018 */       userName = request.getParameter("username");
/*      */     }
/* 1020 */     String entity = request.getParameter("entity");
/* 1021 */     request.setAttribute("entity", entity);
/* 1022 */     Vector annotationValue = new Vector();
/*      */     
/*      */     try
/*      */     {
/* 1026 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1027 */       String query = "select distinct ENTITY,WHO,MODTIME,NOTES from ANNOTATION where ENTITY='" + entity + "' order by MODTIME desc";
/* 1028 */       ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1029 */       String ann = null;
/* 1030 */       while (rs.next())
/*      */       {
/* 1032 */         Properties p = new Properties();
/* 1033 */         p.put("modTime", FormatUtil.formatDT(rs.getString("MODTIME")));
/* 1034 */         p.put("modTimeMillis", "" + rs.getLong("MODTIME"));
/* 1035 */         p.put("entity", rs.getString("ENTITY"));
/* 1036 */         System.out.println("before String:" + rs.getString("NOTES"));
/* 1037 */         if (rs.getString("NOTES").equals("Picked up")) {
/* 1038 */           ann = FormatUtil.getString(rs.getString("NOTES"));
/*      */         }
/*      */         else {
/* 1041 */           ann = rs.getString("NOTES").substring(rs.getString("NOTES").indexOf(":") + 1);
/*      */         }
/* 1043 */         System.out.println("After String:" + ann);
/* 1044 */         p.put("notes", ann);
/* 1045 */         p.put("who", rs.getString("WHO"));
/* 1046 */         p.put("longTime", rs.getString("MODTIME"));
/* 1047 */         annotationValue.add(p);
/*      */       }
/* 1049 */       rs.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1053 */       e.printStackTrace();
/*      */     }
/* 1055 */     request.setAttribute("annotationValue", annotationValue);
/* 1056 */     Vector alertHistory = new Vector();
/*      */     
/* 1058 */     request.setAttribute("alertHistory", alertHistory);
/* 1059 */     if (isApiRequest) {
/* 1060 */       return new ActionForward(null, false);
/*      */     }
/* 1062 */     return mapping.findForward("annAndHistory");
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
/*      */   public ActionForward viewMergedAnnotationAndHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, ServletException
/*      */   {
/* 1084 */     String userName = (String)request.getSession().getAttribute("userName");
/*      */     
/* 1086 */     String entity = request.getParameter("entity");
/* 1087 */     request.setAttribute("entity", entity);
/* 1088 */     boolean isUserPermittedToViewHistory = GenericUtility.isAuthorized(userName, "Get Alert Annotation");
/* 1089 */     boolean isUserPermittedToViewAnnotation = GenericUtility.isAuthorized(userName, "Get Alert Annotation");
/* 1090 */     Vector mergedNotes = null;
/* 1091 */     if ((isUserPermittedToViewAnnotation) && (isUserPermittedToViewHistory))
/*      */     {
/* 1093 */       mergedNotes = AlarmOperationsUtility.getInstance().getAlertMergedNotes(userName, entity);
/*      */     }
/*      */     else
/*      */     {
/* 1097 */       ActionMessages actionMsg = new ActionMessages();
/* 1098 */       ActionMessage messageKey = new ActionMessage("webclient.fault.alarmoperations.authorized.mergednotes");
/* 1099 */       actionMsg.add("message", messageKey);
/* 1100 */       request.setAttribute("org.apache.struts.action.ACTION_MESSAGE", actionMsg);
/* 1101 */       return mapping.findForward("messagePage");
/*      */     }
/*      */     
/* 1104 */     authorizationCheckForOperations(userName, request);
/* 1105 */     request.setAttribute("mergedNotes", mergedNotes);
/* 1106 */     return mapping.findForward("viewMergedNotes");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward deleteAnnotation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1114 */     String redirect = "";
/* 1115 */     String deleteMessage = "&alertMessage=";
/*      */     try
/*      */     {
/* 1118 */       redirect = request.getParameter("redirect");
/* 1119 */       String entity = request.getParameter("entity");
/* 1120 */       String[] modTime = request.getParameterValues("checkbox");
/*      */       
/* 1122 */       String modTimeString = request.getParameter("modTimeString");
/* 1123 */       if ((modTime != null) || (modTimeString != null))
/*      */       {
/* 1125 */         String time = "";
/* 1126 */         if (modTimeString != null)
/*      */         {
/* 1128 */           time = modTimeString;
/*      */         }
/*      */         else
/*      */         {
/* 1132 */           for (int i = 0; i < modTime.length; i++)
/*      */           {
/* 1134 */             time = time + modTime[i];
/* 1135 */             if (i != modTime.length - 1)
/*      */             {
/* 1137 */               time = time + ",";
/*      */             }
/*      */           }
/*      */         }
/* 1141 */         String deletequery = "delete from ANNOTATION where ENTITY = '" + entity + "' and MODTIME in (" + time + ")";
/* 1142 */         AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1143 */         AMConnectionPool.executeUpdateStmt(deletequery);
/* 1144 */         if (EnterpriseUtil.isManagedServer)
/*      */         {
/* 1146 */           EnterpriseUtil.addUpdateQueryToFile(deletequery);
/* 1147 */           AMLog.debug("Query to delete annotation. Updated in file for Central synch ::: " + deletequery);
/*      */         }
/*      */       }
/* 1150 */       deleteMessage = deleteMessage + URLEncoder.encode(FormatUtil.getString("webclient.fault.alarmdetails.delete.text"), "UTF-8");
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1155 */       e.printStackTrace();
/* 1156 */       deleteMessage = deleteMessage + "Annotation(s) could not be deleted";
/*      */     }
/* 1158 */     return new ActionForward(redirect + deleteMessage, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward editAnnotation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1166 */     String redirect = "";
/*      */     try
/*      */     {
/* 1169 */       redirect = request.getParameter("redirect");
/* 1170 */       String entity = request.getParameter("entity");
/* 1171 */       String userName = request.getRemoteUser();
/* 1172 */       String actionFromCentral = request.getParameter("actionFromCentral") != null ? request.getParameter("actionFromCentral") : "false";
/*      */       
/* 1174 */       if (actionFromCentral.equalsIgnoreCase("true"))
/*      */       {
/* 1176 */         userName = request.getParameter("uName");
/*      */       }
/* 1178 */       String text = request.getParameter("text");
/* 1179 */       String time = request.getParameter("time");
/*      */       
/*      */ 
/* 1182 */       text = "Annotation : " + text.trim();
/* 1183 */       long modTime = System.currentTimeMillis();
/* 1184 */       String updatequery = "update ANNOTATION set WHO='" + userName + "', MODTIME='" + modTime + "', NOTES='" + text + "' where ENTITY = '" + entity + "' and MODTIME = '" + time + "'";
/* 1185 */       AMLog.debug("Query to edit annotation ::: " + updatequery);
/* 1186 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1187 */       int updatecount = 0;
/*      */       try {
/* 1189 */         updatecount = AMConnectionPool.executeUpdateStmt(updatequery);
/*      */       } catch (Exception ex) {
/* 1191 */         ex.printStackTrace();
/*      */       }
/*      */       
/* 1194 */       if ((EnterpriseUtil.isManagedServer()) && ("true".equalsIgnoreCase(actionFromCentral))) {
/*      */         try {
/* 1196 */           if (updatecount == 0) {
/* 1197 */             AMConnectionPool.executeUpdateStmt("insert into ANNOTATION values ('" + entity + "','" + userName + "'," + modTime + ",'NULL','" + text + "')");
/* 1198 */             AMLog.logSyncDebug("Old Annotation inserted into mas = insert into ANNOTATION values ('" + entity + "','" + userName + "'," + modTime + ",'NULL','" + text + "')");
/*      */           }
/*      */           
/* 1201 */           String deleteAdminAnnotate = " delete from ANNOTATION where ENTITY = '" + entity + "' and MODTIME = '" + time + "'";
/* 1202 */           EnterpriseUtil.addUpdateQueryToFile(deleteAdminAnnotate);
/* 1203 */           AMLog.logSyncDebug("Annotation deleted from admin this will add via synching =" + deleteAdminAnnotate);
/*      */         } catch (Exception ex1) {
/* 1205 */           ex1.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 1209 */       request.setAttribute("isAlertAnnotated", new Boolean(true));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1213 */       e.printStackTrace();
/*      */     }
/* 1215 */     return mapping.findForward("annotationStatus");
/*      */   }
/*      */   
/*      */   private ArrayList getAssociatedmonitorgroups(String resid) {
/* 1219 */     ManagedApplication mo = new ManagedApplication();
/* 1220 */     String query = "select RESOURCENAME from AM_PARENTCHILDMAPPER,AM_ManagedObject  where CHILDID=" + resid + " and RESOURCEID=PARENTID";
/* 1221 */     ArrayList rows = null;
/* 1222 */     rows = mo.getRows(query);
/*      */     
/*      */ 
/*      */ 
/* 1226 */     System.out.println("parentids" + rows);
/* 1227 */     if (rows != null)
/*      */     {
/* 1229 */       for (int i = 0; i < rows.size(); i++)
/*      */       {
/* 1231 */         ArrayList rowelement = (ArrayList)rows.get(i);
/* 1232 */         String parentid = (String)rowelement.get(0);
/* 1233 */         System.out.println("parentid" + parentid);
/*      */       }
/*      */     }
/*      */     
/* 1237 */     return rows;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward loadEventHistoryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1247 */     int pageIndex = 0;
/* 1248 */     String resourceid = null;
/* 1249 */     String entity = null;
/* 1250 */     PrintWriter out = null;
/*      */     try {
/* 1252 */       pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
/* 1253 */       String historyDetails = request.getParameter("historyDetails");
/* 1254 */       resourceid = request.getParameter("resid");
/* 1255 */       entity = request.getParameter("entity");
/* 1256 */       if ("availabilityHistory".equals(historyDetails)) {
/* 1257 */         String monitorType = DBUtil.getResourceType(resourceid);
/* 1258 */         entity = AMAttributesCache.getAvailabilityId(monitorType);
/* 1259 */         entity = resourceid + "_" + entity;
/*      */       }
/*      */       
/* 1262 */       Vector viewdata = DBUtil.getAlertData(entity, resourceid, pageIndex);
/* 1263 */       if (viewdata.isEmpty()) {
/* 1264 */         response.setHeader("noDataResponse", "true");
/*      */       }
/* 1266 */       String viewId = "Events";
/* 1267 */       String userName = (String)request.getSession().getAttribute("userName");
/* 1268 */       TableColumn[] headerList = EventViewUtil.getInstance().getHeaderList(viewId, userName);
/* 1269 */       request.setAttribute("headerList", headerList);
/* 1270 */       request.setAttribute("viewData", viewdata);
/* 1271 */       request.setAttribute("entity", entity);
/* 1272 */       request.setAttribute("dynamicContent", Boolean.valueOf(true));
/* 1273 */       request.setAttribute("pageIndex", Integer.valueOf(pageIndex));
/*      */     }
/*      */     catch (Exception ex) {
/* 1276 */       ex.printStackTrace();
/*      */     }
/* 1278 */     return mapping.findForward("eventHistoryDetails");
/*      */   }
/*      */   
/*      */ 
/*      */   private void authorizationCheckForOperations(String userName, HttpServletRequest request)
/*      */   {
/* 1284 */     boolean isUserPermittedToPickUp = GenericUtility.isAuthorized(userName, "Alert Pickup");
/* 1285 */     request.setAttribute("isUserPermittedToPickUp", new Boolean(isUserPermittedToPickUp));
/*      */     
/* 1287 */     String isAssign = WebClientUtil.getClientParameter(userName, "ASSIGN_ALERT");
/* 1288 */     request.setAttribute("isAssign", isAssign);
/*      */     
/* 1290 */     boolean isUserPermittedToClearAlert = GenericUtility.isAuthorized(userName, "Clear Alerts");
/* 1291 */     request.setAttribute("isUserPermittedToClearAlert", new Boolean(isUserPermittedToClearAlert));
/*      */     
/* 1293 */     boolean isUserPermittedToViewAnnotation = GenericUtility.isAuthorized(userName, "Get Alert Annotation");
/* 1294 */     request.setAttribute("isUserPermittedToViewAnnotation", new Boolean(isUserPermittedToViewAnnotation));
/*      */     
/* 1296 */     boolean isUserPermittedToSetAnnotation = GenericUtility.isAuthorized(userName, "Set Alert Annotation");
/* 1297 */     request.setAttribute("isUserPermittedToSetAnnotation", new Boolean(isUserPermittedToSetAnnotation));
/* 1298 */     boolean isUserPermittedToViewHistory = GenericUtility.isAuthorized(userName, "Get Alert History");
/* 1299 */     request.setAttribute("isUserPermittedToViewHistory", new Boolean(isUserPermittedToViewHistory));
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\webclient\fault\alarm\AlarmOperationsDispatchAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */