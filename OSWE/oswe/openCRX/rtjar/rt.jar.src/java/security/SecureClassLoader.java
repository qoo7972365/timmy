/*     */ package java.security;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecureClassLoader
/*     */   extends ClassLoader
/*     */ {
/*     */   private final boolean initialized;
/*  52 */   private final HashMap<CodeSource, ProtectionDomain> pdcache = new HashMap<>(11);
/*     */ 
/*     */   
/*  55 */   private static final Debug debug = Debug.getInstance("scl");
/*     */   
/*     */   static {
/*  58 */     ClassLoader.registerAsParallelCapable();
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
/*     */   protected SecureClassLoader(ClassLoader paramClassLoader) {
/*  76 */     super(paramClassLoader);
/*     */     
/*  78 */     SecurityManager securityManager = System.getSecurityManager();
/*  79 */     if (securityManager != null) {
/*  80 */       securityManager.checkCreateClassLoader();
/*     */     }
/*  82 */     this.initialized = true;
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
/*     */   protected SecureClassLoader() {
/* 101 */     SecurityManager securityManager = System.getSecurityManager();
/* 102 */     if (securityManager != null) {
/* 103 */       securityManager.checkCreateClassLoader();
/*     */     }
/* 105 */     this.initialized = true;
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
/*     */   protected final Class<?> defineClass(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, CodeSource paramCodeSource) {
/* 142 */     return defineClass(paramString, paramArrayOfbyte, paramInt1, paramInt2, getProtectionDomain(paramCodeSource));
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
/*     */   protected final Class<?> defineClass(String paramString, ByteBuffer paramByteBuffer, CodeSource paramCodeSource) {
/* 174 */     return defineClass(paramString, paramByteBuffer, getProtectionDomain(paramCodeSource));
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
/*     */   protected PermissionCollection getPermissions(CodeSource paramCodeSource) {
/* 191 */     check();
/* 192 */     return new Permissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProtectionDomain getProtectionDomain(CodeSource paramCodeSource) {
/* 199 */     if (paramCodeSource == null) {
/* 200 */       return null;
/*     */     }
/* 202 */     ProtectionDomain protectionDomain = null;
/* 203 */     synchronized (this.pdcache) {
/* 204 */       protectionDomain = this.pdcache.get(paramCodeSource);
/* 205 */       if (protectionDomain == null) {
/* 206 */         PermissionCollection permissionCollection = getPermissions(paramCodeSource);
/* 207 */         protectionDomain = new ProtectionDomain(paramCodeSource, permissionCollection, this, null);
/* 208 */         this.pdcache.put(paramCodeSource, protectionDomain);
/* 209 */         if (debug != null) {
/* 210 */           debug.println(" getPermissions " + protectionDomain);
/* 211 */           debug.println("");
/*     */         } 
/*     */       } 
/*     */     } 
/* 215 */     return protectionDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void check() {
/* 222 */     if (!this.initialized)
/* 223 */       throw new SecurityException("ClassLoader object not initialized"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/SecureClassLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */