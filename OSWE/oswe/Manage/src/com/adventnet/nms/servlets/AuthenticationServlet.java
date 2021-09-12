/*     */ package com.adventnet.nms.servlets;
/*     */ 
/*     */ import com.adventnet.nms.commonfe.GenericFEAPIImpl;
/*     */ import com.adventnet.nms.util.NmsUtil;
/*     */ import com.adventnet.nms.util.PureServerUtilsFE;
/*     */ import com.adventnet.security.authentication.AuthenticationException;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URLDecoder;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class AuthenticationServlet extends HttpServlet
/*     */ {
/*  24 */   String userName = null;
/*  25 */   String messageDigest = null;
/*  26 */   String password = null;
/*  27 */   boolean passwordExpired = false;
/*     */   
/*  29 */   boolean firstLogin = false;
/*     */   
/*  31 */   int maxSession = 0;
/*     */   
/*     */ 
/*  34 */   boolean bisValidUser = false;
/*     */   
/*     */   public void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  39 */     paramHttpServletResponse.getWriter().println("success");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void service(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException
/*     */   {
/*  49 */     this.userName = null;
/*  50 */     this.password = null;
/*     */     
/*  52 */     this.passwordExpired = false;
/*  53 */     this.firstLogin = false;
/*  54 */     String str1 = "";
/*  55 */     Object localObject = null;
/*  56 */     String str2 = null;
/*  57 */     HttpSession localHttpSession = paramHttpServletRequest.getSession(true);
/*  58 */     PrintWriter localPrintWriter = null;
/*     */     
/*     */ 
/*  61 */     this.bisValidUser = false;
/*     */     
/*  63 */     if (localHttpSession != null)
/*     */     {
/*  65 */       this.userName = ((String)localHttpSession.getAttribute("userName"));
/*     */     }
/*     */     
/*  68 */     if (this.userName != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  74 */       this.bisValidUser = true;
/*     */     }
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
/*  87 */     if (this.userName == null)
/*     */     {
/*  89 */       if ((this.userName = paramHttpServletRequest.getHeader("userName")) == null)
/*     */       {
/*  91 */         this.userName = paramHttpServletRequest.getParameter("userName");
/*     */       }
/*     */     }
/*     */     
/*  95 */     if (paramHttpServletRequest.getHeader("messageDigest") == null)
/*     */     {
/*  97 */       super.service(paramHttpServletRequest, paramHttpServletResponse);
/*  98 */       return;
/*     */     }
/*     */     
/* 101 */     if ((this.messageDigest = paramHttpServletRequest.getHeader("messageDigest")) == null)
/*     */     {
/* 103 */       this.messageDigest = paramHttpServletRequest.getParameter("messageDigest");
/*     */     }
/*     */     
/* 106 */     if (this.messageDigest != null)
/*     */     {
/*     */       try
/*     */       {
/* 110 */         this.messageDigest = URLDecoder.decode(this.messageDigest);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */       
/*     */ 
/* 115 */       str2 = this.messageDigest;
/*     */     }
/*     */     else
/*     */     {
/* 119 */       this.password = ((String)localHttpSession.getAttribute("password"));
/*     */       
/* 121 */       if (this.password == null)
/*     */       {
/* 123 */         if ((this.password = paramHttpServletRequest.getHeader("password")) == null)
/*     */         {
/* 125 */           this.password = paramHttpServletRequest.getParameter("password");
/*     */         }
/*     */       }
/* 128 */       str2 = this.password;
/*     */     }
/*     */     
/*     */ 
/* 132 */     String str3 = paramHttpServletRequest.getParameter("hostaddress");
/* 133 */     Properties localProperties = new Properties();
/*     */     
/* 135 */     if (str3 != null) {
/* 136 */       localProperties.put("hostname", str3);
/*     */     }
/* 138 */     if ((this.userName != null) && (str2 != null)) {
/* 139 */       boolean bool = false;
/*     */       try {
/* 141 */         if (isLimitExceeded())
/*     */         {
/* 143 */           str1 = "Maximum Client Session exceeds, unable to create new session";
/* 144 */           localPrintWriter = paramHttpServletResponse.getWriter();
/* 145 */           localPrintWriter.println(str1);
/* 146 */           localPrintWriter.flush();
/* 147 */           localPrintWriter.close();
/* 148 */           return;
/*     */         }
/* 150 */         if (this.messageDigest != null)
/*     */         {
/* 152 */           bool = PureServerUtilsFE.authenticateUser(this.userName, this.messageDigest, localProperties);
/*     */         }
/*     */         else
/*     */         {
/* 156 */           bool = PureServerUtilsFE.isPasswordCorrect(this.userName, this.password, localProperties);
/*     */         }
/*     */       } catch (AuthenticationException localAuthenticationException) {
/* 159 */         int i = localAuthenticationException.getExceptionType();
/* 160 */         if (i == 0)
/*     */         {
/*     */ 
/*     */ 
/* 164 */           bool = true;
/* 165 */           str1 = "Password Expired";
/*     */           
/*     */ 
/*     */ 
/* 169 */           this.passwordExpired = true;
/*     */         }
/* 171 */         else if (i == 5) {
/* 172 */           bool = true;
/* 173 */           str1 = "First Login";
/* 174 */           this.firstLogin = true;
/* 175 */         } else if (i == 1) {
/* 176 */           str1 = "User Expired";
/* 177 */         } else if (i == 2) {
/* 178 */           str1 = "Login Failed";
/* 179 */         } else if (i == 3) {
/* 180 */           str1 = "User Disabled";
/* 181 */         } else if (i == 4) {
/* 182 */           str1 = "User Forced Out";
/* 183 */         } else if (i == -1) {
/* 184 */           str1 = localAuthenticationException.getMessage();
/*     */         }
/* 186 */         if ((str1 == null) || (str1.trim().equals("")) || (str1.trim().equals("null")))
/*     */         {
/*     */ 
/* 189 */           str1 = "No Message";
/*     */         }
/* 191 */         if ((!this.passwordExpired) && (!this.firstLogin)) {
/* 192 */           localPrintWriter = paramHttpServletResponse.getWriter();
/* 193 */           localPrintWriter.println(str1);
/* 194 */           localPrintWriter.flush();
/* 195 */           localPrintWriter.close();
/* 196 */           return;
/*     */         }
/*     */       }
/* 199 */       if (bool)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */         if (!this.bisValidUser)
/*     */         {
/* 209 */           localHttpSession = paramHttpServletRequest.getSession(true);
/* 210 */           localHttpSession.setAttribute("userName", this.userName);
/* 211 */           if (this.password != null) {
/* 212 */             localHttpSession.setAttribute("password", this.password);
/*     */           }
/*     */         }
/*     */         
/* 216 */         if ((this.passwordExpired) || (this.firstLogin))
/*     */         {
/* 218 */           localPrintWriter = paramHttpServletResponse.getWriter();
/* 219 */           localPrintWriter.println(str1);
/*     */         }
/* 221 */         super.service(paramHttpServletRequest, paramHttpServletResponse);
/*     */       } else {
/* 223 */         localPrintWriter = paramHttpServletResponse.getWriter();
/* 224 */         localPrintWriter.println("unAuthorized");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 229 */       localPrintWriter = paramHttpServletResponse.getWriter();
/* 230 */       localPrintWriter.println("unAuthorized");
/*     */     }
/*     */     
/* 233 */     if (localPrintWriter != null) {
/* 234 */       localPrintWriter.flush();
/* 235 */       localPrintWriter.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getUserName()
/*     */   {
/* 247 */     return this.userName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String getPassword()
/*     */   {
/* 255 */     return this.password;
/*     */   }
/*     */   
/*     */   protected boolean isPasswordExpired() {
/* 259 */     return this.passwordExpired;
/*     */   }
/*     */   
/*     */   public boolean isLimitExceeded()
/*     */   {
/* 264 */     List localList = null;
/*     */     try
/*     */     {
/* 267 */       localList = GenericFEAPIImpl.getAPI().getActualUsers();
/*     */     }
/*     */     catch (RemoteException localRemoteException)
/*     */     {
/* 271 */       return true;
/*     */     }
/*     */     
/* 274 */     if ((this.maxSession > 0) && (localList.size() >= this.maxSession))
/*     */     {
/* 276 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 280 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/* 288 */     super.init();
/* 289 */     String str = NmsUtil.getParameter("MAX_CLIENT_SESSIONS");
/* 290 */     if (str != null)
/*     */     {
/*     */       try
/*     */       {
/* 294 */         this.maxSession = Integer.parseInt(str);
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/* 298 */         this.maxSession = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\nms\servlets\AuthenticationServlet.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */