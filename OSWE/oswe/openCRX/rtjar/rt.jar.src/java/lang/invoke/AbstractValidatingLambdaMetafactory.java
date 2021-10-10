/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.AbstractValidatingLambdaMetafactory;
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.LambdaConversionException;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleInfo;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import sun.invoke.util.Wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractValidatingLambdaMetafactory
/*     */ {
/*     */   final Class<?> targetClass;
/*     */   final MethodType invokedType;
/*     */   final Class<?> samBase;
/*     */   final String samMethodName;
/*     */   final MethodType samMethodType;
/*     */   final MethodHandle implMethod;
/*     */   final MethodHandleInfo implInfo;
/*     */   final int implKind;
/*     */   final boolean implIsInstanceMethod;
/*     */   final Class<?> implDefiningClass;
/*     */   final MethodType implMethodType;
/*     */   final MethodType instantiatedMethodType;
/*     */   final boolean isSerializable;
/*     */   final Class<?>[] markerInterfaces;
/*     */   final MethodType[] additionalBridges;
/*     */   
/*     */   AbstractValidatingLambdaMetafactory(MethodHandles.Lookup paramLookup, MethodType paramMethodType1, String paramString, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3, boolean paramBoolean, Class<?>[] paramArrayOfClass, MethodType[] paramArrayOfMethodType) throws LambdaConversionException {
/* 117 */     if ((paramLookup.lookupModes() & 0x2) == 0) {
/* 118 */       throw new LambdaConversionException(String.format("Invalid caller: %s", new Object[] { paramLookup
/*     */               
/* 120 */               .lookupClass().getName() }));
/*     */     }
/* 122 */     this.targetClass = paramLookup.lookupClass();
/* 123 */     this.invokedType = paramMethodType1;
/*     */     
/* 125 */     this.samBase = paramMethodType1.returnType();
/*     */     
/* 127 */     this.samMethodName = paramString;
/* 128 */     this.samMethodType = paramMethodType2;
/*     */     
/* 130 */     this.implMethod = paramMethodHandle;
/* 131 */     this.implInfo = paramLookup.revealDirect(paramMethodHandle);
/* 132 */     this.implKind = this.implInfo.getReferenceKind();
/* 133 */     this.implIsInstanceMethod = (this.implKind == 5 || this.implKind == 7 || this.implKind == 9);
/*     */ 
/*     */ 
/*     */     
/* 137 */     this.implDefiningClass = this.implInfo.getDeclaringClass();
/* 138 */     this.implMethodType = this.implInfo.getMethodType();
/* 139 */     this.instantiatedMethodType = paramMethodType3;
/* 140 */     this.isSerializable = paramBoolean;
/* 141 */     this.markerInterfaces = paramArrayOfClass;
/* 142 */     this.additionalBridges = paramArrayOfMethodType;
/*     */     
/* 144 */     if (!this.samBase.isInterface()) {
/* 145 */       throw new LambdaConversionException(String.format("Functional interface %s is not an interface", new Object[] { this.samBase
/*     */               
/* 147 */               .getName() }));
/*     */     }
/*     */     
/* 150 */     for (Class<?> clazz : paramArrayOfClass) {
/* 151 */       if (!clazz.isInterface()) {
/* 152 */         throw new LambdaConversionException(String.format("Marker interface %s is not an interface", new Object[] { clazz
/*     */                 
/* 154 */                 .getName() }));
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
/*     */   abstract CallSite buildCallSite() throws LambdaConversionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void validateMetafactoryArgs() throws LambdaConversionException {
/*     */     byte b2, b3;
/* 174 */     switch (this.implKind) {
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */         break;
/*     */       default:
/* 182 */         throw new LambdaConversionException(String.format("Unsupported MethodHandle kind: %s", new Object[] { this.implInfo }));
/*     */     } 
/*     */ 
/*     */     
/* 186 */     int i = this.implMethodType.parameterCount();
/* 187 */     byte b1 = this.implIsInstanceMethod ? 1 : 0;
/* 188 */     int j = this.invokedType.parameterCount();
/* 189 */     int k = this.samMethodType.parameterCount();
/* 190 */     int m = this.instantiatedMethodType.parameterCount();
/* 191 */     if (i + b1 != j + k)
/* 192 */       throw new LambdaConversionException(
/* 193 */           String.format("Incorrect number of parameters for %s method %s; %d captured parameters, %d functional interface method parameters, %d implementation parameters", new Object[] {
/*     */               
/* 195 */               this.implIsInstanceMethod ? "instance" : "static", this.implInfo, Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(i)
/*     */             })); 
/* 197 */     if (m != k)
/* 198 */       throw new LambdaConversionException(
/* 199 */           String.format("Incorrect number of parameters for %s method %s; %d instantiated parameters, %d functional interface method parameters", new Object[] {
/*     */               
/* 201 */               this.implIsInstanceMethod ? "instance" : "static", this.implInfo, Integer.valueOf(m), Integer.valueOf(k)
/*     */             })); 
/* 203 */     for (MethodType methodType : this.additionalBridges) {
/* 204 */       if (methodType.parameterCount() != k) {
/* 205 */         throw new LambdaConversionException(
/* 206 */             String.format("Incorrect number of parameters for bridge signature %s; incompatible with %s", new Object[] { methodType, this.samMethodType }));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (this.implIsInstanceMethod) {
/*     */       Class<?> clazz4;
/*     */ 
/*     */       
/* 218 */       if (j == 0) {
/*     */         
/* 220 */         b2 = 0;
/* 221 */         b3 = 1;
/* 222 */         clazz4 = this.instantiatedMethodType.parameterType(0);
/*     */       } else {
/*     */         
/* 225 */         b2 = 1;
/* 226 */         b3 = 0;
/* 227 */         clazz4 = this.invokedType.parameterType(0);
/*     */       } 
/*     */ 
/*     */       
/* 231 */       if (!this.implDefiningClass.isAssignableFrom(clazz4)) {
/* 232 */         throw new LambdaConversionException(
/* 233 */             String.format("Invalid receiver type %s; not a subtype of implementation type %s", new Object[] { clazz4, this.implDefiningClass }));
/*     */       }
/*     */ 
/*     */       
/* 237 */       Class<?> clazz5 = this.implMethod.type().parameterType(0);
/* 238 */       if (clazz5 != this.implDefiningClass && !clazz5.isAssignableFrom(clazz4)) {
/* 239 */         throw new LambdaConversionException(
/* 240 */             String.format("Invalid receiver type %s; not a subtype of implementation receiver type %s", new Object[] { clazz4, clazz5 }));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 245 */       b2 = 0;
/* 246 */       b3 = 0;
/*     */     } 
/*     */ 
/*     */     
/* 250 */     int n = j - b2; int i1;
/* 251 */     for (i1 = 0; i1 < n; i1++) {
/* 252 */       Class<?> clazz4 = this.implMethodType.parameterType(i1);
/* 253 */       Class<?> clazz5 = this.invokedType.parameterType(i1 + b2);
/* 254 */       if (!clazz5.equals(clazz4)) {
/* 255 */         throw new LambdaConversionException(
/* 256 */             String.format("Type mismatch in captured lambda parameter %d: expecting %s, found %s", new Object[] {
/* 257 */                 Integer.valueOf(i1), clazz5, clazz4
/*     */               }));
/*     */       }
/*     */     } 
/* 261 */     i1 = b3 - n;
/* 262 */     for (int i2 = n; i2 < i; i2++) {
/* 263 */       Class<?> clazz4 = this.implMethodType.parameterType(i2);
/* 264 */       Class<?> clazz5 = this.instantiatedMethodType.parameterType(i2 + i1);
/* 265 */       if (!isAdaptableTo(clazz5, clazz4, true)) {
/* 266 */         throw new LambdaConversionException(
/* 267 */             String.format("Type mismatch for lambda argument %d: %s is not convertible to %s", new Object[] {
/* 268 */                 Integer.valueOf(i2), clazz5, clazz4
/*     */               }));
/*     */       }
/*     */     } 
/*     */     
/* 273 */     Class<?> clazz1 = this.instantiatedMethodType.returnType();
/*     */ 
/*     */ 
/*     */     
/* 277 */     Class<?> clazz2 = (this.implKind == 8) ? this.implDefiningClass : this.implMethodType.returnType();
/* 278 */     Class<?> clazz3 = this.samMethodType.returnType();
/* 279 */     if (!isAdaptableToAsReturn(clazz2, clazz1)) {
/* 280 */       throw new LambdaConversionException(
/* 281 */           String.format("Type mismatch for lambda return: %s is not convertible to %s", new Object[] { clazz2, clazz1 }));
/*     */     }
/*     */     
/* 284 */     if (!isAdaptableToAsReturnStrict(clazz1, clazz3)) {
/* 285 */       throw new LambdaConversionException(
/* 286 */           String.format("Type mismatch for lambda expected return: %s is not convertible to %s", new Object[] { clazz1, clazz3 }));
/*     */     }
/*     */     
/* 289 */     for (MethodType methodType : this.additionalBridges) {
/* 290 */       if (!isAdaptableToAsReturnStrict(clazz1, methodType.returnType())) {
/* 291 */         throw new LambdaConversionException(
/* 292 */             String.format("Type mismatch for lambda expected return: %s is not convertible to %s", new Object[] {
/* 293 */                 clazz1, methodType.returnType()
/*     */               }));
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
/*     */   private boolean isAdaptableTo(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean) {
/* 306 */     if (paramClass1.equals(paramClass2)) {
/* 307 */       return true;
/*     */     }
/* 309 */     if (paramClass1.isPrimitive()) {
/* 310 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass1);
/* 311 */       if (paramClass2.isPrimitive()) {
/*     */         
/* 313 */         Wrapper wrapper1 = Wrapper.forPrimitiveType(paramClass2);
/* 314 */         return wrapper1.isConvertibleFrom(wrapper);
/*     */       } 
/*     */       
/* 317 */       return paramClass2.isAssignableFrom(wrapper.wrapperType());
/*     */     } 
/*     */     
/* 320 */     if (paramClass2.isPrimitive()) {
/*     */       Wrapper wrapper;
/*     */       
/* 323 */       if (Wrapper.isWrapperType(paramClass1) && (wrapper = Wrapper.forWrapperType(paramClass1)).primitiveType().isPrimitive()) {
/*     */         
/* 325 */         Wrapper wrapper1 = Wrapper.forPrimitiveType(paramClass2);
/* 326 */         return wrapper1.isConvertibleFrom(wrapper);
/*     */       } 
/*     */       
/* 329 */       return !paramBoolean;
/*     */     } 
/*     */ 
/*     */     
/* 333 */     return (!paramBoolean || paramClass2.isAssignableFrom(paramClass1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAdaptableToAsReturn(Class<?> paramClass1, Class<?> paramClass2) {
/* 344 */     return (paramClass2.equals(void.class) || (
/* 345 */       !paramClass1.equals(void.class) && isAdaptableTo(paramClass1, paramClass2, false)));
/*     */   }
/*     */   private boolean isAdaptableToAsReturnStrict(Class<?> paramClass1, Class<?> paramClass2) {
/* 348 */     if (paramClass1.equals(void.class)) return paramClass2.equals(void.class); 
/* 349 */     return isAdaptableTo(paramClass1, paramClass2, true);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/AbstractValidatingLambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */