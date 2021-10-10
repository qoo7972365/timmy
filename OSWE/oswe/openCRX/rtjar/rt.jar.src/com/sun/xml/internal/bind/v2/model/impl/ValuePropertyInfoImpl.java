/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*    */ import com.sun.xml.internal.bind.v2.model.core.ValuePropertyInfo;
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
/*    */ class ValuePropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*    */   extends SingleTypePropertyInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*    */   implements ValuePropertyInfo<TypeT, ClassDeclT>
/*    */ {
/*    */   ValuePropertyInfoImpl(ClassInfoImpl<TypeT, ClassDeclT, FieldT, MethodT> parent, PropertySeed<TypeT, ClassDeclT, FieldT, MethodT> seed) {
/* 42 */     super(parent, seed);
/*    */   }
/*    */   
/*    */   public PropertyKind kind() {
/* 46 */     return PropertyKind.VALUE;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ValuePropertyInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */