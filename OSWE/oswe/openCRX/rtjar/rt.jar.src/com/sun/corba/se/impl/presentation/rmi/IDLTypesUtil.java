/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.HashSet;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.IDLEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IDLTypesUtil
/*     */ {
/*     */   private static final String GET_PROPERTY_PREFIX = "get";
/*     */   private static final String SET_PROPERTY_PREFIX = "set";
/*     */   private static final String IS_PROPERTY_PREFIX = "is";
/*     */   public static final int VALID_TYPE = 0;
/*     */   public static final int INVALID_TYPE = 1;
/*     */   public static final boolean FOLLOW_RMIC = true;
/*     */   
/*     */   public void validateRemoteInterface(Class<?> paramClass) throws IDLTypeException {
/*  66 */     if (paramClass == null) {
/*  67 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  70 */     if (!paramClass.isInterface()) {
/*  71 */       String str = "Class " + paramClass + " must be a java interface.";
/*  72 */       throw new IDLTypeException(str);
/*     */     } 
/*     */     
/*  75 */     if (!Remote.class.isAssignableFrom(paramClass)) {
/*  76 */       String str = "Class " + paramClass + " must extend java.rmi.Remote, either directly or indirectly.";
/*     */       
/*  78 */       throw new IDLTypeException(str);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     Method[] arrayOfMethod = paramClass.getMethods();
/*     */     
/*  84 */     for (byte b = 0; b < arrayOfMethod.length; b++) {
/*  85 */       Method method = arrayOfMethod[b];
/*  86 */       validateExceptions(method);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  91 */     validateConstants(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRemoteInterface(Class paramClass) {
/*  98 */     boolean bool = true;
/*     */     try {
/* 100 */       validateRemoteInterface(paramClass);
/* 101 */     } catch (IDLTypeException iDLTypeException) {
/* 102 */       bool = false;
/*     */     } 
/*     */     
/* 105 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPrimitive(Class paramClass) {
/* 113 */     if (paramClass == null) {
/* 114 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 117 */     return paramClass.isPrimitive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValue(Class<?> paramClass) {
/* 125 */     if (paramClass == null) {
/* 126 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 129 */     return (
/* 130 */       !paramClass.isInterface() && Serializable.class
/* 131 */       .isAssignableFrom(paramClass) && 
/* 132 */       !Remote.class.isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray(Class paramClass) {
/* 140 */     boolean bool = false;
/*     */     
/* 142 */     if (paramClass == null) {
/* 143 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 146 */     if (paramClass.isArray()) {
/* 147 */       Class<?> clazz = paramClass.getComponentType();
/*     */ 
/*     */ 
/*     */       
/* 151 */       bool = (isPrimitive(clazz) || isRemoteInterface(clazz) || isEntity(clazz) || isException(clazz) || isValue(clazz) || isObjectReference(clazz)) ? true : false;
/*     */     } 
/*     */     
/* 154 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isException(Class paramClass) {
/* 162 */     if (paramClass == null) {
/* 163 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 168 */     return (isCheckedException(paramClass) && !isRemoteException(paramClass) && isValue(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRemoteException(Class<?> paramClass) {
/* 173 */     if (paramClass == null) {
/* 174 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 177 */     return RemoteException.class.isAssignableFrom(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCheckedException(Class<?> paramClass) {
/* 182 */     if (paramClass == null) {
/* 183 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 186 */     return (Throwable.class.isAssignableFrom(paramClass) && 
/* 187 */       !RuntimeException.class.isAssignableFrom(paramClass) && 
/* 188 */       !Error.class.isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isObjectReference(Class<?> paramClass) {
/* 196 */     if (paramClass == null) {
/* 197 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 200 */     return (paramClass.isInterface() && Object.class
/* 201 */       .isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEntity(Class<?> paramClass) {
/* 209 */     if (paramClass == null) {
/* 210 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 213 */     Class clazz = paramClass.getSuperclass();
/* 214 */     return (!paramClass.isInterface() && clazz != null && IDLEntity.class
/*     */       
/* 216 */       .isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPropertyAccessorMethod(Method paramMethod, Class paramClass) {
/* 225 */     String str1 = paramMethod.getName();
/* 226 */     Class<?> clazz = paramMethod.getReturnType();
/* 227 */     Class[] arrayOfClass1 = paramMethod.getParameterTypes();
/* 228 */     Class[] arrayOfClass2 = paramMethod.getExceptionTypes();
/* 229 */     String str2 = null;
/*     */     
/* 231 */     if (str1.startsWith("get")) {
/*     */       
/* 233 */       if (arrayOfClass1.length == 0 && clazz != void.class && 
/* 234 */         !readHasCorrespondingIsProperty(paramMethod, paramClass)) {
/* 235 */         str2 = "get";
/*     */       }
/*     */     }
/* 238 */     else if (str1.startsWith("set")) {
/*     */       
/* 240 */       if (clazz == void.class && arrayOfClass1.length == 1 && (
/* 241 */         hasCorrespondingReadProperty(paramMethod, paramClass, "get") || 
/* 242 */         hasCorrespondingReadProperty(paramMethod, paramClass, "is"))) {
/* 243 */         str2 = "set";
/*     */       
/*     */       }
/*     */     }
/* 247 */     else if (str1.startsWith("is") && 
/* 248 */       arrayOfClass1.length == 0 && clazz == boolean.class && 
/* 249 */       !isHasCorrespondingReadProperty(paramMethod, paramClass)) {
/* 250 */       str2 = "is";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 255 */     if (str2 != null && (
/* 256 */       !validPropertyExceptions(paramMethod) || str1
/* 257 */       .length() <= str2.length())) {
/* 258 */       str2 = null;
/*     */     }
/*     */ 
/*     */     
/* 262 */     return (str2 != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasCorrespondingReadProperty(Method paramMethod, Class paramClass, String paramString) {
/* 268 */     String str = paramMethod.getName();
/* 269 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 270 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 276 */       String str1 = str.replaceFirst("set", paramString);
/* 277 */       Method method = paramClass.getMethod(str1, new Class[0]);
/*     */ 
/*     */ 
/*     */       
/* 281 */       bool = (isPropertyAccessorMethod(method, paramClass) && method.getReturnType() == arrayOfClass[0]) ? true : false;
/*     */     }
/* 283 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 287 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean readHasCorrespondingIsProperty(Method paramMethod, Class paramClass) {
/* 294 */     return false;
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
/*     */   private boolean isHasCorrespondingReadProperty(Method paramMethod, Class paramClass) {
/* 321 */     String str = paramMethod.getName();
/* 322 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 327 */       String str1 = str.replaceFirst("is", "get");
/*     */       
/* 329 */       Method method = paramClass.getMethod(str1, new Class[0]);
/*     */       
/* 331 */       bool = isPropertyAccessorMethod(method, paramClass);
/*     */     }
/* 333 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 337 */     return bool;
/*     */   }
/*     */   
/*     */   public String getAttributeNameForProperty(String paramString) {
/* 341 */     String str1 = null;
/* 342 */     String str2 = null;
/*     */     
/* 344 */     if (paramString.startsWith("get")) {
/* 345 */       str2 = "get";
/* 346 */     } else if (paramString.startsWith("set")) {
/* 347 */       str2 = "set";
/* 348 */     } else if (paramString.startsWith("is")) {
/* 349 */       str2 = "is";
/*     */     } 
/*     */     
/* 352 */     if (str2 != null && str2.length() < paramString.length()) {
/* 353 */       String str = paramString.substring(str2.length());
/* 354 */       if (str.length() >= 2 && 
/* 355 */         Character.isUpperCase(str.charAt(0)) && 
/* 356 */         Character.isUpperCase(str.charAt(1))) {
/*     */ 
/*     */         
/* 359 */         str1 = str;
/*     */       } else {
/*     */         
/* 362 */         str1 = Character.toLowerCase(str.charAt(0)) + str.substring(1);
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     return str1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDLType getPrimitiveIDLTypeMapping(Class<void> paramClass) {
/* 375 */     if (paramClass == null) {
/* 376 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 379 */     if (paramClass.isPrimitive()) {
/* 380 */       if (paramClass == void.class)
/* 381 */         return new IDLType(paramClass, "void"); 
/* 382 */       if (paramClass == boolean.class)
/* 383 */         return new IDLType(paramClass, "boolean"); 
/* 384 */       if (paramClass == char.class)
/* 385 */         return new IDLType(paramClass, "wchar"); 
/* 386 */       if (paramClass == byte.class)
/* 387 */         return new IDLType(paramClass, "octet"); 
/* 388 */       if (paramClass == short.class)
/* 389 */         return new IDLType(paramClass, "short"); 
/* 390 */       if (paramClass == int.class)
/* 391 */         return new IDLType(paramClass, "long"); 
/* 392 */       if (paramClass == long.class)
/* 393 */         return new IDLType(paramClass, "long_long"); 
/* 394 */       if (paramClass == float.class)
/* 395 */         return new IDLType(paramClass, "float"); 
/* 396 */       if (paramClass == double.class) {
/* 397 */         return new IDLType(paramClass, "double");
/*     */       }
/*     */     } 
/*     */     
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDLType getSpecialCaseIDLTypeMapping(Class<Object> paramClass) {
/* 411 */     if (paramClass == null) {
/* 412 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 415 */     if (paramClass == Object.class) {
/* 416 */       return new IDLType(paramClass, new String[] { "java", "lang" }, "Object");
/*     */     }
/* 418 */     if (paramClass == String.class) {
/* 419 */       return new IDLType(paramClass, new String[] { "CORBA" }, "WStringValue");
/*     */     }
/* 421 */     if (paramClass == Class.class) {
/* 422 */       return new IDLType(paramClass, new String[] { "javax", "rmi", "CORBA" }, "ClassDesc");
/*     */     }
/* 424 */     if (paramClass == Serializable.class) {
/* 425 */       return new IDLType(paramClass, new String[] { "java", "io" }, "Serializable");
/*     */     }
/* 427 */     if (paramClass == Externalizable.class) {
/* 428 */       return new IDLType(paramClass, new String[] { "java", "io" }, "Externalizable");
/*     */     }
/* 430 */     if (paramClass == Remote.class) {
/* 431 */       return new IDLType(paramClass, new String[] { "java", "rmi" }, "Remote");
/*     */     }
/* 433 */     if (paramClass == Object.class) {
/* 434 */       return new IDLType(paramClass, "Object");
/*     */     }
/* 436 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateExceptions(Method paramMethod) throws IDLTypeException {
/* 445 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/*     */     
/* 447 */     boolean bool = false;
/*     */     
/*     */     byte b;
/* 450 */     for (b = 0; b < arrayOfClass.length; b++) {
/* 451 */       Class clazz = arrayOfClass[b];
/* 452 */       if (isRemoteExceptionOrSuperClass(clazz)) {
/* 453 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 458 */     if (!bool) {
/* 459 */       String str = "Method '" + paramMethod + "' must throw at least one exception of type java.rmi.RemoteException or one of its super-classes";
/*     */ 
/*     */       
/* 462 */       throw new IDLTypeException(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 469 */     for (b = 0; b < arrayOfClass.length; b++) {
/* 470 */       Class clazz = arrayOfClass[b];
/*     */       
/* 472 */       if (isCheckedException(clazz) && !isValue(clazz) && 
/* 473 */         !isRemoteException(clazz)) {
/*     */         
/* 475 */         String str = "Exception '" + clazz + "' on method '" + paramMethod + "' is not a allowed RMI/IIOP exception type";
/*     */         
/* 477 */         throw new IDLTypeException(str);
/*     */       } 
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
/*     */   private boolean validPropertyExceptions(Method paramMethod) {
/* 492 */     Class[] arrayOfClass = paramMethod.getExceptionTypes();
/*     */     
/* 494 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 495 */       Class clazz = arrayOfClass[b];
/*     */       
/* 497 */       if (isCheckedException(clazz) && !isRemoteException(clazz)) {
/* 498 */         return false;
/*     */       }
/*     */     } 
/* 501 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRemoteExceptionOrSuperClass(Class<RemoteException> paramClass) {
/* 508 */     return (paramClass == RemoteException.class || paramClass == IOException.class || paramClass == Exception.class || paramClass == Throwable.class);
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
/*     */   private void validateDirectInterfaces(Class paramClass) throws IDLTypeException {
/* 520 */     Class[] arrayOfClass = paramClass.getInterfaces();
/*     */     
/* 522 */     if (arrayOfClass.length < 2) {
/*     */       return;
/*     */     }
/*     */     
/* 526 */     HashSet<String> hashSet1 = new HashSet();
/* 527 */     HashSet<String> hashSet2 = new HashSet();
/*     */     
/* 529 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 530 */       Class clazz = arrayOfClass[b];
/* 531 */       Method[] arrayOfMethod = clazz.getMethods();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 536 */       hashSet2.clear();
/* 537 */       for (byte b1 = 0; b1 < arrayOfMethod.length; b1++) {
/* 538 */         hashSet2.add(arrayOfMethod[b1].getName());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 543 */       for (String str : hashSet2) {
/*     */         
/* 545 */         if (hashSet1.contains(str)) {
/* 546 */           String str1 = "Class " + paramClass + " inherits method " + str + " from multiple direct interfaces.";
/*     */           
/* 548 */           throw new IDLTypeException(str1);
/*     */         } 
/* 550 */         hashSet1.add(str);
/*     */       } 
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
/*     */   private void validateConstants(final Class c) throws IDLTypeException {
/* 564 */     Field[] arrayOfField = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 569 */       arrayOfField = AccessController.<Field[]>doPrivileged(new PrivilegedExceptionAction<Field>() {
/*     */             public Object run() throws Exception {
/* 571 */               return c.getFields();
/*     */             }
/*     */           });
/* 574 */     } catch (PrivilegedActionException privilegedActionException) {
/* 575 */       IDLTypeException iDLTypeException = new IDLTypeException();
/* 576 */       iDLTypeException.initCause(privilegedActionException);
/* 577 */       throw iDLTypeException;
/*     */     } 
/*     */     
/* 580 */     for (byte b = 0; b < arrayOfField.length; b++) {
/* 581 */       Field field = arrayOfField[b];
/* 582 */       Class<?> clazz = field.getType();
/* 583 */       if (clazz != String.class && 
/* 584 */         !isPrimitive(clazz)) {
/*     */ 
/*     */         
/* 587 */         String str = "Constant field '" + field.getName() + "' in class '" + field.getDeclaringClass().getName() + "' has invalid type' " + field.getType() + "'. Constants in RMI/IIOP interfaces can only have primitive types and java.lang.String types.";
/*     */ 
/*     */         
/* 590 */         throw new IDLTypeException(str);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/IDLTypesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */