/*    */ package com.sun.xml.internal.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.Type;
/*    */ import java.lang.reflect.WildcardType;
/*    */ import java.util.Arrays;
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
/*    */ final class WildcardTypeImpl
/*    */   implements WildcardType
/*    */ {
/*    */   private final Type[] ub;
/*    */   private final Type[] lb;
/*    */   
/*    */   public WildcardTypeImpl(Type[] ub, Type[] lb) {
/* 41 */     this.ub = ub;
/* 42 */     this.lb = lb;
/*    */   }
/*    */   
/*    */   public Type[] getUpperBounds() {
/* 46 */     return this.ub;
/*    */   }
/*    */   
/*    */   public Type[] getLowerBounds() {
/* 50 */     return this.lb;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 54 */     return Arrays.hashCode((Object[])this.lb) ^ Arrays.hashCode((Object[])this.ub);
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 58 */     if (obj instanceof WildcardType) {
/* 59 */       WildcardType that = (WildcardType)obj;
/* 60 */       return (Arrays.equals((Object[])that.getLowerBounds(), (Object[])this.lb) && 
/* 61 */         Arrays.equals((Object[])that.getUpperBounds(), (Object[])this.ub));
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/nav/WildcardTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */