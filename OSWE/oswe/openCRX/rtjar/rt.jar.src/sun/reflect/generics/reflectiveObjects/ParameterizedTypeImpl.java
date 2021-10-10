/*     */ package sun.reflect.generics.reflectiveObjects;
/*     */ 
/*     */ import java.lang.reflect.MalformedParameterizedTypeException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
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
/*     */ public class ParameterizedTypeImpl
/*     */   implements ParameterizedType
/*     */ {
/*     */   private final Type[] actualTypeArguments;
/*     */   private final Class<?> rawType;
/*     */   private final Type ownerType;
/*     */   
/*     */   private ParameterizedTypeImpl(Class<?> paramClass, Type[] paramArrayOfType, Type paramType) {
/*  48 */     this.actualTypeArguments = paramArrayOfType;
/*  49 */     this.rawType = paramClass;
/*  50 */     this.ownerType = (paramType != null) ? paramType : paramClass.getDeclaringClass();
/*  51 */     validateConstructorArguments();
/*     */   }
/*     */   
/*     */   private void validateConstructorArguments() {
/*  55 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])this.rawType.getTypeParameters();
/*     */     
/*  57 */     if (arrayOfTypeVariable.length != this.actualTypeArguments.length) {
/*  58 */       throw new MalformedParameterizedTypeException();
/*     */     }
/*  60 */     for (byte b = 0; b < this.actualTypeArguments.length; b++);
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
/*     */   public static ParameterizedTypeImpl make(Class<?> paramClass, Type[] paramArrayOfType, Type paramType) {
/*  92 */     return new ParameterizedTypeImpl(paramClass, paramArrayOfType, paramType);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getActualTypeArguments() {
/* 115 */     return (Type[])this.actualTypeArguments.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getRawType() {
/* 126 */     return this.rawType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getOwnerType() {
/* 148 */     return this.ownerType;
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
/*     */   public boolean equals(Object paramObject) {
/* 160 */     if (paramObject instanceof ParameterizedType) {
/*     */       
/* 162 */       ParameterizedType parameterizedType = (ParameterizedType)paramObject;
/*     */       
/* 164 */       if (this == parameterizedType) {
/* 165 */         return true;
/*     */       }
/* 167 */       Type type1 = parameterizedType.getOwnerType();
/* 168 */       Type type2 = parameterizedType.getRawType();
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
/* 189 */       return (
/* 190 */         Objects.equals(this.ownerType, type1) && 
/* 191 */         Objects.equals(this.rawType, type2) && 
/* 192 */         Arrays.equals((Object[])this.actualTypeArguments, (Object[])parameterizedType
/* 193 */           .getActualTypeArguments()));
/*     */     } 
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return 
/* 201 */       Arrays.hashCode((Object[])this.actualTypeArguments) ^ 
/* 202 */       Objects.hashCode(this.ownerType) ^ 
/* 203 */       Objects.hashCode(this.rawType);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 207 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 209 */     if (this.ownerType != null)
/* 210 */     { if (this.ownerType instanceof Class) {
/* 211 */         stringBuilder.append(((Class)this.ownerType).getName());
/*     */       } else {
/* 213 */         stringBuilder.append(this.ownerType.toString());
/*     */       } 
/* 215 */       stringBuilder.append("$");
/*     */       
/* 217 */       if (this.ownerType instanceof ParameterizedTypeImpl) {
/*     */ 
/*     */         
/* 220 */         stringBuilder.append(this.rawType.getName().replace(((ParameterizedTypeImpl)this.ownerType).rawType.getName() + "$", ""));
/*     */       } else {
/*     */         
/* 223 */         stringBuilder.append(this.rawType.getSimpleName());
/*     */       }  }
/* 225 */     else { stringBuilder.append(this.rawType.getName()); }
/*     */     
/* 227 */     if (this.actualTypeArguments != null && this.actualTypeArguments.length > 0) {
/*     */       
/* 229 */       stringBuilder.append("<");
/* 230 */       boolean bool = true;
/* 231 */       for (Type type : this.actualTypeArguments) {
/* 232 */         if (!bool)
/* 233 */           stringBuilder.append(", "); 
/* 234 */         stringBuilder.append(type.getTypeName());
/* 235 */         bool = false;
/*     */       } 
/* 237 */       stringBuilder.append(">");
/*     */     } 
/*     */     
/* 240 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/generics/reflectiveObjects/ParameterizedTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */