/*     */ package com.sun.xml.internal.ws.api.client;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ public abstract class ServiceInterceptorFactory
/*     */ {
/*     */   public abstract ServiceInterceptor create(@NotNull WSService paramWSService);
/*     */   
/*     */   @NotNull
/*     */   public static ServiceInterceptor load(@NotNull WSService service, @Nullable ClassLoader cl) {
/*  66 */     List<ServiceInterceptor> l = new ArrayList<>();
/*     */ 
/*     */     
/*  69 */     for (ServiceInterceptorFactory f : ServiceFinder.find(ServiceInterceptorFactory.class)) {
/*  70 */       l.add(f.create(service));
/*     */     }
/*     */     
/*  73 */     for (ServiceInterceptorFactory f : threadLocalFactories.get()) {
/*  74 */       l.add(f.create(service));
/*     */     }
/*  76 */     return ServiceInterceptor.aggregate(l.<ServiceInterceptor>toArray(new ServiceInterceptor[l.size()]));
/*     */   }
/*     */   
/*  79 */   private static ThreadLocal<Set<ServiceInterceptorFactory>> threadLocalFactories = new ThreadLocal<Set<ServiceInterceptorFactory>>() {
/*     */       protected Set<ServiceInterceptorFactory> initialValue() {
/*  81 */         return new HashSet<>();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean registerForThread(ServiceInterceptorFactory factory) {
/*  93 */     return ((Set<ServiceInterceptorFactory>)threadLocalFactories.get()).add(factory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean unregisterForThread(ServiceInterceptorFactory factory) {
/* 100 */     return ((Set)threadLocalFactories.get()).remove(factory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/client/ServiceInterceptorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */