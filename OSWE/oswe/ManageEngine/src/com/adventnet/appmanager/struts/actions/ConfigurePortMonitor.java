/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.AMDataCollectionHandler;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConfigurePortMonitor
/*     */   extends Action
/*     */ {
/*  48 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  55 */     ActionMessages messages = new ActionMessages();
/*  56 */     ActionErrors errors = new ActionErrors();
/*  57 */     String haid = request.getParameter("haid");
/*  58 */     String appname = request.getParameter("appname");
/*  59 */     String resourcename = request.getParameter("resourcename");
/*  60 */     String moname = request.getParameter("moname");
/*  61 */     String resourceid = request.getParameter("resourceid");
/*     */     try
/*     */     {
/*  64 */       String name = request.getParameter("host");
/*  65 */       String port = request.getParameter("port");
/*  66 */       String command = request.getParameter("command");
/*  67 */       String search = request.getParameter("search");
/*  68 */       String displayname = request.getParameter("displayname");
/*     */       try
/*     */       {
/*  71 */         AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt("select * from AM_ManagedObject where RESOURCEID=" + resourceid);
/*  72 */         if (rs.next())
/*     */         {
/*  74 */           moname = rs.getString("RESOURCENAME");
/*  75 */           rs.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*  81 */       String poll = request.getParameter("pollInterval");
/*  82 */       int pollinterval = 300;
/*     */       try
/*     */       {
/*  85 */         pollinterval = Integer.parseInt(poll);
/*  86 */         pollinterval *= 60;
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*  92 */       if (request.getParameter("reconfigure").equals("true"))
/*     */       {
/*     */         try
/*     */         {
/*  96 */           String query = "update AM_PORTCONFIG set COMMAND='" + command + "' , SEARCH='" + search + "' where RESOURCEID='" + resourceid + "'";
/*  97 */           AMDataCollectionHandler.getInstance().updateCollectData(moname, "PORT", pollinterval, true);
/*  98 */           AMDataCollectionHandler.getInstance().updateError(Integer.parseInt(resourceid), "Configurations for Service Monitoring is changed. Please wait till the next polling interval.");
/*  99 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 100 */           AMConnectionPool.executeUpdateStmt(query);
/* 101 */           if (displayname != null)
/*     */           {
/* 103 */             String dispChangeQ = "update AM_ManagedObject set DISPLAYNAME='" + displayname + "' where RESOURCEID=" + resourceid;
/* 104 */             AMConnectionPool.executeUpdateStmt(dispChangeQ);
/* 105 */             EnterpriseUtil.addUpdateQueryToFile(dispChangeQ);
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */ 
/* 113 */           e.printStackTrace();
/*     */         }
/* 115 */         return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/*     */       }
/*     */       
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/* 121 */       messages.add("org.apache.struts.action.ERROR", new ActionMessage("portconf.creation.failed", ee.toString()));
/* 122 */       saveMessages(request, messages);
/*     */     }
/* 124 */     return new ActionForward("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ConfigurePortMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */