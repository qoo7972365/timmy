/*     */ package com.sun.xml.internal.ws.api.databinding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.Databinding;
/*     */ import com.oracle.webservices.internal.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.internal.ws.db.DatabindingFactoryImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends DatabindingFactory
/*     */ {
/* 102 */   static final String ImplClass = DatabindingFactoryImpl.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Databinding createRuntime(DatabindingConfig paramDatabindingConfig);
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Map<String, Object> properties();
/*     */ 
/*     */ 
/*     */   
/*     */   public static DatabindingFactory newInstance() {
/*     */     try {
/* 116 */       Class<?> cls = Class.forName(ImplClass);
/* 117 */       return (DatabindingFactory)cls.newInstance();
/* 118 */     } catch (Exception e) {
/* 119 */       e.printStackTrace();
/*     */       
/* 121 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/databinding/DatabindingFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */