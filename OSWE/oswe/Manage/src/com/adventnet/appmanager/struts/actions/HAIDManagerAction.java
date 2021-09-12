/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMAttributesCache;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.fault.DependentDeviceUtil;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.fault.RuleAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.discovery.DiscoveryUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.framework.AMScriptProcess;
/*      */ import com.adventnet.appmanager.server.framework.AM_KeyValueDataCollector;
/*      */ import com.adventnet.appmanager.server.framework.MapViewServerUtil;
/*      */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.GroupComponent;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.AppManagerUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DowntimeScheduleUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.ParentChildRelationalUtil;
/*      */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*      */ import com.adventnet.appmanager.utils.client.MapViewUtil;
/*      */ import com.adventnet.appmanager.utils.client.UserConfigurationUtil;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import com.manageengine.appmanager.server.framework.AAMMonitorAdder;
/*      */ import com.manageengine.appmanager.utils.client.ClientAuditUtil;
/*      */ import com.manageengine.appmanager.vdesktops.vmware.VMwareViewDataCollector;
/*      */ import com.manageengine.appmanager.vservers.util.VMMonitorDBUtil;
/*      */ import com.manageengine.appmanager.vservers.vmware.VCenterDataCollector;
/*      */ import com.me.apm.cmdb.CIManagedObject;
/*      */ import com.me.apm.eventlog.util.EventLogUtil;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.security.Principal;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.ServletConfig;
/*      */ import javax.servlet.ServletContext;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.commons.lang3.StringEscapeUtils;
/*      */ import org.apache.struts.action.ActionError;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.action.ActionServlet;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ import org.htmlparser.util.Translate;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class HAIDManagerAction
/*      */   extends DispatchAction
/*      */ {
/*  111 */   private ManagedApplication mo = new ManagedApplication();
/*  112 */   DiscoveryUtil discUtil = new DiscoveryUtil();
/*  113 */   public static ClientAuditUtil cliAuditUtil = new ClientAuditUtil();
/*  114 */   MGActionNotifier mgNotifier = MGActionNotifier.getInstance();
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  121 */     String applicationName = (String)((DynaActionForm)form).get("name");
/*  122 */     String description = (String)((DynaActionForm)form).get("description");
/*      */     
/*  124 */     applicationName = StringEscapeUtils.unescapeXml(applicationName);
/*  125 */     description = StringEscapeUtils.unescapeXml(description);
/*  126 */     String username = (String)((DynaActionForm)form).get("owner");
/*  127 */     String locationID = (String)((DynaActionForm)form).get("locationid");
/*  128 */     String groupType = (String)((DynaActionForm)form).get("grouptype");
/*  129 */     String createMV = (String)((DynaActionForm)form).get("createMV");
/*  130 */     String parentHaid = request.getParameter("parentHaid");
/*  131 */     String bsgUserName = request.getUserPrincipal().getName();
/*  132 */     int groupType_int = Integer.parseInt(groupType);
/*  133 */     boolean isSubGroup = false;
/*  134 */     ActionMessages messages = new ActionMessages();
/*  135 */     ActionErrors errors = new ActionErrors();
/*      */     
/*  137 */     if (!isTokenValid(request)) {
/*  138 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("error.multiple-submit"));
/*  139 */       saveMessages(request, messages);
/*      */       
/*  141 */       ArrayList lists = this.mo.getRows("select mo.RESOURCEID from AM_ManagedObject mo,AM_HOLISTICAPPLICATION ha where mo.RESOURCEID=ha.HAID and mo.RESOURCENAME='" + applicationName + "' and mo.TYPE='HAI' and ha.TYPE=0");
/*  142 */       String haid = null;
/*  143 */       if (lists.size() > 0)
/*      */       {
/*  145 */         ArrayList list = (ArrayList)lists.get(0);
/*  146 */         haid = (String)list.get(0);
/*      */       }
/*      */       
/*  149 */       ActionForward frwrd = mapping.findForward("showapplication");
/*  150 */       String path = null;
/*  151 */       if (haid != null) {
/*  152 */         return new ActionForward(frwrd.getPath() + "&haid=" + haid, true);
/*      */       }
/*  154 */       return new ActionForward(frwrd.getPath(), true);
/*      */     }
/*      */     
/*      */ 
/*  158 */     resetToken(request);
/*      */     
/*  160 */     ArrayList lists = this.mo.getRows("select * from AM_ManagedObject mo,AM_HOLISTICAPPLICATION ha where mo.RESOURCEID=ha.HAID and mo.RESOURCENAME='" + applicationName + "' and mo.TYPE='HAI' and ha.TYPE=0");
/*  161 */     if (lists.size() > 0)
/*      */     {
/*      */ 
/*  164 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("haid.applicationcreation.namealreadyexists", applicationName));
/*  165 */       saveMessages(request, messages);
/*  166 */       return new ActionForward("/jsp/CreateApplication.jsp");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  171 */       AMManagedObject ammo = this.mo.createManagedApplication(applicationName, description, username, null, null, isSubGroup, groupType_int);
/*  172 */       if (ammo != null)
/*      */       {
/*      */ 
/*  175 */         int haid = ammo.getRESOURCEID();
/*  176 */         String[] owners = (String[])((DynaActionForm)form).get("selectedowners_list");
/*  177 */         if (ClientDBUtil.isPrivilegedUser(request)) {
/*  178 */           String userid = (String)DBUtil.username_userid_mapping.get(request.getRemoteUser());
/*  179 */           boolean userSelected = false;
/*  180 */           for (String id : owners) {
/*  181 */             if (userid.equalsIgnoreCase(id)) {
/*  182 */               userSelected = true;
/*  183 */               break;
/*      */             }
/*      */           }
/*  186 */           if (!userSelected) {
/*  187 */             ArrayList<String> ownerList = new ArrayList(Arrays.asList(owners));
/*  188 */             ownerList.add(userid);
/*  189 */             owners = (String[])ownerList.toArray(new String[ownerList.size()]);
/*      */           }
/*      */         }
/*      */         
/*  193 */         String auditLogMsg = FormatUtil.getString("am.audit.addMonitorGroup.msg", new String[] { ammo.getRESOURCENAME() });
/*      */         
/*  195 */         cliAuditUtil.addToAuditLog(request, haid, 1, auditLogMsg);
/*      */         
/*  197 */         addMonitorGroup(haid, locationID);
/*      */         
/*  199 */         ((DynaActionForm)form).set("haid", String.valueOf(haid));
/*  200 */         ((DynaActionForm)form).set("creationdate", String.valueOf(System.currentTimeMillis()));
/*  201 */         request.setAttribute("haid", String.valueOf(haid));
/*      */         
/*  203 */         addOwner(owners, haid);
/*      */         
/*  205 */         Properties applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/*  206 */         applications.setProperty(String.valueOf(haid), applicationName);
/*  207 */         String path = mapping.findForward("showapplication").getPath();
/*      */         
/*      */         try
/*      */         {
/*  211 */           if (this.mgNotifier.shouldNotify())
/*      */           {
/*  213 */             AMLog.debug("Monitor group creation notifier -" + this.mgNotifier);
/*  214 */             Hashtable toNotify = new Hashtable();
/*  215 */             toNotify.put("MGID", Integer.toString(haid));
/*  216 */             toNotify.put("MGNAME", applicationName);
/*  217 */             toNotify.put("EventType", "Updated");
/*  218 */             this.mgNotifier.setProperties(toNotify);
/*  219 */             Scheduler.getScheduler("main").scheduleTask(this.mgNotifier, System.currentTimeMillis());
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  224 */           AMLog.debug("Exception occurred while notifying the monitor group creation to the observers ; the mg with haid " + haid);
/*  225 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  229 */         boolean isSubgroup = false;
/*  230 */         parentHaid = Constants.getParentHaid(haid + "");
/*  231 */         if (!parentHaid.equals("-1"))
/*      */         {
/*  233 */           isSubgroup = MapViewUtil.isSubgroup(Integer.toString(haid));
/*      */         }
/*  235 */         String mapViewId = MapViewUtil.getMapViewIdForBSGId(parentHaid);
/*      */         
/*  237 */         if ("createMV".equalsIgnoreCase(createMV))
/*      */         {
/*      */           try
/*      */           {
/*      */ 
/*  242 */             AMLog.debug("Creating mapview for BSG " + haid);
/*  243 */             MapViewUtil.createMVForBsg(Integer.toString(haid), username);
/*  244 */             AMLog.debug("Created !!");
/*      */             
/*      */ 
/*  247 */             ((DynaActionForm)form).set("createMV", "");
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  251 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */         try
/*      */         {
/*  257 */           if ("2".equals(groupType))
/*      */           {
/*  259 */             addWebGroup(request, haid);
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  264 */           ex.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  268 */         path = path + "&createNew=true";
/*  269 */         String hidden = request.getParameter("addmonitors");
/*  270 */         if ((hidden != null) && (hidden.equals("0")))
/*      */         {
/*  272 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/*  274 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.admin.success"));
/*      */           }
/*      */           else
/*      */           {
/*  278 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.success"));
/*      */           }
/*      */           
/*  281 */           if (applicationName.length() > 28)
/*      */           {
/*  283 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.success.exceedslength", getTrimmedText(applicationName, 25)));
/*      */           }
/*  285 */           saveMessages(request, messages);
/*  286 */           return new ActionForward(path + "&haid=" + haid, true);
/*      */         }
/*      */         
/*      */ 
/*  290 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.returntoaddmonitors.success"));
/*  291 */         if (applicationName.length() > 28)
/*      */         {
/*  293 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.success.exceedslength", getTrimmedText(applicationName, 25)));
/*      */         }
/*      */         
/*  296 */         path = mapping.findForward("associatemonitorsinwiz").getPath();
/*  297 */         return new ActionForward(path + "&wiz=true&haid=" + haid, true);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  306 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", "Unable to create application"));
/*  307 */       saveErrors(request, errors);
/*  308 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  313 */       se.printStackTrace();
/*  314 */       int errorcode = se.getErrorCode();
/*  315 */       if (errorcode == 1062)
/*      */       {
/*  317 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.namealreadyexists", applicationName));
/*      */       }
/*      */       else
/*      */       {
/*  321 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", se.toString()));
/*      */       }
/*  323 */       saveErrors(request, errors);
/*  324 */       return mapping.getInputForward();
/*      */ 
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  329 */       ee.printStackTrace();
/*  330 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationcreation.failed", ee.toString()));
/*  331 */       saveErrors(request, errors); }
/*  332 */     return mapping.getInputForward();
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
/*      */   public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  346 */     ActionMessages messages = new ActionMessages();
/*  347 */     ActionErrors errors = new ActionErrors();
/*  348 */     String[] haids = request.getParameterValues("select");
/*  349 */     MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/*  350 */     Hashtable toNotifier = null;
/*  351 */     Map<String, String> mapChildParent = new HashMap();
/*      */     
/*  353 */     Vector<String> allHaidsOwners = new Vector();
/*      */     
/*      */ 
/*  356 */     String mapView = (String)request.getAttribute("isMapView");
/*  357 */     if ("true".equalsIgnoreCase(mapView))
/*      */     {
/*  359 */       String bsgId = (String)request.getAttribute("bsgId");
/*  360 */       if (bsgId != null)
/*      */       {
/*  362 */         haids = new String[1];
/*  363 */         haids[0] = bsgId;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  368 */     String redirecttoMG = null;
/*  369 */     if (request.getAttribute("selectresids") != null)
/*      */     {
/*  371 */       haids = (String[])request.getAttribute("selectresids");
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  377 */         if ((haids != null) && (haids.length == 1))
/*      */         {
/*  379 */           redirecttoMG = this.mo.getParentMGResourceid(haids[0]);
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  384 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  390 */     Vector childMgs = new Vector();
/*  391 */     for (int i = 0; (haids != null) && (i < haids.length); i++)
/*      */     {
/*  393 */       childMgs.add(haids[i]);
/*  394 */       Vector tempChild = new Vector();
/*  395 */       ManagedApplication.getChildIDs(tempChild, haids[i] + "");
/*  396 */       childMgs.addAll(tempChild);
/*  397 */       for (int j = 0; j < tempChild.size(); j++) {
/*  398 */         mapChildParent.put((String)tempChild.get(j), haids[i]);
/*      */       }
/*      */     }
/*      */     
/*  402 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */     {
/*      */ 
/*  405 */       RestrictedUsersViewUtil.getAllOwnersInMGTree(allHaidsOwners, haids);
/*      */     }
/*      */     
/*  408 */     String[] temphaids = new String[childMgs.size()];
/*  409 */     Hashtable<String, Hashtable<String, JSONArray>> table = new Hashtable();
/*      */     try {
/*  411 */       for (int count = 0; count < childMgs.size(); count++)
/*      */       {
/*  413 */         temphaids[count] = ((String)childMgs.get(count));
/*  414 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/*  416 */           Hashtable<String, JSONArray> mgTable = DBUtil.getMonitorsInAdminMG(temphaids[count], null);
/*  417 */           table.put(temphaids[count], mgTable);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  424 */       ex.printStackTrace();
/*      */     }
/*  426 */     if ((notifyConsole.shouldNotify()) && (haids != null)) {
/*  427 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  430 */         toNotifier = new Hashtable();
/*  431 */         boolean isSubGroup = false;
/*  432 */         Set<String> mgIds = new HashSet(Arrays.asList(haids));
/*  433 */         mgIds.addAll(Arrays.asList(temphaids));
/*  434 */         String query = "select mo.RESOURCENAME,mo.DISPLAYNAME,ha.HAID,mgt.TYPE from AM_ManagedObject mo join AM_HOLISTICAPPLICATION ha on ha.HAID=mo.RESOURCEID left join AM_MONITORGROUP_TYPES mgt on mgt.TYPEID=ha.GROUPTYPE where RESOURCEID in (" + mgIds.toString().substring(1, mgIds.toString().length() - 1) + ")";
/*  435 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  436 */         List<String> l = new ArrayList(haids.length);
/*  437 */         List<String> l1 = new ArrayList(haids.length);
/*  438 */         while (rs.next())
/*      */         {
/*  440 */           l.add(rs.getString("RESOURCENAME"));
/*  441 */           CIManagedObject ciMo = new CIManagedObject(rs.getString("RESOURCENAME"), rs.getString("DISPLAYNAME"), Integer.parseInt(rs.getString("HAID")), rs.getString("TYPE"));
/*  442 */           ciMo.setResGrp("HAI");
/*  443 */           ciMo.setResSubGrp("HAI");
/*  444 */           l1.add(ciMo.getCIResName());
/*      */         }
/*  446 */         toNotifier.put("MGNames", l.toArray(new String[0]));
/*  447 */         toNotifier.put("CINames", l1.toArray(new String[0]));
/*  448 */         toNotifier.put("MGIDs", mgIds.toArray(new String[0]));
/*  449 */         toNotifier.put("action", "Delete");
/*  450 */         toNotifier.put("userName", request.getUserPrincipal().toString());
/*  451 */         toNotifier.put("cookies", request.getCookies().clone());
/*  452 */         toNotifier.put("EventType", "Deleted");
/*  453 */         notifyConsole.setProperties(toNotifier);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  457 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  461 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  466 */     int result = this.mo.deleteApplications(temphaids);
/*      */     Enumeration<String> enu;
/*  468 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*  470 */       for (enu = table.keys(); enu.hasMoreElements();)
/*      */       {
/*  472 */         String resourceid = (String)enu.nextElement();
/*  473 */         Hashtable mgTable = (Hashtable)table.get(resourceid);
/*  474 */         EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(mgTable, resourceid, 2);
/*      */       }
/*      */     }
/*  477 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  485 */       if (temphaids.length > 0)
/*      */       {
/*  487 */         MapViewServerUtil.deleteMapView(temphaids);
/*  488 */         AMLog.debug("Deleting the mapviews for subgroups " + temphaids.toString());
/*      */       }
/*      */       
/*  491 */       if ((haids != null) && (haids.length > 0))
/*      */       {
/*  493 */         MapViewServerUtil.deleteMapView(haids);
/*  494 */         AMLog.debug("Deleting the mapviews for BSGs " + haids.toString());
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  499 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*  503 */     if (result == 1)
/*      */     {
/*  505 */       ServletContext servletContext = request.getSession().getServletContext();
/*  506 */       Hashtable ht = new Hashtable();
/*  507 */       if (servletContext != null) {
/*  508 */         ht = (Hashtable)servletContext.getAttribute("applications");
/*      */       } else {
/*  510 */         ht = (Hashtable)request.getAttribute("applications");
/*      */       }
/*      */       try {
/*  513 */         Vector ParentMGs = new Vector();
/*  514 */         for (int i = 0; (haids != null) && (i < haids.length); i++)
/*      */         {
/*  516 */           ParentMGs.add(haids[i]);
/*  517 */           Vector tempParent = new Vector();
/*  518 */           this.mo.getParentMGs(tempParent, haids[i] + "");
/*  519 */           ParentMGs.addAll(tempParent);
/*  520 */           if (tempParent.size() > 0) {
/*  521 */             mapChildParent.put(haids[i], (String)tempParent.get(0));
/*      */           }
/*      */         }
/*  524 */         for (int j = 0; j < ParentMGs.size(); j++)
/*      */         {
/*  526 */           new AMRCAnalyser().applyRCA(Integer.parseInt((String)ParentMGs.get(j)), 17, System.currentTimeMillis(), true, true, 1);
/*  527 */           new AMRCAnalyser().applyRCA(Integer.parseInt((String)ParentMGs.get(j)), 18, System.currentTimeMillis(), true, false, 2);
/*      */         }
/*      */         
/*  530 */         for (int i = 0; (temphaids != null) && (i < temphaids.length); i++)
/*      */         {
/*  532 */           if (EnterpriseUtil.isManagedServer())
/*      */           {
/*  534 */             EnterpriseUtil.addBAsForDelete(String.valueOf(temphaids[i]));
/*      */           }
/*  536 */           if (ht.containsKey(String.valueOf(temphaids[i])))
/*      */           {
/*  538 */             ht.remove(temphaids[i]);
/*      */           }
/*      */           
/*  541 */           String deletequery = "delete from AM_PARENTCHILDMAPPER where CHILDID=" + temphaids[i];
/*  542 */           this.mo.executeUpdateStmt(deletequery);
/*  543 */           EnterpriseUtil.addUpdateQueryToFile(deletequery);
/*  544 */           deletequery = "delete from AM_RCAMAPPER where CHILDRESOURCEID=" + temphaids[i];
/*  545 */           this.mo.executeUpdateStmt(deletequery);
/*  546 */           EnterpriseUtil.addUpdateQueryToFile(deletequery);
/*  547 */           deletequery = "DELETE FROM AM_DEPENDENTMONITOR WHERE CHILDID='" + temphaids[i] + "'";
/*  548 */           this.mo.executeUpdateStmt(deletequery);
/*      */         }
/*      */         
/*      */ 
/*  552 */         DBUtil.taborderList.clear();
/*  553 */         DBUtil.cacheOrderedTabIdList();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  557 */         e.printStackTrace();
/*      */       }
/*  559 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationdeletion.success", "Application(s) Successfully deleted"));
/*  560 */       saveMessages(request, messages);
/*      */       
/*  562 */       if (notifyConsole.shouldNotify())
/*      */       {
/*  564 */         AMLog.debug("BusinessService deletion notifier -" + toNotifier);
/*  565 */         notifyConsole.setProperties(toNotifier);
/*  566 */         Scheduler.getScheduler("main").scheduleTask(notifyConsole, System.currentTimeMillis());
/*      */       }
/*      */       
/*  569 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*  571 */         List<String> uList = new ArrayList();
/*      */         try
/*      */         {
/*  574 */           if (!allHaidsOwners.isEmpty())
/*      */           {
/*  576 */             Iterator<String> it = allHaidsOwners.iterator();
/*  577 */             while (it.hasNext())
/*      */             {
/*  579 */               String uid = (String)it.next();
/*  580 */               if ((!uList.contains(uid)) && (RestrictedUsersViewUtil.isRestrictedRole(uid)))
/*      */               {
/*  582 */                 uList.add(uid);
/*      */               }
/*      */             }
/*  585 */             if (!uList.isEmpty()) {
/*  586 */               AMLog.debug("[HAIDManagerAction::(delete)]ruser(s) : " + uList);
/*  587 */               RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(uList);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  593 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  601 */         for (int i = 0; (haids != null) && (i < haids.length); i++)
/*      */         {
/*  603 */           String tempId = haids[i];
/*  604 */           String auditLogMsg = "";
/*      */           int id;
/*  606 */           int operation; if (mapChildParent.containsKey(tempId)) {
/*  607 */             int id = Integer.parseInt((String)mapChildParent.get(tempId));
/*  608 */             int operation = 4;
/*  609 */             auditLogMsg = FormatUtil.getString("am.audit.deletesubgroup.msg", new String[] { DBUtil.getDisplaynameforResourceID(tempId), DBUtil.getDisplaynameforResourceID(id + "") });
/*      */           }
/*      */           else {
/*  612 */             id = Integer.parseInt(tempId);
/*  613 */             operation = 3;
/*  614 */             auditLogMsg = FormatUtil.getString("am.audit.deleteMonitorGroup.msg", new String[] { DBUtil.getDisplaynameforResourceID(tempId) });
/*      */           }
/*  616 */           cliAuditUtil.addToAuditLog(request, id, operation, auditLogMsg);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  620 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/*  626 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("haid.applicationdeletion.failed", "Unable to remove the applications"));
/*  627 */       saveErrors(request, errors);
/*      */     }
/*  629 */     UserConfigurationUtil.updateUserPrivileges(request, null);
/*  630 */     if ((Constants.isIt360) && ("true".equalsIgnoreCase(mapView)))
/*      */     {
/*  632 */       return new ActionForward("/MyPage.do?method=viewDashBoard");
/*      */     }
/*      */     
/*      */ 
/*  636 */     if ((request.getAttribute("fromwhere") != null) && (request.getAttribute("fromwhere").equals("monitorgroupview")))
/*      */     {
/*  638 */       if (request.getAttribute("todelete") != null)
/*      */       {
/*  640 */         return new ActionForward("/jsp/deleteMonitorGroup.jsp?todelete=afterdeleting");
/*      */       }
/*      */       
/*      */ 
/*  644 */       return new ActionForward("/jsp/deleteMonitorGroup.jsp?todelete=afterdeletingMGs");
/*      */     }
/*      */     
/*  647 */     if (redirecttoMG != null)
/*      */     {
/*  649 */       return new ActionForward("/showapplication.do?method=showApplication&messagetoshow=subgroupdeleted&haid=" + redirecttoMG, true);
/*      */     }
/*  651 */     if (Constants.isIt360)
/*      */     {
/*  653 */       return new ActionForward("/MyPage.do?method=viewDashBoard", true);
/*      */     }
/*  655 */     return new ActionForward("/applications.do");
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
/*      */   public ActionForward removeMonitors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  669 */     String removefromhaid = request.getParameter("removefromhaid");
/*  670 */     String[] monitors = request.getParameterValues("monitors");
/*  671 */     if ((monitors == null) && ((String[])request.getAttribute("monitors") != null)) {
/*  672 */       monitors = (String[])request.getAttribute("monitors");
/*      */     }
/*  674 */     if ((request.getParameter("MonitorGroup") != null) && (request.getParameter("MonitorGroup").equals("MonitorGroup")))
/*      */     {
/*  676 */       monitors = request.getParameterValues("monitorsMg");
/*      */     }
/*      */     
/*  679 */     if ((monitors != null) && (System.getProperty("EnterpriseSearch", "false").equalsIgnoreCase("true")))
/*      */     {
/*  681 */       ArrayList<String> pk1 = new ArrayList();
/*  682 */       for (int i = 0; i < monitors.length; i++)
/*      */       {
/*  684 */         pk1.add(monitors[i]);
/*      */       }
/*  686 */       ClientDBUtil.triggerDataChangeEvent("AM_ManagedObject", "update", pk1, null, null);
/*      */     }
/*      */     
/*  689 */     String uri = (String)request.getAttribute("uri");
/*  690 */     String haid = "";
/*  691 */     if ((String[])request.getAttribute("monitors") == null) {
/*  692 */       boolean fromAssociate = uri.indexOf("removeMonitors.do") != -1;
/*  693 */       if (fromAssociate) {
/*  694 */         haid = ((AMActionForm)form).getHaid();
/*      */       } else {
/*  696 */         haid = (String)((DynaActionForm)form).get("haid");
/*      */       }
/*      */     }
/*  699 */     String redirecto = haid;
/*  700 */     if (removefromhaid != null)
/*      */     {
/*  702 */       haid = removefromhaid;
/*      */     }
/*  704 */     String fromwhere = (String)request.getAttribute("fromwhere");
/*  705 */     if ((fromwhere != null) && (fromwhere.equals("monitorgroupview")))
/*      */     {
/*  707 */       request.setAttribute("fromwhere", fromwhere);
/*      */     }
/*  709 */     Vector nwdIds = new Vector();
/*  710 */     Hashtable<String, JSONArray> mgTable = new Hashtable();
/*  711 */     DBUtil.deleteBussinessViewCache();
/*  712 */     EventLogUtil.removemonitorFromEventLog(monitors, haid);
/*      */     
/*  714 */     for (int i = 0; i < monitors.length; i++)
/*      */     {
/*  716 */       int childID = Integer.parseInt(monitors[i]);
/*  717 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  719 */         String masID = EnterpriseUtil.getManagedServerIndex(childID) + "";
/*  720 */         JSONArray jsArr = (JSONArray)mgTable.get(masID);
/*  721 */         if (jsArr == null)
/*      */         {
/*  723 */           jsArr = new JSONArray();
/*  724 */           jsArr.put(childID);
/*  725 */           mgTable.put(masID, jsArr);
/*      */         }
/*      */         else
/*      */         {
/*  729 */           jsArr.put(childID);
/*      */         }
/*      */       }
/*  732 */       String deletequery = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + haid + " and CHILDID=" + childID;
/*  733 */       int rowsdeleted = this.mo.executeUpdateStmt(deletequery);
/*  734 */       EnterpriseUtil.addUpdateQueryToFile(deletequery);
/*      */       
/*  736 */       deletequery = "delete from AM_RCAMAPPER where PARENTRESOURCEID=" + haid + " and CHILDRESOURCEID=" + childID;
/*  737 */       EnterpriseUtil.addUpdateQueryToFile(deletequery);
/*  738 */       rowsdeleted = this.mo.executeUpdateStmt(deletequery);
/*      */       try
/*      */       {
/*  741 */         String auditLogMsg = FormatUtil.getString("am.audit.monitorremove.msg", new String[] { DBUtil.getDisplaynameforResourceID(monitors[i]), DBUtil.getDisplaynameforResourceID(haid) });
/*  742 */         cliAuditUtil.addToAuditLog(request, Integer.parseInt(haid), 6, auditLogMsg);
/*      */       }
/*      */       catch (Exception e) {
/*  745 */         e.printStackTrace();
/*      */       }
/*      */       
/*  748 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */       {
/*  750 */         List<String> uList = new ArrayList();
/*      */         
/*  752 */         Vector<String> allOwnersInHaidHierarchy = new Vector();
/*  753 */         RestrictedUsersViewUtil.getAllOwnersInMGTree(allOwnersInHaidHierarchy, new String[] { haid });
/*  754 */         if (!allOwnersInHaidHierarchy.isEmpty())
/*      */         {
/*  756 */           Iterator<String> it = allOwnersInHaidHierarchy.iterator();
/*  757 */           while (it.hasNext())
/*      */           {
/*  759 */             String uid = (String)it.next();
/*  760 */             if ((!uList.contains(uid)) && (RestrictedUsersViewUtil.isRestrictedRole(uid)))
/*      */             {
/*  762 */               uList.add(uid);
/*      */             }
/*      */           }
/*  765 */           if (!uList.isEmpty())
/*      */           {
/*  767 */             AMLog.debug("[HAIDManagerAction::(removeMonitors)]ruser(s) : " + uList);
/*  768 */             RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(uList);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  774 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/*  775 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*  776 */       ResultSet rs = null;
/*  777 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       try
/*      */       {
/*  780 */         String extdevquery = " select PARENTID,AM_ManagedResourceType.RESOURCEGROUP,CHILDID   from AM_ManagedObject   left outer join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE left outer join  AM_AssociatedExtDevices on AM_AssociatedExtDevices.RESID=AM_ManagedObject .RESOURCEID and AM_AssociatedExtDevices.RESID is not NULL left outer join AM_PARENTCHILDMAPPER on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID  where  AM_ManagedObject.RESOURCEID=" + childID;
/*  781 */         rs = AMConnectionPool.executeQueryStmt(extdevquery);
/*  782 */         if (rs.next())
/*      */         {
/*  784 */           String resourcegroup = rs.getString("RESOURCEGROUP");
/*  785 */           if ((resourcegroup.equals("NWD")) || (resourcegroup.equals("SAN")))
/*      */           {
/*  787 */             if (rs.getString("PARENTID") == null)
/*      */             {
/*  789 */               nwdIds.add(childID + "");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  796 */         ex.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  800 */         if (rs != null)
/*      */         {
/*  802 */           rs.close(); }
/*      */       } }
/*      */     Hashtable<String, Hashtable<String, JSONArray>> subMgTable;
/*      */     Enumeration<String> enu;
/*  806 */     if ((EnterpriseUtil.isAdminServer()) && (AMCacheHandler.getDependentDevice(haid) != null))
/*      */     {
/*  808 */       EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(mgTable, haid, 2);
/*      */       
/*  810 */       subMgTable = DBUtil.getAllMonitorsUnderSubGroupForMonitorGroup(haid, true);
/*  811 */       for (enu = subMgTable.keys(); enu.hasMoreElements();)
/*      */       {
/*  813 */         String subGroupID = (String)enu.nextElement();
/*  814 */         Hashtable<String, JSONArray> subTable = (Hashtable)subMgTable.get(subGroupID);
/*  815 */         if (AMCacheHandler.getDependentDevice(subGroupID) == null)
/*      */         {
/*  817 */           EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(subTable, subGroupID, 2);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  822 */     String mapViewId = MapViewUtil.getMapViewIdForBSGId(haid);
/*  823 */     if ((mapViewId != null) && (monitors != null)) {
/*  824 */       Vector<String> strArrayToVector = new Vector(Arrays.asList(monitors));
/*      */       
/*  826 */       String unselDevices = strArrayToVector.toString();
/*  827 */       unselDevices = unselDevices.substring(1, unselDevices.length() - 1);
/*  828 */       if ((unselDevices != null) && (unselDevices.length() != 0))
/*      */       {
/*  830 */         unselDevices = unselDevices.substring(0, unselDevices.length());
/*      */       }
/*  832 */       String[] devicesToDelete = unselDevices.split(",");
/*  833 */       for (String deviceName : devicesToDelete)
/*      */       {
/*  835 */         deviceName = deviceName.trim();
/*  836 */         MapViewUtil.deleteDeviceAndLinks(deviceName, mapViewId);
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
/*      */ 
/*      */ 
/*  849 */     if (nwdIds.size() > 0)
/*      */     {
/*  851 */       DataCollectionControllerUtil.deleteMO(haid, nwdIds, true);
/*  852 */       if (EnterpriseUtil.isManagedServer())
/*      */       {
/*  854 */         FileOutputStream delmofos = null;
/*      */         
/*      */         try
/*      */         {
/*  858 */           File commands = new File(EnterpriseUtil.deleteDetailsFilePath);
/*  859 */           commands.createNewFile();
/*  860 */           Properties prop = new Properties();
/*      */           try
/*      */           {
/*  863 */             prop.load(new FileInputStream(commands));
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  867 */             ex.printStackTrace();
/*      */           }
/*  869 */           String id = "";
/*  870 */           if (prop.containsKey(EnterpriseUtil.deleteMonitorsKey))
/*      */           {
/*  872 */             id = prop.getProperty(EnterpriseUtil.deleteMonitorsKey) + ",";
/*      */           }
/*  874 */           for (int i = 0; i < nwdIds.size(); i++)
/*      */           {
/*  876 */             id = id + (String)nwdIds.get(i) + ",";
/*      */           }
/*  878 */           prop.setProperty(EnterpriseUtil.deleteMonitorsKey, id.substring(0, id.length() - 1));
/*  879 */           delmofos = new FileOutputStream(commands);
/*  880 */           prop.store(delmofos, "");
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  887 */           if (delmofos != null) {
/*      */             try {
/*  889 */               delmofos.close();
/*      */             }
/*      */             catch (Exception exc) {}
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  897 */           updateQuery = "update AM_HOLISTICAPPLICATION set MODIFIEDDATE=" + System.currentTimeMillis() + "  where HAID=" + haid;
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  884 */           e.printStackTrace();
/*      */         }
/*      */         finally {
/*  887 */           if (delmofos != null) {
/*      */             try {
/*  889 */               delmofos.close();
/*      */             }
/*      */             catch (Exception exc) {}
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     String updateQuery;
/*  898 */     this.mo.executeUpdateStmt(updateQuery);
/*  899 */     EnterpriseUtil.addUpdateQueryToFile(updateQuery);
/*  900 */     ActionMessages messages = new ActionMessages();
/*  901 */     String message1 = "The selected Monitor(s) have been successfully removed from the Monitor Group.";
/*  902 */     saveMessages(request, messages);
/*  903 */     MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/*  904 */     if (notifyConsole.shouldNotify()) {
/*      */       try
/*      */       {
/*  907 */         Hashtable toNotifier = new Hashtable();
/*  908 */         toNotifier.put("MGID", this.mo.getTOPLevelMG(haid));
/*  909 */         toNotifier.put("EventType", "Updated");
/*  910 */         notifyConsole.setProperties(toNotifier);
/*  911 */         Thread t = new Thread(notifyConsole);
/*  912 */         t.start();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  916 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     try {
/*  920 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*  922 */         DowntimeScheduleUtil.updateMASSYNCHDETAILS(haid, "edit");
/*      */       }
/*      */     }
/*      */     catch (Exception ee)
/*      */     {
/*  927 */       ee.printStackTrace();
/*      */     }
/*  929 */     if ((request.getParameter("MonitorGroup") != null) && (request.getParameter("MonitorGroup").equals("MonitorGroup")))
/*      */     {
/*  931 */       return new ActionForward("/showresource.do?method=getMonitorForm&showGroupOnly=true");
/*      */     }
/*  933 */     if ((request.getParameter("fromMGView") != null) && ("true".equals(request.getParameter("fromMGView"))))
/*      */     {
/*  935 */       return new ActionForward("/showresource.do?group=All&method=showMonitorGroupView", true);
/*      */     }
/*  937 */     if (redirecto != null)
/*      */     {
/*  939 */       if (request.getParameter("redirecToMonitor") != null)
/*      */       {
/*  941 */         return new ActionForward("/showresource.do?resourceid=" + monitors[0] + "&method=showResourceForResourceID", true);
/*      */       }
/*      */       
/*      */ 
/*  945 */       return new ActionForward("/showresource.do?method=addResource&savetype=3");
/*      */     }
/*      */     
/*  948 */     return mapping.findForward("showapplication");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward showCreateApplicationForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  957 */     Hashtable globalconfig = (Hashtable)getServlet().getServletContext().getAttribute("globalconfig");
/*  958 */     String helpwizard = null;
/*  959 */     if (globalconfig != null)
/*  960 */       helpwizard = (String)globalconfig.get("showwizhelp");
/*  961 */     if ((helpwizard == null) || (helpwizard.equals("false")))
/*      */     {
/*  963 */       return new ActionForward("/admin/createapplication.do", true);
/*      */     }
/*      */     
/*      */ 
/*  967 */     return new ActionForward("/jsp/CreateApplicationMessage.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward AddSubGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  978 */     String applicationName = (String)((DynaActionForm)form).get("name");
/*  979 */     String description = (String)((DynaActionForm)form).get("description");
/*  980 */     String locationID = (String)((DynaActionForm)form).get("locationid");
/*  981 */     String haid = request.getParameter("haid");
/*  982 */     String[] owner = (String[])request.getParameterValues("selectedowners_list");
/*  983 */     if (owner == null) {
/*  984 */       String userid = (String)DBUtil.username_userid_mapping.get(request.getRemoteUser());
/*  985 */       ArrayList<String> ownerList = new ArrayList();
/*  986 */       ownerList.add(userid);
/*  987 */       owner = (String[])ownerList.toArray(new String[ownerList.size()]);
/*      */     }
/*      */     try
/*      */     {
/*  991 */       ((DynaActionForm)form).set("haid", haid);
/*  992 */       mgtype = (String)((DynaActionForm)form).get("grouptype");
/*  993 */       ActionMessages messages = new ActionMessages();
/*  994 */       ActionErrors errors = new ActionErrors();
/*  995 */       ResultSet rs = null;
/*  996 */       PreparedStatement ps = null;
/*  997 */       boolean name_exist = false;
/*      */       try
/*      */       {
/* 1000 */         ps = AMConnectionPool.getConnection().prepareStatement("select am_mo.RESOURCEID from AM_PARENTCHILDMAPPER am_pcm,AM_ManagedObject am_mo,AM_HOLISTICAPPLICATION am_ha where am_mo.resourceid=am_pcm.childid and am_mo.RESOURCEID=am_ha.HAID and am_mo.TYPE='HAI' and  am_pcm.PARENTID IN (" + haid + ") and am_mo.RESOURCENAME=? and am_ha.GROUPTYPE=" + Integer.parseInt(mgtype));
/* 1001 */         ps.setString(1, applicationName);
/* 1002 */         rs = ps.executeQuery();
/* 1003 */         if (rs.next())
/*      */         {
/* 1005 */           name_exist = true;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/* 1016 */           if (ps != null)
/*      */           {
/* 1018 */             ps.close();
/*      */           }
/* 1020 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1024 */           ex.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/* 1028 */         if (!name_exist) {
/*      */           break label395;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1010 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/* 1016 */           if (ps != null)
/*      */           {
/* 1018 */             ps.close();
/*      */           }
/* 1020 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1024 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1030 */       return new ActionForward("/showapplication.do?method=showApplication&messagetoshow=subgrpAlreadyExists&name=" + applicationName + "&haid=" + haid, true);
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*      */       String mgtype;
/*      */       
/*      */ 
/*      */ 
/*      */       label395:
/*      */       
/*      */ 
/*      */ 
/*      */       String subhaid;
/*      */       
/*      */ 
/*      */ 
/* 1046 */       exp.printStackTrace();
/*      */     }
/* 1033 */     subhaid = createGroup(haid, applicationName, description, mgtype, locationID, owner);
/*      */     try
/*      */     {
/* 1036 */       String auditLogMsg = FormatUtil.getString("am.audit.addSubgroup.msg", new String[] { subhaid, applicationName, DBUtil.getDisplaynameforResourceID(haid) });
/* 1037 */       cliAuditUtil.addToAuditLog(request, Integer.parseInt(haid), 2, auditLogMsg);
/*      */     }
/*      */     catch (Exception e) {
/* 1040 */       e.printStackTrace();
/*      */     }
/* 1042 */     return new ActionForward("/showapplication.do?method=showApplication&messagetoshow=subgroupsuccess&haid=" + subhaid, true);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1048 */     return new ActionForward("/showapplication.do?method=showApplication&haid=" + haid, true);
/*      */   }
/*      */   
/*      */   public String createGroup(String haid, String applicationName, String description, String mgtype, String locationID, String[] owner) throws Exception {
/* 1052 */     return this.discUtil.createGroup(haid, applicationName, null, description, mgtype, locationID, owner);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward refreshNow(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1062 */     String haid = request.getParameter("haid");
/* 1063 */     ActionMessages messages = new ActionMessages();
/*      */     try
/*      */     {
/* 1066 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 17, System.currentTimeMillis(), true, true, 1);
/* 1067 */       new AMRCAnalyser().applyRCA(Integer.parseInt(haid), 18, System.currentTimeMillis(), true, false, 2);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1071 */       ex.printStackTrace();
/*      */     }
/* 1073 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.monitorgroupdetails.refresh.success"));
/* 1074 */     saveMessages(request, messages);
/* 1075 */     return new ActionForward("/showapplication.do?method=showApplication&messagetoshow=refreshstatus&haid=" + haid, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward refreshAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1084 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1085 */     ActionMessages messages = new ActionMessages();
/*      */     try
/*      */     {
/* 1088 */       String qry = "select HAID from AM_HOLISTICAPPLICATION order by TYPE desc,CREATIONDATE desc";
/* 1089 */       ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/* 1090 */       while (rs.next())
/*      */       {
/* 1092 */         int haid = rs.getInt("HAID");
/* 1093 */         new AMRCAnalyser().applyRCA(haid, 17, System.currentTimeMillis(), true, true, 1);
/* 1094 */         new AMRCAnalyser().applyRCA(haid, 18, System.currentTimeMillis(), true, false, 2);
/*      */       }
/* 1096 */       rs.close();
/*      */       
/* 1098 */       FaultUtil.applyRCAForMonitorsSnapshot();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1102 */       ex.printStackTrace();
/*      */     }
/* 1104 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Health and Availability status updated Successfully for all Monitor Groups."));
/* 1105 */     saveMessages(request, messages);
/* 1106 */     return new ActionForward("/applications.do");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/* 1114 */     lengthOfTrimmedString = AppManagerUtil.getWebclientDisplayLength(lengthOfTrimmedString);
/* 1115 */     if ((stringToTrim != null) && (lengthOfTrimmedString > 0))
/*      */     {
/* 1117 */       if (stringToTrim.length() > lengthOfTrimmedString)
/*      */       {
/* 1119 */         return stringToTrim.substring(0, lengthOfTrimmedString - 3) + "...";
/*      */       }
/*      */     }
/* 1122 */     return stringToTrim;
/*      */   }
/*      */   
/*      */   public ActionForward addThresholdConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1127 */     String resourceid = request.getParameter("resourceid");
/* 1128 */     String attributeid = request.getParameter("attributeid");
/* 1129 */     String returnPath = request.getParameter("returnPath");
/* 1130 */     ArrayList<String> actionInfo = new ArrayList();
/* 1131 */     ActionMessages messages = new ActionMessages();
/* 1132 */     Statement stmt = null;
/* 1133 */     deletePreviousAlarmInfo(resourceid, attributeid);
/* 1134 */     if ((request.getParameter("remConfiguration") != null) && (request.getParameter("remConfiguration").equals("true")))
/*      */     {
/* 1136 */       if (EnterpriseUtil.isAdminServer())
/*      */       {
/*      */         try
/*      */         {
/* 1140 */           Hashtable mgTable = DBUtil.getMonitorsInAdminMG(resourceid, null);
/* 1141 */           EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(mgTable, resourceid, 2);
/* 1142 */           subMgTable = DBUtil.getAllMonitorsUnderSubGroupForMonitorGroup(resourceid, true);
/* 1143 */           for (enu = subMgTable.keys(); enu.hasMoreElements();)
/*      */           {
/* 1145 */             String subGroupID = (String)enu.nextElement();
/* 1146 */             Hashtable<String, JSONArray> subTable = (Hashtable)subMgTable.get(subGroupID);
/* 1147 */             if (AMCacheHandler.getDependentDevice(subGroupID) == null)
/*      */             {
/* 1149 */               EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(subTable, subGroupID, 2);
/*      */             }
/*      */           }
/*      */         } catch (Exception e) {
/*      */           Hashtable<String, Hashtable<String, JSONArray>> subMgTable;
/*      */           Enumeration<String> enu;
/* 1155 */           e.printStackTrace();
/*      */         }
/*      */       }
/* 1158 */       DependentDeviceUtil.getInstance().removeDependentDeviceConfiguration(resourceid);
/* 1159 */       RuleAnalyser.insertDefaultRules(Integer.parseInt(resourceid), Integer.parseInt(attributeid), this.mo.isSubGroup(resourceid));
/* 1160 */       RuleAnalyser.removeAllMonitorGrpFromDependency(resourceid);
/* 1161 */       new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 17, System.currentTimeMillis(), true, true, 1);
/* 1162 */       new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 18, System.currentTimeMillis(), true, false, 2);
/* 1163 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.rule.alarmRemovedSuccess")));
/* 1164 */       saveMessages(request, messages);
/* 1165 */       return new ActionForward(returnPath);
/*      */     }
/* 1167 */     if (RuleAnalyser.getAttributeType(attributeid) == 1)
/*      */     {
/* 1169 */       actionInfo.add("selectedactions_down");
/* 1170 */       actionInfo.add("selectedactions_up");
/*      */     }
/*      */     else
/*      */     {
/* 1174 */       actionInfo.add("selectedactions_critical");
/* 1175 */       actionInfo.add("selectedactions_warning");
/* 1176 */       actionInfo.add("selectedactions_clear");
/*      */     }
/*      */     try
/*      */     {
/* 1180 */       stmt = AMConnectionPool.getConnection().createStatement();
/*      */       
/*      */ 
/* 1183 */       for (int i = 0; i < actionInfo.size(); i++)
/*      */       {
/* 1185 */         int severity = 1;
/* 1186 */         String[] actionId = null;
/* 1187 */         if (request.getParameterValues((String)actionInfo.get(i)) != null)
/*      */         {
/* 1189 */           actionId = request.getParameterValues((String)actionInfo.get(i));
/* 1190 */           if ((((String)actionInfo.get(i)).equalsIgnoreCase("selectedactions_clear")) || (((String)actionInfo.get(i)).equalsIgnoreCase("selectedactions_up")))
/*      */           {
/* 1192 */             severity = 5;
/*      */           }
/* 1194 */           else if (((String)actionInfo.get(i)).equalsIgnoreCase("selectedactions_warning"))
/*      */           {
/* 1196 */             severity = 4;
/*      */           }
/*      */         }
/* 1199 */         if (actionId != null)
/*      */         {
/* 1201 */           for (int j = 0; j < actionId.length; j++)
/*      */           {
/* 1203 */             AMLog.debug("Insert Query----->INSERT INTO AM_ATTRIBUTEACTIONMAPPER (ID, ATTRIBUTE, SEVERITY, ACTIONID) values (" + resourceid + "," + attributeid + "," + severity + "," + actionId[j] + ")");
/* 1204 */             stmt.addBatch("INSERT INTO AM_ATTRIBUTEACTIONMAPPER (ID, ATTRIBUTE, SEVERITY, ACTIONID) values (" + resourceid + "," + attributeid + "," + severity + "," + actionId[j] + ")");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1210 */       int ruleCountCritical = 0;
/* 1211 */       int ruleCountWarning = 0;
/* 1212 */       if (request.getParameter("totalRowCount1") != null)
/*      */       {
/* 1214 */         ruleCountCritical = Integer.parseInt(request.getParameter("totalRowCount1"));
/*      */       }
/* 1216 */       if (request.getParameter("totalRowCount4") != null)
/*      */       {
/* 1218 */         ruleCountWarning = Integer.parseInt(request.getParameter("totalRowCount4"));
/*      */       }
/* 1220 */       AMLog.debug("totalRowCountCritical----->" + ruleCountCritical);
/* 1221 */       AMLog.debug("totalRowCountWarning------>" + ruleCountWarning);
/*      */       try
/*      */       {
/* 1224 */         HashMap ruleInfoCritical = new HashMap();
/* 1225 */         HashMap ruleInfoWarning = new HashMap();
/* 1226 */         int attributeAffected = RuleAnalyser.getAttributeType(attributeid);
/* 1227 */         RuleAnalyser.deleteExistingRules(request.getParameter("resourceid"), attributeAffected);
/* 1228 */         for (int i = 0; i <= ruleCountCritical; i++)
/*      */         {
/*      */ 
/* 1231 */           if (request.getParameter("selectMonType" + i + "_1") != null)
/*      */           {
/* 1233 */             String ruleType = request.getParameter("selectMonType" + i + "_1");
/* 1234 */             ruleInfoCritical.put("RULE_TYPE", ruleType);
/* 1235 */             if ((ruleType != null) && (ruleType.equals("1")))
/*      */             {
/* 1237 */               String[] selectedMonitors = request.getParameterValues("selectedMonitors" + i + "_1");
/*      */               
/* 1239 */               ruleInfoCritical.put("SELECTED_MONITORS", FormatUtil.convertArrayToVector(selectedMonitors));
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1244 */             if ((ruleType != null) && (ruleType.equals("1")))
/*      */             {
/* 1246 */               ruleInfoCritical.put("NO_OF_MONITORS", request.getParameter("selectedMonitorCount" + i + "_1"));
/*      */             }
/* 1248 */             else if ((ruleType != null) && ((ruleType.equals("0")) || (ruleType.equals("2"))))
/*      */             {
/* 1250 */               ruleInfoCritical.put("NO_OF_MONITORS", request.getParameter("count" + i + "_1"));
/*      */             }
/* 1252 */             int selectedMORuleType = Integer.parseInt(request.getParameter("selectedMonitorType" + i + "_1"));
/* 1253 */             AMLog.debug("Selected Monitor type critical----->" + selectedMORuleType);
/* 1254 */             ruleInfoCritical.put("CONDITION_ATTRIBUTE", request.getParameter("conditionAttribute" + i + "_1"));
/* 1255 */             ruleInfoCritical.put("CONDITION_SEVERITY", request.getParameter("conditionSeverity" + i + "_1"));
/* 1256 */             ruleInfoCritical.put("MONITOR_CONDITION", "false");
/* 1257 */             if (selectedMORuleType == 1)
/*      */             {
/* 1259 */               ruleInfoCritical.put("MONITOR_CONDITION", "true");
/*      */             }
/* 1261 */             ruleInfoCritical.put("HAID", request.getParameter("resourceid"));
/* 1262 */             ruleInfoCritical.put("ATTRIBUTE_AFFECTED", attributeAffected + "");
/* 1263 */             ruleInfoCritical.put("TARGET_SEVERITY", "1");
/*      */             
/* 1265 */             RuleAnalyser.insertIntoRulesTables(ruleInfoCritical);
/*      */           }
/*      */         }
/*      */         
/* 1269 */         for (int j = 0; j < ruleCountWarning; j++)
/*      */         {
/* 1271 */           if (request.getParameter("selectMonType" + j + "_4") != null)
/*      */           {
/* 1273 */             String ruleType = request.getParameter("selectMonType" + j + "_4");
/* 1274 */             if ((ruleType != null) && (ruleType.equals("1")))
/*      */             {
/* 1276 */               String[] selectedMonitors = request.getParameterValues("selectedMonitors" + j + "_4");
/* 1277 */               ruleInfoWarning.put("SELECTED_MONITORS", FormatUtil.convertArrayToVector(selectedMonitors));
/*      */             }
/* 1279 */             if ((ruleType != null) && (ruleType.equals("1")))
/*      */             {
/* 1281 */               ruleInfoWarning.put("NO_OF_MONITORS", request.getParameter("selectedMonitorCount" + j + "_4"));
/*      */             }
/* 1283 */             else if ((ruleType != null) && ((ruleType.equals("0")) || (ruleType.equals("2"))))
/*      */             {
/* 1285 */               ruleInfoWarning.put("NO_OF_MONITORS", request.getParameter("count" + j + "_4"));
/*      */             }
/*      */             
/* 1288 */             int selectedMORuleType = Integer.parseInt(request.getParameter("selectedMonitorType" + j + "_4"));
/*      */             
/* 1290 */             ruleInfoWarning.put("RULE_TYPE", ruleType);
/* 1291 */             ruleInfoWarning.put("CONDITION_ATTRIBUTE", request.getParameter("conditionAttribute" + j + "_4"));
/* 1292 */             ruleInfoWarning.put("CONDITION_SEVERITY", request.getParameter("conditionSeverity" + j + "_4"));
/* 1293 */             ruleInfoWarning.put("MONITOR_CONDITION", "false");
/* 1294 */             if (selectedMORuleType == 1)
/*      */             {
/* 1296 */               ruleInfoWarning.put("MONITOR_CONDITION", "true");
/*      */             }
/* 1298 */             ruleInfoWarning.put("HAID", request.getParameter("resourceid"));
/* 1299 */             ruleInfoWarning.put("ATTRIBUTE_AFFECTED", attributeAffected + "");
/* 1300 */             ruleInfoWarning.put("TARGET_SEVERITY", "4");
/* 1301 */             RuleAnalyser.insertIntoRulesTables(ruleInfoWarning);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Throwable e)
/*      */       {
/* 1307 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1313 */       String dependentDeviceId = request.getParameter("depDeviceId");
/* 1314 */       int suppressAlerts = 0;
/* 1315 */       if ((request.getParameter("suppressAlert") != null) && (request.getParameter("suppressAlert").equals("1")))
/*      */       {
/* 1317 */         suppressAlerts = 1;
/*      */       }
/* 1319 */       if ((dependentDeviceId != null) && (!dependentDeviceId.equals("")))
/*      */       {
/* 1321 */         dependentDeviceId = Translate.decode(dependentDeviceId);
/* 1322 */         HashMap<String, HashMap<String, String>> dependentMonitorMap = AMCacheHandler.getDependentDevice(resourceid);
/* 1323 */         JSONObject jsArr = new JSONObject(dependentDeviceId);
/* 1324 */         Hashtable addQueryMap = new Hashtable();
/* 1325 */         Hashtable updateQueryMap = new Hashtable();
/* 1326 */         Hashtable deleteQueryMap = new Hashtable();
/* 1327 */         ArrayList deleteMonitorList = new ArrayList();
/* 1328 */         Iterator jsItr = jsArr.keys();
/* 1329 */         int masIndex = EnterpriseUtil.getManagedServerIndex();
/* 1330 */         while (jsItr.hasNext())
/*      */         {
/* 1332 */           String key = (String)jsItr.next();
/* 1333 */           JSONObject jsObj = (JSONObject)jsArr.get(key);
/* 1334 */           String parentID = jsObj.getString("resourceid");
/* 1335 */           int parentIndex = EnterpriseUtil.getManagedServerIndex(Integer.parseInt(parentID));
/* 1336 */           boolean isDependentDeviceAddedForManagedServer = masIndex != parentIndex;
/* 1337 */           if ((dependentMonitorMap == null) || (!dependentMonitorMap.containsKey(parentID)))
/*      */           {
/* 1339 */             Properties prop = new Properties();
/* 1340 */             String dependentDeviceType = jsObj.getString("resourceType");
/* 1341 */             prop.setProperty("PARENTID", parentID);
/* 1342 */             prop.setProperty("CHILDID", resourceid + "");
/* 1343 */             prop.setProperty("SUPPRESS_ALERTS", suppressAlerts + "");
/* 1344 */             prop.setProperty("PARENT_AVAILABILITYID", AMAttributesCache.getAvailabilityId(dependentDeviceType));
/* 1345 */             if (isDependentDeviceAddedForManagedServer)
/*      */             {
/* 1347 */               prop.setProperty("PARENT_TYPE", dependentDeviceType);
/* 1348 */               prop.setProperty("DISPLAYNAME", jsObj.getString("displayname"));
/* 1349 */               prop.setProperty("MANAGEDSERVER", jsObj.getString("managedServer"));
/*      */             }
/* 1351 */             addQueryMap.put(parentID, prop);
/*      */           }
/* 1353 */           else if ((dependentMonitorMap != null) && (dependentMonitorMap.containsKey(parentID)))
/*      */           {
/* 1355 */             HashMap<String, String> monitorDetails = (HashMap)dependentMonitorMap.get(parentID);
/* 1356 */             int monitorSuppressAlert = Integer.parseInt((String)monitorDetails.get("SUPPRESS_ALERTS"));
/* 1357 */             if (monitorSuppressAlert != suppressAlerts)
/*      */             {
/* 1359 */               Properties prop = new Properties();
/* 1360 */               prop.setProperty("SUPPRESS_ALERTS", suppressAlerts + "");
/* 1361 */               updateQueryMap.put(parentID, prop);
/*      */             }
/*      */           }
/*      */         }
/* 1365 */         if (dependentMonitorMap != null)
/*      */         {
/* 1367 */           Iterator itr = dependentMonitorMap.keySet().iterator();
/*      */           
/* 1369 */           while (itr.hasNext())
/*      */           {
/* 1371 */             boolean isParentIDContains = false;
/*      */             
/* 1373 */             String key = (String)itr.next();
/* 1374 */             Iterator jsItr1 = jsArr.keys();
/* 1375 */             while (jsItr1.hasNext())
/*      */             {
/* 1377 */               String key1 = (String)jsItr1.next();
/* 1378 */               JSONObject jsObj = (JSONObject)jsArr.get(key1);
/* 1379 */               String parentID = jsObj.getString("resourceid");
/* 1380 */               if (parentID.equals(key))
/*      */               {
/* 1382 */                 isParentIDContains = true;
/*      */               }
/*      */             }
/* 1385 */             if (!isParentIDContains)
/*      */             {
/* 1387 */               deleteMonitorList.add(key);
/*      */             }
/*      */           }
/*      */         }
/* 1391 */         DependentDeviceUtil.getInstance().performDependentMonitorDBOperations(resourceid, addQueryMap, updateQueryMap, deleteMonitorList);
/* 1392 */         if (EnterpriseUtil.isAdminServer())
/*      */         {
/* 1394 */           Hashtable<String, JSONArray> table = DBUtil.getMonitorsInAdminMG(resourceid, null);
/* 1395 */           EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(table, resourceid, 1);
/* 1396 */           subMgTable = DBUtil.getAllMonitorsUnderSubGroupForMonitorGroup(resourceid, true);
/* 1397 */           for (enu = subMgTable.keys(); enu.hasMoreElements();)
/*      */           {
/* 1399 */             String subGroupID = (String)enu.nextElement();
/* 1400 */             Hashtable<String, JSONArray> subTable = (Hashtable)subMgTable.get(subGroupID);
/* 1401 */             if (AMCacheHandler.getDependentDevice(subGroupID) != null)
/*      */             {
/* 1403 */               EnterpriseUtil.syncAdminMGAssociatedMonitorsInAllMAS(subTable, subGroupID, 1);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (NumberFormatException e1) {
/*      */       Hashtable<String, Hashtable<String, JSONArray>> subMgTable;
/*      */       Enumeration<String> enu;
/* 1411 */       e1.printStackTrace();
/*      */     } catch (Exception e1) {
/* 1413 */       e1.printStackTrace();
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1418 */       stmt.executeBatch();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1426 */       if (stmt != null)
/*      */       {
/*      */         try
/*      */         {
/* 1430 */           stmt.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1434 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 1438 */       selectedDepMGroups = request.getParameter("selectedDepMonGroups");
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1422 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1426 */       if (stmt != null)
/*      */       {
/*      */         try
/*      */         {
/* 1430 */           stmt.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1434 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     String selectedDepMGroups;
/* 1440 */     new RuleAnalyser();RuleAnalyser.addDependentMG(resourceid, selectedDepMGroups, false);
/* 1441 */     new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 17, System.currentTimeMillis(), true, true, 1);
/* 1442 */     new AMRCAnalyser().applyRCA(Integer.parseInt(resourceid), 18, System.currentTimeMillis(), true, false, 2);
/* 1443 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.rule.alarmsuccess")));
/* 1444 */     saveMessages(request, messages);
/* 1445 */     return new ActionForward(returnPath);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean deletePreviousAlarmInfo(String resourceid, String attributeid)
/*      */   {
/* 1452 */     Statement stmt = null;
/*      */     try
/*      */     {
/* 1455 */       stmt = AMConnectionPool.getConnection().createStatement();
/* 1456 */       stmt.addBatch("DELETE FROM AM_ATTRIBUTEACTIONMAPPER WHERE ATTRIBUTE=" + attributeid + " AND ID=" + resourceid);
/*      */       
/*      */ 
/* 1459 */       RuleAnalyser.deleteExistingRules(resourceid, RuleAnalyser.getAttributeType(attributeid));
/* 1460 */       stmt.executeBatch();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1481 */       return true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1464 */       e.printStackTrace();
/* 1465 */       return false;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1471 */         if (stmt != null)
/*      */         {
/* 1473 */           stmt.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1478 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void addMonitorGroup(int haid, String locationID) throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1487 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1488 */       AMConnectionPool.executeUpdateStmt("insert into AM_MinMaxAvgData (ARCHIVEDTIME, RESID, DURATION, ATTRIBUTEID, MINVALUE, MAXVALUE, TOTAL, TOTALCOUNT) values('" + System.currentTimeMillis() + "', '" + haid + "', '-1','17','0','0','0','1')");
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1492 */       exp.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/* 1496 */       int loc = Integer.parseInt(locationID);
/* 1497 */       String query = "insert into AM_GMapCountryResourceRel values(" + haid + "," + loc + ")";
/* 1498 */       AMLog.debug(query);
/* 1499 */       AMConnectionPool.executeUpdateStmt(query);
/* 1500 */       AMLog.debug("***************Associated the location. Location ID ======" + loc);
/*      */     }
/*      */     catch (NumberFormatException e) {
/* 1503 */       AMLog.debug("***************No location or Invalid location specified.=====");
/*      */     }
/*      */   }
/*      */   
/*      */   public void addOwner(String[] owners, int haid) {
/* 1508 */     int relationid = MyFields.getIncrementedID("RELATIONID", "AM_MYFIELDS_ENTITYDATA") - 1;
/* 1509 */     List<String> ownersList = new ArrayList();
/* 1510 */     for (int i = 0; i < owners.length; i++)
/*      */     {
/* 1512 */       String localOwner = owners[i];
/* 1513 */       relationid += 1;
/* 1514 */       String query = "insert into AM_HOLISTICAPPLICATION_OWNERS values(" + haid + "," + localOwner + ")";
/* 1515 */       String query1 = "insert into AM_MYFIELDS_ENTITYDATA values(" + relationid + "," + haid + ",'AM_UserPasswordTable'," + localOwner + ")";
/* 1516 */       this.mo.executeUpdateStmt(query);
/* 1517 */       this.mo.executeUpdateStmt(query1);
/* 1518 */       RestrictedUsersViewUtil.insertIntoAMUserResourcesTable(Long.valueOf(localOwner), Long.valueOf(haid));
/*      */       
/* 1520 */       if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate) && (RestrictedUsersViewUtil.isRestrictedRole(localOwner)))
/*      */       {
/* 1522 */         ownersList.add(localOwner);
/*      */       }
/* 1524 */       if ((AMAutomaticPortChanger.isSsoEnabled()) && (EnterpriseUtil.isManagedServer) && (RestrictedUsersViewUtil.isRestrictedRole(localOwner))) {
/* 1525 */         EnterpriseUtil.addUpdateQueryToFile("insert into AM_USERRESOURCESTABLE  VALUES (" + Long.valueOf(localOwner) + "," + Long.valueOf(haid) + ")");
/* 1526 */         EnterpriseUtil.addUpdateQueryToFile("insert into AM_HOLISTICAPPLICATION_OWNERS values(" + haid + "," + localOwner + ")");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1531 */     if (((!AMAutomaticPortChanger.isSsoEnabled()) || (!EnterpriseUtil.isManagedServer)) && (Constants.doConcurrentUserResourceUpdate))
/*      */     {
/*      */       try
/*      */       {
/* 1535 */         AMLog.debug("[HAIDManagerAction::(addOwner)]ruser(s) : " + ownersList);
/* 1536 */         RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.addAll(ownersList);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1540 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void addWebGroup(HttpServletRequest request, int haid)
/*      */   {
/* 1548 */     ArrayList groupsToCreate = new ArrayList();
/* 1549 */     for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */     {
/* 1551 */       String parameterName = (String)e.nextElement();
/* 1552 */       int index = 0;
/* 1553 */       if ((index = parameterName.indexOf("mgtypes_")) != -1)
/*      */       {
/* 1555 */         String grouptype = parameterName.substring(8, parameterName.length());
/* 1556 */         String noofchilds = request.getParameter(parameterName);
/* 1557 */         if (!noofchilds.equals("0"))
/*      */         {
/* 1559 */           String containsChild = GroupComponent.getDefaultClusterType(grouptype);
/* 1560 */           GroupComponent groupcomponent = new GroupComponent();
/* 1561 */           groupcomponent.setSubgroupType(Integer.parseInt(grouptype));
/* 1562 */           groupcomponent.setNoOfChilds(Integer.parseInt(noofchilds));
/* 1563 */           groupcomponent.setContains(Integer.parseInt(containsChild));
/* 1564 */           groupsToCreate.add(groupcomponent);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1569 */     HashMap hashmap = new HashMap();
/* 1570 */     hashmap.put("haid", String.valueOf(haid));
/* 1571 */     hashmap.put("componenetsToCreate", groupsToCreate);
/* 1572 */     createWebApplicationComponents(hashmap);
/*      */   }
/*      */   
/*      */   private void createWebApplicationComponents(HashMap hashmap)
/*      */   {
/*      */     try {
/* 1578 */       ArrayList arraylist = (ArrayList)hashmap.get("componenetsToCreate");
/* 1579 */       String s = (String)hashmap.get("haid");
/* 1580 */       for (int i = 0; i < arraylist.size(); i++)
/*      */       {
/* 1582 */         GroupComponent groupcomponent = (GroupComponent)arraylist.get(i);
/* 1583 */         int j = groupcomponent.getContains();
/* 1584 */         int k = groupcomponent.getSubgroupType();
/*      */         
/* 1586 */         int i1 = -1;
/* 1587 */         String s1 = GroupComponent.getSubGroupLabel(String.valueOf(k));
/* 1588 */         HashMap hashmap1 = new HashMap();
/* 1589 */         hashmap1.put("name", s1);
/* 1590 */         hashmap1.put("description", FormatUtil.getString("am.webclient.newmonitorgroup.description.textbox"));
/* 1591 */         hashmap1.put("locationid", "-1");
/* 1592 */         hashmap1.put("haid", s);
/* 1593 */         hashmap1.put("selectedowners_list", new String[0]);
/* 1594 */         hashmap1.put("subGroupType", k + "");
/* 1595 */         i1 = createSubGroup(hashmap1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception exception)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1614 */       exception.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public int createSubGroup(HashMap hashmap)
/*      */   {
/* 1620 */     int subhaid = -1;
/* 1621 */     subhaid = this.discUtil.createSubGroup(hashmap);
/* 1622 */     return subhaid;
/*      */   }
/*      */   
/*      */ 
/*      */   public ActionForward createapp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1628 */     String groupType = request.getParameter("grouptype");
/* 1629 */     if (request.isUserInRole("DEMO"))
/*      */     {
/* 1631 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1636 */       if (!DBUtil.hasGlobalConfigValue("NewMonitorgroupAccessed"))
/*      */       {
/* 1638 */         AppManagerUtil.insertTimeforMetrack("NewMonitorgroupAccessed");
/* 1639 */         DBUtil.insertIntoGlobalConfig("NewMonitorgroupAccessed", "true");
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/* 1643 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 1646 */     if ("3".equals(groupType))
/*      */     {
/* 1648 */       String isEdit = request.getParameter("isEdit");
/* 1649 */       if ((isEdit == null) || (isEdit.trim().equals("null")) || ("false".equals(isEdit)))
/*      */       {
/* 1651 */         return new ActionForward("/jsp/CreateVMWareVI.jsp");
/*      */       }
/*      */       
/* 1654 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1655 */       ResultSet rs = null;
/* 1656 */       String haid = null;
/*      */       try {
/* 1658 */         haid = request.getParameter("haid");
/* 1659 */         String name = request.getParameter("haName");
/* 1660 */         String credQuery = "select HOSTNAME,PORT,USERNAME,POLLINGINTERVAL,HOSTDCVIAVC,DISCOVERVM from AM_VCENTER_ARGS where RESID=" + haid;
/* 1661 */         rs = AMConnectionPool.executeQueryStmt(credQuery);
/* 1662 */         if (rs.next())
/*      */         {
/* 1664 */           if (EnterpriseUtil.isIt360MSPEdition)
/*      */           {
/* 1666 */             String custId = this.mo.getParentMGResourceid(haid);
/* 1667 */             ((DynaActionForm)form).set("organization", custId);
/*      */           }
/* 1669 */           ((DynaActionForm)form).set("displayname", name);
/* 1670 */           ((DynaActionForm)form).set("vcHost", rs.getString("HOSTNAME"));
/* 1671 */           ((DynaActionForm)form).set("vcPort", Integer.valueOf(rs.getInt("PORT")));
/* 1672 */           ((DynaActionForm)form).set("username", rs.getString("USERNAME"));
/* 1673 */           ((DynaActionForm)form).set("pollInterval", Integer.valueOf(rs.getInt("POLLINGINTERVAL") / 60));
/* 1674 */           if (rs.getInt("HOSTDCVIAVC") == 1)
/*      */           {
/* 1676 */             ((DynaActionForm)form).set("isHostDCViaVC", Boolean.valueOf(true));
/*      */           }
/*      */           else
/*      */           {
/* 1680 */             ((DynaActionForm)form).set("isHostDCViaVC", Boolean.valueOf(false));
/*      */           }
/* 1682 */           ((DynaActionForm)form).set("discoverVM", Integer.valueOf(rs.getInt("DISCOVERVM")));
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 1687 */         ee.printStackTrace();
/*      */       }
/*      */       finally {
/* 1690 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/* 1692 */       return new ActionForward("/jsp/CreateVMWareVI.jsp?isEdit=true&haid=" + haid);
/*      */     }
/*      */     
/* 1695 */     if ("4".equals(groupType))
/*      */     {
/* 1697 */       String isEdit = request.getParameter("isEdit");
/* 1698 */       if ((isEdit == null) || (isEdit.trim().equals("null")) || ("false".equals(isEdit)))
/*      */       {
/* 1700 */         return new ActionForward("/jsp/CreateVMwareHorizon.jsp");
/*      */       }
/*      */       
/* 1703 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1704 */       ResultSet rs = null;
/* 1705 */       String haid = null;
/*      */       try {
/* 1707 */         haid = request.getParameter("haid");
/* 1708 */         String name = request.getParameter("haName");
/* 1709 */         String credQuery = "select HOSTNAME,USERNAME,POLLINGINTERVAL from AM_VMHORIZON_ARGS where RESID=" + haid;
/* 1710 */         rs = AMConnectionPool.executeQueryStmt(credQuery);
/* 1711 */         if (rs.next())
/*      */         {
/* 1713 */           if (EnterpriseUtil.isIt360MSPEdition)
/*      */           {
/* 1715 */             String custId = this.mo.getParentMGResourceid(haid);
/* 1716 */             ((DynaActionForm)form).set("organization", custId);
/*      */           }
/* 1718 */           ((DynaActionForm)form).set("displayname", name);
/* 1719 */           ((DynaActionForm)form).set("vcHost", rs.getString("HOSTNAME"));
/*      */           
/* 1721 */           ((DynaActionForm)form).set("username", rs.getString("USERNAME"));
/* 1722 */           ((DynaActionForm)form).set("pollInterval", Integer.valueOf(rs.getInt("POLLINGINTERVAL") / 60));
/*      */         }
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 1727 */         ee.printStackTrace();
/*      */       }
/*      */       finally {
/* 1730 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       
/* 1733 */       return new ActionForward("/jsp/CreateVMwareHorizon.jsp?isEdit=true&haid=" + haid);
/*      */     }
/*      */     
/* 1736 */     request.setAttribute("mointorGroupTypes", GroupComponent.getMointorGroupTypes());
/* 1737 */     return new ActionForward("/jsp/CreateApplication.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward configureVCenterDiscovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1742 */     DiscoveryUtil discUtil = new DiscoveryUtil();
/* 1743 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*      */ 
/* 1746 */       ActionErrors errors = new ActionErrors();
/* 1747 */       ActionMessages messages = new ActionMessages();
/* 1748 */       String haid = request.getParameter("haid");
/* 1749 */       String resourcetype = request.getParameter("type");
/* 1750 */       Properties argsprops = new Properties();
/* 1751 */       argsprops.setProperty("monitorType", resourcetype);
/* 1752 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/* 1754 */         String param = (String)e.nextElement();
/* 1755 */         if (!argsprops.containsKey(param))
/*      */         {
/* 1757 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1762 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/* 1763 */         ArrayList<String> al1 = new ArrayList();
/* 1764 */         String displayname = request.getParameter("displayname");
/* 1765 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/* 1766 */           displayname = request.getParameter("displayName");
/*      */         }
/* 1768 */         String status = "Success";
/* 1769 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/* 1770 */         if ((resourcetype != null) && (resourcetype.equals("vCenter"))) {
/* 1771 */           message = "/showapplication.do?method=showApplication&haid=" + (String)responseMap.get("resourceId");
/*      */         }
/* 1773 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/* 1774 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/* 1775 */           status = "Failed";
/* 1776 */           message = (String)responseMap.get("message");
/*      */         }
/* 1778 */         al1.add(displayname);
/* 1779 */         al1.add(status);
/* 1780 */         al1.add(message);
/* 1781 */         al1.add(masDisplayName);
/* 1782 */         request.setAttribute("discoverystatus", al1);
/* 1783 */         request.setAttribute("type", resourcetype);
/* 1784 */         request.setAttribute("basetype", "Script Monitor");
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1789 */         e.printStackTrace();
/*      */       }
/* 1791 */       String forwardPage = "/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid;
/* 1792 */       if ((resourcetype != null) && (resourcetype.equals("vCenter"))) {
/* 1793 */         forwardPage = "/admin/createapplication.do?method=createapp&grouptype=3";
/*      */       }
/* 1795 */       return new ActionForward(forwardPage);
/*      */     }
/* 1797 */     String displayname = null;
/* 1798 */     String vHost = null;
/* 1799 */     int vPort = 443;
/* 1800 */     String userName = null;
/* 1801 */     String passWord = null;
/* 1802 */     int pollinterval = 5;
/* 1803 */     boolean isHostDCviaVC = false;
/* 1804 */     int discoverAndMonitorVM = 0;
/* 1805 */     String fromRESTApi = request.getParameter("fromRESTAPI");
/* 1806 */     fromRESTApi = fromRESTApi == null ? "" : fromRESTApi;
/* 1807 */     if (fromRESTApi.equalsIgnoreCase("true")) {
/* 1808 */       displayname = request.getParameter("displayname");
/* 1809 */       vHost = request.getParameter("host");
/* 1810 */       vPort = Integer.parseInt(request.getParameter("port"));
/* 1811 */       userName = request.getParameter("username");
/* 1812 */       passWord = request.getParameter("password");
/* 1813 */       pollinterval = Integer.parseInt(request.getParameter("pollInterval"));
/* 1814 */       if (request.getParameter("isHostDCViaVC") != null) {
/* 1815 */         isHostDCviaVC = Boolean.valueOf(request.getParameter("isHostDCViaVC")).booleanValue();
/*      */       }
/* 1817 */       if (request.getParameter("discoverVM") != null) {
/* 1818 */         discoverAndMonitorVM = Integer.parseInt(request.getParameter("discoverVM"));
/*      */       }
/*      */     } else {
/* 1821 */       DynaActionForm amform = (DynaActionForm)form;
/* 1822 */       vHost = (String)amform.get("vcHost");
/* 1823 */       vPort = ((Integer)amform.get("vcPort")).intValue();
/* 1824 */       userName = (String)amform.get("username");
/* 1825 */       passWord = (String)amform.get("password");
/* 1826 */       displayname = (String)amform.get("displayname");
/* 1827 */       pollinterval = ((Integer)amform.get("pollInterval")).intValue();
/* 1828 */       if (amform.get("isHostDCViaVC") != null)
/*      */       {
/* 1830 */         isHostDCviaVC = ((Boolean)amform.get("isHostDCViaVC")).booleanValue();
/*      */       }
/* 1832 */       if (amform.get("discoverVM") != null)
/*      */       {
/* 1834 */         discoverAndMonitorVM = ((Integer)amform.get("discoverVM")).intValue();
/*      */       }
/*      */     }
/* 1837 */     ArrayList<String> addStatus = new ArrayList();
/* 1838 */     int poll_interval = pollinterval * 60;
/* 1839 */     int vchaid = -1;
/* 1840 */     String custId = "-1";
/* 1841 */     String siteId = "-1";
/* 1842 */     if (EnterpriseUtil.isIt360MSPEdition)
/*      */     {
/* 1844 */       custId = (String)((DynaActionForm)form).get("organization");
/* 1845 */       siteId = (String)((DynaActionForm)form).get("site");
/* 1846 */       AMLog.debug("[HAIDMGACT] custId: " + custId + ", siteId: " + siteId);
/*      */     }
/* 1848 */     String description = FormatUtil.getString("am.webclient.mg.vi.defaultdescription.text");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1853 */     String isEdit = request.getParameter("isEdit");
/* 1854 */     String existingHAID = null;
/* 1855 */     if ("true".equals(isEdit))
/*      */     {
/* 1857 */       String haRESID = request.getParameter("haRESID");
/* 1858 */       ResultSet nameSet = null;
/* 1859 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1860 */       nameSet = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haRESID);
/* 1861 */       if (nameSet.next())
/*      */       {
/* 1863 */         ActionForward frwrd = mapping.findForward("showapplication");
/* 1864 */         if (!displayname.equalsIgnoreCase(nameSet.getString("DISPLAYNAME")))
/*      */         {
/*      */ 
/*      */ 
/* 1868 */           ArrayList existingLists = this.mo.getRows("select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + displayname + "' and AM_ManagedObject.TYPE='HAI' ");
/* 1869 */           if ((existingLists != null) && (existingLists.size() > 0))
/*      */           {
/* 1871 */             ArrayList list = (ArrayList)existingLists.get(0);
/* 1872 */             existingHAID = (String)list.get(0);
/*      */           }
/* 1874 */           if (existingHAID != null)
/*      */           {
/* 1876 */             AMLog.debug("HAIDManagerAction : configureVCenterDiscovery : A HAI exist in the same name and its HAID=" + existingHAID);
/* 1877 */             ActionMessages messages = new ActionMessages();
/* 1878 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.vc.alreadyexists"));
/* 1879 */             saveMessages(request, messages);
/* 1880 */             return new ActionForward(frwrd.getPath() + "&haid=" + haRESID, true);
/*      */           }
/*      */         }
/*      */         
/* 1884 */         if (haRESID != null)
/*      */         {
/* 1886 */           vchaid = Integer.parseInt(haRESID);
/*      */         }
/*      */         
/* 1889 */         if (vchaid != -1)
/*      */         {
/* 1891 */           String updateMOQuery = "update AM_ManagedObject set RESOURCENAME='" + displayname + "',DISPLAYNAME='" + displayname + "' where RESOURCEID=" + haRESID;
/* 1892 */           String ipdateVCARGSQuery = "update AM_VCENTER_ARGS set HOSTNAME=?, PORT=?, USERNAME=?, PASSWORD=" + DBQueryUtil.encodetoBytes("?") + ", POLLINGINTERVAL=?,HOSTDCVIAVC=?,DISCOVERVM=? where RESID=?";
/*      */           try
/*      */           {
/* 1895 */             Properties params = new Properties();
/* 1896 */             params.put("HostName", vHost);
/* 1897 */             params.put("Port", String.valueOf(vPort));
/* 1898 */             params.put("UserName", userName);
/* 1899 */             params.put("Password", passWord);
/*      */             
/* 1901 */             Properties authCheckRes = DiscoveryUtil.checkVCAuthentication(params);
/* 1902 */             if ((authCheckRes.get("authentication") != null) && ("passed".equals(authCheckRes.get("authentication"))))
/*      */             {
/*      */ 
/* 1905 */               AMConnectionPool.executeUpdateStmt(updateMOQuery);
/* 1906 */               EnterpriseUtil.addUpdateQueryToFile(updateMOQuery);
/* 1907 */               PreparedStatement ps = null;
/*      */               try
/*      */               {
/* 1910 */                 ps = AMConnectionPool.getConnection().prepareStatement(ipdateVCARGSQuery);
/* 1911 */                 ps.setString(1, vHost);
/* 1912 */                 ps.setInt(2, vPort);
/* 1913 */                 ps.setString(3, userName);
/* 1914 */                 ps.setBytes(4, passWord.getBytes());
/* 1915 */                 ps.setInt(5, poll_interval);
/* 1916 */                 if (isHostDCviaVC)
/*      */                 {
/* 1918 */                   ps.setInt(6, 1);
/*      */                 }
/*      */                 else
/*      */                 {
/* 1922 */                   ps.setInt(6, 0);
/*      */                 }
/* 1924 */                 ps.setInt(7, discoverAndMonitorVM);
/* 1925 */                 ps.setInt(8, vchaid);
/* 1926 */                 ps.executeUpdate();
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 try
/*      */                 {
/* 1936 */                   ps.close();
/*      */                 }
/*      */                 catch (Exception exc) {}
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 1943 */                 if (!isHostDCviaVC) {
/*      */                   break label1614;
/*      */                 }
/*      */               }
/*      */               catch (Exception exc)
/*      */               {
/* 1930 */                 exc.printStackTrace();
/*      */               }
/*      */               finally
/*      */               {
/*      */                 try
/*      */                 {
/* 1936 */                   ps.close();
/*      */                 }
/*      */                 catch (Exception exc) {}
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1945 */               Vector vcChildVector = new Vector();
/* 1946 */               ParentChildRelationalUtil.getAllChildMapper(vcChildVector, String.valueOf(vchaid), true);
/* 1947 */               updateArgumentsforResource(vHost, String.valueOf(vPort), userName, passWord, vcChildVector, NewMonitorUtil.getBaseId("VMWare ESX/ESXi"), discoverAndMonitorVM);
/*      */               label1614:
/* 1949 */               ActionMessages messages = new ActionMessages();
/* 1950 */               messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationupdation.success"));
/* 1951 */               saveMessages(request, messages);
/* 1952 */               return new ActionForward(frwrd.getPath() + "&haid=" + haRESID + "&isEdit=true&status=success", true);
/*      */             }
/*      */           }
/*      */           catch (Exception ee)
/*      */           {
/* 1957 */             ee.printStackTrace();
/*      */           }
/*      */         }
/* 1960 */         ActionMessages messages = new ActionMessages();
/* 1961 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.vc.update.failedmsg"));
/* 1962 */         saveMessages(request, messages);
/* 1963 */         return new ActionForward(frwrd.getPath() + "&haid=" + haRESID + "&isEdit=true&status=failed", true);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1971 */       existingHAID = discUtil.isExistHAI(displayname, vHost);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1994 */       if (existingHAID != null)
/*      */       {
/* 1996 */         AMLog.debug("HAIDManagerAction : configureVCenterDiscovery : A HAI exist in the same name and its HAID=" + existingHAID);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2001 */         Properties params = new Properties();
/* 2002 */         params.put("HostName", vHost);
/* 2003 */         params.put("Port", String.valueOf(vPort));
/* 2004 */         params.put("UserName", userName);
/* 2005 */         params.put("Password", passWord);
/*      */         
/* 2007 */         Properties authCheckRes = DiscoveryUtil.checkVCAuthentication(params);
/* 2008 */         if ((authCheckRes.get("authentication") != null) && ("passed".equals(authCheckRes.get("authentication"))))
/*      */         {
/*      */ 
/*      */           try
/*      */           {
/* 2013 */             vchaid = discUtil.createVCenterHAI(displayname, description);
/*      */             
/*      */ 
/*      */ 
/* 2017 */             if (vchaid != -1)
/*      */             {
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/*      */ 
/*      */ 
/* 2025 */                 if ((EnterpriseUtil.isIt360MSPEdition) && (custId != null))
/*      */                 {
/* 2027 */                   DBUtil.insertParentChildMapper(Integer.parseInt(custId), vchaid);
/*      */                   
/* 2029 */                   String act1 = " INSERT INTO AM_HOLISTICAPPLICATION_EXT(RESOURCEID, APP_TYPE) VALUES (" + vchaid + ", 'BSG0')";
/* 2030 */                   AMConnectionPool.executeUpdateStmt(act1);
/* 2031 */                   AMLog.debug("Added the vcenter group with id : " + vchaid + " as a subgroup to parent : " + custId + " with app_type in AM_HOLISTICAPPLICATION_EXT as : BSG0");
/* 2032 */                   EnterpriseUtil.addUpdateQueryToFile(act1);
/* 2033 */                   request.setAttribute("custId", custId);
/*      */                 }
/*      */               }
/*      */               catch (Exception exp) {
/* 2037 */                 exp.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2056 */               if (!fromRESTApi.equalsIgnoreCase("true"))
/*      */               {
/* 2058 */                 Properties applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/* 2059 */                 applications.setProperty(String.valueOf(vchaid), displayname);
/*      */               }
/*      */               
/*      */ 
/* 2063 */               VCenterDataCollector vCenterDC = new VCenterDataCollector(vHost, String.valueOf(vPort), userName, passWord);
/* 2064 */               ArrayList inventoryList = vCenterDC.fetchInventory();
/* 2065 */               AMLog.debug("HAIDManagerAction : configureVCenterDiscovery :inventoryList =============>" + inventoryList);
/* 2066 */               Hashtable parentIDs = new Hashtable();
/* 2067 */               for (int i = 0; i < inventoryList.size(); i++)
/*      */               {
/* 2069 */                 Hashtable inventoryElement = (Hashtable)inventoryList.get(i);
/* 2070 */                 String elementName = (String)inventoryElement.get("NAME");
/* 2071 */                 String elementType = (String)inventoryElement.get("TYPE");
/* 2072 */                 String elementParent = (String)inventoryElement.get("PARENT");
/* 2073 */                 String elementParentType = (String)inventoryElement.get("PARENTTYPE");
/* 2074 */                 String uuid = (String)inventoryElement.get("UUID");
/* 2075 */                 AMLog.debug("HAIDManagerAction : configureVCenterDiscovery :inventoryElement[" + i + "]==============>" + inventoryElement);
/* 2076 */                 if (!elementType.equals("HostSystem"))
/*      */                 {
/* 2078 */                   parentIDs = discUtil.handleParentElementTypes(vchaid, displayname, parentIDs, inventoryElement);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2131 */                   String tempHostID = VMMonitorDBUtil.isESXpresent(uuid, elementName);
/* 2132 */                   int parentID = vchaid;
/* 2133 */                   if (parentIDs.get(elementParent) != null)
/*      */                   {
/* 2135 */                     parentID = ((Integer)parentIDs.get(elementParent)).intValue();
/*      */                   }
/*      */                   
/* 2138 */                   if ("0".equals(tempHostID))
/*      */                   {
/*      */ 
/*      */ 
/* 2142 */                     ResultSet basetypedescset = null;
/* 2143 */                     int baseid = 0;
/* 2144 */                     String delimiter = null;
/* 2145 */                     String amcreated = null;
/* 2146 */                     String dctype = null;
/* 2147 */                     String dcclass = null;
/* 2148 */                     String basetype = null;
/* 2149 */                     String childmonitortype = "VMWare ESX/ESXi";
/*      */                     try
/*      */                     {
/* 2152 */                       Map baseTypeDetails = discUtil.getBaseTypeDetailsForESXHost(childmonitortype);
/*      */                       
/* 2154 */                       baseid = ((Integer)baseTypeDetails.get("baseId")).intValue();
/* 2155 */                       amcreated = (String)baseTypeDetails.get("amCreated");
/* 2156 */                       dctype = (String)baseTypeDetails.get("dcType");
/* 2157 */                       dcclass = (String)baseTypeDetails.get("dcClass");
/* 2158 */                       basetype = (String)baseTypeDetails.get("baseType");
/* 2159 */                       delimiter = (String)baseTypeDetails.get("delimiter");
/*      */ 
/*      */                     }
/*      */                     catch (Exception exc)
/*      */                     {
/* 2164 */                       exc.printStackTrace();
/*      */                     }
/*      */                     
/*      */ 
/* 2168 */                     int childresourceid = 0;
/* 2169 */                     AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/*      */                     
/*      */                     try
/*      */                     {
/* 2173 */                       childresourceid = discUtil.addESXHost(elementName, childmonitortype, isHostDCviaVC);
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2184 */                       if (EnterpriseUtil.isIt360MSPEdition)
/*      */                       {
/* 2186 */                         DBUtil.insertParentChildMapper(Integer.parseInt(siteId), childresourceid);
/* 2187 */                         AMLog.debug("[HAIDMGACT] vchaid: " + vchaid + ", childresourceid: " + childresourceid + ", siteId: " + siteId);
/*      */                       }
/* 2189 */                       if (childresourceid <= 0) {
/* 2190 */                         AMLog.debug("Unable to add child resource " + displayname + " for type " + childmonitortype);
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 2196 */                         discUtil.insertHostDetailsInAMScriptArgsTable(childresourceid, elementName, poll_interval);
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2212 */                         adder.addInterDependentAttributes(childresourceid);
/*      */                       }
/*      */                     }
/*      */                     catch (Exception ee)
/*      */                     {
/* 2217 */                       ee.printStackTrace();
/*      */                     }
/*      */                     
/*      */ 
/* 2221 */                     if (childresourceid > 0)
/*      */                     {
/*      */ 
/* 2224 */                       if (isHostDCviaVC)
/*      */                       {
/* 2226 */                         DiscoveryUtil.insertArgumentsforResource(vHost, String.valueOf(vPort), userName, passWord, childresourceid, baseid, discoverAndMonitorVM);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2230 */                         DiscoveryUtil.insertArgumentsforResource(elementName, String.valueOf(vPort), "root", "password", childresourceid, baseid, 2);
/*      */                       }
/*      */                       
/*      */                       try
/*      */                       {
/* 2235 */                         discUtil.addDependentAttributesForESXType(childmonitortype, childresourceid);
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2274 */                         String[] resources = { String.valueOf(childresourceid) };
/* 2275 */                         Vector forUpdate = new Vector();
/* 2276 */                         this.mo.updateManagedApplicationResourcesForEnterprise(String.valueOf(parentID), "xyz", resources, forUpdate);
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2284 */                         if (!isHostDCviaVC)
/*      */                         {
/* 2286 */                           DataCollectionControllerUtil.updateUnManageMonitors(new String[0], String.valueOf(childresourceid));
/*      */                         }
/* 2288 */                         Map<String, String> esxHostDetails = new HashMap();
/* 2289 */                         esxHostDetails.put("resourceid", String.valueOf(childresourceid));
/* 2290 */                         esxHostDetails.put("esxHostName", elementName);
/* 2291 */                         esxHostDetails.put("esxHostType", childmonitortype);
/* 2292 */                         esxHostDetails.put("baseType", basetype);
/* 2293 */                         esxHostDetails.put("baseId", String.valueOf(baseid));
/* 2294 */                         esxHostDetails.put("amcreated", amcreated);
/* 2295 */                         esxHostDetails.put("dcClass", dcclass);
/* 2296 */                         esxHostDetails.put("dcType", dctype);
/* 2297 */                         discUtil.scheduleDCForESXHost(esxHostDetails, isHostDCviaVC);
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2316 */                         AMLog.debug("Datacollection for ESX-->" + elementName + " discovered through vcenter: " + displayname + " completed");
/*      */ 
/*      */                       }
/*      */                       catch (Exception exc)
/*      */                       {
/* 2321 */                         exc.printStackTrace();
/* 2322 */                         System.err.println("Unable to parse and add the resource " + elementName + " for type " + childmonitortype);
/*      */                       }
/*      */                       
/*      */                     }
/*      */                   }
/*      */                   else
/*      */                   {
/* 2329 */                     String[] resources = { String.valueOf(tempHostID) };
/* 2330 */                     Vector forUpdate = new Vector();
/* 2331 */                     this.mo.updateManagedApplicationResourcesForEnterprise(String.valueOf(parentID), "xyz", resources, forUpdate);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2339 */                     if (isHostDCviaVC)
/*      */                     {
/* 2341 */                       Vector vcChildVector = new Vector();
/* 2342 */                       vcChildVector.add(String.valueOf(tempHostID));
/* 2343 */                       updateArgumentsforResource(vHost, String.valueOf(vPort), userName, passWord, vcChildVector, NewMonitorUtil.getBaseId("VMWare ESX/ESXi"), discoverAndMonitorVM);
/* 2344 */                       updateResName(tempHostID, elementName, poll_interval);
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 2352 */               Properties paramsMap = new Properties();
/* 2353 */               params.setProperty("vchaid", String.valueOf(vchaid));
/* 2354 */               params.setProperty("vHost", vHost);
/* 2355 */               params.setProperty("vPort", String.valueOf(vPort));
/* 2356 */               params.setProperty("userName", userName);
/* 2357 */               params.setProperty("password", passWord);
/* 2358 */               params.setProperty("poll_interval", String.valueOf(poll_interval));
/* 2359 */               params.setProperty("isHostDCviaVC", String.valueOf(isHostDCviaVC));
/* 2360 */               params.setProperty("discoverAndMonitorVM", String.valueOf(discoverAndMonitorVM));
/*      */               
/* 2362 */               discUtil.insertingVCenterArgs(params);
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2423 */             discUtil.scheduleDCForVCenterHAI(vchaid, displayname);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2436 */             e.printStackTrace();
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 2442 */           addStatus.add(displayname);
/* 2443 */           addStatus.add("Failed");
/* 2444 */           addStatus.add((String)authCheckRes.get("error"));
/* 2445 */           request.setAttribute("discoverystatus", addStatus);
/*      */           
/* 2447 */           ActionMessages messages = new ActionMessages();
/* 2448 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage((String)authCheckRes.get("error")));
/* 2449 */           saveMessages(request, messages);
/* 2450 */           return new ActionForward("/jsp/CreateVMWareVI.jsp");
/*      */         }
/*      */       }
/*      */     }
/* 2454 */     String frwrdPage = "/showapplication.do?method=showApplication";
/* 2455 */     if (mapping != null) {
/* 2456 */       ActionForward frwrd = mapping.findForward("showapplication");
/* 2457 */       frwrdPage = frwrd.getPath();
/*      */     }
/*      */     
/* 2460 */     String path = null;
/* 2461 */     if (vchaid != -1)
/*      */     {
/*      */ 
/* 2464 */       addStatus.add(displayname);
/* 2465 */       addStatus.add("Success");
/* 2466 */       addStatus.add(frwrdPage + "&haid=" + vchaid);
/* 2467 */       request.setAttribute("discoverystatus", addStatus);
/* 2468 */       request.setAttribute("resourceid", String.valueOf(vchaid));
/* 2469 */       return new ActionForward(frwrdPage + "&haid=" + vchaid, true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2474 */     addStatus.add(displayname);
/* 2475 */     addStatus.add("Exist");
/* 2476 */     addStatus.add(frwrdPage + "&haid=" + existingHAID);
/* 2477 */     request.setAttribute("discoverystatus", addStatus);
/* 2478 */     request.setAttribute("resourceid", existingHAID);
/* 2479 */     ActionMessages messages = new ActionMessages();
/* 2480 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.vc.alreadyexists"));
/* 2481 */     saveMessages(request, messages);
/* 2482 */     return new ActionForward(frwrdPage + "&haid=" + existingHAID + "&alreadyexists=true", true);
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
/*      */   private static void updateArgumentsforResource(String hostname, String port, String username, String password, Vector residVector, int baseid, int discoverVM)
/*      */   {
/* 2511 */     Properties props = new Properties();
/* 2512 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2513 */     StringBuilder toset = new StringBuilder();
/*      */     
/* 2515 */     toset.append(DBQueryUtil.getSpecialCharToAppend()).append("HostName").append(DBQueryUtil.getSpecialCharToAppend());
/* 2516 */     toset.append("='").append(hostname).append("',");
/*      */     
/* 2518 */     toset.append(DBQueryUtil.getSpecialCharToAppend()).append("Port").append(DBQueryUtil.getSpecialCharToAppend());
/* 2519 */     toset.append("='").append(port).append("',");
/*      */     
/* 2521 */     toset.append(DBQueryUtil.getSpecialCharToAppend()).append("UserName").append(DBQueryUtil.getSpecialCharToAppend());
/* 2522 */     toset.append("='").append(username).append("',");
/*      */     
/* 2524 */     toset.append(DBQueryUtil.getSpecialCharToAppend()).append("Password").append(DBQueryUtil.getSpecialCharToAppend());
/* 2525 */     toset.append("=").append(DBQueryUtil.encode(password)).append(",");
/*      */     
/* 2527 */     toset.append(DBQueryUtil.getSpecialCharToAppend()).append("AddVMS").append(DBQueryUtil.getSpecialCharToAppend());
/* 2528 */     toset.append("='").append(discoverVM).append("' ");
/* 2529 */     String qry = "update AM_ARGS_" + baseid + " set " + toset.toString() + " where " + DBUtil.getCondition("resourceid", residVector);
/*      */     try
/*      */     {
/* 2532 */       AMConnectionPool.executeUpdateStmt(qry);
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 2536 */       exc.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void updateResName(String hostID, String elementName, int poll_interval)
/*      */   {
/* 2542 */     String updateQuery = "update  AM_ScriptArgs set SCRIPTNAME='" + elementName + "',POLLINTERVAL=" + poll_interval + " where RESOURCEID=" + hostID;
/* 2543 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/* 2546 */       AMConnectionPool.executeUpdateStmt(updateQuery);
/* 2547 */       AM_KeyValueDataCollector amkvdc = (AM_KeyValueDataCollector)AMScriptProcess.resid_instance.get(String.valueOf(hostID));
/* 2548 */       if (amkvdc != null)
/*      */       {
/* 2550 */         amkvdc.setResourceName(elementName);
/*      */       }
/*      */     }
/*      */     catch (Exception ee) {
/* 2554 */       ee.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ActionForward configureHorizonDiscovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 2560 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*      */ 
/* 2563 */       ActionErrors errors = new ActionErrors();
/* 2564 */       ActionMessages messages = new ActionMessages();
/* 2565 */       String haid = request.getParameter("haid");
/* 2566 */       String resourcetype = request.getParameter("type");
/* 2567 */       Properties argsprops = new Properties();
/* 2568 */       argsprops.setProperty("monitorType", resourcetype);
/* 2569 */       for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
/*      */       {
/* 2571 */         String param = (String)e.nextElement();
/* 2572 */         if (!argsprops.containsKey(param))
/*      */         {
/* 2574 */           argsprops.setProperty(param, request.getParameter(param));
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 2579 */         HashMap<String, String> responseMap = AAMMonitorAdder.addMonitor(argsprops);
/* 2580 */         ArrayList<String> al1 = new ArrayList();
/* 2581 */         String displayname = request.getParameter("displayname");
/* 2582 */         if ((displayname == null) || (displayname.trim().length() == 0)) {
/* 2583 */           displayname = request.getParameter("displayName");
/*      */         }
/* 2585 */         String status = "Success";
/* 2586 */         String message = "/showresource.do?resourceid=" + (String)responseMap.get("resourceId") + "&method=showResourceForResourceID";
/* 2587 */         if ((resourcetype != null) && (resourcetype.equals("VMwareView"))) {
/* 2588 */           message = "/showapplication.do?method=showApplication&haid=" + (String)responseMap.get("resourceId");
/*      */         }
/* 2590 */         String masDisplayName = (String)responseMap.get("managedServerDispName");
/* 2591 */         if (((String)responseMap.get("addStatus")).equals("false")) {
/* 2592 */           status = "Failed";
/* 2593 */           message = (String)responseMap.get("message");
/*      */         }
/* 2595 */         al1.add(displayname);
/* 2596 */         al1.add(status);
/* 2597 */         al1.add(message);
/* 2598 */         al1.add(masDisplayName);
/* 2599 */         request.setAttribute("discoverystatus", al1);
/* 2600 */         request.setAttribute("type", resourcetype);
/* 2601 */         request.setAttribute("basetype", "Script Monitor");
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2606 */         e.printStackTrace();
/*      */       }
/* 2608 */       String forwardPage = "/adminAction.do?method=reloadHostDiscoveryForm&type=" + resourcetype + "&haid=" + haid;
/* 2609 */       if ((resourcetype != null) && (resourcetype.equals("VMwareView"))) {
/* 2610 */         forwardPage = "/admin/createapplication.do?method=createapp&grouptype=4";
/*      */       }
/* 2612 */       return new ActionForward(forwardPage);
/*      */     }
/* 2614 */     ArrayList<String> addStatus = new ArrayList();
/* 2615 */     String displayname = null;
/* 2616 */     String vHost = null;
/* 2617 */     String userName = null;
/* 2618 */     String passWord = null;
/* 2619 */     int pollinterval = 5;
/* 2620 */     String fromRESTApi = request.getParameter("fromRESTAPI");
/* 2621 */     fromRESTApi = fromRESTApi == null ? "" : fromRESTApi;
/* 2622 */     if (fromRESTApi.equalsIgnoreCase("true")) {
/* 2623 */       displayname = request.getParameter("displayname");
/* 2624 */       vHost = request.getParameter("host");
/* 2625 */       userName = request.getParameter("username");
/* 2626 */       passWord = request.getParameter("password");
/* 2627 */       pollinterval = Integer.parseInt(request.getParameter("pollInterval"));
/*      */     }
/*      */     else
/*      */     {
/* 2631 */       DynaActionForm amform = (DynaActionForm)form;
/* 2632 */       vHost = (String)amform.get("vcHost");
/* 2633 */       userName = (String)amform.get("username");
/* 2634 */       passWord = (String)amform.get("password");
/* 2635 */       displayname = (String)amform.get("displayname");
/* 2636 */       pollinterval = ((Integer)amform.get("pollInterval")).intValue();
/*      */     }
/*      */     
/* 2639 */     int poll_interval = pollinterval * 60;
/* 2640 */     int vchaid = -1;
/* 2641 */     String custId = "-1";
/* 2642 */     String siteId = "-1";
/* 2643 */     if (EnterpriseUtil.isIt360MSPEdition)
/*      */     {
/* 2645 */       custId = (String)((DynaActionForm)form).get("organization");
/* 2646 */       siteId = (String)((DynaActionForm)form).get("site");
/* 2647 */       AMLog.debug("[HAIDMGACT] custId: " + custId + ", siteId: " + siteId);
/*      */     }
/* 2649 */     String description = FormatUtil.getString("am.webclient.mg.vmware.horizon.defaultdescription.text");
/* 2650 */     String isEdit = request.getParameter("isEdit");
/* 2651 */     String existingHAID = null;
/*      */     
/*      */ 
/* 2654 */     if ("true".equals(isEdit))
/*      */     {
/* 2656 */       String haRESID = request.getParameter("haRESID");
/* 2657 */       ResultSet nameSet = null;
/* 2658 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */       try {
/* 2660 */         nameSet = AMConnectionPool.executeQueryStmt("select DISPLAYNAME from AM_ManagedObject where RESOURCEID=" + haRESID);
/* 2661 */         if (nameSet.next())
/*      */         {
/* 2663 */           ActionForward frwrd = mapping.findForward("showapplication");
/* 2664 */           if (!displayname.equalsIgnoreCase(nameSet.getString("DISPLAYNAME")))
/*      */           {
/* 2666 */             ArrayList existingLists = this.mo.getRows("select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + displayname + "' and AM_ManagedObject.TYPE='HAI' ");
/* 2667 */             if ((existingLists != null) && (existingLists.size() > 0))
/*      */             {
/* 2669 */               ArrayList list = (ArrayList)existingLists.get(0);
/* 2670 */               existingHAID = (String)list.get(0);
/*      */             }
/* 2672 */             if (existingHAID != null)
/*      */             {
/* 2674 */               AMLog.debug("HAIDManagerAction : configureHorizonDiscovery : A HAI exist in the same name and its HAID=" + existingHAID);
/* 2675 */               ActionMessages messages = new ActionMessages();
/* 2676 */               messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.vmware.horizon.alreadyexists"));
/* 2677 */               saveMessages(request, messages);
/* 2678 */               return new ActionForward(frwrd.getPath() + "&haid=" + haRESID, true);
/*      */             }
/*      */           }
/*      */           
/* 2682 */           if (haRESID != null)
/*      */           {
/* 2684 */             vchaid = Integer.parseInt(haRESID); }
/*      */           String ipdateVCARGSQuery;
/* 2686 */           if (vchaid != -1)
/*      */           {
/* 2688 */             String updateMOQuery = "update AM_ManagedObject set RESOURCENAME='" + displayname + "',DISPLAYNAME='" + displayname + "' where RESOURCEID=" + haRESID;
/* 2689 */             ipdateVCARGSQuery = "update AM_VMHORIZON_ARGS set HOSTNAME=?, USERNAME=?, PASSWORD=" + DBQueryUtil.encodetoBytes("?") + ", POLLINGINTERVAL=? where RESID=?";
/*      */             try
/*      */             {
/* 2692 */               Properties params = new Properties();
/* 2693 */               params.put("hostname", vHost);
/*      */               
/* 2695 */               params.put("username", userName);
/* 2696 */               params.put("password", passWord);
/*      */               
/* 2698 */               Properties authCheckRes = checkHorizonAuthentication(params);
/* 2699 */               if ((authCheckRes.get("authentication") != null) && ("passed".equals(authCheckRes.get("authentication"))))
/*      */               {
/*      */ 
/* 2702 */                 AMConnectionPool.executeUpdateStmt(updateMOQuery);
/* 2703 */                 EnterpriseUtil.addUpdateQueryToFile(updateMOQuery);
/* 2704 */                 PreparedStatement ps = null;
/*      */                 try
/*      */                 {
/* 2707 */                   ps = AMConnectionPool.getConnection().prepareStatement(ipdateVCARGSQuery);
/* 2708 */                   ps.setString(1, vHost);
/*      */                   
/* 2710 */                   ps.setString(2, userName);
/* 2711 */                   ps.setBytes(3, passWord.getBytes());
/* 2712 */                   ps.setInt(4, poll_interval);
/* 2713 */                   ps.setInt(6, vchaid);
/* 2714 */                   ps.executeUpdate();
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   try
/*      */                   {
/* 2724 */                     ps.close();
/*      */                   }
/*      */                   catch (Exception exc) {}
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/* 2731 */                   messages = new ActionMessages();
/*      */                 }
/*      */                 catch (Exception exc)
/*      */                 {
/* 2718 */                   exc.printStackTrace();
/*      */                 }
/*      */                 finally
/*      */                 {
/*      */                   try
/*      */                   {
/* 2724 */                     ps.close();
/*      */                   }
/*      */                   catch (Exception exc) {}
/*      */                 }
/*      */                 
/*      */ 
/*      */                 ActionMessages messages;
/*      */                 
/* 2732 */                 messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationupdation.success"));
/* 2733 */                 saveMessages(request, messages);
/* 2734 */                 return new ActionForward(frwrd.getPath() + "&haid=" + haRESID + "&isEdit=true&status=success", true);
/*      */               }
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/* 2739 */               ee.printStackTrace();
/*      */             }
/*      */           }
/* 2742 */           ActionMessages messages = new ActionMessages();
/* 2743 */           messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("am.webclient.vc.update.failedmsg"));
/* 2744 */           saveMessages(request, messages);
/* 2745 */           return new ActionForward(frwrd.getPath() + "&haid=" + haRESID + "&isEdit=true&status=failed", true);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 2751 */         ee.printStackTrace();
/*      */       }
/*      */       finally {
/* 2754 */         AMConnectionPool.closeStatement(nameSet);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 2759 */       ArrayList existingLists = this.mo.getRows("select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + displayname + "' and AM_ManagedObject.TYPE='HAI' ");
/*      */       
/* 2761 */       if ((existingLists != null) && (existingLists.size() > 0))
/*      */       {
/* 2763 */         ArrayList list = (ArrayList)existingLists.get(0);
/* 2764 */         existingHAID = (String)list.get(0);
/*      */       }
/* 2766 */       if (existingHAID == null)
/*      */       {
/*      */ 
/* 2769 */         existingLists = this.mo.getRows("select mo.RESOURCEID from AM_VMHORIZON_ARGS as vc, AM_ManagedObject as mo where HOSTNAME='" + vHost + "'  and vc.RESID=mo.RESOURCEID");
/*      */         
/* 2771 */         if ((existingLists != null) && (existingLists.size() > 0))
/*      */         {
/* 2773 */           ArrayList list = (ArrayList)existingLists.get(0);
/* 2774 */           existingHAID = (String)list.get(0);
/*      */         }
/*      */       }
/*      */       
/* 2778 */       if (existingHAID != null)
/*      */       {
/* 2780 */         AMLog.debug("HAIDManagerAction : configureHorizonDiscovery : A HAI exist in the same name and its HAID=" + existingHAID);
/*      */       }
/*      */       else
/*      */       {
/* 2784 */         String os = System.getProperty("os.name");
/* 2785 */         if (os.startsWith("Linux"))
/*      */         {
/*      */ 
/* 2788 */           addStatus.add(displayname);
/* 2789 */           addStatus.add("Failed");
/* 2790 */           addStatus.add(FormatUtil.getString("am.webclient.newresourcetypes.vmware.view.broker.text"));
/* 2791 */           request.setAttribute("discoverystatus", addStatus);
/*      */           
/* 2793 */           ActionMessages messages = new ActionMessages();
/* 2794 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage(FormatUtil.getString("am.webclient.newresourcetypes.vmware.view.broker.text")));
/* 2795 */           saveMessages(request, messages);
/* 2796 */           return new ActionForward("/jsp/CreateVMwareHorizon.jsp");
/*      */         }
/*      */         
/* 2799 */         Properties params = new Properties();
/* 2800 */         params.put("hostname", vHost);
/*      */         
/* 2802 */         params.put("username", userName);
/* 2803 */         params.put("password", passWord);
/*      */         
/* 2805 */         Properties authCheckRes = checkHorizonAuthentication(params);
/* 2806 */         if ((authCheckRes.get("authentication") != null) && ("passed".equals(authCheckRes.get("authentication"))))
/*      */         {
/* 2808 */           AMManagedObject ammo = this.mo.createManagedApplication(displayname, description, "admin", null, null, false, 4);
/* 2809 */           if (ammo != null)
/*      */           {
/*      */ 
/* 2812 */             vchaid = ammo.getRESOURCEID();
/*      */             try {
/* 2814 */               AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2815 */               AMConnectionPool.executeUpdateStmt("insert into AM_MinMaxAvgData (ARCHIVEDTIME, RESID, DURATION, ATTRIBUTEID, MINVALUE, MAXVALUE, TOTAL, TOTALCOUNT) values('" + System.currentTimeMillis() + "', '" + vchaid + "', '-1','17','0','0','0','1')");
/*      */               
/* 2817 */               if ((EnterpriseUtil.isIt360MSPEdition) && (custId != null))
/*      */               {
/* 2819 */                 DBUtil.insertParentChildMapper(Integer.parseInt(custId), vchaid);
/*      */                 
/* 2821 */                 String act1 = " INSERT INTO AM_HOLISTICAPPLICATION_EXT(RESOURCEID, APP_TYPE) VALUES (" + vchaid + ", 'BSG0')";
/* 2822 */                 AMConnectionPool.executeUpdateStmt(act1);
/* 2823 */                 AMLog.debug("Added the vcenter group with id : " + vchaid + " as a subgroup to parent : " + custId + " with app_type in AM_HOLISTICAPPLICATION_EXT as : BSG0");
/* 2824 */                 EnterpriseUtil.addUpdateQueryToFile(act1);
/* 2825 */                 request.setAttribute("custId", custId);
/*      */               }
/*      */             }
/*      */             catch (Exception exp) {
/* 2829 */               exp.printStackTrace();
/*      */             }
/*      */             
/*      */ 
/*      */             try
/*      */             {
/* 2835 */               int loc = -1;
/* 2836 */               String query = "insert into AM_GMapCountryResourceRel values(" + vchaid + "," + loc + ")";
/*      */               
/* 2838 */               AMConnectionPool.executeUpdateStmt(query);
/*      */             }
/*      */             catch (NumberFormatException e)
/*      */             {
/* 2842 */               e.printStackTrace();
/*      */             }
/*      */             catch (Exception ee)
/*      */             {
/* 2846 */               ee.printStackTrace();
/*      */             }
/* 2848 */             if (!fromRESTApi.equalsIgnoreCase("true")) {
/* 2849 */               Properties applications = (Properties)this.servlet.getServletConfig().getServletContext().getAttribute("applications");
/* 2850 */               applications.setProperty(String.valueOf(vchaid), displayname);
/*      */             }
/*      */             
/*      */ 
/* 2854 */             AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2855 */             String qry = "insert into AM_VMHORIZON_ARGS values(?,?,?," + DBQueryUtil.encodetoBytes("?") + ",?)";
/* 2856 */             PreparedStatement ps = null;
/*      */             
/*      */ 
/*      */             try
/*      */             {
/* 2861 */               ps = AMConnectionPool.getConnection().prepareStatement(qry);
/* 2862 */               ps.setInt(1, vchaid);
/* 2863 */               ps.setString(2, vHost);
/* 2864 */               ps.setString(3, userName);
/* 2865 */               ps.setBytes(4, passWord.getBytes());
/* 2866 */               ps.setInt(5, poll_interval);
/* 2867 */               ps.executeUpdate();
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               try
/*      */               {
/* 2877 */                 ps.close();
/*      */               }
/*      */               catch (Exception exc) {}
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2886 */               amkv = new AM_KeyValueDataCollector(String.valueOf(vchaid), displayname, "vmwareview");
/*      */             }
/*      */             catch (Exception exc)
/*      */             {
/* 2871 */               exc.printStackTrace();
/*      */             }
/*      */             finally
/*      */             {
/*      */               try
/*      */               {
/* 2877 */                 ps.close();
/*      */               }
/*      */               catch (Exception exc) {}
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */             AM_KeyValueDataCollector amkv;
/*      */             
/*      */ 
/*      */ 
/* 2888 */             if (amkv != null)
/*      */             {
/* 2890 */               Scheduler.getScheduler("KeyValue_Monitor").scheduleTask(amkv, System.currentTimeMillis());
/* 2891 */               AMScriptProcess.resid_instance.put(Integer.valueOf(vchaid), amkv);
/*      */             }
/*      */             
/*      */           }
/*      */           
/*      */         }
/*      */         else
/*      */         {
/* 2899 */           addStatus.add(displayname);
/* 2900 */           addStatus.add("Failed");
/* 2901 */           addStatus.add((String)authCheckRes.get("error"));
/* 2902 */           request.setAttribute("discoverystatus", addStatus);
/*      */           
/* 2904 */           ActionMessages messages = new ActionMessages();
/* 2905 */           messages.add("org.apache.struts.action.ERROR", new ActionMessage((String)authCheckRes.get("error")));
/* 2906 */           saveMessages(request, messages);
/* 2907 */           return new ActionForward("/jsp/CreateVMwareHorizon.jsp");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2913 */     String frwrdPage = "/showapplication.do?method=showApplication";
/* 2914 */     if (mapping != null) {
/* 2915 */       ActionForward frwrd = mapping.findForward("showapplication");
/* 2916 */       frwrdPage = frwrd.getPath();
/*      */     }
/*      */     
/* 2919 */     if (vchaid != -1)
/*      */     {
/*      */ 
/* 2922 */       addStatus.add(displayname);
/* 2923 */       addStatus.add("Success");
/* 2924 */       addStatus.add(frwrdPage + "&haid=" + vchaid);
/* 2925 */       request.setAttribute("discoverystatus", addStatus);
/* 2926 */       request.setAttribute("resourceid", String.valueOf(vchaid));
/* 2927 */       return new ActionForward(frwrdPage + "&haid=" + vchaid, true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2932 */     addStatus.add(displayname);
/* 2933 */     addStatus.add("Exist");
/* 2934 */     addStatus.add(frwrdPage + "&haid=" + existingHAID);
/* 2935 */     request.setAttribute("discoverystatus", addStatus);
/* 2936 */     request.setAttribute("resourceid", existingHAID);
/* 2937 */     ActionMessages messages = new ActionMessages();
/* 2938 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("haid.applicationcreation.vc.alreadyexists"));
/* 2939 */     saveMessages(request, messages);
/* 2940 */     return new ActionForward(frwrdPage + "&haid=" + existingHAID + "&alreadyexists=true", true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties checkHorizonAuthentication(Properties props)
/*      */   {
/* 2950 */     VMwareViewDataCollector viewDC = new VMwareViewDataCollector();
/* 2951 */     Properties authresult = viewDC.CheckAuthentication(props);
/* 2952 */     return authresult;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ActionForward removeMonitorGrpFromDependency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 2962 */       String parentGroupId = request.getParameter("parentGroupId");
/* 2963 */       String selectedGroupId = request.getParameter("selectedGroupId");
/* 2964 */       String fromAjax = request.getParameter("fromAjax");
/* 2965 */       if ((fromAjax == null) || (fromAjax.trim().length() == 0) || (fromAjax.equalsIgnoreCase("null"))) {
/* 2966 */         fromAjax = "false";
/*      */       }
/* 2968 */       boolean removeStaus = RuleAnalyser.removeMonitorGrpFromDependency(parentGroupId, selectedGroupId);
/*      */       
/*      */ 
/* 2971 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentGroupId), 17, System.currentTimeMillis(), true, true, 1);
/* 2972 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentGroupId), 18, System.currentTimeMillis(), true, false, 2);
/*      */       
/*      */ 
/* 2975 */       if (fromAjax.equalsIgnoreCase("true")) {
/* 2976 */         PrintWriter writer = response.getWriter();
/* 2977 */         JSONObject responseObj = new JSONObject();
/* 2978 */         response.setContentType("text/json");
/* 2979 */         response.setCharacterEncoding("UTF-8");
/* 2980 */         responseObj.put("result", removeStaus);
/* 2981 */         if (!removeStaus) {
/* 2982 */           responseObj.put("message", FormatUtil.getString("am.webclient.dependent.mgroup.deleteFailed.errorMsg"));
/*      */         }
/* 2984 */         writer.print(responseObj);
/* 2985 */         return null;
/*      */       }
/* 2987 */       return new ActionForward("/showapplication.do?method=showApplication&haid=" + parentGroupId, true);
/*      */     } catch (Exception ex) {
/* 2989 */       ex.printStackTrace();
/*      */     }
/* 2991 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\HAIDManagerAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */