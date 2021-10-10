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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodAccessor_Boolean
/*    */   extends Accessor
/*    */ {
/*    */   public MethodAccessor_Boolean() {
/* 42 */     super(Boolean.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 46 */     return Boolean.valueOf(((Bean)bean).get_boolean());
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 50 */     ((Bean)bean).set_boolean((value == null) ? Const.default_value_boolean : ((Boolean)value).booleanValue());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/MethodAccessor_Boolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */