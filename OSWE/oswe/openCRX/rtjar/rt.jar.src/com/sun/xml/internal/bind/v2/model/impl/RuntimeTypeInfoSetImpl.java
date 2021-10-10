/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*    */ import com.sun.xml.internal.bind.v2.model.core.ElementInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import javax.xml.namespace.QName;
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
/*    */ final class RuntimeTypeInfoSetImpl
/*    */   extends TypeInfoSetImpl<Type, Class, Field, Method>
/*    */   implements RuntimeTypeInfoSet
/*    */ {
/*    */   public RuntimeTypeInfoSetImpl(AnnotationReader<Type, Class<?>, Field, Method> reader) {
/* 48 */     super(Utils.REFLECTION_NAVIGATOR, reader, (Map)RuntimeBuiltinLeafInfoImpl.LEAVES);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuntimeNonElement createAnyType() {
/* 53 */     return RuntimeAnyTypeImpl.theInstance;
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTypeInfo(Type type) {
/* 57 */     return (RuntimeNonElement)super.getTypeInfo(type);
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getAnyTypeInfo() {
/* 61 */     return (RuntimeNonElement)super.getAnyTypeInfo();
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getClassInfo(Class clazz) {
/* 65 */     return (RuntimeNonElement)super.getClassInfo(clazz);
/*    */   }
/*    */   
/*    */   public Map<Class, RuntimeClassInfoImpl> beans() {
/* 69 */     return (Map)super.beans();
/*    */   }
/*    */   
/*    */   public Map<Type, RuntimeBuiltinLeafInfoImpl<?>> builtins() {
/* 73 */     return (Map)super.builtins();
/*    */   }
/*    */   
/*    */   public Map<Class, RuntimeEnumLeafInfoImpl<?, ?>> enums() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: invokespecial enums : ()Ljava/util/Map;
/*    */     //   4: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #77	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	5	0	this	Lcom/sun/xml/internal/bind/v2/model/impl/RuntimeTypeInfoSetImpl;
/*    */   }
/*    */   
/*    */   public Map<Class, RuntimeArrayInfoImpl> arrays() {
/* 81 */     return (Map)super.arrays();
/*    */   }
/*    */   
/*    */   public RuntimeElementInfoImpl getElementInfo(Class scope, QName name) {
/* 85 */     return (RuntimeElementInfoImpl)super.getElementInfo(scope, name);
/*    */   }
/*    */   
/*    */   public Map<QName, RuntimeElementInfoImpl> getElementMappings(Class scope) {
/* 89 */     return (Map)super.getElementMappings(scope);
/*    */   }
/*    */   
/*    */   public Iterable<RuntimeElementInfoImpl> getAllElements() {
/* 93 */     return (Iterable)super.getAllElements();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeTypeInfoSetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */