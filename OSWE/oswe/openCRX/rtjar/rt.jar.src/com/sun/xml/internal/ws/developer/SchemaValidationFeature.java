/*     */ package com.sun.xml.internal.ws.developer;
/*     */ 
/*     */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*     */ import com.sun.org.glassfish.gmbal.ManagedData;
/*     */ import com.sun.xml.internal.ws.api.FeatureConstructor;
/*     */ import com.sun.xml.internal.ws.server.DraconianValidationErrorHandler;
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
/*     */ @ManagedData
/*     */ public class SchemaValidationFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://jax-ws.dev.java.net/features/schema-validation";
/*     */   private final Class<? extends ValidationErrorHandler> clazz;
/*     */   private final boolean inbound;
/*     */   private final boolean outbound;
/*     */   
/*     */   public SchemaValidationFeature() {
/*  55 */     this(true, true, (Class)DraconianValidationErrorHandler.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaValidationFeature(Class<? extends ValidationErrorHandler> clazz) {
/*  63 */     this(true, true, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaValidationFeature(boolean inbound, boolean outbound) {
/*  70 */     this(inbound, outbound, (Class)DraconianValidationErrorHandler.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @FeatureConstructor({"inbound", "outbound", "handler"})
/*     */   public SchemaValidationFeature(boolean inbound, boolean outbound, Class<? extends ValidationErrorHandler> clazz) {
/*  78 */     this.enabled = true;
/*  79 */     this.inbound = inbound;
/*  80 */     this.outbound = outbound;
/*  81 */     this.clazz = clazz;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/*  87 */     return "http://jax-ws.dev.java.net/features/schema-validation";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public Class<? extends ValidationErrorHandler> getErrorHandler() {
/*  97 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInbound() {
/* 106 */     return this.inbound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutbound() {
/* 115 */     return this.outbound;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/developer/SchemaValidationFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */