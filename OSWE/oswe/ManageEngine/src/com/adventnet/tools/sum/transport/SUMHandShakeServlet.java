/*    */ package com.adventnet.tools.sum.transport;
/*    */ 
/*    */ import com.adventnet.tools.sum.common.util.SUMParser;
/*    */ import com.adventnet.tools.sum.server.session.SUMHttpRequestHandler;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SUMHandShakeServlet
/*    */   extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 46 */     doPost(paramHttpServletRequest, paramHttpServletResponse);
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
/*    */ 
/*    */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 63 */     ObjectInputStream localObjectInputStream = new ObjectInputStream(paramHttpServletRequest.getInputStream());
/* 64 */     int i = localObjectInputStream.readInt();
/* 65 */     HttpSession localHttpSession; if (i == 1002)
/*    */     {
/* 67 */       localHttpSession = paramHttpServletRequest.getSession(true);
/* 68 */       SUMHttpRequestHandler localSUMHttpRequestHandler = new SUMHttpRequestHandler(paramHttpServletRequest.getRemoteHost());
/* 69 */       localHttpSession.setAttribute("requestHandler", localSUMHttpRequestHandler);
/* 70 */       localHttpSession.setMaxInactiveInterval(300);
/* 71 */       paramHttpServletResponse.setStatus(200);
/* 72 */       ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(paramHttpServletResponse.getOutputStream());
/* 73 */       boolean bool = SUMParser.getInstance().isSmartUpdateForServer();
/* 74 */       if (!bool)
/*    */       {
/* 76 */         localObjectOutputStream.writeObject("Connection failure");
/*    */       }
/*    */       else
/*    */       {
/* 80 */         localObjectOutputStream.writeObject("Successfully Connected");
/*    */       }
/* 82 */       localObjectOutputStream.flush();
/* 83 */       localObjectOutputStream.writeObject(localHttpSession.getId());
/* 84 */       localObjectOutputStream.flush();
/*    */     }
/* 86 */     else if (i == 1003)
/*    */     {
/* 88 */       localHttpSession = paramHttpServletRequest.getSession();
/* 89 */       if (localHttpSession != null)
/*    */       {
/* 91 */         localHttpSession.invalidate();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\tools\sum\transport\SUMHandShakeServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */