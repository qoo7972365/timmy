/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.Invokers;
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandleNatives;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Field;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MethodHandleNatives
/*     */ {
/*     */   static {
/*  76 */     registerNatives();
/*  77 */   } static final boolean COUNT_GWT = (getConstant(4) != 0);
/*     */   static class Constants {
/*     */     static final int GC_COUNT_GWT = 4;
/*  80 */     static final int GC_LAMBDA_SUPPORT = 5; static final int MN_IS_METHOD = 65536; static final int MN_IS_CONSTRUCTOR = 131072; static final int MN_IS_FIELD = 262144; static final int MN_IS_TYPE = 524288; static final int MN_CALLER_SENSITIVE = 1048576; static final int MN_REFERENCE_KIND_SHIFT = 24; static final int MN_REFERENCE_KIND_MASK = 15; static final int MN_SEARCH_SUPERCLASSES = 1048576; static final int MN_SEARCH_INTERFACES = 2097152; static final int T_BOOLEAN = 4; static final int T_CHAR = 5; static final int T_FLOAT = 6; static final int T_DOUBLE = 7; static final int T_BYTE = 8; static final int T_SHORT = 9; static final int T_INT = 10; static final int T_LONG = 11; static final int T_OBJECT = 12; static final int T_VOID = 14; static final int T_ILLEGAL = 99; static final byte CONSTANT_Utf8 = 1; static final byte CONSTANT_Integer = 3; static final byte CONSTANT_Float = 4; static final byte CONSTANT_Long = 5; static final byte CONSTANT_Double = 6; static final byte CONSTANT_Class = 7; static final byte CONSTANT_String = 8; static final byte CONSTANT_Fieldref = 9; static final byte CONSTANT_Methodref = 10; static final byte CONSTANT_InterfaceMethodref = 11; static final byte CONSTANT_NameAndType = 12; static final byte CONSTANT_MethodHandle = 15; static final byte CONSTANT_MethodType = 16; static final byte CONSTANT_InvokeDynamic = 18; static final byte CONSTANT_LIMIT = 19; static final char ACC_PUBLIC = '\001'; static final char ACC_PRIVATE = '\002'; static final char ACC_PROTECTED = '\004'; static final char ACC_STATIC = '\b'; static final char ACC_FINAL = '\020'; static final char ACC_SYNCHRONIZED = ' '; static final char ACC_VOLATILE = '@'; static final char ACC_TRANSIENT = ''; static final char ACC_NATIVE = 'Ā'; static final char ACC_INTERFACE = 'Ȁ'; static final char ACC_ABSTRACT = 'Ѐ'; static final char ACC_STRICT = 'ࠀ'; static final char ACC_SYNTHETIC = 'က'; static final char ACC_ANNOTATION = ' '; static final char ACC_ENUM = '䀀'; static final char ACC_SUPER = ' '; static final char ACC_BRIDGE = '@'; static final char ACC_VARARGS = ''; static final byte REF_NONE = 0; static final byte REF_getField = 1; static final byte REF_getStatic = 2; static final byte REF_putField = 3; static final byte REF_putStatic = 4; static final byte REF_invokeVirtual = 5; static final byte REF_invokeStatic = 6; static final byte REF_invokeSpecial = 7; static final byte REF_newInvokeSpecial = 8; static final byte REF_invokeInterface = 9; static final byte REF_LIMIT = 10; } static boolean refKindIsValid(int paramInt) { return (paramInt > 0 && paramInt < 10); } static boolean refKindIsField(byte paramByte) { assert refKindIsValid(paramByte); return (paramByte <= 4); } static { MethodHandleImpl.initStatics();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     for (byte b = 1; b < 10; b = (byte)(b + 1)) {
/* 231 */       assert refKindHasReceiver(b) == (((1 << b & 0x2AA) != 0)) : b;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     assert verifyConstants(); }
/*     */   
/*     */   static boolean refKindIsGetter(byte paramByte) {
/*     */     assert refKindIsValid(paramByte);
/*     */     return (paramByte <= 2);
/*     */   }
/*     */   static boolean refKindIsSetter(byte paramByte) {
/*     */     return (refKindIsField(paramByte) && !refKindIsGetter(paramByte));
/*     */   }
/*     */   static boolean refKindIsMethod(byte paramByte) {
/*     */     return (!refKindIsField(paramByte) && paramByte != 8);
/*     */   }
/*     */   
/*     */   static MemberName linkCallSite(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object[] paramArrayOfObject) {
/* 292 */     MethodHandle methodHandle = (MethodHandle)paramObject2;
/* 293 */     Class<?> clazz = (Class)paramObject1;
/* 294 */     String str = paramObject3.toString().intern();
/* 295 */     MethodType methodType = (MethodType)paramObject4;
/* 296 */     if (!MethodHandleStatics.TRACE_METHOD_LINKAGE) {
/* 297 */       return linkCallSiteImpl(clazz, methodHandle, str, methodType, paramObject5, paramArrayOfObject);
/*     */     }
/* 299 */     return linkCallSiteTracing(clazz, methodHandle, str, methodType, paramObject5, paramArrayOfObject); } static boolean refKindIsConstructor(byte paramByte) { return (paramByte == 8); }
/*     */   static boolean refKindHasReceiver(byte paramByte) { assert refKindIsValid(paramByte); return ((paramByte & 0x1) != 0); }
/*     */   static boolean refKindIsStatic(byte paramByte) { return (!refKindHasReceiver(paramByte) && paramByte != 8); }
/*     */   static boolean refKindDoesDispatch(byte paramByte) { assert refKindIsValid(paramByte); return (paramByte == 5 || paramByte == 9); }
/*     */   static String refKindName(byte paramByte) { assert refKindIsValid(paramByte); switch (paramByte) { case 1: return "getField";case 2: return "getStatic";case 3: return "putField";case 4: return "putStatic";case 5: return "invokeVirtual";case 6: return "invokeStatic";case 7: return "invokeSpecial";case 8: return "newInvokeSpecial";
/*     */       case 9: return "invokeInterface"; }  return "REF_???"; }
/*     */   static boolean verifyConstants() { Object[] arrayOfObject = { null }; for (byte b = 0;; b++) { arrayOfObject[0] = null; int i = getNamedCon(b, arrayOfObject); if (arrayOfObject[0] == null)
/*     */         break;  String str = (String)arrayOfObject[0]; try { Field field = Constants.class.getDeclaredField(str); int j = field.getInt(null); if (j != i) { String str1 = str + ": JVM has " + i + " while Java has " + j; if (str.equals("CONV_OP_LIMIT")) { System.err.println("warning: " + str1); } else { throw new InternalError(str1); }  }  } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) { String str1 = str + ": JVM has " + i + " which Java does not define"; }  }  return true; }
/* 307 */   static MemberName linkCallSiteImpl(Class<?> paramClass, MethodHandle paramMethodHandle, String paramString, MethodType paramMethodType, Object paramObject, Object[] paramArrayOfObject) { CallSite callSite = CallSite.makeSite(paramMethodHandle, paramString, paramMethodType, paramObject, paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     if (callSite instanceof ConstantCallSite) {
/* 313 */       paramArrayOfObject[0] = callSite.dynamicInvoker();
/* 314 */       return Invokers.linkToTargetMethod(paramMethodType);
/*     */     } 
/* 316 */     paramArrayOfObject[0] = callSite;
/* 317 */     return Invokers.linkToCallSiteMethod(paramMethodType); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MemberName linkCallSiteTracing(Class<?> paramClass, MethodHandle paramMethodHandle, String paramString, MethodType paramMethodType, Object paramObject, Object[] paramArrayOfObject) {
/*     */     MethodHandle methodHandle;
/* 326 */     MemberName memberName = paramMethodHandle.internalMemberName();
/* 327 */     if (memberName == null) methodHandle = paramMethodHandle;
/*     */     
/* 329 */     Object object = (paramObject instanceof Object[]) ? Arrays.<Object>asList((Object[])paramObject) : paramObject;
/*     */     
/* 331 */     System.out.println("linkCallSite " + paramClass.getName() + " " + methodHandle + " " + paramString + paramMethodType + "/" + object);
/*     */ 
/*     */     
/*     */     try {
/* 335 */       MemberName memberName1 = linkCallSiteImpl(paramClass, paramMethodHandle, paramString, paramMethodType, paramObject, paramArrayOfObject);
/*     */       
/* 337 */       System.out.println("linkCallSite => " + memberName1 + " + " + paramArrayOfObject[0]);
/* 338 */       return memberName1;
/* 339 */     } catch (Throwable throwable) {
/* 340 */       System.out.println("linkCallSite => throw " + throwable);
/* 341 */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MethodType findMethodHandleType(Class<?> paramClass, Class<?>[] paramArrayOfClass) {
/* 349 */     return MethodType.makeImpl(paramClass, paramArrayOfClass, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static MemberName linkMethod(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject) {
/* 422 */     if (!MethodHandleStatics.TRACE_METHOD_LINKAGE)
/* 423 */       return linkMethodImpl(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject); 
/* 424 */     return linkMethodTracing(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   static MemberName linkMethodImpl(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject) {
/*     */     try {
/* 430 */       if (paramClass2 == MethodHandle.class && paramInt == 5) {
/* 431 */         return Invokers.methodHandleInvokeLinkerMethod(paramString, fixMethodType(paramClass1, paramObject), paramArrayOfObject);
/*     */       }
/* 433 */     } catch (Throwable throwable) {
/* 434 */       if (throwable instanceof LinkageError) {
/* 435 */         throw (LinkageError)throwable;
/*     */       }
/* 437 */       throw new LinkageError(throwable.getMessage(), throwable);
/*     */     } 
/* 439 */     throw new LinkageError("no such method " + paramClass2.getName() + "." + paramString + paramObject);
/*     */   }
/*     */   private static MethodType fixMethodType(Class<?> paramClass, Object paramObject) {
/* 442 */     if (paramObject instanceof MethodType) {
/* 443 */       return (MethodType)paramObject;
/*     */     }
/* 445 */     return MethodType.fromMethodDescriptorString((String)paramObject, paramClass.getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static MemberName linkMethodTracing(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject, Object[] paramArrayOfObject) {
/* 451 */     System.out.println("linkMethod " + paramClass2.getName() + "." + paramString + paramObject + "/" + 
/* 452 */         Integer.toHexString(paramInt));
/*     */     try {
/* 454 */       MemberName memberName = linkMethodImpl(paramClass1, paramInt, paramClass2, paramString, paramObject, paramArrayOfObject);
/* 455 */       System.out.println("linkMethod => " + memberName + " + " + paramArrayOfObject[0]);
/* 456 */       return memberName;
/* 457 */     } catch (Throwable throwable) {
/* 458 */       System.out.println("linkMethod => throw " + throwable);
/* 459 */       throw throwable;
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
/*     */   static MethodHandle linkMethodHandleConstant(Class<?> paramClass1, int paramInt, Class<?> paramClass2, String paramString, Object paramObject) {
/*     */     try {
/* 475 */       MethodHandles.Lookup lookup = MethodHandles.Lookup.IMPL_LOOKUP.in(paramClass1);
/* 476 */       assert refKindIsValid(paramInt);
/* 477 */       return lookup.linkMethodHandleConstant((byte)paramInt, paramClass2, paramString, paramObject);
/* 478 */     } catch (IllegalAccessException illegalAccessException) {
/* 479 */       Throwable throwable = illegalAccessException.getCause();
/* 480 */       if (throwable instanceof AbstractMethodError) {
/* 481 */         throw (AbstractMethodError)throwable;
/*     */       }
/* 483 */       IllegalAccessError illegalAccessError = new IllegalAccessError(illegalAccessException.getMessage());
/* 484 */       throw initCauseFrom(illegalAccessError, illegalAccessException);
/*     */     }
/* 486 */     catch (NoSuchMethodException noSuchMethodException) {
/* 487 */       NoSuchMethodError noSuchMethodError = new NoSuchMethodError(noSuchMethodException.getMessage());
/* 488 */       throw initCauseFrom(noSuchMethodError, noSuchMethodException);
/* 489 */     } catch (NoSuchFieldException noSuchFieldException) {
/* 490 */       NoSuchFieldError noSuchFieldError = new NoSuchFieldError(noSuchFieldException.getMessage());
/* 491 */       throw initCauseFrom(noSuchFieldError, noSuchFieldException);
/* 492 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 493 */       IncompatibleClassChangeError incompatibleClassChangeError = new IncompatibleClassChangeError();
/* 494 */       throw initCauseFrom(incompatibleClassChangeError, reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Error initCauseFrom(Error paramError, Exception paramException) {
/* 503 */     Throwable throwable = paramException.getCause();
/* 504 */     if (paramError.getClass().isInstance(throwable))
/* 505 */       return (Error)throwable; 
/* 506 */     paramError.initCause((throwable == null) ? paramException : throwable);
/* 507 */     return paramError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isCallerSensitive(MemberName paramMemberName) {
/* 516 */     if (!paramMemberName.isInvocable()) return false;
/*     */     
/* 518 */     return (paramMemberName.isCallerSensitive() || canBeCalledVirtual(paramMemberName));
/*     */   }
/*     */   
/*     */   static boolean canBeCalledVirtual(MemberName paramMemberName) {
/* 522 */     assert paramMemberName.isInvocable();
/* 523 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/* 524 */     switch (paramMemberName.getName()) {
/*     */       case "checkMemberAccess":
/* 526 */         return canBeCalledVirtual(paramMemberName, SecurityManager.class);
/*     */       case "getContextClassLoader":
/* 528 */         return canBeCalledVirtual(paramMemberName, Thread.class);
/*     */     } 
/* 530 */     return false;
/*     */   }
/*     */   
/*     */   static boolean canBeCalledVirtual(MemberName paramMemberName, Class<?> paramClass) {
/* 534 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/* 535 */     if (clazz == paramClass) return true; 
/* 536 */     if (paramMemberName.isStatic() || paramMemberName.isPrivate()) return false; 
/* 537 */     return (paramClass.isAssignableFrom(clazz) || clazz
/* 538 */       .isInterface());
/*     */   }
/*     */   
/*     */   static native void init(MemberName paramMemberName, Object paramObject);
/*     */   
/*     */   static native void expand(MemberName paramMemberName);
/*     */   
/*     */   static native MemberName resolve(MemberName paramMemberName, Class<?> paramClass) throws LinkageError, ClassNotFoundException;
/*     */   
/*     */   static native int getMembers(Class<?> paramClass1, String paramString1, String paramString2, int paramInt1, Class<?> paramClass2, int paramInt2, MemberName[] paramArrayOfMemberName);
/*     */   
/*     */   static native long objectFieldOffset(MemberName paramMemberName);
/*     */   
/*     */   static native long staticFieldOffset(MemberName paramMemberName);
/*     */   
/*     */   static native Object staticFieldBase(MemberName paramMemberName);
/*     */   
/*     */   static native Object getMemberVMInfo(MemberName paramMemberName);
/*     */   
/*     */   static native int getConstant(int paramInt);
/*     */   
/*     */   static native void setCallSiteTargetNormal(CallSite paramCallSite, MethodHandle paramMethodHandle);
/*     */   
/*     */   static native void setCallSiteTargetVolatile(CallSite paramCallSite, MethodHandle paramMethodHandle);
/*     */   
/*     */   private static native void registerNatives();
/*     */   
/*     */   private static native int getNamedCon(int paramInt, Object[] paramArrayOfObject);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodHandleNatives.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */