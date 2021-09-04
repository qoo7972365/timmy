/*     */ package com.adventnet.appmanager.struts.actions.fault;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.struts.beans.DependantMOUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
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
/*     */ public final class RecentAlarms
/*     */   extends DispatchAction
/*     */ {
/*  37 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public ActionForward getRecentAlarms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
/*     */   {
/*  41 */     String severity = request.getParameter("severity");
/*     */     try
/*     */     {
/*  44 */       String query = "";
/*  45 */       String wherecondition = "";
/*  46 */       if (severity != null)
/*     */       {
/*  48 */         wherecondition = " and  severity='" + severity + "' ";
/*     */       }
/*     */       
/*  51 */       if (Constants.isPrivilegedUser(request))
/*     */       {
/*  53 */         if (Constants.isUserResourceEnabled()) {
/*  54 */           String loginUserid = Constants.getLoginUserid(request);
/*  55 */           query = "select SEVERITY, MODTIME, MMESSAGE, CASE WHEN AM_ManagedObject.RESOURCENAME is NULL then " + DBQueryUtil.castasVarchar("SOURCE") + " else AM_ManagedObject.DISPLAYNAME end as  DISPLAYNAME from AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedObject,AM_PARENTCHILDMAPPER,Alert where AM_HOLISTICAPPLICATION_OWNERS.ownerid=" + loginUserid + " and AM_HOLISTICAPPLICATION_OWNERS.haid=AM_PARENTCHILDMAPPER.parentid and AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and AM_ManagedObject.resourceid=Alert.source and groupname='" + "AppManager" + "' " + wherecondition + " order by createtime desc";
/*     */         } else {
/*  57 */           String owner = request.getRemoteUser();
/*  58 */           query = "select SEVERITY, MODTIME, MMESSAGE, CASE WHEN AM_ManagedObject.RESOURCENAME is NULL then " + DBQueryUtil.castasVarchar("SOURCE") + " else AM_ManagedObject.DISPLAYNAME end as  DISPLAYNAME from AM_UserPasswordTable,AM_HOLISTICAPPLICATION_OWNERS,AM_ManagedObject,AM_PARENTCHILDMAPPER,Alert where AM_UserPasswordTable.username='" + owner + "' and AM_UserPasswordTable.userid=AM_HOLISTICAPPLICATION_OWNERS.ownerid and AM_HOLISTICAPPLICATION_OWNERS.haid=AM_PARENTCHILDMAPPER.parentid and AM_ManagedObject.resourceid=AM_PARENTCHILDMAPPER.childid and AM_ManagedObject.resourceid=Alert.source and groupname='" + "AppManager" + "' " + wherecondition + " order by createtime desc";
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       else {
/*  64 */         query = "select SEVERITY, MODTIME, MMESSAGE, CASE WHEN AM_ManagedObject.RESOURCENAME is NULL then " + DBQueryUtil.castasVarchar("SOURCE") + " else AM_ManagedObject.DISPLAYNAME end as  DISPLAYNAME from Alert left outer join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source where groupname='" + "AppManager" + "' " + wherecondition + " order by createtime  desc";
/*     */       }
/*     */       
/*  67 */       query = DBQueryUtil.getTopNValues(query, 10);
/*     */       
/*  69 */       ArrayList recentAlarms = this.mo.getRows(query);
/*  70 */       request.setAttribute("recentAlarms", recentAlarms);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  74 */       e.printStackTrace();
/*     */     }
/*  76 */     if (severity != null)
/*     */     {
/*     */ 
/*  79 */       return new ActionForward(mapping.findForward("recentAlarms").getPath() + "?title=Recent Critical Alerts");
/*     */     }
/*     */     
/*     */ 
/*  83 */     return mapping.findForward("recentAlarms");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getApplicationAlarms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  92 */     String haid = request.getParameter("haid");
/*  93 */     String particularserverity = "";
/*  94 */     String timeperiodcondition = "";
/*  95 */     if (request.getParameter("severity") != null)
/*  96 */       particularserverity = " and severity=" + request.getParameter("severity") + "";
/*  97 */     String viewId = request.getParameter("viewId");
/*  98 */     if (viewId != null)
/*     */     {
/* 100 */       if (viewId.equals("Alerts.2"))
/*     */       {
/* 102 */         timeperiodcondition = " and MODTIME > " + (System.currentTimeMillis() - 3600000L);
/*     */       }
/* 104 */       else if (viewId.equals("Alerts.4"))
/*     */       {
/* 106 */         timeperiodcondition = " and MODTIME > " + (System.currentTimeMillis() - 86400000L);
/*     */       }
/*     */     }
/*     */     
/* 110 */     String query = "select " + getColumns() + "  from Alert,AM_PARENTCHILDMAPPER left outer join AM_ManagedObject on AM_ManagedObject.resourceid=Alert.source where GROUPNAME='" + "AppManager" + "' AND source=childid and parentid=" + haid + " " + particularserverity + timeperiodcondition + " order by RESOURCENAME ";
/* 111 */     ArrayList recentAlarms = this.mo.getRows(query);
/* 112 */     if (recentAlarms.size() == 0)
/*     */     {
/* 114 */       return new ActionForward("/webclient/common/jsp/NoAlarms.jsp");
/*     */     }
/* 116 */     request.setAttribute("recentAlarms", recentAlarms);
/* 117 */     return mapping.findForward("recentAlarms");
/*     */   }
/*     */   
/*     */   private String getColumns()
/*     */   {
/* 122 */     return " severity, modtime, mmessage, IF(AM_ManagedObject.resourcename is NULL,source,AM_ManagedObject.DISPLAYNAME) as RESOURCENAME";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getAlarmsForResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 131 */     String resourceid = request.getParameter("resourceid");
/* 132 */     Vector resourceids = DependantMOUtil.getDependantResourceIDS(resourceid);
/* 133 */     String query = "select " + getColumns() + "  from Alert ,AM_ManagedObject WHERE GROUPNAME='" + "AppManager" + "' AND AM_ManagedObject.resourceid=Alert.source  AND " + DependantMOUtil.getCondition("SOURCE", resourceids);
/*     */     
/* 135 */     ArrayList recentAlarms = this.mo.getRows(query);
/*     */     
/* 137 */     request.setAttribute("recentAlarms", recentAlarms);
/* 138 */     return mapping.findForward("recentAlarms");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\fault\RecentAlarms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */