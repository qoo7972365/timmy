/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import java.sql.ResultSet;
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
/*     */ public final class PublishPages
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward getPublishedDashboard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  31 */     String pagekey = request.getParameter("pagekey");
/*  32 */     return new ActionForward("/MyPage.do?method=viewPublishedPage&pagekey=" + pagekey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward getWidget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  41 */     AMConnectionPool pool = new AMConnectionPool();
/*     */     
/*     */     try
/*     */     {
/*  45 */       String widgetid = request.getParameter("widgetid");
/*  46 */       String pageid = request.getParameter("pageid");
/*  47 */       String pagekey = request.getParameter("pagekey");
/*  48 */       boolean forwardtoGetWidget = false;
/*  49 */       ResultSet rs; ResultSet rs; if (DBQueryUtil.getDBType().equals("mssql"))
/*     */       {
/*  51 */         rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID=0x" + pagekey + "");
/*     */       } else { ResultSet rs;
/*  53 */         if (DBQueryUtil.getDBType().equals("mysql"))
/*     */         {
/*  55 */           rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID='" + pagekey + "'");
/*     */         }
/*     */         else
/*     */         {
/*  59 */           rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID='" + pagekey + "'");
/*     */         }
/*     */       }
/*  62 */       if (rs.next())
/*     */       {
/*  64 */         forwardtoGetWidget = true;
/*     */       }
/*     */       else
/*     */       {
/*  68 */         forwardtoGetWidget = false;
/*     */       }
/*  70 */       if (forwardtoGetWidget)
/*     */       {
/*  72 */         return new ActionForward("/MyPage.do?method=getWidget&pageid=" + pageid + "&widgetid=" + widgetid);
/*     */       }
/*     */       
/*     */ 
/*  76 */       return null;
/*     */ 
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  81 */       ex.printStackTrace();
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward getWidgetProperties(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  90 */     ResultSet rs = null;
/*  91 */     boolean forwardtoGetWidget = false;
/*  92 */     String widgetid = null;
/*     */     try
/*     */     {
/*  95 */       widgetid = request.getParameter("widgetid");
/*  96 */       String pagekey = request.getParameter("pagekey");
/*     */       
/*  98 */       if (DBQueryUtil.getDBType().equals("mssql"))
/*     */       {
/* 100 */         rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID=0x" + pagekey + "");
/*     */       }
/* 102 */       else if (DBQueryUtil.getDBType().equals("mysql"))
/*     */       {
/* 104 */         rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID='" + pagekey + "'");
/*     */       }
/*     */       else
/*     */       {
/* 108 */         rs = AMConnectionPool.executeQueryStmt("select AM_MYPAGE_PUBLISHKEYS.PAGEID,AM_MYPAGE_WIDGET_MAPPING.WIDGETID from AM_MYPAGE_PUBLISHKEYS inner join AM_MYPAGE_WIDGET_MAPPING on AM_MYPAGE_WIDGET_MAPPING .PAGEID=AM_MYPAGE_PUBLISHKEYS.PAGEID  and WIDGETID=" + widgetid + " where PUBLISHID='" + pagekey + "'");
/*     */       }
/*     */       
/* 111 */       if (rs.next())
/*     */       {
/* 113 */         forwardtoGetWidget = true;
/*     */       }
/*     */       else
/*     */       {
/* 117 */         forwardtoGetWidget = false;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 122 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 126 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/*     */     
/* 129 */     if (forwardtoGetWidget)
/*     */     {
/* 131 */       return new ActionForward("/MyPage.do?method=getWidgetProperties&widgetid=" + widgetid);
/*     */     }
/*     */     
/*     */ 
/* 135 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\PublishPages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */