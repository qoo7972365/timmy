/*      */ package com.adventnet.appmanager.client.resourcemanagement;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.fault.RuleAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil;
/*      */ import com.adventnet.appmanager.server.dao.AMManagedObject;
/*      */ import com.adventnet.appmanager.server.discovery.DiscoveryUtil;
/*      */ import com.adventnet.appmanager.server.framework.AMScriptProcess;
/*      */ import com.adventnet.appmanager.server.framework.AMUrlMonitorProcess;
/*      */ import com.adventnet.appmanager.server.framework.AM_KeyValueDataCollector;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.DowntimeScheduleUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.MGUtil;
/*      */ import com.adventnet.appmanager.utils.client.BusinessViewUtil;
/*      */ import com.adventnet.management.scheduler.Scheduler;
/*      */ import java.sql.BatchUpdateException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.ResultSetMetaData;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.naming.Context;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import org.apache.commons.dbcp.BasicDataSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ManagedApplication
/*      */ {
/*   48 */   AMConnectionPool cp = null;
/*   49 */   DiscoveryUtil discUtil = new DiscoveryUtil();
/*   50 */   public static HashMap<String, ArrayList> attributesExt = ConfMonitorUtil.attributesExt;
/*      */   
/*      */   public ManagedApplication() {
/*   53 */     this.cp = AMConnectionPool.getInstance();
/*      */   }
/*      */   
/*      */   public ManagedApplication(AMConnectionPool cp) {
/*   57 */     this.cp = cp;
/*      */   }
/*      */   
/*      */   public AMManagedObject createManagedApplication(String applicationname, String description, String owner, String[] resources, String[] j2eeapps, boolean isSubGroup) throws Exception {
/*   61 */     int groupType = 1;
/*   62 */     if (isSubGroup)
/*      */     {
/*   64 */       groupType = 1;
/*      */     }
/*   66 */     return createManagedApplication(applicationname, description, owner, resources, j2eeapps, isSubGroup, groupType);
/*      */   }
/*      */   
/*      */   public AMManagedObject createManagedApplication(String applicationname, String resourcename, String description, String owner, String[] resources, String[] j2eeapps, boolean isSubGroup, int groupType)
/*      */     throws Exception
/*      */   {
/*   72 */     AMManagedObject ammo = MGUtil.createManagedApplication(applicationname, resourcename, description, owner, resources, j2eeapps, isSubGroup, groupType);
/*   73 */     BusinessViewUtil.deleteBussinessViewCache();
/*   74 */     return ammo;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AMManagedObject createManagedApplication(String applicationname, String description, String owner, String[] resources, String[] j2eeapps)
/*      */     throws Exception
/*      */   {
/*  140 */     return createManagedApplication(applicationname, description, owner, resources, j2eeapps, false);
/*      */   }
/*      */   
/*      */   public AMManagedObject createManagedApplication(String applicationname, String description, String owner, String[] resources, String[] j2eeapps, boolean isSubGroup, int groupType) throws Exception
/*      */   {
/*  145 */     return createManagedApplication(applicationname, null, description, owner, resources, j2eeapps, isSubGroup, groupType);
/*      */   }
/*      */   
/*      */   public static void main(String[] args) throws Exception {
/*  149 */     AMConnectionPool cp = AMConnectionPool.getNewConnectionPool("jdbc:mysql://localhost/RMEDB", "root", "public", "org.gjt.mm.mysql.Driver");
/*      */     
/*      */ 
/*      */ 
/*  153 */     Class.forName("org.gjt.mm.mysql.Driver");
/*  154 */     ManagedApplication m = new ManagedApplication(cp);
/*  155 */     String[] ar = { "1", "2", "3" };
/*      */   }
/*      */   
/*      */   public ArrayList getRowsFromTable(String tableName)
/*      */   {
/*  160 */     return getRows("select * from " + tableName);
/*      */   }
/*      */   
/*      */   public ArrayList getRows(String query) {
/*  164 */     return this.discUtil.getExistingResIds(query);
/*      */   }
/*      */   
/*      */   public ArrayList<ArrayList<String>> getRowsFromDB(String query) throws Exception
/*      */   {
/*  169 */     ArrayList<ArrayList<String>> list = new ArrayList();
/*  170 */     ResultSet rs = null;
/*      */     try {
/*  172 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  173 */       ResultSetMetaData metadata = rs.getMetaData();
/*  174 */       int columncount = metadata.getColumnCount();
/*  175 */       while (rs.next())
/*      */       {
/*  177 */         ArrayList<String> row = new ArrayList(columncount);
/*  178 */         for (int i = 1; i <= columncount; i++)
/*      */         {
/*  180 */           String name = rs.getString(i);
/*  181 */           row.add(name);
/*      */         }
/*  183 */         list.add(row);
/*      */       }
/*      */     }
/*      */     finally {
/*  187 */       if (rs != null) {
/*  188 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*  192 */     return list;
/*      */   }
/*      */   
/*      */   public ArrayList getRowsForSingleColumn(String query)
/*      */   {
/*  197 */     return DBUtil.getRowsForSingleColumn(query);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int deleteApplications(String[] applications)
/*      */     throws Exception
/*      */   {
/*  205 */     Connection con = null;
/*      */     
/*      */ 
/*  208 */     Statement ps = null;
/*      */     try
/*      */     {
/*  211 */       con = AMConnectionPool.getConnection();
/*      */       
/*      */ 
/*  214 */       ps = con.createStatement(1005, 1008);
/*      */       
/*      */ 
/*  217 */       for (int i = 0; (applications != null) && (i < applications.length); i++)
/*      */       {
/*  219 */         FaultUtil.deleteEventFromCache(applications[i]);
/*      */         
/*      */ 
/*  222 */         ps.addBatch("delete from AM_PARENTCHILDMAPPER where PARENTID=" + applications[i]);
/*  223 */         ps.addBatch("delete from AM_HOLISTICAPPLICATION where HAID=" + applications[i]);
/*  224 */         ps.addBatch("delete from AM_HOLISTICAPPLICATION_EXT where RESOURCEID=" + applications[i]);
/*  225 */         ps.addBatch("delete from AM_ManagedObject where RESOURCEID=" + applications[i]);
/*      */         
/*  227 */         ps.addBatch("delete from AM_RCAMAPPER where PARENTRESOURCEID=" + applications[i]);
/*      */         
/*  229 */         ps.addBatch("delete from AM_RCARULESMAPPER where RESOURCEID=" + applications[i]);
/*      */         
/*  231 */         ps.addBatch("delete from AM_ATTRIBUTEACTIONMAPPER where ID=" + applications[i]);
/*      */         
/*  233 */         ps.addBatch("delete from Alert where  SOURCE=" + applications[i]);
/*      */         
/*  235 */         ps.addBatch("delete from Event where SOURCE=" + applications[i]);
/*      */         
/*  237 */         ps.addBatch("delete from AM_ManagedObjectHistoryData where RESID=" + applications[i]);
/*  238 */         ps.addBatch("delete from AM_ManagedObjectData where RESID=" + applications[i]);
/*  239 */         ps.addBatch("delete from AM_MO_DowntimeData where RESID=" + applications[i]);
/*      */         
/*  241 */         ps.addBatch("delete from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + applications[i]);
/*  242 */         ps.addBatch("delete from AM_UnManagedNodes where resid=" + applications[i]);
/*      */         
/*  244 */         ps.addBatch("delete from AM_FLASHVIEW_COORDINATES where PARENTID=" + applications[i] + " or CHILDID=" + applications[i]);
/*  245 */         ps.addBatch("delete from AM_MONITORGROUP_FLASHVIEWCONFIG where HAID=" + applications[i]);
/*  246 */         ps.addBatch("delete from AM_FLASHVIEW_FILTER where HAID=" + applications[i]);
/*  247 */         ps.addBatch("delete from AM_TASKIDRESOURCEIDMAPPER where RESOURCEID=" + applications[i]);
/*  248 */         ps.addBatch("delete from AM_GMapCountryResourceRel where ID=" + applications[i]);
/*      */         
/*      */ 
/*  251 */         ps.addBatch("delete from AM_MYFIELDS_SYSTEMDATA WHERE RESOURCEID=" + applications[i]);
/*  252 */         ps.addBatch("delete from AM_MYFIELDS_USERDATA WHERE RESOURCEID=" + applications[i]);
/*  253 */         ps.addBatch("delete from AM_MYFIELDS_ENTITYDATA WHERE RESOURCEID=" + applications[i]);
/*  254 */         ps.addBatch("delete from AM_MYFIELDS_LABELDATA WHERE RESOURCEID=" + applications[i]);
/*      */         
/*  256 */         ps.addBatch("delete from AM_TABUSERMAPPING where TABID=" + applications[i]);
/*  257 */         ArrayList<String> list = DBUtil.getArchiveingTableNamesForResource(applications[i]);
/*  258 */         if (list != null)
/*      */         {
/*  260 */           for (String tableName : list)
/*      */           {
/*  262 */             ps.addBatch("delete from " + tableName + " where RESID=" + applications[i]);
/*      */           }
/*      */         }
/*      */         try {
/*  266 */           if (EnterpriseUtil.isAdminServer())
/*      */           {
/*  268 */             DowntimeScheduleUtil.updateMASSYNCHDETAILS(applications[i], "edit");
/*      */           }
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/*  273 */           ee.printStackTrace();
/*      */         }
/*      */         try {
/*  276 */           deleteMGDashboard(applications[i]);
/*  277 */           RuleAnalyser.removeAllMonitorGrpFromDependency(applications[i]);
/*  278 */           BusinessViewUtil.deleteBussinessViewCache();
/*      */         }
/*      */         catch (Exception ee)
/*      */         {
/*  282 */           ee.printStackTrace();
/*      */         }
/*      */         
/*  285 */         rs = null;
/*      */         try {
/*  287 */           String haiTypeQuery = "select GROUPTYPE from AM_HOLISTICAPPLICATION where HAID=" + applications[i] + "  and GROUPTYPE in (3,1009,1010,1012,1013,4)";
/*  288 */           rs = AMConnectionPool.executeQueryStmt(haiTypeQuery);
/*  289 */           if (rs.next())
/*      */           {
/*  291 */             int groupType = rs.getInt("GROUPTYPE");
/*  292 */             ps.addBatch("delete from AM_VMWARECLUSTER_METRICS  where RESOURCEID=" + applications[i]);
/*  293 */             ps.addBatch("delete from AM_VMWARERESOURCEPOOL_METRICS  where RESOURCEID=" + applications[i]);
/*  294 */             if (groupType == 3) {
/*  295 */               ps.addBatch("delete from AM_VCENTER_ARGS where RESID=" + applications[i]);
/*      */             }
/*  297 */             else if (groupType == 1013)
/*      */             {
/*  299 */               ps.addBatch("DELETE FROM AM_RESOURCEPOOL_ARGS WHERE RESOURCEID=" + applications[i]);
/*  300 */               ps.addBatch("DELETE FROM AM_RESOURCEPOOL_METRICS WHERE RESOURCEID=" + applications[i]);
/*      */             }
/*  302 */             else if (groupType == 4)
/*      */             {
/*  304 */               ps.addBatch("delete from AM_VMHORIZON_ARGS  where RESID=" + applications[i]);
/*      */             }
/*  306 */             if ((groupType == 3) || (groupType == 1013) || (groupType == 4))
/*      */             {
/*  308 */               AM_KeyValueDataCollector amkv = (AM_KeyValueDataCollector)AMScriptProcess.resid_instance.get(String.valueOf(applications[i]));
/*  309 */               Scheduler schedular = Scheduler.getScheduler("KeyValue_Monitor");
/*  310 */               if (amkv != null)
/*      */               {
/*  312 */                 schedular.removeTask(amkv);
/*  313 */                 amkv.setEnabled(false);
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  321 */           exc.printStackTrace();
/*      */         }
/*      */         finally {
/*  324 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */       }
/*  327 */       ps.executeBatch();
/*      */       
/*  329 */       return 1;
/*      */     }
/*      */     catch (BatchUpdateException bex) {
/*      */       ResultSet rs;
/*  333 */       updateCounts = bex.getUpdateCounts();
/*  334 */       AMLog.debug("Managed Application :BatchUpdateException: updateCounts =======>" + updateCounts);
/*  335 */       return bex.getErrorCode();
/*      */ 
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  340 */       se.printStackTrace();
/*  341 */       return se.getErrorCode();
/*      */     }
/*      */     catch (Exception e) {
/*      */       int[] updateCounts;
/*  345 */       e.printStackTrace();
/*      */       
/*  347 */       return 65132;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  354 */         if (ps != null) {
/*  355 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {
/*  359 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int updateManagedApplication(String applicationname, String description, String modifiedby, String[] resources)
/*      */     throws Exception
/*      */   {
/*  370 */     Connection con = null;
/*  371 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  374 */       con = AMConnectionPool.getConnection();
/*      */       
/*  376 */       ps = con.prepareStatement("insert into ManagedApplicationResources values(?,?,?)");
/*  377 */       ps.addBatch("delete from ManagedApplicationResources where NAME='" + applicationname + "'");
/*      */       
/*  379 */       String creationdate = new Date(System.currentTimeMillis()).toString();
/*  380 */       updatequery = "update ManagedApplications set ModifiedBy='" + modifiedby + "',MODIFIEDDATE =" + System.currentTimeMillis() + ",DISPLAYNAME='" + description + "' where NAME='" + applicationname + "'";
/*  381 */       ps.addBatch(updatequery);
/*      */       
/*  383 */       for (int i = 0; (resources != null) && (i < resources.length); i++)
/*      */       {
/*  385 */         ps.setString(1, applicationname);
/*  386 */         ps.setString(2, resources[i]);
/*  387 */         ps.setInt(3, 1);
/*  388 */         ps.addBatch();
/*      */       }
/*  390 */       ps.executeBatch();
/*      */       
/*  392 */       return 1;
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  396 */       con.rollback();
/*  397 */       se.printStackTrace();
/*  398 */       return se.getErrorCode();
/*      */     }
/*      */     catch (Exception e) {
/*      */       String updatequery;
/*  402 */       e.printStackTrace();
/*  403 */       con.rollback();
/*  404 */       return 65132;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  411 */         if (ps != null) {
/*  412 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {
/*  416 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public int updateManagedApplicationResourcesForEnterprise(String applicationname, String displayname, String[] resources, Vector vect)
/*      */     throws SQLException
/*      */   {
/*  424 */     return updateManagedApplicationResources(applicationname, displayname, resources, null, vect);
/*      */   }
/*      */   
/*      */   public int updateManagedApplicationResources(String applicationname, String displayname, String[] resources) throws SQLException
/*      */   {
/*  429 */     return updateManagedApplicationResources(applicationname, displayname, resources, null, null);
/*      */   }
/*      */   
/*      */   public int updateManagedApplicationResources(String applicationname, String displayname, String[] resourcesArg, String pollinterval) throws SQLException
/*      */   {
/*  434 */     return updateManagedApplicationResources(applicationname, displayname, resourcesArg, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int updateManagedApplicationResources(String applicationname, String displayname, String[] resourcesArg, String pollinterval, Vector vect)
/*      */     throws SQLException
/*      */   {
/*  443 */     int result = this.discUtil.updateManagedApplicationResources(applicationname, displayname, resourcesArg, pollinterval, vect);
/*  444 */     BusinessViewUtil.deleteBussinessViewCache();
/*  445 */     return result;
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
/*      */ 
/*      */   public int[] updateManagedApplicationResources(String[] applicationname, String displayname, String[] resourcesArg, String pollinterval, Vector vect)
/*      */     throws SQLException
/*      */   {
/*  481 */     int[] results = this.discUtil.updateManagedApplicationResources(applicationname, displayname, resourcesArg, pollinterval, vect);
/*  482 */     BusinessViewUtil.deleteBussinessViewCache();
/*  483 */     return results;
/*      */   }
/*      */   
/*      */ 
/*      */   public int updateManagedJ2EEApplications(String applicationname, String[] resources)
/*      */     throws SQLException
/*      */   {
/*  490 */     Connection con = null;
/*  491 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  494 */       con = AMConnectionPool.getConnection();
/*      */       
/*  496 */       ps = con.prepareStatement("insert into ManagedJ2EEApplications values(?,?)");
/*  497 */       for (int i = 0; (resources != null) && (i < resources.length); i++)
/*      */       {
/*  499 */         ps.setString(1, applicationname);
/*  500 */         ps.setInt(2, Integer.parseInt(resources[i]));
/*  501 */         ps.addBatch();
/*      */       }
/*  503 */       ps.executeBatch();
/*      */       
/*  505 */       return 1;
/*      */     }
/*      */     catch (SQLException se)
/*      */     {
/*  509 */       con.rollback();
/*  510 */       se.printStackTrace();
/*  511 */       return se.getErrorCode();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  515 */       e.printStackTrace();
/*  516 */       con.rollback();
/*  517 */       return 65132;
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  524 */         if (ps != null) {
/*  525 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ee) {
/*  529 */         ee.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public Hashtable getDistinctManagedObjects() {
/*  535 */     table = new Hashtable();
/*  536 */     String query = "select  name , type from ManagedObject where type <> 'Interface'";
/*  537 */     ResultSet set = null;
/*      */     try
/*      */     {
/*  540 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  542 */       while (set.next())
/*      */       {
/*  544 */         String name = set.getString(1);
/*  545 */         String type = set.getString(2);
/*  546 */         Vector v = (Vector)table.get(type);
/*  547 */         if (v == null)
/*      */         {
/*  549 */           v = new Vector();
/*  550 */           table.put(type, v);
/*      */         }
/*  552 */         v.add(name);
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
/*  569 */       return table;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  557 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  563 */         set.close();
/*      */       }
/*      */       catch (Exception exp) {
/*  566 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Properties getProxyProps(String user)
/*      */   {
/*  575 */     String query = "select * from AM_ProxyServerConfig where AMUSERNAME='" + user + "'";
/*  576 */     ResultSet set = null;
/*  577 */     props = null;
/*      */     try
/*      */     {
/*  580 */       set = AMConnectionPool.executeQueryStmt(query);
/*      */       
/*  582 */       if (set.next())
/*      */       {
/*  584 */         props = new Properties();
/*  585 */         props.setProperty("host", set.getString("PROXYHOST"));
/*  586 */         props.setProperty("port", String.valueOf(set.getInt("PROXYPORT")));
/*  587 */         props.setProperty("username", set.getString("PROXYUSERNAME"));
/*  588 */         props.setProperty("password", set.getString("PROXYPASSWORD"));
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
/*  605 */       return props;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  592 */       exp.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  599 */         AMConnectionPool.closeStatement(set);
/*      */       }
/*      */       catch (Exception exp) {
/*  602 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void checkAndInsertIntoProxyProps(Properties props)
/*      */     throws SQLException
/*      */   {
/*  611 */     String updateCredentials = props.getProperty("updateCredentials");
/*  612 */     String user = props.getProperty("user");
/*      */     
/*  614 */     String host = props.getProperty("host");
/*  615 */     int port = Integer.parseInt(props.getProperty("port"));
/*  616 */     String username = props.getProperty("username");
/*      */     
/*  618 */     String password = props.getProperty("password");
/*      */     
/*  620 */     Properties check = getProxyProps(user);
/*  621 */     String query = null;
/*  622 */     PreparedStatement ps = null;
/*      */     try
/*      */     {
/*  625 */       if (check != null)
/*      */       {
/*      */ 
/*  628 */         ps = AMConnectionPool.getConnection().prepareStatement("update AM_ProxyServerConfig set PROXYUSERNAME=?,PROXYPASSWORD=" + DBQueryUtil.encode(password) + ",PROXYHOST='" + host + "',PROXYPORT=" + port + " where AMUSERNAME='" + user + "'");
/*  629 */         ps.setString(1, username);
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*  638 */         ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_ProxyServerConfig values('" + user + "',?," + DBQueryUtil.encode(password) + ",'" + host + "'," + port + ",'HTTP')");
/*  639 */         ps.setString(1, username);
/*      */       }
/*  641 */       ps.executeUpdate();
/*  642 */       AMUrlMonitorProcess.initProxyServer(host, port);
/*      */     }
/*      */     catch (Exception exp) {
/*  645 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  649 */       if (ps != null) {
/*  650 */         ps.close();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void deleteMappingForParent(String id)
/*      */   {
/*  657 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try
/*      */     {
/*  660 */       String query = "delete from AM_PARENTCHILDMAPPER using AM_PARENTCHILDMAPPER,AM_EJB where AM_PARENTCHILDMAPPER.PARENTID=" + id + " and AM_PARENTCHILDMAPPER.CHILDID=AM_EJB.EJBID";
/*  661 */       String query1 = "delete from AM_PARENTCHILDMAPPER using AM_PARENTCHILDMAPPER,AM_WAR where AM_PARENTCHILDMAPPER.PARENTID=" + id + " and AM_PARENTCHILDMAPPER.CHILDID=AM_WAR.WARID";
/*  662 */       AMConnectionPool.executeUpdateStmt(query);
/*  663 */       AMConnectionPool.executeUpdateStmt(query1);
/*      */     }
/*      */     catch (Exception exp) {
/*  666 */       exp.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public int executeUpdateStmt(String querystatement)
/*      */   {
/*      */     try
/*      */     {
/*  674 */       return AMConnectionPool.executeUpdateStmt(querystatement);
/*      */     }
/*      */     catch (SQLException nmsstorage) {}
/*      */     
/*  678 */     return -2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getPropertiesList(String query)
/*      */   {
/*  688 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  689 */     ResultSet set = null;
/*  690 */     v = new ArrayList();
/*      */     try
/*      */     {
/*  693 */       set = AMConnectionPool.executeQueryStmt(query);
/*  694 */       ResultSetMetaData metadata = set.getMetaData();
/*  695 */       int columncount = metadata.getColumnCount();
/*  696 */       String[] columnNames = new String[columncount];
/*  697 */       int[] columnTypes = new int[columncount];
/*  698 */       for (int i = 1; i <= columnNames.length; i++)
/*      */       {
/*  700 */         columnNames[(i - 1)] = metadata.getColumnName(i);
/*  701 */         columnTypes[(i - 1)] = metadata.getColumnType(i);
/*      */       }
/*  703 */       while (set.next())
/*      */       {
/*  705 */         Properties row = new Properties();
/*  706 */         for (int i = 1; i <= columncount; i++)
/*      */         {
/*  708 */           int columntype = columnTypes[(i - 1)];
/*  709 */           if ((columntype == -5) || (columntype == 2) || (columntype == -6) || (columntype == 5) || (columntype == 4))
/*      */           {
/*  711 */             long value = set.getLong(i);
/*  712 */             row.put(columnNames[(i - 1)], new Long(value));
/*      */           }
/*      */           else
/*      */           {
/*  716 */             String name = set.getString(i);
/*  717 */             row.setProperty(columnNames[(i - 1)], name);
/*      */           }
/*      */         }
/*  720 */         v.add(row);
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
/*      */ 
/*      */ 
/*  741 */       return v;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  725 */       exp.printStackTrace();
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*  729 */       t.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  735 */         set.close();
/*      */       }
/*      */       catch (Exception exp) {
/*  738 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ArrayList getResultSetAsMap(String query)
/*      */   {
/*  745 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  746 */     ResultSet set = null;
/*  747 */     v = new ArrayList();
/*      */     try
/*      */     {
/*  750 */       set = AMConnectionPool.executeQueryStmt(query);
/*  751 */       ResultSetMetaData metadata = set.getMetaData();
/*  752 */       int columncount = metadata.getColumnCount();
/*  753 */       String[] columnNames = new String[columncount];
/*  754 */       int[] columnTypes = new int[columncount];
/*  755 */       for (int i = 1; i <= columnNames.length; i++)
/*      */       {
/*  757 */         columnNames[(i - 1)] = metadata.getColumnName(i);
/*  758 */         columnTypes[(i - 1)] = metadata.getColumnType(i);
/*      */       }
/*  760 */       while (set.next())
/*      */       {
/*  762 */         LinkedHashMap row = new LinkedHashMap();
/*  763 */         for (int i = 1; i <= columncount; i++)
/*      */         {
/*  765 */           int columntype = columnTypes[(i - 1)];
/*  766 */           if ((columntype == -5) || (columntype == 2) || (columntype == -6) || (columntype == 5) || (columntype == 4))
/*      */           {
/*  768 */             long value = set.getLong(i);
/*  769 */             row.put(columnNames[(i - 1)], new Long(value));
/*      */           }
/*  771 */           else if ((columntype == 8) || (columntype == 6)) {
/*  772 */             double value = set.getDouble(i);
/*  773 */             if (set.wasNull()) {
/*  774 */               row.put(columnNames[(i - 1)], "NULL");
/*      */             }
/*      */             else {
/*  777 */               row.put(columnNames[(i - 1)], new Double(value));
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  782 */             String name = set.getString(i);
/*  783 */             row.put(columnNames[(i - 1)], name);
/*      */           }
/*      */         }
/*  786 */         v.add(row);
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
/*      */ 
/*      */ 
/*      */ 
/*  808 */       return v;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/*  792 */       exp.printStackTrace();
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*  796 */       t.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  802 */         set.close();
/*      */       }
/*      */       catch (Exception exp) {
/*  805 */         exp.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isAddHostToHAEnabled()
/*      */   {
/*  814 */     boolean h2HAEnabled = false;
/*      */     try
/*      */     {
/*  817 */       AMConnectionPool.getInstance();ResultSet set = AMConnectionPool.executeQueryStmt("select VALUE from AM_GLOBALCONFIG where NAME='AddHostToHA'");
/*  818 */       if (set.first())
/*      */       {
/*  820 */         if (set.getString(1).equalsIgnoreCase("true"))
/*      */         {
/*  822 */           h2HAEnabled = true;
/*      */         }
/*      */       }
/*  825 */       set.close();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  829 */       e.printStackTrace();
/*      */     }
/*  831 */     return h2HAEnabled;
/*      */   }
/*      */   
/*      */   public Map getAMMOInfo(int resourceID) throws Exception
/*      */   {
/*  836 */     String qry = "SELECT AM_ManagedObject.RESOURCEID, AM_ManagedObject.RESOURCENAME, AM_ManagedObject.TYPE, AM_ManagedObject.DISPLAYNAME, DESCRIPTION, PROTOCOLVERSION, ManagedObject.POLLINTERVAL POLLINTERVAL,  InetService.PORTNO, InetService.TARGETNAME FROM AM_ManagedObject, InetService,ManagedObject where AM_ManagedObject.RESOURCEID=" + resourceID + " and AM_ManagedObject.RESOURCENAME=InetService.NAME and ManagedObject.NAME=AM_ManagedObject.RESOURCENAME";
/*      */     
/*      */ 
/*  839 */     Map ammon = new HashMap();
/*  840 */     ResultSet rs = AMConnectionPool.executeQueryStmt(qry);
/*  841 */     String type = "";
/*  842 */     while (rs.next())
/*      */     {
/*  844 */       ammon.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/*  845 */       type = rs.getString("TYPE");
/*  846 */       ammon.put("TYPE", type);
/*  847 */       ammon.put("DISPLAYNAME", rs.getString("DISPLAYNAME"));
/*  848 */       ammon.put("DESCRIPTION", rs.getString("DESCRIPTION"));
/*  849 */       ammon.put("PROTOCOLVERSION", rs.getString("PROTOCOLVERSION"));
/*  850 */       ammon.put("POLLINTERVAL", rs.getString("POLLINTERVAL"));
/*  851 */       ammon.put("TARGETNAME", rs.getString("TARGETNAME"));
/*  852 */       ammon.put("PORTNO", rs.getString("PORTNO"));
/*      */     }
/*  854 */     AMConnectionPool.closeStatement(rs);
/*      */     
/*  856 */     if (type.equalsIgnoreCase("SNMP"))
/*      */     {
/*  858 */       String snmpQuery = "select " + DBQueryUtil.decode("COMMUNITYSTRING") + " as communityString, VERSION,SNMPPORT,USERNAME,CONTEXTNAME,SECURITYLEVEL,AUTHPROTOCOL," + DBQueryUtil.decode("AUTHPASSWORD") + " as AUTHPASSWORD,PRIVPROTOCOL," + DBQueryUtil.decode("PRIVPASSWORD") + " as PRIVPASSWORD from AM_SNMP_EXT_INFO where resourceid=" + resourceID + "";
/*  859 */       rs = AMConnectionPool.executeQueryStmt(snmpQuery);
/*      */       
/*  861 */       String snmpVersion = "v1v2";
/*  862 */       while (rs.next())
/*      */       {
/*  864 */         snmpVersion = rs.getString("VERSION");
/*  865 */         Integer snmpPort = Integer.valueOf(rs.getInt("SNMPPORT"));
/*  866 */         ammon.put("snmpPort", snmpPort + "");
/*  867 */         ammon.put("snmpVersion", (snmpVersion != null) && (!snmpVersion.equals("")) ? snmpVersion.toLowerCase() : "v1v2");
/*      */         
/*      */ 
/*  870 */         String userName = rs.getString("USERNAME");
/*  871 */         ammon.put("snmpUserName", rs.getString("USERNAME"));
/*  872 */         ammon.put("snmpContextName", rs.getString("CONTEXTNAME"));
/*  873 */         String securityLevel = rs.getString("SECURITYLEVEL");
/*  874 */         ammon.put("snmpSecurityLevel", (securityLevel != null) && (!securityLevel.equals("")) ? securityLevel.toUpperCase() : "NOAUTHNOPRIV");
/*      */         
/*      */ 
/*  877 */         ammon.put("snmpAuthProtocol", rs.getString("AUTHPROTOCOL"));
/*  878 */         ammon.put("snmpAuthPassword", rs.getString("AUTHPASSWORD"));
/*      */         
/*      */ 
/*      */ 
/*  882 */         ammon.put("snmpPrivProtocol", rs.getString("PRIVPROTOCOL"));
/*  883 */         ammon.put("snmpPrivPassword", rs.getString("PRIVPASSWORD"));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  888 */         ammon.put("snmpCommunityString", rs.getString("COMMUNITYSTRING"));
/*      */       }
/*      */       
/*      */ 
/*  892 */       AMConnectionPool.closeStatement(rs);
/*  893 */     } else if (type.equalsIgnoreCase("JMX1.2-MX4J-RMI")) {
/*  894 */       qry = "SELECT JNDIURL,JMXURL FROM AM_MX4J_EXT_INFO where RESOURCEID =" + resourceID;
/*  895 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  896 */       if (rs.next())
/*      */       {
/*  898 */         ammon.put("JNDIPATH", rs.getString("JNDIURL"));
/*  899 */         ammon.put("JMXURL", rs.getString("JMXURL"));
/*      */       }
/*  901 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  903 */     qry = "select USERNAME," + DBQueryUtil.decodeBytes("PASSWORD") + " as PASSWORD from AM_RESOURCECONFIG where RESOURCEID=" + resourceID;
/*  904 */     rs = AMConnectionPool.executeQueryStmt(qry);
/*  905 */     while (rs.next())
/*      */     {
/*  907 */       ammon.put("USERNAME", rs.getString("USERNAME"));
/*  908 */       ammon.put("PASSWORD", new String(rs.getBytes("PASSWORD")));
/*      */     }
/*      */     
/*  911 */     return ammon;
/*      */   }
/*      */   
/*      */   private void updateDate(String haid) throws Exception
/*      */   {
/*  916 */     AMConnectionPool.executeUpdateStmt("update AM_HOLISTICAPPLICATION set modifieddate=" + System.currentTimeMillis() + " where haid=" + haid);
/*      */   }
/*      */   
/*      */   public void getReloadPeriod(HttpServletRequest request)
/*      */   {
/*  921 */     String resourceid = request.getParameter("resourceid");
/*  922 */     String resourcename = request.getParameter("resourcename");
/*  923 */     String resourcetype = request.getParameter("type");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  928 */     String uri = (String)request.getAttribute("uri");
/*      */     
/*  930 */     if (uri != null)
/*      */     {
/*  932 */       if ((uri.equals("/applications.do")) || (uri.equals("/fault/AlarmView.do")) || (uri.equals("/fault/AMAlarmView.do"))) {
/*  933 */         request.setAttribute("reloadperiod", "300");
/*  934 */         return;
/*      */       }
/*      */     }
/*  937 */     if (resourceid == null)
/*      */     {
/*  939 */       return;
/*      */     }
/*  941 */     String pollintervalquery = null;
/*  942 */     if ((resourcetype != null) && ((resourcetype.equals("Web Service")) || (resourcetype.equals("Script Monitor")) || (resourcetype.equals("MAIL-server")) || (resourcetype.equals("WEB-server")) || (resourcetype.equals("RMI")) || (resourcetype.equals("JMX1.2-MX4J-RMI")) || (resourcetype.equals("Custom-Application")) || (resourcetype.equals("SNMP"))))
/*      */     {
/*  944 */       pollintervalquery = "select ManagedObject.POLLINTERVAL from ManagedObject , AM_ManagedObject where ManagedObject.NAME=AM_ManagedObject.RESOURCENAME and AM_ManagedObject.RESOURCEID=" + resourceid;
/*  945 */       String pollinterval = "POLLINTERVAL";
/*  946 */       if (resourcetype.equals("Script Monitor"))
/*      */       {
/*  948 */         pollintervalquery = "select POLLINTERVAL from AM_ScriptArgs where resourceid=" + resourceid;
/*  949 */         pollinterval = "POLLINTERVAL";
/*      */       }
/*  951 */       else if (resourcetype.equals("Web Service"))
/*      */       {
/*  953 */         pollintervalquery = "select POLLINTERVAL from AM_WSM_Config where resourceid=" + resourceid;
/*  954 */         pollinterval = "POLLINTERVAL";
/*      */       }
/*  956 */       ArrayList rows = getPropertiesList(pollintervalquery);
/*  957 */       if (rows.size() > 0)
/*      */       {
/*  959 */         Properties props = (Properties)rows.get(0);
/*  960 */         String reloadperiod = props.get(pollinterval).toString();
/*  961 */         if (reloadperiod != null)
/*      */         {
/*  963 */           if (!resourcetype.equals("Web Service")) {
/*  964 */             request.setAttribute("reloadperiod", reloadperiod);
/*      */           }
/*      */           else {
/*  967 */             request.setAttribute("reloadperiod", Integer.valueOf(Integer.parseInt(reloadperiod) * 60));
/*      */           }
/*  969 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  975 */     pollintervalquery = "select POLLINTERVAL FROM CollectData,AM_ManagedObject where AM_ManagedObject.resourcename=CollectData.RESOURCENAME and AM_ManagedObject.resourceid=" + resourceid;
/*  976 */     ArrayList rows = getPropertiesList(pollintervalquery);
/*  977 */     if (rows.size() > 0)
/*      */     {
/*  979 */       Properties props = (Properties)rows.get(0);
/*  980 */       String reloadperiod = props.get("POLLINTERVAL").toString();
/*  981 */       if (reloadperiod != null)
/*      */       {
/*  983 */         request.setAttribute("reloadperiod", reloadperiod);
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*  988 */     else if ((resourcetype != null) && ((resourcetype.equals("UrlMonitor")) || (resourcetype.equals("UrlEle"))))
/*      */     {
/*  990 */       pollintervalquery = "select POLLINTERVAL FROM AM_URL where urlid = " + resourceid;
/*  991 */       rows = getPropertiesList(pollintervalquery);
/*  992 */       if (rows.size() > 0)
/*      */       {
/*  994 */         Properties props = (Properties)rows.get(0);
/*  995 */         String reloadperiod = props.get("POLLINTERVAL").toString();
/*  996 */         if (reloadperiod != null)
/*      */         {
/*      */           try
/*      */           {
/*      */ 
/* 1001 */             int pollinterval = Integer.parseInt(reloadperiod);
/* 1002 */             pollinterval /= 1000;
/* 1003 */             request.setAttribute("reloadperiod", String.valueOf(pollinterval));
/*      */           }
/*      */           catch (NumberFormatException ne) {}
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
/*      */ 
/*      */   public void getParentMGs(Vector MOlist, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/* 1021 */       AMConnectionPool.getInstance();ResultSet hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.PARENTID from AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI' and AM_PARENTCHILDMAPPER.CHILDID=" + resourceid);
/*      */       
/* 1023 */       if (hasChildrenRes.next())
/*      */       {
/* 1025 */         String chResId = hasChildrenRes.getString(1);
/* 1026 */         MOlist.add(chResId);
/* 1027 */         hasChildrenRes.close();
/* 1028 */         getParentMGs(MOlist, chResId);
/*      */       }
/*      */       else
/*      */       {
/* 1032 */         hasChildrenRes.close();
/* 1033 */         return;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1038 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public ArrayList getBreadCrumbForMO(String resourceid)
/*      */   {
/* 1044 */     breadcrumb = new ArrayList();
/* 1045 */     ResultSet rs = null;
/* 1046 */     ResultSet rs1 = null;
/*      */     
/*      */     try
/*      */     {
/* 1050 */       int subGroupLevels = Constants.getSubGroupLevels();
/* 1051 */       String applev = "";
/* 1052 */       String appjoint = "";
/* 1053 */       for (int i = 8; i <= subGroupLevels + 1; i++)
/*      */       {
/* 1055 */         applev = "" + applev + ", t" + i + ".PARENTID as lev" + i + "";
/* 1056 */         appjoint = "" + appjoint + " LEFT JOIN AM_PARENTCHILDMAPPER AS t" + i + " ON t" + i + ".CHILDID = t" + (i - 1) + ".PARENTID";
/*      */       }
/* 1058 */       String querysubgroup = "SELECT  t1.CHILDID,t1.PARENTID AS lev1, t2.PARENTID as lev2, t3.PARENTID as lev3, t4.PARENTID as lev4,t5.PARENTID as lev5,t6.PARENTID as lev6, t7.PARENTID as lev7 " + applev + " FROM AM_PARENTCHILDMAPPER AS t1 LEFT JOIN AM_PARENTCHILDMAPPER AS t2 ON t2.CHILDID = t1.PARENTID LEFT JOIN AM_PARENTCHILDMAPPER AS t3 ON t3.CHILDID = t2.PARENTID LEFT JOIN AM_PARENTCHILDMAPPER AS t4 ON t4.CHILDID = t3.PARENTID LEFT JOIN AM_PARENTCHILDMAPPER AS t5 ON t5.CHILDID = t4.PARENTID LEFT JOIN AM_PARENTCHILDMAPPER AS t6 ON t6.CHILDID = t5.PARENTID LEFT JOIN AM_PARENTCHILDMAPPER AS t7 ON t7.CHILDID = t6.PARENTID " + appjoint + " WHERE t1.CHILDID =" + resourceid + " ;";
/* 1059 */       rs = AMConnectionPool.executeQueryStmt(querysubgroup);
/* 1060 */       ResultSetMetaData metadata = rs.getMetaData();
/* 1061 */       int columncount = metadata.getColumnCount();
/* 1062 */       ArrayList subtree = new ArrayList(columncount);
/* 1063 */       if (rs.next())
/*      */       {
/* 1065 */         for (int i = columncount; i >= 2; i--)
/*      */         {
/* 1067 */           if (rs.getString(i) != null)
/*      */           {
/* 1069 */             subtree.add(rs.getString(i));
/*      */           }
/*      */         }
/*      */       }
/* 1073 */       String displaynameQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where " + getCondition("RESOURCEID", subtree);
/* 1074 */       rs1 = AMConnectionPool.executeQueryStmt(displaynameQuery);
/* 1075 */       while (rs1.next())
/*      */       {
/* 1077 */         String name = rs1.getString("DISPLAYNAME");
/* 1078 */         String resid = rs1.getString("RESOURCEID");
/* 1079 */         int index = subtree.indexOf(resid);
/* 1080 */         HashMap moinfo = new HashMap(5);
/* 1081 */         moinfo.put("resourceid", resid);
/* 1082 */         moinfo.put("displayname", name);
/* 1083 */         subtree.set(index, moinfo);
/*      */       }
/* 1085 */       return subtree;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1090 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1096 */         rs.close();
/* 1097 */         rs1.close();
/*      */       }
/*      */       catch (SQLException ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getTOPLevelMG(String resourceid)
/*      */   {
/* 1107 */     String toreturn = resourceid;
/*      */     try
/*      */     {
/* 1110 */       AMConnectionPool.getInstance();ResultSet hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.PARENTID from AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI' and AM_PARENTCHILDMAPPER.CHILDID=" + resourceid);
/*      */       
/* 1112 */       if (hasChildrenRes.next())
/*      */       {
/* 1114 */         String chResId = hasChildrenRes.getString(1);
/*      */         
/* 1116 */         hasChildrenRes.close();
/* 1117 */         toreturn = getTOPLevelMG(chResId);
/*      */       }
/*      */       else
/*      */       {
/* 1121 */         hasChildrenRes.close();
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1126 */       ex.printStackTrace();
/* 1127 */       return resourceid;
/*      */     }
/* 1129 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void getChildIDs(Vector toreturn, String resourceid)
/*      */   {
/* 1135 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 1136 */     ResultSet hasChildrenRes = null;
/*      */     try
/*      */     {
/* 1139 */       hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.CHILDID  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1");
/* 1140 */       while (hasChildrenRes.next())
/*      */       {
/* 1142 */         String chResId = hasChildrenRes.getString(1);
/* 1143 */         toreturn.add(chResId);
/* 1144 */         getChildIDs(toreturn, chResId);
/*      */       }
/*      */       return;
/*      */     }
/*      */     catch (Exception ex) {
/* 1149 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*      */       try {
/* 1153 */         if (hasChildrenRes != null)
/*      */         {
/* 1155 */           hasChildrenRes.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 1158 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void getMGtree(Vector toreturn, Vector treelist, String resourceid, String tree)
/*      */   {
/*      */     try {
/* 1166 */       String query = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.DISPLAYNAME  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1";
/* 1167 */       ArrayList templist = getRows(query);
/*      */       
/* 1169 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 1171 */         ArrayList singlerow = (ArrayList)templist.get(i);
/* 1172 */         String chResId = (String)singlerow.get(0);
/* 1173 */         String displayname = (String)singlerow.get(1);
/* 1174 */         toreturn.add(chResId);
/* 1175 */         treelist.add(tree + displayname + "->");
/* 1176 */         getMGtree(toreturn, treelist, chResId, tree + displayname + "->");
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1181 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getParentMGResourceid(String resourceid)
/*      */   {
/* 1189 */     String toreturn = null;
/*      */     try
/*      */     {
/* 1192 */       AMConnectionPool.getInstance();ResultSet hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.PARENTID from AM_PARENTCHILDMAPPER,AM_ManagedObject  where AM_PARENTCHILDMAPPER.PARENTID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI' and AM_PARENTCHILDMAPPER.CHILDID=" + resourceid);
/* 1193 */       if (hasChildrenRes.next())
/*      */       {
/* 1195 */         toreturn = hasChildrenRes.getString(1);
/* 1196 */         hasChildrenRes.close();
/* 1197 */         return toreturn;
/*      */       }
/*      */       
/*      */ 
/* 1201 */       hasChildrenRes.close();
/* 1202 */       return null;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1207 */       ex.printStackTrace();
/*      */     }
/* 1209 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isSubGroup(String resourceid)
/*      */   {
/* 1216 */     ResultSet result = null;
/*      */     try
/*      */     {
/* 1219 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("select TYPE from AM_HOLISTICAPPLICATION where HAID=" + resourceid);
/* 1220 */       while (result.next())
/*      */       {
/* 1222 */         int temp = result.getInt("TYPE");
/* 1223 */         if (temp == 1)
/*      */         {
/* 1225 */           return true;
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
/*      */ 
/*      */ 
/*      */ 
/* 1248 */       return false;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1231 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1237 */         if (result != null)
/*      */         {
/* 1239 */           result.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
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
/*      */   public boolean isGroupContainsSubGroup(String resourceid)
/*      */   {
/* 1255 */     ResultSet result = null;
/*      */     try
/*      */     {
/* 1258 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("select TYPE from AM_ManagedObject,AM_PARENTCHILDMAPPER where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid + " and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE='HAI'");
/* 1259 */       if (result.next())
/*      */       {
/* 1261 */         return true;
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
/*      */ 
/*      */ 
/*      */ 
/* 1283 */       return false;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1266 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1272 */         if (result != null)
/*      */         {
/* 1274 */           result.close();
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getUserID(HttpServletRequest request)
/*      */   {
/* 1287 */     String userid = "-1";
/*      */     
/*      */     try
/*      */     {
/* 1291 */       if ((request != null) && (Constants.isUserResourceEnabled())) {
/* 1292 */         userid = Constants.getLoginUserid(request);
/* 1293 */         if (userid != null) {
/* 1294 */           return userid;
/*      */         }
/*      */       }
/* 1297 */       String query = "select USERID from AM_UserPasswordTable where USERNAME='" + request.getRemoteUser() + "'";
/* 1298 */       AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 1299 */       if (rs.next())
/*      */       {
/* 1301 */         userid = rs.getString("USERID");
/*      */       }
/* 1303 */       rs.close();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1307 */       ex.printStackTrace();
/*      */     }
/* 1309 */     return userid;
/*      */   }
/*      */   
/*      */ 
/*      */   public void getAllChildsinTree(Vector toreturn, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/* 1317 */       String query = "select AM_PARENTCHILDMAPPER.CHILDID,AM_ManagedObject.TYPE  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_PARENTCHILDMAPPER.PARENTID=" + resourceid;
/* 1318 */       ArrayList templist = getRows(query);
/* 1319 */       for (int i = 0; i < templist.size(); i++)
/*      */       {
/* 1321 */         ArrayList singlerow = (ArrayList)templist.get(i);
/* 1322 */         String chResId = (String)singlerow.get(0);
/* 1323 */         String type = (String)singlerow.get(1);
/* 1324 */         toreturn.add(chResId);
/* 1325 */         if (type.equals("HAI"))
/* 1326 */           getAllChildsinTree(toreturn, chResId);
/*      */       }
/*      */     } catch (Exception ex) {
/* 1329 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getCondition(String column, Vector v)
/*      */   {
/* 1335 */     StringBuffer sb = new StringBuffer(column + " in (");
/* 1336 */     if (v.size() > 0)
/*      */     {
/* 1338 */       for (int i = 0; i < v.size(); i++)
/*      */       {
/* 1340 */         if (i + 1 == v.size())
/*      */         {
/* 1342 */           sb.append(v.get(i) + ")");
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           sb.append(v.get(i) + ",");
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     } else {
/* 1352 */       sb.append("-10)");
/*      */     }
/* 1354 */     return sb.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getStringCondition(String column, Vector v)
/*      */   {
/* 1360 */     StringBuffer sb = new StringBuffer(column + " in (");
/* 1361 */     if (v.size() > 0)
/*      */     {
/* 1363 */       for (int i = 0; i < v.size(); i++)
/*      */       {
/* 1365 */         if (i + 1 == v.size())
/*      */         {
/* 1367 */           sb.append("'" + v.get(i) + "')");
/*      */         }
/*      */         else
/*      */         {
/* 1371 */           sb.append("'" + v.get(i) + "',");
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */     } else {
/* 1377 */       sb.append("-10)");
/*      */     }
/* 1379 */     return sb.toString();
/*      */   }
/*      */   
/*      */   public ArrayList<String> getCachedAttributeDetails(String attributeid)
/*      */   {
/* 1384 */     return ConfMonitorUtil.getCachedAttributeDetails(attributeid);
/*      */   }
/*      */   
/*      */   public boolean getAttrbuteExtEntries(ArrayList<String> attributeids) {
/* 1388 */     return ConfMonitorUtil.getAttrbuteExtEntries(attributeids);
/*      */   }
/*      */   
/*      */   public static String getCondition(String column, ArrayList<String> v, boolean isVarcharColumn)
/*      */   {
/* 1393 */     return ConfMonitorUtil.getCondition(column, v, isVarcharColumn);
/*      */   }
/*      */   
/*      */   public static String getCondition(String column, ArrayList<String> v) {
/* 1397 */     return ConfMonitorUtil.getCondition(column, v, false);
/*      */   }
/*      */   
/*      */   public static String findAndReplaceAll(String str, String find, String replace)
/*      */   {
/* 1402 */     StringBuffer des = new StringBuffer("");
/* 1403 */     while (str.indexOf(find) != -1)
/*      */     {
/* 1405 */       des.append(str.substring(0, str.indexOf(find)));
/* 1406 */       des.append(replace);
/* 1407 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/* 1409 */     des.append(str);
/* 1410 */     return des.toString();
/*      */   }
/*      */   
/*      */   public static void refreshDBConection() {
/* 1414 */     AMLog.debug("test1:in to refreshDBConection1");
/*      */     try
/*      */     {
/* 1417 */       Properties props = new Properties();
/*      */       
/* 1419 */       props.setProperty("java.naming.factory.initial", "org.apache.naming.java.javaURLContextFactory");
/*      */       
/* 1421 */       props.setProperty("java.naming.factory.url.pkgs", "org.apache.naming");
/*      */       
/*      */ 
/* 1424 */       AMLog.debug("test1:in to refreshDBConection2");
/* 1425 */       Context initContext = new InitialContext(props);
/* 1426 */       Context envContext = (Context)initContext.lookup("java:/comp/env");
/* 1427 */       BasicDataSource ds = (BasicDataSource)envContext.lookup("jdbc/AMDS");
/* 1428 */       ds.close();
/* 1429 */       AMLog.debug("test1:in to refreshDBConection3");
/* 1430 */       if (ds == null) {
/* 1431 */         throw new Exception("Data source not found!");
/*      */       }
/*      */       
/*      */ 
/* 1435 */       Connection conn = ds.getConnection();
/* 1436 */       conn.close();
/* 1437 */       AMLog.debug("test1:Data source found---->" + ds);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1442 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static HashMap<String, String> getDependentMonitorInfo(String resourceid)
/*      */   {
/* 1449 */     ResultSet result = null;
/* 1450 */     HashMap<String, String> dependentDeviceInfo = null;
/*      */     try
/*      */     {
/* 1453 */       result = AMConnectionPool.executeQueryStmt("SELECT PARENTID,SUPPRESS_ALERTS FROM AM_DEPENDENTMONITOR WHERE CHILDID=" + resourceid);
/* 1454 */       while (result.next())
/*      */       {
/* 1456 */         dependentDeviceInfo = new HashMap();
/* 1457 */         dependentDeviceInfo.put("resourceId", result.getString("PARENTID"));
/* 1458 */         dependentDeviceInfo.put("suppressAlerts", result.getString("SUPPRESS_ALERTS"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1463 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1467 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1469 */     return dependentDeviceInfo;
/*      */   }
/*      */   
/*      */   public static int getMOCountInMG(String haid)
/*      */   {
/* 1474 */     ResultSet result = null;
/*      */     try
/*      */     {
/* 1477 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("SELECT COUNT(*) FROM AM_PARENTCHILDMAPPER WHERE PARENTID=" + haid);
/* 1478 */       if (result.next())
/*      */       {
/* 1480 */         return result.getInt(1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1486 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1490 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1492 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void deleteMGDashboard(String mgID)
/*      */   {
/* 1499 */     AMConnectionPool pool = new AMConnectionPool();
/* 1500 */     ResultSet rs = null;
/* 1501 */     Statement stmt = null;
/* 1502 */     String dashboardQuery = "select PAGEID from AM_MYPAGE_TEMPLATE_MGS where RESOURCEID=" + mgID;
/*      */     try {
/* 1504 */       stmt = AMConnectionPool.getConnection().createStatement();
/* 1505 */       rs = AMConnectionPool.executeQueryStmt(dashboardQuery);
/* 1506 */       while (rs.next())
/*      */       {
/* 1508 */         String pageid = rs.getString("PAGEID");
/* 1509 */         String associatedResTypeDelQuery = "DELETE FROM AM_WIDGETS_ASSOCIATEDTYPES WHERE WIDGETID IN (SELECT AM_MYPAGE_WIDGETS.WIDGETID FROM AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGETS WHERE AM_MYPAGE_WIDGET_MAPPING.PAGEID='" + pageid + "' AND AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID)";
/*      */         
/* 1511 */         String dbType = DBQueryUtil.getDBType();
/* 1512 */         String widgetDeleteQuery = "";
/* 1513 */         String widgetAttributesQuery = "";
/* 1514 */         String widgetMonitorsQuery = "";
/* 1515 */         if (dbType.equalsIgnoreCase("pgsql"))
/*      */         {
/* 1517 */           widgetDeleteQuery = "delete from only AM_MYPAGE_WIDGETS using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1518 */           widgetAttributesQuery = "delete from ONLY AM_MYPAGE_WIDGET_ATTRIBUTES using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_ATTRIBUTES.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1519 */           widgetMonitorsQuery = "delete from only AM_MYPAGE_WIDGET_MONITORS using AM_MYPAGE_WIDGET_MAPPING where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_MONITORS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/*      */         }
/*      */         else
/*      */         {
/* 1523 */           widgetDeleteQuery = "delete AM_MYPAGE_WIDGETS from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGETS where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGETS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1524 */           widgetAttributesQuery = "delete AM_MYPAGE_WIDGET_ATTRIBUTES from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGET_ATTRIBUTES where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_ATTRIBUTES.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/* 1525 */           widgetMonitorsQuery = "delete AM_MYPAGE_WIDGET_MONITORS from AM_MYPAGE_WIDGET_MAPPING,AM_MYPAGE_WIDGET_MONITORS where AM_MYPAGE_WIDGET_MAPPING.PAGEID=" + pageid + " and AM_MYPAGE_WIDGET_MONITORS.WIDGETID=AM_MYPAGE_WIDGET_MAPPING.WIDGETID";
/*      */         }
/* 1527 */         String deletefromwidgetmapping = "delete from AM_MYPAGE_WIDGET_MAPPING where PAGEID=" + pageid;
/* 1528 */         String layoutDeleteQuery = "delete from AM_MYPAGE_LAYOUT where PAGEID=" + pageid;
/* 1529 */         String deletefromDashBoards = "delete from AM_DASHBOARDS where PAGEID=" + pageid;
/* 1530 */         String pagekeysdeleteQuery = "delete from AM_MYPAGE_PUBLISHKEYS where PAGEID=" + pageid;
/* 1531 */         String templatemgDeletQuery = "delete from  AM_MYPAGE_TEMPLATE_MGS where PAGEID=" + pageid;
/* 1532 */         String pageDeleteQuery = "delete from AM_MYPAGES where PAGEID=" + pageid;
/* 1533 */         stmt.addBatch(associatedResTypeDelQuery);
/* 1534 */         stmt.addBatch(widgetDeleteQuery);
/* 1535 */         stmt.addBatch(widgetAttributesQuery);
/* 1536 */         stmt.addBatch(widgetMonitorsQuery);
/* 1537 */         stmt.addBatch(layoutDeleteQuery);
/* 1538 */         stmt.addBatch(deletefromDashBoards);
/* 1539 */         stmt.addBatch(deletefromwidgetmapping);
/* 1540 */         stmt.addBatch(pagekeysdeleteQuery);
/* 1541 */         stmt.addBatch(templatemgDeletQuery);
/* 1542 */         stmt.addBatch(pageDeleteQuery);
/*      */       }
/* 1544 */       stmt.executeBatch();
/* 1545 */       stmt.clearBatch();
/* 1546 */       stmt.close(); return;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1550 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 1555 */         stmt.close();
/*      */       }
/*      */       catch (Exception ex) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getMGsCount(String groupType)
/*      */   {
/* 1569 */     ResultSet result = null;
/*      */     try
/*      */     {
/* 1572 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt("SELECT COUNT(*) FROM AM_HOLISTICAPPLICATION WHERE GROUPTYPE=" + groupType);
/* 1573 */       if (result.next())
/*      */       {
/* 1575 */         return result.getInt(1);
/*      */       }
/*      */       
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1581 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1585 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1587 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */   public static int getMGsCountForOwner(String groupType, HttpServletRequest request)
/*      */   {
/* 1593 */     ResultSet result = null;
/*      */     try
/*      */     {
/* 1596 */       String query = null;
/* 1597 */       String owner; if (Constants.isSsoEnabled()) {
/* 1598 */         String loginUserid = Constants.getLoginUserid(request);
/* 1599 */         query = "SELECT COUNT(HA.HAID) FROM AM_HOLISTICAPPLICATION as HA,AM_USERRESOURCESTABLE WHERE HA.GROUPTYPE=" + groupType + " and AM_USERRESOURCESTABLE.RESOURCEID=HA.HAID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */       } else {
/* 1601 */         owner = request.getRemoteUser();
/* 1602 */         query = "SELECT COUNT(HA.HAID) FROM AM_HOLISTICAPPLICATION as HA,AM_HOLISTICAPPLICATION_OWNERS as HAO,AM_UserPasswordTable as UPT WHERE HA.GROUPTYPE=" + groupType + " and HA.HAID=HAO.HAID and HAO.OWNERID=UPT.USERID and UPT.USERNAME='" + owner + "'";
/*      */       }
/* 1604 */       AMConnectionPool.getInstance();result = AMConnectionPool.executeQueryStmt(query);
/* 1605 */       if (result.next())
/*      */       {
/* 1607 */         return result.getInt(1);
/*      */       }
/*      */     }
/*      */     catch (SQLException e)
/*      */     {
/* 1612 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1616 */       AMConnectionPool.closeStatement(result);
/*      */     }
/* 1618 */     return 0;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\client\resourcemanagement\ManagedApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */