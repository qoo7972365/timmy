/*     */ package com.sun.xml.internal.ws.api.client;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.developer.WSBindingProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ServiceInterceptor
/*     */ {
/*     */   public List<WebServiceFeature> preCreateBinding(@NotNull WSPortInfo port, @Nullable Class<?> serviceEndpointInterface, @NotNull WSFeatureList defaultFeatures) {
/*  78 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCreateProxy(@NotNull WSBindingProvider bp, @NotNull Class<?> serviceEndpointInterface) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCreateDispatch(@NotNull WSBindingProvider bp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServiceInterceptor aggregate(ServiceInterceptor... interceptors) {
/* 104 */     if (interceptors.length == 1)
/* 105 */       return interceptors[0]; 
/* 106 */     return new ServiceInterceptor() {
/*     */         public List<WebServiceFeature> preCreateBinding(@NotNull WSPortInfo port, @Nullable Class<?> portInterface, @NotNull WSFeatureList defaultFeatures) {
/* 108 */           List<WebServiceFeature> r = new ArrayList<>();
/* 109 */           for (ServiceInterceptor si : interceptors)
/* 110 */             r.addAll(si.preCreateBinding(port, portInterface, defaultFeatures)); 
/* 111 */           return r;
/*     */         }
/*     */         
/*     */         public void postCreateProxy(@NotNull WSBindingProvider bp, @NotNull Class<?> serviceEndpointInterface) {
/* 115 */           for (ServiceInterceptor si : interceptors)
/* 116 */             si.postCreateProxy(bp, serviceEndpointInterface); 
/*     */         }
/*     */         
/*     */         public void postCreateDispatch(@NotNull WSBindingProvider bp) {
/* 120 */           for (ServiceInterceptor si : interceptors)
/* 121 */             si.postCreateDispatch(bp); 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/client/ServiceInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */