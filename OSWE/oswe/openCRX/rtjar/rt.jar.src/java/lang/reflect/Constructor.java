/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.ConstructorAccessor;
/*     */ import sun.reflect.Reflection;
/*     */ import sun.reflect.annotation.TypeAnnotation;
/*     */ import sun.reflect.annotation.TypeAnnotationParser;
/*     */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*     */ import sun.reflect.generics.factory.GenericsFactory;
/*     */ import sun.reflect.generics.repository.ConstructorRepository;
/*     */ import sun.reflect.generics.scope.ConstructorScope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Constructor<T>
/*     */   extends Executable
/*     */ {
/*     */   private Class<T> clazz;
/*     */   private int slot;
/*     */   private Class<?>[] parameterTypes;
/*     */   private Class<?>[] exceptionTypes;
/*     */   private int modifiers;
/*     */   private transient String signature;
/*     */   private transient ConstructorRepository genericInfo;
/*     */   private byte[] annotations;
/*     */   private byte[] parameterAnnotations;
/*     */   private volatile ConstructorAccessor constructorAccessor;
/*     */   private Constructor<T> root;
/*     */   
/*     */   private GenericsFactory getFactory() {
/*  77 */     return CoreReflectionFactory.make(this, ConstructorScope.make(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConstructorRepository getGenericInfo() {
/*  84 */     if (this.genericInfo == null)
/*     */     {
/*  86 */       this
/*  87 */         .genericInfo = ConstructorRepository.make(getSignature(), 
/*  88 */           getFactory());
/*     */     }
/*  90 */     return this.genericInfo;
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
/*     */   Executable getRoot() {
/* 107 */     return this.root;
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
/*     */   Constructor(Class<T> paramClass, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 123 */     this.clazz = paramClass;
/* 124 */     this.parameterTypes = paramArrayOfClass1;
/* 125 */     this.exceptionTypes = paramArrayOfClass2;
/* 126 */     this.modifiers = paramInt1;
/* 127 */     this.slot = paramInt2;
/* 128 */     this.signature = paramString;
/* 129 */     this.annotations = paramArrayOfbyte1;
/* 130 */     this.parameterAnnotations = paramArrayOfbyte2;
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
/*     */   Constructor<T> copy() {
/* 146 */     if (this.root != null) {
/* 147 */       throw new IllegalArgumentException("Can not copy a non-root Constructor");
/*     */     }
/* 149 */     Constructor<T> constructor = new Constructor(this.clazz, this.parameterTypes, this.exceptionTypes, this.modifiers, this.slot, this.signature, this.annotations, this.parameterAnnotations);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     constructor.root = this;
/*     */     
/* 157 */     constructor.constructorAccessor = this.constructorAccessor;
/* 158 */     return constructor;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasGenericInformation() {
/* 163 */     return (getSignature() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] getAnnotationBytes() {
/* 168 */     return this.annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getDeclaringClass() {
/* 176 */     return this.clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 185 */     return getDeclaringClass().getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModifiers() {
/* 193 */     return this.modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeVariable<Constructor<T>>[] getTypeParameters() {
/* 204 */     if (getSignature() != null) {
/* 205 */       return (TypeVariable<Constructor<T>>[])getGenericInfo().getTypeParameters();
/*     */     }
/* 207 */     return (TypeVariable<Constructor<T>>[])new TypeVariable[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] getParameterTypes() {
/* 216 */     return (Class[])this.parameterTypes.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount() {
/* 223 */     return this.parameterTypes.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getGenericParameterTypes() {
/* 234 */     return super.getGenericParameterTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?>[] getExceptionTypes() {
/* 242 */     return (Class[])this.exceptionTypes.clone();
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
/*     */   public Type[] getGenericExceptionTypes() {
/* 255 */     return super.getGenericExceptionTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 265 */     if (paramObject != null && paramObject instanceof Constructor) {
/* 266 */       Constructor<T> constructor = (Constructor)paramObject;
/* 267 */       if (getDeclaringClass() == constructor.getDeclaringClass()) {
/* 268 */         return equalParamTypes(this.parameterTypes, constructor.parameterTypes);
/*     */       }
/*     */     } 
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 280 */     return getDeclaringClass().getName().hashCode();
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
/*     */   public String toString() {
/* 302 */     return sharedToString(Modifier.constructorModifiers(), false, this.parameterTypes, this.exceptionTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void specificToStringHeader(StringBuilder paramStringBuilder) {
/* 310 */     paramStringBuilder.append(getDeclaringClass().getTypeName());
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
/*     */   public String toGenericString() {
/* 349 */     return sharedToGenericString(Modifier.constructorModifiers(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   void specificToGenericStringHeader(StringBuilder paramStringBuilder) {
/* 354 */     specificToStringHeader(paramStringBuilder);
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
/*     */   @CallerSensitive
/*     */   public T newInstance(Object... paramVarArgs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/* 410 */     if (!this.override && 
/* 411 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/* 412 */       Class<?> clazz = Reflection.getCallerClass();
/* 413 */       checkAccess(clazz, this.clazz, null, this.modifiers);
/*     */     } 
/*     */     
/* 416 */     if ((this.clazz.getModifiers() & 0x4000) != 0)
/* 417 */       throw new IllegalArgumentException("Cannot reflectively create enum objects"); 
/* 418 */     ConstructorAccessor constructorAccessor = this.constructorAccessor;
/* 419 */     if (constructorAccessor == null) {
/* 420 */       constructorAccessor = acquireConstructorAccessor();
/*     */     }
/*     */     
/* 423 */     return (T)constructorAccessor.newInstance(paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVarArgs() {
/* 433 */     return super.isVarArgs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynthetic() {
/* 443 */     return super.isSynthetic();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ConstructorAccessor acquireConstructorAccessor() {
/* 454 */     ConstructorAccessor constructorAccessor = null;
/* 455 */     if (this.root != null) constructorAccessor = this.root.getConstructorAccessor(); 
/* 456 */     if (constructorAccessor != null) {
/* 457 */       this.constructorAccessor = constructorAccessor;
/*     */     } else {
/*     */       
/* 460 */       constructorAccessor = reflectionFactory.newConstructorAccessor(this);
/* 461 */       setConstructorAccessor(constructorAccessor);
/*     */     } 
/*     */     
/* 464 */     return constructorAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ConstructorAccessor getConstructorAccessor() {
/* 470 */     return this.constructorAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void setConstructorAccessor(ConstructorAccessor paramConstructorAccessor) {
/* 476 */     this.constructorAccessor = paramConstructorAccessor;
/*     */     
/* 478 */     if (this.root != null) {
/* 479 */       this.root.setConstructorAccessor(paramConstructorAccessor);
/*     */     }
/*     */   }
/*     */   
/*     */   int getSlot() {
/* 484 */     return this.slot;
/*     */   }
/*     */   
/*     */   String getSignature() {
/* 488 */     return this.signature;
/*     */   }
/*     */   
/*     */   byte[] getRawAnnotations() {
/* 492 */     return this.annotations;
/*     */   }
/*     */   
/*     */   byte[] getRawParameterAnnotations() {
/* 496 */     return this.parameterAnnotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 506 */     return super.getAnnotation(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 514 */     return super.getDeclaredAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[][] getParameterAnnotations() {
/* 523 */     return sharedGetParameterAnnotations(this.parameterTypes, this.parameterAnnotations);
/*     */   }
/*     */ 
/*     */   
/*     */   void handleParameterNumberMismatch(int paramInt1, int paramInt2) {
/* 528 */     Class<T> clazz = getDeclaringClass();
/* 529 */     if (clazz.isEnum() || clazz
/* 530 */       .isAnonymousClass() || clazz
/* 531 */       .isLocalClass()) {
/*     */       return;
/*     */     }
/* 534 */     if (!clazz.isMemberClass() || (clazz
/*     */ 
/*     */       
/* 537 */       .isMemberClass() && (clazz
/* 538 */       .getModifiers() & 0x8) == 0 && paramInt1 + 1 != paramInt2))
/*     */     {
/* 540 */       throw new AnnotationFormatError("Parameter annotations don't match number of parameters");
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
/*     */   public AnnotatedType getAnnotatedReturnType() {
/* 552 */     return getAnnotatedReturnType0(getDeclaringClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotatedType getAnnotatedReceiverType() {
/* 561 */     if (getDeclaringClass().getEnclosingClass() == null) {
/* 562 */       return super.getAnnotatedReceiverType();
/*     */     }
/* 564 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 565 */         SharedSecrets.getJavaLangAccess()
/* 566 */         .getConstantPool(getDeclaringClass()), this, 
/*     */         
/* 568 */         getDeclaringClass(), 
/* 569 */         getDeclaringClass().getEnclosingClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Constructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */