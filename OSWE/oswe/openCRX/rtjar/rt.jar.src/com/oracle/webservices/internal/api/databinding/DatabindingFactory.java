/*     */ package com.oracle.webservices.internal.api.databinding;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabindingFactory
/*     */ {
/*     */   static final String ImplClass = "com.sun.xml.internal.ws.db.DatabindingFactoryImpl";
/*     */   
/*     */   public abstract Databinding.Builder createBuilder(Class<?> paramClass1, Class<?> paramClass2);
/*     */   
/*     */   public abstract Map<String, Object> properties();
/*     */   
/*     */   public static DatabindingFactory newInstance() {
/*     */     try {
/*  93 */       Class<?> cls = Class.forName("com.sun.xml.internal.ws.db.DatabindingFactoryImpl");
/*  94 */       return convertIfNecessary(cls);
/*  95 */     } catch (Exception e) {
/*  96 */       e.printStackTrace();
/*     */       
/*  98 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DatabindingFactory convertIfNecessary(Class<?> cls) throws InstantiationException, IllegalAccessException {
/* 103 */     return (DatabindingFactory)cls.newInstance();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/databinding/DatabindingFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */