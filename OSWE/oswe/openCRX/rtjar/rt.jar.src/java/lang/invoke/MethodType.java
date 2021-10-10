/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.invoke.Invokers;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.MethodTypeForm;
/*      */ import java.lang.invoke.Stable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.invoke.util.BytecodeDescriptor;
/*      */ import sun.invoke.util.VerifyType;
/*      */ import sun.invoke.util.Wrapper;
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
/*      */ public final class MethodType
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 292L;
/*      */   private final Class<?> rtype;
/*      */   private final Class<?>[] ptypes;
/*      */   @Stable
/*      */   private MethodTypeForm form;
/*      */   @Stable
/*      */   private MethodType wrapAlt;
/*      */   @Stable
/*      */   private Invokers invokers;
/*      */   @Stable
/*      */   private String methodDescriptor;
/*      */   static final int MAX_JVM_ARITY = 255;
/*      */   static final int MAX_MH_ARITY = 254;
/*      */   static final int MAX_MH_INVOKER_ARITY = 253;
/*      */   
/*      */   private MethodType(Class<?> paramClass, Class<?>[] paramArrayOfClass, boolean paramBoolean) {
/*  108 */     checkRtype(paramClass);
/*  109 */     checkPtypes(paramArrayOfClass);
/*  110 */     this.rtype = paramClass;
/*      */     
/*  112 */     this.ptypes = paramBoolean ? paramArrayOfClass : (Class[])Arrays.<Class<?>[]>copyOf((Class<?>[][])paramArrayOfClass, paramArrayOfClass.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MethodType(Class<?>[] paramArrayOfClass, Class<?> paramClass) {
/*  121 */     this.rtype = paramClass;
/*  122 */     this.ptypes = paramArrayOfClass;
/*      */   }
/*      */   
/*  125 */   MethodTypeForm form() { return this.form; }
/*  126 */   Class<?> rtype() { return this.rtype; } Class<?>[] ptypes() {
/*  127 */     return this.ptypes;
/*      */   } void setForm(MethodTypeForm paramMethodTypeForm) {
/*  129 */     this.form = paramMethodTypeForm;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkRtype(Class<?> paramClass) {
/*  162 */     Objects.requireNonNull(paramClass);
/*      */   }
/*      */   private static void checkPtype(Class<?> paramClass) {
/*  165 */     Objects.requireNonNull(paramClass);
/*  166 */     if (paramClass == void.class)
/*  167 */       throw MethodHandleStatics.newIllegalArgumentException("parameter type cannot be void"); 
/*      */   }
/*      */   
/*      */   private static int checkPtypes(Class<?>[] paramArrayOfClass) {
/*  171 */     byte b = 0;
/*  172 */     for (Class<?> clazz : paramArrayOfClass) {
/*  173 */       checkPtype(clazz);
/*  174 */       if (clazz == double.class || clazz == long.class) {
/*  175 */         b++;
/*      */       }
/*      */     } 
/*  178 */     checkSlotCount(paramArrayOfClass.length + b);
/*  179 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   static void checkSlotCount(int paramInt) {
/*  184 */     if ((paramInt & 0xFF) != paramInt)
/*  185 */       throw MethodHandleStatics.newIllegalArgumentException("bad parameter count " + paramInt); 
/*      */   }
/*      */   private static IndexOutOfBoundsException newIndexOutOfBoundsException(Object paramObject) {
/*  188 */     if (paramObject instanceof Integer) paramObject = "bad index: " + paramObject; 
/*  189 */     return new IndexOutOfBoundsException(paramObject.toString());
/*      */   }
/*      */   
/*  192 */   static final ConcurrentWeakInternSet<MethodType> internTable = new ConcurrentWeakInternSet<>();
/*      */   
/*  194 */   static final Class<?>[] NO_PTYPES = new Class[0];
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
/*      */   public static MethodType methodType(Class<?> paramClass, Class<?>[] paramArrayOfClass) {
/*  206 */     return makeImpl(paramClass, paramArrayOfClass, false);
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
/*      */   public static MethodType methodType(Class<?> paramClass, List<Class<?>> paramList) {
/*  220 */     boolean bool = false;
/*  221 */     return makeImpl(paramClass, listToArray(paramList), bool);
/*      */   }
/*      */ 
/*      */   
/*      */   private static Class<?>[] listToArray(List<Class<?>> paramList) {
/*  226 */     checkSlotCount(paramList.size());
/*  227 */     return (Class[])paramList.<Class<?>[]>toArray((Class<?>[][])NO_PTYPES);
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
/*      */   public static MethodType methodType(Class<?> paramClass1, Class<?> paramClass2, Class<?>... paramVarArgs) {
/*  243 */     Class[] arrayOfClass = new Class[1 + paramVarArgs.length];
/*  244 */     arrayOfClass[0] = paramClass2;
/*  245 */     System.arraycopy(paramVarArgs, 0, arrayOfClass, 1, paramVarArgs.length);
/*  246 */     return makeImpl(paramClass1, arrayOfClass, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass) {
/*  259 */     return makeImpl(paramClass, NO_PTYPES, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass1, Class<?> paramClass2) {
/*  274 */     return makeImpl(paramClass1, new Class[] { paramClass2 }, true);
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
/*      */   public static MethodType methodType(Class<?> paramClass, MethodType paramMethodType) {
/*  289 */     return makeImpl(paramClass, paramMethodType.ptypes, true);
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
/*      */   static MethodType makeImpl(Class<?> paramClass, Class<?>[] paramArrayOfClass, boolean paramBoolean) {
/*  301 */     MethodType methodType = internTable.get(new MethodType(paramArrayOfClass, paramClass));
/*  302 */     if (methodType != null)
/*  303 */       return methodType; 
/*  304 */     if (paramArrayOfClass.length == 0) {
/*  305 */       paramArrayOfClass = NO_PTYPES; paramBoolean = true;
/*      */     } 
/*  307 */     methodType = new MethodType(paramClass, paramArrayOfClass, paramBoolean);
/*      */     
/*  309 */     methodType.form = MethodTypeForm.findForm(methodType);
/*  310 */     return internTable.add(methodType);
/*      */   }
/*  312 */   private static final MethodType[] objectOnlyTypes = new MethodType[20];
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
/*      */   public static MethodType genericMethodType(int paramInt, boolean paramBoolean) {
/*  328 */     checkSlotCount(paramInt);
/*  329 */     byte b = !paramBoolean ? 0 : 1;
/*  330 */     int i = paramInt * 2 + b;
/*  331 */     if (i < objectOnlyTypes.length) {
/*  332 */       MethodType methodType1 = objectOnlyTypes[i];
/*  333 */       if (methodType1 != null) return methodType1; 
/*      */     } 
/*  335 */     Class[] arrayOfClass = new Class[paramInt + b];
/*  336 */     Arrays.fill((Object[])arrayOfClass, Object.class);
/*  337 */     if (b != 0) arrayOfClass[paramInt] = Object[].class; 
/*  338 */     MethodType methodType = makeImpl(Object.class, arrayOfClass, true);
/*  339 */     if (i < objectOnlyTypes.length) {
/*  340 */       objectOnlyTypes[i] = methodType;
/*      */     }
/*  342 */     return methodType;
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
/*      */   public static MethodType genericMethodType(int paramInt) {
/*  356 */     return genericMethodType(paramInt, false);
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
/*      */   public MethodType changeParameterType(int paramInt, Class<?> paramClass) {
/*  370 */     if (parameterType(paramInt) == paramClass) return this; 
/*  371 */     checkPtype(paramClass);
/*  372 */     Class[] arrayOfClass = (Class[])this.ptypes.clone();
/*  373 */     arrayOfClass[paramInt] = paramClass;
/*  374 */     return makeImpl(this.rtype, arrayOfClass, true);
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
/*      */   public MethodType insertParameterTypes(int paramInt, Class<?>... paramVarArgs) {
/*  389 */     int i = this.ptypes.length;
/*  390 */     if (paramInt < 0 || paramInt > i)
/*  391 */       throw newIndexOutOfBoundsException(Integer.valueOf(paramInt)); 
/*  392 */     int j = checkPtypes(paramVarArgs);
/*  393 */     checkSlotCount(parameterSlotCount() + paramVarArgs.length + j);
/*  394 */     int k = paramVarArgs.length;
/*  395 */     if (k == 0) return this; 
/*  396 */     Class[] arrayOfClass = (Class[])Arrays.<Class<?>[]>copyOfRange((Class<?>[][])this.ptypes, 0, i + k);
/*  397 */     System.arraycopy(arrayOfClass, paramInt, arrayOfClass, paramInt + k, i - paramInt);
/*  398 */     System.arraycopy(paramVarArgs, 0, arrayOfClass, paramInt, k);
/*  399 */     return makeImpl(this.rtype, arrayOfClass, true);
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
/*      */   public MethodType appendParameterTypes(Class<?>... paramVarArgs) {
/*  412 */     return insertParameterTypes(parameterCount(), paramVarArgs);
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
/*      */   public MethodType insertParameterTypes(int paramInt, List<Class<?>> paramList) {
/*  427 */     return insertParameterTypes(paramInt, listToArray(paramList));
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
/*      */   public MethodType appendParameterTypes(List<Class<?>> paramList) {
/*  440 */     return insertParameterTypes(parameterCount(), paramList);
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
/*      */   MethodType replaceParameterTypes(int paramInt1, int paramInt2, Class<?>... paramVarArgs) {
/*  458 */     if (paramInt1 == paramInt2)
/*  459 */       return insertParameterTypes(paramInt1, paramVarArgs); 
/*  460 */     int i = this.ptypes.length;
/*  461 */     if (0 > paramInt1 || paramInt1 > paramInt2 || paramInt2 > i)
/*  462 */       throw newIndexOutOfBoundsException("start=" + paramInt1 + " end=" + paramInt2); 
/*  463 */     int j = paramVarArgs.length;
/*  464 */     if (j == 0)
/*  465 */       return dropParameterTypes(paramInt1, paramInt2); 
/*  466 */     return dropParameterTypes(paramInt1, paramInt2).insertParameterTypes(paramInt1, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodType asSpreaderType(Class<?> paramClass, int paramInt) {
/*  475 */     assert parameterCount() >= paramInt;
/*  476 */     int i = this.ptypes.length - paramInt;
/*  477 */     if (paramInt == 0) return this; 
/*  478 */     if (paramClass == Object[].class) {
/*  479 */       if (isGeneric()) return this; 
/*  480 */       if (i == 0) {
/*      */         
/*  482 */         MethodType methodType = genericMethodType(paramInt);
/*  483 */         if (this.rtype != Object.class) {
/*  484 */           methodType = methodType.changeReturnType(this.rtype);
/*      */         }
/*  486 */         return methodType;
/*      */       } 
/*      */     } 
/*  489 */     Class<?> clazz = paramClass.getComponentType();
/*  490 */     assert clazz != null;
/*  491 */     for (int j = i; j < this.ptypes.length; j++) {
/*  492 */       if (this.ptypes[j] != clazz) {
/*  493 */         Class[] arrayOfClass = (Class[])this.ptypes.clone();
/*  494 */         Arrays.fill((Object[])arrayOfClass, j, this.ptypes.length, clazz);
/*  495 */         return methodType(this.rtype, arrayOfClass);
/*      */       } 
/*      */     } 
/*  498 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Class<?> leadingReferenceParameter() {
/*      */     Class<?> clazz;
/*  506 */     if (this.ptypes.length == 0 || (clazz = this.ptypes[0])
/*  507 */       .isPrimitive())
/*  508 */       throw MethodHandleStatics.newIllegalArgumentException("no leading reference parameter"); 
/*  509 */     return clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodType asCollectorType(Class<?> paramClass, int paramInt) {
/*      */     MethodType methodType;
/*  518 */     assert parameterCount() >= 1;
/*  519 */     assert lastParameterType().isAssignableFrom(paramClass);
/*      */     
/*  521 */     if (paramClass == Object[].class) {
/*  522 */       methodType = genericMethodType(paramInt);
/*  523 */       if (this.rtype != Object.class) {
/*  524 */         methodType = methodType.changeReturnType(this.rtype);
/*      */       }
/*      */     } else {
/*  527 */       Class<?> clazz = paramClass.getComponentType();
/*  528 */       assert clazz != null;
/*  529 */       methodType = methodType(this.rtype, Collections.nCopies(paramInt, clazz));
/*      */     } 
/*  531 */     if (this.ptypes.length == 1) {
/*  532 */       return methodType;
/*      */     }
/*  534 */     return methodType.insertParameterTypes(0, parameterList().subList(0, this.ptypes.length - 1));
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
/*      */   public MethodType dropParameterTypes(int paramInt1, int paramInt2) {
/*      */     Class[] arrayOfClass;
/*  549 */     int i = this.ptypes.length;
/*  550 */     if (0 > paramInt1 || paramInt1 > paramInt2 || paramInt2 > i)
/*  551 */       throw newIndexOutOfBoundsException("start=" + paramInt1 + " end=" + paramInt2); 
/*  552 */     if (paramInt1 == paramInt2) return this;
/*      */     
/*  554 */     if (paramInt1 == 0) {
/*  555 */       if (paramInt2 == i) {
/*      */         
/*  557 */         arrayOfClass = NO_PTYPES;
/*      */       } else {
/*      */         
/*  560 */         arrayOfClass = (Class[])Arrays.<Class<?>[]>copyOfRange((Class<?>[][])this.ptypes, paramInt2, i);
/*      */       }
/*      */     
/*  563 */     } else if (paramInt2 == i) {
/*      */       
/*  565 */       arrayOfClass = (Class[])Arrays.<Class<?>[]>copyOfRange((Class<?>[][])this.ptypes, 0, paramInt1);
/*      */     } else {
/*  567 */       int j = i - paramInt2;
/*  568 */       arrayOfClass = (Class[])Arrays.<Class<?>[]>copyOfRange((Class<?>[][])this.ptypes, 0, paramInt1 + j);
/*  569 */       System.arraycopy(this.ptypes, paramInt2, arrayOfClass, paramInt1, j);
/*      */     } 
/*      */     
/*  572 */     return makeImpl(this.rtype, arrayOfClass, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType changeReturnType(Class<?> paramClass) {
/*  583 */     if (returnType() == paramClass) return this; 
/*  584 */     return makeImpl(paramClass, this.ptypes, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasPrimitives() {
/*  593 */     return this.form.hasPrimitives();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasWrappers() {
/*  604 */     return (unwrap() != this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType erase() {
/*  614 */     return this.form.erasedType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodType basicType() {
/*  624 */     return this.form.basicType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodType invokerType() {
/*  631 */     return insertParameterTypes(0, new Class[] { MethodHandle.class });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType generic() {
/*  642 */     return genericMethodType(parameterCount());
/*      */   }
/*      */   
/*      */   boolean isGeneric() {
/*  646 */     return (this == erase() && !hasPrimitives());
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
/*      */   public MethodType wrap() {
/*  659 */     return hasPrimitives() ? wrapWithPrims(this) : this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType unwrap() {
/*  670 */     MethodType methodType = !hasPrimitives() ? this : wrapWithPrims(this);
/*  671 */     return unwrapWithNoPrims(methodType);
/*      */   }
/*      */   
/*      */   private static MethodType wrapWithPrims(MethodType paramMethodType) {
/*  675 */     assert paramMethodType.hasPrimitives();
/*  676 */     MethodType methodType = paramMethodType.wrapAlt;
/*  677 */     if (methodType == null) {
/*      */       
/*  679 */       methodType = MethodTypeForm.canonicalize(paramMethodType, 2, 2);
/*  680 */       assert methodType != null;
/*  681 */       paramMethodType.wrapAlt = methodType;
/*      */     } 
/*  683 */     return methodType;
/*      */   }
/*      */   
/*      */   private static MethodType unwrapWithNoPrims(MethodType paramMethodType) {
/*  687 */     assert !paramMethodType.hasPrimitives();
/*  688 */     MethodType methodType = paramMethodType.wrapAlt;
/*  689 */     if (methodType == null) {
/*      */       
/*  691 */       methodType = MethodTypeForm.canonicalize(paramMethodType, 3, 3);
/*  692 */       if (methodType == null)
/*  693 */         methodType = paramMethodType; 
/*  694 */       paramMethodType.wrapAlt = methodType;
/*      */     } 
/*  696 */     return methodType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> parameterType(int paramInt) {
/*  706 */     return this.ptypes[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int parameterCount() {
/*  713 */     return this.ptypes.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> returnType() {
/*  720 */     return this.rtype;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Class<?>> parameterList() {
/*  729 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.ptypes.clone()));
/*      */   }
/*      */   
/*      */   Class<?> lastParameterType() {
/*  733 */     int i = this.ptypes.length;
/*  734 */     return (i == 0) ? void.class : this.ptypes[i - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?>[] parameterArray() {
/*  743 */     return (Class[])this.ptypes.clone();
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
/*      */   public boolean equals(Object paramObject) {
/*  755 */     return (this == paramObject || (paramObject instanceof MethodType && equals((MethodType)paramObject)));
/*      */   }
/*      */   
/*      */   private boolean equals(MethodType paramMethodType) {
/*  759 */     return (this.rtype == paramMethodType.rtype && 
/*  760 */       Arrays.equals((Object[])this.ptypes, (Object[])paramMethodType.ptypes));
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
/*      */   public int hashCode() {
/*  775 */     int i = 31 + this.rtype.hashCode();
/*  776 */     for (Class<?> clazz : this.ptypes)
/*  777 */       i = 31 * i + clazz.hashCode(); 
/*  778 */     return i;
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
/*      */   public String toString() {
/*  793 */     StringBuilder stringBuilder = new StringBuilder();
/*  794 */     stringBuilder.append("(");
/*  795 */     for (byte b = 0; b < this.ptypes.length; b++) {
/*  796 */       if (b > 0) stringBuilder.append(","); 
/*  797 */       stringBuilder.append(this.ptypes[b].getSimpleName());
/*      */     } 
/*  799 */     stringBuilder.append(")");
/*  800 */     stringBuilder.append(this.rtype.getSimpleName());
/*  801 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isViewableAs(MethodType paramMethodType, boolean paramBoolean) {
/*  809 */     if (!VerifyType.isNullConversion(returnType(), paramMethodType.returnType(), paramBoolean))
/*  810 */       return false; 
/*  811 */     return parametersAreViewableAs(paramMethodType, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean parametersAreViewableAs(MethodType paramMethodType, boolean paramBoolean) {
/*  816 */     if (this.form == paramMethodType.form && this.form.erasedType == this)
/*  817 */       return true; 
/*  818 */     if (this.ptypes == paramMethodType.ptypes)
/*  819 */       return true; 
/*  820 */     int i = parameterCount();
/*  821 */     if (i != paramMethodType.parameterCount())
/*  822 */       return false; 
/*  823 */     for (byte b = 0; b < i; b++) {
/*  824 */       if (!VerifyType.isNullConversion(paramMethodType.parameterType(b), parameterType(b), paramBoolean))
/*  825 */         return false; 
/*      */     } 
/*  827 */     return true;
/*      */   }
/*      */   
/*      */   boolean isConvertibleTo(MethodType paramMethodType) {
/*  831 */     MethodTypeForm methodTypeForm1 = form();
/*  832 */     MethodTypeForm methodTypeForm2 = paramMethodType.form();
/*  833 */     if (methodTypeForm1 == methodTypeForm2)
/*      */     {
/*  835 */       return true; } 
/*  836 */     if (!canConvert(returnType(), paramMethodType.returnType()))
/*  837 */       return false; 
/*  838 */     Class<?>[] arrayOfClass1 = paramMethodType.ptypes;
/*  839 */     Class<?>[] arrayOfClass2 = this.ptypes;
/*  840 */     if (arrayOfClass1 == arrayOfClass2)
/*  841 */       return true; 
/*      */     int i;
/*  843 */     if ((i = arrayOfClass1.length) != arrayOfClass2.length)
/*  844 */       return false; 
/*  845 */     if (i <= 1) {
/*  846 */       if (i == 1 && !canConvert(arrayOfClass1[0], arrayOfClass2[0]))
/*  847 */         return false; 
/*  848 */       return true;
/*      */     } 
/*  850 */     if ((methodTypeForm1.primitiveParameterCount() == 0 && methodTypeForm1.erasedType == this) || (methodTypeForm2
/*  851 */       .primitiveParameterCount() == 0 && methodTypeForm2.erasedType == paramMethodType)) {
/*      */ 
/*      */       
/*  854 */       assert canConvertParameters(arrayOfClass1, arrayOfClass2);
/*  855 */       return true;
/*      */     } 
/*  857 */     return canConvertParameters(arrayOfClass1, arrayOfClass2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean explicitCastEquivalentToAsType(MethodType paramMethodType) {
/*  865 */     if (this == paramMethodType) return true; 
/*  866 */     if (!explicitCastEquivalentToAsType(this.rtype, paramMethodType.rtype)) {
/*  867 */       return false;
/*      */     }
/*  869 */     Class<?>[] arrayOfClass1 = paramMethodType.ptypes;
/*  870 */     Class<?>[] arrayOfClass2 = this.ptypes;
/*  871 */     if (arrayOfClass2 == arrayOfClass1) {
/*  872 */       return true;
/*      */     }
/*  874 */     assert arrayOfClass2.length == arrayOfClass1.length;
/*  875 */     for (byte b = 0; b < arrayOfClass2.length; b++) {
/*  876 */       if (!explicitCastEquivalentToAsType(arrayOfClass1[b], arrayOfClass2[b])) {
/*  877 */         return false;
/*      */       }
/*      */     } 
/*  880 */     return true;
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
/*      */   private static boolean explicitCastEquivalentToAsType(Class<?> paramClass1, Class<?> paramClass2) {
/*  898 */     if (paramClass1 == paramClass2 || paramClass2 == Object.class || paramClass2 == void.class) return true; 
/*  899 */     if (paramClass1.isPrimitive())
/*      */     {
/*      */       
/*  902 */       return canConvert(paramClass1, paramClass2); } 
/*  903 */     if (paramClass2.isPrimitive())
/*      */     {
/*  905 */       return false;
/*      */     }
/*      */     
/*  908 */     return (!paramClass2.isInterface() || paramClass2.isAssignableFrom(paramClass1));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canConvertParameters(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2) {
/*  913 */     for (byte b = 0; b < paramArrayOfClass1.length; b++) {
/*  914 */       if (!canConvert(paramArrayOfClass1[b], paramArrayOfClass2[b])) {
/*  915 */         return false;
/*      */       }
/*      */     } 
/*  918 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean canConvert(Class<?> paramClass1, Class<?> paramClass2) {
/*  924 */     if (paramClass1 == paramClass2 || paramClass1 == Object.class || paramClass2 == Object.class) return true;
/*      */     
/*  926 */     if (paramClass1.isPrimitive()) {
/*      */ 
/*      */       
/*  929 */       if (paramClass1 == void.class) return true; 
/*  930 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass1);
/*  931 */       if (paramClass2.isPrimitive())
/*      */       {
/*  933 */         return Wrapper.forPrimitiveType(paramClass2).isConvertibleFrom(wrapper);
/*      */       }
/*      */       
/*  936 */       return paramClass2.isAssignableFrom(wrapper.wrapperType());
/*      */     } 
/*  938 */     if (paramClass2.isPrimitive()) {
/*      */       
/*  940 */       if (paramClass2 == void.class) return true; 
/*  941 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  948 */       if (paramClass1.isAssignableFrom(wrapper.wrapperType())) {
/*  949 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  955 */       if (Wrapper.isWrapperType(paramClass1) && wrapper
/*  956 */         .isConvertibleFrom(Wrapper.forWrapperType(paramClass1)))
/*      */       {
/*  958 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  967 */       return false;
/*      */     } 
/*      */     
/*  970 */     return true;
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
/*      */   int parameterSlotCount() {
/*  987 */     return this.form.parameterSlotCount();
/*      */   }
/*      */   
/*      */   Invokers invokers() {
/*  991 */     Invokers invokers = this.invokers;
/*  992 */     if (invokers != null) return invokers; 
/*  993 */     this.invokers = invokers = new Invokers(this);
/*  994 */     return invokers;
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
/*      */ 
/*      */ 
/*      */   
/*      */   int parameterSlotDepth(int paramInt) {
/* 1021 */     if (paramInt < 0 || paramInt > this.ptypes.length)
/* 1022 */       parameterType(paramInt); 
/* 1023 */     return this.form.parameterToArgSlot(paramInt - 1);
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
/*      */   int returnSlotCount() {
/* 1037 */     return this.form.returnSlotCount();
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
/*      */ 
/*      */   
/*      */   public static MethodType fromMethodDescriptorString(String paramString, ClassLoader paramClassLoader) throws IllegalArgumentException, TypeNotPresentException {
/* 1063 */     if (!paramString.startsWith("(") || paramString
/* 1064 */       .indexOf(')') < 0 || paramString
/* 1065 */       .indexOf('.') >= 0)
/* 1066 */       throw MethodHandleStatics.newIllegalArgumentException("not a method descriptor: " + paramString); 
/* 1067 */     List<Class<?>> list = BytecodeDescriptor.parseMethod(paramString, paramClassLoader);
/* 1068 */     Class<?> clazz = list.remove(list.size() - 1);
/* 1069 */     checkSlotCount(list.size());
/* 1070 */     Class[] arrayOfClass = listToArray(list);
/* 1071 */     return makeImpl(clazz, arrayOfClass, true);
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
/*      */   public String toMethodDescriptorString() {
/* 1088 */     String str = this.methodDescriptor;
/* 1089 */     if (str == null) {
/* 1090 */       str = BytecodeDescriptor.unparse(this);
/* 1091 */       this.methodDescriptor = str;
/*      */     } 
/* 1093 */     return str;
/*      */   }
/*      */   
/*      */   static String toFieldDescriptorString(Class<?> paramClass) {
/* 1097 */     return BytecodeDescriptor.unparse(paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1105 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long rtypeOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long ptypesOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1128 */     paramObjectOutputStream.defaultWriteObject();
/* 1129 */     paramObjectOutputStream.writeObject(returnType());
/* 1130 */     paramObjectOutputStream.writeObject(parameterArray());
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1148 */     MethodType_init(void.class, NO_PTYPES);
/*      */     
/* 1150 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1152 */     Class<?> clazz = (Class)paramObjectInputStream.readObject();
/* 1153 */     Class[] arrayOfClass = (Class[])paramObjectInputStream.readObject();
/* 1154 */     arrayOfClass = (Class[])arrayOfClass.clone();
/*      */ 
/*      */     
/* 1157 */     MethodType_init(clazz, arrayOfClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void MethodType_init(Class<?> paramClass, Class<?>[] paramArrayOfClass) {
/* 1164 */     checkRtype(paramClass);
/* 1165 */     checkPtypes(paramArrayOfClass);
/* 1166 */     MethodHandleStatics.UNSAFE.putObject(this, rtypeOffset, paramClass);
/* 1167 */     MethodHandleStatics.UNSAFE.putObject(this, ptypesOffset, paramArrayOfClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 1175 */       rtypeOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("rtype"));
/*      */       
/* 1177 */       ptypesOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("ptypes"));
/* 1178 */     } catch (Exception exception) {
/* 1179 */       throw new Error(exception);
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
/*      */   private Object readResolve() {
/*      */     try {
/* 1193 */       return methodType(this.rtype, this.ptypes);
/*      */     } finally {
/*      */       
/* 1196 */       MethodType_init(void.class, NO_PTYPES);
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
/*      */   private static class ConcurrentWeakInternSet<T>
/*      */   {
/* 1211 */     private final ConcurrentMap<WeakEntry<T>, WeakEntry<T>> map = new ConcurrentHashMap<>();
/* 1212 */     private final ReferenceQueue<T> stale = new ReferenceQueue<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public T get(T param1T) {
/* 1223 */       if (param1T == null) throw new NullPointerException(); 
/* 1224 */       expungeStaleElements();
/*      */       
/* 1226 */       WeakEntry<Object> weakEntry = (WeakEntry)this.map.get(new WeakEntry<>(param1T));
/* 1227 */       if (weakEntry != null) {
/* 1228 */         T t = (T)weakEntry.get();
/* 1229 */         if (t != null) {
/* 1230 */           return t;
/*      */         }
/*      */       } 
/* 1233 */       return null;
/*      */     }
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
/*      */     public T add(T param1T) {
/* 1246 */       if (param1T == null) throw new NullPointerException();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1253 */       WeakEntry<T> weakEntry = new WeakEntry<>(param1T, this.stale);
/*      */       while (true) {
/* 1255 */         expungeStaleElements();
/* 1256 */         WeakEntry<T> weakEntry1 = this.map.putIfAbsent(weakEntry, weakEntry);
/* 1257 */         T t = (weakEntry1 == null) ? param1T : weakEntry1.get();
/* 1258 */         if (t != null)
/* 1259 */           return t; 
/*      */       } 
/*      */     }
/*      */     private void expungeStaleElements() {
/*      */       Reference<? extends T> reference;
/* 1264 */       while ((reference = this.stale.poll()) != null)
/* 1265 */         this.map.remove(reference); 
/*      */     }
/*      */     
/*      */     private static class WeakEntry<T>
/*      */       extends WeakReference<T>
/*      */     {
/*      */       public final int hashcode;
/*      */       
/*      */       public WeakEntry(T param2T, ReferenceQueue<T> param2ReferenceQueue) {
/* 1274 */         super(param2T, param2ReferenceQueue);
/* 1275 */         this.hashcode = param2T.hashCode();
/*      */       }
/*      */       
/*      */       public WeakEntry(T param2T) {
/* 1279 */         super(param2T);
/* 1280 */         this.hashcode = param2T.hashCode();
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean equals(Object param2Object) {
/* 1285 */         if (param2Object instanceof WeakEntry) {
/* 1286 */           Object object = ((WeakEntry<Object>)param2Object).get();
/* 1287 */           T t = get();
/* 1288 */           return (object == null || t == null) ? ((this == param2Object)) : t.equals(object);
/*      */         } 
/* 1290 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public int hashCode() {
/* 1295 */         return this.hashcode;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */