/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
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
/*    */ final class RuntimeEnumConstantImpl
/*    */   extends EnumConstantImpl<Type, Class, Field, Method>
/*    */ {
/*    */   public RuntimeEnumConstantImpl(RuntimeEnumLeafInfoImpl<Type, Class, Field, Method> owner, String name, String lexical, EnumConstantImpl<Type, Class<?>, Field, Method> next) {
/* 39 */     super(owner, name, lexical, next);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeEnumConstantImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */