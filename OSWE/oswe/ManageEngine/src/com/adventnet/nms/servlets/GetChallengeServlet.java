/*    */ package com.adventnet.nms.servlets;
/*    */ 
/*    */ import com.adventnet.nms.util.PureServerUtilsFE;
/*    */ import com.adventnet.security.authentication.AuthenticationException;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.net.URLEncoder;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class GetChallengeServlet
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*    */   {}
/*    */   
/* 22 */   String userName = null;
/*    */   
/*    */   public void service(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*    */   {
/* 26 */     this.userName = null;
/* 27 */     String str1 = null;
/* 28 */     String str2 = null;
/* 29 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
/* 30 */     PrintWriter localPrintWriter = null;
/*    */     
/* 32 */     if (localHttpSession != null)
/*    */     {
/* 34 */       this.userName = ((String)localHttpSession.getAttribute("userName"));
/*    */     }
/*    */     
/* 37 */     if ((this.userName = paramHttpServletRequest.getHeader("userName")) == null)
/*    */     {
/* 39 */       this.userName = paramHttpServletRequest.getParameter("userName");
/*    */     }
/*    */     
/* 42 */     if (this.userName != null)
/*    */     {
/*    */       try {
/* 45 */         str1 = PureServerUtilsFE.getChallenge(this.userName);
/* 46 */         str1 = URLEncoder.encode(str1);
/*    */       }
/*    */       catch (AuthenticationException localAuthenticationException) {
/* 49 */         localPrintWriter = paramHttpServletResponse.getWriter();
/* 50 */         localPrintWriter.println("No such user");
/*    */       }
/* 52 */       if (str1 != null)
/*    */       {
/*    */ 
/* 55 */         localHttpSession = paramHttpServletRequest.getSession(true);
/*    */         
/* 57 */         localPrintWriter = paramHttpServletResponse.getWriter();
/* 58 */         str2 = "SessionId=" + localHttpSession.getId() + ";Challenge=" + str1;
/* 59 */         localPrintWriter.println(str2);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 64 */     if (localPrintWriter != null) {
/* 65 */       localPrintWriter.flush();
/* 66 */       localPrintWriter.close();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\GetChallengeServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */