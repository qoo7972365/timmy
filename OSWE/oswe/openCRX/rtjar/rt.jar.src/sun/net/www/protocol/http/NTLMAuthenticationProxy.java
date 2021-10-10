/*     */ package sun.net.www.protocol.http;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.URL;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NTLMAuthenticationProxy
/*     */ {
/*     */   private static Method supportsTA;
/*     */   private static Method isTrustedSite;
/*     */   private static final String clazzStr = "sun.net.www.protocol.http.ntlm.NTLMAuthentication";
/*     */   private static final String supportsTAStr = "supportsTransparentAuth";
/*     */   private static final String isTrustedSiteStr = "isTrustedSite";
/*  44 */   static final NTLMAuthenticationProxy proxy = tryLoadNTLMAuthentication();
/*  45 */   static final boolean supported = (proxy != null);
/*  46 */   static final boolean supportsTransparentAuth = supported ? supportsTransparentAuth() : false;
/*     */   
/*     */   private final Constructor<? extends AuthenticationInfo> threeArgCtr;
/*     */   
/*     */   private final Constructor<? extends AuthenticationInfo> fiveArgCtr;
/*     */   
/*     */   private NTLMAuthenticationProxy(Constructor<? extends AuthenticationInfo> paramConstructor1, Constructor<? extends AuthenticationInfo> paramConstructor2) {
/*  53 */     this.threeArgCtr = paramConstructor1;
/*  54 */     this.fiveArgCtr = paramConstructor2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AuthenticationInfo create(boolean paramBoolean, URL paramURL, PasswordAuthentication paramPasswordAuthentication) {
/*     */     try {
/*  62 */       return this.threeArgCtr.newInstance(new Object[] { Boolean.valueOf(paramBoolean), paramURL, paramPasswordAuthentication });
/*  63 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*  64 */       finest(reflectiveOperationException);
/*     */ 
/*     */       
/*  67 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   AuthenticationInfo create(boolean paramBoolean, String paramString, int paramInt, PasswordAuthentication paramPasswordAuthentication) {
/*     */     try {
/*  75 */       return this.fiveArgCtr.newInstance(new Object[] { Boolean.valueOf(paramBoolean), paramString, Integer.valueOf(paramInt), paramPasswordAuthentication });
/*  76 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*  77 */       finest(reflectiveOperationException);
/*     */ 
/*     */       
/*  80 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean supportsTransparentAuth() {
/*     */     try {
/*  89 */       return ((Boolean)supportsTA.invoke(null, new Object[0])).booleanValue();
/*  90 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*  91 */       finest(reflectiveOperationException);
/*     */ 
/*     */       
/*  94 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isTrustedSite(URL paramURL) {
/*     */     try {
/* 102 */       return ((Boolean)isTrustedSite.invoke(null, new Object[] { paramURL })).booleanValue();
/* 103 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 104 */       finest(reflectiveOperationException);
/*     */ 
/*     */       
/* 107 */       return false;
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
/*     */   private static NTLMAuthenticationProxy tryLoadNTLMAuthentication() {
/*     */     try {
/* 120 */       Class<?> clazz = Class.forName("sun.net.www.protocol.http.ntlm.NTLMAuthentication", true, null);
/* 121 */       if (clazz != null) {
/* 122 */         Constructor<?> constructor1 = clazz.getConstructor(new Class[] { boolean.class, URL.class, PasswordAuthentication.class });
/*     */ 
/*     */         
/* 125 */         Constructor<?> constructor2 = clazz.getConstructor(new Class[] { boolean.class, String.class, int.class, PasswordAuthentication.class });
/*     */ 
/*     */ 
/*     */         
/* 129 */         supportsTA = clazz.getDeclaredMethod("supportsTransparentAuth", new Class[0]);
/* 130 */         isTrustedSite = clazz.getDeclaredMethod("isTrustedSite", new Class[] { URL.class });
/* 131 */         return new NTLMAuthenticationProxy((Constructor)constructor1, (Constructor)constructor2);
/*     */       }
/*     */     
/* 134 */     } catch (ClassNotFoundException classNotFoundException) {
/* 135 */       finest(classNotFoundException);
/* 136 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 137 */       throw new AssertionError(reflectiveOperationException);
/*     */     } 
/*     */     
/* 140 */     return null;
/*     */   }
/*     */   
/*     */   static void finest(Exception paramException) {
/* 144 */     PlatformLogger platformLogger = HttpURLConnection.getHttpLogger();
/* 145 */     if (platformLogger.isLoggable(PlatformLogger.Level.FINEST))
/* 146 */       platformLogger.finest("NTLMAuthenticationProxy: " + paramException); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/http/NTLMAuthenticationProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */