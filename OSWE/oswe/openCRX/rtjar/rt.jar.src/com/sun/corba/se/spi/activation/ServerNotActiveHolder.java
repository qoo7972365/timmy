/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerNotActiveHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerNotActive value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerNotActiveHolder() {}
/*    */ 
/*    */   
/*    */   public ServerNotActiveHolder(ServerNotActive paramServerNotActive) {
/* 20 */     this.value = paramServerNotActive;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerNotActiveHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerNotActiveHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerNotActiveHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerNotActiveHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */