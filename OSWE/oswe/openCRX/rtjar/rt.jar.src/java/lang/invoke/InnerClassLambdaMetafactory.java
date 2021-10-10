/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.Serializable;
/*     */ import java.lang.invoke.AbstractValidatingLambdaMetafactory;
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.ConstantCallSite;
/*     */ import java.lang.invoke.InnerClassLambdaMetafactory;
/*     */ import java.lang.invoke.LambdaConversionException;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.invoke.ProxyClassesDumper;
/*     */ import java.lang.invoke.TypeConvertingMethodAdapter;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.PropertyPermission;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.FieldVisitor;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import sun.invoke.util.BytecodeDescriptor;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InnerClassLambdaMetafactory
/*     */   extends AbstractValidatingLambdaMetafactory
/*     */ {
/*  52 */   private static final Unsafe UNSAFE = Unsafe.getUnsafe();
/*     */   
/*     */   private static final int CLASSFILE_VERSION = 52;
/*  55 */   private static final String METHOD_DESCRIPTOR_VOID = Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]);
/*     */   
/*     */   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String NAME_CTOR = "<init>";
/*     */   
/*     */   private static final String NAME_FACTORY = "get$Lambda";
/*     */   private static final String NAME_SERIALIZED_LAMBDA = "java/lang/invoke/SerializedLambda";
/*     */   private static final String NAME_NOT_SERIALIZABLE_EXCEPTION = "java/io/NotSerializableException";
/*     */   private static final String DESCR_METHOD_WRITE_REPLACE = "()Ljava/lang/Object;";
/*     */   private static final String DESCR_METHOD_WRITE_OBJECT = "(Ljava/io/ObjectOutputStream;)V";
/*     */   private static final String DESCR_METHOD_READ_OBJECT = "(Ljava/io/ObjectInputStream;)V";
/*     */   private static final String NAME_METHOD_WRITE_REPLACE = "writeReplace";
/*     */   private static final String NAME_METHOD_READ_OBJECT = "readObject";
/*     */   private static final String NAME_METHOD_WRITE_OBJECT = "writeObject";
/*  70 */   private static final String DESCR_CTOR_SERIALIZED_LAMBDA = MethodType.methodType(void.class, Class.class, new Class[] {
/*     */         
/*     */         String.class, String.class, String.class, int.class, String.class, String.class, String.class, String.class, Object[].class
/*     */ 
/*     */       
/*  75 */       }).toMethodDescriptorString();
/*     */   
/*  77 */   private static final String DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION = MethodType.methodType(void.class, String.class).toMethodDescriptorString();
/*  78 */   private static final String[] SER_HOSTILE_EXCEPTIONS = new String[] { "java/io/NotSerializableException" };
/*     */ 
/*     */   
/*  81 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*     */ 
/*     */   
/*  84 */   private static final AtomicInteger counter = new AtomicInteger(0); private static final ProxyClassesDumper dumper;
/*     */   private final String implMethodClassName;
/*     */   private final String implMethodName;
/*     */   private final String implMethodDesc;
/*     */   private final Class<?> implMethodReturnClass;
/*     */   
/*     */   static {
/*  91 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.internal.lambda.dumpProxyClasses"), (AccessControlContext)null, new Permission[] { new PropertyPermission("jdk.internal.lambda.dumpProxyClasses", "read") });
/*     */ 
/*     */     
/*  94 */     dumper = (null == str) ? null : ProxyClassesDumper.getInstance(str);
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
/*     */   private final MethodType constructorType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ClassWriter cw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] argNames;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] argDescs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String lambdaClassName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InnerClassLambdaMetafactory(MethodHandles.Lookup paramLookup, MethodType paramMethodType1, String paramString, MethodType paramMethodType2, MethodHandle paramMethodHandle, MethodType paramMethodType3, boolean paramBoolean, Class<?>[] paramArrayOfClass, MethodType[] paramArrayOfMethodType) throws LambdaConversionException {
/* 155 */     super(paramLookup, paramMethodType1, paramString, paramMethodType2, paramMethodHandle, paramMethodType3, paramBoolean, paramArrayOfClass, paramArrayOfMethodType);
/*     */ 
/*     */     
/* 158 */     this.implMethodClassName = this.implDefiningClass.getName().replace('.', '/');
/* 159 */     this.implMethodName = this.implInfo.getName();
/* 160 */     this.implMethodDesc = this.implMethodType.toMethodDescriptorString();
/* 161 */     this
/*     */       
/* 163 */       .implMethodReturnClass = (this.implKind == 8) ? this.implDefiningClass : this.implMethodType.returnType();
/* 164 */     this.constructorType = paramMethodType1.changeReturnType(void.class);
/* 165 */     this.lambdaClassName = this.targetClass.getName().replace('.', '/') + "$$Lambda$" + counter.incrementAndGet();
/* 166 */     this.cw = new ClassWriter(1);
/* 167 */     int i = paramMethodType1.parameterCount();
/* 168 */     if (i > 0) {
/* 169 */       this.argNames = new String[i];
/* 170 */       this.argDescs = new String[i];
/* 171 */       for (byte b = 0; b < i; b++) {
/* 172 */         this.argNames[b] = "arg$" + (b + 1);
/* 173 */         this.argDescs[b] = BytecodeDescriptor.unparse(paramMethodType1.parameterType(b));
/*     */       } 
/*     */     } else {
/* 176 */       this.argNames = this.argDescs = EMPTY_STRING_ARRAY;
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
/*     */   CallSite buildCallSite() throws LambdaConversionException {
/* 194 */     final Class<?> innerClass = spinInnerClass();
/* 195 */     if (this.invokedType.parameterCount() == 0) {
/* 196 */       Constructor[] arrayOfConstructor = AccessController.<Constructor[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Constructor<?>[]>()
/*     */           {
/*     */             public Constructor<?>[] run()
/*     */             {
/* 200 */               Constructor[] arrayOfConstructor = (Constructor[])innerClass.getDeclaredConstructors();
/* 201 */               if (arrayOfConstructor.length == 1)
/*     */               {
/*     */                 
/* 204 */                 arrayOfConstructor[0].setAccessible(true);
/*     */               }
/* 206 */               return (Constructor<?>[])arrayOfConstructor;
/*     */             }
/*     */           });
/* 209 */       if (arrayOfConstructor.length != 1) {
/* 210 */         throw new LambdaConversionException("Expected one lambda constructor for " + clazz
/* 211 */             .getCanonicalName() + ", got " + arrayOfConstructor.length);
/*     */       }
/*     */       
/*     */       try {
/* 215 */         Object object = arrayOfConstructor[0].newInstance(new Object[0]);
/* 216 */         return new ConstantCallSite(MethodHandles.constant(this.samBase, object));
/*     */       }
/* 218 */       catch (ReflectiveOperationException reflectiveOperationException) {
/* 219 */         throw new LambdaConversionException("Exception instantiating lambda object", reflectiveOperationException);
/*     */       } 
/*     */     } 
/*     */     try {
/* 223 */       UNSAFE.ensureClassInitialized(clazz);
/* 224 */       return new ConstantCallSite(MethodHandles.Lookup.IMPL_LOOKUP
/*     */           
/* 226 */           .findStatic(clazz, "get$Lambda", this.invokedType));
/*     */     }
/* 228 */     catch (ReflectiveOperationException reflectiveOperationException) {
/* 229 */       throw new LambdaConversionException("Exception finding constructor", reflectiveOperationException);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> spinInnerClass() throws LambdaConversionException {
/* 251 */     String arrayOfString[], str = this.samBase.getName().replace('.', '/');
/* 252 */     int i = (!this.isSerializable && Serializable.class.isAssignableFrom(this.samBase)) ? 1 : 0;
/* 253 */     if (this.markerInterfaces.length == 0) {
/* 254 */       arrayOfString = new String[] { str };
/*     */     } else {
/*     */       
/* 257 */       LinkedHashSet<String> linkedHashSet = new LinkedHashSet(this.markerInterfaces.length + 1);
/* 258 */       linkedHashSet.add(str);
/* 259 */       for (Class<?> clazz : this.markerInterfaces) {
/* 260 */         linkedHashSet.add(clazz.getName().replace('.', '/'));
/* 261 */         i |= (!this.isSerializable && Serializable.class.isAssignableFrom(clazz)) ? 1 : 0;
/*     */       } 
/* 263 */       arrayOfString = linkedHashSet.<String>toArray(new String[linkedHashSet.size()]);
/*     */     } 
/*     */     
/* 266 */     this.cw.visit(52, 4144, this.lambdaClassName, null, "java/lang/Object", arrayOfString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     for (byte b = 0; b < this.argDescs.length; b++) {
/* 272 */       FieldVisitor fieldVisitor = this.cw.visitField(18, this.argNames[b], this.argDescs[b], null, null);
/*     */ 
/*     */ 
/*     */       
/* 276 */       fieldVisitor.visitEnd();
/*     */     } 
/*     */     
/* 279 */     generateConstructor();
/*     */     
/* 281 */     if (this.invokedType.parameterCount() != 0) {
/* 282 */       generateFactory();
/*     */     }
/*     */ 
/*     */     
/* 286 */     MethodVisitor methodVisitor = this.cw.visitMethod(1, this.samMethodName, this.samMethodType
/* 287 */         .toMethodDescriptorString(), null, null);
/* 288 */     methodVisitor.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/* 289 */     (new ForwardingMethodGenerator(methodVisitor)).generate(this.samMethodType);
/*     */ 
/*     */     
/* 292 */     if (this.additionalBridges != null) {
/* 293 */       for (MethodType methodType : this.additionalBridges) {
/* 294 */         methodVisitor = this.cw.visitMethod(65, this.samMethodName, methodType
/* 295 */             .toMethodDescriptorString(), null, null);
/* 296 */         methodVisitor.visitAnnotation("Ljava/lang/invoke/LambdaForm$Hidden;", true);
/* 297 */         (new ForwardingMethodGenerator(methodVisitor)).generate(methodType);
/*     */       } 
/*     */     }
/*     */     
/* 301 */     if (this.isSerializable) {
/* 302 */       generateSerializationFriendlyMethods();
/* 303 */     } else if (i != 0) {
/* 304 */       generateSerializationHostileMethods();
/*     */     } 
/* 306 */     this.cw.visitEnd();
/*     */ 
/*     */ 
/*     */     
/* 310 */     final byte[] classBytes = this.cw.toByteArray();
/*     */ 
/*     */     
/* 313 */     if (dumper != null) {
/* 314 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */           {
/*     */             public Void run() {
/* 317 */               InnerClassLambdaMetafactory.dumper.dumpClass(InnerClassLambdaMetafactory.this.lambdaClassName, classBytes);
/* 318 */               return null;
/*     */             }
/*     */           }(AccessControlContext)null, new Permission[] { new FilePermission("<<ALL FILES>>", "read, write"), new PropertyPermission("user.dir", "read") });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 326 */     return UNSAFE.defineAnonymousClass(this.targetClass, arrayOfByte, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateFactory() {
/* 333 */     MethodVisitor methodVisitor = this.cw.visitMethod(10, "get$Lambda", this.invokedType.toMethodDescriptorString(), null, null);
/* 334 */     methodVisitor.visitCode();
/* 335 */     methodVisitor.visitTypeInsn(187, this.lambdaClassName);
/* 336 */     methodVisitor.visitInsn(89);
/* 337 */     int i = this.invokedType.parameterCount(); byte b; int j;
/* 338 */     for (b = 0, j = 0; b < i; b++) {
/* 339 */       Class<?> clazz = this.invokedType.parameterType(b);
/* 340 */       methodVisitor.visitVarInsn(getLoadOpcode(clazz), j);
/* 341 */       j += getParameterSize(clazz);
/*     */     } 
/* 343 */     methodVisitor.visitMethodInsn(183, this.lambdaClassName, "<init>", this.constructorType.toMethodDescriptorString(), false);
/* 344 */     methodVisitor.visitInsn(176);
/* 345 */     methodVisitor.visitMaxs(-1, -1);
/* 346 */     methodVisitor.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateConstructor() {
/* 354 */     MethodVisitor methodVisitor = this.cw.visitMethod(2, "<init>", this.constructorType
/* 355 */         .toMethodDescriptorString(), null, null);
/* 356 */     methodVisitor.visitCode();
/* 357 */     methodVisitor.visitVarInsn(25, 0);
/* 358 */     methodVisitor.visitMethodInsn(183, "java/lang/Object", "<init>", METHOD_DESCRIPTOR_VOID, false);
/*     */     
/* 360 */     int i = this.invokedType.parameterCount(); byte b; int j;
/* 361 */     for (b = 0, j = 0; b < i; b++) {
/* 362 */       methodVisitor.visitVarInsn(25, 0);
/* 363 */       Class<?> clazz = this.invokedType.parameterType(b);
/* 364 */       methodVisitor.visitVarInsn(getLoadOpcode(clazz), j + 1);
/* 365 */       j += getParameterSize(clazz);
/* 366 */       methodVisitor.visitFieldInsn(181, this.lambdaClassName, this.argNames[b], this.argDescs[b]);
/*     */     } 
/* 368 */     methodVisitor.visitInsn(177);
/*     */     
/* 370 */     methodVisitor.visitMaxs(-1, -1);
/* 371 */     methodVisitor.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateSerializationFriendlyMethods() {
/* 380 */     TypeConvertingMethodAdapter typeConvertingMethodAdapter = new TypeConvertingMethodAdapter(this.cw.visitMethod(18, "writeReplace", "()Ljava/lang/Object;", null, null));
/*     */ 
/*     */ 
/*     */     
/* 384 */     typeConvertingMethodAdapter.visitCode();
/* 385 */     typeConvertingMethodAdapter.visitTypeInsn(187, "java/lang/invoke/SerializedLambda");
/* 386 */     typeConvertingMethodAdapter.visitInsn(89);
/* 387 */     typeConvertingMethodAdapter.visitLdcInsn(Type.getType(this.targetClass));
/* 388 */     typeConvertingMethodAdapter.visitLdcInsn(this.invokedType.returnType().getName().replace('.', '/'));
/* 389 */     typeConvertingMethodAdapter.visitLdcInsn(this.samMethodName);
/* 390 */     typeConvertingMethodAdapter.visitLdcInsn(this.samMethodType.toMethodDescriptorString());
/* 391 */     typeConvertingMethodAdapter.visitLdcInsn(Integer.valueOf(this.implInfo.getReferenceKind()));
/* 392 */     typeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getDeclaringClass().getName().replace('.', '/'));
/* 393 */     typeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getName());
/* 394 */     typeConvertingMethodAdapter.visitLdcInsn(this.implInfo.getMethodType().toMethodDescriptorString());
/* 395 */     typeConvertingMethodAdapter.visitLdcInsn(this.instantiatedMethodType.toMethodDescriptorString());
/* 396 */     typeConvertingMethodAdapter.iconst(this.argDescs.length);
/* 397 */     typeConvertingMethodAdapter.visitTypeInsn(189, "java/lang/Object");
/* 398 */     for (byte b = 0; b < this.argDescs.length; b++) {
/* 399 */       typeConvertingMethodAdapter.visitInsn(89);
/* 400 */       typeConvertingMethodAdapter.iconst(b);
/* 401 */       typeConvertingMethodAdapter.visitVarInsn(25, 0);
/* 402 */       typeConvertingMethodAdapter.visitFieldInsn(180, this.lambdaClassName, this.argNames[b], this.argDescs[b]);
/* 403 */       typeConvertingMethodAdapter.boxIfTypePrimitive(Type.getType(this.argDescs[b]));
/* 404 */       typeConvertingMethodAdapter.visitInsn(83);
/*     */     } 
/* 406 */     typeConvertingMethodAdapter.visitMethodInsn(183, "java/lang/invoke/SerializedLambda", "<init>", DESCR_CTOR_SERIALIZED_LAMBDA, false);
/*     */     
/* 408 */     typeConvertingMethodAdapter.visitInsn(176);
/*     */     
/* 410 */     typeConvertingMethodAdapter.visitMaxs(-1, -1);
/* 411 */     typeConvertingMethodAdapter.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateSerializationHostileMethods() {
/* 418 */     MethodVisitor methodVisitor = this.cw.visitMethod(18, "writeObject", "(Ljava/io/ObjectOutputStream;)V", null, SER_HOSTILE_EXCEPTIONS);
/*     */ 
/*     */     
/* 421 */     methodVisitor.visitCode();
/* 422 */     methodVisitor.visitTypeInsn(187, "java/io/NotSerializableException");
/* 423 */     methodVisitor.visitInsn(89);
/* 424 */     methodVisitor.visitLdcInsn("Non-serializable lambda");
/* 425 */     methodVisitor.visitMethodInsn(183, "java/io/NotSerializableException", "<init>", DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION, false);
/*     */     
/* 427 */     methodVisitor.visitInsn(191);
/* 428 */     methodVisitor.visitMaxs(-1, -1);
/* 429 */     methodVisitor.visitEnd();
/*     */     
/* 431 */     methodVisitor = this.cw.visitMethod(18, "readObject", "(Ljava/io/ObjectInputStream;)V", null, SER_HOSTILE_EXCEPTIONS);
/*     */ 
/*     */     
/* 434 */     methodVisitor.visitCode();
/* 435 */     methodVisitor.visitTypeInsn(187, "java/io/NotSerializableException");
/* 436 */     methodVisitor.visitInsn(89);
/* 437 */     methodVisitor.visitLdcInsn("Non-serializable lambda");
/* 438 */     methodVisitor.visitMethodInsn(183, "java/io/NotSerializableException", "<init>", DESCR_CTOR_NOT_SERIALIZABLE_EXCEPTION, false);
/*     */     
/* 440 */     methodVisitor.visitInsn(191);
/* 441 */     methodVisitor.visitMaxs(-1, -1);
/* 442 */     methodVisitor.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ForwardingMethodGenerator
/*     */     extends TypeConvertingMethodAdapter
/*     */   {
/*     */     ForwardingMethodGenerator(MethodVisitor param1MethodVisitor) {
/* 452 */       super(param1MethodVisitor);
/*     */     }
/*     */     
/*     */     void generate(MethodType param1MethodType) {
/* 456 */       visitCode();
/*     */       
/* 458 */       if (InnerClassLambdaMetafactory.this.implKind == 8) {
/* 459 */         visitTypeInsn(187, InnerClassLambdaMetafactory.this.implMethodClassName);
/* 460 */         visitInsn(89);
/*     */       } 
/* 462 */       for (byte b = 0; b < InnerClassLambdaMetafactory.this.argNames.length; b++) {
/* 463 */         visitVarInsn(25, 0);
/* 464 */         visitFieldInsn(180, InnerClassLambdaMetafactory.this.lambdaClassName, InnerClassLambdaMetafactory.this.argNames[b], InnerClassLambdaMetafactory.this.argDescs[b]);
/*     */       } 
/*     */       
/* 467 */       convertArgumentTypes(param1MethodType);
/*     */ 
/*     */       
/* 470 */       visitMethodInsn(invocationOpcode(), InnerClassLambdaMetafactory.this.implMethodClassName, InnerClassLambdaMetafactory.this
/* 471 */           .implMethodName, InnerClassLambdaMetafactory.this.implMethodDesc, InnerClassLambdaMetafactory.this.implDefiningClass
/* 472 */           .isInterface());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 477 */       Class<?> clazz = param1MethodType.returnType();
/* 478 */       convertType(InnerClassLambdaMetafactory.this.implMethodReturnClass, clazz, clazz);
/* 479 */       visitInsn(InnerClassLambdaMetafactory.getReturnOpcode(clazz));
/*     */       
/* 481 */       visitMaxs(-1, -1);
/* 482 */       visitEnd();
/*     */     }
/*     */     
/*     */     private void convertArgumentTypes(MethodType param1MethodType) {
/* 486 */       int i = 0;
/*     */       
/* 488 */       boolean bool = (InnerClassLambdaMetafactory.this.implIsInstanceMethod && InnerClassLambdaMetafactory.this.invokedType.parameterCount() == 0) ? true : false;
/* 489 */       byte b1 = bool ? 1 : 0;
/* 490 */       if (bool) {
/*     */         
/* 492 */         Class<?> clazz = param1MethodType.parameterType(0);
/* 493 */         visitVarInsn(InnerClassLambdaMetafactory.getLoadOpcode(clazz), i + 1);
/* 494 */         i += InnerClassLambdaMetafactory.getParameterSize(clazz);
/* 495 */         convertType(clazz, InnerClassLambdaMetafactory.this.implDefiningClass, InnerClassLambdaMetafactory.this.instantiatedMethodType.parameterType(0));
/*     */       } 
/* 497 */       int j = param1MethodType.parameterCount();
/* 498 */       int k = InnerClassLambdaMetafactory.this.implMethodType.parameterCount() - j;
/* 499 */       for (byte b2 = b1; b2 < j; b2++) {
/* 500 */         Class<?> clazz = param1MethodType.parameterType(b2);
/* 501 */         visitVarInsn(InnerClassLambdaMetafactory.getLoadOpcode(clazz), i + 1);
/* 502 */         i += InnerClassLambdaMetafactory.getParameterSize(clazz);
/* 503 */         convertType(clazz, InnerClassLambdaMetafactory.this.implMethodType.parameterType(k + b2), InnerClassLambdaMetafactory.this.instantiatedMethodType.parameterType(b2));
/*     */       } 
/*     */     }
/*     */     
/*     */     private int invocationOpcode() throws InternalError {
/* 508 */       switch (InnerClassLambdaMetafactory.this.implKind) {
/*     */         case 6:
/* 510 */           return 184;
/*     */         case 8:
/* 512 */           return 183;
/*     */         case 5:
/* 514 */           return 182;
/*     */         case 9:
/* 516 */           return 185;
/*     */         case 7:
/* 518 */           return 183;
/*     */       } 
/* 520 */       throw new InternalError("Unexpected invocation kind: " + InnerClassLambdaMetafactory.this.implKind);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static int getParameterSize(Class<?> paramClass) {
/* 526 */     if (paramClass == void.class)
/* 527 */       return 0; 
/* 528 */     if (paramClass == long.class || paramClass == double.class) {
/* 529 */       return 2;
/*     */     }
/* 531 */     return 1;
/*     */   }
/*     */   
/*     */   static int getLoadOpcode(Class<?> paramClass) {
/* 535 */     if (paramClass == void.class) {
/* 536 */       throw new InternalError("Unexpected void type of load opcode");
/*     */     }
/* 538 */     return 21 + getOpcodeOffset(paramClass);
/*     */   }
/*     */   
/*     */   static int getReturnOpcode(Class<?> paramClass) {
/* 542 */     if (paramClass == void.class) {
/* 543 */       return 177;
/*     */     }
/* 545 */     return 172 + getOpcodeOffset(paramClass);
/*     */   }
/*     */   
/*     */   private static int getOpcodeOffset(Class<?> paramClass) {
/* 549 */     if (paramClass.isPrimitive()) {
/* 550 */       if (paramClass == long.class)
/* 551 */         return 1; 
/* 552 */       if (paramClass == float.class)
/* 553 */         return 2; 
/* 554 */       if (paramClass == double.class) {
/* 555 */         return 3;
/*     */       }
/* 557 */       return 0;
/*     */     } 
/* 559 */     return 4;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/InnerClassLambdaMetafactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */