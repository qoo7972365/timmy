/*    */ package com.adventnet.nms.servlets;
/*    */ 
/*    */ import com.adventnet.nms.poll.PollAPI;
/*    */ import com.adventnet.nms.util.GenericUtility;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PollerShutdown
/*    */   extends AuthenticationServlet
/*    */ {
/*    */   public String getServletInfo()
/*    */   {
/* 24 */     return "This servlet shuts down the poller or pollers";
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
/*    */ 
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 40 */     doPost(paramHttpServletRequest, paramHttpServletResponse);
/*    */   }
/*    */   
/*    */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException, IOException
/*    */   {
/* 45 */     paramHttpServletResponse.setStatus(200);
/* 46 */     paramHttpServletResponse.setContentType("text/html");
/*    */     
/* 48 */     Hashtable localHashtable = new Hashtable();
/* 49 */     for (Enumeration localEnumeration = paramHttpServletRequest.getParameterNames(); localEnumeration.hasMoreElements();)
/*    */     {
/* 51 */       localObject1 = (String)localEnumeration.nextElement();
/* 52 */       localObject2 = paramHttpServletRequest.getParameter((String)localObject1);
/* 53 */       if (localObject1 == null) localObject1 = "-";
/* 54 */       localHashtable.put(localObject1, localObject2);
/*    */     }
/*    */     
/* 57 */     Object localObject1 = paramHttpServletResponse.getWriter();
/* 58 */     Object localObject2 = GenericUtility.getPollAPI();
/* 59 */     if (localObject2 == null)
/*    */     {
/* 61 */       errorPage("In ShutDownPoller Error getting PollAPI: ", paramHttpServletRequest, paramHttpServletResponse);
/* 62 */       return;
/*    */     }
/* 64 */     String str = (String)localHashtable.get("NAME");
/*    */     
/* 66 */     if (str.equals("ALL"))
/*    */     {
/*    */ 
/* 69 */       ((PollAPI)localObject2).shutDownAllPollers();
/* 70 */       errorPage("Operation completed see logs for more details ", paramHttpServletRequest, paramHttpServletResponse);
/* 71 */       return;
/*    */     }
/*    */     
/*    */ 
/* 75 */     ((PollAPI)localObject2).shutDownPoller(str);
/* 76 */     errorPage("Operation completed see logs for more details ", paramHttpServletRequest, paramHttpServletResponse);
/*    */   }
/*    */   
/*    */ 
/*    */   void errorPage(String paramString, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws IOException
/*    */   {
/* 83 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/* 84 */     localPrintWriter.println(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\PollerShutdown.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */