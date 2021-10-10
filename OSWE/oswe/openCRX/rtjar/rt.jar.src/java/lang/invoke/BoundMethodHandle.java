/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.DelegatingMethodHandle;
/*     */ import java.lang.invoke.InvokerBytecodeGenerator;
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.LambdaFormEditor;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.Stable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.Function;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import sun.invoke.util.ValueConversions;
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
/*     */ abstract class BoundMethodHandle
/*     */   extends MethodHandle
/*     */ {
/*     */   private static final int FIELD_COUNT_THRESHOLD = 12;
/*     */   private static final int FORM_EXPRESSION_THRESHOLD = 24;
/*     */   
/*     */   BoundMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/*  58 */     super(paramMethodType, paramLambdaForm);
/*  59 */     assert speciesData() == speciesData(paramLambdaForm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BoundMethodHandle bindSingle(MethodType paramMethodType, LambdaForm paramLambdaForm, LambdaForm.BasicType paramBasicType, Object paramObject) {
/*     */     try {
/*  69 */       switch (paramBasicType) {
/*     */         case L_TYPE:
/*  71 */           return bindSingle(paramMethodType, paramLambdaForm, paramObject);
/*     */         case I_TYPE:
/*  73 */           return SpeciesData.EMPTY.extendWith(LambdaForm.BasicType.I_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, ValueConversions.widenSubword(paramObject));
/*     */         case J_TYPE:
/*  75 */           return SpeciesData.EMPTY.extendWith(LambdaForm.BasicType.J_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, ((Long)paramObject).longValue());
/*     */         case F_TYPE:
/*  77 */           return SpeciesData.EMPTY.extendWith(LambdaForm.BasicType.F_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, ((Float)paramObject).floatValue());
/*     */         case D_TYPE:
/*  79 */           return SpeciesData.EMPTY.extendWith(LambdaForm.BasicType.D_TYPE).constructor().invokeBasic(paramMethodType, paramLambdaForm, ((Double)paramObject).doubleValue());
/*  80 */       }  throw MethodHandleStatics.newInternalError("unexpected xtype: " + paramBasicType);
/*     */     }
/*  82 */     catch (Throwable throwable) {
/*  83 */       throw MethodHandleStatics.newInternalError(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaFormEditor editor() {
/*  89 */     return this.form.editor();
/*     */   }
/*     */   
/*     */   static BoundMethodHandle bindSingle(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject) {
/*  93 */     return Species_L.make(paramMethodType, paramLambdaForm, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   BoundMethodHandle bindArgumentL(int paramInt, Object paramObject) {
/*  99 */     return editor().bindArgumentL(this, paramInt, paramObject);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentI(int paramInt1, int paramInt2) {
/* 103 */     return editor().bindArgumentI(this, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentJ(int paramInt, long paramLong) {
/* 107 */     return editor().bindArgumentJ(this, paramInt, paramLong);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentF(int paramInt, float paramFloat) {
/* 111 */     return editor().bindArgumentF(this, paramInt, paramFloat);
/*     */   }
/*     */   
/*     */   BoundMethodHandle bindArgumentD(int paramInt, double paramDouble) {
/* 115 */     return editor().bindArgumentD(this, paramInt, paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   BoundMethodHandle rebind() {
/* 120 */     if (!tooComplex()) {
/* 121 */       return this;
/*     */     }
/* 123 */     return makeReinvoker(this);
/*     */   }
/*     */   
/*     */   private boolean tooComplex() {
/* 127 */     return (fieldCount() > 12 || this.form
/* 128 */       .expressionCount() > 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BoundMethodHandle makeReinvoker(MethodHandle paramMethodHandle) {
/* 138 */     LambdaForm lambdaForm = DelegatingMethodHandle.makeReinvokerForm(paramMethodHandle, 7, Species_L.SPECIES_DATA, Species_L.SPECIES_DATA
/*     */         
/* 140 */         .getterFunction(0));
/* 141 */     return Species_L.make(paramMethodHandle.type(), lambdaForm, paramMethodHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SpeciesData speciesData(LambdaForm paramLambdaForm) {
/* 151 */     Object object = (paramLambdaForm.names[0]).constraint;
/* 152 */     if (object instanceof SpeciesData) {
/* 153 */       return (SpeciesData)object;
/*     */     }
/* 155 */     return SpeciesData.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object internalProperties() {
/* 165 */     return "\n& BMH=" + internalValues();
/*     */   }
/*     */ 
/*     */   
/*     */   final Object internalValues() {
/* 170 */     Object[] arrayOfObject = new Object[speciesData().fieldCount()];
/* 171 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 172 */       arrayOfObject[b] = arg(b);
/*     */     }
/* 174 */     return Arrays.asList(arrayOfObject);
/*     */   }
/*     */   
/*     */   final Object arg(int paramInt) {
/*     */     try {
/* 179 */       switch (speciesData().fieldType(paramInt)) { case L_TYPE:
/* 180 */           return (speciesData()).getters[paramInt].invokeBasic(this);
/* 181 */         case I_TYPE: return Integer.valueOf((speciesData()).getters[paramInt].invokeBasic(this));
/* 182 */         case J_TYPE: return Long.valueOf((speciesData()).getters[paramInt].invokeBasic(this));
/* 183 */         case F_TYPE: return Float.valueOf((speciesData()).getters[paramInt].invokeBasic(this));
/* 184 */         case D_TYPE: return Double.valueOf((speciesData()).getters[paramInt].invokeBasic(this)); }
/*     */     
/* 186 */     } catch (Throwable throwable) {
/* 187 */       throw MethodHandleStatics.newInternalError(throwable);
/*     */     } 
/* 189 */     throw new InternalError("unexpected type: " + (speciesData()).typeChars + "." + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Species_L
/*     */     extends BoundMethodHandle
/*     */   {
/*     */     final Object argL0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Species_L(MethodType param1MethodType, LambdaForm param1LambdaForm, Object param1Object) {
/* 211 */       super(param1MethodType, param1LambdaForm);
/* 212 */       this.argL0 = param1Object;
/*     */     }
/*     */     
/*     */     BoundMethodHandle.SpeciesData speciesData() {
/* 216 */       return SPECIES_DATA;
/*     */     }
/*     */     
/*     */     int fieldCount() {
/* 220 */       return 1;
/*     */     }
/* 222 */     static final BoundMethodHandle.SpeciesData SPECIES_DATA = new BoundMethodHandle.SpeciesData("L", (Class)Species_L.class);
/*     */     static BoundMethodHandle make(MethodType param1MethodType, LambdaForm param1LambdaForm, Object param1Object) {
/* 224 */       return new Species_L(param1MethodType, param1LambdaForm, param1Object);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWith(MethodType param1MethodType, LambdaForm param1LambdaForm) {
/* 228 */       return new Species_L(param1MethodType, param1LambdaForm, this.argL0);
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWithExtendL(MethodType param1MethodType, LambdaForm param1LambdaForm, Object param1Object) {
/*     */       try {
/* 233 */         return SPECIES_DATA.extendWith(LambdaForm.BasicType.L_TYPE).constructor().invokeBasic(param1MethodType, param1LambdaForm, this.argL0, param1Object);
/* 234 */       } catch (Throwable throwable) {
/* 235 */         throw MethodHandleStatics.uncaughtException(throwable);
/*     */       } 
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWithExtendI(MethodType param1MethodType, LambdaForm param1LambdaForm, int param1Int) {
/*     */       try {
/* 241 */         return SPECIES_DATA.extendWith(LambdaForm.BasicType.I_TYPE).constructor().invokeBasic(param1MethodType, param1LambdaForm, this.argL0, param1Int);
/* 242 */       } catch (Throwable throwable) {
/* 243 */         throw MethodHandleStatics.uncaughtException(throwable);
/*     */       } 
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWithExtendJ(MethodType param1MethodType, LambdaForm param1LambdaForm, long param1Long) {
/*     */       try {
/* 249 */         return SPECIES_DATA.extendWith(LambdaForm.BasicType.J_TYPE).constructor().invokeBasic(param1MethodType, param1LambdaForm, this.argL0, param1Long);
/* 250 */       } catch (Throwable throwable) {
/* 251 */         throw MethodHandleStatics.uncaughtException(throwable);
/*     */       } 
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWithExtendF(MethodType param1MethodType, LambdaForm param1LambdaForm, float param1Float) {
/*     */       try {
/* 257 */         return SPECIES_DATA.extendWith(LambdaForm.BasicType.F_TYPE).constructor().invokeBasic(param1MethodType, param1LambdaForm, this.argL0, param1Float);
/* 258 */       } catch (Throwable throwable) {
/* 259 */         throw MethodHandleStatics.uncaughtException(throwable);
/*     */       } 
/*     */     }
/*     */     
/*     */     final BoundMethodHandle copyWithExtendD(MethodType param1MethodType, LambdaForm param1LambdaForm, double param1Double) {
/*     */       try {
/* 265 */         return SPECIES_DATA.extendWith(LambdaForm.BasicType.D_TYPE).constructor().invokeBasic(param1MethodType, param1LambdaForm, this.argL0, param1Double);
/* 266 */       } catch (Throwable throwable) {
/* 267 */         throw MethodHandleStatics.uncaughtException(throwable);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class SpeciesData
/*     */   {
/*     */     private final String typeChars;
/*     */ 
/*     */     
/*     */     private final LambdaForm.BasicType[] typeCodes;
/*     */ 
/*     */     
/*     */     private final Class<? extends BoundMethodHandle> clazz;
/*     */     
/*     */     @Stable
/*     */     private final MethodHandle[] constructor;
/*     */     
/*     */     @Stable
/*     */     private final MethodHandle[] getters;
/*     */     
/*     */     @Stable
/*     */     private final LambdaForm.NamedFunction[] nominalGetters;
/*     */     
/*     */     @Stable
/*     */     private final SpeciesData[] extensions;
/*     */ 
/*     */     
/*     */     int fieldCount() {
/* 298 */       return this.typeCodes.length;
/*     */     }
/*     */     LambdaForm.BasicType fieldType(int param1Int) {
/* 301 */       return this.typeCodes[param1Int];
/*     */     }
/*     */     char fieldTypeChar(int param1Int) {
/* 304 */       return this.typeChars.charAt(param1Int);
/*     */     }
/*     */     Object fieldSignature() {
/* 307 */       return this.typeChars;
/*     */     }
/*     */     public Class<? extends BoundMethodHandle> fieldHolder() {
/* 310 */       return this.clazz;
/*     */     }
/*     */     public String toString() {
/* 313 */       return "SpeciesData<" + fieldSignature() + ">";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LambdaForm.NamedFunction getterFunction(int param1Int) {
/* 322 */       LambdaForm.NamedFunction namedFunction = this.nominalGetters[param1Int];
/* 323 */       assert namedFunction.memberDeclaringClassOrNull() == fieldHolder();
/* 324 */       assert namedFunction.returnType() == fieldType(param1Int);
/* 325 */       return namedFunction;
/*     */     }
/*     */     
/*     */     LambdaForm.NamedFunction[] getterFunctions() {
/* 329 */       return this.nominalGetters;
/*     */     }
/*     */     MethodHandle[] getterHandles() {
/* 332 */       return this.getters;
/*     */     }
/*     */     MethodHandle constructor() {
/* 335 */       return this.constructor[0];
/*     */     }
/*     */     
/* 338 */     static final SpeciesData EMPTY = new SpeciesData("", BoundMethodHandle.class);
/*     */     
/*     */     SpeciesData(String param1String, Class<? extends BoundMethodHandle> param1Class) {
/* 341 */       this.typeChars = param1String;
/* 342 */       this.typeCodes = LambdaForm.BasicType.basicTypes(param1String);
/* 343 */       this.clazz = param1Class;
/* 344 */       if (!INIT_DONE) {
/* 345 */         this.constructor = new MethodHandle[1];
/* 346 */         this.getters = new MethodHandle[param1String.length()];
/* 347 */         this.nominalGetters = new LambdaForm.NamedFunction[param1String.length()];
/*     */       } else {
/* 349 */         this.constructor = BoundMethodHandle.Factory.makeCtors(param1Class, param1String, null);
/* 350 */         this.getters = BoundMethodHandle.Factory.makeGetters(param1Class, param1String, null);
/* 351 */         this.nominalGetters = BoundMethodHandle.Factory.makeNominalGetters(param1String, null, this.getters);
/*     */       } 
/* 353 */       this.extensions = new SpeciesData[LambdaForm.BasicType.ARG_TYPE_LIMIT];
/*     */     }
/*     */     
/*     */     private void initForBootstrap() {
/* 357 */       assert !INIT_DONE;
/* 358 */       if (constructor() == null) {
/* 359 */         String str = this.typeChars;
/* 360 */         CACHE.put(str, this);
/* 361 */         BoundMethodHandle.Factory.makeCtors(this.clazz, str, this.constructor);
/* 362 */         BoundMethodHandle.Factory.makeGetters(this.clazz, str, this.getters);
/* 363 */         BoundMethodHandle.Factory.makeNominalGetters(str, this.nominalGetters, this.getters);
/*     */       } 
/*     */     }
/*     */     
/* 367 */     private static final ConcurrentMap<String, SpeciesData> CACHE = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     SpeciesData extendWith(byte param1Byte) {
/* 371 */       return extendWith(LambdaForm.BasicType.basicType(param1Byte));
/*     */     }
/*     */     
/*     */     SpeciesData extendWith(LambdaForm.BasicType param1BasicType) {
/* 375 */       int i = param1BasicType.ordinal();
/* 376 */       SpeciesData speciesData = this.extensions[i];
/* 377 */       if (speciesData != null) return speciesData; 
/* 378 */       this.extensions[i] = speciesData = get(this.typeChars + param1BasicType.basicTypeChar());
/* 379 */       return speciesData;
/*     */     }
/*     */     
/*     */     private static SpeciesData get(String param1String) {
/* 383 */       return CACHE.computeIfAbsent(param1String, new Function<String, SpeciesData>()
/*     */           {
/*     */             public BoundMethodHandle.SpeciesData apply(String param2String) {
/* 386 */               Class<? extends BoundMethodHandle> clazz = BoundMethodHandle.Factory.getConcreteBMHClass(param2String);
/*     */ 
/*     */               
/* 389 */               BoundMethodHandle.SpeciesData speciesData = new BoundMethodHandle.SpeciesData(param2String, clazz);
/*     */ 
/*     */               
/* 392 */               BoundMethodHandle.Factory.setSpeciesDataToConcreteBMHClass(clazz, speciesData);
/*     */ 
/*     */               
/* 395 */               return speciesData;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean speciesDataCachePopulated() {
/* 406 */       Class<BoundMethodHandle> clazz = BoundMethodHandle.class;
/*     */       try {
/* 408 */         for (Class<?> clazz1 : clazz.getDeclaredClasses()) {
/* 409 */           if (clazz.isAssignableFrom(clazz1)) {
/* 410 */             Class<? extends BoundMethodHandle> clazz2 = clazz1.asSubclass(BoundMethodHandle.class);
/* 411 */             SpeciesData speciesData = BoundMethodHandle.Factory.getSpeciesDataFromConcreteBMHClass(clazz2);
/* 412 */             assert speciesData != null : clazz2.getName();
/* 413 */             assert speciesData.clazz == clazz2;
/* 414 */             assert CACHE.get(speciesData.typeChars) == speciesData;
/*     */           } 
/*     */         } 
/* 417 */       } catch (Throwable throwable) {
/* 418 */         throw MethodHandleStatics.newInternalError(throwable);
/*     */       } 
/* 420 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/* 425 */       EMPTY.initForBootstrap();
/* 426 */       BoundMethodHandle.Species_L.SPECIES_DATA.initForBootstrap();
/*     */       
/* 428 */       assert speciesDataCachePopulated();
/*     */     }
/*     */     
/* 431 */     private static final boolean INIT_DONE = Boolean.TRUE.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   static SpeciesData getSpeciesData(String paramString) {
/* 436 */     return SpeciesData.get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Factory
/*     */   {
/*     */     static final String JLO_SIG = "Ljava/lang/Object;";
/*     */     
/*     */     static final String JLS_SIG = "Ljava/lang/String;";
/*     */     
/*     */     static final String JLC_SIG = "Ljava/lang/Class;";
/*     */     
/*     */     static final String MH = "java/lang/invoke/MethodHandle";
/*     */     
/*     */     static final String MH_SIG = "Ljava/lang/invoke/MethodHandle;";
/*     */     
/*     */     static final String BMH = "java/lang/invoke/BoundMethodHandle";
/*     */     
/*     */     static final String BMH_SIG = "Ljava/lang/invoke/BoundMethodHandle;";
/*     */     
/*     */     static final String SPECIES_DATA = "java/lang/invoke/BoundMethodHandle$SpeciesData";
/*     */     
/*     */     static final String SPECIES_DATA_SIG = "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String STABLE_SIG = "Ljava/lang/invoke/Stable;";
/*     */     
/*     */     static final String SPECIES_PREFIX_NAME = "Species_";
/*     */     
/*     */     static final String SPECIES_PREFIX_PATH = "java/lang/invoke/BoundMethodHandle$Species_";
/*     */     
/*     */     static final String BMHSPECIES_DATA_EWI_SIG = "(B)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String BMHSPECIES_DATA_GFC_SIG = "(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String MYSPECIES_DATA_SIG = "()Ljava/lang/invoke/BoundMethodHandle$SpeciesData;";
/*     */     
/*     */     static final String VOID_SIG = "()V";
/*     */     static final String INT_SIG = "()I";
/*     */     static final String SIG_INCIPIT = "(Ljava/lang/invoke/MethodType;Ljava/lang/invoke/LambdaForm;";
/* 475 */     static final String[] E_THROWABLE = new String[] { "java/lang/Throwable" };
/*     */     
/* 477 */     static final ConcurrentMap<String, Class<? extends BoundMethodHandle>> CLASS_CACHE = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Class<? extends BoundMethodHandle> getConcreteBMHClass(String param1String) {
/* 488 */       return CLASS_CACHE.computeIfAbsent(param1String, new Function<String, Class<? extends BoundMethodHandle>>()
/*     */           {
/*     */             public Class<? extends BoundMethodHandle> apply(String param2String)
/*     */             {
/* 492 */               return BoundMethodHandle.Factory.generateConcreteBMHClass(param2String);
/*     */             }
/*     */           });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Class<? extends BoundMethodHandle> generateConcreteBMHClass(String param1String) {
/* 562 */       ClassWriter classWriter = new ClassWriter(3);
/*     */       
/* 564 */       String str1 = LambdaForm.shortenSignature(param1String);
/* 565 */       String str2 = "java/lang/invoke/BoundMethodHandle$Species_" + str1;
/* 566 */       String str3 = "Species_" + str1;
/*     */       
/* 568 */       classWriter.visit(50, 48, str2, null, "java/lang/invoke/BoundMethodHandle", null);
/* 569 */       classWriter.visitSource(str3, null);
/*     */ 
/*     */       
/* 572 */       FieldVisitor fieldVisitor = classWriter.visitField(8, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;", null, null);
/* 573 */       fieldVisitor.visitAnnotation("Ljava/lang/invoke/Stable;", true);
/* 574 */       fieldVisitor.visitEnd();
/*     */ 
/*     */       
/* 577 */       for (byte b1 = 0; b1 < param1String.length(); b1++) {
/* 578 */         char c = param1String.charAt(b1);
/* 579 */         String str4 = makeFieldName(param1String, b1);
/* 580 */         String str5 = (c == 'L') ? "Ljava/lang/Object;" : String.valueOf(c);
/* 581 */         classWriter.visitField(16, str4, str5, null, null).visitEnd();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 587 */       MethodVisitor methodVisitor = classWriter.visitMethod(2, "<init>", makeSignature(param1String, true), null, null);
/* 588 */       methodVisitor.visitCode();
/* 589 */       methodVisitor.visitVarInsn(25, 0);
/* 590 */       methodVisitor.visitVarInsn(25, 1);
/* 591 */       methodVisitor.visitVarInsn(25, 2);
/*     */       
/* 593 */       methodVisitor.visitMethodInsn(183, "java/lang/invoke/BoundMethodHandle", "<init>", makeSignature("", true), false); int i;
/*     */       byte b2;
/* 595 */       for (i = 0, b2 = 0; i < param1String.length(); i++, b2++) {
/*     */         
/* 597 */         char c = param1String.charAt(i);
/* 598 */         methodVisitor.visitVarInsn(25, 0);
/* 599 */         methodVisitor.visitVarInsn(typeLoadOp(c), b2 + 3);
/* 600 */         methodVisitor.visitFieldInsn(181, str2, makeFieldName(param1String, i), typeSig(c));
/* 601 */         if (c == 'J' || c == 'D') {
/* 602 */           b2++;
/*     */         }
/*     */       } 
/*     */       
/* 606 */       methodVisitor.visitInsn(177);
/* 607 */       methodVisitor.visitMaxs(0, 0);
/* 608 */       methodVisitor.visitEnd();
/*     */ 
/*     */       
/* 611 */       methodVisitor = classWriter.visitMethod(16, "speciesData", "()Ljava/lang/invoke/BoundMethodHandle$SpeciesData;", null, null);
/* 612 */       methodVisitor.visitCode();
/* 613 */       methodVisitor.visitFieldInsn(178, str2, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 614 */       methodVisitor.visitInsn(176);
/* 615 */       methodVisitor.visitMaxs(0, 0);
/* 616 */       methodVisitor.visitEnd();
/*     */ 
/*     */       
/* 619 */       methodVisitor = classWriter.visitMethod(16, "fieldCount", "()I", null, null);
/* 620 */       methodVisitor.visitCode();
/* 621 */       i = param1String.length();
/* 622 */       if (i <= 5) {
/* 623 */         methodVisitor.visitInsn(3 + i);
/*     */       } else {
/* 625 */         methodVisitor.visitIntInsn(17, i);
/*     */       } 
/* 627 */       methodVisitor.visitInsn(172);
/* 628 */       methodVisitor.visitMaxs(0, 0);
/* 629 */       methodVisitor.visitEnd();
/*     */       
/* 631 */       methodVisitor = classWriter.visitMethod(8, "make", makeSignature(param1String, false), null, null);
/* 632 */       methodVisitor.visitCode();
/*     */       
/* 634 */       methodVisitor.visitTypeInsn(187, str2);
/* 635 */       methodVisitor.visitInsn(89);
/*     */       
/* 637 */       methodVisitor.visitVarInsn(25, 0);
/* 638 */       methodVisitor.visitVarInsn(25, 1);
/*     */       
/* 640 */       for (b2 = 0, null = 0; b2 < param1String.length(); b2++, null++) {
/*     */         
/* 642 */         char c = param1String.charAt(b2);
/* 643 */         methodVisitor.visitVarInsn(typeLoadOp(c), null + 2);
/* 644 */         if (c == 'J' || c == 'D') {
/* 645 */           null++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 650 */       methodVisitor.visitMethodInsn(183, str2, "<init>", makeSignature(param1String, true), false);
/* 651 */       methodVisitor.visitInsn(176);
/* 652 */       methodVisitor.visitMaxs(0, 0);
/* 653 */       methodVisitor.visitEnd();
/*     */ 
/*     */       
/* 656 */       methodVisitor = classWriter.visitMethod(16, "copyWith", makeSignature("", false), null, null);
/* 657 */       methodVisitor.visitCode();
/*     */       
/* 659 */       methodVisitor.visitTypeInsn(187, str2);
/* 660 */       methodVisitor.visitInsn(89);
/*     */       
/* 662 */       methodVisitor.visitVarInsn(25, 1);
/* 663 */       methodVisitor.visitVarInsn(25, 2);
/*     */       
/* 665 */       emitPushFields(param1String, str2, methodVisitor);
/*     */       
/* 667 */       methodVisitor.visitMethodInsn(183, str2, "<init>", makeSignature(param1String, true), false);
/* 668 */       methodVisitor.visitInsn(176);
/* 669 */       methodVisitor.visitMaxs(0, 0);
/* 670 */       methodVisitor.visitEnd();
/*     */ 
/*     */       
/* 673 */       for (LambdaForm.BasicType basicType : LambdaForm.BasicType.ARG_TYPES) {
/* 674 */         int j = basicType.ordinal();
/* 675 */         char c = basicType.basicTypeChar();
/* 676 */         methodVisitor = classWriter.visitMethod(16, "copyWithExtend" + c, makeSignature(String.valueOf(c), false), null, E_THROWABLE);
/* 677 */         methodVisitor.visitCode();
/*     */ 
/*     */         
/* 680 */         methodVisitor.visitFieldInsn(178, str2, "SPECIES_DATA", "Ljava/lang/invoke/BoundMethodHandle$SpeciesData;");
/* 681 */         int k = 3 + j;
/* 682 */         assert k <= 8;
/* 683 */         methodVisitor.visitInsn(k);
/* 684 */         methodVisitor.visitMethodInsn(182, "java/lang/invoke/BoundMethodHandle$SpeciesData", "extendWith", "(B)Ljava/lang/invoke/BoundMethodHandle$SpeciesData;", false);
/* 685 */         methodVisitor.visitMethodInsn(182, "java/lang/invoke/BoundMethodHandle$SpeciesData", "constructor", "()Ljava/lang/invoke/MethodHandle;", false);
/*     */         
/* 687 */         methodVisitor.visitVarInsn(25, 1);
/* 688 */         methodVisitor.visitVarInsn(25, 2);
/*     */         
/* 690 */         emitPushFields(param1String, str2, methodVisitor);
/*     */         
/* 692 */         methodVisitor.visitVarInsn(typeLoadOp(c), 3);
/*     */         
/* 694 */         methodVisitor.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", makeSignature(param1String + c, false), false);
/* 695 */         methodVisitor.visitInsn(176);
/* 696 */         methodVisitor.visitMaxs(0, 0);
/* 697 */         methodVisitor.visitEnd();
/*     */       } 
/*     */       
/* 700 */       classWriter.visitEnd();
/*     */ 
/*     */       
/* 703 */       byte[] arrayOfByte = classWriter.toByteArray();
/* 704 */       InvokerBytecodeGenerator.maybeDump(str2, arrayOfByte);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 709 */       return MethodHandleStatics.UNSAFE.defineClass(str2, arrayOfByte, 0, arrayOfByte.length, BoundMethodHandle.class.getClassLoader(), null).asSubclass(BoundMethodHandle.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static int typeLoadOp(char param1Char) {
/* 715 */       switch (param1Char) { case 'L':
/* 716 */           return 25;
/* 717 */         case 'I': return 21;
/* 718 */         case 'J': return 22;
/* 719 */         case 'F': return 23;
/* 720 */         case 'D': return 24; }
/* 721 */        throw MethodHandleStatics.newInternalError("unrecognized type " + param1Char);
/*     */     }
/*     */ 
/*     */     
/*     */     private static void emitPushFields(String param1String1, String param1String2, MethodVisitor param1MethodVisitor) {
/* 726 */       for (byte b = 0; b < param1String1.length(); b++) {
/* 727 */         char c = param1String1.charAt(b);
/* 728 */         param1MethodVisitor.visitVarInsn(25, 0);
/* 729 */         param1MethodVisitor.visitFieldInsn(180, param1String2, makeFieldName(param1String1, b), typeSig(c));
/*     */       } 
/*     */     }
/*     */     
/*     */     static String typeSig(char param1Char) {
/* 734 */       return (param1Char == 'L') ? "Ljava/lang/Object;" : String.valueOf(param1Char);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static MethodHandle makeGetter(Class<?> param1Class, String param1String, int param1Int) {
/* 742 */       String str = makeFieldName(param1String, param1Int);
/* 743 */       Class<?> clazz = Wrapper.forBasicType(param1String.charAt(param1Int)).primitiveType();
/*     */       try {
/* 745 */         return BoundMethodHandle.LOOKUP.findGetter(param1Class, str, clazz);
/* 746 */       } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
/* 747 */         throw MethodHandleStatics.newInternalError(noSuchFieldException);
/*     */       } 
/*     */     }
/*     */     
/*     */     static MethodHandle[] makeGetters(Class<?> param1Class, String param1String, MethodHandle[] param1ArrayOfMethodHandle) {
/* 752 */       if (param1ArrayOfMethodHandle == null) param1ArrayOfMethodHandle = new MethodHandle[param1String.length()]; 
/* 753 */       for (byte b = 0; b < param1ArrayOfMethodHandle.length; b++) {
/* 754 */         param1ArrayOfMethodHandle[b] = makeGetter(param1Class, param1String, b);
/* 755 */         assert param1ArrayOfMethodHandle[b].internalMemberName().getDeclaringClass() == param1Class;
/*     */       } 
/* 757 */       return param1ArrayOfMethodHandle;
/*     */     }
/*     */     
/*     */     static MethodHandle[] makeCtors(Class<? extends BoundMethodHandle> param1Class, String param1String, MethodHandle[] param1ArrayOfMethodHandle) {
/* 761 */       if (param1ArrayOfMethodHandle == null) param1ArrayOfMethodHandle = new MethodHandle[1]; 
/* 762 */       if (param1String.equals("")) return param1ArrayOfMethodHandle; 
/* 763 */       param1ArrayOfMethodHandle[0] = makeCbmhCtor(param1Class, param1String);
/* 764 */       return param1ArrayOfMethodHandle;
/*     */     }
/*     */     
/*     */     static LambdaForm.NamedFunction[] makeNominalGetters(String param1String, LambdaForm.NamedFunction[] param1ArrayOfNamedFunction, MethodHandle[] param1ArrayOfMethodHandle) {
/* 768 */       if (param1ArrayOfNamedFunction == null) param1ArrayOfNamedFunction = new LambdaForm.NamedFunction[param1String.length()]; 
/* 769 */       for (byte b = 0; b < param1ArrayOfNamedFunction.length; b++) {
/* 770 */         param1ArrayOfNamedFunction[b] = new LambdaForm.NamedFunction(param1ArrayOfMethodHandle[b]);
/*     */       }
/* 772 */       return param1ArrayOfNamedFunction;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static BoundMethodHandle.SpeciesData getSpeciesDataFromConcreteBMHClass(Class<? extends BoundMethodHandle> param1Class) {
/*     */       try {
/* 781 */         Field field = param1Class.getDeclaredField("SPECIES_DATA");
/* 782 */         return (BoundMethodHandle.SpeciesData)field.get((Object)null);
/* 783 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 784 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */     
/*     */     static void setSpeciesDataToConcreteBMHClass(Class<? extends BoundMethodHandle> param1Class, BoundMethodHandle.SpeciesData param1SpeciesData) {
/*     */       try {
/* 790 */         Field field = param1Class.getDeclaredField("SPECIES_DATA");
/* 791 */         assert field.getDeclaredAnnotation(Stable.class) != null;
/* 792 */         field.set((Object)null, param1SpeciesData);
/* 793 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 794 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String makeFieldName(String param1String, int param1Int) {
/* 804 */       assert param1Int >= 0 && param1Int < param1String.length();
/* 805 */       return "arg" + param1String.charAt(param1Int) + param1Int;
/*     */     }
/*     */     
/*     */     private static String makeSignature(String param1String, boolean param1Boolean) {
/* 809 */       StringBuilder stringBuilder = new StringBuilder("(Ljava/lang/invoke/MethodType;Ljava/lang/invoke/LambdaForm;");
/* 810 */       for (char c : param1String.toCharArray()) {
/* 811 */         stringBuilder.append(typeSig(c));
/*     */       }
/* 813 */       return stringBuilder.append(')').append(param1Boolean ? "V" : "Ljava/lang/invoke/BoundMethodHandle;").toString();
/*     */     }
/*     */     
/*     */     static MethodHandle makeCbmhCtor(Class<? extends BoundMethodHandle> param1Class, String param1String) {
/*     */       try {
/* 818 */         return BoundMethodHandle.LOOKUP.findStatic(param1Class, "make", MethodType.fromMethodDescriptorString(makeSignature(param1String, false), null));
/* 819 */       } catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|TypeNotPresentException noSuchMethodException) {
/* 820 */         throw MethodHandleStatics.newInternalError(noSuchMethodException);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 825 */   private static final MethodHandles.Lookup LOOKUP = MethodHandles.Lookup.IMPL_LOOKUP;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 830 */   static final SpeciesData SPECIES_DATA = SpeciesData.EMPTY;
/*     */   
/* 832 */   private static final SpeciesData[] SPECIES_DATA_CACHE = new SpeciesData[5];
/*     */   private static SpeciesData checkCache(int paramInt, String paramString) {
/* 834 */     int i = paramInt - 1;
/* 835 */     SpeciesData speciesData = SPECIES_DATA_CACHE[i];
/* 836 */     if (speciesData != null) return speciesData; 
/* 837 */     SPECIES_DATA_CACHE[i] = speciesData = getSpeciesData(paramString);
/* 838 */     return speciesData;
/*     */   }
/* 840 */   static SpeciesData speciesData_L() { return checkCache(1, "L"); }
/* 841 */   static SpeciesData speciesData_LL() { return checkCache(2, "LL"); }
/* 842 */   static SpeciesData speciesData_LLL() { return checkCache(3, "LLL"); }
/* 843 */   static SpeciesData speciesData_LLLL() { return checkCache(4, "LLLL"); } static SpeciesData speciesData_LLLLL() {
/* 844 */     return checkCache(5, "LLLLL");
/*     */   }
/*     */   
/*     */   abstract SpeciesData speciesData();
/*     */   
/*     */   abstract int fieldCount();
/*     */   
/*     */   abstract BoundMethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm);
/*     */   
/*     */   abstract BoundMethodHandle copyWithExtendL(MethodType paramMethodType, LambdaForm paramLambdaForm, Object paramObject);
/*     */   
/*     */   abstract BoundMethodHandle copyWithExtendI(MethodType paramMethodType, LambdaForm paramLambdaForm, int paramInt);
/*     */   
/*     */   abstract BoundMethodHandle copyWithExtendJ(MethodType paramMethodType, LambdaForm paramLambdaForm, long paramLong);
/*     */   
/*     */   abstract BoundMethodHandle copyWithExtendF(MethodType paramMethodType, LambdaForm paramLambdaForm, float paramFloat);
/*     */   
/*     */   abstract BoundMethodHandle copyWithExtendD(MethodType paramMethodType, LambdaForm paramLambdaForm, double paramDouble);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/BoundMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */