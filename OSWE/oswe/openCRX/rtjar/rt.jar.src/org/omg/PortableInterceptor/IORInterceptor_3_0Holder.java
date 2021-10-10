/*    */ package org.omg.PortableInterceptor;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class IORInterceptor_3_0Holder
/*    */   implements Streamable
/*    */ {
/* 12 */   public IORInterceptor_3_0 value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public IORInterceptor_3_0Holder() {}
/*    */ 
/*    */   
/*    */   public IORInterceptor_3_0Holder(IORInterceptor_3_0 paramIORInterceptor_3_0) {
/* 20 */     this.value = paramIORInterceptor_3_0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = IORInterceptor_3_0Helper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     IORInterceptor_3_0Helper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return IORInterceptor_3_0Helper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/IORInterceptor_3_0Holder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */