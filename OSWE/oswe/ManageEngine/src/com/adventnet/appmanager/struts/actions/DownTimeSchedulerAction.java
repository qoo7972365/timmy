/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DowntimeScheduleUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.sql.Time;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessage;
/*      */ import org.apache.struts.action.ActionMessages;
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
/*      */ public class DownTimeSchedulerAction
/*      */   extends DispatchAction
/*      */ {
/*   55 */   private ManagedApplication mo = new ManagedApplication();
/*   56 */   private String types = Constants.resourceTypes;
/*   57 */   private String allProcessAndServiceMonsQry = "select parchildmap.PARENTID, ammo1.DISPLAYNAME as PARENTNAME, parchildmap.CHILDID, ammo2.DISPLAYNAME as CHILDNAME from AM_PARENTCHILDMAPPER parchildmap  LEFT JOIN AM_ManagedObject ammo1 on ammo1.RESOURCEID=parchildmap.PARENTID LEFT JOIN AM_ManagedObject ammo2 on ammo2.RESOURCEID=parchildmap.CHILDID where ammo2.TYPE in (" + Constants.downTimeScheduler_2LevelTypes + ") AND ammo1.TYPE NOT IN ('HAI')";
/*      */   
/*      */   public ActionForward maintenanceTaskListView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*   61 */     AMActionForm amform = (AMActionForm)form;
/*   62 */     Vector<String> ids_owned = new Vector();
/*   63 */     Vector<String> taskIds = new Vector();
/*   64 */     ArrayList<Properties> list = new ArrayList();
/*   65 */     boolean isPriviledgedUser = false;
/*   66 */     int userId = DelegatedUserRoleUtil.getLoginUserid(request);
/*   67 */     if ((Constants.getUserType() != null) && (Constants.getUserType().equals("F")))
/*      */     {
/*   69 */       request.setAttribute("tabtoselect", "4");
/*   70 */       request.setAttribute("helpkey", "maintenanceTaskListView");
/*   71 */       return new ActionForward("/jsp/helpmessages_container.jsp");
/*      */     }
/*   73 */     request.setAttribute("productEdition", Constants.getCategorytype());
/*   74 */     DataCollectionControllerUtil DCCU = new DataCollectionControllerUtil();
/*   75 */     if (Constants.isPrivilegedUser(request))
/*      */     {
/*   77 */       isPriviledgedUser = true;
/*   78 */       ids_owned = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(request, 5);
/*   79 */       taskIds = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(userId, 5);
/*   80 */       ArrayList<String> tasks = getSchedulesForResourcesOwnedByUser(request);
/*   81 */       taskIds.addAll(tasks);
/*   82 */       list = DataCollectionControllerUtil.getDowntimeList("", taskIds);
/*      */     }
/*      */     else
/*      */     {
/*   86 */       list = DataCollectionControllerUtil.getDowntimeList("");
/*      */     }
/*   88 */     boolean usrConf = DBUtil.getGlobalConfigValueasBoolean("allowOprViewAllDownTimeSchedule");
/*   89 */     if ((request.isUserInRole("OPERATOR")) && (!usrConf))
/*      */     {
/*   91 */       list = DowntimeScheduleUtil.getTaskListofUser(request.getRemoteUser(), list);
/*      */     }
/*   93 */     list = convertTo18N(list);
/*   94 */     request.setAttribute("list", list);
/*   95 */     request.setAttribute("ids_owned", ids_owned);
/*   96 */     request.setAttribute("isPriviledgedUser", Boolean.valueOf(isPriviledgedUser));
/*   97 */     ResultSet result = null;
/*      */     try
/*      */     {
/*  100 */       result = AMConnectionPool.executeQueryStmt("select * from AM_GLOBALCONFIG where name in ('availability.up.under.maintenance', 'health.clear.under.maintenance', 'enable.processmons.under.downtimescheduler.config','unmanage.monitor.after.maintenance')");
/*  101 */       while (result.next())
/*      */       {
/*  103 */         if (result.getString(1).equals("availability.up.under.maintenance"))
/*  104 */           amform.setAvailabilityUpUnderMaintenance(result.getString(2));
/*  105 */         if (result.getString(1).equals("health.clear.under.maintenance"))
/*  106 */           amform.setHealthClearUnderMaintenance(result.getString(2));
/*  107 */         if (result.getString(1).equals("enable.processmons.under.downtimescheduler.config"))
/*  108 */           amform.setProcessMonsEnablingStatusInDS(result.getString(2));
/*  109 */         if (result.getString(1).equals("unmanage.monitor.after.maintenance")) {
/*  110 */           amform.setUnmanageMonitorAfterMaintenance(result.getString(2));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex) {
/*  115 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  118 */       AMConnectionPool.closeResultSet(result);
/*      */     }
/*  120 */     String tabtoLoad = request.getParameter("tabtoLoad");
/*  121 */     request.setAttribute("tabtoLoad", tabtoLoad);
/*  122 */     return new ActionForward("/jsp/MaintenanceTaskListView.jsp");
/*      */   }
/*      */   
/*      */   public ActionForward viewMaintenanceTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  127 */     String effectfrom = request.getParameter("taskEffectFrom") + ":00";
/*  128 */     String timezone = request.getParameter("timezone");
/*  129 */     ResultSet rs = null;
/*  130 */     ArrayList<Properties> rows = new ArrayList();
/*  131 */     ArrayList<Properties> rowsg = new ArrayList();
/*  132 */     String taskid = request.getParameter("taskid");
/*  133 */     AMActionForm amform = (AMActionForm)form;
/*      */     
/*  135 */     String doEnableProcessMonsInDS = DBUtil.getGlobalConfigValue("enable.processmons.under.downtimescheduler.config");
/*  136 */     String count = "0";
/*  137 */     Properties h = new Properties();
/*  138 */     h.put("Sunday", "1");
/*  139 */     h.put("Monday", "2");
/*  140 */     h.put("Tuesday", "3");
/*  141 */     h.put("Wednesday", "4");
/*  142 */     h.put("Thursday", "5");
/*  143 */     h.put("Friday", "6");
/*  144 */     h.put("Saturday", "7");
/*  145 */     String commaSeperatedResIds = null;
/*  146 */     boolean privalagedUser = false;
/*  147 */     boolean isUserResourceEnabled = false;
/*  148 */     String loginUserid = null;
/*  149 */     if (ClientDBUtil.isPrivilegedUser(request))
/*      */     {
/*  151 */       privalagedUser = true;
/*  152 */       if (Constants.isUserResourceEnabled()) {
/*  153 */         loginUserid = Constants.getLoginUserid(request);
/*  154 */         isUserResourceEnabled = true;
/*      */       } else {
/*  156 */         commaSeperatedResIds = convertVectorToString(ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/*      */       }
/*      */     }
/*      */     
/*  160 */     String retaintree = "";
/*      */     Hashtable childMOs;
/*      */     Hashtable childMOsforMG;
/*  163 */     try { if (Constants.sqlManager)
/*      */       {
/*  165 */         this.types = Constants.sqlManagerresourceTypes;
/*      */       }
/*      */       
/*  168 */       if ((doEnableProcessMonsInDS.equalsIgnoreCase("true")) && (this.types.indexOf(",'Process','Service'") == -1))
/*      */       {
/*  170 */         int chrIndex = this.types.lastIndexOf(")");
/*  171 */         this.types = this.types.substring(0, chrIndex);
/*  172 */         this.types += ",'Process','Service')";
/*  173 */         AMLog.debug("DTSA types::: " + this.types);
/*      */       }
/*  175 */       request.setAttribute("reloadperiod", "120");
/*  176 */       String userName = request.getRemoteUser();
/*  177 */       ActionMessages messages = new ActionMessages();
/*  178 */       ArrayList<String> MonitorsinMGs = new ArrayList();
/*  179 */       ArrayList<ArrayList<String>> list = new ArrayList(5);
/*  180 */       String listQuery = "select  A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID  left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and (AM_HOLISTICAPPLICATION_EXT.APP_TYPE is NULL or AM_HOLISTICAPPLICATION_EXT.APP_TYPE not like 'SLA') order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*  181 */       if (privalagedUser)
/*      */       {
/*  183 */         if (isUserResourceEnabled) {
/*  184 */           listQuery = "select  A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE from AM_ManagedObject as A1,AM_USERRESOURCESTABLE,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_USERRESOURCESTABLE.RESOURCEID=A1.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and (AM_HOLISTICAPPLICATION_EXT.APP_TYPE is NULL or AM_HOLISTICAPPLICATION_EXT.APP_TYPE not like 'SLA') order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */         } else {
/*  186 */           listQuery = "select  A1.RESOURCENAME,A1.DISPLAYNAME,child.ACTIONSTATUS as childactionstatus,'-1','-1','-1',AM_HOLISTICAPPLICATION.HAID,AM_UnManagedNodes.resid,AM_PARENTCHILDMAPPER.CHILDID,child.DISPLAYNAME  as test,child.TYPE,A1.ACTIONSTATUS, AM_ManagedResourceType.IMAGEPATH,AM_ManagedResourceType.SHORTNAME,secondunmanage.resid,AM_HOLISTICAPPLICATION.TYPE from AM_ManagedObject as A1,AM_HOLISTICAPPLICATION left outer join AM_UnManagedNodes on AM_UnManagedNodes.resid=AM_HOLISTICAPPLICATION.HAID left outer join AM_HOLISTICAPPLICATION_EXT on AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID left outer join AM_ManagedObject as child on child.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_UnManagedNodes as secondunmanage on secondunmanage.resid=child.RESOURCEID left outer join AM_ManagedResourceType  on AM_ManagedResourceType.RESOURCETYPE=child.TYPE where AM_HOLISTICAPPLICATION.HAID=A1.RESOURCEID and A1.RESOURCEID in (" + commaSeperatedResIds + ") and (AM_HOLISTICAPPLICATION_EXT.APP_TYPE is NULL or AM_HOLISTICAPPLICATION_EXT.APP_TYPE not like 'SLA') order by A1.DISPLAYNAME,child.DISPLAYNAME";
/*      */         }
/*      */       }
/*  189 */       list = this.mo.getRows(listQuery);
/*  190 */       ArrayList<ArrayList<String>> listofGroups = new ArrayList();
/*  191 */       childMOs = new Hashtable();
/*  192 */       childMOsforMG = new Hashtable();
/*  193 */       for (int j = 0; j < list.size(); j++)
/*      */       {
/*  195 */         ArrayList<String> singlerow = (ArrayList)list.get(j);
/*  196 */         String resourcename = (String)singlerow.get(0);
/*  197 */         String displayname = (String)singlerow.get(1);
/*  198 */         String childactionstatus = (String)singlerow.get(2);
/*  199 */         String owner = (String)singlerow.get(3);
/*  200 */         String CREATIONDATE = (String)singlerow.get(4);
/*  201 */         String MODIFIEDDATE = (String)singlerow.get(5);
/*  202 */         String MGresourceid = (String)singlerow.get(6);
/*      */         
/*  204 */         boolean dontList = false;
/*  205 */         if (EnterpriseUtil.isIt360MSPEdition) { CustomerManagementAPI.getInstance(); if (!CustomerManagementAPI.isSiteId(MGresourceid)) { CustomerManagementAPI.getInstance(); if (!CustomerManagementAPI.isCustomerId(MGresourceid)) {}
/*      */           } else {
/*  207 */             dontList = true;
/*      */           } }
/*  209 */         if ((MGresourceid != null) && (!dontList))
/*      */         {
/*  211 */           int mgResidint = Integer.parseInt(MGresourceid);
/*  212 */           if ((EnterpriseUtil.isAdminServer()) && (mgResidint > EnterpriseUtil.RANGE))
/*      */           {
/*  214 */             displayname = displayname + "_" + CommDBUtil.getManagedServerNameWithPort(MGresourceid);
/*      */           }
/*  216 */           String unmanagednodes = (String)singlerow.get(7);
/*  217 */           String childid = (String)singlerow.get(8);
/*  218 */           String childname = (String)singlerow.get(9);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  228 */           String childtype = (String)singlerow.get(10);
/*  229 */           String MGactionstatus = (String)singlerow.get(11);
/*  230 */           String imagepath = (String)singlerow.get(12);
/*  231 */           String shortname = (String)singlerow.get(13);
/*  232 */           String unmanageChildmos = (String)singlerow.get(14);
/*      */           
/*  234 */           String MGType = (String)singlerow.get(15);
/*  235 */           MonitorsinMGs.add(childid);
/*      */           
/*  237 */           if ((childMOs.containsKey(MGresourceid)) || (childMOsforMG.containsKey(MGresourceid)))
/*      */           {
/*  239 */             ArrayList<ArrayList<String>> childmo = null;
/*  240 */             if (childtype != null)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*  245 */               if (childtype.equals("HAI"))
/*      */               {
/*  247 */                 if (childMOsforMG.get(MGresourceid) != null)
/*      */                 {
/*  249 */                   childmo = (ArrayList)childMOsforMG.get(MGresourceid);
/*      */                 }
/*      */                 else
/*      */                 {
/*  253 */                   childmo = new ArrayList();
/*  254 */                   childMOsforMG.put(MGresourceid, childmo);
/*      */                 }
/*      */                 
/*      */ 
/*      */               }
/*  259 */               else if (childMOs.get(MGresourceid) != null)
/*      */               {
/*  261 */                 childmo = (ArrayList)childMOs.get(MGresourceid);
/*      */               }
/*      */               else
/*      */               {
/*  265 */                 childmo = new ArrayList();
/*  266 */                 childMOs.put(MGresourceid, childmo);
/*      */               }
/*      */               
/*  269 */               ArrayList<String> singrow = new ArrayList();
/*  270 */               if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childmo != null))
/*      */               {
/*  272 */                 singrow.add(childid);
/*  273 */                 singrow.add(childname);
/*  274 */                 singrow.add(childtype);
/*  275 */                 singrow.add(imagepath);
/*  276 */                 singrow.add(shortname);
/*  277 */                 singrow.add(unmanageChildmos);
/*  278 */                 singrow.add(childactionstatus);
/*  279 */                 childmo.add(singrow);
/*      */               }
/*      */             }
/*      */           }
/*      */           else {
/*  284 */             ArrayList<ArrayList<String>> childmo1 = new ArrayList();
/*  285 */             ArrayList<String> singrow = new ArrayList();
/*      */             
/*  287 */             if ((childid != null) && (!childid.equalsIgnoreCase("null")) && (childtype != null))
/*      */             {
/*  289 */               singrow.add(childid);
/*  290 */               singrow.add(childname);
/*  291 */               singrow.add(childtype);
/*  292 */               singrow.add(imagepath);
/*  293 */               singrow.add(shortname);
/*  294 */               singrow.add(unmanageChildmos);
/*  295 */               singrow.add(childactionstatus);
/*  296 */               childmo1.add(singrow);
/*  297 */               if (childtype.equals("HAI"))
/*      */               {
/*  299 */                 childMOsforMG.put(MGresourceid, childmo1);
/*      */               }
/*      */               else
/*      */               {
/*  303 */                 childMOs.put(MGresourceid, childmo1);
/*      */               }
/*      */               
/*      */             }
/*      */             else
/*      */             {
/*  309 */               ArrayList dummylist = new ArrayList();
/*  310 */               childMOs.put(MGresourceid, dummylist);
/*      */             }
/*      */             
/*  313 */             ArrayList<String> singlemonitorgroup = new ArrayList();
/*  314 */             singlemonitorgroup.add(resourcename);
/*  315 */             singlemonitorgroup.add(displayname);
/*  316 */             singlemonitorgroup.add(MGType);
/*  317 */             singlemonitorgroup.add(owner);
/*  318 */             singlemonitorgroup.add(CREATIONDATE);
/*  319 */             singlemonitorgroup.add(MODIFIEDDATE);
/*  320 */             singlemonitorgroup.add(MGresourceid);
/*  321 */             singlemonitorgroup.add("HAI");
/*  322 */             singlemonitorgroup.add(unmanagednodes);
/*  323 */             singlemonitorgroup.add(MGactionstatus);
/*  324 */             listofGroups.add(singlemonitorgroup);
/*      */           }
/*      */         }
/*      */       }
/*  328 */       Hashtable childlist = new Hashtable();
/*      */       try
/*      */       {
/*  331 */         for (int k = 0; k < listofGroups.size(); k++)
/*      */         {
/*  333 */           ArrayList singlerow = (ArrayList)listofGroups.get(k);
/*  334 */           String tempid = (String)singlerow.get(6);
/*  335 */           ArrayList mosinOrder = new ArrayList();
/*  336 */           if (childMOsforMG.get(tempid) != null)
/*      */           {
/*  338 */             mosinOrder = (ArrayList)childMOsforMG.get(tempid);
/*      */           }
/*  340 */           if (childMOs.get(tempid) != null)
/*      */           {
/*  342 */             ArrayList<String> monitors = (ArrayList)childMOs.get(tempid);
/*  343 */             for (int w = 0; w < monitors.size(); w++)
/*      */             {
/*  345 */               mosinOrder.add(monitors.get(w));
/*      */             }
/*      */           }
/*      */           
/*  349 */           if ((mosinOrder != null) && (mosinOrder.size() > 0))
/*      */           {
/*  351 */             childlist.put(tempid, mosinOrder);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  358 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  361 */       if (request.getParameter("retaintree") != null)
/*      */       {
/*  363 */         retaintree = request.getParameter("retaintree");
/*      */       }
/*      */       
/*  366 */       removeUnwantedMGs(listofGroups);
/*      */       
/*  368 */       request.setAttribute("applications", listofGroups);
/*  369 */       request.setAttribute("childlist", childlist);
/*  370 */       request.setAttribute("defaultview", "showMonitorGroupView");
/*  371 */       request.setAttribute("retaintree", retaintree);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  376 */       ex.printStackTrace();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  386 */     String excludeNWDCondn = " INNER JOIN AM_ManagedResourceType ON AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP not in ('NWD','SAN') ";
/*  387 */     if ((taskid != null) && (!taskid.equalsIgnoreCase("null")))
/*      */     {
/*  389 */       AMLog.debug("DTSA taskid? " + taskid + "\n DTSA excludeNWDCondn? " + excludeNWDCondn + "\n DTSA commaseparatedresids:::: " + commaSeperatedResIds);
/*  390 */       String toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE, AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_ManagedObject.TYPE in " + this.types + " and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  391 */       String toconfigureg = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskiddd from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + " where AM_ManagedObject.TYPE ='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  392 */       if (privalagedUser)
/*      */       {
/*  394 */         if (isUserResourceEnabled) {
/*  395 */           toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE, AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_USERRESOURCESTABLE, AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.TYPE!='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  396 */           toconfigureg = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskiddd from AM_USERRESOURCESTABLE,AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + " where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.TYPE ='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*      */         } else {
/*  398 */           toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.TYPE, AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_ManagedObject.RESOURCEID in (" + commaSeperatedResIds + ") and AM_ManagedObject.TYPE!='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  399 */           toconfigureg = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskiddd from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + " where AM_ManagedObject.TYPE ='HAI' and AM_ManagedObject.RESOURCEID in (" + commaSeperatedResIds + ") and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*      */         }
/*      */       }
/*      */       
/*  403 */       String configured = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.TYPE, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject, AM_TASKIDRESOURCEIDMAPPER where AM_ManagedObject.TYPE in " + this.types + " and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + " and AM_ManagedObject.RESOURCEID=AM_TASKIDRESOURCEIDMAPPER.RESOURCEID order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  404 */       String configuredg = "select AM_ManagedObject.RESOURCEID, AM_ManagedObject.DISPLAYNAME from AM_ManagedObject, AM_TASKIDRESOURCEIDMAPPER where AM_ManagedObject.RESOURCEID=AM_TASKIDRESOURCEIDMAPPER.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + " and AM_ManagedObject.TYPE ='HAI' order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  405 */       PreparedStatement ps = null;
/*      */       try
/*      */       {
/*  408 */         ps = AMConnectionPool.getPreparedStatement("select AM_MAINTENANCECONFIG.TASKID, AM_MAINTENANCECONFIG.TASKNAME, AM_MAINTENANCECONFIG.TASKDESCRIPTION, CASE WHEN AM_MAINTENANCECONFIG.STATUS = '1'  THEN 'enable' ELSE 'disable' END as STATUS, (case when AM_MAINTENANCECONFIG.TYPE='1' then 'daily' when AM_MAINTENANCECONFIG.TYPE='2' then 'weekly' else 'custom' end) as TYPE, AM_DAILYMAINTENANCE.STARTTIME, AM_DAILYMAINTENANCE.ENDTIME, AM_DAILYMAINTENANCE.EFFECTFROMTIME, AM_WEEKLYMAINTENANCE.STARTDAY, AM_WEEKLYMAINTENANCE.STARTTIME, AM_WEEKLYMAINTENANCE.ENDDAY, AM_WEEKLYMAINTENANCE.ENDTIME, AM_CUSTOMMAINTENANCE.STARTTIME, AM_CUSTOMMAINTENANCE.ENDTIME,AM_MAINTENANCECONFIG.TIMEZONE from AM_MAINTENANCECONFIG left outer join AM_DAILYMAINTENANCE on AM_DAILYMAINTENANCE.TASKID = AM_MAINTENANCECONFIG.TASKID left outer join AM_WEEKLYMAINTENANCE on AM_WEEKLYMAINTENANCE.TASKID = AM_MAINTENANCECONFIG.TASKID left outer join AM_CUSTOMMAINTENANCE on AM_CUSTOMMAINTENANCE.TASKID = AM_MAINTENANCECONFIG.TASKID where AM_MAINTENANCECONFIG.TASKID=?");
/*  409 */         ps.setInt(1, Integer.parseInt(taskid));
/*  410 */         rs = ps.executeQuery();
/*  411 */         if (!rs.next())
/*      */         {
/*  413 */           request.setAttribute("tabtoselect", "4");
/*  414 */           request.setAttribute("helpkey", "maintenanceTaskListView");
/*  415 */           return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */         }
/*      */         do
/*      */         {
/*  419 */           amform.setTaskName(rs.getString("TASKNAME"));
/*  420 */           amform.setTaskDescription(rs.getString("TASKDESCRIPTION"));
/*  421 */           amform.setTaskStatus(rs.getString("STATUS"));
/*  422 */           amform.setTaskMethod(rs.getString("TYPE"));
/*  423 */           amform.setTimezone(rs.getString("TIMEZONE"));
/*  424 */           if (rs.getString("TYPE").equals("daily"))
/*      */           {
/*  426 */             amform.setTaskStartTime(rs.getTime(6).toString().substring(0, rs.getTime(6).toString().lastIndexOf(":")));
/*  427 */             amform.setTaskEndTime(rs.getTime(7).toString().substring(0, rs.getTime(7).toString().lastIndexOf(":")));
/*  428 */             amform.setTaskEffectFrom(rs.getTimestamp(8).toString().substring(0, rs.getTimestamp(8).toString().lastIndexOf(":")));
/*      */           }
/*  430 */           else if (rs.getString("TYPE").equals("weekly"))
/*      */           {
/*  432 */             amform.setStartDay(count, h.getProperty(rs.getString(9)));
/*  433 */             amform.setStartTime(count, rs.getTime(10).toString().substring(0, rs.getTime(10).toString().lastIndexOf(":")));
/*  434 */             amform.setEndDay(count, h.getProperty(rs.getString(11)));
/*  435 */             amform.setEndTime(count, rs.getTime(12).toString().substring(0, rs.getTime(12).toString().lastIndexOf(":")));
/*  436 */             count = Integer.parseInt(count) + 1 + "";
/*  437 */             amform.setTaskCount(count);
/*      */           }
/*      */           else
/*      */           {
/*  441 */             amform.setCustomTaskStartTime(rs.getTimestamp(13).toString().substring(0, rs.getTimestamp(13).toString().lastIndexOf(":")));
/*  442 */             amform.setCustomTaskEndTime(rs.getTimestamp(14).toString().substring(0, rs.getTimestamp(14).toString().lastIndexOf(":")));
/*      */           }
/*  444 */         } while (rs.next());
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  448 */         e.printStackTrace();
/*  449 */         rs = null;
/*  450 */         request.setAttribute("tabtoselect", "4");
/*  451 */         request.setAttribute("helpkey", "maintenanceTaskListView");
/*  452 */         return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */       }
/*      */       finally
/*      */       {
/*  456 */         AMConnectionPool.closeResultSet(rs);
/*  457 */         AMConnectionPool.closePreparedStatement(ps);
/*      */       }
/*      */       try
/*      */       {
/*  461 */         rs = AMConnectionPool.executeQueryStmt(toconfigure);
/*  462 */         while (rs.next())
/*      */         {
/*  464 */           Properties p = new Properties();
/*  465 */           String dispname = rs.getString("DISPLAYNAME");
/*  466 */           dispname = EnterpriseUtil.decodeString(dispname);
/*  467 */           String type = rs.getString("TYPE");
/*  468 */           if ((!type.equals("Process")) && (!type.equals("Service")))
/*      */           {
/*      */ 
/*      */ 
/*  472 */             if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */             {
/*  474 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */             }
/*  476 */             p.setProperty("label", dispname);
/*  477 */             p.setProperty("value", rs.getString("RESOURCEID"));
/*  478 */             rows.add(p);
/*      */           } }
/*  480 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  484 */         e.printStackTrace();
/*  485 */         rs = null;
/*      */       }
/*      */       
/*  488 */       if (doEnableProcessMonsInDS.equalsIgnoreCase("true"))
/*      */       {
/*  490 */         Collection<Properties> allProcessMonitors = getAvailableProcessAndServiceMonitors(privalagedUser, commaSeperatedResIds, taskid, request);
/*  491 */         if (allProcessMonitors != null)
/*      */         {
/*  493 */           rows.addAll(allProcessMonitors);
/*      */         }
/*      */       }
/*  496 */       amform.setToAdd(rows);
/*      */       try
/*      */       {
/*  499 */         rs = AMConnectionPool.executeQueryStmt(toconfigureg);
/*  500 */         while (rs.next())
/*      */         {
/*  502 */           Properties p = new Properties();
/*  503 */           String dispname = rs.getString("DISPLAYNAME");
/*  504 */           dispname = EnterpriseUtil.decodeString(dispname);
/*  505 */           if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */           {
/*  507 */             dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */           }
/*  509 */           p.setProperty("label", dispname);
/*  510 */           p.setProperty("value", rs.getString("RESOURCEID"));
/*  511 */           rowsg.add(p);
/*      */         }
/*  513 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  517 */         e.printStackTrace();
/*  518 */         rs = null;
/*      */       }
/*  520 */       amform.setToAddg(rowsg);
/*  521 */       rows = new ArrayList();
/*      */       try
/*      */       {
/*  524 */         rs = AMConnectionPool.executeQueryStmt(configured);
/*  525 */         String processResIds = "";
/*  526 */         while (rs.next())
/*      */         {
/*  528 */           Properties p = new Properties();
/*  529 */           String dispname = rs.getString("DISPLAYNAME");
/*  530 */           dispname = EnterpriseUtil.decodeString(dispname);
/*  531 */           String type = rs.getString("TYPE");
/*  532 */           if ((type.equals("Process")) || (type.equals("Service")))
/*      */           {
/*  534 */             processResIds = processResIds + rs.getString("RESOURCEID") + ",";
/*      */           }
/*      */           else {
/*  537 */             if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */             {
/*  539 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */             }
/*  541 */             p.setProperty("label", dispname);
/*  542 */             p.setProperty("value", rs.getString("RESOURCEID"));
/*  543 */             rows.add(p);
/*  544 */             amform.setTaskGroup("allmonitors");
/*      */           } }
/*  546 */         if (!processResIds.equals(""))
/*      */         {
/*  548 */           processResIds = processResIds.substring(0, processResIds.length() - 1);
/*  549 */           String processMonQry = "select  ammo1.DISPLAYNAME as PARENTNAME, ammo2.DISPLAYNAME as CHILDNAME,  parchildmap.CHILDID from AM_PARENTCHILDMAPPER parchildmap  LEFT JOIN AM_ManagedObject ammo1 on ammo1.RESOURCEID=parchildmap.PARENTID RIGHT JOIN AM_ManagedObject ammo2 on ammo2.RESOURCEID=parchildmap.CHILDID where ammo2.RESOURCEID in (" + processResIds + ")  AND ammo1.TYPE NOT IN ('HAI')";
/*  550 */           AMLog.debug("DTSA processMonQry? " + processMonQry);
/*  551 */           rs = AMConnectionPool.executeQueryStmt(processMonQry);
/*  552 */           while (rs.next())
/*      */           {
/*  554 */             Properties p = new Properties();
/*  555 */             String dispname = rs.getString("PARENTNAME") + "_" + rs.getString("CHILDNAME");
/*  556 */             if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("CHILDID") > EnterpriseUtil.RANGE))
/*      */             {
/*  558 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("CHILDID"));
/*      */             }
/*  560 */             p.setProperty("label", dispname);
/*  561 */             p.setProperty("value", rs.getString("CHILDID"));
/*  562 */             rows.add(p);
/*      */           }
/*      */         }
/*  565 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  569 */         e.printStackTrace();
/*  570 */         rs = null;
/*      */       }
/*  572 */       amform.setPresent(rows);
/*  573 */       rowsg = new ArrayList();
/*      */       try
/*      */       {
/*  576 */         rs = AMConnectionPool.executeQueryStmt(configuredg);
/*  577 */         while (rs.next())
/*      */         {
/*  579 */           Properties p = new Properties();
/*  580 */           String dispname = rs.getString("DISPLAYNAME");
/*  581 */           dispname = EnterpriseUtil.decodeString(dispname);
/*  582 */           if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */           {
/*  584 */             dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */           }
/*  586 */           p.setProperty("label", dispname);
/*  587 */           p.setProperty("value", rs.getString("RESOURCEID"));
/*  588 */           rowsg.add(p);
/*  589 */           amform.setTaskGroup("allgroups");
/*      */         }
/*  591 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  595 */         e.printStackTrace();
/*  596 */         rs = null;
/*      */       }
/*  598 */       amform.setPresentg(rowsg);
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  603 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
/*  604 */       Date logDate = new Date(System.currentTimeMillis());
/*  605 */       sdf.format(logDate);
/*  606 */       amform.setCustomTaskStartTime(sdf.format(logDate));
/*  607 */       amform.setCustomTaskEndTime(sdf.format(logDate));
/*  608 */       String allMonitors = "";
/*      */       
/*      */ 
/*  611 */       allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE from AM_ManagedObject " + excludeNWDCondn + "where AM_ManagedObject.TYPE in " + this.types + " order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*  612 */       if (privalagedUser)
/*      */       {
/*  614 */         if (isUserResourceEnabled) {
/*  615 */           allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE from AM_USERRESOURCESTABLE, AM_ManagedObject " + excludeNWDCondn + " where AM_ManagedObject.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.TYPE!='HAI'";
/*      */         } else {
/*  617 */           allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE from AM_ManagedObject " + excludeNWDCondn + "where AM_ManagedObject.RESOURCEID in (" + commaSeperatedResIds + ") and AM_ManagedObject.TYPE!='HAI'";
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/*  622 */         rs = AMConnectionPool.executeQueryStmt(allMonitors);
/*  623 */         while (rs.next())
/*      */         {
/*  625 */           String type = rs.getString("TYPE");
/*  626 */           if ((!type.equals("Process")) && (!type.equals("Service")))
/*      */           {
/*      */ 
/*      */ 
/*  630 */             Properties p = new Properties();
/*  631 */             String dispname = rs.getString("DISPLAYNAME");
/*  632 */             dispname = EnterpriseUtil.decodeString(dispname);
/*  633 */             if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */             {
/*  635 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */             }
/*  637 */             p.setProperty("label", dispname);
/*  638 */             p.setProperty("value", rs.getString("RESOURCEID"));
/*  639 */             rows.add(p);
/*      */           } }
/*  641 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  645 */         e.printStackTrace();
/*  646 */         rs = null;
/*      */       }
/*      */       
/*  649 */       if (doEnableProcessMonsInDS.equalsIgnoreCase("true"))
/*      */       {
/*  651 */         Collection<Properties> allProcessMonitors = getAvailableProcessAndServiceMonitors(privalagedUser, commaSeperatedResIds, null, request);
/*  652 */         if (allProcessMonitors != null)
/*      */         {
/*  654 */           rows.addAll(allProcessMonitors);
/*      */         }
/*      */       }
/*  657 */       amform.setToAdd(rows);
/*      */       
/*      */ 
/*      */ 
/*  661 */       allMonitors = "select RESOURCEID,DISPLAYNAME from AM_HOLISTICAPPLICATION,AM_ManagedObject where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*  662 */       if (privalagedUser)
/*      */       {
/*  664 */         if (isUserResourceEnabled) {
/*  665 */           allMonitors = "select AM_ManagedObject.RESOURCEID,DISPLAYNAME from AM_USERRESOURCESTABLE, AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS where AM_USERRESOURCESTABLE.RESOURCEID=AM_ManagedObject.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID  order by RESOURCENAME";
/*      */         } else {
/*  667 */           allMonitors = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + request.getRemoteUser() + "' order by RESOURCENAME";
/*      */         }
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  673 */         rs = AMConnectionPool.executeQueryStmt(allMonitors);
/*  674 */         while (rs.next())
/*      */         {
/*  676 */           Properties p = new Properties();
/*  677 */           String dispname = rs.getString("DISPLAYNAME");
/*  678 */           dispname = EnterpriseUtil.decodeString(dispname);
/*  679 */           if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */           {
/*  681 */             dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */           }
/*  683 */           p.setProperty("label", dispname);
/*  684 */           p.setProperty("value", rs.getString("RESOURCEID"));
/*  685 */           rowsg.add(p);
/*      */         }
/*      */         
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  691 */         e.printStackTrace();
/*  692 */         rs = null;
/*      */       }
/*      */       finally
/*      */       {
/*  696 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*  698 */       amform.setToAddg(rowsg);
/*      */     }
/*      */     
/*      */ 
/*  702 */     return new ActionForward("/jsp/MaintenanceTask.jsp");
/*      */   }
/*      */   
/*      */   public ArrayList<String> getSchedulesForResourcesOwnedByUser(HttpServletRequest request)
/*      */   {
/*  707 */     ResultSet rs = null;
/*  708 */     Vector<String> resources = DelegatedUserRoleUtil.getResIDsForUser(request);
/*  709 */     String resIds = resources.toString().substring(1, resources.toString().length() - 1);
/*  710 */     String query = "select cast((TASKID) as varchar) from AM_TASKIDRESOURCEIDMAPPER where RESOURCEID IN (" + resIds + ")";
/*  711 */     if (DBQueryUtil.isMysql()) {
/*  712 */       query = "select cast((TASKID) as char) from AM_TASKIDRESOURCEIDMAPPER where RESOURCEID IN (" + resIds + ")";
/*      */     }
/*  714 */     ArrayList<String> taskIds = new ArrayList();
/*      */     try
/*      */     {
/*  717 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  718 */       while (rs.next())
/*      */       {
/*  720 */         taskIds.add(rs.getString(1));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  725 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  729 */       if (rs != null)
/*      */       {
/*  731 */         AMConnectionPool.closeResultSet(rs);
/*      */       }
/*      */     }
/*  734 */     return taskIds;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void removeUnwantedMGs(ArrayList<ArrayList<String>> listofGroups)
/*      */   {
/*  746 */     ArrayList<String> mgIds = new ArrayList();
/*  747 */     ArrayList<String> childIds = new ArrayList();
/*  748 */     ArrayList<ArrayList<String>> mgsToRemove = new ArrayList();
/*      */     
/*      */ 
/*  751 */     for (ArrayList<String> mgInfo : listofGroups)
/*      */     {
/*  753 */       mgIds.add(mgInfo.get(6));
/*      */     }
/*  755 */     for (String mgID : mgIds)
/*      */     {
/*  757 */       allChilds = new Vector();
/*      */       
/*  759 */       DBUtil.getChildIDs(allChilds, mgID, true, true);
/*  760 */       for (ArrayList<String> monitorGroupInfo : listofGroups)
/*      */       {
/*  762 */         String monitorGroupId = (String)monitorGroupInfo.get(6);
/*      */         
/*  764 */         if (allChilds.contains(monitorGroupId))
/*      */         {
/*  766 */           mgsToRemove.add(monitorGroupInfo);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     Vector<String> allChilds;
/*  772 */     for (ArrayList<String> mgToRemove : mgsToRemove)
/*      */     {
/*  774 */       listofGroups.remove(mgToRemove);
/*      */     }
/*      */   }
/*      */   
/*      */   private String convertVectorToString(Vector allMonitorsVector)
/*      */   {
/*  780 */     StringBuffer resids = new StringBuffer();
/*  781 */     for (Object resourceId : allMonitorsVector)
/*      */     {
/*  783 */       resids.append(resourceId).append(",");
/*      */     }
/*  785 */     resids.append("-1");
/*  786 */     return resids.toString();
/*      */   }
/*      */   
/*      */   public ActionForward createMaintenanceTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/*  791 */     ResultSet rs = null;
/*  792 */     AMActionForm amform = (AMActionForm)form;
/*  793 */     Statement stmt = AMConnectionPool.getConnection().createStatement();
/*  794 */     stmt.setQueryTimeout(600);
/*  795 */     String taskname = request.getParameter("taskName");
/*  796 */     String taskdescription = request.getParameter("taskDescription");
/*  797 */     String taskstatus = request.getParameter("taskStatus");
/*  798 */     String taskmethod = request.getParameter("taskMethod");
/*  799 */     String timezone = request.getParameter("timezone");
/*  800 */     int tasktype = 1;
/*  801 */     int status = 1;
/*  802 */     String id = "-1";
/*  803 */     String starttime = "";
/*  804 */     String endtime = "";
/*  805 */     String effectfrom = "";
/*  806 */     String insertquery_type = "";
/*  807 */     String taskID = "";
/*  808 */     boolean name_exist = false;
/*      */     try
/*      */     {
/*  811 */       PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("select * from AM_MAINTENANCECONFIG where TASKNAME=?");
/*      */       try
/*      */       {
/*  814 */         ps.setString(1, taskname);
/*  815 */         rs = ps.executeQuery();
/*  816 */         if (rs.next())
/*      */         {
/*  818 */           name_exist = true;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  827 */         AMConnectionPool.closeResultSet(rs);
/*      */         try
/*      */         {
/*  830 */           if (ps != null)
/*      */           {
/*  832 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  837 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       catch (Exception exp)
/*      */       {
/*  823 */         exp.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  827 */         AMConnectionPool.closeResultSet(rs);
/*      */         try
/*      */         {
/*  830 */           if (ps != null)
/*      */           {
/*  832 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  837 */           ex.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  857 */       if (!name_exist) {
/*      */         break label404;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  843 */       e.printStackTrace();
/*  844 */       rs = null;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  850 */         stmt.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  854 */         stmt = null;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  859 */     ActionMessages messages = new ActionMessages();
/*  860 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.error", "The given task name " + taskname + " already exist."));
/*  861 */     saveMessages(request, messages);
/*  862 */     return new ActionForward("/downTimeScheduler.do?method=viewMaintenanceTask");
/*      */     
/*      */     label404:
/*      */     
/*  866 */     if (taskstatus.equalsIgnoreCase("disable"))
/*      */     {
/*  868 */       status = 0;
/*      */     }
/*  870 */     if (taskmethod.equalsIgnoreCase("daily"))
/*      */     {
/*  872 */       tasktype = 1;
/*      */     }
/*  874 */     else if (taskmethod.equalsIgnoreCase("weekly"))
/*      */     {
/*  876 */       tasktype = 2;
/*      */     }
/*      */     else
/*      */     {
/*  880 */       tasktype = 3;
/*      */     }
/*      */     
/*      */ 
/*  884 */     String displayoption = amform.getTaskGroup();
/*  885 */     String[] resIDs = null;
/*  886 */     if (displayoption.equals("allmonitors"))
/*      */     {
/*  888 */       resIDs = request.getParameterValues("maintenanceCombo2");
/*      */     }
/*      */     
/*  891 */     if (displayoption.equals("allgroups"))
/*      */     {
/*  893 */       resIDs = request.getParameterValues("select");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  900 */       if (tasktype == 1)
/*      */       {
/*  902 */         starttime = request.getParameter("taskStartTime");
/*  903 */         endtime = request.getParameter("taskEndTime");
/*  904 */         effectfrom = request.getParameter("taskEffectFrom") + ":00";
/*  905 */         taskID = DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, status, tasktype, starttime, endtime, effectfrom, null, resIDs, null, timezone);
/*      */       }
/*  907 */       else if (tasktype == 2)
/*      */       {
/*  909 */         Object weeklyDetList = new ArrayList();
/*  910 */         int count = 1;
/*      */         try
/*      */         {
/*  913 */           count = Integer.parseInt(request.getParameter("numbers"));
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  917 */           e.printStackTrace();
/*      */         }
/*  919 */         insertquery_type = " ";
/*  920 */         for (int i = 0; i < count; i++)
/*      */         {
/*  922 */           String[] monthNames = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
/*  923 */           String startDay = request.getParameter("startDay(" + i + ")");
/*  924 */           String startTime = request.getParameter("startTime(" + i + ")");
/*  925 */           String endDay = request.getParameter("endDay(" + i + ")");
/*  926 */           String endTime = request.getParameter("endTime(" + i + ")");
/*  927 */           startDay = monthNames[(Integer.parseInt(startDay) - 1)];
/*  928 */           endDay = monthNames[(Integer.parseInt(endDay) - 1)];
/*  929 */           Hashtable<String, String> weeklyDetHash = new Hashtable();
/*  930 */           weeklyDetHash.put("STARTDAY", startDay);
/*  931 */           weeklyDetHash.put("STARTTIME", startTime);
/*  932 */           weeklyDetHash.put("ENDDAY", endDay);
/*  933 */           weeklyDetHash.put("ENDTIME", endTime);
/*  934 */           ((ArrayList)weeklyDetList).add(weeklyDetHash);
/*      */         }
/*      */         
/*  937 */         taskID = DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, status, tasktype, null, null, null, (ArrayList)weeklyDetList, resIDs, null, timezone);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  942 */         starttime = request.getParameter("customTaskStartTime") + ":00";
/*  943 */         endtime = request.getParameter("customTaskEndTime") + ":00";
/*  944 */         taskID = DowntimeScheduleUtil.createDownTimeScheduler(taskname, taskdescription, status, tasktype, starttime, endtime, null, null, resIDs, null, timezone);
/*      */       }
/*  946 */       int taskId = Integer.parseInt(taskID);
/*  947 */       int userId = DelegatedUserRoleUtil.getLoginUserid(request);
/*  948 */       if (userId == -1)
/*      */       {
/*  950 */         String username = request.getParameter("userId");
/*  951 */         userId = Integer.parseInt(DBUtil.getUserID(username));
/*      */       }
/*  953 */       DelegatedUserRoleUtil.addEntryToConfigUserTable(taskId, userId, -1, 5);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  957 */       e.printStackTrace();
/*      */     }
/*      */     try
/*      */     {
/*  961 */       DataCollectionControllerUtil.updateMaintenanceTable();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  965 */       e.printStackTrace();
/*      */     }
/*  967 */     if (EnterpriseUtil.isAdminServer())
/*      */     {
/*      */       try
/*      */       {
/*  971 */         DowntimeScheduleUtil.handleMASSYNCHDETAILS(taskID, "create", null);
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/*  975 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*  978 */     if (request.isUserInRole("OPERATOR"))
/*      */     {
/*      */       try
/*      */       {
/*  982 */         Properties userInfo = DBUtil.getUserInfo(request.getRemoteUser());
/*  983 */         PreparedStatement ps = AMConnectionPool.getPreparedStatement("Insert into AM_MAINTENANCETASK_USER_MAPPING values (?,?)");
/*  984 */         ps.setInt(1, Integer.valueOf(userInfo.getProperty("USERID")).intValue());
/*  985 */         ps.setInt(2, Integer.valueOf(taskID).intValue());
/*  986 */         ps.execute();
/*      */       }
/*      */       catch (SQLException e)
/*      */       {
/*  990 */         if ((e.getMessage() == null) || (!e.getMessage().contains("Duplicate")))
/*      */         {
/*  992 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  997 */     return new ActionForward("/downTimeScheduler.do?method=maintenanceTaskListView&tabtoLoad=downtimeSchedulersDiv", true);
/*      */   }
/*      */   
/*      */   public ActionForward editMaintenanceTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1002 */     ResultSet rs = null;
/* 1003 */     AMActionForm amform = (AMActionForm)form;
/* 1004 */     String taskid = request.getParameter("taskid");
/* 1005 */     String taskname = request.getParameter("taskName");
/* 1006 */     String taskdescription = request.getParameter("taskDescription");
/* 1007 */     String taskstatus = request.getParameter("taskStatus");
/* 1008 */     String taskmethod = request.getParameter("taskMethod");
/* 1009 */     String timezone = request.getParameter("timezone");
/* 1010 */     int tasktype = 1;
/* 1011 */     int status = 1;
/* 1012 */     String id = "-1";
/* 1013 */     String starttime = "";
/* 1014 */     String endtime = "";
/* 1015 */     String effectfrom = "";
/* 1016 */     String insertquery_type = "";
/*      */     
/* 1018 */     String previous_taskname = "";
/* 1019 */     String previous_taskdescription = "";
/* 1020 */     int previous_taskstatus = 1;
/* 1021 */     int previous_tasktype = 1;
/*      */     
/*      */ 
/* 1024 */     if (taskstatus.equalsIgnoreCase("disable"))
/*      */     {
/* 1026 */       status = 0;
/*      */     }
/* 1028 */     if (taskmethod.equalsIgnoreCase("daily"))
/*      */     {
/* 1030 */       tasktype = 1;
/*      */     }
/* 1032 */     else if (taskmethod.equalsIgnoreCase("weekly"))
/*      */     {
/* 1034 */       tasktype = 2;
/*      */     }
/*      */     else
/*      */     {
/* 1038 */       tasktype = 3;
/*      */     }
/*      */     
/*      */ 
/* 1042 */     PreparedStatement ps_query = AMConnectionPool.getConnection().prepareStatement("select TASKNAME, TASKDESCRIPTION, STATUS, TYPE from AM_MAINTENANCECONFIG where TASKID=?");
/*      */     
/*      */     try
/*      */     {
/* 1046 */       if ((taskid != null) && (!taskid.equalsIgnoreCase("null")))
/*      */       {
/* 1048 */         ps_query.setInt(1, Integer.parseInt(taskid));
/* 1049 */         rs = ps_query.executeQuery();
/* 1050 */         if (!rs.next())
/*      */         {
/* 1052 */           request.setAttribute("tabtoselect", "4");
/* 1053 */           request.setAttribute("helpkey", "maintenanceTaskListView");
/* 1054 */           return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */         }
/* 1056 */         previous_taskname = rs.getString("TASKNAME");
/* 1057 */         previous_taskdescription = rs.getString("TASKDESCRIPTION");
/* 1058 */         previous_taskstatus = rs.getInt("STATUS");
/* 1059 */         previous_tasktype = rs.getInt("TYPE");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1064 */       e.printStackTrace();
/* 1065 */       request.setAttribute("tabtoselect", "4");
/* 1066 */       request.setAttribute("helpkey", "maintenanceTaskListView");
/* 1067 */       return new ActionForward("/jsp/formpages/AccessRestricted.jsp");
/*      */     }
/*      */     finally
/*      */     {
/* 1071 */       AMConnectionPool.closeResultSet(rs);
/* 1072 */       AMConnectionPool.closePreparedStatement(ps_query);
/*      */     }
/*      */     
/*      */ 
/* 1076 */     if (!taskname.equals(previous_taskname))
/*      */     {
/* 1078 */       boolean name_exist = false;
/* 1079 */       PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("select * from AM_MAINTENANCECONFIG where TASKNAME=?");
/*      */       try
/*      */       {
/* 1082 */         ps.setString(1, taskname);
/* 1083 */         rs = ps.executeQuery();
/* 1084 */         if (rs.next())
/*      */         {
/* 1086 */           name_exist = true;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1096 */         AMConnectionPool.closeResultSet(rs);
/*      */         try
/*      */         {
/* 1099 */           if (ps != null)
/*      */           {
/* 1101 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1106 */           ex.printStackTrace();
/*      */         }
/*      */         
/* 1109 */         if (!name_exist) {
/*      */           break label642;
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1092 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1096 */         AMConnectionPool.closeResultSet(rs);
/*      */         try
/*      */         {
/* 1099 */           if (ps != null)
/*      */           {
/* 1101 */             ps.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 1106 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1111 */       ActionMessages messages = new ActionMessages();
/* 1112 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("appmanager.error", "The given task name " + taskname + " already exist."));
/* 1113 */       saveMessages(request, messages);
/* 1114 */       return new ActionForward("/downTimeScheduler.do?method=viewMaintenanceTask&edit=true&taskid=" + taskid);
/*      */     }
/*      */     
/*      */     label642:
/*      */     
/* 1119 */     String displayoption = amform.getTaskGroup();
/* 1120 */     String[] resIDs = null;
/* 1121 */     if (displayoption.equals("allmonitors"))
/*      */     {
/* 1123 */       resIDs = request.getParameterValues("maintenanceCombo2");
/*      */     }
/*      */     
/* 1126 */     if (displayoption.equals("allgroups"))
/*      */     {
/* 1128 */       resIDs = request.getParameterValues("select");
/*      */     }
/*      */     
/* 1131 */     if (tasktype == 1)
/*      */     {
/* 1133 */       starttime = request.getParameter("taskStartTime");
/* 1134 */       endtime = request.getParameter("taskEndTime");
/* 1135 */       effectfrom = request.getParameter("taskEffectFrom") + ":00";
/* 1136 */       DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, status, tasktype, starttime, endtime, effectfrom, null, resIDs, timezone);
/*      */     }
/* 1138 */     else if (tasktype == 2)
/*      */     {
/* 1140 */       Object weeklyDetList = new ArrayList();
/* 1141 */       int count = 1;
/*      */       try
/*      */       {
/* 1144 */         count = Integer.parseInt(request.getParameter("numbers"));
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1148 */         e.printStackTrace();
/*      */       }
/* 1150 */       insertquery_type = " ";
/* 1151 */       for (int i = 0; i < count; i++)
/*      */       {
/* 1153 */         String[] monthNames = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
/* 1154 */         String startDay = request.getParameter("startDay(" + i + ")");
/* 1155 */         String startTime = request.getParameter("startTime(" + i + ")");
/* 1156 */         String endDay = request.getParameter("endDay(" + i + ")");
/* 1157 */         String endTime = request.getParameter("endTime(" + i + ")");
/* 1158 */         startDay = monthNames[(Integer.parseInt(startDay) - 1)];
/* 1159 */         endDay = monthNames[(Integer.parseInt(endDay) - 1)];
/* 1160 */         Hashtable<String, String> weeklyDetHash = new Hashtable();
/* 1161 */         weeklyDetHash.put("STARTDAY", startDay);
/* 1162 */         weeklyDetHash.put("STARTTIME", startTime);
/* 1163 */         weeklyDetHash.put("ENDDAY", endDay);
/* 1164 */         weeklyDetHash.put("ENDTIME", endTime);
/* 1165 */         ((ArrayList)weeklyDetList).add(weeklyDetHash);
/*      */       }
/* 1167 */       DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, status, tasktype, null, null, null, (ArrayList)weeklyDetList, resIDs, timezone);
/*      */     }
/*      */     else
/*      */     {
/* 1171 */       starttime = request.getParameter("customTaskStartTime") + ":00";
/* 1172 */       endtime = request.getParameter("customTaskEndTime") + ":00";
/* 1173 */       DowntimeScheduleUtil.editDownTimeScheduler(taskid, taskname, taskdescription, status, tasktype, starttime, endtime, null, null, resIDs, timezone);
/*      */     }
/* 1175 */     int userId = DelegatedUserRoleUtil.getLoginUserid(request);
/* 1176 */     if (userId == -1)
/*      */     {
/* 1178 */       String username = request.getParameter("userId");
/* 1179 */       userId = Integer.parseInt(DBUtil.getUserID(username));
/*      */     }
/* 1181 */     DelegatedUserRoleUtil.updateEntryToConfigUserTable(userId, -1, Integer.parseInt(taskid), 5);
/*      */     try
/*      */     {
/* 1184 */       DataCollectionControllerUtil.updateMaintenanceTable();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1188 */       e.printStackTrace();
/*      */     }
/* 1190 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try
/*      */       {
/* 1193 */         DowntimeScheduleUtil.handleMASSYNCHDETAILS(taskid, "edit", null);
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 1197 */         ee.printStackTrace();
/*      */       }
/*      */     }
/* 1200 */     return new ActionForward("/downTimeScheduler.do?method=maintenanceTaskListView&tabtoLoad=downtimeSchedulersDiv", true);
/*      */   }
/*      */   
/*      */   public ActionForward deleteMaintenanceTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1205 */     String[] id = request.getParameterValues("checkbox");
/* 1206 */     DowntimeScheduleUtil.deleteDownTimeScheduler(id, true);
/* 1207 */     int configId = -1;
/* 1208 */     for (int i = 0; i < id.length; i++)
/*      */     {
/* 1210 */       configId = Integer.parseInt(id[i]);
/* 1211 */       DelegatedUserRoleUtil.deleteEntryFromConfigUserTable(configId, 5);
/*      */     }
/*      */     try
/*      */     {
/* 1215 */       DataCollectionControllerUtil.updateMaintenanceTable();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1219 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1222 */     if (EnterpriseUtil.isAdminServer()) {
/*      */       try {
/* 1224 */         DowntimeScheduleUtil.handleMASSYNCHDETAILS(DowntimeScheduleUtil.getCommaSeperated(id), "delete", null);
/*      */       }
/*      */       catch (Exception ee)
/*      */       {
/* 1228 */         ee.printStackTrace();
/*      */       }
/*      */       
/* 1231 */       Statement todelete = null;
/*      */       try
/*      */       {
/* 1234 */         todelete = AMConnectionPool.getConnection().createStatement();
/* 1235 */         for (String taskId : id)
/*      */         {
/* 1237 */           todelete.addBatch("delete from AM_MAINTENANCETASK_USER_MAPPING where TASKID=" + taskId);
/*      */         }
/* 1239 */         todelete.executeBatch();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1243 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 1247 */         if (todelete != null)
/*      */         {
/* 1249 */           todelete.close();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1254 */     return new ActionForward("/downTimeScheduler.do?method=maintenanceTaskListView&tabtoLoad=downtimeSchedulersDiv", true);
/*      */   }
/*      */   
/*      */   public Collection<Properties> getAvailableProcessAndServiceMonitors(boolean isOperator, String commaSeperatedResIds, String taskid)
/*      */   {
/* 1259 */     return getAvailableProcessAndServiceMonitors(isOperator, commaSeperatedResIds, taskid, null);
/*      */   }
/*      */   
/*      */   public Collection<Properties> getAvailableProcessAndServiceMonitors(boolean isOperator, String commaSeperatedResIds, String taskid, HttpServletRequest request)
/*      */   {
/* 1264 */     Collection<Properties> rows = null;
/* 1265 */     ResultSet rs = null;
/* 1266 */     String tempQry = this.allProcessAndServiceMonsQry;
/* 1267 */     if (isOperator)
/*      */     {
/* 1269 */       if (Constants.isUserResourceEnabled()) {
/* 1270 */         String loginUserid = Constants.getLoginUserid(request);
/* 1271 */         tempQry = tempQry + " and ammo1.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*      */       } else {
/* 1273 */         tempQry = tempQry + " and ammo1.RESOURCEID in (" + commaSeperatedResIds + ")";
/*      */       }
/*      */     }
/* 1276 */     if ((taskid != null) && (!taskid.equalsIgnoreCase("null")))
/*      */     {
/* 1278 */       tempQry = tempQry + "  and parchildmap.CHILDID not in (select RESOURCEID from AM_TASKIDRESOURCEIDMAPPER where taskid=" + taskid + ")";
/*      */     }
/*      */     try
/*      */     {
/* 1282 */       rs = AMConnectionPool.executeQueryStmt(tempQry);
/* 1283 */       if (rs != null)
/*      */       {
/* 1285 */         rows = new ArrayList();
/* 1286 */         while (rs.next())
/*      */         {
/* 1288 */           Properties p = new Properties();
/* 1289 */           String dispname = rs.getString("PARENTNAME") + "_" + rs.getString("CHILDNAME");
/* 1290 */           if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("CHILDID") > EnterpriseUtil.RANGE))
/*      */           {
/* 1292 */             dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("CHILDID"));
/*      */           }
/* 1294 */           p.setProperty("label", dispname);
/* 1295 */           p.setProperty("value", rs.getString("CHILDID"));
/* 1296 */           rows.add(p);
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1303 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1307 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1309 */     return rows;
/*      */   }
/*      */   
/*      */   public Hashtable getAllMonitors(HttpServletRequest request, boolean isIncludeProcessMons, String taskid) throws Exception
/*      */   {
/* 1314 */     Hashtable ht = new Hashtable();
/* 1315 */     ResultSet rs = null;
/* 1316 */     String commaSeperatedResIds = null;
/* 1317 */     boolean isUserResourceEnabled = false;
/* 1318 */     String loginUserid = null;
/* 1319 */     boolean isOperator = false;
/* 1320 */     if (Constants.isPrivilegedUser(request))
/*      */     {
/* 1322 */       isOperator = true;
/* 1323 */       if (Constants.isUserResourceEnabled()) {
/* 1324 */         isUserResourceEnabled = true;
/* 1325 */         loginUserid = Constants.getLoginUserid(request);
/*      */       } else {
/* 1327 */         commaSeperatedResIds = convertVectorToString(ClientDBUtil.getResourceIdentity(request.getRemoteUser()));
/*      */       }
/*      */     }
/*      */     
/* 1331 */     String excludeNWDCondn = " INNER JOIN AM_ManagedResourceType ON AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP not in ('NWD','SAN') ";
/* 1332 */     String allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject " + excludeNWDCondn + "where AM_ManagedObject.TYPE in " + this.types + " order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/* 1333 */     String allProcessMonitors = this.allProcessAndServiceMonsQry;
/*      */     
/* 1335 */     if (isOperator)
/*      */     {
/* 1337 */       if (isUserResourceEnabled) {
/* 1338 */         allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME from AM_USERRESOURCESTABLE, AM_ManagedObject " + excludeNWDCondn + " where AM_ManagedObject.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.TYPE!='HAI'";
/* 1339 */         allProcessMonitors = allProcessMonitors + " and ammo1.RESOURCEID in (select RESOURCEID from AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*      */       } else {
/* 1341 */         allMonitors = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME from AM_ManagedObject " + excludeNWDCondn + "where AM_ManagedObject.RESOURCEID in (" + commaSeperatedResIds + ") and AM_ManagedObject.TYPE!='HAI'";
/* 1342 */         allProcessMonitors = allProcessMonitors + " and ammo1.RESOURCEID in (" + commaSeperatedResIds + ")";
/*      */       }
/*      */     }
/* 1345 */     if ((taskid != null) && (!taskid.equalsIgnoreCase("null")))
/*      */     {
/* 1347 */       String toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_ManagedObject.TYPE in " + this.types + " and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/* 1348 */       if (isOperator)
/*      */       {
/* 1350 */         if (isUserResourceEnabled) {
/* 1351 */           toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_USERRESOURCESTABLE, AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_ManagedObject.RESOURCEID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and AM_ManagedObject.TYPE!='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*      */         } else {
/* 1353 */           toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_TASKIDRESOURCEIDMAPPER.TASKID as taskid from AM_ManagedObject left outer join AM_TASKIDRESOURCEIDMAPPER on AM_TASKIDRESOURCEIDMAPPER.RESOURCEID = AM_ManagedObject.RESOURCEID and AM_TASKIDRESOURCEIDMAPPER.TASKID=" + taskid + excludeNWDCondn + " where AM_ManagedObject.RESOURCEID in (" + commaSeperatedResIds + ") and AM_ManagedObject.TYPE!='HAI' and AM_TASKIDRESOURCEIDMAPPER.TASKID is null order by AM_ManagedObject.DISPLAYNAME, AM_ManagedObject.TYPE";
/*      */         }
/*      */       }
/* 1356 */       allMonitors = toconfigure;
/* 1357 */       allProcessMonitors = allProcessMonitors + "  and parchildmap.CHILDID not in (select RESOURCEID from AM_TASKIDRESOURCEIDMAPPER where taskid=" + taskid + ")";
/*      */     }
/*      */     try
/*      */     {
/* 1361 */       Vector v = new Vector();
/* 1362 */       rs = AMConnectionPool.executeQueryStmt("select RESOURCEID  from AM_TASKIDRESOURCEIDMAPPER where TASKID=" + taskid);
/* 1363 */       while (rs.next())
/*      */       {
/* 1365 */         v.add(rs.getString("RESOURCEID"));
/*      */       }
/* 1367 */       rs = null;
/*      */       
/* 1369 */       rs = AMConnectionPool.executeQueryStmt(allMonitors);
/* 1370 */       while (rs.next())
/*      */       {
/* 1372 */         Properties p = new Properties();
/* 1373 */         String dispname = rs.getString("DISPLAYNAME");
/* 1374 */         dispname = EnterpriseUtil.decodeString(dispname);
/* 1375 */         if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("RESOURCEID") > EnterpriseUtil.RANGE))
/*      */         {
/* 1377 */           dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("RESOURCEID"));
/*      */         }
/* 1379 */         if (!v.contains(rs.getString("RESOURCEID")))
/*      */         {
/* 1381 */           ht.put(rs.getString("RESOURCEID"), dispname);
/*      */         }
/*      */       }
/*      */       
/* 1385 */       rs = null;
/* 1386 */       if (isIncludeProcessMons)
/*      */       {
/* 1388 */         rs = AMConnectionPool.executeQueryStmt(allProcessMonitors);
/* 1389 */         if (rs != null)
/*      */         {
/* 1391 */           while (rs.next())
/*      */           {
/* 1393 */             Properties p = new Properties();
/* 1394 */             String dispname = rs.getString("PARENTNAME") + "_" + rs.getString("CHILDNAME");
/* 1395 */             if ((EnterpriseUtil.isAdminServer()) && (rs.getInt("CHILDID") > EnterpriseUtil.RANGE))
/*      */             {
/* 1397 */               dispname = dispname + "_" + CommDBUtil.getManagedServerNameWithPort(rs.getString("CHILDID"));
/*      */             }
/* 1399 */             if (!v.contains(rs.getString("CHILDID")))
/*      */             {
/* 1401 */               ht.put(rs.getString("CHILDID"), dispname);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1409 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1413 */       AMConnectionPool.closeResultSet(rs);
/*      */     }
/* 1415 */     return ht;
/*      */   }
/*      */   
/*      */   private ArrayList<Properties> convertTo18N(ArrayList<Properties> list)
/*      */   {
/* 1420 */     for (Properties schedule : list)
/*      */     {
/*      */       try
/*      */       {
/* 1424 */         if ("Weekly".equalsIgnoreCase((String)schedule.get("TYPE")))
/*      */         {
/* 1426 */           String starttime = (String)schedule.get("STARTTIME");
/* 1427 */           String[] splitup = starttime.split(" ", 2);
/* 1428 */           String startday = FormatUtil.getString(splitup[0]);
/* 1429 */           startday = startday + " " + splitup[1];
/* 1430 */           String endtime = (String)schedule.get("ENDTIME");
/* 1431 */           splitup = endtime.split(" ", 2);
/* 1432 */           String endday = FormatUtil.getString(splitup[0]);
/* 1433 */           endday = endday + " " + splitup[1];
/* 1434 */           schedule.put("STARTTIME", startday);
/* 1435 */           schedule.put("ENDTIME", endday);
/*      */         }
/* 1437 */         else if ("Only Once".equalsIgnoreCase((String)schedule.get("TYPE")))
/*      */         {
/* 1439 */           Date d = new Date((String)schedule.get("STARTTIME"));
/* 1440 */           DateFormat df = DateFormat.getDateTimeInstance(0, 0, Locale.getDefault());
/* 1441 */           String formattedDate = df.format(d);
/* 1442 */           schedule.put("STARTTIME", formattedDate);
/* 1443 */           d = new Date((String)schedule.get("ENDTIME"));
/* 1444 */           formattedDate = df.format(d);
/* 1445 */           schedule.put("ENDTIME", formattedDate);
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1450 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1453 */     return list;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\DownTimeSchedulerAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */