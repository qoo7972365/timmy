/*     */ package com.sun.beans;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
/*     */ import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
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
/*     */ public final class TypeResolver
/*     */ {
/*  49 */   private static final WeakCache<Type, Map<Type, Type>> CACHE = new WeakCache<>();
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
/*     */   public static Type resolveInClass(Class<?> paramClass, Type paramType) {
/*  81 */     return resolve(getActualType(paramClass), paramType);
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
/*     */   public static Type[] resolveInClass(Class<?> paramClass, Type[] paramArrayOfType) {
/*  96 */     return resolve(getActualType(paramClass), paramArrayOfType);
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
/*     */   public static Type resolve(Type paramType1, Type paramType2) {
/* 157 */     if (paramType2 instanceof Class) {
/* 158 */       return paramType2;
/*     */     }
/* 160 */     if (paramType2 instanceof GenericArrayType) {
/* 161 */       Type type = ((GenericArrayType)paramType2).getGenericComponentType();
/* 162 */       type = resolve(paramType1, type);
/* 163 */       return (type instanceof Class) ? 
/* 164 */         Array.newInstance((Class)type, 0).getClass() : 
/* 165 */         GenericArrayTypeImpl.make(type);
/*     */     } 
/* 167 */     if (paramType2 instanceof ParameterizedType) {
/* 168 */       ParameterizedType parameterizedType = (ParameterizedType)paramType2;
/* 169 */       Type[] arrayOfType = resolve(paramType1, parameterizedType.getActualTypeArguments());
/* 170 */       return ParameterizedTypeImpl.make((Class)parameterizedType
/* 171 */           .getRawType(), arrayOfType, parameterizedType.getOwnerType());
/*     */     } 
/* 173 */     if (paramType2 instanceof WildcardType) {
/* 174 */       WildcardType wildcardType = (WildcardType)paramType2;
/* 175 */       Type[] arrayOfType1 = resolve(paramType1, wildcardType.getUpperBounds());
/* 176 */       Type[] arrayOfType2 = resolve(paramType1, wildcardType.getLowerBounds());
/* 177 */       return new WildcardTypeImpl(arrayOfType1, arrayOfType2);
/*     */     } 
/* 179 */     if (paramType2 instanceof TypeVariable) {
/*     */       Map<Object, Object> map;
/* 181 */       synchronized (CACHE) {
/* 182 */         map = (Map)CACHE.get(paramType1);
/* 183 */         if (map == null) {
/* 184 */           map = new HashMap<>();
/* 185 */           prepare((Map)map, paramType1);
/* 186 */           CACHE.put(paramType1, map);
/*     */         } 
/*     */       } 
/* 189 */       Type type = (Type)map.get(paramType2);
/* 190 */       if (type == null || type.equals(paramType2)) {
/* 191 */         return paramType2;
/*     */       }
/* 193 */       type = fixGenericArray(type);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       return resolve(paramType1, type);
/*     */     } 
/* 203 */     throw new IllegalArgumentException("Bad Type kind: " + paramType2.getClass());
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
/*     */   public static Type[] resolve(Type paramType, Type[] paramArrayOfType) {
/* 215 */     int i = paramArrayOfType.length;
/* 216 */     Type[] arrayOfType = new Type[i];
/* 217 */     for (byte b = 0; b < i; b++) {
/* 218 */       arrayOfType[b] = resolve(paramType, paramArrayOfType[b]);
/*     */     }
/* 220 */     return arrayOfType;
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
/*     */   public static Class<?> erase(Type paramType) {
/* 233 */     if (paramType instanceof Class) {
/* 234 */       return (Class)paramType;
/*     */     }
/* 236 */     if (paramType instanceof ParameterizedType) {
/* 237 */       ParameterizedType parameterizedType = (ParameterizedType)paramType;
/* 238 */       return (Class)parameterizedType.getRawType();
/*     */     } 
/* 240 */     if (paramType instanceof TypeVariable) {
/* 241 */       TypeVariable typeVariable = (TypeVariable)paramType;
/* 242 */       Type[] arrayOfType = typeVariable.getBounds();
/* 243 */       return (0 < arrayOfType.length) ? 
/* 244 */         erase(arrayOfType[0]) : Object.class;
/*     */     } 
/*     */     
/* 247 */     if (paramType instanceof WildcardType) {
/* 248 */       WildcardType wildcardType = (WildcardType)paramType;
/* 249 */       Type[] arrayOfType = wildcardType.getUpperBounds();
/* 250 */       return (0 < arrayOfType.length) ? 
/* 251 */         erase(arrayOfType[0]) : Object.class;
/*     */     } 
/*     */     
/* 254 */     if (paramType instanceof GenericArrayType) {
/* 255 */       GenericArrayType genericArrayType = (GenericArrayType)paramType;
/* 256 */       return Array.newInstance(erase(genericArrayType.getGenericComponentType()), 0).getClass();
/*     */     } 
/* 258 */     throw new IllegalArgumentException("Unknown Type kind: " + paramType.getClass());
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
/*     */   public static Class[] erase(Type[] paramArrayOfType) {
/* 271 */     int i = paramArrayOfType.length;
/* 272 */     Class[] arrayOfClass = new Class[i];
/* 273 */     for (byte b = 0; b < i; b++) {
/* 274 */       arrayOfClass[b] = erase(paramArrayOfType[b]);
/*     */     }
/* 276 */     return arrayOfClass;
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
/*     */   private static void prepare(Map<Type, Type> paramMap, Type paramType) {
/* 296 */     Class clazz = (paramType instanceof Class) ? (Class)paramType : (Class)((ParameterizedType)paramType).getRawType();
/*     */     
/* 298 */     TypeVariable[] arrayOfTypeVariable = clazz.getTypeParameters();
/*     */ 
/*     */ 
/*     */     
/* 302 */     Object object = (paramType instanceof Class) ? arrayOfTypeVariable : ((ParameterizedType)paramType).getActualTypeArguments();
/*     */     
/* 304 */     assert arrayOfTypeVariable.length == object.length;
/* 305 */     for (byte b = 0; b < arrayOfTypeVariable.length; b++) {
/* 306 */       paramMap.put(arrayOfTypeVariable[b], (Type)object[b]);
/*     */     }
/* 308 */     Type type = clazz.getGenericSuperclass();
/* 309 */     if (type != null) {
/* 310 */       prepare(paramMap, type);
/*     */     }
/* 312 */     for (Type type1 : clazz.getGenericInterfaces()) {
/* 313 */       prepare(paramMap, type1);
/*     */     }
/*     */ 
/*     */     
/* 317 */     if (paramType instanceof Class && arrayOfTypeVariable.length > 0) {
/* 318 */       for (Map.Entry<Type, Type> entry : paramMap.entrySet()) {
/* 319 */         entry.setValue(erase((Type)entry.getValue()));
/*     */       }
/*     */     }
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
/*     */   private static Type fixGenericArray(Type paramType) {
/* 343 */     if (paramType instanceof GenericArrayType) {
/* 344 */       Type type = ((GenericArrayType)paramType).getGenericComponentType();
/* 345 */       type = fixGenericArray(type);
/* 346 */       if (type instanceof Class) {
/* 347 */         return Array.newInstance((Class)type, 0).getClass();
/*     */       }
/*     */     } 
/* 350 */     return paramType;
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
/*     */   private static Type getActualType(Class<?> paramClass) {
/* 372 */     TypeVariable[] arrayOfTypeVariable = (TypeVariable[])paramClass.getTypeParameters();
/* 373 */     return (arrayOfTypeVariable.length == 0) ? paramClass : 
/*     */       
/* 375 */       ParameterizedTypeImpl.make(paramClass, (Type[])arrayOfTypeVariable, paramClass
/* 376 */         .getEnclosingClass());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/TypeResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */