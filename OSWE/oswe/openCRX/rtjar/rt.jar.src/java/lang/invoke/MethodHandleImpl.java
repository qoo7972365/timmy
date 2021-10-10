/*      */ package java.lang.invoke;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.invoke.BoundMethodHandle;
/*      */ import java.lang.invoke.DelegatingMethodHandle;
/*      */ import java.lang.invoke.ForceInline;
/*      */ import java.lang.invoke.InvokerBytecodeGenerator;
/*      */ import java.lang.invoke.LambdaForm;
/*      */ import java.lang.invoke.LambdaForm.Hidden;
/*      */ import java.lang.invoke.MemberName;
/*      */ import java.lang.invoke.MethodHandle;
/*      */ import java.lang.invoke.MethodHandleImpl;
/*      */ import java.lang.invoke.MethodHandleStatics;
/*      */ import java.lang.invoke.MethodHandles;
/*      */ import java.lang.invoke.MethodType;
/*      */ import java.lang.invoke.SimpleMethodHandle;
/*      */ import java.lang.invoke.Stable;
/*      */ import java.lang.invoke.WrongMethodTypeException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.net.URLConnection;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.function.Function;
/*      */ import sun.invoke.empty.Empty;
/*      */ import sun.invoke.util.ValueConversions;
/*      */ import sun.invoke.util.VerifyType;
/*      */ import sun.invoke.util.Wrapper;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class MethodHandleImpl
/*      */ {
/*      */   private static final int MAX_ARITY;
/*      */   
/*      */   static {
/*   53 */     final Object[] values = { Integer.valueOf(255) };
/*   54 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */         {
/*      */           public Void run() {
/*   57 */             values[0] = Integer.getInteger(MethodHandleImpl.class.getName() + ".MAX_ARITY", 255);
/*   58 */             return null;
/*      */           }
/*      */         });
/*   61 */     MAX_ARITY = ((Integer)arrayOfObject[0]).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void initStatics() {
/*   68 */     MemberName.Factory.INSTANCE.getClass();
/*      */   }
/*      */   
/*      */   static MethodHandle makeArrayElementAccessor(Class<?> paramClass, boolean paramBoolean) {
/*   72 */     if (paramClass == Object[].class)
/*   73 */       return paramBoolean ? ArrayAccessor.OBJECT_ARRAY_SETTER : ArrayAccessor.OBJECT_ARRAY_GETTER; 
/*   74 */     if (!paramClass.isArray())
/*   75 */       throw MethodHandleStatics.newIllegalArgumentException("not an array: " + paramClass); 
/*   76 */     MethodHandle[] arrayOfMethodHandle = ArrayAccessor.TYPED_ACCESSORS.get(paramClass);
/*   77 */     boolean bool = paramBoolean ? true : false;
/*   78 */     MethodHandle methodHandle = arrayOfMethodHandle[bool];
/*   79 */     if (methodHandle != null) return methodHandle; 
/*   80 */     methodHandle = ArrayAccessor.getAccessor(paramClass, paramBoolean);
/*   81 */     MethodType methodType = ArrayAccessor.correctType(paramClass, paramBoolean);
/*   82 */     if (methodHandle.type() != methodType) {
/*   83 */       assert methodHandle.type().parameterType(0) == Object[].class;
/*   84 */       assert (paramBoolean ? (Class)methodHandle.type().parameterType(2) : (Class)methodHandle.type().returnType()) == Object.class;
/*   85 */       assert paramBoolean || methodType.parameterType(0).getComponentType() == methodType.returnType();
/*      */       
/*   87 */       methodHandle = methodHandle.viewAsType(methodType, false);
/*      */     } 
/*   89 */     methodHandle = makeIntrinsic(methodHandle, paramBoolean ? Intrinsic.ARRAY_STORE : Intrinsic.ARRAY_LOAD);
/*      */     
/*   91 */     synchronized (arrayOfMethodHandle) {
/*   92 */       if (arrayOfMethodHandle[bool] == null) {
/*   93 */         arrayOfMethodHandle[bool] = methodHandle;
/*      */       } else {
/*      */         
/*   96 */         methodHandle = arrayOfMethodHandle[bool];
/*      */       } 
/*      */     } 
/*   99 */     return methodHandle;
/*      */   }
/*      */   static final class ArrayAccessor { static final int GETTER_INDEX = 0;
/*      */     static final int SETTER_INDEX = 1;
/*      */     static final int INDEX_LIMIT = 2;
/*      */     
/*  105 */     static final ClassValue<MethodHandle[]> TYPED_ACCESSORS = new ClassValue<MethodHandle[]>()
/*      */       {
/*      */         protected MethodHandle[] computeValue(Class<?> param2Class)
/*      */         {
/*  109 */           return new MethodHandle[2];
/*      */         }
/*      */       };
/*      */     static final MethodHandle OBJECT_ARRAY_GETTER; static final MethodHandle OBJECT_ARRAY_SETTER;
/*      */     static {
/*  114 */       MethodHandle[] arrayOfMethodHandle = TYPED_ACCESSORS.get(Object[].class);
/*  115 */       arrayOfMethodHandle[0] = OBJECT_ARRAY_GETTER = MethodHandleImpl.makeIntrinsic(getAccessor(Object[].class, false), MethodHandleImpl.Intrinsic.ARRAY_LOAD);
/*  116 */       arrayOfMethodHandle[1] = OBJECT_ARRAY_SETTER = MethodHandleImpl.makeIntrinsic(getAccessor(Object[].class, true), MethodHandleImpl.Intrinsic.ARRAY_STORE);
/*      */       
/*  118 */       assert InvokerBytecodeGenerator.isStaticallyInvocable(OBJECT_ARRAY_GETTER.internalMemberName());
/*  119 */       assert InvokerBytecodeGenerator.isStaticallyInvocable(OBJECT_ARRAY_SETTER.internalMemberName());
/*      */     }
/*      */     
/*  122 */     static int getElementI(int[] param1ArrayOfint, int param1Int) { return param1ArrayOfint[param1Int]; }
/*  123 */     static long getElementJ(long[] param1ArrayOflong, int param1Int) { return param1ArrayOflong[param1Int]; }
/*  124 */     static float getElementF(float[] param1ArrayOffloat, int param1Int) { return param1ArrayOffloat[param1Int]; }
/*  125 */     static double getElementD(double[] param1ArrayOfdouble, int param1Int) { return param1ArrayOfdouble[param1Int]; }
/*  126 */     static boolean getElementZ(boolean[] param1ArrayOfboolean, int param1Int) { return param1ArrayOfboolean[param1Int]; }
/*  127 */     static byte getElementB(byte[] param1ArrayOfbyte, int param1Int) { return param1ArrayOfbyte[param1Int]; }
/*  128 */     static short getElementS(short[] param1ArrayOfshort, int param1Int) { return param1ArrayOfshort[param1Int]; }
/*  129 */     static char getElementC(char[] param1ArrayOfchar, int param1Int) { return param1ArrayOfchar[param1Int]; } static Object getElementL(Object[] param1ArrayOfObject, int param1Int) {
/*  130 */       return param1ArrayOfObject[param1Int];
/*      */     }
/*  132 */     static void setElementI(int[] param1ArrayOfint, int param1Int1, int param1Int2) { param1ArrayOfint[param1Int1] = param1Int2; }
/*  133 */     static void setElementJ(long[] param1ArrayOflong, int param1Int, long param1Long) { param1ArrayOflong[param1Int] = param1Long; }
/*  134 */     static void setElementF(float[] param1ArrayOffloat, int param1Int, float param1Float) { param1ArrayOffloat[param1Int] = param1Float; }
/*  135 */     static void setElementD(double[] param1ArrayOfdouble, int param1Int, double param1Double) { param1ArrayOfdouble[param1Int] = param1Double; }
/*  136 */     static void setElementZ(boolean[] param1ArrayOfboolean, int param1Int, boolean param1Boolean) { param1ArrayOfboolean[param1Int] = param1Boolean; }
/*  137 */     static void setElementB(byte[] param1ArrayOfbyte, int param1Int, byte param1Byte) { param1ArrayOfbyte[param1Int] = param1Byte; }
/*  138 */     static void setElementS(short[] param1ArrayOfshort, int param1Int, short param1Short) { param1ArrayOfshort[param1Int] = param1Short; }
/*  139 */     static void setElementC(char[] param1ArrayOfchar, int param1Int, char param1Char) { param1ArrayOfchar[param1Int] = param1Char; } static void setElementL(Object[] param1ArrayOfObject, int param1Int, Object param1Object) {
/*  140 */       param1ArrayOfObject[param1Int] = param1Object;
/*      */     }
/*      */     static String name(Class<?> param1Class, boolean param1Boolean) {
/*  143 */       Class<?> clazz = param1Class.getComponentType();
/*  144 */       if (clazz == null) throw MethodHandleStatics.newIllegalArgumentException("not an array", param1Class); 
/*  145 */       return (!param1Boolean ? "getElement" : "setElement") + Wrapper.basicTypeChar(clazz);
/*      */     }
/*      */     static MethodType type(Class<?> param1Class, boolean param1Boolean) {
/*  148 */       Class<?> clazz1 = param1Class.getComponentType();
/*  149 */       Class<?> clazz2 = param1Class;
/*  150 */       if (!clazz1.isPrimitive()) {
/*  151 */         clazz2 = Object[].class;
/*  152 */         clazz1 = Object.class;
/*      */       } 
/*  154 */       return !param1Boolean ? 
/*  155 */         MethodType.methodType(clazz1, clazz2, new Class[] { int.class
/*  156 */           }) : MethodType.methodType(void.class, clazz2, new Class[] { int.class, clazz1 });
/*      */     }
/*      */     static MethodType correctType(Class<?> param1Class, boolean param1Boolean) {
/*  159 */       Class<?> clazz = param1Class.getComponentType();
/*  160 */       return !param1Boolean ? 
/*  161 */         MethodType.methodType(clazz, param1Class, new Class[] { int.class
/*  162 */           }) : MethodType.methodType(void.class, param1Class, new Class[] { int.class, clazz });
/*      */     }
/*      */     static MethodHandle getAccessor(Class<?> param1Class, boolean param1Boolean) {
/*  165 */       String str = name(param1Class, param1Boolean);
/*  166 */       MethodType methodType = type(param1Class, param1Boolean);
/*      */       try {
/*  168 */         return MethodHandles.Lookup.IMPL_LOOKUP.findStatic(ArrayAccessor.class, str, methodType);
/*  169 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/*  170 */         throw MethodHandleStatics.uncaughtException(reflectiveOperationException);
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle makePairwiseConvert(MethodHandle paramMethodHandle, MethodType paramMethodType, boolean paramBoolean1, boolean paramBoolean2) {
/*  191 */     MethodType methodType = paramMethodHandle.type();
/*  192 */     if (paramMethodType == methodType)
/*  193 */       return paramMethodHandle; 
/*  194 */     return makePairwiseConvertByEditor(paramMethodHandle, paramMethodType, paramBoolean1, paramBoolean2);
/*      */   }
/*      */   
/*      */   private static int countNonNull(Object[] paramArrayOfObject) {
/*  198 */     byte b = 0;
/*  199 */     for (Object object : paramArrayOfObject) {
/*  200 */       if (object != null) b++; 
/*      */     } 
/*  202 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle makePairwiseConvertByEditor(MethodHandle paramMethodHandle, MethodType paramMethodType, boolean paramBoolean1, boolean paramBoolean2) {
/*  207 */     Object[] arrayOfObject = computeValueConversions(paramMethodType, paramMethodHandle.type(), paramBoolean1, paramBoolean2);
/*  208 */     int i = countNonNull(arrayOfObject);
/*  209 */     if (i == 0)
/*  210 */       return paramMethodHandle.viewAsType(paramMethodType, paramBoolean1); 
/*  211 */     MethodType methodType1 = paramMethodType.basicType();
/*  212 */     MethodType methodType2 = paramMethodHandle.type().basicType();
/*  213 */     BoundMethodHandle boundMethodHandle = paramMethodHandle.rebind();
/*      */ 
/*      */     
/*  216 */     for (byte b = 0; b < arrayOfObject.length - 1; b++) {
/*  217 */       Object object1 = arrayOfObject[b];
/*  218 */       if (object1 != null) {
/*      */         MethodHandle methodHandle;
/*  220 */         if (object1 instanceof Class) {
/*  221 */           methodHandle = Lazy.MH_castReference.bindTo(object1);
/*      */         } else {
/*  223 */           methodHandle = (MethodHandle)object1;
/*      */         } 
/*  225 */         Class<?> clazz = methodType1.parameterType(b);
/*  226 */         if (--i == 0) {
/*  227 */           methodType2 = paramMethodType;
/*      */         } else {
/*  229 */           methodType2 = methodType2.changeParameterType(b, clazz);
/*  230 */         }  LambdaForm lambdaForm = boundMethodHandle.editor().filterArgumentForm(1 + b, LambdaForm.BasicType.basicType(clazz));
/*  231 */         boundMethodHandle = boundMethodHandle.copyWithExtendL(methodType2, lambdaForm, methodHandle);
/*  232 */         boundMethodHandle = boundMethodHandle.rebind();
/*      */       } 
/*  234 */     }  Object object = arrayOfObject[arrayOfObject.length - 1];
/*  235 */     if (object != null) {
/*      */       MethodHandle methodHandle;
/*  237 */       if (object instanceof Class)
/*  238 */       { if (object == void.class) {
/*  239 */           methodHandle = null;
/*      */         } else {
/*  241 */           methodHandle = Lazy.MH_castReference.bindTo(object);
/*      */         }  }
/*  243 */       else { methodHandle = (MethodHandle)object; }
/*      */       
/*  245 */       Class<?> clazz = methodType1.returnType();
/*  246 */       assert --i == 0;
/*  247 */       methodType2 = paramMethodType;
/*  248 */       if (methodHandle != null) {
/*  249 */         boundMethodHandle = boundMethodHandle.rebind();
/*  250 */         LambdaForm lambdaForm = boundMethodHandle.editor().filterReturnForm(LambdaForm.BasicType.basicType(clazz), false);
/*  251 */         boundMethodHandle = boundMethodHandle.copyWithExtendL(methodType2, lambdaForm, methodHandle);
/*      */       } else {
/*  253 */         LambdaForm lambdaForm = boundMethodHandle.editor().filterReturnForm(LambdaForm.BasicType.basicType(clazz), true);
/*  254 */         boundMethodHandle = boundMethodHandle.copyWith(methodType2, lambdaForm);
/*      */       } 
/*      */     } 
/*  257 */     assert i == 0;
/*  258 */     assert boundMethodHandle.type().equals(paramMethodType);
/*  259 */     return boundMethodHandle;
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle makePairwiseConvertIndirect(MethodHandle paramMethodHandle, MethodType paramMethodType, boolean paramBoolean1, boolean paramBoolean2) {
/*  264 */     assert paramMethodHandle.type().parameterCount() == paramMethodType.parameterCount();
/*      */     
/*  266 */     Object[] arrayOfObject1 = computeValueConversions(paramMethodType, paramMethodHandle.type(), paramBoolean1, paramBoolean2);
/*  267 */     int i = paramMethodType.parameterCount();
/*  268 */     int j = countNonNull(arrayOfObject1);
/*  269 */     boolean bool1 = (arrayOfObject1[i] != null) ? true : false;
/*  270 */     boolean bool2 = (paramMethodType.returnType() == void.class) ? true : false;
/*  271 */     if (bool1 && bool2) {
/*  272 */       j--;
/*  273 */       bool1 = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  278 */     int k = 1 + i;
/*  279 */     int m = k + j + 1;
/*  280 */     boolean bool3 = !bool1 ? true : (m - 1);
/*  281 */     int n = (!bool1 ? m : bool3) - 1;
/*  282 */     boolean bool4 = bool2 ? true : (m - 1);
/*      */ 
/*      */     
/*  285 */     MethodType methodType = paramMethodType.basicType().invokerType();
/*  286 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(m - k, methodType);
/*      */ 
/*      */ 
/*      */     
/*  290 */     Object[] arrayOfObject2 = new Object[0 + i];
/*      */     
/*  292 */     int i1 = k;
/*  293 */     for (byte b = 0; b < i; b++) {
/*  294 */       Object object1 = arrayOfObject1[b];
/*  295 */       if (object1 == null) {
/*      */         
/*  297 */         arrayOfObject2[0 + b] = arrayOfName[1 + b];
/*      */       } else {
/*      */         LambdaForm.Name name;
/*      */ 
/*      */         
/*  302 */         if (object1 instanceof Class) {
/*  303 */           Class clazz = (Class)object1;
/*  304 */           name = new LambdaForm.Name(Lazy.MH_castReference, new Object[] { clazz, arrayOfName[1 + b] });
/*      */         } else {
/*  306 */           MethodHandle methodHandle = (MethodHandle)object1;
/*  307 */           name = new LambdaForm.Name(methodHandle, new Object[] { arrayOfName[1 + b] });
/*      */         } 
/*  309 */         assert arrayOfName[i1] == null;
/*  310 */         arrayOfName[i1++] = name;
/*  311 */         assert arrayOfObject2[0 + b] == null;
/*  312 */         arrayOfObject2[0 + b] = name;
/*      */       } 
/*      */     } 
/*      */     
/*  316 */     assert i1 == n;
/*  317 */     arrayOfName[n] = new LambdaForm.Name(paramMethodHandle, arrayOfObject2);
/*      */     
/*  319 */     Object object = arrayOfObject1[i];
/*  320 */     if (!bool1) {
/*  321 */       assert n == arrayOfName.length - 1;
/*      */     } else {
/*      */       LambdaForm.Name name;
/*  324 */       if (object == void.class) {
/*  325 */         name = new LambdaForm.Name(LambdaForm.constantZero(LambdaForm.BasicType.basicType(paramMethodType.returnType())), new Object[0]);
/*  326 */       } else if (object instanceof Class) {
/*  327 */         Class clazz = (Class)object;
/*  328 */         name = new LambdaForm.Name(Lazy.MH_castReference, new Object[] { clazz, arrayOfName[n] });
/*      */       } else {
/*  330 */         MethodHandle methodHandle = (MethodHandle)object;
/*  331 */         if (methodHandle.type().parameterCount() == 0) {
/*  332 */           name = new LambdaForm.Name(methodHandle, new Object[0]);
/*      */         } else {
/*  334 */           name = new LambdaForm.Name(methodHandle, new Object[] { arrayOfName[n] });
/*      */         } 
/*  336 */       }  assert arrayOfName[bool3] == null;
/*  337 */       arrayOfName[bool3] = name;
/*  338 */       assert bool3 == arrayOfName.length - 1;
/*      */     } 
/*      */     
/*  341 */     LambdaForm lambdaForm = new LambdaForm("convert", methodType.parameterCount(), arrayOfName, bool4);
/*  342 */     return SimpleMethodHandle.make(paramMethodType, lambdaForm);
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
/*      */   @ForceInline
/*      */   static <T, U> T castReference(Class<? extends T> paramClass, U paramU) {
/*  355 */     if (paramU != null && !paramClass.isInstance(paramU))
/*  356 */       throw newClassCastException(paramClass, paramU); 
/*  357 */     return (T)paramU;
/*      */   }
/*      */   
/*      */   private static ClassCastException newClassCastException(Class<?> paramClass, Object paramObject) {
/*  361 */     return new ClassCastException("Cannot cast " + paramObject.getClass().getName() + " to " + paramClass.getName());
/*      */   }
/*      */ 
/*      */   
/*      */   static Object[] computeValueConversions(MethodType paramMethodType1, MethodType paramMethodType2, boolean paramBoolean1, boolean paramBoolean2) {
/*  366 */     int i = paramMethodType1.parameterCount();
/*  367 */     Object[] arrayOfObject = new Object[i + 1];
/*  368 */     for (byte b = 0; b <= i; b++) {
/*  369 */       boolean bool = (b == i) ? true : false;
/*  370 */       Class<?> clazz1 = bool ? paramMethodType2.returnType() : paramMethodType1.parameterType(b);
/*  371 */       Class<?> clazz2 = bool ? paramMethodType1.returnType() : paramMethodType2.parameterType(b);
/*  372 */       if (!VerifyType.isNullConversion(clazz1, clazz2, paramBoolean1)) {
/*  373 */         arrayOfObject[b] = valueConversion(clazz1, clazz2, paramBoolean1, paramBoolean2);
/*      */       }
/*      */     } 
/*  376 */     return arrayOfObject;
/*      */   }
/*      */   
/*      */   static MethodHandle makePairwiseConvert(MethodHandle paramMethodHandle, MethodType paramMethodType, boolean paramBoolean) {
/*  380 */     return makePairwiseConvert(paramMethodHandle, paramMethodType, paramBoolean, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object valueConversion(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean1, boolean paramBoolean2) {
/*      */     MethodHandle methodHandle;
/*  390 */     assert !VerifyType.isNullConversion(paramClass1, paramClass2, paramBoolean1);
/*  391 */     if (paramClass2 == void.class) {
/*  392 */       return paramClass2;
/*      */     }
/*  394 */     if (paramClass1.isPrimitive()) {
/*  395 */       if (paramClass1 == void.class)
/*  396 */         return void.class; 
/*  397 */       if (paramClass2.isPrimitive()) {
/*      */         
/*  399 */         methodHandle = ValueConversions.convertPrimitive(paramClass1, paramClass2);
/*      */       } else {
/*      */         
/*  402 */         Wrapper wrapper = Wrapper.forPrimitiveType(paramClass1);
/*  403 */         methodHandle = ValueConversions.boxExact(wrapper);
/*  404 */         assert methodHandle.type().parameterType(0) == wrapper.primitiveType();
/*  405 */         assert methodHandle.type().returnType() == wrapper.wrapperType();
/*  406 */         if (!VerifyType.isNullConversion(wrapper.wrapperType(), paramClass2, paramBoolean1)) {
/*      */           
/*  408 */           MethodType methodType = MethodType.methodType(paramClass2, paramClass1);
/*  409 */           if (paramBoolean1)
/*  410 */           { methodHandle = methodHandle.asType(methodType); }
/*      */           else
/*  412 */           { methodHandle = makePairwiseConvert(methodHandle, methodType, false); } 
/*      */         } 
/*      */       } 
/*  415 */     } else if (paramClass2.isPrimitive()) {
/*  416 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass2);
/*  417 */       if (paramBoolean2 || paramClass1 == wrapper.wrapperType())
/*      */       {
/*  419 */         methodHandle = ValueConversions.unboxExact(wrapper, paramBoolean1);
/*      */ 
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/*  426 */         methodHandle = paramBoolean1 ? ValueConversions.unboxWiden(wrapper) : ValueConversions.unboxCast(wrapper);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  433 */       return paramClass2;
/*      */     } 
/*  435 */     assert methodHandle.type().parameterCount() <= 1 : "pc" + Arrays.asList((T[])new Object[] { paramClass1.getSimpleName(), paramClass2.getSimpleName(), methodHandle });
/*  436 */     return methodHandle;
/*      */   }
/*      */   
/*      */   static MethodHandle makeVarargsCollector(MethodHandle paramMethodHandle, Class<?> paramClass) {
/*  440 */     MethodType methodType = paramMethodHandle.type();
/*  441 */     int i = methodType.parameterCount() - 1;
/*  442 */     if (methodType.parameterType(i) != paramClass)
/*  443 */       paramMethodHandle = paramMethodHandle.asType(methodType.changeParameterType(i, paramClass)); 
/*  444 */     paramMethodHandle = paramMethodHandle.asFixedArity();
/*  445 */     return new AsVarargsCollector(paramMethodHandle, paramClass);
/*      */   }
/*      */   
/*      */   private static final class AsVarargsCollector extends DelegatingMethodHandle { private final MethodHandle target;
/*      */     private final Class<?> arrayType;
/*      */     @Stable
/*      */     private MethodHandle asCollectorCache;
/*      */     
/*      */     AsVarargsCollector(MethodHandle param1MethodHandle, Class<?> param1Class) {
/*  454 */       this(param1MethodHandle.type(), param1MethodHandle, param1Class);
/*      */     }
/*      */     AsVarargsCollector(MethodType param1MethodType, MethodHandle param1MethodHandle, Class<?> param1Class) {
/*  457 */       super(param1MethodType, param1MethodHandle);
/*  458 */       this.target = param1MethodHandle;
/*  459 */       this.arrayType = param1Class;
/*  460 */       this.asCollectorCache = param1MethodHandle.asCollector(param1Class, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isVarargsCollector() {
/*  465 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected MethodHandle getTarget() {
/*  470 */       return this.target;
/*      */     }
/*      */ 
/*      */     
/*      */     public MethodHandle asFixedArity() {
/*  475 */       return this.target;
/*      */     }
/*      */ 
/*      */     
/*      */     MethodHandle setVarargs(MemberName param1MemberName) {
/*  480 */       if (param1MemberName.isVarargs()) return this; 
/*  481 */       return asFixedArity();
/*      */     }
/*      */     
/*      */     public MethodHandle asTypeUncached(MethodType param1MethodType) {
/*      */       MethodHandle methodHandle2;
/*  486 */       MethodType methodType = type();
/*  487 */       int i = methodType.parameterCount() - 1;
/*  488 */       int j = param1MethodType.parameterCount();
/*  489 */       if (j == i + 1 && methodType
/*  490 */         .parameterType(i).isAssignableFrom(param1MethodType.parameterType(i)))
/*      */       {
/*  492 */         return this.asTypeCache = asFixedArity().asType(param1MethodType);
/*      */       }
/*      */       
/*  495 */       MethodHandle methodHandle1 = this.asCollectorCache;
/*  496 */       if (methodHandle1 != null && methodHandle1.type().parameterCount() == j) {
/*  497 */         return this.asTypeCache = methodHandle1.asType(param1MethodType);
/*      */       }
/*  499 */       int k = j - i;
/*      */       
/*      */       try {
/*  502 */         methodHandle2 = asFixedArity().asCollector(this.arrayType, k);
/*  503 */         assert methodHandle2.type().parameterCount() == j : "newArity=" + j + " but collector=" + methodHandle2;
/*  504 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  505 */         throw new WrongMethodTypeException("cannot build collector", illegalArgumentException);
/*      */       } 
/*  507 */       this.asCollectorCache = methodHandle2;
/*  508 */       return this.asTypeCache = methodHandle2.asType(param1MethodType);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean viewAsTypeChecks(MethodType param1MethodType, boolean param1Boolean) {
/*  513 */       super.viewAsTypeChecks(param1MethodType, true);
/*  514 */       if (param1Boolean) return true;
/*      */       
/*  516 */       assert type().lastParameterType().getComponentType()
/*  517 */         .isAssignableFrom(param1MethodType
/*  518 */           .lastParameterType().getComponentType()) : 
/*  519 */         Arrays.asList((T[])new Object[] { this, param1MethodType });
/*  520 */       return true;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle makeSpreadArguments(MethodHandle paramMethodHandle, Class<?> paramClass, int paramInt1, int paramInt2) {
/*  527 */     MethodType methodType1 = paramMethodHandle.type();
/*      */     
/*  529 */     for (byte b1 = 0; b1 < paramInt2; b1++) {
/*  530 */       Class<?> clazz = VerifyType.spreadArgElementType(paramClass, b1);
/*  531 */       if (clazz == null) clazz = Object.class; 
/*  532 */       methodType1 = methodType1.changeParameterType(paramInt1 + b1, clazz);
/*      */     } 
/*  534 */     paramMethodHandle = paramMethodHandle.asType(methodType1);
/*      */ 
/*      */     
/*  537 */     MethodType methodType2 = methodType1.replaceParameterTypes(paramInt1, paramInt1 + paramInt2, new Class[] { paramClass });
/*      */     
/*  539 */     MethodType methodType3 = methodType2.invokerType();
/*  540 */     LambdaForm.Name[] arrayOfName1 = LambdaForm.arguments(paramInt2 + 2, methodType3);
/*  541 */     int i = methodType3.parameterCount();
/*  542 */     int[] arrayOfInt = new int[methodType1.parameterCount()];
/*      */     byte b3;
/*  544 */     for (byte b2 = 0; b2 < methodType1.parameterCount() + 1; b2++, b3++) {
/*  545 */       Class<?> clazz = methodType3.parameterType(b2);
/*  546 */       if (b2 == paramInt1) {
/*      */         
/*  548 */         MethodHandle methodHandle = MethodHandles.arrayElementGetter(paramClass);
/*  549 */         LambdaForm.Name name = arrayOfName1[b3];
/*  550 */         arrayOfName1[i++] = new LambdaForm.Name(Lazy.NF_checkSpreadArgument, new Object[] { name, Integer.valueOf(paramInt2) });
/*  551 */         for (byte b = 0; b < paramInt2; b2++, b++) {
/*  552 */           arrayOfInt[b2] = i;
/*  553 */           arrayOfName1[i++] = new LambdaForm.Name(methodHandle, new Object[] { name, Integer.valueOf(b) });
/*      */         } 
/*  555 */       } else if (b2 < arrayOfInt.length) {
/*  556 */         arrayOfInt[b2] = b3;
/*      */       } 
/*      */     } 
/*  559 */     assert i == arrayOfName1.length - 1;
/*      */ 
/*      */     
/*  562 */     LambdaForm.Name[] arrayOfName2 = new LambdaForm.Name[methodType1.parameterCount()];
/*  563 */     for (b3 = 0; b3 < methodType1.parameterCount(); b3++) {
/*  564 */       int j = arrayOfInt[b3];
/*  565 */       arrayOfName2[b3] = arrayOfName1[j];
/*      */     } 
/*  567 */     arrayOfName1[arrayOfName1.length - 1] = new LambdaForm.Name(paramMethodHandle, (Object[])arrayOfName2);
/*      */     
/*  569 */     LambdaForm lambdaForm = new LambdaForm("spread", methodType3.parameterCount(), arrayOfName1);
/*  570 */     return SimpleMethodHandle.make(methodType2, lambdaForm);
/*      */   }
/*      */   
/*      */   static void checkSpreadArgument(Object paramObject, int paramInt) {
/*  574 */     if (paramObject == null)
/*  575 */     { if (paramInt == 0)
/*  576 */         return;  } else if (paramObject instanceof Object[])
/*  577 */     { int i = ((Object[])paramObject).length;
/*  578 */       if (i == paramInt)
/*      */         return;  }
/*  580 */     else { int i = Array.getLength(paramObject);
/*  581 */       if (i == paramInt)
/*      */         return;  }
/*      */     
/*  584 */     throw MethodHandleStatics.newIllegalArgumentException("array is not of length " + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class Lazy
/*      */   {
/*  592 */     private static final Class<?> MHI = MethodHandleImpl.class;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  610 */     private static final MethodHandle[] ARRAYS = MethodHandleImpl.makeArrays();
/*  611 */     private static final MethodHandle[] FILL_ARRAYS = MethodHandleImpl.makeFillArrays(); static final LambdaForm.NamedFunction NF_checkSpreadArgument; static final LambdaForm.NamedFunction NF_guardWithCatch; static final LambdaForm.NamedFunction NF_throwException; static final LambdaForm.NamedFunction NF_profileBoolean; static final MethodHandle MH_castReference;
/*      */     static {
/*      */       try {
/*  614 */         NF_checkSpreadArgument = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("checkSpreadArgument", new Class[] { Object.class, int.class }));
/*  615 */         NF_guardWithCatch = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("guardWithCatch", new Class[] { MethodHandle.class, Class.class, MethodHandle.class, Object[].class }));
/*      */         
/*  617 */         NF_throwException = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("throwException", new Class[] { Throwable.class }));
/*  618 */         NF_profileBoolean = new LambdaForm.NamedFunction(MHI.getDeclaredMethod("profileBoolean", new Class[] { boolean.class, int[].class }));
/*      */         
/*  620 */         NF_checkSpreadArgument.resolve();
/*  621 */         NF_guardWithCatch.resolve();
/*  622 */         NF_throwException.resolve();
/*  623 */         NF_profileBoolean.resolve();
/*      */         
/*  625 */         MH_castReference = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "castReference", 
/*  626 */             MethodType.methodType(Object.class, Class.class, new Class[] { Object.class }));
/*  627 */         MH_copyAsPrimitiveArray = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "copyAsPrimitiveArray", 
/*  628 */             MethodType.methodType(Object.class, Wrapper.class, new Class[] { Object[].class }));
/*  629 */         MH_arrayIdentity = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "identity", 
/*  630 */             MethodType.methodType(Object[].class, Object[].class));
/*  631 */         MH_fillNewArray = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "fillNewArray", 
/*  632 */             MethodType.methodType(Object[].class, Integer.class, new Class[] { Object[].class }));
/*  633 */         MH_fillNewTypedArray = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MHI, "fillNewTypedArray", 
/*  634 */             MethodType.methodType(Object[].class, Object[].class, new Class[] { Integer.class, Object[].class }));
/*      */         
/*  636 */         MH_selectAlternative = MethodHandleImpl.makeIntrinsic(MethodHandles.Lookup.IMPL_LOOKUP
/*  637 */             .findStatic(MHI, "selectAlternative", 
/*  638 */               MethodType.methodType(MethodHandle.class, boolean.class, new Class[] { MethodHandle.class, MethodHandle.class })), MethodHandleImpl.Intrinsic.SELECT_ALTERNATIVE);
/*      */       }
/*  640 */       catch (ReflectiveOperationException reflectiveOperationException) {
/*  641 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*      */       } 
/*      */     }
/*      */     static final MethodHandle MH_selectAlternative; static final MethodHandle MH_copyAsPrimitiveArray; static final MethodHandle MH_fillNewTypedArray;
/*      */     static final MethodHandle MH_fillNewArray;
/*      */     static final MethodHandle MH_arrayIdentity; }
/*      */   
/*      */   static MethodHandle makeCollectArguments(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, int paramInt, boolean paramBoolean) {
/*  649 */     MethodType methodType1 = paramMethodHandle1.type();
/*  650 */     MethodType methodType2 = paramMethodHandle2.type();
/*  651 */     int i = methodType2.parameterCount();
/*  652 */     Class<?> clazz = methodType2.returnType();
/*  653 */     byte b = (clazz == void.class) ? 0 : 1;
/*      */     
/*  655 */     MethodType methodType3 = methodType1.dropParameterTypes(paramInt, paramInt + b);
/*  656 */     if (!paramBoolean) {
/*  657 */       methodType3 = methodType3.insertParameterTypes(paramInt, methodType2.parameterList());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  664 */     MethodType methodType4 = methodType3.invokerType();
/*  665 */     LambdaForm.Name[] arrayOfName1 = LambdaForm.arguments(2, methodType4);
/*  666 */     int j = arrayOfName1.length - 2;
/*  667 */     int k = arrayOfName1.length - 1;
/*      */     
/*  669 */     LambdaForm.Name[] arrayOfName2 = Arrays.<LambdaForm.Name>copyOfRange(arrayOfName1, 1 + paramInt, 1 + paramInt + i);
/*  670 */     arrayOfName1[j] = new LambdaForm.Name(paramMethodHandle2, (Object[])arrayOfName2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  675 */     LambdaForm.Name[] arrayOfName3 = new LambdaForm.Name[methodType1.parameterCount()];
/*  676 */     int m = 1;
/*  677 */     int n = 0;
/*  678 */     int i1 = paramInt;
/*  679 */     System.arraycopy(arrayOfName1, m, arrayOfName3, n, i1);
/*  680 */     m += i1;
/*  681 */     n += i1;
/*  682 */     if (clazz != void.class) {
/*  683 */       arrayOfName3[n++] = arrayOfName1[j];
/*      */     }
/*  685 */     i1 = i;
/*  686 */     if (paramBoolean) {
/*  687 */       System.arraycopy(arrayOfName1, m, arrayOfName3, n, i1);
/*  688 */       n += i1;
/*      */     } 
/*  690 */     m += i1;
/*  691 */     i1 = arrayOfName3.length - n;
/*  692 */     System.arraycopy(arrayOfName1, m, arrayOfName3, n, i1);
/*  693 */     assert m + i1 == j;
/*  694 */     arrayOfName1[k] = new LambdaForm.Name(paramMethodHandle1, (Object[])arrayOfName3);
/*      */     
/*  696 */     LambdaForm lambdaForm = new LambdaForm("collect", methodType4.parameterCount(), arrayOfName1);
/*  697 */     return SimpleMethodHandle.make(methodType3, lambdaForm);
/*      */   }
/*      */ 
/*      */   
/*      */   @Hidden
/*      */   static MethodHandle selectAlternative(boolean paramBoolean, MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2) {
/*  703 */     if (paramBoolean) {
/*  704 */       return paramMethodHandle1;
/*      */     }
/*  706 */     return paramMethodHandle2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Hidden
/*      */   static boolean profileBoolean(boolean paramBoolean, int[] paramArrayOfint) {
/*  715 */     boolean bool = paramBoolean ? true : false;
/*      */     try {
/*  717 */       paramArrayOfint[bool] = Math.addExact(paramArrayOfint[bool], 1);
/*  718 */     } catch (ArithmeticException arithmeticException) {
/*      */       
/*  720 */       paramArrayOfint[bool] = paramArrayOfint[bool] / 2;
/*      */     } 
/*  722 */     return paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle makeGuardWithTest(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, MethodHandle paramMethodHandle3) {
/*      */     BoundMethodHandle boundMethodHandle;
/*  729 */     MethodType methodType1 = paramMethodHandle2.type();
/*  730 */     assert paramMethodHandle1.type().equals(methodType1.changeReturnType(boolean.class)) && paramMethodHandle3.type().equals(methodType1);
/*  731 */     MethodType methodType2 = methodType1.basicType();
/*  732 */     LambdaForm lambdaForm = makeGuardWithTestForm(methodType2);
/*      */     
/*      */     try {
/*  735 */       if (MethodHandleStatics.PROFILE_GWT) {
/*  736 */         int[] arrayOfInt = new int[2];
/*      */         
/*  738 */         boundMethodHandle = BoundMethodHandle.speciesData_LLLL().constructor().invokeBasic(methodType1, lambdaForm, paramMethodHandle1, 
/*  739 */             profile(paramMethodHandle2), profile(paramMethodHandle3), arrayOfInt);
/*      */       } else {
/*      */         
/*  742 */         boundMethodHandle = BoundMethodHandle.speciesData_LLL().constructor().invokeBasic(methodType1, lambdaForm, paramMethodHandle1, 
/*  743 */             profile(paramMethodHandle2), profile(paramMethodHandle3));
/*      */       } 
/*  745 */     } catch (Throwable throwable) {
/*  746 */       throw MethodHandleStatics.uncaughtException(throwable);
/*      */     } 
/*  748 */     assert boundMethodHandle.type() == methodType1;
/*  749 */     return boundMethodHandle;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle profile(MethodHandle paramMethodHandle) {
/*  755 */     if (MethodHandleStatics.DONT_INLINE_THRESHOLD >= 0) {
/*  756 */       return makeBlockInlningWrapper(paramMethodHandle);
/*      */     }
/*  758 */     return paramMethodHandle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle makeBlockInlningWrapper(MethodHandle paramMethodHandle) {
/*  768 */     LambdaForm lambdaForm = PRODUCE_BLOCK_INLINING_FORM.apply(paramMethodHandle);
/*  769 */     return new CountingWrapper(paramMethodHandle, lambdaForm, PRODUCE_BLOCK_INLINING_FORM, PRODUCE_REINVOKER_FORM, MethodHandleStatics.DONT_INLINE_THRESHOLD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  775 */   private static final Function<MethodHandle, LambdaForm> PRODUCE_BLOCK_INLINING_FORM = new Function<MethodHandle, LambdaForm>()
/*      */     {
/*      */       public LambdaForm apply(MethodHandle param1MethodHandle) {
/*  778 */         return DelegatingMethodHandle.makeReinvokerForm(param1MethodHandle, 9, MethodHandleImpl.CountingWrapper.class, "reinvoker.dontInline", false, DelegatingMethodHandle.NF_getTarget, MethodHandleImpl.CountingWrapper.NF_maybeStopCounting);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  785 */   private static final Function<MethodHandle, LambdaForm> PRODUCE_REINVOKER_FORM = new Function<MethodHandle, LambdaForm>()
/*      */     {
/*      */       public LambdaForm apply(MethodHandle param1MethodHandle) {
/*  788 */         return DelegatingMethodHandle.makeReinvokerForm(param1MethodHandle, 8, DelegatingMethodHandle.class, DelegatingMethodHandle.NF_getTarget);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   static class CountingWrapper
/*      */     extends DelegatingMethodHandle
/*      */   {
/*      */     private final MethodHandle target;
/*      */     
/*      */     private int count;
/*      */     
/*      */     private Function<MethodHandle, LambdaForm> countingFormProducer;
/*      */     
/*      */     private Function<MethodHandle, LambdaForm> nonCountingFormProducer;
/*      */     
/*      */     private volatile boolean isCounting;
/*      */     
/*      */     static final LambdaForm.NamedFunction NF_maybeStopCounting;
/*      */ 
/*      */     
/*      */     private CountingWrapper(MethodHandle param1MethodHandle, LambdaForm param1LambdaForm, Function<MethodHandle, LambdaForm> param1Function1, Function<MethodHandle, LambdaForm> param1Function2, int param1Int) {
/*  810 */       super(param1MethodHandle.type(), param1LambdaForm);
/*  811 */       this.target = param1MethodHandle;
/*  812 */       this.count = param1Int;
/*  813 */       this.countingFormProducer = param1Function1;
/*  814 */       this.nonCountingFormProducer = param1Function2;
/*  815 */       this.isCounting = (param1Int > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     @Hidden
/*      */     protected MethodHandle getTarget() {
/*  821 */       return this.target;
/*      */     }
/*      */ 
/*      */     
/*      */     public MethodHandle asTypeUncached(MethodType param1MethodType) {
/*  826 */       MethodHandle methodHandle2, methodHandle1 = this.target.asType(param1MethodType);
/*      */       
/*  828 */       if (this.isCounting) {
/*      */         
/*  830 */         LambdaForm lambdaForm = this.countingFormProducer.apply(methodHandle1);
/*  831 */         methodHandle2 = new CountingWrapper(methodHandle1, lambdaForm, this.countingFormProducer, this.nonCountingFormProducer, MethodHandleStatics.DONT_INLINE_THRESHOLD);
/*      */       } else {
/*  833 */         methodHandle2 = methodHandle1;
/*      */       } 
/*  835 */       return this.asTypeCache = methodHandle2;
/*      */     }
/*      */     
/*      */     boolean countDown() {
/*  839 */       if (this.count <= 0) {
/*      */         
/*  841 */         if (this.isCounting) {
/*  842 */           this.isCounting = false;
/*  843 */           return true;
/*      */         } 
/*  845 */         return false;
/*      */       } 
/*      */       
/*  848 */       this.count--;
/*  849 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     @Hidden
/*      */     static void maybeStopCounting(Object param1Object) {
/*  855 */       CountingWrapper countingWrapper = (CountingWrapper)param1Object;
/*  856 */       if (countingWrapper.countDown()) {
/*      */         
/*  858 */         LambdaForm lambdaForm = countingWrapper.nonCountingFormProducer.apply(countingWrapper.target);
/*  859 */         lambdaForm.compileToBytecode();
/*  860 */         countingWrapper.updateForm(lambdaForm);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     static {
/*  866 */       Class<CountingWrapper> clazz = CountingWrapper.class;
/*      */       try {
/*  868 */         NF_maybeStopCounting = new LambdaForm.NamedFunction(clazz.getDeclaredMethod("maybeStopCounting", new Class[] { Object.class }));
/*  869 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/*  870 */         throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static LambdaForm makeGuardWithTestForm(MethodType paramMethodType) {
/*  877 */     LambdaForm lambdaForm = paramMethodType.form().cachedLambdaForm(17);
/*  878 */     if (lambdaForm != null) return lambdaForm;
/*      */ 
/*      */     
/*  881 */     int i = 1 + paramMethodType.parameterCount();
/*  882 */     int j = i;
/*  883 */     int k = j++;
/*  884 */     int m = j++;
/*  885 */     int n = j++;
/*  886 */     boolean bool1 = MethodHandleStatics.PROFILE_GWT ? j++ : true;
/*  887 */     int i1 = j++;
/*  888 */     boolean bool2 = (bool1 != -1) ? j++ : true;
/*  889 */     int i2 = j - 1;
/*  890 */     int i3 = j++;
/*  891 */     int i4 = j++;
/*  892 */     assert i4 == i3 + 1;
/*      */     
/*  894 */     MethodType methodType1 = paramMethodType.invokerType();
/*  895 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, methodType1);
/*      */ 
/*      */ 
/*      */     
/*  899 */     BoundMethodHandle.SpeciesData speciesData = (bool1 != -1) ? BoundMethodHandle.speciesData_LLLL() : BoundMethodHandle.speciesData_LLL();
/*  900 */     arrayOfName[0] = arrayOfName[0].withConstraint(speciesData);
/*  901 */     arrayOfName[k] = new LambdaForm.Name(speciesData.getterFunction(0), new Object[] { arrayOfName[0] });
/*  902 */     arrayOfName[m] = new LambdaForm.Name(speciesData.getterFunction(1), new Object[] { arrayOfName[0] });
/*  903 */     arrayOfName[n] = new LambdaForm.Name(speciesData.getterFunction(2), new Object[] { arrayOfName[0] });
/*  904 */     if (bool1 != -1) {
/*  905 */       arrayOfName[bool1] = new LambdaForm.Name(speciesData.getterFunction(3), new Object[] { arrayOfName[0] });
/*      */     }
/*  907 */     Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 0, i, Object[].class);
/*      */ 
/*      */     
/*  910 */     MethodType methodType2 = paramMethodType.changeReturnType(boolean.class).basicType();
/*  911 */     arrayOfObject[0] = arrayOfName[k];
/*  912 */     arrayOfName[i1] = new LambdaForm.Name(methodType2, arrayOfObject);
/*      */ 
/*      */     
/*  915 */     if (bool2 != -1) {
/*  916 */       arrayOfName[bool2] = new LambdaForm.Name(Lazy.NF_profileBoolean, new Object[] { arrayOfName[i1], arrayOfName[bool1] });
/*      */     }
/*      */     
/*  919 */     arrayOfName[i3] = new LambdaForm.Name(Lazy.MH_selectAlternative, new Object[] { arrayOfName[i2], arrayOfName[m], arrayOfName[n] });
/*      */ 
/*      */     
/*  922 */     arrayOfObject[0] = arrayOfName[i3];
/*  923 */     arrayOfName[i4] = new LambdaForm.Name(paramMethodType, arrayOfObject);
/*      */     
/*  925 */     lambdaForm = new LambdaForm("guard", methodType1.parameterCount(), arrayOfName, true);
/*      */     
/*  927 */     return paramMethodType.form().setCachedLambdaForm(17, lambdaForm);
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
/*      */   private static LambdaForm makeGuardWithCatchForm(MethodType paramMethodType) {
/*  952 */     MethodType methodType1 = paramMethodType.invokerType();
/*      */     
/*  954 */     LambdaForm lambdaForm = paramMethodType.form().cachedLambdaForm(16);
/*  955 */     if (lambdaForm != null) {
/*  956 */       return lambdaForm;
/*      */     }
/*      */ 
/*      */     
/*  960 */     int i = 1 + paramMethodType.parameterCount();
/*      */     
/*  962 */     int j = i;
/*  963 */     int k = j++;
/*  964 */     int m = j++;
/*  965 */     int n = j++;
/*  966 */     int i1 = j++;
/*  967 */     int i2 = j++;
/*  968 */     int i3 = j++;
/*  969 */     int i4 = j++;
/*  970 */     int i5 = j++;
/*      */     
/*  972 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, methodType1);
/*      */     
/*  974 */     BoundMethodHandle.SpeciesData speciesData = BoundMethodHandle.speciesData_LLLLL();
/*  975 */     arrayOfName[0] = arrayOfName[0].withConstraint(speciesData);
/*  976 */     arrayOfName[k] = new LambdaForm.Name(speciesData.getterFunction(0), new Object[] { arrayOfName[0] });
/*  977 */     arrayOfName[m] = new LambdaForm.Name(speciesData.getterFunction(1), new Object[] { arrayOfName[0] });
/*  978 */     arrayOfName[n] = new LambdaForm.Name(speciesData.getterFunction(2), new Object[] { arrayOfName[0] });
/*  979 */     arrayOfName[i1] = new LambdaForm.Name(speciesData.getterFunction(3), new Object[] { arrayOfName[0] });
/*  980 */     arrayOfName[i2] = new LambdaForm.Name(speciesData.getterFunction(4), new Object[] { arrayOfName[0] });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  985 */     MethodType methodType2 = paramMethodType.changeReturnType(Object.class);
/*  986 */     MethodHandle methodHandle1 = MethodHandles.basicInvoker(methodType2);
/*  987 */     Object[] arrayOfObject1 = new Object[methodHandle1.type().parameterCount()];
/*  988 */     arrayOfObject1[0] = arrayOfName[i1];
/*  989 */     System.arraycopy(arrayOfName, 1, arrayOfObject1, 1, i - 1);
/*  990 */     arrayOfName[i3] = new LambdaForm.Name(makeIntrinsic(methodHandle1, Intrinsic.GUARD_WITH_CATCH), arrayOfObject1);
/*      */ 
/*      */     
/*  993 */     Object[] arrayOfObject2 = { arrayOfName[k], arrayOfName[m], arrayOfName[n], arrayOfName[i3] };
/*  994 */     arrayOfName[i4] = new LambdaForm.Name(Lazy.NF_guardWithCatch, arrayOfObject2);
/*      */ 
/*      */     
/*  997 */     MethodHandle methodHandle2 = MethodHandles.basicInvoker(MethodType.methodType(paramMethodType.rtype(), Object.class));
/*  998 */     Object[] arrayOfObject3 = { arrayOfName[i2], arrayOfName[i4] };
/*  999 */     arrayOfName[i5] = new LambdaForm.Name(methodHandle2, arrayOfObject3);
/*      */     
/* 1001 */     lambdaForm = new LambdaForm("guardWithCatch", methodType1.parameterCount(), arrayOfName);
/*      */     
/* 1003 */     return paramMethodType.form().setCachedLambdaForm(16, lambdaForm);
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle makeGuardWithCatch(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2) {
/*      */     MethodHandle methodHandle2;
/*      */     BoundMethodHandle boundMethodHandle;
/* 1010 */     MethodType methodType1 = paramMethodHandle1.type();
/* 1011 */     LambdaForm lambdaForm = makeGuardWithCatchForm(methodType1.basicType());
/*      */ 
/*      */ 
/*      */     
/* 1015 */     MethodType methodType2 = methodType1.changeReturnType(Object[].class);
/* 1016 */     MethodHandle methodHandle1 = varargsArray(methodType1.parameterCount()).asType(methodType2);
/*      */ 
/*      */     
/* 1019 */     Class<?> clazz = methodType1.returnType();
/* 1020 */     if (clazz.isPrimitive()) {
/* 1021 */       if (clazz == void.class) {
/* 1022 */         methodHandle2 = ValueConversions.ignore();
/*      */       } else {
/* 1024 */         Wrapper wrapper = Wrapper.forPrimitiveType(methodType1.returnType());
/* 1025 */         methodHandle2 = ValueConversions.unboxExact(wrapper);
/*      */       } 
/*      */     } else {
/* 1028 */       methodHandle2 = MethodHandles.identity(Object.class);
/*      */     } 
/*      */     
/* 1031 */     BoundMethodHandle.SpeciesData speciesData = BoundMethodHandle.speciesData_LLLLL();
/*      */ 
/*      */     
/*      */     try {
/* 1035 */       boundMethodHandle = speciesData.constructor().invokeBasic(methodType1, lambdaForm, paramMethodHandle1, paramClass, paramMethodHandle2, methodHandle1, methodHandle2);
/*      */     }
/* 1037 */     catch (Throwable throwable) {
/* 1038 */       throw MethodHandleStatics.uncaughtException(throwable);
/*      */     } 
/* 1040 */     assert boundMethodHandle.type() == methodType1;
/* 1041 */     return boundMethodHandle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Hidden
/*      */   static Object guardWithCatch(MethodHandle paramMethodHandle1, Class<? extends Throwable> paramClass, MethodHandle paramMethodHandle2, Object... paramVarArgs) throws Throwable {
/*      */     try {
/* 1053 */       return paramMethodHandle1.asFixedArity().invokeWithArguments(paramVarArgs);
/* 1054 */     } catch (Throwable throwable) {
/* 1055 */       if (!paramClass.isInstance(throwable)) throw throwable; 
/* 1056 */       return paramMethodHandle2.asFixedArity().invokeWithArguments(prepend(throwable, paramVarArgs));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Hidden
/*      */   private static Object[] prepend(Object paramObject, Object[] paramArrayOfObject) {
/* 1063 */     Object[] arrayOfObject = new Object[paramArrayOfObject.length + 1];
/* 1064 */     arrayOfObject[0] = paramObject;
/* 1065 */     System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 1, paramArrayOfObject.length);
/* 1066 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */   
/*      */   static MethodHandle throwException(MethodType paramMethodType) {
/* 1071 */     assert Throwable.class.isAssignableFrom(paramMethodType.parameterType(0));
/* 1072 */     int i = paramMethodType.parameterCount();
/* 1073 */     if (i > 1) {
/* 1074 */       MethodHandle methodHandle = throwException(paramMethodType.dropParameterTypes(1, i));
/* 1075 */       methodHandle = MethodHandles.dropArguments(methodHandle, 1, paramMethodType.parameterList().subList(1, i));
/* 1076 */       return methodHandle;
/*      */     } 
/* 1078 */     return makePairwiseConvert(Lazy.NF_throwException.resolvedHandle(), paramMethodType, false, true);
/*      */   }
/*      */   static <T extends Throwable> Empty throwException(T paramT) throws T {
/* 1081 */     throw paramT;
/*      */   }
/* 1083 */   static MethodHandle[] FAKE_METHOD_HANDLE_INVOKE = new MethodHandle[2];
/*      */   static MethodHandle fakeMethodHandleInvoke(MemberName paramMemberName) {
/*      */     boolean bool;
/* 1086 */     assert paramMemberName.isMethodHandleInvoke();
/* 1087 */     switch (paramMemberName.getName()) { case "invoke":
/* 1088 */         bool = false; break;
/* 1089 */       case "invokeExact": bool = true; break;
/* 1090 */       default: throw new InternalError(paramMemberName.getName()); }
/*      */     
/* 1092 */     MethodHandle methodHandle = FAKE_METHOD_HANDLE_INVOKE[bool];
/* 1093 */     if (methodHandle != null) return methodHandle; 
/* 1094 */     MethodType methodType = MethodType.methodType(Object.class, UnsupportedOperationException.class, new Class[] { MethodHandle.class, Object[].class });
/*      */     
/* 1096 */     methodHandle = throwException(methodType);
/* 1097 */     methodHandle = methodHandle.bindTo(new UnsupportedOperationException("cannot reflectively invoke MethodHandle"));
/* 1098 */     if (!paramMemberName.getInvocationType().equals(methodHandle.type()))
/* 1099 */       throw new InternalError(paramMemberName.toString()); 
/* 1100 */     methodHandle = methodHandle.withInternalMemberName(paramMemberName, false);
/* 1101 */     methodHandle = methodHandle.asVarargsCollector(Object[].class);
/* 1102 */     assert paramMemberName.isVarargs();
/* 1103 */     FAKE_METHOD_HANDLE_INVOKE[bool] = methodHandle;
/* 1104 */     return methodHandle;
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
/*      */   static MethodHandle bindCaller(MethodHandle paramMethodHandle, Class<?> paramClass) {
/* 1117 */     return BindCaller.bindCaller(paramMethodHandle, paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class BindCaller
/*      */   {
/*      */     static MethodHandle bindCaller(MethodHandle param1MethodHandle, Class<?> param1Class) {
/* 1126 */       if (param1Class == null || param1Class
/* 1127 */         .isArray() || param1Class
/* 1128 */         .isPrimitive() || param1Class
/* 1129 */         .getName().startsWith("java.") || param1Class
/* 1130 */         .getName().startsWith("sun.")) {
/* 1131 */         throw new InternalError();
/*      */       }
/*      */       
/* 1134 */       MethodHandle methodHandle1 = prepareForInvoker(param1MethodHandle);
/*      */       
/* 1136 */       MethodHandle methodHandle2 = CV_makeInjectedInvoker.get(param1Class);
/* 1137 */       return restoreToType(methodHandle2.bindTo(methodHandle1), param1MethodHandle, param1Class);
/*      */     }
/*      */     private static MethodHandle makeInjectedInvoker(Class<?> param1Class) {
/*      */       MethodHandle methodHandle;
/* 1141 */       Class<?> clazz = MethodHandleStatics.UNSAFE.defineAnonymousClass(param1Class, T_BYTES, null);
/* 1142 */       if (param1Class.getClassLoader() != clazz.getClassLoader())
/* 1143 */         throw new InternalError(param1Class.getName() + " (CL)"); 
/*      */       try {
/* 1145 */         if (param1Class.getProtectionDomain() != clazz.getProtectionDomain())
/* 1146 */           throw new InternalError(param1Class.getName() + " (PD)"); 
/* 1147 */       } catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1152 */         methodHandle = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(clazz, "init", MethodType.methodType(void.class));
/* 1153 */         methodHandle.invokeExact();
/* 1154 */       } catch (Throwable throwable) {
/* 1155 */         throw MethodHandleStatics.uncaughtException(throwable);
/*      */       } 
/*      */       
/*      */       try {
/* 1159 */         MethodType methodType = MethodType.methodType(Object.class, MethodHandle.class, new Class[] { Object[].class });
/* 1160 */         methodHandle = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(clazz, "invoke_V", methodType);
/* 1161 */       } catch (ReflectiveOperationException reflectiveOperationException) {
/* 1162 */         throw MethodHandleStatics.uncaughtException(reflectiveOperationException);
/*      */       } 
/*      */       
/*      */       try {
/* 1166 */         MethodHandle methodHandle1 = prepareForInvoker(MH_checkCallerClass);
/* 1167 */         Object object = methodHandle.invokeExact(methodHandle1, new Object[] { param1Class, clazz });
/* 1168 */       } catch (Throwable throwable) {
/* 1169 */         throw new InternalError(throwable);
/*      */       } 
/* 1171 */       return methodHandle;
/*      */     }
/* 1173 */     private static ClassValue<MethodHandle> CV_makeInjectedInvoker = new ClassValue<MethodHandle>() {
/*      */         protected MethodHandle computeValue(Class<?> param2Class) {
/* 1175 */           return MethodHandleImpl.BindCaller.makeInjectedInvoker(param2Class);
/*      */         }
/*      */       };
/*      */     private static final MethodHandle MH_checkCallerClass; private static final byte[] T_BYTES;
/*      */     
/*      */     private static MethodHandle prepareForInvoker(MethodHandle param1MethodHandle) {
/* 1181 */       param1MethodHandle = param1MethodHandle.asFixedArity();
/* 1182 */       MethodType methodType = param1MethodHandle.type();
/* 1183 */       int i = methodType.parameterCount();
/* 1184 */       MethodHandle methodHandle = param1MethodHandle.asType(methodType.generic());
/* 1185 */       methodHandle.internalForm().compileToBytecode();
/* 1186 */       methodHandle = methodHandle.asSpreader(Object[].class, i);
/* 1187 */       methodHandle.internalForm().compileToBytecode();
/* 1188 */       return methodHandle;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static MethodHandle restoreToType(MethodHandle param1MethodHandle1, MethodHandle param1MethodHandle2, Class<?> param1Class) {
/* 1195 */       MethodType methodType = param1MethodHandle2.type();
/* 1196 */       MethodHandle methodHandle = param1MethodHandle1.asCollector(Object[].class, methodType.parameterCount());
/* 1197 */       MemberName memberName = param1MethodHandle2.internalMemberName();
/* 1198 */       methodHandle = methodHandle.asType(methodType);
/* 1199 */       methodHandle = new MethodHandleImpl.WrappedMember(methodHandle, methodType, memberName, param1MethodHandle2.isInvokeSpecial(), param1Class);
/* 1200 */       return methodHandle;
/*      */     }
/*      */     
/*      */     static
/*      */     {
/* 1205 */       Class<BindCaller> clazz = BindCaller.class;
/* 1206 */       assert checkCallerClass(clazz, clazz);
/*      */       
/*      */       try {
/* 1209 */         MH_checkCallerClass = MethodHandles.Lookup.IMPL_LOOKUP.findStatic(clazz, "checkCallerClass", 
/* 1210 */             MethodType.methodType(boolean.class, Class.class, new Class[] { Class.class }));
/* 1211 */         assert MH_checkCallerClass.invokeExact(clazz, clazz);
/* 1212 */       } catch (Throwable throwable) {
/* 1213 */         throw new InternalError(throwable);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1230 */       final Object[] values = { null };
/* 1231 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() {
/*      */               try {
/* 1234 */                 Class<MethodHandleImpl.BindCaller.T> clazz = MethodHandleImpl.BindCaller.T.class;
/* 1235 */                 String str1 = clazz.getName();
/* 1236 */                 String str2 = str1.substring(str1.lastIndexOf('.') + 1) + ".class";
/* 1237 */                 URLConnection uRLConnection = clazz.getResource(str2).openConnection();
/* 1238 */                 int i = uRLConnection.getContentLength();
/* 1239 */                 byte[] arrayOfByte = new byte[i];
/* 1240 */                 try (InputStream null = uRLConnection.getInputStream()) {
/* 1241 */                   int j = inputStream.read(arrayOfByte);
/* 1242 */                   if (j != i) throw new IOException(str2); 
/*      */                 } 
/* 1244 */                 values[0] = arrayOfByte;
/* 1245 */               } catch (IOException iOException) {
/* 1246 */                 throw new InternalError(iOException);
/*      */               } 
/* 1248 */               return null;
/*      */             }
/*      */           });
/* 1251 */       T_BYTES = (byte[])arrayOfObject[0]; } @CallerSensitive
/*      */     private static boolean checkCallerClass(Class<?> param1Class1, Class<?> param1Class2) {
/*      */       Class<?> clazz = Reflection.getCallerClass();
/*      */       if (clazz != param1Class1 && clazz != param1Class2)
/*      */         throw new InternalError("found " + clazz.getName() + ", expected " + param1Class1.getName() + ((param1Class1 == param1Class2) ? "" : (", or else " + param1Class2.getName()))); 
/*      */       return true;
/*      */     } private static class T { static void init() {} static Object invoke_V(MethodHandle param2MethodHandle, Object[] param2ArrayOfObject) throws Throwable {
/* 1258 */         return param2MethodHandle.invokeExact(param2ArrayOfObject);
/*      */       } }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class WrappedMember
/*      */     extends DelegatingMethodHandle
/*      */   {
/*      */     private final MethodHandle target;
/*      */     
/*      */     private final MemberName member;
/*      */     private final Class<?> callerClass;
/*      */     private final boolean isInvokeSpecial;
/*      */     
/*      */     private WrappedMember(MethodHandle param1MethodHandle, MethodType param1MethodType, MemberName param1MemberName, boolean param1Boolean, Class<?> param1Class) {
/* 1274 */       super(param1MethodType, param1MethodHandle);
/* 1275 */       this.target = param1MethodHandle;
/* 1276 */       this.member = param1MemberName;
/* 1277 */       this.callerClass = param1Class;
/* 1278 */       this.isInvokeSpecial = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     MemberName internalMemberName() {
/* 1283 */       return this.member;
/*      */     }
/*      */     
/*      */     Class<?> internalCallerClass() {
/* 1287 */       return this.callerClass;
/*      */     }
/*      */     
/*      */     boolean isInvokeSpecial() {
/* 1291 */       return this.isInvokeSpecial;
/*      */     }
/*      */     
/*      */     protected MethodHandle getTarget() {
/* 1295 */       return this.target;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle asTypeUncached(MethodType param1MethodType) {
/* 1301 */       return this.asTypeCache = this.target.asType(param1MethodType);
/*      */     }
/*      */   }
/*      */   
/*      */   static MethodHandle makeWrappedMember(MethodHandle paramMethodHandle, MemberName paramMemberName, boolean paramBoolean) {
/* 1306 */     if (paramMemberName.equals(paramMethodHandle.internalMemberName()) && paramBoolean == paramMethodHandle.isInvokeSpecial())
/* 1307 */       return paramMethodHandle; 
/* 1308 */     return new WrappedMember(paramMethodHandle, paramMethodHandle.type(), paramMemberName, paramBoolean, null);
/*      */   }
/*      */ 
/*      */   
/*      */   enum Intrinsic
/*      */   {
/* 1314 */     SELECT_ALTERNATIVE,
/* 1315 */     GUARD_WITH_CATCH,
/* 1316 */     NEW_ARRAY,
/* 1317 */     ARRAY_LOAD,
/* 1318 */     ARRAY_STORE,
/* 1319 */     IDENTITY,
/* 1320 */     ZERO,
/* 1321 */     NONE;
/*      */   }
/*      */   
/*      */   private static final class IntrinsicMethodHandle
/*      */     extends DelegatingMethodHandle
/*      */   {
/*      */     private final MethodHandle target;
/*      */     private final MethodHandleImpl.Intrinsic intrinsicName;
/*      */     
/*      */     IntrinsicMethodHandle(MethodHandle param1MethodHandle, MethodHandleImpl.Intrinsic param1Intrinsic) {
/* 1331 */       super(param1MethodHandle.type(), param1MethodHandle);
/* 1332 */       this.target = param1MethodHandle;
/* 1333 */       this.intrinsicName = param1Intrinsic;
/*      */     }
/*      */ 
/*      */     
/*      */     protected MethodHandle getTarget() {
/* 1338 */       return this.target;
/*      */     }
/*      */ 
/*      */     
/*      */     MethodHandleImpl.Intrinsic intrinsicName() {
/* 1343 */       return this.intrinsicName;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle asTypeUncached(MethodType param1MethodType) {
/* 1350 */       return this.asTypeCache = this.target.asType(param1MethodType);
/*      */     }
/*      */ 
/*      */     
/*      */     String internalProperties() {
/* 1355 */       return super.internalProperties() + "\n& Intrinsic=" + this.intrinsicName;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public MethodHandle asCollector(Class<?> param1Class, int param1Int) {
/* 1361 */       if (this.intrinsicName == MethodHandleImpl.Intrinsic.IDENTITY) {
/* 1362 */         MethodType methodType = type().asCollectorType(param1Class, param1Int);
/* 1363 */         MethodHandle methodHandle = MethodHandleImpl.varargsArray(param1Class, param1Int);
/* 1364 */         return methodHandle.asType(methodType);
/*      */       } 
/* 1366 */       return super.asCollector(param1Class, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   static MethodHandle makeIntrinsic(MethodHandle paramMethodHandle, Intrinsic paramIntrinsic) {
/* 1371 */     if (paramIntrinsic == paramMethodHandle.intrinsicName())
/* 1372 */       return paramMethodHandle; 
/* 1373 */     return new IntrinsicMethodHandle(paramMethodHandle, paramIntrinsic);
/*      */   }
/*      */   
/*      */   static MethodHandle makeIntrinsic(MethodType paramMethodType, LambdaForm paramLambdaForm, Intrinsic paramIntrinsic) {
/* 1377 */     return new IntrinsicMethodHandle(SimpleMethodHandle.make(paramMethodType, paramLambdaForm), paramIntrinsic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MethodHandle findCollector(String paramString, int paramInt, Class<?> paramClass, Class<?>... paramVarArgs) {
/* 1385 */     MethodType methodType = MethodType.genericMethodType(paramInt).changeReturnType(paramClass).insertParameterTypes(0, paramVarArgs);
/*      */     try {
/* 1387 */       return MethodHandles.Lookup.IMPL_LOOKUP.findStatic(MethodHandleImpl.class, paramString, methodType);
/* 1388 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 1389 */       return null;
/*      */     } 
/*      */   }
/*      */   
/* 1393 */   private static final Object[] NO_ARGS_ARRAY = new Object[0]; private static final int FILL_ARRAYS_COUNT = 11; private static final int LEFT_ARGS = 10;
/* 1394 */   private static Object[] makeArray(Object... paramVarArgs) { return paramVarArgs; } private static Object[] array() {
/* 1395 */     return NO_ARGS_ARRAY;
/*      */   } private static Object[] array(Object paramObject) {
/* 1397 */     return makeArray(new Object[] { paramObject });
/*      */   } private static Object[] array(Object paramObject1, Object paramObject2) {
/* 1399 */     return makeArray(new Object[] { paramObject1, paramObject2 });
/*      */   } private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 1401 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3 });
/*      */   } private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4) {
/* 1403 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 });
/*      */   }
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5) {
/* 1406 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 });
/*      */   }
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6) {
/* 1409 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6 });
/*      */   }
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7) {
/* 1412 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7 });
/*      */   }
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8) {
/* 1415 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8 });
/*      */   }
/*      */   
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9) {
/* 1419 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8, paramObject9 });
/*      */   }
/*      */   
/*      */   private static Object[] array(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9, Object paramObject10) {
/* 1423 */     return makeArray(new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8, paramObject9, paramObject10 });
/*      */   } private static MethodHandle[] makeArrays() {
/* 1425 */     ArrayList<MethodHandle> arrayList = new ArrayList();
/*      */     while (true) {
/* 1427 */       MethodHandle methodHandle = findCollector("array", arrayList.size(), Object[].class, new Class[0]);
/* 1428 */       if (methodHandle == null)
/* 1429 */         break;  methodHandle = makeIntrinsic(methodHandle, Intrinsic.NEW_ARRAY);
/* 1430 */       arrayList.add(methodHandle);
/*      */     } 
/* 1432 */     assert arrayList.size() == 11;
/* 1433 */     return arrayList.<MethodHandle>toArray(new MethodHandle[MAX_ARITY + 1]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object[] fillNewArray(Integer paramInteger, Object[] paramArrayOfObject) {
/* 1439 */     Object[] arrayOfObject = new Object[paramInteger.intValue()];
/* 1440 */     fillWithArguments(arrayOfObject, 0, paramArrayOfObject);
/* 1441 */     return arrayOfObject;
/*      */   }
/*      */   private static Object[] fillNewTypedArray(Object[] paramArrayOfObject1, Integer paramInteger, Object[] paramArrayOfObject2) {
/* 1444 */     Object[] arrayOfObject = Arrays.copyOf(paramArrayOfObject1, paramInteger.intValue());
/* 1445 */     assert arrayOfObject.getClass() != Object[].class;
/* 1446 */     fillWithArguments(arrayOfObject, 0, paramArrayOfObject2);
/* 1447 */     return arrayOfObject;
/*      */   }
/*      */   private static void fillWithArguments(Object[] paramArrayOfObject1, int paramInt, Object... paramVarArgs1) {
/* 1450 */     System.arraycopy(paramVarArgs1, 0, paramArrayOfObject1, paramInt, paramVarArgs1.length);
/*      */   }
/*      */   
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject) {
/* 1454 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject }); return paramArrayOfObject;
/*      */   } private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2) {
/* 1456 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2 }); return paramArrayOfObject;
/*      */   } private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3) {
/* 1458 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3 }); return paramArrayOfObject;
/*      */   } private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4) {
/* 1460 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 }); return paramArrayOfObject;
/*      */   }
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5) {
/* 1463 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 }); return paramArrayOfObject;
/*      */   }
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6) {
/* 1466 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6 }); return paramArrayOfObject;
/*      */   }
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7) {
/* 1469 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7 }); return paramArrayOfObject;
/*      */   }
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8) {
/* 1472 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8 }); return paramArrayOfObject;
/*      */   }
/*      */   
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9) {
/* 1476 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8, paramObject9 }); return paramArrayOfObject;
/*      */   }
/*      */   
/*      */   private static Object[] fillArray(Integer paramInteger, Object[] paramArrayOfObject, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9, Object paramObject10) {
/* 1480 */     fillWithArguments(paramArrayOfObject, paramInteger.intValue(), new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7, paramObject8, paramObject9, paramObject10 }); return paramArrayOfObject;
/*      */   }
/*      */ 
/*      */   
/*      */   private static MethodHandle[] makeFillArrays() {
/* 1485 */     ArrayList<MethodHandle> arrayList = new ArrayList();
/* 1486 */     arrayList.add(null);
/*      */     while (true) {
/* 1488 */       MethodHandle methodHandle = findCollector("fillArray", arrayList.size(), Object[].class, new Class[] { Integer.class, Object[].class });
/* 1489 */       if (methodHandle == null)
/* 1490 */         break;  arrayList.add(methodHandle);
/*      */     } 
/* 1492 */     assert arrayList.size() == 11;
/* 1493 */     return arrayList.<MethodHandle>toArray(new MethodHandle[0]);
/*      */   }
/*      */   
/*      */   private static Object copyAsPrimitiveArray(Wrapper paramWrapper, Object... paramVarArgs) {
/* 1497 */     Object object = paramWrapper.makeArray(paramVarArgs.length);
/* 1498 */     paramWrapper.copyArrayUnboxing(paramVarArgs, 0, object, 0, paramVarArgs.length);
/* 1499 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle varargsArray(int paramInt) {
/* 1506 */     MethodHandle methodHandle = Lazy.ARRAYS[paramInt];
/* 1507 */     if (methodHandle != null) return methodHandle; 
/* 1508 */     methodHandle = findCollector("array", paramInt, Object[].class, new Class[0]);
/* 1509 */     if (methodHandle != null) methodHandle = makeIntrinsic(methodHandle, Intrinsic.NEW_ARRAY); 
/* 1510 */     if (methodHandle != null) { Lazy.ARRAYS[paramInt] = methodHandle; return methodHandle; }
/* 1511 */      methodHandle = buildVarargsArray(Lazy.MH_fillNewArray, Lazy.MH_arrayIdentity, paramInt);
/* 1512 */     assert assertCorrectArity(methodHandle, paramInt);
/* 1513 */     methodHandle = makeIntrinsic(methodHandle, Intrinsic.NEW_ARRAY);
/* 1514 */     Lazy.ARRAYS[paramInt] = methodHandle; return methodHandle;
/*      */   }
/*      */   
/*      */   private static boolean assertCorrectArity(MethodHandle paramMethodHandle, int paramInt) {
/* 1518 */     assert paramMethodHandle.type().parameterCount() == paramInt : "arity != " + paramInt + ": " + paramMethodHandle;
/* 1519 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   static <T> T[] identity(T[] paramArrayOfT) {
/* 1524 */     return paramArrayOfT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MethodHandle buildVarargsArray(MethodHandle paramMethodHandle1, MethodHandle paramMethodHandle2, int paramInt) {
/* 1531 */     int i = Math.min(paramInt, 10);
/* 1532 */     int j = paramInt - i;
/* 1533 */     MethodHandle methodHandle1 = paramMethodHandle1.bindTo(Integer.valueOf(paramInt));
/* 1534 */     methodHandle1 = methodHandle1.asCollector(Object[].class, i);
/* 1535 */     MethodHandle methodHandle2 = paramMethodHandle2;
/* 1536 */     if (j > 0) {
/* 1537 */       MethodHandle methodHandle = fillToRight(10 + j);
/* 1538 */       if (methodHandle2 == Lazy.MH_arrayIdentity) {
/* 1539 */         methodHandle2 = methodHandle;
/*      */       } else {
/* 1541 */         methodHandle2 = MethodHandles.collectArguments(methodHandle2, 0, methodHandle);
/*      */       } 
/* 1543 */     }  if (methodHandle2 == Lazy.MH_arrayIdentity) {
/* 1544 */       methodHandle2 = methodHandle1;
/*      */     } else {
/* 1546 */       methodHandle2 = MethodHandles.collectArguments(methodHandle2, 0, methodHandle1);
/* 1547 */     }  return methodHandle2;
/*      */   }
/*      */ 
/*      */   
/* 1551 */   private static final MethodHandle[] FILL_ARRAY_TO_RIGHT = new MethodHandle[MAX_ARITY + 1];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MethodHandle fillToRight(int paramInt) {
/* 1557 */     MethodHandle methodHandle = FILL_ARRAY_TO_RIGHT[paramInt];
/* 1558 */     if (methodHandle != null) return methodHandle; 
/* 1559 */     methodHandle = buildFiller(paramInt);
/* 1560 */     assert assertCorrectArity(methodHandle, paramInt - 10 + 1);
/* 1561 */     FILL_ARRAY_TO_RIGHT[paramInt] = methodHandle; return methodHandle;
/*      */   }
/*      */   private static MethodHandle buildFiller(int paramInt) {
/* 1564 */     if (paramInt <= 10) {
/* 1565 */       return Lazy.MH_arrayIdentity;
/*      */     }
/*      */     
/* 1568 */     int i = paramInt % 10;
/* 1569 */     int j = paramInt - i;
/* 1570 */     if (i == 0) {
/* 1571 */       j = paramInt - (i = 10);
/* 1572 */       if (FILL_ARRAY_TO_RIGHT[j] == null)
/*      */       {
/* 1574 */         for (byte b = 0; b < j; b += 10) {
/* 1575 */           if (b > 10) fillToRight(b); 
/*      */         }  } 
/*      */     } 
/* 1578 */     if (j < 10) i = paramInt - (j = 10); 
/* 1579 */     assert i > 0;
/* 1580 */     MethodHandle methodHandle1 = fillToRight(j);
/* 1581 */     MethodHandle methodHandle2 = Lazy.FILL_ARRAYS[i].bindTo(Integer.valueOf(j));
/* 1582 */     assert methodHandle1.type().parameterCount() == 1 + j - 10;
/* 1583 */     assert methodHandle2.type().parameterCount() == 1 + i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1589 */     if (j == 10) {
/* 1590 */       return methodHandle2;
/*      */     }
/* 1592 */     return MethodHandles.collectArguments(methodHandle2, 0, methodHandle1);
/*      */   }
/*      */ 
/*      */   
/* 1596 */   private static final ClassValue<MethodHandle[]> TYPED_COLLECTORS = new ClassValue<MethodHandle[]>()
/*      */     {
/*      */       protected MethodHandle[] computeValue(Class<?> param1Class)
/*      */       {
/* 1600 */         return new MethodHandle[256];
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MAX_JVM_ARITY = 255;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static MethodHandle varargsArray(Class<?> paramClass, int paramInt) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual getComponentType : ()Ljava/lang/Class;
/*      */     //   4: astore_2
/*      */     //   5: aload_2
/*      */     //   6: ifnonnull -> 36
/*      */     //   9: new java/lang/IllegalArgumentException
/*      */     //   12: dup
/*      */     //   13: new java/lang/StringBuilder
/*      */     //   16: dup
/*      */     //   17: invokespecial <init> : ()V
/*      */     //   20: ldc 'not an array: '
/*      */     //   22: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   25: aload_0
/*      */     //   26: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   29: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   32: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   35: athrow
/*      */     //   36: iload_1
/*      */     //   37: bipush #126
/*      */     //   39: if_icmplt -> 114
/*      */     //   42: iload_1
/*      */     //   43: istore_3
/*      */     //   44: iload_3
/*      */     //   45: sipush #254
/*      */     //   48: if_icmpgt -> 68
/*      */     //   51: aload_2
/*      */     //   52: invokevirtual isPrimitive : ()Z
/*      */     //   55: ifeq -> 68
/*      */     //   58: iload_3
/*      */     //   59: aload_2
/*      */     //   60: invokestatic forPrimitiveType : (Ljava/lang/Class;)Lsun/invoke/util/Wrapper;
/*      */     //   63: invokevirtual stackSlots : ()I
/*      */     //   66: imul
/*      */     //   67: istore_3
/*      */     //   68: iload_3
/*      */     //   69: sipush #254
/*      */     //   72: if_icmple -> 114
/*      */     //   75: new java/lang/IllegalArgumentException
/*      */     //   78: dup
/*      */     //   79: new java/lang/StringBuilder
/*      */     //   82: dup
/*      */     //   83: invokespecial <init> : ()V
/*      */     //   86: ldc 'too many arguments: '
/*      */     //   88: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   91: aload_0
/*      */     //   92: invokevirtual getSimpleName : ()Ljava/lang/String;
/*      */     //   95: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   98: ldc ', length '
/*      */     //   100: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   103: iload_1
/*      */     //   104: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*      */     //   107: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   110: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   113: athrow
/*      */     //   114: aload_2
/*      */     //   115: ldc java/lang/Object
/*      */     //   117: if_acmpne -> 125
/*      */     //   120: iload_1
/*      */     //   121: invokestatic varargsArray : (I)Ljava/lang/invoke/MethodHandle;
/*      */     //   124: areturn
/*      */     //   125: getstatic java/lang/invoke/MethodHandleImpl.TYPED_COLLECTORS : Ljava/lang/ClassValue;
/*      */     //   128: aload_2
/*      */     //   129: invokevirtual get : (Ljava/lang/Class;)Ljava/lang/Object;
/*      */     //   132: checkcast [Ljava/lang/invoke/MethodHandle;
/*      */     //   135: astore_3
/*      */     //   136: iload_1
/*      */     //   137: aload_3
/*      */     //   138: arraylength
/*      */     //   139: if_icmpge -> 148
/*      */     //   142: aload_3
/*      */     //   143: iload_1
/*      */     //   144: aaload
/*      */     //   145: goto -> 149
/*      */     //   148: aconst_null
/*      */     //   149: astore #4
/*      */     //   151: aload #4
/*      */     //   153: ifnull -> 159
/*      */     //   156: aload #4
/*      */     //   158: areturn
/*      */     //   159: iload_1
/*      */     //   160: ifne -> 184
/*      */     //   163: aload_0
/*      */     //   164: invokevirtual getComponentType : ()Ljava/lang/Class;
/*      */     //   167: iconst_0
/*      */     //   168: invokestatic newInstance : (Ljava/lang/Class;I)Ljava/lang/Object;
/*      */     //   171: astore #5
/*      */     //   173: aload_0
/*      */     //   174: aload #5
/*      */     //   176: invokestatic constant : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
/*      */     //   179: astore #4
/*      */     //   181: goto -> 259
/*      */     //   184: aload_2
/*      */     //   185: invokevirtual isPrimitive : ()Z
/*      */     //   188: ifeq -> 215
/*      */     //   191: getstatic java/lang/invoke/MethodHandleImpl$Lazy.MH_fillNewArray : Ljava/lang/invoke/MethodHandle;
/*      */     //   194: astore #5
/*      */     //   196: aload_0
/*      */     //   197: invokestatic buildArrayProducer : (Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;
/*      */     //   200: astore #6
/*      */     //   202: aload #5
/*      */     //   204: aload #6
/*      */     //   206: iload_1
/*      */     //   207: invokestatic buildVarargsArray : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodHandle;I)Ljava/lang/invoke/MethodHandle;
/*      */     //   210: astore #4
/*      */     //   212: goto -> 259
/*      */     //   215: aload_0
/*      */     //   216: ldc [Ljava/lang/Object;
/*      */     //   218: invokevirtual asSubclass : (Ljava/lang/Class;)Ljava/lang/Class;
/*      */     //   221: astore #5
/*      */     //   223: getstatic java/lang/invoke/MethodHandleImpl.NO_ARGS_ARRAY : [Ljava/lang/Object;
/*      */     //   226: iconst_0
/*      */     //   227: aload #5
/*      */     //   229: invokestatic copyOf : ([Ljava/lang/Object;ILjava/lang/Class;)[Ljava/lang/Object;
/*      */     //   232: astore #6
/*      */     //   234: getstatic java/lang/invoke/MethodHandleImpl$Lazy.MH_fillNewTypedArray : Ljava/lang/invoke/MethodHandle;
/*      */     //   237: aload #6
/*      */     //   239: invokevirtual bindTo : (Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
/*      */     //   242: astore #7
/*      */     //   244: getstatic java/lang/invoke/MethodHandleImpl$Lazy.MH_arrayIdentity : Ljava/lang/invoke/MethodHandle;
/*      */     //   247: astore #8
/*      */     //   249: aload #7
/*      */     //   251: aload #8
/*      */     //   253: iload_1
/*      */     //   254: invokestatic buildVarargsArray : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodHandle;I)Ljava/lang/invoke/MethodHandle;
/*      */     //   257: astore #4
/*      */     //   259: aload #4
/*      */     //   261: aload_0
/*      */     //   262: iload_1
/*      */     //   263: aload_2
/*      */     //   264: invokestatic nCopies : (ILjava/lang/Object;)Ljava/util/List;
/*      */     //   267: invokestatic methodType : (Ljava/lang/Class;Ljava/util/List;)Ljava/lang/invoke/MethodType;
/*      */     //   270: invokevirtual asType : (Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
/*      */     //   273: astore #4
/*      */     //   275: aload #4
/*      */     //   277: getstatic java/lang/invoke/MethodHandleImpl$Intrinsic.NEW_ARRAY : Ljava/lang/invoke/MethodHandleImpl$Intrinsic;
/*      */     //   280: invokestatic makeIntrinsic : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodHandleImpl$Intrinsic;)Ljava/lang/invoke/MethodHandle;
/*      */     //   283: astore #4
/*      */     //   285: getstatic java/lang/invoke/MethodHandleImpl.$assertionsDisabled : Z
/*      */     //   288: ifne -> 308
/*      */     //   291: aload #4
/*      */     //   293: iload_1
/*      */     //   294: invokestatic assertCorrectArity : (Ljava/lang/invoke/MethodHandle;I)Z
/*      */     //   297: ifne -> 308
/*      */     //   300: new java/lang/AssertionError
/*      */     //   303: dup
/*      */     //   304: invokespecial <init> : ()V
/*      */     //   307: athrow
/*      */     //   308: iload_1
/*      */     //   309: aload_3
/*      */     //   310: arraylength
/*      */     //   311: if_icmpge -> 319
/*      */     //   314: aload_3
/*      */     //   315: iload_1
/*      */     //   316: aload #4
/*      */     //   318: aastore
/*      */     //   319: aload #4
/*      */     //   321: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1611	-> 0
/*      */     //   #1612	-> 5
/*      */     //   #1614	-> 36
/*      */     //   #1615	-> 42
/*      */     //   #1617	-> 44
/*      */     //   #1618	-> 58
/*      */     //   #1619	-> 68
/*      */     //   #1620	-> 75
/*      */     //   #1622	-> 114
/*      */     //   #1623	-> 120
/*      */     //   #1625	-> 125
/*      */     //   #1626	-> 136
/*      */     //   #1627	-> 151
/*      */     //   #1628	-> 159
/*      */     //   #1629	-> 163
/*      */     //   #1630	-> 173
/*      */     //   #1631	-> 181
/*      */     //   #1632	-> 191
/*      */     //   #1633	-> 196
/*      */     //   #1634	-> 202
/*      */     //   #1635	-> 212
/*      */     //   #1636	-> 215
/*      */     //   #1637	-> 223
/*      */     //   #1638	-> 234
/*      */     //   #1639	-> 244
/*      */     //   #1640	-> 249
/*      */     //   #1642	-> 259
/*      */     //   #1643	-> 275
/*      */     //   #1644	-> 285
/*      */     //   #1645	-> 308
/*      */     //   #1646	-> 314
/*      */     //   #1647	-> 319
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
/*      */   private static MethodHandle buildArrayProducer(Class<?> paramClass) {
/* 1651 */     Class<?> clazz = paramClass.getComponentType();
/* 1652 */     assert clazz.isPrimitive();
/* 1653 */     return Lazy.MH_copyAsPrimitiveArray.bindTo(Wrapper.forPrimitiveType(clazz));
/*      */   }
/*      */   
/*      */   static void assertSame(Object paramObject1, Object paramObject2) {
/* 1657 */     if (paramObject1 != paramObject2) {
/* 1658 */       String str = String.format("mh1 != mh2: mh1 = %s (form: %s); mh2 = %s (form: %s)", new Object[] { paramObject1, ((MethodHandle)paramObject1).form, paramObject2, ((MethodHandle)paramObject2).form });
/*      */ 
/*      */       
/* 1661 */       throw MethodHandleStatics.newInternalError(str);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/MethodHandleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */