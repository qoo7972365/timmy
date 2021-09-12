/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
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
/*    */ 
/*    */ 
/*    */ public class AM_InitBrowser
/*    */   extends HttpServlet
/*    */ {
/*    */   public void init(ServletConfig sConfig)
/*    */     throws ServletException
/*    */   {
/* 34 */     BrowserThread bt = new BrowserThread();
/* 35 */     bt.start();
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\AM_InitBrowser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */