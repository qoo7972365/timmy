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
/*    */ public final class ServiceContextListHolder
/*    */   implements Streamable
/*    */ {
/* 15 */   public ServiceContext[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ServiceContextListHolder() {}
/*    */ 
/*    */   
/*    */   public ServiceContextListHolder(ServiceContext[] paramArrayOfServiceContext) {
/* 23 */     this.value = paramArrayOfServiceContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 28 */     this.value = ServiceContextListHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 33 */     ServiceContextListHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 38 */     return ServiceContextListHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/ServiceContextListHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */