/*     */ package sun.reflect;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OptionalDataException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Objects;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionFactory
/*     */ {
/*     */   private static boolean initted = false;
/*  65 */   private static final Permission reflectionFactoryAccessPerm = new RuntimePermission("reflectionFactoryAccess");
/*     */   
/*  67 */   private static final ReflectionFactory soleInstance = new ReflectionFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile LangReflectAccess langReflectAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile Method hasStaticInitializerMethod;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean noInflation = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static int inflationThreshold = 15;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class GetReflectionFactoryAction
/*     */     implements PrivilegedAction<ReflectionFactory>
/*     */   {
/*     */     public ReflectionFactory run() {
/* 105 */       return ReflectionFactory.getReflectionFactory();
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
/*     */   public static ReflectionFactory getReflectionFactory() {
/* 128 */     SecurityManager securityManager = System.getSecurityManager();
/* 129 */     if (securityManager != null)
/*     */     {
/* 131 */       securityManager.checkPermission(reflectionFactoryAccessPerm);
/*     */     }
/* 133 */     return soleInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLangReflectAccess(LangReflectAccess paramLangReflectAccess) {
/* 144 */     langReflectAccess = paramLangReflectAccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldAccessor newFieldAccessor(Field paramField, boolean paramBoolean) {
/* 155 */     checkInitted();
/* 156 */     return UnsafeFieldAccessorFactory.newFieldAccessor(paramField, paramBoolean);
/*     */   }
/*     */   
/*     */   public MethodAccessor newMethodAccessor(Method paramMethod) {
/* 160 */     checkInitted();
/*     */     
/* 162 */     if (noInflation && !ReflectUtil.isVMAnonymousClass(paramMethod.getDeclaringClass())) {
/* 163 */       return (new MethodAccessorGenerator())
/* 164 */         .generateMethod(paramMethod.getDeclaringClass(), paramMethod
/* 165 */           .getName(), paramMethod
/* 166 */           .getParameterTypes(), paramMethod
/* 167 */           .getReturnType(), paramMethod
/* 168 */           .getExceptionTypes(), paramMethod
/* 169 */           .getModifiers());
/*     */     }
/* 171 */     NativeMethodAccessorImpl nativeMethodAccessorImpl = new NativeMethodAccessorImpl(paramMethod);
/*     */     
/* 173 */     DelegatingMethodAccessorImpl delegatingMethodAccessorImpl = new DelegatingMethodAccessorImpl(nativeMethodAccessorImpl);
/*     */     
/* 175 */     nativeMethodAccessorImpl.setParent(delegatingMethodAccessorImpl);
/* 176 */     return delegatingMethodAccessorImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public ConstructorAccessor newConstructorAccessor(Constructor<?> paramConstructor) {
/* 181 */     checkInitted();
/*     */     
/* 183 */     Class<?> clazz = paramConstructor.getDeclaringClass();
/* 184 */     if (Modifier.isAbstract(clazz.getModifiers())) {
/* 185 */       return new InstantiationExceptionConstructorAccessorImpl(null);
/*     */     }
/* 187 */     if (clazz == Class.class) {
/* 188 */       return new InstantiationExceptionConstructorAccessorImpl("Can not instantiate java.lang.Class");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (Reflection.isSubclassOf(clazz, ConstructorAccessorImpl.class))
/*     */     {
/* 196 */       return new BootstrapConstructorAccessorImpl(paramConstructor);
/*     */     }
/*     */     
/* 199 */     if (noInflation && !ReflectUtil.isVMAnonymousClass(paramConstructor.getDeclaringClass())) {
/* 200 */       return (new MethodAccessorGenerator())
/* 201 */         .generateConstructor(paramConstructor.getDeclaringClass(), paramConstructor
/* 202 */           .getParameterTypes(), paramConstructor
/* 203 */           .getExceptionTypes(), paramConstructor
/* 204 */           .getModifiers());
/*     */     }
/* 206 */     NativeConstructorAccessorImpl nativeConstructorAccessorImpl = new NativeConstructorAccessorImpl(paramConstructor);
/*     */     
/* 208 */     DelegatingConstructorAccessorImpl delegatingConstructorAccessorImpl = new DelegatingConstructorAccessorImpl(nativeConstructorAccessorImpl);
/*     */     
/* 210 */     nativeConstructorAccessorImpl.setParent(delegatingConstructorAccessorImpl);
/* 211 */     return delegatingConstructorAccessorImpl;
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
/*     */   public Field newField(Class<?> paramClass1, String paramString1, Class<?> paramClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte) {
/* 231 */     return langReflectAccess().newField(paramClass1, paramString1, paramClass2, paramInt1, paramInt2, paramString2, paramArrayOfbyte);
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
/*     */   public Method newMethod(Class<?> paramClass1, String paramString1, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/* 254 */     return langReflectAccess().newMethod(paramClass1, paramString1, paramArrayOfClass1, paramClass2, paramArrayOfClass2, paramInt1, paramInt2, paramString2, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3);
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
/*     */   public Constructor<?> newConstructor(Class<?> paramClass, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 278 */     return langReflectAccess().newConstructor(paramClass, paramArrayOfClass1, paramArrayOfClass2, paramInt1, paramInt2, paramString, paramArrayOfbyte1, paramArrayOfbyte2);
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
/*     */   public MethodAccessor getMethodAccessor(Method paramMethod) {
/* 290 */     return langReflectAccess().getMethodAccessor(paramMethod);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMethodAccessor(Method paramMethod, MethodAccessor paramMethodAccessor) {
/* 295 */     langReflectAccess().setMethodAccessor(paramMethod, paramMethodAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConstructorAccessor getConstructorAccessor(Constructor<?> paramConstructor) {
/* 301 */     return langReflectAccess().getConstructorAccessor(paramConstructor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConstructorAccessor(Constructor<?> paramConstructor, ConstructorAccessor paramConstructorAccessor) {
/* 309 */     langReflectAccess().setConstructorAccessor(paramConstructor, paramConstructorAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method copyMethod(Method paramMethod) {
/* 316 */     return langReflectAccess().copyMethod(paramMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Field copyField(Field paramField) {
/* 323 */     return langReflectAccess().copyField(paramField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Constructor<T> copyConstructor(Constructor<T> paramConstructor) {
/* 330 */     return langReflectAccess().copyConstructor(paramConstructor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExecutableTypeAnnotationBytes(Executable paramExecutable) {
/* 336 */     return langReflectAccess().getExecutableTypeAnnotationBytes(paramExecutable);
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
/*     */   public Constructor<?> newConstructorForSerialization(Class<?> paramClass, Constructor<?> paramConstructor) {
/* 357 */     if (paramConstructor.getDeclaringClass() == paramClass) {
/* 358 */       return paramConstructor;
/*     */     }
/* 360 */     return generateConstructor(paramClass, paramConstructor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Constructor<?> newConstructorForSerialization(Class<?> paramClass) {
/*     */     Constructor<?> constructor;
/* 372 */     Class<?> clazz = paramClass;
/* 373 */     while (Serializable.class.isAssignableFrom(clazz)) {
/* 374 */       if ((clazz = clazz.getSuperclass()) == null) {
/* 375 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 380 */       constructor = clazz.getDeclaredConstructor(new Class[0]);
/* 381 */       int i = constructor.getModifiers();
/* 382 */       if ((i & 0x2) != 0 || ((i & 0x5) == 0 && 
/*     */         
/* 384 */         !packageEquals(paramClass, clazz))) {
/* 385 */         return null;
/*     */       }
/* 387 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 388 */       return null;
/*     */     } 
/* 390 */     return generateConstructor(paramClass, constructor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Constructor<?> generateConstructor(Class<?> paramClass, Constructor<?> paramConstructor) {
/* 398 */     SerializationConstructorAccessorImpl serializationConstructorAccessorImpl = (new MethodAccessorGenerator()).generateSerializationConstructor(paramClass, paramConstructor
/* 399 */         .getParameterTypes(), paramConstructor
/* 400 */         .getExceptionTypes(), paramConstructor
/* 401 */         .getModifiers(), paramConstructor
/* 402 */         .getDeclaringClass());
/* 403 */     Constructor<?> constructor = newConstructor(paramConstructor.getDeclaringClass(), paramConstructor
/* 404 */         .getParameterTypes(), paramConstructor
/* 405 */         .getExceptionTypes(), paramConstructor
/* 406 */         .getModifiers(), 
/* 407 */         langReflectAccess()
/* 408 */         .getConstructorSlot(paramConstructor), 
/* 409 */         langReflectAccess()
/* 410 */         .getConstructorSignature(paramConstructor), 
/* 411 */         langReflectAccess()
/* 412 */         .getConstructorAnnotations(paramConstructor), 
/* 413 */         langReflectAccess()
/* 414 */         .getConstructorParameterAnnotations(paramConstructor));
/* 415 */     setConstructorAccessor(constructor, serializationConstructorAccessorImpl);
/* 416 */     constructor.setAccessible(true);
/* 417 */     return constructor;
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
/*     */   public final Constructor<?> newConstructorForExternalization(Class<?> paramClass) {
/* 429 */     if (!Externalizable.class.isAssignableFrom(paramClass)) {
/* 430 */       return null;
/*     */     }
/*     */     try {
/* 433 */       Constructor<?> constructor = paramClass.getConstructor(new Class[0]);
/* 434 */       constructor.setAccessible(true);
/* 435 */       return constructor;
/* 436 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 437 */       return null;
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
/*     */   public final MethodHandle readObjectForSerialization(Class<?> paramClass) {
/* 453 */     return findReadWriteObjectForSerialization(paramClass, "readObject", ObjectInputStream.class);
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
/*     */   public final MethodHandle readObjectNoDataForSerialization(Class<?> paramClass) {
/* 469 */     return findReadWriteObjectForSerialization(paramClass, "readObjectNoData", ObjectInputStream.class);
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
/*     */   public final MethodHandle writeObjectForSerialization(Class<?> paramClass) {
/* 484 */     return findReadWriteObjectForSerialization(paramClass, "writeObject", ObjectOutputStream.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final MethodHandle findReadWriteObjectForSerialization(Class<?> paramClass1, String paramString, Class<?> paramClass2) {
/* 490 */     if (!Serializable.class.isAssignableFrom(paramClass1)) {
/* 491 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 495 */       Method method = paramClass1.getDeclaredMethod(paramString, new Class[] { paramClass2 });
/* 496 */       int i = method.getModifiers();
/* 497 */       if (method.getReturnType() != void.class || 
/* 498 */         Modifier.isStatic(i) || 
/* 499 */         !Modifier.isPrivate(i)) {
/* 500 */         return null;
/*     */       }
/* 502 */       method.setAccessible(true);
/* 503 */       return MethodHandles.lookup().unreflect(method);
/* 504 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 505 */       return null;
/* 506 */     } catch (IllegalAccessException illegalAccessException) {
/* 507 */       throw new InternalError("Error", illegalAccessException);
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
/*     */   public final MethodHandle readResolveForSerialization(Class<?> paramClass) {
/* 522 */     return getReplaceResolveForSerialization(paramClass, "readResolve");
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
/*     */   public final MethodHandle writeReplaceForSerialization(Class<?> paramClass) {
/* 536 */     return getReplaceResolveForSerialization(paramClass, "writeReplace");
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
/*     */   private MethodHandle getReplaceResolveForSerialization(Class<?> paramClass, String paramString) {
/* 551 */     if (!Serializable.class.isAssignableFrom(paramClass)) {
/* 552 */       return null;
/*     */     }
/*     */     
/* 555 */     Class<?> clazz = paramClass;
/* 556 */     while (clazz != null) {
/*     */       try {
/* 558 */         Method method = clazz.getDeclaredMethod(paramString, new Class[0]);
/* 559 */         if (method.getReturnType() != Object.class) {
/* 560 */           return null;
/*     */         }
/* 562 */         int i = method.getModifiers();
/* 563 */         if ((Modifier.isStatic(i) | Modifier.isAbstract(i)) != 0)
/* 564 */           return null; 
/* 565 */         if ((Modifier.isPublic(i) | Modifier.isProtected(i)) == 0) {
/*     */           
/* 567 */           if (Modifier.isPrivate(i) && paramClass != clazz)
/* 568 */             return null; 
/* 569 */           if (!packageEquals(paramClass, clazz)) {
/* 570 */             return null;
/*     */           }
/*     */         } 
/*     */         try {
/* 574 */           method.setAccessible(true);
/* 575 */           return MethodHandles.lookup().unreflect(method);
/* 576 */         } catch (IllegalAccessException illegalAccessException) {
/*     */           
/* 578 */           throw new InternalError("Error", illegalAccessException);
/*     */         } 
/* 580 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 581 */         clazz = clazz.getSuperclass();
/*     */       } 
/*     */     } 
/* 584 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasStaticInitializerForSerialization(Class<?> paramClass) {
/* 595 */     Method method = hasStaticInitializerMethod;
/* 596 */     if (method == null) {
/*     */       try {
/* 598 */         method = ObjectStreamClass.class.getDeclaredMethod("hasStaticInitializer", new Class[] { Class.class });
/*     */         
/* 600 */         method.setAccessible(true);
/* 601 */         hasStaticInitializerMethod = method;
/* 602 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 603 */         throw new InternalError("No such method hasStaticInitializer on " + ObjectStreamClass.class, noSuchMethodException);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 608 */       return ((Boolean)method.invoke(null, new Object[] { paramClass })).booleanValue();
/* 609 */     } catch (InvocationTargetException|IllegalAccessException invocationTargetException) {
/* 610 */       throw new InternalError("Exception invoking hasStaticInitializer", invocationTargetException);
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
/*     */   public final OptionalDataException newOptionalDataExceptionForSerialization(boolean paramBoolean) {
/*     */     try {
/* 623 */       Constructor<OptionalDataException> constructor = OptionalDataException.class.getDeclaredConstructor(new Class[] { boolean.class });
/* 624 */       constructor.setAccessible(true);
/* 625 */       return constructor.newInstance(new Object[] { Boolean.valueOf(paramBoolean) });
/* 626 */     } catch (NoSuchMethodException|InstantiationException|IllegalAccessException|InvocationTargetException noSuchMethodException) {
/*     */       
/* 628 */       throw new InternalError("unable to create OptionalDataException", noSuchMethodException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int inflationThreshold() {
/* 638 */     return inflationThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkInitted() {
/* 647 */     if (initted)
/* 648 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Void run()
/*     */           {
/* 660 */             if (System.out == null)
/*     */             {
/* 662 */               return null;
/*     */             }
/*     */             
/* 665 */             String str = System.getProperty("sun.reflect.noInflation");
/* 666 */             if (str != null && str.equals("true")) {
/* 667 */               ReflectionFactory.noInflation = true;
/*     */             }
/*     */             
/* 670 */             str = System.getProperty("sun.reflect.inflationThreshold");
/* 671 */             if (str != null) {
/*     */               try {
/* 673 */                 ReflectionFactory.inflationThreshold = Integer.parseInt(str);
/* 674 */               } catch (NumberFormatException numberFormatException) {
/* 675 */                 throw new RuntimeException("Unable to parse property sun.reflect.inflationThreshold", numberFormatException);
/*     */               } 
/*     */             }
/*     */             
/* 679 */             ReflectionFactory.initted = true;
/* 680 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static LangReflectAccess langReflectAccess() {
/* 686 */     if (langReflectAccess == null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 691 */       Modifier.isPublic(1);
/*     */     }
/* 693 */     return langReflectAccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean packageEquals(Class<?> paramClass1, Class<?> paramClass2) {
/* 704 */     return (paramClass1.getClassLoader() == paramClass2.getClassLoader() && 
/* 705 */       Objects.equals(paramClass1.getPackage(), paramClass2.getPackage()));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/ReflectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */