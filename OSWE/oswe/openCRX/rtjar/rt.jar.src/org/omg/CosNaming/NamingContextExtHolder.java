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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NamingContextExtHolder
/*    */   implements Streamable
/*    */ {
/* 26 */   public NamingContextExt value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public NamingContextExtHolder() {}
/*    */ 
/*    */   
/*    */   public NamingContextExtHolder(NamingContextExt paramNamingContextExt) {
/* 34 */     this.value = paramNamingContextExt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 39 */     this.value = NamingContextExtHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 44 */     NamingContextExtHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 49 */     return NamingContextExtHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/NamingContextExtHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */