/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.JMX;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MXBeanSupport
/*     */   extends MBeanSupport<ConvertingMethod>
/*     */ {
/*     */   private final Object lock;
/*     */   private MXBeanLookup mxbeanLookup;
/*     */   private ObjectName objectName;
/*     */   
/*     */   public <T> MXBeanSupport(T paramT, Class<T> paramClass) throws NotCompliantMBeanException {
/*  66 */     super(paramT, paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     this.lock = new Object();
/*     */   }
/*     */   
/*     */   MBeanIntrospector<ConvertingMethod> getMBeanIntrospector() {
/*     */     return MXBeanIntrospector.getInstance();
/*     */   }
/*     */   
/*     */   Object getCookie() {
/*     */     return this.mxbeanLookup;
/*     */   }
/*     */   
/*     */   static <T> Class<? super T> findMXBeanInterface(Class<T> paramClass) {
/*     */     if (paramClass == null)
/*     */       throw new IllegalArgumentException("Null resource class"); 
/*     */     Set<Class<?>> set = transitiveInterfaces(paramClass);
/*     */     Set<?> set1 = Util.newSet();
/*     */     for (Class<?> clazz : set) {
/*     */       if (JMX.isMXBeanInterface(clazz))
/*     */         set1.add(clazz); 
/*     */     } 
/*     */     while (set1.size() > 1) {
/*     */       for (Class<?> clazz : set1) {
/*     */         Iterator<?> iterator;
/*     */         for (iterator = set1.iterator(); iterator.hasNext(); ) {
/*     */           Class clazz1 = (Class)iterator.next();
/*     */           if (clazz != clazz1 && clazz1.isAssignableFrom(clazz))
/*     */             iterator.remove(); 
/*     */         } 
/*     */       } 
/*     */       String str1 = "Class " + paramClass.getName() + " implements more than one MXBean interface: " + set1;
/*     */       throw new IllegalArgumentException(str1);
/*     */     } 
/*     */     if (set1.iterator().hasNext())
/*     */       return Util.<Class<? super T>>cast(set1.iterator().next()); 
/*     */     String str = "Class " + paramClass.getName() + " is not a JMX compliant MXBean";
/*     */     throw new IllegalArgumentException(str);
/*     */   }
/*     */   
/*     */   private static Set<Class<?>> transitiveInterfaces(Class<?> paramClass) {
/*     */     Set<?> set = Util.newSet();
/*     */     transitiveInterfaces(paramClass, (Set)set);
/*     */     return (Set)set;
/*     */   }
/*     */   
/*     */   private static void transitiveInterfaces(Class<?> paramClass, Set<Class<?>> paramSet) {
/*     */     if (paramClass == null)
/*     */       return; 
/*     */     if (paramClass.isInterface())
/*     */       paramSet.add(paramClass); 
/*     */     transitiveInterfaces(paramClass.getSuperclass(), paramSet);
/*     */     for (Class<?> clazz : paramClass.getInterfaces())
/*     */       transitiveInterfaces(clazz, paramSet); 
/*     */   }
/*     */   
/*     */   public void register(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws InstanceAlreadyExistsException {
/*     */     if (paramObjectName == null)
/*     */       throw new IllegalArgumentException("Null object name"); 
/*     */     synchronized (this.lock) {
/*     */       this.mxbeanLookup = MXBeanLookup.lookupFor(paramMBeanServer);
/*     */       this.mxbeanLookup.addReference(paramObjectName, getResource());
/*     */       this.objectName = paramObjectName;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregister() {
/*     */     synchronized (this.lock) {
/*     */       if (this.mxbeanLookup != null && this.mxbeanLookup.removeReference(this.objectName, getResource()))
/*     */         this.objectName = null; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/MXBeanSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */