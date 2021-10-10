/*      */ package com.sun.security.auth.module;
/*      */ 
/*      */ import com.sun.security.auth.LdapPrincipal;
/*      */ import com.sun.security.auth.UserPrincipal;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.naming.InvalidNameException;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.SearchControls;
/*      */ import javax.naming.directory.SearchResult;
/*      */ import javax.naming.ldap.Control;
/*      */ import javax.naming.ldap.InitialLdapContext;
/*      */ import javax.naming.ldap.LdapContext;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.login.FailedLoginException;
/*      */ import javax.security.auth.login.LoginException;
/*      */ import javax.security.auth.spi.LoginModule;
/*      */ import jdk.Exported;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Exported
/*      */ public class LdapLoginModule
/*      */   implements LoginModule
/*      */ {
/*  311 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*      */       {
/*      */         public ResourceBundle run() {
/*  314 */           return ResourceBundle.getBundle("sun.security.util.AuthResources");
/*      */         }
/*      */       });
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String USERNAME_KEY = "javax.security.auth.login.name";
/*      */   
/*      */   private static final String PASSWORD_KEY = "javax.security.auth.login.password";
/*      */   
/*      */   private static final String USER_PROVIDER = "userProvider";
/*      */   
/*      */   private static final String USER_FILTER = "userFilter";
/*      */   
/*      */   private static final String AUTHC_IDENTITY = "authIdentity";
/*      */   
/*      */   private static final String AUTHZ_IDENTITY = "authzIdentity";
/*      */   
/*      */   private static final String USERNAME_TOKEN = "{USERNAME}";
/*      */   
/*  334 */   private static final Pattern USERNAME_PATTERN = Pattern.compile("\\{USERNAME\\}");
/*      */   
/*      */   private String userProvider;
/*      */   
/*      */   private String userFilter;
/*      */   private String authcIdentity;
/*      */   private String authzIdentity;
/*  341 */   private String authzIdentityAttr = null;
/*      */   
/*      */   private boolean useSSL = true;
/*      */   
/*      */   private boolean authFirst = false;
/*      */   
/*      */   private boolean authOnly = false;
/*      */   
/*      */   private boolean useFirstPass = false;
/*      */   
/*      */   private boolean tryFirstPass = false;
/*      */   
/*      */   private boolean storePass = false;
/*      */   
/*      */   private boolean clearPass = false;
/*      */   
/*      */   private boolean debug = false;
/*      */   private boolean succeeded = false;
/*      */   private boolean commitSucceeded = false;
/*      */   private String username;
/*      */   private char[] password;
/*      */   private LdapPrincipal ldapPrincipal;
/*      */   private UserPrincipal userPrincipal;
/*      */   private UserPrincipal authzPrincipal;
/*      */   private Subject subject;
/*      */   private CallbackHandler callbackHandler;
/*      */   private Map<String, Object> sharedState;
/*      */   private Map<String, ?> options;
/*      */   private LdapContext ctx;
/*  370 */   private Matcher identityMatcher = null;
/*  371 */   private Matcher filterMatcher = null;
/*      */   private Hashtable<String, Object> ldapEnvironment;
/*  373 */   private SearchControls constraints = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/*  392 */     this.subject = paramSubject;
/*  393 */     this.callbackHandler = paramCallbackHandler;
/*  394 */     this.sharedState = (Map)paramMap1;
/*  395 */     this.options = paramMap2;
/*      */     
/*  397 */     this.ldapEnvironment = new Hashtable<>(9);
/*  398 */     this.ldapEnvironment.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
/*      */ 
/*      */ 
/*      */     
/*  402 */     for (String str : paramMap2.keySet()) {
/*  403 */       if (str.indexOf(".") > -1) {
/*  404 */         this.ldapEnvironment.put(str, paramMap2.get(str));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  410 */     this.userProvider = (String)paramMap2.get("userProvider");
/*  411 */     if (this.userProvider != null) {
/*  412 */       this.ldapEnvironment.put("java.naming.provider.url", this.userProvider);
/*      */     }
/*      */     
/*  415 */     this.authcIdentity = (String)paramMap2.get("authIdentity");
/*  416 */     if (this.authcIdentity != null && this.authcIdentity
/*  417 */       .indexOf("{USERNAME}") != -1) {
/*  418 */       this.identityMatcher = USERNAME_PATTERN.matcher(this.authcIdentity);
/*      */     }
/*      */     
/*  421 */     this.userFilter = (String)paramMap2.get("userFilter");
/*  422 */     if (this.userFilter != null) {
/*  423 */       if (this.userFilter.indexOf("{USERNAME}") != -1) {
/*  424 */         this.filterMatcher = USERNAME_PATTERN.matcher(this.userFilter);
/*      */       }
/*  426 */       this.constraints = new SearchControls();
/*  427 */       this.constraints.setSearchScope(2);
/*  428 */       this.constraints.setReturningAttributes(new String[0]);
/*      */     } 
/*      */     
/*  431 */     this.authzIdentity = (String)paramMap2.get("authzIdentity");
/*  432 */     if (this.authzIdentity != null && this.authzIdentity
/*  433 */       .startsWith("{") && this.authzIdentity.endsWith("}")) {
/*  434 */       if (this.constraints != null) {
/*  435 */         this
/*  436 */           .authzIdentityAttr = this.authzIdentity.substring(1, this.authzIdentity.length() - 1);
/*  437 */         this.constraints.setReturningAttributes(new String[] { this.authzIdentityAttr });
/*      */       } 
/*      */       
/*  440 */       this.authzIdentity = null;
/*      */     } 
/*      */ 
/*      */     
/*  444 */     if (this.authcIdentity != null) {
/*  445 */       if (this.userFilter != null) {
/*  446 */         this.authFirst = true;
/*      */       } else {
/*  448 */         this.authOnly = true;
/*      */       } 
/*      */     }
/*      */     
/*  452 */     if ("false".equalsIgnoreCase((String)paramMap2.get("useSSL"))) {
/*  453 */       this.useSSL = false;
/*  454 */       this.ldapEnvironment.remove("java.naming.security.protocol");
/*      */     } else {
/*  456 */       this.ldapEnvironment.put("java.naming.security.protocol", "ssl");
/*      */     } 
/*      */     
/*  459 */     this
/*  460 */       .tryFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("tryFirstPass"));
/*      */     
/*  462 */     this
/*  463 */       .useFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("useFirstPass"));
/*      */     
/*  465 */     this.storePass = "true".equalsIgnoreCase((String)paramMap2.get("storePass"));
/*      */     
/*  467 */     this.clearPass = "true".equalsIgnoreCase((String)paramMap2.get("clearPass"));
/*      */     
/*  469 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
/*      */     
/*  471 */     if (this.debug) {
/*  472 */       if (this.authFirst) {
/*  473 */         System.out.println("\t\t[LdapLoginModule] authentication-first mode; " + (this.useSSL ? "SSL enabled" : "SSL disabled"));
/*      */       
/*      */       }
/*  476 */       else if (this.authOnly) {
/*  477 */         System.out.println("\t\t[LdapLoginModule] authentication-only mode; " + (this.useSSL ? "SSL enabled" : "SSL disabled"));
/*      */       }
/*      */       else {
/*      */         
/*  481 */         System.out.println("\t\t[LdapLoginModule] search-first mode; " + (this.useSSL ? "SSL enabled" : "SSL disabled"));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean login() throws LoginException {
/*  502 */     if (this.userProvider == null) {
/*  503 */       throw new LoginException("Unable to locate the LDAP directory service");
/*      */     }
/*      */ 
/*      */     
/*  507 */     if (this.debug) {
/*  508 */       System.out.println("\t\t[LdapLoginModule] user provider: " + this.userProvider);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  513 */     if (this.tryFirstPass) {
/*      */       
/*      */       try
/*      */       {
/*      */         
/*  518 */         attemptAuthentication(true);
/*      */ 
/*      */         
/*  521 */         this.succeeded = true;
/*  522 */         if (this.debug) {
/*  523 */           System.out.println("\t\t[LdapLoginModule] tryFirstPass succeeded");
/*      */         }
/*      */         
/*  526 */         return true;
/*      */       }
/*  528 */       catch (LoginException loginException)
/*      */       {
/*  530 */         cleanState();
/*  531 */         if (this.debug) {
/*  532 */           System.out.println("\t\t[LdapLoginModule] tryFirstPass failed: " + loginException
/*  533 */               .toString());
/*      */         }
/*      */       }
/*      */     
/*  537 */     } else if (this.useFirstPass) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  542 */         attemptAuthentication(true);
/*      */ 
/*      */         
/*  545 */         this.succeeded = true;
/*  546 */         if (this.debug) {
/*  547 */           System.out.println("\t\t[LdapLoginModule] useFirstPass succeeded");
/*      */         }
/*      */         
/*  550 */         return true;
/*      */       }
/*  552 */       catch (LoginException loginException) {
/*      */         
/*  554 */         cleanState();
/*  555 */         if (this.debug) {
/*  556 */           System.out.println("\t\t[LdapLoginModule] useFirstPass failed");
/*      */         }
/*      */         
/*  559 */         throw loginException;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  565 */       attemptAuthentication(false);
/*      */ 
/*      */       
/*  568 */       this.succeeded = true;
/*  569 */       if (this.debug) {
/*  570 */         System.out.println("\t\t[LdapLoginModule] authentication succeeded");
/*      */       }
/*      */       
/*  573 */       return true;
/*      */     }
/*  575 */     catch (LoginException loginException) {
/*  576 */       cleanState();
/*  577 */       if (this.debug) {
/*  578 */         System.out.println("\t\t[LdapLoginModule] authentication failed");
/*      */       }
/*      */       
/*  581 */       throw loginException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean commit() throws LoginException {
/*  608 */     if (!this.succeeded) {
/*  609 */       return false;
/*      */     }
/*  611 */     if (this.subject.isReadOnly()) {
/*  612 */       cleanState();
/*  613 */       throw new LoginException("Subject is read-only");
/*      */     } 
/*      */     
/*  616 */     Set<Principal> set = this.subject.getPrincipals();
/*  617 */     if (!set.contains(this.ldapPrincipal)) {
/*  618 */       set.add(this.ldapPrincipal);
/*      */     }
/*  620 */     if (this.debug) {
/*  621 */       System.out.println("\t\t[LdapLoginModule] added LdapPrincipal \"" + this.ldapPrincipal + "\" to Subject");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  627 */     if (!set.contains(this.userPrincipal)) {
/*  628 */       set.add(this.userPrincipal);
/*      */     }
/*  630 */     if (this.debug) {
/*  631 */       System.out.println("\t\t[LdapLoginModule] added UserPrincipal \"" + this.userPrincipal + "\" to Subject");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     if (this.authzPrincipal != null && 
/*  638 */       !set.contains(this.authzPrincipal)) {
/*  639 */       set.add(this.authzPrincipal);
/*      */       
/*  641 */       if (this.debug) {
/*  642 */         System.out.println("\t\t[LdapLoginModule] added UserPrincipal \"" + this.authzPrincipal + "\" to Subject");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     cleanState();
/*  651 */     this.commitSucceeded = true;
/*  652 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean abort() throws LoginException {
/*  672 */     if (this.debug) {
/*  673 */       System.out.println("\t\t[LdapLoginModule] aborted authentication");
/*      */     }
/*      */     
/*  676 */     if (!this.succeeded)
/*  677 */       return false; 
/*  678 */     if (this.succeeded == true && !this.commitSucceeded) {
/*      */ 
/*      */       
/*  681 */       this.succeeded = false;
/*  682 */       cleanState();
/*      */       
/*  684 */       this.ldapPrincipal = null;
/*  685 */       this.userPrincipal = null;
/*  686 */       this.authzPrincipal = null;
/*      */     }
/*      */     else {
/*      */       
/*  690 */       logout();
/*      */     } 
/*  692 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean logout() throws LoginException {
/*  706 */     if (this.subject.isReadOnly()) {
/*  707 */       cleanState();
/*  708 */       throw new LoginException("Subject is read-only");
/*      */     } 
/*  710 */     Set<Principal> set = this.subject.getPrincipals();
/*  711 */     set.remove(this.ldapPrincipal);
/*  712 */     set.remove(this.userPrincipal);
/*  713 */     if (this.authzIdentity != null) {
/*  714 */       set.remove(this.authzPrincipal);
/*      */     }
/*      */ 
/*      */     
/*  718 */     cleanState();
/*  719 */     this.succeeded = false;
/*  720 */     this.commitSucceeded = false;
/*      */     
/*  722 */     this.ldapPrincipal = null;
/*  723 */     this.userPrincipal = null;
/*  724 */     this.authzPrincipal = null;
/*      */     
/*  726 */     if (this.debug) {
/*  727 */       System.out.println("\t\t[LdapLoginModule] logged out Subject");
/*      */     }
/*  729 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void attemptAuthentication(boolean paramBoolean) throws LoginException {
/*  743 */     getUsernamePassword(paramBoolean);
/*      */     
/*  745 */     if (this.password == null || this.password.length == 0) {
/*  746 */       throw new FailedLoginException("No password was supplied");
/*      */     }
/*      */ 
/*      */     
/*  750 */     String str = "";
/*      */     
/*  752 */     if (this.authFirst || this.authOnly) {
/*      */ 
/*      */       
/*  755 */       String str1 = replaceUsernameToken(this.identityMatcher, this.authcIdentity, this.username);
/*      */ 
/*      */       
/*  758 */       this.ldapEnvironment.put("java.naming.security.credentials", this.password);
/*  759 */       this.ldapEnvironment.put("java.naming.security.principal", str1);
/*      */       
/*  761 */       if (this.debug) {
/*  762 */         System.out.println("\t\t[LdapLoginModule] attempting to authenticate user: " + this.username);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  768 */         this.ctx = new InitialLdapContext(this.ldapEnvironment, null);
/*      */       }
/*  770 */       catch (NamingException namingException) {
/*  771 */         throw (LoginException)(new FailedLoginException("Cannot bind to LDAP server"))
/*      */           
/*  773 */           .initCause(namingException);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  779 */       if (this.userFilter != null) {
/*  780 */         str = findUserDN(this.ctx);
/*      */       } else {
/*  782 */         str = str1;
/*      */       } 
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  789 */         this.ctx = new InitialLdapContext(this.ldapEnvironment, null);
/*      */       }
/*  791 */       catch (NamingException namingException) {
/*  792 */         throw (LoginException)(new FailedLoginException("Cannot connect to LDAP server"))
/*      */           
/*  794 */           .initCause(namingException);
/*      */       } 
/*      */ 
/*      */       
/*  798 */       str = findUserDN(this.ctx);
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  803 */         this.ctx.addToEnvironment("java.naming.security.authentication", "simple");
/*  804 */         this.ctx.addToEnvironment("java.naming.security.principal", str);
/*  805 */         this.ctx.addToEnvironment("java.naming.security.credentials", this.password);
/*      */         
/*  807 */         if (this.debug) {
/*  808 */           System.out.println("\t\t[LdapLoginModule] attempting to authenticate user: " + this.username);
/*      */         }
/*      */ 
/*      */         
/*  812 */         this.ctx.reconnect((Control[])null);
/*      */ 
/*      */       
/*      */       }
/*  816 */       catch (NamingException namingException) {
/*  817 */         throw (LoginException)(new FailedLoginException("Cannot bind to LDAP server"))
/*      */           
/*  819 */           .initCause(namingException);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  824 */     if (this.storePass && 
/*  825 */       !this.sharedState.containsKey("javax.security.auth.login.name") && 
/*  826 */       !this.sharedState.containsKey("javax.security.auth.login.password")) {
/*  827 */       this.sharedState.put("javax.security.auth.login.name", this.username);
/*  828 */       this.sharedState.put("javax.security.auth.login.password", this.password);
/*      */     } 
/*      */ 
/*      */     
/*  832 */     this.userPrincipal = new UserPrincipal(this.username);
/*  833 */     if (this.authzIdentity != null) {
/*  834 */       this.authzPrincipal = new UserPrincipal(this.authzIdentity);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  839 */       this.ldapPrincipal = new LdapPrincipal(str);
/*      */     }
/*  841 */     catch (InvalidNameException invalidNameException) {
/*  842 */       if (this.debug) {
/*  843 */         System.out.println("\t\t[LdapLoginModule] cannot create LdapPrincipal: bad DN");
/*      */       }
/*      */       
/*  846 */       throw (LoginException)(new FailedLoginException("Cannot create LdapPrincipal"))
/*      */         
/*  848 */         .initCause(invalidNameException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String findUserDN(LdapContext paramLdapContext) throws LoginException {
/*  864 */     String str = "";
/*      */ 
/*      */     
/*  867 */     if (this.userFilter != null) {
/*  868 */       if (this.debug) {
/*  869 */         System.out.println("\t\t[LdapLoginModule] searching for entry belonging to user: " + this.username);
/*      */       }
/*      */     } else {
/*      */       
/*  873 */       if (this.debug) {
/*  874 */         System.out.println("\t\t[LdapLoginModule] cannot search for entry belonging to user: " + this.username);
/*      */       }
/*      */       
/*  877 */       throw new FailedLoginException("Cannot find user's LDAP entry");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  884 */       String str1 = replaceUsernameToken(this.filterMatcher, this.userFilter, 
/*  885 */           escapeUsernameChars());
/*      */ 
/*      */       
/*  888 */       NamingEnumeration<SearchResult> namingEnumeration = paramLdapContext.search("", str1, this.constraints);
/*      */ 
/*      */ 
/*      */       
/*  892 */       if (namingEnumeration.hasMore()) {
/*  893 */         SearchResult searchResult = namingEnumeration.next();
/*  894 */         str = searchResult.getNameInNamespace();
/*      */         
/*  896 */         if (this.debug) {
/*  897 */           System.out.println("\t\t[LdapLoginModule] found entry: " + str);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  902 */         if (this.authzIdentityAttr != null) {
/*      */           
/*  904 */           Attribute attribute = searchResult.getAttributes().get(this.authzIdentityAttr);
/*  905 */           if (attribute != null) {
/*  906 */             Object object = attribute.get();
/*  907 */             if (object instanceof String) {
/*  908 */               this.authzIdentity = (String)object;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  913 */         namingEnumeration.close();
/*      */ 
/*      */       
/*      */       }
/*  917 */       else if (this.debug) {
/*  918 */         System.out.println("\t\t[LdapLoginModule] user's entry not found");
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  923 */     catch (NamingException namingException) {}
/*      */ 
/*      */ 
/*      */     
/*  927 */     if (str.equals("")) {
/*  928 */       throw new FailedLoginException("Cannot find user's LDAP entry");
/*      */     }
/*      */     
/*  931 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String escapeUsernameChars() {
/*  951 */     int i = this.username.length();
/*  952 */     StringBuilder stringBuilder = new StringBuilder(i + 16);
/*      */     
/*  954 */     for (byte b = 0; b < i; b++) {
/*  955 */       char c = this.username.charAt(b);
/*  956 */       switch (c) {
/*      */         case '*':
/*  958 */           stringBuilder.append("\\\\2A");
/*      */           break;
/*      */         case '(':
/*  961 */           stringBuilder.append("\\\\28");
/*      */           break;
/*      */         case ')':
/*  964 */           stringBuilder.append("\\\\29");
/*      */           break;
/*      */         case '\\':
/*  967 */           stringBuilder.append("\\\\5C");
/*      */           break;
/*      */         case '\000':
/*  970 */           stringBuilder.append("\\\\00");
/*      */           break;
/*      */         default:
/*  973 */           stringBuilder.append(c);
/*      */           break;
/*      */       } 
/*      */     } 
/*  977 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String replaceUsernameToken(Matcher paramMatcher, String paramString1, String paramString2) {
/*  991 */     return (paramMatcher != null) ? paramMatcher.replaceAll(paramString2) : paramString1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getUsernamePassword(boolean paramBoolean) throws LoginException {
/* 1010 */     if (paramBoolean) {
/*      */       
/* 1012 */       this.username = (String)this.sharedState.get("javax.security.auth.login.name");
/* 1013 */       this.password = (char[])this.sharedState.get("javax.security.auth.login.password");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1018 */     if (this.callbackHandler == null) {
/* 1019 */       throw new LoginException("No CallbackHandler available to acquire authentication information from the user");
/*      */     }
/*      */     
/* 1022 */     Callback[] arrayOfCallback = new Callback[2];
/* 1023 */     arrayOfCallback[0] = new NameCallback(rb.getString("username."));
/* 1024 */     arrayOfCallback[1] = new PasswordCallback(rb.getString("password."), false);
/*      */     
/*      */     try {
/* 1027 */       this.callbackHandler.handle(arrayOfCallback);
/* 1028 */       this.username = ((NameCallback)arrayOfCallback[0]).getName();
/* 1029 */       char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[1]).getPassword();
/* 1030 */       this.password = new char[arrayOfChar.length];
/* 1031 */       System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
/*      */       
/* 1033 */       ((PasswordCallback)arrayOfCallback[1]).clearPassword();
/*      */     }
/* 1035 */     catch (IOException iOException) {
/* 1036 */       throw new LoginException(iOException.toString());
/*      */     }
/* 1038 */     catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 1039 */       throw new LoginException("Error: " + unsupportedCallbackException.getCallback().toString() + " not available to acquire authentication information from the user");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanState() {
/* 1049 */     this.username = null;
/* 1050 */     if (this.password != null) {
/* 1051 */       Arrays.fill(this.password, ' ');
/* 1052 */       this.password = null;
/*      */     } 
/*      */     try {
/* 1055 */       if (this.ctx != null) {
/* 1056 */         this.ctx.close();
/*      */       }
/* 1058 */     } catch (NamingException namingException) {}
/*      */ 
/*      */     
/* 1061 */     this.ctx = null;
/*      */     
/* 1063 */     if (this.clearPass) {
/* 1064 */       this.sharedState.remove("javax.security.auth.login.name");
/* 1065 */       this.sharedState.remove("javax.security.auth.login.password");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/LdapLoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */