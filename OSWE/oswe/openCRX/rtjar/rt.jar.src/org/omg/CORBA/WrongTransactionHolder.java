/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WrongTransactionHolder
/*    */   implements Streamable
/*    */ {
/* 39 */   public WrongTransaction value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public WrongTransactionHolder() {}
/*    */ 
/*    */   
/*    */   public WrongTransactionHolder(WrongTransaction paramWrongTransaction) {
/* 47 */     this.value = paramWrongTransaction;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 52 */     this.value = WrongTransactionHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 57 */     WrongTransactionHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 62 */     return WrongTransactionHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/WrongTransactionHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */