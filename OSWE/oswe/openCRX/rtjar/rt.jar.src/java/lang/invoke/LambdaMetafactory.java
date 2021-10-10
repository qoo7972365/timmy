/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.InnerClassLambdaMetafactory;
/*     */ import java.lang.invoke.LambdaConversionException;
/*     */ import java.lang.invoke.LambdaMetafactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LambdaMetafactory
/*     */ {
/*     */   public static final int FLAG_SERIALIZABLE = 1;
/*     */   public static final int FLAG_MARKERS = 2;
/*     */   public static final int FLAG_BRIDGES = 4;
/* 234 */   private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
/* 235 */   private static final MethodType[] EMPTY_MT_ARRAY = new MethodType[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CallSite metafactory(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType1, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3) throws LambdaConversionException {
/* 299 */     InnerClassLambdaMetafactory innerClassLambdaMetafactory = new InnerClassLambdaMetafactory(paramLookup, paramMethodType1, paramString, paramMethodType2, paramMethodHandle, paramMethodType3, false, EMPTY_CLASS_ARRAY, EMPTY_MT_ARRAY);
/*     */ 
/*     */ 
/*     */     
/* 303 */     innerClassLambdaMetafactory.validateMetafactoryArgs();
/* 304 */     return innerClassLambdaMetafactory.buildCallSite();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CallSite altMetafactory(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType, Object... paramVarArgs) throws LambdaConversionException {
/*     */     Class<?>[] arrayOfClass;
/* 431 */     MethodType arrayOfMethodType[], methodType1 = (MethodType)paramVarArgs[0];
/* 432 */     MethodHandle methodHandle = (MethodHandle)paramVarArgs[1];
/* 433 */     MethodType methodType2 = (MethodType)paramVarArgs[2];
/* 434 */     int i = ((Integer)paramVarArgs[3]).intValue();
/*     */ 
/*     */     
/* 437 */     int j = 4;
/* 438 */     if ((i & 0x2) != 0) {
/* 439 */       int k = ((Integer)paramVarArgs[j++]).intValue();
/* 440 */       arrayOfClass = new Class[k];
/* 441 */       System.arraycopy(paramVarArgs, j, arrayOfClass, 0, k);
/* 442 */       j += k;
/*     */     } else {
/*     */       
/* 445 */       arrayOfClass = EMPTY_CLASS_ARRAY;
/* 446 */     }  if ((i & 0x4) != 0) {
/* 447 */       int k = ((Integer)paramVarArgs[j++]).intValue();
/* 448 */       arrayOfMethodType = new MethodType[k];
/* 449 */       System.arraycopy(paramVarArgs, j, arrayOfMethodType, 0, k);
/* 450 */       j += k;
/*     */     } else {
/*     */       
/* 453 */       arrayOfMethodType = EMPTY_MT_ARRAY;
/*     */     } 
/* 455 */     boolean bool = ((i & 0x1) != 0) ? true : false;
/* 456 */     if (bool) {
/* 457 */       boolean bool1 = Serializable.class.isAssignableFrom(paramMethodType.returnType());
/* 458 */       for (Class<?> clazz : arrayOfClass)
/* 459 */         bool1 |= Serializable.class.isAssignableFrom(clazz); 
/* 460 */       if (!bool1) {
/* 461 */         arrayOfClass = (Class[])Arrays.<Class<?>[]>copyOf((Class<?>[][])arrayOfClass, arrayOfClass.length + 1);
/* 462 */         arrayOfClass[arrayOfClass.length - 1] = Serializable.class;
/*     */       } 
/*     */     } 
/*     */     
/* 466 */     InnerClassLambdaMetafactory innerClassLambdaMetafactory = new InnerClassLambdaMetafactory(paramLookup, paramMethodType, paramString, methodType1, methodHandle, methodType2, bool, arrayOfClass, arrayOfMethodType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 473 */     innerClassLambdaMetafactory.validateMetafactoryArgs();
/* 474 */     return innerClassLambdaMetafactory.buildCallSite();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/LambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */