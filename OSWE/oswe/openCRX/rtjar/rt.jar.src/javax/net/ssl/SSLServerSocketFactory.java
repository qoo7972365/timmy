/*     */ package javax.net.ssl;
/*     */ 
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.net.ServerSocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SSLServerSocketFactory
/*     */   extends ServerSocketFactory
/*     */ {
/*     */   private static SSLServerSocketFactory theFactory;
/*     */   private static boolean propertyChecked;
/*     */   
/*     */   private static void log(String paramString) {
/*  52 */     if (SSLSocketFactory.DEBUG) {
/*  53 */       System.out.println(paramString);
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
/*     */   public static synchronized ServerSocketFactory getDefault() {
/*  79 */     if (theFactory != null) {
/*  80 */       return theFactory;
/*     */     }
/*     */     
/*  83 */     if (!propertyChecked) {
/*  84 */       propertyChecked = true;
/*     */       
/*  86 */       String str = SSLSocketFactory.getSecurityProperty("ssl.ServerSocketFactory.provider");
/*  87 */       if (str != null) {
/*  88 */         log("setting up default SSLServerSocketFactory");
/*     */         try {
/*  90 */           Class<?> clazz = null;
/*     */           try {
/*  92 */             clazz = Class.forName(str);
/*  93 */           } catch (ClassNotFoundException classNotFoundException) {
/*  94 */             ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/*  95 */             if (classLoader != null) {
/*  96 */               clazz = classLoader.loadClass(str);
/*     */             }
/*     */           } 
/*  99 */           log("class " + str + " is loaded");
/* 100 */           SSLServerSocketFactory sSLServerSocketFactory = (SSLServerSocketFactory)clazz.newInstance();
/* 101 */           log("instantiated an instance of class " + str);
/* 102 */           theFactory = sSLServerSocketFactory;
/* 103 */           return sSLServerSocketFactory;
/* 104 */         } catch (Exception exception) {
/* 105 */           log("SSLServerSocketFactory instantiation failed: " + exception);
/* 106 */           theFactory = new DefaultSSLServerSocketFactory(exception);
/* 107 */           return theFactory;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 113 */       return SSLContext.getDefault().getServerSocketFactory();
/* 114 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 115 */       return new DefaultSSLServerSocketFactory(noSuchAlgorithmException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract String[] getDefaultCipherSuites();
/*     */   
/*     */   public abstract String[] getSupportedCipherSuites();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SSLServerSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */