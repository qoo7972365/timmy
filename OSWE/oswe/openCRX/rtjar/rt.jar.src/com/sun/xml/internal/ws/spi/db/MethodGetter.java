/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodGetter
/*     */   extends PropertyGetterBase
/*     */ {
/*     */   private Method method;
/*     */   
/*     */   public MethodGetter(Method m) {
/*  44 */     this.method = m;
/*  45 */     this.type = m.getReturnType();
/*     */   }
/*     */   
/*     */   public Method getMethod() {
/*  49 */     return this.method;
/*     */   }
/*     */   
/*     */   public <A> A getAnnotation(Class<A> annotationType) {
/*  53 */     Class<A> c = annotationType;
/*  54 */     return this.method.getAnnotation(c);
/*     */   }
/*     */   
/*     */   static class PrivilegedGetter
/*     */     implements PrivilegedExceptionAction {
/*     */     private Object value;
/*     */     private Method method;
/*     */     private Object instance;
/*     */     
/*     */     public PrivilegedGetter(Method m, Object instance) {
/*  64 */       this.method = m;
/*  65 */       this.instance = instance;
/*     */     }
/*     */     public Object run() throws IllegalAccessException {
/*  68 */       if (!this.method.isAccessible()) {
/*  69 */         this.method.setAccessible(true);
/*     */       }
/*     */       try {
/*  72 */         this.value = this.method.invoke(this.instance, new Object[0]);
/*  73 */       } catch (Exception e) {
/*     */         
/*  75 */         e.printStackTrace();
/*     */       } 
/*  77 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object get(Object instance) {
/*  82 */     Object[] args = new Object[0];
/*     */     try {
/*  84 */       if (this.method.isAccessible()) {
/*  85 */         return this.method.invoke(instance, args);
/*     */       }
/*  87 */       PrivilegedGetter privilegedGetter = new PrivilegedGetter(this.method, instance);
/*     */       try {
/*  89 */         AccessController.doPrivileged(privilegedGetter);
/*  90 */       } catch (PrivilegedActionException e) {
/*     */         
/*  92 */         e.printStackTrace();
/*     */       } 
/*  94 */       return privilegedGetter.value;
/*     */     }
/*  96 */     catch (Exception e) {
/*     */       
/*  98 */       e.printStackTrace();
/*     */       
/* 100 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/MethodGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */