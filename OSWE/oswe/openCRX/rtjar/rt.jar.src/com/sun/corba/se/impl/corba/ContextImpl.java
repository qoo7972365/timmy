/*    */ package com.sun.corba.se.impl.corba;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.Context;
/*    */ import org.omg.CORBA.NVList;
/*    */ import org.omg.CORBA.ORB;
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
/*    */ public final class ContextImpl
/*    */   extends Context
/*    */ {
/*    */   private ORB _orb;
/*    */   private ORBUtilSystemException wrapper;
/*    */   
/*    */   public ContextImpl(ORB paramORB) {
/* 50 */     this._orb = paramORB;
/* 51 */     this.wrapper = ORBUtilSystemException.get((ORB)paramORB, "rpc.presentation");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContextImpl(Context paramContext) {
/* 58 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */   
/*    */   public String context_name() {
/* 63 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */   
/*    */   public Context parent() {
/* 68 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */   
/*    */   public Context create_child(String paramString) {
/* 73 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set_one_value(String paramString, Any paramAny) {
/* 78 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set_values(NVList paramNVList) {
/* 83 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void delete_values(String paramString) {
/* 89 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NVList get_values(String paramString1, int paramInt, String paramString2) {
/* 96 */     throw this.wrapper.contextNotImplemented();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/ContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */