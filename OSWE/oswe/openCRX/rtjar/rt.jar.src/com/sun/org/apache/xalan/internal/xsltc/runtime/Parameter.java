/*    */ package com.sun.org.apache.xalan.internal.xsltc.runtime;
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
/*    */ public class Parameter
/*    */ {
/*    */   public String _name;
/*    */   public Object _value;
/*    */   public boolean _isDefault;
/*    */   
/*    */   public Parameter(String name, Object value) {
/* 38 */     this._name = name;
/* 39 */     this._value = value;
/* 40 */     this._isDefault = true;
/*    */   }
/*    */   
/*    */   public Parameter(String name, Object value, boolean isDefault) {
/* 44 */     this._name = name;
/* 45 */     this._value = value;
/* 46 */     this._isDefault = isDefault;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/runtime/Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */