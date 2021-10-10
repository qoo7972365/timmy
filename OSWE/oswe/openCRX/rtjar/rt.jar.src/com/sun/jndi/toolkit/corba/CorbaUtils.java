/*     */ package com.sun.jndi.toolkit.corba;
/*     */ 
/*     */ import com.sun.jndi.cosnaming.CNCtx;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import javax.naming.ConfigurationException;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorbaUtils
/*     */ {
/*     */   public static Object remoteToCorba(Remote paramRemote, ORB paramORB) throws ClassNotFoundException, ConfigurationException {
/*     */     Object object;
/*  86 */     synchronized (CorbaUtils.class) {
/*  87 */       if (toStubMethod == null) {
/*  88 */         initMethodHandles();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  99 */       object = toStubMethod.invoke(null, new Object[] { paramRemote });
/*     */     }
/* 101 */     catch (InvocationTargetException invocationTargetException) {
/* 102 */       Throwable throwable = invocationTargetException.getTargetException();
/*     */ 
/*     */       
/* 105 */       ConfigurationException configurationException = new ConfigurationException("Problem with PortableRemoteObject.toStub(); object not exported or stub not found");
/*     */       
/* 107 */       configurationException.setRootCause(throwable);
/* 108 */       throw configurationException;
/*     */     }
/* 110 */     catch (IllegalAccessException illegalAccessException) {
/* 111 */       ConfigurationException configurationException = new ConfigurationException("Cannot invoke javax.rmi.PortableRemoteObject.toStub(java.rmi.Remote)");
/*     */ 
/*     */       
/* 114 */       configurationException.setRootCause(illegalAccessException);
/* 115 */       throw configurationException;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (!corbaStubClass.isInstance(object)) {
/* 121 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       connectMethod.invoke(object, new Object[] { paramORB });
/*     */     }
/* 129 */     catch (InvocationTargetException invocationTargetException) {
/* 130 */       Throwable throwable = invocationTargetException.getTargetException();
/*     */ 
/*     */       
/* 133 */       if (!(throwable instanceof java.rmi.RemoteException)) {
/* 134 */         ConfigurationException configurationException = new ConfigurationException("Problem invoking javax.rmi.CORBA.Stub.connect()");
/*     */         
/* 136 */         configurationException.setRootCause(throwable);
/* 137 */         throw configurationException;
/*     */       }
/*     */     
/*     */     }
/* 141 */     catch (IllegalAccessException illegalAccessException) {
/* 142 */       ConfigurationException configurationException = new ConfigurationException("Cannot invoke javax.rmi.CORBA.Stub.connect()");
/*     */       
/* 144 */       configurationException.setRootCause(illegalAccessException);
/* 145 */       throw configurationException;
/*     */     } 
/*     */     
/* 148 */     return (Object)object;
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
/*     */   public static ORB getOrb(String paramString, int paramInt, Hashtable<?, ?> paramHashtable) {
/*     */     Properties properties;
/* 167 */     if (paramHashtable != null) {
/* 168 */       if (paramHashtable instanceof Properties) {
/*     */         
/* 170 */         properties = (Properties)paramHashtable.clone();
/*     */       }
/*     */       else {
/*     */         
/* 174 */         properties = new Properties();
/* 175 */         for (Enumeration<?> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/* 176 */           String str = (String)enumeration.nextElement();
/* 177 */           Object object = paramHashtable.get(str);
/* 178 */           if (object instanceof String) {
/* 179 */             properties.put(str, object);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 184 */       properties = new Properties();
/*     */     } 
/*     */     
/* 187 */     if (paramString != null) {
/* 188 */       properties.put("org.omg.CORBA.ORBInitialHost", paramString);
/*     */     }
/* 190 */     if (paramInt >= 0) {
/* 191 */       properties.put("org.omg.CORBA.ORBInitialPort", "" + paramInt);
/*     */     }
/*     */ 
/*     */     
/* 195 */     if (paramHashtable != null) {
/* 196 */       Object object = paramHashtable.get("java.naming.applet");
/* 197 */       if (object != null)
/*     */       {
/* 199 */         return initAppletORB(object, properties);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return ORB.init(new String[0], properties);
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
/*     */   public static boolean isObjectFactoryTrusted(Object paramObject) throws NamingException {
/* 217 */     Reference reference = null;
/* 218 */     if (paramObject instanceof Reference) {
/* 219 */       reference = (Reference)paramObject;
/* 220 */     } else if (paramObject instanceof Referenceable) {
/* 221 */       reference = ((Referenceable)paramObject).getReference();
/*     */     } 
/*     */     
/* 224 */     if (reference != null && reference.getFactoryClassLocation() != null && !CNCtx.trustURLCodebase)
/*     */     {
/* 226 */       throw new ConfigurationException("The object factory is untrusted. Set the system property 'com.sun.jndi.cosnaming.object.trustURLCodebase' to 'true'.");
/*     */     }
/*     */ 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ORB initAppletORB(Object paramObject, Properties paramProperties) {
/*     */     try {
/* 239 */       Class<?> clazz = Class.forName("java.applet.Applet", true, null);
/* 240 */       if (!clazz.isInstance(paramObject)) {
/* 241 */         throw new ClassCastException(paramObject.getClass().getName());
/*     */       }
/*     */ 
/*     */       
/* 245 */       Method method = ORB.class.getMethod("init", new Class[] { clazz, Properties.class });
/* 246 */       return (ORB)method.invoke(null, new Object[] { paramObject, paramProperties });
/* 247 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */       
/* 250 */       throw new ClassCastException(paramObject.getClass().getName());
/* 251 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 252 */       throw new AssertionError(noSuchMethodException);
/* 253 */     } catch (InvocationTargetException invocationTargetException) {
/* 254 */       Throwable throwable = invocationTargetException.getCause();
/* 255 */       if (throwable instanceof RuntimeException)
/* 256 */         throw (RuntimeException)throwable; 
/* 257 */       if (throwable instanceof Error) {
/* 258 */         throw (Error)throwable;
/*     */       }
/* 260 */       throw new AssertionError(invocationTargetException);
/* 261 */     } catch (IllegalAccessException illegalAccessException) {
/* 262 */       throw new AssertionError(illegalAccessException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 267 */   private static Method toStubMethod = null;
/* 268 */   private static Method connectMethod = null;
/* 269 */   private static Class<?> corbaStubClass = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initMethodHandles() throws ClassNotFoundException {
/* 276 */     corbaStubClass = Class.forName("javax.rmi.CORBA.Stub");
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 281 */       connectMethod = corbaStubClass.getMethod("connect", new Class[] { ORB.class });
/*     */     }
/* 283 */     catch (NoSuchMethodException noSuchMethodException) {
/* 284 */       throw new IllegalStateException("No method definition for javax.rmi.CORBA.Stub.connect(org.omg.CORBA.ORB)");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 289 */     Class<?> clazz = Class.forName("javax.rmi.PortableRemoteObject");
/*     */ 
/*     */     
/*     */     try {
/* 293 */       toStubMethod = clazz.getMethod("toStub", new Class[] { Remote.class });
/*     */     
/*     */     }
/* 296 */     catch (NoSuchMethodException noSuchMethodException) {
/* 297 */       throw new IllegalStateException("No method definition for javax.rmi.PortableRemoteObject.toStub(java.rmi.Remote)");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/corba/CorbaUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */