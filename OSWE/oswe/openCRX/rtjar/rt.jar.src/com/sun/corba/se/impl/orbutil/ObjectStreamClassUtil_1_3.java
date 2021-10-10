/*     */ package com.sun.corba.se.impl.orbutil;
/*     */ 
/*     */ import com.sun.corba.se.impl.io.ObjectStreamClass;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectStreamClassUtil_1_3
/*     */ {
/*     */   public static long computeSerialVersionUID(Class paramClass) {
/*  60 */     long l = ObjectStreamClass.getSerialVersionUID(paramClass);
/*  61 */     if (l == 0L) {
/*  62 */       return l;
/*     */     }
/*  64 */     l = getSerialVersion(l, paramClass).longValue();
/*  65 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Long getSerialVersion(final long csuid, final Class cl) {
/*  75 */     return AccessController.<Long>doPrivileged(new PrivilegedAction<Long>() {
/*     */           public Object run() {
/*     */             long l;
/*     */             try {
/*  79 */               Field field = cl.getDeclaredField("serialVersionUID");
/*  80 */               int i = field.getModifiers();
/*  81 */               if (Modifier.isStatic(i) && 
/*  82 */                 Modifier.isFinal(i) && Modifier.isPrivate(i)) {
/*  83 */                 l = csuid;
/*     */               } else {
/*  85 */                 l = ObjectStreamClassUtil_1_3._computeSerialVersionUID(cl);
/*     */               } 
/*  87 */             } catch (NoSuchFieldException noSuchFieldException) {
/*  88 */               l = ObjectStreamClassUtil_1_3._computeSerialVersionUID(cl);
/*     */             } 
/*     */ 
/*     */             
/*  92 */             return new Long(l);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static long computeStructuralUID(boolean paramBoolean, Class<?> paramClass) {
/*  98 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
/*     */     
/* 100 */     long l = 0L;
/*     */     
/*     */     try {
/* 103 */       if (!Serializable.class.isAssignableFrom(paramClass) || paramClass
/* 104 */         .isInterface()) {
/* 105 */         return 0L;
/*     */       }
/*     */       
/* 108 */       if (Externalizable.class.isAssignableFrom(paramClass)) {
/* 109 */         return 1L;
/*     */       }
/*     */       
/* 112 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 113 */       DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, messageDigest);
/* 114 */       DataOutputStream dataOutputStream = new DataOutputStream(digestOutputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       Class<?> clazz = paramClass.getSuperclass();
/* 123 */       if (clazz != null && clazz != Object.class) {
/* 124 */         boolean bool = false;
/* 125 */         Class[] arrayOfClass = { ObjectOutputStream.class };
/* 126 */         Method method = getDeclaredMethod(clazz, "writeObject", arrayOfClass, 2, 8);
/*     */         
/* 128 */         if (method != null)
/* 129 */           bool = true; 
/* 130 */         dataOutputStream.writeLong(computeStructuralUID(bool, clazz));
/*     */       } 
/*     */       
/* 133 */       if (paramBoolean) {
/* 134 */         dataOutputStream.writeInt(2);
/*     */       } else {
/* 136 */         dataOutputStream.writeInt(1);
/*     */       } 
/*     */       
/* 139 */       Field[] arrayOfField = getDeclaredFields(paramClass);
/* 140 */       Arrays.sort(arrayOfField, compareMemberByName);
/*     */       
/* 142 */       for (byte b = 0; b < arrayOfField.length; b++) {
/* 143 */         Field field = arrayOfField[b];
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         int k = field.getModifiers();
/* 149 */         if (!Modifier.isTransient(k) && !Modifier.isStatic(k)) {
/*     */ 
/*     */           
/* 152 */           dataOutputStream.writeUTF(field.getName());
/* 153 */           dataOutputStream.writeUTF(getSignature(field.getType()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 159 */       dataOutputStream.flush();
/* 160 */       byte[] arrayOfByte = messageDigest.digest();
/* 161 */       int i = Math.min(8, arrayOfByte.length);
/* 162 */       for (int j = i; j > 0; j--) {
/* 163 */         l += (arrayOfByte[j] & 0xFF) << j * 8;
/*     */       }
/* 165 */     } catch (IOException iOException) {
/*     */       
/* 167 */       l = -1L;
/* 168 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 169 */       throw new SecurityException(noSuchAlgorithmException.getMessage());
/*     */     } 
/* 171 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long _computeSerialVersionUID(Class paramClass) {
/* 180 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
/*     */     
/* 182 */     long l = 0L;
/*     */     try {
/* 184 */       MessageDigest messageDigest = MessageDigest.getInstance("SHA");
/* 185 */       DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, messageDigest);
/* 186 */       DataOutputStream dataOutputStream = new DataOutputStream(digestOutputStream);
/*     */ 
/*     */       
/* 189 */       dataOutputStream.writeUTF(paramClass.getName());
/*     */       
/* 191 */       int i = paramClass.getModifiers();
/* 192 */       i &= 0x611;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       Method[] arrayOfMethod = paramClass.getDeclaredMethods();
/* 203 */       if ((i & 0x200) != 0) {
/* 204 */         i &= 0xFFFFFBFF;
/* 205 */         if (arrayOfMethod.length > 0) {
/* 206 */           i |= 0x400;
/*     */         }
/*     */       } 
/*     */       
/* 210 */       dataOutputStream.writeInt(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (!paramClass.isArray()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 225 */         Class[] arrayOfClass = paramClass.getInterfaces();
/* 226 */         Arrays.sort((Class<?>[][])arrayOfClass, compareClassByName);
/*     */         
/* 228 */         for (byte b = 0; b < arrayOfClass.length; b++) {
/* 229 */           dataOutputStream.writeUTF(arrayOfClass[b].getName());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 234 */       Field[] arrayOfField = paramClass.getDeclaredFields();
/* 235 */       Arrays.sort(arrayOfField, compareMemberByName);
/*     */       
/* 237 */       for (byte b1 = 0; b1 < arrayOfField.length; b1++) {
/* 238 */         Field field = arrayOfField[b1];
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         int j = field.getModifiers();
/* 244 */         if (!Modifier.isPrivate(j) || (
/* 245 */           !Modifier.isTransient(j) && !Modifier.isStatic(j))) {
/*     */ 
/*     */           
/* 248 */           dataOutputStream.writeUTF(field.getName());
/* 249 */           dataOutputStream.writeInt(j);
/* 250 */           dataOutputStream.writeUTF(getSignature(field.getType()));
/*     */         } 
/*     */       } 
/*     */       
/* 254 */       if (hasStaticInitializer(paramClass)) {
/* 255 */         dataOutputStream.writeUTF("<clinit>");
/* 256 */         dataOutputStream.writeInt(8);
/* 257 */         dataOutputStream.writeUTF("()V");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 267 */       MethodSignature[] arrayOfMethodSignature1 = MethodSignature.removePrivateAndSort((Member[])paramClass.getDeclaredConstructors());
/* 268 */       for (byte b2 = 0; b2 < arrayOfMethodSignature1.length; b2++) {
/* 269 */         MethodSignature methodSignature = arrayOfMethodSignature1[b2];
/* 270 */         String str1 = "<init>";
/* 271 */         String str2 = methodSignature.signature;
/* 272 */         str2 = str2.replace('/', '.');
/* 273 */         dataOutputStream.writeUTF(str1);
/* 274 */         dataOutputStream.writeInt(methodSignature.member.getModifiers());
/* 275 */         dataOutputStream.writeUTF(str2);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       MethodSignature[] arrayOfMethodSignature2 = MethodSignature.removePrivateAndSort((Member[])arrayOfMethod);
/* 283 */       for (byte b3 = 0; b3 < arrayOfMethodSignature2.length; b3++) {
/* 284 */         MethodSignature methodSignature = arrayOfMethodSignature2[b3];
/* 285 */         String str = methodSignature.signature;
/* 286 */         str = str.replace('/', '.');
/* 287 */         dataOutputStream.writeUTF(methodSignature.member.getName());
/* 288 */         dataOutputStream.writeInt(methodSignature.member.getModifiers());
/* 289 */         dataOutputStream.writeUTF(str);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       dataOutputStream.flush();
/* 296 */       byte[] arrayOfByte = messageDigest.digest();
/* 297 */       for (byte b4 = 0; b4 < Math.min(8, arrayOfByte.length); b4++) {
/* 298 */         l += (arrayOfByte[b4] & 0xFF) << b4 * 8;
/*     */       }
/* 300 */     } catch (IOException iOException) {
/*     */       
/* 302 */       l = -1L;
/* 303 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 304 */       throw new SecurityException(noSuchAlgorithmException.getMessage());
/*     */     } 
/* 306 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   private static Comparator compareClassByName = new CompareClassByName();
/*     */   
/*     */   private static class CompareClassByName
/*     */     implements Comparator {
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/* 317 */       Class clazz1 = (Class)param1Object1;
/* 318 */       Class clazz2 = (Class)param1Object2;
/* 319 */       return clazz1.getName().compareTo(clazz2.getName());
/*     */     }
/*     */ 
/*     */     
/*     */     private CompareClassByName() {}
/*     */   }
/*     */   
/* 326 */   private static Comparator compareMemberByName = new CompareMemberByName();
/*     */   
/*     */   private static class CompareMemberByName
/*     */     implements Comparator {
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/* 331 */       String str1 = ((Member)param1Object1).getName();
/* 332 */       String str2 = ((Member)param1Object2).getName();
/*     */       
/* 334 */       if (param1Object1 instanceof Method) {
/* 335 */         str1 = str1 + ObjectStreamClassUtil_1_3.getSignature((Method)param1Object1);
/* 336 */         str2 = str2 + ObjectStreamClassUtil_1_3.getSignature((Method)param1Object2);
/* 337 */       } else if (param1Object1 instanceof Constructor) {
/* 338 */         str1 = str1 + ObjectStreamClassUtil_1_3.getSignature((Constructor)param1Object1);
/* 339 */         str2 = str2 + ObjectStreamClassUtil_1_3.getSignature((Constructor)param1Object2);
/*     */       } 
/* 341 */       return str1.compareTo(str2);
/*     */     }
/*     */ 
/*     */     
/*     */     private CompareMemberByName() {}
/*     */   }
/*     */   
/*     */   private static String getSignature(Class<?> paramClass) {
/* 349 */     String str = null;
/* 350 */     if (paramClass.isArray()) {
/* 351 */       Class<?> clazz = paramClass;
/* 352 */       byte b1 = 0;
/* 353 */       while (clazz.isArray()) {
/* 354 */         b1++;
/* 355 */         clazz = clazz.getComponentType();
/*     */       } 
/* 357 */       StringBuffer stringBuffer = new StringBuffer();
/* 358 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 359 */         stringBuffer.append("[");
/*     */       }
/* 361 */       stringBuffer.append(getSignature(clazz));
/* 362 */       str = stringBuffer.toString();
/* 363 */     } else if (paramClass.isPrimitive()) {
/* 364 */       if (paramClass == int.class) {
/* 365 */         str = "I";
/* 366 */       } else if (paramClass == byte.class) {
/* 367 */         str = "B";
/* 368 */       } else if (paramClass == long.class) {
/* 369 */         str = "J";
/* 370 */       } else if (paramClass == float.class) {
/* 371 */         str = "F";
/* 372 */       } else if (paramClass == double.class) {
/* 373 */         str = "D";
/* 374 */       } else if (paramClass == short.class) {
/* 375 */         str = "S";
/* 376 */       } else if (paramClass == char.class) {
/* 377 */         str = "C";
/* 378 */       } else if (paramClass == boolean.class) {
/* 379 */         str = "Z";
/* 380 */       } else if (paramClass == void.class) {
/* 381 */         str = "V";
/*     */       } 
/*     */     } else {
/* 384 */       str = "L" + paramClass.getName().replace('.', '/') + ";";
/*     */     } 
/* 386 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getSignature(Method paramMethod) {
/* 393 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 395 */     stringBuffer.append("(");
/*     */     
/* 397 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 398 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 399 */       stringBuffer.append(getSignature(arrayOfClass[b]));
/*     */     }
/* 401 */     stringBuffer.append(")");
/* 402 */     stringBuffer.append(getSignature(paramMethod.getReturnType()));
/* 403 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getSignature(Constructor paramConstructor) {
/* 410 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 412 */     stringBuffer.append("(");
/*     */     
/* 414 */     Class[] arrayOfClass = paramConstructor.getParameterTypes();
/* 415 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 416 */       stringBuffer.append(getSignature(arrayOfClass[b]));
/*     */     }
/* 418 */     stringBuffer.append(")V");
/* 419 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private static Field[] getDeclaredFields(final Class clz) {
/* 423 */     return AccessController.<Field[]>doPrivileged(new PrivilegedAction<Field>() {
/*     */           public Object run() {
/* 425 */             return clz.getDeclaredFields();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MethodSignature
/*     */     implements Comparator
/*     */   {
/*     */     Member member;
/*     */     
/*     */     String signature;
/*     */     
/*     */     static MethodSignature[] removePrivateAndSort(Member[] param1ArrayOfMember) {
/* 439 */       byte b1 = 0;
/* 440 */       for (byte b2 = 0; b2 < param1ArrayOfMember.length; b2++) {
/* 441 */         if (!Modifier.isPrivate(param1ArrayOfMember[b2].getModifiers())) {
/* 442 */           b1++;
/*     */         }
/*     */       } 
/* 445 */       MethodSignature[] arrayOfMethodSignature = new MethodSignature[b1];
/* 446 */       byte b3 = 0;
/* 447 */       for (byte b4 = 0; b4 < param1ArrayOfMember.length; b4++) {
/* 448 */         if (!Modifier.isPrivate(param1ArrayOfMember[b4].getModifiers())) {
/* 449 */           arrayOfMethodSignature[b3] = new MethodSignature(param1ArrayOfMember[b4]);
/* 450 */           b3++;
/*     */         } 
/*     */       } 
/* 453 */       if (b3 > 0)
/* 454 */         Arrays.sort(arrayOfMethodSignature, arrayOfMethodSignature[0]); 
/* 455 */       return arrayOfMethodSignature;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Object param1Object1, Object param1Object2) {
/*     */       int i;
/* 462 */       if (param1Object1 == param1Object2) {
/* 463 */         return 0;
/*     */       }
/* 465 */       MethodSignature methodSignature1 = (MethodSignature)param1Object1;
/* 466 */       MethodSignature methodSignature2 = (MethodSignature)param1Object2;
/*     */ 
/*     */       
/* 469 */       if (isConstructor()) {
/* 470 */         i = methodSignature1.signature.compareTo(methodSignature2.signature);
/*     */       } else {
/* 472 */         i = methodSignature1.member.getName().compareTo(methodSignature2.member.getName());
/* 473 */         if (i == 0)
/* 474 */           i = methodSignature1.signature.compareTo(methodSignature2.signature); 
/*     */       } 
/* 476 */       return i;
/*     */     }
/*     */     
/*     */     private final boolean isConstructor() {
/* 480 */       return this.member instanceof Constructor;
/*     */     }
/*     */     private MethodSignature(Member param1Member) {
/* 483 */       this.member = param1Member;
/* 484 */       if (isConstructor()) {
/* 485 */         this.signature = ObjectStreamClassUtil_1_3.getSignature((Constructor)param1Member);
/*     */       } else {
/* 487 */         this.signature = ObjectStreamClassUtil_1_3.getSignature((Method)param1Member);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 496 */   private static Method hasStaticInitializerMethod = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasStaticInitializer(Class paramClass) {
/* 502 */     if (hasStaticInitializerMethod == null) {
/* 503 */       Class<ObjectStreamClass> clazz = null;
/*     */       
/*     */       try {
/* 506 */         if (clazz == null) {
/* 507 */           clazz = ObjectStreamClass.class;
/*     */         }
/*     */         
/* 510 */         hasStaticInitializerMethod = clazz.getDeclaredMethod("hasStaticInitializer", new Class[] { Class.class });
/*     */       }
/* 512 */       catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */       
/* 515 */       if (hasStaticInitializerMethod == null) {
/* 516 */         throw new InternalError("Can't find hasStaticInitializer method on " + clazz
/* 517 */             .getName());
/*     */       }
/* 519 */       hasStaticInitializerMethod.setAccessible(true);
/*     */     } 
/*     */     
/*     */     try {
/* 523 */       Boolean bool = (Boolean)hasStaticInitializerMethod.invoke(null, new Object[] { paramClass });
/* 524 */       return bool.booleanValue();
/* 525 */     } catch (Exception exception) {
/* 526 */       throw new InternalError("Error invoking hasStaticInitializer: " + exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method getDeclaredMethod(final Class cl, final String methodName, final Class[] args, final int requiredModifierMask, final int disallowedModifierMask) {
/* 534 */     return AccessController.<Method>doPrivileged(new PrivilegedAction<Method>() {
/*     */           public Object run() {
/* 536 */             Method method = null;
/*     */             
/*     */             try {
/* 539 */               method = cl.getDeclaredMethod(methodName, args);
/* 540 */               int i = method.getModifiers();
/* 541 */               if ((i & disallowedModifierMask) != 0 || (i & requiredModifierMask) != requiredModifierMask)
/*     */               {
/* 543 */                 method = null;
/*     */ 
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 549 */             catch (NoSuchMethodException noSuchMethodException) {}
/*     */ 
/*     */ 
/*     */             
/* 553 */             return method;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ObjectStreamClassUtil_1_3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */