/*    */ package com.sun.xml.internal.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
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
/*    */ final class RuntimeAnyTypeImpl
/*    */   extends AnyTypeImpl<Type, Class>
/*    */   implements RuntimeNonElement
/*    */ {
/*    */   private RuntimeAnyTypeImpl() {
/* 39 */     super(Utils.REFLECTION_NAVIGATOR);
/*    */   }
/*    */   
/*    */   public <V> Transducer<V> getTransducer() {
/* 43 */     return null;
/*    */   }
/*    */   
/* 46 */   static final RuntimeNonElement theInstance = new RuntimeAnyTypeImpl();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeAnyTypeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */