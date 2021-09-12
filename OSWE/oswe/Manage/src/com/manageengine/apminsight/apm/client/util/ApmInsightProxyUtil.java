/*     */ package com.manageengine.apminsight.apm.client.util;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*     */ import com.manageengine.apminsight.common.logging.APMInsightLogger;
/*     */ import com.manageengine.apminsight.common.util.StringUtils;
/*     */ import com.manageengine.apminsight.framework.db.QueryEnvironment;
/*     */ import com.manageengine.apminsight.framework.db.SQLQueryAPI;
/*     */ import com.manageengine.apminsight.server.I18NHandlerApi;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.http.HttpServletRequest;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApmInsightProxyUtil
/*     */ {
/*     */   public static final String APMINSIGHT_URLPATTERN = "/apminsight/";
/*     */   public static final String X_REQUESTED_WITH = "X-Requested-With";
/*     */   public static final String XML_HTTP_REQUEST = "XMLHttpRequest";
/*     */   public static final String MANAGED_SERVER_ID = "managedServerId";
/*     */   public static final String MANAGED_SERVER_SELECTION_ELEMENT = "<input type='hidden' name='managedServerId' value='-1'/>";
/*     */   
/*     */   public static String checkForProxyExecution(ServletRequest request)
/*     */   {
/*  45 */     String resourceId = null;
/*  46 */     HttpServletRequest httpServletReq = (HttpServletRequest)request;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  51 */     resourceId = httpServletReq.getParameter("resourceId");
/*  52 */     if (httpServletReq.getParameter("parentId") != null)
/*     */     {
/*  54 */       resourceId = httpServletReq.getParameter("parentId");
/*     */     }
/*     */     
/*  57 */     if (StringUtils.isEmptyString(resourceId))
/*     */     {
/*  59 */       resourceId = constructFakeResourceId(httpServletReq);
/*     */     }
/*  61 */     return resourceId;
/*     */   }
/*     */   
/*     */   private static String constructFakeResourceId(HttpServletRequest httpServletReq)
/*     */   {
/*  66 */     String resourceId = httpServletReq.getParameter("managedServerId");
/*     */     try
/*     */     {
/*  69 */       Map dServerDetails = null;
/*  70 */       Long allotedGlobalRange = Long.valueOf(-1L);
/*  71 */       if (resourceId == null)
/*     */       {
/*  73 */         masApmInsightMap = getMasInsightMap();
/*  74 */         allotedGlobalRange = (Long)masApmInsightMap.keySet().toArray()[0];
/*     */       }
/*     */       else
/*     */       {
/*  78 */         dServerDetails = CommDBUtil.getManagedServerDetails(Integer.valueOf(resourceId).intValue());
/*  79 */         allotedGlobalRange = Long.valueOf((String)dServerDetails.get("ALLOTED_GLOBAL_RANGE"));
/*     */       }
/*  81 */       Map<Long, Integer> masApmInsightMap = allotedGlobalRange;Long localLong1 = allotedGlobalRange = Long.valueOf(allotedGlobalRange.longValue() + 1L);
/*  82 */       resourceId = allotedGlobalRange.toString();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  86 */       APMInsightLogger.getClientLogger().log(Level.INFO, "Exception while constructing FakeResourceId. resourceId assigned null.", e);
/*     */     }
/*  88 */     return resourceId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Map getMasInsightMap()
/*     */   {
/*     */     try
/*     */     {
/*  98 */       String query = SQLQueryAPI.getSQLString("get.mas.apminsight.apps.count");
/*  99 */       return SQLQueryAPI.getAsMapForColumnKey(query, 1, false, SQLQueryAPI.getQueryEnvironment().getDefaultAdapter());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 103 */       APMInsightLogger.getClientLogger().log(Level.WARNING, "Exception while fetching managed server ids which has apminsight instances");
/*     */     }
/* 105 */     return new HashMap();
/*     */   }
/*     */   
/*     */   public static String fillManagedServersSelectionComboBox(HttpServletRequest httpServletReq)
/*     */   {
/* 110 */     StringBuffer comboBoxAsHTML = new StringBuffer();
/* 111 */     comboBoxAsHTML.append(I18NHandlerApi.getString("apminsight.home.select.managedserver") + ":" + "&nbsp;&nbsp;" + "<select name='" + "managedServerId" + "' onchange='javascript:managedServerChanged(this)'>");
/*     */     
/* 113 */     String managedServerId = httpServletReq.getParameter("managedServerId");
/*     */     try
/*     */     {
/* 116 */       masApmInsightMap = getMasInsightMap();
/* 117 */       if (masApmInsightMap.size() > 0)
/*     */       {
/* 119 */         List allDistrServerDetails = CommDBUtil.getDistributedServers();
/* 120 */         for (Object dServerAsObj : allDistrServerDetails)
/*     */         {
/* 122 */           Map dServer = (Map)dServerAsObj;
/* 123 */           Long dServerAllotedRange = Long.valueOf(StringUtils.getAsLong(dServer.get("ALLOTED_GLOBAL_RANGE").toString()));
/* 124 */           if (masApmInsightMap.containsKey(dServerAllotedRange))
/*     */           {
/* 126 */             String dServerId = (String)dServer.get("ID");
/* 127 */             String dServerDispName = (String)dServer.get("DISPLAYNAME");
/* 128 */             comboBoxAsHTML.append("<option value='" + dServerId + "'");
/* 129 */             if ((managedServerId != null) && (managedServerId.equals(dServerId)))
/*     */             {
/* 131 */               comboBoxAsHTML.append(" selected");
/*     */             }
/* 133 */             comboBoxAsHTML.append(">" + dServerDispName + "</option>");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*     */       Map<Long, Integer> masApmInsightMap;
/* 140 */       APMInsightLogger.getClientLogger().log(Level.INFO, "Exception while filling managed servers selection combobox.", e);
/*     */     }
/* 142 */     return "</select>";
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\apminsight\apm\client\util\ApmInsightProxyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */