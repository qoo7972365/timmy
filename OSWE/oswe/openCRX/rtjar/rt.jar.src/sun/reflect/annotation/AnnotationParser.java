/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.lang.reflect.Type;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import sun.reflect.ConstantPool;
/*     */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*     */ import sun.reflect.generics.parser.SignatureParser;
/*     */ import sun.reflect.generics.scope.ClassScope;
/*     */ import sun.reflect.generics.tree.TypeSignature;
/*     */ import sun.reflect.generics.visitor.Reifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationParser
/*     */ {
/*     */   public static Map<Class<? extends Annotation>, Annotation> parseAnnotations(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass) {
/*  68 */     if (paramArrayOfbyte == null) {
/*  69 */       return Collections.emptyMap();
/*     */     }
/*     */     try {
/*  72 */       return parseAnnotations2(paramArrayOfbyte, paramConstantPool, paramClass, null);
/*  73 */     } catch (BufferUnderflowException bufferUnderflowException) {
/*  74 */       throw new AnnotationFormatError("Unexpected end of annotations.");
/*  75 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/*  77 */       throw new AnnotationFormatError(illegalArgumentException);
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
/*     */   @SafeVarargs
/*     */   static Map<Class<? extends Annotation>, Annotation> parseSelectAnnotations(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass, Class<? extends Annotation>... paramVarArgs) {
/*  97 */     if (paramArrayOfbyte == null) {
/*  98 */       return Collections.emptyMap();
/*     */     }
/*     */     try {
/* 101 */       return parseAnnotations2(paramArrayOfbyte, paramConstantPool, paramClass, paramVarArgs);
/* 102 */     } catch (BufferUnderflowException bufferUnderflowException) {
/* 103 */       throw new AnnotationFormatError("Unexpected end of annotations.");
/* 104 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 106 */       throw new AnnotationFormatError(illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<Class<? extends Annotation>, Annotation> parseAnnotations2(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass, Class<? extends Annotation>[] paramArrayOfClass) {
/* 115 */     LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
/*     */     
/* 117 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 118 */     int i = byteBuffer.getShort() & 0xFFFF;
/* 119 */     for (byte b = 0; b < i; b++) {
/* 120 */       Annotation annotation = parseAnnotation2(byteBuffer, paramConstantPool, paramClass, false, paramArrayOfClass);
/* 121 */       if (annotation != null) {
/* 122 */         Class<? extends Annotation> clazz = annotation.annotationType();
/* 123 */         if (AnnotationType.getInstance(clazz).retention() == RetentionPolicy.RUNTIME && linkedHashMap
/* 124 */           .put(clazz, annotation) != null) {
/* 125 */           throw new AnnotationFormatError("Duplicate annotation for class: " + clazz + ": " + annotation);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     return (Map)linkedHashMap;
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
/*     */   public static Annotation[][] parseParameterAnnotations(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass) {
/*     */     try {
/* 161 */       return parseParameterAnnotations2(paramArrayOfbyte, paramConstantPool, paramClass);
/* 162 */     } catch (BufferUnderflowException bufferUnderflowException) {
/* 163 */       throw new AnnotationFormatError("Unexpected end of parameter annotations.");
/*     */     }
/* 165 */     catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 167 */       throw new AnnotationFormatError(illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Annotation[][] parseParameterAnnotations2(byte[] paramArrayOfbyte, ConstantPool paramConstantPool, Class<?> paramClass) {
/* 175 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 176 */     int i = byteBuffer.get() & 0xFF;
/* 177 */     Annotation[][] arrayOfAnnotation = new Annotation[i][];
/*     */     
/* 179 */     for (byte b = 0; b < i; b++) {
/* 180 */       int j = byteBuffer.getShort() & 0xFFFF;
/* 181 */       ArrayList<Annotation> arrayList = new ArrayList(j);
/*     */       
/* 183 */       for (byte b1 = 0; b1 < j; b1++) {
/* 184 */         Annotation annotation = parseAnnotation(byteBuffer, paramConstantPool, paramClass, false);
/* 185 */         if (annotation != null) {
/* 186 */           AnnotationType annotationType = AnnotationType.getInstance(annotation
/* 187 */               .annotationType());
/* 188 */           if (annotationType.retention() == RetentionPolicy.RUNTIME)
/* 189 */             arrayList.add(annotation); 
/*     */         } 
/*     */       } 
/* 192 */       arrayOfAnnotation[b] = arrayList.<Annotation>toArray(EMPTY_ANNOTATIONS_ARRAY);
/*     */     } 
/* 194 */     return arrayOfAnnotation;
/*     */   }
/*     */   
/* 197 */   private static final Annotation[] EMPTY_ANNOTATIONS_ARRAY = new Annotation[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Annotation parseAnnotation(ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass, boolean paramBoolean) {
/* 226 */     return parseAnnotation2(paramByteBuffer, paramConstantPool, paramClass, paramBoolean, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Annotation parseAnnotation2(ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass, boolean paramBoolean, Class<? extends Annotation>[] paramArrayOfClass) {
/* 235 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 236 */     Class<?> clazz = null;
/* 237 */     String str = "[unknown]";
/*     */     try {
/*     */       try {
/* 240 */         str = paramConstantPool.getUTF8At(i);
/* 241 */         clazz = parseSig(str, paramClass);
/* 242 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/* 244 */         clazz = paramConstantPool.getClassAt(i);
/*     */       } 
/* 246 */     } catch (NoClassDefFoundError noClassDefFoundError) {
/* 247 */       if (paramBoolean)
/*     */       {
/*     */         
/* 250 */         throw new TypeNotPresentException(str, noClassDefFoundError); } 
/* 251 */       skipAnnotation(paramByteBuffer, false);
/* 252 */       return null;
/*     */     }
/* 254 */     catch (TypeNotPresentException typeNotPresentException) {
/* 255 */       if (paramBoolean)
/* 256 */         throw typeNotPresentException; 
/* 257 */       skipAnnotation(paramByteBuffer, false);
/* 258 */       return null;
/*     */     } 
/* 260 */     if (paramArrayOfClass != null && !contains((Object[])paramArrayOfClass, clazz)) {
/* 261 */       skipAnnotation(paramByteBuffer, false);
/* 262 */       return null;
/*     */     } 
/* 264 */     AnnotationType annotationType = null;
/*     */     try {
/* 266 */       annotationType = AnnotationType.getInstance((Class)clazz);
/* 267 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 268 */       skipAnnotation(paramByteBuffer, false);
/* 269 */       return null;
/*     */     } 
/*     */     
/* 272 */     Map<String, Class<?>> map = annotationType.memberTypes();
/*     */     
/* 274 */     LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(annotationType.memberDefaults());
/*     */     
/* 276 */     int j = paramByteBuffer.getShort() & 0xFFFF;
/* 277 */     for (byte b = 0; b < j; b++) {
/* 278 */       int k = paramByteBuffer.getShort() & 0xFFFF;
/* 279 */       String str1 = paramConstantPool.getUTF8At(k);
/* 280 */       Class<?> clazz1 = map.get(str1);
/*     */       
/* 282 */       if (clazz1 == null) {
/*     */         
/* 284 */         skipMemberValue(paramByteBuffer);
/*     */       } else {
/* 286 */         Object object = parseMemberValue(clazz1, paramByteBuffer, paramConstantPool, paramClass);
/* 287 */         if (object instanceof AnnotationTypeMismatchExceptionProxy)
/* 288 */           ((AnnotationTypeMismatchExceptionProxy)object)
/* 289 */             .setMember(annotationType.members().get(str1)); 
/* 290 */         linkedHashMap.put(str1, object);
/*     */       } 
/*     */     } 
/* 293 */     return annotationForMap((Class)clazz, linkedHashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Annotation annotationForMap(final Class<? extends Annotation> type, final Map<String, Object> memberValues) {
/* 303 */     return AccessController.<Annotation>doPrivileged(new PrivilegedAction<Annotation>() {
/*     */           public Annotation run() {
/* 305 */             return (Annotation)Proxy.newProxyInstance(type
/* 306 */                 .getClassLoader(), new Class[] { this.val$type }, new AnnotationInvocationHandler(type, memberValues));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object parseMemberValue(Class<?> paramClass1, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass2) {
/* 343 */     Object object = null;
/* 344 */     byte b = paramByteBuffer.get();
/* 345 */     switch (b) {
/*     */       case 101:
/* 347 */         return parseEnumValue((Class)paramClass1, paramByteBuffer, paramConstantPool, paramClass2);
/*     */       case 99:
/* 349 */         object = parseClassValue(paramByteBuffer, paramConstantPool, paramClass2);
/*     */         break;
/*     */       case 64:
/* 352 */         object = parseAnnotation(paramByteBuffer, paramConstantPool, paramClass2, true);
/*     */         break;
/*     */       case 91:
/* 355 */         return parseArray(paramClass1, paramByteBuffer, paramConstantPool, paramClass2);
/*     */       default:
/* 357 */         object = parseConst(b, paramByteBuffer, paramConstantPool);
/*     */         break;
/*     */     } 
/* 360 */     if (!(object instanceof ExceptionProxy) && 
/* 361 */       !paramClass1.isInstance(object))
/*     */     {
/* 363 */       object = new AnnotationTypeMismatchExceptionProxy(object.getClass() + "[" + object + "]"); } 
/* 364 */     return object;
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
/*     */   private static Object parseConst(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 379 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 380 */     switch (paramInt) {
/*     */       case 66:
/* 382 */         return Byte.valueOf((byte)paramConstantPool.getIntAt(i));
/*     */       case 67:
/* 384 */         return Character.valueOf((char)paramConstantPool.getIntAt(i));
/*     */       case 68:
/* 386 */         return Double.valueOf(paramConstantPool.getDoubleAt(i));
/*     */       case 70:
/* 388 */         return Float.valueOf(paramConstantPool.getFloatAt(i));
/*     */       case 73:
/* 390 */         return Integer.valueOf(paramConstantPool.getIntAt(i));
/*     */       case 74:
/* 392 */         return Long.valueOf(paramConstantPool.getLongAt(i));
/*     */       case 83:
/* 394 */         return Short.valueOf((short)paramConstantPool.getIntAt(i));
/*     */       case 90:
/* 396 */         return Boolean.valueOf((paramConstantPool.getIntAt(i) != 0));
/*     */       case 115:
/* 398 */         return paramConstantPool.getUTF8At(i);
/*     */     } 
/* 400 */     throw new AnnotationFormatError("Invalid member-value tag in annotation: " + paramInt);
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
/*     */   private static Object parseClassValue(ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass) {
/* 416 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/*     */     
/*     */     try {
/* 419 */       String str = paramConstantPool.getUTF8At(i);
/* 420 */       return parseSig(str, paramClass);
/* 421 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 423 */       return paramConstantPool.getClassAt(i);
/*     */     }
/* 425 */     catch (NoClassDefFoundError noClassDefFoundError) {
/* 426 */       return new TypeNotPresentExceptionProxy("[unknown]", noClassDefFoundError);
/*     */     }
/* 428 */     catch (TypeNotPresentException typeNotPresentException) {
/* 429 */       return new TypeNotPresentExceptionProxy(typeNotPresentException.typeName(), typeNotPresentException.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Class<?> parseSig(String paramString, Class<?> paramClass) {
/* 434 */     if (paramString.equals("V")) return void.class; 
/* 435 */     SignatureParser signatureParser = SignatureParser.make();
/* 436 */     TypeSignature typeSignature = signatureParser.parseTypeSig(paramString);
/* 437 */     CoreReflectionFactory coreReflectionFactory = CoreReflectionFactory.make(paramClass, ClassScope.make(paramClass));
/* 438 */     Reifier reifier = Reifier.make(coreReflectionFactory);
/* 439 */     typeSignature.accept(reifier);
/* 440 */     Type type = reifier.getResult();
/* 441 */     return toClass(type);
/*     */   }
/*     */   static Class<?> toClass(Type paramType) {
/* 444 */     if (paramType instanceof GenericArrayType)
/* 445 */       return Array.newInstance(toClass(((GenericArrayType)paramType).getGenericComponentType()), 0)
/*     */         
/* 447 */         .getClass(); 
/* 448 */     return (Class)paramType;
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
/*     */   private static Object parseEnumValue(Class<? extends Enum> paramClass, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass1) {
/* 467 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 468 */     String str1 = paramConstantPool.getUTF8At(i);
/* 469 */     int j = paramByteBuffer.getShort() & 0xFFFF;
/* 470 */     String str2 = paramConstantPool.getUTF8At(j);
/*     */     
/* 472 */     if (!str1.endsWith(";")) {
/*     */       
/* 474 */       if (!paramClass.getName().equals(str1)) {
/* 475 */         return new AnnotationTypeMismatchExceptionProxy(str1 + "." + str2);
/*     */       }
/* 477 */     } else if (paramClass != parseSig(str1, paramClass1)) {
/* 478 */       return new AnnotationTypeMismatchExceptionProxy(str1 + "." + str2);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 483 */       return Enum.valueOf(paramClass, str2);
/* 484 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 485 */       return new EnumConstantNotPresentExceptionProxy((Class)paramClass, str2);
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
/*     */   private static Object parseArray(Class<?> paramClass1, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass2) {
/* 509 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 510 */     Class<?> clazz = paramClass1.getComponentType();
/*     */     
/* 512 */     if (clazz == byte.class)
/* 513 */       return parseByteArray(i, paramByteBuffer, paramConstantPool); 
/* 514 */     if (clazz == char.class)
/* 515 */       return parseCharArray(i, paramByteBuffer, paramConstantPool); 
/* 516 */     if (clazz == double.class)
/* 517 */       return parseDoubleArray(i, paramByteBuffer, paramConstantPool); 
/* 518 */     if (clazz == float.class)
/* 519 */       return parseFloatArray(i, paramByteBuffer, paramConstantPool); 
/* 520 */     if (clazz == int.class)
/* 521 */       return parseIntArray(i, paramByteBuffer, paramConstantPool); 
/* 522 */     if (clazz == long.class)
/* 523 */       return parseLongArray(i, paramByteBuffer, paramConstantPool); 
/* 524 */     if (clazz == short.class)
/* 525 */       return parseShortArray(i, paramByteBuffer, paramConstantPool); 
/* 526 */     if (clazz == boolean.class)
/* 527 */       return parseBooleanArray(i, paramByteBuffer, paramConstantPool); 
/* 528 */     if (clazz == String.class)
/* 529 */       return parseStringArray(i, paramByteBuffer, paramConstantPool); 
/* 530 */     if (clazz == Class.class)
/* 531 */       return parseClassArray(i, paramByteBuffer, paramConstantPool, paramClass2); 
/* 532 */     if (clazz.isEnum()) {
/* 533 */       return parseEnumArray(i, (Class)clazz, paramByteBuffer, paramConstantPool, paramClass2);
/*     */     }
/*     */     
/* 536 */     assert clazz.isAnnotation();
/* 537 */     return parseAnnotationArray(i, (Class)clazz, paramByteBuffer, paramConstantPool, paramClass2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object parseByteArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 544 */     byte[] arrayOfByte = new byte[paramInt];
/* 545 */     boolean bool = false;
/* 546 */     byte b = 0;
/*     */     
/* 548 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 549 */       b = paramByteBuffer.get();
/* 550 */       if (b == 66) {
/* 551 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 552 */         arrayOfByte[b1] = (byte)paramConstantPool.getIntAt(i);
/*     */       } else {
/* 554 */         skipMemberValue(b, paramByteBuffer);
/* 555 */         bool = true;
/*     */       } 
/*     */     } 
/* 558 */     return bool ? exceptionProxy(b) : arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseCharArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 563 */     char[] arrayOfChar = new char[paramInt];
/* 564 */     boolean bool = false;
/* 565 */     byte b = 0;
/*     */     
/* 567 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 568 */       b = paramByteBuffer.get();
/* 569 */       if (b == 67) {
/* 570 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 571 */         arrayOfChar[b1] = (char)paramConstantPool.getIntAt(i);
/*     */       } else {
/* 573 */         skipMemberValue(b, paramByteBuffer);
/* 574 */         bool = true;
/*     */       } 
/*     */     } 
/* 577 */     return bool ? exceptionProxy(b) : arrayOfChar;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseDoubleArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 582 */     double[] arrayOfDouble = new double[paramInt];
/* 583 */     boolean bool = false;
/* 584 */     byte b = 0;
/*     */     
/* 586 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 587 */       b = paramByteBuffer.get();
/* 588 */       if (b == 68) {
/* 589 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 590 */         arrayOfDouble[b1] = paramConstantPool.getDoubleAt(i);
/*     */       } else {
/* 592 */         skipMemberValue(b, paramByteBuffer);
/* 593 */         bool = true;
/*     */       } 
/*     */     } 
/* 596 */     return bool ? exceptionProxy(b) : arrayOfDouble;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseFloatArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 601 */     float[] arrayOfFloat = new float[paramInt];
/* 602 */     boolean bool = false;
/* 603 */     byte b = 0;
/*     */     
/* 605 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 606 */       b = paramByteBuffer.get();
/* 607 */       if (b == 70) {
/* 608 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 609 */         arrayOfFloat[b1] = paramConstantPool.getFloatAt(i);
/*     */       } else {
/* 611 */         skipMemberValue(b, paramByteBuffer);
/* 612 */         bool = true;
/*     */       } 
/*     */     } 
/* 615 */     return bool ? exceptionProxy(b) : arrayOfFloat;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseIntArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 620 */     int[] arrayOfInt = new int[paramInt];
/* 621 */     boolean bool = false;
/* 622 */     byte b = 0;
/*     */     
/* 624 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 625 */       b = paramByteBuffer.get();
/* 626 */       if (b == 73) {
/* 627 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 628 */         arrayOfInt[b1] = paramConstantPool.getIntAt(i);
/*     */       } else {
/* 630 */         skipMemberValue(b, paramByteBuffer);
/* 631 */         bool = true;
/*     */       } 
/*     */     } 
/* 634 */     return bool ? exceptionProxy(b) : arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseLongArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 639 */     long[] arrayOfLong = new long[paramInt];
/* 640 */     boolean bool = false;
/* 641 */     byte b = 0;
/*     */     
/* 643 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 644 */       b = paramByteBuffer.get();
/* 645 */       if (b == 74) {
/* 646 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 647 */         arrayOfLong[b1] = paramConstantPool.getLongAt(i);
/*     */       } else {
/* 649 */         skipMemberValue(b, paramByteBuffer);
/* 650 */         bool = true;
/*     */       } 
/*     */     } 
/* 653 */     return bool ? exceptionProxy(b) : arrayOfLong;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseShortArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 658 */     short[] arrayOfShort = new short[paramInt];
/* 659 */     boolean bool = false;
/* 660 */     byte b = 0;
/*     */     
/* 662 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 663 */       b = paramByteBuffer.get();
/* 664 */       if (b == 83) {
/* 665 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 666 */         arrayOfShort[b1] = (short)paramConstantPool.getIntAt(i);
/*     */       } else {
/* 668 */         skipMemberValue(b, paramByteBuffer);
/* 669 */         bool = true;
/*     */       } 
/*     */     } 
/* 672 */     return bool ? exceptionProxy(b) : arrayOfShort;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseBooleanArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 677 */     boolean[] arrayOfBoolean = new boolean[paramInt];
/* 678 */     boolean bool = false;
/* 679 */     byte b = 0;
/*     */     
/* 681 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 682 */       b = paramByteBuffer.get();
/* 683 */       if (b == 90) {
/* 684 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 685 */         arrayOfBoolean[b1] = (paramConstantPool.getIntAt(i) != 0);
/*     */       } else {
/* 687 */         skipMemberValue(b, paramByteBuffer);
/* 688 */         bool = true;
/*     */       } 
/*     */     } 
/* 691 */     return bool ? exceptionProxy(b) : arrayOfBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object parseStringArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool) {
/* 696 */     String[] arrayOfString = new String[paramInt];
/* 697 */     boolean bool = false;
/* 698 */     byte b = 0;
/*     */     
/* 700 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 701 */       b = paramByteBuffer.get();
/* 702 */       if (b == 115) {
/* 703 */         int i = paramByteBuffer.getShort() & 0xFFFF;
/* 704 */         arrayOfString[b1] = paramConstantPool.getUTF8At(i);
/*     */       } else {
/* 706 */         skipMemberValue(b, paramByteBuffer);
/* 707 */         bool = true;
/*     */       } 
/*     */     } 
/* 710 */     return bool ? exceptionProxy(b) : arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object parseClassArray(int paramInt, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass) {
/* 717 */     Class[] arrayOfClass = new Class[paramInt];
/* 718 */     boolean bool = false;
/* 719 */     byte b = 0;
/*     */     
/* 721 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 722 */       b = paramByteBuffer.get();
/* 723 */       if (b == 99) {
/* 724 */         arrayOfClass[b1] = (Class)parseClassValue(paramByteBuffer, paramConstantPool, paramClass);
/*     */       } else {
/* 726 */         skipMemberValue(b, paramByteBuffer);
/* 727 */         bool = true;
/*     */       } 
/*     */     } 
/* 730 */     return bool ? exceptionProxy(b) : arrayOfClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object parseEnumArray(int paramInt, Class<? extends Enum<?>> paramClass, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass1) {
/* 737 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramClass, paramInt);
/* 738 */     boolean bool = false;
/* 739 */     byte b = 0;
/*     */     
/* 741 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 742 */       b = paramByteBuffer.get();
/* 743 */       if (b == 101) {
/* 744 */         arrayOfObject[b1] = parseEnumValue((Class)paramClass, paramByteBuffer, paramConstantPool, paramClass1);
/*     */       } else {
/* 746 */         skipMemberValue(b, paramByteBuffer);
/* 747 */         bool = true;
/*     */       } 
/*     */     } 
/* 750 */     return bool ? exceptionProxy(b) : arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object parseAnnotationArray(int paramInt, Class<? extends Annotation> paramClass, ByteBuffer paramByteBuffer, ConstantPool paramConstantPool, Class<?> paramClass1) {
/* 758 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramClass, paramInt);
/* 759 */     boolean bool = false;
/* 760 */     byte b = 0;
/*     */     
/* 762 */     for (byte b1 = 0; b1 < paramInt; b1++) {
/* 763 */       b = paramByteBuffer.get();
/* 764 */       if (b == 64) {
/* 765 */         arrayOfObject[b1] = parseAnnotation(paramByteBuffer, paramConstantPool, paramClass1, true);
/*     */       } else {
/* 767 */         skipMemberValue(b, paramByteBuffer);
/* 768 */         bool = true;
/*     */       } 
/*     */     } 
/* 771 */     return bool ? exceptionProxy(b) : arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ExceptionProxy exceptionProxy(int paramInt) {
/* 779 */     return new AnnotationTypeMismatchExceptionProxy("Array with component tag: " + paramInt);
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
/*     */   private static void skipAnnotation(ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 793 */     if (paramBoolean)
/* 794 */       paramByteBuffer.getShort(); 
/* 795 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 796 */     for (byte b = 0; b < i; b++) {
/* 797 */       paramByteBuffer.getShort();
/* 798 */       skipMemberValue(paramByteBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void skipMemberValue(ByteBuffer paramByteBuffer) {
/* 808 */     byte b = paramByteBuffer.get();
/* 809 */     skipMemberValue(b, paramByteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void skipMemberValue(int paramInt, ByteBuffer paramByteBuffer) {
/* 818 */     switch (paramInt) {
/*     */       case 101:
/* 820 */         paramByteBuffer.getInt();
/*     */         return;
/*     */       case 64:
/* 823 */         skipAnnotation(paramByteBuffer, true);
/*     */         return;
/*     */       case 91:
/* 826 */         skipArray(paramByteBuffer);
/*     */         return;
/*     */     } 
/*     */     
/* 830 */     paramByteBuffer.getShort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void skipArray(ByteBuffer paramByteBuffer) {
/* 840 */     int i = paramByteBuffer.getShort() & 0xFFFF;
/* 841 */     for (byte b = 0; b < i; b++) {
/* 842 */       skipMemberValue(paramByteBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean contains(Object[] paramArrayOfObject, Object paramObject) {
/* 850 */     for (Object object : paramArrayOfObject) {
/* 851 */       if (object == paramObject)
/* 852 */         return true; 
/* 853 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 863 */   private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
/*     */   public static Annotation[] toArray(Map<Class<? extends Annotation>, Annotation> paramMap) {
/* 865 */     return (Annotation[])paramMap.values().toArray((Object[])EMPTY_ANNOTATION_ARRAY);
/*     */   }
/*     */   static Annotation[] getEmptyAnnotationArray() {
/* 868 */     return EMPTY_ANNOTATION_ARRAY;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/AnnotationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */