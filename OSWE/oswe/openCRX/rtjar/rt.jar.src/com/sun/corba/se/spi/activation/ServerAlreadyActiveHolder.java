/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyActiveHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerAlreadyActive value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyActiveHolder() {}
/*    */ 
/*    */   
/*    */   public ServerAlreadyActiveHolder(ServerAlreadyActive paramServerAlreadyActive) {
/* 20 */     this.value = paramServerAlreadyActive;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerAlreadyActiveHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerAlreadyActiveHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerAlreadyActiveHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerAlreadyActiveHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */