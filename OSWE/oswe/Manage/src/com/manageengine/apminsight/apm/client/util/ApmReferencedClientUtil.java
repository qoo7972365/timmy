/*     */ package com.manageengine.apminsight.apm.client.util;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.struts.beans.ClientDBUtil;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.UserUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.manageengine.apminsight.client.struts.actionhelpers.ActionsMenuHelper;
/*     */ import com.manageengine.apminsight.client.util.ClientUtilInterface;
/*     */ import com.manageengine.apminsight.common.logging.APMInsightLogger;
/*     */ import com.manageengine.apminsight.common.util.StringUtils;
/*     */ import com.manageengine.apminsight.server.RequestInfo;
/*     */ import com.manageengine.apminsight.server.dao.ResourceType;
/*     */ import java.io.File;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.json.simple.JSONArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApmReferencedClientUtil
/*     */   implements ClientUtilInterface
/*     */ {
/*     */   public String getWebServerPort()
/*     */   {
/*  40 */     return AMAutomaticPortChanger.getwebserverport();
/*     */   }
/*     */   
/*     */   public List<Long> getUserSpecficResouces(HttpServletRequest request)
/*     */   {
/*  45 */     if (ClientDBUtil.isPrivilegedUser(request))
/*     */     {
/*  47 */       APMInsightLogger.getClientLogger().log(Level.WARNING, "OPERATOR :: " + request.getRemoteUser());
/*  48 */       String usrSpecificResIdsString = StringUtils.getAsCSV(ClientDBUtil.getResourceIdentity(request.getRemoteUser()).toArray(), false);
/*  49 */       List<Long> resourceList = new ArrayList();
/*     */       try
/*     */       {
/*  52 */         Long[] userSpecificIds = DBUtil.getResourceIdForType(new String[] { "APM-Insight-Instance" }, usrSpecificResIdsString);
/*  53 */         resourceList.addAll(Arrays.asList(userSpecificIds));
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/*  57 */         APMInsightLogger.getClientLogger().log(Level.WARNING, "Unable to find the user specific apminsight instance ids", e);
/*     */       }
/*  59 */       return resourceList;
/*     */     }
/*  61 */     if ((request.isUserInRole("ENTERPRISEADMIN")) && (request.getHeader("op_instances") != null))
/*     */     {
/*  63 */       return Arrays.asList(StringUtils.getAsLongArrayFromCSV(request.getHeader("op_instances")));
/*     */     }
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public JSONArray getUserSpecficActionsMenu(HttpServletRequest request)
/*     */   {
/*  70 */     JSONArray actions = null;
/*  71 */     if (request.isUserInRole("OPERATOR"))
/*     */     {
/*  73 */       actions = ActionsMenuHelper.getActions("operator-actions");
/*  74 */       if (!UserUtil.checkOperatorPermission())
/*     */       {
/*  76 */         actions = ActionsMenuHelper.getActions("user-actions");
/*     */       }
/*     */     }
/*  79 */     else if (((request.isUserInRole("DEMO")) || (request.isUserInRole("USERS")) || (request.isUserInRole("ENTERPRISEADMIN"))) && (!request.isUserInRole("ADMIN")))
/*     */     {
/*  81 */       actions = ActionsMenuHelper.getActions("user-actions");
/*     */     }
/*     */     else
/*     */     {
/*  85 */       actions = ((RequestInfo)request.getAttribute("requestInfo")).getScope().equals(ResourceType.APPLICATION) ? ActionsMenuHelper.getApplicationActions() : ActionsMenuHelper.getInstanceActions();
/*     */     }
/*     */     
/*  88 */     return actions;
/*     */   }
/*     */   
/*     */   public String getLogoForReport()
/*     */   {
/*  93 */     File file = new File(NmsUtil.AIM_ROOT + File.separator + "images" + File.separator + "report_logo.png");
/*  94 */     if (file.exists()) {
/*  95 */       return NmsUtil.AIM_ROOT + "/images/report_logo.png";
/*     */     }
/*  97 */     return NmsUtil.AIM_ROOT + "/images/am_logo.png";
/*     */   }
/*     */   
/*     */ 
/*     */   public List<Long> getUserSpecficResouces(RequestInfo rInfo)
/*     */   {
/* 103 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public JSONArray getUserSpecficActionsMenu(RequestInfo rInfo)
/*     */   {
/* 109 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\apminsight\apm\client\util\ApmReferencedClientUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */