/*     */ package sun.invoke.util;
/*     */ 
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.util.EnumMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValueConversions
/*     */ {
/*  35 */   private static final Class<?> THIS_CLASS = ValueConversions.class;
/*  36 */   private static final MethodHandles.Lookup IMPL_LOOKUP = MethodHandles.lookup();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WrapperCache
/*     */   {
/*  43 */     private final EnumMap<Wrapper, MethodHandle> map = new EnumMap<>(Wrapper.class);
/*     */     
/*     */     public MethodHandle get(Wrapper param1Wrapper) {
/*  46 */       return this.map.get(param1Wrapper);
/*     */     }
/*     */     
/*     */     public synchronized MethodHandle put(Wrapper param1Wrapper, MethodHandle param1MethodHandle) {
/*  50 */       MethodHandle methodHandle = this.map.putIfAbsent(param1Wrapper, param1MethodHandle);
/*  51 */       if (methodHandle != null) return methodHandle; 
/*  52 */       return param1MethodHandle;
/*     */     }
/*     */     private WrapperCache() {} }
/*     */   
/*     */   private static WrapperCache[] newWrapperCaches(int paramInt) {
/*  57 */     WrapperCache[] arrayOfWrapperCache = new WrapperCache[paramInt];
/*  58 */     for (byte b = 0; b < paramInt; b++)
/*  59 */       arrayOfWrapperCache[b] = new WrapperCache(); 
/*  60 */     return arrayOfWrapperCache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int unboxInteger(Integer paramInteger) {
/*  71 */     return paramInteger.intValue();
/*     */   }
/*     */   static int unboxInteger(Object paramObject, boolean paramBoolean) {
/*  74 */     if (paramObject instanceof Integer)
/*  75 */       return ((Integer)paramObject).intValue(); 
/*  76 */     return primitiveConversion(Wrapper.INT, paramObject, paramBoolean).intValue();
/*     */   }
/*     */   
/*     */   static byte unboxByte(Byte paramByte) {
/*  80 */     return paramByte.byteValue();
/*     */   }
/*     */   static byte unboxByte(Object paramObject, boolean paramBoolean) {
/*  83 */     if (paramObject instanceof Byte)
/*  84 */       return ((Byte)paramObject).byteValue(); 
/*  85 */     return primitiveConversion(Wrapper.BYTE, paramObject, paramBoolean).byteValue();
/*     */   }
/*     */   
/*     */   static short unboxShort(Short paramShort) {
/*  89 */     return paramShort.shortValue();
/*     */   }
/*     */   static short unboxShort(Object paramObject, boolean paramBoolean) {
/*  92 */     if (paramObject instanceof Short)
/*  93 */       return ((Short)paramObject).shortValue(); 
/*  94 */     return primitiveConversion(Wrapper.SHORT, paramObject, paramBoolean).shortValue();
/*     */   }
/*     */   
/*     */   static boolean unboxBoolean(Boolean paramBoolean) {
/*  98 */     return paramBoolean.booleanValue();
/*     */   }
/*     */   static boolean unboxBoolean(Object paramObject, boolean paramBoolean) {
/* 101 */     if (paramObject instanceof Boolean)
/* 102 */       return ((Boolean)paramObject).booleanValue(); 
/* 103 */     return ((primitiveConversion(Wrapper.BOOLEAN, paramObject, paramBoolean).intValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   static char unboxCharacter(Character paramCharacter) {
/* 107 */     return paramCharacter.charValue();
/*     */   }
/*     */   static char unboxCharacter(Object paramObject, boolean paramBoolean) {
/* 110 */     if (paramObject instanceof Character)
/* 111 */       return ((Character)paramObject).charValue(); 
/* 112 */     return (char)primitiveConversion(Wrapper.CHAR, paramObject, paramBoolean).intValue();
/*     */   }
/*     */   
/*     */   static long unboxLong(Long paramLong) {
/* 116 */     return paramLong.longValue();
/*     */   }
/*     */   static long unboxLong(Object paramObject, boolean paramBoolean) {
/* 119 */     if (paramObject instanceof Long)
/* 120 */       return ((Long)paramObject).longValue(); 
/* 121 */     return primitiveConversion(Wrapper.LONG, paramObject, paramBoolean).longValue();
/*     */   }
/*     */   
/*     */   static float unboxFloat(Float paramFloat) {
/* 125 */     return paramFloat.floatValue();
/*     */   }
/*     */   static float unboxFloat(Object paramObject, boolean paramBoolean) {
/* 128 */     if (paramObject instanceof Float)
/* 129 */       return ((Float)paramObject).floatValue(); 
/* 130 */     return primitiveConversion(Wrapper.FLOAT, paramObject, paramBoolean).floatValue();
/*     */   }
/*     */   
/*     */   static double unboxDouble(Double paramDouble) {
/* 134 */     return paramDouble.doubleValue();
/*     */   }
/*     */   static double unboxDouble(Object paramObject, boolean paramBoolean) {
/* 137 */     if (paramObject instanceof Double)
/* 138 */       return ((Double)paramObject).doubleValue(); 
/* 139 */     return primitiveConversion(Wrapper.DOUBLE, paramObject, paramBoolean).doubleValue();
/*     */   }
/*     */   
/*     */   private static MethodType unboxType(Wrapper paramWrapper, int paramInt) {
/* 143 */     if (paramInt == 0)
/* 144 */       return MethodType.methodType(paramWrapper.primitiveType(), paramWrapper.wrapperType()); 
/* 145 */     return MethodType.methodType(paramWrapper.primitiveType(), Object.class, new Class[] { boolean.class });
/*     */   }
/*     */   
/* 148 */   private static final WrapperCache[] UNBOX_CONVERSIONS = newWrapperCaches(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MethodHandle unbox(Wrapper paramWrapper, int paramInt) {
/* 155 */     WrapperCache wrapperCache = UNBOX_CONVERSIONS[paramInt];
/* 156 */     MethodHandle methodHandle = wrapperCache.get(paramWrapper);
/* 157 */     if (methodHandle != null) {
/* 158 */       return methodHandle;
/*     */     }
/*     */     
/* 161 */     switch (paramWrapper) {
/*     */       case OBJECT:
/*     */       case VOID:
/* 164 */         throw new IllegalArgumentException("unbox " + paramWrapper);
/*     */     } 
/*     */     
/* 167 */     String str = "unbox" + paramWrapper.wrapperSimpleName();
/* 168 */     MethodType methodType = unboxType(paramWrapper, paramInt);
/*     */     try {
/* 170 */       methodHandle = IMPL_LOOKUP.findStatic(THIS_CLASS, str, methodType);
/* 171 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 172 */       methodHandle = null;
/*     */     } 
/* 174 */     if (methodHandle != null) {
/* 175 */       if (paramInt > 0) {
/* 176 */         boolean bool = (paramInt != 2) ? true : false;
/* 177 */         methodHandle = MethodHandles.insertArguments(methodHandle, 1, new Object[] { Boolean.valueOf(bool) });
/*     */       } 
/* 179 */       if (paramInt == 1) {
/* 180 */         methodHandle = methodHandle.asType(unboxType(paramWrapper, 0));
/*     */       }
/* 182 */       return wrapperCache.put(paramWrapper, methodHandle);
/*     */     } 
/* 184 */     throw new IllegalArgumentException("cannot find unbox adapter for " + paramWrapper + ((paramInt <= 1) ? " (exact)" : ((paramInt == 3) ? " (cast)" : "")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodHandle unboxExact(Wrapper paramWrapper) {
/* 190 */     return unbox(paramWrapper, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodHandle unboxExact(Wrapper paramWrapper, boolean paramBoolean) {
/* 198 */     return unbox(paramWrapper, paramBoolean ? 0 : 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodHandle unboxWiden(Wrapper paramWrapper) {
/* 207 */     return unbox(paramWrapper, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodHandle unboxCast(Wrapper paramWrapper) {
/* 215 */     return unbox(paramWrapper, 3);
/*     */   }
/*     */   
/* 218 */   private static final Integer ZERO_INT = Integer.valueOf(0); private static final Integer ONE_INT = Integer.valueOf(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Number primitiveConversion(Wrapper paramWrapper, Object paramObject, boolean paramBoolean) {
/*     */     Number number;
/* 230 */     if (paramObject == null) {
/* 231 */       if (!paramBoolean) return null; 
/* 232 */       return ZERO_INT;
/*     */     } 
/* 234 */     if (paramObject instanceof Number) {
/* 235 */       number = (Number)paramObject;
/* 236 */     } else if (paramObject instanceof Boolean) {
/* 237 */       number = ((Boolean)paramObject).booleanValue() ? ONE_INT : ZERO_INT;
/* 238 */     } else if (paramObject instanceof Character) {
/* 239 */       number = Integer.valueOf(((Character)paramObject).charValue());
/*     */     } else {
/*     */       
/* 242 */       number = (Number)paramObject;
/*     */     } 
/* 244 */     Wrapper wrapper = Wrapper.findWrapperType(paramObject.getClass());
/* 245 */     if (wrapper == null || (!paramBoolean && !paramWrapper.isConvertibleFrom(wrapper)))
/*     */     {
/* 247 */       return (Number)paramWrapper.wrapperType().cast(paramObject); } 
/* 248 */     return number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int widenSubword(Object paramObject) {
/* 257 */     if (paramObject instanceof Integer)
/* 258 */       return ((Integer)paramObject).intValue(); 
/* 259 */     if (paramObject instanceof Boolean)
/* 260 */       return fromBoolean(((Boolean)paramObject).booleanValue()); 
/* 261 */     if (paramObject instanceof Character)
/* 262 */       return ((Character)paramObject).charValue(); 
/* 263 */     if (paramObject instanceof Short)
/* 264 */       return ((Short)paramObject).shortValue(); 
/* 265 */     if (paramObject instanceof Byte) {
/* 266 */       return ((Byte)paramObject).byteValue();
/*     */     }
/*     */     
/* 269 */     return ((Integer)paramObject).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Integer boxInteger(int paramInt) {
/* 275 */     return Integer.valueOf(paramInt);
/*     */   }
/*     */   
/*     */   static Byte boxByte(byte paramByte) {
/* 279 */     return Byte.valueOf(paramByte);
/*     */   }
/*     */   
/*     */   static Short boxShort(short paramShort) {
/* 283 */     return Short.valueOf(paramShort);
/*     */   }
/*     */   
/*     */   static Boolean boxBoolean(boolean paramBoolean) {
/* 287 */     return Boolean.valueOf(paramBoolean);
/*     */   }
/*     */   
/*     */   static Character boxCharacter(char paramChar) {
/* 291 */     return Character.valueOf(paramChar);
/*     */   }
/*     */   
/*     */   static Long boxLong(long paramLong) {
/* 295 */     return Long.valueOf(paramLong);
/*     */   }
/*     */   
/*     */   static Float boxFloat(float paramFloat) {
/* 299 */     return Float.valueOf(paramFloat);
/*     */   }
/*     */   
/*     */   static Double boxDouble(double paramDouble) {
/* 303 */     return Double.valueOf(paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   private static MethodType boxType(Wrapper paramWrapper) {
/* 308 */     Class<?> clazz = paramWrapper.wrapperType();
/* 309 */     return MethodType.methodType(clazz, paramWrapper.primitiveType());
/*     */   }
/*     */   
/* 312 */   private static final WrapperCache[] BOX_CONVERSIONS = newWrapperCaches(1);
/*     */   
/*     */   public static MethodHandle boxExact(Wrapper paramWrapper) {
/* 315 */     WrapperCache wrapperCache = BOX_CONVERSIONS[0];
/* 316 */     MethodHandle methodHandle = wrapperCache.get(paramWrapper);
/* 317 */     if (methodHandle != null) {
/* 318 */       return methodHandle;
/*     */     }
/*     */     
/* 321 */     String str = "box" + paramWrapper.wrapperSimpleName();
/* 322 */     MethodType methodType = boxType(paramWrapper);
/*     */     try {
/* 324 */       methodHandle = IMPL_LOOKUP.findStatic(THIS_CLASS, str, methodType);
/* 325 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 326 */       methodHandle = null;
/*     */     } 
/* 328 */     if (methodHandle != null) {
/* 329 */       return wrapperCache.put(paramWrapper, methodHandle);
/*     */     }
/* 331 */     throw new IllegalArgumentException("cannot find box adapter for " + paramWrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void ignore(Object paramObject) {}
/*     */ 
/*     */ 
/*     */   
/*     */   static void empty() {}
/*     */ 
/*     */   
/*     */   static Object zeroObject() {
/* 344 */     return null;
/*     */   }
/*     */   
/*     */   static int zeroInteger() {
/* 348 */     return 0;
/*     */   }
/*     */   
/*     */   static long zeroLong() {
/* 352 */     return 0L;
/*     */   }
/*     */   
/*     */   static float zeroFloat() {
/* 356 */     return 0.0F;
/*     */   }
/*     */   
/*     */   static double zeroDouble() {
/* 360 */     return 0.0D;
/*     */   }
/*     */   
/* 363 */   private static final WrapperCache[] CONSTANT_FUNCTIONS = newWrapperCaches(2); private static final MethodHandle CAST_REFERENCE;
/*     */   
/*     */   public static MethodHandle zeroConstantFunction(Wrapper paramWrapper) {
/* 366 */     WrapperCache wrapperCache = CONSTANT_FUNCTIONS[0];
/* 367 */     MethodHandle methodHandle = wrapperCache.get(paramWrapper);
/* 368 */     if (methodHandle != null) {
/* 369 */       return methodHandle;
/*     */     }
/*     */     
/* 372 */     MethodType methodType = MethodType.methodType(paramWrapper.primitiveType());
/* 373 */     switch (paramWrapper) {
/*     */       case VOID:
/* 375 */         methodHandle = EMPTY; break;
/*     */       case OBJECT: case INT:
/*     */       case LONG:
/*     */       case FLOAT:
/*     */       case DOUBLE:
/* 380 */         try { methodHandle = IMPL_LOOKUP.findStatic(THIS_CLASS, "zero" + paramWrapper.wrapperSimpleName(), methodType); }
/* 381 */         catch (ReflectiveOperationException reflectiveOperationException)
/* 382 */         { methodHandle = null; }
/*     */         
/*     */         break;
/*     */     } 
/* 386 */     if (methodHandle != null) {
/* 387 */       return wrapperCache.put(paramWrapper, methodHandle);
/*     */     }
/*     */ 
/*     */     
/* 391 */     if (paramWrapper.isSubwordOrInt() && paramWrapper != Wrapper.INT) {
/* 392 */       methodHandle = MethodHandles.explicitCastArguments(zeroConstantFunction(Wrapper.INT), methodType);
/* 393 */       return wrapperCache.put(paramWrapper, methodHandle);
/*     */     } 
/* 395 */     throw new IllegalArgumentException("cannot find zero constant for " + paramWrapper);
/*     */   }
/*     */   private static final MethodHandle IGNORE; private static final MethodHandle EMPTY;
/*     */   
/*     */   static {
/*     */     try {
/* 401 */       MethodType methodType1 = MethodType.genericMethodType(1);
/* 402 */       MethodType methodType2 = methodType1.changeReturnType(void.class);
/* 403 */       CAST_REFERENCE = IMPL_LOOKUP.findVirtual(Class.class, "cast", methodType1);
/* 404 */       IGNORE = IMPL_LOOKUP.findStatic(THIS_CLASS, "ignore", methodType2);
/* 405 */       EMPTY = IMPL_LOOKUP.findStatic(THIS_CLASS, "empty", methodType2.dropParameterTypes(0, 1));
/* 406 */     } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {
/* 407 */       throw newInternalError("uncaught exception", noSuchMethodException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MethodHandle ignore() {
/* 412 */     return IGNORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MethodHandle cast() {
/* 417 */     return CAST_REFERENCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float doubleToFloat(double paramDouble) {
/* 428 */     return (float)paramDouble;
/*     */   }
/*     */   static long doubleToLong(double paramDouble) {
/* 431 */     return (long)paramDouble;
/*     */   }
/*     */   static int doubleToInt(double paramDouble) {
/* 434 */     return (int)paramDouble;
/*     */   }
/*     */   static short doubleToShort(double paramDouble) {
/* 437 */     return (short)(int)paramDouble;
/*     */   }
/*     */   static char doubleToChar(double paramDouble) {
/* 440 */     return (char)(int)paramDouble;
/*     */   }
/*     */   static byte doubleToByte(double paramDouble) {
/* 443 */     return (byte)(int)paramDouble;
/*     */   }
/*     */   static boolean doubleToBoolean(double paramDouble) {
/* 446 */     return toBoolean((byte)(int)paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   static double floatToDouble(float paramFloat) {
/* 451 */     return paramFloat;
/*     */   }
/*     */   
/*     */   static long floatToLong(float paramFloat) {
/* 455 */     return (long)paramFloat;
/*     */   }
/*     */   static int floatToInt(float paramFloat) {
/* 458 */     return (int)paramFloat;
/*     */   }
/*     */   static short floatToShort(float paramFloat) {
/* 461 */     return (short)(int)paramFloat;
/*     */   }
/*     */   static char floatToChar(float paramFloat) {
/* 464 */     return (char)(int)paramFloat;
/*     */   }
/*     */   static byte floatToByte(float paramFloat) {
/* 467 */     return (byte)(int)paramFloat;
/*     */   }
/*     */   static boolean floatToBoolean(float paramFloat) {
/* 470 */     return toBoolean((byte)(int)paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   static double longToDouble(long paramLong) {
/* 475 */     return paramLong;
/*     */   }
/*     */   static float longToFloat(long paramLong) {
/* 478 */     return (float)paramLong;
/*     */   }
/*     */   
/*     */   static int longToInt(long paramLong) {
/* 482 */     return (int)paramLong;
/*     */   }
/*     */   static short longToShort(long paramLong) {
/* 485 */     return (short)(int)paramLong;
/*     */   }
/*     */   static char longToChar(long paramLong) {
/* 488 */     return (char)(int)paramLong;
/*     */   }
/*     */   static byte longToByte(long paramLong) {
/* 491 */     return (byte)(int)paramLong;
/*     */   }
/*     */   static boolean longToBoolean(long paramLong) {
/* 494 */     return toBoolean((byte)(int)paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   static double intToDouble(int paramInt) {
/* 499 */     return paramInt;
/*     */   }
/*     */   static float intToFloat(int paramInt) {
/* 502 */     return paramInt;
/*     */   }
/*     */   static long intToLong(int paramInt) {
/* 505 */     return paramInt;
/*     */   }
/*     */   
/*     */   static short intToShort(int paramInt) {
/* 509 */     return (short)paramInt;
/*     */   }
/*     */   static char intToChar(int paramInt) {
/* 512 */     return (char)paramInt;
/*     */   }
/*     */   static byte intToByte(int paramInt) {
/* 515 */     return (byte)paramInt;
/*     */   }
/*     */   static boolean intToBoolean(int paramInt) {
/* 518 */     return toBoolean((byte)paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   static double shortToDouble(short paramShort) {
/* 523 */     return paramShort;
/*     */   }
/*     */   static float shortToFloat(short paramShort) {
/* 526 */     return paramShort;
/*     */   }
/*     */   static long shortToLong(short paramShort) {
/* 529 */     return paramShort;
/*     */   }
/*     */   static int shortToInt(short paramShort) {
/* 532 */     return paramShort;
/*     */   }
/*     */   
/*     */   static char shortToChar(short paramShort) {
/* 536 */     return (char)paramShort;
/*     */   }
/*     */   static byte shortToByte(short paramShort) {
/* 539 */     return (byte)paramShort;
/*     */   }
/*     */   static boolean shortToBoolean(short paramShort) {
/* 542 */     return toBoolean((byte)paramShort);
/*     */   }
/*     */ 
/*     */   
/*     */   static double charToDouble(char paramChar) {
/* 547 */     return paramChar;
/*     */   }
/*     */   static float charToFloat(char paramChar) {
/* 550 */     return paramChar;
/*     */   }
/*     */   static long charToLong(char paramChar) {
/* 553 */     return paramChar;
/*     */   }
/*     */   static int charToInt(char paramChar) {
/* 556 */     return paramChar;
/*     */   }
/*     */   
/*     */   static short charToShort(char paramChar) {
/* 560 */     return (short)paramChar;
/*     */   }
/*     */   static byte charToByte(char paramChar) {
/* 563 */     return (byte)paramChar;
/*     */   }
/*     */   static boolean charToBoolean(char paramChar) {
/* 566 */     return toBoolean((byte)paramChar);
/*     */   }
/*     */ 
/*     */   
/*     */   static double byteToDouble(byte paramByte) {
/* 571 */     return paramByte;
/*     */   }
/*     */   static float byteToFloat(byte paramByte) {
/* 574 */     return paramByte;
/*     */   }
/*     */   static long byteToLong(byte paramByte) {
/* 577 */     return paramByte;
/*     */   }
/*     */   static int byteToInt(byte paramByte) {
/* 580 */     return paramByte;
/*     */   }
/*     */   static short byteToShort(byte paramByte) {
/* 583 */     return (short)paramByte;
/*     */   }
/*     */   static char byteToChar(byte paramByte) {
/* 586 */     return (char)paramByte;
/*     */   }
/*     */   
/*     */   static boolean byteToBoolean(byte paramByte) {
/* 590 */     return toBoolean(paramByte);
/*     */   }
/*     */ 
/*     */   
/*     */   static double booleanToDouble(boolean paramBoolean) {
/* 595 */     return fromBoolean(paramBoolean);
/*     */   }
/*     */   static float booleanToFloat(boolean paramBoolean) {
/* 598 */     return fromBoolean(paramBoolean);
/*     */   }
/*     */   static long booleanToLong(boolean paramBoolean) {
/* 601 */     return fromBoolean(paramBoolean);
/*     */   }
/*     */   static int booleanToInt(boolean paramBoolean) {
/* 604 */     return fromBoolean(paramBoolean);
/*     */   }
/*     */   static short booleanToShort(boolean paramBoolean) {
/* 607 */     return (short)fromBoolean(paramBoolean);
/*     */   }
/*     */   static char booleanToChar(boolean paramBoolean) {
/* 610 */     return (char)fromBoolean(paramBoolean);
/*     */   }
/*     */   static byte booleanToByte(boolean paramBoolean) {
/* 613 */     return fromBoolean(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean toBoolean(byte paramByte) {
/* 619 */     return ((paramByte & 0x1) != 0);
/*     */   }
/*     */   
/*     */   static byte fromBoolean(boolean paramBoolean) {
/* 623 */     return paramBoolean ? 1 : 0;
/*     */   }
/*     */   
/* 626 */   private static final WrapperCache[] CONVERT_PRIMITIVE_FUNCTIONS = newWrapperCaches((Wrapper.values()).length);
/*     */   
/*     */   public static MethodHandle convertPrimitive(Wrapper paramWrapper1, Wrapper paramWrapper2) {
/* 629 */     WrapperCache wrapperCache = CONVERT_PRIMITIVE_FUNCTIONS[paramWrapper1.ordinal()];
/* 630 */     MethodHandle methodHandle = wrapperCache.get(paramWrapper2);
/* 631 */     if (methodHandle != null) {
/* 632 */       return methodHandle;
/*     */     }
/*     */     
/* 635 */     Class<?> clazz1 = paramWrapper1.primitiveType();
/* 636 */     Class<?> clazz2 = paramWrapper2.primitiveType();
/* 637 */     MethodType methodType = MethodType.methodType(clazz2, clazz1);
/* 638 */     if (paramWrapper1 == paramWrapper2) {
/* 639 */       methodHandle = MethodHandles.identity(clazz1);
/*     */     } else {
/* 641 */       assert clazz1.isPrimitive() && clazz2.isPrimitive();
/*     */       try {
/* 643 */         methodHandle = IMPL_LOOKUP.findStatic(THIS_CLASS, clazz1.getSimpleName() + "To" + capitalize(clazz2.getSimpleName()), methodType);
/* 644 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 645 */         methodHandle = null;
/*     */       } 
/*     */     } 
/* 648 */     if (methodHandle != null) {
/* 649 */       assert methodHandle.type() == methodType : methodHandle;
/* 650 */       return wrapperCache.put(paramWrapper2, methodHandle);
/*     */     } 
/*     */     
/* 653 */     throw new IllegalArgumentException("cannot find primitive conversion function for " + clazz1
/* 654 */         .getSimpleName() + " -> " + clazz2.getSimpleName());
/*     */   }
/*     */   
/*     */   public static MethodHandle convertPrimitive(Class<?> paramClass1, Class<?> paramClass2) {
/* 658 */     return convertPrimitive(Wrapper.forPrimitiveType(paramClass1), Wrapper.forPrimitiveType(paramClass2));
/*     */   }
/*     */   
/*     */   private static String capitalize(String paramString) {
/* 662 */     return Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static InternalError newInternalError(String paramString, Throwable paramThrowable) {
/* 667 */     return new InternalError(paramString, paramThrowable);
/*     */   }
/*     */   private static InternalError newInternalError(Throwable paramThrowable) {
/* 670 */     return new InternalError(paramThrowable);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/invoke/util/ValueConversions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */