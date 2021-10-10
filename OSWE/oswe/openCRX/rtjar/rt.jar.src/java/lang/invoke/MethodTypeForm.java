/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.MethodTypeForm;
/*     */ import java.lang.invoke.Stable;
/*     */ import java.lang.ref.SoftReference;
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
/*     */ final class MethodTypeForm
/*     */ {
/*     */   final int[] argToSlotTable;
/*     */   final int[] slotToArgTable;
/*     */   final long argCounts;
/*     */   final long primCounts;
/*     */   final MethodType erasedType;
/*     */   final MethodType basicType;
/*     */   @Stable
/*     */   final SoftReference<MethodHandle>[] methodHandles;
/*     */   static final int MH_BASIC_INV = 0;
/*     */   static final int MH_NF_INV = 1;
/*     */   static final int MH_UNINIT_CS = 2;
/*     */   static final int MH_LIMIT = 3;
/*     */   @Stable
/*     */   final SoftReference<LambdaForm>[] lambdaForms;
/*     */   static final int LF_INVVIRTUAL = 0;
/*     */   static final int LF_INVSTATIC = 1;
/*     */   static final int LF_INVSPECIAL = 2;
/*     */   static final int LF_NEWINVSPECIAL = 3;
/*     */   static final int LF_INVINTERFACE = 4;
/*     */   static final int LF_INVSTATIC_INIT = 5;
/*     */   static final int LF_INTERPRET = 6;
/*     */   static final int LF_REBIND = 7;
/*     */   static final int LF_DELEGATE = 8;
/*     */   static final int LF_DELEGATE_BLOCK_INLINING = 9;
/*     */   static final int LF_EX_LINKER = 10;
/*     */   static final int LF_EX_INVOKER = 11;
/*     */   static final int LF_GEN_LINKER = 12;
/*     */   static final int LF_GEN_INVOKER = 13;
/*     */   static final int LF_CS_LINKER = 14;
/*     */   static final int LF_MH_LINKER = 15;
/*     */   static final int LF_GWC = 16;
/*     */   static final int LF_GWT = 17;
/*     */   static final int LF_LIMIT = 18;
/*     */   public static final int NO_CHANGE = 0;
/*     */   public static final int ERASE = 1;
/*     */   public static final int WRAP = 2;
/*     */   public static final int UNWRAP = 3;
/*     */   public static final int INTS = 4;
/*     */   public static final int LONGS = 5;
/*     */   public static final int RAW_RETURN = 6;
/*     */   
/*     */   public MethodType erasedType() {
/*  89 */     return this.erasedType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodType basicType() {
/*  98 */     return this.basicType;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean assertIsBasicType() {
/* 103 */     assert this.erasedType == this.basicType : "erasedType: " + this.erasedType + " != basicType: " + this.basicType;
/*     */     
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public MethodHandle cachedMethodHandle(int paramInt) {
/* 109 */     assert assertIsBasicType();
/* 110 */     SoftReference<MethodHandle> softReference = this.methodHandles[paramInt];
/* 111 */     return (softReference != null) ? softReference.get() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized MethodHandle setCachedMethodHandle(int paramInt, MethodHandle paramMethodHandle) {
/* 116 */     SoftReference<MethodHandle> softReference = this.methodHandles[paramInt];
/* 117 */     if (softReference != null) {
/* 118 */       MethodHandle methodHandle = softReference.get();
/* 119 */       if (methodHandle != null) {
/* 120 */         return methodHandle;
/*     */       }
/*     */     } 
/* 123 */     this.methodHandles[paramInt] = new SoftReference<>(paramMethodHandle);
/* 124 */     return paramMethodHandle;
/*     */   }
/*     */   
/*     */   public LambdaForm cachedLambdaForm(int paramInt) {
/* 128 */     assert assertIsBasicType();
/* 129 */     SoftReference<LambdaForm> softReference = this.lambdaForms[paramInt];
/* 130 */     return (softReference != null) ? softReference.get() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized LambdaForm setCachedLambdaForm(int paramInt, LambdaForm paramLambdaForm) {
/* 135 */     SoftReference<LambdaForm> softReference = this.lambdaForms[paramInt];
/* 136 */     if (softReference != null) {
/* 137 */       LambdaForm lambdaForm = softReference.get();
/* 138 */       if (lambdaForm != null) {
/* 139 */         return lambdaForm;
/*     */       }
/*     */     } 
/* 142 */     this.lambdaForms[paramInt] = new SoftReference<>(paramLambdaForm);
/* 143 */     return paramLambdaForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MethodTypeForm(MethodType paramMethodType) {
/* 153 */     this.erasedType = paramMethodType;
/*     */     
/* 155 */     Class[] arrayOfClass1 = paramMethodType.ptypes();
/* 156 */     int i = arrayOfClass1.length;
/* 157 */     int j = i;
/* 158 */     boolean bool = true;
/* 159 */     int k = 1;
/*     */     
/* 161 */     int[] arrayOfInt1 = null, arrayOfInt2 = null;
/*     */ 
/*     */     
/* 164 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/* 165 */     Class[] arrayOfClass2 = arrayOfClass1;
/* 166 */     Class[] arrayOfClass3 = arrayOfClass2;
/* 167 */     for (byte b5 = 0; b5 < arrayOfClass2.length; b5++) {
/* 168 */       Class<Object> clazz = arrayOfClass2[b5];
/* 169 */       if (clazz != Object.class) {
/* 170 */         b1++;
/* 171 */         Wrapper wrapper = Wrapper.forPrimitiveType(clazz);
/* 172 */         if (wrapper.isDoubleWord()) b2++; 
/* 173 */         if (wrapper.isSubwordOrInt() && clazz != int.class) {
/* 174 */           if (arrayOfClass3 == arrayOfClass2)
/* 175 */             arrayOfClass3 = (Class[])arrayOfClass3.clone(); 
/* 176 */           arrayOfClass3[b5] = int.class;
/*     */         } 
/*     */       } 
/*     */     } 
/* 180 */     j += b2;
/* 181 */     Class<?> clazz1 = paramMethodType.returnType();
/* 182 */     Class<?> clazz2 = clazz1;
/* 183 */     if (clazz1 != Object.class) {
/* 184 */       b3++;
/* 185 */       Wrapper wrapper = Wrapper.forPrimitiveType(clazz1);
/* 186 */       if (wrapper.isDoubleWord()) b4++; 
/* 187 */       if (wrapper.isSubwordOrInt() && clazz1 != int.class) {
/* 188 */         clazz2 = int.class;
/*     */       }
/* 190 */       if (clazz1 == void.class) {
/* 191 */         bool = k = 0;
/*     */       } else {
/* 193 */         k += b4;
/*     */       } 
/* 195 */     }  if (arrayOfClass2 == arrayOfClass3 && clazz2 == clazz1) {
/* 196 */       this.basicType = paramMethodType;
/*     */     } else {
/* 198 */       this.basicType = MethodType.makeImpl(clazz2, arrayOfClass3, true);
/*     */       
/* 200 */       MethodTypeForm methodTypeForm = this.basicType.form();
/* 201 */       assert this != methodTypeForm;
/* 202 */       this.primCounts = methodTypeForm.primCounts;
/* 203 */       this.argCounts = methodTypeForm.argCounts;
/* 204 */       this.argToSlotTable = methodTypeForm.argToSlotTable;
/* 205 */       this.slotToArgTable = methodTypeForm.slotToArgTable;
/* 206 */       this.methodHandles = null;
/* 207 */       this.lambdaForms = null;
/*     */       return;
/*     */     } 
/* 210 */     if (b2 != 0) {
/* 211 */       int m = i + b2;
/* 212 */       arrayOfInt2 = new int[m + 1];
/* 213 */       arrayOfInt1 = new int[1 + i];
/* 214 */       arrayOfInt1[0] = m;
/* 215 */       for (byte b = 0; b < arrayOfClass2.length; b++) {
/* 216 */         Class<?> clazz = arrayOfClass2[b];
/* 217 */         Wrapper wrapper = Wrapper.forBasicType(clazz);
/* 218 */         if (wrapper.isDoubleWord()) m--; 
/* 219 */         m--;
/* 220 */         arrayOfInt2[m] = b + 1;
/* 221 */         arrayOfInt1[1 + b] = m;
/*     */       } 
/* 223 */       assert m == 0;
/* 224 */     } else if (b1 != 0) {
/*     */       
/* 226 */       assert i == j;
/* 227 */       MethodTypeForm methodTypeForm = MethodType.genericMethodType(i).form();
/* 228 */       assert this != methodTypeForm;
/* 229 */       arrayOfInt2 = methodTypeForm.slotToArgTable;
/* 230 */       arrayOfInt1 = methodTypeForm.argToSlotTable;
/*     */     } else {
/* 232 */       int m = i;
/* 233 */       arrayOfInt2 = new int[m + 1];
/* 234 */       arrayOfInt1 = new int[1 + i];
/* 235 */       arrayOfInt1[0] = m;
/* 236 */       for (byte b = 0; b < i; b++) {
/* 237 */         m--;
/* 238 */         arrayOfInt2[m] = b + 1;
/* 239 */         arrayOfInt1[1 + b] = m;
/*     */       } 
/*     */     } 
/* 242 */     this.primCounts = pack(b4, b3, b2, b1);
/* 243 */     this.argCounts = pack(k, bool, j, i);
/* 244 */     this.argToSlotTable = arrayOfInt1;
/* 245 */     this.slotToArgTable = arrayOfInt2;
/*     */     
/* 247 */     if (j >= 256) throw MethodHandleStatics.newIllegalArgumentException("too many arguments");
/*     */ 
/*     */     
/* 250 */     assert this.basicType == paramMethodType;
/* 251 */     this.lambdaForms = (SoftReference<LambdaForm>[])new SoftReference[18];
/* 252 */     this.methodHandles = (SoftReference<MethodHandle>[])new SoftReference[3];
/*     */   }
/*     */   
/*     */   private static long pack(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 256 */     assert ((paramInt1 | paramInt2 | paramInt3 | paramInt4) & 0xFFFF0000) == 0;
/* 257 */     long l1 = (paramInt1 << 16 | paramInt2), l2 = (paramInt3 << 16 | paramInt4);
/* 258 */     return l1 << 32L | l2;
/*     */   }
/*     */   private static char unpack(long paramLong, int paramInt) {
/* 261 */     assert paramInt <= 3;
/* 262 */     return (char)(int)(paramLong >> (3 - paramInt) * 16);
/*     */   }
/*     */   
/*     */   public int parameterCount() {
/* 266 */     return unpack(this.argCounts, 3);
/*     */   }
/*     */   public int parameterSlotCount() {
/* 269 */     return unpack(this.argCounts, 2);
/*     */   }
/*     */   public int returnCount() {
/* 272 */     return unpack(this.argCounts, 1);
/*     */   }
/*     */   public int returnSlotCount() {
/* 275 */     return unpack(this.argCounts, 0);
/*     */   }
/*     */   public int primitiveParameterCount() {
/* 278 */     return unpack(this.primCounts, 3);
/*     */   }
/*     */   public int longPrimitiveParameterCount() {
/* 281 */     return unpack(this.primCounts, 2);
/*     */   }
/*     */   public int primitiveReturnCount() {
/* 284 */     return unpack(this.primCounts, 1);
/*     */   }
/*     */   public int longPrimitiveReturnCount() {
/* 287 */     return unpack(this.primCounts, 0);
/*     */   }
/*     */   public boolean hasPrimitives() {
/* 290 */     return (this.primCounts != 0L);
/*     */   }
/*     */   public boolean hasNonVoidPrimitives() {
/* 293 */     if (this.primCounts == 0L) return false; 
/* 294 */     if (primitiveParameterCount() != 0) return true; 
/* 295 */     return (primitiveReturnCount() != 0 && returnCount() != 0);
/*     */   }
/*     */   public boolean hasLongPrimitives() {
/* 298 */     return ((longPrimitiveParameterCount() | longPrimitiveReturnCount()) != 0);
/*     */   }
/*     */   public int parameterToArgSlot(int paramInt) {
/* 301 */     return this.argToSlotTable[1 + paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int argSlotToParameter(int paramInt) {
/* 307 */     return this.slotToArgTable[paramInt] - 1;
/*     */   }
/*     */   
/*     */   static MethodTypeForm findForm(MethodType paramMethodType) {
/* 311 */     MethodType methodType = canonicalize(paramMethodType, 1, 1);
/* 312 */     if (methodType == null)
/*     */     {
/* 314 */       return new MethodTypeForm(paramMethodType);
/*     */     }
/*     */     
/* 317 */     return methodType.form();
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
/*     */   public static MethodType canonicalize(MethodType paramMethodType, int paramInt1, int paramInt2) {
/* 338 */     Class[] arrayOfClass1 = paramMethodType.ptypes();
/* 339 */     Class[] arrayOfClass2 = canonicalizeAll(arrayOfClass1, paramInt2);
/* 340 */     Class<?> clazz1 = paramMethodType.returnType();
/* 341 */     Class<?> clazz2 = canonicalize(clazz1, paramInt1);
/* 342 */     if (arrayOfClass2 == null && clazz2 == null)
/*     */     {
/* 344 */       return null;
/*     */     }
/*     */     
/* 347 */     if (clazz2 == null) clazz2 = clazz1; 
/* 348 */     if (arrayOfClass2 == null) arrayOfClass2 = arrayOfClass1; 
/* 349 */     return MethodType.makeImpl(clazz2, arrayOfClass2, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?> canonicalize(Class<?> paramClass, int paramInt) {
/* 357 */     if (paramClass != Object.class)
/*     */     {
/* 359 */       if (!paramClass.isPrimitive()) {
/* 360 */         Class<?> clazz; switch (paramInt) {
/*     */           case 3:
/* 362 */             clazz = Wrapper.asPrimitiveType(paramClass);
/* 363 */             if (clazz != paramClass) return clazz; 
/*     */             break;
/*     */           case 1:
/*     */           case 6:
/* 367 */             return Object.class;
/*     */         } 
/* 369 */       } else if (paramClass == void.class) {
/*     */         
/* 371 */         switch (paramInt) {
/*     */           case 6:
/* 373 */             return int.class;
/*     */           case 2:
/* 375 */             return Void.class;
/*     */         } 
/*     */       
/*     */       } else {
/* 379 */         switch (paramInt) {
/*     */           case 2:
/* 381 */             return Wrapper.asWrapperType(paramClass);
/*     */           case 4:
/* 383 */             if (paramClass == int.class || paramClass == long.class)
/* 384 */               return null; 
/* 385 */             if (paramClass == double.class)
/* 386 */               return long.class; 
/* 387 */             return int.class;
/*     */           case 5:
/* 389 */             if (paramClass == long.class)
/* 390 */               return null; 
/* 391 */             return long.class;
/*     */           case 6:
/* 393 */             if (paramClass == int.class || paramClass == long.class || paramClass == float.class || paramClass == double.class)
/*     */             {
/* 395 */               return null;
/*     */             }
/* 397 */             return int.class;
/*     */         } 
/*     */       } 
/*     */     }
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?>[] canonicalizeAll(Class<?>[] paramArrayOfClass, int paramInt) {
/* 408 */     Class[] arrayOfClass = null; int i; byte b;
/* 409 */     for (i = paramArrayOfClass.length, b = 0; b < i; b++) {
/* 410 */       Class<?> clazz = canonicalize(paramArrayOfClass[b], paramInt);
/* 411 */       if (clazz == void.class)
/* 412 */         clazz = null; 
/* 413 */       if (clazz != null) {
/* 414 */         if (arrayOfClass == null)
/* 415 */           arrayOfClass = (Class[])paramArrayOfClass.clone(); 
/* 416 */         arrayOfClass[b] = clazz;
/*     */       } 
/*     */     } 
/* 419 */     return arrayOfClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 424 */     return "Form" + this.erasedType;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodTypeForm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */