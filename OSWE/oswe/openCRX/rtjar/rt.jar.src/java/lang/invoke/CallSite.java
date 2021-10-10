/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.ConstantCallSite;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandleNatives;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.WrongMethodTypeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CallSite
/*     */ {
/*     */   MethodHandle target;
/*     */   private static final MethodHandle GET_TARGET;
/*     */   private static final MethodHandle THROW_UCS;
/*     */   private static final long TARGET_OFFSET;
/*     */   
/*     */   static {
/*  87 */     MethodHandleImpl.initStatics();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 224 */       GET_TARGET = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(CallSite.class, "getTarget", MethodType.methodType(MethodHandle.class));
/*     */       
/* 226 */       THROW_UCS = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(CallSite.class, "uninitializedCallSite", MethodType.methodType(Object.class, Object[].class));
/* 227 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 228 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
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
/* 252 */     try { TARGET_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(CallSite.class.getDeclaredField("target")); }
/* 253 */     catch (Exception exception) { throw new Error(exception); }
/*     */   
/*     */   }
/*     */   CallSite(MethodType paramMethodType) { this.target = makeUninitializedCallSite(paramMethodType); }
/*     */   CallSite(MethodHandle paramMethodHandle) { paramMethodHandle.type(); this.target = paramMethodHandle; }
/* 258 */   CallSite(MethodType paramMethodType, MethodHandle paramMethodHandle) throws Throwable { this(paramMethodType); ConstantCallSite constantCallSite = (ConstantCallSite)this; MethodHandle methodHandle = (MethodHandle)paramMethodHandle.invokeWithArguments(new Object[] { constantCallSite }); checkTargetChange(this.target, methodHandle); this.target = methodHandle; } public MethodType type() { return this.target.type(); } void setTargetNormal(MethodHandle paramMethodHandle) { MethodHandleNatives.setCallSiteTargetNormal(this, paramMethodHandle); }
/*     */   void checkTargetChange(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) { MethodType methodType1 = paramMethodHandle1.type(); MethodType methodType2 = paramMethodHandle2.type(); if (!methodType2.equals(methodType1)) throw wrongTargetType(paramMethodHandle2, methodType1);  }
/*     */   private static WrongMethodTypeException wrongTargetType(MethodHandle paramMethodHandle, MethodType paramMethodType) { return new WrongMethodTypeException(String.valueOf(paramMethodHandle) + " should be of type " + paramMethodType); }
/*     */   MethodHandle makeDynamicInvoker() { BoundMethodHandle boundMethodHandle = GET_TARGET.bindArgumentL(0, this); MethodHandle methodHandle = MethodHandles.exactInvoker(type()); return MethodHandles.foldArguments(methodHandle, boundMethodHandle); }
/* 262 */   private static Object uninitializedCallSite(Object... paramVarArgs) { throw new IllegalStateException("uninitialized call site"); } private MethodHandle makeUninitializedCallSite(MethodType paramMethodType) { MethodType methodType = paramMethodType.basicType(); MethodHandle methodHandle = methodType.form().cachedMethodHandle(2); if (methodHandle == null) { methodHandle = THROW_UCS.asType(methodType); methodHandle = methodType.form().setCachedMethodHandle(2, methodHandle); }  return methodHandle.viewAsType(paramMethodType, false); } MethodHandle getTargetVolatile() { return (MethodHandle)MethodHandleStatics.UNSAFE.getObjectVolatile(this, TARGET_OFFSET); }
/*     */ 
/*     */   
/*     */   void setTargetVolatile(MethodHandle paramMethodHandle) {
/* 266 */     MethodHandleNatives.setCallSiteTargetVolatile(this, paramMethodHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CallSite makeSite(MethodHandle paramMethodHandle, String paramString, MethodType paramMethodType, Object paramObject, Class<?> paramClass) {
/*     */     CallSite callSite;
/* 277 */     MethodHandles.Lookup lookup = MethodHandles.Lookup.IMPL_LOOKUP.in(paramClass);
/*     */     
/*     */     try {
/*     */       Object object;
/* 281 */       paramObject = maybeReBox(paramObject);
/* 282 */       if (paramObject == null) {
/* 283 */         object = paramMethodHandle.invoke(lookup, paramString, paramMethodType);
/* 284 */       } else if (!paramObject.getClass().isArray()) {
/* 285 */         object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, paramObject);
/*     */       } else {
/* 287 */         MethodType methodType1, methodType2; MethodHandle methodHandle1, methodHandle2; Object[] arrayOfObject = (Object[])paramObject;
/* 288 */         maybeReBoxElements(arrayOfObject);
/* 289 */         switch (arrayOfObject.length) {
/*     */           case 0:
/* 291 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType);
/*     */             break;
/*     */           case 1:
/* 294 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0]);
/*     */             break;
/*     */           
/*     */           case 2:
/* 298 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0], arrayOfObject[1]);
/*     */             break;
/*     */           
/*     */           case 3:
/* 302 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0], arrayOfObject[1], arrayOfObject[2]);
/*     */             break;
/*     */           
/*     */           case 4:
/* 306 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0], arrayOfObject[1], arrayOfObject[2], arrayOfObject[3]);
/*     */             break;
/*     */           
/*     */           case 5:
/* 310 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0], arrayOfObject[1], arrayOfObject[2], arrayOfObject[3], arrayOfObject[4]);
/*     */             break;
/*     */           
/*     */           case 6:
/* 314 */             object = paramMethodHandle.invoke(lookup, paramString, paramMethodType, arrayOfObject[0], arrayOfObject[1], arrayOfObject[2], arrayOfObject[3], arrayOfObject[4], arrayOfObject[5]);
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 319 */             if (3 + arrayOfObject.length > 254)
/* 320 */               throw new BootstrapMethodError("too many bootstrap method arguments"); 
/* 321 */             methodType1 = paramMethodHandle.type();
/* 322 */             methodType2 = MethodType.genericMethodType(3 + arrayOfObject.length);
/* 323 */             methodHandle1 = paramMethodHandle.asType(methodType2);
/* 324 */             methodHandle2 = methodType2.invokers().spreadInvoker(3);
/* 325 */             object = methodHandle2.invokeExact(methodHandle1, lookup, paramString, paramMethodType, arrayOfObject);
/*     */             break;
/*     */         } 
/*     */       } 
/* 329 */       if (object instanceof CallSite) {
/* 330 */         callSite = (CallSite)object;
/*     */       } else {
/* 332 */         throw new ClassCastException("bootstrap method failed to produce a CallSite");
/*     */       } 
/* 334 */       if (!callSite.getTarget().type().equals(paramMethodType))
/* 335 */         throw wrongTargetType(callSite.getTarget(), paramMethodType); 
/* 336 */     } catch (Throwable throwable) {
/*     */       BootstrapMethodError bootstrapMethodError;
/* 338 */       if (throwable instanceof BootstrapMethodError) {
/* 339 */         bootstrapMethodError = (BootstrapMethodError)throwable;
/*     */       } else {
/* 341 */         bootstrapMethodError = new BootstrapMethodError("call site initialization exception", throwable);
/* 342 */       }  throw bootstrapMethodError;
/*     */     } 
/* 344 */     return callSite;
/*     */   }
/*     */   
/*     */   private static Object maybeReBox(Object paramObject) {
/* 348 */     if (paramObject instanceof Integer) {
/* 349 */       int i = ((Integer)paramObject).intValue();
/* 350 */       if (i == (byte)i)
/* 351 */         paramObject = Integer.valueOf(i); 
/*     */     } 
/* 353 */     return paramObject;
/*     */   }
/*     */   private static void maybeReBoxElements(Object[] paramArrayOfObject) {
/* 356 */     for (byte b = 0; b < paramArrayOfObject.length; b++)
/* 357 */       paramArrayOfObject[b] = maybeReBox(paramArrayOfObject[b]); 
/*     */   }
/*     */   
/*     */   public abstract MethodHandle getTarget();
/*     */   
/*     */   public abstract void setTarget(MethodHandle paramMethodHandle);
/*     */   
/*     */   public abstract MethodHandle dynamicInvoker();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/CallSite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */