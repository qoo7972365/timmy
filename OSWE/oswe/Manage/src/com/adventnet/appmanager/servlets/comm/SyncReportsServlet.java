/*    */ package com.adventnet.appmanager.servlets.comm;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SyncReportsServlet
/*    */   extends HttpServlet
/*    */ {
/* 23 */   private ServletContext servletContext = null;
/* 24 */   private ServletConfig config = null;
/*    */   
/*    */   public void init(ServletConfig sConfig) throws ServletException {
/* 27 */     super.init(sConfig);
/* 28 */     this.servletContext = sConfig.getServletContext();
/*    */   }
/*    */   
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 35 */     doGet(request, response);
/*    */   }
/*    */   
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 41 */     String mySQLPort = "13326";
/* 42 */     PrintWriter out = response.getWriter();
/* 43 */     String filePath = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + "conf" + File.separator + "AMServer.properties";
/* 44 */     Properties serverProps = new Properties();
/* 45 */     serverProps.load(new FileInputStream(new File(filePath)));
/* 46 */     mySQLPort = serverProps.getProperty("am.db.port");
/* 47 */     out.print(mySQLPort);
/* 48 */     out.close();
/*    */   }
/*    */   
/*    */   public void destroy() {}
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\SyncReportsServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */