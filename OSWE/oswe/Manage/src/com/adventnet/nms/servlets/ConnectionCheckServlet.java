/*    */ package com.adventnet.nms.servlets;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ConnectionCheckServlet extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*    */   {
/* 15 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/* 16 */     paramHttpServletResponse.setContentType("text/html");
/* 17 */     localPrintWriter.println("<HTML>");
/* 18 */     localPrintWriter.println("<BODY bgColor=white>");
/* 19 */     localPrintWriter.println("<BR>");
/* 20 */     localPrintWriter.println("<BR>");
/* 21 */     localPrintWriter.println("<CENTER>");
/* 22 */     localPrintWriter.println("<FONT size=6 color=green>");
/* 23 */     localPrintWriter.println("Successfully connected to the server !!");
/* 24 */     localPrintWriter.println("</FONT>");
/* 25 */     localPrintWriter.println("</CENTER>");
/* 26 */     localPrintWriter.println("</BODY>");
/* 27 */     localPrintWriter.println("</HTML>");
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\ConnectionCheckServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */