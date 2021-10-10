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
/*    */ 
/*    */ public class FieldGetter
/*    */   extends PropertyGetterBase
/*    */ {
/*    */   protected Field field;
/*    */   
/*    */   public FieldGetter(Field f) {
/* 45 */     this.field = f;
/* 46 */     this.type = f.getType();
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 50 */     return this.field;
/*    */   }
/*    */   
/*    */   static class PrivilegedGetter implements PrivilegedExceptionAction {
/*    */     private Object value;
/*    */     private Field field;
/*    */     private Object instance;
/*    */     
/*    */     public PrivilegedGetter(Field field, Object instance) {
/* 59 */       this.field = field;
/* 60 */       this.instance = instance;
/*    */     }
/*    */     public Object run() throws IllegalAccessException {
/* 63 */       if (!this.field.isAccessible()) {
/* 64 */         this.field.setAccessible(true);
/*    */       }
/* 66 */       this.value = this.field.get(this.instance);
/* 67 */       return null;
/*    */     }
/*    */   }
/*    */   
/*    */   public Object get(Object instance) {
/* 72 */     if (this.field.isAccessible())
/*    */       try {
/* 74 */         return this.field.get(instance);
/* 75 */       } catch (Exception e) {
/*    */         
/* 77 */         e.printStackTrace();
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
/* 89 */         return null;
/*    */       }   PrivilegedGetter privilegedGetter = new PrivilegedGetter(this.field, instance); try { AccessController.doPrivileged(privilegedGetter); }
/*    */     catch (PrivilegedActionException e)
/*    */     { e.printStackTrace(); }
/* 93 */      return privilegedGetter.value; } public <A> A getAnnotation(Class<A> annotationType) { Class<A> c = annotationType;
/* 94 */     return this.field.getAnnotation(c); }
/*    */ 
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/FieldGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */