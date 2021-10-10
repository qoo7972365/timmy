/*    */ package com.sun.org.apache.xerces.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*    */ import java.util.Iterator;
/*    */ import java.util.Vector;
/*    */ import javax.xml.namespace.NamespaceContext;
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
/*    */ public class NamespaceContextWrapper
/*    */   implements NamespaceContext
/*    */ {
/*    */   private NamespaceContext fNamespaceContext;
/*    */   
/*    */   public NamespaceContextWrapper(NamespaceSupport namespaceContext) {
/* 51 */     this.fNamespaceContext = namespaceContext;
/*    */   }
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/* 55 */     if (prefix == null) {
/* 56 */       throw new IllegalArgumentException("Prefix can't be null");
/*    */     }
/* 58 */     return this.fNamespaceContext.getURI(prefix.intern());
/*    */   }
/*    */   
/*    */   public String getPrefix(String namespaceURI) {
/* 62 */     if (namespaceURI == null) {
/* 63 */       throw new IllegalArgumentException("URI can't be null.");
/*    */     }
/* 65 */     return this.fNamespaceContext.getPrefix(namespaceURI.intern());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator getPrefixes(String namespaceURI) {
/* 73 */     if (namespaceURI == null) {
/* 74 */       throw new IllegalArgumentException("URI can't be null.");
/*    */     }
/*    */ 
/*    */     
/* 78 */     Vector vector = ((NamespaceSupport)this.fNamespaceContext).getPrefixes(namespaceURI.intern());
/* 79 */     return vector.iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceContext getNamespaceContext() {
/* 87 */     return this.fNamespaceContext;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/NamespaceContextWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */