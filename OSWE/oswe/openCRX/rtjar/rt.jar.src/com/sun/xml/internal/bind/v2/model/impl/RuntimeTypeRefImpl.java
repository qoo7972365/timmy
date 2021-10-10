/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeRef;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*    */ import java.lang.reflect.Type;
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
/*    */ final class RuntimeTypeRefImpl
/*    */   extends TypeRefImpl<Type, Class>
/*    */   implements RuntimeTypeRef
/*    */ {
/*    */   public RuntimeTypeRefImpl(RuntimeElementPropertyInfoImpl elementPropertyInfo, QName elementName, Type type, boolean isNillable, String defaultValue) {
/* 43 */     super(elementPropertyInfo, elementName, type, isNillable, defaultValue);
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTarget() {
/* 47 */     return (RuntimeNonElement)super.getTarget();
/*    */   }
/*    */   
/*    */   public Transducer getTransducer() {
/* 51 */     return RuntimeModelBuilder.createTransducer((RuntimeNonElementRef)this);
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getSource() {
/* 55 */     return (RuntimePropertyInfo)this.owner;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeTypeRefImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */