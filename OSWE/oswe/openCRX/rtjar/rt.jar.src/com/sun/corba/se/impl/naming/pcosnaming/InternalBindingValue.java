/*    */ package com.sun.corba.se.impl.naming.pcosnaming;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CosNaming.BindingType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InternalBindingValue
/*    */   implements Serializable
/*    */ {
/*    */   public BindingType theBindingType;
/*    */   public String strObjectRef;
/*    */   private transient Object theObjectRef;
/*    */   
/*    */   public InternalBindingValue() {}
/*    */   
/*    */   public InternalBindingValue(BindingType paramBindingType, String paramString) {
/* 55 */     this.theBindingType = paramBindingType;
/* 56 */     this.strObjectRef = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObjectRef() {
/* 61 */     return this.theObjectRef;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setObjectRef(Object paramObject) {
/* 66 */     this.theObjectRef = paramObject;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/InternalBindingValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */