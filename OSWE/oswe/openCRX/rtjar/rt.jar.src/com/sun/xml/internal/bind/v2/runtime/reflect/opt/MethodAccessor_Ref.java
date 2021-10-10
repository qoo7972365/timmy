/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect.opt;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
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
/*    */ public class MethodAccessor_Ref
/*    */   extends Accessor
/*    */ {
/*    */   public MethodAccessor_Ref() {
/* 37 */     super(Ref.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 41 */     return ((Bean)bean).get_ref();
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 45 */     ((Bean)bean).set_ref((Ref)value);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/MethodAccessor_Ref.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */