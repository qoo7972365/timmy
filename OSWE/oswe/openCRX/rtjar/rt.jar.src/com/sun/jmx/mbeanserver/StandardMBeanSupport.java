/*    */ package com.sun.jmx.mbeanserver;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import javax.management.MBeanInfo;
/*    */ import javax.management.MBeanServer;
/*    */ import javax.management.NotCompliantMBeanException;
/*    */ import javax.management.ObjectName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StandardMBeanSupport
/*    */   extends MBeanSupport<Method>
/*    */ {
/*    */   public <T> StandardMBeanSupport(T paramT, Class<T> paramClass) throws NotCompliantMBeanException {
/* 60 */     super(paramT, paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   MBeanIntrospector<Method> getMBeanIntrospector() {
/* 65 */     return StandardMBeanIntrospector.getInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   Object getCookie() {
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(MBeanServer paramMBeanServer, ObjectName paramObjectName) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void unregister() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public MBeanInfo getMBeanInfo() {
/* 85 */     MBeanInfo mBeanInfo = super.getMBeanInfo();
/* 86 */     Class<?> clazz = getResource().getClass();
/* 87 */     if (StandardMBeanIntrospector.isDefinitelyImmutableInfo(clazz))
/* 88 */       return mBeanInfo; 
/* 89 */     return new MBeanInfo(mBeanInfo.getClassName(), mBeanInfo.getDescription(), mBeanInfo
/* 90 */         .getAttributes(), mBeanInfo.getConstructors(), mBeanInfo
/* 91 */         .getOperations(), 
/* 92 */         MBeanIntrospector.findNotifications(getResource()), mBeanInfo
/* 93 */         .getDescriptor());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/StandardMBeanSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */