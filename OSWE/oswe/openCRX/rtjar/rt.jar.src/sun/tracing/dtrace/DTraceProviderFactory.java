/*     */ package sun.tracing.dtrace;
/*     */ 
/*     */ import com.sun.tracing.Provider;
/*     */ import com.sun.tracing.ProviderFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DTraceProviderFactory
/*     */   extends ProviderFactory
/*     */ {
/*     */   public <T extends Provider> T createProvider(Class<T> paramClass) {
/*  79 */     DTraceProvider dTraceProvider = new DTraceProvider(paramClass);
/*  80 */     T t = (T)dTraceProvider.newProxyInstance();
/*  81 */     dTraceProvider.setProxy(t);
/*  82 */     dTraceProvider.init();
/*  83 */     new Activation(dTraceProvider.getModuleName(), new DTraceProvider[] { dTraceProvider });
/*  84 */     return t;
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
/*     */   public Map<Class<? extends Provider>, Provider> createProviders(Set<Class<? extends Provider>> paramSet, String paramString) {
/* 121 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 123 */     HashSet<DTraceProvider> hashSet = new HashSet();
/* 124 */     for (Class<? extends Provider> clazz : paramSet) {
/* 125 */       DTraceProvider dTraceProvider = new DTraceProvider(clazz);
/* 126 */       hashSet.add(dTraceProvider);
/* 127 */       hashMap.put(clazz, dTraceProvider.newProxyInstance());
/*     */     } 
/* 129 */     new Activation(paramString, hashSet.<DTraceProvider>toArray(new DTraceProvider[0]));
/* 130 */     return (Map)hashMap;
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
/*     */   public static boolean isSupported() {
/*     */     try {
/* 145 */       SecurityManager securityManager = System.getSecurityManager();
/* 146 */       if (securityManager != null) {
/* 147 */         RuntimePermission runtimePermission = new RuntimePermission("com.sun.tracing.dtrace.createProvider");
/*     */         
/* 149 */         securityManager.checkPermission(runtimePermission);
/*     */       } 
/* 151 */       return JVM.isSupported();
/* 152 */     } catch (SecurityException securityException) {
/* 153 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/dtrace/DTraceProviderFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */