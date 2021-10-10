/*    */ package com.sun.xml.internal.bind.v2.runtime.property;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
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
/*    */ 
/*    */ 
/*    */ abstract class ArrayProperty<BeanT, ListT, ItemT>
/*    */   extends PropertyImpl<BeanT>
/*    */ {
/*    */   protected final Accessor<BeanT, ListT> acc;
/*    */   protected final Lister<BeanT, ListT, ItemT, Object> lister;
/*    */   
/*    */   protected ArrayProperty(JAXBContextImpl context, RuntimePropertyInfo prop) {
/* 47 */     super(context, prop);
/*    */     
/* 49 */     assert prop.isCollection();
/* 50 */     this.lister = Lister.create((Type)Utils.REFLECTION_NAVIGATOR
/* 51 */         .erasure(prop.getRawType()), prop.id(), prop.getAdapter());
/* 52 */     assert this.lister != null;
/* 53 */     this.acc = prop.getAccessor().optimize(context);
/* 54 */     assert this.acc != null;
/*    */   }
/*    */   
/*    */   public void reset(BeanT o) throws AccessorException {
/* 58 */     this.lister.reset(o, this.acc);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getIdValue(BeanT bean) {
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/ArrayProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */