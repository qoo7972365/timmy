/*     */ package sun.net.ftp;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import sun.net.ftp.impl.DefaultFtpClientProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FtpClientProvider
/*     */ {
/*  48 */   private static final Object lock = new Object();
/*  49 */   private static FtpClientProvider provider = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FtpClientProvider() {
/*  58 */     SecurityManager securityManager = System.getSecurityManager();
/*  59 */     if (securityManager != null) {
/*  60 */       securityManager.checkPermission(new RuntimePermission("ftpClientProvider"));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean loadProviderFromProperty() {
/*  65 */     String str = System.getProperty("sun.net.ftpClientProvider");
/*  66 */     if (str == null) {
/*  67 */       return false;
/*     */     }
/*     */     try {
/*  70 */       Class<?> clazz = Class.forName(str, true, null);
/*  71 */       provider = (FtpClientProvider)clazz.newInstance();
/*  72 */       return true;
/*  73 */     } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SecurityException classNotFoundException) {
/*     */ 
/*     */ 
/*     */       
/*  77 */       throw new ServiceConfigurationError(classNotFoundException.toString());
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
/*     */   private static boolean loadProviderAsService() {
/*  98 */     return false;
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
/*     */   public static FtpClientProvider provider() {
/* 137 */     synchronized (lock) {
/* 138 */       if (provider != null) {
/* 139 */         return provider;
/*     */       }
/* 141 */       return AccessController.<FtpClientProvider>doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run()
/*     */             {
/* 145 */               if (FtpClientProvider.loadProviderFromProperty()) {
/* 146 */                 return FtpClientProvider.provider;
/*     */               }
/* 148 */               if (FtpClientProvider.loadProviderAsService()) {
/* 149 */                 return FtpClientProvider.provider;
/*     */               }
/* 151 */               FtpClientProvider.provider = new DefaultFtpClientProvider();
/* 152 */               return FtpClientProvider.provider;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract FtpClient createFtpClient();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/ftp/FtpClientProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */