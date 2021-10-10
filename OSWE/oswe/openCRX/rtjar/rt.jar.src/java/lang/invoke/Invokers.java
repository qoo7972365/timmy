/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.DirectMethodHandle;
/*     */ import java.lang.invoke.DontInline;
/*     */ import java.lang.invoke.ForceInline;
/*     */ import java.lang.invoke.InvokerBytecodeGenerator;
/*     */ import java.lang.invoke.Invokers;
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.Stable;
/*     */ import java.lang.invoke.WrongMethodTypeException;
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
/*     */ class Invokers
/*     */ {
/*     */   private final MethodType targetType;
/*     */   @Stable
/*  45 */   private final MethodHandle[] invokers = new MethodHandle[3];
/*     */   static final int INV_EXACT = 0;
/*     */   static final int INV_GENERIC = 1;
/*     */   static final int INV_BASIC = 2;
/*     */   static final int INV_LIMIT = 3;
/*     */   private static final int MH_LINKER_ARG_APPENDED = 1;
/*     */   private static final LambdaForm.NamedFunction NF_checkExactType;
/*     */   private static final LambdaForm.NamedFunction NF_checkGenericType;
/*     */   private static final LambdaForm.NamedFunction NF_getCallSiteTarget;
/*     */   private static final LambdaForm.NamedFunction NF_checkCustomized;
/*     */   
/*     */   Invokers(MethodType paramMethodType) {
/*  57 */     this.targetType = paramMethodType;
/*     */   }
/*     */   
/*     */   MethodHandle exactInvoker() {
/*  61 */     MethodHandle methodHandle = cachedInvoker(0);
/*  62 */     if (methodHandle != null) return methodHandle; 
/*  63 */     methodHandle = makeExactOrGeneralInvoker(true);
/*  64 */     return setCachedInvoker(0, methodHandle);
/*     */   }
/*     */   
/*     */   MethodHandle genericInvoker() {
/*  68 */     MethodHandle methodHandle = cachedInvoker(1);
/*  69 */     if (methodHandle != null) return methodHandle; 
/*  70 */     methodHandle = makeExactOrGeneralInvoker(false);
/*  71 */     return setCachedInvoker(1, methodHandle);
/*     */   }
/*     */   
/*     */   MethodHandle basicInvoker() {
/*  75 */     MethodHandle methodHandle = cachedInvoker(2);
/*  76 */     if (methodHandle != null) return methodHandle; 
/*  77 */     MethodType methodType = this.targetType.basicType();
/*  78 */     if (methodType != this.targetType)
/*     */     {
/*  80 */       return setCachedInvoker(2, methodType.invokers().basicInvoker());
/*     */     }
/*  82 */     methodHandle = methodType.form().cachedMethodHandle(0);
/*  83 */     if (methodHandle == null) {
/*  84 */       MemberName memberName = invokeBasicMethod(methodType);
/*  85 */       methodHandle = DirectMethodHandle.make(memberName);
/*  86 */       assert checkInvoker(methodHandle);
/*  87 */       methodHandle = methodType.form().setCachedMethodHandle(0, methodHandle);
/*     */     } 
/*  89 */     return setCachedInvoker(2, methodHandle);
/*     */   }
/*     */   
/*     */   private MethodHandle cachedInvoker(int paramInt) {
/*  93 */     return this.invokers[paramInt];
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized MethodHandle setCachedInvoker(int paramInt, MethodHandle paramMethodHandle) {
/*  98 */     MethodHandle methodHandle = this.invokers[paramInt];
/*  99 */     if (methodHandle != null) return methodHandle; 
/* 100 */     this.invokers[paramInt] = paramMethodHandle; return paramMethodHandle;
/*     */   }
/*     */   
/*     */   private MethodHandle makeExactOrGeneralInvoker(boolean paramBoolean) {
/* 104 */     MethodType methodType1 = this.targetType;
/* 105 */     MethodType methodType2 = methodType1.invokerType();
/* 106 */     byte b = paramBoolean ? 11 : 13;
/* 107 */     LambdaForm lambdaForm = invokeHandleForm(methodType1, false, b);
/* 108 */     BoundMethodHandle boundMethodHandle = BoundMethodHandle.bindSingle(methodType2, lambdaForm, methodType1);
/* 109 */     String str = paramBoolean ? "invokeExact" : "invoke";
/* 110 */     MethodHandle methodHandle = boundMethodHandle.withInternalMemberName(MemberName.makeMethodHandleInvoke(str, methodType1), false);
/* 111 */     assert checkInvoker(methodHandle);
/* 112 */     maybeCompileToBytecode(methodHandle);
/* 113 */     return methodHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeCompileToBytecode(MethodHandle paramMethodHandle) {
/* 119 */     if (this.targetType == this.targetType.erase() && this.targetType
/* 120 */       .parameterCount() < 10) {
/* 121 */       paramMethodHandle.form.compileToBytecode();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static MemberName invokeBasicMethod(MethodType paramMethodType) {
/* 127 */     assert paramMethodType == paramMethodType.basicType();
/*     */     
/*     */     try {
/* 130 */       return MethodHandles.Lookup.IMPL_LOOKUP.resolveOrFail((byte)5, MethodHandle.class, "invokeBasic", paramMethodType);
/* 131 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 132 */       throw MethodHandleStatics.newInternalError("JVM cannot find invoker for " + paramMethodType, reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkInvoker(MethodHandle paramMethodHandle) {
/* 137 */     assert this.targetType.invokerType().equals(paramMethodHandle.type()) : 
/* 138 */       Arrays.asList((T[])new Object[] { this.targetType, this.targetType.invokerType(), paramMethodHandle });
/* 139 */     assert paramMethodHandle.internalMemberName() == null || paramMethodHandle
/* 140 */       .internalMemberName().getMethodType().equals(this.targetType);
/* 141 */     assert !paramMethodHandle.isVarargsCollector();
/* 142 */     return true;
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
/*     */   MethodHandle spreadInvoker(int paramInt) {
/* 154 */     int i = this.targetType.parameterCount() - paramInt;
/* 155 */     MethodType methodType1 = this.targetType;
/* 156 */     Class<?> clazz = impliedRestargType(methodType1, paramInt);
/* 157 */     if (methodType1.parameterSlotCount() <= 253) {
/* 158 */       return genericInvoker().asSpreader(clazz, i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     MethodType methodType2 = methodType1.replaceParameterTypes(paramInt, methodType1.parameterCount(), new Class[] { clazz });
/* 165 */     MethodHandle methodHandle1 = MethodHandles.invoker(methodType2);
/* 166 */     MethodHandle methodHandle2 = MethodHandles.insertArguments(Lazy.MH_asSpreader, 1, new Object[] { clazz, Integer.valueOf(i) });
/* 167 */     return MethodHandles.filterArgument(methodHandle1, 0, methodHandle2);
/*     */   }
/*     */   
/*     */   private static Class<?> impliedRestargType(MethodType paramMethodType, int paramInt) {
/* 171 */     if (paramMethodType.isGeneric()) return Object[].class; 
/* 172 */     int i = paramMethodType.parameterCount();
/* 173 */     if (paramInt >= i) return Object[].class; 
/* 174 */     Class<?> clazz = paramMethodType.parameterType(paramInt);
/* 175 */     for (int j = paramInt + 1; j < i; j++) {
/* 176 */       if (clazz != paramMethodType.parameterType(j))
/* 177 */         throw MethodHandleStatics.newIllegalArgumentException("need homogeneous rest arguments", paramMethodType); 
/*     */     } 
/* 179 */     if (clazz == Object.class) return Object[].class; 
/* 180 */     return Array.newInstance(clazz, 0).getClass();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 184 */     return "Invokers" + this.targetType;
/*     */   }
/*     */ 
/*     */   
/*     */   static MemberName methodHandleInvokeLinkerMethod(String paramString, MethodType paramMethodType, Object[] paramArrayOfObject) {
/*     */     byte b;
/*     */     LambdaForm lambdaForm;
/* 191 */     switch (paramString) { case "invokeExact":
/* 192 */         b = 10; break;
/* 193 */       case "invoke": b = 12; break;
/* 194 */       default: throw new InternalError("not invoker: " + paramString); }
/*     */ 
/*     */     
/* 197 */     if (paramMethodType.parameterSlotCount() <= 253) {
/* 198 */       lambdaForm = invokeHandleForm(paramMethodType, false, b);
/* 199 */       paramArrayOfObject[0] = paramMethodType;
/*     */     } else {
/* 201 */       lambdaForm = invokeHandleForm(paramMethodType, true, b);
/*     */     } 
/* 203 */     return lambdaForm.vmentry;
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
/*     */   private static LambdaForm invokeHandleForm(MethodType paramMethodType, boolean paramBoolean, int paramInt) {
/*     */     boolean bool1, bool2, bool3;
/*     */     String str;
/* 220 */     if (!paramBoolean) {
/* 221 */       paramMethodType = paramMethodType.basicType();
/* 222 */       bool1 = true;
/*     */     } else {
/* 224 */       bool1 = false;
/*     */     } 
/*     */ 
/*     */     
/* 228 */     switch (paramInt) { case 10:
/* 229 */         bool2 = true; bool3 = false; str = "invokeExact_MT"; break;
/* 230 */       case 11: bool2 = false; bool3 = false; str = "exactInvoker"; break;
/* 231 */       case 12: bool2 = true; bool3 = true; str = "invoke_MT"; break;
/* 232 */       case 13: bool2 = false; bool3 = true; str = "invoker"; break;
/* 233 */       default: throw new InternalError(); }
/*     */ 
/*     */     
/* 236 */     if (bool1) {
/* 237 */       LambdaForm lambdaForm1 = paramMethodType.form().cachedLambdaForm(paramInt);
/* 238 */       if (lambdaForm1 != null) return lambdaForm1;
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 243 */     int i = 0 + (bool2 ? 0 : 1);
/* 244 */     int j = i + 1;
/* 245 */     int k = j + paramMethodType.parameterCount();
/* 246 */     int m = k + ((bool2 && !paramBoolean) ? 1 : 0);
/* 247 */     int n = k;
/* 248 */     byte b = paramBoolean ? -1 : n++;
/* 249 */     int i1 = n++;
/* 250 */     boolean bool4 = (MethodHandleStatics.CUSTOMIZE_THRESHOLD >= 0) ? n++ : true;
/* 251 */     int i2 = n++;
/* 252 */     MethodType methodType1 = paramMethodType.invokerType();
/* 253 */     if (bool2) {
/* 254 */       if (!paramBoolean)
/* 255 */         methodType1 = methodType1.appendParameterTypes(new Class[] { MemberName.class }); 
/*     */     } else {
/* 257 */       methodType1 = methodType1.invokerType();
/*     */     } 
/* 259 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(n - m, methodType1);
/* 260 */     assert arrayOfName.length == n : 
/* 261 */       Arrays.asList((T[])new Serializable[] { paramMethodType, Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt), Integer.valueOf(n), Integer.valueOf(arrayOfName.length) });
/* 262 */     if (b >= m) {
/* 263 */       assert arrayOfName[b] == null;
/* 264 */       BoundMethodHandle.SpeciesData speciesData = BoundMethodHandle.speciesData_L();
/* 265 */       arrayOfName[0] = arrayOfName[0].withConstraint(speciesData);
/* 266 */       LambdaForm.NamedFunction namedFunction = speciesData.getterFunction(0);
/* 267 */       arrayOfName[b] = new LambdaForm.Name(namedFunction, new Object[] { arrayOfName[0] });
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 272 */     MethodType methodType2 = paramMethodType.basicType();
/* 273 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, i, k, Object[].class);
/* 274 */     Object object = paramBoolean ? paramMethodType : arrayOfName[b];
/* 275 */     if (!bool3) {
/* 276 */       arrayOfName[i1] = new LambdaForm.Name(NF_checkExactType, new Object[] { arrayOfName[i], object });
/*     */     } else {
/*     */       
/* 279 */       arrayOfName[i1] = new LambdaForm.Name(NF_checkGenericType, new Object[] { arrayOfName[i], object });
/*     */       
/* 281 */       arrayOfObject[0] = arrayOfName[i1];
/*     */     } 
/* 283 */     if (bool4 != -1) {
/* 284 */       arrayOfName[bool4] = new LambdaForm.Name(NF_checkCustomized, new Object[] { arrayOfObject[0] });
/*     */     }
/* 286 */     arrayOfName[i2] = new LambdaForm.Name(methodType2, arrayOfObject);
/* 287 */     LambdaForm lambdaForm = new LambdaForm(str, m, arrayOfName);
/* 288 */     if (bool2)
/* 289 */       lambdaForm.compileToBytecode(); 
/* 290 */     if (bool1)
/* 291 */       lambdaForm = paramMethodType.form().setCachedLambdaForm(paramInt, lambdaForm); 
/* 292 */     return lambdaForm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static WrongMethodTypeException newWrongMethodTypeException(MethodType paramMethodType1, MethodType paramMethodType2) {
/* 298 */     return new WrongMethodTypeException("expected " + paramMethodType2 + " but found " + paramMethodType1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static void checkExactType(Object paramObject1, Object paramObject2) {
/* 305 */     MethodHandle methodHandle = (MethodHandle)paramObject1;
/* 306 */     MethodType methodType1 = (MethodType)paramObject2;
/* 307 */     MethodType methodType2 = methodHandle.type();
/* 308 */     if (methodType2 != methodType1) {
/* 309 */       throw newWrongMethodTypeException(methodType1, methodType2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static Object checkGenericType(Object paramObject1, Object paramObject2) {
/* 319 */     MethodHandle methodHandle = (MethodHandle)paramObject1;
/* 320 */     MethodType methodType = (MethodType)paramObject2;
/* 321 */     return methodHandle.asType(methodType);
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
/*     */   static MemberName linkToCallSiteMethod(MethodType paramMethodType) {
/* 342 */     LambdaForm lambdaForm = callSiteForm(paramMethodType, false);
/* 343 */     return lambdaForm.vmentry;
/*     */   }
/*     */   
/*     */   static MemberName linkToTargetMethod(MethodType paramMethodType) {
/* 347 */     LambdaForm lambdaForm = callSiteForm(paramMethodType, true);
/* 348 */     return lambdaForm.vmentry;
/*     */   }
/*     */ 
/*     */   
/*     */   private static LambdaForm callSiteForm(MethodType paramMethodType, boolean paramBoolean) {
/* 353 */     paramMethodType = paramMethodType.basicType();
/* 354 */     byte b = paramBoolean ? 15 : 14;
/* 355 */     LambdaForm lambdaForm = paramMethodType.form().cachedLambdaForm(b);
/* 356 */     if (lambdaForm != null) return lambdaForm;
/*     */ 
/*     */ 
/*     */     
/* 360 */     int i = 0 + paramMethodType.parameterCount();
/* 361 */     int j = i + 1;
/* 362 */     int k = i;
/* 363 */     int m = k++;
/* 364 */     boolean bool = paramBoolean ? true : m;
/* 365 */     int n = paramBoolean ? m : k++;
/* 366 */     int i1 = k++;
/* 367 */     MethodType methodType = paramMethodType.appendParameterTypes(new Class[] { paramBoolean ? MethodHandle.class : CallSite.class });
/* 368 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(k - j, methodType);
/* 369 */     assert arrayOfName.length == k;
/* 370 */     assert arrayOfName[m] != null;
/* 371 */     if (!paramBoolean) {
/* 372 */       arrayOfName[n] = new LambdaForm.Name(NF_getCallSiteTarget, new Object[] { arrayOfName[bool] });
/*     */     }
/*     */     
/* 375 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 0, i + 1, Object[].class);
/*     */     
/* 377 */     System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, arrayOfObject.length - 1);
/* 378 */     arrayOfObject[0] = arrayOfName[n];
/* 379 */     arrayOfName[i1] = new LambdaForm.Name(paramMethodType, arrayOfObject);
/* 380 */     lambdaForm = new LambdaForm(paramBoolean ? "linkToTargetMethod" : "linkToCallSite", j, arrayOfName);
/* 381 */     lambdaForm.compileToBytecode();
/* 382 */     lambdaForm = paramMethodType.form().setCachedLambdaForm(b, lambdaForm);
/* 383 */     return lambdaForm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static Object getCallSiteTarget(Object paramObject) {
/* 390 */     return ((CallSite)paramObject).getTarget();
/*     */   }
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static void checkCustomized(Object paramObject) {
/* 396 */     MethodHandle methodHandle = (MethodHandle)paramObject;
/* 397 */     if (methodHandle.form.customized == null) {
/* 398 */       maybeCustomize(methodHandle);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @DontInline
/*     */   static void maybeCustomize(MethodHandle paramMethodHandle) {
/* 405 */     byte b = paramMethodHandle.customizationCount;
/* 406 */     if (b >= MethodHandleStatics.CUSTOMIZE_THRESHOLD) {
/* 407 */       paramMethodHandle.customize();
/*     */     } else {
/* 409 */       paramMethodHandle.customizationCount = (byte)(b + 1);
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
/*     */   static {
/*     */     try {
/* 429 */       LambdaForm.NamedFunction[] arrayOfNamedFunction = { NF_checkExactType = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("checkExactType", new Class[] { Object.class, Object.class })), NF_checkGenericType = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("checkGenericType", new Class[] { Object.class, Object.class })), NF_getCallSiteTarget = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("getCallSiteTarget", new Class[] { Object.class })), NF_checkCustomized = new LambdaForm.NamedFunction(Invokers.class.getDeclaredMethod("checkCustomized", new Class[] { Object.class })) };
/*     */       
/* 431 */       for (LambdaForm.NamedFunction namedFunction : arrayOfNamedFunction) {
/*     */         
/* 433 */         assert InvokerBytecodeGenerator.isStaticallyInvocable(namedFunction.member) : namedFunction;
/* 434 */         namedFunction.resolve();
/*     */       } 
/* 436 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 437 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Lazy {
/*     */     private static final MethodHandle MH_asSpreader;
/*     */     
/*     */     static {
/*     */       try {
/* 446 */         MH_asSpreader = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(MethodHandle.class, "asSpreader", 
/* 447 */             MethodType.methodType(MethodHandle.class, Class.class, new Class[] { int.class }));
/* 448 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 449 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/Invokers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */