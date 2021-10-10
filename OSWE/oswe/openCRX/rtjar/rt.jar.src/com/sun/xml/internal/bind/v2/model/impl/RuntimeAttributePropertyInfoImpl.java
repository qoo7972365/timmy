/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
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
/*    */ class RuntimeAttributePropertyInfoImpl
/*    */   extends AttributePropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeAttributePropertyInfo
/*    */ {
/*    */   RuntimeAttributePropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 43 */     super(classInfo, seed);
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTarget() {
/* 51 */     return (RuntimeNonElement)super.getTarget();
/*    */   }
/*    */   
/*    */   public List<? extends RuntimeNonElement> ref() {
/* 55 */     return (List)super.ref();
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getSource() {
/* 59 */     return (RuntimePropertyInfo)this;
/*    */   }
/*    */   
/*    */   public void link() {
/* 63 */     getTransducer();
/* 64 */     super.link();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeAttributePropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */