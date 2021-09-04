/*    */ package com.me.apm.server.xenapp;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.me.apm.server.datatable.DataTableDefinition;
/*    */ import com.me.apm.server.datatable.DataTableHandler;
/*    */ import com.me.apm.xenapp.util.XenAppUtil;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XenAppEventLogHandler
/*    */   extends DataTableHandler
/*    */ {
/*    */   public JsonObject getTableData(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 22 */     this.ajaxDataRequest = request;
/* 23 */     AMLog.info("AJAX " + this.ajaxDataRequest);
/* 24 */     JsonObject resultant = new JsonObject();
/*    */     
/* 26 */     String resId = request.getParameter("resourceid");
/* 27 */     AMLog.debug("XenApp Resid " + resId);
/*    */     
/*    */     try
/*    */     {
/* 31 */       JsonArray dataArray = new JsonArray();
/*    */       
/* 33 */       int lengthRet = getLength();
/* 34 */       int startRet = getStart();
/* 35 */       int drawRet = getDraw();
/* 36 */       int sortColumn = getSortColumnIndex();
/* 37 */       String sortDir = getSortDir();
/* 38 */       AMLog.info("XenApp Len  " + lengthRet);
/* 39 */       AMLog.info("XenApp start " + startRet);
/* 40 */       AMLog.info("XenApp dra " + drawRet);
/* 41 */       AMLog.info("XenApp sortColmn " + sortColumn);
/* 42 */       AMLog.info("XenApp sortDir " + sortDir);
/*    */       
/* 44 */       DataTableDefinition XenEventLogTableDef = XenAppUtil.getEventLogTableDefinition();
/*    */       
/* 46 */       String columnSerachCondition = getColumnSearchCondition(XenEventLogTableDef);
/* 47 */       AMLog.info("XenApp Column Search " + columnSerachCondition);
/*    */       
/*    */ 
/* 50 */       getTotalRecordsCount(XenAppUtil.getEventLogCountPS(resId));
/*    */       
/* 52 */       String orderColumnName = XenEventLogTableDef.getColumnName(String.valueOf(sortColumn));
/* 53 */       AMLog.info("XenApp Order columnName " + orderColumnName);
/*    */       
/*    */ 
/* 56 */       String eventLogQuery = XenAppUtil.getEventLogQuery(resId, getGlobalSearchString(), columnSerachCondition, -1L);
/* 57 */       AMLog.debug("XenApp eventlog query " + eventLogQuery);
/*    */       
/* 59 */       getFilteredRecordCount(eventLogQuery);
/*    */       
/* 61 */       dataArray = XenAppUtil.getEventLogData(eventLogQuery, startRet, lengthRet, sortDir, orderColumnName);
/*    */       
/*    */ 
/* 64 */       resultant.addProperty("draw", Integer.valueOf(drawRet));
/* 65 */       resultant.addProperty("recordsTotal", Integer.valueOf(this.totalRecord));
/* 66 */       resultant.addProperty("recordsFiltered", Integer.valueOf(this.totalAfterFilter));
/* 67 */       AMLog.debug("XenApp Eventlog response " + resultant);
/* 68 */       resultant.add("data", dataArray);
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 72 */       AMLog.debug("Exception in Eventlog Data table handler ", ex);
/*    */     }
/*    */     
/*    */ 
/* 76 */     return resultant;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\server\xenapp\XenAppEventLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */