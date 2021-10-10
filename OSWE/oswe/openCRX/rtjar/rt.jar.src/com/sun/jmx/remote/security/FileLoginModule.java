/*     */ package com.sun.jmx.remote.security;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Util;
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.management.remote.JMXPrincipal;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.auth.login.FailedLoginException;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import javax.security.auth.spi.LoginModule;
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
/*     */ public class FileLoginModule
/*     */   implements LoginModule
/*     */ {
/* 115 */   private static final String DEFAULT_PASSWORD_FILE_NAME = (String)AccessController.<String>doPrivileged(new GetPropertyAction("java.home")) + File.separatorChar + "lib" + File.separatorChar + "management" + File.separatorChar + "jmxremote.password";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String USERNAME_KEY = "javax.security.auth.login.name";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PASSWORD_KEY = "javax.security.auth.login.password";
/*     */ 
/*     */ 
/*     */   
/* 129 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "FileLoginModule");
/*     */ 
/*     */   
/*     */   private boolean useFirstPass = false;
/*     */ 
/*     */   
/*     */   private boolean tryFirstPass = false;
/*     */ 
/*     */   
/*     */   private boolean storePass = false;
/*     */ 
/*     */   
/*     */   private boolean clearPass = false;
/*     */ 
/*     */   
/*     */   private boolean succeeded = false;
/*     */   
/*     */   private boolean commitSucceeded = false;
/*     */   
/*     */   private String username;
/*     */   
/*     */   private char[] password;
/*     */   
/*     */   private JMXPrincipal user;
/*     */   
/*     */   private Subject subject;
/*     */   
/*     */   private CallbackHandler callbackHandler;
/*     */   
/*     */   private Map<String, Object> sharedState;
/*     */   
/*     */   private Map<String, ?> options;
/*     */   
/*     */   private String passwordFile;
/*     */   
/*     */   private String passwordFileDisplayName;
/*     */   
/*     */   private boolean userSuppliedPasswordFile;
/*     */   
/*     */   private boolean hasJavaHomePermission;
/*     */   
/*     */   private Properties userCredentials;
/*     */ 
/*     */   
/*     */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/* 174 */     this.subject = paramSubject;
/* 175 */     this.callbackHandler = paramCallbackHandler;
/* 176 */     this.sharedState = Util.<Map<String, Object>>cast(paramMap1);
/* 177 */     this.options = paramMap2;
/*     */ 
/*     */     
/* 180 */     this
/* 181 */       .tryFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("tryFirstPass"));
/* 182 */     this
/* 183 */       .useFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("useFirstPass"));
/* 184 */     this
/* 185 */       .storePass = "true".equalsIgnoreCase((String)paramMap2.get("storePass"));
/* 186 */     this
/* 187 */       .clearPass = "true".equalsIgnoreCase((String)paramMap2.get("clearPass"));
/*     */     
/* 189 */     this.passwordFile = (String)paramMap2.get("passwordFile");
/* 190 */     this.passwordFileDisplayName = this.passwordFile;
/* 191 */     this.userSuppliedPasswordFile = true;
/*     */ 
/*     */     
/* 194 */     if (this.passwordFile == null) {
/* 195 */       this.passwordFile = DEFAULT_PASSWORD_FILE_NAME;
/* 196 */       this.userSuppliedPasswordFile = false;
/*     */       try {
/* 198 */         System.getProperty("java.home");
/* 199 */         this.hasJavaHomePermission = true;
/* 200 */         this.passwordFileDisplayName = this.passwordFile;
/* 201 */       } catch (SecurityException securityException) {
/* 202 */         this.hasJavaHomePermission = false;
/* 203 */         this.passwordFileDisplayName = "jmxremote.password";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean login() throws LoginException {
/*     */     try {
/* 224 */       loadPasswordFile();
/* 225 */     } catch (IOException iOException) {
/* 226 */       LoginException loginException = new LoginException("Error: unable to load the password file: " + this.passwordFileDisplayName);
/*     */ 
/*     */       
/* 229 */       throw (LoginException)EnvHelp.initCause(loginException, iOException);
/*     */     } 
/*     */     
/* 232 */     if (this.userCredentials == null) {
/* 233 */       throw new LoginException("Error: unable to locate the users' credentials.");
/*     */     }
/*     */ 
/*     */     
/* 237 */     if (logger.debugOn()) {
/* 238 */       logger.debug("login", "Using password file: " + this.passwordFileDisplayName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 243 */     if (this.tryFirstPass) {
/*     */       
/*     */       try
/*     */       {
/*     */         
/* 248 */         attemptAuthentication(true);
/*     */ 
/*     */         
/* 251 */         this.succeeded = true;
/* 252 */         if (logger.debugOn()) {
/* 253 */           logger.debug("login", "Authentication using cached password has succeeded");
/*     */         }
/*     */         
/* 256 */         return true;
/*     */       }
/* 258 */       catch (LoginException loginException)
/*     */       {
/* 260 */         cleanState();
/* 261 */         logger.debug("login", "Authentication using cached password has failed");
/*     */       }
/*     */     
/*     */     }
/* 265 */     else if (this.useFirstPass) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 270 */         attemptAuthentication(true);
/*     */ 
/*     */         
/* 273 */         this.succeeded = true;
/* 274 */         if (logger.debugOn()) {
/* 275 */           logger.debug("login", "Authentication using cached password has succeeded");
/*     */         }
/*     */         
/* 278 */         return true;
/*     */       }
/* 280 */       catch (LoginException loginException) {
/*     */         
/* 282 */         cleanState();
/* 283 */         logger.debug("login", "Authentication using cached password has failed");
/*     */ 
/*     */         
/* 286 */         throw loginException;
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     if (logger.debugOn()) {
/* 291 */       logger.debug("login", "Acquiring password");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 296 */       attemptAuthentication(false);
/*     */ 
/*     */       
/* 299 */       this.succeeded = true;
/* 300 */       if (logger.debugOn()) {
/* 301 */         logger.debug("login", "Authentication has succeeded");
/*     */       }
/* 303 */       return true;
/*     */     }
/* 305 */     catch (LoginException loginException) {
/* 306 */       cleanState();
/* 307 */       logger.debug("login", "Authentication has failed");
/*     */       
/* 309 */       throw loginException;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean commit() throws LoginException {
/* 335 */     if (!this.succeeded) {
/* 336 */       return false;
/*     */     }
/* 338 */     if (this.subject.isReadOnly()) {
/* 339 */       cleanState();
/* 340 */       throw new LoginException("Subject is read-only");
/*     */     } 
/*     */     
/* 343 */     if (!this.subject.getPrincipals().contains(this.user)) {
/* 344 */       this.subject.getPrincipals().add(this.user);
/*     */     }
/*     */     
/* 347 */     if (logger.debugOn()) {
/* 348 */       logger.debug("commit", "Authentication has completed successfully");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 353 */     cleanState();
/* 354 */     this.commitSucceeded = true;
/* 355 */     return true;
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
/*     */   public boolean abort() throws LoginException {
/* 376 */     if (logger.debugOn()) {
/* 377 */       logger.debug("abort", "Authentication has not completed successfully");
/*     */     }
/*     */ 
/*     */     
/* 381 */     if (!this.succeeded)
/* 382 */       return false; 
/* 383 */     if (this.succeeded == true && !this.commitSucceeded) {
/*     */ 
/*     */       
/* 386 */       this.succeeded = false;
/* 387 */       cleanState();
/* 388 */       this.user = null;
/*     */     }
/*     */     else {
/*     */       
/* 392 */       logout();
/*     */     } 
/* 394 */     return true;
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
/*     */   public boolean logout() throws LoginException {
/* 408 */     if (this.subject.isReadOnly()) {
/* 409 */       cleanState();
/* 410 */       throw new LoginException("Subject is read-only");
/*     */     } 
/* 412 */     this.subject.getPrincipals().remove(this.user);
/*     */ 
/*     */     
/* 415 */     cleanState();
/* 416 */     this.succeeded = false;
/* 417 */     this.commitSucceeded = false;
/* 418 */     this.user = null;
/*     */     
/* 420 */     if (logger.debugOn()) {
/* 421 */       logger.debug("logout", "Subject is being logged out");
/*     */     }
/*     */     
/* 424 */     return true;
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
/*     */   private void attemptAuthentication(boolean paramBoolean) throws LoginException {
/* 438 */     getUsernamePassword(paramBoolean);
/*     */ 
/*     */     
/*     */     String str;
/*     */     
/* 443 */     if ((str = this.userCredentials.getProperty(this.username)) == null || 
/* 444 */       !str.equals(new String(this.password))) {
/*     */ 
/*     */       
/* 447 */       if (logger.debugOn()) {
/* 448 */         logger.debug("login", "Invalid username or password");
/*     */       }
/* 450 */       throw new FailedLoginException("Invalid username or password");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (this.storePass && 
/* 456 */       !this.sharedState.containsKey("javax.security.auth.login.name") && 
/* 457 */       !this.sharedState.containsKey("javax.security.auth.login.password")) {
/* 458 */       this.sharedState.put("javax.security.auth.login.name", this.username);
/* 459 */       this.sharedState.put("javax.security.auth.login.password", this.password);
/*     */     } 
/*     */ 
/*     */     
/* 463 */     this.user = new JMXPrincipal(this.username);
/*     */     
/* 465 */     if (logger.debugOn()) {
/* 466 */       logger.debug("login", "User '" + this.username + "' successfully validated");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadPasswordFile() throws IOException {
/*     */     FileInputStream fileInputStream;
/*     */     try {
/* 477 */       fileInputStream = new FileInputStream(this.passwordFile);
/* 478 */     } catch (SecurityException securityException) {
/* 479 */       if (this.userSuppliedPasswordFile || this.hasJavaHomePermission) {
/* 480 */         throw securityException;
/*     */       }
/* 482 */       FilePermission filePermission = new FilePermission(this.passwordFileDisplayName, "read");
/*     */ 
/*     */       
/* 485 */       AccessControlException accessControlException = new AccessControlException("access denied " + filePermission.toString());
/* 486 */       accessControlException.setStackTrace(securityException.getStackTrace());
/* 487 */       throw accessControlException;
/*     */     } 
/*     */     
/*     */     try {
/* 491 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
/*     */       try {
/* 493 */         this.userCredentials = new Properties();
/* 494 */         this.userCredentials.load(bufferedInputStream);
/*     */       } finally {
/* 496 */         bufferedInputStream.close();
/*     */       } 
/*     */     } finally {
/* 499 */       fileInputStream.close();
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
/*     */   private void getUsernamePassword(boolean paramBoolean) throws LoginException {
/* 518 */     if (paramBoolean) {
/*     */       
/* 520 */       this.username = (String)this.sharedState.get("javax.security.auth.login.name");
/* 521 */       this.password = (char[])this.sharedState.get("javax.security.auth.login.password");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 526 */     if (this.callbackHandler == null) {
/* 527 */       throw new LoginException("Error: no CallbackHandler available to garner authentication information from the user");
/*     */     }
/*     */     
/* 530 */     Callback[] arrayOfCallback = new Callback[2];
/* 531 */     arrayOfCallback[0] = new NameCallback("username");
/* 532 */     arrayOfCallback[1] = new PasswordCallback("password", false);
/*     */     
/*     */     try {
/* 535 */       this.callbackHandler.handle(arrayOfCallback);
/* 536 */       this.username = ((NameCallback)arrayOfCallback[0]).getName();
/* 537 */       char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[1]).getPassword();
/* 538 */       this.password = new char[arrayOfChar.length];
/* 539 */       System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
/*     */       
/* 541 */       ((PasswordCallback)arrayOfCallback[1]).clearPassword();
/*     */     }
/* 543 */     catch (IOException iOException) {
/* 544 */       LoginException loginException = new LoginException(iOException.toString());
/* 545 */       throw (LoginException)EnvHelp.initCause(loginException, iOException);
/* 546 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/*     */       
/* 548 */       LoginException loginException = new LoginException("Error: " + unsupportedCallbackException.getCallback().toString() + " not available to garner authentication information from the user");
/*     */ 
/*     */       
/* 551 */       throw (LoginException)EnvHelp.initCause(loginException, unsupportedCallbackException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanState() {
/* 559 */     this.username = null;
/* 560 */     if (this.password != null) {
/* 561 */       Arrays.fill(this.password, ' ');
/* 562 */       this.password = null;
/*     */     } 
/*     */     
/* 565 */     if (this.clearPass) {
/* 566 */       this.sharedState.remove("javax.security.auth.login.name");
/* 567 */       this.sharedState.remove("javax.security.auth.login.password");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/security/FileLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */