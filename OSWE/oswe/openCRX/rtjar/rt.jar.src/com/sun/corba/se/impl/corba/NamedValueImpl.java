/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.NamedValue;
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
/*    */ public class NamedValueImpl
/*    */   extends NamedValue
/*    */ {
/*    */   private String _name;
/*    */   private Any _value;
/*    */   private int _flags;
/*    */   private ORB _orb;
/*    */   
/*    */   public NamedValueImpl(ORB paramORB) {
/* 49 */     this._orb = paramORB;
/* 50 */     this._value = new AnyImpl(this._orb);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NamedValueImpl(ORB paramORB, String paramString, Any paramAny, int paramInt) {
/* 59 */     this._orb = paramORB;
/* 60 */     this._name = paramString;
/* 61 */     this._value = paramAny;
/* 62 */     this._flags = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 67 */     return this._name;
/*    */   }
/*    */ 
/*    */   
/*    */   public Any value() {
/* 72 */     return this._value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int flags() {
/* 77 */     return this._flags;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/NamedValueImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */