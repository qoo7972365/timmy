/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class TaggedProfileHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public TaggedProfile value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public TaggedProfileHolder() {}
/*    */ 
/*    */   
/*    */   public TaggedProfileHolder(TaggedProfile paramTaggedProfile) {
/* 20 */     this.value = paramTaggedProfile;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = TaggedProfileHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     TaggedProfileHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return TaggedProfileHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/TaggedProfileHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */