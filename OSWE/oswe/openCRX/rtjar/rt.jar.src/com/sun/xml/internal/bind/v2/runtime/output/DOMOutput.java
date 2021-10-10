/*    */ package com.sun.xml.internal.bind.v2.runtime.output;
/*    */ 
/*    */ import com.sun.xml.internal.bind.marshaller.SAX2DOMEx;
/*    */ import com.sun.xml.internal.bind.v2.runtime.AssociationMap;
/*    */ import org.w3c.dom.Node;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.SAXException;
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
/*    */ public final class DOMOutput
/*    */   extends SAXOutput
/*    */ {
/*    */   private final AssociationMap assoc;
/*    */   
/*    */   public DOMOutput(Node node, AssociationMap assoc) {
/* 46 */     super((ContentHandler)new SAX2DOMEx(node));
/* 47 */     this.assoc = assoc;
/* 48 */     assert assoc != null;
/*    */   }
/*    */   
/*    */   private SAX2DOMEx getBuilder() {
/* 52 */     return (SAX2DOMEx)this.out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void endStartTag() throws SAXException {
/* 57 */     super.endStartTag();
/*    */     
/* 59 */     Object op = this.nsContext.getCurrent().getOuterPeer();
/* 60 */     if (op != null) {
/* 61 */       this.assoc.addOuter(getBuilder().getCurrentElement(), op);
/*    */     }
/* 63 */     Object ip = this.nsContext.getCurrent().getInnerPeer();
/* 64 */     if (ip != null)
/* 65 */       this.assoc.addInner(getBuilder().getCurrentElement(), ip); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/output/DOMOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */