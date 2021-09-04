/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OPMRequestHandlerServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public static final String WINDOWS_DEVICES = "1";
/*     */   public static final String UNIX_DEVICES = "2";
/*     */   public static final String NETWORKING_DEVICES = "3";
/*     */   public static final String ALL_DEVICES = "4";
/*     */   public static final String NON_NETWORKING_DEVICES = "5";
/*     */   public static final String VM_ENTITY_TYPE = "VirtualMachine";
/*     */   public static final String VHOST_TYPE = "ESXServer";
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException
/*     */   {
/*  33 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
/*     */   {
/*  38 */     Properties resultProps = new Properties();
/*  39 */     String operationType = request.getParameter("OPERATION_TYPE");
/*  40 */     if ((operationType != null) && (operationType.equals("GET_FILTERED_DEVICES")))
/*     */     {
/*  42 */       String filterID = request.getParameter("FILTER_ID");
/*  43 */       resultProps = getFilteredDevices(filterID);
/*     */     }
/*  45 */     else if ((operationType != null) && (operationType.equals("APM_API_KEY_REQUEST")))
/*     */     {
/*  47 */       String userName = request.getParameter("USERNAME");
/*  48 */       String APIKey = EnterpriseUtil.getAPIKey(userName);
/*  49 */       if (APIKey != null)
/*     */       {
/*  51 */         resultProps.put("APIKey", APIKey);
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  56 */       PrintWriter out = response.getWriter();
/*  57 */       resultProps.store(out, "");
/*  58 */       out.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  62 */       AMLog.debug("Exception in OPMRequestHandlerServlet::::" + e);
/*  63 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Properties getFilteredDevices(String filterID)
/*     */   {
/*  70 */     Properties deviceProps = new Properties();
/*  71 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  74 */       StringBuilder selectQuery = new StringBuilder("select RESOURCEID,RESOURCENAME from AM_ManagedObject join AM_ManagedResourceType on AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE");
/*  75 */       if (filterID.equals("SER"))
/*     */       {
/*  77 */         selectQuery.append(" where AM_ManagedResourceType.RESOURCEGROUP IN " + Constants.serverResGpTypes);
/*     */       }
/*  79 */       else if (filterID.equals("VMWARE"))
/*     */       {
/*  81 */         selectQuery.append(" where AM_ManagedResourceType.RESOURCEGROUP IN ('VIR')");
/*     */       }
/*  83 */       else if (filterID.equals("1"))
/*     */       {
/*  85 */         selectQuery.append(" where AM_ManagedResourceType.RESOURCEGROUP IN ('SYS') AND AM_ManagedResourceType.SUBGROUP='Windows'");
/*     */       }
/*  87 */       else if (filterID.equals("2"))
/*     */       {
/*  89 */         selectQuery.append(" where AM_ManagedResourceType.RESOURCEGROUP IN ('SYS') AND AM_ManagedResourceType.SUBGROUP='Linux'");
/*     */       }
/*  91 */       else if (filterID.equals("5"))
/*     */       {
/*  93 */         selectQuery.append(" where AM_ManagedResourceType.RESOURCEGROUP IN ('SYS')");
/*     */       }
/*  95 */       rs = AMConnectionPool.executeQueryStmt(selectQuery.toString());
/*  96 */       while (rs.next())
/*     */       {
/*  98 */         deviceProps.setProperty(rs.getString("RESOURCEID"), rs.getString("RESOURCENAME"));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 103 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 107 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 109 */     return deviceProps;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\OPMRequestHandlerServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */