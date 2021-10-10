/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodSetter
/*    */   extends PropertySetterBase
/*    */ {
/*    */   private Method method;
/*    */   
/*    */   public MethodSetter(Method m) {
/* 44 */     this.method = m;
/* 45 */     this.type = m.getParameterTypes()[0];
/*    */   }
/*    */   
/*    */   public Method getMethod() {
/* 49 */     return this.method;
/*    */   }
/*    */   
/*    */   public <A> A getAnnotation(Class<A> annotationType) {
/* 53 */     Class<A> c = annotationType;
/* 54 */     return this.method.getAnnotation(c);
/*    */   }
/*    */   
/*    */   public void set(final Object instance, Object resource) {
/* 58 */     final Object[] args = { resource };
/* 59 */     if (this.method.isAccessible()) {
/*    */       try {
/* 61 */         this.method.invoke(instance, args);
/* 62 */       } catch (Exception e) {
/* 63 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/*    */       try {
/* 67 */         AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*    */               public Object run() throws IllegalAccessException {
/* 69 */                 if (!MethodSetter.this.method.isAccessible()) {
/* 70 */                   MethodSetter.this.method.setAccessible(true);
/*    */                 }
/*    */                 try {
/* 73 */                   MethodSetter.this.method.invoke(instance, args);
/* 74 */                 } catch (Exception e) {
/*    */                   
/* 76 */                   e.printStackTrace();
/*    */                 } 
/* 78 */                 return null;
/*    */               }
/*    */             });
/* 81 */       } catch (PrivilegedActionException e) {
/*    */         
/* 83 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/MethodSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */