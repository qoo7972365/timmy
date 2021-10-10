/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ import javax.management.openmbean.OpenType;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ConvertingMethod
/*     */ {
/*     */   static ConvertingMethod from(Method paramMethod) {
/*     */     try {
/*  41 */       return new ConvertingMethod(paramMethod);
/*  42 */     } catch (OpenDataException openDataException) {
/*     */       
/*  44 */       String str = "Method " + paramMethod.getDeclaringClass().getName() + "." + paramMethod.getName() + " has parameter or return type that cannot be translated into an open type";
/*     */       
/*  46 */       throw new IllegalArgumentException(str, openDataException);
/*     */     } 
/*     */   }
/*     */   
/*     */   Method getMethod() {
/*  51 */     return this.method;
/*     */   }
/*     */   
/*     */   Descriptor getDescriptor() {
/*  55 */     return Introspector.descriptorForElement(this.method);
/*     */   }
/*     */   
/*     */   Type getGenericReturnType() {
/*  59 */     return this.method.getGenericReturnType();
/*     */   }
/*     */   
/*     */   Type[] getGenericParameterTypes() {
/*  63 */     return this.method.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */   String getName() {
/*  67 */     return this.method.getName();
/*     */   }
/*     */   
/*     */   OpenType<?> getOpenReturnType() {
/*  71 */     return this.returnMapping.getOpenType();
/*     */   }
/*     */   
/*     */   OpenType<?>[] getOpenParameterTypes() {
/*  75 */     OpenType[] arrayOfOpenType = new OpenType[this.paramMappings.length];
/*  76 */     for (byte b = 0; b < this.paramMappings.length; b++)
/*  77 */       arrayOfOpenType[b] = this.paramMappings[b].getOpenType(); 
/*  78 */     return (OpenType<?>[])arrayOfOpenType;
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
/*     */   void checkCallFromOpen() {
/*     */     try {
/*  91 */       for (MXBeanMapping mXBeanMapping : this.paramMappings)
/*  92 */         mXBeanMapping.checkReconstructible(); 
/*  93 */     } catch (InvalidObjectException invalidObjectException) {
/*  94 */       throw new IllegalArgumentException(invalidObjectException);
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
/*     */   void checkCallToOpen() {
/*     */     try {
/* 108 */       this.returnMapping.checkReconstructible();
/* 109 */     } catch (InvalidObjectException invalidObjectException) {
/* 110 */       throw new IllegalArgumentException(invalidObjectException);
/*     */     } 
/*     */   }
/*     */   
/*     */   String[] getOpenSignature() {
/* 115 */     if (this.paramMappings.length == 0) {
/* 116 */       return noStrings;
/*     */     }
/* 118 */     String[] arrayOfString = new String[this.paramMappings.length];
/* 119 */     for (byte b = 0; b < this.paramMappings.length; b++)
/* 120 */       arrayOfString[b] = this.paramMappings[b].getOpenClass().getName(); 
/* 121 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   final Object toOpenReturnValue(MXBeanLookup paramMXBeanLookup, Object paramObject) throws OpenDataException {
/* 126 */     return this.returnMapping.toOpenValue(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   final Object fromOpenReturnValue(MXBeanLookup paramMXBeanLookup, Object paramObject) throws InvalidObjectException {
/* 131 */     return this.returnMapping.fromOpenValue(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   final Object[] toOpenParameters(MXBeanLookup paramMXBeanLookup, Object[] paramArrayOfObject) throws OpenDataException {
/* 136 */     if (this.paramConversionIsIdentity || paramArrayOfObject == null)
/* 137 */       return paramArrayOfObject; 
/* 138 */     Object[] arrayOfObject = new Object[paramArrayOfObject.length];
/* 139 */     for (byte b = 0; b < paramArrayOfObject.length; b++)
/* 140 */       arrayOfObject[b] = this.paramMappings[b].toOpenValue(paramArrayOfObject[b]); 
/* 141 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   
/*     */   final Object[] fromOpenParameters(Object[] paramArrayOfObject) throws InvalidObjectException {
/* 146 */     if (this.paramConversionIsIdentity || paramArrayOfObject == null)
/* 147 */       return paramArrayOfObject; 
/* 148 */     Object[] arrayOfObject = new Object[paramArrayOfObject.length];
/* 149 */     for (byte b = 0; b < paramArrayOfObject.length; b++)
/* 150 */       arrayOfObject[b] = this.paramMappings[b].fromOpenValue(paramArrayOfObject[b]); 
/* 151 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Object toOpenParameter(MXBeanLookup paramMXBeanLookup, Object paramObject, int paramInt) throws OpenDataException {
/* 158 */     return this.paramMappings[paramInt].toOpenValue(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Object fromOpenParameter(MXBeanLookup paramMXBeanLookup, Object paramObject, int paramInt) throws InvalidObjectException {
/* 165 */     return this.paramMappings[paramInt].fromOpenValue(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object invokeWithOpenReturn(MXBeanLookup paramMXBeanLookup, Object paramObject, Object[] paramArrayOfObject) throws MBeanException, IllegalAccessException, InvocationTargetException {
/* 172 */     MXBeanLookup mXBeanLookup = MXBeanLookup.getLookup();
/*     */     try {
/* 174 */       MXBeanLookup.setLookup(paramMXBeanLookup);
/* 175 */       return invokeWithOpenReturn(paramObject, paramArrayOfObject);
/*     */     } finally {
/* 177 */       MXBeanLookup.setLookup(mXBeanLookup);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object invokeWithOpenReturn(Object paramObject, Object[] paramArrayOfObject) throws MBeanException, IllegalAccessException, InvocationTargetException {
/*     */     Object[] arrayOfObject;
/*     */     try {
/* 186 */       arrayOfObject = fromOpenParameters(paramArrayOfObject);
/* 187 */     } catch (InvalidObjectException invalidObjectException) {
/*     */       
/* 189 */       String str = methodName() + ": cannot convert parameters from open values: " + invalidObjectException;
/*     */       
/* 191 */       throw new MBeanException(invalidObjectException, str);
/*     */     } 
/* 193 */     Object object = MethodUtil.invoke(this.method, paramObject, arrayOfObject);
/*     */     try {
/* 195 */       return this.returnMapping.toOpenValue(object);
/* 196 */     } catch (OpenDataException openDataException) {
/*     */       
/* 198 */       String str = methodName() + ": cannot convert return value to open value: " + openDataException;
/*     */       
/* 200 */       throw new MBeanException(openDataException, str);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String methodName() {
/* 205 */     return this.method.getDeclaringClass() + "." + this.method.getName();
/*     */   }
/*     */   
/*     */   private ConvertingMethod(Method paramMethod) throws OpenDataException {
/* 209 */     this.method = paramMethod;
/* 210 */     MXBeanMappingFactory mXBeanMappingFactory = MXBeanMappingFactory.DEFAULT;
/* 211 */     this
/* 212 */       .returnMapping = mXBeanMappingFactory.mappingForType(paramMethod.getGenericReturnType(), mXBeanMappingFactory);
/* 213 */     Type[] arrayOfType = paramMethod.getGenericParameterTypes();
/* 214 */     this.paramMappings = new MXBeanMapping[arrayOfType.length];
/* 215 */     boolean bool = true;
/* 216 */     for (byte b = 0; b < arrayOfType.length; b++) {
/* 217 */       this.paramMappings[b] = mXBeanMappingFactory.mappingForType(arrayOfType[b], mXBeanMappingFactory);
/* 218 */       bool &= DefaultMXBeanMappingFactory.isIdentity(this.paramMappings[b]);
/*     */     } 
/* 220 */     this.paramConversionIsIdentity = bool;
/*     */   }
/*     */   
/* 223 */   private static final String[] noStrings = new String[0];
/*     */   private final Method method;
/*     */   private final MXBeanMapping returnMapping;
/*     */   private final MXBeanMapping[] paramMappings;
/*     */   private final boolean paramConversionIsIdentity;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/ConvertingMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */