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
/*    */ public class FieldAccessor_Boolean
/*    */   extends Accessor
/*    */ {
/*    */   public FieldAccessor_Boolean() {
/* 42 */     super(Boolean.class);
/*    */   }
/*    */   
/*    */   public Object get(Object bean) {
/* 46 */     return Boolean.valueOf(((Bean)bean).f_boolean);
/*    */   }
/*    */   
/*    */   public void set(Object bean, Object value) {
/* 50 */     ((Bean)bean).f_boolean = (value == null) ? Const.default_value_boolean : ((Boolean)value).booleanValue();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/opt/FieldAccessor_Boolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */