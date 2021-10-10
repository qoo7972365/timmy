/*    */ package com.sun.xml.internal.bind.util;
/*    */ 
/*    */ import com.sun.xml.internal.bind.ValidationEventLocatorEx;
/*    */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationEventLocatorExImpl
/*    */   extends ValidationEventLocatorImpl
/*    */   implements ValidationEventLocatorEx
/*    */ {
/*    */   private final String fieldName;
/*    */   
/*    */   public ValidationEventLocatorExImpl(Object target, String fieldName) {
/* 44 */     super(target);
/* 45 */     this.fieldName = fieldName;
/*    */   }
/*    */   
/*    */   public String getFieldName() {
/* 49 */     return this.fieldName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuffer buf = new StringBuffer();
/* 57 */     buf.append("[url=");
/* 58 */     buf.append(getURL());
/* 59 */     buf.append(",line=");
/* 60 */     buf.append(getLineNumber());
/* 61 */     buf.append(",column=");
/* 62 */     buf.append(getColumnNumber());
/* 63 */     buf.append(",node=");
/* 64 */     buf.append(getNode());
/* 65 */     buf.append(",object=");
/* 66 */     buf.append(getObject());
/* 67 */     buf.append(",field=");
/* 68 */     buf.append(getFieldName());
/* 69 */     buf.append("]");
/*    */     
/* 71 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/util/ValidationEventLocatorExImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */