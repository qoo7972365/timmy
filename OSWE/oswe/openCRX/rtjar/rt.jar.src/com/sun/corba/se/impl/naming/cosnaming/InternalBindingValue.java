/*    */ package com.sun.corba.se.impl.naming.cosnaming;
/*    */ 
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CosNaming.Binding;
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
/*    */ public class InternalBindingValue
/*    */ {
/*    */   public Binding theBinding;
/*    */   public String strObjectRef;
/*    */   public Object theObjectRef;
/*    */   
/*    */   public InternalBindingValue() {}
/*    */   
/*    */   public InternalBindingValue(Binding paramBinding, String paramString) {
/* 48 */     this.theBinding = paramBinding;
/* 49 */     this.strObjectRef = paramString;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/InternalBindingValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */