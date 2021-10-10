/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanOperationInfo;
/*     */ import javax.management.MBeanParameterInfo;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
/*     */ import javax.management.openmbean.OpenMBeanOperationInfoSupport;
/*     */ import javax.management.openmbean.OpenMBeanParameterInfo;
/*     */ import javax.management.openmbean.OpenMBeanParameterInfoSupport;
/*     */ import javax.management.openmbean.OpenType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MXBeanIntrospector
/*     */   extends MBeanIntrospector<ConvertingMethod>
/*     */ {
/*  55 */   private static final MXBeanIntrospector instance = new MXBeanIntrospector();
/*     */   
/*     */   static MXBeanIntrospector getInstance() {
/*  58 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanIntrospector.PerInterfaceMap<ConvertingMethod> getPerInterfaceMap() {
/*  63 */     return this.perInterfaceMap;
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanIntrospector.MBeanInfoMap getMBeanInfoMap() {
/*  68 */     return mbeanInfoMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MBeanAnalyzer<ConvertingMethod> getAnalyzer(Class<?> paramClass) throws NotCompliantMBeanException {
/*  74 */     return MBeanAnalyzer.analyzer(paramClass, this);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isMXBean() {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   ConvertingMethod mFrom(Method paramMethod) {
/*  84 */     return ConvertingMethod.from(paramMethod);
/*     */   }
/*     */ 
/*     */   
/*     */   String getName(ConvertingMethod paramConvertingMethod) {
/*  89 */     return paramConvertingMethod.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   Type getGenericReturnType(ConvertingMethod paramConvertingMethod) {
/*  94 */     return paramConvertingMethod.getGenericReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   Type[] getGenericParameterTypes(ConvertingMethod paramConvertingMethod) {
/*  99 */     return paramConvertingMethod.getGenericParameterTypes();
/*     */   }
/*     */ 
/*     */   
/*     */   String[] getSignature(ConvertingMethod paramConvertingMethod) {
/* 104 */     return paramConvertingMethod.getOpenSignature();
/*     */   }
/*     */ 
/*     */   
/*     */   void checkMethod(ConvertingMethod paramConvertingMethod) {
/* 109 */     paramConvertingMethod.checkCallFromOpen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object invokeM2(ConvertingMethod paramConvertingMethod, Object paramObject1, Object[] paramArrayOfObject, Object paramObject2) throws InvocationTargetException, IllegalAccessException, MBeanException {
/* 117 */     return paramConvertingMethod.invokeWithOpenReturn((MXBeanLookup)paramObject2, paramObject1, paramArrayOfObject);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean validParameter(ConvertingMethod paramConvertingMethod, Object paramObject1, int paramInt, Object paramObject2) {
/*     */     Object object;
/* 123 */     if (paramObject1 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       object = paramConvertingMethod.getGenericParameterTypes()[paramInt];
/* 129 */       return (!(object instanceof Class) || !((Class)object).isPrimitive());
/*     */     } 
/*     */     
/*     */     try {
/* 133 */       object = paramConvertingMethod.fromOpenParameter((MXBeanLookup)paramObject2, paramObject1, paramInt);
/* 134 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 137 */       return true;
/*     */     } 
/* 139 */     return isValidParameter(paramConvertingMethod.getMethod(), object, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanAttributeInfo getMBeanAttributeInfo(String paramString, ConvertingMethod paramConvertingMethod1, ConvertingMethod paramConvertingMethod2) {
/*     */     OpenType<?> openType;
/*     */     Type type;
/*     */     MBeanAttributeInfo mBeanAttributeInfo;
/* 147 */     boolean bool1 = (paramConvertingMethod1 != null) ? true : false;
/* 148 */     boolean bool2 = (paramConvertingMethod2 != null) ? true : false;
/* 149 */     boolean bool3 = (bool1 && getName(paramConvertingMethod1).startsWith("is")) ? true : false;
/*     */     
/* 151 */     String str = paramString;
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (bool1) {
/* 156 */       openType = paramConvertingMethod1.getOpenReturnType();
/* 157 */       type = paramConvertingMethod1.getGenericReturnType();
/*     */     } else {
/* 159 */       openType = paramConvertingMethod2.getOpenParameterTypes()[0];
/* 160 */       type = paramConvertingMethod2.getGenericParameterTypes()[0];
/*     */     } 
/* 162 */     Descriptor descriptor = typeDescriptor(openType, type);
/* 163 */     if (bool1) {
/* 164 */       descriptor = ImmutableDescriptor.union(new Descriptor[] { descriptor, paramConvertingMethod1
/* 165 */             .getDescriptor() });
/*     */     }
/* 167 */     if (bool2) {
/* 168 */       descriptor = ImmutableDescriptor.union(new Descriptor[] { descriptor, paramConvertingMethod2
/* 169 */             .getDescriptor() });
/*     */     }
/*     */ 
/*     */     
/* 173 */     if (canUseOpenInfo(type)) {
/* 174 */       mBeanAttributeInfo = new OpenMBeanAttributeInfoSupport(paramString, str, openType, bool1, bool2, bool3, descriptor);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 183 */       mBeanAttributeInfo = new MBeanAttributeInfo(paramString, originalTypeString(type), str, bool1, bool2, bool3, descriptor);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     return mBeanAttributeInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   MBeanOperationInfo getMBeanOperationInfo(String paramString, ConvertingMethod paramConvertingMethod) {
/*     */     MBeanOperationInfo mBeanOperationInfo;
/* 199 */     Method method = paramConvertingMethod.getMethod();
/* 200 */     String str = paramString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     OpenType<?> openType = paramConvertingMethod.getOpenReturnType();
/* 209 */     Type type = paramConvertingMethod.getGenericReturnType();
/* 210 */     OpenType[] arrayOfOpenType = (OpenType[])paramConvertingMethod.getOpenParameterTypes();
/* 211 */     Type[] arrayOfType = paramConvertingMethod.getGenericParameterTypes();
/* 212 */     MBeanParameterInfo[] arrayOfMBeanParameterInfo = new MBeanParameterInfo[arrayOfOpenType.length];
/*     */     
/* 214 */     boolean bool = canUseOpenInfo(type);
/* 215 */     boolean bool1 = true;
/* 216 */     Annotation[][] arrayOfAnnotation = method.getParameterAnnotations();
/* 217 */     for (byte b = 0; b < arrayOfOpenType.length; b++) {
/* 218 */       MBeanParameterInfo mBeanParameterInfo; String str1 = "p" + b;
/* 219 */       String str2 = str1;
/* 220 */       OpenType<?> openType1 = arrayOfOpenType[b];
/* 221 */       Type type1 = arrayOfType[b];
/*     */       
/* 223 */       Descriptor descriptor1 = typeDescriptor(openType1, type1);
/* 224 */       descriptor1 = ImmutableDescriptor.union(new Descriptor[] { descriptor1, 
/* 225 */             Introspector.descriptorForAnnotations(arrayOfAnnotation[b]) });
/*     */       
/* 227 */       if (canUseOpenInfo(type1)) {
/* 228 */         mBeanParameterInfo = new OpenMBeanParameterInfoSupport(str1, str2, openType1, descriptor1);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 233 */         bool1 = false;
/*     */ 
/*     */         
/* 236 */         mBeanParameterInfo = new MBeanParameterInfo(str1, originalTypeString(type1), str2, descriptor1);
/*     */       } 
/*     */ 
/*     */       
/* 240 */       arrayOfMBeanParameterInfo[b] = mBeanParameterInfo;
/*     */     } 
/*     */ 
/*     */     
/* 244 */     Descriptor descriptor = typeDescriptor(openType, type);
/* 245 */     descriptor = ImmutableDescriptor.union(new Descriptor[] { descriptor, 
/* 246 */           Introspector.descriptorForElement(method) });
/*     */     
/* 248 */     if (bool && bool1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       OpenMBeanParameterInfo[] arrayOfOpenMBeanParameterInfo = new OpenMBeanParameterInfo[arrayOfMBeanParameterInfo.length];
/*     */       
/* 259 */       System.arraycopy(arrayOfMBeanParameterInfo, 0, arrayOfOpenMBeanParameterInfo, 0, arrayOfMBeanParameterInfo.length);
/* 260 */       mBeanOperationInfo = new OpenMBeanOperationInfoSupport(paramString, str, arrayOfOpenMBeanParameterInfo, openType, 3, descriptor);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       mBeanOperationInfo = new MBeanOperationInfo(paramString, str, arrayOfMBeanParameterInfo, bool ? openType.getClassName() : originalTypeString(type), 3, descriptor);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return mBeanOperationInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   Descriptor getBasicMBeanDescriptor() {
/* 282 */     return new ImmutableDescriptor(new String[] { "mxbean=true", "immutableInfo=true" });
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
/*     */   Descriptor getMBeanDescriptor(Class<?> paramClass) {
/* 294 */     return ImmutableDescriptor.EMPTY_DESCRIPTOR;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Descriptor typeDescriptor(OpenType<?> paramOpenType, Type paramType) {
/* 299 */     return new ImmutableDescriptor(new String[] { "openType", "originalType" }, new Object[] { paramOpenType, 
/*     */ 
/*     */ 
/*     */           
/* 303 */           originalTypeString(paramType) });
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
/*     */   private static boolean canUseOpenInfo(Type paramType) {
/* 320 */     if (paramType instanceof GenericArrayType)
/* 321 */       return canUseOpenInfo(((GenericArrayType)paramType)
/* 322 */           .getGenericComponentType()); 
/* 323 */     if (paramType instanceof Class && ((Class)paramType).isArray()) {
/* 324 */       return canUseOpenInfo(((Class)paramType)
/* 325 */           .getComponentType());
/*     */     }
/* 327 */     return (!(paramType instanceof Class) || !((Class)paramType).isPrimitive());
/*     */   }
/*     */   
/*     */   private static String originalTypeString(Type paramType) {
/* 331 */     if (paramType instanceof Class) {
/* 332 */       return ((Class)paramType).getName();
/*     */     }
/* 334 */     return typeName(paramType);
/*     */   }
/*     */   
/*     */   static String typeName(Type paramType) {
/* 338 */     if (paramType instanceof Class) {
/* 339 */       Class clazz = (Class)paramType;
/* 340 */       if (clazz.isArray()) {
/* 341 */         return typeName(clazz.getComponentType()) + "[]";
/*     */       }
/* 343 */       return clazz.getName();
/* 344 */     }  if (paramType instanceof GenericArrayType) {
/* 345 */       GenericArrayType genericArrayType = (GenericArrayType)paramType;
/* 346 */       return typeName(genericArrayType.getGenericComponentType()) + "[]";
/* 347 */     }  if (paramType instanceof ParameterizedType) {
/* 348 */       ParameterizedType parameterizedType = (ParameterizedType)paramType;
/* 349 */       StringBuilder stringBuilder = new StringBuilder();
/* 350 */       stringBuilder.append(typeName(parameterizedType.getRawType())).append("<");
/* 351 */       String str = "";
/* 352 */       for (Type type : parameterizedType.getActualTypeArguments()) {
/* 353 */         stringBuilder.append(str).append(typeName(type));
/* 354 */         str = ", ";
/*     */       } 
/* 356 */       return stringBuilder.append(">").toString();
/*     */     } 
/* 358 */     return "???";
/*     */   }
/*     */   
/* 361 */   private final MBeanIntrospector.PerInterfaceMap<ConvertingMethod> perInterfaceMap = new MBeanIntrospector.PerInterfaceMap<>();
/*     */ 
/*     */   
/* 364 */   private static final MBeanIntrospector.MBeanInfoMap mbeanInfoMap = new MBeanIntrospector.MBeanInfoMap();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MXBeanIntrospector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */