/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ import javax.management.openmbean.OpenType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MXBeanMapping
/*     */ {
/*     */   private final Type javaType;
/*     */   private final OpenType<?> openType;
/*     */   private final Class<?> openClass;
/*     */   
/*     */   protected MXBeanMapping(Type paramType, OpenType<?> paramOpenType) {
/* 133 */     if (paramType == null || paramOpenType == null)
/* 134 */       throw new NullPointerException("Null argument"); 
/* 135 */     this.javaType = paramType;
/* 136 */     this.openType = paramOpenType;
/* 137 */     this.openClass = makeOpenClass(paramType, paramOpenType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Type getJavaType() {
/* 145 */     return this.javaType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final OpenType<?> getOpenType() {
/* 153 */     return this.openType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Class<?> getOpenClass() {
/* 164 */     return this.openClass;
/*     */   }
/*     */   
/*     */   private static Class<?> makeOpenClass(Type paramType, OpenType<?> paramOpenType) {
/* 168 */     if (paramType instanceof Class && ((Class)paramType).isPrimitive())
/* 169 */       return (Class)paramType; 
/*     */     try {
/* 171 */       String str = paramOpenType.getClassName();
/* 172 */       return Class.forName(str, false, MXBeanMapping.class.getClassLoader());
/* 173 */     } catch (ClassNotFoundException classNotFoundException) {
/* 174 */       throw new RuntimeException(classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract Object fromOpenValue(Object paramObject) throws InvalidObjectException;
/*     */   
/*     */   public abstract Object toOpenValue(Object paramObject) throws OpenDataException;
/*     */   
/*     */   public void checkReconstructible() throws InvalidObjectException {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MXBeanMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */