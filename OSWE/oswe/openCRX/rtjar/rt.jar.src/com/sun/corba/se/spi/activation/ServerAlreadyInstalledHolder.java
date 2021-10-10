/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerAlreadyInstalledHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerAlreadyInstalled value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerAlreadyInstalledHolder() {}
/*    */ 
/*    */   
/*    */   public ServerAlreadyInstalledHolder(ServerAlreadyInstalled paramServerAlreadyInstalled) {
/* 20 */     this.value = paramServerAlreadyInstalled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerAlreadyInstalledHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerAlreadyInstalledHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerAlreadyInstalledHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerAlreadyInstalledHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */