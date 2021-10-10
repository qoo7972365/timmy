/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx;
/*    */ import java.util.Iterator;
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
/*    */ public class NamespaceContextExAdaper
/*    */   implements NamespaceContextEx
/*    */ {
/*    */   private final NamespaceContext nsContext;
/*    */   
/*    */   public NamespaceContextExAdaper(NamespaceContext nsContext) {
/* 39 */     this.nsContext = nsContext;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<NamespaceContextEx.Binding> iterator() {
/* 44 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/* 49 */     return this.nsContext.getNamespaceURI(prefix);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix(String namespaceURI) {
/* 54 */     return this.nsContext.getPrefix(namespaceURI);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator getPrefixes(String namespaceURI) {
/* 59 */     return this.nsContext.getPrefixes(namespaceURI);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/NamespaceContextExAdaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */