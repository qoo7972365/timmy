/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.lang.invoke.InvokerBytecodeGenerator;
/*      */ import java.lang.invoke.LambdaForm;
/*      */ import java.lang.invoke.MemberName;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleImpl;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.MethodTypeForm;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*      */ import jdk.internal.org.objectweb.asm.Label;
/*      */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*      */ import sun.invoke.util.VerifyAccess;
/*      */ import sun.invoke.util.VerifyType;
/*      */ import sun.invoke.util.Wrapper;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class InvokerBytecodeGenerator
/*      */ {
/*      */   private static final String MH = "java/lang/invoke/MethodHandle";
/*      */   private static final String MHI = "java/lang/invoke/MethodHandleImpl";
/*      */   private static final String LF = "java/lang/invoke/LambdaForm";
/*      */   private static final String LFN = "java/lang/invoke/LambdaForm$Name";
/*      */   private static final String CLS = "java/lang/Class";
/*      */   private static final String OBJ = "java/lang/Object";
/*      */   private static final String OBJARY = "[Ljava/lang/Object;";
/*      */   private static final String MH_SIG = "Ljava/lang/invoke/MethodHandle;";
/*      */   private static final String LF_SIG = "Ljava/lang/invoke/LambdaForm;";
/*      */   private static final String LFN_SIG = "Ljava/lang/invoke/LambdaForm$Name;";
/*      */   private static final String LL_SIG = "(Ljava/lang/Object;)Ljava/lang/Object;";
/*      */   private static final String LLV_SIG = "(Ljava/lang/Object;Ljava/lang/Object;)V";
/*      */   private static final String CLL_SIG = "(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;";
/*      */   private static final String superName = "java/lang/Object";
/*      */   private final String className;
/*      */   private final String sourceFile;
/*      */   private final LambdaForm lambdaForm;
/*      */   private final String invokerName;
/*      */   private final MethodType invokerType;
/*      */   private final int[] localsMap;
/*      */   private final LambdaForm.BasicType[] localTypes;
/*      */   private final Class<?>[] localClasses;
/*      */   private ClassWriter cw;
/*      */   private MethodVisitor mv;
/*   88 */   private static final MemberName.Factory MEMBERNAME_FACTORY = MemberName.getFactory();
/*   89 */   private static final Class<?> HOST_CLASS = LambdaForm.class;
/*      */   private static final HashMap<String, Integer> DUMP_CLASS_FILES_COUNTERS;
/*      */   private static final File DUMP_CLASS_FILES_DIR;
/*      */   
/*      */   private InvokerBytecodeGenerator(LambdaForm paramLambdaForm, int paramInt, String paramString1, String paramString2, MethodType paramMethodType) {
/*   94 */     if (paramString2.contains(".")) {
/*   95 */       int i = paramString2.indexOf(".");
/*   96 */       paramString1 = paramString2.substring(0, i);
/*   97 */       paramString2 = paramString2.substring(i + 1);
/*      */     } 
/*   99 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*  100 */       paramString1 = makeDumpableClassName(paramString1);
/*      */     }
/*  102 */     this.className = "java/lang/invoke/LambdaForm$" + paramString1;
/*  103 */     this.sourceFile = "LambdaForm$" + paramString1;
/*  104 */     this.lambdaForm = paramLambdaForm;
/*  105 */     this.invokerName = paramString2;
/*  106 */     this.invokerType = paramMethodType;
/*  107 */     this.localsMap = new int[paramInt + 1];
/*      */     
/*  109 */     this.localTypes = new LambdaForm.BasicType[paramInt + 1];
/*  110 */     this.localClasses = new Class[paramInt + 1];
/*      */   }
/*      */ 
/*      */   
/*      */   private InvokerBytecodeGenerator(String paramString1, String paramString2, MethodType paramMethodType) {
/*  115 */     this(null, paramMethodType.parameterCount(), paramString1, paramString2, paramMethodType);
/*      */ 
/*      */     
/*  118 */     this.localTypes[this.localTypes.length - 1] = LambdaForm.BasicType.V_TYPE;
/*  119 */     for (byte b = 0; b < this.localsMap.length; b++) {
/*  120 */       this.localsMap[b] = paramMethodType.parameterSlotCount() - paramMethodType.parameterSlotDepth(b);
/*  121 */       if (b < paramMethodType.parameterCount()) {
/*  122 */         this.localTypes[b] = LambdaForm.BasicType.basicType(paramMethodType.parameterType(b));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private InvokerBytecodeGenerator(String paramString, LambdaForm paramLambdaForm, MethodType paramMethodType) {
/*  128 */     this(paramLambdaForm, paramLambdaForm.names.length, paramString, paramLambdaForm.debugName, paramMethodType);
/*      */ 
/*      */     
/*  131 */     LambdaForm.Name[] arrayOfName = paramLambdaForm.names; byte b; int i;
/*  132 */     for (b = 0, i = 0; b < this.localsMap.length; b++) {
/*  133 */       this.localsMap[b] = i;
/*  134 */       if (b < arrayOfName.length) {
/*  135 */         LambdaForm.BasicType basicType = arrayOfName[b].type();
/*  136 */         i += basicType.basicTypeSlots();
/*  137 */         this.localTypes[b] = basicType;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  149 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*  150 */       DUMP_CLASS_FILES_COUNTERS = new HashMap<>();
/*      */       try {
/*  152 */         File file = new File("DUMP_CLASS_FILES");
/*  153 */         if (!file.exists()) {
/*  154 */           file.mkdirs();
/*      */         }
/*  156 */         DUMP_CLASS_FILES_DIR = file;
/*  157 */         System.out.println("Dumping class files to " + DUMP_CLASS_FILES_DIR + "/...");
/*  158 */       } catch (Exception exception) {
/*  159 */         throw MethodHandleStatics.newInternalError(exception);
/*      */       } 
/*      */     } else {
/*  162 */       DUMP_CLASS_FILES_COUNTERS = null;
/*  163 */       DUMP_CLASS_FILES_DIR = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   static void maybeDump(final String className, final byte[] classFile) {
/*  168 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/*  169 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*      */               try {
/*  173 */                 String str = className;
/*      */                 
/*  175 */                 File file = new File(InvokerBytecodeGenerator.DUMP_CLASS_FILES_DIR, str + ".class");
/*  176 */                 System.out.println("dump: " + file);
/*  177 */                 file.getParentFile().mkdirs();
/*  178 */                 FileOutputStream fileOutputStream = new FileOutputStream(file);
/*  179 */                 fileOutputStream.write(classFile);
/*  180 */                 fileOutputStream.close();
/*  181 */                 return null;
/*  182 */               } catch (IOException iOException) {
/*  183 */                 throw MethodHandleStatics.newInternalError(iOException);
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static String makeDumpableClassName(String paramString) {
/*      */     Integer integer;
/*  193 */     synchronized (DUMP_CLASS_FILES_COUNTERS) {
/*  194 */       integer = DUMP_CLASS_FILES_COUNTERS.get(paramString);
/*  195 */       if (integer == null) integer = Integer.valueOf(0); 
/*  196 */       DUMP_CLASS_FILES_COUNTERS.put(paramString, Integer.valueOf(integer.intValue() + 1));
/*      */     } 
/*  198 */     String str = integer.toString();
/*  199 */     while (str.length() < 3)
/*  200 */       str = "0" + str; 
/*  201 */     paramString = paramString + str;
/*  202 */     return paramString;
/*      */   }
/*      */   
/*      */   class CpPatch { final int index;
/*      */     final String placeholder;
/*      */     final Object value;
/*      */     
/*      */     CpPatch(int param1Int, String param1String, Object param1Object) {
/*  210 */       this.index = param1Int;
/*  211 */       this.placeholder = param1String;
/*  212 */       this.value = param1Object;
/*      */     }
/*      */     public String toString() {
/*  215 */       return "CpPatch/index=" + this.index + ",placeholder=" + this.placeholder + ",value=" + this.value;
/*      */     } }
/*      */ 
/*      */   
/*  219 */   Map<Object, CpPatch> cpPatches = new HashMap<>();
/*      */   
/*  221 */   int cph = 0;
/*      */   
/*      */   String constantPlaceholder(Object paramObject) {
/*  224 */     String str = "CONSTANT_PLACEHOLDER_" + this.cph++;
/*  225 */     if (MethodHandleStatics.DUMP_CLASS_FILES) str = str + " <<" + debugString(paramObject) + ">>"; 
/*  226 */     if (this.cpPatches.containsKey(str)) {
/*  227 */       throw new InternalError("observed CP placeholder twice: " + str);
/*      */     }
/*      */     
/*  230 */     int i = this.cw.newConst(str);
/*  231 */     this.cpPatches.put(str, new CpPatch(i, str, paramObject));
/*  232 */     return str;
/*      */   }
/*      */   
/*      */   Object[] cpPatches(byte[] paramArrayOfbyte) {
/*  236 */     int i = getConstantPoolSize(paramArrayOfbyte);
/*  237 */     Object[] arrayOfObject = new Object[i];
/*  238 */     for (CpPatch cpPatch : this.cpPatches.values()) {
/*  239 */       if (cpPatch.index >= i)
/*  240 */         throw new InternalError("in cpool[" + i + "]: " + cpPatch + "\n" + Arrays.toString(Arrays.copyOf(paramArrayOfbyte, 20))); 
/*  241 */       arrayOfObject[cpPatch.index] = cpPatch.value;
/*      */     } 
/*  243 */     return arrayOfObject;
/*      */   }
/*      */   
/*      */   private static String debugString(Object paramObject) {
/*  247 */     if (paramObject instanceof MethodHandle) {
/*  248 */       MethodHandle methodHandle = (MethodHandle)paramObject;
/*  249 */       MemberName memberName = methodHandle.internalMemberName();
/*  250 */       if (memberName != null)
/*  251 */         return memberName.toString(); 
/*  252 */       return methodHandle.debugString();
/*      */     } 
/*  254 */     return paramObject.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getConstantPoolSize(byte[] paramArrayOfbyte) {
/*  269 */     return (paramArrayOfbyte[8] & 0xFF) << 8 | paramArrayOfbyte[9] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MemberName loadMethod(byte[] paramArrayOfbyte) {
/*  276 */     Class<?> clazz = loadAndInitializeInvokerClass(paramArrayOfbyte, cpPatches(paramArrayOfbyte));
/*  277 */     return resolveInvokerMember(clazz, this.invokerName, this.invokerType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> loadAndInitializeInvokerClass(byte[] paramArrayOfbyte, Object[] paramArrayOfObject) {
/*  284 */     Class<?> clazz = MethodHandleStatics.UNSAFE.defineAnonymousClass(HOST_CLASS, paramArrayOfbyte, paramArrayOfObject);
/*  285 */     MethodHandleStatics.UNSAFE.ensureClassInitialized(clazz);
/*  286 */     return clazz;
/*      */   }
/*      */   
/*      */   private static MemberName resolveInvokerMember(Class<?> paramClass, String paramString, MethodType paramMethodType) {
/*  290 */     MemberName memberName = new MemberName(paramClass, paramString, paramMethodType, (byte)6);
/*      */ 
/*      */     
/*      */     try {
/*  294 */       memberName = MEMBERNAME_FACTORY.resolveOrFail((byte)6, memberName, HOST_CLASS, ReflectiveOperationException.class);
/*  295 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/*  296 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*      */     } 
/*      */     
/*  299 */     return memberName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void classFilePrologue() {
/*  307 */     this.cw = new ClassWriter(3);
/*  308 */     this.cw.visit(52, 48, this.className, null, "java/lang/Object", null);
/*  309 */     this.cw.visitSource(this.sourceFile, null);
/*      */     
/*  311 */     String str = this.invokerType.toMethodDescriptorString();
/*  312 */     this.mv = this.cw.visitMethod(8, this.invokerName, str, null, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void classFileEpilogue() {
/*  319 */     this.mv.visitMaxs(0, 0);
/*  320 */     this.mv.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitConst(Object paramObject) {
/*  327 */     if (paramObject == null) {
/*  328 */       this.mv.visitInsn(1);
/*      */       return;
/*      */     } 
/*  331 */     if (paramObject instanceof Integer) {
/*  332 */       emitIconstInsn(((Integer)paramObject).intValue());
/*      */       return;
/*      */     } 
/*  335 */     if (paramObject instanceof Long) {
/*  336 */       long l = ((Long)paramObject).longValue();
/*  337 */       if (l == (short)(int)l) {
/*  338 */         emitIconstInsn((int)l);
/*  339 */         this.mv.visitInsn(133);
/*      */         return;
/*      */       } 
/*      */     } 
/*  343 */     if (paramObject instanceof Float) {
/*  344 */       float f = ((Float)paramObject).floatValue();
/*  345 */       if (f == (short)(int)f) {
/*  346 */         emitIconstInsn((int)f);
/*  347 */         this.mv.visitInsn(134);
/*      */         return;
/*      */       } 
/*      */     } 
/*  351 */     if (paramObject instanceof Double) {
/*  352 */       double d = ((Double)paramObject).doubleValue();
/*  353 */       if (d == (short)(int)d) {
/*  354 */         emitIconstInsn((int)d);
/*  355 */         this.mv.visitInsn(135);
/*      */         return;
/*      */       } 
/*      */     } 
/*  359 */     if (paramObject instanceof Boolean) {
/*  360 */       emitIconstInsn(((Boolean)paramObject).booleanValue() ? 1 : 0);
/*      */       
/*      */       return;
/*      */     } 
/*  364 */     this.mv.visitLdcInsn(paramObject);
/*      */   }
/*      */   
/*      */   private void emitIconstInsn(int paramInt) {
/*      */     byte b;
/*  369 */     switch (paramInt) { case 0:
/*  370 */         b = 3; break;
/*  371 */       case 1: b = 4; break;
/*  372 */       case 2: b = 5; break;
/*  373 */       case 3: b = 6; break;
/*  374 */       case 4: b = 7; break;
/*  375 */       case 5: b = 8; break;
/*      */       default:
/*  377 */         if (paramInt == (byte)paramInt) {
/*  378 */           this.mv.visitIntInsn(16, paramInt & 0xFF);
/*  379 */         } else if (paramInt == (short)paramInt) {
/*  380 */           this.mv.visitIntInsn(17, (char)paramInt);
/*      */         } else {
/*  382 */           this.mv.visitLdcInsn(Integer.valueOf(paramInt));
/*      */         } 
/*      */         return; }
/*      */     
/*  386 */     this.mv.visitInsn(b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitLoadInsn(LambdaForm.BasicType paramBasicType, int paramInt) {
/*  393 */     int i = loadInsnOpcode(paramBasicType);
/*  394 */     this.mv.visitVarInsn(i, this.localsMap[paramInt]);
/*      */   }
/*      */   
/*      */   private int loadInsnOpcode(LambdaForm.BasicType paramBasicType) throws InternalError {
/*  398 */     switch (paramBasicType) { case SELECT_ALTERNATIVE:
/*  399 */         return 21;
/*  400 */       case GUARD_WITH_CATCH: return 22;
/*  401 */       case NEW_ARRAY: return 23;
/*  402 */       case ARRAY_LOAD: return 24;
/*  403 */       case ARRAY_STORE: return 25; }
/*      */     
/*  405 */     throw new InternalError("unknown type: " + paramBasicType);
/*      */   }
/*      */   
/*      */   private void emitAloadInsn(int paramInt) {
/*  409 */     emitLoadInsn(LambdaForm.BasicType.L_TYPE, paramInt);
/*      */   }
/*      */   
/*      */   private void emitStoreInsn(LambdaForm.BasicType paramBasicType, int paramInt) {
/*  413 */     int i = storeInsnOpcode(paramBasicType);
/*  414 */     this.mv.visitVarInsn(i, this.localsMap[paramInt]);
/*      */   }
/*      */   
/*      */   private int storeInsnOpcode(LambdaForm.BasicType paramBasicType) throws InternalError {
/*  418 */     switch (paramBasicType) { case SELECT_ALTERNATIVE:
/*  419 */         return 54;
/*  420 */       case GUARD_WITH_CATCH: return 55;
/*  421 */       case NEW_ARRAY: return 56;
/*  422 */       case ARRAY_LOAD: return 57;
/*  423 */       case ARRAY_STORE: return 58; }
/*      */     
/*  425 */     throw new InternalError("unknown type: " + paramBasicType);
/*      */   }
/*      */   
/*      */   private void emitAstoreInsn(int paramInt) {
/*  429 */     emitStoreInsn(LambdaForm.BasicType.L_TYPE, paramInt);
/*      */   }
/*      */   
/*      */   private byte arrayTypeCode(Wrapper paramWrapper) {
/*  433 */     switch (paramWrapper) { case SELECT_ALTERNATIVE:
/*  434 */         return 4;
/*  435 */       case GUARD_WITH_CATCH: return 8;
/*  436 */       case NEW_ARRAY: return 5;
/*  437 */       case ARRAY_LOAD: return 9;
/*  438 */       case ARRAY_STORE: return 10;
/*  439 */       case IDENTITY: return 11;
/*  440 */       case ZERO: return 6;
/*  441 */       case NONE: return 7;
/*  442 */       case null: return 0; }
/*  443 */      throw new InternalError();
/*      */   }
/*      */   
/*      */   private int arrayInsnOpcode(byte paramByte, int paramInt) throws InternalError {
/*      */     byte b;
/*  448 */     assert paramInt == 83 || paramInt == 50;
/*      */     
/*  450 */     switch (paramByte) { case 4:
/*  451 */         b = 84;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  462 */         return b - 83 + paramInt;case 8: b = 84; return b - 83 + paramInt;case 5: b = 85; return b - 83 + paramInt;case 9: b = 86; return b - 83 + paramInt;case 10: b = 79; return b - 83 + paramInt;case 11: b = 80; return b - 83 + paramInt;case 6: b = 81; return b - 83 + paramInt;case 7: b = 82; return b - 83 + paramInt;case 0: b = 83; return b - 83 + paramInt; }
/*      */     
/*      */     throw new InternalError();
/*      */   }
/*      */   private void freeFrameLocal(int paramInt) {
/*  467 */     int i = indexForFrameLocal(paramInt);
/*  468 */     if (i < 0)
/*  469 */       return;  LambdaForm.BasicType basicType = this.localTypes[i];
/*  470 */     int j = makeLocalTemp(basicType);
/*  471 */     this.mv.visitVarInsn(loadInsnOpcode(basicType), paramInt);
/*  472 */     this.mv.visitVarInsn(storeInsnOpcode(basicType), j);
/*  473 */     assert this.localsMap[i] == paramInt;
/*  474 */     this.localsMap[i] = j;
/*  475 */     assert indexForFrameLocal(paramInt) < 0;
/*      */   }
/*      */   private int indexForFrameLocal(int paramInt) {
/*  478 */     for (byte b = 0; b < this.localsMap.length; b++) {
/*  479 */       if (this.localsMap[b] == paramInt && this.localTypes[b] != LambdaForm.BasicType.V_TYPE)
/*  480 */         return b; 
/*      */     } 
/*  482 */     return -1;
/*      */   }
/*      */   private int makeLocalTemp(LambdaForm.BasicType paramBasicType) {
/*  485 */     int i = this.localsMap[this.localsMap.length - 1];
/*  486 */     this.localsMap[this.localsMap.length - 1] = i + paramBasicType.basicTypeSlots();
/*  487 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitBoxing(Wrapper paramWrapper) {
/*  496 */     String str1 = "java/lang/" + paramWrapper.wrapperType().getSimpleName();
/*  497 */     String str2 = "valueOf";
/*  498 */     String str3 = "(" + paramWrapper.basicTypeChar() + ")L" + str1 + ";";
/*  499 */     this.mv.visitMethodInsn(184, str1, str2, str3, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitUnboxing(Wrapper paramWrapper) {
/*  508 */     String str1 = "java/lang/" + paramWrapper.wrapperType().getSimpleName();
/*  509 */     String str2 = paramWrapper.primitiveSimpleName() + "Value";
/*  510 */     String str3 = "()" + paramWrapper.basicTypeChar();
/*  511 */     emitReferenceCast(paramWrapper.wrapperType(), null);
/*  512 */     this.mv.visitMethodInsn(182, str1, str2, str3, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitImplicitConversion(LambdaForm.BasicType paramBasicType, Class<?> paramClass, Object paramObject) {
/*  524 */     assert LambdaForm.BasicType.basicType(paramClass) == paramBasicType;
/*  525 */     if (paramClass == paramBasicType.basicTypeClass() && paramBasicType != LambdaForm.BasicType.L_TYPE)
/*      */       return; 
/*  527 */     switch (paramBasicType) {
/*      */       case ARRAY_STORE:
/*  529 */         if (VerifyType.isNullConversion(Object.class, paramClass, false)) {
/*  530 */           if (MethodHandleStatics.PROFILE_LEVEL > 0)
/*  531 */             emitReferenceCast(Object.class, paramObject); 
/*      */           return;
/*      */         } 
/*  534 */         emitReferenceCast(paramClass, paramObject);
/*      */         return;
/*      */       case SELECT_ALTERNATIVE:
/*  537 */         if (!VerifyType.isNullConversion(int.class, paramClass, false))
/*  538 */           emitPrimCast(paramBasicType.basicTypeWrapper(), Wrapper.forPrimitiveType(paramClass)); 
/*      */         return;
/*      */     } 
/*  541 */     throw MethodHandleStatics.newInternalError("bad implicit conversion: tc=" + paramBasicType + ": " + paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean assertStaticType(Class<?> paramClass, LambdaForm.Name paramName) {
/*  546 */     int i = paramName.index();
/*  547 */     Class<?> clazz = this.localClasses[i];
/*  548 */     if (clazz != null && (clazz == paramClass || paramClass.isAssignableFrom(clazz)))
/*  549 */       return true; 
/*  550 */     if (clazz == null || clazz.isAssignableFrom(paramClass)) {
/*  551 */       this.localClasses[i] = paramClass;
/*      */     }
/*  553 */     return false;
/*      */   }
/*      */   
/*      */   private void emitReferenceCast(Class<?> paramClass, Object paramObject) {
/*  557 */     LambdaForm.Name name = null;
/*  558 */     if (paramObject instanceof LambdaForm.Name) {
/*  559 */       LambdaForm.Name name1 = (LambdaForm.Name)paramObject;
/*  560 */       if (assertStaticType(paramClass, name1))
/*      */         return; 
/*  562 */       if (this.lambdaForm.useCount(name1) > 1)
/*      */       {
/*  564 */         name = name1;
/*      */       }
/*      */     } 
/*  567 */     if (isStaticallyNameable(paramClass)) {
/*  568 */       String str = getInternalName(paramClass);
/*  569 */       this.mv.visitTypeInsn(192, str);
/*      */     } else {
/*  571 */       this.mv.visitLdcInsn(constantPlaceholder(paramClass));
/*  572 */       this.mv.visitTypeInsn(192, "java/lang/Class");
/*  573 */       this.mv.visitInsn(95);
/*  574 */       this.mv.visitMethodInsn(184, "java/lang/invoke/MethodHandleImpl", "castReference", "(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;", false);
/*  575 */       if (Object[].class.isAssignableFrom(paramClass)) {
/*  576 */         this.mv.visitTypeInsn(192, "[Ljava/lang/Object;");
/*  577 */       } else if (MethodHandleStatics.PROFILE_LEVEL > 0) {
/*  578 */         this.mv.visitTypeInsn(192, "java/lang/Object");
/*      */       } 
/*  580 */     }  if (name != null) {
/*  581 */       this.mv.visitInsn(89);
/*  582 */       emitAstoreInsn(name.index());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitReturnInsn(LambdaForm.BasicType paramBasicType) {
/*      */     char c;
/*  591 */     switch (paramBasicType) { case SELECT_ALTERNATIVE:
/*  592 */         c = '¬'; break;
/*  593 */       case GUARD_WITH_CATCH: c = '­'; break;
/*  594 */       case NEW_ARRAY: c = '®'; break;
/*  595 */       case ARRAY_LOAD: c = '¯'; break;
/*  596 */       case ARRAY_STORE: c = '°'; break;
/*  597 */       case IDENTITY: c = '±'; break;
/*      */       default:
/*  599 */         throw new InternalError("unknown return type: " + paramBasicType); }
/*      */     
/*  601 */     this.mv.visitInsn(c);
/*      */   }
/*      */   
/*      */   private static String getInternalName(Class<?> paramClass) {
/*  605 */     if (paramClass == Object.class) return "java/lang/Object"; 
/*  606 */     if (paramClass == Object[].class) return "[Ljava/lang/Object;"; 
/*  607 */     if (paramClass == Class.class) return "java/lang/Class"; 
/*  608 */     if (paramClass == MethodHandle.class) return "java/lang/invoke/MethodHandle"; 
/*  609 */     assert VerifyAccess.isTypeVisible(paramClass, Object.class) : paramClass.getName();
/*  610 */     return paramClass.getName().replace('.', '/');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MemberName generateCustomizedCode(LambdaForm paramLambdaForm, MethodType paramMethodType) {
/*  617 */     InvokerBytecodeGenerator invokerBytecodeGenerator = new InvokerBytecodeGenerator("MH", paramLambdaForm, paramMethodType);
/*  618 */     return invokerBytecodeGenerator.loadMethod(invokerBytecodeGenerator.generateCustomizedCodeBytes());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkActualReceiver() {
/*  624 */     this.mv.visitInsn(89);
/*  625 */     this.mv.visitVarInsn(25, this.localsMap[0]);
/*  626 */     this.mv.visitMethodInsn(184, "java/lang/invoke/MethodHandleImpl", "assertSame", "(Ljava/lang/Object;Ljava/lang/Object;)V", false);
/*  627 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] generateCustomizedCodeBytes() {
/*  634 */     classFilePrologue();
/*      */ 
/*      */     
/*  637 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */ 
/*      */     
/*  640 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Compiled;", true);
/*      */     
/*  642 */     if (this.lambdaForm.forceInline) {
/*      */       
/*  644 */       this.mv.visitAnnotation("Ljava/lang/invoke/ForceInline;", true);
/*      */     } else {
/*  646 */       this.mv.visitAnnotation("Ljava/lang/invoke/DontInline;", true);
/*      */     } 
/*      */     
/*  649 */     if (this.lambdaForm.customized != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  654 */       this.mv.visitLdcInsn(constantPlaceholder(this.lambdaForm.customized));
/*  655 */       this.mv.visitTypeInsn(192, "java/lang/invoke/MethodHandle");
/*  656 */       assert checkActualReceiver();
/*  657 */       this.mv.visitVarInsn(58, this.localsMap[0]);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  662 */     LambdaForm.Name name = null;
/*  663 */     int i = this.lambdaForm.arity; while (true) { if (i < this.lambdaForm.names.length)
/*  664 */       { Class<?> clazz; MemberName memberName; LambdaForm.Name name1 = this.lambdaForm.names[i];
/*      */         
/*  666 */         emitStoreResult(name);
/*  667 */         name = name1;
/*  668 */         MethodHandleImpl.Intrinsic intrinsic = name1.function.intrinsicName();
/*  669 */         switch (intrinsic)
/*      */         { case SELECT_ALTERNATIVE:
/*  671 */             assert isSelectAlternative(i);
/*  672 */             if (MethodHandleStatics.PROFILE_GWT) {
/*  673 */               assert name1.arguments[0] instanceof LambdaForm.Name && 
/*  674 */                 nameRefersTo((LambdaForm.Name)name1.arguments[0], MethodHandleImpl.class, "profileBoolean");
/*  675 */               this.mv.visitAnnotation("Ljava/lang/invoke/InjectedProfile;", true);
/*      */             } 
/*  677 */             name = emitSelectAlternative(name1, this.lambdaForm.names[i + 1]);
/*  678 */             i++;
/*      */             break;
/*      */           case GUARD_WITH_CATCH:
/*  681 */             assert isGuardWithCatch(i);
/*  682 */             name = emitGuardWithCatch(i);
/*  683 */             i += 2;
/*      */             break;
/*      */           case NEW_ARRAY:
/*  686 */             clazz = name1.function.methodType().returnType();
/*  687 */             if (isStaticallyNameable(clazz)) {
/*  688 */               emitNewArray(name1);
/*      */               break;
/*      */             } 
/*      */           
/*      */           case ARRAY_LOAD:
/*  693 */             emitArrayLoad(name1);
/*      */             break;
/*      */           case ARRAY_STORE:
/*  696 */             emitArrayStore(name1);
/*      */             break;
/*      */           case IDENTITY:
/*  699 */             assert name1.arguments.length == 1;
/*  700 */             emitPushArguments(name1);
/*      */             break;
/*      */           case ZERO:
/*  703 */             assert name1.arguments.length == 0;
/*  704 */             emitConst(name1.type.basicTypeWrapper().zero());
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case NONE:
/*  713 */             memberName = name1.function.member();
/*  714 */             if (isStaticallyInvocable(memberName))
/*  715 */             { emitStaticInvoke(memberName, name1); }
/*      */             else
/*  717 */             { emitInvoke(name1); }  i++; continue;
/*      */           default:
/*      */             throw MethodHandleStatics.newInternalError("Unknown intrinsic: " + intrinsic); }  }
/*      */       else { break; }
/*      */        i++; }
/*  722 */      emitReturn(name);
/*      */     
/*  724 */     classFileEpilogue();
/*  725 */     bogusMethod(new Object[] { this.lambdaForm });
/*      */     
/*  727 */     byte[] arrayOfByte = this.cw.toByteArray();
/*  728 */     maybeDump(this.className, arrayOfByte);
/*  729 */     return arrayOfByte;
/*      */   }
/*      */   
/*  732 */   void emitArrayLoad(LambdaForm.Name paramName) { emitArrayOp(paramName, 50); } void emitArrayStore(LambdaForm.Name paramName) {
/*  733 */     emitArrayOp(paramName, 83);
/*      */   }
/*      */   void emitArrayOp(LambdaForm.Name paramName, int paramInt) {
/*  736 */     assert paramInt == 50 || paramInt == 83;
/*  737 */     Class<?> clazz = paramName.function.methodType().parameterType(0).getComponentType();
/*  738 */     assert clazz != null;
/*  739 */     emitPushArguments(paramName);
/*  740 */     if (clazz.isPrimitive()) {
/*  741 */       Wrapper wrapper = Wrapper.forPrimitiveType(clazz);
/*  742 */       paramInt = arrayInsnOpcode(arrayTypeCode(wrapper), paramInt);
/*      */     } 
/*  744 */     this.mv.visitInsn(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void emitInvoke(LambdaForm.Name paramName) {
/*  751 */     assert !isLinkerMethodInvoke(paramName);
/*      */ 
/*      */     
/*  754 */     MethodHandle methodHandle = paramName.function.resolvedHandle;
/*  755 */     assert methodHandle != null : paramName.exprString();
/*  756 */     this.mv.visitLdcInsn(constantPlaceholder(methodHandle));
/*  757 */     emitReferenceCast(MethodHandle.class, methodHandle);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     emitPushArguments(paramName);
/*      */ 
/*      */     
/*  771 */     MethodType methodType = paramName.function.methodType();
/*  772 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", methodType.basicType().toMethodDescriptorString(), false);
/*      */   }
/*      */   
/*  775 */   private static Class<?>[] STATICALLY_INVOCABLE_PACKAGES = new Class[] { Object.class, Arrays.class, Unsafe.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isStaticallyInvocable(LambdaForm.Name paramName) {
/*  784 */     return isStaticallyInvocable(paramName.function.member());
/*      */   }
/*      */   
/*      */   static boolean isStaticallyInvocable(MemberName paramMemberName) {
/*  788 */     if (paramMemberName == null) return false; 
/*  789 */     if (paramMemberName.isConstructor()) return false; 
/*  790 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/*  791 */     if (clazz.isArray() || clazz.isPrimitive())
/*  792 */       return false; 
/*  793 */     if (clazz.isAnonymousClass() || clazz.isLocalClass())
/*  794 */       return false; 
/*  795 */     if (clazz.getClassLoader() != MethodHandle.class.getClassLoader())
/*  796 */       return false; 
/*  797 */     if (ReflectUtil.isVMAnonymousClass(clazz))
/*  798 */       return false; 
/*  799 */     MethodType methodType = paramMemberName.getMethodOrFieldType();
/*  800 */     if (!isStaticallyNameable(methodType.returnType()))
/*  801 */       return false; 
/*  802 */     for (Class<?> clazz1 : methodType.parameterArray()) {
/*  803 */       if (!isStaticallyNameable(clazz1))
/*  804 */         return false; 
/*  805 */     }  if (!paramMemberName.isPrivate() && VerifyAccess.isSamePackage(MethodHandle.class, clazz))
/*  806 */       return true; 
/*  807 */     if (paramMemberName.isPublic() && isStaticallyNameable(clazz))
/*  808 */       return true; 
/*  809 */     return false;
/*      */   }
/*      */   
/*      */   static boolean isStaticallyNameable(Class<?> paramClass) {
/*  813 */     if (paramClass == Object.class)
/*  814 */       return true; 
/*  815 */     while (paramClass.isArray())
/*  816 */       paramClass = paramClass.getComponentType(); 
/*  817 */     if (paramClass.isPrimitive())
/*  818 */       return true; 
/*  819 */     if (ReflectUtil.isVMAnonymousClass(paramClass)) {
/*  820 */       return false;
/*      */     }
/*  822 */     if (paramClass.getClassLoader() != Object.class.getClassLoader())
/*  823 */       return false; 
/*  824 */     if (VerifyAccess.isSamePackage(MethodHandle.class, paramClass))
/*  825 */       return true; 
/*  826 */     if (!Modifier.isPublic(paramClass.getModifiers()))
/*  827 */       return false; 
/*  828 */     for (Class<?> clazz : STATICALLY_INVOCABLE_PACKAGES) {
/*  829 */       if (VerifyAccess.isSamePackage(clazz, paramClass))
/*  830 */         return true; 
/*      */     } 
/*  832 */     return false;
/*      */   }
/*      */   
/*      */   void emitStaticInvoke(LambdaForm.Name paramName) {
/*  836 */     emitStaticInvoke(paramName.function.member(), paramName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void emitStaticInvoke(MemberName paramMemberName, LambdaForm.Name paramName) {
/*  843 */     assert paramMemberName.equals(paramName.function.member());
/*  844 */     Class<?> clazz = paramMemberName.getDeclaringClass();
/*  845 */     String str1 = getInternalName(clazz);
/*  846 */     String str2 = paramMemberName.getName();
/*      */     
/*  848 */     byte b = paramMemberName.getReferenceKind();
/*  849 */     if (b == 7) {
/*      */       
/*  851 */       assert paramMemberName.canBeStaticallyBound() : paramMemberName;
/*  852 */       b = 5;
/*      */     } 
/*      */     
/*  855 */     if (paramMemberName.getDeclaringClass().isInterface() && b == 5)
/*      */     {
/*      */       
/*  858 */       b = 9;
/*      */     }
/*      */ 
/*      */     
/*  862 */     emitPushArguments(paramName);
/*      */ 
/*      */     
/*  865 */     if (paramMemberName.isMethod()) {
/*  866 */       String str = paramMemberName.getMethodType().toMethodDescriptorString();
/*  867 */       this.mv.visitMethodInsn(refKindOpcode(b), str1, str2, str, paramMemberName
/*  868 */           .getDeclaringClass().isInterface());
/*      */     } else {
/*  870 */       String str = MethodType.toFieldDescriptorString(paramMemberName.getFieldType());
/*  871 */       this.mv.visitFieldInsn(refKindOpcode(b), str1, str2, str);
/*      */     } 
/*      */     
/*  874 */     if (paramName.type == LambdaForm.BasicType.L_TYPE) {
/*  875 */       Class<?> clazz1 = paramMemberName.getInvocationType().returnType();
/*  876 */       assert !clazz1.isPrimitive();
/*  877 */       if (clazz1 != Object.class && !clazz1.isInterface()) {
/*  878 */         assertStaticType(clazz1, paramName);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   void emitNewArray(LambdaForm.Name paramName) throws InternalError {
/*  884 */     Class<?> clazz1 = paramName.function.methodType().returnType();
/*  885 */     if (paramName.arguments.length == 0) {
/*      */       Object object;
/*      */       
/*      */       try {
/*  889 */         object = paramName.function.resolvedHandle.invoke();
/*  890 */       } catch (Throwable throwable) {
/*  891 */         throw MethodHandleStatics.newInternalError(throwable);
/*      */       } 
/*  893 */       assert Array.getLength(object) == 0;
/*  894 */       assert object.getClass() == clazz1;
/*  895 */       this.mv.visitLdcInsn(constantPlaceholder(object));
/*  896 */       emitReferenceCast(clazz1, object);
/*      */       return;
/*      */     } 
/*  899 */     Class<?> clazz2 = clazz1.getComponentType();
/*  900 */     assert clazz2 != null;
/*  901 */     emitIconstInsn(paramName.arguments.length);
/*  902 */     int i = 83;
/*  903 */     if (!clazz2.isPrimitive()) {
/*  904 */       this.mv.visitTypeInsn(189, getInternalName(clazz2));
/*      */     } else {
/*  906 */       byte b1 = arrayTypeCode(Wrapper.forPrimitiveType(clazz2));
/*  907 */       i = arrayInsnOpcode(b1, i);
/*  908 */       this.mv.visitIntInsn(188, b1);
/*      */     } 
/*      */     
/*  911 */     for (byte b = 0; b < paramName.arguments.length; b++) {
/*  912 */       this.mv.visitInsn(89);
/*  913 */       emitIconstInsn(b);
/*  914 */       emitPushArgument(paramName, b);
/*  915 */       this.mv.visitInsn(i);
/*      */     } 
/*      */     
/*  918 */     assertStaticType(clazz1, paramName);
/*      */   }
/*      */   int refKindOpcode(byte paramByte) {
/*  921 */     switch (paramByte) { case 5:
/*  922 */         return 182;
/*  923 */       case 6: return 184;
/*  924 */       case 7: return 183;
/*  925 */       case 9: return 185;
/*  926 */       case 1: return 180;
/*  927 */       case 3: return 181;
/*  928 */       case 2: return 178;
/*  929 */       case 4: return 179; }
/*      */     
/*  931 */     throw new InternalError("refKind=" + paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean memberRefersTo(MemberName paramMemberName, Class<?> paramClass, String paramString) {
/*  938 */     return (paramMemberName != null && paramMemberName
/*  939 */       .getDeclaringClass() == paramClass && paramMemberName
/*  940 */       .getName().equals(paramString));
/*      */   }
/*      */   private boolean nameRefersTo(LambdaForm.Name paramName, Class<?> paramClass, String paramString) {
/*  943 */     return (paramName.function != null && 
/*  944 */       memberRefersTo(paramName.function.member(), paramClass, paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isInvokeBasic(LambdaForm.Name paramName) {
/*  951 */     if (paramName.function == null)
/*  952 */       return false; 
/*  953 */     if (paramName.arguments.length < 1)
/*  954 */       return false; 
/*  955 */     MemberName memberName = paramName.function.member();
/*  956 */     return (memberRefersTo(memberName, MethodHandle.class, "invokeBasic") && 
/*  957 */       !memberName.isPublic() && !memberName.isStatic());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLinkerMethodInvoke(LambdaForm.Name paramName) {
/*  964 */     if (paramName.function == null)
/*  965 */       return false; 
/*  966 */     if (paramName.arguments.length < 1)
/*  967 */       return false; 
/*  968 */     MemberName memberName = paramName.function.member();
/*  969 */     return (memberName != null && memberName
/*  970 */       .getDeclaringClass() == MethodHandle.class && 
/*  971 */       !memberName.isPublic() && memberName.isStatic() && memberName
/*  972 */       .getName().startsWith("linkTo"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isSelectAlternative(int paramInt) {
/*  982 */     if (paramInt + 1 >= this.lambdaForm.names.length) return false; 
/*  983 */     LambdaForm.Name name1 = this.lambdaForm.names[paramInt];
/*  984 */     LambdaForm.Name name2 = this.lambdaForm.names[paramInt + 1];
/*  985 */     return (nameRefersTo(name1, MethodHandleImpl.class, "selectAlternative") && 
/*  986 */       isInvokeBasic(name2) && name2
/*  987 */       .lastUseIndex(name1) == 0 && this.lambdaForm
/*  988 */       .lastUseIndex(name1) == paramInt + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isGuardWithCatch(int paramInt) {
/*  999 */     if (paramInt + 2 >= this.lambdaForm.names.length) return false; 
/* 1000 */     LambdaForm.Name name1 = this.lambdaForm.names[paramInt];
/* 1001 */     LambdaForm.Name name2 = this.lambdaForm.names[paramInt + 1];
/* 1002 */     LambdaForm.Name name3 = this.lambdaForm.names[paramInt + 2];
/* 1003 */     return (nameRefersTo(name2, MethodHandleImpl.class, "guardWithCatch") && 
/* 1004 */       isInvokeBasic(name1) && 
/* 1005 */       isInvokeBasic(name3) && name2
/* 1006 */       .lastUseIndex(name1) == 3 && this.lambdaForm
/* 1007 */       .lastUseIndex(name1) == paramInt + 1 && name3
/* 1008 */       .lastUseIndex(name2) == 1 && this.lambdaForm
/* 1009 */       .lastUseIndex(name2) == paramInt + 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LambdaForm.Name emitSelectAlternative(LambdaForm.Name paramName1, LambdaForm.Name paramName2) {
/* 1024 */     assert isStaticallyInvocable(paramName2);
/*      */     
/* 1026 */     LambdaForm.Name name = (LambdaForm.Name)paramName2.arguments[0];
/*      */     
/* 1028 */     Label label1 = new Label();
/* 1029 */     Label label2 = new Label();
/*      */ 
/*      */     
/* 1032 */     emitPushArgument(paramName1, 0);
/*      */ 
/*      */     
/* 1035 */     this.mv.visitJumpInsn(153, label1);
/*      */ 
/*      */     
/* 1038 */     Class[] arrayOfClass = (Class[])this.localClasses.clone();
/* 1039 */     emitPushArgument(paramName1, 1);
/* 1040 */     emitAstoreInsn(name.index());
/* 1041 */     emitStaticInvoke(paramName2);
/*      */ 
/*      */     
/* 1044 */     this.mv.visitJumpInsn(167, label2);
/*      */ 
/*      */     
/* 1047 */     this.mv.visitLabel(label1);
/*      */ 
/*      */     
/* 1050 */     System.arraycopy(arrayOfClass, 0, this.localClasses, 0, arrayOfClass.length);
/* 1051 */     emitPushArgument(paramName1, 2);
/* 1052 */     emitAstoreInsn(name.index());
/* 1053 */     emitStaticInvoke(paramName2);
/*      */ 
/*      */     
/* 1056 */     this.mv.visitLabel(label2);
/*      */     
/* 1058 */     System.arraycopy(arrayOfClass, 0, this.localClasses, 0, arrayOfClass.length);
/*      */     
/* 1060 */     return paramName2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LambdaForm.Name emitGuardWithCatch(int paramInt) {
/* 1084 */     LambdaForm.Name name1 = this.lambdaForm.names[paramInt];
/* 1085 */     LambdaForm.Name name2 = this.lambdaForm.names[paramInt + 1];
/* 1086 */     LambdaForm.Name name3 = this.lambdaForm.names[paramInt + 2];
/*      */     
/* 1088 */     Label label1 = new Label();
/* 1089 */     Label label2 = new Label();
/* 1090 */     Label label3 = new Label();
/* 1091 */     Label label4 = new Label();
/*      */     
/* 1093 */     Class<?> clazz = name3.function.resolvedHandle.type().returnType();
/*      */ 
/*      */     
/* 1096 */     MethodType methodType1 = name1.function.resolvedHandle.type().dropParameterTypes(0, 1).changeReturnType(clazz);
/*      */     
/* 1098 */     this.mv.visitTryCatchBlock(label1, label2, label3, "java/lang/Throwable");
/*      */ 
/*      */     
/* 1101 */     this.mv.visitLabel(label1);
/*      */     
/* 1103 */     emitPushArgument(name2, 0);
/* 1104 */     emitPushArguments(name1, 1);
/* 1105 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", methodType1.basicType().toMethodDescriptorString(), false);
/* 1106 */     this.mv.visitLabel(label2);
/* 1107 */     this.mv.visitJumpInsn(167, label4);
/*      */ 
/*      */     
/* 1110 */     this.mv.visitLabel(label3);
/*      */ 
/*      */     
/* 1113 */     this.mv.visitInsn(89);
/*      */     
/* 1115 */     emitPushArgument(name2, 1);
/* 1116 */     this.mv.visitInsn(95);
/* 1117 */     this.mv.visitMethodInsn(182, "java/lang/Class", "isInstance", "(Ljava/lang/Object;)Z", false);
/* 1118 */     Label label5 = new Label();
/* 1119 */     this.mv.visitJumpInsn(153, label5);
/*      */ 
/*      */ 
/*      */     
/* 1123 */     emitPushArgument(name2, 2);
/* 1124 */     this.mv.visitInsn(95);
/* 1125 */     emitPushArguments(name1, 1);
/* 1126 */     MethodType methodType2 = methodType1.insertParameterTypes(0, new Class[] { Throwable.class });
/* 1127 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", methodType2.basicType().toMethodDescriptorString(), false);
/* 1128 */     this.mv.visitJumpInsn(167, label4);
/*      */     
/* 1130 */     this.mv.visitLabel(label5);
/* 1131 */     this.mv.visitInsn(191);
/*      */     
/* 1133 */     this.mv.visitLabel(label4);
/*      */     
/* 1135 */     return name3;
/*      */   }
/*      */   
/*      */   private void emitPushArguments(LambdaForm.Name paramName) {
/* 1139 */     emitPushArguments(paramName, 0);
/*      */   }
/*      */   
/*      */   private void emitPushArguments(LambdaForm.Name paramName, int paramInt) {
/* 1143 */     for (int i = paramInt; i < paramName.arguments.length; i++) {
/* 1144 */       emitPushArgument(paramName, i);
/*      */     }
/*      */   }
/*      */   
/*      */   private void emitPushArgument(LambdaForm.Name paramName, int paramInt) {
/* 1149 */     Object object = paramName.arguments[paramInt];
/* 1150 */     Class<?> clazz = paramName.function.methodType().parameterType(paramInt);
/* 1151 */     emitPushArgument(clazz, object);
/*      */   }
/*      */   
/*      */   private void emitPushArgument(Class<?> paramClass, Object paramObject) {
/* 1155 */     LambdaForm.BasicType basicType = LambdaForm.BasicType.basicType(paramClass);
/* 1156 */     if (paramObject instanceof LambdaForm.Name) {
/* 1157 */       LambdaForm.Name name = (LambdaForm.Name)paramObject;
/* 1158 */       emitLoadInsn(name.type, name.index());
/* 1159 */       emitImplicitConversion(name.type, paramClass, name);
/* 1160 */     } else if ((paramObject == null || paramObject instanceof String) && basicType == LambdaForm.BasicType.L_TYPE) {
/* 1161 */       emitConst(paramObject);
/*      */     }
/* 1163 */     else if (Wrapper.isWrapperType(paramObject.getClass()) && basicType != LambdaForm.BasicType.L_TYPE) {
/* 1164 */       emitConst(paramObject);
/*      */     } else {
/* 1166 */       this.mv.visitLdcInsn(constantPlaceholder(paramObject));
/* 1167 */       emitImplicitConversion(LambdaForm.BasicType.L_TYPE, paramClass, paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitStoreResult(LambdaForm.Name paramName) {
/* 1176 */     if (paramName != null && paramName.type != LambdaForm.BasicType.V_TYPE)
/*      */     {
/* 1178 */       emitStoreInsn(paramName.type, paramName.index());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitReturn(LambdaForm.Name paramName) {
/* 1187 */     Class<?> clazz = this.invokerType.returnType();
/* 1188 */     LambdaForm.BasicType basicType = this.lambdaForm.returnType();
/* 1189 */     assert basicType == LambdaForm.BasicType.basicType(clazz);
/* 1190 */     if (basicType == LambdaForm.BasicType.V_TYPE) {
/*      */       
/* 1192 */       this.mv.visitInsn(177);
/*      */     } else {
/*      */       
/* 1195 */       LambdaForm.Name name = this.lambdaForm.names[this.lambdaForm.result];
/*      */ 
/*      */       
/* 1198 */       if (name != paramName) {
/* 1199 */         emitLoadInsn(basicType, this.lambdaForm.result);
/*      */       }
/*      */       
/* 1202 */       emitImplicitConversion(basicType, clazz, name);
/*      */ 
/*      */       
/* 1205 */       emitReturnInsn(basicType);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitPrimCast(Wrapper paramWrapper1, Wrapper paramWrapper2) {
/* 1225 */     if (paramWrapper1 == paramWrapper2) {
/*      */       return;
/*      */     }
/*      */     
/* 1229 */     if (paramWrapper1.isSubwordOrInt()) {
/*      */       
/* 1231 */       emitI2X(paramWrapper2);
/*      */     
/*      */     }
/* 1234 */     else if (paramWrapper2.isSubwordOrInt()) {
/*      */       
/* 1236 */       emitX2I(paramWrapper1);
/* 1237 */       if (paramWrapper2.bitWidth() < 32)
/*      */       {
/* 1239 */         emitI2X(paramWrapper2);
/*      */       }
/*      */     } else {
/*      */       
/* 1243 */       boolean bool = false;
/* 1244 */       switch (paramWrapper1) {
/*      */         case IDENTITY:
/* 1246 */           switch (paramWrapper2) { case ZERO:
/* 1247 */               this.mv.visitInsn(137); break;
/* 1248 */             case NONE: this.mv.visitInsn(138); break; }
/* 1249 */            bool = true;
/*      */           break;
/*      */         
/*      */         case ZERO:
/* 1253 */           switch (paramWrapper2) { case IDENTITY:
/* 1254 */               this.mv.visitInsn(140); break;
/* 1255 */             case NONE: this.mv.visitInsn(141); break; }
/* 1256 */            bool = true;
/*      */           break;
/*      */         
/*      */         case NONE:
/* 1260 */           switch (paramWrapper2) { case IDENTITY:
/* 1261 */               this.mv.visitInsn(143); break;
/* 1262 */             case ZERO: this.mv.visitInsn(144); break; }
/* 1263 */            bool = true;
/*      */           break;
/*      */         
/*      */         default:
/* 1267 */           bool = true;
/*      */           break;
/*      */       } 
/* 1270 */       if (bool) {
/* 1271 */         throw new IllegalStateException("unhandled prim cast: " + paramWrapper1 + "2" + paramWrapper2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void emitI2X(Wrapper paramWrapper) {
/* 1278 */     switch (paramWrapper) { case GUARD_WITH_CATCH:
/* 1279 */         this.mv.visitInsn(145);
/* 1280 */       case ARRAY_LOAD: this.mv.visitInsn(147);
/* 1281 */       case NEW_ARRAY: this.mv.visitInsn(146);
/*      */       case ARRAY_STORE: return;
/* 1283 */       case IDENTITY: this.mv.visitInsn(133);
/* 1284 */       case ZERO: this.mv.visitInsn(134);
/* 1285 */       case NONE: this.mv.visitInsn(135);
/*      */       
/*      */       case SELECT_ALTERNATIVE:
/* 1288 */         this.mv.visitInsn(4);
/* 1289 */         this.mv.visitInsn(126); }
/*      */     
/* 1291 */     throw new InternalError("unknown type: " + paramWrapper);
/*      */   }
/*      */ 
/*      */   
/*      */   private void emitX2I(Wrapper paramWrapper) {
/* 1296 */     switch (paramWrapper) { case IDENTITY:
/* 1297 */         this.mv.visitInsn(136); return;
/* 1298 */       case ZERO: this.mv.visitInsn(139); return;
/* 1299 */       case NONE: this.mv.visitInsn(142); return; }
/* 1300 */      throw new InternalError("unknown type: " + paramWrapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MemberName generateLambdaFormInterpreterEntryPoint(String paramString) {
/* 1308 */     assert LambdaForm.isValidSignature(paramString);
/* 1309 */     String str = "interpret_" + LambdaForm.signatureReturn(paramString).basicTypeChar();
/* 1310 */     MethodType methodType = LambdaForm.signatureType(paramString);
/* 1311 */     methodType = methodType.changeParameterType(0, MethodHandle.class);
/* 1312 */     InvokerBytecodeGenerator invokerBytecodeGenerator = new InvokerBytecodeGenerator("LFI", str, methodType);
/* 1313 */     return invokerBytecodeGenerator.loadMethod(invokerBytecodeGenerator.generateLambdaFormInterpreterEntryPointBytes());
/*      */   }
/*      */   
/*      */   private byte[] generateLambdaFormInterpreterEntryPointBytes() {
/* 1317 */     classFilePrologue();
/*      */ 
/*      */     
/* 1320 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */ 
/*      */     
/* 1323 */     this.mv.visitAnnotation("Ljava/lang/invoke/DontInline;", true);
/*      */ 
/*      */     
/* 1326 */     emitIconstInsn(this.invokerType.parameterCount());
/* 1327 */     this.mv.visitTypeInsn(189, "java/lang/Object");
/*      */ 
/*      */     
/* 1330 */     for (byte b = 0; b < this.invokerType.parameterCount(); b++) {
/* 1331 */       Class<?> clazz1 = this.invokerType.parameterType(b);
/* 1332 */       this.mv.visitInsn(89);
/* 1333 */       emitIconstInsn(b);
/* 1334 */       emitLoadInsn(LambdaForm.BasicType.basicType(clazz1), b);
/*      */       
/* 1336 */       if (clazz1.isPrimitive()) {
/* 1337 */         emitBoxing(Wrapper.forPrimitiveType(clazz1));
/*      */       }
/* 1339 */       this.mv.visitInsn(83);
/*      */     } 
/*      */     
/* 1342 */     emitAloadInsn(0);
/* 1343 */     this.mv.visitFieldInsn(180, "java/lang/invoke/MethodHandle", "form", "Ljava/lang/invoke/LambdaForm;");
/* 1344 */     this.mv.visitInsn(95);
/* 1345 */     this.mv.visitMethodInsn(182, "java/lang/invoke/LambdaForm", "interpretWithArguments", "([Ljava/lang/Object;)Ljava/lang/Object;", false);
/*      */ 
/*      */     
/* 1348 */     Class<?> clazz = this.invokerType.returnType();
/* 1349 */     if (clazz.isPrimitive() && clazz != void.class) {
/* 1350 */       emitUnboxing(Wrapper.forPrimitiveType(clazz));
/*      */     }
/*      */ 
/*      */     
/* 1354 */     emitReturnInsn(LambdaForm.BasicType.basicType(clazz));
/*      */     
/* 1356 */     classFileEpilogue();
/* 1357 */     bogusMethod(new Object[] { this.invokerType });
/*      */     
/* 1359 */     byte[] arrayOfByte = this.cw.toByteArray();
/* 1360 */     maybeDump(this.className, arrayOfByte);
/* 1361 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MemberName generateNamedFunctionInvoker(MethodTypeForm paramMethodTypeForm) {
/* 1368 */     MethodType methodType = LambdaForm.NamedFunction.INVOKER_METHOD_TYPE;
/* 1369 */     String str = "invoke_" + LambdaForm.shortenSignature(LambdaForm.basicTypeSignature(paramMethodTypeForm.erasedType()));
/* 1370 */     InvokerBytecodeGenerator invokerBytecodeGenerator = new InvokerBytecodeGenerator("NFI", str, methodType);
/* 1371 */     return invokerBytecodeGenerator.loadMethod(invokerBytecodeGenerator.generateNamedFunctionInvokerImpl(paramMethodTypeForm));
/*      */   }
/*      */   
/*      */   private byte[] generateNamedFunctionInvokerImpl(MethodTypeForm paramMethodTypeForm) {
/* 1375 */     MethodType methodType = paramMethodTypeForm.erasedType();
/* 1376 */     classFilePrologue();
/*      */ 
/*      */     
/* 1379 */     this.mv.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/*      */ 
/*      */     
/* 1382 */     this.mv.visitAnnotation("Ljava/lang/invoke/ForceInline;", true);
/*      */ 
/*      */     
/* 1385 */     emitAloadInsn(0);
/*      */ 
/*      */     
/* 1388 */     for (byte b = 0; b < methodType.parameterCount(); b++) {
/* 1389 */       emitAloadInsn(1);
/* 1390 */       emitIconstInsn(b);
/* 1391 */       this.mv.visitInsn(50);
/*      */ 
/*      */       
/* 1394 */       Class<?> clazz1 = methodType.parameterType(b);
/* 1395 */       if (clazz1.isPrimitive()) {
/* 1396 */         Class<?> clazz2 = methodType.basicType().wrap().parameterType(b);
/* 1397 */         Wrapper wrapper1 = Wrapper.forBasicType(clazz1);
/* 1398 */         Wrapper wrapper2 = wrapper1.isSubwordOrInt() ? Wrapper.INT : wrapper1;
/* 1399 */         emitUnboxing(wrapper2);
/* 1400 */         emitPrimCast(wrapper2, wrapper1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1405 */     String str = methodType.basicType().toMethodDescriptorString();
/* 1406 */     this.mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "invokeBasic", str, false);
/*      */ 
/*      */     
/* 1409 */     Class<?> clazz = methodType.returnType();
/* 1410 */     if (clazz != void.class && clazz.isPrimitive()) {
/* 1411 */       Wrapper wrapper1 = Wrapper.forBasicType(clazz);
/* 1412 */       Wrapper wrapper2 = wrapper1.isSubwordOrInt() ? Wrapper.INT : wrapper1;
/*      */       
/* 1414 */       emitPrimCast(wrapper1, wrapper2);
/* 1415 */       emitBoxing(wrapper2);
/*      */     } 
/*      */ 
/*      */     
/* 1419 */     if (clazz == void.class) {
/* 1420 */       this.mv.visitInsn(1);
/*      */     }
/* 1422 */     emitReturnInsn(LambdaForm.BasicType.L_TYPE);
/*      */     
/* 1424 */     classFileEpilogue();
/* 1425 */     bogusMethod(new Object[] { methodType });
/*      */     
/* 1427 */     byte[] arrayOfByte = this.cw.toByteArray();
/* 1428 */     maybeDump(this.className, arrayOfByte);
/* 1429 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void bogusMethod(Object... paramVarArgs) {
/* 1437 */     if (MethodHandleStatics.DUMP_CLASS_FILES) {
/* 1438 */       this.mv = this.cw.visitMethod(8, "dummy", "()V", null, null);
/* 1439 */       for (Object object : paramVarArgs) {
/* 1440 */         this.mv.visitLdcInsn(object.toString());
/* 1441 */         this.mv.visitInsn(87);
/*      */       } 
/* 1443 */       this.mv.visitInsn(177);
/* 1444 */       this.mv.visitMaxs(0, 0);
/* 1445 */       this.mv.visitEnd();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/InvokerBytecodeGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */