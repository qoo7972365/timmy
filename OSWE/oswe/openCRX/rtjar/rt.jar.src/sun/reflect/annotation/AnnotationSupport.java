/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.annotation.Repeatable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AnnotationSupport
/*     */ {
/*  40 */   private static final JavaLangAccess LANG_ACCESS = SharedSecrets.getJavaLangAccess();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A extends Annotation> A[] getDirectlyAndIndirectlyPresent(Map<Class<? extends Annotation>, Annotation> paramMap, Class<A> paramClass) {
/*  65 */     ArrayList<Annotation> arrayList = new ArrayList();
/*     */ 
/*     */     
/*  68 */     Annotation annotation = paramMap.get(paramClass);
/*  69 */     if (annotation != null) {
/*  70 */       arrayList.add(annotation);
/*     */     }
/*  72 */     Object[] arrayOfObject = getIndirectlyPresent(paramMap, (Class)paramClass);
/*  73 */     if (arrayOfObject != null && arrayOfObject.length != 0) {
/*     */       
/*  75 */       boolean bool = (annotation == null || containerBeforeContainee(paramMap, paramClass)) ? true : false;
/*     */       
/*  77 */       arrayList.addAll(bool ? 0 : 1, Arrays.asList(arrayOfObject));
/*     */     } 
/*     */ 
/*     */     
/*  81 */     Annotation[] arrayOfAnnotation = (Annotation[])Array.newInstance(paramClass, arrayList.size());
/*  82 */     return (A[])arrayList.<Annotation>toArray(arrayOfAnnotation);
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
/*     */   private static <A extends Annotation> A[] getIndirectlyPresent(Map<Class<? extends Annotation>, Annotation> paramMap, Class<A> paramClass) {
/*  99 */     Repeatable repeatable = paramClass.<Repeatable>getDeclaredAnnotation(Repeatable.class);
/* 100 */     if (repeatable == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     Class<? extends Annotation> clazz = repeatable.value();
/*     */     
/* 105 */     Annotation annotation = paramMap.get(clazz);
/* 106 */     if (annotation == null) {
/* 107 */       return null;
/*     */     }
/*     */     
/* 110 */     Object[] arrayOfObject = getValueArray(annotation);
/* 111 */     checkTypes(arrayOfObject, annotation, paramClass);
/*     */     
/* 113 */     return (A[])arrayOfObject;
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
/*     */   private static <A extends Annotation> boolean containerBeforeContainee(Map<Class<? extends Annotation>, Annotation> paramMap, Class<A> paramClass) {
/* 129 */     Class<? extends Annotation> clazz = ((Repeatable)paramClass.<Repeatable>getDeclaredAnnotation(Repeatable.class)).value();
/*     */     
/* 131 */     for (Class<? extends Annotation> clazz1 : paramMap.keySet()) {
/* 132 */       if (clazz1 == clazz) return true; 
/* 133 */       if (clazz1 == paramClass) return false;
/*     */     
/*     */     } 
/*     */     
/* 137 */     return false;
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
/*     */   public static <A extends Annotation> A[] getAssociatedAnnotations(Map<Class<? extends Annotation>, Annotation> paramMap, Class<?> paramClass, Class<A> paramClass1) {
/* 160 */     Objects.requireNonNull(paramClass);
/*     */ 
/*     */     
/* 163 */     Object[] arrayOfObject = getDirectlyAndIndirectlyPresent(paramMap, (Class)paramClass1);
/*     */ 
/*     */     
/* 166 */     if (AnnotationType.getInstance(paramClass1).isInherited()) {
/* 167 */       Class<?> clazz = paramClass.getSuperclass();
/* 168 */       while (arrayOfObject.length == 0 && clazz != null) {
/* 169 */         arrayOfObject = getDirectlyAndIndirectlyPresent(LANG_ACCESS.getDeclaredAnnotationMap(clazz), paramClass1);
/* 170 */         clazz = clazz.getSuperclass();
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return (A[])arrayOfObject;
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
/*     */   private static <A extends Annotation> A[] getValueArray(Annotation paramAnnotation) {
/*     */     try {
/* 187 */       Class<? extends Annotation> clazz = paramAnnotation.annotationType();
/* 188 */       AnnotationType annotationType = AnnotationType.getInstance(clazz);
/* 189 */       if (annotationType == null) {
/* 190 */         throw invalidContainerException(paramAnnotation, null);
/*     */       }
/* 192 */       Method method = annotationType.members().get("value");
/* 193 */       if (method == null) {
/* 194 */         throw invalidContainerException(paramAnnotation, null);
/*     */       }
/* 196 */       method.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       return (A[])method.invoke(paramAnnotation, new Object[0]);
/*     */ 
/*     */     
/*     */     }
/* 205 */     catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|ClassCastException illegalAccessException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 210 */       throw invalidContainerException(paramAnnotation, illegalAccessException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AnnotationFormatError invalidContainerException(Annotation paramAnnotation, Throwable paramThrowable) {
/* 218 */     return new AnnotationFormatError(paramAnnotation + " is an invalid container for repeating annotations", paramThrowable);
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
/*     */   private static <A extends Annotation> void checkTypes(A[] paramArrayOfA, Annotation paramAnnotation, Class<A> paramClass) {
/* 230 */     for (A a : paramArrayOfA) {
/* 231 */       if (!paramClass.isInstance(a))
/* 232 */         throw new AnnotationFormatError(
/* 233 */             String.format("%s is an invalid container for repeating annotations of type: %s", new Object[] { paramAnnotation, paramClass })); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/AnnotationSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */