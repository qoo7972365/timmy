/*    */ package com.adventnet.appmanager.servlets;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadRecorder
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 32 */     OutputStream ostr = null;
/* 33 */     String fname = "Recorder.exe";
/*    */     try
/*    */     {
/* 36 */       response.setHeader("Content-disposition", "attachment; filename=\"" + fname + "\";");
/* 37 */       response.setContentType("application/exe");
/* 38 */       String Root_Dir = System.getProperty("webnms.rootdir");
/* 39 */       String[] dirs = Root_Dir.split("working");
/* 40 */       String Work_Dir = dirs[0];
/* 41 */       String absolutepath = Work_Dir + "bin/Recorder.exe";
/*    */       
/* 43 */       File file = new File(absolutepath);
/* 44 */       FileInputStream fis = new FileInputStream(file);
/* 45 */       ostr = response.getOutputStream();
/* 46 */       int c = -1;
/* 47 */       while ((c = fis.read()) != -1)
/*    */       {
/* 49 */         ostr.write(c);
/*    */       }
/* 51 */       fis.close();
/* 52 */       ostr.flush(); return;
/*    */     }
/*    */     catch (Exception ex)
/*    */     {
/* 56 */       ex.printStackTrace();
/*    */     }
/*    */     finally {
/*    */       try {
/* 60 */         if (ostr != null)
/*    */         {
/* 62 */           ostr.close();
/*    */         }
/*    */       }
/*    */       catch (Exception ex) {
/* 66 */         System.out.println("Error in Releasing Streams: " + ex.toString());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\DownloadRecorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */