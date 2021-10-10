/*    */ package com.sun.xml.internal.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ public class FieldSetter
/*    */   extends PropertySetterBase
/*    */ {
/*    */   protected Field field;
/*    */   
/*    */   public FieldSetter(Field f) {
/* 44 */     this.field = f;
/* 45 */     this.type = f.getType();
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 49 */     return this.field;
/*    */   }
/*    */   
/*    */   public void set(final Object instance, final Object resource) {
/* 53 */     if (this.field.isAccessible()) {
/*    */       try {
/* 55 */         this.field.set(instance, resource);
/* 56 */       } catch (Exception e) {
/*    */         
/* 58 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/*    */       try {
/* 62 */         AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*    */               public Object run() throws IllegalAccessException {
/* 64 */                 if (!FieldSetter.this.field.isAccessible()) {
/* 65 */                   FieldSetter.this.field.setAccessible(true);
/*    */                 }
/* 67 */                 FieldSetter.this.field.set(instance, resource);
/* 68 */                 return null;
/*    */               }
/*    */             });
/* 71 */       } catch (PrivilegedActionException e) {
/*    */         
/* 73 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public <A> A getAnnotation(Class<A> annotationType) {
/* 79 */     Class<A> c = annotationType;
/* 80 */     return this.field.getAnnotation(c);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/FieldSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */