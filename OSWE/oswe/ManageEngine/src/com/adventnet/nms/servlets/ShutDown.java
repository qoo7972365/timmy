/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.admin.ShutDownException;
/*     */ import com.adventnet.nms.fe.admin.ShutDownAPIFEImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.GenericServlet;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShutDown
/*     */   extends HttpServlet
/*     */   implements Runnable
/*     */ {
/*     */   Properties parameters;
/*     */   
/*     */   public void init(ServletConfig paramServletConfig)
/*     */     throws ServletException
/*     */   {
/*  31 */     super.init(paramServletConfig);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  47 */     doGet(paramHttpServletRequest, paramHttpServletResponse);
/*     */   }
/*     */   
/*  50 */   ShutDownAPIFEImpl api = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  65 */     this.api = ShutDownAPIFEImpl.getInstance();
/*  66 */     this.parameters = new Properties();
/*     */     
/*  68 */     paramHttpServletResponse.setContentType("text/html");
/*  69 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/*     */     String str2;
/*  71 */     for (Enumeration localEnumeration = paramHttpServletRequest.getParameterNames(); localEnumeration.hasMoreElements();)
/*     */     {
/*  73 */       str1 = (String)localEnumeration.nextElement();
/*  74 */       str2 = paramHttpServletRequest.getParameter(str1);
/*  75 */       if (str1 == null) str1 = "-";
/*  76 */       this.parameters.put(str1, str2);
/*     */     }
/*  78 */     String str1 = this.parameters.getProperty("confirm");
/*  79 */     if (str1 == null) {
/*  80 */       localPrintWriter.println("Required parameter(s) missing.");
/*  81 */       return;
/*     */     }
/*  83 */     if (str1.equalsIgnoreCase("Yes"))
/*     */     {
/*  85 */       str2 = paramHttpServletRequest.getHeader("userName");
/*  86 */       String str3 = paramHttpServletRequest.getHeader("password");
/*  87 */       if ((str2 == null) || (str3 == null)) {
/*  88 */         str2 = paramHttpServletRequest.getParameter("userName");
/*  89 */         str3 = paramHttpServletRequest.getParameter("password");
/*     */       }
/*  91 */       if ((str2 == null) || (str3 == null))
/*     */       {
/*     */ 
/*     */ 
/*  95 */         localPrintWriter.println("unAuthorized");
/*  96 */         localPrintWriter.flush();
/*  97 */         localPrintWriter.close();
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 102 */         boolean bool = this.api.shutDownServer(str2, str3, false);
/* 103 */         if (bool)
/*     */         {
/* 105 */           printSuccessMessage(localPrintWriter);
/*     */           
/* 107 */           localObject = new Thread(this);
/* 108 */           ((Thread)localObject).start();
/*     */         }
/*     */         else
/*     */         {
/* 112 */           System.err.println("ShutDown failed");
/* 113 */           localPrintWriter = paramHttpServletResponse.getWriter();
/* 114 */           localPrintWriter.println("unAuthorized");
/* 115 */           localPrintWriter.flush();
/* 116 */           localPrintWriter.close();
/* 117 */           throw new IOException();
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {
/*     */         Object localObject;
/* 122 */         if ((localException instanceof ShutDownException))
/*     */         {
/* 124 */           localObject = (ShutDownException)localException;
/* 125 */           if (((ShutDownException)localObject).getErrorCode() == ShutDownException.AUTHENTICATION_FAILURE)
/*     */           {
/* 127 */             localPrintWriter.println("unAuthenticated");
/*     */           }
/* 129 */           else if (((ShutDownException)localObject).getErrorCode() == ShutDownException.AUTHORIZATION_FAILURE)
/*     */           {
/* 131 */             localPrintWriter.println("unAuthorized");
/*     */           }
/*     */         }
/*     */         
/* 135 */         localPrintWriter.println("Exception while shutting down " + localException);
/* 136 */         localException.printStackTrace();
/* 137 */         localPrintWriter.flush();
/* 138 */         localPrintWriter.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void printSuccessMessage(PrintWriter paramPrintWriter)
/*     */   {
/* 146 */     paramPrintWriter.println("<html>");
/* 147 */     paramPrintWriter.println("<LINK REL=STYLESHEET TYPE=text/css HREF=../template/nmshtmlui.css>");
/* 148 */     paramPrintWriter.println("<body bgColor=white>");
/* 149 */     paramPrintWriter.println("<br><br><br><br>");
/* 150 */     paramPrintWriter.println("<center>");
/* 151 */     paramPrintWriter.println("<font id=cap>The Server has been successfully Shutdown</font>");
/* 152 */     paramPrintWriter.println("</center>");
/* 153 */     paramPrintWriter.println("</body>");
/* 154 */     paramPrintWriter.println("<html>");
/* 155 */     paramPrintWriter.flush();
/* 156 */     paramPrintWriter.close();
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 163 */       Thread.sleep(100L);
/* 164 */       this.api.shutDownApacheServer();
/*     */     }
/*     */     catch (Exception localException) {
/* 167 */       localException.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\ShutDown.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */