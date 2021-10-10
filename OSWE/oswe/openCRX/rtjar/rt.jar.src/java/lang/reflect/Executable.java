/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.MalformedParametersException;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Parameter;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.annotation.AnnotationParser;
/*     */ import sun.reflect.annotation.AnnotationSupport;
/*     */ import sun.reflect.annotation.TypeAnnotation;
/*     */ import sun.reflect.annotation.TypeAnnotationParser;
/*     */ import sun.reflect.generics.repository.ConstructorRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Executable
/*     */   extends AccessibleObject
/*     */   implements Member, GenericDeclaration
/*     */ {
/*     */   private volatile transient boolean hasRealParameterData;
/*     */   private volatile transient Parameter[] parameters;
/*     */   private volatile transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*     */   
/*     */   abstract byte[] getAnnotationBytes();
/*     */   
/*     */   abstract Executable getRoot();
/*     */   
/*     */   abstract boolean hasGenericInformation();
/*     */   
/*     */   abstract ConstructorRepository getGenericInfo();
/*     */   
/*     */   boolean equalParamTypes(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/*  69 */     if (paramArrayOfClass1.length == paramArrayOfClass2.length) {
/*  70 */       for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/*  71 */         if (paramArrayOfClass1[b] != paramArrayOfClass2[b])
/*  72 */           return false; 
/*     */       } 
/*  74 */       return true;
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   Annotation[][] parseParameterAnnotations(byte[] paramArrayOfbyte) {
/*  80 */     return AnnotationParser.parseParameterAnnotations(paramArrayOfbyte, 
/*     */         
/*  82 */         SharedSecrets.getJavaLangAccess()
/*  83 */         .getConstantPool(getDeclaringClass()), 
/*  84 */         getDeclaringClass());
/*     */   }
/*     */   
/*     */   void separateWithCommas(Class<?>[] paramArrayOfClass, StringBuilder paramStringBuilder) {
/*  88 */     for (byte b = 0; b < paramArrayOfClass.length; b++) {
/*  89 */       paramStringBuilder.append(paramArrayOfClass[b].getTypeName());
/*  90 */       if (b < paramArrayOfClass.length - 1) {
/*  91 */         paramStringBuilder.append(",");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void printModifiersIfNonzero(StringBuilder paramStringBuilder, int paramInt, boolean paramBoolean) {
/*  97 */     int i = getModifiers() & paramInt;
/*     */     
/*  99 */     if (i != 0 && !paramBoolean) {
/* 100 */       paramStringBuilder.append(Modifier.toString(i)).append(' ');
/*     */     } else {
/* 102 */       int j = i & 0x7;
/* 103 */       if (j != 0)
/* 104 */         paramStringBuilder.append(Modifier.toString(j)).append(' '); 
/* 105 */       if (paramBoolean)
/* 106 */         paramStringBuilder.append("default "); 
/* 107 */       i &= 0xFFFFFFF8;
/* 108 */       if (i != 0) {
/* 109 */         paramStringBuilder.append(Modifier.toString(i)).append(' ');
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String sharedToString(int paramInt, boolean paramBoolean, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/*     */     try {
/* 118 */       StringBuilder stringBuilder = new StringBuilder();
/*     */       
/* 120 */       printModifiersIfNonzero(stringBuilder, paramInt, paramBoolean);
/* 121 */       specificToStringHeader(stringBuilder);
/*     */       
/* 123 */       stringBuilder.append('(');
/* 124 */       separateWithCommas(paramArrayOfClass1, stringBuilder);
/* 125 */       stringBuilder.append(')');
/* 126 */       if (paramArrayOfClass2.length > 0) {
/* 127 */         stringBuilder.append(" throws ");
/* 128 */         separateWithCommas(paramArrayOfClass2, stringBuilder);
/*     */       } 
/* 130 */       return stringBuilder.toString();
/* 131 */     } catch (Exception exception) {
/* 132 */       return "<" + exception + ">";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void specificToStringHeader(StringBuilder paramStringBuilder);
/*     */ 
/*     */ 
/*     */   
/*     */   String sharedToGenericString(int paramInt, boolean paramBoolean) {
/*     */     try {
/* 144 */       StringBuilder stringBuilder = new StringBuilder();
/*     */       
/* 146 */       printModifiersIfNonzero(stringBuilder, paramInt, paramBoolean);
/*     */       
/* 148 */       TypeVariable[] arrayOfTypeVariable = (TypeVariable[])getTypeParameters();
/* 149 */       if (arrayOfTypeVariable.length > 0) {
/* 150 */         boolean bool = true;
/* 151 */         stringBuilder.append('<');
/* 152 */         for (TypeVariable typeVariable : arrayOfTypeVariable) {
/* 153 */           if (!bool) {
/* 154 */             stringBuilder.append(',');
/*     */           }
/*     */           
/* 157 */           stringBuilder.append(typeVariable.toString());
/* 158 */           bool = false;
/*     */         } 
/* 160 */         stringBuilder.append("> ");
/*     */       } 
/*     */       
/* 163 */       specificToGenericStringHeader(stringBuilder);
/*     */       
/* 165 */       stringBuilder.append('(');
/* 166 */       Type[] arrayOfType1 = getGenericParameterTypes();
/* 167 */       for (byte b = 0; b < arrayOfType1.length; b++) {
/* 168 */         String str = arrayOfType1[b].getTypeName();
/* 169 */         if (isVarArgs() && b == arrayOfType1.length - 1)
/* 170 */           str = str.replaceFirst("\\[\\]$", "..."); 
/* 171 */         stringBuilder.append(str);
/* 172 */         if (b < arrayOfType1.length - 1)
/* 173 */           stringBuilder.append(','); 
/*     */       } 
/* 175 */       stringBuilder.append(')');
/* 176 */       Type[] arrayOfType2 = getGenericExceptionTypes();
/* 177 */       if (arrayOfType2.length > 0) {
/* 178 */         stringBuilder.append(" throws ");
/* 179 */         for (byte b1 = 0; b1 < arrayOfType2.length; b1++) {
/* 180 */           stringBuilder.append((arrayOfType2[b1] instanceof Class) ? ((Class)arrayOfType2[b1])
/* 181 */               .getName() : arrayOfType2[b1]
/* 182 */               .toString());
/* 183 */           if (b1 < arrayOfType2.length - 1)
/* 184 */             stringBuilder.append(','); 
/*     */         } 
/*     */       } 
/* 187 */       return stringBuilder.toString();
/* 188 */     } catch (Exception exception) {
/* 189 */       return "<" + exception + ">";
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
/*     */   abstract void specificToGenericStringHeader(StringBuilder paramStringBuilder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Class<?> getDeclaringClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getModifiers();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TypeVariable<?>[] getTypeParameters();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Class<?>[] getParameterTypes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParameterCount() {
/* 252 */     throw new AbstractMethodError();
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
/*     */   public Type[] getGenericParameterTypes() {
/* 282 */     if (hasGenericInformation()) {
/* 283 */       return getGenericInfo().getParameterTypes();
/*     */     }
/* 285 */     return (Type[])getParameterTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Type[] getAllGenericParameterTypes() {
/* 293 */     boolean bool1 = hasGenericInformation();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     if (!bool1) {
/* 299 */       return (Type[])getParameterTypes();
/*     */     }
/* 301 */     boolean bool2 = hasRealParameterData();
/* 302 */     Type[] arrayOfType1 = getGenericParameterTypes();
/* 303 */     Class[] arrayOfClass = getParameterTypes();
/* 304 */     Type[] arrayOfType2 = new Type[arrayOfClass.length];
/* 305 */     Parameter[] arrayOfParameter = getParameters();
/* 306 */     byte b = 0;
/*     */ 
/*     */     
/* 309 */     if (bool2) {
/* 310 */       for (byte b1 = 0; b1 < arrayOfType2.length; b1++) {
/* 311 */         Parameter parameter = arrayOfParameter[b1];
/* 312 */         if (parameter.isSynthetic() || parameter.isImplicit()) {
/*     */ 
/*     */           
/* 315 */           arrayOfType2[b1] = arrayOfClass[b1];
/*     */         } else {
/*     */           
/* 318 */           arrayOfType2[b1] = arrayOfType1[b];
/* 319 */           b++;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 328 */       return (arrayOfType1.length == arrayOfClass.length) ? arrayOfType1 : (Type[])arrayOfClass;
/*     */     } 
/*     */     
/* 331 */     return arrayOfType2;
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
/*     */   public Parameter[] getParameters() {
/* 357 */     return (Parameter[])privateGetParameters().clone();
/*     */   }
/*     */   
/*     */   private Parameter[] synthesizeAllParams() {
/* 361 */     int i = getParameterCount();
/* 362 */     Parameter[] arrayOfParameter = new Parameter[i];
/* 363 */     for (byte b = 0; b < i; b++)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 368 */       arrayOfParameter[b] = new Parameter("arg" + b, 0, this, b); } 
/* 369 */     return arrayOfParameter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyParameters(Parameter[] paramArrayOfParameter) {
/* 375 */     if ((getParameterTypes()).length != paramArrayOfParameter.length) {
/* 376 */       throw new MalformedParametersException("Wrong number of parameters in MethodParameters attribute");
/*     */     }
/* 378 */     for (Parameter parameter : paramArrayOfParameter) {
/* 379 */       String str = parameter.getRealName();
/* 380 */       int i = parameter.getModifiers();
/*     */       
/* 382 */       if (str != null && (
/* 383 */         str.isEmpty() || str.indexOf('.') != -1 || str
/* 384 */         .indexOf(';') != -1 || str.indexOf('[') != -1 || str
/* 385 */         .indexOf('/') != -1)) {
/* 386 */         throw new MalformedParametersException("Invalid parameter name \"" + str + "\"");
/*     */       }
/*     */ 
/*     */       
/* 390 */       if (i != (i & 0x9010)) {
/* 391 */         throw new MalformedParametersException("Invalid parameter modifiers");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Parameter[] privateGetParameters() {
/* 398 */     Parameter[] arrayOfParameter = this.parameters;
/*     */     
/* 400 */     if (arrayOfParameter == null) {
/*     */ 
/*     */       
/*     */       try {
/* 404 */         arrayOfParameter = getParameters0();
/* 405 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/* 407 */         throw new MalformedParametersException("Invalid constant pool index");
/*     */       } 
/*     */ 
/*     */       
/* 411 */       if (arrayOfParameter == null) {
/* 412 */         this.hasRealParameterData = false;
/* 413 */         arrayOfParameter = synthesizeAllParams();
/*     */       } else {
/* 415 */         this.hasRealParameterData = true;
/* 416 */         verifyParameters(arrayOfParameter);
/*     */       } 
/*     */       
/* 419 */       this.parameters = arrayOfParameter;
/*     */     } 
/*     */     
/* 422 */     return arrayOfParameter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasRealParameterData() {
/* 428 */     if (this.parameters == null) {
/* 429 */       privateGetParameters();
/*     */     }
/* 431 */     return this.hasRealParameterData;
/*     */   }
/*     */ 
/*     */   
/*     */   private native Parameter[] getParameters0();
/*     */ 
/*     */   
/*     */   native byte[] getTypeAnnotationBytes0();
/*     */ 
/*     */   
/*     */   byte[] getTypeAnnotationBytes() {
/* 442 */     return getTypeAnnotationBytes0();
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
/*     */   public abstract Class<?>[] getExceptionTypes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     Type[] arrayOfType;
/* 480 */     if (hasGenericInformation() && (
/* 481 */       arrayOfType = getGenericInfo().getExceptionTypes()).length > 0) {
/* 482 */       return arrayOfType;
/*     */     }
/* 484 */     return (Type[])getExceptionTypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toGenericString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVarArgs() {
/* 503 */     return ((getModifiers() & 0x80) != 0);
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
/*     */   public boolean isSynthetic() {
/* 516 */     return Modifier.isSynthetic(getModifiers());
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
/*     */   public abstract Annotation[][] getParameterAnnotations();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Annotation[][] sharedGetParameterAnnotations(Class<?>[] paramArrayOfClass, byte[] paramArrayOfbyte) {
/* 551 */     int i = paramArrayOfClass.length;
/* 552 */     if (paramArrayOfbyte == null) {
/* 553 */       return new Annotation[i][0];
/*     */     }
/* 555 */     Annotation[][] arrayOfAnnotation = parseParameterAnnotations(paramArrayOfbyte);
/*     */     
/* 557 */     if (arrayOfAnnotation.length != i)
/* 558 */       handleParameterNumberMismatch(arrayOfAnnotation.length, i); 
/* 559 */     return arrayOfAnnotation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void handleParameterNumberMismatch(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 569 */     Objects.requireNonNull(paramClass);
/* 570 */     return paramClass.cast(declaredAnnotations().get(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass) {
/* 579 */     Objects.requireNonNull(paramClass);
/*     */     
/* 581 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Annotation[] getDeclaredAnnotations() {
/* 588 */     return AnnotationParser.toArray(declaredAnnotations());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
/*     */     Map<Class<? extends Annotation>, Annotation> map;
/* 595 */     if ((map = this.declaredAnnotations) == null) {
/* 596 */       synchronized (this) {
/* 597 */         if ((map = this.declaredAnnotations) == null) {
/* 598 */           Executable executable = getRoot();
/* 599 */           if (executable != null) {
/* 600 */             map = executable.declaredAnnotations();
/*     */           } else {
/* 602 */             map = AnnotationParser.parseAnnotations(
/* 603 */                 getAnnotationBytes(), 
/* 604 */                 SharedSecrets.getJavaLangAccess()
/* 605 */                 .getConstantPool(getDeclaringClass()), 
/* 606 */                 getDeclaringClass());
/*     */           } 
/*     */           
/* 609 */           this.declaredAnnotations = map;
/*     */         } 
/*     */       } 
/*     */     }
/* 613 */     return map;
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
/*     */   public abstract AnnotatedType getAnnotatedReturnType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotatedType getAnnotatedReturnType0(Type paramType) {
/* 640 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 641 */         SharedSecrets.getJavaLangAccess()
/* 642 */         .getConstantPool(getDeclaringClass()), this, 
/*     */         
/* 644 */         getDeclaringClass(), paramType, TypeAnnotation.TypeAnnotationTarget.METHOD_RETURN);
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
/*     */   public AnnotatedType getAnnotatedReceiverType() {
/* 669 */     if (Modifier.isStatic(getModifiers()))
/* 670 */       return null; 
/* 671 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 672 */         SharedSecrets.getJavaLangAccess()
/* 673 */         .getConstantPool(getDeclaringClass()), this, 
/*     */         
/* 675 */         getDeclaringClass(), 
/* 676 */         getDeclaringClass(), TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
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
/*     */   public AnnotatedType[] getAnnotatedParameterTypes() {
/* 695 */     return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(), 
/* 696 */         SharedSecrets.getJavaLangAccess()
/* 697 */         .getConstantPool(getDeclaringClass()), this, 
/*     */         
/* 699 */         getDeclaringClass(), 
/* 700 */         getAllGenericParameterTypes(), TypeAnnotation.TypeAnnotationTarget.METHOD_FORMAL_PARAMETER);
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
/*     */   public AnnotatedType[] getAnnotatedExceptionTypes() {
/* 719 */     return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(), 
/* 720 */         SharedSecrets.getJavaLangAccess()
/* 721 */         .getConstantPool(getDeclaringClass()), this, 
/*     */         
/* 723 */         getDeclaringClass(), 
/* 724 */         getGenericExceptionTypes(), TypeAnnotation.TypeAnnotationTarget.THROWS);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Executable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */