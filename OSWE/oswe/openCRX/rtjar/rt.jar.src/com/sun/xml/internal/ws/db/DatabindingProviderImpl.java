/*     */ package com.sun.xml.internal.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.Databinding;
/*     */ import com.oracle.webservices.internal.api.databinding.WSDLGenerator;
/*     */ import com.oracle.webservices.internal.api.databinding.WSDLResolver;
/*     */ import com.sun.xml.internal.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.internal.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingProvider;
/*     */ import java.io.File;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingProviderImpl
/*     */   implements DatabindingProvider
/*     */ {
/*     */   private static final String CachedDatabinding = "com.sun.xml.internal.ws.db.DatabindingProviderImpl";
/*     */   Map<String, Object> properties;
/*     */   
/*     */   public void init(Map<String, Object> p) {
/*  48 */     this.properties = p;
/*     */   }
/*     */   
/*     */   DatabindingImpl getCachedDatabindingImpl(DatabindingConfig config) {
/*  52 */     Object object = config.properties().get("com.sun.xml.internal.ws.db.DatabindingProviderImpl");
/*  53 */     return (object != null && object instanceof DatabindingImpl) ? (DatabindingImpl)object : null;
/*     */   }
/*     */   
/*     */   public Databinding create(DatabindingConfig config) {
/*  57 */     DatabindingImpl impl = getCachedDatabindingImpl(config);
/*  58 */     if (impl == null) {
/*  59 */       impl = new DatabindingImpl(this, config);
/*  60 */       config.properties().put("com.sun.xml.internal.ws.db.DatabindingProviderImpl", impl);
/*     */     } 
/*  62 */     return impl;
/*     */   }
/*     */   
/*     */   public WSDLGenerator wsdlGen(DatabindingConfig config) {
/*  66 */     DatabindingImpl impl = (DatabindingImpl)create(config);
/*  67 */     return new JaxwsWsdlGen(impl);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFor(String databindingMode) {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public static class JaxwsWsdlGen implements WSDLGenerator {
/*     */     DatabindingImpl databinding;
/*     */     WSDLGenInfo wsdlGenInfo;
/*     */     
/*     */     JaxwsWsdlGen(DatabindingImpl impl) {
/*  80 */       this.databinding = impl;
/*  81 */       this.wsdlGenInfo = new WSDLGenInfo();
/*     */     }
/*     */     
/*     */     public WSDLGenerator inlineSchema(boolean inline) {
/*  85 */       this.wsdlGenInfo.setInlineSchemas(inline);
/*  86 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public WSDLGenerator property(String name, Object value) {
/*  91 */       return this;
/*     */     }
/*     */     
/*     */     public void generate(WSDLResolver wsdlResolver) {
/*  95 */       this.wsdlGenInfo.setWsdlResolver(wsdlResolver);
/*  96 */       this.databinding.generateWSDL(this.wsdlGenInfo);
/*     */     }
/*     */ 
/*     */     
/*     */     public void generate(File outputDir, String name) {
/* 101 */       this.databinding.generateWSDL(this.wsdlGenInfo);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/DatabindingProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */