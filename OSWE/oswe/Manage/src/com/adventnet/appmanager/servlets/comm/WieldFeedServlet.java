/*    */ package com.adventnet.appmanager.servlets.comm;
/*    */ 
/*    */ import com.adventnet.appmanager.logging.AMLog;
/*    */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*    */ import com.adventnet.appmanager.util.FormatUtil;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WieldFeedServlet
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 22 */     doGet(request, response);
/*    */   }
/*    */   
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */     try
/*    */     {
/* 31 */       AMLog.debug("Enterprise : License called for in Admin Server" + request.getQueryString());
/* 32 */       String buildnumber = request.getParameter("bn");
/* 33 */       PrintWriter out = response.getWriter();
/* 34 */       String req = request.getParameter("file");
/* 35 */       if (req != null)
/*    */       {
/* 37 */         if ("true".equals(System.getProperty("useConsoleFilter")))
/*    */         {
/* 39 */           if (req.equals("Master"))
/*    */           {
/* 41 */             String file = System.getProperty("webnms.rootdir") + File.separator + ".." + File.separator + ".." + File.separator + "licenses" + File.separator + "MasterLicense.xml";
/* 42 */             out.print(FormatUtil.getContentsAsString(file));
/*    */           }
/*    */         }
/*    */       }
/* 46 */       else if (buildnumber != null)
/*    */       {
/*    */ 
/* 49 */         if (!EnterpriseUtil.isCompatibleBuild(buildnumber))
/*    */         {
/* 51 */           out.println("Enterprise : Unable to send back license as build number is not compatible or is null");
/* 52 */           return;
/*    */         }
/* 54 */         String filePath = System.getProperty("webnms.rootdir") + File.separator + "classes" + File.separator + "AdventNetLicense.xml";
/* 55 */         out.print(FormatUtil.getContentsAsString(filePath));
/*    */       }
/* 57 */       out.close();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 61 */       AMLog.debug("Enterprise : Unable to fetch license for" + request.getQueryString() + " Refer stack trace for details");
/* 62 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\WieldFeedServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */