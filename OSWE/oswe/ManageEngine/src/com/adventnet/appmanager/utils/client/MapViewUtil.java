/*      */ package com.adventnet.appmanager.utils.client;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.DBQueryUtil;
/*      */ import com.adventnet.appmanager.fault.AMAttributesDependencyAdder;
/*      */ import com.adventnet.appmanager.fault.AMRCAnalyser;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.MapViewServerUtil;
/*      */ import com.adventnet.appmanager.server.framework.extprod.ProdIntegThread;
/*      */ import com.adventnet.appmanager.struts.actions.MapViewActions;
/*      */ import com.adventnet.appmanager.struts.actions.ShowResourceDetails;
/*      */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*      */ import com.adventnet.appmanager.util.BSIntegUtil;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.MGActionNotifier;
/*      */ import com.adventnet.appmanager.util.MGUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.manageengine.it360.sp.util.It360SPUserManagementUtil;
/*      */ import java.io.File;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.json.JSONArray;
/*      */ import org.json.JSONException;
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
/*      */ public class MapViewUtil
/*      */ {
/*      */   public static final String MAPVIEW = "MAPVIEW";
/*      */   public static final String MAPVIEWID = "MAPVIEWID";
/*      */   public static final String MAPVIEWNAME = "MAPVIEWNAME";
/*      */   public static final String DISPLAYNAME = "DISPLAYNAME";
/*      */   public static final String BACKGROUNDIMAGE = "BACKGROUNDIMAGE";
/*      */   public static final String STATUS = "STATUS";
/*      */   public static final String MAPVIEWDEVICE = "MAPVIEWDEVICE";
/*      */   public static final String DEVICENAME = "DEVICENAME";
/*      */   public static final String DEVICEID = "DEVICEID";
/*      */   public static final String DEVICEPROPS = "DEVICEPROPS";
/*      */   public static final String MAPVIEWLINK = "MAPVIEWLINK";
/*      */   public static final String LINKNAME = "LINKNAME";
/*      */   public static final String LINKID = "LINKID";
/*      */   public static final String IFNAME = "IFNAME";
/*      */   public static final String SOURCE = "SOURCE";
/*      */   public static final String DESTINATION = "DESTINATION";
/*      */   public static final String LINKPROPS = "LINKPROPS";
/*      */   public static final String MAPVIEWSHORTCUT = "MAPVIEWSHORTCUT";
/*      */   public static final String SHORTCUTNAME = "SHORTCUTNAME";
/*      */   public static final String SHORTCUTID = "SHORTCUTID";
/*      */   public static final String SUBMAP = "SUBMAP";
/*      */   public static final String SHORTCUTPROPS = "SHORTCUTPROPS";
/*      */   public static final String MAPVIEWMAPPING = "MAPVIEWMAPPING";
/*      */   public static final String USERNAME = "USERNAME";
/*   95 */   public static String dbType = System.getProperty("am.dbserver.type");
/*   96 */   public static boolean isMySQL = (dbType != null) && (dbType.equalsIgnoreCase("mysql"));
/*   97 */   public static boolean isMSSQL = (dbType != null) && (dbType.equalsIgnoreCase("mssql"));
/*   98 */   public static boolean isPgSql = (dbType != null) && (dbType.equalsIgnoreCase("pgsql"));
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getLinkName(String srcDevName, String desDevName, String ifName)
/*      */   {
/*  171 */     return srcDevName + "~" + desDevName + "~" + ifName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getListOfMapViews(String userName, HttpServletRequest request)
/*      */   {
/*  182 */     ResultSet rs = null;
/*  183 */     String userId = DBUtil.getUserID(userName);
/*  184 */     StringBuffer sb = new StringBuffer();
/*  185 */     String toReturn = "";
/*  186 */     ArrayList<String> mapViewNameList = new ArrayList();
/*  187 */     It360SPUserManagementUtil spum = new It360SPUserManagementUtil();
/*  188 */     boolean isBSGUser = spum.isBSGUser(userName);
/*  189 */     if ((userName != null) && (!userName.equals("")))
/*      */     {
/*  191 */       boolean isPrivilegedToAllComponents = new It360SPUserManagementUtil().isPrivilegedToAllComponents(userName);
/*      */       try
/*      */       {
/*  194 */         String selectQuery = "select MAPVIEWNAME from MAPVIEWMAPPING mvum ,MAPVIEW mv where mv.MAPVIEWID=mvum.MAPVIEWID";
/*  195 */         if (!isPrivilegedToAllComponents)
/*      */         {
/*  197 */           selectQuery = "select MAPVIEWNAME from AM_HOLISTICAPPLICATION_OWNERS inner join MAPVIEWMAPPING mvm on HAID=BSGID inner join MAPVIEW mv on mvm.MAPVIEWID=mv.MAPVIEWID where OWNERID=" + userId;
/*      */         }
/*  199 */         if ((EnterpriseUtil.isIt360MSPEdition) && (!isBSGUser))
/*      */         {
/*  201 */           int customerId = -1;
/*  202 */           Properties custProps = EnterpriseUtil.getCustProp(request);
/*  203 */           if (custProps != null)
/*      */           {
/*  205 */             Enumeration custEnum = custProps.keys();
/*  206 */             while (custEnum.hasMoreElements())
/*      */             {
/*      */ 
/*  209 */               customerId = Integer.parseInt((String)custProps.get(custEnum.nextElement()));
/*      */             }
/*      */           }
/*  212 */           selectQuery = "select MAPVIEWNAME from MAPVIEW mv inner join MAPVIEWMAPPING mvum on mv.MAPVIEWID=mvum.MAPVIEWID inner join AM_PARENTCHILDMAPPER on BSGID=CHILDID where PARENTID=" + customerId;
/*  213 */           if (!isPrivilegedToAllComponents)
/*      */           {
/*  215 */             selectQuery = "select MAPVIEWNAME from AM_HOLISTICAPPLICATION_OWNERS inner join MAPVIEWMAPPING mvm on HAID=BSGID inner join AM_PARENTCHILDMAPPER on mvm.BSGID=CHILDID inner join MAPVIEW mv on mvm.MAPVIEWID=mv.MAPVIEWID where PARENTID=" + customerId + " and OWNERID=" + userId;
/*      */           }
/*      */         }
/*  218 */         rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  219 */         while (rs.next()) {
/*  220 */           sb.append(rs.getString("MAPVIEWNAME") + ",");
/*  221 */           mapViewNameList.add(rs.getString("MAPVIEWNAME"));
/*      */         }
/*      */         
/*  224 */         if ((!isBSGUser) && ((!isPrivilegedToAllComponents) || (EnterpriseUtil.isIt360MSPEdition)))
/*      */         {
/*  226 */           for (Object mapViewName : mapViewNameList)
/*      */           {
/*  228 */             String bsgId = getBSGIdForMapName((String)mapViewName);
/*  229 */             Vector<String> scIds = new Vector();
/*      */             
/*  231 */             ManagedApplication.getChildIDs(scIds, bsgId);
/*      */             
/*  233 */             if ((scIds != null) && (scIds.size() > 0))
/*      */             {
/*  235 */               String scIdString = Constants.convertVectorToCSV(scIds);
/*  236 */               String scNamesQuery = "select MAPVIEWNAME from MAPVIEW mv inner join MAPVIEWMAPPING mvm on mv.MAPVIEWID=mvm.MAPVIEWID and mvm.BSGID in (" + scIdString + ")";
/*  237 */               rs = AMConnectionPool.executeQueryStmt(scNamesQuery);
/*  238 */               while (rs.next())
/*      */               {
/*  240 */                 sb.append(rs.getString("MAPVIEWNAME") + ",");
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  246 */         toReturn = sb.toString();
/*  247 */         if (!toReturn.trim().equals(""))
/*      */         {
/*  249 */           toReturn = toReturn.substring(0, toReturn.length() - 1);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  257 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/*  261 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  265 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  270 */         AMLog.debug("MAPVIEWUTIL:Mapviews present are " + toReturn);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  253 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  257 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/*  261 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  265 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  271 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getListOfMapViewsForSC(String mapViewName, String userName, HttpServletRequest request)
/*      */   {
/*  277 */     ResultSet rs = null;
/*  278 */     String userId = DBUtil.getUserID(userName);
/*  279 */     StringBuffer sb = new StringBuffer();
/*  280 */     String toReturn = null;
/*  281 */     ArrayList<String> mapViewNameList = new ArrayList();
/*  282 */     It360SPUserManagementUtil spum = new It360SPUserManagementUtil();
/*  283 */     boolean isBSGUser = spum.isBSGUser(userName);
/*  284 */     if ((userName != null) && (!userName.equals("")))
/*      */     {
/*  286 */       boolean isPrivilegedToAllComponents = new It360SPUserManagementUtil().isPrivilegedToAllComponents(userName);
/*      */       try
/*      */       {
/*  289 */         String selectQuery = "SELECT MAPVIEWNAME FROM MAPVIEW LEFT JOIN MAPVIEWSHORTCUT ON MAPVIEW.MAPVIEWNAME = MAPVIEWSHORTCUT.SUBMAP INNER JOIN MAPVIEWMAPPING ON MAPVIEW.MAPVIEWID=MAPVIEWMAPPING.MAPVIEWID WHERE MAPVIEWSHORTCUT.SUBMAP IS NULL";
/*  290 */         if (!isPrivilegedToAllComponents)
/*      */         {
/*  292 */           selectQuery = "SELECT MAPVIEWNAME FROM MAPVIEW LEFT JOIN MAPVIEWSHORTCUT ON MAPVIEW.MAPVIEWNAME = MAPVIEWSHORTCUT.SUBMAP INNER JOIN MAPVIEWMAPPING ON MAPVIEW.MAPVIEWID=MAPVIEWMAPPING.MAPVIEWID  INNER JOIN AM_HOLISTICAPPLICATION_OWNERS AMHO ON MAPVIEWMAPPING.BSGID=AMHO.HAID WHERE MAPVIEWSHORTCUT.SUBMAP IS NULL and AMHO.OWNERID=" + userId;
/*      */         }
/*  294 */         if ((EnterpriseUtil.isIt360MSPEdition) && (!isBSGUser))
/*      */         {
/*  296 */           int customerId = -1;
/*  297 */           Properties custProps = EnterpriseUtil.getCustProp(request);
/*  298 */           if (custProps != null)
/*      */           {
/*  300 */             Enumeration custEnum = custProps.keys();
/*  301 */             while (custEnum.hasMoreElements())
/*      */             {
/*      */ 
/*  304 */               customerId = Integer.parseInt((String)custProps.get(custEnum.nextElement()));
/*      */             }
/*      */           }
/*  307 */           selectQuery = "SELECT MAPVIEWNAME FROM MAPVIEW LEFT JOIN MAPVIEWSHORTCUT ON MAPVIEW.MAPVIEWNAME = MAPVIEWSHORTCUT.SUBMAP INNER JOIN MAPVIEWMAPPING ON MAPVIEW.MAPVIEWID=MAPVIEWMAPPING.MAPVIEWID inner join AM_PARENTCHILDMAPPER on BSGID=CHILDID where PARENTID=" + customerId + " and MAPVIEWSHORTCUT.SUBMAP IS NULL";
/*  308 */           if (!isPrivilegedToAllComponents)
/*      */           {
/*  310 */             selectQuery = "SELECT MAPVIEWNAME FROM MAPVIEW LEFT JOIN MAPVIEWSHORTCUT ON MAPVIEW.MAPVIEWNAME = MAPVIEWSHORTCUT.SUBMAP INNER JOIN MAPVIEWMAPPING ON MAPVIEW.MAPVIEWID=MAPVIEWMAPPING.MAPVIEWID  INNER JOIN AM_HOLISTICAPPLICATION_OWNERS AMHO ON MAPVIEWMAPPING.BSGID=AMHO.HAID inner join AM_PARENTCHILDMAPPER on BSGID=CHILDID where PARENTID=" + customerId + " and MAPVIEWSHORTCUT.SUBMAP IS NULL and AMHO.OWNERID=" + userId;
/*      */           }
/*      */         }
/*  313 */         rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  314 */         while (rs.next()) {
/*  315 */           mapViewNameList.add(rs.getString("MAPVIEWNAME"));
/*      */         }
/*      */         
/*  318 */         ArrayList<String> parentList = new ArrayList();
/*  319 */         getParentMap(mapViewName, parentList);
/*  320 */         String parentMapName = parentList.size() > 0 ? (String)parentList.get(0) : mapViewName;
/*  321 */         if ((parentMapName != null) && (mapViewNameList.contains(parentMapName)))
/*      */         {
/*  323 */           mapViewNameList.remove(parentMapName);
/*      */         }
/*      */         
/*  326 */         for (Object name : mapViewNameList)
/*      */         {
/*  328 */           sb.append((String)name + ",");
/*      */         }
/*      */         
/*  331 */         toReturn = sb.toString();
/*  332 */         if (!toReturn.trim().equals(""))
/*      */         {
/*  334 */           toReturn = toReturn.substring(0, toReturn.length() - 1);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  342 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/*  346 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  350 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  355 */         AMLog.debug("MAPVIEWUTIL:Mapviews present for adding SC are " + toReturn);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  338 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*  342 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/*  346 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*  350 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  356 */     return toReturn;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void getParentMap(String mapViewName, ArrayList<String> parentList)
/*      */   {
/*  362 */     if ((mapViewName != null) && (!mapViewName.trim().equals("")))
/*      */     {
/*  364 */       int count = 0;
/*      */       try
/*      */       {
/*  367 */         ResultSet rs = null;
/*      */         try
/*      */         {
/*  370 */           String subMapQuery = "select MAPVIEWID from MAPVIEWSHORTCUT where SUBMAP='" + mapViewName + "'";
/*  371 */           rs = AMConnectionPool.executeQueryStmt(subMapQuery);
/*  372 */           while (rs.next()) {
/*  373 */             int mapViewId = rs.getInt("MAPVIEWID");
/*  374 */             mapViewName = getMapViewName(mapViewId);
/*  375 */             count++;
/*      */           }
/*  377 */           if (count == 0)
/*      */           {
/*  379 */             parentList.add(mapViewName);
/*      */           }
/*      */           else
/*  382 */             getParentMap(mapViewName, parentList);
/*      */         } catch (Exception ex) {
/*  384 */           ex.printStackTrace();
/*      */         }
/*      */         finally {
/*  387 */           if (rs != null) {
/*  388 */             rs.close();
/*      */           }
/*      */         }
/*      */       } catch (Exception e) {
/*  392 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static String getMapViewName(int mapViewId)
/*      */   {
/*  399 */     ResultSet rs = null;
/*  400 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*      */     try
/*      */     {
/*  404 */       String selectQuery = "select MAPVIEWNAME from MAPVIEW where MAPVIEWID=" + mapViewId;
/*  405 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*  406 */       if (rs.next()) {
/*  407 */         return rs.getString("MAPVIEWNAME");
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
/*  427 */       return null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  411 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  415 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  419 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  423 */           e.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getBSGIdForMapName(String mvName)
/*      */   {
/*  478 */     ResultSet rs = null;
/*  479 */     bsgId = null;
/*      */     try
/*      */     {
/*  482 */       if ((mvName != null) && (!mvName.equals("")))
/*      */       {
/*  484 */         String bsgIdQuery = "select BSGID from MAPVIEWMAPPING,MAPVIEW where MAPVIEW.MAPVIEWNAME='" + mvName + "' and MAPVIEWMAPPING.MAPVIEWID = MAPVIEW.MAPVIEWID";
/*  485 */         rs = AMConnectionPool.executeQueryStmt(bsgIdQuery);
/*  486 */         while (rs.next())
/*      */         {
/*  488 */           bsgId = rs.getString("BSGID");
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
/*  511 */       return bsgId;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  495 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  499 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  503 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  507 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getBSGIdForMapViewId(String mvId)
/*      */   {
/*  519 */     ResultSet rs = null;
/*  520 */     bsgId = null;
/*      */     try
/*      */     {
/*  523 */       if ((mvId != null) && (!mvId.equals("")))
/*      */       {
/*  525 */         String bsgIdQuery = "select BSGID from MAPVIEWMAPPING where MAPVIEWID=" + mvId;
/*  526 */         rs = AMConnectionPool.executeQueryStmt(bsgIdQuery);
/*  527 */         while (rs.next())
/*      */         {
/*  529 */           bsgId = rs.getString("BSGID");
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
/*  552 */       return bsgId;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  536 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  540 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  544 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  548 */           e.printStackTrace();
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
/*      */   public static String getMapViewIdForBSGId(String bsgId)
/*      */   {
/*  562 */     String mapViewId = MGUtil.getMapViewIdForBSGId(bsgId);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  597 */     return mapViewId;
/*      */   }
/*      */   
/*      */   public static Vector<String> removeAppsAndIFs(String devicesString, boolean isIds)
/*      */   {
/*  602 */     ResultSet rs = null;
/*  603 */     deviceList = new Vector();
/*  604 */     AMLog.debug("MAPVIEWUTIL:About to remove apps and interfaces ");
/*      */     try
/*      */     {
/*  607 */       if ((devicesString != null) && (!devicesString.equals("")))
/*      */       {
/*      */ 
/*  610 */         String resourceIdQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.RESOURCEID in (" + devicesString + ") and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP IN ('SYS','NWD','EMO','SAN') and AM_ManagedResourceType.SUBGROUP not in ('OpManager-Interface')";
/*      */         
/*  612 */         if (!isIds)
/*      */         {
/*  614 */           String deviceListCSV = "";
/*  615 */           String[] deviceArray = devicesString.split(",");
/*  616 */           for (String deviceName : deviceArray)
/*      */           {
/*  618 */             deviceListCSV = deviceListCSV + "'" + deviceName + "',";
/*      */           }
/*  620 */           deviceListCSV = deviceListCSV.substring(0, deviceListCSV.length() - 1);
/*      */           
/*      */ 
/*  623 */           resourceIdQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject,AM_ManagedResourceType where AM_ManagedObject.RESOURCENAME in (" + deviceListCSV + ") and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP IN ('SYS','NWD','EMO','SAN') and AM_ManagedResourceType.SUBGROUP not in ('OpManager-Interface')";
/*      */         }
/*  625 */         rs = AMConnectionPool.executeQueryStmt(resourceIdQuery);
/*  626 */         while (rs.next())
/*      */         {
/*  628 */           deviceList.add(rs.getString("RESOURCEID"));
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
/*  650 */       return deviceList;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  634 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  638 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  642 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  646 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getMapViewIdForResourceId(String resourceId)
/*      */   {
/*  655 */     ResultSet rs = null;
/*  656 */     mapViewId = "";
/*      */     try
/*      */     {
/*  659 */       String resourceNameQuery = "select MAPVIEWID from MAPVIEWDEVICE where DEVICEID=" + Integer.parseInt(resourceId);
/*  660 */       rs = AMConnectionPool.executeQueryStmt(resourceNameQuery);
/*  661 */       while (rs.next())
/*      */       {
/*  663 */         mapViewId = rs.getString("MAPVIEWID");
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
/*  684 */       return mapViewId;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  668 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  672 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  676 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  680 */           e.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void putHAEXTEntries(String bsgId, String bsgType)
/*      */   {
/*      */     try
/*      */     {
/*  767 */       if (bsgId != null)
/*      */       {
/*  769 */         String bsgTypeQuery = " INSERT INTO AM_HOLISTICAPPLICATION_EXT(RESOURCEID, APP_TYPE) VALUES (" + bsgId + ", '" + bsgType + "')";
/*  770 */         AMConnectionPool.executeUpdateStmt(bsgTypeQuery);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  775 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void deleteDevice(String resourceId, int mapViewId)
/*      */   {
/*      */     try
/*      */     {
/*  785 */       deleteDeviceFromBSG(mapViewId + "", resourceId);
/*  786 */       String deleteDevice = "delete from MAPVIEWDEVICE where DEVICEID=" + resourceId + " AND MAPVIEWID=" + mapViewId;
/*  787 */       AMConnectionPool.executeUpdateStmt(deleteDevice);
/*      */     }
/*      */     catch (Exception e) {
/*  790 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void deleteResources(String resourceId, String tableName, String columnName, int mapViewId)
/*      */   {
/*  797 */     ResultSet rs = null;
/*      */     
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  803 */       if (!tableName.equalsIgnoreCase("MAPVIEWLINK"))
/*      */       {
/*  805 */         String bsgIdQuery = "select bsgid from MAPVIEWMAPPING MVM inner join MAPVIEW MV on MV.mapviewid=MVM.mapviewid inner join MAPVIEWSHORTCUT on mapviewname=submap where shortcutid='" + resourceId + "'";
/*  806 */         rs = AMConnectionPool.executeQueryStmt(bsgIdQuery);
/*  807 */         while (rs.next())
/*      */         {
/*  809 */           String bsgid = rs.getString("bsgid");
/*  810 */           deleteDeviceFromBSG(mapViewId + "", bsgid);
/*      */         }
/*      */       }
/*  813 */       String deleteDevice = "delete from " + tableName + " where " + columnName + "='" + resourceId + "'";
/*  814 */       AMConnectionPool.executeUpdateStmt(deleteDevice);
/*  815 */       AMLog.debug("MAPVIEWUTIL:Deleting the resource " + resourceId + " from mapview with Id " + mapViewId); return;
/*      */     }
/*      */     catch (Exception e) {
/*  818 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  822 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  826 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  830 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void addDevice(String mapViewID, String deviceId)
/*      */   {
/*      */     try
/*      */     {
/*  841 */       String uniqueId = String.valueOf(UUID.randomUUID());
/*  842 */       String deviceName = getDisplayName(deviceId);
/*  843 */       int top = 10;
/*  844 */       int left = 10;
/*  845 */       String devObjAsString = createJSONStr(deviceName, deviceId, top, left, true, null);
/*      */       
/*  847 */       addDevice(mapViewID, deviceName, deviceId, devObjAsString);
/*      */     }
/*      */     catch (Exception e) {
/*  850 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void addDevice(String mapViewID, String deviceName, String deviceId, String devObjAsString)
/*      */   {
/*  857 */     MGUtil.addDevice(mapViewID, deviceName, deviceId, devObjAsString);
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
/*      */   public static void addDevice(ArrayList<String> deviceList, String mapViewID)
/*      */   {
/*      */     try
/*      */     {
/*  874 */       for (String deviceName : deviceList)
/*      */       {
/*  876 */         addDevice(mapViewID, deviceName);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  880 */       e.printStackTrace();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void deleteDeviceAndLinks(String deviceId, String mapViewID)
/*      */   {
/*  923 */     String condition = "";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/*  931 */       if (mapViewID != null)
/*      */       {
/*  933 */         condition = condition + " AND MAPVIEWID=" + mapViewID;
/*      */       }
/*      */       
/*      */ 
/*  937 */       String deleteDevice = "delete from MAPVIEWDEVICE where DEVICEID=" + Integer.parseInt(deviceId) + condition;
/*  938 */       AMConnectionPool.executeUpdateStmt(deleteDevice);
/*      */       
/*      */ 
/*  941 */       String deleteLinks = "delete from MAPVIEWLINK where SOURCE=" + Integer.parseInt(deviceId) + " OR DESTINATION=" + Integer.parseInt(deviceId) + condition;
/*  942 */       AMConnectionPool.executeUpdateStmt(deleteLinks);
/*      */     }
/*      */     catch (Exception e) {
/*  945 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static JSONObject getMapViewObjects(String mapViewName)
/*      */   {
/*  956 */     mapViewObject = new JSONObject();
/*  957 */     ResultSet rs = null;
/*      */     try
/*      */     {
/*  960 */       int mapViewID = 0;
/*      */       
/*  962 */       String selectDevQuery = "select * from MAPVIEW where MAPVIEWNAME='" + mapViewName + "'";
/*  963 */       rs = AMConnectionPool.executeQueryStmt(selectDevQuery);
/*      */       
/*  965 */       mapViewObject.put("MAPVIEWNAME", mapViewName);
/*  966 */       if (rs.next()) {
/*  967 */         mapViewID = rs.getInt("MAPVIEWID");
/*  968 */         mapViewObject.put("BACKGROUNDIMAGE", rs.getString("BACKGROUNDIMAGE"));
/*      */       }
/*      */       
/*  971 */       Properties statusProps = populateStatus(mapViewID + "");
/*      */       
/*  973 */       mapViewObject.put("MAPVIEWDEVICE", getMapViewDevices(mapViewID, statusProps));
/*  974 */       mapViewObject.put("MAPVIEWLINK", getMapViewLinksDetails(mapViewID, statusProps));
/*  975 */       mapViewObject.put("MAPVIEWSHORTCUT", getMapViewShortcutsDetails(mapViewID, statusProps));
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  996 */       return mapViewObject;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  979 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*  983 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/*  987 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  991 */           e.printStackTrace();
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
/*      */   private static JSONArray getMapViewDevices(int mapViewID, Properties statusProps)
/*      */   {
/* 1005 */     deviceArray = new JSONArray();
/*      */     
/* 1007 */     ResultSet rs = null;
/* 1008 */     String selectDevQuery = "select * from MAPVIEWDEVICE where MAPVIEWID=" + mapViewID;
/*      */     
/*      */     try
/*      */     {
/* 1012 */       rs = AMConnectionPool.executeQueryStmt(selectDevQuery);
/* 1013 */       while (rs.next()) {
/* 1014 */         deviceArray.put(new JSONObject().put("DEVICEPROPS", getJSONStrWithStatus(rs.getString("DEVICEPROPS"), statusProps, "DEVICE")));
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
/* 1036 */       return deviceArray;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1019 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1023 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1027 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1031 */           e.printStackTrace();
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
/*      */   private static JSONArray getMapViewLinksDetails(int mapViewID, Properties statusProps)
/*      */   {
/* 1045 */     linkArray = new JSONArray();
/*      */     
/* 1047 */     ResultSet rs = null;
/* 1048 */     String selectDevQuery = "select * from MAPVIEWLINK where MAPVIEWID=" + mapViewID;
/*      */     
/*      */     try
/*      */     {
/* 1052 */       rs = AMConnectionPool.executeQueryStmt(selectDevQuery);
/* 1053 */       while (rs.next()) {
/* 1054 */         linkArray.put(new JSONObject().put("LINKPROPS", getJSONStrWithStatus(rs.getString("LINKPROPS"), statusProps, "CONNECTOR")));
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
/* 1076 */       return linkArray;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1059 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1063 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1067 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1071 */           e.printStackTrace();
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
/*      */   private static JSONArray getMapViewShortcutsDetails(int mapViewID, Properties statusProps)
/*      */   {
/* 1085 */     JSONObject mapViewShortcutsObject = new JSONObject();
/* 1086 */     shortcutArray = new JSONArray();
/*      */     
/* 1088 */     ResultSet rs = null;
/* 1089 */     String selectQuery = "select * from MAPVIEWSHORTCUT where MAPVIEWID=" + mapViewID;
/*      */     
/*      */     try
/*      */     {
/* 1093 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 1094 */       while (rs.next()) {
/* 1095 */         shortcutArray.put(new JSONObject().put("SHORTCUTPROPS", getJSONStrWithStatus(rs.getString("SHORTCUTPROPS"), statusProps, "SHORTCUT")));
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
/* 1117 */       return shortcutArray;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1100 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1104 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1108 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1112 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void persistMapView(String mapJsonString, String username, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */   {
/* 1123 */     ResultSet rs = null;
/* 1124 */     ActionMessages messages = new ActionMessages();
/* 1125 */     ActionErrors errors = new ActionErrors();
/*      */     
/*      */     try
/*      */     {
/* 1129 */       MapViewActions createBSG = new MapViewActions();
/*      */       
/* 1131 */       ShowResourceDetails details = new ShowResourceDetails();
/* 1132 */       JSONObject jobj = new JSONObject(mapJsonString);
/* 1133 */       JSONObject mapViewObj = (JSONObject)jobj.get("MAPVIEW");
/* 1134 */       String bgImage = mapViewObj.getString("bgImage");
/* 1135 */       String mapViewName = mapViewObj.getString("mapViewName");
/* 1136 */       AMLog.debug("MAPVIEWUTIL:: BGImage " + bgImage + " saving Mapviewname " + mapViewName);
/*      */       
/* 1138 */       ArrayList<String> servers = new ArrayList();
/* 1139 */       ArrayList<String> networkDevices = new ArrayList();
/* 1140 */       ArrayList<String> storageDevices = new ArrayList();
/* 1141 */       boolean isMapViewExists = MapViewServerUtil.isMapViewExists(mapViewName);
/* 1142 */       if (!isMapViewExists)
/*      */       {
/* 1144 */         int status = 5;
/* 1145 */         int mvId = MGUtil.generateMapviewId();
/* 1146 */         String insertQuery = "insert into MAPVIEW VALUES(" + mvId + ",'" + mapViewName + "','" + bgImage + "')";
/* 1147 */         AMLog.debug("ENTERED IN MAPVIEW " + insertQuery);
/* 1148 */         AMConnectionPool.executeUpdateStmt(insertQuery);
/*      */         
/* 1150 */         int mapViewID = MapViewServerUtil.getMapViewID(mapViewName);
/*      */         
/* 1152 */         JSONArray mapViewDevArray = (JSONArray)jobj.get("MAPVIEWDEVICE");
/* 1153 */         ArrayList<String> devicesAdded = persistMapViewDevices(mapViewDevArray, mapViewID);
/*      */         
/* 1155 */         JSONArray mapViewLinkArray = new JSONArray(jobj.getString("MAPVIEWLINK"));
/* 1156 */         persistMapViewLinks(mapViewLinkArray, mapViewID);
/*      */         
/* 1158 */         request.setAttribute("fromMapview", "true");
/* 1159 */         request.setAttribute("name", mapViewName);
/* 1160 */         request.setAttribute("groupname", mapViewName);
/* 1161 */         request.setAttribute("parentHaid", "-1");
/* 1162 */         request.setAttribute("haid", "-1");
/* 1163 */         String data = request.getParameter("data");
/* 1164 */         if (data != null) {
/* 1165 */           data = data.replaceAll("&quot;", "\"");
/*      */           
/* 1167 */           request.setAttribute("data", data);
/*      */         }
/*      */         
/* 1170 */         createBSG.createMonitorGroupForMap(mapping, request, false, messages, errors);
/* 1171 */         int bsgId = ((Integer)request.getAttribute("bsgId")).intValue();
/*      */         
/* 1173 */         MGUtil.persistMapViewUsers(mapViewID, username, bsgId);
/* 1174 */         JSONArray mapViewSCArray = new JSONArray(jobj.getString("MAPVIEWSHORTCUT"));
/* 1175 */         persistMapViewShortcuts(mapViewSCArray, mapViewID, request);
/*      */         
/* 1177 */         HashMap<String, ArrayList<String>> devicesList = new HashMap();
/* 1178 */         ArrayList<String> selectedServersArrayList = new ArrayList();
/* 1179 */         ArrayList<String> selectedNwArrayList = new ArrayList();
/* 1180 */         ArrayList<String> selectedSANArrayList = new ArrayList();
/* 1181 */         String selectedServers = "";
/* 1182 */         String selectedNetworkDevices = "";
/* 1183 */         String selectedStorageDevices = "";
/* 1184 */         if ((devicesAdded != null) && (devicesAdded.size() > 0))
/*      */         {
/* 1186 */           devicesList = splitServersAndNetworkdevices(devicesAdded);
/* 1187 */           selectedServersArrayList = (ArrayList)devicesList.get("servers");
/*      */           
/*      */ 
/* 1190 */           selectedNwArrayList = (ArrayList)devicesList.get("networkdevices");
/*      */           
/*      */ 
/* 1193 */           selectedSANArrayList = (ArrayList)devicesList.get("storagedevices");
/*      */           
/*      */ 
/*      */ 
/* 1197 */           servers = (ArrayList)devicesList.get("servers");
/* 1198 */           networkDevices = (ArrayList)devicesList.get("networkdevices");
/* 1199 */           storageDevices = (ArrayList)devicesList.get("storagedevices");
/*      */           
/* 1201 */           if (!networkDevices.isEmpty())
/*      */           {
/* 1203 */             String networks = networkDevices.toString();
/* 1204 */             networks = networks.substring(1, networks.length() - 1);
/* 1205 */             networks = networks.replaceAll(", ", ",");
/* 1206 */             request.setAttribute("selectedresource", selectedNwArrayList);
/* 1207 */             details.addResourceFromMapView(mapping, form, request, response);
/*      */             
/*      */ 
/* 1210 */             AMLog.debug("MAPVIEWUTIL:added network devices to the BSG " + networks);
/*      */           }
/*      */           
/* 1213 */           if (!storageDevices.isEmpty())
/*      */           {
/* 1215 */             String storage = storageDevices.toString();
/* 1216 */             storage = storage.substring(1, storage.length() - 1);
/* 1217 */             storage = storage.replaceAll(", ", ",");
/*      */             
/*      */ 
/* 1220 */             request.setAttribute("selectedresource", selectedSANArrayList);
/* 1221 */             details.addResourceFromMapView(mapping, form, request, response);
/* 1222 */             AMLog.debug("MAPVIEWUTIL:added storage devices to the BSG " + storage);
/*      */           }
/*      */           
/* 1225 */           if (!servers.isEmpty())
/*      */           {
/* 1227 */             String serverString = servers.toString();
/* 1228 */             serverString = serverString.substring(1, serverString.length() - 1);
/* 1229 */             serverString = serverString.replaceAll(", ", ",");
/*      */             
/*      */ 
/* 1232 */             request.setAttribute("selectedresource", selectedServersArrayList);
/* 1233 */             details.addResourceFromMapView(mapping, form, request, response);
/* 1234 */             AMLog.debug("MAPVIEWUTIL:Added servers to the BSG " + serverString);
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1240 */         int mapViewID = MapViewServerUtil.getMapViewID(mapViewName);
/* 1241 */         String updateQuery = "update MAPVIEW set BACKGROUNDIMAGE='" + bgImage + "' where MAPVIEWID=" + mapViewID;
/* 1242 */         AMLog.debug("Update MAPVIEW " + updateQuery);
/* 1243 */         AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */         
/* 1245 */         JSONArray mapViewDevArray = (JSONArray)jobj.get("MAPVIEWDEVICE");
/* 1246 */         modifyMapViewDevices(mapViewDevArray, mapViewID, mapping, form, request, response);
/*      */         
/* 1248 */         JSONArray mapViewLinkArray = new JSONArray(jobj.getString("MAPVIEWLINK"));
/* 1249 */         modifyMapViewLinks(mapViewLinkArray, mapViewID);
/*      */         
/* 1251 */         JSONArray mapViewSCArray = new JSONArray(jobj.getString("MAPVIEWSHORTCUT"));
/* 1252 */         modifyMapViewShortcuts(mapViewSCArray, mapViewID, mapping, form, request, response);
/*      */         
/* 1254 */         AMLog.debug("MAPVIEWUtil ===> The map view is already exists " + mapViewName);
/*      */       }
/*      */       return;
/*      */     } catch (Exception e) {
/* 1258 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1262 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1266 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1270 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static ArrayList<String> persistMapViewDevices(JSONArray devObjArray, int mapViewID) throws Exception
/*      */   {
/* 1278 */     PreparedStatement ps = null;
/* 1279 */     devicesAdded = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 1283 */       int devicesCount = devObjArray.length();
/* 1284 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWDEVICE values (?,?,?,?)");
/*      */       
/* 1286 */       for (int i = 0; i < devicesCount; i++)
/*      */       {
/* 1288 */         JSONObject devObject = (JSONObject)devObjArray.get(i);
/* 1289 */         Iterator iter = devObject.keys();
/* 1290 */         if (iter.hasNext()) {
/* 1291 */           String deviceId = (String)iter.next();
/* 1292 */           JSONObject deviceProps = (JSONObject)devObject.get(deviceId);
/* 1293 */           JSONObject it360Object = (JSONObject)deviceProps.get("it360Props");
/* 1294 */           String devName = it360Object.getString("name");
/* 1295 */           devicesAdded.add(deviceId);
/* 1296 */           AMLog.debug("MAPVIEWUTIL:Adding the device " + deviceId + " with name " + devName);
/* 1297 */           ps.setInt(1, Integer.parseInt(deviceId));
/* 1298 */           ps.setString(2, devName);
/* 1299 */           ps.setInt(3, mapViewID);
/* 1300 */           ps.setString(4, deviceProps.toString());
/* 1301 */           ps.addBatch();
/*      */         }
/*      */       }
/* 1304 */       ps.executeBatch();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1324 */       return devicesAdded;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1308 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1314 */         if (ps != null)
/*      */         {
/* 1316 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1321 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static void persistMapViewLinks(JSONArray linkObjArray, int mapViewID)
/*      */     throws Exception
/*      */   {
/* 1329 */     PreparedStatement ps = null;
/*      */     
/*      */     try
/*      */     {
/* 1333 */       int linksCount = linkObjArray.length();
/* 1334 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWLINK values (?,?,?,?,?,?,?)");
/*      */       
/* 1336 */       for (int i = 0; i < linksCount; i++)
/*      */       {
/* 1338 */         JSONObject linkObject = (JSONObject)linkObjArray.get(i);
/* 1339 */         Iterator iter = linkObject.keys();
/* 1340 */         if (iter.hasNext()) {
/* 1341 */           String linkId = (String)iter.next();
/* 1342 */           JSONObject linkProps = (JSONObject)linkObject.get(linkId);
/* 1343 */           JSONObject it360Object = (JSONObject)linkProps.get("it360Props");
/* 1344 */           String sourceID = it360Object.getString("source");
/* 1345 */           String destinationID = it360Object.getString("destination");
/* 1346 */           String srcDispName = getDisplayName(sourceID);
/* 1347 */           String desDispName = getDisplayName(destinationID);
/* 1348 */           String ifName = it360Object.getString("ifName");
/*      */           
/* 1350 */           ps.setString(1, linkId);
/* 1351 */           ps.setString(2, getLinkName(srcDispName, desDispName, ifName));
/* 1352 */           ps.setInt(3, mapViewID);
/* 1353 */           ps.setInt(4, Integer.parseInt(sourceID));
/* 1354 */           ps.setInt(5, Integer.parseInt(destinationID));
/* 1355 */           ps.setString(6, ifName);
/* 1356 */           ps.setString(7, linkProps.toString());
/* 1357 */           ps.addBatch();
/*      */         }
/*      */       }
/* 1360 */       ps.executeBatch();
/* 1361 */       AMLog.debug("MAPVIEWUTIL:Saved the mapview links"); return;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1365 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1371 */         if (ps != null)
/*      */         {
/* 1373 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1378 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static void persistMapViewShortcuts(JSONArray scObjArray, int mapViewID, HttpServletRequest request) throws Exception
/*      */   {
/* 1385 */     PreparedStatement ps = null;
/*      */     
/*      */     try
/*      */     {
/* 1389 */       AMLog.debug("ENTERED IN SC");
/* 1390 */       int scCount = scObjArray.length();
/* 1391 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWSHORTCUT values (?,?,?,?,?)");
/*      */       
/* 1393 */       for (int i = 0; i < scCount; i++)
/*      */       {
/* 1395 */         JSONObject scObject = (JSONObject)scObjArray.get(i);
/* 1396 */         Iterator iter = scObject.keys();
/* 1397 */         if (iter.hasNext()) {
/* 1398 */           String scId = (String)iter.next();
/* 1399 */           JSONObject scProps = (JSONObject)scObject.get(scId);
/* 1400 */           JSONObject it360Object = (JSONObject)scProps.get("it360Props");
/* 1401 */           String scName = it360Object.getString("name");
/* 1402 */           String submap = it360Object.getString("submap");
/* 1403 */           AMLog.debug("ENTERED IN SC:: DEviceID " + scId + " devName " + submap + " scName " + scName);
/* 1404 */           ps.setString(1, scId);
/* 1405 */           ps.setString(2, scName);
/* 1406 */           ps.setInt(3, mapViewID);
/* 1407 */           ps.setString(4, submap);
/* 1408 */           ps.setString(5, scProps.toString());
/* 1409 */           ps.addBatch();
/* 1410 */           String childId = getBSGIdForMapName(submap);
/* 1411 */           String parentId = getBSGIdForMapViewId(Integer.toString(mapViewID));
/* 1412 */           request.setAttribute("haid", childId);
/* 1413 */           request.setAttribute("parentHaid", parentId);
/*      */           
/* 1415 */           changeBSGToSubgroup(parentId, childId, request);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1420 */       ps.executeBatch();
/* 1421 */       AMLog.debug("MAPVIEWUTIL:Saved mapview SCs"); return;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1425 */       exp.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 1431 */         if (ps != null)
/*      */         {
/* 1433 */           ps.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 1438 */         ex.printStackTrace();
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
/*      */   private static void modifyMapViewDevices(JSONArray devObjArray, int mapViewID, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/* 1460 */     ResultSet rs = null;
/* 1461 */     PreparedStatement ps = null;
/* 1462 */     HashMap<String, String> deviceIds = new HashMap();
/* 1463 */     ArrayList<String> devicesToAdd = new ArrayList();
/*      */     
/* 1465 */     String bsgId = getBSGIdForMapViewId(Integer.toString(mapViewID));
/*      */     
/*      */     try
/*      */     {
/* 1469 */       String selectQuery = "select DEVICEID, DEVICENAME from MAPVIEWDEVICE where MAPVIEWID=" + mapViewID;
/* 1470 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */       
/* 1472 */       while (rs.next()) {
/* 1473 */         deviceIds.put(rs.getString("DEVICEID"), rs.getString("DEVICENAME"));
/*      */       }
/*      */       
/* 1476 */       AMLog.debug("The devices available in the DB " + deviceIds.keySet().toString());
/*      */       
/* 1478 */       int devicesCount = devObjArray.length();
/* 1479 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWDEVICE values (?,?,?,?)");
/*      */       
/* 1481 */       for (int i = 0; i < devicesCount; i++)
/*      */       {
/* 1483 */         JSONObject devObject = (JSONObject)devObjArray.get(i);
/* 1484 */         Iterator iter = devObject.keys();
/* 1485 */         if (iter.hasNext())
/*      */         {
/* 1487 */           String deviceId = (String)iter.next();
/* 1488 */           JSONObject deviceProps = (JSONObject)devObject.get(deviceId);
/* 1489 */           JSONObject it360Object = (JSONObject)deviceProps.get("it360Props");
/* 1490 */           String devName = it360Object.getString("name");
/* 1491 */           AMLog.debug("ENTERED IN DEVICE:: DEviceID " + deviceId + " devName " + devName);
/*      */           
/* 1493 */           if (deviceIds.keySet().contains(deviceId))
/*      */           {
/* 1495 */             deviceIds.remove(deviceId);
/* 1496 */             String updateQuery = "update MAPVIEWDEVICE set DEVICEPROPS='" + EnterpriseUtil.modStringForSingleQuote(deviceProps.toString()) + "' where MAPVIEWID=" + mapViewID + " and DEVICEID=" + Integer.parseInt(deviceId);
/*      */             
/* 1498 */             AMLog.debug("The update query is modify devices " + updateQuery);
/* 1499 */             AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */           }
/*      */           else
/*      */           {
/* 1503 */             AMLog.debug("The device to be added " + devName);
/* 1504 */             ps.setInt(1, Integer.parseInt(deviceId));
/* 1505 */             ps.setString(2, devName);
/* 1506 */             ps.setInt(3, mapViewID);
/* 1507 */             ps.setString(4, deviceProps.toString());
/* 1508 */             ps.addBatch();
/* 1509 */             devicesToAdd.add(deviceId);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1515 */       ps.executeBatch();
/*      */       
/* 1517 */       for (String resourceId : deviceIds.keySet())
/*      */       {
/* 1519 */         AMLog.debug("The device to be deleted " + resourceId);
/* 1520 */         deleteDevice(resourceId, mapViewID);
/*      */       }
/*      */       
/* 1523 */       String newVal = "";
/* 1524 */       HashMap<String, ArrayList<String>> devicesList = new HashMap();
/* 1525 */       String selectedServers = "";
/* 1526 */       String selectedNetworkDevices = "";
/* 1527 */       String selectedStorageDevices = "";
/* 1528 */       String unSelectedServers = "";
/* 1529 */       String unSelectedNetworkDevices = "";
/* 1530 */       String unSelectedStorageDevices = "";
/* 1531 */       ArrayList<String> selectedServersArrayList = new ArrayList();
/* 1532 */       ArrayList<String> selectedNwArrayList = new ArrayList();
/* 1533 */       ArrayList<String> selectedSANArrayList = new ArrayList();
/*      */       
/* 1535 */       if (!devicesToAdd.isEmpty())
/*      */       {
/* 1537 */         devicesList = splitServersAndNetworkdevices(devicesToAdd);
/* 1538 */         selectedServersArrayList = (ArrayList)devicesList.get("servers");
/* 1539 */         selectedServers = ((ArrayList)devicesList.get("servers")).toString();
/* 1540 */         selectedServers = selectedServers.substring(1, selectedServers.length() - 1);
/* 1541 */         selectedNwArrayList = (ArrayList)devicesList.get("networkdevices");
/* 1542 */         selectedNetworkDevices = ((ArrayList)devicesList.get("networkdevices")).toString();
/* 1543 */         selectedNetworkDevices = selectedNetworkDevices.substring(1, selectedNetworkDevices.length() - 1);
/* 1544 */         selectedSANArrayList = (ArrayList)devicesList.get("storagedevices");
/* 1545 */         selectedStorageDevices = ((ArrayList)devicesList.get("storagedevices")).toString();
/* 1546 */         selectedStorageDevices = selectedStorageDevices.substring(1, selectedStorageDevices.length() - 1);
/*      */       }
/*      */       
/* 1549 */       if (!deviceIds.isEmpty())
/*      */       {
/* 1551 */         ArrayList<String> tempDeviceList = new ArrayList();
/* 1552 */         for (String key : deviceIds.keySet())
/*      */         {
/* 1554 */           tempDeviceList.add(key);
/*      */         }
/* 1556 */         devicesList = splitServersAndNetworkdevices(tempDeviceList);
/* 1557 */         unSelectedServers = ((ArrayList)devicesList.get("servers")).toString();
/* 1558 */         unSelectedServers = unSelectedServers.substring(1, unSelectedServers.length() - 1);
/* 1559 */         unSelectedNetworkDevices = ((ArrayList)devicesList.get("networkdevices")).toString();
/* 1560 */         unSelectedNetworkDevices = unSelectedNetworkDevices.substring(1, unSelectedNetworkDevices.length() - 1);
/* 1561 */         unSelectedStorageDevices = ((ArrayList)devicesList.get("storagedevices")).toString();
/* 1562 */         unSelectedStorageDevices = unSelectedStorageDevices.substring(1, unSelectedStorageDevices.length() - 1);
/*      */       }
/*      */       
/* 1565 */       if ((!devicesToAdd.isEmpty()) || (!deviceIds.isEmpty()))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1585 */         ShowResourceDetails details = new ShowResourceDetails();
/* 1586 */         request.setAttribute("fromMapview", "true");
/* 1587 */         request.setAttribute("selectedresource", selectedServersArrayList);
/* 1588 */         request.setAttribute("parentHaid", "-1");
/* 1589 */         request.setAttribute("bsgId", Integer.valueOf(Integer.parseInt(bsgId)));
/* 1590 */         String data = request.getParameter("data");
/* 1591 */         data = data.replaceAll("&quot;", "\"");
/*      */         
/* 1593 */         request.setAttribute("data", data);
/* 1594 */         HttpSession session = request.getSession();
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1600 */         details.addResourceFromMapView(mapping, form, request, response);
/* 1601 */         request.setAttribute("selectedresource", selectedNwArrayList);
/* 1602 */         details.addResourceFromMapView(mapping, form, request, response);
/* 1603 */         request.setAttribute("selectedresource", selectedSANArrayList);
/* 1604 */         details.addResourceFromMapView(mapping, form, request, response);
/*      */       }
/*      */       return;
/*      */     }
/*      */     catch (Exception exp)
/*      */     {
/* 1610 */       exp.printStackTrace();
/*      */     }
/*      */     finally {
/* 1613 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1617 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1621 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1626 */         if (ps != null) {
/* 1627 */           ps.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 1630 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static void modifyMapViewLinks(JSONArray linkObjArray, int mapViewID)
/*      */     throws Exception
/*      */   {
/* 1638 */     ResultSet rs = null;
/* 1639 */     PreparedStatement ps = null;
/* 1640 */     ArrayList<String> linkIDs = new ArrayList();
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1645 */       String selectQuery = "select LINKID from MAPVIEWLINK where MAPVIEWID=" + mapViewID;
/* 1646 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */       
/* 1648 */       while (rs.next()) {
/* 1649 */         linkIDs.add(rs.getString("LINKID"));
/*      */       }
/*      */       
/* 1652 */       AMLog.debug("The links available in the DB " + linkIDs);
/*      */       
/* 1654 */       int linksCount = linkObjArray.length();
/* 1655 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWLINK values (?,?,?,?,?,?,?)");
/*      */       
/* 1657 */       for (int i = 0; i < linksCount; i++)
/*      */       {
/* 1659 */         JSONObject linkObject = (JSONObject)linkObjArray.get(i);
/* 1660 */         Iterator iter = linkObject.keys();
/* 1661 */         if (iter.hasNext())
/*      */         {
/* 1663 */           String linkId = (String)iter.next();
/* 1664 */           JSONObject linkProps = (JSONObject)linkObject.get(linkId);
/* 1665 */           JSONObject it360Object = (JSONObject)linkProps.get("it360Props");
/* 1666 */           String sourceID = it360Object.getString("source");
/* 1667 */           String destinationID = it360Object.getString("destination");
/* 1668 */           String source = getDisplayName(sourceID);
/* 1669 */           String destination = getDisplayName(destinationID);
/* 1670 */           String ifName = it360Object.getString("ifName");
/* 1671 */           AMLog.debug("ENTERED IN LINK:: LinkID " + linkId + " source " + source + " destination " + destination);
/*      */           
/* 1673 */           if (linkIDs.contains(linkId))
/*      */           {
/* 1675 */             linkIDs.remove(linkId);
/* 1676 */             String updateQuery = "update MAPVIEWLINK set LINKNAME='" + EnterpriseUtil.modStringForSingleQuote(getLinkName(source, destination, ifName)) + "', SOURCE='" + Integer.parseInt(sourceID) + "', DESTINATION='" + Integer.parseInt(destinationID) + "', IFNAME='" + ifName + "',LINKPROPS='" + EnterpriseUtil.modStringForSingleQuote(linkProps.toString()) + "' where MAPVIEWID=" + mapViewID + " and LINKID='" + linkId + "'";
/* 1677 */             AMLog.debug("The update query is modify link " + updateQuery);
/* 1678 */             AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */           }
/*      */           else
/*      */           {
/* 1682 */             ps.setString(1, linkId);
/* 1683 */             ps.setString(2, getLinkName(source, destination, ifName));
/* 1684 */             ps.setInt(3, mapViewID);
/* 1685 */             ps.setInt(4, Integer.parseInt(sourceID));
/* 1686 */             ps.setInt(5, Integer.parseInt(destinationID));
/* 1687 */             ps.setString(6, ifName);
/* 1688 */             ps.setString(7, linkProps.toString());
/* 1689 */             ps.addBatch();
/*      */           }
/*      */         }
/*      */       }
/* 1693 */       ps.executeBatch();
/*      */       
/*      */ 
/* 1696 */       for (String linkUQID : linkIDs)
/*      */       {
/* 1698 */         AMLog.debug("The link to be deleted " + linkUQID);
/* 1699 */         deleteResources(linkUQID, "MAPVIEWLINK", "LINKID", mapViewID);
/*      */       }
/*      */       return;
/* 1702 */     } catch (Exception exp) { exp.printStackTrace();
/*      */     }
/*      */     finally {
/* 1705 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1709 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1713 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1718 */         if (ps != null)
/*      */         {
/* 1720 */           ps.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 1723 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static void modifyMapViewShortcuts(JSONArray scObjArray, int mapViewID, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*      */   {
/* 1730 */     ResultSet rs = null;
/* 1731 */     PreparedStatement ps = null;
/* 1732 */     ArrayList<String> scIDs = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 1736 */       String selectQuery = "select SHORTCUTID from MAPVIEWSHORTCUT where MAPVIEWID=" + mapViewID;
/* 1737 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */       
/* 1739 */       while (rs.next()) {
/* 1740 */         scIDs.add(rs.getString("SHORTCUTID"));
/*      */       }
/*      */       
/* 1743 */       AMLog.debug("The shortcuts available in the DB " + scIDs);
/*      */       
/* 1745 */       int scCount = scObjArray.length();
/* 1746 */       ps = AMConnectionPool.getConnection().prepareStatement("insert into MAPVIEWSHORTCUT values (?,?,?,?,?)");
/*      */       
/* 1748 */       for (int i = 0; i < scCount; i++)
/*      */       {
/* 1750 */         JSONObject scObject = (JSONObject)scObjArray.get(i);
/* 1751 */         Iterator iter = scObject.keys();
/* 1752 */         if (iter.hasNext()) {
/* 1753 */           String scId = (String)iter.next();
/* 1754 */           JSONObject scProps = (JSONObject)scObject.get(scId);
/* 1755 */           JSONObject it360Object = (JSONObject)scProps.get("it360Props");
/* 1756 */           String scName = it360Object.getString("name");
/* 1757 */           String submap = it360Object.getString("submap");
/* 1758 */           AMLog.debug("ENTERED IN SC:: DEviceID " + scId + " devName " + submap + " scName " + scName);
/*      */           
/* 1760 */           if (scIDs.contains(scId))
/*      */           {
/* 1762 */             scIDs.remove(scId);
/* 1763 */             ArrayList<String> oldSubMap = new ArrayList();
/* 1764 */             oldSubMap.add(scId);
/* 1765 */             String subMapName = (String)getMapNameForUID(oldSubMap).get(0);
/* 1766 */             if (!subMapName.equals(submap))
/*      */             {
/* 1768 */               ArrayList<String> scID = new ArrayList();
/* 1769 */               scID.add(scId);
/* 1770 */               String parentId = getBSGIdForMapViewId(Integer.toString(mapViewID));
/* 1771 */               String newBsgId = getBSGIdForMapName(submap);
/* 1772 */               deleteBSGSubgroupRelation(scID, mapViewID, request);
/* 1773 */               changeBSGToSubgroup(parentId, newBsgId, request);
/*      */             }
/* 1775 */             String updateQuery = "update MAPVIEWSHORTCUT set SHORTCUTNAME='" + scName + "', SUBMAP='" + submap + "', SHORTCUTPROPS='" + EnterpriseUtil.modStringForSingleQuote(scProps.toString()) + "' where MAPVIEWID=" + mapViewID + " and SHORTCUTID='" + scId + "'";
/* 1776 */             AMLog.debug("The update query is modify shortcut " + updateQuery);
/* 1777 */             AMConnectionPool.executeUpdateStmt(updateQuery);
/*      */           }
/*      */           else
/*      */           {
/* 1781 */             ps.setString(1, scId);
/* 1782 */             ps.setString(2, scName);
/* 1783 */             ps.setInt(3, mapViewID);
/* 1784 */             ps.setString(4, submap);
/* 1785 */             ps.setString(5, scProps.toString());
/* 1786 */             ps.addBatch();
/*      */             
/* 1788 */             String childId = getBSGIdForMapName(submap);
/* 1789 */             String parentId = getBSGIdForMapViewId(Integer.toString(mapViewID));
/*      */             
/* 1791 */             request.setAttribute("haid", childId);
/* 1792 */             request.setAttribute("parentHaid", parentId);
/*      */             
/* 1794 */             changeBSGToSubgroup(parentId, childId, request);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1799 */       ps.executeBatch();
/*      */       
/*      */ 
/* 1802 */       if (scIDs.size() > 0)
/*      */       {
/* 1804 */         deleteBSGSubgroupRelation(scIDs, mapViewID, request);
/* 1805 */         Vector<String> childIds = new Vector();
/* 1806 */         ArrayList<String> scNames = getMapNameForUID(scIDs);
/* 1807 */         String bsgId = null;
/* 1808 */         String userId = null;
/* 1809 */         for (Object scname : scNames)
/*      */         {
/* 1811 */           bsgId = getBSGIdForMapName(scname.toString());
/* 1812 */           childIds.add(bsgId);
/* 1813 */           if (EnterpriseUtil.isIt360MSPEdition)
/*      */           {
/* 1815 */             DBUtil.getChildIDs(childIds, bsgId);
/*      */           }
/*      */           else
/*      */           {
/* 1819 */             ManagedApplication.getChildIDs(childIds, bsgId);
/*      */           }
/*      */         }
/* 1822 */         bsgId = getBSGIdForMapViewId(Integer.toString(mapViewID));
/* 1823 */         String childIdString = Constants.convertVectorToCSV(childIds);
/* 1824 */         if ((childIds != null) && (childIds.size() > 0))
/*      */         {
/* 1826 */           String userIdQuery = "select OWNERID from AM_HOLISTICAPPLICATION_OWNERS where HAID=" + bsgId;
/* 1827 */           rs = AMConnectionPool.executeQueryStmt(userIdQuery);
/* 1828 */           while (rs.next())
/*      */           {
/* 1830 */             userId = rs.getString("OWNERID");
/*      */           }
/* 1832 */           String ownerDeleteQuery = "delete from AM_HOLISTICAPPLICATION_OWNERS where HAID in (" + childIdString + ") and OWNERID=" + userId;
/* 1833 */           AMConnectionPool.executeUpdateStmt(ownerDeleteQuery);
/* 1834 */           EnterpriseUtil.addUpdateQueryToFile(ownerDeleteQuery);
/* 1835 */           AMLog.debug("Owners deleted for the BSGs  " + childIdString);
/*      */         }
/*      */       }
/*      */       
/* 1839 */       for (String scUQID : scIDs)
/*      */       {
/* 1841 */         AMLog.debug("The link to be deleted " + scUQID);
/* 1842 */         deleteResources(scUQID, "MAPVIEWSHORTCUT", "SHORTCUTID", mapViewID);
/*      */       }
/*      */       return;
/*      */     }
/*      */     catch (Exception exp) {
/* 1847 */       exp.printStackTrace();
/*      */     }
/*      */     finally {
/* 1850 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 1854 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1858 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       try
/*      */       {
/* 1863 */         if (ps != null)
/*      */         {
/* 1865 */           ps.close();
/*      */         }
/*      */       } catch (Exception ex) {
/* 1868 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static String getIFSnapshotLink(String ifName)
/*      */   {
/* 1875 */     String interfaceLink = "";
/* 1876 */     boolean isExtDevice = false;
/* 1877 */     ResultSet extResultSet = null;
/*      */     
/*      */     try
/*      */     {
/* 1881 */       String query = "select AMAED.MASID,AMAED.DEVICELINK from AM_AssociatedExtDevices AMAED, AM_ManagedObject AM, ExternalDeviceInterfaceDetails EXT where AM.RESOURCENAME=EXT.NAME and AM.RESOURCEID=AMAED.RESID and EXT.NAME='" + ifName + "'";
/* 1882 */       extResultSet = AMConnectionPool.executeQueryStmt(query);
/* 1883 */       int masId = 1;
/* 1884 */       String link = null;
/*      */       
/* 1886 */       if (extResultSet.next()) {
/* 1887 */         masId = extResultSet.getInt("MASID");
/* 1888 */         link = extResultSet.getString("DEVICELINK");
/* 1889 */         isExtDevice = true;
/* 1890 */         interfaceLink = link + "&EXTDEVICEMASID=" + masId;
/*      */       }
/*      */       
/*      */ 
/* 1894 */       if (!isExtDevice)
/*      */       {
/* 1896 */         String amQuery = "select RESOURCEID from AM_ManagedObject where RESOURCENAME='" + ifName + "'";
/* 1897 */         extResultSet = AMConnectionPool.executeQueryStmt(amQuery);
/* 1898 */         int resId = 0;
/*      */         
/* 1900 */         if (extResultSet.next()) {
/* 1901 */           resId = extResultSet.getInt("RESOURCEID");
/*      */         }
/*      */         
/* 1904 */         interfaceLink = "/HostResource.do?NetworkInterface=true&net_resourceid=" + resId;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1911 */       if (extResultSet != null)
/*      */       {
/*      */         try
/*      */         {
/* 1915 */           extResultSet.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1919 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 1923 */       AMLog.debug("The interface link is " + interfaceLink);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1907 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 1911 */       if (extResultSet != null)
/*      */       {
/*      */         try
/*      */         {
/* 1915 */           extResultSet.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1919 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1924 */     return interfaceLink;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList<String> getListOfBGImages()
/*      */   {
/* 1931 */     ArrayList<String> imgArray = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 1935 */       String imageDir = "./images/maps";
/*      */       
/* 1937 */       File folder = new File(imageDir);
/* 1938 */       File[] listOfFiles = folder.listFiles();
/* 1939 */       int filesCount = listOfFiles.length;
/*      */       
/* 1941 */       for (int i = 0; i < filesCount; i++)
/*      */       {
/* 1943 */         if (listOfFiles[i].isFile())
/*      */         {
/* 1945 */           String fileName = listOfFiles[i].getName();
/* 1946 */           AMLog.debug("MAPVIEWUTIL:Mapview BG image names " + fileName);
/* 1947 */           imgArray.add(fileName);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 1951 */       e.printStackTrace();
/*      */     }
/* 1953 */     return imgArray;
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getListOfDeviceImages()
/*      */   {
/* 1958 */     ArrayList<String> devList = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 1962 */       String nmshome = System.getProperty("webnms.rootdir");
/* 1963 */       String dirName = nmshome + File.separator + "images" + File.separator;
/*      */       
/* 1965 */       File imgDir = new File(dirName);
/* 1966 */       File[] imageslist = imgDir.listFiles();
/* 1967 */       if ((imageslist != null) && (imageslist.length > 0))
/*      */       {
/* 1969 */         int fcount = imageslist.length;
/*      */         
/* 1971 */         for (int i = 0; i < fcount; i++)
/*      */         {
/* 1973 */           String name = imageslist[i].getName();
/* 1974 */           if (imageslist[i].isFile())
/*      */           {
/* 1976 */             devList.add(name);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1983 */       e.printStackTrace();
/*      */     }
/* 1985 */     return devList;
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
/*      */   public static ArrayList<String> getResourceIds(String mapViewId)
/*      */   {
/* 2039 */     devicesList = new ArrayList();
/* 2040 */     ArrayList<String> tempList = new ArrayList();
/* 2041 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 2044 */       String resourceIdQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject inner join MAPVIEWDEVICE on MAPVIEWDEVICE.DEVICEID=AM_ManagedObject.RESOURCEID and MAPVIEWDEVICE.MAPVIEWID=" + mapViewId;
/* 2045 */       rs = AMConnectionPool.executeQueryStmt(resourceIdQuery);
/*      */       
/* 2047 */       while (rs.next())
/*      */       {
/* 2049 */         devicesList.add(rs.getString("RESOURCEID"));
/*      */       }
/*      */       
/* 2052 */       String shortcutQuery = "select MAPVIEW.MAPVIEWID from MAPVIEW inner join MAPVIEWSHORTCUT on MAPVIEW.MAPVIEWNAME=SUBMAP where MAPVIEWSHORTCUT.MAPVIEWID=" + mapViewId;
/* 2053 */       rs = AMConnectionPool.executeQueryStmt(shortcutQuery);
/* 2054 */       while (rs.next())
/*      */       {
/* 2056 */         tempList.add(rs.getString("MAPVIEWID"));
/*      */       }
/* 2058 */       if ((tempList != null) && (tempList.size() > 0))
/*      */       {
/* 2060 */         for (Object mvId : tempList)
/*      */         {
/* 2062 */           devicesList.add(getBSGIdForMapViewId((String)mvId));
/*      */         }
/*      */       }
/* 2065 */       String interfaceQuery = "select distinct(RESOURCEID) from AM_ManagedObject inner join MAPVIEWLINK on RESOURCENAME=IFNAME and MAPVIEWID=" + mapViewId;
/* 2066 */       rs = AMConnectionPool.executeQueryStmt(interfaceQuery);
/* 2067 */       while (rs.next())
/*      */       {
/* 2069 */         devicesList.add(rs.getString("RESOURCEID"));
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
/*      */ 
/* 2092 */       return devicesList;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2075 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2079 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 2083 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2087 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void createMVForBsgAndSubgroups(String bsgHaid, String username)
/*      */   {
/* 2097 */     AMConnectionPool pool = AMConnectionPool.getInstance();
/* 2098 */     ResultSet hasChildrenRes = null;
/*      */     try
/*      */     {
/* 2101 */       createMVForBsg(bsgHaid, username);
/* 2102 */       hasChildrenRes = AMConnectionPool.executeQueryStmt("select AM_PARENTCHILDMAPPER.CHILDID  from AM_PARENTCHILDMAPPER  left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID left outer join AM_HOLISTICAPPLICATION on AM_HOLISTICAPPLICATION.HAID=AM_ManagedObject.RESOURCEID  where AM_PARENTCHILDMAPPER.PARENTID=" + bsgHaid + " and AM_ManagedObject.TYPE='HAI' and AM_HOLISTICAPPLICATION.TYPE=1");
/* 2103 */       while (hasChildrenRes.next())
/*      */       {
/* 2105 */         String chResId = hasChildrenRes.getString(1);
/* 2106 */         createMVForBsgAndSubgroups(chResId, username);
/*      */       }
/*      */       return;
/*      */     }
/*      */     catch (Exception ex) {
/* 2111 */       ex.printStackTrace();
/*      */     }
/*      */     finally {
/* 2114 */       AMLog.debug("MAPVIEWUTIL:Created Mapviews for BSG " + bsgHaid + " and its subgroups");
/*      */       try
/*      */       {
/* 2117 */         if (hasChildrenRes != null)
/*      */         {
/* 2119 */           hasChildrenRes.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 2124 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void createMVForBsg(String bsgHaid, String username)
/*      */   {
/* 2131 */     MGUtil.createMVForBsg(bsgHaid, username);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String createJSONStr(String name, String uniqueId, int top, int left, boolean isDevice, String submap)
/*      */   {
/* 2239 */     String devJSONString = MGUtil.createJSONStr(name, uniqueId, top, left, isDevice, submap);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2385 */     return devJSONString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void deleteDeviceFromBSG(String mapViewId, String resourceId)
/*      */   {
/*      */     try
/*      */     {
/* 2394 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 2395 */       String bsgId = getBSGIdForMapViewId(mapViewId);
/* 2396 */       String bsgDeviceDeleteQuery = "delete from AM_PARENTCHILDMAPPER where CHILDID=" + resourceId + " and PARENTID=" + bsgId;
/* 2397 */       AMConnectionPool.executeUpdateStmt(bsgDeviceDeleteQuery);
/* 2398 */       adder.deleteDependantAttributes(Integer.parseInt(bsgId), Integer.parseInt(resourceId));
/* 2399 */       AMLog.debug("MAPVIEWUTIL:Deleted the device " + resourceId + " from BSG " + bsgId);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2403 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Properties populateStatus(String mapViewId)
/*      */   {
/* 2415 */     ResultSet rs = null;
/* 2416 */     severityProperties = new Properties();
/*      */     try
/*      */     {
/* 2419 */       ArrayList<String> resourceIds = getResourceIds(mapViewId);
/* 2420 */       ArrayList<String> otherDevices = resourceIds;
/* 2421 */       ArrayList<String> unManagedNodes = getUnManagedNodes(resourceIds);
/* 2422 */       resourceIds.removeAll(unManagedNodes);
/* 2423 */       if ((resourceIds != null) && (resourceIds.size() > 0))
/*      */       {
/* 2425 */         String resourceIdsString = resourceIds.toString();
/* 2426 */         resourceIdsString = resourceIdsString.substring(1, resourceIdsString.length() - 1);
/*      */         
/* 2428 */         String healthQuery = "select source,severity,mmessage,attributeid from Alert inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=CAST(alert.CATEGORY as INT) inner join AM_ManagedObject on AM_ManagedObject.type=AM_ATTRIBUTES.resourcetype and AM_ManagedObject.resourceid=alert.source where AM_ATTRIBUTES.attribute in ('Health') and source in (" + resourceIdsString + ")";
/* 2429 */         if (DBQueryUtil.getDBType().equals("mysql"))
/*      */         {
/* 2431 */           healthQuery = "select source,severity,mmessage,attributeid from Alert inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=CAST(alert.CATEGORY as UNSIGNED INTEGER) inner join AM_ManagedObject on AM_ManagedObject.type=AM_ATTRIBUTES.resourcetype and AM_ManagedObject.resourceid=alert.source where AM_ATTRIBUTES.attribute in ('Health') and source in (" + resourceIdsString + ")";
/*      */         }
/* 2433 */         AMLog.debug("MAPVIEWUTIL:Query to get the health status " + healthQuery);
/* 2434 */         rs = AMConnectionPool.executeQueryStmt(healthQuery);
/* 2435 */         while (rs.next())
/*      */         {
/* 2437 */           String entity = rs.getString("source") + "_health";
/* 2438 */           severityProperties.put(entity, rs.getString("severity"));
/*      */           
/* 2440 */           if (otherDevices.contains(rs.getString("source")))
/*      */           {
/* 2442 */             otherDevices.remove(rs.getString("source"));
/*      */           }
/* 2444 */           entity = entity + "_message";
/* 2445 */           String message = rs.getString("mmessage");
/* 2446 */           message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
/*      */           
/* 2448 */           message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
/*      */           
/* 2450 */           message = EnterpriseUtil.decodeString(message);
/* 2451 */           message = message.replace("\"", "\\\"");
/* 2452 */           severityProperties.put(entity, message);
/*      */         }
/* 2454 */         if ((otherDevices != null) && (otherDevices.size() > 0))
/*      */         {
/* 2456 */           resourceIdsString = otherDevices.toString();
/* 2457 */           resourceIdsString = resourceIdsString.substring(1, resourceIdsString.length() - 1);
/* 2458 */           String clearHealthQuery = "select SOURCE,SEVERITY,TEXT from Event inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=CAST(event.CATEGORY as INT) and AM_ATTRIBUTES.attribute in ('Health') and SEVERITY=5 and source in (" + resourceIdsString + ")";
/* 2459 */           if (DBQueryUtil.getDBType().equals("mysql"))
/*      */           {
/* 2461 */             clearHealthQuery = "select SOURCE,SEVERITY,TEXT from Event inner join AM_ATTRIBUTES on AM_ATTRIBUTES.ATTRIBUTEID=CAST(event.CATEGORY as UNSIGNED INTEGER) and AM_ATTRIBUTES.attribute in ('Health') and SEVERITY=5 and source in (" + resourceIdsString + ")";
/*      */           }
/* 2463 */           rs = AMConnectionPool.executeQueryStmt(clearHealthQuery);
/* 2464 */           while (rs.next())
/*      */           {
/* 2466 */             String entity = rs.getString("SOURCE") + "_health";
/* 2467 */             severityProperties.put(entity, rs.getString("SEVERITY"));
/* 2468 */             if (otherDevices.contains(rs.getString("SOURCE")))
/*      */             {
/* 2470 */               otherDevices.remove(rs.getString("SOURCE"));
/*      */             }
/*      */             
/* 2473 */             entity = entity + "_message";
/* 2474 */             severityProperties.put(entity, rs.getString("TEXT"));
/*      */           }
/*      */         }
/* 2477 */         if (((unManagedNodes != null ? 1 : 0) & (unManagedNodes.size() > 0 ? 1 : 0)) != 0)
/*      */         {
/* 2479 */           for (Object nodeId : unManagedNodes)
/*      */           {
/* 2481 */             String entity = nodeId.toString() + "_health";
/* 2482 */             severityProperties.put(entity, "unmanaged");
/* 2483 */             entity = entity + "_message";
/* 2484 */             severityProperties.put(entity, "Resource is UnManaged");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 2489 */         if (!otherDevices.isEmpty())
/*      */         {
/* 2491 */           for (Object resId : resourceIds)
/*      */           {
/* 2493 */             String entity = resId.toString() + "_health";
/* 2494 */             severityProperties.put(entity, "nohealth");
/* 2495 */             entity = entity + "_message";
/* 2496 */             severityProperties.put(entity, "Health is not configured");
/*      */           }
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
/* 2520 */       return severityProperties;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2502 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2506 */       AMLog.debug("MAPVIEWUTIL:Got the status for the map " + mapViewId);
/* 2507 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 2511 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2515 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList<String> getUnManagedNodes(ArrayList<String> resourceIds)
/*      */   {
/* 2525 */     ResultSet rs = null;
/* 2526 */     unmanagedResourceIds = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 2530 */       if ((resourceIds != null) && (resourceIds.size() > 0))
/*      */       {
/* 2532 */         String resourceIdsString = resourceIds.toString();
/* 2533 */         resourceIdsString = resourceIdsString.substring(1, resourceIdsString.length() - 1);
/* 2534 */         String unManagedNodesQuery = "select resid from AM_UNMANAGEDNODES where resid in (" + resourceIdsString + ")";
/* 2535 */         rs = AMConnectionPool.executeQueryStmt(unManagedNodesQuery);
/* 2536 */         while (rs.next())
/*      */         {
/* 2538 */           unmanagedResourceIds.add(rs.getString("resid"));
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
/* 2560 */       return unmanagedResourceIds;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2543 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2547 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 2551 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2555 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getJSONStrWithStatus(String devJSONString, Properties statusProps, String category)
/*      */   {
/* 2565 */     String resId = null;
/*      */     
/*      */     try
/*      */     {
/* 2569 */       JSONObject jsonObj = new JSONObject(devJSONString);
/* 2570 */       JSONObject it360PropsObj = (JSONObject)jsonObj.get("it360Props");
/*      */       
/* 2572 */       if (category.equalsIgnoreCase("DEVICE")) {
/* 2573 */         resId = (String)it360PropsObj.get("resId");
/* 2574 */         it360PropsObj.put("name", getDisplayName(resId));
/*      */       }
/* 2576 */       else if (category.equalsIgnoreCase("CONNECTOR"))
/*      */       {
/* 2578 */         String name = (String)it360PropsObj.get("ifName");
/* 2579 */         resId = ProdIntegThread.getResId(name);
/*      */ 
/*      */       }
/* 2582 */       else if (category.equalsIgnoreCase("SHORTCUT"))
/*      */       {
/* 2584 */         String subMapName = (String)it360PropsObj.get("submap");
/* 2585 */         resId = getBSGIdForMapName(subMapName);
/*      */       }
/*      */       
/* 2588 */       String healthStatus = statusProps.getProperty(resId + "_health");
/* 2589 */       if (healthStatus != null)
/*      */       {
/* 2591 */         it360PropsObj.put("status", healthStatus);
/* 2592 */         it360PropsObj.put("message", statusProps.getProperty(resId + "_health_message"));
/*      */       }
/*      */       
/* 2595 */       return jsonObj.toString();
/*      */     } catch (JSONException e) {
/* 2597 */       e.printStackTrace();
/*      */     }
/* 2599 */     return devJSONString;
/*      */   }
/*      */   
/*      */   public static void editMapViewName(int mapViewID, String mapviewName) throws Exception
/*      */   {
/* 2604 */     ResultSet rs = null;
/* 2605 */     String oldMapName = null;
/* 2606 */     String shortcutProps = null;
/*      */     try
/*      */     {
/* 2609 */       String selectQuery = "select MAPVIEWNAME from MAPVIEW where MAPVIEWID=" + mapViewID;
/* 2610 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */       
/* 2612 */       while (rs.next())
/*      */       {
/* 2614 */         oldMapName = rs.getString("MAPVIEWNAME");
/*      */       }
/* 2616 */       String scjsonObjQuery = "select SHORTCUTPROPS from MAPVIEWSHORTCUT where SUBMAP='" + oldMapName + "'";
/* 2617 */       rs = AMConnectionPool.executeQueryStmt(scjsonObjQuery);
/* 2618 */       while (rs.next())
/*      */       {
/* 2620 */         shortcutProps = rs.getString("SHORTCUTPROPS");
/* 2621 */         JSONObject jsonObj = new JSONObject(shortcutProps);
/* 2622 */         JSONObject it360PropsObj = (JSONObject)jsonObj.get("it360Props");
/* 2623 */         it360PropsObj.put("submap", mapviewName);
/* 2624 */         jsonObj.put("it360Props", it360PropsObj);
/* 2625 */         String jsonstring = String.valueOf(jsonObj);
/* 2626 */         String mapViewSCUpdateQuery = "update MAPVIEWSHORTCUT set SUBMAP='" + mapviewName + "', SHORTCUTPROPS='" + EnterpriseUtil.modStringForSingleQuote(jsonstring) + "' where SUBMAP='" + oldMapName + "'";
/* 2627 */         AMConnectionPool.executeUpdateStmt(mapViewSCUpdateQuery);
/*      */       }
/* 2629 */       String mapViewUpdateQuery = "update MAPVIEW set MAPVIEWNAME='" + mapviewName + "' where MAPVIEWID=" + mapViewID;
/* 2630 */       AMConnectionPool.executeUpdateStmt(mapViewUpdateQuery);
/* 2631 */       AMLog.debug("MAPVIEWUTIL: Updated the mapview name '" + oldMapName + "' to '" + mapviewName + "'"); return;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2635 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2639 */       if (rs != null) {
/*      */         try {
/* 2641 */           rs.close();
/*      */         } catch (Exception e) {
/* 2643 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getCustomersListForUser(String username)
/*      */   {
/* 2651 */     Properties custProps = EnterpriseUtil.getAllCustomerProps(username);
/* 2652 */     ArrayList<String> customersList = new ArrayList();
/*      */     try
/*      */     {
/* 2655 */       if ((custProps != null) && (custProps.size() > 0))
/*      */       {
/* 2657 */         Enumeration custEnum = custProps.keys();
/* 2658 */         while (custEnum.hasMoreElements())
/*      */         {
/* 2660 */           String custId = custEnum.nextElement().toString();
/* 2661 */           customersList.add(custProps.getProperty(custId));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2667 */       e.printStackTrace();
/*      */     }
/* 2669 */     return customersList;
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getSitesListForCustomer(String customerName, String username)
/*      */   {
/* 2674 */     ArrayList<String> sitesList = new ArrayList();
/* 2675 */     Properties custProps = null;
/* 2676 */     String custId = null;
/*      */     try
/*      */     {
/* 2679 */       custProps = EnterpriseUtil.getAllCustomerProps(username);
/* 2680 */       if ((custProps != null) && (custProps.size() > 0))
/*      */       {
/* 2682 */         Enumeration custEnum = custProps.keys();
/* 2683 */         while (custEnum.hasMoreElements())
/*      */         {
/* 2685 */           String tempCustId = custEnum.nextElement().toString();
/* 2686 */           String tmepCustName = custProps.getProperty(tempCustId);
/* 2687 */           if (tmepCustName.equalsIgnoreCase(customerName))
/*      */           {
/* 2689 */             custId = tempCustId;
/* 2690 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2696 */       Properties siteProps = EnterpriseUtil.getAllSiteProps(custId, username);
/*      */       
/* 2698 */       if ((siteProps != null) && (siteProps.size() > 0))
/*      */       {
/* 2700 */         Enumeration siteEnum = siteProps.keys();
/* 2701 */         while (siteEnum.hasMoreElements())
/*      */         {
/* 2703 */           String siteId = siteEnum.nextElement().toString();
/* 2704 */           sitesList.add(siteProps.getProperty(siteId));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2710 */       e.printStackTrace();
/*      */     }
/* 2712 */     return sitesList;
/*      */   }
/*      */   
/*      */   public static int getCustomerId(String custName)
/*      */   {
/* 2717 */     ResultSet rs = null;
/* 2718 */     custId = -1;
/*      */     try
/*      */     {
/* 2721 */       String query = "SELECT CUSTOMERID FROM CUSTOMERINFO WHERE CUSTOMERNAME='" + custName + "'";
/* 2722 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 2724 */       while (rs.next())
/*      */       {
/* 2726 */         custId = Integer.parseInt(rs.getString("CUSTOMERID"));
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
/* 2748 */       return custId;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2731 */       ex.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2738 */         if (rs != null)
/*      */         {
/* 2740 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 2745 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static int getSiteId(String custName, String siteName)
/*      */   {
/* 2753 */     ResultSet rs = null;
/* 2754 */     int custId = getCustomerId(custName);
/* 2755 */     siteId = -1;
/*      */     try
/*      */     {
/* 2758 */       String query = "SELECT SITEID FROM SITEINFO WHERE CUSTOMERID=" + custId + " and SITENAME='" + siteName + "'";
/* 2759 */       rs = AMConnectionPool.executeQueryStmt(query);
/*      */       
/* 2761 */       while (rs.next())
/*      */       {
/* 2763 */         siteId = Integer.parseInt(rs.getString("SITEID"));
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
/* 2785 */       return siteId;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2768 */       ex.printStackTrace();
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 2775 */         if (rs != null)
/*      */         {
/* 2777 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 2782 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static void deleteBSGSubgroupRelation(ArrayList<String> scUids, int mapviewId, HttpServletRequest request)
/*      */   {
/* 2790 */     ResultSet rs = null;
/* 2791 */     ArrayList<String> submapNames = new ArrayList();
/*      */     try
/*      */     {
/* 2794 */       String parentBsgId = getBSGIdForMapViewId(Integer.toString(mapviewId));
/* 2795 */       if ((scUids != null) && (scUids.size() > 0))
/*      */       {
/* 2797 */         submapNames = getMapNameForUID(scUids);
/* 2798 */         for (Object submapname : submapNames)
/*      */         {
/* 2800 */           String submap = submapname.toString();
/* 2801 */           String childBsgId = getBSGIdForMapName(submap);
/* 2802 */           String parentchildMapperQuery = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + parentBsgId + " and CHILDID=" + childBsgId;
/* 2803 */           String rcaMapperQuery = "delete from AM_RCAMAPPER where PARENTRESOURCEID=" + parentBsgId + " and CHILDRESOURCEID=" + childBsgId;
/* 2804 */           if (!EnterpriseUtil.isIt360MSPEdition)
/*      */           {
/* 2806 */             String bsgTypeQuery = "update AM_HOLISTICAPPLICATION set TYPE=0 where HAID=" + childBsgId;
/* 2807 */             AMConnectionPool.executeUpdateStmt(bsgTypeQuery);
/* 2808 */             EnterpriseUtil.addUpdateQueryToFile(bsgTypeQuery);
/*      */           }
/* 2810 */           if (EnterpriseUtil.isIt360MSPEdition)
/*      */           {
/* 2812 */             String bsgAppTypeQuery = "update AM_HOLISTICAPPLICATION_EXT set APP_TYPE='BSG0' where RESOURCEID=" + childBsgId;
/* 2813 */             String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 2814 */             parentchildMapperQuery = "update AM_PARENTCHILDMAPPER set PARENTID=" + custId + " where CHILDID=" + childBsgId;
/* 2815 */             AMConnectionPool.executeUpdateStmt(bsgAppTypeQuery);
/* 2816 */             EnterpriseUtil.addUpdateQueryToFile(bsgAppTypeQuery);
/*      */           }
/* 2818 */           AMConnectionPool.executeUpdateStmt(parentchildMapperQuery);
/* 2819 */           AMConnectionPool.executeUpdateStmt(rcaMapperQuery);
/* 2820 */           EnterpriseUtil.addUpdateQueryToFile(parentchildMapperQuery);
/* 2821 */           EnterpriseUtil.addUpdateQueryToFile(rcaMapperQuery);
/* 2822 */           AMLog.debug("MAPVIEWUTIL:Deleted the BSG-subgroup relation between '" + parentBsgId + "' and its child '" + childBsgId + "'");
/*      */           
/* 2824 */           MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/* 2825 */           Hashtable toNotifier = null;
/* 2826 */           if (notifyConsole.shouldNotify())
/*      */           {
/*      */             try
/*      */             {
/* 2830 */               String custName = null;
/* 2831 */               Properties custProps = EnterpriseUtil.getCustProp(request);
/* 2832 */               if (custProps != null)
/*      */               {
/* 2834 */                 Enumeration custEnum = custProps.keys();
/* 2835 */                 while (custEnum.hasMoreElements())
/*      */                 {
/* 2837 */                   custName = custEnum.nextElement().toString();
/*      */                 }
/*      */               }
/*      */               
/* 2841 */               toNotifier = new Hashtable();
/* 2842 */               toNotifier.put("MGID", childBsgId);
/* 2843 */               toNotifier.put("action", "DELETE_SC");
/* 2844 */               toNotifier.put("DELETE_SC", "true");
/* 2845 */               String parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", childBsgId);
/* 2846 */               if ((parentGrpNames != null) && (!parentGrpNames.equals("")))
/*      */               {
/* 2848 */                 parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/*      */               }
/* 2850 */               toNotifier.put("MGNAME", submap + "-" + parentGrpNames);
/* 2851 */               String parentMG = DBUtil.getDisplaynameforResourceID(parentBsgId);
/*      */               
/* 2853 */               if (!parentMG.equals(custName))
/*      */               {
/* 2855 */                 parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", parentBsgId);
/* 2856 */                 parentMG = parentMG + "-" + parentGrpNames;
/* 2857 */                 parentMG = parentMG.substring(0, parentMG.length() - 1);
/*      */               }
/* 2859 */               toNotifier.put("PARENTMG", parentMG);
/* 2860 */               notifyConsole.setProperties(toNotifier);
/* 2861 */               AMLog.debug("Going to delete shortcut - " + submap + " from " + parentMG);
/* 2862 */               ExtProdUtil.doBVNotificationToOPM(toNotifier, request, childBsgId);
/* 2863 */               AMLog.debug("MAPVIEWUTIL:Notified OPM to delete the corresponding shortcut ");
/*      */             }
/*      */             catch (Exception e)
/*      */             {
/* 2867 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2873 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentBsgId), 17, System.currentTimeMillis(), true, true, 1);
/* 2874 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentBsgId), 18, System.currentTimeMillis(), true, false, 2); return;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2878 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2882 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 2886 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2890 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static ArrayList<String> getMapNameForUID(ArrayList<String> scUids)
/*      */   {
/* 2898 */     ResultSet rs = null;
/* 2899 */     shortcutNames = new ArrayList();
/*      */     try
/*      */     {
/* 2902 */       if ((scUids != null) && (scUids.size() > 0))
/*      */       {
/* 2904 */         StringBuilder scUidString = new StringBuilder();
/* 2905 */         for (Object scuid : scUids)
/*      */         {
/* 2907 */           scUidString.append("'");
/* 2908 */           scUidString.append(scuid.toString());
/* 2909 */           scUidString.append("',");
/*      */         }
/* 2911 */         String scUidStr = scUidString.toString();
/* 2912 */         scUidStr = scUidStr.substring(0, scUidStr.length() - 1);
/* 2913 */         String childIdsQuery = "select SUBMAP from MAPVIEWSHORTCUT where SHORTCUTID in (" + scUidStr + ")";
/* 2914 */         rs = AMConnectionPool.executeQueryStmt(childIdsQuery);
/* 2915 */         while (rs.next())
/*      */         {
/* 2917 */           shortcutNames.add(rs.getString("SUBMAP"));
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
/* 2939 */       return shortcutNames;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2923 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 2927 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 2931 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 2935 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList<String> getSubMaps(ArrayList<String> subMapList, ArrayList<String> finalList, ArrayList<String> duplicateList)
/*      */   {
/*      */     try
/*      */     {
/* 2947 */       if (subMapList.size() == 0) {
/* 2948 */         return finalList;
/*      */       }
/*      */       
/* 2951 */       for (String mapViewName : subMapList) {
/* 2952 */         int mapViewId = MapViewServerUtil.getMapViewID(mapViewName);
/* 2953 */         ResultSet rs = null;
/*      */         try {
/* 2955 */           String subMapQuery = "select SUBMAP from MAPVIEWSHORTCUT where MAPVIEWID=" + mapViewId;
/* 2956 */           rs = AMConnectionPool.executeQueryStmt(subMapQuery);
/* 2957 */           while (rs.next()) {
/* 2958 */             String submap = rs.getString("SUBMAP");
/* 2959 */             if (!duplicateList.contains(submap)) {
/* 2960 */               duplicateList.add(submap);
/*      */             }
/*      */           }
/* 2963 */           duplicateList.remove(mapViewName);
/* 2964 */           if (!finalList.contains(mapViewName)) {
/* 2965 */             finalList.add(mapViewName);
/*      */           }
/*      */         } catch (Exception ex) {
/* 2968 */           ex.printStackTrace();
/*      */         }
/*      */         finally {
/* 2971 */           if (rs != null) {
/* 2972 */             rs.close();
/*      */           }
/*      */         }
/*      */       }
/* 2976 */       ArrayList<String> tempList = (ArrayList)duplicateList.clone();
/* 2977 */       getSubMaps(tempList, finalList, duplicateList);
/*      */     } catch (Exception e) {
/* 2979 */       e.printStackTrace();
/*      */     }
/* 2981 */     return finalList;
/*      */   }
/*      */   
/*      */   public static boolean verifyMapName(String mapname)
/*      */   {
/* 2986 */     ResultSet rs = null;
/* 2987 */     mapPresent = false;
/*      */     
/*      */     try
/*      */     {
/* 2991 */       if ((mapname != null) && (!mapname.equals("")))
/*      */       {
/* 2993 */         String bsgCheckQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject where AM_ManagedObject.RESOURCENAME='" + mapname + "' and AM_ManagedObject.TYPE='HAI' ";
/* 2994 */         rs = AMConnectionPool.executeQueryStmt(bsgCheckQuery);
/* 2995 */         while (rs.next())
/*      */         {
/* 2997 */           mapPresent = true;
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
/* 3019 */       return mapPresent;
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3003 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 3009 */         if (rs != null)
/*      */         {
/* 3011 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3016 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static ArrayList<String> getDeviceCategories()
/*      */   {
/* 3024 */     ResultSet rs = null;
/* 3025 */     ArrayList<String> categories = new ArrayList();
/*      */     
/*      */     try
/*      */     {
/* 3029 */       String deviceCategoryQuery = "select distinct(category) as CATEGORY from ExternalDeviceDetails where category != 'OpManager-Interface'  and category !='OpManager-Unknown'";
/* 3030 */       rs = AMConnectionPool.executeQueryStmt(deviceCategoryQuery);
/* 3031 */       while (rs.next())
/*      */       {
/* 3033 */         String tempCategory = rs.getString("CATEGORY");
/*      */         
/*      */ 
/* 3036 */         categories.add(tempCategory);
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
/*      */       try
/*      */       {
/* 3051 */         if (rs != null)
/*      */         {
/* 3053 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3058 */         ex.printStackTrace();
/*      */       }
/*      */       
/* 3061 */       AMLog.debug("MAPVIEWUTIL:The device categories are " + categories.toString());
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3045 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 3051 */         if (rs != null)
/*      */         {
/* 3053 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3058 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/* 3062 */     return categories;
/*      */   }
/*      */   
/*      */   public static boolean isSubgroup(String bsgid)
/*      */   {
/* 3067 */     boolean isSubgroup = MGUtil.isSubgroup(bsgid);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3112 */     return isSubgroup;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void changeBSGToSubgroup(String parentId, String childId, HttpServletRequest request)
/*      */   {
/*      */     try
/*      */     {
/* 3121 */       addSubGroup(childId, parentId);
/* 3122 */       if (!EnterpriseUtil.isIt360MSPEdition)
/*      */       {
/* 3124 */         String bsgTypeQuery = "update AM_HOLISTICAPPLICATION set TYPE=1 where HAID=" + childId;
/* 3125 */         AMConnectionPool.executeUpdateStmt(bsgTypeQuery);
/* 3126 */         EnterpriseUtil.addUpdateQueryToFile(bsgTypeQuery);
/*      */       }
/* 3128 */       if (EnterpriseUtil.isIt360MSPEdition)
/*      */       {
/* 3130 */         String bsgAppTypeQuery = "update AM_HOLISTICAPPLICATION_EXT set APP_TYPE='BSG1' where RESOURCEID=" + childId;
/* 3131 */         AMConnectionPool.executeUpdateStmt(bsgAppTypeQuery);
/* 3132 */         EnterpriseUtil.addUpdateQueryToFile(bsgAppTypeQuery);
/* 3133 */         String custId = CustomerManagementAPI.getCustomerIdFromRequest(request);
/* 3134 */         String parentchildMapperQuery = "delete from AM_PARENTCHILDMAPPER where PARENTID=" + custId + " and CHILDID=" + childId;
/* 3135 */         AMConnectionPool.executeUpdateStmt(parentchildMapperQuery);
/* 3136 */         EnterpriseUtil.addUpdateQueryToFile(parentchildMapperQuery);
/* 3137 */         AMLog.debug("MAPVIEWUTIL:Added BSG " + childId + " as a subgroup for " + parentId);
/*      */       }
/*      */       
/* 3140 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentId), 17, System.currentTimeMillis(), true, true, 1);
/* 3141 */       new AMRCAnalyser().applyRCA(Integer.parseInt(parentId), 18, System.currentTimeMillis(), true, false, 2);
/*      */       
/* 3143 */       Hashtable toNotifier = null;
/* 3144 */       MGActionNotifier notifyConsole = MGActionNotifier.getInstance();
/* 3145 */       String mgName = DBUtil.getDisplaynameforResourceID(childId);
/* 3146 */       if (notifyConsole.shouldNotify())
/*      */       {
/* 3148 */         toNotifier = new Hashtable();
/* 3149 */         toNotifier.put("MGID", childId);
/* 3150 */         toNotifier.put("MGNAME", mgName);
/*      */         try
/*      */         {
/* 3153 */           if (childId != null)
/*      */           {
/* 3155 */             toNotifier.put("IS_SUBGROUP", Boolean.valueOf(true));
/* 3156 */             toNotifier.put("ADD_SC_ALONE", "true");
/* 3157 */             toNotifier.put("DISPLAYNAME", mgName);
/* 3158 */             String parentGrpNames = BSIntegUtil.getTOPLevelIntegMGsNames("", childId);
/* 3159 */             parentGrpNames = parentGrpNames.substring(0, parentGrpNames.length() - 1);
/* 3160 */             toNotifier.put("MGNAME", mgName + "-" + parentGrpNames);
/* 3161 */             ExtProdUtil.doBVNotificationToOPM(toNotifier, request, childId);
/* 3162 */             AMLog.debug("MAPVIEWUTIL:sent notification to OPM to add shortcut ");
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 3167 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3173 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static HashMap<String, ArrayList<String>> splitServersAndNetworkdevices(ArrayList<String> deviceList)
/*      */   {
/* 3179 */     toReturn = new HashMap();
/*      */     
/* 3181 */     if (deviceList.size() > 0)
/*      */     {
/* 3183 */       ArrayList<String> networkDevices = new ArrayList();
/* 3184 */       ArrayList<String> servers = new ArrayList();
/* 3185 */       ArrayList<String> storageDevices = new ArrayList();
/* 3186 */       String resIds = deviceList.toString();
/* 3187 */       resIds = resIds.substring(1, resIds.length() - 1);
/*      */       
/* 3189 */       ResultSet rs = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 3198 */         String resourceIdQuery = "select resourceid,resourcename,type,resourcegroup from AM_ManagedObject,AM_ManagedResourceType where resourceid in (" + resIds + ") and AM_ManagedObject.type = AM_ManagedResourceType.resourcetype";
/* 3199 */         rs = AMConnectionPool.executeQueryStmt(resourceIdQuery);
/*      */         
/* 3201 */         while (rs.next())
/*      */         {
/* 3203 */           if (rs.getString("resourcegroup").equalsIgnoreCase("NWD"))
/*      */           {
/* 3205 */             if (rs.getString("type").startsWith("OpManager-"))
/*      */             {
/* 3207 */               networkDevices.add(rs.getString("resourceid"));
/*      */             }
/*      */           }
/* 3210 */           else if (rs.getString("resourcegroup").equalsIgnoreCase("SAN"))
/*      */           {
/* 3212 */             if (rs.getString("type").startsWith("OpStor-"))
/*      */             {
/* 3214 */               storageDevices.add(rs.getString("resourceid"));
/*      */             }
/*      */             
/*      */ 
/*      */           }
/*      */           else {
/* 3220 */             servers.add(rs.getString("resourceid"));
/*      */           }
/*      */         }
/* 3223 */         toReturn.put("servers", servers);
/* 3224 */         toReturn.put("networkdevices", networkDevices);
/* 3225 */         toReturn.put("storagedevices", storageDevices);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3246 */         return toReturn;
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3229 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/* 3235 */           if (rs != null)
/*      */           {
/* 3237 */             rs.close();
/*      */           }
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/* 3242 */           ex.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static HashMap<String, String> getAllDevices1(String category, String resourceIds)
/*      */   {
/* 3251 */     return getAllDevices1(category, resourceIds, null);
/*      */   }
/*      */   
/*      */ 
/*      */   public static HashMap<String, String> getAllDevices1(String category, String resourceIds, HttpServletRequest request)
/*      */   {
/* 3257 */     deviceArray = new HashMap();
/* 3258 */     String resourceidCondition = " and RESOURCEID in (" + resourceIds + ")";
/*      */     
/* 3260 */     if (category == null)
/*      */     {
/* 3262 */       return deviceArray;
/*      */     }
/* 3264 */     ResultSet rs = null;
/* 3265 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     
/*      */     try
/*      */     {
/* 3269 */       if (category.equalsIgnoreCase("ALL"))
/*      */       {
/* 3271 */         ArrayList<String> deviceCategories = new ArrayList();
/* 3272 */         StringBuilder categories = new StringBuilder();
/* 3273 */         deviceCategories = getDeviceCategories();
/* 3274 */         for (Object tempCategory : deviceCategories)
/*      */         {
/* 3276 */           if (!tempCategory.toString().equalsIgnoreCase("server"))
/*      */           {
/* 3278 */             categories.append("'").append(tempCategory.toString()).append("',");
/*      */           }
/*      */         }
/* 3281 */         String devCategory = categories.toString();
/* 3282 */         if (!"".equals(devCategory))
/*      */         {
/* 3284 */           devCategory = devCategory.substring(0, devCategory.length() - 1);
/* 3285 */           String getListOfDevice = null;
/* 3286 */           if ((request != null) && (Constants.isPrivilegedUser(request))) {
/* 3287 */             if (Constants.isUserResourceEnabled()) {
/* 3288 */               String loginuserid = Constants.getLoginUserid(request);
/* 3289 */               getListOfDevice = "select ammo.RESOURCEID, extdev.DISPLAYNAME from AM_USERRESOURCESTABLE,AM_ManagedObject ammo, ExternalDeviceDetails extdev  where AM_USERRESOURCESTABLE.RESOURCEID=ammo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginuserid + " and ammo.RESOURCENAME=extdev.NAME and extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY in (" + devCategory + ")";
/*      */             } else {
/* 3291 */               Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 3292 */               getListOfDevice = "select ammo.RESOURCEID, extdev.DISPLAYNAME from AM_ManagedObject ammo inner join ExternalDeviceDetails extdev on ammo.RESOURCENAME=extdev.NAME where extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY in (" + devCategory + ") and " + Constants.getCondition("ammo.RESOURCEID", resids);
/*      */             }
/*      */           } else {
/* 3295 */             getListOfDevice = "select ammo.RESOURCEID, extdev.DISPLAYNAME from AM_ManagedObject ammo inner join ExternalDeviceDetails extdev on ammo.RESOURCENAME=extdev.NAME where extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY in (" + devCategory + ")";
/*      */           }
/*      */           
/* 3298 */           if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */           {
/* 3300 */             getListOfDevice = getListOfDevice + resourceidCondition;
/*      */           }
/* 3302 */           rs = AMConnectionPool.executeQueryStmt(getListOfDevice);
/* 3303 */           while (rs.next())
/*      */           {
/* 3305 */             String displayname = rs.getString("DISPLAYNAME");
/* 3306 */             displayname = EnterpriseUtil.decodeString(displayname);
/* 3307 */             String resourceid = rs.getString("RESOURCEID");
/* 3308 */             if ((displayname != null) && (resourceid != null))
/*      */             {
/* 3310 */               deviceArray.put(resourceid, displayname);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 3315 */         String getServerList = null;
/* 3316 */         if ((request != null) && (Constants.isPrivilegedUser(request))) {
/* 3317 */           if (Constants.isUserResourceEnabled()) {
/* 3318 */             String loginUserid = Constants.getLoginUserid(request);
/* 3319 */             getServerList = "select ammo.RESOURCEID,ammo.DISPLAYNAME from AM_ManagedObject ammo,AM_USERRESOURCESTABLE, AM_ManagedResourceType amrt where AM_USERRESOURCESTABLE.RESOURCEID=ammo.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid + " and ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS')";
/*      */           } else {
/* 3321 */             Vector resids = ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/* 3322 */             getServerList = "select ammo.RESOURCEID,ammo.DISPLAYNAME from AM_ManagedObject ammo inner join AM_ManagedResourceType amrt on ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS') and " + Constants.getCondition("ammo.RESOURCEID", resids);
/*      */           }
/*      */         }
/*      */         else {
/* 3326 */           getServerList = "select ammo.RESOURCEID,ammo.DISPLAYNAME from AM_ManagedObject ammo inner join AM_ManagedResourceType amrt on ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS')";
/*      */         }
/*      */         
/* 3329 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3331 */           getServerList = getServerList + resourceidCondition;
/*      */         }
/* 3333 */         rs = AMConnectionPool.executeQueryStmt(getServerList);
/* 3334 */         while (rs.next())
/*      */         {
/* 3336 */           String displayname = rs.getString("DISPLAYNAME");
/* 3337 */           displayname = EnterpriseUtil.decodeString(displayname);
/* 3338 */           String resourceid = rs.getString("RESOURCEID");
/* 3339 */           if ((displayname != null) && (resourceid != null))
/*      */           {
/* 3341 */             deviceArray.put(resourceid, displayname);
/*      */           }
/*      */         }
/*      */       }
/* 3345 */       else if (category.equals("Server"))
/*      */       {
/* 3347 */         String getServerList = "select ammo.RESOURCEID,ammo.DISPLAYNAME from AM_ManagedObject ammo inner join AM_ManagedResourceType amrt on ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS')";
/* 3348 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3350 */           getServerList = getServerList + resourceidCondition;
/*      */         }
/* 3352 */         rs = AMConnectionPool.executeQueryStmt(getServerList);
/* 3353 */         while (rs.next())
/*      */         {
/* 3355 */           String displayname = rs.getString("DISPLAYNAME");
/* 3356 */           displayname = EnterpriseUtil.decodeString(displayname);
/* 3357 */           String resourceid = rs.getString("RESOURCEID");
/* 3358 */           if ((displayname != null) && (resourceid != null))
/*      */           {
/* 3360 */             deviceArray.put(resourceid, displayname);
/*      */           }
/*      */         }
/*      */       }
/*      */       else {
/* 3365 */         category = "OpManager-" + category;
/* 3366 */         String getListOfDevice = "select ammo.RESOURCEID, extdev.DISPLAYNAME from AM_ManagedObject ammo inner join ExternalDeviceDetails extdev on ammo .RESOURCENAME=extdev.NAME where extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY='" + category + "'";
/* 3367 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3369 */           getListOfDevice = getListOfDevice + resourceidCondition;
/*      */         }
/* 3371 */         rs = AMConnectionPool.executeQueryStmt(getListOfDevice);
/* 3372 */         while (rs.next())
/*      */         {
/* 3374 */           String displayname = rs.getString("DISPLAYNAME");
/* 3375 */           displayname = EnterpriseUtil.decodeString(displayname);
/* 3376 */           String resourceid = rs.getString("RESOURCEID");
/* 3377 */           if ((displayname != null) && (resourceid != null))
/*      */           {
/* 3379 */             deviceArray.put(resourceid, displayname);
/*      */           }
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
/* 3397 */       return deviceArray;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3385 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3389 */       if (rs != null) {
/*      */         try {
/* 3391 */           rs.close();
/*      */         } catch (Exception e) {
/* 3393 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static ArrayList<String> getAllDevices(String category, String resourceIds, String mapName)
/*      */   {
/* 3403 */     ManagedApplication mo = new ManagedApplication();
/* 3404 */     String haid = null;
/* 3405 */     if (mapName != null) {
/* 3406 */       haid = getBSGIdForMapName(mapName);
/*      */     }
/* 3408 */     deviceArray = new ArrayList();
/* 3409 */     ArrayList<String> opStorCategory = new ArrayList();
/* 3410 */     opStorCategory.add("Host");
/* 3411 */     opStorCategory.add("TapeLibrary");
/* 3412 */     opStorCategory.add("RAID");
/* 3413 */     opStorCategory.add("FCSwitch");
/* 3414 */     opStorCategory.add("HBA");
/* 3415 */     String resourceidCondition = " and RESOURCEID in (" + resourceIds + ")";
/*      */     
/* 3417 */     if ((category == null) || ((EnterpriseUtil.isIt360MSPEdition) && (resourceIds.equals(""))))
/*      */     {
/* 3419 */       return deviceArray;
/*      */     }
/* 3421 */     String path = null;
/*      */     
/* 3423 */     String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + Constants.getEUMChildString() + ")";
/*      */     
/* 3425 */     ResultSet rs = null;
/* 3426 */     AMConnectionPool pool = new AMConnectionPool();
/*      */     try
/*      */     {
/* 3429 */       String toconfigure = null;
/* 3430 */       String configured = null;
/* 3431 */       String oldtoconfigure = null;
/* 3432 */       String oldconfigured = null;
/* 3433 */       String devicetoconfigure = null;
/* 3434 */       if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */       {
/* 3436 */         configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','SAN') and " + eumChildListCond + " and AM_ManagedResourceType.SUBGROUP='" + category + "' " + resourceidCondition + " and relationshipid is not null order by DISPLAYNAME";
/* 3437 */         toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','SAN') and " + eumChildListCond + " and AM_ManagedResourceType.SUBGROUP='" + category + "' " + resourceidCondition + " and relationshipid is null order by DISPLAYNAME";
/*      */       }
/*      */       else
/*      */       {
/* 3441 */         configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','SAN') and " + eumChildListCond + " and AM_ManagedResourceType.SUBGROUP='" + category + "' and relationshipid is not null order by DISPLAYNAME";
/* 3442 */         toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP NOT IN ('NWD','SAN') and " + eumChildListCond + " and AM_ManagedResourceType.SUBGROUP='" + category + "' and relationshipid is null order by DISPLAYNAME";
/*      */       }
/*      */       
/* 3445 */       if (category.equalsIgnoreCase("All Monitors"))
/*      */       {
/* 3447 */         ArrayList<String> deviceCategories = new ArrayList();
/* 3448 */         StringBuilder categories = new StringBuilder();
/* 3449 */         deviceCategories = getDeviceCategories();
/* 3450 */         for (Object tempCategory : deviceCategories)
/*      */         {
/* 3452 */           if (!tempCategory.toString().equalsIgnoreCase("server"))
/*      */           {
/* 3454 */             categories.append("'").append(tempCategory.toString()).append("',");
/*      */           }
/*      */         }
/* 3457 */         String devCategory = categories.toString();
/* 3458 */         if (!"".equals(devCategory))
/*      */         {
/* 3460 */           devCategory = devCategory.substring(0, devCategory.length() - 1);
/* 3461 */           String getListOfDevice = "select ammo.RESOURCEID from AM_ManagedObject ammo inner join ExternalDeviceDetails extdev on ammo .RESOURCENAME=extdev.NAME where extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY in (" + devCategory + ")";
/* 3462 */           if (resourceIds != null)
/*      */           {
/* 3464 */             getListOfDevice = getListOfDevice + resourceidCondition;
/*      */           }
/* 3466 */           rs = AMConnectionPool.executeQueryStmt(getListOfDevice);
/* 3467 */           while (rs.next())
/*      */           {
/* 3469 */             String deviceName = rs.getString("RESOURCEID");
/* 3470 */             if (deviceName != null)
/*      */             {
/* 3472 */               deviceArray.add(deviceName);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 3477 */         String getServerList = "select ammo.RESOURCEID from AM_ManagedObject ammo inner join AM_ManagedResourceType amrt on ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS')";
/*      */         
/* 3479 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3481 */           getServerList = getServerList + resourceidCondition;
/*      */         }
/* 3483 */         rs = AMConnectionPool.executeQueryStmt(getServerList);
/* 3484 */         while (rs.next())
/*      */         {
/* 3486 */           String deviceName = rs.getString("RESOURCEID");
/* 3487 */           if (deviceName != null)
/*      */           {
/* 3489 */             deviceArray.add(deviceName);
/*      */           }
/*      */         }
/*      */       }
/* 3493 */       else if ((category.equalsIgnoreCase("Server")) || (category.equalsIgnoreCase("Windows")))
/*      */       {
/* 3495 */         String getServerList = "select ammo.RESOURCEID from AM_ManagedObject ammo inner join AM_ManagedResourceType amrt on ammo.TYPE=amrt.RESOURCETYPE and amrt.RESOURCEGROUP IN ('SYS')";
/* 3496 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3498 */           getServerList = getServerList + resourceidCondition;
/*      */         }
/* 3500 */         rs = AMConnectionPool.executeQueryStmt(getServerList);
/* 3501 */         while (rs.next())
/*      */         {
/* 3503 */           String deviceName = rs.getString("RESOURCEID");
/* 3504 */           if (deviceName != null)
/*      */           {
/* 3506 */             deviceArray.add(deviceName);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/* 3511 */       else if ((category.indexOf("OpManager") != -1) || (category.indexOf("OpStor") != -1))
/*      */       {
/* 3513 */         configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP in ('NWD','SAN') and " + eumChildListCond + " and relationshipid is not null order by DISPLAYNAME";
/*      */         
/*      */ 
/* 3516 */         toconfigure = "select ammo.RESOURCEID from AM_ManagedObject ammo inner join ExternalDeviceDetails extdev on ammo .RESOURCENAME=extdev.NAME where extdev.CATEGORY NOT in ('OpManager-Interface') and extdev.CATEGORY='" + category + "'";
/* 3517 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3519 */           toconfigure = toconfigure + resourceidCondition;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 3524 */       else if (category.equals("EMO"))
/*      */       {
/*      */ 
/* 3527 */         toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='EMO' and " + eumChildListCond + "and relationshipid is null order by DISPLAYNAME";
/* 3528 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3530 */           toconfigure = toconfigure + resourceidCondition;
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 3535 */       else if ((category.equals("APP")) || (category.equals("DBS")) || (category.equals("SYS")) || (category.equals("URL")))
/*      */       {
/* 3537 */         configured = "select AM_ManagedObject.RESOURCEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='" + category + "' and " + eumChildListCond + " and relationshipid is not null order by DISPLAYNAME";
/* 3538 */         toconfigure = "select AM_ManagedObject.RESOURCEID ,AM_ManagedObject.DISPLAYNAME,AM_ManagedResourceType.IMAGEPATH FROM  AM_ManagedObject JOIN AM_ManagedResourceType on AM_ManagedObject.TYPE  = AM_ManagedResourceType.RESOURCETYPE left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.PARENTID=" + haid + " and AM_ManagedObject.RESOURCEID=AM_PARENTCHILDMAPPER.CHILDID where AM_ManagedResourceType.RESOURCEGROUP='" + category + "' and " + eumChildListCond + " and relationshipid is null order by DISPLAYNAME";
/* 3539 */         if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */         {
/* 3541 */           toconfigure = toconfigure + resourceidCondition;
/*      */         }
/*      */       }
/* 3544 */       if ((deviceArray != null) && (deviceArray.isEmpty()))
/*      */       {
/* 3546 */         ArrayList rows = mo.getRows(toconfigure);
/* 3547 */         rows = mo.getRows(configured);
/*      */         
/* 3549 */         rs = AMConnectionPool.executeQueryStmt(toconfigure);
/* 3550 */         while (rs.next())
/*      */         {
/* 3552 */           String deviceName = rs.getString("RESOURCEID");
/* 3553 */           if (deviceName != null)
/*      */           {
/* 3555 */             deviceArray.add(deviceName);
/*      */           }
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
/* 3573 */       return deviceArray;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3561 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3565 */       if (rs != null) {
/*      */         try {
/* 3567 */           rs.close();
/*      */         } catch (Exception e) {
/* 3569 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getListOfInterfaces(String deviceName)
/*      */   {
/* 3578 */     displaynames = new JSONArray();
/* 3579 */     ResultSet rs = null;
/* 3580 */     StringBuffer sb = new StringBuffer();
/* 3581 */     boolean isExists = false;
/*      */     
/*      */     try
/*      */     {
/* 3585 */       String selectQuery = "select amo.RESOURCENAME, amo.DISPLAYNAME from AM_ManagedObject amo inner join ExternalDeviceInterfaceDetails on amo.RESOURCENAME=NAME inner join AM_ManagedObject am on ROUTERNAME=am.RESOURCENAME where am.RESOURCEID=" + deviceName;
/* 3586 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 3587 */       while (rs.next()) {
/* 3588 */         JSONObject disp = new JSONObject();
/* 3589 */         disp.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 3590 */         disp.put("DISPLAYNAME", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 3591 */         displaynames.put(disp);
/* 3592 */         isExists = true;
/*      */       }
/*      */       
/* 3595 */       if (!isExists)
/*      */       {
/* 3597 */         String apmQuery = "select RESOURCENAME,DISPLAYNAME from AM_ManagedObject where resourceid in (select CHILDID from AM_ManagedObject amo INNER JOIN AM_PARENTCHILDMAPPER ampcm ON amo.RESOURCEID=ampcm.PARENTID and amo.RESOURCEID=" + deviceName + ") and TYPE='NetInterface'";
/* 3598 */         rs = AMConnectionPool.executeQueryStmt(apmQuery);
/* 3599 */         while (rs.next()) {
/* 3600 */           JSONObject disp = new JSONObject();
/* 3601 */           disp.put("RESOURCENAME", rs.getString("RESOURCENAME"));
/* 3602 */           disp.put("DISPLAYNAME", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 3603 */           displaynames.put(disp);
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
/* 3617 */       return displaynames.toString();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3607 */       e.printStackTrace();
/*      */     } finally {
/* 3609 */       if (rs != null) {
/*      */         try {
/* 3611 */           rs.close();
/*      */         } catch (Exception e) {
/* 3613 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String getResIDAndDispName(String resourceids)
/*      */   {
/* 3623 */     displaynames = new JSONArray();
/* 3624 */     ResultSet rs = null;
/*      */     try
/*      */     {
/* 3627 */       if ((resourceids != null) && (!resourceids.trim().equals("")))
/*      */       {
/* 3629 */         String displayNamesQuery = "select RESOURCEID,DISPLAYNAME from AM_ManagedObject where RESOURCEID in (" + resourceids + ")";
/* 3630 */         rs = AMConnectionPool.executeQueryStmt(displayNamesQuery);
/* 3631 */         while (rs.next())
/*      */         {
/* 3633 */           JSONObject disp = new JSONObject();
/* 3634 */           disp.put("RESOURCEID", rs.getString("RESOURCEID"));
/* 3635 */           disp.put("DISPLAYNAME", EnterpriseUtil.decodeString(rs.getString("DISPLAYNAME")));
/* 3636 */           displaynames.put(disp);
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
/* 3659 */       return displaynames.toString();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3643 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/* 3649 */         if (rs != null)
/*      */         {
/* 3651 */           rs.close();
/*      */         }
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/* 3656 */         ex.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getListOfInterfaces1(String deviceName)
/*      */   {
/* 3664 */     ResultSet rs = null;
/* 3665 */     sb = new StringBuffer();
/* 3666 */     boolean isExists = false;
/*      */     
/*      */     try
/*      */     {
/* 3670 */       String selectQuery = "select NAME from ExternalDeviceInterfaceDetails inner join AM_ManagedObject on ROUTERNAME=RESOURCENAME where RESOURCEID=" + deviceName;
/* 3671 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 3672 */       while (rs.next()) {
/* 3673 */         sb.append(rs.getString("NAME") + ",");
/* 3674 */         isExists = true;
/*      */       }
/*      */       
/* 3677 */       if (!isExists)
/*      */       {
/* 3679 */         String apmQuery = "select RESOURCENAME from AM_ManagedObject where resourceid in (select CHILDID from AM_ManagedObject amo INNER JOIN AM_PARENTCHILDMAPPER ampcm ON amo.RESOURCEID=ampcm.PARENTID and amo.RESOURCEID=" + deviceName + ") and TYPE='NetInterface'";
/* 3680 */         rs = AMConnectionPool.executeQueryStmt(apmQuery);
/* 3681 */         while (rs.next()) {
/* 3682 */           sb.append(rs.getString("RESOURCENAME") + ",");
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
/* 3703 */       return sb.toString();
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3687 */       e.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3691 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 3695 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3699 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSnapshotLink(String resourceid)
/*      */   {
/* 3708 */     String deviceLink = "";
/* 3709 */     boolean isExtDevice = false;
/* 3710 */     ResultSet rs = null;
/* 3711 */     String deviceName = Constants.getResName(resourceid);
/*      */     
/*      */     try
/*      */     {
/* 3715 */       String selectQuery = "select * from AM_ManagedObject AM inner join ExternalDeviceDetails EXT on AM.RESOURCENAME=EXT.NAME and AM.RESOURCEID=" + resourceid;
/* 3716 */       rs = AMConnectionPool.executeQueryStmt(selectQuery);
/*      */       
/* 3718 */       if (rs.next()) {
/* 3719 */         isExtDevice = true;
/*      */       }
/*      */       
/* 3722 */       if (isExtDevice)
/*      */       {
/* 3724 */         String query = "select AMAED.MASID, AM.TYPE from AM_AssociatedExtDevices AMAED inner join AM_ManagedObject AM on AM.RESOURCEID=AMAED.RESID inner join ExternalDeviceDetails EXT on AM.RESOURCENAME=EXT.NAME where AM.RESOURCEID=" + resourceid;
/* 3725 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3726 */         int masId = 1;
/*      */         
/* 3728 */         String type = "OpManager-";
/* 3729 */         if (rs.next()) {
/* 3730 */           masId = rs.getInt("MASID");
/* 3731 */           type = rs.getString("TYPE");
/*      */         }
/*      */         
/* 3734 */         if ((type != null) && (type.indexOf("OpStor-") != -1))
/*      */         {
/* 3736 */           deviceLink = "/Properties.do?name=" + deviceName + "&requestid=SNAPSHOT&extProdName=OpStor&EXTDEVICEMASID=" + masId;
/*      */         }
/*      */         else
/*      */         {
/* 3740 */           deviceLink = "/devices/objectdetails.do?name=" + deviceName + "&requestid=SNAPSHOT&extProdName=OpManager&EXTDEVICEMASID=" + masId;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 3745 */         String query = "select RESOURCEID, TYPE from AM_ManagedObject where RESOURCEID=" + resourceid;
/* 3746 */         rs = AMConnectionPool.executeQueryStmt(query);
/* 3747 */         String type = "";
/* 3748 */         int resId = 0;
/*      */         
/* 3750 */         if (rs.next()) {
/* 3751 */           resId = rs.getInt("RESOURCEID");
/* 3752 */           type = rs.getString("TYPE");
/*      */         }
/*      */         
/* 3755 */         deviceLink = "/showresource.do?resourceid=" + resId + "&type=" + type + "&moname=" + deviceName + "&method=showdetails&resourcename=" + deviceName + "&viewType=showResourceTypesAll";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3765 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 3769 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3773 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/* 3777 */       AMLog.debug("The device link is " + deviceLink);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 3761 */       ex.printStackTrace();
/*      */     }
/*      */     finally
/*      */     {
/* 3765 */       if (rs != null)
/*      */       {
/*      */         try
/*      */         {
/* 3769 */           rs.close();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 3773 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3778 */     return deviceLink;
/*      */   }
/*      */   
/*      */   public static String getDisplayName(String resourceId)
/*      */   {
/* 3783 */     String dispName = null;
/* 3784 */     dispName = MGUtil.getDisplayName(resourceId);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3816 */     return dispName;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getImagesForResources(String resourceIds)
/*      */   {
/* 3822 */     ResultSet rs = null;
/* 3823 */     String toReturn = null;
/* 3824 */     if ((resourceIds != null) && (!resourceIds.equals("")))
/*      */     {
/*      */       try
/*      */       {
/* 3828 */         ArrayList<String> resourceidList = new ArrayList();
/* 3829 */         String[] resourceids = resourceIds.split(",");
/* 3830 */         StringBuilder imageList = new StringBuilder();
/* 3831 */         String subQuery = "";
/* 3832 */         if (isPgSql)
/*      */         {
/* 3834 */           subQuery = "ORDER BY ";
/*      */         }
/* 3836 */         else if (isMSSQL)
/*      */         {
/* 3838 */           subQuery = "ORDER BY CHARINDEX(CAST(resourceid AS VARCHAR),'";
/*      */         }
/* 3840 */         else if (isMySQL)
/*      */         {
/* 3842 */           subQuery = "ORDER BY FIELD(resourceid,";
/*      */         }
/* 3844 */         if ((resourceids != null) && (resourceids.length > 0))
/*      */         {
/* 3846 */           for (String resourceid : resourceids)
/*      */           {
/* 3848 */             resourceidList.add(resourceid);
/* 3849 */             if (isPgSql)
/*      */             {
/* 3851 */               subQuery = subQuery + "resourceid=" + resourceid + " DESC,";
/*      */             }
/*      */             else
/*      */             {
/* 3855 */               subQuery = subQuery + resourceid + ",";
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 3860 */         subQuery = subQuery.substring(0, subQuery.length() - 1);
/* 3861 */         if (isMSSQL)
/*      */         {
/* 3863 */           subQuery = subQuery + "')";
/*      */         }
/* 3865 */         if (isMySQL)
/*      */         {
/* 3867 */           subQuery = subQuery + ")";
/*      */         }
/* 3869 */         LinkedHashMap<String, String> tempOtherDeviceHash = new LinkedHashMap();
/* 3870 */         String tempResList = new String();
/* 3871 */         tempResList = resourceidList.toString();
/* 3872 */         tempResList = tempResList.substring(1, tempResList.length() - 1);
/*      */         
/* 3874 */         String otherDeviceQuery = "select AM_ManagedObject.RESOURCEID ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedObject.resourceid IN (" + tempResList + ") " + subQuery + "";
/*      */         
/* 3876 */         rs = AMConnectionPool.executeQueryStmt(otherDeviceQuery);
/* 3877 */         while (rs.next())
/*      */         {
/* 3879 */           String imagePath = rs.getString("IMAGEPATH");
/* 3880 */           imagePath = imagePath.replace("/images/", "");
/*      */           
/* 3882 */           tempOtherDeviceHash.put(rs.getString("RESOURCEID"), imagePath);
/* 3883 */           imageList.append((String)tempOtherDeviceHash.get(rs.getString("RESOURCEID")) + ",");
/*      */         }
/*      */         
/* 3886 */         toReturn = imageList.toString();
/* 3887 */         toReturn = toReturn.substring(0, toReturn.length() - 1);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3895 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/* 3899 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 3903 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 3908 */         AMLog.debug("MAPVIEWUTIL:Returning images for resources");
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 3891 */         e.printStackTrace();
/*      */       }
/*      */       finally
/*      */       {
/* 3895 */         if (rs != null)
/*      */         {
/*      */           try
/*      */           {
/* 3899 */             rs.close();
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/* 3903 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3909 */     return toReturn;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void addSubGroup(String childHaid, String parentHaid)
/*      */   {
/* 3985 */     AMLog.debug("MapViewUtil : About to add as a subgroup with haid : " + childHaid + " for parent  : " + parentHaid);
/*      */     try
/*      */     {
/* 3988 */       long id = DBUtil.insertParentChildMapper(Integer.parseInt(parentHaid), Integer.parseInt(childHaid));
/* 3989 */       AMAttributesDependencyAdder adder = new AMAttributesDependencyAdder();
/* 3990 */       added = adder.addDependentAttributes(Integer.parseInt(parentHaid), Integer.parseInt(childHaid));
/*      */     }
/*      */     catch (Exception exp) {
/*      */       boolean added;
/* 3994 */       exp.printStackTrace();
/*      */     }
/* 3996 */     AMLog.debug("MapViewUtil : Successfully added as a subgroup with haid : " + childHaid + " for parent  : " + parentHaid);
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\utils\client\MapViewUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */