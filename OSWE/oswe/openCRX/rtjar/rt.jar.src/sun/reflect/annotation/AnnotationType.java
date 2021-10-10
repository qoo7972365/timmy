/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.Inherited;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationType
/*     */ {
/*     */   private final Map<String, Class<?>> memberTypes;
/*     */   private final Map<String, Object> memberDefaults;
/*     */   private final Map<String, Method> members;
/*     */   private final RetentionPolicy retention;
/*     */   private final boolean inherited;
/*     */   
/*     */   public static AnnotationType getInstance(Class<? extends Annotation> paramClass) {
/*  82 */     JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/*  83 */     AnnotationType annotationType = javaLangAccess.getAnnotationType(paramClass);
/*  84 */     if (annotationType == null) {
/*  85 */       annotationType = new AnnotationType(paramClass);
/*     */       
/*  87 */       if (!javaLangAccess.casAnnotationType(paramClass, null, annotationType)) {
/*     */         
/*  89 */         annotationType = javaLangAccess.getAnnotationType(paramClass);
/*  90 */         assert annotationType != null;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return annotationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationType(final Class<? extends Annotation> annotationClass) {
/* 105 */     if (!annotationClass.isAnnotation()) {
/* 106 */       throw new IllegalArgumentException("Not an annotation type");
/*     */     }
/*     */     
/* 109 */     Method[] arrayOfMethod = AccessController.<Method[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Method[]>()
/*     */         {
/*     */           public Method[] run() {
/* 112 */             return annotationClass.getDeclaredMethods();
/*     */           }
/*     */         });
/*     */     
/* 116 */     this.memberTypes = new HashMap<>(arrayOfMethod.length + 1, 1.0F);
/* 117 */     this.memberDefaults = new HashMap<>(0);
/* 118 */     this.members = new HashMap<>(arrayOfMethod.length + 1, 1.0F);
/*     */     
/* 120 */     for (Method method : arrayOfMethod) {
/* 121 */       if (Modifier.isPublic(method.getModifiers()) && 
/* 122 */         Modifier.isAbstract(method.getModifiers()) && 
/* 123 */         !method.isSynthetic()) {
/* 124 */         if ((method.getParameterTypes()).length != 0) {
/* 125 */           throw new IllegalArgumentException(method + " has params");
/*     */         }
/* 127 */         String str = method.getName();
/* 128 */         Class<?> clazz = method.getReturnType();
/* 129 */         this.memberTypes.put(str, invocationHandlerReturnType(clazz));
/* 130 */         this.members.put(str, method);
/*     */         
/* 132 */         Object object = method.getDefaultValue();
/* 133 */         if (object != null) {
/* 134 */           this.memberDefaults.put(str, object);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (annotationClass != Retention.class && annotationClass != Inherited.class) {
/*     */       
/* 143 */       JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/*     */       
/* 145 */       Map<Class<? extends Annotation>, Annotation> map = AnnotationParser.parseSelectAnnotations(javaLangAccess
/* 146 */           .getRawClassAnnotations(annotationClass), javaLangAccess
/* 147 */           .getConstantPool(annotationClass), annotationClass, (Class<? extends Annotation>[])new Class[] { Retention.class, Inherited.class });
/*     */ 
/*     */ 
/*     */       
/* 151 */       Retention retention = (Retention)map.get(Retention.class);
/* 152 */       this.retention = (retention == null) ? RetentionPolicy.CLASS : retention.value();
/* 153 */       this.inherited = map.containsKey(Inherited.class);
/*     */     } else {
/*     */       
/* 156 */       this.retention = RetentionPolicy.RUNTIME;
/* 157 */       this.inherited = false;
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
/*     */   public static Class<?> invocationHandlerReturnType(Class<?> paramClass) {
/* 169 */     if (paramClass == byte.class)
/* 170 */       return Byte.class; 
/* 171 */     if (paramClass == char.class)
/* 172 */       return Character.class; 
/* 173 */     if (paramClass == double.class)
/* 174 */       return Double.class; 
/* 175 */     if (paramClass == float.class)
/* 176 */       return Float.class; 
/* 177 */     if (paramClass == int.class)
/* 178 */       return Integer.class; 
/* 179 */     if (paramClass == long.class)
/* 180 */       return Long.class; 
/* 181 */     if (paramClass == short.class)
/* 182 */       return Short.class; 
/* 183 */     if (paramClass == boolean.class) {
/* 184 */       return Boolean.class;
/*     */     }
/*     */     
/* 187 */     return paramClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Class<?>> memberTypes() {
/* 195 */     return this.memberTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Method> members() {
/* 203 */     return this.members;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> memberDefaults() {
/* 211 */     return this.memberDefaults;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RetentionPolicy retention() {
/* 218 */     return this.retention;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInherited() {
/* 225 */     return this.inherited;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 232 */     return "Annotation Type:\n   Member types: " + this.memberTypes + "\n   Member defaults: " + this.memberDefaults + "\n   Retention policy: " + this.retention + "\n   Inherited: " + this.inherited;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/AnnotationType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */