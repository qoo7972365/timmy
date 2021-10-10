/*     */ package javax.security.auth.login;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Security;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.AuthPermission;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.PendingException;
/*     */ import sun.security.util.ResourcesMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoginContext
/*     */ {
/*     */   private static final String INIT_METHOD = "initialize";
/*     */   private static final String LOGIN_METHOD = "login";
/*     */   private static final String COMMIT_METHOD = "commit";
/*     */   private static final String ABORT_METHOD = "abort";
/*     */   private static final String LOGOUT_METHOD = "logout";
/*     */   private static final String OTHER = "other";
/*     */   private static final String DEFAULT_HANDLER = "auth.login.defaultCallbackHandler";
/* 205 */   private Subject subject = null;
/*     */   private boolean subjectProvided = false;
/*     */   private boolean loginSucceeded = false;
/*     */   private CallbackHandler callbackHandler;
/* 209 */   private Map<String, ?> state = new HashMap<>();
/*     */   
/*     */   private Configuration config;
/* 212 */   private AccessControlContext creatorAcc = null;
/*     */   private ModuleInfo[] moduleStack;
/* 214 */   private ClassLoader contextClassLoader = null;
/* 215 */   private static final Class<?>[] PARAMS = new Class[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   private int moduleIndex = 0;
/* 221 */   private LoginException firstError = null;
/* 222 */   private LoginException firstRequiredError = null;
/*     */   
/*     */   private boolean success = false;
/*     */   
/* 226 */   private static final Debug debug = Debug.getInstance("logincontext", "\t[LoginContext]");
/*     */ 
/*     */   
/*     */   private void init(String paramString) throws LoginException {
/* 230 */     SecurityManager securityManager = System.getSecurityManager();
/* 231 */     if (securityManager != null && this.creatorAcc == null) {
/* 232 */       securityManager.checkPermission(new AuthPermission("createLoginContext." + paramString));
/*     */     }
/*     */ 
/*     */     
/* 236 */     if (paramString == null) {
/* 237 */       throw new LoginException(
/* 238 */           ResourcesMgr.getString("Invalid.null.input.name"));
/*     */     }
/*     */     
/* 241 */     if (this.config == null) {
/* 242 */       this
/* 243 */         .config = AccessController.<Configuration>doPrivileged(new PrivilegedAction<Configuration>() {
/*     */             public Configuration run() {
/* 245 */               return Configuration.getConfiguration();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 251 */     AppConfigurationEntry[] arrayOfAppConfigurationEntry = this.config.getAppConfigurationEntry(paramString);
/* 252 */     if (arrayOfAppConfigurationEntry == null) {
/*     */       
/* 254 */       if (securityManager != null && this.creatorAcc == null) {
/* 255 */         securityManager.checkPermission(new AuthPermission("createLoginContext.other"));
/*     */       }
/*     */ 
/*     */       
/* 259 */       arrayOfAppConfigurationEntry = this.config.getAppConfigurationEntry("other");
/* 260 */       if (arrayOfAppConfigurationEntry == null) {
/*     */         
/* 262 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("No.LoginModules.configured.for.name"));
/* 263 */         Object[] arrayOfObject = { paramString };
/* 264 */         throw new LoginException(messageFormat.format(arrayOfObject));
/*     */       } 
/*     */     } 
/* 267 */     this.moduleStack = new ModuleInfo[arrayOfAppConfigurationEntry.length];
/* 268 */     for (byte b = 0; b < arrayOfAppConfigurationEntry.length; b++)
/*     */     {
/* 270 */       this.moduleStack[b] = new ModuleInfo(new AppConfigurationEntry(arrayOfAppConfigurationEntry[b]
/*     */             
/* 272 */             .getLoginModuleName(), arrayOfAppConfigurationEntry[b]
/* 273 */             .getControlFlag(), arrayOfAppConfigurationEntry[b]
/* 274 */             .getOptions()), null);
/*     */     }
/*     */ 
/*     */     
/* 278 */     this
/* 279 */       .contextClassLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public ClassLoader run() {
/* 282 */             ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 283 */             if (classLoader == null)
/*     */             {
/*     */               
/* 286 */               classLoader = ClassLoader.getSystemClassLoader();
/*     */             }
/*     */             
/* 289 */             return classLoader;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadDefaultCallbackHandler() throws LoginException {
/*     */     try {
/* 299 */       final ClassLoader finalLoader = this.contextClassLoader;
/*     */       
/* 301 */       this.callbackHandler = AccessController.<CallbackHandler>doPrivileged(new PrivilegedExceptionAction<CallbackHandler>()
/*     */           {
/*     */             public CallbackHandler run() throws Exception
/*     */             {
/* 305 */               String str = Security.getProperty("auth.login.defaultCallbackHandler");
/* 306 */               if (str == null || str.length() == 0) {
/* 307 */                 return null;
/*     */               }
/*     */               
/* 310 */               Class<? extends CallbackHandler> clazz = Class.forName(str, true, finalLoader).asSubclass(CallbackHandler.class);
/* 311 */               return clazz.newInstance();
/*     */             }
/*     */           });
/* 314 */     } catch (PrivilegedActionException privilegedActionException) {
/* 315 */       throw new LoginException(privilegedActionException.getException().toString());
/*     */     } 
/*     */ 
/*     */     
/* 319 */     if (this.callbackHandler != null && this.creatorAcc == null) {
/* 320 */       this
/* 321 */         .callbackHandler = new SecureCallbackHandler(AccessController.getContext(), this.callbackHandler);
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
/*     */   public LoginContext(String paramString) throws LoginException {
/* 348 */     init(paramString);
/* 349 */     loadDefaultCallbackHandler();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginContext(String paramString, Subject paramSubject) throws LoginException {
/* 381 */     init(paramString);
/* 382 */     if (paramSubject == null)
/* 383 */       throw new LoginException(
/* 384 */           ResourcesMgr.getString("invalid.null.Subject.provided")); 
/* 385 */     this.subject = paramSubject;
/* 386 */     this.subjectProvided = true;
/* 387 */     loadDefaultCallbackHandler();
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
/*     */   
/*     */   public LoginContext(String paramString, CallbackHandler paramCallbackHandler) throws LoginException {
/* 417 */     init(paramString);
/* 418 */     if (paramCallbackHandler == null)
/* 419 */       throw new LoginException(
/* 420 */           ResourcesMgr.getString("invalid.null.CallbackHandler.provided")); 
/* 421 */     this
/* 422 */       .callbackHandler = new SecureCallbackHandler(AccessController.getContext(), paramCallbackHandler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginContext(String paramString, Subject paramSubject, CallbackHandler paramCallbackHandler) throws LoginException {
/* 458 */     this(paramString, paramSubject);
/* 459 */     if (paramCallbackHandler == null)
/* 460 */       throw new LoginException(
/* 461 */           ResourcesMgr.getString("invalid.null.CallbackHandler.provided")); 
/* 462 */     this
/* 463 */       .callbackHandler = new SecureCallbackHandler(AccessController.getContext(), paramCallbackHandler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginContext(String paramString, Subject paramSubject, CallbackHandler paramCallbackHandler, Configuration paramConfiguration) throws LoginException {
/* 507 */     this.config = paramConfiguration;
/* 508 */     if (paramConfiguration != null) {
/* 509 */       this.creatorAcc = AccessController.getContext();
/*     */     }
/*     */     
/* 512 */     init(paramString);
/* 513 */     if (paramSubject != null) {
/* 514 */       this.subject = paramSubject;
/* 515 */       this.subjectProvided = true;
/*     */     } 
/* 517 */     if (paramCallbackHandler == null) {
/* 518 */       loadDefaultCallbackHandler();
/* 519 */     } else if (this.creatorAcc == null) {
/* 520 */       this
/* 521 */         .callbackHandler = new SecureCallbackHandler(AccessController.getContext(), paramCallbackHandler);
/*     */     } else {
/*     */       
/* 524 */       this.callbackHandler = paramCallbackHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void login() throws LoginException {
/* 579 */     this.loginSucceeded = false;
/*     */     
/* 581 */     if (this.subject == null) {
/* 582 */       this.subject = new Subject();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 587 */       invokePriv("login");
/* 588 */       invokePriv("commit");
/* 589 */       this.loginSucceeded = true;
/* 590 */     } catch (LoginException loginException) {
/*     */       try {
/* 592 */         invokePriv("abort");
/* 593 */       } catch (LoginException loginException1) {
/* 594 */         throw loginException;
/*     */       } 
/* 596 */       throw loginException;
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
/*     */   public void logout() throws LoginException {
/* 622 */     if (this.subject == null) {
/* 623 */       throw new LoginException(
/* 624 */           ResourcesMgr.getString("null.subject.logout.called.before.login"));
/*     */     }
/*     */ 
/*     */     
/* 628 */     invokePriv("logout");
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
/*     */   public Subject getSubject() {
/* 646 */     if (!this.loginSucceeded && !this.subjectProvided)
/* 647 */       return null; 
/* 648 */     return this.subject;
/*     */   }
/*     */   
/*     */   private void clearState() {
/* 652 */     this.moduleIndex = 0;
/* 653 */     this.firstError = null;
/* 654 */     this.firstRequiredError = null;
/* 655 */     this.success = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwException(LoginException paramLoginException1, LoginException paramLoginException2) throws LoginException {
/* 662 */     clearState();
/*     */ 
/*     */     
/* 665 */     LoginException loginException = (paramLoginException1 != null) ? paramLoginException1 : paramLoginException2;
/* 666 */     throw loginException;
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
/*     */   private void invokePriv(final String methodName) throws LoginException {
/*     */     try {
/* 680 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */             public Void run() throws LoginException {
/* 682 */               LoginContext.this.invoke(methodName);
/* 683 */               return null;
/*     */             }
/*     */           },  this.creatorAcc);
/* 686 */     } catch (PrivilegedActionException privilegedActionException) {
/* 687 */       throw (LoginException)privilegedActionException.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void invoke(String paramString) throws LoginException {
/* 696 */     for (int i = this.moduleIndex; i < this.moduleStack.length; i++, this.moduleIndex++) {
/*     */       
/*     */       try {
/* 699 */         byte b = 0;
/* 700 */         Method[] arrayOfMethod = null;
/*     */         
/* 702 */         if ((this.moduleStack[i]).module != null) {
/* 703 */           arrayOfMethod = (this.moduleStack[i]).module.getClass().getMethods();
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 710 */           Class<?> clazz = Class.forName((this.moduleStack[i]).entry
/* 711 */               .getLoginModuleName(), true, this.contextClassLoader);
/*     */ 
/*     */ 
/*     */           
/* 715 */           Constructor<?> constructor = clazz.getConstructor(PARAMS);
/* 716 */           Object[] arrayOfObject1 = new Object[0];
/* 717 */           (this.moduleStack[i]).module = constructor.newInstance(arrayOfObject1);
/*     */ 
/*     */           
/* 720 */           arrayOfMethod = (this.moduleStack[i]).module.getClass().getMethods();
/* 721 */           for (b = 0; b < arrayOfMethod.length && 
/* 722 */             !arrayOfMethod[b].getName().equals("initialize"); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 730 */           Object[] arrayOfObject2 = { this.subject, this.callbackHandler, this.state, (this.moduleStack[i]).entry.getOptions() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 736 */           arrayOfMethod[b].invoke((this.moduleStack[i]).module, arrayOfObject2);
/*     */         } 
/*     */ 
/*     */         
/* 740 */         for (b = 0; b < arrayOfMethod.length && 
/* 741 */           !arrayOfMethod[b].getName().equals(paramString); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 747 */         Object[] arrayOfObject = new Object[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 755 */         boolean bool = ((Boolean)arrayOfMethod[b].invoke((this.moduleStack[i]).module, arrayOfObject)).booleanValue();
/*     */         
/* 757 */         if (bool == true) {
/*     */ 
/*     */           
/* 760 */           if (!paramString.equals("abort") && 
/* 761 */             !paramString.equals("logout") && (this.moduleStack[i]).entry
/* 762 */             .getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT && this.firstRequiredError == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 767 */             clearState();
/*     */             
/* 769 */             if (debug != null) {
/* 770 */               debug.println(paramString + " SUFFICIENT success");
/*     */             }
/*     */             return;
/*     */           } 
/* 774 */           if (debug != null)
/* 775 */             debug.println(paramString + " success"); 
/* 776 */           this.success = true;
/*     */         }
/* 778 */         else if (debug != null) {
/* 779 */           debug.println(paramString + " ignored");
/*     */         }
/*     */       
/* 782 */       } catch (NoSuchMethodException noSuchMethodException) {
/*     */         
/* 784 */         MessageFormat messageFormat = new MessageFormat(ResourcesMgr.getString("unable.to.instantiate.LoginModule.module.because.it.does.not.provide.a.no.argument.constructor"));
/* 785 */         Object[] arrayOfObject = { (this.moduleStack[i]).entry.getLoginModuleName() };
/* 786 */         throwException(null, new LoginException(messageFormat.format(arrayOfObject)));
/* 787 */       } catch (InstantiationException instantiationException) {
/* 788 */         throwException(null, new LoginException(
/* 789 */               ResourcesMgr.getString("unable.to.instantiate.LoginModule.") + instantiationException
/* 790 */               .getMessage()));
/* 791 */       } catch (ClassNotFoundException classNotFoundException) {
/* 792 */         throwException(null, new LoginException(
/* 793 */               ResourcesMgr.getString("unable.to.find.LoginModule.class.") + classNotFoundException
/* 794 */               .getMessage()));
/* 795 */       } catch (IllegalAccessException illegalAccessException) {
/* 796 */         throwException(null, new LoginException(
/* 797 */               ResourcesMgr.getString("unable.to.access.LoginModule.") + illegalAccessException
/* 798 */               .getMessage()));
/* 799 */       } catch (InvocationTargetException invocationTargetException) {
/*     */         LoginException loginException;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 805 */         if (invocationTargetException.getCause() instanceof PendingException && paramString
/* 806 */           .equals("login"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 829 */           throw (PendingException)invocationTargetException.getCause();
/*     */         }
/* 831 */         if (invocationTargetException.getCause() instanceof LoginException) {
/*     */           
/* 833 */           loginException = (LoginException)invocationTargetException.getCause();
/*     */         }
/* 835 */         else if (invocationTargetException.getCause() instanceof SecurityException) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 840 */           loginException = new LoginException("Security Exception");
/* 841 */           loginException.initCause(new SecurityException());
/* 842 */           if (debug != null) {
/* 843 */             debug
/* 844 */               .println("original security exception with detail msg replaced by new exception with empty detail msg");
/*     */             
/* 846 */             debug.println("original security exception: " + invocationTargetException
/* 847 */                 .getCause().toString());
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 852 */           StringWriter stringWriter = new StringWriter();
/* 853 */           invocationTargetException.getCause()
/* 854 */             .printStackTrace(new PrintWriter(stringWriter));
/* 855 */           stringWriter.flush();
/* 856 */           loginException = new LoginException(stringWriter.toString());
/*     */         } 
/*     */         
/* 859 */         if ((this.moduleStack[i]).entry.getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.REQUISITE) {
/*     */ 
/*     */           
/* 862 */           if (debug != null) {
/* 863 */             debug.println(paramString + " REQUISITE failure");
/*     */           }
/*     */           
/* 866 */           if (paramString.equals("abort") || paramString
/* 867 */             .equals("logout")) {
/* 868 */             if (this.firstRequiredError == null)
/* 869 */               this.firstRequiredError = loginException; 
/*     */           } else {
/* 871 */             throwException(this.firstRequiredError, loginException);
/*     */           }
/*     */         
/* 874 */         } else if ((this.moduleStack[i]).entry.getControlFlag() == AppConfigurationEntry.LoginModuleControlFlag.REQUIRED) {
/*     */ 
/*     */           
/* 877 */           if (debug != null) {
/* 878 */             debug.println(paramString + " REQUIRED failure");
/*     */           }
/*     */           
/* 881 */           if (this.firstRequiredError == null) {
/* 882 */             this.firstRequiredError = loginException;
/*     */           }
/*     */         } else {
/*     */           
/* 886 */           if (debug != null) {
/* 887 */             debug.println(paramString + " OPTIONAL failure");
/*     */           }
/*     */           
/* 890 */           if (this.firstError == null) {
/* 891 */             this.firstError = loginException;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 897 */     if (this.firstRequiredError != null) {
/*     */       
/* 899 */       throwException(this.firstRequiredError, null);
/* 900 */     } else if (!this.success && this.firstError != null) {
/*     */       
/* 902 */       throwException(this.firstError, null);
/* 903 */     } else if (!this.success) {
/*     */       
/* 905 */       throwException(new LoginException(
/* 906 */             ResourcesMgr.getString("Login.Failure.all.modules.ignored")), null);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 911 */       clearState();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SecureCallbackHandler
/*     */     implements CallbackHandler
/*     */   {
/*     */     private final AccessControlContext acc;
/*     */ 
/*     */     
/*     */     private final CallbackHandler ch;
/*     */ 
/*     */     
/*     */     SecureCallbackHandler(AccessControlContext param1AccessControlContext, CallbackHandler param1CallbackHandler) {
/* 928 */       this.acc = param1AccessControlContext;
/* 929 */       this.ch = param1CallbackHandler;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
/*     */       try {
/* 936 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */             {
/*     */               public Void run() throws IOException, UnsupportedCallbackException {
/* 939 */                 LoginContext.SecureCallbackHandler.this.ch.handle(callbacks);
/* 940 */                 return null;
/*     */               }
/*     */             },  this.acc);
/* 943 */       } catch (PrivilegedActionException privilegedActionException) {
/* 944 */         if (privilegedActionException.getException() instanceof IOException) {
/* 945 */           throw (IOException)privilegedActionException.getException();
/*     */         }
/* 947 */         throw (UnsupportedCallbackException)privilegedActionException.getException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ModuleInfo
/*     */   {
/*     */     AppConfigurationEntry entry;
/*     */     
/*     */     Object module;
/*     */ 
/*     */     
/*     */     ModuleInfo(AppConfigurationEntry param1AppConfigurationEntry, Object param1Object) {
/* 962 */       this.entry = param1AppConfigurationEntry;
/* 963 */       this.module = param1Object;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/login/LoginContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */