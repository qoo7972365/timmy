/*    */ package org.omg.CosNaming;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BindingIteratorHolder
/*    */   implements Streamable
/*    */ {
/* 21 */   public BindingIterator value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public BindingIteratorHolder() {}
/*    */ 
/*    */   
/*    */   public BindingIteratorHolder(BindingIterator paramBindingIterator) {
/* 29 */     this.value = paramBindingIterator;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 34 */     this.value = BindingIteratorHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 39 */     BindingIteratorHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 44 */     return BindingIteratorHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingIteratorHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */