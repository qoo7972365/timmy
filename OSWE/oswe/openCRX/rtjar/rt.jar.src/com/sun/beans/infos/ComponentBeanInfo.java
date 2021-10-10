/*    */ package com.sun.beans.infos;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.beans.IntrospectionException;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.beans.SimpleBeanInfo;
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
/*    */ public class ComponentBeanInfo
/*    */   extends SimpleBeanInfo
/*    */ {
/* 35 */   private static final Class<Component> beanClass = Component.class;
/*    */ 
/*    */   
/*    */   public PropertyDescriptor[] getPropertyDescriptors() {
/*    */     try {
/* 40 */       PropertyDescriptor propertyDescriptor1 = new PropertyDescriptor("name", beanClass);
/* 41 */       PropertyDescriptor propertyDescriptor2 = new PropertyDescriptor("background", beanClass);
/* 42 */       PropertyDescriptor propertyDescriptor3 = new PropertyDescriptor("foreground", beanClass);
/* 43 */       PropertyDescriptor propertyDescriptor4 = new PropertyDescriptor("font", beanClass);
/* 44 */       PropertyDescriptor propertyDescriptor5 = new PropertyDescriptor("enabled", beanClass);
/* 45 */       PropertyDescriptor propertyDescriptor6 = new PropertyDescriptor("visible", beanClass);
/* 46 */       PropertyDescriptor propertyDescriptor7 = new PropertyDescriptor("focusable", beanClass);
/*    */       
/* 48 */       propertyDescriptor5.setExpert(true);
/* 49 */       propertyDescriptor6.setHidden(true);
/*    */       
/* 51 */       propertyDescriptor2.setBound(true);
/* 52 */       propertyDescriptor3.setBound(true);
/* 53 */       propertyDescriptor4.setBound(true);
/* 54 */       propertyDescriptor7.setBound(true);
/*    */       
/* 56 */       return new PropertyDescriptor[] { propertyDescriptor1, propertyDescriptor2, propertyDescriptor3, propertyDescriptor4, propertyDescriptor5, propertyDescriptor6, propertyDescriptor7 };
/*    */     }
/* 58 */     catch (IntrospectionException introspectionException) {
/* 59 */       throw new Error(introspectionException.toString());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/infos/ComponentBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */