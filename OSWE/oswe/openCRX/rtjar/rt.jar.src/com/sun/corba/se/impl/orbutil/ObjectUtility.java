/*    */ package com.sun.corba.se.impl.orbutil;
/*    */ 
/*    */ import java.lang.reflect.Array;
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
/*    */ public final class ObjectUtility
/*    */ {
/*    */   public static Object concatenateArrays(Object paramObject1, Object paramObject2) {
/* 63 */     Class<?> clazz1 = paramObject1.getClass().getComponentType();
/* 64 */     Class<?> clazz2 = paramObject2.getClass().getComponentType();
/* 65 */     int i = Array.getLength(paramObject1);
/* 66 */     int j = Array.getLength(paramObject2);
/*    */     
/* 68 */     if (clazz1 == null || clazz2 == null)
/* 69 */       throw new IllegalStateException("Arguments must be arrays"); 
/* 70 */     if (!clazz1.equals(clazz2)) {
/* 71 */       throw new IllegalStateException("Arguments must be arrays with the same component type");
/*    */     }
/*    */     
/* 74 */     Object object = Array.newInstance(clazz1, i + j);
/*    */     
/* 76 */     byte b1 = 0;
/*    */     byte b2;
/* 78 */     for (b2 = 0; b2 < i; b2++) {
/* 79 */       Array.set(object, b1++, Array.get(paramObject1, b2));
/*    */     }
/* 81 */     for (b2 = 0; b2 < j; b2++) {
/* 82 */       Array.set(object, b1++, Array.get(paramObject2, b2));
/*    */     }
/* 84 */     return object;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ObjectUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */