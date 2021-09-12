/*    */ package com.adventnet.nms.servlets;
/*    */ 
/*    */ import com.adventnet.nms.db.util.TreeAPI;
/*    */ import com.adventnet.nms.store.relational.RelationalAPI;
/*    */ import com.adventnet.nms.util.NmsUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.sql.Connection;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class GetTreeFromDB extends javax.servlet.http.HttpServlet
/*    */ {
/*    */   public String getServletInfo()
/*    */   {
/* 20 */     return "This servlet returns an Element to construct a tree";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 35 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 49 */     PrintWriter localPrintWriter = null;
/* 50 */     String str1 = null;
/* 51 */     Connection localConnection = null;
/* 52 */     String str2 = paramHttpServletRequest.getParameter("userName");
/* 53 */     String str3 = paramHttpServletRequest.getParameter("ACTION-ON-NO-PRIVILEGE");
/* 54 */     if (paramHttpServletRequest.getParameter("nodeID") != null)
/* 55 */       str1 = paramHttpServletRequest.getParameter("nodeID");
/*    */     try {
/* 57 */       localConnection = NmsUtil.relapi.getConnection();
/*    */     }
/*    */     catch (Exception localException1)
/*    */     {
/* 61 */       localException1.printStackTrace();
/*    */     }
/*    */     try
/*    */     {
/* 65 */       paramHttpServletResponse.setStatus(200);
/* 66 */       paramHttpServletResponse.setContentType("text/html");
/* 67 */       localPrintWriter = paramHttpServletResponse.getWriter();
/* 68 */       TreeAPI localTreeAPI = new TreeAPI(localConnection);
/* 69 */       Element localElement = localTreeAPI.getNodeFromDB(str2, str1, str3);
/* 70 */       localPrintWriter.println(localElement);
/*    */     }
/*    */     catch (IOException localIOException)
/*    */     {
/* 74 */       localIOException.printStackTrace();
/* 75 */       throw localIOException;
/*    */     }
/*    */     catch (Exception localException2)
/*    */     {
/* 79 */       localException2.printStackTrace();
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 84 */     localPrintWriter.flush();
/* 85 */     localPrintWriter.close();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\GetTreeFromDB.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */