/*     */ package com.sun.security.auth.module;
/*     */ 
/*     */ import com.sun.security.auth.UnixNumericGroupPrincipal;
/*     */ import com.sun.security.auth.UnixNumericUserPrincipal;
/*     */ import com.sun.security.auth.UnixPrincipal;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.login.FailedLoginException;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import javax.security.auth.spi.LoginModule;
/*     */ import jdk.Exported;
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
/*     */ @Exported
/*     */ public class UnixLoginModule
/*     */   implements LoginModule
/*     */ {
/*     */   private Subject subject;
/*     */   private CallbackHandler callbackHandler;
/*     */   private Map<String, ?> sharedState;
/*     */   private Map<String, ?> options;
/*     */   private boolean debug = true;
/*     */   private UnixSystem ss;
/*     */   private boolean succeeded = false;
/*     */   private boolean commitSucceeded = false;
/*     */   private UnixPrincipal userPrincipal;
/*     */   private UnixNumericUserPrincipal UIDPrincipal;
/*     */   private UnixNumericGroupPrincipal GIDPrincipal;
/*  73 */   private LinkedList<UnixNumericGroupPrincipal> supplementaryGroups = new LinkedList<>();
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
/*     */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/*  97 */     this.subject = paramSubject;
/*  98 */     this.callbackHandler = paramCallbackHandler;
/*  99 */     this.sharedState = paramMap1;
/* 100 */     this.options = paramMap2;
/*     */ 
/*     */     
/* 103 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
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
/*     */   public boolean login() throws LoginException {
/* 123 */     long[] arrayOfLong = null;
/*     */     
/* 125 */     this.ss = new UnixSystem();
/*     */     
/* 127 */     if (this.ss == null) {
/* 128 */       this.succeeded = false;
/* 129 */       throw new FailedLoginException("Failed in attempt to import the underlying system identity information");
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.userPrincipal = new UnixPrincipal(this.ss.getUsername());
/* 134 */     this.UIDPrincipal = new UnixNumericUserPrincipal(this.ss.getUid());
/* 135 */     this.GIDPrincipal = new UnixNumericGroupPrincipal(this.ss.getGid(), true);
/* 136 */     if (this.ss.getGroups() != null && (this.ss.getGroups()).length > 0) {
/* 137 */       arrayOfLong = this.ss.getGroups();
/* 138 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 139 */         UnixNumericGroupPrincipal unixNumericGroupPrincipal = new UnixNumericGroupPrincipal(arrayOfLong[b], false);
/*     */ 
/*     */         
/* 142 */         if (!unixNumericGroupPrincipal.getName().equals(this.GIDPrincipal.getName()))
/* 143 */           this.supplementaryGroups.add(unixNumericGroupPrincipal); 
/*     */       } 
/*     */     } 
/* 146 */     if (this.debug) {
/* 147 */       System.out.println("\t\t[UnixLoginModule]: succeeded importing info: ");
/*     */       
/* 149 */       System.out.println("\t\t\tuid = " + this.ss.getUid());
/* 150 */       System.out.println("\t\t\tgid = " + this.ss.getGid());
/* 151 */       arrayOfLong = this.ss.getGroups();
/* 152 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 153 */         System.out.println("\t\t\tsupp gid = " + arrayOfLong[b]);
/*     */       }
/*     */     } 
/* 156 */     this.succeeded = true;
/* 157 */     return true;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean commit() throws LoginException {
/* 185 */     if (!this.succeeded) {
/* 186 */       if (this.debug) {
/* 187 */         System.out.println("\t\t[UnixLoginModule]: did not add any Principals to Subject because own authentication failed.");
/*     */       }
/*     */ 
/*     */       
/* 191 */       return false;
/*     */     } 
/* 193 */     if (this.subject.isReadOnly()) {
/* 194 */       throw new LoginException("commit Failed: Subject is Readonly");
/*     */     }
/*     */     
/* 197 */     if (!this.subject.getPrincipals().contains(this.userPrincipal))
/* 198 */       this.subject.getPrincipals().add(this.userPrincipal); 
/* 199 */     if (!this.subject.getPrincipals().contains(this.UIDPrincipal))
/* 200 */       this.subject.getPrincipals().add(this.UIDPrincipal); 
/* 201 */     if (!this.subject.getPrincipals().contains(this.GIDPrincipal))
/* 202 */       this.subject.getPrincipals().add(this.GIDPrincipal); 
/* 203 */     for (byte b = 0; b < this.supplementaryGroups.size(); b++) {
/*     */       
/* 205 */       if (!this.subject.getPrincipals().contains(this.supplementaryGroups.get(b))) {
/* 206 */         this.subject.getPrincipals().add(this.supplementaryGroups.get(b));
/*     */       }
/*     */     } 
/* 209 */     if (this.debug) {
/* 210 */       System.out.println("\t\t[UnixLoginModule]: added UnixPrincipal,");
/*     */       
/* 212 */       System.out.println("\t\t\t\tUnixNumericUserPrincipal,");
/* 213 */       System.out.println("\t\t\t\tUnixNumericGroupPrincipal(s),");
/* 214 */       System.out.println("\t\t\t to Subject");
/*     */     } 
/*     */     
/* 217 */     this.commitSucceeded = true;
/* 218 */     return true;
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
/*     */   public boolean abort() throws LoginException {
/* 242 */     if (this.debug) {
/* 243 */       System.out.println("\t\t[UnixLoginModule]: aborted authentication attempt");
/*     */     }
/*     */ 
/*     */     
/* 247 */     if (!this.succeeded)
/* 248 */       return false; 
/* 249 */     if (this.succeeded == true && !this.commitSucceeded) {
/*     */ 
/*     */       
/* 252 */       this.succeeded = false;
/* 253 */       this.ss = null;
/* 254 */       this.userPrincipal = null;
/* 255 */       this.UIDPrincipal = null;
/* 256 */       this.GIDPrincipal = null;
/* 257 */       this.supplementaryGroups = new LinkedList<>();
/*     */     }
/*     */     else {
/*     */       
/* 261 */       logout();
/*     */     } 
/* 263 */     return true;
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
/*     */   public boolean logout() throws LoginException {
/* 281 */     if (this.subject.isReadOnly()) {
/* 282 */       throw new LoginException("logout Failed: Subject is Readonly");
/*     */     }
/*     */ 
/*     */     
/* 286 */     this.subject.getPrincipals().remove(this.userPrincipal);
/* 287 */     this.subject.getPrincipals().remove(this.UIDPrincipal);
/* 288 */     this.subject.getPrincipals().remove(this.GIDPrincipal);
/* 289 */     for (byte b = 0; b < this.supplementaryGroups.size(); b++) {
/* 290 */       this.subject.getPrincipals().remove(this.supplementaryGroups.get(b));
/*     */     }
/*     */ 
/*     */     
/* 294 */     this.ss = null;
/* 295 */     this.succeeded = false;
/* 296 */     this.commitSucceeded = false;
/* 297 */     this.userPrincipal = null;
/* 298 */     this.UIDPrincipal = null;
/* 299 */     this.GIDPrincipal = null;
/* 300 */     this.supplementaryGroups = new LinkedList<>();
/*     */     
/* 302 */     if (this.debug) {
/* 303 */       System.out.println("\t\t[UnixLoginModule]: logged out Subject");
/*     */     }
/*     */     
/* 306 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/UnixLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */