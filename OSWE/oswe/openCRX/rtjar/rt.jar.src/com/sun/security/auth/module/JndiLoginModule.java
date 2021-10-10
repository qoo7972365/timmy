/*     */ package com.sun.security.auth.module;
/*     */ 
/*     */ import com.sun.security.auth.UnixNumericGroupPrincipal;
/*     */ import com.sun.security.auth.UnixNumericUserPrincipal;
/*     */ import com.sun.security.auth.UnixPrincipal;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.Attribute;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.BasicAttributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.directory.SearchResult;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class JndiLoginModule
/*     */   implements LoginModule
/*     */ {
/* 157 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*     */       {
/*     */         public ResourceBundle run() {
/* 160 */           return ResourceBundle.getBundle("sun.security.util.AuthResources");
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public final String USER_PROVIDER = "user.provider.url";
/* 168 */   public final String GROUP_PROVIDER = "group.provider.url";
/*     */   
/*     */   private boolean debug = false;
/*     */   
/*     */   private boolean strongDebug = false;
/*     */   
/*     */   private String userProvider;
/*     */   
/*     */   private String groupProvider;
/*     */   
/*     */   private boolean useFirstPass = false;
/*     */   
/*     */   private boolean tryFirstPass = false;
/*     */   
/*     */   private boolean storePass = false;
/*     */   
/*     */   private boolean clearPass = false;
/*     */   private boolean succeeded = false;
/*     */   private boolean commitSucceeded = false;
/*     */   private String username;
/*     */   private char[] password;
/*     */   DirContext ctx;
/*     */   private UnixPrincipal userPrincipal;
/*     */   private UnixNumericUserPrincipal UIDPrincipal;
/*     */   private UnixNumericGroupPrincipal GIDPrincipal;
/* 193 */   private LinkedList<UnixNumericGroupPrincipal> supplementaryGroups = new LinkedList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private Subject subject;
/*     */ 
/*     */ 
/*     */   
/*     */   private CallbackHandler callbackHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Object> sharedState;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, ?> options;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CRYPT = "{crypt}";
/*     */ 
/*     */   
/*     */   private static final String USER_PWD = "userPassword";
/*     */ 
/*     */   
/*     */   private static final String USER_UID = "uidNumber";
/*     */ 
/*     */   
/*     */   private static final String USER_GID = "gidNumber";
/*     */ 
/*     */   
/*     */   private static final String GROUP_ID = "gidNumber";
/*     */ 
/*     */   
/*     */   private static final String NAME = "javax.security.auth.login.name";
/*     */ 
/*     */   
/*     */   private static final String PWD = "javax.security.auth.login.password";
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/* 236 */     this.subject = paramSubject;
/* 237 */     this.callbackHandler = paramCallbackHandler;
/* 238 */     this.sharedState = (Map)paramMap1;
/* 239 */     this.options = paramMap2;
/*     */ 
/*     */     
/* 242 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
/* 243 */     this
/* 244 */       .strongDebug = "true".equalsIgnoreCase((String)paramMap2.get("strongDebug"));
/* 245 */     this.userProvider = (String)paramMap2.get("user.provider.url");
/* 246 */     this.groupProvider = (String)paramMap2.get("group.provider.url");
/* 247 */     this
/* 248 */       .tryFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("tryFirstPass"));
/* 249 */     this
/* 250 */       .useFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("useFirstPass"));
/* 251 */     this
/* 252 */       .storePass = "true".equalsIgnoreCase((String)paramMap2.get("storePass"));
/* 253 */     this
/* 254 */       .clearPass = "true".equalsIgnoreCase((String)paramMap2.get("clearPass"));
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
/*     */   public boolean login() throws LoginException {
/* 273 */     if (this.userProvider == null) {
/* 274 */       throw new LoginException("Error: Unable to locate JNDI user provider");
/*     */     }
/*     */     
/* 277 */     if (this.groupProvider == null) {
/* 278 */       throw new LoginException("Error: Unable to locate JNDI group provider");
/*     */     }
/*     */ 
/*     */     
/* 282 */     if (this.debug) {
/* 283 */       System.out.println("\t\t[JndiLoginModule] user provider: " + this.userProvider);
/*     */       
/* 285 */       System.out.println("\t\t[JndiLoginModule] group provider: " + this.groupProvider);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (this.tryFirstPass) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 295 */         attemptAuthentication(true);
/*     */ 
/*     */         
/* 298 */         this.succeeded = true;
/* 299 */         if (this.debug) {
/* 300 */           System.out.println("\t\t[JndiLoginModule] tryFirstPass succeeded");
/*     */         }
/*     */         
/* 303 */         return true;
/* 304 */       } catch (LoginException loginException) {
/*     */         
/* 306 */         cleanState();
/* 307 */         if (this.debug) {
/* 308 */           System.out.println("\t\t[JndiLoginModule] tryFirstPass failed with:" + loginException
/*     */               
/* 310 */               .toString());
/*     */         }
/*     */       }
/*     */     
/* 314 */     } else if (this.useFirstPass) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 319 */         attemptAuthentication(true);
/*     */ 
/*     */         
/* 322 */         this.succeeded = true;
/* 323 */         if (this.debug) {
/* 324 */           System.out.println("\t\t[JndiLoginModule] useFirstPass succeeded");
/*     */         }
/*     */         
/* 327 */         return true;
/* 328 */       } catch (LoginException loginException) {
/*     */         
/* 330 */         cleanState();
/* 331 */         if (this.debug) {
/* 332 */           System.out.println("\t\t[JndiLoginModule] useFirstPass failed");
/*     */         }
/*     */         
/* 335 */         throw loginException;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 341 */       attemptAuthentication(false);
/*     */ 
/*     */       
/* 344 */       this.succeeded = true;
/* 345 */       if (this.debug) {
/* 346 */         System.out.println("\t\t[JndiLoginModule] regular authentication succeeded");
/*     */       }
/*     */       
/* 349 */       return true;
/* 350 */     } catch (LoginException loginException) {
/* 351 */       cleanState();
/* 352 */       if (this.debug) {
/* 353 */         System.out.println("\t\t[JndiLoginModule] regular authentication failed");
/*     */       }
/*     */       
/* 356 */       throw loginException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean commit() throws LoginException {
/* 386 */     if (!this.succeeded) {
/* 387 */       return false;
/*     */     }
/* 389 */     if (this.subject.isReadOnly()) {
/* 390 */       cleanState();
/* 391 */       throw new LoginException("Subject is Readonly");
/*     */     } 
/*     */     
/* 394 */     if (!this.subject.getPrincipals().contains(this.userPrincipal))
/* 395 */       this.subject.getPrincipals().add(this.userPrincipal); 
/* 396 */     if (!this.subject.getPrincipals().contains(this.UIDPrincipal))
/* 397 */       this.subject.getPrincipals().add(this.UIDPrincipal); 
/* 398 */     if (!this.subject.getPrincipals().contains(this.GIDPrincipal))
/* 399 */       this.subject.getPrincipals().add(this.GIDPrincipal); 
/* 400 */     for (byte b = 0; b < this.supplementaryGroups.size(); b++) {
/*     */       
/* 402 */       if (!this.subject.getPrincipals().contains(this.supplementaryGroups.get(b))) {
/* 403 */         this.subject.getPrincipals().add(this.supplementaryGroups.get(b));
/*     */       }
/*     */     } 
/* 406 */     if (this.debug) {
/* 407 */       System.out.println("\t\t[JndiLoginModule]: added UnixPrincipal,");
/*     */       
/* 409 */       System.out.println("\t\t\t\tUnixNumericUserPrincipal,");
/* 410 */       System.out.println("\t\t\t\tUnixNumericGroupPrincipal(s),");
/* 411 */       System.out.println("\t\t\t to Subject");
/*     */     } 
/*     */ 
/*     */     
/* 415 */     cleanState();
/* 416 */     this.commitSucceeded = true;
/* 417 */     return true;
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
/*     */   public boolean abort() throws LoginException {
/* 439 */     if (this.debug) {
/* 440 */       System.out.println("\t\t[JndiLoginModule]: aborted authentication failed");
/*     */     }
/*     */     
/* 443 */     if (!this.succeeded)
/* 444 */       return false; 
/* 445 */     if (this.succeeded == true && !this.commitSucceeded) {
/*     */ 
/*     */       
/* 448 */       this.succeeded = false;
/* 449 */       cleanState();
/*     */       
/* 451 */       this.userPrincipal = null;
/* 452 */       this.UIDPrincipal = null;
/* 453 */       this.GIDPrincipal = null;
/* 454 */       this.supplementaryGroups = new LinkedList<>();
/*     */     }
/*     */     else {
/*     */       
/* 458 */       logout();
/*     */     } 
/* 460 */     return true;
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
/*     */   public boolean logout() throws LoginException {
/* 477 */     if (this.subject.isReadOnly()) {
/* 478 */       cleanState();
/* 479 */       throw new LoginException("Subject is Readonly");
/*     */     } 
/* 481 */     this.subject.getPrincipals().remove(this.userPrincipal);
/* 482 */     this.subject.getPrincipals().remove(this.UIDPrincipal);
/* 483 */     this.subject.getPrincipals().remove(this.GIDPrincipal);
/* 484 */     for (byte b = 0; b < this.supplementaryGroups.size(); b++) {
/* 485 */       this.subject.getPrincipals().remove(this.supplementaryGroups.get(b));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 490 */     cleanState();
/* 491 */     this.succeeded = false;
/* 492 */     this.commitSucceeded = false;
/*     */     
/* 494 */     this.userPrincipal = null;
/* 495 */     this.UIDPrincipal = null;
/* 496 */     this.GIDPrincipal = null;
/* 497 */     this.supplementaryGroups = new LinkedList<>();
/*     */     
/* 499 */     if (this.debug) {
/* 500 */       System.out.println("\t\t[JndiLoginModule]: logged out Subject");
/*     */     }
/*     */     
/* 503 */     return true;
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
/* 517 */     String str = null;
/*     */ 
/*     */     
/* 520 */     getUsernamePassword(paramBoolean);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 525 */       InitialContext initialContext = new InitialContext();
/* 526 */       this.ctx = (DirContext)initialContext.lookup(this.userProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 538 */       SearchControls searchControls = new SearchControls();
/* 539 */       NamingEnumeration<SearchResult> namingEnumeration = this.ctx.search("", "(uid=" + this.username + ")", searchControls);
/*     */ 
/*     */       
/* 542 */       if (namingEnumeration.hasMore()) {
/* 543 */         SearchResult searchResult = namingEnumeration.next();
/* 544 */         Attributes attributes = searchResult.getAttributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 563 */         Attribute attribute1 = attributes.get("userPassword");
/* 564 */         String str1 = new String((byte[])attribute1.get(), "UTF8");
/* 565 */         str = str1.substring("{crypt}".length());
/*     */ 
/*     */ 
/*     */         
/* 569 */         if (verifyPassword(str, new String(this.password)) == true) {
/*     */ 
/*     */           
/* 572 */           if (this.debug) {
/* 573 */             System.out.println("\t\t[JndiLoginModule] attemptAuthentication() succeeded");
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 578 */           if (this.debug) {
/* 579 */             System.out.println("\t\t[JndiLoginModule] attemptAuthentication() failed");
/*     */           }
/* 581 */           throw new FailedLoginException("Login incorrect");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 586 */         if (this.storePass && 
/* 587 */           !this.sharedState.containsKey("javax.security.auth.login.name") && 
/* 588 */           !this.sharedState.containsKey("javax.security.auth.login.password")) {
/* 589 */           this.sharedState.put("javax.security.auth.login.name", this.username);
/* 590 */           this.sharedState.put("javax.security.auth.login.password", this.password);
/*     */         } 
/*     */ 
/*     */         
/* 594 */         this.userPrincipal = new UnixPrincipal(this.username);
/*     */ 
/*     */         
/* 597 */         Attribute attribute2 = attributes.get("uidNumber");
/* 598 */         String str2 = (String)attribute2.get();
/* 599 */         this.UIDPrincipal = new UnixNumericUserPrincipal(str2);
/* 600 */         if (this.debug && str2 != null) {
/* 601 */           System.out.println("\t\t[JndiLoginModule] user: '" + this.username + "' has UID: " + str2);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 607 */         Attribute attribute3 = attributes.get("gidNumber");
/* 608 */         String str3 = (String)attribute3.get();
/* 609 */         this.GIDPrincipal = new UnixNumericGroupPrincipal(str3, true);
/*     */         
/* 611 */         if (this.debug && str3 != null) {
/* 612 */           System.out.println("\t\t[JndiLoginModule] user: '" + this.username + "' has GID: " + str3);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 618 */         this.ctx = (DirContext)initialContext.lookup(this.groupProvider);
/* 619 */         namingEnumeration = this.ctx.search("", new BasicAttributes("memberUid", this.username));
/*     */         
/* 621 */         while (namingEnumeration.hasMore()) {
/* 622 */           searchResult = namingEnumeration.next();
/* 623 */           attributes = searchResult.getAttributes();
/*     */           
/* 625 */           attribute3 = attributes.get("gidNumber");
/* 626 */           String str4 = (String)attribute3.get();
/* 627 */           if (!str3.equals(str4)) {
/* 628 */             UnixNumericGroupPrincipal unixNumericGroupPrincipal = new UnixNumericGroupPrincipal(str4, false);
/*     */             
/* 630 */             this.supplementaryGroups.add(unixNumericGroupPrincipal);
/* 631 */             if (this.debug && str4 != null) {
/* 632 */               System.out.println("\t\t[JndiLoginModule] user: '" + this.username + "' has Supplementary Group: " + str4);
/*     */             
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 642 */         if (this.debug) {
/* 643 */           System.out.println("\t\t[JndiLoginModule]: User not found");
/*     */         }
/* 645 */         throw new FailedLoginException("User not found");
/*     */       } 
/* 647 */     } catch (NamingException namingException) {
/*     */       
/* 649 */       if (this.debug) {
/* 650 */         System.out.println("\t\t[JndiLoginModule]:  User not found");
/* 651 */         namingException.printStackTrace();
/*     */       } 
/* 653 */       throw new FailedLoginException("User not found");
/* 654 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/* 656 */       if (this.debug) {
/* 657 */         System.out.println("\t\t[JndiLoginModule]:  password incorrectly encoded");
/*     */         
/* 659 */         unsupportedEncodingException.printStackTrace();
/*     */       } 
/* 661 */       throw new LoginException("Login failure due to incorrect password encoding in the password database");
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
/*     */   private void getUsernamePassword(boolean paramBoolean) throws LoginException {
/* 685 */     if (paramBoolean) {
/*     */       
/* 687 */       this.username = (String)this.sharedState.get("javax.security.auth.login.name");
/* 688 */       this.password = (char[])this.sharedState.get("javax.security.auth.login.password");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 693 */     if (this.callbackHandler == null) {
/* 694 */       throw new LoginException("Error: no CallbackHandler available to garner authentication information from the user");
/*     */     }
/*     */     
/* 697 */     String str = this.userProvider.substring(0, this.userProvider.indexOf(":"));
/*     */     
/* 699 */     Callback[] arrayOfCallback = new Callback[2];
/* 700 */     arrayOfCallback[0] = new NameCallback(str + " " + rb
/* 701 */         .getString("username."));
/* 702 */     arrayOfCallback[1] = new PasswordCallback(str + " " + rb
/* 703 */         .getString("password."), false);
/*     */ 
/*     */     
/*     */     try {
/* 707 */       this.callbackHandler.handle(arrayOfCallback);
/* 708 */       this.username = ((NameCallback)arrayOfCallback[0]).getName();
/* 709 */       char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[1]).getPassword();
/* 710 */       this.password = new char[arrayOfChar.length];
/* 711 */       System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
/*     */       
/* 713 */       ((PasswordCallback)arrayOfCallback[1]).clearPassword();
/*     */     }
/* 715 */     catch (IOException iOException) {
/* 716 */       throw new LoginException(iOException.toString());
/* 717 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 718 */       throw new LoginException("Error: " + unsupportedCallbackException.getCallback().toString() + " not available to garner authentication information from the user");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 724 */     if (this.strongDebug) {
/* 725 */       System.out.println("\t\t[JndiLoginModule] user entered username: " + this.username);
/*     */ 
/*     */       
/* 728 */       System.out.print("\t\t[JndiLoginModule] user entered password: ");
/*     */       
/* 730 */       for (byte b = 0; b < this.password.length; b++)
/* 731 */         System.out.print(this.password[b]); 
/* 732 */       System.out.println();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean verifyPassword(String paramString1, String paramString2) {
/* 741 */     if (paramString1 == null) {
/* 742 */       return false;
/*     */     }
/* 744 */     Crypt crypt = new Crypt();
/*     */     try {
/* 746 */       byte[] arrayOfByte1 = paramString1.getBytes("UTF8");
/* 747 */       byte[] arrayOfByte2 = crypt.crypt(paramString2.getBytes("UTF8"), arrayOfByte1);
/*     */       
/* 749 */       if (arrayOfByte2.length != arrayOfByte1.length)
/* 750 */         return false; 
/* 751 */       for (byte b = 0; b < arrayOfByte2.length; b++) {
/* 752 */         if (arrayOfByte1[b] != arrayOfByte2[b])
/* 753 */           return false; 
/*     */       } 
/* 755 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */       
/* 757 */       return false;
/*     */     } 
/* 759 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanState() {
/* 766 */     this.username = null;
/* 767 */     if (this.password != null) {
/* 768 */       for (byte b = 0; b < this.password.length; b++)
/* 769 */         this.password[b] = ' '; 
/* 770 */       this.password = null;
/*     */     } 
/* 772 */     this.ctx = null;
/*     */     
/* 774 */     if (this.clearPass) {
/* 775 */       this.sharedState.remove("javax.security.auth.login.name");
/* 776 */       this.sharedState.remove("javax.security.auth.login.password");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/JndiLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */