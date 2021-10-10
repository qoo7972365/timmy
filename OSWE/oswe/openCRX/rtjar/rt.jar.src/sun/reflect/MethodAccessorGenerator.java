/*     */ package sun.reflect;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MethodAccessorGenerator
/*     */   extends AccessorGenerator
/*     */ {
/*     */   private static final short NUM_BASE_CPOOL_ENTRIES = 12;
/*     */   private static final short NUM_METHODS = 2;
/*     */   private static final short NUM_SERIALIZATION_CPOOL_ENTRIES = 2;
/*  47 */   private static volatile int methodSymnum = 0;
/*  48 */   private static volatile int constructorSymnum = 0;
/*  49 */   private static volatile int serializationConstructorSymnum = 0;
/*     */ 
/*     */   
/*     */   private Class<?> declaringClass;
/*     */ 
/*     */   
/*     */   private Class<?>[] parameterTypes;
/*     */ 
/*     */   
/*     */   private Class<?> returnType;
/*     */ 
/*     */   
/*     */   private boolean isConstructor;
/*     */   
/*     */   private boolean forSerialization;
/*     */   
/*     */   private short targetMethodRef;
/*     */   
/*     */   private short invokeIdx;
/*     */   
/*     */   private short invokeDescriptorIdx;
/*     */   
/*     */   private short nonPrimitiveParametersBaseIdx;
/*     */ 
/*     */   
/*     */   public MethodAccessor generateMethod(Class<?> paramClass1, String paramString, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt) {
/*  75 */     return (MethodAccessor)generate(paramClass1, paramString, paramArrayOfClass1, paramClass2, paramArrayOfClass2, paramInt, false, false, (Class<?>)null);
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
/*     */   public ConstructorAccessor generateConstructor(Class<?> paramClass, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt) {
/*  92 */     return (ConstructorAccessor)generate(paramClass, "<init>", paramArrayOfClass1, void.class, paramArrayOfClass2, paramInt, true, false, (Class<?>)null);
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
/*     */   public SerializationConstructorAccessorImpl generateSerializationConstructor(Class<?> paramClass1, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt, Class<?> paramClass2) {
/* 111 */     return (SerializationConstructorAccessorImpl)
/* 112 */       generate(paramClass1, "<init>", paramArrayOfClass1, void.class, paramArrayOfClass2, paramInt, true, true, paramClass2);
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
/*     */   private MagicAccessorImpl generate(final Class<?> declaringClass, String paramString, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Class<?> paramClass3) {
/* 134 */     ByteVector byteVector = ByteVectorFactory.create();
/* 135 */     this.asm = new ClassFileAssembler(byteVector);
/* 136 */     this.declaringClass = declaringClass;
/* 137 */     this.parameterTypes = paramArrayOfClass1;
/* 138 */     this.returnType = paramClass2;
/* 139 */     this.modifiers = paramInt;
/* 140 */     this.isConstructor = paramBoolean1;
/* 141 */     this.forSerialization = paramBoolean2;
/*     */     
/* 143 */     this.asm.emitMagicAndVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     short s1 = 42;
/* 272 */     boolean bool = usesPrimitiveTypes();
/* 273 */     if (bool) {
/* 274 */       s1 = (short)(s1 + 72);
/*     */     }
/* 276 */     if (paramBoolean2) {
/* 277 */       s1 = (short)(s1 + 2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 282 */     s1 = (short)(s1 + (short)(2 * numNonPrimitiveParameterTypes()));
/*     */     
/* 284 */     this.asm.emitShort(add(s1, (short)1));
/*     */     
/* 286 */     final String generatedName = generateName(paramBoolean1, paramBoolean2);
/* 287 */     this.asm.emitConstantPoolUTF8(str);
/* 288 */     this.asm.emitConstantPoolClass(this.asm.cpi());
/* 289 */     this.thisClass = this.asm.cpi();
/* 290 */     if (paramBoolean1) {
/* 291 */       if (paramBoolean2) {
/* 292 */         this.asm
/* 293 */           .emitConstantPoolUTF8("sun/reflect/SerializationConstructorAccessorImpl");
/*     */       } else {
/* 295 */         this.asm.emitConstantPoolUTF8("sun/reflect/ConstructorAccessorImpl");
/*     */       } 
/*     */     } else {
/* 298 */       this.asm.emitConstantPoolUTF8("sun/reflect/MethodAccessorImpl");
/*     */     } 
/* 300 */     this.asm.emitConstantPoolClass(this.asm.cpi());
/* 301 */     this.superClass = this.asm.cpi();
/* 302 */     this.asm.emitConstantPoolUTF8(getClassName(declaringClass, false));
/* 303 */     this.asm.emitConstantPoolClass(this.asm.cpi());
/* 304 */     this.targetClass = this.asm.cpi();
/* 305 */     short s2 = 0;
/* 306 */     if (paramBoolean2) {
/* 307 */       this.asm.emitConstantPoolUTF8(getClassName(paramClass3, false));
/* 308 */       this.asm.emitConstantPoolClass(this.asm.cpi());
/* 309 */       s2 = this.asm.cpi();
/*     */     } 
/* 311 */     this.asm.emitConstantPoolUTF8(paramString);
/* 312 */     this.asm.emitConstantPoolUTF8(buildInternalSignature());
/* 313 */     this.asm.emitConstantPoolNameAndType(sub(this.asm.cpi(), (short)1), this.asm.cpi());
/* 314 */     if (isInterface()) {
/* 315 */       this.asm.emitConstantPoolInterfaceMethodref(this.targetClass, this.asm.cpi());
/*     */     }
/* 317 */     else if (paramBoolean2) {
/* 318 */       this.asm.emitConstantPoolMethodref(s2, this.asm.cpi());
/*     */     } else {
/* 320 */       this.asm.emitConstantPoolMethodref(this.targetClass, this.asm.cpi());
/*     */     } 
/*     */     
/* 323 */     this.targetMethodRef = this.asm.cpi();
/* 324 */     if (paramBoolean1) {
/* 325 */       this.asm.emitConstantPoolUTF8("newInstance");
/*     */     } else {
/* 327 */       this.asm.emitConstantPoolUTF8("invoke");
/*     */     } 
/* 329 */     this.invokeIdx = this.asm.cpi();
/* 330 */     if (paramBoolean1) {
/* 331 */       this.asm.emitConstantPoolUTF8("([Ljava/lang/Object;)Ljava/lang/Object;");
/*     */     } else {
/* 333 */       this.asm
/* 334 */         .emitConstantPoolUTF8("(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
/*     */     } 
/* 336 */     this.invokeDescriptorIdx = this.asm.cpi();
/*     */ 
/*     */     
/* 339 */     this.nonPrimitiveParametersBaseIdx = add(this.asm.cpi(), (short)2);
/* 340 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/* 341 */       Class<?> clazz = paramArrayOfClass1[b];
/* 342 */       if (!isPrimitive(clazz)) {
/* 343 */         this.asm.emitConstantPoolUTF8(getClassName(clazz, false));
/* 344 */         this.asm.emitConstantPoolClass(this.asm.cpi());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 349 */     emitCommonConstantPoolEntries();
/*     */ 
/*     */     
/* 352 */     if (bool) {
/* 353 */       emitBoxingContantPoolEntries();
/*     */     }
/*     */     
/* 356 */     if (this.asm.cpi() != s1) {
/* 357 */       throw new InternalError("Adjust this code (cpi = " + this.asm.cpi() + ", numCPEntries = " + s1 + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 362 */     this.asm.emitShort((short)1);
/*     */ 
/*     */     
/* 365 */     this.asm.emitShort(this.thisClass);
/*     */ 
/*     */     
/* 368 */     this.asm.emitShort(this.superClass);
/*     */ 
/*     */     
/* 371 */     this.asm.emitShort((short)0);
/*     */ 
/*     */     
/* 374 */     this.asm.emitShort((short)0);
/*     */ 
/*     */     
/* 377 */     this.asm.emitShort((short)2);
/*     */     
/* 379 */     emitConstructor();
/* 380 */     emitInvoke();
/*     */ 
/*     */     
/* 383 */     this.asm.emitShort((short)0);
/*     */ 
/*     */     
/* 386 */     byteVector.trim();
/* 387 */     final byte[] bytes = byteVector.getData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     return AccessController.<MagicAccessorImpl>doPrivileged(new PrivilegedAction<MagicAccessorImpl>()
/*     */         {
/*     */           public MagicAccessorImpl run() {
/*     */             try {
/* 397 */               return 
/*     */                 
/* 399 */                 (MagicAccessorImpl)ClassDefiner.defineClass(generatedName, bytes, 0, bytes.length, declaringClass
/*     */ 
/*     */ 
/*     */                   
/* 403 */                   .getClassLoader()).newInstance();
/* 404 */             } catch (InstantiationException|IllegalAccessException instantiationException) {
/* 405 */               throw new InternalError(instantiationException);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void emitInvoke() {
/* 416 */     if (this.parameterTypes.length > 65535) {
/* 417 */       throw new InternalError("Can't handle more than 65535 parameters");
/*     */     }
/*     */ 
/*     */     
/* 421 */     ClassFileAssembler classFileAssembler1 = new ClassFileAssembler();
/* 422 */     if (this.isConstructor) {
/*     */       
/* 424 */       classFileAssembler1.setMaxLocals(2);
/*     */     } else {
/*     */       
/* 427 */       classFileAssembler1.setMaxLocals(3);
/*     */     } 
/*     */     
/* 430 */     short s1 = 0;
/*     */     
/* 432 */     if (this.isConstructor) {
/*     */ 
/*     */ 
/*     */       
/* 436 */       classFileAssembler1.opc_new(this.targetClass);
/* 437 */       classFileAssembler1.opc_dup();
/*     */     } else {
/*     */       
/* 440 */       if (isPrimitive(this.returnType)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 446 */         classFileAssembler1.opc_new(indexForPrimitiveType(this.returnType));
/* 447 */         classFileAssembler1.opc_dup();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 455 */       if (!isStatic()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 465 */         classFileAssembler1.opc_aload_1();
/* 466 */         Label label = new Label();
/* 467 */         classFileAssembler1.opc_ifnonnull(label);
/* 468 */         classFileAssembler1.opc_new(this.nullPointerClass);
/* 469 */         classFileAssembler1.opc_dup();
/* 470 */         classFileAssembler1.opc_invokespecial(this.nullPointerCtorIdx, 0, 0);
/* 471 */         classFileAssembler1.opc_athrow();
/* 472 */         label.bind();
/* 473 */         s1 = classFileAssembler1.getLength();
/* 474 */         classFileAssembler1.opc_aload_1();
/* 475 */         classFileAssembler1.opc_checkcast(this.targetClass);
/*     */       } 
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
/* 495 */     Label label1 = new Label();
/* 496 */     if (this.parameterTypes.length == 0) {
/* 497 */       if (this.isConstructor) {
/* 498 */         classFileAssembler1.opc_aload_1();
/*     */       } else {
/* 500 */         classFileAssembler1.opc_aload_2();
/*     */       } 
/* 502 */       classFileAssembler1.opc_ifnull(label1);
/*     */     } 
/* 504 */     if (this.isConstructor) {
/* 505 */       classFileAssembler1.opc_aload_1();
/*     */     } else {
/* 507 */       classFileAssembler1.opc_aload_2();
/*     */     } 
/* 509 */     classFileAssembler1.opc_arraylength();
/* 510 */     classFileAssembler1.opc_sipush((short)this.parameterTypes.length);
/* 511 */     classFileAssembler1.opc_if_icmpeq(label1);
/* 512 */     classFileAssembler1.opc_new(this.illegalArgumentClass);
/* 513 */     classFileAssembler1.opc_dup();
/* 514 */     classFileAssembler1.opc_invokespecial(this.illegalArgumentCtorIdx, 0, 0);
/* 515 */     classFileAssembler1.opc_athrow();
/* 516 */     label1.bind();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 522 */     short s2 = this.nonPrimitiveParametersBaseIdx;
/* 523 */     Label label2 = null;
/* 524 */     byte b = 1;
/*     */     short s3;
/* 526 */     for (s3 = 0; s3 < this.parameterTypes.length; s3++) {
/* 527 */       Class<?> clazz = this.parameterTypes[s3];
/* 528 */       b = (byte)(b + (byte)typeSizeInStackSlots(clazz));
/* 529 */       if (label2 != null) {
/* 530 */         label2.bind();
/* 531 */         label2 = null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 536 */       if (this.isConstructor) {
/* 537 */         classFileAssembler1.opc_aload_1();
/*     */       } else {
/* 539 */         classFileAssembler1.opc_aload_2();
/*     */       } 
/* 541 */       classFileAssembler1.opc_sipush((short)s3);
/* 542 */       classFileAssembler1.opc_aaload();
/* 543 */       if (isPrimitive(clazz)) {
/*     */ 
/*     */ 
/*     */         
/* 547 */         if (this.isConstructor) {
/* 548 */           classFileAssembler1.opc_astore_2();
/*     */         } else {
/* 550 */           classFileAssembler1.opc_astore_3();
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 570 */         Label label = null;
/* 571 */         label2 = new Label();
/*     */         
/* 573 */         for (byte b1 = 0; b1 < primitiveTypes.length; b1++) {
/* 574 */           Class<?> clazz1 = primitiveTypes[b1];
/* 575 */           if (canWidenTo(clazz1, clazz)) {
/* 576 */             if (label != null) {
/* 577 */               label.bind();
/*     */             }
/*     */             
/* 580 */             if (this.isConstructor) {
/* 581 */               classFileAssembler1.opc_aload_2();
/*     */             } else {
/* 583 */               classFileAssembler1.opc_aload_3();
/*     */             } 
/* 585 */             classFileAssembler1.opc_instanceof(indexForPrimitiveType(clazz1));
/* 586 */             label = new Label();
/* 587 */             classFileAssembler1.opc_ifeq(label);
/* 588 */             if (this.isConstructor) {
/* 589 */               classFileAssembler1.opc_aload_2();
/*     */             } else {
/* 591 */               classFileAssembler1.opc_aload_3();
/*     */             } 
/* 593 */             classFileAssembler1.opc_checkcast(indexForPrimitiveType(clazz1));
/* 594 */             classFileAssembler1.opc_invokevirtual(unboxingMethodForPrimitiveType(clazz1), 0, 
/*     */                 
/* 596 */                 typeSizeInStackSlots(clazz1));
/* 597 */             emitWideningBytecodeForPrimitiveConversion(classFileAssembler1, clazz1, clazz);
/*     */ 
/*     */             
/* 600 */             classFileAssembler1.opc_goto(label2);
/*     */           } 
/*     */         } 
/*     */         
/* 604 */         if (label == null) {
/* 605 */           throw new InternalError("Must have found at least identity conversion");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 613 */         label.bind();
/* 614 */         classFileAssembler1.opc_new(this.illegalArgumentClass);
/* 615 */         classFileAssembler1.opc_dup();
/* 616 */         classFileAssembler1.opc_invokespecial(this.illegalArgumentCtorIdx, 0, 0);
/* 617 */         classFileAssembler1.opc_athrow();
/*     */       } else {
/*     */         
/* 620 */         classFileAssembler1.opc_checkcast(s2);
/* 621 */         s2 = add(s2, (short)2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 626 */     if (label2 != null) {
/* 627 */       label2.bind();
/*     */     }
/*     */     
/* 630 */     s3 = classFileAssembler1.getLength();
/*     */ 
/*     */     
/* 633 */     if (this.isConstructor) {
/* 634 */       classFileAssembler1.opc_invokespecial(this.targetMethodRef, b, 0);
/*     */     }
/* 636 */     else if (isStatic()) {
/* 637 */       classFileAssembler1.opc_invokestatic(this.targetMethodRef, b, 
/*     */           
/* 639 */           typeSizeInStackSlots(this.returnType));
/*     */     }
/* 641 */     else if (isInterface()) {
/* 642 */       if (isPrivate()) {
/* 643 */         classFileAssembler1.opc_invokespecial(this.targetMethodRef, b, 0);
/*     */       } else {
/* 645 */         classFileAssembler1.opc_invokeinterface(this.targetMethodRef, b, b, 
/*     */ 
/*     */             
/* 648 */             typeSizeInStackSlots(this.returnType));
/*     */       } 
/*     */     } else {
/* 651 */       classFileAssembler1.opc_invokevirtual(this.targetMethodRef, b, 
/*     */           
/* 653 */           typeSizeInStackSlots(this.returnType));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 658 */     short s4 = classFileAssembler1.getLength();
/*     */     
/* 660 */     if (!this.isConstructor)
/*     */     {
/* 662 */       if (isPrimitive(this.returnType)) {
/* 663 */         classFileAssembler1.opc_invokespecial(ctorIndexForPrimitiveType(this.returnType), 
/* 664 */             typeSizeInStackSlots(this.returnType), 0);
/*     */       }
/* 666 */       else if (this.returnType == void.class) {
/* 667 */         classFileAssembler1.opc_aconst_null();
/*     */       } 
/*     */     }
/* 670 */     classFileAssembler1.opc_areturn();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 678 */     short s5 = classFileAssembler1.getLength();
/*     */ 
/*     */     
/* 681 */     classFileAssembler1.setStack(1);
/* 682 */     classFileAssembler1.opc_invokespecial(this.toStringIdx, 0, 1);
/* 683 */     classFileAssembler1.opc_new(this.illegalArgumentClass);
/* 684 */     classFileAssembler1.opc_dup_x1();
/* 685 */     classFileAssembler1.opc_swap();
/* 686 */     classFileAssembler1.opc_invokespecial(this.illegalArgumentStringCtorIdx, 1, 0);
/* 687 */     classFileAssembler1.opc_athrow();
/*     */     
/* 689 */     short s6 = classFileAssembler1.getLength();
/*     */ 
/*     */     
/* 692 */     classFileAssembler1.setStack(1);
/* 693 */     classFileAssembler1.opc_new(this.invocationTargetClass);
/* 694 */     classFileAssembler1.opc_dup_x1();
/* 695 */     classFileAssembler1.opc_swap();
/* 696 */     classFileAssembler1.opc_invokespecial(this.invocationTargetCtorIdx, 1, 0);
/* 697 */     classFileAssembler1.opc_athrow();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 703 */     ClassFileAssembler classFileAssembler2 = new ClassFileAssembler();
/*     */     
/* 705 */     classFileAssembler2.emitShort(s1);
/* 706 */     classFileAssembler2.emitShort(s3);
/* 707 */     classFileAssembler2.emitShort(s5);
/* 708 */     classFileAssembler2.emitShort(this.classCastClass);
/*     */     
/* 710 */     classFileAssembler2.emitShort(s1);
/* 711 */     classFileAssembler2.emitShort(s3);
/* 712 */     classFileAssembler2.emitShort(s5);
/* 713 */     classFileAssembler2.emitShort(this.nullPointerClass);
/*     */     
/* 715 */     classFileAssembler2.emitShort(s3);
/* 716 */     classFileAssembler2.emitShort(s4);
/* 717 */     classFileAssembler2.emitShort(s6);
/* 718 */     classFileAssembler2.emitShort(this.throwableClass);
/*     */     
/* 720 */     emitMethod(this.invokeIdx, classFileAssembler1.getMaxLocals(), classFileAssembler1, classFileAssembler2, new short[] { this.invocationTargetClass });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean usesPrimitiveTypes() {
/* 728 */     if (this.returnType.isPrimitive()) {
/* 729 */       return true;
/*     */     }
/* 731 */     for (byte b = 0; b < this.parameterTypes.length; b++) {
/* 732 */       if (this.parameterTypes[b].isPrimitive()) {
/* 733 */         return true;
/*     */       }
/*     */     } 
/* 736 */     return false;
/*     */   }
/*     */   
/*     */   private int numNonPrimitiveParameterTypes() {
/* 740 */     byte b1 = 0;
/* 741 */     for (byte b2 = 0; b2 < this.parameterTypes.length; b2++) {
/* 742 */       if (!this.parameterTypes[b2].isPrimitive()) {
/* 743 */         b1++;
/*     */       }
/*     */     } 
/* 746 */     return b1;
/*     */   }
/*     */   
/*     */   private boolean isInterface() {
/* 750 */     return this.declaringClass.isInterface();
/*     */   }
/*     */   
/*     */   private String buildInternalSignature() {
/* 754 */     StringBuffer stringBuffer = new StringBuffer();
/* 755 */     stringBuffer.append("(");
/* 756 */     for (byte b = 0; b < this.parameterTypes.length; b++) {
/* 757 */       stringBuffer.append(getClassName(this.parameterTypes[b], true));
/*     */     }
/* 759 */     stringBuffer.append(")");
/* 760 */     stringBuffer.append(getClassName(this.returnType, true));
/* 761 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized String generateName(boolean paramBoolean1, boolean paramBoolean2) {
/* 767 */     if (paramBoolean1) {
/* 768 */       if (paramBoolean2) {
/* 769 */         int k = ++serializationConstructorSymnum;
/* 770 */         return "sun/reflect/GeneratedSerializationConstructorAccessor" + k;
/*     */       } 
/* 772 */       int j = ++constructorSymnum;
/* 773 */       return "sun/reflect/GeneratedConstructorAccessor" + j;
/*     */     } 
/*     */     
/* 776 */     int i = ++methodSymnum;
/* 777 */     return "sun/reflect/GeneratedMethodAccessor" + i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/MethodAccessorGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */