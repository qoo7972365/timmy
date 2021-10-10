/*    */ package com.sun.xml.internal.stream.dtd.nonvalidating;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.QName;
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
/*    */ 
/*    */ public class XMLAttributeDecl
/*    */ {
/* 31 */   public final QName name = new QName();
/*    */ 
/*    */   
/* 34 */   public final XMLSimpleType simpleType = new XMLSimpleType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean optional;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValues(QName name, XMLSimpleType simpleType, boolean optional) {
/* 48 */     this.name.setValues(name);
/* 49 */     this.simpleType.setValues(simpleType);
/* 50 */     this.optional = optional;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 57 */     this.name.clear();
/* 58 */     this.simpleType.clear();
/* 59 */     this.optional = false;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/dtd/nonvalidating/XMLAttributeDecl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */