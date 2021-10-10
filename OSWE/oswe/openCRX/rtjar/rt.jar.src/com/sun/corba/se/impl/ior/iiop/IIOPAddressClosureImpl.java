/*    */ package com.sun.corba.se.impl.ior.iiop;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.closure.Closure;
/*    */ import org.omg.CORBA_2_3.portable.OutputStream;
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
/*    */ public final class IIOPAddressClosureImpl
/*    */   extends IIOPAddressBase
/*    */ {
/*    */   private Closure host;
/*    */   private Closure port;
/*    */   
/*    */   public IIOPAddressClosureImpl(Closure paramClosure1, Closure paramClosure2) {
/* 45 */     this.host = paramClosure1;
/* 46 */     this.port = paramClosure2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 51 */     return (String)this.host.evaluate();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 56 */     Integer integer = (Integer)this.port.evaluate();
/* 57 */     return integer.intValue();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/iiop/IIOPAddressClosureImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */