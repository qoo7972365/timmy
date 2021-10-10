/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.BoundMethodHandle;
/*     */ import java.lang.invoke.DelegatingMethodHandle;
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.MemberName;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandleStatics;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class DelegatingMethodHandle
/*     */   extends MethodHandle
/*     */ {
/*     */   static final LambdaForm.NamedFunction NF_getTarget;
/*     */   
/*     */   protected DelegatingMethodHandle(MethodHandle paramMethodHandle) {
/*  40 */     this(paramMethodHandle.type(), paramMethodHandle);
/*     */   }
/*     */   
/*     */   protected DelegatingMethodHandle(MethodType paramMethodType, MethodHandle paramMethodHandle) {
/*  44 */     super(paramMethodType, chooseDelegatingForm(paramMethodHandle));
/*     */   }
/*     */   
/*     */   protected DelegatingMethodHandle(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/*  48 */     super(paramMethodType, paramLambdaForm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MemberName internalMemberName() {
/*  59 */     return getTarget().internalMemberName();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isInvokeSpecial() {
/*  64 */     return getTarget().isInvokeSpecial();
/*     */   }
/*     */ 
/*     */   
/*     */   Class<?> internalCallerClass() {
/*  69 */     return getTarget().internalCallerClass();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MethodHandle copyWith(MethodType paramMethodType, LambdaForm paramLambdaForm) {
/*  75 */     throw MethodHandleStatics.newIllegalArgumentException("do not use this");
/*     */   }
/*     */ 
/*     */   
/*     */   String internalProperties() {
/*  80 */     return "\n& Class=" + getClass().getSimpleName() + "\n& Target=" + 
/*  81 */       getTarget().debugString();
/*     */   }
/*     */ 
/*     */   
/*     */   BoundMethodHandle rebind() {
/*  86 */     return getTarget().rebind();
/*     */   }
/*     */   
/*     */   private static LambdaForm chooseDelegatingForm(MethodHandle paramMethodHandle) {
/*  90 */     if (paramMethodHandle instanceof SimpleMethodHandle)
/*  91 */       return paramMethodHandle.internalForm(); 
/*  92 */     return makeReinvokerForm(paramMethodHandle, 8, DelegatingMethodHandle.class, NF_getTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LambdaForm makeReinvokerForm(MethodHandle paramMethodHandle, int paramInt, Object paramObject, LambdaForm.NamedFunction paramNamedFunction) {
/* 100 */     switch (paramInt) { case 7:
/* 101 */         str = "BMH.reinvoke";
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 106 */         return makeReinvokerForm(paramMethodHandle, paramInt, paramObject, str, true, paramNamedFunction, null);case 8: str = "MH.delegate"; return makeReinvokerForm(paramMethodHandle, paramInt, paramObject, str, true, paramNamedFunction, null); }  String str = "MH.reinvoke"; return makeReinvokerForm(paramMethodHandle, paramInt, paramObject, str, true, paramNamedFunction, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LambdaForm makeReinvokerForm(MethodHandle paramMethodHandle, int paramInt, Object paramObject, String paramString, boolean paramBoolean, LambdaForm.NamedFunction paramNamedFunction1, LambdaForm.NamedFunction paramNamedFunction2) {
/* 116 */     MethodType methodType = paramMethodHandle.type().basicType();
/*     */     
/* 118 */     boolean bool1 = (paramInt < 0 || methodType.parameterSlotCount() > 253) ? true : false;
/* 119 */     boolean bool2 = (paramNamedFunction2 != null) ? true : false;
/*     */     
/* 121 */     if (!bool1) {
/* 122 */       LambdaForm lambdaForm1 = methodType.form().cachedLambdaForm(paramInt);
/* 123 */       if (lambdaForm1 != null) return lambdaForm1;
/*     */     
/*     */     } 
/*     */     
/* 127 */     int i = 1 + methodType.parameterCount();
/* 128 */     int j = i;
/* 129 */     boolean bool3 = bool2 ? j++ : true;
/* 130 */     boolean bool4 = bool1 ? true : j++;
/* 131 */     int k = j++;
/* 132 */     LambdaForm.Name[] arrayOfName = LambdaForm.arguments(j - i, methodType.invokerType());
/* 133 */     assert arrayOfName.length == j;
/* 134 */     arrayOfName[0] = arrayOfName[0].withConstraint(paramObject);
/*     */     
/* 136 */     if (bool2) {
/* 137 */       arrayOfName[bool3] = new LambdaForm.Name(paramNamedFunction2, new Object[] { arrayOfName[0] });
/*     */     }
/* 139 */     if (bool1) {
/* 140 */       Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 1, i, Object[].class);
/* 141 */       arrayOfName[k] = new LambdaForm.Name(paramMethodHandle, arrayOfObject);
/*     */     } else {
/* 143 */       arrayOfName[bool4] = new LambdaForm.Name(paramNamedFunction1, new Object[] { arrayOfName[0] });
/* 144 */       Object[] arrayOfObject = Arrays.copyOfRange(arrayOfName, 0, i, Object[].class);
/* 145 */       arrayOfObject[0] = arrayOfName[bool4];
/* 146 */       arrayOfName[k] = new LambdaForm.Name(methodType, arrayOfObject);
/*     */     } 
/* 148 */     LambdaForm lambdaForm = new LambdaForm(paramString, i, arrayOfName, paramBoolean);
/* 149 */     if (!bool1) {
/* 150 */       lambdaForm = methodType.form().setCachedLambdaForm(paramInt, lambdaForm);
/*     */     }
/* 152 */     return lambdaForm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 159 */       NF_getTarget = new LambdaForm.NamedFunction(DelegatingMethodHandle.class.getDeclaredMethod("getTarget", new Class[0]));
/* 160 */     } catch (ReflectiveOperationException reflectiveOperationException) {
/* 161 */       throw MethodHandleStatics.newInternalError(reflectiveOperationException);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract MethodHandle getTarget();
/*     */   
/*     */   abstract MethodHandle asTypeUncached(MethodType paramMethodType);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/DelegatingMethodHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */