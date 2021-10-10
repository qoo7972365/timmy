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
/*    */ 
/*    */ public final class ULongLongSeqHolder
/*    */   implements Streamable
/*    */ {
/* 40 */   public long[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ULongLongSeqHolder() {}
/*    */ 
/*    */   
/*    */   public ULongLongSeqHolder(long[] paramArrayOflong) {
/* 48 */     this.value = paramArrayOflong;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 53 */     this.value = ULongLongSeqHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 58 */     ULongLongSeqHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 63 */     return ULongLongSeqHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ULongLongSeqHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */