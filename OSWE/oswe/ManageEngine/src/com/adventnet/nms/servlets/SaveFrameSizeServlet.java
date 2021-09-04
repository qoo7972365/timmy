/*    */ package com.adventnet.nms.servlets;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class SaveFrameSizeServlet extends javax.servlet.http.HttpServlet
/*    */ {
/* 19 */   String userName = null;
/*    */   
/*    */   public String getServletInfo() {
/* 22 */     return "This Servlet saves the internal frame size and locations";
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
/* 36 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*    */   }
/*    */   
/*    */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*    */     throws ServletException, IOException
/*    */   {
/* 42 */     PrintWriter localPrintWriter = new PrintWriter(paramHttpServletResponse.getOutputStream());
/* 43 */     paramHttpServletResponse.setContentType("text/html");
/*    */     
/* 45 */     Properties localProperties = new Properties();
/*    */     
/* 47 */     for (Enumeration localEnumeration = paramHttpServletRequest.getParameterNames(); localEnumeration.hasMoreElements();)
/*    */     {
/* 49 */       localObject1 = (String)localEnumeration.nextElement();
/* 50 */       localObject2 = paramHttpServletRequest.getParameter((String)localObject1);
/* 51 */       if (((String)localObject1).equalsIgnoreCase("UserName"))
/*    */       {
/* 53 */         this.userName = ((String)localObject2);
/*    */       }
/*    */       else {
/* 56 */         if (localObject1 == null) localObject1 = "-";
/* 57 */         if (localProperties.containsKey(localObject1))
/* 58 */           localProperties.remove(localObject1);
/* 59 */         localProperties.put(localObject1, localObject2);
/*    */       }
/*    */     }
/* 62 */     Object localObject1 = new Properties();
/*    */     
/*    */ 
/*    */ 
/* 66 */     Object localObject2 = new File(com.adventnet.nms.util.PureUtils.usersDir + "users/" + this.userName + "/FramesInfo.conf");
/*    */     
/* 68 */     if (((File)localObject2).exists())
/*    */     {
/*    */ 
/*    */ 
/* 72 */       localObject3 = com.adventnet.nms.util.CommonUtil.openFile((File)localObject2);
/* 73 */       ((Properties)localObject1).load((InputStream)localObject3);
/* 74 */       ((InputStream)localObject3).close();
/*    */     }
/* 76 */     for (Object localObject3 = localProperties.keys(); ((Enumeration)localObject3).hasMoreElements();)
/*    */     {
/* 78 */       localObject4 = (String)((Enumeration)localObject3).nextElement();
/* 79 */       String str = (String)localProperties.get(localObject4);
/* 80 */       if (((Hashtable)localObject1).containsKey(localObject4))
/* 81 */         ((Hashtable)localObject1).remove(localObject4);
/* 82 */       ((Hashtable)localObject1).put(localObject4, str);
/*    */     }
/* 84 */     Object localObject4 = new FileOutputStream((File)localObject2);
/*    */     try {
/* 86 */       ((Properties)localObject1).store((java.io.OutputStream)localObject4, "Size and Location of Internal Frames");
/*    */     } catch (IOException localIOException) {
/* 88 */       throw localIOException;
/*    */     }
/* 90 */     localPrintWriter.flush();
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\SaveFrameSizeServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */