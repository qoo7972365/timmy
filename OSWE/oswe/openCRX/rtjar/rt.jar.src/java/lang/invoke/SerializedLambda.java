/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.lang.invoke.MethodHandleInfo;
/*     */ import java.lang.invoke.SerializedLambda;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SerializedLambda
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8025925345765570181L;
/*     */   private final Class<?> capturingClass;
/*     */   private final String functionalInterfaceClass;
/*     */   private final String functionalInterfaceMethodName;
/*     */   private final String functionalInterfaceMethodSignature;
/*     */   private final String implClass;
/*     */   private final String implMethodName;
/*     */   private final String implMethodSignature;
/*     */   private final int implMethodKind;
/*     */   private final String instantiatedMethodType;
/*     */   private final Object[] capturedArgs;
/*     */   
/*     */   public SerializedLambda(Class<?> paramClass, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, Object[] paramArrayOfObject) {
/* 107 */     this.capturingClass = paramClass;
/* 108 */     this.functionalInterfaceClass = paramString1;
/* 109 */     this.functionalInterfaceMethodName = paramString2;
/* 110 */     this.functionalInterfaceMethodSignature = paramString3;
/* 111 */     this.implMethodKind = paramInt;
/* 112 */     this.implClass = paramString4;
/* 113 */     this.implMethodName = paramString5;
/* 114 */     this.implMethodSignature = paramString6;
/* 115 */     this.instantiatedMethodType = paramString7;
/* 116 */     this.capturedArgs = (Object[])((Object[])Objects.<Object[]>requireNonNull(paramArrayOfObject)).clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCapturingClass() {
/* 124 */     return this.capturingClass.getName().replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFunctionalInterfaceClass() {
/* 134 */     return this.functionalInterfaceClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFunctionalInterfaceMethodName() {
/* 143 */     return this.functionalInterfaceMethodName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFunctionalInterfaceMethodSignature() {
/* 153 */     return this.functionalInterfaceMethodSignature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplClass() {
/* 163 */     return this.implClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplMethodName() {
/* 171 */     return this.implMethodName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getImplMethodSignature() {
/* 179 */     return this.implMethodSignature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImplMethodKind() {
/* 188 */     return this.implMethodKind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getInstantiatedMethodType() {
/* 199 */     return this.instantiatedMethodType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCapturedArgCount() {
/* 207 */     return this.capturedArgs.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCapturedArg(int paramInt) {
/* 216 */     return this.capturedArgs[paramInt];
/*     */   }
/*     */   
/*     */   private Object readResolve() throws ReflectiveOperationException {
/*     */     try {
/* 221 */       Method method = AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/* 224 */               Method method = SerializedLambda.this.capturingClass.getDeclaredMethod("$deserializeLambda$", new Class[] { SerializedLambda.class });
/* 225 */               method.setAccessible(true);
/* 226 */               return method;
/*     */             }
/*     */           });
/*     */       
/* 230 */       return method.invoke(null, new Object[] { this });
/*     */     }
/* 232 */     catch (PrivilegedActionException privilegedActionException) {
/* 233 */       Exception exception = privilegedActionException.getException();
/* 234 */       if (exception instanceof ReflectiveOperationException)
/* 235 */         throw (ReflectiveOperationException)exception; 
/* 236 */       if (exception instanceof RuntimeException) {
/* 237 */         throw (RuntimeException)exception;
/*     */       }
/* 239 */       throw new RuntimeException("Exception in SerializedLambda.readResolve", privilegedActionException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     String str = MethodHandleInfo.referenceKindToString(this.implMethodKind);
/* 246 */     return String.format("SerializedLambda[%s=%s, %s=%s.%s:%s, %s=%s %s.%s:%s, %s=%s, %s=%d]", new Object[] { "capturingClass", this.capturingClass, "functionalInterfaceMethod", this.functionalInterfaceClass, this.functionalInterfaceMethodName, this.functionalInterfaceMethodSignature, "implementation", str, this.implClass, this.implMethodName, this.implMethodSignature, "instantiatedMethodType", this.instantiatedMethodType, "numCaptured", 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 256 */           Integer.valueOf(this.capturedArgs.length) });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/SerializedLambda.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */