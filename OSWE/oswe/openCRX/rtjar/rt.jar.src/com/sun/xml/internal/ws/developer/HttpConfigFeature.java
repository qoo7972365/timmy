/*     */ package com.sun.xml.internal.ws.developer;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.CookieHandler;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public final class HttpConfigFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.java.net/features/http-config";
/*     */   private static final Constructor cookieManagerConstructor;
/*     */   private static final Object cookiePolicy;
/*     */   private final CookieHandler cookieJar;
/*     */   
/*     */   static {
/*     */     Constructor<?> tempConstructor;
/*     */     Object tempPolicy;
/*     */     try {
/*  59 */       Class<?> policyClass = Class.forName("java.net.CookiePolicy");
/*  60 */       Class<?> storeClass = Class.forName("java.net.CookieStore");
/*  61 */       tempConstructor = Class.forName("java.net.CookieManager").getConstructor(new Class[] { storeClass, policyClass });
/*     */ 
/*     */       
/*  64 */       tempPolicy = policyClass.getField("ACCEPT_ALL").get(null);
/*  65 */     } catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/*  71 */         Class<?> policyClass = Class.forName("com.sun.xml.internal.ws.transport.http.client.CookiePolicy");
/*  72 */         Class<?> storeClass = Class.forName("com.sun.xml.internal.ws.transport.http.client.CookieStore");
/*  73 */         tempConstructor = Class.forName("com.sun.xml.internal.ws.transport.http.client.CookieManager").getConstructor(new Class[] { storeClass, policyClass });
/*     */ 
/*     */         
/*  76 */         tempPolicy = policyClass.getField("ACCEPT_ALL").get(null);
/*  77 */       } catch (Exception ce) {
/*  78 */         throw new WebServiceException(ce);
/*     */       } 
/*     */     } 
/*  81 */     cookieManagerConstructor = tempConstructor;
/*  82 */     cookiePolicy = tempPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpConfigFeature() {
/*  88 */     this(getInternalCookieHandler());
/*     */   }
/*     */   
/*     */   public HttpConfigFeature(CookieHandler cookieJar) {
/*  92 */     this.enabled = true;
/*  93 */     this.cookieJar = cookieJar;
/*     */   }
/*     */   
/*     */   private static CookieHandler getInternalCookieHandler() {
/*     */     try {
/*  98 */       return cookieManagerConstructor.newInstance(new Object[] { null, cookiePolicy });
/*  99 */     } catch (Exception e) {
/* 100 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getID() {
/* 105 */     return "http://jax-ws.java.net/features/http-config";
/*     */   }
/*     */   
/*     */   public CookieHandler getCookieHandler() {
/* 109 */     return this.cookieJar;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/HttpConfigFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */