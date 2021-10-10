/*    */ package org.omg.PortableInterceptor;
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
/*    */ public final class ObjectReferenceFactoryHolder
/*    */   implements Streamable
/*    */ {
/* 16 */   public ObjectReferenceFactory value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectReferenceFactoryHolder() {}
/*    */ 
/*    */   
/*    */   public ObjectReferenceFactoryHolder(ObjectReferenceFactory paramObjectReferenceFactory) {
/* 24 */     this.value = paramObjectReferenceFactory;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 29 */     this.value = ObjectReferenceFactoryHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 34 */     ObjectReferenceFactoryHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 39 */     return ObjectReferenceFactoryHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceFactoryHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */