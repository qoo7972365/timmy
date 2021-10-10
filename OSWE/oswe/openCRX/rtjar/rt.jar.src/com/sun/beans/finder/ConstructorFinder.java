/*    */ package com.sun.beans.finder;
/*    */ 
/*    */ import com.sun.beans.util.Cache;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Modifier;
/*    */ import sun.reflect.misc.ReflectUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ConstructorFinder
/*    */   extends AbstractFinder<Constructor<?>>
/*    */ {
/* 45 */   private static final Cache<Signature, Constructor<?>> CACHE = new Cache<Signature, Constructor<?>>(Cache.Kind.SOFT, Cache.Kind.SOFT)
/*    */     {
/*    */       public Constructor create(Signature param1Signature) {
/*    */         try {
/* 49 */           ConstructorFinder constructorFinder = new ConstructorFinder(param1Signature.getArgs());
/* 50 */           return constructorFinder.find(param1Signature.getType().getConstructors());
/*    */         }
/* 52 */         catch (Exception exception) {
/* 53 */           throw new SignatureException(exception);
/*    */         } 
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Constructor<?> findConstructor(Class<?> paramClass, Class<?>... paramVarArgs) throws NoSuchMethodException {
/* 69 */     if (paramClass.isPrimitive()) {
/* 70 */       throw new NoSuchMethodException("Primitive wrapper does not contain constructors");
/*    */     }
/* 72 */     if (paramClass.isInterface()) {
/* 73 */       throw new NoSuchMethodException("Interface does not contain constructors");
/*    */     }
/* 75 */     if (Modifier.isAbstract(paramClass.getModifiers())) {
/* 76 */       throw new NoSuchMethodException("Abstract class cannot be instantiated");
/*    */     }
/* 78 */     if (!Modifier.isPublic(paramClass.getModifiers()) || !ReflectUtil.isPackageAccessible(paramClass)) {
/* 79 */       throw new NoSuchMethodException("Class is not accessible");
/*    */     }
/* 81 */     PrimitiveWrapperMap.replacePrimitivesWithWrappers(paramVarArgs);
/* 82 */     Signature signature = new Signature(paramClass, paramVarArgs);
/*    */     
/*    */     try {
/* 85 */       return CACHE.get(signature);
/*    */     }
/* 87 */     catch (SignatureException signatureException) {
/* 88 */       throw signatureException.toNoSuchMethodException("Constructor is not found");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ConstructorFinder(Class<?>[] paramArrayOfClass) {
/* 98 */     super(paramArrayOfClass);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/ConstructorFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */