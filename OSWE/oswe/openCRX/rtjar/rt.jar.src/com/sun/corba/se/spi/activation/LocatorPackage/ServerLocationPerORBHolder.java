/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerLocationPerORBHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerLocationPerORB value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerLocationPerORBHolder() {}
/*    */ 
/*    */   
/*    */   public ServerLocationPerORBHolder(ServerLocationPerORB paramServerLocationPerORB) {
/* 20 */     this.value = paramServerLocationPerORB;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerLocationPerORBHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerLocationPerORBHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerLocationPerORBHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorPackage/ServerLocationPerORBHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */