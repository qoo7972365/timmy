/*     */ package javax.security.auth.login;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Objects;
/*     */ import javax.security.auth.AuthPermission;
/*     */ import sun.security.jca.GetInstance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Configuration
/*     */ {
/*     */   private static Configuration configuration;
/* 193 */   private final AccessControlContext acc = AccessController.getContext();
/*     */   
/*     */   private static void checkPermission(String paramString) {
/* 196 */     SecurityManager securityManager = System.getSecurityManager();
/* 197 */     if (securityManager != null) {
/* 198 */       securityManager.checkPermission(new AuthPermission("createLoginConfiguration." + paramString));
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
/*     */   public static Configuration getConfiguration() {
/* 226 */     SecurityManager securityManager = System.getSecurityManager();
/* 227 */     if (securityManager != null) {
/* 228 */       securityManager.checkPermission(new AuthPermission("getLoginConfiguration"));
/*     */     }
/* 230 */     synchronized (Configuration.class) {
/* 231 */       if (configuration == null) {
/* 232 */         String str = null;
/*     */         
/* 234 */         str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */               public String run() {
/* 236 */                 return 
/* 237 */                   Security.getProperty("login.configuration.provider");
/*     */               }
/*     */             });
/* 240 */         if (str == null) {
/* 241 */           str = "sun.security.provider.ConfigFile";
/*     */         }
/*     */         
/*     */         try {
/* 245 */           final String finalClass = str;
/* 246 */           final Configuration untrustedImpl = AccessController.<Configuration>doPrivileged(new PrivilegedExceptionAction<Configuration>()
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 public Configuration run() throws ClassNotFoundException, InstantiationException, IllegalAccessException
/*     */                 {
/* 254 */                   Class<? extends Configuration> clazz = Class.forName(finalClass, false, Thread.currentThread().getContextClassLoader()).asSubclass(Configuration.class);
/* 255 */                   return clazz.newInstance();
/*     */                 }
/*     */               });
/* 258 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */               {
/*     */                 public Void run() {
/* 261 */                   Configuration.setConfiguration(untrustedImpl);
/* 262 */                   return null;
/*     */                 }
/* 264 */               },  Objects.<AccessControlContext>requireNonNull(configuration.acc));
/*     */         }
/* 266 */         catch (PrivilegedActionException privilegedActionException) {
/* 267 */           Exception exception = privilegedActionException.getException();
/* 268 */           if (exception instanceof InstantiationException) {
/* 269 */             throw (SecurityException)(new SecurityException("Configuration error:" + exception
/*     */ 
/*     */                 
/* 272 */                 .getCause().getMessage() + "\n"))
/* 273 */               .initCause(exception.getCause());
/*     */           }
/* 275 */           throw (SecurityException)(new SecurityException("Configuration error: " + exception
/*     */ 
/*     */               
/* 278 */               .toString() + "\n"))
/* 279 */             .initCause(exception);
/*     */         } 
/*     */       } 
/*     */       
/* 283 */       return configuration;
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
/*     */   public static void setConfiguration(Configuration paramConfiguration) {
/* 300 */     SecurityManager securityManager = System.getSecurityManager();
/* 301 */     if (securityManager != null)
/* 302 */       securityManager.checkPermission(new AuthPermission("setLoginConfiguration")); 
/* 303 */     configuration = paramConfiguration;
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
/*     */   public static Configuration getInstance(String paramString, Parameters paramParameters) throws NoSuchAlgorithmException {
/* 347 */     checkPermission(paramString);
/*     */     
/*     */     try {
/* 350 */       GetInstance.Instance instance = GetInstance.getInstance("Configuration", ConfigurationSpi.class, paramString, paramParameters);
/*     */ 
/*     */ 
/*     */       
/* 354 */       return new ConfigDelegate((ConfigurationSpi)instance.impl, instance.provider, paramString, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 358 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 359 */       return handleException(noSuchAlgorithmException);
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
/*     */   public static Configuration getInstance(String paramString1, Parameters paramParameters, String paramString2) throws NoSuchProviderException, NoSuchAlgorithmException {
/* 411 */     if (paramString2 == null || paramString2.length() == 0) {
/* 412 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 415 */     checkPermission(paramString1);
/*     */     
/*     */     try {
/* 418 */       GetInstance.Instance instance = GetInstance.getInstance("Configuration", ConfigurationSpi.class, paramString1, paramParameters, paramString2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 423 */       return new ConfigDelegate((ConfigurationSpi)instance.impl, instance.provider, paramString1, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 427 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 428 */       return handleException(noSuchAlgorithmException);
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
/*     */   public static Configuration getInstance(String paramString, Parameters paramParameters, Provider paramProvider) throws NoSuchAlgorithmException {
/* 473 */     if (paramProvider == null) {
/* 474 */       throw new IllegalArgumentException("missing provider");
/*     */     }
/*     */     
/* 477 */     checkPermission(paramString);
/*     */     
/*     */     try {
/* 480 */       GetInstance.Instance instance = GetInstance.getInstance("Configuration", ConfigurationSpi.class, paramString, paramParameters, paramProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 485 */       return new ConfigDelegate((ConfigurationSpi)instance.impl, instance.provider, paramString, paramParameters);
/*     */ 
/*     */     
/*     */     }
/* 489 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 490 */       return handleException(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Configuration handleException(NoSuchAlgorithmException paramNoSuchAlgorithmException) throws NoSuchAlgorithmException {
/* 496 */     Throwable throwable = paramNoSuchAlgorithmException.getCause();
/* 497 */     if (throwable instanceof IllegalArgumentException) {
/* 498 */       throw (IllegalArgumentException)throwable;
/*     */     }
/* 500 */     throw paramNoSuchAlgorithmException;
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
/*     */   public Provider getProvider() {
/* 515 */     return null;
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
/*     */   public String getType() {
/* 530 */     return null;
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
/*     */   public Parameters getParameters() {
/* 545 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AppConfigurationEntry[] getAppConfigurationEntry(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ConfigDelegate
/*     */     extends Configuration
/*     */   {
/*     */     private ConfigurationSpi spi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Provider p;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String type;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Configuration.Parameters params;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ConfigDelegate(ConfigurationSpi param1ConfigurationSpi, Provider param1Provider, String param1String, Configuration.Parameters param1Parameters) {
/* 593 */       this.spi = param1ConfigurationSpi;
/* 594 */       this.p = param1Provider;
/* 595 */       this.type = param1String;
/* 596 */       this.params = param1Parameters;
/*     */     }
/*     */     public String getType() {
/* 599 */       return this.type;
/*     */     } public Configuration.Parameters getParameters() {
/* 601 */       return this.params;
/*     */     } public Provider getProvider() {
/* 603 */       return this.p;
/*     */     }
/*     */     public AppConfigurationEntry[] getAppConfigurationEntry(String param1String) {
/* 606 */       return this.spi.engineGetAppConfigurationEntry(param1String);
/*     */     }
/*     */     
/*     */     public void refresh() {
/* 610 */       this.spi.engineRefresh();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Parameters {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/login/Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */