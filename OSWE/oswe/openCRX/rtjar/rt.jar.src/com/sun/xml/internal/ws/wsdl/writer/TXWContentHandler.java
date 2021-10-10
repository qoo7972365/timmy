/*    */ package com.sun.xml.internal.ws.wsdl.writer;
/*    */ 
/*    */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*    */ import java.util.Stack;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.ContentHandler;
/*    */ import org.xml.sax.Locator;
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
/*    */ public class TXWContentHandler
/*    */   implements ContentHandler
/*    */ {
/*    */   Stack<TypedXmlWriter> stack;
/*    */   
/*    */   public TXWContentHandler(TypedXmlWriter txw) {
/* 41 */     this.stack = new Stack<>();
/* 42 */     this.stack.push(txw);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDocumentLocator(Locator locator) {}
/*    */ 
/*    */   
/*    */   public void startDocument() throws SAXException {}
/*    */ 
/*    */   
/*    */   public void endDocument() throws SAXException {}
/*    */ 
/*    */   
/*    */   public void startPrefixMapping(String prefix, String uri) throws SAXException {}
/*    */ 
/*    */   
/*    */   public void endPrefixMapping(String prefix) throws SAXException {}
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 61 */     TypedXmlWriter txw = ((TypedXmlWriter)this.stack.peek())._element(uri, localName, TypedXmlWriter.class);
/* 62 */     this.stack.push(txw);
/* 63 */     if (atts != null) {
/* 64 */       for (int i = 0; i < atts.getLength(); i++) {
/* 65 */         String auri = atts.getURI(i);
/* 66 */         if ("http://www.w3.org/2000/xmlns/".equals(auri)) {
/* 67 */           if ("xmlns".equals(atts.getLocalName(i))) {
/* 68 */             txw._namespace(atts.getValue(i), "");
/*    */           } else {
/* 70 */             txw._namespace(atts.getValue(i), atts.getLocalName(i));
/*    */           } 
/* 72 */         } else if (!"schemaLocation".equals(atts.getLocalName(i)) || 
/* 73 */           !"".equals(atts.getValue(i))) {
/*    */           
/* 75 */           txw._attribute(auri, atts.getLocalName(i), atts.getValue(i));
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 82 */     this.stack.pop();
/*    */   }
/*    */   
/*    */   public void characters(char[] ch, int start, int length) throws SAXException {}
/*    */   
/*    */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
/*    */   
/*    */   public void processingInstruction(String target, String data) throws SAXException {}
/*    */   
/*    */   public void skippedEntity(String name) throws SAXException {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/wsdl/writer/TXWContentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */