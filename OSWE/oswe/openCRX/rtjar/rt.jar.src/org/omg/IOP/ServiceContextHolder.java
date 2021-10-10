/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class ServiceContextHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public ServiceContext value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServiceContextHolder() {}
/*    */ 
/*    */   
/*    */   public ServiceContextHolder(ServiceContext paramServiceContext) {
/* 20 */     this.value = paramServiceContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = ServiceContextHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     ServiceContextHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return ServiceContextHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/ServiceContextHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */