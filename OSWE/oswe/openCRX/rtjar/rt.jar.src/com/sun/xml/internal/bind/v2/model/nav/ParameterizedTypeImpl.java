/*     */ package com.sun.xml.internal.bind.v2.model.nav;
/*     */ 
/*     */ import java.lang.reflect.MalformedParameterizedTypeException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ class ParameterizedTypeImpl
/*     */   implements ParameterizedType
/*     */ {
/*     */   private Type[] actualTypeArguments;
/*     */   private Class<?> rawType;
/*     */   private Type ownerType;
/*     */   
/*     */   ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
/*  46 */     this.actualTypeArguments = actualTypeArguments;
/*  47 */     this.rawType = rawType;
/*  48 */     if (ownerType != null) {
/*  49 */       this.ownerType = ownerType;
/*     */     } else {
/*  51 */       this.ownerType = rawType.getDeclaringClass();
/*     */     } 
/*  53 */     validateConstructorArguments();
/*     */   }
/*     */   
/*     */   private void validateConstructorArguments() {
/*  57 */     TypeVariable[] formals = (TypeVariable[])this.rawType.getTypeParameters();
/*     */     
/*  59 */     if (formals.length != this.actualTypeArguments.length) {
/*  60 */       throw new MalformedParameterizedTypeException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getActualTypeArguments() {
/*  70 */     return (Type[])this.actualTypeArguments.clone();
/*     */   }
/*     */   
/*     */   public Class<?> getRawType() {
/*  74 */     return this.rawType;
/*     */   }
/*     */   
/*     */   public Type getOwnerType() {
/*  78 */     return this.ownerType;
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
/*     */   public boolean equals(Object o) {
/*  90 */     if (o instanceof ParameterizedType) {
/*     */       
/*  92 */       ParameterizedType that = (ParameterizedType)o;
/*     */       
/*  94 */       if (this == that) {
/*  95 */         return true;
/*     */       }
/*  97 */       Type thatOwner = that.getOwnerType();
/*  98 */       Type thatRawType = that.getRawType();
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
/* 122 */       if ((this.ownerType == null) ? (thatOwner == null) : this.ownerType
/*     */ 
/*     */         
/* 125 */         .equals(thatOwner)) if ((this.rawType == null) ? (thatRawType == null) : this.rawType
/*     */ 
/*     */           
/* 128 */           .equals(thatRawType))
/* 129 */           if (Arrays.equals((Object[])this.actualTypeArguments, (Object[])that
/* 130 */               .getActualTypeArguments()));   return false;
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     return Arrays.hashCode((Object[])this.actualTypeArguments) ^ ((this.ownerType == null) ? 0 : this.ownerType
/* 138 */       .hashCode()) ^ ((this.rawType == null) ? 0 : this.rawType
/* 139 */       .hashCode());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 143 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 145 */     if (this.ownerType != null)
/* 146 */     { if (this.ownerType instanceof Class) {
/* 147 */         sb.append(((Class)this.ownerType).getName());
/*     */       } else {
/* 149 */         sb.append(this.ownerType.toString());
/*     */       } 
/* 151 */       sb.append(".");
/*     */       
/* 153 */       if (this.ownerType instanceof ParameterizedTypeImpl) {
/*     */ 
/*     */         
/* 156 */         sb.append(this.rawType.getName().replace(((ParameterizedTypeImpl)this.ownerType).rawType.getName() + "$", ""));
/*     */       } else {
/*     */         
/* 159 */         sb.append(this.rawType.getName());
/*     */       }  }
/* 161 */     else { sb.append(this.rawType.getName()); }
/*     */     
/* 163 */     if (this.actualTypeArguments != null && this.actualTypeArguments.length > 0) {
/*     */       
/* 165 */       sb.append("<");
/* 166 */       boolean first = true;
/* 167 */       for (Type t : this.actualTypeArguments) {
/* 168 */         if (!first)
/* 169 */           sb.append(", "); 
/* 170 */         if (t instanceof Class) {
/* 171 */           sb.append(((Class)t).getName());
/*     */         } else {
/* 173 */           sb.append(t.toString());
/* 174 */         }  first = false;
/*     */       } 
/* 176 */       sb.append(">");
/*     */     } 
/*     */     
/* 179 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/nav/ParameterizedTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */