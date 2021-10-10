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
/*    */ 
/*    */ public final class ObjectReferenceTemplateSeqHolder
/*    */   implements Streamable
/*    */ {
/* 17 */   public ObjectReferenceTemplate[] value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectReferenceTemplateSeqHolder() {}
/*    */ 
/*    */   
/*    */   public ObjectReferenceTemplateSeqHolder(ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate) {
/* 25 */     this.value = paramArrayOfObjectReferenceTemplate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 30 */     this.value = ObjectReferenceTemplateSeqHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 35 */     ObjectReferenceTemplateSeqHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 40 */     return ObjectReferenceTemplateSeqHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceTemplateSeqHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */