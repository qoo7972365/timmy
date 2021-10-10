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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ObjectReferenceTemplateHolder
/*    */   implements Streamable
/*    */ {
/* 20 */   public ObjectReferenceTemplate value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectReferenceTemplateHolder() {}
/*    */ 
/*    */   
/*    */   public ObjectReferenceTemplateHolder(ObjectReferenceTemplate paramObjectReferenceTemplate) {
/* 28 */     this.value = paramObjectReferenceTemplate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 33 */     this.value = ObjectReferenceTemplateHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 38 */     ObjectReferenceTemplateHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 43 */     return ObjectReferenceTemplateHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableInterceptor/ObjectReferenceTemplateHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */