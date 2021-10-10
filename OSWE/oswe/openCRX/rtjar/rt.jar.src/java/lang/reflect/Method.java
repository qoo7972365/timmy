/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.nio.ByteBuffer;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.MethodAccessor;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.annotation.AnnotationParser;
/*     */ import sun.reflect.annotation.AnnotationType;
/*     */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.repository.ConstructorRepository;
/*     */ import sun.reflect.generics.repository.MethodRepository;
/*     */ import sun.reflect.generics.scope.MethodScope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Method
/*     */   extends Executable
/*     */ {
/*     */   private Class<?> clazz;
/*     */   private int slot;
/*     */   private String name;
/*     */   private Class<?> returnType;
/*     */   private Class<?>[] parameterTypes;
/*     */   private Class<?>[] exceptionTypes;
/*     */   private int modifiers;
/*     */   private transient String signature;
/*     */   private transient MethodRepository genericInfo;
/*     */   private byte[] annotations;
/*     */   private byte[] parameterAnnotations;
/*     */   private byte[] annotationDefault;
/*     */   private volatile MethodAccessor methodAccessor;
/*     */   private Method root;
/*     */   
/*     */   private String getGenericSignature() {
/*  88 */     return this.signature;
/*     */   }
/*     */ 
/*     */   
/*     */   private GenericsFactory getFactory() {
/*  93 */     return CoreReflectionFactory.make(this, MethodScope.make(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MethodRepository getGenericInfo() {
/* 100 */     if (this.genericInfo == null)
/*     */     {
/* 102 */       this.genericInfo = MethodRepository.make(getGenericSignature(), 
/* 103 */           getFactory());
/*     */     }
/* 105 */     return this.genericInfo;
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
/*     */   Method(Class<?> paramClass1, String paramString1, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 124 */     this.clazz = paramClass1;
/* 125 */     this.name = paramString1;
/* 126 */     this.parameterTypes = paramArrayOfClass1;
/* 127 */     this.returnType = paramClass2;
/* 128 */     this.exceptionTypes = paramArrayOfClass2;
/* 129 */     this.modifiers = paramInt1;
/* 130 */     this.slot = paramInt2;
/* 131 */     this.signature = paramString2;
/* 132 */     this.annotations = paramArrayOfbyte1;
/* 133 */     this.parameterAnnotations = paramArrayOfbyte2;
/* 134 */     this.annotationDefault = paramArrayOfbyte3;
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
/*     */   Method copy() {
/* 150 */     if (this.root != null) {
/* 151 */       throw new IllegalArgumentException("Can not copy a non-root Method");
/*     */     }
/* 153 */     Method method = new Method(this.clazz, this.name, this.parameterTypes, this.returnType, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations, this.annotationDefault);
/*     */ 
/*     */     
/* 156 */     method.root = this;
/*     */     
/* 158 */     method.methodAccessor = this.methodAccessor;
/* 159 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Executable getRoot() {
/* 167 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasGenericInformation() {
/* 172 */     return (getGenericSignature() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] getAnnotationBytes() {
/* 177 */     return this.annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getDeclaringClass() {
/* 185 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 194 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModifiers() {
/* 202 */     return this.modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeVariable<Method>[] getTypeParameters() {
/* 213 */     if (getGenericSignature() != null) {
/* 214 */       return (TypeVariable<Method>[])getGenericInfo().getTypeParameters();
/*     */     }
/* 216 */     return (TypeVariable<Method>[])new TypeVariable[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getReturnType() {
/* 226 */     return this.returnType;
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
/*     */   public Type getGenericReturnType() {
/* 254 */     if (getGenericSignature() != null)
/* 255 */       return getGenericInfo().getReturnType(); 
/* 256 */     return getReturnType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] getParameterTypes() {
/* 264 */     return (Class[])this.parameterTypes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount() {
/* 271 */     return this.parameterTypes.length;
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
/*     */   public Type[] getGenericParameterTypes() {
/* 283 */     return super.getGenericParameterTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] getExceptionTypes() {
/* 291 */     return (Class[])this.exceptionTypes.clone();
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
/*     */   public Type[] getGenericExceptionTypes() {
/* 303 */     return super.getGenericExceptionTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 313 */     if (paramObject != null && paramObject instanceof Method) {
/* 314 */       Method method = (Method)paramObject;
/* 315 */       if (getDeclaringClass() == method.getDeclaringClass() && 
/* 316 */         getName() == method.getName()) {
/* 317 */         if (!this.returnType.equals(method.getReturnType()))
/* 318 */           return false; 
/* 319 */         return equalParamTypes(this.parameterTypes, method.parameterTypes);
/*     */       } 
/*     */     } 
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 331 */     return getDeclaringClass().getName().hashCode() ^ getName().hashCode();
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
/*     */   public String toString() {
/* 361 */     return sharedToString(Modifier.methodModifiers(), 
/* 362 */         isDefault(), this.parameterTypes, this.exceptionTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void specificToStringHeader(StringBuilder paramStringBuilder) {
/* 369 */     paramStringBuilder.append(getReturnType().getTypeName()).append(' ');
/* 370 */     paramStringBuilder.append(getDeclaringClass().getTypeName()).append('.');
/* 371 */     paramStringBuilder.append(getName());
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
/*     */   public String toGenericString() {
/* 415 */     return sharedToGenericString(Modifier.methodModifiers(), isDefault());
/*     */   }
/*     */ 
/*     */   
/*     */   void specificToGenericStringHeader(StringBuilder paramStringBuilder) {
/* 420 */     Type type = getGenericReturnType();
/* 421 */     paramStringBuilder.append(type.getTypeName()).append(' ');
/* 422 */     paramStringBuilder.append(getDeclaringClass().getTypeName()).append('.');
/* 423 */     paramStringBuilder.append(getName());
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
/*     */ 
/*     */ 
/*     */   
/*     */   @CallerSensitive
/*     */   public Object invoke(Object paramObject, Object... paramVarArgs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/* 488 */     if (!this.override && 
/* 489 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/* 490 */       Class<?> clazz = Reflection.getCallerClass();
/* 491 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*     */     } 
/*     */     
/* 494 */     MethodAccessor methodAccessor = this.methodAccessor;
/* 495 */     if (methodAccessor == null) {
/* 496 */       methodAccessor = acquireMethodAccessor();
/*     */     }
/* 498 */     return methodAccessor.invoke(paramObject, paramVarArgs);
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
/*     */   public boolean isBridge() {
/* 510 */     return ((getModifiers() & 0x40) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVarArgs() {
/* 519 */     return super.isVarArgs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynthetic() {
/* 529 */     return super.isSynthetic();
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
/*     */   public boolean isDefault() {
/* 547 */     return ((getModifiers() & 0x409) == 1 && 
/* 548 */       getDeclaringClass().isInterface());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MethodAccessor acquireMethodAccessor() {
/* 558 */     MethodAccessor methodAccessor = null;
/* 559 */     if (this.root != null) methodAccessor = this.root.getMethodAccessor(); 
/* 560 */     if (methodAccessor != null) {
/* 561 */       this.methodAccessor = methodAccessor;
/*     */     } else {
/*     */       
/* 564 */       methodAccessor = reflectionFactory.newMethodAccessor(this);
/* 565 */       setMethodAccessor(methodAccessor);
/*     */     } 
/*     */     
/* 568 */     return methodAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MethodAccessor getMethodAccessor() {
/* 574 */     return this.methodAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setMethodAccessor(MethodAccessor paramMethodAccessor) {
/* 580 */     this.methodAccessor = paramMethodAccessor;
/*     */     
/* 582 */     if (this.root != null) {
/* 583 */       this.root.setMethodAccessor(paramMethodAccessor);
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
/*     */   public Object getDefaultValue() {
/* 602 */     if (this.annotationDefault == null)
/* 603 */       return null; 
/* 604 */     Class<?> clazz = AnnotationType.invocationHandlerReturnType(
/* 605 */         getReturnType());
/* 606 */     Object object = AnnotationParser.parseMemberValue(clazz, 
/* 607 */         ByteBuffer.wrap(this.annotationDefault), 
/* 608 */         SharedSecrets.getJavaLangAccess()
/* 609 */         .getConstantPool(getDeclaringClass()), 
/* 610 */         getDeclaringClass());
/* 611 */     if (object instanceof sun.reflect.annotation.ExceptionProxy)
/* 612 */       throw new AnnotationFormatError("Invalid default: " + this); 
/* 613 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 622 */     return super.getAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 630 */     return super.getDeclaredAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[][] getParameterAnnotations() {
/* 639 */     return sharedGetParameterAnnotations(this.parameterTypes, this.parameterAnnotations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedType getAnnotatedReturnType() {
/* 648 */     return getAnnotatedReturnType0(getGenericReturnType());
/*     */   }
/*     */ 
/*     */   
/*     */   void handleParameterNumberMismatch(int paramInt1, int paramInt2) {
/* 653 */     throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Method.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */