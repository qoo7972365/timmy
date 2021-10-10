/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
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
/*    */ class RuntimeReferencePropertyInfoImpl
/*    */   extends ReferencePropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeReferencePropertyInfo
/*    */ {
/*    */   private final Accessor acc;
/*    */   
/*    */   public RuntimeReferencePropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 46 */     super(classInfo, seed);
/* 47 */     Accessor rawAcc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/* 48 */     if (getAdapter() != null && !isCollection())
/*    */     {
/*    */       
/* 51 */       rawAcc = rawAcc.adapt(getAdapter()); } 
/* 52 */     this.acc = rawAcc;
/*    */   }
/*    */   
/*    */   public Set<? extends RuntimeElement> getElements() {
/* 56 */     return (Set)super.getElements();
/*    */   }
/*    */   
/*    */   public Set<? extends RuntimeElement> ref() {
/* 60 */     return (Set)super.ref();
/*    */   }
/*    */   
/*    */   public Accessor getAccessor() {
/* 64 */     return this.acc;
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 68 */     return !isMixed();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeReferencePropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */