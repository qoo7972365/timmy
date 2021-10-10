/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.annotation.Resource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InjectionPlan<T, R>
/*     */ {
/*     */   public void inject(T instance, Callable<R> resource) {
/*     */     try {
/*  66 */       inject(instance, resource.call());
/*  67 */     } catch (Exception e) {
/*  68 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldInjectionPlan<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Field field;
/*     */ 
/*     */     
/*     */     public FieldInjectionPlan(Field field) {
/*  80 */       this.field = field;
/*     */     }
/*     */     
/*     */     public void inject(final T instance, final R resource) {
/*  84 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/*     */               try {
/*  87 */                 if (!InjectionPlan.FieldInjectionPlan.this.field.isAccessible()) {
/*  88 */                   InjectionPlan.FieldInjectionPlan.this.field.setAccessible(true);
/*     */                 }
/*  90 */                 InjectionPlan.FieldInjectionPlan.this.field.set(instance, resource);
/*  91 */                 return null;
/*  92 */               } catch (IllegalAccessException e) {
/*  93 */                 throw new WebServiceException(e);
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MethodInjectionPlan<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Method method;
/*     */ 
/*     */     
/*     */     public MethodInjectionPlan(Method method) {
/* 108 */       this.method = method;
/*     */     }
/*     */     
/*     */     public void inject(T instance, R resource) {
/* 112 */       InjectionPlan.invokeMethod(this.method, instance, new Object[] { resource });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void invokeMethod(final Method method, final Object instance, Object... args) {
/* 120 */     if (method == null)
/* 121 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/* 124 */               if (!method.isAccessible()) {
/* 125 */                 method.setAccessible(true);
/*     */               }
/* 127 */               method.invoke(instance, args);
/* 128 */             } catch (IllegalAccessException e) {
/* 129 */               throw new WebServiceException(e);
/* 130 */             } catch (InvocationTargetException e) {
/* 131 */               throw new WebServiceException(e);
/*     */             } 
/* 133 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Compositor<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Collection<InjectionPlan<T, R>> children;
/*     */     
/*     */     public Compositor(Collection<InjectionPlan<T, R>> children) {
/* 145 */       this.children = children;
/*     */     }
/*     */     
/*     */     public void inject(T instance, R res) {
/* 149 */       for (InjectionPlan<T, R> plan : this.children)
/* 150 */         plan.inject(instance, res); 
/*     */     }
/*     */     
/*     */     public void inject(T instance, Callable<R> resource) {
/* 154 */       if (!this.children.isEmpty()) {
/* 155 */         super.inject(instance, resource);
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
/*     */   public static <T, R> InjectionPlan<T, R> buildInjectionPlan(Class<? extends T> clazz, Class<R> resourceType, boolean isStatic) {
/* 169 */     List<InjectionPlan<T, R>> plan = new ArrayList<>();
/*     */     
/* 171 */     Class<?> cl = clazz;
/* 172 */     while (cl != Object.class) {
/* 173 */       for (Field field : cl.getDeclaredFields()) {
/* 174 */         Resource resource = field.<Resource>getAnnotation(Resource.class);
/* 175 */         if (resource != null) {
/* 176 */           if (isInjectionPoint(resource, field.getType(), "Incorrect type for field" + field
/* 177 */               .getName(), resourceType)) {
/*     */ 
/*     */             
/* 180 */             if (isStatic && !Modifier.isStatic(field.getModifiers())) {
/* 181 */               throw new WebServiceException("Static resource " + resourceType + " cannot be injected to non-static " + field);
/*     */             }
/* 183 */             plan.add(new FieldInjectionPlan<>(field));
/*     */           } 
/*     */         }
/*     */       } 
/* 187 */       cl = cl.getSuperclass();
/*     */     } 
/*     */     
/* 190 */     cl = clazz;
/* 191 */     while (cl != Object.class) {
/* 192 */       for (Method method : cl.getDeclaredMethods()) {
/* 193 */         Resource resource = method.<Resource>getAnnotation(Resource.class);
/* 194 */         if (resource != null) {
/* 195 */           Class[] paramTypes = method.getParameterTypes();
/* 196 */           if (paramTypes.length != 1)
/* 197 */             throw new WebServiceException("Incorrect no of arguments for method " + method); 
/* 198 */           if (isInjectionPoint(resource, paramTypes[0], "Incorrect argument types for method" + method
/* 199 */               .getName(), resourceType)) {
/*     */ 
/*     */             
/* 202 */             if (isStatic && !Modifier.isStatic(method.getModifiers())) {
/* 203 */               throw new WebServiceException("Static resource " + resourceType + " cannot be injected to non-static " + method);
/*     */             }
/* 205 */             plan.add(new MethodInjectionPlan<>(method));
/*     */           } 
/*     */         } 
/*     */       } 
/* 209 */       cl = cl.getSuperclass();
/*     */     } 
/*     */     
/* 212 */     return new Compositor<>(plan);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInjectionPoint(Resource resource, Class fieldType, String errorMessage, Class<?> resourceType) {
/* 220 */     Class t = resource.type();
/* 221 */     if (t.equals(Object.class))
/* 222 */       return fieldType.equals(resourceType); 
/* 223 */     if (t.equals(resourceType)) {
/* 224 */       if (fieldType.isAssignableFrom(resourceType)) {
/* 225 */         return true;
/*     */       }
/*     */       
/* 228 */       throw new WebServiceException(errorMessage);
/*     */     } 
/*     */     
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   public abstract void inject(T paramT, R paramR);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/InjectionPlan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */