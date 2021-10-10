/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.invoke.MemberName;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleNatives;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import sun.invoke.util.BytecodeDescriptor;
/*      */ import sun.invoke.util.VerifyAccess;
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
/*      */ final class MemberName
/*      */   implements Member, Cloneable
/*      */ {
/*      */   private Class<?> clazz;
/*      */   private String name;
/*      */   private Object type;
/*      */   private int flags;
/*      */   private Object resolution;
/*      */   private static final int MH_INVOKE_MODS = 273;
/*      */   static final int BRIDGE = 64;
/*      */   static final int VARARGS = 128;
/*      */   static final int SYNTHETIC = 4096;
/*      */   static final int ANNOTATION = 8192;
/*      */   static final int ENUM = 16384;
/*      */   static final String CONSTRUCTOR_NAME = "<init>";
/*      */   static final int RECOGNIZED_MODIFIERS = 65535;
/*      */   static final int IS_METHOD = 65536;
/*      */   static final int IS_CONSTRUCTOR = 131072;
/*      */   static final int IS_FIELD = 262144;
/*      */   static final int IS_TYPE = 524288;
/*      */   static final int CALLER_SENSITIVE = 1048576;
/*      */   static final int ALL_ACCESS = 7;
/*      */   static final int ALL_KINDS = 983040;
/*      */   static final int IS_INVOCABLE = 196608;
/*      */   static final int IS_FIELD_OR_METHOD = 327680;
/*      */   static final int SEARCH_ALL_SUPERS = 3145728;
/*      */   
/*      */   public Class<?> getDeclaringClass() {
/*   85 */     return this.clazz;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClassLoader getClassLoader() {
/*   90 */     return this.clazz.getClassLoader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*   99 */     if (this.name == null) {
/*  100 */       expandFromVM();
/*  101 */       if (this.name == null) {
/*  102 */         return null;
/*      */       }
/*      */     } 
/*  105 */     return this.name;
/*      */   }
/*      */   
/*      */   public MethodType getMethodOrFieldType() {
/*  109 */     if (isInvocable())
/*  110 */       return getMethodType(); 
/*  111 */     if (isGetter())
/*  112 */       return MethodType.methodType(getFieldType()); 
/*  113 */     if (isSetter())
/*  114 */       return MethodType.methodType(void.class, getFieldType()); 
/*  115 */     throw new InternalError("not a method or field: " + this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType getMethodType() {
/*  122 */     if (this.type == null) {
/*  123 */       expandFromVM();
/*  124 */       if (this.type == null) {
/*  125 */         return null;
/*      */       }
/*      */     } 
/*  128 */     if (!isInvocable()) {
/*  129 */       throw MethodHandleStatics.newIllegalArgumentException("not invocable, no method type");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  134 */     null = this.type;
/*  135 */     if (null instanceof MethodType) {
/*  136 */       return (MethodType)null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  141 */     synchronized (this) {
/*  142 */       if (this.type instanceof String) {
/*  143 */         String str = (String)this.type;
/*  144 */         MethodType methodType = MethodType.fromMethodDescriptorString(str, getClassLoader());
/*  145 */         this.type = methodType;
/*  146 */       } else if (this.type instanceof Object[]) {
/*  147 */         Object[] arrayOfObject = (Object[])this.type;
/*  148 */         Class[] arrayOfClass = (Class[])arrayOfObject[1];
/*  149 */         Class<?> clazz = (Class)arrayOfObject[0];
/*  150 */         MethodType methodType = MethodType.methodType(clazz, arrayOfClass);
/*  151 */         this.type = methodType;
/*      */       } 
/*      */       
/*  154 */       assert this.type instanceof MethodType : "bad method type " + this.type;
/*      */     } 
/*  156 */     return (MethodType)this.type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodType getInvocationType() {
/*  164 */     MethodType methodType = getMethodOrFieldType();
/*  165 */     if (isConstructor() && getReferenceKind() == 8)
/*  166 */       return methodType.changeReturnType(this.clazz); 
/*  167 */     if (!isStatic())
/*  168 */       return methodType.insertParameterTypes(0, new Class[] { this.clazz }); 
/*  169 */     return methodType;
/*      */   }
/*      */ 
/*      */   
/*      */   public Class<?>[] getParameterTypes() {
/*  174 */     return getMethodType().parameterArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public Class<?> getReturnType() {
/*  179 */     return getMethodType().returnType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getFieldType() {
/*  187 */     if (this.type == null) {
/*  188 */       expandFromVM();
/*  189 */       if (this.type == null) {
/*  190 */         return null;
/*      */       }
/*      */     } 
/*  193 */     if (isInvocable()) {
/*  194 */       throw MethodHandleStatics.newIllegalArgumentException("not a field or nested class, no simple type");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  199 */     null = this.type;
/*  200 */     if (null instanceof Class) {
/*  201 */       return (Class)null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  206 */     synchronized (this) {
/*  207 */       if (this.type instanceof String) {
/*  208 */         String str = (String)this.type;
/*  209 */         MethodType methodType = MethodType.fromMethodDescriptorString("()" + str, getClassLoader());
/*  210 */         Class<?> clazz = methodType.returnType();
/*  211 */         this.type = clazz;
/*      */       } 
/*      */       
/*  214 */       assert this.type instanceof Class : "bad field type " + this.type;
/*      */     } 
/*  216 */     return (Class)this.type;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object getType() {
/*  221 */     return isInvocable() ? getMethodType() : getFieldType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSignature() {
/*  228 */     if (this.type == null) {
/*  229 */       expandFromVM();
/*  230 */       if (this.type == null) {
/*  231 */         return null;
/*      */       }
/*      */     } 
/*  234 */     if (isInvocable()) {
/*  235 */       return BytecodeDescriptor.unparse(getMethodType());
/*      */     }
/*  237 */     return BytecodeDescriptor.unparse(getFieldType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getModifiers() {
/*  244 */     return this.flags & 0xFFFF;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getReferenceKind() {
/*  250 */     return (byte)(this.flags >>> 24 & 0xF);
/*      */   }
/*      */   private boolean referenceKindIsConsistent() {
/*  253 */     byte b = getReferenceKind();
/*  254 */     if (b == 0) return isType(); 
/*  255 */     if (isField()) {
/*  256 */       assert staticIsConsistent();
/*  257 */       assert MethodHandleNatives.refKindIsField(b);
/*  258 */     } else if (isConstructor()) {
/*  259 */       assert b == 8 || b == 7;
/*  260 */     } else if (isMethod()) {
/*  261 */       assert staticIsConsistent();
/*  262 */       assert MethodHandleNatives.refKindIsMethod(b);
/*  263 */       if (this.clazz.isInterface() && 
/*  264 */         !$assertionsDisabled && b != 9 && b != 6 && b != 7 && (b != 5 || 
/*      */ 
/*      */         
/*  267 */         !isObjectPublicMethod()))
/*      */         throw new AssertionError(); 
/*      */     } else {
/*      */       assert false;
/*  271 */     }  return true;
/*      */   }
/*      */   private boolean isObjectPublicMethod() {
/*  274 */     if (this.clazz == Object.class) return true; 
/*  275 */     MethodType methodType = getMethodType();
/*  276 */     if (this.name.equals("toString") && methodType.returnType() == String.class && methodType.parameterCount() == 0)
/*  277 */       return true; 
/*  278 */     if (this.name.equals("hashCode") && methodType.returnType() == int.class && methodType.parameterCount() == 0)
/*  279 */       return true; 
/*  280 */     if (this.name.equals("equals") && methodType.returnType() == boolean.class && methodType.parameterCount() == 1 && methodType.parameterType(0) == Object.class)
/*  281 */       return true; 
/*  282 */     return false;
/*      */   }
/*      */   boolean referenceKindIsConsistentWith(int paramInt) {
/*  285 */     byte b = getReferenceKind();
/*  286 */     if (b == paramInt) return true; 
/*  287 */     switch (paramInt) {
/*      */       
/*      */       case 9:
/*  290 */         assert b == 5 || b == 7 : this;
/*      */         
/*  292 */         return true;
/*      */       
/*      */       case 5:
/*      */       case 8:
/*  296 */         assert b == 7 : this;
/*  297 */         return true;
/*      */     } 
/*  299 */     assert false : this + " != " + MethodHandleNatives.refKindName((byte)paramInt);
/*  300 */     return true;
/*      */   }
/*      */   private boolean staticIsConsistent() {
/*  303 */     byte b = getReferenceKind();
/*  304 */     return (MethodHandleNatives.refKindIsStatic(b) == isStatic() || getModifiers() == 0);
/*      */   }
/*      */   private boolean vminfoIsConsistent() {
/*  307 */     byte b = getReferenceKind();
/*  308 */     assert isResolved();
/*  309 */     Object object1 = MethodHandleNatives.getMemberVMInfo(this);
/*  310 */     assert object1 instanceof Object[];
/*  311 */     long l = ((Long)((Object[])object1)[0]).longValue();
/*  312 */     Object object2 = ((Object[])object1)[1];
/*  313 */     if (MethodHandleNatives.refKindIsField(b)) {
/*  314 */       assert l >= 0L : l + ":" + this;
/*  315 */       assert object2 instanceof Class;
/*      */     } else {
/*  317 */       if (MethodHandleNatives.refKindDoesDispatch(b)) {
/*  318 */         assert l >= 0L : l + ":" + this;
/*      */       } else {
/*  320 */         assert l < 0L : l;
/*  321 */       }  assert object2 instanceof MemberName : object2 + " in " + this;
/*      */     } 
/*  323 */     return true;
/*      */   }
/*      */   
/*      */   private MemberName changeReferenceKind(byte paramByte1, byte paramByte2) {
/*  327 */     assert getReferenceKind() == paramByte2;
/*  328 */     assert MethodHandleNatives.refKindIsValid(paramByte1);
/*  329 */     this.flags += paramByte1 - paramByte2 << 24;
/*  330 */     return this;
/*      */   }
/*      */   
/*      */   private boolean testFlags(int paramInt1, int paramInt2) {
/*  334 */     return ((this.flags & paramInt1) == paramInt2);
/*      */   }
/*      */   private boolean testAllFlags(int paramInt) {
/*  337 */     return testFlags(paramInt, paramInt);
/*      */   }
/*      */   private boolean testAnyFlags(int paramInt) {
/*  340 */     return !testFlags(paramInt, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMethodHandleInvoke() {
/*  348 */     if (testFlags(280, 272) && this.clazz == MethodHandle.class)
/*      */     {
/*  350 */       return isMethodHandleInvokeName(this.name);
/*      */     }
/*  352 */     return false;
/*      */   }
/*      */   public static boolean isMethodHandleInvokeName(String paramString) {
/*  355 */     switch (paramString) {
/*      */       case "invoke":
/*      */       case "invokeExact":
/*  358 */         return true;
/*      */     } 
/*  360 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStatic() {
/*  367 */     return Modifier.isStatic(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isPublic() {
/*  371 */     return Modifier.isPublic(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isPrivate() {
/*  375 */     return Modifier.isPrivate(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isProtected() {
/*  379 */     return Modifier.isProtected(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isFinal() {
/*  383 */     return Modifier.isFinal(this.flags);
/*      */   }
/*      */   
/*      */   public boolean canBeStaticallyBound() {
/*  387 */     return Modifier.isFinal(this.flags | this.clazz.getModifiers());
/*      */   }
/*      */   
/*      */   public boolean isVolatile() {
/*  391 */     return Modifier.isVolatile(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isAbstract() {
/*  395 */     return Modifier.isAbstract(this.flags);
/*      */   }
/*      */   
/*      */   public boolean isNative() {
/*  399 */     return Modifier.isNative(this.flags);
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
/*      */   public boolean isBridge() {
/*  411 */     return testAllFlags(65600);
/*      */   }
/*      */   
/*      */   public boolean isVarargs() {
/*  415 */     return (testAllFlags(128) && isInvocable());
/*      */   }
/*      */   
/*      */   public boolean isSynthetic() {
/*  419 */     return testAllFlags(4096);
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
/*      */   public boolean isInvocable() {
/*  443 */     return testAnyFlags(196608);
/*      */   }
/*      */   
/*      */   public boolean isFieldOrMethod() {
/*  447 */     return testAnyFlags(327680);
/*      */   }
/*      */   
/*      */   public boolean isMethod() {
/*  451 */     return testAllFlags(65536);
/*      */   }
/*      */   
/*      */   public boolean isConstructor() {
/*  455 */     return testAllFlags(131072);
/*      */   }
/*      */   
/*      */   public boolean isField() {
/*  459 */     return testAllFlags(262144);
/*      */   }
/*      */   
/*      */   public boolean isType() {
/*  463 */     return testAllFlags(524288);
/*      */   }
/*      */   
/*      */   public boolean isPackage() {
/*  467 */     return !testAnyFlags(7);
/*      */   }
/*      */   
/*      */   public boolean isCallerSensitive() {
/*  471 */     return testAllFlags(1048576);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAccessibleFrom(Class<?> paramClass) {
/*  476 */     return VerifyAccess.isMemberAccessible(getDeclaringClass(), getDeclaringClass(), this.flags, paramClass, 15);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(Class<?> paramClass, String paramString, Object paramObject, int paramInt) {
/*  486 */     this.clazz = paramClass;
/*  487 */     this.name = paramString;
/*  488 */     this.type = paramObject;
/*  489 */     this.flags = paramInt;
/*  490 */     assert testAnyFlags(983040);
/*  491 */     assert this.resolution == null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandFromVM() {
/*  500 */     if (this.type != null) {
/*      */       return;
/*      */     }
/*  503 */     if (!isResolved()) {
/*      */       return;
/*      */     }
/*  506 */     MethodHandleNatives.expand(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int flagsMods(int paramInt1, int paramInt2, byte paramByte) {
/*  511 */     assert (paramInt1 & 0xFFFF) == 0;
/*  512 */     assert (paramInt2 & 0xFFFF0000) == 0;
/*  513 */     assert (paramByte & 0xFFFFFFF0) == 0;
/*  514 */     return paramInt1 | paramInt2 | paramByte << 24;
/*      */   }
/*      */   
/*      */   public MemberName(Method paramMethod) {
/*  518 */     this(paramMethod, false);
/*      */   }
/*      */   
/*      */   public MemberName(Method paramMethod, boolean paramBoolean) {
/*  522 */     paramMethod.getClass();
/*      */     
/*  524 */     MethodHandleNatives.init(this, paramMethod);
/*  525 */     if (this.clazz == null) {
/*  526 */       if (paramMethod.getDeclaringClass() == MethodHandle.class && 
/*  527 */         isMethodHandleInvokeName(paramMethod.getName())) {
/*      */ 
/*      */ 
/*      */         
/*  531 */         MethodType methodType = MethodType.methodType(paramMethod.getReturnType(), paramMethod.getParameterTypes());
/*  532 */         int i = flagsMods(65536, paramMethod.getModifiers(), (byte)5);
/*  533 */         init(MethodHandle.class, paramMethod.getName(), methodType, i);
/*  534 */         if (isMethodHandleInvoke())
/*      */           return; 
/*      */       } 
/*  537 */       throw new LinkageError(paramMethod.toString());
/*      */     } 
/*  539 */     assert isResolved() && this.clazz != null;
/*  540 */     this.name = paramMethod.getName();
/*  541 */     if (this.type == null)
/*  542 */       this.type = new Object[] { paramMethod.getReturnType(), paramMethod.getParameterTypes() }; 
/*  543 */     if (paramBoolean) {
/*  544 */       if (isAbstract())
/*  545 */         throw new AbstractMethodError(toString()); 
/*  546 */       if (getReferenceKind() == 5) {
/*  547 */         changeReferenceKind((byte)7, (byte)5);
/*  548 */       } else if (getReferenceKind() == 9) {
/*      */         
/*  550 */         changeReferenceKind((byte)7, (byte)9);
/*      */       } 
/*      */     } 
/*      */   } public MemberName asSpecial() {
/*  554 */     switch (getReferenceKind()) { case 7:
/*  555 */         return this;
/*  556 */       case 5: return clone().changeReferenceKind((byte)7, (byte)5);
/*  557 */       case 9: return clone().changeReferenceKind((byte)7, (byte)9);
/*  558 */       case 8: return clone().changeReferenceKind((byte)7, (byte)8); }
/*      */     
/*  560 */     throw new IllegalArgumentException(toString());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName asConstructor() {
/*  566 */     switch (getReferenceKind()) { case 7:
/*  567 */         return clone().changeReferenceKind((byte)8, (byte)7);
/*  568 */       case 8: return this; }
/*      */     
/*  570 */     throw new IllegalArgumentException(toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName asNormalOriginal() {
/*  581 */     byte b = this.clazz.isInterface() ? 9 : 5;
/*  582 */     byte b1 = getReferenceKind();
/*  583 */     byte b2 = b1;
/*  584 */     MemberName memberName = this;
/*  585 */     switch (b1) {
/*      */       case 5:
/*      */       case 7:
/*      */       case 9:
/*  589 */         b2 = b;
/*      */         break;
/*      */     } 
/*  592 */     if (b2 == b1)
/*  593 */       return this; 
/*  594 */     memberName = clone().changeReferenceKind(b2, b1);
/*  595 */     assert referenceKindIsConsistentWith(memberName.getReferenceKind());
/*  596 */     return memberName;
/*      */   }
/*      */ 
/*      */   
/*      */   public MemberName(Constructor<?> paramConstructor) {
/*  601 */     paramConstructor.getClass();
/*      */     
/*  603 */     MethodHandleNatives.init(this, paramConstructor);
/*  604 */     assert isResolved() && this.clazz != null;
/*  605 */     this.name = "<init>";
/*  606 */     if (this.type == null) {
/*  607 */       this.type = new Object[] { void.class, paramConstructor.getParameterTypes() };
/*      */     }
/*      */   }
/*      */   
/*      */   public MemberName(Field paramField) {
/*  612 */     this(paramField, false);
/*      */   }
/*      */   
/*      */   public MemberName(Field paramField, boolean paramBoolean) {
/*  616 */     paramField.getClass();
/*      */     
/*  618 */     MethodHandleNatives.init(this, paramField);
/*  619 */     assert isResolved() && this.clazz != null;
/*  620 */     this.name = paramField.getName();
/*  621 */     this.type = paramField.getType();
/*      */     
/*  623 */     byte b = getReferenceKind();
/*  624 */     assert b == (isStatic() ? 2 : 1);
/*  625 */     if (paramBoolean)
/*  626 */       changeReferenceKind((byte)(b + 2), b); 
/*      */   }
/*      */   
/*      */   public boolean isGetter() {
/*  630 */     return MethodHandleNatives.refKindIsGetter(getReferenceKind());
/*      */   }
/*      */   public boolean isSetter() {
/*  633 */     return MethodHandleNatives.refKindIsSetter(getReferenceKind());
/*      */   }
/*      */   public MemberName asSetter() {
/*  636 */     byte b1 = getReferenceKind();
/*  637 */     assert MethodHandleNatives.refKindIsGetter(b1);
/*      */     
/*  639 */     byte b2 = (byte)(b1 + 2);
/*  640 */     return clone().changeReferenceKind(b2, b1);
/*      */   }
/*      */   
/*      */   public MemberName(Class<?> paramClass) {
/*  644 */     init(paramClass.getDeclaringClass(), paramClass.getSimpleName(), paramClass, 
/*  645 */         flagsMods(524288, paramClass.getModifiers(), (byte)0));
/*  646 */     initResolved(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MemberName makeMethodHandleInvoke(String paramString, MethodType paramMethodType) {
/*  656 */     return makeMethodHandleInvoke(paramString, paramMethodType, 4369);
/*      */   }
/*      */   static MemberName makeMethodHandleInvoke(String paramString, MethodType paramMethodType, int paramInt) {
/*  659 */     MemberName memberName = new MemberName(MethodHandle.class, paramString, paramMethodType, (byte)5);
/*  660 */     memberName.flags |= paramInt;
/*  661 */     assert memberName.isMethodHandleInvoke() : memberName;
/*  662 */     return memberName;
/*      */   }
/*      */ 
/*      */   
/*      */   MemberName() {}
/*      */ 
/*      */   
/*      */   protected MemberName clone() {
/*      */     try {
/*  671 */       return (MemberName)super.clone();
/*  672 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  673 */       throw MethodHandleStatics.newInternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName getDefinition() {
/*  681 */     if (!isResolved()) throw new IllegalStateException("must be resolved: " + this); 
/*  682 */     if (isType()) return this; 
/*  683 */     MemberName memberName = clone();
/*  684 */     memberName.clazz = null;
/*  685 */     memberName.type = null;
/*  686 */     memberName.name = null;
/*  687 */     memberName.resolution = memberName;
/*  688 */     memberName.expandFromVM();
/*  689 */     assert memberName.getName().equals(getName());
/*  690 */     return memberName;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  695 */     return Objects.hash(new Object[] { this.clazz, Byte.valueOf(getReferenceKind()), this.name, getType() });
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  699 */     return (paramObject instanceof MemberName && equals((MemberName)paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(MemberName paramMemberName) {
/*  708 */     if (this == paramMemberName) return true; 
/*  709 */     if (paramMemberName == null) return false; 
/*  710 */     return (this.clazz == paramMemberName.clazz && 
/*  711 */       getReferenceKind() == paramMemberName.getReferenceKind() && 
/*  712 */       Objects.equals(this.name, paramMemberName.name) && 
/*  713 */       Objects.equals(getType(), paramMemberName.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName(Class<?> paramClass1, String paramString, Class<?> paramClass2, byte paramByte) {
/*  723 */     init(paramClass1, paramString, paramClass2, flagsMods(262144, 0, paramByte));
/*  724 */     initResolved(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName(Class<?> paramClass, String paramString, MethodType paramMethodType, byte paramByte) {
/*  734 */     int i = (paramString != null && paramString.equals("<init>")) ? 131072 : 65536;
/*  735 */     init(paramClass, paramString, paramMethodType, flagsMods(i, 0, paramByte));
/*  736 */     initResolved(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MemberName(byte paramByte, Class<?> paramClass, String paramString, Object paramObject) {
/*      */     int i;
/*  743 */     if (MethodHandleNatives.refKindIsField(paramByte)) {
/*  744 */       i = 262144;
/*  745 */       if (!(paramObject instanceof Class))
/*  746 */         throw MethodHandleStatics.newIllegalArgumentException("not a field type"); 
/*  747 */     } else if (MethodHandleNatives.refKindIsMethod(paramByte)) {
/*  748 */       i = 65536;
/*  749 */       if (!(paramObject instanceof MethodType))
/*  750 */         throw MethodHandleStatics.newIllegalArgumentException("not a method type"); 
/*  751 */     } else if (paramByte == 8) {
/*  752 */       i = 131072;
/*  753 */       if (!(paramObject instanceof MethodType) || 
/*  754 */         !"<init>".equals(paramString))
/*  755 */         throw MethodHandleStatics.newIllegalArgumentException("not a constructor type or name"); 
/*      */     } else {
/*  757 */       throw MethodHandleStatics.newIllegalArgumentException("bad reference kind " + paramByte);
/*      */     } 
/*  759 */     init(paramClass, paramString, paramObject, flagsMods(i, 0, paramByte));
/*  760 */     initResolved(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasReceiverTypeDispatch() {
/*  765 */     return MethodHandleNatives.refKindDoesDispatch(getReferenceKind());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isResolved() {
/*  774 */     return (this.resolution == null);
/*      */   }
/*      */   
/*      */   private void initResolved(boolean paramBoolean) {
/*  778 */     assert this.resolution == null;
/*  779 */     if (!paramBoolean)
/*  780 */       this.resolution = this; 
/*  781 */     assert isResolved() == paramBoolean;
/*      */   }
/*      */   void checkForTypeAlias(Class<?> paramClass) {
/*      */     Class<?> clazz;
/*  785 */     if (isInvocable()) {
/*      */       MethodType methodType;
/*  787 */       if (this.type instanceof MethodType) {
/*  788 */         methodType = (MethodType)this.type;
/*      */       } else {
/*  790 */         this.type = methodType = getMethodType();
/*  791 */       }  if (methodType.erase() == methodType)
/*  792 */         return;  if (VerifyAccess.isTypeVisible(methodType, paramClass))
/*  793 */         return;  throw new LinkageError("bad method type alias: " + methodType + " not visible from " + paramClass);
/*      */     } 
/*      */     
/*  796 */     if (this.type instanceof Class) {
/*  797 */       clazz = (Class)this.type;
/*      */     } else {
/*  799 */       this.type = clazz = getFieldType();
/*  800 */     }  if (VerifyAccess.isTypeVisible(clazz, paramClass))
/*  801 */       return;  throw new LinkageError("bad field type alias: " + clazz + " not visible from " + paramClass);
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
/*  816 */     if (isType()) {
/*  817 */       return this.type.toString();
/*      */     }
/*  819 */     StringBuilder stringBuilder = new StringBuilder();
/*  820 */     if (getDeclaringClass() != null) {
/*  821 */       stringBuilder.append(getName(this.clazz));
/*  822 */       stringBuilder.append('.');
/*      */     } 
/*  824 */     String str = getName();
/*  825 */     stringBuilder.append((str == null) ? "*" : str);
/*  826 */     Object object = getType();
/*  827 */     if (!isInvocable()) {
/*  828 */       stringBuilder.append('/');
/*  829 */       stringBuilder.append((object == null) ? "*" : getName(object));
/*      */     } else {
/*  831 */       stringBuilder.append((object == null) ? "(*)*" : getName(object));
/*      */     } 
/*  833 */     byte b = getReferenceKind();
/*  834 */     if (b != 0) {
/*  835 */       stringBuilder.append('/');
/*  836 */       stringBuilder.append(MethodHandleNatives.refKindName(b));
/*      */     } 
/*      */     
/*  839 */     return stringBuilder.toString();
/*      */   }
/*      */   private static String getName(Object paramObject) {
/*  842 */     if (paramObject instanceof Class)
/*  843 */       return ((Class)paramObject).getName(); 
/*  844 */     return String.valueOf(paramObject);
/*      */   }
/*      */   
/*      */   public IllegalAccessException makeAccessException(String paramString, Object paramObject) {
/*  848 */     paramString = paramString + ": " + toString();
/*  849 */     if (paramObject != null) paramString = paramString + ", from " + paramObject; 
/*  850 */     return new IllegalAccessException(paramString);
/*      */   }
/*      */   private String message() {
/*  853 */     if (isResolved())
/*  854 */       return "no access"; 
/*  855 */     if (isConstructor())
/*  856 */       return "no such constructor"; 
/*  857 */     if (isMethod()) {
/*  858 */       return "no such method";
/*      */     }
/*  860 */     return "no such field";
/*      */   } public ReflectiveOperationException makeAccessException() {
/*      */     NoSuchFieldException noSuchFieldException;
/*  863 */     String str = message() + ": " + toString();
/*      */     
/*  865 */     if (isResolved() || (!(this.resolution instanceof NoSuchMethodError) && !(this.resolution instanceof NoSuchFieldError))) {
/*      */       
/*  867 */       IllegalAccessException illegalAccessException = new IllegalAccessException(str);
/*  868 */     } else if (isConstructor()) {
/*  869 */       NoSuchMethodException noSuchMethodException = new NoSuchMethodException(str);
/*  870 */     } else if (isMethod()) {
/*  871 */       NoSuchMethodException noSuchMethodException = new NoSuchMethodException(str);
/*      */     } else {
/*  873 */       noSuchFieldException = new NoSuchFieldException(str);
/*  874 */     }  if (this.resolution instanceof Throwable)
/*  875 */       noSuchFieldException.initCause((Throwable)this.resolution); 
/*  876 */     return noSuchFieldException;
/*      */   }
/*      */ 
/*      */   
/*      */   static Factory getFactory() {
/*  881 */     return Factory.INSTANCE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Factory
/*      */   {
/*  888 */     static Factory INSTANCE = new Factory();
/*      */     
/*  890 */     private static int ALLOWED_FLAGS = 983040;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     List<MemberName> getMembers(Class<?> param1Class1, String param1String, Object param1Object, int param1Int, Class<?> param1Class2) {
/*  896 */       param1Int &= ALLOWED_FLAGS;
/*  897 */       String str = null;
/*  898 */       if (param1Object != null) {
/*  899 */         str = BytecodeDescriptor.unparse(param1Object);
/*  900 */         if (str.startsWith("(")) {
/*  901 */           param1Int &= 0xFFF3FFFF;
/*      */         } else {
/*  903 */           param1Int &= 0xFFF4FFFF;
/*      */         } 
/*      */       } 
/*  906 */       boolean bool = (param1String == null) ? true : ((param1Object == null) ? true : true);
/*  907 */       MemberName[] arrayOfMemberName = newMemberBuffer(bool);
/*  908 */       int i = 0;
/*  909 */       ArrayList<MemberName[]> arrayList = null;
/*  910 */       int j = 0;
/*      */       while (true) {
/*  912 */         j = MethodHandleNatives.getMembers(param1Class1, param1String, str, param1Int, param1Class2, i, arrayOfMemberName);
/*      */ 
/*      */ 
/*      */         
/*  916 */         if (j <= arrayOfMemberName.length) {
/*  917 */           if (j < 0) j = 0; 
/*  918 */           i += j;
/*      */           
/*      */           break;
/*      */         } 
/*  922 */         i += arrayOfMemberName.length;
/*  923 */         int k = j - arrayOfMemberName.length;
/*  924 */         if (arrayList == null) arrayList = new ArrayList(1); 
/*  925 */         arrayList.add(arrayOfMemberName);
/*  926 */         int m = arrayOfMemberName.length;
/*  927 */         m = Math.max(m, k);
/*  928 */         m = Math.max(m, i / 4);
/*  929 */         arrayOfMemberName = newMemberBuffer(Math.min(8192, m));
/*      */       } 
/*  931 */       ArrayList<? super MemberName> arrayList1 = new ArrayList(i);
/*  932 */       if (arrayList != null) {
/*  933 */         for (MemberName[] arrayOfMemberName1 : arrayList) {
/*  934 */           Collections.addAll(arrayList1, arrayOfMemberName1);
/*      */         }
/*      */       }
/*  937 */       arrayList1.addAll(Arrays.<MemberName>asList(arrayOfMemberName).subList(0, j));
/*      */ 
/*      */ 
/*      */       
/*  941 */       if (param1Object != null && param1Object != str)
/*  942 */         for (Iterator<? super MemberName> iterator = arrayList1.iterator(); iterator.hasNext(); ) {
/*  943 */           MemberName memberName = iterator.next();
/*  944 */           if (!param1Object.equals(memberName.getType())) {
/*  945 */             iterator.remove();
/*      */           }
/*      */         }  
/*  948 */       return (List)arrayList1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MemberName resolve(byte param1Byte, MemberName param1MemberName, Class<?> param1Class) {
/*  957 */       MemberName memberName = param1MemberName.clone();
/*  958 */       assert param1Byte == memberName.getReferenceKind();
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
/*      */       try {
/*  975 */         memberName = MethodHandleNatives.resolve(memberName, param1Class);
/*  976 */         memberName.checkForTypeAlias(memberName.getDeclaringClass());
/*  977 */         memberName.resolution = null;
/*  978 */       } catch (ClassNotFoundException|LinkageError classNotFoundException) {
/*      */         
/*  980 */         assert !memberName.isResolved();
/*  981 */         memberName.resolution = classNotFoundException;
/*  982 */         return memberName;
/*      */       } 
/*  984 */       assert memberName.referenceKindIsConsistent();
/*  985 */       memberName.initResolved(true);
/*  986 */       assert memberName.vminfoIsConsistent();
/*  987 */       return memberName;
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
/*      */     public <NoSuchMemberException extends ReflectiveOperationException> MemberName resolveOrFail(byte param1Byte, MemberName param1MemberName, Class<?> param1Class, Class<NoSuchMemberException> param1Class1) throws IllegalAccessException, NoSuchMemberException {
/* 1000 */       MemberName memberName = resolve(param1Byte, param1MemberName, param1Class);
/* 1001 */       if (memberName.isResolved())
/* 1002 */         return memberName; 
/* 1003 */       ReflectiveOperationException reflectiveOperationException = memberName.makeAccessException();
/* 1004 */       if (reflectiveOperationException instanceof IllegalAccessException) throw (IllegalAccessException)reflectiveOperationException; 
/* 1005 */       throw (ReflectiveOperationException)param1Class1.cast(reflectiveOperationException);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MemberName resolveOrNull(byte param1Byte, MemberName param1MemberName, Class<?> param1Class) {
/* 1015 */       MemberName memberName = resolve(param1Byte, param1MemberName, param1Class);
/* 1016 */       if (memberName.isResolved())
/* 1017 */         return memberName; 
/* 1018 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getMethods(Class<?> param1Class1, boolean param1Boolean, Class<?> param1Class2) {
/* 1027 */       return getMethods(param1Class1, param1Boolean, null, null, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getMethods(Class<?> param1Class1, boolean param1Boolean, String param1String, MethodType param1MethodType, Class<?> param1Class2) {
/* 1037 */       int i = 0x10000 | (param1Boolean ? 3145728 : 0);
/* 1038 */       return getMembers(param1Class1, param1String, param1MethodType, i, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getConstructors(Class<?> param1Class1, Class<?> param1Class2) {
/* 1045 */       return getMembers(param1Class1, null, null, 131072, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getFields(Class<?> param1Class1, boolean param1Boolean, Class<?> param1Class2) {
/* 1054 */       return getFields(param1Class1, param1Boolean, null, null, param1Class2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getFields(Class<?> param1Class1, boolean param1Boolean, String param1String, Class<?> param1Class2, Class<?> param1Class3) {
/* 1064 */       int i = 0x40000 | (param1Boolean ? 3145728 : 0);
/* 1065 */       return getMembers(param1Class1, param1String, param1Class2, i, param1Class3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<MemberName> getNestedTypes(Class<?> param1Class1, boolean param1Boolean, Class<?> param1Class2) {
/* 1074 */       int i = 0x80000 | (param1Boolean ? 3145728 : 0);
/* 1075 */       return getMembers(param1Class1, null, null, i, param1Class2);
/*      */     }
/*      */     private static MemberName[] newMemberBuffer(int param1Int) {
/* 1078 */       MemberName[] arrayOfMemberName = new MemberName[param1Int];
/*      */       
/* 1080 */       for (byte b = 0; b < param1Int; b++)
/* 1081 */         arrayOfMemberName[b] = new MemberName(); 
/* 1082 */       return arrayOfMemberName;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MemberName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */