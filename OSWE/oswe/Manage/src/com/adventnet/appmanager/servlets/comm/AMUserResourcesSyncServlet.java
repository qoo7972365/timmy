/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*     */ import com.manageengine.appmanager.util.DelegatedUserRoleUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ public class AMUserResourcesSyncServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  24 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  29 */     response.setContentType("text/html; charset=UTF-8");
/*  30 */     PrintWriter out = response.getWriter();
/*  31 */     String isSyncConfigtoUserMap = request.getParameter("isSyncConfigtoUserMap");
/*  32 */     if ((isSyncConfigtoUserMap != null) && ("true".equals(isSyncConfigtoUserMap)))
/*     */     {
/*  34 */       fetchAllConfigToUserMappingForMAS(out);
/*  35 */       return;
/*     */     }
/*  37 */     String masRange = request.getParameter("ForMasRange");
/*  38 */     String userId = request.getParameter("userId");
/*  39 */     String chkRestrictedRole = request.getParameter("chkRestrictedRole");
/*  40 */     AMLog.debug("[AMUserResourcesSyncServlet::(doGet)] masRange : " + masRange + ", userId : " + userId + " , chkRestrictedRole : " + chkRestrictedRole);
/*     */     
/*  42 */     if ((chkRestrictedRole != null) && ("true".equals(chkRestrictedRole)))
/*     */     {
/*  44 */       boolean isRestricted = RestrictedUsersViewUtil.isRestrictedRole(userId);
/*  45 */       out.println(isRestricted);
/*     */ 
/*     */ 
/*     */     }
/*  49 */     else if (masRange != null) {
/*  50 */       if ((userId != null) && (!"".equals(userId)))
/*     */       {
/*  52 */         fetchUserResourcesofMASForUserId(userId, masRange, out);
/*     */       }
/*     */       else
/*     */       {
/*  56 */         fetchAllUserResourcesForMAS(masRange, out);
/*     */       }
/*     */     }
/*     */     else {
/*  60 */       AMLog.debug("[AMUserResourcesSyncServlet::(doGet)] Improper mas range is given");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void fetchUserResourcesofMASForUserId(String userId, String masRange, PrintWriter out)
/*     */   {
/*  68 */     int stRange = Integer.parseInt(masRange);
/*  69 */     int endRange = stRange + EnterpriseUtil.RANGE;
/*  70 */     String qry = "select distinct(RESOURCEID) from AM_USERRESOURCESTABLE where USERID=" + userId + " and RESOURCEID >" + stRange + " and RESOURCEID < " + endRange;
/*  71 */     AMLog.debug("[AMUserResourcesSyncServlet::(fetchUserResourcesofMASForUserId)] qry : " + qry);
/*     */     
/*  73 */     ResultSet rs = null;
/*     */     try
/*     */     {
/*  76 */       rs = AMConnectionPool.executeQueryStmt(qry);
/*  77 */       while (rs.next())
/*     */       {
/*  79 */         String resId = rs.getString(1);
/*  80 */         out.println(resId);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  85 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  89 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void fetchAllUserResourcesForMAS(String masRange, PrintWriter out)
/*     */   {
/*  96 */     int stRange = Integer.parseInt(masRange);
/*  97 */     int endRange = stRange + EnterpriseUtil.RANGE;
/*  98 */     String qry = "select * from AM_USERRESOURCESTABLE where RESOURCEID >" + stRange + " and RESOURCEID < " + endRange;
/*  99 */     AMLog.debug("[AMUserResourcesSyncServlet::(fetchAllUserResourcesForMAS)] qry : " + qry);
/*     */     
/* 101 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 104 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 105 */       while (rs.next())
/*     */       {
/* 107 */         String userId = rs.getString(1);
/* 108 */         String resId = rs.getString(2);
/* 109 */         out.println(userId + "," + resId);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 114 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 118 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void fetchAllConfigToUserMappingForMAS(PrintWriter out)
/*     */   {
/* 125 */     String qry = "select * from AM_CONFIGTOUSER_MAPPING where " + Constants.getCondition("CONFIGTYPE", DelegatedUserRoleUtil.getConfigTypesToSynch());
/* 126 */     AMLog.debug("[AMUserResourcesSyncServlet::(fetchAllConfigToUserMappingForMAS)] qry : " + qry);
/*     */     
/* 128 */     ResultSet rs = null;
/*     */     try
/*     */     {
/* 131 */       rs = AMConnectionPool.executeQueryStmt(qry);
/* 132 */       while (rs.next())
/*     */       {
/* 134 */         String relationID = rs.getString("RELATIONSHIPID");
/* 135 */         String configID = rs.getString("CONFIGID");
/* 136 */         String userID = rs.getString("USERID");
/* 137 */         String userGroupID = rs.getString("USERGROUPID");
/* 138 */         String configType = rs.getString("CONFIGTYPE");
/* 139 */         out.println(relationID + "," + configID + "," + userID + "," + userGroupID + "," + configType);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 144 */       ex.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 148 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\AMUserResourcesSyncServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */