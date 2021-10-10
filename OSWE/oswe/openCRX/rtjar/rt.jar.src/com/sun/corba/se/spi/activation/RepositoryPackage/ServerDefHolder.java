/*    */ package com.sun.corba.se.spi.activation.RepositoryPackage;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServerDefHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServerDef value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServerDefHolder() {}
/*    */ 
/*    */   
/*    */   public ServerDefHolder(ServerDef paramServerDef) {
/* 20 */     this.value = paramServerDef;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServerDefHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServerDefHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServerDefHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/RepositoryPackage/ServerDefHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */