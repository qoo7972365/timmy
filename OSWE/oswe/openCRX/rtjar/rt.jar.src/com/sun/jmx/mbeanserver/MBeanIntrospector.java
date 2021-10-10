/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.MBeanAttributeInfo;
/*     */ import javax.management.MBeanConstructorInfo;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.MBeanOperationInfo;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationBroadcaster;
/*     */ import javax.management.ReflectionException;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MBeanIntrospector<M>
/*     */ {
/*     */   abstract PerInterfaceMap<M> getPerInterfaceMap();
/*     */   
/*     */   abstract MBeanInfoMap getMBeanInfoMap();
/*     */   
/*     */   abstract MBeanAnalyzer<M> getAnalyzer(Class<?> paramClass) throws NotCompliantMBeanException;
/*     */   
/*     */   abstract boolean isMXBean();
/*     */   
/*     */   abstract M mFrom(Method paramMethod);
/*     */   
/*     */   abstract String getName(M paramM);
/*     */   
/*     */   abstract Type getGenericReturnType(M paramM);
/*     */   
/*     */   abstract Type[] getGenericParameterTypes(M paramM);
/*     */   
/*     */   abstract String[] getSignature(M paramM);
/*     */   
/*     */   abstract void checkMethod(M paramM);
/*     */   
/*     */   abstract Object invokeM2(M paramM, Object paramObject1, Object[] paramArrayOfObject, Object paramObject2) throws InvocationTargetException, IllegalAccessException, MBeanException;
/*     */   
/*     */   abstract boolean validParameter(M paramM, Object paramObject1, int paramInt, Object paramObject2);
/*     */   
/*     */   abstract MBeanAttributeInfo getMBeanAttributeInfo(String paramString, M paramM1, M paramM2);
/*     */   
/*     */   abstract MBeanOperationInfo getMBeanOperationInfo(String paramString, M paramM);
/*     */   
/*     */   abstract Descriptor getBasicMBeanDescriptor();
/*     */   
/*     */   abstract Descriptor getMBeanDescriptor(Class<?> paramClass);
/*     */   
/*     */   static final class PerInterfaceMap<M>
/*     */     extends WeakHashMap<Class<?>, WeakReference<PerInterface<M>>> {}
/*     */   
/*     */   final List<Method> getMethods(Class<?> paramClass) {
/* 180 */     ReflectUtil.checkPackageAccess(paramClass);
/* 181 */     return Arrays.asList(paramClass.getMethods());
/*     */   }
/*     */ 
/*     */   
/*     */   final PerInterface<M> getPerInterface(Class<?> paramClass) throws NotCompliantMBeanException {
/* 186 */     PerInterfaceMap<M> perInterfaceMap = getPerInterfaceMap();
/* 187 */     synchronized (perInterfaceMap) {
/* 188 */       WeakReference<PerInterface<M>> weakReference = perInterfaceMap.get(paramClass);
/* 189 */       PerInterface<M> perInterface = (weakReference == null) ? null : weakReference.get();
/* 190 */       if (perInterface == null) {
/*     */         try {
/* 192 */           MBeanAnalyzer<M> mBeanAnalyzer = getAnalyzer(paramClass);
/*     */           
/* 194 */           MBeanInfo mBeanInfo = makeInterfaceMBeanInfo(paramClass, mBeanAnalyzer);
/* 195 */           perInterface = new PerInterface<>(paramClass, this, mBeanAnalyzer, mBeanInfo);
/*     */           
/* 197 */           weakReference = new WeakReference<>(perInterface);
/* 198 */           perInterfaceMap.put(paramClass, weakReference);
/* 199 */         } catch (Exception exception) {
/* 200 */           throw Introspector.throwException(paramClass, exception);
/*     */         } 
/*     */       }
/* 203 */       return perInterface;
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
/*     */   
/*     */   private MBeanInfo makeInterfaceMBeanInfo(Class<?> paramClass, MBeanAnalyzer<M> paramMBeanAnalyzer) {
/* 217 */     MBeanInfoMaker mBeanInfoMaker = new MBeanInfoMaker();
/* 218 */     paramMBeanAnalyzer.visit(mBeanInfoMaker);
/*     */ 
/*     */     
/* 221 */     return mBeanInfoMaker.makeMBeanInfo(paramClass, "Information on the management interface of the MBean");
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean consistent(M paramM1, M paramM2) {
/* 226 */     return (paramM1 == null || paramM2 == null || 
/* 227 */       getGenericReturnType(paramM1).equals(getGenericParameterTypes(paramM2)[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Object invokeM(M paramM, Object paramObject1, Object[] paramArrayOfObject, Object paramObject2) throws MBeanException, ReflectionException {
/*     */     try {
/* 237 */       return invokeM2(paramM, paramObject1, paramArrayOfObject, paramObject2);
/* 238 */     } catch (InvocationTargetException invocationTargetException) {
/* 239 */       unwrapInvocationTargetException(invocationTargetException);
/* 240 */       throw new RuntimeException(invocationTargetException);
/* 241 */     } catch (IllegalAccessException illegalAccessException) {
/* 242 */       throw new ReflectionException(illegalAccessException, illegalAccessException.toString());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void invokeSetter(String paramString, M paramM, Object paramObject1, Object paramObject2, Object paramObject3) throws MBeanException, ReflectionException, InvalidAttributeValueException {
/*     */     try {
/* 267 */       invokeM2(paramM, paramObject1, new Object[] { paramObject2 }, paramObject3);
/* 268 */     } catch (IllegalAccessException illegalAccessException) {
/* 269 */       throw new ReflectionException(illegalAccessException, illegalAccessException.toString());
/* 270 */     } catch (RuntimeException runtimeException) {
/* 271 */       maybeInvalidParameter(paramString, paramM, paramObject2, paramObject3);
/* 272 */       throw runtimeException;
/* 273 */     } catch (InvocationTargetException invocationTargetException) {
/* 274 */       maybeInvalidParameter(paramString, paramM, paramObject2, paramObject3);
/* 275 */       unwrapInvocationTargetException(invocationTargetException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeInvalidParameter(String paramString, M paramM, Object paramObject1, Object paramObject2) throws InvalidAttributeValueException {
/* 282 */     if (!validParameter(paramM, paramObject1, 0, paramObject2)) {
/* 283 */       String str = "Invalid value for attribute " + paramString + ": " + paramObject1;
/*     */       
/* 285 */       throw new InvalidAttributeValueException(str);
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isValidParameter(Method paramMethod, Object paramObject, int paramInt) {
/* 290 */     Class<?> clazz = paramMethod.getParameterTypes()[paramInt];
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 295 */       Object object = Array.newInstance(clazz, 1);
/* 296 */       Array.set(object, 0, paramObject);
/* 297 */       return true;
/* 298 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 299 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void unwrapInvocationTargetException(InvocationTargetException paramInvocationTargetException) throws MBeanException {
/* 306 */     Throwable throwable = paramInvocationTargetException.getCause();
/* 307 */     if (throwable instanceof RuntimeException)
/* 308 */       throw (RuntimeException)throwable; 
/* 309 */     if (throwable instanceof Error) {
/* 310 */       throw (Error)throwable;
/*     */     }
/* 312 */     throw new MBeanException((Exception)throwable, (throwable == null) ? null : throwable
/* 313 */         .toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MBeanInfoMaker
/*     */     implements MBeanAnalyzer.MBeanVisitor<M>
/*     */   {
/*     */     public void visitAttribute(String param1String, M param1M1, M param1M2) {
/* 324 */       MBeanAttributeInfo mBeanAttributeInfo = MBeanIntrospector.this.getMBeanAttributeInfo(param1String, param1M1, param1M2);
/*     */       
/* 326 */       this.attrs.add(mBeanAttributeInfo);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitOperation(String param1String, M param1M) {
/* 332 */       MBeanOperationInfo mBeanOperationInfo = MBeanIntrospector.this.getMBeanOperationInfo(param1String, param1M);
/*     */       
/* 334 */       this.ops.add(mBeanOperationInfo);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     MBeanInfo makeMBeanInfo(Class<?> param1Class, String param1String) {
/* 342 */       MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = this.attrs.<MBeanAttributeInfo>toArray(new MBeanAttributeInfo[0]);
/*     */       
/* 344 */       MBeanOperationInfo[] arrayOfMBeanOperationInfo = this.ops.<MBeanOperationInfo>toArray(new MBeanOperationInfo[0]);
/*     */       
/* 346 */       String str = "interfaceClassName=" + param1Class.getName();
/* 347 */       ImmutableDescriptor immutableDescriptor1 = new ImmutableDescriptor(new String[] { str });
/*     */       
/* 349 */       Descriptor descriptor1 = MBeanIntrospector.this.getBasicMBeanDescriptor();
/*     */       
/* 351 */       Descriptor descriptor2 = Introspector.descriptorForElement(param1Class);
/*     */       
/* 353 */       ImmutableDescriptor immutableDescriptor2 = DescriptorCache.getInstance().union(new Descriptor[] { immutableDescriptor1, descriptor1, descriptor2 });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 358 */       return new MBeanInfo(param1Class.getName(), param1String, arrayOfMBeanAttributeInfo, null, arrayOfMBeanOperationInfo, null, immutableDescriptor2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     private final List<MBeanAttributeInfo> attrs = Util.newList();
/* 368 */     private final List<MBeanOperationInfo> ops = Util.newList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private MBeanInfoMaker() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class MBeanInfoMap
/*     */     extends WeakHashMap<Class<?>, WeakHashMap<Class<?>, MBeanInfo>> {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final MBeanInfo getMBeanInfo(Object paramObject, PerInterface<M> paramPerInterface) {
/* 392 */     MBeanInfo mBeanInfo = getClassMBeanInfo(paramObject.getClass(), paramPerInterface);
/* 393 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = findNotifications(paramObject);
/* 394 */     if (arrayOfMBeanNotificationInfo == null || arrayOfMBeanNotificationInfo.length == 0) {
/* 395 */       return mBeanInfo;
/*     */     }
/* 397 */     return new MBeanInfo(mBeanInfo.getClassName(), mBeanInfo
/* 398 */         .getDescription(), mBeanInfo
/* 399 */         .getAttributes(), mBeanInfo
/* 400 */         .getConstructors(), mBeanInfo
/* 401 */         .getOperations(), arrayOfMBeanNotificationInfo, mBeanInfo
/*     */         
/* 403 */         .getDescriptor());
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
/*     */   final MBeanInfo getClassMBeanInfo(Class<?> paramClass, PerInterface<M> paramPerInterface) {
/* 416 */     MBeanInfoMap mBeanInfoMap = getMBeanInfoMap();
/* 417 */     synchronized (mBeanInfoMap) {
/* 418 */       WeakHashMap<Class<?>, MBeanInfo> weakHashMap = mBeanInfoMap.get(paramClass);
/* 419 */       if (weakHashMap == null) {
/* 420 */         weakHashMap = new WeakHashMap<>();
/* 421 */         mBeanInfoMap.put(paramClass, weakHashMap);
/*     */       } 
/* 423 */       Class<?> clazz = paramPerInterface.getMBeanInterface();
/* 424 */       MBeanInfo mBeanInfo = weakHashMap.get(clazz);
/* 425 */       if (mBeanInfo == null) {
/* 426 */         MBeanInfo mBeanInfo1 = paramPerInterface.getMBeanInfo();
/*     */         
/* 428 */         ImmutableDescriptor immutableDescriptor = ImmutableDescriptor.union(new Descriptor[] { mBeanInfo1.getDescriptor(), 
/* 429 */               getMBeanDescriptor(paramClass) });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 434 */         mBeanInfo = new MBeanInfo(paramClass.getName(), mBeanInfo1.getDescription(), mBeanInfo1.getAttributes(), findConstructors(paramClass), mBeanInfo1.getOperations(), (MBeanNotificationInfo[])null, immutableDescriptor);
/*     */ 
/*     */         
/* 437 */         weakHashMap.put(clazz, mBeanInfo);
/*     */       } 
/* 439 */       return mBeanInfo;
/*     */     } 
/*     */   }
/*     */   
/*     */   static MBeanNotificationInfo[] findNotifications(Object paramObject) {
/* 444 */     if (!(paramObject instanceof NotificationBroadcaster)) {
/* 445 */       return null;
/*     */     }
/* 447 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo1 = ((NotificationBroadcaster)paramObject).getNotificationInfo();
/* 448 */     if (arrayOfMBeanNotificationInfo1 == null)
/* 449 */       return null; 
/* 450 */     MBeanNotificationInfo[] arrayOfMBeanNotificationInfo2 = new MBeanNotificationInfo[arrayOfMBeanNotificationInfo1.length];
/*     */     
/* 452 */     for (byte b = 0; b < arrayOfMBeanNotificationInfo1.length; b++) {
/* 453 */       MBeanNotificationInfo mBeanNotificationInfo = arrayOfMBeanNotificationInfo1[b];
/* 454 */       if (mBeanNotificationInfo.getClass() != MBeanNotificationInfo.class)
/* 455 */         mBeanNotificationInfo = (MBeanNotificationInfo)mBeanNotificationInfo.clone(); 
/* 456 */       arrayOfMBeanNotificationInfo2[b] = mBeanNotificationInfo;
/*     */     } 
/* 458 */     return arrayOfMBeanNotificationInfo2;
/*     */   }
/*     */   
/*     */   private static MBeanConstructorInfo[] findConstructors(Class<?> paramClass) {
/* 462 */     Constructor[] arrayOfConstructor = (Constructor[])paramClass.getConstructors();
/* 463 */     MBeanConstructorInfo[] arrayOfMBeanConstructorInfo = new MBeanConstructorInfo[arrayOfConstructor.length];
/* 464 */     for (byte b = 0; b < arrayOfConstructor.length; b++)
/*     */     {
/* 466 */       arrayOfMBeanConstructorInfo[b] = new MBeanConstructorInfo("Public constructor of the MBean", arrayOfConstructor[b]);
/*     */     }
/* 468 */     return arrayOfMBeanConstructorInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MBeanIntrospector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */