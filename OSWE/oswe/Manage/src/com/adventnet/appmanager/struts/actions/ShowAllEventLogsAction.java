/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*    */ import com.adventnet.appmanager.db.AMConnectionPool;
/*    */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*    */ import java.io.PrintStream;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.Action;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShowAllEventLogsAction
/*    */   extends Action
/*    */ {
/* 22 */   private ManagedApplication ma = new ManagedApplication();
/*    */   
/*    */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/*    */     try
/*    */     {
/* 29 */       ShowAllEventLogsForm showAllEventLogsForm = (ShowAllEventLogsForm)form;
/* 30 */       if (showAllEventLogsForm == null)
/*    */       {
/* 32 */         showAllEventLogsForm = new ShowAllEventLogsForm();
/*    */       }
/* 34 */       Properties monitorDetails = new Properties();
/* 35 */       String resourceid = request.getParameter("resourceid");
/* 36 */       showAllEventLogsForm.initialize(mapping);
/* 37 */       showAllEventLogsForm.setOwner(request.getRemoteUser());
/* 38 */       if (EnterpriseUtil.isIt360MSPEdition())
/*    */       {
/* 40 */         showAllEventLogsForm.getWindowsMonitors(resourceid, request);
/*    */       }
/*    */       else
/*    */       {
/* 44 */         showAllEventLogsForm.getWindowsMonitors(resourceid);
/*    */       }
/*    */       
/* 47 */       showAllEventLogsForm.set("selectedMonitorId", resourceid);
/*    */       
/* 49 */       monitorDetails = showAllEventLogsForm.monitorDetails;
/* 50 */       String selectedMonitorName = showAllEventLogsForm.selectedMonitorName;
/* 51 */       request.setAttribute("selectedMonitorName", selectedMonitorName);
/* 52 */       request.setAttribute("monitorDetails", monitorDetails);
/* 53 */       String query1 = "select AM_EVENTLOGMONITORDATA.RULETYPE,AM_EVENTLOGMONITORDATA.SOURCE,AM_EVENTLOGMONITORDATA.EVENTID,AM_EVENTTYPE.DESCRIPTION,AM_EVENTLOGMONITORDATA.USERNAME,AM_EVENTLOGMONITORDATA.DESCRIPTIONSTRING,AM_EVENTLOGMONITORDATA.EVENTGENERATEDTIME,AM_GLOBALEVENTLOGRULES.RULENAME from AM_EVENTLOGMONITORDATA,AM_EVENTTYPE,AM_GLOBALEVENTLOGRULES where (AM_EVENTLOGMONITORDATA.RESOURCEID=" + resourceid + ") and (AM_EVENTLOGMONITORDATA.CLUSTERRESOURCEID=-1) and (AM_EVENTLOGMONITORDATA.EVENTTYPE=AM_EVENTTYPE.EVENTTYPE) and (AM_GLOBALEVENTLOGRULES.RULEID=AM_EVENTLOGMONITORDATA.RULEID) order by AM_EVENTLOGMONITORDATA.COLLECTIONTIME desc,AM_EVENTLOGMONITORDATA.EVENTGENERATEDTIME desc";
/* 54 */       String hoststatusquery = "select status from AM_HOSTEVENTLOGSTATUS where RESOURCEID=" + resourceid;
/* 55 */       ArrayList allevents = this.ma.getRows(query1);
/* 56 */       ResultSet rs = AMConnectionPool.executeQueryStmt(hoststatusquery);
/* 57 */       String hosteventlogstatus = "-1";
/* 58 */       if (rs.next()) {
/* 59 */         hosteventlogstatus = rs.getString("status");
/*    */       } else {
/* 61 */         hosteventlogstatus = "-1";
/*    */       }
/* 63 */       request.setAttribute("allevents", allevents);
/* 64 */       request.setAttribute("hosteventlogstatus", hosteventlogstatus);
/*    */     }
/*    */     catch (Exception e) {
/* 67 */       System.out.println("::::::EXCEPTION IN ShowAllEventLogsAction::::" + e);
/* 68 */       e.printStackTrace();
/*    */     }
/* 70 */     return mapping.findForward("showAllEventLogsJsp");
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowAllEventLogsAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */