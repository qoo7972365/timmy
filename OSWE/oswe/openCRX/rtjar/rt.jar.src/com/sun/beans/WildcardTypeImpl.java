/*     */ package com.sun.beans;
/*     */ 
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
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
/*     */ final class WildcardTypeImpl
/*     */   implements WildcardType
/*     */ {
/*     */   private final Type[] upperBounds;
/*     */   private final Type[] lowerBounds;
/*     */   
/*     */   WildcardTypeImpl(Type[] paramArrayOfType1, Type[] paramArrayOfType2) {
/*  63 */     this.upperBounds = paramArrayOfType1;
/*  64 */     this.lowerBounds = paramArrayOfType2;
/*     */   }
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
/*     */   public Type[] getUpperBounds() {
/*  77 */     return (Type[])this.upperBounds.clone();
/*     */   }
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
/*     */   public Type[] getLowerBounds() {
/*  91 */     return (Type[])this.lowerBounds.clone();
/*     */   }
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
/*     */   public boolean equals(Object paramObject) {
/* 106 */     if (paramObject instanceof WildcardType) {
/* 107 */       WildcardType wildcardType = (WildcardType)paramObject;
/* 108 */       return (Arrays.equals((Object[])this.upperBounds, (Object[])wildcardType.getUpperBounds()) && 
/* 109 */         Arrays.equals((Object[])this.lowerBounds, (Object[])wildcardType.getLowerBounds()));
/*     */     } 
/* 111 */     return false;
/*     */   }
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
/*     */   public int hashCode() {
/* 124 */     return Arrays.hashCode((Object[])this.upperBounds) ^ 
/* 125 */       Arrays.hashCode((Object[])this.lowerBounds);
/*     */   }
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
/*     */   public String toString() {
/*     */     StringBuilder stringBuilder;
/*     */     Type[] arrayOfType;
/* 140 */     if (this.lowerBounds.length == 0) {
/* 141 */       if (this.upperBounds.length == 0 || Object.class == this.upperBounds[0]) {
/* 142 */         return "?";
/*     */       }
/* 144 */       arrayOfType = this.upperBounds;
/* 145 */       stringBuilder = new StringBuilder("? extends ");
/*     */     } else {
/*     */       
/* 148 */       arrayOfType = this.lowerBounds;
/* 149 */       stringBuilder = new StringBuilder("? super ");
/*     */     } 
/* 151 */     for (byte b = 0; b < arrayOfType.length; b++) {
/* 152 */       if (b > 0) {
/* 153 */         stringBuilder.append(" & ");
/*     */       }
/* 155 */       stringBuilder.append((arrayOfType[b] instanceof Class) ? ((Class)arrayOfType[b])
/* 156 */           .getName() : arrayOfType[b]
/* 157 */           .toString());
/*     */     } 
/* 159 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/WildcardTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */