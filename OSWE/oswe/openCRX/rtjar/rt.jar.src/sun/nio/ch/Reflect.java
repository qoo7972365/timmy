/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Reflect
/*     */ {
/*     */   private static class ReflectionError
/*     */     extends Error
/*     */   {
/*     */     private static final long serialVersionUID = -8659519328078164097L;
/*     */     
/*     */     ReflectionError(Throwable param1Throwable) {
/*  41 */       super(param1Throwable);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setAccessible(final AccessibleObject ao) {
/*  46 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*  48 */             ao.setAccessible(true);
/*  49 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static Constructor<?> lookupConstructor(String paramString, Class<?>[] paramArrayOfClass) {
/*     */     try {
/*  57 */       Class<?> clazz = Class.forName(paramString);
/*  58 */       Constructor<?> constructor = clazz.getDeclaredConstructor(paramArrayOfClass);
/*  59 */       setAccessible(constructor);
/*  60 */       return constructor;
/*  61 */     } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
/*  62 */       throw new ReflectionError(classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static Object invoke(Constructor<?> paramConstructor, Object[] paramArrayOfObject) {
/*     */     try {
/*  68 */       return paramConstructor.newInstance(paramArrayOfObject);
/*  69 */     } catch (InstantiationException|IllegalAccessException|InvocationTargetException instantiationException) {
/*     */ 
/*     */       
/*  72 */       throw new ReflectionError(instantiationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Method lookupMethod(String paramString1, String paramString2, Class... paramVarArgs) {
/*     */     try {
/*  81 */       Class<?> clazz = Class.forName(paramString1);
/*  82 */       Method method = clazz.getDeclaredMethod(paramString2, paramVarArgs);
/*  83 */       setAccessible(method);
/*  84 */       return method;
/*  85 */     } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
/*  86 */       throw new ReflectionError(classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static Object invoke(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) {
/*     */     try {
/*  92 */       return paramMethod.invoke(paramObject, paramArrayOfObject);
/*  93 */     } catch (IllegalAccessException|InvocationTargetException illegalAccessException) {
/*  94 */       throw new ReflectionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Object invokeIO(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws IOException {
/*     */     try {
/* 102 */       return paramMethod.invoke(paramObject, paramArrayOfObject);
/* 103 */     } catch (IllegalAccessException illegalAccessException) {
/* 104 */       throw new ReflectionError(illegalAccessException);
/* 105 */     } catch (InvocationTargetException invocationTargetException) {
/* 106 */       if (IOException.class.isInstance(invocationTargetException.getCause()))
/* 107 */         throw (IOException)invocationTargetException.getCause(); 
/* 108 */       throw new ReflectionError(invocationTargetException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static Field lookupField(String paramString1, String paramString2) {
/*     */     try {
/* 114 */       Class<?> clazz = Class.forName(paramString1);
/* 115 */       Field field = clazz.getDeclaredField(paramString2);
/* 116 */       setAccessible(field);
/* 117 */       return field;
/* 118 */     } catch (ClassNotFoundException|NoSuchFieldException classNotFoundException) {
/* 119 */       throw new ReflectionError(classNotFoundException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static Object get(Object paramObject, Field paramField) {
/*     */     try {
/* 125 */       return paramField.get(paramObject);
/* 126 */     } catch (IllegalAccessException illegalAccessException) {
/* 127 */       throw new ReflectionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static Object get(Field paramField) {
/* 132 */     return get(null, paramField);
/*     */   }
/*     */   
/*     */   static void set(Object paramObject1, Field paramField, Object paramObject2) {
/*     */     try {
/* 137 */       paramField.set(paramObject1, paramObject2);
/* 138 */     } catch (IllegalAccessException illegalAccessException) {
/* 139 */       throw new ReflectionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setInt(Object paramObject, Field paramField, int paramInt) {
/*     */     try {
/* 145 */       paramField.setInt(paramObject, paramInt);
/* 146 */     } catch (IllegalAccessException illegalAccessException) {
/* 147 */       throw new ReflectionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setBoolean(Object paramObject, Field paramField, boolean paramBoolean) {
/*     */     try {
/* 153 */       paramField.setBoolean(paramObject, paramBoolean);
/* 154 */     } catch (IllegalAccessException illegalAccessException) {
/* 155 */       throw new ReflectionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/Reflect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */