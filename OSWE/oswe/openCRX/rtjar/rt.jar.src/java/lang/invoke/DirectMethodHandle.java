/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.DirectMethodHandle;
/*     */ import java.lang.invoke.ForceInline;
/*     */ import java.lang.invoke.InvokerBytecodeGenerator;
/*     */ import java.lang.invoke.Invokers;
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandleNatives;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import sun.invoke.util.ValueConversions;
/*     */ import sun.invoke.util.VerifyAccess;
/*     */ import sun.invoke.util.VerifyType;
/*     */ import sun.invoke.util.Wrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DirectMethodHandle
/*     */   extends MethodHandle
/*     */ {
/*     */   final MemberName member;
/*     */   
/*     */   private DirectMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm, MemberName paramMemberName) {
/*  52 */     super(paramMethodType, paramLambdaForm);
/*  53 */     if (!paramMemberName.isResolved()) throw new InternalError();
/*     */     
/*  55 */     if (paramMemberName.getDeclaringClass().isInterface() && paramMemberName
/*  56 */       .isMethod() && !paramMemberName.isAbstract()) {
/*     */       
/*  58 */       MemberName memberName = new MemberName(Object.class, paramMemberName.getName(), paramMemberName.getMethodType(), paramMemberName.getReferenceKind());
/*  59 */       memberName = MemberName.getFactory().resolveOrNull(memberName.getReferenceKind(), memberName, null);
/*  60 */       if (memberName != null && memberName.isPublic()) {
/*  61 */         assert paramMemberName.getReferenceKind() == memberName.getReferenceKind();
/*  62 */         paramMemberName = memberName;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     this.member = paramMemberName;
/*     */   }
/*     */ 
/*     */   
/*     */   static DirectMethodHandle make(byte paramByte, Class<?> paramClass, MemberName paramMemberName) {
/*  71 */     MethodType methodType = paramMemberName.getMethodOrFieldType();
/*  72 */     if (!paramMemberName.isStatic()) {
/*  73 */       if (!paramMemberName.getDeclaringClass().isAssignableFrom(paramClass) || paramMemberName.isConstructor())
/*  74 */         throw new InternalError(paramMemberName.toString()); 
/*  75 */       methodType = methodType.insertParameterTypes(0, new Class[] { paramClass });
/*     */     } 
/*  77 */     if (!paramMemberName.isField()) {
/*  78 */       switch (paramByte) {
/*     */         case 7:
/*  80 */           paramMemberName = paramMemberName.asSpecial();
/*  81 */           lambdaForm1 = preparedLambdaForm(paramMemberName);
/*  82 */           return new Special(methodType, lambdaForm1, paramMemberName);
/*     */         
/*     */         case 9:
/*  85 */           lambdaForm1 = preparedLambdaForm(paramMemberName);
/*  86 */           return new Interface(methodType, lambdaForm1, paramMemberName, paramClass);
/*     */       } 
/*     */       
/*  89 */       LambdaForm lambdaForm1 = preparedLambdaForm(paramMemberName);
/*  90 */       return new DirectMethodHandle(methodType, lambdaForm1, paramMemberName);
/*     */     } 
/*     */ 
/*     */     
/*  94 */     LambdaForm lambdaForm = preparedFieldLambdaForm(paramMemberName);
/*  95 */     if (paramMemberName.isStatic()) {
/*  96 */       long l1 = MethodHandleNatives.staticFieldOffset(paramMemberName);
/*  97 */       Object object = MethodHandleNatives.staticFieldBase(paramMemberName);
/*  98 */       return new StaticAccessor(methodType, lambdaForm, paramMemberName, object, l1);
/*     */     } 
/* 100 */     long l = MethodHandleNatives.objectFieldOffset(paramMemberName);
/* 101 */     assert l == (int)l;
/* 102 */     return new Accessor(methodType, lambdaForm, paramMemberName, (int)l);
/*     */   }
/*     */ 
/*     */   
/*     */   static DirectMethodHandle make(Class<?> paramClass, MemberName paramMemberName) {
/* 107 */     byte b = paramMemberName.getReferenceKind();
/* 108 */     if (b == 7)
/* 109 */       b = 5; 
/* 110 */     return make(b, paramClass, paramMemberName);
/*     */   }
/*     */   static DirectMethodHandle make(MemberName paramMemberName) {
/* 113 */     if (paramMemberName.isConstructor())
/* 114 */       return makeAllocator(paramMemberName); 
/* 115 */     return make(paramMemberName.getDeclaringClass(), paramMemberName);
/*     */   }
/*     */   static DirectMethodHandle make(Method paramMethod) {
/* 118 */     return make(paramMethod.getDeclaringClass(), new MemberName(paramMethod));
/*     */   }
/*     */   static DirectMethodHandle make(Field paramField) {
/* 121 */     return make(paramField.getDeclaringClass(), new MemberName(paramField));
/*     */   }
/*     */   private static DirectMethodHandle makeAllocator(MemberName paramMemberName) {
/* 124 */     assert paramMemberName.isConstructor() && paramMemberName.getName().equals("<init>");
/* 125 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/* 126 */     paramMemberName = paramMemberName.asConstructor();
/* 127 */     assert paramMemberName.isConstructor() && paramMemberName.getReferenceKind() == 8 : paramMemberName;
/* 128 */     MethodType methodType = paramMemberName.getMethodType().changeReturnType(clazz);
/* 129 */     LambdaForm lambdaForm = preparedLambdaForm(paramMemberName);
/* 130 */     MemberName memberName = paramMemberName.asSpecial();
/* 131 */     assert memberName.getMethodType().returnType() == void.class;
/* 132 */     return new Constructor(methodType, lambdaForm, paramMemberName, memberName, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   BoundMethodHandle rebind() {
/* 137 */     return BoundMethodHandle.makeReinvoker(this);
/*     */   }
/*     */ 
/*     */   
/*     */   MethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/* 142 */     assert getClass() == DirectMethodHandle.class;
/* 143 */     return new DirectMethodHandle(paramMethodType, paramLambdaForm, this.member);
/*     */   }
/*     */ 
/*     */   
/*     */   String internalProperties() {
/* 148 */     return "\n& DMH.MN=" + internalMemberName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   MemberName internalMemberName() {
/* 155 */     return this.member;
/*     */   }
/*     */   
/* 158 */   private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LambdaForm preparedLambdaForm(MemberName paramMemberName) {
/*     */     byte b;
/* 166 */     assert paramMemberName.isInvocable() : paramMemberName;
/* 167 */     MethodType methodType = paramMemberName.getInvocationType().basicType();
/* 168 */     assert !paramMemberName.isMethodHandleInvoke() : paramMemberName;
/*     */     
/* 170 */     switch (paramMemberName.getReferenceKind()) { case 5:
/* 171 */         b = 0; break;
/* 172 */       case 6: b = 1; break;
/* 173 */       case 7: b = 2; break;
/* 174 */       case 9: b = 4; break;
/* 175 */       case 8: b = 3; break;
/* 176 */       default: throw new InternalError(paramMemberName.toString()); }
/*     */     
/* 178 */     if (b == 1 && shouldBeInitialized(paramMemberName)) {
/*     */       
/* 180 */       preparedLambdaForm(methodType, b);
/* 181 */       b = 5;
/*     */     } 
/* 183 */     LambdaForm lambdaForm = preparedLambdaForm(methodType, b);
/* 184 */     maybeCompile(lambdaForm, paramMemberName);
/* 185 */     assert lambdaForm.methodType().dropParameterTypes(0, 1)
/* 186 */       .equals(paramMemberName.getInvocationType().basicType()) : 
/* 187 */       Arrays.asList((T[])new Object[] { paramMemberName, paramMemberName.getInvocationType().basicType(), lambdaForm, lambdaForm.methodType() });
/* 188 */     return lambdaForm;
/*     */   }
/*     */   
/*     */   private static LambdaForm preparedLambdaForm(MethodType paramMethodType, int paramInt) {
/* 192 */     LambdaForm lambdaForm = paramMethodType.form().cachedLambdaForm(paramInt);
/* 193 */     if (lambdaForm != null) return lambdaForm; 
/* 194 */     lambdaForm = makePreparedLambdaForm(paramMethodType, paramInt);
/* 195 */     return paramMethodType.form().setCachedLambdaForm(paramInt, lambdaForm);
/*     */   }
/*     */   private static LambdaForm makePreparedLambdaForm(MethodType paramMethodType, int paramInt) {
/*     */     String str1;
/* 199 */     boolean bool1 = (paramInt == 5) ? true : false;
/* 200 */     boolean bool2 = (paramInt == 3) ? true : false;
/* 201 */     boolean bool3 = (paramInt == 4) ? true : false;
/*     */     
/* 203 */     switch (paramInt) { case 0:
/* 204 */         str1 = "linkToVirtual"; str2 = "DMH.invokeVirtual"; break;
/* 205 */       case 1: str1 = "linkToStatic"; str2 = "DMH.invokeStatic"; break;
/* 206 */       case 5: str1 = "linkToStatic"; str2 = "DMH.invokeStaticInit"; break;
/* 207 */       case 2: str1 = "linkToSpecial"; str2 = "DMH.invokeSpecial"; break;
/* 208 */       case 4: str1 = "linkToInterface"; str2 = "DMH.invokeInterface"; break;
/* 209 */       case 3: str1 = "linkToSpecial"; str2 = "DMH.newInvokeSpecial"; break;
/* 210 */       default: throw new InternalError("which=" + paramInt); }
/*     */     
/* 212 */     MethodType methodType = paramMethodType.appendParameterTypes(new Class[] { MemberName.class });
/* 213 */     if (bool2)
/*     */     {
/*     */       
/* 216 */       methodType = methodType.insertParameterTypes(0, new Class[] { Object.class }).changeReturnType(void.class); } 
/* 217 */     MemberName memberName = new MemberName(MethodHandle.class, str1, methodType, (byte)6);
/*     */     try {
/* 219 */       memberName = IMPL_NAMES.resolveOrFail((byte)6, memberName, null, NoSuchMethodException.class);
/* 220 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 221 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */     } 
/*     */ 
/*     */     
/* 225 */     int i = 1 + paramMethodType.parameterCount();
/* 226 */     int j = i;
/* 227 */     boolean bool4 = bool2 ? j++ : true;
/* 228 */     int k = j++;
/* 229 */     boolean bool5 = bool3 ? j++ : true;
/* 230 */     int m = j++;
/* 231 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, paramMethodType.invokerType());
/* 232 */     assert arrayOfName.length == j;
/* 233 */     if (bool2) {
/*     */       
/* 235 */       arrayOfName[bool4] = new LambdaForm.Name(Lazy.NF_allocateInstance, new Object[] { arrayOfName[0] });
/* 236 */       arrayOfName[k] = new LambdaForm.Name(Lazy.NF_constructorMethod, new Object[] { arrayOfName[0] });
/* 237 */     } else if (bool1) {
/* 238 */       arrayOfName[k] = new LambdaForm.Name(Lazy.NF_internalMemberNameEnsureInit, new Object[] { arrayOfName[0] });
/*     */     } else {
/* 240 */       arrayOfName[k] = new LambdaForm.Name(Lazy.NF_internalMemberName, new Object[] { arrayOfName[0] });
/*     */     } 
/* 242 */     assert findDirectMethodHandle(arrayOfName[k]) == arrayOfName[0];
/* 243 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 1, k + 1, Object[].class);
/* 244 */     if (bool3) {
/* 245 */       arrayOfName[bool5] = new LambdaForm.Name(Lazy.NF_checkReceiver, new Object[] { arrayOfName[0], arrayOfName[1] });
/* 246 */       arrayOfObject[0] = arrayOfName[bool5];
/*     */     } 
/* 248 */     assert arrayOfObject[arrayOfObject.length - 1] == arrayOfName[k];
/* 249 */     byte b = -2;
/* 250 */     if (bool2) {
/* 251 */       assert arrayOfObject[arrayOfObject.length - 2] == arrayOfName[bool4];
/* 252 */       System.arraycopy(arrayOfObject, 0, arrayOfObject, 1, arrayOfObject.length - 2);
/* 253 */       arrayOfObject[0] = arrayOfName[bool4];
/* 254 */       b = bool4;
/*     */     } 
/* 256 */     arrayOfName[m] = new LambdaForm.Name(memberName, arrayOfObject);
/* 257 */     String str2 = str2 + "_" + LambdaForm.shortenSignature(LambdaForm.basicTypeSignature(paramMethodType));
/* 258 */     LambdaForm lambdaForm = new LambdaForm(str2, i, arrayOfName, b);
/*     */     
/* 260 */     lambdaForm.compileToBytecode();
/* 261 */     return lambdaForm;
/*     */   }
/*     */   
/*     */   static Object findDirectMethodHandle(LambdaForm.Name paramName) {
/* 265 */     if (paramName.function == Lazy.NF_internalMemberName || paramName.function == Lazy.NF_internalMemberNameEnsureInit || paramName.function == Lazy.NF_constructorMethod) {
/*     */ 
/*     */       
/* 268 */       assert paramName.arguments.length == 1;
/* 269 */       return paramName.arguments[0];
/*     */     } 
/* 271 */     return null;
/*     */   }
/*     */   
/*     */   private static void maybeCompile(LambdaForm paramLambdaForm, MemberName paramMemberName) {
/* 275 */     if (VerifyAccess.isSamePackage(paramMemberName.getDeclaringClass(), MethodHandle.class))
/*     */     {
/* 277 */       paramLambdaForm.compileToBytecode();
/*     */     }
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object internalMemberName(Object paramObject) {
/* 283 */     return ((DirectMethodHandle)paramObject).member;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Object internalMemberNameEnsureInit(Object paramObject) {
/* 290 */     DirectMethodHandle directMethodHandle = (DirectMethodHandle)paramObject;
/* 291 */     directMethodHandle.ensureInitialized();
/* 292 */     return directMethodHandle.member;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean shouldBeInitialized(MemberName paramMemberName) {
/* 297 */     switch (paramMemberName.getReferenceKind()) {
/*     */       case 2:
/*     */       case 4:
/*     */       case 6:
/*     */       case 8:
/*     */         break;
/*     */       
/*     */       default:
/* 305 */         return false;
/*     */     } 
/* 307 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/* 308 */     if (clazz == ValueConversions.class || clazz == MethodHandleImpl.class || clazz == Invokers.class)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 313 */       return false;
/*     */     }
/* 315 */     if (VerifyAccess.isSamePackage(MethodHandle.class, clazz) || 
/* 316 */       VerifyAccess.isSamePackage(ValueConversions.class, clazz)) {
/*     */ 
/*     */       
/* 319 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(clazz)) {
/* 320 */         MethodHandleStatics.UNSAFE.ensureClassInitialized(clazz);
/*     */       }
/* 322 */       return false;
/*     */     } 
/* 324 */     return MethodHandleStatics.UNSAFE.shouldBeInitialized(clazz);
/*     */   }
/*     */   
/*     */   private static class EnsureInitialized
/*     */     extends ClassValue<WeakReference<Thread>> {
/*     */     protected WeakReference<Thread> computeValue(Class<?> param1Class) {
/* 330 */       MethodHandleStatics.UNSAFE.ensureClassInitialized(param1Class);
/* 331 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(param1Class))
/*     */       {
/*     */         
/* 334 */         return new WeakReference<>(Thread.currentThread()); } 
/* 335 */       return null;
/*     */     }
/* 337 */     static final EnsureInitialized INSTANCE = new EnsureInitialized();
/*     */   }
/*     */   
/*     */   private void ensureInitialized() {
/* 341 */     if (checkInitialized(this.member))
/*     */     {
/* 343 */       if (this.member.isField()) {
/* 344 */         updateForm(preparedFieldLambdaForm(this.member));
/*     */       } else {
/* 346 */         updateForm(preparedLambdaForm(this.member));
/*     */       }  } 
/*     */   }
/*     */   private static boolean checkInitialized(MemberName paramMemberName) {
/* 350 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/* 351 */     WeakReference<Thread> weakReference = EnsureInitialized.INSTANCE.get(clazz);
/* 352 */     if (weakReference == null) {
/* 353 */       return true;
/*     */     }
/* 355 */     Thread thread = weakReference.get();
/*     */     
/* 357 */     if (thread == Thread.currentThread()) {
/*     */       
/* 359 */       if (MethodHandleStatics.UNSAFE.shouldBeInitialized(clazz))
/*     */       {
/* 361 */         return false;
/*     */       }
/*     */     } else {
/* 364 */       MethodHandleStatics.UNSAFE.ensureClassInitialized(clazz);
/*     */     } 
/* 366 */     assert !MethodHandleStatics.UNSAFE.shouldBeInitialized(clazz);
/*     */     
/* 368 */     EnsureInitialized.INSTANCE.remove(clazz);
/* 369 */     return true;
/*     */   }
/*     */   
/*     */   static void ensureInitialized(Object paramObject) {
/* 373 */     ((DirectMethodHandle)paramObject).ensureInitialized();
/*     */   }
/*     */   
/*     */   static class Special
/*     */     extends DirectMethodHandle {
/*     */     private Special(MethodType param1MethodType, LambdaForm param1LambdaForm, MemberName param1MemberName) {
/* 379 */       super(param1MethodType, param1LambdaForm, param1MemberName);
/*     */     }
/*     */     
/*     */     boolean isInvokeSpecial() {
/* 383 */       return true;
/*     */     }
/*     */     
/*     */     MethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 387 */       return new Special(param1MethodType, param1LambdaForm, this.member);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Interface extends DirectMethodHandle {
/*     */     private final Class<?> refc;
/*     */     
/*     */     private Interface(MethodType param1MethodType, LambdaForm param1LambdaForm, MemberName param1MemberName, Class<?> param1Class) {
/* 395 */       super(param1MethodType, param1LambdaForm, param1MemberName);
/* 396 */       assert param1Class.isInterface() : param1Class;
/* 397 */       this.refc = param1Class;
/*     */     }
/*     */     
/*     */     MethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 401 */       return new Interface(param1MethodType, param1LambdaForm, this.member, this.refc);
/*     */     }
/*     */     
/*     */     Object checkReceiver(Object param1Object) {
/* 405 */       if (!this.refc.isInstance(param1Object)) {
/* 406 */         String str = String.format("Class %s does not implement the requested interface %s", new Object[] { param1Object
/* 407 */               .getClass().getName(), this.refc.getName() });
/* 408 */         throw new IncompatibleClassChangeError(str);
/*     */       } 
/* 410 */       return param1Object;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Constructor
/*     */     extends DirectMethodHandle
/*     */   {
/*     */     final MemberName initMethod;
/*     */     final Class<?> instanceClass;
/*     */     
/*     */     private Constructor(MethodType param1MethodType, LambdaForm param1LambdaForm, MemberName param1MemberName1, MemberName param1MemberName2, Class<?> param1Class) {
/* 421 */       super(param1MethodType, param1LambdaForm, param1MemberName1);
/* 422 */       this.initMethod = param1MemberName2;
/* 423 */       this.instanceClass = param1Class;
/* 424 */       assert param1MemberName2.isResolved();
/*     */     }
/*     */     
/*     */     MethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 428 */       return new Constructor(param1MethodType, param1LambdaForm, this.member, this.initMethod, this.instanceClass);
/*     */     }
/*     */   }
/*     */   
/*     */   static Object constructorMethod(Object paramObject) {
/* 433 */     Constructor constructor = (Constructor)paramObject;
/* 434 */     return constructor.initMethod;
/*     */   }
/*     */   
/*     */   static Object allocateInstance(Object paramObject) throws InstantiationException {
/* 438 */     Constructor constructor = (Constructor)paramObject;
/* 439 */     return MethodHandleStatics.UNSAFE.allocateInstance(constructor.instanceClass);
/*     */   }
/*     */   
/*     */   static class Accessor
/*     */     extends DirectMethodHandle {
/*     */     final Class<?> fieldType;
/*     */     final int fieldOffset;
/*     */     
/*     */     private Accessor(MethodType param1MethodType, LambdaForm param1LambdaForm, MemberName param1MemberName, int param1Int) {
/* 448 */       super(param1MethodType, param1LambdaForm, param1MemberName);
/* 449 */       this.fieldType = param1MemberName.getFieldType();
/* 450 */       this.fieldOffset = param1Int;
/*     */     }
/*     */     
/*     */     Object checkCast(Object param1Object) {
/* 454 */       return this.fieldType.cast(param1Object);
/*     */     }
/*     */     
/*     */     MethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 458 */       return new Accessor(param1MethodType, param1LambdaForm, this.member, this.fieldOffset);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static long fieldOffset(Object paramObject) {
/* 466 */     return ((Accessor)paramObject).fieldOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ForceInline
/*     */   static Object checkBase(Object paramObject) {
/* 478 */     paramObject.getClass();
/* 479 */     return paramObject;
/*     */   }
/*     */   
/*     */   static class StaticAccessor
/*     */     extends DirectMethodHandle
/*     */   {
/*     */     private final Class<?> fieldType;
/*     */     private final Object staticBase;
/*     */     private final long staticOffset;
/*     */     
/*     */     private StaticAccessor(MethodType param1MethodType, LambdaForm param1LambdaForm, MemberName param1MemberName, Object param1Object, long param1Long) {
/* 490 */       super(param1MethodType, param1LambdaForm, param1MemberName);
/* 491 */       this.fieldType = param1MemberName.getFieldType();
/* 492 */       this.staticBase = param1Object;
/* 493 */       this.staticOffset = param1Long;
/*     */     }
/*     */     
/*     */     Object checkCast(Object param1Object) {
/* 497 */       return this.fieldType.cast(param1Object);
/*     */     }
/*     */     
/*     */     MethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 501 */       return new StaticAccessor(param1MethodType, param1LambdaForm, this.member, this.staticBase, this.staticOffset);
/*     */     }
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object nullCheck(Object paramObject) {
/* 507 */     paramObject.getClass();
/* 508 */     return paramObject;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object staticBase(Object paramObject) {
/* 513 */     return ((StaticAccessor)paramObject).staticBase;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static long staticOffset(Object paramObject) {
/* 518 */     return ((StaticAccessor)paramObject).staticOffset;
/*     */   }
/*     */   
/*     */   @ForceInline
/*     */   static Object checkCast(Object paramObject1, Object paramObject2) {
/* 523 */     return ((DirectMethodHandle)paramObject1).checkCast(paramObject2);
/*     */   }
/*     */   
/*     */   Object checkCast(Object paramObject) {
/* 527 */     return this.member.getReturnType().cast(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 532 */   private static byte AF_GETFIELD = 0;
/* 533 */   private static byte AF_PUTFIELD = 1;
/* 534 */   private static byte AF_GETSTATIC = 2;
/* 535 */   private static byte AF_PUTSTATIC = 3;
/* 536 */   private static byte AF_GETSTATIC_INIT = 4;
/* 537 */   private static byte AF_PUTSTATIC_INIT = 5;
/* 538 */   private static byte AF_LIMIT = 6;
/*     */ 
/*     */ 
/*     */   
/* 542 */   private static int FT_LAST_WRAPPER = (Wrapper.values()).length - 1;
/* 543 */   private static int FT_UNCHECKED_REF = Wrapper.OBJECT.ordinal();
/* 544 */   private static int FT_CHECKED_REF = FT_LAST_WRAPPER + 1;
/* 545 */   private static int FT_LIMIT = FT_LAST_WRAPPER + 2;
/*     */   private static int afIndex(byte paramByte, boolean paramBoolean, int paramInt) {
/* 547 */     return paramByte * FT_LIMIT * 2 + (paramBoolean ? FT_LIMIT : 0) + paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 552 */   private static final LambdaForm[] ACCESSOR_FORMS = new LambdaForm[afIndex(AF_LIMIT, false, 0)];
/*     */   private static int ftypeKind(Class<?> paramClass) {
/* 554 */     if (paramClass.isPrimitive())
/* 555 */       return Wrapper.forPrimitiveType(paramClass).ordinal(); 
/* 556 */     if (VerifyType.isNullReferenceConversion(Object.class, paramClass)) {
/* 557 */       return FT_UNCHECKED_REF;
/*     */     }
/* 559 */     return FT_CHECKED_REF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LambdaForm preparedFieldLambdaForm(MemberName paramMemberName) {
/*     */     byte b;
/* 568 */     Class<?> clazz = paramMemberName.getFieldType();
/* 569 */     boolean bool = paramMemberName.isVolatile();
/*     */     
/* 571 */     switch (paramMemberName.getReferenceKind()) { case 1:
/* 572 */         b = AF_GETFIELD; break;
/* 573 */       case 3: b = AF_PUTFIELD; break;
/* 574 */       case 2: b = AF_GETSTATIC; break;
/* 575 */       case 4: b = AF_PUTSTATIC; break;
/* 576 */       default: throw new InternalError(paramMemberName.toString()); }
/*     */     
/* 578 */     if (shouldBeInitialized(paramMemberName)) {
/*     */       
/* 580 */       preparedFieldLambdaForm(b, bool, clazz);
/* 581 */       assert AF_GETSTATIC_INIT - AF_GETSTATIC == AF_PUTSTATIC_INIT - AF_PUTSTATIC;
/*     */       
/* 583 */       b = (byte)(b + AF_GETSTATIC_INIT - AF_GETSTATIC);
/*     */     } 
/* 585 */     LambdaForm lambdaForm = preparedFieldLambdaForm(b, bool, clazz);
/* 586 */     maybeCompile(lambdaForm, paramMemberName);
/* 587 */     assert lambdaForm.methodType().dropParameterTypes(0, 1)
/* 588 */       .equals(paramMemberName.getInvocationType().basicType()) : 
/* 589 */       Arrays.asList((T[])new Object[] { paramMemberName, paramMemberName.getInvocationType().basicType(), lambdaForm, lambdaForm.methodType() });
/* 590 */     return lambdaForm;
/*     */   }
/*     */   private static LambdaForm preparedFieldLambdaForm(byte paramByte, boolean paramBoolean, Class<?> paramClass) {
/* 593 */     int i = afIndex(paramByte, paramBoolean, ftypeKind(paramClass));
/* 594 */     LambdaForm lambdaForm = ACCESSOR_FORMS[i];
/* 595 */     if (lambdaForm != null) return lambdaForm; 
/* 596 */     lambdaForm = makePreparedFieldLambdaForm(paramByte, paramBoolean, ftypeKind(paramClass));
/* 597 */     ACCESSOR_FORMS[i] = lambdaForm;
/* 598 */     return lambdaForm;
/*     */   }
/*     */   private static LambdaForm makePreparedFieldLambdaForm(byte paramByte, boolean paramBoolean, int paramInt) {
/*     */     MethodType methodType1;
/* 602 */     boolean bool1 = ((paramByte & 0x1) == (AF_GETFIELD & 0x1)) ? true : false;
/* 603 */     boolean bool2 = (paramByte >= AF_GETSTATIC) ? true : false;
/* 604 */     boolean bool3 = (paramByte >= AF_GETSTATIC_INIT) ? true : false;
/* 605 */     boolean bool4 = (paramInt == FT_CHECKED_REF) ? true : false;
/* 606 */     Wrapper wrapper = bool4 ? Wrapper.OBJECT : Wrapper.values()[paramInt];
/* 607 */     Class<?> clazz = wrapper.primitiveType();
/* 608 */     assert ftypeKind(bool4 ? String.class : clazz) == paramInt;
/* 609 */     String str1 = wrapper.primitiveSimpleName();
/* 610 */     String str2 = Character.toUpperCase(str1.charAt(0)) + str1.substring(1);
/* 611 */     if (paramBoolean) str2 = str2 + "Volatile"; 
/* 612 */     String str3 = bool1 ? "get" : "put";
/* 613 */     String str4 = str3 + str2;
/*     */     
/* 615 */     if (bool1) {
/* 616 */       methodType1 = MethodType.methodType(clazz, Object.class, new Class[] { long.class });
/*     */     } else {
/* 618 */       methodType1 = MethodType.methodType(void.class, Object.class, new Class[] { long.class, clazz });
/* 619 */     }  MemberName memberName = new MemberName(Unsafe.class, str4, methodType1, (byte)5);
/*     */     try {
/* 621 */       memberName = IMPL_NAMES.resolveOrFail((byte)5, memberName, null, NoSuchMethodException.class);
/* 622 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 623 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 628 */     if (bool1) {
/* 629 */       methodType2 = MethodType.methodType(clazz);
/*     */     } else {
/* 631 */       methodType2 = MethodType.methodType(void.class, clazz);
/* 632 */     }  MethodType methodType2 = methodType2.basicType();
/* 633 */     if (!bool2) {
/* 634 */       methodType2 = methodType2.insertParameterTypes(0, new Class[] { Object.class });
/*     */     }
/*     */     
/* 637 */     int i = 1 + methodType2.parameterCount();
/*     */     
/* 639 */     boolean bool5 = bool2 ? true : true;
/*     */     
/* 641 */     boolean bool6 = bool1 ? true : (i - 1);
/* 642 */     int j = i;
/* 643 */     boolean bool7 = bool2 ? j++ : true;
/* 644 */     int k = j++;
/* 645 */     boolean bool8 = bool5 ? j++ : true;
/* 646 */     boolean bool9 = bool3 ? j++ : true;
/* 647 */     boolean bool10 = (bool4 && !bool1) ? j++ : true;
/* 648 */     int m = j++;
/* 649 */     boolean bool11 = (bool4 && bool1) ? j++ : true;
/* 650 */     int n = j - 1;
/* 651 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, methodType2.invokerType());
/* 652 */     if (bool3)
/* 653 */       arrayOfName[bool9] = new LambdaForm.Name(Lazy.NF_ensureInitialized, new Object[] { arrayOfName[0] }); 
/* 654 */     if (bool4 && !bool1)
/* 655 */       arrayOfName[bool10] = new LambdaForm.Name(Lazy.NF_checkCast, new Object[] { arrayOfName[0], arrayOfName[bool6] }); 
/* 656 */     Object[] arrayOfObject = new Object[1 + methodType1.parameterCount()];
/* 657 */     assert arrayOfObject.length == (bool1 ? 3 : 4);
/* 658 */     arrayOfObject[0] = MethodHandleStatics.UNSAFE;
/* 659 */     if (bool2) {
/* 660 */       arrayOfName[bool7] = new LambdaForm.Name(Lazy.NF_staticBase, new Object[] { arrayOfName[0] }); arrayOfObject[1] = new LambdaForm.Name(Lazy.NF_staticBase, new Object[] { arrayOfName[0] });
/* 661 */       arrayOfName[k] = new LambdaForm.Name(Lazy.NF_staticOffset, new Object[] { arrayOfName[0] }); arrayOfObject[2] = new LambdaForm.Name(Lazy.NF_staticOffset, new Object[] { arrayOfName[0] });
/*     */     } else {
/* 663 */       arrayOfName[bool8] = new LambdaForm.Name(Lazy.NF_checkBase, new Object[] { arrayOfName[bool5] }); arrayOfObject[1] = new LambdaForm.Name(Lazy.NF_checkBase, new Object[] { arrayOfName[bool5] });
/* 664 */       arrayOfName[k] = new LambdaForm.Name(Lazy.NF_fieldOffset, new Object[] { arrayOfName[0] }); arrayOfObject[2] = new LambdaForm.Name(Lazy.NF_fieldOffset, new Object[] { arrayOfName[0] });
/*     */     } 
/* 666 */     if (!bool1) {
/* 667 */       arrayOfObject[3] = bool4 ? arrayOfName[bool10] : arrayOfName[bool6];
/*     */     }
/* 669 */     for (Object object : arrayOfObject) assert object != null; 
/* 670 */     arrayOfName[m] = new LambdaForm.Name(memberName, arrayOfObject);
/* 671 */     if (bool4 && bool1)
/* 672 */       arrayOfName[bool11] = new LambdaForm.Name(Lazy.NF_checkCast, new Object[] { arrayOfName[0], arrayOfName[m] }); 
/* 673 */     for (LambdaForm.Name name : arrayOfName) assert name != null; 
/* 674 */     String str5 = bool2 ? "Static" : "Field";
/* 675 */     String str6 = str4 + str5;
/* 676 */     if (bool4) str6 = str6 + "Cast"; 
/* 677 */     if (bool3) str6 = str6 + "Init"; 
/* 678 */     return new LambdaForm(str6, i, arrayOfName, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Lazy
/*     */   {
/*     */     static final LambdaForm.NamedFunction NF_internalMemberName;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_internalMemberNameEnsureInit;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_ensureInitialized;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_fieldOffset;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_checkBase;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_staticBase;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_staticOffset;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_checkCast;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_allocateInstance;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_constructorMethod;
/*     */ 
/*     */     
/*     */     static final LambdaForm.NamedFunction NF_checkReceiver;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 722 */         LambdaForm.NamedFunction[] arrayOfNamedFunction = { NF_internalMemberName = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("internalMemberName", new Class[] { Object.class })), NF_internalMemberNameEnsureInit = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("internalMemberNameEnsureInit", new Class[] { Object.class })), NF_ensureInitialized = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("ensureInitialized", new Class[] { Object.class })), NF_fieldOffset = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("fieldOffset", new Class[] { Object.class })), NF_checkBase = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("checkBase", new Class[] { Object.class })), NF_staticBase = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("staticBase", new Class[] { Object.class })), NF_staticOffset = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("staticOffset", new Class[] { Object.class })), NF_checkCast = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("checkCast", new Class[] { Object.class, Object.class })), NF_allocateInstance = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("allocateInstance", new Class[] { Object.class })), NF_constructorMethod = new LambdaForm.NamedFunction(DirectMethodHandle.class.getDeclaredMethod("constructorMethod", new Class[] { Object.class })), NF_checkReceiver = new LambdaForm.NamedFunction(new MemberName(DirectMethodHandle.Interface.class.getDeclaredMethod("checkReceiver", new Class[] { Object.class }))) };
/*     */         
/* 724 */         for (LambdaForm.NamedFunction namedFunction : arrayOfNamedFunction) {
/*     */           
/* 726 */           assert InvokerBytecodeGenerator.isStaticallyInvocable(namedFunction.member) : namedFunction;
/* 727 */           namedFunction.resolve();
/*     */         } 
/* 729 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 730 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/DirectMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */