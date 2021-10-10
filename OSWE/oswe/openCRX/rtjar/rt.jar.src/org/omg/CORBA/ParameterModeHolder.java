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
/*    */ public final class ParameterModeHolder
/*    */   implements Streamable
/*    */ {
/* 21 */   public ParameterMode value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ParameterModeHolder() {}
/*    */ 
/*    */   
/*    */   public ParameterModeHolder(ParameterMode paramParameterMode) {
/* 29 */     this.value = paramParameterMode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 34 */     this.value = ParameterModeHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 39 */     ParameterModeHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 44 */     return ParameterModeHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/ParameterModeHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */