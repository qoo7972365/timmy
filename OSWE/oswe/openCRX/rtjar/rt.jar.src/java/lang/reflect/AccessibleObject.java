/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.ReflectionFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessibleObject
/*     */   implements AnnotatedElement
/*     */ {
/*  64 */   private static final Permission ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean override;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAccessible(AccessibleObject[] paramArrayOfAccessibleObject, boolean paramBoolean) throws SecurityException {
/*  94 */     SecurityManager securityManager = System.getSecurityManager();
/*  95 */     if (securityManager != null) securityManager.checkPermission(ACCESS_PERMISSION); 
/*  96 */     for (byte b = 0; b < paramArrayOfAccessibleObject.length; b++) {
/*  97 */       setAccessible0(paramArrayOfAccessibleObject[b], paramBoolean);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccessible(boolean paramBoolean) throws SecurityException {
/* 127 */     SecurityManager securityManager = System.getSecurityManager();
/* 128 */     if (securityManager != null) securityManager.checkPermission(ACCESS_PERMISSION); 
/* 129 */     setAccessible0(this, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setAccessible0(AccessibleObject paramAccessibleObject, boolean paramBoolean) throws SecurityException {
/* 137 */     if (paramAccessibleObject instanceof Constructor && paramBoolean == true) {
/* 138 */       Constructor<Class<?>> constructor = (Constructor)paramAccessibleObject;
/* 139 */       if (constructor.getDeclaringClass() == Class.class) {
/* 140 */         throw new SecurityException("Cannot make a java.lang.Class constructor accessible");
/*     */       }
/*     */     } 
/*     */     
/* 144 */     paramAccessibleObject.override = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAccessible() {
/* 153 */     return this.override;
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
/* 173 */   static final ReflectionFactory reflectionFactory = AccessController.<ReflectionFactory>doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
/*     */ 
/*     */   
/*     */   volatile Object securityCheckCache;
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 181 */     throw new AssertionError("All subclasses should override this method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnotationPresent(Class<? extends Annotation> paramClass) {
/* 191 */     return super.isAnnotationPresent(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass) {
/* 200 */     throw new AssertionError("All subclasses should override this method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 207 */     return getDeclaredAnnotations();
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
/*     */   public <T extends Annotation> T getDeclaredAnnotation(Class<T> paramClass) {
/* 219 */     return getAnnotation(paramClass);
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
/*     */   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> paramClass) {
/* 231 */     return getAnnotationsByType(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 238 */     throw new AssertionError("All subclasses should override this method");
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
/*     */   void checkAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt) throws IllegalAccessException {
/* 264 */     if (paramClass1 == paramClass2) {
/*     */       return;
/*     */     }
/* 267 */     Object object = this.securityCheckCache;
/* 268 */     Class<?> clazz = paramClass2;
/* 269 */     if (paramObject != null && 
/* 270 */       Modifier.isProtected(paramInt) && (
/* 271 */       clazz = paramObject.getClass()) != paramClass2) {
/*     */       
/* 273 */       if (object instanceof Class[]) {
/* 274 */         Class[] arrayOfClass = (Class[])object;
/* 275 */         if (arrayOfClass[1] == clazz && arrayOfClass[0] == paramClass1)
/*     */         {
/*     */           return;
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 282 */     else if (object == paramClass1) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 288 */     slowCheckMemberAccess(paramClass1, paramClass2, paramObject, paramInt, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void slowCheckMemberAccess(Class<?> paramClass1, Class<?> paramClass2, Object paramObject, int paramInt, Class<?> paramClass3) throws IllegalAccessException {
/* 296 */     Reflection.ensureMemberAccess(paramClass1, paramClass2, paramObject, paramInt);
/*     */ 
/*     */     
/* 299 */     (new Class[2])[0] = paramClass1; (new Class[2])[1] = paramClass3; Object object = (paramClass3 == paramClass2) ? paramClass1 : new Class[2];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     this.securityCheckCache = object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/AccessibleObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */