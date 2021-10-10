/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.reflect.annotation.AnnotationSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Parameter
/*     */   implements AnnotatedElement
/*     */ {
/*     */   private final String name;
/*     */   private final int modifiers;
/*     */   private final Executable executable;
/*     */   private final int index;
/*     */   private volatile transient Type parameterTypeCache;
/*     */   private volatile transient Class<?> parameterClassCache;
/*     */   private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*     */     if (paramObject instanceof Parameter) {
/*     */       Parameter parameter = (Parameter)paramObject;
/*     */       return (parameter.executable.equals(this.executable) && parameter.index == this.index);
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*     */     return this.executable.hashCode() ^ this.index;
/*     */   }
/*     */   
/*     */   public boolean isNamePresent() {
/*     */     return (this.executable.hasRealParameterData() && this.name != null);
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     StringBuilder stringBuilder = new StringBuilder();
/*     */     Type type = getParameterizedType();
/*     */     String str = type.getTypeName();
/*     */     stringBuilder.append(Modifier.toString(getModifiers()));
/*     */     if (0 != this.modifiers)
/*     */       stringBuilder.append(' '); 
/*     */     if (isVarArgs()) {
/*     */       stringBuilder.append(str.replaceFirst("\\[\\]$", "..."));
/*     */     } else {
/*     */       stringBuilder.append(str);
/*     */     } 
/*     */     stringBuilder.append(' ');
/*     */     stringBuilder.append(getName());
/*     */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   Parameter(String paramString, int paramInt1, Executable paramExecutable, int paramInt2) {
/* 208 */     this.parameterTypeCache = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     this.parameterClassCache = null;
/*     */     this.name = paramString;
/*     */     this.modifiers = paramInt1;
/*     */     this.executable = paramExecutable;
/*     */     this.index = paramInt2;
/*     */   }
/*     */   public Executable getDeclaringExecutable() { return this.executable; } public int getModifiers() { return this.modifiers; } public String getName() {
/*     */     if (this.name == null || this.name.equals(""))
/*     */       return "arg" + this.index; 
/*     */     return this.name;
/*     */   } public boolean isImplicit() {
/* 251 */     return Modifier.isMandated(getModifiers());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getRealName() {
/*     */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynthetic() {
/* 265 */     return Modifier.isSynthetic(getModifiers());
/*     */   }
/*     */   public Type getParameterizedType() {
/*     */     Type type = this.parameterTypeCache;
/*     */     if (null == type) {
/*     */       type = this.executable.getAllGenericParameterTypes()[this.index];
/*     */       this.parameterTypeCache = type;
/*     */     } 
/*     */     return type;
/*     */   }
/*     */   
/* 276 */   public boolean isVarArgs() { return (this.executable.isVarArgs() && this.index == this.executable
/* 277 */       .getParameterCount() - 1); }
/*     */    public Class<?> getType() {
/*     */     Class<?> clazz = this.parameterClassCache;
/*     */     if (null == clazz) {
/*     */       clazz = this.executable.getParameterTypes()[this.index];
/*     */       this.parameterClassCache = clazz;
/*     */     } 
/*     */     return clazz;
/*     */   } public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 286 */     Objects.requireNonNull(paramClass);
/* 287 */     return paramClass.cast(declaredAnnotations().get(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedType getAnnotatedType() {
/*     */     return this.executable.getAnnotatedParameterTypes()[this.index];
/*     */   }
/*     */   
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass) {
/* 296 */     Objects.requireNonNull(paramClass);
/*     */     
/* 298 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 305 */     return this.executable.getParameterAnnotations()[this.index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getDeclaredAnnotation(Class<T> paramClass) {
/* 315 */     return getAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> paramClass) {
/* 326 */     return getAnnotationsByType(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getAnnotations() {
/* 333 */     return getDeclaredAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
/* 339 */     if (null == this.declaredAnnotations) {
/* 340 */       this.declaredAnnotations = new HashMap<>();
/*     */       
/* 342 */       Annotation[] arrayOfAnnotation = getDeclaredAnnotations();
/* 343 */       for (byte b = 0; b < arrayOfAnnotation.length; b++)
/* 344 */         this.declaredAnnotations.put(arrayOfAnnotation[b].annotationType(), arrayOfAnnotation[b]); 
/*     */     } 
/* 346 */     return this.declaredAnnotations;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */