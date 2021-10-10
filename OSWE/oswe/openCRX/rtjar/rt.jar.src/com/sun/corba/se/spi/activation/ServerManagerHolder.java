/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerManagerHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerManager value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerManagerHolder() {}
/*    */ 
/*    */   
/*    */   public ServerManagerHolder(ServerManager paramServerManager) {
/* 20 */     this.value = paramServerManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerManagerHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerManagerHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerManagerHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerManagerHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */