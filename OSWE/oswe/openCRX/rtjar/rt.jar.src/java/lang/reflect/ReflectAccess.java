/*     */ package java.lang.reflect;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Executable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import sun.reflect.ConstructorAccessor;
/*     */ import sun.reflect.LangReflectAccess;
/*     */ import sun.reflect.MethodAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ReflectAccess
/*     */   implements LangReflectAccess
/*     */ {
/*     */   public Field newField(Class<?> paramClass1, String paramString1, Class<?> paramClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte) {
/*  44 */     return new Field(paramClass1, paramString1, paramClass2, paramInt1, paramInt2, paramString2, paramArrayOfbyte);
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
/*     */   public Method newMethod(Class<?> paramClass1, String paramString1, Class<?>[] paramArrayOfClass1, Class<?> paramClass2, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) {
/*  65 */     return new Method(paramClass1, paramString1, paramArrayOfClass1, paramClass2, paramArrayOfClass2, paramInt1, paramInt2, paramString2, paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3);
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
/*     */   public <T> Constructor<T> newConstructor(Class<T> paramClass, Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, int paramInt1, int paramInt2, String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*  87 */     return new Constructor<>(paramClass, paramArrayOfClass1, paramArrayOfClass2, paramInt1, paramInt2, paramString, paramArrayOfbyte1, paramArrayOfbyte2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodAccessor getMethodAccessor(Method paramMethod) {
/*  98 */     return paramMethod.getMethodAccessor();
/*     */   }
/*     */   
/*     */   public void setMethodAccessor(Method paramMethod, MethodAccessor paramMethodAccessor) {
/* 102 */     paramMethod.setMethodAccessor(paramMethodAccessor);
/*     */   }
/*     */   
/*     */   public ConstructorAccessor getConstructorAccessor(Constructor<?> paramConstructor) {
/* 106 */     return paramConstructor.getConstructorAccessor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConstructorAccessor(Constructor<?> paramConstructor, ConstructorAccessor paramConstructorAccessor) {
/* 112 */     paramConstructor.setConstructorAccessor(paramConstructorAccessor);
/*     */   }
/*     */   
/*     */   public int getConstructorSlot(Constructor<?> paramConstructor) {
/* 116 */     return paramConstructor.getSlot();
/*     */   }
/*     */   
/*     */   public String getConstructorSignature(Constructor<?> paramConstructor) {
/* 120 */     return paramConstructor.getSignature();
/*     */   }
/*     */   
/*     */   public byte[] getConstructorAnnotations(Constructor<?> paramConstructor) {
/* 124 */     return paramConstructor.getRawAnnotations();
/*     */   }
/*     */   
/*     */   public byte[] getConstructorParameterAnnotations(Constructor<?> paramConstructor) {
/* 128 */     return paramConstructor.getRawParameterAnnotations();
/*     */   }
/*     */   
/*     */   public byte[] getExecutableTypeAnnotationBytes(Executable paramExecutable) {
/* 132 */     return paramExecutable.getTypeAnnotationBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Method copyMethod(Method paramMethod) {
/* 140 */     return paramMethod.copy();
/*     */   }
/*     */   
/*     */   public Field copyField(Field paramField) {
/* 144 */     return paramField.copy();
/*     */   }
/*     */   
/*     */   public <T> Constructor<T> copyConstructor(Constructor<T> paramConstructor) {
/* 148 */     return paramConstructor.copy();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/ReflectAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */