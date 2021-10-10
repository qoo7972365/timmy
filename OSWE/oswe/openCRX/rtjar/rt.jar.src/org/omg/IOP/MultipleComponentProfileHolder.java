/*    */ package org.omg.IOP;
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
/*    */ public final class MultipleComponentProfileHolder
/*    */   implements Streamable
/*    */ {
/* 15 */   public TaggedComponent[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public MultipleComponentProfileHolder() {}
/*    */ 
/*    */   
/*    */   public MultipleComponentProfileHolder(TaggedComponent[] paramArrayOfTaggedComponent) {
/* 23 */     this.value = paramArrayOfTaggedComponent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 28 */     this.value = MultipleComponentProfileHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 33 */     MultipleComponentProfileHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 38 */     return MultipleComponentProfileHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/MultipleComponentProfileHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */