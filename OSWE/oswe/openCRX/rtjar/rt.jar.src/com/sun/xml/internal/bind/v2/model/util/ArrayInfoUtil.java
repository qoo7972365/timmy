/*    */ package com.sun.xml.internal.bind.v2.model.util;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.TODO;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayInfoUtil
/*    */ {
/*    */   public static QName calcArrayTypeName(QName n) {
/*    */     String uri;
/* 47 */     if (n.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema")) {
/* 48 */       TODO.checkSpec("this URI");
/* 49 */       uri = "http://jaxb.dev.java.net/array";
/*    */     } else {
/* 51 */       uri = n.getNamespaceURI();
/* 52 */     }  return new QName(uri, n.getLocalPart() + "Array");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/util/ArrayInfoUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */