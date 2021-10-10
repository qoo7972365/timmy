/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ import com.sun.beans.TypeResolver;
/*     */ import com.sun.beans.util.Cache;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MethodFinder
/*     */   extends AbstractFinder<Method>
/*     */ {
/*  49 */   private static final Cache<Signature, Method> CACHE = new Cache<Signature, Method>(Cache.Kind.SOFT, Cache.Kind.SOFT)
/*     */     {
/*     */       public Method create(Signature param1Signature) {
/*     */         try {
/*  53 */           MethodFinder methodFinder = new MethodFinder(param1Signature.getName(), param1Signature.getArgs());
/*  54 */           return MethodFinder.findAccessibleMethod(methodFinder.find(param1Signature.getType().getMethods()));
/*     */         }
/*  56 */         catch (Exception exception) {
/*  57 */           throw new SignatureException(exception);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Method findMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
/*  74 */     if (paramString == null) {
/*  75 */       throw new IllegalArgumentException("Method name is not set");
/*     */     }
/*  77 */     PrimitiveWrapperMap.replacePrimitivesWithWrappers(paramVarArgs);
/*  78 */     Signature signature = new Signature(paramClass, paramString, paramVarArgs);
/*     */     
/*     */     try {
/*  81 */       Method method = CACHE.get(signature);
/*  82 */       return (method == null || ReflectUtil.isPackageAccessible(method.getDeclaringClass())) ? method : CACHE.create(signature);
/*     */     }
/*  84 */     catch (SignatureException signatureException) {
/*  85 */       throw signatureException.toNoSuchMethodException("Method '" + paramString + "' is not found");
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
/*     */   public static Method findInstanceMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
/* 101 */     Method method = findMethod(paramClass, paramString, paramVarArgs);
/* 102 */     if (Modifier.isStatic(method.getModifiers())) {
/* 103 */       throw new NoSuchMethodException("Method '" + paramString + "' is static");
/*     */     }
/* 105 */     return method;
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
/*     */   public static Method findStaticMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
/* 120 */     Method method = findMethod(paramClass, paramString, paramVarArgs);
/* 121 */     if (!Modifier.isStatic(method.getModifiers())) {
/* 122 */       throw new NoSuchMethodException("Method '" + paramString + "' is not static");
/*     */     }
/* 124 */     return method;
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
/*     */   public static Method findAccessibleMethod(Method paramMethod) throws NoSuchMethodException {
/* 136 */     Class<?> clazz = paramMethod.getDeclaringClass();
/* 137 */     if (Modifier.isPublic(clazz.getModifiers()) && ReflectUtil.isPackageAccessible(clazz)) {
/* 138 */       return paramMethod;
/*     */     }
/* 140 */     if (Modifier.isStatic(paramMethod.getModifiers())) {
/* 141 */       throw new NoSuchMethodException("Method '" + paramMethod.getName() + "' is not accessible");
/*     */     }
/* 143 */     for (Type type : clazz.getGenericInterfaces()) {
/*     */       try {
/* 145 */         return findAccessibleMethod(paramMethod, type);
/*     */       }
/* 147 */       catch (NoSuchMethodException noSuchMethodException) {}
/*     */     } 
/*     */ 
/*     */     
/* 151 */     return findAccessibleMethod(paramMethod, clazz.getGenericSuperclass());
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
/*     */   private static Method findAccessibleMethod(Method paramMethod, Type paramType) throws NoSuchMethodException {
/* 164 */     String str = paramMethod.getName();
/* 165 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 166 */     if (paramType instanceof Class) {
/* 167 */       Class clazz = (Class)paramType;
/* 168 */       return findAccessibleMethod(clazz.getMethod(str, arrayOfClass));
/*     */     } 
/* 170 */     if (paramType instanceof ParameterizedType) {
/* 171 */       ParameterizedType parameterizedType = (ParameterizedType)paramType;
/* 172 */       Class clazz = (Class)parameterizedType.getRawType();
/* 173 */       for (Method method : clazz.getMethods()) {
/* 174 */         if (method.getName().equals(str)) {
/* 175 */           Class[] arrayOfClass1 = method.getParameterTypes();
/* 176 */           if (arrayOfClass1.length == arrayOfClass.length) {
/* 177 */             if (Arrays.equals((Object[])arrayOfClass, (Object[])arrayOfClass1)) {
/* 178 */               return findAccessibleMethod(method);
/*     */             }
/* 180 */             Type[] arrayOfType = method.getGenericParameterTypes();
/* 181 */             if (arrayOfClass.length == arrayOfType.length && 
/* 182 */               Arrays.equals((Object[])arrayOfClass, (Object[])TypeResolver.erase(TypeResolver.resolve(parameterizedType, arrayOfType)))) {
/* 183 */               return findAccessibleMethod(method);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     throw new NoSuchMethodException("Method '" + str + "' is not accessible");
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
/*     */   private MethodFinder(String paramString, Class<?>[] paramArrayOfClass) {
/* 203 */     super(paramArrayOfClass);
/* 204 */     this.name = paramString;
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
/*     */   protected boolean isValid(Method paramMethod) {
/* 218 */     return (super.isValid(paramMethod) && paramMethod.getName().equals(this.name));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/MethodFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */