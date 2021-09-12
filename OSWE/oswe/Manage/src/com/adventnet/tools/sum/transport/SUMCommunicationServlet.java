/*    */ package com.adventnet.tools.sum.transport;
/*    */ 
/*    */ import com.adventnet.tools.sum.server.session.SUMHttpRequestHandler;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.servlet.http.HttpSession;
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
/*    */ public class SUMCommunicationServlet
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 41 */     doPost(paramHttpServletRequest, paramHttpServletResponse);
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
/*    */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 55 */     HttpSession localHttpSession = paramHttpServletRequest.getSession();
/* 56 */     paramHttpServletResponse.setStatus(200);
/* 57 */     DataOutputStream localDataOutputStream = new DataOutputStream(paramHttpServletResponse.getOutputStream());
/* 58 */     if (localHttpSession == null)
/*    */     {
/* 60 */       localDataOutputStream.writeInt(1000);
/*    */     }
/*    */     else
/*    */     {
/* 64 */       SUMHttpRequestHandler localSUMHttpRequestHandler = (SUMHttpRequestHandler)localHttpSession.getAttribute("requestHandler");
/* 65 */       if (localSUMHttpRequestHandler == null)
/*    */       {
/* 67 */         localDataOutputStream.writeInt(1000);
/*    */       }
/*    */       else
/*    */       {
/* 71 */         byte[] arrayOfByte = localSUMHttpRequestHandler.process(paramHttpServletRequest.getInputStream());
/* 72 */         localDataOutputStream.writeInt(1001);
/* 73 */         localDataOutputStream.flush();
/* 74 */         int i = 0;
/* 75 */         if (arrayOfByte != null)
/*    */         {
/* 77 */           i = arrayOfByte.length;
/*    */         }
/*    */         else
/*    */         {
/* 81 */           arrayOfByte = new byte[0];
/*    */         }
/* 83 */         localDataOutputStream.writeInt(i);
/* 84 */         localDataOutputStream.flush();
/* 85 */         localDataOutputStream.write(arrayOfByte);
/*    */       }
/*    */     }
/* 88 */     localDataOutputStream.flush();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\tools\sum\transport\SUMCommunicationServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */