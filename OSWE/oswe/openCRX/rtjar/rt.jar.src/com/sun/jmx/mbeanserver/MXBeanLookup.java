/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.util.Map;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.JMX;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.MBeanServerInvocationHandler;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MXBeanLookup
/*     */ {
/*     */   private MXBeanLookup(MBeanServerConnection paramMBeanServerConnection) {
/* 181 */     this
/* 182 */       .mxbeanToObjectName = WeakIdentityHashMap.make();
/* 183 */     this
/* 184 */       .objectNameToProxy = Util.newMap();
/*     */     this.mbsc = paramMBeanServerConnection;
/*     */   }
/* 187 */   private static final WeakIdentityHashMap<MBeanServerConnection, WeakReference<MXBeanLookup>> mbscToLookup = WeakIdentityHashMap.make();
/*     */   
/*     */   static MXBeanLookup lookupFor(MBeanServerConnection paramMBeanServerConnection) {
/*     */     synchronized (mbscToLookup) {
/*     */       WeakReference<MXBeanLookup> weakReference = mbscToLookup.get(paramMBeanServerConnection);
/*     */       MXBeanLookup mXBeanLookup = (weakReference == null) ? null : weakReference.get();
/*     */       if (mXBeanLookup == null) {
/*     */         mXBeanLookup = new MXBeanLookup(paramMBeanServerConnection);
/*     */         mbscToLookup.put(paramMBeanServerConnection, new WeakReference<>(mXBeanLookup));
/*     */       } 
/*     */       return mXBeanLookup;
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized <T> T objectNameToMXBean(ObjectName paramObjectName, Class<T> paramClass) {
/*     */     WeakReference<Object> weakReference = this.objectNameToProxy.get(paramObjectName);
/*     */     if (weakReference != null) {
/*     */       Object object = weakReference.get();
/*     */       if (paramClass.isInstance(object))
/*     */         return paramClass.cast(object); 
/*     */     } 
/*     */     T t = (T)JMX.newMXBeanProxy(this.mbsc, paramObjectName, (Class)paramClass);
/*     */     this.objectNameToProxy.put(paramObjectName, new WeakReference(t));
/*     */     return t;
/*     */   }
/*     */   
/*     */   synchronized ObjectName mxbeanToObjectName(Object paramObject) throws OpenDataException {
/*     */     String str1;
/*     */     if (paramObject instanceof Proxy) {
/*     */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(paramObject);
/*     */       if (invocationHandler instanceof MBeanServerInvocationHandler) {
/*     */         MBeanServerInvocationHandler mBeanServerInvocationHandler = (MBeanServerInvocationHandler)invocationHandler;
/*     */         if (mBeanServerInvocationHandler.getMBeanServerConnection().equals(this.mbsc))
/*     */           return mBeanServerInvocationHandler.getObjectName(); 
/*     */         str1 = "proxy for a different MBeanServer";
/*     */       } else {
/*     */         str1 = "not a JMX proxy";
/*     */       } 
/*     */     } else {
/*     */       ObjectName objectName = this.mxbeanToObjectName.get(paramObject);
/*     */       if (objectName != null)
/*     */         return objectName; 
/*     */       str1 = "not an MXBean registered in this MBeanServer";
/*     */     } 
/*     */     String str2 = (paramObject == null) ? "null" : ("object of type " + paramObject.getClass().getName());
/*     */     throw new OpenDataException("Could not convert " + str2 + " to an ObjectName: " + str1);
/*     */   }
/*     */   
/*     */   synchronized void addReference(ObjectName paramObjectName, Object paramObject) throws InstanceAlreadyExistsException {
/*     */     ObjectName objectName = this.mxbeanToObjectName.get(paramObject);
/*     */     if (objectName != null) {
/*     */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("jmx.mxbean.multiname"));
/*     */       if (!"true".equalsIgnoreCase(str))
/*     */         throw new InstanceAlreadyExistsException("MXBean already registered with name " + objectName); 
/*     */     } 
/*     */     this.mxbeanToObjectName.put(paramObject, paramObjectName);
/*     */   }
/*     */   
/*     */   synchronized boolean removeReference(ObjectName paramObjectName, Object paramObject) {
/*     */     if (paramObjectName.equals(this.mxbeanToObjectName.get(paramObject))) {
/*     */       this.mxbeanToObjectName.remove(paramObject);
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   static MXBeanLookup getLookup() {
/*     */     return currentLookup.get();
/*     */   }
/*     */   
/*     */   static void setLookup(MXBeanLookup paramMXBeanLookup) {
/*     */     currentLookup.set(paramMXBeanLookup);
/*     */   }
/*     */   
/*     */   private static final ThreadLocal<MXBeanLookup> currentLookup = new ThreadLocal<>();
/*     */   private final MBeanServerConnection mbsc;
/*     */   private final WeakIdentityHashMap<Object, ObjectName> mxbeanToObjectName;
/*     */   private final Map<ObjectName, WeakReference<Object>> objectNameToProxy;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MXBeanLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */