/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Hashtable;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
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
/*     */ 
/*     */ public class FeedbackAction
/*     */   extends DispatchAction
/*     */ {
/*     */   private AMConnectionPool pool;
/*     */   
/*     */   public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  49 */     String name = request.getParameter("Name");
/*  50 */     String from = request.getParameter("Email");
/*  51 */     if (from.equals("")) {
/*  52 */       from = "unknown@unknown.com";
/*     */     }
/*  54 */     this.pool = AMConnectionPool.getInstance();
/*  55 */     String message = request.getParameter("Description");
/*  56 */     message = new String(message.getBytes("UTF-8"), "UTF-8");
/*  57 */     name = new String(name.getBytes("UTF-8"), "UTF-8");
/*  58 */     String to = request.getParameter("To");
/*  59 */     String subject = request.getParameter("Subject");
/*  60 */     String context = request.getParameter("context");
/*     */     
/*  62 */     String databaseType = "MySQL";
/*  63 */     if (DBQueryUtil.isPgsql())
/*     */     {
/*  65 */       databaseType = "PostgreSQL";
/*     */     }
/*  67 */     else if (DBQueryUtil.isMssql())
/*     */     {
/*  69 */       databaseType = "MSSQL";
/*     */     }
/*  71 */     String body = "Name : " + name + "<br>";
/*  72 */     body = body + "EMail : " + from + "<br>";
/*  73 */     body = body + "Feedback : " + message + "<br>";
/*  74 */     body = body + "Context : " + context + "<br>";
/*  75 */     body = body + "Database Type : " + databaseType + "<br>";
/*  76 */     body = body + FormatUtil.getString("am.webclient.login.footermessagewithbuildno.text", new String[] { NmsUtil.GetString("product.build.number"), String.valueOf(DBUtil.getNumberOfMonitors()), String.valueOf(DBUtil.getNumberOfUsers()) });
/*     */     
/*  78 */     String returnVal = "";
/*  79 */     String errorMsg = "";
/*     */     try {
/*  81 */       SmtpMailer mailer = new SmtpMailer(from, to, "", subject);
/*  82 */       mailer.sendMessage(body, null, true, body, 2, null);
/*     */     } catch (Exception e) {
/*  84 */       e.printStackTrace();
/*  85 */       if ((e instanceof MessagingException))
/*     */       {
/*  87 */         String error = ((MessagingException)e).getNextException().getMessage();
/*  88 */         if ((error.indexOf("Domain name required for sender address") != -1) || ((error.indexOf("Domain of sender address") != -1) && (error.indexOf("does not exist") != -1)))
/*     */         {
/*  90 */           errorMsg = errorMsg + "Invalid EMail ID";
/*  91 */           SmtpMailer mailer = new SmtpMailer("noreply@adventnet.com", to, "", subject);
/*  92 */           mailer.sendMessage(body, null, true, body, 2, null);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 100 */     returnVal = returnVal + FormatUtil.getString("am.webclient.inlinefeedback.success", new String[] { OEMUtil.getOEMString("product.talkback.mailid") });
/* 101 */     response.setContentType("text/html;charset=UTF-8");
/* 102 */     PrintWriter pw = response.getWriter();
/* 103 */     pw.println(returnVal + "," + errorMsg);
/*     */     
/* 105 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ActionForward hideFeedback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 113 */     String query = "update AM_GLOBALCONFIG set VALUE='false' where NAME='showFeedback'";
/* 114 */     AMConnectionPool.executeUpdateStmt(query);
/* 115 */     Hashtable hash = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 116 */     hash.put("showFeedback", "false");
/* 117 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\FeedbackAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */