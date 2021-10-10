/*     */ package sun.tracing;
/*     */ 
/*     */ import com.sun.tracing.Probe;
/*     */ import com.sun.tracing.Provider;
/*     */ import com.sun.tracing.ProviderName;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProviderSkeleton
/*     */   implements InvocationHandler, Provider
/*     */ {
/*     */   protected boolean active;
/*     */   protected Class<? extends Provider> providerType;
/*     */   protected HashMap<Method, ProbeSkeleton> probes;
/*     */   
/*     */   protected abstract ProbeSkeleton createProbe(Method paramMethod);
/*     */   
/*     */   protected ProviderSkeleton(Class<? extends Provider> paramClass) {
/*  92 */     this.active = false;
/*  93 */     this.providerType = paramClass;
/*  94 */     this.probes = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 104 */     Method[] arrayOfMethod = AccessController.<Method[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Method[]>() {
/*     */           public Method[] run() {
/* 106 */             return ProviderSkeleton.this.providerType.getDeclaredMethods();
/*     */           }
/*     */         });
/*     */     
/* 110 */     for (Method method : arrayOfMethod) {
/* 111 */       if (method.getReturnType() != void.class) {
/* 112 */         throw new IllegalArgumentException("Return value of method is not void");
/*     */       }
/*     */       
/* 115 */       this.probes.put(method, createProbe(method));
/*     */     } 
/*     */     
/* 118 */     this.active = true;
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
/*     */   public <T extends Provider> T newProxyInstance() {
/* 133 */     final ProviderSkeleton ih = this;
/* 134 */     return (T)AccessController.<Provider>doPrivileged(new PrivilegedAction<T>() {
/*     */           public T run() {
/* 136 */             return (T)Proxy.newProxyInstance(ProviderSkeleton.this.providerType.getClassLoader(), new Class[] { this.this$0.providerType }, ih);
/*     */           }
/*     */         });
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
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/* 157 */     Class<?> clazz = paramMethod.getDeclaringClass();
/*     */     
/* 159 */     if (clazz != this.providerType) {
/*     */ 
/*     */       
/*     */       try {
/* 163 */         if (clazz == Provider.class || clazz == Object.class)
/*     */         {
/* 165 */           return paramMethod.invoke(this, paramArrayOfObject);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 170 */         throw new SecurityException();
/*     */       }
/* 172 */       catch (IllegalAccessException illegalAccessException) {
/*     */         assert false;
/* 174 */       } catch (InvocationTargetException invocationTargetException) {
/*     */         assert false;
/*     */       } 
/*     */     } else {
/* 178 */       triggerProbe(paramMethod, paramArrayOfObject);
/*     */     } 
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Probe getProbe(Method paramMethod) {
/* 190 */     return this.active ? this.probes.get(paramMethod) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 199 */     this.active = false;
/* 200 */     this.probes.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getProviderName() {
/* 211 */     return getAnnotationString(this.providerType, (Class)ProviderName.class, this.providerType
/* 212 */         .getSimpleName());
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
/*     */   protected static String getAnnotationString(AnnotatedElement paramAnnotatedElement, Class<? extends Annotation> paramClass, String paramString) {
/* 228 */     String str = (String)getAnnotationValue(paramAnnotatedElement, paramClass, "value", paramString);
/*     */     
/* 230 */     return str.isEmpty() ? paramString : str;
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
/*     */   protected static Object getAnnotationValue(AnnotatedElement paramAnnotatedElement, Class<? extends Annotation> paramClass, String paramString, Object paramObject) {
/* 247 */     Object object = paramObject;
/*     */     try {
/* 249 */       Method method = paramClass.getMethod(paramString, new Class[0]);
/* 250 */       Object object1 = paramAnnotatedElement.getAnnotation((Class)paramClass);
/* 251 */       object = method.invoke(object1, new Object[0]);
/* 252 */     } catch (NoSuchMethodException noSuchMethodException) {
/*     */       assert false;
/* 254 */     } catch (IllegalAccessException illegalAccessException) {
/*     */       assert false;
/* 256 */     } catch (InvocationTargetException invocationTargetException) {
/*     */       assert false;
/* 258 */     } catch (NullPointerException nullPointerException) {
/*     */       assert false;
/*     */     } 
/* 261 */     return object;
/*     */   }
/*     */   
/*     */   protected void triggerProbe(Method paramMethod, Object[] paramArrayOfObject) {
/* 265 */     if (this.active) {
/* 266 */       ProbeSkeleton probeSkeleton = this.probes.get(paramMethod);
/* 267 */       if (probeSkeleton != null)
/*     */       {
/* 269 */         probeSkeleton.uncheckedTrigger(paramArrayOfObject);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/ProviderSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */