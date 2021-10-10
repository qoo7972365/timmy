/*     */ package sun.reflect.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.AnnotationFormatError;
/*     */ import java.lang.annotation.IncompleteAnnotationException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AnnotationInvocationHandler
/*     */   implements InvocationHandler, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6182022883658399397L;
/*     */   private final Class<? extends Annotation> type;
/*     */   private final Map<String, Object> memberValues;
/*     */   
/*     */   AnnotationInvocationHandler(Class<? extends Annotation> paramClass, Map<String, Object> paramMap) {
/*  48 */     Class[] arrayOfClass = paramClass.getInterfaces();
/*  49 */     if (!paramClass.isAnnotation() || arrayOfClass.length != 1 || arrayOfClass[0] != Annotation.class)
/*     */     {
/*     */       
/*  52 */       throw new AnnotationFormatError("Attempt to create proxy for a non-annotation type."); } 
/*  53 */     this.type = paramClass;
/*  54 */     this.memberValues = paramMap;
/*     */   }
/*     */   
/*     */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) {
/*  58 */     String str = paramMethod.getName();
/*  59 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/*     */ 
/*     */     
/*  62 */     if (str.equals("equals") && arrayOfClass.length == 1 && arrayOfClass[0] == Object.class)
/*     */     {
/*  64 */       return equalsImpl(paramArrayOfObject[0]); } 
/*  65 */     if (arrayOfClass.length != 0) {
/*  66 */       throw new AssertionError("Too many parameters for an annotation method");
/*     */     }
/*  68 */     switch (str) {
/*     */       case "toString":
/*  70 */         return toStringImpl();
/*     */       case "hashCode":
/*  72 */         return Integer.valueOf(hashCodeImpl());
/*     */       case "annotationType":
/*  74 */         return this.type;
/*     */     } 
/*     */ 
/*     */     
/*  78 */     Object object = this.memberValues.get(str);
/*     */     
/*  80 */     if (object == null) {
/*  81 */       throw new IncompleteAnnotationException(this.type, str);
/*     */     }
/*  83 */     if (object instanceof ExceptionProxy) {
/*  84 */       throw ((ExceptionProxy)object).generateException();
/*     */     }
/*  86 */     if (object.getClass().isArray() && Array.getLength(object) != 0) {
/*  87 */       object = cloneArray(object);
/*     */     }
/*  89 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object cloneArray(Object paramObject) {
/*  97 */     Class<?> clazz = paramObject.getClass();
/*     */     
/*  99 */     if (clazz == byte[].class) {
/* 100 */       byte[] arrayOfByte = (byte[])paramObject;
/* 101 */       return arrayOfByte.clone();
/*     */     } 
/* 103 */     if (clazz == char[].class) {
/* 104 */       char[] arrayOfChar = (char[])paramObject;
/* 105 */       return arrayOfChar.clone();
/*     */     } 
/* 107 */     if (clazz == double[].class) {
/* 108 */       double[] arrayOfDouble = (double[])paramObject;
/* 109 */       return arrayOfDouble.clone();
/*     */     } 
/* 111 */     if (clazz == float[].class) {
/* 112 */       float[] arrayOfFloat = (float[])paramObject;
/* 113 */       return arrayOfFloat.clone();
/*     */     } 
/* 115 */     if (clazz == int[].class) {
/* 116 */       int[] arrayOfInt = (int[])paramObject;
/* 117 */       return arrayOfInt.clone();
/*     */     } 
/* 119 */     if (clazz == long[].class) {
/* 120 */       long[] arrayOfLong = (long[])paramObject;
/* 121 */       return arrayOfLong.clone();
/*     */     } 
/* 123 */     if (clazz == short[].class) {
/* 124 */       short[] arrayOfShort = (short[])paramObject;
/* 125 */       return arrayOfShort.clone();
/*     */     } 
/* 127 */     if (clazz == boolean[].class) {
/* 128 */       boolean[] arrayOfBoolean = (boolean[])paramObject;
/* 129 */       return arrayOfBoolean.clone();
/*     */     } 
/*     */     
/* 132 */     Object[] arrayOfObject = (Object[])paramObject;
/* 133 */     return arrayOfObject.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toStringImpl() {
/* 141 */     StringBuilder stringBuilder = new StringBuilder(128);
/* 142 */     stringBuilder.append('@');
/* 143 */     stringBuilder.append(this.type.getName());
/* 144 */     stringBuilder.append('(');
/* 145 */     boolean bool = true;
/* 146 */     for (Map.Entry<String, Object> entry : this.memberValues.entrySet()) {
/* 147 */       if (bool) {
/* 148 */         bool = false;
/*     */       } else {
/* 150 */         stringBuilder.append(", ");
/*     */       } 
/* 152 */       stringBuilder.append((String)entry.getKey());
/* 153 */       stringBuilder.append('=');
/* 154 */       stringBuilder.append(memberValueToString(entry.getValue()));
/*     */     } 
/* 156 */     stringBuilder.append(')');
/* 157 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String memberValueToString(Object paramObject) {
/* 164 */     Class<?> clazz = paramObject.getClass();
/* 165 */     if (!clazz.isArray())
/*     */     {
/* 167 */       return paramObject.toString();
/*     */     }
/* 169 */     if (clazz == byte[].class)
/* 170 */       return Arrays.toString((byte[])paramObject); 
/* 171 */     if (clazz == char[].class)
/* 172 */       return Arrays.toString((char[])paramObject); 
/* 173 */     if (clazz == double[].class)
/* 174 */       return Arrays.toString((double[])paramObject); 
/* 175 */     if (clazz == float[].class)
/* 176 */       return Arrays.toString((float[])paramObject); 
/* 177 */     if (clazz == int[].class)
/* 178 */       return Arrays.toString((int[])paramObject); 
/* 179 */     if (clazz == long[].class)
/* 180 */       return Arrays.toString((long[])paramObject); 
/* 181 */     if (clazz == short[].class)
/* 182 */       return Arrays.toString((short[])paramObject); 
/* 183 */     if (clazz == boolean[].class)
/* 184 */       return Arrays.toString((boolean[])paramObject); 
/* 185 */     return Arrays.toString((Object[])paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean equalsImpl(Object paramObject) {
/* 192 */     if (paramObject == this) {
/* 193 */       return Boolean.valueOf(true);
/*     */     }
/* 195 */     if (!this.type.isInstance(paramObject))
/* 196 */       return Boolean.valueOf(false); 
/* 197 */     for (Method method : getMemberMethods()) {
/* 198 */       String str = method.getName();
/* 199 */       Object object1 = this.memberValues.get(str);
/* 200 */       Object object2 = null;
/* 201 */       AnnotationInvocationHandler annotationInvocationHandler = asOneOfUs(paramObject);
/* 202 */       if (annotationInvocationHandler != null) {
/* 203 */         object2 = annotationInvocationHandler.memberValues.get(str);
/*     */       } else {
/*     */         try {
/* 206 */           object2 = method.invoke(paramObject, new Object[0]);
/* 207 */         } catch (InvocationTargetException invocationTargetException) {
/* 208 */           return Boolean.valueOf(false);
/* 209 */         } catch (IllegalAccessException illegalAccessException) {
/* 210 */           throw new AssertionError(illegalAccessException);
/*     */         } 
/*     */       } 
/* 213 */       if (!memberValueEquals(object1, object2))
/* 214 */         return Boolean.valueOf(false); 
/*     */     } 
/* 216 */     return Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationInvocationHandler asOneOfUs(Object paramObject) {
/* 225 */     if (Proxy.isProxyClass(paramObject.getClass())) {
/* 226 */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(paramObject);
/* 227 */       if (invocationHandler instanceof AnnotationInvocationHandler)
/* 228 */         return (AnnotationInvocationHandler)invocationHandler; 
/*     */     } 
/* 230 */     return null;
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
/*     */   private static boolean memberValueEquals(Object paramObject1, Object paramObject2) {
/* 242 */     Class<?> clazz = paramObject1.getClass();
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (!clazz.isArray()) {
/* 247 */       return paramObject1.equals(paramObject2);
/*     */     }
/*     */ 
/*     */     
/* 251 */     if (paramObject1 instanceof Object[] && paramObject2 instanceof Object[]) {
/* 252 */       return Arrays.equals((Object[])paramObject1, (Object[])paramObject2);
/*     */     }
/*     */     
/* 255 */     if (paramObject2.getClass() != clazz) {
/* 256 */       return false;
/*     */     }
/*     */     
/* 259 */     if (clazz == byte[].class)
/* 260 */       return Arrays.equals((byte[])paramObject1, (byte[])paramObject2); 
/* 261 */     if (clazz == char[].class)
/* 262 */       return Arrays.equals((char[])paramObject1, (char[])paramObject2); 
/* 263 */     if (clazz == double[].class)
/* 264 */       return Arrays.equals((double[])paramObject1, (double[])paramObject2); 
/* 265 */     if (clazz == float[].class)
/* 266 */       return Arrays.equals((float[])paramObject1, (float[])paramObject2); 
/* 267 */     if (clazz == int[].class)
/* 268 */       return Arrays.equals((int[])paramObject1, (int[])paramObject2); 
/* 269 */     if (clazz == long[].class)
/* 270 */       return Arrays.equals((long[])paramObject1, (long[])paramObject2); 
/* 271 */     if (clazz == short[].class)
/* 272 */       return Arrays.equals((short[])paramObject1, (short[])paramObject2); 
/* 273 */     assert clazz == boolean[].class;
/* 274 */     return Arrays.equals((boolean[])paramObject1, (boolean[])paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Method[] getMemberMethods() {
/* 284 */     if (this.memberMethods == null) {
/* 285 */       this.memberMethods = AccessController.<Method[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Method[]>()
/*     */           {
/*     */             public Method[] run() {
/* 288 */               Method[] arrayOfMethod = AnnotationInvocationHandler.this.type.getDeclaredMethods();
/* 289 */               AnnotationInvocationHandler.this.validateAnnotationMethods(arrayOfMethod);
/* 290 */               AccessibleObject.setAccessible((AccessibleObject[])arrayOfMethod, true);
/* 291 */               return arrayOfMethod;
/*     */             }
/*     */           });
/*     */     }
/* 295 */     return this.memberMethods;
/*     */   }
/* 297 */   private volatile transient Method[] memberMethods = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateAnnotationMethods(Method[] paramArrayOfMethod) {
/* 311 */     boolean bool = true;
/* 312 */     for (Method method : paramArrayOfMethod) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       if (method.getModifiers() != 1025 || method
/* 324 */         .isDefault() || method
/* 325 */         .getParameterCount() != 0 || (method
/* 326 */         .getExceptionTypes()).length != 0) {
/* 327 */         bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       Class<?> clazz = method.getReturnType();
/* 341 */       if (clazz.isArray()) {
/* 342 */         clazz = clazz.getComponentType();
/* 343 */         if (clazz.isArray()) {
/* 344 */           bool = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 349 */       if ((!clazz.isPrimitive() || clazz == void.class) && clazz != String.class && clazz != Class.class && 
/*     */ 
/*     */         
/* 352 */         !clazz.isEnum() && 
/* 353 */         !clazz.isAnnotation()) {
/* 354 */         bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 373 */       String str = method.getName();
/* 374 */       if ((str.equals("toString") && clazz == String.class) || (str
/* 375 */         .equals("hashCode") && clazz == int.class) || (str
/* 376 */         .equals("annotationType") && clazz == Class.class)) {
/* 377 */         bool = false;
/*     */         break;
/*     */       } 
/*     */     } 
/* 381 */     if (bool) {
/*     */       return;
/*     */     }
/* 384 */     throw new AnnotationFormatError("Malformed method on an annotation type");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hashCodeImpl() {
/* 391 */     int i = 0;
/* 392 */     for (Map.Entry<String, Object> entry : this.memberValues.entrySet()) {
/* 393 */       i += 127 * ((String)entry.getKey()).hashCode() ^ 
/* 394 */         memberValueHashCode(entry.getValue());
/*     */     }
/* 396 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int memberValueHashCode(Object paramObject) {
/* 403 */     Class<?> clazz = paramObject.getClass();
/* 404 */     if (!clazz.isArray())
/*     */     {
/* 406 */       return paramObject.hashCode();
/*     */     }
/* 408 */     if (clazz == byte[].class)
/* 409 */       return Arrays.hashCode((byte[])paramObject); 
/* 410 */     if (clazz == char[].class)
/* 411 */       return Arrays.hashCode((char[])paramObject); 
/* 412 */     if (clazz == double[].class)
/* 413 */       return Arrays.hashCode((double[])paramObject); 
/* 414 */     if (clazz == float[].class)
/* 415 */       return Arrays.hashCode((float[])paramObject); 
/* 416 */     if (clazz == int[].class)
/* 417 */       return Arrays.hashCode((int[])paramObject); 
/* 418 */     if (clazz == long[].class)
/* 419 */       return Arrays.hashCode((long[])paramObject); 
/* 420 */     if (clazz == short[].class)
/* 421 */       return Arrays.hashCode((short[])paramObject); 
/* 422 */     if (clazz == boolean[].class)
/* 423 */       return Arrays.hashCode((boolean[])paramObject); 
/* 424 */     return Arrays.hashCode((Object[])paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 429 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 432 */     Class<? extends Annotation> clazz = (Class)getField.get("type", (Object)null);
/*     */     
/* 434 */     Map map = (Map)getField.get("memberValues", (Object)null);
/*     */ 
/*     */ 
/*     */     
/* 438 */     AnnotationType annotationType = null;
/*     */     try {
/* 440 */       annotationType = AnnotationType.getInstance(clazz);
/* 441 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 443 */       throw new InvalidObjectException("Non-annotation type in annotation serial stream");
/*     */     } 
/*     */     
/* 446 */     Map<String, Class<?>> map1 = annotationType.memberTypes();
/*     */     
/* 448 */     LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 452 */     for (Map.Entry entry : map.entrySet()) {
/* 453 */       String str = (String)entry.getKey();
/* 454 */       Object object = null;
/* 455 */       Class clazz1 = map1.get(str);
/* 456 */       if (clazz1 != null) {
/* 457 */         object = entry.getValue();
/* 458 */         if (!clazz1.isInstance(object) && !(object instanceof ExceptionProxy))
/*     */         {
/*     */           
/* 461 */           object = (new AnnotationTypeMismatchExceptionProxy(object.getClass() + "[" + object + "]")).setMember(annotationType
/* 462 */               .members().get(str));
/*     */         }
/*     */       } 
/* 465 */       linkedHashMap.put(str, object);
/*     */     } 
/*     */     
/* 468 */     UnsafeAccessor.setType(this, clazz);
/* 469 */     UnsafeAccessor.setMemberValues(this, (Map)linkedHashMap);
/*     */   }
/*     */   
/*     */   private static class UnsafeAccessor { private static final Unsafe unsafe;
/*     */     private static final long typeOffset;
/*     */     private static final long memberValuesOffset;
/*     */     
/*     */     static {
/*     */       try {
/* 478 */         unsafe = Unsafe.getUnsafe();
/*     */         
/* 480 */         typeOffset = unsafe.objectFieldOffset(AnnotationInvocationHandler.class.getDeclaredField("type"));
/*     */         
/* 482 */         memberValuesOffset = unsafe.objectFieldOffset(AnnotationInvocationHandler.class.getDeclaredField("memberValues"));
/* 483 */       } catch (Exception exception) {
/* 484 */         throw new ExceptionInInitializerError(exception);
/*     */       } 
/*     */     }
/*     */     
/*     */     static void setType(AnnotationInvocationHandler param1AnnotationInvocationHandler, Class<? extends Annotation> param1Class) {
/* 489 */       unsafe.putObject(param1AnnotationInvocationHandler, typeOffset, param1Class);
/*     */     }
/*     */ 
/*     */     
/*     */     static void setMemberValues(AnnotationInvocationHandler param1AnnotationInvocationHandler, Map<String, Object> param1Map) {
/* 494 */       unsafe.putObject(param1AnnotationInvocationHandler, memberValuesOffset, param1Map);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/annotation/AnnotationInvocationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */