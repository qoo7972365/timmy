/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ORBAlreadyRegisteredHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ORBAlreadyRegistered value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ORBAlreadyRegisteredHolder() {}
/*    */ 
/*    */   
/*    */   public ORBAlreadyRegisteredHolder(ORBAlreadyRegistered paramORBAlreadyRegistered) {
/* 20 */     this.value = paramORBAlreadyRegistered;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ORBAlreadyRegisteredHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ORBAlreadyRegisteredHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ORBAlreadyRegisteredHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ORBAlreadyRegisteredHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */