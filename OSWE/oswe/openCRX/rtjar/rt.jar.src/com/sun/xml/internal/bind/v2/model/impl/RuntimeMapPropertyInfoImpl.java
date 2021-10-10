/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeMapPropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
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
/*    */ class RuntimeMapPropertyInfoImpl
/*    */   extends MapPropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeMapPropertyInfo
/*    */ {
/*    */   private final Accessor acc;
/*    */   
/*    */   RuntimeMapPropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 45 */     super(classInfo, seed);
/* 46 */     this.acc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/*    */   }
/*    */   
/*    */   public Accessor getAccessor() {
/* 50 */     return this.acc;
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getKeyType() {
/* 58 */     return (RuntimeNonElement)super.getKeyType();
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getValueType() {
/* 62 */     return (RuntimeNonElement)super.getValueType();
/*    */   }
/*    */   
/*    */   public List<? extends RuntimeTypeInfo> ref() {
/* 66 */     return (List)super.ref();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeMapPropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */