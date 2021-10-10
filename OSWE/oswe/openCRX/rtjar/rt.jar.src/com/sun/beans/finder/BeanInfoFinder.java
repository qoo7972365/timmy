/*     */ package com.sun.beans.finder;
/*     */ 
/*     */ import java.beans.BeanDescriptor;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.MethodDescriptor;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BeanInfoFinder
/*     */   extends InstanceFinder<BeanInfo>
/*     */ {
/*     */   private static final String DEFAULT = "sun.beans.infos";
/*     */   private static final String DEFAULT_NEW = "com.sun.beans.infos";
/*     */   
/*     */   public BeanInfoFinder() {
/*  48 */     super(BeanInfo.class, true, "BeanInfo", new String[] { "sun.beans.infos" });
/*     */   }
/*     */   
/*     */   private static boolean isValid(Class<?> paramClass, Method paramMethod) {
/*  52 */     return (paramMethod != null && paramMethod.getDeclaringClass().isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeanInfo instantiate(Class<?> paramClass, String paramString1, String paramString2) {
/*  57 */     if ("sun.beans.infos".equals(paramString1)) {
/*  58 */       paramString1 = "com.sun.beans.infos";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     BeanInfo beanInfo = (!"com.sun.beans.infos".equals(paramString1) || "ComponentBeanInfo".equals(paramString2)) ? super.instantiate(paramClass, paramString1, paramString2) : null;
/*     */ 
/*     */     
/*  67 */     if (beanInfo != null) {
/*     */       
/*  69 */       BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
/*  70 */       if (beanDescriptor != null) {
/*  71 */         if (paramClass.equals(beanDescriptor.getBeanClass())) {
/*  72 */           return beanInfo;
/*     */         }
/*     */       } else {
/*     */         
/*  76 */         PropertyDescriptor[] arrayOfPropertyDescriptor = beanInfo.getPropertyDescriptors();
/*  77 */         if (arrayOfPropertyDescriptor != null) {
/*  78 */           for (PropertyDescriptor propertyDescriptor : arrayOfPropertyDescriptor) {
/*  79 */             Method method = propertyDescriptor.getReadMethod();
/*  80 */             if (method == null) {
/*  81 */               method = propertyDescriptor.getWriteMethod();
/*     */             }
/*  83 */             if (isValid(paramClass, method)) {
/*  84 */               return beanInfo;
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/*  89 */           MethodDescriptor[] arrayOfMethodDescriptor = beanInfo.getMethodDescriptors();
/*  90 */           if (arrayOfMethodDescriptor != null) {
/*  91 */             for (MethodDescriptor methodDescriptor : arrayOfMethodDescriptor) {
/*  92 */               if (isValid(paramClass, methodDescriptor.getMethod())) {
/*  93 */                 return beanInfo;
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 100 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/beans/finder/BeanInfoFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */