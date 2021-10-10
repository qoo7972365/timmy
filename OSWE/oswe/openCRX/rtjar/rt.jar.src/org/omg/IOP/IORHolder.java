/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class IORHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public IOR value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public IORHolder() {}
/*    */ 
/*    */   
/*    */   public IORHolder(IOR paramIOR) {
/* 20 */     this.value = paramIOR;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = IORHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     IORHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return IORHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/IORHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */