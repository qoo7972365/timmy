/*    */ package org.omg.CosNaming.NamingContextPackage;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NotFoundReasonHolder
/*    */   implements Streamable
/*    */ {
/* 16 */   public NotFoundReason value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NotFoundReasonHolder() {}
/*    */ 
/*    */   
/*    */   public NotFoundReasonHolder(NotFoundReason paramNotFoundReason) {
/* 24 */     this.value = paramNotFoundReason;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 29 */     this.value = NotFoundReasonHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 34 */     NotFoundReasonHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 39 */     return NotFoundReasonHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NamingContextPackage/NotFoundReasonHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */