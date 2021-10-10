/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ORBidListHolder
/*    */   implements Streamable
/*    */ {
/* 13 */   public String[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ORBidListHolder() {}
/*    */ 
/*    */   
/*    */   public ORBidListHolder(String[] paramArrayOfString) {
/* 21 */     this.value = paramArrayOfString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 26 */     this.value = ORBidListHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 31 */     ORBidListHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 36 */     return ORBidListHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/ORBidListHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */