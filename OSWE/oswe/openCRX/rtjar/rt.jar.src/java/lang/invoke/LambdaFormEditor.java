/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.LambdaFormBuffer;
/*     */ import java.lang.invoke.LambdaFormEditor;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleImpl;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ class LambdaFormEditor
/*     */ {
/*     */   final LambdaForm lambdaForm;
/*     */   private static final int MIN_CACHE_ARRAY_SIZE = 4;
/*     */   private static final int MAX_CACHE_ARRAY_SIZE = 16;
/*     */   
/*     */   private LambdaFormEditor(LambdaForm paramLambdaForm) {
/*  47 */     this.lambdaForm = paramLambdaForm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LambdaFormEditor lambdaFormEditor(LambdaForm paramLambdaForm) {
/*  57 */     return new LambdaFormEditor(paramLambdaForm.uncustomize());
/*     */   }
/*     */   
/*     */   private static final class Transform
/*     */     extends SoftReference<LambdaForm> {
/*     */     final long packedBytes;
/*     */     final byte[] fullBytes;
/*     */     private static final boolean STRESS_TEST = false;
/*     */     private static final int PACKED_BYTE_SIZE = 4;
/*     */     private static final int PACKED_BYTE_MASK = 15;
/*     */     private static final int PACKED_BYTE_MAX_LENGTH = 16;
/*     */     
/*     */     private enum Kind {
/*  70 */       NO_KIND,
/*  71 */       BIND_ARG, ADD_ARG, DUP_ARG,
/*  72 */       SPREAD_ARGS,
/*  73 */       FILTER_ARG, FILTER_RETURN, FILTER_RETURN_TO_ZERO,
/*  74 */       COLLECT_ARGS, COLLECT_ARGS_TO_VOID, COLLECT_ARGS_TO_ARRAY,
/*  75 */       FOLD_ARGS, FOLD_ARGS_TO_VOID,
/*  76 */       PERMUTE_ARGS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static long packedBytes(byte[] param1ArrayOfbyte) {
/*  87 */       if (param1ArrayOfbyte.length > 16) return 0L; 
/*  88 */       long l = 0L;
/*  89 */       int i = 0;
/*  90 */       for (byte b = 0; b < param1ArrayOfbyte.length; b++) {
/*  91 */         int j = param1ArrayOfbyte[b] & 0xFF;
/*  92 */         i |= j;
/*  93 */         l |= j << b * 4;
/*     */       } 
/*  95 */       if (!inRange(i))
/*  96 */         return 0L; 
/*  97 */       return l;
/*     */     }
/*     */     private static long packedBytes(int param1Int1, int param1Int2) {
/* 100 */       assert inRange(param1Int1 | param1Int2);
/* 101 */       return (param1Int1 << 0 | param1Int2 << 4);
/*     */     }
/*     */     
/*     */     private static long packedBytes(int param1Int1, int param1Int2, int param1Int3) {
/* 105 */       assert inRange(param1Int1 | param1Int2 | param1Int3);
/* 106 */       return (param1Int1 << 0 | param1Int2 << 4 | param1Int3 << 8);
/*     */     }
/*     */ 
/*     */     
/*     */     private static long packedBytes(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 111 */       assert inRange(param1Int1 | param1Int2 | param1Int3 | param1Int4);
/* 112 */       return (param1Int1 << 0 | param1Int2 << 4 | param1Int3 << 8 | param1Int4 << 12);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean inRange(int param1Int) {
/* 118 */       assert (param1Int & 0xFF) == param1Int;
/* 119 */       return ((param1Int & 0xFFFFFFF0) == 0);
/*     */     }
/*     */     private static byte[] fullBytes(int... param1VarArgs) {
/* 122 */       byte[] arrayOfByte = new byte[param1VarArgs.length];
/* 123 */       byte b = 0;
/* 124 */       for (int i : param1VarArgs) {
/* 125 */         arrayOfByte[b++] = bval(i);
/*     */       }
/* 127 */       assert packedBytes(arrayOfByte) == 0L;
/* 128 */       return arrayOfByte;
/*     */     }
/*     */     
/*     */     private byte byteAt(int param1Int) {
/* 132 */       long l = this.packedBytes;
/* 133 */       if (l == 0L) {
/* 134 */         if (param1Int >= this.fullBytes.length) return 0; 
/* 135 */         return this.fullBytes[param1Int];
/*     */       } 
/* 137 */       assert this.fullBytes == null;
/* 138 */       if (param1Int > 16) return 0; 
/* 139 */       int i = param1Int * 4;
/* 140 */       return (byte)(int)(l >>> i & 0xFL);
/*     */     }
/*     */     Kind kind() {
/* 143 */       return Kind.values()[byteAt(0)];
/*     */     }
/*     */     private Transform(long param1Long, byte[] param1ArrayOfbyte, LambdaForm param1LambdaForm) {
/* 146 */       super(param1LambdaForm);
/* 147 */       this.packedBytes = param1Long;
/* 148 */       this.fullBytes = param1ArrayOfbyte;
/*     */     }
/*     */     private Transform(long param1Long) {
/* 151 */       this(param1Long, null, null);
/* 152 */       assert param1Long != 0L;
/*     */     }
/*     */     private Transform(byte[] param1ArrayOfbyte) {
/* 155 */       this(0L, param1ArrayOfbyte, null);
/*     */     }
/*     */     
/*     */     private static byte bval(int param1Int) {
/* 159 */       assert (param1Int & 0xFF) == param1Int;
/* 160 */       return (byte)param1Int;
/*     */     }
/*     */     private static byte bval(Kind param1Kind) {
/* 163 */       return bval(param1Kind.ordinal());
/*     */     }
/*     */     static Transform of(Kind param1Kind, int param1Int) {
/* 166 */       byte b = bval(param1Kind);
/* 167 */       if (inRange(b | param1Int)) {
/* 168 */         return new Transform(packedBytes(b, param1Int));
/*     */       }
/* 170 */       return new Transform(fullBytes(new int[] { b, param1Int }));
/*     */     }
/*     */     static Transform of(Kind param1Kind, int param1Int1, int param1Int2) {
/* 173 */       byte b = (byte)param1Kind.ordinal();
/* 174 */       if (inRange(b | param1Int1 | param1Int2)) {
/* 175 */         return new Transform(packedBytes(b, param1Int1, param1Int2));
/*     */       }
/* 177 */       return new Transform(fullBytes(new int[] { b, param1Int1, param1Int2 }));
/*     */     }
/*     */     static Transform of(Kind param1Kind, int param1Int1, int param1Int2, int param1Int3) {
/* 180 */       byte b = (byte)param1Kind.ordinal();
/* 181 */       if (inRange(b | param1Int1 | param1Int2 | param1Int3)) {
/* 182 */         return new Transform(packedBytes(b, param1Int1, param1Int2, param1Int3));
/*     */       }
/* 184 */       return new Transform(fullBytes(new int[] { b, param1Int1, param1Int2, param1Int3 }));
/*     */     }
/* 186 */     private static final byte[] NO_BYTES = new byte[0];
/*     */     static Transform of(Kind param1Kind, int... param1VarArgs) {
/* 188 */       return ofBothArrays(param1Kind, param1VarArgs, NO_BYTES);
/*     */     }
/*     */     static Transform of(Kind param1Kind, int param1Int, byte[] param1ArrayOfbyte) {
/* 191 */       return ofBothArrays(param1Kind, new int[] { param1Int }, param1ArrayOfbyte);
/*     */     }
/*     */     static Transform of(Kind param1Kind, int param1Int1, int param1Int2, byte[] param1ArrayOfbyte) {
/* 194 */       return ofBothArrays(param1Kind, new int[] { param1Int1, param1Int2 }, param1ArrayOfbyte);
/*     */     }
/*     */     private static Transform ofBothArrays(Kind param1Kind, int[] param1ArrayOfint, byte[] param1ArrayOfbyte) {
/* 197 */       byte[] arrayOfByte = new byte[1 + param1ArrayOfint.length + param1ArrayOfbyte.length];
/* 198 */       byte b = 0;
/* 199 */       arrayOfByte[b++] = bval(param1Kind);
/* 200 */       for (int i : param1ArrayOfint) {
/* 201 */         arrayOfByte[b++] = bval(i);
/*     */       }
/* 203 */       for (byte b1 : param1ArrayOfbyte) {
/* 204 */         arrayOfByte[b++] = b1;
/*     */       }
/* 206 */       long l = packedBytes(arrayOfByte);
/* 207 */       if (l != 0L) {
/* 208 */         return new Transform(l);
/*     */       }
/* 210 */       return new Transform(arrayOfByte);
/*     */     }
/*     */     
/*     */     Transform withResult(LambdaForm param1LambdaForm) {
/* 214 */       return new Transform(this.packedBytes, this.fullBytes, param1LambdaForm);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 219 */       return (param1Object instanceof Transform && equals((Transform)param1Object));
/*     */     }
/*     */     public boolean equals(Transform param1Transform) {
/* 222 */       return (this.packedBytes == param1Transform.packedBytes && Arrays.equals(this.fullBytes, param1Transform.fullBytes));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 226 */       if (this.packedBytes != 0L) {
/* 227 */         assert this.fullBytes == null;
/* 228 */         return Long.hashCode(this.packedBytes);
/*     */       } 
/* 230 */       return Arrays.hashCode(this.fullBytes);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 234 */       StringBuilder stringBuilder = new StringBuilder();
/* 235 */       long l = this.packedBytes;
/* 236 */       if (l != 0L) {
/* 237 */         stringBuilder.append("(");
/* 238 */         while (l != 0L) {
/* 239 */           stringBuilder.append(l & 0xFL);
/* 240 */           l >>>= 4L;
/* 241 */           if (l != 0L) stringBuilder.append(","); 
/*     */         } 
/* 243 */         stringBuilder.append(")");
/*     */       } 
/* 245 */       if (this.fullBytes != null) {
/* 246 */         stringBuilder.append("unpacked");
/* 247 */         stringBuilder.append(Arrays.toString(this.fullBytes));
/*     */       } 
/* 249 */       LambdaForm lambdaForm = get();
/* 250 */       if (lambdaForm != null) {
/* 251 */         stringBuilder.append(" result=");
/* 252 */         stringBuilder.append(lambdaForm);
/*     */       } 
/* 254 */       return stringBuilder.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private LambdaForm getInCache(Transform paramTransform) {
/* 260 */     assert paramTransform.get() == null;
/*     */     
/* 262 */     Object object = this.lambdaForm.transformCache;
/* 263 */     Transform transform = null;
/* 264 */     if (object instanceof ConcurrentHashMap)
/*     */     
/* 266 */     { ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)object;
/* 267 */       transform = (Transform)concurrentHashMap.get(paramTransform); }
/* 268 */     else { if (object == null)
/* 269 */         return null; 
/* 270 */       if (object instanceof Transform) {
/*     */         
/* 272 */         Transform transform1 = (Transform)object;
/* 273 */         if (transform1.equals(paramTransform)) transform = transform1; 
/*     */       } else {
/* 275 */         Transform[] arrayOfTransform = (Transform[])object;
/* 276 */         for (byte b = 0; b < arrayOfTransform.length; b++) {
/* 277 */           Transform transform1 = arrayOfTransform[b];
/* 278 */           if (transform1 == null)
/* 279 */             break;  if (transform1.equals(paramTransform)) { transform = transform1; break; } 
/*     */         } 
/*     */       }  }
/* 282 */      assert transform == null || paramTransform.equals(transform);
/* 283 */     return (transform != null) ? transform.get() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private enum Kind
/*     */   {
/*     */     NO_KIND, BIND_ARG, ADD_ARG, DUP_ARG, SPREAD_ARGS, FILTER_ARG, FILTER_RETURN, FILTER_RETURN_TO_ZERO, COLLECT_ARGS, COLLECT_ARGS_TO_VOID, COLLECT_ARGS_TO_ARRAY, FOLD_ARGS, FOLD_ARGS_TO_VOID, PERMUTE_ARGS;
/*     */   }
/*     */   
/*     */   private LambdaForm putInCache(Transform paramTransform, LambdaForm paramLambdaForm) {
/* 293 */     paramTransform = paramTransform.withResult(paramLambdaForm);
/* 294 */     for (byte b = 0;; b++) {
/* 295 */       Object object = this.lambdaForm.transformCache;
/* 296 */       if (object instanceof ConcurrentHashMap) {
/*     */         
/* 298 */         ConcurrentHashMap<Transform, Transform> concurrentHashMap = (ConcurrentHashMap)object;
/* 299 */         Transform transform = concurrentHashMap.putIfAbsent(paramTransform, paramTransform);
/* 300 */         if (transform == null) return paramLambdaForm; 
/* 301 */         LambdaForm lambdaForm = transform.get();
/* 302 */         if (lambdaForm != null) {
/* 303 */           return lambdaForm;
/*     */         }
/* 305 */         if (concurrentHashMap.replace(paramTransform, transform, paramTransform)) {
/* 306 */           return paramLambdaForm;
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 312 */         assert !b;
/* 313 */         synchronized (this.lambdaForm) {
/* 314 */           object = this.lambdaForm.transformCache;
/* 315 */           if (object instanceof ConcurrentHashMap) {  }
/*     */           else
/* 317 */           { Transform[] arrayOfTransform; if (object == null) {
/* 318 */               this.lambdaForm.transformCache = paramTransform;
/* 319 */               return paramLambdaForm;
/*     */             } 
/*     */             
/* 322 */             if (object instanceof Transform) {
/* 323 */               Transform transform = (Transform)object;
/* 324 */               if (transform.equals(paramTransform)) {
/* 325 */                 LambdaForm lambdaForm = transform.get();
/* 326 */                 if (lambdaForm == null) {
/* 327 */                   this.lambdaForm.transformCache = paramTransform;
/* 328 */                   return paramLambdaForm;
/*     */                 } 
/* 330 */                 return lambdaForm;
/*     */               } 
/* 332 */               if (transform.get() == null) {
/* 333 */                 this.lambdaForm.transformCache = paramTransform;
/* 334 */                 return paramLambdaForm;
/*     */               } 
/*     */               
/* 337 */               arrayOfTransform = new Transform[4];
/* 338 */               arrayOfTransform[0] = transform;
/* 339 */               this.lambdaForm.transformCache = arrayOfTransform;
/*     */             } else {
/*     */               
/* 342 */               arrayOfTransform = (Transform[])object;
/*     */             } 
/* 344 */             int i = arrayOfTransform.length;
/* 345 */             byte b1 = -1;
/*     */             byte b2;
/* 347 */             for (b2 = 0; b2 < i; b2++) {
/* 348 */               Transform transform = arrayOfTransform[b2];
/* 349 */               if (transform == null) {
/*     */                 break;
/*     */               }
/* 352 */               if (transform.equals(paramTransform)) {
/* 353 */                 LambdaForm lambdaForm = transform.get();
/* 354 */                 if (lambdaForm == null) {
/* 355 */                   arrayOfTransform[b2] = paramTransform;
/* 356 */                   return paramLambdaForm;
/*     */                 } 
/* 358 */                 return lambdaForm;
/*     */               } 
/* 360 */               if (b1 < 0 && transform.get() == null) {
/* 361 */                 b1 = b2;
/*     */               }
/*     */             } 
/* 364 */             if (b2 >= i && b1 < 0)
/*     */             {
/* 366 */               if (i < 16) {
/* 367 */                 i = Math.min(i * 2, 16);
/* 368 */                 arrayOfTransform = Arrays.<Transform>copyOf(arrayOfTransform, i);
/* 369 */                 this.lambdaForm.transformCache = arrayOfTransform;
/*     */               } else {
/* 371 */                 ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(32);
/* 372 */                 for (Transform transform : arrayOfTransform) {
/* 373 */                   concurrentHashMap.put(transform, transform);
/*     */                 }
/* 375 */                 this.lambdaForm.transformCache = concurrentHashMap;
/*     */                 b++;
/*     */               } 
/*     */             }
/* 379 */             boolean bool = (b1 >= 0) ? b1 : b2;
/* 380 */             arrayOfTransform[bool] = paramTransform;
/* 381 */             return paramLambdaForm; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private LambdaFormBuffer buffer() {
/* 387 */     return new LambdaFormBuffer(this.lambdaForm);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BoundMethodHandle.SpeciesData oldSpeciesData() {
/* 393 */     return BoundMethodHandle.speciesData(this.lambdaForm);
/*     */   }
/*     */   private BoundMethodHandle.SpeciesData newSpeciesData(LambdaForm.BasicType paramBasicType) {
/* 396 */     return oldSpeciesData().extendWith(paramBasicType);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentL(BoundMethodHandle paramBoundMethodHandle, int paramInt, Object paramObject) {
/* 400 */     assert paramBoundMethodHandle.speciesData() == oldSpeciesData();
/* 401 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.L_TYPE;
/* 402 */     MethodType methodType = bindArgumentType(paramBoundMethodHandle, paramInt, basicType);
/* 403 */     LambdaForm lambdaForm = bindArgumentForm(1 + paramInt);
/* 404 */     return paramBoundMethodHandle.copyWithExtendL(methodType, lambdaForm, paramObject);
/*     */   }
/*     */   BoundMethodHandle bindArgumentI(BoundMethodHandle paramBoundMethodHandle, int paramInt1, int paramInt2) {
/* 407 */     assert paramBoundMethodHandle.speciesData() == oldSpeciesData();
/* 408 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.I_TYPE;
/* 409 */     MethodType methodType = bindArgumentType(paramBoundMethodHandle, paramInt1, basicType);
/* 410 */     LambdaForm lambdaForm = bindArgumentForm(1 + paramInt1);
/* 411 */     return paramBoundMethodHandle.copyWithExtendI(methodType, lambdaForm, paramInt2);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentJ(BoundMethodHandle paramBoundMethodHandle, int paramInt, long paramLong) {
/* 415 */     assert paramBoundMethodHandle.speciesData() == oldSpeciesData();
/* 416 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.J_TYPE;
/* 417 */     MethodType methodType = bindArgumentType(paramBoundMethodHandle, paramInt, basicType);
/* 418 */     LambdaForm lambdaForm = bindArgumentForm(1 + paramInt);
/* 419 */     return paramBoundMethodHandle.copyWithExtendJ(methodType, lambdaForm, paramLong);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentF(BoundMethodHandle paramBoundMethodHandle, int paramInt, float paramFloat) {
/* 423 */     assert paramBoundMethodHandle.speciesData() == oldSpeciesData();
/* 424 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.F_TYPE;
/* 425 */     MethodType methodType = bindArgumentType(paramBoundMethodHandle, paramInt, basicType);
/* 426 */     LambdaForm lambdaForm = bindArgumentForm(1 + paramInt);
/* 427 */     return paramBoundMethodHandle.copyWithExtendF(methodType, lambdaForm, paramFloat);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentD(BoundMethodHandle paramBoundMethodHandle, int paramInt, double paramDouble) {
/* 431 */     assert paramBoundMethodHandle.speciesData() == oldSpeciesData();
/* 432 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.D_TYPE;
/* 433 */     MethodType methodType = bindArgumentType(paramBoundMethodHandle, paramInt, basicType);
/* 434 */     LambdaForm lambdaForm = bindArgumentForm(1 + paramInt);
/* 435 */     return paramBoundMethodHandle.copyWithExtendD(methodType, lambdaForm, paramDouble);
/*     */   }
/*     */   
/*     */   private MethodType bindArgumentType(BoundMethodHandle paramBoundMethodHandle, int paramInt, LambdaForm.BasicType paramBasicType) {
/* 439 */     assert paramBoundMethodHandle.form.uncustomize() == this.lambdaForm;
/* 440 */     assert (paramBoundMethodHandle.form.names[1 + paramInt]).type == paramBasicType;
/* 441 */     assert LambdaForm.BasicType.basicType(paramBoundMethodHandle.type().parameterType(paramInt)) == paramBasicType;
/* 442 */     return paramBoundMethodHandle.type().dropParameterTypes(paramInt, paramInt + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LambdaForm bindArgumentForm(int paramInt) {
/* 449 */     Transform transform = Transform.of(Transform.Kind.BIND_ARG, paramInt);
/* 450 */     LambdaForm lambdaForm = getInCache(transform);
/* 451 */     if (lambdaForm != null) {
/* 452 */       assert lambdaForm.parameterConstraint(0) == newSpeciesData(this.lambdaForm.parameterType(paramInt));
/* 453 */       return lambdaForm;
/*     */     } 
/* 455 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 456 */     lambdaFormBuffer.startEdit();
/*     */     
/* 458 */     BoundMethodHandle.SpeciesData speciesData1 = oldSpeciesData();
/* 459 */     BoundMethodHandle.SpeciesData speciesData2 = newSpeciesData(this.lambdaForm.parameterType(paramInt));
/* 460 */     LambdaForm.Name name = this.lambdaForm.parameter(0);
/*     */     
/* 462 */     LambdaForm.NamedFunction namedFunction = speciesData2.getterFunction(speciesData1.fieldCount());
/*     */     
/* 464 */     if (paramInt != 0) {
/*     */ 
/*     */       
/* 467 */       lambdaFormBuffer.replaceFunctions(speciesData1.getterFunctions(), speciesData2.getterFunctions(), new Object[] { name });
/* 468 */       LambdaForm.Name name1 = name.withConstraint(speciesData2);
/* 469 */       lambdaFormBuffer.renameParameter(0, name1);
/* 470 */       lambdaFormBuffer.replaceParameterByNewExpression(paramInt, new LambdaForm.Name(namedFunction, new Object[] { name1 }));
/*     */     } else {
/*     */       
/* 473 */       assert speciesData1 == BoundMethodHandle.SpeciesData.EMPTY;
/* 474 */       LambdaForm.Name name1 = (new LambdaForm.Name(LambdaForm.BasicType.L_TYPE)).withConstraint(speciesData2);
/* 475 */       lambdaFormBuffer.replaceParameterByNewExpression(0, new LambdaForm.Name(namedFunction, new Object[] { name1 }));
/* 476 */       lambdaFormBuffer.insertParameter(0, name1);
/*     */     } 
/*     */     
/* 479 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 480 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm addArgumentForm(int paramInt, LambdaForm.BasicType paramBasicType) {
/* 484 */     Transform transform = Transform.of(Transform.Kind.ADD_ARG, paramInt, paramBasicType.ordinal());
/* 485 */     LambdaForm lambdaForm = getInCache(transform);
/* 486 */     if (lambdaForm != null) {
/* 487 */       assert lambdaForm.arity == this.lambdaForm.arity + 1;
/* 488 */       assert lambdaForm.parameterType(paramInt) == paramBasicType;
/* 489 */       return lambdaForm;
/*     */     } 
/* 491 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 492 */     lambdaFormBuffer.startEdit();
/*     */     
/* 494 */     lambdaFormBuffer.insertParameter(paramInt, new LambdaForm.Name(paramBasicType));
/*     */     
/* 496 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 497 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm dupArgumentForm(int paramInt1, int paramInt2) {
/* 501 */     Transform transform = Transform.of(Transform.Kind.DUP_ARG, paramInt1, paramInt2);
/* 502 */     LambdaForm lambdaForm = getInCache(transform);
/* 503 */     if (lambdaForm != null) {
/* 504 */       assert lambdaForm.arity == this.lambdaForm.arity - 1;
/* 505 */       return lambdaForm;
/*     */     } 
/* 507 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 508 */     lambdaFormBuffer.startEdit();
/*     */     
/* 510 */     assert (this.lambdaForm.parameter(paramInt1)).constraint == null;
/* 511 */     assert (this.lambdaForm.parameter(paramInt2)).constraint == null;
/* 512 */     lambdaFormBuffer.replaceParameterByCopy(paramInt2, paramInt1);
/*     */     
/* 514 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 515 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm spreadArgumentsForm(int paramInt1, Class<?> paramClass, int paramInt2) {
/* 519 */     Class<?> clazz1 = paramClass.getComponentType();
/* 520 */     Class<?> clazz2 = paramClass;
/* 521 */     if (!clazz1.isPrimitive())
/* 522 */       clazz2 = Object[].class; 
/* 523 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.basicType(clazz1);
/* 524 */     int i = basicType.ordinal();
/* 525 */     if (basicType.basicTypeClass() != clazz1 && 
/* 526 */       clazz1.isPrimitive()) {
/* 527 */       i = LambdaForm.BasicType.TYPE_LIMIT + Wrapper.forPrimitiveType(clazz1).ordinal();
/*     */     }
/*     */     
/* 530 */     Transform transform = Transform.of(Transform.Kind.SPREAD_ARGS, paramInt1, i, paramInt2);
/* 531 */     LambdaForm lambdaForm = getInCache(transform);
/* 532 */     if (lambdaForm != null) {
/* 533 */       assert lambdaForm.arity == this.lambdaForm.arity - paramInt2 + 1;
/* 534 */       return lambdaForm;
/*     */     } 
/* 536 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 537 */     lambdaFormBuffer.startEdit();
/*     */     
/* 539 */     assert paramInt1 <= 255;
/* 540 */     assert paramInt1 + paramInt2 <= this.lambdaForm.arity;
/* 541 */     assert paramInt1 > 0;
/*     */     
/* 543 */     LambdaForm.Name name1 = new LambdaForm.Name(LambdaForm.BasicType.L_TYPE);
/* 544 */     LambdaForm.Name name2 = new LambdaForm.Name(MethodHandleImpl.Lazy.NF_checkSpreadArgument, new Object[] { name1, Integer.valueOf(paramInt2) });
/*     */ 
/*     */     
/* 547 */     int j = this.lambdaForm.arity();
/* 548 */     lambdaFormBuffer.insertExpression(j++, name2);
/*     */     
/* 550 */     MethodHandle methodHandle = MethodHandles.arrayElementGetter(clazz2);
/* 551 */     for (byte b = 0; b < paramInt2; b++) {
/* 552 */       LambdaForm.Name name = new LambdaForm.Name(methodHandle, new Object[] { name1, Integer.valueOf(b) });
/* 553 */       lambdaFormBuffer.insertExpression(j + b, name);
/* 554 */       lambdaFormBuffer.replaceParameterByCopy(paramInt1 + b, j + b);
/*     */     } 
/* 556 */     lambdaFormBuffer.insertParameter(paramInt1, name1);
/*     */     
/* 558 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 559 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm collectArgumentsForm(int paramInt, MethodType paramMethodType) {
/* 563 */     int i = paramMethodType.parameterCount();
/* 564 */     boolean bool = (paramMethodType.returnType() == void.class) ? true : false;
/* 565 */     if (i == 1 && !bool) {
/* 566 */       return filterArgumentForm(paramInt, LambdaForm.BasicType.basicType(paramMethodType.parameterType(0)));
/*     */     }
/* 568 */     LambdaForm.BasicType[] arrayOfBasicType = LambdaForm.BasicType.basicTypes(paramMethodType.parameterList());
/* 569 */     Transform.Kind kind = bool ? Transform.Kind.COLLECT_ARGS_TO_VOID : Transform.Kind.COLLECT_ARGS;
/*     */ 
/*     */     
/* 572 */     if (bool && i == 0) paramInt = 1; 
/* 573 */     Transform transform = Transform.of(kind, paramInt, i, LambdaForm.BasicType.basicTypesOrd(arrayOfBasicType));
/* 574 */     LambdaForm lambdaForm = getInCache(transform);
/* 575 */     if (lambdaForm != null) {
/* 576 */       assert lambdaForm.arity == this.lambdaForm.arity - (bool ? 0 : 1) + i;
/* 577 */       return lambdaForm;
/*     */     } 
/* 579 */     lambdaForm = makeArgumentCombinationForm(paramInt, paramMethodType, false, bool);
/* 580 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm collectArgumentArrayForm(int paramInt, MethodHandle paramMethodHandle) {
/* 584 */     MethodType methodType = paramMethodHandle.type();
/* 585 */     int i = methodType.parameterCount();
/* 586 */     assert paramMethodHandle.intrinsicName() == MethodHandleImpl.Intrinsic.NEW_ARRAY;
/* 587 */     Class<?> clazz1 = methodType.returnType();
/* 588 */     Class<?> clazz2 = clazz1.getComponentType();
/* 589 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.basicType(clazz2);
/* 590 */     int j = basicType.ordinal();
/* 591 */     if (basicType.basicTypeClass() != clazz2) {
/*     */       
/* 593 */       if (!clazz2.isPrimitive())
/* 594 */         return null; 
/* 595 */       j = LambdaForm.BasicType.TYPE_LIMIT + Wrapper.forPrimitiveType(clazz2).ordinal();
/*     */     } 
/* 597 */     assert methodType.parameterList().equals(Collections.nCopies(i, clazz2));
/* 598 */     Transform.Kind kind = Transform.Kind.COLLECT_ARGS_TO_ARRAY;
/* 599 */     Transform transform = Transform.of(kind, paramInt, i, j);
/* 600 */     LambdaForm lambdaForm = getInCache(transform);
/* 601 */     if (lambdaForm != null) {
/* 602 */       assert lambdaForm.arity == this.lambdaForm.arity - 1 + i;
/* 603 */       return lambdaForm;
/*     */     } 
/* 605 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 606 */     lambdaFormBuffer.startEdit();
/*     */     
/* 608 */     assert paramInt + 1 <= this.lambdaForm.arity;
/* 609 */     assert paramInt > 0;
/*     */     
/* 611 */     LambdaForm.Name[] arrayOfName = new LambdaForm.Name[i];
/* 612 */     for (byte b = 0; b < i; b++) {
/* 613 */       arrayOfName[b] = new LambdaForm.Name(paramInt + b, basicType);
/*     */     }
/* 615 */     LambdaForm.Name name = new LambdaForm.Name(paramMethodHandle, (Object[])arrayOfName);
/*     */ 
/*     */     
/* 618 */     int k = this.lambdaForm.arity();
/* 619 */     lambdaFormBuffer.insertExpression(k, name);
/*     */ 
/*     */     
/* 622 */     int m = paramInt + 1;
/* 623 */     for (LambdaForm.Name name1 : arrayOfName) {
/* 624 */       lambdaFormBuffer.insertParameter(m++, name1);
/*     */     }
/* 626 */     assert lambdaFormBuffer.lastIndexOf(name) == k + arrayOfName.length;
/* 627 */     lambdaFormBuffer.replaceParameterByCopy(paramInt, k + arrayOfName.length);
/*     */     
/* 629 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 630 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm filterArgumentForm(int paramInt, LambdaForm.BasicType paramBasicType) {
/* 634 */     Transform transform = Transform.of(Transform.Kind.FILTER_ARG, paramInt, paramBasicType.ordinal());
/* 635 */     LambdaForm lambdaForm = getInCache(transform);
/* 636 */     if (lambdaForm != null) {
/* 637 */       assert lambdaForm.arity == this.lambdaForm.arity;
/* 638 */       assert lambdaForm.parameterType(paramInt) == paramBasicType;
/* 639 */       return lambdaForm;
/*     */     } 
/*     */     
/* 642 */     LambdaForm.BasicType basicType = this.lambdaForm.parameterType(paramInt);
/* 643 */     MethodType methodType = MethodType.methodType(basicType.basicTypeClass(), paramBasicType
/* 644 */         .basicTypeClass());
/* 645 */     lambdaForm = makeArgumentCombinationForm(paramInt, methodType, false, false);
/* 646 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */ 
/*     */   
/*     */   private LambdaForm makeArgumentCombinationForm(int paramInt, MethodType paramMethodType, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     LambdaForm.Name[] arrayOfName;
/* 652 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 653 */     lambdaFormBuffer.startEdit();
/* 654 */     int i = paramMethodType.parameterCount();
/* 655 */     byte b = paramBoolean2 ? 0 : 1;
/*     */     
/* 657 */     assert paramInt <= 255;
/* 658 */     assert paramInt + b + (paramBoolean1 ? i : 0) <= this.lambdaForm.arity;
/* 659 */     assert paramInt > 0;
/* 660 */     assert paramMethodType == paramMethodType.basicType();
/* 661 */     assert paramMethodType.returnType() != void.class || paramBoolean2;
/*     */     
/* 663 */     BoundMethodHandle.SpeciesData speciesData1 = oldSpeciesData();
/* 664 */     BoundMethodHandle.SpeciesData speciesData2 = newSpeciesData(LambdaForm.BasicType.L_TYPE);
/*     */ 
/*     */ 
/*     */     
/* 668 */     LambdaForm.Name name1 = this.lambdaForm.parameter(0);
/* 669 */     lambdaFormBuffer.replaceFunctions(speciesData1.getterFunctions(), speciesData2.getterFunctions(), new Object[] { name1 });
/* 670 */     LambdaForm.Name name2 = name1.withConstraint(speciesData2);
/* 671 */     lambdaFormBuffer.renameParameter(0, name2);
/*     */     
/* 673 */     LambdaForm.Name name3 = new LambdaForm.Name(speciesData2.getterFunction(speciesData1.fieldCount()), new Object[] { name2 });
/* 674 */     Object[] arrayOfObject = new Object[1 + i];
/* 675 */     arrayOfObject[0] = name3;
/*     */     
/* 677 */     if (paramBoolean1) {
/* 678 */       arrayOfName = new LambdaForm.Name[0];
/* 679 */       System.arraycopy(this.lambdaForm.names, paramInt + b, arrayOfObject, 1, i);
/*     */     } else {
/*     */       
/* 682 */       arrayOfName = new LambdaForm.Name[i];
/* 683 */       LambdaForm.BasicType[] arrayOfBasicType = LambdaForm.BasicType.basicTypes(paramMethodType.parameterList());
/* 684 */       for (byte b1 = 0; b1 < arrayOfBasicType.length; b1++) {
/* 685 */         arrayOfName[b1] = new LambdaForm.Name(paramInt + b1, arrayOfBasicType[b1]);
/*     */       }
/* 687 */       System.arraycopy(arrayOfName, 0, arrayOfObject, 1, i);
/*     */     } 
/*     */     
/* 690 */     LambdaForm.Name name4 = new LambdaForm.Name(paramMethodType, arrayOfObject);
/*     */ 
/*     */     
/* 693 */     int j = this.lambdaForm.arity();
/* 694 */     lambdaFormBuffer.insertExpression(j + 0, name3);
/* 695 */     lambdaFormBuffer.insertExpression(j + 1, name4);
/*     */ 
/*     */     
/* 698 */     int k = paramInt + b;
/* 699 */     for (LambdaForm.Name name : arrayOfName) {
/* 700 */       lambdaFormBuffer.insertParameter(k++, name);
/*     */     }
/* 702 */     assert lambdaFormBuffer.lastIndexOf(name4) == j + 1 + arrayOfName.length;
/* 703 */     if (!paramBoolean2) {
/* 704 */       lambdaFormBuffer.replaceParameterByCopy(paramInt, j + 1 + arrayOfName.length);
/*     */     }
/*     */     
/* 707 */     return lambdaFormBuffer.endEdit();
/*     */   }
/*     */   LambdaForm filterReturnForm(LambdaForm.BasicType paramBasicType, boolean paramBoolean) {
/*     */     LambdaForm.Name name;
/* 711 */     Transform.Kind kind = paramBoolean ? Transform.Kind.FILTER_RETURN_TO_ZERO : Transform.Kind.FILTER_RETURN;
/* 712 */     Transform transform = Transform.of(kind, paramBasicType.ordinal());
/* 713 */     LambdaForm lambdaForm = getInCache(transform);
/* 714 */     if (lambdaForm != null) {
/* 715 */       assert lambdaForm.arity == this.lambdaForm.arity;
/* 716 */       assert lambdaForm.returnType() == paramBasicType;
/* 717 */       return lambdaForm;
/*     */     } 
/* 719 */     LambdaFormBuffer lambdaFormBuffer = buffer();
/* 720 */     lambdaFormBuffer.startEdit();
/*     */     
/* 722 */     int i = this.lambdaForm.names.length;
/*     */     
/* 724 */     if (paramBoolean)
/*     */     
/* 726 */     { if (paramBasicType == LambdaForm.BasicType.V_TYPE) {
/* 727 */         name = null;
/*     */       } else {
/* 729 */         name = new LambdaForm.Name(LambdaForm.constantZero(paramBasicType), new Object[0]);
/*     */       }  }
/* 731 */     else { BoundMethodHandle.SpeciesData speciesData1 = oldSpeciesData();
/* 732 */       BoundMethodHandle.SpeciesData speciesData2 = newSpeciesData(LambdaForm.BasicType.L_TYPE);
/*     */ 
/*     */ 
/*     */       
/* 736 */       LambdaForm.Name name1 = this.lambdaForm.parameter(0);
/* 737 */       lambdaFormBuffer.replaceFunctions(speciesData1.getterFunctions(), speciesData2.getterFunctions(), new Object[] { name1 });
/* 738 */       LambdaForm.Name name2 = name1.withConstraint(speciesData2);
/* 739 */       lambdaFormBuffer.renameParameter(0, name2);
/*     */       
/* 741 */       LambdaForm.Name name3 = new LambdaForm.Name(speciesData2.getterFunction(speciesData1.fieldCount()), new Object[] { name2 });
/* 742 */       lambdaFormBuffer.insertExpression(i++, name3);
/* 743 */       LambdaForm.BasicType basicType = this.lambdaForm.returnType();
/* 744 */       if (basicType == LambdaForm.BasicType.V_TYPE) {
/* 745 */         MethodType methodType = MethodType.methodType(paramBasicType.basicTypeClass());
/* 746 */         name = new LambdaForm.Name(methodType, new Object[] { name3 });
/*     */       } else {
/* 748 */         MethodType methodType = MethodType.methodType(paramBasicType.basicTypeClass(), basicType.basicTypeClass());
/* 749 */         name = new LambdaForm.Name(methodType, new Object[] { name3, this.lambdaForm.names[this.lambdaForm.result] });
/*     */       }  }
/*     */ 
/*     */     
/* 753 */     if (name != null)
/* 754 */       lambdaFormBuffer.insertExpression(i++, name); 
/* 755 */     lambdaFormBuffer.setResult(name);
/*     */     
/* 757 */     lambdaForm = lambdaFormBuffer.endEdit();
/* 758 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm foldArgumentsForm(int paramInt, boolean paramBoolean, MethodType paramMethodType) {
/* 762 */     int i = paramMethodType.parameterCount();
/* 763 */     Transform.Kind kind = paramBoolean ? Transform.Kind.FOLD_ARGS_TO_VOID : Transform.Kind.FOLD_ARGS;
/* 764 */     Transform transform = Transform.of(kind, paramInt, i);
/* 765 */     LambdaForm lambdaForm = getInCache(transform);
/* 766 */     if (lambdaForm != null) {
/* 767 */       assert lambdaForm.arity == this.lambdaForm.arity - ((kind == Transform.Kind.FOLD_ARGS) ? 1 : 0);
/* 768 */       return lambdaForm;
/*     */     } 
/* 770 */     lambdaForm = makeArgumentCombinationForm(paramInt, paramMethodType, true, paramBoolean);
/* 771 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   LambdaForm permuteArgumentsForm(int paramInt, int[] paramArrayOfint) {
/* 775 */     assert paramInt == 1;
/* 776 */     int i = this.lambdaForm.names.length;
/* 777 */     int j = paramArrayOfint.length;
/* 778 */     int k = 0;
/* 779 */     boolean bool = true;
/* 780 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/* 781 */       int i3 = paramArrayOfint[b1];
/* 782 */       if (i3 != b1) bool = false; 
/* 783 */       k = Math.max(k, i3 + 1);
/*     */     } 
/* 785 */     assert paramInt + paramArrayOfint.length == this.lambdaForm.arity;
/* 786 */     if (bool) return this.lambdaForm; 
/* 787 */     Transform transform = Transform.of(Transform.Kind.PERMUTE_ARGS, paramArrayOfint);
/* 788 */     LambdaForm lambdaForm = getInCache(transform);
/* 789 */     if (lambdaForm != null) {
/* 790 */       assert lambdaForm.arity == paramInt + k : lambdaForm;
/* 791 */       return lambdaForm;
/*     */     } 
/*     */     
/* 794 */     LambdaForm.BasicType[] arrayOfBasicType = new LambdaForm.BasicType[k]; byte b2;
/* 795 */     for (b2 = 0; b2 < j; b2++) {
/* 796 */       int i3 = paramArrayOfint[b2];
/* 797 */       arrayOfBasicType[i3] = (this.lambdaForm.names[paramInt + b2]).type;
/*     */     } 
/* 799 */     assert paramInt + j == this.lambdaForm.arity;
/* 800 */     assert permutedTypesMatch(paramArrayOfint, arrayOfBasicType, this.lambdaForm.names, paramInt);
/* 801 */     b2 = 0;
/* 802 */     while (b2 < j && paramArrayOfint[b2] == b2) {
/* 803 */       b2++;
/*     */     }
/* 805 */     LambdaForm.Name[] arrayOfName = new LambdaForm.Name[i - j + k];
/* 806 */     System.arraycopy(this.lambdaForm.names, 0, arrayOfName, 0, paramInt + b2);
/* 807 */     int m = i - this.lambdaForm.arity;
/* 808 */     System.arraycopy(this.lambdaForm.names, paramInt + j, arrayOfName, paramInt + k, m);
/* 809 */     int n = arrayOfName.length - m;
/* 810 */     int i1 = this.lambdaForm.result;
/* 811 */     if (i1 >= paramInt)
/* 812 */       if (i1 < paramInt + j) {
/* 813 */         i1 = paramArrayOfint[i1 - paramInt] + paramInt;
/*     */       } else {
/* 815 */         i1 = i1 - j + k;
/*     */       }  
/*     */     int i2;
/* 818 */     for (i2 = b2; i2 < j; i2++) {
/* 819 */       LambdaForm.Name name1 = this.lambdaForm.names[paramInt + i2];
/* 820 */       int i3 = paramArrayOfint[i2];
/* 821 */       LambdaForm.Name name2 = arrayOfName[paramInt + i3];
/* 822 */       if (name2 == null) {
/* 823 */         arrayOfName[paramInt + i3] = name2 = new LambdaForm.Name(arrayOfBasicType[i3]);
/*     */       } else {
/* 825 */         assert name2.type == arrayOfBasicType[i3];
/*     */       } 
/* 827 */       for (int i4 = n; i4 < arrayOfName.length; i4++) {
/* 828 */         arrayOfName[i4] = arrayOfName[i4].replaceName(name1, name2);
/*     */       }
/*     */     } 
/* 831 */     for (i2 = paramInt + b2; i2 < n; i2++) {
/* 832 */       if (arrayOfName[i2] == null) {
/* 833 */         arrayOfName[i2] = LambdaForm.argument(i2, arrayOfBasicType[i2 - paramInt]);
/*     */       }
/*     */     } 
/* 836 */     for (i2 = this.lambdaForm.arity; i2 < this.lambdaForm.names.length; i2++) {
/* 837 */       int i3 = i2 - this.lambdaForm.arity + n;
/* 838 */       LambdaForm.Name name1 = this.lambdaForm.names[i2];
/* 839 */       LambdaForm.Name name2 = arrayOfName[i3];
/* 840 */       if (name1 != name2) {
/* 841 */         for (int i4 = i3 + 1; i4 < arrayOfName.length; i4++) {
/* 842 */           arrayOfName[i4] = arrayOfName[i4].replaceName(name1, name2);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 847 */     lambdaForm = new LambdaForm(this.lambdaForm.debugName, n, arrayOfName, i1);
/* 848 */     return putInCache(transform, lambdaForm);
/*     */   }
/*     */   
/*     */   static boolean permutedTypesMatch(int[] paramArrayOfint, LambdaForm.BasicType[] paramArrayOfBasicType, LambdaForm.Name[] paramArrayOfName, int paramInt) {
/* 852 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 853 */       assert paramArrayOfName[paramInt + b].isParam();
/* 854 */       assert (paramArrayOfName[paramInt + b]).type == paramArrayOfBasicType[paramArrayOfint[b]];
/*     */     } 
/* 856 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/LambdaFormEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */