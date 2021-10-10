/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class BadServerDefinitionHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public BadServerDefinition value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public BadServerDefinitionHolder() {}
/*    */ 
/*    */   
/*    */   public BadServerDefinitionHolder(BadServerDefinition paramBadServerDefinition) {
/* 20 */     this.value = paramBadServerDefinition;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = BadServerDefinitionHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     BadServerDefinitionHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return BadServerDefinitionHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/BadServerDefinitionHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */