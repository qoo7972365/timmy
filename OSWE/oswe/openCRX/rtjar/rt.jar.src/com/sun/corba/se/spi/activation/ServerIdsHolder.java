/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ServerIdsHolder
/*    */   implements Streamable
/*    */ {
/* 13 */   public int[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerIdsHolder() {}
/*    */ 
/*    */   
/*    */   public ServerIdsHolder(int[] paramArrayOfint) {
/* 21 */     this.value = paramArrayOfint;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 26 */     this.value = ServerIdsHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 31 */     ServerIdsHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 36 */     return ServerIdsHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ServerIdsHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */