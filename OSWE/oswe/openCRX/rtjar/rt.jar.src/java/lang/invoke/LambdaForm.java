/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.lang.annotation.ElementType;
/*      */ import java.lang.annotation.Retention;
/*      */ import java.lang.annotation.RetentionPolicy;
/*      */ import java.lang.annotation.Target;
/*      */ import java.lang.invoke.DirectMethodHandle;
/*      */ import java.lang.invoke.DontInline;
/*      */ import java.lang.invoke.InvokerBytecodeGenerator;
/*      */ import java.lang.invoke.Invokers;
/*      */ import java.lang.invoke.LambdaForm;
/*      */ import java.lang.invoke.LambdaForm.Hidden;
/*      */ import java.lang.invoke.LambdaFormEditor;
/*      */ import java.lang.invoke.MemberName;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleImpl;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.MethodTypeForm;
/*      */ import java.lang.invoke.SimpleMethodHandle;
/*      */ import java.lang.invoke.Stable;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class LambdaForm
/*      */ {
/*      */   final int arity;
/*      */   final int result;
/*      */   final boolean forceInline;
/*      */   final MethodHandle customized;
/*      */   @Stable
/*      */   final Name[] names;
/*      */   final String debugName;
/*      */   MemberName vmentry;
/*      */   private boolean isCompiled;
/*      */   volatile Object transformCache;
/*      */   public static final int VOID_RESULT = -1;
/*      */   public static final int LAST_RESULT = -2;
/*      */   private static final boolean USE_PREDEFINED_INTERPRET_METHODS = true;
/*      */   
/*      */   enum BasicType
/*      */   {
/*  135 */     L_TYPE('L', Object.class, Wrapper.OBJECT),
/*  136 */     I_TYPE('I', int.class, Wrapper.INT),
/*  137 */     J_TYPE('J', long.class, Wrapper.LONG),
/*  138 */     F_TYPE('F', float.class, Wrapper.FLOAT),
/*  139 */     D_TYPE('D', double.class, Wrapper.DOUBLE),
/*  140 */     V_TYPE('V', void.class, Wrapper.VOID);
/*      */     
/*  142 */     static final BasicType[] ALL_TYPES = values();
/*  143 */     static final BasicType[] ARG_TYPES = Arrays.<BasicType>copyOf(ALL_TYPES, ALL_TYPES.length - 1);
/*      */     
/*  145 */     static final int ARG_TYPE_LIMIT = ARG_TYPES.length;
/*  146 */     static final int TYPE_LIMIT = ALL_TYPES.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final char btChar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Class<?> btClass;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final Wrapper btWrapper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  232 */       assert checkBasicType();
/*      */     } BasicType(char param1Char, Class<?> param1Class, Wrapper param1Wrapper) { this.btChar = param1Char; this.btClass = param1Class; this.btWrapper = param1Wrapper; } char basicTypeChar() { return this.btChar; } Class<?> basicTypeClass() { return this.btClass; } Wrapper basicTypeWrapper() { return this.btWrapper; } int basicTypeSlots() { return this.btWrapper.stackSlots(); } static BasicType basicType(byte param1Byte) { return ALL_TYPES[param1Byte]; } static BasicType basicType(char param1Char) { switch (param1Char) { case 'L': return L_TYPE;case 'I': return I_TYPE;case 'J': return J_TYPE;case 'F': return F_TYPE;case 'D': return D_TYPE;case 'V': return V_TYPE;case 'B': case 'C': case 'S': case 'Z': return I_TYPE; }  throw MethodHandleStatics.newInternalError("Unknown type char: '" + param1Char + "'"); } static BasicType basicType(Wrapper param1Wrapper) { char c = param1Wrapper.basicTypeChar(); return basicType(c); } static BasicType basicType(Class<?> param1Class) { if (!param1Class.isPrimitive()) return L_TYPE;  return basicType(Wrapper.forPrimitiveType(param1Class)); } static char basicTypeChar(Class<?> param1Class) { return (basicType(param1Class)).btChar; } static BasicType[] basicTypes(List<Class<?>> param1List) { BasicType[] arrayOfBasicType = new BasicType[param1List.size()]; for (byte b = 0; b < arrayOfBasicType.length; b++) arrayOfBasicType[b] = basicType(param1List.get(b));  return arrayOfBasicType; } static BasicType[] basicTypes(String param1String) { BasicType[] arrayOfBasicType = new BasicType[param1String.length()]; for (byte b = 0; b < arrayOfBasicType.length; b++) arrayOfBasicType[b] = basicType(param1String.charAt(b));  return arrayOfBasicType; } static byte[] basicTypesOrd(BasicType[] param1ArrayOfBasicType) { byte[] arrayOfByte = new byte[param1ArrayOfBasicType.length]; for (byte b = 0; b < param1ArrayOfBasicType.length; b++) arrayOfByte[b] = (byte)param1ArrayOfBasicType[b].ordinal();  return arrayOfByte; } static boolean isBasicTypeChar(char param1Char) { return ("LIJFDV".indexOf(param1Char) >= 0); } static boolean isArgBasicTypeChar(char param1Char) { return ("LIJFD".indexOf(param1Char) >= 0); }
/*  234 */     private static boolean checkBasicType() { byte b; for (b = 0; b < ARG_TYPE_LIMIT; b++) {
/*  235 */         assert ARG_TYPES[b].ordinal() == b;
/*  236 */         assert ARG_TYPES[b] == ALL_TYPES[b];
/*      */       } 
/*  238 */       for (b = 0; b < TYPE_LIMIT; b++) {
/*  239 */         assert ALL_TYPES[b].ordinal() == b;
/*      */       }
/*  241 */       assert ALL_TYPES[TYPE_LIMIT - 1] == V_TYPE;
/*  242 */       assert !Arrays.<BasicType>asList(ARG_TYPES).contains(V_TYPE);
/*  243 */       return true; }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   LambdaForm(String paramString, int paramInt1, Name[] paramArrayOfName, int paramInt2) {
/*  249 */     this(paramString, paramInt1, paramArrayOfName, paramInt2, true, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   LambdaForm(String paramString, int paramInt1, Name[] paramArrayOfName, int paramInt2, boolean paramBoolean, MethodHandle paramMethodHandle)
/*      */   {
/*  774 */     this.invocationCounter = 0; assert namesOK(paramInt1, paramArrayOfName); this.arity = paramInt1; this.result = fixResult(paramInt2, paramArrayOfName); this.names = (Name[])paramArrayOfName.clone(); this.debugName = fixDebugName(paramString); this.forceInline = paramBoolean; this.customized = paramMethodHandle; int i = normalize(); if (i > 253) { assert i <= 255; compileToBytecode(); }  } LambdaForm(String paramString, int paramInt, Name[] paramArrayOfName) { this(paramString, paramInt, paramArrayOfName, -2, true, null); } LambdaForm(String paramString, int paramInt, Name[] paramArrayOfName, boolean paramBoolean) { this(paramString, paramInt, paramArrayOfName, -2, paramBoolean, null); } LambdaForm(String paramString, Name[] paramArrayOfName1, Name[] paramArrayOfName2, Name paramName) { this(paramString, paramArrayOfName1.length, buildNames(paramArrayOfName1, paramArrayOfName2, paramName), -2, true, null); } LambdaForm(String paramString, Name[] paramArrayOfName1, Name[] paramArrayOfName2, Name paramName, boolean paramBoolean) { this(paramString, paramArrayOfName1.length, buildNames(paramArrayOfName1, paramArrayOfName2, paramName), -2, paramBoolean, null); } private static Name[] buildNames(Name[] paramArrayOfName1, Name[] paramArrayOfName2, Name paramName) { int i = paramArrayOfName1.length; int j = i + paramArrayOfName2.length + ((paramName == null) ? 0 : 1); Name[] arrayOfName = Arrays.<Name>copyOf(paramArrayOfName1, j); System.arraycopy(paramArrayOfName2, 0, arrayOfName, i, paramArrayOfName2.length); if (paramName != null) arrayOfName[j - 1] = paramName;  return arrayOfName; } private static Name[] buildEmptyNames(int paramInt, String paramString) { assert isValidSignature(paramString); int i = paramInt + 1; if (paramInt < 0 || paramString.length() != i + 1) throw new IllegalArgumentException("bad arity for " + paramString);  byte b1 = (BasicType.basicType(paramString.charAt(i)) == BasicType.V_TYPE) ? 0 : 1; Name[] arrayOfName = arguments(b1, paramString.substring(0, paramInt)); for (byte b2 = 0; b2 < b1; b2++) { Name name = new Name(constantZero(BasicType.basicType(paramString.charAt(i + b2))), new Object[0]); arrayOfName[paramInt + b2] = name.newIndex(paramInt + b2); }  return arrayOfName; } private static int fixResult(int paramInt, Name[] paramArrayOfName) { if (paramInt == -2) paramInt = paramArrayOfName.length - 1;  if (paramInt >= 0 && (paramArrayOfName[paramInt]).type == BasicType.V_TYPE) paramInt = -1;  return paramInt; } private static String fixDebugName(String paramString) { if (DEBUG_NAME_COUNTERS != null) { Integer integer; int i = paramString.indexOf('_'); int j = paramString.length(); if (i < 0) i = j;  String str = paramString.substring(0, i); synchronized (DEBUG_NAME_COUNTERS) { integer = DEBUG_NAME_COUNTERS.get(str); if (integer == null) integer = Integer.valueOf(0);  DEBUG_NAME_COUNTERS.put(str, Integer.valueOf(integer.intValue() + 1)); }  StringBuilder stringBuilder = new StringBuilder(str); stringBuilder.append('_'); int k = stringBuilder.length(); stringBuilder.append(integer.intValue()); for (int m = stringBuilder.length() - k; m < 3; m++) stringBuilder.insert(k, '0');  if (i < j) { i++; while (i < j && Character.isDigit(paramString.charAt(i))) i++;  if (i < j && paramString.charAt(i) == '_') i++;  if (i < j) stringBuilder.append('_').append(paramString, i, j);  }  return stringBuilder.toString(); }  return paramString; } private static boolean namesOK(int paramInt, Name[] paramArrayOfName) { for (byte b = 0; b < paramArrayOfName.length; b++) { Name name = paramArrayOfName[b]; assert name != null : "n is null"; if (b < paramInt) { assert name.isParam() : name + " is not param at " + b; } else { assert !name.isParam() : name + " is param at " + b; }  }  return true; } LambdaForm customize(MethodHandle paramMethodHandle) { LambdaForm lambdaForm = new LambdaForm(this.debugName, this.arity, this.names, this.result, this.forceInline, paramMethodHandle); if (COMPILE_THRESHOLD > 0 && this.isCompiled) lambdaForm.compileToBytecode();  lambdaForm.transformCache = this; return lambdaForm; } LambdaForm uncustomize() { if (this.customized == null) return this;  assert this.transformCache != null; LambdaForm lambdaForm = (LambdaForm)this.transformCache; if (COMPILE_THRESHOLD > 0 && this.isCompiled) lambdaForm.compileToBytecode();  return lambdaForm; } private int normalize() { Name[] arrayOfName = null; int i = 0; int j = 0; int k; for (k = 0; k < this.names.length; k++) { Name name = this.names[k]; if (!name.initIndex(k)) { if (arrayOfName == null) { arrayOfName = (Name[])this.names.clone(); j = k; }  this.names[k] = name.cloneWithIndex(k); }  if (name.arguments != null && i < name.arguments.length) i = name.arguments.length;  }  if (arrayOfName != null) { k = this.arity; if (k <= j) k = j + 1;  for (int n = k; n < this.names.length; n++) { Name name = this.names[n].replaceNames(arrayOfName, this.names, j, n); this.names[n] = name.newIndex(n); }  }  assert nameRefsAreLegal(); k = Math.min(this.arity, 10); boolean bool = false; int m; for (m = 0; m < k; m++) { Name name1 = this.names[m], name2 = internArgument(name1); if (name1 != name2) { this.names[m] = name2; bool = true; }  }  if (bool) for (m = this.arity; m < this.names.length; m++) this.names[m].internArguments();   assert nameRefsAreLegal(); return i; } boolean nameRefsAreLegal() { assert this.arity >= 0 && this.arity <= this.names.length; assert this.result >= -1 && this.result < this.names.length; int i; for (i = 0; i < this.arity; i++) { Name name = this.names[i]; assert name.index() == i : Arrays.asList((T[])new Integer[] { Integer.valueOf(name.index()), Integer.valueOf(i) }); assert name.isParam(); }  for (i = this.arity; i < this.names.length; i++) { Name name = this.names[i]; assert name.index() == i; for (Object object : name.arguments) { if (object instanceof Name) { Name name1 = (Name)object; short s = name1.index; assert 0 <= s && s < this.names.length : name.debugString() + ": 0 <= i2 && i2 < names.length: 0 <= " + s + " < " + this.names.length; assert this.names[s] == name1 : Arrays.asList((T[])new Object[] { "-1-", Integer.valueOf(i), "-2-", name.debugString(), "-3-", Integer.valueOf(s), "-4-", name1.debugString(), "-5-", this.names[s].debugString(), "-6-", this }); assert s < i; }  }  }  return true; } BasicType returnType() { if (this.result < 0) return BasicType.V_TYPE;  Name name = this.names[this.result]; return name.type; } private LambdaForm(String paramString) { this.invocationCounter = 0; assert isValidSignature(paramString); this.arity = signatureArity(paramString); this.result = (signatureReturn(paramString) == BasicType.V_TYPE) ? -1 : this.arity; this.names = buildEmptyNames(this.arity, paramString); this.debugName = "LF.zero"; this.forceInline = true; this.customized = null; assert nameRefsAreLegal(); assert isEmpty(); assert paramString.equals(basicTypeSignature()) : paramString + " != " + basicTypeSignature(); }
/*      */   BasicType parameterType(int paramInt) { return (parameter(paramInt)).type; }
/*      */   Name parameter(int paramInt) { assert paramInt < this.arity; Name name = this.names[paramInt]; assert name.isParam(); return name; }
/*      */   Object parameterConstraint(int paramInt) { return (parameter(paramInt)).constraint; }
/*      */   int arity() { return this.arity; }
/*      */   int expressionCount() { return this.names.length - this.arity; }
/*  780 */   MethodType methodType() { return signatureType(basicTypeSignature()); } final String basicTypeSignature() { StringBuilder stringBuilder = new StringBuilder(arity() + 3); byte b; int i; for (b = 0, i = arity(); b < i; b++) stringBuilder.append(parameterType(b).basicTypeChar());  return stringBuilder.append('_').append(returnType().basicTypeChar()).toString(); } static int signatureArity(String paramString) { assert isValidSignature(paramString); return paramString.indexOf('_'); } @Hidden @DontInline Object interpretWithArguments(Object... paramVarArgs) throws Throwable { if (TRACE_INTERPRETER)
/*  781 */       return interpretWithArgumentsTracing(paramVarArgs); 
/*  782 */     checkInvocationCounter();
/*  783 */     assert arityCheck(paramVarArgs);
/*  784 */     Object[] arrayOfObject = Arrays.copyOf(paramVarArgs, this.names.length);
/*  785 */     for (int i = paramVarArgs.length; i < arrayOfObject.length; i++) {
/*  786 */       arrayOfObject[i] = interpretName(this.names[i], arrayOfObject);
/*      */     }
/*  788 */     Object object = (this.result < 0) ? null : arrayOfObject[this.result];
/*  789 */     assert resultCheck(paramVarArgs, object);
/*  790 */     return object; } static BasicType signatureReturn(String paramString) { return BasicType.basicType(paramString.charAt(signatureArity(paramString) + 1)); } static boolean isValidSignature(String paramString) { int i = paramString.indexOf('_'); if (i < 0) return false;  int j = paramString.length(); if (j != i + 2) return false;  for (byte b = 0; b < j; b++) { if (b != i) { char c = paramString.charAt(b); if (c == 'V') return (b == j - 1 && i == j - 2);  if (!BasicType.isArgBasicTypeChar(c)) return false;  }  }  return true; } static MethodType signatureType(String paramString) { Class[] arrayOfClass = new Class[signatureArity(paramString)]; for (byte b = 0; b < arrayOfClass.length; b++) arrayOfClass[b] = (BasicType.basicType(paramString.charAt(b))).btClass;  Class<?> clazz = (signatureReturn(paramString)).btClass; return MethodType.methodType(clazz, arrayOfClass); } public void prepare() { if (COMPILE_THRESHOLD == 0 && !this.isCompiled) compileToBytecode();  if (this.vmentry != null) return;  LambdaForm lambdaForm = getPreparedForm(basicTypeSignature()); this.vmentry = lambdaForm.vmentry; } MemberName compileToBytecode() { if (this.vmentry != null && this.isCompiled) return this.vmentry;  MethodType methodType = methodType(); assert this.vmentry == null || this.vmentry.getMethodType().basicType().equals(methodType); try { this.vmentry = InvokerBytecodeGenerator.generateCustomizedCode(this, methodType); if (TRACE_INTERPRETER) traceInterpreter("compileToBytecode", this);  this.isCompiled = true; return this.vmentry; } catch (Error|Exception error) { throw MethodHandleStatics.newInternalError(toString(), error); }  } private static void computeInitialPreparedForms() { for (MemberName memberName : MemberName.getFactory().getMethods(LambdaForm.class, false, null, null, null)) { if (!memberName.isStatic() || !memberName.isPackage()) continue;  MethodType methodType = memberName.getMethodType(); if (methodType.parameterCount() > 0 && methodType.parameterType(0) == MethodHandle.class && memberName.getName().startsWith("interpret_")) { String str = basicTypeSignature(methodType); assert memberName.getName().equals("interpret" + str.substring(str.indexOf('_'))); LambdaForm lambdaForm = new LambdaForm(str); lambdaForm.vmentry = memberName; lambdaForm = methodType.form().setCachedLambdaForm(6, lambdaForm); }  }  } static Object interpret_L(MethodHandle paramMethodHandle) throws Throwable { Object[] arrayOfObject = { paramMethodHandle }; String str = null; assert argumentTypesMatch(str = "L_L", arrayOfObject); Object object = paramMethodHandle.form.interpretWithArguments(arrayOfObject); assert returnTypesMatch(str, arrayOfObject, object); return object; } static Object interpret_L(MethodHandle paramMethodHandle, Object paramObject) throws Throwable { Object[] arrayOfObject = { paramMethodHandle, paramObject }; String str = null; assert argumentTypesMatch(str = "LL_L", arrayOfObject); Object object = paramMethodHandle.form.interpretWithArguments(arrayOfObject); assert returnTypesMatch(str, arrayOfObject, object); return object; } static Object interpret_L(MethodHandle paramMethodHandle, Object paramObject1, Object paramObject2) throws Throwable { Object[] arrayOfObject = { paramMethodHandle, paramObject1, paramObject2 }; String str = null; assert argumentTypesMatch(str = "LLL_L", arrayOfObject); Object object = paramMethodHandle.form.interpretWithArguments(arrayOfObject); assert returnTypesMatch(str, arrayOfObject, object); return object; } private static LambdaForm getPreparedForm(String paramString) { MethodType methodType = signatureType(paramString); LambdaForm lambdaForm = methodType.form().cachedLambdaForm(6); if (lambdaForm != null) return lambdaForm;  assert isValidSignature(paramString); lambdaForm = new LambdaForm(paramString); lambdaForm.vmentry = InvokerBytecodeGenerator.generateLambdaFormInterpreterEntryPoint(paramString); return methodType.form().setCachedLambdaForm(6, lambdaForm); }
/*      */   private static boolean argumentTypesMatch(String paramString, Object[] paramArrayOfObject) { int i = signatureArity(paramString); assert paramArrayOfObject.length == i : "av.length == arity: av.length=" + paramArrayOfObject.length + ", arity=" + i; assert paramArrayOfObject[0] instanceof MethodHandle : "av[0] not instace of MethodHandle: " + paramArrayOfObject[false]; MethodHandle methodHandle = (MethodHandle)paramArrayOfObject[0]; MethodType methodType = methodHandle.type(); assert methodType.parameterCount() == i - 1; for (byte b = 0; b < paramArrayOfObject.length; b++) { Class<?> clazz = (b == 0) ? MethodHandle.class : methodType.parameterType(b - 1); assert valueMatches(BasicType.basicType(paramString.charAt(b)), clazz, paramArrayOfObject[b]); }  return true; }
/*      */   private static boolean valueMatches(BasicType paramBasicType, Class<?> paramClass, Object paramObject) { if (paramClass == void.class) paramBasicType = BasicType.V_TYPE;  assert paramBasicType == BasicType.basicType(paramClass) : paramBasicType + " == basicType(" + paramClass + ")=" + BasicType.basicType(paramClass); switch (paramBasicType) { case I_TYPE: assert checkInt(paramClass, paramObject) : "checkInt(" + paramClass + "," + paramObject + ")";case J_TYPE: assert paramObject instanceof Long : "instanceof Long: " + paramObject;case F_TYPE: assert paramObject instanceof Float : "instanceof Float: " + paramObject;case D_TYPE: assert paramObject instanceof Double : "instanceof Double: " + paramObject;case L_TYPE: assert checkRef(paramClass, paramObject) : "checkRef(" + paramClass + "," + paramObject + ")";case V_TYPE: return true; }  assert false; }
/*      */   private static boolean returnTypesMatch(String paramString, Object[] paramArrayOfObject, Object paramObject) { MethodHandle methodHandle = (MethodHandle)paramArrayOfObject[0]; return valueMatches(signatureReturn(paramString), methodHandle.type().returnType(), paramObject); }
/*      */   private static boolean checkInt(Class<?> paramClass, Object paramObject) { assert paramObject instanceof Integer; if (paramClass == int.class) return true;  Wrapper wrapper = Wrapper.forBasicType(paramClass); assert wrapper.isSubwordOrInt(); Object object = Wrapper.INT.wrap(wrapper.wrap(paramObject)); return paramObject.equals(object); }
/*      */   private static boolean checkRef(Class<?> paramClass, Object paramObject) { assert !paramClass.isPrimitive(); if (paramObject == null) return true;  if (paramClass.isInterface()) return true;  return paramClass.isInstance(paramObject); }
/*      */   private static final int COMPILE_THRESHOLD = Math.max(-1, MethodHandleStatics.COMPILE_THRESHOLD); private int invocationCounter; static final int INTERNED_ARGUMENT_LIMIT = 10;
/*  797 */   @Hidden @DontInline Object interpretName(Name paramName, Object[] paramArrayOfObject) throws Throwable { if (TRACE_INTERPRETER)
/*  798 */       traceInterpreter("| interpretName", paramName.debugString(), (Object[])null); 
/*  799 */     Object[] arrayOfObject = Arrays.copyOf(paramName.arguments, paramName.arguments.length, Object[].class);
/*  800 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*  801 */       Object object = arrayOfObject[b];
/*  802 */       if (object instanceof Name) {
/*  803 */         int i = ((Name)object).index();
/*  804 */         assert this.names[i] == object;
/*  805 */         object = paramArrayOfObject[i];
/*  806 */         arrayOfObject[b] = object;
/*      */       } 
/*      */     } 
/*  809 */     return paramName.function.invokeWithArguments(arrayOfObject); }
/*      */ 
/*      */   
/*      */   private void checkInvocationCounter() {
/*  813 */     if (COMPILE_THRESHOLD != 0 && this.invocationCounter < COMPILE_THRESHOLD) {
/*      */       
/*  815 */       this.invocationCounter++;
/*  816 */       if (this.invocationCounter >= COMPILE_THRESHOLD)
/*      */       {
/*  818 */         compileToBytecode(); } 
/*      */     } 
/*      */   }
/*      */   Object interpretWithArgumentsTracing(Object... paramVarArgs) throws Throwable {
/*      */     Object object;
/*  823 */     traceInterpreter("[ interpretWithArguments", this, paramVarArgs);
/*  824 */     if (this.invocationCounter < COMPILE_THRESHOLD) {
/*  825 */       int i = this.invocationCounter++;
/*  826 */       traceInterpreter("| invocationCounter", Integer.valueOf(i));
/*  827 */       if (this.invocationCounter >= COMPILE_THRESHOLD) {
/*  828 */         compileToBytecode();
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  833 */       assert arityCheck(paramVarArgs);
/*  834 */       Object[] arrayOfObject = Arrays.copyOf(paramVarArgs, this.names.length);
/*  835 */       for (int i = paramVarArgs.length; i < arrayOfObject.length; i++) {
/*  836 */         arrayOfObject[i] = interpretName(this.names[i], arrayOfObject);
/*      */       }
/*  838 */       object = (this.result < 0) ? null : arrayOfObject[this.result];
/*  839 */     } catch (Throwable throwable) {
/*  840 */       traceInterpreter("] throw =>", throwable);
/*  841 */       throw throwable;
/*      */     } 
/*  843 */     traceInterpreter("] return =>", object);
/*  844 */     return object;
/*      */   }
/*      */   
/*      */   static void traceInterpreter(String paramString, Object paramObject, Object... paramVarArgs) {
/*  848 */     if (TRACE_INTERPRETER)
/*  849 */       System.out.println("LFI: " + paramString + " " + ((paramObject != null) ? (String)paramObject : "") + ((paramVarArgs != null && paramVarArgs.length != 0) ? (String)Arrays.<Object>asList(paramVarArgs) : "")); 
/*      */   }
/*      */   
/*      */   static void traceInterpreter(String paramString, Object paramObject) {
/*  853 */     traceInterpreter(paramString, paramObject, (Object[])null);
/*      */   }
/*      */   private boolean arityCheck(Object[] paramArrayOfObject) {
/*  856 */     assert paramArrayOfObject.length == this.arity : this.arity + "!=" + Arrays.asList((T[])paramArrayOfObject) + ".length";
/*      */     
/*  858 */     assert paramArrayOfObject[0] instanceof MethodHandle : "not MH: " + paramArrayOfObject[false];
/*  859 */     MethodHandle methodHandle = (MethodHandle)paramArrayOfObject[0];
/*  860 */     assert methodHandle.internalForm() == this;
/*      */     
/*  862 */     argumentTypesMatch(basicTypeSignature(), paramArrayOfObject);
/*  863 */     return true;
/*      */   }
/*      */   private boolean resultCheck(Object[] paramArrayOfObject, Object paramObject) {
/*  866 */     MethodHandle methodHandle = (MethodHandle)paramArrayOfObject[0];
/*  867 */     MethodType methodType = methodHandle.type();
/*  868 */     assert valueMatches(returnType(), methodType.returnType(), paramObject);
/*  869 */     return true;
/*      */   }
/*      */   
/*      */   private boolean isEmpty() {
/*  873 */     if (this.result < 0)
/*  874 */       return (this.names.length == this.arity); 
/*  875 */     if (this.result == this.arity && this.names.length == this.arity + 1) {
/*  876 */       return this.names[this.arity].isConstantZero();
/*      */     }
/*  878 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  882 */     StringBuilder stringBuilder = new StringBuilder(this.debugName + "=Lambda(");
/*  883 */     for (byte b = 0; b < this.names.length; b++) {
/*  884 */       if (b == this.arity) stringBuilder.append(")=>{"); 
/*  885 */       Name name = this.names[b];
/*  886 */       if (b >= this.arity) stringBuilder.append("\n    "); 
/*  887 */       stringBuilder.append(name.paramString());
/*  888 */       if (b < this.arity) {
/*  889 */         if (b + 1 < this.arity) stringBuilder.append(",");
/*      */       
/*      */       } else {
/*  892 */         stringBuilder.append("=").append(name.exprString());
/*  893 */         stringBuilder.append(";");
/*      */       } 
/*  895 */     }  if (this.arity == this.names.length) stringBuilder.append(")=>{"); 
/*  896 */     stringBuilder.append((this.result < 0) ? "void" : this.names[this.result]).append("}");
/*  897 */     if (TRACE_INTERPRETER) {
/*      */       
/*  899 */       stringBuilder.append(":").append(basicTypeSignature());
/*  900 */       stringBuilder.append("/").append(this.vmentry);
/*      */     } 
/*  902 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  907 */     return (paramObject instanceof LambdaForm && equals((LambdaForm)paramObject));
/*      */   }
/*      */   public boolean equals(LambdaForm paramLambdaForm) {
/*  910 */     if (this.result != paramLambdaForm.result) return false; 
/*  911 */     return Arrays.equals((Object[])this.names, (Object[])paramLambdaForm.names);
/*      */   }
/*      */   public int hashCode() {
/*  914 */     return this.result + 31 * Arrays.hashCode((Object[])this.names);
/*      */   }
/*      */   LambdaFormEditor editor() {
/*  917 */     return LambdaFormEditor.lambdaFormEditor(this);
/*      */   }
/*      */   
/*      */   boolean contains(Name paramName) {
/*  921 */     int i = paramName.index();
/*  922 */     if (i >= 0) {
/*  923 */       return (i < this.names.length && paramName.equals(this.names[i]));
/*      */     }
/*  925 */     for (int j = this.arity; j < this.names.length; j++) {
/*  926 */       if (paramName.equals(this.names[j]))
/*  927 */         return true; 
/*      */     } 
/*  929 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   LambdaForm addArguments(int paramInt, BasicType... paramVarArgs) {
/*  934 */     int i = paramInt + 1;
/*  935 */     assert i <= this.arity;
/*  936 */     int j = this.names.length;
/*  937 */     int k = paramVarArgs.length;
/*  938 */     Name[] arrayOfName = Arrays.<Name>copyOf(this.names, j + k);
/*  939 */     int m = this.arity + k;
/*  940 */     int n = this.result;
/*  941 */     if (n >= i) {
/*  942 */       n += k;
/*      */     }
/*      */     
/*  945 */     System.arraycopy(this.names, i, arrayOfName, i + k, j - i);
/*  946 */     for (byte b = 0; b < k; b++) {
/*  947 */       arrayOfName[i + b] = new Name(paramVarArgs[b]);
/*      */     }
/*  949 */     return new LambdaForm(this.debugName, m, arrayOfName, n);
/*      */   }
/*      */   
/*      */   LambdaForm addArguments(int paramInt, List<Class<?>> paramList) {
/*  953 */     return addArguments(paramInt, BasicType.basicTypes(paramList));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   LambdaForm permuteArguments(int paramInt, int[] paramArrayOfint, BasicType[] paramArrayOfBasicType) {
/*  959 */     int i = this.names.length;
/*  960 */     int j = paramArrayOfBasicType.length;
/*  961 */     int k = paramArrayOfint.length;
/*  962 */     assert paramInt + k == this.arity;
/*  963 */     assert permutedTypesMatch(paramArrayOfint, paramArrayOfBasicType, this.names, paramInt);
/*  964 */     byte b = 0;
/*      */     
/*  966 */     for (; b < k && paramArrayOfint[b] == b; b++);
/*  967 */     Name[] arrayOfName = new Name[i - k + j];
/*  968 */     System.arraycopy(this.names, 0, arrayOfName, 0, paramInt + b);
/*      */     
/*  970 */     int m = i - this.arity;
/*  971 */     System.arraycopy(this.names, paramInt + k, arrayOfName, paramInt + j, m);
/*  972 */     int n = arrayOfName.length - m;
/*  973 */     int i1 = this.result;
/*  974 */     if (i1 >= 0) {
/*  975 */       if (i1 < paramInt + k) {
/*      */         
/*  977 */         i1 = paramArrayOfint[i1 - paramInt];
/*      */       } else {
/*  979 */         i1 = i1 - k + j;
/*      */       } 
/*      */     }
/*      */     int i2;
/*  983 */     for (i2 = b; i2 < k; i2++) {
/*  984 */       Name name1 = this.names[paramInt + i2];
/*  985 */       int i3 = paramArrayOfint[i2];
/*      */       
/*  987 */       Name name2 = arrayOfName[paramInt + i3];
/*  988 */       if (name2 == null) {
/*  989 */         arrayOfName[paramInt + i3] = name2 = new Name(paramArrayOfBasicType[i3]);
/*      */       } else {
/*  991 */         assert name2.type == paramArrayOfBasicType[i3];
/*  992 */       }  for (int i4 = n; i4 < arrayOfName.length; i4++) {
/*  993 */         arrayOfName[i4] = arrayOfName[i4].replaceName(name1, name2);
/*      */       }
/*      */     } 
/*      */     
/*  997 */     for (i2 = paramInt + b; i2 < n; i2++) {
/*  998 */       if (arrayOfName[i2] == null)
/*  999 */         arrayOfName[i2] = argument(i2, paramArrayOfBasicType[i2 - paramInt]); 
/*      */     } 
/* 1001 */     for (i2 = this.arity; i2 < this.names.length; i2++) {
/* 1002 */       int i3 = i2 - this.arity + n;
/*      */       
/* 1004 */       Name name1 = this.names[i2];
/* 1005 */       Name name2 = arrayOfName[i3];
/* 1006 */       if (name1 != name2) {
/* 1007 */         for (int i4 = i3 + 1; i4 < arrayOfName.length; i4++) {
/* 1008 */           arrayOfName[i4] = arrayOfName[i4].replaceName(name1, name2);
/*      */         }
/*      */       }
/*      */     } 
/* 1012 */     return new LambdaForm(this.debugName, n, arrayOfName, i1);
/*      */   }
/*      */   
/*      */   static boolean permutedTypesMatch(int[] paramArrayOfint, BasicType[] paramArrayOfBasicType, Name[] paramArrayOfName, int paramInt) {
/* 1016 */     int i = paramArrayOfBasicType.length;
/* 1017 */     int j = paramArrayOfint.length;
/* 1018 */     for (byte b = 0; b < j; b++) {
/* 1019 */       assert paramArrayOfName[paramInt + b].isParam();
/* 1020 */       assert (paramArrayOfName[paramInt + b]).type == paramArrayOfBasicType[paramArrayOfint[b]];
/*      */     } 
/* 1022 */     return true;
/*      */   }
/*      */   static class NamedFunction { final MemberName member;
/*      */     @Stable
/*      */     MethodHandle resolvedHandle;
/*      */     @Stable
/*      */     MethodHandle invoker;
/*      */     
/*      */     NamedFunction(MethodHandle param1MethodHandle) {
/* 1031 */       this(param1MethodHandle.internalMemberName(), param1MethodHandle);
/*      */     }
/*      */     NamedFunction(MemberName param1MemberName, MethodHandle param1MethodHandle) {
/* 1034 */       this.member = param1MemberName;
/* 1035 */       this.resolvedHandle = param1MethodHandle;
/*      */     }
/*      */ 
/*      */     
/*      */     NamedFunction(MethodType param1MethodType) {
/* 1040 */       assert param1MethodType == param1MethodType.basicType() : param1MethodType;
/* 1041 */       if (param1MethodType.parameterSlotCount() < 253) {
/* 1042 */         this.resolvedHandle = param1MethodType.invokers().basicInvoker();
/* 1043 */         this.member = this.resolvedHandle.internalMemberName();
/*      */       } else {
/*      */         
/* 1046 */         this.member = Invokers.invokeBasicMethod(param1MethodType);
/*      */       } 
/* 1048 */       assert isInvokeBasic(this.member);
/*      */     }
/*      */     
/*      */     private static boolean isInvokeBasic(MemberName param1MemberName) {
/* 1052 */       return (param1MemberName != null && param1MemberName
/* 1053 */         .getDeclaringClass() == MethodHandle.class && "invokeBasic"
/* 1054 */         .equals(param1MemberName.getName()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NamedFunction(Method param1Method) {
/* 1062 */       this(new MemberName(param1Method));
/*      */     }
/*      */     NamedFunction(Field param1Field) {
/* 1065 */       this(new MemberName(param1Field));
/*      */     }
/*      */     NamedFunction(MemberName param1MemberName) {
/* 1068 */       this.member = param1MemberName;
/* 1069 */       this.resolvedHandle = null;
/*      */     }
/*      */     
/*      */     MethodHandle resolvedHandle() {
/* 1073 */       if (this.resolvedHandle == null) resolve(); 
/* 1074 */       return this.resolvedHandle;
/*      */     }
/*      */     
/*      */     void resolve() {
/* 1078 */       this.resolvedHandle = DirectMethodHandle.make(this.member);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1083 */       if (this == param1Object) return true; 
/* 1084 */       if (param1Object == null) return false; 
/* 1085 */       if (!(param1Object instanceof NamedFunction)) return false; 
/* 1086 */       NamedFunction namedFunction = (NamedFunction)param1Object;
/* 1087 */       return (this.member != null && this.member.equals(namedFunction.member));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1092 */       if (this.member != null)
/* 1093 */         return this.member.hashCode(); 
/* 1094 */       return super.hashCode();
/*      */     }
/*      */ 
/*      */     
/*      */     static void initializeInvokers() {
/* 1099 */       for (MemberName memberName : MemberName.getFactory().getMethods(NamedFunction.class, false, null, null, null)) {
/* 1100 */         if (!memberName.isStatic() || !memberName.isPackage())
/* 1101 */           continue;  MethodType methodType = memberName.getMethodType();
/* 1102 */         if (methodType.equals(INVOKER_METHOD_TYPE) && memberName
/* 1103 */           .getName().startsWith("invoke_")) {
/* 1104 */           String str = memberName.getName().substring("invoke_".length());
/* 1105 */           int i = LambdaForm.signatureArity(str);
/* 1106 */           MethodType methodType1 = MethodType.genericMethodType(i);
/* 1107 */           if (LambdaForm.signatureReturn(str) == LambdaForm.BasicType.V_TYPE)
/* 1108 */             methodType1 = methodType1.changeReturnType(void.class); 
/* 1109 */           MethodTypeForm methodTypeForm = methodType1.form();
/* 1110 */           methodTypeForm.setCachedMethodHandle(1, DirectMethodHandle.make(memberName));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Hidden
/*      */     static Object invoke__V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1120 */       assert arityCheck(0, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1121 */       param1MethodHandle.invokeBasic();
/* 1122 */       return null;
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_L_V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1126 */       assert arityCheck(1, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1127 */       param1MethodHandle.invokeBasic(param1ArrayOfObject[0]);
/* 1128 */       return null;
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LL_V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1132 */       assert arityCheck(2, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1133 */       param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1]);
/* 1134 */       return null;
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLL_V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1138 */       assert arityCheck(3, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1139 */       param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2]);
/* 1140 */       return null;
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLLL_V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1144 */       assert arityCheck(4, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1145 */       param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2], param1ArrayOfObject[3]);
/* 1146 */       return null;
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLLLL_V(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1150 */       assert arityCheck(5, void.class, param1MethodHandle, param1ArrayOfObject);
/* 1151 */       param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2], param1ArrayOfObject[3], param1ArrayOfObject[4]);
/* 1152 */       return null;
/*      */     }
/*      */     
/*      */     @Hidden
/*      */     static Object invoke__L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1157 */       assert arityCheck(0, param1MethodHandle, param1ArrayOfObject);
/* 1158 */       return param1MethodHandle.invokeBasic();
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_L_L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1162 */       assert arityCheck(1, param1MethodHandle, param1ArrayOfObject);
/* 1163 */       return param1MethodHandle.invokeBasic(param1ArrayOfObject[0]);
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LL_L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1167 */       assert arityCheck(2, param1MethodHandle, param1ArrayOfObject);
/* 1168 */       return param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1]);
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLL_L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1172 */       assert arityCheck(3, param1MethodHandle, param1ArrayOfObject);
/* 1173 */       return param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2]);
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLLL_L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1177 */       assert arityCheck(4, param1MethodHandle, param1ArrayOfObject);
/* 1178 */       return param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2], param1ArrayOfObject[3]);
/*      */     }
/*      */     @Hidden
/*      */     static Object invoke_LLLLL_L(MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) throws Throwable {
/* 1182 */       assert arityCheck(5, param1MethodHandle, param1ArrayOfObject);
/* 1183 */       return param1MethodHandle.invokeBasic(param1ArrayOfObject[0], param1ArrayOfObject[1], param1ArrayOfObject[2], param1ArrayOfObject[3], param1ArrayOfObject[4]);
/*      */     }
/*      */     private static boolean arityCheck(int param1Int, MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) {
/* 1186 */       return arityCheck(param1Int, Object.class, param1MethodHandle, param1ArrayOfObject);
/*      */     }
/*      */     private static boolean arityCheck(int param1Int, Class<?> param1Class, MethodHandle param1MethodHandle, Object[] param1ArrayOfObject) {
/* 1189 */       assert param1ArrayOfObject.length == param1Int : 
/* 1190 */         Arrays.asList((T[])new Integer[] { Integer.valueOf(param1ArrayOfObject.length), Integer.valueOf(param1Int) });
/* 1191 */       assert param1MethodHandle.type().basicType() == MethodType.genericMethodType(param1Int).changeReturnType(param1Class) : 
/* 1192 */         Arrays.asList((T[])new Object[] { param1MethodHandle, param1Class, Integer.valueOf(param1Int) });
/* 1193 */       MemberName memberName = param1MethodHandle.internalMemberName();
/* 1194 */       if (isInvokeBasic(memberName)) {
/* 1195 */         assert param1Int > 0;
/* 1196 */         assert param1ArrayOfObject[0] instanceof MethodHandle;
/* 1197 */         MethodHandle methodHandle = (MethodHandle)param1ArrayOfObject[0];
/* 1198 */         assert methodHandle.type().basicType() == MethodType.genericMethodType(param1Int - 1).changeReturnType(param1Class) : 
/* 1199 */           Arrays.asList((T[])new Object[] { memberName, methodHandle, param1Class, Integer.valueOf(param1Int) });
/*      */       } 
/* 1201 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1205 */     static final MethodType INVOKER_METHOD_TYPE = MethodType.methodType(Object.class, MethodHandle.class, new Class[] { Object[].class });
/*      */     
/*      */     private static MethodHandle computeInvoker(MethodTypeForm param1MethodTypeForm) {
/* 1208 */       param1MethodTypeForm = param1MethodTypeForm.basicType().form();
/* 1209 */       MethodHandle methodHandle1 = param1MethodTypeForm.cachedMethodHandle(1);
/* 1210 */       if (methodHandle1 != null) return methodHandle1; 
/* 1211 */       MemberName memberName = InvokerBytecodeGenerator.generateNamedFunctionInvoker(param1MethodTypeForm);
/* 1212 */       methodHandle1 = DirectMethodHandle.make(memberName);
/* 1213 */       MethodHandle methodHandle2 = param1MethodTypeForm.cachedMethodHandle(1);
/* 1214 */       if (methodHandle2 != null) return methodHandle2; 
/* 1215 */       if (!methodHandle1.type().equals(INVOKER_METHOD_TYPE))
/* 1216 */         throw MethodHandleStatics.newInternalError(methodHandle1.debugString()); 
/* 1217 */       return param1MethodTypeForm.setCachedMethodHandle(1, methodHandle1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Hidden
/*      */     Object invokeWithArguments(Object... param1VarArgs) throws Throwable {
/* 1224 */       if (LambdaForm.TRACE_INTERPRETER) return invokeWithArgumentsTracing(param1VarArgs); 
/* 1225 */       assert checkArgumentTypes(param1VarArgs, methodType());
/* 1226 */       return invoker().invokeBasic(resolvedHandle(), param1VarArgs);
/*      */     }
/*      */     
/*      */     @Hidden
/*      */     Object invokeWithArgumentsTracing(Object[] param1ArrayOfObject) throws Throwable {
/*      */       Object object;
/*      */       try {
/* 1233 */         LambdaForm.traceInterpreter("[ call", this, param1ArrayOfObject);
/* 1234 */         if (this.invoker == null) {
/* 1235 */           LambdaForm.traceInterpreter("| getInvoker", this);
/* 1236 */           invoker();
/*      */         } 
/* 1238 */         if (this.resolvedHandle == null) {
/* 1239 */           LambdaForm.traceInterpreter("| resolve", this);
/* 1240 */           resolvedHandle();
/*      */         } 
/* 1242 */         assert checkArgumentTypes(param1ArrayOfObject, methodType());
/* 1243 */         object = invoker().invokeBasic(resolvedHandle(), param1ArrayOfObject);
/* 1244 */       } catch (Throwable throwable) {
/* 1245 */         LambdaForm.traceInterpreter("] throw =>", throwable);
/* 1246 */         throw throwable;
/*      */       } 
/* 1248 */       LambdaForm.traceInterpreter("] return =>", object);
/* 1249 */       return object;
/*      */     }
/*      */     
/*      */     private MethodHandle invoker() {
/* 1253 */       if (this.invoker != null) return this.invoker;
/*      */       
/* 1255 */       return this.invoker = computeInvoker(methodType().form());
/*      */     }
/*      */     
/*      */     private static boolean checkArgumentTypes(Object[] param1ArrayOfObject, MethodType param1MethodType) {
/* 1259 */       return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MethodType methodType() {
/* 1276 */       if (this.resolvedHandle != null) {
/* 1277 */         return this.resolvedHandle.type();
/*      */       }
/*      */       
/* 1280 */       return this.member.getInvocationType();
/*      */     }
/*      */     
/*      */     MemberName member() {
/* 1284 */       assert assertMemberIsConsistent();
/* 1285 */       return this.member;
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean assertMemberIsConsistent() {
/* 1290 */       if (this.resolvedHandle instanceof DirectMethodHandle) {
/* 1291 */         MemberName memberName = this.resolvedHandle.internalMemberName();
/* 1292 */         assert memberName.equals(this.member);
/*      */       } 
/* 1294 */       return true;
/*      */     }
/*      */     
/*      */     Class<?> memberDeclaringClassOrNull() {
/* 1298 */       return (this.member == null) ? null : this.member.getDeclaringClass();
/*      */     }
/*      */     
/*      */     LambdaForm.BasicType returnType() {
/* 1302 */       return LambdaForm.BasicType.basicType(methodType().returnType());
/*      */     }
/*      */     
/*      */     LambdaForm.BasicType parameterType(int param1Int) {
/* 1306 */       return LambdaForm.BasicType.basicType(methodType().parameterType(param1Int));
/*      */     }
/*      */     
/*      */     int arity() {
/* 1310 */       return methodType().parameterCount();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1314 */       if (this.member == null) return String.valueOf(this.resolvedHandle); 
/* 1315 */       return this.member.getDeclaringClass().getSimpleName() + "." + this.member.getName();
/*      */     }
/*      */     
/*      */     public boolean isIdentity() {
/* 1319 */       return equals(LambdaForm.identity(returnType()));
/*      */     }
/*      */     
/*      */     public boolean isConstantZero() {
/* 1323 */       return equals(LambdaForm.constantZero(returnType()));
/*      */     }
/*      */     
/*      */     public MethodHandleImpl.Intrinsic intrinsicName() {
/* 1327 */       return (this.resolvedHandle == null) ? MethodHandleImpl.Intrinsic.NONE : this.resolvedHandle
/* 1328 */         .intrinsicName();
/*      */     } }
/*      */ 
/*      */   
/*      */   public static String basicTypeSignature(MethodType paramMethodType) {
/* 1333 */     char[] arrayOfChar = new char[paramMethodType.parameterCount() + 2];
/* 1334 */     byte b = 0;
/* 1335 */     for (Class<?> clazz : paramMethodType.parameterList()) {
/* 1336 */       arrayOfChar[b++] = BasicType.basicTypeChar(clazz);
/*      */     }
/* 1338 */     arrayOfChar[b++] = '_';
/* 1339 */     arrayOfChar[b++] = BasicType.basicTypeChar(paramMethodType.returnType());
/* 1340 */     assert b == arrayOfChar.length;
/* 1341 */     return String.valueOf(arrayOfChar);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String shortenSignature(String paramString) {
/* 1346 */     byte b = -1; byte b1 = 0;
/* 1347 */     StringBuilder stringBuilder = null;
/* 1348 */     int i = paramString.length();
/* 1349 */     if (i < 3) return paramString; 
/* 1350 */     for (byte b2 = 0; b2 <= i; b2++) {
/*      */       
/* 1352 */       byte b3 = b; b = (b2 == i) ? -1 : paramString.charAt(b2);
/* 1353 */       if (b == b3) { b1++; }
/*      */       else
/* 1355 */       { byte b4 = b1; b1 = 1;
/*      */         
/* 1357 */         if (b4 < 3)
/* 1358 */         { if (stringBuilder != null) {
/* 1359 */             while (--b4 >= 0) {
/* 1360 */               stringBuilder.append((char)b3);
/*      */             }
/*      */           } }
/*      */         else
/*      */         
/* 1365 */         { if (stringBuilder == null)
/* 1366 */             stringBuilder = (new StringBuilder()).append(paramString, 0, b2 - b4); 
/* 1367 */           stringBuilder.append((char)b3).append(b4); }  }
/*      */     
/* 1369 */     }  return (stringBuilder == null) ? paramString : stringBuilder.toString();
/*      */   }
/*      */   
/*      */   static final class Name { final LambdaForm.BasicType type;
/*      */     private short index;
/*      */     final LambdaForm.NamedFunction function;
/*      */     final Object constraint;
/*      */     @Stable
/*      */     final Object[] arguments;
/*      */     
/*      */     private Name(int param1Int, LambdaForm.BasicType param1BasicType, LambdaForm.NamedFunction param1NamedFunction, Object[] param1ArrayOfObject) {
/* 1380 */       this.index = (short)param1Int;
/* 1381 */       this.type = param1BasicType;
/* 1382 */       this.function = param1NamedFunction;
/* 1383 */       this.arguments = param1ArrayOfObject;
/* 1384 */       this.constraint = null;
/* 1385 */       assert this.index == param1Int;
/*      */     }
/*      */     private Name(Name param1Name, Object param1Object) {
/* 1388 */       this.index = param1Name.index;
/* 1389 */       this.type = param1Name.type;
/* 1390 */       this.function = param1Name.function;
/* 1391 */       this.arguments = param1Name.arguments;
/* 1392 */       this.constraint = param1Object;
/* 1393 */       assert param1Object == null || isParam();
/* 1394 */       assert param1Object == null || param1Object instanceof BoundMethodHandle.SpeciesData || param1Object instanceof Class;
/*      */     }
/*      */     Name(MethodHandle param1MethodHandle, Object... param1VarArgs) {
/* 1397 */       this(new LambdaForm.NamedFunction(param1MethodHandle), param1VarArgs);
/*      */     }
/*      */     Name(MethodType param1MethodType, Object... param1VarArgs) {
/* 1400 */       this(new LambdaForm.NamedFunction(param1MethodType), param1VarArgs);
/* 1401 */       assert param1VarArgs[0] instanceof Name && ((Name)param1VarArgs[0]).type == LambdaForm.BasicType.L_TYPE;
/*      */     }
/*      */     Name(MemberName param1MemberName, Object... param1VarArgs) {
/* 1404 */       this(new LambdaForm.NamedFunction(param1MemberName), param1VarArgs);
/*      */     }
/*      */     Name(LambdaForm.NamedFunction param1NamedFunction, Object... param1VarArgs) {
/* 1407 */       this(-1, param1NamedFunction.returnType(), param1NamedFunction, param1VarArgs = Arrays.copyOf(param1VarArgs, param1VarArgs.length, Object[].class));
/* 1408 */       assert param1VarArgs.length == param1NamedFunction.arity() : "arity mismatch: arguments.length=" + param1VarArgs.length + " == function.arity()=" + param1NamedFunction.arity() + " in " + debugString();
/* 1409 */       for (byte b = 0; b < param1VarArgs.length; b++)
/* 1410 */         assert typesMatch(param1NamedFunction.parameterType(b), param1VarArgs[b]) : "types don't match: function.parameterType(" + b + ")=" + param1NamedFunction.parameterType(b) + ", arguments[" + b + "]=" + param1VarArgs[b] + " in " + debugString(); 
/*      */     }
/*      */     
/*      */     Name(int param1Int, LambdaForm.BasicType param1BasicType) {
/* 1414 */       this(param1Int, param1BasicType, null, null);
/*      */     }
/*      */     Name(LambdaForm.BasicType param1BasicType) {
/* 1417 */       this(-1, param1BasicType);
/*      */     }
/* 1419 */     LambdaForm.BasicType type() { return this.type; } int index() {
/* 1420 */       return this.index;
/*      */     } boolean initIndex(int param1Int) {
/* 1422 */       if (this.index != param1Int) {
/* 1423 */         if (this.index != -1) return false; 
/* 1424 */         this.index = (short)param1Int;
/*      */       } 
/* 1426 */       return true;
/*      */     }
/*      */     char typeChar() {
/* 1429 */       return this.type.btChar;
/*      */     }
/*      */     
/*      */     void resolve() {
/* 1433 */       if (this.function != null)
/* 1434 */         this.function.resolve(); 
/*      */     }
/*      */     
/*      */     Name newIndex(int param1Int) {
/* 1438 */       if (initIndex(param1Int)) return this; 
/* 1439 */       return cloneWithIndex(param1Int);
/*      */     }
/*      */     Name cloneWithIndex(int param1Int) {
/* 1442 */       Object[] arrayOfObject = (this.arguments == null) ? null : (Object[])this.arguments.clone();
/* 1443 */       return (new Name(param1Int, this.type, this.function, arrayOfObject)).withConstraint(this.constraint);
/*      */     }
/*      */     Name withConstraint(Object param1Object) {
/* 1446 */       if (param1Object == this.constraint) return this; 
/* 1447 */       return new Name(this, param1Object);
/*      */     }
/*      */     Name replaceName(Name param1Name1, Name param1Name2) {
/* 1450 */       if (param1Name1 == param1Name2) return this;
/*      */       
/* 1452 */       Object[] arrayOfObject = this.arguments;
/* 1453 */       if (arrayOfObject == null) return this; 
/* 1454 */       boolean bool = false;
/* 1455 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1456 */         if (arrayOfObject[b] == param1Name1) {
/* 1457 */           if (!bool) {
/* 1458 */             bool = true;
/* 1459 */             arrayOfObject = (Object[])arrayOfObject.clone();
/*      */           } 
/* 1461 */           arrayOfObject[b] = param1Name2;
/*      */         } 
/*      */       } 
/* 1464 */       if (!bool) return this; 
/* 1465 */       return new Name(this.function, arrayOfObject);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Name replaceNames(Name[] param1ArrayOfName1, Name[] param1ArrayOfName2, int param1Int1, int param1Int2) {
/* 1471 */       if (param1Int1 >= param1Int2) return this;
/*      */       
/* 1473 */       Object[] arrayOfObject = this.arguments;
/* 1474 */       boolean bool = false;
/*      */       
/* 1476 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1477 */         if (arrayOfObject[b] instanceof Name) {
/* 1478 */           Name name = (Name)arrayOfObject[b];
/* 1479 */           short s = name.index;
/*      */           
/* 1481 */           if (s < 0 || s >= param1ArrayOfName2.length || name != param1ArrayOfName2[s])
/*      */           {
/*      */             
/* 1484 */             for (int i = param1Int1; i < param1Int2; i++) {
/* 1485 */               if (name == param1ArrayOfName1[i]) {
/* 1486 */                 if (name == param1ArrayOfName2[i])
/*      */                   break; 
/* 1488 */                 if (!bool) {
/* 1489 */                   bool = true;
/* 1490 */                   arrayOfObject = (Object[])arrayOfObject.clone();
/*      */                 } 
/* 1492 */                 arrayOfObject[b] = param1ArrayOfName2[i];
/*      */                 break;
/*      */               } 
/*      */             }  } 
/*      */         } 
/*      */       } 
/* 1498 */       if (!bool) return this; 
/* 1499 */       return new Name(this.function, arrayOfObject);
/*      */     }
/*      */     
/*      */     void internArguments() {
/* 1503 */       Object[] arrayOfObject = this.arguments;
/* 1504 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 1505 */         if (arrayOfObject[b] instanceof Name) {
/* 1506 */           Name name = (Name)arrayOfObject[b];
/* 1507 */           if (name.isParam() && name.index < 10)
/* 1508 */             arrayOfObject[b] = LambdaForm.internArgument(name); 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     boolean isParam() {
/* 1513 */       return (this.function == null);
/*      */     }
/*      */     boolean isConstantZero() {
/* 1516 */       return (!isParam() && this.arguments.length == 0 && this.function.isConstantZero());
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1520 */       return (isParam() ? "a" : "t") + ((this.index >= 0) ? this.index : System.identityHashCode(this)) + ":" + typeChar();
/*      */     }
/*      */     public String debugString() {
/* 1523 */       String str = paramString();
/* 1524 */       return (this.function == null) ? str : (str + "=" + exprString());
/*      */     }
/*      */     public String paramString() {
/* 1527 */       String str = toString();
/* 1528 */       Object object = this.constraint;
/* 1529 */       if (object == null)
/* 1530 */         return str; 
/* 1531 */       if (object instanceof Class) object = ((Class)object).getSimpleName(); 
/* 1532 */       return str + "/" + object;
/*      */     }
/*      */     public String exprString() {
/* 1535 */       if (this.function == null) return toString(); 
/* 1536 */       StringBuilder stringBuilder = new StringBuilder(this.function.toString());
/* 1537 */       stringBuilder.append("(");
/* 1538 */       String str = "";
/* 1539 */       for (Object object : this.arguments) {
/* 1540 */         stringBuilder.append(str); str = ",";
/* 1541 */         if (object instanceof Name || object instanceof Integer) {
/* 1542 */           stringBuilder.append(object);
/*      */         } else {
/* 1544 */           stringBuilder.append("(").append(object).append(")");
/*      */         } 
/* 1546 */       }  stringBuilder.append(")");
/* 1547 */       return stringBuilder.toString();
/*      */     }
/*      */     
/*      */     static boolean typesMatch(LambdaForm.BasicType param1BasicType, Object param1Object) {
/* 1551 */       if (param1Object instanceof Name) {
/* 1552 */         return (((Name)param1Object).type == param1BasicType);
/*      */       }
/* 1554 */       switch (param1BasicType) { case I_TYPE:
/* 1555 */           return param1Object instanceof Integer;
/* 1556 */         case J_TYPE: return param1Object instanceof Long;
/* 1557 */         case F_TYPE: return param1Object instanceof Float;
/* 1558 */         case D_TYPE: return param1Object instanceof Double; }
/*      */       
/* 1560 */       assert param1BasicType == LambdaForm.BasicType.L_TYPE;
/* 1561 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int lastUseIndex(Name param1Name) {
/* 1568 */       if (this.arguments == null) return -1; 
/* 1569 */       for (int i = this.arguments.length; --i >= 0;) {
/* 1570 */         if (this.arguments[i] == param1Name) return i; 
/*      */       } 
/* 1572 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int useCount(Name param1Name) {
/* 1579 */       if (this.arguments == null) return 0; 
/* 1580 */       byte b = 0;
/* 1581 */       for (int i = this.arguments.length; --i >= 0;) {
/* 1582 */         if (this.arguments[i] == param1Name) b++; 
/*      */       } 
/* 1584 */       return b;
/*      */     }
/*      */     
/*      */     boolean contains(Name param1Name) {
/* 1588 */       return (this == param1Name || lastUseIndex(param1Name) >= 0);
/*      */     }
/*      */     
/*      */     public boolean equals(Name param1Name) {
/* 1592 */       if (this == param1Name) return true; 
/* 1593 */       if (isParam())
/*      */       {
/* 1595 */         return false; } 
/* 1596 */       return (this.type == param1Name.type && this.function
/*      */ 
/*      */         
/* 1599 */         .equals(param1Name.function) && 
/* 1600 */         Arrays.equals(this.arguments, param1Name.arguments));
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1604 */       return (param1Object instanceof Name && equals((Name)param1Object));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1608 */       if (isParam())
/* 1609 */         return this.index | this.type.ordinal() << 8; 
/* 1610 */       return this.function.hashCode() ^ Arrays.hashCode(this.arguments);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int lastUseIndex(Name paramName) {
/* 1618 */     short s = paramName.index; int i = this.names.length;
/* 1619 */     assert this.names[s] == paramName;
/* 1620 */     if (this.result == s) return i; 
/* 1621 */     for (int j = i; --j > s;) {
/* 1622 */       if (this.names[j].lastUseIndex(paramName) >= 0)
/* 1623 */         return j; 
/*      */     } 
/* 1625 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   int useCount(Name paramName) {
/* 1630 */     short s = paramName.index; int i = this.names.length;
/* 1631 */     int j = lastUseIndex(paramName);
/* 1632 */     if (j < 0) return 0; 
/* 1633 */     int k = 0;
/* 1634 */     if (j == i) { k++; j--; }
/* 1635 */      int m = paramName.index() + 1;
/* 1636 */     if (m < this.arity) m = this.arity; 
/* 1637 */     for (int n = m; n <= j; n++) {
/* 1638 */       k += this.names[n].useCount(paramName);
/*      */     }
/* 1640 */     return k;
/*      */   }
/*      */   
/*      */   static Name argument(int paramInt, char paramChar) {
/* 1644 */     return argument(paramInt, BasicType.basicType(paramChar));
/*      */   }
/*      */   static Name argument(int paramInt, BasicType paramBasicType) {
/* 1647 */     if (paramInt >= 10)
/* 1648 */       return new Name(paramInt, paramBasicType); 
/* 1649 */     return INTERNED_ARGUMENTS[paramBasicType.ordinal()][paramInt];
/*      */   }
/*      */   static Name internArgument(Name paramName) {
/* 1652 */     assert paramName.isParam() : "not param: " + paramName;
/* 1653 */     assert paramName.index < 10;
/* 1654 */     if (paramName.constraint != null) return paramName; 
/* 1655 */     return argument(paramName.index, paramName.type);
/*      */   }
/*      */   static Name[] arguments(int paramInt, String paramString) {
/* 1658 */     int i = paramString.length();
/* 1659 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1660 */     for (byte b = 0; b < i; b++)
/* 1661 */       arrayOfName[b] = argument(b, paramString.charAt(b)); 
/* 1662 */     return arrayOfName;
/*      */   }
/*      */   static Name[] arguments(int paramInt, char... paramVarArgs) {
/* 1665 */     int i = paramVarArgs.length;
/* 1666 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1667 */     for (byte b = 0; b < i; b++)
/* 1668 */       arrayOfName[b] = argument(b, paramVarArgs[b]); 
/* 1669 */     return arrayOfName;
/*      */   }
/*      */   static Name[] arguments(int paramInt, List<Class<?>> paramList) {
/* 1672 */     int i = paramList.size();
/* 1673 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1674 */     for (byte b = 0; b < i; b++)
/* 1675 */       arrayOfName[b] = argument(b, BasicType.basicType(paramList.get(b))); 
/* 1676 */     return arrayOfName;
/*      */   }
/*      */   static Name[] arguments(int paramInt, Class<?>... paramVarArgs) {
/* 1679 */     int i = paramVarArgs.length;
/* 1680 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1681 */     for (byte b = 0; b < i; b++)
/* 1682 */       arrayOfName[b] = argument(b, BasicType.basicType(paramVarArgs[b])); 
/* 1683 */     return arrayOfName;
/*      */   }
/*      */   static Name[] arguments(int paramInt, MethodType paramMethodType) {
/* 1686 */     int i = paramMethodType.parameterCount();
/* 1687 */     Name[] arrayOfName = new Name[i + paramInt];
/* 1688 */     for (byte b = 0; b < i; b++)
/* 1689 */       arrayOfName[b] = argument(b, BasicType.basicType(paramMethodType.parameterType(b))); 
/* 1690 */     return arrayOfName;
/*      */   }
/*      */   
/* 1693 */   private static final Name[][] INTERNED_ARGUMENTS = new Name[BasicType.ARG_TYPE_LIMIT][10];
/*      */   
/*      */   static {
/* 1696 */     for (BasicType basicType : BasicType.ARG_TYPES) {
/* 1697 */       int i = basicType.ordinal();
/* 1698 */       for (byte b = 0; b < (INTERNED_ARGUMENTS[i]).length; b++) {
/* 1699 */         INTERNED_ARGUMENTS[i][b] = new Name(b, basicType);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/* 1704 */   private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory();
/*      */   
/*      */   static LambdaForm identityForm(BasicType paramBasicType) {
/* 1707 */     return LF_identityForm[paramBasicType.ordinal()];
/*      */   }
/*      */   static LambdaForm zeroForm(BasicType paramBasicType) {
/* 1710 */     return LF_zeroForm[paramBasicType.ordinal()];
/*      */   }
/*      */   static NamedFunction identity(BasicType paramBasicType) {
/* 1713 */     return NF_identity[paramBasicType.ordinal()];
/*      */   }
/*      */   static NamedFunction constantZero(BasicType paramBasicType) {
/* 1716 */     return NF_zero[paramBasicType.ordinal()];
/*      */   }
/* 1718 */   private static final LambdaForm[] LF_identityForm = new LambdaForm[BasicType.TYPE_LIMIT];
/* 1719 */   private static final LambdaForm[] LF_zeroForm = new LambdaForm[BasicType.TYPE_LIMIT];
/* 1720 */   private static final NamedFunction[] NF_identity = new NamedFunction[BasicType.TYPE_LIMIT];
/* 1721 */   private static final NamedFunction[] NF_zero = new NamedFunction[BasicType.TYPE_LIMIT]; private static final HashMap<String, Integer> DEBUG_NAME_COUNTERS;
/*      */   private static void createIdentityForms() {
/* 1723 */     for (BasicType basicType : BasicType.ALL_TYPES) {
/* 1724 */       LambdaForm lambdaForm1, lambdaForm2; int i = basicType.ordinal();
/* 1725 */       char c = basicType.basicTypeChar();
/* 1726 */       boolean bool = (basicType == BasicType.V_TYPE) ? true : false;
/* 1727 */       Class<?> clazz = basicType.btClass;
/* 1728 */       MethodType methodType1 = MethodType.methodType(clazz);
/* 1729 */       MethodType methodType2 = bool ? methodType1 : methodType1.appendParameterTypes(new Class[] { clazz });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1734 */       MemberName memberName1 = new MemberName(LambdaForm.class, "identity_" + c, methodType2, (byte)6);
/* 1735 */       MemberName memberName2 = new MemberName(LambdaForm.class, "zero_" + c, methodType1, (byte)6);
/*      */       try {
/* 1737 */         memberName2 = IMPL_NAMES.resolveOrFail((byte)6, memberName2, null, NoSuchMethodException.class);
/* 1738 */         memberName1 = IMPL_NAMES.resolveOrFail((byte)6, memberName1, null, NoSuchMethodException.class);
/* 1739 */       } catch (IllegalAccessException|NoSuchMethodException illegalAccessException) {
/* 1740 */         throw MethodHandleStatics.newInternalError(illegalAccessException);
/*      */       } 
/*      */       
/* 1743 */       NamedFunction namedFunction1 = new NamedFunction(memberName1);
/*      */       
/* 1745 */       if (bool) {
/* 1746 */         Name[] arrayOfName = { argument(0, BasicType.L_TYPE) };
/* 1747 */         lambdaForm1 = new LambdaForm(memberName1.getName(), 1, arrayOfName, -1);
/*      */       } else {
/* 1749 */         Name[] arrayOfName = { argument(0, BasicType.L_TYPE), argument(1, basicType) };
/* 1750 */         lambdaForm1 = new LambdaForm(memberName1.getName(), 2, arrayOfName, 1);
/*      */       } 
/* 1752 */       LF_identityForm[i] = lambdaForm1;
/* 1753 */       NF_identity[i] = namedFunction1;
/*      */       
/* 1755 */       NamedFunction namedFunction2 = new NamedFunction(memberName2);
/*      */       
/* 1757 */       if (bool) {
/* 1758 */         lambdaForm2 = lambdaForm1;
/*      */       } else {
/* 1760 */         Object object = Wrapper.forBasicType(c).zero();
/* 1761 */         Name[] arrayOfName = { argument(0, BasicType.L_TYPE), new Name(namedFunction1, new Object[] { object }) };
/* 1762 */         lambdaForm2 = new LambdaForm(memberName2.getName(), 1, arrayOfName, 1);
/*      */       } 
/* 1764 */       LF_zeroForm[i] = lambdaForm2;
/* 1765 */       NF_zero[i] = namedFunction2;
/*      */       
/* 1767 */       assert namedFunction1.isIdentity();
/* 1768 */       assert namedFunction2.isConstantZero();
/* 1769 */       assert (new Name(namedFunction2, new Object[0])).isConstantZero();
/*      */     } 
/*      */ 
/*      */     
/* 1773 */     for (BasicType basicType : BasicType.ALL_TYPES) {
/* 1774 */       int i = basicType.ordinal();
/* 1775 */       NamedFunction namedFunction1 = NF_identity[i];
/* 1776 */       LambdaForm lambdaForm1 = LF_identityForm[i];
/* 1777 */       MemberName memberName1 = namedFunction1.member;
/* 1778 */       namedFunction1.resolvedHandle = SimpleMethodHandle.make(memberName1.getInvocationType(), lambdaForm1);
/*      */       
/* 1780 */       NamedFunction namedFunction2 = NF_zero[i];
/* 1781 */       LambdaForm lambdaForm2 = LF_zeroForm[i];
/* 1782 */       MemberName memberName2 = namedFunction2.member;
/* 1783 */       namedFunction2.resolvedHandle = SimpleMethodHandle.make(memberName2.getInvocationType(), lambdaForm2);
/*      */       
/* 1785 */       assert namedFunction1.isIdentity();
/* 1786 */       assert namedFunction2.isConstantZero();
/* 1787 */       assert (new Name(namedFunction2, new Object[0])).isConstantZero();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int identity_I(int paramInt) {
/* 1792 */     return paramInt;
/* 1793 */   } private static long identity_J(long paramLong) { return paramLong; }
/* 1794 */   private static float identity_F(float paramFloat) { return paramFloat; }
/* 1795 */   private static double identity_D(double paramDouble) { return paramDouble; } private static Object identity_L(Object paramObject) {
/* 1796 */     return paramObject;
/*      */   } private static void identity_V() {}
/* 1798 */   private static int zero_I() { return 0; }
/* 1799 */   private static long zero_J() { return 0L; }
/* 1800 */   private static float zero_F() { return 0.0F; }
/* 1801 */   private static double zero_D() { return 0.0D; } private static Object zero_L() {
/* 1802 */     return null;
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
/*      */   private static void zero_V() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1825 */     if (MethodHandleStatics.debugEnabled()) {
/* 1826 */       DEBUG_NAME_COUNTERS = new HashMap<>();
/*      */     } else {
/* 1828 */       DEBUG_NAME_COUNTERS = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1833 */     createIdentityForms();
/*      */     
/* 1835 */     computeInitialPreparedForms();
/* 1836 */     NamedFunction.initializeInvokers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1845 */   private static final boolean TRACE_INTERPRETER = MethodHandleStatics.TRACE_INTERPRETER;
/*      */   
/*      */   @Target({ElementType.METHOD})
/*      */   @Retention(RetentionPolicy.RUNTIME)
/*      */   static @interface Hidden {}
/*      */   
/*      */   @Target({ElementType.METHOD})
/*      */   @Retention(RetentionPolicy.RUNTIME)
/*      */   static @interface Compiled {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/LambdaForm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */