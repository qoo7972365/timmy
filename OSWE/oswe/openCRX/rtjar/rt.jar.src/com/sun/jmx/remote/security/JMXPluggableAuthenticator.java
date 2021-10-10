/*     */ package com.sun.jmx.remote.security;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.management.remote.JMXAuthenticator;
/*     */ import javax.security.auth.AuthPermission;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.auth.login.AppConfigurationEntry;
/*     */ import javax.security.auth.login.Configuration;
/*     */ import javax.security.auth.login.LoginContext;
/*     */ import javax.security.auth.login.LoginException;
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
/*     */ 
/*     */ public final class JMXPluggableAuthenticator
/*     */   implements JMXAuthenticator
/*     */ {
/*     */   private LoginContext loginContext;
/*     */   private String username;
/*     */   private String password;
/*     */   private static final String LOGIN_CONFIG_PROP = "jmx.remote.x.login.config";
/*     */   private static final String LOGIN_CONFIG_NAME = "JMXPluggableAuthenticator";
/*     */   private static final String PASSWORD_FILE_PROP = "jmx.remote.x.password.file";
/*     */   
/*     */   public JMXPluggableAuthenticator(Map<?, ?> paramMap) {
/*  92 */     String str1 = null;
/*  93 */     String str2 = null;
/*     */     
/*  95 */     if (paramMap != null) {
/*  96 */       str1 = (String)paramMap.get("jmx.remote.x.login.config");
/*  97 */       str2 = (String)paramMap.get("jmx.remote.x.password.file");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 102 */       if (str1 != null) {
/*     */         
/* 104 */         this.loginContext = new LoginContext(str1, new JMXCallbackHandler());
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 109 */         SecurityManager securityManager = System.getSecurityManager();
/* 110 */         if (securityManager != null) {
/* 111 */           securityManager.checkPermission(new AuthPermission("createLoginContext.JMXPluggableAuthenticator"));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 116 */         final String pf = str2;
/*     */         try {
/* 118 */           this.loginContext = AccessController.<LoginContext>doPrivileged(new PrivilegedExceptionAction<LoginContext>()
/*     */               {
/*     */                 public LoginContext run() throws LoginException {
/* 121 */                   return new LoginContext("JMXPluggableAuthenticator", null, new JMXPluggableAuthenticator.JMXCallbackHandler(), new JMXPluggableAuthenticator.FileLoginConfig(pf));
/*     */                 }
/*     */               });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 128 */         catch (PrivilegedActionException privilegedActionException) {
/* 129 */           throw (LoginException)privilegedActionException.getException();
/*     */         }
/*     */       
/*     */       } 
/* 133 */     } catch (LoginException loginException) {
/* 134 */       authenticationFailure("authenticate", loginException);
/*     */     }
/* 136 */     catch (SecurityException securityException) {
/* 137 */       authenticationFailure("authenticate", securityException);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject authenticate(Object paramObject) {
/* 160 */     if (!(paramObject instanceof String[])) {
/*     */       
/* 162 */       if (paramObject == null) {
/* 163 */         authenticationFailure("authenticate", "Credentials required");
/*     */       }
/*     */ 
/*     */       
/* 167 */       String str = "Credentials should be String[] instead of " + paramObject.getClass().getName();
/* 168 */       authenticationFailure("authenticate", str);
/*     */     } 
/*     */ 
/*     */     
/* 172 */     String[] arrayOfString = (String[])paramObject;
/* 173 */     if (arrayOfString.length != 2) {
/* 174 */       String str = "Credentials should have 2 elements not " + arrayOfString.length;
/*     */ 
/*     */       
/* 177 */       authenticationFailure("authenticate", str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 182 */     this.username = arrayOfString[0];
/* 183 */     this.password = arrayOfString[1];
/* 184 */     if (this.username == null || this.password == null)
/*     */     {
/* 186 */       authenticationFailure("authenticate", "Username or password is null");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 191 */       this.loginContext.login();
/* 192 */       final Subject subject = this.loginContext.getSubject();
/* 193 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 195 */               subject.setReadOnly();
/* 196 */               return null;
/*     */             }
/*     */           });
/*     */       
/* 200 */       return subject;
/*     */     }
/* 202 */     catch (LoginException loginException) {
/* 203 */       authenticationFailure("authenticate", loginException);
/*     */       
/* 205 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void authenticationFailure(String paramString1, String paramString2) throws SecurityException {
/* 210 */     String str = "Authentication failed! " + paramString2;
/* 211 */     SecurityException securityException = new SecurityException(str);
/* 212 */     logException(paramString1, str, securityException);
/* 213 */     throw securityException;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void authenticationFailure(String paramString, Exception paramException) throws SecurityException {
/*     */     String str;
/*     */     SecurityException securityException;
/* 221 */     if (paramException instanceof SecurityException) {
/* 222 */       str = paramException.getMessage();
/* 223 */       securityException = (SecurityException)paramException;
/*     */     } else {
/* 225 */       str = "Authentication failed! " + paramException.getMessage();
/* 226 */       SecurityException securityException1 = new SecurityException(str);
/* 227 */       EnvHelp.initCause(securityException1, paramException);
/* 228 */       securityException = securityException1;
/*     */     } 
/* 230 */     logException(paramString, str, securityException);
/* 231 */     throw securityException;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void logException(String paramString1, String paramString2, Exception paramException) {
/* 237 */     if (logger.traceOn()) {
/* 238 */       logger.trace(paramString1, paramString2);
/*     */     }
/* 240 */     if (logger.debugOn()) {
/* 241 */       logger.debug(paramString1, paramException);
/*     */     }
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
/* 253 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "JMXPluggableAuthenticator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class JMXCallbackHandler
/*     */     implements CallbackHandler
/*     */   {
/*     */     private JMXCallbackHandler() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(Callback[] param1ArrayOfCallback) throws IOException, UnsupportedCallbackException {
/* 270 */       for (byte b = 0; b < param1ArrayOfCallback.length; b++) {
/* 271 */         if (param1ArrayOfCallback[b] instanceof NameCallback) {
/* 272 */           ((NameCallback)param1ArrayOfCallback[b]).setName(JMXPluggableAuthenticator.this.username);
/*     */         }
/* 274 */         else if (param1ArrayOfCallback[b] instanceof PasswordCallback) {
/* 275 */           ((PasswordCallback)param1ArrayOfCallback[b])
/* 276 */             .setPassword(JMXPluggableAuthenticator.this.password.toCharArray());
/*     */         } else {
/*     */           
/* 279 */           throw new UnsupportedCallbackException(param1ArrayOfCallback[b], "Unrecognized Callback");
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FileLoginConfig
/*     */     extends Configuration
/*     */   {
/*     */     private AppConfigurationEntry[] entries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     private static final String FILE_LOGIN_MODULE = FileLoginModule.class
/* 302 */       .getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String PASSWORD_FILE_OPTION = "passwordFile";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FileLoginConfig(String param1String) {
/*     */       Map<?, ?> map;
/* 316 */       if (param1String != null) {
/* 317 */         map = new HashMap<>(1);
/* 318 */         map.put("passwordFile", param1String);
/*     */       } else {
/* 320 */         map = Collections.emptyMap();
/*     */       } 
/*     */       
/* 323 */       this.entries = new AppConfigurationEntry[] { new AppConfigurationEntry(FILE_LOGIN_MODULE, AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, (Map)map) };
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
/*     */     public AppConfigurationEntry[] getAppConfigurationEntry(String param1String) {
/* 335 */       return param1String.equals("JMXPluggableAuthenticator") ? this.entries : null;
/*     */     }
/*     */     
/*     */     public void refresh() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/JMXPluggableAuthenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */