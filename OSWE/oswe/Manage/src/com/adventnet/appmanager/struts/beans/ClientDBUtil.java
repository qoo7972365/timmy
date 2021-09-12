/*      */ package com.adventnet.appmanager.struts.beans;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.client.views.ViewsCreator;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.AMServerFramework;
/*      */ import com.adventnet.appmanager.struts.actions.ShowResourceDetails;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*      */ import com.adventnet.appmanager.util.SqlJobActionUtil;
/*      */ import com.adventnet.appmanager.util.WinServiceActionUtil;
/*      */ import com.adventnet.appmanager.utils.client.CommonAPIUtil;
/*      */ import com.adventnet.tools.prevalent.Wield;
/*      */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
/*      */ import com.me.apm.fault.actions.util.ActionsUtil;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.Method;
/*      */ import java.sql.Connection;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
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
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClientDBUtil
/*      */ {
/*      */   public static HashMap getSystemHealthPollInfoForService(String resourceid, long poll)
/*      */   {
/*   61 */     String oldquery = "select AM_ManagedObject.RESOURCENAME , InetService.TARGETNAME , InetService.TARGETADDRESS , CollectData.RESOURCETYPE ,CollectData.POLLINTERVAL , max(COLLECTIONTIME) , COALESCE(Alert.SEVERITY,-1) , AM_ATTRIBUTES.ATTRIBUTEID,portno from AM_ManagedObject , AM_ATTRIBUTES  left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST'  left outer join Alert on Alert.SOURCE=" + resourceid + "  and Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID  left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=" + resourceid + " where AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_ATTRIBUTES.ATTRIBUTE='Health' group by AM_ManagedObject.RESOURCENAME,InetService.TARGETNAME,InetService.TARGETADDRESS,CollectData.RESOURCETYPE,CollectData.POLLINGINTERVAL,AM_ATTRIBUTES.ATTRIBUTEID,portno";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   70 */     String query = "select AM_ManagedObject.RESOURCENAME , InetService.TARGETNAME , InetService.TARGETADDRESS , CollectData.RESOURCETYPE ,CollectData.POLLINTERVAL , COLLECTIONTIME , Alert.SEVERITY , AM_ATTRIBUTES.ATTRIBUTEID,portno from AM_ManagedObject join AM_ATTRIBUTES  on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ATTRIBUTES.ATTRIBUTE='Health' left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST'  left outer join Alert on Alert.SOURCE='" + resourceid + "' and Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID  left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=" + resourceid + " where AM_ManagedObject.RESOURCEID=" + resourceid + " order by COLLECTIONTIME desc";
/*      */     
/*      */ 
/*   73 */     if (DBQueryUtil.isPgsql())
/*      */     {
/*   75 */       query = "select AM_ManagedObject.RESOURCENAME , InetService.TARGETNAME , InetService.TARGETADDRESS , CollectData.RESOURCETYPE ,CollectData.POLLINTERVAL , COLLECTIONTIME , Alert.SEVERITY , AM_ATTRIBUTES.ATTRIBUTEID,portno from AM_ManagedObject join AM_ATTRIBUTES  on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE and AM_ATTRIBUTES.ATTRIBUTE='Health' left outer join InetService on InetService.NAME=AM_ManagedObject.RESOURCENAME left outer join CollectData on CollectData.TARGETADDRESS=InetService.TARGETADDRESS and CollectData.COMPONENTNAME='HOST'  left outer join Alert on Alert.SOURCE='" + resourceid + "' and Alert.CATEGORY=" + DBQueryUtil.castasVarchar("AM_ATTRIBUTES.ATTRIBUTEID") + " left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=" + resourceid + " where AM_ManagedObject.RESOURCEID=" + resourceid + " order by COLLECTIONTIME desc";
/*      */     }
/*   77 */     FormatUtil.printQueryChange("ClientDBUtil.java", oldquery, query);
/*   78 */     HashMap map = new HashMap();
/*   79 */     ResultSet rs = null;
/*      */     try {
/*   81 */       rs = AMConnectionPool.executeQueryStmt(query);
/*   82 */       ResultSet rs1; if (rs.next())
/*      */       {
/*   84 */         map.put("HOSTNAME", rs.getString(2));
/*   85 */         map.put("HOSTIP", rs.getString(3));
/*   86 */         if (rs.getString(4) != null) {
/*   87 */           if ((rs.getString(4).equals("Node")) || (rs.getString(4).equals("Snmp-node")) || (rs.getString(4).equals("null")) || (rs.getString(4).equals("NULL")))
/*      */           {
/*      */ 
/*      */ 
/*   91 */             map.put("HOSTOS", "Unknown");
/*      */           } else {
/*   93 */             map.put("HOSTOS", rs.getString(4));
/*      */           }
/*      */         } else {
/*   96 */           map.put("HOSTOS", "Unknown");
/*      */         }
/*   98 */         if (rs.getLong(6) != 0L) {
/*   99 */           if (rs.getLong(6) > AMServerFramework.serverStartupTime) {
/*  100 */             map.put("LASTDC", new Long(rs.getLong(6)));
/*  101 */             map.put("NEXTDC", new Long(rs.getLong(6) + poll * 1000L));
/*      */           }
/*      */           else {
/*  104 */             map.put("LASTDC", new Long(rs.getLong(6)));
/*  105 */             map.put("NEXTDC", new Long(AMServerFramework.serverStartupTime + rs.getLong(5) * 1000L));
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  112 */           map.put("NEXTDC", new Long(System.currentTimeMillis() + rs.getLong(5) * 1000L));
/*      */         }
/*      */         
/*      */ 
/*  116 */         if (rs.getString(7) != null)
/*      */         {
/*  118 */           map.put("HEALTH", rs.getString(7));
/*      */         }
/*      */         else
/*      */         {
/*  122 */           map.put("HEALTH", "-1");
/*      */         }
/*  124 */         map.put("HEALTHID", rs.getString(8));
/*  125 */         map.put("PORTNO", rs.getString(9));
/*      */         
/*      */         try
/*      */         {
/*  129 */           AMConnectionPool.closeStatement(rs);
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  133 */           exc.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  137 */         rs1 = null;
/*      */         try
/*      */         {
/*  140 */           String query1 = "select RESOURCEID from AM_ManagedObject where RESOURCENAME ='" + map.get("HOSTNAME") + "' and (TYPE in " + Constants.allServerTypes + " or TYPE = 'Node')";
/*  141 */           rs1 = AMConnectionPool.executeQueryStmt(query1);
/*  142 */           if (rs1.next())
/*      */           {
/*  144 */             map.put("host_resid", rs1.getString(1));
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  149 */           e.printStackTrace();
/*      */         }
/*      */         finally {}
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
/*  164 */       return null;
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  169 */       e.printStackTrace();
/*  170 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  175 */         AMConnectionPool.closeStatement(rs); } catch (Exception exc3) {} } try { AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception exc3) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  182 */     return map;
/*      */   }
/*      */   
/*      */   public static HashMap getSystemInfoAndPollingInfoForHost(String resourceid) {
/*  186 */     String oldquery = "select AM_ManagedObject.RESOURCENAME , CollectData.TARGETADDRESS,  CollectData.RESOURCETYPE , CollectData.POLLINTERVAL , max(COLLECTIONTIME) , COALESCE(Alert.SEVERITY,-1) , AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.TYPE from AM_ManagedObject , AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join CollectData on CollectData.RESOURCENAME= AM_ManagedObject.RESOURCENAME  and CollectData.COMPONENTNAME='HOST'  left outer join Alert on Alert.SOURCE=" + resourceid + "  and Alert.CATEGORY=AM_ATTRIBUTES.ATTRIBUTEID  left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=" + resourceid + " where AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_ATTRIBUTES.ATTRIBUTE='Health' group by AM_ManagedObject.RESOURCENAME,CollectData.TARGETADDRESS,  CollectData.RESOURCETYPE , CollectData.POLLINTERVAL ,AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.TYPE,Alert.SEVERITY";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  195 */     String query = "select AM_ManagedObject.RESOURCENAME , CollectData.TARGETADDRESS,  CollectData.RESOURCETYPE , CollectData.POLLINTERVAL , max(COLLECTIONTIME) , COALESCE(Alert.SEVERITY,-1) , AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.TYPE from AM_ManagedObject join AM_ATTRIBUTES on AM_ATTRIBUTES.RESOURCETYPE=AM_ManagedObject.TYPE left outer join CollectData on CollectData.RESOURCENAME= AM_ManagedObject.RESOURCENAME  and CollectData.COMPONENTNAME='HOST'  left outer join Alert on Alert.SOURCE='" + resourceid + "' and Alert.CATEGORY=" + DBQueryUtil.castasVarchar("AM_ATTRIBUTES.ATTRIBUTEID") + "  left outer join AM_ManagedObjectData on AM_ManagedObjectData.RESID=" + resourceid + " where AM_ManagedObject.RESOURCEID=" + resourceid + " and AM_ATTRIBUTES.ATTRIBUTE='Health' group by AM_ManagedObject.RESOURCENAME, CollectData.TARGETADDRESS,  CollectData.RESOURCETYPE , CollectData.POLLINTERVAL ,AM_ATTRIBUTES.ATTRIBUTEID,AM_ManagedObject.TYPE,Alert.SEVERITY";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  204 */     FormatUtil.printQueryChange("ClientDBUtil.java", oldquery, query);
/*  205 */     map = new HashMap();
/*  206 */     ResultSet rs = null;
/*      */     try {
/*  208 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  209 */       if (rs.next()) {
/*  210 */         map.put("HOSTNAME", rs.getString(1));
/*  211 */         map.put("HOSTIP", rs.getString(2));
/*  212 */         if (rs.getString(3) != null) {
/*  213 */           if ((rs.getString(3).equals("Node")) || (rs.getString(3).equals("Snmp-node")))
/*      */           {
/*  215 */             map.put("HOSTOS", "Unknown");
/*      */           } else {
/*  217 */             map.put("HOSTOS", rs.getString(3));
/*      */           }
/*      */         }
/*  220 */         if (rs.getLong(5) != 0L) {
/*  221 */           if (rs.getLong(5) > AMServerFramework.serverStartupTime) {
/*  222 */             map.put("LASTDC", new Long(rs.getLong(5)));
/*  223 */             map.put("NEXTDC", new Long(rs.getLong(5) + rs.getLong(4) * 1000L));
/*      */           }
/*      */           else {
/*  226 */             map.put("LASTDC", new Long(rs.getLong(5)));
/*  227 */             map.put("NEXTDC", new Long(AMServerFramework.serverStartupTime + rs.getLong(4) * 1000L));
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  234 */           map.put("NEXTDC", new Long(System.currentTimeMillis() + rs.getLong(4) * 1000L));
/*      */         }
/*      */         
/*      */ 
/*  238 */         map.put("HEALTH", rs.getString(6));
/*  239 */         map.put("HEALTHID", rs.getString(7));
/*  240 */         map.put("HOSTTYPE", rs.getString(8));
/*  241 */         map.put("HOSTTYPEDISPLAYNAME", rs.getString(8));
/*      */       } else {
/*  243 */         return null;
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
/*  261 */       return map;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  247 */       e.printStackTrace();
/*      */       
/*  249 */       return null;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  255 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc3) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Hashtable getAssociatedMonitors(String resourceid)
/*      */   {
/*  265 */     String haidquery = "select parentid,displayname from AM_PARENTCHILDMAPPER,AM_ManagedObject WHERE parentid=resourceid and type='HAI' and childid=" + resourceid;
/*      */     
/*  267 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/*  269 */       haidquery = "select AM_PARENTCHILDMAPPER.parentid,AM_ManagedObject.displayname from AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_HOLISTICAPPLICATION_EXT WHERE AM_PARENTCHILDMAPPER.parentid=AM_ManagedObject.resourceid and AM_ManagedObject.type='HAI' and AM_PARENTCHILDMAPPER.childid=" + resourceid + " and AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION_EXT.RESOURCEID and AM_HOLISTICAPPLICATION_EXT.APP_TYPE not in ('CUSTOMER', 'SITE')";
/*      */     }
/*  271 */     ManagedApplication mo = new ManagedApplication();
/*  272 */     ArrayList rows = mo.getRows(haidquery);
/*      */     
/*  274 */     if (rows.size() == 0) {
/*  275 */       return null;
/*      */     }
/*  277 */     Hashtable table = new Hashtable(rows.size());
/*  278 */     for (int i = 0; i < rows.size(); i++) {
/*  279 */       ArrayList row = (ArrayList)rows.get(i);
/*  280 */       table.put(row.get(0), row.get(1));
/*      */     }
/*  282 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Hashtable getAssociatedMonitorsForOwner(String resourceid, String owner)
/*      */   {
/*  288 */     String haidquery = "SELECT RESOURCEID,RESOURCENAME FROM AM_ManagedObject,AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable WHERE CHILDID=" + resourceid + " AND RESOURCEID=PARENTID AND TYPE='HAI' AND HAID=RESOURCEID AND USERID=OWNERID AND AM_UserPasswordTable.USERNAME='" + owner + "' group by RESOURCENAME,RESOURCEID";
/*      */     
/*  290 */     ManagedApplication mo = new ManagedApplication();
/*  291 */     ArrayList rows = mo.getRows(haidquery);
/*  292 */     if (rows.size() == 0) {
/*  293 */       return null;
/*      */     }
/*  295 */     Hashtable table = new Hashtable();
/*      */     
/*  297 */     for (int i = 0; i < rows.size(); i++) {
/*  298 */       ArrayList row = (ArrayList)rows.get(i);
/*  299 */       table.put(row.get(0), row.get(1));
/*      */     }
/*  301 */     return table;
/*      */   }
/*      */   
/*      */   public static Vector getAssociatedMonitorGroupsForOwner(String owner) {
/*  305 */     Vector mgs = new Vector();
/*  306 */     ResultSet rs = null;
/*      */     try {
/*  308 */       rs = AMConnectionPool.executeQueryStmt("select HAID from AM_HOLISTICAPPLICATION_OWNERS, AM_UserPasswordTable where AM_UserPasswordTable.USERID=AM_HOLISTICAPPLICATION_OWNERS.OWNERID and AM_UserPasswordTable.USERNAME='" + owner + "'");
/*  309 */       while (rs.next()) {
/*  310 */         mgs.add(rs.getString("HAID"));
/*      */       }
/*      */     } catch (Exception ex) {
/*  313 */       ex.printStackTrace();
/*      */     } finally {
/*  315 */       if (rs != null) {
/*  316 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*  320 */     return mgs;
/*      */   }
/*      */   
/*      */   public static Vector getUserResourceID(HttpServletRequest request) {
/*  324 */     String userid = Constants.getLoginUserid(request);
/*  325 */     return getUserResourceID(userid);
/*      */   }
/*      */   
/*      */   public static Vector getUserResourceID(String userid) {
/*  329 */     return Constants.getUserResourceID(userid);
/*      */   }
/*      */   
/*      */   public static String getLoginUserid(HttpServletRequest request) {
/*  333 */     return Constants.getLoginUserid(request);
/*      */   }
/*      */   
/*      */   public static boolean isUserResourceEnabled() {
/*  337 */     return Constants.isUserResourceEnabled();
/*      */   }
/*      */   
/*      */   public static boolean isPrivilegedUser(HttpServletRequest request) {
/*  341 */     return Constants.isPrivilegedUser(request);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Vector getResourceIdentity(String owner)
/*      */   {
/*  348 */     return getResourceIdentity(owner, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Vector getResourceIdentity(String owner, HttpServletRequest request, HashMap widgetProps)
/*      */   {
/*  357 */     Vector v = new Vector();
/*      */     
/*  359 */     if (request != null)
/*      */     {
/*  361 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  363 */         v = CustomerManagementAPI.filterResourceIds(request);
/*  364 */         return v;
/*      */       }
/*  366 */       HttpSession session = request.getSession();
/*  367 */       if ((session.getAttribute("resourceIDforOwner") != null) && ((request.isUserInRole("OPERATOR")) || (request.isUserInRole("ADMIN")))) {
/*  368 */         return (Vector)session.getAttribute("resourceIDforOwner");
/*      */       }
/*      */       
/*      */     }
/*  372 */     else if (widgetProps != null)
/*      */     {
/*  374 */       if (EnterpriseUtil.isIt360MSPEdition())
/*      */       {
/*  376 */         v = CustomerManagementAPI.filterResourceIds(widgetProps);
/*  377 */         return v;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  383 */     Vector tempvec = new Vector();
/*  384 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  385 */     String query1 = "SELECT AM_HOLISTICAPPLICATION.HAID hd FROM AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "'";
/*  386 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  389 */       rs = AMConnectionPool.executeQueryStmt(query1);
/*      */       
/*  391 */       while (rs.next())
/*      */       {
/*  393 */         v.add(rs.getString("hd"));
/*  394 */         tempvec.add(rs.getString("hd"));
/*      */       }
/*  396 */       AMConnectionPool.closeStatement(rs);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  405 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc3) {}
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  413 */       query = "select CHILDID from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable WHERE AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "'";
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  400 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  405 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */       catch (Exception exc3) {}
/*      */     }
/*      */     
/*      */ 
/*      */     String query;
/*      */     
/*      */ 
/*  414 */     ResultSet rs1 = null;
/*  415 */     String parentIds = "";
/*      */     try
/*      */     {
/*  418 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/*  419 */       while (rs1.next())
/*      */       {
/*  421 */         if (!v.contains(rs1.getString("CHILDID"))) {
/*  422 */           v.add(rs1.getString("CHILDID"));
/*      */         }
/*  424 */         if (parentIds.trim().equals("")) {
/*  425 */           parentIds = "'" + rs1.getString("CHILDID") + "'";
/*      */         }
/*      */         else {
/*  428 */           parentIds = parentIds + ",'" + rs1.getString("CHILDID") + "'";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  439 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/*      */       catch (Exception exc3)
/*      */       {
/*  443 */         exc3.printStackTrace();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  448 */       if (parentIds.trim().equals("")) {
/*      */         break label511;
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  434 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/*  439 */         AMConnectionPool.closeStatement(rs1);
/*      */       }
/*      */       catch (Exception exc3)
/*      */       {
/*  443 */         exc3.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  449 */     SegmentReportUtil.getchildsAssociated(v, parentIds);
/*  450 */     SegmentReportUtil.getEUMchildsAssociated(v, parentIds);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*      */       label511:
/*      */       
/*      */ 
/*      */ 
/*  461 */       ManagedApplication mo = new ManagedApplication();
/*  462 */       for (int i = 0; (tempvec != null) && (i < tempvec.size()); i++)
/*      */       {
/*  464 */         Vector subgrouplist = new Vector();
/*  465 */         ManagedApplication.getChildIDs(subgrouplist, (String)tempvec.get(i));
/*  466 */         String childquery = "select CHILDID from AM_PARENTCHILDMAPPER,AM_ManagedObject where " + ManagedApplication.getCondition("AM_PARENTCHILDMAPPER.PARENTID", subgrouplist) + " and  AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID and AM_ManagedObject.TYPE !='HAI'";
/*  467 */         ArrayList rows = mo.getRows(childquery);
/*  468 */         for (int j = 0; (rows != null) && (j < rows.size()); j++)
/*      */         {
/*  470 */           ArrayList singlerow = (ArrayList)rows.get(j);
/*  471 */           if (!v.contains(singlerow.get(0))) {
/*  472 */             v.add(singlerow.get(0));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  479 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  482 */     return v;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static Vector getMonitorTypesForOwner(String owner, String monitors)
/*      */   {
/*  489 */     return getMonitorTypesForOwner(owner, monitors, null);
/*      */   }
/*      */   
/*      */   public static Vector getMonitorTypesForOwner(String owner, String monitors, HttpServletRequest request) {
/*  493 */     Vector v = new Vector();
/*  494 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/*  495 */     boolean isBSGUser = false;
/*  496 */     String loginName = null;
/*      */     
/*      */ 
/*  499 */     if (monitors.charAt(monitors.length() - 1) == ',')
/*      */     {
/*      */ 
/*  502 */       monitors = monitors + "-10";
/*  503 */       monitors = monitors.replaceAll(",", "','");
/*  504 */       monitors = "'" + monitors + "'";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  509 */     if (EnterpriseUtil.isIt360MSPEdition())
/*      */     {
/*  511 */       loginName = request.getRemoteUser();
/*  512 */       It360SPUserManagementUtil usrMgmt = new It360SPUserManagementUtil();
/*  513 */       isBSGUser = usrMgmt.isBSGUser(loginName);
/*      */     }
/*      */     
/*  516 */     String query = "SELECT AM_PARENTCHILDMAPPER.CHILDID  FROM AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable,AM_ManagedObject,AM_ManagedResourceType  WHERE AM_HOLISTICAPPLICATION.HAID=AM_PARENTCHILDMAPPER.PARENTID AND AM_HOLISTICAPPLICATION.HAID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "'  AND AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID  AND  AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.SUBGROUP in(" + monitors + ")";
/*  517 */     ResultSet rs1 = null;
/*      */     
/*  519 */     if ((request != null) && (EnterpriseUtil.isIt360MSPEdition()) && (!isBSGUser))
/*      */     {
/*  521 */       Vector resIds = CustomerManagementAPI.filterResourceIds(request);
/*  522 */       if (monitors.contains("Interface")) {
/*  523 */         resIds = CustomerManagementAPI.filterResourceIds(request, false);
/*      */       }
/*  525 */       query = "SELECT AM_PARENTCHILDMAPPER.CHILDID FROM AM_PARENTCHILDMAPPER inner join AM_ManagedObject on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID inner join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE where AM_ManagedResourceType.SUBGROUP in(" + monitors + ") AND " + ManagedApplication.getCondition("AM_PARENTCHILDMAPPER.CHILDID", resIds);
/*  526 */       if (monitors.contains("OpManager")) {
/*  527 */         query = "SELECT AM_PARENTCHILDMAPPER.CHILDID FROM AM_PARENTCHILDMAPPER inner join AM_ManagedObject mo on AM_PARENTCHILDMAPPER.CHILDID=mo.RESOURCEID inner join AM_AssociatedExtDevices aed on aed.ResID = mo.resourceID inner join  ExternalDeviceDetails edd on edd.productID = aed.productID and edd.name = mo.resourcename where edd.category in (" + monitors + ") AND " + ManagedApplication.getCondition("AM_PARENTCHILDMAPPER.CHILDID", resIds);
/*      */       }
/*      */       
/*  530 */       if (monitors.contains("Trap")) {
/*  531 */         query = "Select RESOURCEID CHILDID from AM_ManagedObject where TYPE in (" + monitors + ")";
/*      */       }
/*      */       
/*  534 */       AMLog.debug("getMonitorTypesForOwner() SP Edition:QRY" + query);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  541 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/*  542 */       while (rs1.next())
/*      */       {
/*      */ 
/*  545 */         v.add(rs1.getString("CHILDID"));
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */ 
/*  553 */       e.printStackTrace();
/*      */     } finally {
/*  555 */       AMConnectionPool.closeStatement(rs1);
/*      */     }
/*  557 */     return v;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Properties getLicenseInfo()
/*      */   {
/*  563 */     Properties p = new Properties();
/*      */     try
/*      */     {
/*  566 */       Wield w = Wield.getInstance();
/*  567 */       p.setProperty("licensetype", w.getUserType());
/*  568 */       p.setProperty("numberofclients", "1");
/*  569 */       Date amsExpiry = AMCacheHandler.getAmsExpiryDate();
/*  570 */       if (amsExpiry != null) {
/*  571 */         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  572 */         p.setProperty("amsExpiryDate", dateFormat.format(amsExpiry));
/*      */       }
/*  574 */       Properties moduleprops = w.getModuleProperties("Users");
/*  575 */       if ((moduleprops != null) && (moduleprops.containsKey("NumberOfClients")) && (moduleprops.getProperty("NumberOfClients") != null))
/*      */       {
/*  577 */         if (moduleprops.getProperty("NumberOfClients").equalsIgnoreCase("unlimited"))
/*      */         {
/*  579 */           p.setProperty("numberofclients", moduleprops.getProperty("NumberOfClients"));
/*      */         }
/*      */         else
/*      */         {
/*      */           try
/*      */           {
/*  585 */             Integer.parseInt(moduleprops.getProperty("NumberOfClients"));
/*  586 */             p.setProperty("numberofclients", moduleprops.getProperty("NumberOfClients"));
/*      */           }
/*      */           catch (NumberFormatException exp)
/*      */           {
/*  590 */             exp.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*  594 */       moduleprops = new Properties();
/*  595 */       moduleprops = w.getModuleProperties("DISCOVERY");
/*  596 */       p.setProperty("nodes", "unlimited");
/*      */       try
/*      */       {
/*  599 */         if ((moduleprops != null) && (moduleprops.containsKey("Nodes")) && (moduleprops.getProperty("Nodes") != null))
/*      */         {
/*  601 */           p.setProperty("nodes", moduleprops.getProperty("Nodes"));
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  606 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  611 */       e.printStackTrace();
/*      */     }
/*  613 */     return p;
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getActionsForOwner(String owner, String userid, boolean isOperator)
/*      */   {
/*  618 */     ArrayList<String> ActionIdList = new ArrayList();
/*  619 */     ResultSet rs = null;
/*  620 */     String operatorCondition = "";
/*      */     try
/*      */     {
/*  623 */       Vector<String> actionIdList = new Vector();
/*  624 */       boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(owner);
/*  625 */       if (isDelegatedAdmin)
/*      */       {
/*  627 */         Vector<String> resIds = DelegatedUserRoleUtil.getMonitorsForUser(userid);
/*  628 */         if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/*  629 */           actionIdList = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(Integer.parseInt(userid), 2);
/*      */         }
/*      */         else {
/*  632 */           actionIdList = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(Integer.parseInt(userid), 2);
/*      */         }
/*  634 */         String qry = "select ACTIONID from AM_ATTRIBUTEACTIONMAPPER where " + DBUtil.getCondition("AM_ATTRIBUTEACTIONMAPPER.ID", resIds) + " and AM_ATTRIBUTEACTIONMAPPER.ACTIONID not in ";
/*  635 */         actionIdList = DelegatedUserRoleUtil.getCompleteConfigIds(qry, actionIdList);
/*      */         
/*  637 */         return new ArrayList(actionIdList);
/*      */       }
/*      */       
/*  640 */       if (isOperator)
/*      */       {
/*  642 */         Vector resids = getResourceIdentity(owner);
/*  643 */         operatorCondition = operatorCondition + " and " + DependantMOUtil.getCondition("aam.ID", resids);
/*      */       }
/*      */       
/*  646 */       String actionsQry = "select distinct ACTIONID from AM_ATTRIBUTEACTIONMAPPER aam,AM_ACTIONPROFILE prof where prof.ID=aam.ACTIONID" + operatorCondition;
/*  647 */       AMLog.info("ClientDBUtil: getActionsForOwner:" + actionsQry);
/*  648 */       rs = AMConnectionPool.executeQueryStmt(actionsQry);
/*  649 */       while (rs.next())
/*      */       {
/*  651 */         ActionIdList.add(rs.getString("ACTIONID"));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  656 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  660 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  662 */     return ActionIdList;
/*      */   }
/*      */   
/*      */   public static void getActionProfiles_new(HttpServletRequest request, String owner, boolean isOperatorRole)
/*      */   {
/*  667 */     Hashtable<Integer, Hashtable<String, Object>> actionProfilesList = new Hashtable();
/*  668 */     Hashtable<String, Hashtable<String, Object>> actionProfiles = new Hashtable();
/*      */     try {
/*  670 */       String userName = request.getRemoteUser();
/*  671 */       boolean isPrivilegedUser = Constants.isPrivilegedUser(request);
/*  672 */       String userId = DBUtil.getUserID(userName);
/*  673 */       Vector<String> resIds = DelegatedUserRoleUtil.getResIDsForPrivilegedUser(userId);
/*  674 */       actionProfilesList = ActionsUtil.getActions(userName, resIds, isPrivilegedUser);
/*      */       
/*  676 */       if (actionProfilesList.size() > 0) {
/*  677 */         Hashtable<String, Object> actionProfile = new Hashtable();
/*  678 */         ArrayList<ArrayList<String>> actionsList = new ArrayList();
/*      */         
/*  680 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1));
/*  681 */         actionsList = getActionsListFromProfile(actionProfile);
/*  682 */         if (!actionsList.isEmpty()) {
/*  683 */           request.setAttribute("emailActions", actionsList);
/*  684 */           actionProfiles.put("emailActions", actionProfile);
/*      */         }
/*  686 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(2));
/*  687 */         actionsList = getActionsListFromProfile(actionProfile);
/*  688 */         if (!actionsList.isEmpty()) {
/*  689 */           request.setAttribute("smsActions", actionsList);
/*  690 */           actionProfiles.put("smsActions", actionProfile);
/*      */         }
/*  692 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(10));
/*  693 */         actionsList = getActionsListFromProfile(actionProfile);
/*  694 */         if (!actionsList.isEmpty()) {
/*  695 */           request.setAttribute("execQueryActions", actionsList);
/*  696 */           actionProfiles.put("execQueryActions", actionProfile);
/*      */         }
/*  698 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(3));
/*  699 */         actionsList = getActionsListFromProfile(actionProfile);
/*  700 */         if (!actionsList.isEmpty()) {
/*  701 */           request.setAttribute("execprogActions", actionsList);
/*  702 */           actionProfiles.put("execprogActions", actionProfile);
/*      */         }
/*  704 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(7));
/*  705 */         actionsList = getActionsListFromProfile(actionProfile);
/*  706 */         if (!actionsList.isEmpty()) {
/*  707 */           request.setAttribute("threaddumpActions", actionsList);
/*  708 */           actionProfiles.put("threaddumpActions", actionProfile);
/*      */         }
/*  710 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(8));
/*  711 */         actionsList = getActionsListFromProfile(actionProfile);
/*  712 */         if (!actionsList.isEmpty()) {
/*  713 */           request.setAttribute("heapdumpActions", actionsList);
/*  714 */           actionProfiles.put("heapdumpActions", actionProfile);
/*      */         }
/*  716 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(9));
/*  717 */         actionsList = getActionsListFromProfile(actionProfile);
/*  718 */         if (!actionsList.isEmpty()) {
/*  719 */           request.setAttribute("performgcActions", actionsList);
/*  720 */           actionProfiles.put("performgcActions", actionProfile);
/*      */         }
/*  722 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1520));
/*  723 */         actionsList = getActionsListFromProfile(actionProfile);
/*  724 */         if (!actionsList.isEmpty()) {
/*  725 */           request.setAttribute("ec2InastanceActions", actionsList);
/*  726 */           actionProfiles.put("ec2InastanceActions", actionProfile);
/*      */         }
/*  728 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1570));
/*  729 */         actionsList = getActionsListFromProfile(actionProfile);
/*  730 */         if (!actionsList.isEmpty()) {
/*  731 */           request.setAttribute("winServiceActions", actionsList);
/*  732 */           actionProfiles.put("winServiceActions", actionProfile);
/*      */         }
/*  734 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1580));
/*  735 */         actionsList = getActionsListFromProfile(actionProfile);
/*  736 */         if (!actionsList.isEmpty()) {
/*  737 */           request.setAttribute("sqlJobActions", actionsList);
/*  738 */           actionProfiles.put("sqlJobActions", actionProfile);
/*      */         }
/*  740 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1510));
/*  741 */         actionsList = getActionsListFromProfile(actionProfile);
/*  742 */         if (!actionsList.isEmpty()) {
/*  743 */           request.setAttribute("sendTrapActions", actionsList);
/*  744 */           actionProfiles.put("sendTrapActions", actionProfile);
/*      */         }
/*  746 */         actionProfile = (Hashtable)actionProfilesList.get(Integer.valueOf(1590));
/*  747 */         actionsList = getActionsListFromProfile(actionProfile);
/*  748 */         if (!actionsList.isEmpty()) {
/*  749 */           request.setAttribute("ticketActions", actionsList);
/*  750 */           actionProfiles.put("ticketActions", actionProfile);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  755 */       e.printStackTrace();
/*      */     }
/*      */     
/*  758 */     request.setAttribute("actionProfiles", actionProfilesList);
/*      */   }
/*      */   
/*      */   public static ArrayList<ArrayList<String>> getActionsListFromProfile(Hashtable<String, Object> actionProfile)
/*      */   {
/*  763 */     ArrayList<ArrayList<String>> actionsList = new ArrayList();
/*      */     try {
/*  765 */       if (actionProfile != null) {
/*  766 */         actionsList = (ArrayList)actionProfile.get("ActionProfiles");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  770 */       e.printStackTrace();
/*      */     }
/*  772 */     return actionsList;
/*      */   }
/*      */   
/*      */   public static void getActionProfiles(HttpServletRequest request, String owner, boolean isOperatorRole)
/*      */   {
/*  777 */     ManagedApplication mo = new ManagedApplication();
/*  778 */     Vector<String> resids = new Vector();
/*  779 */     Vector<String> configIds = new Vector();
/*  780 */     String resIdCondition = "";
/*  781 */     ResultSet rs = null;
/*  782 */     HashMap<String, String> globalCountby = new HashMap();
/*  783 */     boolean isAPIRequest = request.getParameter("apikey") != null;
/*  784 */     String apiKey = request.getParameter("apikey");
/*  785 */     String userName = !isAPIRequest ? request.getRemoteUser() : CommonAPIUtil.getUsername(apiKey);
/*  786 */     int userId = isAPIRequest ? Integer.parseInt(CommonAPIUtil.getUserIdForAPIKey(apiKey)) : DelegatedUserRoleUtil.getLoginUserid(request);
/*  787 */     Hashtable actionProfiles = new Hashtable();
/*  788 */     boolean isPrivilegedUser = request != null ? CommonAPIUtil.isPrivilegedUser(apiKey) : !isAPIRequest ? Constants.isPrivilegedUser(request) : isOperatorRole;
/*  789 */     boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(userName);
/*      */     try
/*      */     {
/*  792 */       if (isPrivilegedUser) {
/*  793 */         resIdCondition = " and " + DBUtil.getCondition("mo.RESOURCEID", DelegatedUserRoleUtil.getResIDsForPrivilegedUser(DBUtil.getUserID(userName)));
/*      */       }
/*      */       
/*  796 */       rs = AMConnectionPool.executeQueryStmt("select ACTIONID,count(ID) from AM_ATTRIBUTEACTIONMAPPER act_map,AM_ManagedObject mo where  mo.RESOURCEID=act_map.ID" + resIdCondition + " group by ACTIONID");
/*  797 */       while (rs.next()) {
/*  798 */         globalCountby.put(rs.getString(1), rs.getString(2));
/*      */       }
/*  800 */       request.setAttribute("GlobalUsedCount", globalCountby);
/*      */     }
/*      */     catch (SQLException ex) {
/*  803 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/*  806 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  811 */     if (isPrivilegedUser) {
/*  812 */       resids = DelegatedUserRoleUtil.getMonitorsForUser(DBUtil.getUserID(userName));
/*  813 */       if (isDelegatedAdmin) {
/*  814 */         if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions")) {
/*  815 */           configIds = DelegatedUserRoleUtil.getConfigIDsWithViewPerm(userId, 2);
/*      */         }
/*      */         else {
/*  818 */           configIds = DelegatedUserRoleUtil.getConfigIDsOwnedByUser(userId, 2);
/*      */         }
/*      */       }
/*      */     }
/*  822 */     String configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or prof.ID", configIds) : "";
/*  823 */     String query = "select AM_EMAILACTION.ID,NAME,FROMADDRESS, TOADDRESS, SUBJECT from AM_EMAILACTION,AM_ACTIONPROFILE where AM_EMAILACTION.ID =AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.TYPE=1 order by AM_ACTIONPROFILE.NAME";
/*  824 */     if (isPrivilegedUser) {
/*  825 */       query = "select email.ID,NAME,FROMADDRESS, TOADDRESS, SUBJECT from AM_EMAILACTION email,AM_ACTIONPROFILE prof where prof.ID=email.ID and prof.TYPE=1 and (email.ID in (select distinct ACTIONID from AM_ATTRIBUTEACTIONMAPPER aam,AM_ACTIONPROFILE prof where " + DependantMOUtil.getCondition("aam.ID", resids) + " and prof.ID=aam.ACTIONID)" + configIdCond + ")";
/*      */     }
/*      */     
/*  828 */     ArrayList rows = mo.getRows(query);
/*  829 */     if (rows.size() > 0)
/*      */     {
/*  831 */       Hashtable actionProfile = new Hashtable();
/*  832 */       ArrayList<String> actionProps = new ArrayList();
/*  833 */       actionProps.add("ID");
/*  834 */       actionProps.add("NAME");
/*  835 */       actionProps.add("FROMADDRESS");
/*  836 */       actionProps.add("TOADDRESS");
/*  837 */       actionProps.add("SUBJECT");
/*  838 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.email"));
/*  839 */       actionProfile.put("actionProps", actionProps);
/*  840 */       actionProfiles.put("emailActions", actionProfile);
/*      */       
/*  842 */       request.setAttribute("emailActions", rows);
/*      */     }
/*      */     
/*      */ 
/*  846 */     ArrayList threadDump = new ArrayList();
/*  847 */     ArrayList heapDump = new ArrayList();
/*  848 */     ArrayList performGC = new ArrayList();
/*  849 */     ArrayList ec2InstanceActionlist = new ArrayList();
/*  850 */     ArrayList aListWinServiceActions = new ArrayList();
/*  851 */     ArrayList sqlJobActionList = new ArrayList();
/*      */     
/*      */ 
/*  854 */     query = "SELECT ACTIONID FROM AM_ACTION_BUSINESSHOURMAPPER";
/*  855 */     ArrayList actionIDForBusinessHour = new ArrayList();
/*      */     try
/*      */     {
/*  858 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  859 */       while (rs.next())
/*      */       {
/*  861 */         actionIDForBusinessHour.add(rs.getString(1));
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  866 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  870 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*  872 */     request.setAttribute("actionIDForBusinessHourConfiguredAction", actionIDForBusinessHour);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  877 */     HashMap<String, String[]> emailActionDetails = null;
/*  878 */     if (isPrivilegedUser)
/*      */     {
/*  880 */       emailActionDetails = new HashMap();
/*  881 */       String emailQuery = "select prof.ID,FROMADDRESS,TOADDRESS from AM_EMAILACTION email,AM_ACTIONPROFILE  prof  where email.ID=prof.ID and email.ID in (select distinct EMAIL_ACTION_ID from AM_ATTRIBUTEACTIONMAPPER aam,AM_JREACTIONS jre where aam.ACTIONID=jre.ID and " + DependantMOUtil.getCondition("aam.ID", resids) + ")";
/*      */       try {
/*  883 */         rs = AMConnectionPool.executeQueryStmt(emailQuery);
/*  884 */         while (rs.next()) {
/*  885 */           String key = rs.getString("ID");
/*  886 */           String[] value = new String[2];
/*  887 */           value[0] = rs.getString("FROMADDRESS");
/*  888 */           value[1] = rs.getString("TOADDRESS");
/*  889 */           emailActionDetails.put(key, value);
/*      */         }
/*      */       } catch (Exception e) {
/*  892 */         e.printStackTrace();
/*      */       } finally {
/*  894 */         AMConnectionPool.closeStatement(rs);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  899 */     query = "select  AM_JREACTIONS.ID,THREADDUMP.NAME, AM_JREACTIONS.DELAY, AM_JREACTIONS.COUNT, AM_JREACTIONS.TYPE, AM_ManagedObject.RESOURCENAME, ACTION.NAME as ENAME, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS,THREADDUMP.TYPE as ATYPE  from  AM_JREACTIONS left join AM_ACTIONPROFILE as THREADDUMP on AM_JREACTIONS.ID=THREADDUMP.ID left join AM_ManagedObject on AM_JREACTIONS.TARGET_RESID =AM_ManagedObject.RESOURCEID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID";
/*      */     
/*  901 */     if (isPrivilegedUser) {
/*  902 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_JREACTIONS.ID ", configIds) : "";
/*      */       
/*  904 */       query = "select distinct AM_JREACTIONS.ID, AM_ACTIONPROFILE.NAME, AM_JREACTIONS.DELAY, AM_JREACTIONS.COUNT, AM_JREACTIONS.TYPE , AM_ManagedObject.RESOURCENAME, ACTION.NAME as ENAME, ACTION.ID as EMAILID, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS, AM_ACTIONPROFILE.TYPE as ATYPE  from AM_ACTIONPROFILE, AM_JREACTIONS left join AM_ManagedObject on AM_JREACTIONS.TARGET_RESID =AM_ManagedObject.RESOURCEID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID  where AM_ACTIONPROFILE.ID=AM_JREACTIONS.ID and (AM_JREACTIONS.ID in (select aam.actionid  from AM_JREACTIONS, AM_ATTRIBUTEACTIONMAPPER aam where AM_JREACTIONS.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ")";
/*      */     }
/*      */     
/*      */     try
/*      */     {
/*  909 */       rs = AMConnectionPool.executeQueryStmt(query);
/*  910 */       while (rs.next())
/*      */       {
/*  912 */         ArrayList row = new ArrayList();
/*  913 */         String actionType = rs.getString("ATYPE");
/*  914 */         if ((actionType.equals("7")) || (actionType.equals("8")) || (actionType.equals("9")))
/*      */         {
/*  916 */           row.add(rs.getString("ID"));
/*  917 */           row.add(rs.getString("NAME"));
/*  918 */           row.add(rs.getString("DELAY"));
/*  919 */           row.add(rs.getString("COUNT"));
/*  920 */           row.add(rs.getString("TYPE"));
/*  921 */           row.add(rs.getString("RESOURCENAME"));
/*  922 */           row.add(rs.getString("ENAME"));
/*  923 */           if (!isPrivilegedUser) {
/*  924 */             row.add(rs.getString("FROMADDRESS"));
/*  925 */             row.add(rs.getString("TOADDRESS"));
/*  926 */           } else if (emailActionDetails != null) {
/*  927 */             String[] addr = (String[])emailActionDetails.get(rs.getString("EMAILID"));
/*  928 */             if ((addr != null) && (addr.length == 2)) {
/*  929 */               row.add(addr[0]);
/*  930 */               row.add(addr[1]);
/*      */             } else {
/*  932 */               row.add("");
/*  933 */               row.add("");
/*      */             }
/*      */           }
/*  936 */           if (actionType.equals("7")) {
/*  937 */             threadDump.add(row);
/*      */           }
/*  939 */           else if (actionType.equals("8")) {
/*  940 */             heapDump.add(row);
/*      */           }
/*  942 */           else if (actionType.equals("9")) {
/*  943 */             performGC.add(row);
/*      */           }
/*      */         }
/*  946 */         else if ((actionType.equals("14")) || (actionType.equals("15")) || (actionType.equals("16")))
/*      */         {
/*  948 */           row.add(rs.getString("ID"));
/*  949 */           row.add(rs.getString("NAME"));
/*      */           
/*      */ 
/*  952 */           row.add(rs.getString("TYPE"));
/*  953 */           row.add(actionType);
/*  954 */           row.add(rs.getString("RESOURCENAME"));
/*  955 */           row.add(rs.getString("ENAME"));
/*  956 */           if (!isPrivilegedUser) {
/*  957 */             row.add(rs.getString("FROMADDRESS"));
/*  958 */             row.add(rs.getString("TOADDRESS"));
/*  959 */           } else if (emailActionDetails != null) {
/*  960 */             String[] addr = (String[])emailActionDetails.get(rs.getString("EMAILID"));
/*  961 */             if ((addr != null) && (addr.length == 2)) {
/*  962 */               row.add(addr[0]);
/*  963 */               row.add(addr[1]);
/*      */             } else {
/*  965 */               row.add("");
/*  966 */               row.add("");
/*      */             }
/*      */           }
/*  969 */           ec2InstanceActionlist.add(row);
/*  970 */         } else if ((actionType.equals("401")) || (actionType.equals("402")) || (actionType.equals("403")))
/*      */         {
/*  972 */           row.add(rs.getString("ID"));
/*  973 */           row.add(rs.getString("NAME"));
/*  974 */           row.add(rs.getString("TYPE"));
/*  975 */           row.add(actionType);
/*  976 */           row.add(rs.getString("ENAME"));
/*  977 */           if (!isPrivilegedUser) {
/*  978 */             row.add(rs.getString("FROMADDRESS"));
/*  979 */             row.add(rs.getString("TOADDRESS"));
/*  980 */           } else if (emailActionDetails != null) {
/*  981 */             String[] addr = (String[])emailActionDetails.get(rs.getString("EMAILID"));
/*  982 */             if ((addr != null) && (addr.length == 2)) {
/*  983 */               row.add(addr[0]);
/*  984 */               row.add(addr[1]);
/*      */             } else {
/*  986 */               row.add("");
/*  987 */               row.add("");
/*      */             }
/*      */           }
/*  990 */           sqlJobActionList.add(row);
/*      */           
/*      */ 
/*  993 */           String resourceInfosToDisp = "";
/*  994 */           String actionId = (String)row.get(0);
/*  995 */           String applyto = (String)row.get(2);
/*  996 */           if ((applyto != null) && (applyto.equals("2"))) {
/*  997 */             Map<String, String[]> resources = SqlJobActionUtil.getSelectedResources(Integer.parseInt(actionId), applyto);
/*  998 */             if ((resources != null) && (resources.size() > 0)) {
/*  999 */               Iterator<String> itr_key = resources.keySet().iterator();
/* 1000 */               while (itr_key.hasNext()) {
/* 1001 */                 String key = (String)itr_key.next();
/* 1002 */                 String[] resdetails = (String[])resources.get(key);
/* 1003 */                 resourceInfosToDisp = resourceInfosToDisp + resdetails[1] + "<BR>";
/*      */               }
/*      */             }
/*      */           }
/* 1007 */           if ((resourceInfosToDisp != null) && (resourceInfosToDisp.length() > 0)) {
/* 1008 */             resourceInfosToDisp = resourceInfosToDisp.substring(0, resourceInfosToDisp.length() - 4);
/* 1009 */             request.setAttribute("sqlJobResInfo_" + actionId, resourceInfosToDisp);
/*      */           }
/*      */         }
/*      */       }
/* 1013 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1017 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1020 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/*      */ 
/* 1024 */     query = "select  AM_JREACTIONS.ID,THREADDUMP.NAME, AM_JREACTIONS.TYPE,ACTION.NAME as ENAME, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS,THREADDUMP.TYPE as ATYPE  from  AM_JREACTIONS left join AM_ACTIONPROFILE as THREADDUMP on AM_JREACTIONS.ID=THREADDUMP.ID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID";
/* 1025 */     if (isPrivilegedUser) {
/* 1026 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_JREACTIONS.ID ", configIds) : "";
/*      */       
/* 1028 */       query = "select distinct AM_JREACTIONS.ID,THREADDUMP.NAME, AM_JREACTIONS.DELAY, AM_JREACTIONS.COUNT, AM_JREACTIONS.TYPE, AM_ManagedObject.DISPLAYNAME, ACTION.NAME as ENAME, THREADDUMP.TYPE as ATYPE from  AM_JREACTIONS left join AM_ACTIONPROFILE as THREADDUMP on AM_JREACTIONS.ID=THREADDUMP.ID left join AM_ManagedObject on AM_JREACTIONS.TARGET_RESID =AM_ManagedObject.RESOURCEID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID where AM_JREACTIONS.ID in (select distinct AM_JREACTIONS.ID from AM_JREACTIONS, AM_ATTRIBUTEACTIONMAPPER aam where AM_JREACTIONS.ID=aam.actionid  and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond;
/*      */     }
/*      */     try
/*      */     {
/* 1032 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1033 */       while (rs.next()) {
/* 1034 */         ArrayList row = new ArrayList();
/* 1035 */         String actionType = rs.getString("ATYPE");
/* 1036 */         if ((actionType.equals("301")) || (actionType.equals("302")) || (actionType.equals("303")))
/*      */         {
/* 1038 */           row.add(rs.getString("ID"));
/* 1039 */           row.add(rs.getString("NAME"));
/* 1040 */           row.add(rs.getString("TYPE"));
/* 1041 */           row.add(actionType);
/* 1042 */           row.add(rs.getString("ENAME"));
/* 1043 */           if (!isPrivilegedUser) {
/* 1044 */             row.add(rs.getString("FROMADDRESS"));
/* 1045 */             row.add(rs.getString("TOADDRESS"));
/* 1046 */           } else if (emailActionDetails != null) {
/* 1047 */             String[] addr = (String[])emailActionDetails.get(rs.getString("EMAILID"));
/* 1048 */             if ((addr != null) && (addr.length == 2)) {
/* 1049 */               row.add(addr[0]);
/* 1050 */               row.add(addr[1]);
/*      */             } else {
/* 1052 */               row.add("");
/* 1053 */               row.add("");
/*      */             }
/*      */           }
/* 1056 */           aListWinServiceActions.add(row);
/*      */           
/*      */ 
/* 1059 */           String resourceInfosToDisp = "";
/* 1060 */           String actionId = (String)row.get(0);
/* 1061 */           String applyto = (String)row.get(2);
/* 1062 */           if ((applyto != null) && ((applyto.equals("2")) || (applyto.equals("3")))) {
/* 1063 */             Map<String, String[]> resources = WinServiceActionUtil.getSelectedResources(actionId, applyto);
/* 1064 */             if ((resources != null) && (resources.size() > 0)) {
/* 1065 */               Iterator<String> itr_key = resources.keySet().iterator();
/* 1066 */               while (itr_key.hasNext()) {
/* 1067 */                 String key = (String)itr_key.next();
/* 1068 */                 String[] resdetails = (String[])resources.get(key);
/* 1069 */                 resourceInfosToDisp = resourceInfosToDisp + resdetails[1] + "<BR>";
/*      */               }
/*      */             }
/*      */           }
/* 1073 */           if ((resourceInfosToDisp != null) && (resourceInfosToDisp.length() > 0)) {
/* 1074 */             resourceInfosToDisp = resourceInfosToDisp.substring(0, resourceInfosToDisp.length() - 4);
/* 1075 */             request.setAttribute("winServiceResInfo_" + actionId, resourceInfosToDisp);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1081 */       e.printStackTrace();
/*      */     } finally {
/* 1083 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 1086 */     String javaActionTabSelected = "";
/*      */     
/* 1088 */     if (!threadDump.isEmpty()) {
/* 1089 */       request.setAttribute("threaddumpActions", threadDump);
/* 1090 */       javaActionTabSelected = "am.javaruntime.javaaction.threaddump";
/*      */       
/* 1092 */       Hashtable javaActionProfile = new Hashtable();
/* 1093 */       ArrayList<String> javaActionProps = new ArrayList();
/* 1094 */       javaActionProps.add("ID");
/* 1095 */       javaActionProps.add("NAME");
/* 1096 */       javaActionProps.add("DELAY");
/* 1097 */       javaActionProps.add("COUNT");
/* 1098 */       javaActionProps.add("TYPE");
/* 1099 */       javaActionProps.add("RESOURCENAME");
/* 1100 */       javaActionProps.add("ENAME");
/* 1101 */       javaActionProps.add("FROMADDRESS");
/* 1102 */       javaActionProps.add("TOADDRESS");
/* 1103 */       javaActionProfile.put("actionProps", javaActionProps);
/* 1104 */       javaActionProfile.put("Displayname", FormatUtil.getString("am.javaruntime.javaaction.threaddump"));
/* 1105 */       actionProfiles.put("threaddumpActions", javaActionProfile);
/*      */     }
/* 1107 */     if (!heapDump.isEmpty()) {
/* 1108 */       request.setAttribute("heapdumpActions", heapDump);
/* 1109 */       if (javaActionTabSelected.trim().equals("")) {
/* 1110 */         javaActionTabSelected = "am.javaruntime.javaaction.heapdump";
/*      */       }
/*      */       
/* 1113 */       Hashtable javaActionProfile = new Hashtable();
/* 1114 */       ArrayList<String> javaActionProps = new ArrayList();
/* 1115 */       javaActionProps.add("ID");
/* 1116 */       javaActionProps.add("NAME");
/* 1117 */       javaActionProps.add("DELAY");
/* 1118 */       javaActionProps.add("COUNT");
/* 1119 */       javaActionProps.add("TYPE");
/* 1120 */       javaActionProps.add("RESOURCENAME");
/* 1121 */       javaActionProps.add("ENAME");
/* 1122 */       javaActionProps.add("FROMADDRESS");
/* 1123 */       javaActionProps.add("TOADDRESS");
/* 1124 */       javaActionProfile.put("actionProps", javaActionProps);
/* 1125 */       javaActionProfile.put("Displayname", FormatUtil.getString("am.javaruntime.javaaction.heapdump"));
/* 1126 */       actionProfiles.put("heapdumpActions", javaActionProfile);
/*      */     }
/* 1128 */     if (!performGC.isEmpty()) {
/* 1129 */       request.setAttribute("performgcActions", performGC);
/* 1130 */       if (javaActionTabSelected.trim().equals("")) {
/* 1131 */         javaActionTabSelected = "am.javaruntime.javaaction.performgc";
/*      */       }
/*      */       
/* 1134 */       Hashtable javaActionProfile = new Hashtable();
/* 1135 */       ArrayList<String> javaActionProps = new ArrayList();
/* 1136 */       javaActionProps.add("ID");
/* 1137 */       javaActionProps.add("NAME");
/* 1138 */       javaActionProps.add("DELAY");
/* 1139 */       javaActionProps.add("COUNT");
/* 1140 */       javaActionProps.add("TYPE");
/* 1141 */       javaActionProps.add("RESOURCENAME");
/* 1142 */       javaActionProps.add("ENAME");
/* 1143 */       javaActionProps.add("FROMADDRESS");
/* 1144 */       javaActionProps.add("TOADDRESS");
/* 1145 */       javaActionProfile.put("actionProps", javaActionProps);
/* 1146 */       javaActionProfile.put("Displayname", FormatUtil.getString("am.javaruntime.javaaction.performgc"));
/* 1147 */       actionProfiles.put("performgcActions", javaActionProfile);
/*      */     }
/*      */     
/* 1150 */     request.setAttribute("selected", javaActionTabSelected);
/*      */     
/*      */ 
/*      */ 
/* 1154 */     if (!ec2InstanceActionlist.isEmpty()) {
/* 1155 */       request.setAttribute("ec2InastanceActions", ec2InstanceActionlist);
/*      */       
/* 1157 */       Hashtable serviceActionProfile = new Hashtable();
/* 1158 */       ArrayList<String> serviceActionProps = new ArrayList();
/* 1159 */       serviceActionProps.add("ID");
/* 1160 */       serviceActionProps.add("NAME");
/* 1161 */       serviceActionProps.add("TYPE");
/* 1162 */       serviceActionProps.add("ATYPE");
/* 1163 */       serviceActionProps.add("RESOURCENAME");
/* 1164 */       serviceActionProps.add("ENAME");
/* 1165 */       serviceActionProps.add("TOADDRESS");
/* 1166 */       serviceActionProfile.put("actionProps", serviceActionProps);
/* 1167 */       serviceActionProfile.put("Displayname", FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/* 1168 */       actionProfiles.put("ec2InastanceActions", serviceActionProfile);
/*      */     }
/* 1170 */     if (!aListWinServiceActions.isEmpty()) {
/* 1171 */       request.setAttribute("winServiceActions", aListWinServiceActions);
/*      */       
/* 1173 */       Hashtable serviceActionProfile = new Hashtable();
/* 1174 */       ArrayList<String> serviceActionProps = new ArrayList();
/* 1175 */       serviceActionProps.add("ID");
/* 1176 */       serviceActionProps.add("NAME");
/* 1177 */       serviceActionProps.add("TYPE");
/* 1178 */       serviceActionProps.add("ATYPE");
/* 1179 */       serviceActionProps.add("ENAME");
/* 1180 */       serviceActionProps.add("FROMADDRESS");
/* 1181 */       serviceActionProps.add("TOADDRESS");
/* 1182 */       serviceActionProfile.put("actionProps", serviceActionProps);
/* 1183 */       serviceActionProfile.put("Displayname", FormatUtil.getString("am.windowsservices.action.createnew"));
/* 1184 */       actionProfiles.put("winServiceActions", serviceActionProfile);
/*      */     }
/*      */     
/* 1187 */     if (!sqlJobActionList.isEmpty()) {
/* 1188 */       request.setAttribute("sqlJobActions", sqlJobActionList);
/*      */       
/* 1190 */       Hashtable serviceActionProfile = new Hashtable();
/* 1191 */       ArrayList<String> serviceActionProps = new ArrayList();
/* 1192 */       serviceActionProps.add("ID");
/* 1193 */       serviceActionProps.add("NAME");
/* 1194 */       serviceActionProps.add("TYPE");
/* 1195 */       serviceActionProps.add("ATYPE");
/* 1196 */       serviceActionProps.add("ENAME");
/* 1197 */       serviceActionProps.add("FROMADDRESS");
/* 1198 */       serviceActionProps.add("TOADDRESS");
/* 1199 */       serviceActionProfile.put("actionProps", serviceActionProps);
/* 1200 */       serviceActionProfile.put("Displayname", FormatUtil.getString("am.sqljob.action.createnew"));
/* 1201 */       actionProfiles.put("sqlJobActions", serviceActionProfile);
/*      */     }
/*      */     
/*      */ 
/* 1205 */     ArrayList startVM = new ArrayList();
/* 1206 */     ArrayList stopVM = new ArrayList();
/* 1207 */     ArrayList restartVM = new ArrayList();
/* 1208 */     ArrayList startContainer = new ArrayList();
/* 1209 */     ArrayList stopContainer = new ArrayList();
/* 1210 */     ArrayList restartContainer = new ArrayList();
/* 1211 */     ArrayList containerActionList = new ArrayList();
/* 1212 */     rs = null;
/* 1213 */     query = "select  AM_JREACTIONS.ID,THREADDUMP.NAME, AM_JREACTIONS.DELAY, AM_JREACTIONS.COUNT, AM_JREACTIONS.TYPE, AM_ManagedObject.DISPLAYNAME, ACTION.NAME as ENAME, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS,THREADDUMP.TYPE as ATYPE  from  AM_JREACTIONS left join AM_ACTIONPROFILE as THREADDUMP on AM_JREACTIONS.ID=THREADDUMP.ID left join AM_ManagedObject on AM_JREACTIONS.TARGET_RESID =AM_ManagedObject.RESOURCEID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID";
/* 1214 */     if (isPrivilegedUser) {
/* 1215 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_JREACTIONS.ID ", configIds) : "";
/*      */       
/* 1217 */       query = "select distinct AM_JREACTIONS.ID,THREADDUMP.NAME, AM_JREACTIONS.DELAY, AM_JREACTIONS.COUNT, AM_JREACTIONS.TYPE, AM_ManagedObject.DISPLAYNAME, ACTION.NAME as ENAME, THREADDUMP.TYPE as ATYPE from  AM_JREACTIONS left join AM_ACTIONPROFILE as THREADDUMP on AM_JREACTIONS.ID=THREADDUMP.ID left join AM_ManagedObject on AM_JREACTIONS.TARGET_RESID =AM_ManagedObject.RESOURCEID left join AM_ACTIONPROFILE as ACTION on AM_JREACTIONS.EMAIL_ACTION_ID=ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_JREACTIONS.EMAIL_ACTION_ID where AM_JREACTIONS.ID in (select distinct AM_JREACTIONS.ID from AM_JREACTIONS, AM_ATTRIBUTEACTIONMAPPER aam where AM_JREACTIONS.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond;
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1222 */       rs = AMConnectionPool.executeQueryStmt(query);
/* 1223 */       while (rs.next())
/*      */       {
/* 1225 */         ArrayList row = new ArrayList();
/* 1226 */         String actionType = rs.getString("ATYPE");
/* 1227 */         if ((actionType.equals("101")) || (actionType.equals("201")) || (actionType.equals("801")))
/*      */         {
/* 1229 */           row.add(rs.getString("ID"));
/* 1230 */           row.add(rs.getString("NAME"));
/* 1231 */           row.add(rs.getString("TYPE"));
/* 1232 */           if (actionType.equals("101"))
/*      */           {
/* 1234 */             row.add("ESX");
/*      */           }
/* 1236 */           if (actionType.equals("201"))
/*      */           {
/* 1238 */             row.add("Hyper-V");
/*      */           }
/* 1240 */           if (actionType.equals("801"))
/*      */           {
/* 1242 */             row.add("XenServer");
/*      */           }
/* 1244 */           row.add(FormatUtil.getString("am.vm.action.startvm"));
/* 1245 */           row.add(rs.getString("DELAY"));
/* 1246 */           if (rs.getString("DISPLAYNAME") != null)
/*      */           {
/* 1248 */             row.add(rs.getString("DISPLAYNAME"));
/*      */           }
/*      */           else
/*      */           {
/* 1252 */             row.add(FormatUtil.getString("am.vm.action.type1"));
/*      */           }
/* 1254 */           row.add(rs.getString("ENAME"));
/* 1255 */           startVM.add(row);
/*      */         }
/* 1257 */         else if ((actionType.equals("102")) || (actionType.equals("202")) || (actionType.equals("802")))
/*      */         {
/* 1259 */           row.add(rs.getString("ID"));
/* 1260 */           row.add(rs.getString("NAME"));
/* 1261 */           row.add(rs.getString("TYPE"));
/* 1262 */           if (actionType.equals("102"))
/*      */           {
/* 1264 */             row.add("ESX");
/*      */           }
/* 1266 */           if (actionType.equals("202"))
/*      */           {
/* 1268 */             row.add("Hyper-V");
/*      */           }
/* 1270 */           if (actionType.equals("802"))
/*      */           {
/* 1272 */             row.add("XenServer");
/*      */           }
/* 1274 */           row.add(FormatUtil.getString("am.vm.action.stopvm"));
/* 1275 */           row.add(rs.getString("DELAY"));
/* 1276 */           if (rs.getString("DISPLAYNAME") != null)
/*      */           {
/* 1278 */             row.add(rs.getString("DISPLAYNAME"));
/*      */           }
/*      */           else
/*      */           {
/* 1282 */             row.add(FormatUtil.getString("am.vm.action.type1"));
/*      */           }
/* 1284 */           row.add(rs.getString("ENAME"));
/* 1285 */           stopVM.add(row);
/*      */         }
/* 1287 */         else if ((actionType.equals("103")) || (actionType.equals("203")) || (actionType.equals("803")))
/*      */         {
/* 1289 */           row.add(rs.getString("ID"));
/* 1290 */           row.add(rs.getString("NAME"));
/* 1291 */           row.add(rs.getString("TYPE"));
/* 1292 */           if (actionType.equals("103"))
/*      */           {
/* 1294 */             row.add("ESX");
/*      */           }
/* 1296 */           if (actionType.equals("203"))
/*      */           {
/* 1298 */             row.add("Hyper-V");
/*      */           }
/* 1300 */           if (actionType.equals("803"))
/*      */           {
/* 1302 */             row.add("XenServer");
/*      */           }
/* 1304 */           row.add(FormatUtil.getString("am.vm.action.restartvm"));
/* 1305 */           row.add(rs.getString("DELAY"));
/* 1306 */           if (rs.getString("DISPLAYNAME") != null)
/*      */           {
/* 1308 */             row.add(rs.getString("DISPLAYNAME"));
/*      */           }
/*      */           else
/*      */           {
/* 1312 */             row.add(FormatUtil.getString("am.vm.action.type1"));
/*      */           }
/* 1314 */           row.add(rs.getString("ENAME"));
/* 1315 */           restartVM.add(row);
/* 1316 */         } else if ((actionType.equals("850")) || (actionType.equals("851")) || (actionType.equals("852"))) {
/*      */           try {
/* 1318 */             row.add(rs.getString("ID"));
/* 1319 */             row.add(rs.getString("NAME"));
/* 1320 */             row.add(rs.getString("TYPE"));
/* 1321 */             row.add("Docker");
/* 1322 */             String actionTypeText = actionType.equals("851") ? "am.docker.container.action.stop" : actionType.equals("850") ? "am.docker.container.action.start" : "am.docker.container.action.restart";
/* 1323 */             row.add(FormatUtil.getString(actionTypeText));
/* 1324 */             row.add(rs.getString("DELAY"));
/* 1325 */             row.add(rs.getString("DISPLAYNAME") != null ? rs.getString("DISPLAYNAME") : "am.docker.container.action.select.auto.text");
/* 1326 */             row.add(rs.getString("ENAME"));
/* 1327 */             ArrayList currentList = actionType.equals("851") ? stopContainer : actionType.equals("850") ? startContainer : restartContainer;
/* 1328 */             currentList.add(row);
/*      */           }
/*      */           catch (Exception e) {
/* 1331 */             e.printStackTrace();
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1340 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/* 1343 */       AMConnectionPool.closeStatement(rs);
/*      */     }
/*      */     
/* 1346 */     ArrayList combinedVmActionsList = new ArrayList();
/* 1347 */     if (!startVM.isEmpty()) {
/* 1348 */       combinedVmActionsList.addAll(startVM);
/*      */     }
/* 1350 */     if (!stopVM.isEmpty()) {
/* 1351 */       combinedVmActionsList.addAll(stopVM);
/*      */     }
/* 1353 */     if (!restartVM.isEmpty()) {
/* 1354 */       combinedVmActionsList.addAll(restartVM);
/*      */     }
/*      */     
/* 1357 */     if (!combinedVmActionsList.isEmpty())
/*      */     {
/* 1359 */       request.setAttribute("combinedVmActionsList", combinedVmActionsList);
/*      */       
/* 1361 */       Hashtable vmActionProfile = new Hashtable();
/* 1362 */       Object vmActionProps = new ArrayList();
/* 1363 */       ((ArrayList)vmActionProps).add("ID");
/* 1364 */       ((ArrayList)vmActionProps).add("NAME");
/* 1365 */       ((ArrayList)vmActionProps).add("TYPE");
/* 1366 */       ((ArrayList)vmActionProps).add("HOSTTYPE");
/* 1367 */       ((ArrayList)vmActionProps).add("ACTIONTYPE");
/* 1368 */       ((ArrayList)vmActionProps).add("DELAY");
/* 1369 */       ((ArrayList)vmActionProps).add("DISPLAYNAME");
/* 1370 */       ((ArrayList)vmActionProps).add("ENAME");
/* 1371 */       vmActionProfile.put("actionProps", vmActionProps);
/* 1372 */       vmActionProfile.put("Displayname", FormatUtil.getString("am.vm.action"));
/* 1373 */       actionProfiles.put("combinedVmActionsList", vmActionProfile);
/*      */     }
/* 1375 */     containerActionList.addAll(startContainer);
/* 1376 */     containerActionList.addAll(stopContainer);
/* 1377 */     containerActionList.addAll(restartContainer);
/*      */     
/* 1379 */     if (!containerActionList.isEmpty()) {
/* 1380 */       request.setAttribute("containerActionList", containerActionList);
/* 1381 */       Hashtable containerActionProfile = new Hashtable();
/* 1382 */       Object containerActionProps = new ArrayList();
/* 1383 */       ((ArrayList)containerActionProps).add("ID");
/* 1384 */       ((ArrayList)containerActionProps).add("NAME");
/* 1385 */       ((ArrayList)containerActionProps).add("TYPE");
/* 1386 */       ((ArrayList)containerActionProps).add("HOSTTYPE");
/* 1387 */       ((ArrayList)containerActionProps).add("ACTIONTYPE");
/* 1388 */       ((ArrayList)containerActionProps).add("DELAY");
/* 1389 */       ((ArrayList)containerActionProps).add("DISPLAYNAME");
/* 1390 */       ((ArrayList)containerActionProps).add("ENAME");
/* 1391 */       containerActionProfile.put("actionProps", containerActionProps);
/* 1392 */       containerActionProfile.put("Displayname", FormatUtil.getString("am.docker.container.actions.text"));
/* 1393 */       actionProfiles.put("containerActionList", containerActionProfile);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1400 */     query = "select AM_EMAILACTION.ID,NAME,CATEGORY, PRIORITY, TECHNICIAN, SUBJECT from AM_EMAILACTION,AM_ACTIONPROFILE,AM_SDESK_TICKET_DETAILS where AM_EMAILACTION.ID =AM_ACTIONPROFILE.ID and AM_SDESK_TICKET_DETAILS.ID=AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.TYPE in (6,17,19) order by AM_ACTIONPROFILE.NAME";
/* 1401 */     if (isPrivilegedUser) {
/* 1402 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_ACTIONPROFILE.ID ", configIds) : "";
/*      */       
/* 1404 */       query = "select AM_EMAILACTION.ID,NAME,CATEGORY, PRIORITY, TECHNICIAN, SUBJECT from AM_EMAILACTION,AM_ACTIONPROFILE,AM_SDESK_TICKET_DETAILS where AM_EMAILACTION.ID =AM_ACTIONPROFILE.ID and AM_SDESK_TICKET_DETAILS.ID=AM_ACTIONPROFILE.ID and AM_ACTIONPROFILE.TYPE in (6,17,19) and (AM_EMAILACTION.ID in (select AM_EMAILACTION.ID from AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER aam where AM_ACTIONPROFILE.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ") group by AM_EMAILACTION.ID,NAME,CATEGORY, PRIORITY, TECHNICIAN, SUBJECT order by AM_ACTIONPROFILE.NAME";
/*      */     }
/* 1406 */     rows = mo.getRows(query);
/* 1407 */     if (rows.size() > 0)
/*      */     {
/* 1409 */       request.setAttribute("ticketActions", rows);
/*      */       
/* 1411 */       Hashtable tktActionProfile = new Hashtable();
/* 1412 */       Object tktActionProps = new ArrayList();
/* 1413 */       ((ArrayList)tktActionProps).add("ID");
/* 1414 */       ((ArrayList)tktActionProps).add("NAME");
/* 1415 */       ((ArrayList)tktActionProps).add("CATEGORY");
/* 1416 */       ((ArrayList)tktActionProps).add("PRIORITY");
/* 1417 */       ((ArrayList)tktActionProps).add("TECHNICIAN");
/* 1418 */       ((ArrayList)tktActionProps).add("SUBJECT");
/* 1419 */       tktActionProfile.put("actionProps", tktActionProps);
/* 1420 */       tktActionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.ticket"));
/* 1421 */       actionProfiles.put("ticketActions", tktActionProfile);
/*      */     }
/*      */     try
/*      */     {
/* 1425 */       query = "select AM_SMSACTION.ID,NAME,FROMADDRESS, TOADDRESS,MESSAGE from AM_SMSACTION,AM_ACTIONPROFILE where AM_SMSACTION.ID =AM_ACTIONPROFILE.ID union select AM_SMSMODEMACTION.ID,NAME,'-',MOBILENO,MESSAGE from AM_SMSMODEMACTION,AM_ACTIONPROFILE where AM_SMSMODEMACTION.ID=AM_ACTIONPROFILE.ID";
/*      */       
/* 1427 */       if (isPrivilegedUser) {
/* 1428 */         configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_ACTIONPROFILE.ID ", configIds) : "";
/*      */         
/* 1430 */         query = "select AM_SMSACTION.ID,NAME,FROMADDRESS, TOADDRESS,MESSAGE from AM_SMSACTION,AM_ACTIONPROFILE where AM_SMSACTION.ID =AM_ACTIONPROFILE.ID and (AM_SMSACTION.ID in (select distinct AM_SMSACTION.ID from AM_SMSACTION,AM_ATTRIBUTEACTIONMAPPER aam where AM_SMSACTION.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ") " + configIdCond + ") group by name,AM_SMSACTION.ID,FROMADDRESS, TOADDRESS, MESSAGE union select AM_SMSMODEMACTION.ID,NAME,'-', MOBILENO,MESSAGE from AM_SMSMODEMACTION,AM_ACTIONPROFILE where AM_SMSMODEMACTION.ID =AM_ACTIONPROFILE.ID and (AM_SMSMODEMACTION.ID in (select AM_SMSMODEMACTION.ID from AM_SMSMODEMACTION,AM_ATTRIBUTEACTIONMAPPER aam where AM_SMSMODEMACTION.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ") " + configIdCond + ")";
/*      */       }
/*      */     } catch (Exception ex) {
/* 1433 */       ex.printStackTrace();
/*      */     }
/* 1435 */     rows = mo.getRows(query);
/* 1436 */     if (rows.size() > 0)
/*      */     {
/* 1438 */       Hashtable actionProfile = new Hashtable();
/* 1439 */       Object actionProps = new ArrayList();
/* 1440 */       ((ArrayList)actionProps).add("ID");
/* 1441 */       ((ArrayList)actionProps).add("NAME");
/* 1442 */       ((ArrayList)actionProps).add("FROMADDRESS");
/* 1443 */       ((ArrayList)actionProps).add("TOADDRESS");
/* 1444 */       ((ArrayList)actionProps).add("MESSAGE");
/* 1445 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.sms"));
/* 1446 */       actionProfile.put("actionProps", actionProps);
/* 1447 */       actionProfiles.put("smsActions", actionProfile);
/*      */       
/* 1449 */       request.setAttribute("smsActions", rows);
/*      */     }
/*      */     try
/*      */     {
/* 1453 */       query = "select AM_QUERYACTIONS.ID,QUERYPROFILE.NAME,AM_QUERYACTIONS.QUERY,ACTION.NAME, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS,QUERYPROFILE.TYPE as TYPE from AM_QUERYACTIONS left join AM_ACTIONPROFILE  as QUERYPROFILE  on AM_QUERYACTIONS.ID =QUERYPROFILE.ID left join AM_ACTIONPROFILE as ACTION on  AM_QUERYACTIONS.EMAIL_ACTION_ID =ACTION.ID left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_QUERYACTIONS.EMAIL_ACTION_ID ";
/*      */       
/* 1455 */       if (isPrivilegedUser) {
/* 1456 */         configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_QUERYACTIONS.ID ", configIds) : "";
/* 1457 */         query = "select AM_QUERYACTIONS.ID,QUERYPROFILE.NAME,AM_QUERYACTIONS.QUERY,ACTION.NAME, AM_EMAILACTION.FROMADDRESS, AM_EMAILACTION.TOADDRESS,QUERYPROFILE.TYPE as TYPE from AM_QUERYACTIONS left join AM_ACTIONPROFILE  as QUERYPROFILE  on AM_QUERYACTIONS.ID =QUERYPROFILE.ID left join AM_ACTIONPROFILE as ACTION on  AM_QUERYACTIONS.EMAIL_ACTION_ID =ACTION.ID  left join AM_EMAILACTION on AM_EMAILACTION.ID=AM_QUERYACTIONS.EMAIL_ACTION_ID  where AM_QUERYACTIONS.ID in (select distinct AM_QUERYACTIONS.ID from AM_QUERYACTIONS,AM_ATTRIBUTEACTIONMAPPER aam where AM_QUERYACTIONS.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ") " + configIdCond;
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1462 */       ex.printStackTrace();
/*      */     }
/* 1464 */     rows = mo.getRows(query);
/* 1465 */     if (rows.size() > 0)
/*      */     {
/* 1467 */       Hashtable actionProfile = new Hashtable();
/* 1468 */       Object actionProps = new ArrayList();
/* 1469 */       ((ArrayList)actionProps).add("ID");
/* 1470 */       ((ArrayList)actionProps).add("NAME");
/* 1471 */       ((ArrayList)actionProps).add("QUERY");
/* 1472 */       ((ArrayList)actionProps).add("EMAILACTIONNAME");
/* 1473 */       ((ArrayList)actionProps).add("FROMADDRESS");
/* 1474 */       ((ArrayList)actionProps).add("TOADDRESS");
/* 1475 */       ((ArrayList)actionProps).add("TYPE");
/* 1476 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.query"));
/* 1477 */       actionProfile.put("actionProps", actionProps);
/* 1478 */       actionProfiles.put("execQueryActions", actionProfile);
/*      */       
/* 1480 */       request.setAttribute("execQueryActions", rows);
/*      */     }
/*      */     
/*      */ 
/* 1484 */     query = "select AM_SERVERCMDACTION.ID,NAME,COMMAND,DIRTOEXECUTE,ABORTAFTER from AM_SERVERCMDACTION,AM_ACTIONPROFILE where AM_SERVERCMDACTION.ID =AM_ACTIONPROFILE.ID and AM_SERVERCMDACTION.COMMAND!='Restart The Service' order by AM_ACTIONPROFILE.NAME";
/* 1485 */     if (isPrivilegedUser) {
/* 1486 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_SERVERCMDACTION.ID ", configIds) : "";
/*      */       
/* 1488 */       query = "select AM_SERVERCMDACTION.ID,NAME,COMMAND,DIRTOEXECUTE,ABORTAFTER from AM_SERVERCMDACTION,AM_ACTIONPROFILE where AM_SERVERCMDACTION.ID =AM_ACTIONPROFILE.ID and AM_SERVERCMDACTION.COMMAND!='Restart The Service' and (AM_SERVERCMDACTION.ID in (select AM_ACTIONPROFILE.ID from AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER aam where AM_ACTIONPROFILE.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ") group by AM_SERVERCMDACTION.ID,NAME,COMMAND,DIRTOEXECUTE,ABORTAFTER order by AM_ACTIONPROFILE.NAME";
/*      */     }
/* 1490 */     rows = mo.getRows(query);
/* 1491 */     if (rows.size() > 0)
/*      */     {
/* 1493 */       Hashtable actionProfile = new Hashtable();
/* 1494 */       Object actionProps = new ArrayList();
/* 1495 */       ((ArrayList)actionProps).add("ID");
/* 1496 */       ((ArrayList)actionProps).add("NAME");
/* 1497 */       ((ArrayList)actionProps).add("COMMAND");
/* 1498 */       ((ArrayList)actionProps).add("DIRTOEXECUTE");
/* 1499 */       ((ArrayList)actionProps).add("ABORTAFTER");
/* 1500 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.program"));
/* 1501 */       actionProfile.put("actionProps", actionProps);
/* 1502 */       actionProfiles.put("execprogActions", actionProfile);
/*      */       
/* 1504 */       request.setAttribute("execprogActions", rows);
/*      */     }
/*      */     
/* 1507 */     ArrayList trapActions = new ArrayList();
/* 1508 */     query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '11' then 'v1' END,DESTINATIONHOST,DESTINATIONPORT from AM_V1_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V1_TRAPACTION.ID order by AM_ACTIONPROFILE.NAME";
/* 1509 */     if (isPrivilegedUser) {
/* 1510 */       configIdCond = isDelegatedAdmin ? DBUtil.getCondition(" or AM_ACTIONPROFILE.ID ", configIds) : "";
/*      */       
/* 1512 */       query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '11' then 'v1' END,DESTINATIONHOST,DESTINATIONPORT from AM_V1_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V1_TRAPACTION.ID and (AM_V1_TRAPACTION.ID in ( select AM_ACTIONPROFILE.ID from AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER aam where AM_ACTIONPROFILE.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ") group by name ,AM_ACTIONPROFILE.ID,type,DESTINATIONHOST ,DESTINATIONPORT order by AM_ACTIONPROFILE.NAME";
/*      */     }
/* 1514 */     ArrayList v1 = mo.getRows(query);
/* 1515 */     for (int i = 0; i < v1.size(); i++)
/*      */     {
/* 1517 */       trapActions.add(v1.get(i));
/*      */     }
/*      */     
/* 1520 */     query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '12' then 'v2c' END,DESTINATIONHOST,DESTINATIONPORT from AM_V2_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V2_TRAPACTION.ID order by AM_ACTIONPROFILE.NAME";
/* 1521 */     if (isPrivilegedUser)
/*      */     {
/* 1523 */       query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '12' then 'v2c' END,DESTINATIONHOST,DESTINATIONPORT from AM_V2_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V2_TRAPACTION.ID and (AM_V2_TRAPACTION.ID in ( select AM_ACTIONPROFILE.ID from AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER aam where AM_ACTIONPROFILE.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ") group by name ,AM_ACTIONPROFILE.ID,type,DESTINATIONHOST ,DESTINATIONPORT order by AM_ACTIONPROFILE.NAME";
/*      */     }
/* 1525 */     ArrayList v2 = mo.getRows(query);
/* 1526 */     for (int j = 0; j < v2.size(); j++)
/*      */     {
/* 1528 */       trapActions.add(v2.get(j));
/*      */     }
/*      */     
/* 1531 */     query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '13' then 'v3' END,DESTINATIONHOST,DESTINATIONPORT from AM_V3_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V3_TRAPACTION.ID order by AM_ACTIONPROFILE.NAME";
/* 1532 */     if (isPrivilegedUser)
/*      */     {
/* 1534 */       query = "select AM_ACTIONPROFILE.ID,NAME,case TYPE when '13' then 'v3' END,DESTINATIONHOST,DESTINATIONPORT from AM_V3_TRAPACTION,AM_ACTIONPROFILE where AM_ACTIONPROFILE.ID=AM_V3_TRAPACTION.ID and (AM_V3_TRAPACTION.ID in ( select AM_ACTIONPROFILE.ID from AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER aam where AM_ACTIONPROFILE.ID=aam.actionid and " + DependantMOUtil.getCondition("aam.ID", resids) + ")" + configIdCond + ") group by name ,AM_ACTIONPROFILE.ID,type,DESTINATIONHOST ,DESTINATIONPORT order by AM_ACTIONPROFILE.NAME";
/*      */     }
/* 1536 */     ArrayList v3 = mo.getRows(query);
/* 1537 */     for (int k = 0; k < v3.size(); k++)
/*      */     {
/* 1539 */       trapActions.add(v3.get(k));
/*      */     }
/* 1541 */     if (trapActions.size() > 0)
/*      */     {
/* 1543 */       Hashtable actionProfile = new Hashtable();
/* 1544 */       ArrayList<String> actionProps = new ArrayList();
/* 1545 */       actionProps.add("ID");
/* 1546 */       actionProps.add("NAME");
/* 1547 */       actionProps.add("TYPE");
/* 1548 */       actionProps.add("DESTINATIONHOST");
/* 1549 */       actionProps.add("DESTINATIONPORT");
/* 1550 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.trap"));
/* 1551 */       actionProfile.put("actionProps", actionProps);
/* 1552 */       actionProfiles.put("sendTrapActions", actionProfile);
/*      */       
/* 1554 */       request.setAttribute("sendTrapActions", trapActions);
/*      */     }
/*      */     
/* 1557 */     ArrayList mopActions = FaultUtil.getMBeanOperationActionsForResourceID(null, userName, isPrivilegedUser);
/* 1558 */     if (mopActions.size() > 0)
/*      */     {
/* 1560 */       Hashtable actionProfile = new Hashtable();
/* 1561 */       ArrayList<String> actionProps = new ArrayList();
/* 1562 */       actionProps.add("ID");
/* 1563 */       actionProps.add("NAME");
/* 1564 */       actionProps.add("TARGETNAME");
/* 1565 */       actionProps.add("GROUPNAME");
/* 1566 */       actionProps.add("OPERATIONNAME");
/* 1567 */       actionProps.add("ARGSCOUNT");
/* 1568 */       actionProfile.put("Displayname", FormatUtil.getString("am.webclient.viewaction.mbean"));
/* 1569 */       actionProfile.put("actionProps", actionProps);
/* 1570 */       actionProfiles.put("executeMopActions", actionProfile);
/*      */       
/* 1572 */       request.setAttribute("executeMopActions", mopActions);
/*      */     }
/*      */     
/* 1575 */     request.setAttribute("actionProfiles", actionProfiles);
/*      */   }
/*      */   
/*      */ 
/*      */   private static Object invokeConsoleAgentnonStaticMethod(String methodName, Class[] partypes, Object[] arglist)
/*      */   {
/* 1581 */     Object toReturn = null;
/* 1582 */     if (Constants.isIt360)
/*      */     {
/*      */       try
/*      */       {
/* 1586 */         Class consoleAgentClass = Class.forName("com.adventnet.console.agent.ConsoleAgent");
/* 1587 */         Method getInstanceMethod = consoleAgentClass.getMethod("getInstance", new Class[0]);
/* 1588 */         Object o = getInstanceMethod.invoke(consoleAgentClass, new Object[0]);
/* 1589 */         Method consoleAgentMethodToBeInvoked = consoleAgentClass.getMethod(methodName, partypes);
/* 1590 */         toReturn = consoleAgentMethodToBeInvoked.invoke(o, arglist);
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1595 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1598 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static Vector<String> getLicenseViolationMessages(Properties resourceTypeProps, String operation) {
/* 1602 */     Vector<String> toReturn = new Vector();
/* 1603 */     if ((Constants.isIt360) && (resourceTypeProps != null) && (!resourceTypeProps.isEmpty()) && (operation != null) && (!operation.trim().equals("")))
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/* 1608 */         Class[] partypes = new Class[2];
/* 1609 */         partypes[0] = Properties.class;
/* 1610 */         partypes[1] = String.class;
/* 1611 */         Object[] arglist = new Object[2];
/* 1612 */         arglist[0] = resourceTypeProps;
/* 1613 */         arglist[1] = new String(operation);
/*      */         
/* 1615 */         Object obj = invokeConsoleAgentnonStaticMethod("getLicenseViolationMessagesVector", partypes, arglist);
/* 1616 */         toReturn = obj != null ? (Vector)obj : toReturn;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1620 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1623 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static String getLicenseViolationMessage(String resourceType, String operation) {
/* 1627 */     String toReturn = "";
/* 1628 */     if ((!excludeLicenseCheck(resourceType)) && (Constants.isIt360) && (resourceType != null) && (!resourceType.trim().equals("")) && (operation != null) && (!operation.trim().equals("")))
/*      */     {
/*      */       try
/*      */       {
/* 1632 */         resourceType = getActualResourceType(resourceType);
/* 1633 */         Class[] partypes = new Class[2];
/* 1634 */         partypes[0] = String.class;
/* 1635 */         partypes[1] = String.class;
/* 1636 */         Object[] arglist = new Object[2];
/* 1637 */         arglist[0] = new String(resourceType);
/* 1638 */         arglist[1] = new String(operation);
/*      */         
/* 1640 */         Object obj = invokeConsoleAgentnonStaticMethod("getLicenseViolationMessage", partypes, arglist);
/* 1641 */         toReturn = obj != null ? obj.toString() : "";
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 1645 */         e.printStackTrace();
/*      */       }
/*      */     }
/* 1648 */     return toReturn;
/*      */   }
/*      */   
/*      */   public static boolean excludeLicenseCheck(String resourceType) {
/* 1652 */     return "All:1008".equalsIgnoreCase(resourceType);
/*      */   }
/*      */   
/*      */   public static String getActualResourceType(String type) {
/* 1656 */     String toReturn = type;
/* 1657 */     if ((Constants.isIt360) && (type != null) && (!type.trim().equals("")))
/*      */     {
/* 1659 */       ShowResourceDetails sh = new ShowResourceDetails();
/* 1660 */       List list = sh.getMonitorTypesforNewMonitor();
/* 1661 */       Properties props = new Properties();
/* 1662 */       for (Object temp : list)
/*      */       {
/* 1664 */         List subList = (List)temp;
/* 1665 */         String typ = (String)subList.get(1);
/* 1666 */         if (subList.size() <= 3)
/*      */         {
/* 1668 */           props.setProperty(typ, typ);
/*      */         }
/*      */         else
/*      */         {
/* 1672 */           String resourceType = (String)subList.get(3);
/* 1673 */           props.setProperty(typ, resourceType);
/*      */         } }
/* 1675 */       props.setProperty("Solaris", "SUN");
/* 1676 */       props.setProperty("windows XP", "Windows XP");
/* 1677 */       props.setProperty("MYSQLDB", "MYSQL-DB-server");
/* 1678 */       props.setProperty("DB2", "DB2-server");
/* 1679 */       props.setProperty("MSSQLDB", "MSSQL-DB-server");
/* 1680 */       props.setProperty("ORACLEDB", "ORACLE-DB-server");
/* 1681 */       props.setProperty("SYBASEDB", "SYBASE-DB-server");
/* 1682 */       props.setProperty("WebSphere", "WebSphere-server");
/* 1683 */       props.setProperty("WebLogic", "WEBLOGIC-server");
/* 1684 */       props.setProperty("WLI", "WEBLOGIC-Integration");
/* 1685 */       props.setProperty("Tomcat", "Tomcat-server");
/* 1686 */       props.setProperty("ORACLEAS", "ORACLE-APP-server");
/* 1687 */       props.setProperty("JBoss", "JBOSS-server");
/* 1688 */       props.setProperty("SERVICE", "Port-Test");
/* 1689 */       props.setProperty("WEB", "WEB-server");
/*      */       
/* 1691 */       if (props.containsKey(type))
/*      */       {
/* 1693 */         toReturn = props.getProperty(type);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1698 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public static void sendResponse(String message, HttpServletResponse response)
/*      */   {
/*      */     try
/*      */     {
/* 1706 */       response.setCharacterEncoding("UTF-8");
/*      */       
/* 1708 */       PrintWriter out = response.getWriter();
/* 1709 */       out.println(message);
/* 1710 */       out.flush();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1714 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getI18NedTemplate()
/*      */   {
/* 1720 */     String firstLine = FormatUtil.getString("Hello,");
/* 1721 */     return getI18NedTemplate(firstLine, 0);
/*      */   }
/*      */   
/*      */   public static String getI18NedTemplate(String firstLine, int demovideoType)
/*      */   {
/* 1726 */     String demoVideoLinks = null;
/* 1727 */     if (demovideoType > 0)
/*      */     {
/* 1729 */       if (demovideoType == 1)
/*      */       {
/* 1731 */         demoVideoLinks = "";
/*      */       }
/* 1733 */       else if (demovideoType == 2)
/*      */       {
/* 1735 */         demoVideoLinks = "";
/*      */       }
/*      */     }
/* 1738 */     return getI18NedTemplate(firstLine, demoVideoLinks);
/*      */   }
/*      */   
/*      */   public static String getI18NedTemplate(String firstLine, String demoVideoLinks)
/*      */   {
/* 1743 */     String productname = OEMUtil.getOEMString("ManageEngine") + " " + OEMUtil.getOEMString("product.name");
/* 1744 */     String host = Constants.getAppHostName();
/* 1745 */     String port = System.getProperty("webserver.port");
/*      */     
/* 1747 */     String iI8nedTemplate = getDefaultMailTemplate();
/* 1748 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~wishes~", firstLine != null ? firstLine : "");
/* 1749 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~signature~", productname);
/* 1750 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~footer~", FormatUtil.getString("am.product.footer.txt", new String[] { "~host~", "~port~" }));
/* 1751 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~unsubscribe~", FormatUtil.getString("am.product.unsubscribe.txt"));
/* 1752 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~demovideolinks~", demoVideoLinks != null ? demoVideoLinks : "");
/* 1753 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~host~", host);
/* 1754 */     iI8nedTemplate = FormatUtil.findAndReplaceAll(iI8nedTemplate, "~port~", port);
/*      */     
/* 1756 */     return iI8nedTemplate;
/*      */   }
/*      */   
/*      */   public static String getDefaultMailTemplate()
/*      */   {
/* 1761 */     return "~wishes~ \r\n\n \t~content~ \r\n \n\n - ~signature~ \r \n \n ~footer~ \r\n ~unsubscribe~ \r\n\r\n ~demovideolinks~";
/*      */   }
/*      */   
/*      */ 
/*      */   public static boolean triggerDataChangeEvent(String tableName, String changeType, ArrayList<String> pk1, ArrayList<String> pk2, ArrayList<String> pk3)
/*      */   {
/* 1767 */     if ((pk1 == null) || (!tableName.equalsIgnoreCase("AM_ManagedObject")))
/*      */     {
/* 1769 */       return false;
/*      */     }
/* 1771 */     if (pk2 == null)
/*      */     {
/* 1773 */       pk2 = new ArrayList();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1778 */     String dbType = System.getProperty("am.dbserver.type");
/*      */     
/* 1780 */     if ((dbType.equalsIgnoreCase("pgsql")) || (dbType.equalsIgnoreCase("mysql")))
/*      */     {
/* 1782 */       tableName = "am_managedobject";
/*      */     }
/*      */     else
/*      */     {
/* 1786 */       tableName = "AM_ManagedObject";
/*      */     }
/*      */     
/* 1789 */     String query = null;
/* 1790 */     Statement toinsert = null;
/* 1791 */     ResultSet rs1 = null;
/* 1792 */     ResultSet rs2 = null;
/*      */     try {
/* 1794 */       toinsert = AMConnectionPool.getConnection().createStatement();
/* 1795 */       int pkSize = pk1.size();
/* 1796 */       for (int i = 0; i < pkSize; i++) {
/* 1797 */         query = "insert into TRIGGER_LOG (TABLENAME,OPERATION,PK1,PK2,PK3) values ('" + tableName + "','" + changeType + "','" + (String)pk1.get(i) + "',' ',' ');";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1805 */         toinsert.addBatch(query);
/*      */       }
/* 1807 */       toinsert.executeBatch();
/* 1808 */       toinsert.clearBatch();
/*      */       
/*      */ 
/* 1811 */       String resourceCon = getResourceCondition(pk1);
/*      */       
/*      */ 
/*      */ 
/* 1815 */       pk1.clear();
/* 1816 */       pk2.clear();
/* 1817 */       query = "select PRODUCTID,NAME from ExternalDeviceDetails where RID in " + resourceCon;
/* 1818 */       rs1 = AMConnectionPool.executeQueryStmt(query);
/* 1819 */       while (rs1.next())
/*      */       {
/* 1821 */         if (dbType.equalsIgnoreCase("pgsql"))
/*      */         {
/* 1823 */           pk1.add(rs1.getString("PRODUCTID"));
/* 1824 */           pk2.add(rs1.getString("NAME"));
/*      */         }
/*      */         else
/*      */         {
/* 1828 */           pk1.add(rs1.getString("NAME"));
/* 1829 */           pk2.add(rs1.getString("PRODUCTID"));
/*      */         }
/*      */       }
/* 1832 */       rs1.close();
/*      */       
/* 1834 */       if (tableName.equals("am_managedobject")) {
/* 1835 */         tableName = "externaldevicedetails";
/*      */       } else {
/* 1837 */         tableName = "ExternalDeviceDetails";
/*      */       }
/*      */       
/* 1840 */       pkSize = pk1.size();
/* 1841 */       for (int i = 0; i < pkSize; i++) {
/* 1842 */         query = "insert into TRIGGER_LOG (TABLENAME,OPERATION,PK1,PK2,PK3) values ('" + tableName + "','" + changeType + "','" + (String)pk1.get(i) + "','" + (String)pk2.get(i) + "',' ');";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1850 */         toinsert.addBatch(query);
/*      */       }
/* 1852 */       toinsert.executeBatch();
/* 1853 */       toinsert.clearBatch();
/*      */       
/*      */ 
/*      */ 
/* 1857 */       pk1.clear();
/* 1858 */       pk2.clear();
/* 1859 */       query = "select NAME,OWNERNAME from TopoObject,AM_ManagedObject where TopoObject.NAME=AM_ManagedObject.RESOURCENAME AND AM_ManagedObject.RESOURCEID in " + resourceCon;
/* 1860 */       rs2 = AMConnectionPool.executeQueryStmt(query);
/* 1861 */       while (rs2.next())
/*      */       {
/* 1863 */         if (dbType.equalsIgnoreCase("pgsql"))
/*      */         {
/* 1865 */           pk1.add(rs2.getString("OWNERNAME"));
/* 1866 */           pk2.add(rs2.getString("NAME"));
/*      */         }
/*      */         else
/*      */         {
/* 1870 */           pk1.add(rs2.getString("NAME"));
/* 1871 */           pk2.add(rs2.getString("OWNERNAME"));
/*      */         }
/*      */       }
/* 1874 */       rs2.close();
/*      */       
/* 1876 */       if (tableName.equals("externaldevicedetails")) {
/* 1877 */         tableName = "topoobject";
/*      */       } else {
/* 1879 */         tableName = "TopoObject";
/*      */       }
/*      */       
/* 1882 */       pkSize = pk1.size();
/* 1883 */       for (int i = 0; i < pkSize; i++) {
/* 1884 */         query = "insert into TRIGGER_LOG (TABLENAME,OPERATION,PK1,PK2,PK3) values ('" + tableName + "','" + changeType + "','" + (String)pk1.get(i) + "','" + (String)pk2.get(i) + "',' ');";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1892 */         toinsert.addBatch(query);
/*      */       }
/* 1894 */       toinsert.executeBatch();
/* 1895 */       toinsert.clearBatch();
/*      */       
/* 1897 */       toinsert.close();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1922 */       return true;
/*      */     }
/*      */     catch (Exception exc)
/*      */     {
/* 1900 */       exc.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try {
/* 1905 */         if (toinsert != null)
/*      */         {
/* 1907 */           toinsert.close();
/*      */         }
/* 1909 */         if (rs1 != null)
/*      */         {
/* 1911 */           rs1.close();
/*      */         }
/* 1913 */         if (rs2 != null)
/*      */         {
/* 1915 */           rs2.close();
/*      */         }
/*      */       }
/*      */       catch (SQLException e) {
/* 1919 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getResourceCondition(ArrayList<String> indexInfos)
/*      */   {
/* 1927 */     int size = indexInfos.size();
/* 1928 */     boolean isFirstTime = true;
/* 1929 */     StringBuffer buffer = new StringBuffer();
/* 1930 */     buffer.append("(");
/* 1931 */     for (int k = 0; k < size; k++) {
/* 1932 */       String resourceID = (String)indexInfos.get(k);
/*      */       
/* 1934 */       if (isFirstTime) {
/* 1935 */         buffer.append("'" + resourceID + "'");
/* 1936 */         isFirstTime = false;
/*      */       } else {
/* 1938 */         buffer.append(",'" + resourceID + "'");
/*      */       }
/*      */     }
/*      */     
/* 1942 */     buffer.append(")");
/* 1943 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(HttpServletRequest request) {
/* 1947 */     return getAllMonitorsInHost(request, true);
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(HttpServletRequest request, boolean needDefaultServices) {
/* 1951 */     String resourceId = request.getParameter("resourceid");
/* 1952 */     String serverName = request.getParameter("resourcename");
/* 1953 */     boolean isAdminRole = !isPrivilegedUser(request);
/* 1954 */     String owner = isAdminRole ? "admin" : request.getRemoteUser();
/* 1955 */     return getAllMonitorsInHost(serverName, resourceId, owner, isAdminRole, needDefaultServices);
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(String serverName, String owner, boolean isAdminRole) {
/* 1959 */     return getAllMonitorsInHost(serverName, null, owner, isAdminRole, true);
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(String serverName, String owner, boolean isAdminRole, boolean needDefaultServices) {
/* 1963 */     return getAllMonitorsInHost(serverName, null, owner, isAdminRole, needDefaultServices);
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(String serverName, String resourceid, String owner, boolean isAdminRole) {
/* 1967 */     return getAllMonitorsInHost(serverName, resourceid, owner, isAdminRole, true);
/*      */   }
/*      */   
/*      */   public static List<Map<String, String>> getAllMonitorsInHost(String serverName, String resourceid, String owner, boolean isAdminRole, boolean needDefaultServices)
/*      */   {
/* 1972 */     AMLog.info("\n ClientDBUtil: getAllMonitorsInHost:" + serverName);
/* 1973 */     List<Map<String, String>> servicesInHost = null;
/*      */     try
/*      */     {
/* 1976 */       if (needDefaultServices)
/*      */       {
/* 1978 */         servicesInHost = isAdminRole ? ViewsCreator.getServicesInHost(resourceid, serverName) : ViewsCreator.getServicesInHostForOwner(serverName, owner);
/*      */       }
/* 1980 */       AMLog.info("\n ClientDBUtil: getAllMonitorsInHost: step 1:" + servicesInHost);
/*      */       
/*      */ 
/* 1983 */       List<Map<String, String>> monitorList = ViewsCreator.getConfBasedMonitorsForHost(serverName, resourceid, owner);
/* 1984 */       if ((monitorList != null) && (monitorList.size() > 0)) {
/* 1985 */         if (servicesInHost != null) {
/* 1986 */           servicesInHost.addAll(monitorList);
/*      */         } else {
/* 1988 */           servicesInHost = monitorList;
/*      */         }
/*      */       }
/* 1991 */       AMLog.info("\n ClientDBUtil: getAllMonitorsInHost: step 2: associated conf monitors" + monitorList);
/*      */       
/*      */ 
/* 1994 */       monitorList = ViewsCreator.getAssociatedMonitorsForHost(serverName, resourceid, owner);
/* 1995 */       if ((monitorList != null) && (monitorList.size() > 0)) {
/* 1996 */         if (servicesInHost != null) {
/* 1997 */           servicesInHost.addAll(monitorList);
/*      */         } else {
/* 1999 */           servicesInHost = monitorList;
/*      */         }
/*      */       }
/* 2002 */       AMLog.info("\n ClientDBUtil: getAllMonitorsInHost: step 3: associated url and script monitors" + monitorList);
/*      */       
/*      */ 
/* 2005 */       monitorList = ViewsCreator.getFileDirMonitorsForHost(serverName, resourceid, owner);
/* 2006 */       if ((monitorList != null) && (monitorList.size() > 0)) {
/* 2007 */         if (servicesInHost != null) {
/* 2008 */           servicesInHost.addAll(monitorList);
/*      */         } else {
/* 2010 */           servicesInHost = monitorList;
/*      */         }
/*      */       }
/* 2013 */       AMLog.info("\n ClientDBUtil: getAllMonitorsInHost: step 4: associated File/Directory monitors" + monitorList);
/*      */       
/*      */ 
/* 2016 */       monitorList = ViewsCreator.getCustomMonitorsForHost(serverName, resourceid, owner);
/* 2017 */       if ((monitorList != null) && (monitorList.size() > 0)) {
/* 2018 */         if (servicesInHost != null) {
/* 2019 */           servicesInHost.addAll(monitorList);
/*      */         } else {
/* 2021 */           servicesInHost = monitorList;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2027 */       e.printStackTrace();
/*      */     }
/*      */     
/* 2030 */     AMLog.info("\n ClientDBUtil: getAllMonitorsInHost: {" + serverName + "} : final list of monitors:" + servicesInHost);
/* 2031 */     return servicesInHost;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\beans\ClientDBUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */