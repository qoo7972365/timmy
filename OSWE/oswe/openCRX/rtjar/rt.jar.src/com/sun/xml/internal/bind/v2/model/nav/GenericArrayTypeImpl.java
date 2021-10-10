/*    */ package com.sun.xml.internal.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.Type;
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
/*    */ final class GenericArrayTypeImpl
/*    */   implements GenericArrayType
/*    */ {
/*    */   private Type genericComponentType;
/*    */   
/*    */   GenericArrayTypeImpl(Type ct) {
/* 38 */     assert ct != null;
/* 39 */     this.genericComponentType = ct;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getGenericComponentType() {
/* 51 */     return this.genericComponentType;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     Type componentType = getGenericComponentType();
/* 56 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 58 */     if (componentType instanceof Class) {
/* 59 */       sb.append(((Class)componentType).getName());
/*    */     } else {
/* 61 */       sb.append(componentType.toString());
/* 62 */     }  sb.append("[]");
/* 63 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 68 */     if (o instanceof GenericArrayType) {
/* 69 */       GenericArrayType that = (GenericArrayType)o;
/*    */       
/* 71 */       Type thatComponentType = that.getGenericComponentType();
/* 72 */       return this.genericComponentType.equals(thatComponentType);
/*    */     } 
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 79 */     return this.genericComponentType.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/nav/GenericArrayTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */