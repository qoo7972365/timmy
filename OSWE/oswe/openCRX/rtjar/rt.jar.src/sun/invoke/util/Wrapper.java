/*     */ package sun.invoke.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Wrapper
/*     */ {
/*  30 */   BOOLEAN(Boolean.class, boolean.class, 'Z', Boolean.valueOf(false), new boolean[0], Format.unsigned(1)),
/*     */   
/*  32 */   BYTE(Byte.class, byte.class, 'B', Byte.valueOf((byte)0), new byte[0], Format.signed(8)),
/*  33 */   SHORT(Short.class, short.class, 'S', Short.valueOf((short)0), new short[0], Format.signed(16)),
/*  34 */   CHAR(Character.class, char.class, 'C', Character.valueOf(false), new char[0], Format.unsigned(16)),
/*  35 */   INT(Integer.class, int.class, 'I', Integer.valueOf(0), new int[0], Format.signed(32)),
/*  36 */   LONG(Long.class, long.class, 'J', Long.valueOf(0L), new long[0], Format.signed(64)),
/*  37 */   FLOAT(Float.class, float.class, 'F', Float.valueOf(0.0F), new float[0], Format.floating(32)),
/*  38 */   DOUBLE(Double.class, double.class, 'D', Double.valueOf(0.0D), new double[0], Format.floating(64)),
/*  39 */   OBJECT(Object.class, Object.class, 'L', null, new Object[0], Format.other(1)),
/*     */   
/*  41 */   VOID(Void.class, void.class, 'V', null, null, Format.other(0)); private final Class<?> wrapperType;
/*     */   private final Class<?> primitiveType;
/*     */   private final char basicTypeChar;
/*     */   private final Object zero;
/*     */   private final Object emptyArray;
/*     */   private final int format;
/*     */   private final String wrapperSimpleName;
/*     */   private final String primitiveSimpleName;
/*     */   private static final Wrapper[] FROM_PRIM;
/*     */   private static final Wrapper[] FROM_WRAP;
/*     */   private static final Wrapper[] FROM_CHAR;
/*     */   
/*     */   Wrapper(Class<?> paramClass1, Class<?> paramClass2, char paramChar, Object paramObject1, Object paramObject2, int paramInt1) {
/*  54 */     this.wrapperType = paramClass1;
/*  55 */     this.primitiveType = paramClass2;
/*  56 */     this.basicTypeChar = paramChar;
/*  57 */     this.zero = paramObject1;
/*  58 */     this.emptyArray = paramObject2;
/*  59 */     this.format = paramInt1;
/*  60 */     this.wrapperSimpleName = paramClass1.getSimpleName();
/*  61 */     this.primitiveSimpleName = paramClass2.getSimpleName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String detailString() {
/*  66 */     return this.wrapperSimpleName + 
/*  67 */       Arrays.<Object>asList(new Object[] {
/*  68 */           this.wrapperType, this.primitiveType, Character.valueOf(this.basicTypeChar), this.zero, "0x" + 
/*  69 */           Integer.toHexString(this.format) });
/*     */   }
/*     */   private static abstract class Format { static final int SLOT_SHIFT = 0; static final int SIZE_SHIFT = 2; static final int KIND_SHIFT = 12; static final int SIGNED = -4096; static final int UNSIGNED = 0; static final int FLOATING = 4096; static final int SLOT_MASK = 3;
/*     */     static final int SIZE_MASK = 1023;
/*     */     static final int INT = -3967;
/*     */     static final int SHORT = -4031;
/*     */     static final int BOOLEAN = 5;
/*     */     static final int CHAR = 65;
/*     */     static final int FLOAT = 4225;
/*     */     static final int VOID = 0;
/*     */     static final int NUM_MASK = -4;
/*     */     
/*     */     static int format(int param1Int1, int param1Int2, int param1Int3) {
/*  82 */       assert param1Int1 >> 12 << 12 == param1Int1;
/*  83 */       assert (param1Int2 & param1Int2 - 1) == 0; assert false;
/*  84 */       throw new AssertionError();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static int signed(int param1Int) {
/* 101 */       return format(-4096, param1Int, (param1Int > 32) ? 2 : 1);
/* 102 */     } static int unsigned(int param1Int) { return format(0, param1Int, (param1Int > 32) ? 2 : 1); }
/* 103 */     static int floating(int param1Int) { return format(4096, param1Int, (param1Int > 32) ? 2 : 1); } static int other(int param1Int) {
/* 104 */       return param1Int << 0;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public int bitWidth() {
/* 110 */     return this.format >> 2 & 0x3FF;
/*     */   } public int stackSlots() {
/* 112 */     return this.format >> 0 & 0x3;
/*     */   } public boolean isSingleWord() {
/* 114 */     return ((this.format & 0x1) != 0);
/*     */   } public boolean isDoubleWord() {
/* 116 */     return ((this.format & 0x2) != 0);
/*     */   } public boolean isNumeric() {
/* 118 */     return ((this.format & 0xFFFFFFFC) != 0);
/*     */   } public boolean isIntegral() {
/* 120 */     return (isNumeric() && this.format < 4225);
/*     */   } public boolean isSubwordOrInt() {
/* 122 */     return (isIntegral() && isSingleWord());
/*     */   } public boolean isSigned() {
/* 124 */     return (this.format < 0);
/*     */   } public boolean isUnsigned() {
/* 126 */     return (this.format >= 5 && this.format < 4225);
/*     */   } public boolean isFloating() {
/* 128 */     return (this.format >= 4225);
/*     */   } public boolean isOther() {
/* 130 */     return ((this.format & 0xFFFFFFFC) == 0);
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
/*     */   public boolean isConvertibleFrom(Wrapper paramWrapper) {
/* 143 */     if (this == paramWrapper) return true; 
/* 144 */     if (compareTo(paramWrapper) < 0)
/*     */     {
/* 146 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 150 */     boolean bool = ((this.format & paramWrapper.format & 0xFFFFF000) != 0) ? true : false;
/* 151 */     if (!bool) {
/* 152 */       if (isOther()) return true;
/*     */       
/* 154 */       if (paramWrapper.format == 65) return true;
/*     */       
/* 156 */       return false;
/*     */     } 
/*     */     
/* 159 */     assert isFloating() || isSigned();
/* 160 */     assert paramWrapper.isFloating() || paramWrapper.isSigned();
/* 161 */     return true;
/*     */   }
/*     */   
/* 164 */   static { assert checkConvertibleFrom();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     FROM_PRIM = new Wrapper[16];
/* 308 */     FROM_WRAP = new Wrapper[16];
/* 309 */     FROM_CHAR = new Wrapper[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     for (Wrapper wrapper : values())
/* 326 */     { int i = hashPrim(wrapper.primitiveType);
/* 327 */       int j = hashWrap(wrapper.wrapperType);
/* 328 */       int k = hashChar(wrapper.basicTypeChar);
/* 329 */       assert FROM_PRIM[i] == null;
/* 330 */       assert FROM_WRAP[j] == null;
/* 331 */       assert FROM_CHAR[k] == null;
/* 332 */       FROM_PRIM[i] = wrapper;
/* 333 */       FROM_WRAP[j] = wrapper;
/* 334 */       FROM_CHAR[k] = wrapper; }  }
/*     */   private static boolean checkConvertibleFrom() { for (Wrapper wrapper : values()) { assert wrapper.isConvertibleFrom(wrapper); assert VOID.isConvertibleFrom(wrapper); if (wrapper != VOID) { assert OBJECT.isConvertibleFrom(wrapper); assert !wrapper.isConvertibleFrom(VOID); }  if (wrapper != CHAR) { assert !CHAR.isConvertibleFrom(wrapper); if (!wrapper.isConvertibleFrom(INT) && !$assertionsDisabled && wrapper.isConvertibleFrom(CHAR)) throw new AssertionError();  }  if (wrapper != BOOLEAN) { assert !BOOLEAN.isConvertibleFrom(wrapper); if (wrapper != VOID && wrapper != OBJECT && !$assertionsDisabled && wrapper.isConvertibleFrom(BOOLEAN)) throw new AssertionError();  }  if (wrapper.isSigned()) for (Wrapper wrapper1 : values()) { if (wrapper != wrapper1) if (wrapper1.isFloating()) { assert !wrapper.isConvertibleFrom(wrapper1); } else if (wrapper1.isSigned()) { if (wrapper.compareTo(wrapper1) < 0) { assert !wrapper.isConvertibleFrom(wrapper1); } else { assert wrapper.isConvertibleFrom(wrapper1); }  }   }   if (wrapper.isFloating()) for (Wrapper wrapper1 : values()) { if (wrapper != wrapper1) if (wrapper1.isSigned()) { assert wrapper.isConvertibleFrom(wrapper1); } else if (wrapper1.isFloating()) { if (wrapper.compareTo(wrapper1) < 0) { assert !wrapper.isConvertibleFrom(wrapper1); } else { assert wrapper.isConvertibleFrom(wrapper1); }  }   }   }  return true; }
/*     */   public Object zero() { return this.zero; }
/*     */   public <T> T zero(Class<T> paramClass) { return convert(this.zero, paramClass); }
/*     */   public static Wrapper forPrimitiveType(Class<?> paramClass) { Wrapper wrapper = findPrimitiveType(paramClass); if (wrapper != null) return wrapper;  if (paramClass.isPrimitive()) throw new InternalError();  throw newIllegalArgumentException("not primitive: " + paramClass); }
/*     */   static Wrapper findPrimitiveType(Class<?> paramClass) { Wrapper wrapper = FROM_PRIM[hashPrim(paramClass)]; if (wrapper != null && wrapper.primitiveType == paramClass) return wrapper;  return null; }
/* 340 */   public static Wrapper forWrapperType(Class<?> paramClass) { Wrapper wrapper = findWrapperType(paramClass); if (wrapper != null) return wrapper;  for (Wrapper wrapper1 : values()) { if (wrapper1.wrapperType == paramClass) throw new InternalError();  }  throw newIllegalArgumentException("not wrapper: " + paramClass); } public Class<?> primitiveType() { return this.primitiveType; }
/*     */   static Wrapper findWrapperType(Class<?> paramClass) { Wrapper wrapper = FROM_WRAP[hashWrap(paramClass)]; if (wrapper != null && wrapper.wrapperType == paramClass) return wrapper;  return null; }
/*     */   public static Wrapper forBasicType(char paramChar) { Wrapper wrapper = FROM_CHAR[hashChar(paramChar)]; if (wrapper != null && wrapper.basicTypeChar == paramChar) return wrapper;  for (Wrapper wrapper1 : values()) { if (wrapper.basicTypeChar == paramChar) throw new InternalError();  }  throw newIllegalArgumentException("not basic type char: " + paramChar); }
/* 343 */   public static Wrapper forBasicType(Class<?> paramClass) { if (paramClass.isPrimitive()) return forPrimitiveType(paramClass);  return OBJECT; } private static int hashPrim(Class<?> paramClass) { String str = paramClass.getName(); if (str.length() < 3) return 0;  return (str.charAt(0) + str.charAt(2)) % 16; } private static int hashWrap(Class<?> paramClass) { String str = paramClass.getName(); assert 10 == "java.lang.".length(); if (str.length() < 13) return 0;  return (3 * str.charAt(11) + str.charAt(12)) % 16; } private static int hashChar(char paramChar) { return (paramChar + (paramChar >> 1)) % 16; } public Class<?> wrapperType() { return this.wrapperType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Class<T> wrapperType(Class<T> paramClass) {
/* 353 */     if (paramClass == this.wrapperType)
/* 354 */       return paramClass; 
/* 355 */     if (paramClass == this.primitiveType || this.wrapperType == Object.class || paramClass
/*     */       
/* 357 */       .isInterface()) {
/* 358 */       return forceType(this.wrapperType, paramClass);
/*     */     }
/* 360 */     throw newClassCastException(paramClass, this.primitiveType);
/*     */   }
/*     */   
/*     */   private static ClassCastException newClassCastException(Class<?> paramClass1, Class<?> paramClass2) {
/* 364 */     return new ClassCastException(paramClass1 + " is not compatible with " + paramClass2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Class<T> asWrapperType(Class<T> paramClass) {
/* 371 */     if (paramClass.isPrimitive()) {
/* 372 */       return forPrimitiveType(paramClass).wrapperType(paramClass);
/*     */     }
/* 374 */     return paramClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Class<T> asPrimitiveType(Class<T> paramClass) {
/* 381 */     Wrapper wrapper = findWrapperType(paramClass);
/* 382 */     if (wrapper != null) {
/* 383 */       return forceType(wrapper.primitiveType(), paramClass);
/*     */     }
/* 385 */     return paramClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isWrapperType(Class<?> paramClass) {
/* 390 */     return (findWrapperType(paramClass) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPrimitiveType(Class<?> paramClass) {
/* 395 */     return paramClass.isPrimitive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char basicTypeChar(Class<?> paramClass) {
/* 402 */     if (!paramClass.isPrimitive()) {
/* 403 */       return 'L';
/*     */     }
/* 405 */     return forPrimitiveType(paramClass).basicTypeChar();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char basicTypeChar() {
/* 411 */     return this.basicTypeChar;
/*     */   }
/*     */   
/*     */   public String wrapperSimpleName() {
/* 415 */     return this.wrapperSimpleName;
/*     */   }
/*     */   
/*     */   public String primitiveSimpleName() {
/* 419 */     return this.primitiveSimpleName;
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
/*     */   public <T> T cast(Object paramObject, Class<T> paramClass) {
/* 439 */     return convert(paramObject, paramClass, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T convert(Object paramObject, Class<T> paramClass) {
/* 448 */     return convert(paramObject, paramClass, false);
/*     */   }
/*     */   
/*     */   private <T> T convert(Object paramObject, Class<T> paramClass, boolean paramBoolean) {
/* 452 */     if (this == OBJECT) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 457 */       assert !paramClass.isPrimitive();
/* 458 */       if (!paramClass.isInterface()) {
/* 459 */         paramClass.cast(paramObject);
/*     */       }
/* 461 */       return (T)paramObject;
/*     */     } 
/*     */     
/* 464 */     Class<T> clazz = wrapperType(paramClass);
/* 465 */     if (clazz.isInstance(paramObject)) {
/* 466 */       return clazz.cast(paramObject);
/*     */     }
/* 468 */     if (!paramBoolean) {
/* 469 */       Class<?> clazz1 = paramObject.getClass();
/* 470 */       Wrapper wrapper = findWrapperType(clazz1);
/* 471 */       if (wrapper == null || !isConvertibleFrom(wrapper)) {
/* 472 */         throw newClassCastException(clazz, clazz1);
/*     */       }
/* 474 */     } else if (paramObject == null) {
/*     */       
/* 476 */       return (T)this.zero;
/*     */     } 
/*     */ 
/*     */     
/* 480 */     Object object = wrap(paramObject);
/* 481 */     assert ((object == null) ? (Class)Void.class : (Class)object.getClass()) == clazz;
/* 482 */     return (T)object;
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
/*     */   static <T> Class<T> forceType(Class<?> paramClass, Class<T> paramClass1) {
/* 494 */     boolean bool = (paramClass == paramClass1 || (paramClass.isPrimitive() && forPrimitiveType(paramClass) == findWrapperType(paramClass1)) || (paramClass1.isPrimitive() && forPrimitiveType(paramClass1) == findWrapperType(paramClass)) || (paramClass == Object.class && !paramClass1.isPrimitive())) ? true : false;
/* 495 */     if (!bool) {
/* 496 */       System.out.println(paramClass + " <= " + paramClass1);
/*     */     }
/* 498 */     assert (paramClass1
/* 499 */       .isPrimitive() && forPrimitiveType(paramClass1) == findWrapperType(paramClass)) || (paramClass == Object.class && 
/* 500 */       !paramClass1.isPrimitive());
/*     */     
/* 502 */     return (Class)paramClass;
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
/*     */   public Object wrap(Object paramObject) {
/* 516 */     switch (this.basicTypeChar) { case 'L':
/* 517 */         return paramObject;
/* 518 */       case 'V': return null; }
/*     */     
/* 520 */     Number number = numberValue(paramObject);
/* 521 */     switch (this.basicTypeChar) { case 'I':
/* 522 */         return Integer.valueOf(number.intValue());
/* 523 */       case 'J': return Long.valueOf(number.longValue());
/* 524 */       case 'F': return Float.valueOf(number.floatValue());
/* 525 */       case 'D': return Double.valueOf(number.doubleValue());
/* 526 */       case 'S': return Short.valueOf((short)number.intValue());
/* 527 */       case 'B': return Byte.valueOf((byte)number.intValue());
/* 528 */       case 'C': return Character.valueOf((char)number.intValue());
/* 529 */       case 'Z': return Boolean.valueOf(boolValue(number.byteValue())); }
/*     */     
/* 531 */     throw new InternalError("bad wrapper");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object wrap(int paramInt) {
/* 541 */     if (this.basicTypeChar == 'L') return Integer.valueOf(paramInt); 
/* 542 */     switch (this.basicTypeChar) { case 'L':
/* 543 */         throw newIllegalArgumentException("cannot wrap to object type");
/* 544 */       case 'V': return null;
/* 545 */       case 'I': return Integer.valueOf(paramInt);
/* 546 */       case 'J': return Long.valueOf(paramInt);
/* 547 */       case 'F': return Float.valueOf(paramInt);
/* 548 */       case 'D': return Double.valueOf(paramInt);
/* 549 */       case 'S': return Short.valueOf((short)paramInt);
/* 550 */       case 'B': return Byte.valueOf((byte)paramInt);
/* 551 */       case 'C': return Character.valueOf((char)paramInt);
/* 552 */       case 'Z': return Boolean.valueOf(boolValue((byte)paramInt)); }
/*     */     
/* 554 */     throw new InternalError("bad wrapper");
/*     */   }
/*     */   
/*     */   private static Number numberValue(Object paramObject) {
/* 558 */     if (paramObject instanceof Number) return (Number)paramObject; 
/* 559 */     if (paramObject instanceof Character) return Integer.valueOf(((Character)paramObject).charValue()); 
/* 560 */     if (paramObject instanceof Boolean) return Integer.valueOf(((Boolean)paramObject).booleanValue() ? 1 : 0);
/*     */     
/* 562 */     return (Number)paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean boolValue(byte paramByte) {
/* 569 */     paramByte = (byte)(paramByte & 0x1);
/* 570 */     return (paramByte != 0);
/*     */   }
/*     */   
/*     */   private static RuntimeException newIllegalArgumentException(String paramString, Object paramObject) {
/* 574 */     return newIllegalArgumentException(paramString + paramObject);
/*     */   }
/*     */   private static RuntimeException newIllegalArgumentException(String paramString) {
/* 577 */     return new IllegalArgumentException(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object makeArray(int paramInt) {
/* 582 */     return Array.newInstance(this.primitiveType, paramInt);
/*     */   }
/*     */   public Class<?> arrayType() {
/* 585 */     return this.emptyArray.getClass();
/*     */   }
/*     */   public void copyArrayUnboxing(Object[] paramArrayOfObject, int paramInt1, Object paramObject, int paramInt2, int paramInt3) {
/* 588 */     if (paramObject.getClass() != arrayType())
/* 589 */       arrayType().cast(paramObject); 
/* 590 */     for (byte b = 0; b < paramInt3; b++) {
/* 591 */       Object object = paramArrayOfObject[b + paramInt1];
/* 592 */       object = convert(object, this.primitiveType);
/* 593 */       Array.set(paramObject, b + paramInt2, object);
/*     */     } 
/*     */   }
/*     */   public void copyArrayBoxing(Object paramObject, int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3) {
/* 597 */     if (paramObject.getClass() != arrayType())
/* 598 */       arrayType().cast(paramObject); 
/* 599 */     for (byte b = 0; b < paramInt3; b++) {
/* 600 */       Object object = Array.get(paramObject, b + paramInt1);
/*     */       
/* 602 */       assert object.getClass() == this.wrapperType;
/* 603 */       paramArrayOfObject[b + paramInt2] = object;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/Wrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */