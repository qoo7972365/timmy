/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.AnnotatedType;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Type;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.reflect.ConstantPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeAnnotationParser
/*     */ {
/*  47 */   private static final TypeAnnotation[] EMPTY_TYPE_ANNOTATION_ARRAY = new TypeAnnotation[0];
/*     */   
/*     */   private static final byte CLASS_TYPE_PARAMETER = 0;
/*     */   
/*     */   private static final byte METHOD_TYPE_PARAMETER = 1;
/*     */   
/*     */   private static final byte CLASS_EXTENDS = 16;
/*     */   
/*     */   private static final byte CLASS_TYPE_PARAMETER_BOUND = 17;
/*     */   
/*     */   private static final byte METHOD_TYPE_PARAMETER_BOUND = 18;
/*     */   
/*     */   private static final byte FIELD = 19;
/*     */   
/*     */   private static final byte METHOD_RETURN = 20;
/*     */   private static final byte METHOD_RECEIVER = 21;
/*     */   private static final byte METHOD_FORMAL_PARAMETER = 22;
/*     */   private static final byte THROWS = 23;
/*     */   private static final byte LOCAL_VARIABLE = 64;
/*     */   
/*     */   public static AnnotatedType buildAnnotatedType(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, AnnotatedElement paramAnnotatedElement, Class<?> paramClass, Type paramType, TypeAnnotation.TypeAnnotationTarget paramTypeAnnotationTarget) {
/*  68 */     TypeAnnotation[] arrayOfTypeAnnotation1 = parseTypeAnnotations(paramArrayOfbyte, paramConstantPool, paramAnnotatedElement, paramClass);
/*     */ 
/*     */ 
/*     */     
/*  72 */     ArrayList<TypeAnnotation> arrayList = new ArrayList(arrayOfTypeAnnotation1.length);
/*  73 */     for (TypeAnnotation typeAnnotation : arrayOfTypeAnnotation1) {
/*  74 */       TypeAnnotation.TypeAnnotationTargetInfo typeAnnotationTargetInfo = typeAnnotation.getTargetInfo();
/*  75 */       if (typeAnnotationTargetInfo.getTarget() == paramTypeAnnotationTarget)
/*  76 */         arrayList.add(typeAnnotation); 
/*     */     } 
/*  78 */     TypeAnnotation[] arrayOfTypeAnnotation2 = arrayList.<TypeAnnotation>toArray(EMPTY_TYPE_ANNOTATION_ARRAY);
/*  79 */     return AnnotatedTypeFactory.buildAnnotatedType(paramType, TypeAnnotation.LocationInfo.BASE_LOCATION, arrayOfTypeAnnotation2, arrayOfTypeAnnotation2, paramAnnotatedElement);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final byte RESOURCE_VARIABLE = 65;
/*     */   
/*     */   private static final byte EXCEPTION_PARAMETER = 66;
/*     */   
/*     */   private static final byte INSTANCEOF = 67;
/*     */   
/*     */   private static final byte NEW = 68;
/*     */   
/*     */   private static final byte CONSTRUCTOR_REFERENCE = 69;
/*     */   
/*     */   private static final byte METHOD_REFERENCE = 70;
/*     */   
/*     */   private static final byte CAST = 71;
/*     */   
/*     */   private static final byte CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT = 72;
/*     */   
/*     */   private static final byte METHOD_INVOCATION_TYPE_ARGUMENT = 73;
/*     */   
/*     */   private static final byte CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT = 74;
/*     */   private static final byte METHOD_REFERENCE_TYPE_ARGUMENT = 75;
/*     */   
/*     */   public static AnnotatedType[] buildAnnotatedTypes(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, AnnotatedElement paramAnnotatedElement, Class<?> paramClass, Type[] paramArrayOfType, TypeAnnotation.TypeAnnotationTarget paramTypeAnnotationTarget) {
/* 105 */     int i = paramArrayOfType.length;
/* 106 */     AnnotatedType[] arrayOfAnnotatedType = new AnnotatedType[i];
/* 107 */     Arrays.fill((Object[])arrayOfAnnotatedType, AnnotatedTypeFactory.EMPTY_ANNOTATED_TYPE);
/*     */     
/* 109 */     ArrayList[] arrayOfArrayList = new ArrayList[i];
/*     */     
/* 111 */     TypeAnnotation[] arrayOfTypeAnnotation = parseTypeAnnotations(paramArrayOfbyte, paramConstantPool, paramAnnotatedElement, paramClass);
/*     */ 
/*     */ 
/*     */     
/* 115 */     for (TypeAnnotation typeAnnotation : arrayOfTypeAnnotation) {
/* 116 */       TypeAnnotation.TypeAnnotationTargetInfo typeAnnotationTargetInfo = typeAnnotation.getTargetInfo();
/* 117 */       if (typeAnnotationTargetInfo.getTarget() == paramTypeAnnotationTarget) {
/* 118 */         int j = typeAnnotationTargetInfo.getCount();
/* 119 */         if (arrayOfArrayList[j] == null) {
/* 120 */           ArrayList arrayList1 = new ArrayList(arrayOfTypeAnnotation.length);
/* 121 */           arrayOfArrayList[j] = arrayList1;
/*     */         } 
/*     */         
/* 124 */         ArrayList<TypeAnnotation> arrayList = arrayOfArrayList[j];
/* 125 */         arrayList.add(typeAnnotation);
/*     */       } 
/*     */     } 
/* 128 */     for (byte b = 0; b < i; b++) {
/*     */       TypeAnnotation[] arrayOfTypeAnnotation1;
/* 130 */       ArrayList arrayList = arrayOfArrayList[b];
/*     */       
/* 132 */       if (arrayList != null) {
/* 133 */         arrayOfTypeAnnotation1 = (TypeAnnotation[])arrayList.toArray((Object[])new TypeAnnotation[arrayList.size()]);
/*     */       } else {
/* 135 */         arrayOfTypeAnnotation1 = EMPTY_TYPE_ANNOTATION_ARRAY;
/*     */       } 
/* 137 */       arrayOfAnnotatedType[b] = AnnotatedTypeFactory.buildAnnotatedType(paramArrayOfType[b], TypeAnnotation.LocationInfo.BASE_LOCATION, arrayOfTypeAnnotation1, arrayOfTypeAnnotation1, paramAnnotatedElement);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     return arrayOfAnnotatedType;
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
/*     */   public static AnnotatedType buildAnnotatedSuperclass(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass) {
/* 159 */     Type type = paramClass.getGenericSuperclass();
/* 160 */     if (type == null)
/* 161 */       return AnnotatedTypeFactory.EMPTY_ANNOTATED_TYPE; 
/* 162 */     return buildAnnotatedType(paramArrayOfbyte, paramConstantPool, paramClass, paramClass, type, TypeAnnotation.TypeAnnotationTarget.CLASS_EXTENDS);
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
/*     */   public static AnnotatedType[] buildAnnotatedInterfaces(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass) {
/* 181 */     if (paramClass == Object.class || paramClass
/* 182 */       .isArray() || paramClass
/* 183 */       .isPrimitive() || paramClass == void.class)
/*     */     {
/* 185 */       return AnnotatedTypeFactory.EMPTY_ANNOTATED_TYPE_ARRAY; } 
/* 186 */     return buildAnnotatedTypes(paramArrayOfbyte, paramConstantPool, paramClass, paramClass, paramClass
/*     */ 
/*     */ 
/*     */         
/* 190 */         .getGenericInterfaces(), TypeAnnotation.TypeAnnotationTarget.CLASS_IMPLEMENTS);
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
/*     */   public static <D extends java.lang.reflect.GenericDeclaration> Annotation[] parseTypeVariableAnnotations(D paramD, int paramInt) {
/*     */     Executable executable;
/*     */     TypeAnnotation.TypeAnnotationTarget typeAnnotationTarget;
/* 209 */     if (paramD instanceof Class) {
/* 210 */       Class clazz = (Class)paramD;
/* 211 */       typeAnnotationTarget = TypeAnnotation.TypeAnnotationTarget.CLASS_TYPE_PARAMETER;
/* 212 */     } else if (paramD instanceof Executable) {
/* 213 */       executable = (Executable)paramD;
/* 214 */       typeAnnotationTarget = TypeAnnotation.TypeAnnotationTarget.METHOD_TYPE_PARAMETER;
/*     */     } else {
/* 216 */       throw new AssertionError("Unknown GenericDeclaration " + paramD + "\nthis should not happen.");
/*     */     } 
/* 218 */     List<TypeAnnotation> list = TypeAnnotation.filter(parseAllTypeAnnotations(executable), typeAnnotationTarget);
/*     */     
/* 220 */     ArrayList<Annotation> arrayList = new ArrayList(list.size());
/* 221 */     for (TypeAnnotation typeAnnotation : list) {
/* 222 */       if (typeAnnotation.getTargetInfo().getCount() == paramInt)
/* 223 */         arrayList.add(typeAnnotation.getAnnotation()); 
/* 224 */     }  return arrayList.<Annotation>toArray(new Annotation[0]);
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
/*     */   public static <D extends java.lang.reflect.GenericDeclaration> AnnotatedType[] parseAnnotatedBounds(Type[] paramArrayOfType, D paramD, int paramInt) {
/* 237 */     return parseAnnotatedBounds(paramArrayOfType, paramD, paramInt, TypeAnnotation.LocationInfo.BASE_LOCATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <D extends java.lang.reflect.GenericDeclaration> AnnotatedType[] parseAnnotatedBounds(Type[] paramArrayOfType, D paramD, int paramInt, TypeAnnotation.LocationInfo paramLocationInfo) {
/* 244 */     List<TypeAnnotation> list = fetchBounds(paramD);
/* 245 */     if (paramArrayOfType != null) {
/* 246 */       byte b1 = 0;
/* 247 */       AnnotatedType[] arrayOfAnnotatedType = new AnnotatedType[paramArrayOfType.length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       if (paramArrayOfType.length > 0) {
/* 259 */         Type type = paramArrayOfType[0];
/* 260 */         if (!(type instanceof Class)) {
/* 261 */           b1 = 1;
/*     */         } else {
/* 263 */           Class clazz = (Class)type;
/* 264 */           if (clazz.isInterface()) {
/* 265 */             b1 = 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       for (byte b2 = 0; b2 < paramArrayOfType.length; b2++) {
/* 271 */         ArrayList<TypeAnnotation> arrayList = new ArrayList(list.size());
/* 272 */         for (TypeAnnotation typeAnnotation : list) {
/* 273 */           TypeAnnotation.TypeAnnotationTargetInfo typeAnnotationTargetInfo = typeAnnotation.getTargetInfo();
/* 274 */           if (typeAnnotationTargetInfo.getSecondaryIndex() == b2 + b1 && typeAnnotationTargetInfo
/* 275 */             .getCount() == paramInt) {
/* 276 */             arrayList.add(typeAnnotation);
/*     */           }
/*     */         } 
/* 279 */         arrayOfAnnotatedType[b2] = AnnotatedTypeFactory.buildAnnotatedType(paramArrayOfType[b2], paramLocationInfo, arrayList
/*     */             
/* 281 */             .<TypeAnnotation>toArray(EMPTY_TYPE_ANNOTATION_ARRAY), list
/* 282 */             .<TypeAnnotation>toArray(EMPTY_TYPE_ANNOTATION_ARRAY), (AnnotatedElement)paramD);
/*     */       } 
/*     */       
/* 285 */       return arrayOfAnnotatedType;
/*     */     } 
/* 287 */     return new AnnotatedType[0];
/*     */   }
/*     */   private static <D extends java.lang.reflect.GenericDeclaration> List<TypeAnnotation> fetchBounds(D paramD) {
/*     */     Executable executable;
/*     */     TypeAnnotation.TypeAnnotationTarget typeAnnotationTarget;
/* 292 */     if (paramD instanceof Class) {
/* 293 */       typeAnnotationTarget = TypeAnnotation.TypeAnnotationTarget.CLASS_TYPE_PARAMETER_BOUND;
/* 294 */       Class clazz = (Class)paramD;
/*     */     } else {
/* 296 */       typeAnnotationTarget = TypeAnnotation.TypeAnnotationTarget.METHOD_TYPE_PARAMETER_BOUND;
/* 297 */       executable = (Executable)paramD;
/*     */     } 
/* 299 */     return TypeAnnotation.filter(parseAllTypeAnnotations(executable), typeAnnotationTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static TypeAnnotation[] parseAllTypeAnnotations(AnnotatedElement paramAnnotatedElement) {
/*     */     Class<?> clazz;
/*     */     byte[] arrayOfByte;
/* 312 */     JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/* 313 */     if (paramAnnotatedElement instanceof Class) {
/* 314 */       clazz = (Class)paramAnnotatedElement;
/* 315 */       arrayOfByte = javaLangAccess.getRawClassTypeAnnotations(clazz);
/* 316 */     } else if (paramAnnotatedElement instanceof Executable) {
/* 317 */       clazz = ((Executable)paramAnnotatedElement).getDeclaringClass();
/* 318 */       arrayOfByte = javaLangAccess.getRawExecutableTypeAnnotations((Executable)paramAnnotatedElement);
/*     */     } else {
/*     */       
/* 321 */       return EMPTY_TYPE_ANNOTATION_ARRAY;
/*     */     } 
/* 323 */     return parseTypeAnnotations(arrayOfByte, javaLangAccess.getConstantPool(clazz), paramAnnotatedElement, clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TypeAnnotation[] parseTypeAnnotations(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, AnnotatedElement paramAnnotatedElement, Class<?> paramClass) {
/* 332 */     if (paramArrayOfbyte == null) {
/* 333 */       return EMPTY_TYPE_ANNOTATION_ARRAY;
/*     */     }
/* 335 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 336 */     int i = byteBuffer.getShort() & 0xFFFF;
/* 337 */     ArrayList<TypeAnnotation> arrayList = new ArrayList(i);
/*     */ 
/*     */     
/* 340 */     for (byte b = 0; b < i; b++) {
/* 341 */       TypeAnnotation typeAnnotation = parseTypeAnnotation(byteBuffer, paramConstantPool, paramAnnotatedElement, paramClass);
/* 342 */       if (typeAnnotation != null) {
/* 343 */         arrayList.add(typeAnnotation);
/*     */       }
/*     */     } 
/* 346 */     return arrayList.<TypeAnnotation>toArray(EMPTY_TYPE_ANNOTATION_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Map<Class<? extends Annotation>, Annotation> mapTypeAnnotations(TypeAnnotation[] paramArrayOfTypeAnnotation) {
/* 352 */     LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
/*     */     
/* 354 */     for (TypeAnnotation typeAnnotation : paramArrayOfTypeAnnotation) {
/* 355 */       Annotation annotation = typeAnnotation.getAnnotation();
/* 356 */       Class<? extends Annotation> clazz = annotation.annotationType();
/* 357 */       AnnotationType annotationType = AnnotationType.getInstance(clazz);
/* 358 */       if (annotationType.retention() == RetentionPolicy.RUNTIME && 
/* 359 */         linkedHashMap.put(clazz, annotation) != null)
/* 360 */         throw new AnnotationFormatError("Duplicate annotation for class: " + clazz + ": " + annotation); 
/*     */     } 
/* 362 */     return (Map)linkedHashMap;
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
/*     */   private static TypeAnnotation parseTypeAnnotation(ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, AnnotatedElement paramAnnotatedElement, Class<?> paramClass)
/*     */   {
/*     */     try {
/* 397 */       TypeAnnotation.TypeAnnotationTargetInfo typeAnnotationTargetInfo = parseTargetInfo(paramByteBuffer);
/* 398 */       TypeAnnotation.LocationInfo locationInfo = TypeAnnotation.LocationInfo.parseLocationInfo(paramByteBuffer);
/* 399 */       Annotation annotation = AnnotationParser.parseAnnotation(paramByteBuffer, paramConstantPool, paramClass, false);
/* 400 */       if (typeAnnotationTargetInfo == null)
/* 401 */         return null; 
/* 402 */       return new TypeAnnotation(typeAnnotationTargetInfo, locationInfo, annotation, paramAnnotatedElement);
/* 403 */     } catch (IllegalArgumentException|java.nio.BufferUnderflowException illegalArgumentException) {
/*     */       
/* 405 */       throw new AnnotationFormatError(illegalArgumentException);
/*     */     }  } private static TypeAnnotation.TypeAnnotationTargetInfo parseTargetInfo(ByteBuffer paramByteBuffer) { int j;
/*     */     TypeAnnotation.TypeAnnotationTargetInfo typeAnnotationTargetInfo;
/*     */     short s;
/*     */     byte b;
/* 410 */     int i = paramByteBuffer.get() & 0xFF;
/* 411 */     switch (i) {
/*     */       case 0:
/*     */       case 1:
/* 414 */         j = paramByteBuffer.get() & 0xFF;
/*     */         
/* 416 */         if (i == 0) {
/* 417 */           typeAnnotationTargetInfo = new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.CLASS_TYPE_PARAMETER, j);
/*     */         } else {
/*     */           
/* 420 */           typeAnnotationTargetInfo = new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.METHOD_TYPE_PARAMETER, j);
/*     */         } 
/* 422 */         return typeAnnotationTargetInfo;
/*     */       
/*     */       case 16:
/* 425 */         j = paramByteBuffer.getShort();
/* 426 */         if (j == -1)
/* 427 */           return new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.CLASS_EXTENDS); 
/* 428 */         if (j >= 0) {
/* 429 */           typeAnnotationTargetInfo = new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.CLASS_IMPLEMENTS, j);
/*     */           
/* 431 */           return typeAnnotationTargetInfo;
/*     */         }  break;
/*     */       case 17:
/* 434 */         return parse2ByteTarget(TypeAnnotation.TypeAnnotationTarget.CLASS_TYPE_PARAMETER_BOUND, paramByteBuffer);
/*     */       case 18:
/* 436 */         return parse2ByteTarget(TypeAnnotation.TypeAnnotationTarget.METHOD_TYPE_PARAMETER_BOUND, paramByteBuffer);
/*     */       case 19:
/* 438 */         return new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.FIELD);
/*     */       case 20:
/* 440 */         return new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.METHOD_RETURN);
/*     */       case 21:
/* 442 */         return new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
/*     */       case 22:
/* 444 */         j = paramByteBuffer.get() & 0xFF;
/* 445 */         return new TypeAnnotation.TypeAnnotationTargetInfo(TypeAnnotation.TypeAnnotationTarget.METHOD_FORMAL_PARAMETER, j);
/*     */ 
/*     */       
/*     */       case 23:
/* 449 */         return parseShortTarget(TypeAnnotation.TypeAnnotationTarget.THROWS, paramByteBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 64:
/*     */       case 65:
/* 457 */         j = paramByteBuffer.getShort();
/* 458 */         for (s = 0; s < j; s++) {
/* 459 */           short s1 = paramByteBuffer.getShort();
/* 460 */           short s2 = paramByteBuffer.getShort();
/* 461 */           short s3 = paramByteBuffer.getShort();
/*     */         } 
/* 463 */         return null;
/*     */       case 66:
/* 465 */         s = paramByteBuffer.get();
/*     */         
/* 467 */         return null;
/*     */       case 67:
/*     */       case 68:
/*     */       case 69:
/*     */       case 70:
/* 472 */         s = paramByteBuffer.getShort();
/*     */         
/* 474 */         return null;
/*     */       case 71:
/*     */       case 72:
/*     */       case 73:
/*     */       case 74:
/*     */       case 75:
/* 480 */         s = paramByteBuffer.getShort();
/* 481 */         b = paramByteBuffer.get();
/*     */         
/* 483 */         return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     throw new AnnotationFormatError("Could not parse bytes for type annotations"); }
/*     */ 
/*     */   
/*     */   private static TypeAnnotation.TypeAnnotationTargetInfo parseShortTarget(TypeAnnotation.TypeAnnotationTarget paramTypeAnnotationTarget, ByteBuffer paramByteBuffer) {
/* 493 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 494 */     return new TypeAnnotation.TypeAnnotationTargetInfo(paramTypeAnnotationTarget, i);
/*     */   }
/*     */   private static TypeAnnotation.TypeAnnotationTargetInfo parse2ByteTarget(TypeAnnotation.TypeAnnotationTarget paramTypeAnnotationTarget, ByteBuffer paramByteBuffer) {
/* 497 */     int i = paramByteBuffer.get() & 0xFF;
/* 498 */     int j = paramByteBuffer.get() & 0xFF;
/* 499 */     return new TypeAnnotation.TypeAnnotationTargetInfo(paramTypeAnnotationTarget, i, j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/TypeAnnotationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */