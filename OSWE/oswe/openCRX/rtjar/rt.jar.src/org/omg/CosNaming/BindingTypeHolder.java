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
/*    */ public final class BindingTypeHolder
/*    */   implements Streamable
/*    */ {
/* 17 */   public BindingType value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public BindingTypeHolder() {}
/*    */ 
/*    */   
/*    */   public BindingTypeHolder(BindingType paramBindingType) {
/* 25 */     this.value = paramBindingType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 30 */     this.value = BindingTypeHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 35 */     BindingTypeHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 40 */     return BindingTypeHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/BindingTypeHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */