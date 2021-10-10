/*    */ package com.sun.xml.internal.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.lang.reflect.TypeVariable;
/*    */ import java.lang.reflect.WildcardType;
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
/*    */ abstract class TypeVisitor<T, P>
/*    */ {
/*    */   public final T visit(Type t, P param) {
/* 39 */     assert t != null;
/*    */     
/* 41 */     if (t instanceof Class)
/* 42 */       return onClass((Class)t, param); 
/* 43 */     if (t instanceof ParameterizedType)
/* 44 */       return onParameterizdType((ParameterizedType)t, param); 
/* 45 */     if (t instanceof GenericArrayType)
/* 46 */       return onGenericArray((GenericArrayType)t, param); 
/* 47 */     if (t instanceof WildcardType)
/* 48 */       return onWildcard((WildcardType)t, param); 
/* 49 */     if (t instanceof TypeVariable) {
/* 50 */       return onVariable((TypeVariable)t, param);
/*    */     }
/*    */     
/*    */     assert false;
/* 54 */     throw new IllegalArgumentException();
/*    */   }
/*    */   
/*    */   protected abstract T onClass(Class paramClass, P paramP);
/*    */   
/*    */   protected abstract T onParameterizdType(ParameterizedType paramParameterizedType, P paramP);
/*    */   
/*    */   protected abstract T onGenericArray(GenericArrayType paramGenericArrayType, P paramP);
/*    */   
/*    */   protected abstract T onVariable(TypeVariable paramTypeVariable, P paramP);
/*    */   
/*    */   protected abstract T onWildcard(WildcardType paramWildcardType, P paramP);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/nav/TypeVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */