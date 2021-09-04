/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.manageengine.apminsight.server.util.InsightUtilApi;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EditDisplaynames
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward editDisplaynames(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  34 */     ActionMessages messages = new ActionMessages();
/*  35 */     String resids = request.getParameter("resids");
/*  36 */     ArrayList resourceids = new ArrayList();
/*  37 */     ArrayList<String> mgIdList = new ArrayList();
/*  38 */     Statement statement = null;
/*  39 */     Connection connection = null;
/*  40 */     StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/*  41 */     if (resids == null)
/*     */     {
/*  43 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.editdisplayname.notselected")));
/*  44 */       saveMessages(request, messages);
/*  45 */       request.setAttribute("SUCCESS", "false");
/*  46 */       return mapping.findForward("failed");
/*     */     }
/*     */     
/*     */ 
/*  50 */     int count = 0;
/*  51 */     while (nameAfterComma.hasMoreTokens())
/*     */     {
/*  53 */       String resid = nameAfterComma.nextToken();
/*  54 */       resourceids.add(resid);
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  59 */       connection = AMConnectionPool.getConnection();
/*  60 */       statement = connection.createStatement();
/*     */       
/*     */ 
/*     */ 
/*  64 */       checkForMgs(mgIdList, resourceids);
/*     */       
/*  66 */       String resId = null;
/*  67 */       for (int i = 0; i < resourceids.size(); i++)
/*     */       {
/*  69 */         resId = ((String)resourceids.get(i)).trim();
/*  70 */         String newname = request.getParameter(resId);
/*     */         
/*  72 */         if ((newname != null) && (resId != null))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */           count++;
/*  79 */           String query = "update AM_ManagedObject set DISPLAYNAME = '" + newname + "' where RESOURCEID = " + resId + "";
/*  80 */           if (mgIdList.contains(resId))
/*     */           {
/*  82 */             query = "update AM_ManagedObject set DISPLAYNAME = '" + newname + "',RESOURCENAME = '" + newname + "'  where RESOURCEID = " + resId + "";
/*     */           }
/*  84 */           statement.addBatch(query);
/*  85 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*     */           
/*     */ 
/*  88 */           query = "update AM_ScriptArgs set DISPLAYNAME = '" + newname + "' where RESOURCEID =" + resId + "";
/*  89 */           statement.addBatch(query);
/*  90 */           EnterpriseUtil.addUpdateQueryToFile(query);
/*  91 */           NewMonitorUtil.updateEUMChildDisplayNames(resId, newname, "", "Resource");
/*     */         }
/*     */       }
/*  94 */       statement.executeBatch();
/*  95 */       statement.close();
/*  96 */       if (!Constants.sqlManager) {
/*  97 */         InsightUtilApi.editDisplayNames(request);
/*     */       }
/*     */     }
/*     */     catch (Exception exception)
/*     */     {
/* 102 */       System.out.println("\n\nUnable to edit displayname: check for some exceptions");
/* 103 */       exception.printStackTrace();
/*     */     }
/* 105 */     if (count != 0)
/*     */     {
/* 107 */       String Count = "";
/* 108 */       Count = Count + count;
/* 109 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(FormatUtil.getString("am.webclient.editdisplayname.edited", new String[] { Count })));
/* 110 */       saveMessages(request, messages);
/* 111 */       request.setAttribute("SUCCESS", "true");
/* 112 */       return mapping.findForward("success");
/*     */     }
/*     */     
/*     */ 
/* 116 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("<br>Unable to edit displayname: Check whether you have entered the names for all monitors"));
/* 117 */     saveMessages(request, messages);
/* 118 */     request.setAttribute("SUCCESS", "false");
/* 119 */     return mapping.findForward("failed");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkForMgs(ArrayList<String> mgIdList, ArrayList resourceids)
/*     */   {
/* 130 */     String mgQuery = "select RESOURCEID from AM_ManagedObject where " + Constants.getCondition("RESOURCEID ", new Vector(resourceids)) + " and TYPE='HAI'";
/* 131 */     ResultSet mgRs = null;
/*     */     try
/*     */     {
/* 134 */       mgRs = AMConnectionPool.executeQueryStmt(mgQuery);
/* 135 */       while (mgRs.next())
/*     */       {
/* 137 */         mgIdList.add(mgRs.getString("RESOURCEID"));
/*     */       }
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 142 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 146 */       AMConnectionPool.closeStatement(mgRs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\EditDisplaynames.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */