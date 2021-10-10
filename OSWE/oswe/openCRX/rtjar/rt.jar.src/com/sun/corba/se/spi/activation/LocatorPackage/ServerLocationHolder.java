/*    */ package com.sun.corba.se.spi.activation.LocatorPackage;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerLocationHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerLocation value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerLocationHolder() {}
/*    */ 
/*    */   
/*    */   public ServerLocationHolder(ServerLocation paramServerLocation) {
/* 20 */     this.value = paramServerLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerLocationHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerLocationHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerLocationHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorPackage/ServerLocationHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */