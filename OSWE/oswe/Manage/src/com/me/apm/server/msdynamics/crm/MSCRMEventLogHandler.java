/*    */ package com.me.apm.server.msdynamics.crm;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.me.apm.msdynamics.crm.util.MSDynamicsCRMUtil;
/*    */ import com.me.apm.server.datatable.DataTableDefinition;
/*    */ import com.me.apm.server.datatable.DataTableHandler;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MSCRMEventLogHandler
/*    */   extends DataTableHandler
/*    */ {
/*    */   public JsonObject getTableData(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/* 22 */     this.ajaxDataRequest = request;
/* 23 */     AMLog.info("AJAX " + this.ajaxDataRequest);
/* 24 */     JsonObject resultant = new JsonObject();
/*    */     
/* 26 */     String resId = request.getParameter("resourceid");
/* 27 */     AMLog.info("MSCRM Resid " + resId);
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
/* 38 */       AMLog.info("MSCRM Len  " + lengthRet);
/* 39 */       AMLog.info("MSCRM start " + startRet);
/* 40 */       AMLog.info("MSCRM dra " + drawRet);
/* 41 */       AMLog.info("MSCRM sortColmn " + sortColumn);
/* 42 */       AMLog.info("MSCRM sortDir " + sortDir);
/*    */       
/* 44 */       DataTableDefinition crmEventLogTableDef = MSDynamicsCRMUtil.getEventLogTableDefinition();
/*    */       
/* 46 */       String columnSerachCondition = getColumnSearchCondition(crmEventLogTableDef);
/* 47 */       AMLog.info("MSCRM Column Search " + columnSerachCondition);
/*    */       
/*    */ 
/* 50 */       getTotalRecordsCount(MSDynamicsCRMUtil.getEventLogCountPS(resId));
/*    */       
/* 52 */       String orderColumnName = crmEventLogTableDef.getColumnName(String.valueOf(sortColumn));
/* 53 */       AMLog.info("MSCRM Order columnName " + orderColumnName);
/*    */       
/*    */ 
/* 56 */       String eventLogQuery = MSDynamicsCRMUtil.getEventLogQuery(resId, getGlobalSearchString(), columnSerachCondition, -1L);
/* 57 */       AMLog.info("MSCRM eventlog query " + eventLogQuery);
/*    */       
/* 59 */       getFilteredRecordCount(eventLogQuery);
/*    */       
/* 61 */       dataArray = MSDynamicsCRMUtil.getEventLogData(eventLogQuery, startRet, lengthRet, sortDir, orderColumnName);
/*    */       
/*    */ 
/* 64 */       resultant.addProperty("draw", Integer.valueOf(drawRet));
/* 65 */       resultant.addProperty("recordsTotal", Integer.valueOf(this.totalRecord));
/* 66 */       resultant.addProperty("recordsFiltered", Integer.valueOf(this.totalAfterFilter));
/* 67 */       resultant.add("data", dataArray);
/* 68 */       AMLog.info("MSCRM Eventlog response " + resultant);
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 72 */       AMLog.info("Exception in Eventlog Data table handler ", ex);
/*    */     }
/*    */     
/*    */ 
/* 76 */     return resultant;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\server\msdynamics\crm\MSCRMEventLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */