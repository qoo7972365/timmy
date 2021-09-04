/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.util.AMRegexUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ReportUtil;
/*      */ import com.adventnet.appmanager.utils.client.ThresholdActionsAPIUtil;
/*      */ import com.adventnet.nms.util.DBParamsParser;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Writer;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Driver;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.commons.collections.MultiHashMap;
/*      */ import org.apache.commons.collections.list.SetUniqueList;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
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
/*      */ public class ThresholdConfigurationAsCSV
/*      */   extends DispatchAction
/*      */ {
/*      */   private String thresholdVsEntityQuery;
/*   64 */   private boolean showAll = false;
/*      */   private String subgroup;
/*      */   private boolean flag;
/*   67 */   private boolean isPrivilegedUser = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   73 */   private final String actionVsEntityQuery = "select aam.ID,aam.ATTRIBUTE,aam.SEVERITY,aam.ACTIONID,ap.NAME from AM_ATTRIBUTEACTIONMAPPER aam join AM_ACTIONPROFILE ap on ap.ID=aam.ACTIONID join AM_ManagedObject mo on mo.RESOURCEID=aam.ID ";
/*   74 */   private final String actionProfilesQuery = "select ID,NAME from AM_ACTIONPROFILE";
/*   75 */   private final String numericThresholdProfileQuery = "select ID,NAME,TYPE,DESCRIPTION,CRITICALTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE END as CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE,WARNINGTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE END as WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE,INFOTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE END as INFOTHRESHOLDVALUE,INFOTHRESHOLDMESSAGE,CRITICAL_POLLSTOTRY,WARNING_POLLSTOTRY,CLEAR_POLLSTOTRY from AM_THRESHOLDCONFIG";
/*   76 */   private final String stringThresholdProfileQuery = "select * from AM_PATTERNMATCHERCONFIG";
/*   77 */   private final String managedObjectQuery = "select RESOURCEID,DISPLAYNAME,TYPE,CREATIONTIME from AM_ManagedObject";
/*   78 */   private final String attributesQuery = "select ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES";
/*   79 */   private final String parentChildMapperQuery = "select PARENTID,CHILDID from AM_PARENTCHILDMAPPER";
/*   80 */   private final String monitorsQuery = "select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and TYPE!='HAI'";
/*   81 */   private StringBuilder haIdQuery = null;
/*   82 */   String groupIdCondition = null;
/*      */   
/*   84 */   final int parentGroup = 0;
/*   85 */   final int subGroup = 1;
/*   86 */   final int childMonitor = 2;
/*      */   
/*      */   private HashMap<String, String> thresholdVsEntityMap;
/*      */   
/*      */   private Map<String, String> thresholdVsDescriptionMap;
/*      */   private Map<String, List<String>> actionVsEntityMap;
/*      */   private HashMap<String, HashMap<String, String>> numericThresholdProfileVsThresholdIdMap;
/*      */   private HashMap<String, HashMap<String, String>> stringThresholdProfileVsThresholdIdMap;
/*      */   private HashMap<String, HashMap<String, String>> managedObjectVsResourceIdMap;
/*      */   private HashMap<String, String> resIdVsCreationTimeMap;
/*      */   private LinkedHashSet<String> validEntities;
/*      */   private MultiHashMap validResIdsVsEntitySet;
/*      */   private MultiHashMap validResIdsForAction;
/*      */   private HashMap<String, String> attributeMap;
/*      */   private HashMap<String, String> actionProfileVsActionIdMap;
/*      */   private HashSet<String> monitors;
/*      */   private MultiHashMap childVsParentMap;
/*      */   private MultiHashMap parentVsChildMap;
/*      */   private LinkedHashMap<String, ArrayList<String>> parentGroupVsSubGrpIds;
/*      */   private StringBuilder parentName;
/*      */   private ArrayList<String> monitorsAlreadyAdded;
/*      */   private ArrayList<String> monitorsAlreadyAddedGlobal;
/*  108 */   private boolean showUncategorizedMonitors = false;
/*  109 */   private Vector<String> resIdsForUser = new Vector();
/*      */   
/*      */   private ArrayList<String> orphanedResIds;
/*      */   
/*  113 */   private boolean showConfiguredMonitors = false;
/*  114 */   private boolean showUnConfiguredMonitors = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward createThresholdConfCSV(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*  140 */     AMLog.debug("ThresholdConfigurationAsCSV.createThresholdConfCSV called at : " + FormatUtil.formatDT(new StringBuilder().append("").append(System.currentTimeMillis()).toString()));
/*  141 */     this.haIdQuery = new StringBuilder("SELECT HAID FROM AM_HOLISTICAPPLICATION");
/*  142 */     this.groupIdCondition = " ORDER BY HAID";
/*  143 */     if ((request.getParameterValues("selectedGroups") != null) && (request.getParameterValues("selectedGroups").length > 0))
/*      */     {
/*  145 */       Vector<String> grpIdVec = new Vector(Arrays.asList(request.getParameterValues("selectedGroups")));
/*  146 */       this.groupIdCondition = (" WHERE " + DBUtil.getCondition("HAID", grpIdVec) + " ORDER BY HAID");
/*      */     }
/*  148 */     this.haIdQuery.append(this.groupIdCondition);
/*  149 */     this.showAll = (request.getParameter("showAll") != null);
/*  150 */     this.showUncategorizedMonitors = (request.getParameter("unCatMonitors") != null);
/*  151 */     this.showUnConfiguredMonitors = (request.getParameter("unAssociatedMonitors") != null);
/*  152 */     if ((this.showAll) || ((request.getParameterValues("selectedGroups") != null) && (request.getParameterValues("selectedGroups").length > 0)))
/*      */     {
/*  154 */       this.showConfiguredMonitors = true;
/*      */     }
/*      */     else
/*      */     {
/*  158 */       this.showConfiguredMonitors = false;
/*      */     }
/*      */     
/*  161 */     AMLog.info("ThresholdConfigurationAsCSV.createThresholdConfCSV() showUncategorizedMonitors : " + this.showUncategorizedMonitors + " showConfiguredMonitors : " + this.showConfiguredMonitors + " showUnConfiguredMonitors : " + this.showUnConfiguredMonitors);
/*  162 */     AMLog.info("ThresholdConfigurationAsCSV.createThresholdConfCSV() haIdQuery " + this.haIdQuery);
/*      */     
/*      */ 
/*  165 */     this.isPrivilegedUser = Constants.isPrivilegedUser(request);
/*  166 */     this.thresholdVsEntityMap = new HashMap();
/*  167 */     this.thresholdVsDescriptionMap = new HashMap();
/*  168 */     this.actionVsEntityMap = new HashMap();
/*  169 */     this.numericThresholdProfileVsThresholdIdMap = new HashMap();
/*  170 */     this.stringThresholdProfileVsThresholdIdMap = new HashMap();
/*  171 */     this.managedObjectVsResourceIdMap = new HashMap();
/*  172 */     this.resIdVsCreationTimeMap = new HashMap();
/*  173 */     this.attributeMap = new HashMap();
/*  174 */     this.validEntities = new LinkedHashSet();
/*  175 */     this.validResIdsVsEntitySet = new MultiHashMap();
/*  176 */     this.validResIdsForAction = new MultiHashMap();
/*  177 */     this.actionProfileVsActionIdMap = new HashMap();
/*  178 */     this.monitors = new HashSet();
/*  179 */     this.childVsParentMap = new MultiHashMap();
/*  180 */     this.parentVsChildMap = new MultiHashMap();
/*  181 */     this.parentGroupVsSubGrpIds = new LinkedHashMap();
/*  182 */     this.monitorsAlreadyAdded = new ArrayList();
/*  183 */     this.monitorsAlreadyAddedGlobal = new ArrayList();
/*  184 */     this.orphanedResIds = new ArrayList();
/*      */     
/*  186 */     if (this.isPrivilegedUser) {
/*  187 */       this.resIdsForUser = DelegatedUserRoleUtil.getResIDsForUser(request);
/*      */     }
/*      */     
/*  190 */     initAllVariables(this.showAll);
/*      */     
/*  192 */     String fileName = constructCSV();
/*  193 */     response.setContentType("text/html; charset=UTF-8");
/*  194 */     PrintWriter out = response.getWriter();
/*      */     try
/*      */     {
/*  197 */       out.append(fileName);
/*  198 */       out.flush();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  208 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  212 */         e.printStackTrace();
/*      */       }
/*      */       
/*      */       try
/*      */       {
/*  217 */         getDisplayNameForOrphaned();
/*      */       } catch (Exception e) {
/*  219 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  202 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  208 */         out.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  212 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  221 */     AMLog.debug("ThresholdConfigurationAsCSV.createThresholdConfCSV finished at : " + FormatUtil.formatDT(new StringBuilder().append("").append(System.currentTimeMillis()).toString()));
/*  222 */     return null;
/*      */   }
/*      */   
/*      */   private void getDisplayNameForOrphaned()
/*      */   {
/*  227 */     AMLog.debug("ThresholdConfigurationAsCSV.getDisplayNameForOrphaned : orphanedResIds : " + this.orphanedResIds);
/*  228 */     if ((this.orphanedResIds != null) && (!this.orphanedResIds.isEmpty()))
/*      */     {
/*  230 */       String orphQuery = "select RESOURCEID,RESOURCENAME,TYPE,DISPLAYNAME from AM_ManagedObject where RESOURCEID in " + DBUtil.getInConditonQueryfromList(this.orphanedResIds);
/*  231 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  234 */         rs = AMConnectionPool.executeQueryStmt(orphQuery);
/*  235 */         while (rs.next())
/*      */         {
/*  237 */           AMLog.debug("ThresholdConfigurationAsCSV.getDisplayNameForOrphaned : RESOURCEID : " + rs.getString("RESOURCEID") + " RESOURCENAME : " + rs.getString("RESOURCENAME") + " TYPE : " + rs.getString("TYPE") + " DISPLAYNAME : " + rs.getString("DISPLAYNAME"));
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  242 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  246 */         AMConnectionPool.closeResultSet(rs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initAllVariables(boolean showAll)
/*      */   {
/*  281 */     ResultSet thresholdVsEntityResultSet = null;
/*      */     
/*  283 */     if (showAll)
/*      */     {
/*  285 */       this.thresholdVsEntityQuery = "select mo.DESCRIPTION,RESOURCEID,ATTRIBUTEID,THRESHOLDCONFIGURATIONID from AM_ManagedObject mo join AM_ATTRIBUTES aa on mo.TYPE=aa.RESOURCETYPE left join AM_ATTRIBUTETHRESHOLDMAPPER aam on mo.RESOURCEID=aam.ID and aam.ATTRIBUTE=aa.ATTRIBUTEID  left join AM_THRESHOLDCONFIG tc on tc.ID=aam.THRESHOLDCONFIGURATIONID where ((mo.type='HAI' AND (aa.ATTRIBUTE='Health' OR aa.ATTRIBUTE='Availability')) OR mo.TYPE!='HAI'  OR mo.TYPE!='Network') AND mo.DCSTARTED!=0";
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  290 */       this.thresholdVsEntityQuery = "select mo.DESCRIPTION,RESOURCEID,ATTRIBUTEID,THRESHOLDCONFIGURATIONID from AM_ManagedObject mo join AM_ATTRIBUTES aa on mo.TYPE=aa.RESOURCETYPE join AM_ATTRIBUTETHRESHOLDMAPPER aam on mo.RESOURCEID=aam.ID and aam.ATTRIBUTE=aa.ATTRIBUTEID join AM_THRESHOLDCONFIG tc on tc.ID=aam.THRESHOLDCONFIGURATIONID where ((mo.type='HAI' AND (aa.ATTRIBUTE='Health' OR aa.ATTRIBUTE='Availability')) OR mo.TYPE!='HAI' OR mo.TYPE!='Network') AND mo.DCSTARTED!=0 ";
/*      */     }
/*      */     try
/*      */     {
/*  294 */       thresholdVsEntityResultSet = AMConnectionPool.executeQueryStmt(this.thresholdVsEntityQuery);
/*  295 */       while (thresholdVsEntityResultSet.next())
/*      */       {
/*  297 */         String validResId = thresholdVsEntityResultSet.getString("RESOURCEID");
/*  298 */         String entity = validResId + "_" + thresholdVsEntityResultSet.getString("ATTRIBUTEID");
/*  299 */         String description = thresholdVsEntityResultSet.getString("DESCRIPTION");
/*  300 */         this.validEntities.add(entity);
/*      */         
/*  302 */         this.validResIdsVsEntitySet.put(validResId, entity);
/*  303 */         this.thresholdVsEntityMap.put(entity, thresholdVsEntityResultSet.getString("THRESHOLDCONFIGURATIONID"));
/*  304 */         this.thresholdVsDescriptionMap.put(validResId, description);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  309 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  313 */       AMConnectionPool.closeStatement(thresholdVsEntityResultSet);
/*      */     }
/*      */     
/*  316 */     ResultSet actionVsEntityResultSet = null;
/*      */     try
/*      */     {
/*  319 */       actionVsEntityResultSet = AMConnectionPool.executeQueryStmt("select aam.ID,aam.ATTRIBUTE,aam.SEVERITY,aam.ACTIONID,ap.NAME from AM_ATTRIBUTEACTIONMAPPER aam join AM_ACTIONPROFILE ap on ap.ID=aam.ACTIONID join AM_ManagedObject mo on mo.RESOURCEID=aam.ID ");
/*  320 */       while (actionVsEntityResultSet.next())
/*      */       {
/*  322 */         String validResId = actionVsEntityResultSet.getString("ID");
/*  323 */         String entity = validResId + "_" + actionVsEntityResultSet.getString("ATTRIBUTE");
/*  324 */         this.validEntities.add(entity);
/*      */         
/*      */ 
/*  327 */         this.validResIdsForAction.put(validResId, entity);
/*      */         
/*  329 */         String entityWithSeverity = entity + "_" + actionVsEntityResultSet.getString("SEVERITY");
/*  330 */         List<String> actions = (List)this.actionVsEntityMap.get(entityWithSeverity);
/*  331 */         if (actions == null)
/*      */         {
/*  333 */           actions = new ArrayList();
/*  334 */           this.actionVsEntityMap.put(entityWithSeverity, actions);
/*      */         }
/*  336 */         actions.add(actionVsEntityResultSet.getString("NAME"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  341 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  345 */       AMConnectionPool.closeStatement(actionVsEntityResultSet);
/*      */     }
/*      */     
/*  348 */     ResultSet numericThresholdProfileResultSet = null;
/*      */     try
/*      */     {
/*  351 */       numericThresholdProfileResultSet = AMConnectionPool.executeQueryStmt("select ID,NAME,TYPE,DESCRIPTION,CRITICALTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.CRITICALTHRESHOLDVALUE END as CRITICALTHRESHOLDVALUE,CRITICALTHRESHOLDMESSAGE,WARNINGTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.WARNINGTHRESHOLDVALUE END as WARNINGTHRESHOLDVALUE,WARNINGTHRESHOLDMESSAGE,INFOTHRESHOLDCONDITION,CASE WHEN TYPE=4 THEN (select AM_FLOAT_THRESHOLDCONFIG.INFOTHRESHOLDVALUE FROM AM_FLOAT_THRESHOLDCONFIG WHERE AM_FLOAT_THRESHOLDCONFIG.ID=AM_THRESHOLDCONFIG.ID) ELSE AM_THRESHOLDCONFIG.INFOTHRESHOLDVALUE END as INFOTHRESHOLDVALUE,INFOTHRESHOLDMESSAGE,CRITICAL_POLLSTOTRY,WARNING_POLLSTOTRY,CLEAR_POLLSTOTRY from AM_THRESHOLDCONFIG");
/*  352 */       while (numericThresholdProfileResultSet.next())
/*      */       {
/*  354 */         this.numericThresholdProfileVsThresholdIdMap.put(numericThresholdProfileResultSet.getString("ID"), getHashMapfromResultSetRow(numericThresholdProfileResultSet));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  359 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  363 */       AMConnectionPool.closeStatement(numericThresholdProfileResultSet);
/*      */     }
/*      */     
/*  366 */     ResultSet stringThresholdProfileResultSet = null;
/*      */     try
/*      */     {
/*  369 */       stringThresholdProfileResultSet = AMConnectionPool.executeQueryStmt("select * from AM_PATTERNMATCHERCONFIG");
/*  370 */       while (stringThresholdProfileResultSet.next())
/*      */       {
/*  372 */         this.stringThresholdProfileVsThresholdIdMap.put(stringThresholdProfileResultSet.getString("ID"), getHashMapfromResultSetRow(stringThresholdProfileResultSet));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  377 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  381 */       AMConnectionPool.closeStatement(stringThresholdProfileResultSet);
/*      */     }
/*      */     
/*  384 */     ResultSet managedObjectResultSet = null;
/*      */     try
/*      */     {
/*  387 */       managedObjectResultSet = AMConnectionPool.executeQueryStmt("select RESOURCEID,DISPLAYNAME,TYPE,CREATIONTIME from AM_ManagedObject");
/*  388 */       while (managedObjectResultSet.next())
/*      */       {
/*  390 */         String resId = managedObjectResultSet.getString("RESOURCEID");
/*  391 */         this.managedObjectVsResourceIdMap.put(resId, getHashMapfromResultSetRow(managedObjectResultSet));
/*  392 */         String creationTime = managedObjectResultSet.getString("CREATIONTIME");
/*  393 */         if (creationTime != null)
/*      */         {
/*  395 */           this.resIdVsCreationTimeMap.put(resId, FormatUtil.formatDT(creationTime).replaceAll(",", "  "));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  401 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  405 */       AMConnectionPool.closeStatement(managedObjectResultSet);
/*      */     }
/*      */     
/*      */ 
/*  409 */     ResultSet attributeResultSet = null;
/*      */     try
/*      */     {
/*  412 */       attributeResultSet = AMConnectionPool.executeQueryStmt("select ATTRIBUTEID,DISPLAYNAME from AM_ATTRIBUTES");
/*  413 */       while (attributeResultSet.next())
/*      */       {
/*  415 */         this.attributeMap.put(attributeResultSet.getString("ATTRIBUTEID"), FormatUtil.getString(attributeResultSet.getString("DISPLAYNAME")));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  420 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  424 */       AMConnectionPool.closeStatement(attributeResultSet);
/*      */     }
/*      */     
/*  427 */     ResultSet actionProfileVsActionIdResultSet = null;
/*      */     
/*      */     try
/*      */     {
/*  431 */       actionProfileVsActionIdResultSet = AMConnectionPool.executeQueryStmt("select ID,NAME from AM_ACTIONPROFILE");
/*  432 */       while (actionProfileVsActionIdResultSet.next())
/*      */       {
/*  434 */         this.actionProfileVsActionIdMap.put(actionProfileVsActionIdResultSet.getString("ID"), actionProfileVsActionIdResultSet.getString("NAME"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  439 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  443 */       AMConnectionPool.closeStatement(actionProfileVsActionIdResultSet);
/*      */     }
/*      */     
/*  446 */     ResultSet monitorsResultSet = null;
/*      */     try
/*      */     {
/*  449 */       monitorsResultSet = AMConnectionPool.executeQueryStmt("select RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and TYPE!='HAI'");
/*  450 */       while (monitorsResultSet.next())
/*      */       {
/*  452 */         this.monitors.add(monitorsResultSet.getString("RESOURCEID"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  457 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  461 */       AMConnectionPool.closeStatement(monitorsResultSet);
/*      */     }
/*      */     
/*  464 */     ResultSet parentChildMapperResultSet = null;
/*      */     try
/*      */     {
/*  467 */       parentChildMapperResultSet = AMConnectionPool.executeQueryStmt("select PARENTID,CHILDID from AM_PARENTCHILDMAPPER");
/*  468 */       while (parentChildMapperResultSet.next())
/*      */       {
/*  470 */         String parentId = parentChildMapperResultSet.getString("PARENTID");
/*  471 */         String childId = parentChildMapperResultSet.getString("CHILDID");
/*      */         
/*  473 */         this.childVsParentMap.put(childId, parentId);
/*  474 */         this.parentVsChildMap.put(parentId, childId);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  479 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  483 */       AMConnectionPool.closeStatement(parentChildMapperResultSet);
/*      */     }
/*      */     
/*      */ 
/*  487 */     Object mgIdList = new ArrayList();
/*  488 */     ResultSet haIdQueryRs = null;
/*      */     try
/*      */     {
/*  491 */       haIdQueryRs = AMConnectionPool.executeQueryStmt(this.haIdQuery.toString());
/*  492 */       while (haIdQueryRs.next())
/*      */       {
/*  494 */         ((ArrayList)mgIdList).add(haIdQueryRs.getString("HAID"));
/*      */       }
/*      */     }
/*      */     catch (Exception e1)
/*      */     {
/*  499 */       e1.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  503 */       AMConnectionPool.closeStatement(haIdQueryRs);
/*      */     }
/*      */     try
/*      */     {
/*  507 */       for (String mgId : (ArrayList)mgIdList)
/*      */       {
/*      */ 
/*  510 */         Object subGroups = (ArrayList)ReportUtil.getLastLevelSubGroup(new ArrayList(), mgId);
/*  511 */         this.parentGroupVsSubGrpIds.put(mgId, subGroups);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  516 */       e.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ArrayList<String> getSecondLevelMonitors(ArrayList<String> secondLevelMonitorsList, String childResId)
/*      */   {
/*  534 */     if ((this.parentVsChildMap.getCollection(childResId) != null) && (!getResourceType(childResId).equals("HAI")))
/*      */     {
/*  536 */       ArrayList<String> secondLevelMonitorIdsList = (ArrayList)this.parentVsChildMap.getCollection(childResId);
/*  537 */       for (String secondLevelMonitorId : secondLevelMonitorIdsList)
/*      */       {
/*  539 */         ArrayList<String> v1 = (ArrayList)this.validResIdsVsEntitySet.get(secondLevelMonitorId);
/*  540 */         if (v1 != null)
/*      */         {
/*  542 */           secondLevelMonitorsList.addAll(v1);
/*      */         }
/*      */         
/*      */ 
/*  546 */         ArrayList<String> v2 = (ArrayList)this.validResIdsForAction.get(secondLevelMonitorId);
/*  547 */         if (v2 != null)
/*      */         {
/*  549 */           secondLevelMonitorsList.addAll(v2);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  554 */     return secondLevelMonitorsList;
/*      */   }
/*      */   
/*      */   private String constructCSV()
/*      */     throws Exception
/*      */   {
/*  560 */     SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.getDefault());
/*  561 */     String currentTime = simpledateformat.format(new Date(System.currentTimeMillis()));
/*  562 */     String fileName = "ThresholdConfiguration" + "-" + "(" + Constants.getAppHostName() + "-" + System.getProperty("webserver.port") + (EnterpriseUtil.isManagedServer() ? "-MASID-".concat("" + EnterpriseUtil.getManagedServerIndex()) : "") + ")" + "-" + currentTime + ".csv";
/*      */     
/*      */ 
/*      */ 
/*  566 */     File file = new File(".." + File.separator + "Reports" + File.separator + fileName);
/*  567 */     BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
/*      */     
/*      */ 
/*  570 */     String fileCreationTime = "******* THRESHOLD CONFIGURATION - Creation Time : " + currentTime + " *******".concat(System.getProperty("line.separator"));
/*  571 */     String csvHeader = "Monitor Group,Sub Group,Parent Monitor,Monitor Name,Monitor Type,Creation Time,Attribute,Threshold Profile,Critical If,Warning If,Clear If,Critical Action,Warning Action,Clear Action".concat(System.getProperty("line.separator"));
/*  572 */     Iterator<String> grpIdItr = this.parentGroupVsSubGrpIds.keySet().iterator();
/*  573 */     bufferedwriter.append(fileCreationTime);
/*  574 */     bufferedwriter.append(csvHeader);
/*      */     
/*  576 */     ArrayList<String> subGrpsToExclude = new ArrayList();
/*      */     
/*  578 */     AMLog.info("parentVsChildMap is : " + this.parentVsChildMap);
/*  579 */     AMLog.info("validResIdsVsEntitySet is : " + this.validResIdsVsEntitySet);
/*  580 */     AMLog.info("validResIdsForAction is : " + this.validResIdsForAction);
/*  581 */     AMLog.info("actionProfileVsActionIdMap is : " + this.actionProfileVsActionIdMap);
/*  582 */     AMLog.info("childVsParentMap is : " + this.childVsParentMap);
/*      */     
/*  584 */     while (((this.showUncategorizedMonitors) || (this.showConfiguredMonitors)) && (grpIdItr.hasNext()))
/*      */     {
/*      */       try
/*      */       {
/*  588 */         String parentGrpId = (String)grpIdItr.next();
/*  589 */         if (!subGrpsToExclude.contains(parentGrpId))
/*      */         {
/*  591 */           ArrayList<String> parentList = new ArrayList();
/*  592 */           parentList.add(parentGrpId);
/*  593 */           ArrayList<String> childIds = (ArrayList)this.parentVsChildMap.getCollection(parentGrpId);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  607 */           ArrayList<String> subGrps = (ArrayList)this.parentGroupVsSubGrpIds.get(parentGrpId);
/*  608 */           if (subGrps != null)
/*      */           {
/*  610 */             subGrpsToExclude.addAll(subGrps);
/*      */           }
/*  612 */           if (childIds != null)
/*      */           {
/*  614 */             childIds.removeAll(subGrps);
/*      */           }
/*  616 */           this.parentName = new StringBuilder();
/*  617 */           AMLog.info("ThresholdConfigurationAsCSV.constructCSV() \nparentList : " + parentList + "\nchildIds : " + childIds + "\nparentName : " + this.parentName + "\nSubGroups : " + subGrps);
/*  618 */           writeIntoFile(0, parentList, bufferedwriter, childIds, this.parentName);
/*  619 */           this.monitorsAlreadyAddedGlobal.addAll(this.monitorsAlreadyAdded);
/*  620 */           this.monitorsAlreadyAdded.clear();
/*  621 */           for (String individualSubGrps : subGrps)
/*      */           {
/*  623 */             ArrayList<String> subGrpList = new ArrayList();
/*  624 */             subGrpList.add(individualSubGrps);
/*      */             
/*  626 */             ArrayList<String> subChildIds = (ArrayList)this.parentVsChildMap.getCollection(individualSubGrps);
/*  627 */             ArrayList<String> innerSubGrps = (ArrayList)this.parentGroupVsSubGrpIds.get(individualSubGrps);
/*  628 */             if ((subChildIds != null) && (innerSubGrps != null))
/*      */             {
/*  630 */               subChildIds.removeAll(innerSubGrps);
/*      */             }
/*  632 */             StringBuilder localParentName = new StringBuilder(this.parentName);
/*  633 */             AMLog.info("ThresholdConfigurationAsCSV.constructCSV() \nsubGrpList : " + subGrpList + "\nsubChildIds : " + subChildIds + "\nlocalParentName : " + localParentName);
/*  634 */             writeIntoFile(1, subGrpList, bufferedwriter, subChildIds, localParentName);
/*  635 */             this.monitorsAlreadyAddedGlobal.addAll(this.monitorsAlreadyAdded);
/*  636 */             this.monitorsAlreadyAdded.clear();
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ww)
/*      */       {
/*  642 */         ww.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  646 */     AMLog.info("ThresholdConfigurationAsCSV.constructCSV() monitorsAlreadyAdded : " + this.monitorsAlreadyAdded);
/*  647 */     if ((this.showUncategorizedMonitors) && (!this.isPrivilegedUser))
/*      */     {
/*  649 */       if (!this.showConfiguredMonitors)
/*      */       {
/*  651 */         bufferedwriter = new BufferedWriter(new FileWriter(file));
/*  652 */         bufferedwriter.append(fileCreationTime);
/*  653 */         bufferedwriter.append(csvHeader);
/*      */       }
/*  655 */       Set<String> uncategorizedList = null;
/*      */       try
/*      */       {
/*  658 */         this.validEntities.removeAll(this.monitorsAlreadyAddedGlobal);
/*  659 */         uncategorizedList = new TreeSet(this.validEntities);
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  663 */         ex.printStackTrace();
/*      */       }
/*  665 */       bufferedwriter.append("*******UNCATEGORIZED MONITORS*******").append(System.getProperty("line.separator"));
/*      */       
/*      */       try
/*      */       {
/*  669 */         for (String entity : uncategorizedList)
/*      */         {
/*      */           try
/*      */           {
/*  673 */             String resourceId = entity.split("_")[0];
/*  674 */             String attributeId = entity.split("_")[1];
/*      */             
/*      */ 
/*  677 */             bufferedwriter.append(" ,");
/*  678 */             bufferedwriter.append(" ,");
/*  679 */             bufferedwriter.append(getResourceName(resourceId)).append(",");
/*  680 */             bufferedwriter.append(getResourceType(resourceId)).append(",");
/*  681 */             bufferedwriter.append(getCreationTime(resourceId)).append(",");
/*  682 */             bufferedwriter.append(getResourceAttribute(attributeId)).append(",");
/*      */             
/*  684 */             bufferedwriter.append(getResourceThresholdInfo(entity));
/*  685 */             bufferedwriter.append(getResourceActionInfo(entity + "_1")).append(",").append(getResourceActionInfo(entity + "_4")).append(",").append(getResourceActionInfo(entity + "_5"));
/*  686 */             bufferedwriter.append(System.getProperty("line.separator"));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  690 */             e.printStackTrace();
/*  691 */             AMLog.info("Entity=" + entity + "  Error Message=" + e.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex1)
/*      */       {
/*  697 */         ex1.printStackTrace();
/*      */       }
/*      */     }
/*  700 */     if (this.showUnConfiguredMonitors)
/*      */     {
/*  702 */       ArrayList<String> configuredList = new ArrayList(this.validResIdsVsEntitySet.keySet());
/*  703 */       configuredList.addAll(this.validResIdsForAction.keySet());
/*  704 */       configuredList.add(DBUtil.getGlobalConfigValue("scriptmonitorsresourceid"));
/*      */       
/*  706 */       bufferedwriter.append("*******MONITORS WITHOUT THRESHOLD OR ACTION*******").append(System.getProperty("line.separator"));
/*  707 */       String notinCondition = ConfMonitorUtil.getCondition("RESOURCEID", configuredList, false);
/*  708 */       notinCondition = notinCondition.replace("RESOURCEID in", " RESOURCEID NOT IN ");
/*  709 */       String query = "select mo.RESOURCEID from AM_ManagedObject mo where mo.RESOURCEID NOT IN (select ID from AM_ATTRIBUTEACTIONMAPPER) and mo.RESOURCEID NOT IN (select ID from  AM_ATTRIBUTETHRESHOLDMAPPER) and RESOURCEID NOT IN (select TYPEID from AM_MONITOR_TYPES) and TYPE NOT IN (select RESOURCEGROUP from AM_ManagedResourceType) and (TYPE not like '%-TEMPLATE%' AND TYPE not like 'DUMMY') and " + notinCondition;
/*      */       
/*  711 */       ArrayList<String> unConfiguredList = new ArrayList();
/*      */       
/*      */ 
/*  714 */       ResultSet rs = null;
/*      */       try
/*      */       {
/*  717 */         rs = AMConnectionPool.executeQueryStmt(query);
/*  718 */         while (rs.next())
/*      */         {
/*  720 */           if ((this.isPrivilegedUser) && (this.resIdsForUser != null) && (this.resIdsForUser.contains(rs.getString("RESOURCEID"))))
/*      */           {
/*  722 */             unConfiguredList.add(rs.getString("RESOURCEID"));
/*      */           }
/*  724 */           else if (!this.isPrivilegedUser)
/*      */           {
/*  726 */             unConfiguredList.add(rs.getString("RESOURCEID"));
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  732 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  736 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       try
/*      */       {
/*  740 */         SetUniqueList.decorate(unConfiguredList);
/*  741 */         for (String resourceId : unConfiguredList)
/*      */         {
/*      */           try
/*      */           {
/*  745 */             String resName = getResourceName(resourceId);
/*  746 */             bufferedwriter.append(getMonitorGroup(resourceId)).append(",");
/*  747 */             bufferedwriter.append(",");
/*  748 */             bufferedwriter.append(resName).append(",");
/*  749 */             bufferedwriter.append(getResourceType(resourceId)).append(",");
/*  750 */             bufferedwriter.append(getCreationTime(resourceId)).append(",");
/*  751 */             bufferedwriter.append(",");
/*  752 */             bufferedwriter.append(",");
/*  753 */             bufferedwriter.append(",");
/*  754 */             bufferedwriter.append(",");
/*  755 */             bufferedwriter.append(",");
/*  756 */             bufferedwriter.append(",");
/*  757 */             bufferedwriter.append(",");
/*  758 */             bufferedwriter.append(",");
/*  759 */             bufferedwriter.append(System.getProperty("line.separator"));
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  763 */             e.printStackTrace();
/*  764 */             AMLog.info("resourceId=" + resourceId + "  Error Message=" + e.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception ex1)
/*      */       {
/*  770 */         ex1.printStackTrace();
/*      */       }
/*      */     }
/*      */     try
/*      */     {
/*  775 */       if (bufferedwriter != null)
/*      */       {
/*  777 */         bufferedwriter.close();
/*      */       }
/*      */     }
/*      */     catch (IOException ioexception)
/*      */     {
/*  782 */       ioexception.printStackTrace();
/*      */     }
/*  784 */     return fileName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addSecondLevelMonitors(ArrayList<String> childIds)
/*      */   {
/*  792 */     for (String childResId : (ArrayList)childIds.clone())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  801 */       if ((this.parentVsChildMap.getCollection(childResId) != null) && (!getResourceType(childResId).equals("HAI")))
/*      */       {
/*  803 */         ArrayList<String> secondLevelMonitorIdsList = (ArrayList)this.parentVsChildMap.getCollection(childResId);
/*  804 */         for (String secondLevelMonitorId : secondLevelMonitorIdsList)
/*      */         {
/*  806 */           ArrayList<String> validSeconLevelEntitys = (ArrayList)this.validResIdsVsEntitySet.get(secondLevelMonitorId);
/*  807 */           if (validSeconLevelEntitys != null)
/*      */           {
/*  809 */             addAllResIds(childIds, validSeconLevelEntitys);
/*      */           }
/*      */           
/*  812 */           ArrayList<String> v2 = (ArrayList)this.validResIdsForAction.get(secondLevelMonitorId);
/*  813 */           if (v2 != null)
/*      */           {
/*  815 */             addAllResIds(childIds, v2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void addAllResIds(ArrayList<String> childIds, ArrayList<String> validSeconLevelEntitys)
/*      */   {
/*  829 */     for (String entity : validSeconLevelEntitys)
/*      */     {
/*  831 */       AMLog.info("ThresholdConfigurationAsCSV.addAllResIds() entity is " + entity);
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
/*      */ 
/*      */   private void writeIntoFile(int resType, ArrayList<String> parentList, BufferedWriter bufferedwriter, ArrayList<String> childList, StringBuilder parentName)
/*      */   {
/*      */     try
/*      */     {
/*  847 */       writeToBuffer(resType, bufferedwriter, parentList, parentName);
/*  848 */       writeToBuffer(2, bufferedwriter, childList, parentName);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  852 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void writeToBuffer(int resType, BufferedWriter bufferedwriter, ArrayList<String> childList, StringBuilder parentName)
/*      */   {
/*      */     try
/*      */     {
/*  861 */       if (childList == null)
/*      */       {
/*  863 */         return;
/*      */       }
/*  865 */       for (String childId : childList)
/*      */       {
/*  867 */         Object thresholdConfig = this.validResIdsVsEntitySet.get(childId);
/*  868 */         if ((thresholdConfig == null) && ((resType == 0) || (resType == 1))) {
/*      */           StringBuilder parentTree;
/*  870 */           if (this.validResIdsForAction.containsKey(childId))
/*      */           {
/*  872 */             parentTree = new StringBuilder(parentName);
/*  873 */             ArrayList<String> entityIds = (ArrayList)this.validResIdsForAction.get(childId);
/*      */             
/*  875 */             SetUniqueList.decorate(entityIds);
/*  876 */             AMLog.info("type is " + resType + " and entityIds entityIds is " + entityIds);
/*  877 */             for (String entity : entityIds)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  883 */               this.monitorsAlreadyAdded.add(entity);
/*  884 */               String attributeId = entity.split("_")[1];
/*  885 */               setResName(resType, childId, bufferedwriter, parentName);
/*  886 */               parentName = parentTree;
/*      */               
/*  888 */               if (resType == 1)
/*      */               {
/*  890 */                 bufferedwriter.append(",");
/*      */               }
/*  892 */               String type = getResourceType(childId);
/*  893 */               bufferedwriter.append(type.equals("HAI") ? ",Monitor Group" : type).append(",");
/*  894 */               bufferedwriter.append(getCreationTime(childId)).append(",");
/*  895 */               bufferedwriter.append(getResourceAttribute(attributeId)).append(",");
/*  896 */               bufferedwriter.append(",");
/*  897 */               bufferedwriter.append(",");
/*  898 */               bufferedwriter.append(",");
/*  899 */               bufferedwriter.append(",");
/*  900 */               bufferedwriter.append(getResourceActionInfo(entity + "_1")).append(",").append(getResourceActionInfo(entity + "_4")).append(",").append(getResourceActionInfo(entity + "_5"));
/*  901 */               bufferedwriter.append(System.getProperty("line.separator"));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  906 */             String type = getResourceType(childId);
/*      */             
/*  908 */             if ((!type.equals("HAI")) || (resType != 1))
/*      */             {
/*  910 */               setResName(resType, childId, bufferedwriter, parentName);
/*  911 */               if (resType == 1)
/*      */               {
/*  913 */                 bufferedwriter.append(",");
/*      */               }
/*  915 */               bufferedwriter.append(type.equals("HAI") ? ",Monitor Group" : type).append(",");
/*  916 */               bufferedwriter.append(getCreationTime(childId)).append(",");
/*  917 */               bufferedwriter.append(",");
/*  918 */               bufferedwriter.append(",");
/*  919 */               bufferedwriter.append(",");
/*  920 */               bufferedwriter.append(",");
/*  921 */               bufferedwriter.append(System.getProperty("line.separator"));
/*      */             }
/*      */             
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  928 */           ArrayList<String> secondLevelIdsList = new ArrayList();
/*  929 */           if (this.validResIdsVsEntitySet.containsKey(childId))
/*      */           {
/*  931 */             ArrayList<String> entityIds = (ArrayList)thresholdConfig;
/*  932 */             for (String entity : entityIds)
/*      */             {
/*      */               try
/*      */               {
/*  936 */                 this.monitorsAlreadyAdded.add(entity);
/*  937 */                 String resourceId = entity.split("_")[0];
/*  938 */                 String attributeId = entity.split("_")[1];
/*  939 */                 AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() called for entity : " + entity + " displayname : " + getResourceName(resourceId));
/*      */                 
/*  941 */                 String description = getDescription(resourceId);
/*  942 */                 if ((!"Description....This service is critical to our business".equals(description)) || (resType != 1)) {
/*  943 */                   setResName(resType, resourceId, bufferedwriter, parentName);
/*  944 */                   bufferedwriter.append(getResourceType(resourceId)).append(",");
/*  945 */                   bufferedwriter.append(getCreationTime(resourceId)).append(",");
/*  946 */                   bufferedwriter.append(getResourceAttribute(attributeId)).append(",");
/*  947 */                   bufferedwriter.append(getResourceThresholdInfo(entity));
/*  948 */                   bufferedwriter.append(getResourceActionInfo(entity + "_1")).append(",").append(getResourceActionInfo(entity + "_4")).append(",").append(getResourceActionInfo(entity + "_5"));
/*  949 */                   bufferedwriter.append(System.getProperty("line.separator"));
/*      */                 }
/*  951 */                 ArrayList<String> secondLevelIds = new ArrayList();
/*  952 */                 getSecondLevelMonitors(secondLevelIds, resourceId);
/*  953 */                 if ((secondLevelIds != null) && (secondLevelIds.size() > 0))
/*      */                 {
/*  955 */                   for (String secondLevelEntity : secondLevelIds)
/*      */                   {
/*  957 */                     if (!this.monitorsAlreadyAdded.contains(secondLevelEntity))
/*      */                     {
/*  959 */                       this.monitorsAlreadyAdded.add(secondLevelEntity);
/*  960 */                       String secondLevelId = secondLevelEntity.split("_")[0];
/*  961 */                       String secondLevelAttributeId = secondLevelEntity.split("_")[1];
/*  962 */                       AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() called for entity : " + secondLevelEntity + " displayname : " + getResourceName(secondLevelId));
/*  963 */                       setResName(resType, secondLevelId, bufferedwriter, parentName);
/*  964 */                       bufferedwriter.append(getResourceType(secondLevelId)).append(",");
/*  965 */                       bufferedwriter.append(getCreationTime(secondLevelId)).append(",");
/*  966 */                       bufferedwriter.append(getResourceAttribute(secondLevelAttributeId)).append(",");
/*  967 */                       bufferedwriter.append(getResourceThresholdInfo(secondLevelEntity));
/*  968 */                       bufferedwriter.append(getResourceActionInfo(secondLevelEntity + "_1")).append(",").append(getResourceActionInfo(secondLevelEntity + "_4")).append(",").append(getResourceActionInfo(secondLevelEntity + "_5"));
/*  969 */                       bufferedwriter.append(System.getProperty("line.separator"));
/*      */                     }
/*      */                     
/*      */                   }
/*      */                 }
/*      */               }
/*      */               catch (Exception e1)
/*      */               {
/*  977 */                 e1.printStackTrace();
/*      */               }
/*      */             }
/*      */           }
/*  981 */           if (this.validResIdsForAction.containsKey(childId))
/*      */           {
/*  983 */             ArrayList<String> entityIds = (ArrayList)this.validResIdsForAction.get(childId);
/*  984 */             AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() validResIdsForAction called for childId : " + childId + " and  entityIds is " + entityIds);
/*  985 */             if (entityIds != null)
/*      */             {
/*  987 */               for (String entity : entityIds)
/*      */               {
/*      */                 try
/*      */                 {
/*  991 */                   if (!this.monitorsAlreadyAdded.contains(entity))
/*      */                   {
/*  993 */                     this.monitorsAlreadyAdded.add(entity);
/*  994 */                     String resourceId = entity.split("_")[0];
/*  995 */                     String attributeId = entity.split("_")[1];
/*  996 */                     AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() validResIdsForAction called for entity : " + entity + " displayname : " + getResourceName(resourceId));
/*  997 */                     setResName(resType, resourceId, bufferedwriter, parentName);
/*  998 */                     bufferedwriter.append(getResourceType(resourceId)).append(",");
/*  999 */                     bufferedwriter.append(getCreationTime(resourceId)).append(",");
/* 1000 */                     bufferedwriter.append(getResourceAttribute(attributeId)).append(",");
/* 1001 */                     bufferedwriter.append(getResourceThresholdInfo(entity));
/* 1002 */                     bufferedwriter.append(getResourceActionInfo(entity + "_1")).append(",").append(getResourceActionInfo(entity + "_4")).append(",").append(getResourceActionInfo(entity + "_5"));
/* 1003 */                     bufferedwriter.append(System.getProperty("line.separator"));
/* 1004 */                     ArrayList<String> secondLevelIds = new ArrayList();
/* 1005 */                     getSecondLevelMonitors(secondLevelIds, resourceId);
/* 1006 */                     if ((secondLevelIds != null) && (secondLevelIds.size() > 0))
/*      */                     {
/* 1008 */                       for (String secondLevelEntity : secondLevelIds)
/*      */                       {
/* 1010 */                         if (!this.monitorsAlreadyAdded.contains(secondLevelEntity))
/*      */                         {
/* 1012 */                           this.monitorsAlreadyAdded.add(secondLevelEntity);
/* 1013 */                           String secondLevelId = secondLevelEntity.split("_")[0];
/* 1014 */                           String secondLevelAttributeId = secondLevelEntity.split("_")[1];
/* 1015 */                           AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() called for entity : " + secondLevelEntity + " displayname : " + getResourceName(secondLevelId));
/* 1016 */                           setResName(resType, secondLevelId, bufferedwriter, parentName);
/* 1017 */                           bufferedwriter.append(getResourceType(secondLevelId)).append(",");
/* 1018 */                           bufferedwriter.append(getCreationTime(secondLevelId)).append(",");
/* 1019 */                           bufferedwriter.append(getResourceAttribute(secondLevelAttributeId)).append(",");
/* 1020 */                           bufferedwriter.append(getResourceThresholdInfo(secondLevelEntity));
/* 1021 */                           bufferedwriter.append(getResourceActionInfo(secondLevelEntity + "_1")).append(",").append(getResourceActionInfo(secondLevelEntity + "_4")).append(",").append(getResourceActionInfo(secondLevelEntity + "_5"));
/* 1022 */                           bufferedwriter.append(System.getProperty("line.separator"));
/*      */                         }
/*      */                         
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 catch (Exception e1)
/*      */                 {
/* 1031 */                   e1.printStackTrace();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 1036 */           if (!this.monitorsAlreadyAdded.contains(childId))
/*      */           {
/* 1038 */             ArrayList<String> secondLevelIds = new ArrayList();
/* 1039 */             getSecondLevelMonitors(secondLevelIds, childId);
/* 1040 */             if ((secondLevelIds != null) && (secondLevelIds.size() > 0))
/*      */             {
/* 1042 */               for (String secondLevelEntity : secondLevelIds)
/*      */               {
/* 1044 */                 if (!this.monitorsAlreadyAdded.contains(secondLevelEntity))
/*      */                 {
/* 1046 */                   this.monitorsAlreadyAdded.add(secondLevelEntity);
/* 1047 */                   String secondLevelId = secondLevelEntity.split("_")[0];
/* 1048 */                   String secondLevelAttributeId = secondLevelEntity.split("_")[1];
/* 1049 */                   AMLog.info("ThresholdConfigurationAsCSV.writeToBuffer() called for entity : " + secondLevelEntity + " displayname : " + getResourceName(secondLevelId));
/* 1050 */                   setResName(resType, secondLevelId, bufferedwriter, parentName);
/* 1051 */                   bufferedwriter.append(getResourceType(secondLevelId)).append(",");
/* 1052 */                   bufferedwriter.append(getCreationTime(secondLevelId)).append(",");
/* 1053 */                   bufferedwriter.append(getResourceAttribute(secondLevelAttributeId)).append(",");
/* 1054 */                   bufferedwriter.append(getResourceThresholdInfo(secondLevelEntity));
/* 1055 */                   bufferedwriter.append(getResourceActionInfo(secondLevelEntity + "_1")).append(",").append(getResourceActionInfo(secondLevelEntity + "_4")).append(",").append(getResourceActionInfo(secondLevelEntity + "_5"));
/* 1056 */                   bufferedwriter.append(System.getProperty("line.separator"));
/*      */                 }
/*      */                 
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1067 */       e.printStackTrace();
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
/*      */ 
/*      */   private void writeSecondLevelMonitors() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setResName(int type, String resourceId, BufferedWriter bufferedwriter, StringBuilder parentName)
/*      */   {
/*      */     try
/*      */     {
/* 1092 */       String resourceDispName = getResourceName(resourceId);
/* 1093 */       if (type == 0)
/*      */       {
/*      */ 
/* 1096 */         this.flag = false;
/* 1097 */         bufferedwriter.append(resourceDispName).append(",");
/* 1098 */         bufferedwriter.append(",");
/* 1099 */         bufferedwriter.append(",");
/*      */         
/* 1101 */         if (!parentName.toString().equals(resourceDispName)) {
/* 1102 */           parentName.append(resourceDispName);
/*      */         }
/*      */       }
/* 1105 */       else if (type == 1)
/*      */       {
/* 1107 */         this.flag = true;
/* 1108 */         this.subgroup = resourceDispName;
/* 1109 */         bufferedwriter.append(parentName.toString()).append(",");
/*      */         
/*      */ 
/* 1112 */         bufferedwriter.append(resourceDispName);
/* 1113 */         bufferedwriter.append(",");
/* 1114 */         bufferedwriter.append(",");
/*      */       }
/*      */       else
/*      */       {
/* 1118 */         bufferedwriter.append(parentName.toString()).append(",");
/* 1119 */         if (this.flag) {
/* 1120 */           bufferedwriter.append(this.subgroup).append(",");
/*      */         }
/* 1122 */         if (!this.flag) {
/* 1123 */           bufferedwriter.append(",");
/*      */         }
/* 1125 */         bufferedwriter.append(resourceDispName).append(",");
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1130 */       e.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getResourceName(String resourceId)
/*      */   {
/* 1165 */     HashMap<String, String> managedObject = (HashMap)this.managedObjectVsResourceIdMap.get(resourceId);
/* 1166 */     StringBuffer toReturn = new StringBuffer();
/* 1167 */     String type = (String)managedObject.get("TYPE");
/* 1168 */     if ((!"HAI".equals(type)) && (!this.monitors.contains(resourceId)))
/*      */     {
/* 1170 */       toReturn.append("\"").append(getParentResourceDisplayname(resourceId)).append("\"").append(",");
/*      */     }
/* 1172 */     else if ((!"HAI".equals(type)) && (this.monitors.contains(resourceId)))
/*      */     {
/* 1174 */       toReturn.append(",");
/*      */     }
/* 1176 */     toReturn.append("\"").append((String)managedObject.get("DISPLAYNAME")).append("\"");
/* 1177 */     return toReturn.toString();
/*      */   }
/*      */   
/*      */   private String getParentResourceDisplayname(String resourceId) {
/* 1181 */     ArrayList parentIds = (ArrayList)this.childVsParentMap.get(resourceId);
/* 1182 */     if ((parentIds != null) && (!parentIds.isEmpty())) {
/* 1183 */       return (String)((HashMap)this.managedObjectVsResourceIdMap.get(parentIds.get(0).toString())).get("DISPLAYNAME");
/*      */     }
/* 1185 */     this.orphanedResIds.add(resourceId);
/* 1186 */     return "";
/*      */   }
/*      */   
/*      */   private String getResourceType(String resourceId) {
/* 1190 */     return (String)((HashMap)this.managedObjectVsResourceIdMap.get(resourceId)).get("TYPE");
/*      */   }
/*      */   
/* 1193 */   private String getDescription(String resourceId) { return (String)this.thresholdVsDescriptionMap.get(resourceId); }
/*      */   
/*      */ 
/*      */   private String getCreationTime(String resourceId)
/*      */   {
/* 1198 */     return (String)this.resIdVsCreationTimeMap.get(resourceId);
/*      */   }
/*      */   
/*      */   private String getResourceAttribute(String attributeId) {
/* 1202 */     return (String)this.attributeMap.get(attributeId);
/*      */   }
/*      */   
/*      */   private String getMonitorGroup(String resourceId)
/*      */   {
/* 1207 */     StringBuffer toReturn = new StringBuffer();
/* 1208 */     ArrayList mgIds = (ArrayList)this.childVsParentMap.get(resourceId);
/* 1209 */     if (mgIds == null)
/*      */     {
/* 1211 */       return "";
/*      */     }
/* 1213 */     for (Object mgId : mgIds)
/*      */     {
/* 1215 */       HashMap resourceInfo = (HashMap)this.managedObjectVsResourceIdMap.get(mgId.toString());
/* 1216 */       if ((resourceInfo != null) && ("HAI".equals(resourceInfo.get("TYPE"))))
/*      */       {
/*      */ 
/* 1219 */         toReturn.append(resourceInfo.get("DISPLAYNAME"));
/*      */       }
/*      */     }
/* 1222 */     return toReturn.toString();
/*      */   }
/*      */   
/*      */   private String cookResourceVsMonitorGroupMap(String resourceId) {
/* 1226 */     return "";
/*      */   }
/*      */   
/*      */   private StringBuilder getResourceThresholdInfo(String entity) {
/* 1230 */     StringBuilder toReturn = new StringBuilder().append("");
/*      */     try {
/* 1232 */       String thresholdId = (String)this.thresholdVsEntityMap.get(entity);
/* 1233 */       HashMap<String, String> thresholdInfo = (HashMap)this.numericThresholdProfileVsThresholdIdMap.get(thresholdId);
/* 1234 */       if (thresholdInfo == null) {
/* 1235 */         return toReturn.append(",,,,");
/*      */       }
/* 1237 */       toReturn.append((String)thresholdInfo.get("NAME")).append(",");
/* 1238 */       if (!"3".equals(thresholdInfo.get("TYPE"))) {
/* 1239 */         if ("4".equals(thresholdInfo.get("TYPE"))) {
/* 1240 */           ArrayList thresholdvalue = new ArrayList();
/* 1241 */           if (AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"))) {
/* 1242 */             ThresholdActionsAPIUtil.checkAndpopulateThresholdValues((String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"), (String)thresholdInfo.get("CRITICALTHRESHOLDVALUE"), thresholdvalue);
/* 1243 */             toReturn.append("Value ").append(thresholdvalue.get(0)).append(",");
/* 1244 */           } else if (!AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"))) {
/* 1245 */             toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"))).append(" ").append(Math.round(Double.parseDouble(((String)thresholdInfo.get("CRITICALTHRESHOLDVALUE")).toString()) * 100.0D) / 100.0D).append(",");
/*      */           }
/* 1247 */           thresholdvalue.clear();
/* 1248 */           if (AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"))) {
/* 1249 */             ThresholdActionsAPIUtil.checkAndpopulateThresholdValues((String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"), (String)thresholdInfo.get("WARNINGTHRESHOLDVALUE"), thresholdvalue);
/* 1250 */             toReturn.append("Value ").append(thresholdvalue.get(0)).append(",");
/* 1251 */           } else if (!AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"))) {
/* 1252 */             toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"))).append(" ").append(Math.round(Double.parseDouble(((String)thresholdInfo.get("WARNINGTHRESHOLDVALUE")).toString()) * 100.0D) / 100.0D).append(",");
/*      */           }
/* 1254 */           thresholdvalue.clear();
/* 1255 */           if (AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("INFOTHRESHOLDCONDITION"))) {
/* 1256 */             ThresholdActionsAPIUtil.checkAndpopulateThresholdValues((String)thresholdInfo.get("INFOTHRESHOLDCONDITION"), (String)thresholdInfo.get("INFOTHRESHOLDVALUE"), thresholdvalue);
/* 1257 */             toReturn.append("Value ").append(thresholdvalue.get(0)).append(",");
/*      */           }
/* 1259 */           else if (!AMRegexUtil.matches("(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?)((OR|AND)(GT|LT|EQ|NE|LE|GE)([0-9]+(.[0-9]+)?))+", (String)thresholdInfo.get("INFOTHRESHOLDCONDITION"))) {
/* 1260 */             toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("INFOTHRESHOLDCONDITION"))).append(" ").append(Math.round(Double.parseDouble(((String)thresholdInfo.get("INFOTHRESHOLDVALUE")).toString()) * 100.0D) / 100.0D).append(",");
/*      */           }
/* 1262 */           thresholdvalue.clear();
/*      */         } else {
/* 1264 */           toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"))).append(" ").append((String)thresholdInfo.get("CRITICALTHRESHOLDVALUE")).append(",");
/* 1265 */           toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"))).append(" ").append((String)thresholdInfo.get("WARNINGTHRESHOLDVALUE")).append(",");
/* 1266 */           toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("INFOTHRESHOLDCONDITION"))).append(" ").append((String)thresholdInfo.get("INFOTHRESHOLDVALUE")).append(",");
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1271 */         toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("CRITICALTHRESHOLDCONDITION"))).append(" ").append((String)((HashMap)this.stringThresholdProfileVsThresholdIdMap.get(thresholdId)).get("CRITICALTHRESHOLDVALUE")).append(",");
/* 1272 */         toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("WARNINGTHRESHOLDCONDITION"))).append(" ").append((String)((HashMap)this.stringThresholdProfileVsThresholdIdMap.get(thresholdId)).get("WARNINGTHRESHOLDVALUE")).append(",");
/* 1273 */         toReturn.append("Value ").append(getCondition((String)thresholdInfo.get("INFOTHRESHOLDCONDITION"))).append(" ").append((String)((HashMap)this.stringThresholdProfileVsThresholdIdMap.get(thresholdId)).get("INFOTHRESHOLDVALUE")).append(",");
/*      */       }
/*      */     }
/*      */     catch (NullPointerException npe) {}
/*      */     
/* 1278 */     return toReturn;
/*      */   }
/*      */   
/*      */   private StringBuilder getResourceActionInfo(String entityWithSeverity) {
/* 1282 */     StringBuilder toReturn = new StringBuilder().append("");
/*      */     try {
/* 1284 */       if (this.actionVsEntityMap.containsKey(entityWithSeverity))
/*      */       {
/* 1286 */         List<String> actions = (List)this.actionVsEntityMap.get(entityWithSeverity);
/* 1287 */         for (int i = 0; i < actions.size(); i++)
/*      */         {
/* 1289 */           toReturn.append("\"");
/* 1290 */           toReturn.append((String)actions.get(i));
/* 1291 */           toReturn.append("\"");
/* 1292 */           if (i != actions.size() - 1)
/*      */           {
/* 1294 */             toReturn.append(" ");
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (NullPointerException npe) {}
/*      */     
/* 1301 */     return toReturn;
/*      */   }
/*      */   
/*      */   private String getCondition(String s)
/*      */   {
/* 1306 */     if (s == null)
/*      */     {
/* 1308 */       return "";
/*      */     }
/* 1310 */     if (s.equals("GT"))
/*      */     {
/* 1312 */       return ">";
/*      */     }
/* 1314 */     if (s.equals("GE"))
/*      */     {
/* 1316 */       return ">=";
/*      */     }
/* 1318 */     if (s.equals("LT"))
/*      */     {
/* 1320 */       return "<";
/*      */     }
/* 1322 */     if (s.equals("LE"))
/*      */     {
/* 1324 */       return "<=";
/*      */     }
/* 1326 */     if (s.equals("EQ"))
/*      */     {
/* 1328 */       return "=";
/*      */     }
/* 1330 */     if (s.equals("NE"))
/*      */     {
/* 1332 */       return "<=";
/*      */     }
/* 1334 */     if (s.equals("CT"))
/*      */     {
/* 1336 */       return "contains";
/*      */     }
/* 1338 */     if (s.equals("DC"))
/*      */     {
/* 1340 */       return "does not contain";
/*      */     }
/* 1342 */     if (s.equals("QL"))
/*      */     {
/* 1344 */       return "equal to";
/*      */     }
/* 1346 */     if (s.equals("NQ"))
/*      */     {
/* 1348 */       return "not equal to";
/*      */     }
/* 1350 */     if (s.equals("SW"))
/*      */     {
/* 1352 */       return "starts with";
/*      */     }
/* 1354 */     if (s.equals("SW"))
/*      */     {
/* 1356 */       return "starts with";
/*      */     }
/*      */     
/*      */ 
/* 1360 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Connection getConnection()
/*      */     throws Exception
/*      */   {
/* 1371 */     DBParamsParser dbparamsparser = null;
/*      */     try {
/* 1373 */       Thread.sleep(3000L);
/* 1374 */       dbparamsparser = DBParamsParser.getInstance(new File("." + File.separator + "conf" + File.separator + "database_params.conf"));
/*      */     }
/*      */     catch (Exception exception) {
/* 1377 */       exception.printStackTrace();
/* 1378 */       return null;
/*      */     }
/* 1380 */     String s = dbparamsparser.getURL();
/* 1381 */     Object obj = null;
/* 1382 */     Object obj1 = null;
/*      */     try {
/* 1384 */       Driver driver = (Driver)Class.forName(dbparamsparser.getDriverName()).newInstance();
/* 1385 */       Properties properties = new Properties();
/* 1386 */       properties.put("user", dbparamsparser.getUserName());
/* 1387 */       if (dbparamsparser.getPassword() != null)
/*      */       {
/* 1389 */         properties.put("password", dbparamsparser.getPassword());
/*      */       }
/* 1391 */       return driver.connect(s, properties);
/*      */     }
/*      */     catch (Exception exception1) {}
/*      */     
/* 1395 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap<String, String> getHashMapfromResultSetRow(ResultSet resultset)
/*      */   {
/* 1406 */     return getHashMapfromResultSetRow(resultset, new ArrayList());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap<String, String> getHashMapfromResultSetRow(ResultSet resultset, ArrayList arraylist)
/*      */   {
/* 1417 */     HashMap<String, String> hashmap = new HashMap();
/* 1418 */     if (arraylist == null)
/*      */     {
/* 1420 */       arraylist = new ArrayList();
/*      */     }
/*      */     try
/*      */     {
/* 1424 */       ResultSetMetaData resultsetmetadata = resultset.getMetaData();
/* 1425 */       int i = resultsetmetadata.getColumnCount();
/* 1426 */       for (int j = 1; j <= i; j++)
/*      */       {
/* 1428 */         if (!arraylist.contains(resultset.getString(j)))
/*      */         {
/* 1430 */           hashmap.put(resultsetmetadata.getColumnName(j), resultset.getString(j));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception exception)
/*      */     {
/* 1436 */       exception.printStackTrace();
/*      */     }
/* 1438 */     return hashmap;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ThresholdConfigurationAsCSV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */