/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.authentication.UserConfigAPI;
/*     */ import com.adventnet.nms.authentication.UserConfigException;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.security.AuthUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLDecoder;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ public class ChangePasswordServlet
/*     */   extends HttpServlet
/*     */ {
/*  23 */   private String userName = null;
/*     */   
/*  25 */   private String newPassword = null;
/*  26 */   private int passwordAge = 0;
/*  27 */   private boolean result = false;
/*  28 */   private boolean resetPassword = false;
/*  29 */   UserConfigAPI userConfigAPI = (UserConfigAPI)NmsUtil.getAPI("UserConfigAPI");
/*     */   
/*     */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  34 */     this.userName = null;
/*     */     
/*  36 */     this.newPassword = null;
/*  37 */     String str = null;
/*  38 */     this.passwordAge = 0;
/*  39 */     this.result = false;
/*  40 */     this.resetPassword = false;
/*     */     
/*  42 */     PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
/*     */     
/*     */ 
/*  45 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/*     */     
/*  47 */     if (localHttpSession != null) {
/*  48 */       if (localHttpSession.getAttribute("userName") == null) {
/*  49 */         localPrintWriter.println("unAthorized");
/*  50 */         localPrintWriter.flush();
/*  51 */         localPrintWriter.close();
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     else {
/*  57 */       localPrintWriter.println("session is null");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  62 */     this.userName = paramHttpServletRequest.getHeader("userName");
/*  63 */     if (this.userName == null) {
/*  64 */       this.userName = paramHttpServletRequest.getParameter("userName");
/*     */     }
/*     */     
/*     */ 
/*  68 */     if (this.userName == null) {
/*  69 */       localPrintWriter.println("Required Parameter(s) missing");
/*  70 */       return;
/*     */     }
/*     */     
/*  73 */     this.resetPassword = new Boolean(paramHttpServletRequest.getHeader("resetPassword")).booleanValue();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */     if (!this.resetPassword)
/*     */     {
/*     */ 
/*     */ 
/*  83 */       this.newPassword = paramHttpServletRequest.getHeader("newPassword");
/*  84 */       if (this.newPassword == null) {
/*  85 */         this.newPassword = paramHttpServletRequest.getParameter("newPassword");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  90 */       str = paramHttpServletRequest.getHeader("passwordAge");
/*  91 */       if (str == null) {
/*  92 */         str = paramHttpServletRequest.getParameter("passwordAge");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  98 */       if ((this.newPassword == null) || (str == null)) {
/*  99 */         localPrintWriter.println("Required Parameter(s) missing");
/* 100 */         localPrintWriter.flush();
/* 101 */         localPrintWriter.close();
/* 102 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 108 */       if (this.resetPassword)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */         this.result = this.userConfigAPI.changePassword(this.userName, "", "");
/*     */       }
/*     */       else
/*     */       {
/* 118 */         this.newPassword = URLDecoder.decode(this.newPassword);
/*     */         
/* 120 */         this.result = this.userConfigAPI.changePassword(this.userName, "", AuthUtil.decryptString(this.newPassword, "WqrTnvA"));
/* 121 */         if (this.result)
/*     */         {
/*     */ 
/* 124 */           localHttpSession.setAttribute("password", AuthUtil.decryptString(this.newPassword, "WqrTnvA"));
/*     */           
/*     */ 
/* 127 */           if ((str != null) && (!str.equals("")))
/*     */           {
/* 129 */             this.passwordAge = Integer.parseInt(str);
/* 130 */             this.userConfigAPI.setPasswordAge(this.userName, this.passwordAge);
/*     */           }
/*     */         }
/*     */       }
/* 134 */       if (!this.result)
/*     */       {
/*     */ 
/* 137 */         localPrintWriter.println("failure");
/* 138 */         localPrintWriter.flush();
/* 139 */         localPrintWriter.close();
/* 140 */         return;
/*     */       }
/*     */     }
/*     */     catch (RemoteException localRemoteException) {
/* 144 */       localPrintWriter.println("failure");
/* 145 */       localPrintWriter.flush();
/* 146 */       localPrintWriter.close();
/* 147 */       return;
/*     */     }
/*     */     catch (UserConfigException localUserConfigException) {
/* 150 */       localPrintWriter.println("failure");
/* 151 */       localPrintWriter.flush();
/* 152 */       localPrintWriter.close();
/* 153 */       return;
/*     */     }
/* 155 */     localPrintWriter.println("success");
/* 156 */     localPrintWriter.flush();
/* 157 */     localPrintWriter.close();
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\ChangePasswordServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */